package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert.*;


import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_ATS_HistoryTabStepDef extends CRMUtilities implements ApiBase {
    BrowserUtils browserUtils = new BrowserUtils();
    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CRM_CreateApplicationStepDef crmCreateApplicationStepDef=new CRM_CreateApplicationStepDef();
    CRM_CreateApplicationMemberStepDef applicationMemberStepDef = new CRM_CreateApplicationMemberStepDef();
    CRM_ApplicationTrackingAuthorizedRepStepDef authorizedRepStepDef = new CRM_ApplicationTrackingAuthorizedRepStepDef();
    Actions actions = new Actions(Driver.getDriver());

    APIATSApplicationController applicationController = new APIATSApplicationController();
    APIATSSendEventAndCreateLinksController apiatsSendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    public static String caseIdUI = "";
    CRMHistoryTabPage historyTabPage = new CRMHistoryTabPage();


    @When("I search for consumer with first name from ATS application from {string}")
    public void i_search_for_consumer_with_first_name_as_from_ATS_application_from(String type) {
        if(type.equals("UI")){
            clearAndFillText(manualCaseConsumerSearch.firstName, crmCreateApplicationStepDef.piFirstName);
            System.out.println("Search parameter for case/consumer search from UI created app: " + crmCreateApplicationStepDef.piFirstName);
        } else {
            clearAndFillText(manualCaseConsumerSearch.firstName, applicationController.piFirstName.get());
            System.out.println("Search parameter for case/consumer search from API: " + applicationController.piFirstName);
        }
    }

    @When("I verify that default values are selected to get business event for {string}")
    public void i_verify_that_default_values_are_selected_to_get_business_event_for(String consumerType) {
        waitFor(5);
        assertTrue(historyTabPage.businessEventsDropdown.getText().equals("All"), "Default Business event name isnt verified");
        assertTrue(historyTabPage.processDateRangeDropdown.getText().equals("Last 2 years"), "Default Process date range isnt verified");
        System.out.println("Dropdown value for consumer name: "+historyTabPage.consumerNameDropdown.getText());
        switch (consumerType){
            case "Primary Individual":
                assertTrue(historyTabPage.consumerNameDropdown.getText().equals(applicationController.piFirstName + " " + applicationController.piLastName), "Default Consumer name isnt verified for PI");
                break;
            case "Primary Ind UI":
                assertTrue(historyTabPage.consumerNameDropdown.getText().equals(crmCreateApplicationStepDef.piFirstName + " " + crmCreateApplicationStepDef.piLastName), "Default Consumer name isnt verified for PI from UI");
                break;
            case "Application Member":
                assertTrue(historyTabPage.consumerNameDropdown.getText().equals(applicationController.amFirstName + " " + applicationController.amLastName), "Default Consumer name isnt verified for AM");
                break;
            case "Authorized Rep":
                assertTrue(historyTabPage.consumerNameDropdown.getText().equals(applicationController.authRepFirstName + " " + applicationController.authRepLastName), "Default Consumer name isnt verified for AR");
                break;

        }


    }

    @Then("I verify {string} business event details on History Tab")
    public void i_verify_business_event_details_on_History_Tab(String eventType) {
        assertTrue(historyTabPage.eventName.getText().equals(eventType),"Event name isnt verified");
        assertTrue(historyTabPage.applicationId.getText().equals(applicationController.applicationIdAPI.get()),"Application id isnt verified");
        assertTrue(historyTabPage.createdBy.getText().equals(sendEventAndCreateLinksController.apiUserID),"Created by isnt verified");

        switch (eventType) {
            case "Application Cleared":
                assertTrue(historyTabPage.applicationCode.getText().equals(applicationController.applicationCode), "Application code isnt verified for Application Cleared");
                assertFalse(historyTabPage.applicationConsumerId.getText().isEmpty(),"Application consumer id isnt verified for Application Cleared");
                assertFalse(historyTabPage.applicationConsumerId.getText().equals("-- --"),"Application consumer id isnt verified for Application Cleared");
                assertTrue(historyTabPage.applicationDeadlineDate.getText().equals(applicationController.applicationDeadlineDate)," Application deadline date isnt verified for Application Cleared");
                assertTrue(historyTabPage.applicationStatus.getText().equals("Determining"),"Application status isnt verified for Application Cleared");
                assertTrue(historyTabPage.applicationTypeCycle.getText().equals("Medical Assistance-New")," Application type and cycle isnt verified for Application Cleared");
                assertTrue(historyTabPage.createdDate.getText().contains(convertMMddyyyyToyyyyMMdd(getCurrentDate()))," Created date isnt verified for Application Cleared");
                assertTrue(historyTabPage.externalApplicationNumber.getText().equals("-- --"),"External appplication number isnt verified for Application Cleared");
                break;
            case "Application Expired":
                assertTrue(historyTabPage.applicationCode.getText().equals(applicationController.applicationCode), "Application code isnt verified for Application Expired");
                assertFalse(historyTabPage.applicationConsumerId.getText().isEmpty(),"Application consumer id isnt verified for Application Expired");
                assertFalse(historyTabPage.applicationConsumerId.getText().equals("-- --"),"Application consumer id isnt verified for Application Expired");
                assertTrue(historyTabPage.expiredBy.getText().equals("ATS Service"),"Expired by isnt verified  for Application Expired");
                assertTrue(historyTabPage.expiredDate.getText().contains(convertMMddyyyyToyyyyMMdd(getCurrentDate())),"Expired date isnt verified for Application Expired");
                assertTrue(historyTabPage.applicationStatus.getText().equals("Expired"),"Application status isnt verified for Application Expired");
                assertTrue(historyTabPage.externalApplicationNumber.getText().equals("-- --"),"External appplication number isnt verified for Application Expired");
                break;
            case "Application Program Eligibility Status":
                assertTrue(historyTabPage.applicationCode.getText().equals(applicationController.applicationCode), "Application code isnt verified for Application Program Eligibility Status");
                assertFalse(historyTabPage.applicationConsumerId.getText().isEmpty(),"Application consumer id isnt verified for Application Program Eligibility Status");
                assertFalse(historyTabPage.applicationConsumerId.getText().equals("-- --"),"Application consumer id isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.applicationDeadlineDate.getText().equals(applicationController.applicationDeadlineDate)," Application deadline date isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.program.getText().equals("Medicaid"), "Program isnt verified");
                assertTrue(historyTabPage.applicationStatus.getText().equals("Determining"),"Application status isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.programEligibilityStatus.getText().equals("Eligible"),"Program eligibility status isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.primaryYN.getText().equals("Y"),"Primary Y/N isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.updatedBy.getText().equals(applicationController.loggedInUserId), "Updated by isnt verified for Application Program Eligibility Status");
                assertTrue(historyTabPage.updatedDate.getText().contains(convertMMddyyyyToyyyyMMdd(getCurrentDate())),"Updated date isnt verified for Application Program Eligibility Status");
                break;
            case"Application Missing Information":
                assertFalse(historyTabPage.applicationConsumerId.getText().isEmpty(),"Application consumer id isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.applicationDeadlineDate.getText().equals(applicationController.applicationDeadlineDate)," Application deadline date isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.applicationStatus.getText().equals("Determining"),"Application status isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.externalApplicationNumber.getText().equals("-- --"),"External appplication number isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.miCreatedBy.getText().equals("1066"),"MI CreatedBy isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.miCreatedDate.getText().contains(convertMMddyyyyToyyyyMMdd(getCurrentDate())),"MI Create date isnt verified for Application Missing Info event");
                assertFalse(historyTabPage.missingInformationId.getText().isEmpty(),"Missing information id isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.miCategoryType.getText().equals("Application Consumer/SSN"),"MI Category/Type isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.miDueDate.getText().equals(getFutureDateFormatYYYYMMdd(45 - 7)),"MI Due date isnt verified for Application Missing Info event");
                assertTrue(historyTabPage.miStatus.getText().equals("PENDING"),"MI status isnt verified for Application Missing Info Event");
                break;
        }
    }

    @Then("I choose consumer name value from dropdown as {string}")
    public void i_choose_consumer_name_value_from_dropdown_as(String dropdownValue) {
        waitFor(2);
        String value="";
        switch (dropdownValue){
            case "Primary Individual":
                historyTabPage.consumerNameDropdown.click();
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(applicationController.piFirstName+" "+applicationController.piLastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        waitFor(2);
                        actions.moveToElement(historyTabPage.textCenter).click().perform();
                        selectDropDown(historyTabPage.consumerNameDropdown,value);
                    }
                }
                break;
            case "Application Member":
                historyTabPage.consumerNameDropdown.click();
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(applicationController.amFirstName+" "+applicationController.amLastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        waitFor(2);
                        actions.moveToElement(historyTabPage.textCenter).click().perform();
                        selectDropDown(historyTabPage.consumerNameDropdown,value);
                    }
                }
                break;
            case "Authorized Representative":
                historyTabPage.consumerNameDropdown.click();
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    System.out.println(i+" relates to dropdown value:"+createApplication.relatesToDropdownValues.get(i).getText());
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(applicationController.authRepFirstName+" "+applicationController.authRepLastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        waitFor(2);
                        actions.moveToElement(historyTabPage.textCenter).click().perform();
                        selectDropDown(historyTabPage.consumerNameDropdown,value);
                    }
                }
                break;
        }
    }

}