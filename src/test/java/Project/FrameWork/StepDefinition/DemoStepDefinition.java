package Project.FrameWork.StepDefinition;

import Project.FrameWork.base.BaseTest;
import cucumber.api.java.en.Then;

public class DemoStepDefinition extends BaseTest{
	
	@Then("^I click some thing$")
	public void iClickSomething() {
		System.out.println("Clickkkk");
	}
}
