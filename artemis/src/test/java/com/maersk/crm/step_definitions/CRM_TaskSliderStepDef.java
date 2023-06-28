package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class CRM_TaskSliderStepDef extends BrowserUtils {


    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    Actions actions = new Actions(Driver.getDriver());
    final ThreadLocal<CRM_EditTaskStepDef> editTaskStepDef = ThreadLocal.withInitial(CRM_EditTaskStepDef::new);
    CRMDemographicContactInfoPage crmDemographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMWOrkQueuePage workQueue = new CRMWOrkQueuePage();
    CRMTaskSearchPage taskSearchPage=new CRMTaskSearchPage();
    CRM_CaseMemberStepDef caseMemberStepDef=new CRM_CaseMemberStepDef();
    CRMTaskEditHistoryPage editHistory= new CRMTaskEditHistoryPage();

    @And("I will update the following information in task slider")
    public void i_will_update_the_following_information_in_task_slider(Map<String, String> data) {
        waitFor(4);
        CRM_GeneralTaskStepDef.taskValues.get().put("assignee", createGeneralTask.userAccountName.getText());
        String random = "";
        for (String key : data.keySet()) {
            switch (key) {
                case "status":
                    CRM_TaskManagementStepDef.taskStatus.set(data.get("status"));
                    waitForVisibility(myTask.statusDropdown, 5);
                    myTask.statusDropdown.click();
                    waitFor(2);
                    actions.moveToElement(Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + data.get("status") + "')]"))).click().build().perform();
                    waitFor(1);
                    if (data.get("status").equalsIgnoreCase("Cancel"))
                        CRM_GeneralTaskStepDef.taskValues.get().put("status", "Cancelled");
                    else
                        CRM_GeneralTaskStepDef.taskValues.get().put("status", data.get("status"));
                    if (data.get("status").equalsIgnoreCase("Cancel") || data.get("status").equalsIgnoreCase("Complete")) {
                        CRM_TaskManagementStepDef.dueIn.set(CRM_GeneralTaskStepDef.taskValues.get().get("dueIn"));
                        CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", "-- --");
                        //CRM_GeneralTaskStepDef.taskValues.get().put("assignee", "-- --");
                    }
                    break;
                /*
                case "taskNote":
                    if (!data.get("taskNote").isEmpty() && data.get("taskNote").equals("random")) {
                        random = "A" + getRandomString(299);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskNote", random);
                        clearAndFillText(myTask.taskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                        sendKeyToTextField(myTask.taskInfo, getRandomString(45));
                        assertEquals(myTask.taskInfo.getAttribute("value").length(), 300, "Task Note is accepting more then 300 character");
                    } else if (!data.get("taskNote").isEmpty() && data.get("taskNote").equals("maxLength")) {
                        random = "A" + getRandomString(999);
                        CRM_GeneralTaskStepDef.taskValues.get().put("taskNote", random);
                        clearAndFillText(myTask.taskInfo, CRM_GeneralTaskStepDef.taskValues.get().get("taskNote"));
                        sendKeyToTextField(myTask.taskInfo, getRandomString(45));
                        assertEquals(myTask.taskInfo.getAttribute("value").length(), 1000, "Task Note is accepting more then 1000 character");
                    } else
                        editTaskStepDef.fillText(data.get("taskNote"), "taskNote", myTask.taskInfo);
                    break;
                    */
                case "noteValue":
                    if (!data.get("noteValue").isEmpty() && data.get("noteValue").equals("random")) {
                        random = "A" + getRandomString(300);
                        CRM_GeneralTaskStepDef.taskValues.get().put("noteValue", random);
                        clearAndFillText(myTask.taskSilderTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("noteValue"));
                        //sendKeyToTextField(myTask.taskSilderTaskNotes, getRandomString(45));
                        assertEquals(myTask.taskSilderTaskNotes.getAttribute("value").length(), 301, "Task Note is accepting more then 300 character");
                    } else if (!data.get("noteValue").isEmpty() && data.get("noteValue").equals("maxLength")) {
                        random = "A" + getRandomString(1000);
                        CRM_GeneralTaskStepDef.taskValues.get().put("noteValue", random);
                        clearAndFillText(myTask.taskSilderTaskNotes, CRM_GeneralTaskStepDef.taskValues.get().get("noteValue"));
                        //sendKeyToTextField(myTask.taskSilderTaskNotes, getRandomString(45));
                        assertEquals(myTask.taskSilderTaskNotes.getAttribute("value").length(), 1000, "Task Note is accepting more then 1000 character");
                    } else
                        editTaskStepDef.get().fillText(data.get("noteValue"), "noteValue", myTask.taskSilderTaskNotes);
                    break;
                case "actionTakenSingle":
                    editTaskStepDef.get().selectSingleSelectDD(data.get("actionTakenSingle"), "actionTakenSingle", createGeneralTask.actionTakenSlider);
                    break;
                case "disposition":
                    selectDropDownSlider(createGeneralTask.disposition, data.get("disposition"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("disposition", data.get("disposition"));
                    break;
                case "externalApplicationId":
                    clearAndFillText(createGeneralTask.externalApplicationId, CRM_GeneralTaskStepDef.taskValues.get().get("externalApplicationId"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("externalApplicationId", data.get("externalApplicationId"));
                    break;
                case "invalidReason":
                    waitFor(1);
                    selectDropDownSlider(createGeneralTask.invalidReasonDD, data.get("invalidReason"));
                    CRM_GeneralTaskStepDef.taskValues.get().put("invalidReason", data.get("invalidReason"));
                    break;
                case "actionTaken":
                    if (!createGeneralTask.actionTaken.getText().isEmpty()) {
                        //in order to update the dd it needs to empty it first
                        createGeneralTask.actionTaken.click();
                        hover(createGeneralTask.actionTaken);
                        for (int i = 0; i < myTask.multiSelectedVlus.size(); ) {
                            click(myTask.multiSelectedVlus.get(i++));
                            i--;
                            waitFor(1);
                        }
                        actions.moveToElement(myTask.taskCreatedOn).click().perform();
                    }
                    synchronized (CRM_GeneralTaskStepDef.selectValue){CRM_GeneralTaskStepDef.selectValue.set(new ArrayList<>());}
                    if (data.get("actionTaken").isEmpty()) {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", "-- --");
                    } else {
                        CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", data.get("actionTaken"));
                    }
                    createGeneralTask.actionTaken.click();
                    String[] expectedValues = data.get("actionTaken").split(",");
                    for (int i = 0; i < expectedValues.length; i++) {
                        if (i != 0) {
                            CRM_GeneralTaskStepDef.selectValue.get().add(expectedValues[i]);
                        } else {
                            synchronized (CRM_GeneralTaskStepDef.actionTaken1stVlu){
                                CRM_GeneralTaskStepDef.actionTaken1stVlu.set(expectedValues[i]);
                            }
                        }
                        waitFor(5);
                        actions.moveToElement(Driver.getDriver().findElement(By.xpath("//div[@id='menu-actionTaken']//ul/li[text()='" + expectedValues[i] + "']"))).click().perform();
                    }
                    actions.moveToElement(myTask.taskCreatedOn).click().perform();
                    break;
            }
            if (data.containsKey("reasonForCancel") && !data.get("reasonForCancel").isEmpty()) {
                selectDropDown(myTask.reasonForCancelDropDown, data.get("reasonForCancel"));
                CRM_GeneralTaskStepDef.taskValues.get().put("reasonForCancel", data.get("reasonForCancel"));
                CRM_TaskManagementStepDef.dueIn.set(CRM_GeneralTaskStepDef.taskValues.get().get("dueIn"));
                CRM_GeneralTaskStepDef.taskValues.get().put("dueIn", "-- --");
            } else if (!data.containsKey("reasonForCancel") &&
                    CRM_GeneralTaskStepDef.taskValues.get().containsKey("reasonForCancel")) {
                CRM_GeneralTaskStepDef.taskValues.get().remove("reasonForCancel");
            }
        }
    }
    @Then("I will verify Case Id is removed in task slider")
    public void verifyCaseIDIsHidenInTaskSlider() {
        assertFalse(isElementDisplayed(myTask.taskSilderCaseId),"CaseId is displayed in task slider");
    }
    @Then("I will verify External application id is removed in task slider")
    public void verifyApplicationIDIsHidenInTaskSlider() {
        assertFalse(isElementDisplayed(createGeneralTask.externalApplicationId),"Application ID is displayed in task slider");
    }

    @Then("I verify {string} single select drop down value in slider")
    public void verifySingleSelectDropDownValuesInSlider(String ddName, List expectedValues) {
        waitFor(5);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        dropdown.click();
        waitFor(3);
        List actualValues = new LinkedList();
        List<WebElement> dropdownVlu =
                Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li"));
        for (int i = 0; i < dropdownVlu.size(); i++) {
            actualValues.add(dropdownVlu.get(i).getText());
        }

        for (int k = 0; k < expectedValues.size(); k++) {
            assertTrue(actualValues.contains(expectedValues.get(k)), ddName + " drop down values are incorrect");
        }

        actions.moveToElement(myTask.taskSilderTaskNotes).click().perform();
    }

    @Then("I will update the action taken in task slider as {string}")
    public void i_will_update_the_action_taken_in_task_slider_as(String value) {
        createGeneralTask.actionTaken.click();
        for (int i = 0; i < createGeneralTask.actionTakenDrDnValues.size(); i++) {
            waitFor(1);
            try {
                if (createGeneralTask.actionTakenDrDnValues.get(i).getAttribute("aria-selected").equals("true")) {
                    createGeneralTask.actionTakenDrDnValues.get(i).click();
                }
            } catch (NullPointerException e) {
                continue;
            }
        }
        actions.click(myTask.txtTaskNotes).build().perform();
        if (!value.isEmpty()) {
            selectOptionFromMultiSelectDropDown(createGeneralTask.actionTaken, value);
            CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", value);
        } else {
            CRM_GeneralTaskStepDef.taskValues.get().put("actionTaken", "-- --");
        }
        actions.click(myTask.txtTaskNotes).build().perform();
    }
    @Then("I verify the below details are displayed in task slider")
    public void verifyTaskSliderDetails() {
        if (CRM_GeneralTaskStepDef.taskValues.get().containsKey("taskInfo")){
            assertEquals(myTask.taskSilderTaskInformation.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"),
                    "Task Information is mismatch");
        }
    }

    @Then("I am navigated to the View Consumer Profile UI and it is populated with the linked Consumer ID")
    public void isUserNavigatedToConsumerProfileUIandIsPopulatedWithLinkedId(){
        waitForVisibility(crmDemographicContactInfoPage.demographicInfoTab,5);
        assertEquals("DEMOGRAPHIC INFO",crmDemographicContactInfoPage.demographicInfoTab.getText(), "DEMOGRAPHIC INFO Tab is not displaying");
        assertTrue(Driver.getDriver().findElements(crmAddPrimaryIndividualPage.PItablecells).stream().anyMatch(x -> x.getText().equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID"))),"Incorrect consumerID");
        assertTrue(Driver.getDriver().findElements(crmAddPrimaryIndividualPage.PItablecells).stream().anyMatch(x -> x.getText().equalsIgnoreCase(CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"))),"Incorrect consumerName");
    }

    @And("I click on task id on the task slider")
    public void clickOnTaskIdOnTaskSlider(){
        waitForVisibility(myTask.taskIdOnTaskSlider,500);
        click(myTask.taskIdOnTaskSlider);
        sa.get().assertThat(editHistory.taskDetailsTab.isDisplayed()).as("Header is not displayed").isTrue();
    }

    @Then("I verify GetInsured case and consumer ids in {string}")
    public void i_verify_GetInsured_case_and_consumer_ids_in(String pageType) {
        switch (pageType) {
            case "Task Slider":
                sa.get().assertThat(myTask.getInsuredCaseIdValueTaskSlider.getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("caseID")) || myTask.getInsuredCaseIdValueTaskSlider.getText().equals("Incomplete App")).
                    as("GetInsured caseId on task slider is mismatch").isTrue();
                sa.get().assertThat(myTask.getInsuredConsumerIdValueTaskSlider.getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")) || myTask.getInsuredConsumerIdValueTaskSlider.getText().equals("Incomplete App")).
                    as("GetInsured consumerId on task slider is mismatch").isTrue();
              break;

            case "Work Queue":
            case "My Task":
                sa.get().assertThat(workQueue.workQueueColumnsValues.get(8).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("caseID")) || workQueue.workQueueColumnsValues.get(8).getText().equals("Incomplete App")).
                        as("GetInsured caseId on task slider is mismatch").isTrue();
                sa.get().assertThat(workQueue.workQueueColumnsValues.get(9).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")) || workQueue.workQueueColumnsValues.get(9).getText().equals("Incomplete App")).
                        as("GetInsured consumerId on task slider is mismatch").isTrue();
                break;

            case "Create Task":
                sa.get().assertThat(createGeneralTask.getInsuredCaseIDonCreateTask.getText().equals(caseMemberStepDef.linkedGetInsuredCaseID.get()) || createGeneralTask.getInsuredCaseIDonCreateTask.getText().equals("Incomplete App")).
                        as("GetInsured caseId is mismatch on Create Task Page").isTrue();
                sa.get().assertThat(createGeneralTask.getInsuredConsumerIDonCreateTask.getText().equals(caseMemberStepDef.linkedGetInsuredConsumerID.get()) || createGeneralTask.getInsuredConsumerIDonCreateTask.getText().equals("Incomplete App")).
                        as("GetInsured consumerId is mismatch on Create Task Page").isTrue();
                break;

            case "Task Search":
                sa.get().assertThat(taskSearchPage.taskGetInsuredCaseID.get(0).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("caseID")) || taskSearchPage.taskGetInsuredCaseID.get(0).getText().equals("Incomplete App")).
                        as("GetInsured caseId on task search is mismatch").isTrue();
                sa.get().assertThat(taskSearchPage.taskGetInsuredConsumerID.get(0).getText().equals(CRM_GeneralTaskStepDef.taskValues.get().get("consumerID")) || taskSearchPage.taskGetInsuredConsumerID.get(0).getText().equals("Incomplete App")).
                        as("GetInsured consumerId on task search is mismatch").isTrue();
                break;
        }
    }


    @Then("Verify I will see error message {string}")
    public void verifyIWillSeeErrorMessage(String message) {
        waitFor(5);
        sa.get().assertThat(Driver.getDriver().findElement(By.xpath
                ("//*[text()='" + message + "']")).isDisplayed()).as("Header is not displayed").isTrue();
    }

    @Then("I verify error message contains {string}")
    public void verifyIWillSeeErrorMessageForDuplicatedTask(String message) {
        waitFor(10);
        WebElement element = Driver.getDriver().findElement(By.xpath("//*[text()=' ERROR MESSAGE']//following::p"));
        actions.moveToElement(element).click().perform();
        String errorMessage = element.getText();
        sa.get().assertThat(errorMessage.replaceAll("\\d","")).containsIgnoringCase(message.replaceAll("\\d",""));
    }
}