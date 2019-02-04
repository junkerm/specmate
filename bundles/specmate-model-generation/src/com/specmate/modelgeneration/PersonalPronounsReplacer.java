package com.specmate.modelgeneration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.exception.SpecmateException;
import com.specmate.nlp.api.INLPService;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.PRP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

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
	private List<String> pronouns;

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
			jcas = tagger.processText(text);
		} catch (SpecmateException e) {
			return null;
		}

		for (PRP prp : JCasUtil.select(jcas, PRP.class)) {
			pronouns = Arrays.asList("he", "she", "it");
			if (pronouns.contains(prp.getCoveredText())) {
				Constituent subj = findPreceedingSubject(prp);
				if (subj != null) {
					System.out.println(subj);
				}
			}
		}

		// for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
		// boolean changed = false;
		// String satz = sentence.getCoveredText();
		// for (CoreferenceLink link : JCasUtil.selectCovered(jcas,
		// CoreferenceLink.class, sentence)) {
		// if ((link.getReferenceType().equals("NOMINAL") ||
		// link.getReferenceType().equals("PROPER"))
		// && link.getNext() != null &&
		// link.getNext().getReferenceType().equals("PRONOMINAL")) {
		// try {
		// satz = satz.substring(0, link.getNext().getBegin() - sentence.getBegin() - 1)
		// + satz.substring(link.getNext().getBegin() - sentence.getBegin() -
		// 1).replace(
		// " " + link.getNext().getCoveredText(),
		// " " + link.getCoveredText().toLowerCase());
		// changed = true;
		// } catch (StringIndexOutOfBoundsException e) {
		// if (!sentences.contains(satz)) {
		// sentences += satz + "\n";
		// }
		// }
		// if (!sentences.contains(satz)) {
		// sentences += satz + " ";
		// }
		// }
		// }
		// if (!changed) {
		// sentences += satz + " ";
		// }
		// }
		return sentences;
	}

	private Constituent findPreceedingSubject(PRP prp) {
		Dependency dep = null;
		Annotation anno = prp;
		do {
			List<Dependency> preceedingDependencies = JCasUtil.selectPreceding(Dependency.class, anno, 1);
			if (preceedingDependencies.size() > 0) {
				dep = preceedingDependencies.get(0);
				anno = dep;
				if (dep.getDependencyType().contentEquals("nsubj")) {
					if (!dep.getDependent().getPos().getPosValue().equals("PRP")) {
						break;
					}
				}
			} else {
				anno = null;
			}
			dep = null;
		} while (anno != null);

		if (dep != null && dep.getDependencyType().equals("nsubj")) {
			Token subj = dep.getDependent();
			List<Constituent> consituents = JCasUtil.selectCovering(Constituent.class, subj);
			Optional<Constituent> np = consituents.stream().filter(c -> c.getConstituentType().equals("NP"))
					.findFirst();
			if (np.isPresent()) {
				return np.get();
			} else {
				return null;
			}
		}
		return null;
	}

	@Reference
	void setNlptagging(INLPService tagger) {
		this.tagger = tagger;
	}

}
