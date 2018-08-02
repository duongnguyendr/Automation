package com.gvi.project.framework.pom;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gvi.project.framework.base.BasePage;

public class LoginPage extends BasePage {
	public static Logger logger = Logger.getLogger(LoginPage.class.getSimpleName());
	
	private By userNameField = By.xpath("//input[@id='popup-login-email']");
	private By passwordField = By.xpath("//input[@id='login_password']");
	private By signInButton = By.xpath("//button[@id='login_popup_submit']");
	
	public void enterUserNameAndPassword(String userName, String password) {
		sendKeyTextBox(userNameField, userName, "userNameField");
		sendKeyTextBox(passwordField, password, "passwordField");
	}
	
	public void clickOnSignInButton() {
		clickElement(signInButton);
	}
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void navigateToUrl(String url) {
		logger.info("Navigate to url: " + url);
		driver.get(url);
	}
}
