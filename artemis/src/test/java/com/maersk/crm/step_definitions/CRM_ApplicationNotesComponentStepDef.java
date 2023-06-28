package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;


public class CRM_ApplicationNotesComponentStepDef extends CRMUtilities implements ApiBase {
    BrowserUtils browserUtils = new BrowserUtils();
    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    ThreadLocal<CRM_CreateApplicationStepDef> crmCreateApplicationStepDef = ThreadLocal.withInitial(CRM_CreateApplicationStepDef::new);
    ThreadLocal<CRM_CreateApplicationMemberStepDef> applicationMemberStepDef = ThreadLocal.withInitial(CRM_CreateApplicationMemberStepDef::new);
    ThreadLocal<CRM_ApplicationTrackingAuthorizedRepStepDef> authorizedRepStepDef = ThreadLocal.withInitial(CRM_ApplicationTrackingAuthorizedRepStepDef::new);
    Actions actions = new Actions(Driver.getDriver());
    CRMTaskNoteComponentPage taskNoteComponentPage = new CRMTaskNoteComponentPage();
    public static ThreadLocal<String> notesText = ThreadLocal.withInitial(String::new);


    APIATSApplicationController applicationController = new APIATSApplicationController();

    @And("I verify that no notes component is available")
    public void I_verify_that_no_notes_component_is_available() {
        waitFor(2);
        scrollDown();
        try {
            assertFalse(createApplication.notesText.isDisplayed());
        } catch (NoSuchElementException e) {
            System.out.println("Application notes is not displayed");
        }
    }


    @Then("I verify notes component for application appeared on the page")
    public void I_verify_notes_component_for_application_appeared_on_the_page(){
        assertTrue(createApplication.applicationNotes.isDisplayed(), "Notes component is not displayed");
        assertTrue(createApplication.relatesToDropdown.isDisplayed(), "Relates to dropdown is not displayed");
        assertTrue(createApplication.notesText.isDisplayed(), "Notes text is not displayed");
        assertTrue(createApplication.notesText.getText().isEmpty());
    }

    @Then("I choose relates to value as {string}")
    public void i_choose_relates_to_value_as(String dropdownValue) {
        waitFor(2);
        scrollDown();
        String value="";
        switch (dropdownValue){
            case "Application":
                selectDropDown(createApplication.relatesToDropdown,dropdownValue);
                break;
            case "Primary Individual":
                createApplication.relatesToDropdown.click();
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(crmCreateApplicationStepDef.get().piFirstName+" "+crmCreateApplicationStepDef.get().piLastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        actions.moveToElement(createApplication.notesText).click().perform();
                        waitFor(2);
                        selectDropDown(createApplication.relatesToDropdown,value);
                    }
                }
                break;
            case "Application Member":
                createApplication.relatesToDropdown.click();
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(applicationMemberStepDef.get().firstName.get()+" "+applicationMemberStepDef.get().lastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        actions.moveToElement(createApplication.notesText).click().perform();
                        waitFor(2);
                        selectDropDown(createApplication.relatesToDropdown,value);
                    }
                }

