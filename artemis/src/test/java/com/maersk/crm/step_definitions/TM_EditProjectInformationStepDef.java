package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.Assert.*;

public class TM_EditProjectInformationStepDef extends TMUtilities {


    private TMProjectListPage tmProjectListPage = new TMProjectListPage();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    private TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMProjectDetailsPage TMProjectDetailsPage =new TMProjectDetailsPage();
    TM_SearchProjectStepDefs tmSearchProject=new TM_SearchProjectStepDefs();
    //private JavascriptExecutor js = (JavascriptExecutor) driver;
    private List<String> currentProjectInfo = new ArrayList<String>();
    private List<String> returnProjectInfo = new ArrayList<String>();
    static int currentProjIndex;
    private List<List<String>> allOriginalRowsOfContact = new ArrayList<>();
    Select dropdown;
    String currentProjectName;
    public static String currentProjectId;
    String prevProjName;
    String updProjName;
    public static String TimeZoneValue = "Alaska";
    int projectSelectIndex=0;

    @When("I expend a Project to view the details")
    public void i_expend_a_Project_to_view_the_details() {

        // TODO Change Currents index to random after bug fixed
        waitFor(3);
        currentProjIndex = randomNumberBetweenTwoNumbers(0, tmProjectListPage.projects.size() - 1);
//for (int i=0; i<tmProjectListPage.projects.size(); i++){
        hover(tmProjectListPage.projects.get(currentProjIndex));
        waitFor(2);
        currentProjectInfo = tmProjectListPage.projectInfo(currentProjIndex);
// Click on Expend Button on Project
        tmProjectListPage.expendProject(tmProjectListPage.projects.get(currentProjIndex));
        assertTrue(tmProjectDetailsPage.viewProjectDashboard.isDisplayed());
        currentProjectName = tmProjectDetailsPage.getCurrentProjectName();
        currentProjectId = currentProjectInfo.get(3);

    }


    @When("I expend the Project to view the details")
    public void i_expend_the_Project_to_view_the_details() {


        // Click on Expend Button on Project
        tmProjectListPage.expendProject(tmProjectListPage.projects.get(currentProjIndex));
        assertTrue(tmProjectDetailsPage.viewProjectDashboard.isDisplayed());
        waitFor(3);
        currentProjectName = tmProjectDetailsPage.getCurrentProjectName();
    }
    @When("I expand the project {string} to view the details")
    public void i_expand_the_project_something_to_view_the_details(String projName) {

        for (int i=0;i<tmProjectListPage.projectNamesList.size();i++)
        {
            if(tmProjectListPage.projectNamesList.get(i).getText().equalsIgnoreCase(projName))
            {
                projectSelectIndex=i;
                break;
            }
        }
        tmProjectListPage.expendProject(tmProjectListPage.projects.get(projectSelectIndex));
    }

    @When("I expand a random project to view the details")
    public void i_expand_a_random_project_to_view_the_details() {

        waitFor(3);
        currentProjIndex = randomNumberBetweenTwoNumbers(0, tmProjectListPage.projects.size() - 1);
        //for (int i=0; i<tmProjectListPage.projects.size(); i++){
        hover(tmProjectListPage.projects.get(currentProjIndex));
        waitFor(2);
        currentProjectInfo = tmProjectListPage.projectInfo(currentProjIndex);
        // Click on Expend Button on Project
        tmProjectListPage.expendProject(tmProjectListPage.projects.get(currentProjIndex));
        assertTrue(tmProjectDetailsPage.viewProjectDashboard.isDisplayed());
        currentProjectName = tmProjectDetailsPage.getCurrentProjectName();
        currentProjectId = currentProjectInfo.get(3);
    }

    @When("I expand first project to view the details")
    public void i_expand_first_project_to_view_the_details() {
        waitFor(3);
//        currentProjIndex = randomNumberBetweenTwoNumbers(0, tmProjectListPage.projects.size() - 1);
        //for (int i=0; i<tmProjectListPage.projects.size(); i++){
        hover(tmProjectListPage.projects.get(0));
        waitFor(2);
        currentProjectInfo = tmProjectListPage.projectInfo(0);
        // Click on Expend Button on Project
        tmProjectListPage.expendProject(tmProjectListPage.projects.get(0));
        assertTrue(tmProjectDetailsPage.viewProjectDashboard.isDisplayed());
        currentProjectName = tmProjectDetailsPage.getCurrentProjectName();
        currentProjectId = currentProjectInfo.get(3);
    }

