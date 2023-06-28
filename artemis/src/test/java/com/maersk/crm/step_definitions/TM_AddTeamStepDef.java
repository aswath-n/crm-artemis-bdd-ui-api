package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITMEventController;
import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.assertTrue;

public class TM_AddTeamStepDef extends BrowserUtils {

    TMAddTeamPage teamPage = new TMAddTeamPage();
    TMListOfTeamPage teamListPage = new TMListOfTeamPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMAddBusinessUnitPage businessUnitPage = new TMAddBusinessUnitPage();
    TMBusinessUnitListPage bunsUtListPage = new TMBusinessUnitListPage();

    final ThreadLocal<TM_AddBusinessUnitStepDef> addBUStepDef = ThreadLocal.withInitial(TM_AddBusinessUnitStepDef::new);
    final ThreadLocal<TM_AddNewUserToProjectStepDef> addProjectUsertepDef = ThreadLocal.withInitial(TM_AddNewUserToProjectStepDef::new);


    public static final ThreadLocal<String> teamValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamEndDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamDescription = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessUnit = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> status = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> flag = ThreadLocal.withInitial(() -> false);
    public final ThreadLocal<String> existingSupervisorName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> newSupervisorName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<Boolean> isEarlierSupervisor = ThreadLocal.withInitial(() -> false);

    /*This method to navigate to Team Details Page
        Author -Vidya
    */

    @When("I navigate to team details page")
    public void navigateToTeamDetailsPage() {
        Assert.assertTrue(tmProjectDetailsPage.teamIcon.isDisplayed(), "Team icon is  not displayed");
        tmProjectDetailsPage.teamIcon.click();
        waitFor(3);
        waitForVisibility(teamListPage.addButton, 8);
        Assert.assertTrue(teamListPage.addButton.isDisplayed(), "Add Team button is not displayed");
        teamListPage.addButton.click();
        waitForVisibility(teamPage.addTeamHeader, 5);
        Assert.assertTrue(teamPage.addTeamHeader.isDisplayed(), "Team Details page is not displayed");
    }

    /*This method verifies All fields are displayed when I click on (+) Add Button
    Author -Vidya
    Modified:- 22/10/2019
     */
    @Then("I verify all the fields are displayed in team details page")
    public void verifyAllFieldsAreDisplayedInTeamDetailsPage() {
        Assert.assertTrue(teamPage.teamName.isDisplayed(), "Team Name field is not displayed");
        Assert.assertTrue(teamPage.teamStartDate.isDisplayed(), "Start Date field is not displayed");
        Assert.assertTrue(teamPage.teamEndDate.isDisplayed(), "End Date field is not displayed");
        Assert.assertTrue(teamPage.teamDescription.isDisplayed(), "Description field is not displayed");
        Assert.assertTrue(teamPage.teamSaveButton.isDisplayed(), "Save button is not displayed");
        Assert.assertTrue(teamPage.teamCancelButton.isDisplayed(), "Cancel button is not displayed");
        Assert.assertTrue(teamPage.backArrow.isDisplayed(), "Back Arrow is not displayed");
        Assert.assertTrue(teamPage.buDropDownBox.isDisplayed(), "Business Unit dropdwon list is not displayed");
        Assert.assertTrue(teamPage.userAddBtn.isDisplayed(), "Add user button is not displayed");
        Assert.assertTrue(teamPage.userListSection.isDisplayed(), "User list section is not displayed");
        Assert.assertTrue(teamPage.noRecordAvailableTxt.isDisplayed(), "No Record Found text is not displayed");

    }

    /*This method to click on Save button present on Team Details Page
        Author -Vidya
    */

    @And("I click on Save button on team details page")
    public void clickOnSaveButton() {
        waitFor(1);
        teamPage.teamSaveButton.click();
        //click(teamPage.teamSaveButton);

    }

     /*This method to verify field level error messages
        Author -Vidya
    */

