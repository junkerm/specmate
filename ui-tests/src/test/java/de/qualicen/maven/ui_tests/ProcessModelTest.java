package de.qualicen.maven.ui_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import SpecmatePageClasses.CommonControlElements;
import SpecmatePageClasses.LoginElements;
import SpecmatePageClasses.ProcessEditorElements;
import SpecmatePageClasses.ProjectExplorerElements;
import SpecmatePageClasses.RequirementOverviewElements;

public class ProcessModelTest extends TestBase {

	public ProcessModelTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	
	/**
	  * Runs a test verifying the creation of a process model.
	  * @throws InvalidElementStateException
	  */
	@Test
	public void verifyProcessModelTest() {
		Actions builder = new Actions(driver);
		
		ProjectExplorerElements projectExplorer = new ProjectExplorerElements(driver);
		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CommonControlElements commonControl = new CommonControlElements(driver);
		LoginElements login = new LoginElements(driver);
		ProcessEditorElements processEditor = new ProcessEditorElements(driver, builder);
		
		driver.get("http://localhost:8080/");
		
		if(!login.isLoggedIn()) {
			login.username("username");
			login.password("password");
			login.changeToProject("test-data");
			login.login(); 
			assertTrue(login.isLoggedIn());
		} 
			
		// Navigation to requirement
		projectExplorer.expand("Evaluation");
		projectExplorer.open("Erlaubnis Autofahren");
		
		// Creating and opening new model
		String modelName = "Model By Automated UI Test " +  new Timestamp(System.currentTimeMillis());
		requirementOverview.createProcessModelFromRequirement(modelName);		

		// Create Start node
		WebElement startNode = processEditor.createStart(300, 100);

		// Create Activity 
		WebElement initActivity = processEditor.createActivity("Initialise", 300, 200);

		// Set expected outcome of init activity 
		processEditor.setExpectedOutcome("Initialisation completed");

		// Set description of init activity 
		processEditor.setDescription("Description for activity");

		// Create Decision
		WebElement decision1 = processEditor.createDecison("Age-Check", 300, 300);

		// Create Activity 
		WebElement childActivity = processEditor.createActivity("Child", 100, 350);

		// Create Activity 
		WebElement parentActivity = processEditor.createActivity("Parent", 500, 350);

		// Reference requirement 
		processEditor.addRelatedRequirement("Zellenmarkierung");

		// Assert, that the requirement is in the list 
		assertTrue(processEditor.relatedRequirementDisplayed());

		// Delete referenced requirement 
		processEditor.removeRelatedRequirement();

		assertFalse(processEditor.relatedRequirementDisplayed());

		// Reference the requirement again, so that it is visible in the requirements overview
		processEditor.addRelatedRequirement("Zellenmarkierung");

		// Create End node
		WebElement endNode = processEditor.createEnd(300, 450);

		// Check if error message is shown (Assert true)
		assertTrue(processEditor.errorMessageDisplayed());
		
		
		// Create connections between the activities 
		processEditor.connectActivity("", startNode, initActivity);

		processEditor.connectActivity("", initActivity, decision1);

		processEditor.connectActivity("isChild", decision1, childActivity);

		processEditor.connectActivity("isParent", decision1, parentActivity);

		processEditor.connectActivity("", childActivity, endNode);

		processEditor.connectActivity("", parentActivity, endNode);

		// Check if error message is hidden (Assert false)
		assertFalse(processEditor.noWarningsMessageDisplayed());
		

		assertTrue(processEditor.correctModelCreated(6, 6));

		// Save CEG
		commonControl.save();

		// Create test specification
		processEditor.generateTestSpecification();
		
		// Assert that the test specification contains two rows
		assertTrue(processEditor.correctTestSpecificationGenerated(2));

		// Click on created CEG in the requirement overview
		processEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");

		// Assert that the related requirement is shown in the requirement overview
		assertTrue(requirementOverview.checkForRelatedRequirement());
		
		requirementOverview.clickOnCreatedProcess(modelName);

		// Duplicate CEG
		processEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.duplicateProcess(modelName);
		// Click on it, to check if the duplication created a new model
		requirementOverview.clickOnDuplicateProcess(modelName);

		// Delete duplicate
		processEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.deleteDuplicateProcess(modelName);

		// Delete created model 
		requirementOverview.deleteProcess(modelName);

		requirementOverview.refreshRequirementOverviewPage();

		// Assert that the related requirement is not shown in the requirement overview, as we deleted the model referencing the requirement
		assertFalse(requirementOverview.checkForRelatedRequirement());
	}
}