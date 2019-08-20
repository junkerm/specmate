package com.specmate.uitests.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Class
 */
public class ProjectExplorerElements {
	WebDriver driver;

	public ProjectExplorerElements(WebDriver driver1) {
		driver = driver1;
	}

	/**
	 * open element <name> in the editor or in detailed view an element can be a
	 * requirement, a model or a test specification
	 */
	public void open(String name) {

		driver.findElement(By.id(name)).click(); // only one element can have name <name>!
		UITestUtil.waitForModalToDisappear(driver);
	}

	/**
	 * expands or closes folder, requirement or model <code>name</code> in the
	 * project explorer, depending on its current status
	 */
	public void expand(String name) {

		driver.findElement(By.id("projectexplorer-expand-" + name)).click();

	}
}
