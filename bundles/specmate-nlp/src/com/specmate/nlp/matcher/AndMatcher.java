package com.specmate.nlp.matcher;

import java.util.Arrays;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

public class AndMatcher implements IConstituentTreeMatcher {

	private List<IConstituentTreeMatcher> matchers;

	public AndMatcher(IConstituentTreeMatcher... constituentTreeMatchers) {
		matchers = Arrays.asList(constituentTreeMatchers);
	}

	@Override
	public MatchResult match(Annotation constituent) {
		return matchers.stream().map(m -> m.match(constituent)).reduce(new MatchResult(true), (r1, r2) -> {
			MatchResult r = new MatchResult(r1.isMatch() && r2.isMatch());
			r.addMatchGroupsFrom(r1);
			r.addMatchGroupsFrom(r2);
			return r;
		});

	}

}
