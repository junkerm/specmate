package com.specmate.modelgeneration;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
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
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;

/**
 * Class create a CEGModel from a given text by extracting causes and effects
 * using the {@link INLPService}.
 *
 * @author Andreas Wehrle
 *
 */
public abstract class CEGFromRequirementGenerator {

	/** The logging service */
	private LogService logService;

	/** The NLP service */
	private INLPService nlpService;

	private CEGCreation cegCreation;
	private int levelOneX = 100;
	private int levelOneY = 50;
	private int levelTwoX = 350;
	private int levelTwoY = 110;
	private int levelThreeX = 600;
	private int levelThreeY = 150;

	public CEGFromRequirementGenerator(LogService logService, INLPService nlpService) {
		super();
		this.logService = logService;
		this.nlpService = nlpService;
		cegCreation = new CEGCreation();
	}

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
		JCas jcas = nlpService.processText(text, ELanguage.DE);
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
		String[] causeEffectArray = getPatternMatcher().detectCauseAndEffect(sentence, jCas);
		cause = causeEffectArray[0];
		effect = causeEffectArray[1];

		if (!StringUtils.isEmpty(cause)) {
			String[] effects = getAndOrSplitter().textSplitterAnd(effect, sentence, jCas);
			String[] causes = new String[] { cause };
			if (containsConjection(cause) && containsDisjunction(cause)) {
				causes = getAndOrSplitter().textSplitterAnd(cause, sentence, jCas);
				String[] causesTemp = causes.clone();
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionEffect[0], splittedVariabelAndConditionEffect[1], levelThreeX,
							levelThreeY, NodeType.AND);
					levelThreeY += 100;
					for (int j = 0; j < causes.length; j++) {
						String[] causesOr = getAndOrSplitter().textSplitterOr(causes[j], sentence, jCas);
						String causeNew = cuttingEnds(causes[j]);
						causesTemp = ArrayUtils.addAll(causesTemp, causesOr);
						if (causesOr.length == 1) {
							String causeNewOr = cuttingEnds(causesOr[0]);
							if (notReplacement(causeNewOr) != null) {
								String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
										sentence, notReplacement(causesOr[0]));
								CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
										splittedVariabelAndConditionCauseOr[0], splittedVariabelAndConditionCauseOr[1],
										levelOneX, levelOneY, NodeType.AND);
								cegCreation.createConnection(model, causeNodeOr, effectNode, true);
							} else {
								String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
										sentence, causesOr[0]);
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
									splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1],
									levelTwoX, levelTwoY, NodeType.OR);
							for (int k = 0; k < causesOr.length; k++) {
								String causeNewOr = cuttingEnds(causesOr[k]);
								if (notReplacement(causeNewOr) != null) {
									String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
											sentence, notReplacement(causesOr[k]));
									CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
											splittedVariabelAndConditionCauseOr[0],
											splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
									cegCreation.createConnection(model, causeNodeOr, causeNode, true);
								} else {
									String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
											sentence, causesOr[k]);
									CEGNode causeNodeOr = cegCreation.createNodeIfNotExist(nodes, model,
											splittedVariabelAndConditionCauseOr[0],
											splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
									cegCreation.createConnection(model, causeNodeOr, causeNode, false);
								}
								levelOneY += 100;
							}
							cegCreation.createConnection(model, causeNode, effectNode, false);
							levelTwoY += 100;
						}
					}
				}
			} else if (isConjunction(cause)) {// 'and' in the sentence
				causes = getAndOrSplitter().textSplitterAnd(cause, sentence, jCas);
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionEffect[0], splittedVariabelAndConditionEffect[1], levelTwoX,
							levelTwoY, NodeType.AND);
					levelTwoY += 100;
					for (int j = 0; j < causes.length; j++) {
						String causeNew = cuttingEnds(causes[j]);
						if (notReplacement(causeNew) != null) {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									notReplacement(causes[j]));
							CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNode, effectNode, true);
						} else {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									causes[j]);
							CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNode, effectNode, false);
						}
						levelOneY += 100;
					}
				}
			} else if (isDisjunction(cause)) { // 'or' in the sentence
				causes = getAndOrSplitter().textSplitterOr(cause, sentence, jCas);
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionEffect[0], splittedVariabelAndConditionEffect[1], levelTwoX,
							levelTwoY, NodeType.OR);
					levelTwoY += 100;
					for (int j = 0; j < causes.length; j++) {
						String causeNew = cuttingEnds(causes[j]);
						if (notReplacement(causeNew) != null) {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									notReplacement(causes[j]));
							CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNode, effectNode, true);
						} else {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									causes[j]);
							CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
									splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1],
									levelOneX, levelOneY, NodeType.AND);
							cegCreation.createConnection(model, causeNode, effectNode, false);
						}
						levelOneY += 100;
					}
				}
			} else {
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = cegCreation.createNodeIfNotExist(nodes, model,
							splittedVariabelAndConditionEffect[0], splittedVariabelAndConditionEffect[1], levelTwoX,
							levelTwoY, NodeType.AND);
					levelTwoY += 100;
					String causeNew = cuttingEnds(cause);
					if (notReplacement(causeNew) != null) {
						String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
								notReplacement(cause));
						CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
								splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
								levelOneY, NodeType.AND);
						cegCreation.createConnection(model, causeNode, effectNode, true);
					} else {
						String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
								cause);
						CEGNode causeNode = cegCreation.createNodeIfNotExist(nodes, model,
								splittedVariabelAndConditionCause[0], splittedVariabelAndConditionCause[1], levelOneX,
								levelOneY, NodeType.AND);
						cegCreation.createConnection(model, causeNode, effectNode, false);
					}

				}
				levelOneY += 100;
			}
		} else {
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
		int begin = sentence.getBegin() + sentence.getCoveredText().indexOf(text);
		int end = sentence.getBegin() + sentence.getCoveredText().indexOf(text) + text.length();
		if (begin == -1) {
			begin = sentence.getBegin();
			end = sentence.getEnd();
		}
		String[] back = new String[] { text, "" };
		List<Chunk> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);

		for (Chunk np : nounPhrases) {
			if (np.getBegin() >= begin && np.getEnd() <= end) {
				String covered = np.getCoveredText();
				String[] splitted = covered.split("( and )|( or )");
				for (int k = 0; k < splitted.length; k++) {
					if (notReplacement(splitted[k]) != null) {
						if (text.contains(notReplacement(splitted[k]))) {
							back[0] = cuttingEnds(notReplacement(splitted[k]));
							back[1] = cuttingEnds(text.replace(notReplacement(splitted[k]), ""));
							return back;
						}
					} else {
						if (text.contains(splitted[k])) {
							back[0] = cuttingEnds(splitted[k]);
							back[1] = cuttingEnds(text.replace(splitted[k], ""));
							return back;
						}
					}
				}
			}
		}
		return back;
	}

	/**
	 * Replace a negation in the sentence.
	 *
	 * @param text
	 * @return text without negation or null if no negation was found
	 */
	public String notReplacement(String text) {
		if (text.contains("no ")) {
			return text.replace("no ", " ");
		}
		if (text.contains("cannot ")) {
			return text.replace("cannot ", "can ");
		}
		if (text.contains("not ")) {
			return text.replace("not ", "");
		}
		if (text.contains("n't ")) {
			return text.replace("n't ", " ");
		}
		return null;

	}

	/**
	 * Remove spaces, dots and commas at the end or beginning of the text.
	 *
	 * @param text
	 * @return
	 */
	public String cuttingEnds(String text) {
		text = text.trim();
		if (text.endsWith(".") || text.endsWith(",")) {
			text = text.substring(0, text.length() - 1).trim();
		}
		if (text.startsWith(",")) {
			text = text.substring(1, text.length()).trim();
		}
		return text;
	}

	/**
	 * Return the first verbphrase after the nounphrase with the given position
	 *
	 * @param pos
	 *            position of the nounphrase
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return verbphrase
	 */
	public Chunk getVPafterNP(int pos, Sentence sentence, JCas jCas) {
		List<Chunk> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);
		List<Chunk> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Chunk np : nounPhrases) {
			if (pos >= np.getBegin() && pos <= np.getEnd()) {
				pos = np.getEnd();
				break;
			}
		}

		Chunk back = null;
		int best = Integer.MAX_VALUE;
		for (Chunk vp : verbPhrases) {
			if (vp.getBegin() >= pos && vp.getBegin() < best) {
				best = vp.getBegin();
				back = vp;
			}
		}
		return back;

	}

	/**
	 * Return the next verbphrase before the given position
	 *
	 * @param pos
	 *            position
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return verbphrase
	 */
	public Chunk getVPBeforePosition(int pos, Sentence sentence, JCas jCas) {
		List<Chunk> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);
		List<Chunk> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Chunk np : nounPhrases) {
			if (pos >= np.getBegin() && pos <= np.getEnd()) {
				pos = np.getBegin();
				break;
			}
		}
		Chunk back = null;
		int best = 0;
		for (Chunk vp : verbPhrases) {
			if (vp.getEnd() >= best && vp.getEnd() <= pos) {
				best = vp.getEnd();
				back = vp;
			}
		}
		return back;
	}

	protected abstract AndOrSplitter getAndOrSplitter();

	protected abstract PatternMatcher getPatternMatcher();

}
