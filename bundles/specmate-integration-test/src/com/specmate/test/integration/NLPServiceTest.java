package com.specmate.test.integration;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.uima.jcas.JCas;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.nlp.dependency.DependencyData;
import com.specmate.nlp.dependency.matcher.MatchResult;
import com.specmate.nlp.dependency.matcher.MatchUtil;
import com.specmate.nlp.dependency.matcher.Matcher;
import com.specmate.nlp.dependency.matcher.SubtreeMatcher;
import com.specmate.nlp.dependency.matcher.TokenMatcher;
import com.specmate.nlp.matcher.AndMatcher;
import com.specmate.nlp.matcher.AnyMatcher;
import com.specmate.nlp.matcher.ChildrenSequenceMatcher;
import com.specmate.nlp.matcher.ConstituentTypeMatcher;
import com.specmate.nlp.matcher.CoveredTextMatcher;
import com.specmate.nlp.matcher.ExactlyOneConsumer;
import com.specmate.nlp.matcher.IConstituentTreeMatcher;
import com.specmate.nlp.matcher.SequenceMatcher;
import com.specmate.nlp.matcher.ZeroOrMoreConsumer;
import com.specmate.nlp.util.EnglishSentenceUnfolder;
import com.specmate.nlp.util.GermanSentenceUnfolder;
import com.specmate.nlp.util.NLPUtil;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

public class NLPServiceTest {

	@Test
	public void testOpenNlpService() throws SpecmateException {
		INLPService nlpService = getNLPService();
		JCas result = nlpService.processText("If the tool detects an error, it shows a warning window.", ELanguage.EN);

		String parseString = NLPUtil.printParse(result);
		Assert.assertEquals(
				"( ROOT ( S ( SBAR If ( S ( NP the tool ) ( VP detects ( NP an error ) ) ) ) , ( NP it ) ( VP shows ( NP a warning window ) ) . ) )",
				parseString);

		String posString = NLPUtil.printPOSTags(result);
		Assert.assertEquals(
				"If (IN) the (DT) tool (NN) detects (VBZ) an (DT) error (NN) , (,) it (PRP) shows (VBZ) a (DT) warning (NN) window (NN) . (.)",
				posString);

		result = nlpService.processText("Wenn das Werkzeug einen Fehler erkennt, zeigt es ein Warnfenster.",
				ELanguage.DE);

		Assert.assertEquals(
				"Wenn (KOUS) das (ART) Werkzeug (NN) einen (ART) Fehler (NN) erkennt (VVFIN) , ($,) zeigt (VVFIN) es (PPER) ein (ART) Warnfenster (NN) . ($.)",
				NLPUtil.printPOSTags(result));
		Assert.assertEquals("das Werkzeug einen Fehler (NP) erkennt (VP) zeigt (VP) ein Warnfenster (NP)",
				NLPUtil.printChunks(result));

		Assert.assertEquals("erkennt <--KONJ-- Wenn\n" + "Werkzeug <--DET-- das\n"
				+ "erkennt <--SUBJ-- Werkzeug\nFehler <--DET-- einen\n" + "erkennt <--OBJA-- Fehler\n"
				+ "erkennt <--ROOT-- erkennt\n" + ", <--ROOT-- ,\n" + "zeigt <--ROOT-- zeigt\n" + "zeigt <--SUBJ-- es\n"
				+ "Warnfenster <--DET-- ein\n" + "zeigt <--OBJA-- Warnfenster\n" + ". <--ROOT-- .",
				NLPUtil.printDependencies(result));

		Assert.assertEquals(
				"( ROOT ( S ( S Wenn ( NP das Werkzeug ) ( NP einen Fehler ) ( S erkennt ) ) , zeigt es ( NP ein Warnfenster ) . ) )",
				NLPUtil.printParse(result));

	}

	@Test
	public void testSentenceUnfolding() throws SpecmateException {
		INLPService nlpService = getNLPService();
		JCas result = nlpService.processText(
				"If the tool has an error or fails, the tool alerts the user and shows a window.", ELanguage.EN);

		Sentence sentence = NLPUtil.getSentences(result).iterator().next();

		String chunkString = NLPUtil.printChunks(result);
		System.out.println(chunkString);

		String unfolded = new EnglishSentenceUnfolder().insertMissingSubjects(result, sentence);
		System.out.println(unfolded);
		Assert.assertEquals(
				"If the tool has an error or the tool fails, the tool alerts the user and the tool shows a window.",
				unfolded);

		result = nlpService.processText(
				"Wenn das Werkzeug fehlschlägt oder einen Fehler findet, blinkt und piept das Werkzeug.", ELanguage.DE);

		chunkString = NLPUtil.printChunks(result);

		sentence = NLPUtil.getSentences(result).iterator().next();

		GermanSentenceUnfolder unfolder = new GermanSentenceUnfolder();
		unfolded = unfolder.insertMissingSubjects(result, sentence);
		Assert.assertEquals("Wenn das Werkzeug fehlschlägt oder einen Fehler findet, blinkt und piept das Werkzeug.",
				unfolded);
	}

