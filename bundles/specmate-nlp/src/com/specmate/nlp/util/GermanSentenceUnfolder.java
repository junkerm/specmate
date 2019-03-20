package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class GermanSentenceUnfolder extends SentenceUnfolder {

	@Override
	protected Optional<Dependency> findSubjectForVerbPhrase(Collection<Dependency> dependencies, Chunk vp) {
		return NLPUtil.findDependency(dependencies, vp, "SUBJ", true);
	}

	@Override
	protected Optional<Chunk> findMissingVerbSubjectByConjunction(JCas jCas, Chunk vp) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		Optional<Dependency> conj = NLPUtil.findDependency(dependencies, vp, "CJ", false);
		if (conj.isPresent()) {
			Token governor = conj.get().getGovernor();
			Optional<Dependency> kon = NLPUtil.findDependency(dependencies, governor, "KON", false);
			if (kon.isPresent()) {
				governor = kon.get().getGovernor();

				Optional<Dependency> subj = NLPUtil.findDependency(dependencies, governor, "SUBJ", true);
				if (subj.isPresent()) {
					Token subjToken = subj.get().getDependent();
					List<Chunk> chunk = JCasUtil.selectCovering(jCas, Chunk.class, subjToken);
					Chunk np = chunk.get(0);
					if (np.getChunkValue().equals("NP")) {
						return Optional.of(np);
					}
				}
			}
		}
		return Optional.empty();
	}

	@Override
	protected List<Pair<Annotation, Annotation>> completeSubjectsByConjunction(JCas jCas, Annotation subj) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		List<Pair<Annotation, Annotation>> result = new ArrayList<>();
		result.add(Pair.of(null, subj));
		Optional<Dependency> cjDep = null;
		Optional<Dependency> konDep = null;
		do {
			cjDep = NLPUtil.findDependency(dependencies, subj, "KON", true);
			if (cjDep.isPresent()) {
				Token govCjToken = cjDep.get().getDependent();
				konDep = NLPUtil.findDependency(dependencies, govCjToken, "CJ", true);
				if (konDep.isPresent()) {
					Token govKonToken = konDep.get().getDependent();
					Annotation conSubj = NLPUtil.selectIfCovering(Chunk.class, govKonToken);
					Annotation cc = NLPUtil.selectIfCovering(Chunk.class, govCjToken);
					result.add(Pair.of(cc, conSubj));
					subj = conSubj;
				}
			}
		} while (cjDep.isPresent());
		return result;
	}

}
