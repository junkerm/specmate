package SpecmatePageClasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//Page Class
public class CEGEditorElements {
	
	WebDriver driver;
	Actions builder;
	
	//Editor Elements and their locators
	By toolbarMove = By.id("toolbar-tools.move-button");
	By toolbarNode = By.id("toolbar-tools.addCegNode-button");
	By toolbarConnection = By.id("toolbar-tools.addCegConnection-button");
	By toolbarDelete = By.id("toolbar-tools.delete-button");
	By toolbarClear = By.id("toolbar-clear-button");
	By editor = By.id("editor-field");
	
	//Property Editor Elements and their locators
	By propertiesVariable = By.id("properties-variable-textfield");
	By propertiesCondition = By.id("properties-condition-textfield");
	By propertiesName = By.id("properties-name-textfield");
	By propertiesDescription = By.id("properties-description-textfield");
	By propertiesType = By.id("properties-type-dropdown");
	By TypeAND = By.id("type-AND");
	By TypeOR = By.id("type-OR");
	
	// Links & Actions
	By generateTestSpec = By.id("generatetestspec-button");
	
	//Pop-Up Elements and their locators
	By accept = By.id("popup-accept-button");
	By cancel = By.id("popup-dismiss-button");
	
	// Generates a test specification within the CEG Editor
	public void generateTestSpecification () {
		scrollDownTo(generateTestSpec);
		driver.findElement(generateTestSpec).click();
	}
	
	private void scrollDownTo(By elementLocator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

        //Find element by link text and store in variable "Element"        		
        WebElement Element = driver.findElement(elementLocator);

        //This will scroll the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	
	
	public CEGEditorElements(WebDriver driver, Actions builder) { //constructor
		
		this.driver = driver;
		this.builder = builder;
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
	
	/**creates a new node with corresponding variable and condition
	 *at position x,y
	 *and returns the newly created node
	 */
	public WebElement createNode(String variable, String condition, int x, int y) {
		
		List<WebElement> nodeList = new ArrayList<WebElement>();
		
		int numberOfNodes = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]")).size();
		
		//int numberOfNodes = driver.findElements(By.xpath("//*[@class='draggable-element-default']")).size();
		driver.findElement(toolbarNode).click();
		WebElement editorField = driver.findElement(editor);
		//numberOfNodes ++;
		builder.moveToElement(editorField, x, y).click().build().perform();
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-node]")));
		nodeList = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-node]"));
		
		WebElement nodeFromList = nodeList.get(numberOfNodes);
		
		
		//WebDriverWait wait=new WebDriverWait(driver, 20);
		//WebElement node = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='draggable-element-default'])[" + numberOfNodes + "]")));
				
				//driver.findElement(By.xpath("//*[@generic-graphical-node][" + numberOfNodes + "]//*[@ceg-graphical-node]"));
		WebElement variableTextfield = driver.findElement(propertiesVariable);
		WebElement conditionTextfield = driver.findElement(propertiesCondition);
		variableTextfield.clear();
		variableTextfield.sendKeys(variable);
		conditionTextfield.clear();
		conditionTextfield.sendKeys(condition);
		
		
		
		
		return nodeFromList;
	}
	
	/**establishes a connection from node1 to node2
	*and returns the newly created connection
	*/
	public WebElement connect(WebElement node1, WebElement node2) {
		List<WebElement> connectionList = new ArrayList<WebElement>();
		
		int numberOfConnections = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]")).size();
		
		//int numberOfConnections = driver.findElements(By.xpath("//*[@class='inner' or @class='inner innerSelected']")).size();
		
		driver.findElement(toolbarConnection).click();
		node1.click();
		node2.click();
		//numberOfConnections ++;
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("g:first-child > [generic-graphical-connection]")));
		connectionList = driver.findElements(By.cssSelector("g:first-child > [generic-graphical-connection]"));
		
		WebElement connectionFromList = connectionList.get(numberOfConnections);
		
		//WebElement connection = driver.findElement(By.xpath("(//*[@class='inner' or @class='inner innerSelected'])[" + numberOfConnections + "]"));
		return connectionFromList;
	}
	
	public void delete(WebElement element) {
		driver.findElement(toolbarDelete).click();
		element.click();
	}
	
	public void clear() {
		driver.findElement(toolbarClear).click();
		driver.findElement(accept).click();
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
	
	/*
	public void move(WebElement node) {
	 
	}

	public void findNode(String variable, String condition="*"{
	
	}
	*/
}
