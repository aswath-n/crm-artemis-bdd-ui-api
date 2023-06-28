package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.pages.tm.TMSearchRegionInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TM_SearchForServiceRegionStepDef extends BrowserUtils {
    TMConfigurationPage tmConfigurationPage = new TMConfigurationPage();
    TMSearchRegionInfoPage tmSearchRegionInfoPage = new TMSearchRegionInfoPage();
    final ThreadLocal<TM_SearchForPlanStepDef> tm_searchForPlanStepDef = ThreadLocal.withInitial(TM_SearchForPlanStepDef::new);
    final ThreadLocal<TM_ProjectConfigStepDef> tm_projectConfigStepDef = ThreadLocal.withInitial(TM_ProjectConfigStepDef::new);
    private final ThreadLocal<String> actualErrorMsg = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<WebElement> matchSearchResult = new ThreadLocal<>();

    /*This method verify the Search Service Regions configuration screen page all fields
    Author -Mohammad
     */
    @Then("I verify the Search Service Regions configuration screen page fields")
    public void i_verify_the_Search_Service_Regions_configuration_screen_page_fields() {
        scrollToElement(tmConfigurationPage.configEndOfPage);
        waitForVisibility(tmConfigurationPage.searchServiceRegionCancelBtn, 10);
        waitForVisibility(tmConfigurationPage.searchBtn, 10);
        waitFor(1);
        Assert.assertTrue("Service Region Config main tab Header is not displayed", tmConfigurationPage.serviceRegionMainTabHdr.isDisplayed());
        Assert.assertTrue("Search Service Region Header is not displayed", tmConfigurationPage.searchServiceRegionHdr.isDisplayed());
        Assert.assertTrue("Search Button is not displayed", tmConfigurationPage.searchBtn.isDisplayed());
        Assert.assertTrue("Cancel Button is not displayed", tmConfigurationPage.searchServiceRegionCancelBtn.isDisplayed());
        Assert.assertTrue("State Wide check box is not displayed", tmConfigurationPage.stateWideCheckBx.isDisplayed());
    }


    //For text box, Do use clearAndFillText method across as example, clearAndFillText(tmConfigurationPage.countyNameAndCodeInput, selection);
    //click method, it we are standardizing this

    /*This method perform search selection based on field parameter input
    Author -Mohammad
     */


    @And("I select {string} in {string} field")
    public void Wi_select_in_field(String selection, String field) {

        scrollToElement(tmConfigurationPage.serviceRegionNameDropDwn);
        switch (field) {
            case "ServiceRegionName":
                tmConfigurationPage.serviceRegionNameDropDwn.click();
                waitFor(2);
                for (WebElement r : tmConfigurationPage.serviceRegionNameDrpDwnLst) {
                    if (r.getText().equalsIgnoreCase(selection)) {
                        waitForClickablility(r, 10);
                        jsClick(r);
                        break;
                    }
                }
                break;
            case "CountyNameAndCode":
                autoCompleteSingleSelectDropDown(tmConfigurationPage.countyNameAndCodeDropDwn, selection);

                break;
            case "ZipCode":
                clearAndFillText(tmConfigurationPage.zipCodeInput, selection);
                break;
        }
    }

    /*This method click on Search Button on Search Service Region tab
    Author -Mohammad
     */
    @When("I click on Search Button on Search Service Region tab")
    public void i_click_on_Search_Button_on_Search_Service_Region_tab() {
        waitForClickablility(tmConfigurationPage.searchBtn, 10);
        //scrollToElement(tmConfigurationPage.configEndOfPage);
        scrollDown();
        scrollDown();
        jsClick(tmConfigurationPage.searchBtn);
//        scrollToElement(tmConfigurationPage.configEndOfPage);
    }

    /*This method verify search results on Search Service Region tab
    Author -Mohammad
     */
    @Then("I verify the {string} search in results")
    public void i_verify_search_in_results(String selection) {
        scrollToElement(tmConfigurationPage.configEndOfPage);
        waitFor(2);
        Boolean matchResult = false;
        for (WebElement r : tmConfigurationPage.searchResultList) {
            if (r.getText().equalsIgnoreCase(selection)) {
                synchronized (matchSearchResult) {
                    matchSearchResult.set(r);
                }
                matchResult = true;
                break;
            }
        }
        Assert.assertTrue("result do not match", matchResult);
    }

    /*This method cancel button on Search Service Region tab
    Author -Mohammad
     */
    @Then("I Click on Search Service Region tab Cancel button")
    public void i_Click_on_Search_Service_Region_tab_Cancel_button() {
        waitFor(1);
        waitForClickablility(tmConfigurationPage.searchServiceRegionCancelBtn, 10);
        jsClick(tmConfigurationPage.searchServiceRegionCancelBtn);
        waitFor(1);
    }

    /*This method verify the error message on Search Service Region tab
    Author -Mohammad
     */
    @Then("I verify the {string} error message")
    public void i_verify_the_error_message(String expectedErrorMsg) {
        actualErrorMsg.set(tmConfigurationPage.searchServiceRegionInvalidSearchErrorMsg.getText());
        Assert.assertEquals("Invalid Error Msg", expectedErrorMsg, actualErrorMsg.get());
        waitFor(2);
    }

    /*This method select check box of state-wide on Search Service Region tab
     Author -Mohammad
    */
    @When("I click on the checkbox next to State-Wide")
    public void i_click_on_the_checkbox_next_to_State_Wide() {
        scrollToElement(tmConfigurationPage.configEndOfPage);
        waitForClickablility(tmConfigurationPage.stateWideCheckBx, 10);
        jsClick(tmConfigurationPage.stateWideCheckBx);
        waitFor(1);
    }

    /*This method verify that all fields are disabled on Search Service Region tab
        Author -Mohammad
    */
    @Then("all other search parameters will be unavailable to the user")
    public void all_other_search_parameters_will_be_unavailable_to_the_user() {
        waitFor(2);
        Assert.assertTrue("serviceRegionNameDropDwn is NOT disabled", tmConfigurationPage.serviceRegionNameDropDwn.getAttribute("aria-disabled").contains("true"));
        Assert.assertTrue("countyNameAndCodeDropDwn is NOT disabled", !tmConfigurationPage.countyNameAndCodeDropDwn.isEnabled());
        Assert.assertTrue("zipCodeInput is NOT disabled", tmConfigurationPage.zipCodeInput.getAttribute("class").contains("disabled"));
    }

    /*This method verify the search result header on Search Service Region tab
    Author -Mohammad
     */
    @Then("I verify Search Result header as {string}")
    public void i_verify_Search_Result_header_as(String expectedResultHeaderName) {
        scrollToElement(tmConfigurationPage.configEndOfPage);
        String actualHeaderName = tmConfigurationPage.searchResultHeader.getText();
        Assert.assertEquals("Search Result header name is NOT matching", expectedResultHeaderName, actualHeaderName);
    }

    /*This method click on search result on Search Service Region tab
    Author -Mohammad
     */
    @Then("I click on the Service region Name on search results")
    public void i_click_on_the_Service_region_Name_on_search_results() {
        waitForClickablility(matchSearchResult.get(), 10);
        jsClick(matchSearchResult.get());
    }

    /*This method verify that system navigated to the Service Region Detail page
    Author -Mohammad
     */
    @Then("I verify that I have navigated to the Service Region Detail page")
    public void i_verify_that_I_have_navigated_to_the_Service_Region_Detail_page() {
        waitForVisibility(tmSearchRegionInfoPage.serviceRegionInfoPageHdr, 10);
        Assert.assertTrue("Service Region Info Page did NOT appeared", tmSearchRegionInfoPage.serviceRegionInfoPageHdr.isDisplayed());
        waitFor(1);
    }


    /*This method to click on Plans Management Link
    Author -Aswath
     */

    @And("I verify data on the Plans Management")
    public void verifyDataOnPlansManagement() throws AWTException {
        Wi_select_in_field("ServiceRegionName", "Central");
        i_click_on_Search_Button_on_Search_Service_Region_tab();
        scrollToElement(tmConfigurationPage.configEndOfPage);
        waitFor(2);
        Boolean matchResult = false;
        for (WebElement r : tmConfigurationPage.searchResultList) {
            if (r.getText().equalsIgnoreCase("Central")) {
                matchSearchResult.set(r);
                matchResult = true;
                break;
            }
        }
        //Assert.assertTrue("result do not match",matchResult);
        if (matchResult == false) {
            tm_projectConfigStepDef.get().uploadServiceFileBeforePlanFile();
            tm_searchForPlanStepDef.get().iClickOnPlansConfigurationTab();
            tm_projectConfigStepDef.get().iChooseFileToUploadInPlanConfigTab("File Success");
            tm_projectConfigStepDef.get().attachedTheFileToPlanConfigTab();
            tm_projectConfigStepDef.get().clickOnPlanUploadButton();
            waitFor(10);
        }


    }

    @And("I Click on the Service Region header")
    public void clickOnServiceRegion() {
        waitForVisibility(tmConfigurationPage.serviceRegionMainTabHdr, 30);
        tmConfigurationPage.serviceRegionMainTabHdr.click();


    }


}