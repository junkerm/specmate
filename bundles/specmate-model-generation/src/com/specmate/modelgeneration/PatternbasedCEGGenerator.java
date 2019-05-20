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
import com.specmate.cause_effect_patterns.parse.wrapper.MatchResultWrapper.RuleType;
import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.cause_effect_patterns.resolve.XTextUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
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
		text = preprocessData(text);
		JCas tagResult = this.tagger.processText(text, this.lang);
		DependencyParsetree data = DependencyParsetree.generateFromJCas(tagResult);
		List<MatchResult> results = MatchUtil.evaluateRuleset(this.rules, data);
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();
		
		boolean generatedSomething = false;
		for(MatchResult result: results) {
			if(!result.isSuccessfulMatch()) {
				continue;
			}
			
			MatchResultWrapper res = new MatchResultWrapper(result);
			
			Vector<MatchResultWrapper> limits = new Vector<MatchResultWrapper>();
			while(res.isLimitedCondition()) {
				limits.add(res.getFirstArgument());
				res = res.getSecondArgument();
			}
			
			if(res.isCondition()) {
				generatedSomething = true;
				// Resolve Cause & Effect
				
				MatchResultWrapper cause = res.getFirstArgument();
				MatchResultWrapper effect= res.getSecondArgument();
				
				Vector<MatchResultWrapper> causes = new Vector<MatchResultWrapper>();
				causes.add(fixOrderOfOperations(cause));
				// If Effect is a cause/effect itself
				while(effect.isCondition()) {
					// Correct Order of Operations
					MatchResultWrapper orderedCause = fixOrderOfOperations(effect.getFirstArgument());
					causes.add(orderedCause);
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
		if(!generatedSomething) {
			throw new SpecmateInternalException(ErrorCode.NLP, "No Cause-Effect Pair Found.");
		}
		
		return model;
	}
	
	private MatchResultWrapper fixOrderOfOperations(MatchResultWrapper cause) {
		if(cause.getArgumentCount() == 0) {
			return cause;
		}
		
		if(cause.isNegation()) {
			return fixOrderOfOperations(cause.getFirstArgument());
		}
		
		fixOrderOfOperations(cause.getFirstArgument());
		fixOrderOfOperations(cause.getSecondArgument());
		
		RuleType type 		= cause.getType();
		RuleType typeChild  = cause.getFirstArgument().getType();
		if(type.getPriority() > typeChild.getPriority() && typeChild != RuleType.NEGATION) {
			// Left Swap
			cause.leftSwap();
			fixOrderOfOperations(cause.getFirstArgument());
		}
		
		typeChild = cause.getSecondArgument().getType();
		if(type.getPriority() > typeChild.getPriority() && typeChild != RuleType.NEGATION) {
			// Right Swap
			cause.rightSwap();
			fixOrderOfOperations(cause);
		}
		
		return cause;
	}

	private String innerVariableString() {
		if(this.lang == ELanguage.DE) {
			return "Innerer Knoten";
		}
		return "Inner Node";
	}
	
	private String innerConditionString() {
		if(this.lang == ELanguage.DE) {
			return "Ist erfüllt";
		}
		return "Is fulfilled";
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
			RuleType type = cause.getType();
			
			List<MatchResultWrapper> worklist = new LinkedList<MatchResultWrapper>(Arrays.asList(cause.getFirstArgument(),cause.getSecondArgument()));
			int result = -1;
			while(!worklist.isEmpty()) {
				MatchResultWrapper wrap = worklist.remove(0);
				RuleType typeWrap = wrap.getType();
				if(type.equals(typeWrap)) {
					worklist.add(wrap.getFirstArgument());
					worklist.add(wrap.getSecondArgument());
				} else {
					int depth = getCauseDepth(wrap);
					if(depth > result) {
						result = depth;
					}
				}
			}
			
			
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
			
			List<MatchResultWrapper> worklist = new LinkedList<MatchResultWrapper>(Arrays.asList(cause.getFirstArgument(),cause.getSecondArgument()));
			Vector<DirectCause> arguments = new Vector<DirectCause>();
			
			RuleType type = cause.getType();
			while(!worklist.isEmpty()) {
				MatchResultWrapper wrap = worklist.remove(0);
				RuleType typeWrap = wrap.getType();
				if(type.equals(typeWrap)) {
					worklist.add(wrap.getFirstArgument());
					worklist.add(wrap.getSecondArgument());
				} else {
					int argDepth = getCauseDepth(wrap);
					int argOffset = argDepth > 1 ? 1 : 0; 
					DirectCause argument = resolveCause(model, nodes, wrap, posTable, offset - nodeOffset - argOffset);
					int argCauseCount = argument.positiveCauses.size() + argument.negativeCauses.size();
					if(argCauseCount > 1) {
						// Create Inner Node
						String variable = innerVariableString()+ " "+(offset-nodeOffset+1)+"-"+(posTable[offset-nodeOffset]+1);
						CEGNode node = addNode(model, nodes, variable, innerConditionString(), posTable, offset-nodeOffset, argument.effectType);
						fullyConnect(model, argument, node);
						argument.positiveCauses.clear();
						argument.negativeCauses.clear();
						argument.positiveCauses.add(node);
					}
					arguments.add(argument);
				}
			}
			
			DirectCause merge = DirectCause.mergeCauses(arguments.toArray(new DirectCause[arguments.size()]));
			
			if(cause.isXorConjunction()) {
				// Create inner XOR Nodes use them as new positive set
				// Create XOR Node
				// Directly connect all other nodes normally
				// Connect one node negated
				// Add XOR Node to positiveCauses 

				for(CEGNode pNode: merge.positiveCauses) {
					String variable = innerVariableString()+ " "+ (offset+1)+"-"+(posTable[offset]+1);
					CEGNode xorNode = addNode(model, nodes, variable, innerConditionString(), posTable, offset, NodeType.AND);
					xorConnect(model, merge, xorNode, pNode);
					result.positiveCauses.add(xorNode);
				}
				
				for(CEGNode nNode: merge.negativeCauses) {
					String variable = innerVariableString()+ " "+ (offset+1)+"-"+(posTable[offset]+1);
					CEGNode xorNode = addNode(model, nodes, variable, innerConditionString(), posTable, offset, NodeType.AND);
					xorConnect(model, merge, xorNode, nNode);
					result.positiveCauses.add(xorNode);
				}
				result.effectType = NodeType.OR;				
			} else if(cause.isNorConjunction()) {
				// Swap positive & negative causes, then AND
				// XXX Technically NOR is not associative so a nested NOR would turn up with a not equivalent result, however since nested NORs usually don't happen we ignore this case.
				List<CEGNode> tmp = merge.negativeCauses;
				merge.negativeCauses = merge.positiveCauses;
				merge.positiveCauses = tmp;
			} else if(cause.isOrConjunction()) {
				result.effectType = NodeType.OR;
				// Swap result type, then AND
			}
			// Default / AND
			// Combine positive & negative causes of dA & dB in result
			result.addCauses(merge);
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
				MatchResultWrapper verbWrap = condWrap.getFirstArgument();
				String verb = verbWrap.result.getMatchTree().getRepresentationString(true);
				if(verbWrap.isVerbPreposition()) {
					String prep = verbWrap.getSecondArgument().result.getMatchTree().getRepresentationString(false);
					verb = verbWrap.getFirstArgument().result.getMatchTree().getRepresentationString(true) + " " + prep;
				}
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
		
		// Make Condition Positive
		condition = cleanCondition(condition);
		
		return addNode(model, nodes, variable, condition, posTable, offset, type);
	}
	
	private String cleanCondition(String condition) {
		if(this.lang == ELanguage.DE) {
			condition = condition.replaceAll("(?i)\\b(nicht)\\b", "");
			condition = condition.replaceAll("(?i)\\bk(ein(en?)?)\\b", "$1");
			condition = capitalize(condition);
		}
		return condition;
	}
	
	private String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1); 
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
		
		public static DirectCause mergeCauses(DirectCause... causes) {
			DirectCause result = new DirectCause();
			for(DirectCause subCause:causes) {
				result.addCauses(subCause);	
			}
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
