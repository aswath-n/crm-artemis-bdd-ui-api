package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class CRM_CaseConsumerProfileSearchStepDef extends CRMUtilities implements ApiBase {

    private String baseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String searchConsumerEndPoint = "app/crm/case/consumers";
    private ThreadLocal<JsonObject> consumerRequestParams = ThreadLocal.withInitial(JsonObject::new);
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMManualCaseConsumerSearchPage caseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();
    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMTaskSearchPage taskSearch = new CRMTaskSearchPage();
    CRMDashboardPage dashboardPage = new CRMDashboardPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
//    public String caseId;
    public static ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);

    public static void setConsumerID(String consumerID) {
        CRM_CaseConsumerProfileSearchStepDef.consumerID.set(consumerID);
    }
    @Then("I am able to manual Case Consumer Profile Search")
    public void manualCaseConsumerProfileSearch() {
        caseConsumerSearchPage.middleName.click();
        waitFor(1);
        caseConsumerSearchPage.firstName.sendKeys("UdPMnAe");
        caseConsumerSearchPage.middleName.sendKeys("");
        caseConsumerSearchPage.lastName.sendKeys("UAsGzuc");
        caseConsumerSearchPage.consumerSSN.sendKeys("866570375");
        caseConsumerSearchPage.DOB.sendKeys("11/17/1963");
        selectDropDown(caseConsumerSearchPage.searchCaseOptions, "Internal");
        waitFor(2);
        caseConsumerSearchPage.internalCaseId.sendKeys("118904");
        selectDropDown(caseConsumerSearchPage.searchConsumerOptions, "Internal");
        waitFor(2);
        caseConsumerSearchPage.internalConsumerId.sendKeys("205829");
        waitFor(1);
        caseConsumerSearchPage.searchButton.click();
        waitFor(4);

        String ResultFound = caseConsumerSearchPage.searchResultFirstNameFound.getText() + " " + caseConsumerSearchPage.searchResultLastNameFound.getText();
        assertTrue(ResultFound.contains("UdPMnAe UAsGzuc"));
    }

    @Then("I entered a search parameter in the incorrect format and receive a error message for case consumer search")
    public void searchParameterInTheIncorrectFormatAndReceiveAErrorMessage() {
        caseConsumerSearchPage.advanceSearchManualSrhScreen.click();
        caseConsumerSearchPage.email.sendKeys("register");
        caseConsumerSearchPage.searchButton.click();
        if (caseConsumerSearchPage.email.isDisplayed()) {
            String emailErrorText = contactRecord.ErrorTextMsg.getText();
            Assert.assertEquals(emailErrorText, "Please resolve the following:");
        }
    }

    @Then("I search and validate the result for case consumer search")
    public void searchAndValidateTheResult() {
        caseConsumerSearchPage.consumerSSN.click();
        caseConsumerSearchPage.firstName.sendKeys("testar");
        caseConsumerSearchPage.searchButton.click();
        caseConsumerSearchPage.searchResultFirstNameFound.isDisplayed();
        String ResultFound = caseConsumerSearchPage.searchResultFirstNameFound.getText();
        assertTrue(ResultFound.contains("Testar"));
    }

    @Then("I search without any parameters and will be provided with error message {string} for case consumer search")
    public void searchAndWillBeProvidedWithErrorMessage(String error) {
        caseConsumerSearchPage.searchButton.click();
        waitFor(2);
        contactRecord.noparamsText.isDisplayed();
        String noParams = contactRecord.noparamsText.getText();
        Assert.assertEquals(noParams, error);
    }

    @Then("I search and validate the no result message {string} for case consumer search")
    public void validateTheNoResultMessage(String noResult) {
        caseConsumerSearchPage.firstName.sendKeys("zxcvjuk");
        caseConsumerSearchPage.searchButton.click();
        waitFor(2);
        caseConsumerSearchPage.noResultsFound.isDisplayed();
        String noResultFound = caseConsumerSearchPage.noResultsFound.getText();
        Assert.assertEquals(noResultFound, noResult);
    }

    @Then("I enter the search data and validate the search result with the search data for case consumer search")
    public void validateTheSearchResultWithTheSearchData() {
        String caseIdReq = "118904";
        String consumerIdReq = "205829";
        caseConsumerSearchPage.internalCaseId.sendKeys(caseIdReq);
        caseConsumerSearchPage.internalConsumerId.sendKeys(consumerIdReq);
        caseConsumerSearchPage.DOB.sendKeys("11/17/1963");
        caseConsumerSearchPage.consumerSSN.sendKeys("866570375");
        waitFor(1);
        caseConsumerSearchPage.searchButton.click();
        caseConsumerSearchPage.caseIdFirstRecordValue.isDisplayed();
        String caseId = caseConsumerSearchPage.caseIdFirstRecordValue.getText();
        String consumerId = caseConsumerSearchPage.consumerIdFirstRecordValue.getText();
        Assert.assertEquals(caseId, caseIdReq);
        Assert.assertEquals(consumerId, consumerIdReq);
        waitFor(4);
    }

    @Then("I enter the search data and validate whether the search result contains the search data for case consumer search")
    public void validateWhetherTheSearchResultContainsTheSearchData() {
        waitFor(1);
        caseConsumerSearchPage.firstName.sendKeys("asad");
        waitFor(1);
        caseConsumerSearchPage.advanceSearchManualSrhScreen.click();
        waitFor(1);
        caseConsumerSearchPage.addressLine1.sendKeys("new");
        waitFor(2);
        caseConsumerSearchPage.searchButton.click();
    }

    @Given("I initiated Consumer Search API for profile search")
    public void i_initiated_consumer_search_api_for_profile_search() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchConsumerEndPoint);
    }

    @When("I can search consumer by {string} with value {string} for profile search")
    public void i_can_search_consumer_for_profile_search(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        consumerRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        consumerRequestParams.get().addProperty(item, value);
        System.out.println(consumerRequestParams.get());
    }

    @And("I run the consumer search API for profile search")
    public void i_run_the_consumer_search_api() {
        System.out.println(consumerRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(consumerRequestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        //System.out.println(api.responseString);
    }

    @Then("I can verify case consumer details are available based on search paramters for case consumer search")
    public void verifyCaseConsumerDetailsForSearchParameters() {
        Object caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result");
        assertNotNull(caseList);
    }

    @Then("I should view CRM case id, CRM consumer id, FirstName, Middle Name, LastName, DOB, SSN fields at search results")
    public void i_should_view_CRM_case_id_CRM_consumer_id_FirstName_Middle_Name_LastName_DOB_SSN_fields_at_search_results() {
        assertTrue(caseConsumerSearchPage.crmCaseIdField.isDisplayed(), "CaseId field isnt displayed");
        assertTrue(caseConsumerSearchPage.crmConsumerIdField.isDisplayed(), "ConsumerId field isnt displayed");
        assertTrue(caseConsumerSearchPage.firstNameField.isDisplayed(), "First name field isnt displayed");
        assertTrue(caseConsumerSearchPage.middleNameField.isDisplayed(), "Middle name field isnt displayed");
        assertTrue(caseConsumerSearchPage.lastNameField.isDisplayed(), "Last name field isnt displayed");
        assertTrue(caseConsumerSearchPage.DOBField.isDisplayed(), "DOB field isnt displayed");
        assertTrue(caseConsumerSearchPage.SSNField.isDisplayed(), "SSN field isnt displayed");
        assertTrue(caseConsumerSearchPage.caseIdFirstRecordValue.isDisplayed(), "CaseId value isnt displayed");
        assertTrue(caseConsumerSearchPage.consumerIdFirstRecordValue.isDisplayed(), "ConsumerId value isnt displayed");
        assertTrue(caseConsumerSearchPage.searchResultFirstNameFound.isDisplayed(), "First name value isnt displayed");
        assertTrue(caseConsumerSearchPage.searchResultLastNameFound.isDisplayed(), "Last name value isnt displayed");
        assertTrue(caseConsumerSearchPage.searchResultDOBFound.isDisplayed(), "DOB value isnt displayed");
        assertTrue(caseConsumerSearchPage.searchResultSSNFound.isDisplayed(), "SSN value isnt displayed");
    }

    @Then("I expand the Case Consumer in search result")
    public void i_expand_the_Case_Consumer_in_search_result() {
        waitFor(2);
        jsClick(caseConsumerSearchPage.expandButton);
        waitFor(1);
    }

    @Then("click on found case or consumer")
    public void click_on_found_case_or_consumer() {
        caseConsumerSearchPage.consumerIdTable.get(0).click();
    }

    @Then("I should see address information for each result")
    public void i_should_see_address_information_for_each_result() {
        assertTrue(caseConsumerSearchPage.homeAddressField.isDisplayed());
        System.out.println(caseConsumerSearchPage.homeAddressField.getText());
        String homeAddress = caseConsumerSearchPage.homeAddressInformationFirstLine.getText() + " " +
                caseConsumerSearchPage.homeAddressInformationSecondLine.getText();
        System.out.println("Home address is: " + homeAddress);
        assertTrue(homeAddress.equals("123 st lane, " +
                "apt.12\n" +
                "Richmond, county, Florida, 99898-9999"));
        System.out.println("Address is verified");
    }

    @Then("I should view all Consumer's associated to the Case & their specific Role on the Case")
    public void i_should_view_all_Consumer_s_associated_to_the_Case_their_specific_Role_on_the_Case() {
        scrollDownUsingActions(2);
        assertTrue(caseConsumerSearchPage.primaryIndividualField.isDisplayed(), "Primary individial field isnt displayed");
        assertTrue(caseConsumerSearchPage.caseMembersField.isDisplayed(), "Case members field isnt displayed");
        assertTrue(caseConsumerSearchPage.authorizedRepresentativeField.isDisplayed(), "Authorized representative field isnt displayed");
        waitFor(2);
        assertTrue(!caseConsumerSearchPage.primaryIndividualMembersTable.isEmpty(), "There is no primary individual member");
        assertTrue(!caseConsumerSearchPage.caseMembersTable.isEmpty(), "There is no case members");
        assertTrue(!caseConsumerSearchPage.authorizedRepresentativetable.isEmpty(), "There is no authorized representative members");
    }


    @When("I search consumer has first name as {string}")
    public void i_search_consumer_has_first_name_as(String firstname) {
        clearAndFillText(contactRecord.firstName, firstname);
        contactRecord.searchButton.click();
    }


    @Then("I verify that all records sorted by Case Id in descending order")
    public void i_verify_that_all_records_sorted_by_Case_Id_in_descending_order() {
        //selectDropDown(caseConsumerSearchPage.itemsPerPageDropdown,"show 20");
        waitFor(2);
        assertTrue(descendingOrderContactIDs(caseConsumerSearchPage.caseIdTable), "CASE ID" + " column is not in descending order");
        waitFor(2);
        assertTrue(descendingOrderContactIDs(caseConsumerSearchPage.consumerIdTable), "CONSUMER ID" + " column is not in descending order");
    }



    @Then("I verify that all records which doesnt have case id sorted in descending order")
    public void i_verify_that_all_records_which_doesnt_have_case_id_sorted_in_descending_order() {
        scrollDown();
        waitFor(3);
        selectOptionFromMultiSelectDropDown(caseConsumerSearchPage.itemsPerPageDropdown, "20");
        waitFor(2);
        assertTrue(descendingOrderContactIDs(caseConsumerSearchPage.consumerIdTableWithoutCaseId), "CONSUMER ID" + " column is not in descending order");
    }

    @When("I click the first consumer id from the search results")
    public void i_click_the_first_consumer_id_from_the_search_results() {
        waitFor(2);
        if (LoginStepDef.projectName1.get().equalsIgnoreCase("BLCRM") && caseConsumerSearchPage.caseIdFRValue.getText().equals("--")) {
            caseConsumerSearchPage.consumerIdFirstRecordValue.click();
        } else
            caseConsumerSearchPage.caseIdFRValue.click();
        waitFor(2);
    }

    @Then("I see notes component with dropdown relates to save and cancel button {string}")
    public void i_see_notes_component_with_dropdown_relates_to_save_and_cancel_button(String consumerType) {
        if (isElementDisplayed(demographicContactInfoPage.primaryIndividualsLabel)) {
            assertTrue(isElementDisplayed(demographicContactInfoPage.relatesToDropdown), "relatesToDropdown not present on a page");
            assertTrue(isElementDisplayed(demographicContactInfoPage.caseNotesLabel), "caseNotesLabel not present on a page");
        } else if (!isElementDisplayed(demographicContactInfoPage.primaryIndividualsLabel)) {
            if (consumerType.equalsIgnoreCase("Case")) {
                scrollDown();
                assertTrue(isElementDisplayed(demographicContactInfoPage.relatesToDropdown), "relatesToDropdown not present on a page");
                assertTrue(isElementDisplayed(demographicContactInfoPage.caseNotesLabel), "caseNotesLabel not present on a page");
            } else if (consumerType.equalsIgnoreCase("ConsumerPorfile")) {
                assertTrue(isElementDisplayed(demographicContactInfoPage.consumerNotesLabel), "NotesLabel not present on a page");
            }

        }
        assertTrue(isElementDisplayed(demographicContactInfoPage.notesTextInputField), "notesTextInputFeald not present on a page");
        assertTrue(isElementDisplayed(demographicContactInfoPage.saveNotesButton), "saveNotesButton not present on a page");
        assertTrue(isElementDisplayed(demographicContactInfoPage.cancelNotesButton), "cancelNotesButton not present on a page");

    }

    @Then("I verify text field is can take 1000alphanumeric characters")
    public void alphanumericCharactersTextField() {
        scrollDown();
        waitFor(2);
        selectRandomDropDownOption(demographicContactInfoPage.relatesToDropdown);
        clearAndFillText(demographicContactInfoPage.notesTextInputField, getRandomString(1000));
        jsClick(demographicContactInfoPage.saveNotesButton);
        waitFor(1);
        assertTrue(isElementDisplayed(demographicContactInfoPage.NotesSavedSuccesfullyMessage));
    }

    @Then("Verify Then I will have a required single select list of options Case and all Consumers should be listed")
    public void VerifyDropdownValues() {
        waitForVisibility(demographicContactInfoPage.primaryIndividualsLabel, 5);
        scrollDown();
        waitFor(4);
        List<String> listOfConsumersInCase = getAllCaseMembersFullNameFromCase();
        System.out.println(listOfConsumersInCase.toString());
        List<String> listOfDropdownOptions = new ArrayList<>();
        demographicContactInfoPage.relatesToDropdown.click();
        waitFor(2);
        for (int i = 0; i < demographicContactInfoPage.relatesToDropdownOptions.size(); i++) {
            listOfDropdownOptions.add(demographicContactInfoPage.relatesToDropdownOptions.get(i).getAttribute("data-value"));
            System.out.println(demographicContactInfoPage.relatesToDropdownOptions.get(i).getAttribute("data-value"));
        }
        assertEquals(listOfDropdownOptions.get(0), "Household/Case", "Household/Case label not exist");
        assertTrue(listOfConsumersInCase.size() <= listOfDropdownOptions.size() - 1, "Dropdown options more then consumers +2");
        for (int i = 1; i < listOfConsumersInCase.size(); i++) {
            assertTrue(listOfDropdownOptions.toString().contains(listOfConsumersInCase.get(i)), "Consumer not match");
        }
    }

    @Then("I will verify new note get saved as 1st note in table")
    public void validayeNotesWasCaptured() {
        String notes = "Test notes" + getRandomString(20);
        if (isElementDisplayed(demographicContactInfoPage.primaryIndividualsLabel)) {
            selectRandomDropDownOption(demographicContactInfoPage.relatesToDropdown);
            waitFor(2);
            clearAndFillText(demographicContactInfoPage.notesTextInputField, notes);
            demographicContactInfoPage.saveNotesButton.click();
            waitFor(6);
            System.out.println(notes + "|||" + demographicContactInfoPage.firstNotesInNotesList.getText());
            assertEquals(demographicContactInfoPage.firstNotesInNotesList.getText().toLowerCase(Locale.ROOT), notes.toLowerCase(Locale.ROOT), "new created Notes Not present");
        } else if (!isElementDisplayed(demographicContactInfoPage.primaryIndividualsLabel)) {
            waitFor(2);
            clearAndFillText(demographicContactInfoPage.notesTextInputField, notes);
            waitFor(2);
            demographicContactInfoPage.saveNotesButton.click();
            waitFor(6);
            System.out.println(notes + "|||" + demographicContactInfoPage.firstNotesInNotesList.getText());
            assertEquals(demographicContactInfoPage.firstNotesInNotesList.getText().toLowerCase(Locale.ROOT), notes.toLowerCase(Locale.ROOT), "new created Notes Not present");
        }
    }

    @Then("I Verify error message {string}")
    public void VerifyErrorMessage(String message) {
        scrollDown();
        if (message.contains("relates to")) {
            String notes = "test notes" + getRandomString(20);
            clearAndFillText(demographicContactInfoPage.notesTextInputField, notes);
            demographicContactInfoPage.saveNotesButton.click();
            waitFor(2);
            assertEquals(demographicContactInfoPage.messagePleaseIndicateWhoThisNoteRelatesTo.getText(), message, " Message NOT match");
        } else if (message.contains("Note text")) {
            waitFor(1);
            selectRandomDropDownOption(demographicContactInfoPage.relatesToDropdown);
            demographicContactInfoPage.saveNotesButton.click();
            waitFor(2);
            assertEquals(demographicContactInfoPage.messagePleaseProvideNoteText.getText(), message, " Message NOT match");
        }
    }

    @Then("I validate consumer search result according to {string} Consumer Id {string}")
    public void iValidateConsumerSearchResultAccordingToConsumerId(String idtype, String idValue) {
        waitFor(2);
        assertTrue(searchResult.caseIdColumnHeader.getText().contains(idtype),
                "Expected " + idtype + " is not displayed in search result");
        assertTrue(searchResult.CaseIDs.get(0).getText().contains(idValue),
                "Expected " + idValue + " is not displayed in search result");

    }

    @And("I searched consumer with {string} Consumer Id {string}")
    public void iSearchedConsumerWithConsumerId(String idType, String idValue) {
        waitFor(1);
        selectDropDown(contactRecord.searchConsumerOptions, idType);
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.externalConsumerIdSearch, idValue);
        waitFor(2);
        contactRecord.searchButton.click();
    }

    @Then("I validate task search result displayed expected record")
    public void iValidateTaskSearchResultDisplayedExpectedRecord() {
        waitFor(1);
        assertTrue(taskSearch.taskIDColumnHeader.isDisplayed(), "Task search by External Consumer Id did not return any record");
        assertFalse(taskSearch.taskIDs.get(0).getText().isEmpty(), "Task Id for search result with External Consumer Id is empty");
    }

    @And("I searched task with {string} Consumer Id {string}")
    public void iSearchedTaskWithConsumerId(String idType, String idValue) {
        waitFor(1);
        selectDropDown(taskSearch.searchConsumerDropdown, idType);
        waitFor(1);
        clearAndFillText(taskSearch.consumerId, idValue);
        waitFor(2);
        taskSearch.searchBtn.click();
    }

    @Given("I type some note in to text field but not saved")
    public void i_type_some_note_in_to_text_field_but_not_saved() {
        waitForVisibility(demographicContactInfoPage.notesTextInputField, 10);
        clearAndFillText(demographicContactInfoPage.notesTextInputField, getRandomString(7));
    }

    @When("I Navigates Away While Unsaved Note Present")
    public void i_Navigates_Away_While_Unsaved_Note_Present() {
        hover(dashboardPage.caseConsumerSideBarIcon);
        waitForClickablility(dashboardPage.case_ConsumerSearchTab, 5);
        jsClick(dashboardPage.case_ConsumerSearchTab);
    }

    @Then("I should see warning message: {string}")
    public void i_should_see_warning_message(String expectedWarning) {
        waitForVisibility(demographicContactInfoPage.warningNoteMessage, 10);
        assertEquals(demographicContactInfoPage.warningNoteMessage.getText(), expectedWarning);

    }

    @And("I click on consumer id in search result")
    public void iVerifyTheSearchResults() {
        waitFor(2);
        caseConsumerSearchPage.consumerIdFirstRecordValue.click();
    }

    @Then("I validate consumer search result according to {string} with extra space Consumer Id {string}")
    public void iValidateConsumerSearchResultWithSpaceConsumerId(String idtype, String idValue) {
        waitFor(2);
        System.out.println(searchResult.caseIdColumnHeader.getText() + ":::::::::::::::::::::::");
        assertTrue(searchResult.consumerIdColumnHeader.getText().contains(idtype),
                "Expected " + idtype + " is not displayed in search result");
        assertTrue(searchResult.ConsumerIDs.get(0).getText().contains(idValue),
                "Expected " + idValue + " is not displayed in search result");

    }

    @And("I search created consumer by internal ID")
    public void iSearByInternalConsumerId() {
        waitFor(1);
        selectDropDown(contactRecord.searchConsumerOptions, "Internal");
        waitFor(1);
        System.out.println("ConsumerID =============== " + consumerID.get());
        clearAndFillText(manualContactRecordSearchPage.consumerIDField, consumerID.get());
        waitFor(2);
        contactRecord.searchButton.click();
    }
}