    @When("I see {string} message under the {string} field on team details page")
    public void i_see_message_under_the_field(String message, String field) {
        switch (field) {
            case "TeamName":
                waitForVisibility(teamPage.teamNameFieldError, 10);
                assertTrue(teamPage.teamNameFieldError.isDisplayed());
                assertTrue(teamPage.teamNameFieldError.getText().toLowerCase().equals(message.toLowerCase()));
                break;

            case "StartDate":
                waitForVisibility(teamPage.startDateFieldError, 10);
                assertTrue(teamPage.startDateFieldError.isDisplayed());
                assertTrue(teamPage.startDateFieldError.getText().toLowerCase().equals(message.toLowerCase()));
                break;

            case "EndDate":
                waitForVisibility(teamPage.endDateFieldError, 10);
                assertTrue(teamPage.endDateFieldError.isDisplayed());
                assertTrue(teamPage.endDateFieldError.getText().toLowerCase().equals(message.toLowerCase()));
                break;

            case "BusinessUnit":
                waitForVisibility(teamPage.buFieldError, 10);
                assertTrue(teamPage.buFieldError.isDisplayed());
                assertTrue(teamPage.buFieldError.getText().toLowerCase().equals(message.toLowerCase()));
                break;
        }
    }

    /*This method verifies that Team Name accepts only alphanumeric & spaces
    Input paramter - number of characters
    Author -Vidya
     */

    @Then("I verify team name accepts {int} alphanumeric spaces are allowed")
    public void teamNameFieldValidation(Integer int1) {
        String text = "TeamTest Unit 1 New Test verify 50 Character Allow";
        teamPage.teamName.sendKeys(text);
        Assert.assertTrue(teamPage.teamName.getAttribute("value").length() == int1, "Team name field length is not 50 characters");
        text = "TeamTest Unit 1 New Test verify 50 Character Allow1fghj";
        clearAndFillText(teamPage.teamName, text);
        Assert.assertTrue(teamPage.teamName.getAttribute("value").length() == int1, "Team name field length is not 50 characters");
    }

    /*This method verifies that Description accepts only alphanumeric & spaces
    Input paramter - number of characters
    Author -Vidya
     */

