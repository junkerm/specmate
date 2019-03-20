package com.specmate.modelgeneration;

import java.util.List;
import java.util.Optional;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class GermanPatternMatcher extends PatternMatcher {
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

		String cause = "";
		String effect = "";

		if (matchPattern2_1(sentence, jCas)) {
			int posComma = sentenceText.indexOf(",");
			for (Token constituent : JCasUtil.selectCovered(jCas, Token.class, sentence)) {
				if (constituent.getPosValue().equals(",")
				// &&
				// constituent.getParent().getType().getShortName().equals("S")
				// &&
				// constituent.getParent().getCoveredText().equals(sentence.getCoveredText())
				) {
					posComma = constituent.getBegin() - sentence.getBegin();
				}
			}
			cause = sentenceText.substring(5, posComma);
			effect = sentenceText.substring(posComma + 2, sentenceText.length() - 1);
		}

		return new String[] { cause, effect };

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
		if (text.startsWith("Wenn")) {
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

}
