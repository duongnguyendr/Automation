package Project.runner;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;

import Project.FrameWork.listeners.CustomCucumberRunner;
import cucumber.api.CucumberOptions;
@RunWith(CustomCucumberRunner.class)
@CucumberOptions(features = { "src\\test\\java\\TestSuite" }, 
				format = {"json:target/cucumber-report.json","html:target/site/cucumber-pretty" }, 
				glue = "Project.FrameWork.StepDefinition")
public class JUnitRunner {
	@BeforeClass
	public static void beforeSuite() {
//        Generic.sBrowser = "chrome";
//        Generic.sVersion = "60";
//        Generic.sOS = "WIN10";
    }
}
