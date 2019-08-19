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
 * Process Editor Elements
 */
public class ProcessEditorElements extends EditorElements {

	By toolbarStep = By.id("toolbar-tools.addStep-button");
	By toolbarDecision = By.id("toolbar-tools.addDecision-button");
	By toolbarStart = By.id("toolbar-tools.addStart-button");
	By toolbarEnd = By.id("toolbar-tools.addEnd-button");
	By toolbarConnection = By.id("toolbar-tools.addProcessConnection-button");
	
	By expectedOutcome = By.id("properties-expectedOutcome-textfield");
	
	public ProcessEditorElements(WebDriver driver, Actions builder) {
		super(driver, builder);
	}
	
	/**
	 * creates a new start with corresponding variable and condition at position x,y
	 * and returns the newly created node
	 */
	public WebElement createStart(int x, int y) {

		driver.findElement(toolbarStart).click();
		
		WebElement editorField = driver.findElement(editor);
		builder.moveToElement(editorField, x, y).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(editor));
		WebElement startNode = driver.findElement(By.cssSelector("[process-start-graphical-node]"));

		return startNode;
	}
	
	public WebElement createActivity(String variable, int x, int y) {

		List<WebElement> activityList = new ArrayList<WebElement>();

		int numberOfActivities = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]")).size();

		driver.findElement(toolbarStep).click();
		
		WebElement editorField = driver.findElement(editor);
		builder.moveToElement(editorField, x, y).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-node]")));
		activityList = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]"));

		WebElement nodeFromList = activityList.get(numberOfActivities);

		WebElement activityTextfield = driver.findElement(propertiesName);
		activityTextfield.clear();
		activityTextfield.sendKeys(variable);

		return nodeFromList;
	}
	
	public WebElement connectActivity(String connectionCondition, WebElement node1, WebElement node2) {
		WebElement connection =  super.connect(node1, node2, toolbarConnection);
		
		// A condition is required if the connection originated from a decision node
		WebElement conditionTextfield = driver.findElement(propertiesCondition);
		conditionTextfield.clear();
		conditionTextfield.sendKeys(connectionCondition);
		
		return connection; 
	}
	
	public void setExpectedOutcome(String outcome) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(expectedOutcome));
		WebElement outcomeTextfield = driver.findElement(expectedOutcome);
		
		outcomeTextfield.clear();
		outcomeTextfield.sendKeys(outcome);
	}
	
	public WebElement createEnd(int x, int y) {

		driver.findElement(toolbarEnd).click();
		
		WebElement editorField = driver.findElement(editor);
		builder.moveToElement(editorField, x, y).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("[process-end-graphical-node]")));
		WebElement endNode = driver.findElement(By.cssSelector("[process-end-graphical-node]"));

		return endNode;
	}
	
	public WebElement createDecison(String name, int x, int y) {

		List<WebElement> decisionList = new ArrayList<WebElement>();

		int numberOfDecisions = driver.findElements(By.cssSelector("[process-decision-graphical-node]")).size();

		driver.findElement(toolbarDecision).click();
		
		WebElement editorField = driver.findElement(editor);
		builder.moveToElement(editorField, x, y).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("[process-decision-graphical-node]")));
		decisionList = driver.findElements(By.cssSelector("[process-decision-graphical-node]"));

		WebElement decisionFromList = decisionList.get(numberOfDecisions);
		
		List<WebElement> childs = decisionFromList.findElements(By.xpath(".//*"));
		WebElement decisionRectangle = childs.get(1);

		WebElement decisionTextfield = driver.findElement(propertiesName);
		decisionTextfield.clear();
		decisionTextfield.sendKeys(name);

		return decisionRectangle;
	}

	public boolean relatedRequirementDisplayed() {
		return UITestUtil.isElementPresent(By.cssSelector("tracing-link"), driver);
	}
}