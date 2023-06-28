package com.maersk.dms.steps;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.InboundCorrespondenceSearchPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.step_definitions.InboundCorrespondenceNoteEventStepDefs;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InboundCorrespondenceNoteEventSteps extends CRMUtilities implements ApiBase {

    private final ThreadLocal<InboundCorrespondenceNoteEventStepDefs> inboundCorrespondenceNoteEventStepDefs = ThreadLocal.withInitial(InboundCorrespondenceNoteEventStepDefs::new);
    private InboundCorrespondenceSearchPage inboundCorrespondenceSearchPage = new InboundCorrespondenceSearchPage();
    private CRMDashboardPage dashboard = new CRMDashboardPage();
    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(()->"");
    private final ThreadLocal<EventsUtilities> eventsUtilities = ThreadLocal.withInitial(EventsUtilities::new);
    private InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    private final ThreadLocal<String> noteBox = ThreadLocal.withInitial(()->"");
    private ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    CRMTaskManageMyTasksPage taskManageMyTasksPage = new CRMTaskManageMyTasksPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);
    private final ThreadLocal<List<String>> tabs = ThreadLocal.withInitial(ArrayList::new);

    /**
     * @param dataTable - iterates through dataTable map and fills out in ui the search criteria, does not click search
     */
    @When("I search for an Inbound Document by the following values")
    public void inboundSearchUI(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            inboundCorrespondenceNoteEventStepDefs.get().fillInSearchCriteria(searchCriteria, dataTable.get(searchCriteria));
        }
        inboundCorrespondenceSearchPage.searchButton.click();
        if (dataTable.containsKey("FROMNAME")) {
            if (dataTable.get("FROMNAME").equalsIgnoreCase("NEVERUSETHISNAME")) {
                return;
            }
        } else if (dataTable.containsKey("BEFOREDATERECEIVED")) {
            if (dataTable.get("BEFOREDATERECEIVED").equalsIgnoreCase("BEFORETODAY")) {
                return;
            }

        } else if (dataTable.containsKey("CID")) {
            if (dataTable.get("CID").equalsIgnoreCase("fromRequest")) {
                return;
            }

        }
        waitFor(2);
        try {
            waitForVisibility(inboundCorrespondenceSearchPage.cidList.get(0), 30);
        } catch (Exception exception) {
            Assert.fail("No results");

        }
    }

    //Commented below code, as it is clicking one of the drop down in the page unnecessarily. Modified by:Basha
    @And("I should see the Inbound Correspondence Search Icon")
    public void iShouldSeeTheInboundCorrespondenceSearchIcon() {
        boolean successful = false;
        try {
//            waitForClickablility(inboundCorrespondenceSearchPage.inboundSearchPageMenuItem, 60);
            waitForVisibility(dashboard.sideMenuCorrespondenceArrowUpButton,60);
            inboundCorrespondenceSearchPage.inboundSearchPageMenuItem.click();
            waitForPageToLoad(60);
            Driver.getDriver().findElement(By.xpath("//body")).click();
            waitFor(10);
            successful = inboundCorrespondencePage.inboundCorrespondenceSearchHeader.isDisplayed();
            waitFor(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!successful) {
            new OutboundCorrespondenceGlobalSearch().iClickOnTheMainNavigationMenu();
            clickInboundSearch();
        }
        Assert.assertTrue(inboundCorrespondencePage.inboundCorrespondenceSearchHeader.isDisplayed());
    }

    public void clickInboundSearch() {
        try {
            waitForVisibility(dashboard.sideMenuCorrespondenceArrowUpButton,60);
//            waitForClickablility(inboundCorrespondenceSearchPage.inboundSearchPageMenuItem, 4);
            inboundCorrespondenceSearchPage.inboundSearchPageMenuItem.click();
            waitForPageToLoad(60);
            Driver.getDriver().findElement(By.xpath("//body")).click();
            waitFor(10);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @And("I click on the Inbound Document CID link {string}")
    public void iClickOnTheInboundDocumentCIDLink(String cid) {
        if ("firstAvailable".equalsIgnoreCase(cid)) {
            inboundCorrespondenceNoteEventStepDefs.get().selectFirstInList();
        } else {
            inboundCorrespondenceNoteEventStepDefs.get().selectSpecificCidInList(cid);
        }
    }

    @When("I add a new note to the Inbound Correspondence")
    public void iAddANewNoteToTheInboundCorrespondence() {
        inboundCorrespondenceNoteEventStepDefs.get().addRandomNote();
    }


    @And("I should see that the event mapping for {string} exists")
    public void iShouldSeeThatTheEventMappingForExists(String event) {
        eventsUtilities.get().verifyEventMapping(event);
    }

   /* @And("I should see that the {string} record is produced to DPBI")
    public void iShouldSeeThatTheRecordIsProducedToDPBI(String event) {
        switch (event) {
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                inboundCorrespondenceNoteEventStepDefs.get().verifyEventSentDPBI(event);
                break;
            case "NOTIFICATION_UPDATE_EVENT":
                new OutboundCorrespondenceDefinitionStepDefs().verifyEventSentDPBI(event);
                break;
        }

    }*/

    @Then("i should see that the system captures the following data")
    public void iShouldSeeThatTheSystemCapturesTheFollowingData(List<String> data) {
        for (String i : data) {
            if (i.equalsIgnoreCase("Created On")) {
                assertEquals(inboundCorrespondenceSearchPage.firstCreatedOn.getText().substring(0, 10), getCurrentDate());
            } else if (i.equalsIgnoreCase("Created By")) {

                assertTrue(inboundCorrespondenceSearchPage.firstCreatedby.isDisplayed());
            }
        }
    }

    @Then("i should see that the system allows 500 free text characters in the note field")
    public void iShouldSeeThatTheSystemAllowsFreeTextCharactersInTheNoteField() {
        waitFor(4);
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().length() <= 500);
    }

    @And("I add a second new note to the Inbound Correspondence")
    public void iAddASecondNewNoteToTheInboundCorrespondence() {
        waitFor(5);
        inboundCorrespondencePage.addNoteInput.sendKeys("Second note added");
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        waitFor(2);
    }

    @Then("I should see the most recent note at the top")
    public void iShouldSeeTheMostRecentNoteAtTheTop() {
        waitFor(4);
        List<WebElement> notes = inboundCorrespondencePage.notesList;
        System.out.println(notes.get(0).getText());
        assertEquals(notes.get(0).getText().toUpperCase(), "SECOND NOTE ADDED");
    }

    @When("I add a new note to the Inbound Correspondence but dont save")
    public void iAddANewNoteToTheInboundCorrespondenceButDontSave() {
        inboundCorrespondencePage.addNoteInput.sendKeys(getRandomString(30));
        noteBox.set(inboundCorrespondencePage.addNoteInput.getText());
        waitFor(2);
    }

    @Then("I should see that I am able to cancel the note")
    public void iShouldSeeThatIAmAbleToCancelTheNote() {
        inboundCorrespondencePage.correspondenceDetailsNotesCancelBtn.click();
        waitFor(1);
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().isEmpty());
    }

    @Then("I should see that a warning message will come warning about navigating away")
    public void iShouldSeeThatAWarningMessageWillComeWarningAboutNavigatingAway() {
        waitFor(2);
        assertEquals(inboundCorrespondencePage.warningMessage.getText(),
                "If you continue, all the captured information will be lost");
    }

    @And("I click cancel in Warning Message popup")
    public void iClickCancel() {
        waitFor(1);
        inboundCorrespondencePage.warningMessageCancel.click();
    }

    @And("I click continue in Warning Message popup")
    public void iClickContinue() {
        waitFor(1);
        inboundCorrespondencePage.warningMessageContinue.click();
    }

    @Then("I should see that my data is intact")
    public void iShouldSeeThatMyDataIsIntact() {
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().equalsIgnoreCase(noteBox.get()));
    }

    @And("I click discard my changes")
    public void iClickDiscardMyChanges() {
        waitFor(2);
        inboundCorrespondencePage.createOutboundPopupContinueBtn.click();
    }

    @Then("I should see that my changes are gone and navigated away from page")
    public void iShouldSeeThatMyChangesAreGoneAndNavigatedAwayFromPage() {
        waitFor(1);
        assertTrue(inboundCorrespondencePage.inboundCorrespondenceSearchHeader.isDisplayed());
        inboundCorrespondenceNoteEventStepDefs.get().selectFirstInList();
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().isEmpty());
    }

    @Then("I should see that there is a {string} button")
    public void iShouldSeeThatThereIsAButton(String button) {
        waitForVisibility(inboundCorrespondenceSearchPage.getImageLocation, 15);
        assertTrue(inboundCorrespondenceSearchPage.getImageLocation.getText().contains(button));
    }

    @When("I click on he Get Image Location button")
    public void iClickOnHeGetImageLocationButton() {
        World.generalSave.get().put("userId", BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.loggedInUserId.getText().trim()));
        waitFor(2);
        inboundCorrespondenceSearchPage.getImageLocation.click();
    }

    @Then("I should see {string} pop up message")
    public void iShouldSeePopUpMessage(String popupNote) {
        waitForVisibility(inboundCorrespondenceSearchPage.clipPopUp, 15);
        assertEquals(inboundCorrespondenceSearchPage.clipPopUp.getText(), popupNote);
    }


    @Then("I should see the full path to the image is copied into the user's clipboard")
    public void iShouldSeeTheFullPathToTheImageIsCopiedIntoTheUserSClipboard() {
        waitFor(7);
        scrollDown();
        inboundCorrespondencePage.addNoteInput.sendKeys(Keys.CONTROL, "v");
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        waitFor(7);
        scrollDown();
        waitFor(2);
        assertEquals("\\\\UVAAPMMFSG01SBE\\NJSBE-NONPROD\\QE\\ECMSFILES\\" + APIATSApplicationController.loggedInUserId + "\\" + World.generalSave.get().get("InboundCorrespondenceId").toString() + ".PDF", inboundCorrespondencePage.firstNote.getText().toUpperCase().trim());
    }

    @Then("I should see the path of the image is copied into the user's clipboard")
    public void iShouldSeeThePathOfTheImageIsCopiedIntoTheUserSClipboard() {
        String userId = World.generalSave.get().get("userId").toString();
        waitFor(7);
        scrollDown();
        inboundCorrespondencePage.addNoteInput.sendKeys(Keys.CONTROL, "v");
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        waitFor(7);
        scrollDown();
        waitFor(2);
        String note1 = inboundCorrespondencePage.firstNote.getText().toUpperCase().trim();
        String note2 = "";
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName().equals("CoverVA")) {
            note2 = "\\\\UVAAPMMFSG01CVA\\VACV-NONPROD\\QE\\ECMSFILES\\" + userId + "\\" + World.generalSave.get().get("InboundDocumentId").toString() + ".PDF";
        } else if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName().equals("NJ-SBE")) {
            note2 = "\\\\UVAAPMMFSG01SBE\\NJSBE-NONPROD\\QE\\ECMSFILES\\" + userId + "\\" + World.generalSave.get().get("InboundDocumentId").toString() + ".PDF";
        }
        assertEquals(note1, note2);
    }


    @And("I click on the clear button on Inbound Document search")
    public void iClickOnTheClearButtonOnInboundDocumentSearch() {
        waitFor(7);
        inboundCorrespondencePage.cancelButton.click();
        waitFor(7);
    }

    @Then("I should see the clipboard has been cleared out")
    public void iShouldSeeTheClipboardHasBeenClearedOut() {
        waitFor(7);
        scrollDown();
        inboundCorrespondencePage.addNoteInput.sendKeys(Keys.CONTROL, "v");
        waitFor(2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        waitFor(7);
        scrollDown();
        waitFor(2);
        assertTrue(BrowserUtils.validNumberFilter(inboundCorrespondencePage.firstNote.getText().toUpperCase().trim()).length() < 2);
    }

    @Then("I verify clipboard has been cleared out")
    public void iVerifyClipboardHasBeenClearedOut() {
        waitFor(7);
        scrollDown();
        inboundCorrespondencePage.addNoteInput.sendKeys(Keys.CONTROL, "v");
        waitFor(2);
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().length() < 2);
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        waitFor(7);
        scrollDown();
        waitFor(2);
        assertTrue(inboundCorrespondencePage.addNoteInput.getText().length() < 2);
    }

    @Then("I should see that the Inbound Document is no longer available in the Simple Storage Service bucket")
    public void iShouldSeeThatTheInboundDocumentIsNoLongerAvailableInTheSimpleStorageServiceBucket() {
        String url = inboundCorrespondencePage.addNoteInput.getText().trim();
        Assert.assertNotNull(url);
        Assert.assertTrue(url.contains("QE"), "url - " + url);
        Driver.getDriver().get(url);
        waitFor(4);
        Assert.assertTrue(inboundCorrespondencePage.pageNotFound.isDisplayed());
    }

    @And("I paste the path into the note field to verify it on the Inbound Document Details Page")
    public void iPasteThePathIntoTheNoteFieldToVerifyItOnTheInboundDocumentDetailsPage() {
        String id = BrowserUtils.validNumberFilter(inboundCorrespondencePage.userId.getText());
        String cid = inboundCorrespondencePage.cid.getText();
        browserUtils.get().scrollDownUsingActions(10);
        inboundCorrespondenceNoteEventStepDefs.get().pasteNote();
        waitFor(7);
        String url = inboundCorrespondencePage.addNoteInput.getText().trim();
        Assert.assertTrue(url.contains(id));
        Assert.assertTrue(url.contains(cid));
    }

    @Then("I am navigated back to the Inbound Correspondence Details screen")
    public void i_am_navigated_back_to_the_Inbound_Correspondence_Details_screen() {
        waitFor(2);
        Assert.assertTrue(inboundCorrespondencePage.inboundCorrespondenceHeaderTxt.isDisplayed());
    }

    @Given("I should be navigated to image viewer")
    public void i_should_be_navigated_to_image_viewer() {
        waitFor(3);
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        Driver.getDriver().switchTo().window(tabs.get().get(1));
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().startsWith("blob:" + ConfigurationReader.getProperty("crmPageURL")), "PDF viewer verification failed");
    }

    @Given("I close image viewer")
    public void i_close_image_viewer() {
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(tabs.get().get(0));
    }

    @Given("I store all tasks under Links section")
    public void i_store_all_tasks_under_Links_section() {
        inboundCorrespondenceNoteEventStepDefs.get().storeAllTasks();
    }

    @When("I click on newly added task after type changed")
    public void i_click_on_newly_added_task_after_type_changed() {
        inboundCorrespondenceNoteEventStepDefs.get().clickOnNewlyAddedTask();
    }

    @Then("I should be navigated to Task Details Page")
    public void i_should_be_navigated_to_Task_Details_Page() {
        waitForVisibility(taskManageMyTasksPage.taskDetailsHeader, 5);
        Assert.assertTrue(taskManageMyTasksPage.taskDetailsHeader.isDisplayed(), "Task Details tab verification failed");
    }

    @Then("I should see a Link to the {string} in the Links Section")
    public void i_should_see_a_Link_to_the_in_the_Links_Section(String value) {
        inboundCorrespondenceNoteEventStepDefs.get().verifyLinkInLinkSection(value);
    }

    @When("I should see new task added to the task list")
    public void i_should_see_new_task_added_to_the_task_list() {
        inboundCorrespondenceNoteEventStepDefs.get().verifyTaskAddedToTaskList();
    }

    @Given("I verify link from new Task to Application is created")
    public void i_verify_link_from_new_Task_to_Application_is_created() {
        inboundCorrespondenceNoteEventStepDefs.get().verifyLinkFromTaskToApplication();
    }

    @Given("I verify link from Application to new Task is created")
    public void i_verify_link_from_Application_to_new_Task_is_created() {
        inboundCorrespondenceNoteEventStepDefs.get().verifyLinkFromApplicationToTask();
    }


}
