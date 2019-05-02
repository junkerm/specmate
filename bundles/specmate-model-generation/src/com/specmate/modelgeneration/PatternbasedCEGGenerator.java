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

public class PatternbasedCEGGenerator {
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
		URI dep = getLocalFile(folder + "/Dep " + lang.getLanguage().toUpperCase() + ".spec");
		URI pos = getLocalFile(folder + "/Pos " + lang.getLanguage().toUpperCase() + ".spec");
		URI rule= getLocalFile(folder + "/Rule "+ lang.getLanguage().toUpperCase() + ".spec");
		
		this.rules = XTextUtil.generateMatchers(rule, dep, pos); 
		this.tagger = tagger;
		this.creation = new CEGCreation();
		this.lang = lang;
	}

	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(PatternbasedCEGGenerator.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
	
	public void createModel(CEGModel model, String text) throws SpecmateException {
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
				// => List of Causes + Final Effect
				// Resolve Causes				
				DirectCause directCause = resolveCauses(model, nodes, causes);
				
				resolveEffect(model, nodes, directCause, effect);
			}
		}
	}
	
	private DirectCause resolveCauses(CEGModel model, LinkedList<CEGNode> nodes, List<MatchResult> causes) {
		DirectCause result = new DirectCause();
		// Get Maximal Depth
		// (MAX_cause in causes GETCAUSEDEPTH)
		int maxDepth = causes.stream().mapToInt(this::getCauseDepth).max().getAsInt();
		// Create Positioning Table
		int[] positioningTable = new int[maxDepth];
		Arrays.fill(positioningTable, 0);
		
		for(int i=0; i<causes.size(); i++) {
			MatchResult cause = causes.get(i);
			// Resolve Single Cause
			DirectCause dirCause = resolveCause(model, nodes, cause, positioningTable, maxDepth-1);

			// If there are multiple causes
			if(causes.size() > 1) {
				int xPos = XSTART + maxDepth * XOFFSET;
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
				int xPos = XSTART + XOFFSET*(offset-1);
				int yPos = YSTART + YOFFSET*(posTable[offset-1]);
				String variable = "Inner Node "+(offset)+"-"+(posTable[offset-1]+1);
				CEGNode node = this.creation.createNode(model, variable, "is fulfilled", xPos, yPos, dA.effectType);
				posTable[offset-1]++;
				nodes.add(node);
				fullyConnect(model, dA, node);
				dA.positiveCauses.clear();
				dA.negativeCauses.clear();
				dA.positiveCauses.add(node);
			}
			
			if(dBCauseCount > 1) {
				// Create Inner Node
				int xPos = XSTART + XOFFSET*(offset-1);
				int yPos = YSTART + YOFFSET*(posTable[offset-1]);
				String variable = "Inner Node "+(offset)+"-"+(posTable[offset-1]+1);
				CEGNode node = this.creation.createNode(model, variable, "is fulfilled", xPos, yPos, dA.effectType);
				posTable[offset-1]++;
				nodes.add(node);
				fullyConnect(model, dB, node);
				dB.positiveCauses.clear();
				dB.negativeCauses.clear();
				dB.positiveCauses.add(node);
			}
			
			
			if(cause.getRuleName().contains("_XOR")) {
				// Create inner XOR Nodes use them as new positive set
				
			}
			
			if(cause.getRuleName().contains("_NOR")) {
				// Swap positive & negative causes, then AND
				List<CEGNode> tmp = dA.negativeCauses;
				dA.negativeCauses = dA.positiveCauses;
				dA.positiveCauses = tmp;
				
				tmp = dB.negativeCauses;
				dB.negativeCauses = dB.positiveCauses;
				dB.positiveCauses = tmp;
			}
			
			if(cause.getRuleName().contains("_OR")) {
				result.effectType = NodeType.OR;
				// Swap result type, then AND
			}
			
			// Default / AND
			// Combine positive & negative causes of dA & dB in result
			result.positiveCauses.addAll(dA.positiveCauses);
			result.positiveCauses.addAll(dB.positiveCauses);
			result.negativeCauses.addAll(dA.negativeCauses);
			result.negativeCauses.addAll(dB.negativeCauses);
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
			int posX = XSTART + offset * XOFFSET;
			int posY = YSTART + posTable[offset] * YOFFSET;
			CEGNode node = this.creation.createNodeIfNotExist(nodes, model, causeText, "", posX, posY, NodeType.AND);
			if(!nodes.contains(node)) {
				nodes.add(node);
				posTable[offset]++;
			}
			result.positiveCauses.add(node);
		}
		return result;
	}
	
	private void fullyConnect(CEGModel model,DirectCause dirCause, CEGNode node) {
		for(CEGNode pCause: dirCause.positiveCauses) {
			this.creation.createConnection(model, pCause, node, false);
		}
		for(CEGNode nCause: dirCause.negativeCauses) {
			this.creation.createConnection(model, nCause, node, true);
		}
	}

	private void resolveEffect(CEGModel model, LinkedList<CEGNode> nodes, DirectCause directCause, MatchResult effect) {
		// For Each Conjunction Element
			// Resolve Negations
			// if Node Negated
				// Swap positiveCauses & negativeCauses
			// Create Node of effectType at effectPosition
			// positiveCauses -->  Node
			// negativeCauses -!-> Node
	}
	
	private class DirectCause {
		public List<CEGNode> positiveCauses;
		public List<CEGNode> negativeCauses;
		public NodeType effectType;
		
		public DirectCause() {
			this.positiveCauses = new Vector<CEGNode>();
			this.negativeCauses = new Vector<CEGNode>();
			this.effectType = NodeType.AND;
		}
	}
}
