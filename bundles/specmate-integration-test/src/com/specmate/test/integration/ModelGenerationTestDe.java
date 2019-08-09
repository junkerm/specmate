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
 * Class to test the CEG-Model generation for german sentences. The grammar of
 * the generated nodes is not correct. For example 'Wenn der Knoten ausgewählt
 * ist, ...' leads to the variable 'der Knoten' and the condition 'ausgewählt
 * ist' instead of 'ist ausgewählt'.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelGenerationTestDe extends ModelGenerationTestBase {

	public ModelGenerationTestDe() throws Exception {
		super();
	}

	/**
	 * @Test public void testModelGenerationDE01_or() {String text = "Wenn das
	 *       Werkzeug einen Fehler oder ein Problem erkennt oder der Benutzer keine
	 *       Anmeldung hat, zeigt das Werkzeug ein Warnfenster an und gibt einen
	 *       Signalton aus."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Das Werkzeug", "Erkennt einen Fehler", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Das Werkzeug", "Erkennt ein Problem",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Der Benutzer", "Hat
	 *       eine Anmeldung", NodeType.AND);CEGNode node4 = createNode(model, "Das
	 *       Werkzeug", "Zeigt ein Warnfenster an", NodeType.OR);CEGNode node5 =
	 *       createNode(model, "Das Werkzeug", "Gibt einen Signalton aus",
	 *       NodeType.OR);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node4,
	 *       false);createConnection(model, node3, node4,
	 *       true);createConnection(model, node1, node5,
	 *       false);createConnection(model, node2, node5,
	 *       false);createConnection(model, node3, node5, true);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE02_passive() {String text = "Wenn ein
	 *       Fehler erkannt wird oder der Benutzer keine Anmeldung hat, wird ein
	 *       Warnfenster angezeigt und hervorgehoben."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ein Fehler",
	 *       "Wird erkannt", NodeType.AND);CEGNode node2 = createNode(model, "Der
	 *       Benutzer", "Hat eine Anmeldung", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Ein Warnfenster", "Wird angezeigt",
	 *       NodeType.OR);CEGNode node4 = createNode(model, "Ein Warnfenster", "Wird
	 *       hervorgehoben", NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3,
	 *       true);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node4, true);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE03_and_or() {String text = "Wenn der
	 *       Benutzer keine Anmeldung hat und Anmeldepflicht besteht oder ein Fehler
	 *       erkannt wird, zeigt das Werkzeug ein Warnfenster an und gibt einen
	 *       Signalton aus."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Ein Fehler", "Wird erkannt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Der Benutzer", "Hat eine Anmeldung",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Anmeldepflicht",
	 *       "Besteht", NodeType.AND);CEGNode node4 = createNode(model, "Der
	 *       Benutzer", "Hat keine Anmeldung und Anmeldepflicht besteht",
	 *       NodeType.AND);CEGNode node5 = createNode(model, "Das Werkzeug", "Zeigt
	 *       ein Warnfenster an", NodeType.OR);CEGNode node6 = createNode(model,
	 *       "Das Werkzeug", "Gibt einen Signalton aus",
	 *       NodeType.OR);createConnection(model, node2, node4,
	 *       true);createConnection(model, node3, node4,
	 *       false);createConnection(model, node4, node5,
	 *       false);createConnection(model, node4, node6,
	 *       false);createConnection(model, node1, node5,
	 *       false);createConnection(model, node1, node6, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE04_SpecmateExample() {String text =
	 *       "Wenn der Benutzer die Option zum Anlegen eines Prozessmodells im
	 *       Abschnitt Prozessmodelle der Anforderungsübersicht wählt, wird im
	 *       Prozessmodell-Editor ein leeres Prozessmodell angezeigt.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Wählt die Option zum Anlegen eines Prozessmodells im Abschnitt
	 *       Prozessmodelle der Anforderungsübersicht", NodeType.AND);CEGNode node2
	 *       = createNode(model, "Prozessmodell-Editor", "Zeigt ein leeres
	 *       Prozessmodell an", NodeType.AND);createConnection(model, node1, node2,
	 *       false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE05_pattern1_1() {String text = "Falls
	 *       Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Specmate", "Einen
	 *       Fehler erkennt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE06_pattern1_2() {String text =
	 *       "Specmate zeigt ein Warnfenster an, falls Specmate einen Fehler
	 *       erkennt."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Einen Fehler erkennt",
	 *       NodeType.AND);createConnection(model, node2, node1, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE07_pattern1_3() {String text = "Falls
	 *       Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Specmate", "Einen
	 *       Fehler erkennt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE08_pattern2_1() {String text = "Wenn
	 *       Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Specmate", "Einen
	 *       Fehler erkennt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE09_pattern2_2() {String text =
	 *       "Specmate zeigt ein Warnfenster an, wenn Specmate einen Fehler
	 *       erkennt."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Einen Fehler erkennt",
	 *       NodeType.AND);createConnection(model, node2, node1, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE10_pattern2_3() {String text = "Wenn
	 *       Specmate einen Fehler erkennt, zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Specmate", "Einen
	 *       Fehler erkennt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE11_and_1() {String text = "Wenn der
	 *       Benutzer die Taste drückt und das Modell nicht gespeichert ist,
	 *       speichert Specmate das Modell."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Die Taste drückt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Das Modell", "Gespeichert ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, true);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE12_or_1() {String text = "Wenn der
	 *       Benutzer die Taste drückt oder das Modell nicht gespeichert ist,
	 *       speichert Specmate das Modell."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Die Taste drückt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Das Modell", "Gespeichert ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Specmate", "Speichert das Modell",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE13_or_2() {String text = "Wenn das
	 *       Modell eine Kante, einen Knoten oder einen Entscheidungsknoten enthält,
	 *       zeigt Specmate die Details rechts an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Eine Kante enthält", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Das Modell", "Einen Knoten enthält", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Das Modell", "Einen Entscheidungsknoten enthält",
	 *       NodeType.AND);CEGNode node4 = createNode(model, "Specmate", "Zeigt die
	 *       Details rechts an", NodeType.OR);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node4,
	 *       false);createConnection(model, node3, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE14_and_or_1() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt oder der Benutzer die Enter-Taste
	 *       drückt und ungespeicherte Änderungen vorhanden sind, zeigt Specmate ein
	 *       Bestätigungsfenster an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Der Benutzer", "Die Enter-Taste drückt",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Ungespeicherte
	 *       Änderungen", "Vorhanden sind", NodeType.AND);CEGNode node4 =
	 *       createNode(model, "Specmate", "Zeigt ein Bestätigungsfenser an",
	 *       NodeType.AND);CEGNode node5 = createNode(model, "Der Benutzer", "Auf
	 *       die Schaltfläche klickt oder der Benutzer auf die Enter-Taste drückt",
	 *       NodeType.OR);createConnection(model, node1, node5,
	 *       false);createConnection(model, node2, node5,
	 *       false);createConnection(model, node3, node4,
	 *       false);createConnection(model, node5, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE15_and_2() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt, speichert Specmate die Änderungen
	 *       und Specmate öffnet ein neues Fenster."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Speichert die Änderungen",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Specmate", "Öffnet ein
	 *       neues Fenster", NodeType.AND);createConnection(model, node1, node2,
	 *       false);createConnection(model, node1, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE16_and_3() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt, öffnet Specmate ein Fenster und
	 *       Specmate speichert die Änderungen und Specmate lädt die Seite neu.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Öffnet ein Fenster",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Specmate", "Speichert
	 *       die Änderungen", NodeType.AND);CEGNode node4 = createNode(model,
	 *       "Specmate", "Lädt die Seite neu", NodeType.AND);createConnection(model,
	 *       node1, node2, false);createConnection(model, node1, node3,
	 *       false);createConnection(model, node1, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE17_and_4() {String text = "Wenn der
	 *       Benutzer auf die Schaltfläche klickt, öffnet Specmate ein Fenster,
	 *       Specmate speichert die Änderungen und Specmate lädt die Seite neu.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf die Schaltfläche klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Öffnet ein Fenster",
	 *       NodeType.AND);CEGNode node3 = createNode(model, "Specmate", "Speichert
	 *       die Änderungen", NodeType.AND);CEGNode node4 = createNode(model,
	 *       "Specmate", "Lädt die Seite neu", NodeType.AND);createConnection(model,
	 *       node1, node2, false);createConnection(model, node1, node3,
	 *       false);createConnection(model, node1, node4, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE18_or_3() {
		String text = "Wenn der Benutzer auf die Schaltfläche klickt oder die Enter-Taste drückt, lädt Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Der Benutzer", "Auf die Schaltfläche klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "Der Benutzer", "Wenn drückt die Enter-Taste", NodeType.AND);
		CEGNode node3 = createNode(model, "Specmate", "Lädt das Modell", NodeType.OR);
		createConnection(model, node1, node3, false);
		createConnection(model, node2, node3, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE19_or_4() {String text = "Wenn das
	 *       Modell nur einen Knoten oder eine Kante hat, ist die
	 *       Speicherschaltfläche nicht sichtbar."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell", "Nur
	 *       einen Knoten hat", NodeType.AND);CEGNode node2 = createNode(model, "Das
	 *       Modell", "Eine Kante hat", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Die Speicherschaltfläche", "Ist nicht sichtbar",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE20_or_pronoun() {String text = "Wenn
	 *       ein Knoten oder eine Kante ausgewählt ist, kann sie gelöscht werden. ";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ein Knoten",
	 *       "Ausgewählt ist", NodeType.AND);CEGNode node2 = createNode(model, "Eine
	 *       Kante", "Ausgewählt ist", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Der Knoten oder die Kante", "Kann gelöscht werden",
	 *       NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node2, node3, false);JSONArray generated
	 *       = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE21_pronoun() {String text = "Wenn der
	 *       Benutzer auf das Modell klickt, wird es in den Editor geladen.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf das Modell klickt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Das Modell", "Wird in den Editor geladen",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE22_passiv_1() {String text = "Durch
	 *       Anklicken des Links wird der Editor gestartet."; RequirementsFactory f
	 *       = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Link", "Wird
	 *       angeklickt", NodeType.AND);CEGNode node2 = createNode(model, "Der
	 *       Editor", "Wird gestartet", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE23_passiv_not() {String text = "Wenn
	 *       ein Fehler erkannt wird oder der Benutzer keine Anmeldung hat, wird ein
	 *       Warnfenster angezeigt und ein Ton ausgegeben."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ein Fehler",
	 *       "Erkannt wird", NodeType.AND);CEGNode node2 = createNode(model, "Der
	 *       Benutzer", "Keine Anmeldung hat", NodeType.AND);CEGNode node3 =
	 *       createNode(model, "Ein Warnfenster", "Wird angezeigt",
	 *       NodeType.OR);CEGNode node4 = createNode(model, "Ein Ton", "Wird
	 *       ausgegeben", NodeType.OR);createConnection(model, node1, node3,
	 *       false);createConnection(model, node1, node4,
	 *       false);createConnection(model, node2, node3,
	 *       true);createConnection(model, node2, node4, true);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE24_passiv_2() {
		String text = "Wenn der Link durch einen Benutzer geklickt wird, öffnet Specmate ein neues Fenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Der Link", "Durch einen Benutzer geklickt wird", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "Öffnet ein neues Fenster", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE25_passiv_3() {String text = "Wenn der
	 *       Link durch einen Benutzer geklickt wird, öffnet Specmate ein neues
	 *       Fenster."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Der Benutzer", "Klickt auf den Link", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Öffnet ein neues Fenster",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE26_not_1() {
		String text = "Wenn Änderungen nicht gespeichert sind, öffnet Specmate ein Bestätigungsfenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Änderungen", "Gespeichert sind", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "Öffnet ein Bestätigungsfenster", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE27_not_2() {
		String text = "Wenn Änderungen nicht gespeichert sind, öffnet Specmate ein Bestätigungsfenster.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Änderungen", "Gespeichert sind", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "Öffnet ein Bestätigungsfenster", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	@Test
	public void testModelGenerationDE28_pattern3_1() {
		String text = "Specmate öffnet das Modell, weil der Benutzer auf den Link klickt.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Der Benutzer", "Auf den Link klickt", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "Öffnet das Modell", NodeType.AND);
		createConnection(model, node1, node2, false);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}

	/**
	 * @Test public void testModelGenerationDE29_pattern3_2() {String text = "Da der
	 *       Benutzer auf den Link klickt, öffnet Specmate das Modell.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Auf den Link klickt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Öffnet das Modell", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE30_pattern4_1() {String text = "Der
	 *       Benutzer klickt auf die Schaltfläche, aus diesem Grund öffnet Specmate
	 *       das Modell."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Der Benutzer", "Klickt auf die Schaltfläche", NodeType.AND);CEGNode
	 *       node2 = createNode(model, "Specmate", "Öffnet das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE31_pattern4_2() {String text = "Der
	 *       Nutzer gibt seine Zugangsdaten ein. Aus diesem Grund meldet Specmate
	 *       den Benutzer an."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Nutzer",
	 *       "Gibt seine Zugangsdaten ein", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Meldet den Benutzer an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE32_pattern5_1() {String text =
	 *       "Specmate zeigt das Fehlerfenster als Ergebnis ungültiger Login-Daten
	 *       an."; RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel
	 *       model = f.createCEGModel();CEGNode node1 = createNode(model, "Ungültig
	 *       Login-Daten", "Existieren", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt das Fehlerfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE33_pattern5_2() {String text = "Der
	 *       Benutzer drückt die Taste. Infolgedessen zeigt Specmate ein Fenster
	 *       an."; RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel
	 *       model = f.createCEGModel();CEGNode node1 = createNode(model, "Der
	 *       Benutzer", "Drückt die Taste", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt ein Fenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE34_pattern6_1() {String text =
	 *       "Specmate zeigt ein Warnfenster wegen ungültiger Zugangsdaten an. ";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ungültige
	 *       Zugangsdaten", "Existieren", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE35_pattern6_2() {String text =
	 *       "Aufgrund ungültiger Zugangsdaten zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ungültige
	 *       Zugangsdaten", "Existieren", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE36_pattern7_1() {String text =
	 *       "Specmate zeigt ein Warnfenster wegen ungültiger Zugangsdaten an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ungültige
	 *       Zugangsdaten", "Existieren", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE37_pattern7_2() {String text =
	 *       "Aufgrund ungültiger Zugangsdaten zeigt Specmate ein Warnfenster an.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Ungültige
	 *       Zugangsdaten", "Existieren", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Zeigt ein Warnfenster an",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE38_pattern8_1() {String text =
	 *       "Specmate speichert das Modell, sofern das Modell korrekt ist.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Korrekt ist", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, true);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE39_pattern8_2() {String text = "Sofern
	 *       das Modell korrekt ist, speichert Specmate das Modell.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Korrekt ist", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, true);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	@Test
	public void testModelGenerationDE40_not_3() {
		String text = "Sofern das Modell keine Fehler enthält, speichert Specmate das Modell.";
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel model = f.createCEGModel();
		CEGNode node1 = createNode(model, "Das Modell", "Eine Fehler enthält", NodeType.AND);
		CEGNode node2 = createNode(model, "Specmate", "Speichert das Modell", NodeType.AND);
		createConnection(model, node1, node2, true);
		JSONArray generated = generateCEGWithModelRequirementsText(text);
		checkResultingModel(generated, model);
	}
	/**
	 * @Test public void testModelGenerationDE41_pattern9() {String text = "Das
	 *       Problem hat etwas mit dem Fehler zu tun."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Fehler",
	 *       "Existiert", NodeType.AND);CEGNode node2 = createNode(model, "Das
	 *       Problem", "Tritt auf", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE42_pattern10() {String text = "Das
	 *       Problem hat viel mit dem Fehler zu tun."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Fehler",
	 *       "Existiert", NodeType.AND);CEGNode node2 = createNode(model, "Das
	 *       Problem", "Tritt auf", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE43_pattern11() {String text = "Der
	 *       Fehler tritt auf, so dass Specmate abstürzt."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Fehler",
	 *       "Tritt auf", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Abstürzt", NodeType.AND);createConnection(model, node1,
	 *       node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE44_pattern12_1() {String text = "Der
	 *       Benutzer klickt auf den Link, damit Specmate das Modell anzeigt.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Klickt auf den Link", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Das Modell anzeigt", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE45_pattern12_2() {String text = "Damit
	 *       Specmate das Modell öffnet, muss der Benutzer auf den Link klicken.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Muss auf den Limk klicken", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Das Modell öffnet",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE46_pattern13_1() {String text =
	 *       "Specmate speichert das Modell, obwohl das Modell nicht gültig ist.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Gültig ist", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, true);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE47_pattern13_2() {String text =
	 *       "Obwohl das Modell nicht gültig ist, speichert Specmate das Modell.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Gültig ist", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, true);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE48_pattern14_1() {String text =
	 *       "Specmate speichert das Modell, obwohl das Modell nicht gültig ist.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Specmate",
	 *       "Speichert das Modell", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Das Modell", "Gültig ist", NodeType.AND);createConnection(model,
	 *       node2, node1, true);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE49_pattern14_2() {String text = "Auch
	 *       wenn das Modell nicht gültig ist, speichert Specmate das Modell.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Das Modell",
	 *       "Gültig ist", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Speichert das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, true);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE50_pattern15_1() {String text =
	 *       "Specmate lädt das Modell, falls der Benutzer die Taste drückt.";
	 *       RequirementsFactory f = RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Der Benutzer",
	 *       "Die Taste drückt", NodeType.AND);CEGNode node2 = createNode(model,
	 *       "Specmate", "Lädt das Modell", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE51_pattern15_2() {String text = "Für
	 *       den Fall, dass der Benutzer die Schaltfläche drückt, lädt Specmate das
	 *       Modell."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Der Benutzer", "Die Schaltfläche drückt", NodeType.AND);CEGNode node2
	 *       = createNode(model, "Specmate", "Lädt das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE52_pattern16_1() {String text =
	 *       "Specmate lädt das Modell unter der Bedingung, dass der Benutzer die
	 *       Taste drückt."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Specmate", "Lädt das Modell", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Der Benutzer", "Die Taste drückt",
	 *       NodeType.AND);createConnection(model, node2, node1, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE53_pattern16_2() {String text = "Unter
	 *       der Bedingung, dass der Benutzer die Taste drückt, lädt Specmate das
	 *       Modell."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Der Benutzer", "Die Taste drückt", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Specmate", "Lädt das Modell",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE54_pattern17_1() {String text = "Es
	 *       wird eine lokale Version geladen, unter der Annahme, dass die
	 *       Verbindung fehlschlägt."; RequirementsFactory f =
	 *       RequirementsFactory.eINSTANCE; CEGModel model =
	 *       f.createCEGModel();CEGNode node1 = createNode(model, "Die Verbindung",
	 *       "Fehlschlägt", NodeType.AND);CEGNode node2 = createNode(model, "Eine
	 *       lokale Version", "Wird geladen", NodeType.AND);createConnection(model,
	 *       node1, node2, false);JSONArray generated =
	 *       generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */
	/**
	 * @Test public void testModelGenerationDE55_pattern17_2() {String text =
	 *       "Angenommen, die Verbindung schlägt fehl, wird eine lokale Version
	 *       geladen."; RequirementsFactory f = RequirementsFactory.eINSTANCE;
	 *       CEGModel model = f.createCEGModel();CEGNode node1 = createNode(model,
	 *       "Die Verbindung", "Schlägt fehl", NodeType.AND);CEGNode node2 =
	 *       createNode(model, "Eine lokale Version", "Wird geladen",
	 *       NodeType.AND);createConnection(model, node1, node2, false);JSONArray
	 *       generated = generateCEGWithModelRequirementsText(text);
	 *       checkResultingModel(generated, model); }
	 */

}
