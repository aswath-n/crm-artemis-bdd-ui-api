package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APICaseLoaderEligibilityEnrollmentController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.pages.tm.TMAddNewUserPage;
import com.maersk.crm.pages.tm.TMAddTeamPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CRM_INEBCreateConsumer extends CRMUtilities implements ApiBase {
    CRMUnidentifiedContactPage  unidentifiedContactPage = new  CRMUnidentifiedContactPage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMAddCaseMemberPage addCaseMemberPage = new CRMAddCaseMemberPage();
    TMAddNewUserPage addNewUserPage = new TMAddNewUserPage();
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();

    @Then("I see all fields on IN-EB Create Consumer present")
    public void iSeeAllFieldsOnINEBCreateConsumerPresent() {
        assertTrue(createConsumer.consumerFN.isDisplayed(), "First name field is not displayed");
        assertTrue(createConsumer.consumerLN.isDisplayed(), "Last name field is not displayed");
        assertTrue(createConsumer.consumerDB.isDisplayed(), "Date of birth field is not displayed");
        assertTrue(createConsumer.consumerSSN.isDisplayed(), "SSN field is not displayed");
        assertTrue(createConsumer.consumerPhoneNum.isDisplayed(), "Phone number field is not displayed");
        assertTrue(createConsumer.consumerGender.isDisplayed(), "Gender dropdown field is not displayed");
        assertTrue(createConsumer.consumerAddressType.isDisplayed(), "Address type field is not displayed");
        assertTrue(createConsumer.consumerAddress1.isDisplayed(), "Address 1 field is not displayed");
        assertTrue(createConsumer.consumerAddress2.isDisplayed(), "Address 2 field is not displayed");
        assertTrue(createConsumer.consumerCityDropdown.isDisplayed(), "City dropdown field is not displayed");
        assertTrue(createConsumer.consumerCountyField.isDisplayed(), "County dropdown field is not displayed");
        assertTrue(createConsumer.consumerStateField.isDisplayed(), "State dropdown field is not displayed");
        assertTrue(createConsumer.consumerZipCode.isDisplayed(), "Zip code field is not displayed");
        assertTrue(createConsumer.cancelConsumerButton.isDisplayed(), "Cancel button is not displayed");
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Create Consumer button is not displayed");
    }

    @Then("I don't see unexpected {string} on IN-EB Create Consumer present")
    public void iDonTSeeUnexpectedOnINEBCreateConsumerPresent(String string, List<String> options) {
        for (String option : options) {
            switch (option) {
                case "MI":
                    assertTrue(IsElementDisplayed(createConsumer.consumerMI), option + " field should not be displayed");
                    break;
                case "Email":
                    assertTrue(IsElementDisplayed(createConsumer.consumerEmail), option + " field should not be displayed");
                    break;
                case "Correspondence preference":
                    assertTrue(IsElementDisplayed(createConsumer.correspondencePreferenceDropDown), option + " field should not be displayed");
                    break;
                case "optInOut":
                    assertTrue(IsElementDisplayed(createConsumer.commOptMail), option + " field should not be displayed");
                    assertTrue(IsElementDisplayed(createConsumer.commOptEmail), option + " field should not be displayed");
                    assertTrue(IsElementDisplayed(createConsumer.commOptText), option + " field should not be displayed");
                    assertTrue(IsElementDisplayed(createConsumer.commOptFax), option + " field should not be displayed");
                    assertTrue(IsElementDisplayed(createConsumer.commOptPhone), option + " field should not be displayed");
                    break;
                case "Written Language":
                    assertTrue(IsElementDisplayed(createConsumer.writtenLanguage), option + " field should not be displayed");
                    break;
                case "Spoken Language":
                    assertTrue(IsElementDisplayed(createConsumer.spokenLanguage), option + " field should not be displayed");
                    break;
                case "Ethnicity":
                    assertTrue(IsElementDisplayed(createConsumer.ethnicityConsumerNotONaCase), option + " field should not be displayed");
                    break;
                case "Race":
                    assertTrue(IsElementDisplayed(createConsumer.ethnicityConsumerNotONaCase), option + " field should not be displayed");
                    break;
                case "Citizenship":
                    assertTrue(IsElementDisplayed(createConsumer.citizenshipConsumerNotONaCase), option + " field should not be displayed");
                    break;
                case "American Indian or Alaska Native (checkbox)":
                    assertTrue(IsElementDisplayed(createConsumer.AmericanIndianorAlaskaNativeCheckBox), option + " field should not be displayed");
                    break;
            }
        }
    }


    @Then("I don't see unexpected {string} option should not be a Phone Type on IN-EB Create Consumer present")
    public void iDonTSeeUnexpectedOptionShouldNotBeAPhoneTypeOnINEBCreateConsumerPresent(String type) throws Throwable {
        boolean notDisplayed = false;
        try {
            selectDropDown(createConsumer.phoneType, type);
        } catch (NoSuchElementException e) {
            notDisplayed = true;
        }
        assertTrue(notDisplayed, "Phone Type " + type + " should not be displayed");
    }

    @Then("I verify Do Not Call field label with checkbox is displayed")
    public void i_verify_Do_Not_Call_field_label_with_checkbox_is_displayed() {
        waitFor(2);
        assertTrue(createConsumer.doNotCall.isDisplayed());
        assertTrue(createConsumer.doNotCallCheckBox.isEnabled());
    }

    @And("I verify {string} checkbox is not displayed and the consumer is inactive")
    public void i_verify_checkbox_is_not_displayed_and_the_consumer_is_inactive(String string) {
        assertTrue(createConsumer.InactiveStatus.getText().equalsIgnoreCase("INACTIVE"));
        assertTrue(!isElementDisplayed(addCaseMemberPage.checkboxLabelInactive));
    }

    @Then("I verify {string} checkbox is not displayed and the status is inactive")
    public void i_verify_checkbox_is_not_displayed_and_the_status_is_inactive(String string) {
        assertTrue(addNewUserPage.inactiveStatus.getText().equalsIgnoreCase("INACTIVE"));
        assertTrue(!isElementDisplayed(addCaseMemberPage.checkboxLabelInactive));
    }

    public List<String> getOptionsFor(WebElement e) {
        List<String> strOptions;
        Actions action = new Actions(Driver.getDriver());
        e.click();
        strOptions = convertToListStr(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
        strOptions.remove(0);
        waitFor(1);
        System.out.println(strOptions.toString()+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return strOptions;
    }

    public List<String> getOptionsForWithoutDeselect(WebElement e) {
        List<String> strOptions;
        Actions action = new Actions(Driver.getDriver());
        e.click();
        strOptions = convertToListStr(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
        waitFor(1);
        System.out.println(strOptions.toString()+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return strOptions;
    }


    @Then("I see {string} field has all expected options on IN-EB")
    public void iSeeFieldHasAllExpectedOptionsOnINEB(String field) {
        List<String> actualOptions = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        switch (field) {
            case "Address Source":
                actualOptions = getOptionsFor(createConsumer.addressSourceDropdown);
                expected = inebAddressSourceOptions();
                actualOptions.forEach(System.out::println);
                break;
            case "City":
                actualOptions = getOptionsForWithoutDeselect(createConsumer.consumerCityDropdown);
                expected = inebCityOptions();
                actualOptions.forEach(System.out::println);
                break;
            case "County":
                actualOptions = getOptionsFor(createConsumer.consumerCountyField);
                expected = inebCountyOptions();
                actualOptions.forEach(System.out::println);
                break;
            case "searchCounty":
                actualOptions = getOptionsFor(contactRecord.county);
                actualOptions.forEach(System.out::println);
                expected = inebCountyOptions();
                break;
            case "searchCity":
                actualOptions = getOptionsFor(contactRecord.city);
                actualOptions.forEach(System.out::println);
                expected = inebCityOptions();
                break;
        }
        assertTrue(actualOptions.equals(expected), field + " Options are not matching expected");
    }

    @Then("I don't see unexpected {string} on IN-EB search consumer component")
    public void iDonTSeeUnexpectedOnINEBSearchConsumerComponent(String string, List<String> options) {
        for (String option : options) {
            switch (option) {
                case "MI":
                    assertTrue(IsElementDisplayed(contactRecord.middleInitialSearch), option + " field should not be displayed");
                    break;
                case "Email":
                    assertTrue(IsElementDisplayed(contactRecord.consumerEmail), option + " field should not be displayed");
                    break;
            }
        }
    }

    @Then("I see expected {string} on IN-EB search consumer component")
    public void iSeeExpectedOnINEBSearchConsumerComponent(String option) {
        switch (option) {
            case "field":
                assertTrue(contactRecord.firstName.isDisplayed(), "First name field is not displayed");
                assertTrue(contactRecord.lastName.isDisplayed(), "Last name field is not displayed");
                assertTrue(contactRecord.dobSearch.isDisplayed(), "Date of birth field is not displayed");
                assertTrue(contactRecord.ssnSearch.isDisplayed(), "SSN field is not displayed");
                assertTrue(contactRecord.caseConsumerSearch.isDisplayed(), "Case Id field is not displayed");
                assertTrue(contactRecord.searchCaseOptions.isDisplayed(), "Case Type options field is not displayed");
                assertTrue(contactRecord.searchConsumerOptions.isDisplayed(), "Consumer Type options field is not displayed");
                assertTrue(contactRecord.searchConsumerID.isDisplayed(), "Consumer Id field is not displayed");
                break;
            case "advanced field":
                assertTrue(contactRecord.phoneNumber.isDisplayed(), "Phone number field is not displayed");
                assertTrue(contactRecord.address1.isDisplayed(), "Address 1 field is not displayed");
                assertTrue(contactRecord.address2.isDisplayed(), "Address 2 field is not displayed");
                assertTrue(contactRecord.city.isDisplayed(), "City dropdown field is not displayed");
                assertTrue(contactRecord.county.isDisplayed(), "County dropdown field is not displayed");
                assertTrue(contactRecord.state.isDisplayed(), "State dropdown field is not displayed");
                assertTrue(contactRecord.zipCode.isDisplayed(), "Zip code field is not displayed");
                break;
        }
    }


    @Then("I validate search result component has expected {string}")
    public void iValidateSearchResultComponentHasExpectedColumns(String string, List<String> options) throws Throwable {
        List<String> actualHeaderValues = new ArrayList<>();
        String headerName = "";
        contactRecord.expandButtonforLink.click();
        waitFor(2);
        for (int i = 0; i < (contactRecord.listOfSearchResultHeaders.size()); i++) {
            if (contactRecord.listOfSearchResultHeaders.get(i).getText().endsWith("DATE OF BIRTH")) {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText().substring(11, 24);
                actualHeaderValues.add(i, headerName);
            } else if (contactRecord.listOfSearchResultHeaders.get(i).getText().endsWith("SSN")) {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText().substring(11, 14);
                actualHeaderValues.add(i, headerName);
            } else {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText();
                actualHeaderValues.add(i, headerName);
            }
        }
        for (String option : options) {
            assertTrue(actualHeaderValues.contains(option), option + " Column header is not displayed");
        }

    }

    @Then("I validate search result component has does not display unexpected {string}")
    public void iValidateSearchResultComponentHasDoesNotDisplayUnexpectedColumns(String string, List<String> options) {
        List<String> actualHeaderValues = new ArrayList<>();
        for (int i = 0; i < (contactRecord.listOfSearchResultHeaders.size()); i++) {
            String headerName = contactRecord.listOfSearchResultHeaders.get(i).getText();
            actualHeaderValues.add(i, headerName);
        }
        for (String option : options) {
            assertFalse(actualHeaderValues.contains(option), option + " Column header should not be displayed");
        }
    }

    @Then("I see source field has all expected options on Add Edit address IN-EB")
    public void iSeeSourceFieldHasAllExpectedOptionsaddEditOnINEB() {
        waitFor(2);
        List<String> actualOptions = new ArrayList<>();
        List<String> expected = new ArrayList<>();
       actualOptions = getOptionsForWithoutDeselect(createConsumer.addressSource);
       expected = inebAddressSourceOptions();
       actualOptions.forEach(System.out::println);

        assertTrue(actualOptions.equals(expected), " Options are not matching expected");
    }

    @And("I see {string} field on Create Consumer UI has all expected options on IN-EB")
    public void iSeeFieldOnCreateConsumerUIHasAllExpectedOptionsOnINEB(String field) throws Throwable {
        String actualOption = createConsumer.addressSourceOnCreateConsumer.getAttribute("value");
        assertTrue(actualOption.equals("Consumer Reported"), field + " Fiel parm is not matching expected");
    }

    @And("I add {string} external ID")
    public void i_add_external_ID(String program) {
        jsClick(createConsumer.externalIdAdd);
        waitFor(2);
        jsClick(createConsumer.externalIdDropdown.get(0));
        createConsumer.externalIdDropdown.get(0).click();
        waitFor(2);
        jsClick(createConsumer.externalIdDropdownMedicAIDvalue);
        clearAndFillText(createConsumer.externalIdInputField.get(0),getRandomString(10));
        if(program.length()==0) {
            jsClick(new CRMCreateApplicationMemberPage().saveButton);
        }
        if(program.length()>0){
            jsClick(createConsumer.externalIdAdd);
            waitFor(2);
            jsClick(createConsumer.externalIdDropdown.get(1));
            createConsumer.externalIdDropdown.get(1).click();
            waitFor(3);
            jsClick(createConsumer.externalIdDropdownCHIPvalue);
            waitFor(2);
            clearAndFillText(createConsumer.externalIdInputField.get(1),getRandomString(10));
            jsClick(new CRMCreateApplicationMemberPage().saveButton);
        }
    }

    @Then("I verify external ID on edit consumer page is NOT editable")
    public void i_verify_external_ID_on_edit_consumer_page_is_NOT_editable() {
        try {
            createConsumer.externalIdInFirsRowInEditPage.isEnabled();
            System.out.println("Element is clickable");
        }
        catch(Exception e) {
            assertFalse(createConsumer.externalIdAdd.isEnabled(), " add button should not be enable on this page");
            assertFalse(isElementDisplayed(createConsumer.externalIdInputField.get(0)), "externalID value shod not be editable");
        }
    }

    @Then("I verify external ID on edit consumer page not have a little trash bucket")
    public void i_verify_external_ID_on_edit_consumer_page_is_NOT_editableTrashBucket() {
            assertFalse(isElementDisplayed(createConsumer.littleTrashBucket), "Trash bucket is displayed on a page");
    }

    @And("click on existing case")
    public void click_on_existing_case(){
        waitForVisibility(addCaseMemberPage.caseMembersStatus,2);
        addCaseMemberPage.caseMembersStatus.click();
    }

    @Then("I will be UNABLE to edit any of the fields on {string}")
    public void i_will_be_UNABLE_to_edit_any_of_the_fields_on_this_UI(String role) {
        waitFor(3);
       assertTrue(!isElementDisplayed(addCaseMemberPage.firstNameInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.lastNameInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.dobInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.startDateInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.endDateInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.ageInput), "Element is Displayed on a page");
       assertTrue(!isElementDisplayed(addCaseMemberPage.ssnInput), "Element is Displayed on a page");

    }

    @Then("Verify Save and Cancel buttons not visible")
    public void verify_Save_and_Cancel_buttons_not_visible() {
        waitFor(2);
      assertTrue(!isElementDisplayed(unidentifiedContactPage.saveBtn), "Element is Displayed on a page");
   }

    @Then("I will not see the {string} button for the External ID component")
    public void i_will_not_see_the_button_for_the_External_ID_component(String string) {
        waitFor(2);
       assertTrue(!isElementDisplayed(createConsumer.externalIdAdd));
    }

    @Then("I able to see all state from state dropdown in format USPS two character abbreviation in the Report Label column")
    public void i_able_to_see_all_sate_from_sate_dropdown_in_format_USPS_two_character_abbreviation_in_the_Report_Label_column() {
        assertTrue(stateDropdownHasAllAbbr(createConsumer.consumerState), "State dropdown has incorrect value/s");
    }

    @And ("I search existing case by caseID")
    public void searchByCAseId(){
        clearAndFillText(manualContactRecordSearchPage.StateCaseIdSeach, new APICaseLoaderEligibilityEnrollmentController().getCaseIdentificationNo());
        waitFor(1);
        manualContactRecordSearchPage.searchButton.click();
    }

    @Then("consumer on the case who was already the PI, will be removed as PI and will marked as a Case Member")
    public void consumer_on_the_case_who_was_already_the_PI_will_be_removed_as_PI_and_will_marked_as_a_Case_Member() {
        demographicContactInfo.demographicInfoTab.click();
        Integer consumerAgeFirstPI = Integer.parseInt(demographicContactInfo.firstAgeInPiTableInCase.getText().substring(0,2));
        Integer consumerAgeFirstMember = Integer.parseInt(demographicContactInfo.firsMemberAge.getText().substring(0,2));
        System.out.println(consumerAgeFirstPI + "+++++++++++++++" + consumerAgeFirstMember);
        assertTrue(consumerAgeFirstPI>consumerAgeFirstMember, "Active PI age is lower then inactive PI");
        assertEquals(demographicContactInfo.fistRowPIstatus.getText(), "ACTIVE", "First PI status is INACTIVE");
    }

    @Then ("verify in PI table only one consumer has active status")
    public void verifyOnePiIsActive(){
        demographicContactInfo.demographicInfoTab.click();
        waitFor(1);
        assertTrue(demographicContactInfo.numberOfActivePI.size()==1, "Active PI more then one");
    }

    @Then("I verify existing member End Date and inactivated {string}")
    public void verifyOnePiIsInactive(String status){
        demographicContactInfo.demographicInfoTab.click();
        waitFor(1);
        assertEquals(demographicContactInfo.fistRowPIstatus.getText(), status, "Status ACTIVE" );
    }

    @Then("I will see existing PI inactivate and next old member RE-evaluated and become a PI")
    public void I_will_see_existingPIinactivate(){
        demographicContactInfo.demographicInfoTab.click();
        Integer consumerAgeFirstPI = Integer.parseInt(demographicContactInfo.firstAgeInPiTableInCase.getText().substring(0,2));
        Integer consumerAgeFirstMember = Integer.parseInt(demographicContactInfo.firsMemberAge.getText().substring(0,2));
        System.out.println(consumerAgeFirstPI + "+++++++++++++++" + consumerAgeFirstMember);
        assertTrue(consumerAgeFirstPI<consumerAgeFirstMember, "Active PI age is grater then inactive Member");
        assertEquals(demographicContactInfo.fistRowPIstatus.getText(), "ACTIVE", "First PI status is INACTIVE");
    }

    @Then("I verify consuber OptIn by default true for mail and phone")
    public void verifyConsumerOptInDefaoultStatus(){
        demographicContactInfo.demographicInfoTab.click();
        jsClick(demographicContactInfo.fistRowPIstatus);
        waitFor(2);
        assertTrue(demographicContactInfo.mailChexboxEdidPiPage.isSelected(), "Mail checkbox not selected");
        assertTrue(demographicContactInfo.phoneChexboxEdidPiPage.isSelected(), "Phone checkbox not selected");
        assertFalse(demographicContactInfo.emailChexboxEdidPiPage.isSelected(), "Email checkbox is selected");
        assertFalse(demographicContactInfo.textChexboxEdidPiPage.isSelected(), "Text checkbox is selected");
        assertFalse(demographicContactInfo.faxChexboxEdidPiPage.isSelected(), "Fax checkbox is selected");

    }

    @Then("I verify the opt-in channels consumed in the response are not null")
    public void i_verify_the_opt_in_channels_consumed_in_the_response_are_not_null() {
        demographicContactInfo.demographicInfoTab.click();
        jsClick(demographicContactInfo.fistRowPIstatus);
        waitFor(2);
        assertNotNull(demographicContactInfo.mailChexboxEdidPiPage);
        assertNotNull(demographicContactInfo.phoneChexboxEdidPiPage);
        }

    @And("I add {string} external ID to update consumer profile")
    public void i_add_external_ID_update(String id) {
        jsClick(createConsumer.externalIdAdd);
        waitFor(2);
        jsClick(createConsumer.externalIdDropdown.get(0));
        createConsumer.externalIdDropdown.get(0).click();
        waitFor(2);
        jsClick(createConsumer.externalIdDropdownMedicAIDvalue);
        clearAndFillText(createConsumer.externalIdInputField.get(0),id);
        jsClick(new CRMCreateApplicationMemberPage().saveButton);
        waitFor(3);
    }
}
