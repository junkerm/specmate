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

public class EnglishSentenceUnfolder extends SentenceUnfolder {

	@Override
	protected Optional<Dependency> findSubjectForVerbPhrase(Collection<Dependency> dependencies, Chunk vp) {
		return NLPUtil.findDependency(dependencies, vp, "nsubj", true);
	}

	@Override
	protected Optional<Chunk> findMissingVerbSubjectByConjunction(JCas jCas, Chunk vp) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		Optional<Dependency> conj = NLPUtil.findDependency(dependencies, vp, "conj", false);
		if (conj.isPresent()) {
			Token governor = conj.get().getGovernor();
			Optional<Dependency> subj = NLPUtil.findDependency(dependencies, governor, "nsubj", true);
			if (subj.isPresent()) {
				Token subjToken = subj.get().getDependent();
				List<Chunk> chunk = JCasUtil.selectCovering(jCas, Chunk.class, subjToken);
				Chunk np = chunk.get(0);
				if (np.getChunkValue().equals("NP")) {
					return Optional.of(np);
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
		Optional<Dependency> conjDep;
		Optional<Dependency> ccDep;
		do {
			conjDep = NLPUtil.findDependency(dependencies, subj, "conj", true);
			ccDep = NLPUtil.findDependency(dependencies, subj, "cc", true);
			if (conjDep.isPresent() && ccDep.isPresent()) {
				Token govConjToken = conjDep.get().getDependent();
				Token govCcToken = ccDep.get().getDependent();
				Annotation conSubj = NLPUtil.selectIfCovering(Chunk.class, govConjToken);
				Annotation cc = NLPUtil.selectIfCovering(Chunk.class, govCcToken);
				result.add(Pair.of(cc, conSubj));
				subj = conSubj;
			}
		} while (conjDep.isPresent());
		return result;
	}
}
