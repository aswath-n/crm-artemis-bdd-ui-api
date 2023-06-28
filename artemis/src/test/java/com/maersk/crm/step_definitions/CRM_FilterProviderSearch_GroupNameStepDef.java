package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMFilterProviderResultsPage;
import com.maersk.crm.pages.crm.CRMProviderDetailsPage;
import com.maersk.crm.pages.crm.CRMProviderSearchPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/17/2020
 */
public class CRM_FilterProviderSearch_GroupNameStepDef extends CRMUtilities implements ApiBase {
    CRMFilterProviderResultsPage filterResult = new CRMFilterProviderResultsPage();
    CRMProviderSearchPage searchPage = new CRMProviderSearchPage();
    CRMProviderDetailsPage providerPage = new CRMProviderDetailsPage();


    @And("I searching with Group Name {string}")
    public void iSearchingWithGroupName(String grpName) {
        click(searchPage.searchForAdvance);
        searchPage.inputGroupName.sendKeys(grpName);
        click(filterResult.searchButton);
    }

    @Then("Verify I will be able to filter the results by Group Name {string}")
    public void iWillBeAbleToFilterTheResultsByGroupName(String groupName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(groupName);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
        Assert.assertEquals(filterResult.firstGroupNameFromTable.getText(), groupName,"Group Name are not displayed");
    }

    @Then("I am able to filter with {string} GROUP NAME WITH PROVIDER NAMES {string} and {string}")
    public void iAmAbleToFilterWithGROUPNAMEWITHPROVIDERNAMESAnd(String groupName, String prd1, String prd2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(prd1);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        clearAndFillText(filterResult.filterSearch, prd2);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
        staticWait(2000);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(groupName);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        waitFor(3);
        click(filterResult.filterButton);
    }

    @Then("I filter by Group Name is {string}")
    public void iFilterByGroupNameIs(String grpName) {
        waitForVisibility(filterResult.groupNameFilter, 55);
        filterResult.groupNameFilter.click();
        filterResult.filterSearch.sendKeys(grpName);
        waitForVisibility(filterResult.filterCheckBox, 15);
        click(filterResult.filterCheckBox);
        click(filterResult.filterButton);
    }

    @And("Verify Group Name {string} results list are displayed")
    public void verifyGroupNameResultsListAreDisplayed(String grp) {
        Assert.assertTrue(filterResult.allTextInTableIsExist(filterResult.groupNamesFromTable,grp),"Group name are not exist");
    }

    @Then("I filtered by Group name by {string} and {string}")
    public void iFilteredByGroupNameByAnd(String grp1, String grp2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(grp1);
        filterResult.filterCheckBox.click();
        clearAndFillText(filterResult.filterSearch, grp2);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @And("Verify user should be able to view filtered GROUP NAME is enabled in applied column")
    public void verifyUserShouldBeAbleToViewFilteredGROUPNAMEIsEnabledInAppliedColumn() {
        Assert.assertNotEquals(getColorCode(filterResult.groupNameFilter),"#f4f4f4","Color not matching");
    }


    @Then("Verify I will be able to filter the results by PLAN NAME {string}")
    public void verifyIWillBeAbleToFilterTheResultsByPLANNAME(String planName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.planNameFilter.click();
        filterResult.filterSearch.sendKeys(planName);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
        Assert.assertEquals(filterResult.firstPlanName.getText(), planName,"Plan Name are not displayed");
    }

    @Then("I am able to filter with {string} PROVIDER NAME and {string} PLAN NAME")
    public void iAmAbleToFilterWithPROVIDERNAMEAndPLANNAME(String name, String planName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
        staticWait(2000);
        filterResult.planNameFilter.click();
        filterResult.filterSearch.sendKeys(planName);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @And("Verify PROVIDER NAME {string} AND PLAN NAME {string} are displayed")
    public void verifyPROVIDERNAMEANDPLANNAMEAreDisplayed(String name, String planName) {
        Assert.assertEquals(filterResult.firstNameFromTable.getText(), name, "Name are not displayed");
        Assert.assertEquals(filterResult.firstPlanName.getText(), planName,"Plan Name are not displayed");
    }

    @Then("I am able to filter with {string} PLAN NAME WITH PROVIDER NAMES {string} and {string}")
    public void iAmAbleToFilterWithPLANNAMEWITHPROVIDERNAMESAnd(String planName, String prd1, String prd2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(prd1);
        filterResult.filterCheckBox.click();
        clearAndFillText(filterResult.filterSearch, prd2);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
        staticWait(2000);
        filterResult.planNameFilter.click();
        filterResult.filterSearch.sendKeys(planName);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @Then("I am able to filter with {string} PROVIDER NAME where PLAN NAME contains {string}")
    public void iAmAbleToFilterWithPROVIDERNAMEWherePLANNAMEContains(String name, String planName) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.filterFirstNameIcon.click();
        filterResult.filterSearch.sendKeys(name);
        filterResult.filterCheckBox.click();
        staticWait(2000);
        filterResult.filterButton.click();
        staticWait(2000);
        filterResult.planNameFilter.click();
        filterResult.filterSearch.sendKeys(planName);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @And("Verify PLAN Name {string} results list are displayed")
    public void verifyPLANNameResultsListAreDisplayed(String planName) {
        Assert.assertTrue(filterResult.allTextInTableIsExist(filterResult.planNamesFromTable,planName),"PLAN NAME are not exist");
    }

    @Then("I filtered by PLAN Name by {string} and {string}")
    public void iFilteredByPLANNameByAnd(String pln1, String pln2) {
        waitForVisibility(filterResult.filterFirstNameIcon, 55);
        filterResult.planNameFilter.click();
        filterResult.filterSearch.sendKeys(pln1);
        filterResult.filterCheckBox.click();
        staticWait(2000);
        clearAndFillText(filterResult.filterSearch, pln2);
        filterResult.filterCheckBox.click();
        filterResult.filterButton.click();
    }

    @Then("Verify after multi filtering for PLAN NAME {string} and {string} results are displayed")
    public void verifyAfterMultiFilteringForPLANNAMEAndResultsAreDisplayed(String pln1, String pln2) {
        String frsPlanName = searchAnyElementInList(filterResult.planNamesFromTable, pln1);
        String scndPlanName = searchAnyElementInList(filterResult.planNamesFromTable, pln2);
        Assert.assertEquals(frsPlanName, pln1,pln1+" Plan Name Not Found");
        Assert.assertEquals(scndPlanName, pln2,pln2+" Plan Name Not Found");
    }

    @And("Verify user should be able to view filtered PLAN NAME is enabled in applied column")
    public void verifyUserShouldBeAbleToViewFilteredPLANNAMEIsEnabledInAppliedColumn() {
        Assert.assertNotEquals(getColorCode(filterResult.planNameFilter),"#f4f4f4","Color not matching");
    }


}
