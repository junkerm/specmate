package com.allianz.view.test;

import javax.ws.rs.core.Variant;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.SpecmateException;
import com.specmate.model.basemodel.BasemodelPackage;
import com.specmate.persistency.event.EChangeKind;


public class BlueprintEmfRestTest extends EmfRestTestBase {

	//private static final String REFINEMENTS_NAME = BlueprintPackage.Literals.ACTION__REFINEMENTS.getName();
	private static final String BP_VIEW_KEY = "com.allianz.views.blueprint";
	
	/* package */ static final String BASEMODEL_NODE_NAME = BasemodelPackage.Literals.BASE_MODEL_NODE.getName();
	/* package */ static final String VARIANTS_NAME = BlueprintPackage.Literals.BLUEPRINT__VARIANTS.getName();
	/* package */ static final String SUCC_NAME = BlueprintPackage.Literals.BLUE_PRINT_ELEMENT_BASE__SUCCESSORS.getName();
	/* package */ static final String PRED_NAME = BlueprintPackage.Literals.BLUE_PRINT_ELEMENT_BASE__PREDECESSORS.getName();
	
	
	private static int yCounter=0;
	

	
	/* package */ static Blueprint getTestBlueprint() {
		return getTestBlueprint("Blueprint " + (counter++));
	}
	
	/* package */ static Blueprint getTestBlueprint(String name) {
		Blueprint blueprint = BlueprintFactory.eINSTANCE.createBlueprint();
		blueprint.setName(name);
		blueprint.setDescription("Blueprint Description" + (counter++));
		blueprint.setId("ID"+ (counter++));
		return blueprint;
	}
	
	/* package */ static Action getTestAction(boolean optional) {
		return getTestAction(optional, "Action " + (counter++));
	}
	
	/* package */ static Action getTestAction(boolean optional, String actionName, int myYCounter) {
		return getTestAction(optional, "Action " + (counter++), "Action Description" + (counter++), myYCounter);		
	}
	
	/* package */ static Action getTestAction(boolean optional, String actionName, String description, int myYCounter) {
		Action action = BlueprintFactory.eINSTANCE.createAction();
		action.setOptional(optional);
		action.setName(actionName);
		action.setDescription(description);
		action.setId("ID"+ (counter++));
		action.setX(200);
		action.setY(200+100*(myYCounter));
		return action;
	}

	
//	private static BlueprintToTestRefinement getTestBlueprintToTestRefinement() {
//		BlueprintToTestRefinement refinement = TestFactory.eINSTANCE.createBlueprintToTestRefinement();
//		refinement.setName("Refinement " + (counter++));
//		refinement.setDescription("Description " + (counter++));
//		refinement.setId("ID"+(counter++));
//		return refinement;
//	}
	
	/* package */ static Action getTestAction(boolean optional, String actionName) {
		return  getTestAction(optional, actionName, yCounter++);
	}
	
	/* package */ static Action getTestAction(){
		return getTestAction(false, "Action " + (counter++));
	}
	
	/* package */ static Decision getTestDecision() {
		Decision decision = BlueprintFactory.eINSTANCE.createDecision();
		decision.setName("Decision" + (counter++));
		decision.setDescription("Decision Description" + (counter++));
		decision.setId("ID"+ (counter++));
		return decision;
	}
	
	/* package */ static Condition getTestCondition() {
		Condition condition = BlueprintFactory.eINSTANCE.createCondition();
		condition.setName("Condition" + (counter++));
		condition.setDescription("Condition Description" + (counter++));
		condition.setId("ID"+ (counter++));
		return condition;
	}
	
	/* package */ static Variant getTestVariant(){
		Variant variant = BlueprintFactory.eINSTANCE.createVariant();
		variant.setName("Variant" + (counter++));
		variant.setDescription("Variant Description" + (counter++));
		variant.setId("ID"+ (counter++));
		return variant;
	}
	

	/* package */ static String createAndPostBlueprintToUrl(String url) throws SpecmateException {
		return createAndPostBlueprintToUrl(url, "Name " + (counter++));
	}

	
	/* package */ static String createAndPostBlueprintToUrl(String url, String blueprintName) throws SpecmateException {
		Blueprint blueprint = getTestBlueprint(blueprintName);
		return postBlueprintToUrl(url, blueprint);
	}
	
	
	/* package */ static String postBlueprintToUrl(String url, Blueprint blueprint) throws SpecmateException {
		String blueprintUrl = postAndVerify(blueprint, url, CONTENTS_NAME);
		return blueprintUrl;
	}

