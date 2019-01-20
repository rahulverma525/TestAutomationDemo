package com.rahul.TestAutomation.ServiceNSW.pages;

import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResults extends BasePageObject {
	WebDriverWait wait;
	
	/**
	 * Constructor to initialize the Search Results  class with the webdriver
	 */
	
	public SearchResults(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 5);
		assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains("Search Results | Service NSW")));
	}
	
	/**
	 * Method to select the required service from the list of search results and initialize the class for the selected service page which gets opened 
	 */

	public RequestedService choseRequestedService (String requestedService) throws InterruptedException {
		//requestedServiceLocator = By.linkText(requestedService);
		click(By.linkText(requestedService));
		
		//Contacts contacts = home.<Contacts>goToATab("requestedService");
		return new RequestedService(driver,requestedService);
	}
	}
