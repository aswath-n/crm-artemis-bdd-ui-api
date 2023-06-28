package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMBusinessDaysPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class TM_BusinessDaysStepDef extends BrowserUtils {
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMBusinessDaysPage tmBusinessDaysPage = new TMBusinessDaysPage();

    final ThreadLocal<List<String>> tempStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> tempEndDate = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<List<String>> businessDays = ThreadLocal.withInitial(ArrayList::new);

    public static final ThreadLocal<String> businessDayNameValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> startDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> endDateValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> taskIndicator = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> srIndicator = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<String> businessDaySunValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDayMonValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDayTueValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDayWedValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDayThuValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDayFriValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> businessDaySatValue = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Boolean> businessDaySunCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDayMonCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDayTueCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDayWedCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDayThuCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDayFriCBStatus = ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> businessDaySatCBStatus = ThreadLocal.withInitial(() -> false);

    @Given("I navigate to Business Days Page")
    public void navigate_to_business_days_page() {
        Assert.assertTrue(tmProjectDetailsPage.projConfig.isDisplayed(), "Project configuration icon is not displayed");
        tmProjectDetailsPage.projConfig.click();
        scrollUpRobot();
        waitFor(1);
        tmProjectDetailsPage.businessDaysNavigation.click();
        waitFor(1);
    }

    @Then("I see a Warning Message permanently displayed at the bottom")
    public void i_see_warning_message_displayed_at_the_bottom() {
        Assert.assertTrue(tmBusinessDaysPage.warningMsg1.isDisplayed(), "Warning message is not displayed");
        Assert.assertEquals(tmBusinessDaysPage.businessDays.getText(), "MON-FRI", "Task type icon is  not displayed");
    }

    @When("I click Add Business Days")
    public void i_click_add_business_days() {
        waitFor(3);
        tmBusinessDaysPage.addButton.click();
    }

    @When("I populate data on business days details page {string},{string},{string},{string},{string}")
    public void i_populate_data_on_project_role_page(String businessDayName, String startDate, String endDate, String excludeForTask, String excludeForSR) {
        waitFor(1);
//        tmBusinessDaysPage.addButton.click();
        if (businessDayName.equals("{random}")) {
            businessDayNameValue.set(getRandomString(20));
            if (Character.isLowerCase(businessDayNameValue.get().charAt(0))) {
                businessDayNameValue.set(Character.toUpperCase(businessDayNameValue.get().charAt(0)) +
                        businessDayNameValue.get().substring(1));
            }
            clearAndFillText(tmBusinessDaysPage.businessDaysName, businessDayNameValue.get());
        } else {
            businessDayNameValue.set(businessDayName);
            clearAndFillText(tmBusinessDaysPage.businessDaysName, businessDayNameValue.get());
        }
        System.out.println("Business Day Name: " + businessDayNameValue.get());

        startDateValue.set(startDate);
        if (startDate.equals("today")) startDateValue.set(getCurrentDate());
        else if (startDate.contains("-")) {
            startDateValue.set(getPriorDate(Integer.parseInt(startDate.replace("-", ""))));
        } else if (startDate.contains("+")) {
            startDateValue.set(getGreaterDate(Integer.parseInt(startDate.replace("+", ""))));
        }
        if (!startDateValue.get().equals("") && startDateValue.get() != null) {
            clearAndFillText(tmBusinessDaysPage.startDate, startDateValue.get());
        }

        endDateValue.set(endDate);
        if (endDate.equals("today")) endDateValue.set(getCurrentDate());
        else if (endDate.contains("-")) {
            endDateValue.set(getPriorDate(Integer.parseInt(endDate.replace("-", ""))));
        } else if (endDate.contains("+")) {
            endDateValue.set(getGreaterDate(Integer.parseInt(endDate.replace("+", ""))));
        }
        if (!endDateValue.get().equals("") && endDateValue.get() != null) {
            clearAndFillText(tmBusinessDaysPage.endDate, endDateValue.get());
        }

        if (!excludeForTask.isEmpty()) {
            jsClick(tmBusinessDaysPage.isExcludeTask);
            taskIndicator.set(true);
        } else
            taskIndicator.set(false);

        if (!excludeForSR.isEmpty()) {
            jsClick(tmBusinessDaysPage.isExcludeServiceRequest);
            srIndicator.set(true);
        } else
            srIndicator.set(false);
    }

    @And("I select business days {string},{string},{string},{string},{string},{string},{string}")
    public void i_select_business_days(String sun, String mon, String tue, String wed, String thu, String fri, String sat) {
        if (!sun.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.sunCB);
            businessDaySunValue.set("SUN");
            businessDaySunCBStatus.set(true);
            businessDays.get().add(businessDaySunValue.get());
        } else {
            businessDaySunValue.set("");
            businessDaySunCBStatus.set(false);
        }

        if (!mon.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.monCB);
            businessDayMonValue.set("MON");
            businessDayMonCBStatus.set(true);
            businessDays.get().add(businessDayMonValue.get());
        } else {
            businessDayMonValue.set("");
            businessDayMonCBStatus.set(false);
        }

        if (!tue.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.tueCB);
            businessDayTueValue.set("TUE");
            businessDayTueCBStatus.set(true);
            businessDays.get().add(businessDayTueValue.get());
        } else {
            businessDayTueValue.set("");
            businessDayTueCBStatus.set(false);
        }

        if (!wed.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.wedCB);
            businessDayWedValue.set("WED");
            businessDayWedCBStatus.set(true);
            businessDays.get().add(businessDayWedValue.get());
        } else {
            businessDayWedValue.set("");
            businessDayWedCBStatus.set(false);
        }

        if (!thu.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.thuCB);
            businessDayThuValue.set("THU");
            businessDayThuCBStatus.set(true);
            businessDays.get().add(businessDayThuValue.get());
        } else {
            businessDayThuValue.set("");
            businessDayThuCBStatus.set(false);
        }

        if (!fri.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.friCB);
            businessDayFriValue.set("FRI");
            businessDayFriCBStatus.set(true);
            businessDays.get().add(businessDayFriValue.get());
        } else {
            businessDayFriValue.set("");
            businessDayFriCBStatus.set(false);
        }

        if (!sat.isEmpty()) {
            waitFor(1);
            jsClick(tmBusinessDaysPage.satCB);
            businessDaySatValue.set("SAT");
            businessDaySatCBStatus.set(true);
            businessDays.get().add(businessDaySatValue.get());
        } else {
            businessDaySatValue.set("");
            businessDaySatCBStatus.set(false);
        }
        waitFor(1);
    }

    @And("I click on Save button on business days details page")
    public void clickOnSaveButton() {
        scrollToElement(tmBusinessDaysPage.saveButton);
        waitFor(1);
        click(tmBusinessDaysPage.saveButton);
        Assert.assertTrue(tmBusinessDaysPage.successMessage.isDisplayed(), "successMessage is not displayed");
        Assert.assertTrue(tmBusinessDaysPage.businessDaysSuccessMsg.isDisplayed(), "businessDaysSuccessMsg is not displayed");
    }

