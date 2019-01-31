package com.specmate.nlp.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.jcas.tcas.Annotation;

public class CoveredTextMatcher implements IConstituentTreeMatcher {

	private Pattern regexp;
	private String[] names;

	public CoveredTextMatcher(String regexp, String... names) {
		this.regexp = Pattern.compile(regexp);
		this.names = names;
	}

	@Override
	public MatchResult match(Annotation constituent) {

		Matcher matcher = regexp.matcher(constituent.getCoveredText());
		boolean result = matcher.matches();
		MatchResult matchResult = new MatchResult(result);
		if (result) {
			// group zero is whole macth, not included in group count (hence +1)
			for (int i = 0; i < Math.min(matcher.groupCount() + 1, names.length); i++) {
				matchResult.addMatchGroup(names[i], matcher.group(i + 1));
			}
		}
		return matchResult;
	}

}
