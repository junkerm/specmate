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
import com.specmate.nlp.api.ELanguage;
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
import com.specmate.nlp.util.EnglishSentenceUnfolder;
import com.specmate.nlp.util.GermanSentenceUnfolder;
import com.specmate.nlp.util.NLPUtil;
import com.specmate.nlp.util.SentenceUnfolderBase;

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
		Assert.assertEquals("das Werkzeug (NP) einen Fehler (NP) erkennt (VP) zeigt (VP) ein Warnfenster (NP)",
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
	public void testSentenceUnfoldingEnglish() throws SpecmateException {
		INLPService nlpService = getNLPService();

		String text = "If the tool has an error or fails, the tool alerts the user and shows a window.";
		String unfolded = new EnglishSentenceUnfolder().unfold(nlpService, text, ELanguage.EN);
		Assert.assertEquals(
				"If the tool has an error or the tool fails, the tool alerts the user and the tool shows a window.",
				unfolded);

		text = "The magazine contains the nicest hiking tours and trips.";
		unfolded = new EnglishSentenceUnfolder().unfold(nlpService, text, ELanguage.EN);
		Assert.assertEquals("The magazine contains the nicest hiking tours and The magazine contains trips.", unfolded);
	}

	@Test
	public void testSentenceUnfoldingGerman() throws SpecmateException {
		INLPService nlpService = getNLPService();
		SentenceUnfolderBase unfolder = new GermanSentenceUnfolder();

		String text = "Wenn das Werkzeug fehlschlägt oder einen Fehler oder ein Problem hat, zeigt das Werkzeug ein Warnfenster und einen Fehlermarker an und gibt eine Meldung aus.";
		String unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals(
				"Wenn das Werkzeug fehlschlägt oder das Werkzeug einen Fehler hat oder das Werkzeug ein Problem hat, zeigt das Werkzeug ein Warnfenster und zeigt das Werkzeug einen Fehlermarker an und gibt das Werkzeug eine Meldung aus.",
				unfolded);

		text = "Das Magazin hat die schönsten Wanderungen und Ausflugziele.";
		unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals("Das Magazin hat die schönsten Wanderungen und Das Magazin hat Ausflugziele.", unfolded);

		text = "Felix Lindner legte sein Abitur ab und nahm dann ein Studium der neueren Sprachen auf.";
		unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals(
				"Felix Lindner legte sein Abitur ab und Felix nahm dann ein Studium der neueren Sprachen auf.",
				unfolded);

		text = "Der sowjetische Chirurg Fjodorow reist im Jahr 1982 nach Kabul, um dort Vorlesungen zu halten und um in einem Militärkrankenhaus zu arbeiten.";
		unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals(
				"Der sowjetische Chirurg Fjodorow reist im Jahr 1982 nach Kabul, um dort Vorlesungen zu halten und um in einem Militärkrankenhaus zu arbeiten.",
				unfolded);

		text = "Er versorgt Verwundete beider Seiten und befragt sie nach ihren Erfahrungen und persönlichen Lebensumständen.";
		unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals(
				"Er versorgt Verwundete beider Seiten und Er befragt sie nach ihren Erfahrungen und persönlichen Lebensumständen.",
				unfolded);

		text = "Der häufige Blätterpilz wächst im Herbst  und fruktifiziert gerne in Hexenringen.";
		unfolded = unfolder.unfold(nlpService, text, ELanguage.DE);
		Assert.assertEquals(
				"Der häufige Blätterpilz wächst im Herbst  und Der häufige Blätterpilz fruktifiziert gerne in Hexenringen.",
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
