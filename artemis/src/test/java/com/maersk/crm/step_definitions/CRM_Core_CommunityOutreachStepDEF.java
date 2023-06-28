package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APICoreCommunityOutreachController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.maersk.crm.utilities.BrowserUtils.getFutureDateFormatYYYYMMdd;
import static org.testng.Assert.*;

public class CRM_Core_CommunityOutreachStepDEF extends CRMUtilities implements ApiBase {

//    CRMUtilities crmUtils = new CRMUtilities();

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRM_CORE_CommunityOutreachPage communityOutreach = new CRM_CORE_CommunityOutreachPage();
    CRM_CORE_CommunitySearchPage communitySearchPage = new CRM_CORE_CommunitySearchPage();
    CRM_CORE_CommunityEditPage detailsEditPage = new CRM_CORE_CommunityEditPage();

    Actions actions = new Actions(Driver.getDriver());


    final List<String> reasonForEditValues = new ArrayList<>(Arrays.asList(
            "", "Updating Post Session Followup", "Updating Session Details", "Updating Session Status", "Updating Site Contact Information", "Updating Site Information"));
    final List<String> sessionTypeValues = new ArrayList<>(Arrays.asList(
            "Community Stakeholders Presentation", "Education/Enrollment Session", "Health Fair", "Parents Meeting", "PTA Meeting", "Staff Meeting"));
    final List<String> channelValues = new ArrayList<>(Arrays.asList("Consumer Request", "Project Request"));
    final List<String> estimatedAttendeesValues = new ArrayList<>(Arrays.asList("", "1-10", "100+", "11-25", "26-50", "51-100"));
    final List<String> sessionRegionValues = new ArrayList<>(Arrays.asList("Central", "East", "North", "Northeast", "Northwest", "Out of State", "South", "Southeast", "Southwest", "Statewide", "West"));
    final List<String> sessionStatusValues = new ArrayList<>(Arrays.asList("Cancelled", "Presented - Post Session Follow Up Screen to display", "Requested", "Scheduled"));
    final List<String> languagesValues = new ArrayList<>(Arrays.asList("English", "Other", "Russian", "Spanish", "Vietnamese"));
    final List<String> recurrenceFrequencyValues = new ArrayList<>(Arrays.asList("Monthly", "Quarterly", "Weekly", "Yearly"));
    final List<String> presenterValuesDCEB = new ArrayList<>(Arrays.asList("Iris", "Outreach Staff", "Terry"));
    final List<String> presenterValues = new ArrayList<>(Arrays.asList("Outreach Staff"));
    final List<String> siteTypeValues = new ArrayList<>(Arrays.asList("Agency", "Media", "Provider"));
    final String fiveHundredChars = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, " +
            "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget," +
            " arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibu";
    final ThreadLocal<String> oneHundredFiftyCharsAlphaNum = ThreadLocal.withInitial(() -> RandomStringUtils.randomAlphanumeric(150));
    final ThreadLocal<String> randomAplphaFifteenCharsOne = ThreadLocal.withInitial(() -> RandomStringUtils.randomAlphabetic(15));
    final ThreadLocal<String> randomAplphaFifteenCharsTwo = ThreadLocal.withInitial(() -> RandomStringUtils.randomAlphabetic(15));


    @When("I click on Create Outreach Session option")
    public void i_click_on_Create_Outreach_Session_option() {
        contactRecord.createOutReachSessionOption.click();
    }

    @When("I input session notes and estimated attendees")
    public void i_input_session_notes_and_estimated_attendees() {
        communityOutreach.communityOutreachSessionNotes.sendKeys("test notes");
        communityOutreach.estimatedAttendees.sendKeys("2345");
    }

    @Then("I navigate away to contact record search screen")
    public void i_navigate_away_to_contact_record_search_screen() {
        hover(dashboard.case_ConsumerSearchTab);
        click(dashboard.contactRecordSearchTab);
        waitFor(1);
    }

    @Then("I am able to see Warning pop-up message with cancel and continue buttons")
    public void i_am_able_to_see_Warning_pop_up_message_with_cancel_and_continue_buttons() {
        assertTrue(communityOutreach.warningMessage.isDisplayed());
    }

    @When("I click cancel button on warning pop-up")
    public void i_click_cancel_button_on_warning_pop_up() {
        waitFor(2);
        communityOutreach.warningMessageCancel.click();
    }

    @Then("I remain on Create Community Outreach Session page")
    public void i_remain_on_Create_Community_Outreach_Session_page() {
        waitFor(2);
        assertTrue(communityOutreach.createComunityOutreachSessionScreen.isDisplayed());
    }

    @When("I click continue button on warning pop-up")
    public void i_click_continue_button_on_warning_pop_up() {
        waitFor(2);
        communityOutreach.warningMessageContinue.click();
    }

    @Then("I am navigated to contact record search screen")
    public void i_am_navigated_to_contact_record_search_screen() {
        waitFor(2);
        assertTrue(communityOutreach.contactRecordSearchScreen.isDisplayed());
    }

    @Then("I verify all elements on Session Details screen are displayed")
    public void i_verify_all_elements_on_Session_Details_screen_are_displayed() {
        waitFor(3);
        assertTrue(communityOutreach.sessionType.isDisplayed());
        assertTrue(communityOutreach.channel.isDisplayed());
        assertTrue(communityOutreach.sessionRegion.isDisplayed());
        assertFalse(communityOutreach.yesPublicAllowed.isSelected());
        assertFalse(communityOutreach.noPublicAllowed.isSelected());
        assertTrue(communityOutreach.estimatedAttendees.isDisplayed());
        assertTrue(communityOutreach.sessionStatus.isDisplayed());
        assertTrue(communityOutreach.languages.isDisplayed());
        assertTrue(communityOutreach.communityOutreachSessionNotes.isDisplayed());
        assertTrue(communityOutreach.numberOfAttendees.isDisplayed());
        assertTrue(communityOutreach.enrollmentUpdates.isDisplayed());
        assertTrue(communityOutreach.surveysCollected.isDisplayed());
        assertTrue(communityOutreach.callCenterVerified.isDisplayed());
    }

    @Then("I verify input elements")
    public void i_verify_input_elements(List inputElements) {
        String testInput = "test123";
        String testInput2 = "test1";
        communityOutreach.estimatedAttendees.click();
        communityOutreach.estimatedAttendees.clear();

        Actions a = new Actions(Driver.getDriver());
        communityOutreach.communityOutreachSessionNotes.sendKeys(testInput);
        assertTrue(communityOutreach.communityOutreachSessionNotes.getAttribute("value").equals(testInput));
        communityOutreach.numberOfAttendees.sendKeys(testInput2);
        assertTrue(communityOutreach.numberOfAttendees.getAttribute("value").equals(testInput2));
        communityOutreach.enrollmentUpdates.sendKeys(testInput2);
        assertTrue(communityOutreach.enrollmentUpdates.getAttribute("value").equals(testInput2));
        communityOutreach.surveysCollected.sendKeys(testInput2);
        assertTrue(communityOutreach.surveysCollected.getAttribute("value").equals(testInput2));
        communityOutreach.callCenterVerified.sendKeys(testInput2);
        assertTrue(communityOutreach.callCenterVerified.getAttribute("value").equals(testInput2));
    }

