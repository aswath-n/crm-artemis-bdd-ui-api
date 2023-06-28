package com.maersk.crm.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.utilities.World.getWorld;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class CRM_CreateConsumerProfileStepDef extends CRMUtilities implements ApiBase {
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMAddAuthorizedRepresentativePage AuthorizedRep = new CRMAddAuthorizedRepresentativePage();

    public final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    final ThreadLocal<String> expectedName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString() + " " + newConsumer.get().get("name").toString().charAt(1) + " " + newConsumer.get().get("surname").toString());
    private FillOutForm fillOutForm = new FillOutForm();
    private ReadDataFromForm readDataFromForm = new ReadDataFromForm();
    //    private CRMUtilities crmUtilities = new CRMUtilities();
    private CRMConsumerSearchResultPage consumerSearchResultPage = new CRMConsumerSearchResultPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> inboundCallQueueOption = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> callCampaignOption = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> outcomeCallOption = ThreadLocal.withInitial(String::new);
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    public static final ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerPrimaryPhone = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerPrimaryEmail = ThreadLocal.withInitial(String::new);
    CRMCreateGeneralTaskPage crmCreateGeneralTaskPage = new CRMCreateGeneralTaskPage();
    CRMEditConsumerProfilePage crmEditConsumerProfilePage = new CRMEditConsumerProfilePage();
    CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    CRMDemographicContactInfoPage crmDemographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRM_Edit_ContactRecord_Page edit_cont_Rec = new CRM_Edit_ContactRecord_Page();
    public static final ThreadLocal<String> commPref_createdBy = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> commPref_createdOn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> commPref_effectiveStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> commPref_effectiveEndDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> commPref_updatedOn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> commPref_updatedBy = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> offerderIDValue = ThreadLocal.withInitial(String::new);


    @When("I click on Search Button")
    public void i_click_on_Search_Button() {
        waitFor(2);
        contactRecord.searchButton.click();
    }

    @When("I click on Search Button on Search Consumer Page")  //added new step Definition
    public void i_click_on_Search_Button_on_Search_Consumer_Page() {
        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        try {
            contactRecord.searchButton.click();
        } catch (Exception e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(2);
    }

    @Then("I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI")
    public void i_am_able_to_see_Add_Consumer_button_and_I_click_on_it_to_navigate_to_Create_Consumer_UI() {
        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
    }

    @Then("I verify {string} is enabled in Create Consumer Page")
    public void i_verify_is_enabled_in_Create_Consumer_Page(String string) {
        assertTrue(createConsumer.continueAddConsumer.isDisplayed());
        assertTrue(createConsumer.continueAddConsumer.isEnabled());
    }

    @Then("I verify {string} is disabled in Create Consumer Page")
    public void i_verify_is_disabled_in_Create_Consumer_Page(String string) {
        assertTrue(!isElementDisplayed(createConsumer.continueAddConsumer));
    }

    @Then("I ensure {string} is available in Phone Number Type dropdown")
    public void iEnsureIsAvailableInPhoneNumberTypeDropdown(String value) {
        waitFor(2);
        assertTrue(createConsumer.phoneType.isDisplayed(), "Phone Type field is not displayed");
        click(createConsumer.phoneType);
        waitFor(1);
        List<String> actualL = getElementsText(createConsumer.phoneTypeList);
        Assert.assertTrue(actualL.contains(value), "Fax is missing in Phone Type dropdown Actual-->" + actualL);
    }

    @When("correspondence preference dropdown is displayed")
    public void correspondence_preference_dropdown_is_displayed() {
        assertTrue(createConsumer.correspondencePreferenceDropDownText.isDisplayed(), "Consumer Preference dropdown is displayed");
        System.out.println("1");
    }

    @When("correspondence preference dropdown is displayed and {string} option is selectable")
    public void correspondence_preference_dropdown_is_displayed_and_option_is_selectable(String string) {
        assertTrue(createConsumer.correspondencePreferenceDropDownText.isDisplayed(), "Consumer Preference dropdown is not displayed");
        selectOptionFromMultiSelectDropDown(createConsumer.correspondencePreferenceDropDown, "Paperless");
        assertTrue(createConsumer.corresPrefDropDownOptionPaperless.isDisplayed(), "Consumer Preference dropdown paperless is not selected");
    }

    @When("I enter Search criteria fields for a new consumer")
    public void i_enter_Search_criteria_fields_for_a_new_consumer() {

        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
//        assertTrue(newConsumer.get().get("name").toString().equalsIgnoreCase(contactRecord.firstName.getAttribute("value")),
//                "First Name field does not have expected" + newConsumer.get().get("name") + "value");
//        assertTrue(newConsumer.get().get("surname").toString().equalsIgnoreCase(contactRecord.lastName.getAttribute("value")),
//                "Last Name field does not have expected" + newConsumer.get().get("name") + "value");
    }

    @When("I enter case id to be linked to the Inbound correspondence")
    public void i_enter_case_id_to_be_linked_to_IBC() {
        clearAndFillText(contactRecord.searchCaseID, World.generalSave.get().get("caseConsumerCaseId").toString());
        waitFor(1);
    }

    @Then("I see previously populated criteria fields are auto-filled")
    public void i_see_previously_populated_criteria_fields_are_auto_filled() {
        assertTrue(newConsumer.get().get("name").toString().equalsIgnoreCase(createConsumer.consumerFN.getAttribute("value")),
                "First Name field does not have expected" + newConsumer.get().get("name") + "value");
        assertTrue(newConsumer.get().get("surname").toString().equalsIgnoreCase(createConsumer.consumerLN.getAttribute("value")),
                "Last Name field does not have expected" + newConsumer.get().get("name") + "value");
    }

    @Then("I see all fields and buttons present")
    public void i_see_all_fields_and_buttons_present() {
        assertTrue(createConsumer.consumerFN.isDisplayed(), "First name field is not displayed");
        assertTrue(createConsumer.consumerLN.isDisplayed(), "Last name field is not displayed");
        assertTrue(createConsumer.consumerMI.isDisplayed(), "Middle initial field is not displayed");
        // assertTrue(createConsumer.consumerUniqueId.isDisplayed(), "Unique ID field is not displayed");
        assertTrue(createConsumer.consumerDB.isDisplayed(), "Date of birth field is not displayed");
        assertTrue(createConsumer.consumerSSN.isDisplayed(), "SSN field is not displayed");
        assertTrue(createConsumer.consumerPhoneNum.isDisplayed(), "Phone number field is not displayed");
        assertTrue(createConsumer.consumerEmail.isDisplayed(), "Email field is not displayed");
        assertTrue(createConsumer.consumerGender.isDisplayed(), "Gender dropdown field is not displayed");
//        assertTrue(createConsumer.commOptIn.isDisplayed(), "Communication option check-mark field is not displayed");
        assertTrue(createConsumer.commOptPhone.isDisplayed(), "Communication option PHONE check-mark field is not displayed");
        assertTrue(createConsumer.commOptText.isDisplayed(), "Communication option TEXT check-mark field is not displayed");
        assertTrue(createConsumer.commOptEmail.isDisplayed(), "Communication option EMAIL check-mark field is not displayed");
        assertTrue(createConsumer.consumerAddressType.isDisplayed(), "Address type field is not displayed");
        assertTrue(createConsumer.consumerAddress1.isDisplayed(), "Address 1 field is not displayed");
        assertTrue(createConsumer.consumerAddress2.isDisplayed(), "Address 2 field is not displayed");
        assertTrue(createConsumer.consumerCity.isDisplayed(), "City field is not displayed");
        assertTrue(createConsumer.consumerCountyField.isDisplayed(), "County dropdown field is not displayed");
        assertTrue(createConsumer.consumerStateField.isDisplayed(), "State dropdown field is not displayed");
        assertTrue(createConsumer.consumerZipCode.isDisplayed(), "Zip code field is not displayed");
        assertTrue(createConsumer.cancelConsumerButton.isDisplayed(), "Cancel button is not displayed");
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Create Consumer button is not displayed");
//        assertTrue(createConsumer.createCaseButton.isDisplayed(), "Create Case button is not displayed");

    }

    @When("I click on Cancel Button on Create Consumer Page")
    public void i_click_on_Cancel_Button_on_Create_Consumer_Page() {
        createConsumer.cancelConsumerButton.click();
        waitFor(5);
        click(createConsumer.warningMessageContinueButton);
    }

    @Then("I am navigated back to Contact Record UI page")
    public void i_am_navigated_back_to_Contact_Record_UI_page() {
        assertTrue(activeContact.contactRecordHeader.isDisplayed(), "Unlink contact record sign is not displayed");
    }

    @Then("I am navigated back to Search Contact Record UI page")
    public void i_am_navigated_back_to_Search_Contact_Record_UI_page() {
        waitFor(2);
        scrollUpUsingActions(5);
        waitFor(2);
        assertTrue(activeContact.consumerFirstName.isDisplayed(), "User is not navigated to search for contact record UI");
    }

    @When("I populate Create Consumer fields for a new consumer")
    public void i_populate_Create_Consumer_fields_for_a_new_consumer() {
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);
//        selectRandomDropDownOption(createConsumer.consumerCountyField);

        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);
        if (createConsumer.checkedOptMail.getAttribute("checked") != null)
            click(createConsumer.optInMailText);
        //Refactored by Shruti
        // clearAndFillText(createConsumer.consumerZipCode, ("12345"));
        //clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

    }

    @Then("I add phone number to an existing consumer")
    public void i_add_phone_number_to_an_existing_consumer() {
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
    }

    @When("I searched customer have First Name as created above and Last Name as created above")
    public void i_searched_customer_have_First_Name_as_created_above_and_Last_Name_as_created_above() {
        clearAndFillText(contactRecord.firstName, newConsumer.get().get("name").toString());
        clearAndFillText(contactRecord.lastName, newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        click(contactRecord.searchButton);
    }

    @When("I populate Create Consumer fields for a new consumer with Email Address")
    public void i_populate_Create_Consumer_fields_for_a_new_consumer_with_Email_Address() {
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        //        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerEmail, (newConsumer.get().get("email").toString()));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        //        selectRandomDropDownOption(createConsumer.consumerAddressType);
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
    }

    @When("I populate Create Consumer fields for a new consumer  and select single {string} option for {string}")
    public void i_populate_Create_Consumer_fields_for_a_new_consumer_and_select_single_option_for(String string, String string2) {
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        selectOptionFromMultiSelectDropDown(createConsumer.correspondencePreferenceDropDown, "CORRESPONDENCE_PAPERLESS");
        assertTrue(createConsumer.corresPrefDropDownOptionPaperless.isDisplayed(), "Consumer Preference dropdown paperless is selected");
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        //        selectRandomDropDownOption(createConsumer.consumerAddressType);
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
    }


    @When("I click on Create Consumer Button on Create Consumer Page")
    public void i_click_on_Create_Consumer_Button_on_Create_Consumer_Page() {
        staticWait(500);
        createConsumer.createConsumerButton.click();
        waitFor(3);
        scrollUpUsingActions(2);
        waitForVisibility(contactRecord.lblCrmConsumerId, 30);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        waitFor(3);
    }

    @When("I select one option from inbound call queue dropdown")
    public void i_select_one_option_from_inbound_call_queue_dropdown() {
        selectRandomOptionFromMultiSelectDropDown(createConsumer.inboundCallQueueDropDownSelection);
        System.out.println("1");
        waitFor(4);
        //createConsumer.saveButton.click();
        selectRandomOptionFromMultiSelectDropDown(createConsumer.programTypeContactDetails);
        System.out.println("program");
        waitFor(2);
        clearAndFillText(createConsumer.phoneContactDetails, (newConsumer.get().get("phone").toString()));
        System.out.println("phone");
        waitFor(2);
        selectRandomOptionFromMultiSelectDropDown(createConsumer.dispositionContactDetails);
        System.out.println("transfer");
        waitFor(2);
        jsClick(createConsumer.saveButton);
        System.out.println("2");
        inboundCallQueueOption.set(createConsumer.inboundCallQueueDropDownSelection.getText());
        System.out.println("inbound call selection " + inboundCallQueueOption.get());
    }

    @When("I select one option from outbound call queue dropdown")
    public void i_select_one_option_from_outbound_call_queue_dropdown() {
        selectOptionFromMultiSelectDropDown(createConsumer.contactTypeContactDetails, "Outbound");
        selectRandomOptionFromMultiSelectDropDown(createConsumer.callCampaignDropDown);
        waitFor(2);
        selectRandomOptionFromMultiSelectDropDown(createConsumer.outcomeOfContactDropDown);
        waitFor(2);
        selectRandomOptionFromMultiSelectDropDown(createConsumer.programTypeContactDetails);
        System.out.println("program");
        waitFor(2);
        clearAndFillText(createConsumer.phoneContactDetails, (newConsumer.get().get("phone").toString()));
        System.out.println("phone");
        waitFor(2);
        jsClick(createConsumer.saveButton);
        System.out.println("2");
        callCampaignOption.set(createConsumer.callCampaignDropDown.getText());
        System.out.println("call campaign " + callCampaignOption);
        outcomeCallOption.set(createConsumer.outcomeOfContactDropDown.getText());
        System.out.println("outcome call  " + outcomeCallOption.get());
    }

    //By Vinuta, Method to validate invalid details error messages for mandatory fields in Create Consumer page
    @Then("I see mandatory fields highlighted with error messages")
    public void i_see_mandatory_fields_highlighted_with_error_messages() {
        assertTrue(createConsumer.fillOutErrorHeaderMandatory.isDisplayed(), "Error header is not displayed");
        assertTrue(createConsumer.invalidPhoneError.isDisplayed(), "Invalid phone number error is not displayed");
        assertTrue(createConsumer.invalidZipCodeError.isDisplayed(), "Invalid Zip Code error is not displayed");
    }

    //By Vinuta, Method to validate invalid details error messages for optional fields in Create Consumer page
    @Then("I see optional fields highlighted with error messages")
    public void i_see_optional_fields_highlighted_with_error_messages() {
        highLightElement(createConsumer.fillOutErrorHeaderOptional);
        assertTrue(createConsumer.fillOutErrorHeaderOptional.isDisplayed(), "Error header is not displayed");
        highLightElement(createConsumer.invalidEmailError);
        assertTrue(createConsumer.invalidEmailError.isDisplayed(), "Invalid Email error is not displayed");
        scrollUpUsingActions(4);
        assertTrue(createConsumer.invalidDobError.isDisplayed(), "Invalid Date Of Birth error is not displayed");
        highLightElement(createConsumer.invalidSsnError);
        assertTrue(createConsumer.invalidSsnError.isDisplayed(), "Invalid SSN error is not displayed");

    }

    //By Vinuta, Method to enter invalid details into phone & zipcode fields
    @Then("I populate invalid phone,zipcode")
    public void i_populate_invalid_phone_zipcode() {
        clearAndFillText(createConsumer.consumerPhoneNum, "123");
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        //        selectRandomDropDownOption(createConsumer.consumerAddressType);
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, "123");
    }

    //By Vinuta, Method to enter invalid details into dob,ssn,email fields
    @Then("I populate invalid date, ssn, email")
    public void i_populate_invalid_date_ssn_email() {
        scrollUpUsingActions(6);
        waitFor(3);
        createConsumer.consumerDB.clear();
        clearAndFillText(createConsumer.consumerDB, "13");
        waitFor(2);
        clearAndFillText(createConsumer.consumerSSN, "13");
        scrollDownUsingActions(4);
        waitFor(3);
        clearAndFillText(createConsumer.consumerEmail, "test");

    }

    @Then("I am navigated to active contact page")
    public void i_am_navigated_to_active_contact_page() {
        assertTrue(contactRecord.contactRecordSign.isDisplayed());
        waitFor(1);
    }

    @Then("I see a unique Consumer Profile ID generated")
    public void i_see_a_unique_Consumer_Profile_ID_generated() {
        activeContact.consumerID.isDisplayed();
    }


    @And("I click on Unlink Contact Button on Active Contact Page")
    public void i_click_on_Unlink_Contact_Button_on_Active_Contact_Page() {
        waitFor(1);
        assertTrue(activeContact.unlinkContactRecord.isDisplayed());
        highLightElement(activeContact.unlinkContactRecord);
        scrollUpUsingActions(5);
        activeContact.unlinkContactRecord.click();
    }

    @When("I enter random {string} Search criteria fields for a new consumer")
    public void i_enter_random_Search_criteria_fields_for_a_new_consumer(String value) {
        clearAndFillText(contactRecord.firstName, value);
        clearAndFillText(contactRecord.lastName, value);
    }

    @Then("I see Consumer already exists, please associate existing Consumer to Case message displayed")
    public void iSeeConsumerAlreadyExistsPleaseAssociateExistingConsumerToCaseMessageDisplayed() throws Throwable {
        assertTrue(createConsumer.duplicateConsumererrorMessage.isDisplayed(), "Duplicate Error message was not present");
    }

    @And("I clear all fields values")
    public void iClearAllFieldsValues() throws Throwable {
        waitFor(3);
        clearFiled(createConsumer.consumerFN);
        //  clearAndFillText(createConsumer.consumerFN, " ");
        staticWait(100);
        //     clearAndFillTextWithActionClass(createConsumer.consumerLN, " ");
        clearFiled(createConsumer.consumerLN);

    }

    @Then("I see Please fill in the required field error message displayed")
    public void i_see_Please_fill_in_the_required_field_error_message_displayed() {
        highLightElement(createConsumer.enterValueMainWarning);
        createConsumer.enterValueMainWarning.isDisplayed();
        assertTrue(createConsumer.enterValueMainWarning.isDisplayed(), "Error not displayed");
    }

    /*Refactoring 09/23/18*/
    @Then("I see new consumer is created and has a unique Consumer ID")
    public void i_see_new_consumer_is_created_and_has_a_unique_Consumer_ID() {
        waitFor(1);
        assertTrue(activeContact.consumerID.isDisplayed(), "Consumer ID is not displayed");
        String expectedFirstName = newConsumer.get().get("name").toString();
        String expectedLastName = newConsumer.get().get("surname").toString();
        String expectedMI = newConsumer.get().get("name").toString().substring(1, 2);
        String expectedFullName = expectedFirstName + " " + expectedMI + " " + expectedLastName;
//        assertTrue(activeContact.fullName.getText().equalsIgnoreCase(expectedFullName), "Created Consumer has different name");
    }

    @When("I see {string} field accept {int} characters in total")
    public void i_see_field_accept_characters_in_total(String field, Integer textLength) {

        String actual = "";
        String sentText = createTextString(textLength * 4);
        switch (field) {
            case "addressLine1":
                clearAndFillText(createConsumer.consumerAddress1, sentText);
                waitFor(1);
                actual = createConsumer.consumerAddress1.getAttribute("value");
                break;
            case "addressLine2":
                clearAndFillText(createConsumer.consumerAddress2, sentText);
                waitFor(1);
                actual = createConsumer.consumerAddress2.getAttribute("value");
                break;
            case "city":
                clearAndFillText(createConsumer.consumerCity, sentText);
                waitFor(1);
                actual = createConsumer.consumerCity.getText();
                break;
            case "county":
                createConsumer.consumerCounty.sendKeys(sentText);
                waitFor(1);
                actual = createConsumer.consumerCounty.getText();
                break;
        }
        assertTrue(actual.length() <= textLength, "Field accepts text which is not " + textLength + " characters long");
    }


    @Then("I should be able to see all states in State dropdown are available")
    public void i_should_be_able_to_see_all_states_in_State_dropdown_are_available() {
        assertTrue(stateDropdownHasAllAbbr(createConsumer.consumerState), "State dropdown has incorrect value/s");
    }


    @Then("I verify {string} fields being marked mandatory")
    public void i_verify_fields_being_marked_mandatory(String string, List<String> options) {
        String actual = "";
        for (String option : options) {
            actual = "";
            switch (option) {
                case "Consumer First Name":
                    actual = createConsumer.firstNameLabel.getText();
                    System.out.println(createConsumer.firstNameLabel.getText());
                    break;
                case "Consumer Last Name":
                    actual = createConsumer.lastNameLabel.getText();
                    System.out.println(createConsumer.lastNameLabel.getText());
                    break;
                case "Zip Code":
                    actual = createConsumer.zipCodeLabel.getText();
                    break;
                case "Phone Number":
                    actual = createConsumer.phoneNumberLabel.getText();
                    break;
                case "Consumer DOB":
                    actual = createConsumer.consumerDOBLabel.getText();
                    System.out.println(createConsumer.consumerDOBLabel.getText());
                    break;
                case "Consumer Type":
                    actual = createConsumer.consumerTypeLabel.getText();
                    System.out.println(createConsumer.consumerTypeLabel.getText());
                    break;
                case "Written Language":
                    actual = createConsumer.writtenLanguageLabel.getText();
                    System.out.println(createConsumer.writtenLanguageLabel.getText());
                    break;
                case "Spoken Language":
                    actual = createConsumer.spokenLanguageLabel.getText();
                    System.out.println(createConsumer.spokenLanguageLabel.getText());
                    break;
                case "SSN":
                    actual = createConsumer.ssnLabel.getText();
                    System.out.println(createConsumer.ssnLabel.getText());
                    break;
                case "Gender":
                    actual = createConsumer.genderLabel.getText();
                    System.out.println(createConsumer.genderLabel.getText());
                    break;

            }
            assertTrue(actual.endsWith("*"), option + " field is not marked mandatory");
        }
    }

    @When("I see {string} field accepts up to {int} digits")
    public void i_see_field_accepts_up_to_digits(String field, Integer length) {
        clearAndFillText(createConsumer.consumerZipCode, "qaws234dfert567hfug999jg044k-jj55");
        assertTrue(hasOnlyDigits(createConsumer.consumerZipCode.getAttribute("value")),
                field + " has to accept digits only.");
        assertTrue(createConsumer.consumerZipCode.getAttribute("value").replace("-", "").length() <= 9,
                field + " has to accept up to " + length + " digits only.");

    }

    //refactoring 10/17/18
    @When("I see {int} digits required and {int} digits optional format")
    public void i_see_digits_required_and_digits_optional_format(Integer five, Integer nine) {
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        String testZipCode = "1";
        int i;

        for (i = 1; i < 9; i++) {
            if (i >= five) {
                createConsumer.createConsumerButton.click();
                createConsumer.consumerZipCode.sendKeys(testZipCode);
                waitFor(1);
                createConsumer.createConsumerButton.click();

            } else {
                createConsumer.consumerZipCode.sendKeys(testZipCode);
                waitFor(1);
                createConsumer.createConsumerButton.click();
                waitFor(1);
                //Failing due error message change
                assertTrue(createConsumer.fillOutErrorZipCode.isDisplayed(), "Error message is not displayed");
            }
        }
    }

    @Then("I see {string} field accept only alphanumeric characters")
    public void i_see_field_accept_only_alphanumeric_characters(String string) {
        String actual = "";
        clearAndFillText(createConsumer.consumerUniqueId, createTextString(2));
        actual = createConsumer.consumerUniqueId.getAttribute("value").trim();
        assertTrue(isAlphanumeric(actual), "Unique ID field accepts non-alphanumeric characters");
    }

    @Then("I am able to see Add Consumer button on Consumer Search Results Page")
    public void i_am_able_to_see_Add_Consumer_button() {
        highLightElement(createConsumer.addConsumerButton);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer Button is not displayed");
    }

    @When("I enter Search criteria fields for the new consumer created")
    public void i_enter_Search_criteria_fields_for_new_consumer_created() {
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        assertTrue(newConsumer.get().get("name").toString().equalsIgnoreCase(contactRecord.firstName.getAttribute("value")),
                "First Name field does not have expected" + newConsumer.get().get("name") + "value");
        assertTrue(newConsumer.get().get("surname").toString().equalsIgnoreCase(contactRecord.lastName.getAttribute("value")),
                "Last Name field does not have expected" + newConsumer.get().get("name") + "value");
    }

    @Then("Add Consumer button is not displayed on Consumer Search Results Page")
    public void Add_Consumer_button_is_not_displayed_on_Consumer_Search_Results_Page() {
        //createConsumer.addConsumerButton.getAttribute("aria-hidden").equals("true"
//        assertEquals(true, IsElementDisplayed(createConsumer.addConsumerButton), "Add Consumer Button is not displayed");
        assertFalse("Add Consumer Button is displayed", isElementDisplayed(createConsumer.addConsumerButton));

    }

    @When("I click on Search Button without entering search parameters")
    public void i_click_on_Search_Button_without_entering_search_parameters() {
        contactRecord.searchButton.click();
        waitFor(3);
    }


    @When("I verify latest Contact Record appears on Contact History table")
    public void i_verify_latest_Contact_Record_appears_on_Contact_History_table() {
        waitFor(3);
        assertEquals(contactHistory.datesHistory.get(0).getText(), getCurrentDate());
        assertTrue(contactHistory.types.get(0).getText().equals("General"));
        System.out.println(expectedName.get());
        System.out.println(contactHistory.namesHistory.get(0).getText());
        assertTrue(contactHistory.namesHistory.get(0).getText().equalsIgnoreCase(expectedName.get()));
        assertEquals(contactHistory.reasonListHistory.get(1).getText(), "Materials Request");

    }

    @Then("I verify previous Contact Record appears on Contact History table after latest record")
    public void i_verify_previous_Contact_Record_appears_on_Contact_History_table_after_latest_record() {
        assertTrue(contactHistory.datesHistory.get(2).getText().equals(getCurrentDate()));
        assertTrue(contactHistory.namesHistory.get(2).getText().equalsIgnoreCase(expectedName.get()));
        assertTrue(contactHistory.reasonListHistory.get(2).getText().equals("Update Information Request"));

    }

    // new -StepDef for CRM-864 ; Creation Date 11/12/18
    @When("I select Email Opt in check box")
    public void i_select_Email_Opt_in_check_box() {
//        createConsumer.commOptIn.click();
        createConsumer.commOptEmail.click();
        waitFor(1);

    }

    @Then("I see Email becomes mandatory")
    public void i_see_Email_becomes_mandatory() {
        assertTrue(createConsumer.mandatoryEmail.isDisplayed(), "Email is not mandatory");

    }

    @When("I select Email Opt in check box and leave Email field blank and click on create")
    public void i_select_Email_Opt_in_check_box_and_leave_Email_field_blank_and_click_on_create() {
//        createConsumer.commOptIn.click();
        createConsumer.commOptEmail.click();
        createConsumer.createConsumerButton.click();
    }

    @Then("I verify Error is displayed for Email address field")
    public void i_verify_Error_is_displayed_for_Email_address_field() {
        assertTrue(createConsumer.fillOutErrorEmail.isDisplayed());
    }

    @When("I select Email Opt out check box and leave Email field blank and click on create")
    public void i_select_Email_Opt_out_check_box_and_leave_Email_field_blank_and_click_on_create() {
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
//        createConsumer.commOptIn.click();
        createConsumer.commOptText.click();
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        createConsumer.createConsumerButton.click();
    }

    @Then("I verify Error is not displayed for Email address field")
    public void i_verify_Error_is_not_displayed_for_Email_address_field() {
        assertEquals(true, IsElementDisplayed(createConsumer.fillOutErrorEmail), "Fill out email error not displayed");

    }

    @Then("I see Consumer Role and Preferred Language fields displayed")
    public void i_see_Consumer_Role_and_Preferred_Language_fields_displayed() {
        assertTrue(activeContact.defaultConsumerRole.isDisplayed(), "Default Consumer Type is not displayed");
        assertTrue(activeContact.defaultPreferredLang.isDisplayed(), "Default Preferred Language is not displayed");
    }

    @Then("I see default value {string} for Consumer Type and {string} for Preferred Language displayed")
    public void i_see_default_value_for_Consumer_Type_and_for_Preferred_Language_displayed(String expectedConsumerType, String expectedLanguage) {
        //this line is disabled due to UI changes with release 16.0 CRM-1289
//        assertTrue(activeContact.defaultMember.getText().equals(expectedConsumerType), "Actual Consumer Type is not equal to expected");
        assertTrue(activeContact.defaultEnglish.getText().equals(expectedLanguage), "Actual Preferred Language is not equal to expected");
    }

    @When("I capture current contact phone number")
    public void i_capture_current_contact_phone_number() {
        clearAndFillText(activeContact.contactPhoneNumber, newConsumer.get().get("phone").toString());
    }

    @Then("I can view the Contact Record's CaseConsumer Unique IDs")
    public void i_can_view_the_Contact_Record_s_Case_Consumer_Unique_IDs() {
        assertTrue(activeContact.consumerID.isDisplayed());
    }

    @Then("I see the error that date does not exists")
    public void date_does_not_exists() {
        assertTrue(activeContact.notExistDateError.isDisplayed(), "Date does not exist error is not displayed");
        waitFor(2);
    }

    @Then("I see the error that DOB is in future")
    public void dob_in_future_error() {
        assertTrue(activeContact.futureDOBError.isDisplayed(), "DB is in future error is not displayed");
    }

    @And("I enter dob as {string} and click on Save")
    public void enter_dob_save(String dob) {
        waitFor(2);
        scrollUpUsingActions(1);
        createConsumer.consumerDB.clear();
        clearAndFillText(createConsumer.consumerDB, dob);
        createConsumer.createConsumerButton.click();
    }

    @When("I enter only special character in Address Line one field")
    public void i_enter_only_special_character_in_Address_Line_one_field() {

        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Cell");
        clearAndFillText(createConsumer.consumerZipCode, "12345");
        clearAndFillText(createConsumer.consumerAddress1, "###***///--  ");
        createConsumer.createConsumerButton.click();
    }

    @Then("I verify error is displayed for the Address Line one")
    public void i_verify_error_is_displayed_for_the_Address_Line_one() {
        assertTrue(createConsumer.addressLineOneError.isDisplayed(), "Error message not displayed for Address Line 2");
    }

    @When("I enter only special character in Address Line two field")
    public void i_enter_only_special_character_in_Address_Line_two_field() {
        clearAndFillText(createConsumer.consumerDB, getPriorDate(300));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Cell");
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, "12345");
        clearAndFillText(createConsumer.consumerAddress2, "###***///--  ");
        createConsumer.createConsumerButton.click();
    }

    @Then("I verify error is displayed for the Address Line two")
    public void i_verify_error_is_displayed_for_the_Address_Line_two() {
        waitFor(1);
        //highLightElement(createConsumer.addressLineTwoError);
        //createConsumer.addressLineTwoError.isDisplayed();
        assertTrue(createConsumer.addressLineTwoError.isDisplayed(), "Error message not displayed for Address Line One");
    }

    @And("I search for a consumer that does not exist")
    public void searchForNonExistingConsumerStep() {
        waitFor(3);
        clearAndFillText(createConsumer.firstNameSearch, RandomStringUtils.random(8, true, false));
        contactRecord.searchButton.click();
        waitFor(3);
    }

    @And("I Create a Random Consumer")
    public void createRandomConsumerUIStep() {
        createConsumer.addConsumerButton.click();
        waitForVisibility(createConsumer.consumerFN, 3);
        fillOutForm.createConsumerForm(createConsumer);
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        scrollToElement(createConsumer.createConsumerButton);
        createConsumer.createConsumerButton.click();
    }

    @And("I log the inbound contact record with the following values:")
    public void logInboundContactRecordStep(Map<String, String> dataTable) {
        scrollToElement(contactRecord.bottomCrmLogo);
        fillOutForm.contactDetailsInbound(contactRecord, dataTable);
    }

    @And("I log the outbound contact record with the following values:")
    public void logOutboundContactRecordStep(Map<String, String> dataTable) {
        scrollToElement(contactRecord.bottomCrmLogo);
        fillOutForm.contactDetailsOutbound(contactRecord, dataTable);
    }

    /*
        Send keys in dataTable in this format:
        contactReason1 contactAction1 reasonComments1
        contactReason2 contactAction2 reasonComments2
        Keep this format and increase number for each set of reasons values
     */
    @And("I log the contact record reasons with the following values:")
    public void logContactRecordCommentStep(Map<String, String> dataTable) {
        scrollUpUsingActions(2);
        fillOutForm.addContactReason(contactRecord, dataTable.size() / 3, dataTable);
        contactRecord.stopContact.click();
        // readDataFromForm.readContactRecordTimes(contactRecord);
        scrollToElement(contactRecord.bottomCrmLogo);
//        expandIfNecesary(contactRecord.expandWrapUpAndClose, "aria-expanded", "false");
        staticWait(500);
        contactRecord.closeButton.click();
    }

    @And("I am logged out of the system")
    public void logoutStep() {
        logout();
    }

    @And("I search for and access the previously created consumer")
    public void searchForExistingConsumerStep() {
        waitFor(3);
        clearAndFillText(createConsumer.firstNameSearch, getWorld().contactBean.get().getRandomConsumer().get("firstName"));
        clearAndFillText(createConsumer.lastNameSearch, getWorld().contactBean.get().getRandomConsumer().get("lastName"));
        contactRecord.searchButton.click();
        scrollToElement(contactRecord.bottomCrmLogo);
        readDataFromForm.selectPreviouslyCreatedConsumerFromConsumerSearch(consumerSearchResultPage);
    }

    @When("I select contact program type as {string}")
    public void iSelectProgramType(String program) {
        selectOptionFromMultiSelectDropDown(activeContact.programTypes, program);
    }

    @When("I click on Create Consumer Button")
    public void i_click_on_Create_Consumer_Button() {
        waitFor(4);
        createConsumer.createConsumerButton.click();
        //commented out this part, since it doesnt see error message to catch exception
//        try {
//            createConsumer.optInWarningContinueButton.click();
//        } catch (Exception e) {
//            System.out.println("Click after continue button is not needed");
//        }
        waitFor(1);

    }

    @Then("I verify Create Consumer UI is inline with the application by checking on hidden element from Contact Record UI page")
    public void i_verify_Create_Consumer_UI_is_inline_with_the_application_by_checking_on_hidden_element() {
        assertTrue(isElementDisplayed(contactRecord.firstName), "First Name WebElement from Contact Record UI page is Displayed");

    }

    @Then("I verify Back Arrow button is displayed and has {string} text")
    public void i_verify_Back_Arrow_button_is_displayed_and_has_text(String text) {
        assertTrue(createConsumer.backArrow.isDisplayed(), "Navigate away Back Arrow is not Display");
        System.out.println(createConsumer.backArrowText.getText());
        assertTrue(createConsumer.backArrowText.getText().equals(text), "UI text next to Back Arrow on Create Consumer page should be " + text + ".");
    }


    @When("I click on Back Arrow to navigate away from Create Consumer Page")
    public void i_click_on_Back_Arrow_to_navigate_away_from_Create_Consumer_Page() {
        waitForClickablility(createConsumer.backArrowText, 5);
        createConsumer.backArrowText.click();
        //    waitForVisibility(createConsumer.warningMessageContinueButton, 10);
        //createConsumer.optInWarningContinueButton.click();
        waitFor(2);
        //     createConsumer.warningMessageContinueButton.click();
    }

    @Then("I verify system navigates back to Contact Record UI")
    public void i_verify_system_navigates_back_to_Contact_Record_UI() {
        assertTrue(contactRecord.firstName.isDisplayed(), "Is not navigated to Contact Record UI to view Consumer First name filed.");
        assertTrue(contactRecord.searchButton.isDisplayed(), "Is not navigated to Contact Record UI to view Search Consumer Button.");
    }

    @When("I click on navigate back arrow on updated Create consumer profile page")
    public void i_click_on_navigate_back_arrow_on_updated_Create_consumer_profile_page() {
        assertTrue(createConsumer.backArrow.isDisplayed(), "Back arrow on create consumer Profile page is not displayed");
        scrollUpUsingActions(2);
        createConsumer.backArrowText.click();
    }

    @When("I clear Consumer Type option on Updated Create Consumer Profile Page")
    public void i_clear_Consumer_Type_option_on_Updated_Create_Consumer_Profile_Page() {
        clearDropDown(createConsumer.consumerTypeOptions);
        waitFor(2);
        createConsumer.createConsumerButton.click();
    }

    @Then("I see error message if required field {string} is not captured")
    public void i_see_error_message_if_required_field_is_not_captured(String option) {
        createConsumer.checkMandatoryFieldsErrors(option);

    }

    @When("I complete Consumer profile and save with {string}")
    public void i_complete_Consumer_profile_and_save_with(String option) {
        createConsumer.createConsumerWith(option);
    }

    @When("I populate one of the related fields {string} on Create Consumer Profile page")
    public void i_populate_one_of_the_related_fields_on_Create_Consumer_Profile_page(String field) {
        createConsumer.populateOneRelatedField(field);
    }


    @Then("I see {string} Start Date will be defaulted to the Current System Date")
    public void the_Start_Date_will_be_defaulted_to_the_Current_System_Date(String infoType) {
        assertTrue(checkDefaultStatusStartDate(infoType), infoType + " is not defaulted to the Current System Date");
    }

    @Then("I see new {string} field on Updated Create Consumer Profile Page")
    public void i_see_new_field_on_Updated_Create_Consumer_Profile_Page(String field) {
        assertTrue(createConsumer.checkIfFieldIsDisplayed(field), field + " field is not displayed on Create Consumer Profile UI");
    }

    @Then("I see {string} field has default {string} value capture on Updated Create Consumer Profile Page")
    public void i_see_field_has_default_value_capture_on_Updated_Create_Consumer_Profile_Page(String field, String value) {
        assertTrue(createConsumer.fieldHasDefaultValue(field, value), field + " field is not defaulted to " + value);
    }

    @When("I enter Manual Search criteria fields for previously created consumer")
    public void i_enter_Manual_Search_criteria_fields_for_previously_created_consumer() {
        waitForPageToLoad(10);
        waitFor(3);
        clearAndFillText(manualCaseConsumerSearch.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(manualCaseConsumerSearch.lastName, (newConsumer.get().get("surname").toString()));
        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        manualCaseConsumerSearch.searchButton.click();
        try {
            manualCaseConsumerSearch.consumerIDs.get(0).click();
        } catch (Exception e) {
            manualCaseConsumerSearch.cancelButton.click();
            clearAndFillText(manualCaseConsumerSearch.firstName, (newConsumer.get().get("name").toString()));
            clearAndFillText(manualCaseConsumerSearch.lastName, (newConsumer.get().get("surname").toString()));
            manualCaseConsumerSearch.searchButton.click();
            waitFor(1);
            manualCaseConsumerSearch.consumerIDs.get(0).click();
            waitFor(2);
        }
    }

    @And("I enter Manual Search criteria fields consumer First Name as {string} and Last Name as {string}")
    public void i_enter_manual_search_criteria_fields_consumer_first_name_as_something_and_last_name_as_something(String fn, String ln) {
        waitForPageToLoad(10);
        waitFor(3);
        sendKeyToTextField(manualCaseConsumerSearch.lastName, "");
        waitFor(1);
        clearAndFillText(manualCaseConsumerSearch.firstName, (fn));
        clearAndFillText(manualCaseConsumerSearch.lastName, (ln));
        manualCaseConsumerSearch.searchButton.click();
        try {
            //manualCaseConsumerSearch.consumerIDs.get(0).click();
            manualCaseConsumerSearch.caseIDs.get(0).click();
        } catch (Exception e) {
            manualCaseConsumerSearch.cancelButton.click();
            clearAndFillText(manualCaseConsumerSearch.firstName, (fn));
            clearAndFillText(manualCaseConsumerSearch.lastName, (ln));
            manualCaseConsumerSearch.searchButton.click();
            waitFor(1);
            //manualCaseConsumerSearch.consumerIDs.get(0).click();
            manualCaseConsumerSearch.caseIDs.get(0).click();
            waitFor(2);
        }
    }

    @When("I select contact program types as {string} and {string}")
    public void i_select_contact_program_types_as_and(String program1, String program2) {
        selectOptionFromMultiSelectDropDown(activeContact.programTypes, program1);
        selectOptionFromMultiSelectDropDown(activeContact.programTypes, program2);
    }

    @Given("I add new consumer to the record by choosing email as primary")
    public void i_add_new_consumer_to_the_record_by_choosing_email_as_primary() {
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(5);
        scrollUpUsingActions(6);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        waitFor(1);

        jsClick(createConsumer.commOptEmail);
        jsClick(createConsumer.commOptMail);

        jsClick(createConsumer.commOptPhone);
        Assert.assertTrue(isElementDisplayed(createConsumer.commOptEmail), "Email isnt selected");
        System.out.println("Email is selected");

        clearAndFillText(createConsumer.consumerEmail, (newConsumer.get().get("email").toString()));
        i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();

    }

    @Given("I add new consumer to the record by choosing phone as primary")
    public void i_add_new_consumer_to_the_record_by_choosing_phone_as_primary() {
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(5);
        scrollUpUsingActions(6);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());

        jsClick(createConsumer.commOptMail);
        Assert.assertTrue(isElementDisplayed(createConsumer.commOptPhone), "Phone number isnt selected");
        System.out.println("Phone number is selected");

        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");

        waitFor(1);

        i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
    }

    @Then("The consumer I created should be linked to Active Contact")
    public void theConsumerICreatedShouldBeLinkedToActiveContact() {
        assertTrue(activeContact.fullName.isDisplayed());
    }

    @Then("I should see the {string} field in the Profile Details")
    public void i_should_see_the_field_in_the_Profile_Details(String field) {
        assertTrue(demographicContactInfo.checkIfFieldIsDisplayed(field), field + " field is not displayed on Create Consumer Profile UI");
    }

    @Then("I should see following fields in the Profile Details")
    public void i_should_see_following_fields_in_the_Profile_Details() {
        assertTrue(demographicContactInfo.consumerName.isDisplayed());
        assertTrue(demographicContactInfo.dateOfBirth.isDisplayed());
        assertTrue(demographicContactInfo.consumerAge.isDisplayed());
        assertTrue(demographicContactInfo.consumerSSN.isDisplayed());
        assertTrue(demographicContactInfo.consumerGender.isDisplayed());
        assertTrue(demographicContactInfo.spokenLanguage.isDisplayed());
        assertTrue(demographicContactInfo.writtenLanguage.isDisplayed());
        assertTrue(demographicContactInfo.startDate.isDisplayed());
        assertTrue(demographicContactInfo.endDate.isDisplayed());
        assertTrue(demographicContactInfo.correspondencePreferences.isDisplayed());
    }

    @And("I add mandatory DOB field value on Create consumer profile UI Page")
    public void iAddMandatoryDOBFieldValueOnCreateConsumerProfileUIPage() throws Throwable {
        clearAndFillText(createConsumer.consumerDB, getPriorDate(6000));
    }

    @When("I search for consumer profile summary information for viewing consumer profile")
    public void searchForConsumerProfile() {
        waitFor(3);
        manualCaseConsumerSearch.advancedSearchButton.click();
        manualCaseConsumerSearch.firstName.sendKeys(newConsumer.get().get("name").toString());
        waitFor(2);
        manualCaseConsumerSearch.searchButton.click();
        waitFor(2);
        manualCaseConsumerSearch.consumerCancelBtn.click();
        contactRecord.firstName.sendKeys(newConsumer.get().get("name").toString());
        waitFor(2);
        contactRecord.consumerSearchBtn.click();
        waitFor(2);
        waitForVisibility(contactRecord.consumerIdFirstRecord, 10);
    }

    @And("I click on cancel button")
    public void iClickOnCancelButtonOnConsumerProfilePage() {
        waitFor(2);
        click(createConsumer.cancelConsumerButton);
    }

    @Then("I verify the  warning message on dialog box on consumer profile page")
    public void iVerifyTheWarningMessageOnDialogBoxOnConsumerProfilePage() {
        waitFor(2);
        waitForVisibility(createConsumer.cpModalWarningMessage, 2);
        assertTrue(isElementDisplayed(createConsumer.cpModalWarningMessage));
    }

    @Then("I verify there is no warning message on dialog box")
    public void iVerifyNoWarningMessageOnDialogBoxOnConsumerProfilePage() {
        waitFor(2);
        assertFalse(isElementDisplayed(createConsumer.cpModalWarningMessage));
    }

    @And("I verify users stays in same page when clicked on Cancel")
    public void iVerifyUsersStaysInSamePageWhenClickedOnCancel() {
        if (isElementDisplayed(createConsumer.cpModalWarningMessage)) {
            createConsumer.optInWarningCancelButton.click();
        }
        waitForVisibility(createConsumer.createConsumerButton, 2);
        assertTrue(isElementDisplayed(createConsumer.createConsumerButton));
    }

    @And("I verify user is navigated back to previous screen when clicked on Continue")
    public void iVerifyUserIsNavigatedBackToPreviousScreenWhenClickedOnContinue() {
        waitFor(2);
        iClickOnCancelButtonOnConsumerProfilePage();
        waitFor(2);
        if (createConsumer.cpModalWarningMessage.isDisplayed()) {
            createConsumer.optInWarningContinueButton.click();
        }
        waitForVisibility(createConsumer.caseConsumerSearch, 2);
        assertTrue(isElementDisplayed(createConsumer.caseConsumerSearch));
    }


    @And("I click on {string} manual case consumer info page")
    public void clickOnNavigateAwayButton(String button) {
        switch (button) {
            case "Back Arrow":
                waitForVisibility(createConsumer.backArrow, 10);
                click(createConsumer.backArrow);
                break;

            case "Contact Icon":
                hover(crmDashboardPage.contactRecordSearchTab);
                waitFor(2);
                crmDashboardPage.contactRecordSearchTab.click();
                break;
        }

    }


    @Then("I verify if email input field does not accept below invalid test data in create customer page")
    public void iVerifyIfEmailInputFieldDoesNotAcceptBelowInvalidTestData(List<Map<String, String>> data) {
        waitFor(2);
        for (Map<String, String> column : data) {
            boolean isFound = false;
            waitFor(1);
            clearAndFillText(createConsumer.consumerEmail, column.get("InvalidEmail"));
            click(createConsumer.createConsumerButton);
            waitFor(1);

            try {
                isFound = crmAddContactInfoPage.invalidEmailAddressError.isDisplayed();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            assertTrue(isFound, "Email Address invalid error message didn't appeared::" + column);
        }
    }

    @Then("I verify email input field accepts below valid test data in create customer page")
    public void iVerifyEmailInputFieldAcceptsBelowValidTestData(List<Map<String, String>> data) {
        waitFor(2);
        for (Map<String, String> column : data) {
            boolean isFound = false;
            waitFor(1);
            clearAndFillText(createConsumer.consumerEmail, column.get("ValidEmail"));
            click(createConsumer.createConsumerButton);
            waitFor(2);

            try {
                isFound = crmAddContactInfoPage.invalidEmailAddressError.isDisplayed();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            assertFalse("Email Address invalid error message appeared::" + column, isFound);
        }

        waitFor(2);
        scrollUpRobot();
    }


    @Then("I verify spoken languages configured")
    public void iVerifySpokenLanguagesConfigured(List<Map<String, String>> data) {
        String[] expectedLang = data.get(0).get("spoken_languages").split(",");
        List<String> expectedLangs = Arrays.asList(expectedLang);
        click((createConsumer.spokenLanguage));
        waitFor(1);

        for (int i = 0; i < expectedLang.length; i++) {
            WebElement actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedLang[i] + "']"));
            assertTrue(isElementDisplayed(actual), expectedLang[i] + " not exist in dropdown");
        }

    }

    @Then("I verify written languages configured")
    public void iVerifyWrittenLanguagesConfigured(List<Map<String, String>> data) {
        String[] expectedLang = data.get(0).get("written_languages").split(",");
        List<String> expectedLangs = Arrays.asList(expectedLang);
        click((createConsumer.writtenLanguage));
        waitFor(1);

        for (int i = 0; i < expectedLang.length; i++) {
            WebElement actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedLang[i] + "']"));
            assertTrue(isElementDisplayed(actual), expectedLang[i] + " not exist in dropdown");
        }
    }

    @Then("I verify race configured")
    public void i_verify_race_configured(List<Map<String, String>> data) {
        boolean isExist = false;
        String expectedLang = data.get(0).get("race");
        List<String> expectedLangs = Arrays.asList(expectedLang.split(","));
        click(createConsumer.raceDropdown);
        waitFor(1);
        List<String> actualLang = getElementsText(createConsumer.raceList);

        for (String lang : actualLang) {
            isExist = expectedLangs.contains(lang.trim());
        }
        Assert.assertTrue(isExist, "Mis-match:::" + "Expected-->" + expectedLangs + " Actual-->" + actualLang);
    }

    @Then("I verify ethnicity configured")
    public void i_verify_ethnicity_configured(List<Map<String, String>> data) {
        boolean isExist = false;
        String expectedLang = data.get(0).get("ethnicity");
        List<String> expectedLangs = Arrays.asList(expectedLang.split(","));
        click(createConsumer.ethnicityDropdown);
        waitFor(1);
        List<String> actualLang = getElementsText(createConsumer.ethnicityList);

        for (String lang : actualLang) {
            isExist = expectedLangs.contains(lang.trim());
        }
        Assert.assertTrue(isExist, "Mis-match:::" + "Expected-->" + expectedLangs + " Actual-->" + actualLang);
    }

    @Then("I verify citizenship configured")
    public void i_verify_citizenship_configured(List<Map<String, String>> data) {
        boolean isExist = false;
        String expectedLang = data.get(0).get("citizenship");
        List<String> expectedLangs = Arrays.asList(expectedLang.split(","));
        click(createConsumer.citizenshipDropdown);
        waitFor(1);
        List<String> actualLang = getElementsText(createConsumer.citizenshipList);

        for (String lang : actualLang) {
            isExist = expectedLangs.contains(lang.trim());
        }
        Assert.assertTrue(isExist, "Mis-match:::" + "Expected-->" + expectedLangs + " Actual-->" + actualLang);
    }

    @Then("I verify residency configured")
    public void i_verify_residency_configured(List<Map<String, String>> data) {
        boolean isExist = false;
        String expectedLang = data.get(0).get("residency");
        List<String> expectedLangs = Arrays.asList(expectedLang.split(","));
        click(createConsumer.residencyDropdown);
        waitFor(1);
        List<String> actualLang = getElementsText(createConsumer.residencyList);

        for (String lang : actualLang) {
            isExist = expectedLangs.contains(lang.trim());
        }
        Assert.assertTrue(isExist, "Mis-match:::" + "Expected-->" + expectedLangs + " Actual-->" + actualLang);
    }

    @Then("I ensure customer detail fields are present for VA Create Customer profile")
    public void iEnsureCustomerDetailFieldsArePresentForVACreateCustomerProfile(List<Map<String, String>> data) {

        assertTrue(createConsumer.consumerFN.isDisplayed(), "First name field is not displayed");
        assertTrue(createConsumer.consumerLN.isDisplayed(), "Last name field is not displayed");
        assertTrue(createConsumer.consumerMI.isDisplayed(), "Middle initial field is not displayed");
        assertTrue(createConsumer.consumerDB.isDisplayed(), "Date of birth field is not displayed");
        assertTrue(createConsumer.consumerSSN.isDisplayed(), "SSN field is not displayed");
        assertTrue(createConsumer.consumerGender.isDisplayed(), "Gender dropdown field is not displayed");
        assertTrue(createConsumer.writtenLanguage.isDisplayed(), "writtenLanguage dropdown field is not displayed");
        assertTrue(createConsumer.spokenLanguage.isDisplayed(), "spokenLanguage dropdown field is not displayed");
        assertTrue(createConsumer.consumerTypeOptions.isDisplayed(), "Consumer Tye dropdown field is not displayed");
        assertTrue(createConsumer.correspondencePreferenceDropDown.isDisplayed(), "Correspondence Preference DropDown field is not displayed");
        assertTrue(createConsumer.cancelConsumerButton.isDisplayed(), "Cancel button is not displayed");
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Create Consumer button is not displayed");

        String expected = data.get(0).get("Gender");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.genderType);
        waitFor(1);
        WebElement actual = null;
        for (int i = 0; i < expectedV.size(); i++) {
            actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedV.get(i) + "']"));
            assertTrue(isElementDisplayed(actual), expectedV.get(i) + " not exist in dropdown");
        }
        actual.click();
        createConsumer.consumerPhoneNum.click();
        expected = data.get(0).get("Consumer_Type");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.consumerType);
        waitFor(1);

        for (int i = 0; i < expectedV.size(); i++) {
            actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedV.get(i) + "']"));
            assertTrue(isElementDisplayed(actual), expectedV.get(i) + " not exist in dropdown");
        }
        createConsumer.consumerTypeDropDValue.click();

        expected = data.get(0).get("Corp_Pref");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.correspondencePreferenceType);
        waitFor(1);
        for (int i = 0; i < expectedV.size(); i++) {
            actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedV.get(i) + "']"));
            assertTrue(isElementDisplayed(actual), expectedV.get(i) + " not exist in dropdown");
        }
        actual.click();

        expected = data.get(0).get("ExternalId_program");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        for (int i = 0; i < expectedV.size(); i++) {
            actual = Driver.getDriver().findElement(By.xpath("//*[text()='" + expectedV.get(i) + "']"));
            assertTrue(isElementDisplayed(actual), expectedV.get(i) + " not exist in dropdown");
        }

    }

    @Then("I ensure customer communication fields are present for VA Create Customer profile")
    public void iEnsureCustomerCommunicationFieldsArePresentForVACreateCustomerProfile() {

        assertTrue(createConsumer.va_commOptPhone.isDisplayed(), "Communication option PHONE check-mark field is not displayed");
        assertTrue(createConsumer.va_commOptText.isDisplayed(), "Communication option TEXT check-mark field is not displayed");
        assertTrue(createConsumer.va_commOptEmail.isDisplayed(), "Communication option EMAIL check-mark field is not displayed");
        assertTrue(createConsumer.va_commOptMail.isDisplayed(), "Communication option MAIL check-mark field is not displayed");
        assertTrue(createConsumer.va_commOptPhoneSelect.getAttribute("checked").contains("true"), "Communication option Phone checked default");
        assertTrue(createConsumer.va_commOptMail.isDisplayed(), "Communication option MAIL check-mark field is not displayed");
        assertTrue(createConsumer.va_commOptMailSelect.getAttribute("checked").contains("true"), "Communication option MAIL checked default");

    }

    @Then("I ensure customer contact fields are present for VA Create Customer profile")
    public void iEnsureCustomerContactFieldsArePresentForVACreateCustomerProfile(List<Map<String, String>> data) {

        assertTrue(createConsumer.consumerPhoneNum.isDisplayed(), "Phone number field is not displayed");
        assertTrue(createConsumer.consumerEmail.isDisplayed(), "Email field is not displayed");
        assertTrue(createConsumer.phoneType.isDisplayed(), "Phone Type field is not displayed");
        assertTrue(createConsumer.consumerAddressType.isDisplayed(), "Address type field is not displayed");
        assertTrue(createConsumer.consumerAddress1.isDisplayed(), "Address 1 field is not displayed");
        assertTrue(createConsumer.consumerAddress2.isDisplayed(), "Address 2 field is not displayed");
        assertTrue(createConsumer.consumerCity.isDisplayed(), "City field is not displayed");
        assertTrue(createConsumer.consumerCountyField.isDisplayed(), "County dropdown field is not displayed");
        assertTrue(createConsumer.consumerStateField.isDisplayed(), "State dropdown field is not displayed");
        assertTrue(createConsumer.consumerZipCode.isDisplayed(), "Zip code field is not displayed");

        boolean isExist = false;
        String expected = data.get(0).get("Phone_Type");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.phoneType);
        waitFor(1);
        List<String> actualL = getElementsText(createConsumer.phoneTypeList);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match Phone_Type:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        Assert.assertFalse(actualL.contains("Fax"), "Fax avaialble" + "All available options-->" + actualL);
        click(createConsumer.selectOption(actualL.get(1)));
        //selectDropDown(createConsumer.phoneType, "Home");
        waitFor(2);

        isExist = false;
        expected = data.get(0).get("Addres_Type");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.addressType);
        waitFor(1);
        actualL = getElementsText(createConsumer.addressTypeist);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }
        Assert.assertTrue(isExist, "Mis-match Addres_Type:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
    }


    @Then("I enter first name begining with space for search consumer details")
    public void i_enter_first_name_begining_with_space_for_search_consumer_details() {
        waitFor(2);
        clearFieldCharByChar(contactRecord.firstName);
        clearFieldCharByChar(contactRecord.lastName);
        String data = " " + (newConsumer.get().get("name").toString());
        contactRecord.firstName.sendKeys(data);
        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        try {
            contactRecord.searchButton.click();
        } catch (Exception e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(2);
    }

    @Then("I verify consumer details are returned in result")
    public void i_verify_consumer_details_are_returned_in_result() {
        assertTrue(newConsumer.get().get("name").toString().equalsIgnoreCase(contactRecord.consumerFirstName.getText()),
                "First Name field does not have expected" + newConsumer.get().get("name") + "value");
        assertTrue(newConsumer.get().get("surname").toString().equalsIgnoreCase(contactRecord.consumerLastName.getText()),
                "Last Name field does not have expected" + newConsumer.get().get("name") + "value");
    }

    @When("I enter first name ending with space for search consumer details")
    public void i_enter_first_name_ending_with_space_for_search_consumer_details() {

        waitFor(2);
        clearFieldCharByChar(contactRecord.firstName);
        clearFieldCharByChar(contactRecord.lastName);
        String data = (newConsumer.get().get("name").toString()) + " ";
        contactRecord.firstName.sendKeys(data);
        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        try {
            contactRecord.searchButton.click();
        } catch (Exception e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(2);

    }

    @When("I enter last name begining with space for search consumer details")
    public void i_enter_last_name_begining_with_space_for_search_consumer_details() {

        waitFor(2);
        clearFieldCharByChar(contactRecord.firstName);
        clearFieldCharByChar(contactRecord.lastName);
        String data = " " + (newConsumer.get().get("surname").toString());
        contactRecord.lastName.sendKeys(data);
        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        try {
            contactRecord.searchButton.click();
        } catch (Exception e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(2);

    }

    @When("I enter last name ending with space for search consumer details")
    public void i_enter_last_name_ending_with_space_for_search_consumer_details() {
        waitFor(2);
        clearFieldCharByChar(contactRecord.firstName);
        clearFieldCharByChar(contactRecord.lastName);
        String data = (newConsumer.get().get("surname").toString()) + " ";
        contactRecord.lastName.sendKeys(data);
        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        try {
            contactRecord.searchButton.click();
        } catch (Exception e) {
            jsClick(contactRecord.searchButton);
        }
        waitFor(2);
    }

    @When("I navigate to left nav bar Case Consumer Search")
    public void I_navigate_to_left_nav_bar_Case_Consumer_Search() {
        waitFor(2);
        hover(contactRecord.hoverLeftNabar);
        waitFor(1);
        click(contactRecord.caseConsumerSearch);
        waitFor(2);
        hover(contactRecord.lastName);
        waitFor(2);
    }

    @Then("I verify removing of External ID Type, External ID fields")
    public void iVerifyRemovingOfExternalIDTypeExternalIDFields() {
        boolean isFound = false;

        try {
            isFound = createConsumer.externalIdType.isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertFalse("Extrenal ID Type dropdown appeared::", isFound);

        try {
            isFound = createConsumer.externalId.isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertFalse("Extrenal ID field appeared::", isFound);

    }

    @Then("I verify program dropdown value behaviour if External ID is missing")
    public void iVerifyProgramDropdownValueBehaviourIfExternalIDIsMissing(Map<String, String> data) {

        //String expectedV = data.get("ExternalId_program");
        String expectedV = "CHIP,MEDICAID";
        boolean isExist = false;
        List<String> actualL;
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);
        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }
        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), "1234");
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(1));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);
        Assert.assertTrue(actualL.size() <= expectedV.length(), "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
//        click(createConsumer.selectOption(actualL.get(0)));
//        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0),"1234");
//        waitFor(1);
//
//        click(createConsumer.external_id_Delete.get(0));
//        waitFor(1);
//
//        click(createConsumer.externalIdAdd);
//        waitFor(1);
//        click(createConsumer.externalIdProgram.get(0));
//        waitFor(1);
//        actualL = getElementsText(createConsumer.externalIdProgramList);
//        for (String act : actualL) {
//            isExist = expectedV.contains(act.trim());
//        }
//        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
//        click(createConsumer.selectOption(actualL.get(0)));
//        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0),"1234");
//        click(createConsumer.externalIdAdd);
//        waitFor(1);
//        click(createConsumer.externalIdProgram.get(1));
//        waitFor(1);
//        actualL = getElementsText(createConsumer.externalIdProgramList);
//        Assert.assertTrue(actualL.size() <= expectedV.length() && actualL.get(0).equalsIgnoreCase(expectedV.split(",")[0]), "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
//        click(createConsumer.selectOption(actualL.get(0)));
//        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(1),"1234");
//        waitFor(1);
    }


    @Then("I verify able to add more than on external ID")
    public void iVerifyAbleToAddMoreThanOnExternalID(Map<String, String> data) {
        List<String> expectedLang;
        assertTrue(isElementDisplayed(createConsumer.externalIdDropdownCHIPvalueTxt), "CHIP is not displayed in dropdown");
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
//        waitFor(1);
//        expectedLang = getElementsText(createConsumer.externalIdProgramList);
//        for(int i=0; i<3;i++){
//            WebElement actual = Driver.getDriver().findElement(By.xpath("//*[text()='"+expectedLang.get(i)+"']"));
//            assertTrue(isElementDisplayed(actual), expectedLang.get(i)+" not exist in dropdown");
//        }
//        click(createConsumer.selectOption(expectedLang.get(0)));
//        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0),"1234");
//       click(createConsumer.externalIdAdd);
//        waitFor(1);
//        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        expectedLang = getElementsText(createConsumer.externalIdProgramList);
        System.out.println(expectedLang);
        assertTrue(expectedLang.contains("MEDICAID"));
        click(createConsumer.selectOption(expectedLang.get(0)));
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0),"1234");
        assertTrue(isElementDisplayed(createConsumer.externalIdDropdownMEDICAIDvalueTxt), "MEDICAID is not displayed in dropdown");
        assertFalse(createConsumer.externalIdAdd.isEnabled());
    }

    @Then("I verify removal of every external ID added")
    public void iVerifyRemovalOfEveryExternalIDAdded(Map<String, String> data) {
        String expectedV = data.get("ExternalId_program");
        boolean isExist = false;
        List<String> actualL;
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);
        Assert.assertTrue(createConsumer.external_id_Delete.get(0).isDisplayed(), "Delete button is missing for first  External ID");
        click(createConsumer.selectOption(actualL.get(0)));
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), "1234");
        Assert.assertTrue(createConsumer.external_id_Delete.get(0).isDisplayed(), "Delete button is missing for first  External ID");
        click(createConsumer.externalIdAdd);
        waitFor(1);

        click(createConsumer.externalIdProgram.get(1));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);
        click(createConsumer.selectOption(actualL.get(0)));
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(1), "1234");
        waitFor(1);
        Assert.assertTrue(createConsumer.external_id_Delete.get(1).isDisplayed(), "Delete button is missing for second  External ID");

        click(createConsumer.external_id_Delete.get(1));
        waitFor(1);
        click(createConsumer.external_id_Delete.get(0));
    }

    @Then("I verify program,consumer ID are visible On Consumer profile")
    public void iVerifyProgramConsumerIDAreVisibleOnConsumerProfile() {
        waitFor(2);
        Assert.assertTrue(createConsumer.lblProgram.isDisplayed(), "Program is  missing in  External ID");
        Assert.assertTrue(createConsumer.lblConsumberID.isDisplayed(), "Consumer ID is  missing in  External ID");
    }

    @When("I populate Create Consumer fields for a new consumer with external id")
    public void I_populate_Create_Consumer_fields_for_a_new_consumer_with_external_id() {

        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);

        jsClick(createConsumer.externalIdAdd);
        waitFor(3);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(3);
        jsClick(createConsumer.externalIdDropdownCHIPvalue);
        waitFor(3);
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), String.valueOf(new Random().nextInt(99999)));
        waitFor(1);

    }

    @When("I populate Create Consumer fields for a new consumer with external id for cover-va")
    public void I_populate_Create_Consumer_fields_for_a_new_consumer_with_external_id_for_coverva() {

        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);
        clearAndFillText(createConsumer.addressCountyText, (getRandomString(6) + " County"));
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);

        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        click(createConsumer.selectOption("MMIS"));
        waitFor(1);
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), String.valueOf(new Random().nextInt(99999)));
        waitFor(1);
    }

    @When("I populate Create Consumer fields for a new consumer with default state for cover-va")
    public void I_populate_Create_Consumer_fields_for_a_new_consumer_with_default_state_for_coverva() {

        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(3);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(2);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(2);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        clearAndFillText(createConsumer.addressCountyText, (getRandomString(6) + " County"));
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);

        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        click(createConsumer.selectOption("MMIS"));
        waitFor(1);
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), String.valueOf(new Random().nextInt(99999)));
        waitFor(1);
    }


    @When("I populate Create Consumer fields for a new consumer with Offender external id for cover-va")
    public void i_populate_Create_Consumer_fields_for_a_new_consumer_with_Offender_external_id_for_cover_va() {
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        waitFor(2);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, newConsumer.get().get("phone").toString());
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        clearAndFillText(createConsumer.addressCountyText, (getRandomString(6) + " County"));
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);

        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        click(createConsumer.selectOption("Offender"));
        waitFor(1);
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), String.valueOf(new Random().nextInt(99999)));
        offerderIDValue.set(createConsumer.externalId_ConsumerId.get(0).getAttribute("value"));
        waitFor(1);
    }

    @When("I searching for contact with Offender ID")
    public void i_searching_for_contact_with_Offender_ID() {
        edit_cont_Rec.advancedSearch.click();
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.searchConsumerType, "Offender");
        clearAndFillText(manualContactRecordSearchPage.consumerExternalId, "23554");
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @When("I searched customer by Offender ID in case and consumer page")
    public void i_searched_customer_by_Offender_ID_in_case_and_consumer_page() {
        selectDropDown(manualContactRecordSearchPage.searchConsumerDropdown, "Offender");
        clearAndFillText(manualContactRecordSearchPage.caseConsumerID, offerderIDValue.get());
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @When("I verify Offender ID column is displayed")
    public void i_verify_Offender_ID_column_is_displayed() {
        waitFor(1);
        assertTrue(manualContactRecordSearchPage.OffenderColumn.isDisplayed());
    }

    @Then("I ensure below fields are present in edit customer detail page")
    public void iEnsureCustomerDetailFieldsArePresentForEditCustomerProfile(Map<String, String> data) {
        waitFor(2);
        click(crmEditConsumerProfilePage.consumerEditButton);
        waitFor(1);
        boolean isExist = false;
        assertTrue(createConsumer.consumerFN.isDisplayed(), "First name field is not displayed");
        assertTrue(createConsumer.consumerLN.isDisplayed(), "Last name field is not displayed");
        assertTrue(createConsumer.consumerMI.isDisplayed(), "Middle initial field is not displayed");
        assertTrue(createConsumer.consumerDB.isDisplayed(), "Date of birth field is not displayed");
        assertTrue(createConsumer.consumerSSN.isDisplayed(), "SSN field is not displayed");
        assertTrue(createConsumer.consumerGender.isDisplayed(), "Gender dropdown field is not displayed");
        assertTrue(createConsumer.writtenLanguage.isDisplayed(), "writtenLanguage dropdown field is not displayed");
        assertTrue(createConsumer.spokenLanguage.isDisplayed(), "spokenLanguage dropdown field is not displayed");
        // assertTrue(createConsumer.consumerTypeOptions.isDisplayed(), "Consumer Tye dropdown field is not displayed");
        assertTrue(createConsumer.correspondencePreferenceDropDown.isDisplayed(), "Correspondence Preference DropDown field is not displayed");


        String expected = data.get("Gender");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.genderType);
        waitFor(1);
        List<String> actualL = getElementsText(createConsumer.genderCodeist);
        System.out.println("actualL = " + actualL);
        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match Gender:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(1)));
        waitFor(2);
