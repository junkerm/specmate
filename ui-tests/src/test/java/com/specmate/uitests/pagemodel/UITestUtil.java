package com.specmate.uitests.pagemodel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

public class UITestUtil {
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		/*
		 * 	If modal is present, wait till it vanishes
		 * 	If it modal is not present resume
		 * */
		
		boolean modalDisplayed = true;
    	int counter = 6;
    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    	do {
    		try {
    			counter--;
    			modalDisplayed = ((counter>0) && driver.findElement(modalLocator).isDisplayed());
    		} catch (NoSuchElementException ne) {
    			modalDisplayed = false;
    		} catch (StaleElementReferenceException se) {
    			modalDisplayed = false;
    		}
    	} while(modalDisplayed);
    	// Change timeout back to the defined value
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
}