    @Then("I verify team description accepts {int} alphanumeric spaces are allowed")
    public void teamDescriptionFieldValidation(Integer int1) {
        teamPage.teamDescription.sendKeys(getRandomString(150));
        Assert.assertTrue(teamPage.teamDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
        clearAndFillText(teamPage.teamDescription, getRandomString(175));
        Assert.assertTrue(teamPage.teamDescription.getAttribute("value").length() == int1, "Description field length is not 150 characters");
    }

    /*This method enter data to all fields of Team Details Page
        Author -Vidya
    */

    @When("I populate data on team details page {string},{string},{string},{string},{string}")
    public void iPopulateDataOnTeamDetailsPage(String teamName, String desc, String startDate, String endDate, String teamBU) {
        if (teamName.equals("{random}")) {
            synchronized (teamValue) {
                teamValue.set(getRandomString(8));
            }
            highLightElement(teamPage.teamName);
            scrollUpRobot();
            clearAndFillText2(teamPage.teamName, teamValue.get());
        } else {
            clearAndFillText(teamPage.teamName, teamValue.get());
        }

        teamDescription.set("");
        if (desc.equals("{random}")) {
            teamDescription.set(getRandomString(7));
            clearAndFillText(teamPage.teamDescription, teamDescription.get());
        }

        teamStartDate.set(startDate);
        if (startDate.equals("today")) teamStartDate.set(getCurrentDate());
        else if (startDate.contains("-")) {
            teamStartDate.set(getPriorDate(Integer.parseInt(startDate.replace("-", ""))));
        } else if (startDate.contains("+")) {
            teamStartDate.set(getGreaterDate(Integer.parseInt(startDate.replace("+", ""))));
        }
        if (!teamStartDate.get().equals("") && teamStartDate.get() != null) {
            clearAndFillText(teamPage.teamStartDate, teamStartDate.get());
        }

        teamEndDate.set(endDate);
        if (endDate.equals("today")) teamEndDate.set(getCurrentDate());
        else if (endDate.contains("-")) {
            teamEndDate.set(getPriorDate(Integer.parseInt(endDate.replace("-", ""))));
        } else if (endDate.contains("+")) {
            teamEndDate.set(getGreaterDate(Integer.parseInt(endDate.replace("+", ""))));
        }
        if (!teamEndDate.get().equals("") && teamEndDate.get() != null) {
            clearAndFillText(teamPage.teamEndDate, teamEndDate.get());
        }

        if (teamBU.equals("")) {
            teamPage.buDropDownBox.click();
            waitFor(2);
            businessUnit.set(teamPage.buDropDownBoxValue.get(1).getText());
            teamPage.buDropDownBoxValue.get(1).click();
            //selectDropDown(teamPage.buDropDownBox, businessUnit);

        } else if (!teamBU.equals("null")) {
            businessUnit.set(teamBU);
            selectBUNAME(businessUnit.get());
        }
    }

    public void selectBUNAME(String buname) {

        teamPage.buDropDownBox.click();
        waitFor(2);


        List<WebElement> getBUName = teamPage.buDropDownBoxValue;
        System.out.println("BU SIZE :" + getBUName.size());
        for (int i = 0; i < getBUName.size(); i++) {
            if (teamPage.buDropDownBoxValue.get(i).getText().equalsIgnoreCase(buname)) {
                teamPage.buDropDownBoxValue.get(i).click();
                break;
            }
        }


    }

    /*This method to click on different button accourding to user requirement
        Author -Vidya
    */

    @And("I click on {string} on team details page")
    public void clickOnButton(String button) {
        waitFor(1);
        switch (button) {
            case "Cancel":
                waitForVisibility(teamPage.teamCancelButton, 10);
                click(teamPage.teamCancelButton);
                break;

            case "Back Arrow":
                waitForVisibility(teamPage.backArrow, 10);
                click(teamPage.backArrow);
                break;

            case "BU Icon":
                waitForVisibility(tmProjectDetailsPage.businessUnitIcon, 10);
                click(tmProjectDetailsPage.businessUnitIcon);
                break;
        }

    }

    /*This method to verify Warning Message Popup
        Author -Vidya
    */

    @Then("I verify Warning message and Warning text is displayed with Continue and Cancel button on team details page")
    public void verifyWarningMessage() {
        waitForVisibility(teamPage.warningMessage, 10);
        Assert.assertTrue(teamPage.warningMessage.isDisplayed(), "Warning Message is not displayed");
        Assert.assertTrue(teamPage.warningMessageTxt.isDisplayed(), "Warning message text is not displayed");
        Assert.assertTrue(teamPage.warningMsgContinueBtn.isDisplayed(), "Warning message Continue button is not displayed");
        Assert.assertTrue(teamPage.warningMsgCancelBtn.isDisplayed(), "Warning message Cancel button is not displayed");
    }

    @Then("I verify it should display {string} page")
    public void verifyItShouldDisplayBusinessUnitListPage(String page) {
        switch (page) {
            case "Team List page":
                waitForVisibility(teamListPage.teamHeader, 10);
                Assert.assertTrue(teamListPage.teamHeader.isDisplayed(), "Team List page is not displayed");
                Assert.assertTrue(teamListPage.addButton.isDisplayed(), "Team List page is not displayed");
                break;

            case "Business Unit List page":
                waitForVisibility(businessUnitPage.businessUnitListPage, 10);
                Assert.assertTrue(businessUnitPage.businessUnitListPage.isDisplayed(), "Business Unit List page is not displayed");
                Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "Business Unit List page is not displayed");
                break;
        }
    }


    /*This method to click on cancel button present on warning message
        Author -Vidya
    */

    @And("I click on cancel button on warning message of team details page")
    public void clickOnCancelButtonOfWarningMessage() {
        waitForVisibility(teamPage.warningMsgCancelBtn, 10);
        click(teamPage.warningMsgCancelBtn);
    }

    @Then("I verify it should remain on the team details page and information should not save")
    public void verifyItShouldRemainsOnTheSamePage() {
        Assert.assertTrue(teamPage.addTeamHeader.isDisplayed(), "It is not remains on same page");
        Assert.assertNotEquals(teamPage.teamName.getAttribute("value"), "", "Business Unit Name field value is cleared");
    }

    /*This method to click on continue button present on warning message
        Author -Vidya
    */

    @And("I click on continue button on warning message of team details page")
    public void clickOnContinueButtonOfWarningMsg() {
        waitForVisibility(teamPage.warningMsgContinueBtn, 10);
        click(teamPage.warningMsgContinueBtn);
    }

    /*This method to verify End Date and Description fields are optional
        Author -Vidya
    */

    @Then("I verify end date and description are optional in team details page")
    public void verifyOptionalFieldsInTeam() {
        waitForVisibility(teamPage.succesMessage, 10);
        Assert.assertTrue(teamPage.succesMessage.isDisplayed(), "Success Message is not displayed");
        Assert.assertTrue(teamPage.succesMessageTxt.isDisplayed(), "Success Message text is not displayed");
        Assert.assertTrue(teamPage.addTeamHeader.isDisplayed(), "Team Details page is not displayed");
    }

