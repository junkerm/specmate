package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public abstract class SentenceUnfolder {

	public String insertMissingSubjects(JCas jCas, Sentence sentence) {
		List<Pair<Integer, String>> insertions = new ArrayList<Pair<Integer, String>>();
		List<Chunk> vpws = findVerbalPhraseWithoutSubject(jCas, sentence);
		for (Chunk vp : vpws) {
			Optional<Chunk> subj = findMissingVerbSubjectByConjunction(jCas, vp);
			if (subj.isPresent()) {
				List<Pair<Annotation, Annotation>> allSubj = completeSubjectsByConjunction(jCas, subj.get());
				for (Pair<Annotation, Annotation> toInsert : allSubj) {
					String conj = toInsert.getLeft() != null ? toInsert.getLeft().getCoveredText() + " " : "";
					insertions.add(Pair.of(vp.getBegin(), conj + toInsert.getRight().getCoveredText()));
				}
			}
		}
		return insert(sentence.getCoveredText(), insertions);
	}

	private List<Chunk> findVerbalPhraseWithoutSubject(JCas jCas, Sentence sentence) {
		List<Chunk> result = new ArrayList<>();
		List<Chunk> verbPhrases = NLPUtil.getVerbPhrases(jCas, sentence);
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		for (Chunk vp : verbPhrases) {
			Optional<Dependency> subject = findSubjectForVerbPhrase(dependencies, vp);
			if (!subject.isPresent()) {
				result.add(vp);
			}
		}
		return result;
	}

	private static String insert(String text, List<Pair<Integer, String>> insertions) {
		StringBuffer buffer = new StringBuffer(text);
		int indexCorrection = 0;
		insertions.sort((p1, p2) -> p1.getLeft().compareTo(p2.getLeft()));
		for (Pair<Integer, String> insertion : insertions) {
			int index = insertion.getLeft() + indexCorrection;
			String toInsert = insertion.getRight() + " ";
			buffer.insert(index, toInsert);
			indexCorrection += toInsert.length();
		}
		return buffer.toString();
	}

	protected abstract List<Pair<Annotation, Annotation>> completeSubjectsByConjunction(JCas jCas, Annotation subj);

	protected abstract Optional<Chunk> findMissingVerbSubjectByConjunction(JCas jCas, Chunk vp);

	protected abstract Optional<Dependency> findSubjectForVerbPhrase(Collection<Dependency> dependencies, Chunk vp);

}
