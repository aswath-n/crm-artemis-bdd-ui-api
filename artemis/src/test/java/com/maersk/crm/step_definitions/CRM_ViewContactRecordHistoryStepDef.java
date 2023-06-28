package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class CRM_ViewContactRecordHistoryStepDef extends CRMUtilities implements ApiBase {


    Actions action = new Actions(Driver.getDriver());
    CRMLinksComponentPage linksComponentPage = new CRMLinksComponentPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRM_ContactRecordUIStepDef contactRecordStepDef = new CRM_ContactRecordUIStepDef();
    CRM_Edit_ContactRecord_Page edit_cont_Rec = new CRM_Edit_ContactRecord_Page();
    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    CRMApplicationSearchPage applicationSearchPage = new CRMApplicationSearchPage();
    CRMAddCaseMemberPage addCaseMemberPage = new CRMAddCaseMemberPage();
    CRM_Edit_ContactRecord_Page editContactRecordPage = new CRM_Edit_ContactRecord_Page();
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();

    final ThreadLocal<CRM_CreateConsumerProfileStepDef> createConsumerStepDef = ThreadLocal.withInitial(CRM_CreateConsumerProfileStepDef::new);
    final ThreadLocal<CRM_ContactRecordUIStepDef> contactRecordUISepDef = ThreadLocal.withInitial(CRM_ContactRecordUIStepDef::new);
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);

    public static final ThreadLocal<String> reasonForEditUpdatedValue = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> thirdPartyContactId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> savedConsumerID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> contactRecordType = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> inboundCallQueueOption = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> callCampaignOption = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> outcomeCallOption = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> ContactId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> birthday = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> contentType = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> externalId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> email = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> applicationIDValue = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactReason = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> contactAction = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<Boolean> isInbound = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Boolean> isOutbound = ThreadLocal.withInitial(() -> false);


    @Then("I verify all the columns on Contact History table are present")
    public void i_verify_all_the_columns_on_Contact_History_table_are_present() {
        assertTrue(contactHistory.contactIdColumn.isDisplayed());
        assertTrue(contactHistory.dateColumn.isDisplayed());
        assertTrue(contactHistory.consumerNameColumn.isDisplayed());
        assertTrue(contactHistory.typeColumn.isDisplayed());
        assertTrue(contactHistory.reasonColumn.isDisplayed());
        assertTrue(contactHistory.dispositionCol.isDisplayed());
    }

    @Then("I scroll up to Contact Reasons and comments")
    public void i_scroll_up_to_Contact_Reasons_and_comments() {
        //scrollUp();
        waitFor(1);
    }

    @When("I click on initiate a contact button")
    public void i_click_on_initiate_a_contact_button() {
        waitFor(3);
        contactRecord.initContact.click();
        waitForVisibility(contactRecord.contactInProgressGreenSign, 15);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());
    }

    @When("I verify Contact History has one record with {string}")
    public void i_verify_Contact_History_has_one_record_with(String expectedReason) {
        assertTrue(contactHistory.contactIDs.size() == 2, "There should lines of records displayed");
        System.out.println(contactHistory.reasonsList.get(1).getText());
        assertTrue(contactHistory.reasonsList.get(1).getText().equals(expectedReason), "Non-expected reason is displayed");
    }

    @When("I click on {string} column and verify it is in descending or ascending order")
    public void i_click_on_column_and_verify_it_is_in_descending_or_ascending_order(String column) {
        switch (column) {
            case "Contact ID":
                waitFor(3);
                assertTrue(descendingOrderContactIDs(contactHistory.contactIdsHistory), column + " column is not in descending order");
                contactHistory.contactIdColumn.click();
                waitFor(5);
                assertTrue(ascendingOrderContactIDs(contactHistory.contactIdsHistory), column + " column is not in ascending order");
                break;
            case "Date":
                //todo check after bug "Incorrect Date of contacts is fixed"
                contactHistory.dateColumn.click();
                waitFor(1);
                assertTrue(descendingOrderDates(contactHistory.dates), column + " column is not in descending order");
                contactHistory.dateColumn.click();
                waitFor(5);
                assertTrue(ascendingOrderDates(contactHistory.dates), column + " column is not in ascending order");
                break;
            case "Consumer":
                contactHistory.consumerNameColumn.click();
                waitFor(1);
                assertTrue(descendingOrderTexts(contactHistory.consumers), column + " column is not in descending order");
                contactHistory.consumerNameColumn.click();
                waitFor(5);
                assertTrue(ascendingOrderTexts(contactHistory.consumers), column + " column is not in ascending order");
                break;
            case "Type":
                contactHistory.typeColumn.click();
                waitFor(1);
                assertTrue(descendingOrderTexts(contactHistory.types), column + " column is not in descending order");
                contactHistory.consumerNameColumn.click();
                waitFor(1);
                assertTrue(ascendingOrderTexts(contactHistory.types), column + " column is not in ascending order");
                break;
            case "Disposition":
                contactHistory.dispositionColumn.click();
                waitFor(1);
                assertTrue(descendingOrderTexts(contactHistory.dispositions), column + " column is not in descending order");
                contactHistory.consumerNameColumn.click();
                waitFor(1);
                assertTrue(ascendingOrderTexts(contactHistory.dispositions), column + " column is not in ascending order");
                break;
            //refactoring 12/03/18
        }
    }

    @Then("I see no more then {int} Contact history records are displayed")
    public void i_see_no_more_then_Contact_history_records_are_displayed(Integer expected) {
        int records = contactHistory.contactIDs.size();
        assertTrue(records <= expected, "There should not be more than " + records + " records displayed");
    }

    //CRM-2232
    @When("I close the current Contact Record and re-initiate a new Contact Record")
    public void i_close_the_current_Contact_Record_and_re_initiate_a_new_Contact_Record() {

        waitFor(1);
        contactRecord.stopContact.click();
        waitFor(1);
        //refactored by Vinuta - call campaign is for outbound calls only
        //selectOptionFromMultiSelectDropDown(contactRecord.callCamp, "Enrollment Reminder");
        //waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        //refactored by Vinuta- call campaign is for outbound calls only
        //selectDropDown(contactRecord.outcomeOfContact, "Did Not Reach/Left Voicemail");
        //waitFor(2);

        contactRecord.closeButton.click();
        waitFor(2);
//        waitForVisibility(dashboard.activeContactTab, 3);
//        dashboard.activeContactTab.click();
        jsClick(contactRecord.initContact);
        waitFor(1);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "New Contact is not initiated");
    }

    @When("I verify a new contact re-initiated")
    public void i_verify_a_new_contact_re_initiated() {
        waitFor(2);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "New Contact is not initiated");
    }

    @When("I add New Consumer to the record")
    public void i_add_New_Consumer_to_the_record() {
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        firstName.set(newConsumer.get().get("name").toString());
        lastName.set(newConsumer.get().get("surname").toString());
        birthday.set(newConsumer.get().get("birthday").toString());

        System.out.println(birthday.get() + "@#$%^&*()_)(*&^%$#@!#$%^&*()_");
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        jsClick(contactRecord.searchButton);
        waitFor(2);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        scrollUp();
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(1);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        waitFor(1);
        createConsumerStepDef.get().i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
        savedConsumerID.set(activeContact.consumerID.getText());
        System.out.println(savedConsumerID.get());
    }

    @When("I search for an existing contact record by {string}")
    public void i_search_for_an_existing_contact_record_by(String searchCriteria) {
        if (searchCriteria.equals("firstName")) {
            manualContactRecordSearchPage.manualSearchFirstName.sendKeys(newConsumer.get().get("name").toString());
        } else if (searchCriteria.equals("lastName")) {
            manualContactRecordSearchPage.manualSearchLastName.sendKeys(newConsumer.get().get("surname").toString());
        } else if (searchCriteria.equals("internalConsumerId")) {
            manualContactRecordSearchPage.manualSearchInternalConsumerId.sendKeys(savedConsumerID.get());
        } else if (searchCriteria.equals("date")) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String time = sdf.format(date);
            System.out.println("time " + time);
            manualContactRecordSearchPage.manualSearchDate.sendKeys(time);
        } else if (searchCriteria.equals("disposition")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.manualSearchDisposition, "Complete");
        } else if (searchCriteria.equals("type")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.consumerTypeDropdown, "Third Party");
        } else if (searchCriteria.equals("contactType")) {
            selectRandomOptionFromMultiSelectDropDown(manualContactRecordSearchPage.manualSearchContactType);
        } else if (searchCriteria.equals("internalCaseId")) {
            manualContactRecordSearchPage.manualSearchInternalCaseId.sendKeys("7346");
        }
        manualContactRecordSearchPage.searchButton.click();
    }

    @When("I save the contact as {string} and {string}")
    public void i_save_the_contact_as_and(String contactType, String contactChanel) {
        scrollUpUsingActions(3);
//        contactRecord.expendReasonButton.click();
        selectDropDown(contactRecord.contactReason, "Information Request");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Case Status/Information");
        waitFor(1);
        contactRecord.saveReasonButton.click();
        if (!contactChanel.equals("Phone")) {
            selectDropDown(contactRecord.contactChannelType, contactChanel);
            selectDropDown(contactRecord.contactType, contactType);
        }
        if (!contactType.equals("Inbound")) {
            selectDropDown(contactRecord.contactType, contactType);
        }
        if (contactChanel.equals("Phone") || contactChanel.equals("SMS Text")) {
            waitFor(2);
            clearAndFillText(activeContact.contactPhoneNumber, newConsumer.get().get("phone").toString());
        } else {
            clearAndFillText(activeContact.contactEmail, newConsumer.get().get("email").toString());
            System.out.println("email " + newConsumer.get().get("email").toString());
        }
        singleSelectFromDropDown(activeContact.programTypes, "Program A");
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I verify Contact Record Component has unique ContactRecordId, name = {string}, type = {string}, status date is present, and status = {string}")
    public void i_verify_Contact_Record_Component_has_unique_ContactRecordId_name_type_status_date_is_present_and_status(String name, String type, String status) {
        waitFor(3);
        assertTrue(linksComponentPage.contactRecordIdOnTaskPage.isDisplayed());
        waitFor(2);
        assertEquals(linksComponentPage.contactRecordNameOnTaskPage.getText(), name);
        waitFor(3);
        assertEquals(linksComponentPage.contactRecordTypeOnTaskPage.getText(), type);
        waitFor(2);
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        System.out.println("today's date " + dateFormat.format(date));
        assertEquals(linksComponentPage.contactRecordDateOnTaskPage.getText(), dateFormat.format(date));
        waitFor(3);
        assertEquals(linksComponentPage.contactRecordStatusOnTaskPage.getText(), status);
    }

    @When("I save the contact with {string} option")
    public void i_save_the_contact_with_option(String inboundCallQueueOptionExample) {
        scrollUpUsingActions(3);
//        contactRecord.expendReasonButton.click();
        selectDropDown(contactRecord.contactReason, "Information Request");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Case Status/Information");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        selectDropDown(contactRecord.contactChannelType, "Phone");
        selectDropDown(contactRecord.contactType, "Inbound");

        clearAndFillText(activeContact.contactPhoneNumber, newConsumer.get().get("phone").toString());
        if (inboundCallQueueOptionExample.equals("random")) {
            selectRandomOptionFromMultiSelectDropDown(contactRecord.inboundCallQueueDropDownSelection);
        } else {
            selectOptionFromMultiSelectDropDown(contactRecord.inboundCallQueueDropDownSelection, inboundCallQueueOptionExample);
        }
        inboundCallQueueOption.set(contactRecord.inboundCallQueueDropDownSelection.getText());
        System.out.println("inboundCallQueueOption " + inboundCallQueueOption.get());

        singleSelectFromDropDown(activeContact.programTypes, "Program A");
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");

        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I save the contact with  contactType as {string} and {string} option and {string}")
    public void i_save_the_contact_with_contactType_as_and_option_and(String contactTypeEx, String callCampaignEx, String outcomeOfContactEx) {
        scrollUpUsingActions(3);
//        contactRecord.expendReasonButton.click();
        selectDropDown(contactRecord.contactReason, "Information Request");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Case Status/Information");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        selectDropDown(contactRecord.contactChannelType, "Phone");
        selectDropDown(contactRecord.contactType, contactTypeEx);

        clearAndFillText(activeContact.contactPhoneNumber, newConsumer.get().get("phone").toString());
        if (callCampaignEx.equals("random")) {
            selectRandomOptionFromMultiSelectDropDown(contactRecord.callCampaignDropDown);
        } else {
            selectOptionFromMultiSelectDropDown(contactRecord.callCampaignDropDown, callCampaignEx);
        }
        if (outcomeOfContactEx.equals("random")) {
            selectRandomOptionFromMultiSelectDropDown(contactRecord.outcomeOfContactDropDown);
        } else {
            selectOptionFromMultiSelectDropDown(contactRecord.outcomeOfContactDropDown, outcomeOfContactEx);
        }

        callCampaignOption.set(contactRecord.callCampaignDropDown.getText());
        System.out.println("call campaign " + callCampaignOption.get());
        outcomeCallOption.set(contactRecord.outcomeOfContactDropDown.getText());
        System.out.println("outcome call  " + outcomeCallOption.get());

        singleSelectFromDropDown(activeContact.programTypes, "Program A");
        waitFor(3);
        selectDropDown(contactRecord.contactDispositions, "Complete");

        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I search for an existing contact record by PhoneNumber under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_PhoneNumber_under_Advanced_Search() {
        String phoneNumber = newConsumer.get().get("phone").toString();
        System.out.println("phoneNum " + phoneNumber);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.manualSearchChannel, "Phone");
        manualContactRecordSearchPage.phoneNumberTextBox.sendKeys(phoneNumber);
        System.out.println("1");
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by ContactRecordId under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_ContactRecordId_under_Advanced_Search() {
        String contactRecordId = manualContactRecordSearchPage.contactRecordIdText.getText();
        System.out.println("contactRecordId " + contactRecordId);
        manualContactRecordSearchPage.phoneNumberTextBox.click();
        manualContactRecordSearchPage.phoneNumberTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        System.out.println("phone clear");
        manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(contactRecordId);
        System.out.println("contact record id sent");
        manualContactRecordSearchPage.searchButton.click();
    }

    @When("I search for an existing contact record by UserID under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_UserID_under_Advanced_Search() {
        String UserID = tdm.get("CrmConsumerId");
        System.out.println("userID " + UserID);
        manualContactRecordSearchPage.advanceSearchEmployeeID.sendKeys(UserID);
        System.out.println("1");
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by EmailAddress under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_EmailAddress_under_Advanced_Search() {
        String emailAddress = newConsumer.get().get("email").toString();
        System.out.println("emailAddress " + emailAddress);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.manualSearchChannel, "Email");
        manualContactRecordSearchPage.emailAddressAdvancedSearch.sendKeys(emailAddress);
        System.out.println("1");
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by CreatedBy under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_CreatedBy_under_Advanced_Search() {
        String createdByAccount = getServiceAccountUserName(ConfigurationReader.getProperty(("login")));
        System.out.println("createdByAccount " + createdByAccount);
        manualContactRecordSearchPage.createdByAdvancedSearch.sendKeys(createdByAccount);
        waitFor(2);
        System.out.println("1");
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by Inbound Call Queue Option under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_Inbound_Call_Queue_Option_under_Advanced_Search() {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, inboundCallQueueOption.get());
        System.out.println("1");
        waitFor(3);
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by Call Campaign under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_Call_Campaign_under_Advanced_Search() {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callCampaignDropDown, callCampaignOption.get());
        System.out.println("1");
        waitFor(3);
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I search for an existing contact record by Outcome of Call under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_Outcome_of_Call_under_Advanced_Search() {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callOutcomeDropDown, outcomeCallOption.get());
        System.out.println("search outcome of the call " + outcomeCallOption.get());
        System.out.println("1");
        waitFor(3);
        manualContactRecordSearchPage.searchButton.click();
        System.out.println("2");
    }

    @When("I navigate to contact history page after re-initiating a new contact with the same Consumer")
    public void i_navigate_to_contact_history_page_after_re_initiating_a_new_contact_with_the_same_Consumer() throws InterruptedException {
        waitFor(2);
        contactRecord.initContact.click();
        waitForVisibility(contactRecord.firstName, 10);
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        System.out.println("++++++++++++");
        System.out.println(newConsumer.get().get("name"));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecordUISepDef.get().i_link_the_contact_to_an_existing_Case_or_Consumer_Profile();
        waitFor(1);
        contactRecordDashboard.caseContactDetailsTab.click();
    }

    @Then("I see expected {string} on Contact Record History Page")
    public void i_see_expected_on_Contact_Record_History_Page(String icon) {
        waitFor(2);
        assertTrue(contactHistory.contactTypeChanelIconIsPresent(icon));
    }

    @Then("I see expected {string} when hover over {string} on Contact Record History Page")
    public void i_see_expected_when_hover_over_on_Contact_Record_History_Page(String iconTitleText, String icon) {
        waitFor(2);
        assertTrue(contactHistory.getIconsHoverOverText(icon).equalsIgnoreCase(iconTitleText), "Hover over text should be '" + iconTitleText + "'.");
    }

    @Then("I verify newly added contact record with third party details displayed in contact history grid")
    public void verifyThirdPartyRecordAdded() {
        waitFor(3);
        assertEquals(contactHistory.contactHistoryRowTwo.get(1).getText(), CRM_CORE_HistoryTabStepDef.classContactId.get());
        assertEquals(contactHistory.contactHistoryRowTwo.get(2).getText(), getCurrentDate());
        String firstName = APIConsumerPopulationDmnController.consumerFirstName.get();
        String lastName = APIConsumerPopulationDmnController.consumerLastName.get();
        assertTrue(contactHistory.contactHistoryRowTwo.get(3).getText().contains(firstName) && contactHistory.contactHistoryRowTwo.get(3).getText().contains(lastName));
        assertEquals(contactHistory.contactHistoryRowTwo.get(4).getText(), "Third Party");
    }

    @When("I read the contact id for the latest contact added with third party details")
    public void getContactIdForContactWithThirdPartyDetails() {
        waitFor(2);
        int count = 0;
        for (WebElement type : contactHistory.types) {
            if (type.getText().equalsIgnoreCase("THIRD PARTY")) {
                thirdPartyContactId.set(contactHistory.contactIDs.get(count).getText());
                break;
            }
        }
    }

    @When("I select the latest contact id with type {string}")
    public void selectContactRecordWIthGivenType(String recordType) {
        waitForVisibility(contactHistory.types.get(1), 10);
        waitFor(2);
        // scrollUp();
        scrollToElement(contactRecordDashboard.contactHistory);
        boolean recordFound = false;
        int count = 0;
        int size = Driver.getDriver().findElements(By.xpath("//ul[@class='pagination']/li")).size();
        if (size == 1)
            size = size + 2;
        for (int i = 1; i <= size - 2; i++) {
            count = 0;
            for (int k = 0; k < contactHistory.types.size(); k++) {
//            for (WebElement type : contactHistory.types) {
                String type = contactHistory.types.get(k).getText();
                System.out.println(type);
                System.out.println(recordType);
                if (type.equalsIgnoreCase(recordType)) {
                    if (contactHistory.inbountOutBoundIcons.get(count).getAttribute("title").contains("Inbound")) {
                        isOutbound.set(false);
                        isInbound.set(true);
                    } else if (contactHistory.inbountOutBoundIcons.get(count).getAttribute("title").contains("Outbound")) {
                        isOutbound.set(true);
                        isInbound.set(false);
                    } else {
                        isOutbound.set(false);
                        isInbound.set(false);
                    }
                    if (isOutbound.get()) {
                        String personIcon = Driver.getDriver().findElement(By.xpath("//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[" + (count + 1) + "]//td[2]/i")).getText();
                        assertTrue(personIcon.equalsIgnoreCase("person"));
                    }
                    contactHistory.contactIDs.get(k).click();
                    recordFound = true;
                    break;
                }
                count++;
            }
            if (recordFound) {
                break;
            } else {
                Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']//li/a[text()='" + (i + 1) + "']")).click();
                waitFor(2);
            }
        }
    }

    @Then("I verify third party details are displayed as read only fields")
    public void verifyThirdPartyRecordDetailsReadOnly() {
        waitFor(2);
        assertTrue(contactHistory.lblThirdPartyFirstName.isDisplayed());
        assertTrue(contactHistory.lblThirdPartyLastName.isDisplayed());
        assertTrue(contactHistory.lblThirdPartyOrganizationName.isDisplayed());
        assertTrue(contactHistory.lblThirdPartyConsumerType.isDisplayed());
        assertTrue(contactHistory.lblThirdPartyPreferredLanguage.isDisplayed());
    }

    @Then("I verify that the displayed details match with the details entered while creating contact {string}, {string}, {string}, {string} and {string}")
    public void verifyThirdPartyRecordDetailsAreMatching(String fname, String lname, String orgName, String consumerType, String preferredLanguage) {
        waitFor(2);
        assertEquals(contactHistory.lblThirdPartyFirstName.getText().toUpperCase(), fname.toUpperCase());
        assertEquals(contactHistory.lblThirdPartyLastName.getText().toUpperCase(), lname.toUpperCase());
        assertEquals(contactHistory.lblThirdPartyOrganizationName.getText().toUpperCase(), orgName.toUpperCase());
        assertEquals(contactHistory.lblThirdPartyConsumerType.getText().toUpperCase(), consumerType.toUpperCase());
        assertEquals(contactHistory.lblThirdPartyPreferredLanguage.getText().toUpperCase(), preferredLanguage.toUpperCase());
    }

    @Then("I verify that contact channel type and contact channel details are matching {string} and {string}")
    public void verifyThirdPartyRecordDetailsAreMatching(String contactChannelType, String data) {
        waitFor(2);
        if (contactChannelType.equalsIgnoreCase("Phone") || contactChannelType.equalsIgnoreCase("SMS Text")) {
            String number = data.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
            assertEquals(contactHistory.channelValue.getText(), contactChannelType);
            assertEquals(contactHistory.phoneValue.getText(), number);
        } else {
            assertEquals(contactHistory.channelValue.getText(), contactChannelType);
            assertEquals(contactHistory.emailValue.getText(), data);
        }
    }

    @Then("I verify contact record details in contact history is displayed as {string}")
    public void verifyContactRecordDetailsAreMatching(String userName) {
        waitFor(2);
        assertEquals(contactHistory.lblUserName.getText(), userName);
        if (CRM_ContactRecordUIStepDef.caseId.get() != null && !CRM_ContactRecordUIStepDef.caseId.get().isEmpty())
            assertEquals(contactHistory.lnkConsumerId.getText(), CRM_ContactRecordUIStepDef.consumerId.get());
        assertEquals(contactHistory.lblUserId.getText(), "8369");
        assertTrue(contactHistory.lblContactId.getText().contains(CRM_CORE_HistoryTabStepDef.classContactId.get()));
        assertTrue(contactHistory.lblContactEnd.getText().contains(getCurrentDate()), "Actual date: " + contactHistory.lblContactEnd.getText() + " Expected date: " + getCurrentDate());
        assertTrue(contactHistory.lblContactStart.getText().contains(getCurrentDate()), "Actual date: " + contactHistory.lblContactEnd.getText() + " Expected date: " + getCurrentDate());
    }

    @Then("I verify person icon displayed before contact id for out bound contact type")
    public void verifyPersonIconForOutbound() {
        waitFor(2);
        assertTrue(contactHistory.lblContactId.findElement(By.xpath("./i")).getText().equalsIgnoreCase("person"));
    }

    @When("I click on edit icon the Contact Details page")
    public void clickEditContactButton() {
        waitFor(3);
        try {
            jsClick(contactHistory.btnEdit);
        } catch (NoSuchElementException e) {
            contactRecord.editContactIcon.click();
        }
    }

    @When("I click on edit icon the Contact Details page new")
    public void clickEditContactButtonNew() {
        waitFor(2);
        try {
            jsClick(contactRecord.editContactIcon);
        } catch (NoSuchElementException e) {
            contactHistory.btnEdit.click();
        }
    }

    @Then("I verify contact record exist with the reason {string}")
    public void verifyRecordWithReason(String reason) {
        waitFor(2);
        scrollDownUsingActions(1);
        waitFor(2);
        boolean recordFound = false;
        int count = 0;
        int size = Driver.getDriver().findElements(By.xpath("//ul[@class='pagination']/li")).size();
        if (size == 1)
            size = size + 2;
        for (int i = 1; i <= size - 2; i++) {
            count = 0;
            for (WebElement reasonEle : contactHistory.reasons) {
                if (reasonEle.getText().equalsIgnoreCase(reason)) {
                    recordFound = true;
                    break;
                }
                count++;
            }
            if (recordFound)
                break;
            else
                Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']//li/a[text()='" + (i + 1) + "']")).click();
        }
        Assert.assertTrue(recordFound);
    }

    @Then("I verify contact disposition is updated and reason for edit {string} and {string}")
    public void verifyContactDispositionAndReasonForEdit(String disposition, String reasonForEdit) {
        waitFor(2);
        assertEquals(contactHistory.readOnlyContactDisposition.getText(), disposition);
        assertEquals(contactHistory.lblReasonForEdit.getText(), reasonForEdit);
    }

    @When("I re-initiate a new contact with previously created consumer to expand saved contact record")
    public void i_re_initiate_a_new_contact_with_previously_created_consumer_to_expand_saved_contact_record() {
        logout();
        contactRecordStepDef.logIntoSystemFromLoginStep();
        createConsumerStepDef.get().searchForExistingConsumerStep();
        contactRecordStepDef.searchForExistingRecordStep();
    }

    @Then("I see the previously created inbound contact record")
    public void i_see_the_previously_created_inbound_contact_record() {
        contactRecordStepDef.verifyInboundContactRecordValuesStep();
        assertTrue(contactHistory.btnEdit.isDisplayed(), "Edit button is not displayed");
        contactHistory.btnEdit.click();
        for (WebElement el : contactHistory.reasonForEditDropDownValues) {
            assertTrue(el.isDisplayed(), "Reasons for edit section is not displayed");
        }
    }

    @Then("I should see the previously created outbound contact record")
    public void i_should_see_the_previously_created_outbound_contact_record() {
        contactRecordStepDef.verifyOutboundContactRecordValuesStep();
        assertTrue(contactHistory.manualIcon.isDisplayed(), "Manual Ourbond contact record Icon is not displayed");
        contactHistory.btnEdit.click();
        for (WebElement el : contactHistory.reasonForEditDropDownValues) {
            assertTrue(el.isDisplayed(), "Reasons for edit section is not displayed");
        }
    }

    /*Verfies the hover text on the contact history records
        Author - Aswath
        Date - 04/12/2019
        Parameters - Accepts SContact channel type value
        */


    @Then("I verify all the hover text on Contact icon Case {string}")
    public void i_verify_all_the_hover_text_on_contact_icon_case_something(String contactchanneltype) {
        getHoverContactIcons(contactchanneltype);
    }

    /*Verfies the hover text on the contact history records
        Author - Aswath
        Date - 04/12/2019
        Parameters - Accepts SContact channel type value
        */

    public void getHoverContactIcons(String option) {

        String valIconHoverTxt = "";
        String valIconHoverTxtCampMan = "";
        switch (option) {
            case "Web Chat":
                valIconHoverTxt = contactHistory.contactIconTitle.getAttribute("title");
                valIconHoverTxtCampMan = contactHistory.campManualIcon.getAttribute("title");
                System.out.println(valIconHoverTxtCampMan);

                if (valIconHoverTxt.contains("Outbound Web Chat")) {
                    assertEquals(valIconHoverTxt, "Outbound Web Chat", "Outbound hover text value is not displayed");
                    assertEquals(valIconHoverTxtCampMan, "Manual Contact", "Manual icon hover text value is not displayed");
                } else {
                    assertEquals(valIconHoverTxt, "Inbound Web Chat", "Outbound hover text value is not displayed");
                }

                break;
            case "Email":
                valIconHoverTxt = contactHistory.contactIconTitle.getAttribute("title");
                valIconHoverTxtCampMan = contactHistory.campManualIcon.getAttribute("title");
                if (valIconHoverTxt.contains("Outbound Email")) {
                    assertEquals(valIconHoverTxt, "Outbound Email", "Outbound hover text value is not displayed");
                    assertEquals(valIconHoverTxtCampMan, "Manual Contact", "Manual hover text value is not displayed");
                } else {
                    assertEquals(valIconHoverTxt, "Inbound Email", "Inbound hover text value is not displayed");
                }
                break;
            case "Phone":
                valIconHoverTxt = contactHistory.contactIconTitle.getAttribute("title");
                System.out.println("1 valIconHoverTxt");
                valIconHoverTxtCampMan = contactHistory.campManualIcon.getAttribute("title");
                System.out.println("2 valIconHoverTxtCampMan");
                System.out.println("valIconHoverTxt " + valIconHoverTxt);
                System.out.println("valIconHoverTxtCampMan " + valIconHoverTxtCampMan);

                if (valIconHoverTxt.contains("Inbound Phone")) {
                    assertEquals(valIconHoverTxt, "Inbound Phone", "Inbound hover text value is not displayed");
                } else {
                    assertEquals(valIconHoverTxt, "Outbound Phone", "Outbound hover text value is not displayed");
                    assertEquals(valIconHoverTxtCampMan, "Manual Contact", "Manual hover text value is not displayed");
                }
                break;
            case "SMS Text":
                valIconHoverTxt = contactHistory.contactIconTitle.getAttribute("title");
                System.out.println("1 valIconHoverTxt");
                valIconHoverTxtCampMan = contactHistory.campManualIcon.getAttribute("title");
                System.out.println("2 valIconHoverTxtCampMan");
                System.out.println("valIconHoverTxt " + valIconHoverTxt);
                System.out.println("valIconHoverTxtCampMan " + valIconHoverTxtCampMan);
                if (valIconHoverTxt.contains("Inbound SMS Text")) {
                    assertEquals(valIconHoverTxt, "Inbound SMS Text", "Inbound hover text value is not displayed");
                } else {
                    assertEquals(valIconHoverTxt, "Outbound SMS Text", "Outbound hover text value is not displayed");
                    assertEquals(valIconHoverTxtCampMan, "Manual Contact", "Manual hover text value is not displayed");
                }
                break;
        }
    }


    @When("I select the latest contact record with type {string}")
    public void i_select_the_latest_contact_record_with_type(String recordType) {
        waitFor(2);
        scrollUpUsingActions(2);
        int count = 0;
        for (WebElement type : contactHistory.types) {
            if (type.getText().equalsIgnoreCase(recordType)) {
                jsClick(contactHistory.contactIDs.get(count));
                //contactHistory.contactIDs.get(count).click();
                break;
            }
            count++;
        }
    }

    @When("I click on contactId if record type is {string}")
    public void iClickOnContactIdIfRecordTypeIsGeneral(String expectedReason) {
        assertTrue(contactHistory.contactIDs.size() == 1, "There should lines of records displayed");
        System.out.println(contactHistory.types.get(0).getText());
        assertTrue(contactHistory.types.get(0).getText().equals(expectedReason),
                "Non-expected type is displayed");
        jsClick(contactHistory.contactIDs.get(0));
    }

    @When("I click on edit button on contact history page")
    public void clickOnEditButton() {
        waitForVisibility(contactHistory.editButton.get(0), 10);
        jsClick(contactHistory.editButton.get(0));
    }

    @And("I select reason for edit drop down and provide details to edit")
    public void selectReasonForEditDropDown() {
        selectDropDown(contactHistory.reasonForEditDropDown, "Adding Additional Comment");
        clearAndFillText(contactRecord.commentArea, "Adding comments for edit");
        contactRecord.saveAdditionalComments.click();
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.contactDispositions, "Dropped");
        waitFor(2);
        jsClick(contactHistory.saveBtn);
        waitFor(10);
        waitForVisibility(contactHistory.contactIDs.get(0), 10);
        jsClick(contactHistory.contactIDs.get(0));
    }

    @Then("I will verify all section header orders")
    public void verifyAllSectionHeaderOrder() {
        waitForVisibility(contactHistory.generalConsumerInContact, 10);
        Assert.assertTrue(contactHistory.generalConsumerInContact.isDisplayed());
        Assert.assertTrue(contactHistory.contactDetailsHeader.isDisplayed());
        Assert.assertTrue(contactHistory.wrapUpAndCloseHeader.isDisplayed());
        Assert.assertTrue(contactHistory.contactEditReasonHeader.isDisplayed());
        Assert.assertTrue(contactHistory.linkHeader.isDisplayed());
    }

    @And("I will verify link section contains linked case or consumer")
    public void verifyLinkSectionContainsLinkedCaseOrConsumer() {
        Assert.assertTrue(contactHistory.linkSectionId.isDisplayed());
    }

    @And("I verify whether it contains contact details and edit history tabs and only one edit button for page")
    public void verifyContactDetailsAndEditHistoryAndEditButtonIsDisplayed() {
        Assert.assertTrue(contactHistory.editButton.get(0).isDisplayed());
        Assert.assertEquals(contactHistory.editButton.size(), 1);
        Assert.assertTrue(contactHistory.contactDetailsTAB.isDisplayed());
        Assert.assertTrue(contactHistory.editHistoryTab.isDisplayed());
    }

    @When("I search for a consumer that I just created")
    public void i_search_for_a_consumer_that_I_just_created() {
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        System.out.println("++++++++++++");
        System.out.println(newConsumer.get().get("name"));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
    }

    @When("I link the consumer to authenticate the consumer")
    public void i_link_the_consumer_to_authenticate_the_consumer() {
        waitFor(2);
        waitForClickablility(contactRecord.expandButtonforLink, 5);
        scrollDown();
        consumerId.set(contactRecord.consumerIdFirstRecord.getText());
        jsClick(contactRecord.expandButtonforLink);
        waitFor(2);
        jsClick(contactRecord.fullNameRadioButton); //contactRecord.fullNameRadioButton.click();
        jsClick(contactRecord.DOBradioButton); //contactRecord.DOBradioButton.click();
        //    contactRecord.SSNradioButton.click();
        jsClick(contactRecord.consumerIDcheckButton); //contactRecord.consumerIDcheckButton.click();
        contactRecord.linkRecordButtonOnContactRecordUI.click();
        System.out.println(newConsumer.get().get("name") + "  " + newConsumer.get().get("surname") + "Consumer authenticated");
    }

    @When("I choose a contact reason from reason dropdown list")
    public void i_choose_a_contact_reason_from_reason_dropdown_list() {
        scrollUpUsingActions(2);
        selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "Complaint - Benefits Issues");
    }

    @Then("I choose a contact action from action dropdown list")
    public void i_choose_a_contact_action_from_action_dropdown_list() {
        selectDropDown(contactRecord.actionList, "Escalated");
        waitFor(1);
        Driver.getDriver().findElement(By.xpath("//body")).click();
    }

    @Then("I choose contact action and contact reason in {string} project")
    public void i_choose_contact_action_and_reason_in_project(String projectName) {
        scrollUpUsingActions(2);
        if (projectName.equalsIgnoreCase("BLCRM")) {
            selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "Complaint - Benefits Issues");
            selectDropDown(contactRecord.actionList, "Escalated");
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//body")).click();
        } else if (projectName.equalsIgnoreCase("NJ-SBE")) {
            selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "Broker Support");
            waitFor(1);
            selectDropDown(contactRecord.actionList, "Certification Status");
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//body")).click();
        } else if (projectName.equalsIgnoreCase("IN-EB")) {
            selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "HCC Outbound");
            waitFor(2);
            selectDropDown(contactRecord.actionList, "Outbound Dialer");
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//body")).click();
        } else if (projectName.equalsIgnoreCase("CoverVA")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "Change Request");
            waitFor(2);
            selectDropDown(contactRecord.actionList, "Address Change");
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//body")).click();
            waitFor(1);
            contactRecord.commentArea.click();
            clearAndFillText(contactRecord.contactAddComments, "Adding comments for CoverVA");
            jsClick(contactRecord.reasonSaveButton);
        }

    }

    @Then("I enter ApplicationId for CoverVA")
    public void i_enter_applicationId_for_CoverVA() {
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
    }

    @When("I search for a consumer that I just created for general contact")
    public void i_search_for_a_consumer_that_I_just_created_for_general_contact() {
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        System.out.println("++++++++++++");
        System.out.println(newConsumer.get().get("name"));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        contactRecord.searchButton.click();
        waitFor(2);

    }

    //modifiedBy: vidya Date:01/16/2020 Reason: Added wait and line of code to take consumer id
    @When("I link the consumer for general contact")
    public void i_link_the_consumer_for_general_contact() {
        waitFor(1);
        contactRecord.expandButtonforLink.click();
        contactRecord.fullNameRadioButton.click();
        contactRecord.DOBradioButton.click();
        contactRecord.SSNradioButton.click();
        consumerId.set(contactRecord.consumerIdFirstRecord.getText());
        contactRecord.consumerIDcheckButton.click();
        contactRecord.linkRecordButtonOnContactRecordUI.click();
        System.out.println(newConsumer.get().get("name") + "  " + newConsumer.get().get("surname") + "Consumer authenticated");
    }


    @Then("I enter additional comments {string}")
    public void i_enter_additional_comments(String string) {
        staticWait(1000);
        if (!isElementDisplayed(contactRecord.additionalComments))
            contactRecord.expend.click();
        contactRecord.additionalComments.click();
        clearAndFillText(contactRecord.additionalCommentsTextBox, "First additional comment");
        contactRecord.saveCallSummaryButton.click();
        System.out.println("test");
    }

    @Then("I click save button for reason and action")
    public void i_click_save_button_for_reason_and_action() {
        jsClick(contactRecord.reasonSaveButton);
    }

    @Then("I click the edit button to edit reason, action, and comment {string}")
    public void i_click_the_edit_button_to_edit_reason_action_and_comment(String string) {
        scrollDownUsingActions(2);
        jsClick(contactRecord.expandreasonsComments);
        //  jsClick(contactRecord.getEditAreaButton);
        waitFor(2);
        jsClick(contactRecord.editReasonActionButton);
        scrollDownUsingActions(2);
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.expandContactReasonDropDownUpdate, "Enrollment - Family Dental");
        selectOptionFromMultiSelectDropDown(contactRecord.expandContactActionDropDownUpdate, "Enrolled - Molina");
        waitFor(1);

        // contactRecord.editCommentArea.click();
        clearAndFillText(contactRecord.editCommentArea, "Third last updated reason and action");
        scrollDownUsingActions(2);
        jsClick(contactRecord.editReasonSaveButton);
    }

    @When("I click on save button for saving additional comment")
    public void i_click_on_save_button_for_saving_additional_comment() {
        jsClick(contactRecord.saveAdditionalCommentsButton);
    }

    @When("I edit the previously saved Additional comments")
    public void i_edit_the_previously_saved_Additional_comments() {
        waitFor(2);
        jsClick(contactRecord.expandAdditionalComments);
        waitFor(2);
        jsClick(contactRecord.editAdditionalComments);

        jsClick(contactRecord.additionalCommentsTextBox2);
        clearAndFillText(contactRecord.additionalCommentsTextBox2, "Updated comment");

        jsClick(contactRecord.saveAdditionalEditCommentsButton);
        waitFor(1);
    }

    @Then("I select {string} as contact program type")
    public void i_select_as_contact_program_type(String program) {
        waitFor(3);
        selectOptionFromMultiSelectDropDown(activeContact.programTypes, program);
        waitFor(2);
    }

    @Then("I click expand arrow to see consumers")
    public void i_click_expand_arrow_to_see_consumers() {
        scrollDownUsingActions(6);
        scrollDown();
        waitFor(5);

        contactRecord.expandButtonforLink.click();
        waitFor(3);

        contactRecord.fullNameRadioButton.click();
        contactRecord.DOBradioButton.click();
        contactRecord.SSNradioButton.click();
        contactRecord.consumerIDcheckButton.click();
        contactRecord.linkRecordButtonOnContactRecordUI.click();
        System.out.println(newConsumer.get().get("name") + "  " + newConsumer.get().get("surname") + "Consumer authenticated");
    }

    @Then("I click on to latest contact id to see contact record details")
    public void i_click_on_to_latest_contact_id_to_see_contact_record_details() {
        contactRecordType.set(contactRecord.contactRecordTypeField.getText());
        scrollUpUsingActions(2);
        waitFor(2);
        contactRecord.latestContactRecord.click();
        System.out.println("contact record type: " + contactRecordType.get());
    }

    @Then("I verify the contact record type")
    public void i_verify_the_contact_record_type() {
        contactRecord.getEditedByContactRecordType(contactRecordType.get());
    }

    @Then("I edit contact record by choosing reason for edit")
    public void i_edit_contact_record_by_choosing_reason_for_edit() {
        waitFor(2);
        jsClick(contactRecord.editContactButton);
        waitFor(4);
        i_choose_a_contact_reason_from_reason_dropdown_list();
        i_choose_a_contact_action_from_action_dropdown_list();
        i_click_save_button_for_reason_and_action();
        //scrollDownUsingActions(6);
        waitFor(6);

        selectDropDown(contactRecord.editReasonListContactRecord, "Adding Contact Reason/Action");
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitFor(2);
    }


    @When("I click on to latest contact id to see contact record details and edit contact record")
    public void i_click_on_to_latest_contact_id_to_see_contact_record_details_and_edit_contact_record() {
        scrollUpUsingActions(2);
        waitFor(2);
        contactRecord.latestContactRecord.click();
        contactRecord.editContactButton.click();
        waitFor(4);

        scrollDownUsingActions(6);
        waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecord.contactRecordReasonForEdit, "Adding Additional Comment");
        waitFor(2);
        clearAndFillText(contactRecord.additionalCommentsTextBox, "editing contact record");
        jsClick(contactRecord.saveAdditionalComments);
        waitFor(3);
        jsClick(contactRecord.saveContactRecordEditButton);
    }


    /* Author -Paramita
    This method to  verify 'Third Party Details' header relabel to 'Third Party-Consumer in Contact
     */
    @Then("I verify 'Third Party Details' header relabel to 'Third Party-Consumer in Contact'")
    public void verifyThirdPartyHeaderLabel() {
        waitForVisibility(contactHistory.thirdPartyLabelText, 10);
        Assert.assertTrue(contactHistory.thirdPartyLabelText.isDisplayed(), "Header label is not displaying");
    }


    /* Author -Paramita
   This method to verify Standard Links Component
    */
    @And("I verify Standard Links Component")
    public void verifyStandardLinksComponent() {
        Assert.assertTrue(contactHistory.thirdPartylinkHeader.isDisplayed(), "Standard Links Component is not dsiplaying");
    }


    /* Author -Paramita
    This method to verify Case ID and/or Consumer ID reference to the Standard Link Component in 'THIRD PARTY - CONSUMER CONTACTED ABOUT' component
  */
    @And("I verify Case ID and Consumer ID reference to the Standard Link Component in THIRD PARTY - CONSUMER CONTACTED ABOUT component")
    public void verifyCaseIDCosumerID() {
        Assert.assertTrue(contactHistory.thirdPartyCaseID.isDisplayed(), "Case ID column is not displaying");
        Assert.assertTrue(contactHistory.thirdPartyConsumerCaseID.isDisplayed(), "Consumer ID column is not displaying");
        // Assert.assertTrue(!contactHistory.thirdPartyValue.get(0).getText().isEmpty(), "Case ID value showing empty");
        Assert.assertTrue(!contactHistory.thirdPartyValue.get(1).getText().isEmpty(), "Consumer ID value showing empty");
    }


    /* Author -Paramita
   This method to verify Edit button displayed at page level
 */
    @And("I verify Edit button displayed at page level")
    public void verifyEditButtonDisplayedPageLevel() {
        Assert.assertTrue(contactHistory.thirdPartyEditContact.isDisplayed(), "Edit Button not displaying");
    }


    /* Author -Paramita
      This method to verify wrap up and close component is displayed below contact details section
    */
    @And("I verify wrap up and close component is displayed below contact details section")
    public void verifyWrapUpAndCloseSectionDisplayedAfterContactDetails() {
        waitForVisibility(contactRecordDashboard.wrapUpCloseSec, 10);
        Assert.assertTrue(contactRecordDashboard.wrapUpCloseSec.isDisplayed(), "Wrap and Close component is not displaying");

    }

    /* Author -Paramita
     This method to click on Save button in Edit Third Party Contact screen
   */
    @And("I click on Save button in Edit screen")
    public void clickOnSaveButton() {
        jsClick(contactHistory.editSave);
        waitFor(6);

    }


    /* Author -Paramita
        This method to verify different sections of Third Party components in sequence order
      */
    @Then("I verify different sections of Third Party components in sequence order")
    public void verifyThirdPartyComponents() {
        waitFor(3);
        String[] hOnethirdPartySection = {"THIRD PARTY - CONSUMER IN CONTACT", "CONTACT DETAILS", "WRAP-UP AND CLOSE"};
        String[] hFivethirdPartySection = {"THIRD PARTY - CONSUMER CONTACTED ABOUT", "CONTACT EDIT REASONS", "LINKS"};
        Assert.assertTrue(contactHistory.hOneThirdPartySection.get(0).getText().replace("person", "").trim().contains(hOnethirdPartySection[0]), "Section values are wrong");
        Assert.assertTrue(contactHistory.hOneThirdPartySection.get(1).getText().replace("file_copy", "").trim().contains(hOnethirdPartySection[1]), "Section values are wrong");
        Assert.assertTrue(contactHistory.hOneThirdPartySection.get(2).getText().replace("publish", "").trim().contains(hOnethirdPartySection[2]), "Section values are wrong");
        Assert.assertTrue(contactHistory.hFiveThirdPartySection.get(1).getText().replace("person", "").trim().contains(hFivethirdPartySection[0]), "Section values are wrong");
        Assert.assertTrue(contactHistory.hFiveThirdPartySection.get(2).getText().replace("person", "").trim().contains(hFivethirdPartySection[1]), "Section values are wrong");
        Assert.assertTrue(contactHistory.hFiveThirdPartySection.get(3).getText().replace("link", "").trim().contains(hFivethirdPartySection[2]), "Section values are wrong");
    }

    /* Author -Paramita
       This method to verify available tabs name in Third Party Contact Page
     */
    @And("I verify available tabs name")
    public void verify_available_tabs_name() {
        String tabName[] = {"CONTACT DETAILS", "EDIT HISTORY"};
        //System.out.println("Size :" +contactHistory.tabMenuName.size());
        for (int m = 0; m < contactHistory.tabMenuName.size(); m++) {
            Assert.assertTrue(contactHistory.tabMenuName.get(m).getText().contains(tabName[m]), "Tabs name are showing wrong");
        }
    }

    @Then("I verify that edited By field is displayed")
    public void i_verify_that_edited_By_field_is_displayed() {
        assertTrue(contactRecord.contactRecordEditedByField.isDisplayed(), "Edited by field isnt displayed");
        assertTrue(contactRecord.contactRecordEditedByField.getText().equals("EDITED BY"), "Name of the edited by field didnt match");
//        Assert.assertTrue(contactHistory.editB.getText().endsWith(editedBy));
    }

    @Then("I verify that User's First and Last Name who made edit is displayed")
    public void i_verify_that_User_s_First_and_Last_Name_who_made_edit_is_displayed() {
        waitFor(5);
        scrollDownUsingActions(3);
        Assert.assertTrue(contactRecord.editedByNameContact.isDisplayed());

        Assert.assertTrue(contactRecord.editedByNameContact.getText().contains(getServiceAccountUserName(ConfigurationReader.getProperty("login"))));
        System.out.println("Edited name is " + contactRecord.editedByNameContact.getText());
    }

    @Then("I verify that User's First and Last Name who made edit is more than {int} characters")
    public void i_verify_that_User_s_First_and_Last_Name_who_made_edit_is_more_than_characters(Integer size) {
        waitFor(5);
        scrollDownUsingActions(3);
        String editedByName = contactRecord.editedByNameContact.getText();
        System.out.println("Edited by is: " + editedByName);

        String[] splitEditedByName = editedByName.split(" ");
        String firstName = splitEditedByName[0];
        String lastName = splitEditedByName[1];

        Assert.assertTrue(firstName.length() <= size, "First name is longer than 15 characters");
        System.out.println("FirstName of edited by: " + firstName);

        Assert.assertTrue(lastName.length() <= size);
        System.out.println("LastName of edited by: " + lastName);
    }

    @When("I close the current Contact Record and re-initiate a new Contact Record for Outbound")
    public void i_close_the_current_Contact_Record_and_re_initiate_a_new_Contact_Record_for_Outbound() {

        waitFor(1);
        contactRecord.stopContact.click();
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(1);

        contactRecord.closeButton.click();
        waitFor(2);
        jsClick(contactRecord.initContact);
        waitFor(1);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "New Contact is not initiated");
    }

    @When("I choose contact disposition and click on save button to save contact record")
    public void i_choose_contact_disposition_andclick_on_save_button_to_save_contact_record() {
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(5);
        jsClick(contactRecord.closeButton);
        //   contactRecord.closeButton.click();
        waitFor(5);

    }

    @Then("Verify contact record has reason {string}")
    public void verifyContactRecordHasReason(String comment) {
        waitFor(2);
        scrollDownUsingActions(1);
        waitFor(2);
        for (WebElement reasonEle : contactHistory.reasons) {
            Assert.assertTrue(reasonEle.getText().equalsIgnoreCase(comment));
        }
    }

    @Then("Search with CONTACT ID")
    public void searchWithCONTACTID() {
        waitFor(1);
        contactRecord.caseContactDetailsTab.click();
        waitFor(2);
        ContactId.set(manualContactRecordSearchPage.contactiD.getText());
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        manualContactRecordSearchPage.manualSearchContactRecordId.click();
        clearAndFillText(manualContactRecordSearchPage.manualSearchContactRecordId, ContactId.get());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I search with {string} than CreatedOn date")
    public void iSearchWithThanCurrentDay(String symbol) {
        waitFor(1);
        contactRecord.caseContactDetailsTab.click();
        waitFor(2);
        ContactId.set(manualContactRecordSearchPage.contactiD.getText());
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        manualContactRecordSearchPage.manualSearchContactRecordId.click();
        clearAndFillText(manualContactRecordSearchPage.manualSearchContactRecordId, ContactId.get());
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.createdDataSymbol, symbol);
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.CreatedOn, getCurrentDate());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I search with First name")
    public void iSearchWithFirstName() {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, firstName.get());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @And("Verify result  with search First Name")
    public void verifyResultWithSearchFirstName() {
        Assert.assertTrue(manualContactRecordSearchPage.consumerNameSearchRes.getText().contains(firstName.get()));
        waitFor(1);
    }

    @Then("I search with Last Name")
    public void iSearchWithLastName() {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.lasttNameSearch, lastName.get());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @And("Verify result  with search Last Name")
    public void verifyResultWithSearchLastName() {
        Assert.assertTrue(manualContactRecordSearchPage.consumerNameSearchRes.getText().contains(lastName.get()));
        waitFor(1);
    }

    @Then("I search with FirstName and ConsumerType")
    public void iSearchWithCaseIdAndConsumerType() {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        if (contentType.get().equalsIgnoreCase("MEDICAID")) {
            selectDropDown(manualContactRecordSearchPage.searchConsumerType, "Medicaid");
        } else {
            selectDropDown(manualContactRecordSearchPage.searchConsumerType, contentType.get());
        }
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, firstName.get());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @When("I add New Consumer to the record with Consumer Id Type {string}")
    public void iAddNewConsumerToTheRecordWithConsumerIdType(String contype) {
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        firstName.set(newConsumer.get().get("name").toString());
        lastName.set(newConsumer.get().get("surname").toString());
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        // assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        scrollUp();
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        selectDropDown(manualContactRecordSearchPage.consumerIdType, contype);
        contentType.set(contype);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        externalId.set(getRandomNumber(6));
        clearAndFillText(manualContactRecordSearchPage.consumerExternalId, externalId.get());
        clearAndFillText(createConsumer.consumerPhoneNum, phoneNumber.get());
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(1);
        email.set(newConsumer.get().get("email").toString());
        clearAndFillText(createConsumer.consumerEmail, email.get());
        waitFor(1);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        waitFor(1);
        createConsumerStepDef.get().i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
        savedConsumerID.set(activeContact.consumerID.getText());
        System.out.println(savedConsumerID.get());
    }

    @Then("I search with CaseId length of up to {int} characters")
    public void iSearchWithCaseIdLenghtOfUpToCharacters(int len) {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        clearAndFillText(manualContactRecordSearchPage.caseIdSearch, getRandomString(len));
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(3);
    }

    @And("Verify length of CaseId not accepted")
    public void verifyLenghtOfCaseIdNotAccepted() {
        waitFor(1);
        Assert.assertTrue(manualContactRecordSearchPage.caseIdSearch.getText().isEmpty());
    }

    @Then("I navigate to Contact Record Search Page and Verify that {string} are available")
    public void InavigateToContacRecordverifyThatAreAvailable(String type) {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.searchConsumerType, type);
    }

    @Then("I search with ConsumerId field length of up to {int} characters")
    public void iSearchWithConsumerIdFieldLengthOfUpToCharacters(int len) {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        clearAndFillText(manualContactRecordSearchPage.manualSearchInternalConsumerId, getRandomString(len));
        waitFor(2);
    }

    @And("Verify length of ConsumerId not accepted")
    public void verifyLengthOfConsumerIdNotAccepted() {
        Assert.assertTrue(manualContactRecordSearchPage.manualSearchInternalConsumerId.getText().isEmpty());
    }

    @And("Verify {string} are displayed")
    public void verifyAreDisplayed(String type) {
        selectDropDown(manualContactRecordSearchPage.contactRecordTypeSearch, type);
    }

    @And("I navigate to {string}")
    public void iNavigateTo(String tab) {
        if (tab.equalsIgnoreCase("General")) {
            manualContactRecordSearchPage.generalCon.click();
        } else if (tab.equalsIgnoreCase("Third Party")) {
            manualContactRecordSearchPage.thirdPartCon.click();
        } else if (tab.equalsIgnoreCase("Unidenfied")) {
            manualContactRecordSearchPage.unidentifiedCon.click();
        }
    }

    @Then("will verify Contact Record types are displayed")
    public void willVerifyContactRecordTypesAreDisplayed(List<String> conRec) {
        for (String type : conRec) {
            selectDropDown(manualContactRecordSearchPage.contactRecordTypeSearch, type);
            waitFor(1);
        }
    }

    @And("Verify options {string} for Contact Channel")
    public void verifyOptionsForContactChannel(String type) {
        waitFor(1);
        jsClick(dashboard.contactRecordSearchTab);
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.channelSearch, type);
    }

    @And("Verify I will see the options for {string} are displayed")
    public void iWillSeeTheOptionsForAreDisplayed(String drop) {
        selectDropDown(manualContactRecordSearchPage.dispositionSearch, "Cancelled");
        manualContactRecordSearchPage.disComplete.click();
        manualContactRecordSearchPage.disDropped.click();
        manualContactRecordSearchPage.disEscalate.click();
        manualContactRecordSearchPage.disIncomplete.click();
        manualContactRecordSearchPage.disOutboundIncomplete.click();
        manualContactRecordSearchPage.disRequestedCallBack.click();
        manualContactRecordSearchPage.disTransfer.click();
    }

    @Then("Will verify INBOUND CALL QUEUE types are displayed")
    public void willVerifyINBOUNDCALLQUEUETypesAreDisplayed(List<String> types) {
        for (String type : types) {
            selectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, type);
            waitFor(1);
        }
    }

    @Then("I will verify Call Campaign")
    public void iWillVerifyCallCampaign() {
        manualContactRecordSearchPage.advancedSearch.click();
        selectDropDown(manualContactRecordSearchPage.callCamp, "Enrollment Reminder");
        manualContactRecordSearchPage.paymentRem.click();
        manualContactRecordSearchPage.progInfo.click();
    }

    @Then("I will verify Outcome of Contact")
    public void iWillVerifyOutcomeOfContact() {
        manualContactRecordSearchPage.advancedSearch.click();
        selectDropDown(manualContactRecordSearchPage.outComeCon, "Did Not Reach/Left Voicemail");
        manualContactRecordSearchPage.noVoicmail.click();
        manualContactRecordSearchPage.invalidPhone.click();
        manualContactRecordSearchPage.reachedSuccess.click();
    }

    @Then("Verify search with Created By")
    public void verifySearchWithCreatedBy() {
        waitForVisibility(manualContactRecordSearchPage.advancedSearch, 3);
        manualContactRecordSearchPage.advancedSearch.click();
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        waitFor(1);
        manualContactRecordSearchPage.createdBy.sendKeys("Aswath Nagaraju");
        contactRecord.searchButton.click();
        waitFor(2);
        Assert.assertTrue(manualContactRecordSearchPage.consumerNameSearchRes.getText().contains(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
    }

    @Then("I will verify search with UserId")
    public void iWillVerifySearchWithUserId() {
        manualContactRecordSearchPage.advancedSearch.click();
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, "sgfdfhgf");
        clearAndFillText(manualContactRecordSearchPage.userId, getRandomString(6));
        waitFor(1);
        manualContactRecordSearchPage.createdBy.sendKeys("Aswath Nagaraju");
        contactRecord.searchButton.click();
    }

    @Then("I will verify search with Phone number")
    public void iWillVerifySearchWithPhoneNumber() {
        manualContactRecordSearchPage.advancedSearch.click();
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        clearAndFillText(manualContactRecordSearchPage.phoneSearch, APIUtilitiesForUIScenarios.getInstance().getPhoneNumber());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(3);
        Assert.assertTrue(manualContactRecordSearchPage.consumerNameSearchRes.getText().contains(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
    }

    @Then("I will verify search with Email Address")
    public void iWillVerifySearchWithEmailAddress() {
        manualContactRecordSearchPage.advancedSearch.click();
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        clearAndFillText(manualContactRecordSearchPage.emailSearch, APIUtilitiesForUIScenarios.getInstance().getContactEmail());
        waitFor(2);
        contactRecord.searchButton.click();
        Assert.assertTrue(manualContactRecordSearchPage.consumerNameSearchRes.getText().contains(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
    }

    @Then("I search with Third Party Organization length of up to {int} characters")
    public void iSearchWithThirdPartyOrganizationLengthOfUpToCharacters(int len) {
        manualContactRecordSearchPage.advancedSearch.click();
        waitFor(1);
        manualContactRecordSearchPage.thirdOrgName.sendKeys(getRandomString(len));
        waitFor(1);
        contactRecord.searchButton.click();
    }

    @And("I should be able view all expanded third party parameters")
    public void iShouldBeAbleViewAllExpandedThirdPartyParameters() {
        waitFor(2);
        List<WebElement> listOfSearchParams = manualContactRecordSearchPage.expandedViewSearchParam;
        List<String> listOfSearchParamText = new ArrayList<>();
        for (WebElement listOfSearchParam : listOfSearchParams) {
            listOfSearchParamText.add(listOfSearchParam.getText());
        }
        assertTrue(listOfSearchParamText.contains("CREATED BY"), "CREATED BY parameter missing");
        assertTrue(listOfSearchParamText.contains("PHONE NUMBER"), "PHONE NUMBER parameter missing");
        assertTrue(listOfSearchParamText.contains("INBOUND CALL QUEUE"), "INBOUND CALL QUEUE parameter missing");
        assertTrue(listOfSearchParamText.contains("OUTCOME OF CALL"), "OUTCOME OF CALL parameter missing");
        assertTrue(listOfSearchParamText.contains("USER ID"), "USER ID parameter missing");
        assertTrue(listOfSearchParamText.contains("EMAIL"), "EMAIL parameter missing");
        assertTrue(listOfSearchParamText.contains("CALL CAMPAIGN"), "CALL CAMPAIGN parameter missing");
        assertTrue(listOfSearchParamText.contains("THIRD PARTY NAME"), "THIRD PARTY NAME parameter missing");
        assertTrue(listOfSearchParamText.contains("3RD PARTY ORGANIZATION"), "3RD PARTY ORGANIZATION parameter missing");
    }

    @Then("I search for the Third Party {string} in the advance search parameter")
    public void iSearchForTheThirdPartyInTheAdvanceSearchParameter(String organizationName) {
        manualContactRecordSearchPage.advancedSearch.click();
        waitFor(1);
        manualContactRecordSearchPage.thirdOrgName.sendKeys(organizationName);
        waitFor(1);
        contactRecord.searchButton.click();
    }

    @And("I should be able to view Third Party {string} as part of in the expanded view")
    public void iShouldBeAbleToViewThirdPartyAsPartOfInTheExpandedView(String organizationName) {
        manualContactRecordSearchPage.expandFieldButton.click();
        waitFor(2);
        organizationName = organizationName.replaceAll(" ", "");
        String actualOrgName = manualContactRecordSearchPage.thirdPtyOrgNameValue.getText().replaceAll(" ", "");
        Assert.assertEquals(actualOrgName, organizationName, "Expected and actual organization name does not match in the expanded view");
    }

    @When("I save the contact as {string} and {string} for NJ-SBE project")
    public void i_save_the_contact_as_and_for_NJ_SBE_project(String contactType, String contactChanel) {
        scrollUpUsingActions(3);
//        contactRecord.expendReasonButton.click();
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDownNew(contactRecord.contactAction, "Unable to Resolve Issue");
        waitFor(1);
        contactRecord.saveReasonButton.click();
        if (!contactChanel.equals("Phone")) {
            selectDropDown(contactRecord.contactChannelType, contactChanel);
            selectDropDown(contactRecord.contactType, contactType);
        }
        if (!contactType.equals("Inbound")) {
            selectDropDown(contactRecord.contactType, contactType);
        }
        if (contactChanel.equals("Phone") || contactChanel.equals("SMS Text")) {
            waitFor(2);
            clearAndFillText(activeContact.contactPhoneNumber, newConsumer.get().get("phone").toString());
        } else {
            clearAndFillText(activeContact.contactEmail, newConsumer.get().get("email").toString());
            System.out.println("email " + newConsumer.get().get("email").toString());
        }
        singleSelectFromDropDown(activeContact.programTypes, "GetCovered NJ");
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @And("I save created reason and action")
    public void iSaveCreatedReasonAndAction() {
        waitFor(3);
        contactRecord.reasonSvBttn.click();
    }

    @And("I search by Phone and enter {string}")
    public void iSearchByPhoneAndEnter(String arg0) {
        manualContactRecordSearchPage.phoneNumberTextBox.sendKeys(arg0);
        manualContactRecordSearchPage.searchButton.click();
    }

    @When("I click on initiate contact button and continue button in warning popup")
    public void i_click_on_initiate_contact_button_and_continue_button_in_warning_popup() {
        waitFor(3);
        contactRecord.initContact.click();
        waitForVisibility(dashboard.warningPopupContinueBtn, 10);
        //jsClick(dashboard.warningPopupContinueBtn);
        dashboard.warningPopupContinueBtn.click();
        waitFor(2);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());
        scrollUpRobotKey();
        scrollUpUsingActions(2);
        waitFor(2);
    }

    @Then("I click on Call Managment button")
    public void iClickOnCallManagmentButton() {
        try {
            consumerId.set(edit_cont_Rec.consumerId.getText());
        } catch (Exception e) {
        }
        waitForVisibility(activeContact.callManagment, 10);
        activeContact.callManagment.click();
    }

    @When("I save the contact and click {string} to electronic signature for COVER-VA")
    public void iSaveTheContactAndClickToElectronicSingnatureForCOVERVA(String elSigBox) {
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());
        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();
        waitFor(3);
        selectDropDown(edit_cont_Rec.elecSignCheckBox, elSigBox);
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I choose Contact Reason for Cover VA {string} and associated Contact Action")
    public void i_choose_Contact_Reason_for_Cover_VA_and_associated_Contact_Action(String opt) {
        waitFor(3);
        if (opt.equalsIgnoreCase("Appeal")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("New Application - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("New Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Renewal - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Renewal - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Pre-Release Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Re-Entry Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            scrollDown();
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Expedited Applications - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            waitFor(2);
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Complaint")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, "Complaint");
            waitFor(2);
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.reasonsComments.click();
            contactRecord.reasonsComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Change Request")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.editContactRes, "Change Request");
            waitFor(2);
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.otherReasonComments.click();
            contactRecord.otherReasonComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        } else if (opt.equalsIgnoreCase("Complaint - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, "Complaint - CVIU");
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
            action.sendKeys(Keys.ESCAPE).build().perform();
            contactRecord.otherReasonComments.click();
            contactRecord.otherReasonComments.sendKeys("hdht");
            contactRecord.saveReasonButton.click();
        }
    }

    @And("I searching for contact with electronic signature and choosing first record")
    public void iSearchingForForContactWithElectronicSignature() {
        edit_cont_Rec.advancedSearch.click();
        waitFor(1);
        clearAndFillText(edit_cont_Rec.phoneNumber, phoneNumber.get());
        waitFor(5);
        contactRecord.searchButton.click();
        waitFor(6);
        waitForVisibility(edit_cont_Rec.frstRecord, 13);
        waitFor(2);
        edit_cont_Rec.frstRecord.click();
    }

    @Then("I verify Electronic Signature Captured Checkbox")
    public void iVerifyElectronicSignatureCapturedCheckbox() {
        waitFor(2);
        assertEquals(edit_cont_Rec.viewElSignConRecordNew.getText(), "Yes");
    }

    @And("I edit Electronic Signature Checkbox in contact record")
    public void iEditElectronicSignatureCheckboxInContactRecord() {
        waitFor(2);
        jsClick(contactRecord.editContactButton);
        waitFor(4);
        scrollUpUsingActions(2);
        selectOptionFromMultiSelectDropDown(contactRecord.reasonList, "Change Request");
        selectDropDown(contactRecord.actionList, "Escalated to Supervisor");
        waitFor(1);
        jsClick(contactRecord.reasonSaveButton);
        waitFor(6);
        scrollDownUsingActions(2);
        edit_cont_Rec.elSigcheckboxEditPage.click();
        selectDropDown(contactRecord.editReasonListContactRecord, "Adding Additional Comment");

        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitFor(2);
    }

    @Then("I verify edit Electronic Signature Captured Checkbox to Unchecked")
    public void iVerifyEditElectronicSignatureCapturedCheckboxUnchecked() {
        waitFor(2);
        jsClick(contactRecord.editContactButton);
        assertEquals(edit_cont_Rec.elecSignCheckBox.getText(), "No");
    }

    @And("I edit Electronic Signature Checkbox in contact record with {string} Reason for Edit")
    public void iEditElectronicSignatureCheckboxInContactRecordWithReasonForEdit(String reason) {
        if (reason.equalsIgnoreCase("Correcting Contact Details")) {
            waitFor(2);
            jsClick(contactRecord.editContactButton);
            selectDropDown(edit_cont_Rec.elecSignCheckBox, "No");
            waitFor(2);
            selectDropDown(contactRecord.editReasonListContactRecord, reason);

            waitFor(2);
            jsClick(contactRecord.saveContactRecordEditButton);
        } else {
            throw new CucumberException("This Contact Reason Not handle in method");
        }
    }

    @Then("I verify Electronic Signature Captured Checkbox Unchecked")
    public void iVerifyElectronicSignatureCapturedCheckboxUnchecked() {
        assertEquals(edit_cont_Rec.electronicSignCaptured.getText(), "No");
    }

    @And("I click on Call Managment window")
    public void iClickOnCallManagmentWindow() {
        waitForVisibility(activeContact.callManagment, 10);
        activeContact.callManagment.click();
    }

    @Then("I verify the error message for not providing comments in Contact Reasons")
    public void i_verify_the_error_message_for_not_providing_comments_in_Contact_Reasons() {
        waitForVisibility(activeContact.callManagment, 10);
        activeContact.callManagment.click();
        waitFor(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        selectDropDown(contactRecord.contactReason, "General Inquiry");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
        waitFor(2);
        contactRecord.reasonsComments.click();
        waitFor(2);
        clearAndFillText(contactRecord.reasonsComments, " ");
        waitForVisibility(contactRecord.saveReasonButton, 10);
        jsClick(contactRecord.saveReasonButton);
        Assert.assertTrue(contactRecord.commentRequireError.isDisplayed());
    }

    @Then("I verify comments field is successfully saved when providing at least {int} character")
    public void i_verify_comments_field_is_successfully_saved_when_providing_at_least_character(Integer int1) {
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "h");
        waitForVisibility(contactRecord.saveReasonButton, 10);
        jsClick(contactRecord.saveReasonButton);
    }

    @Then("I click on expand reason button to edit previous saved comment and verify the error message")
    public void i_click_on_expand_reason_button_to_edit_previous_saved_comment() {
        waitFor(4);
        crmContactRecordUIPage.expandEditReasonButton.click();
        waitForVisibility(crmContactRecordUIPage.editCreatedCommentBtn, 5);
        crmContactRecordUIPage.editCreatedCommentBtn.click();
        clearAndFillText(contactRecord.editExistingCommentNew, " ");
        jsClick(contactRecord.saveEditReasonButton);
        waitFor(3);
        Assert.assertTrue(isElementDisplayed(contactRecord.commentRequireError));
        waitFor(3);
        contactRecord.editExistingCommentNew.click();
        clearAndFillText(contactRecord.editExistingCommentNew, "a");
        jsClick(contactRecord.saveEditReasonButton);
    }

    @Then("I click on expand reason button to edit previous contact reason")
    public void i_click_on_expand_reason_button_to_edit_previous_contact_reason() {
        waitFor(4);
        crmContactRecordUIPage.expandEditReasonButton.click();
        waitForVisibility(crmContactRecordUIPage.editCreatedCommentBtn, 5);
        crmContactRecordUIPage.editCreatedCommentBtn.click();
    }

    @Then("I verify Electronic Signature Captured checkbox is displayed as a multi-select drop with following options")
    public void i_verify_Electronic_Signature_Captured_checkbox_is_displayed_as_a_multi_select_drop_with_following_options(List<String> options) {
        waitFor(2);
        Assert.assertTrue(edit_cont_Rec.elecSignCheckBox.isDisplayed());
        for (String opt : options) {
            if (opt.equalsIgnoreCase("N/A")) {
                selectDropDown(edit_cont_Rec.elecSignCheckBox, opt);
            } else if (opt.equalsIgnoreCase("No")) {
                selectDropDown(edit_cont_Rec.elecSignCheckBox, opt);
            } else if (opt.equalsIgnoreCase("Yes")) {
                selectDropDown(edit_cont_Rec.elecSignCheckBox, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I verify Electronic Signature Captured checkbox is hidden")
    public void i_verify_Electronic_Signature_Captured_checkbox_is_hidden() {
        waitFor(1);
        assert (!edit_cont_Rec.elecSignCheckBox.isDisplayed());
    }

    @When("I save the contact without choose an option in Electronic Signature Dropdown")
    public void i_save_the_contact_without_choose_an_option_in_Electronic_Signature_Dropdown() {
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @Then("I get an error {string}")
    public void i_get_an_error(String errorMsg) {
        Assert.assertTrue(edit_cont_Rec.elecSignErrorTxt.isDisplayed());
    }

    @Given("I search for contact with Electronic Signature {string}")
    public void i_search_for_contact_with_Electronic_Signature(String elSigBox) {
        waitFor(3);
        manualContactRecordSearchPage.advancedSearch.click();
        clearAndFillText(edit_cont_Rec.consumerIdSearch, consumerId.get());
        clearAndFillText(edit_cont_Rec.phoneNumber, phoneNumber.get());
        selectDropDown(edit_cont_Rec.elecSignCheckBox, elSigBox);
        contactRecord.SearchButton.click();
    }

    @Then("I verify Electronic Signature is captured in expanded contact record search results")
    public void i_verify_Electronic_Signature_is_captured_in_expanded_contact_record_search_results() {
        waitFor(2);
        manualContactRecordSearchPage.expandFieldButton.click();
        Assert.assertTrue(edit_cont_Rec.elecSignCapturedTxt.isDisplayed());
    }

    @Then("I verify Electronic Signature is captured in View page with options {string}")
    public void i_verify_Electronic_Signature_is_captured_in_View_page_with_options(String elSigBox) {
        waitFor(3);
        edit_cont_Rec.frstRecord.click();
        waitFor(2);
        assertEquals(edit_cont_Rec.viewElSignOption.getText(), elSigBox);
    }

    @Then("I verify Electronic Signature is captured in Edit page with options {string}")
    public void i_verify_Electronic_Signature_is_captured_in_Edit_page_with_options(String elSigBox) {
        waitFor(2);
        assertEquals(edit_cont_Rec.elecSignCheckBox.getText(), elSigBox);
    }

    @When("I searching for contact with Application ID")
    public void i_searching_for_contact_with_Application_ID() {
        waitFor(2);
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        applicationSearchPage.applicationID.click();
        waitFor(2);
        applicationSearchPage.applicationID.sendKeys(applicationIDValue.get());
        contactRecord.SearchButton.click();
        waitFor(2);
    }

    @Then("I verify Application ID matches the contact")
    public void i_verify_Application_ID_matches_the_contact() {
        String consumerID = contactRecord.consumerId.getText();
        Assert.assertEquals(consumerID, consumerId.get());
    }

    @Then("I will verify Email field will be optional to save with Channel is set to {string}")
    public void iWillVerifyEmailFieldWillBeOptionalToSaveWithChannelIsSetTo(String channel) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();
        waitFor(2);

        selectDropDown(crmContactRecordUIPage.contactChannelType, channel);

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");

        selectRandomDropDownOption(edit_cont_Rec.elecSignCheckBox);
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(2);

        assertTrue(contactRecord.email.getText().isEmpty());
        contactRecord.closeButton.click();

        waitForVisibility(contactRecord.initContact, 10);
        assertTrue(contactRecord.initContact.isDisplayed());
    }

    @Then("I will verify Phone field will be optional to save with Channel is set to {string}")
    public void i_will_verify_Phone_field_will_be_optional_to_save_with_Channel_is_set_to(String channel) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();
        waitFor(2);

        selectDropDown(activeContact.channelField, channel);

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");

        selectRandomDropDownOption(edit_cont_Rec.elecSignCheckBox);
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);

        assertTrue(contactRecord.phoneNumber.isDisplayed());
        assertTrue(contactRecord.phoneNumber.getText().isEmpty());
        contactRecord.closeButton.click();

        waitForVisibility(contactRecord.initContact, 10);
        assertTrue(contactRecord.initContact.isDisplayed());
    }

    @Then("I will save Phone field for {string} channel")
    public void i_will_save_Phone_field_for_channel(String channel) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();
        waitFor(2);

        selectDropDown(activeContact.channelField, channel);

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");

        selectRandomDropDownOption(edit_cont_Rec.elecSignCheckBox);
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);

        assertTrue(contactRecord.phoneNumber.isDisplayed());
        clearAndFillText(contactRecord.phoneNumber, getRandomNumber(10));
        phoneNumber.set(contactRecord.phoneNumber.getAttribute("value"));
        contactRecord.closeButton.click();

        waitForVisibility(contactRecord.initContact, 10);
        assertTrue(contactRecord.initContact.isDisplayed());
    }


    @Then("I clicking on first Contact Record")
    public void iClickingOnFirstContactRecord() {
        waitForVisibility(edit_cont_Rec.frstRecord, 13);
        edit_cont_Rec.frstRecord.click();
    }

    @Then("I will verify Email field will be optional for the Edit Contact screen")
    public void iWillVerifyEmailFieldWillBeOptionalForTheEditContactScreen() {
        selectRandomDropDownOption(edit_cont_Rec.tanslationService);
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, "Adding Contact Details");
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        scrollDownUsingActions(2);
        waitForVisibility(edit_cont_Rec.contactEditReasonEdit, 6);
        assertTrue(edit_cont_Rec.contactEditReasonEdit.isDisplayed());
    }

    @Then("I will verify Phone field will be optional for the Edit Contact screen")
    public void i_will_verify_Phone_field_will_be_optional_for_the_Edit_Contact_screen() {
        selectRandomDropDownOption(edit_cont_Rec.tanslationService);
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, "Correcting Contact Details");

        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        scrollDownUsingActions(2);
        waitForVisibility(edit_cont_Rec.contactEditReasonEdit, 6);
        assertTrue(edit_cont_Rec.contactEditReasonEdit.isDisplayed());
    }

    @Then("I will verify Phone field is displayed for saved contact record")
    public void i_will_verify_Phone_field_is_displayed_for_saved_contact_record() {
        assertEquals(edit_cont_Rec.phoneNumber.getText(), phoneNumber);
    }

    @Then("I will expand the contact record and will see a read only version of the Application ID field")
    public void iWillExpandTheContactRecordAndWillSeeAReadOnlyVersionOfTheApplicationIDField() {
        waitForVisibility(contactRecord.expandSearchResult, 10);
        contactRecord.expandSearchResult.click();
        assertTrue(contactRecord.getAppIdFromExpandetContactRecord(applicationIDValue.get()).isDisplayed(),
                "ApplicationId not visible on expanded Contact Record");
    }

    @When("I closed the current Contact Record and initiate a new Contact Record")
    public void i_closed_the_current_Contact_Record_and_initiate_a_new_Contact_Record() {
        waitFor(1);
        contactRecord.stopContact.click();
        waitFor(1);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);

        contactRecord.closeButton.click();
        waitFor(2);

        jsClick(contactRecord.initContact);
        waitFor(1);
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed(), "New Contact is not initiated");
    }

    @When("I select the latest created contact record")
    public void selectContactRecordForType() {
        waitForVisibility(contactHistory.types.get(1), 10);
        contactRecord.secondConId.click();
    }

    @When("I verify Application ID field will be visible and Required with Contact Reasons")
    public void IverifyApplicationiDfieldwillbevisibleandRequiredwithContactReasons(List<String> reasons) {
        waitForVisibility(activeContact.contactRes, 10);
        for (String reason : reasons) {
            if (reason.equalsIgnoreCase("Appeal")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(2);
                action.sendKeys(Keys.ESCAPE).build().perform();
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("New Application - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Renewal - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            } else if (reason.equalsIgnoreCase("Change Request")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                waitFor(2);
                selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
                waitFor(1);
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                waitFor(1);
                contactRecord.saveReasonButton.click();
                waitFor(2);
                assertTrue(isElementDisplayed(contactRecord.applicationId));
                contactRecord.saveEditContactRecordBtn.click();

                contactRecord.cancelButtonAfterSavingReason.click();
                waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                contactRecord.cancelWarningContinueButton.click();
                waitForVisibility(contactRecord.initContact, 5);
                contactRecord.initContact.click();
            }
        }
    }

    @Then("I click on Call Managment button without getting consumerId")
    public void iClickOnCallManagmentButtonwithoutgettingconsumerId() {
        waitForVisibility(activeContact.callManagment, 10);
        activeContact.callManagment.click();
    }

    @And("I verify error message {string} for ApplicationId")
    public void iVerifyErrorMessageforApplicationId(String message) {
        waitFor(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        waitFor(1);
        selectDropDown(activeContact.contactRes, "Appeal");
        waitFor(1);
        selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, "Escalated to Supervisor");
        action.sendKeys(Keys.ESCAPE).build().perform();
        contactRecord.reasonsComments.click();
        contactRecord.reasonsComments.sendKeys("Test ApllicationId");
        contactRecord.saveReasonButton.click();

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());

        contactRecord.applicationId.sendKeys(getRandomNumber(3));

        contactRecord.saveEditContactRecordBtn.click();
        assertTrue(contactRecord.appIdMustBEnineChars.getText().equalsIgnoreCase(message));
    }

    @Then("I verify Application ID field becomes hidden when unselect all selected application-related Contact Reasons")
    public void iVerifyApplicationIDFieldBecomesHiddenWhenUnselectAllSelectedApplicationRelatedContactReasons() {
        jsClick(crmContactRecordUIPage.expandreasonsComments);
        waitForClickablility(crmContactRecordUIPage.editIcon, 5);
        jsClick(crmContactRecordUIPage.editIcon);

        selectOptionFromMultiSelectDropDown(contactRecord.expandContactReasonDropDownUpdate, "Complaint");
        selectOptionFromMultiSelectDropDownNew(activeContact.contactActionEdit, "Referred to LDSS");
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(1);
        scrollDownUsingActions(3);
        jsClick(contactRecord.editReasonSaveButtonNew);

        waitFor(2);
        assertFalse(isElementDisplayed(contactRecord.applicationId));
    }

    @Then("I verify Application ID field will be NOT visible and Required with Contact Reasons")
    public void i_verify_Application_ID_field_will_be_NOT_visible_and_Required_with_Contact_Reasons(List<String> reasons) {
        waitForVisibility(activeContact.contactRes, 10);
        for (String reason : reasons) {
            if (reason.equalsIgnoreCase("Appeal")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("New Application - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Renewal - CVCC")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                scrollDown();
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
                selectDropDown(activeContact.contactRes, reason);
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));

            } else if (reason.equalsIgnoreCase("Change Request")) {
                selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
                selectDropDown(activeContact.contactRes, reason);
                selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                contactRecord.reasonsComments.click();
                contactRecord.reasonsComments.sendKeys("Test ApllicationId");
                contactRecord.saveReasonButton.click();

                Assert.assertFalse(isElementDisplayed(contactRecord.applicationId));
            }
        }
    }


    @Then("Verify for Business Unit CVCC only have certain Contact Reason options available for selection")
    public void verifyForBusinessUnitCVCCOnlyHaveCertainContactReasonOptionsAvailableForSelection(List<String> reasons) {
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText(), reasons.get(i) + " Not Found or Not in the order");
        }
    }

    @Then("Verify for Business Unit {string} only have certain Contact Reason options available for selection")
    public void verifyForBusinessUnitHaveCertainContactReasonOptionsAvailableForSelection(String dropdown, List<String> reasons) {
        for (String option : reasons) {
            switch (dropdown) {
                case "CVCC":
                    selectDropDown(activeContact.businessUnitDropdwn, dropdown);
                    selectDropDown(contactRecord.contactReason, option);
                    break;
                case "CVIU - DJJ":
                    selectDropDown(activeContact.businessUnitDropdwn, dropdown);
                    selectDropDown(contactRecord.contactReason, option);
                    break;
            }
        }
    }

    @Then("Verify for Business Unit {string} only have certain Contact Reason options available for selection on edit page")
    public void verifyForBusinessUnitHaveCertainContactReasonOptionsAvailableForSelection_on_edit_page(String dropdown, List<String> reasons) {
        for (String option : reasons) {
            switch (dropdown) {
                case "CVCC":
                    selectDropDown(contactRecord.contactReason, option);
                    break;
                case "CVIU - DJJ":
                    selectDropDown(contactRecord.contactReason, option);
                    break;
            }
        }
    }


    @Then("Verify for Business Unit CVIU only have certain Contact Reason options available for selection")
    public void verifyForBusinessUnitCVIUOnlyHaveCertainContactReasonOptionsAvailableForSelection(List<String> reasons) {
        waitFor(2);
        activeContact.contactReasons.get(0).click();
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DOC");
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText());
        }

        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DJJ");
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText());
        }

        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - Jails");
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText());
        }

        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - Other");
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText());
        }
    }

    @And("I save the contact with Business Unit {string} and Contact Reason option {string}")
    public void iSaveTheContactWithBusinessUnitAndContactReasonOption(String busUnit, String reason) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, busUnit);
        selectDropDown(contactRecord.contactReason, reason);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "business unit");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");

        selectDropDown(edit_cont_Rec.elecSignCheckBox, "Yes");
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @Then("Verify Business Unit read only on View\\/Edit Contact")
    public void verifyBusinessUnitReadOnlyOnViewEditContact() {
        assertTrue(contactRecord.businessUnitDropDwn.getAttribute("aria-disabled").contains("true"),
                "Business Unit not disabled in Contact Record view");
        contactRecord.editIcon.click();
        assertTrue(contactRecord.businessUnitDropDwn.getAttribute("aria-disabled").contains("true"),
                "Business Unit not disabled in Contact Record view");
    }

    @Then("I verify Electronic Signature Captured checkbox not  displayed on Unidentified Contact")
    public void I_verify_Electronic_Signature_CapturedcheckboxnotdisplayedonUnidentifiedContact() {
        waitFor(3);
        Assert.assertFalse(isElementDisplayed(edit_cont_Rec.elecSignCheckBox));
    }

    @Then("Verify Electronic Signature Captured in Edit History and Reason for Edit")
    public void VerifyElectronicSignatureCapturedinEditHistoryandReasonforEdit() {
        assertTrue(contactHistory.editedOnDate.isDisplayed());
        assertTrue(contactHistory.editedByUser.getText().contains("Service TesterThree"));
        assertEquals(contactHistory.frsReasonForEditColumn.getText(), "Correcting Contact Details");
        assertEquals(contactHistory.frsFieldLabel.getText(), "Electronic Signature Captured");
        assertEquals(contactHistory.frsPreviousVal.getText(), "Yes");
        assertEquals(contactHistory.frsUpdatedVal.getText(), "No");
    }

    @And("Verify error message that {string} with my incorrect Reason for Edit")
    public void VerifyerrormessagethatcorrespondswithmyincorrectReasonforEdit(String reason) {
        waitFor(2);
        jsClick(contactRecord.editContactButton);
        selectDropDown(edit_cont_Rec.elecSignCheckBox, "No");
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        scrollDownUsingActions(3);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);
        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please add Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Correcting Contact Details to save edits to details").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("No Contact Details have been Added/edited").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid reason for edit for this Contact Record").isDisplayed());
        }
    }

    @And("I searching for contact with electronic signature")
    public void iSearchingForForContactWithElectronicSig() {
        edit_cont_Rec.advancedSearch.click();
        waitFor(1);
        clearAndFillText(edit_cont_Rec.consumerIdSearch, consumerId.get());
        clearAndFillText(edit_cont_Rec.phoneNumber, phoneNumber.get());
        assertTrue(edit_cont_Rec.elecSignCheckBox.isDisplayed());
        contactRecord.searchButton.click();
        waitForVisibility(edit_cont_Rec.frstRecord, 13);
    }


    @Then("Select by previous date on Manual Contact Record Search page")
    public void selectByPreviousDateOnManualContactRecordSearchPage() {
        manualContactRecordSearchPage.manualSearchDate.sendKeys("07/02/2021");
        selectDropDown(manualContactRecordSearchPage.createdDataSymbol, ">");
        manualContactRecordSearchPage.searchButton.click();
    }

    @Then("I verify {string} dropdown has following options")
    public void i_verify_dropdown_has_following_options(String dropdown, List<String> options) {
        waitFor(3);
        for (String opt : options) {
            if (opt.equalsIgnoreCase("CVCC")) {
                selectDropDown(manualContactRecordSearchPage.businessUnitDropDown, opt);
            } else if (opt.equalsIgnoreCase("CVIU")) {
                selectDropDown(manualContactRecordSearchPage.businessUnitDropDown, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @And("Verify language {string}  captured in the Translation Service")
    public void verifyLanguageCapturedInTheTranslationService(String lan) {
        assertTrue(contactRecord.TranslationServicelanguage(lan).isDisplayed());
    }

    @Then("I will see the on-field error {string}")
    public void iWillSeeTheOnFieldErrorPROGRAMIsRequiredAndCannotBeLeftBlank(String message) {
        assertEquals(contactRecord.programError.getText(), message, "No Error message for Program");
    }

    @And("Verify Program Dropdown Does Not Have a Null or Blank Option")
    public void verifyProgramDropdownDoesNotHaveANullBlankOption() {
        contactRecord.lstSelectProgram.click();
        waitForVisibility(contactRecord.lstSelectProgramValues.get(1), 10);
        for (int i = 0; i < contactRecord.lstSelectProgramValues.size(); i++) {
            assertTrue(contactRecord.lstSelectProgramValues.get(i).getText() != null);
            assertTrue(!contactRecord.lstSelectProgramValues.get(i).getText().isEmpty());
        }
    }

    @And("I have altered the existing value in the Program field with Reason for Edit {string}")
    public void iHaveAlteredTheExistingValueInTheProgramFieldWithReasonForEdit(String val) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
        waitFor(1);
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program A");
        waitFor(1);
        jsClick(contactHistory.editSave);
        waitFor(5);
        assertTrue(manualContactRecordSearchPage.editButton.isDisplayed());
    }

    @And("I have added a value when the Program field was null with Reason for Edit {string}")
    public void iHaveAddedAValueWhenTheProgramFieldWasNullWithReasonForEdit(String val) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, val);
        waitFor(1);
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program A");
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program B");
        waitFor(1);
        jsClick(contactHistory.editSave);
        waitFor(5);
        assertTrue(manualContactRecordSearchPage.editButton.isDisplayed());
    }

    @Then("Verify changes should be reflected in the Edit History, displaying the previous value and the new value")
    public void verifyChangesShouldBeReflectedInTheEditHistoryDisplayingThePreviousValueAndTheNewValue() {
        jsClick(contactHistory.editHistoryTab);
        waitForVisibility(contactHistory.reasonForEd, 10);
        assertEquals(contactHistory.reasonForEd.getText(), "Adding Contact Details", "Reason for Edit is Incorrect");
        assertEquals(contactHistory.fieldLabelEditHistory.getText(), "Program", "Reason for Edit is Incorrect");
        assertTrue(contactHistory.previousValueEditHistory.getText().isEmpty());
        assertEquals(contactHistory.updatedValueEditHistory.getText(), "Program A, Program B");
    }

    @Then("I will see the error message for Program Field {string} with incorrect Reason for Edit")
    public void iWillSeeTheErrorMessageForProgramFieldWithIncorrectReasonForEdit(String reason) {
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program A");
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);
        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please add Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Correcting Details to save edits to details").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid reason for edit for this Contact Record").isDisplayed());
        }
    }

    @When("I save the contact without Application ID with BusinessUnit Drop Down {string}")
    public void IsavethecontactwithoutApplicationID(String bus) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, bus);
        selectDropDown(contactRecord.contactReason, "Reported Fraud");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Provider Fraud Information");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I verify Application ID field will be visible with BusinessUnit Drop Down {string} and Contact Reasons on Edit page")
    public void IverifyApplicationiDfieldwillbevisibleandRequiredwithContactReasonsOnEdit(String bus, List<String> reasons) {
        if (bus.equals("CVCC")) {
            for (String reason : reasons) {
                if (reason.equalsIgnoreCase("Appeal")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();

                } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVCC")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("New Application - CVCC")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();

                } else if (reason.equalsIgnoreCase("Renewal - CVCC")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Change Request")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                }
            }
        } else if (bus.equals("CVIU - DOC")) {
            for (String reason : reasons) {
                if (reason.equalsIgnoreCase("Appeal")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                }
            }
        } else if (bus.equals("CVIU - DJJ")) {
            for (String reason : reasons) {
                if (reason.equalsIgnoreCase("Appeal")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                }
            }
        } else if (bus.equals("CVIU - Jails")) {
            for (String reason : reasons) {
                if (reason.equalsIgnoreCase("Appeal")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                }
            }

        } else if (bus.equals("CVIU - Other")) {
            for (String reason : reasons) {
                if (reason.equalsIgnoreCase("Appeal")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("New Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Renewal - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Pre-Release Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Re-Entry Application - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Expedited Applications - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                } else if (reason.equalsIgnoreCase("Change Request - CVIU")) {
                    waitForVisibility(manualContactRecordSearchPage.editButton, 5);
                    manualContactRecordSearchPage.editButton.click();

                    selectDropDown(activeContact.contactRes, reason);
                    selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
                    contactRecord.otherReasonComments.click();
                    contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
                    contactRecord.saveReasonButton.click();

                    assertTrue(contactRecord.applicationId.isDisplayed());

                    jsClick(contactHistory.editSave);
                    assertTrue(contactHistory.applicationIdError.getText().contains("APPLICATION ID is Required"));

                    contactRecord.cancelButtonAfterSavingReason.click();
                    waitForVisibility(contactRecord.cancelWarningContinueButton, 5);
                    contactRecord.cancelWarningContinueButton.click();
                }
            }
        } else {
            throw new CucumberException("Business DropDown not found");
        }
    }

    @When("I attempt to save the Contact without providing a minimum of 9 characters of text in the Application ID field")
    public void iAttemptToSaveTheContactWithoutProvidingAMinimumOfCharactersOfTextInTheApplicationIDField() {
        selectDropDown(activeContact.contactRes, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
        contactRecord.otherReasonComments.click();
        contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
        contactRecord.saveReasonButton.click();

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());

        contactRecord.applicationId.sendKeys(getRandomNumber(3));

        contactRecord.saveEditContactRecordBtn.click();
    }

    @When("Verify will see error message {string}")
    public void verifyWillSeeErrorMessage(String message) {
        assertTrue(contactRecord.appIdMustBEnineChars.getText().equalsIgnoreCase(message));
    }

    @Then("I delete the contents of the Application ID and attempt to Save without providing a minimum of 9 characters")
    public void iDeleteTheContentsOfTheApplicationIDAndAttemptToSaveWithoutProvidingAMinimumOfCharacters() {
        clearAndFillText(contactRecord.applicationId, getRandomNumber(3));
        contactRecord.saveEditContactRecordBtn.click();
    }

    @Then("Verify I unselect all selected application-related Contact Reasons Application ID field becomes hidden")
    public void verifyIUnselectAllSelectedApplicationRelatedContactReasonsApplicationIDFieldBecomesHidden() {
        jsClick(crmContactRecordUIPage.expandreasonsComments);
        waitForClickablility(crmContactRecordUIPage.editIcon, 5);
        jsClick(crmContactRecordUIPage.editIcon);

        selectOptionFromMultiSelectDropDown(contactRecord.expandContactReasonDropDownUpdate, "Complaint");
        selectOptionFromMultiSelectDropDown(contactRecord.expandContactActionDropDownUpdate, "Referred to LDSS");
        waitFor(1);

        clearAndFillText(contactRecord.editCommentArea, "Edit");
        scrollDownUsingActions(3);
        jsClick(contactRecord.editReasonSaveButton);

        waitFor(2);
        assertFalse(isElementDisplayed(contactRecord.applicationId));
    }

    @Then("I edit the Application ID field and verify edit is visible on Edit History")
    public void i_edit_the_Application_ID_field_and_verify_edit_is_visible_on_Edit_History() {
        waitFor(4);

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());


        contactRecord.applicationId.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        waitFor(3);
        String applicationId = getRandomNumber(9);
        contactRecord.applicationId.sendKeys(applicationId);
        waitFor(3);

        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, "Correcting Contact Details");

        jsClick(contactHistory.editSave);
        waitFor(6);


        jsClick(contactHistory.editHistoryTab);
        waitFor(1);

        assertTrue(editContactRecordPage.applicationIDField.isDisplayed());
        assertEquals(editContactRecordPage.updatedAppIdField(applicationId.substring(0, 4)).getText(), applicationId);
        assertTrue(editContactRecordPage.reasonForEditField("Correcting Contact Details").isDisplayed());
    }

    @Then("I add the Application ID field and verify edit is visible on Edit History")
    public void i_add_the_Application_ID_field_and_verify_edit_is_visible_on_Edit_History() {
        selectDropDown(activeContact.contactRes, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
        contactRecord.otherReasonComments.click();
        contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
        contactRecord.saveReasonButton.click();
        waitFor(4);

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());

        String applicationId = getRandomNumber(9);
        contactRecord.applicationId.sendKeys(applicationId);
        waitFor(4);

        selectDropDown(edit_cont_Rec.elecSignCheckBox, "Yes");

        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, "Adding Contact Reason/Action");
        manualContactRecordSearchPage.reasonForEdit.click();
        editContactRecordPage.reasonForEditField("Adding Contact Details").click();

        contactRecord.saveEditContactRecordBtn.click();

        jsClick(contactHistory.editHistoryTab);
        waitFor(1);

        assertTrue(editContactRecordPage.applicationIDField.isDisplayed());
        assertEquals(editContactRecordPage.updatedAppIdField(applicationId.substring(0, 3)).getText(), applicationId);
        assertTrue(editContactRecordPage.reasonForEditField("Adding Contact Details").isDisplayed());
    }

    @Then("I edit the Application ID field and verify Error Message when trying to save Edit with {string}")
    public void i_edit_the_Application_ID_field_and_verify_Error_Message_when_trying_to_save_Edit_with(String reason) {

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());

        contactRecord.applicationId.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        waitFor(3);
        String applicationId = getRandomNumber(9);
        contactRecord.applicationId.sendKeys(applicationId);
        waitFor(3);

        //selectDropDown(contactRecord.editReasonListContactRecord, reason);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, reason);
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);

        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Correcting Details to save edits to details").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid").isDisplayed());
        }
    }

    @Then("I add the Application ID field and verify Error Message when trying to save Edit with {string}")
    public void i_add_the_Application_ID_field_and_verify_Error_Message_when_trying_to_save_Edit_with(String reason) {
        selectDropDown(activeContact.contactRes, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Escalated to Supervisor");
        contactRecord.otherReasonComments.click();
        contactRecord.otherReasonComments.sendKeys("Test ApllicationId");
        contactRecord.saveReasonButton.click();

        waitForVisibility(contactRecord.applicationId, 6);
        assertTrue(contactRecord.applicationId.isDisplayed());

        String applicationId = getRandomNumber(9);
        contactRecord.applicationId.sendKeys(applicationId);


        selectDropDown(contactRecord.editReasonListContactRecord, "Correcting Contact Reason/Action");
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);

        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Correcting Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Adding Contact Details to save adding information to details").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid").isDisplayed());
        }
    }

    @Then("I save deletion by choosing {string} from Edit Reason DropDown")
    public void i_save_deletion_by_choosing_from_Edit_Reason_DropDown(String reason) {
        waitFor(1);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, reason);
        waitFor(2);
        jsClick(contactHistory.editSave);
        waitFor(5);
        assertTrue(manualContactRecordSearchPage.editButton.isDisplayed());
    }

    @Then("I verify reason\\/action deletion is reflected on Edit History")
    public void i_verify_reason_action_deletion_is_reflected_on_Edit_History() {
        jsClick(contactHistory.editHistoryTab);
        waitForVisibility(contactHistory.reasonForEd, 10);
        assertEquals(contactHistory.reasonForEd.getText(), "Correcting Contact Reason/Action", "Reason for Edit is Incorrect");
        assertEquals(contactHistory.fieldLabelEditHistory.getText(), "Contact Reason/Actions", "Reason for Edit is Incorrect");
        assertEquals(contactHistory.previousValueEditHistory.getText(), "Complaint - Account Access / Escalated-3,", "Previous Value is incorrect");
        assertTrue(contactHistory.updatedValueEditHistory.getText().equals("-- --"), "Updated value is NOT empty");
    }

    @Then("I will see the error message for Reason&Action deletion with {string} with incorrect Reason for Edit")
    public void i_will_see_the_error_message_for_Reason_Action_deletion_with_with_incorrect_Reason_for_Edit(String reason) {
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);
        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Correcting Details to save edits to details").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid").isDisplayed());
        }
    }

    @When("I save the contact with Reason {string} and Action {string}")
    public void iSaveTheContactWithReasonAndAction(String reason, String action) {
        scrollUpUsingActions(3);
        selectDropDown(contactRecord.contactReason, reason);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, action);
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");
        selectRandomDropDownOption(activeContact.inboundCallQueue);

        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @Then("Verify ClaimId not visible on Active Contact")
    public void verifyClaimIdNotVisibleOnActiveContact() {
        Assert.assertFalse(isElementDisplayed(edit_cont_Rec.claimIDField));
    }


    @When("I add New Consumer to the record with address {string}")
    public void i_add_New_Consumer_to_the_record_with_address(String addressType) {
        System.out.println(newConsumer.get().get("name").toString() + " " + newConsumer.get().get("surname").toString());
        firstName.set(newConsumer.get().get("name").toString());
        lastName.set(newConsumer.get().get("surname").toString());
        clearAndFillText(contactRecord.firstName, (newConsumer.get().get("name").toString()));
        clearAndFillText(contactRecord.lastName, (newConsumer.get().get("surname").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        scrollUp();
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, addressType);
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(1);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
        waitFor(1);
        createConsumer.createConsumerButton.click();
        try {
            jsClick(createConsumer.warningMessageContinueButton);
        } catch (Exception e) {
            savedConsumerID.set(activeContact.consumerID.getText());
            System.out.println(savedConsumerID.get());
        }
    }

    @Then("I verify when attend to Save Phone field with blank value error {string}")
    public void iVerifyWhenAttendToSavePhoneFieldWithBlankValueError(String error) {
        clearAndFillText(contactRecord.unidentifiedContactRecordPhoneInput, "");
        waitFor(1);
        jsClick(contactHistory.editSave);
        waitFor(6);
        assertEquals(contactRecord.phoneError.getText(), error, "No Error message for Phone");
    }

    @Then("I verify when I saving Phone field less than 10 chars getting error {string}")
    public void iVerifyWhenISavingPhoneFieldLessThanCharsGettingError(String error) {
        clearAndFillText(contactRecord.unidentifiedContactRecordPhoneInput, "15");
        contactRecord.applicationId.click();
        waitFor(1);
        jsClick(contactHistory.editSave);
        waitFor(6);
        assertEquals(contactRecord.phoneError.getText(), error, "No Error message for Phone");
    }

    @Then("I will see the error message for Phone Field {string} with incorrect Reason for Edit")
    public void iWillSeeTheErrorMessageForPhoneFieldWithIncorrectReasonForEdit(String reason) {
        waitFor(2);
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, "Program A");
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        waitFor(2);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);
        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("Please select Correcting Details to save edits to details").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid").isDisplayed());
        }
    }

    @Then("I added valid phone number")
    public void iAddedValidPhoneNumber() {
        clearAndFillText(contactRecord.unidentifiedContactRecordPhoneInput, getRandomNumber(10));
    }

    @Then("Verify changes should be reflected in the Edit History for Multiple Edits")
    public void VerifychangesshouldbereflectedintheEditHistoryforMultipleEdits() {
        jsClick(contactHistory.editHistoryTab);
        waitForVisibility(contactHistory.reasonForEd, 10);
        assertEquals(contactHistory.gethisReasonsList.get(0).getText(), "Correcting Contact Details", "Reason for Edit is Incorrect");
        assertEquals(contactHistory.gethisReasonsList.get(1).getText(), "Adding Contact Details", "Reason for Edit is Incorrect");
    }

    @Then("I verify there is NO cancel button")
    public void i_verify_there_is_NO_cancel_button() {
        assertFalse(contactRecord.cancelButtonAfterSavingReason.isDisplayed());
    }

    @Then("I verify cancel button is displayed")
    public void i_verify_cancel_button_is_displayed() {
        assertTrue(contactRecord.cancelButtonAfterSavingReason.isDisplayed());
    }

    @When("I verify {string} column exists for contact record search result")
    public void i_verify_column_exists_for_contact_record_search_result(String businessUnitColumn) {
        assertTrue(manualContactRecordSearchPage.contactRecordSearchResultColumn(businessUnitColumn).isDisplayed());

    }


    @Then("I click on already selected Business Unit dropdown in order to verify warning message in edit page")
    public void iClickOnBusinessUnitDropdownInOrderToVerifyWarningMessageInEditPage() {
        assertTrue(contactRecord.ariaDisabled.isDisplayed());
        contactRecord.businessUnitDropDwn.click();
        assertTrue(contactRecord.allContactReasonsMustBeDeletedMessage.isDisplayed(), "\'All Contact Reasons must be deleted...\" warning message is not displayed");
    }


    @Then("I saved Reason For Edit dropdown with {string} value")
    public void i_saved_Reason_For_Edit_Dropdown_With_Provided_Value(String editReasonValue) {
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.reasonForEdit, editReasonValue);
        jsClick(contactHistory.editSave);
        assertTrue(manualContactRecordSearchPage.editButton.isDisplayed());
    }

    @Then("I will see the error message for editing and saving Reason&Action with {string} incorrect Reason for Edit")
    public void i_will_see_the_error_message_for_editing_and_saving_Reason_Action__with_with_incorrect_Reason_for_Edit(String reason) {
        waitFor(2);
        selectDropDown(contactRecord.editReasonListContactRecord, reason);
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        scrollDownUsingActions(3);
        jsClick(contactRecord.saveContactRecordEditButton);
        waitForVisibility(edit_cont_Rec.errorMessagePop, 5);
        if (reason.equals("Adding Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please add Comment").isDisplayed());
        } else if (reason.equals("Adding Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("No Contact Details have been Added/edited").isDisplayed());
        } else if (reason.equals("Adding Contact Reason/Action")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Reason/Action").isDisplayed());
        } else if (reason.equals("Correcting Additional Comment")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit Comment").isDisplayed());
        } else if (reason.equals("Correcting Case/Consumer Link")) {
            assertTrue(edit_cont_Rec.errorMessage("Please edit the Case/Consumer link").isDisplayed());
        } else if (reason.equals("Correcting Contact Details")) {
            assertTrue(edit_cont_Rec.errorMessage("No Contact Details have been Added/edited").isDisplayed());
        } else if (reason.equals("Correcting Disposition")) {
            assertTrue(edit_cont_Rec.errorMessage("Please save Disposition/Reason For Edit").isDisplayed());
        } else if (reason.equals("Correcting Third Party Information")) {
            assertTrue(edit_cont_Rec.errorMessage("Correcting Third Party Information is not a valid reason for edit for this Contact Record").isDisplayed());
        }
    }

    @Then("I select multiple values from Reason For Edit field on the Contact Details page")
    public void selectMultipleValuesFromReasonForEditField(List<String> options) {
        for (String option : options) {
            reasonForEditUpdatedValue.set(option + "/");
            selectOptionFromMultiSelectDropDown(contactRecord.contactRecordReasonForEdit, option);
        }
        contactRecord.saveReasonForEditButton.click();
        reasonForEditUpdatedValue.set(reasonForEditUpdatedValue.get().substring(0, reasonForEditUpdatedValue.get().length() - 1));
    }

    @Then("I Verify that corresponded Business Unit value is displayed after selecting inbound call queue value")
    public void Verify_that_corresponded_Business_Unit_value_is_displayed_after_selecting_inbound_call_queue_value() {
        waitFor(2);
        System.out.println(activeContact.businessUnitCVCCvalue.getText());
        assertEquals(activeContact.businessUnitCVCCvalue.getText(), "CVCC");
    }


    @And("I Verify that corresponded Business Unit value {string} is displayed after selecting inbound call queue value")
    public void i_click_on_the_contact_type(String unitValue) {
        waitFor(2);
        System.out.println(activeContact.businessUnitCVCCvalue.getText());
        if (unitValue.equalsIgnoreCase("CVCC")) {
            assertEquals(activeContact.businessUnitCVCCvalue.getText(), unitValue);
            assertTrue(activeContact.businessUnitCVCCvalue.isDisplayed());
        } else if (unitValue.contains("CVIU")) {
            assertTrue(activeContact.businessUnitCVIUvalue.getText().contains("CVIU"));
        }
    }


    @When("I verify Edit Button is not displayed")
    public void i_verify_Edit_Button_is_not_displayed() {
        waitFor(3);
        assertFalse(isElementDisplayed(editContactRecordPage.editButton));
    }

    @When("I save the contact record for IN_EB")
    public void i_save_the_contact_record_for_IN_EB() {
        scrollUpUsingActions(3);
        selectDropDown(contactRecord.contactReason, "Just Cause");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Just Cause Form");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, "KIDS");

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");

        sendKeyToTextField(contactRecord.additionalComments, "Call summary");
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(2);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I searching for contact with phone number and consumer id")
    public void i_searching_for_contact_with_phone_number_and_consumer_id() {
        edit_cont_Rec.advancedSearch.click();
        waitFor(1);
        clearAndFillText(edit_cont_Rec.consumerIdSearch, consumerId.get());
        clearAndFillText(edit_cont_Rec.phoneNumber, phoneNumber.get());
        contactRecord.searchButton.click();
        waitForVisibility(edit_cont_Rec.frstRecord, 13);
    }

    @Then("I search by EmployeeID {string} on manual contact record search page")
    public void i_search_by_EmployeeID_on_manual_contact_record_search_page(String employeeID) {
        manualContactRecordSearchPage.advanceSearchEmployeeID.sendKeys(employeeID);
        manualContactRecordSearchPage.searchButton.click();
        waitFor(5);
    }

    @When("I select business unit {string} and contact reason {string}")
    public void select_business_unit_and_reason(String businessUnit, String contactReason) {
        selectDropDown(activeContact.businessUnitDropdwn, businessUnit);
        selectDropDown(contactRecord.contactReason, contactReason);
    }

    @When("I verify contact action multi select drop down values")
    public void verify_contact_action_values(List<String> actions) {
        contactRecord.contactAction.click();
        waitFor(2);
        List<WebElement> actualActions = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'contactAction')]//ul/li"));
        assertEquals(actions.size(), actualActions.size(), "Mismatch in the Actual and Expected List size");
        for (int i = 0; i < actions.size(); i++) {
            assertTrue(actions.contains(actualActions.get(i).getText()), "Drop down values are incorrect. Expected List did not contain actual Value: " + actualActions.get(i).getText());
        }
    }

    @When("I verify additional contact action  drop down values")
    public void verify_additional_contact_action_values(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, option);
        }
    }

    @When("I click on the previous created contact record Id")
    public void i_select_the_previous_contact_record_with_type() {
        waitFor(2);
        scrollUpUsingActions(2);
        for (int i = 0; i < contactHistory.contactIDs.size(); i++) {
            if (contactHistory.contactIDs.get(i).getText().equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("contactRecord"))) {
                jsClick(contactHistory.contactIDs.get(i));
                break;
            }
        }
    }

    @When("I created contact record with reason and action {string}")
    public void I_created_contact_record_with_reason_and_contact(String bus) {
        scrollUpUsingActions(3);
        click(contactRecord.reasonsButton);
        selectDropDown(contactRecord.contactReason, "Complaint");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Referral - Anthem");
        waitFor(1);
        click(contactRecord.saveReasonButton);
        selectDropDown(contactRecord.inboundCallQueueDropDownSelection, "CARE_ENG_Q");
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HIP");
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I save the contact record for IN_EB with random order for {string} and {string}")
    public void i_save_the_contact_record_for_IN_EB_with_random_for_Inbound(String contactType, String callQueueDropdown) {

        scrollUpUsingActions(3);
        selectDropDown(crmContactRecordUIPage.contactType, contactType);
        if (contactType.equalsIgnoreCase("Inbound")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, callQueueDropdown);
        } else if (contactType.equalsIgnoreCase("Outbound")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callCampaignDropDown, callQueueDropdown);
        }

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());

        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");
        selectDropDown(contactRecord.contactReason, "Just Cause");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Just Cause Form");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        sendKeyToTextField(contactRecord.additionalComments, "Call summary");
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(2);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);

        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.saveButton.click();
        waitFor(1);
    }

    @When("I search contact record with phone number and click on the first record")
    public void i_search_contact_record_with_phone_number_and_click_on_first_record() {
        edit_cont_Rec.advancedSearch.click();
        waitFor(1);
        clearAndFillText(edit_cont_Rec.phoneNumber, phoneNumber.get());
        contactRecord.searchButton.click();
        waitForVisibility(edit_cont_Rec.frstRecord, 13);
        edit_cont_Rec.frstRecord.click();
    }

    @When("I verify that comment is not visible under reason on view contact page")
    public void i_verify_that_comments_is_not_visible_on_view_contact_page() {
        waitForVisibility(crmContactRecordUIPage.reasonArrowOnEditPage, 6);
        jsClick(crmContactRecordUIPage.reasonArrowOnEditPage);
        assertFalse(isElementDisplayed(contactRecord.reasonsComments));
    }

    @When("I verify that comment is not visible on edit contact page")
    public void i_verify_that_comments_is_not_visible_on_edit_contact_contact_page() {
        waitFor(2);
        waitForVisibility(contactRecord.editContact, 3);
        jsClick(contactRecord.editContact);
        assertFalse(isElementDisplayed(contactRecord.reasonsComments));

    }

    @When("I verify that comment is not visible on active contact")
    public void i_verify_that_comment_is_not_visible_on_active_contact() {
        waitFor(3);
        assertFalse(isElementDisplayed(contactRecord.reasonsComments));
    }

    @When("I save the contact record for IN_EB with random order for Outband")
    public void i_save_the_contact_record_for_IN_EB_with_random_for_Outband() {
        scrollUpUsingActions(3);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callCampaignDropDown, "Outbound Calls - English");

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");
        selectDropDown(contactRecord.contactReason, "Just Cause");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Just Cause Form");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        sendKeyToTextField(contactRecord.additionalComments, "Call summary");
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(2);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I create new unidentified contact record for {string} and {string}")
    public void createNewUnidentifiedContactRecordForContactRecordEdit(String contactType, String callQueueDropdown) {
        waitFor(2);
        contactRecord.selectUnidentifiedContactRecordType.click();
        scrollUpUsingActions(3);
        selectDropDown(contactRecord.preferredLanguage, "English");
        selectDropDown(contactRecord.contactType, contactType);
        if (contactType.equalsIgnoreCase("Inbound")) {
            selectOptionFromMultiSelectDropDown(contactRecord.inboundCallQueueDropDownSelection, callQueueDropdown);
        } else if (contactType.equalsIgnoreCase("Outbound")) {
            selectOptionFromMultiSelectDropDown(contactRecord.callCampaignDropDown, callQueueDropdown);
        }
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");
        selectDropDown(contactRecord.contactReason, "Just Cause");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Just Cause Form");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        sendKeyToTextField(contactRecord.additionalComments, "Call summary");
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(2);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        contactRecord.stopContact.click();
        waitFor(1);
        click(contactRecord.saveButton);
    }

    @When("I navigate to unidentified page")
    public void navigate_to_unidentified_page() {
        waitFor(2);
        contactRecord.selectUnidentifiedContactRecordType.click();
    }

    @When("I save the contact record third party IN_EB with random order for {string} and {string}")
    public void i_save_the_contact_record_third_party_IN_EB_with_random_for_Inbound(String contactType, String callQueueDropdown) {

        scrollUpUsingActions(3);
        selectDropDown(crmContactRecordUIPage.contactType, contactType);
        if (contactType.equalsIgnoreCase("Inbound")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.inboundCallQueueDropDown, callQueueDropdown);
        } else if (contactType.equalsIgnoreCase("Outbound")) {
            selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.callCampaignDropDown, callQueueDropdown);
        }

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());

        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "HCC");
        selectDropDown(contactRecord.contactReason, "Just Cause");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Just Cause Form");
        waitFor(1);
        contactRecord.saveReasonButton.click();

        sendKeyToTextField(contactRecord.additionalComments, "Call summary");
        crmContactRecordUIPage.saveCallSummaryButton.click();
        waitFor(2);

        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(2);
        scrollUp();

        waitFor(1);
        scrollUpUsingActions(2);
        waitFor(2);
        clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, "araz");
        clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, "ismayilov");
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, "MAX");
        selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, "Media");
        selectDropDown(thirdPartyDetails.cicPreferedLanguage, "English");
        waitFor(2);
        scrollDown();
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.saveButton.click();
        waitFor(1);
    }

    @Then("I link the contact to an existing Case with consumer id for IN-EB")
    public void iLinkTheContactToAnExistingCaseWithConsumerIdFORi() {
        click(contactRecord.expandFistConsumer);
        waitFor(2);
        click(contactRecord.lstFirstConsumerNameRadioButton);
        contactRecord.unableToAuthenticate.click();
        waitFor(2);
        contactRecord.linkRecordButton.click();
    }

    @And("I verify contact action dropdown values")
    public void i_verify_contact_action_dropdown_values_for_associated_reason(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(activeContact.contactAct, option);
        }
    }

    @When("I save the contact record with business unit {string}")
    public void iSaveTheContact_record_with_associated_business_unit(String busUnit) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, busUnit);
        selectDropDown(contactRecord.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Appeals Summary");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");

        selectDropDown(edit_cont_Rec.elecSignCheckBox, "Yes");
        contactRecord.applicationId.click();
        waitFor(2);
        contactRecord.applicationId.sendKeys(getRandomString(9));
        applicationIDValue.set(contactRecord.applicationId.getAttribute("value"));
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @Then("I validate systematic program selection based on inbound call queue")
    public void i_validate_systematic_program_selection(Map<String, String> inbCallQueue) { //AC 1.0, 1.1
        selectDropDown(crmContactRecordUIPage.callQueueType, "UAT_CARE_ENG_Q"); //AC 2.0
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "HHW");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "HIP");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "Traditional Medicaid");
        assertTrue(crmContactRecordUIPage.programTypes.getAttribute("value").equals("HCC,HHW,HIP,Traditional Medicaid"));
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "HHW");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "HIP");
        assertTrue(crmContactRecordUIPage.programTypes.getAttribute("value").equals("HCC,Traditional Medicaid")); //AC 3.0
        crmContactRecordUIPage.lstSelectProgram.click();
        crmContactRecordUIPage.lstSelectProgramValues.get(0).sendKeys(Keys.ESCAPE);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage.lstSelectProgram, "Traditional Medicaid"); //AC 4
        selectDropDown(crmContactRecordUIPage.callQueueType, "UAT_HIP_ENG_Q");
        assertTrue(crmContactRecordUIPage.programTypes.getAttribute("value").equals("HCC,HIP"));
        selectDropDown(crmContactRecordUIPage.callQueueType, "UAT_HOOS_ENG_Q"); //AC 4.1
        emptyProgramDropdown();
        selectDropDown(crmContactRecordUIPage.callQueueType, "UAT_INS_ENG_Q");
        assertTrue(crmContactRecordUIPage.programTypes.getAttribute("value").equals("HHW"));
        for (Map.Entry<String, String> entry : inbCallQueue.entrySet()) {
            emptyProgramDropdown();
            selectDropDown(crmContactRecordUIPage.callQueueType, entry.getKey());
            assertTrue(crmContactRecordUIPage.programTypes.getAttribute("value").equals(entry.getValue()));
        }
    }

    private void emptyProgramDropdown() {
        crmContactRecordUIPage.lstSelectProgram.click();
        for (int i = 0; i < crmContactRecordUIPage.lstSelectProgramValues.size(); i++) {
            try {
                if (crmContactRecordUIPage.lstSelectProgramValues.get(i).getAttribute("aria-selected").equals("true")) {
                    if (!crmContactRecordUIPage.lstSelectProgramValues.get(i).getAttribute("aria-disabled").equals("true"))
                        crmContactRecordUIPage.lstSelectProgramValues.get(i).click();
                }
            } catch (NullPointerException e) {
                //keep looping
            }
        }
        crmContactRecordUIPage.lstSelectProgramValues.get(0).sendKeys(Keys.ESCAPE);
    }

    @And("I verify business unit and associated contact reason")
    public void i_verify_contact_action_dropdown_values_and_associated_reason(Map<String, String> options) {
        for (Map.Entry<String, String> entry : options.entrySet()) {
            selectDropDown(activeContact.businessUnitDropdwn, entry.getKey());
            selectDropDown(contactRecord.contactReason, entry.getValue());
        }
    }

    @Then("Verify for Business Unit {string}, contact reasons {string} and contact actions have the respective dropdowns")
    public void verifyForBusinessUnit_contact_reason_and_contact_actions_have_respective_dropdowns(String busUnit, String contactReason, List<String> actions) {
        for (String option : actions) {
            if (busUnit.equalsIgnoreCase("CVCC") || busUnit.contains("CVIU")) {
                selectDropDown(activeContact.businessUnitDropdwn, busUnit);
                selectDropDown(activeContact.contactRes, contactReason);
                selectOptionFromMultiSelectDropDown(activeContact.contactAct, option);
            }
        }
    }

    @Then("I select Business Unit {string} and contact reasons {string}")
    public void select_business_unit_and_contact_reason(String busUnit, String contactReason) {
        action.sendKeys(Keys.ESCAPE).build().perform();
        selectDropDown(activeContact.businessUnitDropdwn, busUnit);
        selectDropDown(activeContact.contactRes, contactReason);
    }

    @When("I navigate to business unit {string}")
    public void i_navigate_on_task_type(String businessUnit) {
        action.sendKeys(Keys.ESCAPE).build().perform();
        selectDropDown(activeContact.businessUnitDropdwn, businessUnit);
    }

    @When("I navigate to Contact Reason {string}")
    public void i_navigate_to_contact_reason(String contactReason) {
        action.sendKeys(Keys.ESCAPE).build().perform();
        selectDropDown(activeContact.contactRes, contactReason);
    }

    @When("I navigate to Contact Reason with Text {string}")
    public void i_navigate_to_contact_reason_with_text(String contactReason) {
        action.sendKeys(Keys.ESCAPE).build().perform();
        selectDropDownWithText(activeContact.contactRes, contactReason);
    }

    @Then("Verify that facility name and facility type displayed")
    public void verify_that_facility_type_and_name_displayed_on_associated_business_unit(List<String> options) {
        for (String busUnit : options) {
            selectDropDown(activeContact.businessUnitDropdwn, busUnit);
            waitFor(5);
            if (busUnit.contains("CVUI")) {
                assertTrue(activeContact.facilityName.isDisplayed());
                waitFor(1);
                assertTrue(activeContact.facilityType.isDisplayed());

            }
        }
    }

    @Then("Verify the facility name field values in dropdown")
    public void verify_the_facility_name_dropdown_values_in_dropdown(List<String> options) {
        for (String facilityName : options) {
            selectDropDown(activeContact.facilityName, facilityName);

        }
    }

    @Then("Verify the facility name with associated facility type {string} local")
    public void verify_the_facility_name_associated_facility_type_Local(String facilityType, List<String> options) {
        for (String facilityName : options) {
            waitFor(1);
            selectDropDown(activeContact.facilityName, facilityName);
            waitFor(2);
            assertTrue(activeContact.facilityTypeValueLocal.getText().contains(facilityType), "not expected facility type selected");
            waitFor(2);
        }
    }

    @Then("Verify the facility name with associated facility type {string} all")
    public void verify_the_facility_name_associated_facility_type_All(String facilityType, List<String> options) {
        for (String facilityName : options) {
            waitFor(1);
            selectDropDown(activeContact.facilityName, facilityName);
            waitFor(2);
            assertTrue(activeContact.facilityTypeValueAll.getText().contains(facilityType), "not expected facility type selected");
            waitFor(2);
        }
    }

    @Then("Verify the facility name with associated facility type as text free field")
    public void verify_the_facility_name_associated_facility_type_text_free_field(List<String> options) {
        for (String facilityName : options) {
            selectDropDown(activeContact.facilityName, facilityName);
            waitFor(2);
            assertTrue(activeContact.facilityTypeField.isDisplayed());
        }
    }

    @When("I verify facility type is read only field")
    public void i_verify_user_role_is_readonly_text() {
        assertTrue(isElementDisplayed(activeContact.facilityType));
        assertEquals(activeContact.facilityType.getText(), "FACILITY TYPE");
        assertFalse(activeContact.facilityType.getTagName().equals("input"));
    }

    @When("I save the Contact Record with no Facility name chosen")
    public void Isavethecontactwithfacilityname() {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DOC");
        selectDropDown(contactRecord.contactReason, "Reported Fraud");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Recipient Fraud Information");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "hdht");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I save the Contact Record with Required Facility name {string} based on contactReason {string} and contactAction {string}")
    public void IsavethecontactwithRequiredfacilityname(String facilityName, String contactReason, String contactAction) {
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DOC");
        selectDropDown(contactRecord.contactReason, contactReason);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, contactAction);
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testing");
        contactRecord.saveReasonButton.click();

        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        selectDropDown(activeContact.facilityName, facilityName);
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I save the Contact Record without facility name to observe the warning error message")
    public void IsavethecontactwithoutFacilityName() {
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DOC");
        selectDropDown(contactRecord.contactReason, "New Application - CVIU");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Application Incomplete");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testing");
        contactRecord.saveReasonButton.click();
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());

        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I save the Contact Record without facility name to observe the warning error message on edit page")
    public void IsavethecontactwithoutFacilityNameOnEditPage() {
        scrollUpUsingActions(2);
        selectDropDown(contactRecord.contactReason, "New Application - CVIU");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Withdrew Application");
        waitFor(1);
        contactRecord.reasonsCommentsOnEditPage.click();
        clearAndFillText(contactRecord.reasonsCommentsOnEditPage, "testing");
        contactRecord.saveReasonButton.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @Then("I verify facilityName is required field on certain contactReason")
    public void i_verify_facilityName_is_required() {
        //assertTrue(activeContact.facilityNameRequiredMessage.isDisplayed());
        //assertTrue(isElementDisplayed(activeContact.facilityNameRequiredMessage));
        waitFor(2);
        activeContact.facilityNameRequiredMessage.isDisplayed();
    }

    @When("I update the Contact Record with Facility name {string}")
    public void Iupdatethecontactwithfacilityname(String facilityName) {
        selectDropDown(activeContact.facilityName, facilityName);
    }

    @When("I save the Contact Record with facility type as facility other")
    public void IsavethecontactwithRequiredfacilityname() {
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - DOC");
        selectDropDown(contactRecord.contactReason, "Complaint - CVIU");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Withdrew Complaint");
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testing");
        contactRecord.saveReasonButton.click();
        scrollDown();
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());
        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        selectDropDown(activeContact.facilityName, "Facility-Other");
        clearAndFillText(activeContact.facilityTypeNew, "TestingTestingTestingTestingTestingTestingTestingTestingTestingTestingTestingTesting");
        Assert.assertTrue(activeContact.facilityTypeMaxCharsDisplayed.isDisplayed());
        clearAndFillText(activeContact.facilityTypeNew, "Test!@#$");
        Assert.assertTrue(activeContact.facilityTypeInvalidFormatText.isDisplayed());
        clearAndFillText(activeContact.facilityTypeNew, ".,!@");
        Assert.assertTrue(activeContact.facilityTypeInvalidFormatText.isDisplayed());
        clearAndFillText(activeContact.facilityTypeNew, "Testing");
        waitFor(2);
        contactRecord.stopContact.click();
        scrollDown();
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I update the facility type")
    public void IupdatetheFaclityType() {
        clearAndFillText(activeContact.facilityTypeNew, "Demo");
    }

    @And("I select reason for edit {string} drop down and provide details to edit for facility Name")
    public void selectReasonForEditDropDownToUpdateFacilityName(String reasonForEdit) {
        scrollDown();
        selectDropDown(contactHistory.reasonForEditDropDown, reasonForEdit);
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        contactRecord.saveButton.click();
    }

    @And("I select the disposition field {string}")
    public void i_select_disposition_field(String dispositionField) {
        scrollDownUsingActions(2);
        waitFor(2);
        selectOptionFromMultiSelectDropDown(manualContactRecordSearchPage.dispositionDrpDwn, dispositionField);
    }

    @And("I will observe the value {string} in the reason droplist for each business Unit value")
    public void i_will_verify_the_value_in_reason_droplist(String ContactReason, List<String> options) {
        scrollUpUsingActions(2);
        waitFor(2);
        for (String option : options) {
            selectDropDown(activeContact.businessUnitDropdwn, option);
            selectDropDown(contactRecord.contactReason, ContactReason);
        }
    }

    @And("I will verify associated action options")
    public void i_will_verify_action_options(List<String> options) {
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(contactRecord.contactAction, option);
        }
    }

    @When("I fill out the active contact page and save it disposition {string} busUnit {string} contactReason {string} contactAction {string}")
    public void I_fill_out_the_active_page_and_save_it(String dispositioField, String busUnit, String contactReason, String contactAction) {
        scrollUpUsingActions(3);
        selectDropDown(activeContact.businessUnitDropdwn, busUnit);
        selectDropDown(contactRecord.contactReason, contactReason);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, contactAction);
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testCancelledOption");
        contactRecord.saveReasonButton.click();
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());
        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, dispositioField);
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @When("I fill the disposition {string} busUnit {string} contactReason {string} contactAction {string}")
    public void I_fill_the_fiels_to_observe_disposition_field_is_disabled(String dispositioField, String busUnit, String contactReason, String contactAction) {
        scrollDownUsingActions(2);
        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, dispositioField);
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, busUnit);
        selectDropDown(contactRecord.contactReason, contactReason);
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, contactAction);
        waitFor(1);
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testCancelledOption");
        contactRecord.saveReasonButton.click();
        waitFor(2);
    }

    @Then("I verify Error message is displayed without cancelled related contact reason")
    public void i_verify_Error_is_displayed_wihtout_selecting_contact_reason() {
        waitForVisibility(activeContact.cancelledErrorMessage, 2);
        assertTrue(activeContact.cancelledErrorMessage.isDisplayed(), "disposition field cancelled error message not displayed");
    }

    @Then("I verify that disposition field is disabled")
    public void i_verify_that_disposition_field_is_disabled() {
        waitFor(1);
        assertTrue(activeContact.dispositionFieldDisabled.isDisplayed(), "disposition field is not disabled");
    }

    @Then("I select the associated reason for edit field {string} and save it")
    public void i_select_reason_for_edit(String reasonForEdit) {
        waitFor(2);
        selectDropDown(contactHistory.reasonForEditDropDown, reasonForEdit);
        waitFor(1);
        contactRecord.closeButton.click();
    }


    @And("I select Business unit {string} for coverVA")
    public void iSelectBusinessUnitForCoverVA(String unitType) {
        action.sendKeys(Keys.ESCAPE).build().perform();
        selectDropDown(activeContact.businessUnitDropdwn, unitType);
        waitFor(3);
    }

    @And("I select contact Reason {string} and Contact Action {string}")
    public void iSelectContactReasonAndContactAction(String reasonType, String actionType) {
        if ("RANDOM".equals(reasonType)) {
            selectRandomDropDownOption(activeContact.contactRes);
        } else {
            selectDropDown(activeContact.contactRes, reasonType);
        }
        waitFor(1);
        if ("RANDOM".equals(actionType)) {
            selectRandomOptionFromMultiSelectDropDown(contactRecord.contactAction);
        } else {
            selectFromMultiSelectDropDown(contactRecord.contactAction, actionType);
        }
        action.sendKeys(Keys.ESCAPE).build().perform();
        contactReason.set(activeContact.contactRes.getText());
        for (WebElement webElement : contactRecord.selectedContactActionValueList) {
            contactAction.get().add(webElement.getText());
        }
    }

    @And("I verify the selected Contact Reason and Contact Action values {string}")
    public void iVerifyTheSelectedContactReasonAndContactActionValues(String verifyType) {
        switch (verifyType) {
            case "REMAIN THE SAME":
                assertEquals(contactReason, activeContact.contactRes.getText(), "Mismatch in expected CONTACT REASON value selected");
                List<WebElement> actualContactReason = Driver.getDriver().findElements(By.xpath("//div[@id='mui-component-select-contactAction']/div/div/span"));
                assertEquals(contactAction.get().size(), actualContactReason.size(), "Mismatch in expected numbers of CONTACT ACTION values");
                List<String> actualContactReasonValues = new ArrayList<>();
                for (WebElement webElement : actualContactReason) {
                    actualContactReasonValues.add(webElement.getText());
                }
                Collections.sort(contactAction.get());
                Collections.sort(actualContactReasonValues);
                assertEquals(contactAction.get(), actualContactReasonValues, "Mismatch in expected list of CONTACT ACTION values");
                System.out.println("actualContactReasonValues = " + actualContactReasonValues);
                System.out.println("contactAction = " + contactAction.get());
                break;
            case "HAS CLEARED FROM SELECTION":
                assertNull(activeContact.contactReasonInputValue.getAttribute("required value"));
                assertNull(activeContact.contactActionInputValue.getAttribute("required value"));
                break;
        }
    }

    @Then("I verify that edited By field is displayed with {string} value")
    public void i_verify_that_edited_By_field_is_displayed_with_value(String editedBy) {
        contactHistory.editHistoryTab.click();
        System.out.println("editedBy " + editedBy);
        System.out.println("value edited by " + contactHistory.editB.getText());
        System.out.println("text edited by " + contactHistory.editB.getAttribute("textContent"));
        System.out.println("text attribute edited by " + contactHistory.editB.getAttribute("innerText"));
        //assertTrue(contactHistory.editB.getText().contains(editedBy));
        assertTrue(contactHistory.editB.getText().equals(editedBy));

    }

    @When("I save the Contact Record with facility name text free {string}")
    public void IsavethecontactwithFacilityNameTextFree(String contactAction) {
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - Other");
        selectDropDown(contactRecord.contactReason, "Complaint - CVIU");
        selectDropDown(activeContact.contactAct, contactAction);
        waitFor(1);
        action.sendKeys(Keys.ESCAPE).build().perform();
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testing");
        contactRecord.saveReasonButton.click();
        scrollDown();
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());
        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        //sent 55 chars to verify not exceeding 50 chars
        waitFor(2);
        String random = getRandomString(55);
        clearAndFillText(activeContact.facilityNameTextFree, random);
        int expectedLenght = activeContact.facilityNameTextFree.getAttribute("value").length();
        Assert.assertTrue(expectedLenght <= 50);
        clearAndFillText(activeContact.facilityNameTextFree, "Araz123,. #$%^jsdhs");
        selectDropDown(activeContact.facilityTypesNew, "Local Jail");
        waitFor(2);
        contactRecord.stopContact.click();
        scrollDown();
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(1);
        contactRecord.closeButton.click();
        waitForVisibility(contactRecord.initContact, 5);
        Assert.assertTrue(contactRecord.initContact.isDisplayed());
    }

    @When("I updated the saved facility name text free field and facility type in edit page")
    public void IUpdatedFacilityNameAndFacilityTaype(List<String> options) {
        String random = getRandomString(55);
        clearAndFillText(activeContact.facilityNameTextFree, random);
        int expectedLenght = activeContact.facilityNameTextFree.getAttribute("value").length();
        Assert.assertTrue(expectedLenght <= 50);
        waitFor(2);
        clearAndFillText(activeContact.facilityNameTextFree, "test12345!@#$");
        waitFor(2);
        Assert.assertEquals(activeContact.facilityNameTextFree.getAttribute("value"), "test12345!@#$");
        activeContact.facilityTypesNew.click();
        assertTrue(activeContact.actualFacilityTypes.size() - 1 == options.size());
        for (int i = 1; i < options.size(); i++) {
            assertTrue(options.contains(activeContact.actualFacilityTypes.get(i).getText()), " drop down values are incorrect");
        }
    }

    @When("I select CVUI other to observe the facility name text free and facility type fields dropdown")
    public void ISelectcvuiToObserveFacilityNameTextFreeFeild(List<String> options) {
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVIU - Other");
        clearAndFillText(activeContact.facilityNameTextFree, "test");
        activeContact.facilityTypesNew.click();
        assertTrue(activeContact.actualFacilityTypes.size() - 1 == options.size());
        for (int i = 1; i < options.size(); i++) {
            assertTrue(options.contains(activeContact.actualFacilityTypes.get(i).getText()), " drop down values are incorrect");
        }
    }

    @And("I select the {string} ui created contact record")
    public void iSelectTheUiCreatedContactRecord(String type) {
        waitFor(2);
        switch (type) {
            case "previously":
                contactHistory.contactHistoryRowTwo.get(1).click();
                break;
        }
    }

    @Then("Verify that contact actions have the respective dropdowns")
    public void verify_that_contact_actions_have_respective_dropdown_values(List<String> actions) {
        for (String option : actions) {
            selectOptionFromMultiSelectDropDownNew(activeContact.contactAct, option);
        }
    }

    @Then("I navigate and verify Contact Reason drop down values")
    public void iVerifyContactReasonDropDownValues(List<String> Values) {
        waitForVisibility(activeContact.contactRes, 3);
        activeContact.contactRes.click();
        List<String> actualValues = new ArrayList<>();
        waitForVisibility(activeContact.contactReasonDropdownList.get(0), 5);
        for (int i = 0; i < activeContact.contactReasonDropdownList.size(); i++) {
            if (!activeContact.contactReasonDropdownList.get(i).getText().isEmpty())
                actualValues.add(activeContact.contactReasonDropdownList.get(i).getText());
        }
        List<String> expectedValues = new ArrayList<>(Values);
        Collections.sort(actualValues);
        Collections.sort(expectedValues);
        assertEquals(actualValues, expectedValues, "Contact Reason Dropdown values are incorrect" + "\nActual Values:   " + actualValues + "\nExpected Values: " + expectedValues);
        assertEquals(actualValues.size(), expectedValues.size(), "actual value size: " + actualValues.size() + " not equals to expected value size: " + expectedValues.size());
    }

    @Then("I verify Contact Action drop down value with associated Contact Reason")
    public void iVerifyContactActionDropDownValueWithAssociatedContactReason(List<String> expectedValues) {
        waitForVisibility(activeContact.contactAct, 3);
        activeContact.contactAct.click();
        List<String> actualValues = new ArrayList<>();
        waitForVisibility(activeContact.contactActionDropdownList.get(0), 5);
        for (int i = 0; i < activeContact.contactActionDropdownList.size(); i++) {
            if (!activeContact.contactActionDropdownList.get(i).getText().isEmpty())
                actualValues.add(activeContact.contactActionDropdownList.get(i).getText());
        }
        List<String> expectedValuess = new ArrayList<>(expectedValues);
        Collections.sort(actualValues);
        Collections.sort(expectedValuess);
        assertEquals(actualValues, expectedValuess, "Contact Action Dropdown values are incorrect" + "\nActual Values:   " + actualValues + "\nExpected Values: " + expectedValuess);
    }

    @Then("I verify newly added Inbound Call Queue dropdown values")
    public void iVerifyNewlyAddedInboundCallQueueDropdown(List<String> expected) {
        waitForVisibility(manualContactRecordSearchPage.inboundCallQueueDropDown, 3);
        crmContactRecordUIPage.callQueueType.click();
        List<String> actualValues = new ArrayList<>();
        waitForVisibility(activeContact.inboundCallQueueDropdownList.get(0), 5);
        for (int i = 0; i < activeContact.inboundCallQueueDropdownList.size(); i++) {
            if (!activeContact.inboundCallQueueDropdownList.get(i).getText().isEmpty())
                actualValues.add(activeContact.inboundCallQueueDropdownList.get(i).getText());
        }
        List<String> expectedValuess = new ArrayList<>(expected);
        assertTrue(actualValues.containsAll(expectedValuess), "Contact Action Dropdown values are incorrect" + "\nActual Values:   " + actualValues + "\nExpected Values: " + expectedValuess);
    }

    @When("I save {string} contact record with mandatory fields")
    public void IsavethecontactwithMandatoryFields(String type) {
        if (type.equals("Outbound")) {
            selectDropDown(crmContactRecordUIPage.contactType, type);
        }
        scrollUpUsingActions(2);
        selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
        selectDropDown(contactRecord.contactReason, "Complaint");
        selectDropDown(activeContact.contactAct, "Resolved");
        waitFor(1);
        action.sendKeys(Keys.ESCAPE).build().perform();
        contactRecord.reasonsComments.click();
        clearAndFillText(contactRecord.reasonsComments, "testing");
        contactRecord.saveReasonButton.click();
        scrollDown();
        waitFor(2);
        phoneNumber.set(newConsumer.get().get("phone").toString());
        clearAndFillText(activeContact.contactPhoneNumber, phoneNumber.get());
        singleSelectFromDropDown(activeContact.programTypes, "Medicaid");
        waitFor(2);
        contactRecord.stopContact.click();
        scrollDown();
        selectDropDown(contactRecord.contactDispositions, "Complete");
        waitFor(1);
        contactRecord.closeButton.click();
        waitForVisibility(contactRecord.initContact, 5);
        Assert.assertTrue(contactRecord.initContact.isDisplayed());
    }

    @And("I verify Application ID field is displayed in Contact Details panel after saving PW Outbound with a corresponding contact action")
    public void iVerifyApplicationIDFieldIsDisplayedInContactDetailsPanelAfterSavingPWOutboundWithACorrespondingContactAction() {
        waitFor(1);
        Driver.getDriver().findElement(By.xpath("//li[contains(@data-value, 'Contact Successful')]")).click();
        waitFor(1);
        action.sendKeys(Keys.ESCAPE).build().perform();
        waitFor(2);
        clearAndFillText(contactRecord.commentArea, "Auto Test Script in progress");
        Driver.getDriver().findElement(By.xpath("(//span[contains(text(),'check')])[2]")).click();
        waitFor(3);
        assertTrue(isElementDisplayed(contactRecord.applicationId), "Application Id field not found in Contact Details");
        clearAndFillText(contactRecord.applicationId, "123456789");
        assertEquals(contactRecord.applicationId.getAttribute("value"), "123456789", "Entered data not found for Application Id Field");
    }
}