	/* package */ static String createAndPostActionToUrl(String url, boolean optional) throws SpecmateException {
		Action action = getTestAction(optional);
		String actionUrl = postAndVerify(action,url, CONTENTS_NAME);
		return actionUrl;
	}
	
	/* package */ static String postActionToUrl(String url, Action action) throws SpecmateException {
		String actionUrl = postAndVerify(action,url, CONTENTS_NAME);
		return actionUrl;
	}
	
//	/*package */ static String createAndPostBlueprintToTestRefinement(String actionUrl) throws SpecmateException {
//		BlueprintToTestRefinement refinement = getTestBlueprintToTestRefinement();
//		String refinementUrl = postAndVerify(refinement, actionUrl, REFINEMENTS_NAME);
//		return refinementUrl;
//	}


	/* package */ static String createAndPostDecisionToUrl(String url) throws SpecmateException {
		Decision decision = getTestDecision();
		return postDecisionToUrl(url, decision);
	}
	
	/* package */ static String postDecisionToUrl(String url, Decision decision) throws SpecmateException {
		String decisionUrl = postAndVerify(decision,url, CONTENTS_NAME);
		return decisionUrl;
	}
	
	/* package */ static String createAndPostConditionToUrl(String url) throws SpecmateException {
		Condition condition = getTestCondition();
		return postConditionToUrl(url, condition);
	}
	
	/* package */ static String postConditionToUrl(String url, Condition condition) throws SpecmateException {
		String conditionUrl = postAndVerify(condition,url, CONTENTS_NAME);
		return conditionUrl;
	}
	
	/* package */ static String createAndPostVariantToUrl(String url) throws SpecmateException {
		Variant variant = getTestVariant();
		return postVariantToUrl(url, variant);
	}
	
	/* package */ static String postVariantToUrl(String url, Variant variant) throws SpecmateException {
		String variantUrl = postAndVerify(variant,url, VARIANTS_NAME);
		return variantUrl;
	}
	

	/* package */ static void connectAndDisconnectSuccActions(String folderUrl) throws SpecmateException {
		Action action1 = getTestAction();
		Action action2 = getTestAction();
		String actionUrl1=postAndVerify(action1,folderUrl, CONTENTS_NAME);
		String actionUrl2=postAndVerify(action2,folderUrl, CONTENTS_NAME);
		
		JSONObject retrieved1 = get(actionUrl1);
		JSONObject proxy = proxy(actionUrl2);
		
		add(retrieved1,PRED_NAME,proxy);
		putAndVerify(retrieved1,actionUrl1,expectEventWithValue(EChangeKind.ADD.toString(), proxy,false));
		
		remove(retrieved1,PRED_NAME,proxy);
		putAndVerify(retrieved1,actionUrl1,expectEventWithUrl(EChangeKind.CLEAR.toString(),actionUrl1));
	}


	/* package */ static void connectAndDisconnectPredActions(String folderUrl) throws SpecmateException {
		Action action1 = getTestAction();
		Action action2 = getTestAction();
		String actionUrl1=postAndVerify(action1,folderUrl, CONTENTS_NAME);
		String actionUrl2=postAndVerify(action2,folderUrl, CONTENTS_NAME);
		
		JSONObject retrieved1 = get(actionUrl1);
		JSONObject proxy = proxy(actionUrl2);
		
		add(retrieved1,PRED_NAME,proxy);
		putAndVerify(retrieved1,actionUrl1,expectEventWithValue(EChangeKind.ADD.toString(), proxy,false));
		
		remove(retrieved1,PRED_NAME,proxy);
		putAndVerify(retrieved1,actionUrl1,expectEventWithUrl(EChangeKind.CLEAR.toString(),actionUrl1));
	}
	
	/* package */ static void connectAndDisconnectDecisionToAction(String blueprintUrl) throws SpecmateException {
		Action action1 = getTestAction();
		Decision decision = getTestDecision();
		String actionUrl=postAndVerify(action1,blueprintUrl, CONTENTS_NAME);
		String decisionUrl=postAndVerify(decision,blueprintUrl, CONTENTS_NAME);
		
		JSONObject retrievedAction = get(actionUrl);
		JSONObject proxy = proxy(decisionUrl);
		
		add(retrievedAction,SUCC_NAME,proxy);
		putAndVerify(retrievedAction,actionUrl,expectEventWithValue(EChangeKind.ADD.toString(), proxy,false));
		
		remove(retrievedAction,SUCC_NAME,proxy);
		putAndVerify(retrievedAction,actionUrl,expectEventWithUrl(EChangeKind.CLEAR.toString(),actionUrl));
	}
	
