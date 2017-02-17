// package com.allianz.view.test;
//
// import org.junit.Test;
//
// import com.specmate.common.SpecmateException;
// import com.specmate.model.basemodel.BaseModelNode;
//
// public class AllianzTestEmfResttest extends EmfRestTest {
//
// /*package*/ static final String PRECONDITIION_NAME =
// TestPackage.Literals.TEST_SPECIFICATION__PRECONDITION.getName();
// /*package*/ static final String TESTPROCEDURE_NAME =
// TestPackage.Literals.TEST_SPECIFICATION__TESTPROCEDURE.getName();
// private static final String VIEW_KEY = "com.allianz.views.testview";
//
//
// /*package*/ static String createAndPostTestSpecificationToProject() throws
// SpecmateException {
// String projectUrl = createAndPostProject();
// return createAndPostTestSpecificationToUrl(projectUrl);
// }
//
// /* package */ static String createAndPostTestSpecificationToUrl(String url)
// throws SpecmateException {
// TestSpecification test = getTestSpecification();
// return postAndVerify(test, url, CONTENTS_NAME);
//
// }
//
// /*package*/ static String createAndPostAllianzTestFolderToProject() throws
// SpecmateException {
// String projectUrl = createAndPostProject();
// return createAndPostAllianzTestFolderToUrl(projectUrl);
// }
//
//
// /* package */ static String createAndPostPreconditionToTestSpecification()
// throws SpecmateException {
// String specUrl = createAndPostTestSpecificationToProject();
// Slice slice = createTestSlice();
// return postAndVerify(slice,specUrl,PRECONDITIION_NAME );
// }
//
//
// /*package*/ static String createAndPostTestProcedureToTestSpecification()
// throws SpecmateException {
// String specUrl = createAndPostTestSpecificationToProject();
// Slice slice = createTestSlice();
// return postAndVerify(slice,specUrl,TESTPROCEDURE_NAME);
// }
//
// /*package*/ static String createAndPostAllianzTestFolderToUrl(String url)
// throws SpecmateException {
// BaseModelNode node = getTestBaseModelNode();
// String folderUri = postAndVerify(node, url, CONTENTS_NAME);
// return folderUri;
// }
//
//
//
// /*package*/ static TestSpecification getTestSpecification() {
// return getTestSpecification("Test Case " + (counter++));
// }
//
// /*package*/ static TestSpecification getTestSpecification(String name) {
// return getTestSpecification(name, "Desription"+ (counter++));
// }
//
// /*package*/ static TestSpecification getTestSpecification(String name, String
// description) {
// TestSpecification test = TestFactory.eINSTANCE.createTestSpecification();
// test.setName(name);
// test.setDescription(description);
// test.setId("ID"+(counter++));
// // TODO further properties
//
// return test;
// }
//
// /*package*/ static Slice createTestSlice() {
// return createTestSlice("Test Slice " + (counter++));
// }
//
// /*package*/ static Slice createTestSlice(String name) {
// return createTestSlice(name, "Desription"+ (counter++));
// }
//
// /*package*/ static Slice createTestSlice(String name, String description) {
//
// if (description == null) {
// return createTestSlice(name);
// }
//
// Slice slice = TestFactory.eINSTANCE.createSlice();
// slice.setName(name);
// slice.setDescription(description);
// slice.setId("ID"+(counter++));
// return slice;
// }
//
// @Test
// public void testPostTestSpecification() throws Exception {
// createAndPostTestSpecificationToProject();
// }
//
// @Test
// public void testDeleteTestSpecification() throws Exception {
// String url = createAndPostTestSpecificationToProject();
// deleteAndVerify(url);
// }
//
// @Test
// public void testPostPrecondition() throws Exception {
// createAndPostPreconditionToTestSpecification();
// }
//
// @Test
// public void testPostTestProcedure() throws Exception {
// createAndPostTestProcedureToTestSpecification();
// }
//
// @Test
// public void testDeleteSlice() throws Exception {
// String url = createAndPostTestProcedureToTestSpecification();
// deleteAndVerify(url);
// }
//
// @Test
// public void testUpdateSlice_attribute_from() throws Exception {
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
// // add slice to blueprint
// Action bp_action1 = BlueprintEmfRestTest.getTestAction();
// String bp_action1Url = postAndVerify(bp_action1, bp_blueprintUri,
// CONTENTS_NAME);
// System.out.println("created action: " + bp_action1Url);
//
// // translate BP-URI -> TestSpec-URI
// String ts_projectUri =
// translateBlueprintUriToTestSpecificationUri(bp_projectUrl);
//
// // create test spec
// TestSpecification test = getTestSpecification();
// String ts_specUrl = postAndVerify(test, ts_projectUri, CONTENTS_NAME);
// System.out.println("created test specification: " + ts_specUrl);
//
// // add slice to test spec
// Slice slice = createTestSlice();
// String ts_sliceUri = postAndVerify(slice,ts_specUrl,TESTPROCEDURE_NAME);
// System.out.println("created test slice: " + ts_sliceUri);
//
// setAttribute(ts_sliceUri, "from", bp_action1Url);
// System.out.println("set 'from' of slice to action");
// }
//
// @Test
// public void testUpdateSlice_attribute_to() throws Exception {
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
// // add slice to blueprint
// Action bp_action1 = BlueprintEmfRestTest.getTestAction();
// String bp_action1Url = postAndVerify(bp_action1, bp_blueprintUri,
// CONTENTS_NAME);
// System.out.println("created action: " + bp_action1Url);
//
// // translate BP-URI -> TestSpec-URI
// String ts_projectUri =
// translateBlueprintUriToTestSpecificationUri(bp_projectUrl);
//
// // create test spec
// TestSpecification test = getTestSpecification();
// String ts_specUrl = postAndVerify(test, ts_projectUri, CONTENTS_NAME);
// System.out.println("created test specification: " + ts_specUrl);
//
// // add slice to test spec
// Slice slice = createTestSlice();
// String ts_sliceUri = postAndVerify(slice,ts_specUrl,TESTPROCEDURE_NAME);
// System.out.println("created test slice: " + ts_sliceUri);
//
// setAttribute(ts_sliceUri, "to", bp_action1Url);
// System.out.println("set 'to' of slice to action");
// }
//
// }
