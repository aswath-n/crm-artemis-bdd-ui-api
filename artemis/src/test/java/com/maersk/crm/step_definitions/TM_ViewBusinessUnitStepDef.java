package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

public class TM_ViewBusinessUnitStepDef extends BrowserUtils {

    TMViewBusinessUnitPage buViewPage = new TMViewBusinessUnitPage();
    TMBusinessUnitListPage buListPage = new TMBusinessUnitListPage();
    TMListOfTeamPage teamListPage = new TMListOfTeamPage();
    TMAddTeamPage teamPage = new TMAddTeamPage();
    final ThreadLocal<TM_AddTeamStepDef> addTeam = ThreadLocal.withInitial(TM_AddTeamStepDef::new);
    TMBusinessUnitListPage bunsUtListPage = new TMBusinessUnitListPage();
    final ThreadLocal<TM_AddBusinessUnitStepDef> tmAddBusinessUnitStepDef = ThreadLocal.withInitial(TM_AddBusinessUnitStepDef::new);
    com.maersk.crm.pages.tm.TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
//    int j = 0;

    public static final ThreadLocal<String> businessUnit = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> startDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> endDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> description = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> status = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> flag = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<String> TeamBUName1 = ThreadLocal.withInitial(String::new);
    //    public static final ThreadLocal<String> TeamUserName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> TeamStatus = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> TeamName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Integer> TeamUserCount = new ThreadLocal<>();

    /*This method to click on business unit name I use jsClick() to avoid scrolling and then clicking
       Author -Vidya
   */
    @When("I will click on business unit name")
    public void clickOnBusinessUnitName() {
        if (flag.get()) {
            jsClick(buListPage.businessUnitNameClumVlu.get(Integer.parseInt(tmAddBusinessUnitStepDef.get().businessUnitValue.get())));
        } else {
            jsClick(Driver.getDriver().findElement(By.xpath("//td/a[contains(text(), '" + tmAddBusinessUnitStepDef.get().businessUnitValue.get() + "')]")));
        }

    }


    /*This method to verify should I navigate to business unit details view page
       Author -Vidya
   */
    @Then("I should navigated to business unit details view page")
    public void verifyShouldINavigateToBUViewPage() {
        Assert.assertTrue(buViewPage.BUDetailsHdr.isDisplayed(), "It is not navigated to business unit view page");
    }


    /*This method to verify all fields are in read only type in business unit view page
       Author -Vidya
   */
    @Then("I should see all the fields in ready only type in business unit view page")
    public void verifyAllFieldAreReadyOnly() {
        String header[] = {"Business Unit Name", "Start Date", "End Date", "Description"};
        for (int i = 0; i < buViewPage.allFieldsheader.size(); i++) {
            Assert.assertEquals(buViewPage.allFieldsheader.get(i).getText(), header[i], "Headers values are wrong");
        }
        Assert.assertTrue(buViewPage.allFieldsVlu.get(0).isDisplayed(), "BU Name is not displayed");
        Assert.assertTrue(buViewPage.allFieldsVlu.get(1).isDisplayed(), "BU Start Date is not displayed");
        Assert.assertTrue(buViewPage.allFieldsVlu.get(2).isEnabled(), "BU End Date is not displayed");
        Assert.assertTrue(buViewPage.allFieldsVlu.get(3).isEnabled(), "BU Description is not displayed");

    }

    /*This method to verify Edit button is present on business unit view page
       Author -Vidya
   */
    @Then("I should see edit button in business unit view page")
    public void verifyEditButton() {
        Assert.assertTrue(buViewPage.editButton.isDisplayed(), "Edit button is not displayed");
    }

    /*This method to verify Status displayed in business unit view page is as per the business unit list page
       Author -Vidya
   */

