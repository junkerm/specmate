package com.specmate.modelgeneration;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

/**
 * Class create a CEGModel from a given text by extracting causes and effects
 * using the {@link INLPService}.
 *
 * @author Andreas Wehrle
 *
 */
public abstract class CEGFromRequirementGenerator {

	private LogService logService;
	private INLPService tagger;
	private ICauseEffectPatternMatcher patternMatcher;
	private IAndOrSplitter andOrSplitter;
	private CEGCreation cegCreation;
	private int levelOneX = 100;
	private int levelOneY = 50;
	private int levelTwoX = 350;
	private int levelTwoY = 110;
	private int levelThreeX = 600;
	private int levelThreeY = 150;

	public CEGFromRequirementGenerator(LogService logService, INLPService tagger) {
		super();
		this.logService = logService;
		this.tagger = tagger;
		patternMatcher = getPatternMatcher();
		andOrSplitter = getAndOrSplitter();
		cegCreation = new CEGCreation();
	}

	protected abstract IAndOrSplitter getAndOrSplitter();

	protected abstract ICauseEffectPatternMatcher getPatternMatcher();

	protected abstract ELanguage getLanguage();

	/**
	 * Replace a negation in the sentence.
	 *
	 * @param text
	 * @return text without negation or null if no negation was found
	 */
	protected abstract String replaceNegation(String original);

	/**
	 * Add the nodes and connections to the given CEGModel, which are extracted from
	 * the text.
	 *
	 * @param model
	 *            the CEGModel to add the nodes/connections
	 * @param text
	 *            text of the requirement
	 * @return generated CEGModel
	 */
	public CEGModel createModel(CEGModel model, String text) throws SpecmateException {
		JCas jcas = tagger.processText(text, getLanguage());
		model.getContents().clear();
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();

		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
			detectCausality(sentence, jcas, model, nodes);
		}

