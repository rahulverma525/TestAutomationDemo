package com.rahul.TestAutomation.ServiceNSW.pages;

import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindLocation extends BasePageObject {
	private By locationSearchStringLocator = By.id("locatorTextSearch");
	private By searchBtnLocator=By.xpath("//button/*[contains(text(),'Search')]/following-sibling::*");
	private By expectedSearchResultLinkLocator;
	WebDriverWait wait;
	
	/**
	 * Constructor to initialize the Find Location  class with the webdriver 
	 */
	
	public FindLocation(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 5);
		assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains("Find a Service NSW location | Service NSW")));
	}

	
	/**
	 * Method to search for service NSW locations for a particular suburb and postcode
	 */
	
	public void searchBySuburbOrPostCode(String searchString) throws InterruptedException {
		type(searchString, locationSearchStringLocator);
		click(searchBtnLocator);
		//return new SearchResults(driver);
	}

	/**
	 * Method to verify if expected location was on the list of suggested locations
	 */
	public Boolean isExpectedLocationFound(String expectedLocation) throws InterruptedException {
		expectedSearchResultLinkLocator=By.partialLinkText(expectedLocation);
		return IsDisplayed(expectedSearchResultLinkLocator,10);
	}
	
}
