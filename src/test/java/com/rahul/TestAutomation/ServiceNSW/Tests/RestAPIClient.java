package com.rahul.TestAutomation.ServiceNSW.Tests;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.rahul.TestAutomation.ServiceNSW.Config.Constant;
import com.rahul.TestAutomation.ServiceNSW.Utils.ExcelUtils;
import com.rahul.TestAutomation.ServiceNSW.Utils.TestUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;


public class RestAPIClient {
	static String json;
	static JSONObject obj;
	ExtentReports extent;
	ExtentTest logger;
	
	
	/**
	 * Before the test execution is started , setup and initialize the extent reporter 
	 */
	@BeforeTest
	public void setup() {

		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/TestReport_API_Automation.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
	}
	
	/**
	 * Before the test  is executed ,  register the test with the extent reporter
	 * so that test result is captured in the html report
	 */
	@BeforeMethod
	public void register(Method method) {
		String testName = method.getName();
		logger = extent.createTest(testName);
		}

	
	@DataProvider	   
	public static Object[][]  getCurrentWeatherAPITestData() throws Exception {
	   
		Object[][] testObjArray = ExcelUtils.getTableArray(Constant.GET_CURRENT_WEATHER_API_TEST_DATA_PATH);
		return (testObjArray); 
	}	    
	
	@DataProvider	   
	public static Object[][]  getWeatherForecastAPITestData() throws Exception {
	   
		Object[][] testObjArray = ExcelUtils.getTableArray(Constant.GET_WEATHER_FORECAST_API_TEST_DATA_PATH);
		return (testObjArray); 
	}	
//	@Test(dataProvider="getCurrentWeatherAPITestData")
	public static void getCurrentWeather(String endPoint,String latitude,String longitude)
	 {
		try {

			URL url = new URL(endPoint+"/current?lat="+latitude+"&lon="+longitude+"&key="+Constant.APIKey);																												// .
			
			//URL url = new URL(
			//		"https://api.weatherbit.io/v2.0/current?lat=40.730610&lon=-73.935242&key=a8d3668e488943a3aaa86260f616722a");																												// .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			while ((json = br.readLine()) != null) {
				obj = new JSONObject(json);
				System.out.println(json);
			}
			// obj = new JSONObject(json);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.length(); i++) {
				String stateCode = arr.getJSONObject(i).getString("state_code");
				System.out.println("The state code is"+stateCode);
			}
			// System.out.println(stateCode);
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in RestClientGet:- " + e);
			e.printStackTrace();
		}
	}
	
	@Test(dataProvider="getWeatherForecastAPITestData")
	public static void getThreeHourlyForecast(String endPoint,String postalCode)
 {
		try {

			URL url = new URL(endPoint+"/forecast/3hourly?postal_code="+postalCode+"&key="+Constant.APIKey);																												// .
			
			//URL url = new URL(
			//		"https://api.weatherbit.io/v2.0/current?lat=40.730610&lon=-73.935242&key=a8d3668e488943a3aaa86260f616722a");																												// .
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			while ((json = br.readLine()) != null) {
				obj = new JSONObject(json);
				System.out.println(json);
			}
			// obj = new JSONObject(json);
			JSONArray arr = obj.getJSONArray("data");
			for (int i = 0; i < arr.length(); i++) {
				String timeStamp = arr.getJSONObject(i).getString("timestamp_utc");
			//	String weather = arr.getJSONObject(i).getString("weather");
				JSONObject weather = arr.getJSONObject(i).getJSONObject("weather");
				String weatherDesc=weather.getString("description");
				System.out.println("The timestamp is"+timeStamp);
				System.out.println("The weather is "+weatherDesc);
			}
			// System.out.println(stateCode);
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in RestClientGet:- " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Test the result after each test run to make sure that screenshots are captured for each tests error/failure and attached to the test report.	 * 
	 */
	
	@AfterMethod
	
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "The Test Method named as :" + result.getName() + " is passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
		
				logger.log(Status.FAIL, "The Test Method named as :" + result.getName() + " is failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, "The Test Method named as :" + result.getName() + " is skipped");
		}
		}

	/**
	 * After the test execution is complete , flush the extent html report 
	 */
	
	@AfterTest

	public void cleanUp() {

		extent.flush();

	}

	
}