    @Then("I should see {string} in business unit view page")
    public void verifyStatus(String status) {
        if (flag.get()) {
            waitForVisibility(buViewPage.editButton, 10);
            switch (status) {
                case "ACTIVE":
                    Assert.assertTrue(buViewPage.activeStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;

                case "FUTURE":
                    Assert.assertTrue(buViewPage.futureStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;

                case "INACTIVE":
                    Assert.assertTrue(buViewPage.inactiveStatus.isDisplayed(),
                            "Status is not as in Business unit list page");
                    break;
            }
        }
    }

    /*This method to verify Associated Task Type section listing each Task Type associated to this Business Unit
       Author -Vidya
   */

    @Then("I verify Associated Task Type section has task type {string}")
    public void verifyTaskTypeIsAssociatedToBU(String taskType) {
        waitForVisibility(buViewPage.associateTaskTypeHdr, 10);
        Assert.assertTrue(buViewPage.associateTaskTypeHdr.isDisplayed());
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//span[text()= '" + taskType + "']")).
                isDisplayed(), "Task type is not associated to BU");
    }

    /*This method to hover the mouse over task type associate to BU
       Author -Vidya
   */

    @Then("I hover my mouse over {string} Task Type")
    public void hoverMouserOverTheTaskType(String taskType) {
        waitForVisibility(buViewPage.associateTaskTypeVlu, 10);
        hover(Driver.getDriver().findElement(By.xpath("//span[text()= '" + taskType + "']")));
    }

    /*This method to Verify should I see the associated Permission Groups configured for that task types
       Author -Vidya
   */

    @Then("Verify should I see the associated Permission Groups configured for that task types")
    public void verifyAssociatedPermissionGropsConfiguredForThatTaskType() {
        waitForVisibility(buViewPage.permissionHeaderOfTaskType.get(0), 10);
        String str[] = {"PERMISSION GROUP TO WORK", "PERMISSION GROUP TO WORK - ESCALATED",
                "PERMISSION GROUP TO CREATE", "PERMISSION GROUP TO EDIT"};
        for (int i = 0; i < buViewPage.permissionHeaderOfTaskType.size(); i++) {
            Assert.assertTrue(buViewPage.permissionHeaderOfTaskType.get(i).getText().contains(str[i]));
            Assert.assertTrue(buViewPage.permissionVluOfTaskType.get(i).isDisplayed());
        }
    }



    /*This method to Verify the Associated Team section in Business Unit detail page
      Author -Paramita
   *//*
    @Then("I verify Associated Team section in Business Unit detail page")
    public void verify_associated_teams_section_in_business_unit_detail_page() {
        scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
        waitFor(2);
        Assert.assertTrue(bunsUtListPage.businessUnitAssociatedTeam.isDisplayed(), "Associated Team(s) section is not displayed");

    }*/

    /*This method to select newly created business unit
          Author -Paramita
       *//*
    @And("I select newly created business unit")
    public void clickOnNewlyCreatedBusinessUnit() {
        waitFor(3);
        waitForVisibility(bunsUtListPage.businessUnitNameClumVlu.get(0), 10);
        for (int i = 0; i < bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().toLowerCase().
                    equals(TM_AddBusinessUnitStepDef.businessUnitValue.toLowerCase())) {
             //   j = i;
             //   flag.get() = true;
                break;
            }

            bunsUtListPage.businessUnitNameClumVlu.get(i).click();

        }

    }*/


    /*This method to verify whether team is associate with business unit
     Author -Paramita
     */
    @Then("I check for a team associate with business unit {string},{string},{string},{string},{string}")
    public void createTeam(String teamName, String teamDesc, String teamStartDate, String teamEndDate, String teamBU) {
        if (teamListPage.teamrowVlu.size() > 1) {

            TeamBUName1.set(teamListPage.bUClumVlu.get(0).getText());
            TeamStatus.set(teamListPage.statusClumVlu.get(0).getText());
            TeamName.set(teamListPage.teamNameClumVlu.get(0).getText());
            tmProjectDetailsPage.businessUnitIcon.click();

        } else {
            teamListPage.addButton.click();
            addTeam.get().iPopulateDataOnTeamDetailsPage(teamName, teamDesc, teamStartDate, teamEndDate, teamBU);
            waitFor(2);
            addTeam.get().clickOnSaveButton();
            addTeam.get().clickOnBackArrowInTeamDetailsPage();
            TeamBUName1.set(teamListPage.bUClumVlu.get(0).getText());
            TeamStatus.set(teamListPage.statusClumVlu.get(0).getText());
            TeamName.set(teamListPage.teamNameClumVlu.get(0).getText());
            tmProjectDetailsPage.businessUnitIcon.click();
        }

        for (int i = 0; i < bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().equals(TeamBUName1.get())) {
                bunsUtListPage.businessUnitNameClumVlu.get(i).click();
                scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
                waitFor(1);
                for (int m = 0; m < buViewPage.associatedTeamTitle.size(); m++) {
                    Assert.assertTrue(buViewPage.associatedTeamTitle.get(m).getText().equals(TeamName.get()), "Associated Team Title is missing");
                    Assert.assertTrue(buViewPage.associatedTeamStatus.get(m).getText().equals(TeamStatus.get()), "Associated Team Status is missing");
                    break;
                }

            }

        }
    }


