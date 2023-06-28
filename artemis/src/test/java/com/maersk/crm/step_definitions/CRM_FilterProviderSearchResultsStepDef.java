package com.maersk.crm.step_definitions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.Multimap;
import com.maersk.crm.pages.crm.CRMFilterProviderResultsPage;
import com.maersk.crm.pages.crm.CRMProviderDetailsPage;
import com.maersk.crm.pages.crm.CRMProviderSearchPage;
import com.maersk.crm.pages.crm.CRMSearchProviderPart2UIPage;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Api_CommonSteps;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/13/2020
 */
public class CRM_FilterProviderSearchResultsStepDef extends CRMUtilities implements ApiBase {
    CRMFilterProviderResultsPage filterResult = new CRMFilterProviderResultsPage();
    CRMProviderSearchPage searchPage = new CRMProviderSearchPage();
    CRMProviderDetailsPage providerDetail = new CRMProviderDetailsPage();
    CRMSearchProviderPart2UIPage searchProviderPart2UIPage = new CRMSearchProviderPart2UIPage();
    final ThreadLocal<Api_CommonSteps> api_common = ThreadLocal.withInitial(Api_CommonSteps::new);
    final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);

    final ThreadLocal<Multimap<String, String>> multimap = ThreadLocal.withInitial(ArrayListMultimap::create);
    final ThreadLocal<String> a = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> c = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> str1 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> str2 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> str3 = ThreadLocal.withInitial(String::new);

    final ThreadLocal<List<String>> actualOptions = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> actualAddresses = ThreadLocal.withInitial(ArrayList::new);

    @And("I will be able to filter the results by Provider Name")
    public void iWillBeAbleToFilterTheResultsByProviderName() throws Exception {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(filterResult.firstNameFromTable.getText());
        firstName.set(filterResult.firstNameFromTable.getText());
        hover(filterResult.filterCheckBox);
        filterResult.filterCheckBox.click();
        click(filterResult.filterButton);

    }

    @And("Verify filter options are displayed")
    public void verifyFilterOptionsAreDisplayed() {
        Assert.assertTrue(filterResult.filterFirstNameIcon.isDisplayed(), "Filter provider name not displayed");
//        Assert.assertTrue(filterResult.groupNameFilter.isDisplayed(), "Group name filter is not displayed");       // Implementation change
        Assert.assertTrue(filterResult.planNameFilter.isDisplayed(), "Plan Name filter is not displayed");
        Assert.assertTrue(filterResult.specialityFilter.isDisplayed(), "Speciality filter not displayed");
    }

    @Then("I am able to filter with {string} PROVIDER NAME and {string} GROUP NAME")
    public void verifyFilterWithPROVIDERNAMEAndGROUPNAME(String name, String groupName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
        staticWait(2000);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(groupName);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        jsClick(filterResult.filterButton);
    }

    @And("Verify PROVIDER NAME {string} AND GROUP NAME {string} are displayed")
    public void verifyPROVIDERNAMEANDGROUPNAMEAreDisplayed(String name, String groupName) {
        Assert.assertEquals(filterResult.firstNameFromTable.getText(), name, "Name are not displayed");
        Assert.assertEquals(filterResult.firstGroupNameFromTable.getText(), groupName, "Group Name are not displayed");

    }

    @Then("I am able to filter with {string} PROVIDER NAME with GROUP NAMES {string} and {string}")
    public void iAmAbleToFilterWithPROVIDERNAMEWithTwoGROUPNAMEAnd(String name, String grp1, String grp2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
        staticWait(2000);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(grp1);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        clearAndFillText(filterResult.filterSearch, grp2);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
    }

    @Then("Verify after multi filtering for GROUP NAME {string} and {string} results are displayed")
    public void verifyAfterMultiFilteringResultsAreDisplayed(String grp1, String grp2) {
        String frsGroupName = searchAnyElementInList(filterResult.providerNamesFromTable, grp1);
        String scndGroupName = searchAnyElementInList(filterResult.providerNamesFromTable, grp2);
        Assert.assertEquals(frsGroupName, grp1, "First Group Name Not Found");
        Assert.assertEquals(scndGroupName, grp2, "Second Group Name Not Found");
    }

    @Then("I am able to filter with {string} PROVIDER NAME where GROUP NAME contains {string}")
    public void iAmAbleToFilterWithPROVIDERNAMEWhereGROUPNAMEContains(String name, String groupName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        staticWait(2000);
        click(filterResult.filterButton);
        staticWait(2000);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(groupName);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
    }

    @Then("I filter by Provider Name is {string}")
    public void iFilterByProviderNameIs(String name) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
    }

    @And("Verify Provider Name {string} results list are displayed")
    public void verifyProviderNameResultsListAreDisplayed(String name) {
        Assert.assertTrue(filterResult.allTextInTableIsExist(filterResult.providerNamesFromTable, name), "Provider name are not exist");
    }


    @Then("I am able to filter with {string} PROVIDER NAME with two GROUP NAME {string}")
    public void iAmAbleToFilterWithTwoGROUPNAME(String name, String grp) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(grp);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
    }

    @Then("I filtered Provider name by {string} and {string}")
    public void iFilteredProviderNameByAnd(String name1, String name2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name1);
        hover(filterResult.filterCheckBox);
        filterResult.filterCheckBox.click();
        waitFor(2);
        clearAndFillText(filterResult.filterSearch, name2);
        hover(filterResult.filterCheckBox);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @Then("Verify after multi filtering for PROVIDER NAME {string} and {string} results are displayed")
    public void verifyAfterMultiFilteringForPROVIDERNAMEAndResultsAreDisplayed(String name1, String name2) {
        Assert.assertEquals(searchAnyElementInList(filterResult.providerNamesFromTable, name1), name1, "Name not filtered");
        Assert.assertEquals(searchAnyElementInList(filterResult.providerNamesFromTable, name2), name2, "Name not filtered");
    }

    @And("Verify user should be able to view filtered is enabled in the applied column")
    public void verifyUserShouldBeAbleToViewFilteredIsEnabledInTheAppliedColumn() throws InterruptedException {
        Assert.assertNotEquals(getColorCode(filterResult.colorIconProvider), "#f4f4f4", "Color not matching");
    }

    @Then("Verify user by default should see {int} selected options are displayed")
    public void verifyUserByDefaultShouldSeeSelectedOptionsAreDisplayed(int num) {
        Assert.assertEquals(searchPage.count(filterResult.providerNamesFromTable), num, "Selected options not displayed");
    }

    @And("I searching with first name {string} and last name {string}")
    public void iSearchingForFirstNameAndLastName(String firstName, String lastName) {
        click(searchPage.searchForAdvance);
        searchPage.getFirstName.sendKeys(firstName);
        searchPage.lastName.sendKeys(lastName);
        waitFor(2);
        click(filterResult.searchButton);
    }

    @And("I searching first name {string} and last name {string} with Plan Name {string}")
    public void iSearchingForFirstNameLastNameAndPlanName(String firstName, String lastName, String planName) throws InterruptedException {
        click(searchPage.searchForAdvance);
        searchPage.getFirstName.sendKeys(firstName);
        searchPage.lastName.sendKeys(lastName);
        searchPage.selectDropDown(searchPage.planName, planName);
        click(filterResult.searchButton);
    }

    @And("I searching for first name {string}")
    public void iSearchingForFirstName(String firstName) {
        click(searchPage.searchForAdvance);
        searchPage.getFirstName.sendKeys(firstName);
        click(filterResult.searchButton);
    }

    @And("I click on {string} button on provider search page")
    public void I_clicked_on_advanced_search_button_on_provider_search_page(String buttonName) {
        // possible options: Advanced Search, SEARCH
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        click(searchProviderPart2UIPage.getButtonInGivenParentByName(
                searchProviderPart2UIPage.providerSearchForm, buttonName
        ));
    }

    @And("I verify provider search result contains same NPI {string}")
    public void I_verify_provider_search_result_contains_same_NPI(String npi) {
        List<WebElement> l1 = searchProviderPart2UIPage.expandproviderList;
        System.out.println(l1.size());
        for (int i = 0; i < l1.size(); i++) {
            searchProviderPart2UIPage.expandproviderList.get(i).click();
            waitFor(4);
            a.set(searchProviderPart2UIPage.getListOfNPI.get(i).getText());

            str1.set(searchProviderPart2UIPage.provideraddressResultList.get(i).getText().split("\\n")[0].split(",")[0].split(" ")[0]);
            str2.set(searchProviderPart2UIPage.provideraddressResultList.get(i).getText().split("\\n")[0].split(",")[0].split(" ")[1]);
            str3.set(searchProviderPart2UIPage.provideraddressResultList.get(i).getText().split("\\n")[0].split(",")[0].split(" ")[2]);

            c.set(searchProviderPart2UIPage.providerplannameList.get(i).getText());
            str1.set(str1.get() + str2.get() + str3.get());

            multimap.get().put(str1.get(), c.get());
            Assert.assertEquals(a.get(), npi);
        }


    }

    @And("I verify provider search result contains different plans for address {string}")
    public void I_verify_provider_search_result_contain_different_addresses(String address) {

        String temp = address.replace(" ", "");
        System.out.println(multimap.get());
        if (multimap.get().containsKey(temp)) {
            Collection<String> s = multimap.get().get(temp);
            // System.out.println(s);
            List<String> s1 = s.stream().distinct().collect(Collectors.toList());
            //  System.out.println(s1);
            Assert.assertEquals(s, s1); // This line verifies plan names are different for a provider for same address.

        }
    }