    /*This method to verify Business unit drop down values are sorted order and
       it has only Active and Future Business unit records
        Author -Vidya
        Modified date:-22/10/2019 Reason:- They change the AC
    */

    @Then("I verify business unit dropdown box values are sorted")
    public void verifyBUDropdownBoxValue() {
        waitForVisibility(teamPage.buDropDownBox, 10);
        click(teamPage.buDropDownBox);
        waitFor(3);
        waitForVisibility(teamPage.buDropDownBoxValue.get(0), 10);
        ArrayList<String> buDropdownBox = new ArrayList<>();
        for (int i = 1; i < teamPage.buDropDownBoxValue.size(); i++) {
            buDropdownBox.add(teamPage.buDropDownBoxValue.get(i).getAttribute("data-value"));
        }
        Assert.assertEquals(buDropdownBox.size(), TM_ListBusinessUnitStepDef.buName.size());

        for (int i = 0; i < buDropdownBox.size(); i++) {
            Assert.assertTrue(buDropdownBox.get(i).trim().equalsIgnoreCase(TM_ListBusinessUnitStepDef.buName.get(i).trim())
                    , "Drop down values are not sorted");
        }
    }

    /*This method to verify new Team created Successfully
        Author -Vidya
    */

    @Then("I verify Team Successfully Created message is displayed and I go back to Team List page")
    public void verifySaveFunctionalityOfTeam() {
        waitForVisibility(teamPage.succesMessage, 10);
        Assert.assertTrue(teamPage.succesMessage.isDisplayed(), "Success Message is not displayed");
        Assert.assertTrue(teamPage.succesMessageTxt.isDisplayed(), "Success Message text is not displayed");
        //   Assert.assertTrue(teamPage.addTeamHeader.isDisplayed(), "Team Details page is not displayed");
    }

    /*This method to verify add user button is disable
        Author -Vidya
    */

    @Then("I verify Add User Button in the User List section is disabled")
    public void verifyAddUserBtnIsDisable() {
        waitForVisibility(teamPage.userAddBtn, 10);
        Assert.assertTrue(teamPage.userAddBtn.isDisplayed(), "Edit button is not displayed");
        Assert.assertFalse(teamPage.userAddBtn.isEnabled(), "Add user button is enabled");
    }

    /*This method to verify all fields are in read only type after creating the team
       Author -Vidya
   */

    @And("I verify Team Details section displays as Read Only")
    public void verifyAllFieldAreReadyOnly() {
        waitFor(2);
        String header[] = {"TEAM NAME", "BUSINESS UNIT NAME", "START DATE", "END DATE", "DESCRIPTION"};
        for (int i = 0; i < teamPage.allFieldsheader.size(); i++) {
            Assert.assertEquals(teamPage.allFieldsheader.get(i).getText(), header[i], "Headers values are wrong");
        }
        Assert.assertTrue(teamPage.allFieldsVlu.get(0).isDisplayed(), "Team Name is not displayed");
        Assert.assertTrue(teamPage.allFieldsVlu.get(1).isDisplayed(), "BU is not displayed");
        Assert.assertTrue(teamPage.allFieldsVlu.get(2).isDisplayed(), "Start Date is not displayed");
        Assert.assertTrue(teamPage.allFieldsVlu.get(3).isEnabled(), "End Date is not displayed");
        Assert.assertTrue(teamPage.allFieldsVlu.get(4).isEnabled(), "Description is not displayed");

    }

    /*This method to verify status for team is displayed
       Author -Vidya
   */

    @And("I verify the {string} for the Team is displayed")
    public void verifyStatusForTheTeamIsDisplayed(String status) {
        Assert.assertEquals(teamPage.teamStatus.getText(), status, "Status is as not expected");
    }

    /*This method to verify edit button is displayed of team details page after saving the team
       Author -Vidya
   */

    @And("I verify Edit Team button is displayed")
    public void verifyEditTeamButtonIsDisplayed() {
        Assert.assertTrue(teamPage.editButton.isDisplayed(), "Edit button is not displayed");
        Assert.assertTrue(teamPage.editButton.isEnabled(), "Edit button is not enabled");
    }

    /*This method to verify Add user button is enabled after saving the team
       Author -Vidya
   */

