package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMDemographicMemberPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_DashboardStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMDemographicMemberPage crmDemographicMemberPage = new CRMDemographicMemberPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();

    @When("I expand active roles list")
    public void expandRolesList() {
        waitFor(2);
        dashBoard.clickOnRoleDropDown();
    }

    @Then("I verify that all active {string} displayed for the user")
    public void verifyRolesDisplayed(String roles) {
        ArrayList<String> actualRoles = dashBoard.getListOfActiveRoles();
        ArrayList<String> expRoles = new ArrayList<String>();
        for (String role : roles.split(",")) {
            expRoles.add(role);
        }
        // Assert.assertTrue(expRoles.containsAll(actualRoles));
        Assert.assertEquals(actualRoles, expRoles);
    }

    @When("I select a role {string} from role list")
    public void selectRole(String role) {
        dashBoard.selectRole(role);
    }

    @Then("I verify that {string} is selected")
    public void verifySelectedRoleDisplayed(String role) {
        waitFor(2);
        Assert.assertEquals(dashBoard.getSelectedRole(), role);
    }

    @Then("I verify warning popup displayed with message {string}")
    public void verifyPopUpMessage(String expMessage) {
        waitForVisibility(dashBoard.popUpMessage, 3);
        Assert.assertEquals(dashBoard.getPopUpMessage(), expMessage);
    }

    @When("I click cancel button on role change warning pop up")
    public void clickCancelRoleCHnagePopUp() {
        waitForVisibility(dashBoard.popUpCancelButton, 3);
        dashBoard.clickPopUpCancelButton();
    }

    @When("I click continue button on role change warning pop up")
    public void clickContinueRoleCHnagePopUp() {
        waitForVisibility(dashBoard.popUpContinueButton, 3);
        dashBoard.clickPopUpContinueButton();
    }

    @Then("I verify {string} is displayed for the session")
    public void i_verify_is_displayed_for_the_session(String defaultrole) {
        Assert.assertEquals(dashBoard.getSelectedRole(), defaultrole);
    }
    @When ("I click case consumer search tab")
    public void clickCaseConsumerSearchTab() {
        waitFor(4);
        jsClick(dashBoard.case_ConsumerSearchTab);
        waitFor(2);
    }

    @When ("I click active contact tab")
    public void clickActiveContactTabTab() {
        waitFor(1);
        jsClick(dashBoard.activeContactTab);
        hover(contactRecord.contactReason);
    }

    @Then("I verify warning is displayed with message {string}")
    public void verifyWarningMessage(String expMessage) {
        waitForVisibility(dashBoard.warningPopUpMessage, 3);
        Assert.assertEquals(dashBoard.warningPopUpMessage.getText(), expMessage);
    }

    @Then("I verify warning message is not displayed")
    public void verifyWarningMessageNotDisplayed() {
        waitFor(1);
        Assert.assertTrue(!isElementDisplayed(dashBoard.warningPopUpMessage));
    }

    @When("I click on the Add button for Authorized Representative")
    public void clickAddButtonForAuthorizedRepresentative() {
        scrollToElement(crmDemographicMemberPage.addARButton);
        waitFor(2);
        jsClick(crmDemographicMemberPage.addARButton);
    }
    /**
     * Author :Shruti
     * Verifies the title of the Page
     */
    @Then("I verify page title is displayed as {string}")
    public void verifyPageTitle(String title) {
        waitFor(4);
        System.out.println("Title here  "+Driver.getDriver().getTitle());
        assertEquals(Driver.getDriver().getTitle().replaceAll(" "," "), title);
    }

    @When ("I click contact search tab for Contact Record")
    public void clickContactSearchTab() {
        waitFor(1);
        jsClick(dashBoard.contactRecordSearchTab);
    }

    /*This method to Verify user lands on dashboard page
    Author -Vidya
     */

    @Then("I verify home dashboard page is displayed")
    public void verifyHomeDashboardPageIsDisplayed() {
        sa.get().assertThat(isElementDisplayed(contactRecord.initContact)).as("Home page is not displayed").isTrue();
    }

    @When("I click on first consumerID for Authorized Representative")
    public void i_click_on_first_consumerID_for_Authorized_Representative() {
        waitFor(2);
        jsClick(crmDemographicMemberPage.firstARconsumer);
    }
}