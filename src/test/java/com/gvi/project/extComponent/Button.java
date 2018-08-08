
package com.gvi.project.extComponent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Duong.Nguyen
 */
public class Button extends ExtJSComponent
{

    String clickButton = "ExtJsWebDriver.prototype.clickButton = function (query, uuid) {" + "var comp = this.findVisibleComponent(query);"
                    + "var success = comp.btnEl.dom.click();" + "var writeDataToDiv(success, uuid);" + "}";

    public Button(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    public Button(WebDriver driver, By by)
    {
        super(driver, by);
    }

    /**
     * Method click and check if is no error after Ajax callback.
     * 
     * @throws InterruptedException
     */
    public void clickAndWaitForAjaxValid() throws InterruptedException
    {
        waitForExecScriptToReturnTrue(".disabled == false");
        click();
        wait(2);
        waitForFinishAjaxRequest();
        waitForDialogFailure();
    }

}