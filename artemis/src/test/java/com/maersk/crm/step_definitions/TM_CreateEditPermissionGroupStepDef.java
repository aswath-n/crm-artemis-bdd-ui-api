package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

public class TM_CreateEditPermissionGroupStepDef extends TMUtilities {


    TMProjectDetailsPage projectDetails = new TMProjectDetailsPage();
    TMPermissionGroupPage permissionGroup = new TMPermissionGroupPage();
    TMPermissionPage tmPermissionPage = new TMPermissionPage ();
    TMSearchRolePage searchRole = new TMSearchRolePage();
    TMProjectRolePage projectRole = new TMProjectRolePage();
    final ThreadLocal<TM_CreateProjectStepDef> createProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
    final ThreadLocal<TM_ProjectRoleDetailsStepDef> projectRoleDetailsStepDef = ThreadLocal.withInitial(TM_ProjectRoleDetailsStepDef::new);

    private String[] levels = {"page", "section", "field"};

    static String permissionName = "Test ";
    static String permissionDescription = "Test Description ";
    static String permissionRole = "";
    public int recordsCountInPage;
    List<String> defaultSortList = new ArrayList<> ();
    List<String> listOfRoles = new ArrayList<> ();
    public String editPermissionName;
    private HashMap<String, String> permissionMap = new HashMap<String, String>();

    @Given("I navigate to Permission Group Page")
    public void i_navigate_to_Permission_Group_Page() {
        projectDetails.viewPermissionList.click();
        waitForVisibility(permissionGroup.permissionGroupHeader, 5);
        assertTrue(permissionGroup.permissionGroupHeader.isDisplayed(), "Did not navigate to Permission Group Page");
    }

    @When("I click on add new permission button")
    public void i_click_on_add_new_permission_button() {
        permissionGroup.addPermissionHeader.click();
        assertTrue(permissionGroup.permissionGroupDetails.isDisplayed(), "Did not navigate to Add Permission Page");

    }

    @Then("I verify all fields are displayed on Permission Group Page")
    public void i_verify_all_fields_are_displayed_on_Permission_Group_Page() {
        assertTrue(permissionGroup.inputPermissionGrpName.isDisplayed(), "Group Permission Name field is not displayed");
        assertTrue(permissionGroup.inputPermissionGrpDesc.isDisplayed(), "Group Permission Name field is not displayed");
        assertTrue(permissionGroup.selectRoleDropdown.isDisplayed(), "Role drop down is not displayed");
        assertTrue(permissionGroup.saveOnPrmGrpDet.isDisplayed(), "Save Group Permission Button is not displayed");
    }

    @Then("I verify all mandatory fields on Permission Group Page")
    public void i_verify_all_mandatory_fields_on_Permission_Group_Page() {
        permissionGroup.saveOnPrmGrpDet.click();
        assertTrue(permissionGroup.blankPermissionNameError.isDisplayed(), "Permission Name field is not mandatory");
        assertTrue(permissionGroup.blankDescriptionError.isDisplayed(), "Permission Description  field is not mandatory");
        assertTrue(permissionGroup.blankRoleError.isDisplayed(), "Role dropdown field is not mandatory");
    }


    @When("I populate all field with valid data on Add Permission Group Page")
    public void i_populate_all_field_with_valid_data_on_Add_Permission_Group_Page() {
        String permissionName = "Name" + getRandomString ( 4 );
        /*if(permissionName==null) {
            permissionName = permissionName + getRandomString ( 4 );
            clearAndFillText ( permissionGroup.inputPermissionGrpName, permissionName );
        }*/
        clearAndFillText ( permissionGroup.inputPermissionGrpName, permissionName );
        permissionDescription = permissionDescription + getRandomString ( 4 );
        clearAndFillText ( permissionGroup.inputPermissionGrpDesc, permissionDescription );


        if(projectRoleDetailsStepDef.get().roleForPermGr.get().isEmpty())
            permissionGroup.selectRoleByIndex(1);
        else
            selectDropDown(permissionGroup.selectRoleDropdown, projectRoleDetailsStepDef.get().roleForPermGr.get());

        permissionRole = permissionGroup.selectRoleDropdown.getText();
    }

