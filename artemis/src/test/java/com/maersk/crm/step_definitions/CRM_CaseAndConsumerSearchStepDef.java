package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APICreateConsumerContactController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class CRM_CaseAndConsumerSearchStepDef extends BrowserUtils {

    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    CRMCaseAndConsumerSearchPage caseConsumerSrhPage = new CRMCaseAndConsumerSearchPage();
    ThreadLocal<CRM_PrimaryIndividualStepDef> primaryIndividualPage = ThreadLocal.withInitial(CRM_PrimaryIndividualStepDef::new);
    CRM_GeneralTaskStepDef createTaskPage = new CRM_GeneralTaskStepDef();
    CRMEditConsumerProfilePage editConsumerProfilePage = new CRMEditConsumerProfilePage();
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    CreateOutboundCorrespondencePage cOCP = new CreateOutboundCorrespondencePage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    CRMContactRecordDashboardPage dashboardPage = new CRMContactRecordDashboardPage();
    CRMAddContactInfoPage addContactInfo = new CRMAddContactInfoPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCreateGeneralTaskPage createGeneralTaskPage = new CRMCreateGeneralTaskPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    CRMEnrollmentUpdateStepDef enrollmentUpdateStepDef = new CRMEnrollmentUpdateStepDef();
    WebElement authenticationField1;
    WebElement authenticationField2;
    WebElement authenticationField1chkbox;
    private ThreadLocal<String> consumerFrstName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> expectedConsumerId = ThreadLocal.withInitial(String::new);
//    String expectedConsumerId= "";

    Actions actions = new Actions(Driver.getDriver());

    @When("I navigate to case and consumer search page")
    public void navigateToCaseAndConsumerSearchPage() {
        waitFor(3);
        hover(crmDashboardPage.case_ConsumerSearchTab);
        crmDashboardPage.case_ConsumerSearchTab.click();
        click(manualCaseConsumerSearch.lastName);
    }

    @When("I navigate to manual case and consumer search page")
    public void i_navigate_to_manual_case_and_consumer_search_page() {
        waitFor(2);
        hover(crmDashboardPage.case_ConsumerSearchTab);
        crmDashboardPage.case_ConsumerSearchTab.click();
    }

    @And("I search for record by value {string}")
    public void searchRecordByConsumerFirstName(String name) {
        caseConsumerSrhPage.consumerFirstName.sendKeys(name);
        jsClick(caseConsumerSrhPage.searchBtn);
    }

    @And("I expand first contact record in search results on case and consumer search page")
    public void expandFirstRecord() {
        caseConsumerSrhPage.contactIDs.get(0).click();
        Assert.assertTrue(caseConsumerSrhPage.DemographicInfoTab.isDisplayed());
    }

    @And("I select {string} on preferred language dropdown")
    public void selectPreferredLanguageDropdown(String val) {
        selectDropDown(caseConsumerSrhPage.preferredLanguageDropdown, val);
    }

    /* CP-670
     * Author -Paramita Created On- 10th Feb 2020*/

    @And("I click first contact record in search results on case and consumer search page")
    public void clickFirstRecord() {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        caseConsumerSrhPage.searchCaseConsumerID.get(0).click();
        Assert.assertTrue(caseConsumerSrhPage.DemographicInfoTab.isDisplayed());
    }

    @And("I click on Add button on DemographicInfo tab")
    public void clickAddButton() {
        //waitFor(5);
        waitForVisibility(caseConsumerSrhPage.addbutton, 10);
        caseConsumerSrhPage.addButton.get(0).click();
        Assert.assertTrue(caseConsumerSrhPage.primaryIndividualLabel.isDisplayed());
    }

    @And("I add all mandatory details of primary individual")
    public void populatePrimaryIndividualData() {
        primaryIndividualPage.get().i_populate_all_mandatory_details_of_primary_individual();
    }

    @And("I click to navigate {string} task page")
    public void navigateToTaskPage(String taskName) {
        waitForVisibility(crmDashboardPage.btnMenuList, 20);
        System.out.println("1");
        click(crmDashboardPage.btnMenuList);
        waitFor(2);
        waitForVisibility(crmDashboardPage.createTaskMenu, 10);
        waitFor(2);
        crmDashboardPage.createTaskMenu.click();
        // click(dashBoard.createTaskMenu);
        waitForVisibility(crmDashboardPage.subMenuList, 10);
        WebElement task = Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']"));
        scrollToElement(task);
        waitFor(2);
        try {
            jsClick(task);
        } catch (StaleElementReferenceException e) {
            Driver.getDriver().findElement(By.xpath("//div[@id='sub-menu-list']//ul/li//span[text()='" + taskName + "']")).click();
        }
    }

    @And("I click on primary individual record on DemographicInfo tab")
    public void navigateToCreateTaskPage() {
        caseConsumerSrhPage.searchCaseConsumerID.get(0).click();
        Assert.assertTrue(caseConsumerSrhPage.primaryIndividualLabel.isDisplayed());
    }

    @And("I update {string} on preferred language dropdown")
    public void updatePreferredLanguageDropdown(String val) {
        selectDropDown(caseConsumerSrhPage.UpdatePreferredLanguage, val);
    }

    @Then("I click on edit button on Profile Contact Page")
    public void i_click_on_edit_button_on_Profile_Contact_Page() {
        jsClick(caseConsumerSrhPage.profileContactEditButton);
        waitFor(5);
    }

    @Then("I provide ssn number and DOB to edit consumer profile")
    public void i_provide_ssn_number_and_DOB_to_edit_consumer_profile() {
        clearAndFillText(editConsumerProfilePage.consUpdateSSN, getRandomNumber(9));
        clearAndFillText(editConsumerProfilePage.consUpdateDOB, "03/02/1970");
        waitFor(2);
    }

    @Then("I click on save button on Edit Consumer Profile page")
    public void i_click_on_save_button_on_Edit_Consumer_Profile_page() {
        editConsumerProfilePage.saveButtonUpdateProfilePage.click();
        waitFor(3);
    }

    @Then("I click on task service request tab")
    public void clickOnTaskServiceRequestTab() {
        click(crmDashboardPage.taskServiceRequestTab);
    }

    /* Author - Paramita - To verify
  Script  to Verify -To click first contact record in search results
 */
    @And("I click first contact record in search results")
    public void i_click_first_contact_record_in_search_results() {
        click(caseConsumerSrhPage.expandFistConsumer);
        //scrollDownUsingActions(1);
    }

    /* Author - Paramita - To verify
    Script  to Verify - Display and Hidden fields in the Consumer Authentication Grid
    */
    @Then("I see Display and Hidden fields in the Authentication Grid")
    public void display_fields_in_the_authentication_grid() {
        waitFor(1);


        List<String> expectedAuthenticationLabels1 = Arrays.asList("HOME ADDRESS", "CASE ID");
        for (int i = 0; i < expectedAuthenticationLabels1.size(); i++) {

            WebElement e1;

            authenticationField1 = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + expectedAuthenticationLabels1.get(i) + "')]"));
            Assert.assertTrue(authenticationField1.isDisplayed(), "Consumer Authentication fields are not displayed");
            e1 = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + expectedAuthenticationLabels1.get(i) + "')]/..//input[@type='checkbox']/../.."));
            Assert.assertTrue(e1.getAttribute("aria-disabled").contains("false"), "Checkbox are not showing enabled");


        }

        List<String> expectedAuthenticationLabels2 = Arrays.asList("FULL NAME", "CONSUMER ROLE", "DOB", "SSN", "CRM CONSUMER ID", "PROGRAM CONSUMER ID");
        for (int m = 0; m < caseConsumerSrhPage.authenticationTableColumn.size(); m++) {
            authenticationField2 = Driver.getDriver().findElement(By.xpath("//table//tr/th[text()='" + expectedAuthenticationLabels2.get(m) + "']"));
            Assert.assertTrue(authenticationField2.isDisplayed(), "Consumer Authentication fields are not displayed");
        }
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.phField), "Ph number not displayed");


        // To check checkbox visibility
        for (int p = 1; p < caseConsumerSrhPage.firstRecordCheckboxCount.size(); p++) {
            WebElement e2 = Driver.getDriver().findElement(By.xpath("(//table//tbody/tr[1]//input[@type='checkbox'])[" + p + "]/../.."));
            Assert.assertTrue(e2.getAttribute("aria-disabled").contains("false"), "Checkbox are not showing enabled");
        }


    }


    @When("I select first record radio button")
    public void select_first_contact_record_radioButton() {

        caseConsumerSrhPage.firstConsumerNameRadioButton.click();
    }

    /* Author - Paramita - To verify
    Script  to Verify - Display of 'Consumer Authenticated' button on selecting more than one Authentication checkbox
    */
    @Then("I verify display of 'Consumer Authenticated' button based on select {string} of checkboxes")
    public void select_authentication_checkbox(String number) {
        WebElement e2;
        switch (number) {
            case "1":
                caseConsumerSrhPage.firstRecordCheckboxCount.get(0).click();
                Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton), "Consumer Authenticated button is displayed");
                Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.linkRecordButton), "Consumer Authenticated button is displayed");
                break;

            case "2":
                caseConsumerSrhPage.firstRecordCheckboxCount.get(0).click();
                caseConsumerSrhPage.firstRecordCheckboxCount.get(1).click();
                Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton), "Consumer Authenticated button is displayed");
                Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.linkRecordButton), "Consumer Authenticated button is displayed");
                break;

            case "3":
                caseConsumerSrhPage.firstRecordCheckboxCount.get(0).click();
                caseConsumerSrhPage.firstRecordCheckboxCount.get(1).click();
                caseConsumerSrhPage.firstRecordCheckboxCount.get(2).click();
                Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton), "Consumer Authenticated button is displayed");
                Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.linkRecordButton), "Consumer Authenticated button is displayed");
                break;

        }

    }


    @Then("I verify 'Consumer Authenticated' button should not display less than 2 checkbox")
    public void verifyAuthenticationMessage() {
        WebElement e2;
        for (int p = 1; p <= 2; p++) {
            e2 = Driver.getDriver().findElement(By.xpath("(//tbody/tr[1]//input[@type='checkbox'])[" + p + "]"));
            e2.click();
        }
        Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton), "Consumer Authenticated button is displayed");
        Assert.assertTrue(isElementDisplayed(caseConsumerSrhPage.linkRecordButton), "Consumer Authenticated button is displayed");

        if (Driver.getDriver().findElement(By.xpath("(//tbody/tr[1]//input[@type='checkbox'])[2]")).isSelected()) {
            Driver.getDriver().findElement(By.xpath("(//tbody/tr[1]//input[@type='checkbox'])[2]")).click();

        }
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.consumerAuthenticateButton), "Consumer Authenticated button is displayed");
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.linkRecordButton), "Consumer Authenticated button is displayed");
    }

    @And("I click first consumer ID in search results on case and consumer search page")
    public void iClickFirstConsumerIDInSearchResultsOnCaseAndConsumerSearchPage() {
        click(caseConsumerSrhPage.consumerIDSearch.get(0));
    }

    @Then("I verify {string} is not listed as a tab header")
    public void iVerifyIsNotListedAsATabHeader(String header) {
        for (WebElement tabName : manualCaseConsumerSearch.caseProfileTabHeaders) {
            Assert.assertFalse(tabName.getText().equals(header), "Page contains unexpected header tab");
        }
    }

    @When("I verify Search Case field is not displayed")
    public void i_verify_Search_Case_field_is_not_displayed() {
        Assert.assertTrue(!isElementDisplayed(manualContactRecordSearchPage.searchCase));
    }

    @When("I verify Case ID field is not displayed")
    public void i_verify_Case_ID_field_is_not_displayed() {
        Assert.assertTrue(!isElementDisplayed(cOCP.caseID));
    }

    @When("I verify search result for consumer is inactive")
    public void i_verify_search_result_for_consumer_is_inactive() {
        Assert.assertTrue(isElementDisplayed(cOCP.inactiveConsumer));
    }

    @When("I click on first Primary Individual")
    public void i_click_on_first_Primary_Individual() {
        waitFor(1);
        contactHistory.editedO.click();
    }


    @And("I click first consumer ID in search results for case and consumer search page")
    public void iClickFirstConsumerIDInSearchResultsforCaseAndConsumerSearchPage() {
        click(caseConsumerSrhPage.caseIDSearchResult.get(0));
        waitForVisibility(caseConsumerSrhPage.frstPrimaryCon, 10);
        consumerFrstName.set(caseConsumerSrhPage.frstPrimaryCon.getText());
    }

    @And("Verify Primary Individual consumer first then all case members listed alphabetically by First Name")
    public void verifyPrimaryIndividualConsumerFirstThenAllCaseMembersListedAlphabeticallyByFirstName() {
        dashboardPage.consumerDropHistoryTab.click();
        Assert.assertTrue(dashboardPage.consumerNameDrop.get(0).getText().contains(consumerFrstName.get()));
        dashboardPage.consumerNameDrop.remove(0);
        ascendingOrderTexts(dashboardPage.consumerNameDrop);
    }

    @Then("I verify Channel dropdown on History screen")
    public void iVerifyChannelDropdownOnHistoryScreen(List<String> channel) {
        dashboardPage.consumerNameDrop.get(0).click();
        dashboardPage.channelDropHistoryTab.click();
        for (int i = 0; i < channel.size(); i++) {
            Assert.assertTrue(dashboardPage.lstChannelDrop.get(i).getText().contains(channel.get(i)));
        }
    }

    @Then("I verify {string} Business Events has required data elements")
    public void iVerifyBusinessEventsHasRequiredDataElements(String bus) {
        if (bus.equalsIgnoreCase("Eligibility")) {
            waitFor(2);
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-businessEvents']"))).click().pause(1000).build().perform();
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='Eligibility']"))).click().pause(1000).sendKeys(Keys.ESCAPE).build().perform();
            waitFor(2);
            Assert.assertTrue(dashboardPage.busEventName.isDisplayed());
            Assert.assertTrue(dashboardPage.channelName.isDisplayed());
            Assert.assertTrue(dashboardPage.processDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.createdByHis.isDisplayed());
            Assert.assertTrue(dashboardPage.elstartDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.elEndDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.elProgramCode.isDisplayed());
            Assert.assertTrue(dashboardPage.elsubProgramCode.isDisplayed());
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-businessEvents']"))).click().pause(1000).build().perform();
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='Eligibility']"))).click().pause(1000).
                    click().sendKeys(Keys.ESCAPE).build().perform();
        } else if (bus.equalsIgnoreCase("Enrollment")) {
            waitFor(2);
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-businessEvents']"))).click().pause(1000).build().perform();
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='Enrollment']"))).click().sendKeys(Keys.ESCAPE).build().perform();
            waitFor(2);
            Assert.assertTrue(dashboardPage.busEventName.isDisplayed());
            Assert.assertTrue(dashboardPage.channelName.isDisplayed());
            Assert.assertTrue(dashboardPage.processDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.createdByHis.isDisplayed());
            Assert.assertTrue(dashboardPage.enstartDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.enEndDatehis.isDisplayed());
            Assert.assertTrue(dashboardPage.elProgramCode.isDisplayed());
            Assert.assertTrue(dashboardPage.elsubProgramCode.isDisplayed());
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//div[@id='mui-component-select-businessEvents']"))).click().pause(1000).build().perform();
            actions.moveToElement(Driver.getDriver().findElement(By.xpath("//li[@data-value='Enrollment']"))).click().pause(1000).
                    click().sendKeys(Keys.ESCAPE).build().perform();
        }
    }

    @Then("Verify I will see screen display the consumer’s {int} most recent events")
    public void verifyIWillSeeScreenDisplayTheConsumerSMostRecentEvents(int count) {
        waitFor(3);
//        Assert.assertTrue(dashboardPage.listOfEvents.size() >= count);
        Assert.fail("manually check verification");
    }

    @Then("I see the State Case ID value displayed underneath the Primary Individual's name")
    public void i_see_the_State_Case_ID_value_displayed_underneath_the_Primary_Individual_s_name() {
        Assert.assertTrue(caseConsumerSrhPage.stateCaseIDValue.isDisplayed());
        assertEquals(caseConsumerSrhPage.stateCaseIDValue.getText(), "425wffs");
    }

    @Then("I see the State Case ID value displayed in Active Contact widget")
    public void i_see_the_State_Case_ID_value_displayed_in_Active_Contact_widget() {
        Assert.assertTrue(manualContactRecordSearchPage.widgetPhoneIcon.isDisplayed());
        assertEquals(caseConsumerSrhPage.stateCaseIDValueWidged.getText(), "425wffs");
    }

    @Then("I verify Alert Banner is displayed with text {string} on Case Summary Page")
    public void i_verify_Alert_Banner_is_displayed_with_text_on_Case_Summary_Page(String message) {
        waitFor(2);
        Assert.assertTrue(caseConsumerSrhPage.alertBannerText.isDisplayed());
        assertTrue(caseConsumerSrhPage.alertBannerText.getText().contains(message));
    }

    @Then("I verify Alert Banner is displayed with text {string} on View Task Page")
    public void i_verify_Alert_Banner_is_displayed_with_text_on_View_Task_Page(String message) {
        waitFor(2);
        Assert.assertTrue(caseConsumerSrhPage.alertBannerTxtViewTask.isDisplayed());
        assertTrue(caseConsumerSrhPage.alertBannerTxtViewTask.getText().contains(message));
    }

    @Then("I verify consumer name {string} is displayed on alert banner")
    public void i_verify_consumer_name_is_displayed_on_alert_banner(String name) {
        System.out.println(caseConsumerSrhPage.alertBannerConsumerName.get(0).getText());
        Assert.assertTrue(caseConsumerSrhPage.alertBannerConsumerName.get(0).isDisplayed());
        assertTrue(caseConsumerSrhPage.alertBannerConsumerName.get(0).getText().contains(name));
    }

    @Then("I verify Alert Banner is not displayed on Case Summary Page")
    public void i_verify_Alert_Banner_is_not_displayed_on_Case_Summary_Page() {
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.alertBannerText));
    }

    @Then("I verify {string} button is displayed on case summary page")
    public void i_verify_button_is_displayed_on_case_summary_page(String string) {
        Assert.assertTrue(caseConsumerSrhPage.viewAllAlertsBtn.isDisplayed());
    }

    @When("I click on View All Alerts button")
    public void i_click_on_View_All_Alerts_button() {
        scrollToTop();
        // scrollUpUsingActions(6);
        waitForVisibility(caseConsumerSrhPage.viewAllAlertsBtn, 10);
        caseConsumerSrhPage.viewAllAlertsBtn.click();
    }

    @Then("I verify header text and close button are displayed")
    public void i_verify_header_text_and_close_button_are_displayed() {
        Assert.assertTrue(caseConsumerSrhPage.closeXBtn.isDisplayed());
        Assert.assertTrue(caseConsumerSrhPage.AlertsText.isDisplayed());
    }

    @Then("I verify active alert with text {string} is displayed")
    public void i_verify_active_alert_with_text_is_displayed(String alertText) {
        if (alertText.equalsIgnoreCase("Active Alert Banner")) {
            System.out.println(caseConsumerSrhPage.ActiveAlertMsg.getText());
            assertTrue(caseConsumerSrhPage.ActiveAlertMsg.getText().contains(alertText));
        } else if (alertText.equalsIgnoreCase("Inactive Past Alert Banner")) {
            System.out.println(caseConsumerSrhPage.PastAlertMsg.getText());
            assertTrue(caseConsumerSrhPage.PastAlertMsg.getText().contains(alertText));
        } else if (alertText.equalsIgnoreCase("Inactive Future Alert Banner")) {
            System.out.println(caseConsumerSrhPage.FutureAlertMsg.getText());
            assertTrue(caseConsumerSrhPage.FutureAlertMsg.getText().contains(alertText));
        }
    }

    @Given("I validate transaction details fields for Elibigility Business Events")
    public void i_validate_transaction_details_fields_for_Elibigility_Business_Events() {
        dashboardPage.expandingCaratForEligibilityTransactionDetails.click();
        waitFor(3);
        dashboardPage.eligibilityBenefitStatus.isDisplayed();
        dashboardPage.eligibilityCategoryCode.isDisplayed();
        dashboardPage.eligibilityCoverageCode.isDisplayed();
        dashboardPage.eligibilityEndDate.isDisplayed();
        dashboardPage.eligibilityEndReason.isDisplayed();
        dashboardPage.eligibilityExemptionCode.isDisplayed();
        dashboardPage.eligibilityProgramCode.isDisplayed();
        dashboardPage.eligibilityStartDate.isDisplayed();
        dashboardPage.eligibilitySubProgramCode.isDisplayed();
    }

    @Given("I validate transaction details fields for Enrollement Business Events")
    public void i_validate_transaction_details_fields_for_Enrollement_Business_Events() {
        dashboardPage.expandingCaratForEnrollementTransactionDetails.click();
        waitFor(3);
        dashboardPage.eligibilityBenefitStatus.isDisplayed();
        dashboardPage.enrollementEndDate.isDisplayed();
        dashboardPage.enrollementStartDate.isDisplayed();
        dashboardPage.enrollementType.isDisplayed();
        dashboardPage.planCode.isDisplayed();
        dashboardPage.rejectReason.isDisplayed();
        dashboardPage.selectionStatus.isDisplayed();
        dashboardPage.enrollementOldValue.isDisplayed();
        dashboardPage.enrollementUpdatedValue.isDisplayed();
    }

    @When("I verify Create Alert page is displayed")
    public void i_verify_Create_Alert_page_is_displayed() {
        Assert.assertTrue(caseConsumerSrhPage.createAlertTxt.isDisplayed());
    }

    @When("I verify the following fields are displayed in Create Alert page")
    public void i_verify_the_following_fields_are_displayed_in_Create_Alert_page() {
        Assert.assertTrue(caseConsumerSrhPage.alertText.isDisplayed());
        Assert.assertTrue(caseConsumerSrhPage.alertType.isDisplayed());
        Assert.assertTrue(addContactInfo.startDate.isDisplayed());
        Assert.assertTrue(addContactInfo.endDate.isDisplayed());
        Assert.assertTrue(addContactInfo.saveButton.isDisplayed());
        Assert.assertTrue(addContactInfo.cancelButton.isDisplayed());
    }

    @When("I verify Type contains following values")
    public void i_verify_Type_contains_following_values(List<String> options) {
        for (String option : options) {
            selectDropDown(caseConsumerSrhPage.alertType, option);
        }
    }

    @Then("I verify error message for fields to be required")
    public void i_verify_error_message_for_fields_to_be_required() {
        Assert.assertTrue(caseConsumerSrhPage.alertTxtRequiredErrorMsg.isDisplayed());
        Assert.assertTrue(caseConsumerSrhPage.alertTypeRequiredErrorMsg.isDisplayed());
        Assert.assertTrue(caseConsumerSrhPage.alertDateRequiredErrorMsg.isDisplayed());
    }

    @Then("I verify medicaid case id is systematically populated with the linked consumer")
    public void i_verify_medicaid_case_id_is_systematically_populated_with_the_linked_consumer() {
        assertTrue(caseConsumerSrhPage.CaseIdResult.getText().contains("7346"));
    }

    @When("I successfully create an {string} Alert for consumer")
    public void i_successfully_create_an_Alert_for_consumer(String status) {
        if (status.equals("Active")) {
            caseConsumerSrhPage.alertText.sendKeys("Sample Alert for linked consumer");
            selectDropDown(caseConsumerSrhPage.alertType, "ACTIONABLE");
            clearAndFillText(addContactInfo.startDate, getCurrentDate());
        } else if (status.equals("Future")) {
            caseConsumerSrhPage.alertText.sendKeys("Future Alert");
            selectDropDown(caseConsumerSrhPage.alertType, "ACTIONABLE");
            clearAndFillText(addContactInfo.startDate, "12/12/2028");
        }
    }

    @Then("I am navigated back to Active Contact Search Page")
    public void i_am_navigated_back_to_Active_Contact_Search_Page() {
        waitFor(2);
        Assert.assertTrue(contactHistory.generalConsumerInContact.isDisplayed());
    }

    @Then("I will see create links button")
    public void I_will_see_create_links_button() {
        assertTrue(isElementDisplayed(caseConsumerSrhPage.createAlertLinkBnt), "create links button not present on page");
    }

    @And("I will click on create link button on manual create alert page")
    public void i_will_click_on_create_link_button_on_manual_create_alert_page() {
        waitForVisibility(caseConsumerSrhPage.createAlertLinkBnt, 10);
        caseConsumerSrhPage.createAlertLinkBnt.click();
        waitForVisibility(caseConsumerSrhPage.caseConsumerBnt, 10);
        caseConsumerSrhPage.caseConsumerBnt.click();
    }

    @And("I click on search button on manual create alert page")
    public void i_click_on_search_button_on_manual_create_alert_page() {
        caseConsumerSrhPage.searchBtnOnManualCreateAlertPage.click();
    }

    @Then("I will see the Case Consumer Search Results in the component")
    public void caseConsumerSearchResults(List<String> options) {
        List<String> actualHeaderValues = new ArrayList<>();
        String headerName = "";
        for (int i = 0; i < (contactRecord.listOfSearchResultHeaders.size()); i++) {
            if (contactRecord.listOfSearchResultHeaders.get(i).getText().endsWith("DATE OF BIRTH")) {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText().substring(11, 24);
                actualHeaderValues.add(i, headerName);
            } else if (contactRecord.listOfSearchResultHeaders.get(i).getText().endsWith("SSN")) {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText().substring(11, 14);
                actualHeaderValues.add(i, headerName);
            } else {
                headerName = contactRecord.listOfSearchResultHeaders.get(i).getText();
                actualHeaderValues.add(i, headerName);
            }
        }
        for (String option : options) {
            assertTrue(actualHeaderValues.contains(option), option + " Column header is not displayed");
        }
    }

    @When("I click on each radio box in front of all consumer one by one and verify linkCaseConsumer button is displayed")
    public void I_click_on_each_radio_box() {
        List<WebElement> listOfCheckBox = caseConsumerSrhPage.listOfCheckBoxFromExpandSearchResult;

        for (int i = 0; i < listOfCheckBox.size(); i++) {
            listOfCheckBox.get(i).click();
            assertTrue(listOfCheckBox.get(i).isSelected(), "checkbox not selected");
            waitForVisibility(contactRecord.linkCaseConsumer, 5);
            assertTrue(isElementDisplayed(contactRecord.linkCaseOnlyCheckBox), "link Case Only CheckBox not displayed");
            assertTrue(!contactRecord.linkCaseOnlyCheckBox.isSelected(), "link Case Only CheckBox selected");
            assertTrue(isElementDisplayed(contactRecord.linkCaseConsumer), "linkCaseConsumer button not displayed");
        }
    }

    @Then("I will see the Link Consumer button")
    public void IwillseetheLinkConsumerButton() {
        waitFor(2);
        assertTrue(isElementDisplayed(createGeneralTaskPage.linkConsumerbutton), "link Consumer button not present on page");

    }

    @Then("I see LINK CASE button is displayed on the page")
    public void Isee_LINK_TO_CASE_ONLYcheckbox() {
        assertTrue(isElementDisplayed(createGeneralTaskPage.linkRecord_Casebutton), "link Consumer button not present on page");
    }

    @When("I click the Link Case button")
    public void I_click_the_Link_Case_button() {
        createGeneralTaskPage.linkRecord_Casebutton.click();
    }

    @Then("Alert is linked to the Case record only and not a specific Consumer on that Case")
    public void alert_is_linked_to_the_Case_record() {
        waitForVisibility(createGeneralTaskPage.unlinkCaseOrConsumer, 10);
        assertTrue(isElementDisplayed(createGeneralTaskPage.unlinkCaseOrConsumer), "unlinkCaseOrConsumer not displayed");
    }

    @Then("Verify Phone Number {string} will be selectable as an authentication data point")
    public void verify_Phone_Number_will_be_selectable_as_an_authentication_data_point(String phoneType) {
        assertTrue(isElementDisplayed(contactRecord.phoneNumberAuthCheckBox), "phoneNumberAuthCheckBox no displayed on page");
        if (phoneType.equalsIgnoreCase("cell")) {
            assertTrue(isElementDisplayed(contactRecord.cellPhoneImage), "cellPhoneImage no displayed on page");
        } else if (phoneType.equalsIgnoreCase("home")) {
            assertTrue(isElementDisplayed(contactRecord.homePhoneImage), "cellPhoneImage no displayed on page");
        } else if (phoneType.equalsIgnoreCase("work")) {
            assertTrue(isElementDisplayed(contactRecord.workPhoneImage), "cellPhoneImage no displayed on page");
        }
    }

    @Then("I verify start and end date when I hover over on calendar icon")
    public void add_Tool_TipVerification() {
        waitFor(2);
        hover(caseConsumerSrhPage.calendarIconAlertSlider);
        waitFor(2);
        waitForVisibility(caseConsumerSrhPage.toolTipDateOnAlertSlider, 10);
        assertTrue(isElementDisplayed(caseConsumerSrhPage.toolTipDateOnAlertSlider), "toolTipDateOnAlertSlider not displayed");
        assertEquals(caseConsumerSrhPage.toolTipDateOnAlertSlider.getText(), getCurrentDate());
    }

    @Then("I verify Business Events dropdown values on History screen")
    public void iVerifyBusinessEventsDropdownOnHistoryScreen(List<String> businessEvents) {
        dashboardPage.busDrop.click();
        waitFor(1);
        for (int i = 0; i < businessEvents.size(); i++) {
            Assert.assertTrue(dashboardPage.businessEventsdrop.get(i).getText().equals(businessEvents.get(i)));
        }
        new Actions(Driver.getDriver())
                .moveToElement(dashboardPage.historyScreen).click().perform();
    }

    @Then("I verify Process Date Range dropdown values on History screen")
    public void iVerifyProcessDateRangeDropdownOnHistoryScreen(List<String> businessEvents) {
        dashboardPage.historyScreenProcessDateRangeDropDown.click();
        waitFor(1);
        for (int i = 0; i < dashboardPage.processDateRangeDropDownValues.size(); i++) {
            Assert.assertTrue(dashboardPage.businessEventsdrop.get(i).getText().equals(businessEvents.get(i)));
        }
        new Actions(Driver.getDriver())
                .moveToElement(dashboardPage.historyScreen).click().perform();
    }

    @Then("I verify Channel dropdown values on History screen")
    public void iVerifyChannelDropdownValuesOnHistoryScreen(List<String> channel) {
        dashboardPage.channelDropHistoryTab.click();
        waitFor(1);
        for (int i = 0; i < channel.size(); i++) {
            Assert.assertTrue(dashboardPage.lstChannelDrop.get(i).getText().contains(channel.get(i)));
        }
        new Actions(Driver.getDriver())
                .moveToElement(dashboardPage.historyScreen).click().perform();
    }

    @Then("I select {string} as Business Events")
    public void iSelectBEValue(String businessEvent) {
        selectFromMultiSelectDropDown(dashboardPage.busDrop, businessEvent);
        new Actions(Driver.getDriver())
                .moveToElement(dashboardPage.historyScreen).click().perform();
    }

    @Then("I select {string} as Process Data Range")
    public void iSelectDateRange(String range) {
        selectDropDown(dashboardPage.historyScreenProcessDateRangeDropDown, range);
    }

    @Then("I select {string} as Channel")
    public void iSelectChannel(String channel) {
        selectFromMultiSelectDropDown(dashboardPage.channelDropHistoryTab, channel);
        new Actions(Driver.getDriver())
                .moveToElement(dashboardPage.historyScreen).click().perform();
    }

    @Then("I reset filters")
    public void iResetFilters() {
        click(dashboardPage.resetFilterButton);
    }

    @And("I verify consumer name dropdown field and select primary member")
    public void iverifyConsumerNameDropdown() {
        click(dashboardPage.historyScreenConsumerNameDropDown);
        waitFor(1);
        assertTrue(dashboardPage.consumerNameDrop.get(0).getText().equals("FnHhLmn LnfqzlB"), "Primary consumer is not listed first in dropdown");
        click(Driver.getDriver().findElement(By.xpath("(//*[contains(text(),'FnHhLmn')])[2]")));
    }

    @And("I navigate to Business Events history for the consumer")
    public void iNavigateToHistoryTab() {
        waitFor(4);
        contactRecord.stateCaseIdFirstRecordBusEvents.click();
        waitForVisibility(dashboardPage.historyScreen, 10);
        dashboardPage.historyScreen.click();
    }

    @When("I verify Hamburger menu is displayed for ACTIONABLE alerts")
    public void i_verify_Hamburger_menu_is_displayed_for_ACTIONABLE_alerts() {
        Assert.assertTrue(caseConsumerSrhPage.alertHamburger.isDisplayed());
    }

    @When("I verify Hamburger menu is not displayed for STATIC alerts")
    public void i_verify_Hamburger_menu_is_not_displayed_for_STATIC_alerts() {
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.alertHamburger));
    }

    @When("I click on Hamburger menu")
    public void i_click_on_Hamburger_menu() {
        waitFor(1);
        caseConsumerSrhPage.alertHamburger.click();
    }

    @Then("I verify option Inactivate is displayed")
    public void i_verify_option_Inactivate_is_displayed() {
        Assert.assertTrue(caseConsumerSrhPage.inactivateTxt.isDisplayed());
    }

    @Then("I click on {string} option for alert")
    public void i_click_on_option_for_alert(String option) {
        waitFor(2);
        if (option.equals("Inactivate")) {
            caseConsumerSrhPage.inactivateTxt.click();
            waitFor(2);
        } else if (option.equals("Activate")) {
            caseConsumerSrhPage.activateTxt.click();
            waitFor(2);
        } else if (option.equals("Edit")) {
            caseConsumerSrhPage.editTxt.click();
            waitFor(2);
        }
    }

    @Then("I verify the the border of alert turns {string}")
    public void i_verify_the_the_border_of_alert_turns(String color) {
        waitFor(2);
        if (color.equals("grey")) {
            String greyColor = caseConsumerSrhPage.greyBorderAlert.getCssValue("color");
            assertEquals(greyColor, "rgba(158, 158, 158, 1)");
        } else if (color.equals("red")) {
            String redColor = caseConsumerSrhPage.redBorderAlert.getCssValue("color");
            assertEquals(redColor, "rgba(211, 47, 47, 1)");
        } else if (color.equals("orange")) {
            String orangeColor = caseConsumerSrhPage.orangeBorderAlert.getCssValue("color");
            assertEquals(orangeColor, "rgba(245, 124, 0, 1)");
        }
    }

    @Then("I verify option Edit is displayed")
    public void i_verify_option_Edit_is_displayed() {
        Assert.assertTrue(caseConsumerSrhPage.editTxt.isDisplayed(), "Edit button is not displayed");
    }

    @Then("I verify option Edit is not displayed")
    public void i_verify_option_Edit_is_not_displayed() {
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.editTxt), "Edit button is displayed");
    }

    @Then("I edit the alert details")
    public void i_edit_the_alert_details() {
        caseConsumerSrhPage.alertText.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        caseConsumerSrhPage.alertText.sendKeys("Edited Alert");
        selectDropDown(caseConsumerSrhPage.alertType, "STATIC");
        clearAndFillText(addContactInfo.startDate, "10/10/2025");
    }

    @Then("I verify I am on Alert Details screen")
    public void i_verify_I_am_on_Alert_Details_screen() {
        Assert.assertTrue(caseConsumerSrhPage.alertDetails.isDisplayed(), "Alert Details Page is not displayed");
    }

    @Then("I click on first Alert ID")
    public void i_click_on_first_Alert_ID() {
        caseConsumerSrhPage.firstAlertID.click();
    }

    @Then("I click on inactivate Alert button on Alert Details Page")
    public void i_click_on_inactivate_Alert_button_on_Alert_Details_Page() {
        waitFor(2);
        click(caseConsumerSrhPage.inactivateAlert);
        waitFor(2);
    }

    @And("I verify {string} dropdown contains the following values on History screen")
    public void iVerifyDropdownContainsTheFollowingValuesOnHistoryScreen(String dropdownType, List<String> data) {
        switch (dropdownType) {
            case "BUSINESS EVENTS":
                dashboardPage.busDrop.click();
                waitFor(1);
                List<String> actualBElist = new ArrayList<>();
                List<String> expectedBElist = new ArrayList<>(data);
                for (WebElement webElement : dashboardPage.businessEventsdrop) {
                    actualBElist.add(webElement.getText());
                }
                for (int i = 0; i < expectedBElist.size(); i++) {
                    assertTrue(actualBElist.contains(expectedBElist.get(i)), "Expected application value " + expectedBElist.get(i) + " in " + dropdownType + " but missing");
                }
                break;
            default:
                fail("Provided key did not match");

        }
    }

    @Then("I verify hidden Unlink Case and Consumer button is visible")
    public void i_verify_hidden_Unlink_Case_and_Consumer_button_is_visible() {
        Assert.assertTrue(caseConsumerSrhPage.hiddenUnlinkBtn.isDisplayed(), "Unlink Case and Consumer button is not visible");
    }

    @When("I click hidden Unlink Case and Consumer button")
    public void i_click_hidden_Unlink_Case_and_Consumer_button() {
        click(caseConsumerSrhPage.hiddenUnlinkBtn);
    }

    @Then("I verify Unlink Case and Consumer button is not visible")
    public void i_verify_Unlink_Case_and_Consumer_button_is_not_visible() {
        Assert.assertTrue(!isElementDisplayed(caseConsumerSrhPage.hiddenUnlinkBtn), "Unlink Case and Consumer button is visible");
    }

    @Then("I verify validation error {string}")
    public void i_verify_validation_error(String errorMsg) {
        waitForVisibility(caseConsumerSrhPage.linkAlertErrorMsg, 10);
        assertTrue(caseConsumerSrhPage.linkAlertErrorMsg.isDisplayed(), "Error Message for Linking Consumer Alert is not displayed");
    }

    @When("I search for consumer by {string} with value {string}")
    public void i_search_for_consumer_by_with_value(String field, String value) {
        waitFor(2);
        switch (field) {
            case "StateID":
                waitFor(1);
                caseConsumerSrhPage.ConsumerDOB.click();
                caseConsumerSrhPage.externalID.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "SSN":
                waitFor(1);
                caseConsumerSrhPage.ConsumerDOB.click();
                caseConsumerSrhPage.consumerSsn.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "Name":
                caseConsumerSrhPage.Name.click();
                caseConsumerSrhPage.Name.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "DOB":
                caseConsumerSrhPage.ConsumerDOB.click();
                caseConsumerSrhPage.ConsumerDOB.sendKeys(value);
                caseConsumerSrhPage.Name.sendKeys("Lee");
                caseConsumerSrhPage.searchButton.click();
                break;
            case "Address":
                caseConsumerSrhPage.consumerAddress.click();
                caseConsumerSrhPage.consumerAddress.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "City,Zip":
                caseConsumerSrhPage.consumerCityZip.click();
                caseConsumerSrhPage.consumerCityZip.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "Phone":
                caseConsumerSrhPage.consumerPhoneNumber.click();
                caseConsumerSrhPage.consumerPhoneNumber.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "Email":
                caseConsumerSrhPage.consumerEmail.click();
                caseConsumerSrhPage.consumerEmail.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "CaseID":
                caseConsumerSrhPage.ConsumerDOB.click();
                contactRecord.btnAdvancedSearch.click();
                waitFor(1);
                selectDropDown(caseConsumerSrhPage.searchByDropdown, "Medicaid Case ID");
                caseConsumerSrhPage.consumerID.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
            case "ConsumerID":
                caseConsumerSrhPage.ConsumerDOB.click();
                contactRecord.btnAdvancedSearch.click();
                waitFor(1);
                selectDropDown(caseConsumerSrhPage.searchByDropdown, "Internal Consumer ID");
                caseConsumerSrhPage.consumerID.sendKeys(value);
                caseConsumerSrhPage.searchButton.click();
                break;
        }
    }

    @Then("I verify search result displays {string} with value {string}")
    public void i_verify_search_result_displays_with_value(String field, String value) {
        waitFor(2);
        switch (field) {
            case "StateID":
                assertEquals(caseConsumerSrhPage.StateIDResult.getText(), value);
                break;
            case "SSN":
                caseConsumerSrhPage.SSNMaskBtnSearchParameter.click();
                assertEquals(caseConsumerSrhPage.SNNResult.getText(), "660-00-0011");
                break;
            case "Name":
                assertEquals(caseConsumerSrhPage.NameResult.getText(), "Lilly Lee");
                break;
            case "DOB":
                caseConsumerSrhPage.visibilityOff.get(0).click();
                assertEquals(caseConsumerSrhPage.DOBResult.getText(), value);
                break;
            case "Address":
                assertTrue(caseConsumerSrhPage.AddressResult.getText().contains(value));
                break;
            case "City,Zip":
                assertEquals(caseConsumerSrhPage.AddressResult.getText(), "8888 Parker Dr,Denver,Colorado,11111-1111");
                break;
            case "Phone":
                assertEquals(caseConsumerSrhPage.PhoneResult.getText(), value);
                break;
            case "Email":
                assertEquals(caseConsumerSrhPage.EmailResult.getText(), value);
                break;
            case "CaseID":
                assertEquals(caseConsumerSrhPage.CaseIdResult.getText(), "371548325121");
                break;
        }
    }

    @When("I verify {string} field accepts {int} characters")
    public void i_verify_field_accepts_characters(String field, int stringLength) {
        String actual = "";
        String sentText = createTextString(stringLength);
        switch (field) {
            case "StateID":
                clearAndFillText(caseConsumerSrhPage.externalID, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.externalID.getAttribute("value");
                break;
            case "SSN":
                clearAndFillText(caseConsumerSrhPage.consumerSsn, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.consumerSsn.getAttribute("value");
                break;
            case "Name":
                clearAndFillText(caseConsumerSrhPage.Name, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.Name.getAttribute("value");
                break;
            case "DOB":
                clearAndFillText(caseConsumerSrhPage.ConsumerDOB, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.ConsumerDOB.getAttribute("value");
                break;
            case "Address":
                clearAndFillText(caseConsumerSrhPage.consumerAddress, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.consumerAddress.getAttribute("value");
                break;
            case "Zip":
                clearAndFillText(caseConsumerSrhPage.consumerCityZip, "12345");
                waitFor(1);
                actual = caseConsumerSrhPage.consumerCityZip.getAttribute("value");
                break;
            case "Phone":
                clearAndFillText(caseConsumerSrhPage.consumerPhoneNumber, "1234567890");
                break;
            case "Email":
                clearAndFillText(caseConsumerSrhPage.consumerEmail, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.consumerEmail.getAttribute("value");
                break;
            case "ConsumerID":
                clearAndFillText(caseConsumerSrhPage.consumerID, sentText);
                waitFor(1);
                actual = caseConsumerSrhPage.consumerID.getAttribute("value");
                break;
        }
        assertTrue(actual.length() <= stringLength);
    }

    @When("I enter invalid value for {string} to verify Error Message")
    public void i_enter_invalid_value_for_to_verify_Error_Message(String field) {
        String maxLength = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxyz";
        String actual = "";
        String value = "11";
        switch (field) {
            case "SSN":
                clearAndFillText(caseConsumerSrhPage.consumerSsn, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidSnnError.isDisplayed());
                break;
            case "DOB":
                clearAndFillText(caseConsumerSrhPage.ConsumerDOB, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidDOBError.isDisplayed());
                break;
            case "Address":
                clearAndFillText(caseConsumerSrhPage.consumerAddress, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidAddressError.isDisplayed());
                break;
            case "City":
                clearAndFillText(caseConsumerSrhPage.consumerCityZip, maxLength);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidCityError.isDisplayed());
                break;
            case "Zip":
                clearAndFillText(caseConsumerSrhPage.consumerCityZip, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidZipError.isDisplayed());
                break;
            case "Phone":
                clearAndFillText(caseConsumerSrhPage.consumerPhoneNumber, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidPhoneError.isDisplayed());
                break;
            case "Email":
                clearAndFillText(caseConsumerSrhPage.consumerEmail, value);
                caseConsumerSrhPage.searchButton.click();
                waitFor(1);
                Assert.assertTrue(caseConsumerSrhPage.invalidEmailError.isDisplayed());
                break;
        }
    }

    @When("I verify Search By IDs dropdown options are displayed")
    public void i_verify_Search_By_IDs_dropdown_options_are_displayed() {
        caseConsumerSrhPage.searchByDropdown.click();
        assertTrue(caseConsumerSrhPage.intConsumerID.isDisplayed());
        assertTrue(caseConsumerSrhPage.medCaseID.isDisplayed());
        assertTrue(caseConsumerSrhPage.intCaseID.isDisplayed());
    }

    @Then("I click on unmask button for {string} on search result")
    public void i_click_on_unmask_button_for_on_search_result(String value) {
        if(value.equals("SSN")) {
            caseConsumerSrhPage.SSNMaskBtnSearchParameter.click();
            waitFor(2);
        }else if(value.equals("DOB")){
            caseConsumerSrhPage.DOBMaskBtnSearchParameter.click();
            waitFor(2);
        }
    }

    @When("I verify {string} field is in {string} format")
    public void i_verify_field_is_in_format(String value, String format) {
        if(value.equals("SSN")) {
            assertTrue(caseConsumerSrhPage.SSNMaskedResult.isDisplayed());
            waitFor(2);
        }else if(value.equals("DOB")){
            assertTrue(caseConsumerSrhPage.DOBMaskedResult.isDisplayed());
        }
    }

    @When("I verify field headers after search are displayed")
    public void i_verify_field_headers_after_search_are_displayed() {
        waitFor(2);
        assertTrue(caseConsumerSrhPage.stateIDheader.isDisplayed());
        assertTrue(caseConsumerSrhPage.nameHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.dobHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.sourceHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.ssnHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.addressHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.phoneNumHeader.isDisplayed());
        assertTrue(caseConsumerSrhPage.emailHeader.isDisplayed());
    }

    @When("I search just created consumer by external consumer ID")
    public void I_search_just_created_consumer_by_external_consumerID(){
        waitFor(1);
        String externalConsumerID = APICreateConsumerContactController.getExternalconsumerId();
        expectedConsumerId.set(externalConsumerID);
        caseConsumerSrhPage.ConsumerDOB.click();
        caseConsumerSrhPage.externalID.sendKeys(externalConsumerID);
        jsClick(caseConsumerSrhPage.searchButton);
    }


    @Then("I validate the search result remain on search screen")
    public void iValidateTheSearchResultRemainOnSearchScreen() {
        assertTrue(manualCaseConsumerSearch.caseIDs.get(0).getText().equalsIgnoreCase(expectedConsumerId.get()));
    }

}