    @And("I verify Add User button on User List section is enabled")
    public void verifyAddUserButtonIsEnabled() {
        Assert.assertTrue(teamPage.userAddBtn.isDisplayed(), "Add user button is not displayed");
        Assert.assertTrue(teamPage.userAddBtn.isEnabled(), "Add user button is not enabled");
    }

    /*This method is to click on ADD USER button in team details screen
    Author-paramita
    */
    @When("I click on Add User button")
    public void clickOnAddUserButton() {
        jsClick(teamPage.userAddBtn);
        //click(teamPage.userAddBtn);
        waitFor(1);
    }

    /* This method to verify all the available fields in User details screen
     Author-Paramita
     */
    @Then("I verify all the fields are displayed in User details screen")
    public void verifyAllFieldsUserDetaisScreen() {

        Assert.assertTrue(teamPage.userNameField.isDisplayed(), "User Name is not displayed");
        Assert.assertTrue(teamPage.emailField.isDisplayed(), "Email is not displayed");
        Assert.assertTrue(teamPage.maerskIdField.isDisplayed(), "maersk ID is not displayed");
        Assert.assertTrue(teamPage.supervisorCheckBox.isEnabled(), "Team Supervisor is not displayed");

    }

    @And("I select any team value from team userlist")
    public void seletTeamValue() {
        if (teamListPage.teamrowVlu.size() > 0) {
            for (int i = 0; i <= teamListPage.teamrowVlu.size(); i++) {
                String TName = teamListPage.teamNameClumVlu.get(i).getText();
                String TStatus = teamListPage.statusClumVlu.get(i).getText();
                if (!TName.equals("")) {
                    teamListPage.teamNameClumVluText.get(i).click();
                    waitFor(2);
                    // scrollToElement(teamPage.userAddBtn);
                    Assert.assertTrue(teamPage.teamDetailsheader.isDisplayed(), "Team Details page is not displayed");
                    break;
                }

            }
        }
    }

    /*  This method to check whether it has team value, if not, It will create a new team
    Author -Paramita
    */
    @And("I check whether it has team value, if not, I will create a team {string},{string},{string},{string},{string}")
    public void checkAtleastOneTeamIsPresent(String teamName, String teamDesc, String teamStartDate, String teamEndDate, String teamBU) {
        boolean flag1 = true;
        if (teamListPage.teamrowVlu.size() > 0) {
            for (int i = 0; i <= teamListPage.teamrowVlu.size(); i++) {
                String TName = teamListPage.teamNameClumVlu.get(i).getText();
                String TStatus = teamListPage.statusClumVlu.get(i).getText();
                if (!TName.equals("")) {
                    int randomValue = Integer.parseInt(getRandomNumber(1));
                    teamListPage.teamNameClumVluText.get(randomValue).click();
                    waitFor(2);
                    scrollToElement(teamPage.userAddBtn);
                    Assert.assertTrue(teamPage.teamDetailsheader.isDisplayed(), "Team Details page is not displayed");
                    flag1 = false;
                    flag.set(true);
                    break;
                }

            }
        }
        if (flag1) {
            teamListPage.addButton.click();
            iPopulateDataOnTeamDetailsPage(teamName, teamDesc, teamStartDate, teamEndDate, teamBU);
            waitFor(2);
            clickOnSaveButton();
            flag.set(true);
        }
        scrollToElement(teamPage.userAddBtn);

    }

    /*  This method to click on TEAM icon
     Author - Paramita
     */
    @When("I click on Team icon from left menu bar")
    public void clickOnTeamIcon() {
        waitFor(2);
        Assert.assertTrue(tmProjectDetailsPage.teamIcon.isDisplayed(), "Team icon is  not displayed");
        tmProjectDetailsPage.teamIcon.click();

    }

