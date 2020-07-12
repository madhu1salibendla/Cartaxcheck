package org.cartaxcheck.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.cartaxcheck.model.VehicleDetails;
import org.cartaxcheck.pageobjects.CarHistoryCheckPage;
import org.cartaxcheck.utils.Utils;
import org.cartaxcheck.webdriver.DriverProvider;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.List;

import static org.cartaxcheck.utils.Utils.readInputFile;

public class VehicleDetailsSteps extends BaseStepDefinition {
    private static List<String> vehicleRegistrationNumbers;
    private static List<VehicleDetails> expectedVehicleDetails;
    private static List<VehicleDetails> actualVehicleDetails;
    @Given("^read and extract vehicle registration numbers from car input file$")
    public void readAndExtractVehicleRegistrationNumbersFromCarInputFile() throws IOException {
        vehicleRegistrationNumbers = readInputFile();
    }

    @And("^I am on car tax check page$")
    public void iAmOnCarTaxCheckPage() {
        String applicationUrl = DriverProvider.properties.getProperty("applicationUrl");
        logger.info("applicationUrl: " + applicationUrl);
        carHistoryCheckPage = carHistoryCheckPage.navigateToSite(applicationUrl);
    }

    @When("^I enter each registration and extract the vehicle details$")
    public void iEnterEachRegistrationAndExtractTheVehicleDetails() {
        actualVehicleDetails = carHistoryCheckPage.getVehicleDetailsList(vehicleRegistrationNumbers);
    }

    @Then("^compare vehicle details with output file$")
    public void compareVehicleDetailsWithOutputFile() throws IOException {
        expectedVehicleDetails = Utils.readOutputFile();
        carHistoryCheckPage.validateVehicleDetails(expectedVehicleDetails, actualVehicleDetails);
    }

    @Before
    public void setUp() throws Exception {
        logger.info("Execution Starts");
        if (null != driver) {
            driver.quit();
        }
        if (DriverProvider.browserName != null) {
            driver = (RemoteWebDriver) DriverProvider.getDriver(DriverProvider.BrowserDriver.valueOf(DriverProvider.browserName));
        } else {
            driver = (RemoteWebDriver) DriverProvider.getDriver(DriverProvider.BrowserDriver.CHROME);
        }
        driver.manage().deleteAllCookies();
        carHistoryCheckPage = new CarHistoryCheckPage(driver);
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        logger.info("Execution Ends");
    }
}