    //refactorBy: Vidya Date:1-31-2020 Changed the code according into CP-1722
    @When("I edit and save the project {string} with {string} one at the time")
    public void i_edit_and_save_the_project_with_one_at_the_time(String field, String value) {
        waitFor(1);
        prevProjName = tmProjectDetailsPage.projectName.getAttribute("value");
        swichMetodForEditProjectPage(field, value);
        waitFor(2);
        if(tmProjectDetailsPage.timezonefield.getText().equals("") ||
                tmProjectDetailsPage.timezonefield.getText()==null||tmProjectDetailsPage.timezonefield.getText().equals(" ")){
            selectDropDown(tmProjectDetailsPage.timezonefield,"Pacific");
            waitFor(1);
        }
        highLightElement(tmProjectDetailsPage.saveButton);
        tmProjectDetailsPage.saveButton.click();
        waitFor(2);
    }
    @Then("I confirm the project naming {string} got updated with {string} {string}")
    public void i_confirm_the_project_naming_got_updated_with_values( String projName, String detail, String value) {
        for (int i=0;i<tmProjectListPage.projectNamesList.size();i++) {
            if (tmProjectListPage.projectNamesList.get(i).getText().equalsIgnoreCase(projName)) {
                tmProjectListPage.expendProject(tmProjectListPage.projects.get(projectSelectIndex));
                break;
            }
            }
        waitFor(3);
            switch (detail) {
                case "project_Name":
                    assertEquals(tmProjectDetailsPage.projectName.getText(),value);
                    break;
                case "state":
                    assertEquals(tmProjectDetailsPage.state.getText(),updatedValue.get());
                    break;
                case "program_name":
                    assertEquals(tmProjectDetailsPage.programName.getText(),updatedValue.get());
                    break;
                case "contract_id":
                    assertEquals(tmProjectDetailsPage.contractId.getText(),updatedValue.get());
                    break;
                case "client_agency":
                    assertEquals(tmProjectDetailsPage.stateAgencyName.getText(),updatedValue.get());
                    break;
                case "pro_status":
                    assertEquals(tmProjectDetailsPage.provisioningStatus.getText(),statusValue.get());
                    break;
                case "start_date":
                    assertEquals(tmProjectDetailsPage.editStartDate.getAttribute("value").replace("/",""),priorDate.get());
                    break;
                case "end_date":
                    assertEquals(tmProjectDetailsPage.editEndDate.getAttribute("value").replace("/",""),pastDate.get());
                    break;

            }
    }

    @When("I confirm the project {string} {string}is updated")
    public void i_confirm_the_project_is_updated(String field, String value) {
        returnProjectInfo = tmProjectListPage.projectInfo(currentProjIndex);
        waitFor ( 5 );
        System.out.println(returnProjectInfo);
        System.out.println(getPriorDate(12));
                if (field.equals("start_date")) {
            assertTrue(returnProjectInfo.get(7).replace("/", "").contains(getPriorDate(12)));
        }else if (field.equals("end_date")) {
            assertTrue(returnProjectInfo.get(8).replace("/", "").contains(getGreaterDate(1)));
        } else {
                    if(value==null){
                        value = getRandomString ( 6 );
                        System.out.println ( "Printing Value  0:"+value );
                        Assert.assertTrue(returnProjectInfo.contains(value), "Didn't update field " + field + " with value " + value);
                    }
                    System.out.println ( "Printing Value 1:"+value+"   Space  "+returnProjectInfo );
            Assert.assertTrue(returnProjectInfo.contains(value), "Didn't update field " + field + " with value " + value);
        }
    }

