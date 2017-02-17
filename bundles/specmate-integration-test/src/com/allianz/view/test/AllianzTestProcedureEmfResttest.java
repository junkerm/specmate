// package com.allianz.view.test;
//
// import javax.ws.rs.core.Response.Status;
//
// import org.json.JSONObject;
// import org.junit.Assert;
// import org.junit.Test;
//
// import com.specmate.common.SpecmateException;
//
// public class AllianzTestProcedureEmfResttest extends EmfRestTest {
//
// private static final String TEST_VIEW_KEY = "com.allianz.views.testview";
//
// /**
// * Gets the uri for an test procedure element based on the uri of its test
// * counterpart.
// */
// public static String getTestProcedureUriFromTestUri(String testFolderUri) {
// String testProcedureFolderUri =
// testFolderUri.replace("com_allianz_views_testview",
// "com_allianz_views_testproceduresview");
// return testProcedureFolderUri;
// }
//
// /**
// * Verifies if a test folder has correctly been replicated as test procedure
// * folder.
// *
// * @param testFolderUri
// * uri of (source) test folder
// */
// private static void getAndVerifyTestProcedureFolder(String testFolderUri) {
// JSONObject jsonfolder = get(testFolderUri);
//
// JSONObject jsonprocedurefolder =
// get(getTestProcedureUriFromTestUri(testFolderUri));
//
// Assert.assertEquals("ui does not match",
// jsonfolder.getString(URL_KEY).replace("com_allianz_views_testview",
// "com_allianz_views_testproceduresview"),
// jsonprocedurefolder.getString(URL_KEY));
//
// Assert.assertEquals("name does not match", jsonfolder.getString("name"),
// jsonprocedurefolder.getString("name"));
//
// Assert.assertEquals("description does not match",
// jsonfolder.getString("description"),
// jsonprocedurefolder.getString("description"));
// }
//
// /**
// * Verifies if a test procedure element has correctly been created based on
// * its test specification counter part.
// *
// * @param testSpecificationUri
// * uri of (source) test specification.
// */
// private static void getAndVerifyTestProcedure(String testSpecificationUri) {
// JSONObject jsonTestSpecification = get(testSpecificationUri);
//
// String testProcedureUri =
// getTestProcedureUriFromTestUri(testSpecificationUri);
// JSONObject jsonTestProcedure = get(testProcedureUri);
//
// Assert.assertEquals("ui does not match",
// jsonTestSpecification.getString(URL_KEY)
// .replace("com_allianz_views_testview",
// "com_allianz_views_testproceduresview"),
// jsonTestProcedure.getString(URL_KEY));
//
// Assert.assertEquals("name does not match",
// jsonTestSpecification.getString("name"),
// jsonTestProcedure.getString("name"));
// Assert.assertEquals("description does not match",
// jsonTestSpecification.getString("description"),
// jsonTestProcedure.getString("description"));
// }
//
// @Test
// public void testPostAllianzTestFolderAndVerifyTestProcedureFolder() throws
// Exception {
// String testFolderUri =
// AllianzTestEmfResttest.createAndPostAllianzTestFolderToProject();
// getAndVerifyTestProcedureFolder(testFolderUri);
// }
//
// @Test
// public void testUpdateAllianzTestFolderAndVerifyTestProcedureFolder() throws
// Exception {
// // String url = createAndPostAllianzTestFolderToProject();
// // String id = url.substring(url.lastIndexOf("/") + 1);
// // TestFolder folder = getTestAllianzTestFolder();
// // folder.setId(id);
// // putAndVerify(folder, url,
// // expectEventWithUrl(EChangeKind.SET.toString(), url));
// //
// // getAndVerifyTestProcedureFolder(url);
//
// Assert.fail("update not implemented yet");
// }
//
// @Test
// public void testDeleteAllianzTestFolderAndTestProcedureFolder() throws
// Exception {
// String testFolderUri =
// AllianzTestEmfResttest.createAndPostAllianzTestFolderToProject();
// AllianzTestEmfResttest.deleteAndVerify(testFolderUri);
//
// get(getTestProcedureUriFromTestUri(testFolderUri), Status.NOT_FOUND);
// }
//
// @Test
// public void testPostTestSpecificationAndVerifyTestProcedure() throws
// Exception {
// String testSpecificationUri =
// AllianzTestEmfResttest.createAndPostTestSpecificationToProject();
// getAndVerifyTestProcedure(testSpecificationUri);
// }
//
// @Test
// public void testUpdateTestSpecificationAndVerifyTestProcedure() throws
// Exception {
// Assert.fail("update not implemented yet");
// }
//
// @Test
// public void testDeleteTestSpecificationAndVerifyTestProcedure() throws
// Exception {
// Assert.fail("delete not implemented yet");
// }
//
// @Test
// public void testPostTestSliceAndVerifySliceProcedure() throws Exception {
//
// // create project
// String bp_projectUrl = BlueprintEmfRestTest.createAndPostProject();
// System.out.println("created project: " + bp_projectUrl);
//
// // create blueprint
// String bp_blueprintFolderUrl =
// BlueprintEmfRestTest.createAndPostBaseModelNodeToUrl(bp_projectUrl);
// System.out.println("created blue print folder: " + bp_blueprintFolderUrl);
//
// String bp_blueprintUri =
// BlueprintEmfRestTest.createAndPostBlueprintToUrl(bp_blueprintFolderUrl);
// System.out.println("created blue print: " + bp_blueprintUri);
//
// // add blueprint action to blueprint
// Action bp_action1 = BlueprintEmfRestTest.getTestAction();
// String bp_action1Url = postAndVerify(bp_action1, bp_blueprintUri,
// CONTENTS_NAME);
// System.out.println("created action: " + bp_action1Url);
//
// // add blueprint action to blueprint
// Action bp_action2 = BlueprintEmfRestTest.getTestAction();
// String bp_action2Url = postAndVerify(bp_action2, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action2Url, "predecessors", bp_action1Url);
// System.out.println("created action: " + bp_action2Url);
//
// // add blueprint action to blueprint
// Action bp_action3 = BlueprintEmfRestTest.getTestAction();
// String bp_action3Url = postAndVerify(bp_action3, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action3Url, "predecessors", bp_action2Url);
// System.out.println("created action: " + bp_action3Url);
//
// // add blueprint action to blueprint
// Action bp_action4 = BlueprintEmfRestTest.getTestAction();
// String bp_action4Url = postAndVerify(bp_action4, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action4Url, "predecessors", bp_action3Url);
// System.out.println("created action: " + bp_action4Url);
//
// // add blueprint action to blueprint
// Action bp_action5 = BlueprintEmfRestTest.getTestAction();
// String bp_action5Url = postAndVerify(bp_action5, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action5Url, "predecessors", bp_action4Url);
// System.out.println("created action: " + bp_action5Url);
//
// // translate BP-URI -> TestSpec-URI
// String ts_projectUri =
// translateBlueprintUriToTestSpecificationUri(bp_projectUrl);
//
// // create test spec
// TestSpecification test = AllianzTestEmfResttest.getTestSpecification();
// String ts_specUrl = postAndVerify(test, ts_projectUri, CONTENTS_NAME);
// System.out.println("created test specification: " + ts_specUrl);
//
// // add slice to test spec
// Slice slice = AllianzTestEmfResttest.createTestSlice();
// String ts_sliceUri = postAndVerify(slice,ts_specUrl,
// AllianzTestEmfResttest.TESTPROCEDURE_NAME);
// System.out.println("created test slice: " + ts_sliceUri);
//
// // get test procedure object
// String tp_sliceUri =
// getTestProcedureSliceUriFromTestSpecificationSliceUri(ts_sliceUri);
// JSONObject tp_jsonTestProcedureSlice = get(tp_sliceUri);
//
// setAttribute(ts_sliceUri, "from", bp_action2Url);
// System.out.println("set 'from' of slice to action");
//
// setAttribute(ts_sliceUri, "to", bp_action4Url);
// System.out.println("set 'to' of slice to action");
//
//
//
// setAttribute(ts_sliceUri, "to", bp_action5Url);
// System.out.println("set 'to' of slice to action");
//
// //Thread.sleep(Long.MAX_VALUE);
// Assert.fail("unrolling of test steps is not implemented yet");
//
// // TODO verify slice procedure
// }
//
// @Test
// public void testDEMO() throws Exception {
//
// // create project
// String bp_projectUrl = BlueprintEmfRestTest.createAndPostProject("ROOT");
// System.out.println("created project: " + bp_projectUrl);
//
// // create blueprint folder
// String bp_blueprintFolderUrl =
// BlueprintEmfRestTest.createAndPostBaseModelNodeToUrl(bp_projectUrl,
// "Blueprints");
// System.out.println("created blue print folder: " + bp_blueprintFolderUrl);
//
// // create blueprint
// String bp_blueprintUri =
// BlueprintEmfRestTest.createAndPostBlueprintToUrl(bp_blueprintFolderUrl, "SAD
// Blueprint");
// System.out.println("created blue print: " + bp_blueprintUri);
//
// // add blueprint action to blueprint
// Action bp_action1 = BlueprintEmfRestTest.getTestAction(false, "Load
// Application");
// String bp_action1Url = postAndVerify(bp_action1, bp_blueprintUri,
// CONTENTS_NAME);
// System.out.println("created action: " + bp_action1Url);
//
// // add blueprint action to blueprint
// Action bp_action2 = BlueprintEmfRestTest.getTestAction(false, "Login");
// String bp_action2Url = postAndVerify(bp_action2, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action2Url, "predecessors", bp_action1Url);
// System.out.println("created action: " + bp_action2Url);
//
//// // add blueprint action to blueprint
//// Action bp_action3 = BlueprintEmfRestTest.getTestAction(false, "Retrieve
// Data");
//// String bp_action3Url = postAndVerify(bp_action3, bp_blueprintUri,
// CONTENTS_NAME);
//// addAttribute(bp_action3Url, "predecessors", bp_action2Url);
//// System.out.println("created action: " + bp_action3Url);
//
// // add blueprint action to blueprint
// Action bp_action4 = BlueprintEmfRestTest.getTestAction(false, "Select
// Contract");
// String bp_action4Url = postAndVerify(bp_action4, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action4Url, "predecessors", bp_action2Url);
// //addAttribute(bp_action4Url, "predecessors", bp_action3Url);
// System.out.println("created action: " + bp_action4Url);
//
//// // add blueprint action to blueprint
// Action bp_action4_1 = BlueprintEmfRestTest.getTestAction(false, "Select
// Contract_pt.1");
// String bp_action4_1Url = postAndVerify(bp_action4_1, bp_action4Url,
// CONTENTS_NAME);
//// //addAttribute(bp_action4Url, "predecessors", bp_action2Url);
//// System.out.println("created action: " + bp_action4_1Url);
//
// // add blueprint action to blueprint
// Action bp_action5 = BlueprintEmfRestTest.getTestAction(false, "Edit
// Contract");
// String bp_action5Url = postAndVerify(bp_action5, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action5Url, "predecessors", bp_action4Url);
// System.out.println("created action: " + bp_action5Url);
//
// // add blueprint action to blueprint
// Action bp_action6 = BlueprintEmfRestTest.getTestAction(false, "Save
// Contract");
// String bp_action6Url = postAndVerify(bp_action6, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action6Url, "predecessors", bp_action5Url);
// System.out.println("created action: " + bp_action6Url);
//
// // add blueprint action to blueprint
// Action bp_action7 = BlueprintEmfRestTest.getTestAction(false, "Close
// Application");
// String bp_action7Url = postAndVerify(bp_action7, bp_blueprintUri,
// CONTENTS_NAME);
// addAttribute(bp_action7Url, "predecessors", bp_action6Url);
// System.out.println("created action: " + bp_action7Url);
//
// // translate BP-URI -> TestSpec-URI
// String ts_projectUri =
// translateBlueprintUriToTestSpecificationUri(bp_projectUrl);
//
// // create test spec
// TestSpecification test = AllianzTestEmfResttest.getTestSpecification("My
// First Test");
// String ts_specUrl = postAndVerify(test, ts_projectUri, CONTENTS_NAME);
// System.out.println("created test specification: " + ts_specUrl);
//
//
// // add slice to test spec
// addSliceToTestSpec(ts_specUrl, "setup", null, bp_action1Url, bp_action2Url);
// addSliceToTestSpec(ts_specUrl, "check if edit contract works", null,
// bp_action4Url, bp_action7Url);
//
//
//
// Thread.sleep(Long.MAX_VALUE);
// Assert.fail("unrolling of test steps is not implemented yet");
//
// // TODO verify slice procedure
// }
//
// public static void addSliceToTestSpec(String testSpecUri, String name, String
// description, String startActionUri, String endActionUri)
// throws SpecmateException {
// Slice slice1 = AllianzTestEmfResttest.createTestSlice(name, description);
// String ts_slice1Uri = postAndVerify(slice1,testSpecUri,
// AllianzTestEmfResttest.TESTPROCEDURE_NAME);
// System.out.println("created test slice: " + ts_slice1Uri);
//
// setAttribute(ts_slice1Uri, "from", startActionUri);
// System.out.println("set 'from' of slice to action");
//
// setAttribute(ts_slice1Uri, "to", endActionUri);
// System.out.println("set 'to' of slice to action");
// }
//
// private String getTestProcedureSliceUriFromTestSpecificationSliceUri(String
// testSpecificationSliceUri) {
// String testProcedureSliceUri =
// getTestProcedureUriFromTestUri(testSpecificationSliceUri);
// testProcedureSliceUri = testProcedureSliceUri.replace("/testprocedure/",
// "/slices/");
// return testProcedureSliceUri;
// }
//
// @Test
// public void testUpdateTestSliceAndVerifySliceProcedure() throws Exception {
// Assert.fail("update not implemented yet");
// }
//
// @Test
// public void testDeleteTestSliceAndVerifySliceProcedure() throws Exception {
// Assert.fail("delete not implemented yet");
// }
//
// }
