/*
 * generated by Xtext 2.17.1
 */
package com.specmate.cause_effect_patterns.internal.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class SpecDSLAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("com/specmate/cause_effect_patterns/internal/parser/antlr/internal/InternalSpecDSL.tokens");
	}
}
