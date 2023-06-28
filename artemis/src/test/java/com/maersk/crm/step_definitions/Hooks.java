package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.*;
//import cucumber.api.Scenario;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
//import net.masterthought.cucumber.Configuration;
//import net.masterthought.cucumber.ReportBuilder;
//import net.masterthought.cucumber.Reportable;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Hooks {
    public final static ThreadLocal<String> nameAndTags = ThreadLocal.withInitial(String::new);

    @Before
    public void setUp(Scenario scenario) {
        System.out.println(scenario.getSourceTagNames().toString() + "===============______________________" + this.toString() + this.hashCode());
        nameAndTags.set(String.join("", scenario.getSourceTagNames()) + " | " + scenario.getName());
        System.out.format("Thread ID - %2d - %s ++++++++++++++++______________________" + nameAndTags.get() + ".\n",
                Thread.currentThread().getId(), scenario);

        if ("api".equalsIgnoreCase(System.getProperty("testType")) || "api-remote".equalsIgnoreCase(System.getProperty("testType"))) {
            return;
        }
        WebDriver driver = Driver.getDriver();
        try {
            driver = Driver.getDriver();
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            if (driver instanceof RemoteWebDriver &&
                    ((RemoteWebDriver) driver).getCapabilities() instanceof ChromeOptions) {
                // already maximized, do nothing
            } else {
                Driver.getDriver().manage().window().maximize();
            }
        } catch (NoSuchSessionException | UnreachableBrowserException exception) {
            exception.printStackTrace();
            driver = Driver.getDriver(true);
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            if (driver instanceof RemoteWebDriver &&
                    ((RemoteWebDriver) driver).getCapabilities() instanceof ChromeOptions) {
                // already maximized, do nothing
            } else {
                Driver.getDriver().manage().window().maximize();
            }
        }
//		Dimension dimension = new Dimension(1920, 1080);
//		Driver.getDriver().manage().window().setSize(dimension);

        // If we're using a remote Chrome browser, don't attempt to maximize
        // Exceptions are sure to follow

        /**
         * The following if statement is to address a known issue with Bonigarcia WebdriverManager
         * Repeated calls with Firefox and Opera will return a Http response of 403
         * To address this add a github token name and secret to your maven command
         */
//		if(System.getProperty("browser").contains("firefox")){
//		    System.setProperty("WDM_GITHUBTOKENNAME",System.getProperty("GITHUBTOKEN"));
//		    System.setProperty("WDM_GITHUBTOKENSECRET",System.getProperty("GITHUBSECRET"));
//        }
        /*Added try catch as with mtag property initialisation we are loosing scenario debug process*/

        //Need to discuss on the which method
        try {
            switch (String.valueOf(scenario.getSourceTagNames())) {
                case "@ga-regression":
                    CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("gaProjectName"));
                    break;
                case "@ga-smoke":
                    CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("gaProjectName"));
                    break;
                case "@nj-regression":
                    CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("njProjectName"));
                    break;
                case "@nj-smoke":
                    CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("njProjectName"));
                    break;
                default:
                    CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("projectName"));
                    break;
            }

        } catch (NullPointerException exception) {
            CRM_ContactRecordUIStepDef.projectName.set(ConfigurationReader.getProperty("projectName"));
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println(scenario.getSourceTagNames().toString() + "______________________Tags AT End OF Scenario");
        System.out.println(scenario.getName() + "______________________Name AT End OF Scenario");

        if ("api".equalsIgnoreCase(System.getProperty("testType")) || "api-remote".equalsIgnoreCase(System.getProperty("testType"))) {
            return;
        }
        synchronized (scenario) {
            if (scenario.isFailed()) {
                //taking a screenshot
//            final byte[] screenshot = ((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
                //adding the screenshot to the report
                File src = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
                byte[] screenshot = new byte[0];
                try {
                    screenshot = FileUtils.readFileToByteArray(src);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        }
//        CRMUtilities.closeThreads();
        Driver.closeDriver();
    }
}
