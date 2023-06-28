package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMAddNewUserPage;
import com.maersk.crm.pages.tm.TMSearchUserPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.ExcelReader;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.maersk.crm.utilities.BrowserUtils.*;
import com.maersk.crm.utilities.TMUtilities.*;

import java.util.List;

public class TM_SearchProjectUserStepDef extends BrowserUtils {


    TMSearchUserPage tmSearchUserPage = new TMSearchUserPage();
    TMAddNewUserPage tmAddNewUserPage = new TMAddNewUserPage();
    final ThreadLocal<TM_AddNewUserToProjectStepDef> tm_addNewUserToProjectStepDef = ThreadLocal.withInitial(TM_AddNewUserToProjectStepDef::new);
    final ThreadLocal<TMUtilities> tmUtilities = ThreadLocal.withInitial(TMUtilities::new);

    //By Vinuta
    @When("I click on add new user button")
    public void i_click_on_add_new_user_button() {
        Assert.assertTrue(tmSearchUserPage.addUserButton.isDisplayed());
        tmSearchUserPage.addUserButton.click();
        waitForVisibility(tmAddNewUserPage.get_userDetailsPageTitle(), 10);
    }

    //By Vinuta
    @Then("I enter search criteria for a user by {string} value {string}")
    public void i_enter_serach_criteria_for_a_user_by_value(String field, String value) {

        switch (field) {
            case "maxID":
                //If max ID is empty, user was created using random ID, so fetch that
                if (value.isEmpty())
                    value = Integer.toString(TM_AddNewUserToProjectStepDef.randomMaxID.get());
                //Generate random Max ID for next use
                //  TM_AddNewUserToProjectStepDef.randomMaxID.get()=randomNumberBetweenTwoNumbers(154000,154700);
                tmSearchUserPage.searchMaxID.sendKeys(value);
                System.out.print("Max ID entered:" + value);
                break;

            case "status":
                selectDropDown(tmSearchUserPage.searchStatusType, value.toUpperCase());
                waitFor(5);
                break;

            case "first name":
                tmSearchUserPage.searchFirstName.sendKeys(value);
                break;

            case "last name":
                tmSearchUserPage.searchLastName.sendKeys(value);
                break;

            case "email":
                tmSearchUserPage.searchEmail.sendKeys(value);
                break;
            case "accountType":
                selectDropDown(tmSearchUserPage.searchAccountType, value);
                waitFor(5);
                break;
        }
    }

    //By Vinuta
    @Then("I click on Search Button on User List Page")
    public void i_click_on_Search_Button_on_User_List_Page() {
        Assert.assertTrue(tmSearchUserPage.searchButton.isDisplayed());
        tmSearchUserPage.searchButton.click();
        waitFor(5); // to display search results
    }

    //By Vinuta
    //Example: I should see all users with "maxID" value "154269"
    //Example: I should see all users with "status" value "active"
    //Should implement all other fields as part of CRM-628
    @Then("I should see all users with {string} value {string}")
    public void i_should_see_all_users_with_value(String field, String value) {
        int i = 0;
        assertTrue(tmSearchUserPage.resultFirstNames.size() > 0,"No results found for given search criteria");
        for (i = 0 ; i < tmSearchUserPage.resultFirstNames.size() ; i++) {
            // this explicit wait allows the project details to be loaded

            waitForVisibility(tmSearchUserPage.resultFirstNames.get(i), 5);
            switch (field) {
                case "maxID":
                    assertTrue(value.equalsIgnoreCase(tmSearchUserPage.resultMaxIDs.get(i).getText()));
                    break;
                case "status":
                    assertTrue(value.equalsIgnoreCase(tmSearchUserPage.resultStatuses.get(i).getText()), "User status expected-" + value + " actual-" + tmSearchUserPage.resultStatuses.get(i).getText());
                    break;
                case "first name":
                    break;
                case "last name":
                    break;
                case "email":
                    break;
                case "account type":
                    break;
            }
        }
        System.out.println(i + " Users found for " + field + " " + value);
    }

    @Then("I select checkbox against column {string} value {string} on User List Page")
    public void i_select_checkbox_against_column_value_on_User_List_Page(String column, String value) {
        if (column.equalsIgnoreCase("maxID"))
            if (value.isEmpty())
                value = Integer.toString(TM_AddNewUserToProjectStepDef.randomMaxID.get());
        tmSearchUserPage.getCheckboxBy(value).click();
    }

    /*Author Vinuta
     This method verifies only Inactive users can be selected
     */
    @Then("I can select only Inactive users but not active users")
    public void i_can_select_only_Inactive_users_but_not_active_users() {
        int i = 0;
        for (i = 1; i <= tmSearchUserPage.resultFirstNames.size() - 1; i++) {
            // this explicit wait allows the project details to be loaded
            waitForVisibility(tmSearchUserPage.resultFirstNames.get(i), 5);
            if (tmSearchUserPage.resultStatuses.get(i).getText().equalsIgnoreCase("active")) {
                try {
                    assertFalse(tmSearchUserPage.getCheckboxValue(tmSearchUserPage.resultFirstNames.get(i).getText()), "Active users should not be selectable");
                } catch (NoSuchElementException e) {
                }
            }
            if (tmSearchUserPage.resultStatuses.get(i).getText().equalsIgnoreCase("inactive")) {
                assertTrue(tmSearchUserPage.getCheckboxValue(tmSearchUserPage.resultFirstNames.get(i).getText()), "Inactive user is not selected");
            }
        }
    }

    @Then("I click on first user to open User Details")
    public void i_click_on_first_user_to_open_User_Details() {
        waitFor(10);
        tmSearchUserPage.resultFirstNames.get(0).click();
        assertTrue(tmAddNewUserPage.userDetailsPageTitle.isDisplayed(), "User Details page is not displayed");
    }

    @When("I click on select all checkbox")
    public void i_click_on_select_all_checkbox() {
        tmSearchUserPage.selectAllUserCheckbox.click();
    }

    @Then("I click on Remove User button")
    public void i_click_on_Remove_User_button() {
        tmSearchUserPage.discardButton.click();
        waitFor(3);
    }

    @Then("I should not see user with {string} {string} on User List page")
    public void i_should_not_see_user_with_on_User_List_page(String field, String value) {
        switch (field) {
            case ("maxID"):
                for (WebElement ti : tmSearchUserPage.resultMaxIDs)
                    assertFalse(value == ti.getText(), "Max ID with value" + value + "found");
                break;
            case ("status"):
                for (WebElement ti : tmSearchUserPage.resultStatuses) {
                    assertFalse(value.equalsIgnoreCase(ti.getText()), "Status with value" + value + "found");
                }
                break;
        }
    }
}



