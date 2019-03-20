package com.specmate.nlp.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			return constituentTypeName;
		}

		private ConstituentType(String name) {
			constituentTypeName = name;
		}
	}

	public static List<Constituent> getVerbPhrases(JCas jCas, Sentence sentence) {
		return getConstituents(jCas, sentence, ConstituentType.VP);
	}

	public static List<Constituent> getNounPhrases(JCas jCas, Sentence sentence) {
		return getConstituents(jCas, sentence, ConstituentType.NP);
	}

	public static List<Chunk> getVerbPhraseChunks(JCas jCas, Sentence sentence) {
		return getChunks(jCas, sentence, ConstituentType.VP);
	}

	public static List<Chunk> getNounPhraseChunks(JCas jCas, Sentence sentence) {
		return getChunks(jCas, sentence, ConstituentType.NP);
	}

	public static List<Chunk> getChunks(JCas jCas, Sentence sentence, ConstituentType type) {
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

	public static <T extends Annotation> Annotation selectIfCovering(Class<T> clazz, Annotation covering) {
		List<T> cov = JCasUtil.selectCovering(clazz, covering);
		if (cov.isEmpty()) {
			return covering;
		} else {
			return cov.get(0);
		}
	}

	public static Optional<Dependency> findDependency(Collection<Dependency> dependencies, Annotation chunk,
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