    @When("I click on Cancel button on Add Permission Group Page")
    public void i_click_on_Cancel_button_on_Add_Permission_Group_Page() {
        permissionGroup.cancelOnPrmGrpDet.click();
        waitFor(2);
        assertTrue(permissionGroup.cancelPermissionWarning.isDisplayed(), "Warning message is not displayed");
    }

    @When("I select {string} on warning pop-up of Add Permission Group Page")
    public void i_select_on_warning_pop_up_of_Add_Permission_Group_Page(String button) {
        if (button.equalsIgnoreCase("yes")) {
            permissionGroup.cancelPermissionWarningYesBtn.click();
        } else {
            permissionGroup.cancelPermissionWarningNoBtn.click();
        }
    }

    @Then("I see permission was not saved on Permission Group Page")
    public void i_see_permission_was_not_saved_on_Permission_Group_Page() {
        assertFalse(permissionGroup.groupPermissionIsCreated(permissionName, permissionDescription, permissionRole), "Group Permission was created");
    }

    @Then("I see previously populated data on fields it not changed on add Permission Group Page")
    public void i_see_previously_populated_data_on_fields_it_not_changed_on_add_Permission_Group_Page() {
        assertTrue(permissionGroup.permissionGroupDetails.isDisplayed(), "Navigated from Permission Group Details Page");
        assertTrue(permissionGroup.inputPermissionGrpName.getAttribute("Value").equalsIgnoreCase(permissionName), "Populated Group Permission Name changed");
        assertTrue(permissionGroup.inputPermissionGrpDesc.getAttribute("Value").equalsIgnoreCase(permissionDescription), "Populated Group Permission Description changed");
        assertTrue(permissionGroup.selectRoleDropdown.getText().equalsIgnoreCase(permissionRole), "Selected Role changed");

    }

    @When("I click on Save button on Add Permission Group Page")
    public void i_click_on_Save_button_on_Add_Permission_Group_Page() {
        waitFor(2);
        permissionGroup.saveOnPrmGrpDet.click();
        waitForVisibility(permissionGroup.permissionSucessMsg, 15);
        assertTrue(permissionGroup.permissionSucessMsg.isDisplayed(), "Successful Creation message is not displayed");
    }

    @Then("I see section for capturing the specific permission on UI components")
    public void i_see_section_for_capturing_the_specific_permission_on_UI_components() {
        assertTrue(permissionGroup.accessPermissionHeader.isDisplayed(), "Access Permission Section is not DisplayedHeader");
        assertTrue(permissionGroup.pageLevel.isDisplayed(), "Pages Section is not DisplayedHeader");
        assertTrue(permissionGroup.viewRadiobtn.isDisplayed(), "View radio button is not displayed");
        assertTrue(permissionGroup.editRadiobtn.isDisplayed(), "Edit radio button is not displayed");
        assertTrue(permissionGroup.noPermRadiobtn.isDisplayed(), "NoPermission radio button is not displayed");
        assertTrue(permissionGroup.applyAllCheckBox.isDisplayed(), "Apply All Check box is not Displayed");
        assertTrue(permissionGroup.saveAccessPermission.isDisplayed(), "Save Access Permission button is not displayed");
        assertTrue(permissionGroup.cancelAccessPermission.isDisplayed(), "Cancel Access Permission button is not displayed");
    }


    @Then("I see permission options flags by default not selected")
    public void i_see_permission_options_flags_by_default_not_selected() {
        assertFalse(permissionGroup.viewRadioButtonFlag.isSelected(), "View radiobutton is selected by default");
        assertFalse(permissionGroup.editRadioButtonFlag.isSelected(), "Edit radiobutton is selected by default");
        assertFalse(permissionGroup.noPermissionRadioButtonFlag.isSelected(), "No Permission radiobutton is selected by default");
        assertTrue(permissionGroup.applyCheckBoxFlag.getAttribute("value").equals("false"), "Apply for All checkbox is selected by default");
    }

