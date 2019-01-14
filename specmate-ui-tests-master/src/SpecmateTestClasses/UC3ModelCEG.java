package SpecmateTestClasses;

import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

//for sauce labs integration
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import SpecmatePageClasses.LoginElements;
import SpecmatePageClasses.ProjectExplorerElements;
import SpecmatePageClasses.CommonControlElements;
import SpecmatePageClasses.RequirementOverviewElements;
import SpecmatePageClasses.CEGEditorElements;

public class UC3ModelCEG {
	
	//for sauce labs integration
	public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

	public static void main(String[] args) throws InterruptedException, Exception {
		
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("version", "latest");
		caps.setCapability("tunnel-identifier", System.getenv("TRAVIS_JOB_NUMBER"));

			
		//TODO: Download the browser drivers from https://www.seleniumhq.org/download/ and add the corresponding path:
		System.setProperty("webdriver.chrome.driver", "/Users/janarudolf/Downloads/chromedriver");
		
		//to run with sauce labs
		WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
		//WebDriver driver = new ChromeDriver();
		Actions builder = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); //page synchronization
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/");
			
		LoginElements login = new LoginElements(driver); //creating object of class LoginElements
		ProjectExplorerElements projectExplorer = new ProjectExplorerElements(driver);
		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CEGEditorElements cegEditor = new CEGEditorElements(driver, builder);
		CommonControlElements commonControl = new CommonControlElements(driver);
		
		//method calls
		
		//ensure entry condition: user is logged into test-data project
		login.changeToProject("test-data");
		login.login();

		//create new CEG model for requirement "Erlaubnis Autofahren"
		projectExplorer.expand("test-data");
		projectExplorer.expand("Evaluation");
		
		//1. Der Benutzer folgt dem Link zu einer Anforderung
		//2. Das System zeigt die Anforderung in der Anforderungsüberischt an
		projectExplorer.open("Erlaubnis Autofahren");
		
		//3. Der Benutzer wählt die Option zum Erstellen eines CEG-Modells aus
		//4. Das System legt ein leeres CEG-Modell an und öffnet dieses im CEG-Editor
		requirementOverview.createModel();
		
		String modelName = "Model By Automated UI Test" +  new Timestamp(System.currentTimeMillis());
		cegEditor.setModelName(modelName);
		
		//5. Der Benutzer fügt über die Werkzeugpalette Modellelemente (Knoten und Verbindungen) hinzu
		//6. Das System zeigt das aktuelle Modell während der Modellierung im Editor an
		WebElement nodeAlter = cegEditor.createNode("Alter", ">17",100,100);//results in x=15, y=60
		WebElement nodeFS = cegEditor.createNode("Führerschein", "vorhanden",100,300);//results in x=15, y=270
		WebElement nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);
		
		cegEditor.connect(nodeAlter, nodeAutofahren);
		cegEditor.connect(nodeFS, nodeAutofahren);
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);
		
		//7. Der Benutzer speichert das neue CEG-Modell über die Common Controls
		//8. Das System aktualisiert das Objekt im Backend
		commonControl.save();
		
		projectExplorer.open("Erlaubnis Autofahren");
		requirementOverview.deleteModel(modelName);
		driver.quit();
		//close browser
		//driver.close();
	}
}