        @Then("I change it back to previous value for {string}")
        public void i_change_it_back_to_previous_value_for (String field){
            waitFor(1);
            tmProjectListPage.expendProject(tmProjectListPage.projects.get(currentProjIndex));

            switch (field) {
                case "project_name":
                    System.out.println(prevProjName);
                    tmProjectDetailsPage.projectName.clear();
                    tmProjectDetailsPage.projectName.sendKeys(prevProjName);
                    //clearAndFillText(tmProjectDetailsPage.editProjectName, currentProjectInfo.get(0));
                    break;
                case "program_name":
                    clearAndFillText(tmProjectDetailsPage.editProgramName, currentProjectInfo.get(5));
                    break;
                case "contract_id":
                    clearAndFillText(tmProjectDetailsPage.editContractId, currentProjectInfo.get(2));
                    break;
                case "client_agency":
                    clearAndFillText(tmProjectDetailsPage.editStateAgencyName, currentProjectInfo.get(6));
                    break;
                case "start_date":
                    clearAndFillText(tmProjectDetailsPage.editStartDate, currentProjectInfo.get(7));
                    break;
                case "end_date":
                    clearAndFillText(tmProjectDetailsPage.editEndDate, currentProjectInfo.get(8));
                    break;
                case "state":
                    selectDropDown(tmProjectDetailsPage.editState, currentProjectInfo.get(1));
                    break;
                case "pro_status":
                    selectDropDown(tmProjectDetailsPage.editProvStatus, currentProjectInfo.get(4));
                    break;
                case "time_zone":
                    selectDropDown(TMProjectDetailsPage.timezonefield,TimeZoneValue);
                    break;
            }
            tmProjectDetailsPage.clickSaveButton();

            returnProjectInfo = tmProjectListPage.projectInfo(currentProjIndex);
            waitFor(2);

            //Assert.assertTrue(currentProjectInfo.containsAll(returnProjectInfo), "Restore Project details back failed");


        }

        @Then("I should see the project details updated by example")
        public void i_should_see_the_project_details_updated_by_example (List < String > exampleInfo) {
            // Method should accept data from feature file.
            // Assertions from UI project and feature file project details for NEW Just entered data.
            tmProjectDetailsPage.saveButton.click();
            List<String> editedProject = tmProjectListPage.projectInfo(0);
            System.out.println(editedProject);
            System.out.println(exampleInfo);
            // Assert.assertTrue(editedProject.equals(exampleInfo), "not equals"); //exp how to compare

        }


        @Then("I edit all the project details at one step")
        public void i_edit_all_the_project_details_at_one_step () {

            // TODO replace project info from List "currentProjectInfo" to UI.
            // TODO add saved info to the List "returnProjectInfo"
            tmProjectListPage.expendProject(tmProjectListPage.projects.get(0));

            tmProjectDetailsPage.editProjectName.clear();
//        timer

            tmProjectDetailsPage.editProjectName.sendKeys(currentProjectInfo.get(0));
            /// .....

            tmProjectDetailsPage.saveButton.click();
//        returnProjectInfo = tmListOfProjectsPage.projectInfo(workingProjNumber);
//        System.out.println(returnProjectInfo);
//        Assert.assertTrue(currentProjectInfo.equals(returnProjectInfo), "not equals"); //exp how to compare
        }

        //Refactored by Vinuta 01/04/2019
        @When("I edit but don't save the project {string} with {string} one at the time")
        public void i_edit_but_don_t_save_the_project_with_one_at_the_time (String field, String value){
            /* update project info on IU..... Method accepts data from feature file.
             */
            swichMetodForEditProjectPage(field, value);
            tmProjectDetailsPage.clickBackButton();
            waitFor(2);
            tmProjectDetailsPage.warningMsgCntBtn.click();
            waitFor(1);
            waitForVisibility(tmListOfProjectsPage.arrowClick, 5);

        }

        @When("I confirm the project {string} {string} is not updated")
        public void i_confirm_the_project_is_not_updated (String field, String value){
            returnProjectInfo = tmProjectListPage.projectInfo(currentProjIndex);
            // TODO Add logic for Pending status, if project has same ProvStatus as Example
            assertFalse(returnProjectInfo.contains(value), field + " field was updated by value " + value);

        }

        @When("I edit each role with new project contact details and see updates")
        public void i_edit_each_role_with_new_project_contact_details_and_see_updates () {
            // TODO change to random dependency
            List<String> tempInfo = new ArrayList<>();
            tempInfo.add("Contact");
            tempInfo.add("Smith");
            tempInfo.add("D");
            tempInfo.add("Hunt");
            tempInfo.add("4045054545");
            tempInfo.add("test@gmail.com");

            //TODO add : if projectContactRows is 0 try again.
            // some projects do not have any contacts persons. and test just pass.

           // for (int i = 1; i < tmProjectDetailsPage.projectContactRows.size(); i++) {

                WebElement currentRow = tmProjectDetailsPage.projectContactRows.get(0);

                TMUtilities tmUtilities = new TMUtilities();
                List<String> currentOriginalRowInfo = tmUtilities.getContactInfo(currentRow);

                while (currentOriginalRowInfo.get(4).equals("")) {
                    currentOriginalRowInfo = tmUtilities.getContactInfo(currentRow);
                }
                allOriginalRowsOfContact.add(currentOriginalRowInfo);

                currentRow.findElement(By.cssSelector(tmProjectDetailsPage.contactEditButton)).click();
                updateContactInfo(currentRow, tempInfo);
                currentRow.findElement(By.cssSelector(tmProjectDetailsPage.contactEditButton)).click();
                waitFor(5);
                System.out.println(tmUtilities.getContactInfo(tmProjectDetailsPage.projectContactRows.get(0))+"=="+tempInfo);
                Assert.assertEquals(tmUtilities.getContactInfo(tmProjectDetailsPage.projectContactRows.get(0)), tempInfo,
                        "New Contact Information weren't save correctly");
            //}
        }

