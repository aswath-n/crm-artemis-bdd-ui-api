package com.maersk.crm.step_definitions;

import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.api_step_definitions.APIProviderController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.pages.tm.CRMSearchProvidersResultViewPage;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.ShowOutboundCorrespondenceDetailsStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.testng.Assert.*;

import java.util.*;
import java.util.stream.Collectors;



public class CRMSearchtProviderPart2UIStepDef extends CRMUtilities implements ApiBase {


    CRMSearchProviderPart2UIPage crmSearchProviderPart2UIPage = new CRMSearchProviderPart2UIPage();
    CRM_SearchProviders_AdvancedSearchUIPage crmSearchProvidersAdvancedSearchUIPage = new CRM_SearchProviders_AdvancedSearchUIPage();
    CRMSearchProvidersResultViewPage crmSearchProvidersResultViewPage = new CRMSearchProvidersResultViewPage();
    Api_CommonSteps api_common = new Api_CommonSteps();
    final ThreadLocal<APIProviderController> apiProviderController = ThreadLocal.withInitial(APIProviderController::new);
    CRMSearchProviderPart2UIPage searchProviderPart2UIPage = new CRMSearchProviderPart2UIPage();

    public static final ThreadLocal<String> numberOfDisplayedProviders = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> numberOfTotalProviders = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<HashMap<String, String>> providerName = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<HashMap<String, String>> providerData = ThreadLocal.withInitial(HashMap::new);

    @Then("Verify search screen")
    public void Verify_search_screen() throws InterruptedException {
        Thread.sleep(10000);
        assertTrue(crmSearchProviderPart2UIPage.providerfirstname.isDisplayed());
        waitFor(5);
    }

