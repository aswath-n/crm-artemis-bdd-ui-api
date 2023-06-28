package com.maersk.dms.steps;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import com.maersk.dms.step_definitions.ViewOutboundCorrespondenceStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.generalSave;
import static com.maersk.dms.step_definitions.ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ViewOutboundCorrespondenceSteps extends CRMUtilities implements ApiBase {
    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    ViewOutboundCorrespondenceStepDefs viewOutboundCorrespondenceStepDefs = new ViewOutboundCorrespondenceStepDefs();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private final ThreadLocal<JsonPath> getCID = new ThreadLocal<>();
    CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);


    @And("there is a Notification Object Uploaded onto Onbase for each notification")
    public void that_there_is_a_Notification_Object_Uploaded_onto_Onbase_for_each_notification() {
        assertTrue(viewOutboundCorrespondenceDetailsPage.viewIconCorrespondenceLevel.isDisplayed());
    }

    @When("I select the correspondence by {string} and select the document by {string}")
    public void i_select_the_correspondence_by_and_select_the_document_by(String cid, String nid) {
        if(cid.equalsIgnoreCase("CREATEDFROMAPI")){
            cid= String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
        }
        correspondenceChevronByCid(cid);
        if(nid.equalsIgnoreCase("CREATEDFROMAPI")){
            JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
            nid = response.get("recipients[0].notifications[0].notificationId");
        }
        viewOutboundCorrespondenceStepDefs.viewCorrespondenceByNid(nid);
    }

    public void correspondenceChevronByCid(String cid) {
        if (cid.equalsIgnoreCase("CREATEDFROMAPI")) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
        }
        Actions actions = new Actions(Driver.getDriver());
        String x = "(//*[text()='" + cid + "'])/../td[1]";
        actions.moveToElement(Driver.getDriver().findElement(By.xpath(x))).perform();
        Driver.getDriver().findElement(By.xpath(x)).click();
    }

    @Then("I should see the Mail notification is viewed")
    public void i_should_see_the_Mail_notification_is_viewed() throws IOException {
        viewOutboundCorrespondenceStepDefs.verifyNotificationViewed("Mail");
    }

    @Then("I should see the Email notification is viewed")
    public void i_should_see_the_Email_notification_is_viewed() throws IOException {
        viewOutboundCorrespondenceStepDefs.verifyNotificationViewed("Email");
    }

    @Then("I should see the Mobile notification is viewed")
    public void i_should_see_the_Mobile_notification_is_viewed() throws IOException {
        viewOutboundCorrespondenceStepDefs.verifyNotificationViewed("Mobile App");
    }

    @Then("I should see the Text notification is viewed")
    public void i_should_see_the_Text_notification_is_viewed() throws IOException {
        viewOutboundCorrespondenceStepDefs.verifyNotificationViewed("Text");
    }

    @Then("no view icons are present")
    public void no_view_icons_are_present() {
        assertFalse(inboundCorrespondencePage.elementIsDisplayed(inboundCorrespondencePage.outboundCorrespondenceDetailIcons));
    }

    @Then("I should see the opened document is a new window")
    public void i_should_see_the_opened_document_is_a_new_window() {
        viewOutboundCorrespondenceStepDefs.verifyNewWindow();
    }

    @When("I navigate away in the crm application")
    public void i_navigate_away_in_the_crm_application() {
        viewOutboundCorrespondenceStepDefs.navigateToFirstWindow();
    }

    @Then("I should see the opened document is closed")
    public void i_should_see_the_opened_document_is_closed() {
        viewOutboundCorrespondenceStepDefs.verifyDocumentWindowClosed();
    }

    @Then("I should see the follow elements are visible; save button, cancel button.")
    public void i_should_see_the_follow_elements_are_visible_save_button_cancel_button() {
        viewOutboundCorrespondenceStepDefs.verifyNoteButtons();
    }

    @Then("I should see the note and any existing notes are present")
    public void i_should_see_the_note_and_any_existing_notes_are_present() {
        viewOutboundCorrespondenceStepDefs.verifyLatestNoteText();
    }

    @Then("I should see the note displayed and includes the following fields; User Name, timestamp, text")
    public void i_should_see_the_note_displayed_and_includes_the_following_fields_User_Name_date_time_created_text() {
        viewOutboundCorrespondenceStepDefs.verifyNotesFields();
    }

    @Then("I should see existing notes are in descending order")
    public void i_should_see_existing_notes_are_in_descending_order() {
        viewOutboundCorrespondenceStepDefs.verifyDescendingNotes();
    }

    @Then("I should see the note has created on and created by")
    public void i_should_see_the_note_has_created_on_and_created_by() {
        viewOutboundCorrespondenceStepDefs.verifyCreatedOnOBNotes();
        viewOutboundCorrespondenceStepDefs.verifyCreatedBy();
    }

    @Then("I should see the note will store the assigned values in a Note record associated with the Correspondence")
    public void i_should_see_the_note_will_store_the_assigned_values_in_a_Note_record_associated_with_the_Correspondence() {
        viewOutboundCorrespondenceStepDefs.verifyLatestNoteText();
        viewOutboundCorrespondenceStepDefs.verifyCreatedOnOBNotes();
        viewOutboundCorrespondenceStepDefs.verifyCreatedBy();
    }

    @Then("I should see the save will trigger a save successful message")
    public void i_should_see_the_save_will_trigger_a_save_successful_message() {
        viewOutboundCorrespondenceStepDefs.verifySaveSuccessMsg();
    }

    @Then("I should see note will appear in note list and clear the note text field")
    public void i_should_see_note_will_appear_in_note_list_and_clear_the_note_text_field() {
        viewOutboundCorrespondenceStepDefs.verifyCreatedOnOBNotes();
        viewOutboundCorrespondenceStepDefs.verifyCreatedBy();
        BrowserUtils.waitFor(4);
        viewOutboundCorrespondenceStepDefs.verifyTextInputClear();
    }

    @Given("I type a new note in the note text box")
    public void i_type_a_new_note_in_the_note_text_box() {
        viewOutboundCorrespondenceStepDefs.populateNoteTextInput();
    }

    @When("attempt to navigate away from the page")
    public void attempt_to_navigate_away_from_the_page() {
        inboundCorrespondencePage.outboundCorrespondencBackspaceArrow.click();
    }

    @Then("I should see a warning message")
    public void i_should_see_a_warning_message() {
        viewOutboundCorrespondenceStepDefs.verifyNavigationWarningMsg();
    }

    @When("attempt click on cancel")
    public void attempt_click_on_cancel() {
        viewOutboundCorrespondenceStepDefs.attemptCancel();
    }

    @Then("I should remain on the current page and the text box should be cleared")
    public void i_should_remain_on_current_page_and_the_text_box_should_be_cleared() {
        viewOutboundCorrespondenceStepDefs.verifyTextInputClear();
    }

    @And("I select Outbound Correspondence by {string}")
    public void iSelectOutboundCorrespondenceBy(String cid) {
        if ("Any".equalsIgnoreCase(cid)) {
            uiAutoUitilities.get().selectFirstOutboundCorrespondence();
        } else if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = getCorrespondenceResponse.get().getString("id");
            viewOutboundCorrespondenceStepDefs.moverToCorrespondenceHeader();
            viewOutboundCorrespondenceStepDefs.viewCorrespondenceDetailsByCid(cid);
            viewOutboundCorrespondenceStepDefs.verifyCorrespondenceDetailsHeaderByCid(cid);
        } else {
            viewOutboundCorrespondenceStepDefs.moverToCorrespondenceHeader();
            viewOutboundCorrespondenceStepDefs.viewCorrespondenceDetailsByCid(cid);
            viewOutboundCorrespondenceStepDefs.verifyCorrespondenceDetailsHeaderByCid(cid);
        }
        String outbCid = "--:--";
        for (int timeout = 0; timeout < 7; timeout++) {
            outbCid = viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText();
            if (!"--:--".equalsIgnoreCase(cid) && cid.trim().length() > 1) {
                break;
            } else {
                BrowserUtils.waitFor(4);
            }
        }
    }

    @And("I add and save new note")
    public void iAddSaveNewNote() {
        viewOutboundCorrespondenceStepDefs.addNewNote();
    }

    @Given("I have an Outbound Correspondence with a notification for {string}")
    public void iHaveAnOutboundCorrespondenceWithANotificationFor(String notifications) {
        String[] channels = notifications.split(",");
        List<String> list = Arrays.asList(channels);
        String CID =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondenceMultipleChannels(list);
        synchronized (getCID){
            getCID.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(CID));
        }
        synchronized (getCorrespondenceResponse){
            Assert.assertNotNull(getCID.get());
            getCorrespondenceResponse.set(getCID.get());
        }
        List<Map<String, String>> createdCorrespondences = getCID.get().getList("recipients[0].notifications");
        DPBIOutboundCorrespondenceConfigEventsSteps.notificationIds.set(getCID.get().getList("recipients[0].notifications.notificationId"));
        DPBIOutboundCorrespondenceConfigEventsSteps.currentCID.set(CID);
        World.generalList.get().clear();
        for (Map<String, String> nids : createdCorrespondences) {
            String docId =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().uploadOutboundCorrespondence(nids.get("notificationId"), nids.get("channelType").toLowerCase());
           API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().patchNotificationObjectReceived(nids.get("notificationId"),docId);
            World.generalList.get().add(nids.get("notificationId"));
        }
    }

    @Then("I should see the Inbound Document is viewable")
    public void iShouldSeeTheInboundDocumentIsViewable() throws IOException {
        Assert.assertTrue(viewOutboundCorrespondenceStepDefs.verifyNotificationViewed("InboundDocument"));
    }

    @And("I click save for notification")
    public void iClickSaveForNotification() {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(1);
        scrollToElement(viewOutboundCorrespondenceDetailsPage.linksHeader);
        jsClick(viewOutboundCorrespondenceDetailsPage.saveBtn);
        waitFor(1);
    }

    @Then("I should see there is no view icon for the Outbound Correspondence")
    public void iShouldSeeThereIsNoViewIconForTheOutboundCorrespondence() {
        assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondencePage.correspondenceFileIcons));
    }

    @Given("I have an Outbound Correspondence with a notification for {string} and without Object File")
    public void i_have_an_Outbound_Correspondence_with_a_notification_for_and_without_Object_File(String notifications) {
        String[] channels = notifications.split(",");
        List<String> list = Arrays.asList(channels);
        String CID =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondenceMultipleChannels(list);
        synchronized (getCID){
            getCID.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(CID));
        }
        synchronized (getCorrespondenceResponse){
            getCorrespondenceResponse.set(getCID.get());
        }
        List<Map<String, String>> createdCorrespondences = getCID.get().getList("recipients[0].notifications");
        DPBIOutboundCorrespondenceConfigEventsSteps.notificationIds.set(getCID.get().getList("recipients[0].notifications.notificationId"));
        DPBIOutboundCorrespondenceConfigEventsSteps.currentCID.set(CID);
    }

    @Then("I should see the Correspondence Name that was created")
    public void iVerifyCorrespondenceNameExists() {
        String corrId = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        String actualCorrespondenceType = caseAndContactDetailsPage.getCorrespondenceType(corrId);
        Assert.assertFalse(actualCorrespondenceType.isEmpty());
        actualCorrespondenceType = actualCorrespondenceType.split("-")[0].trim();
        Assert.assertTrue(actualCorrespondenceType.equals(World.generalSave.get().get("CORRESPONDENCENAME").toString()));
    }

    @Given("I verify notification actions dropdown contains the following values for notification status {string}")
    public void i_verify_notification_actions_dropdown_contains_the_following_values(String status, List<String> expectedValues) {
       viewOutboundCorrespondenceStepDefs.verifyActionDropdownContainsValues(expectedValues, status);
    }

    @Given("I verify the system updates the Notification for Status {string} and cascade to Correspondence")
    public void i_verify_the_system_updates_the_Notification_for_Status_and_cascade_to_Correspondence(String status) {
        viewOutboundCorrespondenceStepDefs.verifySystemUpdatesNotificationAndCascadeCorForGivenStatus(status);
    }

    @Given("I update and store notification status date")
    public void i_update_notification_status_date() {
        viewOutboundCorrespondenceStepDefs.updateAndStoreNotificationStatusDate();
    }

    @Then("The system ignores my navigation request and keeps my data")
    public void the_system_ignores_my_navigation_request_and_keeps_my_data() {
        Assert.assertEquals(Driver.getDriver().getTitle(), "View Outbound Correspondence Details");
        viewOutboundCorrespondenceStepDefs.verifySystemKeepsStatusDate();
    }

    @Given("I store notification status and date")
    public void i_store_notification_status_and_date() {
        viewOutboundCorrespondenceStepDefs.storeNotificationStatusAndData();
    }

    @Then("The system will revert the Status and Status Date values on the screen to the values they were previously")
    public void the_system_will_revert_the_Status_and_Status_Date_values_on_the_screen_to_the_values_they_were_previously() {
        viewOutboundCorrespondenceStepDefs.verifyNotificationStatusAndDate();
    }


    @Given("I have an Outbound Correspondence with body data elements schema as {string}")
    public void iHaveAnOutboundCorrespondenceWithBodydataElementsSchema(String type) {

        Response response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondenceBodyDataElementsSchema(type);

        switch(type.toLowerCase()){
            case "validschema" :
                Assert.assertTrue( !response.jsonPath().get("data.id").toString().isEmpty());
                break;
            case "invalidobjecttype" :
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Body data schema validation error:"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("LT_1_REQ_AM_FN: string found, array expected"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("ST_1_REQ_AM_FN: string found, array expected"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Date_1_REQ_AM_FN: string found, array expected"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Num_1_REQ_AM_FN: integer found, array expected"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("CB_1_REQ_AM_FN: boolean found, array expected"));
               Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("LT_2_FN: array found, string expected"));
                break;
            case "invaliddatatype" :
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Body data schema validation error:"));
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Date_1_REQ_AM_FN[0]: number found, string expected"));
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("ST_1_REQ_AM_FN[0]: number found, string expected"));
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Num_1_REQ_AM_FN[0]: string found, number expected"));
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("CB_1_REQ_AM_FN[0]: number found, boolean expected"));
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("LT_2_FN: integer found, string expected"));
                break;
            case "invalidshorttextlength" :
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Body data schema validation error: [ST_1_REQ_AM_FN[0]: may only be 150 characters long]"));
                break;
            case "missingrequiredbodydataelements" :
                Assert.assertTrue(response.jsonPath().get("errors[0].message").toString().contains("Body data schema validation error: [ST_1_REQ_AM_FN: is missing but it is required, LT_1_REQ_AM_FN: is missing but it is required, Num_1_REQ_AM_FN: is missing but it is required, Date_1_REQ_AM_FN: is missing but it is required, CB_1_REQ_AM_FN: is missing but it is required]"));
                break;
        }

    }

    @And("there is a no Notification Object Uploaded onto Onbase for each notification")
    public void that_there_is_no_Notification_Object_Uploaded_onto_Onbase_for_each_notification() {
        assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewOutboundCorrespondenceDetailsPage.viewIconCorrespondenceLevel));
    }

    @Then("I should see the Status Date is displayed in {string} format")
    public void iShouldSeeTheStatusDateIsDisplayedInMMDDYYYYFormat(String format) {
        viewOutboundCorrespondenceStepDefs.verifyStatusDateDisplayed(format);
    }

    @Then("I should see the successful message after saving a note")
    public void iShouldSeeTheSuccessfulMessageAfterSavingANote() {
        viewOutboundCorrespondenceStepDefs.addNewNote();
    }

    @And("I add {string} new notes to the Outbound Correspondence")
    public void iAddNewNotesToTheOutboundCorrespondence(String count) {
        browserUtils.get().scrollDownUsingActions(10);
        for (int i = 1; i <= Integer.parseInt(count); i++) {
            waitFor(8);
            World.generalSave.get().put("note",i);
            inboundCorrespondencePage.addNoteInput.sendKeys(String.valueOf(World.generalSave.get().get("note")));
            inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
            assertTrue(inboundCorrespondencePage.correspondenceDetailsSaveNoteSuccessMsg.isDisplayed());
        }
    }
}



