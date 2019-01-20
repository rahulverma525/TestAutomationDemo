package com.rahul.TestAutomation.ServiceNSW.Config;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
 
public class GetPropertyValues {
	
	public static String chromeDriverPath; 
	public  static String isHeadless;
	public  static String isGrid;
		String result = "";
		InputStream inputStream;
	 
		public void getPropValues() throws IOException {
	 
			try {
				Properties prop = new Properties();
				String propFileName = "config.properties";
	 
				//inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
				inputStream = GetPropertyValues.class.getResourceAsStream(propFileName);
				 
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
				}
	 
				Date time = new Date(System.currentTimeMillis());
	 
				// get the property value and print it out
				chromeDriverPath = prop.getProperty("CHROME_DRIVER_PATH");
				isHeadless = prop.getProperty("IS_HEADLESS");
				isGrid = prop.getProperty("IS_GRID");
				
	 
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			} finally {
				inputStream.close();
			}
			
		}
	}
