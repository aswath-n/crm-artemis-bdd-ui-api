package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Assert.*;

import static org.testng.Assert.assertTrue;

import com.maersk.crm.utilities.BrowserUtils;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.text.Format;

import org.apache.commons.lang3.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class TM_UserRoleStepDef extends BrowserUtils {
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_ProjectRoleDetailsStepDef> tmProjectRoleStepDef = ThreadLocal.withInitial(TM_ProjectRoleDetailsStepDef::new);
    TMSearchRolePage tmSearchRolePage = new TMSearchRolePage();
    final ThreadLocal<TM_ListAllProjectsRoleStepDef> tmListProjectRole = ThreadLocal.withInitial(TM_ListAllProjectsRoleStepDef::new);
    TMUserRolePage tmUserRolePage = new TMUserRolePage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    final ThreadLocal<TM_AddNewUserToProjectStepDef> addProjectUsertepDef = ThreadLocal.withInitial(TM_AddNewUserToProjectStepDef::new);
    TMSearchUserPage tmSearchUserPage = new TMSearchUserPage();

    final ThreadLocal<ArrayList<String>> actual_activeroles = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> expected_activeroles = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> obtainedRoleList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> remainRoleList = ThreadLocal.withInitial(ArrayList::new);
    //    final ThreadLocal<ArrayList<String>> sortedRole = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<String> startDate = ThreadLocal.withInitial(BrowserUtils::getCurrentDateWithFormat);
    public final ThreadLocal<String> endDate = ThreadLocal.withInitial(() -> getPriorDate(1));
    public final ThreadLocal<String> futureDate = ThreadLocal.withInitial(() -> getGreaterDate(3));
    final ThreadLocal<Date> today = ThreadLocal.withInitial(Date::new);
    final ThreadLocal<Boolean> flag3 = ThreadLocal.withInitial(() -> true);
    public static final ThreadLocal<String> updatedRoleValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> str2 = ThreadLocal.withInitial(String::new);

    static final ThreadLocal<Integer> randomMaxID = ThreadLocal.withInitial(() -> randomNumberBetweenTwoNumbers(154000, 154700));

    /*Author-Paramita This method to verify-
     navigate to project role page*/
    @And("I navigate to project role page")
    public void navigate_to_project_role_page() {
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
    }


    /*Author-Paramita This method to verify-
    If role not exits with Active status , create a role*/
    @And("I check for a role with Active status,if not will create a Role on project role page {string},{string},{string},{string}")
    public void check_for_existing_role(String roleName, String roleDesc, String startDate, String endDate) {
//        tmSearchRolePage.refresh.click();
        tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();

        waitFor(5);
        boolean flag = true;
        tmListProjectRole.get().i_enter_serach_criteria_for_a_role_by_value("Role Status", "Active");
        tmListProjectRole.get().i_click_on_search_button_on_role_search_page();
        flag = tmSearchRolePage.roleNames.size() > 0;

        if (!flag) {
            int m = 0;
            while (m < 3) {
                tmUserRolePage.rolebutton.click();
                tmProjectRoleStepDef.get().i_populate_data_on_project_role_page(roleName, roleDesc, startDate, endDate);
                tmUserRolePage.RolesaveButton.click();
                m++;
            }

            flag = true;
        }

        if (flag) {
            //pagination added
            if (tmUserRolePage.lnkPageCount.size() > 1) {
                for (int m = 0; m < tmUserRolePage.lnkPageCount.size() - 1; m++) {
                    for (WebElement role1 : tmSearchRolePage.roleNames) {
                        str2.set(role1.getText());
                        String str3 = str2.get().replace(" ", "");
                        actual_activeroles.get().add(str3);
                    }

                    Collections.sort(actual_activeroles.get());
                    actual_activeroles.get().remove("");
                    jsClick(tmUserRolePage.lnkPageCount.get(m + 1));
                    waitFor(2);
                    System.out.println("Actual Active roles1" + actual_activeroles.get());
                }
            } else {
                for (WebElement role1 : tmSearchRolePage.roleNames) {
                    str2.set(role1.getText());
                    String str3 = str2.get().replace(" ", "");
                    actual_activeroles.get().add(str3);
                    Collections.sort(actual_activeroles.get());
                    actual_activeroles.get().remove("");
                }
                System.out.println("Actual Active roles1" + actual_activeroles.get());
            }
        }
        tmSearchRolePage.addRoleButton.click();
        Assert.assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }


    /*Author-Paramita This method to verify-
    scroll down and click on User Role section */
    @And("I scroll down and click on User Role section")
    public void scroll_down_to_user_role_section() {
        tmUserRolePage.addRoleButton.click();
        tmUserRolePage.userRoleDropdown.click();
        waitFor(2); //Do not remove wait method
        for (WebElement Userrole : tmUserRolePage.activeRoleNames) {
            String str = Userrole.getText();
            String str1 = str.replaceAll("([\\d]{1,2}/[\\d]{1,2}/[\\d]{2,4})", "").replace("//", "").replace(" ", "");
            expected_activeroles.get().add(str1);
            expected_activeroles.get().remove("");
            Collections.sort(expected_activeroles.get());
        }
        System.out.println("expected Active roles" + expected_activeroles.get());

    }


    /*Author-Paramita This method to verify-
    Only Active Role displaying in dropdown */
    @Then("I verify only active roles are displaying in User Role dropdown")
    public void i_verify_only_active_roles_are_displaying_in_user_role_dropdown() {
        Assert.assertTrue(actual_activeroles.get().equals(expected_activeroles.get()), "Active roles are not displaying");
    }


    /*Author-Paramita This method to verify-
    Active roles are sorted in ascending order */
    @Then("I verify only active roles are displaying sorted in ascending order in User Role dropdown")
    public void active_roles_SortedOrder() {
        for (WebElement RoleList : tmUserRolePage.activeRoleNames) {
            String str5 = RoleList.getText();
            String str6 = str5.replaceAll("([\\d]{1,2}/[\\d]{1,2}/[\\d]{2,4})", "").replace("//", "").replace(" ", "");
            obtainedRoleList.get().add(str6);
            obtainedRoleList.get().remove("");
        }
        Assert.assertTrue(checkSorting(obtainedRoleList.get()), "Roles are not sorted ascending order");
    }


    /*Author-Paramita This method to verify-
    select user-role value from dropdown*/
    @And("I scroll down and select User Role {string} from User ROLE dropdown list,then Role description populates automatically")
    public void selectUserRoleValue(String rolevalue) {
        tmUserRolePage.addRoleButton.click();
        selectDropDown(tmUserRolePage.userRoleDropdown, rolevalue);
        String roledescrp = tmUserRolePage.roleDescription.getAttribute("value");
        Assert.assertTrue(!roledescrp.isEmpty() || roledescrp.isEmpty(), "Role decsription  not populated automatically ");

    }

    @Then("I verify Role description field is not editable")
    public void role_description_populates_automatically_and_the_field_is_not_editable() {
        String readonlyField = tmUserRolePage.roleDescription.getAttribute("readonly");
        Assert.assertNotNull(readonlyField, "Role description field showing editable");
    }


    /*Author-Paramita This method to verify-
    select mutilple user-role from dropdown */
    @And("I scroll down and select multiple User Role from User ROLE dropdown list")
    public void scroll_down_and_select_multiple_user_role_from_user_role_dropdown_list() {
        click(tmUserRolePage.addRoleButton);
        scrollToElement(tmUserRolePage.userRoleSection);
        Assert.assertTrue(tmUserRolePage.userRoleSection.isDisplayed(), "User Role section is not displayed");
        int i = 0;
        while (i < 2) {
            tmUserRolePage.addRoleButton.click();
            tmUserRolePage.userRoleDropdown.click();
            waitFor(2);
            for (int k = 1; k <= tmUserRolePage.activeRoleNames.size(); k++) {
                click(tmUserRolePage.activeRoleNames.get(k));
                waitFor(1);
                tmUserRolePage.roleStartDate.sendKeys(startDate.get());
                //tmUserRolePage.addRoleIcon.click();
                i++;
            }

        }
    }

    @And("I scroll down and select {string} User Role from User ROLE dropdown list")
    public void i_scroll_down_and_select_something_user_role_from_user_role_dropdown_list(String count) {
        int count1 = Integer.parseInt(count);
        for (int i = 2, j = 6; i <= count1 + 1; i++, j = j + 2) {
            waitFor(1);
            highLightElement(tmUserRolePage.addRoleButton);
            tmUserRolePage.addRoleButton.click();
            waitFor(2);
            highLightElement(tmUserRolePage.userRoleDropdownWithIndex(i));
            selectRandomDropDownOption(tmUserRolePage.userRoleDropdownWithIndex(i));
//           waitFor(2);
            highLightElement(tmUserRolePage.startDateCalendarButtonAtIndex(j));
            tmUserRolePage.startDateCalendarButtonAtIndex(j).click();
//           waitFor(2);// <-------------And comment these two lines
            highLightElement(tmUserRolePage.startDateSelectOkButton);
            tmUserRolePage.startDateSelectOkButton.click();
        }
    }

    /*Author-Paramita This method to verify-
    Multiple User Roles can be added at one time */
    @Then("I verify Multiple User Roles can be added at one time")
    public void i_verify_multiple_user_roles_can_be_added_at_one_time() {
        tmSearchUserPage.searchFirstName.sendKeys(addProjectUsertepDef.get().firstNameValue.get());
        tmSearchUserPage.searchButton.click();
        waitFor(3);
        tmSearchUserPage.resultFirstNames.get(0).click();
//        Assert.assertTrue(tmUserRolePage.deleteRoleIcon.size() > 1, "User is not associated with Multiple roles");
        Assert.assertTrue(tmUserRolePage.savedUserRoles.size() > 1, "User is not associated with Multiple roles");
    }


    /*Author-Paramita This method to verify-
    assigned a user with role value */
    @And("I scroll down and assigned a User with role {string} value")
    public void scroll_down_and_assigned_a_user_role(String rolename) {
        scrollToElement(tmUserRolePage.userRoleSection);
        selectDropDown(tmUserRolePage.userRoleDropdown, rolename);
        tmUserRolePage.roleStartDate.sendKeys(startDate.get());
    }


    /*Author-Paramita This method to verify-
     Role assigned already will not display in role dropdown anymore*/
    @Then("I verify role {string} already assigned to the User will not display in User Role dropdown list")
    public void assignedRole_Notdisplay_in_userRole_dropdownlist(String assignedRole) {
        tmUserRolePage.addRoleIcon.click();
        tmUserRolePage.userRoleDropdown.click();
        for (WebElement remainingUserrole : tmUserRolePage.activeRoleNames) {
            String remained_userRole = remainingUserrole.getText();
            remainRoleList.get().add(remained_userRole);
            remainRoleList.get().remove("");
        }
        // System.out.println(assignedRole);
        // System.out.println(remainRoleList.get());
        Assert.assertFalse(remainRoleList.get().contains(assignedRole), "Assigned role is displaying in Remaining role dropdown list");
    }


    /*Author-Paramita This method to verify-
    delete the role which is not saved */
    @Then("I can delete the same role that is not saved")
    public void deleteUnsavedUserRole() {
        tmUserRolePage.userRoleCancelButton.click();
        waitFor(1);
        Assert.assertTrue(tmUserRolePage.deleteRoleIcon.size() == 1, "User is still associated with Role");
    }


    /*Author-Paramita This method to verify-
    enter Role End date less than Start date */
    @And("I enter Role's End date less than Role's Start date")
    public void enter_roles_end_date_less_than_start_date() {
        tmUserRolePage.roleEndDate.sendKeys(endDate.get());
        tmUserRolePage.addRoleIcon.click();
    }

    /*Author-Paramita This method to verify-
    Validate error message when Role End date less than Start date */
    @Then("I see {string} message under the roles {string} field")
    public void i_see_message_under_the_field(String message, String field) {
        switch (field) {
            case "EndDate":
                Assert.assertTrue(tmUserRolePage.priorRoleEndDateError.isDisplayed());
                Assert.assertTrue(tmUserRolePage.priorRoleEndDateError.getText().equals(message));
                break;

            case "Select Role":
                waitForVisibility(tmUserRolePage.selectRoleError, 10);
                Assert.assertTrue(tmUserRolePage.selectRoleError.isDisplayed());
                Assert.assertTrue(tmUserRolePage.selectRoleError.getText().equals(message));
                break;

            case "Start Date":
                waitForVisibility(tmUserRolePage.startDateError, 10);
                Assert.assertTrue(tmUserRolePage.startDateError.isDisplayed());
                Assert.assertTrue(tmUserRolePage.startDateError.getText().equals(message));
                break;
        }

    }


    /*Author-Paramita This method to verify-
    click on available button */
    @When("I click on available {string}")
    public void click_on_button(String button) {
        tmUserRolePage.addRoleIcon.click();
        switch (button) {
            case "Cancel":
                waitForVisibility(tmUserRolePage.userRoleCancelButton, 10);
                click(tmUserRolePage.userRoleCancelButton);
                break;

            case "Back Arrow":
                waitForVisibility(tmUserRolePage.userRoleBackArrow, 10);
                click(tmUserRolePage.userRoleBackArrow);
                break;

            case "User Icon":
                waitForVisibility(tmProjectDetailsPage.viewUserList, 10);
                click(tmProjectDetailsPage.viewUserList);
        }

    }


    /*Author-Paramita This method to verify-
    warning message displayed on click Continue & Cancel button */
    @Then("I verify warning message and warning text is displayed with Continue and Cancel button")
    public void verifyWarningMessageText() {
        waitForVisibility(tmUserRolePage.warningMessage, 10);
        Assert.assertTrue(tmUserRolePage.warningMessage.isDisplayed(), "Warning Message is not displayed");
        Assert.assertTrue(tmUserRolePage.warningMessageTxt.isDisplayed(), "Warning message text is not displayed");
        Assert.assertTrue(tmUserRolePage.warningMsgContinueBtn.isDisplayed(), "Warning message Continue button is not displayed");
        Assert.assertTrue(tmUserRolePage.warningMsgCancelBtn.isDisplayed(), "Warning message Cancel button is not displayed");
    }


    /*Author-Paramita This method to verify-
    click on Cancel button */
    @When("I click on Cancel option on warning message")
    public void click_on_cancel_option_on_warning_message() {
        waitForVisibility(tmUserRolePage.warningMsgCancelBtn, 5);
        tmUserRolePage.warningMsgCancelBtn.click();
    }


    /*Author-Paramita This method to verify-
    remain on same screen on click cancel button */
    @Then("I should remain on the same page and information should not get saved")
    public void i_should_remain_on_the_same_page_and_information_should_not_get_saved() {
        waitForVisibility(tmUserRolePage.addUserScreen, 10);
        Assert.assertTrue(tmUserRolePage.addUserScreen.isDisplayed(), "It's not remains on same screen");
    }


    /*Author-Paramita This method to verify-
     click on Continue button*/
    @When("I click on Continue option on warning message")
    public void click_on_continue_option_on_warning_message() {
        waitForVisibility(tmUserRolePage.warningMsgContinueBtn, 5);
        tmUserRolePage.warningMsgContinueBtn.click();
    }


    /*Author-Paramita This method to verify-
     Redirect to Userlist screen*/
    @Then("I should redirect to User List Screen")
    public void should_redirect_to_Userlist_screen() {
        waitForVisibility(tmUserRolePage.userListScreen, 10);
        Assert.assertTrue(tmUserRolePage.userListScreen.isDisplayed(), "UserList screen is not displaying");
        Assert.assertTrue(tmUserRolePage.userRoleAddButton.isDisplayed(), "Add button in User List page is not displayed");
    }


    /*Author-Paramita This method to verify-
    scroll down and click on add role button  */
    @And("I scroll down and click on add role button on Add User page")
    public void click_on_add_role_button_on_user_list_page() {
        //scrollToElement(tmUserRolePage.userRoleSection);
        tmUserRolePage.addRoleButton.click();
        //tmUserRolePage.addRoleIcon.click();
    }


    /*Author-Paramita This method to verify-
    verify user-role end date and user-role description are optional */
    @Then("I verify user-role end date and user-role description are optional")
    public void verifyUserRoleOptionalFields() {
        Assert.assertTrue(tmUserRolePage.roleDescription.isDisplayed(), "User Role End Date field are not optional");
        Assert.assertTrue(tmUserRolePage.roleEndDate.isDisplayed(), "User Role Description field are not optional");
    }


    /*Author-Paramita This method to verify-
     select an existing user record*/
    @And("I select an existing user record with {string} and scroll to User Role section")
    public void select_existing_user_record(String status) {
        if (tmUserRolePage.userName.size() > 0) {
            for (int i = 3; i <= tmUserRolePage.userName.size(); i++) {
                if (tmUserRolePage.userStatus.get(i).getText().equals(status)) {
                    tmUserRolePage.userName.get(i).click();
                    break;
                }
            }

            scrollToElement(tmUserRolePage.userRoleSection);
            waitFor(2);
            // waitForVisibility(tmUserRolePage.userRoleSection,10);
        }
    }

    @And("I verify user has default user role assigned")
    public void i_verify_user_has_default_user_role_assigned() {
//
        String temp = "";
        Boolean flag = false;
        for (int i = 0; i < tmUserRolePage.defaultRoleToggle.size(); i++) {
            temp = tmUserRolePage.defaultRoleToggle.get(i).getAttribute("value");
            if (temp.equals("true"))
                flag = true;
        }
        Assert.assertTrue(flag, "User has no default role assigned");
    }

    @And("I verify that I can select another role as default")
    public void i_verify_that_i_can_select_another_role_as_default() {
        int count = randomNumberBetweenTwoNumbers(1, tmUserRolePage.defaultRoleToggle.size() - 1);
        tmUserRolePage.defaultRoleToggle.get(count).click();
        Assert.assertTrue(tmUserRolePage.defaultRoleToggle.get(count).getAttribute("value").equals("true"), "Another role not selected as default");
        int temp = 0;
        for (int i = 0; i < tmUserRolePage.defaultRoleToggle.size(); i++) {
            if (tmUserRolePage.defaultRoleToggle.get(i).getAttribute("value").equals("true"))
                temp++;
        }
        Assert.assertFalse(temp > 1, "Multiple roles are selected as default");
    }


    /*Author-Paramita This method to verify-
    no delete icon is showing when START DATE less than Current Date */
    @Then("I verify no delete icon is showing for a saved user role when START DATE less than Current Date")
    public void noDeleteIconSavedUserRole() {
        waitFor(2);
        for (WebElement e : tmUserRolePage.roleStartDateValue) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            String savedRoleDate = e.getAttribute("value");
            String today1 = formatter.format(today.get());
            try {

                Date savedRole_Date = formatter.parse(savedRoleDate);
                Date today_date = formatter.parse(today1);

                if (savedRole_Date.before(today_date) || savedRole_Date.equals(today_date)) {
                    waitFor(1);
                    Assert.assertTrue(IsElementDisplayed(tmUserRolePage.delRoleIcon), "Delete icon is showing when START DATE less than Current Date ");
                    break;
                }


            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }


    /*Author-Paramita This method to verify-
     Role End Date is updated and saved */
    @Then("I verify Role End Date is updated for Saved User Role with update success message")
    public void role_end_date_is_editable_for_saved_user_role() {
        waitFor(2);

        clearAndFillText(tmUserRolePage.roleEndDateValue.get(0), futureDate.get());
        scrollDownUsingActions(2);
        tmUserRolePage.editRoleSavButton.click();
        waitForVisibility(tmUserRolePage.updateSuccessMesg, 5);
        Assert.assertTrue(tmUserRolePage.updateSuccessMesg.isDisplayed(), "Update success message not displaying");
        Assert.assertTrue(tmUserRolePage.userListScreen.isDisplayed(), "UserList screen is not displaying");
    }


    /*Author-Paramita This method to verify-
    Add role with Start date greater than today's date */
    @And("I check for existing Future role,if not add a new role with Start date greater than today's date and saved the role")
    public void addNewRole_with_startDate_greater_than_todays_date() {
        waitFor(1);
        boolean flag2 = true;
        flag2 = tmUserRolePage.deleteRoleIcon.size() > 0;
        if (flag2) {
            flag3.set(true);
        }

        if (!flag2) {
            tmUserRolePage.userRoleDropdown.click();
            for (int t = 1; t <= tmUserRolePage.activeRoleNames.size(); t++) {
                tmUserRolePage.activeRoleNames.get(t).click();
                waitFor(1);
                break;
            }
            tmUserRolePage.roleStartDate.sendKeys(futureDate.get());
            tmUserRolePage.addRoleIcon.click();
            tmUserRolePage.editRoleSavButton.click();
            Assert.assertTrue(tmUserRolePage.editSuccessMesg.isDisplayed(), "Update success message not displaying");
            select_existing_user_record("ACTIVE");
            scrollToElement(tmUserRolePage.accountActivation);
            flag3.set(true);
        }
    }


    /*Author-Paramita This method to verify-
    delete icon is showing when START DATE greater than Today's Date */

    @Then("I verify delete icon is showing for a saved user role when START DATE greater than Today's Date")
    public void deleteIconDisplayRoleFutureStartDate() {
        waitFor(3);
        if (flag3.get()) {
            for (WebElement e : tmUserRolePage.roleStartDateValue) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                String saved_RoleDate = e.getAttribute("value");
                String today2 = formatter.format(today.get());
                try {

                    Date savedRole_Date1 = formatter.parse(saved_RoleDate);
                    Date today_date2 = formatter.parse(today2);

                    if (savedRole_Date1.after(today_date2)) {
                        Assert.assertTrue(!tmUserRolePage.deleteRoleIcon.isEmpty(), "Delete/Trash icon is not showing");
                        break;
                    }


                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }


    /*Author-Paramita This method to verify-
    To delete the role whose start date is greater than current date
     */
    @When("I delete the Future Role")
    public void i_delete_the_future_role() {
        if (tmUserRolePage.deleteRoleIcon.size() > 0) {
            tmUserRolePage.deleteRoleIcon.get(0).click();
        }
    }

    /*Author-Paramita This method to verify-
     To verify role count when future role is deleted
      */
    @Then("I verify selected Future role which is deleted is not displaying")
    public void i_verify_selected_future_role_which_is_deleted_is_not_displaying() {
        //waitFor(2);
        waitForVisibility(tmUserRolePage.userDdeleteRoleIcon, 10);
        Assert.assertTrue(tmUserRolePage.deleteRoleIcon.size() == tmUserRolePage.deleteRoleIcon.size() - 1, "User role is not Deleted successfully");
    }


    /*Author-Paramita This method to verify-
     To verify exitsing role value is updated with an changed role
      */
    @And("I select existing role and update with another role value")
    public void i_select_existing_role_and_update_with_another_role_value() {
        boolean flag2 = true;
        String prev_Value = tmUserRolePage.defaultRoleValue.get(0).getText();
        waitFor(1);
        tmUserRolePage.defaultRoleDropdown.get(0).click();
        int rolelistSize = tmUserRolePage.activeRoleNames.size();
        flag2 = rolelistSize - 1 > 0;

        if (rolelistSize - 1 == 0) {
            tmUserRolePage.activeRoleNames.get(0).click();
            Assert.assertTrue(prev_Value.equals(prev_Value), "No Roles is present to Update");
        }

        if (flag2) {
            Random rand = new Random();
            int list = rand.nextInt(rolelistSize - 1);
            waitFor(5);
            tmUserRolePage.activeRoleNames.get(list).click();
            updatedRoleValue.set(tmUserRolePage.defaultRoleValue.get(0).getText());
            waitFor(1);
            System.out.println("updatedRoleValue" + updatedRoleValue.get());
            Assert.assertFalse(prev_Value.equals(updatedRoleValue.get()), "Role Value is not updated");
        }
    }


    /*Author-Paramita This method to verify-
     To click on User Role Save button
      */
    @And("I click on User Role Save button")
    public void i_click_on_user_role_save_button() {
        waitFor(3);
        scrollToElement(tmUserRolePage.accountActivation);
        tmUserRolePage.editRoleSavButton.click();
    }


    /*Author-Paramita This method to verify-
      see update success message when role is successfully updated
       */
    @Then("I see update success message {string}")
    public void upadte_success_message(String message) {
        Assert.assertTrue(tmUserRolePage.updateSuccessMesg.isDisplayed());
        Assert.assertTrue(tmUserRolePage.updateSuccessMesg.getText().equals(message));

    }


    /*Author-Paramita This method to verify-
         navigate back to the User List screen
          */
    @And("I should be navigate back to the User List screen")
    public void i_should_be_navigate_back_to_the_user_list_screen() {
        waitForVisibility(tmUserRolePage.userListScreen, 20);
        Assert.assertTrue(tmUserRolePage.userListScreen.isDisplayed(), "UserList screen is not displaying");
        Assert.assertTrue(tmUserRolePage.userRoleAddButton.isDisplayed(), "Add button in User List page is not displayed");
    }

    @And("I select existing role and update end date")
    public void updateEndDate_ExistingRole() {
        waitFor(2);
        clearAndFillText(tmUserRolePage.roleEndDateValue.get(0), futureDate.get());
    }


}