    @Then("I am able to enter one or more of the following fields in addition")
    public void i_am_able_to_enter_one_or_more_of_the_following_fields_in_addition() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(crmSearchProviderPart2UIPage.streetaddress.isEnabled());
        assertTrue(crmSearchProviderPart2UIPage.cityfield.isEnabled());
        assertTrue(crmSearchProviderPart2UIPage.zipfield.isEnabled());
    }

    @Then("I have entered data into the {string},{string}")
    public void i_have_entered_data_into_the_Address_Line_and_City_field(String address, String city) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.streetaddress.sendKeys(address);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(city);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.ENTER);
    }

    @Given("I’ve entered data into the {string} \\(Address Line {int}), zip {string}")
    public void i_ve_entered_data_into_the_Address_Line_and_ZIP_field(String address, String city) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.streetaddress.sendKeys(address);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(city);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
    }

    @When("I return provider search results")
    public void i_return_provider_search_results() {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.searchButton.click();
    }

    @Then("Verify Provider address displayed in the search results will match the {string} and City or ZIP entered")
    public void then_the_provider_address_displayed_in_the_search_results_will_match_the_Street_Address_Address_Line_and_City_or_ZIP_entered(String address) {
        // Write code here that turns the phrase above into concrete actions
        String s = crmSearchProvidersAdvancedSearchUIPage.getFirstProviderAddress.getText();
        assertTrue(s.contains(address), "Address one is not matchig");

    }

    @Then("Enter distance {string}")
    public void enter_distance(String string) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.distancefield.sendKeys(string);
    }

    @Then("I will see Distance as a column")
    public void i_will_see_Distance_as_a_column() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(crmSearchProviderPart2UIPage.distancefield.isDisplayed());
    }

    @Given("I have entered street address {string}")
    public void i_have_entered_street_address(String address) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.streetaddress.sendKeys(address);
    }

    @When("I select the cancel button")
    public void i_select_the_cancel_button() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(15);
        crmSearchProviderPart2UIPage.CancelButton.click();
    }

    @Then("{string} entered will be cleared and I will remain on the same screen")
    public void entered_will_be_cleared_and_I_will_remain_on_the_same_screen(String street) {
        // Write code here that turns the phrase above into concrete actions
        assertNotEquals(crmSearchProviderPart2UIPage.streetaddress.getAttribute("value"), street);
    }

    @When("Verify that distance field should be disable")
    public void verify_that_distance_field_should_be_disable() {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(crmSearchProviderPart2UIPage.distancefield, 30);
        assertFalse(crmSearchProviderPart2UIPage.distancefield.isEnabled());
    }

    public void clearAllProviderSearchFormFields() {
        try {
            crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
            crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
        } catch (Exception e) {
            System.out.println("Street Address Field does not exist!");
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.cityfield) && !crmSearchProviderPart2UIPage.cityfield.getAttribute("value").isEmpty()) {
            hover(crmSearchProviderPart2UIPage.getParentFieldForHover(crmSearchProviderPart2UIPage.cityfield));
            click(crmSearchProviderPart2UIPage.cityFieldClearButton);
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.zipfield) && !crmSearchProviderPart2UIPage.zipfield.getAttribute("value").isEmpty()) {
            hover(crmSearchProviderPart2UIPage.getParentFieldForHover(crmSearchProviderPart2UIPage.zipfield));
            click(crmSearchProviderPart2UIPage.zipFieldClearButton);
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.distanceField)) {
            while (crmSearchProviderPart2UIPage.distanceField.getAttribute("value").length() > 0) {
                crmSearchProviderPart2UIPage.distanceField.sendKeys(Keys.BACK_SPACE);
                waitFor(1);
            }
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.specialityField) && !crmSearchProviderPart2UIPage.specialityField.getAttribute("value").isEmpty()) {
            hover(crmSearchProviderPart2UIPage.getParentFieldForHover(crmSearchProviderPart2UIPage.specialityField));
            click(crmSearchProviderPart2UIPage.specialityFieldClearButton);
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.languageField) && !crmSearchProviderPart2UIPage.languageField.getAttribute("value").isEmpty()) {
            hover(crmSearchProviderPart2UIPage.getParentFieldForHover(crmSearchProviderPart2UIPage.languageField));
            click(crmSearchProviderPart2UIPage.languageFieldClearButton);
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.ageRangeField) && !crmSearchProviderPart2UIPage.ageRangeField.getAttribute("value").isEmpty()) {
            hover(crmSearchProviderPart2UIPage.getParentFieldForHover(crmSearchProviderPart2UIPage.ageRangeField));
            click(crmSearchProviderPart2UIPage.ageRangeFieldClearButton);
        }
        if (isElementDisplayed(crmSearchProviderPart2UIPage.providerfirstname) && !crmSearchProviderPart2UIPage.providerfirstname.getAttribute("value").isEmpty()) {
            crmSearchProviderPart2UIPage.providerfirstname.sendKeys(Keys.BACK_SPACE);
            waitFor(1);
        }
    }

    @And("I clear and fill provider search form with data")
    public void I_clear_and_fill_provider_search_form_with_data(Map<String, String> data) {
        clearAllProviderSearchFormFields();
        I_fill_provider_search_form_with_data(data);
    }

    @And("I fill provider search form with data")
    public void I_fill_provider_search_form_with_data(Map<String, String> data) {
        for (String key : data.keySet()) {
            waitFor(5);
            switch (key) {
                case "streetAddress":
                    crmSearchProviderPart2UIPage.streetaddressField.sendKeys(data.get(key));
                    System.out.println("Filling street Address with " + data.get(key));
                    break;
                case "city":
                    selectDropDown(crmSearchProviderPart2UIPage.cityfield, data.get(key));
                    System.out.println("Filling city with " + data.get(key));
                    break;
                case "zip":
                    // zip field has validation logic, doesn't let to sendKeys at once
                    for (String num : data.get(key).split("")) {
                        crmSearchProviderPart2UIPage.zipfield.sendKeys(num);
                        waitFor(1);
                    }
                    crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
                    System.out.println("Filling zip with " + data.get(key));
                    break;
                case "distance":
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        crmSearchProviderPart2UIPage.distanceField.clear();
                    } else {
                        crmSearchProviderPart2UIPage.distanceField.sendKeys(data.get(key));
                        System.out.println("Filling distance with " + data.get(key));
                    }
                    break;
                case "specialty":
                    selectDropDown(crmSearchProviderPart2UIPage.specialityField, data.get(key));
                    System.out.println("Filling street Address with " + data.get(key));
                    break;
                case "language":
                    selectDropDown(crmSearchProviderPart2UIPage.languageField, data.get(key));
                    System.out.println("Filling specialty with " + data.get(key));
                    break;
                case "ageRange":
                    selectDropDown(crmSearchProviderPart2UIPage.ageRangeField, data.get(key));
                    System.out.println("Filling ageRange with " + data.get(key));
                    break;
                case "First Name":
                    waitFor(2);
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        crmSearchProviderPart2UIPage.providerfirstname.clear();
                    } else {
                        crmSearchProviderPart2UIPage.providerfirstname.sendKeys(data.get(key));
                        System.out.println("Filling first name with " + data.get(key));
                    }
                    break;
                case "Last Name":
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        crmSearchProviderPart2UIPage.providerlastname.clear();
                    } else {
                        crmSearchProviderPart2UIPage.providerlastname.sendKeys(data.get(key));
                        System.out.println("Filling last name with " + data.get(key));
                    }
                    break;
                case "Group Name":
                    if (data.get(key).equals("null") || data.get(key).isEmpty()) {
                        crmSearchProviderPart2UIPage.providergroupname.clear();
                    } else {
                        crmSearchProviderPart2UIPage.providergroupname.sendKeys(data.get(key));
                        System.out.println("Filling group name with " + data.get(key));
                    }
                    break;
            }
        }
    }

    @Then("Enter city {string}")
    public void entercity(String city) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.cityfield.sendKeys(city);
        waitFor(2);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DOWN);
        waitFor(2);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.ENTER);

    }

    @Given("Select Zip")
    public void select_Zip() {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.zipfield.click();
        waitFor(2);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.DOWN);
        waitFor(2);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
    }

    @Then("Return {string} mapped to the city entered")
    public void return_mapped_to_the_city_entered(String zip) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(crmSearchProviderPart2UIPage.zipfield.getAttribute("value"), zip);
    }

    @Then("Enter zip {string}")
    public void enterzip(String zip) {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.zipfield.sendKeys(zip);
        waitFor(2);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.DOWN);
        waitFor(2);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
    }

    @Then("Verify that distance field should be enable")
    public void verify_that_distance_field_should_be_enable() {
        // Write code here that turns the phrase above into concrete actions
        staticWait(3000);
        assertTrue(crmSearchProviderPart2UIPage.distancefield.isEnabled());
    }

    @Then("Click at cancel button")
    public void click_at_cancel_button() {
        // Write code here that turns the phrase above into concrete actions
        crmSearchProviderPart2UIPage.CancelButton.click();
        staticWait(3000);

    }

    @When("I hover my mouse over the orange icon in the Distance field")
    public void i_hover_my_mouse_over_the_orange_icon_in_the_Distance_field() {
        // Write code here that turns the phrase above into concrete actions
        hover(crmSearchProviderPart2UIPage.distancefield);

    }

    @Then("I will see the following text: {string}")
    public void i_will_see_the_following_text(String tooltip) {
        // Write code here that turns the phrase above into concrete actions
        String title = crmSearchProviderPart2UIPage.distancetooltip1.getAttribute("title");
        System.out.println(title);
        assertEquals(title, tooltip);
    }

    @Then("I will return providers who have an active location association within the radius of miles entered of the City entered")
    public void i_will_return_providers_who_have_an_active_location_association_within_the_radius_of_miles_entered_of_the_city_entered() {
        assertTrue(crmSearchProviderPart2UIPage.getDistance.getText().contains("0.0881"), "Distance is not displayed accordingly");

    }

    @Then("I will return providers who have an active location association within the radius of miles entered of the City entered {string}")
    public void i_will_return_providers_who_have_an_active_location_association_within_the_radius_of_miles_entered_of_the_city_entered_with_Parameter(String dist) {
        Double distance = Double.parseDouble(crmSearchProviderPart2UIPage.getDistance.getText());
        Double dist1 = Double.parseDouble(dist);
        assertTrue(distance < dist1, "Distance is not displayed accordingly");

    }

    @And("I click on search provider button")
    public void i_click_on_search_provider_button() {
        crmSearchProviderPart2UIPage.searchProviderbutton.click();
        waitFor(3);
        crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.searchbutton.click();
        waitFor(3);
    }

    @Then("I verify the following fields {string}, {string}, {string},{string}, {string}, {string}, {string}, {string} populated with defaulted value")
    public void i_verify_the_following_fields_populated_with_defaulted_value(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        assertEquals(crmSearchProviderPart2UIPage.addressLine.getAttribute("value"), string);
        assertEquals(crmSearchProviderPart2UIPage.cityfield.getAttribute("value"), string2);
        assertEquals(crmSearchProviderPart2UIPage.zipfield.getAttribute("value"), string3);
        assertEquals(crmSearchProviderPart2UIPage.distanceField.getAttribute("value"), string4);
        assertEquals(crmSearchProviderPart2UIPage.specialityField.getAttribute("value"), string5);
        assertEquals(crmSearchProviderPart2UIPage.languageField.getAttribute("value"), string6);
        assertEquals(crmSearchProviderPart2UIPage.providerGenderField.getAttribute("value"), null);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeField.getAttribute("value"), string8);
    }

    @When("I search with speciality, language, provider gender and age range")
    public void i_search_with_speciality_language_provider_gender_and_age_range() {
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.specialityField.click();
        crmSearchProviderPart2UIPage.specialityOption.click();
        crmSearchProviderPart2UIPage.languageField.click();
        crmSearchProviderPart2UIPage.languageOption.click();
        crmSearchProviderPart2UIPage.providerGenderField.click();
        crmSearchProviderPart2UIPage.providerGenderOption.click();
        crmSearchProviderPart2UIPage.ageRangeField.click();
        crmSearchProviderPart2UIPage.ageRangeOption.click();
        waitFor(3);
        crmSearchProviderPart2UIPage.searchbutton.click();
        waitFor(5);

    }

    @When("I search with {string}")
    public void i_search_with_language(String attribute) {
        switch (attribute.toLowerCase()) {
            case "language":
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.languageField.click();
                crmSearchProviderPart2UIPage.languageOption.click();
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
            case "speciality":
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.specialityField.click();
                crmSearchProviderPart2UIPage.specialityOption.click();
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
            case "distance":
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.DELETE);
                waitFor(2);
                crmSearchProviderPart2UIPage.zipfield.sendKeys("30144");
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
            case "city and zip":
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
            case "street address and city":
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.streetaddressField.sendKeys("6000 Joe Frank Harris Pkwy NW");
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
            case "street address and zip":
            case "zip":
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
                crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
                crmSearchProviderPart2UIPage.zipfield.sendKeys("30103");
                crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.ENTER);
                crmSearchProviderPart2UIPage.searchbutton.click();
                break;
        }
    }

    @Then("I verify I will be presented with the “expanded view” which contains {string}, {string}, {string}, {string}")
    public void i_verify_I_will_be_presented_with_the_expanded_view_which_contains(String string, String string2, String string3, String string4) {
        waitFor(3);
        crmSearchProviderPart2UIPage.expandArrow.get(1).click();
        assertEquals(crmSearchProviderPart2UIPage.providerGenderSearchResult.get(0).getText(), string3);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeSearchResult.getText(), string4);
        assertEquals(crmSearchProviderPart2UIPage.languageSearchResult.get(0).getText().replace("star", ""), string2);
        assertEquals(crmSearchProviderPart2UIPage.specialitySearchResult.get(0).getText(), string);
    }

    @And("I click on a search button")
    public void i_click_on_a_search_button() {
        click(crmSearchProviderPart2UIPage.searchbutton);
        System.out.println("Searching ...");
        waitFor(5);
    }

    @And("I click on name field title to sort")
    public void i_click_on_name_field_title_to_sort() {
        click(crmSearchProviderPart2UIPage.nameFieldTitle);
        System.out.println("Sorting ...");
        waitFor(2);
    }

    @And("I click on search provider")
    public void i_click_on_search_provider() {
        crmSearchProviderPart2UIPage.searchProviderbutton.click();
    }

    @Then("I verify I will be presented with the “expanded view” which contains {string}, {string}, {string}, {string} for all search results")
    public void i_verify_I_will_be_presented_with_the_expanded_view_which_contains_for_all_search_results(String string, String string2, String string3, String string4) {
        waitFor(3);
        for (WebElement gender : crmSearchProviderPart2UIPage.providerGenderSearchResult) {
            assertEquals(gender.getText(), string3);
        }
        for (WebElement language : crmSearchProviderPart2UIPage.languageSearchResult) {
            assertEquals(language.getText(), string2);
        }
        for (WebElement speciality : crmSearchProviderPart2UIPage.specialitySearchResult) {
            assertEquals(speciality.getText(), string);
        }
    }

    @When("I select Age Range")
    public void i_select_Age_Range() {
        crmSearchProviderPart2UIPage.ageRangeField.click();
        waitFor(2);
    }

    @Then("I expect to see a drop-down with {int} options {string}, {string}, {string}, {string}")
    public void i_expect_to_see_a_drop_down_with_options(Integer int1, String string, String string2, String string3, String string4) {
        waitFor(1);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeOption.getText(), string);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeOption1.getText(), string2);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeOption2.getText(), string3);
        assertEquals(crmSearchProviderPart2UIPage.ageRangeOption3.getText(), string4);
    }

    @When("I click the Checkmark button")
    public void i_click_the_Checkmark_button() {
        scrollDownUsingActions(3);
        scrollRight();
        scrollUpUsingActions(2);
        crmSearchProviderPart2UIPage.checkmarkButton.click();
        waitFor(3);
    }

    @Then("I verify the following panels will be displayed: {string}, {string}, {string}")
    public void i_verify_the_following_panels_will_be_displayed(String string, String string2, String string3) {
        crmSearchProviderPart2UIPage.selectedConsumers.isDisplayed();
        crmSearchProviderPart2UIPage.selectedPlans.isDisplayed();
        scrollDownUsingActions(1);
        crmSearchProviderPart2UIPage.selectedProviders.isDisplayed();
    }

    @When("I click the Select Provider button")
    public void i_click_the_Select_Provider_button() {
        crmSearchProviderPart2UIPage.providerName.click();
        waitFor(2);
        hover(crmSearchProviderPart2UIPage.selectProvider);
        crmSearchProviderPart2UIPage.selectProvider.click();
    }

    @Then("I verify the following fields will be displayed: Provider Name, First Name, Last Name, NPI")
    public void i_verify_the_following_fields_will_be_displayed_Provider_Name_First_Name_Last_Name_NPI() {
        scrollDownUsingActions(1);
        assertTrue(crmSearchProviderPart2UIPage.providerNameField.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerNPI.isDisplayed());
    }

    @When("I click on a selected provider")
    public void i_click_on_a_selected_provider() {
        crmSearchProviderPart2UIPage.providerName.click();
        waitFor(2);

    }

    @Then("I verify I will be presented with the PROVIDER DETAILS")
    public void i_verify_i_will_be_presented_with_the_PROVIDER_DETAILS() {
        assertTrue(crmSearchProviderPart2UIPage.providername.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.planname.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerGender.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.pcp.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.obgyn.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.ageRange.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.npiProvider.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerLaguages.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerSpecialities.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerAddress.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerPhone.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerFax.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerEmail.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerNewPatients.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.handicap.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.officeHours.isDisplayed());
    }

    @Then("I verify the following fields {string}, {string}, {string},{string} populated with defaulted value")
    public void i_verify_the_following_fields_populated_with_defaulted_value(String string, String string2, String string3, String string4) {
        assertEquals(crmSearchProviderPart2UIPage.addressLine.getAttribute("value"), string);
        assertEquals(crmSearchProviderPart2UIPage.cityfield.getAttribute("value"), string2);
        assertEquals(crmSearchProviderPart2UIPage.zipfield.getAttribute("value"), string3);
        assertEquals(crmSearchProviderPart2UIPage.distanceField.getAttribute("value"), string4);
    }

    @Then("I verify I will be presented with the “expanded view” which contains Provider Name, Provider Address, Provider Telephone Number, DISTANCE, SPECIALTY, LANGUAGE, LAST UPDATED")
    public void i_verify_I_will_be_presented_with_the_expanded_view_which_contains_Provider_Name_Provider_Address_Provider_Telephone_Number_DISTANCE_SPECIALTY_LANGUAGE_LAST_UPDATED() {
        waitFor(5);
        scrollDownUsingActions(2);
        crmSearchProviderPart2UIPage.expandArrow.get(0);
        waitFor(1);
        assertTrue(crmSearchProviderPart2UIPage.providerName.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerAddressResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerPhoneResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerDistanceResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerLanguageResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerSpecialityResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.lastUpdatedResult.isDisplayed());

    }

    @Then("I validate providers have a PCP Indicator of Yes")
    public void i_validate_providers_have_a_PCP_Indicator_of_Yes() {
        assertEquals(crmSearchProviderPart2UIPage.pcpYes.getText(), "Yes");
    }

    @Then("I validate that providers participate in the selected {string}")
    public void i_validate_that_providers_participate_in_the_selected(String string) {
        assertEquals(crmSearchProviderPart2UIPage.planName.getText(), string);
    }

    @When("I  click on the BACK button")
    public void i_click_on_the_BACK_button() {
        waitFor(2);
        crmSearchProviderPart2UIPage.backButton.click();
    }

    @Then("I verify I will be returned to the previous page with no change to search results")
    public void i_verify_I_will_be_returned_to_the_previous_page_with_no_change_to_search_results() {
        crmSearchProviderPart2UIPage.selectedConsumers.isDisplayed();
        crmSearchProviderPart2UIPage.selectedPlans.isDisplayed();
        crmSearchProviderPart2UIPage.providerSearch.isDisplayed();
    }

    @Then("I verify will get a list of providers and any field I did not populate will display NULL value \\(blank)")
    public void i_verify_will_get_a_list_of_providers_and_any_field_I_did_not_populate_will_display_NULL_value_blank() {
        assertTrue(crmSearchProviderPart2UIPage.npiProvider.isEnabled());
    }

    @When("I search with provider First name {string} and Last Name {string}")
    public void i_search_with_provider_First_name_and_Last_Name(String string, String string2) {
        crmSearchProviderPart2UIPage.providerFirstname.sendKeys(string);
        crmSearchProviderPart2UIPage.providerLastname.sendKeys(string2);
        crmSearchProviderPart2UIPage.searchbutton.click();
    }


    @And("I search provider with groupname parameter {string}")
    public void i_search_provider_with_groupname_parameter_something(String strArg1) {
        crmSearchProviderPart2UIPage.providergroupname.sendKeys(strArg1);
        i_click_on_search_button();
    }

    @And("I verify provider name values {string} search parameters {string} in First Name and {string} in Last Name")
    public void i_verify_provider_name_values_something_search_parameters_something_in_first_name_and_something_in_last_name(String operator, String fnparameter, String lnparameter) {
        List<String> providerNameList = new ArrayList<>();
        for (WebElement temp : crmSearchProviderPart2UIPage.nameResultList) {
            providerNameList.add(temp.getText());
        }
        System.out.println(providerNameList);
        if (operator.equals("Contains")) {
            for (int i = 1; i < providerNameList.size(); i++) {
                int size = providerNameList.get(i).split(" ").length;
                String resultFirstName = providerNameList.get(i).split(" ")[0];
                String resultLastName = providerNameList.get(i).split(" ")[size - 1];
                assertTrue(resultFirstName.contains(fnparameter), "FAIL! Provider first name value doesn't contain search parameter");
                assertTrue(resultLastName.contains(lnparameter), "FAIL! Provider last name value doesn't contain search parameter");
            }
        }
        if (operator.equals("Starts with")) {
            for (int i = 1; i < providerNameList.size(); i++) {
                int size = providerNameList.get(i).split(" ").length;
                String resultFirstName = providerNameList.get(i).split(" ")[0];
                String resultLastName = providerNameList.get(i).split(" ")[size - 1];
                assertTrue(resultFirstName.startsWith(fnparameter), "FAIL! Provider first name value doesn't start with search parameter");
                assertTrue(resultLastName.startsWith(lnparameter), "FAIL! Provider last name value doesn't start with search parameter");
            }
        }
    }

    @When("I fill out fields on provider search page with data created via API")
    public void iFillOutFieldsOnProviderSearchPageWithDataCreatedViaAPI() {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        searchProviderPart2UIPage.providerFirstname.sendKeys(apiProviderController.get().fname.get());
        searchProviderPart2UIPage.providerlastname.sendKeys(apiProviderController.get().lname.get());
    }

    @And("I verify the expected provider is fetched")
    public void IVerifyThatExpectedProviderIsFetched() {
        assertTrue(crmSearchProviderPart2UIPage.providerNameFieldValue.getText().contains(APIProviderController.fname.get()));
        assertTrue(crmSearchProviderPart2UIPage.providerNameFieldValue.getText().contains(APIProviderController.lname.get()));
    }

    @And("I verify Provider Name i.e. Group name field {string} value {string}")
    public void i_verify_provider_name_group_name_value_something_value_something(String operator, String grpname) {
        List<String> providerNameList = new ArrayList<>();
        for (WebElement temp : crmSearchProviderPart2UIPage.nameResultList)
            providerNameList.add(temp.getText());
        System.out.println(providerNameList);
        if (operator.equals("Contains"))
            for (int i = 1; i < providerNameList.size(); i++)
                assertTrue(providerNameList.get(i).contains(grpname), "FAIL! Provider name value doesn't contain search parameter");
        if (operator.equals("Starts with"))
            for (int i = 1; i < providerNameList.size(); i++)
                assertTrue(providerNameList.get(i).startsWith(grpname), "FAIL! Provider name value doesn't start with search parameter");
    }

    @And("I set Search By operator to {string}")
    public void i_set_search_by_operator_to_something(String operator) {
        if (operator.equals("Contains")) {
            crmSearchProviderPart2UIPage.searchOperatorToggle.click();
            assertTrue(crmSearchProviderPart2UIPage.searchOperatorToggle.getAttribute("value").equals("true"), "Search by operator is not set to : 'Contains' !");
        }
        if (operator.equals("Starts with")) {
            assertTrue(crmSearchProviderPart2UIPage.searchOperatorToggle.getAttribute("value").equals("false"), "Search by operator is not set to : 'Starts with' !");
        }

    }


    @Then("I verify search results contains First name {string} and Last Name {string}, address, phone number, speciality, language, last updated")
    public void i_verify_search_results_contains_First_name_and_Last_Name_address_phone_number_speciality_language_last_updated(String firstName, String lastName) {
        waitFor(5);
        assertTrue(crmSearchProviderPart2UIPage.providernameResult.getText()
                .toLowerCase().contains(firstName.toLowerCase()));
        assertTrue(crmSearchProviderPart2UIPage.providernameResult.getText()
                .toLowerCase().contains(lastName.toLowerCase()));
        assertTrue(crmSearchProviderPart2UIPage.provideraddressResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerPhoneResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerLanguageResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerSpecialityResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.lastUpdatedResult.isDisplayed());
    }

    @When("I search with provider phone number  {string}")
    public void i_search_with_provider_phone_number(String string) {
        crmSearchProviderPart2UIPage.providerphoneNumber.sendKeys(string);
        crmSearchProviderPart2UIPage.searchbutton.click();
    }

    @Then("I verify search results contains provider address and phone number {string}")
    public void i_verify_search_results_contains_provider_address_and_phone_number(String string) {
        waitFor(3);
        scrollDownUsingActions(1);
        assertTrue(crmSearchProviderPart2UIPage.providerPhoneResult.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerFirstAddressResult.isDisplayed());
    }

    @Then("I verify provider search results table with data")
    public void i_verify_provider_search_results_table_with_data(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "distance":
                    assertEquals(crmSearchProviderPart2UIPage.providerDistanceResult.getText(),
                            data.get(key), key + " mismatch!");
            }
        }
    }

    @When("I search with provider plan name {string}")
    public void i_search_with_provider_plan_name(String string) {
        crmSearchProviderPart2UIPage.providerplanNameDropDownButton.click();
        crmSearchProviderPart2UIPage.getProviderPlanNameOptionByValue(string).click();
        crmSearchProviderPart2UIPage.searchbutton.click();
        waitFor(3);
    }

    @Then("I verify search results contains provider name, address, phone number, speciality, language, last updated and plan name  {string}")
    public void i_verify_search_results_contains_provider_name_address_phone_number_speciality_language_last_updated_and_plan_name(String string) {
        scrollDownUsingActions(1);
        assertEquals(crmSearchProviderPart2UIPage.providerPlanNameResult.getText(), string);
        crmSearchProviderPart2UIPage.providerLanguageResult.isDisplayed();
        crmSearchProviderPart2UIPage.providerSpecialityResult.isDisplayed();
        crmSearchProviderPart2UIPage.lastUpdatedResult.isDisplayed();
        crmSearchProviderPart2UIPage.provideraddressResult.isDisplayed();
        crmSearchProviderPart2UIPage.providerName.isDisplayed();
        crmSearchProviderPart2UIPage.providerPhoneResult.isDisplayed();
    }

    @When("I filter on the {string} column")
    public void i_filter_on_the_column(String column) {
        switch (column.toLowerCase()) {
            case "language":
                waitFor(2);
                crmSearchProviderPart2UIPage.languageFilter.click();
                crmSearchProviderPart2UIPage.languageFilterCheckbox.click();
                waitFor(2);
                scrollDownUsingActions(1);
                crmSearchProviderPart2UIPage.filterButton.click();
                waitFor(2);
                break;
            case "speciality":
                waitFor(2);
                crmSearchProviderPart2UIPage.specialityFilter.click();
                crmSearchProviderPart2UIPage.specialityFilterCheckbox.click();
                waitFor(2);
                scrollDownUsingActions(1);
                crmSearchProviderPart2UIPage.filterButton.click();
                waitFor(3);
                break;
        }
    }

    @Then("the results should filter using the approach currently implemented for specialty {string}")
    public void the_results_should_filter_using_the_approach_currently_implemented_for_specialty(String attribute) {
        switch (attribute.toLowerCase()) {
            case "language":
                for (WebElement language : crmSearchProviderPart2UIPage.languageSearchResult) {
                    waitFor(2);
                    assertEquals(language.getText(), "English");
                }
                break;
            case "family practice":
                for (WebElement speciality : crmSearchProviderPart2UIPage.specialitySearchResult) {
                    assertEquals(speciality.getText(), "Family Practice");
                }
                break;
        }
    }

    @Then("I validate the returned provider records must be within the radius of the distance value")
    public void i_validate_the_returned_provider_records_must_be_within_the_radius_of_the_distance_value() {
        for (int i = 1; i < crmSearchProviderPart2UIPage.milesColumn.size(); i++) {
            waitFor(1);
            String miles = crmSearchProviderPart2UIPage.milesColumn.get(i).getText().substring(0, 1);
            int mil = Integer.parseInt(miles);
            assertTrue(mil < 15, "Distance results are bigger than search distance");
        }
    }

    // Below step is updated with another step and no longer used in feature files. Delete after 06/01/2022 if no longer will be needed
