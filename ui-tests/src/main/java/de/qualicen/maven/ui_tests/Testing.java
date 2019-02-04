package de.qualicen.maven.ui_tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import SpecmatePageClasses.CEGEditorElements;
import SpecmatePageClasses.CommonControlElements;
import SpecmatePageClasses.LoginElements;
import SpecmatePageClasses.ProjectExplorerElements;
import SpecmatePageClasses.RequirementOverviewElements;

import java.net.MalformedURLException;
import java.net.URL;


import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

 
    
//TestClass
public class Testing {
	
	  public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	  public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	  public static final String TUNNELIDENTFIER = System.getenv("TRAVIS_JOB_NUMBER");
	
	public static void main(String[] args) throws InterruptedException, MalformedURLException {
		
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
	    caps.setCapability("platform", "Windows 10");
	    caps.setCapability("version", "latest");
	    caps.setCapability("tunnel-identifier", TUNNELIDENTFIER); 
	 
	    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
	    Actions builder = new Actions(driver);

		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); //page synchronization
		//driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/");
		
		LoginElements login = new LoginElements(driver); //creating object of class LoginElements
		ProjectExplorerElements projectExplorer = new ProjectExplorerElements(driver);
		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CEGEditorElements cegEditor = new CEGEditorElements(driver, builder);
		CommonControlElements commonControl = new CommonControlElements(driver);
		
		// Login
		login.username("username");
		login.password("password");
		login.changeToEnglish();
		login.changeToGerman();
		login.changeToProject("test-data");
		login.login(); 
		
		// Navigation to requirement
		projectExplorer.expand("Evaluation");
		projectExplorer.open("Erlaubnis Autofahren");
		
		
		// Creating and opening new model
		requirementOverview.createModelFromRequirement("test-model1");
		
		// Adding nodes to the CEG
		WebElement nodeAlter = cegEditor.createNode("Alter", ">17",100,100);//results in x=15, y=60
		WebElement nodeFS = cegEditor.createNode("Führerschein", "vorhanden",100,300);//results in x=15, y=270
		WebElement nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);
		
		cegEditor.connect(nodeAlter, nodeAutofahren);
		cegEditor.connect(nodeFS, nodeAutofahren);
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);
		
		// Save CEG
		commonControl.save();
		
		// Create Test specification
		
		
		//close browser
		//driver.close();
		driver.quit();
	}
	
}

