# TestAutomationDemo


Project is developed in Java and uses Selenium Web Driver API to interact with the service NSW website and run sample tests.The UI test searches for 'Apply for a number plate' service and then searches for service locations near Sydney and Domestic Airport.
API tests hits two API to get current weather for a log/lat location and forcast weather for a post code. 

The project was developed on Eclipse and Maven was used as the dependency manager.
TestNG is used as the text execution tool. testng.xml in the project home directory specify which tests are to be run.

The tests are data driven and takes the data from Excel.There are two test classes: one for the UI testing for Service NSW website and the other test class has two test methods for calling the two REST APIs for current weather and weather forecast.
One or both of these test classes can be run in one go by including/omitting these test cases in testng.xml.


Extent Reports utility is used to show html reports in project Reports after the tests have been executed.UI tests capture screenshots at the time of failures.



Project has a config.properties file in 'com.rahul.TestAutomation.ServiceNSW.Config' package where configuration variables are set to run against local, selenium grid & headless.


The project source code  can be downloaded from this repository and can be build and run as TestNG tests.

The project also includes TestNG runner class which has the main method that can be used to run the tests from command line.

Also included is a runnable jar file.

Assumptions:

Platform : Windows
Chrome: Version 71 (tested with this versin)
Chrome driver : 2.45.615291 ( tested with this version) : default location expected is 'C:\chromedriver\chromedriver.exe 
If Chrome driver is installed in some other location , config.properties file may have to be modifield accordingly 
Similary config.properties file would have to be changed if chrome is to be run in headless mode or if Selenium Grid is to be used. 

Also  bat files to start the selenium hub and node have to be manually clicked to start the grid before runnning the tests with grid configuration.


Pending:
Yet to hook the API tests to circleCI to demonstrate automated run based on change triggers
Write Performance tests for the API Testing where you hit both APIs 
•	10 times in an 1 minute duration 
The API tests are taking data from the excel files , but still to write code to run this tests in a loop 10 times in an 1 minute duration


