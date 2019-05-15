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
import com.specmate.cause_effect_patterns.parse.wrapper.MatchResultWrapper;
import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.cause_effect_patterns.resolve.XTextUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.util.EnglishSentenceUnfolder;
import com.specmate.nlp.util.GermanSentenceUnfolder;
import com.specmate.nlp.util.SentenceUnfolderBase;

public class PatternbasedCEGGenerator implements ICEGFromRequirementGenerator {
	private INLPService tagger;
	private List<MatchRule> rules;
	private CEGCreation creation;
	private ELanguage lang;
	
	private static final int XSTART  = 225;
	private static final int YSTART  = 225;
	
	private static final int XOFFSET = 300;
	private static final int YOFFSET = 150;
	
	
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
	
	private String preprocessData(String text) throws SpecmateException {
		SentenceUnfolderBase unfolder;
		if(this.lang == ELanguage.DE) {
			unfolder = new GermanSentenceUnfolder();
		} else {
			unfolder = new EnglishSentenceUnfolder();
		}
		return unfolder.unfold(this.tagger, text, this.lang);
	}
	
	public CEGModel createModel(CEGModel model, String text) throws SpecmateException {
		{
			System.out.println(text);
			JCas tagResult = this.tagger.processText(text, this.lang);
			DependencyParsetree data = DependencyParsetree.generateFromJCas(tagResult);
			System.out.println(data);
		}
		
		text = preprocessData(text);
		JCas tagResult = this.tagger.processText(text, this.lang);
		DependencyParsetree data = DependencyParsetree.generateFromJCas(tagResult);
		List<MatchResult> results = MatchUtil.evaluateRuleset(this.rules, data);
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();
		
		for(MatchResult result: results) {
			MatchResultWrapper res = new MatchResultWrapper(result);
			
			Vector<MatchResultWrapper> limits = new Vector<MatchResultWrapper>();
			while(res.isLimitedCondition()) {
				limits.add(res.getFirstArgument());
				res = res.getSecondArgument();
			}
			
			if(res.isCondition()) {
				// Resolve Cause & Effect
				
				MatchResultWrapper cause = res.getFirstArgument();
				MatchResultWrapper effect= res.getSecondArgument();
				
				Vector<MatchResultWrapper> causes = new Vector<MatchResultWrapper>();
				causes.add(cause);
				// If Effect is a cause/effect itself
				while(effect.isCondition()) {
					causes.add(effect.getFirstArgument());
					// Decent until we reach the final effect
					effect = effect.getSecondArgument();
				}
				
				// Get Maximal Depth
				int maxDepth = causes.stream().mapToInt(this::getCauseDepth).max().getAsInt();
				
				if(causes.size() == 1 && maxDepth > 1) {
					// We can save a layer if we only have one direct cause
					maxDepth--;
				}
				
				// Create Positioning Table with extra layer for the effects
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
	
	private String innerVariableString() {
		if(this.lang == ELanguage.DE) {
			return "Innerer Knoten";
		}
		return "Inner Node";
	}
	
	private String innerConditionString() {
		if(this.lang == ELanguage.DE) {
			return "ist erfüllt";
		}
		return "is fulfilled";
	}
	
	private DirectCause resolveCauses(CEGModel model, LinkedList<CEGNode> nodes, List<MatchResultWrapper> causes, int[] positioningTable, int offset) {
		DirectCause result = new DirectCause();
		for(int i=0; i<causes.size(); i++) {
			MatchResultWrapper cause = causes.get(i);
			// Resolve Single Cause
			int trueOffset = (causes.size() > 1)? offset-1: offset;
			DirectCause dirCause = resolveCause(model, nodes, cause, positioningTable, trueOffset);

			// If there are multiple causes
			if(causes.size() > 1) {
				int xPos = XSTART + (offset) * XOFFSET;
				int yPos = YSTART + i * YOFFSET;
				// Generate Fake Effect Nodes
				String var  = innerVariableString();
				String cond = innerConditionString(); 
				CEGNode node = this.creation.createNode(model, var+" "+(i+1), cond, xPos , yPos, dirCause.effectType);
				fullyConnect(model, dirCause, node);
				// Add Fake Effect Node to DirectCause Positive Causes
				result.positiveCauses.add(node);
			} else {
				// Add Resolved Positive/Negative Causes to DirectCause
				result = dirCause;
			}
		}
			
		return result;
	}
	
	/**
	 * Compute how many layers of nodes we need for the node diagram
	 * @param causes
	 * @return
	 */
	private int getCauseDepth(MatchResultWrapper cause) {
		if(cause.isConjunction()) {
			int dA = getCauseDepth(cause.getFirstArgument());
			int dB = getCauseDepth(cause.getSecondArgument());
			int result = Math.max(dA, dB);
			
			if(cause.isXorConjunction()) {
				// max(A,B)+2 for XOR
				return result + 2;
			} 
			// max(A,B)+1 for AND,OR,NOR
			return result + 1;
		}
		
		if(cause.isNegation()) {
			// A+0 for NOT
			return getCauseDepth(cause.getFirstArgument());
		}
		return 1;
	}
	
	private DirectCause resolveCause(CEGModel model, LinkedList<CEGNode> nodes, MatchResultWrapper cause, int[] posTable, int offset) {
		DirectCause result = new DirectCause();
		
		if(cause.isConjunction()) {
			
			int nodeOffset = 0;
			if(cause.isXorConjunction()) {
				nodeOffset = 1;
			}
			
			MatchResultWrapper partA = cause.getFirstArgument();
			int depthA = getCauseDepth(partA);
			int offsetA = depthA > 1? 1 : 0;
			
			MatchResultWrapper partB = cause.getSecondArgument();
			int depthB = getCauseDepth(partA);
			int offsetB = depthB > 1? 1 : 0;
			
			DirectCause dA = resolveCause(model, nodes, partA, posTable, offset - nodeOffset - offsetA);
			int dACauseCount = dA.negativeCauses.size() + dA.positiveCauses.size();
			
			DirectCause dB = resolveCause(model, nodes, partB, posTable, offset - nodeOffset - offsetB);
			int dBCauseCount = dB.negativeCauses.size() + dB.positiveCauses.size();
			
			if(dACauseCount > 1) {
				// Create Inner Node
				String variable = innerVariableString()+ " "+(offset-nodeOffset+1)+"-"+(posTable[offset-nodeOffset]+1);
				CEGNode node = addNode(model, nodes, variable, innerConditionString(), posTable, offset-nodeOffset, dA.effectType);
				fullyConnect(model, dA, node);
				dA.positiveCauses.clear();
				dA.negativeCauses.clear();
				dA.positiveCauses.add(node);
			}
			
			if(dBCauseCount > 1) {
				// Create Inner Node
				String variable = innerVariableString()+ " "+(offset-nodeOffset+1)+"-"+(posTable[offset-nodeOffset]+1);
				CEGNode node = addNode(model, nodes, variable, innerConditionString(), posTable, offset-nodeOffset, dB.effectType);
				fullyConnect(model, dB, node);
				dB.positiveCauses.clear();
				dB.negativeCauses.clear();
				dB.positiveCauses.add(node);
			}
			
			if(cause.isXorConjunction()) {
				// Create inner XOR Nodes use them as new positive set
				// Create XOR Node
				// Directly connect all other nodes normally
				// Connect one node negated
				// Add XOR Node to positiveCauses 

				DirectCause tmp = DirectCause.mergeCauses(dA, dB);
				for(CEGNode pNode: tmp.positiveCauses) {
					String variable = innerVariableString()+ " "+ (offset+1)+"-"+(posTable[offset]+1);
					CEGNode xorNode = addNode(model, nodes, variable, innerConditionString(), posTable, offset, NodeType.AND);
					xorConnect(model, tmp, xorNode, pNode);
					result.positiveCauses.add(xorNode);
				}
				
				for(CEGNode nNode: tmp.negativeCauses) {
					String variable = innerVariableString()+ " "+ (offset+1)+"-"+(posTable[offset]+1);
					CEGNode xorNode = addNode(model, nodes, variable, innerConditionString(), posTable, offset, NodeType.AND);
					xorConnect(model, tmp, xorNode, nNode);
					result.positiveCauses.add(xorNode);
				}
				dA.negativeCauses.clear();
				dA.positiveCauses.clear();
				dB.negativeCauses.clear();
				dB.positiveCauses.clear();
				result.effectType = NodeType.OR;				
			} else if(cause.isNorConjunction()) {
				// Swap positive & negative causes, then AND
				List<CEGNode> tmp = dA.negativeCauses;
				dA.negativeCauses = dA.positiveCauses;
				dA.positiveCauses = tmp;
				
				tmp = dB.negativeCauses;
				dB.negativeCauses = dB.positiveCauses;
				dB.positiveCauses = tmp;
			} else if(cause.isOrConjunction()) {
				result.effectType = NodeType.OR;
				// Swap result type, then AND
			}
			// Default / AND
			// Combine positive & negative causes of dA & dB in result
			result.addCauses(dA);
			result.addCauses(dB);
		} else if(cause.isNegation()) {
			DirectCause dHead = resolveCause(model, nodes, cause.getFirstArgument(), posTable, offset);
			result.positiveCauses = dHead.negativeCauses;
			result.negativeCauses = dHead.positiveCauses;
			if(dHead.effectType.equals(NodeType.AND)) {
				result.effectType = NodeType.OR;
			}
		} else {
			// Create Direct Node
			CEGNode node = addDirectNode(model, nodes, cause, posTable, offset, NodeType.AND);
			result.positiveCauses.add(node);
		}
		return result;
	}
	
	private CEGNode addNode(CEGModel model, LinkedList<CEGNode> nodes, String variable, String condition, int[] posTable, int offset, NodeType type) {
		int posX = XSTART + offset * XOFFSET;
		int posY = YSTART + posTable[offset] * YOFFSET;
		
		int oldSize = nodes.size();
		CEGNode node = this.creation.createNodeIfNotExist(nodes, model, variable, condition, posX, posY, type);
		if(nodes.size() != oldSize) {
			nodes.add(node);
			posTable[offset]++;
		}
		return node;
	}
	
	private CEGNode addDirectNode(CEGModel model, LinkedList<CEGNode> nodes, MatchResultWrapper result, int[] posTable, int offset, NodeType type) {
		String variable = "";
		String condition = "";
		
		if(result.isConditionVarible()) {
			variable = result.getFirstArgument().result.getMatchTree().getRepresentationString(true);
			MatchResultWrapper condWrap = result.getSecondArgument();
			if(condWrap.isVerbObject()) {
				String verb = condWrap.getFirstArgument().result.getMatchTree().getRepresentationString(true);
				String object = condWrap.getSecondArgument().result.getMatchTree().getRepresentationString(false);
				condition = verb + " " + object;
			} else {
				condition = condWrap.result.getMatchTree().getRepresentationString(true);	
			}
		} else if (result.isVerbObject()) {
			variable  = result.getSecondArgument().result.getMatchTree().getRepresentationString(true);
			condition = result.getFirstArgument().result.getMatchTree().getRepresentationString(true);
		} else {
			variable = result.result.getMatchTree().getRepresentationString(true);
		} 
		return addNode(model, nodes, variable, condition, posTable, offset, type);
	}
	
	private void fullyConnect(CEGModel model, DirectCause dirCause, CEGNode node) {
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

	private void resolveEffect(CEGModel model, LinkedList<CEGNode> nodes, DirectCause directCause, MatchResultWrapper effect, int[] positioningTable, int offset) {
		if(effect.isConjunction()) { 
			// Resolve Conjunctions
			resolveEffect(model, nodes, directCause, effect.getFirstArgument(), positioningTable, offset);
			resolveEffect(model, nodes, directCause, effect.getSecondArgument(), positioningTable, offset);
		} else if(effect.isNegation()) {
			// Resolve Negations
			resolveEffect(model, nodes, directCause.swapPosNegCauses(), effect.getFirstArgument(), positioningTable, offset);
		} else {
			CEGNode node = addDirectNode(model, nodes, effect, positioningTable, offset, directCause.effectType);
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
