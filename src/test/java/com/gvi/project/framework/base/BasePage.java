package com.gvi.project.framework.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public static Logger logger = Logger.getLogger(BasePage.class.getSimpleName());
	public int waitTime = 30;
	public WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	 /**
     * @param element     element defined on page class
     * @param text        The content of text that we want to input.
     * @param elementName Name of element that we want to input value.
     * @Description: Send a String to textBox.
     * @Description: Send a String to textBox.
     */
    public void sendKeyTextBox(By element, String text, String elementName) {
        logger.info("+++ SendKey on : " + elementName);
        findElement(element).clear();
        findElement(element).sendKeys(text);
    }
    
    /**
     * @param element     element defined on page class
     * @param elementName Name of element that we want to verify
     * @Description In order to wait element to be visible.
     */
    public void waitForVisibleElement(WebElement element, String elementName) {
        logger.info("+++ Wait For Visible Element: " + elementName);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    protected WebElement findElement (By element) {
		return driver.findElement(element);
	}
    
    protected void clickElement(By element) {
		System.out.println("Click on an Element.");
		findElement(element).click();
	}
    
    public WebElement getElementByComponentQuery(String query) {
    	StringBuilder builder = new StringBuilder();
    	builder.append("Ext.ComponentQuery.query(\"");
    	builder.append(query);
    	builder.append("\")");
    	System.out.println("Builder: " + builder);
    	return (WebElement) ((JavascriptExecutor)driver).executeScript(query);
    }
    
	}