    @When("I see {string} Permission")
    public void i_see_Permission(String level) {
        if (level.equalsIgnoreCase("{random}")) {
            int index = randomNumberBetweenTwoNumbers(0, 2);
            level = levels[index];
            System.out.println(level);
        }

        waitFor(3);
        switch (level) {
            case "page":
                permissionGroup.pageLevel.click();
                waitFor(1);
                assertTrue(permissionGroup.pageLevel.isDisplayed(), "There is no Page level permission");
                break;
            case "section":
                permissionGroup.expandPageLevel.click();
                waitFor(1);
                assertTrue(permissionGroup.sectionLevel.isDisplayed(), "There is no Section level permission");
                permissionGroup.sectionLevel.click();
                break;
            case "field":
                permissionGroup.expandPageLevel.click();
                permissionGroup.expandSectionLevel.click();
                for (WebElement fieldLevel : permissionGroup.fieldLevels) {
                    fieldLevel.click();
                    assertTrue(fieldLevel.isDisplayed(), "There is no Field level permission");
                }
                break;
        }

    }

    @Then("I am able to select and deselect View\\/Edit\\/No and Apply For All permission flags on Add Permission Group Page")
    public void i_am_able_to_select_and_deselect_View_Edit_No_and_Apply_For_All_permission_flags_on_Add_Permission_Group_Page() {
        waitFor(3);
        permissionGroup.viewRadioButtonFlag.click();
        waitFor(2);
        assertFalse(permissionGroup.editRadioButtonFlag.isSelected(), "Edit radiobutton is selected by default");
        assertFalse(permissionGroup.noPermissionRadioButtonFlag.isSelected(), "No Permission radiobutton is selected by default");
        assertTrue(permissionGroup.applyCheckBoxFlag.getAttribute("value").equals("false"), "Apply for All checkbox is selected by default");


        permissionGroup.viewRadioButtonFlag.click();
        assertFalse(permissionGroup.viewRadioButtonFlag.isSelected(), "View radiobutton is selected by default");
        assertFalse(permissionGroup.noPermissionRadioButtonFlag.isSelected(), "No Permission radiobutton is selected by default");
        assertTrue(permissionGroup.applyCheckBoxFlag.getAttribute("value").equals("false"), "Apply for All checkbox is selected by default");

        permissionGroup.noPermissionRadioButtonFlag.click();
        assertFalse(permissionGroup.viewRadioButtonFlag.isSelected(), "View radiobutton is selected by default");
        assertFalse(permissionGroup.editRadioButtonFlag.isSelected(), "Edit radiobutton is not selected by default");
        assertTrue(permissionGroup.applyCheckBoxFlag.getAttribute("value").equals("false"), "Apply for All checkbox is selected by default");

        permissionGroup.applyCheckBoxFlag.click();
        assertTrue(permissionGroup.applyCheckBoxFlag.getAttribute("value").equals("true"), "Apply for All checkbox is not selected");

    }

    @When("I see select permission options flags {string} one of the pages and save it")
    public void i_see_select_permission_options_flags_one_of_the_pages_and_save_it(String radiobutton) {
        switch (radiobutton) {
            case "view":
                permissionGroup.viewRadioButtonFlag.click();
                break;
            case "edit":
                permissionGroup.editRadioButtonFlag.click();
                break;
            case "noPermission":
                permissionGroup.noPermissionRadioButtonFlag.click();
                break;
        }
        waitFor(1);
        permissionGroup.saveAccessPermission.click();
        waitFor(1);
        i_select_on_warning_pop_up_of_Add_Permission_Group_Page("Yes");
        waitForVisibility(permissionGroup.permissionGroupHeader, 5);
        assertTrue(permissionGroup.permissionGroupHeader.isDisplayed(), "Did not navigate to Permission Group Page");
    }

