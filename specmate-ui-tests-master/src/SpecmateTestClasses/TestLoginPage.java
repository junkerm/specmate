package SpecmateTestClasses;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import SpecmatePageClasses.LoginElements;

//TestClass
public class TestLoginPage {
	public static void main(String[] args) throws InterruptedException {
		
		//TODO: Download the browser drivers from https://www.seleniumhq.org/download/ and add the corresponding path:
		System.setProperty("webdriver.chrome.driver", "/Users/janarudolf/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //page synchronization
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/");
		
		LoginElements login = new LoginElements(driver); //creating object of class LoginElements
		
		//method calls
		login.username("username");
		login.password("password");
		login.changeToEnglish();
		login.changeToGerman();
		login.changeToProject("test-data");
		login.login();
		
		//close browser
		driver.close();
	}
}