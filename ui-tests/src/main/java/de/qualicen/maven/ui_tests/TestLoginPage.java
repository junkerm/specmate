package de.qualicen.maven.ui_tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import SpecmatePageClasses.LoginElements;

public class TestLoginPage {
	
	public static void executeTest(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //page synchronization
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/");
		
		LoginElements login = new LoginElements(driver);
		
		login.username("username");
		login.password("password");
		login.changeToEnglish();
		login.changeToGerman();
		login.changeToProject("test-data");
		login.login(); 
	}
}