    @Then("I expand previously created Permission Group")
    public void i_expand_previously_created_Permission_Group() {
        permissionGroup.expendGroupPermissionByAllFields(permissionName, permissionDescription, permissionRole);
        assertTrue(permissionGroup.permissionGroupDetails.isDisplayed(), "Did not navigate to Add Permission Page");
    }

    @Then("I can edit {string} on Edit Permission Group Page")
    public void i_can_edit_on_Edit_Permission_Group_Page(String field) {

        switch (field) {
            case "Group Permission Name":
                permissionName = permissionName.substring(5) + getRandomString(2);
                clearAndFillText(permissionGroup.inputPermissionGrpName, permissionName);
                break;
            case "Description":
                permissionDescription = permissionDescription.substring(17) + getRandomString(2);
                clearAndFillText(permissionGroup.inputPermissionGrpDesc, permissionDescription);
                break;
            case "User Role":
                permissionGroup.selectRoleByIndex(2);
                permissionRole = permissionGroup.selectRoleDropdown.getText();
//                selectDropDown(permissionGroup.selectRoleDropdown, permissionRole);
                break;
        }
        permissionGroup.saveAccessPermission.click();
        waitFor(1);


    }

    @Then("I can see new permission group was saved with expected value")
    public void i_can_see_new_permission_group_was_saved_with_expected_value() {
        selectDropDown(permissionGroup.itemsPerPageDropdown, "show 20");
        assertTrue(permissionGroup.groupPermissionIsCreated(permissionName, permissionDescription, permissionRole),
                "Changed Group Permission Values are not Saved");
    }

    @Then("I navigate to add a new role page")
    public void i_navigate_to_add_a_new_role_page() {
        createProjectStepDef.get().i_click_on_Role_List_Menu();
        searchRole.addRoleButton.click();
        assertTrue(projectRole.addRoleHeader.isDisplayed(), "Role Details page is not displayed");
    }

    @Then("I see Select Role drop-down has only Active role as an option")
    public void i_see_Select_Role_drop_down_has_only_Active_role_as_an_option() {
        waitFor(2);
        String expectedRole = (projectRoleDetailsStepDef.get().roleForPermGr.get().substring(0, 1) + projectRoleDetailsStepDef.get().roleForPermGr.get().substring(1).toLowerCase());
        selectDropDown(permissionGroup.selectRoleDropdown, expectedRole);
        assertTrue(permissionGroup.selectRoleDropdown.getText().equals(expectedRole), "Active Permission is not displayed in dropdown options");
    }

    @When("I save the Permission Group")
    public void i_save_the_Permission_Group() {
        permissionGroup.saveAccessPermission.click();
        i_select_on_warning_pop_up_of_Add_Permission_Group_Page("yes");
        waitFor(1);
    }