	/* package */ static void connectAndDisconnectConditionToDecision(String blueprintUrl) throws SpecmateException {
		Decision decision = getTestDecision();
		Condition condition = getTestCondition();
		String decisionUrl=postAndVerify(decision,blueprintUrl, CONTENTS_NAME);
		String conditionUrl=postAndVerify(condition,blueprintUrl, CONTENTS_NAME);
		
		JSONObject retrievedDecision = get(decisionUrl);
		JSONObject proxy = proxy(conditionUrl);
		
		add(retrievedDecision,SUCC_NAME,proxy);
		putAndVerify(retrievedDecision,decisionUrl,expectEventWithValue(EChangeKind.ADD.toString(), proxy,false));
		
		remove(retrievedDecision,SUCC_NAME,proxy);
		putAndVerify(retrievedDecision,decisionUrl,expectEventWithUrl(EChangeKind.CLEAR.toString(),decisionUrl));
	}


	
	/* package */ static String createAndPostBlueprintToFolder() throws  SpecmateException {
		String folderUri = createAndPostFolderToProject();
		return createAndPostBlueprintToUrl(folderUri);
	}
	
	
	/* package */ static String postBlueprintToFolder(Blueprint blueprint) throws  SpecmateException {
		String folderUri = createAndPostFolderToProject();
		return postBlueprintToUrl(folderUri, blueprint);
	}
	

