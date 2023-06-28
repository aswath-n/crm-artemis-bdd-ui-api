package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_ThirdPartyContactRecStepDef extends CRMUtilities implements ApiBase {
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CRM_ViewContactRecordHistoryStepDef contactHistory = new CRM_ViewContactRecordHistoryStepDef();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMLinksComponentPage linksComponentPage = new CRMLinksComponentPage();
    CRM_CreateConsumerProfileStepDef createConsumerStepDef = new CRM_CreateConsumerProfileStepDef();
    Map<String, Object> newConsumer = new HashMap<>(getNewTestData2());
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactHistoryPage contactHistoryPage = new CRMContactHistoryPage();
    CRMThirdPartyContactRecPage contactRecordTP = new CRMThirdPartyContactRecPage();

    private static final ThreadLocal<String> savedConsumerID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> savedThirdPartyOrganizationName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> savedThirdPartyFirstName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> savedThirdPartyLastName = ThreadLocal.withInitial(String::new);

    public final ThreadLocal<String> updatedFirstName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedLastName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedOrgName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedConsumerType = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedPreferredLanguage = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> newReasonForEdit = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> updatedContactDisposition = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> randomizedThirtCHarFirstName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> randomizedThirtyCharLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> newLinkedConsumerId = ThreadLocal.withInitial(String::new);

    @Then("I verify that third party details header is displayed")
    public void verifyThirdPartyDetailsHeaderIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyConsumerInContact));
    }

    @Then("I verify that third party first name is displayed")
    public void verifyThirdPartyDetailsFirstNameIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyFirstName));
    }

    @Then("I verify that third party last name is displayed")
    public void verifyThirdPartyDetailsLastNameIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyFirstName));
    }

    @Then("I verify that third party organization name is displayed")
    public void verifyThirdPartyDetailsOrganizationNameIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.txtThirdPartyOrganizationName));
    }

    @Then("I verify that third party consumer drop down displayed with correct values")
    public void verifyConsumerTypeDropDownValues(List<String> expValues) {
        thirdPartyDetails.lstThirdPartyConsumerType.click();
        ArrayList<String> actList = new ArrayList<String>();
        ArrayList<String> expList = new ArrayList<String>();

        for (WebElement option : thirdPartyDetails.lstConsumerTypeValues) {
            if (option.getText().isEmpty())
                continue;
            actList.add(option.getText());
        }
        thirdPartyDetails.lstConsumerTypeValues.get(0).sendKeys(Keys.ESCAPE);
        for (String expValue : expValues) {
            expList.add(expValue);
        }

        Collections.sort(actList);
        Collections.sort(expList);
        Assert.assertEquals(actList, expList);
    }

    @Then("I verify that third party preferred language drop down displayed with correct values")
    public void verifyPreferredLanguageDropDownValues(List<String> expValues) {
        waitFor(2);
        thirdPartyDetails.lstThirdPartyPreferredLanguage.click();
        ArrayList<String> actList = new ArrayList<String>();
        ArrayList<String> expList = new ArrayList<String>();
        for (WebElement option : thirdPartyDetails.lstPreferredLanguageValues) {
            if (option.getText().isEmpty())
                continue;
            actList.add(option.getText());
        }
        thirdPartyDetails.lstPreferredLanguageValues.get(0).sendKeys(Keys.ESCAPE);
        for (String expValue : expValues) {
            expList.add(expValue);
        }
        Collections.sort(actList);
        Collections.sort(expList);
        Assert.assertEquals(actList, expList);
    }

    @When("I Enter mandatory Third Party Details {string}, {string}, {string}, {string} and {string}")
    public void enterThirdPartyDetails(String fname, String lname, String orgName, String consumerType, String preLanguage) {
        waitFor(3);
        scrollUpUsingActions(2);
        waitFor(2);
        waitForVisibility(thirdPartyDetails.thirdPartyFirstNameNew, 5);
        clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, fname);
        waitForVisibility(thirdPartyDetails.thirdPartyLastNameNew, 5);
        clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, lname);
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, orgName);
        selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, consumerType);
        waitFor(2);
        selectDropDown(thirdPartyDetails.lstThirdPartyPreferredLanguage, preLanguage);
    }

    @When("I Enter mandatory Third Party Details {string}, {string}, {string}, {string} and {string} and capture organization name")
    public void i_Enter_mandatory_Third_Party_Details_and_and_capture_organization_name(String fname, String lname, String orgName, String consumerType, String preLanguage) {
        waitFor(3);
        scrollUpUsingActions(2);
        waitFor(2);
        String _35CharsText = "fdsgdhfdhfdhgfhfdhfhfdhfhfhfhdfhfgf";
        if (fname.equalsIgnoreCase("random") && lname.equalsIgnoreCase("random") && orgName.equalsIgnoreCase("random")) {
            savedThirdPartyFirstName.set(getRandomString(8));
            savedThirdPartyLastName.set(getRandomString(8));
            savedThirdPartyOrganizationName.set(getRandomString(8));

            clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, _35CharsText);
            assertEquals(thirdPartyDetails.thirdPartyFirstNameNew.getText().length(), 0);
            clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, _35CharsText);
            assertEquals(thirdPartyDetails.thirdPartyLastNameNew.getText().length(), 0);
            clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, savedThirdPartyFirstName.get());
            clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, savedThirdPartyLastName.get());
            clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, savedThirdPartyOrganizationName.get());

        } else {
            clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, _35CharsText);
            assertEquals(thirdPartyDetails.thirdPartyFirstNameNew.getText().length(), 0);
            clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, _35CharsText);
            assertEquals(thirdPartyDetails.thirdPartyLastNameNew.getText().length(), 0);
            clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, fname);
            clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, lname);
            clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, orgName);
            savedThirdPartyOrganizationName.set(orgName);
            savedThirdPartyFirstName.set(fname);
            savedThirdPartyLastName.set(lname);
        }

        System.out.println("org name " + savedThirdPartyOrganizationName.get());
        System.out.println("third party first name " + savedThirdPartyFirstName.get());
        System.out.println("third party last name " + savedThirdPartyLastName.get());
        selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, consumerType);
        waitFor(2);
        selectDropDown(thirdPartyDetails.lstThirdPartyPreferredLanguage, preLanguage);
    }

    @When("I search for an existing contact record by Third Party Organization name under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_Third_Party_Organization_name_under_Advanced_Search() {
        manualContactRecordSearchPage._3rdPartyOrganizationNameAdvancedSearch.sendKeys(savedThirdPartyOrganizationName.get());
        waitFor(4);
        System.out.println("1");
        waitFor(3);
        manualCaseConsumerSearch.searchButton.click();
        System.out.println("2");
    }

    @When("I enter third party first name as {string}")
    public void enterFirstNameForThirdPartyDetails(String fname) {
        clearAndFillText(thirdPartyDetails.thirdPartyFirstNameNew, fname);
        waitFor(2);
    }

    @Then("I verify that first name is not accepted any letters other than alphabets")
    public void verifyFirstNameIsNotEntered() {
        String enteredValue = thirdPartyDetails.thirdPartyFirstNameNew.getAttribute("value");
        Assert.assertEquals(enteredValue, "He -ermione");
    }

    @Then("I verify that first name accepts maximum thirty characters")
    public void verifyFirstNameAccptsThirtyChars() {
        String enteredValue = thirdPartyDetails.thirdPartyFirstNameNew.getAttribute("value");
        Assert.assertEquals(enteredValue.length(), 30);
    }

    @When("I enter third party last name as {string}")
    public void enterLastNameForThirdPartyDetails(String lname) {
        waitFor(5);
        clearAndFillText(thirdPartyDetails.thirdPartyLastNameNew, lname);
        waitFor(2);
    }

    @Then("I verify that last name is not accepted any letters other than alphabets")
    public void verifyFirstNameIsNotAcceptedNumericsAndSpecialCharacters() {
        String enteredValue = thirdPartyDetails.txtThirdPartyLastName.getAttribute("value");
        Assert.assertEquals(enteredValue, "He -ermione");
    }

    @Then("I verify that last name accepts maximum thirty characters")
    public void verifyLastNameAccptsThirtyChars() {
        String enteredValue = thirdPartyDetails.thirdPartyLastNameNew.getAttribute("value");
        Assert.assertEquals(enteredValue.length(), 30);
    }

    @When("I enter third party organization name as {string}")
    public void enterOrgNameForThirdPartyDetails(String orgName) {
        waitFor(5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, orgName);
        waitFor(2);
    }

    @Then("I verify that organization name accepts only alphanumeric values")
    public void verifyOrganizationNameIsAcceptsOnlyAlphaNumerics() {
        String enteredValue = thirdPartyDetails.txtThirdPartyOrganizationName.getAttribute("value");
        Assert.assertEquals(enteredValue, "3He ermione");
    }

    @Then("I verify that organization name accepts maximum fifty characters")
    public void verifyOrganizationFieldAcceptsMaxFiftyChracters() {
        String enteredValue = thirdPartyDetails.txtThirdPartyOrganizationName.getAttribute("value");
        Assert.assertEquals(enteredValue.length(), 50);
    }

    @Then("I verify that following dropdown options for Inbound Queue Call field are displayed")
    public void verifyInboundQueueCallDropDownValues(List<String> expValues) {
        waitFor(2);
        contactRecord.callQueueType.click();
        verifyDropDownValues(contactRecord.callQueueTypeDropDownValues, expValues);
        waitFor(2);
    }


    @Then("I verify that following dropdown options for Call Campaign Reference field are displayed")
    public void verifyCallCampaignReferenceDropDownValues(List<String> expValues) {
        waitFor(2);
        contactRecord.callCampaignDropDown.click();
        verifyDropDownValues(contactRecord.callCampaignReferenceDropDownValues, expValues);

    }

    @Then("I verify that following dropdown options for Outcome Of Contact field are displayed")
    public void verifyOutcomeOfContactDropDownValues(List<String> expValues) {
        waitFor(2);
        contactRecord.outcomeOfContact.click();
        verifyDropDownValues(contactRecord.outcomeOfContactDropDownValues, expValues);

    }

    private void verifyDropDownValues(List<WebElement> elementForListOfValues, List<String> expValues) {
        ArrayList<String> actList = new ArrayList<String>();
        ArrayList<String> expList = new ArrayList<String>();

        for (WebElement option : elementForListOfValues) {
            if (option.getText().isEmpty())
                continue;
            actList.add(option.getText());
        }
        elementForListOfValues.get(0).sendKeys(Keys.ESCAPE);
        for (String expValue : expValues) {
            expList.add(expValue);
        }

        Collections.sort(actList);
        Collections.sort(expList);
        Assert.assertEquals(actList, expList);
        waitFor(2);
    }

    @When("I select contact channel type as {string}")
    public void selectContactDetails(String contachChannelType) {
        waitFor(2);
        selectDropDown(contactRecord.contactChannelType, contachChannelType);
    }

    @Then("I verify contact phone number filed is displayed")
    public void verifyPhoneNumberFieldDisplayed() {
        waitFor(2);
        Assert.assertTrue(isElementDisplayed(contactRecord.phoneNumber));
    }

    @Then("I verify contact email filed is displayed")
    public void verifyEmailFieldDisplayed() {
        waitFor(2);
        Assert.assertTrue(isElementDisplayed(contactRecord.email));
    }

    @Then("I verify contact phone number filed is not displayed")
    public void verifyPhoneNumberFieldNotDisplayed() {
        waitFor(2);
        Assert.assertTrue(!isElementDisplayed(contactRecord.phoneNumber));
    }

    @Then("I verify contact email filed is not displayed")
    public void verifyEmailFieldNotDisplayed() {
        waitFor(2);
        Assert.assertTrue(!isElementDisplayed(contactRecord.email));
    }

    @Then("I navigate to case details tab")
    public void i_navigate_to_case_details_tab() {
        waitFor(3);
        contactRecordDashboard.caseContactDetailsTab.click();
        scrollDownUsingActions(4);
        waitFor(5);
    }

    @Then("I navigate to active contact tab")
    public void i_navigate_to_active_contact_tab() {
        contactRecordDashboard.activeContactTab.click();
    }

    @When("I enter contact email as {string}")
    public void enterEmail(String email) {
        waitFor(3);
        clearAndFillText(contactRecord.email, email);
    }

    @When("I update third party contact details {string}, {string} and {string}")
    public void i_update_third_party_contact_details_and(String consumerType, String preLanguage, String reasonForEdit) {
        updatedFirstName.set(RandomStringUtils.randomAlphabetic(5));
        updatedLastName.set(RandomStringUtils.randomAlphabetic(5));
        updatedOrgName.set(RandomStringUtils.randomAlphabetic(5));
        clearAndFillText(thirdPartyDetails.editThirdPartyFirstName, updatedFirstName.get());
        clearAndFillText(thirdPartyDetails.editThirdPartyLastName, updatedLastName.get());
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, updatedOrgName.get());
        updatedConsumerType.set(consumerType);
        selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, consumerType);
        waitFor(2);
        updatedPreferredLanguage.set(preLanguage);
        selectDropDown(thirdPartyDetails.lstThirdPartyPreferredLanguage, preLanguage);
        waitFor(2);
        newReasonForEdit.set(reasonForEdit);
        selectDropDown(contactRecord.contactRecordReasonForEdit, reasonForEdit);
    }

    @When("I click on save button in edit third party contact record")
    public void clickSave() {
        waitFor(3);
        thirdPartyDetails.savebtnEditPage.click();
    }

    @Then("I verify that the displayed details match with updated details")
    public void verifyThirdPartyRecordDetailsAreMatching() {
        waitFor(2);
        contactHistory.verifyThirdPartyRecordDetailsAreMatching(updatedFirstName.get(), updatedLastName.get(), updatedOrgName.get(), updatedConsumerType.get(), updatedPreferredLanguage.get());

    }

    @When("I update third party contact disposition details {string} and {string}")
    public void updateContactDisposition(String contactDisposition, String reasonForEdit) {

        waitFor(2);
        updatedContactDisposition.set(contactDisposition);
        selectDropDown(thirdPartyDetails.lstContactDisposition, updatedContactDisposition.get());
        waitFor(2);
        newReasonForEdit.set(reasonForEdit);
        selectDropDown(thirdPartyDetails.lstReasonForEdit, reasonForEdit);
    }

    @Then("I verify updated contact disposition and reason for edit of third party contact record")
    public void verifyContactDisposition() {
        waitFor(2);
        contactHistory.verifyContactDispositionAndReasonForEdit(updatedContactDisposition.get(), newReasonForEdit.get());


    }

    @Then("I verify wrap up and close section is displayed after contact details section")
    public void verifyWrapUpAndCloseSectionDisplayedAfterContactDetails() {
        scrollDownUsingActions(1);
        waitForVisibility(thirdPartyDetails.lblThirdPartyWrapUpClose, 5);
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyWrapUpClose));

    }

    @Then("I verify sections displayed in third party contact record")
    public void verifySectionsDisplayed() {
        waitFor(2);
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyConsumerInContact), "Third Party Consumer in Contact didnt show up");
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyConsumerSearch), "Third Party Case Consumer Search didnt show up");
        scrollDownUsingActions(2);
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyContactDetails), "Third Party Contact Details didnt show up");
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyWrapUpClose), "Third Party Wrap-Up didnt show up");

    }

    @Then("I click on third party contact record type radio button")
    public void clickContactRecordType() {
        waitForClickablility(thirdPartyDetails.thirdPartyRecordTypeRadio, 10);
        waitFor(1);
        scrollToElement(thirdPartyDetails.thirdPartyRecordTypeRadio);
        scrollUpUsingActions(1);
        jsClick(thirdPartyDetails.thirdPartyRecordTypeRadio);

    }

    @Then("I verify sections displayed in third party contact record after link")
    public void verifySectionsDisplayedAfterLinkConsumer() {
        waitFor(2);
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyConsumerInContact));
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyConsumerContactedAbout));
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyContactDetails));
        assertTrue(isElementDisplayed(thirdPartyDetails.lblThirdPartyWrapUpClose));

    }

    @Then("I verify that following dropdown options for Inbount Queue Call field are displayed")
    public void verifyInboundCallQueDropDownValues(List<String> expValues) {
        waitFor(3);
        waitForClickablility(thirdPartyDetails.lstInboundCallQueue, 10);
        click(thirdPartyDetails.lstInboundCallQueue);
        waitFor(5);
        ArrayList<String> actList = new ArrayList<String>();
        ArrayList<String> expList = new ArrayList<String>();

        for (WebElement option : thirdPartyDetails.callQueueTypeListValues) {
            if (option.getText().isEmpty())
                continue;
            actList.add(option.getText());
        }
        thirdPartyDetails.callQueueTypeListValues.get(0).sendKeys(Keys.ESCAPE);
        for (String expValue : expValues) {
            expList.add(expValue);
        }

        Collections.sort(actList);
        Collections.sort(expList);
        Assert.assertEquals(actList, expList);
    }

    @When("I verify that third party details header is changed as {string}")
    public void i_verify_that_third_party_details_header_is_changed_as(String headerOfThirdParty) {
        Assert.assertTrue(thirdPartyDetails.lblThirdPartyConsumerInContact.getText().contains(headerOfThirdParty), headerOfThirdParty);

    }

    @When("I verify the link icon at the third party contact record page")
    public void i_verify_the_link_icon_at_the_third_party_contact_record_page() {
        scrollDownUsingActions(4);
        assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinksHeader));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinksHeader.getText().contains("LINKS"), "LINKS");
        assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinksIcon));
    }

    @When("I add New Consumer to the record for third party")
    public void i_add_New_Consumer_to_the_record_for_third_party() {
        System.out.println(newConsumer.get("name").toString() + " " + newConsumer.get("surname").toString());
        waitFor(4);
        contactRecord.thirdPartyConsumerFistNameSearch.sendKeys((newConsumer.get("name").toString()));
        waitFor(6);
        contactRecord.searchButton.click();
        waitFor(2);
        assertTrue(createConsumer.addConsumerButtonNew.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButtonNew.click();
        waitFor(3);
        //  scrollUpUsingActions(3); failing because scrollUp function
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        waitFor(3);
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerLN, newConsumer.get("surname").toString());
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");

        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get("zip").toString()));
        waitFor(3);
        createConsumerStepDef.i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
        waitFor(1);
        savedConsumerID.set(activeContact.consumerID.getText());
        System.out.println("Saved consumer ID:  " + savedConsumerID.get());
        waitFor(4);

    }

    @When("I add New Consumer to the record for the third party and it is automatically linked")
    public void i_add_New_Consumer_to_the_record_for_the_third_party_and_it_is_automatically_linked() {
        System.out.println(newConsumer.get("name").toString() + " " + newConsumer.get("surname").toString());
        clearAndFillText(contactRecord.thirdPartyConsumerFistNameSearch, (newConsumer.get("name").toString()));
        contactRecord.searchButton.click();
        waitFor(2);
        assertTrue(createConsumer.addConsumerButtonNew.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(5);
        scrollUpUsingActions(6);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerLN, newConsumer.get("surname").toString());

        clearAndFillText(createConsumer.consumerDB, (newConsumer.get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");

        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get("zip").toString()));
        createConsumerStepDef.i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
        savedConsumerID.set(activeContact.consumerID.getText());
        System.out.println("Saved consumer ID:  " + savedConsumerID.get());
    }

    @When("I verify that Links Component has uniqueID, name equals to {string}, type equals to {string}, status date and status are present")
    public void i_verify_that_Links_Component_has_uniqueID_name_equals_to_type_equals_to_status_date_is_present_and_status_is_not_present(String nameField, String typeField) {
        Assert.assertEquals(thirdPartyDetails.thirdPartyLinkedCaseId.getText(), savedConsumerID.get());
        assertEquals(linksComponentPage.idField.getText(), contactRecord.caseId.getText());
        System.out.println(linksComponentPage.idField.getText());
        assertEquals(linksComponentPage.nameFieldForConsumer.getText(), nameField);
        System.out.println(linksComponentPage.nameFieldForConsumer.getText());
        assertTrue(linksComponentPage.consumerStatusDateField.isDisplayed());
        System.out.println(linksComponentPage.consumerStatusDateField.getText());
        assertEquals(linksComponentPage.typeFieldForConsumer.getText(), typeField);
        System.out.println(linksComponentPage.typeFieldForConsumer.getText());
        assertTrue(linksComponentPage.consumerStatusField.isDisplayed());
    }


    @When("I search for a consumer that I just created for third party and link the consumer")
    public void i_search_for_a_consumer_that_I_just_created_for_third_party_and_link_the_consumer() {
        clearAndFillText(contactRecord.thirdPartyConsumerFistNameSearch, (newConsumer.get("name").toString()));
        System.out.println("++++++++++++");
        System.out.println(newConsumer.get("name"));
//        clearAndFillText(contactRecord.lastName, (newConsumer.get("surname").toString()));
        contactRecord.searchButton.click();

        contactRecord.expandFistConsumer.click();
        waitFor(2);
        thirdPartyDetails.thirdPartyConsumerRadioButton.click();
        thirdPartyDetails.thirdPartyLinkConsumerdButton.click();
        scrollDown();
    }


    @When("I verify that new header for case consumer is added")
    public void i_verify_that_new_header_for_case_consumer_is_added() {
        assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyCaseConsumerHeader));
        Assert.assertTrue(thirdPartyDetails.thirdPartyCaseConsumerHeader.getText().contains("CASE / CONSUMER SEARCH"));
    }

    @Then("I will verify that linked Case or Consumer Profile added to link section")
    public void i_will_verify_that_linked_Case_or_Consumer_Profile_added_to_link_section() {
        Assert.assertEquals(thirdPartyDetails.thirdPartyLinkCaseId.getText(), savedConsumerID.get());
    }

    @Then("I will verify that {string} section is not displayed on Third Party Contact Record")
    public void i_will_verify_that_section_is_not_displayed_on_Third_Party_Contact_Record(String header) {
        Assert.assertFalse(thirdPartyDetails.thirdPartyAllPage.getText().contains(header));
    }

    @Then("I will verify that {string} section is displayed")
    public void i_will_verify_that_section_is_displayed(String header) {
        Assert.assertTrue(thirdPartyDetails.thirdPartyConsumerAboutHeader.isDisplayed());
        Assert.assertTrue(thirdPartyDetails.thirdPartyConsumerAboutHeader.getText().contains(header));
    }

    @Then("I verify that after linking the consumer links section is added and has an icon consistent with other sections")
    public void i_verify_that_after_linking_the_consumer_links_section_is_added_and_has_an_icon_consistent_with_other_sections() {
        scrollDownUsingActions(2);
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkID));

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkName));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkName.getText().contains("NAME"), "NAME not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkType));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkType.getText().contains("TYPE"), "TYPE not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkStatusDate));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkStatusDate.getText().contains("STATUS DATE"), "STATUS DATE not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkStatus));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkStatus.getText().contains("STATUS"), "STATUS not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyConsumerProfile));
        Assert.assertTrue(thirdPartyDetails.thirdPartyConsumerProfile.getText().contains("Consumer Profile"), "Consumer Profile not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkConsumer));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkConsumer.getText().contains("Consumer"), "Consumer");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkDateTime));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkDateTime.getText().contains(getCurrentDate()), getCurrentDate()+ " not found in third party link");

        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyLinkStatusField));
        Assert.assertTrue(thirdPartyDetails.thirdPartyLinkStatusField.getText().contains("Active"), "Active not found in third party link");
    }

    @When("I verify that there are three new Radio Buttons at the top of the head")
    public void i_verify_that_there_are_three_new_Radio_Buttons_at_the_top_of_the_head() {
        Assert.assertTrue(isElementDisplayed(thirdPartyDetails.thirdPartyGeneralContactHeader));
        Assert.assertEquals(thirdPartyDetails.thirdPartyGeneralContactHeader.getText(), "GENERAL CONTACT");
        Assert.assertEquals(thirdPartyDetails.thirdPartyThirdPartyHeader.getText(), "THIRD PARTY CONTACT");
        Assert.assertEquals(thirdPartyDetails.thirdPartyUnidentifiedContactHeader.getText(), "UNIDENTIFIED CONTACT");
    }

    @When("I will verify that {string} section is added")
    public void i_will_verify_that_section_is_added(String header) {
        System.out.println(thirdPartyDetails.thirdPartyCaseConsumerHeader.getAttribute("innerHTML"));
        Assert.assertTrue(thirdPartyDetails.thirdPartyCaseConsumerHeader.getAttribute("innerHTML").contains("CASE / CONSUMER SEARCH"));

    }

    @Then("I verify the program {string} field that captures during saving the contact record")
    public void i_verify_the_program_field_that_captures_during_saving_the_contact_record(String program) {
        Assert.assertEquals(activeContact.programTypes.getText(), program);
        System.out.println(program + "  is selected for third party");
    }

    @Then("I verify programs {string} and {string} fields are captured")
    public void i_verify_programs_and_fields_are_captured(String program1, String program2) {
        Assert.assertEquals(activeContact.programTypes.getText().replace("\n", ","), program1 + "," + program2);
        System.out.println(program1 + "  " + program2 + "  is selected for third party");

    }

    @And("I select random third party consumer type")
    public void iSelectRandomThirdPartyConsumerType() {
        selectRandomOptionFromMultiSelectDropDown(thirdPartyDetails.lstThirdPartyConsumerType);
    }

    @When("I search for an existing contact record by Third Party First Name & Last Name under Advanced Search")
    public void i_search_for_an_existing_contact_record_by_Third_Party_First_Name_Last_Name_under_Advanced_Search() {
        manualContactRecordSearchPage._3rdPartyFistNameAdvancedSearch.sendKeys(savedThirdPartyFirstName.get());
        manualContactRecordSearchPage._3rdPartyLastNameAdvancedSearch.sendKeys(savedThirdPartyLastName.get());

        waitFor(4);
        manualCaseConsumerSearch.searchButton.click();

    }

    @Then("I search for the Third Party {string} and {string} in the advance search parameter")
    public void iSearchForTheThirdPartyAndInTheAdvanceSearchParameter(String firstName, String lastName) {
        if (firstName.equalsIgnoreCase("random30")) {
            firstName = randomizedThirtCHarFirstName.get();
        }
        if (lastName.equalsIgnoreCase("random30")) {
            lastName = randomizedThirtyCharLastName.get();
        }
        manualContactRecordSearchPage.advancedSearch.click();
        waitFor(1);
        manualContactRecordSearchPage.thirdPtyFirstName.sendKeys(firstName);
        waitFor(1);
        manualContactRecordSearchPage.thirdPtyLastName.sendKeys(lastName);
        waitFor(1);
        contactRecord.searchButton.click();
    }

    @And("I should be able to view Third party {string} and {string} as part of in the expanded view")
    public void iShouldBeAbleToViewThirdPartyAndAsPartOfInTheExpandedView(String firstName, String lastName) {
        if (firstName.equalsIgnoreCase("random30")) {
            firstName = randomizedThirtCHarFirstName.get();
        }
        if (lastName.equalsIgnoreCase("random30")) {
            lastName = randomizedThirtyCharLastName.get();
        }
        manualContactRecordSearchPage.expandFieldButton.click();
        waitFor(2);
        String actualName = manualContactRecordSearchPage.thirdPtyNameValue.getText().replaceAll(" ", "").toLowerCase();
        String fullName = (firstName + lastName).toLowerCase();
        Assert.assertEquals(actualName, fullName, "Expected and actual fullname does not match in the expanded view");
        Assert.assertEquals(actualName.length(), 60, "Combined first and last name is 60 chars; actual chars: " + actualName.length());
    }

    @And("I Enter Thirty character Third Party  {string}, {string} with mandatory {string}, {string} and {string}")
    public void iEnterThirtyCharacterThirdPartyWithMandatoryAnd(String fname, String lname, String orgName, String consumerType, String preLanguage) {
        if (fname.equalsIgnoreCase("random30")) {
            fname = getRandomString(30);
            randomizedThirtCHarFirstName.set(fname);
        }
        if (lname.equalsIgnoreCase("random30")) {
            lname = getRandomString(30);
            randomizedThirtyCharLastName.set(lname);
        }
        waitFor(3);
        scrollUpUsingActions(2);
        waitFor(2);
        waitForVisibility(thirdPartyDetails.txtThirdPartyFirstName, 5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyFirstName, fname);
        waitForVisibility(thirdPartyDetails.txtThirdPartyLastName, 5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyLastName, lname);
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, orgName);
        selectDropDown(thirdPartyDetails.lstThirdPartyConsumerType, consumerType);
        waitFor(2);
        selectDropDown(thirdPartyDetails.lstThirdPartyPreferredLanguage, preLanguage);
    }

    @And("I verify Field Label has value Consumer ID with previous value updated value")
    public void iVerifyFieldLabelHasValueConsumerIdWithPreviousValueUpdatedValue() {
        for (int i = 0; i < contactHistoryPage.fieldLabelList.size(); i++) {
            if (contactHistoryPage.fieldLabelList.get(i).getText().equalsIgnoreCase("Consumer ID")) {
                assertEquals(contactHistoryPage.fieldPreValList.get(i).getText(), savedConsumerID.get(), "Previous Consumer ID is INCORRECT");
                assertEquals(contactHistoryPage.fieldUpdValList.get(i).getText(), newLinkedConsumerId.get(), "Updated Consumer ID is INCORRECT");
            }
        }
    }

    @And("I unlink existing consumer and linking consumer fn {string} and ln {string}")
    public void andIUnlinkExistingConsumerAndLinkingConsumerFnAndLn(String fn, String ln) {
        waitFor(3);
        manualContactRecordSearchPage.unlinkContactRecord.click();
        waitFor(1);
        clearAndFillText(manualContactRecordSearchPage.afterthirdPartyFrstName, fn);
        clearAndFillText(manualContactRecordSearchPage.afterthirdPartyLstName, ln);
        manualContactRecordSearchPage.searchButton.click();
        waitFor(3);
        newLinkedConsumerId.set(manualContactRecordSearchPage.newLinkConId.getText());
        manualContactRecordSearchPage.expandFrstRecord.click();
        manualContactRecordSearchPage.primaryIndividualRadioButton.click();
        waitFor(1);
        manualContactRecordSearchPage.linkCaseAndConsumer.click();
        waitFor(2);
    }

    @Then("I am able to view the completed {string} third party Contact Record")
    public void ableToViewTheThirdPartyContactRecord(String contactType) {
        assertTrue(contactRecordTP.thirdPartyFN.getText().equalsIgnoreCase(savedThirdPartyFirstName.get()));
        assertTrue(contactRecordTP.thirdPartyLN.getText().equalsIgnoreCase(savedThirdPartyLastName.get()));
        assertTrue(contactRecordTP.thirdPartyOrg.getText().equalsIgnoreCase(savedThirdPartyOrganizationName.get()));
        assertEquals(contactRecordTP.thirdPartyType.getText(), "Agency");
        assertEquals(contactRecordTP.thirdPartyPrefLang.getText(), "English");
        assertTrue(verifyDateFormat(contactHistoryPage.lblContactStart));
        assertTrue(verifyDateFormat(contactHistoryPage.lblContactEnd));
        assertEquals(contactRecord.contactRecordContactType.getText(), contactType);
        assertTrue(contactRecord.contactRecordConsumerName.getText().contains((newConsumer.get("name").toString())));
        assertEquals(contactRecord.contactRecordContactChannel.getText(), "Phone");
        assertEquals(contactRecord.contactRecordPrefLanguage.getText(), "English");
    }

    @And("I search for the created Case Consumer in the Third Party tab With {string}")
    public void iSearchForTheCreatedCaseConsumerInTheThirdPartyTabWith(String searchType) {
        switch (searchType) {
            case "First and Last names":
                String firstName = APIConsumerPopulationDmnController.consumerFirstName.get();
                String lastName = APIConsumerPopulationDmnController.consumerLastName.get();
                clearAndFillText(contactRecord.thirdPartyConsumerFistNameSearch, firstName);
                waitFor(2);
                clearAndFillText(contactRecord.thirdPartyConsumerLastNameSearch, lastName);
                contactRecord.searchButton.click();
                break;
            case "SSN":
                String ssn = APIConsumerPopulationDmnController.consumerSSN.get();
                clearAndFillText(contactRecord.ssnSearch, ssn);
                contactRecord.searchButton.click();
                break;
            case "HARRY POTTER":
                clearAndFillText(contactRecord.thirdPartyConsumerFistNameSearch, "Harry");
                waitFor(2);
                clearAndFillText(contactRecord.thirdPartyConsumerLastNameSearch, "Potter");
                contactRecord.searchButton.click();
                break;
            default:
                fail("Mismatch in expected search type");
        }
    }

    @And("I link the {string} consumer searched from the third party tab to the active contact")
    public void iLinkTheConsumerSearchedFromTheThirdPartyTabToTheActiveContact(String type) {
        waitFor(4);
        switch (type) {
            case "First":
                contactRecord.listOfChevronRight.get(0).click();
                contactRecord.primaryIndividualRadioButton.get(0).click();
                waitFor(1);
                contactRecord.linkCaseConsumerButton.click();
                break;
            default:
                fail("Mismatch in expected search type");
        }
    }

    @Then("I verify the Case and Consumer Ids are displayed after the third party link to contact record")
    public void iVerifyTheCaseAndConsumerIdsAreDisplayedAfterTheThirdPartyLinkToContactRecord() {
        waitFor(4);
        assertEquals(contactRecord.consumerContactedAboutListData.get(0).getText(),APIConsumerPopulationDmnController.caseId.get(), "Mismatch in expected Case Id");
        assertEquals(contactRecord.consumerContactedAboutListData.get(1).getText(), APIConsumerPopulationDmnController.consumerId.get(), "Mismatch in expected Consumer Id");
    }

    @And("I verify Third Party tab dropdown {string} displays the following values")
    public void iVerifyThirdPartyTabDropdownDisplaysTheFollowingValues(String type, List<String> data) {
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        data.forEach(each -> expected.add(each));
        switch (type) {
            case "CONTACT TYPE":
                contactRecord.contactType.click();
                contactRecord.dropdownselectvalues.forEach(each -> actual.add(each.getText()));
                collectionSort(expected, actual);
                assertEquals(actual, expected, "Mismatch in expected List of values");
                break;
            case "contact chanel":
                contactRecord.contactChannelType.click();
                contactRecord.dropdownselectvalues.forEach(each -> actual.add(each.getText()));
                collectionSort(expected, actual);
                assertEquals(actual, expected, "Mismatch in expected List of values");
                break;
        }
    }

    public void collectionSort(List<String> expected, List<String> actual) {
        Collections.sort(expected);
        Collections.sort(actual);
    }
}