    @Then("I expand previously created random Permission Group")
    public void i_expand_previously_created_random_Permission_Group() {
        permissionGroup.expendRandomGroupPermission();
        assertTrue(permissionGroup.permissionGroupDetails.isDisplayed(), "Did not navigate to Add Permission Page");
    }
     /* Checks the number records displayed on screen
    Author - Aswath
    Date - 04/05/2019
    Parameters - NA
    */
    public void recordsInPage(){

        waitForPageToLoad ( 10 );
        int count=0;
        for (WebElement gpd : tmPermissionPage.grpPermissionTab) {
            waitForVisibility ( gpd, 5 );
            List<WebElement> listOfrecords = gpd.findElements ( By.xpath ( "td[1]" ) );
            List<WebElement> listOfRoles = gpd.findElements ( By.xpath ( "td[5]" ) );
            for (WebElement e : listOfrecords){
                if(e.getText ()!= null)
                    count++;
            }
            for (WebElement roles : listOfRoles){
                if(roles.getText ()!= null)
                    defaultSortList.add(roles.getText ());

            }
            scrollDown ();
        }
        recordsCountInPage=count;
    }
    /* Adds new permissions to the project
    Author - Aswath
    Date - 03/20/2019
    Parameters - NA
    */
    @And("I add new permission records")
    public void i_add_new_permission_records()  {
        recordsInPage();
        waitFor ( 25 );
        int addRecords = 6-recordsCountInPage;
        for(int i=1;i<=addRecords;i++){
            waitForPageToLoad ( 5 );
            i_click_on_add_new_permission_button ();
            waitForPageToLoad ( 5 );
            permissionName = permissionName + getRandomString(4);
            permissionDescription = permissionDescription + getRandomString(4);
            clearAndFillText(permissionGroup.inputPermissionGrpName, permissionName);
            clearAndFillText(permissionGroup.inputPermissionGrpDesc, permissionDescription);
            if(i<=2){
            permissionGroup.selectRoleByIndex(i+1);}
            else
                permissionGroup.selectRoleByIndex(i);
            permissionRole = permissionGroup.selectRoleDropdown.getText();
            waitFor ( 5 );
            i_click_on_Save_button_on_Add_Permission_Group_Page();
            waitForPageToLoad ( 10 );
            tmPermissionPage.backArrow.click ();
            waitForPageToLoad ( 5 );
        }
    }

    /* Edits the permissions and the fields
   Author - Aswath
   Date - 03/20/2019
   Parameters - NA
   */
    @And("I edit the Permission on the permission group page")
    public void i_edit_the_permission_on_the_permission_group_page()  {
        for (WebElement gpd : tmPermissionPage.grpPermissionTab) {
            waitForVisibility ( gpd, 5 );
            List<WebElement> listOfrecords = gpd.findElements ( By.xpath ( "td[1]" ) );
            for (WebElement e : listOfrecords) {
                if (e.getText () != null)
                    e.click ();
                break;
            }
        }
        i_populate_all_field_with_valid_data_on_Add_Permission_Group_Page();
        i_click_on_Save_button_on_Add_Permission_Group_Page ();
        tmPermissionPage.viewRadiobtn.click ();
        tmPermissionPage.applyAllCheckBox.click ();
        tmPermissionPage.expandFirst.click ();
        tmPermissionPage.expandSecond.click ();
        tmPermissionPage.viewRadiobtn.click ();
        tmPermissionPage.applyAllCheckBox.click ();
        tmPermissionPage.saveOnPrmGrpDet.click ();
    }
     /* Validate the edit permision page
    Author - Aswath
    Date - 04/05/2019
    Parameters - NA
    */

    @And("I verify the edit permission page")
    public void i_verify_edit_the_permission_page(){
        String  currURL = Driver.getDriver().getCurrentUrl ();
        String expURL= "https://mars-tenant-manager-ui-qa.apps.non-prod.pcf-maersk.com/tenant/projectDetails/permission/add";
        assertTrue (currURL.contains ( expURL),"Edit Permission page is not displayed"  );
    }

