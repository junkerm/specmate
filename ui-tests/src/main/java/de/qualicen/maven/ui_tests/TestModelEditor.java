package de.qualicen.maven.ui_tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.Timestamp;

import SpecmatePageClasses.CEGEditorElements;
import SpecmatePageClasses.CommonControlElements;
import SpecmatePageClasses.ProjectExplorerElements;
import SpecmatePageClasses.RequirementOverviewElements;

public class TestModelEditor {
	
	public static void executeTest(WebDriver driver) {
		
		Actions builder = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); //page synchronization
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		ProjectExplorerElements projectExplorer = new ProjectExplorerElements(driver);
		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CEGEditorElements cegEditor = new CEGEditorElements(driver, builder);
		CommonControlElements commonControl = new CommonControlElements(driver);
		
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
				
		// Save CEG
		commonControl.save();
				
		// Create test specification
		cegEditor.generateTestSpecification();
	}

}
