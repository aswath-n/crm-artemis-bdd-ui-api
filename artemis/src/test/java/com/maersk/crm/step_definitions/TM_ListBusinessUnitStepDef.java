package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APITMAddBusinessUnitController;
import com.maersk.crm.pages.tm.*;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.internal.Arrays;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TM_ListBusinessUnitStepDef extends BrowserUtils {

    TMAddBusinessUnitPage businessUnitPage = new TMAddBusinessUnitPage();
    TMBusinessUnitListPage bunsUtListPage = new TMBusinessUnitListPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TM_AddBusinessUnitStepDef addBUStepDef = new TM_AddBusinessUnitStepDef();
    TMListOfTeamPage teamListPage = new TMListOfTeamPage();
    TMAddTeamPage teamPage = new TMAddTeamPage();
    TM_AddTeamStepDef addTeam = new TM_AddTeamStepDef();


    String[] str = {"BUSINESS UNIT NAME", "DESCRIPTION", "START DATE", "END DATE", "STATUS"};
    int j = 0, k = 0, n = 0;
    boolean flag = false;
    String value[] = {TM_AddBusinessUnitStepDef.businessUnitValue.get(), TM_AddBusinessUnitStepDef.descriptionValue.get(),
            TM_AddBusinessUnitStepDef.startDateValue.get(), TM_AddBusinessUnitStepDef.endDateValue.get()};
    public static ArrayList<String> buName = new ArrayList<>();

    /*This method to navigate to Business Unit List Page
        Author -Vidya
    */

    @When("I navigate to business unit list  page")
    public void navigateToBusinessUnitListPage() {
        Assert.assertTrue(tmProjectDetailsPage.businessUnitIcon.isDisplayed(), "Business unit icon is  not displayed");
        tmProjectDetailsPage.businessUnitIcon.click();
        //  waitForVisibility(bunsUtListPage.businessUnitListPage,10);
        // Assert.assertTrue(bunsUtListPage.businessUnitListPage.isDisplayed(), "Business Unit List Page is not displayed");
    }

    @Then("I verify all columns headers and add button and back arrow are displayed")
    public void verifyBusinessUnitListPageColumnsHeader() {
        for (int i = 0; i < bunsUtListPage.columnsHeader.size(); i++) {
            Assert.assertEquals(bunsUtListPage.columnsHeader.get(i).getText().toUpperCase(), str[i], "Column header " + str[i] + " is not displayed");
        }
        Assert.assertTrue(bunsUtListPage.addButton.isDisplayed(), "Add Button is not displayed");
        Assert.assertTrue(bunsUtListPage.backArrow.isDisplayed(), "Back Arrow is not displayed");
    }

    /*This method to navigate to newly created business unit in business unit List page
       Author -Vidya
   */
    @And("I navigate to newly created business unit")
    public void navigateToNewlyCreatedBusinessUnit() {
        waitFor(3);
        waitForVisibility(bunsUtListPage.businessUnitNameClumVlu.get(0), 10);
        for (int i = 0; i < bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().toLowerCase().
                    equals(TM_AddBusinessUnitStepDef.businessUnitValue.get().toLowerCase())) {
                j = i;
                flag = true;
                break;
            }

        }
    }

    /*This method to verify newly created business unit record values
       Author -Vidya
   */
    @Then("I verify Business Unit List columns Values")
    public void verifyBusinessUnitListColumnsValue() {
        if (flag) {
            for (int i = j * 5; i < ((j * 5) + 5); i++) {
                if (k != value.length && k < value.length) {
                    Assert.assertEquals(bunsUtListPage.allColumnsValue.get(i).getText().toLowerCase(),
                            value[k].toLowerCase(),
                            "Business Unit is not created because " + str[n] + " is not displayed");
                } else {
                    Assert.assertTrue(bunsUtListPage.allColumnsValue.get(i).isDisplayed(),
                            "Business Unit is not created because status is not displayed");
                }
                k++;
                n++;
            }
        } else {
            Assert.assertTrue(false, "Business Unit is not created");
        }
    }

    /*This method to verify newly created business unit record status value
       Author -Vidya
   */
    @Then("I verify business unit list status as {string}")
    public void verifyStatus(String status) {
        if (flag) {
            switch (status) {
                case "ACTIVE":
                    Assert.assertEquals(bunsUtListPage.allColumnsValue.get((j * 5) + 4).getText().toUpperCase(),
                            "ACTIVE", "Status is not as Active");
                    break;
                case "FUTURE":
                    Assert.assertEquals(bunsUtListPage.allColumnsValue.get((j * 5) + 4).getText().toUpperCase(),
                            "FUTURE", "Status is not as Future");
                    break;
                case "INACTIVE":
                    Assert.assertEquals(bunsUtListPage.allColumnsValue.get((j * 5) + 4).getText().toUpperCase(),
                            "INACTIVE", "Status is not as Inactive");
                    break;
            }
        } else {
            Assert.assertTrue(false, "Business Unit is not created to check status");
        }
    }

    /*This method to check navigate away functionality
       Author -Vidya
   */
    @And("I click on {string} on business unit list page")
    public void clickOnButton(String button) {
        switch (button) {
            case "Back Arrow":
                waitForVisibility(bunsUtListPage.backArrow, 10);
                click(bunsUtListPage.backArrow);
                break;

            case "Team Icon":
                waitForVisibility(tmProjectDetailsPage.teamIcon, 10);
                click(tmProjectDetailsPage.teamIcon);
                break;
        }

    }

    /*This method to check navigate away functionality
       Author -Vidya
   */
    @Then("I verify it is display {string}")
    public void verifyItShouldDisplayBusinessUnitListPage(String page) {
        switch (page) {
            case "Project Details Page":
                waitForVisibility(tmProjectDetailsPage.projectDetailsTitle, 10);
                Assert.assertTrue(tmProjectDetailsPage.projectDetailsTitle.isDisplayed(), "Project Details page is not displayed");
                Assert.assertTrue(tmProjectDetailsPage.projectName.isDisplayed(), "Project Details page is not displayed");
                break;

            case "Team List page":
                waitForVisibility(businessUnitPage.teamListPage, 10);
                Assert.assertTrue(businessUnitPage.teamListPage.isDisplayed(), "Team List page is not displayed");
                Assert.assertTrue(businessUnitPage.addButton.isDisplayed(), "Team List page is not displayed");
                break;
        }
    }

    /*This method to check in business unit list page has at least 5 records if not then
           it add 6 records to Business Unit List Page
       Author -Vidya
    */
    @And("I check whether it has 5 business units, if not, I add 5 business units")
    public void checkIfItHas5BusinessUnit() {
        if (bunsUtListPage.businessUnitNameClumVlu.size() < 5) {
            String[] sd = {"today", "+1", "+2", "today", "today", "+1"};
            String[] ed = {"today", "+1", "", "", "+2", "+2"};
            for (int i = 0; i < 6 - bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
                addBUStepDef.clickOnAddButton();
                addBUStepDef.i_populate_data_on_project_role_page("{random}", "{random}", sd[i], ed[i]);
                addBUStepDef.clickOnSaveButton();
            }
        }
    }

    /*This method to check sorting order of business unit records present on
    Business Unit List Page
       Author -Vidya
   */
    @Then("I verify business unit records are in sorted order")
    public void verifySortingOrderOfBusinessUnitRecords() {
        waitForVisibility(bunsUtListPage.statusClumVlu.get(0), 10);
        Assert.assertTrue(ascendingOrderTexts(bunsUtListPage.statusClumVlu), "Records are not in ascending order");

        ArrayList<String> active = new ArrayList<>();
        ArrayList<String> future = new ArrayList<>();
        ArrayList<String> inActive = new ArrayList<>();

        for (int i = 0; i < bunsUtListPage.allColumnsValue.size(); ) {
            if (bunsUtListPage.allColumnsValue.get(i + 4).getText().equalsIgnoreCase("ACTIVE")) {
                active.add(bunsUtListPage.allColumnsValue.get(i).getText());
                buName.add(bunsUtListPage.allColumnsValue.get(i).getText());
            } else if (bunsUtListPage.allColumnsValue.get(i + 4).getText().equalsIgnoreCase("FUTURE")) {
                future.add(bunsUtListPage.allColumnsValue.get(i).getText());
                buName.add(bunsUtListPage.allColumnsValue.get(i).getText());
            } else if (bunsUtListPage.allColumnsValue.get(i + 4).getText().equalsIgnoreCase("INACTIVE")) {
                inActive.add(bunsUtListPage.allColumnsValue.get(i).getText());
            }
            i += 5;
        }

        ArrayList<String> copy = new ArrayList(active);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(active, copy, "With in Active status records are not sorted according to Business Unit Name");

        copy = new ArrayList(future);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(future, copy, "With in Future status records are not sorted according to Business Unit Name");

        copy = new ArrayList(inActive);
        Collections.sort(copy, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(inActive, copy, "With in Inactive status records are not sorted according to Business Unit Name");
    }

    /*This method to verify at least one Business unit record is present if not then
       it create the Business unit records
        Author -Vidya
    */

    @And("I check whether it has business unit, if not, I will create business unit {string},{string},{string},{string}")
    public void checkAtleastOneBusinessUnitIsPresent(String buName, String desc, String buStartDate, String buEndDate) {
        String startDate = "";
        if (buStartDate.equals("today")) startDate = getCurrentDate().
                replaceAll("/", "");
        else if (buStartDate.contains("-")) {
            startDate = getPriorDate(Integer.parseInt(buStartDate.replace("-", "")));
        } else if (buStartDate.contains("+")) {
            startDate = getGreaterDate(Integer.parseInt(buStartDate.replace("+", "")));
        }

        String endDate = "";
        if (buEndDate.equals("today")) endDate = getCurrentDate().
                replaceAll("/", "");
        else if (buEndDate.contains("-")) {
            endDate = getPriorDate(Integer.parseInt(buEndDate.replace("-", "")));
        } else if (buEndDate.contains("+")) {
            endDate = getGreaterDate(Integer.parseInt(buEndDate.replace("+", "")));
        }

        boolean flag = true;
        for (int i = 0; i < bunsUtListPage.startDateClumVlu.size(); i++) {
            if (bunsUtListPage.startDateClumVlu.get(i).getText().
                    replaceAll("/", "").equalsIgnoreCase(startDate)) {
                if (bunsUtListPage.endDateClumVlu.get(i).getText().
                        replaceAll("/", "").equalsIgnoreCase(endDate)) {
                    TM_AddTeamStepDef.businessUnit.set(bunsUtListPage.businessUnitNameClumVlu.get(i).getText());
                    TM_ViewBusinessUnitStepDef.businessUnit.set( bunsUtListPage.businessUnitNameClumVlu.get(i).getText());
                    TM_ViewBusinessUnitStepDef.startDate.set( bunsUtListPage.startDateClumVlu.get(i).getText());
                    TM_ViewBusinessUnitStepDef.endDate.set( bunsUtListPage.endDateClumVlu.get(i).getText());
                    TM_ViewBusinessUnitStepDef.description.set( bunsUtListPage.descriptionClumVlu.get(i).getText());
                    TM_ViewBusinessUnitStepDef.status.set(bunsUtListPage.statusClumVlu.get(i).getText());
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            bunsUtListPage.addButton.click();
            addBUStepDef.i_populate_data_on_project_role_page(buName, desc, buStartDate, buEndDate);
            TM_AddTeamStepDef.businessUnit.set(TM_AddBusinessUnitStepDef.businessUnitValue.get());
            TM_ViewBusinessUnitStepDef.businessUnit.set( TM_AddBusinessUnitStepDef.businessUnitValue.get());
            TM_ViewBusinessUnitStepDef.startDate.set( TM_AddBusinessUnitStepDef.startDateValue.get());
            TM_ViewBusinessUnitStepDef.endDate.set( TM_AddBusinessUnitStepDef.endDateValue.get());
            TM_ViewBusinessUnitStepDef.description.set( TM_AddBusinessUnitStepDef.descriptionValue.get());
            addBUStepDef.clickOnSaveButton();
            addBUStepDef.verifySaveFunctionality();
        }
    }

    /*This method to verify at least one Business unit record is present with status Inactive
        Author -Vidya
    */

    @And("I check whether it has business unit with status Inactive")
    public void checkIthasBUWithInactiveStatus() {
        for (int i = 0; i < bunsUtListPage.statusClumVlu.size(); i++) {
            if (bunsUtListPage.statusClumVlu.get(i).getText().equalsIgnoreCase("INACTIVE")) {
                TM_ViewBusinessUnitStepDef.businessUnit.set( i + "");
                TM_ViewBusinessUnitStepDef.flag.set(true);
                break;
            }
        }
    }

    /*This method to take all business unit which has End Date null or > Current Date on
    Business Unit List Page
    (Note:-don't take business unit which has End date= current date)
       Author -Vidya
   */
    @And("I will take all business unit which has End Date null or => Current Date")
    public void takingBUWhichHasEndDateNullOrGreaterThanOrEquellToCurrentDate() {
        for (int i = 0; i < bunsUtListPage.endDateClumVlu.size(); i++) {
            if (bunsUtListPage.endDateClumVlu.get(i).getText().isEmpty()) {
                buName.add(bunsUtListPage.businessUnitNameClumVlu.get(i).getText());
            } else {
                Date date1 = convertStringToDate(bunsUtListPage.endDateClumVlu.get(i).getText());
                Date date2 = convertStringToDate(getCurrentDate());
                if (bunsUtListPage.endDateClumVlu.get(i).getText().equals(getCurrentDate())) {
                    buName.add(bunsUtListPage.businessUnitNameClumVlu.get(i).getText());
                } else if (date1.after(date2)) {
                    buName.add(bunsUtListPage.businessUnitNameClumVlu.get(i).getText());
                }
            }
        }
        Collections.sort(buName, String.CASE_INSENSITIVE_ORDER);
    }

    @Then("I verify Associated Team section in Business Unit detail page")
    public void verify_associated_teams_section_in_business_unit_detail_page() {
        scrollToElement(bunsUtListPage.businessUnitAssociatedTeam);
        waitFor(2);
        Assert.assertTrue(bunsUtListPage.businessUnitAssociatedTeam.isDisplayed(), "Associated Team(s) section is not displayed");

    }


    @And("I select newly created business unit")
    public void clickOnNewlyCreatedBusinessUnit() {
        waitFor(3);
        waitForVisibility(bunsUtListPage.businessUnitNameClumVlu.get(0), 10);
        for (int i = 0; i < bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
            if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().toLowerCase().
                    equals(TM_AddBusinessUnitStepDef.businessUnitValue.get().toLowerCase())) {
                j = i;
                flag = true;
                break;
            }
            bunsUtListPage.businessUnitNameClumVlu.get(i).click();
        }

    }


    @And("I will check business unit is associated with a team else with create a team with business unit {string},{string},{string},{string},{string}")
    public void createTeam(String teamName, String teamDesc, String teamStartDate, String teamEndDate, String teamBU) {

        if (teamListPage.teamrowVlu.size() > 0) {

            String TName = teamListPage.teamNameClumVlu.get(0).getText();
            String TeamBUName = teamListPage.bUClumVlu.get(0).getText();
            if (!TeamBUName.equals("")) {
                tmProjectDetailsPage.businessUnitIcon.click();
            }
            for (int i = 0; i < bunsUtListPage.businessUnitNameClumVlu.size(); i++) {
                if (bunsUtListPage.businessUnitNameClumVlu.get(i).getText().
                        equals(TeamBUName)) {
                    bunsUtListPage.businessUnitNameClumVlu.get(i).click();
                    waitFor(2);
                    break;
                }


            }

        } else {
            teamListPage.addButton.click();
            addTeam.iPopulateDataOnTeamDetailsPage(teamName, teamDesc, teamStartDate, teamEndDate, teamBU);

            addTeam.clickOnSaveButton();
            waitFor(2);
            System.out.println(teamPage.allFieldsVlu.get(0).getText());
            System.out.println(teamPage.allFieldsVlu.get(1).getText());
            System.out.println(teamPage.allFieldsVlu.get(2).getText());
            System.out.println(teamPage.allFieldsVlu.get(3).getText());

        }

    }


}
