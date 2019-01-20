package com.rahul.TestAutomation.ServiceNSW.pages;

import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RequestedService extends BasePageObject{
	WebDriverWait wait;
	
//	//private By searchBtnLocator = By.id("edit-submit-site-search");
	/**
	 * Constructor to initialize the Retunred Service class with the webdriver
	 */
	
	public RequestedService(WebDriver driver, String requestedService) {
		super(driver);
		wait = new WebDriverWait(driver, 5);
		assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains(requestedService +" | Service NSW")));
	}
	
	


}
