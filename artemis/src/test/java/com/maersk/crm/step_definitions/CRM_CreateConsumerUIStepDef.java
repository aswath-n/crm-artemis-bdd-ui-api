package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.tm.TMSearchUserPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class CRM_CreateConsumerUIStepDef extends BrowserUtils {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    TMSearchUserPage searchUserPage = new TMSearchUserPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());

    @When("I search for a consumer and to click on Add Consumer button")
    public void searchForConsumerAndClickOnAddConsumer() {
        clearAndFillText(contactRecord.firstName, firstName.get());
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(1);
    }

    @Then("I see contact reasons and contact actions")
    public void seeContactReasonsAndContactActions() {
        assertTrue(contactRecord.contactReason.isDisplayed(), "Contact Reasons is not displayed");
        assertTrue(contactRecord.contactAction.isDisplayed(), "Contact Actions is not displayed");
    }

    @Then("I see create consumer back arrow and active contact header")
    public void seeCreateConsumerAndActiveContactHeader() {
        assertTrue(searchUserPage.backToProjectListButton.isDisplayed(), "Create Consumer Back Arrow is not displayed");
        assertTrue(contactRecord.activeContactHeader.isDisplayed(), "Active Contact Header is not displayed");
    }

    @Then("I verify if {string} value is displayed.")
    public void iVerifyIfCreatedOnValueIsDisplayed(String value) {
        waitFor(5); //needed this wait for application to load..
        assertTrue(contactRecord.profileCreatedBy.isDisplayed(), "profileCreatedBy is not displayed");
        String createdByText = contactRecord.profileCreatedBy.getText();
        System.out.println("Value of created by is " + createdByText);
        assertTrue(createdByText.contains(ConfigurationReader.getProperty("loginForCreatedBy")), "Current user name is not disaplayed");
        assertTrue(createdByText.contains(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))), "Current date is is not disaplayed");
        assertTrue(createdByText.contains(":") && (createdByText.contains("AM") || createdByText.contains("PM")), "Current time is not disaplayed");
    }

    @When("I search consumers by DOB")
    public void i_search_consumers_by_DOB() {
        waitFor(1);
        contactRecord.dobTextbox.sendKeys("09/01/2021");
        contactRecord.searchButton.click();
        waitFor(10);
    }

    @When("I search consumers by Case Id")
    public void i_search_consumers_by_CaseId() {
        waitFor(1);
        contactRecord.internalCaseId.sendKeys( World.generalSave.get().get("caseConsumerCaseId").toString());
        contactRecord.searchButton.click();
        waitFor(5);
    }

    @Then("I will see warning message results exceed {int} records")
    public void i_will_see_warning_message_results_exceed_records(Integer int1) {
        assertTrue(contactRecord.maxRecordsWarningMsg.isDisplayed(), "warning message is not displayed");
    }

    @Then("I verify {int} records listed in each page of results by default")
    public void i_verify_records_listed_in_each_page_of_results_by_default(int num) {
        assertEquals(contactRecord.defaultResults.size(), num);
    }

    @Then("I have the option to manually increase my display of results per page to {string}")
    public void i_have_the_option_to_manually_increase_my_display_of_results_per_page_to(String option) {
        scrollDown();
        waitFor(3);
        contactRecord.resultsOptions.click();
        contactRecord.optionFifty.click();
        waitFor(1);
    }
    @When("I search contact {string} record by name and I will see warning message results exceed {int} records")
    public void i_search_contact_record_by_name( String name, int int1) {
        waitFor(1);
        contactRecord.firstName.sendKeys(name);
        contactRecord.searchButton.click();
        assertTrue(contactRecord.maxRecordsWarningMsg.isDisplayed(), "warning message is not displayed");
        //waitForVisibility()
        waitFor(20);
    }
    @When("I click on Add Consumer button")
    public void clickAndClickOnAddConsumer() {
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        waitFor(2);
        createConsumer.addConsumerButton.click();
        waitFor(1);
    }
}