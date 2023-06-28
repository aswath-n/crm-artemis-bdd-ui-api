package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;

public class TM_EditBusinessUnitStepDef extends BrowserUtils {

    TMAddBusinessUnitPage businessUnitPage = new TMAddBusinessUnitPage();
    TMEditBusinessUnitPage businessUnitEditPage = new TMEditBusinessUnitPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMTaskTypePage tmTaskTypePage = new TMTaskTypePage();

    final ThreadLocal<TM_AddBusinessUnitStepDef> AddBUStepDef = ThreadLocal.withInitial(TM_AddBusinessUnitStepDef::new);
    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    final ThreadLocal<TM_EditProjectInformationStepDef> tmEditProjectInformationStepDef = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);


    public static final ThreadLocal<String> update_businessUnitValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> update_startDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> update_endDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> update_descriptionValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> Status = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> Status1 = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> Status_BUNAME = ThreadLocal.withInitial(String::new);
//    boolean flag = true;
//    public static boolean flag3 = false;


    /*This method to Login to tenant manager and set the project context
      Author -Paramita
    */
    @Given("I have logged into Tenant Manager and set the project context {string} value {string}")
    public void BusinessUnitDetailsPage(String field, String value) {
        tmCreateProjectStepDef.get().i_logged_into_Tenant_Manager_Project_list_page();
        tmSearchProjectStepDefs.get().i_search_for_a_project_by_value(field, value);
        tmEditProjectInformationStepDef.get().i_expand_a_random_project_to_view_the_details();
    }

    /*This method to navigate to Business Unit Details Page
        Author -Paramita
    */

    @And("I navigate to business unit list page")
    public void navigateToBusinessUnitListPage() {
        Assert.assertTrue(tmProjectDetailsPage.businessUnitIcon.isDisplayed(), "Business unit icon is  not displayed");
        tmProjectDetailsPage.businessUnitIcon.click();


    }

    /*This method to verify Business Unit record exists in FUTURE status in List Page
        Author -Paramita
    */

    @When("I select {string} BU record or populate new BU record with {string},{string},{string},{string} if record not exists")
    public void verify_BU_record_status(String status, String businessUnitName, String desc, String startDate, String endDate) {
        Status.set(status);
        List<WebElement> ele = Driver.getDriver().findElements(By.xpath("//td[text()= '" + Status.get() + "']"));
        boolean flag = businessUnitEditPage.statusClumVlu.size() > 0;
        if (!flag || ele.isEmpty()) {
            AddBUStepDef.get().clickOnAddButton();
            AddBUStepDef.get().i_populate_data_on_project_role_page(businessUnitName, desc, startDate, endDate);
            AddBUStepDef.get().clickOnSaveButton();
            flag = true;
        }
        if (flag) {
            for (int t = 0; t <= businessUnitEditPage.businessUnitNameClumVlu.size(); t++) {
                if (businessUnitEditPage.statusClumVlu.get(t).getText().equalsIgnoreCase(Status.get())) {
                    update_businessUnitValue.set(businessUnitEditPage.businessUnitNameClumVlu.get(t).getText());
                    businessUnitEditPage.businessUnitNameClumVlu.get(t).click();
                    break;
                }
            }
        }

    }


    /*This method to verify BU Name and Start Date fields in read only mode when BU record in ACTIVE / INACTIVE state
        Author -Paramita
    */
    @Then("I verify BU Name and Start Date fields are in read only mode")
    public void verify_BUName_and_StartDate_fields_in_read_only_mode() {
        waitFor(2);
        Assert.assertTrue(businessUnitEditPage.BUName.isDisplayed(), "Business Unit Name field is not displayed");
        Assert.assertTrue(businessUnitEditPage.BUStartDate.isDisplayed(), "Start Date field is not displayed");
    }

    /*This method to click Edit button in Edit Business Unit screen
        Author -Paramita
    */
    @And("I click on Edit button")
    public void click_on_edit_button() {
        scrollUpUsingActions(3);

        waitFor(3);
        scrollToElement(businessUnitEditPage.businessUnitEditIcon);
        scrollToTop();
        jsClick(businessUnitEditPage.businessUnitEditIcon);
    }

    /*This method is to Edit Active/InActive BU record fields in Edit Business Unit screen
      Author -Paramita
     */
    @And("I edit {string},{string} field")
    public void edit_Active_BU_field(String updateDesc, String updateEndDate) {

        update_descriptionValue.set("");
        if (updateDesc.equals("{random}")) {
            update_descriptionValue.set(getRandomString(7));
            clearAndFillText(businessUnitPage.businessUnitDescription, update_descriptionValue.get());
        }

        update_endDateValue.set(updateEndDate);
        if (updateEndDate.equals("today")) update_endDateValue.set(getCurrentDate());
        else if (updateEndDate.contains("-")) {
            update_endDateValue.set(getPriorDate(Integer.parseInt(updateEndDate.replace("-", ""))));
        } else if (updateEndDate.contains("+")) {
            update_endDateValue.set(getGreaterDate(Integer.parseInt(updateEndDate.replace("+", ""))));
        }
        if (!update_endDateValue.get().equals("") && update_endDateValue.get() != null) {
            clearAndFillText(businessUnitPage.endDate, update_endDateValue.get());
        }
    }

    /*This method is to Edit Future BU record fields in Edit Business Unit screen
      Author -Paramita
     */
    @And("I edit {string},{string},{string},{string} fields")
    public void edit_Future_BU_field(String updateBusinessUnitName, String updateDesc, String updateStartDate, String updateEndDate) {
        if (updateBusinessUnitName.equals("{random}")) {
            update_businessUnitValue.set(getRandomString(8).concat("updated"));
            if (Character.isLowerCase(update_businessUnitValue.get().charAt(0))) {
                update_businessUnitValue.set(Character.toUpperCase(update_businessUnitValue.get().charAt(0)) +
                        update_businessUnitValue.get().substring(1));
            }
            clearAndFillText(businessUnitPage.businessUnitName, update_businessUnitValue.get());
        } else {
            clearAndFillText(businessUnitPage.businessUnitName, update_businessUnitValue.get());
        }

        update_descriptionValue.set("");
        if (updateDesc.equals("{random}")) {
            update_descriptionValue.set(getRandomString(7));
            clearAndFillText(businessUnitPage.businessUnitDescription, update_descriptionValue.get());
        }

        update_startDateValue.set(updateStartDate);
        if (updateStartDate.equals("today")) update_startDateValue.set(getCurrentDate());
        else if (updateStartDate.contains("-")) {
            update_startDateValue.set(getPriorDate(Integer.parseInt(updateStartDate.replace("-", ""))));
        } else if (updateStartDate.contains("+")) {
            update_startDateValue.set(getGreaterDate(Integer.parseInt(updateStartDate.replace("+", ""))));
        }
        if (!update_startDateValue.get().equals("") && update_startDateValue.get() != null) {
            clearAndFillText(businessUnitPage.startDate, update_startDateValue.get());
        }

        update_endDateValue.set(updateEndDate);
        if (updateEndDate.equals("today")) update_endDateValue.set(getCurrentDate());
        else if (updateEndDate.contains("-")) {
            update_endDateValue.set(getPriorDate(Integer.parseInt(updateEndDate.replace("-", ""))));
        } else if (updateEndDate.contains("+")) {
            update_endDateValue.set(getGreaterDate(Integer.parseInt(updateEndDate.replace("+", ""))));
        }
        if (!update_endDateValue.get().equals("") && update_endDateValue.get() != null) {
            clearAndFillText(businessUnitPage.endDate, update_endDateValue.get());
        }
    }

    /*This method is to associate / deassociate Task type
     Author -Paramita
    */
    @And("I associate one more TaskType or de-associate Tasktype field")
    public void update_associate_TaskType_field() {
        waitForVisibility(businessUnitPage.taskTypeDropDown, 7);
        click(businessUnitPage.taskTypeDropDown);
        /*System.out.println(Driver.getDriver().findElement(By.xpath("//*[text()='General']")).getTagName());
        System.out.println(Driver.getDriver().findElement(By.xpath("//*[text()='General']/..")).getTagName());
        System.out.println(Driver.getDriver().findElement(By.xpath("//*[text()='General']/..")).getAttribute("class"));
        System.out.println(Driver.getDriver().findElements(By.xpath("//ul[@class='MuiAutocomplete-listbox']")).size());*/

        if (businessUnitEditPage.taskTypeDropDownValue.size() > 0) {
            jsClick(businessUnitEditPage.taskTypeDropDownValue.get(0));
            click(businessUnitPage.taskTypeDropDownValueDeleteIcon.get(0));
            AddBUStepDef.get().clickOnSaveButton();
        } else {
            jsClick(businessUnitPage.taskTypeDropDown);
            AddBUStepDef.get().clickOnSaveButton();
        }


    }


    /*This method is to update BU record on click Save button and navigate to BU list page on successful updation
        Author -Paramita
     */
    @Then("I see BU record get updated succesfully and navigate to Businesss Unit list page")
    public void BU_Update_record_successfull_mesg() {

        waitForVisibility(businessUnitEditPage.successMessage, 10);
        Assert.assertTrue(businessUnitEditPage.successMessage.isDisplayed(), "Success Message is not displayed");
        Assert.assertTrue(businessUnitEditPage.successMessageTxt.isDisplayed(), "Success Message text is not displayed");
        Assert.assertTrue(businessUnitEditPage.businessUnitListPage.isDisplayed(), "It is not navigating to Business Unit List page");
        Assert.assertTrue(businessUnitEditPage.addButton.isDisplayed(), "It is not showing Add button in Business Unit List Page");
    }


    /* This method is to verify  END date & Description as an optional field in Edit BU screen
        Author -Paramita
     */
    @And("I clear {string},{string} field")
    public void clear_endDate_Desc_Tasktype_field(String updateEnddate, String updatedesc) {
        if (updateEnddate.equals("")) {
            clearAndFillText(businessUnitPage.endDate, "");
        }

        update_descriptionValue.set("");
        if (updatedesc.equals("")) {
            clearAndFillText(businessUnitPage.businessUnitDescription, " ");

        }
        AddBUStepDef.get().clickOnSaveButton();

    }


    /*This method is to verify Description field allow only 150 characters
        Author -Paramita
    */
    @Then("I verify description field accepts 150 alphanumeric character and spaces are allowed")
    public void description_field_accepts_150_alphanumeric_caharacter() {
        businessUnitPage.businessUnitDescription.sendKeys(getRandomString(175));
        int maxlength = businessUnitPage.businessUnitDescription.getAttribute("value").length();
        Assert.assertTrue(maxlength == 150, "Description field length is not 150 characters");
    }

    /*This method is to edit INACTIVE record  if it exists in the BU list
    Author -Paramita
*/
    @Then("I edit {string},{string},TaskType field of {string} BU record")
    public void
    checkIthasBUWithInactiveStatus(String updateDesc, String updateEndDate, String status) {
        Status1.set(status);
        List<WebElement> inactiveEle = Driver.getDriver().findElements(By.xpath("//td[text()= '" + Status1.get() + "']"));
        if (!inactiveEle.isEmpty()) {
            for (int t = 0; t <= businessUnitEditPage.businessUnitNameClumVlu.size(); t++) {
                if (businessUnitEditPage.statusClumVlu.get(t).getText().equalsIgnoreCase(Status1.get())) {
                    businessUnitEditPage.businessUnitNameClumVlu.get(t).click();
                    click_on_edit_button();
                    edit_Active_BU_field(updateDesc, updateEndDate);
                    update_associate_TaskType_field();
                    BU_Update_record_successfull_mesg();
                    break;
                }
            }
        }
    }

    @And("I associate one more TaskType and click on Save")
    public void iAssociateOneMoreTaskTypeAndClickOnSave() {
        waitFor(1);
        selectRandomDropDownOption(businessUnitPage.selectTaskType);
        businessUnitPage.saveButton.click();
    }

    @And("I disassociate one more TaskType and click on Save")
    public void iDisassociateOneMoreTaskTypeAndClickOnSave() {
        waitFor(1);
        businessUnitPage.frstTask.click();
        businessUnitPage.saveButton.click();
    }

    @Then("I associate one more TaskType and disassociate one more TaskType")
    public void iAssociateOneMoreTaskTypeAndDisassociateOneMoreTaskType() {
        waitFor(1);
        selectRandomDropDownOption(businessUnitPage.selectTaskType);
        waitFor(1);
        businessUnitPage.frstTask.click();
        businessUnitPage.saveButton.click();
    }
}
