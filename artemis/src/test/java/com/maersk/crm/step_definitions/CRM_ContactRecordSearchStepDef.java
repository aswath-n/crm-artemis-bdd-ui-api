package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class CRM_ContactRecordSearchStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();

    private final ThreadLocal<String> prefixFirstName =ThreadLocal.withInitial(() -> getRandomString(4));
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> fullNameTab = ThreadLocal.withInitial(String::new);

    @Then("I am able to search using the following basic search parameters for Contact Record")
    public void searchUsingTheFollowingSearchParametersForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        waitFor(3);
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactName = contactRecord.contactNameValue.getText();
        String[] splitName = contactName.split("\\s+");

        assertTrue(splitName[0].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
        assertTrue(splitName[2].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName()));
        assertEquals(contactRecord.contactConsumerIdValue.getText(), APIUtilitiesForUIScenarios.getInstance().getConsumerId());
    }

    @When("I create consumer for Contact Record")
    public void createConsumerForContactRecord() {
        creatNewConsumerForContactRecord(firstName.get());
    }

    @When("I create {int} consumers for Contact Record")
    public void createMultipleConsumersForContactRecord(int numOfConsumers) {
        for (int i = 0; i < numOfConsumers; i++) {
            creatNewConsumerForContactRecord(prefixFirstName + getRandomString(4));
        }
    }

    @Then("I select Advanced Search and able to search using the following additional parameters for Contact Record")
    public void ableToSearchUsingTheFollowingAdditionalParametersForContactRecord() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactName = contactRecord.contactNameValue.getText();
        String[] splitName = contactName.split("\\s+");

        assertTrue(splitName[0].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
        assertTrue(splitName[2].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName()));
        assertEquals(contactRecord.contactConsumerIdValue.getText(), APIUtilitiesForUIScenarios.getInstance().getConsumerId());
    }

    @Then("I validate the fields if I have entered a search parameter in the incorrect format for Contact Record")
    public void validateTheFieldsForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactRecordNumber.sendKeys("3");
        contactRecord.contactPhoneNumber.sendKeys("551551");
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);
        String contactRecordError = contactRecord.contactRecordNumberError.getText();
        String phoneError = contactRecord.contactPhoneError.getText();
        System.out.println(contactRecordError);
        System.out.println(phoneError);
        Assert.assertEquals(contactRecordError, "CONTACT RECORD ID must be at least 3 characters");
        Assert.assertEquals(phoneError, "PHONE must be 10 characters");
    }

    @Then("I enter search parameters and validate the results for Contact Record")
    public void enterSearchParametersAndvalidateTheResultsForContactRecord() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.searchConsumerValue.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactName = contactRecord.contactNameValue.getText();
        String[] splitName = contactName.split("\\s+");

        assertTrue(splitName[0].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
        assertTrue(splitName[2].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName()));
        assertEquals(contactRecord.contactConsumerIdValue.getText(), APIUtilitiesForUIScenarios.getInstance().getConsumerId());
    }

    @And("I enter search parameters that return null results for Contact Record")
    public void searchParametersThatReturnNullResultsForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys("zyx");
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);
    }

    @Then("I will receive the following message {string} for Contact Record")
    public void receiveTheFollowingMessageForContactRecord(String errorMessage) {
        //contactRecord.noResultsFound.isDisplayed();
        String noResultFound = manualContactRecordSearchPage.noResultsFound.getText();
        Assert.assertEquals(noResultFound, errorMessage);
    }

    @Then("I enter search parameters and validate the results if the information I have entered is an exact match for Contact Record")
    public void searchParametersAndValidateForContactRecord() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.searchConsumerValue.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactConsumerId = contactRecord.contactConsumerIdValue.getText();
        assertEquals(contactConsumerId, APIUtilitiesForUIScenarios.getInstance().getConsumerId());
    }

    @Then("I enter search parameters and validate search results if the information I have entered matches information for Contact Record")
    public void enterSearchParametersAndValidateResultsIfTheInformationdMatchesForContactRecord() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.searchConsumerValue.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactName = contactRecord.contactNameValue.getText();
        String[] splitName = contactName.split("\\s+");

        assertTrue(splitName[0].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
        assertTrue(splitName[2].equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName()));
        assertEquals(contactRecord.contactConsumerIdValue.getText(), APIUtilitiesForUIScenarios.getInstance().getConsumerId());
    }

    @Then("I enter more than one search criteria and validate all matching results for Contact Record")
    public void enterMoreThanOneSearchCriteriaForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(prefixFirstName.get());
        contactRecord.contactLN.sendKeys("");
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        String contactName = contactRecord.contactNameValue.getText();
        String[] splitName = contactName.split("\\s+");

        System.out.println("First Name Value of 1st record:" + splitName[0]);
        assertTrue(splitName[0].substring(0, 3).equalsIgnoreCase(prefixFirstName.get().substring(0, 3)));

        String contact2ndName = contactRecord.contactName2ndValue.getText();
        String[] split2ndName = contact2ndName.split("\\s+");

        System.out.println("First Name Value of 2nd record:" + split2ndName[0]);
        assertTrue(split2ndName[0].substring(0, 3).equalsIgnoreCase(prefixFirstName.get().substring(0, 3)));
        waitFor(4);
    }

    @And("I have not entered at least one search parameter and click search")
    public void notEnteredAtLeastOneSearchParameterForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.enterSearchParametersWarning, 3);
    }

    @Then("I will be provided an Error Message {string} for Contact Record")
    public void willBeProvidedAnErrorMessageForContactRecord(String noResultMsg) {
        String error = contactRecord.enterSearchParametersWarning.getText();
        waitFor(3);
        Assert.assertEquals(error, noResultMsg);
    }

    @Then("I search and will be able to view the results for Contact Record")
    public void searchAndWillBeAbleToViewTheResultsForContactRecord() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.searchConsumerValue.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerId());
        waitFor(2);

        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);
        assertNotNull(contactRecord.contactCreatedOnValue.getText());
        assertNotNull(contactRecord.contactConsumerIdValue.getText());
        assertNotNull(contactRecord.contactNameValue.getText());
        assertNotNull(contactRecord.contactRecordTypeValue.getText());
        assertNotNull(contactRecord.contactDispositionsValue.getText());

    }

    @Then("I search and expand to see specific Search Result for Contact Record")
    public void searchAndExpandToSeeSpecificSearchResultForContactRecord() {
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        contactRecord.contactCaseId.click();
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.contactShowMoreInfo.click();
        waitFor(2);

        assertNotNull(contactRecord.contactCreatedByValue.getText());
        assertNotNull(contactRecord.contactPhoneNumberValue.getText());
        assertNotNull(contactRecord.contactInboundCallValue.getText());
        assertNotNull(contactRecord.contactUserIdValue.getText());
    }

    @Then("I search and will be able to view the Search Results sorted in descending order by Contact Record ID for Contact Record")
    public void searchAndViewTheSearchResultsSortedForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(prefixFirstName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);

        String contactIdFirstRecord = contactRecord.contactIdFirstRecord.getText();
        String contactIdSecondRecord = contactRecord.contactIdSecondRecord.getText();
        boolean contactIdSorted = Integer.parseInt(contactIdFirstRecord) > Integer.parseInt(contactIdSecondRecord);

        assertTrue(contactIdSorted);
    }

    @Then("I search and will be able to sort each column of Search Results in ascending or descending order for Contact Record")
    public void searchAndWillBeAbleToSortForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactFN.sendKeys(prefixFirstName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);

        String consumerIdFirstRecord = contactRecord.contactConsumerIdFirstRecord.getText();
        String consumerIdSecondRecord = contactRecord.contactConsumerIdSecondRecord.getText();
        boolean consumerIdSorted = Integer.parseInt(consumerIdFirstRecord) > Integer.parseInt(consumerIdSecondRecord);

        assertTrue(consumerIdSorted);
    }

    @And("I search and view results for Contact Record")
    public void searchAndViewResultsForContactRecord() {
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);
    }

    @Then("I will be able to select the Contact Record ID for the specific Contact Record and view Contact Record Details for Contact Record")
    public void willBeAbleToSelectAndViewContactRecordDetailsForContactRecord() {
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(2);
        String contactId = contactRecord.contactIdFirstRecord.getText();
        contactRecord.contactIdFirstRecord.click();
        String contactIdDetailsPage = contactRecord.contactIdDetailsPage.getText();
        Assert.assertTrue(contactIdDetailsPage.contains(contactId));
    }

    @Then("I will be able to navigate back to the Manual Contact Record Search Results for Contact Record")
    public void willBeAbleToNavigateBackToSearchResultsForContactRecord() {
        contactRecord.contactIdFirstRecord.click();
        contactRecord.backButton.click();
        waitFor(2);

        assertNotNull(contactRecord.contactNameValue.getText());
    }

    @Then("I can edit the Contact Record by adding an Additional Comment and will be able to navigated back to Search Results for Contact Record")
    public void editTheContactRecordAndAbleToNavigateBackToSearchResultsForContactRecord() {
        waitForVisibility(contactRecord.contactIdFirstRecord, 8);
        contactRecord.contactIdFirstRecord.click();
        waitFor(1);
        contactRecord.editContactButton.click();
        contactRecord.additionalCommentsTextBox.sendKeys("New Comment");
        contactRecord.saveAdditionalComments.click();
        waitFor(1);
        selectOptionFromMultiSelectDropDown(contactRecord.contactReasonForEdit, "Adding Additional Comment");
        contactRecord.saveEditContactRecordBtn.click();
        waitFor(2);
    }

    private void creatNewConsumerForContactRecord(String firstNameSent) {
        waitFor(3);
        contactRecord.initContact.click();

        clearAndFillText(contactRecord.firstName, firstNameSent);
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstNameSent + " " + lastName.get());
        System.out.println(firstNameSent + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstNameSent);
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        waitFor(2);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());

        scrollUpUsingActions(3);
        selectDropDown(contactRecord.contactReason, "Enrollment");
        selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Plan Change");
        waitFor(1);
        contactRecord.saveReasonButton.click();
        waitFor(2);
        clearAndFillText(contactRecord.phoneNumber, newConsumer.get().get("phone").toString());
        singleSelectFromDropDown(contactRecord.lstSelectProgram, "Medicaid");

        waitFor(2);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecord.stopContact.click();
        waitFor(1);
        contactRecord.closeButton.click();
    }

    @And("I will verify Edit Contact button are not displayed at page level")
    public void iWillVerifyEditContactButtonAreNotDisplayedAtPageLevel() {
        waitForVisibility(contactRecord.firstContactRecordTable, 15);
        contactRecord.firstContactRecordTable.click();
        Assert.assertFalse(isElementDisplayed(manualContactRecordSearchPage.editContactButton));

    }

    @When("I create consumer with CSR account for Contact Record")
    public void iCreateConsumerWithCSRAccountForContactRecord() {
        creatNewConsumerForCSRContactRecord(firstName.get());
    }

    private void creatNewConsumerForCSRContactRecord(String firstName) {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName);
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstName + " " + lastName.get());
        System.out.println(firstName + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstName);
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
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
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());
    }

    @Then("I search for newly created record and navigated to the record")
    public void iSearchForNewCreatedRecordAndNavigatedToTheRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        waitFor(3);
        contactRecord.contactFN.sendKeys(firstName.get());
        contactRecord.contactLN.sendKeys(lastName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.firstContactRecordTable, 15);
        contactRecord.firstContactRecordTable.click();
    }

    @And("I navigate to the record")
    public void InavigateToTheRecord() {
        waitForVisibility(contactRecord.CSRcaseAndContactDetails, 15);
        contactRecord.CSRcaseAndContactDetails.click();
        scrollUpUsingActions(1);
        waitForVisibility(contactRecord.firstContactRecordTable, 15);
        contactRecord.firstContactRecordTable.click();
    }

    @When("I searched customer have First Name as {string} and Last Name as {string} with todays date")
    public void i_searched_customer_have_First_Name_as_and_Last_Name_as_with_todays_date(String firstname, String lastname) {
        hover(dashBoard.btnMenuList);
        waitFor(2);
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.lastName, lastname);
        CRM_WorkQueueStepDef.consumerName.set(firstname + " " + lastname);
        waitFor(3);
        fullNameTab.set(firstname + " " + lastname);
        System.out.println(fullNameTab);
        String currentDate = getCurrentDate();
        clearAndFillText(contactRecord.createdOn, currentDate);
        click(contactRecord.searchButton);
        waitFor(5);
    }

    @Then("When I enter Search criteria fields for a newly created consumer")
    public void whenIEnterSearchCriteriaFieldsForANewlyCreatedConsumer() {
        hover( contactRecord.contactLN);
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        if(!contactRecord.contactFN.getAttribute("value").equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName())
                &&contactRecord.contactLN.getAttribute("value").equalsIgnoreCase(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName())){
            contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
            contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        }
        Assert.assertEquals(contactRecord.contactFN.getAttribute("value").toUpperCase(),APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName().toUpperCase());
        Assert.assertEquals(contactRecord.contactLN.getAttribute("value").toUpperCase(),APIUtilitiesForUIScenarios.getInstance().getConsumerLastName().toUpperCase());
