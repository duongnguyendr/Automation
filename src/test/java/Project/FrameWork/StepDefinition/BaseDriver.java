package Project.FrameWork.StepDefinition;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Project.FrameWork.base.BaseTest;
import Project.FrameWork.common.Generic;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseDriver extends BaseTest{
	private static Logger logger = Logger.getLogger(BaseDriver.class.getSimpleName());
	@Before
	public void initializeDriver(Scenario scenario) {
		logger.info("***** Feature File name11: " + scenario.getId().split(";")[0]);
		logger.info("baseUrl baseUrl");
		logger.info("baseUrl" + baseUrl);
		getToEmail();
        getCcEmail();
		startChromeDriver();
	}
	
	@After
	public void tearDownTest(Scenario scenario) {
		try {
            logger.info("***** Scenario Status: " + scenario.getStatus());
            if (scenario.isFailed() || scenario.getStatus().equals("undefined")) {
                scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
                scenario.write("Scenario failed.");
                logger.info(">>>>>>>>>>>>>> Scenario Status: FAILED <<<<<<<<<<<");
//                getExportFailTestCase(getFeatureName(scenario), scenario.getName());
            } else {
                scenario.write("Scenario Passed");
                logger.info(">>>>>>>>>>>>>> Scenario Status: PASSED <<<<<<<<<<<");
            }
            driver.close();
            driver.quit();
            logger.info("***** Closed browser *****");
            logger.info("||||||||||||||||||||||||||||| End scenario: " + scenario.getName() + ": " + scenario.getStatus() + " |||||||||||||||||||||||||||||");
        }catch (Exception e){
            logger.error("***** There are some errors when closing browser: \n"+e.getMessage() + " ******");
        }
	}
	
	private void startChromeDriver(){
        System.setProperty("webdriver.chrome.driver", Generic.sDirPath + "/src/test/resources/Driver/chromedriver.exe");
        ChromeOptions options = setOptionsForChrome();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        logger.info("***** Opened browser *****");
    }
	
	private ChromeOptions setOptionsForChrome() {
        String downloadFilepath = Generic.sDirPath + "/src/test/resources/testData/download/";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("disable-infobars");
        return options;
    }
	
	@SuppressWarnings("unused")
	private String getFeatureName(Scenario scenario) {
        return scenario.getId().split(";")[0].toLowerCase();
    }
}
