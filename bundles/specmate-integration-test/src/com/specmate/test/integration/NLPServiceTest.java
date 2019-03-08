package com.specmate.test.integration;

import java.util.Arrays;

import org.apache.uima.jcas.JCas;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.matcher.AndMatcher;
import com.specmate.nlp.matcher.AnyMatcher;
import com.specmate.nlp.matcher.ChildrenSequenceMatcher;
import com.specmate.nlp.matcher.ConstituentTypeMatcher;
import com.specmate.nlp.matcher.CoveredTextMatcher;
import com.specmate.nlp.matcher.ExactlyOneConsumer;
import com.specmate.nlp.matcher.IConstituentTreeMatcher;
import com.specmate.nlp.matcher.MatchResult;
import com.specmate.nlp.matcher.SequenceMatcher;
import com.specmate.nlp.matcher.ZeroOrMoreConsumer;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class NLPServiceTest {

	@Test
	public void testOpenNlpService() throws SpecmateException {
		INLPService nlpService = getNLPService();
		// JCas result = nlpService.processText("If the tool detects an error, it shows
		// a warning window.");
		//
		// String parseString = NLPUtil.printParse(result);
		// Assert.assertEquals(
		// "( ROOT ( S ( SBAR If ( S ( NP the tool ) ( VP detects ( NP an error ) ) ) )
		// , ( NP it ) ( VP shows ( NP a warning window ) ) . ) )",
		// parseString);
		//
		// String posString = NLPUtil.printPOSTags(result);
		// Assert.assertEquals(
		// "If (IN) the (DT) tool (NN) detects (VBZ) an (DT) error (NN) , (,) it (PRP)
		// shows (VBZ) a (DT) warning (NN) window (NN) . (.)",
		// posString);

		JCas result = nlpService.processText("Das Werkzeug zeigt einen Fehler und zeigt ein Warnfenster");
		System.out.println(NLPUtil.printPOSTags(result));
		System.out.println(NLPUtil.printChunks(result));
	}

	@Test
	public void testMatcher() throws SpecmateException {
		String text = "If the tool detects an error, then consequently it shows a warning window.";
		String expectedCause = "the tool detects an error";
		String expectedEffect = "it shows a warning window.";

		checkCauseEffect(text, expectedCause, expectedEffect);

		text = "If Specmate detects an error or the user has no login, Specmate shows a warning window and makes a sound.";
		expectedCause = "Specmate detects an error or the user has no login";
		expectedEffect = "Specmate shows a warning window and makes a sound.";

		checkCauseEffect(text, expectedCause, expectedEffect);

		text = "If Specmate detects an error or if the user has no login, Specmate shows a warning window and makes a sound.";
		expectedCause = "Specmate detects an error or if the user has no login";
		expectedEffect = "Specmate shows a warning window and makes a sound.";

		checkCauseEffect(text, expectedCause, expectedEffect);

	}

	private void checkCauseEffect(String text, String expectedCause, String expectedEffect) throws SpecmateException {
		INLPService nlpService = getNLPService();
		JCas result = nlpService.processText(text);
		System.out.println(NLPUtil.printParse(result));
		Constituent cons = NLPUtil.getSentenceConstituents(result).get(0);

		IConstituentTreeMatcher matcher = new ChildrenSequenceMatcher(new SequenceMatcher(Arrays.asList(
				new ExactlyOneConsumer(result,
						new AndMatcher(new ConstituentTypeMatcher("SBAR"),
								new CoveredTextMatcher("If\\s*(.*)", "cause"))),
				new ZeroOrMoreConsumer(result, new CoveredTextMatcher(",")),
				new ZeroOrMoreConsumer(result, new ConstituentTypeMatcher("ADVP")),
				new ZeroOrMoreConsumer(result, new AnyMatcher(), "effect"))));
		MatchResult matchResult = matcher.match(cons);
		Assert.assertTrue(matchResult.isMatch());
		String effect = matchResult.getMatchGroupAsText("effect");
		String cause = matchResult.getMatchGroupAsText("cause");

		Assert.assertEquals(expectedCause, cause);
		Assert.assertEquals(expectedEffect, effect);
	}

	private INLPService getNLPService() throws SpecmateException {
		BundleContext context = FrameworkUtil.getBundle(NLPServiceTest.class).getBundleContext();
		ServiceTracker<INLPService, INLPService> nlpServiceTracker = new ServiceTracker<>(context,
				INLPService.class.getName(), null);
		nlpServiceTracker.open();
		INLPService nlpService;
		try {
			nlpService = nlpServiceTracker.waitForService(20000);
		} catch (InterruptedException e) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, e);
		}
		Assert.assertNotNull(nlpService);
		return nlpService;
	}

}
