package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TM_CreateProjectStepDef extends TMUtilities {


    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();

    public final ThreadLocal<String> userNamedata = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    public final ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));

    public final ThreadLocal<String> projectName = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Project", "Valid", "projectName"));
    public final ThreadLocal<String> programName = ThreadLocal.withInitial(() -> getCellValueBySheetRowAndColoumn("Project", "Valid", "programName"));

    public final ThreadLocal<Map<String, String>> projContact = ThreadLocal.withInitial(HashMap::new);
    final ThreadLocal<String> temp = ThreadLocal.withInitial(String::new);

    @Given("I logged into Tenant Manager Project list page")
    public void i_logged_into_Tenant_Manager_Project_list_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("tmPageURL"));
        tmLogin(userNamedata.get(), password.get());
        waitFor(2);
    }


    //Todo check with TM_SaveAProjectStepDef line 48
    //todo uncomment after DEMO
    @When("I click on Create a New Project")
    public void i_click_on_create_a_new_project() {
        highLightElement(tmListOfProjectsPage.createProjectButton);
        staticWait(100);
        tmListOfProjectsPage.createProjectButton.click();

    }

    @Then("I should be navigated to the Add Project Page")
    public void i_should_be_navigated_to_the_add_project_page() {
        Assert.assertTrue(tmProjectDetailsPage.addProjectLogo.isDisplayed());
        highLightElement(tmProjectDetailsPage.addProjectLogo);

    }

//    /*following step is using and duplicating an existing Step/s
//     * the original feature line/s should be used*/
//    @Given("I am on the Home Page")
//    public void i_am_on_the_home_page(){
//        i_logged_into_Tenant_Manager_Project_list_page();
//
//    }
    /*following step is using and duplicating an existing Step/s
     * the original feature line/s should to be used*/
