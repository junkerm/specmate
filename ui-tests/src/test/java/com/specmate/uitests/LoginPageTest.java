package com.specmate.uitests;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import com.specmate.uitests.pagemodel.*;

import static org.junit.Assert.*;


public class LoginPageTest extends TestBase {
	
	 public LoginPageTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		 super(os, version, browser, deviceName, deviceOrientation);
	 }

	 /**
	  * Runs a test verifying login.
	  * @throws InvalidElementStateException
	  */
	 @Test
	 public void verifyLoginTest() throws InvalidElementStateException {
		driver.get("http://localhost:8080/");
			
		LoginElements login = new LoginElements(driver);
			
		performLogin(login);

		assertTrue(login.isLoggedIn());
	 }
}