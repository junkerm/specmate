package de.qualicen.maven.ui_tests;

import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

@Ignore
@RunWith(ConcurrentParameterized.class)
public class TestBase implements SauceOnDemandSessionIdProvider {
	public static String username = System.getenv("SAUCE_USERNAME");
    public static String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public static String seleniumURI;
    public static String buildTag;
    public static final String tunnelidentifier = System.getenv("TRAVIS_JOB_NUMBER");
   
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    //Mark the Sauce Job as passed/failed when the test succeeds or fails
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

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

 
    // Constructor for test instances

    public TestBase(String os, String version, String browser, String deviceName, String deviceOrientation) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.deviceName = deviceName;
        this.deviceOrientation = deviceOrientation;
    }

    // Browser configurations
    @ConcurrentParameterized.Parameters
    public static LinkedList<String[]> browsersStrings() {
        LinkedList<String[]> browsers = new LinkedList<String[]>();

        browsers.add(new String[]{"Windows 10", "14.14393", "MicrosoftEdge", null, null});
        browsers.add(new String[]{"Windows 10", "11.0", "internet explorer", null, null});
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
}
