package com.specmate.modelgeneration;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.uima.jcas.JCas;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;
import com.specmate.cause_effect_patterns.parse.matcher.MatchResult;
import com.specmate.cause_effect_patterns.parse.matcher.MatchRule;
import com.specmate.cause_effect_patterns.parse.matcher.MatchUtil;
import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.cause_effect_patterns.resolve.XTextUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

public class PatternbasedCEGGenerator implements ICEGFromRequirementGenerator {
	private INLPService tagger;
	private List<MatchRule> rules;
	private CEGCreation creation;
	private ELanguage lang;
	
	private static final int XSTART  = 100;
	private static final int YSTART  = 50;
	private static final int XOFFSET = 250;
	private static final int YOFFSET = 60;
	
	
	public PatternbasedCEGGenerator(ELanguage lang, INLPService tagger) throws URISyntaxException, XTextException {
		this("resources/"+lang.getLanguage().toUpperCase(), lang, tagger);
	}
	
	public PatternbasedCEGGenerator(String folder, ELanguage lang, INLPService tagger) throws URISyntaxException, XTextException {
		URI dep = getLocalFile(folder + "/Dep_" + lang.getLanguage().toUpperCase() + ".spec");
		URI pos = getLocalFile(folder + "/Pos_" + lang.getLanguage().toUpperCase() + ".spec");
		URI rule= getLocalFile(folder + "/Rule_"+ lang.getLanguage().toUpperCase() + ".spec");
		
		this.rules = XTextUtil.generateMatchers(rule, dep, pos); 
		this.tagger = tagger;
		this.creation = new CEGCreation();
		this.lang = lang;
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(PatternbasedCEGGenerator.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
	
	public CEGModel createModel(CEGModel model, String text) throws SpecmateException {
		JCas tagResult = this.tagger.processText(text, this.lang);
		DependencyParsetree data = DependencyParsetree.generateFromJCas(tagResult);
		List<MatchResult> results = MatchUtil.evaluateRuleset(this.rules, data);
		
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();
		
		for(MatchResult result: results) {
			if(result.isSuccessfulMatch() && result.hasSubmatch("Cause") && result.hasSubmatch("Effect")) {
				// Resolve Cause & Effect
				MatchResult cause = result.getSubmatch("Cause");
				MatchResult effect = result.getSubmatch("Effect");
				
				Vector<MatchResult> causes = new Vector<MatchResult>();
				causes.add(cause);
				// If Effect is a cause/effect itself
				while(effect.hasSubmatch("Cause") && effect.hasSubmatch("Effect")) {
					causes.add(effect.getSubmatch("Cause"));
					// Decent until we reach the final effect
					effect = effect.getSubmatch("Effect");
				}
				
				// Get Maximal Depth
				// (MAX_cause in causes GETCAUSEDEPTH)
				int maxDepth = causes.stream().mapToInt(this::getCauseDepth).max().getAsInt();
				if(causes.size() > 1) {
					maxDepth++;
				}
				
				// Create Positioning Table
				int[] positioningTable = new int[maxDepth + 1];
				Arrays.fill(positioningTable, 0);
				
				// => List of Causes + Final Effect
				// Resolve Causes 
				DirectCause directCause = resolveCauses(model, nodes, causes, positioningTable, maxDepth-1);
				resolveEffect(model, nodes, directCause, effect, positioningTable, maxDepth);
			}
		}
		return model;
	}
	
	private DirectCause resolveCauses(CEGModel model, LinkedList<CEGNode> nodes, List<MatchResult> causes, int[] positioningTable, int offset) {
		DirectCause result = new DirectCause();
		for(int i=0; i<causes.size(); i++) {
			MatchResult cause = causes.get(i);
			// Resolve Single Cause
			int trueOffset = (causes.size() > 1)? offset-1: offset;
			DirectCause dirCause = resolveCause(model, nodes, cause, positioningTable, trueOffset);

			// If there are multiple causes
			if(causes.size() > 1) {
				int xPos = XSTART + (offset) * XOFFSET;
				int yPos = YSTART + i * YOFFSET;
				// Generate Fake Effect Nodes
				CEGNode node = this.creation.createNode(model, "Inner Cause "+(i+1), "is fulfilled", xPos , yPos, dirCause.effectType);
				fullyConnect(model, dirCause, node);
				// Add Fake Effect Node to DirectCause Positive Causes
				result.positiveCauses.add(node);
			} else {
				// Add Resolved Positive/Negative Causes to DirectCause
				result.positiveCauses.addAll(dirCause.positiveCauses);
				result.negativeCauses.addAll(dirCause.negativeCauses);
			}
		}
			
		return result;
	}
	
	/**
	 * Compute how many layers of nodes we need for the node diagram
	 * @param causes
	 * @return
	 */
	private int getCauseDepth(MatchResult cause) {
		if(cause.hasSubmatch("PartA") && cause.hasSubmatch("PartB")) {
			int dA = getCauseDepth(cause.getSubmatch("PartA"));
			int dB = getCauseDepth(cause.getSubmatch("PartB"));
			int result = Math.max(dA, dB);
			
			if(cause.getRuleName().contains("_XOR")) {
				// max(A,B)+2 for XOR
				return result + 2;
			} 
			// max(A,B)+1 for AND,OR,NOR
			return result + 1;
		}
		
		if(cause.hasSubmatch("Head")) {
			// A+0 for NOT
			return getCauseDepth(cause.getSubmatch("Head"));
		}
		return 1;
	}
	
	private DirectCause resolveCause(CEGModel model, LinkedList<CEGNode> nodes, MatchResult cause, int[] posTable, int offset) {
		DirectCause result = new DirectCause();
		
		if(cause.hasSubmatch("PartA") && cause.hasSubmatch("PartB")) {
			int subOffset = 1;
			if(cause.getRuleName().contains("_XOR")) {
				subOffset = 2;
			}
			DirectCause dA = resolveCause(model, nodes, cause.getSubmatch("PartA"), posTable, offset - subOffset);
			int dACauseCount = dA.negativeCauses.size() + dA.positiveCauses.size();
			DirectCause dB = resolveCause(model, nodes, cause.getSubmatch("PartB"), posTable, offset - subOffset);
			int dBCauseCount = dB.negativeCauses.size() + dB.positiveCauses.size();
			
			if(dACauseCount > 1) {
				// Create Inner Node
				String variable = "Inner Node "+(offset-subOffset+1)+"-"+(posTable[offset-subOffset]+1);
				CEGNode node = addNode(model, nodes, variable, "is fulfilled", posTable, offset-subOffset, dA.effectType);
				fullyConnect(model, dA, node);
				dA.positiveCauses.clear();
				dA.negativeCauses.clear();
				dA.positiveCauses.add(node);
			}
			
			if(dBCauseCount > 1) {
				// Create Inner Node
				String variable = "Inner Node "+(offset-subOffset+1)+"-"+(posTable[offset-subOffset]+1);
				CEGNode node = addNode(model, nodes, variable, "is fulfilled", posTable, offset-subOffset, dB.effectType);
				fullyConnect(model, dB, node);
				dB.positiveCauses.clear();
				dB.negativeCauses.clear();
				dB.positiveCauses.add(node);
			}
			
			if(cause.getRuleName().contains("_XOR")) {
				// Create inner XOR Nodes use them as new positive set
				// Create XOR Node
				// Directly connect all other nodes normally
				// Connect one node negated
				// Add XOR Node to positiveCauses 

				DirectCause tmp = DirectCause.mergeCauses(dA, dB);
				for(CEGNode pNode: tmp.positiveCauses) {
					String variable = "XOR Node "+ (offset)+"-"+(posTable[offset-1]+1);
					CEGNode xorNode = addNode(model, nodes, variable, "is fulfilled", posTable, offset-1, NodeType.AND);
					xorConnect(model, tmp, xorNode, pNode);
					result.positiveCauses.add(xorNode);
				}
				
				for(CEGNode nNode: tmp.negativeCauses) {
					String variable = "XOR Node "+ (offset)+"-"+(posTable[offset-1]+1);
					CEGNode xorNode = addNode(model, nodes, variable, "is fulfilled", posTable, offset-1, NodeType.AND);
					xorConnect(model, tmp, xorNode, nNode);
					result.positiveCauses.add(xorNode);
				}
				dA.negativeCauses.clear();
				dA.positiveCauses.clear();
				dB.negativeCauses.clear();
				dB.positiveCauses.clear();
				result.effectType = NodeType.OR;				
			} else if(cause.getRuleName().contains("_NOR")) {
				// Swap positive & negative causes, then AND
				List<CEGNode> tmp = dA.negativeCauses;
				dA.negativeCauses = dA.positiveCauses;
				dA.positiveCauses = tmp;
				
				tmp = dB.negativeCauses;
				dB.negativeCauses = dB.positiveCauses;
				dB.positiveCauses = tmp;
			} else if(cause.getRuleName().contains("_OR")) {
				result.effectType = NodeType.OR;
				// Swap result type, then AND
			}
			// Default / AND
			// Combine positive & negative causes of dA & dB in result
			result.addCauses(dA);
			result.addCauses(dB);
		} else if(cause.hasSubmatch("Head")) {
			DirectCause dHead = resolveCause(model, nodes, cause.getSubmatch("Head"), posTable, offset);
			result.positiveCauses = dHead.negativeCauses;
			result.negativeCauses = dHead.positiveCauses;
			if(dHead.effectType.equals(NodeType.AND)) {
				result.effectType = NodeType.OR;
			}
		} else {
			// Create Direct Node
			String causeText = cause.getMatchTree().getTextInterval(0).text; 
			CEGNode node = addNode(model, nodes, causeText, "", posTable, offset, NodeType.AND);
			result.positiveCauses.add(node);
		}
		return result;
	}
	
	private CEGNode addNode(CEGModel model, LinkedList<CEGNode> nodes, String variable, String condition, int[] posTable, int offset, NodeType type) {
		int posX = XSTART + offset * XOFFSET;
		int posY = YSTART + posTable[offset] * YOFFSET;
		CEGNode node = this.creation.createNodeIfNotExist(nodes, model, variable, condition, posX, posY, type);
		if(!nodes.contains(node)) {
			nodes.add(node);
			posTable[offset]++;
		}
		return node;
	}
	
	private void fullyConnect(CEGModel model,DirectCause dirCause, CEGNode node) {
		for(CEGNode pCause: dirCause.positiveCauses) {
			this.creation.createConnection(model, pCause, node, false);
		}
		for(CEGNode nCause: dirCause.negativeCauses) {
			this.creation.createConnection(model, nCause, node, true);
		}
	}
	
	private void xorConnect(CEGModel model, DirectCause dirCause, CEGNode node, CEGNode invertedNode) {
		for(CEGNode pCause: dirCause.positiveCauses) {
			boolean inverted = false;
			if(pCause.equals(invertedNode)) {
				inverted = true;
			}
			this.creation.createConnection(model, pCause, node, inverted);
		}
		for(CEGNode nCause: dirCause.negativeCauses) {
			boolean inverted = true;
			if(nCause.equals(invertedNode)) {
				inverted = false;
			}
			this.creation.createConnection(model, nCause, node, inverted);
		}
	}

	private void resolveEffect(CEGModel model, LinkedList<CEGNode> nodes, DirectCause directCause, MatchResult effect, int[] positioningTable, int offset) {
		
		if(effect.hasSubmatch("PartA") && effect.hasSubmatch("PartB")) { 
			// Resolve Conjunctions
			resolveEffect(model, nodes, directCause, effect.getSubmatch("PartA"), positioningTable, offset);
			resolveEffect(model, nodes, directCause, effect.getSubmatch("PartB"), positioningTable, offset);
		} else if(effect.hasSubmatch("Head")) {
			// Resolve Negations
			resolveEffect(model, nodes, directCause.swapPosNegCauses(), effect.getSubmatch("Head"), positioningTable, offset);
		} else {
			String variable = effect.getMatchTree().getTextInterval(0).text;
			String condition = "";
			CEGNode node = addNode(model, nodes, variable, condition, positioningTable, offset, directCause.effectType);
			fullyConnect(model, directCause, node);
		}
	}
	
	private static class DirectCause {
		public List<CEGNode> positiveCauses;
		public List<CEGNode> negativeCauses;
		public NodeType effectType;
		
		public DirectCause() {
			this.positiveCauses = new Vector<CEGNode>();
			this.negativeCauses = new Vector<CEGNode>();
			this.effectType = NodeType.AND;
		}
		
		public void addCauses(DirectCause cause) {
			this.negativeCauses.addAll(cause.negativeCauses);
			this.positiveCauses.addAll(cause.positiveCauses);
		}
		
		public static DirectCause mergeCauses(DirectCause causeA, DirectCause causeB) {
			DirectCause result = new DirectCause();
			result.addCauses(causeA);
			result.addCauses(causeB);
			return result;
		}
		
		public DirectCause swapPosNegCauses() {
			DirectCause result = new DirectCause();
			result.effectType = this.effectType;
			result.negativeCauses = this.positiveCauses;
			result.positiveCauses = this.negativeCauses;
			return result;
		}
	}
}
