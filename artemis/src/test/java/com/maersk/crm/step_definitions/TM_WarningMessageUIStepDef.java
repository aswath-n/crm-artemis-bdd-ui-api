package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public class TM_WarningMessageUIStepDef extends TMUtilities {
    final ThreadLocal<TM_ProjectRoleDetailsStepDef> projRoleStepDef = ThreadLocal.withInitial(TM_ProjectRoleDetailsStepDef::new);
    final ThreadLocal<TM_CreateEditPermissionGroupStepDef> createPermGroupStepDef = ThreadLocal.withInitial(TM_CreateEditPermissionGroupStepDef::new);
    final ThreadLocal<TM_AddNewUserToProjectStepDef> newUserStepDef = ThreadLocal.withInitial(TM_AddNewUserToProjectStepDef::new);
    final ThreadLocal<TM_CreateProjectStepDef> createProjStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_TaskTemplateStepDef> taskTemplateStepDef = ThreadLocal.withInitial(TM_TaskTemplateStepDef::new);
    TMTaskTemplatePage taskTemplate = new TMTaskTemplatePage();
    TMProjectDetailsPage projDetails = new TMProjectDetailsPage();
    final ThreadLocal<TM_UpdateDateHoldayPageStepDef> holidayStepDef = ThreadLocal.withInitial(TM_UpdateDateHoldayPageStepDef::new);

    @When("I navigate to Create {string} of TM project")
    public void i_navigate_to_Create_of_TM_project(String page) {
        switch (page) {
            case "AddRole":
                createPermGroupStepDef.get().i_navigate_to_add_a_new_role_page();
                break;
            case "AddUser":
                createProjStepDef.get().i_click_on_User_List_Menu();
                newUserStepDef.get().i_click_on_add_new_user_button_on_add_new_user_page();
                break;
            case "EditProjectDetails":
                break;
            case "AddGroupPerm":
                createPermGroupStepDef.get().i_navigate_to_Permission_Group_Page();
                createPermGroupStepDef.get().i_click_on_add_new_permission_button();
                break;
            case "AddHolidays":
                projDetails.projConfig.click();
                break;
            case "AddTaskTemplate":
                taskTemplateStepDef.get().navigate_to_task_template();
                taskTemplateStepDef.get().i_click_on_add_new_task_template_button();
                break;
            case "UploadProject":
                break;
        }
    }

    @When("I populate some fields with data on {string} of the project")

    public void i_populate_some_fields_with_data_on_of_the_project(String page) {
        switch (page) {
            case "AddRole":
                //createPermGroupStepDef.get().i_navigate_to_add_a_new_role_page();
                projRoleStepDef.get().i_populate_data_on_project_role_page("xyz", "desc", "today", "+2");
                break;
            case "AddUser":
                newUserStepDef.get().i_populate_some_data_in_the_fields();
                break;
            case "EditProjectDetails":
                swichMetodForEditProjectPage("contract_id", " AAAA");
                break;
            case "AddGroupPerm":
                createPermGroupStepDef.get().i_populate_all_field_with_valid_data_on_Add_Permission_Group_Page();
                break;
            case "AddHolidays":
                holidayStepDef.get().enterHolidayDetails();
                break;
            case "AddTaskTemplate":
                taskTemplateStepDef.get().i_populate_template_name_as_template_description_as("RANDOM", "RANDOM");
                break;
            case "UploadProject":
                break;
        }
    }

    @When("I navigate away by clicking the back arrow without saving changes on this page")
    public void i_navigate_away_by_clicking_the_back_arrow_without_saving_changes_on_this_page() {
        Assert.assertTrue(taskTemplate.taskTemplateBackArrow.isDisplayed(), "Back arrow is not displayed");
        taskTemplate.taskTemplateBackArrow.click();

    }

    @When("I see Warning pop-up message displayed")
    public void i_see_Warning_pop_up_message_displayed() {
        waitForVisibility(taskTemplate.cancelWarningPopUp,5);
        assertTrue(taskTemplate.cancelWarningPopUp.isDisplayed(), "Warning Pop Up is not displayed");
    }

    @Then("I see static warning message on this page")
    public void i_see_static_warning_message_on_this_page() {
        waitForVisibility(taskTemplate.warningMessage,5);
        assertTrue(taskTemplate.warningMessage.isDisplayed(), "Static Warning Message is not displayed");
    }

    @Then("Verify user remain on the Holiday screen")
    public void verify_user_remain_holliday_screen() {
        holidayStepDef.get().verifyHolidayPageDisplayed();
    }
}
