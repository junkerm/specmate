package com.specmate.modelgeneration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.matcher.AndMatcher;
import com.specmate.nlp.matcher.AnyMatcher;
import com.specmate.nlp.matcher.ChildrenSequenceMatcher;
import com.specmate.nlp.matcher.ConstituentTypeMatcher;
import com.specmate.nlp.matcher.CoveredTextMatcher;
import com.specmate.nlp.matcher.ExactlyOneConsumer;
import com.specmate.nlp.matcher.IConstituentTreeMatcher;
import com.specmate.nlp.matcher.MatchResult;
import com.specmate.nlp.matcher.SequenceMatcher;
import com.specmate.nlp.matcher.ZeroOrMoreConsumer;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

/**
 * Matches the patterns, which are used to detect causality, to the sentences
 *
 * @author Andreas Wehrle
 *
 */
public class EnglishPatternMatcher implements ICauseEffectPatternMatcher {

	/**
	 * Method splits the sentences into the cause and the effect if it matches one
	 * pattern
	 *
	 * @param sentence
	 *            sentence to split
	 * @param jCas
	 *            NLPTagged text
	 * @return array with two elements. First element: cause, second element:effect
	 * @throws SpecmateInternalException
	 */
	@Override
	public String[] detectCauseAndEffect(Sentence sentence, JCas jCas) throws SpecmateInternalException {
		String sentenceText = sentence.getCoveredText();
		Optional<Constituent> optSentenceConstituent = NLPUtil.getSentenceConstituent(jCas, sentence);
		if (!optSentenceConstituent.isPresent()) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM,
					"Could not find sentence constituent for sentence: " + sentenceText);
		}
		Constituent sentenceConstituent = optSentenceConstituent.get();

		String cause = "";
		String effect = "";

		if (matchPattern2_1(sentence, jCas)) {
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
		if (matchPattern2_2(sentence, jCas)) {
			int posIf = sentenceText.indexOf("when");

			cause = sentenceText.substring(posIf + 5, sentenceText.length() - 1);
			effect = sentenceText.substring(0, posIf - 1);
		}

		IConstituentTreeMatcher pattern1_1 = buildMatcher1_1(jCas);
		MatchResult matchResult = pattern1_1.match(sentenceConstituent);
		if (matchResult.isMatch()) {
			cause = matchResult.getMatchGroupAsText("cause");
			effect = matchResult.getMatchGroupAsText("effect");
		}
		// if (matchPattern1_1(sentence, jCas)) {
		// int posComma = sentenceText.indexOf(",");
		// for (Token constituent : JCasUtil.selectCovered(jCas, Token.class,
		// sentence)) {
		// if (constituent.getPosValue().equals(",")
		// && constituent.getParent().getType().getShortName().equals("S")
		// &&
		// constituent.getParent().getCoveredText().equals(sentence.getCoveredText()))
		// {
		// posComma = constituent.getBegin() - sentence.getBegin();
		// }
		// }
		// cause = sentenceText.substring(3, posComma);
		// effect = sentenceText.substring(posComma + 2, sentenceText.length() -
		// 1);
		// }
		if (matchPattern1_2(sentence, jCas)) {
			int posIf = sentenceText.indexOf("if");

			cause = sentenceText.substring(posIf + 3, sentenceText.length() - 1);
			effect = sentenceText.substring(0, posIf - 1);
		}

		if (matchPattern1_3(sentence, jCas)) {
			int posThen = sentenceText.indexOf("then");
			cause = sentenceText.substring(3, posThen);
			effect = sentenceText.substring(posThen + 5, sentenceText.length() - 1);
		}

		if (matchPattern2_3(sentence, jCas)) {
			int posThen = sentenceText.indexOf("then");
			cause = sentenceText.substring(5, posThen);
			effect = sentenceText.substring(posThen + 5, sentenceText.length() - 1);
		}

		if (matchPattern4(sentence, jCas)) {
			String[] value = causalityPattern4(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern5(sentence, jCas)) {
			String[] value = causalityPattern5(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern6(sentence, jCas)) {
			String[] value = causalityPattern6(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern3(sentence, jCas)) {
			String[] value = causalityPattern3(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern7(sentence, jCas)) {
			String[] value = causalityPattern7(sentence, jCas);
			Arrays.toString(value);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern8(sentence, jCas)) {
			String[] value = causalityPattern8(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern9(sentence, jCas)) {
			String[] value = causalityPattern9(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern10(sentence, jCas)) {
			String[] value = causalityPattern10(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern11(sentence, jCas)) {
			String[] value = causalityPattern11(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern12(sentence, jCas)) {
			String[] value = causalityPattern12(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern13(sentence, jCas)) {
			String[] value = causalityPattern13(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern14(sentence, jCas)) {
			String[] value = causalityPattern14(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern15(sentence, jCas)) {
			String[] value = causalityPattern15(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern16(sentence, jCas)) {
			String[] value = causalityPattern16(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}
		if (matchPattern17(sentence, jCas)) {
			String[] value = causalityPattern17(sentence, jCas);
			cause = value[0];
			effect = value[1];
		}

		return new String[] { cause, effect };

	}

	/**
	 * Detect if the sentence matches pattern 1.1: If-sentences(starting with if)
	 *
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public IConstituentTreeMatcher buildMatcher1_1(JCas jCas) {
		return new ChildrenSequenceMatcher(new SequenceMatcher(Arrays.asList(
				new ExactlyOneConsumer(jCas,
						new AndMatcher(new ConstituentTypeMatcher("SBAR"),
								new CoveredTextMatcher("If\\s*(.*)", "cause"))),
				new ZeroOrMoreConsumer(jCas, new CoveredTextMatcher(",")),
				new ZeroOrMoreConsumer(jCas, new ConstituentTypeMatcher("ADVP")),
				new ZeroOrMoreConsumer(jCas, new AnyMatcher(), "effect"))));
	}
	// public boolean matchPattern1_1(Sentence sentence, JCas jCas) {
	// String text = sentence.getCoveredText();
	// int positionComma = -1;
	// List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
	// List<Constituent> verbPhrases = JCasUtil.selectCovered(jCas,
	// Constituent.class, sentence).stream()
	// .filter(c ->
	// c.getConstituentType().contentEquals("VP")).collect(Collectors.toList());
	// ;
	// for (Token token : pos) {
	// if (token.getPosValue().equals(",")) {
	// positionComma = token.getBegin();
	// }
	// }
	// if (text.startsWith("If")) {
	// boolean start = false;
	// boolean end = false;
	// for (Constituent vp : verbPhrases) {
	// if (vp.getEnd() <= positionComma) {
	// start = true;
	// }
	// if (vp.getBegin() >= positionComma) {
	// end = true;
	// }
	// }
	// return start && end;
	// }
	// return false;
	// }

	/**
	 * Detect if the sentence matches pattern 1.2: If-sentences(if in the middle)
	 *
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public boolean matchPattern1_2(Sentence sentence, JCas jCas) {
		int positionIf = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<Constituent> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Token token : pos) {
			if (token.getCoveredText().equals("if")) {
				positionIf = token.getBegin();
			}
		}
		boolean start = false;
		boolean end = false;
		for (Constituent vp : verbPhrases) {
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
	 * Detect if the sentence matches pattern 1.3: If-sentences(starting with if)
	 * and effect introduced with then
	 *
	 * @param sentence
	 * @param jCas
	 * @return
	 */
	public boolean matchPattern1_3(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		if (text.startsWith("If") && text.contains("then")) {
			return true;
		}
		return false;
	}

	/**
	 * Detect if the sentence matches pattern 2.1: If-sentences with 'when' instead
	 * of 'if'(starting with when)
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern2_1(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		int positionComma = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<Constituent> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Token token : pos) {
			if (token.getPosValue().equals(",")) {
				positionComma = token.getBegin();
			}
		}
		if (text.startsWith("When")) {
			boolean start = false;
			boolean end = false;
			for (Constituent vp : verbPhrases) {
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
	 * Detect if the sentence matches pattern 2.2: If-sentences with 'when' instead
	 * of 'if'(when in the middle)
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern2_2(Sentence sentence, JCas jCas) {
		int positionIf = -1;
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		List<Constituent> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Token token : pos) {
			if (token.getCoveredText().equals("when")) {
				positionIf = token.getBegin();
			}
		}
		boolean start = false;
		boolean end = false;
		for (Constituent vp : verbPhrases) {
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
	 * Detect if the sentence matches pattern 2.3: If-sentences with 'when' instead
	 * of 'if'(starting with when) and effect introduced with then
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern2_3(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();
		if (text.startsWith("When") && text.contains("then")) {
			return true;
		}
		return false;
	}

	/**
	 * Detect if the sentence matches pattern 3: because
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern3(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 3: because
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern3(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 4: for this reason
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern4(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 4: for this reason
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern4(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 5: as a result
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern5(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 5: as a result
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern5(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 6: due to
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern6(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 6: due to
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern6(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 7: owing to
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern7(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("owing to")) {
			return true;
		}
		if (sentence.getCoveredText().startsWith("Owing to")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 7: owing to
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern7(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 8: provided that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern8(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 8: provided that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern8(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 9: have something to do
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern9(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 9: have something
	 * to do
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern9(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 10: a lot to do
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern10(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("a lot to do")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 10: a lot to do
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern10(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 11: so that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern11(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("so that")) {
			return true;
		}
		return false;
	}

	/**
	 * Return the cause and effect of a sentence matching pattern 11: so that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern11(Sentence sentence, JCas jCas) {
		if (sentence.getCoveredText().contains("so that")) {
			return sentence.getCoveredText().split("so that ");
		}
		return null;
	}

	/**
	 * Detect if the sentence matches pattern 12: in order that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern12(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 12: in order that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern12(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 13: although
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern13(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 13: although
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern13(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 14: even though
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern14(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 14: even though
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern14(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 15: in the case that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern15(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 15: in the case
	 * that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern15(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 16: on condition that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern16(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 16: on condition
	 * that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern16(Sentence sentence, JCas jCas) {
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
	 * Detect if the sentence matches pattern 17: supposing that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public boolean matchPattern17(Sentence sentence, JCas jCas) {
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
	 * Return the cause and effect of a sentence matching pattern 17: supposing that
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return array containing cause and effect: First element: cause, second
	 *         element effect
	 */
	public String[] causalityPattern17(Sentence sentence, JCas jCas) {
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
}
