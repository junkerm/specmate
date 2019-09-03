package com.specmate.test.integration;

import org.json.JSONArray;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;

/**
 * Class to test the CEG-Model generation for german sentences. *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelGenerationTestDe extends ModelGenerationTestBase {

	public ModelGenerationTestDe() throws Exception {
		super();
	}

	@Test
	public void testModelGenerationDE01_or() {
		String text = "Wenn das Werkzeug einen Fehler oder ein Problem erkennt oder der Benutzer keine Anmeldung hat, zeigt das Werkzeug ein Warnfenster an und gibt einen Signalton aus.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Werkzeug", "einen Fehler erkennt", NodeType.AND);
		CEGNode node2 = createNode(model, "das Werkzeug", "wenn ein Problem erkennt", NodeType.AND);
		CEGNode node3 = createNode(model, "der Benutzer", "eine Anmeldung hat", NodeType.AND);
		CEGNode node4 = createNode(model, "das Werkzeug", "zeigt ein Warnfenster an", NodeType.OR);
		CEGNode node5 = createNode(model, "das Werkzeug", "gibt einen Signalton aus", NodeType.OR);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, false);
		createConnection(model, node3, node4, true);
		createConnection(model, node1, node5, false);
		createConnection(model, node2, node5, false);
		createConnection(model, node3, node5, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE02_passive() {
		String text = "Wenn ein Fehler erkannt wird oder der Benutzer keine Anmeldung hat, wird ein Warnfenster angezeigt und hervorgehoben.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ein Fehler", "wird erkannt", NodeType.AND);
		CEGNode node2 = createNode(model, "der Benutzer", "hat eine Anmeldung", NodeType.AND);
		CEGNode node3 = createNode(model, "ein Warnfenster", "wird angezeigt", NodeType.OR);
		CEGNode node4 = createNode(model, "ein Warnfenster", "hervorgehoben", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, true);
		createConnection(model, node1, node4, false);
		createConnection(model, node2, node4, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE03_and_or() {String text = "Wenn der
	 *       Benutzer keine Anmeldung hat und Anmeldepflicht besteht oder ein Fehler
	 *       erkannt wird, zeigt das Werkzeug ein Warnfenster an und gibt einen
	 *       Signalton aus."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "ein Fehler", "wird erkannt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "der Benutzer", "hat eine Anmeldung",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Anmeldepflicht",
	 *       "besteht", NodeType.AND);CEGNode node4 = createNode(model, "der
	 *       Benutzer", "hat keine Anmeldung und Anmeldepflicht besteht",
	 *       NodeType.AND);CEGNode node5 = createNode(model, "das Werkzeug", "zeigt
	 *       ein Warnfenster an", NodeType.OR);CEGNode node6 = createNode(model,
	 *       "das Werkzeug", "gibt einen Signalton aus",
	 *       NodeType.OR);createConnection(model, node2, node4,
	 *       true);createConnection(model, node3, node4,
	 *       false);createConnection(model, node4, node5,
	 *       false);createConnection(model, node4, node6,
	 *       false);createConnection(model, node1, node5,
	 *       false);createConnection(model, node1, node6, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE04_SpecmateExample() {
		String text = "Wenn der Benutzer die Option zum Anlegen eines Prozessmodells im Abschnitt Prozessmodelle der Anforderungsübersicht wählt, wird im Prozessmodell-Editor ein leeres Prozessmodell angezeigt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer",
				"wählt die Option zum Anlegen eines Prozessmodells im Abschnitt Prozessmodelle der Anforderungsübersicht",
				NodeType.AND);
		CEGNode node2 = createNode(model, "ein leeres Prozessmodell ", "wird im Prozessmodell-Editor angezeigt",
				NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE05_pattern1_1() {
		String text = "Falls Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "einen Fehler erkennt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE06_pattern1_2() {
		String text = "Specmate zeigt ein Warnfenster an, falls Specmate einen Fehler erkennt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "einen Fehler erkennt", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE07_pattern2_1() {
		String text = "Wenn Specmate einen Fehler erkennt, dann zeigt Specmate ein Warnfenster an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "einen Fehler erkennt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE08_pattern2_2() {
		String text = "Wenn Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "einen Fehler erkennt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE09_pattern2_3() {
		String text = "Specmate zeigt ein Warnfenster an, wenn Specmate einen Fehler erkennt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "einen Fehler erkennt", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE10_and_1() {String text = "Wenn der
	 *       Benutzer die Taste drückt und das Modell nicht gespeichert ist,
	 *       speichert Specmate das Modell."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "die Taste drückt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "das Modell", "gespeichert ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Specmate", "speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, true);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE11_or_1() {String text = "Wenn der
	 *       Benutzer die Taste drückt oder das Modell nicht gespeichert ist,
	 *       speichert Specmate das Modell."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "die Taste drückt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "das Modell", "gespeichert ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Specmate", "speichert das Modell",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, true);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE12_or_2() {String text = "Wenn das
	 *       Modell eine Kante, einen Knoten oder einen Entscheidungsknoten enthält,
	 *       zeigt Specmate die Details rechts an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "das Modell",
	 *       "eine Kante enthält", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "das Modell", "einen Knoten enthält", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "das Modell", "einen Entscheidungsknoten enthält",
	 *       NodeType.AND);CEGNode node4 = createNode(model, "Specmate", "zeigt die
	 *       Details rechts an", NodeType.OR);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node4,
	 *       false);createConnection(model, node3, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE13_and_or_1() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt oder der Benutzer die Enter-Taste
	 *       drückt und ungespeicherte Änderungen vorhanden sind, zeigt Specmate ein
	 *       Bestätigungsfenster an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "der Benutzer", "die Enter-Taste drückt",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "ungespeicherte
	 *       Änderungen", "vorhanden sind", NodeType.AND);CEGNode node4 =
	 *       createNode(model, "Specmate", "zeigt ein Bestätigungsfenster an",
	 *       NodeType.AND);CEGNode node5 = createNode(model, "innerer Knoten 2-2",
	 *       "ist erfüllt", NodeType.OR);createConnection(model, node1, node5,
	 *       false);createConnection(model, node2, node5,
	 *       false);createConnection(model, node3, node4,
	 *       false);createConnection(model, node5, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE14_and_2() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt, speichert Specmate die Änderungen
	 *       und Specmate öffnet ein neues Fenster."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "speichert die Änderungen",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Specmate", "öffnet ein
	 *       neues Fenster", NodeType.AND);createConnection(model, node1, node2,
	 *       false);createConnection(model, node1, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE15_and_3() {
		String text = "Wenn der Benutzer auf die Schaltfläche klickt, öffnet Specmate ein Fenster und Specmate gibt einen Hinweis und Specmate lädt die Seite neu.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "auf die Schaltfläche klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet ein Fenster", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "gibt einen Hinweis", NodeType.AND);
		CEGNode node4 = createNode(model, "Specmate", "lädt die Seite neu", NodeType.AND);
		createConnection(model, node1, node2, false);
		createConnection(model, node1, node3, false);
		createConnection(model, node1, node4, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE16_and_4() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt, öffnet Specmate ein Fenster,
	 *       Specmate speichert die Änderungen und Specmate lädt die Seite neu.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "öffnet ein Fenster",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Specmate", "speichert
	 *       die Änderungen", NodeType.AND);CEGNode node4 = createNode(model,
	 *       "Specmate", "lädt die Seite neu", NodeType.AND);createConnection(model,
	 *       node1, node2, false);createConnection(model, node1, node3,
	 *       false);createConnection(model, node1, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE17_or_3() {
		String text = "Wenn der Benutzer auf die Schaltfläche klickt oder die Enter-Taste drückt, lädt Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "auf die Schaltfläche klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "der Benutzer", "wenn die Enter-Taste drückt", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "lädt das Modell", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE18_or_4() {String text = "Wenn das
	 *       Modell nur einen Knoten oder eine Kante hat, ist die
	 *       Speicherschaltfläche nicht sichtbar."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "das Modell", "nur
	 *       einen Knoten hat", NodeType.AND);CEGNode node2 = createNode(model, "das
	 *       Modell", "wenn eine Kante hat", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "die Speicherschaltfläche", "ist nicht sichtbar",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE19_or_pronoun() {String text = "Wenn
	 *       ein Knoten oder eine Kante ausgewählt ist, kann sie gelöscht werden. ";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "ein Knoten",
	 *       "ausgewählt ist", NodeType.AND);CEGNode node2 = createNode(model, "eine
	 *       Kante", "ausgewählt ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "der Knoten oder die Kante", "kann gelöscht werden",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE20_pronoun() {String text = "Wenn der
	 *       Benutzer auf das Modell klickt, wird es in den Editor geladen.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "auf das Modell klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "das Modell", "wird in den Editor geladen",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE21_passiv_1() {String text = "Durch
	 *       Anklicken des Links wird der Editor gestartet."; RequirementsFactory f
	 *       = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Link", "wird
	 *       angeklickt", NodeType.AND);CEGNode node2 = createNode(model, "der
	 *       Editor", "wird gestartet", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE22_passiv_not() {String text = "Wenn
	 *       ein Fehler erkannt wird oder der Benutzer keine Anmeldung hat, wird ein
	 *       Warnfenster angezeigt und ein Ton ausgegeben."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "ein Fehler",
	 *       "erkannt wird", NodeType.AND);CEGNode node2 = createNode(model, "der
	 *       Benutzer", "eine Anmeldung hat", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "ein Warnfenster", "wird angezeigt",
	 *       NodeType.OR);CEGNode node4 = createNode(model, "ein Ton", "wird
	 *       ausgegeben", NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node3,
	 *       true);createConnection(model, node2, node4, true);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE23_passiv_2() {
		String text = "Wenn der Link durch einen Benutzer geklickt wird, öffnet Specmate ein neues Fenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Link", "durch einen Benutzer geklickt wird", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet ein neues Fenster", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE24_passiv_3() {String text = "Wenn der
	 *       Link durch einen Benutzer geklickt wird, öffnet Specmate ein neues
	 *       Fenster."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "der Benutzer", "klickt auf den Link", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "öffnet ein neues Fenster",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE25_not_1() {
		String text = "Wenn Änderungen nicht gespeichert sind, öffnet Specmate ein Bestätigungsfenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Änderungen", "gespeichert sind", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet ein Bestätigungsfenster", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE26_not_2() {
		String text = "Da die Änderungen nicht gespeichert sind, öffnet Specmate ein Bestätigungsfenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "die Änderungen", "gespeichert sind", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet ein Bestätigungsfenster", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE27_pattern3_1() {
		String text = "Specmate öffnet das Modell, weil der Benutzer auf den Link klickt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "auf den Link klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE28_pattern3_2() {
		String text = "Da der Benutzer auf den Link klickt, öffnet Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "auf den Link klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE29_pattern4_1() {
		String text = "Der Benutzer klickt auf die Schaltfläche, aus diesem Grund öffnet Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "klickt auf die Schaltfläche", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "öffnet das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE30_pattern4_2() {String text = "Der
	 *       Nutzer gibt seine Zugangsdaten ein. Aus diesem Grund meldet Specmate
	 *       den Benutzer an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Nutzer",
	 *       "gibt seine Zugangsdaten ein", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "meldet den Benutzer an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE31_pattern5_1() {
		String text = "Specmate zeigt das Fehlerfenster als Ergebnis ungültiger Login-Daten an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ungültiger Login-Daten", "", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt das Fehlerfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE32_pattern5_2() {String text = "Der
	 *       Benutzer drückt die Taste. Infolgedessen zeigt Specmate ein Fenster
	 *       an."; RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel
	 *       model = f.createCEGModel();CEGNode node1 = createNode(model, "der
	 *       Benutzer", "drückt die Taste", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "zeigt ein Fenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE33_pattern6_1() {
		String text = "Specmate zeigt ein Warnfenster wegen ungültiger Zugangsdaten an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ungültiger Zugangsdaten", "", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE34_pattern6_2() {
		String text = "Wegen ungültiger Zugangsdaten zeigt Specmate ein Warnfenster an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ungültiger Zugangsdaten", "", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE35_pattern7_1() {
		String text = "Specmate zeigt ein Warnfenster aufgrund ungültiger Zugangsdaten an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ungültiger Zugangsdaten", "", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE36_pattern7_2() {
		String text = "Aufgrund ungültiger Zugangsdaten zeigt Specmate ein Warnfenster an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ungültiger Zugangsdaten", "", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt ein Warnfenster an", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE37_pattern8_1() {
		String text = "Specmate speichert das Modell, sofern das Modell korrekt ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "korrekt ist", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE38_pattern8_2() {
		String text = "Sofern das Modell korrekt ist, speichert Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "korrekt ist", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE39_not_3() {
		String text = "Sofern das Modell keine Fehler enthält, speichert Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "eine Fehler enthält", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE40_pattern9() {
		String text = "Das Problem hat etwas mit der Verbindung zu tun.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Verbindung", "", NodeType.AND);
		CEGNode node2 = createNode(model, "das Problem", "", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE41_pattern10() {
		String text = "Das Problem hat viel mit dem Fehler zu tun.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "dem Fehler", "", NodeType.AND);
		CEGNode node2 = createNode(model, "das Problem", "", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE42_pattern11() {
		String text = "Der Fehler tritt auf, so dass Specmate abstürzt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Fehler", "tritt auf", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "abstürzt", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE43_pattern12_1() {
		String text = "Der Benutzer muss sein Passwort eingeben, damit Specmate das Modell anzeigt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "muss sein Passwort eingeben", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "das Modell anzeigt", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE44_pattern12_2() {String text = "Damit
	 *       Specmate das Modell öffnet, muss der Benutzer sein Passwort eingeben.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "der Benutzer",
	 *       "muss sein Passwort eingeben", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "das Modell öffnet",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE45_pattern13_1() {
		String text = "Specmate zeigt einen Fehler, obwohl das Modell gültig ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "gültig ist", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "zeigt einen Fehler", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE46_pattern13_2() {
		String text = "Obwohl das Modell nicht gültig ist, speichert Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "gültig ist", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE47_pattern14_1() {
		String text = "Specmate speichert das Modell, obwohl das Modell nicht gültig ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		CEGNode node2 = createNode(model, "das Modell", "gültig ist", NodeType.AND);
		createConnection(model, node2, node1, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE48_pattern14_2() {
		String text = "Auch wenn das Modell nicht gültig ist, speichert Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "gültig ist", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE49_pattern15_1() {
		String text = "Specmate lädt das Modell, sofern der Benutzer die Taste drückt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "die Taste drückt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "lädt das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE50_pattern15_2() {
		String text = "Für den Fall, dass der Benutzer die Schaltfläche drückt, lädt Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "die Schaltfläche drückt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "lädt das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE51_pattern16_1() {
		String text = "Specmate lädt das Modell unter der Bedingung, dass der Benutzer die Taste drückt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "lädt das Modell", NodeType.AND);
		CEGNode node2 = createNode(model, "der Benutzer", "die Taste drückt", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE52_pattern16_2() {
		String text = "Unter der Bedingung, dass der Benutzer die Taste drückt, lädt Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "die Taste drückt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "lädt das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE53_pattern17_1() {String text = "Es
	 *       wird eine lokale Version geladen, unter der Annahme, dass die
	 *       Verbindung fehlschlägt."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "die Verbindung",
	 *       "fehlschlägt", NodeType.AND);CEGNode node2 = createNode(model, "eine
	 *       lokale Version", "wird geladen", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE54_pattern17_2() {String text =
	 *       "Angenommen, die Verbindung schlägt fehl, wird eine lokale Version
	 *       geladen."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "die Verbindung", "schlägt fehl", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "eine lokale Version", "wird geladen",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE55_pattern17_2() {String text = "Der
	 *       Nutzer muss auf die Schaltfläche klicken, damit das Tool die Änderungen
	 *       übernimmt."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "der Nutzer", "muss auf die Schaltfläche klicken",
	 *       NodeType.AND);CEGNode node2 = createNode(model, "das Tool", "die
	 *       Änderungen übernimmt", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE56_obgleich() {
		String text = "Die Datei wird gespeichert, obgleich die Datei nicht richtig formatiert ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "die Datei", "wird gespeichert", NodeType.AND);
		CEGNode node2 = createNode(model, "die Datei", "richtig formatiert ist", NodeType.AND);
		createConnection(model, node2, node1, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE57_obschon() {
		String text = "Das Modell wird angezeigt, obschon die Datei nicht richtig formatiert ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "wird angezeigt", NodeType.AND);
		CEGNode node2 = createNode(model, "die Datei", "richtig formatiert ist", NodeType.AND);
		createConnection(model, node2, node1, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE58_obzwar() {
		String text = "Specmate zeigt das Modell an, obzwar die Datei nicht richtig formatiert ist.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "zeigt an das Modell", NodeType.AND);
		CEGNode node2 = createNode(model, "die Datei", "richtig formatiert ist", NodeType.AND);
		createConnection(model, node2, node1, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE59_wenngleich() {
		String text = "Das Tool speichert die Datei, wenngleich die Datei Fehler enthält.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Tool", "speichert die Datei", NodeType.AND);
		CEGNode node2 = createNode(model, "die Datei", "Fehler enthält", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE60_wiewohl() {
		String text = "Die Berechnung ist korrekt, wiewohl der Beweis schwierig nachzuvollziehen war.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "die Berechnung", "ist korrekt", NodeType.AND);
		CEGNode node2 = createNode(model, "der Beweis", "schwierig nachzuvollziehen war", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE61_voraussetzung_1() {
		String text = "Unter der Voraussetzung, dass das Modell korrekt ist, speichert das Tool das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "korrekt ist", NodeType.AND);
		CEGNode node2 = createNode(model, "das Tool", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE62_voraussetzung_2() {
		String text = "Das Tool speichert das Modell, unter der Voraussetzung, dass das Modell korrekt ist. ";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "korrekt ist", NodeType.AND);
		CEGNode node2 = createNode(model, "das Tool", "speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE63_weswegen() {
		String text = "Das Modell enthält einen Fehler, weswegen Specmate einen Hinweis anzeigt. ";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "das Modell", "enthält einen Fehler", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "einen Hinweis anzeigt", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE64_zurFolge_1() {String text = "Ein
	 *       Klick auf die Schaltfläche hat einen Backend-Aufruf zur Folge.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "ein Klick", "auf
	 *       die Schaltfläche", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "einen Backend-Aufruf", "", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE65_zurFolge_2() {
		String text = "Ein Klick auf die Schaltfläche hat zur Folge, dass ein Backend-Aufruf ausgeführt wird.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "ein Klick auf die Schaltfläche", "", NodeType.AND);
		CEGNode node2 = createNode(model, "ein Backend-Aufruf", "ausgeführt wird", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE66_gesetzt_1() {
		String text = "Gesetzt den Fall, dass die Validierung erfolgreich ist, wird die Speicher-Schaltfläche aktiviert.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "die Validierung", "erfolgreich ist", NodeType.AND);
		CEGNode node2 = createNode(model, "die Speicher-Schaltfläche", "wird aktiviert", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE67_so() {
		String text = "Klickt der Benutzer auf die Schaltfläche so wird das Modell gespeichert.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "der Benutzer", "klickt auf die Schaltfläche", NodeType.AND);
		CEGNode node2 = createNode(model, "das Modell", "wird gespeichert", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE68_infolge() {
		String text = "Infolge einer falschen Eingabe wird ein Hinweis angezeigt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "einer falschen Eingabe", "", NodeType.AND);
		CEGNode node2 = createNode(model, "ein Hinweis", "wird angezeigt", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE69_aufgrund() {String text = "Ein
	 *       Fehler tritt auf, aufgrund dessen Specmate abstürzt.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "ein Fehler",
	 *       "tritt auf", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "abstürzt", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE70_nachdem_1() {
		String text = "Specmate zeigt das Modell an, nachdem der Nutzer den Link angeklickt hat.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "zeigt das Modell an", NodeType.AND);
		CEGNode node2 = createNode(model, "der Nutzer ", "den Link angeklickt hat", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE71_nachdem_2() {
		String text = "Nachdem der Nutzer den Link angeklickt hat, zeigt Specmate das Modell an.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Specmate", "zeigt das Modell an", NodeType.AND);
		CEGNode node2 = createNode(model, "der Nutzer ", "nachdem angeklickt hat den Link", NodeType.AND);
		createConnection(model, node2, node1, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

}
