package com.gvi.project.framework.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.gvi.project.framework.common.Generic;
import cucumber.api.Scenario;

public class BaseTest {
	private static Logger logger = Logger.getLogger(BaseTest.class.getSimpleName());
	public static WebDriver driver;
	public static Scenario scenario;
	/**
	 * Description: Methods to get sBaseUrl
	 */
	public static String baseUrl = "https://movieworld.enovision.net/";

	// private static void setBaseUrl(String serverDomainName) {
	// baseUrl = serverDomainName;
	// if (baseUrl == null) {
	// baseUrl = Generic.getConfigValue(convertDirectory(Generic.PROPERTIES_FILE),
	// "DEFAULT_URL");
	// }
	// logger.info("***** Url of testing server is: " + baseUrl);
	// }
	//
	// protected static String getBaseUrl() {
	// setBaseUrl(System.getProperty("serverDomainName"));
	// return baseUrl;
	// }

	
	public WebDriver getDriver() {
        return driver;
    }
	/**
	 * Description: Methods to get sRunMode
	 */
	protected static String sRunMode = "local";

	private static void setRunMode(String runMode) {
		sRunMode = runMode;
		logger.info("***** Run Mode: " + sRunMode);
	}

	protected static String getRunMode() {
		setRunMode(System.getProperty("runMode"));
		return sRunMode;
	}
	
	/**
	 * Description: Methods to get SToEmail
	 */
	public static String sToEmail = "";

	private void setToEmail(String toEmail) {
		sToEmail = toEmail;
		logger.info("***** Send Mail To Email: " + sToEmail);
	}

	public String getToEmail() {
		setToEmail(System.getProperty("toEmail"));
//		System.out.println("1111111111111111111111111111");
//		Generic.getConfigValue(Generic.convertDirectory(Generic.PROPERTIES_FILE), "TO_EMAIL");
		return sToEmail;
	}

	/**
	 * Description: Methods to get SCcEmail
	 */
	public static String sCcEmail = "";

	private void setCcEmail(String CcEmail) {
		sCcEmail = CcEmail;
		logger.info("***** Send Mail To ccEmail: " + sCcEmail);
	}

	public String getCcEmail() {
		setCcEmail(System.getProperty("ccEmail"));
		return sCcEmail;
	}

	public static String runBrowser;

	protected void setDefaultBrowser() {
		runBrowser = Generic.sBrowser;
		if (runBrowser == null) {
			runBrowser = "chrome";
		}
		logger.info("***** We will start test with browser: " + runBrowser);
	}

}
