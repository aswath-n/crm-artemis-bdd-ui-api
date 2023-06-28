package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.applicationCodeAPI;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationMemberStepDef.programList;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef.applicationCode;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef.programListPI;
import static org.testng.Assert.*;

public class CRMApplicationTrackingStepDef extends BrowserUtils {
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    CRMApplicationSearchPage applicationSearchPage = new CRMApplicationSearchPage();
    CRMCreateApplicationPage createApplicationPage = new CRMCreateApplicationPage();
    CRMCreateApplicationMemberPage createApplicationMemberPage = new CRMCreateApplicationMemberPage();
    ATSAddAuthorizedRepresentativePage addAuthorizedRepresentativePage = new ATSAddAuthorizedRepresentativePage();
    CRM_CreateApplicationStepDef appStep = new CRM_CreateApplicationStepDef();
    CRM_ApplicationTrackingAuthorizedRepStepDef authRep = new CRM_ApplicationTrackingAuthorizedRepStepDef();
    Actions action = new Actions(Driver.getDriver());
    APIATSApplicationController applicationController=new APIATSApplicationController();
    CRMMemberMatchingPage memberMatchingPage=new CRMMemberMatchingPage();

    private String piFirstName = appStep.piFirstName;
    private String piLastName = appStep.piLastName;
    private String piMi = appStep.piMiddleInitial;
    private String applicationId = appStep.applicationId;
    private String signatureDate = appStep.signatureDate;
    private String cycle = appStep.cycle;
    private String receivedDate = appStep.receivedDate;
    private String channel = appStep.channel;
    private String createDate;
    private String lastUpdateDate;
    private String priority;
    private static String deadlineDate;
    private static String deadlineDateFirstValue;
    private String doubleDash = "-- --";
    private String fullResidentAddress;
    private String primaryPhoneNum = appStep.cellPhone;
    private String residenceAddressLineOne = appStep.residenceAddressLineOne;
    private String residenceAddressLinetwo = appStep.residenceAddressLineTwo;
    private String residenceAddressCity = appStep.residenceCity;
    private String residenceAddressState = appStep.residenceState;
    private String residenceAddressZip = appStep.residenceZipcode;
    private String residenceAddressCounty = appStep.residenceCounty;
    private String spokenLanguage = appStep.piSpokenLanguage;
    private String writtenLanguage = appStep.piWrittenLanguage;
    private static String eligibilityStartDate;
    private static String eligibilityEndDate;
    List<String> strOptions = new ArrayList<>();
    public static String applicationCodeFromUI;


    @Then("I see Application Tracking page header includes Primary Individual Name as {string} & Application ID as {string} and icon")
    public void i_see_Application_Tracking_page_header_includes_Primary_Individual_Name_as_Application_ID_as_and_icon(String primaryIndividualName, String applicationID) {
        Assert.assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Icon not displayed");
        assertEquals(primaryIndividualName, applicationTrackingPage.primaryIndividualName.getText(), "primary individual name mismatch");
        assertEquals(applicationID, applicationTrackingPage.applicationId.getText(), "application id mismatch");
    }

    @Then("I see Application Tracking is selected by default")
    public void i_see_Application_Tracking_is_selected_by_default() {
        String elementClass = applicationTrackingPage.applicationTrackingTab.getAttribute("class");
        Assert.assertTrue(elementClass.contains("active"));
    }

    @Then("I see application Status as {string} in the application information")
    public void i_see_application_Status_as_in_the_application_information(String expectedStatus) {
        waitForPageToLoad(3);
        waitFor(5);
        String actualStatus = applicationTrackingPage.applicationStatus.getText();
        assertEquals(actualStatus.toLowerCase(),expectedStatus.toLowerCase(), "Expected Application status: " + expectedStatus + " But found: " + actualStatus);
    }

    @Then("I see Withdraw link in the the application information")
    public void i_see_Withdraw_link_in_the_the_application_information() {
        Assert.assertTrue(applicationTrackingPage.withdrawLink.isDisplayed());
    }

    @Then("I see columns displayed in this order in the Application Information panel")
    public void i_see_values_displayed_in_this_order_in_the_Application_Information_panel(List<List<String>> data) {
        List<String> expectedColumns = data.get(0);
        List<WebElement> actualColumns = applicationTrackingPage.appInfoColumns;

        assertEquals(expectedColumns.size(), actualColumns.size());

        for (int i = 0; i < expectedColumns.size(); i++) {
            assertEquals(expectedColumns.get(i).toLowerCase(), actualColumns.get(i).getText().toLowerCase(), "Expected Application " + expectedColumns.get(i) + " Tracking Tab Info not found");
        }

    }

    @Then("I click application id under Application Tracking tab in the Application Information panel")
    public void i_click_application_id_under_Application_Tracking_tab_in_the_Application_Information_panel() {
        jsClick(applicationTrackingPage.applicationIdUnderApplicationTrackingTab);
    }

    @Then("I see Application tab is selected")
    public void i_see_Application_tab_is_selected() {
        waitForVisibility(applicationTrackingPage.applicationTab,10);
        String applicationElementClass = applicationTrackingPage.applicationTab.getAttribute("class");
        Assert.assertTrue(applicationElementClass.contains("active"));
    }

    @Then("I see Primary Individual details displayed in this order in the Application tab")
    public void i_see_Primary_Individual_details_displayed_in_this_order_in_the_Application_tab(List<List<String>> data) {
        BrowserUtils.waitFor(2);
        List<String> expectedDetails = data.get(1);
        List<String> actualDetails = new ArrayList<>();

        // remove first element is expander and remove last element because its empty col
        for (int i = 1; i < applicationTrackingPage.primaryIndividualDetails.size() - 1; i++) {
            actualDetails.add(applicationTrackingPage.primaryIndividualDetails.get(i).getText());
        }

        System.out.println("Expected: " + expectedDetails.size());
        System.out.println("Actual: " + actualDetails.size());

        System.out.println("Expected: " + expectedDetails);
        System.out.println("Actual: " + actualDetails);

        assertEquals(expectedDetails.size(), actualDetails.size());

        for (int i = 0; i < expectedDetails.size(); i++) {
            assertEquals(expectedDetails.get(i).toLowerCase(), actualDetails.get(i).toLowerCase());
        }
    }

    @Then("I see APPLICATION INFORMATION panel values in the Application Information Panel under Application Tracking Tab")
    public void i_see_APPLICATION_INFORMATION_panel_values_in_the_Application_Information_Panel_under_Application_Tracking_Tab(List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);
        List<String> actualData = applicationTrackingPage.applicationInformationPanelValues.stream().map(el -> el.getText()).collect(Collectors.toList());

