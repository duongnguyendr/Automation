package com.gvi.project.framework.stepdefinition;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.gvi.project.framework.pom.HomePage;
import com.gvi.project.framework.pom.LoginPage;
import com.gvi.project.framework.base.BaseTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class GeneralStepsDefinition extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	public GeneralStepsDefinition() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@And("^I take screenshoots")
	public void takeScreenShoots() {
		scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	}
	@Given("^I navigate to login page$")
	public void navigateToLoginPage() {
		loginPage.navigateToUrl(baseUrl);
		homePage.verifyHomaPageLoaded();
	}
	
	@And("^I click SignIn button$")
	public void clickLoginBtn() {
		loginPage.clickOnSignInButton();
	}
	
	@And("^I input username and password$")
	public void inputUsernamePassword() {
		loginPage.enterUserNameAndPassword("Duong Nguyen", "123456789");
	}
	
	@And("^I click on cinema tab$")
	public void iClickCinemaTab() {
		homePage.clickOnCinemaTab();
	}
	
	@And("^I click on remove button$")
	public void clickRemoveButton() {
		homePage.clickRemoveButton();
	}
}
