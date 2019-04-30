package com.specmate.uitests.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Class
 * Common Control Elements
 */
public class CommonControlElements {
	
	WebDriver driver;
	
	/**Elements and their locators*/
	By specmate = By.id("commoncontrol-specmate-button");
	By save = By.id("commoncontrol-save-button");
	By undo = By.id("commoncontrol-undo-button");
	By back = By.id("commoncontrol-back-button");
	By forward = By.id("commoncontrol-forward-button");
	By language = By.id("language-dropdown");
	By german = By.id("language-de");
	By english = By.id("language-gb");
	By logout = By.id("logout-button");
	
	
	public CommonControlElements(WebDriver driver1) {
		this.driver = driver1;
	}
	
	/**click on specmate icon*/
	public void clickOnSpecmateIcon() {
		
		driver.findElement(specmate).click();
	}
	
	/**click on save button*/
	public void save() {

		driver.findElement(save).click();
	}
	
	/**click on undo button*/
	public void undo() {
		
		driver.findElement(undo).click();
	}
	
	/**click on left arrow*/
	public void navigateBack() {
		
		driver.findElement(back).click();
	}
	
	/**click on right arrow*/
	public void navigateForward() {
		
		driver.findElement(forward).click();
	}

	/**change language to German using the language dropdown*/
	public void changeLanguageToGerman() {
		driver.findElement(language).click();
		driver.findElement(german).click();
	}
	
	/**change language to English using the language dropdown*/
	public void changeLanguageToEnglish() {
		driver.findElement(language).click();
		driver.findElement(english).click();
	}

	/**click on logout button*/
	public void logout() {
		driver.findElement(logout).click();
	}
}

