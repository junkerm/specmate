package com.allianz.view.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.SpecmateException;

public class AllianzDemoDataTest extends EmfRestTestBase {
	private static final String TEST_VIEW_KEY = "com.allianz.views.testview";
	private static Map<String, String> urlMap = new HashMap<>();
	
	@Test
	public void testSAD() throws Exception {

		urlMap.clear();
		
		int myYCounter = 0;
		
		// create project
		String bp_projectUrl = createSADProject();
		
		// create blueprint
		String bp_blueprintUri = createSADBluePrint(bp_projectUrl);
		String action_SchadenAnlegenUri = create_SchadenAnlegen(bp_blueprintUri, myYCounter++);		
		String action_DeckungPruefenUri = create_DeckungPruefen(bp_blueprintUri, action_SchadenAnlegenUri, myYCounter++);
		String action_SchadenBearbeitenUri = create_SchadenBearbeiten(bp_blueprintUri, action_DeckungPruefenUri, myYCounter++);
		String action_SchadenRegulierenUri = create_SchadenRegulieren(bp_blueprintUri, action_SchadenBearbeitenUri, myYCounter++);
		String action_SchadenSchliessenUri = create_SchadenSchliessen(bp_blueprintUri, action_SchadenRegulierenUri, myYCounter++);

		// create tests
		
		// translate BP-URI -> TestSpec-URI
		String ts_projectUri = translateBlueprintUriToTestSpecificationUri(bp_projectUrl);
		
		// create test spec
		TestSpecification test = AllianzTestEmfResttest.getTestSpecification("Test 930927 Check for creation of claim on backdate", "To check for the creation of claim for Migratin Hausrat VHB 42, VHB 66, VHB 74 for backate, once the Migration of product is done."
				+ "\n\nTest Approach - Part 1:\n1. Create a basic  claim on Migration Hausrat VHB 42, VHB 66, VHB 74 for backdate(for February)\n2. Fill all mandatory details in Tasche\"Meldung\" and Lasche \"Ereignis\".\n3. dd the mandatory Sache at Tasche \"Sache\" and Lasche \"Liste\".\n4. Under Tasche \"Schaden\" and Lasche \"Stamm\", fill the reserve amount.\n5. Save the claim.\n\nTest Approach - Part 2\n6. Reopen the claim.\n7.Edit the claim\n8. Go to Tasche \"Sache\" and Lasche \"Liste\", and a new Sache.\n9. Save and exit the claim.\n\nRequirement:\n-Take a Migration Hausrat VHB 42, VHB 66, VHB 74 product\nExpected Result:\n-Creation of claim should be successful on back dated claim\n-Claim andern should be successfull");
		String ts_specUrl = postAndVerify(test, ts_projectUri, CONTENTS_NAME);
		System.out.println("created test specification: " + ts_specUrl);
		
		AllianzTestProcedureEmfResttest.addSliceToTestSpec(ts_specUrl, "Part 1 - Create a basic claim.", "Create a basic claim on Migration Hausrat VHB42, HVB 74, for backdate (for February) and fill all mandatory fields.\n\nmissing data:\n- test data\n- selection of optional steps\n- selection of variants", urlMap.get("Schaden anlegen"), urlMap.get("Deckung pruefen"));
		AllianzTestProcedureEmfResttest.addSliceToTestSpec(ts_specUrl, "Part 2 - Reopen claim and add another object.", "Reopen the claim and edit it. Add another damaged object to the claim.\n\nmissing data:\n- test data\n- selection of optional steps\n- selection of variants",  urlMap.get("Neue Sache Auswaehlen/Anlegen"), urlMap.get("Voraussichtliche Schadenhoehe erfassen"));
				
		// end
		Thread.sleep(Long.MAX_VALUE);
		Assert.fail("END OF TEST");
	}

	private static String createSADProject() throws SpecmateException {
		// create project
		String bp_projectUrl = BlueprintEmfRestTest.createAndPostProject("Allianz");
		System.out.println("created project: " + bp_projectUrl);

		return bp_projectUrl;
	}	

	private static String createSADBluePrint(String projectUri) throws SpecmateException {
		String bp_blueprintUri = BlueprintEmfRestTest.createAndPostBlueprintToUrl(projectUri, "Schaden Assendienst");
		System.out.println("created blue print: " + bp_blueprintUri);

		return bp_blueprintUri;
	}
	
	private String create_SchadenAnlegen(String parentUri, int yCounter) throws SpecmateException {
		
		String mainActionUri = createAction("Schaden anlegen", parentUri, null, false, yCounter);
		
		int myYCounter = 0;
		
		String vertragSuchenUri = createAction("Vertrag suchen", mainActionUri, null, false, myYCounter++);
		String allgemSchadenDatenErfassenUri = create_AllgemeineSchadenDatenErfassen(mainActionUri, vertragSuchenUri, myYCounter++);
		String beschaedigtesObjektErfassenUri = create_BeschaedigtesObjektErfassen(mainActionUri, allgemSchadenDatenErfassenUri, myYCounter++);
				
		String beteiligtePersonErfassenUri = create_BeteiligtePersonenErfassen(mainActionUri, beschaedigtesObjektErfassenUri, myYCounter++);
						
		return mainActionUri;
	}
	
	private String create_AllgemeineSchadenDatenErfassen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
			
		String mainActionUri = createAction("Allgemeine Schadendaten erfassen", parentUri, predecessorUri, false, yCounter);

		int myYCounter = 0;
		
