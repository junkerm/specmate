package com.specmate.uitests.pagemodel;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestUtil {
	/** 
     * If the modal is displayed, we wait 1 second and then check again if the modal is still there
     * The method checks for 10 seconds at intervals of 1 second (driver.findElement TimeOut 
     * (defined by implictlyWait) (1 seconds) * counter (10)) if the modal disappeared
     * 	*/
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		/*boolean modalDisplayed = true;
    	int counter = 11;
    	
    	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    	do {
    		try {
    			counter--;
    			// If modal is not displayed, exception is thrown
    			driver.findElement(modalLocator).isDisplayed();
    			modalDisplayed = counter>0;
    		} catch (NoSuchElementException | StaleElementReferenceException e) {
    			modalDisplayed = false;
    		}
    	} while(modalDisplayed);
    	// Change timeout back to the defined value
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);*/
		try {
			new WebDriverWait(driver, 2).until(
			        ExpectedConditions.visibilityOfElementLocated(modalLocator));
		} catch (TimeoutException te) {
			return;
		}
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
		
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
	
	/** 
     * If the projects are not loaded beforehand, Specmate will display 'Bad Gateway' till they are loaded.
     * This method checks for 30 seconds (driver.findElement TimeOut (defined by implictlyWait) (5 seconds) * counter (6)) if the 
     * loading is finished and refreshes the page each 5 seconds
     * 	*/
    public static void waitForProjectsToLoad(WebDriver driver) {
    	/*boolean displayed = false;
    	int counter = 5;
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	do {
    		try {
    			counter--;
    			displayed = (counter<0) || driver.findElement(By.id("login-username-textfield")).isDisplayed();
    		} catch (NoSuchElementException e) {
    			driver.navigate().refresh();
    		}
    	} while(!displayed);
    	// Change timeout back to the defined value
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);*/
        
        
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
    			.ignoring(NoSuchElementException.class);

    	wait.until(new Function<WebDriver, WebElement>() {
    		public WebElement apply(WebDriver driver) {
    			driver.navigate().refresh();
    			return driver.findElement(By.id("foo"));
    		}
    	});
    }
}