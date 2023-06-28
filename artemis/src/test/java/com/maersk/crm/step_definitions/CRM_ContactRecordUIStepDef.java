package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.pages.OutBoundCorrespondenceDestiPage;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.*;

//import static com.maersk.crm.utilities.ApiBaseClass.contactRecord;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.testng.Assert.*;

public class CRM_ContactRecordUIStepDef extends CRMUtilities implements ApiBase, UiBase {
//    CRMUtilities crmUtils = new CRMUtilities();

    //    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
//    CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    CRMDashboardPage dashboard = new CRMDashboardPage();
    //    CRMUnidentifiedContactPage unidentifiedContact = new CRMUnidentifiedContactPage();
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();
    //    CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();
    CRMManualCaseConsumerSearchPage caseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();
    LoginPage loginPage = new LoginPage();
    //    CRMCreateGeneralTaskPage createGeneralTaskPage = new CRMCreateGeneralTaskPage();
    CRM_TaskManagementStepDef taskManagementStepDef = new CRM_TaskManagementStepDef();
    //    CRM_CreateConsumerProfileStepDef createConsumerProfileStepDef = new CRM_CreateConsumerProfileStepDef();
//    CRMConsumerSearchResultPage consumerSearchResult = new CRMConsumerSearchResultPage();
//    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
//    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    final ThreadLocal<Map<String, String>> newConsumer = new ThreadLocal<>();
    public String userNameData = ConfigurationReader.getProperty("login");
    public String CallCenterSupervisor_Mars_Tester3_login = ConfigurationReader.getProperty("svcTester3");
    public String password = ConfigurationReader.getProperty("password");
    public String userNameData1 = ConfigurationReader.getProperty("additionalLogin");
    //public String projectName = ConfigurationReader.getProperty("projectName");
    public static final ThreadLocal<String> projectName = new ThreadLocal<>();
    private final ThreadLocal<ReadDataFromForm> readDataFromForm = ThreadLocal.withInitial(ReadDataFromForm::new);
    public static final ThreadLocal<String> caseId = new ThreadLocal<>();
    public static final ThreadLocal<String> consumerId = new ThreadLocal<>();
    int firstWaitTime = 3;
    public static final ThreadLocal<String> expectedConsumerName = new ThreadLocal<>();
    OutBoundCorrespondenceDestiPage CorresPage = new OutBoundCorrespondenceDestiPage();
    private String csrUser6 = ConfigurationReader.getProperty("CSRLogin");
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    CRMWOrkQueuePage workQueue = new CRMWOrkQueuePage();
    private final ThreadLocal<String> taskID = new ThreadLocal<>();
    public static final ThreadLocal<String> contactPhoneNumber = new ThreadLocal<>();
    private CreateOutboundCorrespondencePage crOutCor = new CreateOutboundCorrespondencePage();
    Actions actions = new Actions(Driver.getDriver());
    public static final ThreadLocal<String[]> programs = new ThreadLocal<>();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    private CRMCreateConsumerProfilePage crmCreateConsumerProfilePage = new CRMCreateConsumerProfilePage();
    private CRMContactRecordUIPage crmContactRecordUIPage1 = new CRMContactRecordUIPage();
    private CRMActiveContactPage crmActiveContactPage = new CRMActiveContactPage();
    private CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();


//    String comments = getCellValueBySheetRowAndColoumn("Reasons", "Valid ", "Comments");
/*
3202 test cases
 */