    /*This method to verify Team Start and End Date on mouse hover over associated Team
     Author -Paramita
     */
    @Then("I verify Team Start and End Date on mouse hover over associated Team status")
    public void hoverMouserOverTeamStatus() {
        hover(Driver.getDriver().findElement(By.xpath("//p[text()= '" + TeamStatus.get() + "']")));
        Assert.assertTrue(buViewPage.statusStartDate.getText().contains("START DATE"), "Start Date of Associated Team is missing");
        Assert.assertTrue(buViewPage.statusEndDate.getText().contains("END DATE"), "End Date of Associated Team is missing");
    }

    /* Create multiple user associated with a team - Author -Paramita */
    public void addMultipleTeamUser() {
        int t = 1;
        while (t < 10) {
            Driver.getDriver().findElement(By.xpath("//i[text()='add']/..")).click();
            addTeam.get().selectTeamUserField();
            t++;
            teamPage.userSaveBtn.click();
            waitFor(1);
        }

    }

    /*  This method to add multiple users for a team and select a business unit from business unit list page -
     Author -Paramita */
    @And("I add multiple users for a team and select a business unit from business unit list page")
    public void associateMultipleUsersInTeam() {

        addTeam.get().seletTeamValue();
        waitForVisibility(teamPage.BUValue, 10);
        String TeamBU = teamPage.BUValue.getText();
        TeamName.set(teamPage.TeamNameValue.getText());
        //  System.out.println("Team BU Name:" +TeamBU);

        if (teamPage.associatedTeamUserCount.size() < 7) {
            addMultipleTeamUser();
        }
        scrollToElement(teamPage.userListSection);
        synchronized (TeamUserCount) {
            TeamUserCount.set(teamPage.associatedTeamImg.size());
        }
        tmProjectDetailsPage.businessUnitIcon.click();

        for (int i = 0; i <= bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().equalsIgnoreCase(TeamBU)) {
                jsClick(bunsUtListPage.businessUnitNameClumVlu.get(i));
                break;
            }

        }
    }


    /*  This method to verify user photo is displayed for multiple associated team users
     Author -Paramita */
    @Then("I verify user photo is displayed for multiple associated team users")
    public void VerifyUserIconAssociatedTeam() {
        //scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
        waitFor(2);
        System.out.println("//h5[text()='" + TeamName.get() + "']/../following-sibling::div//img");
        List<WebElement> ele = Driver.getDriver().findElements(By.xpath("(//h5[text()='ASSOCIATED TEAM(S)']/..//div//h5)[1]/../following-sibling::div//img"));
        Assert.assertTrue(ele.size() == 7, "User photo icon is not displayed ");
    }


    /*  This method to see plus icon is displayed for more than 7 user in Associated section
     Author -Paramita */
    @Then("I see plus icon is displayed for more than 7 user in Associated section")
    public void verifyPlusIconMoreThan7users() {
        //waitFor(3);
        waitForVisibility(bunsUtListPage.businessUnitAssociatedTeam, 10);
        scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
        List<WebElement> ele = Driver.getDriver().findElements(By.xpath("(//h5[text()='ASSOCIATED TEAM(S)']/..//div//h5)[1]/../following-sibling::div//button[contains(text(),'+')]"));
        int ExtraUser = TeamUserCount.get() - 7;
        Assert.assertTrue(ele.size() == ExtraUser, "Count Value not matching");
    }

    /* This method to see  username on click plus icon
        Author -Paramita */
    @And("I see username on click plus icon")
    public void seeUsernameOnclickPlusIcon() {
        //waitFor(2);
        jsClick(buViewPage.ExtraTeamUserIcon);
        for (int i = 0; i < buViewPage.ExtraTeamUserName.size(); i++) {
            Assert.assertTrue(buViewPage.ExtraTeamUserName.get(i).isDisplayed(), "Username is not displaying");
        }
    }

    /*  This method to add teams with multiple users
      Author -Paramita */
    @And("I add teams with multiple users with {string},{string},{string},{string},{string} and select a business unit from business unit list page")
    public void addTeamsWithMultipleTeamUsers(String teamName, String teamDesc, String teamStartDate, String teamEndDate, String teamBU) {
        waitFor(2);

        TeamBUName1.set(teamListPage.bUClumVlu.get(0).getText());
        teamListPage.addButton.click();
        addTeam.get().iPopulateDataOnTeamDetailsPage(teamName, teamDesc, teamStartDate, teamEndDate, TeamBUName1.get());
        waitFor(2);
        addTeam.get().clickOnSaveButton();

        scrollToElement(teamPage.userListSection);
        waitFor(1);
        addMultipleTeamUser();
        tmProjectDetailsPage.businessUnitIcon.click();

        scrollToTop();

        List<WebElement> BUName = bunsUtListPage.businessUnitNameClumVlu;
        BUName.size();
        for (int i = 0; i <= BUName.size(); i++) {
            //if(bunsUtListPage.businessUnitNameClumVlu.get(i).getText().equalsIgnoreCase(TeamBUName1.get())) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().equalsIgnoreCase(TeamBUName1.get())) {
                jsClick(bunsUtListPage.businessUnitNameClumVlu.get(i));
                break;
            }
        }


    }

    /*  This method to verify the color code of the associated Team based on status
    Author -Paramita */
    @Then("I verify the color code of the associated Team based on {string} status")
    public void verifyColorCodeTeamStatus(String status) {
        scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
        waitFor(2);
        boolean flag3 = true;
        if (flag3)
            switch (status) {
                case "ACTIVE":
                    Assert.assertTrue(buViewPage.activeTeamStatus.isDisplayed(),
                            "Status is not displaying as active");
                    Assert.assertEquals("#43a047", getColorCode1(buViewPage.activeTeamStatus),
                            "Green color is missing");
                    break;

                case "FUTURE":
                    Assert.assertTrue(buViewPage.futureTeamStatus.isDisplayed(),
                            "Status is not displaying as future");
                    Assert.assertEquals("#fb8c00", getColorCode1(buViewPage.futureTeamStatus),
                            "Orange color is missing");
                    break;

            }
    }


    /*  This method to verify active teams displaying in first position
    Author -Paramita */
    @Then("I verify  active teams displaying in first position")
    public void verifyActiveTeamDisplayingFirst() {
        //waitFor(2);
        waitForVisibility(buViewPage.associatedTeamStatus.get(0), 10);
        int index = -1;
        for (int i = 0; i < buViewPage.associatedTeamStatus.size(); i++) {
            if (buViewPage.associatedTeamStatus.get(i).getText().equals("ACTIVE")) {
                index = i;
                Assert.assertTrue(buViewPage.associatedTeamStatus.get(index).isDisplayed(), "Active Status is not listed first");
                break;
            } else {
                Assert.assertFalse(buViewPage.associatedTeamStatus.get(index).isDisplayed(), "Active Status is listed first");
                break;
            }
        }


    }

    /*  This method to see first and last name of the associated team users on click plus icon
    Author -Paramita */
    @Then("I see first and last name of the associated team users on click plus icon in business unit page")
    public void selectTeamAssocaitedWithBU() {
        String TUser1 = teamListPage.bUClumVlu.get(0).getText();

        teamListPage.teamNameClumVluText.get(0).click();

        System.out.println("count size :" + teamPage.associatedUserEditIcon.size());
        ArrayList<String> actualUserToCompare = new ArrayList<String>();

        for (int i = 7; i < teamPage.userName.size(); i++) {
            actualUserToCompare.add(teamPage.userName.get(i).getText());
        }

        tmProjectDetailsPage.businessUnitIcon.click();

        List<WebElement> BUName_1 = bunsUtListPage.businessUnitNameClumVlu;
        BUName_1.size();
        for (int i = 0; i <= BUName_1.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().equalsIgnoreCase(TUser1)) {
                jsClick(bunsUtListPage.businessUnitNameClumVlu.get(i));
                break;
            }
        }
        waitFor(1);
        hover(buViewPage.ExtraTeamUserIcon);
        waitFor(1);
        List<String> ExtraUserNameList = new ArrayList<String>();
        for (WebElement we : buViewPage.ExtraTeamUserName) {
            ExtraUserNameList.add(we.getText());
        }
        System.out.println(actualUserToCompare);
        System.out.println(ExtraUserNameList);
        Assert.assertEquals(ExtraUserNameList, actualUserToCompare, "User name not matched");
    }
}