	/*package*/ static String createAndPostActionToBlueprint(boolean optional) throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return createAndPostActionToUrl(blueprintUri,optional);
	}
	
	/*package*/ static String createAndPostActionToBlueprint(Action action) throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return postActionToUrl(blueprintUri,action);
	}
	
	/* package */ static String createAndPostDecisionToBlueprint() throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return createAndPostDecisionToUrl(blueprintUri);
	}
	
	/* package */ static String postDecisionToBlueprint(Decision decision) throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return postDecisionToUrl(blueprintUri, decision);
	}
	
	/* package */ static String createAndPostConditionToBlueprint() throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return createAndPostConditionToUrl(blueprintUri);
	}

	/* package */ static String postConditionToBlueprint(Condition condition) throws SpecmateException {
		String blueprintUri = createAndPostBlueprintToFolder();
		return postConditionToUrl(blueprintUri,condition);
	}

	/* package */ static String createAndPostVariantToBlueprint() throws SpecmateException {
		String blueprintUrl = createAndPostBlueprintToFolder();
		return createAndPostVariantToUrl(blueprintUrl);
	}
	
	/* package */ static String postVariantToBlueprint(Variant variant) throws SpecmateException {
		String blueprintUrl = createAndPostBlueprintToFolder();
		return postVariantToUrl(blueprintUrl,variant);
	}


	@Test
	public void testPostBlueprint() throws Exception {
		createAndPostBlueprintToFolder();
	}
	
	@Test
	public void testDeleteBlueprint() throws Exception {
		String url = createAndPostBlueprintToFolder();
		deleteAndVerify(url);
	}
	
	@Test
	public void updateBlueprint() throws Exception {
		Blueprint blueprint = getTestBlueprint();
		String url = postBlueprintToFolder(blueprint);
		String id = blueprint.getId();
		Blueprint blueprintUpdate = getTestBlueprint();
		blueprintUpdate.setId(id);
		putAndVerify(blueprintUpdate, url, expectEventWithUrl(EChangeKind.SET.toString(), url));
	}
	
	@Test
	public void testDeleteBlueprintFolderWithBlueprint() throws Exception {
		String folderUri = createAndPostFolderToProject();
		createAndPostBlueprintToUrl(folderUri);
		deleteAndVerify(folderUri);
	}
	
	@Test
	public void testPostAction() throws Exception {
		createAndPostActionToBlueprint(false);
	}
	
	@Test
	public void testDeleteAction() throws Exception {
		String url = createAndPostActionToBlueprint(false);
		deleteAndVerify(url);
	}
	
	@Test
	public void testUpdateAction() throws Exception {
		Action testAction = getTestAction(false);
		String url = createAndPostActionToBlueprint(testAction);

		Action update = getTestAction(false);
		update.setId(testAction.getId());
		putAndVerify(update, url, expectEventWithUrl(EChangeKind.SET.toString(), url));
	}
	
	@Test
	public void testPostDecision() throws Exception {
		createAndPostDecisionToBlueprint();
	}
	
	@Test
	public void testDeleteDecision() throws Exception {
		String url = createAndPostDecisionToBlueprint();
		deleteAndVerify(url);
	}
	
	@Test
	public void testUpdateDecision() throws Exception {
		Decision decision = getTestDecision();
		String url = postDecisionToBlueprint(decision);
		Decision update = getTestDecision();
		update.setId(decision.getId());
		putAndVerify(update, url, expectEventWithUrl(EChangeKind.SET.toString(), url));
	}
	
	
	@Test 
	public void testConnectDecisionToAction() throws Exception {
		String blueprintUrl = createAndPostBlueprintToFolder();
		connectAndDisconnectDecisionToAction(blueprintUrl);	
	}
	
	@Test 
	public void testConnectConditionToDecision() throws Exception {
		String blueprintUrl = createAndPostBlueprintToFolder();
		connectAndDisconnectConditionToDecision(blueprintUrl);	
	}
	
	@Test
	public void testPostCondition() throws Exception {
		createAndPostConditionToBlueprint();
	}
	
	@Test
	public void testDeleteCondition() throws Exception {
		String url = createAndPostConditionToBlueprint();
		deleteAndVerify(url);
	}
	
	@Test
	public void testUpdateCondition() throws Exception {
		Condition condition = getTestCondition();
		String url = postConditionToBlueprint(condition);
		Condition update = getTestCondition();
		update.setId(condition.getId());
		putAndVerify(update, url, expectEventWithUrl(EChangeKind.SET.toString(), url));
	}
	
	@Test
	public void testPostOptionalAction() throws Exception {
		createAndPostActionToBlueprint(true);
	}

	@Test 
	public void testUpdateSuccessorAction() throws Exception {
		String blueprintUrl = createAndPostBlueprintToFolder();
		connectAndDisconnectSuccActions(blueprintUrl);	
	}
		
	@Test 
	public void testUpdatePredecessorAction() throws Exception {
		String blueprintUrl = createAndPostBlueprintToFolder();
		connectAndDisconnectPredActions(blueprintUrl);	
	}
	
	@Test
	public void testDeleteBlueprintWithConnectedActions() throws Exception {
		String blueprintUrl = createAndPostBlueprintToFolder();
		connectAndDisconnectSuccActions(blueprintUrl);	
		deleteAndVerify(blueprintUrl);
	}
	

	@Test
	public void testPostVariant() throws Exception{
		createAndPostVariantToBlueprint();
	}

	@Test
	public void testReferenceVariant() throws Exception{
		String blueprintUrl = createAndPostBlueprintToFolder();
		String variantUrl = createAndPostVariantToUrl(blueprintUrl);
		Action action1 = getTestAction();
		String actionUrl1=postAndVerify(action1,blueprintUrl, CONTENTS_NAME);
		JSONObject retrievedAction = get(actionUrl1);
		JSONObject proxy = proxy(variantUrl);
		add(retrievedAction,VARIANTS_NAME,proxy);
		putAndVerify(retrievedAction,actionUrl1,expectEventWithValue(EChangeKind.ADD.toString(), proxy,false));
//		
//		retrievedAction  = get(actionUrl1);
//		Assert.assertEquals(1,retrievedAction.getJSONArray(VARIANTS_NAME).length());
//		Assert.assertTrue(compare(proxy,retrievedAction.getJSONArray(VARIANTS_NAME).getJSONObject(0),false));
	}
	
	@Test
	public void testDeleteVariant() throws Exception{
		String blueprintUrl = createAndPostBlueprintToFolder();
		String variantUrl = createAndPostVariantToUrl(blueprintUrl);
		Action action1 = getTestAction();
		String actionUrl1=postAndVerify(action1,blueprintUrl, CONTENTS_NAME);
		JSONObject retrievedAction = get(actionUrl1);
		add(retrievedAction,VARIANTS_NAME,proxy(variantUrl));
		putAndVerify(retrievedAction,actionUrl1,expectEventWithValue(EChangeKind.ADD.toString(), proxy(variantUrl),false));
		
		deleteAndVerify(variantUrl);
		JSONObject action1Json = get(actionUrl1);
		Assert.assertEquals(0,action1Json.getJSONArray(VARIANTS_NAME).length());
	}
	
	@Test
	public void testUpdateVariant() throws Exception {
		Variant variant = getTestVariant();
		String url = postVariantToBlueprint(variant);
		Variant update = getTestVariant();
		update.setId(variant.getId());
		putAndVerify(update, url, expectEventWithUrl(EChangeKind.SET.toString(), url));
	}
	
//	@Test
//	public void testCreateBlueprintToTestRefinement() throws Exception {
//		String url = createAndPostActionToBlueprint(false);
//		createAndPostBlueprintToTestRefinement(url);
//	}
	//TODO: Delete / Modify Refinement

	
	
	//TODO: test blueprint startElement

}