//    @And("I verify time frame is added on the screen")
//    public void verifyTimeFrameAddedOnTheScreen() {
//        boolean elementPresent = false;
//        scrollUpRobot();
//        waitFor(1);
//        scrollUpUsingActions(100);
//        if (startDateValue.contains("/"))
//            startDateValue.set( startDateValue.replace("/", "");
//        //s.substring(s.length()-4);
//        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.substring(startDateValue.length()-4)))
//            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.substring(startDateValue.length()-4));
//        waitFor(2);
//
//        for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
//            if (tmBusinessDaysPage.lblNames.get(i).getText().equalsIgnoreCase(businessDayNameValue.get())) {
//                Assert.assertEquals(tmBusinessDaysPage.lblStartDates.get(i).getText().replace("/", ""), startDateValue, "Start Date not matches");
//                Assert.assertEquals(tmBusinessDaysPage.EndDates.get(i).getAttribute("value").replace("/", ""), endDateValue.get(), "End Date not matches");
//                waitFor(1);
//                String dad = tmBusinessDaysPage.isExcludeTaskFromCurrentTF.get(i).getAttribute("value");
//                String dsd = String.valueOf(taskIndicator);
//                Assert.assertEquals(tmBusinessDaysPage.isExcludeTaskFromCurrentTF.get(i).getAttribute("value"), String.valueOf(taskIndicator), "taskIndicator not matches");
//                waitFor(1);
//                String dads = tmBusinessDaysPage.isExcludeServiceRequestCurrentTF.get(i).getAttribute("value");
//                String dssd = String.valueOf(srIndicator);
//                Assert.assertEquals(tmBusinessDaysPage.isExcludeServiceRequestCurrentTF.get(i).getAttribute("value"), String.valueOf(srIndicator), "srIndicator not matches");
//                Assert.assertEquals(tmBusinessDaysPage.sunListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDaySunCBStatus), "sun CB status not matches");
////                String bbb= tmBusinessDaysPage.monListCurrentTF.get(i).getAttribute("value");
////                String n = String.valueOf(businessDayMonCBStatus);
//                Assert.assertEquals(tmBusinessDaysPage.monListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayMonCBStatus), "mon CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.tueListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayTueCBStatus), "tue CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.wedListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayWedCBStatus), "wed CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.thuListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayThuCBStatus), "thu CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.friListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayFriCBStatus), "fri CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.satListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDaySatCBStatus), "sat CB status not matches");
//
//                elementPresent = true;
//                break;
//            }
//
//        }
//        if (!elementPresent) {
//            fail("Time frame not added");
//        }
//    }


    @And("I verify time frame is added on the screen")
    public void verifyTimeFrameAddedOnTheScreen() {
        boolean elementPresent = false;
        int count = 0;
        scrollUpRobot();
        waitFor(1);
        scrollUpUsingActions(100);
        if (startDateValue.get().contains("/"))
            startDateValue.set(startDateValue.get().replace("/", ""));
//        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length()-4)))
//            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length()-4));
//        waitFor(2);

        if (startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))
                && tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4))) {
            verifyTimeFrame();
        } else if (startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))
                && !tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4))) {
            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length() - 4));
            verifyTimeFrame();
        } else if (!startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))) {
            verifyTimeFrame();
            selectDropDown(tmBusinessDaysPage.selectYear, endDateValue.get().substring(endDateValue.get().length() - 4));
            verifyTimeFrame();
        } else
            fail("condition not matches");
    }

    public void verifyTimeFrame() {
        boolean elementPresent = false;
        for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
            if (tmBusinessDaysPage.lblNames.get(i).getText().equalsIgnoreCase(businessDayNameValue.get())) {
                Assert.assertEquals(tmBusinessDaysPage.lblStartDates.get(i).getText().replace("/", ""), startDateValue.get(), "Start Date not matches");
                Assert.assertEquals(tmBusinessDaysPage.EndDates.get(i).getAttribute("value").replace("/", ""), endDateValue.get(), "End Date not matches");
                Assert.assertEquals(tmBusinessDaysPage.isExcludeTaskFromCurrentTF.get(i).getAttribute("value"), String.valueOf(taskIndicator.get()), "taskIndicator not matches");
                Assert.assertEquals(tmBusinessDaysPage.isExcludeServiceRequestCurrentTF.get(i).getAttribute("value"), String.valueOf(srIndicator.get()), "srIndicator not matches");
                Assert.assertEquals(tmBusinessDaysPage.sunListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDaySunCBStatus.get()), "sun CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.monListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayMonCBStatus.get()), "mon CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.tueListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayTueCBStatus.get()), "tue CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.wedListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayWedCBStatus.get()), "wed CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.thuListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayThuCBStatus.get()), "thu CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.friListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDayFriCBStatus.get()), "fri CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.satListCurrentTF.get(i).getAttribute("value"), String.valueOf(businessDaySatCBStatus.get()), "sat CB status not matches");
                elementPresent = true;
                break;
            }

        }
        if (!elementPresent) {
            fail("Time frame not added");
        }
    }

