
package com.gvi.project.extComponent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JSExtendedWebElement
{

    static final protected String FUNCTION_DEFINE_EXTJSWEBDRIVER = "if(typeof ExtJsWebDriver === \"undefined\") {ExtJsWebDriver = function(){}; ExtJsWebDriver.log = function(arg){ if(console && console.log) console.log(arg)} };";

    static private final String FUNCTION_highlight = "ExtJsWebDriver.highlight = function(element, timesec) {"
                    + "  var prevBackgroundColor = element.style.backgroundColor;"//
                    + "  var prevBorder = element.style.border;" + "  element.style.backgroundColor = \"#FDFF47\";"//
                    + "  element.style.border = \"3px solid #11FF11\";"
                    + "  window.setTimeout( function (element, prevBackgroundColor, prevBorder) {"//
                    + "    element.style.backgroundColor = prevBackgroundColor;"// 
                    + "    element.style.border = prevBorder;"//
                    + "  }, timesec * 1000, element, prevBackgroundColor, prevBorder);"// 
                    + "}";

    static final private String FUNCTION_htmlEscape = "ExtJsWebDriver.htmlEscape = function(str) {"// 
                    + "return String(str) "//
                    + "	.replace(/&/g, '&amp;') "// 
                    + "	.replace(/\"/g, '&quot;')"// 
                    + "	.replace(/'/g, '&#39;')"//
                    + "	.replace(/</g, '&lt;')"// 
                    + "	.replace(/>/g, '&gt;');"// 
                    + "}";

    /**
     * NON ZERO VALUE for sleeping when waiting.
     */
    static protected long sleepInMillis = 300;

    static protected long timeOutInSeconds = 5;

    protected WebDriver driver;

    protected JavascriptExecutor js = null;

    public WebElement topElement;

    protected WebDriverWait wait;

    /**
     * If a driver is set, but not a topElement, bad things could happen.
     * This method is only here so that the driver can be set, and then JS executed to find the top Element.
     * Maybe It should require a string to find the element?
     * 
     * @param driver
     */
    public JSExtendedWebElement(WebDriver driver, String jsCode)
    {
        setDriverAndFindElementByScript(driver, jsCode);
    }

    public JSExtendedWebElement(WebDriver driver, By by)
    {
        setDriver(driver);
        WebElement element = by.findElement(driver);
        topElement = element;
    }

    /**
     * @param driver
     * @param topElement
     *            - locator of either parent topElement which wraps text input and drop down button or text input
     */
    public JSExtendedWebElement(WebDriver driver, WebElement topElement)
    {
        setDriver(driver);
        setElement(topElement);
    }

    /**
     * Method click.
     */
    public void click()
    {
        topElement.click();
    }

    /**
     * Method getCleanEval.
     * 
     * @param expr
     *            String
     * @return String
     */
    protected Object execScriptClean(final String expr)
    {
        waitForFinishAjaxRequest();
        /**
         * SUPER IMPORTANT:
         * 1) If you want to get a value from the webpage, expr must return.  Ex: "return thing();"
         * 2) If you return ANY OBJECT other than a String, Int, Decimal, or WebElement, the app will hang. Permanently.
         *   a) Ext.getCmp() returns an Object - NOT a WebElement. Therefore "return Ext.getCmp('x-1000')" hangs.
         */
        Object res = js.executeScript(expr);
        return res;
    }

    /**
     * Method cleanEvalTrue.
     * 
     * @param expr
     *            String
     * @return boolean
     */
    public boolean execScriptCleanReturnBoolean(final String expr)
    {
        try
        {
            Object res = execScriptClean(expr);
            if (Boolean.TRUE.equals(res) || Boolean.TRUE.equals(Boolean.parseBoolean((String) res)))
            {
                return true;
            }
        }
        catch (final Exception e)
        {
            return false;
        }
        return false;
    }

    /**
     * Method evalNullComponent.
     * 
     * @param expr
     *            String
     * @return boolean
     */
    public boolean execScriptCleanReturnIsNull(final String expr)
    {
        try
        {
            Object out = execScriptClean(expr);
            return out == null || "null".equals(out);
        }
        catch (final Exception e)
        {
            return false;
        }
    }

    protected Object execScriptOnElement(String jsCode, WebElement element)
    {
        String finalScript = String.format("var el = arguments[0]; %s;", jsCode);
        waitForFinishAjaxRequest();
        return js.executeScript(finalScript, element);
    }

    /**
     * This is used to run javascript on the Top level HTML element of the Ext JS compoenet, such as the top level DIV or TABLE. For Example,
     * execScriptOnTopLevelElement("return el.tagName"); will return the tagName for the top level HTML element.
     * 
     * @param jsCode
     *            Javscript code to execute.
     * @return
     */
    protected Object execScriptOnTopLevelElement(String jsCode)
    {
        return execScriptOnElement(jsCode, topElement);
    }

    protected Boolean execScriptOnTopLevelElementReturnBoolean(String jsCode)
    {
        try
        {
            Object res = execScriptOnElement(jsCode, topElement);
            if (Boolean.TRUE.equals(res) || Boolean.TRUE.equals(Boolean.parseBoolean((String) res)))
            {
                return true;
            }
        }
        catch (final Exception e)
        {
            return false;
        }
        return false;
    }

    protected void setDriver(WebDriver driver)
    {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, timeOutInSeconds, sleepInMillis);
    }

    private void setDriverAndFindElementByScript(WebDriver driver, String jsCode)
    {
        setDriver(driver);
        WebElement element = null;
        try
        {
            element = (WebElement) js.executeScript(jsCode);
        }
        catch (ClassCastException e)
        {
            // Script did not return a WebElement
            element = null;
        }
        topElement = element;
    }

    protected void setElement(WebElement el)
    {
        topElement = el;
    }

    /**
     * Method waitEvalTrue.
     * 
     * @param fullExpr
     *            String
     */
    protected Boolean waitForExecScriptToReturnTrue(final String fullExpr)
    {
        for (int second = 0;; second++)
        {
            if (second >= timeOutInSeconds)
            {
                return false;
            }
            try
            {
                if (Boolean.TRUE.equals(execScriptCleanReturnBoolean(fullExpr)))
                {
                    break; // should not run the sleep below.
                }
            }
            catch (final Exception e)
            {
                // ignore
            }
            try
            {
                Thread.sleep(sleepInMillis);
            }
            catch (final InterruptedException e)
            {
                // ignore
            }
        }
        return true;
    }

    /**
     * Wait until all ajax requests are finished. This should be specified by each framework.
     * 
     * @return boolean return true if all ajax requests are finished.
     */
    protected boolean waitForFinishAjaxRequest()
    {
        return true;
    }

}
