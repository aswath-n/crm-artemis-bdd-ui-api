package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import io.cucumber.java.en.And;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Assert.*;

import com.maersk.crm.utilities.BrowserUtils;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.testng.Assert.*;

public class TM_ProjectRoleDetailsStepDef extends TMUtilities {

    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    TMSearchRolePage tmSearchRolePage = new TMSearchRolePage();
    TMProjectListPage projectListPage = new TMProjectListPage();
    TMProjectDetailsPage projectDetails = new TMProjectDetailsPage();
    TMTaskTypePage tmTaskTypePage = new TMTaskTypePage();

    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    final ThreadLocal<TM_EditProjectInformationStepDef> tmEditProjectInformationStepDef = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);
    public static final ThreadLocal<String> roleForPermGr = ThreadLocal.withInitial(() -> "Role" + TMUtilities.generateRandomCharacters());
    public static final ThreadLocal<String> taskType = ThreadLocal.withInitial(String::new);

    final ThreadLocal<String> permissionType = ThreadLocal.withInitial(String::new);

    public String getPermissionType() {
        int i = randomNumberBetweenTwoNumbers(1, 3);
        String temp = "";
        if (i == 1)
            temp = "write";
        if (i == 2)
            temp = "read";
        if (i == 3)
            temp = "noPermission";
        return temp;
    }

    public boolean verifyNotEditable(WebElement el) {
        try {
            clearAndFillText(el, "editing");
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    //refactorBy: vidya Date:- 1-24-2020
    @Given("I navigate to project role details page")
    public void i_navigate_to_project_role_details_page() throws Exception {
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        tmSearchRolePage.addRoleButton.click();
        Assert.assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }

    @Given("I navigate back to project role details page")
    public void i_navigate_back_to_project_role_details_page() throws Exception {
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        Assert.assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }

    @Then("I see project name, project ID, role name, role description, start date, end date fields displayed")
    public void i_see_project_name_project_ID_role_name_role_description_start_date_end_date_fields_displayed() {
        assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Add Role not displayed on the header");
        assertTrue(tmProjectRolePage.roleDetailsPageTitle.isDisplayed(), "Role details is not displayed as page title");
        assertTrue(tmProjectRolePage.roleName.isDisplayed(), "Role name text field is not displayed");
        assertTrue(tmProjectRolePage.roleDesc.isDisplayed(), "Role description text field is not displayed ");
        assertTrue(tmProjectRolePage.roleStartDate.isDisplayed(), "Role start date is not displayed");
        assertTrue(tmProjectRolePage.roleEndDate.isDisplayed(), "Role end date is not displayed");
        assertTrue(tmProjectRolePage.projectName.getAttribute("value").equalsIgnoreCase(projectDetails.getCurrentProjectName()));
        assertTrue(tmEditProjectInformationStepDef.get().currentProjectId.contains(tmProjectRolePage.projectId.getAttribute("value")));
    }

    @When("I click on Save button on project role page")
    public void i_click_on_Save_button_on_project_role_page() {
        //Uncommented as refactoring --aswath 071719
        //System.out.println("Disabled until further notice");
        /* staticWait(10000);*/
        tmProjectRolePage.saveButton.click();
        //refactoredBy Shruti on 03/25/2020: commenting the assertion as we use same method to click save button to verify mandatory field's error message
        // Assert.assertTrue(tmProjectRolePage.successSnackbar.isDisplayed());
    }

    @When("I enter End Date {string} to Start Date")
    public void i_enter_End_Date_to_Start_Date(String value) {
        clearAndFillText(tmProjectRolePage.roleStartDate, getCurrentDate());
        switch (value) {
            case "prior":
                clearAndFillText(tmProjectRolePage.roleEndDate, getPriorDate(2));
                break;

            case "equal":
                clearAndFillText(tmProjectRolePage.roleEndDate, getCurrentDate());
                break;

        }
    }

    @Then("I see pop-up that role is created")
    public void i_see_pop_up_that_role_is_created() {
        waitForVisibility(tmProjectRolePage.successSnackbar, 10);
        assertTrue(tmProjectRolePage.roleCreatedMessage.isDisplayed(), "Role created pop-up is not displayed");
        waitForVisibility(tmSearchRolePage.addRoleButton, 10);
		/* waitForVisibility(tmProjectRolePage.roleCreatedPopUp, 10);
        assertTrue(tmProjectRolePage.roleCreatedPopUp.isDisplayed(), "Role created pop-up is not displayed");
        tmProjectRolePage.okButton.click();
		*/
    }

    //refactorBy: vidya  Date:1-24-2020
    @When("I populate data on project role page {string},{string},{string},{string}")
    public void i_populate_data_on_project_role_page(String roleName, String roleDesc, String startDate, String endDate) {
        if (roleName.equals("{random}")) roleName = "Role" + getRandomString(5).toLowerCase() + getRandomNumber(2);
        if (roleDesc.equals("This is to test duplicate role")) roleName = roleForPermGr.get();
        clearAndFillText(tmProjectRolePage.roleName, roleName);

        roleForPermGr.set(roleName);

        if (roleDesc.equals("{random}")) roleDesc = "RoleDesc " + getRandomString(7);
        clearAndFillText(tmProjectRolePage.roleDesc, roleDesc);

        if (startDate.equals("today")) startDate = getCurrentDate();
        else if (startDate.contains("-")) {
            startDate = getPriorDate(Integer.parseInt(startDate.replace("-", "")));
        } else if (startDate.contains("+")) {
            startDate = getGreaterDate(Integer.parseInt(startDate.replace("+", "")));
        }
        if (!startDate.equals("") && startDate != null) {
            clearAndFillText(tmProjectRolePage.roleStartDate, startDate);
        }

        if (endDate.equals("today")) endDate = getCurrentDate();
        else if (endDate.contains("-")) {
            endDate = getPriorDate(Integer.parseInt(endDate.replace("-", "")));
        } else if (endDate.contains("+")) {
            endDate = getGreaterDate(Integer.parseInt(endDate.replace("+", "")));
        }
        if (!endDate.equals("") && endDate != null) {
            clearAndFillText(tmProjectRolePage.roleEndDate, endDate);
        }
    }


    @Then("I click on add role button on role list page")
    public void i_click_on_add_role_button_on_role_list_page() {
        tmSearchRolePage.addRoleButton.click();
        waitForVisibility(tmProjectRolePage.addRoleHeader, 10);
    }

    //refactored 03/14 Vinuta - added explicit wait
    @Then("I see error that role already exists")
    public void i_see_error_that_role_already_exists() {
        waitForVisibility(tmProjectRolePage.duplicateRoleError, 5);
        assertTrue(tmProjectRolePage.duplicateRoleError.isDisplayed(), "Role already exists error is not displayed");
    }

    @When("I click on Cancel button on Add Role Page")
    public void i_click_on_Cancel_button_on_Add_Role_Page() {
        tmProjectRolePage.cancelButton.click();
    }

    @When("I click on No and I am navigated back to Add Role page and see all previously entered unsaved data")
    public void i_click_on_No_and_I_am_navigated_back_to_Add_Role_page_and_see_all_previously_entered_unsaved_data() {
        tmProjectRolePage.warningPopUpNoButton.click();
        assertTrue(tmProjectRolePage.roleName.getAttribute("value").equals("Test"),
                "First Name did not remain the same");
        assertTrue(tmProjectRolePage.roleDesc.getAttribute("value").equals("Testing cancel button"),
                "Test description did not remain the same");
        assertTrue(tmProjectRolePage.roleStartDate.getAttribute("value").equals(getCurrentDate()),
                "Start date did not remain the same");
        assertTrue(tmProjectRolePage.roleEndDate.getAttribute("value").equals(getCurrentDate()),
                "End date did not remain the same");
    }


    @Then("I click on Yes and I am navigated back to Role List Page")
    public void i_click_on_Yes_and_I_am_navigated_back_to_Role_List_Page() {
        tmProjectRolePage.warningPopUpYesButton.click();
        assertTrue(tmSearchRolePage.addRoleButton.isDisplayed(), "Add User Button id not displayed");
    }

    @And("I click on roles tab")
    public void navigateToRolesPage() {
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
    }

    @When("I create roles both active and inactive")
    public void i_create_roles_both_active_and_inactive() {
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("ActiveRole1", "This is first active role", "today", "+1");
        i_click_on_Save_button_on_project_role_page();
        //i_see_pop_up_that_role_is_created();
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("ActiveRole2", "This is second active role", "today", "+1");
        i_click_on_Save_button_on_project_role_page();
        //i_see_pop_up_that_role_is_created();
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("InactiveRole1", "This is first inactive role", "today", "today");
        i_click_on_Save_button_on_project_role_page();
        //i_see_pop_up_that_role_is_created();
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("ActiveRole1", "This is first active role", "today", "today");
        i_click_on_Save_button_on_project_role_page();
        //i_see_pop_up_that_role_is_created();
    }

    @Given("I navigate to project role details page of first project")
    public void i_navigate_to_project_role_details_page_of_first_project() {
        tmCreateProjectStepDef.get().i_logged_into_Tenant_Manager_Project_list_page();
        projectListPage.expendProject(projectListPage.projects.get(0));
        assertTrue(projectDetails.viewProjectDashboard.isDisplayed());
        waitFor(3);
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        tmSearchRolePage.addRoleButton.click();
        assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }


    @Then("I see all fields on Select Permission Group section displayed")
    public void i_see_all_fields_on_Select_Permission_Group_section_displayed() {
        assertTrue(tmProjectRolePage.groupPermissionName.isDisplayed(), "Group Permission Name field is not displayed");
        assertTrue(tmProjectRolePage.groupPermissionDescription.isDisplayed(), "Group Permission Discription field is not displayed");
        assertTrue(tmProjectRolePage.permissionStartDate.isDisplayed(), "Group Permission Start Date field is not displayed");
        assertTrue(tmProjectRolePage.permissionEndDate.isDisplayed(), "Group Permission End Date field is not displayed");
    }

    @When("I enter a permission name {string} on select permission group section")
    public void i_enter_a_permission_name_on_select_permission_group_section(String name) {
        clearAndFillText2(tmProjectRolePage.groupPermissionName, name);
    }

    @Then("I see group permission options available for this name {string} displayed")
    public void i_see_group_permission_options_available_for_this_name_displayed(String name) {
        int options = tmProjectRolePage.permissionOptions.size();
        if (options != 0) {
            for (int i = 0; i < options; i++) {
                assertTrue(tmProjectRolePage.permissionOptions.get(i).getText().startsWith(name));
            }
        } else {
            System.out.println("Selected Project does not have any GROUP PERMISSION starting with " + name);
        }


    }

    @Then("I see permission description field on Permission Group section is not editable")
    public void i_see_permission_description_field_on_Permission_Group_section_is_not_editable() {
        clearAndFillText(tmProjectRolePage.groupPermissionDescription, "editing");
        assertTrue(tmProjectRolePage.groupPermissionDescription.getText().equals(""));
    }

    @When("I populate data on select permission group section for {string} group name and {string}, {string}")
    public void i_populate_data_on_select_permission_group_section_for_group_name_and(String name, String
            startDate, String endDate) {
        if (name.equals("random")) {
            name = (getRandomString(6));
        }
        clearAndFillText2(tmProjectRolePage.groupPermissionName, name);
        if (startDate.equals("past")) {
            System.out.println(startDate + " Start " + getPriorDate(4));
            clearAndFillText(tmProjectRolePage.permissionStartDate, getPriorDate(4));
        } else if (startDate.equals("current")) {
            System.out.println(startDate + " Start");
            clearAndFillText(tmProjectRolePage.permissionStartDate, getCurrentDate());
        } else {
            System.out.println(startDate + "");
            clearAndFillText(tmProjectRolePage.permissionStartDate, getGreaterDate(2));
        }
        if (endDate.equals("past")) {
            System.out.println(endDate + " end " + getPriorDate(2));
            clearAndFillText(tmProjectRolePage.permissionEndDate, getPriorDate(2));
        } else if (endDate.equals("future")) {
            System.out.println(endDate);
            clearAndFillText(tmProjectRolePage.permissionEndDate, getGreaterDate(4));
        } else if (endDate.equals("current")) {
            System.out.println(endDate);
            clearAndFillText(tmProjectRolePage.permissionEndDate, getCurrentDate());
        } else if (endDate.equals("sameFuture")) {
            clearAndFillText(tmProjectRolePage.permissionEndDate, getGreaterDate(2));
        }

    }

    @Then("I see incorrect permission group dates error messages {string}")
    public void i_see_incorrect_permission_group_dates_error_messages(String message) {
        if (message.equals("lessOrEqual")) {
            assertTrue(tmProjectRolePage.lessOrEqualEndDateError.isDisplayed(), "End date should not be less or equal than start date is not displayed");
        } else if (message.equals("past")) {
            assertTrue(tmProjectRolePage.lessStartDateError.isDisplayed(), "The start date cannot be in the past message is not displayed");
        }

    }

    @Then("I see warning message to save a role without permission group selected")
    public void i_see_warning_message_to_save_a_role_without_permission_group_selected() {
        waitForVisibility(tmProjectRolePage.warningNoPermGroup, 10);
        assertTrue(tmProjectRolePage.warningNoPermGroup.isDisplayed(), "Warning message is not displayed");
    }

    @Then("I see pop-up that role with selected permission group is created")
    public void i_see_pop_up_that_role_with_selected_permission_group_is_created() {
        waitForVisibility(tmProjectRolePage.roleCreatedPopUp, 10);
        assertTrue(tmProjectRolePage.roleCreatedPopUp.isDisplayed(), "Role created pop-up is not displayed");
    }

    @Then("I add role to project")
    public void i_add_role_to_project() {


        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("ACCASR", "CCSR Role", "today", "");
        i_click_on_Save_button_on_project_role_page();
        i_see_pop_up_that_role_is_created();

        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        waitFor(5);
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("ACNonCSR", "NonCSR Role", "today", "");
        i_click_on_Save_button_on_project_role_page();
        i_see_pop_up_that_role_is_created();

        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
        waitFor(5);
        tmSearchRolePage.addRoleButton.click();
        i_populate_data_on_project_role_page("QA", "QA Role", "today", "");
        i_click_on_Save_button_on_project_role_page();
        i_see_pop_up_that_role_is_created();
    }


    @When("I selected newly created role")
    public void Iselectednewlycreatedrole() {
        for (int i = 0; i < tmSearchRolePage.roleNamesRolelist.size(); i++) {
            if (tmSearchRolePage.roleNamesRolelist.get(i).getText().equalsIgnoreCase(roleForPermGr.get())) {
                waitFor(1);
                tmSearchRolePage.roleNamesRolelist.get(i).click();
            }
        }
    }

    @Then("I update role name with new name")
    public void i_update_role_name_with_new_name() {
        waitFor(2);
        tmProjectRolePage.roleName.sendKeys("new");
        tmProjectRolePage.saveButton.click();

    }

    @And("Verify Permission Details grid")
    public void verifyPermissionDetailsGrid() {
        assertTrue(tmSearchRolePage.viewPermissionRadio.isEnabled());
        assertTrue(tmSearchRolePage.editPermissonRadio.isEnabled());
        assertTrue(tmSearchRolePage.noPermissionRadio.isEnabled());
        assertTrue(tmSearchRolePage.viewPermissionRadio.isEnabled());
        assertTrue(tmSearchRolePage.applyAllCheckBox.isEnabled());
    }

    @And("Verify each page has a caret")
    public void verifyEachPageHasACaret() {
        for (int i = 0; i < tmSearchRolePage.listOfCarets.size(); i++) {
            assertTrue(tmSearchRolePage.listOfCarets.get(i).isDisplayed());
            assertEquals(tmSearchRolePage.listOfCarets.get(i).getAttribute("aria-expanded"), "false", "Caret is expanded");
        }
    }

    @Then("Verify Filter By Page dropdown")
    public void verifyFilterByPageDropDown() {
        for (int i = 0; i < tmSearchRolePage.uploadedProNames.size(); i++) {
            selectDropDown(tmSearchRolePage.filterByPagedrpdwn, tmSearchRolePage.uploadedProNames.get(i).getText());
            waitFor(1);
            tmSearchRolePage.clearFilterbutton.click();
        }
    }

    @Then("Verify selected the caret, then the fields and grids are displayed")
    public void verifySelectedTheCaretThenTheFieldsAndOrGridsAreDisplayed() {
        selectRandomDropDownOption(tmSearchRolePage.filterByPagedrpdwn);
        scrollDownUsingActions(1);
        waitFor(1);
        tmSearchRolePage.listOfCarets.get(0).click();
        if (tmSearchRolePage.listOfCarets.size() > 1) {
            for (int i = 1; i < tmSearchRolePage.listOfCarets.size(); i++) {
                assertTrue(tmSearchRolePage.listOfCarets.get(i).isDisplayed());
            }
        }
    }

    @Then("Verify checked Apply to All checkbox displayed for the selected permissions")
    public void verifyCheckedApplyToAllCheckboxDisplayedForTheSelectedPermissions() {
        waitFor(3);
        tmSearchRolePage.applyAllCheckBox.click();
        waitFor(1);
        for (int i = 0; i < tmSearchRolePage.listApplyAllCheckBox.size(); i++) {
            assertEquals(tmSearchRolePage.listApplyAllCheckBox.get(i).getAttribute("value"), "true", "Apply to All checkbox not checked");
        }
    }

    @Then("Verify after expand the sections Apply to All checkbox displayed for the selected permissions")
    public void verifyAfterExpandTheSectionsApplyToAllCheckboxDisplayedForTheSelectedPermissions() {
        waitFor(3);
        selectDropDown(tmSearchRolePage.filterByPagedrpdwn, "common");
        waitFor(3);
        scrollDownUsingActions(1);
        click(tmSearchRolePage.expandFistCaret);
        waitFor(3);
        tmSearchRolePage.applyAllCheckBox.click();
        waitFor(2);
        for (int i = 0; i < tmSearchRolePage.listApplyAllCheckBox.size(); i++) {
            assertEquals(tmSearchRolePage.listApplyAllCheckBox.get(i).getAttribute("value"), "true", "Apply to All checkbox not checked");
        }
    }

    @Then("Verify select a Permission for a field beneath that section that field display the same permission selected")
    public void verifySelectAPermissionForAFieldBeneathThatSectionThatFieldDisplayTheSamePermissionSelected() {
        waitFor(3);
        tmSearchRolePage.listOfViewPermissions.get(0).click();
        waitFor(1);
        scrollDownUsingActions(1);
        for (int i = 0; i < tmSearchRolePage.listOfViewPermissions.size(); i++) {
            assertTrue(tmSearchRolePage.listOfViewPermissions.get(i).isSelected());
        }

    }

    @Then("I checked View Permission and Apply to All")
    public void iCheckedViewPermissionAndApplyToAll() {
        waitFor(6);
        selectDropDown(tmSearchRolePage.filterByPagedrpdwn, "common");
        waitFor(5);
        tmSearchRolePage.expandFistCaret.click();
        waitFor(3);
        tmSearchRolePage.listOfViewPermissions.get(0).click();
        tmSearchRolePage.applyAllCheckBox.click();
    }

    @And("Verify for expanded caret same permission selected")
    public void verifyForExpandedCaretSamePermissionSelected() {
        waitFor(5);
        for (int i = 0; i < tmSearchRolePage.listOfViewPermissions.size(); i++) {
            assertTrue(tmSearchRolePage.listOfViewPermissions.get(i).isSelected(), "View permission is fail");

        }
    }

    @Then("Verify Clear filter by page button")
    public void verifyClearFilterByPageButton() {
        waitFor(2);
        selectRandomDropDownOption(tmSearchRolePage.filterByPagedrpdwn);
        assertEquals(tmSearchRolePage.uploadedProNames.size(), 1);
        tmSearchRolePage.clearFilterbutton.click();
        waitFor(2);
        assertTrue(tmSearchRolePage.uploadedProNames.size() > 1);
    }

    @Then("I updated newly created role")
    public void thenIUpdatedNewlyCreatedRole() {
        waitFor(3);
        tmSearchRolePage.listOfViewPermissions.get(0).click();
        tmSearchRolePage.listApplyAllCheckBox.get(0).click();
        waitFor(1);
        tmSearchRolePage.permissionSaveButton.click();
    }

    @And("Verify cannot de-select a Permissions")
    public void verifyCannotDeSelectAPermissions() {
        waitFor(1);
        for (int i = 1; i < tmSearchRolePage.listApplyAllCheckBox.size(); i++) {
            waitFor(2);
            scrollUpUsingActions(1);
            tmSearchRolePage.listOfViewPermissions.get(i).click();
            assertTrue(tmSearchRolePage.listOfViewPermissions.get(i).isSelected(), "View permission is fail");
            waitFor(1);
            tmSearchRolePage.listApplyAllCheckBox.get(i).click();
            assertEquals(tmSearchRolePage.listApplyAllCheckBox.get(i).getAttribute("value"), "true", "Apply to All checkbox not checked");
            scrollDownUsingActions(1);
        }
    }


    @And("Verify success message is displayed & user remains on the same screen")
    public void verifySuccessMessageIsDisplayedUserRemainsOnTheSameScreen() {
        waitForVisibility(tmSearchRolePage.successMesAfterSave, 7);
        assertEquals(tmSearchRolePage.successMesAfterSave.getText(), "Your request is being processed in the background. Please check after few minutes");
        assertTrue(tmSearchRolePage.roleDetailsPage.isDisplayed());
    }


    @And("Verify Permission Group icon is not displayed")
    public void verifyPermissionGroupIconIsNotDisplayed() {
        waitFor(3);
        assertFalse(isElementDisplayed(projectDetails.viewPermissionList));
    }

    @And("No Permission is selected by default to all pages")
    public void noPermissionIsSelectedByDefaultToAllPages() {
        for (int i = 1; i < tmSearchRolePage.listOfNoPermission.size(); i++) {
            assertTrue(tmSearchRolePage.listOfNoPermission.get(i).isEnabled(), "NO PERMISSION not checked");
        }
    }

    @Then("Click on Save button and saving Permission")
    public void clickOnSaveButtonAndSavingPermission() {
        waitFor(1);
        tmSearchRolePage.permissionSaveBtnNewSetting.click();
    }

    @And("I checked {string} Permission and Apply to All")
    public void iCheckedPermissionAndApplyToAll(String permission) {
        if (permission.equalsIgnoreCase("Edit")) {
            tmSearchRolePage.listOfEditPermission.get(0).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllCheckBox.get(0).click();
        } else if (permission.equalsIgnoreCase("No")) {
            tmSearchRolePage.listOfNoPermission.get(0).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllCheckBox.get(0).click();
        } else if (permission.equalsIgnoreCase("View")) {
            tmSearchRolePage.listOfViewPermissions.get(0).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllCheckBox.get(0).click();
        }
    }

    @And("I checked {string} Permission and Apply to All Roles")
    public void iCheckedPermissionAndApplyToAllRoles(String permission) {
        if (permission.equalsIgnoreCase("Edit")) {
            tmSearchRolePage.listOfEditPermission.get(1).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllRolesCheckBox.get(1).click();
        } else if (permission.equalsIgnoreCase("No")) {
            tmSearchRolePage.listOfNoPermission.get(1).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllRolesCheckBox.get(1).click();
        } else if (permission.equalsIgnoreCase("View")) {
            tmSearchRolePage.listOfViewPermissions.get(1).click();
            waitFor(1);
            tmSearchRolePage.listApplyAllRolesCheckBox.get(1).click();
        }
    }

    @And("I verify other role has also got saved with same {string} permissions")
    public void iVerifyOtherRoleHasAlsoGotSavedWithSamePermissions(String permission) {
        if (permission.equalsIgnoreCase("Edit")) {
            Assert.assertTrue(tmSearchRolePage.listOfEditPermission.get(1).isSelected());
        } else if (permission.equalsIgnoreCase("No")) {
            Assert.assertTrue(tmSearchRolePage.listOfNoPermission.get(0).isSelected());
        } else if (permission.equalsIgnoreCase("View")) {
            Assert.assertTrue(tmSearchRolePage.listOfViewPermissions.get(0).isSelected());
        }
    }

    @And("I checked {string} Permission for {string} page")
    public void iCheckedPermission(String permission, String page) {
        permissionType.set(getPermissionType());
        tmSearchRolePage.getpermissionforGivenPage(page, permissionType.get()).click();
    }

    @And("I checked {string} Permission for {string} template in page {string}")
    public void iCheckedPermission(String permission, String template, String page) {
        permissionType.set(getPermissionType());
        tmSearchRolePage.getTemplateFomPage(permissionType.get(), template, page).click();
    }

    @And("I select page {string} from Filter by Page dropdown")
    public void iSelectPageFromFilterByPageDropdown(String page) {
        waitFor(3);
        selectDropDown(tmSearchRolePage.filterByPagedrpdwn, page);
        waitFor(3);
    }

    @And("I click on caret sign to select template permission")
    public void iClickOnCarrotSign() {
        waitFor(3);
        tmSearchRolePage.caretSign.click();
        waitFor(3);
    }

    @And("I click on {string} template caret sign to select field or grid permission")
    public void iClickOntemplateCarrotSign(String template) {
        waitFor(3);
        tmSearchRolePage.getCaretSignForTemplate(template).click();
        waitFor(3);
    }

    @And("I click on {string} grid caret sign to select column permission")
    public void iClickOngridCarrotSign(String grid) {
        waitFor(3);
        tmSearchRolePage.getCaretSignForGrid(grid).click();
        waitFor(3);
    }

    @And("I checked {string} Permission for field {string} in {string} template in page {string}")
    public void iCheckedPermissionForFieldInTemplateInPage(String permission, String field, String template, String page) {
        permissionType.set(getPermissionType());
        tmSearchRolePage.getfieldFromTemplateFomPage(permissionType.get(), field, template, page).click();

    }

    @And("I checked {string} Permission for grid {string} in {string} template in page {string}")
    public void iCheckedPermissionForGridInTemplateInPage(String permission, String grid, String template, String page) {
        permissionType.set(getPermissionType());
        tmSearchRolePage.getgridFromTemplateFomPage(permissionType.get(), grid, template, page).click();
    }

    @And("I checked {string} Permission for grid column {string} in grid {string} in {string} template in page {string}")
    public void iCheckedPermissionForGridColumnInTemplateInPage(String permission, String column, String grid, String template, String page) {
        permissionType.set(getPermissionType());
        tmSearchRolePage.getgridColumnFromTemplateFomPage(permissionType.get(), column, grid, template, page).click();
    }

    @And("I verify {string} is set to field {string} in {string} template in page {string}")
    public void iVerifyPermissionForFieldInTemplateInPage(String permission, String field, String template, String page) {
        Assert.assertTrue(tmSearchRolePage.getfieldFromTemplateFomPage(permissionType.get(), field, template, page).isSelected());
    }

    @And("I verify {string} is set to grid {string} in {string} template in page {string}")
    public void iVerifyPermissionForGridInTemplateInPage(String permission, String grid, String template, String page) {
        Assert.assertTrue(tmSearchRolePage.getfieldFromTemplateFomPage(permissionType.get(), grid, template, page).isSelected());
    }

    @And("I verify {string} is set to grid column {string} in grid {string} in {string} template in page {string}")
    public void iVerifyPermissionForGridColumnInTemplateInPage(String permission, String column, String grid, String template, String page) {
        Assert.assertTrue(tmSearchRolePage.getgridColumnFromTemplateFomPage(permissionType.get(), column, grid, template, page).isSelected());
    }

    @Then("I verify {string} is set to {string} template in page {string}")
    public void iVerifyIsSetToPage(String permission, String template, String page) {
        scrollDownUsingActions(1);
        waitFor(6);
        Assert.assertTrue(tmSearchRolePage.getTemplateFomPage(permissionType.get(), template, page).isSelected());
    }

    @Then("I verify {string} is set to {string} page")
    public void iVerifyIsSetToPage(String permission, String page) {
        scrollDownUsingActions(1);
        waitFor(6);
        Assert.assertTrue(tmSearchRolePage.getpermissionforGivenPage(page, permissionType.get()).isSelected());
    }

    @Then("I verify {string} permission is applied to all sections after expand")
    public void iVerifyPermissionIsAppliedToAllSectionsAfterExpand(String permission) {
        if (permission.equalsIgnoreCase("Edit")) {
            scrollDownUsingActions(1);
            waitFor(6);
            click(tmSearchRolePage.expandFistCaret);
            waitFor(5);
            for (int i = 1; i <= tmSearchRolePage.listOfEditPermission.size(); i++) {
                assertTrue(tmSearchRolePage.listOfEditPermission.get(i).isSelected());
            }
        } else if (permission.equalsIgnoreCase("No")) {
            scrollDownUsingActions(1);
            waitFor(6);
            click(tmSearchRolePage.expandFistCaret);
            waitFor(5);
            for (int i = 1; i <= tmSearchRolePage.listOfNoPermission.size(); i++) {
                assertTrue(tmSearchRolePage.listOfNoPermission.get(i).isSelected());
            }
        } else if (permission.equalsIgnoreCase("View")) {
            scrollDownUsingActions(1);
            waitFor(6);
            click(tmSearchRolePage.expandFistCaret);
            waitFor(5);
            for (int i = 1; i <= tmSearchRolePage.listOfViewPermissions.size(); i++) {
                assertTrue(tmSearchRolePage.listOfViewPermissions.get(i).isSelected());
            }
        }
    }

    @And("Verify cannot de-select a {string} Permission")
    public void verifyCannotDeSelectAPermission(String permission) {
        if (permission.equalsIgnoreCase("Edit")) {
            tmSearchRolePage.listOfEditPermission.get(0).click();
            waitFor(1);
            assertTrue(tmSearchRolePage.listOfEditPermission.get(0).isSelected());
        } else if (permission.equalsIgnoreCase("No")) {
            tmSearchRolePage.listOfNoPermission.get(0).click();
            waitFor(1);
            assertTrue(tmSearchRolePage.listOfNoPermission.get(0).isSelected());
        } else if (permission.equalsIgnoreCase("View")) {
            tmSearchRolePage.listOfViewPermissions.get(0).click();
            waitFor(1);
            assertTrue(tmSearchRolePage.listOfViewPermissions.get(0).isSelected());
        }
    }


    @Then("Verify inbound-correspondence option is displayed in result table")
    public void verifyInboundCorrespondenceIsDisplayed() {
        waitFor(1);
        Assert.assertTrue(tmSearchRolePage.firstResultName.getText().trim().equalsIgnoreCase("inbound-correspondence"));
        tmSearchRolePage.listOfCarets.get(0).click();
        if (tmSearchRolePage.listOfCarets.size() > 1) {
            for (int i = 1; i < tmSearchRolePage.listOfCarets.size(); i++) {
                assertTrue(tmSearchRolePage.listOfCarets.get(i).isDisplayed());
            }
        }
    }
}

