package com.specmate.uitests.pagemodel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UITestUtil {
	
	/** 
     * If the modal is displayed, we wait 3 seconds and then check again if the modal is still there
     * The method checks for 15 seconds at intervals of 3 seconds (driver.findElement TimeOut 
     * (defined by implictlyWait) (3 seconds) * counter (5)) if the modal disappeared
     * 	*/
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		
		
		boolean modalDisplayed = true;
    	int counter = 6;
    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    	do {
    		try {
    			counter--;
    			modalDisplayed = ((counter>0) && driver.findElement(modalLocator).isDisplayed());
    		} catch (NoSuchElementException | StaleElementReferenceException e) {
    			modalDisplayed = false;
    		}
    	} while(modalDisplayed);
    	// Change timeout back to the defined value
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
	/**
	 * Checks if the element is present or absent
	 *
	 * @param selector
	 * @return true if the element specified by the selector can be found, false
	 *         otherwise
	 */
	public static boolean isElementPresent(By selector, WebDriver driver) {
		// Set the timeout to 1 second in order to avoid delay
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean returnVal = true;
		try {
			driver.findElement(selector);
		} catch (NoSuchElementException e) {
			returnVal = false;
		} finally {
			// Change timeout back to the defined value
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return returnVal;
	}
	
	public static void scrollDownTo(By elementLocator, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by locator
		WebElement Element = driver.findElement(elementLocator);

		// Scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}
}