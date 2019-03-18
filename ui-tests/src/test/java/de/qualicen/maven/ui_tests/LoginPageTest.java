package de.qualicen.maven.ui_tests;

import SpecmatePageClasses.*;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import static org.junit.Assert.*;

import org.junit.Ignore;

public class LoginPageTest extends TestBase {
	
	 public LoginPageTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		 super(os, version, browser, deviceName, deviceOrientation);
	 }

	 /**
	  * Runs a simple test verifying login.
	  * @throws InvalidElementStateException
	  */
	 @Ignore
	 @Test
	 public void verifyLoginTest() throws InvalidElementStateException {
		driver.get("http://localhost:8080/");
			
		LoginElements login = new LoginElements(driver);
			
		login.username("username");
		login.password("password");
		login.changeToEnglish();
		login.changeToGerman();
		login.changeToProject("test-data");
		login.login(); 

		assertTrue(login.isLoggedIn());
	 }
}
