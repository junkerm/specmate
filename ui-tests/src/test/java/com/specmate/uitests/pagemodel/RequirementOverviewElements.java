package com.specmate.uitests.pagemodel;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Class Requirements Overview
 */
public class RequirementOverviewElements {

	WebDriver driver;

	/** Elements and their locators */
	By createModel = By.id("requirement-createmodel-button");
	By createProcessModel = By.id("requirement-createprocess-button");
	By createTestSpec = By.id("requirement-createtestspec-button");
	By createCEGModel = By.id("requirement-createceg-button");
	By cegModelInputField = By.id("cegModelNameForm");
	By processModelInputField = By.id("processModelNameForm");

	/** Pop-Up Elements and their locators */
	By discard = By.id("popup-accept-button");
	By cancel = By.id("popup-dismiss-button");

	public RequirementOverviewElements(WebDriver driver1) {
		this.driver = driver1;
	}

	public void createCEGModelFromRequirement(String modelName) {
		// first enter name of the model
		driver.findElement(cegModelInputField).sendKeys(modelName);
		// second click on create model
		driver.findElement(createCEGModel).click();
	}

	public void createProcessModelFromRequirement(String modelName) {
		// first enter name of the model
		driver.findElement(processModelInputField).sendKeys(modelName);
		// second click on create model
		driver.findElement(createProcessModel).click();
	}

	public void clickOnCreatedModel(String modelName) {
		driver.findElement(By.id("requirement-" + modelName + "-show-model-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void clickOnCreatedProcess(String processName) {
		driver.findElement(By.id("requirement-" + processName + "-show-process-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void clickOnDuplicateModel(String modelName) {
		driver.findElement(By.id("requirement-Copy 1 of " + modelName + "-show-model-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void clickOnDuplicateProcess(String modelName) {
		driver.findElement(By.id("requirement-Copy 1 of " + modelName + "-show-process-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	/** click on button to create new test specification */
	public void createTestSpec() {
		driver.findElement(createTestSpec).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteModel(String modelName) {
		UITestUtil.scrollDownTo(By.cssSelector("ceg-model-container"), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("requirement-" + modelName + "-deletemodel-button")));
		driver.findElement(By.id("requirement-" + modelName + "-deletemodel-button")).click();
		driver.findElement(discard).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteDuplicateModel(String modelName) {
		UITestUtil.scrollDownTo(By.cssSelector("ceg-model-container"), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("requirement-Copy 1 of " + modelName + "-deletemodel-button")));
		driver.findElement(By.id("requirement-Copy 1 of " + modelName + "-deletemodel-button")).click();
		driver.findElement(discard).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteModelbutCancel(String modelName) {
		driver.findElement(By.id("requirement-" + modelName + "-deletemodel-button")).click();
		driver.findElement(cancel).click();
	}

	public void duplicateCEGModel(String cegModelName) {
		driver.findElement(By.id("requirement-" + cegModelName + "-duplicatemodel-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void duplicateProcess(String processName) {
		driver.findElement(By.id("requirement-" + processName + "-duplicateprocess-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteProcess(String processName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("requirement-" + processName + "-deleteprocess-button")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(discard));
		driver.findElement(By.id("requirement-" + processName + "-deleteprocess-button")).click();
		driver.findElement(discard).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteDuplicateProcess(String processName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("requirement-Copy 1 of " + processName + "-deleteprocess-button")));
		driver.findElement(By.id("requirement-Copy 1 of " + processName + "-deleteprocess-button")).click();
		driver.findElement(discard).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void deleteTestSpec(String testSpecName) {
		driver.findElement(By.id("requirement-" + testSpecName + "-deletetestspec-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	/** Generate test specification for a given model */
	public void generateTestSpecification(String modelName) {
		driver.findElement(By.id(modelName + "-generate-testspec-button")).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public boolean checkForRelatedRequirement() {
		// Check if there is a related requirement shown
		return UITestUtil.isElementPresent(By.cssSelector("[id^=related-requirement-overview]"), driver);
	}

	public void refreshRequirementOverviewPage() {
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("related-requirements-container")));
	}

	public boolean checkForDeletedModel(String modelName) {
		UITestUtil.scrollDownTo(By.cssSelector("ceg-model-container"), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ceg-model-container")));
		return UITestUtil.isElementPresent(By.id("requirement-" + modelName + "-deletemodel-button"), driver);
	}

	public boolean checkForDeletedDuplicateModel(String modelName) {
		UITestUtil.scrollDownTo(By.cssSelector("ceg-model-container"), driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ceg-model-container")));
		return UITestUtil.isElementPresent(By.id("requirement-Copy 1 of " + modelName + "-deletemodel-button"), driver);
	}

	public boolean checkForDeletedProcess(String processName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ceg-model-container")));
		return UITestUtil.isElementPresent(By.id("requirement-" + processName + "-deleteprocess-button"), driver);
	}

	public boolean checkForDeletedDuplicateProcess(String processName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ceg-model-container")));
		return UITestUtil.isElementPresent(By.id("requirement-Copy 1 of " + processName + "-deleteprocess-button"), driver);
	}
}