package com.specmate.uitests.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestUtil {
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		try {
			driver.findElement(modalLocator).isDisplayed();
		} catch (TimeoutException te) {
			// Modal did not appear
			return;
		} catch (NoSuchElementException ne) {
			// Modal did not appear
			return; 
		}
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
	}
}
