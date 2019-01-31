package com.specmate.nlp.matcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class ZeroOrMoreConsumer implements IConstituentConsumingMatcher {

	private IConstituentTreeMatcher matcher;
	private List<Annotation> consumedAnnorations = new ArrayList<>();
	private String name;
	private JCas jCas;

	public ZeroOrMoreConsumer(JCas jCas, IConstituentTreeMatcher matcher, String name) {
		this.matcher = matcher;
		this.name = name;
		this.jCas = jCas;
	}

	public ZeroOrMoreConsumer(JCas jCas, IConstituentTreeMatcher matcher) {
		this(jCas, matcher, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean consume(Annotation annotation) {
		MatchResult result = matcher.match(annotation);
		boolean matched = result.isMatch();
		if (matched) {
			consumedAnnorations.add(annotation);
		}
		return matched;
	}

	@Override
	public MatchResult getMatchResult() {
		MatchResult result = new MatchResult(true);
		if (name != null) {
			int begin = consumedAnnorations.get(0).getBegin();
			int end = consumedAnnorations.get(consumedAnnorations.size() - 1).getEnd();
			result.addMatchGroup(name, jCas.getDocumentText().substring(begin, end));
		}
		return result;
	}

	@Override
	public void consumeEmpty() {

	}

}
