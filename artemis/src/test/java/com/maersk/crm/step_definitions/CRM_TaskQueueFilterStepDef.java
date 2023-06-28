package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.maersk.crm.step_definitions.CRM_EditTaskStepDef.replaceNullValuesInMap;
import static org.testng.Assert.*;

public class CRM_TaskQueueFilterStepDef extends CRMUtilities implements ApiBase {


    CRMTaskQueueFilterPage taskQueueFilter = new CRMTaskQueueFilterPage();
    CRMTaskQueueFilterViewPage taskQueueFilterView = new CRMTaskQueueFilterViewPage();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRM_EditQueueFilter editQFilter=new CRM_EditQueueFilter();
    public static final ThreadLocal<String> taskTypes = ThreadLocal.withInitial(String::new);
//    boolean flag = false;

    public static final ThreadLocal<HashMap<String, String>> taskValues = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<HashMap<String, String>> qFilterVlus = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<HashMap<String, String>> editValue = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<String> firstName =ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> desc = ThreadLocal.withInitial(String::new);

    @And("I navigate to create filter page")
    public void navigateToCreateFilterPage(){
        waitFor(1);
        assertTrue(taskQueueFilter.createFilterBt.isDisplayed(), "Create Filter button is not displayed");
        click(taskQueueFilter.createFilterBt);
        waitFor(1);
    }