        @Then("I change each role to previous value")
        public void i_change_each_role_to_previous_value () {
            for (int i = 1; i < tmProjectDetailsPage.projectContactRows.size(); i++) {

                WebElement currentRow = tmProjectDetailsPage.projectContactRows.get(i);
                currentRow.findElement(By.cssSelector(tmProjectDetailsPage.contactEditButton)).click();
                updateContactInfo(currentRow, allOriginalRowsOfContact.get(i - 1));
                currentRow.findElement(By.cssSelector(tmProjectDetailsPage.contactEditButton)).click();
                Assert.assertEquals(getContactInfo(tmProjectDetailsPage.projectContactRows.get(i)), allOriginalRowsOfContact.get(i - 1),
                        "Restore Contact Information weren't save correctly");
            }
        }

 /*Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date is less than Contract Start date */
    @When("I enter Go-Live Date less than Contract start date")
    public void enterGoLiveDateLessThanContractStartDateEditproject() {
        clearAndFillText(TMProjectDetailsPage.contractStartDate, getCurrentDateWithFormat());
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(1));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getPriorDate(10));
        TMProjectDetailsPage.saveButton.click();
    }

    /* Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date is equal to Contract Start date */
    @When("I enter Go-Live Date equal to Contract start date project updated sucessfully")
    public void enterGoLiveDateEqualToContractStartDateEditproject() {
        clearAndFillText(TMProjectDetailsPage.contractStartDate, getCurrentDateWithFormat());
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(1));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getCurrentDateWithFormat());
        TMProjectDetailsPage.saveButton.click();
        Assert.assertTrue(TMProjectDetailsPage.projSuccessStatus.isDisplayed(),"Project Success status is not displayed ");

    }

    @And("I update Go-Live Date to next year")
    public void i_update_golive_date_to_next_year() {
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getGreaterDate(365));
        TMProjectDetailsPage.saveButton.click();
    }

    /* Author -Paramita 11/12/2019 - Script for GO-LIVE-DATE when Go-Live Date is less than Contract End date */
    @When("I enter Go-Live Date less than Contract end date")
    public void enterGoLiveDateLessThanContractEndDateEditproject() {
        clearAndFillText(TMProjectDetailsPage.contractStartDate, getCurrentDateWithFormat());
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(10));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getGreaterDate(365));
        TMProjectDetailsPage.saveButton.click();
    }


    @Then("I should see system throw an Error message when less than Contract Start Date")
        public void errorMessageDisplayGoLiveDateLessThanStartDateEditProject()
        {
            String str="Go-Live date cannot be before"+" "+getCurrentDate();
            Assert.assertTrue(TMProjectDetailsPage.goLiveDateErrorMessage.getText().contains(str),"Error message is no displayed");
        }

    @Then("I should see system throw an Error message when less than Contract End Date")
    public void errorMessageDisplayGoLiveDateLessThanEndDateEditProject()
    {
        String str1="Go-Live date cannot be after"+" "+getGreaterDateFormatMMddyyyy(10);
        Assert.assertTrue(TMProjectDetailsPage.goLiveDateErrorMessage.getText().contains(str1),"Error message is no displayed");
    }

    /* Author -paramita
     * This method to validate error message when project is created with Go-Live Date less than Contract Start Date
     * */
    public void addGoLiveDateAndSaveLessThanContractStartDate(String projectName, String programName, String contractId, String clientName, String startDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        waitForVisibility(TMProjectDetailsPage.projectName, 2);
        TMProjectDetailsPage.state.click();
        waitForVisibility(TMProjectDetailsPage.stateoption, 5);
        TMProjectDetailsPage.stateoption.click();

      waitFor(2);
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        waitForVisibility(TMProjectDetailsPage.contractId, 5);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);

        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, getCurrentDate());
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(1));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getPriorDate(10));
        TMProjectDetailsPage.provisioningStatus.click();
        waitForVisibility(TMProjectDetailsPage.activeStatus, 2);
        TMProjectDetailsPage.activeStatus.click();
        waitFor(2);
        TMProjectDetailsPage.buttonSave.click();
        waitFor(2);

    }

    /* Author -paramita
     * This method to validate error message when project is created with Go-Live Date less than Contract End Date
     * */
    public void addGoLiveDateAndSaveLessThanContractEndDate(String projectName, String programName, String contractId, String clientName, String startDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        waitForVisibility(TMProjectDetailsPage.projectName, 2);
        TMProjectDetailsPage.state.click();
        waitForVisibility(TMProjectDetailsPage.stateoption, 5);
        TMProjectDetailsPage.stateoption.click();
       waitFor(2);
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        waitForVisibility(TMProjectDetailsPage.contractId, 5);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);

        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(10));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getGreaterDate(11));
        TMProjectDetailsPage.provisioningStatus.click();
        waitForVisibility(TMProjectDetailsPage.activeStatus, 2);
        TMProjectDetailsPage.activeStatus.click();
        waitFor(2);
        TMProjectDetailsPage.saveButton.click();
        waitFor(2);
    }


    /* Author -paramita
     * This method to validate error message when project is created with Go-Live Date but no Contract Start Date
     * */
    public void addGoLiveDateWithoutContractStartDate(String projectName, String programName, String contractId, String clientName, String startDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        waitForVisibility(TMProjectDetailsPage.projectName, 2);
        TMProjectDetailsPage.state.click();
        waitForVisibility(TMProjectDetailsPage.stateoption, 5);
        TMProjectDetailsPage.stateoption.click();
        waitFor(2);
        clearAndFillText(TMProjectDetailsPage.programName, programName);

        waitForVisibility(TMProjectDetailsPage.contractId, 5);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);

        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getCurrentDateWithFormat());
        TMProjectDetailsPage.provisioningStatus.click();
        waitForVisibility(TMProjectDetailsPage.activeStatus, 2);
        TMProjectDetailsPage.activeStatus.click();
        waitFor(2);
        TMProjectDetailsPage.buttonSave.click();
        waitFor(2);
    }


    /* Author -paramita
     * This method to create project with Go-Live Date greater than Contract Start Date
     * */
    public void addGoLiveDateAndSaveProjectEqualContratStartDate(String projectName, String programName, String contractId, String clientName, String startDate)
    {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        waitForVisibility(TMProjectDetailsPage.projectName, 2);
        TMProjectDetailsPage.state.click();
        waitForVisibility(TMProjectDetailsPage.stateoption, 5);
        TMProjectDetailsPage.stateoption.click();
      waitFor(2);
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        waitForVisibility(TMProjectDetailsPage.contractId, 5);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);

        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(TMProjectDetailsPage.contractEndDate, getGreaterDate(1));
        clearAndFillText(TMProjectDetailsPage.goLiveDate, getCurrentDateWithFormat());
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        waitForVisibility(TMProjectDetailsPage.activeStatus, 2);
        TMProjectDetailsPage.activeStatus.click();
        selectDropDown(TMProjectDetailsPage.timezonefield,TimeZoneValue);
        TMProjectDetailsPage.buttonSave.click();
    }

    /* Author -Paramita script to edit and save project to develop CP-1070 */
    @When("I edit and save the project {string} with {string}")
    public void edit_and_save_the_project_one_at_the_time(String field, String value) {
        swichMetodForEditProjectPage(field, value);
        waitForVisibility(tmProjectDetailsPage.saveButton, 2);
        tmProjectDetailsPage.saveButton.click();
    }

    @Then("I update tenant manger {string} field with value {string}")
    public void i_update_field_with_value (String field,String value){
        waitFor(5);
        String current_timezone = TMProjectDetailsPage.timezonefield.getText();
        if(!current_timezone.equalsIgnoreCase(value)) {
            switch (field) {
                case "time_zone":
                    selectDropDown(TMProjectDetailsPage.timezonefield, value);
                    break;
            }
            tmProjectDetailsPage.clickSaveButton();
            waitForVisibility(TMProjectDetailsPage.projSuccessStatus, 5);
            Assert.assertTrue(TMProjectDetailsPage.projSuccessStatus.isDisplayed(), "Project Success status is not displayed ");
        }
    }



}