    @Then("I verify required elements")
    public void i_verify_required_elements(List requiredElements) {
        communityOutreach.nextButton.click();
        communityOutreach.nextButton.click();
        communityOutreach.nextButton.click();
        communityOutreach.saveButton.click();
        communityOutreach.sessionDetailsNavigation.click();
        waitFor(2);
        assertTrue(communityOutreach.sessionTypeRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.sessionStatusRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.sessionRegionRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.channelRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.languagesRequiredErrorMessage.isDisplayed());
    }

    @When("I click on Site Information  Page from stepping header")
    public void i_click_on_Site_Information_Page_from_stepping_header() {
        communityOutreach.siteInformationPageSteppingHeader.click();
    }

    @Then("I verify all elements on Site Information screen are displayed")
    public void i_verify_all_elements_on_Site_Information_screen_are_displayed() {
        waitFor(3);
        assertTrue(communityOutreach.siteName.isDisplayed());
        assertTrue(communityOutreach.siteType.isDisplayed());
        assertTrue(communityOutreach.addressLine1.isDisplayed());
        assertTrue(communityOutreach.addressLine2.isDisplayed());
        assertFalse(communityOutreach.city.isSelected());
        assertFalse(communityOutreach.state.isSelected());
        assertTrue(communityOutreach.zip.isDisplayed());
        assertTrue(communityOutreach.county.isDisplayed());
        assertFalse(communityOutreach.accessibleCheckBox.isSelected());
        assertTrue(communityOutreach.siteNotesInput.isDisplayed());
    }

    @Then("I verify input elements for Session - Site Information Screen")
    public void i_verify_input_elements_for_Session_Site_Information_Screen(List inputelements) {
        String generatedString = RandomStringUtils.random(500, true, true);
        communityOutreach.siteNotesInput.click();
        communityOutreach.siteNotesInput.clear();
        actions.sendKeys(communityOutreach.siteNotesInput, generatedString).build().perform();
        assertTrue(communityOutreach.siteNotesInput.getAttribute("value").equalsIgnoreCase(generatedString));
        String generatedStringMore500 = RandomStringUtils.random(501, true, true);
        communityOutreach.siteNotesInput.click();
        communityOutreach.siteNotesInput.clear();
        actions.sendKeys(communityOutreach.siteNotesInput, generatedStringMore500).build().perform();
        System.out.println("length");
        assertTrue(communityOutreach.siteNotesInput.getAttribute("value").length() == 500);
    }

    @Then("I verify required elements for Session - Site Information screen")
    public void i_verify_required_elements_for_Session_Site_Information_screen() {
        communityOutreach.nextButton.click();
        communityOutreach.saveButton.click();
        communityOutreach.siteInformationPageSteppingHeader.click();
        waitFor(2);
        assertTrue(communityOutreach.siteNameRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.siteTypeRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.addressLine1RequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.cityRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.stateRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.zipRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.countyRequiredErrorMessage.isDisplayed());
    }

    @When("I verify Next button is displayed and functioning")
    public void i_verify_Next_button_is_displayed_and_functioning() {
        communityOutreach.communityOutreachSessionNotes.sendKeys("Test123");
        communityOutreach.nextButton.isDisplayed();
        communityOutreach.nextButton.click();
    }

    @When("I verify Required Information Icon is displayed")
    public void i_verify_Required_Information_Icon_is_displayed() {
        assertTrue(communityOutreach.requiredInformationIcon.isDisplayed());
    }

    @When("I verify Navigattion via Stepping Header is working")
    public void i_verify_Navigattion_via_Stepping_Header_is_working() {
        communityOutreach.sessionDetailsNavigation.click();
        assertTrue(communityOutreach.estimatedAttendees.isDisplayed());
    }

    @When("I verify data entry is retained when I navigate to next page")
    public void i_verify_data_entry_is_retained_when_I_navigate_to_next_page() {
        assertTrue(communityOutreach.communityOutreachSessionNotes.getAttribute("value").equals("Test123"));
    }

    @When("I click on Session Schedule Page from stepping header")
    public void i_click_on_Session_Schedule_Page_from_stepping_header() {
        communityOutreach.sessionSchedulePageSteppingHeader.click();
    }

    @Then("I verify all elements on Session Schedule screen are displayed")
    public void i_verify_all_elements_on_Session_Schedule_screen_are_displayed() {
        assertTrue(communityOutreach.sessionDate.isDisplayed());
        assertTrue(communityOutreach.sessionStartTime.isDisplayed());
        assertTrue(communityOutreach.sessionEndTime.isDisplayed());
        assertTrue(communityOutreach.travelTime.isDisplayed());
        assertTrue(communityOutreach.preparationTime.isDisplayed());
        assertTrue(communityOutreach.reoccurencyFrequency.isDisplayed());
        assertTrue(communityOutreach.clientRegistrationRequiredYes.isDisplayed());
        assertTrue(communityOutreach.clientRegistrationRequiredNo.isDisplayed());
        assertTrue(communityOutreach.presenter.isDisplayed());
    }

    @Then("I verify required elements on Session Schedule screen")
    public void i_verify_required_elements_on_Session_Schedule_screen() {
        communityOutreach.nextButton.click();
        communityOutreach.nextButton.click();
        communityOutreach.saveButton.click();
        communityOutreach.sessionSchedulePageSteppingHeader.click();
        assertTrue(communityOutreach.sessionDateRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.sessionStartTimeRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.sessionEndTimeRequiredErrorMessage.isDisplayed());
        assertTrue(communityOutreach.presenterRequiredErrorMessage.isDisplayed());
    }

    @And("I verify {string} option is available in the Left Hand Navigation")
    public void iVerifyOptionIsAvailableInTheLeftHandNavigation(String dataType) {
        switch (dataType) {
            case "COMMUNITY EVENT CALENDER":
                assertTrue(communityOutreach.leftHandNavEvenMgmtCalendarIcon.isDisplayed());
                break;
            case "COMMUNITY EVENT MGMT":
                hover(dashboard.caseConsumerSideBarIcon);
                waitFor(2);
                assertTrue(communityOutreach.leftHandNavCommOutreachMgmtButton.isDisplayed());
                break;
            default:
                fail("Selected option mismatch");
        }
    }

    @When("I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections")
    public void iClickOnTheCOMMUNITYEVENTMGMTLeftMenuArrowToBringUpSelections() {
        hover(dashboard.caseConsumerSideBarIcon);
        waitFor(2);
        jsClick(communityOutreach.leftHandNavCommArrowSelection);
        jsClick(dashboard.expandcommunityOutreachMGMTSideTab);
        waitFor(3);
        actions.moveToElement(dashboard.expandcommunityOutreachMGMTSideTab).click().build().perform();
        waitFor(4);
    }

    @Then("I verify the following selections are available to choose from the Community Outreach Mgmt")
    public void iVerifyTheFollowingSelectionsAreAvailableToChooseFromTheCommunityOutreachMgmt(List<String> dataTable) {
        for (String eachType : dataTable) {
            switch (eachType) {
                case "CREATE SESSION":
                    Assert.assertEquals(communityOutreach.leftHandCommunityCreateSession.getText(), eachType, "Mismatch in expected CREATE SESSION option in left hand navigation");
                    break;
                case "SEARCH SESSION":
                    Assert.assertEquals(communityOutreach.leftHandCommunitySearchSession.getText(), eachType, "Mismatch in expected SEARCH SESSION option in left hand navigation");
                    break;
            }
        }
    }