        for (String key : expectedData.keySet()) {
            Assert.assertTrue(actualData.contains(expectedData.get(key)), expectedData.get(key) + " : does not match or exist");
        }
    }

    @Then("I see Member\\(s) INFO panel values in the MEMBER\\(S) INFO Panel under Application Tracking Tab")
    public void i_see_Member_s_INFO_panel_values_in_the_MEMBER_S_INFO_Panel_under_Application_Tracking_Tab(List<Map<String, String>> data) {
        for (int i = 0; i < data.size(); i++) {
            String xpath = "//div[@class='table-responsive mb-4']//tbody//tr[" + (i + 1) + "]//td[not(contains(@class,'mx-table-icon'))]";
            List<WebElement> actualRow = Driver.getDriver().findElements(By.xpath(xpath));
            Map<String, String> expectedRow = data.get(i);

            assertEquals(actualRow.size(), expectedRow.size());

            int j = 0;
            for (String key : expectedRow.keySet()) {
                assertEquals(actualRow.get(j).getText().toLowerCase().trim(), expectedRow.get(key).toLowerCase().trim(), key + " is not matching");
                j++;
            }
        }
    }

    @Then("I see AUTHORIZED REPRESENTATIVE\\(S) panel values in the AUTHORIZED REPRESENTATIVE\\(S) Panel under Application Tracking Tab")
    public void i_see_AUTHORIZED_REPRESENTATIVE_S_panel_values_in_the_AUTHORIZED_REPRESENTATIVE_S_Panel_under_Application_Tracking_Tab(List<Map<String, String>> data) {
        for (int i = 0; i < data.size(); i++) {
            String xpath = "//div[@class='table-responsive']//tbody//tr[" + (i + 1) + "]//td[@class!='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-table-icon']";
            List<WebElement> actualRow = Driver.getDriver().findElements(By.xpath(xpath));
            Map<String, String> expectedRow = data.get(i);

            int j = 0;
            for (String key : expectedRow.keySet()) {
                assertEquals(actualRow.get(j).getText().toLowerCase().trim(), expectedRow.get(key).toLowerCase().trim());
                j++;
            }
        }
    }

    @Then("I see CONTACT INFO panel values in the CONTACT INFO Panel under Application Tracking Tab")
    public void i_see_CONTACT_INFO_panel_values_in_the_CONTACT_INFO_Panel_under_Application_Tracking_Tab(List<Map<String, String>> data) {
        Map<String, String> expectedData = data.get(0);
        List<WebElement> actualData = applicationTrackingPage.contactInfoValues;
        int i = 0;
        for (String key : expectedData.keySet()) {
            assertEquals(actualData.get(i).getText(), expectedData.get(key), "Expected Contact Info details NOT displayed");
            i++;
        }
    }

    @Then("I click on the back arrow in header row next to the icon Primary Individual name and application id")
    public void i_click_on_the_back_arrow_in_header_row_next_to_the_icon_Primary_Individual_name_and_application_id() {
        scrollToTop();
        Assert.assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Application Icon is not displayed in the header on Application Traking Tab");
        assertEquals(applicationTrackingPage.primaryIndividualName.getText(), piFirstName + " " + piLastName, "Expected full name: " + piFirstName + " " + piLastName + " But found " + applicationTrackingPage.primaryIndividualName.getText());
        assertEquals(applicationTrackingPage.applicationId.getText(), applicationId, "Expected App ID " + applicationId + " But found: " + applicationTrackingPage.applicationId.getText() + " in the tracking tab header");
        applicationTrackingPage.backArrow.click();
    }

    @Then("I click on the back arrow in header row on Application Tracking page")
    public void i_click_on_the_back_arrow_in_header_row() {
        Assert.assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Application Icon is not displayed in the header on Application Traking Tab");
        applicationTrackingPage.backArrow.click();
    }

    @Then("I navigate back to Search Application page without getting a warning message")
    public void i_navigate_back_to_Search_Application_page_without_getting_a_warning_message() {
        Assert.assertTrue(applicationSearchPage.searchTitle.isDisplayed());
    }

    @Then("I see I see columns displayed in this order in the  Member\\(s) INFO panel")
    public void i_see_I_see_columns_displayed_in_this_order_in_the_Member_s_INFO_panel(List<List<String>> data) {
        List<String> expectedColumns = data.get(0);
        List<WebElement> actualColumns = applicationTrackingPage.memberInfoColumns;

        assertEquals(expectedColumns.size(), actualColumns.size());

        for (int i = 0; i < expectedColumns.size(); i++) {
            assertEquals(expectedColumns.get(i).toLowerCase(), actualColumns.get(i).getText().toLowerCase());
        }

    }

    @Then("I see primary individual icon in the  Member\\(s) INFO panel")
    public void i_see_primary_individual_icon_in_the_Member_s_INFO_panel() {
        Assert.assertTrue(applicationTrackingPage.primaryIndividualIcon.isDisplayed(), "primary individual icon is not displayed");
    }

    @Then("I navigate to Application Tracking")
    public void i_navigate_to_Application_Tracking() {
        applicationTrackingPage.applicationTrackingTab.click();
    }

    @Then("I verify AUTHORIZED REPRESENTATIVE\\(S) panel is not displayed and application does not have AUTHORIZED REPRESENTATIVE\\(S)")
    public void i_verify_AUTHORIZED_REPRESENTATIVE_S_panel_is_not_displayed_and_application_does_not_have_AUTHORIZED_REPRESENTATIVE_S() {
        List<WebElement> webElementList = Driver.getDriver().findElements(By.xpath("//h3[.='AUTHORIZED REPRESENTATIVE(S)']"));
        assertEquals(webElementList.size(), 0, "Unexpected! Authorized Representative is displayed");
    }

    @Then("I verify Edit button is displayed and disabled in create application page in Application tab")
    public void i_verify_Edit_button_is_displayed_and_disabled_in_create_application_page_in_Application_tab() {
        Assert.assertTrue(createApplicationPage.primaryIndividualEditButton.isDisplayed(), "EDIT button is NOT displayed in Create Application page in Application tab");
        Assert.assertFalse(createApplicationPage.primaryIndividualEditButton.isEnabled(), "Edit button is clickable");

    }

    @Then("I verify Add Application member button is displayed  and disabled in create application page in Application tab")
    public void i_verify_Add_Application_member_button_is_displayed_and_disabled_in_create_application_page_in_Application_tab() {
        waitFor(3);
        scrollDown();
        Assert.assertFalse(createApplicationPage.addApplicationMemberButton.isEnabled(), "Add Application Member button is clickable");

    }

    @Then("I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab")
    public void i_verify_Add_Authorized_Representative_button_is_displayed_and_disabled_in_create_application_page_in_Application_tab() {
        waitForVisibility(createApplicationPage.addAuthorizedRepBtn, 5);
        Assert.assertFalse(createApplicationPage.addAuthorizedRepBtn.isEnabled(), "Add Authorized Representative button is clickable");

    }

    @Then("I verify I see Withdraw button displayed and disabled in Application Information panel")
    public void i_verify_I_see_Withdraw_button_displayed_and_disabled_in_Application_Information_panel() {
        waitFor(3);
        Assert.assertTrue(applicationTrackingPage.withdrawLink.isDisplayed(), "Withdraw link is not displayed");
        Assert.assertFalse((applicationTrackingPage.withdrawLink.isEnabled()), "Withdraw button is clickable");

    }

    @Then("I verify Edit button and Remove button for Application Member page are displayed and disabled")
    public void i_verify_Edit_button_and_Remove_button_for_Application_Member_page_are_displayed_and_disabled() {
        BrowserUtils.waitFor(3);
        Assert.assertTrue(createApplicationMemberPage.appMemEditButton.isDisplayed(), "App Member EDIT button is not displayed");
        Assert.assertFalse((createApplicationMemberPage.appMemEditButton.isEnabled()), "App Member EDIT button is clickable");

    }

    @Then("I verify Edit button for Authorized Representative page is displayed and disabled")
    public void i_verify_Edit_button_for_Authorized_Representative_page_is_displayed_and_disabled() {
        BrowserUtils.waitFor(3);
        Assert.assertTrue(addAuthorizedRepresentativePage.arEditBtn.isDisplayed(), "Auth Rep Edit button is NOT displayed");
        Assert.assertFalse((addAuthorizedRepresentativePage.arEditBtn.isEnabled()), "Auth Rep Edit button is clickable");
    }


    @Then("I verify set as Primary Individual button is displayed and disabled in application member panel create application page in Application tab")
    public void i_verify_set_as_Primary_Individual_button_is_displayed_and_disabled_in_application_member_panel_create_application_page_in_Application_tab() {
        waitForVisibility(createApplicationMemberPage.setAsPrimaryIndividualBtnInAppMemberPanel, 5);
        Assert.assertTrue(createApplicationMemberPage.setAsPrimaryIndividualBtnInAppMemberPanel.isDisplayed(), "Application member panel SET AS PI Button is not displayed");

    }

    @Then("I verify application Member Remove Primary Individual button is displayed and disabled in application member panel create application page in Application tab")
    public void i_verify_application_Member_Remove_Primary_Individual_button_is_displayed_and_disabled_in_application_member_panel_create_application_page_in_Application_tab() {
        waitForVisibility(createApplicationMemberPage.removePrimaryIndividualButtonInAppMemberPanel, 5);
        Assert.assertTrue(createApplicationMemberPage.removePrimaryIndividualButtonInAppMemberPanel.isDisplayed(), "Application member panel DELETE Button is not displayed");

    }

    @Then("I see Submit button is displayed and disabled in create application page in Application tab")
    public void i_see_Submit_button_is_displayed_and_disabled_in_create_application_page_in_Application_tab() {
        BrowserUtils.waitFor(3);
        scrollToElement(createApplicationPage.submitBtn);
        waitFor(3);
        Assert.assertTrue(createApplicationPage.submitBtn.isDisplayed(), "Submit button is not displayed");
        Assert.assertTrue(createApplicationPage.submitbuttonValue.getAttribute("class").contains("disabled"), "Submit button is clickable");
    }

    @Then("I verify application icon with Primary Individual name and App Id is displayed in the Header")
    public void iVerifyApplicationIconWithPrimaryIndividualNameAndAppIdIsDisplayedInTheHeader() {
        Assert.assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Application Icon is not displayed in the header on Application Traking Tab");
        assertEquals(applicationTrackingPage.primaryIndividualName.getText(), piFirstName + " " + piLastName, "Expected full name: " + piFirstName + " " + piLastName + " But found " + applicationTrackingPage.primaryIndividualName.getText());
        assertEquals(applicationTrackingPage.applicationId.getText(), applicationId, "Expected App ID " + applicationId + " But found: " + applicationTrackingPage.applicationId.getText() + " in the tracking tab header");
    }

    @Then("I verify application header for {string} initiated applications")
    public void iVerifyApplicationHeaderForCreatedApplications(String appType) {
        if (appType.equalsIgnoreCase("Review Incomplete Application")) {
            assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Application Icon is not displayed in the header on Application Traking Tab");
            assertEquals(applicationTrackingPage.primaryIndividualName.getText(), doubleDash, "Expected " + doubleDash + " But found " + applicationTrackingPage.primaryIndividualName.getText());
            assertEquals(applicationTrackingPage.applicationId.getText(), applicationIdAPI.get(), "Expected App ID " + applicationIdAPI.get() + " But found: " + applicationTrackingPage.applicationId.getText() + " in the tracking tab header");
        }
        if (appType.equalsIgnoreCase("Member Matching")) {
            assertTrue(applicationTrackingPage.applicationIcon.isDisplayed(), "Application Icon is not displayed in the header on Application Traking Tab");
            assertEquals(applicationTrackingPage.primaryIndividualName.getText(), duplicateFirstname + " " + duplicateLastName, "Expected full name: " + duplicateFirstname + " " + duplicateLastName + " But found " + applicationTrackingPage.primaryIndividualName.getText());
            assertEquals(applicationTrackingPage.applicationId.getText(), applicationIdAPI.get(), "Expected App ID " + applicationIdAPI.get() + " But found: " + applicationTrackingPage.applicationId.getText() + " in the tracking tab header");
            assertTrue(memberMatchingPage.memberMatchingPageHeader.getText().contains("-"));
            assertTrue(memberMatchingPage.memberMatchingPageHeader.getText().contains("MEMBER MATCHING"));
        }
    }

    @And("I verify I am on Primary Individual details page")
    public void iVerifyIAmOnPrimaryIndividualDetailsPage() {
        waitFor(2);
        Assert.assertTrue(createApplicationPage.piDetailsHeader.getText().contains("PRIMARY INDIVIDUAL DETAILS"), "Expected to be on Application tab by checking PRIMARY INDIVIDUAL DETAILS header but not found");
    }

    @Then("I verify the Application panel values for {string} in the Application tracking tab")
    public void iVerifyTheApplicationPanelValuesForInTheApplicationTrackingTab(String appType) {
        assertEquals(applicationTrackingPage.appPannelIdValue.getText(), applicationId, "Created app Id does not match the app Id value shown in APP INFO panel: ");
        assertEquals(applicationTrackingPage.signatureDateValue.getText(), signatureDate, "Incorrect Signature date found in App Info Panel inside Application Tracking Tab");
        assertEquals(applicationTrackingPage.receivedDateValue.getText(), receivedDate, "Incorrect Received date found in App Info Panel inside Application Tracking Tab");
        assertEquals(applicationTrackingPage.channelValue.getText(), channel, "Incorrect Channel found in App Info Panel inside Application Tracking Tab");
        assertEquals(applicationTrackingPage.createDateValue.getText(), getCurrentDate(), "Incorrect Create date found in App Info Panel inside Application Tracking Tab");
        assertEquals(applicationTrackingPage.lastUpdatedDateValue.getText(), getCurrentDate(), "Incorrect Last Updated date found in App Info Panel inside Application Tracking Tab");
        if ("MEDICAL ASSISTANCE".equals(appType)) {
            assertEquals(applicationTrackingPage.appTypeValue.getText(), "Medical Assistance", "Expected Medical Assistance for Application Type in App Info Panel inside Application Tracking Tab");
            assertEquals(applicationTrackingPage.cycleValue.getText(), cycle, "Incorrect Application Cycle found in App Info Panel inside Application Tracking Tab");
        } else if ("LONG TERM CARE".equals(appType)) {
            assertEquals(applicationTrackingPage.appTypeValue.getText(), "Long Term Care", "Expected Long Term Care for Application Type in App Info Panel inside Application Tracking Tab");
            assertEquals(applicationTrackingPage.cycleValue.getText(), cycle, "Incorrect Application Cycle found in App Info Panel inside Application Tracking Tab");
        } else {
            Assert.fail("Provided incorrect Application type");
        }
    }

    @Then("I verify double dash is present in signature date value on Application info on Application Tracking tab")
    public void iVerifyDoubleDashIsPresentInSignatureDateValueOnApplicationInfoOnApplicationTrackingTab() {
        assertEquals(applicationTrackingPage.signatureDateValue.getText(), "-- --", "Expected double dashes for a null value but found different value: ");
    }

    @Then("I verify I am on Search Application page")
    public void iVerifyIAmOnSearchApplicationPage() {
        waitFor(1);
        Assert.assertTrue(applicationSearchPage.searchTitle.getText().contains("SEARCH APPLICATION"), "Expected to be on Application Search page but did not find SEARCH APPLICATION Header");
    }

    @Then("I verify application is cleared from view")
    public void iVerifyApplicationIsClearedFromView() {
        Assert.assertFalse(quickIsDisplayed(applicationTrackingPage.applicationIcon), "Application is still in view after clicking on back arrow in Application tracking Tab");
    }

    @Then("I verify the labels in Application Tracking tab Members Info panel")
    public void iVerifyTheLabelsInApplicationTrackingTabMembersInfoPanel() {
        Assert.assertTrue(applicationTrackingPage.primaryIndividualIcon.isDisplayed(), "Expected to find Primary Individual Icon in Member(s) Info Panel but none found");
        assertEquals(applicationTrackingPage.memberInfoFullNameLabel.getText(), "FULL NAME", "Incorrect FULL NAME label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoAgeGenderLabel.getText(), "AGE / GENDER", "Incorrect AGE / GENDER label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoApplicantStatusLabel.getText(), "APPLICANT STATUS", "Incorrect APPLICANT STATUS label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoProgramAppliedLabel.getText(), "PROGRAM(S) APPLIED FOR", "Incorrect PROGRAM(S) APPLIED FOR label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoEligibilityStatusLabel.getText(), "ELIGIBILITY STATUS", "Incorrect ELIGIBILITY STATUS label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoDenialReasonsLabel.getText(), "DENIAL REASON(S)", "Incorrect DENIAL REASON(S) label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoDeterminationDateLabel.getText(), "DETERMINATION DATE", "Incorrect DETERMINATION DATE label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoStartDateLabel.getText(), "START DATE", "Incorrect START DATE label found in MEMBER(S) INFO panel in App Tracking tab");
        assertEquals(applicationTrackingPage.memberInfoEndDateLabel.getText(), "END DATE", "Incorrect END DATE label found in MEMBER(S) INFO panel in App Tracking tab");
    }

    @Then("I verify Primary Individual name created with sixtyone letters is first on list in Members Info panel")
    public void iVerifyPrimaryIndividualNameCreatedWithSixtyoneLettersIsFirstOnListInMembersInfoPanel() {
        String fullPiName = piFirstName + " " + piMi + " " + piLastName;
        Assert.assertTrue((piFirstName + piMi + piLastName).length() == 61);
        assertEquals(applicationTrackingPage.memberInfoFirstRowFullName.getText(), fullPiName);
    }

    @Then("I verify created Members following gender abbreviation are listed in Members Info Panel")
    public void iVerifyCreatedMembersFollowingGenderAbbreviationAreListedInMembersInfoPanel(List<String> data) {
        List<String> expectedGenderList = new ArrayList<>();
        List<String> actualGenderList = new ArrayList<>();
        expectedGenderList.addAll(data);
        for (WebElement webElement : applicationTrackingPage.memberInfoAgeGenderColumn) {
            actualGenderList.add(webElement.getText().substring(webElement.getText().length() - 1));
        }
        Collections.sort(expectedGenderList);
        Collections.sort(actualGenderList);
        assertEquals(actualGenderList, expectedGenderList);
    }

    @And("I verify correct ages are listed in members Info panel from created Applicant members")
    public void iVerifyCorrectAgesAreListedInMembersInfoPanelFromCreatedApplicantMembers(List<String> data) {
        List<String> actualAgeList = new ArrayList<>();
        List<String> dobList = new ArrayList<>();
        List<String> expectedAgeList = new ArrayList<>();
        dobList.addAll(data);
        for (String each : dobList) {
            expectedAgeList.add(String.valueOf(calculateAge(each)));
        }
        for (WebElement webElement : applicationTrackingPage.memberInfoAgeGenderColumn) {
            actualAgeList.add(webElement.getText().substring(0, webElement.getText().indexOf(" /")));
        }
        Collections.sort(actualAgeList);
        Collections.sort(expectedAgeList);
        assertEquals(actualAgeList, expectedAgeList, "Incorrect ages found for Applicant Members: ");
    }

    @Then("I verify the programs are listed and displayed stacked in Alphabetical order")
    public void iVerifyTheProgramsAreListedAndDisplayedStackedInAlphabeticalOrder() {
        assertEquals(applicationTrackingPage.memberInfoFirstRowFirstProgram.getText(), "CHIP", "Incorrect first Program found in program List");
        assertEquals(applicationTrackingPage.memberInfoFirstRowSecondProgram.getText(), "Medicaid", "Incorrect second Program found in program List");
        assertEquals(applicationTrackingPage.memberInfoFirstRowThirdProgram.getText(), "Pregnancy Assistance", "Incorrect third Program found in program List");
    }

    @Then("I verify Long Term Care {string} is listed in Application Tracking page Members Info panel")
    public void iVerifyLongTermCareIsListedInApplicationTrackingPageMembersInfoPanel(String programType) {
        assertEquals(applicationTrackingPage.hcbsFirstRowFirstProgram.getText(), programType, "Incorrect first Program found in program List: ");
    }

    @Then("I verify double dash is used for null values in Application tracking tab Members Info panel")
    public void iVerifyDoubleDashIsUsedForNullValuesInApplicationTrackingTabMembersInfoPanel() {
        assertEquals(applicationTrackingPage.memberInfoFirstRowAgeGender.getText(), doubleDash, "Expected -- -- for Age / Gender value in Members infor panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowPrograms.getText(), doubleDash, "Expected -- -- for Programs List value in Members infor panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowEligiblityStatus.getText(), doubleDash, "Expected -- -- for Eligibility Status value in Members infor panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowDenialReasons.getText(), doubleDash, "Expected -- -- for Denial Reason(s) in Members info panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowDeterminationDate.getText(), doubleDash, "Expected -- -- for Determination Date value in Members info panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowStartDate.getText(), doubleDash, "Expected -- -- for Start Date value in Members info panel but found different");
        assertEquals(applicationTrackingPage.memberInfoFirstRowEndDate.getText(), doubleDash, "Expected -- -- for End Date Status value in Members info panel but found different");
    }

    @Then("I verify the following labels in Contact Info panel in Application tracking Tab")
    public void iVerifyTheFollowingLabelsInContactInfoPanelInApplicationTrackingTab(List<String> data) {
        waitForVisibility(applicationTrackingPage.contactInfoPanelHeader, 5);
        assertEquals(applicationTrackingPage.contactInfoPanelHeader.getText(), "CONTACT INFO", "Expected to Find CONTACT INFO Header inside panel in Application Tracking tab");
        List<String> actualLabelList = new ArrayList<>();
        List<String> expectedLabellist = new ArrayList<>();
        expectedLabellist.addAll(data);
        for (WebElement each : applicationTrackingPage.contactInfoLabels) {
            actualLabelList.add(each.getText());
        }
        assertEquals(actualLabelList, expectedLabellist, "Incorrect label found in Contact Info : ");
    }

    @Then("I verify Primary Phone Number Spoken Written Language and Residence Address values are shown in Contact Info Panel")
    public void iVerifyPrimaryPhoneNumberSpokenWrittenLanguageAndResidenceAddressValuesAreShownInContactInfoPanel() {
        fullResidentAddress = residenceAddressLineOne + "," + residenceAddressLinetwo + "," + residenceAddressCity + "," + residenceAddressState + "," + residenceAddressZip + "," + residenceAddressCounty;
        primaryPhoneNum = "(" + primaryPhoneNum.substring(0, 3) + ") " + primaryPhoneNum.substring(3, 6) + "-" + primaryPhoneNum.substring(6);
        assertEquals(applicationTrackingPage.contactInfoValues.get(3).getText(), fullResidentAddress, "Incorrect Resident Address found in Contact Info panel: ");
        assertEquals(applicationTrackingPage.contactInfoValues.get(0).getText(), primaryPhoneNum, "Incorrect Primary Phone number found in Contact Info panel: ");
        assertEquals(applicationTrackingPage.contactInfoValues.get(1).getText(), spokenLanguage, "Incorrect Spoken Language found in Contact Info panel: ");
        assertEquals(applicationTrackingPage.contactInfoValues.get(2).getText(), writtenLanguage, "Incorrect Written Language found in Contact Info panel: ");
    }


    @Then("I verify double dash is used for null values in the Contact Info panel for following")
    public void iVerifyDoubleDashIsUsedForNullValuesInTheContactInfoPanelForFollowing(List<String> data) {
        for (String each : data) {
            switch (each.toUpperCase()) {
                case "PRIMARY PHONE":
                    assertEquals(applicationTrackingPage.contactInfoValues.get(0).getText(), doubleDash, "Expected \"-- --\" for Primary Phone Number but found different: ");
                    break;
                case "SPOKEN LANGUAGE":
                    assertEquals(applicationTrackingPage.contactInfoValues.get(1).getText(), "English", "Expected Spoken Language default value English but found different: ");
                    break;
                case "WRITTEN LANGUAGE":
                    assertEquals(applicationTrackingPage.contactInfoValues.get(2).getText(), "English", "Expected Written Language default value English but found different: ");
                    break;
                case "RESIDENT ADDRESS":
                    assertEquals(applicationTrackingPage.contactInfoValues.get(3).getText(), doubleDash, "Expected \"-- --\" for Resident Address Number but found different: ");
                    break;
            }
        }
    }

    @Then("I verify the following labels in AUTHORIZED REPRESENTATIVES Members Info Panel")
    public void iVerifyTheFollowingLabelsInAUTHORIZEDREPRESENTATIVESMembersInfoPanel(List<String> data) {
        for (String each : data) {
            switch (each.toUpperCase()) {
                case "FULL NAME":
                    assertEquals(applicationTrackingPage.authRepPanelLabels.get(1).getText(), "FULL NAME", "Expected \"-- --\" for Primary Phone Number but found different: ");
                    break;
                case "AUTH TYPE":
                    assertEquals(applicationTrackingPage.authRepPanelLabels.get(2).getText(), "AUTH TYPE", "Expected Spoken Language default value English but found different: ");
                    break;
                case "ACCESS TYPE":
                    assertEquals(applicationTrackingPage.authRepPanelLabels.get(3).getText(), "ACCESS TYPE", "Expected Written Language default value English but found different: ");
                    break;
                case "AUTHORIZED":
                    assertEquals(applicationTrackingPage.authRepPanelLabels.get(4).getText(), "AUTHORIZED", "Expected \"-- --\" for Resident Address Number but found different: ");
                    break;
                case "STATUS":
                    assertEquals(applicationTrackingPage.authRepPanelLabels.get(5).getText(), "STATUS", "Expected \"-- --\" for Resident Address Number but found different: ");
                    break;
                default:
                    Assert.fail("Wrong Label value provided");
            }
        }
    }

    @Then("I verify Auth Type contains the following in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab")
    public void iVerifyAuthTypeContainsTheFollowingInAUTHORIZEDREPRESENTATIVESPanelForApplicationTrackingTab(List<String> data) {
        List<String> actualAuthTypeList = new ArrayList<>();
        List<String> expectedAuthTypeList = new ArrayList<>(data);
        for (WebElement webElement : applicationTrackingPage.authRepPanelAuthTypeColumn) {
            actualAuthTypeList.add(webElement.getText());
        }
        Collections.sort(actualAuthTypeList);
        Collections.sort(expectedAuthTypeList);
        assertEquals(actualAuthTypeList, expectedAuthTypeList, "Incorrect list of Auth Type shown in AUTHORIZED REPRESENTATIVE(S) pannel in Application tracking tab");
    }

    @Then("I verify the the folowing Access typ is in AUTHORIZED REPRESENTATIVES panel for Application Tracking tab")
    public void iVerifyTheTheFolowingAccessTypIsInAUTHORIZEDREPRESENTATIVESPanelForApplicationTrackingTab(List<String> data) {
        List<String> actualAccessType = new ArrayList<>();
        List<String> expectedAccessType = new ArrayList<>(data);
        for (WebElement webElement : applicationTrackingPage.authRepPanelAccessTypeColumn) {
            actualAccessType.add(webElement.getText());
        }
        Collections.sort(expectedAccessType);
        Collections.sort(actualAccessType);
        assertEquals(actualAccessType, expectedAccessType, "Incorrect list of Access Type shown in AUTHORIZED REPRESENTATIVE(S) pannel in Application tracking tab");
    }

    @Then("I verify the Full Name for auth rep in AUTHORIZED REPRESENTATIVES panel contains sixtyone letters for First Mi and Last Name")
    public void iVerifyTheFullNameForAuthRepInAUTHORIZEDREPRESENTATIVESPanelContainsSixtyoneLettersForFirstMiAndLastName() {
        String authRepFullName = authRep.authRepFirstName + " " + authRep.authRepMi + " " + authRep.authRepLastName;
        Assert.assertTrue(applicationTrackingPage.authRepPanelFirstRowValues.get(1).getText().replaceAll(" ", "").length() == 61);
        assertEquals(applicationTrackingPage.authRepPanelFirstRowValues.get(1).getText(), authRepFullName, "Incorrect Auth Rep name found in AUTHORIZED REPRESENTATIVE(S) panel in Application tracking: ");
    }

    @Then("I verify Authorized is shown with green Yes and Unauthorized is shown with red No in AUTHORIZED REPRESENTATIVES panel")
    public void iVerifyAuthorizedIsShownWithGreenYesAndUnauthorizedIsShownWithRedNoInAUTHORIZEDREPRESENTATIVESPanel() {
        WebElement firstRowAuthorized = applicationTrackingPage.authRepPanelFirstRowValues.get(4);
        WebElement secondRowAuthorized = applicationTrackingPage.authRepPanelSecondRowValues.get(4);
        Assert.assertTrue(firstRowAuthorized.getAttribute("class").contains("green") && "Yes".equals(firstRowAuthorized.getText()), "Expected green Yes value for Authorized but found: ");
        Assert.assertTrue(secondRowAuthorized.getAttribute("class").contains("red") && "No".equals(secondRowAuthorized.getText()), "Expected red No value for Authorized but found: ");
    }

    @Then("I verify the created Auth Reps are in order of Active status first then Future status")
    public void iVerifyTheCreatedAuthRepsAreInOrderOfActiveStatusFirstThenFutureStatus() {
        WebElement firstRowStatus = applicationTrackingPage.authRepPanelFirstRowValues.get(5);
        WebElement secondRowStatus = applicationTrackingPage.authRepPanelSecondRowValues.get(5);
        WebElement thirdRowStatus = applicationTrackingPage.authRepPanelThirdRowValues.get(5);
        WebElement fourthRowStatus = applicationTrackingPage.authRepPanelFourthRowValues.get(5);
        assertEquals(firstRowStatus.getText(), "ACTIVE", "Incorrect Auth Rep status found: ");
        assertEquals(secondRowStatus.getText(), "ACTIVE", "Incorrect Auth Rep status found: ");
        assertEquals(thirdRowStatus.getText(), "FUTURE", "Incorrect Auth Rep status found: ");
        assertEquals(fourthRowStatus.getText(), "FUTURE", "Incorrect Auth Rep status found: ");
    }

    @Then("I verify that there is no Authorized Representative panel shown in the Members panel")
    public void iVerifyThatThereIsNoAuthorizedRepresentativePanelShownInTheMembersPanel() {
        Assert.assertFalse(isElementDisplayed(applicationTrackingPage.authorizedRepresentativePanelHeader), "Expected no Auth Rep panel but found AUTHORIZED REPRESENTATIVE(S) Header");
    }

    @Then("I should see double dash is represented for null value in Access Type in AUTHORIZED REPRESENTATIVE panel")
    public void iShouldSeeDoubleDashIsRepresentedForNullValueInAccessTypeInAUTHORIZEDREPRESENTATIVEPanel() {
        assertEquals(applicationTrackingPage.authRepPanelFirstRowValues.get(3).getText(), doubleDash, "Expected -- -- for Null value for Access type in Auth Rep but found: ");
    }

    @And("I verify the {string} priority value on the Application Tracking page")
    public void iVerifyThePriorityValueOnTheApplicationTrackingPage(String expectedPriorityValue) {
        waitForVisibility(applicationTrackingPage.priorityLabel, 10);
        assertEquals(applicationTrackingPage.priorityValue.getText(), expectedPriorityValue, "Unexpected Priority value found in saved Application details page");
    }

    @Then("I verify Applicant status {string} for PI and AM on application tracking page")
    public void iVerifyApplicantStatusForPIAndAMe(String applicantStatus) {
        waitFor(2);
        assertEquals(applicationTrackingPage.memberInfoFirstRowApplicantStatus.getText(), applicantStatus, "Mismatch in expected Applicant Status for Primary Individual");
        assertEquals(applicationTrackingPage.memberInfoSecondRowApplicantStatus.getText(), applicantStatus, "Mismatch in expected Applicant Status for Application Member");
    }

    @And("I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab")
    public void iVerifyEditButtonIsDisplayedAndDisabledInApplicationTrackingPageForApplicationInformationTab() {
        Assert.assertTrue(applicationTrackingPage.editButtonApplicationInformation.isDisplayed(), "App Information EDIT button is not displayed");
        Assert.assertFalse((applicationTrackingPage.editButtonApplicationInformation.isEnabled()), "App Information EDIT button is not disabled");
    }

    @And("I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel")
    public void iVerifyEditButtonIsDisplayedAndDisabledInApplicationTrackingPageForApplicationMemberPanel() {
        Assert.assertTrue(applicationTrackingPage.editButtonMembersInfoPanel.isDisplayed(), "Member(s) Information EDIT button is not displayed");
        Assert.assertFalse((applicationTrackingPage.editButtonMembersInfoPanel.isEnabled()), "Member(s) Information EDIT button is not disabled");
    }

    @And("I verify Application Information panel has the  {string} label within Application Tracking Tab")
    public void iVerifyApplicationInformationPanelHasTheLabelWithinApplicationTrackingTab(String verifyLabel) {
        List<String> appInfoColumnsString = new ArrayList<>();
        for (WebElement appInfoColumnsElement : applicationTrackingPage.appInfoColumns) {
            appInfoColumnsString.add(appInfoColumnsElement.getText());
        }
        Assert.assertTrue(appInfoColumnsString.contains(verifyLabel), "Application information label doesn't contain the " + verifyLabel);
    }


    @Then("I verify Edit button is displayed and clickable in Application Information Panel")
    public void iVerifyEditButtonIsDisplayedAndClickableInApplicationInformationPanel() {
        Assert.assertTrue(applicationTrackingPage.editButtonApplicationInformation.isDisplayed(), "Edit Button is not displayed for App Member Info Panel");
        Assert.assertTrue(applicationTrackingPage.editButtonApplicationInformation.isEnabled(), "Edit Button is not enabled for App Member Info Panel");
    }

    @And("I verify Edit button is displayed and enabled in Application Tracking Page for  Application Member Panel")
    public void iVerifyEditButtonIsDisplayedAndEnabledInApplicationTrackingPageForApplicationMemberPanel() {
        waitFor(5);
        Assert.assertTrue(applicationTrackingPage.editButtonMembersInfoPanel.isDisplayed(), "Edit Button is not displayed for  Member Info Panel");
        Assert.assertTrue(applicationTrackingPage.editButtonMembersInfoPanel.isEnabled(), "Edit Button is not enabled for  Member Info Panel");
    }

    @And("I click Edit Button for the Application Information Panel")
    public void iClickEditButtonForTheApplicationInformationPanel() {
        waitFor(5);
        click(applicationTrackingPage.editButtonApplicationInformation);
    }

    @And("I click Edit Button for the Members Info Panel")
    public void iClickEditButtonForTheMembersInfoPanel() {
        waitFor(5);
        scrollToElement(applicationTrackingPage.editButtonMembersInfoPanel);
        waitForClickablility(applicationTrackingPage.editButtonMembersInfoPanel,4);
        jsClick(applicationTrackingPage.editButtonMembersInfoPanel);
    }

    @And("I select {string} for the {string} on Application Tracking Page")
    public void iSelectForTheOnApplicationTrackingPage(String dateType, String dateLabel) {
        if (dateLabel.equalsIgnoreCase("DEADLINE DATE")) {
            int randomDays = Integer.parseInt(getRandomNumber(3));
            switch (dateType.toUpperCase()) {
                case "PRESENT DATE":
                    deadlineDate = getCurrentDate();
                    break;
                case "RANDOM PAST DATE":
                    deadlineDate = BrowserUtils.getPriorDate(randomDays);
                    break;
                default:
                    deadlineDate = BrowserUtils.getGreaterDateFormatMMddyyyy(randomDays);
                    break;
            }
            clearAndFillText(applicationTrackingPage.deadlineDateInputBox, deadlineDate);
        }
    }

    @When("I click save button for Application Information Panel")
    public void iClickSaveButtonForApplicationInformationPanel() {
        waitForClickablility(applicationTrackingPage.applicationInfoSaveButton, 5);
        applicationTrackingPage.applicationInfoSaveButton.click();
    }

    @Then("I verify {string} on Application Tracking Page")
    public void iVerifyOnApplicationTrackingPage(String verifyValue) {
        waitForVisibility(applicationTrackingPage.externalAppId, 10);
        if (verifyValue.equalsIgnoreCase("DEADLINE DATE")) {
            assertEquals(applicationTrackingPage.deadlineDateValue.getText(), deadlineDate, "Deadline Date didn't match , Expected : " + deadlineDate + "Actual :" + applicationTrackingPage.deadlineDateValue.getText());
        } else if (verifyValue.equalsIgnoreCase("FIRST DEADLINE DATE")) {
            assertEquals(applicationTrackingPage.deadlineDateValue.getText(), deadlineDateFirstValue, "Deadline Date didn't match , Expected : " + deadlineDateFirstValue + "Actual :" + applicationTrackingPage.deadlineDateValue.getText());
        } else if ("EXTERNAL APPLICATION ID".equals(verifyValue)) {
            assertEquals(CRM_CreateApplicationStepDef.extAppID, applicationTrackingPage.externalAppId.getText(), "Expected Tracking Tab External App Id Did not match Entered External App ID");
        } else if ("EXT APP ID WITHOUT SPACES".equals(verifyValue)) {
            assertEquals(CRM_CreateApplicationStepDef.extAppID.replaceAll(" ", "").trim(), applicationTrackingPage.externalAppId.getText(), "Expected Tracking Tab External App Id Did not match Entered External App ID");
        } else if ("OVER MAX VARIABLE CHARACTERS".equals(verifyValue)) {
            assertEquals(CRM_CreateApplicationStepDef.extAppID.substring(0, 36), applicationTrackingPage.externalAppId.getText(), "Expected Tracking Tab External App Id Did not match Entered External App ID");
        }
    }

    @Then("I verify Warning message displayed for {string} on Application Tracking Page")
    public void iVerifyWarningMessageDisplayedForOnApplicationTrackingPage(String messageLabel) {
      //  waitForVisibility(memberMatchingPage.backArrowWarningMessage,5);
        waitFor(3);
        if (messageLabel.equalsIgnoreCase("DEADLINE DATE")) {
            Assert.assertTrue(memberMatchingPage.deadlineDateWarningMessage.isDisplayed(), "DEADLINE DATE cannot be before RECEIVED DATE message is not displayed");
        }
    }

    @And("I store the {string} on Application tracking Page")
    public void iStoreTheOnApplicationTrackingPage(String storedLabel) {
        if (storedLabel.equalsIgnoreCase("DEADLINE DATE")) {
            deadlineDateFirstValue = applicationTrackingPage.deadlineDateValue.getText();
        }
    }

    @Then("I verify I am on the Application Tracking Page")
    public void iVerifyIAmOnTheApplicationTrackingPage() {
        scrollToTop();
        Assert.assertTrue(applicationTrackingPage.applicationInformationPanel.isDisplayed(), "User is not on the Application Tracking Page,Application Information Header not displayed");
    }

    @And("I set all the Eligibility Status of the {string} as {string}")
    public void iSetAllTheEligibilityStatusOfTheAs(String applicationMemberType, String eligibilityStatus) {
        switch (applicationMemberType.toUpperCase()) {
            case "ALL":
                for (WebElement eligibilityDropDown :
                        applicationTrackingPage.memberInfoEligibilityStatusDropDownList) {
                    selectDropDown(eligibilityDropDown, eligibilityStatus);
                }
                break;
            case "PI": // Only for single program scenario
                selectDropDown(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, eligibilityStatus);
                break;
            case "AM":// Only for single program scenario
                singleSelectFromDropDown(applicationTrackingPage.memberInfoSecondRowEligibilityStatus, eligibilityStatus);
                break;
        }
    }

    @And("I set all the Eligibility subprograms of {string} as {string}")
    public void iSetAllTheEligibilitySubProgramsOf(String applicationMemberType, String subProgram) {
        if(applicationMemberType.toUpperCase().equals("ALL")) {
                for (WebElement subProgramDropDown :
                        applicationTrackingPage.membersInfoEligibilitySubProgramList) {
                    try {
                        assertTrue(subProgramDropDown.getAttribute("aria-disabled").equals("true"));
                    }
                    catch(Exception e) {
                        if (subProgram.equals("RANDOM"))
                            selectRandomDropDownOption(subProgramDropDown);
                        else
                            selectDropDown(subProgramDropDown, subProgram);
                    }
                }
        }
    }
    @Then("I set all the Eligibility Status of the Application Member as {string}")
    public void iSetAllTheEligibilityStatusOfTheApplicationMemberAs(String eligibilityStatus) {
        for (WebElement eligibilityDropDown :
                applicationTrackingPage.memberInfoEligibilityStatusDropDownList) {
            selectDropDown(eligibilityDropDown, eligibilityStatus);
        }
    }

    @And("I set all the Denial Reasons of the {string} as {string}")
    public void iSetAllTheDenialReasonsOfTheAs(String applicationMemberType, String denialReasons) {
        switch (applicationMemberType.toUpperCase()) {
            case "ALL":
                for (WebElement denialReasonDropdown : applicationTrackingPage.membersInfoEligibilityDenialReasonsDropdownList
                ) {
                    singleSelectFromDropDown(denialReasonDropdown, denialReasons);
                }
                break;
            case "PI": // Only for single program scenario
                singleSelectFromDropDown(applicationTrackingPage.memberInfoFirstRowDenialReasons, denialReasons);
                break;
            case "AM":// Only for single program scenario
                singleSelectFromDropDown(applicationTrackingPage.memberInfoSecondRowDenialReasons, denialReasons);
                break;
        }
    }

    @Then("I verify all the Eligibility Status of the Application Member displays as {string}")
    public void iVerifyAllTheEligibilityStatusOfTheApplicationMemberDisplaysAs(String eligibilityStatus) {
        int rowNumber = 1;
        for (WebElement eligibilityDropDown :
                applicationTrackingPage.memberInfoEligibilityStatusDropDownList) {
            assertEquals(eligibilityDropDown.getText(), eligibilityStatus,
                    "Eligibility status is not as expected for " + rowNumber + "Expected : " + eligibilityStatus + " Actual : " + eligibilityDropDown.getText());
            rowNumber++;
        }
    }

    @Then("I set all the Eligibility Start Date of the Application Member as {string}")
    public void iSetAllTheEligibilityStartDateOfTheApplicationMemberAs(String dateType) {
        int randomDays = Integer.parseInt(getRandomNumber(3));
        switch (dateType.toUpperCase()) {
            case "PRESENT DATE":
                eligibilityStartDate = getCurrentDate();
                break;
            case "FUTURE DATE":
                eligibilityStartDate = BrowserUtils.getGreaterDateFormatMMddyyyy(randomDays);
                break;
            default:
                eligibilityStartDate = BrowserUtils.getPriorDate(randomDays);
                break;
        }
        System.out.println(eligibilityStartDate);
        for (WebElement startDate : applicationTrackingPage.memberInfoStartDateList) {
            clearAndFillText2(startDate, eligibilityStartDate);
        }
    }

    @Then("I set all the Eligibility End Date of the Application Member as {string}")
    public void iSetAllTheEligibilityEndDateOfTheApplicationMemberAs(String dateType) {
        int randomDays = Integer.parseInt(getRandomNumber(3));
        switch (dateType.toUpperCase()) {
            case "CURRENT":
                eligibilityEndDate = getCurrentDate();
                break;
            case "PAST":
                eligibilityEndDate = BrowserUtils.getPriorDate(randomDays);
                break;
            default:
                eligibilityEndDate = BrowserUtils.getGreaterDateFormatMMddyyyy(randomDays);
                break;
        }
        for (WebElement startDate : applicationTrackingPage.memberInfoEndDateList) {
            clearAndFillText2(startDate, eligibilityEndDate);
        }
    }

    @Then("I verify  the Eligibility Status of the {string} displays as {string}")
    public void iVerifyTheEligibilityStatusOfTheDisplaysAs(String applicationMember, String verifyValue) {
        waitFor(2);
        switch (applicationMember) {
            case "Primary Individual":
                if (!programListPI.isEmpty()) {
                    for (String program : programListPI) {
                        By tempEligibilityStatus = By.xpath("//td[text()='" + program + "']/following-sibling::td[1]");
                        assertEquals(getElementsText(tempEligibilityStatus).get(0), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + getElementsText(tempEligibilityStatus).get(0));
                    }
                } else
                    assertEquals(applicationTrackingPage.memberInfoFirstRowEligibilityStatus.getText(), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + applicationTrackingPage.memberInfoFirstRowEligibilityStatus.getText());
                break;
            case "Application Member 1":
                if (!programListPI.isEmpty()) {
                    for (String program : programList.get()) {
                        By tempEligibilityStatus = By.xpath("//td[text()='" + program + "']/following-sibling::td[1]");
                        if (programListPI.contains(program)) {
                            assertEquals(getElementsText(tempEligibilityStatus).get(1), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + getElementsText(tempEligibilityStatus).get(1));
                        } else {
                            assertEquals(getElementsText(tempEligibilityStatus).get(0), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + getElementsText(tempEligibilityStatus).get(0));
                        }
                    }
                } else
                    assertEquals(applicationTrackingPage.memberInfoSecondRowEligibilityStatus.getText(), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + applicationTrackingPage.memberInfoFirstRowEligibilityStatus.getText());
                break;
            case "Application Member 2":
                assertEquals(applicationTrackingPage.memberInfoThirdRowEligibilityStatus.getText(), verifyValue, "Eligibility status is not as expected for " + applicationMember + "Expected : " + verifyValue + " Actual : " + applicationTrackingPage.memberInfoFirstRowEligibilityStatus.getText());
                break;
        }
    }

    @Then("I click on save button for the Members Info Panel")
    public void iClickOnSaveButtonForTheMembersInfoPanel() {
        click(applicationTrackingPage.saveButtonMembersInfoPanel);
        waitFor(5);
    }

    @Then("I verify there is no {string} displays for the AUTHORIZED REPRESENTATIVE")
    public void iVerifyThereIsNoDisplaysForTheAUTHORIZEDREPRESENTATIVE(String verifyLabel) {
        for (WebElement label :
                applicationTrackingPage.authRepPanelLabels) {
            Assert.assertFalse(label.getText().equalsIgnoreCase(verifyLabel), verifyLabel + "found for Authorized Representative");
        }
    }

    @Then("I verify I will see {string} as {string} for PI on Members Info Panel")
    public void iVerifyIWillSeeExpectedValuesForPIOnMembersInfoPanel(String verifyLabels, String expectedValues) {
        switch (verifyLabels.toUpperCase()) {
            case "APPLICANT STATUS":
                assertEquals(applicationTrackingPage.memberInfoFirstRowApplicantStatus.getText(), expectedValues, "APPLICANT STATUS values is not as expected");
                break;
            case "PROGRAMS APPLIED FOR":
                assertEquals(applicationTrackingPage.memberInfoFirstRowPrograms.getText(), expectedValues, "PROGRAMS APPLIED FOR values is not as expected");
                break;
            case "ELIGIBILITY STATUS":
                assertEquals(applicationTrackingPage.memberInfoFirstRowEligibilityStatus.getText(), expectedValues, "ELIGIBILITY STATUS FOR values is not as expected");
                break;
            case "DENIAL REASONS":
                assertEquals(applicationTrackingPage.memberInfoFirstRowDenialReasons.getText(), expectedValues, "DENIAL REASONS  values is not as expected");
                break;
            case "DETERMINATION DATE":
                if ("CURRENT DATE".equals(expectedValues)) {
                    expectedValues = getCurrentDate();
                    assertEquals(applicationTrackingPage.eligibilityDeterminationDate.getAttribute("value"), expectedValues, "DETERMINATION DATE  values is not as expected");
                }
                else if ("PAST DATE".equals(expectedValues)) {
                    expectedValues = getPriorDateFormatMMddyyyy(1);
                    assertEquals(applicationTrackingPage.eligibilityDeterminationDate.getAttribute("value"), expectedValues, "DETERMINATION DATE  values is not as expected");
                }
                else {
                    assertEquals(applicationTrackingPage.memberInfoFirstRowDeterminationDate.getText(), expectedValues, "DETERMINATION DATE  values is not as expected");
                }
                break;
            case "START DATE":
                assertEquals(applicationTrackingPage.memberInfoFirstRowStartDate.getText(), expectedValues, "START DATE  values is not as expected");
                break;
            case "END DATE":
                assertEquals(applicationTrackingPage.memberInfoFirstRowEndDate.getText(), expectedValues, "END DATE  values is not as expected");
                break;
            case "SUB-PROGRAM":
                System.out.println("Found: "+applicationTrackingPage.memberInfoFirstRowSubProgram.getText());
                assertEquals(applicationTrackingPage.memberInfoFirstRowSubProgram.getText(), expectedValues, "SUB-PROGRAM  values is not as expected");
                break;


        }
    }

    @Then("I verify I will see {string} as {string} for AM on Members Info Panel")
    public void iVerifyIWillSeeExpectedValuesForAMOnMembersInfoPanel(String verifyLabels, String expectedValues) {
        switch (verifyLabels.toUpperCase()) {
            case "APPLICANT STATUS":
                assertEquals(applicationTrackingPage.memberInfoSecondRowApplicantStatus.getText(), expectedValues, "APPLICANT STATUS values is not as expected");
                break;
            case "PROGRAMS APPLIED FOR":
                assertEquals(applicationTrackingPage.memberInfoSecondRowPrograms.getText(), expectedValues, "PROGRAMS APPLIED FOR values is not as expected");
                break;
            case "ELIGIBILITY STATUS":
                assertEquals(applicationTrackingPage.memberInfoSecondRowEligibilityStatus.getText(), expectedValues, "ELIGIBILITY STATUS FOR values is not as expected");
                break;
            case "DENIAL REASONS":
                assertEquals(applicationTrackingPage.memberInfoSecondRowDenialReasons.getText(), expectedValues, "DENIAL REASONS  values is not as expected");
                break;
            case "DETERMINATION DATE":
                assertEquals(applicationTrackingPage.memberInfoSecondRowDeterminationDate.getText(), expectedValues, "DETERMINATION DATE  values is not as expected");
                break;
            case "START DATE":
                assertEquals(applicationTrackingPage.memberInfoSecondRowStartDate.getText(), expectedValues, "START DATE  values is not as expected");
                break;
            case "END DATE":
                assertEquals(applicationTrackingPage.memberInfoSecondRowEndDate.getText(), expectedValues, "END DATE  values is not as expected");
                break;
        }
    }

    @Then("I Select {string} to verify the following dropdown values")
    public void iSelectToVerifyTheFollowingDropdownValues(String dtype, List<String> dValues) {
        switch (dtype) {
            case "Eligibility Status":
                List<String> actualStatusList = new ArrayList<>();
                List<String> expectedStatusList = new ArrayList<>(dValues);
                waitForClickablility(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, 5);
                applicationTrackingPage.memberInfoFirstRowEligibilityStatus.click();
                waitFor(2);
                for (WebElement webElement : applicationTrackingPage.eligibilityDropdownList) {
                    actualStatusList.add(webElement.getText());
                }
                applicationTrackingPage.eligibilityDropdownList.get(0).click();
                Collections.sort(expectedStatusList);
                Collections.sort(actualStatusList);
                assertEquals(actualStatusList, expectedStatusList);
                break;
            case "Denial Reasons":
                List<String> actualDenialList = new ArrayList<>();
                List<String> expectedDenialList = new ArrayList<>(dValues);
                selectDropDown(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, "Not Eligible");
                waitForClickablility(applicationTrackingPage.memberInfoFirstRowDenialReasons, 5);
                applicationTrackingPage.memberInfoFirstRowDenialReasons.click();
                waitFor(2);
                for (WebElement webElement : applicationTrackingPage.eligibilityDropdownList) {
                    actualDenialList.add(webElement.getText());
                }
                Collections.sort(expectedDenialList);
                Collections.sort(actualDenialList);
                assertEquals(actualDenialList, expectedDenialList);
                break;
            case "Sub-Program":
                List<String> actualSubProgramList = new ArrayList<>();
                List<String> expectedSubProgramList = new ArrayList<>(dValues);
                waitForClickablility(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, 5);
                applicationTrackingPage.eliSubProgram.click();
                waitFor(2);
                for (WebElement webElement : applicationTrackingPage.eligibilityDropdownList) {
                    actualSubProgramList.add(webElement.getText());
                }
                applicationTrackingPage.eligibilityDropdownList.get(0).click();
                Collections.sort(expectedSubProgramList);
                Collections.sort(actualSubProgramList);
                assertEquals(actualSubProgramList, expectedSubProgramList);
                break;
        }
    }

    @Then("I verify Eligibility edit field has cleared from view")
    public void iVerifyEligibilityEditFieldHasClearedFromView() {
        waitFor(3);
        assertFalse(quickIsDisplayed(applicationTrackingPage.eliStatusDropdown));
        assertFalse(quickIsDisplayed(applicationTrackingPage.eliDenialDropdown));
    }

    @Override
    public boolean quickIsDisplayed(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            if (element.isDisplayed()) return true;
        } catch (Exception exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            return false;
        }
        return false;
    }

    @And("I click on {string} dropdown and select the following values")
    public void iClickOnDropdownAndSelectTheFollowingValues(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "ELIGIBILITY STATUS":
                waitForClickablility(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, 5);
                selectDropDown(applicationTrackingPage.memberInfoFirstRowEligibilityStatus, data.get(0));
                break;
            case "SUB-PROGRAM":
                waitForClickablility(applicationTrackingPage.eliSubProgram, 5);
                selectDropDown(applicationTrackingPage.eliSubProgram, data.get(0));
                break;
            case "DENIAL REASONS":
                multiDropdownSelector(applicationTrackingPage.eliDenialDropdown, data);
                waitFor(2);
                action.moveToElement(createApplicationPage.missingInfoTab).click(applicationTrackingPage.applicationTrackingTab).build().perform();
                break;
            case "DETERMINATION DATE":
                if ("FUTURE DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDate.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDate.sendKeys(getGreaterDateFormatMMddyyyy(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                }
                else if ("PAST DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDate.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDate.sendKeys(getPriorDateFormatMMddyyyy(1));
                } else if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDate.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilityDeterminationDate.sendKeys(data.get(0));
                }
                break;
            case "START DATE":
                if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityStartDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityStartDate.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilityStartDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityStartDate.sendKeys(data.get(0));
                }
                break;
            case "END DATE":
                if ("FUTURE DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityEndDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityEndDate.sendKeys(getGreaterDateFormatMMddyyyy(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                } else {
                    applicationTrackingPage.eligibilityEndDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityEndDate.sendKeys(data.get(0));
                }
                break;
        }
    }

    private List<String> convertToStrList(List<WebElement> options) {
        List<String> strOptions = new ArrayList<>();
        options.forEach(option -> {
            strOptions.add(option.getText().trim());
        });
        return strOptions;
    }

    public List<String> multiDropdownSelector(WebElement element, List<String> selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        waitFor(1);
        strOptions = convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
        hover(element);
        for (String each : selector) {
            WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + each + "')]"));
            scrollToElement(single);
            hover(single);
            single.click();
            waitFor(1);
        }
        return strOptions;
    }

    @Then("I verify Members Info {string} warning message with following")
    public void iVerifyMembersInfoWarningMessageWithFollowing(String warningType, List<String> warningMsg) {
        switch (warningType) {
            case "FUTURE DETERMINATION DATE":
                waitForVisibility(applicationTrackingPage.futureWarningMsg,3);
                assertEquals(applicationTrackingPage.futureWarningMsg.getText(), warningMsg.get(0));
                break;
            case "START DATE REQUIRED":
                waitForVisibility(applicationTrackingPage.reqStartDateMsg,3);
                assertEquals(applicationTrackingPage.reqStartDateMsg.getText(), warningMsg.get(0));
                break;
            case "DENIAL REASON REQUIRED":
                waitForVisibility(applicationTrackingPage.reqDenialMsg,3);
                assertEquals(applicationTrackingPage.reqDenialMsg.getText(), warningMsg.get(0));
                break;
            case "SUB-PROGRAM REQUIRED":
                waitForVisibility(applicationTrackingPage.subProgramRequiredMsg,3);
                assertEquals(applicationTrackingPage.subProgramRequiredMsg.getText(), warningMsg.get(0));
                break;
        }
    }

    @Then("I verify the following fields are not editable on Members Info Panel")
    public void iVerifyTheFollowingFieldsAreNotEditableOnMembersInfoPanel(List<String> datatable) {
        for (String s : datatable) {
            switch (s) {
                case "DETERMINATION DATE":
                    assertFalse(applicationTrackingPage.eligibilityDeterminationDate.isEnabled(),"Determination Date is enabled");
                    break;
                case "START DATE":
                    assertFalse(applicationTrackingPage.eligibilityStartDate.isEnabled(),"Start Date is enabled");
                    break;
                case "END DATE":
                    assertFalse(applicationTrackingPage.eligibilityEndDate.isEnabled(),"End Date is enabled");
                    break;
            }
        }
    }

    @When("I click on missing information tab to navigate to Missing Information page from Application Tracking Page")
    public void iClickOnMissingInformationTabToNavigateToMissingInformationPageFromApplicationTrackingPage() {
        waitFor(2);
        waitForClickablility(applicationTrackingPage.missingInfoTab,5);
        click(applicationTrackingPage.missingInfoTab);
    }

    @And("I verify {string} field is saved as {string} in the Application Tracking tab")
    public void iVerifyFieldIsSavedAsInTheApplicationTrackingTab(String verifyType, String verifyValue) {
        waitFor(2);
        switch (verifyType){
            case "RECEIVED LANGUAGE":
                assertEquals(applicationTrackingPage.savedReceivedLanguageLabel.getText(), "RECEIVED LANGUAGE", "Did not find RECEIVED LANGUAGE label in saved application page");
                assertEquals(applicationTrackingPage.savedReceivedLanguage.getText(), verifyValue, "Mismatch in expected Saved Received Language");
                break;
            case "APPLICATION CODE UI":
                waitForVisibility(applicationTrackingPage.applicationCodeValue,5);
                assertEquals(applicationTrackingPage.applicationCodeValue.getText(), applicationCode, "Mismatch in expected Saved Application Code ");
                assertFalse(applicationTrackingPage.applicationCodeValue.getAttribute("class").contains("input"), "Expected uneditalbe field value but able to input");
                break;
            case "APPLICATION CODE API":
                waitForVisibility(applicationTrackingPage.applicationCodeValue,5);
                assertEquals(applicationTrackingPage.applicationCodeValue.getText(), applicationCodeAPI, "Mismatch in expected Saved Application Code");
                assertFalse(applicationTrackingPage.applicationCodeValue.getAttribute("class").contains("input"), "Expected uneditalbe field value but able to input");
                break;
        }
    }

    @And("I click on {string} dropdown for second row and select the following values")
    public void iClickOnDropdownSecondAndSelectTheFollowingValues(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "ELIGIBILITY STATUS":
                waitForClickablility(applicationTrackingPage.memberInformationSecondRowEligibilityStatus, 5);
                selectDropDown(applicationTrackingPage.memberInformationSecondRowEligibilityStatus, data.get(0));
                break;
            case "SUB-PROGRAM":
                waitForClickablility(applicationTrackingPage.eliSubProgramSecondRow, 5);
                selectDropDown(applicationTrackingPage.eliSubProgramSecondRow, data.get(0));
                break;
            case "DENIAL REASONS":
                multiDropdownSelector(applicationTrackingPage.eliDenialSecondDropdown, data);
                waitFor(2);
                action.moveToElement(createApplicationPage.missingInfoTab).click(applicationTrackingPage.applicationTrackingTab).build().perform();
                break;
            case "DETERMINATION DATE":
                if ("FUTURE DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilitySecondDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondDeterminationDate.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondDeterminationDate.sendKeys(getGreaterDateFormatMMddyyyy(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                }
                else if ("PAST DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilitySecondDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondDeterminationDate.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondDeterminationDate.sendKeys(getPriorDateFormatMMddyyyy(1));
                }else if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilitySecondDeterminationDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondDeterminationDate.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilitySecondDeterminationDate.sendKeys(data.get(0));
                }
                break;
            case "START DATE":
                if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilitySecondStartDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondStartDate.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilitySecondStartDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondStartDate.sendKeys(data.get(0));
                }
                break;
            case "END DATE":
                if ("FUTURE DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilitySecondEndDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondEndDate.sendKeys(getGreaterDateFormatMMddyyyy(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                } else {
                    applicationTrackingPage.eligibilitySecondEndDate.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilitySecondEndDate.sendKeys(data.get(0));
                }
                break;

        }
    }

    @And("I click on the first case ID under links section in application tracking page")
    public void iClickOnFirstCaseIdUnderLinksSectionInApplicationTrackingPage() {
        waitForClickablility(applicationTrackingPage.caseId.get(0),5);
        click((WebElement) applicationTrackingPage.caseId.get(0));
    }

    @When("I get the applicaion code from Application Tracking Page")
    public void iGetTheApplicationCodeFromApplicationTrackingPage() {
        waitForClickablility(applicationTrackingPage.applicationCodeValue,5);
        applicationCodeFromUI = applicationTrackingPage.applicationCodeValue.getText();
    }

    @And("I click on {string} dropdown for first row and select the following values")
    public void iClickOnDropdownFirstAndSelectTheFollowingValues(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "ELIGIBILITY STATUS":
                waitForClickablility(applicationTrackingPage.memberInfoFirstRowSecondProgramEligibilityStatus, 5);
                selectDropDown(applicationTrackingPage.memberInfoFirstRowSecondProgramEligibilityStatus, data.get(0));
                break;
            case "SUB-PROGRAM":
                waitForClickablility(applicationTrackingPage.eliSubProgramForSecondProgram, 5);
                selectDropDown(applicationTrackingPage.eliSubProgramForSecondProgram, data.get(0));
                break;
            case "DENIAL REASONS":
                multiDropdownSelector(applicationTrackingPage.eliDenialDropdownForSecondProgram, data);
                waitFor(2);
                action.moveToElement(createApplicationPage.missingInfoTab).click(applicationTrackingPage.applicationTrackingTab).build().perform();
                break;
            case "DETERMINATION DATE":
                if ("FUTURE DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.sendKeys(getGreaterDateFormatMMddyyyy(Integer.parseInt(RandomStringUtils.randomNumeric(1))));
                } else if ("PAST DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.clear();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.sendKeys(getPriorDateFormatMMddyyyy(1));
                } else if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityDeterminationDateForSecondProgram.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilitySecondDeterminationDate.sendKeys(data.get(0));
                }
                break;
            case "START DATE":
                if ("CURRENT DATE".equals(data.get(0))) {
                    applicationTrackingPage.eligibilityStartDateForSecondProgram.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityStartDateForSecondProgram.sendKeys(getCurrentDate());
                } else {
                    applicationTrackingPage.eligibilityStartDateForSecondProgram.click();
                    waitFor(1);
                    applicationTrackingPage.eligibilityStartDateForSecondProgram.sendKeys(data.get(0));
                }
                break;
        }
    }

    @Then("I verify that sub-program dropdown is not clickable")
    public void i_verify_that_sub_program_dropdown_is_not_clickable() {
        waitFor(1);
        assertTrue(applicationTrackingPage.eliSubProgram.getAttribute("class").contains("disabled"), "Sub-program dropdown is clickable");
      //  assertTrue(createApplication.primaryIndividualEditButton.getAttribute("class").contains("disabled"), "Edit button is clickable");

    }

}