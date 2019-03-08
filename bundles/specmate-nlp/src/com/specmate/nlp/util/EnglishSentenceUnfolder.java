package com.specmate.nlp.util;

import java.util.Collection;
import java.util.Optional;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class EnglishSentenceUnfolder extends SentenceUnfolder {

	@Override
	protected Optional<Dependency> findSubjectForVerbPhrase(Collection<Dependency> dependencies, Chunk vp) {
		return NLPUtil.findDependency(dependencies, vp, "nsubj", true);
	}
}