    @When("I click on Site Contact Information page from stepping header")
    public void i_click_on_Site_Contact_Information_page_from_stepping_header() {
        communityOutreach.siteContactInfoPageSteppingHeader.click();
        waitFor(2);
    }

    @Then("I verify all elements on Site Contact Information screen are displayed")
    public void i_verify_all_elements_on_Site_Contact_Information_screen_are_displayed() {
        assertTrue(communityOutreach.siteContactFirstName.isDisplayed());
        assertTrue(communityOutreach.siteContactLastName.isDisplayed());
        assertTrue(communityOutreach.siteContactEmail.isDisplayed());
        assertTrue(communityOutreach.siteContactAddressLine1.isDisplayed());
        assertTrue(communityOutreach.siteContactAddressLine2.isDisplayed());
        assertTrue(communityOutreach.siteContactCity.isDisplayed());
        assertTrue(communityOutreach.siteContactState.isDisplayed());
        assertTrue(communityOutreach.siteContactZip.isDisplayed());
        assertTrue(communityOutreach.siteContactWorkPhone.isDisplayed());
        assertTrue(communityOutreach.siteContactCellPhone.isDisplayed());
    }

    @When("I input all required information on Session Details Screen")
    public void i_input_all_required_information_on_Session_Details_Screen() {
        communityOutreach.sessionType.sendKeys("Health Fair");
        selectDropDown(communityOutreach.sessionType, "Health Fair");
        communityOutreach.sessionStatus.sendKeys("Requested");
        selectDropDown(communityOutreach.sessionStatus, "Requested");
        communityOutreach.nextButton.click();
    }

    @When("I input all required information on Session Schedule Screen")
    public void i_input_all_required_information_on_Session_Schedule_Screen() {
        waitFor(2);
        communityOutreach.sessionDateCalendar.click();
        communityOutreach.calendarOKButton.click();
        communityOutreach.sessionStartTimeCalendar.click();
        communityOutreach.calendarOKButton.click();
        communityOutreach.sessionEndTimeCalendar.click();
        communityOutreach.calendarOKButton.click();
        communityOutreach.presenter.sendKeys("John");
        selectDropDown(communityOutreach.presenter, "John");
        communityOutreach.nextButton.click();
    }

    @When("I input all required information on Site Information Screen")
    public void i_input_all_required_information_on_Site_Information_Screen() {
        communityOutreach.siteName.sendKeys("Site Name Test");
        communityOutreach.siteType.sendKeys("Provider");
        selectDropDown(communityOutreach.siteType, "Provider");
        communityOutreach.addressLine1.sendKeys("Site Name Test");
        communityOutreach.city.sendKeys("Chicago");
        communityOutreach.state.sendKeys("IL");
        communityOutreach.zip.sendKeys("353253");
        communityOutreach.county.sendKeys("353253");
        communityOutreach.nextButton.click();
    }

    @When("I input all required information on Site Contact Info Screen")
    public void i_input_all_required_information_on_Site_Contact_Info_Screen() {
        communityOutreach.contactAddressLine1.sendKeys("123 Address");
        assertTrue(communityOutreach.firstNameRequiredErrorMessage.isDisplayed());
        communityOutreach.siteContactFirstName.sendKeys("Test First Name");
    }

    @When("I verify I am able to save community outreach sessions")
    public void i_verify_I_am_able_to_save_community_outreach_sessions() {
        communityOutreach.saveButton.click();
        waitFor(3);
        assertTrue(communityOutreach.successSaveMessage.isDisplayed());
    }

    @And("I click on Search Session from left hand side")
    public void iClickOnSearchSessionFromLeftHandSide() {
        communityOutreach.leftHandCommunitySearchSession.click();
        waitFor(3);
        hover(contactRecord.initContact);
        waitFor(2);
    }

    @When("I verify all elements are displayed for community outreach search screen")
    public void i_verify_all_elements_are_displayed_for_community_outreach_search_screen() {
        assertTrue(communityOutreach.searchButton.isDisplayed());
        assertTrue(communityOutreach.cancelButton.isDisplayed());
        assertTrue(communityOutreach.sessionID.isDisplayed());
        assertTrue(communityOutreach.sessionType.isDisplayed());
        assertTrue(communityOutreach.siteName.isDisplayed());
        assertTrue(communityOutreach.reoccurencyFrequencySearch.isDisplayed());
        assertTrue(communityOutreach.presenerName.isDisplayed());
        assertTrue(communityOutreach.sessionRegion.isDisplayed());
        assertTrue(communityOutreach.sessionStatus.isDisplayed());
        assertTrue(communityOutreach.zip.isDisplayed());
        assertTrue(communityOutreach.county.isDisplayed());
    }

    @When("I verify all displayed fields for View Screen")
    public void i_verify_all_displayed_fields_for_View_Screen() {
        assertTrue(communityOutreach.sessionId.isDisplayed());
        assertTrue(communityOutreach.createdByLabel.isDisplayed());
        assertTrue(communityOutreach.createdByValue.isDisplayed());
        assertTrue(communityOutreach.createdDateLabel.isDisplayed());
        assertTrue(communityOutreach.editedByLabel.isDisplayed());
        assertTrue(communityOutreach.editedDateLabel.isDisplayed());
        assertTrue(communityOutreach.reasonForEditLabel.isDisplayed());
        assertTrue(communityOutreach.editSessionButton.isDisplayed());
        assertTrue(communityOutreach.sessionTypeValue.isDisplayed());
        assertTrue(communityOutreach.sessionStatusValue.isDisplayed());
        assertTrue(communityOutreach.sessionDateValue.isDisplayed());
        assertTrue(communityOutreach.sessionStartTimeValue.isDisplayed());
        assertTrue(communityOutreach.sessionEndTimeValue.isDisplayed());
        assertTrue(communityOutreach.presenterValue.isDisplayed());
        assertTrue(communityOutreach.siteNameValue.isDisplayed());
        assertTrue(communityOutreach.siteInfoAddressLineValue.isDisplayed());
        assertTrue(communityOutreach.siteInfoCityValue.isDisplayed());
        assertTrue(communityOutreach.siteInfoStateValue.isDisplayed());
        assertTrue(communityOutreach.siteInfoZipValue.isDisplayed());
        assertTrue(communityOutreach.siteInfoCountyValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoFirstNameValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoLastNameValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoEmailValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoAddressLine1Value.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoAddressLine2Value.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoWorkPhoneValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoFaxPhoneValue.isDisplayed());
        assertTrue(communityOutreach.siteContactInfoCellPhoneValue.isDisplayed());
    }

