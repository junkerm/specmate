package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import com.specmate.nlp.util.NLPUtil.ConstituentType;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class EnglishSentenceUnfolder extends SentenceUnfolderBase {

	/** The dependency types for a cunjunction depenendenc (and, or) */
	private static final String DEPENDENCY_TYPE_CONJUNCTION = "conj";
	private static final String DEPENDENCY_TYPE_CC = "cc";

	/** The dependency type for a subject dependency */
	private static final String DEPENDENCY_TYPE_SUBJECT = "nsubj";

	@Override
	protected Optional<Dependency> findSubjectDependency(JCas jCas, Annotation vp, boolean isGovernor) {
		return NLPUtil.findDependency(jCas, vp, DEPENDENCY_TYPE_SUBJECT, isGovernor);
	}

	@Override
	protected Optional<Pair<Annotation, EWordOrder>> findImplicitVerbSubjectByConjunction(JCas jCas, Chunk vp) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		Optional<Dependency> conj = NLPUtil.findDependency(dependencies, vp, DEPENDENCY_TYPE_CONJUNCTION, false);
		if (conj.isPresent()) {
			Token governor = conj.get().getGovernor();
			Optional<Dependency> subj = NLPUtil.findDependency(dependencies, governor, DEPENDENCY_TYPE_SUBJECT, true);
			if (subj.isPresent()) {
				Token subjToken = subj.get().getDependent();
				List<Chunk> chunk = JCasUtil.selectCovering(jCas, Chunk.class, subjToken);
				Chunk np = chunk.get(0);
				if (np.getChunkValue().equals(ConstituentType.NP.getName())) {
					return Optional.of(Pair.of(np, EWordOrder.SOV));
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
			conjDep = NLPUtil.findDependency(dependencies, subj, DEPENDENCY_TYPE_CONJUNCTION, true);
			ccDep = NLPUtil.findDependency(dependencies, subj, DEPENDENCY_TYPE_CC, true);
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

	@Override
	protected int determineSubjectInsertionPoint(JCas jcas, Chunk vp, EWordOrder order) {
		return vp.getBegin();
	}

	@Override
	protected Optional<Dependency> findVerbForNounPhrase(JCas jCas, Chunk np) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	protected Optional<Triple<Annotation, EWordOrder, ENounRole>> findImplicitVerbByConjunction(JCas jCas, Chunk np) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	protected int determineVerbInsertionPoint(JCas jcas, Chunk np, Annotation verb, EWordOrder order, ENounRole role) {
		return np.getEnd() + 1;
	}

}