                break;
            case "Authorized Representative":
                createApplication.relatesToDropdown.click();
                System.out.println("Expected auth name:"+authorizedRepStepDef.get().authRepFirstName+" "+authorizedRepStepDef.get().authRepLastName);
                for (int i = 0; i < createApplication.relatesToDropdownValues.size(); i++) {
                    System.out.println(i+" relates to dropdown value:"+createApplication.relatesToDropdownValues.get(i).getText());
                    if (createApplication.relatesToDropdownValues.get(i).getText().contains(authorizedRepStepDef.get().authRepFirstName+" "+authorizedRepStepDef.get().authRepLastName)) {
                        value=createApplication.relatesToDropdownValues.get(i).getText();
                        System.out.println("Value from AR: "+value);
                        actions.moveToElement(createApplication.notesText).click().perform();
                        selectDropDown(createApplication.relatesToDropdown,value);
                    }
                }
                break;
        }
    }

    @Then("I provide notes text with {int} characters")
    public void i_provide_notes_text_with_characters(int characters) {
        waitForClickablility(createApplication.notesText,3);
        notesText.set(getRandomString(characters));
        sendKeyToTextField(createApplication.notesText, notesText.get());
        System.out.println("Notest text: "+notesText.get());
    }

    @Then("I click on {string} button for Application Notes")
    public void i_click_on_button_for_Application_Notes(String buttonType) {
          switch (buttonType){
                case "Save":
                    createApplication.saveNotes.click();
                    break;
                case "Cancel":
                    createApplication.cancelNotes.click();
                    break;
         }
    }

    @Then("I verify that Notes Success Message is displayed")
    public void i_verify_that_Notes_Success_Message_is_displayed() {
        waitFor(2);
        assertTrue(createApplication.noteSuccessMessage.isDisplayed());
    }

    @And("I verify that Note Record is created with expected details for {string}")
    public void i_verify_that_Note_Record_is_created_with_expected_details_for(String consumerType){
        scrollDown();
        waitFor(2);
        assertTrue(createApplication.createdByNotes.getText().equalsIgnoreCase(applicationController.loggedInUserName.get()),"Expected: "+applicationController.loggedInUserName+" ,but found: "+createApplication.createdByNotes.getText());

        switch (consumerType){
            case "App":
                if(Integer.parseInt(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ").split(" ")[1].split(":")[0]) < 10)
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0")),"Found for app:"+createApplication.createdOnNotes.getText()+" expected for later than 10:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0"));
                     else
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ")),"Found for app:"+createApplication.createdOnNotes.getText()+" but expected:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " "));

                assertTrue(createApplication.relatesToNotes.getText().contains("| RELATES TO : Application"),"Expected: "+createApplication.relatesToNotes.getText());
                break;
            case "PI":
                if(Integer.parseInt(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ").split(" ")[1].split(":")[0]) < 10)
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0")), "Found for PI:"+createApplication.createdOnNotes.getText()+" expected for later than 10:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0"));
                else
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ")),"Found for PI: "+createApplication.createdOnNotes.getText()+"expected: "+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " "));

                assertTrue(createApplication.relatesToNotes.getText().contains("| RELATES TO : "+crmCreateApplicationStepDef.get().piFirstName+" "+crmCreateApplicationStepDef.get().piLastName),"Expected: "+createApplication.relatesToNotes.getText());
                break;
            case "AM":
                if(Integer.parseInt(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ").split(" ")[1].split(":")[0]) < 10)
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0")), "Found for AM:"+createApplication.createdOnNotes.getText()+" expected for later than 10:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0"));
                else
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ")), "Found for AM:"+createApplication.createdOnNotes.getText()+" but expected:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " "));
                assertTrue(createApplication.relatesToNotes.getText().contains("| RELATES TO : "+applicationMemberStepDef.get().firstName.get()+" "+applicationMemberStepDef.get().lastName),"Expected for AM: "+createApplication.relatesToNotes.getText());
                break;
            case "AR":
                if(Integer.parseInt(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ").split(" ")[1].split(":")[0]) < 10)
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0")),"Found for AR:"+createApplication.createdOnNotes.getText()+" expected for later than 10:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " 0"));
                else
                    assertTrue(createApplication.createdOnNotes.getText().equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " ")),"Found for AR:"+createApplication.createdOnNotes.getText()+" but expected:"+taskNoteComponentPage.projectDateAtHeader.getText().replace("\n", " "));
                assertTrue(createApplication.relatesToNotes.getText().contains("| RELATES TO : "+authorizedRepStepDef.get().authRepFirstName+" "+authorizedRepStepDef.get().authRepLastName),"Expected for AR:"+createApplication.relatesToNotes.getText());
                break;
            case "App API":
                assertTrue(createApplication.createdOnNotes.getText().substring(0,10).equalsIgnoreCase(taskNoteComponentPage.projectDateAtHeader.getText().substring(0,10).replace("\n"," ")), "Expected for App PI:"+taskNoteComponentPage.projectDateAtHeader.getText().substring(0,10).replace("\n"," ")+" ,but found: "+createApplication.createdOnNotes.getText().substring(0,10));
                assertTrue(createApplication.relatesToNotes.getText().contains("| RELATES TO : Application"),"Expected: "+createApplication.relatesToNotes.getText());
                break;
        }
    }

    @And("I verify existing notes are not editable")
    public boolean i_verify_existing_notes_are_not_editable(){
        waitFor(1);
        scrollDown();
        try {
            waitForClickablility(createApplication.existingNotesText,5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Then("I verify sorted order of application notes")
    public void i_verify_sorted_order_of_application_notes(){

        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        boolean recordFound = false;

            for (int i = 0; i < createApplication.applicationNotesDateTimeList.size(); i++) {
                waitFor(1);
                displayedInOrder.add(createApplication.applicationNotesDateTimeList.get(i).getText());
                toSort.add(createApplication.applicationNotesDateTimeList.get(i).getText());
                assertTrue(isElementDisplayed(createApplication.notesTextList.get(i)));
                assertTrue(isElementDisplayed(createApplication.createdByList.get(i+1)));
                recordFound = true;
            }

        assertTrue(recordFound, "Records not found");
        Collections.sort(toSort, Collections.reverseOrder());
        assertEquals(displayedInOrder,toSort,"Note list is not sorted by date");
}

    @And("I verify previously provided notes text is drafted")
    public void i_verify_previously_provided_notest_text_is_drafted(){
        assertTrue(createApplication.notesText.getText().equalsIgnoreCase(notesText.get()));
    }










}

