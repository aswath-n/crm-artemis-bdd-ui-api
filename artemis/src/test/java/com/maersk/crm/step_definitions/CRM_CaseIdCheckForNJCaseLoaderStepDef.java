package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_CaseIdCheckForNJCaseLoaderStepDef extends CRMUtilities implements ApiBase {

    ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCreateGeneralTaskPage generalTaskPage = new CRMCreateGeneralTaskPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMManualContactRecordSearchPage manualContactRec = new CRMManualContactRecordSearchPage();

    @And("I click validate&link and contact details tab")
    public void clickValidateAndContactDetails() {
        waitFor(2);
        assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validateAndLink));
        waitFor(2);
        click(contactRecord.validateAndLink);
        waitForVisibility(activeContactPage.caseContact, 10);
        activeContactPage.caseContact.click();
    }

    @Then("I verify case id as {string} and consumer id as {string} after clicking validate&link button for {string}")
    public void verifyCaseConsumerIdAfterClickingValidateAndLink(String caseId, String consumerId, String taskType) {
        if (taskType.equals("createTask")) {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 10);
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
            waitForVisibility(generalTaskPage.lblCaseId, 10);
            Assert.assertEquals(generalTaskPage.lblCaseId.getText(), caseId);
            Assert.assertEquals(generalTaskPage.lblConsumerId.getText(), consumerId);
        } else if (taskType.equals("taskSearch")) {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 10);
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
            waitForVisibility(generalTaskPage.editTaskCaseId, 10);
            Assert.assertEquals(generalTaskPage.editTaskCaseId.getText(), caseId);
            Assert.assertEquals(generalTaskPage.editTaskConsumerId.getText(), consumerId);
        } else if (taskType.equals("search")) {
            waitForVisibility(activeContactPage.caseID, 10);
            Assert.assertEquals(activeContactPage.caseID.getText(), caseId);
            Assert.assertEquals(activeContactPage.consumerID.getText(), consumerId);
        } else {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validateAndLink));
            waitFor(2);
            click(contactRecord.validateAndLink);
            waitForVisibility(activeContactPage.caseID, 10);
            Assert.assertEquals(activeContactPage.caseID.getText(), caseId);
            Assert.assertEquals(activeContactPage.consumerID.getText(), consumerId);
        }
    }

    @Then("I verify GI consumer id as {string} in the search result")
    public void iVerifyGIConsumerIdAsInTheSearchResult(String GIConsumerID) {
        System.out.println(searchResult.consumerIdColumnHeader.getText() + "++++++++++++++++++++++++++++++++++++++%%%%%%%%%%%%%%%");
        assertEquals(searchResult.consumerIdColumnHeader.getText(), GIConsumerID,
                "Search result is missing GI consumer Id column name");

    }

    @Then("I verify GI case id as {string} in the search result")
    public void iVerifyGICaseIdAsInTheSearchResult(String GIcaseId) throws Throwable {
        waitFor(5);
        assertEquals(searchResult.caseIdColumnHeader.getText(), GIcaseId,
                "Search result is missing GI case Id column name");
    }


    @Then("I verify consumer id as {string} after clicking validate&link button for {string}")
    public void iVerifyConsumerIdAsAfterClickingValidateLinkButtonFor(String consumerId, String screen) throws Throwable {
        staticWait(2);
        if (screen.equals("createTask")) {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 3);
            contactRecord.linkCaseConsumer.click();
            waitFor(4);
            assertEquals(generalTaskPage.editTaskConsumerId.getText(), consumerId);
        } else if (screen.equals("taskSearch")) {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
            assertEquals(generalTaskPage.editTaskConsumerId.getText(), consumerId);
        } else if (screen.equals("search")) {
            click(contactRecord.validateAndLink);
            waitFor(2);
            assertEquals(activeContactPage.consumerID.getText(), consumerId);
        } else {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validateAndLink));
            waitFor(2);
            click(contactRecord.validateAndLink);
            waitFor(2);
            System.out.println(activeContactPage.consumerID.getText());
            assertEquals(activeContactPage.consumerID.getText(), consumerId);
        }
    }

    @Then("I verify case id as {string} after clicking validate&link button for {string}")
    public void iVerifyCaseIdAsAfterClickingValidateLinkButtonFor(String caseId, String screen) throws Throwable {
        if (screen.equals("createTask")) {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            waitForVisibility(contactRecord.linkCaseConsumer, 3);
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
            assertEquals(generalTaskPage.editTaskCaseId.getText(), caseId);
        } else if (screen.equals("taskSearch")) {
            assert (pubCorr.get().elementIsDisplayed(contactRecord.validate));
            waitFor(2);
            contactRecord.validate.click();
            contactRecord.linkCaseConsumer.click();
            waitFor(2);
            assertEquals(generalTaskPage.editTaskCaseId.getText(), caseId);
        } else if (screen.equals("search")) {
            click(contactRecord.validateAndLink);
            waitFor(3);
            assertEquals(activeContactPage.caseID.getText(), caseId);
        } else {
            assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.validateAndLink));
            waitFor(2);
            click(contactRecord.validateAndLink);
            waitFor(3);
            assertEquals(activeContactPage.caseID.getText(), caseId);
        }
    }

    @When("I can search Contact Record by status date and id {string}")
    public void iCanSearchContactRecordByID(String caseId) {
        click(manualContactRec.CreatedOn);
        manualContactRec.CreatedOn.sendKeys("04/27/2023");
        waitFor(1);
        selectDropDown(manualContactRec.searchCase, "GetInsured");
        waitFor(1);
        clearAndFillText(manualContactRec.inputGIcaseId, caseId);
        click(contactRecord.searchButton);
        waitFor(2);
    }

    @When("I search Contact Record type {string} and case id {string}")
    public void i_can_search_Contact_Record_type_and_case_id(String type, String caseId) {
        waitFor(2);
        singleSelectFromDropDown(manualContactRec.consumerTypeDropdown, type);
        waitFor(2);
        clearAndFillText(manualContactRec.inputGIcaseId, caseId);
        click(contactRecord.searchButton);
        waitFor(2);
    }

    @When("I click on first Contact Record ID on Contact Record")
    public void iClickOnFirstContactRecordIDOnContactRecord() {
        waitFor(3);
        try {
            if(isElementDisplayed(contactRecord.CONTACTHISTORYlable)){
                Driver.getDriver().findElement(By.xpath("(//td[2])[1]")).click();
                waitFor(5);
            }else {
            Driver.getDriver().findElement(By.xpath("(//td[3])[1]")).click();
            waitFor(5);}
           // Driver.getDriver().findElement(By.xpath("//h5[text()='CONTACT RECORD INFORMATION']")).isDisplayed();
        } catch (NoSuchElementException e) {
            click(contactRecord.firstContactID);
        }
    }

    @When("I click on first Contact Record ID on Contact Record Edit record Screen")
    public void iClickOnFirstContactRecordIDOnContactRecordEditScreen() {
        waitFor(3);
        contactRecord.firstContactID.click();

    }

    @Then("Navigate to first Link Contact History page")
    public void navigateToFirstLink() {
        waitForVisibility(contactRecord.consumerIdSearch, 15);
        contactRecord.consumerIdSearch.click();
    }

    @Then("Verify GetInsured Case ID is {string} and Primary Individual's name {string} are displayed")
    public void verifyGetInsuredCaseIDIsAndPrimaryIndividualSNameIsDisplayed(String id, String name) {
        assertEquals(contactRecord.headerCaseId(id).getText(), id, "CaseId is not Equal or Not visible");
        assertEquals(contactRecord.individualName(name).getText(), name, "Name is not Equal or Not visible");
    }

    @Then("Navigate to first Link in Create Task page")
    public void navigateToFirstLinkInCreateTaskPage() {
        waitForVisibility(contactRecord.firstContactRecordTable, 15);
        scrollDownUsingActions(2);
        waitFor(1);
        contactRecord.firstContactRecordTable.click();
        waitFor(5);
        //contactRecord.cancelButtonContinue.click();
    }

    @Then("I click CaseId in Consumer In Contact {string}")
    public void IclickCaseIdinConsumerInContact(String caseId) {
        contactRecord.generalCaseId(caseId).click();
        waitFor(3);
        contactRecord.cancelWarningContinueButton.click();
    }

    @Then("Verify GetInsured Case ID is {string}")
    public void verifyGetInsuredCaseIDIs(String id) {
        assertEquals(contactRecord.headerCaseId(id).getText(), id, "CaseId is not Equal or Not visible");
    }


}