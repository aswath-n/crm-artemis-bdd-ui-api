package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class CRM_NJ_ConsumerSearchConfigStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    @Then("I see expected {string} on NJ-SBE search consumer component")
    public void iSeeExpectedOnNJSBESearchConsumerComponent(String option) {
        switch (option) {
            case "field":
                assertTrue(contactRecord.firstName.isDisplayed(), "First name field is not displayed");
                assertTrue(contactRecord.lastName.isDisplayed(), "Last name field is not displayed");
                assertTrue(contactRecord.dobSearch.isDisplayed(), "Date of birth field is not displayed");
                assertTrue(contactRecord.ssnSearch.isDisplayed(), "SSN field is not displayed");
                assertTrue(contactRecord.caseConsumerSearch.isDisplayed(), "Case field is not displayed");
                assertTrue(contactRecord.searchCaseOptions.isDisplayed(), "Case Type options field is not displayed");
                break;
            case "advanced field":
                assertTrue(contactRecord.phoneNumber.isDisplayed(), "Phone number field is not displayed");
                break;
        }
    }

    @Then("I don't see unexpected {string} on NJ-SBE search consumer component")
    public void iDonTSeeUnexpectedOnNJSBESearchConsumerComponent(String string, List<String> options) {
        for (String option : options) {
            switch (option) {
                case "MI":
                    assertTrue(IsElementDisplayed(contactRecord.middleInitialSearch), option + " field should not be displayed");
                    break;
                case "Email":
                    assertTrue(IsElementDisplayed(contactRecord.consumerEmail), option + " field should not be displayed");
                    break;
                case "ConsumerID":
                    assertTrue(IsElementDisplayed(contactRecord.searchConsumerID), option + " field should not be displayed");
                    break;
                case "ConsumerIDType":
                    assertTrue(IsElementDisplayed(contactRecord.searchConsumerOptions), option + " field should not be displayed");
                    break;
                case "State":
                    assertTrue(IsElementDisplayed(contactRecord.state), option + " field should not be displayed");
                    break;
                case "Zip":
                    assertTrue(IsElementDisplayed(contactRecord.zipCode), option + " field should not be displayed");
                    break;
                case "City":
                    assertTrue(IsElementDisplayed(contactRecord.city), option + " field should not be displayed");
                    break;
                case "County":
                    assertTrue(IsElementDisplayed(contactRecord.county), option + " field should not be displayed");
                    break;
                case "Address1":
                    assertTrue(IsElementDisplayed(contactRecord.address1), option + " field should not be displayed");
                    break;
                case "Address2":
                    assertTrue(IsElementDisplayed(contactRecord.address2), option + " field should not be displayed");
                    break;
            }
        }
    }
}
