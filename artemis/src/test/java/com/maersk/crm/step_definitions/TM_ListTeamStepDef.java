package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMBusinessUnitListPage;
import com.maersk.crm.pages.tm.TMListOfTeamPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMProjectListPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;

public class TM_ListTeamStepDef extends BrowserUtils {

    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    final ThreadLocal<TM_AddTeamStepDef> addTeamStepDef = ThreadLocal.withInitial(TM_AddTeamStepDef::new);
    TMListOfTeamPage teamListPage = new TMListOfTeamPage();
    final ThreadLocal<TM_ListBusinessUnitStepDef> listBUStepDef = ThreadLocal.withInitial(TM_ListBusinessUnitStepDef::new);
    TMBusinessUnitListPage bunsUtListPage = new TMBusinessUnitListPage();

    String[] str = {"TEAM NAME", "BUSINESS UNIT NAME", "DESCRIPTION", "START DATE", "END DATE", "STATUS"};
    int j = 0, k = 0, n = 0;
    boolean flag = false;
    String value[] = {TM_AddTeamStepDef.teamValue.get(), TM_AddTeamStepDef.businessUnit.get(),
            TM_AddTeamStepDef.teamDescription.get(),
            TM_AddTeamStepDef.teamStartDate.get(), TM_AddTeamStepDef.teamEndDate.get()};

    /*This method to navigate to Team List Page
        Author -Vidya
    */

    @When("I navigate to team list page")
    public void navigateToTeamListPage() {
        Assert.assertTrue(tmProjectDetailsPage.teamIcon.isDisplayed(), "Team icon is  not displayed");
        tmProjectDetailsPage.teamIcon.click();
        waitForVisibility(teamListPage.teamHeader, 10);
        Assert.assertTrue(teamListPage.teamHeader.isDisplayed(), "Team List Page is not displayed");
    }

    @Then("I verify all columns headers and add button and back arrow are displayed in team list page")
    public void verifyTeamListPageColumnsHeader() {
        for (int i = 0; i < teamListPage.columnheaders.size(); i++) {
            Assert.assertEquals(teamListPage.columnheaders.get(i).getText().toUpperCase(), str[i], "Column header " + str[i] + " is not displayed");
        }
        Assert.assertTrue(teamListPage.addButton.isDisplayed(), "Add Button is not displayed");
        Assert.assertTrue(teamListPage.backArrow.isDisplayed(), "Back Arrow is not displayed");
    }

    /*This method to check navigate away functionality
       Author -Vidya
   */

    @And("I click on {string} on team list page")
    public void clickOnButton(String button) {
        switch (button) {
            case "Back Arrow":
                waitForVisibility(teamListPage.backArrow, 10);
                click(teamListPage.backArrow);
                break;

            case "BU Icon":
                waitForVisibility(tmProjectDetailsPage.businessUnitIcon, 10);
                click(tmProjectDetailsPage.businessUnitIcon);
                break;
        }

    }

    /*This method to check navigate away functionality
        Author -Vidya
    */

    @Then("I verify it is display {string} page")
    public void verifyItShouldDisplayBusinessUnitListPage(String page) {
        switch (page) {
            case "Project Details Page":
                waitForVisibility(tmProjectDetailsPage.projectDetailsTitle, 10);
                Assert.assertTrue(tmProjectDetailsPage.projectDetailsTitle.isDisplayed(), "Project Details page is not displayed");
                Assert.assertTrue(tmProjectDetailsPage.projectName.isDisplayed(), "Project Details page is not displayed");
                break;

            case "BU List page":
                waitForVisibility(bunsUtListPage.businessUnitListPage, 10);
                Assert.assertTrue(bunsUtListPage.businessUnitListPage.isDisplayed(), "Business Unit List page is not displayed");
                Assert.assertTrue(bunsUtListPage.addButton.isDisplayed(), "Business Unit List page is not displayed");
                break;
        }
    }

    /*This method to navigate to newly created team in Team List page
       Author -Vidya
   */
    @And("I navigate to newly created team")
    public void navigateToNewlyCreatedTeam() {
        waitFor(3);
        addTeamStepDef.get().clickOnButton("Back Arrow");
        //addTeamStepDef.get().clickOnContinueButtonOfWarningMsg();
        addTeamStepDef.get().verifyItShouldDisplayBusinessUnitListPage("Team List page");
        waitForVisibility(teamListPage.teamNameClumVlu.get(0), 10);
        for (int i = 0; i < teamListPage.teamNameClumVlu.size(); i++) {
            if (teamListPage.teamNameClumVlu.get(i).getText().toLowerCase().
                    equals(TM_AddTeamStepDef.teamValue.get().toLowerCase())) {
                j = i;
                flag = true;
                break;
            }
        }
    }

