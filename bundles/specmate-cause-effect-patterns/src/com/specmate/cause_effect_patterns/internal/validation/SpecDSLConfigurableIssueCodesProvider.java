/*
 * generated by Xtext 2.17.1
 */
package com.specmate.cause_effect_patterns.internal.validation;

import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;

public class SpecDSLConfigurableIssueCodesProvider extends ConfigurableIssueCodesProvider {
	protected static final String ISSUE_CODE_PREFIX = "com.specmate.cause_effect_patterns.";

	public static final String DEPRECATED_MODEL_PART = ISSUE_CODE_PREFIX + "deprecatedModelPart";
	
	/*@Override
	protected void initialize(IAcceptor<PreferenceKey> acceptor) {
		super.initialize(acceptor);
		acceptor.accept(create(DEPRECATED_MODEL_PART, SeverityConverter.SEVERITY_WARNING));
	}*/
}
