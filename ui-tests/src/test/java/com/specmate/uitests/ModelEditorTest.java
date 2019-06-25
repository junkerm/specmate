package com.specmate.uitests;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.specmate.uitests.pagemodel.CEGEditorElements;
import com.specmate.uitests.pagemodel.CommonControlElements;
import com.specmate.uitests.pagemodel.LoginElements;
import com.specmate.uitests.pagemodel.ProjectExplorerElements;
import com.specmate.uitests.pagemodel.RequirementOverviewElements;

import java.sql.Timestamp;

import static org.junit.Assert.*;


public class ModelEditorTest extends TestBase {
	 public ModelEditorTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		 super(os, version, browser, deviceName, deviceOrientation);
	 }
	 
	 /**
	  * Runs a test verifying the creation of a CEG.
	  * @throws InvalidElementStateException
	  */
	 @Test 
	 public void verifyModelEditorTest() throws InvalidElementStateException {
		
		Actions builder = new Actions(driver);
			
		ProjectExplorerElements projectExplorer = new ProjectExplorerElements(driver);
		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CEGEditorElements cegEditor = new CEGEditorElements(driver, builder);
		CommonControlElements commonControl = new CommonControlElements(driver);
		LoginElements login = new LoginElements(driver);
		
		driver.get("http://localhost:8080/");
		
		if(!login.isLoggedIn()) {
			performLogin(login); 
			assertTrue(login.isLoggedIn());
		} 
			
		// Navigation to requirement
		annotate("Navigation to requirement: Erlaubnis Autofahren");
		projectExplorer.expand("Evaluation");
		projectExplorer.open("Erlaubnis Autofahren");
					
		// Creating and opening new model
		annotate("Creating and opening new model");
		String modelName = "Model By Automated UI Test " +  new Timestamp(System.currentTimeMillis());
		requirementOverview.createCEGModelFromRequirement(modelName);
					
		// Adding nodes to the CEG
		annotate("Adding nodes to the CEG");
		WebElement nodeAlter = cegEditor.createNode("Alter", ">17",100,100);//results in x=15, y=60
		WebElement nodeFS = cegEditor.createNode("Führerschein", "vorhanden",100,300);//results in x=15, y=27
		WebElement nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);
		
		// Check if error message is shown (Assert true)
		annotate("Assert: error message is shown");
		assertTrue(cegEditor.errorMessageDisplayed());
			
		// Connecting created nodes
		annotate("Connecting created nodes");
		cegEditor.connectNode(nodeAlter, nodeAutofahren);
		cegEditor.connectNode(nodeFS, nodeAutofahren);
		
		// Check if error message is hidden (Assert false)
		annotate("Assert: error message is hidden");
		assertTrue(cegEditor.noWarningsMessageDisplayed());
		
		// Negate Connection
		annotate("Negate Connection");
		cegEditor.toggleNegateButtonOnLastConnection();
		
		// Check if tilde is shown (Assert True)
		annotate("Assert: tilde is shown");
		assertTrue(cegEditor.negationDisplayed());
		
		// Remove negation 
		annotate("Remove negation");
		cegEditor.toggleNegateButtonOnLastConnection();
		
		// Check if tile is hidden (Assert false)
		annotate("Assert: tilde is hidden");
		assertFalse(cegEditor.negationDisplayed());
		
		// Change connection type
		annotate("Change connection type");
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);
		
		annotate("Assert: correct model created with 3 nodes and 2 connections");
		assertTrue(cegEditor.correctModelCreated(3, 2));
					
		// Save CEG
		annotate("Save CEG");
		commonControl.save();
					
		// Create test specification
		annotate("Create test specification");
		cegEditor.generateTestSpecification();
		
		annotate("Assert: correct test specification created containing 3 rows");
		assertTrue(cegEditor.correctTestSpecificationGenerated(3));
		
		// Click on created CEG in the requirement overview
		annotate("Click on created CEG in the requirement overview");
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.clickOnCreatedModel(modelName);

		// Duplicate CEG
		annotate("Duplicate CEG");
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.duplicateCEGModel(modelName);
		// Click on it, to check if the duplication created a new model
		annotate("Click on it, to check if the duplication created a new model");
		requirementOverview.clickOnDuplicateModel(modelName);

		// Delete duplicate
		annotate("Delete duplicate model");
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.deleteDuplicateModel(modelName);
		requirementOverview.refreshRequirementOverviewPage();
		// The model should be deleted, thus, use assertFalse
		annotate("Assert: the duplicate model is deleted");
		assertFalse(requirementOverview.checkForDeletedDuplicateModel(modelName));

		// Delete created model 
		annotate("Delete created model");
		requirementOverview.deleteModel(modelName);
		
		requirementOverview.refreshRequirementOverviewPage();
		// The model should be deleted, thus, use assertFalse
		annotate("Assert: the created model is deleted");
		assertFalse(requirementOverview.checkForDeletedModel(modelName));
	 }
}