package com.gvi.project.runner;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;

import com.gvi.project.framework.listeners.CustomCucumberRunner;
import cucumber.api.CucumberOptions;
@RunWith(CustomCucumberRunner.class)
@CucumberOptions(features = { "src\\test\\java\\com\\gvi\\testsuite" }, 
				format = {"json:target/cucumber-report.json","html:target/site/cucumber-pretty" }, 
				glue = "com.gvi.project.framework.stepdefinition")
public class JUnitRunner {
	@BeforeClass
	public static void beforeSuite() {
//        Generic.sBrowser = "chrome";
//        Generic.sVersion = "60";
//        Generic.sOS = "WIN10";
    }
}