/*
    @When("I select Age Range field")
    public void i_select_Age_Range() {
        searchProviderPart2UIPage.ageRangeField.click();
        waitFor(2);
    }
*/

    @Then("I verify records fetched contain Gender Served value as {string}")
    public void i_verify_records_fetched_contain_gender_served_value_as_something(String strArg1) {

        for (int i = 0; i < searchProviderPart2UIPage.expandArrow.size(); i++) {
            waitFor(1);
            searchProviderPart2UIPage.expandArrow.get(i).click();
            waitFor(1);
            String temp = searchProviderPart2UIPage.getGenderServedfromrecords.get(i).getText();
            Assert.assertEquals(temp, strArg1, "Gender value mismatch");
            searchProviderPart2UIPage.minimizeArrow.click();
        }
    }

    @Then("I verify records fetched contain all Gender Served values")
    public void i_verify_records_fetched_contain_all_gender_served_values() {
        for (int i = 0; i < searchProviderPart2UIPage.expandArrow.size(); i++) {
            waitFor(1);
            searchProviderPart2UIPage.expandArrow.get(i).click();
            waitFor(1);
            String temp = searchProviderPart2UIPage.getGenderServedfromrecords.get(i).getText();
            Assert.assertTrue(temp.equals("Male") || temp.equals("Female") || temp.equals("Both Male and Female") || temp.equals("-- --"), "Invalid gender served value");
            searchProviderPart2UIPage.minimizeArrow.click();
        }
    }


    @And("I verify first name search results contains value and not just starts with {string}")
    public void i_verify_first_name_search_results_contains_value_and_not_starts_with(String val) {
        List<WebElement> l1 = searchProviderPart2UIPage.providernameResultList;
        List<String> l2 = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            String temp = l1.get(i).getText().split(" ")[0];
            Assert.assertTrue(temp.toLowerCase().contains(val));
            l2.add(temp);
        }
        System.out.println(l2);
    }

    @And("I verify Last Name search results contains value and not just starts with {string}")
    public void i_verify_Last_name_search_results_contains_value_and_not_just_starts_with(String val) {
        List<WebElement> l1 = searchProviderPart2UIPage.providernameResultList;
        List<String> l2 = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            String last = "";

            String[] temp1 = l1.get(i).getText().split(" ");
            if (temp1.length == 2)
                last = temp1[1];
            else if (temp1.length == 3)
                last = temp1[2];

            Assert.assertTrue(last.contains(val));
            l2.add(last);
        }
        System.out.println(l2);
    }

    @And("I verify Group Name search results contains value and not just starts with {string}")
    public void i_verify_group_name_search_results_contains_value_and_not_just_starts_with(String val) {
        List<WebElement> l1 = searchProviderPart2UIPage.providergroupnameList;

        List<String> l2 = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            String temp = l1.get(i).getText();
            Assert.assertTrue(temp.contains(val));
            l2.add(temp);
        }
        System.out.println(l2);


    }


    @And("I verify dropdown {string} on provider search page has options")
    public void I_verify_dropdown_on_provider_search_page_has_options(String dropdownName, List<String> expectedOptions) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        click(searchProviderPart2UIPage.getFieldInGivenParent(
                searchProviderPart2UIPage.providerSearchForm, dropdownName));
        waitForVisibility(searchProviderPart2UIPage.presentationPopUp, 2);
        List<String> actualOptions = getElementsText(searchProviderPart2UIPage.dropdownOptions).stream().sorted().collect(Collectors.toList());
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        System.out.println("expectedOptions = " + expectedOptions);
        System.out.println("actualOptions = " + actualOptions);
        Assert.assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }


    @And("I verify dropdown Age Range on provider search page has options")
    public void I_verify_dropdown_age_range_on_provider_search_page_has_options(List<String> expectedOptions) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);

        click(searchProviderPart2UIPage.patientAgeRangeInput);


        waitForVisibility(searchProviderPart2UIPage.presentationPopUp, 2);
        List<String> actualOptions = getElementsText(searchProviderPart2UIPage.dropdownOptions).stream().sorted().collect(Collectors.toList());
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }
    @Then("I verify dropdown County name on provider search page has options")
    public void I_verify_dropdown_County_name_on_provider_search_page_has_options(List<String> expectedOptions) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        click(searchProviderPart2UIPage.CountyNameOption);
        List<String> actualOptions = getElementsText(searchProviderPart2UIPage.Countydropdownlist).stream().sorted().collect(Collectors.toList());
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }
    @And("I verify dropdown Gender Served on provider search page has options")
    public void I_verify_dropdown_gender_served_on_provider_search_page_has_options(List<String> expectedOptions) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);

        click(searchProviderPart2UIPage.genderServedInput);

        waitForVisibility(searchProviderPart2UIPage.presentationPopUp, 2);
        List<String> actualOptions = getElementsText(searchProviderPart2UIPage.genderServedDropdownList).stream().sorted().collect(Collectors.toList());
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(actualOptions, expectedOptions, "dropdown options doesn't contain all expected options");
    }

    @And("I verify dropdown Provider Type on provider search page has options")
    public void I_verify_dropdown_field_on_provider_search_page_has_options(List<String> expectedOptions) {

        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        click(searchProviderPart2UIPage.providerTypeDropdown);
        waitForVisibility(searchProviderPart2UIPage.presentationPopUp, 2);
        synchronized (actualOptions){
            actualOptions.set(getElementsText(searchProviderPart2UIPage.genderServedDropdownList).stream().sorted().collect(Collectors.toList()));
        }
        expectedOptions = expectedOptions.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(actualOptions.get(), expectedOptions, "dropdown options doesn't contain all expected options");

    }

    @When("I select gender served {string} value from dropdown")
    public void i_select_gender_served_value_from_dropdown(String Option) throws Throwable {
        selectDropDown(searchProviderPart2UIPage.genderServedInput, Option);

    }


    @And("I fill out fields on provider search page with data")
    public void I_fill_out_fields_on_provider_search_page_with_data(Map<String, String> data) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        for (String key : data.keySet()) {
            if (data.get(key).equalsIgnoreCase("skip")) {
                clearText(searchProviderPart2UIPage.getInputByFieldInGivenParent(
                        searchProviderPart2UIPage.providerSearchForm, key
                ));
            } else if (Arrays.asList("FIRST NAME", "LAST NAME", "GROUP NAME", "PHONE NUMBER",
                    "STREET ADDRESS", "DISTANCE (mi)", "NPI").contains(key)) {
                System.out.println("Filling " + key + " field with " + data.get(key));
                clearAndFillText2(searchProviderPart2UIPage.getInputByFieldInGivenParent(
                        searchProviderPart2UIPage.providerSearchForm, key
                ), String.valueOf(api_common.get().defineValue(data.get(key))));
            } else {
                selectDropDown(searchProviderPart2UIPage.getInputByFieldInGivenParent(
                        searchProviderPart2UIPage.providerSearchForm, key
                ), String.valueOf(api_common.get().defineValue(data.get(key))));
            }
        }
    }

    @And("I fill out Provider Type field on provider search page with value {string}")
    public void i_fill_out_provider_type_field_with_value_something(String strArg1) {
        selectDropDown(searchProviderPart2UIPage.providerTypeInputBx, strArg1);
    }

    @When("I fill out field Handicap Accessible on provider search page with data {string}")
    public void i_fill_out_field_Handicap_Accessible_on_provider_search_page_with_data(String strArg1) {
        selectDropDown(searchProviderPart2UIPage.handicapAccessibleInputBx, strArg1);
    }

    @When("I fill out field Accepting new patients on provider search page with data {string}")
    public void i_fill_out_field_Accepting_new_patients_on_provider_search_page_with_data(String strArg1) {
        selectDropDown(searchProviderPart2UIPage.acceptingNewPatientsDrop, strArg1);
    }


    @Then("^I verify provider's Handicap Accessible value \"([^\"]*)\" in provider search result$")
    public void i_verify_providers_handicap_accessible_value_something_in_provider_search_result(String strArg1) {
        if (strArg1.equals("No")) {
            for (int i = 1; i < searchProviderPart2UIPage.getNotHandicapAccessibleIcon.size(); i++)
                Assert.assertTrue(searchProviderPart2UIPage.getNotHandicapAccessibleIcon.get(i).isDisplayed(), "Handicap Accessible..! FAIL!!");
        } else {
            for (int i = 1; i < searchProviderPart2UIPage.getHandicapAccessibleIcon.size(); i++)
                Assert.assertTrue(searchProviderPart2UIPage.getHandicapAccessibleIcon.get(i).isDisplayed(), "Not Handicap Accessible..! FAIL!!");
            System.out.println("Yes");
        }

    }


    @And("I verify fields on provider search page with data")
    public void I_verify_fields_on_provider_search_page_with_data(Map<String, String> data) {
        waitForVisibility(searchProviderPart2UIPage.providerSearch, 2);
        for (String key : data.keySet()) {
            System.out.println("validating " + key + " in search criteria if value " + data.get(key));
            if (data.get(key).equalsIgnoreCase("is displayed")) {
                try {
                    Assert.assertTrue(searchProviderPart2UIPage.getFieldInGivenParent(
                            searchProviderPart2UIPage.providerSearchForm, key
                    ).isDisplayed());
                    System.out.println(key + " is displayed! - PASS!");
                } catch (Exception e) {
                    System.out.println(key + " is not displayed! - FAIL!");
                }
            } else if (data.get(key).equalsIgnoreCase("hidden")) {
                try {
                    Assert.assertFalse(searchProviderPart2UIPage.getFieldInGivenParent(
                            searchProviderPart2UIPage.providerSearchForm, key
                    ).isDisplayed(), key + " is displayed! - FAIL!");
                } catch (Exception e) {
                    System.out.println(key + " is hidden.. Pass!");
                }
                // AGE RANGE locator has additional whitespace so to not fail this else if block is added in additioon to else block below
            } else if (key.equals("AGE RANGE")) {
                Assert.assertEquals(searchProviderPart2UIPage.ageRangeField
                        .getAttribute("value"), data.get(key), key + " mismatch!");
            } else {
                waitFor(2);
                Assert.assertEquals(searchProviderPart2UIPage.getInputByFieldInGivenParent(
                        searchProviderPart2UIPage.providerSearchForm, key
                ).getAttribute("value"), data.get(key), key + " mismatch!");
            }
        }
    }

    @And("I verify provider search results table fields At A Glance View with data")
    public void I_verify_provider_search_results_table_fields_At_A_Glance_View_with_data(Map<String, String> data) {
        waitForVisibility(searchProviderPart2UIPage.providerSearchResultForm, 2);
        waitFor(1);
        for (String key : data.keySet()) {
            if (!data.get(key).equalsIgnoreCase("hidden")) {
                System.out.println("checking if " + key + " is displayed");
                try {
                    Assert.assertTrue(searchProviderPart2UIPage.getProviderSearchResultTableHeaderInGivenParentByName(
                            searchProviderPart2UIPage.providerSearchResultForm, key
                    ).isDisplayed());
                } catch (Exception e) {
                    System.out.println(key + " is not displayed!");
                }
            }
            switch (key) {
                case "PROVIDER ADDRESS":
                    List<WebElement> addresses = searchProviderPart2UIPage.getProviderAddressfieldsInGivenParent(
                            searchProviderPart2UIPage.providerSearchResultForm
                    );
                    for (WebElement eachAddress : addresses) {
                        System.out.println("validating " + eachAddress.getText());
                        String[] addressLine = eachAddress.getText().split(",");
                        String streetAddress = addressLine.length > 4
                                ? addressLine[0] + addressLine[1]
                                : addressLine[0];

                        String city = eachAddress.getText().split("\\n")[0].split(",")[eachAddress.getText().split("\\n")[0].split(",").length - 1].trim();
                        String state = eachAddress.getText().split("\\n")[1].split(" ")[0].trim();
                        String zip = eachAddress.getText().split("\\n")[1].split(" ")[2].trim();
                        String phone = eachAddress.getText().split("\\n")[1].substring(16, 28);
//                        String city = addressLine[addressLine.length].trim();
//                        String state = addressLine[addressLine.length - 2].trim();
//                        String zip = addressLine[addressLine.length - 1].trim().split("\\s", 2)[0];
//                        String phone = addressLine[addressLine.length - 1].trim().split("\\s", 3)[2].substring(0, 12);

                        Assert.assertTrue(streetAddress.matches("^\\d+.*$"));
                        Assert.assertTrue(city.matches("^[A-Za-z\\s]*.$"));
                        Assert.assertTrue(state.matches("^[A-Z]{2}$"));
                        Assert.assertTrue(zip.matches("^\\d{5}$"));
                        Assert.assertTrue(phone.matches("^(\\d{3}\\s){2}\\d{4}$"));
                        try {
                            Assert.assertTrue(searchProviderPart2UIPage.getAcceptingNewPatiensIconInGivenParent(eachAddress).isDisplayed());
                        } catch (Exception e) {
                            System.out.println("icon at address " + streetAddress + " is not displayed!");
                        }
                    }
                    break;
                case "DISTANCE":
                    System.out.println("validating DISTANCE in " + data.get(key) + " order");
                    List<String> distances = getElementsText(searchProviderPart2UIPage.getProviderDistanceFieldsInGivenParent(
                            searchProviderPart2UIPage.providerSearchResultForm
                    ));
                    System.out.println("actual distances order = " + distances.toString());
                    if (data.get(key).equalsIgnoreCase("ascending order")) {
                        List<String> ascending = distances.stream().sorted().collect(Collectors.toList());
                        System.out.println("expected ascending order = " + ascending);
                        Assert.assertEquals(distances, ascending, "distance is not in ascending order!");
                    } else if (data.get(key).equalsIgnoreCase("descending order")) {
                        List<String> ascending = distances.stream().sorted().collect(Collectors.toList());
                        List<String> descending = ascending.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
                        System.out.println("expected descending order = " + descending);
                        Assert.assertEquals(distances, descending, "distance is not in descending order!");
                    } else {
                        Assert.assertEquals(distances.get(0), data.get(key), key + " missmatch!");
                    }
                    break;
            }
        }

    }

    @Then("I verify value in Distance column rounded to one digit after decimal point")
    public void i_verify_value_in_distance_column_rounded_to_one_digit_after_decimal_point() {
        List<String> distances = getElementsText(searchProviderPart2UIPage.getProviderDistanceFieldsInGivenParent(
                searchProviderPart2UIPage.providerSearchResultForm));
        System.out.println("actual distances order = " + distances.toString());
        for (String s : distances) {
            if (s.contains(".")) {
                String temp = s.split("\\.")[1];
                Assert.assertTrue(temp.length() == 1);
            }
        }
    }

    @And("I verify provider serch results table fields At Expanded View with data")
    public void I_verify_provider_serch_results_table_fields_At_Expanded_View_with_data(Map<String, String> data) {
        waitForVisibility(searchProviderPart2UIPage.firstFoundProvider, 2);
        click(searchProviderPart2UIPage.getExpandButtonInGivenParent(
                searchProviderPart2UIPage.firstFoundProvider
        ));
        waitFor(2);
        for (String key : data.keySet()) {
            System.out.println("validating " + key + " in Expanded View if value " + data.get(key));
            if (data.get(key).equalsIgnoreCase("is displayed")) {
                try {
                    Assert.assertTrue(searchProviderPart2UIPage.getFieldInGivenParent(
                            searchProviderPart2UIPage.providerSearchForm, key
                    ).isDisplayed());
                    System.out.println(key + " is displayed! - PASS!");
                } catch (Exception e) {
                    System.out.println(key + " is not displayed! - FAIL!");
                }
            } else if (data.get(key).equalsIgnoreCase("hidden")) {
                try {
                    Assert.assertFalse(searchProviderPart2UIPage.getFieldInGivenParent(
                            searchProviderPart2UIPage.providerSearchForm, key
                    ).isDisplayed(), key + " is displayed! - FAIL!");
                } catch (Exception e) {
                    System.out.println(key + " is hidden.. Pass!");
                }
            } else {
                Assert.assertEquals(searchProviderPart2UIPage.getInputByFieldInGivenParent(
                        searchProviderPart2UIPage.providerSearchForm, key
                ).getAttribute("value"), data.get(key), key + " mismatch!");
            }
        }
    }

    @And("I click on first found provider from provider search result")
    public void clickOnFirstFoundProvider() {
        waitFor(2);
        hover(searchProviderPart2UIPage.firstFoundProviderName);
        searchProviderPart2UIPage.firstFoundProviderName.click();
        waitFor(2);
    }

    @And("I click on first found provider and verify Provider Details with data")
    public void I_click_on_first_found_provider_and_verify_Provider_Details_with_data(Map<String, String> data) {
//        waitForVisibility(searchProviderPart2UIPage.firstFoundProviderName, 2);
        hover(searchProviderPart2UIPage.firstFoundProviderName);
        waitFor(1);
        searchProviderPart2UIPage.firstFoundProviderName.click();
        waitFor(2);
        for (String key : data.keySet()) {
            System.out.println("validating " + key + " in Provider Details if it " + data.get(key));
            switch (key.split("\\.")[0]) {
                case "PROVIDER DETAILS":
                    if (data.get(key).equals("is displayed")) {
                        try {
                            Assert.assertTrue(searchProviderPart2UIPage.getFieldInsideProviderDetailsByName(
                                    key.split("\\.")[1]
                            ).isDisplayed());
                            System.out.println(key + " is displayed!- PASS!");
                        } catch (Exception e) {
                            System.out.println(key + " is not displayed! - FAILL!");
                        }
                    } else if (data.get(key).equalsIgnoreCase("hidden")) {
                        try {
                            Assert.assertFalse(searchProviderPart2UIPage.getFieldInsideProviderDetailsByName(
                                    key.split("\\.")[1]
                            ).isDisplayed(), key + "is displayed! - FAIL!");
                        } catch (Exception e) {
                            System.out.println(key + " is not displayed! - PASS!");
                        }
                    } else {
                        Assert.assertEquals(searchProviderPart2UIPage.getFieldInsideProviderDetailsByName(
                                key.split("\\.")[1]
                        ).getText(), data.get(key), key + " mismatch!");
                    }
                    break;
                case "LANGUAGES":
                    List<WebElement> languages = searchProviderPart2UIPage.providerLanguages;
                    if (data.get(key).equals("1 - 10")) {
                        Assert.assertTrue(languages.size() > 0 && languages.size() < 11, key + " count out of expected range! - FAIL!");
                    } else if (data.get(key).equals("is displayed")) {
                        for (int i = 0; i < languages.size(); i++) {
                            System.out.println("language-> " + searchProviderPart2UIPage.providerLanguages.get(i).getText());
                            Assert.assertTrue(searchProviderPart2UIPage.providerLanguages.get(i).isDisplayed());
                        }
                    } else {
                        List<String> expectedSpecialties = Arrays.stream(data.get(key).split(", ")).sorted().collect(Collectors.toList());
                        List<String> actualSpecialties = getElementsText(languages).stream().sorted().collect(Collectors.toList());
                        System.out.println("expectedLanguages = " + expectedSpecialties);
                        System.out.println("actualLanguages = " + actualSpecialties);
                        Assert.assertEquals(actualSpecialties, expectedSpecialties, "Actual languages mismatch! - Fail!");
                    }
                    break;
                case "SPECIALITY":
                    List<WebElement> specialties = searchProviderPart2UIPage.providerSpecialties;
                    if (data.get(key).equals("1 - 10")) {
                        Assert.assertTrue(specialties.size() > 0 && specialties.size() < 11, key + " count out of expected range! - FAIL!");
                    } else if (data.get(key).equals("is displayed")) {
                        for (int i = 0; i < specialties.size(); i++) {
                            System.out.println("specialty-> " + searchProviderPart2UIPage.providerSpecialties.get(i).getText());
                            Assert.assertTrue(searchProviderPart2UIPage.providerSpecialties.get(i).isDisplayed());
                        }
                    } else {
                        List<String> expectedSpecialties = Arrays.stream(data.get(key).split(", ")).sorted().collect(Collectors.toList());
                        List<String> actualSpecialties = getElementsText(specialties).stream().sorted().collect(Collectors.toList());
                        System.out.println("expectedSpecialties = " + expectedSpecialties);
                        System.out.println("actualSpecialties = " + actualSpecialties);
                        Assert.assertEquals(actualSpecialties, expectedSpecialties, "Actual specialties mismatch! - Fail!");
                    }
                    break;
                case "HOSPITAL AFFILIATION":
                    List<WebElement> hospitalAffiliations = searchProviderPart2UIPage.providerHospitalAffiliations;
                    if (data.get(key).equals("1 - 5")) {
                        Assert.assertTrue(hospitalAffiliations.size() > 0 && hospitalAffiliations.size() < 6, key + " count out of expected range! - FAIL!");
                    } else if (data.get(key).equals("is displayed")) {
                        for (int i = 0; i < hospitalAffiliations.size(); i++) {
                            System.out.println("hospital-> " + searchProviderPart2UIPage.providerHospitalAffiliations.get(i).getText());
                            Assert.assertTrue(searchProviderPart2UIPage.providerHospitalAffiliations.get(i).isDisplayed());
                        }
                    } else {
                        List<String> expectedAffiliations = Arrays.stream(data.get(key).split(", ")).sorted().collect(Collectors.toList());
                        List<String> actualAffiliations = getElementsText(hospitalAffiliations).stream().sorted().collect(Collectors.toList());
                        System.out.println("expectedSpecialties = " + expectedAffiliations);
                        System.out.println("actualSpecialties = " + actualAffiliations);
                        Assert.assertEquals(actualAffiliations, expectedAffiliations, "Actual affiliations mismatch! - Fail!");
                    }
                    break;
                case "LOCATION ASSOCIATED TO THE PROVIDER":
                    if (data.get(key).equals("is displayed")) {
                        try {
                            Assert.assertTrue(searchProviderPart2UIPage.getFieldInsideLocationsAssociatedToTheProviderByName(
                                    key.split("\\.")[1]
                            ).isDisplayed());
                        } catch (Exception e) {
                            System.out.println(key + " is NOT displayed! - FAIL!");
                        }
                    } else if (data.get(key).equalsIgnoreCase("hidden")) {
                        try {
                            Assert.assertFalse(searchProviderPart2UIPage.getFieldInsideLocationsAssociatedToTheProviderByName(
                                    key.split("\\.")[1]
                            ).isDisplayed(), key + "is displayed! - FAIL!");
                        } catch (Exception e) {
                            System.out.println(key + " is not displayed! - PASS!");
                        }
                    }
                    break;
                case "AGE RANGE":
                    Assert.assertTrue(searchProviderPart2UIPage.getFieldInsideProviderDetailsByName(
                            key.split("\\.")[0]
                    ).isDisplayed(), key + " is displayed not displaying");
                    break;
            }
        }
    }

    @And("I verivy provider search results at {string} column contains cells with values")
    public void I_verivy_provider_search_results_at_column_contains_cells_with_values(String columnName, List<String> expectedCellValues) {
        waitForVisibility(searchProviderPart2UIPage.firstFoundProvider, 2);
        List<String> tableHeads = getElementsText(searchProviderPart2UIPage.providerSearchResultsTableHeads);
        System.out.println("tableHeads = " + tableHeads);
        int columnIndex = 0;
        while (columnIndex < tableHeads.size()) {
            if (tableHeads.get(columnIndex++).contains(columnName)) break;
        }
        List<String> actualCellValues = getElementsText(searchProviderPart2UIPage.getAllCellsByColumnIndex(columnIndex))
                .stream().sorted().collect(Collectors.toList());
        System.out.println("expectedCellValues = " + expectedCellValues);
        System.out.println("actualCellValues = " + actualCellValues);
        Assert.assertTrue(actualCellValues.containsAll(expectedCellValues), "Not all expected values are in " + columnName + "! - FAIL!");
    }


    @And("I verify provider search results table fields with data")
    public void I_verify_provider_search_results_table_fields_with_data(Map<String, String> data) {
        waitForVisibility(searchProviderPart2UIPage.providerSearchResultForm, 2);
        waitFor(1);
        for (String key : data.keySet()) {
            if (!data.get(key).equalsIgnoreCase("hidden")) {
                System.out.println("checking if " + key + " is displayed");
                try {
                    Assert.assertTrue(searchProviderPart2UIPage.getProviderSearchResultTableHeaderInGivenParentByName(
                            searchProviderPart2UIPage.providerSearchResultForm, key
                    ).isDisplayed());
                } catch (Exception e) {
                    System.out.println(key + " is not displayed!");
                }
            }
            switch (key) {
                case "PROVIDER ADDRESS":
                    List<WebElement> addresses = searchProviderPart2UIPage.getProviderAddressfieldsInGivenParent(
                            searchProviderPart2UIPage.providerSearchResultForm
                    );
//                    for (WebElement eachAddress : addresses) {
                    WebElement eachAddress = addresses.get(0);
                    String address = eachAddress.getText();
                    System.out.println("validating " + address);
                    waitFor(5);
                    String firstAddressLine = address.substring(0, address.indexOf("\n"));
                    System.out.println("First address line is : " + firstAddressLine);
                    String streetAddress = firstAddressLine.substring(0, firstAddressLine.lastIndexOf(","));
                    System.out.println("street address : " + streetAddress);
                    String city = firstAddressLine.substring(firstAddressLine.lastIndexOf(",") + 2);
                    System.out.println("city :" + city);
                    String secondAddressLine = address.substring(address.indexOf("\n") + 1);
                    String state = secondAddressLine.substring(0, secondAddressLine.indexOf(" - "));
                    System.out.println("state : " + state);
                    String zip = secondAddressLine.substring(secondAddressLine.indexOf("-") + 2, secondAddressLine.indexOf(" - call"));
                    System.out.println("zip : " + zip);
                    String phone = secondAddressLine.substring(secondAddressLine.length() - 12);
                    System.out.println("phone :" + phone);

                    Assert.assertTrue(streetAddress.matches("^\\d+.*$"));
                    Assert.assertTrue(city.matches("^[A-Za-z]*.$"));
                    Assert.assertTrue(state.matches("^[A-Z]{2}$"));
                    Assert.assertTrue(zip.matches("^\\d{5}$"));
                    Assert.assertTrue(phone.matches("^(\\d{3}\\s){2}\\d{4}$"));
                    try {
                        Assert.assertTrue(searchProviderPart2UIPage.getAcceptingNewPatiensIconInGivenParent(eachAddress).isDisplayed());
                    } catch (Exception e) {
                        System.out.println("accepting new patient icon at address " + streetAddress + " is not displayed!");
                    }
                    try {
                        Assert.assertTrue(searchProviderPart2UIPage.getAcceptingHandicapIconInGivenParent(eachAddress).isDisplayed());
                    } catch (Exception e) {
                        System.out.println("accepting handicap icon at address " + streetAddress + " is not displayed!");
                    }
//                    }
                    break;
                case "DISTANCE":
                    System.out.println("validating DISTANCE in " + data.get(key) + " order");
                    List<String> distances = getElementsText(searchProviderPart2UIPage.getProviderDistanceFieldsInGivenParent(
                            searchProviderPart2UIPage.providerSearchResultForm
                    ));
                    System.out.println("actual distances order = " + distances.toString());
                    if (data.get(key).equalsIgnoreCase("ascending order")) {
                        List<String> ascending = distances.stream().sorted().collect(Collectors.toList());
                        System.out.println("expected ascending order = " + ascending);
                        Assert.assertEquals(distances, ascending, "distance is not in ascending order!");
                    } else if (data.get(key).equalsIgnoreCase("descending order")) {
                        List<String> ascending = distances.stream().sorted().collect(Collectors.toList());
                        List<String> descending = ascending.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
                        System.out.println("expected descending order = " + descending);
                        Assert.assertEquals(distances, descending, "distance is not in descending order!");
                    } else {
                        Assert.assertEquals(distances.get(0), data.get(key), key + " missmatch!");
                    }
                    break;
                case "NAME":
                    System.out.println("validating NAMES in " + data.get(key) + " order");
                    List<String> names = getElementsText(searchProviderPart2UIPage.getProviderNameFieldsInGivenParent(
                            searchProviderPart2UIPage.providerSearchResultForm
                    ));
                    List<String> namesLowercase = names.stream().map(String::toLowerCase).collect(Collectors.toList());
                    System.out.println("actual names order            = " + names.toString());
                    System.out.println("actual lowercase names order  = " + namesLowercase.toString());
                    if (data.get(key).equalsIgnoreCase("ascending order")) {
                        List<String> ascending = namesLowercase.stream().sorted().collect(Collectors.toList());
                        System.out.println("expected ascending order      = " + ascending);
                        Assert.assertEquals(namesLowercase, ascending, "names are not in ascending order!");
                    } else if (data.get(key).equalsIgnoreCase("descending order")) {
                        List<String> ascending = namesLowercase.stream().sorted().collect(Collectors.toList());
                        List<String> descending = ascending.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
                        System.out.println("expected descending order     = " + descending);
                        Assert.assertEquals(namesLowercase, descending, "names are not in descending order!");
                    } else {
                        Assert.assertEquals(names.get(0), data.get(key), key + " missmatch!");
                    }
                    break;
                case "CHECKMARK":
                    if (!data.get(key).equalsIgnoreCase("hidden")) {
                        System.out.println("checking if " + key + " is displayed");
                        try {
                            Assert.assertTrue(searchProviderPart2UIPage.firstCheckMarkButton.isDisplayed());
                        } catch (Exception e) {
                            System.out.println(key + " is not displayed!");
                        }
                    }
                    break;
            }
        }

    }

    @Then("I verify visuals should match the following list in this order on provider search page")
    public void i_verify_visuals_should_match_the_following_list_in_this_order_on_provider_search_page(List<String> expectedFields) {
        List<String> actualFields = new ArrayList<>();
        for (String s : expectedFields) {
            actualFields.add(searchProviderPart2UIPage.getAdvanceSearchField(s).getText());
            Assert.assertTrue(searchProviderPart2UIPage.getAdvanceSearchField(s).isDisplayed());
        }
        Assert.assertEquals(expectedFields, actualFields, "Fields are not in required order");
    }

    @And("I verify office hours segment getting correctly displayed with Sunday in Red Color")
    public void iVerifyOfficeHoursSegmentGettingCorrectlyDisplayedWithHovertext() {
        scrollToElement(searchProviderPart2UIPage.officeHoursSegment);
        String expectedvalue = searchProviderPart2UIPage.officeHoursSegment.getAttribute("class");
        Assert.assertTrue(expectedvalue.contains("red"), "The Sunday field Color is not Red");
    }

    @And("I verify office hours segment getting correctly displayed with Saturday in blue Color")
    public void iVerifyOfficeHoursSegmentGettingCorrectlyDisplayedWithbluecolour() {
        scrollToElement(searchProviderPart2UIPage.officeHoursSegmentsat);
        String expectedvalue = searchProviderPart2UIPage.officeHoursSegmentsat.getAttribute("class");
        Assert.assertTrue(expectedvalue.contains("blue"), "The Saturday field Color is not blue");
    }

    @Then("I mouse over on the office hours to verify hovertext as Evening Hours")
    public void iMouseOverOnTheStatusOfTheNewlyAddedAddress1() {
        Actions builder = new Actions(Driver.getDriver());
        WebElement status = Driver.getDriver().findElement(By.xpath("//td//span[5]"));
        builder.moveToElement(status).perform();
        waitFor(2);
        Assert.assertTrue(searchProviderPart2UIPage.hovertextEveningHours.isDisplayed(), "HoverText is not getting displayed");
    }

    @Then("I mouse over on the office hours to verify hovertext as Evening Hours for day {string}")
    public void iMouseOverOnTheStatusOfTheNewlyAddedAddresswithday(String day) {
        Actions builder = new Actions(Driver.getDriver());
        WebElement status = Driver.getDriver().findElement(By.xpath("//td//span[" + day + "]"));
        builder.moveToElement(status).perform();
        waitFor(2);
        Assert.assertTrue(searchProviderPart2UIPage.hovertextEveningHours.isDisplayed(), "HoverText is not getting displayed");
    }

    @Then("I click on plus sign to expand addresses for first provider and save addresses in a list")
    public void iClickOnPlusSignToExpandAddressesAndSaveAddressesInAList() {
        waitFor(1);
        searchProviderPart2UIPage.expandAddressButton.click();
        waitFor(1);
        actualAddresses.get().add(searchProviderPart2UIPage.firstFoundProviderAddress.getText().split(",")[0].trim());
        for (WebElement s : searchProviderPart2UIPage.expandedAddress) {
            actualAddresses.get().add(s.getText().split(",")[0].trim());
        }
    }

    @And("I verify multiple addresses are getting correctly displayed for a provider")
    public void iVerifyMultipleAddressesAreGettingCorrectlyDisplayedForAProvider() {
        Assert.assertEquals(Integer.parseInt(searchProviderPart2UIPage.expandAddressButton.getText()) + 1, actualAddresses.get().size());
    }

    @And("I verify row for each provider address is displayed in Locations Associated")
    public void iVerifyRowForEachProviderAddressIsDisplayedInLocationsAssociated() {
        List<String> resultAddress = new ArrayList<>();
        for (WebElement s : searchProviderPart2UIPage.addressesInLocationAssociated) {
            resultAddress.add(s.getText().split(",")[0]);
        }
        Assert.assertEquals(actualAddresses.get(), resultAddress);
    }

    @And("I verify program name values displayed as")
    public void iVerifyProgramNameValuesDisplayedAs(List<String> subPrograms) {
        for (String s : subPrograms) {
            Assert.assertTrue(searchProviderPart2UIPage.programName.getText().contains(s), "SubProgram added is not displayed");
        }

    }
}