//        isExist = false;
//        expected = data.get(0).get("Consumer_Type");
//        expectedV = Arrays.asList(expected.split(","));
//        click(createConsumer.consumerType);
//        waitFor(1);
//        actualL = getElementsText(createConsumer.consumerTypeList);
//        for (String act : actualL) {
//            isExist = expectedV.contains(act.trim());
//        }
//        Assert.assertTrue(isExist, "Mis-match Consumer Type:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
//        Assert.assertFalse(actualL.contains("Internal"), "Fax avaialble" + "All available options-->" + actualL );
//        click(createConsumer.selectOption(actualL.get(0)));
//        waitFor(2);

        isExist = false;
        expected = data.get("Corp_Pref");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.correspondencePreferenceType);
        waitFor(1);
        actualL = getElementsText(createConsumer.correspondencePreferenceList);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match Correspondence PreferenceList:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);

        isExist = false;
        expected = data.get("written_languages");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.writtenLanguage);
        waitFor(1);
        actualL = getElementsText(createConsumer.writtenLanglist);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match written_languages:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);

        isExist = false;
        expected = data.get("spoken_languages");
        expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.spokenLanguage);
        waitFor(1);
        actualL = getElementsText(createConsumer.spokenLanglist);
        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }
        Assert.assertTrue(isExist, "Mis-match spoken_languages:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);

        isExist = false;
        expected = data.get("ExternalId_program");
        expectedV = Arrays.asList(expected.split(","));
        //click(createConsumer.externalIdAdd);
        waitFor(2);
        click(createConsumer.externalIdAdd);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);
    }

    @Then("I verify error messages for mandatory fields in edit consumer profile page")
    public void iVerifyerrormessagesformandatoryfieldsineditconsumerprofilepage(List<Map<String, String>> data) {
        waitFor(2);
        click(createConsumer.consumerEdit);
        waitFor(1);
        clearFieldCharByChar(createConsumer.consumerLN);
        clearFieldCharByChar(createConsumer.consumerMI);
        clearFieldCharByChar(createConsumer.consumerFN);
        click(createConsumer.dobVisibYeyButton);
        clearFieldCharByChar(createConsumer.consumerDB);
        jsClick(new CRMAddCaseMemberPage().SSNvisibilityoffButton);
        clearFieldCharByChar(createConsumer.consumerSSN);
        clearFieldCharByChar(createConsumer.consumerStartDate);
        click(createConsumer.SaveButton);
        waitFor(2);

        for (Map<String, String> field : data) {
            Assert.assertTrue(createConsumer.getMandatoryFieldError(field.get("Field_Name")).isDisplayed(), field.get("Field_Name") + " mandatory error missing");
        }
    }

    @Then("I verify update consumer successful message")
    public void iVerifyUpdateConsumerSuccessfulmessage() {
        jsClick(createConsumer.consumerEdit);
        waitFor(1);
        clearAndFillText(createConsumer.consumerFN, "update");
        click(createConsumer.SaveButton);
        try {
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 5);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        } catch (Exception e) {
            if (isElementDisplayed(createConsumer.warningMessageContinueButton)) {
                createConsumer.warningMessageContinueButton.click();
                waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 5);
                assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
            }
        }
        click(createConsumer.closeSuccessfulAlert);
        waitFor(2);
    }

    @Then("I verify created consumer linked to Active Contact")
    public void iVerifyCreatedConsumerlinkedToActiveContact() {
        waitForVisibility(createConsumer.consumerLinkedActivecontact, 5);
        assertTrue(isElementDisplayed(createConsumer.consumerLinkedActivecontact));
    }

    @Then("I update spoken language as {string} on Edit Consumer profile page")
    public void iUpdateSpokenLanguageAsOnEditConsumerProfilePage(String language) {
        click(createConsumer.consumerEdit);
        waitFor(2);
        selectDropDown(createConsumer.spokenLanguage, language);
        click(createConsumer.SaveButton);
        try {
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 5);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        } catch (Exception e) {
            if (createConsumer.warningMessageContinueButton.isDisplayed()) {
                createConsumer.warningMessageContinueButton.click();
            }
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 2);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        }
    }

    @Then("I update Correspondence Preference as {string} on Edit Consumer profile page")
    public void iUpdateCorrsPrefAsOnEditConsumerProfilePage(String data) {
        click(createConsumer.consumerEdit);
        waitFor(2);
        selectDropDown(createConsumer.correspondencePreferenceType, data);
        click(createConsumer.SaveButton);
        try {
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 5);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        } catch (Exception e) {
            if (createConsumer.warningMessageContinueButton.isDisplayed()) {
                createConsumer.warningMessageContinueButton.click();
            }
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 2);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        }
    }

    @Then("I update Phone Type as {string} on Edit Consumer profile page")
    public void iUpdatePhoneTypeAsOnEditConsumerProfilePage(String data) {
        click(createConsumer.consumerEdit);
        waitFor(2);
        clearFieldCharByChar(createConsumer.consumerMI);
        clearAndFillText(createConsumer.consumerMI, "M");
        click(createConsumer.SaveButton);
        try {
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 5);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        } catch (Exception e) {
            if (createConsumer.warningMessageContinueButton.isDisplayed()) {
                createConsumer.warningMessageContinueButton.click();
            }
            waitForVisibility(createConsumer.consumerUpdateSuccessMessage, 2);
            assertTrue(isElementDisplayed(createConsumer.consumerUpdateSuccessMessage));
        }
    }

    public void iverifyUpdatedConsumerDetailsIneventListResponse(JsonObject apiJsonObject) {
        JsonArray json = apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            JSONObject payload = new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString());
            if (payload.getJSONObject("dataObject").get("consumerFirstName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("name").toString()))) {

                assertEquals(payload.getJSONObject("dataObject").get("consumerFirstName").toString(), newConsumer.get().get("name"));
                assertEquals(payload.getJSONObject("dataObject").get("consumerLastName").toString(), newConsumer.get().get("surname"));
                break;
            }

        }

    }

    public void iCaptureCommPrefDetailsBeforeSpokenLanguageUpdate(JsonObject apiJsonObject) {
        JsonArray json = apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            JSONObject payload = new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString());
            if (payload.getJSONObject("dataObject").get("consumerFirstName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("name").toString()))
                    && payload.getJSONObject("dataObject").get("consumerLastName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("surname").toString()))) {
                JSONArray commPrefs = payload.getJSONObject("dataObject").getJSONArray("communicationPreferences");
                for (int j = 0; j < commPrefs.length(); j++) {
                    JSONObject commPref = new JSONObject(commPrefs.get(j).toString());
                    if (commPref.get("valuePairIdCommPref").toString().contains("SPOKEN_LANGUAGE_ENGLISH")) {
                        commPref_createdBy.set(commPref.get("createdBy").toString());
                        commPref_createdOn.set(commPref.get("createdOn").toString());
                        commPref_effectiveStartDate.set(commPref.get("effectiveStartDate").toString());
                        commPref_effectiveEndDate.set(commPref.get("effectiveEndDate").toString());
                        commPref_updatedOn.set(commPref.get("updatedOn").toString());
                        commPref_updatedBy.set(commPref.get("updatedBy").toString());
                        break;
                    }

                    if (commPref.get("valuePairIdCommPref").toString().contains("CORRESPONDENCE_PAPERLESS")) {
                        commPref_createdBy.set(commPref.get("createdBy").toString());
                        commPref_createdOn.set(commPref.get("createdOn").toString());
                        commPref_effectiveStartDate.set(commPref.get("effectiveStartDate").toString());
                        commPref_effectiveEndDate.set(commPref.get("effectiveEndDate").toString());
                        commPref_updatedOn.set(commPref.get("updatedOn").toString());
                        commPref_updatedBy.set(commPref.get("updatedBy").toString());
                        break;
                    }
                }
                break;
            }
        }
    }

    public void iVerifyCommPrefDetailsAfterSpokenLanguageUpdate(JsonObject apiJsonObject) {
        JsonArray json = apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            JSONObject payload = new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString());
            if (payload.getJSONObject("dataObject").get("consumerFirstName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("name").toString()))
                    && payload.getJSONObject("dataObject").get("consumerLastName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("surname").toString()))) {
                JSONArray commPrefs = payload.getJSONObject("dataObject").getJSONArray("communicationPreferences");
                for (int j = 0; j < commPrefs.length(); j++) {
                    JSONObject commPref = new JSONObject(commPrefs.get(j).toString());
                    if (commPref.get("valuePairIdCommPref").toString().contains("SPOKEN_LANGUAGE_ENGLISH")) {
                        assertTrue(commPref.get("effectiveEndDate").toString().contains(LocalDate.now().toString()));
                        assertTrue(commPref.get("updatedOn").toString().contains(LocalDate.now().toString()));
                        assertEquals(commPref.get("updatedBy").toString(), commPref_createdBy.get());
                        assertEquals(commPref_createdBy.get(), commPref.get("createdBy").toString());
                        assertEquals(commPref_createdOn.get(), commPref.get("createdOn").toString());
                        assertEquals(commPref_effectiveStartDate.get(), commPref.get("effectiveStartDate").toString());
                        continue;
                    }

                    if (commPref.get("valuePairIdCommPref").toString().contains("SPOKEN_LANGUAGE_SPANISH")) {
                        assertTrue(commPref.get("effectiveEndDate").toString() == null);
                        assertTrue(commPref.get("effectiveStartDate").toString().contains(LocalDate.now().toString()));
                        assertTrue(commPref.get("createdOn").toString().contains(LocalDate.now().toString()));
                        assertTrue(commPref.get("updatedOn").toString().contains(LocalDate.now().toString()));
                        assertEquals(commPref.get("updatedBy").toString(), commPref_createdBy.get());
                        assertEquals(commPref.get("createdBy").toString(), commPref_createdBy.get());
                        break;
                    }
                }
                break;
            }
        }
    }

    public void iVerifyCommPrefDetailsWithNoLanguageUpdate(JsonObject apiJsonObject) {
        JsonArray json = apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            JSONObject payload = new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString());
            if (payload.getJSONObject("dataObject").get("consumerFirstName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("name").toString()))
                    && payload.getJSONObject("dataObject").get("consumerLastName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("surname").toString()))) {
                JSONArray commPrefs = payload.getJSONObject("dataObject").getJSONArray("communicationPreferences");
                for (int j = 0; j < commPrefs.length(); j++) {
                    JSONObject commPref = new JSONObject(commPrefs.get(j).toString());
                    if (commPref.get("valuePairIdCommPref").toString().contains("SPOKEN_LANGUAGE_ENGLISH")) {
                        assertEquals(commPref_createdBy.get(), commPref.get("createdBy").toString());
                        assertEquals(commPref_createdOn.get(), commPref.get("createdOn").toString());
                        assertEquals(commPref_effectiveStartDate.get(), commPref.get("effectiveStartDate").toString());
                        assertEquals(commPref_effectiveEndDate.get(), commPref.get("effectiveEndDate").toString());
                        assertEquals(commPref_updatedOn.get(), commPref.get("updatedOn").toString());
                        assertEquals(commPref_updatedBy.get(), commPref.get("updatedBy").toString());
                    }
                }
                break;
            }
        }
    }

    public void iVerifyCommPrefDetailsWhenCorresPrefUpdated(JsonObject apiJsonObject) {

        JsonArray json = apiJsonObject.getAsJsonObject("eventsList").getAsJsonArray("content");
        for (int i = 0; i < json.size(); i++) {
            JSONObject payload = new JSONObject(new JSONObject(json.get(i).toString()).get("payload").toString());
            if (payload.getJSONObject("dataObject").get("consumerFirstName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("name").toString()))
                    && payload.getJSONObject("dataObject").get("consumerLastName").toString().equalsIgnoreCase(String.valueOf(newConsumer.get().get("surname").toString()))) {
                JSONArray commPrefs = payload.getJSONObject("dataObject").getJSONArray("communicationPreferences");
                for (int j = 0; j < commPrefs.length(); j++) {
                    JSONObject commPref = new JSONObject(commPrefs.get(j).toString());
                    if (commPref.get("valuePairIdCommPref").toString().contains("CORRESPONDENCE_PAPERLESS")) {
                        assertTrue(commPref.get("effectiveEndDate").toString().contains(LocalDate.now().toString()));
                        assertTrue(commPref.get("updatedOn").toString().contains(LocalDate.now().toString()));
                        assertEquals(commPref.get("updatedBy").toString(), commPref_createdBy.get());
                        assertEquals(commPref_createdBy.get(), commPref.get("createdBy").toString());
                        assertEquals(commPref_createdOn.get(), commPref.get("createdOn").toString());
                        assertEquals(commPref_effectiveStartDate.get(), commPref.get("effectiveStartDate").toString());
                        break;
                    }
                }
                break;
            }
        }
    }

    @Then("I ensure below external ids lds are present for VA Create Customer profile")
    public void iEnsureBelowExternalIdsLdsArePresentForVACreateCustomerProfile(List<Map<String, String>> data) {
        boolean isExist = false;

        String expected = data.get(0).get("ExternalId_program");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        List<String> actualL = getElementsText(createConsumer.externalIdProgramList);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);
    }

    @Then("I ensure below external ids lds are present for VA Edit Customer profile")
    public void iEnsureBelowExternalIdsLdsArePresentForVAEditCustomerProfile(List<Map<String, String>> data) {
        boolean isExist = false;

        click(createConsumer.consumerEdit);
        waitFor(2);

        String expected = data.get(0).get("ExternalId_program");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        List<String> actualL = getElementsText(createConsumer.externalIdProgramList);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);
    }
    // @Then("^I verify Country,City,Zip code fields are input in \"(Active Contact|Create Consumer|Edit Address|Case Consumer|Case Consumer Save Record Search|Create Task)\" page$")

    @Then("I verify Country,City,Zip code fields are input in {string} page")
    public void iVerifyCountyCityZipFieldsAreInput(String screen) {

        waitFor(2);
        switch (screen) {
            case "Active Contact":
                assertEquals(activeContact.consumerCity.getTagName(), "input");
                assertEquals(activeContact.consumerZipCode.getTagName(), "input");
                assertEquals(activeContact.consumerCounty.getTagName(), "input");
                break;
            case "Case Consumer":
                assertEquals(manualCaseConsumerSearch.caseConsumerSearchZipCode.getTagName(), "input");
                assertEquals(manualCaseConsumerSearch.caseConsumerSearchCounty.getTagName(), "input");
                assertEquals(manualCaseConsumerSearch.caseConsumerSearchCity.getTagName(), "input");
                break;
            case "Create Consumer":
                assertEquals(createConsumer.consumerCity.getTagName(), "input");
                assertEquals(createConsumer.consumerZipCode.getTagName(), "input");
                assertEquals(createConsumer.consumerCounty.getTagName(), "input");
                break;
            case "Edit Address":
                assertEquals(crmAddContactInfoPage.city.getTagName(), "input");
                assertEquals(crmAddContactInfoPage.zip.getTagName(), "input");
                assertEquals(crmAddContactInfoPage.county.getTagName(), "input");
                break;
            case "Case Consumer Save Record Search":
                assertEquals(createConsumer.consumerCity.getTagName(), "input");
                assertEquals(createConsumer.consumerZipCode.getTagName(), "input");
                assertEquals(createConsumer.consumerCounty.getTagName(), "input");
                break;
            case "Create Task":
                assertEquals(createConsumer.consumerCity.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.taskZipCode.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.newAddressCity.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.newAddressZipCode.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.oldAddressZipCode.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.providerAddressCity.getTagName(), "input");
                assertEquals(crmCreateGeneralTaskPage.providerAddressSZipCode.getTagName(), "input");
                break;
        }
    }

    @When("I navigate to Case Consumer Advance Search")
    public void iNavigateToCaseConsumerAdvanceSearch() {
        waitFor(2);
        hover(crmDashboardPage.caseConsumerSideBarIcon);
        jsClick(crmDashboardPage.case_ConsumerSearchTab);
        waitFor(2);
        click(contactRecord.contactRecordAdvanceSearch);
        waitFor(2);
    }

    @Then("I navigate to Case Consumer Advance Search in Saved Contact record")
    public void iNavigateToCaseConsumerAdvanceSearchInSavedContactRecord() {
        iNavigateToCaseConsumerAdvanceSearch();
        waitFor(3);
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        i_click_on_Search_Button_on_Search_Consumer_Page();
        waitFor(2);
        click(crmActiveContactPage.searchResultsFirstRecord);
        waitFor(2);
        click(crmDemographicContactInfoPage.addAddressButton);
    }

    @When("I add new phone number to check Primary Consumer Phone Flag")
    public void iAddnewphonenumbertocheckPrimaryConsumerPhoneFlag() {
        waitFor(2);
        click(demographicContactInfo.addPhoneButton);
        waitFor(2);
        selectDropDown(crmAddContactInfoPage.phoneType, "Work");
        clearAndFillText(createConsumer.consumerStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDate.now().minusDays(5)).toString());
        click(createConsumer.consumerPhoneEndDate);
    }

    @When("I add new phone number to check default Primary Consumer Phone Flag")
    public void iAddnewphonenumbertocheckDefaultPrimaryConsumerPhoneFlag() {
        iAddnewphonenumbertocheckPrimaryConsumerPhoneFlag();
        consumerPrimaryPhone.set(RandomStringUtils.random(10, false, true));
        clearAndFillText(crmAddContactInfoPage.phoneNumber, consumerPrimaryPhone.get());
        assertTrue(Boolean.valueOf(crmAddContactInfoPage.primaryCaseEnabledCheckBox.getAttribute("checked")));
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        consumerID.set(createConsumer.consumerID.getText().trim());
    }

    @Then("I verify warning message when Primary Phone is available")
    public void iVerifyWarningMessageWhenPrimaryPhoneisAvailable() {
        waitFor(2);
        click(demographicContactInfo.addPhoneButton);
        waitFor(2);
        selectDropDown(crmAddContactInfoPage.phoneType, "Cell");
        clearAndFillText(createConsumer.consumerStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDate.now().minusDays(2)).toString());
        clearText(createConsumer.consumerPhoneEndDate);
        waitFor(1);
        consumerPrimaryPhone.set(RandomStringUtils.random(10, false, true));
        clearAndFillText(crmAddContactInfoPage.phoneNumber, consumerPrimaryPhone.get());
        click(crmAddContactInfoPage.primaryCasePhoneOrEmailCheckBox);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        assertTrue(createConsumer.newPhoneNumWarningMessage.isDisplayed());
    }

    @Then("I verify warning message when editing already available phone number")
    public void iVerifyWarningMessageWhenEditPrimaryPhoneisAvailable() {
        waitFor(2);
        click(createConsumer.editFirstPhoneNum);
        waitFor(2);
        selectDropDown(crmAddContactInfoPage.phoneType, "Cell");
        click(crmAddContactInfoPage.primaryCasePhoneOrEmailCheckBox);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        assertTrue(createConsumer.newPhoneNumWarningMessage.isDisplayed());
    }

    @Then("I verify Primary Consumer Email Flag is enabled")
    public void iVerifyPrimaryConsumerEmailFlagisenabled() {
        waitFor(5);
        assertTrue(crmAddContactInfoPage.primaryCaseEnabledCheckBox.getAttribute("disabled") == null);
    }

    @Then("I verify Primary Consumer Phone Flag is enabled")
    public void iVerifyPrimaryConsumerPhoneFlagisenabled() {
        waitFor(5);
        assertTrue(crmAddContactInfoPage.primaryCaseEnabledCheckBox.getAttribute("disabled") == null);
    }

    @Then("I verify consumer phone number primary indicator added on UI")
    public void iVerifyPrimaryIndicatorForPhoneNum() {
        waitFor(2);
        assertTrue(crmAddContactInfoPage.checkPrimaryPhoneNum.isDisplayed());
    }


    @And("I edit existing phone number to check primary indicator flag")
    public void iEditExistingPhoneNumToCheckPrimaryIndicatorFlag() {
        waitFor(2);
        click(createConsumer.editPhoneNum);
        waitFor(2);
        selectDropDown(crmAddContactInfoPage.phoneType, "Home");
    }

    @Then("I click on existing phone number")
    public void i_click_on_existing_phone_number() {
        waitFor(2);
        click(createConsumer.editPhoneNum);
    }

    @And("I update existing phone number to set primary indicator flag")
    public void iEditExistingPhoneNumToSetPrimaryIndicatorFlag() {
        iEditExistingPhoneNumToCheckPrimaryIndicatorFlag();
        consumerPrimaryPhone.set(RandomStringUtils.random(10, false, true));
        clearAndFillText(crmAddContactInfoPage.phoneNumber, consumerPrimaryPhone.get());
        click(crmAddContactInfoPage.primaryCasePhoneOrEmailCheckBox);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        consumerID.set(createConsumer.consumerID.getText().trim());
    }

    @And("I verify Add Consumer button")
    public void iVerifyAddConsumerButton() {
        waitFor(2);
        assertTrue(createConsumer.addConsumerButton.isDisplayed());
    }

    @And("I search for consumer details in case consumer search")
    public void iSearchForCaseConsumerSearch() {
        waitFor(2);
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        click(manualCaseConsumerSearch.searchButton);

    }

    @Then("I verify search cretria fields are populated in Create Consumer UI with the values i have entered")
    public void iVerifySearchfieldsWithValuesArePopulatedOnCreateConsumerUI() {
        System.out.println("FirstName ======= " + newConsumer.get().get("name").toString());
        System.out.println("LastName ======= " + newConsumer.get().get("surname").toString());
        assertTrue(createConsumer.consumerFN.getAttribute("value").equalsIgnoreCase(newConsumer.get().get("name").toString()));
        assertTrue(createConsumer.consumerLN.getAttribute("value").equalsIgnoreCase(newConsumer.get().get("surname").toString()));
    }

    @Then("I verify navigated to the Consumer Profile of the consumer created")
    public void iverifyNavigatedToConsumerProfileCreated() {
        waitFor(2);
        assertTrue(crmDemographicContactInfoPage.profileDetailsLabel.isDisplayed());
    }

    @And("I select Contact Type as {string}")
    public void iSelectContactTypeAs(String ddValue) {
        waitFor(2);
        selectDropDown(manualCaseConsumerSearch.searchConsumerOptions, ddValue);
    }


    @Then("I verify fields are present in case consumer search page")
    public void iVerifyFieldsArePresentInCaseConsumerSearchPage(List<Map<String, String>> data) {
        assertTrue(manualCaseConsumerSearch.firstName.isDisplayed());
        assertTrue(manualCaseConsumerSearch.lastName.isDisplayed());
        assertTrue(manualCaseConsumerSearch.middleName.isDisplayed());
        assertTrue(manualCaseConsumerSearch.consumerSSN.isDisplayed());
        assertTrue(manualCaseConsumerSearch.DOB.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseConsumerConsumerId.isDisplayed());

        boolean isExist = false;
        String expected = data.get(0).get("Consumer_Type");
        List<String> expectedV = Arrays.asList(expected.split(","));
        click(manualCaseConsumerSearch.searchConsumerOptions);
        waitFor(1);
        List<String> actualL = getElementsText(manualCaseConsumerSearch.searchConsumerTypeValue);

        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }

        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);
        click(createConsumer.selectOption(actualL.get(0)));
        waitFor(2);
    }


    @Then("I verify fields are present in manual case consumer advance search page")
    public void iVerifyFieldsArePresentInManualCaseConsumerAdvanceSearchPage() {
        assertTrue(manualCaseConsumerSearch.addressLine1.isDisplayed());
        assertTrue(manualCaseConsumerSearch.addressLine2.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseConsumerSearchCity.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseconsumerPhoneNumber.isDisplayed());
        assertTrue(manualCaseConsumerSearch.email.isDisplayed());
        //assertTrue(manualCaseConsumerSearch.caseconsumerEmail.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseConsumerSearchZipCode.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseConsumerSearchCounty.isDisplayed());
        assertTrue(manualCaseConsumerSearch.caseConsumerState.isDisplayed());
    }

    @And("I search for data without any data for single field")
    public void iSearchForDataWithoutAnyDataForSingleField() {
        waitFor(1);
        i_click_on_Search_Button_on_Search_Consumer_Page();
    }

    @Then("I verify the search error message")
    public void iVerifyTheSearchErrorMessage() {
        assertTrue(manualCaseConsumerSearch.searchErrorWithNoInputData.isDisplayed());
    }

    @And("I search for data with first name, last name less than minimum length")
    public void iSearchForDataWithFirstNameLastNameLessThanMinimumLength() {
        clearAndFillText(manualCaseConsumerSearch.firstName, RandomStringUtils.random(1, true, false));
        clearAndFillText(manualCaseConsumerSearch.lastName, RandomStringUtils.random(1, true, false));
        i_click_on_Search_Button_on_Search_Consumer_Page();
        waitFor(2);
    }

    @And("I search for data with first name {string}, last name {string} with minimum length")
    public void iSearchForDataWithFirstNameLastNameWithMinimumLength(String firstName, String lastName) {
        clearAndFillText(manualCaseConsumerSearch.firstName, firstName);
        clearAndFillText(manualCaseConsumerSearch.lastName, lastName);
        i_click_on_Search_Button_on_Search_Consumer_Page();
        waitFor(2);
    }

    @Then("I verify the minimum chars error message")
    public void iVerifyTheMinimumCharsErrorMessage() {
        waitFor(2);
        assertTrue(manualCaseConsumerSearch.minCharserrorMessage("FIRST NAME").isDisplayed());
        assertTrue(manualCaseConsumerSearch.minCharserrorMessage("LAST NAME").isDisplayed());
    }

    @Then("I verify the search results")
    public void iVerifyTheSearchResults() {
        waitFor(2);
        Assert.assertTrue(isElementDisplayed(manualCaseConsumerSearch.consumerIdFirstRecordValue));
    }

    @When("I search for data with minimum criteria {string}")
    public void iSearchForDataWithMinimumCriteria(String data) {
        clearAndFillText(manualCaseConsumerSearch.firstName, data);
        i_click_on_Search_Button_on_Search_Consumer_Page();
        waitFor(2);
    }


    @Then("I see Search Results are details of consumer created")
    public void iseeSearchResultsOfCreatedConsumer() {
        waitFor(2);
        assertTrue(manualCaseConsumerSearch.consumerFNs.get(0).getText().equals(newConsumer.get().get("name")));
        assertTrue(manualCaseConsumerSearch.consumerLNs.get(0).getText().equals(newConsumer.get().get("surname")));
    }

    @And("I minimize active contact gadget popup if populates")
    public void minimizeGenesysPopupIfPopulates() {
        waitFor(2);
        if (manualCaseConsumerSearch.activeContactGadget.size() != 0) {
            manualCaseConsumerSearch.activeContactGadget.get(0).click();
        }
        waitFor(2);
    }


    @And("I ensure County filed is optional")
    public void iEnsureCountyFiledIsOptionalBeforeCreateConsumer() {
        clearFieldCharByChar(createConsumer.consumerCounty);
        assertTrue(createConsumer.consumerCounty.getAttribute("value").equals(""));
    }

    @Then("I verify Consumer updated with County field is optional")
    public void iVerifyConsumerCreatedWithCountyFieldIsOptional() {
        waitFor(2);
        jsClick(createConsumer.SaveButton);
        waitFor(2);
        assertTrue(crmDemographicContactInfoPage.addressesStatusesNotOnCase.get(0).isDisplayed());
    }

    @Then("I am navigated to General Consumer in Contact UI page")
    public void i_am_navigated_to_General_Consumer_in_Contact_UI_page() {
        assertTrue(activeContact.unlinkContactRecord.isDisplayed(), "Unlink contact record sign is not displayed");
    }

    //mui-component-select-addressState
    @Then("I verify default State dropdown to {string}")
    public void i_verify_default_state_dropdown_virginia(String data) {
        waitFor(2);
        assertTrue(createConsumer.defaltConsumerStateValue.getText().trim().equals(data));
    }

    @When("I populate Create Consumer fields for a new consumer with out County for cover-va")
    public void I_populate_Create_Consumer_fields_for_a_new_consumer_with_out_County_for_coverva() {

        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);

        //click(createConsumer.optInMailText);

        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        click(createConsumer.selectOption("MMIS"));
        waitFor(1);
        sendKeyToTextField(createConsumer.externalId_ConsumerId.get(0), String.valueOf(new Random().nextInt(99999)));
        waitFor(1);
    }

    @When("I add new email to check Primary Consumer Email Flag")
    public void iAddnewemailtocheckPrimaryConsumerFlag() {
        waitFor(2);
        click(demographicContactInfo.addEmailButton);
        waitFor(2);
        clearAndFillText(createConsumer.consumerStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDate.now().minusDays(5)).toString());
        click(createConsumer.consumerEndDate);
    }


    @When("I add new email to check default Primary Consumer Email Flag")
    public void iAddnewpemailtocheckDefaultPrimaryConsumerEmailFlag() {
        iAddnewemailtocheckPrimaryConsumerFlag();
        consumerPrimaryEmail.set(randomEmailId());
        clearAndFillText(demographicContactInfo.consumerEmailAddress, consumerPrimaryEmail.get());
        assertTrue(Boolean.valueOf(crmAddContactInfoPage.primaryCaseEnabledCheckBox.getAttribute("checked")));
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        consumerID.set(createConsumer.consumerID.getText().trim());
    }

    @Then("I verify warning message when Primary Email is available")
    public void iVerifyWarningMessageWhenPrimaryEmailisAvailable() {
        waitFor(2);
        click(demographicContactInfo.addEmailButton);
        waitFor(2);
        clearAndFillText(demographicContactInfo.consumerEmailAddress, randomEmailId());
        clearAndFillText(createConsumer.consumerStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDate.now()).toString());
        click(createConsumer.consumerEndDate);
        waitFor(3);
        click(crmAddContactInfoPage.primaryCasePhoneOrEmailCheckBox);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        assertTrue(createConsumer.newEmailWarningMessage.isDisplayed());
    }

    @When("I verify Potential Match error message is displayed")
    public void i_verify_Potential_Match_error_message_is_displayed() {
        assertTrue(createConsumer.potentialMatchWarningMessage.isDisplayed());
        waitFor(2);
    }

    @When("I update DOB of the consumer in Create Consumer Page")
    public void i_update_DOB_of_the_consumer_in_Create_Consumer_Page() {
        clearAndFillText(demographicMemberPage.consumerDoB, BrowserUtils.getPriorDateFormatYYYYMMdd(9000));
        waitFor(1);
    }

    @Then("I verify consumer email number primary indicator added on UI")
    public void iVerifyConsumerEmailNumberPrimaryIndicatorAddedOnUI() {
        waitFor(2);
        assertTrue(crmAddContactInfoPage.chkFirstEmailWithStar.isDisplayed());
    }

    @And("I edit existing email to check primary indicator flag")
    public void iEditExistingEmailToCheckPrimaryIndicatorFlag() {
        waitFor(2);
        click(createConsumer.editEmail);
        waitFor(2);
        consumerPrimaryEmail.set(randomEmailId());
        clearAndFillText(demographicContactInfo.consumerEmailAddress, consumerPrimaryEmail.get());
    }

    @Then("I verify warning message when editing already available email")
    public void iVerifyWarningMessageWhenEditPrimaryEmailisAvailable() {
        waitFor(2);
        click(demographicContactInfo.addEmailButton);
        waitFor(2);
        clearAndFillText(demographicContactInfo.consumerEmailAddress, randomEmailId());
        clearAndFillText(createConsumer.consumerStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDate.now()).toString());
        clearText(createConsumer.consumerEndDate);
        waitFor(1);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        click(createConsumer.editFirstEmail);
        waitFor(2);
        clearAndFillText(demographicContactInfo.consumerEmailAddress, randomEmailId());
        waitFor(2);
        click(crmAddContactInfoPage.primaryCasePhoneOrEmailCheckBox);
        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        assertTrue(createConsumer.newEmailWarningMessage.isDisplayed());
    }

    @And("I update existing email to set primary indicator flag")
    public void iEditExistingPEmailToSetPrimaryIndicatorFlag() {
        iEditExistingEmailToCheckPrimaryIndicatorFlag();

        click(crmAddContactInfoPage.saveButton);
        waitFor(2);
        consumerID.set(createConsumer.consumerID.getText().trim());
    }

    @When("I populate Create Consumer fields for a new consumer for IN-EB")
    public void iPopulateCreateConsumerFieldsForANewConsumerForINEB() throws Throwable {
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        selectRandomDropDownOption(createConsumer.consumerCityDropdown);
        waitFor(1);
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(1);
    }

    @When("I populate Create Consumer fields for a new  pregnant Female consumer")
    public void i_populate_Create_Consumer_fields_for_a_newFemale_consumer() {
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        waitFor(2);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectDropDown(crmAddPrimaryIndividualPage.piGender, "Female");
        crmAddPrimaryIndividualPage.pregnancyIndCheckBox.click();
        waitFor(2);
        clearAndFillText(crmAddPrimaryIndividualPage.pregnancyDueDateField, getFutureDate(50));
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);

        waitFor(2);
        // clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(2);
        if (createConsumer.checkedOptMail.getAttribute("checked") != null)
            click(createConsumer.optInMailText);
    }

    @Then("I verify email field is not displayed on profile details")
    public void i_verify_email_field_is_not_displayed_on_profile_details() {
        waitFor(1);
        Assert.assertTrue(!isElementDisplayed(manualContactRecordSearchPage.emailExpandedView));
    }

    @And("I populate email and phone number and click on create consumer button")
    public void I_populate_email_and_phone_number() {
        clearAndFillText(createConsumer.consumerDB, "06011993");
        clearAndFillText(createConsumer.consumerEmail, "test@gmail.com");
        clearAndFillText(createConsumer.consumerPhoneNum, "2223332323");
        selectDropDown(createConsumer.phoneType, "Cell");
        try {
            jsClick(createConsumer.checkedOptMail);
        } catch (Exception e) {
        }
        jsClick(createConsumer.createConsumerButton);
    }

    @Then("Verify potential matches {string} presented and I able to click and navigate to consumer profile")
    public void Verify_consumer_exist(String name) {
        assertEquals(createConsumer.consumerTable(name).getText().replaceAll("  ", " "), name.replaceAll("  ", " "), name + "NOT displayed");
        waitForVisibility(createConsumer.consumerTableRow.get(1), 3);
        jsClick(createConsumer.consumerTableRow.get(1));
        waitFor(2);
        jsClick(createConsumer.continueAddConsumer);
        waitFor(2);
        assertTrue(isElementDisplayed(crmEditConsumerProfilePage.consumerId), "NOT displayed");
    }

    @Then("I do not see a carat expansion-Authentication Grid with the option to link")
    public void I_dont_see_a_carat() {
        waitForVisibility(createConsumer.consumerTableRow.get(0), 3);
        assertEquals(createConsumer.consumerTableRow.get(0).getText(), "", "carat  is visible");
    }

    @Then("I see Phone Source field label")
    public void i_see_Phone_Source_field_label() {
        waitFor(2);
        assertTrue(createConsumer.phoneSource.isDisplayed());
    }

    @Then("I verify Phone Source is {string}")
    public void i_verify_Phone_Source_is(String value) {
        assertEquals(createConsumer.phoneSourceValue.getText(), value);
    }

    @Then("I verify Phone Source is State Reported")
    public void i_verify_Phone_Source_is_State_Reported() {
        assertTrue(createConsumer.stateReportedSource.isDisplayed());
    }

    @Then("I very Phone number is not editable")
    public boolean i_very_Phone_number_is_not_editable() {
        try {
            waitForClickablility(createConsumer.stateReportedSource, 5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Then("I verify status is {string} for State Reported Phone")
    public void i_verify_status_is_for_State_Reported_Phone(String status) {
        assertEquals(createConsumer.phoneSourceType.get(2).getText(), "State Reported");
        assertEquals(createConsumer.phoneSourceStatus.get(2).getText(), status);
        assertEquals(createConsumer.typePhone.get(2).getText(), "Cell");
    }

    @Then("I verify status is {string} for Consumer Reported Phone with the same type")
    public void i_verify_status_is_for_Consumer_Reported_Phone_with_the_same_type(String status) {
        assertEquals(createConsumer.phoneSourceType.get(1).getText(), "Consumer Reported");
        assertEquals(createConsumer.phoneSourceStatus.get(1).getText(), status);
        assertEquals(createConsumer.typePhone.get(1).getText(), "Cell");
    }

    @When("I populate {string} and {string} for existing consumer")
    public void i_populate_DoB_and_SSN_for_existing_consumer(String DoB, String ssn) {
        if (ssn.length() <= 0) {
            ssn = getRandomNumber(9);
        }
        clearAndFillText(contactRecord.dobSearch, DoB);
        clearAndFillText(contactRecord.ssnSearch, ssn);
        click(contactRecord.searchButton);
    }

    @When("I populate the rest fields and try to create consumer {string} to trigger potential match functionality")
    public void i_populate_the_rest_fields_and_try_to_create_consumer_to_trigger_potential_match_functionality(String project) {
        waitFor(2);
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        if (project.equals("BLCRM") || project.equals("COVER-VA")) {
            clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
            if (project.equals("BLCRM")) {
                selectRandomDropDownOption(createConsumer.consumerCountyField);
            }
        } else if (project.equals("INEB")) {
            selectRandomDropDownOption(createConsumer.consumerCityDropdown);
            selectRandomDropDownOption(createConsumer.consumerCountyField);
        }
        waitFor(4);
        clearAndFillText(createConsumer.consumerZipCode, "123456789");
        selectRandomDropDownOption(createConsumer.consumerState);
        waitFor(2);
        jsClick(createConsumer.createConsumerButton);
    }

    @Then("I will be presented with Consumer options that match the data I entered per")
    public void i_will_be_presented_with_Consumer_options_that_match_the_data_I_entered_per() {
        waitForVisibility(createConsumer.popUperrorMessageInCreateConsumerPage, 10);
        String message = createConsumer.popUperrorMessageInCreateConsumerPage.getText();
        assertTrue(message.contains("Potential match"), "Error message not displayed");
    }

    @Then("I am able to link the selected matching Consumer to the Contact Record {string}")
    public void linkPotentialMathConsumer(String project) {
        scrollDownUsingActions(1);
        waitFor(5);
        contactRecord.contactShowMoreInfo.click();
        waitFor(1);
        contactRecord.contactMoreInfoName.click();
        contactRecord.contactMoreInfoDOB.click();
        try {
            contactRecord.contactMoreInfoSSN.click();
        } catch (Exception e) {
            activeContact.crmConsumerID1.click();
        }
        if (project.equals("INEB")) {
            contactRecord.unableToAuthenticate.click();
        }
        waitFor(2);
        waitForVisibility(contactRecord.contactMoreInfoLinkRecord, 5);
        contactRecord.contactMoreInfoLinkRecord.click();
        assertTrue(isElementDisplayed(demographicContactInfo.demographicInfoTab), "demographicInfoTab NOT Displayed");
    }

    @Then("I am able to click on consumer id to expand Potential Match consumer")
    public void clickConsumerId() {
        new CRM_ConsumerUpdateProfile().i_click_on_consumer_record_to_navigate_Profile_Details();
    }

    @When("I populate Create Consumer fields with no county for a new consumer for IN-EB")
    public void iPopulateCreateConsumerFieldsForANewConsumerForINEBnoCounty() throws Throwable {
        System.out.println(newConsumer.get().toString());
        System.out.println(" name " + newConsumer.get().get("name"));
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        System.out.println(" surname " + newConsumer.get().get("surname"));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        clearAndFillText(createConsumer.consumerSSN, (newConsumer.get().get("ssn").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        System.out.println(" phone " + newConsumer.get().get("phone"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        selectRandomDropDownOption(createConsumer.consumerCityDropdown);
        waitFor(1);
        selectRandomDropDownOption(createConsumer.consumerState);
        String zip = newConsumer.get().get("zip").toString();
        if (zip.length() < 5)
            zip = zip + radomNumber(5 - zip.length());
        clearAndFillText(createConsumer.consumerZipCode, zip);
        waitFor(1);
    }

    @Then("I see list of Potential Match consumers table with header")
    public void verifyHeaderSearchResult(List<String> data) {
        List<WebElement> header = createConsumer.searchResultTableHeader;
        for (int i = 1; i < header.size(); i++) {
            System.out.println(header.get(i).getText() + "=============" + data.get(i));
            assertEquals(header.get(i).getText(), data.get(i), "Header name order or value is mismatch");
        }
    }

    @Then("the grid displaying the Consumer information will include")
    public void verifyGridHeaderPotentialMatch(List<String> data) {
        waitFor(2);
        List<WebElement> header = createConsumer.associatedCaseHeader;
        for (int i = 0; i < header.size(); i++) {
            System.out.println(header.get(i).getText() + "=============" + data.get(i));
            assertEquals(header.get(i).getText(), data.get(i), "Header name order or value is mismatch");
        }
    }

    @Then("I can only proceed by clicking a button labeled {string}")
    public void verifyButtonIsDisplay(String str) {
        for (WebElement each : createConsumer.useThisConsumerButton) {
            assertTrue(isElementDisplayed(each), "button not display");
        }
    }

    @When("I click on Use This Consumer button")
    public void clickOnUseThisConsumerButton() {
        waitFor(2);
        System.out.println("Click on + button on third record");
        scrollDown();
        scrollDown();
        scrollDown();
        waitFor(1);
        createConsumer.useThisConsumerButton.get(0).click();
    }


    @Then("I populate Authorized Representative field with following data")
    public void i_populate_Authorized_Representative_field_with_following_data(Map<String, String> data) {
        scrollUpUsingActions(2);
        waitFor(2);
        selectDropDown(AuthorizedRep.arAccessType, "Full Access");
        clearAndFillText(AuthorizedRep.arStartDate, ("01/01/2019"));
        clearAndFillText(AuthorizedRep.arEndDate, ("01/01/2023"));
        try {
            selectDropDown(AuthorizedRep.arReceiveCorrespondence, "Yes");
        } catch (Exception e) {
        }

        clearAndFillText(AuthorizedRep.arFirstName, data.get("firstName"));
        try {
            clearAndFillText(AuthorizedRep.arMiddleName, data.get("midleName"));
        } catch (Exception e) {
        }
        clearAndFillText(AuthorizedRep.arLastName, data.get("lastName"));
        clearAndFillText(AuthorizedRep.arDOB, data.get("DoB"));
        selectRandomDropDownOption(AuthorizedRep.arGender);
        clearAndFillText(AuthorizedRep.arSSN, data.get("SSN"));
        waitFor(3);
    }

    @Then("I should be able to edit all the fields")
    public void iShouldBeAbleToEditAllTheFields(List<Map<String, String>> data_table) {
        for (Map<String, String> data : data_table) {
            assertEquals(getText(AuthorizedRep.arAccessType), data.get("AuthorizationType"));
            assertEquals(AuthorizedRep.arStartDate.getAttribute("value"), data.get("StartDate"));
            assertEquals(getText(AuthorizedRep.arwrittenLanguage), data.get("WrittenLanguage"));
            assertEquals(AuthorizedRep.arFirstName.getAttribute("value"), data.get("Firstname"));
            assertEquals(AuthorizedRep.arMiddleName.getAttribute("value"), data.get("Middleinitial"));
            assertEquals(AuthorizedRep.arLastName.getAttribute("value"), data.get("Lastname"));
            assertEquals(AuthorizedRep.arDOB.getAttribute("value"), data.get("DOB"));
            assertEquals(getText(AuthorizedRep.arGender), data.get("Gender"));
            assertEquals(AuthorizedRep.arSSN.getAttribute("value").replaceAll("-", ""), data.get("SSN"));

        }
    }

    @Then("The following field  will not changed manually entered value")
    public void verifyFieldsNotChanged(Map<String, String> data) {
        String actualConsumerRole = demographicMemberPage.roleDropdownValue.getAttribute("value");
        String actualArAccessType = AuthorizedRep.arAccessType.getText();
        String actualStartDate = AuthorizedRep.arStartDate.getAttribute("value");
        String actualEndDate = AuthorizedRep.arEndDate.getAttribute("value");
        String actualArReceiveCorrespondence = AuthorizedRep.arReceiveCorrespondence.getText();
        System.out.println("actualConsumerRole=====" + actualConsumerRole);
        System.out.println("actualArAccessType=====" + actualArAccessType);
        System.out.println("actualStartDate========" + actualStartDate);
        System.out.println("actualEndDate==========" + actualEndDate);
        System.out.println("actualArReceiveCorrespondence==" + actualArReceiveCorrespondence);
        assertEquals(actualConsumerRole, data.get("Consumer Role"));
        assertEquals(actualArAccessType, data.get("Access Type"));
        assertEquals(actualStartDate, data.get("Start Date"));
        assertEquals(actualEndDate, data.get("End Date"));
        assertEquals(actualArReceiveCorrespondence, data.get("Receive Correspondence"));

    }

    @Then("The following field  will change manually entered values")
    public void verifyFieldsChangeValues(Map<String, String> data) {
        consumerSearchResultPage.dobMaskButtonSearchParameter.click();
        consumerSearchResultPage.ssnMaskButtonSearchParameter.click();
        String firstName = AuthorizedRep.arFirstName.getAttribute("value");
        String midleName = AuthorizedRep.arMiddleName.getAttribute("value");
        String lastName = AuthorizedRep.arLastName.getAttribute("value");
        String dob = AuthorizedRep.arDOB.getAttribute("value");
        String age = AuthorizedRep.arAge.getAttribute("value");
        String gender = AuthorizedRep.arGender.getText();
        String spokenLanguage = AuthorizedRep.arSpokenLanguage.getText();
        String writtenLanguage = AuthorizedRep.arWrittenLanguage.getText();
        String SSN = AuthorizedRep.arSSN.getAttribute("value");
        String correspondencePreferences = AuthorizedRep.correspondencePreferenceDropdownOptions.getText();


        System.out.println("firstName======" + firstName);
        System.out.println("midleName=======" + midleName);
        System.out.println("lastName=======" + lastName);
        System.out.println("dob=======" + dob);
        System.out.println("age=======" + age);
        System.out.println("gender=======" + gender);
        System.out.println("spokenLanguage=======" + spokenLanguage);
        System.out.println("writtenLanguage=======" + writtenLanguage);
        System.out.println("SSN=======" + SSN);
        System.out.println("correspondencePreferences=======" + correspondencePreferences);


        assertEquals(firstName, data.get("First Name"));
        assertEquals(midleName, data.get("Middle Initial"));
        assertEquals(lastName, data.get("Last Name"));
        assertEquals(dob, data.get("DOB"));
        assertEquals(age, data.get("Age"));
        assertEquals(gender, data.get("Gender"));
        assertEquals(spokenLanguage, data.get("Spoken Language"));
        assertEquals(writtenLanguage, data.get("Written Language"));
        assertEquals(SSN, data.get("SSN"));
        assertEquals(correspondencePreferences, data.get("Correspondence Preferences"));

        assertFalse(contactRecord.conMail.isSelected());
        assertFalse(contactRecord.conPhone.isSelected());
        assertFalse(contactRecord.conEmail.isSelected());
        assertFalse(contactRecord.conFax.isSelected());
    }

    @Then("I see error message {string} is displayed")
    public void verifyErrorMessageIsDispl(String message) {
        String actualMessage = "";
        try {
            actualMessage = Driver.getDriver().findElement(By.xpath("//*[text() = '" + message + "'] ")).getText();
        } catch (Exception e) {
            createConsumer.useThisConsumerButton.get(1).click();
            actualMessage = Driver.getDriver().findElement(By.xpath("//*[text() = '" + message + "'] ")).getText();
        }
        System.out.println("actualMessage == " + actualMessage);
        assertEquals(actualMessage, message, "message not displayed");

    }

    @When("I enter Search criteria fields for a new consumer from Third Party")
    public void i_enter_Search_criteria_fields_for_a_new_consumerfromThirdParty() {
        clearAndFillText(contactRecord.thirdPartyLastName.get(1), (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.thirdPartyFirstName.get(1), (newConsumer.get().get("surname").toString()));
        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
    }

    @When("I enter Search criteria fields for a new consumer from Third  with firstName {string} and lastName {string}")
    public void i_enter_Search_criteria_fields_for_a_new_consumerfromThirdPartyWith(String firstName, String lastName) {
        clearAndFillText(contactRecord.thirdPartyLastName.get(1), lastName);
        clearAndFillText(contactRecord.thirdPartyFirstName.get(1), firstName);
        consumerName.set(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
    }

    @And("I populate email and phone number and click on create consumer button INEB")
    public void I_populate_email_and_phone_numberINEB() {
        clearAndFillText(createConsumer.consumerDB, "06011993");
        clearAndFillText(createConsumer.consumerEmail, "test@gmail.com");
        clearAndFillText(createConsumer.consumerPhoneNum, "2223332323");
        selectDropDown(createConsumer.phoneType, "Cell");
        jsClick(createConsumer.createConsumerButton);
    }

    @Then("I invalid format date error message for {string}")
    public void iInvalidFormatDateErrorMessageFor(String field) {
        if (field.equals("DOB")) {
            assertTrue(createConsumer.invalidDobError.isDisplayed(), "Invalid format " + field + " error is not displayed");
        } else assertTrue(false);
    }

    @Then("I verify program dropdown value behaviour if External ID is missing in CoverVA")
    public void iVerifyProgramDropdownValueBehaviourIfExternalIDIsMissingCoverVA(List<Map<String, String>> data) {
        String expectedV = data.get(0).get("ExternalId_program");
        boolean isExist = false;
        List<String> actualL;
        click(createConsumer.externalIdAdd);
        waitFor(1);
        click(createConsumer.externalIdProgram.get(0));
        waitFor(1);
        actualL = getElementsText(createConsumer.externalIdProgramList);
        for (String act : actualL) {
            isExist = expectedV.contains(act.trim());
        }
        Assert.assertTrue(isExist, "Mis-match ExtranlId program list:::" + "Expected-->" + expectedV + " Actual-->" + actualL);


    }

    @Then("I click on existing email")
    public void I_click_on_existing_email() {
        waitFor(2);
        click(createConsumer.editEmail);
    }

    public boolean checkDefaultStatusStartDate(String option) {
        boolean isExpected = false;
        switch (option) {
            case "Address":
                hover(demographicContactInfo.addressesStatuses.get(0));
                waitFor(1);
                System.out.println(demographicContactInfo.statusStartDate.getText());
                System.out.println(getCurrentDate());
                isExpected = demographicContactInfo.statusStartDate.getText().equals(getCurrentDate());
                break;
            case "Phone":
                hover(demographicContactInfo.phoneStatuses.get(0));
                waitFor(1);
                System.out.println(demographicContactInfo.phonestatusStartDate.getText());
                System.out.println(getCurrentDate());
                isExpected = demographicContactInfo.phonestatusStartDate.getText().equals(getCurrentDate());
                break;
            case "Email":
                scrollDown();
                waitFor(1);
                hover(demographicContactInfo.emailStatuses.get(0));
                System.out.println(demographicContactInfo.emailStatuses.get(0).getText());
                waitFor(1);
                System.out.println(demographicContactInfo.emailStatusStartDate.getText());
                System.out.println(getCurrentDate());
                isExpected = demographicContactInfo.emailStatusStartDate.getText().equals(getCurrentDate());
                break;

        }
        waitFor(1);
        System.out.println(demographicContactInfo.statusStartDate.getText());
        System.out.println(getCurrentDate());
        isExpected = demographicContactInfo.statusStartDate.getText().equals(getCurrentDate());
        return isExpected;
    }


}
