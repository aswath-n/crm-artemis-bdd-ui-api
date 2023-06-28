package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CRM_ViewFutureEligibilitySectionStepDef extends BrowserUtils {

    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    final ThreadLocal<ApiTestDataUtil> apiTestDataUtil = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);

    private List<String>countDownDays=new ArrayList<>();
   private List<String>hoverText;
   private List<String>hoverDates;
    private List<Integer>countDownDaysIndex=new ArrayList<>();



    @And("I view the Future Eligibility Section")
    public void iViewTheFutureEligibilitySection() {
        if (programAndBenefitInfoPage.futureEligibilityFieldList.size() > 0) {
            for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityFieldList.size(); i++) {
                assertTrue(programAndBenefitInfoPage.futureEligibilityFieldList.get(i).isDisplayed());
            }
        }
    }

    @Then("I see the {string} for all the consumers")
    public void i_see_the_for_all_the_consumers(String status) {
        status = status.toUpperCase();
        switch (status) {
            case "BENEFIT STATUS":
                if (programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.size() > 0) {
                    for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.size(); i++) {
                        assertTrue(programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.get(i).isDisplayed());
                    }
                }
                break;
            case "ELIGIBILITY STATUS":
                if (programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.size() > 0) {
                    for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.size(); i++) {
                        assertTrue(programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.get(i).isDisplayed());
                    }
                }
                break;
            default:
                Assert.fail("Status field name " + status + " not defined.");
        }
    }

    @Then("Future Eligibility records contains following Benefit Statuses:")
    public void future_Eligibility_records_contains_following_Benefit_Statuses(List<String> expectListOfBenefitStatus) {
        if (programAndBenefitInfoPage.futureEligibilityFieldList.size() > 0) {
            for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityFieldList.size(); i++) {
                assertTrue(programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.get(i).getText().contains(expectListOfBenefitStatus.get(i)),
                        "The Benefit Status Field does not contain the " + expectListOfBenefitStatus.get(i));
            }
        }
    }

    @Then("I hover over the {string} I see {string} text is displayed/not-displayed")
    public void iHoverOverTheISeeTextIsDisplayed(String status, String statusText) {
        status = status.toUpperCase();
        for (int i = 0; i <programAndBenefitInfoPage.listOfCountDownDays.size() ; i++) {
            countDownDays.add(programAndBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS",""));
        }
       switch (status) {
          case "BENEFIT STATUS":
                if (programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.size() > 0) {
                    for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.size(); i++) {
                       hover(programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.get(i));
                        waitFor(3);
                        assertTrue(programAndBenefitInfoPage.futureEligibilityBenefitStatusFieldList.get(i).getAttribute("title").equalsIgnoreCase(statusText));
                    }
               }
                break;
            case "ELIGIBILITY STATUS":
                if (programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.size() > 0) {
                    for (int i = 0; i < programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.size(); i++) {
                        hover(programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.get(i));
                        waitFor(3);
                        assertTrue(programAndBenefitInfoPage.futureEligibilityEligibilityStatusFieldList.get(i).getAttribute("title").equalsIgnoreCase(statusText));
                    }
                }
                break;
           case"CALENDAR ICON":
                if(statusText.equalsIgnoreCase("PRE-LOCKIN - WINDOW")){
                    scrollUpUsingActions(2);
                    for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                       if(!countDownDays.get(i).equalsIgnoreCase("-- --")){
                           hover(programAndBenefitInfoPage.calendarIcon.get(i));
                        hover(programAndBenefitInfoPage.calendarIcon.get(0));
                        waitFor(3);
                  assertTrue(programAndBenefitInfoPage.calendarHoverPreLokin.getText().equalsIgnoreCase(statusText),"The "+statusText+" does mot match");
                  assertTrue(programAndBenefitInfoPage.calendarHoverPreLokinDates.isDisplayed(),"The "+statusText+" dates are not displayed");
                       }
                  }
               }else if(statusText.equalsIgnoreCase("ANNIVERSARY - WINDOW")){
                   for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                       scrollUpUsingActions(2);
                       if (!countDownDays.get(i).equalsIgnoreCase("-- --")) {
                           hover(programAndBenefitInfoPage.calendarIcon.get(i));
                           hover(programAndBenefitInfoPage.calendarIcon.get(0));
                           waitFor(3);
                           assertTrue(programAndBenefitInfoPage.calendarHoverAnniversary.getText().equalsIgnoreCase(statusText), "The " + statusText + " does mot match");
                           assertTrue(programAndBenefitInfoPage.calendarHoverAnniversaryDates.isDisplayed(), "The " + statusText + " dates are not displayed");
                       }
                   }
                   //}
               }else if(statusText.equalsIgnoreCase("PRE-LOCKIN - WINDOW dates")){
                   for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                       if (!countDownDays.get(i).equalsIgnoreCase("-- --")) {
                           hover(programAndBenefitInfoPage.calendarIcon.get(i));
                           waitFor(1);
                           assertFalse(isElementDisplayed(programAndBenefitInfoPage.calendarHoverPreLokin),"The PRE-LOCKIN - WINDOW is displayed");
                           assertFalse(isElementDisplayed(programAndBenefitInfoPage.calendarHoverPreLokinDates),"The "+statusText+" is displayed");
                       }
                   }
               }else if(statusText.equalsIgnoreCase("ANNIVERSARY - WINDOW dates")){
                   for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                       if (!countDownDays.get(i).equalsIgnoreCase("-- --")) {
                           hover(programAndBenefitInfoPage.calendarIcon.get(i));
                           waitFor(1);
                           assertFalse(isElementDisplayed(programAndBenefitInfoPage.calendarHoverAnniversary),"The ANNIVERSARY - WINDOW is displayed");
                           assertFalse(isElementDisplayed(programAndBenefitInfoPage.calendarHoverAnniversaryDates),"The "+statusText+" is displayed");
                       }
                   }
               }
               break;
           case"COUNT DOWN WITH NO ENROLL ACTION":
               scrollUpUsingActions(2);
               for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                   if(countDownDays.get(i).equalsIgnoreCase("-- --")){
                       hover(programAndBenefitInfoPage.infoIcon.get(i));
                       waitFor(1);
                       assertFalse(isElementDisplayed(programAndBenefitInfoPage.hoverElementIconInfo),"The hover over message is dispalyed");
                   }
               }
               break;
            default:
                Assert.fail("Status " + status + " is not defined");
        }
    }
    @When("I hover over {string}")
    public void i_hover_over(String section) {
        section=section.toUpperCase();
        for (int i = 0; i <programAndBenefitInfoPage.listOfCountDownDays.size() ; i++) {
            countDownDays.add(programAndBenefitInfoPage.listOfCountDownDays.get(i).getText().replace(" DAYS",""));
        }
        switch (section){
            case "COUNT DOWN":
                hoverText=new ArrayList<>();
                for (int i = 0; i <programAndBenefitInfoPage.infoIcon.size() ; i++) {
                    if (!countDownDays.get(i).equalsIgnoreCase("-- --")) {
                        hover(programAndBenefitInfoPage.infoIcon.get(i));
                        waitFor(2);
                        hoverText.add(programAndBenefitInfoPage.hoverElementIconInfo.getText());
                    }
                }
                break;
            case "CALENDAR ICON":
                hoverText=new ArrayList<>();
                for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                    hover(programAndBenefitInfoPage.calendarIcon.get(i));
                    waitFor(1);
                    hoverText.add(programAndBenefitInfoPage.calendarHoverIcon.getText());
                }
                break;
            case"CALENDAR ICON WITH NO PLAN CHANGE ACTION":
            case"CALENDAR ICON WITH NO ENROLL ACTION":
                hoverText=new ArrayList<>();
                for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                    if(countDownDays.get(i).equalsIgnoreCase("-- --")){
                        hover(programAndBenefitInfoPage.calendarIcon.get(i));
                        waitFor(1);
                        hoverText.add(programAndBenefitInfoPage.emptyDatesFromImportantDatesHover.getText());
                    }
                }
                break;
            case"COUNT DOWN WITH NO ENROLL ACTION":
                hoverText=new ArrayList<>();
                for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                    if(countDownDays.get(i).equalsIgnoreCase("-- --")){
                        hover(programAndBenefitInfoPage.infoIcon.get(i));
                        waitFor(1);
                        hoverText.add(programAndBenefitInfoPage.emptyDatesFromImportantDatesHover.getText());
                        assertFalse(isElementDisplayed(programAndBenefitInfoPage.hoverElementIconInfo),"The hover over message is dispalyed");
                    }
                }
             break;
            case"CALENDAR ICON WITH ENROLL ACTION":
                hoverText=new ArrayList<>();
                hoverDates=new ArrayList<>();
                for (int i = 0; i <programAndBenefitInfoPage.calendarIcon.size() ; i++) {
                    click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
                    waitFor(1);
                    if(isElementDisplayed(programAndBenefitInfoPage.futureEligibilityEnrollBttn)) {
                        hover(programAndBenefitInfoPage.calendarIcon.get(i));
                        countDownDaysIndex.add(i);
                        waitFor(1);
                        hoverText.add(programAndBenefitInfoPage.calendarHoverTextLine1.getText());
                        hoverDates.add(programAndBenefitInfoPage.datesFromImportantDatesHover.getText());
//                        if(isElementDisplayed(programAndBenefitInfoPage.calendarHoverTextLine2)){
//                        hoverText.add(programAndBenefitInfoPage.calendarHoverTextLine2.getText());
//                        hoverDates.add(programAndBenefitInfoPage.calendarHoverPreLokinDates.getText());
//                        }
//                        if(isElementDisplayed(programAndBenefitInfoPage.calendarHoverTextLine3)){
//                        hoverText.add(programAndBenefitInfoPage.calendarHoverTextLine3.getText());
//                        hoverDates.add(programAndBenefitInfoPage.calendarHoverAnniversaryDates.getText());}
//                        click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));
                    }else{
                        click(programAndBenefitInfoPage.listOfConsumerExpandButtons.get(i));

                    }}
                break;
            default:
                Assert.fail(section+" is not defined in switch statement");
        }

    }
    @Then("I see {string} text displayed")
    public void i_see_text_displayed(String text) {
            for (int i = 0; i <hoverText.size() ; i++) {
                assertTrue(hoverText.get(i).equalsIgnoreCase(text),
                        "The hover over text does not mach " + text);
            }

    }

    @Then("I verify {string} text is displayed on {string}")
    public void iVerifyTextIsDisplayedOn(String expectedText, String section) {
        section=section.toUpperCase();
        switch (section){
            case"HOVER OVER CALENDAR TEXT":
                assertTrue(hoverText.get(0).equalsIgnoreCase(expectedText),"The "+expectedText+" is not displayed");
                break;
            case"HOVER OVER CALENDAR TEXT AS FIRST IN ORDER":
                assertEquals(hoverText.indexOf("ENROLL"),0,"The "+expectedText+" is not displayed at order first");
                break;
            default:
                Assert.fail("The "+section+" is not declared in switch statement");
        }
    }
    @Then("I verify count down days are calculated correct")
    public void i_verify_count_down_days_are_calculated_correct() {
        for (int i = 0; i <countDownDaysIndex.size() ; i++) {
            assertEquals(daysDifference(getCurrentDate(),hoverDates.get(i).substring(13)),
                    Integer.parseInt(programAndBenefitInfoPage.listOfCountDownDays.get(countDownDaysIndex.get(i)).getText().replace(" DAYS", "")), "The days count doesn't match");
            if(hoverDates.size()>1){
            assertFalse(daysDifference(getCurrentDate(),hoverDates.get(i+1).substring(13))==
                    Integer.parseInt(programAndBenefitInfoPage.listOfCountDownDays.get(countDownDaysIndex.get(i)).getText().replace(" DAYS", "")),
                    "The days counts calculated by PRE-LOCKIN - WINDOW");}
            if(hoverDates.size()>2){
            assertFalse(daysDifference(getCurrentDate(),hoverDates.get(i+2).substring(13))==
                    Integer.parseInt(programAndBenefitInfoPage.listOfCountDownDays.get(countDownDaysIndex.get(i)).getText().replace(" DAYS", "")),
                    "The days counts calculated by ANNIVERSARY - WINDOW");}
        }
    }
    @Then("I verify below details on newly created current enrollment segment")
    public void verifyPlanDetail(List<String> fieldsToValidate){
        waitFor(5);
        String expValue = null;
        String actValue = "";
        for(String field:fieldsToValidate){
            switch(field){
                case "Enrollment Start Date":
                    expValue = apiTestDataUtil.get().getStartDate("firstDayofPresntMonth");
                    actValue = programAndBenefitInfoPage.currentEnrollmentPlanStartDate.getText();
                    break;
                case "Enrollment End Date":
//                    expValue = apiTestDataUtil.get().getEndDate("lastDayofpresentMonth");
                    expValue = "-- --";
                    actValue = programAndBenefitInfoPage.currentEnrollmentPlanEndDate.getText();
                    break;
                case "Selection Status":
//                    expValue = "Disenroll Requested";
                    expValue = "Selection Made";
                    actValue = programAndBenefitInfoPage.currentEnrollmentSelectionStatus.getText();
                    break;
                case "Channel":
                    expValue = CRM_CloseContactRecordStepDef.selectedChannel.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.currentEnrollmentChannel.getText();
                    break;
                case "Plan name":
                    expValue = CRMEnrollmentUpdateStepDef.selectedPlanName.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.currentEnrollmentPlanName.getText();
                    break;
                default:
                    assertFalse(false, "Field validation is not implemented for: "+field);
            }
            assertEquals(actValue.toLowerCase(), expValue.toLowerCase(), "assertion failed for the field validation: "+field);
        }
    }


    @Then("I verify below details on newly created future enrollment segment")
    public void verifyFutureEnrollmentPlanDetail(List<String> fieldsToValidate){
        waitFor(5);
        String expValue = null;
        String actValue = "";
        for(String field:fieldsToValidate){
            switch(field){
                case "Enrollment Start Date":
                    expValue = CRMEnrollmentUpdateStepDef.selectedPlanStartDate.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.futureEnrollmentPlanStartDate.get(0).getText();
                    break;
                case "Enrollment End Date":
                    expValue = CRMEnrollmentUpdateStepDef.selectedPlanEndDate.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.futureEnrollmentPlanEndDate.getText();
                    break;
                case "Selection Status":
                    expValue = "Selection Made";
                    actValue = programAndBenefitInfoPage.futureEnrollmentSelectionStatus.getText();
                    break;
                case "Channel":
                    expValue = CRM_CloseContactRecordStepDef.selectedChannel.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.futureEnrollmentChannel.getText();
                    break;
                case "Plan name":
                    expValue = CRMEnrollmentUpdateStepDef.selectedPlanName.get().toLowerCase();
                    actValue = programAndBenefitInfoPage.futureEnrollmentPlanName.getText();
                    break;
                default:
                    assertFalse(false, "Field validation is not implemented for: "+field);
            }
            assertEquals(actValue.toLowerCase(), expValue.toLowerCase(), "assertion faile for the field validation: "+field);
        }
    }
    @When("I verify start date is first day of next month")
    public void i_verify_start_date_is_first_day_of_next_month() {
        waitFor(5);
        assertEquals(apiTestDataUtil.get().getStartDate("firstdayofNextMonth"),getText(programAndBenefitInfoPage.futureEligibilityPlanStartDate));
    }

    @When("I verify start date is first day of present month")
    public void i_verify_start_date_is_first_day_of_present_month() {
        waitFor(5);
        Assert.assertEquals(apiTestDataUtil.get().getStartDate("firstDayofPresntMonth"),programAndBenefitInfoPage.currentEligibilityPlanStartDate.get(0).getText());
    }

    @Then("I verify consumer enrollment action Plan Start Date is >= to a Plan Enrollment Start Date")
    public void i_verify_consumer_enrollment_action_Plan_Start_Date_is_to_a_Plan_Enrollment_Start_Date() {
        waitFor(2); // let all data to be loaded in all fields otherwise default value is "-- --"
        DateTimeFormatter df = DateTimeFormatter .ofPattern("MM/dd/yyyy");
        List<WebElement> allCaseMembers = programAndBenefitInfoPage.accountsList;
        System.out.println(allCaseMembers.size() + " case members displayed");
        assertTrue(allCaseMembers.size() > 0, "Something went wrong! Not able to get consumers information!");
        int count = 1;
        for (WebElement caseMember : allCaseMembers) {
            System.out.println("case member " + count + ":");
            LocalDate eligibility = LocalDate.parse(programAndBenefitInfoPage.getEligibilityStartDateFromAccount(caseMember).getText(), df);
            LocalDate enrollment = LocalDate.parse(programAndBenefitInfoPage.getEnrollmentStartDateFromAccount(caseMember).getText(), df);
            System.out.println("Plan Enrollment Start Date is " + eligibility);
            System.out.println("Enrollment action Plan Start Date is " + enrollment);
            assertTrue((eligibility.isBefore(enrollment) || eligibility.isEqual(enrollment)),
                    "Enrollment action Plan Start Date is before a Plan Enrollment Start Date!");
            count++;
        }
    }

    @And("I add all case members to plan selection")
    public void I_add_all_case_members_to_plan_selection() {
        System.out.println("Adding all case members to plan selection ...");
        waitForClickablility(programAndBenefitInfoPage.addCaseMembersButton, 2).click();
        waitForClickablility(programAndBenefitInfoPage.selectAllDropdownSelection, 2).click();
    }
}