	@Test
	public void testMatcher() throws SpecmateException {
		String text = "If the tool detects an error, then consequently it shows a warning window.";
		String expectedCause = "the tool detects an error";
		String expectedEffect = "it shows a warning window.";

		checkCauseEffect(text, ELanguage.EN, expectedCause, expectedEffect);

		text = "If Specmate detects an error or the user has no login, Specmate shows a warning window and makes a sound.";
		expectedCause = "Specmate detects an error or the user has no login";
		expectedEffect = "Specmate shows a warning window and makes a sound.";

		checkCauseEffect(text, ELanguage.EN, expectedCause, expectedEffect);

		text = "If Specmate detects an error or if the user has no login, Specmate shows a warning window and makes a sound.";
		expectedCause = "Specmate detects an error or if the user has no login";
		expectedEffect = "Specmate shows a warning window and makes a sound.";

		checkCauseEffect(text, ELanguage.EN, expectedCause, expectedEffect);

	}

	@Test
	public void testDependencyParse() throws SpecmateException {
		INLPService nlpService = getNLPService();
		JCas result = nlpService.processText("If the great tool detects an error, it shows a warning window.", ELanguage.EN);
		DependencyData data = DependencyData.generateFromJCas(result);
		Assert.assertEquals(data.getHeads().size(), 1);
		System.out.println(data);
		
		// Define a Cause Effect Rule
		SubtreeMatcher treeMatcherEffect = new SubtreeMatcher("Effect", ".*");
		SubtreeMatcher treeMatcherCause = new SubtreeMatcher("Cause", ".*");
		treeMatcherEffect.arcTo(treeMatcherCause,"advcl");
		TokenMatcher explicitMatcher1 = new TokenMatcher("(?i)if", "IN");
		treeMatcherCause.arcTo(explicitMatcher1,"mark");
		// And Rule
		
		
		// Define Subject-Preficate Rule
		SubtreeMatcher treeMatcherSubject = new SubtreeMatcher("Subject",".*");
		SubtreeMatcher treeMatcherPredicate = new SubtreeMatcher("Predicate",".*");
		treeMatcherPredicate.arcTo(treeMatcherSubject, "nsubj");
		
		Vector<Matcher> rules = new Vector<Matcher>();
		rules.add(treeMatcherEffect);
		rules.add(treeMatcherPredicate);
		
		// Run the rules
		List<MatchResult> results = MatchUtil.evaluateRuleset(rules, data);
		Assert.assertEquals(data.getHeads().size(), results.size());
		
		MatchResult res = results.get(0);
		Assert.assertTrue(res.isSuccessfulMatch());
		
		// Get the result:
		//Cause
		Assert.assertTrue(res.hasSubmatch("Cause"));
		MatchResult cause = res.getSubmatch("Cause");
		System.out.println("Cause:\n"+cause.getMatchTree());
		Assert.assertTrue(cause.isSuccessfulMatch());
		
		Assert.assertTrue(cause.hasSubmatch("Subject"));
		MatchResult subj = cause.getSubmatch("Subject");
		System.out.println("Cause Subject:\n"+subj.getMatchTree());
		
		Assert.assertTrue(cause.hasSubmatch("Predicate"));
		MatchResult pred = cause.getSubmatch("Predicate");
		System.out.println("Cause Predicate:\n"+pred.getMatchTree());
		
		//Effect
		Assert.assertTrue(res.hasSubmatch("Effect"));
		MatchResult effect = res.getSubmatch("Effect");
		System.out.println("Effect:\n"+effect.getMatchTree());
		Assert.assertTrue(effect.isSuccessfulMatch());
		
		Assert.assertTrue(effect.hasSubmatch("Subject"));
		MatchResult eSubj = effect.getSubmatch("Subject");
		System.out.println("Effect Subject:\n"+eSubj.getMatchTree());
		
		Assert.assertTrue(effect.hasSubmatch("Predicate"));
		MatchResult ePred = effect.getSubmatch("Predicate");
		System.out.println("Effect Predicate:\n"+ePred.getMatchTree());
	}
	
	private void checkCauseEffect(String text, ELanguage language, String expectedCause, String expectedEffect)
			throws SpecmateException {
		INLPService nlpService = getNLPService();
		JCas result = nlpService.processText(text, language);
		System.out.println(NLPUtil.printParse(result));
		Constituent cons = NLPUtil.getSentenceConstituents(result).get(0);

		IConstituentTreeMatcher matcher = new ChildrenSequenceMatcher(new SequenceMatcher(Arrays.asList(
				new ExactlyOneConsumer(result,
						new AndMatcher(new ConstituentTypeMatcher("SBAR"),
								new CoveredTextMatcher("If\\s*(.*)", "cause"))),
				new ZeroOrMoreConsumer(result, new CoveredTextMatcher(",")),
				new ZeroOrMoreConsumer(result, new ConstituentTypeMatcher("ADVP")),
				new ZeroOrMoreConsumer(result, new AnyMatcher(), "effect"))));
		com.specmate.nlp.matcher.MatchResult matchResult = matcher.match(cons);
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