//        clearAndFillText(contactRecord.firstName, (APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()));
//        clearAndFillText(contactRecord.lastName, (APIUtilitiesForUIScenarios.getInstance().getConsumerLastName()));
        consumerName.set(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName() + " " + APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
    }

    @And("Verify Search with CONTACT ID result from Contact Record search")
    public void verifySearchwithCONTACTIDResultFromContactRecordSearch() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        waitFor(3);
        contactRecord.contactFN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        contactRecord.contactLN.sendKeys(APIUtilitiesForUIScenarios.getInstance().getConsumerLastName());
        contactRecord.contactRecordId.sendKeys(APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
        waitFor(2);
        contactRecord.searchButton.click();
        contactRecord.searchButton.click();
        waitFor(3);

        assertEquals(contactRecord.contactConsumerIdValue.getText(), APIUtilitiesForUIScenarios.getInstance().getConsumerId());
        Assert.assertEquals(manualContactRecordSearchPage.contactiD.getText(), APIUtilitiesForUIScenarios.getInstance().getContactRecordId());
    }

    @And("I search with FirstName and ConsumerType {string}")
    public void IsearchwithFirstNameandwithConsumerType(String contentType) {
        waitFor(1);
        jsClick(dashBoard.case_ConsumerSearchTab);
        waitFor(1);
        selectDropDown(manualCaseConsumerSearchPage.contactRecordTypeDrp, contentType);

        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.firstNameSearch, APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName());
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @And("Verify result with search CaseId and ConsumerType")
    public void verifyResultWithSearchCaseIdAndConsumerType() {
        waitFor(1);
        Assert.assertTrue(manualCaseConsumerSearchPage.getConFirstSearchResult(APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName()).isDisplayed());
        Assert.assertTrue(manualCaseConsumerSearchPage.consumerIdTable.get(0).isDisplayed());
    }

    @Then("I search with  Search Case {string} and Case ID {string}")
    public void i_search_with_Search_Case_and_Case_ID(String SearchCase, String caseId) {
        waitFor(1);
        selectDropDown(manualCaseConsumerSearchPage.contactRecordTypeDrp, SearchCase);
        waitFor(1);
        if (SearchCase.equalsIgnoreCase("Internal")) {
            clearAndFillText(manualContactRecordSearchPage.caseIdSearch, caseId);
        } else if (SearchCase.equalsIgnoreCase("State")) {
            clearAndFillText(manualContactRecordSearchPage.StateCaseIdSeach, caseId);
            waitFor(2);
        } else if (SearchCase.equalsIgnoreCase("Medicaid") || SearchCase.equalsIgnoreCase("CHIP")){
            clearAndFillText(manualContactRecordSearchPage.externalCaseIdSearch, caseId);
            waitFor(2);
        }
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I search by Case ID {string}")
    public void i_search_by_Case_ID(String caseId) {
        waitFor(2);
        clearAndFillText(manualContactRecordSearchPage.caseIdSearch, caseId);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I search by Consumer ID {string}")
    public void i_search_by_Consumer_ID(String consumerId) {
        waitFor(2);
        clearAndFillText(manualContactRecordSearchPage.consumerIDField, consumerId);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I search with Search Case {string} and Case ID {string}")
    public void i_search_with_searchCase_and_caseId(String dropdownValue, String caseId) {
        waitFor(1);
        if (dropdownValue.equalsIgnoreCase("Internal")) {
            clearAndFillText(manualContactRecordSearchPage.caseIdSearch, caseId);
        } else if (dropdownValue.equalsIgnoreCase("State")) {
            clearAndFillText(manualContactRecordSearchPage.StateCaseIdSearch, caseId);
            waitFor(2);
        }
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }


    @Then("I see expected {string} on IN-EB Search Case component")
    public void i_see_expected_on_IN_EB_Search_Case_component(String State) {
        waitFor(1);
        selectDropDown(manualCaseConsumerSearchPage.contactRecordTypeDrp, State);
        Assert.assertTrue(manualContactRecordSearchPage.State.isDisplayed());
    }

    @Then("I see the column for Case ID with the correct value {string}")
    public void i_see_the_column_for_Case_ID_with_the_correct_value(String caseId) {
        if (contactRecord.searchCaseDropdown.equals("Internal")) {
            assertTrue(manualContactRecordSearchPage.CRMCaseID.isDisplayed());
        } else if (contactRecord.searchCaseDropdown.equals("State")) {
            assertTrue(manualContactRecordSearchPage.StateCaseID.isDisplayed());
            waitFor(2);
        }
        assertEquals(manualCaseConsumerSearchPage.CRMCaseIdTable.getText(), caseId);
    }


    @Then("I see the column for Case ID with the correct value {string} Contact Record Search page")
    public void i_see_the_column_for_Case_ID_with_the_correct_value_contact_record_search_page(String caseId) {
        if (contactRecord.searchCaseDropdownNew.equals("Internal")) {
            assertTrue(manualContactRecordSearchPage.CRMCaseID.isDisplayed());
        } else if (contactRecord.searchCaseDropdownNew.equals("State")) {
            assertTrue(manualContactRecordSearchPage.StateCaseIDTable.isDisplayed());
            waitFor(2);
        }
        assertEquals(manualCaseConsumerSearchPage.CRMCaseIdTable.getText(), caseId);
    }


    @Then("I search Consumer Type {string} and Consumer ID {string}")
    public void i_search_Consumer_Type_and_Consumer_ID(String consumerType, String consumerId) {
        waitFor(1);
        selectDropDown(manualContactRecordSearchPage.consumerTypeDrpDwn, consumerType);
        waitFor(1);
        if (consumerType.equalsIgnoreCase("Internal")) {
            clearAndFillText(manualContactRecordSearchPage.consumerIDField, consumerId);
        } else if (consumerType.equalsIgnoreCase("Medicaid/RID")) {
            clearAndFillText(manualContactRecordSearchPage.medAidconsumerIDField, consumerId);
            waitFor(2);
        }else if (consumerType.equalsIgnoreCase("Medicaid") || consumerType.equalsIgnoreCase("CHIP")){
            clearAndFillText(manualContactRecordSearchPage.externalConsumerIdSearch, consumerId);
            waitFor(2);
        }
        waitFor(1);
        contactRecord.searchButton.click();
        waitFor(1);
    }

    @Then("I see the column for Consumer ID with the correct value {string}")
    public void i_see_the_column_for_Consumer_ID_with_the_correct_value(String consumerId) {
        if (manualContactRecordSearchPage.consumerTypeDrpDwn.equals("Internal")) {
            assertTrue(manualContactRecordSearchPage.CRMConsumerID.isDisplayed());
        } else if (manualContactRecordSearchPage.consumerTypeDrpDwn.equals("Medicaid/RID")) {
            assertTrue(manualContactRecordSearchPage.MedicAidConsumerID.isDisplayed());
            waitFor(2);
        }
        assertEquals(manualCaseConsumerSearchPage.consumerIdTable.get(0).getText(), consumerId);
    }
}