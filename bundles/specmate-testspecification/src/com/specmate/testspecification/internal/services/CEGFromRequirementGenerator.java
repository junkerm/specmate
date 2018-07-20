package com.specmate.testspecification.internal.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.VP;

/**
 * Class create a CEGModel from a given text by extracting causes and effects
 * using the {@link NLPTagger}.
 * 
 * @author Andreas Wehrle
 *
 */
public class CEGFromRequirementGenerator {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private LogService logService;
	private NLPTagger tagger;
	private int levelOneX = 100;
	private int levelOneY = 50;
	private int levelTwoX = 350;
	private int levelTwoY = 110;
	private int levelThreeX = 600;
	private int levelThreeY = 150;

	public CEGFromRequirementGenerator(LogService logService, NLPTagger tagger) {
		super();
		this.logService = logService;
		this.tagger = tagger;
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
	public CEGModel createModel(CEGModel model, String text) {
		JCas jcas = null;
		try {
			jcas = tagger.tagText(text);
		} catch (SpecmateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.getContents().clear();
		LinkedList<CEGNode> nodes = new LinkedList<CEGNode>();
		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
			detectCausality(sentence, jcas, model, nodes);
		}

		return model;
	}

	/**
	 * Method splits the sentences into the cause and the effect if it matches one
	 * pattern
	 * 
	 * @param sentence
	 *            sentence to split
	 * @param jCas
	 *            NLPTagged text
	 * @return array with two elements. First element: cause, second element:effect
	 */
	public String[] detectCauseAndEffect(Sentence sentence, JCas jCas) {
		String cause = "";
		String effect = "";
		String sentenceText = sentence.getCoveredText();

		if (matchPattern0When(sentence, jCas)) {
			int posComma = sentenceText.indexOf(",");
			for (Token constituent : JCasUtil.selectCovered(jCas, Token.class, sentence)) {
				if (constituent.getPosValue().equals(",")
						&& constituent.getParent().getType().getShortName().equals("S")
						&& constituent.getParent().getCoveredText().equals(sentence.getCoveredText())) {
					posComma = constituent.getBegin() - sentence.getBegin();
				}
			}
			cause = sentenceText.substring(5, posComma);
			effect = sentenceText.substring(posComma + 2, sentenceText.length() - 1);
		}
		if (matchPattern0RevWhen(sentence, jCas)) {
			int posIf = sentenceText.indexOf("when");

			cause = sentenceText.substring(posIf + 5, sentenceText.length() - 1);
			effect = sentenceText.substring(0, posIf - 1);
		}

		if (matchPattern0(sentence, jCas)) {
			int posComma = sentenceText.indexOf(",");
			for (Token constituent : JCasUtil.selectCovered(jCas, Token.class, sentence)) {
				if (constituent.getPosValue().equals(",")
						&& constituent.getParent().getType().getShortName().equals("S")
						&& constituent.getParent().getCoveredText().equals(sentence.getCoveredText())) {
					posComma = constituent.getBegin() - sentence.getBegin();
				}
			}
			cause = sentenceText.substring(3, posComma);
			effect = sentenceText.substring(posComma + 2, sentenceText.length() - 1);
		}
		if (matchPattern0Rev(sentence, jCas)) {
			int posIf = sentenceText.indexOf("if");

			cause = sentenceText.substring(posIf + 3, sentenceText.length() - 1);
			effect = sentenceText.substring(0, posIf - 1);
		}
		
		if (matchPattern0Then(sentence, jCas)) {
			int posThen = sentenceText.indexOf("then");
			cause = sentenceText.substring(3, posThen);
			effect = sentenceText.substring(posThen + 5, sentenceText.length() - 1);
		}
		
		if (matchPattern0WhenThen(sentence, jCas)) {
			int posThen = sentenceText.indexOf("then");
			cause = sentenceText.substring(5, posThen);
			effect = sentenceText.substring(posThen + 5, sentenceText.length() - 1);
		}

		if (matchPattern13_1(sentence, jCas)) {
			String[] value = causalityPattern13_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern13_2(sentence, jCas)) {
			String[] value = causalityPattern13_2(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern14_1(sentence, jCas)) {
			String[] value = causalityPattern14_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern14_1_1(sentence, jCas)) {
			String[] value = causalityPattern14_1_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern14_2(sentence, jCas)) {
			String[] value = causalityPattern14_2(sentence, jCas);
			Arrays.toString(value);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern15(sentence, jCas)) {
			String[] value = causalityPattern15(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern16_1(sentence, jCas)) {
			String[] value = causalityPattern16_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern16_2(sentence, jCas)) {
			String[] value = causalityPattern16_2(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern17_1(sentence, jCas)) {
			String[] value = causalityPattern17_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern17_2(sentence, jCas)) {
			String[] value = causalityPattern17_2(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern18_1(sentence, jCas)) {
			String[] value = causalityPattern18_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern18_2(sentence, jCas)) {
			String[] value = causalityPattern18_2(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern19(sentence, jCas)) {
			String[] value = causalityPattern19(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern20_1(sentence, jCas)) {
			String[] value = causalityPattern20_1(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern20_2(sentence, jCas)) {
			String[] value = causalityPattern20_2(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}

		return new String[] { cause, effect };

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
	 */
	public void detectCausality(Sentence sentence, JCas jCas, CEGModel model, LinkedList<CEGNode> nodes) {
		String cause = "";
		String effect = "";
		String[] causeEffectArray = detectCauseAndEffect(sentence, jCas);
		cause = causeEffectArray[0];
		effect = causeEffectArray[1];

		if (!cause.equals("")) {
			String[] effects = textSplitterAnd(effect, sentence, jCas);
			String[] causes = new String[] { cause };
			if (cause.contains(" and ") && cause.contains(" or ")) {// 'or' and 'and' in the sentence
				causes = textSplitterAnd(cause, sentence, jCas);
				String[] causesTemp = causes.clone();
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
							splittedVariabelAndConditionEffect[1], levelThreeX, levelThreeY, NodeType.AND);
					levelThreeY += 100;
					for (int j = 0; j < causes.length; j++) {
						String[] causesOr = textSplitterOr(causes[j], sentence, jCas);
						String causeNew = cuttingEnds(causes[j]);
						causesTemp = ArrayUtils.addAll(causesTemp, causesOr);
						if (causesOr.length == 1) {
							String causeNewOr = cuttingEnds(causesOr[0]);
							if (notReplacement(causeNewOr) != null) {
								String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
										sentence, notReplacement(causesOr[0]));
								CEGNode causeNodeOr = createNodeIfNotExist(nodes, model,
										splittedVariabelAndConditionCauseOr[0],
										splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
								createConnection(model, causeNodeOr, effectNode, true);
							} else {
								String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
										sentence, causesOr[0]);
								CEGNode causeNodeOr = createNodeIfNotExist(nodes, model,
										splittedVariabelAndConditionCauseOr[0],
										splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
								createConnection(model, causeNodeOr, effectNode, false);
							}
							levelOneY += 100;

						} else {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									causes[j]);
							CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
									splittedVariabelAndConditionCause[1], levelTwoX, levelTwoY, NodeType.OR);
							for (int k = 0; k < causesOr.length; k++) {
								String causeNewOr = cuttingEnds(causesOr[k]);
								if (notReplacement(causeNewOr) != null) {
									String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
											sentence, notReplacement(causesOr[k]));
									CEGNode causeNodeOr = createNodeIfNotExist(nodes, model,
											splittedVariabelAndConditionCauseOr[0],
											splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
									createConnection(model, causeNodeOr, causeNode, true);
								} else {
									String[] splittedVariabelAndConditionCauseOr = splitNodeInVariableAndCondition(jCas,
											sentence, causesOr[k]);
									CEGNode causeNodeOr = createNodeIfNotExist(nodes, model,
											splittedVariabelAndConditionCauseOr[0],
											splittedVariabelAndConditionCauseOr[1], levelOneX, levelOneY, NodeType.AND);
									createConnection(model, causeNodeOr, causeNode, false);
								}
								levelOneY += 100;
							}
							createConnection(model, causeNode, effectNode, false);
							levelTwoY += 100;
						}
					}
				}
			} else if (cause.contains(" and ")) {// 'and' in the sentence
				causes = textSplitterAnd(cause, sentence, jCas);
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
							splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.AND);
					levelTwoY += 100;
					for (int j = 0; j < causes.length; j++) {
						String causeNew = cuttingEnds(causes[j]);
						if (notReplacement(causeNew) != null) {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									notReplacement(causes[j]));
							CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
									splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
							createConnection(model, causeNode, effectNode, true);
						} else {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									causes[j]);
							CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
									splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
							createConnection(model, causeNode, effectNode, false);
						}
						levelOneY += 100;
					}
				}
			} else if (cause.contains(" or ")) { // 'or' in the sentence
				causes = textSplitterOr(cause, sentence, jCas);
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
							splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.OR);
					levelTwoY += 100;
					for (int j = 0; j < causes.length; j++) {
						String causeNew = cuttingEnds(causes[j]);
						if (notReplacement(causeNew) != null) {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									notReplacement(causes[j]));
							CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
									splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
							createConnection(model, causeNode, effectNode, true);
						} else {
							String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
									causes[j]);
							CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
									splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
							createConnection(model, causeNode, effectNode, false);
						}
						levelOneY += 100;
					}
				}
			} else {
				for (int i = 0; i < effects.length; i++) {
					String[] splittedVariabelAndConditionEffect = splitNodeInVariableAndCondition(jCas, sentence,
							effects[i]);
					CEGNode effectNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionEffect[0],
							splittedVariabelAndConditionEffect[1], levelTwoX, levelTwoY, NodeType.AND);
					levelTwoY += 100;
					String causeNew = cuttingEnds(cause);
					if (notReplacement(causeNew) != null) {
						String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
								notReplacement(cause));
						CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
								splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
						createConnection(model, causeNode, effectNode, true);
					} else {
						String[] splittedVariabelAndConditionCause = splitNodeInVariableAndCondition(jCas, sentence,
								cause);
						CEGNode causeNode = createNodeIfNotExist(nodes, model, splittedVariabelAndConditionCause[0],
								splittedVariabelAndConditionCause[1], levelOneX, levelOneY, NodeType.AND);
						createConnection(model, causeNode, effectNode, false);
					}

				}
				levelOneY += 100;
			}
		} else {
		}
	}

	/**
	 * Create a new node and add it to the CEGModel
	 * 
	 * @param model
	 * @param variable
	 * @param condition
	 * @param x
	 * @param y
	 * @param type
	 * @return the created node
	 */
	public CEGNode createNode(CEGModel model, String variable, String condition, int x, int y, NodeType type) {
		CEGNode node = RequirementsFactory.eINSTANCE.createCEGNode();
		node.setId(SpecmateEcoreUtil.getIdForChild(model, node.eClass()));
		node.setVariable(variable);
		node.setCondition(condition);
		node.setY(y);
		node.setX(x);
		node.setType(type);
		model.getContents().add(node);
		return node;
	}

	/**
	 * Create a new connection to the CEGModel
	 * 
	 * @param model
	 * @param nodeFrom
	 * @param nodeTo
	 * @param negate
	 * @return
	 */
	public CEGConnection createConnection(CEGModel model, CEGNode nodeFrom, CEGNode nodeTo, boolean negate) {
		CEGConnection con = RequirementsFactory.eINSTANCE.createCEGConnection();
		con.setId(SpecmateEcoreUtil.getIdForChild(model, con.eClass()));
		con.setSource(nodeFrom);
		con.setTarget(nodeTo);
		con.setNegate(negate);
		con.setName("New Connection " + dateFormat.format(new Date()));
		model.getContents().add(con);
		return con;
	}

	/**
	 * Create a new node it it does not exist in the list. Otherwise return the
	 * existing node. Nodes are only compared by variable, condition and type
	 * 
	 * @param list
	 * @param model
	 * @param variable
	 * @param condition
	 * @param x
	 * @param y
	 * @param type
	 * @return new or existing node
	 */
	public CEGNode createNodeIfNotExist(LinkedList<CEGNode> list, CEGModel model, String variable, String condition,
			int x, int y, NodeType type) {
		for (CEGNode cegNode : list) {
			if (cegNode.getVariable().equals(variable) && cegNode.getCondition().equals(condition)
					&& cegNode.getType().equals(type)) {
				return cegNode;
			}
		}
		CEGNode node = createNode(model, variable, condition, x, y, type);
		list.add(node);
		return node;
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
		if(begin == -1 ) {
			begin = sentence.getBegin();
			end = sentence.getEnd();
		}
		String[] back = new String[] { text, "" };
		List<NP> nounPhrases = JCasUtil.selectCovered(jCas, NP.class, sentence);

		for (NP np : nounPhrases) {
			if (np.getBegin() >= begin && np.getEnd() <= end) {
				if (notReplacement(np.getCoveredText()) != null) {
					if (text.contains(notReplacement(np.getCoveredText()))) {
						back[0] = cuttingEnds(notReplacement(np.getCoveredText()));
						back[1] = cuttingEnds(text.replace(notReplacement(np.getCoveredText()), ""));
						break;
					}
				} else {
					if (text.contains(np.getCoveredText())) {
						back[0] = cuttingEnds(np.getCoveredText());
						back[1] = cuttingEnds(text.replace(np.getCoveredText(), ""));
						break;
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
	 * Split a sentences at 'and' to get the single conditions/effects
	 * 
	 * @param text
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array of causes/effects
	 */
	public String[] textSplitterAnd(String text, Sentence sentence, JCas jCas) {
		String[] splittedText = text.split(" and ");
		return textSplitter(splittedText, sentence, jCas);
	}

	/**
	 * Split a sentences at 'or' to get the single conditions/effects
	 * 
	 * @param text
	 * @param sentence
	 * @param jCas
	 * @return array of causes/effects
	 */
	public String[] textSplitterOr(String text, Sentence sentence, JCas jCas) {
		String[] splittedText = text.split(" or ");
		return textSplitter(splittedText, sentence, jCas);
	}

	/**
	 * Add the missing parts(subjects, verbs, objects) to the splitted text.
	 * Example: 'The house is green or blue' is splitted into 'the house is green'
	 * and 'the house is blue'. the subject is added to the second sentence.
	 * 
	 * @param splittedText
	 *            splitted text at 'or' or 'and'
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public String[] textSplitter(String[] splittedText, Sentence sentence, JCas jCas) {
		String[] back = splittedText;
		if (back.length == 1) {
			return back;
		}

		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		List<NP> nounPhrases = JCasUtil.selectCovered(jCas, NP.class, sentence);
		for (int i = 0; i < back.length; i++) {
			for (Iterator<VP> iterator = verbPhrases.iterator(); iterator.hasNext();) {
				VP vp = (VP) iterator.next();
				if (back[i].startsWith(vp.getCoveredText().split(" if")[0])) {
					back[i] = getNPBeforePosition(vp.getBegin(), sentence, jCas).getCoveredText() + " " + back[i];
				}
			}
			for (int j = 0; j < nounPhrases.size(); j++) {
				NP np = (NP) nounPhrases.get(j);
				if (back[i].equals(np.getCoveredText())) {
					VP vp = getVPafterNP(np.getEnd(), sentence, jCas);
					if (vp != null) {
						if (vp.getCoveredText().contains(" if ")) {
							back[i] = back[i] + " "
									+ vp.getCoveredText().substring(0, vp.getCoveredText().indexOf(" if "));
						} else {
							back[i] = back[i] + " " + vp.getCoveredText();
						}
					} else {
						back[i] = back[i - 1].replace(nounPhrases.get(j - 1).getCoveredText(), np.getCoveredText());
					}
				}
			}
		}
		return back;
	}

	/**
	 * Return the next nounphrase before the given position in the sentence
	 * 
	 * @param pos
	 *            position
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return nounphrase
	 */
	public NP getNPBeforePosition(int pos, Sentence sentence, JCas jCas) {
		List<NP> nounPhrases = JCasUtil.selectCovered(jCas, NP.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (VP vp : verbPhrases) {
			if (pos >= vp.getBegin() && pos <= vp.getEnd()) {
				pos = vp.getBegin();
				break;
			}
		}
		NP back = null;
		int best = 0;
		for (NP np : nounPhrases) {
			if (np.getEnd() >= best && np.getEnd() <= pos) {
				best = np.getEnd();
				back = np;
			}
		}
		return back;
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
	public VP getVPafterNP(int pos, Sentence sentence, JCas jCas) {
		List<NP> nounPhrases = JCasUtil.selectCovered(jCas, NP.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (NP np : nounPhrases) {
			if (pos >= np.getBegin() && pos <= np.getEnd()) {
				pos = np.getEnd();
				break;
			}
		}

		VP back = null;
		int best = Integer.MAX_VALUE;
		for (VP vp : verbPhrases) {
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
	public VP getVPBeforePosition(int pos, Sentence sentence, JCas jCas) {
		List<NP> nounPhrases = JCasUtil.selectCovered(jCas, NP.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (NP np : nounPhrases) {
			if (pos >= np.getBegin() && pos <= np.getEnd()) {
				pos = np.getBegin();
				break;
			}
		}
		VP back = null;
		int best = 0;
		for (VP vp : verbPhrases) {
			if (vp.getEnd() >= best && vp.getEnd() <= pos) {
				best = vp.getEnd();
				back = vp;
			}
		}
		return back;
	}

	/**
	 * Detect if the sentence match pattern 1-12: If-sentences(starting with if) and effect introduced with then
	 * 
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public boolean matchPattern0Then(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		if (text.startsWith("If") && text.contains("then")) {
			return true;
		}
		return false;
	}


	/**
	 * Detect if the sentence match pattern 1-12: If-sentences with 'when' instead
	 * of 'if'(starting with when) and effect introduced with then
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern0WhenThen(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		if (text.startsWith("When") && text.contains("then")) {
			return true;
		}
		return false;
	}


	
	/**
	 * Detect if the sentence match pattern 1-12: If-sentences(starting with if)
	 * 
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public boolean matchPattern0(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		int positionComma = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (Token token : pos) {
			if (token.getPosValue().equals(",")) {
				positionComma = token.getBegin();
			}
		}
		if (text.startsWith("If")) {
			boolean start = false;
			boolean end = false;
			for (VP vp : verbPhrases) {
				if (vp.getEnd() <= positionComma) {
					start = true;
				}
				if (vp.getBegin() >= positionComma) {
					end = true;
				}
			}
			return start && end;
		}
		return false;
	}

	/**
	 * Detect if the sentence match pattern 1-12: If-sentences(if in the middle)
	 * 
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public boolean matchPattern0Rev(Sentence sentence, JCas jCas) {
		int positionIf = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (Token token : pos) {
			if (token.getCoveredText().equals("if")) {
				positionIf = token.getBegin();
			}
		}
		boolean start = false;
		boolean end = false;
		for (VP vp : verbPhrases) {
			if (vp.getBegin() <= positionIf) {
				start = true;
			}
			if (vp.getBegin() >= positionIf) {
				end = true;
			}
		}
		return start && end;
	}

	/**
	 * Detect if the sentence match pattern 1-12: If-sentences with 'when' instead
	 * of 'if'(starting with when)
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern0When(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		int positionComma = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (Token token : pos) {
			if (token.getPosValue().equals(",")) {
				positionComma = token.getBegin();
			}
		}
		if (text.startsWith("When")) {
			boolean start = false;
			boolean end = false;
			for (VP vp : verbPhrases) {
				if (vp.getEnd() <= positionComma) {
					start = true;
				}
				if (vp.getBegin() >= positionComma) {
					end = true;
				}
			}
			return start && end;
		}
		return false;
	}

	/**
	 * Detect if the sentence match pattern 1-12: If-sentences with 'when' instead
	 * of 'if'(when in the middle)
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern0RevWhen(Sentence sentence, JCas jCas) {
		int positionIf = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<VP> verbPhrases = JCasUtil.selectCovered(jCas, VP.class, sentence);
		for (Token token : pos) {
			if (token.getCoveredText().equals("when")) {
				positionIf = token.getBegin();
			}
		}
		boolean start = false;
		boolean end = false;
		for (VP vp : verbPhrases) {
			if (vp.getBegin() <= positionIf) {
				start = true;
			}
			if (vp.getBegin() >= positionIf) {
				end = true;
			}
		}
		return start && end;
	}

	/**
	 * Detect if the sentence match pattern 13: for this reason
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern13_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("for this reason")) {
			return true;
		}
		if (sentence.getCoveredText().contains("For this reason")) {
			Collection<Sentence> sen = JCasUtil.select(jCas, Sentence.class);
			Sentence[] senArray = new Sentence[sen.size()];
			sen.toArray();
			sen.toArray(senArray);
			for (int i = 0; i < senArray.length; i++) {
				if (senArray[i].getCoveredText().equals(sentence.getCoveredText()) && i > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 13: for this
	 * reason
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern13_1(Sentence sentence, JCas jCas) {
		String[] back = new String[2];
		if (sentence.getCoveredText().contains("for this reason")) {
			return sentence.getCoveredText().split("for this reason");
		}
		if (sentence.getCoveredText().contains("For this reason")) {
			Collection<Sentence> sen = JCasUtil.select(jCas, Sentence.class);
			Sentence[] senArray = new Sentence[sen.size()];
			sen.toArray();
			sen.toArray(senArray);
			for (int i = 0; i < senArray.length; i++) {
				if (senArray[i].getCoveredText().equals(sentence.getCoveredText()) && i > 0) {
					back[0] = senArray[i - 1].getCoveredText();
					back[1] = sentence.getCoveredText().replaceAll("For this reason, ", "");
					return back;
				}
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 13: as a result
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern13_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("as a result")) {
			return true;
		}
		if (sentence.getCoveredText().contains("As a result")) {
			Collection<Sentence> sen = JCasUtil.select(jCas, Sentence.class);
			Sentence[] senArray = new Sentence[sen.size()];
			sen.toArray();
			sen.toArray(senArray);
			for (int i = 0; i < senArray.length; i++) {
				if (senArray[i].getCoveredText().equals(sentence.getCoveredText()) && i > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 13: as a result
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern13_2(Sentence sentence, JCas jCas) {
		String[] back = new String[2];
		if (sentence.getCoveredText().contains("as a result")) {
			return sentence.getCoveredText().split("as a result, ");
		}
		if (sentence.getCoveredText().contains("As a result")) {
			Collection<Sentence> sen = JCasUtil.select(jCas, Sentence.class);
			Sentence[] senArray = new Sentence[sen.size()];
			sen.toArray();
			sen.toArray(senArray);
			for (int i = 0; i < senArray.length; i++) {
				if (senArray[i].getCoveredText().equals(sentence.getCoveredText()) && i > 0) {
					back[0] = senArray[i - 1].getCoveredText();
					back[1] = sentence.getCoveredText().replaceAll("As a result, ", "");
					return back;
				}
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 14: due to
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern14_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("due to")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Due to")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 14: due to
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern14_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("due to")) {
			String[] back = sentence.getCoveredText().split("due to ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Due to")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Due to ", "");
				return back;
			}

		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 14: because
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern14_1_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("because")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Because")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 14: because
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern14_1_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("because")) {
			String[] back = sentence.getCoveredText().split("because ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Because")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Because ", "");
				return back;
			}

		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 14: owing to
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern14_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("owing to")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Owing to")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 14: owing to
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern14_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("owing to")) {
			String[] back = sentence.getCoveredText().split("owing to ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Owing to")) {
			String[] back = sentence.getCoveredText().split(", ");
			back[0] = back[0].replaceAll("Owing to ", "");
			return back;
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 15: provided that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern15(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("provided that")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Provided that")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 15: provided that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern15(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("provided that")) {
			String[] back = sentence.getCoveredText().split("provided that ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Provided that")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Provided that ", "");
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 16: have something to do
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern16_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("have something to do")
				|| sentence.getCoveredText().contains("has something to do")) {
			return true;
		}
		if (sentence.getCoveredText().contains("had something to do")) {
			return true;

		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 16: have something
	 * to do
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern16_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("have something to do")) {
			String[] back = sentence.getCoveredText().split("have something to do ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().contains("has something to do")) {
			String[] back = sentence.getCoveredText().split("has something to do ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().contains("had something to do")) {
			String[] back = sentence.getCoveredText().split("had something to do ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;

		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 16: a lot to do
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern16_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("a lot to do")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 16: a lot to do
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern16_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("a lot to do")) {
			String[] back = sentence.getCoveredText().split("a lot to do ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 17: so that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern17_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("so that")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 17: so that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern17_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("so that")) {
			return sentence.getCoveredText().split("so that ");
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 17: in order that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern17_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("in order that")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("In order that")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 17: in order that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern17_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("in order that")) {
			return sentence.getCoveredText().split("in order that ");
		}
		if (sentence.getCoveredText().startsWith("In order that")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("In order that ", "");
				String tmp = back[0];
				back[0] = back[1];
				back[1] = tmp;
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 18: although
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern18_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("although")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Although")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 18: although
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern18_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("although")) {
			String[] back = sentence.getCoveredText().split("although ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Although")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Although ", "");
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 18: even though
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern18_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("even though")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Even though")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 18: even though
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern18_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("even though")) {
			String[] back = sentence.getCoveredText().split("even though ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Even though")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Even though ", "");
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 19: in the case that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern19(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("in the case that")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("In the case that")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 19: in the case
	 * that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern19(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("in the case that")) {
			String[] back = sentence.getCoveredText().split("in the case that ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("In the case that")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("In the case that ", "");
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 20: on condition that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern20_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("on condition that")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("On condition that")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 20: on condition
	 * that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern20_1(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("on condition that")) {
			String[] back = sentence.getCoveredText().split("on condition that ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("On condition that")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("On condition that ", "");
				return back;
			}
		}
		return null;
	}

	/**
	 * Detect if the sentence match pattern 20: supposing that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern20_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("supposing that")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Supposing that")) {
			if (sentence.getCoveredText().contains(",")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 20: supposing that
	 * 
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern20_2(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("supposing that")) {
			String[] back = sentence.getCoveredText().split("supposing that ");
			String tmp = back[0];
			back[0] = back[1];
			back[1] = tmp;
			return back;
		}
		if (sentence.getCoveredText().startsWith("Supposing that")) {
			if (sentence.getCoveredText().contains(",")) {
				String[] back = sentence.getCoveredText().split(", ");
				back[0] = back[0].replaceAll("Supposing that ", "");
				return back;
			}
		}
		return null;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	void setNlptagging(NLPTagger tagger) {
		this.tagger = tagger;
	}

}
