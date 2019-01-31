package com.specmate.nlp.matcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class ExactlyOneConsumer implements IConstituentConsumingMatcher {

	private IConstituentTreeMatcher matcher;
	private boolean consumed = false;
	private String name;
	private List<Annotation> consumedAnnorations = new ArrayList<>();
	private JCas jCas;
	private MatchResult matchingResult;

	public ExactlyOneConsumer(JCas jCas, IConstituentTreeMatcher matcher) {
		this(jCas, matcher, null);
	}

	public ExactlyOneConsumer(JCas jCas, IConstituentTreeMatcher matcher, String name) {
		this.matcher = matcher;
		this.name = name;
		this.jCas = jCas;
	}

	@Override
	public boolean consume(Annotation annotation) {
		if (this.consumed) {
			return false;
		}
		this.consumed = true;
		MatchResult result = this.matcher.match(annotation);

		if (result.isMatch()) {
			this.matchingResult = result;
			this.consumedAnnorations.add(annotation);
		}
		return result.isMatch();
	}

	@Override
	public MatchResult getMatchResult() {
		MatchResult result = new MatchResult(this.matchingResult != null);
		if (this.matchingResult != null) {
			result.addMatchGroupsFrom(this.matchingResult);
		}
		if (this.name != null && this.consumedAnnorations.size() > 0) {
			int begin = this.consumedAnnorations.get(0).getBegin();
			int end = this.consumedAnnorations.get(this.consumedAnnorations.size() - 1).getEnd();
			result.addMatchGroup(this.name, this.jCas.getDocumentText().substring(begin, end));
		}
		return result;
	}

	@Override
	public void consumeEmpty() {

	}

}
