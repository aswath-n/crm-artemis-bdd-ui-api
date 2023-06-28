package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class CRM_NavigateThroughContactDashboardStepDef extends CRMUtilities implements ApiBase {

    final ThreadLocal<Api_Storage> stg = ThreadLocal.withInitial(Api_Storage::getInstance);

    CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMProgramAndBenefitInfoPage crmProgramAndBenefitInfoPage = new CRMProgramAndBenefitInfoPage();


    public final ThreadLocal<String> caseConsumerName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> caseConsumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> additionalCommentText = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<List<String>> eligibleConsumersForCahmiSurvey = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<List<String>> eligibleConsumersForHraSurvey = ThreadLocal.withInitial(ArrayList::new);

    @When("I click on {string} Tab on Contact Dashboard Page")
    public void i_click_on_Tab_on_Contact_Dashboard_Page(String tab) {
        waitFor(5);
        switch (tab) {
            case "Demographic Info":
                contactRecordDashboard.demographicInfoTab.click();
                waitFor(1);
                break;
            case "Active Contact":
                contactRecordDashboard.activeContactTab.click();
                waitFor(1);
                break;
            case "Case & Contact Details":
                waitFor(1);
                contactRecordDashboard.caseContactDetailsTab.click();
                waitFor(3);
                break;
            case "Task & Service Request":
                waitFor(3);
                contactRecordDashboard.taskServiceRequestTab.click();
                scrollDown();
                waitFor(5);
                scrollUpRobot();
                waitFor(2);
                break;
            case "Program & Benefit Info":
                contactRecordDashboard.programBenefitInfoTab.click();
                waitFor(1);
                break;
            case "History":
                waitFor(1);
                contactRecordDashboard.historyScreen.click();
                waitFor(3);
                break;
        }
        assertTrue(contactRecordDashboard.activeTab.getText().equalsIgnoreCase(tab));
    }

    @When("I Enter {string} as additional Comments")
    public void i_Enter_as_additional_Comments(String comments) {
        additionalCommentText.set(comments);
        contactRecord.additionalCommentsTextBox.click();
        clearAndFillText(contactRecord.additionalCommentsTextBox, comments);
    }

    @And("I click on save Reasons Options button")
    public void i_click_on_save_Reasons_Options_button() {
        waitFor(1);
        contactRecord.saveReasonButton.click();
    }

    @When("I verify values for Reason Action and Additional Comments are present")
    public void i_verify_values_for_Reason_Action_and_Additional_Comments_are_present() {
        waitFor(2);
        assertTrue(textIsPresent("Materials Request"));
        assertTrue(textIsPresent("Valid Additional Comment Text"));
    }

    @Then("I navigate to Task & Service details tab")
    public void i_navigate_to_task_tab() {
        waitForVisibility(contactRecordDashboard.taskServiceRequestTab, 10);
        contactRecordDashboard.taskServiceRequestTab.click();
        String expectedTaskURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/active-contact/contact/active/details/task/info";
        String actualTaskURL = Driver.getDriver().getCurrentUrl();
        assertEquals(actualTaskURL, expectedTaskURL);
        waitForPageToLoad(10);
    }

    @Then("I navigate to Program & Benefit Info details tab")
    public void i_navigate_to_program_tab() {
        waitForVisibility(contactRecordDashboard.programBenefitInfoTab, 10);
        contactRecordDashboard.programBenefitInfoTab.click();
        String expectedTaskURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/active-contact/contact/active/details/program";
        String actualTaskURL = Driver.getDriver().getCurrentUrl();
        assertEquals(actualTaskURL, expectedTaskURL);
        waitForPageToLoad(10);
    }

    @Then("I navigate to active contact tab on dashboard")
    public void i_navigate_to_active_contact_tab_on_dashboard() {
        waitForVisibility(contactRecordDashboard.activeContactTab, 10);
        contactRecordDashboard.activeContactTab.click();
        String expectedTaskURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/active-contact";
        String actualTaskURL = Driver.getDriver().getCurrentUrl();
        assertEquals(actualTaskURL, expectedTaskURL);
        waitForPageToLoad(10);
    }

    @Then("I navigate to case details tab on dashboard")
    public void i_navigate_to_case_details_tab_on_dashboard() {
        waitForVisibility(contactRecordDashboard.caseContactDetailsTab, 10);
        contactRecordDashboard.caseContactDetailsTab.click();
        contactRecordDashboard.contactHistory.isDisplayed();
        waitForPageToLoad(10);
    }

    @When("I navigate to all the tabs linked contact record")
    public void i_navigate_to_all_Tabs() {
        waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
        assertTrue(demographicContactInfo.demographicInfoTab.isDisplayed());
        demographicContactInfo.demographicInfoTab.click();
        assertTrue(contactRecord.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");

        waitForVisibility(contactRecord.caseContactDetailsTab, 10);
        assertTrue(contactRecord.caseContactDetailsTab.isDisplayed());
        contactRecord.caseContactDetailsTab.click();
        assertTrue(contactRecord.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");

        waitForVisibility(contactRecordDashboard.programBenefitInfoTab, 10);
        assertTrue(contactRecordDashboard.programBenefitInfoTab.isDisplayed());
        contactRecordDashboard.programBenefitInfoTab.click();
        assertTrue(contactRecord.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");

        waitForVisibility(contactRecordDashboard.taskServiceRequestTab, 10);
        assertTrue(contactRecordDashboard.taskServiceRequestTab.isDisplayed());
        contactRecordDashboard.taskServiceRequestTab.click();
        assertTrue(contactRecord.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");

    }


    @When("I see Secondary Header Global Navigation bar")
    public void i_see_Secondary_Header_Global_Navigation_bar() {
        assertTrue(contactRecord.secondaryGlobNav.isDisplayed(), "Secondary Header Global Navigation bar should be displayed");
    }


    @When("I navigate to Case Consumer search")
    public void i_navigate_to_Case_Consumer_search() {
        hover(dashboard.case_ConsumerSearchTab);
        waitFor(3);
        dashboard.case_ConsumerSearchTab.click();
        waitFor(2);
    }

    @When("I navigate to Contact Record search")
    public void i_navigate_to_Contact_Record_search() {
//        dashboard.contactRecordSearchTabIcon.click();
        waitFor(2);
        jsClick(dashboard.contactRecordSearchTab);//.click();
    }

    @When("I verify Active Contact phone Icon is not displayed")
    public void i_verify_Active_Contact_phone_Icon_is_not_displayed() {
        assertFalse(isElementDisplayed(dashboard.activeContactTab), "Active Contact phone Icon should not be displayed");
    }

    @When("I click on Secondary Header Global Navigation bar")
    public void i_click_on_Secondary_Header_Global_Navigation_bar() {
        contactRecord.secondaryGlobNav.click();
        hover(dashboard.createTask);

    }

    @When("I select a task to create")
    public void i_select_a_task_to_create() {
        hover(dashboard.createTask);
        waitFor(3);
        if (dashboard.taskTypes.size() > 1) {
            dashboard.taskTypes.get(radomNumber(dashboard.taskTypes.size() - 1)).click();
        } else {
            dashboard.taskTypes.get(0).click();
        }
    }

    @When("I populate some new data on Create Task Page")
    public void i_populate_some_new_data_on_Create_Task_Page() {
        waitFor(2);
        selectDropDown(dashboard.priorityDropDown, "1");
    }

    @Then("I see Warning Message pop-up")
    public void i_see_Warning_Message_pop_up() {
        waitForVisibility(contactHistory.warningMessageContinueButton, 5);
        contactHistory.warningMessageContinueButton.isDisplayed();
        contactHistory.warningMessageCancelButton.isDisplayed();
    }

    @When("I initiate a contact from Secondary Header Global Navigation")
    public void i_initiate_a_contact_from_Secondary_Header_Global_Navigation() {
        waitFor(3);
        contactRecord.initContact.click();

    }

    @Then("I see all the tasks that can be selected")
    public void i_see_all_the_tasks_that_can_be_selected() {
        hover(dashboard.createTask);
        waitFor(3);
        System.out.println(dashboard.taskTypes.size() + "  Tasks Types are available for this user");
    }

    @Then("I navigated to Create Task Page")
    public void i_navigated_to_Create_Task_Page() {
        assertTrue(dashboard.createTaskSign.isDisplayed(), "Did not navigate to Create Task Page");
    }

    @Then("I verify Active Contact Widget is visible on screen")
    public void i_verify_active_contact_widget_is_visible_on_screen() {
        assertTrue(contactRecord.activeContactWidzard.isDisplayed());
        waitFor(2);
    }

    @And("I have navigated to active contact page clicking left panel Call icon")
    public void i_have_navigated_to_active_contact_page_clicking_left_panel_call_icon() {
        waitFor(1);
        jsClick(dashboard.activeContactTab);
        //  click(dashBoard.activeContactTab);
        hover(contactRecord.contactReason);
        /*contactRecord.leftMenuCallIcon.click();
        waitFor(2);*/
    }

    @And("I have collected Case Consumer Name and ID from header")
    public void i_have_collected_case_or_consumer_name_and_id_from_header() {
        caseConsumerName.set(contactRecord.activeContactCaseConsumerNameHeaderNew.getText());
        caseConsumerId.set(contactRecord.activeContactCaseConsumerIdHeaderNew.getText());
        System.out.println(caseConsumerName.get());
        System.out.println(caseConsumerId.get());
    }

    @Then("I verify Active Contact Widget is not visible on screen")
    public void i_verify_active_contact_widget_is_not_visible_on_screen() {
        assertFalse(contactRecord.activeContactWidzardVisible());
        waitFor(2);
    }

    @When("I navigate to Case Consumer Search Page")
    public void i_navigate_to_case_consumer_search_page() {
        waitFor(2);
        hover(dashboard.case_ConsumerSearchIcon);
        // jsClick(dashboard.case_ConsumerSearchTab);
        waitFor(3);
        jsClick(dashboard.case_ConsumerSearchTab);
        hover(dashboard.btnMenuList);
    }

    @When("I navigate to Contact Record Search Page")
    public void i_navigate_to_contact_record_search_page() {
        waitFor(2);
        hover(dashboard.case_ConsumerSearchTab);
        // jsClick(dashboard.contactRecordSearchTab);
        click(dashboard.contactRecordSearchTab);
       /* contactRecord.contactRecordInsilderBar.click();
        waitFor(1);*/
        waitFor(1);
        hover(contactRecord.contactLN);
    }

    @Then("I verify Active Contact Widget Start Time is visible on screen")
    public void i_verify_active_contact_widget_start_time_is_visible_on_screen() {
        assertTrue(contactRecord.activeContactWidzardStartTime.isDisplayed());
        waitFor(2);
        String start_time = contactRecord.activeContactWidzardStartTime.getText().replace("schedule", "").replace("\n", "").replace("\r", "");
        System.out.println("Start Time = " + start_time);
        String result = "";
        Pattern pattern = Pattern.compile("(1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm])");
        Matcher matcher = pattern.matcher(start_time);
        while (matcher.find()) {
            result += start_time.substring(matcher.start(), matcher.end());
        }
        System.out.println(result);
        assertFalse(StringUtils.isEmpty(result));
    }

    @Then("I verify Active Contact Widget Timer is visible on screen")
    public void i_verify_active_contact_widget_timer_is_visible_on_screen() {
        assertTrue(contactRecord.activeContactWidzardTimer.isDisplayed());
        waitFor(2);
        String timer = contactRecord.activeContactWidzardTimer.getText().replace("av_timer", "").replace("\n", "").replace("\r", "");
        System.out.println("Timer = " + timer);
        String timer_result = "";
        Pattern pattern = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]:[0-5][0-9]");
        Matcher matcher = pattern.matcher(timer);
        while (matcher.find()) {
            timer_result += timer.substring(matcher.start(), matcher.end());
        }
        System.out.println(timer_result);
        assertFalse(StringUtils.isEmpty(timer_result));
    }

    @Then("I verify Active Contact Widget Case Consumer name is visible on screen")
    public void i_verify_active_contact_widget_case_or_consumer_name_is_visible_on_screen() {
        assertTrue(contactRecord.activeContactWidzardCaseConsumerName.isDisplayed());
        String consumer_name = contactRecord.activeContactWidzardCaseConsumerName.getText();
        System.out.println("caseConsumerName == " + consumer_name);
        // System.out.println(caseConsumerName + " == " + consumer_name);
        assertFalse(caseConsumerName.get() == consumer_name);
        waitFor(2);
    }

    @And("I verify Active Contact Widget Case Consumer id is visible on screen")
    public void i_verify_active_contact_widget_case_or_consumer_id_is_visible_on_screen() {
        assertTrue(contactRecord.activeContactWidzardCaseConsumerId.isDisplayed());
        String consumer_id = contactRecord.activeContactWidzardCaseConsumerId.getText();
        System.out.println("caseConsumerId == " + consumer_id);
        // System.out.println(caseConsumerId + " == " + consumer_id);
        assertFalse(caseConsumerId.get() == consumer_id);
        waitFor(2);
    }

    @And("I can minimize Active Contact Widget")
    public void i_can_minimize_active_contact_widget() {
        contactRecord.activeContactWidzardMinimize.click();
        waitFor(2);
    }

    @Then("I verify {string} is displayed")
    public void verifyActiveTab(String tab) {
        waitFor(1);
        assertTrue(contactRecordDashboard.activeTab.getText().equalsIgnoreCase(tab));
    }

    @When("I save first contactId with name {string}")
    public void I_save_first_contactId_with_name(String name) {
        waitForVisibility(contactRecord.firstContactId, 2);
        String contactId = contactRecord.firstContactId.getText();
        Api_Body api_req = new EventBuilder()
                .body("{\"contactId\": \"" + contactId + "\"}")
                .name(name)
                .build();
        stg.get().addProduced(api_req);
        System.out.println("contactId is " + contactId);
    }

    @When("I click {string} survey")
    public void i_select_survey(String surveyName) {
        WebElement element = surveyName.equalsIgnoreCase("CAHMI") ? crmProgramAndBenefitInfoPage.cahmiSurveyButton : crmProgramAndBenefitInfoPage.hraSurveyButton;
        click(element);
    }

    @When("I click on new survey button")
    public void click_on_new_survey_button() {
        click(crmProgramAndBenefitInfoPage.newSurveyButton);
    }

    @When("I verify new survey button is disabled")
    public void verify_new_survey_button_is_disabled() {
        sa.get().assertThat(crmProgramAndBenefitInfoPage.newSurveyButton.getAttribute("disabled")).as("survey button is not disabled").hasToString("true");
    }

    @Then("I verify {string} survey label")
    public void i_verify_survey_label(String surveyName) {
        WebElement surveyWebElement = getDynamicWebElement("//h5[text()='surveyName' and text()=' SURVEY']", "surveyName", surveyName);
        sa.get().assertThat(isElementDisplayed(surveyWebElement)).as(surveyName + "is not displayed on the PROGRAM & BENEFIT INFO").isTrue();
    }

    @Then("I verify no records available on the survey slider")
    public void i_verify_no_survey_available_on_survey_slider() {
        sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.noRecordsAvailableTxt)).as("no records available message is not displayed").isTrue();
    }

    @When("I select eligible consumer on the survey slider")
    public void select_consumer_to_start_survey() {
        sa.get().assertThat(crmProgramAndBenefitInfoPage.listConsumers.get(0).getAttribute("value")).as("Select All value is not in dropdown").hasToString("Select All");
        crmProgramAndBenefitInfoPage.listConsumers.stream().skip(1).findFirst().ifPresent(WebElement::click);
    }

    @Then("I verify only {string} eligible consumers presented on the drop down list")
    public void verify_only_eligible_consumers(String surveyName) {
        List<String> eligibleConsumers = crmProgramAndBenefitInfoPage.listConsumers.stream().skip(1).map(x -> x.getAttribute("value")).collect(Collectors.toList());
        switch (surveyName) {
            case "CAHMI":
                sa.get().assertThat(eligibleConsumers).containsExactlyInAnyOrderElementsOf(eligibleConsumersForCahmiSurvey.get()).hasSize(eligibleConsumersForCahmiSurvey.get().size()).doesNotHaveDuplicates();
                break;
            case "HRA":
                sa.get().assertThat(eligibleConsumers).containsExactlyInAnyOrderElementsOf(eligibleConsumersForHraSurvey.get()).hasSize(eligibleConsumersForHraSurvey.get().size()).doesNotHaveDuplicates();
                break;
        }
    }

    @Then("I identify eligible consumers for {string} survey and store in the list")
    public void store_eligible_consumers(String surveyName) {
        waitFor(5);
        switch (surveyName) {
            case "CAHMI":
                synchronized (eligibleConsumersForCahmiSurvey){
                    eligibleConsumersForCahmiSurvey.set(crmProgramAndBenefitInfoPage.eligibleConsumers.stream().map(WebElement::getText).collect(Collectors.toList()));
                }
                break;
            case "HRA":
                synchronized (eligibleConsumersForHraSurvey){
                    eligibleConsumersForHraSurvey.set(crmProgramAndBenefitInfoPage.hraEligibleConsumers.stream().map(WebElement::getText).collect(Collectors.toList()));
                }
                break;
        }
    }

    @When("I click on start survey")
    public void click_on_start_survey_button() {
        click(crmProgramAndBenefitInfoPage.startSurveyButton);
    }

    @And("I click on close survey")
    public void click_on_close_survey_button() {
        click(crmProgramAndBenefitInfoPage.closeSurveyButton);
    }

    @When("Verify following questions must be captured as part of the survey for CAHMI")
    public void verifyFollowingQuestionsMustbecapturedaspartofthesurveyforCAHMI(List<String> survey) {
        for (String question : survey) {
            if (question.equalsIgnoreCase("Does this child have any health problems or medical treatments your health plan should know about?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(1).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.qusOneYes)).as("please describe any health problems or medical treatments. not displayed").isTrue();

            } else if (question.equalsIgnoreCase("Is this child pregnant?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(4).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.doctorName)).as("Doctor's Name Field not displayed").isTrue();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.deliveryDate)).as("Delivery Date not displayed").isTrue();

            } else if (question.equalsIgnoreCase("Does your child currently need or use medicine prescribed by a doctor (other than vitamins)?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(7).click();
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.medication)).as("Medication Field not displayed").isTrue();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Does your child need or use these medications because of a condition that has lasted, or is expected last, for at least one year?"))).as("Question Three Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.medicationStr)).as("Medication Option not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Does your child need or use medical care, mental health, or educational services than is usual for most children of the same age?")) {
                crmProgramAndBenefitInfoPage.nextButton.click();
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(1).click();
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Is this because of any medical, behavioral, or other health condition?"))).as("Question Four Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Has this condition lasted, or is it expected to last, for at least one year?"))).as("Question Four Field not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Is your child limited or prevented in any way (his/her) ability to do things most children the same age can do?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(10).click();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Is this because of any medical, behavioral, or other health condition?"))).as("Question Five Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Has this condition lasted, or is it expected to last, for at least one year?"))).as("Question Five Field not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Does your child need or get special therapy, such as physical, occupational, or speech therapy?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(19).click();
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Is this because of any medical, behavioral, or other health condition?"))).as("Question Six Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Has this condition lasted, or is it expected to last, for at least one year?"))).as("Question Six Field not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Does your child have any kind of emotional, developmental, or behavioral problem?")) {
                crmProgramAndBenefitInfoPage.nextButton.click();
                waitFor(2);
                scrollUpUsingActions(2);
                waitFor(3);
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(1).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Does your child need or get treatment or counselling for this problem?"))).as("Question Seven Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.findSurveyQuestion("Has the problem lasted, or is it expected to last, for at least one year?"))).as("Question Seven Field not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Does your child have any special medical procedures that have already been scheduled? Examples include chemotherapy, surgery, allergy shots, or other therapy of any kind?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(10).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.mediProce)).as("Question Eight Field not displayed").isTrue();
            } else {
                sa.get().fail("No Survey Question Found: " + question);
            }
        }
    }

    @When("Verify following questions must be captured as part of the survey for HRA")
    public void verifyFollowingQuestionsMustbecapturedaspartofthesurveyforHRA(List<String> survey) {
        for (String question : survey) {
            if (question.equalsIgnoreCase("Do you or a family member have any doctors appointments in the next month")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(1).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.doctorName)).as("Doctor's Name Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.appointmentDate)).as("Appointment Date not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Do you or a family member take any medicines that have been prescribed by a doctor?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(4).click();
                waitFor(1);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.medication)).as("Medicine Name Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.dateMedicineRunsOut)).as("Medicine RunOut Date not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Do you or a family member get home-based care?")) {
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(7).click();
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.tellUsAboutTheCare)).as("Tell Us About The Care Field not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Are you or a family member pregnant?")) {
                crmProgramAndBenefitInfoPage.nextButton.click();
                crmProgramAndBenefitInfoPage.qusOneCahmi.get(1).click();
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.doctorNameHRA)).as("Doctor Name Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.dateBabyIsOut)).as("Date Baby Is Out not displayed").isTrue();
            } else if (question.equalsIgnoreCase("When was the last time you and your family members saw a doctor?")) {
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.doctorNameHRA)).as("Doctor Name Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.appointmentDate)).as("Appointment Date not displayed").isTrue();
            } else if (question.equalsIgnoreCase("When was the last time you and your family members saw a dentist?")) {
                waitFor(2);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.dentistName)).as("Dentist Name Field not displayed").isTrue();
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.appointmentDate)).as("Appointment Date not displayed").isTrue();
            } else if (question.equalsIgnoreCase("Tell us about any health problems or treatment plans that you or your family member’s have?")) {
                crmProgramAndBenefitInfoPage.nextButton.click();
                waitFor(2);
                scrollUpUsingActions(2);
                waitFor(3);
                sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.healthProblemOrTreatmentPlan)).as("The Health Problem Or treatment Plan not displayed").isTrue();
            } else {
                sa.get().fail("No Survey Question Found: " + question);
            }
        }
    }

    @When("I will provide answers with following questions for CAHMI survey")
    public void i_provide_answers_for_followed_questions_for_cahmi(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "Does this child have any health problems or medical treatments your health plan should know about?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"1");
                    break;
                case "Is this child pregnant?":
                    navigateToPageAndSelectAnswer(1, data.get(key),"1");
                    break;
                case "Does your child currently need or use medicine prescribed by a doctor (other than vitamins)?":
                    navigateToPageAndSelectAnswer(2, data.get(key),"1");
                    break;
                case "Does your child need or use medical care, mental health, or educational services than is usual for most children of the same age?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"2");
                    break;
                case "Is your child limited or prevented in any way (his/her) ability to do things most children the same age can do?":
                    navigateToPageAndSelectAnswer(1, data.get(key),"2");
                    break;
                case "Does your child need or get special therapy, such as physical, occupational, or speech therapy?":
                    navigateToPageAndSelectAnswer(2, data.get(key),"2");
                    break;
                case "Does your child have any kind of emotional, developmental, or behavioral problem?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"3");
                    break;
                case "Does your child have any special medical procedures that have already been scheduled? Examples include chemotherapy, surgery, allergy shots, or other therapy of any kind?":
                    navigateToPageAndSelectAnswer(1, data.get(key),"3");
                    break;
                default:
                    fail("Incorrect switch argument");
            }
        }
    }

    @When("I will provide answers with following questions for HRA survey")
    public void i_provide_answers_for_followed_questions_for_hra(Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "Do you or a family member have any doctors appointments in the next month?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"1");
                    break;
                case "Do you or a family member take any medicines that have been prescribed by a doctor?":
                    navigateToPageAndSelectAnswer(1, data.get(key),"1");
                    break;
                case "Do you or a family member get home-based care?":
                    navigateToPageAndSelectAnswer(2, data.get(key),"1");
                    break;
                case "Are you or a family member pregnant?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"2");
                    break;
                case "When was the last time you or your family members saw a doctor?":
                    navigateToPageAndSelectAnswer(1, data.get(key),"2");
                    break;
                case "When was the last time you or your family members saw a dentist?":
                    navigateToPageAndSelectAnswer(2, data.get(key),"2");
                    break;
                case "Tell us about any health problems or treatment plans that you or your family member’s have?":
                    navigateToPageAndSelectAnswer(0, data.get(key),"3");
                    break;
                default:
                    fail("Incorrect switch argument");
            }
        }
    }


    private void navigateToPageAndSelectAnswer(int index, String value, String pageNumber) {
        waitFor(3);
        while (!crmProgramAndBenefitInfoPage.backButton.getAttribute("class").contains("disabled")) {
            crmProgramAndBenefitInfoPage.backButton.click();
        }
        if (pageNumber.equalsIgnoreCase("2")) {
            crmProgramAndBenefitInfoPage.nextButton.click();
        } else if (pageNumber.equalsIgnoreCase("3")){
            crmProgramAndBenefitInfoPage.nextButton.click();
            crmProgramAndBenefitInfoPage.nextButton.click();
        }
        waitFor(3);
        Driver.getDriver().findElements(By.xpath("//input[@value='" + value + "']")).get(index).click();
    }

    @When("I click on save button on survey response")
    public void click_on_save_survey_button() {
        click(crmProgramAndBenefitInfoPage.saveButton);
    }

    @Then("I verify survey slider is collapsed")
    public void i_verify_survey_slider_is_collapsed() {
        waitFor(5);
        sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.startSurveyButton)).as("survey slider is still displayed").isFalse();
    }

    @Then("I verify icon is displayed for {string} survey")
    public void i_verify_icon_displayed_for_survey(String surveyName) {
        WebElement surveyWebElement = getDynamicWebElement("//*[text()='surveyName']//following-sibling::i[text()='check_circle']", "surveyName", surveyName);
        sa.get().assertThat(isElementDisplayed(surveyWebElement)).as("Icons is not displayed for " + surveyName).isTrue();
    }

    @Then("I verify survey response is presented on the survey slider")
    public void i_verify_survey_response_is_displayed() {
        waitFor(5);
        sa.get().assertThat(isElementDisplayed(crmProgramAndBenefitInfoPage.viewResponse)).as("survey response is not displayed").isTrue();
    }
}
