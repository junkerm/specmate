package com.specmate.nlp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.Chunk;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class NLPUtil {
	enum ConstituentType {
		VP("VP"), NP("NP");

		private String constituentTypeName;

		public String getName() {
			return this.constituentTypeName;
		}

		private ConstituentType(String name) {
			this.constituentTypeName = name;
		}
	}

	public static List<Chunk> getVerbPhrases(JCas jCas, Sentence sentence) {
		return getChunk(jCas, sentence, ConstituentType.VP);
	}

	public static List<Chunk> getNounPhrases(JCas jCas, Sentence sentence) {
		return getChunk(jCas, sentence, ConstituentType.NP);
	}

	public static List<Chunk> getChunk(JCas jCas, Sentence sentence, ConstituentType type) {
		return JCasUtil.selectCovered(jCas, Chunk.class, sentence).stream()
				.filter(c -> c.getChunkValue().contentEquals(type.getName())).collect(Collectors.toList());
	}

	public static List<Constituent> getConstituents(JCas jCas, Sentence sentence, ConstituentType type) {
		return JCasUtil.selectCovered(jCas, Constituent.class, sentence).stream()
				.filter(c -> c.getConstituentType().contentEquals(type.getName())).collect(Collectors.toList());
	}

	public static String printPOSTags(JCas jcas) {
		StringJoiner joiner = new StringJoiner(" ");
		JCasUtil.select(jcas, Token.class).forEach(p -> {
			joiner.add(p.getCoveredText()).add("(" + p.getPosValue() + ")");
		});
		return joiner.toString();
	}

	public static String printChunks(JCas jcas) {
		StringJoiner joiner = new StringJoiner(" ");
		JCasUtil.select(jcas, Chunk.class).forEach(c -> {
			joiner.add(c.getCoveredText()).add("(" + c.getChunkValue() + ")");
		});
		return joiner.toString();
	}

	public static String printParse(JCas jcas) {
		StringJoiner builder = new StringJoiner(" ");
		Constituent con = JCasUtil.select(jcas, Constituent.class).iterator().next();
		printConstituent(con, builder);
		return builder.toString();
	}

	public static String printDependencies(JCas jcas) {
		Collection<Dependency> dependencies = JCasUtil.select(jcas, Dependency.class);
		StringJoiner builder = new StringJoiner("\n");
		for (Dependency dep : dependencies) {
			builder.add(dep.getGovernor().getCoveredText() + " <--" + dep.getDependencyType() + "-- "
					+ dep.getDependent().getCoveredText());
		}
		return builder.toString();
	}

	private static void printConstituent(Constituent con, StringJoiner joiner) {
		joiner.add("(").add(con.getConstituentType());
		for (FeatureStructure fs : con.getChildren()) {
			if (fs instanceof Constituent) {
				printConstituent((Constituent) fs, joiner);
			} else if (fs instanceof Token) {
				Token token = (Token) fs;
				joiner.add(token.getText());
			}
		}
		joiner.add(")");
	}

	public static Collection<Sentence> getSentences(JCas jCas) {
		return JCasUtil.select(jCas, Sentence.class);
	}

	public static Optional<Constituent> getSentenceConstituent(JCas jCas, Sentence sentence) {
		return JCasUtil.selectCovered(Constituent.class, sentence).stream()
				.filter(c -> c.getConstituentType().contentEquals("S") || c.getConstituentType().contentEquals("SBAR"))
				.findFirst();
	}

	public static List<Constituent> getSentenceConstituents(JCas jCas) {
		return getSentences(jCas).stream().map(s -> getSentenceConstituent(jCas, s))
				.flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty()).collect(Collectors.toList());
	}

	public static String insertMissingSubjects(JCas jCas, Sentence sentence) {
		List<Pair<Integer, String>> insertions = new ArrayList<Pair<Integer, String>>();
		List<Chunk> vpws = findVerbalPhraseWithoutSubject(jCas, sentence);
		for (Chunk vp : vpws) {
			Optional<Chunk> subj = findSubjectByConjunction(jCas, vp);
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

	private static List<Pair<Annotation, Annotation>> completeSubjectsByConjunction(JCas jCas, Annotation subj) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		List<Pair<Annotation, Annotation>> result = new ArrayList<>();
		result.add(Pair.of(null, subj));
		Optional<Dependency> conjDep;
		Optional<Dependency> ccDep;
		do {
			conjDep = findDependency(dependencies, subj, "conj", true);
			ccDep = findDependency(dependencies, subj, "cc", true);
			if (conjDep.isPresent() && ccDep.isPresent()) {
				Token govConjToken = conjDep.get().getDependent();
				Token govCcToken = ccDep.get().getDependent();
				Annotation conSubj = selectIfCovering(Chunk.class, govConjToken);
				Annotation cc = selectIfCovering(Chunk.class, govCcToken);
				result.add(Pair.of(cc, conSubj));
				subj = conSubj;
			}
		} while (conjDep.isPresent());
		return result;
	}

	private static <T extends Annotation> Annotation selectIfCovering(Class<T> clazz, Annotation covering) {
		List<T> cov = JCasUtil.selectCovering(clazz, covering);
		if (cov.isEmpty()) {
			return covering;
		} else {
			return cov.get(0);
		}
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

	private static Optional<Chunk> findSubjectByConjunction(JCas jCas, Chunk vp) {
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		Optional<Dependency> conj = findDependency(dependencies, vp, "conj", false);
		if (conj.isPresent()) {
			Token governor = conj.get().getGovernor();
			Optional<Dependency> subj = findDependency(dependencies, governor, "nsubj", true);
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

	public static List<Chunk> findVerbalPhraseWithoutSubject(JCas jCas, Sentence sentence) {
		List<Chunk> result = new ArrayList<>();
		List<Chunk> verbPhrases = getVerbPhrases(jCas, sentence);
		Collection<Dependency> dependencies = JCasUtil.select(jCas, Dependency.class);
		for (Chunk vp : verbPhrases) {
			Optional<Dependency> subject = findSubjectForVerbPhrase(dependencies, vp);
			if (!subject.isPresent()) {
				result.add(vp);
			}
		}
		return result;
	}

	private static Optional<Dependency> findSubjectForVerbPhrase(Collection<Dependency> dependencies, Chunk vp) {
		return findDependency(dependencies, vp, "nsubj", true);
	}

	private static Optional<Dependency> findDependency(Collection<Dependency> dependencies, Annotation chunk,
			String depType, boolean isGovernor) {
		List<Token> tokens;
		if (chunk instanceof Token) {
			tokens = Arrays.asList((Token) chunk);
		} else {
			tokens = JCasUtil.selectCovered(Token.class, chunk);
		}
		for (Dependency dep : dependencies) {
			if (dep.getDependencyType().equals(depType)) {
				if (tokens.contains(isGovernor ? dep.getGovernor() : dep.getDependent())) {
					return Optional.of(dep);
				}
			}
		}
		return Optional.empty();
	}

}