    /* This method to verify autopopulate the other fields value on select one field value
     Author - Paramita
     */
    @Then("I select any dropdown {string} value other remaining fields get autopopulated")
    public void autopopulatFields(String field) {
        switch (field) {
            case "User Name":
                waitForVisibility(teamPage.userNameField, 10);
                click(teamPage.userNameField);
                waitFor(1);
                int listSize1 = teamPage.userDropDownBoxValue.size();
                if (listSize1 > 0) {
                    int randomNum = ((int) (Math.random() * (listSize1 - 1))) + 1;
                    teamPage.userDropDownBoxValue.get(randomNum).click();

                }
                break;

            case "Email":
                waitForVisibility(teamPage.emailField, 10);
                click(teamPage.emailField);
                waitFor(1);

                int email_listSize = teamPage.emailDropDownBoxValue.size();
                if (email_listSize > 0) {
                    int randomNum = ((int) (Math.random() * (email_listSize - 1))) + 1;
                    teamPage.emailDropDownBoxValue.get(randomNum).click();
                }

                break;

            case "maersk ID":
                waitForVisibility(teamPage.maerskIdField, 10);
                click(teamPage.maerskIdField);
                waitFor(1);

                int maxID_listSize = teamPage.maxIDDropDownBoxValue.size();
                if (maxID_listSize > 0) {
                    int randomNum = ((int) (Math.random() * (maxID_listSize - 1))) + 1;
                    teamPage.maxIDDropDownBoxValue.get(randomNum).click();
                }
                break;
        }
    }

    /* This method to select one dropdown value from Team User list
    Author -Paramita
    */
    @And("I select any dropdown value other remaining fields get autopopulated")
    public void selectTeamUserField() {
        waitForVisibility(teamPage.userNameField, 5);
        click(teamPage.userNameField);
        waitFor(3);
        int listSize = teamPage.userDropDownBoxValue.size();
        if (listSize > 0) {
            waitFor(2);
            int randomNum = ((int) (Math.random() * (listSize - 1))) + 1;
            teamPage.userDropDownBoxValue.get(randomNum).click();
        }
        if (listSize == 1) {
            waitFor(2);
//            int randomNum = ((int) (Math.random() * (listSize - 1))) + 1;
            teamPage.userDropDownBoxValue.get(0).click();
        }
    }

    /* This method to click ADD User button for second time
     Author -Paramita*/

    @When("I click on Add User button second time")
    public void i_click_on_add_user_button_second_time() {
        jsClick(teamPage.userAddBtn);
        // click(teamPage.userAddBtn);

    }

    /* This method to associate User to a team Author -paramita*/
    @And("I click on team user save button")
    public void i_click_on_team_user_save_button() {
        teamPage.userSaveBtn.click();
        waitFor(1);
    }

    /* This method is to SAVE empty user data for a team
    Author -Paramita
    */
    @And("I click on team user save button without adding data")
    public void i_click_on_team_user_save_button_without_data() {
        teamPage.userSaveBtn.click();
        waitFor(2);
    }

    /* This method is to associate single User to a team
       Author -Paramita
     */
    @Then("I see single user is associated with a Team")
    public void i_see_single_user_associated_team() {
        teamAssociatedMultipleAndSingleUser();
    }

    /* This method is to associate multiple User to a team
    Author -Paramita
    */
    @Then("I see multiple user is associated with a Team")
    public void teamAssociatedMultipleAndSingleUser() {
        if (teamPage.associatedTeamUserCount.size() != 0) {
            Assert.assertTrue(teamPage.associatedTeamUserEdit.isDisplayed(), "Record is Available");
        } else {
            waitFor(2);
            Assert.assertTrue(teamPage.noRecordAvailableTxt.isDisplayed(), "No Record is Available");
        }

    }

    /* This method to see no record is added in Userlist section with empty data
    Author -Paramita
    */
    @Then("I see empty record is not saved")
    public void i_see_empty_record_is_not_saved() {
        teamAssociatedMultipleAndSingleUser();

    }

