package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMTaskTemplatePage;
import com.maersk.crm.pages.tm.TMProjectHolidaysPage;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class TM_HolidaysStepDef extends TMUtilities {
    TM_ProjectRoleDetailsStepDef projRoleStepDef = new TM_ProjectRoleDetailsStepDef();
    TM_CreateEditPermissionGroupStepDef createPermGroupStepDef = new TM_CreateEditPermissionGroupStepDef();
    TM_AddNewUserToProjectStepDef newUserStepDef = new TM_AddNewUserToProjectStepDef();
    TM_CreateProjectStepDef createProjStepDef = new TM_CreateProjectStepDef();
    TM_TaskTemplateStepDef taskTemplateStepDef = new TM_TaskTemplateStepDef();
    TMTaskTemplatePage taskTemplate = new TMTaskTemplatePage();
    TMProjectDetailsPage projDetails = new TMProjectDetailsPage();
    TMProjectHolidaysPage projHolidays = new TMProjectHolidaysPage();

    @When("I press {string} button on Holidays of TM project")
    public void i_press_button_on_holidays_of_TM_project(String button) {
        switch (button) {
            case "Save":
                projHolidays.holidaysSaveButton.click();
                break;
            case "Cancel":
                projHolidays.holidaysCancelButton.click();
                break;
        }
    }

    @When("I input Holiday Name {string} for a holiday of TM project")
    public void i_input_holiday_name_for_a_holidays_of_TM_project(String holidayName) {
        projHolidays.holidaysName.sendKeys(holidayName);
    }

    @When("I click cancel on warning popup message for a holiday of TM project")
    public void i_click_on_Cancel_on_warning_popup_message_for_a_holidays_of_TM_project() {
        taskTemplate.WarningPopupCancelBtn.click();
        waitFor(1);
    }

    @When("I click continue on warning popup message for a holiday of TM project")
    public void i_click_on_Continue_on_warning_popup_message_for_a_holidays_of_TM_project() {
        taskTemplate.WarningPopupContinueBtn.click();
        waitFor(1);
    }

    @When("I input Holiday Date {string} for a holiday of TM project")
    public void i_input_Holiday_Date_for_a_holiday_of_TM_project(String holidayDate) {
        projHolidays.selectHolidayDate.sendKeys(holidayDate);
    }

    @Then("I see inline warning beneath the field and the field itself is highlighted in orange")
    public void i_see_inline_warning_beneath_the_field_and_the_field_itself_is_highlighted_in_orange() {
        assertTrue(projHolidays.selectDateWarningMessage.isDisplayed(), "Holiday Date on Weekend warning message is not displayed");
    }

    /*this method is to navigate to haliday page
    Author:Vidya Date:03-30-2020*/
    @Then("I navigate to Holiday Details page")
    public void iNavigateToHolidayDetailsPage() {
        Assert.assertTrue(projDetails.projConfig.isDisplayed(),"Project configuration icon is  not displayed");
        projDetails.projConfig.click();
        Assert.assertTrue(projHolidays.holidayHeader.isDisplayed(),"Holiday details page is  not displayed");
    }

    /*This method to check all selected date values
    Author -Vidya Date:29-03-2020
     */
    @Then("I verify select date are correctly configure for GA project")
    public void iVerifySelecteDate(List<String> selectDates) {
        for (int i=0;i<projHolidays.selectDate.size()-2;i++) {
            Assert.assertEquals(projHolidays.selectDate.get(i).getAttribute("value"),selectDates.get(i),
                    "Select Date value is not correct");
        }
    }

    /*This method to check all Holiday name values
    Author - Vidya Date:29-03-2020
     */
    @And("I verify holiday name are correctly configure for GA project")
    public void iVerifyHolidayNameA(List<String> holidayNames) {
        for (int i=0;i<projHolidays.holidayName.size()-2;i++) {
            Assert.assertEquals(projHolidays.holidayName.get(i).getAttribute("value").trim(),holidayNames.get(i),
                    "Holiday Name value is not correct");
        }

    }

    /*This method to check all exclude task and service request check boxes are unchecked
    Author - Vidya Date:29-03-2020
     */
    @And("I will verify all task and service request check boxes are unchecked")
    public void iVerifyCheckBoxesAreUnchecked() {
        for (int i=0;i<projHolidays.excludeForTaskCkBx.size()-2;i++) {
            Assert.assertEquals(projHolidays.excludeForTaskCkBx.get(i).getAttribute("type"),"checkbox",
                    "Type is not a check box");
            Assert.assertFalse(projHolidays.excludeForTaskCkBx.get(i).isSelected(),"Task Check box is checked");
            Assert.assertEquals(projHolidays.excludeForSrCkBx.get(i).getAttribute("type"),"checkbox",
                    "Type is not a check box");
            Assert.assertFalse(projHolidays.excludeForSrCkBx.get(i).isSelected(),"Service request Check box is checked");
        }

    }

    @And("I will click on add icon on Holidays Page")
    public void addHolidayIcon(){
        projHolidays.addHolidayBtn.click();
    }

    @When("I see Warning pop-up message displayed that holiday is on weekend")
    public void i_see_Warning_pop_up_message_displayed_holiday_weekend() {
        waitForVisibility(projHolidays.holidayWeekendError,5);
        assertTrue(projHolidays.holidayWeekendError.isDisplayed(), "Warning Pop Up is not displayed");
    }
}
