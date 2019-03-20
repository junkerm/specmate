package de.qualicen.maven.ui_tests;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.Timestamp;

import SpecmatePageClasses.CEGEditorElements;
import SpecmatePageClasses.CommonControlElements;
import SpecmatePageClasses.LoginElements;
import SpecmatePageClasses.ProjectExplorerElements;
import SpecmatePageClasses.RequirementOverviewElements;

import static org.junit.Assert.*;


public class ModelEditorTest extends TestBase {
	 public ModelEditorTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		 super(os, version, browser, deviceName, deviceOrientation);
	 }
	 
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
		String modelName = "Model By Automated UI Test" +  new Timestamp(System.currentTimeMillis());
		requirementOverview.createModelFromRequirement(modelName);
					
		// Adding nodes to the CEG
		WebElement nodeAlter = cegEditor.createNode("Alter", ">17",100,100);//results in x=15, y=60
		WebElement nodeFS = cegEditor.createNode("Führerschein", "vorhanden",100,300);//results in x=15, y=27
		WebElement nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);
			
		// Connecting created nodes
		cegEditor.connect(nodeAlter, nodeAutofahren);
		cegEditor.connect(nodeFS, nodeAutofahren);
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);
		
		assertTrue(cegEditor.correctModelCreated());
					
		// Save CEG
		commonControl.save();
	
					
		// Create test specification
		cegEditor.generateTestSpecification();
		
		assertTrue(cegEditor.correctTestSpecificationGenerated());
	 }
}