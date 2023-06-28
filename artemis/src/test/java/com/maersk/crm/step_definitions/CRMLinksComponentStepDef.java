package com.maersk.crm.step_definitions;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import java.lang.reflect.Array;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


import static com.maersk.crm.utilities.World.getWorld;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang3.NotImplementedException;

public class CRMLinksComponentStepDef extends CRMUtilities implements ApiBase {
    //
    CRMLinksComponentPage linksComponentPage = new CRMLinksComponentPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activePage = new CRMActiveContactPage();
    private final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);

    @Then("I verify that Links Component's uniqueID, name = {string}, type = {string}, status date is present, and status = {string}")
    public void i_verify_that_Links_Component_s_uniqueID_name_type_status_date_is_present_and_status(String caseName, String typeField, String statusField) {
        assertEquals(linksComponentPage.idFieldForConsumerInCase.getText(), contactRecord.caseId.getText());
        System.out.println(linksComponentPage.idField.getText());
        assertEquals(linksComponentPage.nameFieldForCase.getText(), caseName);
        System.out.println(linksComponentPage.nameFieldForCase.getText());
        assertTrue(linksComponentPage.statusDateFieldForCase.isDisplayed());
        System.out.println(linksComponentPage.statusDateFieldForCase.getText());
        assertEquals(linksComponentPage.typeFieldForCase.getText(), typeField);
        System.out.println(linksComponentPage.typeFieldForCase.getText());
        assertEquals(linksComponentPage.statusFieldForCase.getText(), statusField);
        System.out.println(linksComponentPage.statusFieldForCase.getText());
    }

    @Then("I verify Task Link Component has unique TaskId, name = {string}, type = {string}, status date is present, and status = {string}")
    public void i_verify_Task_Link_Component_has_unique_TaskId_name_type_status_date_is_present_and_status(String taskName, String taskType, String taskStatus) throws ParseException {
//        linksComponentPage.demographicInfoTab.click();
        waitFor(2);
//        jsClick(linksComponentPage.activeContactScreen);
        assertTrue(linksComponentPage.taskIdField.isDisplayed());
        taskId.set(linksComponentPage.taskIdField.getText());
        waitFor(2);
        assertEquals(linksComponentPage.taskNameField.getText(), taskName);
        waitFor(3);
        assertEquals(linksComponentPage.taskTypeField.getText(), taskType);
        waitFor(2);
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        System.out.println("today's date " + dateFormat.format(date));
        assertEquals(linksComponentPage.taskStatusDateField.getText(), dateFormat.format(date));
        waitFor(3);
        assertEquals(linksComponentPage.taskStatusField.getText(), taskStatus);
    }

    @When("I search Task by Task Id")
    public void i_search_Task_by_Task_Id() {
        linksComponentPage.taskIdForLinkVerification.sendKeys(taskId.get());
    }

    @When("I click on found task record")
    public void i_click_on_found_task_record() {
        linksComponentPage.foundTaskRecord.click();
    }


    @Then("I verify that Links Component has uniqueID, name equals to {string}, type equals to {string}, status date is present, and status equals to {string}")
    public void i_verify_that_Links_Component_has_uniqueID_name_equals_to_type_equals_to_status_date_is_present_and_status_equals_to(String nameField, String typeField, String statusField) {
        // "Consumer Profile", type equals to "Consumer", status date is present, and status equals to "ACTIVE"
        assertEquals(linksComponentPage.consumerIdField.getText(), contactRecord.caseId.getText());
        System.out.println(linksComponentPage.consumerIdField.getText());
        assertEquals(linksComponentPage.consumerNameField.getText(), nameField);
        System.out.println(linksComponentPage.consumerNameField.getText());
        assertTrue(linksComponentPage.consumerStatusDateField.isDisplayed());
        System.out.println(linksComponentPage.consumerStatusDateField.getText());
        assertEquals(linksComponentPage.consumerTypeField.getText(), typeField);
        System.out.println(linksComponentPage.consumerTypeField.getText());
        assertEquals(linksComponentPage.consumerStatusField.getText(), statusField);
        System.out.printf(linksComponentPage.consumerStatusField.getText());
    }

    @When("I verify that Links Components has {int} rows of data {string}, {string} and {string}")
    public void i_verify_that_Links_Components_has_rows_of_data_and(Integer sizeOfLinks, String contactRecord, String consumerProfile, String caseName) {
        assertEquals(linksComponentPage.consumerLinkedToTaskList.size(), Integer.parseInt(sizeOfLinks.toString()));
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskContactRecordName.getText(), contactRecord);
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskConsumerProfileName.getText(), consumerProfile);
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskCaseName.getText(), caseName);
    }

    @When("I verify that Links Components has {int} rows of data {string} and Consumer Profile")
    public void i_verify_that_Links_Components_has_rows_of_data_and(Integer sizeOfLinks, String contactRecord) {
        assertEquals(linksComponentPage.consumerLinkedToTaskList.size(), Integer.parseInt(sizeOfLinks.toString()));
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskContactRecordName.getText(), contactRecord);
        assertTrue(linksComponentPage.consumerLinkedToGeneralTaskConsumerProfileName.isDisplayed());
    }


    @When("I verify that {string} row  has status data equal to today's date")
    public void i_verify_that_row_has_status_data_equal_to_today_s_date(String string) {

        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        System.out.println(" ldt " + dateFormat.format(date));
        assertTrue(Driver.getDriver().findElement(By.xpath("//td[text()[contains(.,'" + dateFormat.format(date) + "')]]")).isDisplayed());
    }

    @When("I verify that Consumer Profile row has type equal to {string} and status equal to {string}")
    public void i_verify_that_Consumer_Profile_row_has_type_equal_to_and_status_equal_to(String consumerType, String statusActive) {
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskConsumerType.getText(), consumerType);
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskConsumerProfileStatus.getText(), statusActive);
    }

    @When("I verify that Case row has type equal to {string} and status equal to {string}")
    public void i_verify_that_Case_row_has_type_equal_to_and_status_equal_to(String caseType, String caseStatus) {
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskNullType.getText(), caseType);
        assertEquals(linksComponentPage.consumerLinkedToGeneralTaskCaseStatus.getText(), caseStatus);
    }

    @Then("I verify that Links Component's column titles")
    public void i_verify_that_Links_Component_s_column_titles() {
        assertTrue(activePage.linksIDColumn.isDisplayed());
        assertTrue(activePage.linksNameColumn.isDisplayed());
        assertTrue(activePage.linksTypeColumn.isDisplayed());
        assertTrue(activePage.linksStatusColumn.isDisplayed());
        assertTrue(activePage.linksStatusDateColumn.isDisplayed());
    }
}
