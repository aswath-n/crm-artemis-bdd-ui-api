package com.maersk.crm.step_definitions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMManualContactRecordSearchPage;
import com.maersk.crm.pages.crm.CRMManualCaseConsumerSearchPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class CRM_AdvanceSearchStepDef extends CRMUtilities implements ApiBase {
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() ->getNewTestData2());
    public static ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage=new CRMManualCaseConsumerSearchPage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> String.valueOf(newConsumer.get().get("name")));
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> String.valueOf(newConsumer.get().get("surname")));
    private String consumerId;

    @When("I click on advance search icon")
    public void i_click_on_Search_Button() {
        waitFor(3);
        contactRecord.btnAdvancedSearch.click();
        waitFor(2);
    }

    @When("I enter county as {string} city as {string} zip as {string} and state as {string}")
    public void enterAdvancedSearchDetails(String county, String city, String zip, String state) {
        waitFor(2);
        if(!county.isEmpty())
            selectDropDown(contactRecord.lstConsumerCounty, county);

        if(!city.isEmpty())
            selectDropDown(contactRecord.lstConsumerCity, city);

        if(!zip.isEmpty())
            selectDropDown(contactRecord.lstZipCode, zip);

        if(!state.isEmpty())
            selectDropDown(contactRecord.lstConsumerState, state);
    }

    @Then("I verify the following options displayed for city field")
    public void verfyCitiesDisplayed(List<String> expCities){
        List<String> actCities = new ArrayList<>();
        int count = 1;
        waitFor(2);
        contactRecord.lstConsumerCity.click();
        for(WebElement city:contactRecord.lstConsumerCityValues){
            if(count==1){
                count++;
                continue;
            }
            actCities.add(city.getText());

        }
        contactRecord.lstConsumerCityValues.get(0).click();
        Assert.assertEquals(actCities, expCities);
    }

    @Then("I verify the following options displayed for county field")
    public void verfyCountyDisplayed(List<String> expCounties){
        List<String> actCounties = new ArrayList<>();
        int count = 1;
        waitFor(5);
        contactRecord.lstConsumerCounty.click();
        for(WebElement county:contactRecord.lstConsumerCountyValues){
            if(count==1){
                count++;
                continue;
            }
            actCounties.add(county.getText());

        }
        contactRecord.lstConsumerCountyValues.get(0).click();
        Assert.assertEquals(actCounties, expCounties);

    }

    @Then("I verify the following options displayed for state field")
    public void verfyStatesDisplayed(List<String> expStates){
        List<String> actStates = new ArrayList<>();
        int count = 1;
        waitFor(2);
        contactRecord.lstConsumerState.click();
        for(WebElement state:contactRecord.lstConsumerStateValues){
            if(count==1){
                count++;
                continue;
            }
            actStates.add(state.getText());

        }
        contactRecord.lstConsumerStateValues.get(0).click();
        Assert.assertEquals(actStates, expStates);

    }

    @Then("I verify the following options displayed for zip field")
    public void verfyZipCodesDisplayed(List<String> expZips){
        List<String> actZips = new ArrayList<>();
        int count = 1;
        waitFor(2);
        contactRecord.lstZipCode.click();
        for(WebElement zip:contactRecord.lstZipCodeValues){
            if(count==1){
                count++;
                continue;
            }
            actZips.add(zip.getText());


        }
        contactRecord.lstZipCodeValues.get(0).click();
        Assert.assertEquals(actZips, expZips);

    }


    @When("I click on advanced search")
    public void i_click_on_advanced_search() {
        waitFor(2);
        contactRecord.contactRecordAdvanceSearch.click();
        waitFor(1);
    }

    @When("I enter Advance Search with {string},{string},{string},{string},{string},{string} and {string} on Contact Record")
    public void i_enter_advanced_search_with_Addresss_City_State_County_Zip_Phone_Email_on_Contact_Record(String address, String city, String state, String county, String zipCode, String phoneNumber, String email) {
        contactRecord.resetButton.click();
        i_click_on_advanced_search();
        //waitForVisibility(contactRecord.city, 3);
        assertTrue(contactRecord.address1.isDisplayed(), "Consumer Address textbox is not displayed");
        if (!address.isEmpty()) {
            contactRecord.address1.sendKeys(address);
        }
        if (!city.isEmpty()) {
            selectDropDown(contactRecord.city, city);
        }
        if (!state.isEmpty()) {
            selectDropDown(contactRecord.state, state);
        }
        if (!county.isEmpty()) {
            selectDropDown(contactRecord.county, county);
        }
        if (!zipCode.isEmpty()) {
            selectDropDown(contactRecord.zipCode, zipCode);
        }
        if (!phoneNumber.isEmpty()) {
            contactRecord.consumerPhoneNumber.sendKeys(phoneNumber);
        }
        if (!email.isEmpty()) {
            contactRecord.consumerEmail.sendKeys(email);
        }
    }

    @Then("I verify search is providing a valid search result is {string} on Contact Record")
    public void i_verify_search_is_providing_a_valid_search_result_is_on_contact_record(String expValidSearchResult) {
        waitFor(10);
        try {

            if (expValidSearchResult.toLowerCase().contains("true")) {
                assertTrue(contactRecord.searchResultFirstRowFirstName.isDisplayed(), "At least one record expected to show as search result. Instead, no result found message is displayed.");
            } else {
                assertTrue(contactRecord.noRecordsAvailableMessage.isDisplayed(), "'No Records Available' message is expected. However, at least one record found on search result.");
            }
            System.out.println("At least one search item found on Contact Record.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertFalse(true, "At least one record expected to show as search result. Instead, no result found message is displayed");
        }
    }

    @Then("verify all advanced fields are displayed")
    public void verify_all_advanced_fields_are_displayed() {
        String error_message = null;
        if (!contactRecord.address1.isDisplayed()) {
            error_message = error_message + "Address field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.city.isDisplayed()) {
            error_message = error_message + "City field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.state.isDisplayed()) {
            error_message = error_message + "State field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.county.isDisplayed()) {
            error_message = error_message + "County field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.zipCode.isDisplayed()) {
            error_message = error_message + "Zip Code field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.consumerPhoneNumber.isDisplayed()) {
            error_message = error_message + "Phone Number field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        if (!contactRecord.consumerEmail.isDisplayed()) {
            error_message = error_message + "Email field is not displayed on Screen as part of advanced Seach on Contact Record" + "\n";
        }
        //contactRecord.searchButton.click();
        if (error_message != null) {
            System.out.println(error_message);
            assertFalse(error_message.length() == 0);
        }
        waitFor(5);
    }

    @Then("I verify search results according to the first and last name {string} {string} on Contact Record")
    public void i_verify_search_results_according_to_the_first_and_last_name(String firstName, String lastName) {
        waitFor(10);
        try {
            if (firstName.isEmpty() & lastName.isEmpty()) {
                assertTrue(contactRecord.searchResultFirstRowFirstName.isDisplayed(), "At least one record expected to show as search result. Instead, no result found message is displayed.");
                System.out.println("At least one search item found on Contact Record.");
            } else {
                if (!firstName.isEmpty()) {
                    assertTrue(contactRecord.searchResultFirstRowFirstName.getText().contains(firstName), "First Name did not Match on Contact FRecord Search");
                }
                if (!lastName.isEmpty()) {
                    assertTrue(contactRecord.searchResultFirstRowLastName.getText().contains(lastName), "Last Name did not Match on Contact FRecord Search");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertFalse(true, "At least one record expected to show as search result. Instead, no result found message is displayed");
        }
    }

    @When("I search for consumer profile for Profile View")
    public void searchForConsumerProfile() {
        createNewConsumerForContactRecord();
        System.out.print("First Name:" +lastName.get());
   hover(dashboard.case_ConsumerSearchTab);

        dashboard.case_ConsumerSearchTab.click();
        waitFor(3);
        hover(contactRecord.lastName);
        manualCaseConsumerSearchPage.lastName.sendKeys(lastName.get());
        manualCaseConsumerSearchPage.searchButton.click();
        waitFor(2);
        waitForVisibility(contactRecord.consumerIdFirstRecord, 20);
    }

    @And("I navigate to consumer profile for Profile View")
    public void navigateToConsumerProfile() {
        contactRecord.consumerIdFirstRecord.click();
    }

    @Then("I will see a visual indication in the header for Profile View")
    public void seeAVisualIndicationInTheHeader() {
        String clickedId = contactRecord.consumerIdFirstRecord.getText();
        contactRecord.consumerIdFirstRecord.click();
        waitForVisibility(contactRecord.viewRecordConsumerId,5);
        String actualId = contactRecord.viewRecordConsumerId.getText();
        assertEquals(clickedId, actualId, "Id is not matching or is not displayed ");
    }

    @Then("I view the Icon and I will see text display that indicates a user is viewing the {string}")
    public void viewIconAndSeeTextDisplay(String expectedText) {
        hover(contactRecord.viewRecordConsumerIcon);
        String hoverText = contactRecord.viewRecordConsumerIcon.getAttribute("title");
        assertEquals(hoverText, expectedText, "Hover Over Consumer icon Text is not matching or is not displayed");
    }

    private void createNewConsumerForContactRecord() {
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

        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, getPriorDate(4000));
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
        consumerId = contactRecord.lblCrmConsumerId.getText();
    }

    @When("I verify no results are found")
    public void i_verify_no_results_are_found() {
        CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
        assertTrue(manualContactRecordSearchPage.noRecordsAvailable.isDisplayed());
    }


}
