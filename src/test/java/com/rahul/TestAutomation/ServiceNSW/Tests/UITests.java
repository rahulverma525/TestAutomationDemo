package com.rahul.TestAutomation.ServiceNSW.Tests;

import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.rahul.TestAutomation.ServiceNSW.Config.Constant;
import com.rahul.TestAutomation.ServiceNSW.Config.GetPropertyValues;
import com.rahul.TestAutomation.ServiceNSW.Utils.ExcelUtils;
import com.rahul.TestAutomation.ServiceNSW.Utils.TestUtils;
import com.rahul.TestAutomation.ServiceNSW.pages.FindLocation;
import com.rahul.TestAutomation.ServiceNSW.pages.HomePage;
import com.rahul.TestAutomation.ServiceNSW.pages.RequestedService;
import com.rahul.TestAutomation.ServiceNSW.pages.SearchResults;




public class UITests {
	HomePage homePage;
	SearchResults searchResults;
	RequestedService returnedService;
	FindLocation findLocation;
	WebDriver driver;
	WebDriverWait wait;
	ExtentReports extent;
	ExtentTest logger;
	DesiredCapabilities capability;
	ChromeOptions chromeOptions;
	/**
	 * Before the test execution is started , setup and initialize the extent reporter 
	 * @throws IOException 
	 */
	
	
	@BeforeTest 
	
	
	public void setup() throws IOException {
		GetPropertyValues propertyConfig=new GetPropertyValues();
		propertyConfig.getPropValues();
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/TestReport_UI_Automation.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_PATH);
		//System.setProperty("webdriver.chrome.driver", propertyConfig.chromeDriverPath);
		 chromeOptions=new ChromeOptions();
		if (GetPropertyValues.isHeadless.contains("Yes"))
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.setAcceptInsecureCerts(true);
		chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
	}

	/**
	 * Before the test  is executed , setup and initialize web driver and register the test with the extent reporter
	 * so that test result is captured in the html report
	 * @throws MalformedURLException 
	 */
	@BeforeMethod
	public void register(Method method) throws MalformedURLException {
		
		String testName = method.getName();
		logger = extent.createTest(testName);
		 if (GetPropertyValues.isGrid.contains("Yes"))
		 { 
			 DesiredCapabilities capability = DesiredCapabilities.chrome();
			 capability.setVersion("2.45");

			 driver= new RemoteWebDriver (new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
		
		 } else  driver = new ChromeDriver(chromeOptions);
	   
		driver.get(Constant.URL);
		homePage = new HomePage(driver);
		assertTrue("Home Page was not found", homePage.isTitlePresent("Home | Service NSW"));
	}

	/**
	 * Test to see if correct service NSW location is shown when user checks if a particular service
	 * is available in a particular suburb and post code 
	 *
	 * @param  requestedService is the service desired eg. Apply for a number plate
	 * @param  requestedSuburbPostCode is the suburb and post code near which the service is desired
	 * @param  expectedLocation is the service location expected to show up for the desired service and suburb post code
	 * Assertions are made that the user is navigated to correct pages and finally if the suggested Service NSW locations include the expected location. 
	 */
	
	@DataProvider	   
	public static Object[][]  getData() throws Exception {
	   
		Object[][] testObjArray = ExcelUtils.getTableArray(Constant.UI_TEST_DATA_PATH);
		return (testObjArray); 
		//return new Object[][] {{"Apply for a number plate","Sydney 2000","Marrickville Service Centre"},{"Apply for a number plate","Sydney Domestic Airport 2020","Rockdale Service Centre"}};
		//return new Object[][] {{"Apply for a number plate","Sydney 2000","Marrickville Service Centre"}};
		    
	}
	
	@Test(dataProvider="getData")

	public void verifyLocationsForRequestedService(String requestedService, String requestedSuburbPostCode,
			String expectedLocation) throws InterruptedException {
		logger.log(Status.INFO,
				"Search for required service and see locations providing the service near a particular post code");
		searchResults = homePage.searchService(requestedService);// Search for desired service on home page and expect search results page
		assertTrue("Search Results page was not found", searchResults.isTitlePresent("Search Results | Service NSW"));
		returnedService = searchResults.choseRequestedService(requestedService);//Click on the desired service on the search results page to land on desired service page 
		assertTrue("Requested service page was not found",
				returnedService.isTitlePresent(requestedService + " | Service NSW"));
		findLocation = returnedService.locateServiceCenters();//Click on locate us to land on Find a Service NSW location page 
		findLocation.searchBySuburbOrPostCode(requestedSuburbPostCode);// Search for particular suburb and post code
		assertTrue("Expected Location was not found", findLocation.isExpectedLocationFound(expectedLocation));// Assert that the list of service centers include the expected service center location
	}

	/**
	 * Test the result after each test run to make sure that screenshots are captured for each tests error/failure and attached to the test report.	 * 
	 */
	
	@AfterMethod
	
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "The Test Method named as :" + result.getName() + " is passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			String temp = TestUtils.getScreenshot(driver);

			logger.fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			logger.log(Status.FAIL, "The Test Method named as :" + result.getName() + " is failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, "The Test Method named as :" + result.getName() + " is skipped");
		}
		driver.quit();
	}

	/**
	 * After the test execution is complete , flush the extent html report 
	 */
	
	@AfterTest

	public void cleanUp() {

		extent.flush();

	}

}