    /*This method to navigate to given team in Team List page
       Author -Vinuta
   */
    @And("I navigate to team {string}")
    public void navigateToTeam(String team) {
        waitFor(3);
        for (int i = 0; i < teamListPage.teamNameClumVlu.size(); i++) {
            if (teamListPage.teamNameClumVlu.get(i).getText().toLowerCase().equals(team.toLowerCase())) {
                teamListPage.teamNameClumVlu.get(i).click();
                Assert.assertTrue(addTeamStepDef.get().teamPage.userAddBtn.isDisplayed(), "Team Name field is not displayed");
                break;
            }
        }
    }


    /*This method to verify newly created team record values
       Author -Vidya
   */
    @Then("I verify Team List columns Values")
    public void verifyTeamListColumnsValue() {
        if (flag) {
            for (int i = j * 6; i < ((j * 6) + 6); i++) {
                if (k != value.length && k < value.length) {
                    Assert.assertEquals(teamListPage.allColumnsValue.get(i).getText().toLowerCase(),
                            value[k].toLowerCase(),
                            "Team is not created because " + str[n] + " is not displayed");
                } else {
                    Assert.assertTrue(teamListPage.allColumnsValue.get(i).isDisplayed(),
                            "Team is not created because status is not displayed");
                }
                k++;
                n++;
            }
        } else {
            Assert.assertTrue(false, "Team is not created");
        }
    }

    /*This method to verify newly created team record status value
       Author -Vidya
   */
    @Then("I verify team list status as {string}")
    public void verifyStatus(String status) {
        if (flag) {
            switch (status) {
                case "ACTIVE":
                    Assert.assertEquals(teamListPage.allColumnsValue.get((j * 6) + 5).getText().toUpperCase(),
                            "ACTIVE", "Status is not as Active");
                    break;
                case "FUTURE":
                    Assert.assertEquals(teamListPage.allColumnsValue.get((j * 6) + 5).getText().toUpperCase(),
                            "FUTURE", "Status is not as Future");
                    break;
                case "INACTIVE":
                    Assert.assertEquals(teamListPage.allColumnsValue.get((j * 6) + 5).getText().toUpperCase(),
                            "INACTIVE", "Status is not as Inactive");
                    break;
            }
        } else {
            Assert.assertTrue(false, "Team is not created to check status");
        }
    }

    /*This method to check in team list page has at least 5 records if not then
           it add 6 records to Team List Page
       Author -Vidya
    */
    @And("I check weather it as 5 team records if not I add 5 team records")
    public void checkIfItHas5Team() {
        if (teamListPage.teamNameClumVlu.size() < 5) {
            String[] sd = {"today", "+1", "+2", "today", "today", "+1"};
            String[] ed = {"today", "+1", "", "", "+2", "+2"};
            for (int i = 0; i < 6; i++) {
                clickOnButton("BU Icon");
                listBUStepDef.get().checkAtleastOneBusinessUnitIsPresent("{random}", "{random}", "today", "+5");
                addTeamStepDef.get().navigateToTeamDetailsPage();
                addTeamStepDef.get().iPopulateDataOnTeamDetailsPage("{random}", "{random}", sd[i], ed[i], "");
                addTeamStepDef.get().clickOnSaveButton();
                /*addTeamStepDef.get().clickOnAddButton();*/
            }
        }
    }

    /*This method to check sorting order of team records present on
    Team List Page
       Author -Vidya
   */
    @Then("I verify team records are in according to sorted order")
    public void verifySortingOrderOfTeamRecords() {
        waitForVisibility(teamListPage.statusClumVlu.get(0), 10);
        Assert.assertTrue(ascendingOrderTexts(teamListPage.statusClumVlu), "Records are not in ascending order");

        ArrayList<String> active = new ArrayList<>();
        ArrayList<String> future = new ArrayList<>();
        ArrayList<String> inActive = new ArrayList<>();

        for (int i = 0; i < teamListPage.allColumnsValue.size(); ) {
            if (teamListPage.allColumnsValue.get(i + 5).getText().equalsIgnoreCase("ACTIVE")) {
                active.add(teamListPage.allColumnsValue.get(i).getText());
            } else if (teamListPage.allColumnsValue.get(i + 5).getText().equalsIgnoreCase("FUTURE")) {
                future.add(teamListPage.allColumnsValue.get(i).getText());
            } else if (teamListPage.allColumnsValue.get(i + 5).getText().equalsIgnoreCase("INACTIVE")) {
                inActive.add(teamListPage.allColumnsValue.get(i).getText());
            }
            i += 6;
        }
        ArrayList<String> copy = new ArrayList(active);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(active, copy, "With in Active status records are not sorted according to Team Name");

        copy = new ArrayList(future);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(future, copy, "With in Future status records are not sorted according to Team Name");

        copy = new ArrayList(inActive);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(inActive, copy, "With in Inactive status records are not sorted according to Team Name");
    }
}