//    @When("I click on the Create new Project")
//    public void i_click_on_the_create_new_project(){
//
//        i_click_on_create_a_new_project();
//    }

    //Refactored 1-31-2020 Vidya As per strory CP-1722
    @Then("I should see all the elements displayed in the Project Contact Page")
    public void i_should_see_all_the_elements_displayed_in_the_project_contact_page() {
        staticWait(100);
        Assert.assertTrue(tmProjectDetailsPage.addProjectLogo.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.projectName.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.state.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.programName.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.contractId.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.stateAgencyName.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.contractStartDate.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.contractEndDate.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.provisioningStatus.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.saveButton.isDisplayed());
        Assert.assertTrue(tmProjectDetailsPage.timezonefield.isDisplayed());

    }

    /* *//*following step is using and duplicating an existing Step/s
     * the original feature line/s need/s to be used*//*
    @Given("I am on the Project Contact Details Page")
    public void i_am_on_the_project_contact_details_page(){
        i_logged_into_Tenant_Manager_Project_list_page();
        i_click_on_create_a_new_project();

    }*/

    /* *//*following step is using and duplicating an existing Step/s
     * the original feature line/s need/s to be used*//*
    @When("I enter all the Details and save the Project")
    public void i_enter_all_the_details_and_save_the_project(){
        staticWait(100);
        browserUtils.createAndSave(projectName,programName,contractId,clientAgency);
        BrowserUtils

    }*/

    @And("I search for the project in Home Page")
    public void i_search_for_the_project_in_the_home_page() {
        staticWait(3000);
        search(projectName.get(), programName.get());
        selectSearchResults();
        staticWait(100);

    }

    @Then("I should be navigated to the Home Page")
    public void i_should_be_navigated_to_the_home_page() {
        Assert.assertTrue(tmListOfProjectsPage.projectList.isDisplayed());
        highLightElement(tmListOfProjectsPage.projectList);

    }

    //By Vinuta, Method to click on User List option on left navigation menu on Project Details Page
    @When("I click on User List Menu")
    public void i_click_on_User_List_Menu() {
        Assert.assertTrue(tmProjectDetailsPage.viewUserList.isDisplayed());
        tmProjectDetailsPage.viewUserList.click();
    }

    //By Vinuta, Method to click on Role List option on left navigation menu on Project Details Page
    @When("I click on Role List Menu")
    public void i_click_on_Role_List_Menu() {
        Assert.assertTrue(tmProjectDetailsPage.viewRoleList.isDisplayed());
        tmProjectDetailsPage.viewRoleList.click();
    }

    @Then("I verify {string} button is displayed & click on it")
    public void i_verify_button_displayed_and_click(String button) {
        waitFor(2);
        Assert.assertTrue(tmProjectDetailsPage.addContactDetailsButton.isDisplayed());
        hover(tmProjectDetailsPage.addContactDetailsButton);
        tmProjectDetailsPage.addContactDetailsButton.click();
    }

    @Then("I verify the {string} button is displayed")
    public void i_verify_button_displayed(String button) {
        waitFor(2);
        Assert.assertTrue(tmProjectDetailsPage.addContactDetailsButton.isDisplayed());
    }

    @When("I can enter project contact details")
    public void i_enter_project_contact_details(Map<String, String> data) {
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "ROLE":
                    selectDropDown(tmProjectDetailsPage.projectRoleDropdown, data.get("ROLE"));
                    waitFor(1);
                    projContact.get().put("ROLE", data.get("ROLE"));
                    break;
                case "FIRST NAME":
                    if ("RANDOM 10".equals(data.get("FIRST NAME"))) {
                        projContact.get().put("FIRST NAME", getRandomString(10));
                        clearAndFillText2(tmProjectDetailsPage.createRoleFirstName, projContact.get().get("FIRST NAME"));
                    }
                    break;
                case "LAST NAME":
                    if ("RANDOM 10".equals(data.get("LAST NAME"))) {
                        projContact.get().put("LAST NAME", getRandomString(10));
                        clearAndFillText2(tmProjectDetailsPage.createRoleLastName, projContact.get().get("LAST NAME"));
                    }
                    break;
                case "MIDDLE NAME":
                    if ("RANDOM 1".equals(data.get("MIDDLE NAME"))) {
                        projContact.get().put("MIDDLE NAME", getRandomString(1));
                        clearAndFillText2(tmProjectDetailsPage.createRoleMiddleName, projContact.get().get("MIDDLE NAME"));
                    }
                    break;
                case "PHONE NUMBER":
                    if ("RANDOM 10".equals(data.get("PHONE NUMBER"))) {
                        projContact.get().put("PHONE NUMBER", getRandomNumber(10));
                        clearAndFillText(tmProjectDetailsPage.createRolePhone, projContact.get().get("PHONE NUMBER"));
                    }
                    break;
                case "EMAIL":
                    if ("RANDOM EMAIL".equals(data.get("EMAIL"))) {
                        projContact.get().put("EMAIL", randomEmailId());
                        clearAndFillText2(tmProjectDetailsPage.createRoleEmail, projContact.get().get("EMAIL"));
                    } else if ("DUPLICATE EMAIL".equals(data.get("EMAIL"))) {
                        clearAndFillText2(tmProjectDetailsPage.createRoleEmail, projContact.get().get("EMAIL"));
                    } else {
                        clearAndFillText(tmProjectDetailsPage.createRoleEmail, data.get("EMAIL"));
                        projContact.get().put("EMAIL", data.get("EMAIL"));
                    }
                    break;
            }
        }
    }

    @Then("I verify if email ID can be duplicated or not")
    public void i_verify_if_email_id_can_be_duplicated_or_not() throws Throwable {
        temp.set(tmProjectDetailsPage.emailIDoffirstRecord.getText());
        tmProjectDetailsPage.secondEditButton.click();
        waitFor(1);
        clearAndFillText(tmProjectDetailsPage.emailIDofSecondRecord, temp.get());
        tmProjectDetailsPage.checkButton.click();
        waitFor(1);
    }

    @Then("I verify error message displayed with trace id")
    public void i_verify_error_message_with_trace_id() throws Throwable {
        waitFor(1);
        Assert.assertTrue(tmProjectDetailsPage.errorSnackBarTitle.isDisplayed());
        WebElement errorMessage = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'Email " + temp.get() + " already exists | traceId:')]"));
        Assert.assertTrue(errorMessage.getText().contains("Email " + temp.get() + " already exists | traceId:"));
    }

    @And("I verify project contact details fields are displayed")
    public void i_verify_project_contact_details_fields_are_displayed() {
        Assert.assertTrue(tmProjectDetailsPage.projectRoleDropdown.isEnabled());
        Assert.assertTrue(tmProjectDetailsPage.createRoleFirstName.isEnabled());
        Assert.assertTrue(tmProjectDetailsPage.createRoleMiddleName.isEnabled());
        Assert.assertTrue(tmProjectDetailsPage.createRoleLastName.isEnabled());
        Assert.assertTrue(tmProjectDetailsPage.createRolePhone.isEnabled());
        Assert.assertTrue(tmProjectDetailsPage.createRoleEmail.isEnabled());
    }

    @Then("I verify I can delete the project contact details row before save")
    public void i_verify_i_can_delete_the_project_contact_details_row_before_save() {
        Assert.assertTrue(tmProjectDetailsPage.clearbutton.isDisplayed());
        tmProjectDetailsPage.clearbutton.click();
        Assert.assertTrue(tmProjectDetailsPage.noProjectContacts.isDisplayed());
    }

    @Then("I verify the project contact detail values are saved")
    public void i_verify_the_project_contact_detail_values_are_saved() {
        List<String> projContactLabels = Arrays.asList(new String[]{"ROLE", "FIRST NAME", "MIDDLE NAME", "LAST NAME", "PHONE NUMBER", "EMAIL"});
        int i = 0;
        waitFor(3);
        for (String element : projContactLabels) {
            Assert.assertTrue(tmProjectDetailsPage.colvalues.get(i).getText().equals(element));
            Assert.assertTrue(projContact.get().get(element).equalsIgnoreCase(tmProjectDetailsPage.projContactTableColValues.get(i).getText()));
            i++;
        }

    }

    @And("I validate error message for duplicate emailid in add contact page")
    public void iValidateErrorMessageInAddContacctPage() {
        String expectedEmailId = projContact.get().get("EMAIL");
        waitForVisibility(tmProjectDetailsPage.errorSnackBarTitle, 10);
        Assert.assertTrue(tmProjectDetailsPage.errorSnackBarTitle.isDisplayed());
        WebElement errorMessage = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'Email " + expectedEmailId + " already exists | traceId:')]"));
        Assert.assertTrue(errorMessage.getText().contains("Email " + expectedEmailId + " already exists | traceId:"));
    }

}