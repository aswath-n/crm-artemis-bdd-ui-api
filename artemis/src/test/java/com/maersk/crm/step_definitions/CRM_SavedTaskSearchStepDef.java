package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_SavedTaskSearchStepDef extends CRMUtilities implements ApiBase {

    final ThreadLocal<CRM_TaskSearchStepDef> taskSrchStepDef = ThreadLocal.withInitial(CRM_TaskSearchStepDef::new);
//    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMSavedTaskSearchPage savedTaskSrchPage = new CRMSavedTaskSearchPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMTaskEditHistoryPage editHistory = new CRMTaskEditHistoryPage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();

    @When("I create the save task search")
    public void createTheSaveTaskSearch() {
        taskSrchStepDef.get().clickOnSearchButton();
        taskSrchStepDef.get().enterValueInSearchNameField();
        taskSrchStepDef.get().clickOnSaveButton();
      //  taskSrchStepDef.get().verifySuccessMsgIsDisplayed();
    }

    @Then("I verify newly created save task search is listed on the saved task search page")
    public void verifyNewlyCreatedSaveTaskSearchIsListedOnPage() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        boolean flag = false;
        int size = 1;
        if (savedTaskSrchPage.savedTaskSrchName.size() != 0) {
            if (isElementDisplayed(savedTaskSrchPage.lnkArrowForward)) {
                click(savedTaskSrchPage.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
                click(savedTaskSrchPage.lnkArrowBack);
                waitFor(1);
            }
            l1:
            for (int j = 1; j <= size; j++) {
                for (int i = 0; i < savedTaskSrchPage.savedTaskSrchName.size(); i++) {
                    if (savedTaskSrchPage.savedTaskSrchName.get(i).getText().equals(taskSrchStepDef.get().searchName.get())) {
                        Assert.assertEquals(savedTaskSrchPage.createdOnList.get(i).getText(),
                                createGeneralTask.projectDateAtHeader.getText(), "Created On mismatch");
                        flag = true;
                        break l1;
                    }
                }
                if (j != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                    waitFor(2);
                }
            }
        }

        Assert.assertTrue(flag, "Save task search is not listed in Saved task search page");
    }

    @And("I create the save task search with 50 char name")
    public void createSaveTaskSearchWith50Char() {
        taskSrchStepDef.get().clickOnSearchButton();
        taskSrchStepDef.get().searchNameFieldValidation(50);
        taskSrchStepDef.get().clickOnSaveButton();
        taskSrchStepDef.get().verifySuccessMsgIsDisplayed();
    }

    @Then("I verify saved task search is remains on the screen")
    public void verifyFirstGlanceView() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        Assert.assertEquals(savedTaskSrchPage.savedTaskSrchName.get(0).getText(), taskSrchStepDef.get().searchName.get(),
                "Saved task search is deleted from the screen");
    }

    @And("I select to expand a Saved Task Search Record")
    public void expandASavedTaskSearchRecord() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        int size = 1;
        if (savedTaskSrchPage.savedTaskSrchName.size() != 0) {
            if (isElementDisplayed(savedTaskSrchPage.lnkArrowForward)) {
                click(savedTaskSrchPage.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
                click(savedTaskSrchPage.lnkArrowBack);
                waitFor(1);
            }
            l1:
            for (int j = 1; j <= size; j++) {
                for (int i = 0; i < savedTaskSrchPage.savedTaskSrchName.size(); i++) {
                    if (savedTaskSrchPage.savedTaskSrchName.get(i).getText().equals(taskSrchStepDef.get().searchName.get())) {
                        jsClick(savedTaskSrchPage.expandArrow.get(i));
                        waitFor(3);
                        break l1;
                    }
                }
                if (j != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                    waitFor(2);
                }
            }
        }
    }

    @Then("I will be able to view the Selected Parameters for the Saved Search {string},{string},{string},{string}," +
            "{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void verifySelectedParametersForTheSavedSearch(String taskId, String taskType, String status, String statusDate,
                                                          String Priority, String dueDate, String searchCase, String caseId,
                                                          String searchConsumer, String consumerId, String advanceSearch,
                                                          String consumerFN, String consumerLN, String source, String assignee,
                                                          String createdBy, String createdOn) {
        String[] str = {"TASK ID", "TASK TYPE", "STATUS", "STATUS DATE", "DUE DATE", "PRIORITY", "SEARCH CASE",
                "CASE ID", "SEARCH CONSUMER", "CONSUMER ID", "CONSUMER FIRST NAME", "CONSUMER LAST NAME",
                "SOURCE", "ASSIGNEE", "CREATED BY", "CREATED ON"};
        String val[] = {taskId, taskType, status, statusDate, dueDate, Priority, searchCase, caseId, searchConsumer,
                consumerId, consumerFN, consumerLN, source, assignee, createdBy, createdOn};

        List<WebElement> srchParameterHeader = Driver.getDriver().findElements(By.xpath
                ("//p[text()='" + taskSrchStepDef.get().searchName.get() + "']/../../following-sibling::tr//p"));
        List<WebElement> srchParameterValue = Driver.getDriver().findElements(By.xpath
                ("//p[text()='" + taskSrchStepDef.get().searchName.get() + "']/../../following-sibling::tr//h6"));

        for (int i = 0; i < srchParameterHeader.size(); i++) {
            Assert.assertEquals(srchParameterHeader.get(i).getText(), str[i],
                    "Header " + str[i] + " is not present");
            if (val[i].isEmpty()) {
                if (i == 6 || i == 8) {
                    Assert.assertEquals(srchParameterValue.get(i).getText(), "Internal",
                            str[i] + " value is not match");
                } else {
                    Assert.assertEquals(srchParameterValue.get(i).getText(), "-- --",
                            str[i] + " value is not match");
                }
            } else {
                if (str[i].equals("STATUS DATE") || str[i].equals("DUE DATE") || str[i].equals("CREATED ON")) {
                    if (val[i].equals("today")) {
                        val[i] = "= " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(createGeneralTask.projectDateAtHeader.getText());
                    } else if (val[i].contains("+")) {
                        val[i] = "= " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(getGreaterDate(Integer.parseInt(val[i].replace("+", ""))));
                    } else if (val[i].contains("-")) {
                        val[i] = "= " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDate(getPriorDate(Integer.parseInt(val[i].replace("-", ""))));
                    }
                }
                Assert.assertEquals(srchParameterValue.get(i).getText(), val[i],
                        str[i] + " value is not match");
            }
        }
    }

    @Then("I verify No Records Available message is displayed")
    public void verifyNoRecordAvailableMessageIsDisplayed() {
        waitFor(3);
        waitForVisibility(savedTaskSrchPage.noRecordsAvailable, 5);
        Assert.assertTrue(savedTaskSrchPage.noRecordsAvailable.isDisplayed(), "User has at least one record");
    }

    @Then("I verify will have the option to delete the Saved Task Search")
    public void verifyDelectButtonIsDisplayed() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        boolean flag = false;
        int size = 1;
        if (savedTaskSrchPage.savedTaskSrchName.size() != 0) {
            if (isElementDisplayed(savedTaskSrchPage.lnkArrowForward)) {
                click(savedTaskSrchPage.lnkArrowForward);
                waitFor(2);
                size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
                click(savedTaskSrchPage.lnkArrowBack);
                waitFor(1);
            }
            l1:
            for (int j = 1; j <= size; j++) {
                for (int i = 0; i < savedTaskSrchPage.savedTaskSrchName.size(); i++) {
                    if (savedTaskSrchPage.savedTaskSrchName.get(i).getText().equals(taskSrchStepDef.get().searchName.get())) {
                        if (savedTaskSrchPage.deleteBtn.get(i).isDisplayed()) {
                            flag = true;
                            break l1;
                        }
                    }
                }
                if (j != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                    waitFor(2);
                }
            }
        }
        Assert.assertTrue(flag, "Delete button is not displayed in Saved task search page");
    }

    @And("I click on delete saved Task search button")
    public void clickOnDeleteButtonOnSavedTaskSearchPage() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        taskSrchStepDef.get().searchName.set("");
        taskSrchStepDef.get().searchName.set(savedTaskSrchPage.savedTaskSrchName.get(0).getText());
        savedTaskSrchPage.deleteBtn.get(0).click();
    }

    @Then("I verify warning message is displayed in saved task search page")
    public void verifyWarningMsgIsDisplayed() {
        waitForVisibility(savedTaskSrchPage.warningMsg, 10);
        Assert.assertTrue(savedTaskSrchPage.warningMsg.isDisplayed(), "Warning message is not displayed");
        Assert.assertTrue(savedTaskSrchPage.warningMsgTxt.isDisplayed(), " Warning message text is not displayed");
    }

    @Then("I verify newly created save task search is deleted from saved task search page")
    public void verifyNewlyCreatedSavedTaskSearchIsDeleted() {
        waitFor(1);
        boolean flag = false;
        List<WebElement> taskSearchName = Driver.getDriver().findElements(By.xpath("//tbody//tr//td[2]//p"));
        for (int i = 0; i < savedTaskSrchPage.savedTaskSrchName.size(); i++) {
            if (taskSearchName.get(i).getText().equals(taskSrchStepDef.get().searchName.get())) {
                flag = true;
                break;
            }
        }
        Assert.assertFalse(flag, "Save task search is not deleted from Saved task search page");
    }

    @Then("I get the already created saved task search name")
    public void getSavedTaskSearchName() {
        waitForVisibility(savedTaskSrchPage.savedTaskSrchName.get(0), 10);
        CRM_TaskSearchStepDef.searchName.set("");
        CRM_TaskSearchStepDef.searchName.set(savedTaskSrchPage.savedTaskSrchName.get(0).getText());
    }

    @Then("I will verify Paginate for {string} records in {string} Page")
    public void verifyPaginationControlDD(String str, String pName) {
        waitFor(1);
        int size = 1;

        if (!str.equalsIgnoreCase("show 5"))
            selectDropDown(savedTaskSrchPage.pageNationDD, str);
        int records = Integer.parseInt(str.split(" ")[1]);

        if (!isElementDisplayed(savedTaskSrchPage.lnkArrowForward)) {
            if (pName.equalsIgnoreCase("Edit"))
                assertTrue(editHistory.listEditedOn.size() <= records, "Edit History pagination count not matches");
            else
                assertTrue(savedTaskSrchPage.savedTaskSrchName.size() <= records, "Saved task search  records count not matches ");
        } else if (isElementDisplayed(savedTaskSrchPage.lnkArrowForward)) {
            click(savedTaskSrchPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
            click(savedTaskSrchPage.lnkArrowBack);
            waitFor(1);
            for (int i = 1; i <= size; i++) {
                if (i == size) {
                    if (pName.equalsIgnoreCase("Edit"))
                        assertTrue(editHistory.listEditedOn.size() <= records, "Edit History pagination count not matches");
                    else
                        assertTrue(savedTaskSrchPage.savedTaskSrchName.size() <= records, "Saved task search pagination count not matches");
                }
                if (i != size) {
                    if (pName.equalsIgnoreCase("Edit"))
                        assertTrue(editHistory.listEditedOn.size() <= records, "Edit History pagination count not matches");
                    else
                        assertEquals(savedTaskSrchPage.savedTaskSrchName.size(), records, "Saved task search pagination count not matches");
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
    }

        @Then("I verify I see warning message to update saved task search")
        public void I_verify_I_see_warning_message_to_update_saved_task_search() {
            click(taskSearchPage.searchBtn);
            waitForVisibility(savedTaskSrchPage.updateSavedTaskSearchMessage,5);
            assertTrue(savedTaskSrchPage.updateSavedTaskSearchMessage.isDisplayed(),"Update saved task search message isnt displayed");

        }

}
