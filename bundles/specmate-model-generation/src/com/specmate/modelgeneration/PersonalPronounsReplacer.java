package com.specmate.modelgeneration;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.exception.SpecmateException;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * This class replaces every personal pronoun with the corresponding object by
 * using coreferences of the {@link INLPService}
 *
 *
 * @author Andreas Wehrle
 *
 */
public class PersonalPronounsReplacer {
	INLPService tagger;
	String sentences = "";

	public PersonalPronounsReplacer(INLPService tagger) {
		super();
		this.tagger = tagger;
	}

	/**
	 * Method replace every personal pronoun with the corresponding object using
	 * coreferences of the {@link INLPService}
	 *
	 * @param text
	 *            Text with personal pronouns
	 * @return Text with replaced personal pronouns
	 */
	public String replacePronouns(String text) {
		JCas jcas;
		try {
			jcas = this.tagger.processText(text, ELanguage.EN);
		} catch (SpecmateException e) {
			return null;
		}

		for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
			boolean changed = false;
			String satz = sentence.getCoveredText();
			for (CoreferenceLink link : JCasUtil.selectCovered(jcas, CoreferenceLink.class, sentence)) {
				if ((link.getReferenceType().equals("NOMINAL") || link.getReferenceType().equals("PROPER"))
						&& link.getNext() != null && link.getNext().getReferenceType().equals("PRONOMINAL")) {
					try {
						satz = satz.substring(0, link.getNext().getBegin() - sentence.getBegin() - 1)
								+ satz.substring(link.getNext().getBegin() - sentence.getBegin() - 1).replace(
										" " + link.getNext().getCoveredText(),
										" " + link.getCoveredText().toLowerCase());
						changed = true;
					} catch (StringIndexOutOfBoundsException e) {
						if (!this.sentences.contains(satz)) {
							this.sentences += satz + "\n";
						}
					}
					if (!this.sentences.contains(satz)) {
						this.sentences += satz + " ";
					}
				}
			}
			if (!changed) {
				this.sentences += satz + " ";
			}
		}
		return this.sentences;
	}

	@Reference
	void setNlptagging(INLPService tagger) {
		this.tagger = tagger;
	}

}