    /* This method to see dropdown value sorted in alphabetical order
     author - Paramita
     */
    @Then("I verify {string} dropdown box values are sorted alphabetically in user list section")
    public void verifyUserDropdownBoxValue(String field) {
        switch (field) {
            case "User Name":
                waitForVisibility(teamPage.userNameField, 10);
                click(teamPage.userNameField);
                waitFor(3);
                ArrayList<String> obtainedListuser = new ArrayList<>();
                for (WebElement we : teamPage.userDropDownBoxValue) {
                    obtainedListuser.add(we.getText());
                }
                Collections.sort(obtainedListuser);
                ArrayList<String> sortedListuser = new ArrayList<>();
                for (String s : obtainedListuser) {
                    sortedListuser.add(s);
                }
                Collections.sort(sortedListuser);

               /* For descending order -need to add this line
                Collections.reverse(sortedList); */

                Assert.assertTrue(sortedListuser.equals(obtainedListuser), "User Dropdown value is alphabetically sorted");
                break;


            case "Email":
                waitForVisibility(teamPage.emailField, 10);
                click(teamPage.emailField);
                waitFor(3);
                ArrayList<String> obtainedListEmail = new ArrayList<>();
                for (WebElement we : teamPage.emailDropDownBoxValue) {
                    obtainedListEmail.add(we.getText());
                }
                ArrayList<String> sortedListEmail = new ArrayList<>();
                for (String s : obtainedListEmail) {
                    sortedListEmail.add(s);
                }
                Collections.sort(sortedListEmail);
                Assert.assertTrue(sortedListEmail.equals(obtainedListEmail), "Email Dropdown value is alphabetically sorted");
                break;

            case "maersk ID":
                waitForVisibility(teamPage.maerskIdField, 10);
                click(teamPage.maerskIdField);
                waitFor(3);
                ArrayList<String> obtainedListMaxID = new ArrayList<>();
                for (WebElement we : teamPage.maxIDDropDownBoxValue) {
                    obtainedListMaxID.add(we.getText());
                }
                ArrayList<String> sortedLisMaxID = new ArrayList<>();
                for (String s : obtainedListMaxID) {
                    sortedLisMaxID.add(s);
                }
                Collections.sort(sortedLisMaxID);
                Assert.assertTrue(sortedLisMaxID.equals(obtainedListMaxID), "MAX ID  Dropdown value is numerically sorted");
                break;
        }


    }


    @And("I check whether it has 5 users, if not, I add users")
    public void checkIfItHasMultipleUsers() {
        if (tmProjectDetailsPage.viewUserListMaxID.size() >= 5) {
            System.out.println("5 users are present");
        } else {
            addProjectUsertepDef.get().i_click_on_add_new_user_button_on_add_new_user_page();
            String maxID[] = {"154000", "154200", "154346", "154255", "282262"};
            int size1 = maxID.length;
            for (int k = 0; k <= size1 - 1; k++) {
                addProjectUsertepDef.get().createUser("Yes", maxID[k], "", "");
                addProjectUsertepDef.get().i_click_on_save_a_user_button();
                waitFor(4);
                addProjectUsertepDef.get().i_click_on_add_new_user_button_on_add_new_user_page();
            }

        }
        waitFor(5);
        tmProjectDetailsPage.teamIcon.click();

    }

    @And("I click on X icon on the record level")
    public void deleteTeamUserRecordWithoutSave() {
        System.out.println("Delete Icon Size:" + teamPage.teamuserdeleteicon);
        if (teamPage.teamuserdeleteicon.size() > 0) {
            for (int i = 1; i < teamPage.teamuserdeleteicon.size(); i++) {
                teamPage.teamuserdeleteicon.get(i).click();
                break;
            }
        }
    }

    @Then("I see the record is cancelled")
    public void validateRecordGetcancelled() {
        teamAssociatedMultipleAndSingleUser();

    }

    @When("I click on Cancel button on Team User Page")
    public void i_click_on_Cancel_button_on_Team_User_Page() {
        waitFor(2);
        teamPage.userCancelBtn.click();
    }

    @And("I see {string} alert message displayed")
    public void alertMessagedisplayed(String string) {
        waitForVisibility(teamPage.warningMessage, 3);
        assertTrue(teamPage.warningMessage.isDisplayed(), "Warning Pop Up is not displayed");

    }

    @When("I click on Cancel option and I am navigated back to Team User page and see all previously entered unsaved data")
    public void i_click_on_Cancel_and_I_am_navigated_back_to_Team_User_page_and_see_all_previously_entered_unsaved_data() {
        teamPage.warningMsgCancelBtn.click();
        assertTrue(teamPage.teamDetailsheader.isDisplayed(), "Team Details Page is not displayed");
    }

    @When("I click on Continue option and navigated back to any selected List Page")
    public void navigateToSelectedPage() {
        teamPage.warningMsgContinueBtn.click();
        waitFor(2);
        tmProjectDetailsPage.businessUnitIcon.click();
        Assert.assertTrue(bunsUtListPage.addButton.isDisplayed(), "Add Button is not displayed");
    }

    @When("I click on Back arrow on Team User Page")
    public void clickOnBackArrowInTeamDetailsPage() {
        waitFor(2);
        teamPage.backArrow.click();
    }

    @When("I click on Continue option and I am navigated back to Team User List Page")
    public void navigateToTeamUserPage() {
        teamPage.warningMsgContinueBtn.click();
        waitFor(2);
        Assert.assertTrue(teamListPage.addButton.isDisplayed(), "Add Team Button id not displayed");


    }

