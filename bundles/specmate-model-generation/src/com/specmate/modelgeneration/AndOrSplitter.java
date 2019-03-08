package com.specmate.modelgeneration;

import java.util.Iterator;
import java.util.List;

import org.apache.uima.jcas.JCas;

import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;

/**
 * Class splits the sentences at 'and' and/or 'or'.
 *
 * @author Andreas Wehrle
 *
 */
public class AndOrSplitter {

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
	 * Split a sentences at 'or'or 'and' to get the single conditions/effects
	 *
	 * @param text
	 * @param sentence
	 * @param jCas
	 * @return array of causes/effects
	 */
	public String[] textSplitterAndOr(String text, Sentence sentence, JCas jCas) {
		String[] splittedText = text.split("( and )|( or )");
		return textSplitter(splittedText, sentence, jCas);
	}

	/**
	 * Add the missing parts(subjects, verbs, objects) to the splitted text.
	 * Example: 'The house is green or blue' is splitted into 'the house is
	 * green' and 'the house is blue'. the subject is added to the second
	 * sentence.
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

		List<Chunk> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		List<Chunk> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);
		for (int i = 0; i < back.length; i++) {
			for (Iterator<Chunk> iterator = verbPhrases.iterator(); iterator.hasNext();) {
				Chunk vp = iterator.next();
				if (back[i].startsWith(vp.getCoveredText().split(" if")[0])) {
					back[i] = getNPBeforePosition(vp.getBegin(), sentence, jCas).getCoveredText() + " " + back[i];
				}
			}
			for (int j = 0; j < nounPhrases.size(); j++) {
				Chunk np = nounPhrases.get(j);
				if (np.getCoveredText().contains(back[i])) {
					Chunk vp = getVPafterNP(np.getEnd(), sentence, jCas);
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
	public Chunk getNPBeforePosition(int pos, Sentence sentence, JCas jCas) {
		List<Chunk> nounPhrases = NLPUtil.getNounPhrases(jCas, sentence);
		List<Chunk> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		for (Chunk vp : verbPhrases) {
			if (pos >= vp.getBegin() && pos <= vp.getEnd()) {
				pos = vp.getBegin();
				break;
			}
		}
		Chunk back = null;
		int best = 0;
		for (Chunk np : nounPhrases) {
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

}
