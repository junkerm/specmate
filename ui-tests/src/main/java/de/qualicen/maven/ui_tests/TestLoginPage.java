package de.qualicen.maven.ui_tests;


import org.openqa.selenium.WebDriver;

import SpecmatePageClasses.LoginElements;

public class TestLoginPage {
	
	public static void executeTest(WebDriver driver) {
		
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
