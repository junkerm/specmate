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
		projectExplorer.expand("Evaluation");
		projectExplorer.open("Erlaubnis Autofahren");
					
		// Creating and opening new model
		String modelName = "Model By Automated UI Test " +  new Timestamp(System.currentTimeMillis());
		requirementOverview.createCEGModelFromRequirement(modelName);
					
		// Adding nodes to the CEG
		WebElement nodeAlter = cegEditor.createNode("Alter", ">17",100,100);//results in x=15, y=60
		WebElement nodeFS = cegEditor.createNode("Führerschein", "vorhanden",100,300);//results in x=15, y=27
		WebElement nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);
		
		// Check if error message is shown (Assert true)
		assertTrue(cegEditor.errorMessageDisplayed());
			
		// Connecting created nodes
		cegEditor.connectNode(nodeAlter, nodeAutofahren);
		cegEditor.connectNode(nodeFS, nodeAutofahren);
		
		// Check if error message is hidden (Assert false)
		assertTrue(cegEditor.noWarningsMessageDisplayed());
		
		// Negate Connection
		cegEditor.toggleNegateButtonOnLastConnection();
		
		// Check if tilde is shown (Assert True)
		assertTrue(cegEditor.negationDisplayed());
		
		// Remove negation 
		cegEditor.toggleNegateButtonOnLastConnection();
		
		// Check if tile is hidden (Assert false)
		assertFalse(cegEditor.negationDisplayed());
		
		// Change connection type
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);
		
		assertTrue(cegEditor.correctModelCreated(3, 2));
					
		// Save CEG
		commonControl.save();
	
					
		// Create test specification
		cegEditor.generateTestSpecification();
		
		if(cegEditor.checkForPopUp()) {
			cegEditor.acceptPopUp();
		}
		
		assertTrue(cegEditor.correctTestSpecificationGenerated(3));
		
		// Click on created CEG in the requirement overview
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.clickOnCreatedModel(modelName);

		// Duplicate CEG
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.duplicateCEGModel(modelName);
		// Click on it, to check if the duplication created a new model
		requirementOverview.clickOnDuplicateModel(modelName);

		// Delete duplicate
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.deleteDuplicateModel(modelName);
		// The model should be deleted, thus, use assertFalse
		assertFalse(requirementOverview.checkForDeletedDuplicateModel(modelName));

		// Delete created model 
		requirementOverview.deleteModel(modelName);
		
		driver.navigate().refresh();
		
		// The model should be deleted, thus, use assertFalse
		assertFalse(requirementOverview.checkForDeletedModel(modelName));
	 }
}
