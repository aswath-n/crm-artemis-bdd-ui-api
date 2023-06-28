package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.ExcelReader;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.*;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_AddNewUserToProjectStepDef extends TMUtilities {


    final static ThreadLocal<Integer> randomMaxID = ThreadLocal.withInitial(() -> randomNumberBetweenTwoNumbers(154000, 154700));

    private static final ThreadLocal<String> maxID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> middleName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> email = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> startDate = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> emailID = ThreadLocal.withInitial(String::new);

    TMAddNewUserPage tmAddNewUserPage = new TMAddNewUserPage();
    TMProjectListPage tmProjectListPage = new TMProjectListPage();
    TMSearchUserPage searchUser = new TMSearchUserPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    
    
    final ThreadLocal<TM_SaveAProjectStepDef> tmSaveAProjectStepDef = ThreadLocal.withInitial(TM_SaveAProjectStepDef::new);
    final ThreadLocal<TM_EditProjectInformationStepDef> tmEditProjectInformationStepDef = ThreadLocal.withInitial(TM_EditProjectInformationStepDef::new);
    final ThreadLocal<TM_SearchProjectStepDefs> tmSearchProjectStepDefs = ThreadLocal.withInitial(TM_SearchProjectStepDefs::new);
    final ThreadLocal<Map<String, Object>> newTestData = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    final ThreadLocal<TM_CreateProjectStepDef> tm_createProjectStepDef = ThreadLocal.withInitial(TM_CreateProjectStepDef::new);
   
    public static final ThreadLocal<String> firstNameValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> lastNameValue = ThreadLocal.withInitial(String::new);

    public int get_random_max_ID() {
        return randomMaxID.get();
    }

    public static String getFirsProName() {
        return firsProName;
    }

    private static String firsProName;


    public String getFirstName() {
        return firstName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public String getMaxID() {
        return maxID.get();
    }

    @When("I set maersk employee to yes")
    public void i_set_maersk_employee_to_yes() {
        tmAddNewUserPage.set_maerskEmp_Status("True");
    }

    @When("I enter max id {string}")
    public void i_enter_max_id(String string) {
        tmAddNewUserPage.set_maerskEmp_Id(string);
    }

    @When("I click on add max id button")
    public void i_click_on_add_max_id_button() {
        tmAddNewUserPage.addMaxIdButton.click();
        tmAddNewUserPage.add_maxId();
    }

    @Then("I receive an error that max ID does not exist")
    public void i_receive_an_error_that_max_ID_does_not_exist() {
        assertTrue(tmAddNewUserPage.get_maxIDError(), "Error that Max ID is not found, is not displayed");
    }

    @When("I create,save and view new project details")
    public void i_create_save_and_view_new_project_details() {
        tmSaveAProjectStepDef.get().i_enter_valid_details_and_save_the_project();
        tmSaveAProjectStepDef.get().should_be_navigated_to_the_home_page();
        tmSearchProjectStepDefs.get().i_search_for_a_project_by_value("project", tmSaveAProjectStepDef.get().projectName.get());
        tmEditProjectInformationStepDef.get().i_expand_a_random_project_to_view_the_details();
        /*tmSearchProjectStepDefs.get().i_search_for_a_project_by_value("project", tmSaveAProjectStepDef.get().projectName);
        waitFor(10);
        tmProjectListPage.getProjectDetailsButton(tmSaveAProjectStepDef.get().projectName).click();*/
    }

    @Then("I should be navigated to User Details page")
    public void i_should_be_navigated_to_User_Details_page() {
        assertTrue(tmAddNewUserPage.get_addUserHeader().isDisplayed(), "Project-Add User is not displayed on Header");
        assertTrue(tmAddNewUserPage.get_userDetailsPageTitle().isDisplayed(), "User Details title is not displayed");
    }

    @Then("I should see all the elements displayed in the User Details Page")
    public void i_should_see_all_the_elements_displayed_in_the_User_Details_Page() {
        waitForVisibility(tmAddNewUserPage.addFirstName, 5);
        assertTrue(tmAddNewUserPage.addFirstName.isDisplayed(), "First Name field is not displayed.");
        assertTrue(tmAddNewUserPage.addLastName.isDisplayed(), "Last Name field is not displayed.");
        assertTrue(tmAddNewUserPage.addMiddleName.isDisplayed(), "Middle Initial field is not displayed.");
        //assertTrue(tmAddNewUserPage.maerskEmpCheckbox.isDisplayed(), "maersk ID Status checkbox is not displayed");
        tmAddNewUserPage.maerskEmpCheckbox.click();
        assertTrue(tmAddNewUserPage.maerskEmpIdTextbox.isDisplayed(), "maersk ID Field is not displayed.");
        tmAddNewUserPage.maerskEmpCheckbox.click();
        assertTrue(tmAddNewUserPage.addEmail.isDisplayed(), "Email field is not displayed.");
        assertTrue(tmAddNewUserPage.startDate.isDisplayed(), "Start Date field is not displayed.");
        assertTrue(tmAddNewUserPage.endDate.isDisplayed(), "End Date field is not displayed.");
        assertTrue(tmAddNewUserPage.accountType.isDisplayed(), "Account Type field is not displayed.");
        assertTrue(tmAddNewUserPage.accountAuthType.isDisplayed(), "Account Authorisation field is not displayed.");
        assertTrue(tmAddNewUserPage.authDate.isDisplayed(), "Authorisation Date field is not displayed.");
        assertTrue(tmAddNewUserPage.overrideAuthCheckbox.isDisplayed(), "Override Authorisation checkbox is not displayed.");
        scrollDown();
        click(tmAddNewUserPage.overrideAuthCheckbox);
        assertTrue(tmAddNewUserPage.overrideAuthReasonType.isDisplayed(), "Override Authorisation Reason field is not displayed.");
        tmAddNewUserPage.overrideAuthCheckbox.click();
        assertTrue(tmAddNewUserPage.accessToPHICheckbox.isDisplayed(), "Access to PHI checkbox is not displayed");
        tmAddNewUserPage.accessToPHICheckbox.click();
        assertTrue(tmAddNewUserPage.phiReasonType.isDisplayed(), "PHI Reason dropdown is not displayed");
        tmAddNewUserPage.accessToPHICheckbox.click();
        assertTrue(tmAddNewUserPage.accessToPIICheckbox.isDisplayed(), "Access to PII checkbox is not displayed");
        tmAddNewUserPage.accessToPIICheckbox.click();
        assertTrue(tmAddNewUserPage.piiReasonType.isDisplayed(), "PII Reason dropdown is not displayed");
        tmAddNewUserPage.accessToPIICheckbox.click();
        assertTrue(tmAddNewUserPage.saveButton.isDisplayed());
        assertTrue(tmAddNewUserPage.cancelButton.isDisplayed());


    }

    @Then("I should see default values populated")
    public void i_should_see_default_values_populated() {
        assertTrue(tmAddNewUserPage.isDisabledByDefault("ismaerskEmp"), "maersk ID Status checkbox has to be disabled by default.");
        assertFalse(tmAddNewUserPage.maerskEmpIdTextbox.isEnabled(), "maersk ID field has to be disabled by default.");
        assertTrue(tmAddNewUserPage.isDisabledByDefault("isOverrideAuth"), "Authorisation Date field is not displayed.");
        assertTrue(tmAddNewUserPage.accountType.getText().equals("Individual - Non-maersk"),
                "Account Type dropdown option has to be 'Individual - Non-maersk' by default.");
        assertTrue(tmAddNewUserPage.isDisabledByDefault("isAccessToPHI"), "Access to PHI checkbox has to be disabled by default.");
        assertTrue(tmAddNewUserPage.isDisabledByDefault("isAccessToPII"), "Access to PII checkbox has to be disabled by default.");
        assertFalse(tmAddNewUserPage.projectName.getAttribute("value").equals(tmEditProjectInformationStepDef.get().currentProjectName),
                "Project Name is not displayed by default");
        assertTrue(tmAddNewUserPage.authDate.getAttribute("value").replace("/", "").equals(getCurrentDateWithFormat()));
        tmAddNewUserPage.maerskEmpCheckbox.click();
        assertTrue(tmAddNewUserPage.accountType.getText().equals("Individual - maersk"),
                "Account Type dropdown option has to be 'Individual - maersk when 'Is maersk Employee' is set to True");

    }

    @Then("I should see a pop-up as an overlay on User Details page")
    public void i_should_see_a_pop_up_as_an_overlay_on_User_Details_page() {
        waitFor(5);
        assertTrue(tmAddNewUserPage.get_noApproversError().isDisplayed(), "Pop-up is not displayed when approvers are not added");
    }

    @Then("I click on Continue button on User Details error pop-up")
    public void i_click_on_Continue_button_on_User_Details_error_pop_up() {
        assertTrue(tmAddNewUserPage.get_continueBtnOnApproversError().isDisplayed(), "Continue button is not displayed");
        tmAddNewUserPage.get_continueBtnOnApproversError().click();

    }

    @Then("I am navigated to Project Details page")
    public void i_am_navigated_to_Project_Details_page() {
        waitFor(5);
        assertTrue(tmProjectDetailsPage.projectDetailsTitle.isDisplayed(), "Project Details page is not displayed");
    }

    /* By Vinuta
    This method creates a user inside the project
    Parameters: isMaxEmp(Yes, No),maxID(if not given, takes a random maxID),first name, middle initial, last name, email, startDate(is an integer to be added to current date, if not given, takes current date),end date(integer),
                acctType,acctAuth,authDate,overrideAuthReason,phiAccess,phiReason,piiAccess,piiReason,statusverrideAuth - Not implemented into the method as they are not required at this point
                status(Active, Inactive)
       Refactor By; Vidya Date:01-03-2020 Added line to select role
     */
    @When("I create User with given data {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_create_User_with_given_data(String isMaxEmp, String maxID, String fn, String mn, String ln, String email, String startDate, String endDate, String acctType, String acctAuth, String authDate, String overrideAuth, String overrideAuthReason, String phiAccess, String phiReason, String piiAccess, String piiReason, String status, String role) {

        //If maersk Employee is Yes
        if (isMaxEmp.equalsIgnoreCase("Yes")) {
            tmAddNewUserPage.set_maerskEmp_Status("True");

            //If max ID is not provided, enter random max ID
            if (maxID.isEmpty()) maxID = Integer.toString(randomMaxID.get());
            tmAddNewUserPage.set_maerskEmp_Id(maxID);

            tmAddNewUserPage.addMaxId.click();

            waitFor(5);
            assertTrue(!(tmAddNewUserPage.addFirstName.getAttribute("value").isEmpty()), "Max ID employee details are not populated");
            this.maxID.set(maxID);
            this.email.set(tmAddNewUserPage.addEmail.getAttribute("value"));
            this.firstName.set(tmAddNewUserPage.addFirstName.getAttribute("value"));
            this.lastName.set(tmAddNewUserPage.addLastName.getAttribute("value"));
            this.middleName.set(tmAddNewUserPage.addMiddleName.getAttribute("value"));
        } else {
            //to implement when not max employee
//            this.email=randomEmailId();
//            this.firstName=getRandomString(5);
//            this.lastName=getRandomString(5);
//            this.middleName=getRandomString(1)
            firstNameValue.set(getRandomString(5));
            lastNameValue.set(getRandomString(5));
            tmAddNewUserPage.addFirstName.sendKeys(firstNameValue.get());
            tmAddNewUserPage.addLastName.sendKeys(lastNameValue.get());
            tmAddNewUserPage.addMiddleName.sendKeys(getRandomString(1));
            synchronized (emailID){
                emailID.set(randomEmailId());
            }
            tmAddNewUserPage.addEmail.sendKeys(emailID.get());
        }


        //If start date is not given:If status is active:enter today's date as start date,end date as today+1
        //If start date is not given:If status is inactive:enter today+1 as start date, end date as today+2

        if (startDate.isEmpty() && endDate.isEmpty() && status.equalsIgnoreCase("active")) {
            clearAndFillText(tmAddNewUserPage.startDate, getCurrentDate());
            //clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(1));
        }

        if (startDate.isEmpty() && endDate.isEmpty() && status.equalsIgnoreCase("inactive")) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(1));
            clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(2));
        }

        if (!startDate.isEmpty() && endDate.isEmpty()) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(Integer.parseInt(startDate)));
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate((Integer.parseInt(startDate)) + 1));
        }

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(Integer.parseInt(startDate)));
            clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(Integer.parseInt(endDate)));
        }

        if (!(role.equals("") || role.equals(" "))) {
            tmAddNewUserPage.addRoleButton.click();
            selectDropDown(tmAddNewUserPage.roleDropDown, "Csr");
//            clearAndFillText(tmAddNewUserPage.roleStartDate, getGreaterDate(Integer.parseInt(startDate)));
//            clearAndFillText(tmAddNewUserPage.roleEndDate,getGreaterDate(Integer.parseInt(endDate)));
            click(tmAddNewUserPage.rolesStartDateCalender);
            click(tmAddNewUserPage.StartDateOkButton);
            //tmAddNewUserPage.addRolePlusSign.click();
        }
    }

    @When("I create User with duplicate emailID {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_create_User_with_duplicate_emailID(String isMaxEmp, String maxID, String fn, String mn, String ln, String email, String startDate, String endDate, String acctType, String acctAuth, String authDate, String overrideAuth, String overrideAuthReason, String phiAccess, String phiReason, String piiAccess, String piiReason, String status, String role) {
        //If maersk Employee is Yes
        if (isMaxEmp.equalsIgnoreCase("Yes")) {
            tmAddNewUserPage.set_maerskEmp_Status("True");

            //If max ID is not provided, enter random max ID
            if (maxID.isEmpty()) maxID = Integer.toString(randomMaxID.get());
            tmAddNewUserPage.set_maerskEmp_Id(maxID);

            tmAddNewUserPage.addMaxId.click();

            waitFor(5);
            assertTrue(!(tmAddNewUserPage.addFirstName.getAttribute("value").isEmpty()), "Max ID employee details are not populated");
            this.maxID.set(maxID);
            this.email.set(tmAddNewUserPage.addEmail.getAttribute("value"));
            this.firstName.set(tmAddNewUserPage.addFirstName.getAttribute("value"));
            this.lastName.set(tmAddNewUserPage.addLastName.getAttribute("value"));
            this.middleName.set(tmAddNewUserPage.addMiddleName.getAttribute("value"));
        } else {
            //to implement when not max employee
//            this.email=randomEmailId();
//            this.firstName=getRandomString(5);
//            this.lastName=getRandomString(5);
//            this.middleName=getRandomString(1);

            tmAddNewUserPage.addFirstName.sendKeys(getRandomString(5));
            tmAddNewUserPage.addLastName.sendKeys(getRandomString(5));
            tmAddNewUserPage.addMiddleName.sendKeys(getRandomString(1));
            tmAddNewUserPage.addEmail.sendKeys(emailID.get());
        }


        //If start date is not given:If status is active:enter today's date as start date,end date as today+1
        //If start date is not given:If status is inactive:enter today+1 as start date, end date as today+2

        if (startDate.isEmpty() && endDate.isEmpty() && status.equalsIgnoreCase("active")) {
            clearAndFillText(tmAddNewUserPage.startDate, getCurrentDate());
            //clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(1));
        }

        if (startDate.isEmpty() && endDate.isEmpty() && status.equalsIgnoreCase("inactive")) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(1));
            clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(2));
        }

        if (!startDate.isEmpty() && endDate.isEmpty()) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(Integer.parseInt(startDate)));
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate((Integer.parseInt(startDate)) + 1));
        }

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            clearAndFillText(tmAddNewUserPage.startDate, getGreaterDate(Integer.parseInt(startDate)));
            clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(Integer.parseInt(endDate)));
        }

        if (!(role.equals("") || role.equals(" "))) {
            tmAddNewUserPage.addRoleButton.click();
            selectDropDown(tmAddNewUserPage.roleDropDown, "Csr");
            clearAndFillText(tmAddNewUserPage.roleStartDate, getCurrentDate());
            //tmAddNewUserPage.addRolePlusSign.click();
        }
    }

    @When("I click on save a user button")
    public void i_click_on_save_a_user_button() {
        waitFor(2);
        tmAddNewUserPage.saveButton.click();
    }

    @And("I update end date in User Details")
    public void i_update_end_date_in_user_details() {
        tmAddNewUserPage.endDate.sendKeys(getFutureDate(5));
    }

    @And("I update user role with another role value")
    public void i_update_user_role_with_value() {
        waitFor(2);
        selectRandomDropDownOption(tmAddNewUserPage.roleDropDown);
    }


    @Then("Create new user with same FirstName and StartDate in different project")
    public void createNewUserWithSameFirstNameAndStartDateInDifferentProject() {
        System.out.println("First Name is : " + firstName.get());
        clearAndFillText(tmAddNewUserPage.addFirstName, firstName.get());
        clearAndFillText(tmAddNewUserPage.startDate, startDate.get());
        selectDropDown(tmAddNewUserPage.accountType, "System");
    }

    @Then("Navigate back to Project List page")
    public void navigateBackToProjectListPage() {
        waitFor(5);
        searchUser.backToProjectListButton.click();
        waitFor(5);
        searchUser.backToProjectListButton.click();
        waitForVisibility(tmAddNewUserPage.firstProject, 15);
        firsProName = tmAddNewUserPage.frstProName.getText();
        tmAddNewUserPage.firstProject.click();
    }

    //refactoring 10-19-18
    @Then("I see mandatory fields have error messages")
    public void i_see_mandatory_fields_have_error_messages(List<String> fields) {

        for (String field : fields) {
            switch (field) {
                case "First Name":
                    scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
                    assertTrue(tmAddNewUserPage.blankFirstNameError.isDisplayed(), "Blank " + field + " Error has to be displayed");
                    //waitFor(10);
                    break;
                case "Last Name":
                    scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
                    assertTrue(tmAddNewUserPage.blankLastNameError.isDisplayed(), "Blank " + field + " Error has to be displayed");
                    //waitFor(10);
                    break;
                case "Email Address":
                    scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
                    assertTrue(tmAddNewUserPage.blankEmailError.isDisplayed(), "Blank " + field + " Error has to be displayed");
                    break;
                case "Start Date":
                    scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
                    assertTrue(tmAddNewUserPage.blankStartDateError.isDisplayed(), "Blank " + field + " Error has to be displayed");
                    break;
                //todo confirm account type is not mandatory
                /*case "Account Type":
                    tmAddNewUserPage.accountType.click();
                    hover(tmAddNewUserPage.blankAccountTypeOption);
                    tmAddNewUserPage.blankAccountTypeOption.click();
                    waitFor(1);
                    tmAddNewUserPage.saveButton.click();
                    assertTrue(tmAddNewUserPage.blankAccountTypeError.isDisplayed(), "Blank " + field + " Error has to be displayed");
                    break;*/
                case "Internal maersk Employee? - Y/N":
                    scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
//                    JavascriptExecutor jse = (JavascriptExecutor)driver;
//
//                    jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");
                    // scrollDownToElement(tmAddNewUserPage.maerskEmpCheckbox);
                    tmAddNewUserPage.maerskEmpCheckbox.click();
                    tmAddNewUserPage.saveButton.click();
                    assertTrue(tmAddNewUserPage.blankMaxIdError.isDisplayed(), "Blank Max Id Error has to be displayed");
                    break;
                case "Role Name":
                    assertTrue(tmProjectRolePage.roleNameBlankError.isDisplayed(), "The Role Name Is Required And Cannot Be Left Blank. error is not displayed");
                    break;
                case "Role Start Date":
                    assertTrue(tmProjectRolePage.roleStartDateBlankError.isDisplayed(), "The Start Date Is Required And Cannot Be Left Blank. error is not displayed ");
                    break;
            }
        }
    }

    /*@When("I select Yes for maersk Employee")
    public void i_select_Yes_for_maersk_Employee() {
        tmAddNewUserPage.maerskEmpCheckbox.click();
        assertFalse(tmAddNewUserPage.isDisabledByDefault("ismaerskEmp"), "maersk ID Status checkbox is not set to 'Yes'");
        waitFor(1);
    }

    @When("I enter valid {string} Max ID in the maersk Id field and click on plus button")
    public void i_enter_valid_Max_ID_in_the_maersk_Id_field_and_click_on_plus_button(String id) {
       tmAddNewUserPage.addMaxId.sendKeys(id);
       tmAddNewUserPage.addMaxIdButton.click();
       assertTrue(tmAddNewUserPage.addMaxId.getAttribute("value").equals(id));
    }*/

    @When("I see fields auto-populated with the corresponding values from Active Directory")
    public void i_see_fields_auto_populated_with_the_corresponding_values_from_Active_Directory() {
        assertTrue(tmAddNewUserPage.verifyReadOnlyElByValue("Muhabbat"), "First Name does not match");
        assertTrue(tmAddNewUserPage.verifyReadOnlyElByValue("Khaydarova"), "Last Name does not match");
        assertTrue(tmAddNewUserPage.verifyReadOnlyElByValue("MuhabbatKhaydarova@maersk.com"), "Email does not match");

    }

    @Then("I see Auto-populated fields are not editable")
    public void i_see_Auto_populated_fields_are_not_editable() {
        assertTrue(tmAddNewUserPage.verifyNotEditable(tmAddNewUserPage.addFirstName, "Edit"), "Auto-populated First Name field has can't be editable");
        assertTrue(tmAddNewUserPage.verifyNotEditable(tmAddNewUserPage.addLastName, "Edit"), "Auto-populated Last Name field has can't be editable");
        assertTrue(tmAddNewUserPage.verifyNotEditable(tmAddNewUserPage.addEmail, "Edit"), "Auto-populated Email field has can't be editable");
    }

    @When("I populate some data in the fields")
    public void i_populate_some_data_in_the_fields() {
        clearAndFillText(tmAddNewUserPage.addFirstName, (newTestData.get().get("name").toString()));
        clearAndFillText(tmAddNewUserPage.addLastName, (newTestData.get().get("surname").toString()));
        clearAndFillText(tmAddNewUserPage.addEmail, (newTestData.get().get("email").toString()));

    }

    @When("I click on Cancel button on Add User Page")
    public void i_click_on_Cancel_button_on_Add_User_Page() {
        scrollToElement(tmAddNewUserPage.cancelButton);
        waitForVisibility(tmAddNewUserPage.cancelButton, 5);
        tmAddNewUserPage.cancelButton.click();
    }

    @When("I see {string} alert displayed")
    public void i_see_alert_displayed(String string) {
        waitForVisibility(tmAddNewUserPage.cancelWarningPopUp, 5);
        assertTrue(tmAddNewUserPage.cancelWarningPopUp.isDisplayed(), "Warning Pop Up is not displayed");
    }

    @When("I click on No and I am navigated back to Add User page and see all previously entered unsaved data")
    public void i_click_on_No_and_I_am_navigated_back_to_Add_User_page_and_see_all_previously_entered_unsaved_data() {
        tmAddNewUserPage.warningPopUpCancelButton.click();
        assertTrue(tmAddNewUserPage.addFirstName.getAttribute("value").equals(newTestData.get().get("name").toString()),
                "First Name did not remain the same");
        assertTrue(tmAddNewUserPage.addLastName.getAttribute("value").equals(newTestData.get().get("surname").toString()),
                "Last Name did not remain the same");
        assertTrue(tmAddNewUserPage.addEmail.getAttribute("value").equals(newTestData.get().get("email").toString()),
                "Email did not remain the same");
    }

    @Then("I click on Yes and I am navigated back to User List Page")
    public void i_click_on_Yes_and_I_am_navigated_back_to_User_List_Page() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", tmAddNewUserPage.warningPopUpContButton);
        assertTrue(searchUser.addUserButton.isDisplayed(), "Add User Button id not displayed");
        assertTrue(searchUser.projectName.isDisplayed(), "Project Name is not displayed on Header");

    }

    @Then("I am navigated back to User List Page")
    public void i_am_navigated_back_to_User_List_Page() {
        assertTrue(searchUser.addUserButton.isDisplayed(), "Add User Button id not displayed");
        assertTrue(searchUser.projectName.isDisplayed(), "Project Name is not displayed on Header");
    }

    @When("I click on add new user button on add new user page")
    public void i_click_on_add_new_user_button_on_add_new_user_page() {
        waitFor(2);
        click(searchUser.addUserButton);
    }

    @When("I enter the Start Date prior to the date of creation")
    public void i_enter_the_Start_Date_prior_to_the_date_of_creation() {
        clearAndFillText(tmAddNewUserPage.startDate, getPriorDate(2));
    }

    //refactoring 10-19-18
    @When("I see {string} message under the {string} field")
    public void i_see_message_under_the_field(String message, String field) {
        if (field.equals("start")) {
            scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
            assertTrue(tmAddNewUserPage.priorDateError.isDisplayed());
            assertTrue(tmAddNewUserPage.priorDateError.getText().equals(message));
        }
        if (field.equals("end")) {
            scrollToElement(tmAddNewUserPage.userDetailsPageTitle);
            assertTrue(tmAddNewUserPage.endDateErrorMessage.isDisplayed());
            System.out.println(tmAddNewUserPage.endDateErrorMessage.getText());
            assertTrue(tmAddNewUserPage.endDateErrorMessage.getText().equals(message));
        }
        if (field.equals("roleEndDate")) {
            if (message.equals("The end date cannot be in the past")) {
                assertTrue(tmProjectRolePage.lessOrEqualEndDateError.isDisplayed());
                assertTrue(tmProjectRolePage.lessOrEqualEndDateError.getText().equals(message));
            }
            if (message.equals("Invalid date format")) {
                assertTrue(tmProjectRolePage.invalidEndDateError.isDisplayed());
                assertTrue(tmProjectRolePage.invalidEndDateError.getText().equals(message));
            }
            if (message.equals("The date entered does not exist. Please enter a valid date.")) {
                assertTrue((tmProjectRolePage.nonExistingEndDate.isDisplayed()));
                assertTrue(tmProjectRolePage.nonExistingEndDate.getText().equals(message));
            }
        }
        if (field.equals("roleStartDate")) {
            if (message.equals("The start date cannot be in the past")) {
                assertTrue(tmProjectRolePage.lessStartDateError.isDisplayed());
                assertTrue(tmProjectRolePage.lessStartDateError.getText().equals(message));
            }
            if (message.equals("Invalid date format")) {
                assertTrue(tmProjectRolePage.invalidStartDateError.isDisplayed());
                assertTrue(tmProjectRolePage.invalidStartDateError.getText().equals(message));
            }
            if (message.equals("The date entered does not exist. Please enter a valid date.")) {
                assertTrue((tmProjectRolePage.nonExistingStartDate.isDisplayed()));
                assertTrue(tmProjectRolePage.nonExistingStartDate.getText().equals(message));
            }
        }
    }

    @When("I enter the End Date {string} to the Start Start")
    public void i_enter_the_End_Date_to_the_Start_Start(String value) {
        clearAndFillText(tmAddNewUserPage.startDate, getCurrentDate());
        switch (value) {
            case "prior":
                clearAndFillText(tmAddNewUserPage.endDate, getPriorDate(2));
                break;

            case "equal":
                clearAndFillText(tmAddNewUserPage.endDate, getCurrentDate());
                break;

        }
    }

    @Then("I should see a pop-up that user is created successfully")
    public void i_should_see_a_pop_up_that_user_is_created_successfully() {
        waitForVisibility(tmAddNewUserPage.successSnackbar, 10);
        assertTrue(tmAddNewUserPage.successSnackbar.isDisplayed());
        assertTrue(tmAddNewUserPage.get_userCreatedMessage().isDisplayed(), "User created successfully pop-up is not displayed");
        waitForVisibility(searchUser.addUserButton, 10);
        Assert.assertTrue(searchUser.addUserButton.isDisplayed());
    }


    //Refactored 03/14 Vinuta - Added explicit wait
    @Then("I should see error that user is already active")
    public void i_should_see_error_that_user_is_already_active() {
        waitForVisibility(tmAddNewUserPage.errorSnackbar, 5);
        assertTrue(tmAddNewUserPage.errorSnackbar.isDisplayed());
        assertTrue(tmAddNewUserPage.get_duplicateUserError().isDisplayed(), "Duplicate user error is not displayed");
    }

    @Then("I validate the system displays an Error Message: {string}")
    public void verifyTheSystemDisplaysAnErrorMessage(String msg) {
        waitFor(5);
        sa.get().assertThat(isElementDisplayed(tmAddNewUserPage.errorSnackbar)).as("Error message is not displayed").isTrue();
        sa.get().assertThat(Driver.getDriver().findElement(By.xpath("//p[text()='" + msg + "']")).getText().equalsIgnoreCase(msg)).isTrue();
        tmAddNewUserPage.closeButtonOnErrorAlert.click();
    }

    //By Vinuta, TODO when add project contact confusion is resolved
    /*@When("I add project contacts if not added")
    public void i_add_project_contacts_if_not_added() {
        waitFor(5);
        if(tmAddNewUserPage.get_noApproversError().isDisplayed())
            i_click_on_Continue_button_on_User_Details_error_pop_up();
            tm_createProjectStepDef.i_should_be_navigated_to_the_add_project_page();
            tmSaveAProjectStepDef.get().createProjectContactAndSave("Test","T","Test","1234567890","test@test.com");
    }
    */

    @Then("I set Inactive Immediately to Yes")
    public void i_set_Inactive_Immediately_to_Yes() {
        waitForVisibility(tmAddNewUserPage.inactiveImmediatelyCheckbox, 10);
        jsClick(tmAddNewUserPage.inactiveImmediatelyCheckbox);
        //assertTrue(tmAddNewUserPage.inactiveImmediatelyCheckbox.isSelected(), "Inactive Immediately checkbox is not checked");
    }

    @And("I update user Email ID value")
    public void updateEmailId_ExistingRole() {
        waitFor(2);
        scrollToElement(tmAddNewUserPage.addEmail);
        emailID.set(randomEmailId());
        clearAndFillText(tmAddNewUserPage.addEmail, emailID.get());
    }

    @Then("I should see pop-up with message {string}")
    public void i_should_see_pop_up_with_message(String string) {
        waitForVisibility(tmAddNewUserPage.inactivateUserMessage, 5);
        assertTrue(tmAddNewUserPage.inactivateUserMessage.isDisplayed(), string + "error is not displayed");
    }

    @Then("I click on ok button to inactivate user")
    public void i_click_on_ok_button_to_inactivate_user() {
        tmAddNewUserPage.OkButton.click();
    }

    @Then("I verify end date is set to current date & not editable")
    public void i_verify_end_date_is_set_to_current_date_not_editable() {
        waitForVisibility(tmAddNewUserPage.endDate, 5);
        assertTrue(tmAddNewUserPage.verifyNotEditable(tmAddNewUserPage.endDate, "01/02/2025"), "End date cannot be edited");
    }

    @Then("I verify values in account inactivation {string} dropdown")
    public void i_verify_values_in_account_inactivation_dropdown(String string, List<String> options) {
        for (String option : options) {
            selectInactivateReason(tmAddNewUserPage.accInactivationReason, option);
            waitFor(3);
        }

    }

    @Then("I verify values in account reactivation {string} dropdown")
    public void i_verify_values_in_account_reactivation_dropdown(String string, List<String> options) {
        for (String option : options) {
            selectInactivateReason(tmAddNewUserPage.accReactivationReason, option);
            waitFor(3);
        }

    }

    @When("I select value {string} in account reactivation dropdown")
    public void i_select_value_in_account_reactivation_dropdown(String string) {
        selectInactivateReason(tmAddNewUserPage.accReactivationReason, string);
        waitFor(3);
    }

    @Then("I should see message that user has been inactivated successfully")
    public void i_should_see_message_that_user_has_been_inactivated_successfully() {
        waitForVisibility(tmAddNewUserPage.successSnackbar, 10);
        assertTrue(tmAddNewUserPage.successSnackbar.isDisplayed());
        assertTrue(tmAddNewUserPage.inactivateSuccessMessage.isDisplayed(), "User is not inactivated");
        assertTrue(searchUser.addUserButton.isDisplayed());
        waitFor(10);
    }

    @Then("I see the error that End date must be empty to reactivate a user")
    public void i_see_the_error_that_End_date_must_be_empty_to_reactivate_a_user() {
        scrollUpUsingActions(3);
        waitFor(2);
        assertTrue(tmAddNewUserPage.endDateReactivateError.isDisplayed(), "End Date should be blank for reactivation");
    }

    @Then("I nullify the end date")
    public void i_nullify_the_end_date() {
//        tmAddNewUserPage.endDate.clear();
        tmAddNewUserPage.endDateCalendarButton.click();
        tmAddNewUserPage.clearDate.click();
    }

    @Then("I should see message that user has been reactivated successfully")
    public void i_should_see_message_that_user_has_been_reactivated_successfully() {
        waitForVisibility(tmAddNewUserPage.successSnackbar, 10);
        assertTrue(tmAddNewUserPage.successSnackbar.isDisplayed());
        assertTrue(tmAddNewUserPage.reactivateSuccessMessage.isDisplayed(), "User is not inactivated");
        waitFor(8);
    }

    @Then("I click on ok button to reactivate user")
    public void i_click_on_ok_button_to_reactivate_user() {
        tmAddNewUserPage.OkButton.click();
    }

    @And("I select value {string} in account inactivation dropdown")
    public void i_select_value_in_account_inactivation_dropdown(String value) {
        selectDropDown(tmAddNewUserPage.accInactivationReason, value);
        waitFor(3);
    }

    // added to handle If the Project does not have any Project Role Like Account Approver and Account Manager
    @When("I check Account Manager and Approver are added")
    public void i_check_Account_Manager_and_Approver_are_added() {
        if (tmProjectDetailsPage.isApproverAdded()) {
            try {
                tmProjectDetailsPage.editButton.get(0).click();
                waitFor(2);
                clearAndFillText(tmProjectDetailsPage.firstNameList.get(1), getRandomString(8));
                clearAndFillText(tmProjectDetailsPage.lastNameList.get(1), getRandomString(8));
                clearAndFillText(tmProjectDetailsPage.phoneNumList.get(1), getRandomNumber(10));
                tmProjectDetailsPage.checkButton.click();
                waitFor(2);
                tmProjectDetailsPage.editButton.get(1).click();
                waitFor(2);
                clearAndFillText(tmProjectDetailsPage.firstNameList.get(1), getRandomString(8));
                clearAndFillText(tmProjectDetailsPage.lastNameList.get(1), getRandomString(8));
                clearAndFillText(tmProjectDetailsPage.phoneNumList.get(1), getRandomNumber(10));
                tmProjectDetailsPage.checkButton.click();
            } catch (Exception e) {
                System.out.print("Exception accrued :");
                e.printStackTrace();
            }
        } else {
            try {
                selectDropDown(tmProjectDetailsPage.projectRoleDropdown, "Account Approver");
                waitFor(1);
                tmProjectDetailsPage.createRoleFirstName.sendKeys("Test");
                tmProjectDetailsPage.createRoleLastName.sendKeys("Test");
                tmProjectDetailsPage.createRolePhone.sendKeys("2222222222");
                tmProjectDetailsPage.createRoleEmail.sendKeys("test@email.com");
                tmProjectDetailsPage.createRoleSaveButton.click();

                selectDropDown(tmProjectDetailsPage.projectRoleDropdown, "Account Manager");
                waitFor(1);
                tmProjectDetailsPage.createRoleFirstName.sendKeys("Test");
                tmProjectDetailsPage.createRoleLastName.sendKeys("Test");
                tmProjectDetailsPage.createRolePhone.sendKeys("3333333332");
                tmProjectDetailsPage.createRoleEmail.sendKeys("test@email.com");
                tmProjectDetailsPage.createRoleSaveButton.click();
            } catch (Exception e) {
                System.out.print("Exception accrued :");
                e.printStackTrace();
            }

        }
    }

    @When("I inactivate user in project with data {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_inactivate_user_in_project_with_data(String isMaxEmp, String maxID, String fn, String mn, String ln, String email, String startDate, String endDate, String acctType, String acctAuth, String authDate, String overrideAuth, String overrideAuthReason, String phiAccess, String phiReason, String piiAccess, String piiReason, String status) {
        this.i_set_Inactive_Immediately_to_Yes();
        this.i_should_see_pop_up_with_message("The user account will be deactivated when you click the Save button");
        this.i_click_on_ok_button_to_inactivate_user();
        this.i_select_value_in_account_inactivation_dropdown("Resigned");
        this.i_click_on_save_a_user_button();
        this.i_should_see_message_that_user_has_been_inactivated_successfully();
        this.i_click_on_ok_button_to_inactivate_user();
    }

    @When("I add Account Manager and Approver roles to new project")
    public void addAccountApproverAndManagerRoles() {
        selectDropDown(tmProjectDetailsPage.projectRoleDropdown, "Account Approver");
        waitFor(1);
        tmProjectDetailsPage.createRoleFirstName.sendKeys("Test");
        tmProjectDetailsPage.createRoleLastName.sendKeys("Test");
        tmProjectDetailsPage.createRolePhone.sendKeys("2222222222");
        tmProjectDetailsPage.createRoleEmail.sendKeys("test@email.com");
        tmProjectDetailsPage.addRole.click();
        waitFor(5);
        selectDropDown(tmProjectDetailsPage.projectRoleDropdown, "Account Manager");
        waitFor(1);
        tmProjectDetailsPage.createRoleFirstName.sendKeys("Test");
        tmProjectDetailsPage.createRoleLastName.sendKeys("Test");
        tmProjectDetailsPage.createRolePhone.sendKeys("3333333332");
        tmProjectDetailsPage.createRoleEmail.sendKeys("test@email.com");
        tmProjectDetailsPage.addRole.click();
        waitFor(5);
    }

    @When("I create User with given data {string},{string},{string},{string}")
    public void createUser(String isMaxEmp, String maxID, String startDate, String endDate) {

        //If maersk Employee is Yes
        if (isMaxEmp.equalsIgnoreCase("Yes")) {
            tmAddNewUserPage.set_maerskEmp_Status("True");

            //If max ID is not provided, enter random max ID
            if (maxID.isEmpty())
                tmAddNewUserPage.set_maerskEmp_Id(Integer.toString(randomMaxID.get()));
            else
                waitFor(2);
            tmAddNewUserPage.set_maerskEmp_Id(maxID);
            tmAddNewUserPage.addMaxId.click();
            waitFor(5);
            assertFalse(tmAddNewUserPage.addFirstName.getAttribute("value").isEmpty(), "Max ID employee details are not populated");
            firstName.set(tmAddNewUserPage.addFirstName.getAttribute("value"));
            lastName.set(tmAddNewUserPage.addLastName.getAttribute("value"));
            emailID.set(tmAddNewUserPage.addEmail.getAttribute("value"));
        } else {
            //to implement when not max employee
        }
        clearAndFillText(tmAddNewUserPage.startDate, getCurrentDate());
        clearAndFillText(tmAddNewUserPage.endDate, getGreaterDate(10));

    }

    @When("I assign role to User with given data {string},{string}")
    public void assignRole(String roleName, String roleStatus) {
        selectDropDown(tmAddNewUserPage.roleDropDown, roleName);
        waitFor(2);
        if (roleStatus.equalsIgnoreCase("active")) {
            clearAndFillText(tmAddNewUserPage.roleStartDate, getCurrentDate());
            clearAndFillText(tmAddNewUserPage.roleEndDate, getGreaterDate(10));
        } else {
            clearAndFillText(tmAddNewUserPage.roleStartDate, getGreaterDate(10));
            clearAndFillText(tmAddNewUserPage.roleEndDate, getGreaterDate(20));
        }

    }

    @And("I assign role to User with given data {string},{string} with Default Role active")
    public void i_assign_role_to_user_with_given_data_somethingsomething_with_default_role_active(String roleName, String roleStatus) {
        tmAddNewUserPage.defaultRoleTypeOff.click();
        selectDropDown(tmAddNewUserPage.roleDropDown, roleName);
        waitFor(2);
        if (roleStatus.equalsIgnoreCase("Active")) {
            click(tmAddNewUserPage.rolesStartDateCalender);
            click(tmAddNewUserPage.StartDateOkButton);
        }

    }

    @When("I click add role button to assign")
    public void clickAddRoleButton() {
        waitFor(2);
        tmAddNewUserPage.addRolePlusSign.click();

    }

    @When("I click {string} on all changes will be lost warning message")
    public void i_click_on_all_changes_will_be_lost_warning_message(String button) {
        if (button.equalsIgnoreCase("yes")) {
            waitForClickablility(tmAddNewUserPage.warningPopUpContButton, 10);
            tmAddNewUserPage.warningPopUpContButton.click();
        }
    }

    @When("I add Account Manager to the project")
    public void i_add_Account_Manager_to_the_project() {
        selectDropDown(tmProjectDetailsPage.projectRoleDropdown, "Account Manager");
        waitFor(1);
        tmProjectDetailsPage.createRoleFirstName.sendKeys("AccMgr1");
        tmProjectDetailsPage.createRoleLastName.sendKeys("Test");
        tmProjectDetailsPage.createRolePhone.sendKeys("9993452222");
        tmProjectDetailsPage.createRoleEmail.sendKeys("accmgr1@email.com");
        tmProjectDetailsPage.createRoleSaveButton.click();

        waitFor(3);
    }

    @Then("I verify user is not navigated to Project List Page")
    public void i_verify_user_is_not_navigated_to_Project_List_Page() {
        assertTrue(!IsElementDisplayed(tmProjectListPage.projectListDashboard), "Navigated to Project List Page");

    }

    //Author: Vidya 02-03-2020 Method to delete the user if exist
    @Then("I will make sure that user by {string} is not there for this project")
    public void i_verify_user_is_not_navigated_to_Project_List_Page(String maxId) {
        searchUser.searchMaxID.sendKeys(maxId);
        searchUser.searchButton.click();
        waitFor(6);
        try {
            Assert.assertTrue(searchUser.noRecardMsg.isDisplayed());
        } catch (Exception NoSuchElementException) {
            if (searchUser.resultStatuses.get(0).getText().equalsIgnoreCase("INACTIVE")) {
                searchUser.resultCheckBoxes.get(0).click();
                searchUser.discardButton.click();
            } else if (searchUser.resultStatuses.get(0).getText().equalsIgnoreCase("ACTIVE")) {
                searchUser.resultFirstNames.get(0).click();
                selectInactivateReason(tmAddNewUserPage.accInactivationReason, "Change of Job Functions");
                i_set_Inactive_Immediately_to_Yes();
                i_should_see_pop_up_with_message("The user account will be deactivated when you click the Save button");
                i_click_on_ok_button_to_inactivate_user();
                i_click_on_save_a_user_button();
                waitForVisibility(searchUser.discardButton, 10);
                searchUser.searchMaxID.sendKeys(maxId);
                searchUser.searchButton.click();
                Driver.getDriver().findElements(By.xpath("//*[contains(@class ,'table-responsive')]//tr[*]//td[1]")).get(0).click();
                //searchUser.resultCheckBoxes.get(0).click();
                searchUser.discardButton.click();

            }

        }
    }

    //Author: Vidya 02-03-2020
    @And("I click on yes button on warning message for selected role for user")
    public void selectYesBtnOnWarningMsg() {
        waitForVisibility(tmAddNewUserPage.warningMsg, 10);
        Assert.assertTrue(tmAddNewUserPage.warningMsg.isDisplayed());
        tmAddNewUserPage.warningPopUpYesButton.click();
    }

    //Author: Vidya 02-03-2020 M
    @And("I click on No button on warning message for selected role for user")
    public void selectNoBtnOnWarningMsg() {
        waitForVisibility(tmAddNewUserPage.warningMsg, 10);
        Assert.assertTrue(tmAddNewUserPage.warningMsg.isDisplayed());
        tmAddNewUserPage.warningPopUpNoButton.click();
    }

    //Author: Vidya 02-03-2020 M
    @And("I verify user is on same page and see all previously entered unsaved data")
    public void verifyUserIsOnSamePageAndSeeAllPreviouslyEnterUnSavedData() {
        assertEquals(tmAddNewUserPage.addFirstName.getAttribute("value"), firstName.get(), "First Name did not remain the same");
        assertEquals(tmAddNewUserPage.addLastName.getAttribute("value"), lastName.get(), "Last Name did not remain the same");
        assertEquals(tmAddNewUserPage.addEmail.getAttribute("value"), emailID.get(), "Email did not remain the same");
    }


    @Then("I entering FirstName and StartDate")
    public void iEnteringFirstNameAndStartDate() {
        synchronized (firstName) {
            firstName.set(getRandomString(5));
        }
        synchronized (startDate) {
            startDate.set(getCurrentDateWithFormat());
        }
        System.out.println("First Name is : " + firstName.get());
        clearAndFillText(tmAddNewUserPage.addFirstName, firstName.get());
        clearAndFillText(tmAddNewUserPage.startDate, startDate.get());
    }

    @When("I select Account Type as System")
    public void selectAccountType() {
        waitFor(1);
        selectDropDown(tmAddNewUserPage.accountType, "System");
        waitFor(2);
    }

    @When("I select Account Type as Individual Non-maersk")
    public void selectAccountTypeIndividualNonmaersk() {
        scrollUpUsingActions(2);
        selectDropDown(tmAddNewUserPage.accountType, "Individual - Non-maersk");
        waitFor(2);
    }


    @Then("Verify when Account Type is System, other fields are not editable")
    public void verifyWhenAccountTypeisSystemSavingTheRoleOtherFieldsNotEditable() {
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.projectName, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.maerskEmpCheckbox, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.maerskEmpIdTextbox, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.addLastName, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.addEmail, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.endDate, "disabled"));
        assertEquals(tmAddNewUserPage.applicationType.getAttribute("aria-disabled"), "true");
        assertEquals(tmAddNewUserPage.disabledAcctAuthType.getAttribute("aria-disabled"), "true");
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.authDate, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.authCheckbox, "disabled"));
        assertEquals(tmAddNewUserPage.authReasonType.getAttribute("aria-disabled"), "true");
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.isAccessToPHI, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.isAccessToPII, "disabled"));
        assertEquals(tmAddNewUserPage.PHIReasonTypeOff.getAttribute("aria-disabled"), "true");
        assertEquals(tmAddNewUserPage.PIIReasonTypeOff.getAttribute("aria-disabled"), "true");
        assertEquals(tmAddNewUserPage.defaultRoleTypeOff.getAttribute("aria-disabled"), "true");
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.roleDisc, "disabled"));
        assertTrue(isAttribtueAvailable(tmAddNewUserPage.roleEndDate, "disabled"));
        waitFor(1);
    }

    @Then("I validate the system displays an Warning Message: {string}")
    public void verifyTheSystemDisplaysAnWarningMessage(String msg) {
        waitForVisibility(tmAddNewUserPage.warningPopUpContButton, 3);
        assertTrue(tmAddNewUserPage.warningPopUpContButton.isDisplayed());
        assertEquals(msg, Driver.getDriver().findElement(By.xpath("//p[text()='" + msg + "']")).getText());
        tmAddNewUserPage.warningPopUpContButton.click();
    }

    @Then("I verify user role mandatory error is displayed")
    public void verifyErrorMessageThatUserRoleMandatory() {
        assertTrue(tmAddNewUserPage.errorSnackbar.isDisplayed());
        assertTrue(Driver.getDriver().findElement(By.xpath("//*[text()='User must be assigned an active default user role']")).isDisplayed());
    }


}