    @When("I add {string} permission for all grids")
    public void verifyRolePermissionChanged(String permissionType) {
        int pageCount = 1;
        int sectionCount = 1;
        List<WebElement> roots = Driver.getDriver().findElements(By.xpath("//div[contains(@class, 'mt-2')]/div/div/div/table"));
        pageCount = roots.size();
        for(int k=0;k<pageCount;k++){
            if(k!=0){
                editPermission("Csr");
                waitFor(1);
                roots = Driver.getDriver().findElements(By.xpath("//div[contains(@class, 'mt-2')]/div/div/div/table"));
            }
            WebElement pageElement = roots.get(k).findElement(By.xpath("./tbody//button"));
            click(pageElement);
            List<WebElement> sections = roots.get(k).findElements(By.xpath("./following-sibling::table[1]/tbody/tr/td/div/table/tbody/tr[not(contains(@class, 'table-collapse'))]"));
            for(int i=0;i<sections.size();i++){
                if(i!=0){
                    editPermission("Csr");
                    waitFor(1);
                    roots = Driver.getDriver().findElements(By.xpath("//div[contains(@class, 'mt-2')]/div/div/div/table"));
                    pageElement = roots.get(k).findElement(By.xpath("./tbody//button"));
                    click(pageElement);
                    waitFor(1);
                    sections = roots.get(k).findElements(By.xpath("./following-sibling::table[1]/tbody/tr/td/div/table/tbody/tr[not(contains(@class, 'table-collapse'))]"));
                }
                WebElement sectionElement = sections.get(i).findElement(By.xpath("./td//button"));
                click(sectionElement);
                List<WebElement> nodes = sections.get(i).findElements(By.xpath("./following-sibling::tr[1]//table//td"));
                for(int j=0;j<nodes.size();j++){
                    if(j!=0){
                        editPermission("Csr");
                        waitFor(1);
                        roots = Driver.getDriver().findElements(By.xpath("//div[contains(@class, 'mt-2')]/div/div/div/table"));
                        pageElement = roots.get(k).findElement(By.xpath("./tbody//button"));
                        click(pageElement);
                        waitFor(1);
                        sections = roots.get(k).findElements(By.xpath("./following-sibling::table[1]/tbody/tr/td/div/table/tbody/tr[not(contains(@class, 'table-collapse'))]"));
                        sectionElement = sections.get(i).findElement(By.xpath("./td//button"));
                        click(sectionElement);
                        nodes = sections.get(i).findElements(By.xpath("./following-sibling::tr[1]//table//td"));
                    }
                    waitFor(1);
                    permissionMap.put(nodes.get(j).getText(), permissionType);
                    click(nodes.get(j));
                    updatePermission(permissionType);
                }
            }
        }
    }

    @When("I click on group permission to edit for role {string}")
    public void editPermission(String roleName){
        if(!roleName.isEmpty())
            permissionRole = roleName;
        waitFor(1);
        int size = 1;
        boolean recordFound = false;
        if(isElementDisplayed(permissionGroup.lnkArrowForward))
            size = permissionGroup.lnkPageNations.size()-1;
        for(int i=1;i<=size;i++){
            try{
                for(WebElement row:permissionGroup.permissionGroupNameRows){
                    if(row.findElement(By.xpath("./td[5]")).getText().equalsIgnoreCase(permissionRole)){
                        click(row.findElement(By.xpath("./td[1]")));
                        recordFound = true;
                        break;
                    }
                }
            }catch(NoSuchElementException nse){
                assertTrue(false, "No role displayed");
            }
            if(recordFound)
                break;
            if(i!=size){
                click(permissionGroup.lnkPageNations.get(i));
                waitFor(1);
            }

        }
    }

    public void updatePermission(String type){
        waitFor(2);
        scrollUpUsingActions(2);
        switch (type){
            case "View":
                click(tmPermissionPage.btnViewRadio);
                break;
            case "Edit":
                click(tmPermissionPage.btnEditRadio);
                break;
            case "No Permission":
                click(tmPermissionPage.btnNoPermissionRadio);
                break;
        }
        waitFor(1);
        if(!tmPermissionPage.applyAllCheckBox.isSelected())
            click(tmPermissionPage.applyAllCheckBox);
        click(tmPermissionPage.btnAccessSave);
        waitForVisibility(tmPermissionPage.btnYesConfirmationPopUp, 10);
        click(tmPermissionPage.btnYesConfirmationPopUp);

    }

