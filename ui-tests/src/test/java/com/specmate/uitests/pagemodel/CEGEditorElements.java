package com.specmate.uitests.pagemodel;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Class
 * CEG Editor Elements
 */
public class CEGEditorElements extends EditorElements {

	/**Editor Elements and their locators*/
	By toolbarNode = By.id("toolbar-tools.addCegNode-button");
	By toolbarConnection = By.id("toolbar-tools.addCegConnection-button");

	/**Property Editor Elements and their locators*/
	By propertiesVariable = By.id("properties-variable-textfield");
	By propertiesCondition = By.id("properties-condition-textfield");
	By propertiesType = By.id("properties-type-dropdown");
	By TypeAND = By.id("type-AND");
	By TypeOR = By.id("type-OR");


	public CEGEditorElements(WebDriver driver, Actions builder) { // constructor
		super(driver, builder);
	}

	/**
	 * creates a new node with corresponding variable and condition at position x,y
	 * and returns the newly created node
	 */
	public WebElement createNode(String variable, String condition, int x, int y) {

		List<WebElement> nodeList = new ArrayList<WebElement>();

		int numberOfNodes = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]")).size();

		driver.findElement(toolbarNode).click();
		
		WebElement editorField = driver.findElement(editor);
		builder.moveToElement(editorField, x, y).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-node]")));
		nodeList = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]"));

		WebElement nodeFromList = nodeList.get(numberOfNodes);

		WebElement variableTextfield = driver.findElement(propertiesVariable);
		WebElement conditionTextfield = driver.findElement(propertiesCondition);
		variableTextfield.clear();
		variableTextfield.sendKeys(variable);
		conditionTextfield.clear();
		conditionTextfield.sendKeys(condition);

		return nodeFromList;
	}

	/**
	 * establishes a connection from node1 to node2 and returns the newly created
	 * connection
	 */
	public WebElement connectNode(WebElement node1, WebElement node2) {
		return super.connect(node1, node2, toolbarConnection);
	}
	
	public void toggleNegateButtonOn(WebElement connection) {
		// Chose the Select tool from the toolbar in order to be able to select a connection
		driver.findElement(toolbarMove).click();
		
		connection.click();
		
		// Assert, that the connection is selected 
		if(!UITestUtil.isElementPresent(By.cssSelector(".form-check-input"), driver)){
			connection.click();
		}
			
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-check-input")));
		driver.findElement(By.cssSelector(".form-check-input")).click();
	}
	
	public void toggleNegateButtonOnLastConnection() {
			
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-check-input")));
		driver.findElement(By.cssSelector(".form-check-input")).click();
	}
	
	public boolean negationDisplayed() {
		return UITestUtil.isElementPresent(By.cssSelector(".tilde"), driver);
	}
	
	public boolean checkUndoConnection() {
		int numberOfConnections = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]"))
				.size();
		return numberOfConnections == 1; 
	}

	public void clearButCancel() {
		driver.findElement(toolbarClear).click();
		driver.findElement(cancel).click();
	}

	public void changeTypeToAND(WebElement node) {
		node.click();
		driver.findElement(propertiesType).click();
		driver.findElement(TypeAND).click();
	}

	public void changeTypeToOR(WebElement node) {
		node.click();
		driver.findElement(propertiesType).click();
		driver.findElement(TypeOR).click();
	}
}