    @Given("I logged into CRM and click on initiate contact")
    public void i_logged_into_CRM_and_click_on_initiate_contact() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 10);
        jsClick(crmContactRecordUIPage1.initContact);
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
    }

    @Given("I logged into CRM with Call Center Supervisor account and click on initiate contact")
    public void i_logged_into_CRM_with_Call_Center_Supervisor_account_and_click_on_initiate_contact() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(CallCenterSupervisor_Mars_Tester3_login, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 10);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
    }

    @Given("I logged into CRM with service account 2 and click on initiate contact")
    public void i_logged_into_CRM_with_service_account2_and_click_on_initiate_contact() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData1, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 10);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());

    }

    @Given("I enter {string} and click search")
    public void EnterCaseid(String caseId) throws InterruptedException {
        CorresPage.CaseID.sendKeys(caseId);
        CorresPage.searchBtn.click();

        System.out.println("this is page title" + Driver.getDriver().getCurrentUrl());
    }

    @Given("I verify casenumber click and expend")
    public void confirmFirstConsumerWithCaseNumber() throws InterruptedException {
        waitFor(5);
        String caseNumber = CorresPage.MatchCaseId.getText();
        Assert.assertEquals(caseNumber, "488");
        waitFor(5);
        List<WebElement> li = CorresPage.ExpandArrow;
        //  List<WebElement> ls= Driver.getDriver().findElements(By.xpath("//*[contains(text(), 'chevron_right')]"));
        li.get(0).click();
        System.out.println(caseNumber);
        waitFor(5);

    }

    @Given("I create an Outbound Correspondence")
    public void createOutBoundCorrespondence() {
        waitFor(8);
        CorresPage.menuListBtn.click();
        waitFor(3);
        CorresPage.createCorrespondenceBtn.click();
        System.out.println("page title" + CorresPage.getPageTitle());
        waitForPageToLoad(10);
        waitFor(10);
        Assert.assertEquals(CorresPage.getPageTitle(), "Create Outbound Correspondence Request");
        System.out.println("success at creating correspondence!");
        waitFor(30);
    }

    @Given("I choose correspondence type")
    public void iChooseCorrespondenceType() {
        waitFor(3);
        selectDropDown(CorresPage.selectCorrespondeType, "Case id is required - 010200");
        System.out.println("success at creating selecting correspondence type!");
    }


    @Given("I scroll down and check Woods Blanche")
    public void iScrollDownAndCheckWoodsBlanche() {
        CorresPage.checkConsumer.click();
        System.out.println("success at Check Consumer!");
    }

    @Given("I scroll down and check the recipient by consumerId - {string}")
    public void checkRecipient(String consumerId) {
        CorresPage.getRecipient(consumerId).click();
        System.out.println("success at Check Consumer!");
    }

    @Then("I select Text opt in check box click other")
    public void iSelectTextOptInCheckBox() throws Exception {
        waitFor(2);
        List<WebElement> ltext = CorresPage.CheckText;
        ltext.get(2).click();
        waitFor(1);
        List<WebElement> list = CorresPage.OpenTextList;
        list.get(2).click();
        waitFor(2);
        CorresPage.SelectOther.click();


    }


    @Then("I enter valid phoneNumber {string}")
    public void iEnterValidPhoneNumber(String phoneNumber) {
        WebElement phoneNumberfiled = CorresPage.phoneNumber;
        jsClick(phoneNumberfiled);

        waitFor(2);

        clearAndFillText(phoneNumberfiled, phoneNumber);


    }


    @Then("I select mail opt in checkbox click other")
    public void iSelectMailOptInCheckbox() {
        List<WebElement> lmail = CorresPage.CheckMail;
        lmail.get(2).click();
        scrollDown();
        waitFor(3);
        List<WebElement> list = CorresPage.OpenMailList;
        list.get(2).click();
        waitFor(3);
        CorresPage.SelectOther.click();
        //  CorresPage.CheckMail.click();
        waitFor(2);
        //  selectDropDown(CorresPage.OtherMail, "other");
    }

    @Then("I verify by entering valid data in the address field {string} at Correspondence")
    public void iVerifyByEnteringValidDataInTheAddressFieldAtCorrespondence(String address1) {
        clearAndFillText(CorresPage.addressLineOne, address1);

    }

    @Then("I verify by entering valid data in the address field 2 {string} at Correspondence")
    public void EnterAddressTwo(String address2) {
        if(address2.equalsIgnoreCase("empty")){
            address2 = "";
        }
        clearAndFillText(CorresPage.addressLineTwo, address2);
    }

    @Then("I verify by entering valid data in the City field {string} at Correspondence")
    public void enterCityName(String city) throws InterruptedException {

       /* CorresPage.OpenCityList.click();
        waitFor(5);
        CorresPage.selectCity(city);*/

        clearAndFillText(CorresPage.City, city);

    }

    @Then("I verify by entering valid data in the state field {string} at Correspondence")
    public void enterState(String state) throws InterruptedException {
        waitFor(5);
        CorresPage.OpenStateList.click();
        waitFor(5);
        CorresPage.SelectState(state);
    }


    @Then("I verify by entering valid data in the zip code {string} at Correspondence")
    public void enterZipcode(String zipcode) throws InterruptedException {
       /* //   selectDropDown(CorresPage.ZipCode, zipcode);
        waitFor(5);
        CorresPage.OpenZipList.click();
        waitFor(5);
        // CorresPage.SelectZip.click();
        System.out.println(zipcode);
        CorresPage.selectZipCode(zipcode);
*/
        clearAndFillText(CorresPage.ZipCode, zipcode);

    }


    @Then("I select email in check Box click other")
    public void iSelectEmailInCheckBox() {
        waitFor(2);
        List<WebElement> leml = CorresPage.CheckEmail;
        leml.get(2).click();
        waitFor(1);
        List<WebElement> list = CorresPage.OpenEmailList;
        list.get(2).click();
        waitFor(5);
        CorresPage.SelectOther.click();


    }


    @Then("I verify by entering valid {string}")
    public void iVerifyWithEmailAddress(String emailaddress) {
        waitFor(10);
        clearAndFillText(CorresPage.Emailfield, emailaddress);

    }

    @Then("I select Fax in check box, select other")
    public void iSelectFaxInCheckBox() {
        waitFor(2);
        List<WebElement> lfax = CorresPage.CheckFax;
        lfax.get(2).click();

        List<WebElement> list = CorresPage.OpenFaxList;
        list.get(2).click();
        CorresPage.SelectOther.click();


    }


    @Then("I verify by entering valid data in the Fax filed {string} at Correspondence")
    public void enterValidFaxNumber(String faxNumber) {
        waitFor(3);
        clearAndFillText(CorresPage.FaxField, faxNumber);


    }

    //message alert verifications

    @Then("I verify Successfully saved OutboundCorrespondence")
    public void iVerifySuccess() {
        //    waitForVisibility(CorresPage.SaveButton,10);
        waitFor(10);
        // List<WebElement> save = Driver.getDriver().findElements(By.xpath("//span[contains(@class, 'MuiButton-label')"));
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String message = CorresPage.SuccessMessage.getText();
        System.out.println("success Message : " + message);
        // Assert.assertEquals("thecorrespondencerecordssuccesfullysaved", CorresPage.SuccessMessageWithMessage.getText().toLowerCase().replaceAll("[ ()0-9]", ""));
        Assert.assertEquals("SUCCESS MESSAGE", message);

    }

    @Then("I verify error is displayed for the Address Line one  as I save")
    public void VerifyErrorMessage() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String ErrorMessage = CorresPage.ErrorMessageForAddressOne.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        System.out.println("errormessage :   " + ErrorMessage);
        Assert.assertEquals("addresslineisrequiredandcannotbeleftblank", ErrorMessage);
        //ADDRESS LINE 1 is required and cannot be left blank
    }


    @Then("I try to save but expect to get error message on city filed as I save")
    public void VerifyCityErrorMessage() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorMsg = CorresPage.ErrorMessageForcity.getText();
        System.out.println("error message is + :" + errorMsg);
        Assert.assertEquals("CITY is required and cannot be left blank", errorMsg);
    }

    @Then("I expect to get error message on state filed")
    public void verfiyStateErrorMessage() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorStateMsg = CorresPage.ErrorMessageForState.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        System.out.println("error message:    " + errorStateMsg);
        Assert.assertEquals("stateisrequiredandcannotbeleftblank", errorStateMsg);
    }

    @Then("I expect to get error message on on Zipcode as I save")
    public void verifyZipErrorMessage() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorZipMessage = CorresPage.ErrorMessageForZipCode.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        System.out.println("actualMessage :  " + errorZipMessage);
        Assert.assertEquals("zipcodeisrequiredandcannotbeleftblank", errorZipMessage);
    }

    @Then("I expect to get error message on invalid Zipcode format as I save")
    public void verifyZipCodeformatErrorMessage() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorZipMessage = CorresPage.ErrorMessageForZipCodeInvalidformat.getText();
        System.out.println("actualMessage :  " + errorZipMessage);
        Assert.assertEquals("ZIP CODE must be 5 or 9 characters", errorZipMessage);
    }

    @Then("I expect to get error message on emailfiled as I save")
    public void verifyEmailAddressBlankMessage() {
        String errorEmail = CorresPage.ErrorMessageForEmail.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        Assert.assertEquals("EMAILisrequiredandcannotbeleftblank", errorEmail);

    }


    @Then("I expect to get blank email format message on emailfield as I save")
    public void verifyblankEmailForMat() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorFormat = CorresPage.ErrorMessageForWrongFormat.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        System.out.println("error message :    " + errorFormat);
        Assert.assertEquals("emailisrequiredandcannotbeleftblank", errorFormat);

    }


    @Then("I expect to get wrong email format message on emailfield as I save")
    public void verifyWrongEmailForMat() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorWrongFormat = CorresPage.ErrorMessageForWrongFormat.getText().toLowerCase().replaceAll("[ ()0-9]", "");
        System.out.println("error message :    " + errorWrongFormat);
        Assert.assertEquals("emailisnotinthecorrectformat", errorWrongFormat);

    }

    @Then("I expect to get error message on Text Channel as I save")
    public void verifyErrorMessageBlankTextField() {
        waitFor(10);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorMessageText = CorresPage.ErrorMessageForTextField.getText();
        System.out.println("error message:     " + errorMessageText);
        Assert.assertEquals("PHONE is required and cannot be left blank", errorMessageText);

    }

    @Then("I expect to get error message on Fax field as I save")
    public void verifyErrorFax() {
        waitFor(5);
        WebElement element = CorresPage.SaveButton;
        jsClick(element);
        String errorMessageFax = CorresPage.ErrorMessageForFaxNumber.getText();
        System.out.println("testmessage!21213  :  " + errorMessageFax);
        Assert.assertEquals("PHONE is required and cannot be left blank", errorMessageFax);

    }


    /*

    above the create correspondence by Sean Thorson
     */


    @Given("I verify that Start Time Icon is displayed")
    public void i_verify_that_Start_Time_Icon_is_displayed() {
        assertTrue(crmContactRecordUIPage1.contactStart.isDisplayed());
    }

    @Given("I verify that Duration Icon is displayed")
    public void i_verify_that_Duration_Icon_is_displayed() {
        assertTrue(crmContactRecordUIPage1.contactDurationValue.isDisplayed());
    }


    @Given("I logged into CRM with {string} and click on initiate contact")
    public void i_logged_into_CRM_with_and_click_on_initiate_contact(String sa4) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(sa4, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 10);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
    }


    /**
     * this method verifies the system provides the local time in <dd/mm/yyyy> format
     */
    @When("I verify Contact Start Date is captured")
    public void i_verify_Contact_Start_Date_is_captured() {
        waitForVisibility(crmContactRecordUIPage1.headerDate, 2);
        String actualDate = crmContactRecordUIPage1.headerDate.getText();
        String expectedDate = getCurrentDate();
        System.out.println(actualDate);
        System.out.println(expectedDate);
        assertTrue(actualDate.equals(expectedDate));
    }


    /**
     * this method verifies the system provides the local time in <hh:mm a> format
     */
    @Then("I verify Contact Start Time is captured")
    public void i_verify_Contact_Start_Time_is_captured() {
        waitForVisibility(crmContactRecordUIPage1.contactStart, 5);
        String actualTime = crmContactRecordUIPage1.contactStart.getText().substring(9);
        waitFor(firstWaitTime);
        String expectedTime = crmContactRecordUIPage1.headerTime.getText();
        assertTrue(expectedTime.contains(actualTime));
    }

    @Then("I verify Contact Duration has no value while in progress")
    public void i_verify_Contact_Duration_has_no_value_while_in_progress() {
        String progressDuration = crmContactRecordUIPage1.contactDurationValue.getText().substring(9);
        assertTrue(progressDuration.length() == 8, "Call Duration has incorrect value ");

    }

    @Then("I verify Contact Duration Time is captured")
    public void i_verify_Contact_Duration_Time_is_captured() {
        int callDuration = 5;
        int expectedWaitTime = 1;
        String expectedDuration = "";
        waitFor(callDuration);
        crmContactRecordUIPage1.stopContact.click();
//        waitFor(expectedWaitTime);

        String actualDuration = crmContactRecordUIPage1.contactDurationValue.getText();
        expectedDuration = firstWaitTime + callDuration + expectedWaitTime + "";
        System.out.println(expectedDuration);
        System.out.println(actualDuration);
        assertTrue(actualDuration.endsWith(expectedDuration));


    }

    @Given("I logged into CRM with {string}, {string} and {string}")
    public void i_logged_into_CRM_with_and(String userNameData, String password, String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData, password, projectName);
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);

    }

    @When("I click on initiate contact record")
    public void clickOnInitiateContactRecord() {
        waitFor(5);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
        waitFor(1);
    }

    @When("I click on initiate contact recordNew")
    public void clickOnInitiateContactRecordNew() {
        waitFor(3);
        crmContactRecordUIPage1.initContact.click();
        waitFor(1);
    }

    @Then("I verify the mandatory fields for contact initiation")
    public void i_verify_the_mandatory_fields_for_contact_initiation() {
//todo check on implementation to be done
    }

    @Given("I search for an existing contact by criteria")
    public void i_search_for_an_existing_contact_by_criteria() {
        crmContactRecordUIPage1.firstName.sendKeys("Ethan"); //refactoring
        crmContactRecordUIPage1.searchButton.click();
        waitFor(5);
        assertTrue(crmContactRecordUIPage1.searchResultSign.isDisplayed());

    }

    /**
     * This method links a consumer to an existing Case or Consumer profile
     * Modified by Shruti to make method reuasble for the contact record types
     * orginal method, can be deleted while review
     * Added scroll and wait --aswath-04042019
     **/
    @Then("I link the contact to an existing Case or Consumer Profile")
    public void i_link_the_contact_to_an_existing_Case_or_Consumer_Profile() throws InterruptedException {
        waitForVisibility(crmContactRecordUIPage1.expandFistConsumer, 10);
        waitFor(2);
        expectedConsumerName.set(crmContactRecordUIPage1.getExpectedName());
        waitFor(4);
        click(crmContactRecordUIPage1.expandFistConsumer);
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.caseIdFirstRecord, 2);
        synchronized (caseId) {
            caseId.set(crmContactRecordUIPage1.caseIdFirstRecord.getText());
        }
        synchronized (consumerId) {
            consumerId.set(crmContactRecordUIPage1.consumerIdLnk.getText());
        }
        waitForVisibility(crmContactRecordUIPage1.caseIdFirstRecord, 2);
        synchronized (CRM_GeneralTaskStepDef.externalCaseID) {
            CRM_GeneralTaskStepDef.externalCaseID.set(crmContactRecordUIPage1.caseIdFirstRecord.getText());
        }
        synchronized (CRM_GeneralTaskStepDef.externalConsumerID) {
            CRM_GeneralTaskStepDef.externalConsumerID.set(crmContactRecordUIPage1.consumerIdLnk.getText());
        }
        System.out.println(caseId.get() + expectedConsumerName.get());

        //if 3rd party
        if (thirdPartyDetails.thirdPartyRecordTypeRadio.getAttribute("class").contains("active")) {
            //if consumer
            if (caseId.get().equals("--") || (!crmActiveContactPage.caseId.isDisplayed() == true)) {
                //click on link button
            }
            //if case
            else {
                crmContactRecordUIPage1.lstFirstConsumerNameRadioButton.click();
                waitFor(2);
                scrollDown();
                expectedConsumerName.set(crmContactRecordUIPage1.lstFirstConsumerName.getText());
                System.out.println("radio button clicked");
            }
        }
        //if general contact record
        else {
            scrollDown();
            click(crmContactRecordUIPage1.lstFirstConsumerNameRadioButton);
            crmContactRecordUIPage1.unableToAuthenticate.click();
        }

        //wait for warning snackbar to disappear, until which page will not be clickable
        waitFor(5);
        scrollUp();
        crmContactRecordUIPage1.linkRecordButton.click();
        scrollUpRobot();
        try {
            assertTrue(crmContactRecordUIPage1.unLink.isDisplayed());
        } catch (Exception e) {
            click(crmContactRecordUIPage1.lstFirstConsumerNameRadioButton);
            crmContactRecordUIPage1.linkRecordButton.click();
        }
    }

    @Then("I link the contact to an existing Case")
    public void i_link_the_contact_to_an_existing_Case() {
        waitFor(4);
        click(crmContactRecordUIPage1.expandFistConsumer);
        waitFor(2);
        caseId.set(crmContactRecordUIPage1.caseIdFirstRecord.getText());
        if (!caseId.get().isEmpty() && caseId != null) {
            crmContactRecordUIPage1.fistConsumerRadioBtn.click();
            crmContactRecordUIPage1.DOBcheckBox.click();
            crmContactRecordUIPage1.SSNcheckBox.click();
        }
        waitFor(3);
        crmContactRecordUIPage1.linkRecordButton.click();
        System.out.println("Link Record button clicked");
        waitFor(2);
        scrollUpUsingActions(2);
    }

    @Given("I see {string} field accept only letters")
    public void i_see_field_accept_only_letters(String field, List<String> texts) {

        for (String text : texts) {
            String actual = "";
            switch (field) {
                case "firstName":
                    clearAndFillText(crmContactRecordUIPage1.firstName, text);
                    waitFor(1);
                    actual = crmContactRecordUIPage1.firstName.getAttribute("value");
                    break;
                case "lastName":
                    clearAndFillText(crmContactRecordUIPage1.lastName, text);
                    waitFor(1);
                    actual = crmContactRecordUIPage1.lastName.getAttribute("value");
                    break;
            }
            assertTrue(hasOnlyLettersSpaces(actual));
        }
    }


    @Then("I see {string} field accept only {int} characters")
    public void i_see_field_accept_only_characters(String field, int stringLength) {

        String actual = "";
        String sentText = createTextString(stringLength * 4);
        switch (field) {
            case "firstName":
                clearAndFillText(crmContactRecordUIPage1.firstName, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.firstName.getAttribute("value");
                break;
            case "lastName":
                clearAndFillText(crmContactRecordUIPage1.lastName, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.lastName.getAttribute("value");
                break;
            case "otherReason":
                clearAndFillText(crmContactRecordUIPage1.contactActionTextForOtherReason, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.contactActionTextForOtherReason.getText();
                break;
            case "caseID":
                clearAndFillText(crmContactRecordUIPage1.searchCaseID, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.searchCaseID.getText();
            case "consumerID":
                clearAndFillText(crmContactRecordUIPage1.searchConsumerID, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.searchConsumerID.getText();
        }
        assertTrue(actual.length() <= stringLength);
    }

    @And("I click on {string} type of call option in {string} dropdown")
    public void i_click_on_type_of_call_option_in_dropdowm(String option, String field) {
        scrollToElement(crmContactRecordUIPage1.contactDetailsHeader);
        waitFor(4);
        selectDropDown(crmContactRecordUIPage1.contactType, option);
    }

    /**
     * this method checks if called field has listed dropdown options to be displayed
     */
    @Then("I should see following dropdown options for {string} field displayed")
    public void i_should_see_following_dropdown_options_for_field_displayed(String field, List<String> options) {
/*
        if (contactRecord.businessUnitDropDwn.isDisplayed()){
            selectDropDown(contactRecord.businessUnitDropDwn,"CVCC");
        }
 */
        int count = 0;
        for (String option : options) {
            switch (field) {
                case "consumer type":
                    selectDropDown(crmContactRecordUIPage1.consumerType, option);
                    break;
                case "contact type":
                    selectDropDown(crmContactRecordUIPage1.contactType, option);
                    break;
                case "contact channel":
                    selectDropDown(crmContactRecordUIPage1.contactChannelType, option);
                    //     waitFor(2);
                    break;
                case "preferred language":
                    selectDropDown(crmContactRecordUIPage1.preferredLanguage, option);
                    break;
                case "contact reason":
                    //scrollUpRobot();
                    scrollUpUsingActions(2);
                    while (count == 0) {
                        waitFor(3);
                        waitForVisibility(crmContactRecordUIPage1.expendReasonButton, 10);
//                        contactRecord.expendReasonButton.click();
                        count++;
                    }
                    waitFor(4);
                    selectDropDown(crmContactRecordUIPage1.contactReason, option);
                    break;
                case "contact action":
                    selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.contactAction, option);
                    break;
                case "call campaign reference":
                    selectDropDown(crmContactRecordUIPage1.callCampaignReference, option);
                    break;
                case "outcome of contact":
                    waitFor(4);
                    selectDropDown(crmContactRecordUIPage1.outcomeOfContact, option);
                    break;
                case "Search Case":
                    selectDropDown(manualCaseConsumerSearchPage.searchCaseOptions, option);
                    break;
                case "Search Consumer":
                    selectDropDown(manualCaseConsumerSearchPage.searchConsumerOptions, option);
                    break;
                case "show items per page":
                    selectDropDown(crmContactRecordUIPage1.showItemsDropdown, option);
                    break;

            }
        }
    }


    @Then("I should see the following dropdown options for {string} field displayed in Contact Record page")
    public void i_should_see_following_dropdown_options_for_field_displayed_in_contact_record_page(String field, List<String> options) {
        int count = 0;
        for (String option : options) {
            switch (field) {
                case "Search Case":
                    selectDropDown(crmContactRecordUIPage1.searchCaseDropdownNew, option);
                    break;
                case "Search Consumer":
                    selectDropDown(crmContactRecordUIPage1.searchConsumerDropdown, option);
                    break;
            }
        }
    }

    /**
     * this method checks if text of the element is present on the page
     */
    @When("I should see Contact Action Dropdown disabled")
    public void i_should_see_Contact_Action_Dropdown_disabled() {
        assertTrue(IsElementDisplayed(crmContactRecordUIPage1.contactAction), "Contact Action dropdown should not be displayed");
    }

    @When("I populate Search criteria fields for a new consumer")
    public void i_populate_Search_criteria_fields_for_a_new_consumer() {
        waitFor(1);
        newConsumer.set(new HashMap<>(getNewTestData2()));
        //System.out.println(newConsumer.keySet());
        clearAndFillText(crmContactRecordUIPage1.firstName, (newConsumer.get().get("name")));
        clearAndFillText(crmContactRecordUIPage1.lastName, (newConsumer.get().get("surname")));
        System.out.println((newConsumer.get().get("name") + " " + newConsumer.get().get("surname")));
        //commented out due to selenium sincronisation issue, no need to assert input value
        //  assertTrue(newConsumer.get("name").equalsIgnoreCase(contactRecord.firstName.getAttribute("value")), "Consumer first name is not correct");
        //   assertTrue(newConsumer.get("surname").equalsIgnoreCase(contactRecord.lastName.getAttribute("value")), "Consumer last name is not correct");

    }

    @When("I click on reset button on Contact Record UI")
    public void i_click_on_reset_button() {
        crmContactRecordUIPage1.resetButton.click();
    }

    @Then("I see Search criteria fields have no value")
    public void i_see_Search_criteria_fields_have_no_value() {
        String expected = "";
        assertTrue(expected.equals(crmContactRecordUIPage1.firstName.getAttribute("value")));
        assertTrue(expected.equals(crmContactRecordUIPage1.lastName.getAttribute("value")));
    }

    @When("I enter Consumer details {string} on contact record UI")
    public void i_enter_Consumer_details_on_contact_record_UI(String name) {
        clearAndFillText(thirdPartyDetails.ccSearchThirdPartyFirstName, (name));
        crmContactRecordUIPage1.searchButton.click();
        waitFor(5);
    }

    @When("I click on cancel button and see a message")
    public void i_click_on_cancel_button_and_see_a_message() {
        waitFor(3);
        crmContactRecordUIPage1.cancelButton.click();
        waitFor(3);

    }

    @When("I confirm cancellation on the message")
    public void i_confirm_cancellation_on_the_message() {
        jsClick(crmContactRecordUIPage1.cancelWarningContinueButton);
    }

    //By Vinuta, Search without entering any parameters
    @When("I do not enter any search parameters")
    public void i_do_not_enter_any_search_parameters() {
        Reporter.log("Clear all textboxes");
        crmContactRecordUIPage1.firstName.clear();
        crmContactRecordUIPage1.lastName.clear();
        crmContactRecordUIPage1.middleName.clear();
        crmContactRecordUIPage1.ssnTextbox.clear();
        crmContactRecordUIPage1.dobTextbox.clear();
        crmContactRecordUIPage1.uniqueIDTextbox.clear();
        assertTrue(crmContactRecordUIPage1.searchButton.isDisplayed(), "Search button is not displayed on Contact Record UI");
        crmContactRecordUIPage1.searchButton.click();
    }

    @Then("I should be navigated to dashboard")
    public void i_should_be_navigated_to_dashboard() {
        assertFalse(isElementDisplayed(crmContactRecordUIPage1.recordIcon));
        //assertTrue(contactRecord.noContactInProgressGraySign.isDisplayed());
    }

    @When("I should see following options for {string} section displayed")
    public void i_should_see_following_options_for_section_displayed(String string) {
//        contactRecord.expendReasonButton.click();
        waitFor(1);
        assertTrue(crmContactRecordUIPage1.contactReason.isDisplayed(), "Contact Reason dropdown is not displayed");
        assertTrue(crmContactRecordUIPage1.contactAction.isDisplayed(), "Contact Action dropdown is not displayed");
        assertTrue(crmContactRecordUIPage1.reasonsComments.isDisplayed(), "Comments Field is not displayed");
        assertTrue(crmContactRecordUIPage1.expendAdditionalCommentsButton.isDisplayed(), "Additional Comments dropdown is not displayed");
    }

    @When("I click on Hamburger Menu I should see the create Task button and I should be able to click")
    public void i_click_on_Hamburger_Menu_I_should_see_the_create_Task_button_and_I_should_be_able_to_click() {
        navigateToHamBurgerMenu("CREATEATASK");
    }

    @When("I click on the Hamburger Menu")
    public void i_click_on_the_hamburger_menu() {
        crmContactRecordUIPage1.hamBurgerMenu.click();

    }

    //More option is removed -aswath 04/03/2019
    @Then("I should see all the options present")
    public void i_should_see_all_the_options_present() {
        waitForVisibility(crmContactRecordUIPage1.createATask, 1);
        assertTrue(crmContactRecordUIPage1.createATask.isDisplayed());
        //waitForVisibility(contactRecord.moreOption, 1);
        //assertTrue(contactRecord.moreOption.isDisplayed());
//        waitForVisibility(contactRecord.serviceRequestOption, 1);
//        assertTrue(contactRecord.serviceRequestOption.isDisplayed());
    }

    @And("I should see the Username should be present")
    public void i_should_see_the_username_should_be_present() {
        assertTrue(crmContactRecordUIPage1.headerUsername.isDisplayed());

    }

    @And("I should see the Role present in the header")
    public void i_should_see_the_role_present_in_the_header() {
        assertTrue(crmContactRecordUIPage1.headerRole.isDisplayed());

    }

    @And("I should see the Current Date and Time Displayed")
    public void i_should_see_the_current_date_time_displayed() {
        String actualcontDate = crmContactRecordUIPage1.headerDate.getText();
        String actaulTime = crmContactRecordUIPage1.headerTime.getText();
        String expectedDate = getCurrentDate();
        String expectedTime = timeNow();
        waitForVisibility(crmContactRecordUIPage1.headerTime, 10);
        assertTrue(crmContactRecordUIPage1.headerTime.isDisplayed(), "time");
        assertTrue(actaulTime.equals(expectedTime), "time is not correct");
        waitForVisibility(crmContactRecordUIPage1.getHeaderDate(expectedDate), 10);
        assertTrue(crmContactRecordUIPage1.getHeaderDate(expectedDate).isDisplayed());
    }

    @And("I should see the office Address displayed in the bottom")
    public void i_should_see_the_office_address_displayed_in_the_bottom(String data) {
        waitFor(4);
        String actualAddress = tmListOfProjectsPage.footerAddressLabel.getText();
        assertEquals(actualAddress, data, "Mismatch in expected footer label address");
    }

    @Given("I logged into the CRM Application")
    public void i_logged_into_the_CRM_Application() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData, password, projectName.get());

    }

    @Given("I logged into the CRM Application {string}")
    public void i_logged_into_the_CRM_Application_attempt(String attempt) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData, password, projectName.get());
    }

    //By Vinuta, Method to verify error when blank search is performed
    @Then("I see notification to fill search criteria")
    public void i_see_notification_to_fill_search_criteria() {
        assertTrue(crmContactRecordUIPage1.blankSearchError.isDisplayed(), "Blank Search Error is not displayed");
    }

    //By Vinuta, Method to enter search parameters into all search criteria fields on contact record UI

    /**
     * This method links a consumer to an existing Case or Consumer profile
     * Conditional statement is added to select member radio button if record type selected as Third Party
     *
     * @Then("I link the contact to an existing Case or Consumer Profile")
     * public void i_link_the_contact_to_an_existing_Case_or_Consumer_Profile() throws InterruptedException {
     * contactRecord.expandFistConsumer.click();
     * if(unidentifiedContact.contactRecordTypeDropDown.getText().equalsIgnoreCase("Third Party")){
     * waitFor(2);
     * contactRecord.rdoMember.click();
     * }else{
     * contactRecord.unableToAuthenticate.click();
     * }
     * <p>
     * //wait for warning snackbar to disappear, until which page will not be clickable
     * waitFor(5);
     * contactRecord.linkRecordButton.click();
     * //  waitForVisibility(crmActiveContactPage.defaultPreferredLang, 10);
     * //  crmActiveContactPage.defaultPreferredLang.click();
     * scrollUpRobot();
     * waitFor(2);
     * //        assertTrue(contactRecord.unLink.isDisplayed());
     * <p>
     * //todo choosing an existing project (not implemented yet) 08/24/18
     * }
     */
    @When("I enter First Name as {string}, Middle Initial as {string}, Last Name as {string}, SSN as {string}, Date Of Birth as {string}, Unique ID as {string}  on Contact Record")
    public void i_enter_First_Name_as_Middle_Initial_as_Last_Name_as_SSN_as_Date_Of_Birth_as_Unique_ID_as_on_Contact_Record(String fname, String mname, String lname, String ssn, String dob, String UID) {
        crmContactRecordUIPage1.resetButton.click();
        assertTrue(crmContactRecordUIPage1.firstName.isDisplayed(), "Consumer first name textbox is not displayed");
        crmContactRecordUIPage1.firstName.sendKeys(fname);
        crmContactRecordUIPage1.lastName.sendKeys(lname);
        crmContactRecordUIPage1.middleName.sendKeys(mname);
        crmContactRecordUIPage1.ssnTextbox.sendKeys(ssn);
        crmContactRecordUIPage1.dobTextbox.sendKeys(dob);
        crmContactRecordUIPage1.uniqueIDTextbox.sendKeys(UID);
    }

    //By Vinuta, Validate SSN field-level error
    @Then("I see SSN field validation displayed")
    public void i_see_SSN_field_validation_displayed() {
        assertTrue(crmContactRecordUIPage1.headerError.isDisplayed());
        assertTrue(crmContactRecordUIPage1.ssnError.isDisplayed());
    }

    //By Vinuta, Validate DOB field-level error
    @Then("I see DOB field validation displayed")
    public void i_see_DOB_field_validation_displayed() {
        assertTrue(crmContactRecordUIPage1.headerError.isDisplayed());
        assertTrue(crmContactRecordUIPage1.dobError.isDisplayed());
    }

    //By Vinuta, Validate error on Contact reason,comments section
    @Then("I should receive error to fill out required fields on reasons and comments")
    public void i_should_receive_error_to_fill_out_required_fields_on_reasons_and_comments() {
        assertTrue(crmContactRecordUIPage1.contactReasonError.isDisplayed(), "Contact Reason error is not displayed");
        assertTrue(crmContactRecordUIPage1.contactActionError.isDisplayed(), "Contact Action error is not displayed");
        //Error is not displayed anymore as per latest changes
        //assertTrue(contactRecord.additionalCommentsError.isDisplayed(), "Additional Comments error is not displayed");

    }

    @When("I choose {string} option for Contact Action field")
    public void i_choose_option_for_Contact_Action_field(String option) {
        selectOptionFromMultiSelectDropDownNew(crmContactRecordUIPage1.contactAction, option);
        waitFor(1);
        crmContactRecordUIPage1.saveReasonButton.click();
    }

    @When("I click on Close Button on Contact record UI Page")
    public void i_click_on_Close_Button_on_Contact_record_UI_Page() {
        crmContactRecordUIPage1.closeButton.click();
    }

    @When("I see fill out mandatory fields warning on Contact record UI Page")
    public void i_see_fill_out_mandatory_fields_warning_on_Contact_record_UI_Page() {
        waitFor(1);
        assertTrue(crmContactRecordUIPage1.enterSearchParametersWarning.isDisplayed(), "Enter Search parameters warning is not displayed");
    }

    @Then("I verify all mandatory fields on Contact record UI Page are labeled and displayed")
    public void i_verify_all_mandatory_fields_on_Contact_record_UI_Page_are_labeled_and_displayed() {
//        contactRecord.expendReasonButton.click();
        waitFor(1);
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactReasonLabel), "Contact Reason is not marked as mandatory");
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactActionLabel), "Contact Action is not marked as mandatory");
//        contactRecord.expendAdditionalCommentsButton.click();
        waitFor(1);
        //Additional Comments will not be marked mandatory since release 40 11/04/2020
        //assertTrue(markedMandatory(contactRecord.commentsLabel), "Additional Comment is not marked as mandatory");
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactTypeLabel), "Contact Type is not marked as mandatory");
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactChannelTypeLabel), "Contact Channel Type is not marked as mandatory");
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactDispositionsLabel), "Contact Disposition is not marked as mandatory");
        assertTrue(markedMandatory(crmContactRecordUIPage1.contactPhoneLabel), "Contact Phone is not marked as mandatory");
    }

    @Given("I enter value into mandatory First and Last name fields on Contact record UI Page")
    public void i_enter_value_into_mandatory_First_and_Last_name_fields_on_Contact_record_UI_Page() {
        clearAndFillText(crmContactRecordUIPage1.firstName, ("name"));
        clearAndFillText(crmContactRecordUIPage1.lastName, ("surname"));
    }

    @Then("I verify all non-mandatory fields are not labeled")
    public void i_verify_all_non_mandatory_fields_are_not_labeled() {
        assertFalse(markedMandatory(crmContactRecordUIPage1.ssnSearch), "SSN field should not be mandatory");
        assertFalse(markedMandatory(crmContactRecordUIPage1.dobSearch), "DOB field should not be mandatory");
        assertFalse(markedMandatory(crmContactRecordUIPage1.middleInitialSearch), "Middle Initial field should not be mandatory");
        assertFalse(markedMandatory(crmContactRecordUIPage1.callQueueType), "Call Queue Type field should not be mandatory");
    }


    @Given("I search for an existing contact by first name and SSN")
    public void i_search_for_an_existing_contact_by_first_name_and_SSN() {
        clearAndFillText(crmContactRecordUIPage1.firstName, "Joan");
        waitFor(7);
        clearAndFillText(crmContactRecordUIPage1.ssnSearch, "111223333");
        crmContactRecordUIPage1.searchButton.click();
        waitFor(5);
    }

    @When("I end the current call")
    public void i_end_the_current_call() {
        waitFor(1);
        crmContactRecordUIPage1.stopContact.click();
    }

    @When("I close the current Active Contact")
    public void i_close_the_current_Active_Contact() {
        waitFor(1);
        //selectDropDown(contactRecord.contactDispositions, "Complete");
        singleSelectFromDropDown(crmContactRecordUIPage1.contactDispositions, "Complete");
        waitFor(1);
        crmContactRecordUIPage1.closeButton.click();
    }

    @Then("I see initiate a contact button on CRM Dashboard")
    public void i_see_initiate_a_contact_button_on_CRM_Dashboard() {
        waitFor(2);
        assertTrue(crmContactRecordUIPage1.initContact.isDisplayed(), "Initiate a contact button is not displayed");
    }

    @And("I search for and access the previously created contacted record")
    public void searchForExistingRecordStep() {
        waitForVisibility(crmContactRecordUIPage1.caseContactDetailsTab, 4);
        crmContactRecordUIPage1.caseContactDetailsTab.click();
        readDataFromForm.get().selectPreviouslyContactRecord(crmContactRecordUIPage1);
    }

    @Then("I should see the inbound contact record with the previously created values")
    public void verifyInboundContactRecordValuesStep() {
        readDataFromForm.get().assertInboundContactRecordDetails(crmContactRecordUIPage1);
    }

    @Then("I should see the outbound contact record with the previously created values")
    public void verifyOutboundContactRecordValuesStep() {
        readDataFromForm.get().assertOutboundContactRecordDetails(crmContactRecordUIPage1);
    }

    @Then("I should see the contact reasons with the previously created values")
    public void verifyContactRecordAndReasonsValuesStep() {
        waitForVisibility(crmContactRecordUIPage1.getElementContainingTextOAllNodesInNodeSet("CONTACT RECORD"), 3);
        readDataFromForm.get().assertContactReasonsDetails(crmContactRecordUIPage1);
    }

    @Given("I log back into CRM and click on initiate contact")
    public void logIntoSystemFromLoginStep() {
        loginCRM(userNameData, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
    }

    @Then("I verify dropdown values  available for Program Field")
    public void verifyProgramFieldDropDownValues(List<String> options) {
        crmContactRecordUIPage1.lstSelectProgram.click();
        ArrayList<String> actualValues = new ArrayList<String>();
        ArrayList<String> expValues = new ArrayList<String>();
        for (String option : options)
            expValues.add(option);
        for (WebElement program : crmContactRecordUIPage1.lstSelectProgramValues) {
            actualValues.add(program.getText());
        }

        Collections.sort(actualValues);
        Collections.sort(expValues);
        assertEquals(actualValues, expValues);
    }

    @Then("I verify that multiple options can be selected for Program Field")
    public void verifyUserCanSelectMoreThanOneProgram(List<String> options) {
        List<String> actualValues = new ArrayList<String>();
        List<String> expValues = new ArrayList<String>();
        String[] items;
        for (String option : options) {
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, option);
            expValues.add(option);
            waitFor(2);
        }

        for (WebElement program : crmContactRecordUIPage1.lblSelectedPrograms) {
            items = program.getText().split(", ");
            // actualValues = Arrays.asList(items);
            actualValues.add(program.getText());
        }

        Collections.sort(actualValues);
        Collections.sort(expValues);
        assertEquals(actualValues, expValues);
    }

    @Then("I verify error message {string} displayed for program field")
    public void verifyProgramFieldErrorMessage(String expMessage) {
        String actualValues = crmContactRecordUIPage1.lblSelectProgramMessage.getText();
        assertEquals(actualValues, expMessage);
    }

    @When("I link the contact to an existing Case or Consumer Profile {string}")
    public void linkExistingConsumer(String caseOrConsumer) throws InterruptedException {
        boolean recordFound = false;
        int size = Driver.getDriver().findElements(By.xpath("//*[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/tbody/tr")).size();

        for (int i = 1; i <= size; i++) {
            String consumerID = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[3]")).getText();
            if (consumerID.equals(tdm.get("CrmConsumerId"))) {
                Driver.getDriver().findElement(By.xpath("//*[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[1]//button")).click();
                recordFound = true;
            }
            waitFor(1);
            if (recordFound) {
                // if (unidentifiedContact.contactRecordTypeButton.getText().equalsIgnoreCase("THIRD PARTY CONTACT")) {
                //Driver.getDriver().findElement(By.xpath("(//input[@name='member'])[" + ((i + 1) / 2) + "]")).click();
                //} else {
                consumerSearchResult.consumerNameRadioButton.click();
                consumerSearchResult.unableToAuthenticateCheckBox.click();
                //}
                waitFor(5);
                scrollToElement(Driver.getDriver().findElement(By.xpath("(//*[contains(text(), 'link')]/..)[" + ((i + 1) / 2) + "]")));
                scrollRight();
                jsClick(Driver.getDriver().findElement(By.xpath("(//*[contains(text(), 'link')]/..)[" + ((i + 1) / 2) + "]")));
                break;
            }
            i++;
        }
        scrollUpUsingActions(2);

    }


    @When("I link the contact to an existing Case or Consumer Profile with first name {string} and last name {string}")
    public void linkExistingConsumerWithFirstNameLastName(String firstName, String lastName) throws InterruptedException {
        waitForPageToLoad(5);
        boolean recordFound = false;
        boolean nextSetExist = false;

        do {
            int size = Driver.getDriver().findElements(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr")).size();
            for (int i = 1; i <= size; i++) {
                String fname = Driver.getDriver().findElement(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[4]")).getText();
                String lname = Driver.getDriver().findElement(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[5]")).getText();
                waitFor(3);
                if (fname.equals(firstName) && lname.equalsIgnoreCase(lastName)) {
                    Driver.getDriver().findElement(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[1]//button")).click();
                    recordFound = true;
                }
                waitFor(3);
                if (recordFound) {
                    if (thirdPartyDetails.thirdPartyRecordTypeRadio.getAttribute("class").contains("active")) {
                        if (!Driver.getDriver().findElement(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[2]")).getText().isEmpty())
                            Driver.getDriver().findElement(By.xpath("(//input[@name='member'])[" + ((i + 1) / 2) + "]")).click();
                    } else {
                        Driver.getDriver().findElement(By.xpath("//*[@name='0_caConsumerFullName']")).click();
                        Driver.getDriver().findElement(By.xpath("(//*[@name='caUnableToAuthenticate'])[" + ((i + 1) / 2) + "]")).click();
                    }
                    waitFor(5);
                    // scrollToElement(Driver.getDriver().findElement(By.xpath("(//*[contains(text(), 'link')]/..)[" + ((i + 1) / 2) + "]")));
                    scrollRight();
                    click(Driver.getDriver().findElement(By.xpath("(//*[contains(text(), 'link')]/..)[" + ((i + 1) / 2) + "]")));
                    break;
                }
                i++;
            }
            if (recordFound) {
                break;
            } else {
                if (Driver.getDriver().findElement(By.xpath("//button[@aria-label='Next Page']")).isEnabled()) {
                    Driver.getDriver().findElement(By.xpath("//button[@aria-label='Next Page']")).click();
                    nextSetExist = true;
                } else {
                    break;
                }


            }

        } while (nextSetExist);

//        scrollUpUsingActions(3);

    }

    @When("I close active contact after selecting all mandatory fields")
    public void i_close_active_contact_after_selecting_all_mandatory_fields() {
        singleSelectFromDropDown(crmActiveContactPage.programTypes, "Program A");
        waitFor(2);
        selectDropDown(crmContactRecordUIPage1.contactDispositions, "Complete");
        waitFor(2);
        contactPhoneNumber.set(getRandomNumber(10));
        clearAndFillText(crmActiveContactPage.contactPhoneNumber, contactPhoneNumber.get());
        crmContactRecordUIPage1.stopContact.click();
        waitFor(1);
        crmContactRecordUIPage1.closeButton.click();
    }


    /* Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu
       Author - Aswath
       Date - 03/01/2019
       Parameters - NA
       */
    @Then("Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu")
    public void verify_the_slide_bar_menu() {

        waitForVisibility(crmContactRecordUIPage1.taskManagementInsilderBar, 10);
        assertTrue(crmContactRecordUIPage1.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");

        waitForVisibility(crmContactRecordUIPage1.activeContactInsilderBar, 10);
        assertTrue(crmContactRecordUIPage1.activeContactInsilderBar.isDisplayed(), "Active Contact menu is not displayed");

        waitForVisibility(crmContactRecordUIPage1.caseConsumerInsilderBar, 10);
        assertTrue(crmContactRecordUIPage1.caseConsumerInsilderBar.isDisplayed(), "Case/Consumer menu is not displayed");

        waitForVisibility(crmContactRecordUIPage1.contactRecordInsilderBar, 10);
        assertTrue(crmContactRecordUIPage1.contactRecordInsilderBar.isDisplayed(), "Contact record serach menu is not displayed");
    }

    /* Verifies the Task Management is displayed, on each tab of contact search
   Author - Aswath
   Date - 03/01/2019
   Parameters - NA
   */
    @Then("Verify the Task management page")
    public void Verify_the_Task_management_page() {
        assertTrue(crmContactRecordUIPage1.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");
        crmContactRecordUIPage1.taskManagementInsilderBar.click();
        String expectedTaskURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/task-management/info";
        String actualTaskURL = Driver.getDriver().getCurrentUrl();
        assertEquals(actualTaskURL, expectedTaskURL);
        waitFor(10);
        crmContactRecordUIPage1.activeContactInsilderBar.click();
        String expectedActiveURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars/crm/active-contact/contact/active";
        String actualActiveURL = Driver.getDriver().getCurrentUrl();
        assertEquals(actualActiveURL, expectedActiveURL);
        assertTrue(crmContactRecordUIPage1.taskManagementInsilderBar.isDisplayed(), "Task Management menu is not displayed");
    }


    @Given("I logged into CRM")
    public void i_logged_into_CRM() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData, password, projectName.get());
        waitFor(6);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);
    }

    @Given("I verify that {string} button is displayed")
    public void i_verify_that_button_is_displayed(String initContactButtonText) {
        waitFor(3);
        assertTrue(crmContactRecordUIPage1.initContact.isDisplayed());
        assertEquals(crmContactRecordUIPage1.initContact.getText().substring(5), initContactButtonText);
    }

    @Given("I click on {string} and verify that {string} is displayed")
    public void i_click_on_and_verify_that_is_displayed(String initContactButtonText, String endContactButtonText) {
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);
        crmContactRecordUIPage1.initContact.click();
        waitFor(3);
        //assertFalse(contactRecord.initContact.isDisplayed());
        assertTrue(crmContactRecordUIPage1.stopContact.isDisplayed());
        //assertEquals(contactRecord.stopContact.getText(),endContactButtonText);
    }


    @Given("I logged into CRM with service account 2")
    public void i_logged_into_CRM_with_service_account_2() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        System.out.println("Project Name" + projectName.get());
        loginCRM(userNameData1, password, projectName.get());
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);
    }

    @When("I initiate contact")
    public void initiateContact() {
        waitFor(3);
        waitForVisibility(crmContactRecordUIPage1.initContact, 2);
        crmContactRecordUIPage1.initContact.click();
        assertTrue(crmContactRecordUIPage1.contactInProgressGreenSign.isDisplayed());
        scrollUpRobotKey();
        scrollUpUsingActions(3);
        waitFor(1);
    }

    @Then("I verify active contact record link is not displayed")
    public void verifyActiveContactRecordLinkNotDisplayed() {
        waitFor(2);
        assertTrue(!isElementDisplayed(dashboard.activeContactTab));
    }

    @Then("I verify active contact record link is displayed")
    public void verifyActiveContactRecordLinkDisplayed() {
        waitFor(2);
        assertTrue(isElementDisplayed(dashboard.activeContactTab));
    }


    @When("I enter contact details for inbound contact type with call queue {string} contact channel type {string} contact channel {string} program type {string} and contact disposition {string}")
    public void enterContactDetails(String callQueue, String conactChannelType, String contactChannel, String programType, String contactDispostion) {
        selectDropDown(crmContactRecordUIPage1.contactType, "Inbound");
        selectDropDown(crmContactRecordUIPage1.callQueueType, callQueue);
        if (conactChannelType.equalsIgnoreCase("Phone")) {
            selectDropDown(crmContactRecordUIPage1.contactChannelType, conactChannelType);
            sendKeyToTextField(crmContactRecordUIPage1.phoneNumber, contactChannel);
        }
        singleSelectFromDropDown(crmActiveContactPage.programTypes, programType);
        waitFor(2);
        selectDropDown(crmContactRecordUIPage1.contactDispositions, contactDispostion);

    }


    @Then("I verify inbound contact record values retained for call queue {string} contact channel type {string} contact channel {string} program type {string} and contact disposition {string}")
    public void verifyInboundContactRecordValues(String callQueue, String conactChannelType, String contactChannel, String programType, String contactDispostion) {
        assertEquals(crmContactRecordUIPage1.contactType.getText(), "Inbound");
        assertEquals(crmContactRecordUIPage1.callQueueType.getText(), callQueue);

        if (conactChannelType.equalsIgnoreCase("Phone")) {
            assertEquals(crmContactRecordUIPage1.contactChannelType.getText(), conactChannelType);
            // assertEquals(contactRecord.contactChannelType.findElement(By.xpath("./input")).getAttribute("value"), conactChannelType);
            assertEquals(crmContactRecordUIPage1.phoneNumber.getAttribute("value"), contactChannel);
        }
        assertEquals(crmActiveContactPage.programTypes.findElement(By.xpath("./input")).getAttribute("value"), programType);
        //assertEquals(contactRecord.contactDispositions.findElement(By.xpath("./input")).getAttribute("value"), contactDispostion);
        assertEquals(crmContactRecordUIPage1.contactDispositions.getText(), contactDispostion);

    }

    @Then("I verify third party details are retained {string}, {string}, {string}, {string} and {string}")
    public void verifyThirdPartyContactDetails(String fname, String lname, String orgName, String consumerType, String preLanguage) {
        assertEquals(thirdPartyDetails.txtThirdPartyFirstName.getAttribute("value"), fname);
        assertEquals(thirdPartyDetails.txtThirdPartyLastName.getAttribute("value"), lname);
        assertEquals(thirdPartyDetails.txtThirdPartyOrganizationName.getAttribute("value"), orgName);
        assertEquals(thirdPartyDetails.lstThirdPartyConsumerType.findElement(By.xpath("./input")).getAttribute("value"), consumerType);
        assertEquals(thirdPartyDetails.lstThirdPartyPreferredLanguage.findElement(By.xpath("./input")).getAttribute("value"), preLanguage);
    }

    @When("I enter unidentified contact details caller type {string} and preferred language {string}")
    public void enterUnidentifiedContactDetails(String callerType, String preferredLanguage) {
        selectDropDown(crmContactRecordUIPage1.preferredLanguage, preferredLanguage);
        if (!callerType.isEmpty())
            sendKeyToTextField(crmContactRecordUIPage1.callerTypeValue, callerType);
    }

    @Then("I verify unidentified contact details are retained caller type {string} and preferred language {string}")
    public void verifyUnidentifiedContactDetails(String callerType, String preferredLanguage) {
        assertEquals(crmContactRecordUIPage1.callerTypeValue.getText(), callerType);
        assertEquals(crmContactRecordUIPage1.preferredLanguage.getText(), preferredLanguage);
    }

    @Then("I verify linked information is retained")
    public void verifyLinkedInformationRetained() {
        assertTrue(isElementDisplayed(crmContactRecordUIPage1.linkedConsumerInContact));
    }

    @Given("I select a {string} as a contact Record Type")
    public void i_select_a_as_a_contact_Record_Type(String recordType) {
        if (!recordType.equals("GENERAL CONTACT")) {
            clickOnContactRecordType(recordType);
        }
    }

    @When("I select a {string} as Contact Type dropdown")
    public void i_select_a_as_Contact_Type_dropdown(String option) {
        if (!option.equals("Inbound")) {
            selectDropDown(crmContactRecordUIPage1.contactType, option);
        }
    }


    @When("I select {string} as a contact Channel Type")
    public void i_select_as_a_contact_Channel_Type(String channelType) {
        if (!channelType.equals("Phone")) {
            selectDropDown(crmContactRecordUIPage1.contactChannelType, channelType);
        }
    }

    @Then("I am able to verify if Inbound Call Queue is possible to capture {string}")
    public void i_am_able_to_verify_if_Inbound_Call_Queue_is_possible_to_capture(String applicable) {
        Boolean inboundCallQueue = false;
        try {
            inboundCallQueue = crmContactRecordUIPage1.callQueueType.isDisplayed();
        } catch (Exception e) {
        } finally {
            assertTrue(inboundCallQueue.equals(Boolean.parseBoolean(applicable)), "Inbound Call Queue should be " + applicable + " to be displayed");
        }
    }

    @Then("I should be navigated to initial landing page")
    public void i_should_be_navigated_to_initial_landing_page() {
        waitFor(3);
        assertTrue(crmContactRecordUIPage1.initContact.isDisplayed(), "Not navigated to dashboard of initial landing page");
    }

    /* Verify user icon in dashboard user information bar
    Author - Aswath
    Date - 04/23/2019
    Parameters -
    */

    @Then("I verify the user icon and logout option")
    public void i_verify_the_user_icon_and_logout_option() {
        waitForPageToLoad(10);
        //System.out.println("Testing jenkins");
        waitForVisibility(crmContactRecordUIPage1.userIcon, 10);
        assertTrue(crmContactRecordUIPage1.userIcon.isDisplayed(), "User icon is not displayed on landing page");
        assertTrue(crmContactRecordUIPage1.logOutArrow.isDisplayed(), "Logout dropdown is not displayed on landing page");
    }

    /* Verify logout functionality
   Author - Aswath
   Date - 04/23/2019
   Parameters -
   */
    @Then("I verify logout functionality")
    public void i_verify_logout_functionality() {
        waitFor(10);
        waitForVisibility(crmContactRecordUIPage1.logOutArrow, 10);
        crmContactRecordUIPage1.logOutArrow.click();
        crmContactRecordUIPage1.logOutOption.click();
        waitForPageToLoad(10);
        String currUrl = Driver.getDriver().getCurrentUrl();
        String acURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/login";
        assertTrue(acURL.startsWith(currUrl), "User is not navigate login page");
    }

    @When("I navigate to {string} UI")
    public void i_navigate_to_UI(String page) {
        switch (page) {
            case "myTask":
                taskManagementStepDef.i_navigate_to_task_Managments_page("My Task");
                break;
            case "crmActiveContactPage":
                waitFor(3);
                crmContactRecordUIPage1.initContact.click();
                i_populate_Search_criteria_fields_for_a_new_consumer();
                STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().i_click_on_Search_Button_on_Search_Consumer_Page();
                STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().i_am_able_to_see_Add_Consumer_button_and_I_click_on_it_to_navigate_to_Create_Consumer_UI();
                STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().i_populate_Create_Consumer_fields_for_a_new_consumer();
                STEP_DEFINITION_THREAD_LOCAL_FACTORY.getCrmCreateConsumerProfileStepDefThreadLocal().i_click_on_Create_Consumer_Button_on_Create_Consumer_Page();
                waitFor(2);
                break;
            case "caseConsumerSearch":
                crmContactRecordUIPage1.caseConsumerInsilderBar.click();
                waitFor(1);
                break;
            case "contactRecord":
                waitFor(3);
                crmContactRecordUIPage1.initContact.click();
                i_populate_Search_criteria_fields_for_a_new_consumer();
                break;
        }
    }

    @When("I initiate logout button")
    public void i_initiate_logout_button() {
        crmContactRecordUIPage1.logOutArrow.click();
        crmContactRecordUIPage1.logOutOption.click();
    }

    @When("I click on continue button on warning message")
    public void i_click_on_continue_button_on_warning_message() {
        waitFor(5);
        dashboard.warningPopupContinueBtn.click();
        waitFor(2);

    }

    @When("I click on cancel button on warning message")
    public void i_click_on_cancel_button_on_warning_message() {
        waitFor(5);
        actions.moveToElement(dashboard.warningPopupCancelBtn).click().perform();
        waitFor(2);
    }

    @Then("I verify system did not navigate away from current {string} UI")
    public void i_verify_system_did_not_navigate_away_from_current_UI(String page) {
        switch (page) {
            case "crmActiveContactPage":
                assertTrue(crmContactRecordUIPage1.lblCrmConsumerId.isDisplayed(), "Did not remain on active contact record page");
                break;
            case "myTask":
                taskManagementStepDef.i_am_Navigated_to_Task_details_page();
                break;
            case "caseConsumerSearch":
                break;
            case "contactRecord":
                assertTrue(newConsumer.get().get("name").equalsIgnoreCase(crmContactRecordUIPage1.firstName.getAttribute("value")), "Did not remain on Contact Record UI page");
                break;
        }
    }

    @Then("I verify system navigated to login page")
    public void i_verify_system_navigated_to_login_page() {
        waitForPageToLoad(10);
        String currUrl = Driver.getDriver().getCurrentUrl();
        String acURL = "https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/login";
        assertTrue(acURL.startsWith(currUrl), "User is not navigate to login page");
    }

    @Then("I see updated basic search parameters fields")
    public void i_see_updated_basic_search_parameters_fields() {
        assertTrue(caseConsumerSearchPage.searchCaseOptions.isDisplayed(), "Case Search Drop Down field is not displayed");
        assertTrue(caseConsumerSearchPage.searchConsumerOptions.isDisplayed(), "Consumer Search Drop Down field is not displayed");
        assertTrue(crmContactRecordUIPage1.internalCaseId.isDisplayed(), "Case Search ID field is not displayed");
        assertTrue(crmContactRecordUIPage1.crmContactID.isDisplayed(), "Consumer Search ID field is not displayed");
    }

    @Then("I see {string} field has default {string} value captured on basic search")
    public void i_see_field_has_default_value_capture_on_basic_search(String field, String value) {
        String actual = "";
        switch (field) {
            case "searchCase":
                actual = crmContactRecordUIPage1.searchCaseTypeValue.getAttribute("value");
                break;
            case "searchConsumer":
                actual = crmContactRecordUIPage1.searchConsumerTypeValue.getAttribute("value");
                break;
            case "caseID":
                actual = crmContactRecordUIPage1.caseIdValue.getText();
                System.out.println(actual);
                break;
            case "consumerID":
                actual = crmContactRecordUIPage1.consumerIdValue.getText();
                System.out.println(actual);
                break;
        }
        assertTrue(actual.equals(value), field + " field value is not defaulted to " + value);
    }


    @Then("I see {string} field has default {string} value captured on Contact Record Search page")
    public void i_see_field_has_default_value_capture_on_contact_record_search_page(String field, String value) {
        switch (field) {
            case "searchCase":
                assertEquals(crmContactRecordUIPage1.searchCaseDropdownNew.getText(), value, field + " field value is not defaulted to " + value);
                break;
            case "searchConsumer":
                assertEquals(crmContactRecordUIPage1.searchConsumerDropdown.getText(), value, field + " field value is not defaulted to " + value);
                break;
        }
    }


    @Then("I see {string} basic search field accepts only alphanumeric characters")
    public void i_see_field_accept_only_alphanumeric_characters(String field) {
        String actual = "";
        if (field.equals("caseID")) {
            clearAndFillText(crmContactRecordUIPage1.searchCaseID, createTextString(2));
            actual = crmContactRecordUIPage1.searchCaseID.getAttribute("value").trim();
        } else {
            clearAndFillText(crmContactRecordUIPage1.searchConsumerID, createTextString(2));
            actual = crmContactRecordUIPage1.searchConsumerID.getAttribute("value").trim();
            assertTrue(isAlphanumeric(actual), "Unique ID field accepts non-alphanumeric characters");
        }
    }


    @Then("I choose {string} permission from dropdown")
    public void i_choose_permission_from_dropdown(String limitedPermission) {
        selectOptionFromMultiSelectDropDown(loginPage.permissionDropdown, limitedPermission);
    }

    @And("I select call campaign for outbound contact type")
    public void i_select_call_campaign_for_outbound_contact_type() {
        waitFor(3);
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.callCampaignDropDown, "Enrollment Reminder");
        waitFor(2);
    }


    @And("I click on expand reason button to provide new reason and action")
    public void i_click_on_expand_reason_button_to_provide_new_reaso_and_action() {
        waitFor(3);
        jsClick(crmContactRecordUIPage1.expendReasonButton);
        waitFor(2);
    }

    @And("I expand saved contact reason")
    public void expand_saved_reason() {
        waitFor(3);
        jsClick(crmContactRecordUIPage1.expandreasonsComments);
        waitFor(2);
    }


    @Then("I link the contact to an existing Case with consumer id")
    public void iLinkTheContactToAnExistingCaseWithConsumerId() {
        String consumerId = crmContactRecordUIPage1.conId.getText();
        waitFor(1);
        if (!consumerId.isEmpty() && consumerId != null) {
            crmContactRecordUIPage1.buttonForLink.click();
            waitFor(1);
            crmContactRecordUIPage1.fullNameCheck.click();
            crmContactRecordUIPage1.DOBcheckBox.click();
            crmContactRecordUIPage1.conIdCheckBox.click();
        }
        waitFor(3);
        crmContactRecordUIPage1.linkRecordButton.click();
        System.out.println("Link Record button clicked");

    }

    @Then("I link the contact to an existing CaseConsumer in {string}")
    public void iLinkTheContactToAnExistingCaseConsumer(String projectName) {
        String consumerId = crmContactRecordUIPage1.conId.getText();
        waitFor(1);
        if (!consumerId.isEmpty() && consumerId != null) {
            crmContactRecordUIPage1.buttonForLink.click();
            waitFor(1);
            if (projectName.equalsIgnoreCase("IN-EB")) {
                crmContactRecordUIPage1.fullNameCheck.click();
                crmContactRecordUIPage1.DOBcheckBox.click();
                crmContactRecordUIPage1.SSNcheckBox.click();
                crmContactRecordUIPage1.mailingAddressCheckBox.click();
            } else {
                crmContactRecordUIPage1.fullNameCheck.click();
                crmContactRecordUIPage1.DOBcheckBox.click();
                crmContactRecordUIPage1.SSNcheckBox.click();
                if(!projectName.equalsIgnoreCase("NJ-SBE")) {
                    crmContactRecordUIPage1.conIdCheckBox.click();
                }
                crmContactRecordUIPage1.phoneNumberAuthCheckBox.click();
                crmContactRecordUIPage1.mailingAddressCheckBox.click();
            }
        }
        waitFor(3);
        if(projectName.equalsIgnoreCase("NJ-SBE")) {
            crmContactRecordUIPage1.validateLinkButton.click();
        } else {
            crmContactRecordUIPage1.linkRecordButton.click();
        }
        System.out.println("Link Record button clicked");
    }

    @Given("I logged into CRM with service account 6")
    public void iLoggedIntoCRMWithServiceAccount6() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        System.out.println("Project Name" + projectName.get());
        loginCRM(csrUser6, password, projectName.get());
        waitFor(5);
    }


    @Then("I verify Cancel Button is available on active contact screen")
    public void i_verify_Cancel_Button_is_available_on_active_contact_screen() {
        assertTrue(crmContactRecordUIPage1.cancelButtonActiveContact.isDisplayed());
    }

    @Then("I verify when I click cancel button the warning message \"If you continue, the Contact Record will not be saved.” displayed")
    public void i_verify_when_I_click_cancel_button_the_warning_message_If_you_continue_the_Contact_Record_will_not_be_saved_displayed() {
        crmContactRecordUIPage1.cancelButtonActiveContact.click();
        waitFor(3);
        crmContactRecordUIPage1.cancelButtonWarningMessage.isDisplayed();
    }

    @When("I click cancel button and click continue on warning message")
    public void i_click_cancel_button_and_click_continue_on_warning_message() {
        waitFor(4);
        crmContactRecordUIPage1.cancelButtonActiveContact.click();
        waitFor(3);
        crmContactRecordUIPage1.cancelButtonContinue.click();
    }

    @Then("I link the contact record to an existing Case or Consumer Profile")
    public void i_link_the_contact_record_to_an_existing_Case_or_Consumer_Profile() throws InterruptedException {
        waitForVisibility(crmContactRecordUIPage1.expandFistConsumer, 10);
        waitFor(2);
        expectedConsumerName.set(crmContactRecordUIPage1.getExpectedName());
        waitFor(1);
        click(crmContactRecordUIPage1.expandFistConsumer);
        waitFor(3);
        caseId.set(crmContactRecordUIPage1.caseIdFirstRecord.getText());
        if (!caseId.get().isEmpty() && caseId != null) {
            if (thirdPartyDetails.thirdPartyRecordTypeRadio.getAttribute("class").contains("active")) {
                crmContactRecordUIPage1.rdoMember.click();
                System.out.println("radio button clicked");

            } else {
                crmContactRecordUIPage1.lstFirstConsumerNameRadioButton.click();
                waitFor(2);
                scrollDown();
                crmContactRecordUIPage1.unableToAuthenticate.click();
            }
        } else {
            if (!thirdPartyDetails.thirdPartyRecordTypeRadio.getAttribute("class").contains("active")) {
                scrollDown();
                click(crmContactRecordUIPage1.lstFirstConsumerNameRadioButton);
                crmContactRecordUIPage1.unableToAuthenticate.click();
            }
        }

        //wait for warning snackbar to disappear, until which page will not be clickable
        waitFor(2);
        scrollUp();

        crmContactRecordUIPage1.linkRecordButton.click();
        scrollUpRobot();
    }

    @And("I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE")
    public void iLinkTheContactToAnExistingCaseOrConsumerProfileForNJSBE() {
        waitFor(3);
        click(crmContactRecordUIPage1.expandFistConsumer);
        click(crmContactRecordUIPage1.lstFirstConsumerNameRadioButton);
        crmContactRecordUIPage1.DOBcheckBox.click();
        crmContactRecordUIPage1.SSNradioButton.click();
        waitFor(2);
        Assert.assertTrue(crmContactRecordUIPage1.validateAndLink.isDisplayed());
        waitFor(2);
        click(crmContactRecordUIPage1.validateAndLink);
        waitFor(4);
    }

    @Then("I click validate and link Consumer & Case")
    public void iClickValidateAndLinkConsumerCase() {
        waitFor(2);
        crmContactRecordUIPage1.validate.click();
        waitForVisibility(crmContactRecordUIPage1.linkCaseConsumer, 10);
        crmContactRecordUIPage1.linkCaseConsumer.click();
        waitFor(10);
    }

    @Then("I click validate and link Consumer & Case for task")
    public void tiClickValidateAndLinkConsumerCase() {
        scrollDownUsingActions(2);
        waitFor(5);
        createGeneralTask.validateTaskbutton.click();
        waitFor(4);
        createGeneralTask.linkCaseConsumerButtonAfterValidation.click();
        waitFor(5);
        scrollDownUsingActions(2);
        waitForVisibility(createGeneralTask.linkedNames.get(0), 10);
        scrollUpUsingActions(1);
        waitFor(1);
        caseId.set(createGeneralTask.getCaseIdTask.getText());
        selectRandomDropDownOption(createGeneralTask.selectReasonDropdown);
        selectRandomDropDownOption(createGeneralTask.complaintAboutDropdown);
        clearAndFillText(createGeneralTask.name, getRandomString(8));
        waitFor(2);
        createGeneralTask.butSaveAfterLink.click();
    }

    @Then("I click validate and link only Link Case")
    public void ClickValidateAndLinkOnlyLinkCase() {
        waitFor(2);
        createGeneralTask.validateTaskbutton.click();
        waitForVisibility(createGeneralTask.linkCaseConsumerButtonAfterValidation, 10);
        createGeneralTask.linkCaseOnly_Chkbox1.click();
        waitFor(2);
        createGeneralTask.linkCaseButton.click();

        scrollUpUsingActions(1);
        selectRandomDropDownOption(createGeneralTask.reasonForEditDrpDn);
        selectRandomDropDownOption(createGeneralTask.selectReasonDropdown);
        selectRandomDropDownOption(createGeneralTask.complaintAboutDropdown);
        clearAndFillText(createGeneralTask.name, getRandomString(8));
        scrollDownUsingActions(1);
        waitFor(1);
        createGeneralTask.saveEditTask.click();
    }

    @Then("Verify Link event for {string}")
    public void verifyLinkEventFor(String page) {
        if (page.equalsIgnoreCase("General Contact")) {
            waitForVisibility(crmContactRecordUIPage1.caseIdAfterLink, 10);
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.caseIdAfterLink));
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.nameAfterLink));
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.unlinkButton));
        } else if (page.equalsIgnoreCase("Third party")) {
            waitForVisibility(crmContactRecordUIPage1.caseIdAfterLink, 10);
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.caseIdAfterLink));
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.nameAfterLink));
            assertTrue(isElementDisplayed(crmContactRecordUIPage1.unlinkButton));
        } else if (page.equalsIgnoreCase("Create Task")) {
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(0))));
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(1))));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(0)));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(1)));
        } else if (page.equalsIgnoreCase("Create Task only Link Case")) {
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(0))));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(0)));
        } else if (page.equalsIgnoreCase("Edit Contact Record")) {
            assertTrue(isElementDisplayed(createGeneralTask.generalContactTab));
        } else if (page.equalsIgnoreCase("Edit Task")) {
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(0))));
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(1))));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(0)));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(1)));
        } else if (page.equalsIgnoreCase("Edit Task only Link Case")) {
            assertTrue(isElementDisplayed((createGeneralTask.linkedNames.get(0))));
            assertTrue(isElementDisplayed(createGeneralTask.linkedIds.get(0)));
        }
    }

    @Then("I navigating newly creating Task and clicking to see Task details")
    public void iNavigatingNewlyCreatingTaskAndClickingToSeeTaskDetails() {
        waitFor(6);
        workQueue.taskIDColumnHeader.click();
        waitFor(2);
        taskID.set(workQueue.firstTaskInTable.getText());
        System.out.println("TaskID is : " + taskID.get());
        workQueue.firstTaskInTable.click();
    }

    @Then("Creating {string} task")
    public void creatingTask(String task) {
        if (task.equalsIgnoreCase("Review Complaint")) {
            selectRandomDropDownOption(createGeneralTask.complaintAboutDropdown);
            selectRandomDropDownOption(createGeneralTask.selectReasonDropdown);
            clearAndFillText(createGeneralTask.name, getRandomString(8));
            waitFor(2);
            createGeneralTask.butSaveAfterLink.click();
        }
    }


    @Then("I click validate and link only Link Case for Create Task")
    public void iClickValidateAndLinkOnlyLinkCaseForCreateTask() {
        waitFor(2);
        createGeneralTask.validateTaskbutton.click();
        waitForVisibility(createGeneralTask.linkCaseConsumerButtonAfterValidation, 10);
        createGeneralTask.linkCaseOnly_Chkbox1.click();
        waitFor(2);
        createGeneralTask.linkCaseButton.click();

        scrollUpUsingActions(1);
        selectRandomDropDownOption(createGeneralTask.selectReasonDropdown);
        selectRandomDropDownOption(createGeneralTask.complaintAboutDropdown);
        clearAndFillText(createGeneralTask.name, getRandomString(8));
        scrollDownUsingActions(1);
        waitFor(1);
        createGeneralTask.butSaveAfterLink.click();
    }

    @Given("I log back into CRM")
    public void logIntoSystem() {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        waitFor(6);
        selectDropDown(loginPage.projectId, projectName.get());
        waitFor(4);
        click(loginPage.continueBtn);
        //added wait here to give sometime for UI to parse the response before making config api request.
        waitFor(10);
        waitForPageToLoad(10);
        click(loginPage.acceptAndContinueBtn);
        waitForVisibility(loginPage.userIcon, 5);
    }

    @When("I verify user navigate back to home page")
    public void verifyUserNavigateBackToHomePage() {
        waitForVisibility(crmContactRecordUIPage1.initContact, 10);
        assertTrue(isElementDisplayed(crmContactRecordUIPage1.initContact));
    }

    @When("I verify Translation Service dropdown with options below:")
    public void i_verify_Translation_Service_dropdown_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Amharic")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Arabic")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Bassa")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Bengali")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Chinese (Traditional)")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("English")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Farsi")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("French")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("German")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Hindi")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Ibo")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Korean")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Russian")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Spanish")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Tagalog")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Urdu")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Vietnamese")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Yoruba")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else if (opt.equalsIgnoreCase("Burmese")) {
                selectDropDown(crmActiveContactPage.translationServiceDropDown, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I click on unidentified contact record type radio button")
    public void i_click_on_unidentified_contact_record_type_radio_button() {
        waitForClickablility(crmActiveContactPage.unidentfied, 10);
        waitFor(1);
        scrollToElement(crmActiveContactPage.unidentfied);
        scrollUpUsingActions(1);
        jsClick(crmActiveContactPage.unidentfied);
    }

    @Then("I see {string} field accept  {int} characters")
    public void i_see_field_accept_characters(String field, int stringLength) {

        String actual = "";
        String sentText = createTextString(stringLength);
        switch (field) {
            case "firstName":
                clearAndFillText(crmContactRecordUIPage1.firstName, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.firstName.getAttribute("value");
                break;
            case "lastName":
                clearAndFillText(crmContactRecordUIPage1.lastName, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.lastName.getAttribute("value");
                break;
            case "middleName":
                clearAndFillText(crmContactRecordUIPage1.middleName, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.middleName.getText();
                break;
            case "ssn":
                sentText = random(stringLength, false, true);
                clearAndFillText(crmContactRecordUIPage1.ssnTextbox, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.ssnTextbox.getAttribute("value").replace("-", "");
                break;
            case "dob":
                sentText = random(stringLength, false, true);
                clearAndFillText(crmContactRecordUIPage1.dobTextbox, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.dobTextbox.getAttribute("value").replace("/", "");
                break;
            case "consumerID":
                sentText = random(stringLength, false, true);
                clearAndFillText(crmContactRecordUIPage1.searchConsumerValue, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.consumerIdValue.getText();
            case "addressLine1":
                clearAndFillText(crmContactRecordUIPage1.address1, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.address1.getAttribute("value");
                break;
            case "addressLine2":
                clearAndFillText(crmContactRecordUIPage1.address2, sentText);
                waitFor(1);
                actual = crmContactRecordUIPage1.address2.getAttribute("value");
                break;
            case "city":
                sentText = random(stringLength, true, false);
                clearAndFillText(manualCaseConsumerSearchPage.caseConsumerSearchCity, sentText);
                waitFor(1);
                actual = manualCaseConsumerSearchPage.caseConsumerSearchCity.getAttribute("value");
                break;
            case "county":
                sentText = random(stringLength, true, false);
                clearAndFillText(manualCaseConsumerSearchPage.caseConsumerSearchCounty, sentText);
                waitFor(1);
                actual = manualCaseConsumerSearchPage.caseConsumerSearchCounty.getAttribute("value");
                break;
            case "phone":
                sentText = random(stringLength, false, true);
                clearAndFillText(manualCaseConsumerSearchPage.caseconsumerPhoneNumber, sentText);
                waitFor(1);
                actual = manualCaseConsumerSearchPage.caseconsumerPhoneNumber.getAttribute("value").replace("-", "");
                break;
            case "zipCode":
                sentText = random(stringLength, false, true);
                clearAndFillText(manualCaseConsumerSearchPage.caseConsumerSearchZipCode, sentText);
                waitFor(1);
                actual = manualCaseConsumerSearchPage.caseConsumerSearchZipCode.getAttribute("value");
                break;
        }
        assertTrue(actual.length() <= stringLength);
    }

    @And("I select call campaign for outbound contact type with {string}")
    public void i_select_call_campaign_for_outbound_contact_type_with_channel_type(String conChannelType) {
        if (conChannelType.equalsIgnoreCase("Phone")) {
            waitFor(3);
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.callCampaignDropDown, "Enrollment Reminder");
            waitFor(2);
        } else {
            waitFor(2);
        }
    }

    @Then("I verify required fields are CallerType, ConsumerType, PreferredLanguage, InboundCallQueue, Program, Disposition")
    public void i_verify_required_fields_are_CallerType_ConsumerType_PreferredLanguage_InboundCallQueue_Program_Disposition() {
        crmContactRecordUIPage1.saveContactRecordButton.click();
        assertTrue(crmActiveContactPage.phoneRequired.isDisplayed());
        assertTrue(crmActiveContactPage.programRequired.isDisplayed());
        assertTrue(crmActiveContactPage.dispositionRequired.isDisplayed());

        waitForClickablility(crmActiveContactPage.thirdPartyContactTab, 10);
        waitFor(1);
        scrollToElement(crmActiveContactPage.thirdPartyContactTab);
        scrollUpUsingActions(1);
        jsClick(crmActiveContactPage.thirdPartyContactTab);

        assertTrue(crmActiveContactPage.consumerType.isDisplayed());
    }


    @When("I verify Contact Reason for IN-EB {string} and associated Contact Action")
    public void i_verify_Contact_Reason_for_IN_EB_and_associated_Contact_Action(String opt) {
        List<String> expectedList = new ArrayList<>();
        if (opt.equalsIgnoreCase("Complaint")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "maersk - Supervisor Escalation Form");
            crmActiveContactPage.action("Referral - Anthem").click();
            crmActiveContactPage.action("Referral - Caresource").click();
            crmActiveContactPage.action("Referral - FSSA").click();
            crmActiveContactPage.action("Referral - Managed Health Services").click();
            crmActiveContactPage.action("Referral - MDwise").click();
            crmActiveContactPage.action("Referral - Southeast Trans").click();
            crmActiveContactPage.action("Referral - United Healthcare").click();
            crmActiveContactPage.action("State").click();
        } else if (opt.equalsIgnoreCase("Enrollment")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            expectedList.addAll(Arrays.asList("Education - Health Plan", "Plan Selection Form"));
            editPageDropListcheck(crmActiveContactPage.contactAct, expectedList);
        } else if (opt.equalsIgnoreCase("HCC Outbound")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Manual Outbound");
            crmActiveContactPage.action("Outbound Dialer").click();
            crmActiveContactPage.action("Research Check").click();
        } else if (opt.equalsIgnoreCase("HIP 2.0")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "01 HIP 2.0 Inquiry");
            crmActiveContactPage.action("02 HIP 2.0 Application Request").click();
            crmActiveContactPage.action("03 HIP 2.0 Plan/Provider Information").click();
            crmActiveContactPage.action("04 HIP 2.0 Plan Selection").click();
            crmActiveContactPage.action("05 HIP 2.0 Transition Question").click();
            crmActiveContactPage.action("06 HIP 2.0 Plus vs. Basic").click();
            crmActiveContactPage.action("07 HIP 2.0 Power Account").click();
            crmActiveContactPage.action("08 Eligibility/General Information").click();
            crmActiveContactPage.action("09 Eligibility/Referral to DFR").click();
            crmActiveContactPage.action("10 HIP 2.0 Benefits/Covered Services").click();
            crmActiveContactPage.action("11 HIP 2.0 Plan Change Denied Due to Transition").click();
            crmActiveContactPage.action("12 HIP 2.0 Referral to Plan").click();
            crmActiveContactPage.action("14 HH Plan Assistance - Child").click();
            crmActiveContactPage.action("15 HH Plan Assistance - Adult").click();
            crmActiveContactPage.action("16 HH Plan Assistance - Family").click();
            crmActiveContactPage.action("Document Tobacco No").click();
            crmActiveContactPage.action("Document Tobacco Refused").click();
            crmActiveContactPage.action("Document Tobacco Yes").click();
        } else if (opt.equalsIgnoreCase("Inquiry AE/OE")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            expectedList.add("Education - Choice Period");
            editPageDropListcheck(crmActiveContactPage.contactAct, expectedList);
        } else if (opt.equalsIgnoreCase("Inquiry Application/Eligibility")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Education - Application");
            crmActiveContactPage.action("Referral - FSSA").click();
        } else if (opt.equalsIgnoreCase("Inquiry Billing")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Referral - Gainwell Medworks");
            crmActiveContactPage.action("Referral - Gainwell Package C").click();
            crmActiveContactPage.action("Referral - Health Plan").click();
            crmActiveContactPage.action("Referral - Traditional Medicaid").click();
        } else if (opt.equalsIgnoreCase("Inquiry Covered Benefits")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Dental/Vision - Health Plan");
            crmActiveContactPage.action("Dental/Vision - Traditional Medicaid").click();
            crmActiveContactPage.action("Education - Health Plan").click();
            crmActiveContactPage.action("Pharmacy - Health Plan").click();
            crmActiveContactPage.action("Pharmacy - Traditional Medicaid").click();
            crmActiveContactPage.action("Referral - Health Plan").click();
            crmActiveContactPage.action("Referral - Traditional Medicaid").click();
            crmActiveContactPage.action("Transportation - Health Plan").click();
            crmActiveContactPage.action("Transportation - Traditional Medicaid").click();
        } else if (opt.equalsIgnoreCase("Inquiry Disenrollment")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Education - Disenrollment Form");
            crmActiveContactPage.action("Plan Selection Form").click();
        } else if (opt.equalsIgnoreCase("Inquiry Guardianship/Authorized Representative")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
        } else if (opt.equalsIgnoreCase("Inquiry General Information")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            expectedList.add("Education - General Program Information");
            editPageDropListcheck(crmActiveContactPage.contactAct, expectedList);
        } else if (opt.equalsIgnoreCase("Inquiry Health Plan Contact Information")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            expectedList.add("Education - Health Plan");
            editPageDropListcheck(crmActiveContactPage.contactAct, expectedList);
        } else if (opt.equalsIgnoreCase("Inquiry PMP")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "PMP Availability Search");
            crmActiveContactPage.action("Referral - Health Plan").click();
        } else if (opt.equalsIgnoreCase("Inquiry Replacement Card")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Referral - Health Plan");
            crmActiveContactPage.action("Referral - Traditional Medicaid").click();
        } else if (opt.equalsIgnoreCase("Just Cause")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Just Cause Form");
            crmActiveContactPage.action("Just Cause Status Update").click();
            crmActiveContactPage.action("Referral - Health Plan Grievance").click();
        } else if (opt.equalsIgnoreCase("Member Information Update")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Address Change Request Form");
            crmActiveContactPage.action("Referral - FSSA").click();
        } else if (opt.equalsIgnoreCase("Other")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Call Disconnected");
            crmActiveContactPage.action("Education - Gateway to Work").click();
            crmActiveContactPage.action("Education - Virtual Care at Home").click();
            crmActiveContactPage.action("Health Insurance Marketplace").click();
            crmActiveContactPage.action("Health Insurance Marketplace").click();
            crmActiveContactPage.action("Referral - FSSA").click();
            crmActiveContactPage.action("Referral - Gainwell HPE").click();
            crmActiveContactPage.action("Referral - Gainwell Third Party Liability").click();
            crmActiveContactPage.action("Referral - SNAP").click();
            crmActiveContactPage.action("Supervisor Escalation").click();
        } else if (opt.equalsIgnoreCase("Right Choices Program")) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            expectedList.add("Referral - Health Plan");
            editPageDropListcheck(crmActiveContactPage.contactAct, expectedList);
        } else if ("Inquiry Covered Benefits - Education-Health Plan".equals(opt)) {
            selectDropDown(crmActiveContactPage.contactRes, "Inquiry Covered Benefits");
            waitFor(1);
            crmActiveContactPage.contactAct.click();
            waitFor(1);
            assertTrue(crmActiveContactPage.contactReasonEducationHealthPlan.isDisplayed(), "Education-Health Plan is not displayed in Contact Action dropdown");
            actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
        } else if ("Inquiry Covered Benefits - NO Education-Health Plan".equals(opt)) {
            selectDropDown(crmActiveContactPage.contactRes, "Inquiry Covered Benefits");
            waitFor(1);
            crmActiveContactPage.contactAct.click();
            waitFor(1);
            assertFalse(isElementDisplayed(crmActiveContactPage.contactReasonEducationHealthPlan), "Education-Health Plan is displayed in Contact Action dropdown");
            actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
        } else if ("Transfer".equals(opt)) {
            selectDropDown(crmActiveContactPage.contactRes, opt);
            selectDropDown(crmActiveContactPage.contactAct, "Transfer-FSSA");
            crmActiveContactPage.action("Transfer-Health Plan").click();
            crmActiveContactPage.action("Transfer-Member Request").click();
            crmActiveContactPage.action("Transfer-Wrong Number").click();
            actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
        }
    }

    @And("I verify autopopulated value on Contact details component")
    public void IverifyautopopulatedvalueonContactdetailscomponent() {
        assertEquals(crmContactRecordUIPage1.contactType.getText(), "Outbound", "Contact Type is mismatch");
        assertEquals(crmContactRecordUIPage1.contactChannelType.getText(), "Phone", "Contact Channel is mismatch");
        String phNumber = crmContactRecordUIPage1.phoneNumber.getAttribute("value").replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        assertEquals(phNumber, CRM_GeneralTaskStepDef.taskValues.get().get("preferredPhone"), "phoneNumber is mismatch");
        assertEquals(crmContactRecordUIPage1.contactReason.getText(), CRM_GeneralTaskStepDef.taskValues.get().get("contactReason").split(",")[0], "Contact Reason is mismatch");
    }

    @And("I verify contact type,contact channel, phone, contact Reason fields are editable fields on active contact ui")
    public void Iverifycontactdetailsfieldsareeditablefields() {
        assertTrue(crmContactRecordUIPage1.contactType.isEnabled(), "Contact Type is not editable");
        assertTrue(crmContactRecordUIPage1.contactChannelType.isEnabled(), "Contact Channel is not editable");
        assertTrue(crmContactRecordUIPage1.phoneNumber.isEnabled(), "Phone is not editable");
        assertTrue(crmContactRecordUIPage1.contactReason.isEnabled(), "contact Reason is not editable");
    }

    @Given("I started to login with {string} and choose {string}")
    public void i_started_to_login_with_and_choose(String username, String project) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        clearAndFillText(loginPage.loginUserNameOrEmail, username);
        loginPage.nextBtn.click();
        clearAndFillText(loginPage.passwordCRM, password);
        loginPage.signInBtn.click();
        waitFor(4);
        selectDropDown(loginPage.selectProject, project);
    }

    @Then("I am able to see default role populated in user role dropdown")
    public void i_am_able_to_see_default_role_populated_in_user_role_dropdown() {

        assertTrue(loginPage.userRoleDropDown.isDisplayed());
        assertEquals(loginPage.userRoleDropDown.getText(), "Call Center Supervisor");
    }

    @Then("I am able to see multiple roles populated in user role dropdown")
    public void i_am_able_to_see_multiple_roles_populated_in_user_role_dropdown() {
        assertTrue(loginPage.userRoleDropDown.isDisplayed());
        selectDropDown(loginPage.userRoleDropDown, "Mailroom Supervisor");
        assertEquals(loginPage.userRoleDropDown.getText(), "Mailroom Supervisor");
        selectDropDown(loginPage.userRoleDropDown, "Mailroom Specialist");
        assertEquals(loginPage.userRoleDropDown.getText(), "Mailroom Specialist");

    }


    @Then("I see Business Unit must be selected before Contact Reason message displayed")
    public void iSeeBusinessUnitMustBeSelectedBeforeContactReasonMessageDisplayed() throws Throwable {
        assertTrue(crmContactRecordUIPage1.businessUnitMustBeSelectedWarningMessage.isDisplayed(), "\"Business Unit must be selected...\" warning message is not displayed");
    }


    @When("I verify Contact Reason for CoverVA has no selection options")
    public void i_verify_Contact_Reason_for_Cover_VA_has_no_selection_option() {
        crmContactRecordUIPage1.contactReason.click();
        assertEquals(crmContactRecordUIPage1.contactReason.getText(), "");
    }

    @When("I verify user role is read only text")
    public void i_verify_user_role_is_readonly_text() {
        assertTrue(crmContactRecordUIPage1.userRoleTextQA.isDisplayed());
        assertFalse(crmContactRecordUIPage1.userRoleTextQA.getTagName().equals("input"));
    }

    @Then("I verify Successfully Unlink on Correspondence")
    public void iVerifySuccessUnlinkonCorrespondence() {
        waitFor(15);
        crOutCor.warningMessage_Continue.click();
        String message = CorresPage.SuccessMessage.getText();
        System.out.println("success Message : " + message);
        Assert.assertEquals("SUCCESS MESSAGE", message);

    }

    @Then("I add Medicaid and save it")
    public void iadd_random_medicaid_id() {
        jsClick(crmCreateConsumerProfilePage.externalIdAdd);
        waitFor(1);
        jsClick(crmCreateConsumerProfilePage.externalIdDropdown.get(0));
        crmCreateConsumerProfilePage.externalIdDropdown.get(0).click();
        waitFor(2);
        jsClick(crmCreateConsumerProfilePage.externalIdDropdownMedicAIDvalue);
        consumerId.set(getRandomNumber(10));
        clearAndFillText(crmCreateConsumerProfilePage.externalIdInputField.get(0), consumerId.get());
    }

    @Then("I verify Medicaid ID displayed in link section as ID for the consumer")
    public void iverify_medicaid_id_in_link_section() {
        waitFor(2);
        scrollToElement(crmContactRecordUIPage1.linkSectionID);
        assertTrue(crmContactRecordUIPage1.linkSectionID.getText().equals(consumerId.get()), "ID is different");
    }

    @When("I select All program types")
    public void selectAllProgramTypes() {
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HCC");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HHW");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HIP");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "Traditional Medicaid");
    }

    @When("I navigate to {string} contact record type")
    public void i_navigate_on_task_type(String crType) {

        switch (crType) {
            case "ThirdParty":
                waitForClickablility(crmActiveContactPage.thirdPartyContactTab, 10);
                waitFor(1);
                scrollToElement(crmActiveContactPage.thirdPartyContactTab);
                scrollUpUsingActions(1);
                jsClick(crmActiveContactPage.thirdPartyContactTab);
                break;
            case "Unidentified":
                waitForClickablility(crmActiveContactPage.unidentfied, 10);
                waitFor(1);
                scrollToElement(crmActiveContactPage.unidentfied);
                scrollUpUsingActions(1);
                jsClick(crmActiveContactPage.unidentfied);
                break;
        }
    }

    @When("I verify Contact Reason and associated Contact Action")
    public void i_verify_Contact_Reason_and_associated_Contact_Action(List<String> options) {

        for (String opt : options) {

            if (opt.equalsIgnoreCase("Complaint")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "maersk - Supervisor Escalation Form",
                        "Referral - Anthem",
                        "Referral - Caresource",
                        "Referral - FSSA",
                        "Referral - Managed Health Services",
                        "Referral - MDwise",
                        "Referral - Southeast Trans",
                        "Referral - United Healthcare",
                        "State");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Enrollment")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Health Plan",
                        "Plan Selection Form");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("HCC Outbound")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Manual Outbound",
                        "Outbound Dialer",
                        "Research Check");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("HIP 2.0")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "01 HIP 2.0 Inquiry",
                        "02 HIP 2.0 Application Request",
                        "03 HIP 2.0 Plan/Provider Information",
                        "04 HIP 2.0 Plan Selection",
                        "05 HIP 2.0 Transition Question",
                        "06 HIP 2.0 Plus vs. Basic",
                        "07 HIP 2.0 Power Account",
                        "08 Eligibility/General Information",
                        "09 Eligibility/Referral to DFR",
                        "10 HIP 2.0 Benefits/Covered Services",
                        "11 HIP 2.0 Plan Change Denied Due to Transition",
                        "12 HIP 2.0 Referral to Plan",
                        "14 HH Plan Assistance - Child",
                        "15 HH Plan Assistance - Adult",
                        "16 HH Plan Assistance - Family",
                        "Document Tobacco No",
                        "Document Tobacco Refused",
                        "Document Tobacco Yes");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry AE/OE")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Choice Period");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Application/Eligibility")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Application",
                        "Referral - FSSA");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Billing")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Referral - Gainwell Medworks",
                        "Referral - Gainwell Package C",
                        "Referral - Health Plan",
                        "Referral - Traditional Medicaid");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Covered Benefits")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Dental/Vision - Health Plan",
                        "Dental/Vision - Traditional Medicaid",
                        "Education - Health Plan",
                        "Pharmacy - Health Plan",
                        "Pharmacy - Traditional Medicaid",
                        "Referral - Health Plan",
                        "Referral - Traditional Medicaid",
                        "Transportation - Health Plan",
                        "Transportation - Traditional Medicaid");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Disenrollment")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Disenrollment Form",
                        "Plan Selection Form");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Guardianship/Authorized Representative")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Guardianship/AR");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry General Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - General Program Information");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Health Plan Contact Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Education - Health Plan");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry PMP")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "PMP Availability Search",
                        "Referral - Health Plan");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inquiry Replacement Card")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Referral - Health Plan",
                        "Referral - Traditional Medicaid");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Just Cause")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Just Cause Form",
                        "Just Cause Status Update",
                        "Referral - Health Plan Grievance");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Member Information Update")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Address Change Request Form",
                        "Referral - FSSA");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Call Disconnected",
                        "Education - Gateway to Work",
                        "Education - Virtual Care at Home",
                        "Health Insurance Marketplace",
                        "Referral - FSSA",
                        "Referral - Gainwell HPE",
                        "Referral - Gainwell Third Party Liability",
                        "Referral - SNAP",
                        "Supervisor Escalation");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Right Choices Program")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Referral - Health Plan");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Transfer")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Transfer-FSSA",
                        "Transfer-Health Plan",
                        "Transfer-Member Request",
                        "Transfer-Wrong Number");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            }
        }
    }

    @When("I verify Contact Reason and associated Contact Action dependency with program selection")
    public void i_verify_Contact_Reason_and_associated_Contact_Action_Program_Dependency() {
        if (programs.get()[0].equals("HIP") && programs.get()[1].equals("HCC")) {
            selectDropDown(crmActiveContactPage.contactRes, "Inquiry Billing");
            List<String> complaintActions = Arrays.asList(
                    "Referral - Gainwell Medworks",
                    "Referral - Health Plan",
                    "Referral - Traditional Medicaid");
            verifyMultiSelectDropDownValues("contactAction", complaintActions);
        } else if (programs.get()[0].equals("HHW") && programs.get()[1].equals("Traditional Medicaid")) {
            selectDropDown(crmActiveContactPage.contactRes, "Inquiry Billing");
            List<String> complaintActions = Arrays.asList(
                    "Referral - Gainwell Package C",
                    "Referral - Health Plan",
                    "Referral - Traditional Medicaid");
            verifyMultiSelectDropDownValues("contactAction", complaintActions);
        }
    }

    @When("I verify Contact Reason suppress depending on program selection")
    public void i_verify_Contact_Reason_suppress_with_Program_selection() {
        if (programs.get()[0].equals("HIP") && programs.get()[1].equals("HHW")) {
            verifySuppressedValueFromDD("contactReason", "HCC Outbound");
        } else if (programs.get()[0].equals("HHW") && programs.get()[1].equals("HCC")) {
            verifySuppressedValueFromDD("contactReason", "HIP 2.0");
        } else if (programs.get()[0].equals("HIP") && programs.get()[1].equals("Traditional Medicaid")) {
            verifySuppressedValueFromDD("contactReason", "Inquiry General Information");
        }
    }

    @When("I select {string} program types")
    public void selectAllProgramTypes(String program) {
        emptyMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram); //empty dd first
        programs.set(program.split(","));
        waitFor(2);
        if (program.equalsIgnoreCase("All")) {
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HCC");
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HHW");
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "HIP");
            selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, "Traditional Medicaid");
        } else {
            for (String prog : programs.get()) {
                selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.lstSelectProgram, prog);
            }
        }
    }

    @When("I navigate {string} contact type")
    public void navigateToContactType(String contactType) {
        waitFor(1);
        if (contactType.equals("General")) {
            crmContactRecordUIPage1.generalContact.click();
        } else if (contactType.equals("Third Party")) {
            crmContactRecordUIPage1.thirdPartyContact.click();
        } else if (contactType.equals("Unidentified")) {
            crmContactRecordUIPage1.unidentifiedContact.click();
        }
    }

    @When("I verify error message for action selection reason without program")
    public void i_verify_call_summary_error_msg_as() {
        assertEquals(crmContactRecordUIPage1.reasonSelectWithoutProgramErrorMsg.getText(),
                "A Program must be selected before a Reason can be chosen.", "Error message is incorrect or missing");
    }

    @When("I try to select any reason from dropdown")
    public void i_try_to_select_random_value_from_dd() {
        click(crmContactRecordUIPage1.disabledContactReason);
    }

    @When("I select reason and action")
    public void i_select_random_value_for_reason_and_action() {
        selectDropDown(crmContactRecordUIPage1.contactReason, "Complaint");
        selectDropDown(crmContactRecordUIPage1.contactAction, "State");
        actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
    }

    @When("I verify reason and action dropdowns are cleared")
    public void i_verify_reason_and_action_dropdowns_are_cleared() {
        waitFor(2);
        assertTrue(crmContactRecordUIPage1.reasonDdValue.getText().isEmpty(), "reason dropdown was not cleared");
        assertTrue(crmContactRecordUIPage1.actionDdValue.getText().isEmpty(), "action dropdown was not cleared");
    }

    private void verifyMultiSelectDropDownValues(String ddName, List<String> expectedValues) {
        waitFor(1);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        dropdown.click();
        waitFor(2);
        List<String> actualValues = new ArrayList<>();
        List<WebElement> dropdownVlu =
                Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li"));
        for (int i = 0; i < dropdownVlu.size(); i++) {
            actualValues.add(dropdownVlu.get(i).getText());
        }
        assertEquals(actualValues, expectedValues, ddName + " drop down values are incorrect");
        actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
    }

    private void emptyMultiSelectDropDown(WebElement ele) {
        waitFor(1);
        ele.click();
        for (int i = 0; i < myTask.multiSelectedVlus.size(); ) {
            click(myTask.multiSelectedVlus.get(i++));
            i--;
            waitFor(1);
        }
        actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
    }

    private void verifySuppressedValueFromDD(String ddName, String suppressed) {
        waitFor(2);
        WebElement dropdown =
                Driver.getDriver().findElement(By.xpath("//input[@id='" + ddName + "']/.."));
        hover(dropdown);
        dropdown.click();
        waitFor(2);
        List<String> actualValues = new ArrayList<>();
        List<WebElement> dropdownVlu =
                Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + ddName + "')]//ul/li"));
        for (int i = 0; i < dropdownVlu.size(); i++) {
            actualValues.add(dropdownVlu.get(i).getText());
        }
        assertFalse(actualValues.contains(suppressed), ddName + " drop down contains suppressed value " + suppressed);
        actions.moveToElement(crmContactRecordUIPage1.generalContact).click().perform();
    }

    @When("I populate Search criteria fields for a new consumer for third party contact")
    public void i_populate_Search_criteria_fields_for_a_new_consumer_for_3rdparty() {
        waitFor(1);
        newConsumer.set(new HashMap<>(getNewTestData2()));
        //System.out.println(newConsumer.keySet());
        clearAndFillText(thirdPartyDetails.ccSearchThirdPartyFirstName, (newConsumer.get().get("name")));
        clearAndFillText(thirdPartyDetails.ccSearchThirdPartyLastName, (newConsumer.get().get("surname")));
        System.out.println((newConsumer.get().get("name") + " " + newConsumer.get().get("surname")));
    }

    @Then("I see Search criteria fields have no value for third party contact")
    public void i_see_Search_criteria_fields_have_no_value_for_3rdparty() {
        waitFor(2);
        assertTrue(thirdPartyDetails.thirdPartyFirstName.getAttribute("value").isEmpty());
        assertTrue(thirdPartyDetails.thirdPartyLastName.getAttribute("value").isEmpty());
    }

    @Then("I verify case not have PI")
    public void I_verify_case_not_have_PI() {
        assertTrue(isElementDisplayed(crmContactRecordUIPage1.noRecordsAvailableTextOnPITable), "PI table not empty");
    }

    @Then("I verify older member became a PI on this case")
    public void I_verify_older_member_became_a_PI_on_this_case() {
        assertFalse(isElementDisplayed(crmContactRecordUIPage1.noRecordsAvailableTextOnPITable), "PI table empty");
    }

    @Then("I see error message for {string} with {int}")
    public void iSeeErrorMessageForWith(String fieldName, int characters) {
        waitFor(3);
        if (fieldName.equals("FIRST NAME") || fieldName.equals("LAST NAME")) {
            hasLeastLengthErrorMessage(crmContactRecordUIPage1.genericErrorMessage, fieldName, characters);
        } else if (fieldName.equals("SSN") || fieldName.equals("PHONE")) {
            hasMinLengthErrorMessage(crmContactRecordUIPage1.genericErrorMessage, fieldName, characters);

        }

    }

    @And("I search for consumer by Phone and enter {string}")
    public void iSearchForConsumerByPhoneAndEnter(String incompletePhoneNum) {
        crmContactRecordUIPage1.consumerPhoneNumber.sendKeys(incompletePhoneNum);
    }

    @Then("I should see Create Outreach Session option")
    public void i_should_see_Create_Outreach_Session_option() {
        waitFor(2);
        assertTrue(crmContactRecordUIPage1.createOutReachSessionOption.isDisplayed());
    }

    @When("I verify Contact Reason and default Contact Action")
    public void i_verify_Contact_Reason_and_default_Contact_Action(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Complaint")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Enrollment")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Health Plan1", "default value is incorrect");
                selectMultiSelectDropDown(crmActiveContactPage.contactAct, "Plan Selection Form");
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Health Plan1,Plan Selection Form", "default value is incorrect"); //AC2.0
            } else if (opt.equalsIgnoreCase("HCC Outbound")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("HIP 2.0")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry AE/OE")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Choice Period", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Application/Eligibility")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Billing")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Covered Benefits")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Disenrollment")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Plan Selection Form1", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Guardianship/Authorized Representative")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Guardianship/AR", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry General Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - General Program Information", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Health Plan Contact Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Health Plan", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry PMP")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Inquiry Replacement Card")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Just Cause")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Member Information Update")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Referral - FSSA", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Right Choices Program")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Referral - Health Plan5", "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Transfer")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertTrue(crmContactRecordUIPage1.contactActionInput.getAttribute("value").isEmpty(), "default value is incorrect");
            }
        }
    }

    @When("I verify cannot unselect some default Contact Action")
    public void i_verify_cannot_unselect_some_default_Contact_Action(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Enrollment")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Health Plan1", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionEducationHealthPlan.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionEducationHealthPlan.sendKeys(Keys.ESCAPE);
            } else if (opt.equalsIgnoreCase("Inquiry AE/OE")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Choice Period", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionEducationChoicePeriod.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionEducationChoicePeriod.sendKeys(Keys.ESCAPE);
            } else if (opt.equalsIgnoreCase("Inquiry Guardianship/Authorized Representative")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Guardianship/AR", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionEducationGuardianshipAr.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionEducationGuardianshipAr.sendKeys(Keys.ESCAPE);
            } else if (opt.equalsIgnoreCase("Inquiry General Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - General Program Information", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionEducationGeneralProgramInformation.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionEducationGeneralProgramInformation.sendKeys(Keys.ESCAPE);
            } else if (opt.equalsIgnoreCase("Inquiry Health Plan Contact Information")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Education - Health Plan", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionEducationHealthPlan.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionEducationHealthPlan.sendKeys(Keys.ESCAPE);
            } else if (opt.equalsIgnoreCase("Right Choices Program")) {
                selectDropDown(crmActiveContactPage.contactRes, opt);
                assertEquals(crmContactRecordUIPage1.contactActionInput.getAttribute("value"), "Referral - Health Plan5", "default value is incorrect");
                crmActiveContactPage.contactAct.click();
                assertTrue(crmContactRecordUIPage1.contactActionReferralHealthPlan.getAttribute("aria-disabled").equals("true"));
                crmContactRecordUIPage1.contactActionReferralHealthPlan.sendKeys(Keys.ESCAPE);
            }
        }
    }

    @And("I fill Call Summary field with {string} and save")
    public void iFillCallSummaryFieldWithAndSave(String data) {
        if ("Second call summary".equals(data) || "Third call summary".equals(data)) {
            crmContactRecordUIPage1.additionCommentBox.click();
            waitFor(1);
        }
        crmContactRecordUIPage1.additionalComments.click();
        sendKeyToTextField(crmContactRecordUIPage1.additionalComments, data);
        crmContactRecordUIPage1.saveCallSummaryButton.click();
    }

    @And("I select {string} from contact reason with {string} Contact Action for ineb and save")
    public void iSelectFromContactReasonWithContactActionForInebAndSave(String reason, String action) {
        scrollUp();
        selectDropDown(crmContactRecordUIPage1.contactReason, reason);
        waitFor(2);
        crmContactRecordUIPage1.contactAction.click();
        waitFor(1);
        WebElement contactActionValue = Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + action + "')]"));
        contactActionValue.click();
        int counter = 0;
        WebElement webPage = Driver.getDriver().findElement(By.xpath("//body"));
        do {
            webPage.click();
            counter++;
        } while (null == webPage.getAttribute("style") || counter == 5);
        jsClick(crmContactRecordUIPage1.reasonSaveButton);
    }

    public void editPageDropListcheck(WebElement dropType, List<String> expectedList) {
        waitFor(1);
        dropType.click();
        waitFor(2);
        List<String> expected = new ArrayList<>();
        expectedList.forEach(each -> expected.add(each));
        List<String> actual = new ArrayList<>();
        List<WebElement> droplist = new ArrayList<>(Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']/li")));
        droplist.forEach(each -> actual.add(each.getText()));
        Collections.sort(expected);
        Collections.sort(actual);
        System.out.println("actual = " + actual);
        System.out.println("expected = " + expected);
        Assert.assertEquals(actual, expected, "List actual Values did not meet expected list");
        actions.sendKeys(Keys.ESCAPE).build().perform();
    }

    @Then("I verify claim id is displayed")
    public void i_verify_claim_id_is_displayed() {
        assertTrue(crmContactRecordUIPage1.claimIDField.isDisplayed());
    }

    @When("I verify DC_EB Unidentified Caller Type drop down with options below:")
    public void i_verify_DC_EB_Unidentified_Caller_Type_drop_down_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Anonymous")) {
                selectDropDown(crmActiveContactPage.callerType, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I verify DC_EB  Unidentified Preferred Language drop down with options below:")
    public void i_verify_DC_EB_Unidentified_Preferred_Language_drop_down_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("English")) {
                selectDropDown(crmActiveContactPage.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(crmActiveContactPage.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Russian")) {
                selectDropDown(crmActiveContactPage.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Spanish")) {
                selectDropDown(crmActiveContactPage.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Vietnamese")) {
                selectDropDown(crmActiveContactPage.preferredLanguageCode, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I choose a contact reason and contact action from dropdown list for NJ-SBE")
    public void i_choose_a_contact_reason_and_a_contact_action_from_dropdown_list_for_NJSBE() {
        selectDropDown(crmContactRecordUIPage1.contactReason, "Appeal");
        selectOptionFromMultiSelectDropDown(crmContactRecordUIPage1.contactAction, "Follow Up");
        waitFor(1);
        crmContactRecordUIPage1.saveReasonButton.click();
    }
}


