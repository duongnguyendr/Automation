package com.gvi.project.framework.pom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.gvi.project.extComponent.Panel;
import com.gvi.project.framework.base.BasePage;
import com.gvi.project.extComponent.Button;
import com.gvi.project.extComponent.ByExtJsComponentQuery;

public class HomePage extends BasePage{
	public Logger logger = Logger.getLogger(HomePage.class.getSimpleName());
	private ByExtJsComponentQuery byExtJsComponentQuery;
	public HomePage(WebDriver driver) {
		super(driver);
		byExtJsComponentQuery = new ByExtJsComponentQuery(driver);
		
		// TODO Auto-generated constructor stub
	}
	public String cinemaTab = "button[text='Who worked with who']";
	private String homePageHeader = "panel[title='Enovision Movieworld']";
	
	public void clickOnCinemaTab() {
		logger.info("Click on Cinema Tab");
		Button button = new Button(driver, byExtJsComponentQuery.byExtJsComponentQuery(cinemaTab));
		waitForVisibleElement(button.topElement, "Who worker with who");
		button.click();
	}
	
	public void verifyHomaPageLoaded() {
		logger.info("Waiting for home page load.");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Panel panel = new Panel(driver, byExtJsComponentQuery.byExtJsComponentQuery(homePageHeader));
		waitForVisibleElement(panel.topElement, "Header");
	}
	
}