//    @Then("the returned provider records must sorted by distance- ascending")
//    public void the_returned_provider_records_must_sorted_by_distance_ascending() {
//        List<String> actualSequence = getElementsText(crmSearchProviderPart2UIPage.milesColumn).stream().map(p -> p.substring(0, p.indexOf(" "))).collect(Collectors.toList());
//        if (!actualSequence.contains("-- --")) {
//            System.out.println("actualSequence = " + actualSequence);
//            List<Double> actualSequenceDouble = actualSequence.stream().map(s -> Double.parseDouble(s)).collect(Collectors.toList());
//            List<Double> actualSequenceDoubleSorted = actualSequenceDouble.stream().sorted().collect(Collectors.toList());
//            List<String> sortedSequence = actualSequenceDoubleSorted.stream().map(s -> String.valueOf(s)).collect(Collectors.toList());
//            System.out.println("sortedSequence = " + sortedSequence);
//            assertEquals(sortedSequence, actualSequence);
//        } else {
//            System.out.println("Distance column contains '-- --'");
//        }
//    }

    @Then("I verify the provider records must be returned in a random sorted order")
    public void i_verify_the_provider_records_must_be_returned_in_a_random_sorted_order() {
        waitFor(3);
        assertNotEquals(crmSearchProviderPart2UIPage.providerNames.get(1).getText(), crmSearchProviderPart2UIPage.providerNames.get(2).getText());
    }

    @Then("I verify I would not see the distance column")
    public void i_verify_I_would_not_see_the_distance_column() {
        try {
            assertFalse(crmSearchProviderPart2UIPage.distancecolumn.isDisplayed());
        } catch (Exception e) {
        }
    }

    @When("no distance value has been specified")
    public void no_distance_value_has_been_specified() {
        crmSearchProviderPart2UIPage.distanceField.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.distanceField.sendKeys(Keys.DELETE);
    }

    @When("I have initiated a search with no values specified")
    public void i_have_initiated_a_search_with_no_values_specified() {
        crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.streetaddressField.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.cityfield.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.zipfield.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.distanceField.sendKeys(Keys.CONTROL + "a");
        crmSearchProviderPart2UIPage.distanceField.sendKeys(Keys.DELETE);
        crmSearchProviderPart2UIPage.searchbutton.click();
    }

    @Then("the provider search results returned should revert to default")
    public void the_provider_search_results_returned_should_revert_to_default() {
        waitFor(3);
        assertTrue(crmSearchProviderPart2UIPage.streetaddressField.getAttribute("value").equalsIgnoreCase("22 main st") || crmSearchProviderPart2UIPage.streetaddressField.getAttribute("value").equalsIgnoreCase("")
                , "Street address field is mismatch");
        assertTrue(crmSearchProviderPart2UIPage.cityfield.getAttribute("value").equalsIgnoreCase("Evans") || crmSearchProviderPart2UIPage.cityfield.getAttribute("value").equalsIgnoreCase(""), "City field is mismatch");
        assertTrue(crmSearchProviderPart2UIPage.zipfield.getAttribute("value").equalsIgnoreCase("30809") || crmSearchProviderPart2UIPage.zipfield.getAttribute("value").equalsIgnoreCase(""), "Zip field is mismatch");
        assertTrue(crmSearchProviderPart2UIPage.distanceField.getAttribute("value").equalsIgnoreCase("15") || crmSearchProviderPart2UIPage.distanceField.getAttribute("value").equalsIgnoreCase(""), "Distance field is mismatch");
    }

    @Then("the search criteria must be set to null values")
    public void the_search_criteria_must_be_set_to_null_values() {
        waitFor(3);
        assertEquals(crmSearchProviderPart2UIPage.streetaddressField.getText(), "");
        assertEquals(crmSearchProviderPart2UIPage.cityfield.getText(), "");
        assertEquals(crmSearchProviderPart2UIPage.zipfield.getText(), "");
        assertEquals(crmSearchProviderPart2UIPage.distanceField.getText(), "");
    }

    @Then("I verify the search results must be removed")
    public void i_verify_the_search_results_must_be_removed() {
        try {
            assertFalse(crmSearchProviderPart2UIPage.searchResult.isDisplayed());
        } catch (Exception e) {
        }
    }

    @Then("I verify the following search criterias will be displayed: Provider FIRST NAME, Provider LAST NAME, Group Name, Provider PHONE NUMBER, PLAN NAME")
    public void i_verify_the_following_search_criterias_will_be_displayed_Provider_FIRST_NAME_Provider_LAST_NAME_Group_Name_Provider_PHONE_NUMBER_PLAN_NAME() {
        assertTrue(crmSearchProviderPart2UIPage.providerplanName.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providergroupname.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.ProviderPhoneno.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerFirstname.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.providerLastname.isDisplayed());


    }


    @Then("I verify search and cancel buttons are displayed in provider search page")
    public void i_verify_search_and_cancel_buttons_are_displayed_in_provider_search_page() {
        assertTrue(crmSearchProviderPart2UIPage.searchbutton.isDisplayed());
        assertTrue(crmSearchProviderPart2UIPage.cancelbutton.isDisplayed());
    }


    @And("I verify provider type dropdown has below options")
    public void I_verify_provider_type_dropdown_has_below_options(List<String> expectedOptions) {
        waitForClickablility(crmSearchProviderPart2UIPage.providertype, 15);
        crmSearchProviderPart2UIPage.providertype.click();
        System.out.println("Getting actual options from dropdown");
        List<String> actualOptions = getElementsText(crmSearchProviderPart2UIPage.dropdownOptions2).stream().sorted().collect(Collectors.toList());
        System.out.println("Getting expected options from data");
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        System.out.println("expectedOptions = " + expectedOptions);
        System.out.println("actualOptions = " + actualOptions);
        assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }

    @And("I click provider type to display its options")
    public void I_click_provider_type_to_display_its_options() {
        crmSearchProviderPart2UIPage.providertype.click();
    }

    @And("I select {string} option from provider type dropdown")
    public void I_verify_dropdown_on_provider_search_page_has_options(String option) {
        waitFor(1);
        crmSearchProviderPart2UIPage.getProviderTypeOptionByName(option).click();
    }


    @Then("I verify below are the options to display more providers in a page")
    public void I_verify_below_are_the_options_to_display_more_providers_in_a_page(List<String> expectedOptions) {
        waitForClickablility(crmSearchProviderPart2UIPage.displayNumberOfProviders, 5);
        crmSearchProviderPart2UIPage.displayNumberOfProviders.click();
        System.out.println("Getting actual options from dropdown");
        List<String> actualOptions = getElementsText(crmSearchProviderPart2UIPage.dropdownOptions2).stream().sorted().collect(Collectors.toList());
        System.out.println("Getting expected options from data");
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        System.out.println("expectedOptions = " + expectedOptions);
        System.out.println("actualOptions = " + actualOptions);
        assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }

    @Then("I verify below are the options to display more providers in a Provider search page")
    public void I_verify_below_are_the_options_to_display_more_providers_in_a_Provider_search_page(List<String> expectedOptions) {
        waitForClickablility(crmSearchProviderPart2UIPage.displayNumberOfProviders, 5);
        crmSearchProviderPart2UIPage.displayNumberOfProviders.click();
        System.out.println("Getting actual options from dropdown");
        List<String> actualOptions = getElementsText(crmSearchProviderPart2UIPage.dropdownOptions2).stream().sorted().collect(Collectors.toList());
        System.out.println("Getting expected options from data");
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        System.out.println("expectedOptions = " + expectedOptions);
        System.out.println("actualOptions = " + actualOptions);
        assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }

    @Then("I verify warning message is displayed for returning providers found")
    public void i_verify_warning_message_is_displayed_for_returning_providers_found() {
//        waitFor(3);
//        try{
//            System.out.println("Now asserting warning message text");
//            assertTrue(crmSearchProviderPart2UIPage.warningMessageText.isDisplayed());
//        } catch(Exception e){
//            System.out.println("Waiting for visibility of warning message");
//            waitForVisibility(crmSearchProviderPart2UIPage.warningMessageText,20);
//            System.out.println("Now asserting warning message text");
//            assertTrue(crmSearchProviderPart2UIPage.warningMessageText.isDisplayed());
//        }
        System.out.println("Waiting for visibility of warning message");
        waitForVisibility(crmSearchProviderPart2UIPage.warningMessageText, 10);
        System.out.println("Now asserting warning message text");
        assertTrue(crmSearchProviderPart2UIPage.warningMessageText.isDisplayed());
        String[] warningMessageWords = crmSearchProviderPart2UIPage.warningMessageText.getText().split(" ");
        numberOfDisplayedProviders.set(warningMessageWords[1]);
        numberOfTotalProviders.set(warningMessageWords[3]);
        System.out.println("Displayed Providers : " + numberOfDisplayedProviders.get());
        System.out.println("Total Providers : " + numberOfTotalProviders.get());
    }

    @And("I select {string} as the number of returned providers to be displayed on each page")
    public void i_select_as_the_number_of_returned_providers_to_be_displayed_on_each_page(String value) {
        crmSearchProviderPart2UIPage.getDropDownOptionByValue(value).click();
    }

    @Then("I verify the number of returned providers matches the number in warning message")
    public void i_verify_the_number_of_returned_providers_matches_the_number_in_warning_message() {
        int countOfDisplayedProviders = 0;
        countOfDisplayedProviders += crmSearchProviderPart2UIPage.providerNames.size();
        System.out.println("Counting Displayed Providers : " + countOfDisplayedProviders);
        boolean moreProvidersInNextPage = true;
        try {
            if (crmSearchProviderPart2UIPage.forwardPaginator.isDisplayed()) moreProvidersInNextPage = true;
            else moreProvidersInNextPage = false;

        } catch (Exception e) {
            moreProvidersInNextPage = false;
        }
        while (moreProvidersInNextPage) {
            crmSearchProviderPart2UIPage.forwardPaginator.click();
            countOfDisplayedProviders += crmSearchProviderPart2UIPage.providerNames.size();
            System.out.println("Counting Displayed Providers : " + countOfDisplayedProviders);
            try {
                if (crmSearchProviderPart2UIPage.forwardPaginator.isDisplayed()) moreProvidersInNextPage = true;
                else moreProvidersInNextPage = false;

            } catch (Exception e) {
                moreProvidersInNextPage = false;
            }
        }
        System.out.println("Number of displayed providers in warning message: " + numberOfDisplayedProviders.get());
        assertEquals(String.valueOf(countOfDisplayedProviders), numberOfDisplayedProviders.get(), " The number of displayed providers do not match the number in warning message");
        crmSearchProviderPart2UIPage.goToPageNumber(1).click();
    }


    @When("I click the first checkmark button")
    public void i_click_the_first_Checkmark_button() {
        scrollUpUsingActions(5);
        crmSearchProviderPart2UIPage.firstCheckMarkButton.click();
        waitFor(3);
    }

    @Then("I verify the following fields under Selected Providers will be displayed: {string}, {string}, {string}")
    public void i_verify_the_following_fields_under_Selected_Providers_will_be_displayed(String string, String string2, String string3) {
        crmSearchProviderPart2UIPage.providerNameUnderSelectedProviders.isDisplayed();
        crmSearchProviderPart2UIPage.providerTypeUnderSelectedProviders.isDisplayed();
        crmSearchProviderPart2UIPage.NPIUnderSelectedProviders.isDisplayed();
    }

    @And("I click on search button")
    public void i_click_on_search_button() {
        click(crmSearchProviderPart2UIPage.searchbutton);
        System.out.println("Searching ...");
    }

    @Then("I verify the following fields value under Selected Providers will be displayed")
    public void i_verify_the_following_fields_under_Selected_Providers_will_be_displayed_with_data(Map<String, String> fieldNames) {
        for (String s : fieldNames.keySet()) {
            switch (s) {
                case "PROVIDER NAME":
                    assertTrue(crmSearchProviderPart2UIPage.providerNameDataUnderSelectedProviders.get(0).getText().equals(providerName.get().get("PDPName")) ||
                            crmSearchProviderPart2UIPage.providerNameDataUnderSelectedProviders.get(0).getText().equals(providerName.get().get("PCPName")), s + " field value is mismatch");
                    assertTrue(crmSearchProviderPart2UIPage.providerNameDataUnderSelectedProviders.get(1).getText().equals(providerName.get().get("PDPName")) ||
                            crmSearchProviderPart2UIPage.providerNameDataUnderSelectedProviders.get(1).getText().equals(providerName.get().get("PCPName")), s + " field value is mismatch");
                    break;
                case "PROVIDER TYPE":
                    assertTrue(crmSearchProviderPart2UIPage.providerTypeDataUnderSelectedProviders.get(0).getText().equals("Dental") ||
                            crmSearchProviderPart2UIPage.providerTypeDataUnderSelectedProviders.get(0).getText().equals("Medical"), s + " field value is mismatch");
                    assertTrue(crmSearchProviderPart2UIPage.providerTypeDataUnderSelectedProviders.get(1).getText().equals("Dental") ||
                            crmSearchProviderPart2UIPage.providerTypeDataUnderSelectedProviders.get(1).getText().equals("Medical"), s + " field value is mismatch");
                    break;
                case "NPI":
                    assertFalse(crmSearchProviderPart2UIPage.providerNpiDataUnderSelectedProviders.get(0).getText().equals("-- --"), "NPI is empty");
                    assertFalse(crmSearchProviderPart2UIPage.providerNpiDataUnderSelectedProviders.get(1).getText().equals("-- --"), "NPI is empty");
                    break;
            }
        }
    }

    @And("I verify {string}, {string} and {string} are displayed")
    public void iVerifyLanguagesSpecialtiesAndHospitalAffiliationsAreDisplayed(String language, String speacilty, String hospitalAffiliation) {
        List<String> langaugeList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerLanguages) {
            langaugeList.add(s.getText());
        }
        assertTrue(langaugeList.contains(language));
        List<String> speaciltyList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerSpecialties) {
            speaciltyList.add(s.getText());
        }
        assertTrue(speaciltyList.contains(speacilty));
        List<String> affiliationsList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerHospitalAffiliations) {
            affiliationsList.add(s.getText());
        }
        assertTrue(affiliationsList.contains(hospitalAffiliation));
    }

    @And("I verify {string}, {string} and {string} are not fetched")
    public void iVerifyLanguagesSpecialtiesAndHospitalAffiliationsIsNotFetched(String language, String speacilty, String hospitalAffiliation) {
        List<String> langaugeList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerLanguages) {
            langaugeList.add(s.getText());
        }
        assertFalse(langaugeList.contains(language));

        List<String> speaciltyList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerSpecialties) {
            speaciltyList.add(s.getText());
        }
        assertFalse(speaciltyList.contains(speacilty));

        List<String> affiliationsList = new ArrayList<>();
        for (WebElement s : crmSearchProviderPart2UIPage.providerHospitalAffiliations) {
            affiliationsList.add(s.getText());
        }
        assertFalse(affiliationsList.contains(hospitalAffiliation));
    }

    @When("I click the Select Provider button on Provider Details Page")
    public void i_click_the_Select_Provider_button_On_Provider_Details_Page() {
        waitFor(2);
        scrollUpUsingActions(1);
        crmSearchProviderPart2UIPage.selectProvider.click();
    }

    @Then("Save First Provider name on enrollment update page")
    public void save_first_provider_data() {
        if (crmSearchProviderPart2UIPage.providertype.getText().equals("Dental")) {
            providerData.get().put("PDPName", crmSearchProviderPart2UIPage.firstProviderName.get(0).getText());
            providerName.get().put("PDPName", crmSearchProviderPart2UIPage.firstProviderName.get(0).getText());
            String pdp = providerData.get().get("PDPName").split(" ")[0] + " " + providerData.get().get("PDPName").split(" ")[2];
            providerData.get().put("PDPName", pdp);
        } else {
            providerData.get().put("PCPName", crmSearchProviderPart2UIPage.firstProviderName.get(0).getText());
            providerName.get().put("PCPName", crmSearchProviderPart2UIPage.firstProviderName.get(0).getText());
            String pcp = providerData.get().get("PCPName").split(" ")[0] + " " + providerData.get().get("PCPName").split(" ")[2];
            providerData.get().put("PCPName", pcp);
        }
    }

    @And("I verify the provider is affiliated to following hospitals")
    public void iVerifyTheProviderIsAffiliatedToFollowingHospitals(List<String> expectedHospitalNames) {
        List<String> actualHospitalNames = new ArrayList<>();
        for (WebElement e : crmSearchProviderPart2UIPage.providerHospitalAffiliations) {
            actualHospitalNames.add(e.getText());
        }
        assertEquals(expectedHospitalNames, actualHospitalNames);
    }
}