		return model;
	}

	/**
	 * Method add the nodes and connections detected from the sentence to the given
	 * CEGModel
	 *
	 * @param sentence
	 *            sentences to detect causal relation
	 * @param jCas
	 *            NLP tagged text
	 * @param model
	 *            CEGModel to add nodes/connections
	 * @param nodes
	 *            list of all nodes in the graph
	 * @throws SpecmateInternalException
	 */
	public void detectCausality(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes)
			throws SpecmateInternalException {
		String cause = "";
		String effect = "";
		String[] causeEffectArray = patternMatcher.detectCauseAndEffect(sentence, jCas);
		cause = causeEffectArray[0];
		effect = causeEffectArray[1];

		if (!StringUtils.isEmpty(cause)) {
			String[] effects = andOrSplitter.textSplitterAnd(effect, sentence, jCas);
			if (andOrSplitter.containsAnd(cause) && andOrSplitter.containsOr(cause)) {// 'or' and 'and' in the sentence
				handleAndOrInCause(sentence, jCas, model, nodes, cause, effects);
			} else if (andOrSplitter.containsAnd(cause)) {// 'and' in the sentence
				handleOnlyAndInCause(sentence, jCas, model, nodes, cause, effects);
			} else if (andOrSplitter.containsOr(cause)) { // 'or' in the sentence
				handleOnlyOrInCause(sentence, jCas, model, nodes, cause, effects);
			} else {
				handleNeitherAndNorOrInCause(sentence, jCas, model, nodes, cause, effects);
			}
		} else {
		}
	}

	private void handleNeitherAndNorOrInCause(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes,
			String cause, String[] effects) {
		for (int i = 0; i < effects.length; i++) {
			String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence, effects[i]);
			CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
					splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.AND);
			levelTwoY += 100;
			String causeNew = trimWithPunctuation(cause);
			if (replaceNegation(causeNew) != null) {
				String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
						replaceNegation(cause));
				CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
						splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
				cegCreation.createConnection(model, causeNode, effectNode, true);
			} else {
				String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence, cause);
				CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
						splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
				cegCreation.createConnection(model, causeNode, effectNode, false);
			}

		}
		levelOneY += 100;
	}

	private void handleOnlyOrInCause(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes,
			String cause, String[] effects) {
		String[] causes;
		causes = andOrSplitter.textSplitterOr(cause, sentence, jCas);
		for (int i = 0; i < effects.length; i++) {
			String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence, effects[i]);
			CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
					splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.OR);
			levelTwoY += 100;
			for (int j = 0; j < causes.length; j++) {
				String causeNew = trimWithPunctuation(causes[j]);
				if (replaceNegation(causeNew) != null) {
					String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
							replaceNegation(causes[j]));
					CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
							levelOneY, NodeType.AND);
					cegCreation.createConnection(model, causeNode, effectNode, true);
				} else {
					String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
							causes[j]);
					CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
							levelOneY, NodeType.AND);
					cegCreation.createConnection(model, causeNode, effectNode, false);
				}
				levelOneY += 100;
			}
		}
	}

	private void handleOnlyAndInCause(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes,
			String cause, String[] effects) {
		String[] causes;
		causes = andOrSplitter.textSplitterAnd(cause, sentence, jCas);
		for (int i = 0; i < effects.length; i++) {
			String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence, effects[i]);
			CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
					splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.AND);
			levelTwoY += 100;
			for (int j = 0; j < causes.length; j++) {
				String causeNew = trimWithPunctuation(causes[j]);
				if (replaceNegation(causeNew) != null) {
					String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
							replaceNegation(causes[j]));
					CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
							levelOneY, NodeType.AND);
					cegCreation.createConnection(model, causeNode, effectNode, true);
				} else {
					String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
							causes[j]);
					CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
							levelOneY, NodeType.AND);
					cegCreation.createConnection(model, causeNode, effectNode, false);
				}
				levelOneY += 100;
			}
		}
	}

	private void handleAndOrInCause(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes,
			String cause, String[] effects) {
		String[] causes;
		causes = andOrSplitter.textSplitterAnd(cause, sentence, jCas);
		String[] causesTemp = causes.clone();
		for (int i = 0; i < effects.length; i++) {
			String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence, effects[i]);
			CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
					splittedVariabelAndConditionEffect[1], levelThreeX, levelThreeY, NodeType.AND);
			levelThreeY += 100;
			for (int j = 0; j < causes.length; j++) {
				String[] causesOr = andOrSplitter.textSplitterOr(causes[j], sentence, jCas);
				causesTemp = ArrayUtils.addAll(causesTemp, causesOr);
				if (causesOr.length == 1) {
					String causeNewOr = trimWithPunctuation(causesOr[0]);
					if (replaceNegation(causeNewOr) != null) {
						String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas, sentence,
								replaceNegation(causesOr[0]));
						CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
								splittedVariabelAndConditionCauseOr[0], splittedVariabelAndConditionCauseOr[1],
								levelOneX, levelOneY, NodeType.AND);
						cegCreation.createConnection(model, causeNodeOr, effectNode, true);
					} else {
						String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas, sentence,
								causesOr[0]);
						CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
								splittedVariabelAndConditionCauseOr[0], splittedVariabelAndConditionCauseOr[1],
								levelOneX, levelOneY, NodeType.AND);
						cegCreation.createConnection(model, causeNodeOr, effectNode, false);
					}
					levelOneY += 100;

				} else {
					String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
							causes[j]);
					CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelTwoX,
							levelTwoY, NodeType.OR);
					for (int k = 0; k < causesOr.length; k++) {
						String causeNewOr = trimWithPunctuation(causesOr[k]);
						if (replaceNegation(causeNewOr) != null) {
							String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
									sentence, replaceNegation(causesOr[k]));
							CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCauseOr[0], splittedVariabelAndConditionCauseOr[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNodeOr, causeNode, true);
						} else {
							String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
									sentence, causesOr[k]);
							CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCauseOr[0], splittedVariabelAndConditionCauseOr[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNodeOr, causeNode, false);
						}
						levelOneY += 100;
					}
					cegCreation.createConnection(model, causeNode, effectNode, false);
					levelTwoY += 100;
				}
			}
		}
	}

	/**
	 * Method split a cause/effect in the variable and the condition
	 *
	 * @param jCas
	 *            NLP tagged text
	 * @param sentence
	 *            sentence containing the cause/effect
	 * @param text
	 *            the cause/effect
	 * @return array containing the variable and the condition. First element:
	 *         variable, second element condition
	 */
	public String[] splitNodeInVariableAndCondition(JCas jCas, Sentence sentence, String text) {
		String sentenceCoveredText = sentence.getCoveredText();
		int beginInSentence = sentence.getBegin() + sentenceCoveredText.indexOf(text);
		int endInSentence = sentence.getBegin() + sentenceCoveredText.indexOf(text) + text.length();

		// Happens when negations have been replaced in text
		if (beginInSentence == -1) {
			beginInSentence = sentence.getBegin();
			endInSentence = sentence.getEnd();
		}
		String[] result = new String[] { text, "" };
		List<Constituent> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);

		for (Constituent np : nounPhrases) {
			if (np.getBegin() >= beginInSentence && np.getEnd() <= endInSentence) {
				String covered = np.getCoveredText();
				String[] splitted = andOrSplitter.splitAndOrSimple(covered);
				for (int k = 0; k < splitted.length; k++) {
					if (replaceNegation(splitted[k]) != null) {
						if (text.contains(replaceNegation(splitted[k]))) {
							result[0] = trimWithPunctuation(replaceNegation(splitted[k]));
							result[1] = trimWithPunctuation(text.replace(replaceNegation(splitted[k]), ""));
							return result;
						}
					} else {
						if (text.contains(splitted[k])) {
							result[0] = trimWithPunctuation(splitted[k]);
							result[1] = trimWithPunctuation(text.replace(splitted[k], ""));
							return result;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Remove spaces, dots and commas at the end or beginning of the text.
	 *
	 * @param text
	 * @return
	 */
	public String trimWithPunctuation(String text) {
		text = text.trim();
		if (text.endsWith(".") || text.endsWith(",")) {
			text = text.substring(0, text.length() - 1).trim();
		}
		if (text.startsWith(",")) {
			text = text.substring(1, text.length()).trim();
		}
		return text;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	void setNlptagging(INLPService tagger) {
		this.tagger = tagger;
	}

}
