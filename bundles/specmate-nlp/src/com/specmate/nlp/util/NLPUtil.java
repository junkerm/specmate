package com.specmate.nlp.util;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

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

	public static List<Constituent> getVerbPhrases(JCas jCas, Sentence sentence) {
		return getConstituents(jCas, sentence, ConstituentType.VP);
	}

	public static List<Constituent> getNounPhrases(JCas jCas, Sentence sentence) {
		return getConstituents(jCas, sentence, ConstituentType.NP);
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

	public static String printParse(JCas jcas) {
		StringJoiner builder = new StringJoiner(" ");
		Constituent con = JCasUtil.select(jcas, Constituent.class).iterator().next();
		printConstituent(con, builder);
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

	public static List<Constituent> getSentenceConstituents(JCas jCas) {
		return getSentences(jCas).stream().map(s -> JCasUtil.selectCovered(Constituent.class, s).stream()
				.filter(c -> c.getConstituentType().contentEquals("S") || c.getConstituentType().contentEquals("SBAR"))
				.findFirst()).flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
				.collect(Collectors.toList());
	}
}
