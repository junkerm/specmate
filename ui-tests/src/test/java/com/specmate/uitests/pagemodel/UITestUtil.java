package com.specmate.uitests.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestUtil {
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		try {
			new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
		} catch (TimeoutException te) {
			// Modal did not appear
			return;
		}
		new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
	}
}
