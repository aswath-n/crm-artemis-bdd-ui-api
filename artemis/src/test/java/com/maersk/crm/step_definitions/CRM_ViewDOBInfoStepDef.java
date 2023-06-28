package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CRM_ViewDOBInfoStepDef extends CRMUtilities implements ApiBase {

    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    private final ThreadLocal<List<String>> searchResults = ThreadLocal.withInitial(ArrayList::new);


    @Then("I see all Search results have DOB field value masked in {string} format on Contact Record UI Page for {string}")
    public void i_see_all_Search_results_have_DOB_field_value_masked_in_format_on_Contact_Record_UI_Page(String format, String project) {
        if(project.equals("BLCRM")) {
            for (WebElement e : searchResult.DOBs) {
                if (e.getText().equalsIgnoreCase("")) {
                    continue;
                }
                searchResults.get().add(e.getText());
            }
        } else if(project.equals("NJ-SBE")){
            for (WebElement e : searchResult.DOBsNJ) {
                if (e.getText().equalsIgnoreCase("")) {
                    continue;
                }
                searchResults.get().add(e.getText());
            }


        }


        for (String dob : searchResults.get()) {
            assertTrue(dob.equals(format), "DOB has to be displayed in " + format + ".");
        }


    }

    @Then("I see all Search results have DOB field value masked in {string} format on Active Consumer Page")
    public void i_see_all_Search_results_have_DOB_field_value_masked_in_format_on_Active_Consumer_Page(String format) {
        String actualDOB = "";
        if (activeContact.dob.getText().endsWith("visibility_off")) {
            actualDOB = activeContact.dob.getText().substring(0, 11);
        } else {
            actualDOB = activeContact.dob.getText();
        }
        assertTrue(actualDOB.startsWith(format), "Masked DOB on Active Contact Record Page is not in " + format + ".");

    }

    @Then("I see DOB unmasking button displayed on Contact Record UI Page")
    public void i_see_DOB_unmasking_button_displayed_on_Contact_Record_UI_Page() {
        assertTrue(searchResult.unmaskDOB.isDisplayed(), "Unmasking DOB button on Contact Record UI Page is not displayed");
    }

    @When("I and click on unmasking button on Contact Record UI Page")
    public void i_and_click_on_unmasking_button_on_Contact_Record_UI_Page() {
        jsClick(searchResult.unmaskDOB);
        waitForVisibility(searchResult.maskingDOB, 10);
        assertTrue(searchResult.maskingDOB.isDisplayed(), "DOB on Active Contact Record Page was not unmasked");
        waitFor(3);
    }

    @Then("I see DOB in MM\\/DD\\/YYYY format unmasked on Contact Record UI Page in {string}")
    public void i_see_DOB_in_MM_DD_YYYY_format_unmasked_on_Contact_Record_UI_Page_in(String project) {
        if(project.equals("BLCRM")){
         for (WebElement e : searchResult.DOBs) {
            if (e.getText().equalsIgnoreCase("")) {
                continue;
            }
            searchResults.get().add(e.getText());
        }}
         else if(project.equals("NJ-SBE")){
                for (WebElement e : searchResult.DOBsNJ) {
                    if (e.getText().equalsIgnoreCase("")) {
                        continue;
                    }
                    searchResults.get().add(e.getText());
            }

        }
        for (String actualDOB : searchResults.get()) {
            assertTrue(isMMddYYYYformat(actualDOB),"DOB on Contact Record UI Page is not in MM/DD/YYYY format.");
        }
    }

    @Then("I see unmasking DOB button is displayed on Active Contact Record Page")
    public void i_see_unmasking_DOB_button_is_displayed_on_Active_Contact_Record_Page() {
        assertTrue(activeContact.unmaskDOB.isDisplayed(), "Unmasking DOB button on Active Contact Record Page is not displayed");
        waitFor(2);
    }

    @When("I and click on unmasking button on Active Contact Record Page")
    public void i_and_click_on_unmasking_button_on_Active_Contact_Record_Page() {
        activeContact.unmaskDOB.click();
        waitForVisibility(activeContact.maskingDOB, 10);
        assertTrue(activeContact.maskingDOB.isDisplayed(), "DOB on Active Contact Record Page was not unmasked");
    }

    @Then("I see DOB in MM\\/DD\\/YYYY format unmasked on Active Contact Record Page")
    public void i_see_DOB_in_MM_DD_YYYY_format_unmasked_on_Active_Contact_Record_Page() {
        String actualDOB = "";
        if (activeContact.dob.getText().endsWith("visibility")) {
            actualDOB = activeContact.dob.getText().substring(0, 11);
        } else {
            actualDOB = activeContact.dob.getText();
        }
        assertTrue(isMMddYYYYformat(actualDOB), "DOB on Active Contact Record Page is not in MM/DD/YYYY format.");
    }

    @Then("I verify that {string} unmask button on authentication grid")
    public void i_verify_that_unmask_button_on_authentication_grid(String field) {
        if(field.equals("DOB")){
            assertTrue(searchResult.authGridDOBunmask.isDisplayed(),"DOB unmask button isnt displayed on authentication grid");
        }else if(field.equals("SSN")){
            assertTrue(searchResult.authGridSSNunmask.isDisplayed(),"SSN unmask isnt displayed on authentication grid");
        }

    }

    @Then("I click on {string} unmask button authentication grid")
    public void i_click_on_unmask_button_authentication_grid(String field) {
        if(field.equals("DOB")){
            jsClick(searchResult.authGridDOBunmask);
            waitFor(2);
        }else if(field.equals("SSN")){
            jsClick(searchResult.authGridSSNunmask);
            waitFor(2);
        }
    }

    @Then("I verify that DOB in MM\\/DD\\/YYYY format on Authentication Grid")
    public void i_verify_that_DOB_in_MM_DD_YYYY_format_on_Authentication_Grid() {
        assertTrue(isMMddYYYYformat(contactRecord.ConsumerDOBVal.getText()), "DOB value isnt correct on authentication grid");

    }
}
