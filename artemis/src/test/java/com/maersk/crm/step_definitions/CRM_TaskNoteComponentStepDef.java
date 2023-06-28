package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_TaskNoteComponentStepDef extends BrowserUtils {
    CRMTaskNoteComponentPage noteComp = new CRMTaskNoteComponentPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    public static final ThreadLocal<HashMap<String, String>> taskNoteValues = ThreadLocal.withInitial(HashMap::new);
    CRMSavedTaskSearchPage savedTaskSrchPage = new CRMSavedTaskSearchPage();

    @Then("I verify notes component present in view or edit task details page")
    public void I_verify_task_notes_component_present_in_view_or_edit_task_details_page() {
        assertTrue(isElementDisplayed(noteComp.noteComLabel), "Note Component label is not present on screen");
        assertTrue(noteComp.noteSaveButton.isDisplayed(), "Save button is not present on screen");
        assertTrue(noteComp.noteCancelButton.isDisplayed(), "Cancel button is not present on screen");
    }

    @And("I verify Notes component field length")
    public void I_verify_Notes_component_field_length() {
        waitFor(2);
        noteComp.notesFieldTxtBox.sendKeys(getRandomString(1010));
        sa.get().assertThat(noteComp.notesFieldTxtBox.getAttribute("value")).as("Note component length is not 1000 characters").hasSize(1000);
    }

    @And("I will provide information to task or SR note component")
    public void I_will_provide_information_to_task_or_SR_note_component(Map<String, String> data) {
        taskNoteValues.set(new HashMap<>());
        if (data.containsKey("noteValue") && !data.get("noteValue").isEmpty() && data.get("noteValue").equals("String")) {
            taskNoteValues.get().put("noteValue",getRandomString(50));
            sendKeyToTextField(noteComp.notesFieldTxtBox,taskNoteValues.get().get("noteValue"));
        } else if (data.containsKey("noteValue") && !data.get("noteValue").isEmpty() && data.get("noteValue").equals("Number")) {
            taskNoteValues.get().put("noteValue",getRandomNumber(50));
            sendKeyToTextField(noteComp.notesFieldTxtBox,taskNoteValues.get().get("noteValue"));
        } else if (data.containsKey("noteValue") && !data.get("noteValue").isEmpty() && data.get("noteValue").equals("String + Number")) {
            taskNoteValues.get().put("noteValue",getRandomString(900) + getRandomNumber(100));
            sendKeyToTextField(noteComp.notesFieldTxtBox,taskNoteValues.get().get("noteValue"));
        } else if (data.containsKey("noteValue") && !data.get("noteValue").isEmpty() && data.get("noteValue").equals("Line Break")) {
            taskNoteValues.get().put("noteValue", "abd\n" + "xyz\n" + "345\n" + "ABC1234%\n" + "ASHJ_9087");
            sendKeyToTextField(noteComp.notesFieldTxtBox,taskNoteValues.get().get("noteValue"));
        } else if (data.containsKey("noteValue") && !data.get("noteValue").isEmpty()) {
            sendKeyToTextField(noteComp.notesFieldTxtBox, data.get("noteValue"));
            taskNoteValues.get().put("noteValue", data.get("noteValue"));
        }
    }

    @Then("I verify Success message is displayed on note component")
    public void i_verify_Success_message_is_displayed_on_note_component() {
        waitForVisibility(createGeneralTask.lblSuccessMessage, 10);
        assertTrue(isElementDisplayed(createGeneralTask.lblSuccessMessage));
        assertTrue(isElementDisplayed(noteComp.noteSuccessMessage), "Success message Note is not displayed");
        waitFor(1);
    }

    @And("click on save button present in task or SR note component")
    public void click_on_save_button_present_in_task_or_SR_note_component() {
        taskNoteValues.get().put("createOn", noteComp.projectDateAtHeader.getText());
        taskNoteValues.get().put("createdBy", createGeneralTask.userAccountName.getText());
        noteComp.noteSaveButton.click();
    }

    @Then("I verify notes components and edit button not present in view page")
    public void I_verify_task_notes_component_and_edit_button_not_present_in_view_page() {
        assertTrue(noteComp.noteComLabel.isDisplayed(), "Note Component label is not present on screen");
        assertFalse(isElementDisplayed(noteComp.noteSaveButton), "Save button is displayed on screen");
        assertFalse(isElementDisplayed(noteComp.noteCancelButton), "Save button is displayed on screen");
        assertFalse(isElementDisplayed(noteComp.notesFieldTxtBox), "Save button is displayed on screen");
    }

    @Then("I verify saved task note")
    public void verifySavedTaskNote() {
        waitFor(1);
        assertEquals(noteComp.noteTxtValue.get(0).getText(), taskNoteValues.get().get("noteValue"),
                "Note Value is mismatch");
        assertEquals(noteComp.noteCreatedBy.get(0).getText().replace("account_circle", "").replace("\n", ""),
                taskNoteValues.get().get("createdBy"), "Note Created By is mismatch");
        if(Integer.parseInt(taskNoteValues.get().get("createOn").replace("\n"," ").split(" ")[1].split(":")[0])<10){
            assertEquals(noteComp.noteDateTime.get(0).getText(),taskNoteValues.get().get("createOn").replace("\n"," 0"),
                    "Note Created Date/Time is mismatch");
        }else{
            assertEquals(noteComp.noteDateTime.get(0).getText(),taskNoteValues.get().get("createOn").replace("\n"," "),
                    "Note Created Date/Time is mismatch");
        }

    }

    @Then("I verify List of existing Notes including all fields and ordered by date time descending")
    public void I_verify_list_is_ordered_by_date_time_descending() {
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(noteComp.lastPage)) {
            click(noteComp.lastPage);
            waitFor(1);
            size = Integer.parseInt(noteComp.notePagination.get(noteComp.notePagination.size() - 1).getText());
            click(noteComp.firstPage);
            waitFor(1);
        }
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < noteComp.noteDateTime.size(); i++) {
                waitFor(1);
                displayedInOrder.add(noteComp.noteDateTime.get(i).getText());
                toSort.add(noteComp.noteDateTime.get(i).getText());
                sa.get().assertThat(isElementDisplayed(noteComp.noteTxtValue.get(i))).as("Note Value is not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(noteComp.noteCreatedBy.get(i))).as("Note created BY is not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(noteComp.noteDateTime.get(i))).as("Note created on is not displayed").isTrue();
                recordFound = true;
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='NOTES']/..//ul/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records not found");
        Collections.sort(toSort, Collections.reverseOrder());
        assertEquals(displayedInOrder,toSort,"Note list is not sorted by date");
    }

    @Then("I verify note component is not present in create task page")
    public void iVerifyNoteComponentIsNotPresentInCreateTaskPage() {
        sa.get().assertThat(isElementDisplayed(noteComp.noteComLabel)).as("Note Component label is not present on screen").isFalse();
        sa.get().assertThat(isElementDisplayed(noteComp.noteSaveButton)).as("Save button is displayed on screen").isFalse();
        sa.get().assertThat(isElementDisplayed(noteComp.noteCancelButton)).as("Save button is displayed on screen").isFalse();
        sa.get().assertThat(isElementDisplayed(noteComp.notesFieldTxtBox)).as("Save button is displayed on screen").isFalse();
    }

    @Then("I will copy and paste the previous note text")
    public void iWillCopyAndPasteThePreviousNoteText() {
        waitForVisibility(noteComp.noteTxtValue.get(0), 10);
        sendKeyToTextField(noteComp.notesFieldTxtBox, noteComp.noteTxtValue.get(0).getText());
    }

    @Then("I click on cancel button on Note Component")
    public void clickOnCancelButtonOnNoteComponent() {
        noteComp.noteCancelButton.click();
    }

    @Then("I Verify Note Component empty after click on Continue button on Warning Popup")
    public void verifyNoteCompEmptyOrNot(){
        sa.get().assertThat(noteComp.notesFieldTxtBox.getText()).as("Note Component is not displaying empty").isEmpty();
    }
    @Then("I Verify Note Component is not empty after click on Cancel button on Warning Popup")
    public void verifyNoteComponentIsNotEmpty(){
        sa.get().assertThat(noteComp.notesFieldTxtBox.getText()).as("Note Component is not empty").isNotEmpty();
    }
    @Then("I verify Note Component has five records with pagination")
    public void verifyNoteComponentListWithPageNation() {
        waitFor(1);
        int size = 1;
        if (!isElementDisplayed(noteComp.noteCompArrowForward)) {
            assertTrue(noteComp.noteCreatedBy.size() <= 5, "Task queue filter records count not matches ");
        } else if (isElementDisplayed(noteComp.noteCompArrowForward)) {
            click(noteComp.noteCompArrowForward);
            waitFor(1);
            size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
            click(noteComp.noteCompArrowBack);
            waitFor(1);
            for (int i = 1; i <= size; i++) {
                if (i == size)
                    assertTrue(noteComp.noteCreatedBy.size() <= 5, "Task queue filter records count not matches ");
                if (i != size) {
                    assertEquals(noteComp.noteCreatedBy.size(), 5, "Task queue filter records count not matches ");
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
        if(isElementDisplayed(noteComp.noteCompArrowBack))
            click(noteComp.noteCompArrowBack);
    }


    @Then("I verify saved task note from slider")
    public void verifySavedTaskNoteFromSlider () {
        waitFor(1);
        assertEquals(noteComp.noteTxtValue.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("noteValue"),
                "Note Value is mismatch");
        assertEquals(noteComp.noteCreatedBy.get(0).getText().replace("account_circle", "").replace("\n", ""),
                CRM_GeneralTaskStepDef.taskValues.get().get("createdBy"), "Note Created By is mismatch");
        if (Integer.parseInt(CRM_GeneralTaskStepDef.taskValues.get().get("createOn").replace("\n", " ").split(" ")[1].split(":")[0]) < 10) {
            assertEquals(noteComp.noteDateTime.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("createOn").replace("\n", " 0"),
                    "Note Created Date/Time is mismatch");
        } else {
            assertEquals(noteComp.noteDateTime.get(0).getText(), CRM_GeneralTaskStepDef.taskValues.get().get("createOn").replace("\n", " "),
                    "Note Created Date/Time is mismatch");
        }
    }
}