package com.rahul.TestAutomation.ServiceNSW.pages;

import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import com.rahul.TestAutomation.QantasQACaseStudy.UIAutomation.ServiceNSW.Utils;

public class HomePage extends BasePageObject {
	private By globalSearchLocator = By.id("edit-contains");
	private By searchBtnLocator = By.id("edit-submit-site-search");
	WebDriverWait wait;

	/**
	 * Constructor to initialize the HomePage class with the webdriver 
	 */
	
	public HomePage(WebDriver driver) {
		super(driver);
		//visit("https://www.service.nsw.gov.au/");
		//visit(Constant.URL);
		wait = new WebDriverWait(driver, 5);
		assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains("Home | Service NSW")));
	}

	/**
	 * Method to perform a global search on the Home page and initialize the class for the search results page which gets opened 
	 */
	
	public SearchResults searchService(String searchString) throws InterruptedException {
		type(searchString, globalSearchLocator);
		click(searchBtnLocator);
		return new SearchResults(driver);
	}
}
