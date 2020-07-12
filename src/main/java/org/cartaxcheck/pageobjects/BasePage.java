package org.cartaxcheck.pageobjects;

import org.apache.log4j.Logger;
import org.cartaxcheck.webdriver.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class BasePage extends WebDriverUtils {

	protected WebDriver driver;

	public static final Logger logger = Logger.getLogger(BasePage.class);
	
	public BasePage(WebDriver driver) {
		super(driver);
		logger.debug("in BasePage constructor Start WebDriver");
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}

	public <T> T getPage(Class<T> classs) {
		return PageFactory.initElements(driver, classs);
	}

	public void closeDriver() {
		driver.close();
	}

}