		String schadenzeitpunktErfassenUri = createAction("Schadenzeitpunkt erfassen", mainActionUri, null, false, myYCounter++);
		String schadenartErfassenUri = createAction("Schadenart erfassen", mainActionUri, schadenzeitpunktErfassenUri, false, myYCounter++);
		String schadenursacheErfassenUri = createAction("Schadenursache erfassen", mainActionUri, schadenartErfassenUri, false, myYCounter++);
		String schadenortErfassenUri = createAction("Schadenort erfassen", mainActionUri, schadenursacheErfassenUri, false, myYCounter++);
		String schadenhergangErfassenUri = createAction("Schadenhergang erfassen", mainActionUri, schadenortErfassenUri, false, myYCounter++);
		String melderErfassenUri = createAction("Melder erfassen", mainActionUri, schadenhergangErfassenUri, false, myYCounter++);
		String meldewegErfassenUri = createAction("Meldeweg erfassen", mainActionUri, melderErfassenUri, false, myYCounter++);
		String verschuldenErfassenUri = createAction("Verschulden erfassen", mainActionUri, meldewegErfassenUri, true, myYCounter++);
				
		return mainActionUri;
	}
	
	private String create_BeschaedigtesObjektErfassen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
		
		String mainActionUri = createAction("Beschaedigtes Objekt erfassen", parentUri, predecessorUri, false, yCounter);
		
		int myYCounter = 0;
		
		String neueSacheAuswaehlenAnlegenUri = createAction("Neue Sache Auswaehlen/Anlegen", mainActionUri, null, false, myYCounter++);		
		String adddressdatenErfassenUri = create_BeschaedigungErfassen(mainActionUri, neueSacheAuswaehlenAnlegenUri, myYCounter++);
		
		return mainActionUri;
	}
	
	private String create_BeschaedigungErfassen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
		
		String mainActionUri = createAction("Beschaedigung erfassen", parentUri, predecessorUri, false, yCounter);
		
		int myYCounter = 0;
		
		String BeschaedigungBeschreibenUri = createAction("Beschaedigung beschreiben", mainActionUri, null, false, myYCounter++);
		String neueSacheAuswaehlenAnlegenUri = createAction("Voraussichtliche Schadenhoehe erfassen", mainActionUri, BeschaedigungBeschreibenUri, false, myYCounter++);
		
		return mainActionUri;
	}
	
	private String create_BeteiligtePersonenErfassen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
			
		String mainActionUri = createAction("Beteiligte Personen erfassen", parentUri, predecessorUri, false, yCounter);

		int myYCounter = 0;
		
		String adddressdatenErfassenUri = create_AddressdatenErfassen(mainActionUri, null, myYCounter++);
		String verletzungsumfangErfassenUri = createAction("Verletzungsumfang erfassen", mainActionUri, adddressdatenErfassenUri, false, myYCounter++);
		
		return mainActionUri;
	}
	
	private String create_AddressdatenErfassen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
		
		String mainActionUri = createAction("Addressdaten erfassen", parentUri, predecessorUri, false, yCounter);

		int myYCounter = 0;
		
		String personSuchenUri = createAction("Person suchen", mainActionUri, null, false, myYCounter++);
		String personNeuAnlegenUri = createAction("Person neu anlegen", mainActionUri, personSuchenUri, false, myYCounter++);
		String kommunikationsdatenerfassenUri = createAction("Kommunikationsdaten erfassen", mainActionUri, personNeuAnlegenUri, false, myYCounter++);
		String datenschutzdatenErfassenUri = createAction("Datenschutzdaten erfassen", mainActionUri, kommunikationsdatenerfassenUri, false, myYCounter++);
		String bankdatenErfassenUri = createAction("Bankdaten erfassen", mainActionUri, datenschutzdatenErfassenUri, true, myYCounter++);
		String rolleDerPersonErfassenUri = createAction("Rolle der Person erfassen", mainActionUri, bankdatenErfassenUri, false, myYCounter++);
		
		return mainActionUri;
	}
	
	
	private String create_DeckungPruefen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
			
		String mainActionUri = createAction("Deckung pruefen", parentUri, predecessorUri, false, yCounter);
		
		int myYCounter = 0;
		
		String deckungEingebenUri = createAction("Deckung eingeben", mainActionUri, null, false, myYCounter++);
		String deckungPruefenUri = createAction("Deckung validieren", mainActionUri, deckungEingebenUri, false, myYCounter++);
		
		return mainActionUri;
	}
	
	private String create_SchadenBearbeiten(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {

		int myYCounter = 0;
		
		String mainActionUri = createAction("Schaden bearbeiten", parentUri, predecessorUri, false, yCounter);

		// TODO;
		
		return mainActionUri;
	}

	private String create_SchadenRegulieren(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {

		int myYCounter = 0;
		
		String mainActionUri = createAction("Schaden regulieren", parentUri, predecessorUri, false, yCounter);

		// TODO;
		
		return mainActionUri;
	}

	private String create_SchadenSchliessen(String parentUri, String predecessorUri, int yCounter) throws SpecmateException {
		
		int myYCounter = 0;
		
		String mainActionUri = createAction("Schaden schliessen", parentUri, predecessorUri, false, yCounter);

		// TODO;
		
		return mainActionUri;
	}
	
	private static String createAction(String name, String parentUri, String predecessorUri, boolean optional, String description, int yCounter) throws SpecmateException {
		Action action = BlueprintEmfRestTest.getTestAction(optional, name, description, yCounter);
		String actionUri = postAndVerify(action, parentUri, CONTENTS_NAME);

		if (predecessorUri != null) {
			addAttribute(actionUri, "predecessors", predecessorUri);
		}

		System.out.println("created action: " + actionUri);
		
		// cache URL
		urlMap.put(name, actionUri);
		
		return actionUri;
	}

	private static String createAction(String name, String parentUri, String predecessorUri, boolean optional, int yCounter) throws SpecmateException {		
		return createAction(name, parentUri, predecessorUri, optional, "<add description here>", yCounter);
	}

}
