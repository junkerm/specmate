package com.specmate.uitests;


import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.junit.runners.model.Statement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

//import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.specmate.uitests.pagemodel.LoginElements;

import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

@Ignore
@RunWith(Parameterized.class)
public class TestBase implements SauceOnDemandSessionIdProvider {
	public static String username = System.getenv("SAUCE_USERNAME");
    public static String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public static String seleniumURI;
    public static String buildTag;
    public static final String tunnelidentifier = System.getenv("TRAVIS_JOB_NUMBER");
    private static boolean result;

    
    @Rule
	public final TestWatcher watcher = new TestWatcher() {
		@Override
		public Statement apply(Statement base, Description description) {
			return super.apply(base, description);
		}

		@Override
		protected void failed(Throwable e, Description description) {
			result = false;
		}

		@Override
		protected void succeeded(Description description) {
			result = true;
		}
	};

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    protected String browser;
    protected String os;
    protected String version;
    protected String deviceName;
    protected String deviceOrientation;
    protected String sessionId;
    protected WebDriver driver;

 
    /**Constructor for test instances*/
    public TestBase(String os, String version, String browser, String deviceName, String deviceOrientation) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.deviceName = deviceName;
        this.deviceOrientation = deviceOrientation;
    }

    /**Browser configurations*/
    @Parameters
    public static LinkedList<String[]> browsersStrings() {
        LinkedList<String[]> browsers = new LinkedList<String[]>();

        browsers.add(new String[]{"Windows 10", "59.0", "Chrome", null, null});
        //browsers.add(new String[]{"Windows 10", "14.14393", "MicrosoftEdge", null, null});
        //browsers.add(new String[]{"Windows 10", "11.0", "internet explorer", null, null});
        return browsers;
    }

 
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities(); 

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("device-orientation", deviceOrientation);
        capabilities.setCapability("platform", os);
        capabilities.setCapability("tunnel-identifier", tunnelidentifier); 

        String methodName = name.getMethodName();
        capabilities.setCapability("name", methodName);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }
        this.driver = new RemoteWebDriver(new URL("https://" + username+ ":" + accesskey + seleniumURI +"/wd/hub"), capabilities);
        
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    }

    @After
    public void tearDown() throws Exception {
    	((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result ? "passed" : "failed"));
    	
        driver.quit();
    }

    public String getSessionId() {
        return sessionId;
    }

    @BeforeClass
    public static void setupClass() {
        // Get the uri to send the commands to
        seleniumURI = "@ondemand.saucelabs.com:443";
        
        // Set the buildTag to the Travis Build number 
        buildTag = System.getenv("TRAVIS_BUILD_NUMBER");
        if (buildTag == null) {
            buildTag = System.getenv("SAUCE_BUILD_NAME");
        }
    }
    
    protected void waitForProjectsToLoad() {
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
    	       .withTimeout(Duration.ofSeconds(30))
    	       .pollingEvery(Duration.ofSeconds(5))
    	       .ignoring(NoSuchElementException.class);
    	

    	wait.until(new Function<WebDriver, WebElement>() {
    	     public WebElement apply(WebDriver driver) {
    	       driver.navigate().refresh();
    	       return driver.findElement(By.id("login-username-textfield"));
    	     }
    	});
    }
    
	public void performLogin(LoginElements login) {
		waitForProjectsToLoad();
		login.username("username");
		login.password("password");
		login.changeToEnglish();
		login.changeToGerman();
		login.changeToProject("test-data");
		login.login();
	}
}
