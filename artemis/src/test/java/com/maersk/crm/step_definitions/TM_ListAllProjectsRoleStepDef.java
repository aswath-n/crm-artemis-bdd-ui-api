package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectRolePage;
import com.maersk.crm.pages.tm.TMSearchRolePage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_ListAllProjectsRoleStepDef extends BrowserUtils {

    TMSearchRolePage tmSearchRolePage = new TMSearchRolePage();
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    TM_CreateProjectStepDef tmCreateProjectStepDef = new TM_CreateProjectStepDef();
    TM_EditProjectInformationStepDef tmEditProjectInformationStepDef = new TM_EditProjectInformationStepDef();
    List<Integer> l1= new ArrayList<>();
    List<Integer> l2= new ArrayList<>();

    @Then("I should see all the fields in the Role Page")
    public void verifyAllFields() {
        assertTrue(tmSearchRolePage.roleName.isDisplayed(), "Role Name search field is not displayed");
        assertTrue(tmSearchRolePage.roleDescription.isDisplayed(), "Role Description search field is not displayed");
        assertTrue(tmSearchRolePage.creationDate.isDisplayed(), "Creation Date search field is not displayed");
        assertTrue(tmSearchRolePage.modifiedDate.isDisplayed(), "Modified Date search field is not displayed");
        assertTrue(tmSearchRolePage.rolestatus.isDisplayed(), "Role Status search field is not displayed");
        waitForVisibility(tmSearchRolePage.creationDate, 5);
        // assertTrue(tmSearchRolePage.creationDate.getText().equalsIgnoreCase("Null"));
        //assertTrue(tmSearchRolePage.modifiedDate.getText().equalsIgnoreCase("Null"));
        //assertTrue(tmSearchRolePage.rolestatus.getText().equalsIgnoreCase("Active"));
    }

    //refactorBy: Vidya Date: 02-04-20202
    @Given("I navigate to project role search page")
    public void i_navigate_to_project_role_details_page() {
        //tmCreateProjectStepDef.i_logged_into_Tenant_Manager_Project_list_page();
        //tmEditProjectInformationStepDef.i_expand_a_random_project_to_view_the_details();
        tmCreateProjectStepDef.i_click_on_Role_List_Menu();
    }

    @And("I select a target role {string}")
    public void i_select_a_role_something(String strArg1)  {
        tmSearchRolePage.roleNamewithIndex(strArg1).click();

    }
    @And("I click on button Copy Permission")
    public void i_click_on_button_copy_permission()  {
        tmProjectRolePage.copyPermissionsButton.click();
    }
    @Then("I select a source role {string} from dropdown to be cloned and save it")
    public void i_select_the_role_something_from_dropdown_to_be_cloned_and_save_it(String strArg1) {
       singleSelectFromDropDown(tmProjectRolePage.roleSelectforCloningDropdown,strArg1);
        tmProjectRolePage.permissionDetailsSaveButton.click();
    }
    @And("I verify the success message is displayed for cloned permissions")
    public void i_verify_the_success_message_is_displayed_for_cloned_permissions(){
        waitFor(3);
        Assert.assertTrue(tmProjectRolePage.cloningSuccessMessage.isDisplayed());
    }
    @And("I verify the permissions are cloned from role {string}")
    public void i_verify_the_permissions_are_cloned_from_role_something_to_role_something(String srcRole) {
        int i=1;
        for(WebElement e: tmProjectRolePage.allRadioButtons)
        {
            if(e.isSelected())
            {
                l2.add(i);
            }
            i++;
        }
        Assert.assertEquals(l1,l2,"Permission not correcly cloned");
    }
    @Then("I save permissions assigned in a list to compare with target role")
    public void i_save_permissions_assigned_in_a_list_to_compare_with_target_role() throws Throwable {
        waitFor(4);
        int i=1;
        for(WebElement e: tmProjectRolePage.allRadioButtons)
        {
            if(e.isSelected())
            {
                l1.add(i);
            }
            i++;
        }
    }


    @Then("I query all the fields in the Role page")
    public void queryAllFields() {
        clearAndFillText(tmSearchRolePage.roleName, "qa");
        clearAndFillText(tmSearchRolePage.roleDescription, "qa");
        tmSearchRolePage.creationDate.click();
        // toDO The First element should be added here
        tmSearchRolePage.modifiedDate.click();
        // toDo The First Element method should be added here
        tmSearchRolePage.rolestatus.click();
        // toDO The First element method should be added in here

    }

    @And("I click on search button on role search page")
    public void i_click_on_search_button_on_role_search_page() {
        waitFor(3);
        tmSearchRolePage.search.click();
        waitFor(3);

    }

    @Then("I verify Refresh Button is displayed and working on Role List Page")
    public void i_verify_refresh_button_is_displayed_on_role_list_page() {
      Assert.assertTrue(tmSearchRolePage.refreshBtn.isDisplayed(),"Refresh button is not displayed");
      tmSearchRolePage.refreshBtn.click();
      Assert.assertTrue(tmSearchRolePage.UIConfigRefreshBar.isDisplayed(),"UI Config Refresh Message Bar is not displayed");
      waitForVisibility(tmSearchRolePage.successMsgAfterConfigChanges,90);
      Assert.assertTrue(tmSearchRolePage.successMsgAfterConfigChanges.isDisplayed(),"Success message after config changes not displayed");
    }


    @Then("Exact match role names are displayed in search results for role name {string}")
    public void exact_match_role_name_results(String roleName) {

        for (WebElement e : tmSearchRolePage.roleNames) {
            assertTrue(e.getText().equalsIgnoreCase(roleName), "Exact role name match not found");
        }

    }

    @Then("I provide role name as {string}")
    public void roleNameText(String roleName) {
        clearAndFillText(tmSearchRolePage.roleName, roleName);
    }

    @Then("I provide the creation date as {string}")
    public void creationDate(String creationDate) {

        tmSearchRolePage.creationDate.click();
        //toDo get the  Element Text
    }

    @Then("I enter search criteria for a role by {string} value {string}")
    public void i_enter_serach_criteria_for_a_role_by_value(String field, String value) {

        switch (field) {
            case "Creation Date":
                singleSelectFromDropDown(tmSearchRolePage.creationDate, value);
                break;

            case "Role Name":
                clearAndFillText(tmSearchRolePage.roleName, value);
                break;

            case "Role Description":
                clearAndFillText(tmSearchRolePage.roleDescription, value);
                break;

            case "Role Status":
                selectDropDown(tmSearchRolePage.rolestatus, value);
                break;

            case "Modified Date":
                singleSelectFromDropDown(tmSearchRolePage.modifiedDate, value);
                break;

            case "account type":
                break;
        }
    }

    @Then("Specified fields should be displayed in the results table")
    public void specified_fields_displayed_in_results_table() {
        assertTrue(tmSearchRolePage.resultTableHeaderRoleName.isDisplayed());
        assertTrue(tmSearchRolePage.resultTableHeaderRoleDesc.isDisplayed());
        assertTrue(tmSearchRolePage.resultTableHeaderStartDate.isDisplayed());
        assertTrue(tmSearchRolePage.resultTableHeaderEndDate.isDisplayed());
        assertTrue(tmSearchRolePage.resultTableHeaderCreationDate.isDisplayed());
        assertTrue(tmSearchRolePage.resultTableHeaderModifyDate.isDisplayed());
    }

    @Then("Error displayed that no role exists")
    public void error_displayed_that_no_role_exists() {
        assertTrue(tmSearchRolePage.noRoleExistError.isDisplayed());
    }

    @And("I click on clear button on role search page")
    public void clickClearButton() {
        tmSearchRolePage.clearButton.click();
    }

    @Then("All search criteria are reset to blank on role search page")
    public void resetSearch() {
        assertTrue(tmSearchRolePage.roleName.getAttribute("value").isEmpty());
        assertTrue(tmSearchRolePage.roleDescription.getAttribute("value").isEmpty());
        assertTrue(tmSearchRolePage.creationDate.getText().isEmpty());
        assertTrue(tmSearchRolePage.modifiedDate.getText().isEmpty());
        assertTrue(tmSearchRolePage.rolestatus.getText().isEmpty());
    }

    @Then("I see following dropdown options for {string} field displayed")
    public void i_see_following_dropdown_options_for_field_displayed(String field, List<String> options) {
        for (String option : options) {
            switch (field) {
                case "Creation Date":
                    selectDropDown(tmSearchRolePage.creationDate, option);
                    break;
                case "Modified Date":
                    selectDropDown(tmSearchRolePage.modifiedDate, option);
                    break;
                case "Role Status":
                    selectDropDown(tmSearchRolePage.rolestatus, option);
            }
        }
    }

    //refactorBy: Vidya Date: 02-04-20202
    @When("Wild card match for role description are displayed in search results")
    public void wild_card_role_description() {
        for (WebElement e : tmSearchRolePage.roleDescriptions) {
            try{
                assertTrue(e.getText().contains("csr") || e.getText().contains("Csr"), "Wild card match role description match not found");
            }
            catch (Exception ex){
                Assert.assertTrue(e.getText().equals("") || e.getText().equals(" ") ||
                        e.getText()==null);
            }
        }
    }

    @Then("Only active roles are displayed and not inactive roles")
    public void only_active_roles_are_displayed_and_not_inactive_roles() {
        for (WebElement e : tmSearchRolePage.endDates) {
            if (e.getText().isEmpty()) continue;
            Date end_date = convertStringToDate(e.getText());
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            assertTrue(end_date.after(today) || end_date.equals(today));

        }
    }

    @Then("Only inactive roles are displayed and not active roles")
    public void only_inactive_roles_are_displayed_and_not_active_roles() {
        try {
            if (tmSearchRolePage.noRoleExistError.isDisplayed()) {
                return;
            }
        } catch (Exception e) {
        }
        for (WebElement e : tmSearchRolePage.endDates) {
            if (e.getText().isEmpty()) continue;
            Date end_date = convertStringToDate(e.getText());
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            assertTrue(end_date.before(today));

        }
    }

    /**
     * This method verifies if start_dates of all roles fall in the current month
     * Author - Vinuta
     * Date - Jan 10 2019
     * RefactorBy: Vidya Date: 02-04-2020
     */
    @Then("Only roles created this month are displayed")
    public void only_roles_created_this_month() {
        try {
            if (tmSearchRolePage.noRoleExistError.isDisplayed()) {
                return;
            }
        } catch (Exception e) {
        }

        for (WebElement e : tmSearchRolePage.creationDates) {
            Date start_date = convertStringToDate(e.getText());
            Calendar cal = Calendar.getInstance();

            Calendar start_date_cal = Calendar.getInstance();
            start_date_cal.setTime(start_date);

            assertTrue(cal.get(Calendar.YEAR) == start_date_cal.get(Calendar.YEAR));
            assertTrue(cal.get(Calendar.MONTH) == start_date_cal.get(Calendar.MONTH));
        }
    }


    /**
     * This method verifies if modified_dates of all roles fall in the current month
     * Author - Vinuta
     * Date - Jan 10 2019
     */
    @Then("Only roles modified this month are displayed")
    public void only_roles_modified_this_month() {
        try {
            if (tmSearchRolePage.noRoleExistError.isDisplayed()) {
                return;
            }
        } catch (Exception e) {
        }
        for (WebElement e : tmSearchRolePage.lastModifiedDates) {
            if (e.getText().isEmpty()) continue;
            Date start_date = convertStringToDate(e.getText());
            Calendar cal = Calendar.getInstance();

            Calendar start_date_cal = Calendar.getInstance();
            start_date_cal.setTime(start_date);

            assertTrue(cal.get(Calendar.YEAR) == start_date_cal.get(Calendar.YEAR));
            assertTrue(cal.get(Calendar.MONTH) == start_date_cal.get(Calendar.MONTH));
        }
    }

    /**
     * This method clicks on the first role in the role list page
     * Author - Vinuta
     * Date - Jan 10 2019
     */
    @Then("I click on first role to open role details page")
    public void open_first_role() {
        tmSearchRolePage.roleNames.get(0).click();
        assertTrue(tmProjectRolePage.roleDetailsPageTitle.isDisplayed());
    }

    /**
     * This method checks if start dates on role list are sorted in descending order
     * Author - Vinuta
     * Date - Jan 10 2019
     */
    @Then("Roles are sorted by start date descending")
    public void roles_are_sorted_by_start_date_descending() {
        assertTrue(ascendingOrderDates(tmSearchRolePage.startDates));
    }

    @When("I navigate to new permission settings")
    public void iNavigateToNewPermissionSettings() {
        tmProjectRolePage.newPermissionSettingPanel.click();
    }
}
