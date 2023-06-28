package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_ConsumerUpdateProfile extends CRMUtilities implements ApiBase {

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public final static ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMEditConsumerProfilePage editconsumer = new CRMEditConsumerProfilePage();
    CRM_ContactRecordUIStepDef crm_contactRecordUIStepDef=new CRM_ContactRecordUIStepDef();
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dob = ThreadLocal.withInitial(() -> "12/01/1991");
    private final ThreadLocal<String> firstNameUpdated = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastNameUpdated = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dobUpdated = ThreadLocal.withInitial(() -> "12/09/1993");
    private final ThreadLocal<String> genderUpdated = ThreadLocal.withInitial(() -> "Female");
    private final ThreadLocal<String> startDateUpdated = ThreadLocal.withInitial(() -> "09/15/2019");
    private final ThreadLocal<String> endDateUpdated = ThreadLocal.withInitial(() -> "04/09/2023");
    private final ThreadLocal<String> startDateFuture = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> endDateFuture = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> startDatePast = ThreadLocal.withInitial(() -> "01/01/2019");
    private final ThreadLocal<String> endDatePast = ThreadLocal.withInitial(() -> "01/01/2020");
    private final ThreadLocal<String> prefLangUpdated = ThreadLocal.withInitial(() -> "English");
    private final ThreadLocal<String> ssnUpdated = ThreadLocal.withInitial(() -> getRandomNumber(9));
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactType = ThreadLocal.withInitial(() -> "Inbound");
    private final ThreadLocal<String> contactChannelType = ThreadLocal.withInitial(() -> "Phone");
    private final ThreadLocal<String> contactDispositionsValue = ThreadLocal.withInitial(() -> "Complete");
    private final ThreadLocal<String> contactSearchRecordType = ThreadLocal.withInitial(() -> "General");
    public final ThreadLocal<String> expectedConsumerName = ThreadLocal.withInitial(String::new);

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCaseAndConsumerSearchPage consumerRecord = new CRMCaseAndConsumerSearchPage();
    CRM_ViewContactRecordHistoryStepDef viewContactRecordHistoryStepDef=new CRM_ViewContactRecordHistoryStepDef();

    String consumerNameSearch;
    String consumerNameDetailPage;

    @When("I create a consumer for case consumer profile search")
    public void editconsumerForConsumerSearch() {
       // createNewConsumerForConsumerSearch();
        viewContactRecordHistoryStepDef.i_add_New_Consumer_to_the_record();
    }

    private void createNewConsumerForConsumerSearch() {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName.get());
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstName.get() + " " + lastName.get());
        System.out.println(firstName.get() + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstName.get());
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, dob.get());
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        waitFor(2);
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());
    }

    @And("I search for consumer profile created")
    public void searchForConsumerProfile() {
        consumerRecord.consumerFirstName.sendKeys(viewContactRecordHistoryStepDef.firstName.get());
        consumerRecord.consumerLastName.sendKeys(viewContactRecordHistoryStepDef.lastName.get());
        waitFor(2);
        jsClick(consumerRecord.searchBtn);
        waitFor(1);

    }

    @Then("I am able to validate the required fields information")
    public void validateTheRequiredFieldsInformation() {
        waitFor(1);
        clearFiled(editconsumer.consUpdateFN);
        clearFiled(editconsumer.consUpdateLN);
        waitFor(2);
        searchResult.ssnMaskButtonSearchParameter.click();
        waitFor(2);
        searchResult.ssnMaskDateOfBirth.click();
        editconsumer.consUpdateDOB.clear();
        editconsumer.consUpdateStartDate.clear();
        waitFor(2);
        editconsumer.saveConsumerUpdate.click();
        waitFor(3);

        assertTrue(editconsumer.consUpdateFNVal.isDisplayed(),"First name isnt required");
        assertTrue(editconsumer.consUpdateLNVal.isDisplayed(),"Last name isnt required");
        assertTrue(editconsumer.consUpdateDOBVal.isDisplayed(),"DOB values isnt required");
        assertTrue(editconsumer.consUpdateStartVal.isDisplayed(),"Start date isnt required");

    }

    @Then("I am able to validate the fields format")
    public void validateTheFieldsFormat() {
        waitFor(1);
        searchResult.ssnMaskButtonSearchParameter.click();
        searchResult.ssnMaskDateOfBirth.click();
        editconsumer.consUpdateDOB.clear();
        clearAndFillText(editconsumer.consUpdateDOB, "21/09/1993");
        editconsumer.consUpdateStartDate.clear();
        clearAndFillText(editconsumer.consUpdateStartDate, "29/15/2019");
        editconsumer.consUpdateEndDate.clear();
        clearAndFillText(editconsumer.consUpdateEndDate, "24/09/2020");
        clearAndFillText(editconsumer.consUpdateSSN, "827");
        waitFor(2);
        editconsumer.saveConsumerUpdate.click();

        assertTrue(editconsumer.consUpdateDOBFormat.isDisplayed(),"Invalid date format error for DOB isnt displayed");
        assertTrue(editconsumer.consUpdateStartFormat.isDisplayed(),"Invalid date format error for start date isnt displayed");
        assertTrue(editconsumer.consUpdateEndFormat.isDisplayed(),"Invalid date format error for end date isnt displayed");
        assertTrue(editconsumer.consUpdateSSNFormat.isDisplayed(),"Wrong SSN format error isnt displayed");

    }

    @Then("I am able to edit the Consumer profile")
    public void i_am_able_to_edit_the_Consumer_profile() {
        waitForClickablility(editconsumer.consUpdateFN,25);
        jsClick(editconsumer.consUpdateFN);
        clearAndFillText(editconsumer.consUpdateFN, firstNameUpdated.get());
        clearAndFillText(editconsumer.consUpdateMN, "M");
        clearAndFillText(editconsumer.consUpdateLN, lastNameUpdated.get());
        clearAndFillText(editconsumer.consUpdateDOB, dobUpdated.get());
        selectDropDown(editconsumer.consUpdateGender, genderUpdated.get());
        clearAndFillText(editconsumer.consUpdateStartDate, startDateUpdated.get());
        clearAndFillText(editconsumer.consUpdateEndDate, endDateUpdated.get());
        selectFromMultiSelectDropDown(editconsumer.spokenLanguageDropdown,"Spanish");
        selectFromMultiSelectDropDown(editconsumer.writtenLanguageDropdown,"Other");
        clearAndFillText(editconsumer.consUpdateSSN, ssnUpdated.get());
        waitFor(2);
    }

    @Then("I am able to cancel updating the Consumer profile")
    public void cancelUpdatingTheConsumerProfile() {
        clearAndFillText(editconsumer.consUpdateDOB, dobUpdated.get());
        editconsumer.cancelConsumerUpdate.click();
        waitFor(1);

        assertTrue(editconsumer.warningText.isDisplayed(),"Error message with correct context isnt displayed");

        editconsumer.cancelPopUp.click();
        waitFor(1);
        assertTrue(editconsumer.profileContactPage.isDisplayed());

        editconsumer.cancelConsumerUpdate.click();
        waitFor(1);
        editconsumer.continuePopUp.click();
        waitFor(1);
        assertTrue(editconsumer.profileContactPage.isDisplayed());
    }

    @Then("I select the Inactivate Immediately Button and verify consumer status")
    public void i_select_the_Inactivate_Immediately_Button_and_verify_consumer_status() {
        waitFor(1);
        editconsumer.consumerInactiveImmediateBtn.click();
        waitFor(2);
        editconsumer.saveConsumerUpdate.click();
        waitFor(5);
        assertTrue(editconsumer.consumerStatus.getText().equals("INACTIVE"),"Inactive status isnt verified");
    }

    @Then("I am able to check the consumer status based on {string} and {string} values")
    public void checkConsumerStatusBasedOnStartEndDates(String startDate, String endDate) throws ParseException {
        if (startDate.equals("Future"))
            startDate = getDateFuture("startDate");
        if (endDate.equals("Future"))
            endDate = getDateFuture("endDate");

        updateStartEndDate(startDate, endDate);
        validateStatus();
    }

    @Then("I am able to edit the consumer dob and validate the age has been updated")
    public void editTheConsumerDobAndValidateTheAge() {
        waitFor(1);
        searchResult.ssnMaskDateOfBirth.click();
        editconsumer.consUpdateDOB.clear();
        clearAndFillText(editconsumer.consUpdateDOB, dobUpdated.get());

        i_click_on_Save_button_for_Profile_Details();

        String consumerAge = editconsumer.consumerAge.getText();
        String calculatedAge = String.valueOf(calculateAge(dobUpdated.get()));
        assertEquals(calculatedAge, consumerAge,"Consumer age isnt updated");
    }

    @Then("I am able to edit the consumer ssn and validate the ssn has been updated")
    public void editTheConsumerSSNAndValidate() {
        waitFor(1);
        jsClick(searchResult.ssnMaskButtonSearchParameter);
        clearAndFillText(editconsumer.consUpdateSSN, ssnUpdated.get());
        i_click_on_Save_button_for_Profile_Details();
        waitFor(2);

        editconsumer.ssnUnMaskUpdatePage.click();
        String SSNonView= editconsumer.consumerSSN.getText().substring(0,11).replace("-","");
        assertEquals(SSNonView, ssnUpdated,"SSN doesnt match with updated SSN");
    }

    @Then("I am able to edit the consumer Name fields and validate the Name fields are updated")
    public void editConsumerNameFieldsAndValidate() {
        String firstNameModified = newConsumer.get().get("name").toString();
        String lastNameModified = newConsumer.get().get("surname").toString();

        waitFor(1);

        clearAndFillText(editconsumer.consUpdateFN, firstNameModified);
        clearAndFillText(editconsumer.consUpdateMN, "T");
        clearAndFillText(editconsumer.consUpdateLN, lastNameModified);
        waitFor(2);
        i_click_on_Save_button_for_Profile_Details();

        String fullNameUpdated=editconsumer.updatedFullName.getText();
        assertEquals(firstNameModified+" "+"T"+" "+lastNameModified,fullNameUpdated,"Full name isnt updated");
    }

    @Then("I am able to save and capture the updated by information")
    public void saveAndCaptureTheUpdatedByInformation() {
    }

    private void updateStartEndDate(String startDate, String endDate) {
        waitFor(1);

        System.out.println("Start Date: "+startDate);
        System.out.println("End Date: "+endDate);

        editconsumer.consUpdateStartDate.clear();
        clearAndFillText(editconsumer.consUpdateStartDate, startDate);
        editconsumer.consUpdateEndDate.clear();
        clearAndFillText(editconsumer.consUpdateEndDate, endDate);
        waitFor(2);

        i_click_on_Save_button_for_Profile_Details();
        System.out.println("Editing start and end date is completed.");
    }

    private void validateStatus() throws ParseException {
        waitFor(3);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String curDate = sdf.format(currentDate);
        Date curDateFormatted = sdf.parse(curDate);
        scrollUpUsingActions(2);

        String startDate = editconsumer.consumerStartDate.getText();
        System.out.println("start Date from UI:" + startDate);
        Date startDateFormatted = sdf.parse(startDate);
        String endDate = editconsumer.consumerEndDate.getText();
        System.out.println("end Date from UI:" + endDate);
        String status = editconsumer.consumerStatus.getText();
        System.out.println("status from UI:" + status);

        if (!endDate.isEmpty()) {
            Date endDateFormatted = sdf.parse(endDate);

            if (startDateFormatted.compareTo(curDateFormatted) > 0) {
                assertEquals(status, "FUTURE","Future status didnt verify, when there is end date");
            }
            else if (startDateFormatted.compareTo(curDateFormatted) < 0 & endDateFormatted.compareTo(curDateFormatted)<0) {
                assertEquals(status, "INACTIVE", "Inactive status didnt verify, when there is end date");
            }
            else if (startDateFormatted.compareTo(curDateFormatted) < 0) {
                assertEquals(status, "ACTIVE","Active status didnt verify, when there is end date");
            }
            else if (endDateFormatted.compareTo(curDateFormatted) > 0) {
                assertEquals(status, "INACTIVE","Inactive status didnt verify, when there is end date");
            }
        } else if(endDate.isEmpty()){
            assertEquals(status, "ACTIVE","Active status didnt verify, when there is no end date");
        }
    }
    private String getDateFuture(String dateType) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        if (dateType.equals("startDate"))
            cal.add(Calendar.DATE, 2);
        else if (dateType.equals("endDate"))
            cal.add(Calendar.DATE, 10);
        String dateFuture = dateFormat.format(cal.getTime());
        System.out.println(dateType + " Future: " + dateFuture);

        return dateFuture;
    }

    @When("I click on consumer record to navigate Profile Details")
    public void i_click_on_consumer_record_to_navigate_Profile_Details() {
        waitFor(2);
        expectedConsumerName.set(consumerRecord.consumerFirstName.getText() + " " + consumerRecord.consumerLastName.getText());
        waitFor(1);
        if(consumerRecord.crmCaseIdValue.getText().equals("--")){
            jsClick(consumerRecord.crmConsumerIdValue);
        }else{
            waitFor(2);
            jsClick(consumerRecord.crmCaseIdValue);
        }


    }

    @Then("I click on Edit button for Profile Details")
    public void i_click_on_Edit_button_for_Profile_Details() {
        waitFor(3);
       jsClick(editconsumer.consumerEditButton);
    }

    @Then("I click on Save button for Profile Details")
    public void i_click_on_Save_button_for_Profile_Details() {
        editconsumer.saveConsumerUpdate.click();
       // crm_contactRecordUIStepDef.i_click_on_continue_button_on_warning_message();
        waitFor(2);
        String successMessage = contactRecord.noparamsText.getText();
        assertEquals("Consumer Profile successfully updated", successMessage);
        waitFor(5);
    }

    @Then("I click on Cancel button on Profile Details page and {string}")
    public void i_click_on_Cancel_button_on_Profile_Details_page_and(String button) {
        waitFor(2);
        editconsumer.cancelConsumerUpdate.click();
        waitFor(2);

        assertTrue(editconsumer.warningText.isDisplayed(),"Error message with correct context isnt displayed");

        if(button.equals("Cancel")) {
            editconsumer.cancelPopUp.click();
            waitFor(1);
            assertTrue(editconsumer.profileContactPage.isDisplayed());
        }
        else if(button.equals("Continue")){
            editconsumer.continuePopUp.click();
            waitFor(1);
            assertTrue(editconsumer.profileContactPage.isDisplayed());
            assertTrue(editconsumer.consumerNameHeader.isDisplayed());
        }

    }



}
