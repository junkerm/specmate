package com.specmate.modelgeneration;

import java.util.Iterator;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.VP;

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

}
