package com.specmate.modelgeneration;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class GermanPatternMatcher implements ICauseEffectPatternMatcher {

	private static final String CONSTITUENT_TYPE_SENTENCE = "S";
	private static final String POS_COMMA = "$,";
	private static final String WENN = "Wenn";

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

		Optional<Pair<String, String>> match = matchPattern_When(sentence, jCas);
		if (match.isPresent()) {
			cause = match.get().getLeft();
			effect = match.get().getRight();
		}

		match = matchPattern_VVFIN(sentence, jCas);
		if (match.isPresent()) {
			cause = match.get().getLeft();
			effect = match.get().getRight();
		}

		return new String[] { cause, effect };

	}

	/**
	 * Detect if the sentence matches pattern "Wenn [cause], [effect]"
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public Optional<Pair<String, String>> matchPattern_When(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();

		if (!text.startsWith(WENN)) {
			return Optional.empty();
		}
		int positionComma = getCommaPosition(jCas, sentence);

		if (!hasVerbPhraseBeforeAndAfterPosition(jCas, sentence, positionComma)) {
			return Optional.empty();
		}

		String cause = text.substring("When".length(), positionComma);
		String effect = text.substring(positionComma + 2, text.length() - 1);
		return Optional.of(Pair.of(cause, effect));
	}

	/**
	 * Detect if the sentence matches pattern "[POS=VVFIN] [cause], [effect]"
	 *
	 * @param sentence
	 * @param jCas
	 *            NLP tagged text
	 * @return
	 */
	public Optional<Pair<String, String>> matchPattern_VVFIN(Sentence sentence, JCas jCas) {
		String text = sentence.getCoveredText();

		List<Token> tokens = JCasUtil.selectCovered(jCas, Token.class, sentence);
		if (tokens.size() == 0) {
			return Optional.empty();
		}
		Token firstToken = tokens.get(0);
		if (!firstToken.getPosValue().contentEquals("VVFIN")) {
			return Optional.empty();
		}
		int positionComma = getCommaPosition(jCas, sentence);

		if (!hasVerbPhraseBeforeAndAfterPosition(jCas, sentence, positionComma)) {
			return Optional.empty();
		}

		String cause = text.substring(0, positionComma);
		String effect = text.substring(positionComma + 2, text.length() - 1);
		return Optional.of(Pair.of(cause, effect));
	}

	private int getCommaPosition(JCas jCas, Sentence sentence) {
		List<Token> pos = JCasUtil.selectCovered(jCas, Token.class, sentence);
		int positionComma = -1;
		for (Token token : pos) {
			if (token.getPosValue().equals(POS_COMMA)) {
				positionComma = token.getBegin();
			}
		}
		return positionComma;
	}

	private boolean hasVerbPhraseBeforeAndAfterPosition(JCas jCas, Sentence sentence, int positionComma) {
		boolean start = true;
		boolean end = true;
		List<Chunk> verbPhrases = NLPUtil.getVerbPhraseChunks(jCas, sentence);
		for (Chunk vp : verbPhrases) {
			if (vp.getEnd() <= positionComma) {
				start = true;
			}
			if (vp.getBegin() >= positionComma) {
				end = true;
			}
		}
		return start && end;
	}
}
