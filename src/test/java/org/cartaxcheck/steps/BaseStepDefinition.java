package org.cartaxcheck.steps;

import org.apache.log4j.Logger;
import org.cartaxcheck.pageobjects.CarHistoryCheckPage;
import org.openqa.selenium.WebDriver;

public class BaseStepDefinition {

	public static final Logger logger = Logger.getLogger(BaseStepDefinition.class);
	protected static WebDriver driver;
	protected static CarHistoryCheckPage carHistoryCheckPage;

}
