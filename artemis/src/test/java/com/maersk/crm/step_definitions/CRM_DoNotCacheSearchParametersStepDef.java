package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_DoNotCacheSearchParametersStepDef extends CRMUtilities implements ApiBase {

//    CRMUtilities crmUtils = new CRMUtilities();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMThirdPartyContactRecPage thirdPartyContact = new CRMThirdPartyContactRecPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactRecordDashboardPage contactRecord = new CRMContactRecordDashboardPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    CRMCaseContactDetailsPage caseContact = new CRMCaseContactDetailsPage();
    CRMTaskSearchPage taskSearch = new CRMTaskSearchPage();
    CRMCaseAndConsumerSearchPage caseConsumerSearch = new CRMCaseAndConsumerSearchPage();
    CRMContactHistoryPage contactHistory = new CRMContactHistoryPage();
    final ThreadLocal<Map<String, String>> newConsumer = new ThreadLocal<>();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    final ThreadLocal<String> contactTC03 = new ThreadLocal<>();
    @When("I populate All Search criteria fields for an Active Contact")
    public void i_populate_All_Search_criteria_fields_for_an_active_contact() {
        synchronized (newConsumer){
            newConsumer.set(new HashMap<>(getNewTestData2()));
        }
        clearAndFillText(activeContact.consumerFirstName, (newConsumer.get().get("name")));
        clearAndFillText(activeContact.consumerLastName, (newConsumer.get().get("surname")));
        clearAndFillText(activeContact.consumerSsn, (newConsumer.get().get("ssn")));
        clearAndFillText(activeContact.consumerDob, (newConsumer.get().get("birthday")));

        assertTrue(newConsumer.get().get("name").equalsIgnoreCase(activeContact.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
        assertTrue(newConsumer.get().get("surname").equalsIgnoreCase(activeContact.consumerLastName.getAttribute("value")), "Consumer last name is not correct");
        assertEquals(newConsumer.get().get("ssn").toString(),activeContact.consumerSsn.getAttribute("value").toString().replaceAll("-",""), "Consumer ssn is not correct");
        assertEquals(newConsumer.get().get("birthday"),activeContact.consumerDob.getAttribute("value").toString(), "Consumer dob is not correct");

    }

    @And("I click on Active Contact Search Button")
    public void i_click_on_active_contact_search_button(){
        try { fluentWait(activeContact.searchBtn,5);

        }catch(TimeoutException a){
            waitForClickablility(activeContact.searchBtn,5);
        }

        jsClick(activeContact.searchBtn);
        // waitForVisibility(activeContact.searchResultHeader, 3);
        // Assert.assertTrue(isElementDisplayed(activeContact.searchResultHeader));
    }

    @Then("I click on the Cancel Button") //(precaution as to my uncertainty over the meaning of 'Active Context')
    public void clickCancelButton(){
        activeContact.cancelBtn.click();

        Assert.assertFalse(isElementDisplayed(activeContact.searchResultHeader));
    }

    @Then("I click on Active Contact Reset Button")
    public void i_click_on_active_contact_reset_button(){
       jsClick(activeContact.resetBtn);


    }

    @And("I navigate away from Active Contact page to Third Party Contact page")
    public void i_navigate_away_from_active_contact_page_to_third_party_contact_page(){

        jsClick(activeContact.thirdPartyContactTab);
        waitForVisibility(thirdPartyContact.lblThirdPartyConsumerInContact, 3);
        Assert.assertTrue(isElementDisplayed(thirdPartyContact.lblThirdPartyConsumerInContact));
    }

    @When("I navigate from Third Party Contact page to Active Contact page")
    public void i_navigate_from_third_party_contact_page_to_active_contact_page(){
        jsClick(thirdPartyContact.generalContactTab);

    }

    @And("I enter the first three characters of the previously populated Active Contact parameters")
    public void i_enter_the_first_three_characters_of_the_previously_populated_active_contact_parameters(){
        activeContact.consumerFirstName.sendKeys(newConsumer.get().get("name").substring(0,3));
        activeContact.consumerLastName.sendKeys(newConsumer.get().get("surname").substring(0,3));
        activeContact.consumerDob.sendKeys(newConsumer.get().get("birthday").substring(0,3));
        activeContact.consumerSsn.sendKeys(newConsumer.get().get("ssn").substring(0,3));
    }



    @Then("I do not see auto-populated values of Active Contact and no search parameters are cached")
    public void i_do_not_see_autopopulated_values_of_Active_Contact_and_no_search_parameters_are_cached(){

        String testSsn = activeContact.consumerSsn.getAttribute("value").replaceAll("-","");
        testSsn = filterTextFor(testSsn);
        String testDob = activeContact.consumerDob.getAttribute("value").replaceAll("_","");
        testDob = filterTextFor(testDob);
        assertEquals(activeContact.consumerLastName.getAttribute("value"), newConsumer.get().get("surname").substring(0,3));
        assertEquals(activeContact.consumerFirstName.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        testDob = filterTextFor(testDob);
        assertEquals(testSsn.trim(),newConsumer.get().get("ssn").substring(0,3));
        assertEquals(testDob,filterTextFor(newConsumer.get().get("birthday").substring(0,3)));

    }

    @And("I click on Create Consumer cancel button")
    public void i_click_on_create_consumer_cancel_button(){
        activeContact.addConsumerCancelBtn.click();

    }
    @When("I populate the Create Consumer fields")
    public void i_populate_the_create_consumer_fields(){

        clearAndFillText(createConsumer.consumerCity, (newConsumer.get().get("name")+" City"));
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone")));
        clearAndFillText(createConsumer.consumerAddress1, (newConsumer.get().get("name")+ " Ct."));
        clearAndFillText(createConsumer.consumerAddress2, (newConsumer.get().get("name")+" St."));
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip")));
        clearAndFillText(createConsumer.consumerEmail, newConsumer.get().get("name")+ "@" + getRandomString(5) + ".com");


    }





    @Then("I do not see auto-populated values of Create Consumer fields and no search parameters are cached")
    public void i_do_not_see_autopopulated_values_of_create_consumer_fields_and_no_search_parameters_are_cached(){

        String testSsn = createConsumer.consumerSSN.getAttribute("value").replaceAll("-","");
        testSsn = filterTextFor(testSsn);
        String testDob = createConsumer.consumerDB.getAttribute("value").replaceAll("_","");
        testDob = filterTextFor(testDob);
        assertEquals(createConsumer.consumerLN.getAttribute("value"), newConsumer.get().get("surname").substring(0,3));
        assertEquals(createConsumer.consumerFN.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(testSsn.trim(),newConsumer.get().get("ssn").substring(0,3));
        assertEquals(testDob,filterTextFor(newConsumer.get().get("birthday").substring(0,3)));
        assertEquals(createConsumer.consumerCity.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(createConsumer.consumerEmail.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(createConsumer.consumerAddress2.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(createConsumer.consumerAddress1.getAttribute("value"), newConsumer.get().get("name").substring(0,3));

    }

    @When("I populate the free text parameters on Contact Record page")
    public void i_populate_the_free_text_parameters_on_contact_record_page(){
        clearAndFillText(contactRecord.contactRecordId, (newConsumer.get().get("phone")));
        selectDropDown(createConsumer.phoneType, "Home");
        selectRandomDropDownOption(createConsumer.consumerGender);
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
    }

    @And("I click on Case Consumer Search page")
    public void i_click_on_case_consumer_search_page(){
        //       System.out.println(Driver.getDriver()getTitle());
        waitFor(3);
        try {
            hover(dashboard.caseConsumerSideBarIcon);
            waitForClickablility(dashboard.case_ConsumerSearchTab, 5);
            jsClick(dashboard.case_ConsumerSearchTab);
        }catch(NullPointerException a){
            try {
                Robot ur = new Robot();
                ur.keyPress(KeyEvent.VK_CONTROL);
                ur.keyPress(KeyEvent.VK_R);
                ur.keyPress(KeyEvent.VK_R);
                ur.keyPress(KeyEvent.VK_CONTROL);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            fluentWait(dashboard.case_ConsumerSearchTab,5);
            click(dashboard.case_ConsumerSearchTab);
        }
        assertTrue(caseConsumerSearch.consumerFirstName.isDisplayed());
        caseConsumerSearch.consumerLastName.click();
        waitFor(1);

    }

    @And("I populate the free text parameters on Case Consumer Search page")
    public void i_populate_the_free_text_parameters_on_case_consumer_search_page(){
        synchronized (newConsumer){
            newConsumer.set(new HashMap<>(getNewTestData2()));
        }

        clearAndFillText(caseConsumerSearch.consumerLastName, (newConsumer.get().get("surname")));
        clearAndFillText(caseConsumerSearch.consumerSsn, (newConsumer.get().get("ssn")));
        clearAndFillText(caseConsumerSearch.consumerDob, (newConsumer.get().get("birthday")));
        clearAndFillText(caseConsumerSearch.consumerFirstName, (newConsumer.get().get("name")));
    }

    @And("I click the Case Consumer search button")
    public void i_click_the_case_consumer_search_button(){
        caseContact.searchBtn.click();

    }

    @And("I click on the Contact Record Search Tab")
    public void i_click_on_the_contact_record_search_tab(){
        hover(caseContact.contactRecordSearchSidebarIcon);
        caseContact.contactRecordSearchTab.click();
    }

    @And("I click on the Case Consumer cancel button")
    public void i_click_on_the_case_consumer_cancel_button(){
        caseContact.cancelBtn.click();
    }

    @When("I populate the free text parameters on Task Search page")
    public void i_populate_the_free_text_parameters_on_task_search_page(){
        synchronized (newConsumer){
            newConsumer.set(new HashMap<>(getNewTestData2()));
        }
        WebDriverWait webDriverWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(taskSearch.statusDate));
        clearAndFillText(taskSearch.statusDate,(newConsumer.get().get("birthday")));
        clearAndFillText(taskSearch.dueDate,(newConsumer.get().get("birthday")));
        clearAndFillText(taskSearch.taskId,(newConsumer.get().get("phone")));
        clearAndFillText(taskSearch.caseId,(newConsumer.get().get("phone")));
        clearAndFillText(taskSearch.taskConsumerId,(newConsumer.get().get("phone")));
        jsClick(taskSearch.savedSearchArrow);
        jsClick(taskSearch.randonSavedSearch);

    }

    @And("I click the task search button")
    public void i_click_the_task_search_button(){
        jsClick(taskSearch.searchBtn);

    }

    @And("I click the task cancel button")
    public void i_click_the_task_cancel_button(){
        taskSearch.cancelBtn.click();

    }

    @Then("the task search free text fields have been cleared")
    public void the_task_search_free_text_fields_have_been_cleared(){
        assertEquals(taskSearch.taskId.getAttribute("value"), "");
        assertEquals(taskSearch.caseId.getAttribute("value"), "");
        assertEquals(taskSearch.consumerId.getAttribute("value"), "");
    }

    @And("I enter the first three characters of the previously populated Case Consumer search parameters")
    public void i_enter_the_first_three_characters_of_the_previously_populated_case_consumer_search_parameters(){
        caseContact.consumerFirstName.sendKeys(newConsumer.get().get("name").substring(0,3));
        caseContact.consumerLastName.sendKeys(newConsumer.get().get("surname").substring(0,3));
        caseContact.consumerSsn.sendKeys(newConsumer.get().get("ssn").substring(0,3));
        caseContact.consumerDob.sendKeys(newConsumer.get().get("birthday").substring(0,3));


    }

    @Then("I do not see auto-populated values of Case Consumer fields and no search parameters are cached")
    public void i_do_not_see_autopopulated_values_of_case_consumer_fields_and_no_search_parameters_are_cached(){

        String testSsn = caseContact.consumerSsn.getAttribute("value").replaceAll("-","");
        testSsn = filterTextFor(testSsn);
        String testDob = caseContact.consumerDob.getAttribute("value").replaceAll("_","");
        testDob = filterTextFor(testDob);
        assertEquals(caseContact.consumerFirstName.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(caseContact.consumerLastName.getAttribute("value"), newConsumer.get().get("surname").substring(0,3));
        assertEquals(testSsn.trim(),newConsumer.get().get("ssn").substring(0,3));
        assertEquals(testDob,filterTextFor(newConsumer.get().get("birthday").substring(0,3)));

    }


    @When("I populate the free text parameteres on Contact Record page")
    public void iPopulateTheFreeTextParameteresOnContactRecordPage() {
        synchronized (newConsumer){
            newConsumer.set(new HashMap<>(getNewTestData2()));
        }

        WebDriverWait webDriverWait = new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(12));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(contactRecord.consumerLN));
        clearAndFillText(contactRecord.consumerLN, newConsumer.get().get("surname"));
        clearAndFillText(contactRecord.consumerFN, newConsumer.get().get("name"));
        clearAndFillText(contactRecord.recordId,(newConsumer.get().get("phone")));
        clearAndFillText(contactRecord.recordDate, newConsumer.get().get("birthday"));
        clearAndFillText(contactRecord.recordCaseId, newConsumer.get().get("phone"));
        clearAndFillText(contactRecord.recordConsumerId, newConsumer.get().get("phone"));
    }

    @And("I click search button on Contact Record Search page")
    public void iClickSearchButtonOnContactRecordSearchPage() {
        contactRecord.searchBtn.click();
    }

    @And("I click cancel button on Contact Records Search page")
    public void iClickCancelButtonOnContactRecordsSearchPage() {

        contactRecord.cancelBtn.click();
    }

    @And("I navigate to the Case Consumer Search page")
    public void iNavigateToTheCaseConsumerSearchPage() {
        hover(contactRecord.caseConsumerSidebarIcon);
        jsClick(contactRecord.caseConsumerSidebarIcon);

    }

    @And("I enter the first three characters of the previously populated Contact Record search parameters")
    public void iEnterTheFirstThreeCharactersOfThePreviouslyPopulatedContactRecordSearchParameters() {
        contactRecord.recordId.sendKeys(newConsumer.get().get("phone").substring(0,3));
        contactRecord.consumerFN.sendKeys(newConsumer.get().get("name").substring(0,3));
        contactRecord.consumerLN.sendKeys(newConsumer.get().get("surname").substring(0,3));
        contactRecord.recordDate.sendKeys(newConsumer.get().get("birthday").substring(0,3));
        contactRecord.recordCaseId.sendKeys(newConsumer.get().get("phone").substring(0,3));
        contactRecord.recordConsumerId.sendKeys(newConsumer.get().get("phone").substring(0,3));

    }

    @Then("I do not see auto-populated values of Contact Record fields and no search parameters are cached")
    public void iDoNotSeeAutoPopulatedValuesOfContactRecordFieldsAndNoSearchParametersAreCached() {
        String testDob = contactRecord.recordDate.getAttribute("value").replaceAll("_","");
        testDob = filterTextFor(testDob);
        assertEquals(contactRecord.consumerFN.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(contactRecord.consumerLN.getAttribute("value"), newConsumer.get().get("surname").substring(0,3));
        assertEquals(contactRecord.recordId.getAttribute("value"), newConsumer.get().get("phone").substring(0,3));
        assertEquals(contactRecord.recordCaseId.getAttribute("value"), newConsumer.get().get("phone").substring(0,3));
        assertEquals(contactRecord.recordConsumerId.getAttribute("value"), newConsumer.get().get("phone").substring(0,3));
        assertEquals(testDob,filterTextFor(newConsumer.get().get("birthday").substring(0,3)));


    }

    @When("I navigate away from Task Search to Contact Record Search")
    public void iNavigateAwayFromTaskSearchToContactRecordSearch() {
        hover(taskSearch.contactRecorcSideBarIcon);
        click(taskSearch.contactRecordTab);

    }

    @And("I enter the first three characters of the previously populated Task search parameters")
    public void iEnterTheFirstThreeCharactersOfThePreviouslyPopulatedTaskSearchParameters() {
        taskSearch.taskId.sendKeys((newConsumer.get().get("phone").substring(0,3)));
        taskSearch.statusDate.sendKeys((newConsumer.get().get("birthday").substring(0,3)));
        taskSearch.dueDate.sendKeys((newConsumer.get().get("birthday").substring(0,3)));
        taskSearch.caseId.sendKeys((newConsumer.get().get("phone").substring(0,3)));
        taskSearch.taskConsumerId.sendKeys((newConsumer.get().get("phone").substring(0,3)));
    }

    @Then("I do not see auto-populated values of Task search fields and no search parameters are cached")
    public void iDoNotSeeAutoPopulatedValuesOfTaskSearchFieldsAndNoSearchParametersAreCached() {
        String testDueDate = taskSearch.dueDate.getAttribute("value").replaceAll("_","");
        testDueDate = filterTextFor(testDueDate);
        String testStatusDate = taskSearch.statusDate.getAttribute("value").replaceAll("_","");
        testStatusDate = filterTextFor(testStatusDate);

        assertEquals(taskSearch.taskId.getAttribute("value"), newConsumer.get().get("phone").substring(0,3));
        assertEquals(taskSearch.caseId.getAttribute("value"), newConsumer.get().get("phone").substring(0,3));
        assertEquals(testDueDate,filterTextFor(newConsumer.get().get("birthday").substring(0,3)));
        assertEquals(testStatusDate, filterTextFor(newConsumer.get().get("birthday").substring(0,3)));

    }

    @And("I populate the task search name")
    public void iPopulateTheTaskSearchName() {

        clearAndFillText(taskSearch.searchName, newConsumer.get().get("name"));
    }

    @And("I click cancel on search name")
    public void iClickCancelOnSearchName() {
        click(taskSearch.saveTaskSearchCancelBtn);
    }

    @And("I clear the task search Warning Message")
    public void iClearTheTaskSearchWarningMessage() {
        try{
            taskSearch.warningMsg.isDisplayed();
            click(taskSearch.warningMsgContinueBtn);
        }catch(NoSuchElementException a){
            assertFalse(taskSearch.warningMsg.isDisplayed(), "Warning Message Missing");
        }
    }

    @And("I navigate to the task search page")
    public void iNavigateToTheTaskSearchPage() {
        waitFor(3);
        hover(dashboard.taskSidebarIcon);
        waitFor(3);
        jsClick(dashboard.taskSidebarIcon);
        waitFor(3);
        jsClick(dashboard.taskSearchTab);
    }

    @And("I populate the remaining Create Consumer fields with the first three characters of the previous parameters")
    public void iPopulateTheRemainingCreateConsumerFieldsWithTheFirstThreeCharactersOfThePreviousParameters() {
        createConsumer.consumerDB.sendKeys(newConsumer.get().get("birthday").substring(0,3));
        createConsumer.consumerSSN.sendKeys(newConsumer.get().get("ssn").substring(0,3));
        createConsumer.consumerCity.sendKeys((newConsumer.get().get("name").substring(0,3)));
        createConsumer.consumerPhoneNum.sendKeys((newConsumer.get().get("phone").substring(0,3)));
        createConsumer.consumerAddress1.sendKeys((newConsumer.get().get("name").substring(0,3)));
        createConsumer.consumerAddress2.sendKeys((newConsumer.get().get("name").substring(0,3)));
        createConsumer.consumerZipCode.sendKeys((newConsumer.get().get("zip").substring(0,3)));
        createConsumer.consumerEmail.sendKeys((newConsumer.get().get("name").substring(0,3)));


    }

    @And("I enter the first three characters of the previously populated Active Contact Name parameters")
    public void iEnterTheFirstThreeCharactersOfThePreviouslyPopulatedActiveContactNameParameters() {
        activeContact.consumerFirstName.sendKeys(newConsumer.get().get("name").substring(0,3));
        activeContact.consumerLastName.sendKeys(newConsumer.get().get("surname").substring(0,3));
    }


    @And("I populate the required Create Consumer dropdown fields")
    public void iPopulateTheRequiredCreateConsumerDropdownFields() {

    }

    @And("I enter Contact Reason Comment")
    public void iEnterContactReasonComment() {
        jsClick(createConsumer.expendReasonComment);
        //jsClick(createConsumer.phoneInput);
        jsClick(createConsumer.reasonComment);

        createConsumer.reasonComment.sendKeys(newConsumer.get().get("birthday"));
        waitFor(2);
        jsClick(createConsumer.contactReasonCommentSaveBtn);
    }

    @And("I enter Additional Comment")
    public void iEnterAdditionalComment() {

            jsClick(createConsumer.additionalReasonComment);
           createConsumer.additionalReasonComment.sendKeys(newConsumer.get().get("phone"));
        waitFor(2);
           click(createConsumer.additionalCommentsSaveBtn);

       }

    @And("I populate the free text fields on the Edit Contact Record page")
    public void iPopulateTheFreeTextFieldsOnTheEditContactRecordPage() {
       waitFor(2);
        contactHistory.firstCommentBx.sendKeys(newConsumer.get().get("name"));
        contactHistory.secondCommentBx.sendKeys(newConsumer.get().get("surname"));

        System.out.println(contactHistory.firstCommentBx.getAttribute("value"));
        System.out.println(contactHistory.secondCommentBx.getAttribute("value"));

        click(contactHistory.cancelCommentBtn);
        waitForClickablility(contactHistory.continueBtnPop,2);
        hover(contactHistory.continueBtnPop);
        jsClick(contactHistory.continueBtnPop);
        waitFor(2);
        click(contactHistory.cancelAdditionalCommentBtn);
        waitForClickablility(contactHistory.continueBtnPop,2);
        hover(contactHistory.continueBtnPop);
        jsClick(contactHistory.continueBtnPop);


    }


    @And("I navigate to the Edit History page")
    public void iNavigateToTheEditHistoryPage() {
        jsClick(contactHistory.editHistoryTab);
        assertTrue(contactHistory.editHistoryHeader.isDisplayed());
    }


    @And("I navigate to Contact Details")
    public void iNavigateToContactDetails() {
        jsClick(contactHistory.contactDetailsTab);
        assertTrue(contactHistory.contactRecordInfoHeader.isDisplayed());

    }

    @And("I enter the first three characters of the previously populated Edit Contact Record parameters")
    public void iEnterTheFirstThreeCharactersOfThePreviouslyPopulatedEditContactRecordParameters() {
        contactHistory.firstCommentBx.sendKeys(newConsumer.get().get("name").substring(0,3));
        contactHistory.secondCommentBx.sendKeys(newConsumer.get().get("surname").substring(0,3));

    }

    @Then("I do not see auto-populated values of Edit Contact Record fields and no search parameters are cached")
    public void iDoNotSeeAutoPopulatedValuesOfEditContactRecordFieldsAndNoSearchParametersAreCached() {
        assertEquals(contactHistory.firstCommentBx.getAttribute("value"), newConsumer.get().get("name").substring(0,3));
        assertEquals(contactHistory.secondCommentBx.getAttribute("value"), newConsumer.get().get("surname").substring(0,3));
    }


    @And("I populate the Contact Details")
    public void iPopulateTheContactDetails() {
        waitFor(3);
       createConsumer.phoneInput.sendKeys(newConsumer.get().get("phone"));
       selectRandomDropDownOption(activeContact.programTypes);
       synchronized (contactTC03){
           contactTC03.set(activeContact.consumerInContactId.getText());
       }
      scrollToTop();
       waitFor(2);

    }

    @When("I populate all the Create Consumer fields")
    public void iPopulateAllTheCreateConsumerFields() {
        clearAndFillText(createConsumer.consumerFN, newConsumer.get().get("name").toString());
        clearAndFillText(createConsumer.consumerLN, newConsumer.get().get("surname").toString());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        //        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        //        selectRandomDropDownOption(createConsumer.consumerAddressType);
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(3);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));
    }

    @When("I search for created consumer")
    public void iSearchForCreatedConsumer() {
        manualContactRecordSearchPage.manualSearchInternalConsumerId.sendKeys(contactTC03.get());
        activeContact.searchBtn.click();
    }

    @And("I link the result to an existing contact")
    public void iLinkTheResultToAnExistingContact(){
        waitFor(2);
        try {
            scrollToElement(activeContact.firstContactBtn);
            jsClick(activeContact.crmConsumerID1);
        }catch(NoSuchElementException j){
            jsClick(activeContact.crmConsumerID1);
            assertTrue(activeContact.firstContactBtn.isDisplayed());
        }
        if(contactTC03.get().equalsIgnoreCase(activeContact.consumerIdResult.getText())){
            waitFor(2);
            jsClick(activeContact.firstContactBtn);
            jsClick(activeContact.nameConsumer1);
            jsClick(activeContact.dobConsumer1);
         //   jsClick(activeContact.ssnConsumer1);  we didnt have to use ssn, unless we provide ssn when creating consumer
            jsClick(activeContact.crmConsumerID1);
            jsClick(activeContact.linkRecordBtn);
        }
    }

    @And("I click on the first contact id")
    public void iClickOnTheFirstContactId() {
        jsClick(contactHistory.firstContactId);
    }

    @And("I click on the first contact id on contact details page")
    public void iClickOnTheFirstContactId2() {
        jsClick(contactHistory.firstContactIdOnContactDetailsPage);
    }
}


