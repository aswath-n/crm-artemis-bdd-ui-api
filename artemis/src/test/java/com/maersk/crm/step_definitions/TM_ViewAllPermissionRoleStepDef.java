package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMPermissionPage;
import com.maersk.crm.pages.tm.TMProjectRolePage;
import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.*;

public class TM_ViewAllPermissionRoleStepDef extends TMUtilities {


    TMSearchRolePage tmSearchRolePage = new TMSearchRolePage();
    final ThreadLocal<TM_CreateProjectStepDef> tmCreateProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    TMPermissionPage tmPermissionPage = new TMPermissionPage ();
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    TM_CreateEditPermissionGroupStepDef tmCreateEditPermissionGroupStepDef = new TM_CreateEditPermissionGroupStepDef ();
//    final ThreadLocal<List<String>> defaultSortList = ThreadLocal.withInitial(ArrayList::new);

    /* Creates the new project and navigates to detail section
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @And("I create project and navigate to the project")
    public void i_navigated_to_created_project()  {
        tmCreateProjectStepDef.get().i_click_on_create_a_new_project ();
        String projName= getRandomString ( 6 );
        String progName = getRandomString ( 8 );
        String conID= generateRandomNumberChars ();
        String clientN = getRandomString ( 4 );
        createAndSave (projName,progName, conID,clientN );
        waitForPageToLoad ( 10 );
        tmSearchProjectStepDefs.get().i_search_for_a_project_by_value("project",projName );
        waitForPageToLoad ( 10 );
    }

    /* Navigate to the role section and add a new role
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @And("I navigate to the role and add a new role")
    public void i_navigated_to_role_and_create_role()  {
    tmCreateProjectStepDef.get().i_click_on_Role_List_Menu();
    tmSearchRolePage.addRoleButton.click();
    assertTrue(tmProjectRolePage.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }

    /* Validates the Permission table structure
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @Then("I verify the Permission table")
    public void i_verify_the_permission_table()  {
        String currURL= Driver.getDriver().getCurrentUrl ();
        String expURL = "https://mars-tenant-manager-ui-qa.apps.non-prod.pcf-maersk.com/tenant/projectDetails/permission/search";
        assertTrue ( currURL.startsWith ( expURL ), "User not navigated to Permission page");


    }

    /* Verifies the message No Permission in the permission table
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @Then("I verify No Permission message in permission list page")
    public void i_verify_no_permission_message_in_permission_list_page()  {
        assertTrue( tmPermissionPage.noPermissionsFound.isDisplayed(), "No Permission message is not displayed");

    }

    /* Validates the data in Permission table
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @Then("I verify the records in a permission table")
    public void i_verify_the_records_in_a_permission_table() {
        waitForPageToLoad ( 5 );
        assertTrue( tmPermissionPage.groupPermissionNameTabHdr.isDisplayed(), "Group Permission column is not displayed");
        assertTrue( tmPermissionPage.groupPermissionDescTabHdr.isDisplayed(), "Group Description column is not displayed");
        assertTrue( tmPermissionPage.creationDateTabHdr.isDisplayed(), "Creation Date column is not displayed");
        assertTrue( tmPermissionPage.lastModifiedDateTabHdr.isDisplayed(), "Last Modified Date column is not displayed");
        assertTrue( tmPermissionPage.rolesNameTabHdr.isDisplayed(), "Last Modified Date column is not displayed");

        for (WebElement gpd : tmPermissionPage.grpPermissionTab) {
            waitForVisibility ( gpd, 5 );
            List<WebElement> listOfStatus = gpd.findElements ( By.xpath ( "td[1]" ) );
            for (WebElement e : listOfStatus){
            if(e.getText ()==null)
                break;
            }
        }
    }

    /* Validates the default drop down value in Permission table
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @Then("I verify default pagination in permission page")
    public void i_verify_default_pagination_in_permission_page()  {
        tmPermissionPage.showFiveDropdown.isDisplayed ();
        String actualshowfive = tmPermissionPage.showFiveDropdown.getText ();
        assertEquals ( actualshowfive,"show 5" );
    }

    /* Validates the displayed records and show 20 drop down value in Permission table
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @Then("I verify the (.+) records are displayed")
    public void i_verify_the_some_records_are_displayed(String i)  {
        waitForPageToLoad ( 10 );
        int count=0;
        for (WebElement gpd : tmPermissionPage.grpPermissionTab) {
            waitForVisibility ( gpd, 5 );
            List<WebElement> listOfStatus = gpd.findElements ( By.xpath ( "td[1]" ) );
            for (WebElement e : listOfStatus){
                if(e.getText ()!= null)
                    count++;
            }
            scrollDown ();
        }
        if(tmPermissionPage.nextPermissionPage.isDisplayed ()){
        int j = Integer.parseInt(i);
        assertEquals ( count,j,"Table records are not list accordingly thr view selection" );
        }
    }

        /* Selects the show 20 dropdown to view 20 records in permission table
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @And("I select the pagination option has (.+) records view option")
    public void i_select_the_pagination_option_has_any_records_view_option(String i)  {
        waitForVisibility ( tmPermissionPage.paginationDrpdwn, 5 );
        tmPermissionPage.paginationDrpdwn.click ();
        selectViewDropDown(i);
    }

    /* Select the show 20 dropdown
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    public void selectViewDropDown(String i){
        WebElement single = Driver.getDriver ().findElement ( By.xpath ( "//ul/li[contains(text(), 'show " + i + "')]" ) );
        waitForVisibility ( single, 5 );
        single.click ();
        waitFor ( 10 );
    }

    /* Verify the default sort ord
    Author - Aswath
    Date - 03/25/2019
    Parameters - NA
    */
    @Then("I verify the default sort for the permission column")
    public void i_verify_the_default_sort_for_the_permission_column(){
        for (WebElement gpd : tmPermissionPage.grpPermissionTab)
                {
                    List<WebElement> listOfPermissions = gpd.findElements ( By.xpath ( "td[1]" ) );
                    ascendingOrderTexts ( listOfPermissions );
                }
    }

}
