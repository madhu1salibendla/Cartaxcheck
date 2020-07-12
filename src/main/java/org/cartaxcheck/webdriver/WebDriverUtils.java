package org.cartaxcheck.webdriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebDriverUtils {

    private static Logger logger = Logger.getLogger(WebDriverUtils.class);
    private WebDriver webDriver;
    private static final Integer WAIT_TIMEOUT = 20;


    public WebDriverUtils(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver getDriver() {
        return webDriver;
    }


    public void pressTab() {
        Actions builder = new Actions(webDriver);
        builder.sendKeys(Keys.TAB).build().perform();
        //builder.sendKeys(Keys.RETURN).build().perform();
    }

    public WebElement getElement(final By selector) {
        WebElement webElement = (new WebDriverWait(getDriver(), WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(selector));
        return webElement;

    }

    public List<WebElement> getElements(final By selector) {
        return (new WebDriverWait(getDriver(), WAIT_TIMEOUT))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(selector));

    }


    public void clearField(WebElement element) {
        waitForVisibilityOfElement(element);
        element.clear();
    }


    public void enterText(WebElement element, String charSequence) {
        waitForVisibilityOfElement(element);
        clearField(element);
        element.sendKeys(charSequence);
    }


    public void waitForPageToLoad() {
        new WebDriverWait(webDriver, WAIT_TIMEOUT).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });
    }


    public void waitForVisibilityOfElement(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                new WebDriverWait(getDriver(), WAIT_TIMEOUT).until(ExpectedConditions.visibilityOf(element));
                break;
            } catch (Exception e) {
                logger.error("Element not found" + e.getMessage());
            }
            attempts++;
        }

    }

    public List<WebElement> findElements(By childSelector) {
        try {
            return webDriver.findElements(childSelector);
        } catch (NoSuchElementException e) {
            return null;
        } catch (WebDriverException e) {
            logger.error("in findElement method WebDriverException thrown by findElement(%s)");
            return null;
        }
    }

    public void waitForElementTobeClickable(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                new WebDriverWait(webDriver, WAIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
                break;
            } catch (Exception e) {
                logger.error("in waitForElementTobeClickable method Exception occured :" + e.getCause());
            }
            attempts++;
        }
    }
}