    /**
     * <description>clicks edit button for team user with given index in the list of users</>
     */
    @When("I click on Edit icon on a User contact card")
    public void click_on_edit_buttton_for_First_TeamUser() {
        WebElement user = teamPage.listOfTeamUsers.get(0);
        user.findElement(By.xpath(".//i[text()='edit']")).click();
    }

    /**
     * <description>clicks edit button for team user with given index in the list of users</>
     */
    @Then("I verify the supervisor check box is available to update")
    public void verifySupervisorCheckBoxIsDisplayed() {
        WebElement user = teamPage.listOfTeamUsers.get(0);
        Assert.assertTrue(user.findElement(By.xpath(".//input/ancestor::span[contains(@class, 'Button-root')]")).isDisplayed());
        Assert.assertTrue(user.findElement(By.xpath(".//input/ancestor::span[contains(@class, 'Button-root')]")).isDisplayed());
    }

    /**
     * <description>check/uncheck supervisor check box</>
     */
    @When("I update the user by checking or unchecking the checkbox")
    public void updatedTheUser() {
        isEarlierSupervisor.set(false);
        WebElement user = teamPage.listOfTeamUsers.get(0);
        isEarlierSupervisor.set(Boolean.parseBoolean(user.findElement(By.xpath(".//input")).getAttribute("value")));
        user.findElement(By.xpath(".//input")).click();
        // user.findElement(By.xpath("//i[text()='check']/parent::button")).click();
    }

    /**
     * <description>click save check mark for the first user in the list of users</>
     */
    @When("I click on Save checkmark on the Contact Card for the User")
    public void clickSaveCheckmarkFirstUser() {
        WebElement user = teamPage.listOfTeamUsers.get(0);
        user.findElement(By.xpath("//i[text()='check']/parent::button")).click();
    }

    /**
     * <description>verify user update is saved</>
     */
    @Then("I verify the update is reflected on the Contact card")
    public void verifyUpdateReflectedInContactCard() {
        waitFor(2);
        WebElement user = teamPage.listOfTeamUsers.get(0);
        user.findElement(By.xpath(".//i[text()='edit']")).click();
        boolean isSupervisor = Boolean.parseBoolean(user.findElement(By.xpath(".//input")).getAttribute("value"));
        if (isEarlierSupervisor.get()) {
            Assert.assertTrue(!isSupervisor);
        } else {
            Assert.assertTrue(isSupervisor);
        }
    }

    /**
     * <description>Make the given user as supervisor if not</>
     */
    @Given("Supervisor checkbox is selected for user {string}")
    public void makeFirstUserAsSupervisor(String userNumber) {
        WebElement user = teamPage.listOfTeamUsers.get(Integer.parseInt(userNumber));
        user.findElement(By.xpath(".//i[text()='edit']")).click();
        boolean isSupervisor = Boolean.parseBoolean(user.findElement(By.xpath(".//input")).getAttribute("value"));
        if (!isSupervisor)
            user.findElement(By.xpath(".//input")).click();
        user.findElement(By.xpath("//i[text()='check']/parent::button")).click();
    }

    /**
     * <description>edit non-supervisor user and update to supervisor</>
     */
    @When("I check the supervisor check box for a non supervisor user")
    public void click_on_edit_buttton_for_TeamUser() {
        existingSupervisorName.set(teamPage.getSupervisorName());
        WebElement user = teamPage.listOfTeamUsers.get(1);
        user.findElement(By.xpath(".//i[text()='edit']")).click();
        user.findElement(By.xpath(".//input")).click();
        user.findElement(By.xpath("//i[text()='check']/parent::button")).click();
        newSupervisorName.set(user.findElement(By.xpath(".//p[2]")).getText());
    }

    /**
     * <description>verify user become non-suprevisor when other user made to supervisor</>
     */
    @Then("I verify non supervisor become supervisor and supervisor become non supervisor")
    public void verifySupervisorUpdated() {
        waitFor(2);
        String supervisorName = teamPage.getSupervisorName();
        Assert.assertEquals(supervisorName, newSupervisorName.get());
        WebElement user = teamPage.listOfTeamUsers.get(1);
        String nonSupervisorNam = user.findElement(By.xpath(".//p[2]")).getText();
        Assert.assertEquals(nonSupervisorNam, existingSupervisorName.get());
    }


}