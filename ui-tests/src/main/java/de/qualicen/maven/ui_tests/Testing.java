package de.qualicen.maven.ui_tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

 
    
//TestClass
public class Testing {
	
	public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	public static final String TUNNELIDENTFIER = System.getenv("TRAVIS_JOB_NUMBER");
	
	public static void main(String[] args) throws InterruptedException, MalformedURLException {
		
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
	    caps.setCapability("platform", "Windows 10");
	    caps.setCapability("version", "latest");
	    caps.setCapability("tunnel-identifier", TUNNELIDENTFIER); 
	 
	    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
	    
	    // Call two test classes 
	    TestLoginPage.executeTest(driver);
	    TestModelEditor.executeTest(driver);
	    
		driver.quit();
	}
}