//    @And("I verify time frame is in the future")
//    public void verifyTimeFrameIsInTheFuture() {
//        boolean elementPresent = false;
//        scrollUpRobot();
//        waitFor(1);
//        scrollUpUsingActions(100);
//        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length()-4)))
//            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length()-4));
//        waitFor(2);
//
//        for (int i = 0; i < tmBusinessDaysPage.editableTimeFrames.size(); i++) {
//            if (tmBusinessDaysPage.businessDaysNames.get(i).getAttribute("value").equalsIgnoreCase(businessDayNameValue.get())) {
//                Assert.assertEquals(tmBusinessDaysPage.startDates.get(i).getAttribute("value").replace("/", ""), startDateValue.get(), "Start Date not matches");
//                Assert.assertEquals(tmBusinessDaysPage.editTimeFrameEndDates.get(i).getAttribute("value").replace("/", ""), endDateValue.get(), "End Date not matches");
//                Assert.assertEquals(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value"), String.valueOf(taskIndicator), "taskIndicator not matches");
//                Assert.assertEquals(tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i).getAttribute("value"), String.valueOf(srIndicator), "srIndicator not matches");
//                Assert.assertEquals(tmBusinessDaysPage.sunListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDaySunCBStatus), "sun CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.monListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayMonCBStatus), "mon CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.tueListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayTueCBStatus), "tue CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.wedListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayWedCBStatus), "wed CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.thuListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayThuCBStatus), "thu CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.friListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayFriCBStatus), "fri CB status not matches");
//                Assert.assertEquals(tmBusinessDaysPage.satListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDaySatCBStatus), "sat CB status not matches");
//                elementPresent = true;
//                break;
//            }
//        }
//        if (!elementPresent) {
//            fail("Time frame not added");
//        }
//    }

    @And("I verify time frame is in the future")
    public void verifyTimeFrameIsInTheFuture() {
//        boolean elementPresent = false;
        scrollUpRobot();
        waitFor(1);
        scrollUpUsingActions(100);
//        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length()-4)))
//            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length()-4));
        waitFor(2);
//
        if (startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))
                && tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4))) {
            verifyFutureTimeFrame();
        } else if (startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))
                && !tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4))) {
            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length() - 4));
            verifyFutureTimeFrame();
        } else if (!startDateValue.get().substring(startDateValue.get().length() - 4).equalsIgnoreCase(endDateValue.get().substring(endDateValue.get().length() - 4))) {
            verifyFutureTimeFrame();
            selectDropDown(tmBusinessDaysPage.selectYear, endDateValue.get().substring(endDateValue.get().length() - 4));
            verifyFutureTimeFrame();
        } else
            fail("condition not matches");

    }

    public void verifyFutureTimeFrame() {
        boolean elementPresent = false;

        for (int i = 0; i < tmBusinessDaysPage.editableTimeFrames.size(); i++) {
            if (tmBusinessDaysPage.businessDaysNames.get(i).getAttribute("value").equalsIgnoreCase(businessDayNameValue.get())) {
                Assert.assertEquals(tmBusinessDaysPage.startDates.get(i).getAttribute("value").replace("/", ""), startDateValue.get(), "Start Date not matches");
                Assert.assertEquals(tmBusinessDaysPage.editTimeFrameEndDates.get(i).getAttribute("value").replace("/", ""), endDateValue.get(), "End Date not matches");
                Assert.assertEquals(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value"), String.valueOf(taskIndicator.get()), "taskIndicator not matches");
                Assert.assertEquals(tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i).getAttribute("value"), String.valueOf(srIndicator.get()), "srIndicator not matches");
                Assert.assertEquals(tmBusinessDaysPage.sunListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDaySunCBStatus.get()), "sun CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.monListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayMonCBStatus.get()), "mon CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.tueListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayTueCBStatus.get()), "tue CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.wedListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayWedCBStatus.get()), "wed CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.thuListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayThuCBStatus.get()), "thu CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.friListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDayFriCBStatus.get()), "fri CB status not matches");
                Assert.assertEquals(tmBusinessDaysPage.satListFutureTF.get(i).getAttribute("value"), String.valueOf(businessDaySatCBStatus.get()), "sat CB status not matches");
                elementPresent = true;
                break;
            }
        }
        if (!elementPresent) {
            fail("Time frame not added");
        }
    }

    @And("I edit the fields in a future time frame {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_edit_the_fields_in_a_time_frame(String businessDayName, String startDate, String endDate, String excludeForTask, String excludeForSR, String sun, String mon, String tue, String wed, String thu, String fri, String sat) {
//        List<String> businessDays = null;
        synchronized (businessDays) {
            businessDays.set(new ArrayList<>());
        }
        boolean elementPresent = false;
        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4)))
            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length() - 4));

        for (int i = 0; i < tmBusinessDaysPage.editableTimeFrames.size(); i++) {
            if (tmBusinessDaysPage.businessDaysNames.get(i).getAttribute("value").equalsIgnoreCase(businessDayNameValue.get())) {
                //Name
                if (businessDayName.equals("{random}")) {
                    businessDayNameValue.set(getRandomString(20));
                    if (Character.isLowerCase(businessDayNameValue.get().charAt(0))) {
                        businessDayNameValue.set(Character.toUpperCase(businessDayNameValue.get().charAt(0)) +
                                businessDayNameValue.get().substring(1));
                    }
                } else {
                    businessDayNameValue.set(businessDayName);
                }
                clearAndFillText(tmBusinessDaysPage.businessDaysNames.get(i), businessDayNameValue.get());
                System.out.println("Business Day Name: " + businessDayNameValue.get());

                //Start Date
                startDateValue.set(startDate);
                if (startDate.equals("today")) startDateValue.set(getCurrentDate());
                else if (startDate.contains("-")) {
                    startDateValue.set(getPriorDate(Integer.parseInt(startDate.replace("-", ""))));
                } else if (startDate.contains("+")) {
                    startDateValue.set(getGreaterDate(Integer.parseInt(startDate.replace("+", ""))));
                }
                if (!startDateValue.get().equals("") && startDateValue.get() != null) {
                    clearAndFillText(tmBusinessDaysPage.startDates.get(i), startDateValue.get());
                }

                //End Date
                endDateValue.set(endDate);
                if (endDate.equals("today")) endDateValue.set(getCurrentDate());
                else if (endDate.contains("-")) {
                    endDateValue.set(getPriorDate(Integer.parseInt(endDate.replace("-", ""))));
                } else if (endDate.contains("+")) {
                    endDateValue.set(getGreaterDate(Integer.parseInt(endDate.replace("+", ""))));
                }
                if (!endDateValue.get().equals("") && endDateValue.get() != null) {
                    clearAndFillText(tmBusinessDaysPage.editTimeFrameEndDates.get(i), endDateValue.get());
                }

                //Exclude for task
                if (!excludeForTask.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i));
                    }
                    taskIndicator.set(true);
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i));
                    }
                    taskIndicator.set(false);
                }

                //Exclude for SR
                if (!excludeForSR.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value"))) {
                    if (!tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i));
                    }
                    srIndicator.set(true);
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.isExcludeTaskFromFutureTF.get(i).getAttribute("value"))) {
                    if (tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.isExcludeServiceRequestFutureTF.get(i));
                    }
                    srIndicator.set(false);
                }

                //BD SUN
                if (!sun.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.sunListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.sunListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.sunListFutureTF.get(i));
                    }
                    businessDaySunValue.set("SUN");
                    businessDaySunCBStatus.set(true);
                    businessDays.get().add(businessDaySunValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.sunListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.sunListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.sunListFutureTF.get(i));
                    }
                    businessDaySunValue.set("");
                    businessDaySunCBStatus.set(false);
                }

                //BD MON
                if (!mon.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.monListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.monListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.monListFutureTF.get(i));
                    }
                    businessDayMonValue.set("MON");
                    businessDayMonCBStatus.set(true);
                    businessDays.get().add(businessDayMonValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.monListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.monListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.monListFutureTF.get(i));
                    }
                    businessDayMonValue.set("");
                    businessDayMonCBStatus.set(false);
                }

                //BD TUE
                if (!tue.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.tueListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.tueListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.tueListFutureTF.get(i));
                    }
                    businessDayTueValue.set("TUE");
                    businessDayTueCBStatus.set(true);
                    businessDays.get().add(businessDayTueValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.tueListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.tueListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.tueListFutureTF.get(i));
                    }
                    businessDayTueValue.set("");
                    businessDayTueCBStatus.set(false);
                }

                //BD Wed
                if (!wed.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.wedListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.wedListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.wedListFutureTF.get(i));
                    }
                    businessDayWedValue.set("WED");
                    businessDayWedCBStatus.set(true);
                    businessDays.get().add(businessDayWedValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.wedListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.wedListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.wedListFutureTF.get(i));
                    }
                    businessDayWedValue.set("");
                    businessDayWedCBStatus.set(false);
                }

                //BD Thu
                if (!thu.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.thuListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.thuListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.thuListFutureTF.get(i));
                    }
                    businessDayThuValue.set("THU");
                    businessDayThuCBStatus.set(true);
                    businessDays.get().add(businessDayThuValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.thuListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.thuListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.thuListFutureTF.get(i));
                    }
                    businessDayThuValue.set("");
                    businessDayThuCBStatus.set(false);
                }

                //BD Fri
                if (!fri.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.friListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.friListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.friListFutureTF.get(i));
                    }
                    businessDayFriValue.set("FRI");
                    businessDayFriCBStatus.set(true);
                    businessDays.get().add(businessDayFriValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.friListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.friListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.friListFutureTF.get(i));
                    }
                    businessDayFriValue.set("");
                    businessDayFriCBStatus.set(false);
                }

                //BD Sat
                if (!sat.isEmpty()) {
//                    if(!Boolean.parseBoolean(tmBusinessDaysPage.satListFutureTF.get(i).getAttribute("value")))
                    if (!tmBusinessDaysPage.satListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.satListFutureTF.get(i));
                    }
                    businessDaySatValue.set("SAT");
                    businessDaySatCBStatus.set(true);
                    businessDays.get().add(businessDaySatValue.get());
                } else {
//                    if(Boolean.parseBoolean(tmBusinessDaysPage.satListFutureTF.get(i).getAttribute("value")))
                    if (tmBusinessDaysPage.satListFutureTF.get(i).isSelected()) {
                        jsClick(tmBusinessDaysPage.satListFutureTF.get(i));
                    }
                    businessDaySatValue.set("");
                    businessDaySatCBStatus.set(false);
                }
                elementPresent = true;
                break;
            }
        }
        if (!elementPresent) {
            fail("Time frame not added");
        }
    }

    @And("I can edit the End Date {string} field in current time frame")
    public void i_can_edit_end_date_field_in_current_time_frame(String endDate) {
        boolean elementPresent = false;
        scrollUpRobot();
        waitFor(1);
        scrollUpUsingActions(100);
        if (startDateValue.get().contains("/"))
            startDateValue.set(startDateValue.get().replace("/", ""));
        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4)))
            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length() - 4));
        waitFor(2);

        for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
            if (tmBusinessDaysPage.lblNames.get(i).getText().equalsIgnoreCase(businessDayNameValue.get())) {
                clearAndFillText(tmBusinessDaysPage.EndDates.get(i), startDateValue.get());
                click(tmBusinessDaysPage.saveButton);
                Assert.assertTrue(tmBusinessDaysPage.endDateErrorMsg.isDisplayed(), "End Date error message is not displayed");

                endDateValue.set(endDate);
                if (endDate.equals("today")) endDateValue.set(getCurrentDate());
                else if (endDate.contains("-")) {
                    endDateValue.set(getPriorDate(Integer.parseInt(endDate.replace("-", ""))));
                } else if (endDate.contains("+")) {
                    endDateValue.set(getGreaterDate(Integer.parseInt(endDate.replace("+", ""))));
                }
                if (!endDateValue.get().equals("") && endDateValue.get() != null) {
                    scrollUpRobot();
                    waitFor(1);
                    scrollUpUsingActions(100);
                    waitFor(2);
                    clearAndFillText(tmBusinessDaysPage.EndDates.get(i), endDateValue.get());
                }
                clickOnSaveButton();
                waitFor(1);
                elementPresent = true;
                break;
            }
        }
        if (!elementPresent) {
            fail("Time frame not added");
        }
    }

    // if the field has tag name input. then it is editable. if the tag name is other than input then the field is not editable(for text box)
    @And("I verify i cannot edit the fields in time frame")
    public void i_verify_i_cannot_edit_the_fields() {
        boolean elementPresent = false;
        scrollUpRobot();
        waitFor(1);
        scrollUpUsingActions(100);
        if (startDateValue.get().contains("/"))
            startDateValue.set(startDateValue.get().replace("/", ""));
        if (!tmBusinessDaysPage.defaultYear.getAttribute("value").equals(startDateValue.get().substring(startDateValue.get().length() - 4)))
            selectDropDown(tmBusinessDaysPage.selectYear, startDateValue.get().substring(startDateValue.get().length() - 4));
        waitFor(2);

        for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
            if (tmBusinessDaysPage.lblNames.get(i).getText().equalsIgnoreCase(businessDayNameValue.get())) {
                assertFalse(tmBusinessDaysPage.lblNames.get(i).getTagName().equalsIgnoreCase("input"), "Name field is editable");
                assertFalse(tmBusinessDaysPage.lblStartDates.get(i).getTagName().equalsIgnoreCase("input"), "Start Date field is editable");
                assertTrue(tmBusinessDaysPage.EndDates.get(i).getTagName().equalsIgnoreCase("input"), "End Date field is not editable");
                assertFalse(tmBusinessDaysPage.isExcludeTaskFromCurrentTF.get(i).isEnabled(), "Exclude for Task CB is Enabled");
                assertFalse(tmBusinessDaysPage.isExcludeServiceRequestCurrentTF.get(i).isEnabled(), "Exclude for SR CB is Enabled");
                assertFalse(tmBusinessDaysPage.sunListCurrentTF.get(i).isEnabled(), "Business Day SUN CB is Enabled");
                assertFalse(tmBusinessDaysPage.monListCurrentTF.get(i).isEnabled(), "Business Day MON CB is Enabled");
                assertFalse(tmBusinessDaysPage.tueListCurrentTF.get(i).isEnabled(), "Business Day TUE CB is Enabled");
                assertFalse(tmBusinessDaysPage.wedListCurrentTF.get(i).isEnabled(), "Business Day WED CB is Enabled");
                assertFalse(tmBusinessDaysPage.thuListCurrentTF.get(i).isEnabled(), "Business Day THU CB is Enabled");
                assertFalse(tmBusinessDaysPage.friListCurrentTF.get(i).isEnabled(), "Business Day FRI CB is Enabled");
                assertFalse(tmBusinessDaysPage.satListCurrentTF.get(i).isEnabled(), "Business Day SAT CB is Enabled");
                elementPresent = true;
                break;
            }
        }
        if (!elementPresent) {
            fail("Time frame not added");
        }
    }

    @When("I verify changes on the screen after clicking cancel and continue buttons")
    public void i_verify_changes_on_the_screen_after_clicking_cancel_and_continue_buttons() {
        //IF I select Cancel, then my changes remain but are not saved and I remain on the same UI
        scrollToElement(tmBusinessDaysPage.cancelButton);
        waitFor(1);
        tmBusinessDaysPage.cancelButton.click();
        waitForVisibility(tmBusinessDaysPage.navigationWarningMsg, 5);
        Assert.assertTrue(tmBusinessDaysPage.navigationWarningMsg.isDisplayed(), "navigationWarningMsg is not displayed");
        jsClick(tmBusinessDaysPage.warningMsgCancelBtn);
        scrollToElement(tmBusinessDaysPage.addButton);
        Assert.assertTrue(tmBusinessDaysPage.businessDaysName.getAttribute("value").equalsIgnoreCase(businessDayNameValue.get()), "Name not matches");
        Assert.assertEquals(tmBusinessDaysPage.startDate.getAttribute("value").replace("/", ""), startDateValue.get(), "Start Date not matches");
        Assert.assertEquals(tmBusinessDaysPage.endDate.getAttribute("value").replace("/", ""), endDateValue.get(), "End Date not matches");

        //IF I select Continue, then my changes are reverted and I remain on the same UI
        scrollToElement(tmBusinessDaysPage.cancelButton);
        waitFor(1);
        tmBusinessDaysPage.cancelButton.click();
        waitForVisibility(tmBusinessDaysPage.navigationWarningMsg, 5);
        Assert.assertTrue(tmBusinessDaysPage.navigationWarningMsg.isDisplayed(), "navigationWarningMsg is not displayed");
        jsClick(tmBusinessDaysPage.warningMsgContinueBtn);
        waitFor(1);
        scrollToElement(tmBusinessDaysPage.addButton);
        if (tmBusinessDaysPage.timeFrames.size() != 0) {
            for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
                Assert.assertFalse(tmBusinessDaysPage.lblNames.get(i).getText().equalsIgnoreCase(businessDayNameValue.get()), "Name matches");
            }
        }
        if (tmBusinessDaysPage.editableTimeFrames.size() != 0) {
            for (int i = 0; i < tmBusinessDaysPage.editableTimeFrames.size(); i++) {
                Assert.assertFalse(tmBusinessDaysPage.businessDaysNames.get(i).getAttribute("value").equalsIgnoreCase(businessDayNameValue.get()), "Name matches");
            }
        }
    }

    @Then("I verify system displays conflict error message on the screen")
    public void i_verify_system_displays_conflict_error_message() {
        Assert.assertTrue(tmBusinessDaysPage.conflictsErrorMsg.isDisplayed(), "conflictsErrorMsg is not displayed");
    }

    @Then("I am able to copy the Time Frames from the previous year")
    public void i_am_able_to_copy_the_time_frames_from_the_previous_year() {
        int yearVal = tmBusinessDaysPage.getDefaultYear();

        while (tmBusinessDaysPage.copyPreviousCalendarList.isEmpty()) {
            yearVal++;
            selectDropDown(tmBusinessDaysPage.selectYear, String.valueOf(yearVal));
            if (!tmBusinessDaysPage.copyPreviousCalendarList.isEmpty())
                break;
        }

        tmBusinessDaysPage.copyPreviousCalendar.click();
        waitForVisibility(TMBusinessDaysPage.TIME_FRAME, 5);

        if (tmBusinessDaysPage.timeFrames.size() != 0) {
            for (int i = 0; i < tmBusinessDaysPage.timeFrames.size(); i++) {
                tempStartDate.get().add(tmBusinessDaysPage.lblStartDates.get(i).getText());
            }
            Assertions.assertThat(tmBusinessDaysPage.getYears(tempStartDate.get())).containsOnly(String.valueOf(yearVal));
        }

        if (tmBusinessDaysPage.editableTimeFrames.size() != 0) {
            for (int i = 0; i < tmBusinessDaysPage.editableTimeFrames.size(); i++) {
                tempStartDate.get().add(tmBusinessDaysPage.startDates.get(i).getAttribute("value"));
            }
            Assertions.assertThat(tmBusinessDaysPage.getYears(tempStartDate.get())).containsOnly(String.valueOf(yearVal));
        }
    }

    @Then("I verify navigation warning message displayed")
    public void i_verify_warning_message_displayed() {
        waitForVisibility(tmBusinessDaysPage.navigationWarningMsg, 5);
        Assert.assertTrue(tmBusinessDaysPage.navigationWarningMsg.isDisplayed(), "navigationWarningMsg is not displayed");
        Assert.assertTrue(tmBusinessDaysPage.warningMsgCancelBtn.isDisplayed(), "navigationWarningMsg is not displayed");
        Assert.assertTrue(tmBusinessDaysPage.warningMsgContinueBtn.isDisplayed(), "navigationWarningMsg is not displayed");

    }

    @Then("I verify start date can not be past error message displayed")
    public void i_verify_start_date_error_message_displayed() {
        Assert.assertTrue(tmBusinessDaysPage.startDateCanNotBePastErrorMsg.isDisplayed(), "Start Date can not be past error message is not displayed");
    }

    @And("I verify name field")
    public void i_verify_name_field() {
        tmBusinessDaysPage.businessDaysName.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        scrollToElement(tmBusinessDaysPage.saveButton);
        click(tmBusinessDaysPage.saveButton);
        scrollToElement(tmBusinessDaysPage.addButton);
        Assert.assertTrue(tmBusinessDaysPage.nameRequiredErrorMsg.isDisplayed(), "Name required error message is not displayed");
        assertEquals(tmBusinessDaysPage.businessDaysName.getAttribute("maxlength"), "50", "maxlength not matches for Name field");
        clearAndFillText(tmBusinessDaysPage.businessDaysName, getRandomString(45) + " 99!@    ");
        assertEquals(tmBusinessDaysPage.businessDaysName.getAttribute("value").length(), 50, "Name is accepting more then 50 character");
    }

}