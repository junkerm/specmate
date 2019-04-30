package com.specmate.uitests.pagemodel;

import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Class
 * Login Elements
 */
public class LoginElements {
	
	WebDriver driver;
	
	/**Elements and their locators*/
	By username = By.id("login-username-textfield");
	By password = By.id("login-password-textfield");
	By login = By.id("login-button");
	By language = By.id("language-dropdown");
	By german = By.id("language-de");
	By english = By.id("language-gb");
	By project = By.id("login-project-dropdown"); 
	
	
	public LoginElements(WebDriver driver1) {
		this.driver = driver1;
	}
	
	/**enter username to username field*/
	public void username(String un) {
		
		driver.findElement(username).sendKeys(un);
	}
	
	/**enter password to password field*/
	public void password(String pw) {
		
		driver.findElement(password).sendKeys(pw);
	}
	
	/**click on Log In Button*/
	public void login() {
		
		driver.findElement(login).click();
	}
	
	/**change language to English using the language dropdown*/
	public void changeToEnglish() {
		
		driver.findElement(language).click();
		driver.findElement(english).click();
	}
	
	/**change language to German using the language dropdown*/
	public void changeToGerman() {
		
		driver.findElement(language).click();
		driver.findElement(german).click();
	}
	
	/**change project to <code>name</code> using the project dropdown*/
	public void changeToProject(String name) {
		driver.findElement(project).click();
		driver.findElement(By.id("project-" + name)).click();
	}
	
	public boolean isLoggedIn() {
		try {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/-/welcome"));
		} catch (TimeoutException e) {
			return false;
		}
		String URL = driver.getCurrentUrl();
		String expectedURL = "http://localhost:8080/-/welcome";
		return Objects.equals(URL, expectedURL);
	}
}

