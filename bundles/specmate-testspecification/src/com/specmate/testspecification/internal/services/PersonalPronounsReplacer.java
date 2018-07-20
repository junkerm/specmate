package com.specmate.testspecification.internal.services;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * This class replaces every personal pronoun with the corresponding subject by
 * using coreferences of the {@link NLPTagger}
 * 
 * 
 * @author Andreas Wehrle
 *
 */
public class PersonalPronounsReplacer {
	NLPTagger tagger;
	String sentences = "";

	public PersonalPronounsReplacer(NLPTagger tagger) {
		super();
		this.tagger = tagger;
	}

	/**
	 * Method replace every personal pronoun with the corresponding subject using
	 * coreferences of the {@link NLPTagger}
	 * 
	 * @param text
	 *            Text with personal pronouns
	 * @return Text with replaced personal pronouns
	 */
	public String replacePronouns(String text) {
		JCas jcas;
		try {
			jcas = tagger.tagText(text);
		} catch (SpecmateException e) {
			return null;
		}
		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
			boolean changed = false;
			String satz = sentence.getCoveredText();
			for (CoreferenceLink link : JCasUtil.selectCovered(jcas, CoreferenceLink.class, sentence)) {
				if ((link.getReferenceType().equals("NOMINAL") || link.getReferenceType().equals("PROPER"))
						&& link.getNext() != null && link.getNext().getReferenceType().equals("PRONOMINAL")) {

					satz = satz.substring(0, link.getNext().getBegin() - sentence.getBegin() - 1)
							+ satz.substring(link.getNext().getBegin() - sentence.getBegin() - 1).replace(
									" " + link.getNext().getCoveredText(), " " + link.getCoveredText().toLowerCase());
					changed = true;
					if (!sentences.contains(satz)) {
						sentences += satz + " ";
					}
				}
			}
			if (!changed) {
				sentences += satz + " ";
			}
		}
		return sentences;
	}

	@Reference
	void setNlptagging(NLPTagger tagger) {
		this.tagger = tagger;
	}

}