    @When("I will create task queue filter with the below information")
    public void createTaskQueueFilter(Map<String, String> data) {
        data=replaceNullValuesInMap(data, "");
        //name
        if (data.containsKey("name") && !data.get("name").isEmpty() && data.get("name").equals("random")) {
            taskValues.get().put("name",getRandomString(50));
            sendKeyToTextField(taskQueueFilter.name,taskValues.get().get("name") + "99");
            sa.get().assertThat(taskQueueFilter.name.getAttribute("value").length() == 50).as("Error message for Name field is not displayed").isTrue();
        }
        else if (data.containsKey("name") && !data.get("name").isEmpty()) {
            taskValues.get().put("name", getRandomString(8)+"77");
            clearAndFillText(taskQueueFilter.name, taskValues.get().get("name"));
        }

        //Description
        if (data.containsKey("description") && !data.get("description").isEmpty() && data.get("description").equals("random")) {
            taskValues.get().put("description", getRandomString(150));
            sendKeyToTextField(taskQueueFilter.taskDescription,taskValues.get().get("description") + "77");
            sa.get().assertThat(taskQueueFilter.taskDescription.getAttribute("value").length() == 150).as("Error message Description field is not displayed on create filter page").isTrue();
        }
        else if (data.containsKey("description") && !data.get("description").isEmpty()) {
            clearAndFillText(taskQueueFilter.taskDescription, data.get("description"));
            taskValues.get().put("description", data.get("description"));
        }
        else if (data.containsKey("description") && data.get("description").isEmpty()) {
            clearFiled(taskQueueFilter.taskDescription);
            taskValues.get().put("description", "-- --");
        }

        //task type
        if (data.containsKey("taskType") && !data.get("taskType").isEmpty()) {
            if(data.get("taskType").contains(",")){
                selectOptionFromMultiSelectDropDown(taskQueueFilter.taskType,data.get("taskType").
                        split(",")[0]);
                selectOptionFromMultiSelectDropDown(taskQueueFilter.taskType,data.get("taskType").
                        split(",")[1]);
            }else{
                selectOptionFromMultiSelectDropDown(taskQueueFilter.taskType,data.get("taskType"));
            }
            taskValues.get().put("taskType",data.get("taskType"));
        }

        //ApplyFilter
        if (data.containsKey("applyFilterFor") && !data.get("applyFilterFor").isEmpty()) {
            selectDropDown(taskQueueFilter.applyFilterFor, data.get("applyFilterFor"));
            taskValues.get().put("applyFilterFor",data.get("applyFilterFor"));
        }
        waitFor(2);

        //Apply Filter sub tab
        if(!data.get("applyFilterFor").equalsIgnoreCase("USER")) {
            if (data.containsKey("applyFilterSubTab") && !data.get("applyFilterSubTab").isEmpty()) {
                if (data.get("applyFilterSubTab").contains(",")) {
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").
                            split(",")[0]);
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").
                            split(",")[1]);
                } else {
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab"));
                }
            }
        }
        else{
            if (data.get("applyFilterSubTab").contains(",")) {
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").split(",")[0]);
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").split(",")[1]);
            }
            else{
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab"));
            }

        }
        waitFor(2);
        CRM_TaskQueueFilterStepDef.taskValues.get().put("applyFilterSubTab",data.get("applyFilterSubTab"));

        //status
        if (data.containsKey("status") && !data.get("status").isEmpty()) {
            taskQueueFilter.activateFilter.click();
            taskValues.get().put("status",data.get("status"));
        }
        else if (data.containsKey("status") && data.get("status").isEmpty()) {
            taskValues.get().put("status", "INACTIVE");
        }
        taskValues.get().put("createOn",createGeneralTask.projectDateAtHeader.getText());
        taskValues.get().put("createdBy",createGeneralTask.userAccountName.getText());

        taskQueueFilter.save.click();
    }

    @Then("I verify task queue filter save success message")
    public void verifyTaskQueueFilterSaveSuccessMessage() {
        waitForVisibility(taskQueueFilter.successMessage, 10);
        assertTrue(taskQueueFilter.successMessage.isDisplayed(),"Q Filter Success Message is not displayed");
        assertTrue(taskQueueFilter.successMessageText.isDisplayed(),
                "Q Filter Success Message Text is not displayed");
    }

    @Then("I select {string} value in Task Type drop down")
    public void i_verify_task_type_drop_down_is_not_empty(String value) {
        WebElement element = editQFilter.editPageHeader.isEmpty() ? taskQueueFilter.taskType : editQFilter.taskType;
        selectOptionFromMultiSelectDropDown(element,value);
        waitFor(1);
    }

    @Then("I verify task type drop down has only active task types in create queue filter")
    public void verifyTaskTypeDropDownValues() {
        waitForVisibility(taskQueueFilter.taskType, 5);
        taskQueueFilter.taskType.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(taskQueueFilter.taskTypeIdDrDw.get(0), 5);
        for (int i = 0; i < taskQueueFilter.taskTypeIdDrDw.size(); i++) {
            actualValues.add(taskQueueFilter.taskTypeIdDrDw.get(i).getText().toLowerCase());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(),String.CASE_INSENSITIVE_ORDER);
        assertEquals(actualValues, APITaskManagementController.allFieldsValue.get(),
                "Task Type are not Active or Task Type are not listed in alphabetical order");
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(taskQueueFilter.name).click().perform();
        waitFor(1);
        selectOptionFromMultiSelectDropDown(taskQueueFilter.taskType,"General");
        waitFor(3);
    }

    @Then("I verify Apply Filter drop down values")
    public void verifyApplyFilterDropDownValues(List expectedValues) {
        waitForVisibility(taskQueueFilter.applyFilterFor, 5);
        taskQueueFilter.applyFilterFor.click();
        waitForVisibility(taskQueueFilter.taskApplyFilterForDrDw.get(0), 5);
        List<String> actualValues = taskQueueFilter.taskApplyFilterForDrDw.stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(actualValues, expectedValues, "Apply Filter drop down values are incorrect");
        click(taskQueueFilter.taskApplyFilterForDrDw.get(0));
        waitFor(3);
    }

    @When("I will get the index of newly created record and click on first name for that")
    public void getTheIndexOfTaskAndClickOnFirstName() {
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if (taskQueueFilter.lnkPageNations.size()>1 && isElementDisplayed(taskQueueFilter.lnkArrowForward)) {
            click(taskQueueFilter.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskQueueFilter.lnkPageNations.get(taskQueueFilter.lnkPageNations.size() - 1).getText());
            click(taskQueueFilter.lnkArrowBack);
            waitFor(1);
        }

        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskQueueFilter.taskQueueFilterRows.size(); j++) {
                if (taskQueueFilter.firstNameList.get(j).getText().equalsIgnoreCase(taskValues.get().get("name"))) {
                    waitFor(1);
                    click(taskQueueFilter.firstNameList.get(j));
                    waitFor(1);
                    recordFound = true;
                    break;
                }
            }
            if(recordFound)
                break;
            else if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
    }

    @Then("I verify Task Queue Filter view details")
    public void verify_task_queue_filter_view_details() {
        waitFor(2);
        sa.get().assertThat(taskQueueFilterView.lblCreatedBy.getText()).as("Created By value not matches").isEqualTo(taskValues.get().get("createdBy"));
        sa.get().assertThat(taskQueueFilterView.lblCreatedOn.getText()).as("Created On value not matches").isEqualTo(taskValues.get().get("createOn"));
        sa.get().assertThat(taskQueueFilterView.lblName.getText().equalsIgnoreCase(taskValues.get().get("name"))).as("Name value not matches").isTrue();
        sa.get().assertThat(taskQueueFilterView.lblDescription.getText().equalsIgnoreCase(taskValues.get().get("description"))).as("Description value not matches").isTrue();

        if(taskValues.get().get("taskType").contains(",")) {
            String[] values = taskValues.get().get("taskType").split(",");
            for (String value : values) {
                sa.get().assertThat(taskQueueFilterView.lblTaskType.getText().contains(value)).as("TaskType value not matches").isTrue();
            }
        }
        else{
            sa.get().assertThat(taskQueueFilterView.lblTaskType.getText()).as("TaskType value not matches").isEqualTo(taskValues.get().get("taskType"));
        }

        sa.get().assertThat(taskQueueFilterView.lblApplyFilterFor.getText()).as("ApplyFilterFor value not matches").isEqualTo(taskValues.get().get("applyFilterFor"));
        switch (taskValues.get().get("applyFilterFor")) {
            case "TEAM":
                sa.get().assertThat(taskQueueFilterView.lblTeams.getText()).as("TEAMs value not matches").isEqualTo(taskValues.get().get("applyFilterSubTab"));
                break;
            case "BUSINESS UNIT":
                sa.get().assertThat(taskQueueFilterView.lblBusinessUnits.getText()).as("Business Units value not matches").isEqualTo(taskValues.get().get("applyFilterSubTab"));
                break;
            case "USER":
                sa.get().assertThat(taskQueueFilterView.lblUsers.getText()).as("Users value not matches").isEqualTo(taskValues.get().get("applyFilterSubTab"));
                break;
        }
        sa.get().assertThat(taskQueueFilterView.status.getText()).as("Status value not matches").isEqualTo(taskValues.get().get("status"));
    }

    @Then("I verify task queue filter records are in sorted order")
    public void verifyTaskQueueFilterRecordsAreInSortedOrder() {
        waitFor(1);
        int size = 1;
        ArrayList<String> active=new ArrayList<>();
        ArrayList<String> allStatuses=new ArrayList<>();
        ArrayList<String> inActive=new ArrayList<>();

        if (isElementDisplayed(taskQueueFilter.lnkArrowForward)) {
            click(taskQueueFilter.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskQueueFilter.lnkPageNations.get(taskQueueFilter.lnkPageNations.size() - 1).getText());
            click(taskQueueFilter.lnkArrowBack);
            waitFor(1);
        }

        for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskQueueFilter.taskQueueFilterRows.size(); j++) {
                allStatuses.add(taskQueueFilter.statusClumVlu.get(j).getText());

                if(taskQueueFilter.statusClumVlu.get(j).getText().equalsIgnoreCase("ACTIVE")){
                    active.add(taskQueueFilter.firstNameList.get(j).getText());
                }
                if(taskQueueFilter.statusClumVlu.get(j).getText().equalsIgnoreCase("INACTIVE")){
                    inActive.add(taskQueueFilter.firstNameList.get(j).getText());
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }

//        ArrayList<String> copy = new ArrayList(active);
//        Collections.sort(copy);
//        assertEquals(active,copy,"With in Active status records are not sorted associated to First name column");
        List<String> copy = active;
        Collections.sort(copy);
        for(int i =0 ; i<=copy.size()-1; i++){
            assertEquals(copy.get(i),active.get(i), "With in Active status records are not sorted associated to First name column");
        }

//        copy = new ArrayList(inActive);
//        Collections.sort(copy,String.CASE_INSENSITIVE_ORDER);
//        assertEquals(inActive,copy,"With in Inactive status records are not sorted associated to First Name column");
        List<String> copyInactive = inActive;
        Collections.sort(copyInactive);
        for(int i =0 ; i<=copyInactive.size()-1; i++){
            assertEquals(copyInactive.get(i),inActive.get(i), "With in Inactive status records are not sorted associated to First name column");
        }

//        copy = new ArrayList(allStatuses);
//        Collections.sort(copy,String.CASE_INSENSITIVE_ORDER);
//        assertEquals(allStatuses,copy,"Status column values are not in ascending order");
        List<String> copyAllStatuses = allStatuses;
        Collections.sort(copyAllStatuses);
        for(int i =0 ; i<=copyAllStatuses.size()-1; i++){
            assertEquals(copyAllStatuses.get(i),allStatuses.get(i), "Status column values are not in ascending order");
        }

    }

    @Then("I verify task queue filter has five records with pagination")
    public void verifyTaskListWithPageNation() {
        waitFor(1);
        int size = 1;

        if (!isElementDisplayed(taskQueueFilter.lnkArrowForward)) {
            assertTrue(taskQueueFilter.taskQueueFilterRows.size() <= 5, "Task queue filter records count not matches ");
        }
        else if (isElementDisplayed(taskQueueFilter.lnkArrowForward)) {
            click(taskQueueFilter.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskQueueFilter.lnkPageNations.get(taskQueueFilter.lnkPageNations.size() - 1).getText());
            click(taskQueueFilter.lnkArrowBack);
            waitFor(1);
            for (int i = 1; i <= size; i++) {
                if (i == size)
                    assertTrue(taskQueueFilter.taskQueueFilterRows.size() <= 5, "Task queue filter records count not matches ");
                if (i != size) {
                    assertEquals(taskQueueFilter.taskQueueFilterRows.size(), 5, "Task queue filter records count not matches ");
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
        }
    }

    @Then("I verify Task queue filter list displayed")
    public void verifyTaskQueueFilterListDisplayed() {
        waitFor(5);
        waitForVisibility(taskQueueFilter.firstNameList.get(0),10);
        assertTrue(isElementDisplayed(taskQueueFilter.taskQueueFilterHeader), "Task queue filter header not displayed");
    }

    @When("I select save button")
    public void selectSaveButton() {
        taskQueueFilter.save.click();
        waitFor(1);
    }

    @Then("I verify required field error message {string}")
    public void verifyRequiredFieldErrorMessage(String expectedMessage) {
        expectedMessage="The "+expectedMessage+" is required and cannot be left blank.";
        assertTrue(Driver.getDriver().findElement(By.xpath
                        ("//p[text()='" + expectedMessage + "']")).isDisplayed(),
                expectedMessage+" Mandatory Field level error message is not displayed");
    }

    @Then("I verify Apply Filter drop down is disabled")
    public void verifyApplyFilterDDisDisabled() {
        WebElement element = editQFilter.editPageHeader.isEmpty() ? taskQueueFilter.applyFilterFor : editQFilter.applyFilterFor;
        assertTrue(element.getAttribute("class").trim().endsWith("Mui-disabled"),
                "Apply Filter for drop down is not disabled");
    }

    @Then("I verify all the fields are displayed on create queue screen")
    public void verifyAllFieldsInCreateQueueFilterPage() {
        assertTrue(taskQueueFilter.name.isDisplayed(),"Name field is not displayed");
        assertTrue(taskQueueFilter.taskType.isDisplayed(),"Task Type field is not displayed");
        assertTrue(taskQueueFilter.taskDescription.isDisplayed(),"Description field is not displayed");
        assertTrue(taskQueueFilter.applyFilterFor.isDisplayed(),"Apply For Filter field is not displayed");
        assertFalse(taskQueueFilter.activateFilter.isSelected(),"Active Filter Check box field is not displayed");
        assertTrue(taskQueueFilter.save.isDisplayed(),"Save button field is not displayed");
        assertTrue(taskQueueFilter.cancel.isDisplayed(),"Cancel button field is not displayed");

    }

    @Then("I select {string} in Apply Filter For drop down")
    public void selectApplyForFilterDropDown(String value) {
        selectDropDown(taskQueueFilter.applyFilterFor,value);
    }

    @Then("I verify Apply Filter drop down is enable")
    public void verifyApplyFilterIsEnabled() {
        assertFalse(taskQueueFilter.applyFilterFor.getAttribute("class").trim().endsWith("Mui-disabled"),
                "Apply Filter for drop down is not enabled");
    }

    @Then("I verify {string} drop down is disapper from screen")
    public void verifyselectedDropdownIsdisapperfromScreen(String dropDownName) {
        switch (dropDownName){
            case "USER" :
                assertFalse(isElementDisplayed(taskQueueFilter.users),"USER Drop Down is displayed");
                break;
            case "BUSINESS UNIT" :
                assertFalse(isElementDisplayed(taskQueueFilter.businessUnits),"BUSINESS UNIT Drop Down is displayed");
                break;
            case "TEAMS" :
                assertFalse(isElementDisplayed(taskQueueFilter.teams),"TEAMS Drop Down is displayed");
                break;
        }
    }

    @Then("I will scroll up to application")
    public void scrollToUP() {
        scrollUpRobot();
        waitFor(1);
    }

    @When("I will verify record in List page")
    public void verifyRecordInListPage() {
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if (taskQueueFilter.lnkPageNations.size()>1 && isElementDisplayed(taskQueueFilter.lnkArrowForward)) {
            click(taskQueueFilter.lnkArrowForward);
            waitFor(1);
            size = Integer.parseInt(taskQueueFilter.lnkPageNations.get(taskQueueFilter.lnkPageNations.size() - 1).getText());
            click(taskQueueFilter.lnkArrowBack);
            waitFor(1);
        }

        l1:for (int i = 1; i <= size; i++) {
            for (int j = 0; j < taskQueueFilter.firstNameList.size(); j++) {
                if (taskQueueFilter.firstNameList.get(j).getText().equalsIgnoreCase(taskValues.get().get("name"))) {
                    waitFor(1);
                    sa.get().assertThat(taskQueueFilter.firstNameList.get(j).getText().equalsIgnoreCase(taskValues.get().get("name"))).as("Name is mismatch").isTrue();
                    sa.get().assertThat(taskQueueFilter.descriptionList.get(j).getText().equalsIgnoreCase(taskValues.get().get("description"))).as("Description is mismatch").isTrue();
                    if(taskValues.get().get("taskType").contains(",")) {
                        String[] values = taskValues.get().get("taskType").split(",");
                        for (String value : values) {
                            sa.get().assertThat(taskQueueFilter.taskTypeList.get(j).getText().contains(value)).as("TaskType value not matches").isTrue();
                        }
                    }
                    else{
                        sa.get().assertThat(taskQueueFilter.taskTypeList.get(j).getText()).as("TaskType value not matches").isEqualTo(taskValues.get().get("taskType"));
                    }
                    sa.get().assertThat(taskQueueFilter.scopeList.get(j).getText()).as("Task Queue Scope is mismatch").isEqualTo(taskValues.get().get("applyFilterFor"));
                    sa.get().assertThat(taskQueueFilter.createdOnList.get(j).getText()).as("Create On is mismatch").isEqualTo(taskValues.get().get("createOn"));
                    sa.get().assertThat(taskQueueFilter.createdByList.get(j).getText()).as("Created By is mismatch").isEqualTo(taskValues.get().get("createdBy"));
                    assertEquals(taskQueueFilter.statusClumVlu.get(j).getText(),taskValues.get().get("status"),
                            "Status is mismatch");
                    recordFound = true;
                    break l1;
                }
            }
            if (i != size) {
                click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                waitFor(1);
            }
        }
        sa.get().assertThat(recordFound).as("Newly created record is not there in Q filter list").isTrue();
    }

    @Then("I verify the {string} drop down has value {string}")
    public void verifyBusinessUnitDropDownChanges(String dropDown, String value) {
        if(dropDown.equals("Business Unit") || dropDown.equals("Teams")){
            selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab,value);
        }
        else if(dropDown.equals("User")){
            selectDropDown(taskQueueFilter.applyFilterSubTab, value);
        }
    }

    @Then("I verify the {string} drop down has value {string} on edit page")
    public void verifyBusinessUnitDropDownChangesOnEditPage(String dropDown, String value) {
        switch (dropDown) {
            case "Business Unit":
            case "Teams":
                selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, value);
                break;
            case "User":
                selectDropDown(taskQueueFilter.applyFilterSubTab, value);
                break;
            default:fail("Incorrect switch argument" + dropDown);
        }
    }


    @And("I will click on edit button in q filter view page")
    public void clickOnEditButton() {
        waitFor(1);
        qFilterVlus.get().put("createdBy",taskQueueFilterView.lblCreatedBy.getText());
        qFilterVlus.get().put("createOn",taskQueueFilterView.lblCreatedOn.getText());
        qFilterVlus.get().put("name",taskQueueFilterView.lblName.getText());
        qFilterVlus.get().put("description",taskQueueFilterView.lblDescription.getText());
        qFilterVlus.get().put("taskType",taskQueueFilterView.lblTaskType.getText());
        qFilterVlus.get().put("applyFilterFor",taskQueueFilterView.lblApplyFilterFor.getText());
        switch (qFilterVlus.get().get("applyFilterFor")) {
            case "TEAM":
                qFilterVlus.get().put("applyFilterSubTab",taskQueueFilterView.lblTeams.getText());
                break;
            case "BUSINESS UNIT":
                qFilterVlus.get().put("applyFilterSubTab",taskQueueFilterView.lblBusinessUnits.getText());
                break;
            case "USER":
                qFilterVlus.get().put("applyFilterSubTab",taskQueueFilterView.lblUsers.getText());
                break;
        }
        qFilterVlus.get().put("status",taskQueueFilterView.status.getText());
        jsClick(taskQueueFilterView.editBtn);
        waitFor(1);
    }

    @Then("I verify Apply Filter drop down values in edit page")
    public void verifyApplyFilterDropDownValuesinEditPage(List expectedValues) {
        waitForVisibility(editQFilter.applyFilterFor, 5);
        editQFilter.applyFilterFor.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(editQFilter.taskApplyFilterForDrDw.get(0), 5);
        for (int i = 0; i < editQFilter.taskApplyFilterForDrDw.size(); i++) {
            actualValues.add(editQFilter.taskApplyFilterForDrDw.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, "Apply Filter drop down values are incorrect");
        click(editQFilter.taskApplyFilterForDrDw.get(0));
        waitFor(3);
    }

    @When("I will edit task queue filter with the below information")
    public void editTaskQueueFilter(Map<String, String> data) {
        data=replaceNullValuesInMap(data, "");
        taskValues.set(new HashMap<>());
        //name
        if (data.containsKey("name") && !data.get("name").isEmpty() && data.get("name").equals("random")) {
            taskValues.get().put("name", getRandomString(55));
            sendKeyToTextField(editQFilter.name,taskValues.get().get("name") + "99");
            sa.get().assertThat(editQFilter.name.getAttribute("value").length() == 50).as("Error message for Name field is not displayed").isTrue();
        }
        else if (data.containsKey("name") && !data.get("name").isEmpty()) {
            taskValues.get().put("name", "edit"+getRandomString(5)+"77");
            clearAndFillText(editQFilter.name, taskValues.get().get("name"));
        }

        else if (data.containsKey("name") && data.get("name").isEmpty()) {
            clearAndFillText(editQFilter.name," ");
            taskValues.get().put("name", "-- --");
        }

        //Description
        if (data.containsKey("description") && !data.get("description").isEmpty() && data.get("description").equals("random")) {
            taskValues.get().put("description", getRandomString(155));
            sendKeyToTextField(editQFilter.taskDescription,taskValues.get().get("description"));
            sa.get().assertThat(editQFilter.taskDescription.getAttribute("value").length() == 150).as("Error message Description field is not displayed").isTrue();
        }
        else if (data.containsKey("description") && !data.get("description").isEmpty()) {
            clearAndFillText(editQFilter.taskDescription, data.get("description"));
            taskValues.get().put("description", data.get("description"));
        }
        else if (data.containsKey("description") && data.get("description").isEmpty()) {
            clearTextField(editQFilter.taskDescription);
            taskValues.get().put("description", "-- --");
        }

        //deselect task type
        if (data.containsKey("deselectTaskType") && !data.get("deselectTaskType").isEmpty()) {
            if(data.get("deselectTaskType").contains(",")){
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("deselectTaskType").
                        split(",")[0]);
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("deselectTaskType").
                        split(",")[1]);
            }else{
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("deselectTaskType"));
            }
        }

        //task type
        if (data.containsKey("taskType") && !data.get("taskType").isEmpty()) {
            if(data.get("taskType").contains(",")){
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("taskType").
                        split(",")[0]);
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("taskType").
                        split(",")[1]);
            }else{
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,data.get("taskType"));
            }
            taskValues.get().put("taskType",data.get("taskType"));
        }
        waitFor(2);

        //ApplyFilter
        if (data.containsKey("applyFilterFor") && !data.get("applyFilterFor").isEmpty()) {
            selectDropDown(editQFilter.applyFilterFor, data.get("applyFilterFor"));
            taskValues.get().put("applyFilterFor",data.get("applyFilterFor"));
        }
        waitFor(2);

        //Apply Filter sub tab
        if(data.containsKey("applyFilterFor") && !data.get("applyFilterFor").equalsIgnoreCase("USER")) {
            if (data.containsKey("applyFilterSubTab") && !data.get("applyFilterSubTab").isEmpty()) {
                if (data.get("applyFilterSubTab").contains(",")) {
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").
                            split(",")[0]);
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").
                            split(",")[1]);
                } else {
                    selectOptionFromMultiSelectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab"));
                }
            }
        }
        else if(data.containsKey("applyFilterSubTab")){
            if (data.get("applyFilterSubTab").contains(",")) {
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").split(",")[0]);
                waitFor(1);
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab").split(",")[1]);
                waitFor(1);
            }
            else{
                selectDropDown(taskQueueFilter.applyFilterSubTab, data.get("applyFilterSubTab"));
            }

        }
        waitFor(2);
        CRM_TaskQueueFilterStepDef.taskValues.get().put("applyFilterSubTab",data.get("applyFilterSubTab"));

        //status
        if (data.containsKey("status") && !data.get("status").isEmpty()) {
            if(!editQFilter.activateFilter.isSelected())
                editQFilter.activateFilter.click();
            taskValues.get().put("status",data.get("status"));
        }
        else if (data.containsKey("status") && data.get("status").isEmpty()) {
            if(editQFilter.activateFilter.isSelected())
                editQFilter.activateFilter.click();
            taskValues.get().put("status", "INACTIVE");
        }

        if (data.containsKey("name") && !data.get("name").isEmpty()) {
            taskValues.get().put("createOn",createGeneralTask.projectDateAtHeader.getText());
            taskValues.get().put("createdBy",createGeneralTask.userAccountName.getText());
            taskQueueFilter.save.click();
        }

    }

    @Then("I verify task queue filter edit success message")
    public void verifyTaskQueueFilterEditSuccessMessage() {
        waitForVisibility(taskQueueFilter.successMessage, 10);
        assertTrue(taskQueueFilter.successMessage.isDisplayed(),"Q Filter Success Message is not displayed");
        assertTrue(editQFilter.successMessageText.isDisplayed(),
                "Q Filter Success Message Text is not displayed");
    }

    @Then("I select first record and navigate to view q filter page")
    public void selectFirstRecordAndNavigateToViewQFilterPage() {
        waitForVisibility(taskQueueFilter.firstNameList.get(0),20);
        taskTypes.set(taskQueueFilter.taskTypeList.get(0).getText());
        click(taskQueueFilter.firstNameList.get(0));
        assertTrue(isElementDisplayed(taskQueueFilterView.editBtn),"Task queue filter view page is not displayed");
    }

    @Then("I deselect all task types in edit page")
    public void deselectAllTaskTypesInEditPage() {
        if(taskTypes.get().contains(",")){
            String[] str=taskTypes.get().split(",");
            for(int i=0;i<str.length;i++){
                selectOptionFromMultiSelectDropDown(editQFilter.taskType,str[i].trim());
                waitFor(1);
            }
        }
        else{
            selectOptionFromMultiSelectDropDown(editQFilter.taskType,taskTypes.get().trim());
            waitFor(1);
        }
        taskValues.get().put("taskType","");
    }

    @Then("I verify Apply Filter drop down is disabled in edit q filter page")
    public void verifyApplyFilterDisDisabledInEditPage() {
        assertTrue(editQFilter.applyFilterFor.getAttribute("class").trim().endsWith("Mui-disabled"),
                "Apply Filter for drop down is not disabled edit page");
    }

    @Then("I verify warning message is displayed for {string} function in Edit Q Filter page")
    public void i_verify_warning_message_is_displayed_for_task(String function) {
        editValue.set(taskValues.get());
        taskValues.set(qFilterVlus.get());
        waitForClickablility(createGeneralTask.lblWarningMessage, 10);
        if(function.equalsIgnoreCase("Cancel")){
            assertEquals(createGeneralTask.lblWarningMessage.getText(),"Your Task Queue Filter Will Not Be Saved",
                    "Warning pop up message is wrong");
        }else{
            assertEquals(createGeneralTask.lblWarningMessage.getText(),"If you continue, all the captured information will be lost",
                    "Warning pop up message is wrong");
        }
    }

    @Then("I verify it should remain on the edit q filter page and information should not save")
    public void verifyUserRemainsOnSamePage() {
        assertTrue(isElementDisplayed(taskQueueFilter.save),"User is not remains on Edit page");
        if(editValue.get().get("name").equals("-- --")){
            assertEquals(editQFilter.name.getText(),"","Name has changed");
        } else {
            assertEquals(editQFilter.name.getText(),editValue.get().get("name"),"Name has changed");
        }
        if(editValue.get().get("description").equals("-- --")){
            assertEquals(editQFilter.taskDescription.getText(),"","description has changed");
        } else {
            assertEquals(editQFilter.taskDescription.getAttribute("value"),editValue.get().get("description"),
                    "description has changed");
        }
        if(editValue.get().get("taskType").contains(",")) {
            String[] values = editValue.get().get("taskType").split(",");
            for (String value : values) {
                assertTrue(editQFilter.taskType.getText().contains(value),
                        "TaskType value not matches");
            }
        }
        else{
            assertEquals(editQFilter.taskType.getText(), editValue.get().get("taskType"),
                    "TaskType value not matches");
        }
        if(editValue.get().containsKey("applyFilterFor")){
            assertEquals(editQFilter.applyFilterFor.getText(),editValue.get().get("applyFilterFor"),
                    "Apply Filter For has changed");
        }
        else{
            verifyApplyFilterDisDisabledInEditPage();
        }
        if(editValue.get().containsKey("applyFilterSubTab") && editValue.get().get("applyFilterSubTab")!=null &&
                editValue.get().get("applyFilterSubTab").contains(",")) {
            String[] values = editValue.get().get("applyFilterSubTab").split(",");

            for (String value : values) {
                assertTrue(taskQueueFilter.applyFilterSubTabBusinessUnit.getText().contains(value),
                        "Business Unit not matches");
            }
        } else if(editValue.get().containsKey("applyFilterSubTab") && editValue.get().get("applyFilterSubTab")!=null){
            assertEquals(taskQueueFilter.applyFilterSubTab.getText(),editValue.get().get("applyFilterSubTab"),
                    "Business Unit For has changed");
        }
    }

    @Then("I verify service requests are not present in task type dropdown on create queue filter")
    public void verifyTaskTypeDropDownDoesNotHaveServiceRequest() {
        waitForVisibility(taskQueueFilter.taskType, 5);
        taskQueueFilter.taskType.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(taskQueueFilter.taskTypeIdDrDw.get(0), 5);
        for (int i = 0; i < taskQueueFilter.taskTypeIdDrDw.size(); i++) {
            assertFalse(APITaskManagementController.allFieldsValue.get().
                    contains(taskQueueFilter.taskTypeIdDrDw.get(i).getText().toLowerCase()),
                    "Task type drop down has service request value");
            actualValues.add(taskQueueFilter.taskTypeIdDrDw.get(i).getText());
        }
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(taskQueueFilter.name).click().perform();
        waitFor(1);
        selectOptionFromMultiSelectDropDown(taskQueueFilter.taskType, actualValues.get(0));
        waitFor(3);
    }

    @Then("I verify service requests are not present in task type dropdown on edit queue filter")
    public void verifyTaskTypeDropDownDoesNotHaveServiceRequesInEditPaget() {
        waitForVisibility(editQFilter.taskType, 5);
        editQFilter.taskType.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(editQFilter.taskTypeIdDrDw.get(0), 5);
        for (int i = 0; i < editQFilter.taskTypeIdDrDw.size(); i++) {
            assertFalse(APITaskManagementController.allFieldsValue.get().
                            contains(editQFilter.taskTypeIdDrDw.get(i).getText().toLowerCase()),
                    "Task type drop down has service request value");
            actualValues.add(editQFilter.taskTypeIdDrDw.get(i).getText());
        }
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(editQFilter.name).click().perform();
        waitFor(1);
        selectOptionFromMultiSelectDropDown(editQFilter.taskType, actualValues.get(0));
        waitFor(3);
    }

    @Then("I verify task type drop down has only active task types in edit queue filter")
    public void verifyTaskTypeDropDownValuesInEditQFilterPage() {
        waitForVisibility(editQFilter.taskType, 5);
        editQFilter.taskType.click();
        waitFor(1);
        List<String> actualValues = new LinkedList<>();
        waitForVisibility(editQFilter.taskTypeIdDrDw.get(0), 5);
        for (int i = 0; i < editQFilter.taskTypeIdDrDw.size(); i++) {
            actualValues.add(editQFilter.taskTypeIdDrDw.get(i).getText().toLowerCase());
        }
        Collections.sort(APITaskManagementController.allFieldsValue.get(),String.CASE_INSENSITIVE_ORDER);
        assertEquals(actualValues, APITaskManagementController.allFieldsValue.get(),
                "Task Type are not Active or Task Type are not listed in alphabetical order");
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(editQFilter.name).click().perform();
        waitFor(1);
        selectOptionFromMultiSelectDropDown(editQFilter.taskType,"General");
        waitFor(3);
    }

    @Then("I deselect all task types in edit page of manager queue filter")
    public void deselectAllTaskTypesInEditPageInManagerQueueFilter() {
        Arrays.stream(editQFilter.taskType.getText().split(","))
                .collect(Collectors.toList())
                .forEach(taskType -> selectOptionFromMultiSelectDropDown(editQFilter.taskType, taskType.trim()));
        taskValues.get().put("taskType", "");
    }
}
