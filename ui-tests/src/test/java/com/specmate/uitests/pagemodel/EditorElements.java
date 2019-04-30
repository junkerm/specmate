package com.specmate.uitests.pagemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Class
 * Editor Elements
 */
public class EditorElements {
	protected WebDriver driver;
	protected Actions builder;
	
	/**Property Editor Elements and their locators*/
	protected By propertiesName = By.id("properties-name-textfield");
	protected By propertiesDescription = By.id("properties-description-textfield");
	protected By propertiesCondition = By.id("properties-condition-textfield");
	
	/**Editor elements*/
	protected By toolbarMove = By.id("toolbar-tools.select-button");
	protected By toolbarDelete = By.id("toolbar-tools.delete-button");
	protected By toolbarClear = By.id("toolbar-clear-button");
	protected By editor = By.id("editor-field");
	
	/**Pop-Up Elements and their locators*/
	protected By accept = By.id("popup-accept-button");
	protected By cancel = By.id("popup-dismiss-button");
	
	/**Links & Actions*/
	By generateTestSpec = By.id("generatetestspec-button");
	By relatedRequirement = By.id("traces-addrequirement-textfield");
	
	By suggestionItem = By.id("ngb-typeahead-1-0");
	
	public EditorElements(WebDriver driver, Actions builder) {
		this.driver = driver;
		this.builder = builder;
	}
	
	public void delete(WebElement element) {
		driver.findElement(toolbarDelete).click();
		element.click();
	}

	public void clear() {
		driver.findElement(toolbarClear).click();
		driver.findElement(accept).click();
	}
	
	protected void scrollDownTo(By elementLocator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by locator
		WebElement Element = driver.findElement(elementLocator);

		// Scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	
	public void clickOnRelatedRequirement(String requirement) {
		driver.findElement(By.id("requirement-" + requirement + "-link")).click();
	}
	
	public void setModelName(String name) {
		driver.findElement(toolbarMove).click();
		driver.findElement(editor).click();
		WebElement modelName = driver.findElement(propertiesName);
		modelName.clear();
		modelName.sendKeys(name);
	}

	public void setModelDescription(String description) {
		driver.findElement(editor).click();
		WebElement modelDescription = driver.findElement(propertiesDescription);
		modelDescription.clear();
		modelDescription.sendKeys(description);
	}
	
	public void setDescription(String description) {
		WebElement modelDescription = driver.findElement(propertiesDescription);
		modelDescription.clear();
		modelDescription.sendKeys(description);
	}
	
	/**
	 * establishes a connection from node1 to node2 and returns the newly created
	 * connection
	 */
	public WebElement connect(WebElement node1, WebElement node2, By connectionSelector) {
		List<WebElement> connectionList = new ArrayList<WebElement>();

		int numberOfConnections = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]"))
				.size();

		driver.findElement(connectionSelector).click();
		node1.click();
		node2.click();

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-connection]")));
		connectionList = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]"));

		WebElement connectionFromList = connectionList.get(numberOfConnections);
		
		return connectionFromList;
	}
	
	/**
	 *   
	 * @param assertedNodeNumber
	 * @param assertedConnectionNumber
	 * @return true if the model contains the number of nodes and connections specified by the parameters
	 */
	public boolean correctModelCreated(int assertedNodeNumber, int assertedConnectionNumber) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-connection]")));
		int numberOfNodes = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]")).size();
		int numberOfConnections = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]"))
				.size();

		return (numberOfNodes == assertedNodeNumber && numberOfConnections == assertedConnectionNumber);
	}
	
	public boolean errorMessageDisplayed() {
		return isElementPresent(By.cssSelector(".text-danger"));
	}
	
	public boolean noWarningsMessageDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-connection]")));
		return isElementPresent(By.cssSelector(".text-success"));
	}
	
	/**
	 * Checks if the element is present or absent
	 * @param selector
	 * @return true if the element specified by the selector can be found, false otherwise
	 */
	protected boolean isElementPresent(By selector) {
		// Set the timeout to 1 second in order to avoid delay
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    boolean returnVal = true;
	    try{
	        driver.findElement(selector);
	    } catch (NoSuchElementException e){
	        returnVal = false;
	    } finally {
	    	// Change timeout back to the defined value
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    }
	    return returnVal;
	}
	
	/**
	 * Generates a test specification within the Editor
	 */
	public void generateTestSpecification() {
		scrollDownTo(generateTestSpec);
		driver.findElement(generateTestSpec).click();
	}
	
	public void addRelatedRequirement(String name) {
		scrollDownTo(relatedRequirement);
		WebElement relatedRequirementField = driver.findElement(relatedRequirement);
		relatedRequirementField.clear();
		relatedRequirementField.sendKeys(name);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionItem));
		driver.findElement(suggestionItem).click();
	}
	
	public void removeRelatedRequirement() {
		List<WebElement> relatedRequirementsList = new ArrayList<WebElement>();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id^=delete-related-requirement]")));
		relatedRequirementsList = driver.findElements(By.cssSelector("[id^=delete-related-requirement]"));
		
		// Delete the first related requirement
		relatedRequirementsList.get(0).click();
	}
	
	/**
	 * Checks if the test specification contains the number of expected rows
	 * @param expectedRows
	 * @return true if expexted rows equals actual rows of test specification
	 */
	public boolean correctTestSpecificationGenerated(int expectedRows) {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".test-case-row")));
		int numberOfTestCases = driver.findElements(By.cssSelector(".test-case-row")).size();

		return numberOfTestCases == expectedRows;
	}
}
