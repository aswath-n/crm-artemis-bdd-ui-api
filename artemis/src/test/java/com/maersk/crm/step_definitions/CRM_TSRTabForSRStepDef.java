package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

public class CRM_TSRTabForSRStepDef extends BrowserUtils {

    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CRMTSRTabForSRPage trsTabSRPage = new CRMTSRTabForSRPage();
    CRMTaskServiceRequiestTabPage TaskSRTabPage = new CRMTaskServiceRequiestTabPage();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();

    public static final ThreadLocal<HashMap<String, String>> taskValues = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<String> srId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> taskId = ThreadLocal.withInitial(String::new);

    @Then("I Verify {string} SR with {string} status does not have Initiate button in TSR tab")
    public void verifyTaskDoesNotHaveInitiateButtonTaskSRTab(String taskTypeExpected, String taskStatusExpected) {
        waitFor(2);
        boolean recordFound = false;
        int size = 1;
        if (trsTabSRPage.lnkPageNations.size() > 1 && isElementDisplayed(trsTabSRPage.lnkArrowForward)) {
            click(trsTabSRPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(trsTabSRPage.lnkPageNations.get(trsTabSRPage.lnkPageNations.size() - 1).getText());
            click(trsTabSRPage.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < trsTabSRPage.srListRows.size(); j++) {
                if (trsTabSRPage.srTypes.get(j).getText().equals(taskTypeExpected) &&
                        trsTabSRPage.srStatuses.get(j).getText().equals(taskStatusExpected)) {
                    assertEquals(trsTabSRPage.srInitiateButtonsText.size(), 0, "Initiate button displayed for SR records");
                    recordFound = true;
                    break;
                }
            }
            if (recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "SR Type and status not matches");
    }

    @And("I navigate to newly created SR in Task & Service Request Tab")
    public void navigateToNewlyCreatedTask() {
        scrollDown();
        waitFor(2);
        scrollUpRobot();
        waitFor(2);
        waitForVisibility(trsTabSRPage.columnSRId, 10);
        jsClick(trsTabSRPage.columnSRId);
        waitFor(1);
        jsClick(trsTabSRPage.columnSRId);
        waitFor(2);
        CRM_TaskManagementStepDef.taskId.set(trsTabSRPage.srIDs.get(0).getText());
        CRM_TaskManagementStepDef.srID.set(trsTabSRPage.srIDs.get(0).getText());
    }

    @When("I navigate to view SR details page by clicking on sr id")
    public void iNavigateToSRDetailsPage() {
        waitFor(4);
        scrollDown();
        int tries = 0;
        do {
            if (trsTabSRPage.srStatusDate.get(0).getText().equalsIgnoreCase(createGeneralTask.projectDateAtHeader.getText())){
                this.srId.set(trsTabSRPage.srIDs.get(0).getText());
                CRM_TaskManagementStepDef.taskId.set(trsTabSRPage.srIDs.get(0).getText());
                CRM_TaskManagementStepDef.srID.set(trsTabSRPage.srIDs.get(0).getText());
                jsClick(trsTabSRPage.srIDs.get(0));
                break;
            }
            else {
                System.out.println("Newly sr id is not displayed on the Service Request List trying again");
                jsClick(trsTabSRPage.columnSRId);
                tries++;
                break;
            }
        }while (tries!=3);
    }

    @When("I navigate to view SR details page by clicking on existing sr id")
    public void iNavigateToExistingSRDetailsPage() {
        waitFor(4);
        scrollDown();
        if(!trsTabSRPage.srStatusDate.get(0).getText().equalsIgnoreCase(createGeneralTask.projectDateAtHeader.getText())){
            jsClick(trsTabSRPage.srIDs.get(0));
        }
    }

    @When("I verify latest task displayed in {string} page")
    public void iVerifyLatestTaskDisplayed(String pageName) {
        waitFor(2);
        switch (pageName){
            case "Task & Service Request":
                assertEquals(TaskSRTabPage.taskIDs.get(0).getText(),
                        Integer.parseInt(CRM_TaskManagementStepDef.taskId.get())+1+"","Task ID not matches");
                break;
            case "Work Queue or My Task":
                boolean recordFound=false;
                l1:for (int i = 1; i <= 5; i++) {
                    for (int j = 0; j < myTask.taskIDs.size(); j++) {
                        if (myTask.taskIDs.get(j).getText().equalsIgnoreCase(CRM_TaskManagementStepDef.taskId.get())) {
                            recordFound = true;
                            break l1;
                        }
                    }
                    if (i != 5) {
                        click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                        waitFor(1);
                    }
                }
                assertTrue(recordFound, "Task Type not found");

                break;
            case "Task Search":
                assertEquals(taskSearchPage.taskIDs.get(0).getText(),CRM_TaskManagementStepDef.taskId.get(), "Task ID not matches");
                break;
            default:
                Assert.fail("Entered Key did not match existing keys");
        }
    }

    @Then("I verify all fields are displayed on Service Request Summary page")
    public void i_verify_all_field_are_displayed_on_Service_Request_Summary_page() {
        sa.get().assertThat(trsTabSRPage.srListLabel.isDisplayed()).as("SR list label is not present on screen").isTrue();
        sa.get().assertThat(trsTabSRPage.columnSRId.isDisplayed()).as("SR Id column is not present on SR page").isTrue();
        sa.get().assertThat(trsTabSRPage.srtypeColumn.isDisplayed()).as("Type column is not present on SR page").isTrue();
        sa.get().assertThat(trsTabSRPage.srpriorityColumn.isDisplayed()).as("Priority column is not present on SR page").isTrue();
        sa.get().assertThat(trsTabSRPage.srstatusColumn.isDisplayed()).as("Status column is not present on SR page").isTrue();
        sa.get().assertThat(trsTabSRPage.srStatusDateColumn.isDisplayed()).as("StatusDate column is not present on SR page").isTrue();
        sa.get().assertThat(trsTabSRPage.srconsumerNameColumn.isDisplayed()).as("Consumer Name column is not present on SR page").isTrue();
    }

    @Then("I verify all fields SR values on Task & Service Request Tab")
    public void verifyAllFieldsSRValuesOnTaskAndServiceRequestTab() {
        waitFor(2);
        sa.get().assertThat(hasOnlyDigits(trsTabSRPage.srIDs.get(0).getText())).as("Is not digit").isTrue();
        sa.get().assertThat(trsTabSRPage.srTypes.get(0).getText()).as("Task type is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskType"));
        sa.get().assertThat(trsTabSRPage.srpriorities.get(0).getText()).as("Priority is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("priority"));
        sa.get().assertThat(trsTabSRPage.srStatuses.get(0).getText()).as("Status is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("status"));
        sa.get().assertThat(trsTabSRPage.srStatusDate.get(0).getText()).as("Status date is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("startDate"));
        sa.get().assertThat(trsTabSRPage.srconsumerNames.get(0).getText()).as("Consumer Name is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("consumerName"));
    }

    @When("I expand the first row in SR list")
    public void expandATaskRecordromSRList() {
        waitFor(1);
        trsTabSRPage.expandSRArrows.get(0).click();
    }

    @Then("I verify all headers are displayed in SR service request tab")
    public void verifyHeadersWhenIExpandTheSR() {
        waitFor(1);
        String[] str = {"CASE ID", "CREATED ON", "CREATED BY", "SR INFORMATION"};
        for (String s : str) {
            sa.get().assertThat(Driver.getDriver().findElement(By.xpath("//p[text()='" + s + "']")).isDisplayed()).as(s + " is not displayed").isTrue();
        }
    }

    @When("I click on first SR ID")
    public void i_click_on_first_SR_ID() {
        this.srId.set(trsTabSRPage.srIDs.get(0).getText());
        scrollUpRobotKey();
        jsClick(trsTabSRPage.srIDs.get(0));
    }

    @Then("I verify SR id and edit SR button are displayed")
    public void verifySRIdEditLink() {
        assertTrue(isElementDisplayed(trsTabSRPage.lblSRId), "SR Id is not present on view page");
        assertTrue(isElementDisplayed(trsTabSRPage.btnEditSR), "Edit button is not present on view page");
    }

    @Then("I verify the SR details are displayed when expanded")
    public void verifySRDetailsWhenExpanded() {
        waitFor(1);
        sa.get().assertThat(trsTabSRPage.createdBySRValue.get(0).getText()).as("CreatedBy is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_by"));
        if (CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").equals("") ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo") == null ||
                CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo").isEmpty()) {
            sa.get().assertThat(trsTabSRPage.srInfoValue.get(0).getText()).as("SR Information is mismatch").isEqualTo("-- --");
        } else {
            sa.get().assertThat(trsTabSRPage.srInfoValue.get(0).getText()).as("SR information is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("taskInfo"));
        }
        sa.get().assertThat(trsTabSRPage.createdOnSR.get(0).getText()).as("Created On is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("created_on"));
        sa.get().assertThat(trsTabSRPage.caseIDValue.get(0).getText()).as("Case ID is mismatch").isEqualTo(CRM_GeneralTaskStepDef.taskValues.get().get("caseID"));
    }

    @Then("I will click on back arrow on view sr page")
    public void clickBackArrowInViewsrPage() {
        click(trsTabSRPage.backarrow);
    }

    @Then("I verify the pagination in SR List present in TSR tab")
    public void verifySRListWithPageNation() {
        assertTrue(trsTabSRPage.srIDs.size() <= 5);
    }

    @Then("I will not see the Service Request records in the Task List grid {string}")
    public void verifySRRecordNotPresentInTaskList(String serviceRequest) {
        waitFor(1);
        boolean recordFound = false;
        int size = 1;
        if (isElementDisplayed(myTask.lnkArrowForward)) {
            click(myTask.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(TaskSRTabPage.lnkPageNations.get(TaskSRTabPage.lnkPageNations.size() - 1).getText());
            click(myTask.lnkArrowBack);
            waitFor(1);
        }
        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < TaskSRTabPage.taskTypes.size(); j++) {
                assertNotEquals(serviceRequest, (TaskSRTabPage.taskTypes.get(j)), "SR present in Task List");
                waitFor(2);
                recordFound = true;
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='TASK LIST']/..//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records not found");
    }

    @Then("I will verify default sorting order in SR list present on TSR tab")
    public void verifySortingOrderInSRList() {
        waitFor(1);
        boolean recordFound = false;
        List<Integer> priority = new ArrayList<>();
        ArrayList<Integer> toSort = new ArrayList();
        int size = 1;
        size = getSizeOfPagination(size);
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < trsTabSRPage.srStatuses.size(); i++) {
                waitFor(1);
                if (trsTabSRPage.srStatuses.get(i).getText().equalsIgnoreCase("Open")) {
                    priority.add(Integer.parseInt(trsTabSRPage.srpriorities.get(i).getText()));
                    toSort.add(Integer.parseInt(trsTabSRPage.srpriorities.get(i).getText()));
                    recordFound = true;
                } else
                    break;
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='SERVICE REQUEST LIST']/..//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records are not present in the SR List");
        Collections.sort(toSort);
        assertEquals(priority, toSort, "Default sorting order in SR list present on TSR tab is wrong");
    }

    @Then("I will verify default sorting order of closed SR present in SR list on TSR tab")
    public void verifyDecSortingOrderInSRList() {
        waitFor(1);
        boolean recordFound = false;
        List<String> statusDate = new ArrayList<>();
        int size = 1;
        size = getSizeOfPagination(size);
        for (int j = 1; j <= size; j++) {
            for (int i = 0; i < trsTabSRPage.srStatuses.size(); i++) {
                waitFor(1);
                if (trsTabSRPage.srStatuses.get(i).getText().equalsIgnoreCase("Closed")) {
                    statusDate.add(trsTabSRPage.srStatusDate.get(i).getText());
                    recordFound = true;
                } else
                    break;
            }
            if (j != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='SERVICE REQUEST LIST']/..//ul[@class='pagination']/li/a[text()='" + (j + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records not found");
        assertTrue(descendingOrderDatesString(statusDate), "Closed SR is not sorted by Status date");
    }

    @When("I click on {string} column on SR List")
    public void i_click_on_column_on_SR_List(String columnName) {
        waitFor(2);
        String value = "";
        switch (columnName) {
            case "SR ID":
                trsTabSRPage.columnSRId.click();
                waitFor(1);
                break;
            case "TYPE":
                trsTabSRPage.srtypeColumn.click();
                waitFor(1);
                break;
            case "PRIORITY":
                trsTabSRPage.srpriorityColumn.click();
                waitFor(1);
                trsTabSRPage.srpriorityColumn.click();
                break;
            case "STATUS":
                trsTabSRPage.srstatusColumn.click();
                waitFor(1);
                break;
            case "STATUSDATE":
                trsTabSRPage.srStatusDateColumn.click();
                waitFor(1);
                break;
            case "CONSUMER NAME":
                trsTabSRPage.srconsumerNameColumn.click();
                waitFor(1);
                break;
        }
    }

    @Then("I verify SR records are displayed in ascending order of their {string}")
    public void verifyAscendingOrder(String columnsName) {
        waitFor(1);
        int columnNum = 0;
        switch (columnsName) {
            case "SR ID":
                columnNum = 2;
                break;
            case "TYPE":
                columnNum = 3;
                break;
            case "PRIORITY":
                columnNum = 4;
                break;
            case "STATUS":
                columnNum = 5;
                break;
            case "STATUSDATE":
                columnNum = 6;
                break;
            case "CONSUMER NAME":
                columnNum = 7;
                break;
        }
        waitFor(3);
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        ArrayList<Integer> srIdtoSort = new ArrayList();
        ArrayList<Integer> srIdDisplayedInOrder = new ArrayList();
        boolean recordFound = false;
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (WebElement row : trsTabSRPage.srListRows) {
                if(columnNum==2 || columnNum==4 ) {
                    srIdtoSort.add(Integer.parseInt(row.findElement(By.xpath("./td[" + columnNum + "]")).getText()));
                    srIdDisplayedInOrder.add(Integer.parseInt(row.findElement(By.xpath("./td[" + columnNum + "]")).getText()));
                    recordFound = true;
                }
                else {
                    toSort.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    displayedInOrder.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    recordFound = true;
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='SERVICE REQUEST LIST']/..//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records not found");
        if(columnNum==2 || columnNum==4 ) {
            Collections.sort(srIdtoSort);
            Assert.assertEquals(srIdDisplayedInOrder, srIdtoSort, "Records present in SR List are not in ascending order by SR ID");
        }
        else{
            Collections.sort(toSort);
            Assert.assertEquals(displayedInOrder, toSort, "Records present in SR List are not in ascending order");
        }
    }
    @When("I double click on {string} column on SR List")
    public void i_double_click_on_column_on_SR_List(String columnName) {
        waitFor(2);
        String value = "";
        switch (columnName) {
            case "SR ID":
                trsTabSRPage.columnSRId.click();
                waitFor(1);
                trsTabSRPage.columnSRId.click();
                break;
            case "TYPE":
                trsTabSRPage.srtypeColumn.click();
                waitFor(1);
                trsTabSRPage.srtypeColumn.click();
                break;
            case "PRIORITY":
                trsTabSRPage.srpriorityColumn.click();
                waitFor(1);
                break;
            case "STATUS":
                trsTabSRPage.srstatusColumn.click();
                waitFor(1);
                trsTabSRPage.srstatusColumn.click();
                break;
            case "STATUSDATE":
                trsTabSRPage.srStatusDateColumn.click();
                waitFor(1);
                trsTabSRPage.srStatusDateColumn.click();
                break;
            case "CONSUMER NAME":
                trsTabSRPage.srconsumerNameColumn.click();
                waitFor(1);
                trsTabSRPage.srconsumerNameColumn.click();
                break;
        }
    }

    @Then("I verify SR records are displayed in descending order of their {string}")
    public void verifyDescendingOrder(String columnsName) {
        waitFor(1);
        int columnNum = 0;
        switch (columnsName) {
            case "SR ID":
                columnNum = 2;
                break;
            case "TYPE":
                columnNum = 3;
                break;
            case "PRIORITY":
                columnNum = 4;
                break;
            case "STATUS":
                columnNum = 5;
                break;
            case "STATUSDATE":
                columnNum = 6;
                break;
            case "CONSUMER NAME":
                columnNum = 7;
                break;
        }
        waitFor(3);
        ArrayList<String> displayedInOrder = new ArrayList();
        ArrayList<String> toSort = new ArrayList();
        ArrayList<Integer> srIdtoSort = new ArrayList();
        ArrayList<Integer> srIdDisplayedInOrder = new ArrayList();
        boolean recordFound = false;
        int size = 1;
        size = getSizeOfPagination(size);
        for (int i = 1; i <= size; i++) {
            for (WebElement row : trsTabSRPage.srListRows) {
                if(columnNum==2 || columnNum==4 ) {
                    srIdtoSort.add(Integer.parseInt(row.findElement(By.xpath("./td[" + columnNum + "]")).getText()));
                    srIdDisplayedInOrder.add(Integer.parseInt(row.findElement(By.xpath("./td[" + columnNum + "]")).getText()));
                    recordFound = true;
                }
                else {
                    toSort.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    displayedInOrder.add(row.findElement(By.xpath("./td[" + columnNum + "]")).getText());
                    recordFound = true;
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//h5[text()='SERVICE REQUEST LIST']/..//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        assertTrue(recordFound, "Records not found");
        if(columnNum==2 || columnNum==4 ) {
            Collections.sort(srIdtoSort, Collections.reverseOrder());
            Assert.assertEquals(srIdDisplayedInOrder, srIdtoSort, "Records present in SR List are not in descending order");
        }
        else{
            Collections.sort(toSort, Collections.reverseOrder());
            Assert.assertEquals(displayedInOrder, toSort, "Records present in SR List are not in descending order");
        }
    }

    private int getSizeOfPagination(int size) {
        if (isElementDisplayed(trsTabSRPage.lnkArrowForward)) {
            click(trsTabSRPage.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(trsTabSRPage.lnkPageNations.get(trsTabSRPage.lnkPageNations.size() - 1).getText());
            click(trsTabSRPage.lnkArrowBack);
            waitFor(1);
        }
        return size;
    }
}
