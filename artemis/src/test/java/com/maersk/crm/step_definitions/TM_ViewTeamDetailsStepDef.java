package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.awt.*;
import java.util.*;

import static org.testng.Assert.assertTrue;

public class TM_ViewTeamDetailsStepDef extends BrowserUtils {

    TMAddTeamPage teamPage = new TMAddTeamPage();
    TMAddNewUserPage addNewUserPage = new TMAddNewUserPage();
    TMListOfTeamPage teamListPage = new TMListOfTeamPage();
    TM_AddTeamStepDef addTeam = new TM_AddTeamStepDef();
//    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
//    TMAddBusinessUnitPage businessUnitPage = new TMAddBusinessUnitPage();
//    TMBusinessUnitListPage bunsUtListPage = new TMBusinessUnitListPage();
//    TM_AddBusinessUnitStepDef addBUStepDef = new TM_AddBusinessUnitStepDef();
//    TM_AddNewUserToProjectStepDef addProjectUsertepDef = new TM_AddNewUserToProjectStepDef();


    public static final ThreadLocal<String> teamValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamStartDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamEndDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> teamDescription = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessUnit = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> status = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> flag = ThreadLocal.withInitial(() -> false);


    /* Author -Paramita 13/12/2019  Script to verify Fields displaying in ready only mode*/
    @Then("I should see all the fields as Ready only in Team view page")
    public void verifyAllTeamFieldAreReadyOnly() {
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

    /* Author -Paramita 13/12/2019  Script to verify Edit button in Team View Page*/
    @Then("I should see edit button in Team view page")
    public void verifyEditButton() {
        Assert.assertTrue(teamPage.editButton.isDisplayed(), "Edit button is not displayed");
    }


    /* Author -Paramita 13/12/2019  Script to verify Status in Team View Page*/
    @Then("I should see {string} in Team view page")
    public void verifyStatus(String status) {
        System.out.println(flag.get() + "    " + TM_AddTeamStepDef.flag.get());
        if (flag.get() || TM_AddTeamStepDef.flag.get())
            switch (status) {
                case "ACTIVE":
                    Assert.assertTrue(addNewUserPage.activeStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;

                case "FUTURE":
                    Assert.assertTrue(addNewUserPage.futureStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;

                case "INACTIVE":
                    Assert.assertTrue(addNewUserPage.inactiveStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;
            }
    }


    /* Author -Paramita 13/12/2019  Script to verify Back button in Team View Page*/
    @Then("I should see Back arrow icon in Team view page and navigate to team list page on click Back button")
    public void backArrowFunctionalityTeamView() {
        Assert.assertTrue(teamPage.backArrow.isDisplayed(), "Back arrow button is not displayed");
        teamPage.backArrow.click();
        waitFor(2);
        Assert.assertTrue(teamListPage.teamHeader.isDisplayed(), "Team list page is not displayed");
    }

    /* Author -Paramita 13/12/2019  Script to verify fields of each team user contact records are ready only*/
    @And("I verify fields of each team user contact records are ready only")
    public void i_verify_fields_of_each_user_contact_records_are_ready_only() {
        if (teamPage.associatedTeamUserCount.size() == 0) {
            click(teamPage.userAddBtn);
            addTeam.selectTeamUserField();
            teamPage.supervisorCheckBox.click();
            teamPage.userSaveBtn.click();
            waitFor(1);
        }
        for (int i = 0; i <= teamPage.associatedTeamUserCount.size() - 1; i++) {
            if (teamPage.associatedTeamUserCount.get(0).getText().contains("SUPERVISOR")) {
                Assert.assertTrue(teamPage.teamUserName.isDisplayed(), "User Name is not displayed");
                Assert.assertTrue(teamPage.teamUserEmail.isDisplayed(), "User Email is not displayed");
                Assert.assertTrue(teamPage.teamMaxIDNO.isDisplayed(), "User MaxID is not displayed");
                Assert.assertTrue(teamPage.associatedTeamUserEdit.isDisplayed(), "Edit icon is not displayed");

            } else {
                Assert.assertTrue(teamPage.teamUserName.isDisplayed(), "User Name is not displayed");
                Assert.assertTrue(teamPage.teamUserEmail.isDisplayed(), "User Email is not displayed");
                Assert.assertTrue(teamPage.teamMaxIDNO.isDisplayed(), "User MaxID is not displayed");
                Assert.assertTrue(teamPage.associatedTeamUserEdit.isDisplayed(), "Edit icon is not displayed");

            }
        }
    }


    /* Author -vidya 18/12/2019
       This method to verify  color contact card of Supervisor and Non Supervisor users in View Team screen */
    @Then("I verify color contact card of Supervisor in View Team screen")
    public void verifyColorContactCardsSupervisorUser() {
        //System.out.println(teamPage.teamUserSupervisorCardColor.get(0).getAttribute("class"));
        //flag=teamPage.teamUserSupervisorCardColor.get(0).getAttribute("class").contains("supervisor");
        //.getText().contains("SUPERVISOR");
        /*if(!(teamPage.teamUserSupervisorCardColor.get(0).getAttribute("class").contains("supervisor"))){
            click(teamPage.userAddBtn);
            addTeam.selectTeamUserField();
            teamPage.supervisorCheckBox.click();
            teamPage.userSaveBtn.click();
            waitFor(1);
        }*/
        Assert.assertEquals("#e57373", getColorCode(teamPage.teamUserSupervisorCardColor.get(0)),
                "Red color is missing");
    }

    @Then("I verify color contact card of Non Supervisor in View Team screen")
    public void verifyColorContactCardsNonSupervisorUser() {
        if (teamPage.teamUserSupervisorCardColor.size() > 1) {
            flag.set( false);
        } else if (teamPage.teamUserSupervisorCardColor.size() == 1 && teamPage.associatedTeamUserCount.get(0).getText().contains("SUPERVISOR")) {
            flag.set( true);
        }
        if (flag.get()) {
            click(teamPage.userAddBtn);
            addTeam.selectTeamUserField();
            teamPage.userSaveBtn.click();
            waitFor(1);
        }
        for (int i = 1; i < teamPage.teamUserSupervisorCardColor.size() - 1; i++) {
            Assert.assertEquals("#768fdf", getColorCode(teamPage.teamUserSupervisorCardColor.get(i)),
                    "Blue color is missing");
        }
    }


/* Author -Vidya 18/12/2019 : Refactored - Paramita  03/05/2020
   This method to verify team users are sorted in alphabetical order */

    @Then("I verify Users are sorted in alphabetical order")
    public void verifyUsersSortedAlphabetically() {
        ArrayList<String> obtainedListuser = new ArrayList<>();
        ArrayList<String> sortedListuser = new ArrayList<>();
        if (teamPage.associatedTeamUserCount.size() <= 1) {
            int count = 4;
            while (count > 0) {
                assertTrue(teamPage.userAddBtn.isDisplayed(), "Add user is not displayed on Team Details Page");
                teamPage.userAddBtn.click();
                addTeam.selectTeamUserField();
                count++;
            }
            teamPage.userSaveBtn.click();
            waitFor(1);
        }

        int n = 0;
        if (teamPage.associatedTeamUserCount.get(0).getText().contains("SUPERVISOR")) {
            n = 1;
        }
        for (int i = n; i < teamPage.userName.size(); i++) {
            obtainedListuser.add(teamPage.userName.get(i).getText());

        }
        Collections.sort(obtainedListuser);
        //System.out.println("Obtained user list"+obtainedListuser);

        for (String s : obtainedListuser) {
            sortedListuser.add(s);
        }

        Collections.sort(sortedListuser);
        // System.out.println("Sorted user list"+sortedListuser);
        Assert.assertTrue(sortedListuser.equals(obtainedListuser), "Users are not alphabetically sorted");

    }

/* Author -Paramita 18/12/2019
   This method to verify Supervisor User is listed first */

    @Then("I verify Supervisor User is listed first")
    public void verifySupervisorListedFirst() {
        for (int i = 1; i <= teamPage.associatedTeamUserCount.size(); i++) {
            if (teamPage.teamUserSupervisorCardColor.get(i).getText().contains("SUPERVISOR")) {
                Assert.assertFalse(teamPage.teamUserSupervisorCardColor.get(i).isDisplayed(), "Supervisor Name is listed first");
            } else {
                Assert.assertTrue(teamPage.teamUserSupervisorCardColor.get(0).isDisplayed(), "Supervisor user is not listed first");
                break;
            }
        }
    }



/* Author -Paramita 18/12/2019
   This method to verify team value with status Inactive */

    @And("I check whether it has team value with status Inactive")
    public void checkIthasTeamWithInactiveStatus() {
        flag.set( false);
        TM_AddTeamStepDef.flag.set(false);
        System.out.println("Status Column size" + teamListPage.statusClumVlu.size());
        for (int i = 0; i < teamListPage.statusClumVlu.size(); i++) {
            if (teamListPage.statusClumVlu.get(i).getText().equalsIgnoreCase("INACTIVE")) {
                jsClick(teamListPage.teamNameClumVlu.get(i));
                flag.set( true);
                break;
            }
        }
    }

/* Author -Paramita 18/12/2019
   This method to verify team value with status Future is present else create a new team with Future status */

    @And("I check whether it has team value with status Future , if not will create a team {string},{string},{string},{string},{string}")
    public void checkIthasTeamWithFutureStatus(String teamName, String teamDesc, String teamStartDate, String teamEndDate, String teamBU) {
        Boolean flag1 = true;
        if (teamListPage.teamrowVlu.size() > 0) {
            for (int i = 0; i < teamListPage.teamrowVlu.size(); i++) {

                String TStatus = teamListPage.statusClumVlu.get(i).getText();
                if (!TStatus.equals("") && TStatus.equalsIgnoreCase("FUTURE")) {
                    teamListPage.teamNameClumVluText.get(i).click();
                    flag1 = false;
                    flag.set( true);
                    break;
                }

            }
            if (flag1) {

                teamListPage.addButton.click();
                addTeam.iPopulateDataOnTeamDetailsPage(teamName, teamDesc, teamStartDate, teamEndDate, teamBU);
                waitFor(2);
                addTeam.clickOnSaveButton();
                flag.set( true);
            }
        }
    }


}
