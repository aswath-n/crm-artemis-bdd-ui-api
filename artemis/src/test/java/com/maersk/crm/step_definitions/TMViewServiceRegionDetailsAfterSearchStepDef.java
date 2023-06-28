package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.pages.tm.TMSearchRegionInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TMViewServiceRegionDetailsAfterSearchStepDef extends BrowserUtils {
    TMSearchRegionInfoPage tmSearchRegionInfoPage = new TMSearchRegionInfoPage();
    WebDriver driver = Driver.getDriver();


    @Then("I verify the screen is divided into {string} tab and {string} tab")
    public void i_verify_the_screen_is_divided_into_tab_and_tab(String expectedServiceRegionInfoTab, String expectedGeographicalInfoTab) {
        String actualServiceRegionInfoTab = tmSearchRegionInfoPage.serviceRegionInfoTab.getText();
        String actualGeographicalInfoTab = tmSearchRegionInfoPage.geographicalInfoTab.getText();

        Assert.assertEquals("ServiceRegionInfoTab name is INVALID",expectedServiceRegionInfoTab,actualServiceRegionInfoTab);
        Assert.assertEquals("GeographicalInfoTab  name is INVALID",expectedGeographicalInfoTab,actualGeographicalInfoTab);


    }

    @Then("I verify the Default tab view is always {string} tab")
    public void i_verify_the_Default_tab_view_is_always_tab(String expServiceRegionInfoPageHdr) {
        String actualServiceRegionInfoPageHdr = tmSearchRegionInfoPage.serviceRegionInfoPageHdr.getText();
//        boolean isExist = tmSearchRegionInfoPage.serviceRegionInfoPageHdr.getText().contains(expServiceRegionInfoPageHdr);
//        Assert.assertTrue("INVALID DEFAULT PAGE HEADER",isExist);
        Assert.assertEquals(expServiceRegionInfoPageHdr,actualServiceRegionInfoPageHdr);
    }

    @Then("I verify the Service Region Name field as {string}")
    public void i_verify_the_Service_Region_Name_field_as(String expectedServiceRegionName) {
//        String actualServiceRegionName = tmSearchRegionInfoPage.serviceRegionName.getText();
        boolean isExist = tmSearchRegionInfoPage.serviceRegionName.getText().contains(expectedServiceRegionName);
        Assert.assertTrue("ServiceRegionName is INVALID",isExist);
//        Assert.assertEquals("ServiceRegionName is INVALID",expectedServiceRegionName,actualServiceRegionName);
    }

    @Then("I verify the Program Type field as {string}")
    public void i_verify_the_Program_Type_field_as(String expectedProgramType) {
        String actualProgramType = tmSearchRegionInfoPage.programType.getText();
        Assert.assertEquals("ProgramType is INVALID",expectedProgramType,actualProgramType);
    }

    @Then("I verify the Sub-Program Type field as {string}")
    public void i_verify_the_Sub_Program_Type_field_as(String subProgramTypeString) {
        List<String> expectedListOfSubProgramTypes = Arrays.asList(subProgramTypeString.split(":"));
        //making sure the quantity of actual vs. expected is same
        Assert.assertEquals(expectedListOfSubProgramTypes.size(),tmSearchRegionInfoPage.subProgramTypes.size());
        // checking each item to the expected list
        boolean isExist;
        for(WebElement actualSubProgramType : tmSearchRegionInfoPage.subProgramTypes){
            isExist = expectedListOfSubProgramTypes.contains(actualSubProgramType.getText());
            Assert.assertTrue("Invalid SubProgramType is displayed -> " + actualSubProgramType,isExist);
        }
    }

    @When("I click on Geographical Info tab")
    public void i_click_on_Geographical_Info_tab() {
        waitForClickablility(tmSearchRegionInfoPage.geographicalInfoTab,10);
        tmSearchRegionInfoPage.geographicalInfoTab.click();
    }

    @Then("I verify the system displays all the fields on page")
    public void i_verify_the_system_displays_all_the_fields_on_page() {
        waitFor(2);
        Assert.assertTrue("geographicalInfo tab is not displayed",tmSearchRegionInfoPage.geographicalInfoTab.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab countyNameAndCodeDropDwn is not displayed",tmSearchRegionInfoPage.countyNameAndCodeDropDwn1.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab city DropDwn is not displayed",tmSearchRegionInfoPage.cityDropDwn1.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab zip code is not displayed",tmSearchRegionInfoPage.zipCodeInput.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab search button  is not displayed",tmSearchRegionInfoPage.geographicalInfoSearchBtn.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab cancel button  is not displayed",tmSearchRegionInfoPage.geographicalInfoCancelBtn1.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab search Result Main Hdr is not displayed",tmSearchRegionInfoPage.geographicalInfo_searchResultMainHdr.isDisplayed ());
        Assert.assertTrue("geographicalInfo tab search Result countyName Hdr is not displayed",tmSearchRegionInfoPage.searchResultList_countyNameHdr.isDisplayed());
        Assert.assertTrue("geographicalInfo tab search Result countyCode Hdr is not displayed",tmSearchRegionInfoPage.searchResultList_countyCodeHdr.isDisplayed());
        Assert.assertTrue("geographicalInfo tab search Result zip code Hdr is not displayed",tmSearchRegionInfoPage.searchResultList_zipCodeHdr.isDisplayed());
        Assert.assertTrue("geographicalInfo tab search Result cities Hdr is not displayed",tmSearchRegionInfoPage.searchResultList_citiesHdr.isDisplayed());
        Assert.assertTrue("geographicalInfo tab search Result cities Hdr is not displayed",tmSearchRegionInfoPage.searchResultList_citiesHdr.isDisplayed());
    }

    @When("I select {string} in {string} field on GEOGRAPHICAL INFO tab")
    public void i_select_in_field_on_GEOGRAPHICAL_INFO_tab(String selection, String field) {
        switch(field){
            case "CountyNameAndCode":

               waitFor ( 1 );
                scrollUp ();
                scrollUp ();
                autoFillSingleSelectDropDown (  tmSearchRegionInfoPage.countyNameAndCodeInput1,selection );
                break;
            case "City":
                waitFor ( 1 );
                autoFillSingleSelectDropDown (tmSearchRegionInfoPage.cityInput1,selection);
                break;
            case "ZipCode":
                tmSearchRegionInfoPage.zipCodeInput.sendKeys(selection);
                waitFor(2);
                break;
        }
    }

    @When("I click on Search Button on GEOGRAPHICAL INFO tab")
    public void i_click_on_Search_Button_on_GEOGRAPHICAL_INFO_tab() {
        waitForClickablility(tmSearchRegionInfoPage.geographicalInfoSearchBtn,10);
        jsClick(tmSearchRegionInfoPage.geographicalInfoSearchBtn);
    }

    WebElement matchSearchResult;

    @Then("I verify the {string} search in {string} results")
    public void i_verify_the_search_in_results(String selection, String field) {
        Boolean matchResult =false;
        switch (field){
             case "CountyNameAndCode":
                waitFor(2);
                matchResult = false;
                for(WebElement r : tmSearchRegionInfoPage.searchResultList_countyName){
                    if(  selection.contains(r.getText())  ){
                        matchSearchResult=r;
                        matchResult = true;
                        break;
                    }
                }
                Assert.assertTrue("result do not match",matchResult);
             break;
            case "City":
                waitFor(2);
                matchResult = false;
                for(WebElement r : tmSearchRegionInfoPage.searchResultList_cities){
                    if(r.getText().equalsIgnoreCase(selection)){
                        matchSearchResult=r;
                        matchResult = true;
                        break;
                    }
                }
                Assert.assertTrue("result do not match",matchResult);
                break;
            case "ZipCode":
                waitFor(2);
                matchResult = false;
                for(WebElement r : tmSearchRegionInfoPage.searchResultList_zipCode){
                   if(r.getText().equalsIgnoreCase(selection)){
                       matchSearchResult=r;
                       matchResult = true;
                       break;
                   }
                }
                Assert.assertTrue("result do not match",matchResult);
             break;
            default:
                Assert.assertTrue("result do not match - CHECK TEST DATA",matchResult);
                break;
        }
    }

    @Then("Then I verify the {string} error message")
    public void then_I_verify_the_error_message(String expectedErrorMsg) {
        String actualErrorMsg = tmSearchRegionInfoPage.searchResultList_InvalidSearchErrorMsg.getText();
        Assert.assertEquals("Invalid Error Msg",expectedErrorMsg,actualErrorMsg);
        waitFor(2);
    }


    @Then("I view {string} records at first glance with the option to expand the number of results viewed at first glance")
    public void i_view_records_at_first_glance_with_the_option_to_expand_the_number_of_results_viewed_at_first_glance(String expCountOfRecord) {
        waitForVisibility(tmSearchRegionInfoPage.geographicalInfo_default_ItemsPerPage,10);
        scrollToElement(tmSearchRegionInfoPage.geographicalInfo_default_ItemsPerPage);
        Assert.assertTrue("default_ItemsPerPage is NOT correct", tmSearchRegionInfoPage.geographicalInfo_default_ItemsPerPage.getText().contains(expCountOfRecord) );
        waitFor(2);
    }

    @Then("I verify the pagination to view additional pages of results")
    public void i_verify_the_pagination_to_view_additional_pages_of_results() {
        Assert.assertTrue(tmSearchRegionInfoPage.searchResultList_pagesList.size()>0);
    }

    @Then("I will be able to sort the Results in ascending and descending order by the Column Headers")
    public void i_will_be_able_to_sort_the_Results_in_ascending_and_descending_order_by_the_Column_Headers() {
        scrollToElement(tmSearchRegionInfoPage.geographicalInfo_default_ItemsPerPage);
        ArrayList<String> obtainedList = new ArrayList<>();
        ArrayList<String> sortedList = new ArrayList<>();
        for(WebElement item : tmSearchRegionInfoPage.searchResultList_cities){
            obtainedList.add(item.getText());
        }
        for(String s:obtainedList){
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        Assert.assertFalse("Results are not getting sorted",sortedList.equals(obtainedList));
    }



}