    @And("I search on Community Outreach page by the following field")
    public void iSearchOnCommunityOutreachPageByTheFollowingField(List<Map<String, String>> data) {
        Map<String, String> keysToOverride = data.get(0);
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "SESSION ID":
                    if ("API AUTO CREATED".equals(keysToOverride.get(key))) {
                        selectSearchfield(communitySearchPage.sessionIdSearchField, APICoreCommunityOutreachController.communityId.get());
                    } else {
                        selectSearchfield(communitySearchPage.sessionIdSearchField, keysToOverride.get(key));
                    }
                    break;
                case "SESSION TYPE":
                    selectDropDown(communitySearchPage.sessionTypeSearchField, keysToOverride.get(key));
                    break;
                case "SITE NAME":
                    if ("API AUTO CREATED".equals(keysToOverride.get(key))) {
                        selectSearchfield(communitySearchPage.siteNameSearchField, APICoreCommunityOutreachController.siteName.get());
                    } else {
                        selectSearchfield(communitySearchPage.siteNameSearchField, keysToOverride.get(key));
                    }
                    break;
                case "RECCURRENCE FREQUENCY":
                    selectDropDown(communitySearchPage.recurrenceFreqSearchField, keysToOverride.get(key));
                    break;
                case "PRESENTER NAME":
                    selectDropDown(communitySearchPage.presenterNameSearchField, keysToOverride.get(key));
                    break;
                case "SESSION REGION":
                    selectDropDown(communitySearchPage.sessionRegionSearchField, keysToOverride.get(key));
                    break;
                case "SESSION STATUS":
                    selectDropDown(communitySearchPage.sessionStatusSearchField, keysToOverride.get(key));
                    break;
                case "ZIP":
                    selectDropDown(communitySearchPage.zipSearchField, keysToOverride.get(key));
                    break;
                case "COUNTY":
                    selectDropDown(communitySearchPage.countySearchField, keysToOverride.get(key));
                    break;
                default:
                    fail("Mismatch in provided key");
            }
            waitFor(1);
            communitySearchPage.searchButton.click();
        }
    }

    public void selectSearchfield(WebElement field, String data) {
        waitFor(1);
        field.click();
        waitFor(1);
        field.sendKeys(data);
    }

    @Then("I verify the Community Outreach Search results details by the following fields and values")
    public void iVerifyTheCommunityOutreachSearchResultsDetailsByTheFollowingFieldsAndValues(List<Map<String, String>> data) {
        Map<String, String> keysToCheck = data.get(0);
        for (String key : keysToCheck.keySet()) {
            switch (key) {
                case "SESSION ID":
                    if ("API CREATED".equals(keysToCheck.get(key))) {
                        assertEquals(communitySearchPage.searchResultRowOne.get(1).getText(), APICoreCommunityOutreachController.communityId);
                    }
                    break;
                case "SESSION TYPE":
                    assertEquals(communitySearchPage.searchResultRowOne.get(2).getText(), keysToCheck.get(key));
                    break;
                case "SESSION STATUS":
                    assertEquals(communitySearchPage.searchResultRowOne.get(3).getText(), keysToCheck.get(key));
                    break;
                case "SESSION SCHEDULED DATE":
                    assertEquals(communitySearchPage.searchResultRowOne.get(4).getText(), getGreaterDateFormatMMddyyyy(1));
                    break;
                case "SESSION REGION":
                    assertEquals(communitySearchPage.searchResultRowOne.get(5).getText(), keysToCheck.get(key));
                    break;
                case "SITE NAME":
                    assertEquals(communitySearchPage.searchResultRowOne.get(6).getText(), APICoreCommunityOutreachController.siteName);
                    break;
                case "RECURRENCE FREQUENCY":
                    assertEquals(communitySearchPage.searchResultRowOne.get(7).getText(), keysToCheck.get(key));
                    break;
                case "ZIP":
                    assertEquals(communitySearchPage.searchResultRowOne.get(8).getText(), keysToCheck.get(key));
                    break;
                case "COUNTY":
                    assertEquals(communitySearchPage.searchResultRowOne.get(9).getText(), keysToCheck.get(key));
                    break;
                case "PRESENTER NAME":
                    assertEquals(communitySearchPage.searchResultRowOne.get(10).getText(), keysToCheck.get(key));
                    break;
            }
        }
    }


    @Then("I verify the page header and following field labels on the community outreach search page")
    public void iVerifyThePageHeaderAndFollowingFieldLabelsOnTheCommunityOutreachSearchPage(List<String> data) {
        waitFor(5);
        for (String each : data) {
            switch (each) {
                case "C.O. SEARCH HEADER":
                    assertTrue(communitySearchPage.communitySearchPageHeader.getText().contains("SEARCH COMMUNITY OUTREACH SESSION"), "SEARCH COMMUNITY OUTREACH SESSION Header incorrectly displayed");
                    break;
                case "SESSION ID":
                    assertEquals(communitySearchPage.sessionIdSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "SESSION TYPE":
                    assertEquals(communitySearchPage.sessionTypeSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "SITE NAME":
                    assertEquals(communitySearchPage.siteNameSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "RECURRENCE FREQUENCY":
                    assertEquals(communitySearchPage.recurrenceFreqSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "PRESENTER NAME":
                    assertEquals(communitySearchPage.presenterNameSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "SESSION REGION":
                    assertEquals(communitySearchPage.sessionRegionSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "SESSION STATUS":
                    assertEquals(communitySearchPage.sessionStatusSearchlabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "ZIP":
                    assertEquals(communitySearchPage.zipSearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
                case "COUNTY":
                    assertEquals(communitySearchPage.countySearchLabel.getText(), each, each + " Label incorrectly displayed");
                    break;
            }
        }
    }

    @Then("I verify the search result headers for Community Outreach search page")
    public void iVerifyTheSearchResultHeadersForCommunityOutreachSearchPage(List<String> data) {
        waitFor(5);
        for (String each : data) {
            switch (each) {
                case "SEARCH RESULT(S)":
                    assertTrue(communitySearchPage.communitySearchResultHeader.getText().contains("SEARCH RESULT(S)"), "SEARCH RESULT(S) Header incorrectly displayed");
                    break;
                case "SESSION ID":
                    assertEquals(communitySearchPage.searchResultHeaders.get(1).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "SESSION SCHEDULE DATE":
                    assertEquals(communitySearchPage.searchResultHeaders.get(4).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "SESSION TYPE":
                    assertEquals(communitySearchPage.searchResultHeaders.get(2).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "SITE NAME":
                    assertEquals(communitySearchPage.searchResultHeaders.get(6).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "RECURRENCE FREQUENCY":
                    assertEquals(communitySearchPage.searchResultHeaders.get(7).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "PRESENTER NAME":
                    assertEquals(communitySearchPage.searchResultHeaders.get(10).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "SESSION REGION":
                    assertEquals(communitySearchPage.searchResultHeaders.get(5).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "SESSION STATUS":
                    assertEquals(communitySearchPage.searchResultHeaders.get(3).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "ZIP":
                    assertEquals(communitySearchPage.searchResultHeaders.get(8).getText(), each, each + " Search header incorrectly displayed");
                    break;
                case "COUNTY":
                    assertEquals(communitySearchPage.searchResultHeaders.get(9).getText(), each, each + " Search header incorrectly displayed");
                    break;
            }
        }
    }

    @Then("I verify Community Outreach search results are sorted by Session Id in descending order and displayed by five results with the option to use pagination")
    public void iVerifyCommunityOutreachSearchResultsAreSortedBySessionIdInDescendingOrderAndDisplayedByFiveResultsWithTheOptionToUsePagination() {
        selectDropDown(communitySearchPage.sessionTypeSearchField, "Health Fair");
        waitFor(1);
        communitySearchPage.searchButton.click();
        waitForVisibility(communitySearchPage.communitySearchResultHeader, 10);
        assertEquals(communitySearchPage.itemsPerPage.getText(), "show 5", "Incorrect option found for show 5 result option");
        assertEquals(communitySearchPage.searchResultpaginationList.get(0).getText(), "1", "Mismatch for page 1 for search result pagination");
        assertEquals(communitySearchPage.searchResultpaginationList.get(0).getText(), "2", "Mismatch for page 1 for search result pagination");
        assertEquals(communitySearchPage.searchResultpaginationList.get(0).getText(), "3", "Mismatch for page 1 for search result pagination");
        for (int i = 0; i < 3; i++) {
            assertTrue(Integer.parseInt(communitySearchPage.sessionIDColumn.get(i).getText()) < Integer.parseInt(communitySearchPage.sessionIDColumn.get(i + 1).getText()), "Session Id is not in descending order");
        }
    }

    @And("I click on the {string} from the Community search result")
    public void iClickOnTheFromTheCommunitySearchResult(String type) {
        switch (type) {
            case "API CREATED SESSION ID":
                WebElement sessionIdByAPI = Driver.getDriver().findElement(By.xpath("//td[.='" + APICoreCommunityOutreachController.communityId + "']"));
                actions.moveToElement(sessionIdByAPI).click(sessionIdByAPI).build().perform();
                break;
            default:
                WebElement data = Driver.getDriver().findElement(By.xpath("//td[.='" + type + "']"));
                actions.moveToElement(data).click(data).build().perform();
        }
    }

    @And("I verify that clicking on Search result Session Id navigates me to the Community Outreach page")
    public void iVerifyThatClickingOnSearchResultSessionIdNavigatesMeToTheCommunityOutreachPage() {
        waitForVisibility(detailsEditPage.communityOutreachDeatilsPageHeader, 10);
        assertTrue(detailsEditPage.communityOutreachDeatilsPageHeader.isDisplayed(), "Did not find COMMUNITY OUTREACH SESSION DETAILS header");
        assertTrue(detailsEditPage.sessionIdHeader.getText().contains(APICoreCommunityOutreachController.communityId.get()), "Mismatch in Session Id from navigated from search selection");
    }

    @And("I click on the {string} in Community Outreach functionality")
    public void iClickOnTheInCommunityOutreachFunctionality(String type) {
        waitFor(1);
        switch (type) {
            case "API CREATED SESSION ID":
                WebElement sessionIdByAPI = Driver.getDriver().findElement(By.xpath("//td[.='" + APICoreCommunityOutreachController.communityId + "']"));
                actions.moveToElement(sessionIdByAPI).click(sessionIdByAPI).build().perform();
                break;
            case "FIRST RESULT FROM SEARCH":
                communitySearchPage.searchResultRowOne.get(1).click();
                break;
            case "EDIT BUTTON":
                waitForVisibility(detailsEditPage.editSessionButton, 10);
                detailsEditPage.editSessionButton.click();
                break;
            case "SAVE BUTTON":
                scrollDownUsingActions(3);
                detailsEditPage.saveButton.click();
                break;
            case "CALENDAR VIEW BUTTON":
                scrollDownUsingActions(3);
                communityOutreach.calendarViewButton.click();
                break;
            default:
                fail("Mismatch in type with case");
        }
        waitFor(3);
    }

    @Then("I verify following {string} labels in the edit community outreach session page")
    public void iVerifyFollowingLabelsInTheEditCommunityOutreachSessionPage(String type, List<String> data) {
        switch (type) {
            case "SESSION DETAILS":
                waitFor(2);
                List<String> actualSessionDetailsLabelList = new ArrayList<>();
                List<String> expectedSessionDetailsLabelList = new ArrayList<>(data);
                detailsEditPage.sessionDetailsLabelsList.forEach(each -> actualSessionDetailsLabelList.add(each.getText().replace("*", "").trim()));
                System.out.println("actualSessionDetailsLabelList = " + actualSessionDetailsLabelList);
                System.out.println("expectedSessionDetailsLabelList = " + expectedSessionDetailsLabelList);
                assertEquals(actualSessionDetailsLabelList.size(), expectedSessionDetailsLabelList.size());
                assertThroughListContains(actualSessionDetailsLabelList, expectedSessionDetailsLabelList);
                break;
            case "POST SESSION FOLLOW UP":
                scrollDownWithArrow(10);
                waitFor(2);
                List<String> actualPostSessionLabelList = new ArrayList<>();
                List<String> expectedPostSessionLabelList = new ArrayList<>(data);
                detailsEditPage.postSessionsFollowUpLabelsList.forEach(each -> actualPostSessionLabelList.add(each.getText().replace("*", "").trim()));
                System.out.println("actualPostSessionLabelList = " + actualPostSessionLabelList);
                System.out.println("expectedPostSessionLabelList = " + expectedPostSessionLabelList);
                assertEquals(actualPostSessionLabelList.size(), expectedPostSessionLabelList.size());
                assertThroughListContains(actualPostSessionLabelList, expectedPostSessionLabelList);
                break;
            case "SESSION SCHEDULE":
                scrollDownWithArrow(10);
                waitFor(1);
                List<String> actualSessionScheduleLabelList = new ArrayList<>();
                List<String> expectedSessionScheduleLabelList = new ArrayList<>(data);
                detailsEditPage.sessionScheduleLabelsList.forEach(each -> actualSessionScheduleLabelList.add(each.getText().replace("*", "").trim()));
                System.out.println("actualSessionScheduleLabelList = " + actualSessionScheduleLabelList);
                System.out.println("expectedSessionScheduleLabelList = " + expectedSessionScheduleLabelList);
                assertEquals(actualSessionScheduleLabelList.size(), expectedSessionScheduleLabelList.size());
                assertThroughListContains(actualSessionScheduleLabelList, expectedSessionScheduleLabelList);
                break;
            case "SITE INFORMATION":
                scrollDownWithArrow(10);
                waitFor(1);
                List<String> actualSiteInfoLabelList = new ArrayList<>();
                List<String> expectedSiteInfoLabelList = new ArrayList<>(data);
                detailsEditPage.siteInformationLabelsList.forEach(each -> actualSiteInfoLabelList.add(each.getText().replace("*", "").trim()));
                System.out.println("actualSiteInfoLabelList = " + actualSiteInfoLabelList);
                System.out.println("expectedSiteInfoLabelList = " + expectedSiteInfoLabelList);
                assertEquals(actualSiteInfoLabelList.size(), expectedSiteInfoLabelList.size());
                assertThroughListContains(actualSiteInfoLabelList, expectedSiteInfoLabelList);
                break;
            case "SITE CONTACT INFORMATION":
                scrollDownWithArrow(10);
                waitFor(1);
                List<String> actualSiteContactInfoLabelList = new ArrayList<>();
                List<String> expectedSiteContactInfoLabelList = new ArrayList<>(data);
                detailsEditPage.siteContactInfoLabelsList.forEach(each -> actualSiteContactInfoLabelList.add(each.getText().replace("*", "").trim()));
                System.out.println("actualSiteContactInfoLabelList = " + actualSiteContactInfoLabelList);
                System.out.println("expectedSiteContactInfoLabelList = " + expectedSiteContactInfoLabelList);
                assertEquals(actualSiteContactInfoLabelList.size(), expectedSiteContactInfoLabelList.size());
                assertThroughListContains(actualSiteContactInfoLabelList, expectedSiteContactInfoLabelList);
                break;
            default:
                fail("Mismatch in Case");
        }
    }

    private void assertThroughListContains(List<String> actual, List<String> expected) {
        for (int i = 0; i < expected.size(); i++) {
            assertTrue(actual.get(i).contains(expected.get(i)), "Actual Label: " + actual.get(i) + "\nExpected: " + expected.get(i));
        }
    }

    private List<String> dropdownTextList() {
        List<WebElement> dropdownWebelementsListList = new ArrayList<>(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")));
        List<String> dropdownTextList = new ArrayList<>();
        dropdownWebelementsListList.forEach(eachElement -> dropdownTextList.add(eachElement.getText()));
        System.out.println("Actual dropdownTextList = " + dropdownTextList);
        return dropdownTextList;
    }

    private void clickOutOfDropdown() {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(1);
    }

    @Then("I verify the following {string} field formats for the Edit Community Outreach session page")
    public void iVerifyTheFollowingFieldFormatsForTheEditCommunityOutreachSessionPage(String type, List<String> data) {
        switch (type) {
            case "SESSION DETAILS":
                scrollToTop();
                waitFor(2);
                List<String> expectedSessionDetailsList = new ArrayList<>(data);
                for (String each : expectedSessionDetailsList) {
                    switch (each) {
                        case "REASON FOR EDIT":
                            detailsEditPage.reasonForEdit.click();
                            assertEquals(reasonForEditValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "SESSION TYPE":
                            detailsEditPage.sessionType.click();
                            assertEquals(sessionTypeValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "CHANNEL":
                            detailsEditPage.channel.click();
                            assertEquals(channelValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "ESTIMATED ATTENDEES":
                            detailsEditPage.estimateAttendees.click();
                            assertEquals(estimatedAttendeesValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "SESSION REGION":
                            detailsEditPage.sessionRegion.click();
                            System.out.println("sessionRegionValues = " + sessionRegionValues);
                            assertEquals(sessionRegionValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "SESSION STATUS":
                            detailsEditPage.sessionStatus.click();
                            assertEquals(sessionStatusValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "LANGUAGES":
                            detailsEditPage.languages.click();
                            System.out.println("languagesValues = " + languagesValues);
                            assertEquals(languagesValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "SESSION NOTES":
                            detailsEditPage.sessionNotes.click();
                            detailsEditPage.sessionNotes.sendKeys(fiveHundredChars + "test");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.sessionNotes.getAttribute("value"), fiveHundredChars, "Found more then 500 characters for Session Notes field");
                            break;
                    }
                }
                break;
            case "POST SESSION FOLLOW UP":
                scrollToTop();
                scrollDownWithArrow(10);
                waitFor(1);
                List<String> expectedPostSessionLabelList = new ArrayList<>(data);
                for (String each : expectedPostSessionLabelList) {
                    switch (each) {
                        case "NUMBER OF ATTENDEES":
                            detailsEditPage.numOfAttendees.click();
                            detailsEditPage.numOfAttendees.sendKeys("abc123456");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.numOfAttendees.getAttribute("value"), "12345", "Mismatch in expected field format for " + each);
                            break;
                        case "ENROLLMENT UPDATES":
                            detailsEditPage.enrollmentUpdates.click();
                            detailsEditPage.enrollmentUpdates.sendKeys("abc123456");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.enrollmentUpdates.getAttribute("value"), "12345", "Mismatch in expected field format for " + each);
                            break;
                        case "SURVEYS COLLECTED":
                            detailsEditPage.numOfSurveysCollected.click();
                            detailsEditPage.numOfSurveysCollected.sendKeys("abc123456");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.numOfSurveysCollected.getAttribute("value"), "12345", "Mismatch in expected field format for " + each);
                            break;
                        case "CALL CENTER VERIFIED":
                            detailsEditPage.numOfCallVerified.click();
                            detailsEditPage.numOfCallVerified.sendKeys("abc123456");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.numOfCallVerified.getAttribute("value"), "12345", "Mismatch in expected field format for " + each);
                            break;
                    }
                }
                break;
            case "SESSION SCHEDULE":
                scrollToTop();
                scrollDownWithArrow(30);
                waitFor(1);
                List<String> expectedSessionScheduleLabelList = new ArrayList<>(data);
                for (String each : expectedSessionScheduleLabelList) {
                    switch (each) {
                        case "SESSION DATE":
                            actions.moveToElement(detailsEditPage.sessionDatePicker).click(detailsEditPage.sessionDatePicker).build().perform();
                            waitFor(2);
                            assertTrue(detailsEditPage.dateTimePicker.isDisplayed());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(2);
                            break;
                        case "SESSION START TIME":
                            actions.moveToElement(detailsEditPage.sessionStartTimePicker).click(detailsEditPage.sessionStartTimePicker).build().perform();
                            waitFor(2);
                            assertTrue(detailsEditPage.dateTimePicker.isDisplayed());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(2);
                            break;
                        case "SESSION END TIME":
                            actions.moveToElement(detailsEditPage.sessionEndTimePicker).click(detailsEditPage.sessionEndTimePicker).build().perform();
                            waitFor(2);
                            assertTrue(detailsEditPage.dateTimePicker.isDisplayed());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(2);
                            break;
                        case "TRAVEL TIME":
                            actions.moveToElement(detailsEditPage.travelTimePicker).click(detailsEditPage.travelTimePicker).build().perform();
                            waitFor(2);
                            assertTrue(detailsEditPage.dateTimePicker.isDisplayed());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(2);
                            break;
                        case "PREPARATION TIME":
                            actions.moveToElement(detailsEditPage.preparationTimePicker).click(detailsEditPage.preparationTimePicker).build().perform();
                            waitFor(2);
                            assertTrue(detailsEditPage.dateTimePicker.isDisplayed());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(2);
                            break;
                        case "RECURRENCE FREQUENCY":
                            detailsEditPage.recurrenceFrequency.click();
                            assertEquals(recurrenceFrequencyValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "PRESENTER":
                            detailsEditPage.presenter.click();
                            assertEquals(presenterValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "DCEB PRESENTER":
                            detailsEditPage.presenter.click();
                            assertEquals(presenterValuesDCEB, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                    }
                }
                break;
            case "SITE INFORMATION":
                scrollToTop();
                scrollDownUsingActions(1);
                waitFor(1);
                List<String> expectedSiteInfoLabelList = new ArrayList<>(data);
                for (String each : expectedSiteInfoLabelList) {
                    switch (each) {
                        case "SITE NAME":
                            clearAndFillText(detailsEditPage.siteName, oneHundredFiftyCharsAlphaNum.get() + "123456");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.siteName.getAttribute("value"), oneHundredFiftyCharsAlphaNum.get(), "Found more then 150 characters for Session Notes field");
                            break;
                        case "SITE TYPE":
                            detailsEditPage.siteType.click();
                            assertEquals(siteTypeValues, dropdownTextList(), "Mismatch in " + each);
                            clickOutOfDropdown();
                            break;
                        case "ADDRESS LINE 1":
                            assertTrue(detailsEditPage.siteAddressLine1.isDisplayed());
                            break;
                        case "ADDRESS LINE 2":
                            assertTrue(detailsEditPage.siteAddressLine2.isDisplayed());
                            break;
                        case "CITY":
                            assertTrue(detailsEditPage.siteCity.isDisplayed());
                            break;
                        case "STATE":
                            assertTrue(detailsEditPage.siteState.isDisplayed());
                            break;
                        case "ZIP":
                            assertTrue(detailsEditPage.siteZip.isDisplayed());
                            break;
                        case "COUNTY":
                            assertTrue(detailsEditPage.siteCounty.isDisplayed());
                            break;
                        case "ACCESSIBLE":
                            assertEquals(detailsEditPage.accessibleIndCheckbox.getAttribute("type"), "checkbox");
                            break;
                        case "SITE NOTES":
                            detailsEditPage.siteNotes.click();
                            detailsEditPage.siteNotes.sendKeys(fiveHundredChars + "test");
                            clickOutOfDropdown();
                            assertEquals(detailsEditPage.siteNotes.getAttribute("value"), fiveHundredChars, "Found more then 500 characters for Session Notes field");
                            break;
                    }
                }
                break;
            case "SITE CONTACT INFORMATION":
                scrollDownUsingActions(2);
                waitFor(1);
                List<String> expectedSiteContactInfoLabelList = new ArrayList<>(data);
                // | FIRST NAME | LAST NAME | EMAIL | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP | WORK PHONE | FAX PHONE | CELL PHONE |
                for (String each : expectedSiteContactInfoLabelList) {
                    switch (each) {
                        case "FIRST NAME":
                            clearAndFillText(detailsEditPage.contactFirstName, randomAplphaFifteenCharsOne.get() + "12345" + randomAplphaFifteenCharsTwo.get());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(1);
                            assertEquals(detailsEditPage.contactFirstName.getAttribute("value"), randomAplphaFifteenCharsOne.get() + randomAplphaFifteenCharsTwo.get());
                            break;
                        case "LAST NAME":
                            clearAndFillText(detailsEditPage.contactLastName, randomAplphaFifteenCharsOne.get() + "12345" + randomAplphaFifteenCharsTwo.get());
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(1);
                            assertEquals(detailsEditPage.contactLastName.getAttribute("value"), randomAplphaFifteenCharsOne.get() + randomAplphaFifteenCharsTwo.get());
                            break;
                        case "EMAIL":
                            detailsEditPage.contactEmail.click();
                            detailsEditPage.contactEmail.sendKeys("abcd");
                            actions.sendKeys(Keys.ESCAPE).build().perform();
                            waitFor(1);
                            assertTrue(detailsEditPage.contactEmailIncorrectMsg.isDisplayed());
                            break;
                        case "ADDRESS LINE 1":
                            assertTrue(detailsEditPage.contactAddressLine1.isDisplayed());
                            break;
                        case "ADDRESS LINE 2":
                            assertTrue(detailsEditPage.contactAddressLine2.isDisplayed());
                            break;
                        case "CITY":
                            assertTrue(detailsEditPage.contactCity.isDisplayed());
                            break;
                        case "STATE":
                            assertTrue(detailsEditPage.contactState.isDisplayed());
                            break;
                        case "ZIP":
                            assertTrue(detailsEditPage.contactZip.isDisplayed());
                            break;
                        case "WORK PHONE":
                            assertTrue(detailsEditPage.contactWorkPhone.isDisplayed());
                            break;
                        case "FAX PHONE":
                            assertTrue(detailsEditPage.contactFaxPhone.isDisplayed());
                            break;
                        case "CELL PHONE":
                            assertTrue(detailsEditPage.cellPhone.isDisplayed());
                            break;
                    }
                }
                break;
            default:
                fail("Mismatch in Case");
        }
    }

    @And("I enter the following field value for {string} in Community Outreach edit page")
    public void iEnterTheFollowingFieldValueForInCommunityOutreachEditPage(String type, List<Map<String, String>> data) {
        Map<String, String> inputData = data.get(0);
        switch (type) {
            case "SESSION DETAILS":
                for (String each : inputData.keySet()) {
                    switch (each) {
                        case "ESTIMATED ATTENDEES":
                            selectDropDown(detailsEditPage.estimateAttendees, inputData.get(each));
                            break;
                        case "REASON FOR EDIT":
                            selectFromMultiSelectDropDownForWithEscapedKey(detailsEditPage.reasonForEdit, inputData.get(each));
                            break;
                    }
                }
                break;
            case "POST SESSION FOLLOW UP":

                break;
        }
    }

    @Then("I verify the Navigate away warning functionality in Community Outreach Edit page")
    public void iVerifyTheNavigateAwayWarningFunctionalityInCommunityOutreachEditPage() {
        detailsEditPage.backspaceArrow.click();
        waitFor(2);
        assertTrue(detailsEditPage.warningMessageContinue.isDisplayed());
        detailsEditPage.warningMessageCancel.click();
        waitFor(2);
        assertTrue(detailsEditPage.communityOutreachDeatilsPageHeader.isDisplayed());
        waitFor(2);
        detailsEditPage.backspaceArrow.click();
        waitFor(2);
        detailsEditPage.warningMessageContinue.click();
        waitFor(3);
        assertTrue(communitySearchPage.communitySearchPageHeader.isDisplayed());
    }

    @Then("I verify the following field values in the community outreach session details page")
    public void iVerifyTheFollowingFieldValuesInTheCommunityOutreachSessionDetailsPage(List<String> data) {
        waitFor(2);
        scrollToTop();
        waitFor(2);
        for (String each : data) {
            switch (each) {
                case "EDITED BY":
                    assertEquals(detailsEditPage.detailsPageEditedByValue.getText(), "Service AccountOne");
                    break;
                case "EDITED DATE":
                    assertEquals(detailsEditPage.detailsPageEditedDateValue.getText(), getCurrentDate());
                    break;
                case "REASON FOR EDIT":
                    assertEquals(detailsEditPage.detailsPageReasonForEditValue.getText(), "Updating Session Details");
                    break;
            }
        }
    }

    private void clearFieldValues(WebElement element) {
        waitFor(1);
        element.click();
        waitFor(1);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        waitFor(1);
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    @And("I clear all values for fields that are required to save in Community Outreach Edit")
    public void iClearAllValuesForFieldsThatAreRequiredToSaveInCommunityOutreachEdit() {
        List<WebElement> requiredList = new ArrayList<>();
        requiredList.add(detailsEditPage.sessionType);
        requiredList.add(detailsEditPage.channel);
        requiredList.add(detailsEditPage.sessionRegion);
        requiredList.add(detailsEditPage.sessionStatus);
        requiredList.add(detailsEditPage.languages);
        requiredList.add(detailsEditPage.sessionDate);
        requiredList.add(detailsEditPage.sessionStartTime);
        requiredList.add(detailsEditPage.sessionEndTime);
        requiredList.add(detailsEditPage.recurrenceFrequency);
        requiredList.add(detailsEditPage.presenter);
        requiredList.add(detailsEditPage.siteName);
        requiredList.add(detailsEditPage.siteType);
        requiredList.add(detailsEditPage.siteAddressLine1);
        requiredList.add(detailsEditPage.siteAddressLine2);
        requiredList.add(detailsEditPage.siteCity);
        requiredList.add(detailsEditPage.siteState);
        requiredList.add(detailsEditPage.siteZip);
        requiredList.add(detailsEditPage.siteCounty);
        for (WebElement webElement : requiredList) {
            if (webElement.equals(detailsEditPage.sessionDate)) {
                scrollDownUsingActions(1);
            }
            clearFieldValues(webElement);
        }
        scrollDownUsingActions(2);
        detailsEditPage.saveButton.click();
        waitFor(2);
    }

    @And("I verify the error message has populated for required information error")
    public void iVerifyTheErrorMessageHasPopulatedForRequiredInformationError() {
        List<String> expectedList = new ArrayList<>(Arrays.asList("REASON FOR EDIT", "SESSION TYPE", "CHANNEL", "SESSION REGION", "SESSION STATUS", "LANGUAGES",
                "SESSION DATE", "SESSION START TIME", "SESSION END TIME", "PRESENTER", "SITE NAME", "SITE TYPE", "ADDRESS LINE 1", "CITY", "STATE", "ZIP", "COUNTY"));
        List<String> actualList = new ArrayList<>();
        waitFor(3);
        List<WebElement> elementList = Driver.getDriver().findElements(By.xpath("//p[contains(text(),'is required')]"));
        elementList.forEach(each -> actualList.add(each.getText().replaceAll("\"", "")));
        Collections.sort(expectedList);
        Collections.sort(actualList);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i) + " is required and cannot be left blank", actualList.get(i));
        }
    }

    @And("I navigate to {string} tab in create Community Outreach")
    public void iNavigateToTabInCreateCommunityOutreach(String type) {
        waitFor(5);
        switch (type) {
            case "SESSION DETAILS":
                communityOutreach.sessionDetailsNavigateTabButton.click();
                break;
            case "SESSION SCHEDULE":
                communityOutreach.sessionScheduleNavigateTabButton.click();
                break;
            case "SITE INFORMATION":
                communityOutreach.siteInfoNavigateTabButton.click();
                break;
            case "SITE CONTACT INFO":
                communityOutreach.siteContactInfoNavigateTabbutton.click();
                break;
        }
        waitFor(2);
    }

    @And("I verify {string} field {string} displayed")
    public void iVerifyFieldDisplayed(String field, String condition) {
        switch (condition) {
            case "is":
                switch (field) {
                    case "WARD":
                        assertTrue(communityOutreach.wardDropdown.isDisplayed());
                        break;
                    default:
                        fail("Mismatch in case");
                }
                break;
            case "is not":
                switch (field) {
                    case "COUNTY":
                        assertFalse(quickIsDisplayed(communityOutreach.county), "County Field is displayed but should not be displayed");
                        break;
                    default:
                        fail("Mismatch in case");
                }
                break;
            default:
                fail("Mismatch in case");
        }
    }

    @Override
    public boolean quickIsDisplayed(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (element.isDisplayed()) return true;
        } catch (Exception exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return false;
        }
        return false;
    }

    @And("I verify the {string} dropdown in Community Outreach contains the following values")
    public void iVerifyTheDropdownInCommunityOutreachContainsTheFollowingValues(String type, List<List<String>> data) {
        List<String> expectedList = new ArrayList<>(data.get(0));
        List<String> actualList = new ArrayList<>();
        switch (type) {
            case "WARD":
                actions.moveToElement(communityOutreach.wardDropdown).click(communityOutreach.wardDropdown).build().perform();
                waitFor(2);
                communityOutreach.wardValuesList.forEach(each -> actualList.add(each.getText()));
                break;
            case "DCEB SITE TYPE":
                actions.moveToElement(communityOutreach.siteType).click(communityOutreach.siteType).build().perform();
                waitFor(2);
                communityOutreach.siteTypeValuesList.forEach(each -> actualList.add(each.getText()));
                break;
            case "DCEB SESSION REGION":
                actions.moveToElement(communityOutreach.sessionRegion).click(communityOutreach.sessionRegion).build().perform();
                waitFor(2);
                communityOutreach.sessionRegionValuesList.forEach(each -> actualList.add(each.getText()));
                break;
            case "DCEB CHANNEL":
                actions.moveToElement(communityOutreach.channel).click(communityOutreach.channel).build().perform();
                waitFor(2);
                communityOutreach.channelValueList.forEach(each -> actualList.add(each.getText()));
                break;
            case "DCEB SESSION TYPE":
                actions.moveToElement(communityOutreach.sessionType).click(communityOutreach.sessionType).build().perform();
                waitFor(2);
                communityOutreach.sessionTypeValuesList.forEach(each -> actualList.add(each.getText()));
                break;
            case "DCEB LANGUAGES":
                communityOutreach.languagesOpenButton.click();
                waitFor(2);
                communityOutreach.languagesValuesList.forEach(each -> actualList.add(each.getText()));
                break;
        }
        Collections.sort(expectedList);
        Collections.sort(actualList);
        assertEquals(actualList, expectedList, "Actual List: " + actualList + "\nExpected List: " + expectedList);
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    @And("I click on {string} Community Outreach option from left hand menu")
    public void iClickOnCommunityOutreachOptionFromLeftHandMenu(String type) {
        WebElement leftHandMenu = Driver.getDriver().findElement(By.xpath("//div[@class='mx-layout-left-drawer-tm mx-sidebar-menu']"));
        waitFor(2);
        hover(leftHandMenu);
        waitFor(2);
        hover(Driver.getDriver().findElement(By.xpath("//span[text()='COMMUNITY OUTREACH MGMT']")));
        Driver.getDriver().findElement(By.xpath("//span[text()='COMMUNITY OUTREACH MGMT']/..//span[text()='keyboard_arrow_down']")).click();
        waitFor(1);
        if ("CREATE SESSION".equals(type)) {
            Driver.getDriver().findElement(By.xpath("//span[text()='CREATE SESSION']")).click();
        } else if ("SEARCH SESSION".equals(type)) {
            Driver.getDriver().findElement(By.xpath("//span[text()='SEARCH SESSION']")).click();
        } else {
            fail("Mismatch in provided Type");
        }
        waitFor(1);
        actions.moveByOffset(200, 200).perform();
        waitFor(1);
    }

    @Then("I validate the Community Outreach Session entry {string} displayed in the search view calendar")
    public void iValidateTheCommunityOutreachSessionEntryDisplayedInTheSearchViewCalendar(String type) {
        waitFor(5);
        List<WebElement> sessionEntry = Driver.getDriver().findElements(By.xpath("//td[@data-date='" + getFutureDateFormatYYYYMMdd(1) + "']//div[contains(text(),'" + APICoreCommunityOutreachController.siteName + "')]"));
        switch (type) {
            case "is":
                assertTrue(sessionEntry.get(0).isDisplayed(), "Public Allowed Community Outreach Session entry was not found in calendar view");
                break;
            case "is not":
                assertEquals(sessionEntry.size(), 0, "Non Public Allowed Community Outreach Session entry was found in calendar view");
                break;
            default:
                fail("Mismatch in provided case type");
        }
    }
}
