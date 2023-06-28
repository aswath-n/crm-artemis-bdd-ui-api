package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectHolidaysPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class TM_UpdateDateHoldayPageStepDef extends CRMUtilities implements ApiBase {

    TMProjectHolidaysPage projHolidays = new TMProjectHolidaysPage();

    private final String holidayDate = "12/22/2023";
    private final String holidayName = "test";

    @And("I enter the holiday details and click on save")
    public void enterHolidayDetailsAndSave() {
        int holidayDateSize = projHolidays.selectDate.size();
        clearAndFillText(projHolidays.selectDate.get(holidayDateSize - 1), holidayDate);
        waitFor(1);

        int holidayNameSize = projHolidays.holidayName.size();
        clearAndFillText(projHolidays.holidayName.get(holidayNameSize - 1), holidayName);
        waitFor(1);

        projHolidays.addHolidayBtn.click();
        waitFor(1);
        projHolidays.holidaysSaveButton.click();
    }

    @Then("I remove the added holiday")
    public void removeHolidayAdded() {
        int holidaysSize = projHolidays.removeHolidayBtn.size();
        projHolidays.removeHolidayBtn.get(holidaysSize - 1).click();
        waitFor(1);
        projHolidays.holidaysSaveButton.click();
        waitFor(2);
    }

    @And("I enter the holiday details")
    public void enterHolidayDetails() {
        int holidayDateSize = projHolidays.selectDate.size();
        clearAndFillText(projHolidays.selectDate.get(holidayDateSize - 1), holidayDate);
        waitFor(1);

        int holidayNameSize = projHolidays.holidayName.size();
        clearAndFillText(projHolidays.holidayName.get(holidayNameSize - 1), holidayName);
        waitFor(1);
    }

    public void verifyHolidayPageDisplayed(){
        Assert.assertTrue(projHolidays.holidayHeader.isDisplayed());
    }
}