    @When("I add {string} permission for page {string} section {string} and grid {string}")
    public void updatePermissionForGivenGrid(String permissionType, String page, String section, String grid){
       //Expand till grid
        waitFor(1);
        waitForVisibility(Driver.getDriver().findElement(By.xpath("//td[text()='"+page+"']/button")), 30);
        if(!page.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+page+"']/button")));
        waitFor(1);
        if(!section.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+section+"']/button")));
        waitFor(1);
        //Selecting grid
        if(!grid.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+grid+"']")));
        updatePermission(permissionType);
    }

    @Then("I verify {string} permission is updated for page {string} section {string} and grid {string}")
    public void verifyPermissionUpdated(String permissionType, String page, String section, String grid){
        //Expand till grid
        editPermission("Csr");
        waitFor(1);
        if(!page.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+page+"']/button")));
        waitFor(1);
        if(!section.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+section+"']/button")));
        waitFor(1);
        //Selecting grid
        if(!grid.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+grid+"']")));
        scrollUpUsingActions(2);
        waitFor(1);
        boolean isSelected = false;
        switch (permissionType){
            case "View":
                isSelected = tmPermissionPage.btnViewRadio.isSelected();
                break;
            case "Edit":
                isSelected = tmPermissionPage.btnEditRadio.isSelected();
                break;
            case "No Permission":
                isSelected = tmPermissionPage.btnNoPermissionRadio.isSelected();
                break;
        }
       assertTrue(isSelected);
        waitFor(1);
        click(tmPermissionPage.btnAccessSave);
        waitForVisibility(tmPermissionPage.btnYesConfirmationPopUp, 10);
        click(tmPermissionPage.btnYesConfirmationPopUp);
    }

    @When("I add {string} permission for page {string} section {string} with appy for all")
    public void updatePermissionForGivenSectionWithApplyForAll(String permissionType, String page, String section){
        //Expand till section
        waitFor(1);
        if(!page.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+page+"']/button")));
        waitFor(1);
        if(!section.isEmpty())
            click(Driver.getDriver().findElement(By.xpath("//td[text()='"+section+"']/button")));
        waitFor(1);
        click(tmPermissionPage.applyAllCheckBox);
        updatePermission(permissionType);

    }

    @Then("I verify {string} permission for page {string} section {string} for all grids")
    public void verifyRolePermissionChanged(String permissionType, String page, String section) {
        int pageCount = 1;
        int sectionCount = 1;
        List<WebElement> roots = Driver.getDriver().findElements(By.xpath("//div[contains(@class, 'mt-2')]/div/div/div/table"));
        pageCount = roots.size();
        for(int k=0;k<pageCount;k++){
            WebElement pageElement = roots.get(k).findElement(By.xpath("./tbody//button"));

            if(!roots.get(k).findElement(By.xpath("./tbody/tr/td")).getText().split("\n")[1].trim().equalsIgnoreCase(page))
                continue;
            else
                click(pageElement);
            List<WebElement> sections = roots.get(k).findElements(By.xpath("./following-sibling::table[1]/tbody/tr/td/div/table/tbody/tr[not(contains(@class, 'table-collapse'))]"));
            for(int i=0;i<sections.size();i++){
                WebElement sectionElement = sections.get(i).findElement(By.xpath("./td//button"));

                if(!sections.get(i).findElement(By.xpath("./td")).getText().split("\n")[1].equalsIgnoreCase(section))
                    continue;
                else
                    click(sectionElement);
                List<WebElement> nodes = sections.get(i).findElements(By.xpath("./following-sibling::tr[1]//table//td"));
                for(int j=0;j<nodes.size();j++){
                    System.out.println(nodes.get(j).getText());
                    waitFor(1);
                    click(nodes.get(j));
                    waitFor(1);
                    scrollUpUsingActions(2);
                    waitFor(1);
                    boolean isSelected = false;
                    switch (permissionType){
                        case "View":
                            isSelected = tmPermissionPage.btnViewRadio.isSelected();
                            break;
                        case "Edit":
                            isSelected = tmPermissionPage.btnEditRadio.isSelected();
                            break;
                        case "NpPermission":
                            isSelected = tmPermissionPage.btnNoPermissionRadio.isSelected();
                            break;
                    }
                    assertTrue(isSelected);
                }
            }
        }
    }
}
