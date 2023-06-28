package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.applicationCodeAPI;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationMemberStepDef.programList;
//import static com.maersk.crm.utilities.ApiBaseClass.loginStepDef;
import static org.testng.Assert.*;

public class CRM_CreateApplicationStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    BrowserUtils browserUtils = new BrowserUtils();
    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    CRM_ContactRecordUIStepDef crm_contactRecordUIStepDef = new CRM_ContactRecordUIStepDef();
    Map<String, Object> newPrimaryIndividual = new HashMap<>(getNewTestData2());
    String expectedPrimaryIndividualName = newPrimaryIndividual.get("name").toString() + " " + newPrimaryIndividual.get("name").toString().charAt(1) + " " + newPrimaryIndividual.get("surname").toString();
    CRMMemberMatchingPage memberMatchingPage = new CRMMemberMatchingPage();
    CRMCreateApplicationMemberPage createApplicationMemberPage = new CRMCreateApplicationMemberPage();
    ATSAddAuthorizedRepresentativePage addAuthorizedRepresentativePage = new ATSAddAuthorizedRepresentativePage();
    UIAutoUitilities uiAutoUitilities = new UIAutoUitilities();
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    private ApiTestDataUtil sendEventTdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private List<String> actualLabels = new ArrayList<>();
    private List<String> expectedLabels = new ArrayList<>();
    private List<String> pICIactualLabels = new ArrayList<>();
    private List<String> pICIexpectedLabels = new ArrayList<>();
    private List<String> residenceActualLabels = new ArrayList<>();
    private List<String> residenceExpectedLabels = new ArrayList<>();
    private List<String> mailActualLabels = new ArrayList<>();
    private List<String> mailExpectedLabels = new ArrayList<>();
    private List<String> programActualLabels = new ArrayList<>();
    private List<String> programExpectedLabels = new ArrayList<>();
    private List<String> applicationActualLabels = new ArrayList<>();
    private List<String> applicationExpectedLabels = new ArrayList<>();
    private List<String> commOPtINInfo = new ArrayList<>();
    public static List<String> programListPI = new ArrayList<>();
    private List<String> expectedCycleList = new ArrayList<>();
    private List<String> actualCycleList = new ArrayList<>();
    List<String> expectedGenderList = new ArrayList<>(Arrays.asList("", "Female", "Male", "Neutral", "Other", "Unknown"));
    List<String> expectednoOfBabiesList = new ArrayList<>(Arrays.asList("", "1", "2", "3", "4", "5", "6", "7", "8"));
    List<String> expectedSpokenLangList = new ArrayList<>(Arrays.asList("", "English", "Other", "Russian", "Spanish", "Vietnamese"));
    List<String> expectedWrittenLangList = new ArrayList<>(Arrays.asList("", "Braille English", "Braille Spanish", "English", "Other", "Russian", "Spanish", "Vietnamese"));
    List<String> expectedExIdTypeList = new ArrayList<>(Arrays.asList("", "CHIP", "Internal", "Medicaid"));
    List<String> expectedChannelList = new ArrayList<>(Arrays.asList("", "Email", "Fax", "Interface", "Mail", "Phone", "Web", "Web Chat"));
    public static String piFirstName;
    public static String piMiddleInitial;
    public static String piLastName;
    public static String piSuffix;
    public static String piDOB;
    public static String piGender;
    public static String piAreYouPregnant;
    public static String piBabiesExpected;
    public static String piExpectedDueDate;
    public static String piSSN;
    public static String piSpokenLanguage;
    public static String piWrittenLanguage;
    public static String piExtConId;
    public static String piExtIdType;
    public static String cellPhone;
    private String homePhone;
    private String workPhone;
    private String faxNumber;
    private String email;
    public static String residenceAddressLineOne;
    public static String residenceAddressLineTwo;
    public static String residenceCity;
    public static String residenceState;
    public static String residenceZipcode;
    public static String residenceCounty;
    private String mailingAddressLineOne;
    private String mailingAddressLineTwo;
    private String mailingCity;
    private String mailingState;
    private String mailingZipcode;
    public static String cycle;
    public static String receivedDate;
    public static String channel;
    public static String signature;
    public static String signatureDate;
    public static String applicationId;
    private JsonObject requestParams;
    public static String accountUserId;
    public static String priority;
    public static String extAppID;
    public static String applicatonType;
    public static String consumerType;
    public static String receivedLanguage;
    public static String applicationCode;
    public static HashMap<String, String> applicantInformation = new HashMap<>();


    @Then("I should see Create Application option")
    public void i_should_see_Create_Application_option() {
        waitForVisibility(createApplication.createApplicationButton, 1);
        assertTrue(createApplication.createApplicationButton.isDisplayed(), "Create application button isnt displayed");
    }

    @Then("I verify that there are two application types")
    public void i_verify_that_there_are_two_application_types() {
        waitFor(3);
        hover(createApplication.createApplicationButton);
        assertEquals(createApplication.createApplicationSubMenuList.size(), 2, "Sub-menu doesnt have two application types");
        waitFor(10);
    }

    @When("I navigate to Create {string} application page")
    public void i_navigate_to_Create_application_page(String applicationType) {
        waitForVisibility(dashBoard.btnMenuList, 5);
        jsClick(dashBoard.btnMenuList);
        waitForVisibility(createApplication.createApplicationButton, 3);
        waitForClickablility(createApplication.createApplicationButton, 5);
        assertTrue(createApplication.createApplicationButton.isDisplayed());
        waitFor(2);
        createApplication.crateApplicationMenu.click();
        waitFor(5);
        if (applicationType.equals("LONG TERM CARE")) {
            waitForVisibility(createApplication.longTermCare, 5);
            jsClick(createApplication.longTermCare);
            try {
                createApplication.applicationPageHeader.isDisplayed();
            } catch (NoSuchElementException e) {
                jsClick(createApplication.crateApplicationMenu);
                waitFor(2);
                jsClick(createApplication.longTermCare);
                waitFor(1);
            }
            this.applicatonType = "Long Term Care";

        } else if (applicationType.equals("MEDICAL ASSISTANCE")) {
            if(!LoginStepDef.projectName1.get().equals("BLATS2")) {
                waitForVisibility(createApplication.medicalAssistance, 5);
                jsClick(createApplication.medicalAssistance);
                try {
                    createApplication.applicationPageHeader.isDisplayed();
                } catch (NoSuchElementException e) {
                    jsClick(createApplication.crateApplicationMenu);
                    waitFor(2);
                    jsClick(createApplication.medicalAssistance);
                    waitFor(1);
                }
            }
            this.applicatonType = "Medical Assistance";

        }
    }

    @Then("I verify {string} Application Page header is displayed")
    public void i_verify_Application_Page_header_is_displayed(String applicationType) {
        waitFor(3);
        if (applicationType.equals("Long Term Care")) {
            System.out.println("Long term care header:" + createApplication.applicationPageHeader.getText());
            assertTrue(createApplication.applicationPageHeader.getText().equals("APPLICATION FOR LONG TERM CARE"), "Long term care header didnt match");
        } else if (applicationType.equals("Medical Assistance")) {
            System.out.println("Medical assistance header: " + createApplication.applicationPageHeader.getText());
            assertTrue(createApplication.applicationPageHeader.getText().equals("APPLICATION FOR MEDICAL ASSISTANCE"), "Medical assistance header didnt match");
        }
    }

    @Then("I verify Back Arrow button is displayed on Create Application screen")
    public void i_verify_Back_Arrow_button_is_displayed_on_Create_Application_screen() {
        assertTrue(createApplication.backArrowCreateApp.isDisplayed(), "Back arrow isnt displayed on Create Application screen");
        assertTrue(createApplication.backArrowName.isDisplayed(), "Back arrow name is not correct");
    }

    @Then("I click on back arrow button on create application page")
    public void i_click_on_back_arrow_button_on_create_application_page() {
        waitForClickablility(createApplication.backArrowCreateApp, 5);
        highLightElement(createApplication.backArrowCreateApp);
        jsClick(createApplication.backArrowCreateApp);
    }

    @Then("I verify that user is navigated\\/stayed {string} page")
    public void i_verify_that_user_is_navigated_stayed_page(String page) {
        if (page.equals("Dashboard")) {
            assertTrue(contactRecord.initContact.isDisplayed(), "Initiate contact button isnt displayed");
            assertTrue(Driver.getDriver().getTitle().contains("Dashboard"), "Page title for dashboard isnt matched");
        }
        if (page.equals("Active Contact")) {
            assertTrue(contactRecord.endContactButton.isDisplayed(), "End contact button isnt displayed");
            assertTrue(Driver.getDriver().getTitle().contains("Active General Contact"), "Page title for active contact isnt matched");
        } else if (page.equals("Create Application")) {
            assertTrue(createApplication.backArrowCreateApp.isDisplayed(), "Navigated to other page");
            assertTrue(createApplication.applicationPageHeader.isDisplayed(), "Navigated to other page");
        }
    }

    @Then("I provide mandatory primary individual details on Create Application Page")
    public void i_provide_mandatory_primary_details_on_Create_Application_Page() {
        piFirstName = newPrimaryIndividual.get("name").toString();
        piLastName = newPrimaryIndividual.get("surname").toString();
    //    piMiddleInitial = newPrimaryIndividual.get("name").toString().substring(1);
        int randomNum = Integer.parseInt(getRandomNumber(2));
        piDOB = getPriorDateFormatMMddyyyy(randomNum);
        System.out.println("Name: " + newPrimaryIndividual.get("name"));
        clearAndFillText(createApplication.firstName, piFirstName);
        clearAndFillText(createApplication.lastName, piLastName);
        System.out.println("Surname: " + newPrimaryIndividual.get("surname"));
        clearAndFillText(createApplication.middleInitial, piMiddleInitial);
        waitFor(2);
        clearAndFillText(createApplication.DOB, piDOB);
        cellPhone = "7" + getRandomNumber(9);
        clearAndFillText2(createApplication.cellPhoneNumber, cellPhone);
        selectRandomDropDownOption(createApplication.channelDropdown);
        channel = createApplication.channelDropdown.getText();
        System.out.println("Selected channel is: " + channel);
    }

    @Then("I choose {string} as program type")
    public void i_choose_as_program_type(String programType) {
        waitFor(1);
        switch (programType.toUpperCase()) {
            case "MEDICAID":
                scrollToElement(createApplication.medicaidCheckbox);
                jsClick(createApplication.medicaidCheckbox);
                programListPI.add("Medicaid");
                break;
            case "CHIP":
                waitFor(2);
                scrollToElement(createApplication.CHIPCheckbox);
                jsClick(createApplication.CHIPCheckbox);
                programListPI.add("CHIP");
                break;
            case "PREGNANCY ASSISTANCE":
                scrollToElement(createApplication.pregnancyAssistanceCheckbox);
                jsClick(createApplication.pregnancyAssistanceCheckbox);
                programListPI.add("Pregnancy Assistance");
                break;
            case "HCBS":
                scrollToElement(createApplication.HCBSCheckbox);
                jsClick(createApplication.HCBSCheckbox);
                programListPI.add("HCBS");
                break;
            case "NOT APPLYING":
                createApplication.NOTAPPLYINGCheckbox.click();
                programListPI.clear();
                break;
        }
    }

    public static boolean markedMandatoryFields(String field) {
        if (field.equals("pregnancyIndicator")) {
            if (Driver.getDriver().findElement(By.xpath("//*[contains(text(),'ARE YOU PREGNANT')]")).getText().equals("*"))
                ;
            return true;
        } else if (!field.equals("pregnancyIndicator")) {
            WebElement element = Driver.getDriver().findElement(By.xpath("//label[@for='" + field + "']//span"));
            if (element.getText().equals("*")) ;
            return true;
        }
        return false;
    }

    @Then("I verify that following fields become mandatory for {string}")
    public void i_verify_that_following_fields_become_mandatory_for(String programType) {
        waitFor(8);
        scrollUpUsingActions(2);
        switch (programType) {
            case "Medicaid for PI":
                assertTrue(markedMandatoryFields("SSN"), "SSN didnt displayed as mandatory for Medicaid primary individual");
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for Medicaid primary individual");
                break;
            case "Medicaid for AM":
                assertTrue(markedMandatoryFields("ssn"), "SSN didnt displayed as mandatory for Medicaid application member");
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for Medicaid application member");
                break;
            case "CHIP for PI":
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for CHIP for primary individual");
                assertTrue(markedMandatoryFields("genderCode"), "Gender didnt displayed as mandatory for CHIP for primary individual");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for CHIP for primary individual");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                assertTrue(markedMandatoryFields("pregnancyNoOfBabies"), "Expected babies didnt displayed as mandatory for CHIP for primary individual");
                assertTrue(markedMandatoryFields("pregnancyDueDate"), "Expected due date didnt displayed as mandatory for CHIP for primary individual");
                break;
            case "CHIP for AM":
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for CHIP for application member");
                assertTrue(markedMandatoryFields("genderCode"), "Gender didnt displayed as mandatory for CHIP for application member");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for CHIP for application member");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                assertTrue(markedMandatoryFields("expectedBabies"), "Expected babies didnt displayed as mandatory for CHIP for application member");
                assertTrue(markedMandatoryFields("expectedDueDate"), "Expected due date didnt displayed as mandatory for CHIP for application member");
                break;
            case "HCBS for PI":
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for HCBS for primary individual");
                assertTrue(markedMandatoryFields("SSN"), "SSN didnt displayed as mandatory for HCBS for primary individual");
                assertTrue(markedMandatoryFields("genderCode"), "Gender didnt displayed as mandatory for HCBS for primary individual");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for HCBS for primary individual");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                assertTrue(markedMandatoryFields("pregnancyNoOfBabies"), "Number of babies didnt displayed as mandatory for HCBS for primary individual");
                assertTrue(markedMandatoryFields("pregnancyDueDate"), "Expected due date didnt displayed as mandatory for HCBS for primary individual");
                break;
            case "HCBS for AM":
                assertTrue(markedMandatoryFields("dateOfBirth"), "DOB didnt displayed as mandatory for HCBS for application member");
                assertTrue(markedMandatoryFields("ssn"), "SSN didnt displayed as mandatory for HCBS for application member");
                assertTrue(markedMandatoryFields("genderCode"), "Gender didnt displayed as mandatory for HCBS for application member");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for HCBS for application member");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                assertTrue(markedMandatoryFields("expectedBabies"), "Number of babies didnt displayed as mandatory for HCBS for application member");
                assertTrue(markedMandatoryFields("expectedDueDate"), "Expected due date didnt displayed as mandatory for HCBS for application member");
                break;

        }

    }

    @Then("I click on Save button on Create Application Page")
    public void i_click_on_Save_button_on_Create_Application_Page() {
        waitForClickablility(createApplication.saveButton,2);
        scrollToElement(createApplication.saveButton);
        jsClick(createApplication.saveButton);
      //  createApplication.saveButton.click();
        //waitFor(3);
    }

    @And("I click on Submit button and continue button for application without Programs")
    public void iClickOnSubmitButtonAndContinueButtonForApplicationWithoutPrograms() {
        waitFor(2);
        createApplication.submitBtn.click();
        waitFor(2);
        waitForVisibility(createApplication.continueBtn, 10);
        createApplication.continueBtn.click();
        waitFor(2);
    }

    @And("I click on continue button for application without Programs")
    public void iClickOnContinueButtonForApplicationWithoutPrograms() {
        waitForVisibility(createApplication.continueBtn, 10);
        createApplication.continueBtn.click();
    }

    //it will be used if application has programs as selected
    @Then("I click on Submit button only in Create Application Page")
    public void i_click_on_Submit_button_only_in_Create_Application_Page() {
        waitFor(3);
        scrollToElement(createApplication.submitBtn);
        createApplication.submitBtn.click();
    }

    @Then("I click submit button twice")
    public void iClickSubmitButtonTwice() {
        scrollToElement(createApplication.submitBtn);
        createApplication.submitBtn.click();
        waitFor(2);
        createApplication.submitBtn.click();
    }

    @Then("I see application Status as {string} in the create application page")
    public void i_see_application_Status_as_in_the_create_application_page(String expectedStatus) {
        browserUtils.scrollUpRobot();
        BrowserUtils.waitFor(2);
        String appStatus = createApplication.applicationStatus.getText();
        System.out.println("Application Status: " + appStatus);
        assertEquals(appStatus.trim(), expectedStatus.trim(), "Application Status does NOT match");
    }

    @Then("I see applicant status for all applicants as {string} in the create application page")
    public void i_see_applicant_Status_as_in_the_create_application_page(String expectedStatus) {
        browserUtils.scrollDown();
        BrowserUtils.waitFor(2);
        String appStatus = createApplication.firstMemberApplicantStatus.getText();
        System.out.println("Application Status: " + appStatus);
        assertEquals(appStatus.trim(), expectedStatus.trim(), "Application Status does NOT match");
    }

    @When("I add Application Member for the Created Application with mandatory fields")
    public void addApplicationMemberCreatedApplicationwithMandatoryFields() {
        waitFor(2);
        waitForClickablility(createApplication.addApplicationMemberButton,5);
        createApplication.addApplicationMemberButton.click();
        waitForVisibility(createApplication.applicationMemberPage, 10);
        clearAndFillText(createApplication.memberFirstName, newPrimaryIndividual.get("name").toString());
        clearAndFillText(createApplication.memberLastName, newPrimaryIndividual.get("surname").toString());
        waitFor(1);
        createApplication.saveButton.click();
        waitForVisibility(createApplication.addApplicationMemberButton, 10);
    }

    @When("I add Authorized Representative for the Created Application with mandatory fields")
    public void addAuthorizedRepresentativeCreatedApplicationwithMandatoryFields() {
        createApplication.addAuthorizedRepBtn.click();
        waitForVisibility(createApplication.authorizedRepPage, 10);
        clearAndFillText(createApplication.memberFirstName, newPrimaryIndividual.get("name").toString());
        clearAndFillText(createApplication.memberLastName, newPrimaryIndividual.get("surname").toString());
        waitFor(1);
        createApplication.saveButton.click();
        waitForVisibility(createApplication.addAuthorizedRepBtn, 10);
    }

    @Then("I verify mandatory fields error messages displayed for {string}")
    public void i_verify_mandatory_fields_error_messages_displayed_for(String program) {
        scrollUpUsingActions(2);
        switch (program) {
            case "Medicaid":
                assertTrue(createApplication.SSNErrorMessage.isDisplayed(), "SSN error message didnt displayed for Medicaid");
                assertTrue(createApplication.DOBErrorMessage.isDisplayed(), "DOB error message didnt displayed for Medicaid");
                break;
            case "CHIP":
                assertTrue(createApplication.DOBErrorMessage.isDisplayed(), "DOB error message didnt displayed for CHIP");
                assertTrue(createApplication.GenderErrorMessage.isDisplayed(), "Gender error message didnt displayed for CHIP");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for CHIP");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                i_click_on_Save_button_on_Create_Application_Page();
                assertTrue(createApplication.noOfBabiesErrorMessage.isDisplayed(), "Expected babies error message displayed as for CHIP");
                assertTrue(createApplication.expectedDueDateErrorMessage.isDisplayed(), "Expected due date error message didnt displayed for CHIP");
                break;
            case "HCBS":
                assertTrue(createApplication.DOBErrorMessage.isDisplayed(), "DOB error message didnt displayed for HCBS");
                assertTrue(createApplication.SSNErrorMessage.isDisplayed(), "SSN error message didnt displayed for HCBS");
                assertTrue(createApplication.GenderErrorMessage.isDisplayed(), "Gender error message didnt displayed for HCBS");
                selectFromMultiSelectDropDown(createApplication.genderDropdown, "Female");
                assertTrue(markedMandatoryFields("pregnancyIndicator"), "Pregnancy indicator didnt displayed as mandatory for HCBS");
                createApplication.pregnancyIndicatorYESCheckbox.click();
                i_click_on_Save_button_on_Create_Application_Page();
                assertTrue(createApplication.noOfBabiesErrorMessage.isDisplayed(), "Number of babies error message didnt displayed for HCBS");
                assertTrue(createApplication.expectedDueDateErrorMessage.isDisplayed(), "Expected due date error message didnt displayed for HCBS");
                break;
        }
    }

    @When("I verify programs for {string}")
    public void i_verify_programs_for(String program) {
        scrollDownUsingActions(1);
        waitFor(5);
        switch (program) {
            case "Medical Assistance":
                assertTrue(isElementDisplayed(createApplication.medicaidCheckbox), "Medicaid box didnt displayed for Medical Assistance");
                assertTrue(isElementDisplayed(createApplication.CHIPCheckbox), "CHIP isnt displayed for Medical Assistance");
                assertTrue(isElementDisplayed(createApplication.pregnancyAssistanceCheckbox), "Pregnancy assistance checkbox didnt displayed for Medical Assistance");
                assertFalse(isElementDisplayed(createApplication.HCBSCheckbox), "HCBS box is displayed for Medical Assistance");
                break;

            case "Long Term Care":
                assertFalse(isElementDisplayed(createApplication.medicaidCheckbox), "Medicaid displayed for Long Term Care");
                assertFalse(isElementDisplayed(createApplication.CHIPCheckbox), "CHIP displayed for Long Term Care");
                assertFalse(isElementDisplayed(createApplication.pregnancyAssistanceCheckbox), "Pregnancy Assistance displayed for Long Term Care");
                assertTrue(isElementDisplayed(createApplication.HCBSCheckbox), "HCBS isnt displayed for Long Term Care");
        }
    }

    @When("I verify application cycles for {string}")
    public void i_verify_application_cycles_for(String appCycle, List<String> cycleOptions) {
        int count = 0;
        for (String option : cycleOptions) {
            switch (appCycle) {
                case "Medical Assistance":
                    selectDropDown(createApplication.applicationCycleDropdown, option);
                    break;
                case "Long Term Care":
                    selectDropDown(createApplication.applicationCycleDropdown, option);
                    break;
            }
        }
    }

    @Then("I verify the Primary Individual have following labels")
    public void iVerifyThePrimaryIndividualHaveFollowingLabels(List<String> dataTable) {
        expectedLabels.addAll(dataTable);
        waitFor(2);
        actualLabels.add(createApplication.firstNameLabel.getText());
        actualLabels.add(createApplication.middleInitialLabel.getText());
        actualLabels.add(createApplication.lastNameLabel.getText());
        actualLabels.add(createApplication.suffixLabel.getText());
        actualLabels.add(createApplication.dateOfBirthLabel.getText());
        actualLabels.add(createApplication.ageLabel.getText());
        actualLabels.add(createApplication.genderLabel.getText());
        actualLabels.add(createApplication.ssnLabel.getText());
        actualLabels.add(createApplication.spokenLanguageLabel.getText());
        actualLabels.add(createApplication.writtenLanguageLabel.getText());
        actualLabels.add(createApplication.externalConsumerIdLabel.getText());
        actualLabels.add(createApplication.externaleIdTypeLabel.getText());
        Collections.sort(expectedLabels);
        Collections.sort(actualLabels);
        for (int i = 0; i < expectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(actualLabels.get(i)).equals(BrowserUtils.filterTextFor(expectedLabels.get(i))), "Actual label: " + actualLabels.get(i) + " does not match Expected label: " + expectedLabels.get(i));
        }
    }

    @Then("I verify Primary Individual Contact Information has the following labels")
    public void iVerifyPrimaryIndividualContactInformationHasTheFollowingLabels(List<String> dataTable) {
        pICIexpectedLabels.addAll(dataTable);
        waitFor(2);
        pICIactualLabels.add(createApplication.cellPhoneNumberLabel.getText());
        pICIactualLabels.add(createApplication.homePhoneNumberLabel.getText());
        pICIactualLabels.add(createApplication.workPhoneNumberLabel.getText());
        pICIactualLabels.add(createApplication.faxNumberLabel.getText());
        pICIactualLabels.add(createApplication.emailLabel.getText());
        Collections.sort(pICIexpectedLabels);
        Collections.sort(pICIactualLabels);
        for (int i = 0; i < pICIexpectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(pICIactualLabels.get(i)).equals(BrowserUtils.filterTextFor(pICIexpectedLabels.get(i))), "Actual label: " + pICIactualLabels.get(i) + " does not match Expected label: " + pICIexpectedLabels.get(i));
        }
    }

    @And("I verify Contact Information Residence Address labels")
    public void iVerifyContactInformationResidenceAddressLabels(List<String> dataTable) {
        residenceExpectedLabels.addAll(dataTable);
        waitFor(2);
        residenceActualLabels.add(createApplication.residenceAddressOneLabel.getText());
        residenceActualLabels.add(createApplication.residenceAddressTwoLabel.getText());
        residenceActualLabels.add(createApplication.residenceCityLabel.getText());
        residenceActualLabels.add(createApplication.residenceStateLabel.getText());
        residenceActualLabels.add(createApplication.residencZipLabel.getText());
        residenceActualLabels.add(createApplication.residenceCountyLabel.getText());
        Collections.sort(residenceExpectedLabels);
        Collections.sort(residenceActualLabels);
        for (int i = 0; i < residenceExpectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(residenceActualLabels.get(i)).equals(BrowserUtils.filterTextFor(residenceExpectedLabels.get(i))), "Actual label: " + residenceActualLabels.get(i) + " does not match Expected label: " + residenceExpectedLabels.get(i));
        }
    }

    @And("I verify Contact Information Mailing Address has the following labels")
    public void iVerifyContactInformationMailingAddressHasTheFollowingLabels(List<String> dataTable) {
        mailExpectedLabels.addAll(dataTable);
        waitFor(2);
        mailActualLabels.add(createApplication.mailAddressStreetOneLabel.getText());
        mailActualLabels.add(createApplication.mailAddressStreetTwoLabel.getText());
        mailActualLabels.add(createApplication.mailCityLabel.getText());
        mailActualLabels.add(createApplication.mailStateLabel.getText());
        mailActualLabels.add(createApplication.mailZipLabel.getText());
        Collections.sort(mailExpectedLabels);
        Collections.sort(mailActualLabels);
        for (int i = 0; i < mailExpectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(mailActualLabels.get(i)).equals(BrowserUtils.filterTextFor(mailExpectedLabels.get(i))), "Actual label: " + mailActualLabels.get(i) + " does not match Expected label: " + mailExpectedLabels.get(i));
        }
    }

    @And("I verify Programs\\(s) Applied for and Communication opt-in information has the following labels in {string}")
    public void iVerifyProgramsSAppliedForAndCommunicationOptInInformationHasTheFollowingLabelsIn(String type, List<String> dataTable) {
        if ("Medical Assistance".equals(type)) {
            programExpectedLabels.addAll(dataTable);
            waitFor(2);
            programActualLabels.add(createApplication.medicaidLabel.getText());
            programActualLabels.add(createApplication.chipLabel.getText());
            programActualLabels.add(createApplication.pregnanyAssistLabel.getText());
            programActualLabels.add(createApplication.communicationEmailLabel.getText());
            programActualLabels.add(createApplication.faxLabel.getText());
            programActualLabels.add(createApplication.mailLabel.getText());
            programActualLabels.add(createApplication.phoneLabel.getText());
            programActualLabels.add(createApplication.textLabel.getText());
            Collections.sort(programExpectedLabels);
            Collections.sort(programActualLabels);
            for (int i = 0; i < programExpectedLabels.size(); i++) {
                Assert.assertTrue(BrowserUtils.filterTextFor(programActualLabels.get(i)).equals(BrowserUtils.filterTextFor(programExpectedLabels.get(i))), "Actual label: " + programActualLabels.get(i) + " does not match Expected label: " + programExpectedLabels.get(i));
            }
        } else if ("Long Term Care".equals(type)) {
            programExpectedLabels.addAll(dataTable);
            waitFor(2);
            programActualLabels.add(createApplication.hcbsLabel.getText());
            programActualLabels.add(createApplication.communicationEmailLabel.getText());
            programActualLabels.add(createApplication.faxLabel.getText());
            programActualLabels.add(createApplication.mailLabel.getText());
            programActualLabels.add(createApplication.phoneLabel.getText());
            programActualLabels.add(createApplication.textLabel.getText());
            Collections.sort(programExpectedLabels);
            Collections.sort(programActualLabels);
            for (int i = 0; i < programExpectedLabels.size(); i++) {
                Assert.assertTrue(BrowserUtils.filterTextFor(programActualLabels.get(i)).equals(BrowserUtils.filterTextFor(programExpectedLabels.get(i))), "Actual label: " + programActualLabels.get(i) + " does not match Expected label: " + programExpectedLabels.get(i));
            }
        } else {
            Assert.fail("Application type does not match Medical Assistance or Long Term Care");
        }
    }

    @And("I verify Application Info for Primary Individual has the following labels")
    public void iVerifyApplicationInfoForPrimaryIndividualHasTheFollowingLabels(List<String> dataTable) {
        applicationExpectedLabels.addAll(dataTable);
        waitFor(2);
        applicationActualLabels.add(createApplication.appCycleLabel.getText());
        applicationActualLabels.add(createApplication.appReceivedDateleLabel.getText());
        applicationActualLabels.add(createApplication.channelLabel.getText());
        applicationActualLabels.add(createApplication.appSignatureLabel.getText());
        applicationActualLabels.add(createApplication.signatureDateLabel.getText());
        Collections.sort(applicationExpectedLabels);
        Collections.sort(applicationActualLabels);
        for (int i = 0; i < applicationExpectedLabels.size(); i++) {
            Assert.assertTrue(BrowserUtils.filterTextFor(applicationActualLabels.get(i)).equals(BrowserUtils.filterTextFor(applicationExpectedLabels.get(i))), "Actual label: " + actualLabels.get(i) + " does not match Expected label: " + expectedLabels.get(i));
        }
    }

    @Then("I fill in the following {string} Primary Individual with the following values")
    public void iFillInTheFollowingPrimaryIndividualWithTheFollowingValues(String type, Map<String, String> datatable) {
        browserUtils.scrollUpRobot();
        applicantInformation.put("applicationConsumerType", "Primary Individual");
        if ("MEDICAL ASSISTANCE".equals(type)) {
            primaryIndividualDetailsFieldValueFiller(datatable);
        } else if ("LONG TERM CARE".equals(type)) {
            primaryIndividualDetailsFieldValueFiller(datatable);
        } else {
            Assert.fail("did not select correct type of Application");
        }
        waitFor(5);
    }

    @Then("I create duplicate application to land on member matching page")
    public void iCreateDuplicateApplication() {
        clearAndFillText(createApplication.firstName, piFirstName);
        clearAndFillText(createApplication.lastName, piLastName);
        System.out.println("Surname: " + newPrimaryIndividual.get("surname"));
        clearAndFillText(createApplication.middleInitial, piMiddleInitial);
        waitFor(2);
        clearAndFillText(createApplication.DOB, piDOB);
        cellPhone = "7" + getRandomNumber(9);
        clearAndFillText2(createApplication.cellPhoneNumber, cellPhone);
        selectRandomDropDownOption(createApplication.channelDropdown);
        channel = createApplication.channelDropdown.getText();
        System.out.println("Selected channel is: " + channel);
    }


    /**
     * Use map key to choose Field
     * Enter Primary Individual Detail FIELD in all CAPS
     * <p>
     * Use map value to sendkeys to Field
     *
     * @param data ARE YOU PREGNANT is generated only if GENDER TYPE is FEMALE
     * @param data NO. OF BABIES EXPECTED, and EXPECTED DUE DATE are generated if ARE YOU PREGNANT is YES
     */
    private void primaryIndividualDetailsFieldValueFiller(Map<String, String> data) {
        applicantInformation.put("mailing", "false");
        applicantInformation.put("residential", "false");
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "FIRST NAME":
                    if ("Alphabetic 30".equals(data.get("FIRST NAME"))) {
                        piFirstName = getRandomString(30);
                        clearAndFillTextWithSendKeys(createApplication.firstName, piFirstName);
                    } else if ("Random 5".equals(data.get("FIRST NAME"))) {
                        piFirstName = getRandomString(5);
                        clearAndFillTextWithSendKeys(createApplication.firstName, piFirstName);
                    } else {
                        piFirstName = data.get("FIRST NAME");
                        clearAndFillTextWithSendKeys(createApplication.firstName, piFirstName);
                    }
                    break;
                case "MIDDLE INITIAL":
                    if ("Alphabetic 1".equals(data.get("MIDDLE INITIAL"))) {
                        piMiddleInitial = getRandomString(1);
                        clearAndFillTextWithSendKeys(createApplication.middleInitial, piMiddleInitial);
                    } else {
                        piMiddleInitial = data.get("MIDDLE INITIAL");
                        clearAndFillTextWithSendKeys(createApplication.middleInitial, piMiddleInitial);
                    }
                    break;
                case "LAST NAME":
                    if ("Alphabetic 30".equals(data.get("LAST NAME"))) {
                        piLastName = getRandomString(30);
                        clearAndFillTextWithSendKeys(createApplication.lastName, piLastName);
                    } else if ("Random 5".equals(data.get("LAST NAME"))) {
                        piLastName = getRandomString(5);
                        clearAndFillTextWithSendKeys(createApplication.lastName, piLastName);
                    } else {
                        piLastName = data.get("LAST NAME");
                        clearAndFillTextWithSendKeys(createApplication.lastName, piLastName);
                    }
                    break;
                case "SUFFIX":
                    if ("Alphabetic 3".equals(data.get("SUFFIX"))) {
                        piSuffix = getRandomString(3);
                        clearAndFillTextWithSendKeys(createApplication.suffix, piSuffix);
                    } else {
                        piSuffix = data.get("SUFFIX");
                        clearAndFillTextWithSendKeys(createApplication.suffix, piSuffix);
                    }
                    break;
                case "DOB":
                    if ("random past date".equals(data.get("DOB"))) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        piDOB = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillTextWithSendKeys(createApplication.DOB, piDOB);
                    } else {
                        piDOB = data.get("DOB");
                        clearAndFillTextWithSendKeys(createApplication.DOB, piDOB);
                    }
                    break;
                case "GENDER":
                    piGender = data.get("GENDER");
                    waitForClickablility(createApplication.genderDropdown, 10);
                    selectDropDown(createApplication.genderDropdown, piGender);
                    break;
                case "ARE YOU PREGNANT":
                    piAreYouPregnant = data.get("ARE YOU PREGNANT");
                    waitFor(2);
                    if ("Yes".equals(piAreYouPregnant)) {
                        createApplication.pregnancyIndicatorYESCheckbox.click();
                    } else if ("No".equals(piAreYouPregnant)) {
                        createApplication.pregnancyIndicatorNOCheckbox.click();
                    } else {
                        Assert.fail("Selected value does not match YES or NO for Are you Pregnant");
                    }
                    break;
                case "NO. BABIES EXPECTED":
                    waitForClickablility(createApplication.numberOfBabiesDropdown, 10);
                    piBabiesExpected = data.get("NO. BABIES EXPECTED");
                    selectDropDown(createApplication.numberOfBabiesDropdown, piBabiesExpected);
                    break;
                case "EXPECTED DUE DATE":
                    waitFor(1);
                    if ("random future date".equals(data.get("EXPECTED DUE DATE"))) {
                        int random = Integer.parseInt(getRandomNumber(2));
                        piExpectedDueDate = getGreaterDateFormatMMddyyyy(random);
                        clearAndFillTextWithSendKeys(createApplication.expectedDueDate, piExpectedDueDate);
                        break;
                    } else {
                        piExpectedDueDate = data.get("EXPECTED DUE DATE");
                        clearAndFillTextWithSendKeys(createApplication.expectedDueDate, piExpectedDueDate);
                    }
                case "SSN":
                    if ("Numeric 9".equals(data.get("SSN"))) {
                        piSSN = getRandomNumber(9);
                        clearAndFillTextWithSendKeys(createApplication.SSN, piSSN);
                    } else {
                        piSSN = data.get("SSN");
                        clearAndFillTextWithSendKeys(createApplication.SSN, piSSN);
                    }
                    break;
                case "SPOKEN LANGUAGE":
                    waitForClickablility(createApplication.spokenLanguageDropdown, 10);
                    if ("random dropdown".equals(data.get("SPOKEN LANGUAGE"))) {
                        selectRandomDropDownOption(createApplication.spokenLanguageDropdown);
                        piSpokenLanguage = createApplication.spokenLanguageDropdown.getText();
                    } else {
                        selectDropDown(createApplication.spokenLanguageDropdown, data.get("SPOKEN LANGUAGE"));
                        piSpokenLanguage = createApplication.spokenLanguageDropdown.getText();
                    }
                    break;
                case "WRITTEN LANGUAGE":
                    waitForClickablility(createApplication.writtenLanguageDropdown, 10);
                    if ("random dropdown".equals(data.get("WRITTEN LANGUAGE"))) {
                        selectRandomDropDownOption(createApplication.writtenLanguageDropdown);
                        piWrittenLanguage = createApplication.writtenLanguageDropdown.getText();
                    } else {
                        selectDropDown(createApplication.writtenLanguageDropdown, data.get("WRITTEN LANGUAGE"));
                        piWrittenLanguage = createApplication.writtenLanguageDropdown.getText();
                    }
                    break;
                case "EXTERNAL CONSUMER ID":
                    waitFor(1);
                    if ("Alpha-Numeric 15".equals(data.get("EXTERNAL CONSUMER ID"))) {
                        piExtConId = RandomStringUtils.random(15, true, true);
                        clearAndFillTextWithSendKeys(createApplication.externalConsumerID, piExtConId);
                    } else {
                        piExtConId = data.get("EXTERNAL CONSUMER ID");
                        clearAndFillTextWithSendKeys(createApplication.externalConsumerID, piExtConId);
                    }
                    break;
                case "EXTERNAL ID TYPE":
                    waitFor(1);
                    if ("random dropdown".equals(data.get("EXTERNAL ID TYPE"))) {
                        selectRandomDropDownOption(createApplication.externalIdTypeDropdown);
                        piExtIdType = createApplication.externalIdTypeDropdown.getText();
                    } else {
                        selectDropDown(createApplication.externalIdTypeDropdown, data.get("EXTERNAL ID TYPE"));
                        piExtIdType = createApplication.externalIdTypeDropdown.getText();
                    }
                    break;
                default:
                    Assert.fail("Primary Individual Details search criteria does not match");
            }
        }
    }

    @And("I fill in the Primary Individual Contact Information with the following values")
    public void iFillInThePrimaryIndividualContactInformationWithTheFollowingValues(Map<String, String> data) {
        //Initialiazing addressType to false to prevent NullPointerException
        applicantInformation.put("mailing", "false");
        applicantInformation.put("residential", "false");
        applicantInformation.put("applicationConsumerType", "Primary Individual");
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "SET PRIMARY CONTACT":
                    String primaryContact = data.get("SET PRIMARY CONTACT");
                    if (primaryContact.equalsIgnoreCase("CELL PHONE NUMBER"))
                        createApplication.cellPhoneStarIcon.click();
                    if (primaryContact.equalsIgnoreCase("HOME PHONE NUMBER"))
                        createApplication.homePhoneStarIcon.click();
                    if (primaryContact.equalsIgnoreCase("WORK PHONE NUMBER"))
                        createApplication.workPhoneStarIcon.click();
                    break;
                case "CELL PHONE NUMBER":
                    if ("Random Numeric 10".equals(data.get("CELL PHONE NUMBER"))) {
                        cellPhone = "7" + getRandomNumber(9);
                        clearAndFillTextWithSendKeys(createApplication.cellPhoneNumber, cellPhone);
                    } else if (data.get("CELL PHONE NUMBER").isEmpty()) {
                        clearTextField(createApplication.cellPhoneNumber);
                    } else {
                        cellPhone = data.get("CELL PHONE NUMBER");
                        clearAndFillTextWithSendKeys(createApplication.cellPhoneNumber, cellPhone);
                    }
                    applicantInformation.put("contactNumber", cellPhone);
                    applicantInformation.put("phoneType", BrowserUtils.snakeToCamel("Cell Phone Number"));
                    break;
                case "HOME PHONE NUMBER":
                    if ("Random Numeric 10".equals(data.get("HOME PHONE NUMBER"))) {
                        homePhone = "7" + getRandomNumber(9);
                        clearAndFillTextWithSendKeys(createApplication.homePhoneNumber, homePhone);
                    } else if (data.get("HOME PHONE NUMBER").isEmpty()) {
                        clearTextField(createApplication.homePhoneNumber);
                    } else {
                        homePhone = data.get("HOME PHONE NUMBER");
                        clearAndFillTextWithSendKeys(createApplication.homePhoneNumber, homePhone);
                    }
                    applicantInformation.put("contactNumber", homePhone);
                    applicantInformation.put("phoneType", BrowserUtils.snakeToCamel("Home Phone Number"));
                    break;
                case "WORK PHONE NUMBER":
                    if ("Random Numeric 10".equals(data.get("WORK PHONE NUMBER"))) {
                        workPhone = "7" + getRandomNumber(9);
                        clearAndFillTextWithSendKeys(createApplication.workPhoneNumber, workPhone);
                    } else if (data.get("WORK PHONE NUMBER").isEmpty()) {
                        clearTextField(createApplication.workPhoneNumber);
                    } else {
                        workPhone = data.get("WORK PHONE NUMBER");
                        clearAndFillTextWithSendKeys(createApplication.workPhoneNumber, workPhone);
                    }
                    applicantInformation.put("contactNumber", workPhone);
                    applicantInformation.put("phoneType", BrowserUtils.snakeToCamel("Work Phone Number"));
                    break;
                case "FAX NUMBER":
                    if ("Random Numeric 10".equals(data.get("FAX NUMBER"))) {
                        faxNumber = "7" + getRandomNumber(9);
                        clearAndFillTextWithSendKeys(createApplication.faxNumber, faxNumber);
                    } else if (data.get("FAX NUMBER").isEmpty()) {
                        clearTextField(createApplication.faxNumber);
                    } else {
                        faxNumber = data.get("FAX NUMBER");
                        clearAndFillTextWithSendKeys(createApplication.faxNumber, faxNumber);
                    }
                    applicantInformation.put("contactNumber", faxNumber);
                    applicantInformation.put("phoneType", "faxNumber");
                    break;
                case "EMAIL":
                    email = data.get("EMAIL");
                    clearAndFillTextWithSendKeys(createApplication.emailAddress, email);
                    if (data.get("EMAIL").isEmpty())
                        clearTextField(createApplication.emailAddress);
                    applicantInformation.put("emailAddress", email);
                    break;
                case "RESIDENCE ADDRESS LINE 1":
                    if ("random".equals(data.get("RESIDENCE ADDRESS LINE 1"))) {
                        residenceAddressLineOne = getRandomNumber(3) + " " + getRandomString(5);
                        clearAndFillTextWithSendKeys(createApplication.addressLineOne, residenceAddressLineOne);
                    } else if ("random 41".equals(data.get("RESIDENCE ADDRESS LINE 1"))) {
                        residenceAddressLineOne = getRandomNumber(10) + " " + getRandomString(31);
                        clearAndFillTextWithSendKeys(createApplication.addressLineOne, residenceAddressLineOne);
                    } else if (data.get("RESIDENCE ADDRESS LINE 1").isEmpty()) {
                        clearTextField(createApplication.addressLineOne);
                    } else {
                        residenceAddressLineOne = data.get("RESIDENCE ADDRESS LINE 1");
                        clearAndFillTextWithSendKeys(createApplication.addressLineOne, residenceAddressLineOne);
                    }
                    applicantInformation.put("addressStreet1", residenceAddressLineOne);
                    applicantInformation.put("residential", "true");
                    break;
                case "RESIDENCE ADDRESS LINE 2":
                    if ("random".equals(data.get("RESIDENCE ADDRESS LINE 2"))) {
                        residenceAddressLineTwo = getRandomNumber(3) + " " + getRandomString(5);
                        clearAndFillTextWithSendKeys(createApplication.addressLineTwo, residenceAddressLineTwo);
                    }// Burak's addition
                    else if ("random 41".equals(data.get("RESIDENCE ADDRESS LINE 2"))) {
                        residenceAddressLineTwo = getRandomNumber(10) + " " + getRandomString(31);
                        clearAndFillTextWithSendKeys(createApplication.addressLineTwo, residenceAddressLineTwo);
                    } else if (data.get("RESIDENCE ADDRESS LINE 2").isEmpty()) {
                        clearTextField(createApplication.addressLineTwo);
                    } else {
                        residenceAddressLineTwo = data.get("RESIDENCE ADDRESS LINE 2");
                        clearAndFillTextWithSendKeys(createApplication.addressLineTwo, residenceAddressLineTwo);
                    }
                    applicantInformation.put("addressStreet2", residenceAddressLineTwo);
                    break;
                case "RESIDENCE CITY":
                    residenceCity = data.get("RESIDENCE CITY");
                    clearAndFillTextWithSendKeys(createApplication.residenceCity, residenceCity);
                    applicantInformation.put("addressCity", residenceCity);
                    break;
                case "RESIDENCE STATE":
                    residenceState = data.get("RESIDENCE STATE");
                    clearAndFillTextWithSendKeys(createApplication.residenceState, residenceState);
                    applicantInformation.put("addressState", residenceState);
                    break;
                case "RESIDENCE ZIP CODE":
                    residenceZipcode = data.get("RESIDENCE ZIP CODE");
                    clearAndFillTextWithSendKeys(createApplication.residenceZipcode, residenceZipcode);
                    applicantInformation.put("addressZip", residenceZipcode);
                    break;
                case "RESIDENCE COUNTY":
                    residenceCounty = data.get("RESIDENCE COUNTY");
                    clearAndFillTextWithSendKeys(createApplication.residenceCounty, residenceCounty);
                    applicantInformation.put("addressCounty", residenceCounty);
                    break;
                case "MAILING ADDRESS LINE 1":
                    mailingAddressLineOne = data.get("MAILING ADDRESS LINE 1");
                    clearAndFillTextWithSendKeys(createApplication.mAddressLineOne, mailingAddressLineOne);
                    if (data.get("MAILING ADDRESS LINE 1").isEmpty())
                        clearTextField(createApplication.mAddressLineOne);
                    applicantInformation.put("addressStreet1", mailingAddressLineOne);
                    applicantInformation.put("mailing", "true");
                    break;
                case "MAILING ADDRESS LINE 2":
                    mailingAddressLineTwo = data.get("MAILING ADDRESS LINE 2");
                    clearAndFillTextWithSendKeys(createApplication.mAddressLineTwo, mailingAddressLineTwo);
                    if (data.get("MAILING ADDRESS LINE 2").isEmpty())
                        clearTextField(createApplication.mAddressLineTwo);
                    applicantInformation.put("addressStreet2", mailingAddressLineTwo);
                    break;
                case "MAILING CITY":
                    mailingCity = data.get("MAILING CITY");
                    clearAndFillTextWithSendKeys(createApplication.mCity, mailingCity);
                    applicantInformation.put("addressCity", mailingCity);
                    break;
                case "MAILING STATE":
                    mailingState = data.get("MAILING STATE");
                    clearAndFillTextWithSendKeys(createApplication.mState, mailingState);
                    applicantInformation.put("addressState", mailingState);
                    break;
                case "MAILING ZIP CODE":
                    mailingZipcode = data.get("MAILING ZIP CODE");
                    clearAndFillTextWithSendKeys(createApplication.mZipcode, mailingZipcode);
                    applicantInformation.put("addressZip", mailingZipcode);
                    break;
                default:
                    Assert.fail("Primary Individual Contact Information search criteria does not match");
            }
        }
    }

    @And("I choose Communication Opt In Information by the following list")
    public void iChooseCommunicationOptInInformationByTheFollowingList(List<String> data) {
        commOPtINInfo.addAll(data);
        for (String eachCommInfo : commOPtINInfo) {
            switch (eachCommInfo) {
                case "Email":
                    createApplication.communicationEmailCheckbox.click();
                    break;
                case "Fax":
                    createApplication.communicationFaxCheckbox.click();
                    break;
                case "Mail":
                    createApplication.communicationMailCheckbox.click();
                    break;
                case "Phone":
                    createApplication.communicationPhoneCheckbox.click();
                    break;
                case "Text":
                    createApplication.communicationTextCheckbox.click();
                    break;
            }
        }
    }

    @And("I select Primary Individual Application information with the following")
    public void iSelectPrimaryIndividualApplicationInformationWithTheFollowing(Map<String, String> data) {
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "PRIORITY":
                    if ("random".equals(data.get("PRIORITY"))) {
                        selectRandomDropDownOption(createApplication.priorityDropdown);
                        priority = createApplication.priorityDropdown.getText();
                    } else {
                        priority = data.get("PRIORITY");
                        selectDropDown(createApplication.priorityDropdown, priority);
                    }
                    break;
                case "EXTERNAL APPLICATION ID":
                    if ("random".equals(data.get("EXTERNAL APPLICATION ID")))
                        extAppID = generateRandomCharacters();
                    else if ("Alpha-Numeric 36".equals(data.get("EXTERNAL APPLICATION ID")))
                        extAppID = RandomStringUtils.random(36, true, true);
                    else if ("36 VARIABLE CHARACTERS".equals(data.get("EXTERNAL APPLICATION ID"))) {
                        extAppID = "!@#$%^&*()_+-=" + RandomStringUtils.randomAlphanumeric(22);
                    } else if ("ID ENTERED WITH SPACES".equals(data.get("EXTERNAL APPLICATION ID"))) {
                        extAppID = "  " + RandomStringUtils.randomAlphanumeric(5) + "   " + RandomStringUtils.randomAlphanumeric(5) + "   ";
                    } else if ("OVER MAX VARIABLE CHARACTERS".equals(data.get("EXTERNAL APPLICATION ID"))) {
                        extAppID = RandomStringUtils.randomAlphanumeric(40);
                    } else
                        extAppID = data.get("EXTERNAL APPLICATION ID");
                    clearAndFillTextWithSendKeys(createApplication.externalAppId, extAppID);
                    break;
                case "CYCLE":
                    cycle = data.get("CYCLE");
                    if ("New".equals(cycle)) {
                        selectDropDown(createApplication.cycleDropdown, cycle);
                    } else if ("Renewal".equals(cycle)) {
                        selectDropDown(createApplication.cycleDropdown, cycle);
                    } else {
                        Assert.fail("Entered value does not match Application Cycle value");
                    }
                    break;
                case "RECEIVED DATE":
                    receivedDate = data.get("RECEIVED DATE");
                    if ("random past".equals(receivedDate)) {
                        int randomNum = Integer.parseInt(getRandomNumber(2)) + 31;
                        receivedDate = getPriorDateFormatMMddyyyy(randomNum);
                        jsClick(createApplication.appReceivedDate);
                        waitFor(1);
                        createApplication.appReceivedDate.clear();
                        waitFor(1);
                        jsClick(createApplication.appReceivedDate);
                        waitFor(1);
                        createApplication.appReceivedDate.sendKeys(receivedDate.replaceAll("/", ""));
                    } else if ("current".equals(receivedDate)) {
                        receivedDate = getCurrentDate();
                    } else if ("yyyymmdd".equals(receivedDate)) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        receivedDate = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillText2(createApplication.appReceivedDate, receivedDate);
                        receivedDate = browserUtils.convertMMddyyyyToyyyyMMdd(receivedDate);
                    } else {
                        clearAndFillTextWithSendKeys(createApplication.appReceivedDate, receivedDate);
                    }
                    break;
                case "CHANNEL":
                    if ("random".equals(data.get("CHANNEL"))) {
                        selectRandomDropDownOption(createApplication.channelDropdown);
                        channel = createApplication.channelDropdown.getText();
                    } else {
                        channel = data.get("CHANNEL");
                        selectDropDown(createApplication.channelDropdown, channel);
                    }
                    break;
                case "SIGNATURE":
                    signature = data.get("SIGNATURE");
                    waitFor(1);
                    if ("Yes".equalsIgnoreCase(signature)) {
                        createApplication.appSignatureYes.click();
                    } else if ("No".equalsIgnoreCase(signature)) {
                        createApplication.appSignatureNo.click();
                    } else {
                        Assert.fail("Selected Option value is not yes or no for Signature");
                    }
                    break;
                case "SIGNATURE DATE":
                    signatureDate = data.get("SIGNATURE DATE");
                    if ("random".equals(signatureDate)) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        signatureDate = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillTextWithSendKeys(createApplication.signatureDate, signatureDate);
                    } else if ("yyyymmdd".equals(signatureDate)) {
                        int randomNum = Integer.parseInt(getRandomNumber(2));
                        signatureDate = getPriorDateFormatMMddyyyy(randomNum);
                        clearAndFillTextWithSendKeys(createApplication.signatureDate, signatureDate);
                        signatureDate = browserUtils.convertMMddyyyyToyyyyMMdd(signatureDate);
                    } else {
                        signatureDate = data.get("SIGNATURE DATE");
                        clearAndFillTextWithSendKeys(createApplication.signatureDate, signatureDate);
                    }
                    break;
                case "RECEIVED LANGUAGE":
                    if ("Default".equals(data.get("RECEIVED LANGUAGE"))) {
                        receivedLanguage = createApplication.receivedLanguageDropdown.getText();
                    } else {
                        receivedLanguage = data.get("RECEIVED LANGUAGE");
                        selectDropDown(createApplication.receivedLanguageDropdown, receivedLanguage);
                    }
                    break;
                default:
                    Assert.fail("Primary Individual Details search criteria does not match");
            }
        }
    }

    @And("I verify the following saved Primary Individual application details")
    public void iVerifyTheFollowingSavedPrimaryIndividualApplicationDetails(List<String> dataTable) {
        for (String each : dataTable) {
            switch (each.toUpperCase()) {
                case "FULL NAME":
                    String expectedfullName = piFirstName + " " + piMiddleInitial + " " + piLastName + " " + piSuffix;
                    String actualFullName = createApplication.savedFullName.getText();
                    Assert.assertEquals(actualFullName, expectedfullName, "Actual Full name:" + actualFullName + " does not match expected full name: " + expectedfullName);
                    break;
                case "DOB":
                    String actualDOB = createApplication.savedDOB.getText();
                    Assert.assertEquals(actualDOB, piDOB, "Actual DOB: " + actualDOB + " Expected DOB: " + piDOB);
                    break;
                case "SSN":
                    browserUtils.scrollUpRobot();
                    waitForVisibility(createApplication.ssnVisible, 5);
                    createApplication.ssnVisible.click();
                    String actualSSN = createApplication.saveSSN.getText().replaceAll("-", "");
                    Assert.assertEquals(actualSSN, piSSN);
                    break;
                case "AGE/GENDER":
                    String actualAgeGender = createApplication.SavedAgeGender.getText();
                    String age = String.valueOf(calculateAge(piDOB));
                    String expectedAgeGender = age + " / " + piGender.charAt(0);
                    Assert.assertEquals(actualAgeGender, expectedAgeGender, "Actual Age/Gender: " + actualAgeGender + " Expected Age/Gender: " + expectedAgeGender);
                    break;
                case "SPOKEN LANG":
                    String actualSpokeLang = createApplication.savedSpokenLanguage.getText();
                    Assert.assertEquals(actualSpokeLang, piSpokenLanguage, "Actual Spoken Language: " + actualSpokeLang + " Expected Spoken Language: " + piSpokenLanguage);
                    break;
                case "WRITTEN LANGUAGE":
                    String actualWrittenLang = createApplication.savedWrittenLanguage.getText();
                    Assert.assertEquals(actualWrittenLang, piWrittenLanguage, "Actual written language: " + actualWrittenLang + " Expected written language: " + piWrittenLanguage);
                    break;
                case "PROGRAMS":
                    String actualPrograms = createApplication.savedPrograms.getText();
                    for (String eachProgram : programListPI) {
                        Assert.assertTrue(actualPrograms.contains(eachProgram), "Actual Program List: " + programListPI + " does not contain expected program: " + eachProgram);
                    }
                    break;
                case "OPT INS":
                    String actualOptIns = createApplication.savedOptIns.getText();
                    for (String eachOptIn : commOPtINInfo) {
                        Assert.assertTrue(actualOptIns.contains(eachOptIn), "Actual Comm Opt-in List: " + commOPtINInfo + " does not contain expected Opt-In: " + eachOptIn);
                    }
                    break;
                default:
                    Assert.fail("Primary Individual Details search criteria does not match");
            }
        }
    }

    @And("I verify the following saved Primary Individual collapsed view application details")
    public void iVerifyTheFollowingSavedPrimaryIndividualCollapsedViewApplicationDetails(List<String> dataTable) {
        waitFor(1);
        createApplication.pIChevronRight.click();
        waitFor(1);
        for (String each : dataTable) {
            switch (each.toUpperCase()) {
                case "ARE YOU PREGNANT":
                    String actualAreYouPregnant = createApplication.savedAreYouPregnant.getText();
                    Assert.assertEquals(actualAreYouPregnant, piAreYouPregnant, "Actual Are you Pregnant entered value: " + actualAreYouPregnant + " does not match Expected value: " + piAreYouPregnant);
                    break;
                case "NO. OF BABIES EXPECTED":
                    String actualnumBabiesExpected = createApplication.savedExpectedBabies.getText();
                    Assert.assertEquals(actualnumBabiesExpected, piBabiesExpected, "Actual NO. OF BABIES EXPECTED entered value: " + actualnumBabiesExpected + " does not match Expected value: " + piBabiesExpected);
                    break;
                case "EXPECTED DUE DATE":
                    String actualExpectedDueDate = createApplication.savedExpectedDueDate.getText();
                    Assert.assertEquals(actualExpectedDueDate, piExpectedDueDate, "Actual EXPECTED DUE DATE entered value: " + actualExpectedDueDate + " does not match Expected value: " + piExpectedDueDate);
                    break;
                case "EXTERNAL CONSUMER ID":
                    String actualExtConsumerID = createApplication.savedExternalId.getText();
                    Assert.assertEquals(actualExtConsumerID, piExtConId, "Actual EXTERNAL CONSUMER ID entered value: " + actualExtConsumerID + " does not match Expected value: " + piExtConId);
                    break;
                case "EXTERNAL ID TYPE":
                    String actualExtIDType = createApplication.savedExternalType.getText();
                    Assert.assertEquals(actualExtIDType, piExtIdType, "Actual EXTERNAL ID TYPE entered value: " + actualExtIDType + " does not match Expected value: " + piExtIdType);
                    break;
                default:
                    Assert.fail("Primary Individual Details search criteria does not match");
            }
        }
    }

    @And("I verify the following saved Primary Individual Contact Information application")
    public void iVerifyTheFollowingSavedPrimaryIndividualContactInformationApplication(List<String> dataTable) {
        waitFor(1);
        for (String each : dataTable) {
            switch (each.toUpperCase()) {
                case "CELL PHONE":
                    String actualCellPhone = createApplication.savedCellPhone.getText();
                    if (actualCellPhone.contains("star")) {
                        actualCellPhone = actualCellPhone.replace("star", "");
                    }
                    String expectedCellPhone = convertNumToExpectedPhoneNum(cellPhone);
                    Assert.assertEquals(actualCellPhone, expectedCellPhone, "Actual CELL PHONE entered value: " + actualCellPhone + " does not match Expected value: " + expectedCellPhone);
                    break;
                case "HOME PHONE":
                    String actualHomePhone = createApplication.savedHomePhone.getText();
                    if (actualHomePhone.contains("star")) {
                        actualHomePhone = actualHomePhone.replace("star", "");
                    }
                    String expectedHomePhone = convertNumToExpectedPhoneNum(homePhone);
                    Assert.assertEquals(actualHomePhone, expectedHomePhone, "Actual HOME PHONE entered value: " + actualHomePhone + " does not match Expected value: " + expectedHomePhone);
                    break;
                case "WORK PHONE":
                    String actualWorkPhone = createApplication.savedWorkPhone.getText();
                    if (actualWorkPhone.contains("star")) {
                        actualWorkPhone = actualWorkPhone.replace("star", "");
                    }
                    String expectedWorkPhone = convertNumToExpectedPhoneNum(workPhone);
                    Assert.assertEquals(actualWorkPhone, expectedWorkPhone, "Actual WORK PHONE entered value: " + actualWorkPhone + " does not match Expected value: " + expectedWorkPhone);
                    break;
                case "FAX NUMBER":
                    String actualFaxNum = createApplication.savedFaxNumber.getText();
                    String expectedFaxNum = convertNumToExpectedPhoneNum(faxNumber);
                    Assert.assertEquals(actualFaxNum, expectedFaxNum, "Actual FAX NUMBER entered value: " + actualFaxNum + " does not match Expected value: " + expectedFaxNum);
                    break;
                case "EMAIL":
                    String actualEmail = createApplication.savedEmail.getText();
                    Assert.assertEquals(actualEmail, email, "Actual EMAIL entered value " + actualEmail + " does not match Expected value: " + email);
                    break;
                case "RESIDENCE ADDRESS":
                    List<String> actualResidenceAddress = new ArrayList<>();
                    actualResidenceAddress.add(createApplication.savedResidAddOne.getText());
                    actualResidenceAddress.add(createApplication.savedResidAddtwo.getText());
                    actualResidenceAddress.add(createApplication.savedResidCity.getText());
                    actualResidenceAddress.add(createApplication.saveResidState.getText());
                    actualResidenceAddress.add(createApplication.savedResidZip.getText());
                    actualResidenceAddress.add(createApplication.savedResidCounty.getText());
                    List<String> expectedResidenceAddress = new ArrayList<>();
                    expectedResidenceAddress.add(residenceAddressLineOne);
                    expectedResidenceAddress.add(residenceAddressLineTwo);
                    expectedResidenceAddress.add(residenceCity);
                    expectedResidenceAddress.add(residenceState);
                    expectedResidenceAddress.add(residenceZipcode);
                    expectedResidenceAddress.add(residenceCounty);
                    Assert.assertEquals(actualResidenceAddress, expectedResidenceAddress, "Actual Residence address: " + actualResidenceAddress + " does not match " + expectedResidenceAddress);
                    break;
                case "MAILING ADDRESS":
                    List<String> actualMailingAddress = new ArrayList<>();
                    actualMailingAddress.add(createApplication.savedMailAddOne.getText());
                    actualMailingAddress.add(createApplication.savedMailAddTwo.getText());
                    actualMailingAddress.add(createApplication.savedMailCity.getText());
                    actualMailingAddress.add(createApplication.savedMailState.getText());
                    actualMailingAddress.add(createApplication.savedMailZip.getText());
                    List<String> expectedMailingeAddress = new ArrayList<>();
                    expectedMailingeAddress.add(mailingAddressLineOne);
                    expectedMailingeAddress.add(mailingAddressLineTwo);
                    expectedMailingeAddress.add(mailingCity);
                    expectedMailingeAddress.add(mailingState);
                    expectedMailingeAddress.add(mailingZipcode);
                    Assert.assertEquals(actualMailingAddress, expectedMailingeAddress, "Actual Residence address: " + actualMailingAddress + " does not match " + expectedMailingeAddress);
                    break;
                default:
                    Assert.fail("Primary Individual Contact Info searc criteria does not match");
            }
        }
    }

    @And("I verify the following saved Primary Individual Application Info")
    public void iVerifyTheFollowingSavedPrimaryIndividualApplicationInfo(List<String> dataTable) {
        waitFor(1);
        for (String each : dataTable) {
            switch (each.toUpperCase()) {
                case "CYCLE":
                    String actualCycle = createApplication.savedAppCycle.getText();
                    Assert.assertEquals(actualCycle, cycle, "Actual cycle: " + actualCycle + " does not match expected: " + cycle);
                    break;
                case "RECEIVED DATE":
                    String actualReceivedDate = createApplication.savedReceivedDate.getText();
                    Assert.assertEquals(actualReceivedDate, receivedDate, "Actual RECEIVED DATE: " + actualReceivedDate + " does not match expected: " + receivedDate);
                    break;
                case "CHANNEL":
                    String actualChannel = createApplication.savedChannel.getText();
                    Assert.assertEquals(actualChannel, channel, "Actual CHANNEL: " + actualChannel + " does not match expected: " + channel);
                    break;
                case "APPLICATION SIGNATURE":
                    if ("Yes".equalsIgnoreCase(signature)) {
                        Assert.assertTrue(createApplication.yesSignatureValue.getAttribute("class").contains("checked"), "Selected Yes for Application Signature did not select the Yes radio button");
                    } else if ("No".equalsIgnoreCase(signature)) {
                        Assert.assertTrue(createApplication.noSignatureValue.getCssValue("class").contains("checked"), "Selected Yes for Application Signature did not select the Yes radio button");
                    } else {
                        Assert.fail("Selected Application SIGNATURE value does not match Yes or No");
                    }
                    break;
                case "SIGNATURE DATE":
                    String actualSignatureDate = createApplication.savedSignatureDate.getText();
                    Assert.assertEquals(actualSignatureDate, signatureDate, "Actual SIGNATURE DATE: " + actualSignatureDate + " does not match expected: " + signatureDate);
                    break;
                case "EXTERNAL APPLICATION ID":
                    assertEquals(createApplication.savedExternalAppId.getText(), extAppID, "Saved External App Id did not match entered External App Id");
                    break;
                case "EXT APP ID WITHOUT SPACES":
                    assertEquals(createApplication.savedExternalAppId.getText(), extAppID.replaceAll(" ", "").trim(), "Saved External App Id did not match entered External App Id");
                    break;
                case "OVER MAX VARIABLE CHARACTERS":
                    assertEquals(createApplication.savedExternalAppId.getText(), extAppID.substring(0, 36), "Saved External App Id did not match entered External App Id");
                    break;
                case "RECEIVED LANGUAGE":
                    assertEquals(createApplication.savedReceivedLanguageLabel.getText(), "RECEIVED LANGUAGE", "Did not find RECEIVED LANGUAGE label in saved application page");
                    assertEquals(createApplication.savedReceivedLanguage.getText(), receivedLanguage, "Mismatch in expected Saved Received Language");
                    break;
                case "APPLICATION CODE UI":
                    waitForVisibility(createApplication.savedApplicationCode,5);
                    assertEquals(createApplication.savedApplicationCode.getText(), applicationCode, "Mismatch in expected Saved Application Code");
                    assertFalse(createApplication.savedApplicationCode.getAttribute("class").contains("input"), "Expected uneditalbe field value but able to input");
                    break;
                case "APPLICATION CODE API":
                    scrollToElement(createApplication.savedApplicationCode);
                    waitForVisibility(createApplication.savedApplicationCode,5);
                    assertEquals(createApplication.savedApplicationCode.getText(), applicationCodeAPI, "Mismatch in expected Saved Application Code");
                    assertFalse(createApplication.savedApplicationCode.getAttribute("class").contains("input"), "Expected uneditalbe field value but able to input");
                    break;
                default:
                    Assert.fail("Primary Individual Details search criteria does not match");
            }
        }
    }

    public String convertNumToExpectedPhoneNum(String num) {
        Assert.assertTrue(num.length() == 10);
        num = "(" + num.substring(0, 3) + ") " + num.substring(3, 6) + "-" + num.substring(6);
        return num;
    }

    @And("I verify Application created Success Message and Store Application ID value created")
    public void iVerifyApplicationCreatedSuccessMessageAndStoreApplicationIDValueCreated() {
        waitForVisibility(createApplication.applicationSuccessfulyCreatedMsg, 10);
        Assert.assertEquals(createApplication.applicationSuccessfulyCreatedMsg.getText(), "Application successfully created!", "Save button failed to create Application succesfully!");
        applicationId = createApplication.createdAppId.getText();
        System.out.println("Application id:" + applicationId);
    }

    @Then("I verify the following Application cycle values for {string}")
    public void iVerifyTheFollowingApplicationCycleValuesFor(String appType, List<String> data) {
        expectedCycleList.addAll(data);
        createApplication.cycleDropdown.click();
        waitFor(3);
        for (WebElement appCycleDropdownValue : createApplication.appCycleDropdownValues) {
            actualCycleList.add(appCycleDropdownValue.getText());
        }
        Collections.sort(expectedCycleList);
        Collections.sort(actualCycleList);
        if ("MEDICAL ASSISTANCE".equalsIgnoreCase(appType)) {
            Assert.assertEquals(actualCycleList, expectedCycleList, "Actual Medical Assistance cycle value list: " + actualCycleList + " does not match expected cycle value list: " + expectedCycleList);
        } else if ("LONG TERM CARE".equalsIgnoreCase(appType)) {
            Assert.assertEquals(actualCycleList, expectedCycleList, "Actual Long Term Care cycle value list: " + actualCycleList + " does not match expected cycle value list: " + expectedCycleList);
        } else {
            Assert.fail("Selected Application type does not match: MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @And("I verify the defined programs for Primary Individual {string}")
    public void iVerifyTheDefinedProgramsForPrimaryIndividual(String appType) {
        if ("MEDICAL ASSISTANCE".equalsIgnoreCase(appType)) {
            Assert.assertTrue(createApplication.medicaidLabel.isDisplayed(), "Medical Assistance program Medicaid is missing");
            Assert.assertTrue(createApplication.pregnanyAssistLabel.isDisplayed(), "Medical Assistance program Pregnancy Assistance is missing");
            Assert.assertTrue(createApplication.chipLabel.isDisplayed(), "Medical Assistance program CHIP is missing");
        } else if ("LONG TERM CARE".equalsIgnoreCase(appType)) {
            Assert.assertTrue(createApplication.hcbsLabel.isDisplayed(), "Long Term Care program HCBS is missing");
        } else {
            Assert.fail("Selected Application type does not match: MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @And("I click on Cancel button for Primary Individual Create Application Page")
    public void iClickOnCancelButtonForPrimaryIndividualCreateApplicationPage() {
        waitFor(2);
        waitForVisibility(createApplication.cancelButton, 10);
        click(createApplication.cancelButton);
        waitFor(2);
    }

    @And("I verify the primary Individual fields have returned to default")
    public void iVerifyThePrimaryIndividualFieldsHaveReturnedToDefault() {
        assertEquals(createApplication.getSpokenLanguage.getText(), "English", "Default spoken language is not English. Actual value is " + createApplication.getSpokenLanguage);
        assertEquals(createApplication.getWrittenLanguage.getText(), "English", "Default written language is not English. Actual value is " + createApplication.getWrittenLanguage);
        assertEquals(createApplication.cycleDropdown.getText(), "New");
        System.out.println(getCurrentDate());
        System.out.println(getCurrentDateInYearFormat());
        assertEquals(getCurrentDate(), createApplication.appReceivedDate.getAttribute("value"), "Defaul Application received date does not equal current dat: " + getCurrentDate());
    }

    @And("I verify PI Channel {string} dropdown value")
    public void iVerifyPIChannelDropdownValue(String channelValue) {
        waitFor(1);
        assertEquals(createApplication.getChannel.getAttribute("value"), channelValue, "Actual channel value: " + createApplication.getChannel.getAttribute("value") + " does not match Expected: " + channelValue);
    }

    @And("I verify Primary Individual Channel dropdown value is empty")
    public void iVerifyPrimaryIndividualChannelDropdownValueIsEmpty() {
        scrollToElement(createApplication.getChannel);
        waitFor(1);
        Assert.assertTrue(createApplication.getChannel.getAttribute("value").length() < 1, "Channel Active Contact counter part field value present. Expected empty. Actual " + createApplication.getChannel.getAttribute("value") + " in channel dropdown");
    }

    @Then("I verify the Primary Individual fields are cleared for {string}")
    public void iVerifyThePrimaryIndividualFieldsAreClearedFor(String appType) {
        Assert.assertTrue(createApplication.firstName.getAttribute("value").length() < 1, "First Name field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.middleInitial.getAttribute("value").length() < 1, "Middle Initial field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.lastName.getAttribute("value").length() < 1, "Last Name field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.suffix.getAttribute("value").length() < 1, "Suffix field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.DOB.getAttribute("value").length() < 1, "DOB field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.getGender.getAttribute("value").length() < 1, "Gender field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.SSN.getAttribute("value").length() < 1, "SSN field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.externalConsumerID.getAttribute("value").length() < 1, "External Consumer ID field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.externalIdType.getAttribute("value").length() < 1, "External ID Type field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.cellPhoneNumber.getAttribute("value").length() < 1, "Cell Phone field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.homePhoneNumber.getAttribute("value").length() < 1, "Home Phone field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.workPhoneNumber.getAttribute("value").length() < 1, "Work Phone field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.faxNumber.getAttribute("value").length() < 1, "Fax field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.emailAddress.getAttribute("value").length() < 1, "Email field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.addressLineOne.getAttribute("value").length() < 1, "Residence Add Line 1 field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.addressLineTwo.getAttribute("value").length() < 1, "Residence Add Line 2 field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.residenceCity.getAttribute("value").length() < 1, "Residence City field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.residenceState.getAttribute("value").length() < 1, "Residence State field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.residenceZipcode.getAttribute("value").length() < 1, "Residence Zip Code field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.residenceCounty.getAttribute("value").length() < 1, "Residence County field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.mAddressLineOne.getAttribute("value").length() < 1, "Mailing Add Line 1 field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.mAddressLineTwo.getAttribute("value").length() < 1, "Mailing Add Line 2 field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.mCity.getAttribute("value").length() < 1, "Mailing City field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.mState.getAttribute("value").length() < 1, "Mailing State field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.mZipcode.getAttribute("value").length() < 1, "Mailing Zip Code field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.getChannel.getAttribute("value").length() < 1, "Channel field value still present after Cancel button clicked");
        Assert.assertTrue(createApplication.signatureDate.getAttribute("value").length() < 1, "Signature Date field value still present after Cancel button clicked");
        Assert.assertFalse(createApplication.getEmailCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Email Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getFaxCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Fax Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getMailCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Mail Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getPhoneCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Phone Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getTextCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Text Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getAppSignatureYes.getAttribute("class").contains("Mui-checked"), "Application Yes Checkbox still selected after Cancel button clicked");
        Assert.assertFalse(createApplication.getAppSignatureNo.getAttribute("class").contains("Mui-checked"), "Application No Checkbox still selected after Cancel button clicked");
        if ("MEDICAL ASSISTANCE".equals(appType)) {
            Assert.assertFalse(createApplication.getMedacaidCheckbox.getAttribute("class").contains("Mui-checked"), "Medicaid Checkbox still selected after Cancel button clicked");
            Assert.assertFalse(createApplication.getChipCheckbox.getAttribute("class").contains("Mui-checked"), "CHIP Checkbox still selected after Cancel button clicked");
            Assert.assertFalse(createApplication.getPregnancyAssistanceCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Pregnancy Assistance Checkbox still selected after Cancel button clicked");
        } else if ("LONG TERM CARE".equals(appType)) {
            Assert.assertFalse(createApplication.getHCBSCheckbox.getAttribute("class").contains("Mui-checked"), "HCBS Checkbox still selected after Cancel button clicked");
        } else {
            System.out.println("Application type chosen does not match MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @And("I click on the Edit button for the Primary Individual Details")
    public void iClickOnTheEditButtonForThePrimaryIndividualDetails() {
        waitFor(2);
        scrollToTop();
        jsClick(createApplication.primaryIndividualEditButton);
     //   createApplication.primaryIndividualEditButton.click();
        waitFor(1);
        //scrollDownUsingActions(5);
    }

    @And("I set Primary Individual values from the sent application request payload to be checked")
    public void iSetPrimaryIndividualValuesFromTheSentApplicationRequestPayloadToBeChecked() {
        requestParams = APIATSApplicationController.appPayload.get();
        piFirstName = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("consumerFirstName").getAsString();
        piMiddleInitial = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("consumerMiddleName").getAsString();
        piLastName = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("consumerLastName").getAsString();
        piSuffix = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("consumerSuffix").getAsString();
        piSSN = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("ssn").getAsString();
        piGender = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("genderCode").getAsString();
        piDOB = convertyyyyMMddToMMddyyyy(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("dateOfBirth").toString().substring(1, 11));
        piSpokenLanguage = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("spokenLanguage").getAsString();
        piWrittenLanguage = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("writtenLanguage").getAsString();
        programListPI.add(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").get(1).getAsJsonObject().get("programType").getAsString());
        programListPI.add(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("programs").get(0).getAsJsonObject().get("programType").getAsString());
        commOPtINInfo.add(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("consumerOptInInformation").get(0).getAsString());
        commOPtINInfo.add(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("consumerOptInInformation").get(1).getAsString());
        commOPtINInfo.add(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("consumerOptInInformation").get(2).getAsString());
        if (requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("pregnancyInd").getAsBoolean()) {
            piAreYouPregnant = "Yes";
        } else {
            piAreYouPregnant = "No";
        }
        piBabiesExpected = String.valueOf(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("expectedBabies").getAsInt());
        piExpectedDueDate = convertyyyyMMddToMMddyyyy(requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("expectedDueDate").getAsString());
        piExtConId = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("externalConsumerId").getAsString();
        piExtIdType = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().get("externalConsumerIdType").getAsString();
        workPhone = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerPhone").get(0).getAsJsonObject().get("phoneNumber").getAsString();
        email = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerEmail").get(0).getAsJsonObject().get("emailAddress").getAsString();
        residenceAddressLineOne = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressStreet1").getAsString();
        residenceAddressLineTwo = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressStreet2").getAsString();
        residenceCity = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressCity").getAsString();
        residenceState = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressState").getAsString();
        residenceZipcode = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressZip").getAsString();
        residenceCounty = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0).getAsJsonObject().get("addressCounty").getAsString();
        mailingAddressLineOne = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(1).getAsJsonObject().get("addressStreet1").getAsString();
        mailingAddressLineTwo = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(1).getAsJsonObject().get("addressStreet2").getAsString();
        mailingCity = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(1).getAsJsonObject().get("addressCity").getAsString();
        mailingState = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(1).getAsJsonObject().get("addressState").getAsString();
        mailingZipcode = requestParams.getAsJsonArray("applicationConsumers").get(0).getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(1).getAsJsonObject().get("addressZip").getAsString();
        cycle = requestParams.get("applicationCycle").getAsString();
        receivedDate = convertyyyyMMddToMMddyyyy(requestParams.get("applicationReceivedDate").getAsString().substring(0, 10));
        channel = requestParams.get("channelId").getAsString();
        if (requestParams.get("applicationSignatureExistsInd").getAsBoolean()) {
            signature = "Yes";
        } else {
            signature = "No";
        }
        signatureDate = convertyyyyMMddToMMddyyyy(requestParams.get("applicationSignatureDate").getAsString());
    }

    @Then("I verify {string} warning message displayed in create application page")
    public void i_verify_warning_message_displayed_in_create_application_page(String expectedMessage) {
        switch (expectedMessage.toUpperCase()) {
            case "MUST HAVE AT LEAST ONE APPLICATION MEMBER APPLYING FOR COVERAGE.":
                waitForClickablility(createApplication.submitButtonProgramWarningMessage, 3);
                Assert.assertTrue(createApplication.submitButtonProgramWarningMessage.isDisplayed(), expectedMessage + " is NOT displayed");
                break;
            case "MUST HAVE AT LEAST ONE APPLICATION MEMBER APPLYING FOR COVERAGE, YOUR CHANGES WILL NOT BE SAVED":
                waitForClickablility(createApplication.noProgramSelectedWarningMsg, 3);
                Assert.assertTrue(createApplication.noProgramSelectedWarningMsg.isDisplayed(), expectedMessage + " is NOT displayed");
                break;
            case "APPLICATION MISSING CRITICAL INFORMATION, PLEASE UPDATE THE REQUIRED FIELDS AND SUBMIT AGAIN":
                waitForClickablility(createApplication.missingCriticalInformationWarningMessage, 3);
                Assert.assertTrue(createApplication.missingCriticalInformationWarningMessage.isDisplayed(), expectedMessage + " is NOT displayed");
                break;
            default:
                fail("Expected Message didn't match");
        }
    }

    @Then("I see error message as {string} in the required field {string} in the create application page")
    public void i_see_error_message_as_in_the_required_field_in_the_create_application_page(String expectedErrorMsg, String fieldName) {
        waitFor(1);
        String errorMsg = Driver.getDriver().findElement(By.xpath("//label[text()='" + fieldName + "']/following-sibling::p")).getText();
        System.out.println(errorMsg);
        Assert.assertEquals(errorMsg, expectedErrorMsg, "Error Messages do NOT match or Unexpected Message displayed");
    }


    @And("I verify the following required message is populated")
    public void iVerifyTheFollowingRequiredMessageIsPopulated(List<String> dataTable) {
        for (String eachrequired : dataTable) {
            switch (eachrequired.toUpperCase()) {
                case "FIRST":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.firstNameRequiredMsg), "Expected \"FIRST NAME is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.firstNameRequiredMsg.getText(), "FIRST NAME is required and cannot be left blank", "Expected: \"FIRST NAME is required and cannot be left blank\" message did not appear");
                    break;
                case "LAST":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.lastNameRequiredMsg), "Expected \"LAST NAME is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.lastNameRequiredMsg.getText(), "LAST NAME is required and cannot be left blank", "Expected: \"LAST NAME is required and cannot be left blank\" message did not appear");
                    break;
                case "CELL":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.cellPhoneRequiredMsg), "Expected \"CELLPHONE NUMBER is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.cellPhoneRequiredMsg.getText(), "CELLPHONE NUMBER is required and cannot be left blank", "Expected: \"CELLPHONE NUMBER is required and cannot be left blank\" message did not appear");
                    break;
                case "HOME":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.homePhoneRequiredMsg), "Expected \"FIRST NAME is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.homePhoneRequiredMsg.getText(), "HOME PHONE NUMBER is required and cannot be left blank", "Expected: \"HOME PHONE NUMBER is required and cannot be left blank\" message did not appear");
                    break;
                case "WORK":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.workPhoneRequiredMsg), "Expected \"FIRST NAME is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.workPhoneRequiredMsg.getText(), "WORK PHONE NUMBER is required and cannot be left blank", "Expected: \"WORK PHONE NUMBER is required and cannot be left blank\" message did not appear");
                    break;
                case "FAX":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.faxNumberRequiredMsg), "Expected \"FAX NUMBER is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.faxNumberRequiredMsg.getText(), "FAX NUMBER is required and cannot be left blank", "Expected: \"FAX NUMBER is required and cannot be left blank\" message did not appear");
                    break;
                case "RESIDENCE":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.residenceAddLine1RequiredMsg), "Expected \"ADDRESS LINE 1 is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.residenceAddLine1RequiredMsg.getText(), "ADDRESS LINE 1 is required and cannot be left blank", "Expected: \"ADDRESS LINE 1 is required and cannot be left blank\" message did not appear");
                    break;
                case "MAILING":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.mailAddLine1RequiredMsg), "Expected \"ADDRESS LINE 1 is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.mailAddLine1RequiredMsg.getText(), "ADDRESS LINE 1 is required and cannot be left blank", "Expected: \"ADDRESS LINE 1 is required and cannot be left blank\" message did not appear");
                    break;
                case "CHANNEL":
                    assertTrue(uiAutoUitilities.quickIsDisplayed(createApplication.channelRequiredMsg), "Expected \"CHANNEL is required and cannot be left blank\" Error Message but no message error message found: ");
                    assertEquals(createApplication.channelRequiredMsg.getText(), "CHANNEL is required and cannot be left blank", "Expected: \"CHANNEL is required and cannot be left blank\" message did not appear");
                    break;
                default:
                    fail("Entered value did not match required error message list");

            }
        }
    }

    @Then("I see I navigated to Member Matching page")
    public void i_see_I_navigated_to_Member_Matching_page() {
        waitFor(3);
        scrollToTop();
        Assert.assertTrue(memberMatchingPage.memberMatchingPageHeader.isDisplayed(), "Member matching page is NOT displayed");
        try {
            if (createApplication.successMsgCloseBtn.isDisplayed()) createApplication.successMsgCloseBtn.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("I click on member matching page back arrow to navigate to create application page")
    public void i_click_on_member_matching_page_back_arrow_to_navigate_to_create_application_page() {
        waitFor(3);
      //  assertTrue(memberMatchingPage.memberMatchingPageHeader.isDisplayed(), "Member Matching Page header is not correct");
        memberMatchingPage.memberMatchingBackArrow.click();

    }
//    @Then("I click continue inside warning message to navigate to member matching page")
//    public void i_click_continue_inside_warning_message_to_navigate_to_member_matching_page() {
//         waitForClickablility(createApplicationMemberPage.navigatAwayWarningContinue, 2);
//        createApplicationMemberPage.navigatAwayWarningContinue.click();
//    }
//    @Then("I close the Success Message displayed in Create Application Page by clicking Close button")
//    public void i_close_the_Success_Message_displayed_in_Create_Application_Page_by_clicking_Close_button() {
//        createApplication.successMsgCloseBtn.click();
    //   }

    @And("I click on application tracking tab to navigate to Application Tracking page")
    public void iClickOnApplicationTrackingTabToNavigateToApplicationTrackingPage() {
        waitForVisibility(createApplication.appTrackingTab, 5);
        createApplication.appTrackingTab.click();
        waitFor(3);
    }

    @And("I click on missing information tab to navigate to Missing Information page")
    public void iClickOnMissingInformationTabToNavigateToMissingInformationPage() {
        waitForVisibility(createApplication.missingInfoTab, 7);
        jsClick(createApplication.missingInfoTab);
      //  createApplication.missingInfoTab.click();
    }

    @Then("I verify that there is no Inbound Correspondence application pdf view icon")
    public void iVerifyThatThereIsNoInboundCorrespondenceApplicationPdfViewIcon() {
        scrollToElement(createApplication.applicationStatus);
        waitFor(2);
        assertFalse(isElementDisplayed(createApplication.inbCorrPDFIconListFalseCondition), "Expected no Inbound Correspondence pdf view Icon but found icon displayed");
    }

    @Then("I verify that there is an Inbound Correspondence application pdf view icon")
    public void iVerifyThatThereIsAnInboundCorrespondenceApplicationPdfViewIcon() {
        waitFor(2);
        assertTrue(isElementDisplayed(createApplication.inbCorrPDFIconList.get(0)), "Expected Inbound Correspondence pdf view Icon but did not find icon displayed");
    }

    @And("I click on the Inbound Correspondence application pdf view icon")
    public void iClickOnTheInboundCorrespondenceApplicationPdfViewIcon() {
        waitForVisibility(createApplication.inbCorrPDFIconList.get(0), 10);
        createApplication.inbCorrPDFIconList.get(0).click();
    }

    @And("I click on {string} Inbound Correspondence application pdf view icon")
    public void iClickOnInboundCorrespondenceApplicationPdfViewIcon(String correspondenceOrder) {

        switch (correspondenceOrder.toUpperCase()){
            case "FIRST":
                waitForVisibility(createApplication.inbCorrPDFIconList.get(0), 10);
                createApplication.inbCorrPDFIconList.get(0).click();
                break;
            case "SECOND":
                waitForVisibility(createApplication.inbCorrPDFIconList.get(1), 10);
                createApplication.inbCorrPDFIconList.get(1).click();
                break;
            default:Assert.fail("Correspondence Number mismatched");
        }
    }

    @Then("I Verify the Priority Label and the default values for the dropdown is set to {string}")
    public void iVerifyThePriorityLabelAndTheDefaultValuesForTheDropdownIsSetTo(String priorityValue) {
        waitForVisibility(createApplication.priorityLabel, 5);
        Assert.assertEquals(filterForLettersOnly(createApplication.priorityLabel.getText()), "PRIORITY", "No Priority label found in Primary Individual Member Creation");
        Assert.assertEquals(createApplication.priorityDropdown.getText(), priorityValue, "Expected default value of " + priorityValue + " in dropdown but found: " + createApplication.priorityDropdown.getText());

    }

    @And("I verify the Priorty dropdown contains the following values")
    public void iVerifyThePriortyDropdownContainsTheFollowingValues(List<String> dataTable) {
        waitForVisibility(createApplication.priorityDropdown, 5);
        createApplication.priorityDropdown.click();
        waitFor(2);
        List<String> expectedPriority = new ArrayList<>(dataTable);
        List<String> actualPriority = new ArrayList<>();
        for (WebElement each : createApplication.priorityDropdownList) {
            actualPriority.add(each.getText());
        }
        Collections.sort(expectedPriority);
        Collections.sort(actualPriority);
        Assert.assertEquals(actualPriority, expectedPriority, "Mismatching Priority dropdown list found");

    }

    @And("I verify the {string} Priority value on the Application details Page")
    public void iVerifyThePriorityValueOnTheApplicationDetailsPage(String expectedPriorityValue) {
        waitForVisibility(createApplication.savedPriorityValue, 10);
        Assert.assertEquals(createApplication.savedPriorityValue.getText(), expectedPriorityValue, "Unexpected Priority value found in saved Application details page");
    }

    @And("I select {string} value from the Application Priority dropdown")
    public void iSelectValueFromTheApplicationPriorityDropdown(String priorityValue) {
        waitForVisibility(createApplication.priorityDropdown, 10);
        selectDropDown(createApplication.priorityDropdown, priorityValue);
        waitFor(1);
    }

    @And("I verify the Priority dropdown is enabled for application in edit mode")
    public void iVerifyThePriorityDropdownIsEnabledForApplicationInEditMode() {
        waitForVisibility(createApplication.priorityDropdown, 10);
        Assert.assertTrue(createApplication.priorityDropdown.isEnabled());
    }

    @And("I verify the Contact Info Information Icon label with the hover over message {string}")
    public void iVerifyTheContactInfoInformationIconLabelWithTheHoverOverMessage(String expectedMsg) {
        hover(createApplication.contactInfoIcon);
        waitFor(2);
        Assert.assertEquals(createApplication.contactinfoIconHoverOverMsg.getText(), expectedMsg, "Contact Info Icon Hover Over Message did not match");
    }

    @Then("I verify that {string} Star icon is captured and following are not captured")
    public void iVerifyThatStarIconIsCapturedAndFollowingAreNotCaptured(String expectedCapturedStar, List<String> nonCapturedStar) {
        switch (expectedCapturedStar.toUpperCase()) {
            case "CELL PHONE":
                Assert.assertEquals(createApplication.cellPhoneStarIcon.getText(), "star", "No Solid star found to indicate phone type is the Primary phone indicated");
                break;
            case "HOME PHONE":
                Assert.assertEquals(createApplication.homePhoneStarIcon.getText(), "star", "No Solid star found to indicate phone type is the Primary phone indicated");
                break;
            case "WORK PHONE":
                Assert.assertEquals(createApplication.workPhoneStarIcon.getText(), "star", "No Solid star found to indicate phone type is the Primary phone indicated");
                break;
            default:
                Assert.fail("Expected key did not match");
        }
        for (String each : nonCapturedStar) {
            switch (each.toUpperCase()) {
                case "CELL PHONE":
                    Assert.assertEquals(createApplication.cellPhoneStarIcon.getText(), "star_border", "No star shape border found to indicate phone type is NOT the Primary phone indicated");
                    break;
                case "HOME PHONE":
                    Assert.assertEquals(createApplication.homePhoneStarIcon.getText(), "star_border", "No star shape border found to indicate phone type is NOT the Primary phone indicated");
                    break;
                case "WORK PHONE":
                    Assert.assertEquals(createApplication.workPhoneStarIcon.getText(), "star_border", "No star shape border found to indicate phone type is NOT the Primary phone indicated");
                    break;
                default:
                    Assert.fail("Expected key did not match");
            }
        }
    }

    @Then("I verify the field is required asterick symbol is generated next to the {string} Address field labels")
    public void iVerifyTheFieldIsRequiredAsterickSymbolIsGeneratedNextToTheAddressFieldLabels(String addressType) {
        if ("RESIDENCE".equals(addressType)) {
            assertTrue(createApplication.residenceAddressOneLabel.getText().contains("*"), "Expected an asterick next to residence Address One label to indicate field is required");
            assertTrue(createApplication.residenceCityLabel.getText().contains("*"), "Expected an asterick next to residence City label to indicate field is required");
            assertTrue(createApplication.residenceStateLabel.getText().contains("*"), "Expected an asterick next to residence State label to indicate field is required");
            assertTrue(createApplication.residencZipLabel.getText().contains("*"), "Expected an asterick next to residence Zip label to indicate field is required");
            assertTrue(createApplication.residenceCountyLabel.getText().contains("*"), "Expected an asterick next to residence County label to indicate field is required");
        } else if ("MAILING".equals(addressType)) {
            assertTrue(createApplication.mailAddressStreetOneLabel.getText().contains("*"), "Expected an asterick next to mail Address Street One label to indicate field is required");
            assertTrue(createApplication.mailCityLabel.getText().contains("*"), "Expected an asterick next to mail City label to indicate field is required");
            assertTrue(createApplication.mailStateLabel.getText().contains("*"), "Expected an asterick next to mail State label to indicate field is required");
            assertTrue(createApplication.mailZipLabel.getText().contains("*"), "Expected an asterick next to label mail Zip to indicate field is required");
        }
    }

    @And("I click on the Same As Residence Address checkbox in Primary Individual Application Create")
    public void iClickOnTheSameAsResidenceAddressCheckboxInPrimaryIndividualApplicationCreate() {
        createApplication.sameAsResidenceCheckBox.click();
        applicantInformation.put("mailing", "true");
        applicantInformation.put("residential", "");
        applicantInformation.put("applicationConsumerType", "Primary Individual");
        applicantInformation.put("addressStreet1", createApplication.addressLineOne.getAttribute("value"));
        applicantInformation.put("addressStreet2", createApplication.addressLineTwo.getAttribute("value"));
        applicantInformation.put("addressCity", createApplication.residenceCity.getAttribute("value"));
        applicantInformation.put("addressState", createApplication.residenceState.getAttribute("value"));
        applicantInformation.put("addressZip", createApplication.residenceZipcode.getAttribute("value"));
        waitFor(2);
    }

    @Then("I verify corresponding Residence address values are populated for Mailing Address")
    public void iVerifyCorrespondingResidenceAddressValuesArePopulatedForMailingAddress() {
        assertEquals(createApplication.sameAsResidenceMailAddressOne.getText(), residenceAddressLineOne, "Mismatch in Residence and Mailing Address value when Same As residence is checked: ");
        assertEquals(createApplication.sameAsResidenceMailAddressTwo.getText(), residenceAddressLineTwo, "Mismatch in Residence and Mailing Address value when Same As residence is checked: ");
        assertEquals(createApplication.sameAsResidenceMailCity.getText(), residenceCity, "Mismatch in Residence and Mailing Address value when Same As residence is checked: ");
        assertEquals(createApplication.sameAsResidenceMailState.getText(), residenceState, "Mismatch in Residence and Mailing Address value when Same As residence is checked: ");
        assertEquals(createApplication.sameAsResidenceMailZipCode.getText(), residenceZipcode, "Mismatch in Residence and Mailing Address value when Same As residence is checked: ");
    }

    @And("I verify the Mailing Fields are disabled and unable to be edited")
    public void iVerifyTheMailingFieldsAreDisabledAndUnableToBeEdited() {
        assertFalse(uiAutoUitilities.quickIsDisplayed(createApplication.mAddressLineOne), "Editable Mail Address Line One field found after Same as Residence Address selected");
        assertFalse(uiAutoUitilities.quickIsDisplayed(createApplication.mAddressLineTwo), "Editable Mail Address Line Two field found after Same as Residence Address selected");
        assertFalse(uiAutoUitilities.quickIsDisplayed(createApplication.mCity), "Editable Mail City field found after Same as Residence Address selected");
        assertFalse(uiAutoUitilities.quickIsDisplayed(createApplication.mState), "Editable Mail State field found after Same as Residence Address selected");
        assertFalse(uiAutoUitilities.quickIsDisplayed(createApplication.mZipcode), "Editable Mail Zip Code field found after Same as Residence Address selected");
    }

    @Then("I verify Application Contact Info phone number value is in correct Phone Number Format")
    public void iVerifyApplicationContactInfoPhoneNumberValueIsInCorrectPhoneNumberFormat() {
        assertEquals(createApplication.cellPhoneNumber.getAttribute("value"), convertNumToEntryPhone(cellPhone), "Mismatch in expected and actual phone number displayed in data entry");
        assertEquals(createApplication.homePhoneNumber.getAttribute("value"), convertNumToEntryPhone(homePhone), "Mismatch in expected and actual phone number displayed in data entry");
        assertEquals(createApplication.faxNumber.getAttribute("value"), convertNumToEntryPhone(faxNumber), "Mismatch in expected and actual phone number displayed in data entry");
        assertEquals(createApplication.workPhoneNumber.getAttribute("value"), convertNumToEntryPhone(workPhone), "Mismatch in expected and actual phone number displayed in data entry");
    }

    public String convertNumToEntryPhone(String num) {
        Assert.assertTrue(num.length() == 10);
        num = num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6);
        return num;
    }

    @Then("I verify the PRIMARY INDIVIDUAL DETAILS dropdown values for following")
    public void iVerifyThePRIMARYINDIVIDUALDETAILSDropdownValuesForFollowing(List<String> data) {
        for (String eachDropdownType : data) {
            switch (eachDropdownType.toUpperCase()) {
                case "GENDER":
                    createApplication.genderDropdown.click();
                    waitFor(1);
                    Collections.sort(expectedGenderList);
                    List<String> actualGenderList = new ArrayList<>();
                    for (WebElement each : createApplication.genderList) {
                        actualGenderList.add(each.getText());
                    }
                    Collections.sort(actualGenderList);
                    Assert.assertEquals(actualGenderList, expectedGenderList, "List of Gender Dropdown Values did not meet expected");
                    createApplication.genderList.get(1).click();
                    waitFor(1);
                    break;
                case "SPOKEN LANGUAGE":
                    createApplication.spokenLanguageDropdown.click();
                    waitFor(1);
                    Collections.sort(expectedSpokenLangList);
                    List<String> actualSpokenLangList = new ArrayList<>();
                    for (WebElement each : createApplication.spokenLanguageList) {
                        actualSpokenLangList.add(each.getText());
                    }
                    Collections.sort(actualSpokenLangList);
                    Assert.assertEquals(actualSpokenLangList, expectedSpokenLangList, "List of Spoken Language Dropdown Values did not meet expected");
                    createApplication.spokenLanguageList.get(1).click();
                    waitFor(1);
                    break;
                case "WRITTEN LANGUAGE":
                    createApplication.writtenLanguageDropdown.click();
                    waitFor(1);
                    Collections.sort(expectedWrittenLangList);
                    List<String> actualWrittenLangList = new ArrayList<>();
                    for (WebElement each : createApplication.writtenLanguageList) {
                        actualWrittenLangList.add(each.getText());
                    }
                    Collections.sort(actualWrittenLangList);
                    Assert.assertEquals(actualWrittenLangList, expectedWrittenLangList, "List of Written Language Dropdown Values did not meet expected");
                    createApplication.writtenLanguageList.get(1).click();
                    waitFor(1);
                    break;
                case "EXTERNAL ID TYPE":
                    createApplication.externalIdTypeDropdown.click();
                    waitFor(1);
                    Collections.sort(expectedExIdTypeList);
                    List<String> actualExIdTypeList = new ArrayList<>();
                    for (WebElement each : createApplication.exIdTypeList) {
                        actualExIdTypeList.add(each.getText());
                    }
                    Collections.sort(actualExIdTypeList);
                    Assert.assertEquals(actualExIdTypeList, expectedExIdTypeList, "List of External Id Type Dropdown Values did not meet expected");
                    createApplication.exIdTypeList.get(1).click();
                    waitFor(1);
                    break;
                case "CHANNEL":
                    createApplication.channelDropdown.click();
                    waitFor(1);
                    Collections.sort(expectedChannelList);
                    List<String> actualChannelList = new ArrayList<>();
                    for (WebElement each : createApplication.channelList) {
                        actualChannelList.add(each.getText());
                    }
                    Collections.sort(actualChannelList);
                    Assert.assertEquals(actualChannelList, expectedChannelList, "List of Channel Dropdown Values did not meet expected");
                    createApplication.channelList.get(1).click();
                    waitFor(1);
                    break;
                case "BABIES EXPECTED":
                    waitForClickablility(createApplication.numberOfBabiesDropdown, 4);
                    createApplication.numberOfBabiesDropdown.click();
                    waitFor(2);
                    Collections.sort(expectednoOfBabiesList);
                    List<String> actualNoOfExpBabiesList = new ArrayList<>();
                    for (WebElement each : createApplication.noOfBabiesExpectedList) {
                        actualNoOfExpBabiesList.add(each.getText());
                    }
                    Collections.sort(actualNoOfExpBabiesList);
                    Assert.assertEquals(actualNoOfExpBabiesList, expectednoOfBabiesList, "List of number Of BabiesDropdown Dropdown Values did not meet expected");
                    createApplication.noOfBabiesExpectedList.get(1).click();
                    waitFor(1);
                    break;
                default:
                    Assert.fail("Provided key does not match expected switch key");
            }
        }
    }

    @Then("I verify {string} application status in the top right corner in the Application details tab")
    public void iVerifyApplicationStatusInTheTopRightCornerInTheApplicationDetailsTab(String expectedStatus) {
        waitFor(2);
        String actualStatus = createApplication.appStatusTextInGold.getText();
        Assert.assertEquals(expectedStatus.toLowerCase(), actualStatus.toLowerCase(), "Expected Application status: " + expectedStatus + " But found: " + actualStatus);
    }


    @When("I verify {string} button is displayed and clickable")
    public void i_verify_button_is_displayed_and_clickable(String buttonType) {
        switch (buttonType) {
            case "Edit Primary Individual":
                waitFor(2);
                assertTrue(createApplication.primaryIndividualEditButton.isDisplayed(), "No Edit Button is displayed for Primary Individual");
                assertTrue(createApplication.primaryIndividualEditButton.isEnabled(), "Edit Button isnt clickable for Primary Individual");
                break;
            case "Add Application Member":
                waitFor(2);
                assertTrue(createApplication.addApplicationMemberButton.isDisplayed(), "No Add button is displayed for Application Member");
                assertTrue(createApplication.addApplicationMemberButton.isEnabled(), "Add button isnt clickable for Application Member");
                break;

            case "Add Authorized Representative":
                waitFor(2);
                assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed(), "No Add button is displayed for Authorized Representative");
                assertTrue(createApplication.addAuthorizedRepBtn.isEnabled(), "Add button isnt clickable for Authorized Representative");
                break;

            case "Set as Primary Individual":
                waitFor(2);
                assertTrue(createApplication.appMemSetAsPIButton.isDisplayed(), "No Set As PI button is displayed for Application Member");
                assertTrue(createApplication.appMemSetAsPIButton.isEnabled(), "Set As PI button isnt clickable for Application Member");
                break;

            case "Remove Application Member":
                waitFor(2);
                assertTrue(createApplicationMemberPage.removeButton.isDisplayed(), "No Remove button is displayed for Application Member");
                assertTrue(createApplicationMemberPage.removeButton.isEnabled(), "Remove app member button isnt clickable for Application Member");
                break;

            case "Edit Authorized Representative":
                waitFor(2);
                assertTrue(addAuthorizedRepresentativePage.arEditBtn.isDisplayed(), "No Edit button is displayed for Authorized Representative");
                assertTrue(addAuthorizedRepresentativePage.arEditBtn.isEnabled(), "Edit button isnt clickable for Authorized Representative");
                break;

            case "Submit":
                waitFor(2);
                assertTrue(createApplication.submitBtn.isDisplayed(), "Submit button isnt displayed");
                assertTrue(createApplication.submitBtn.isEnabled(), "Submit button isnt clickable");
                break;

        }
    }

    @When("I verify {string} button is disabled and not clickable")
    public void i_verify_button_is_disabled_and_not_clickable(String buttonType) {
        switch (buttonType) {
            case "Edit Primary Individual":
                waitFor(2);
                assertTrue(createApplication.primaryIndividualEditButton.isDisplayed(), "No Edit Button is displayed for Primary Individual");
                assertTrue(createApplication.primaryIndividualEditButton.getAttribute("class").contains("disabled"), "Edit button is clickable");
                break;
            case "Add Application Member":
                waitFor(2);
                assertTrue(createApplication.addApplicationMemberButton.isDisplayed(), "No Add button is displayed for Application Member");
                assertTrue(createApplication.addApplicationMemberButton.getAttribute("class").contains("disabled"), "Add button is clickable for Application Member");
                break;
            case "Add Authorized Representative":
                waitFor(2);
                assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed(), "No Add button is displayed for Authorized Representative");
                assertTrue(createApplication.addAuthorizedRepBtn.getAttribute("class").contains("disabled"), "Add button is clickable for Authorized Representative");
                break;

        }
    }

    @And("I verify application is in read-only mode")
    public void readOnlyApplication() {
        List<WebElement> elements = createApplication.inputFields;
        System.out.println(elements.size());
        Assert.assertTrue(elements.size() == 3, "There are input fields in write mode");
    }

    @Then("I verify {string} check box are disabled")
    public void iVerifyCheckBoxAreDisabled(String checkBoxType) {
        waitFor(2);
        switch (checkBoxType.toUpperCase()) {
            case "PROGRAMS":
                Assert.assertFalse(createApplication.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not disabled");
                Assert.assertFalse(createApplication.CHIPCheckbox.isEnabled(), "Chip Checkbox is not disabled");
                Assert.assertFalse(createApplication.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not disabled");
                break;
            case "MEDICAID":
                Assert.assertFalse(createApplication.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not disabled");
                break;
            case "CHIP":
                Assert.assertFalse(createApplication.CHIPCheckbox.isEnabled(), "Chip Checkbox is not disabled");
                break;
            case "PREGNANCY ASSISTANCE":
                Assert.assertFalse(createApplication.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not disabled");
                break;
            case "HCBS":
                Assert.assertFalse(createApplication.HCBSCheckbox.isEnabled(), "HCBS Checkbox is not disabled");
                break;
            case "NOT APPLYING":
                Assert.assertFalse(createApplication.NOTAPPLYINGCheckbox.isEnabled(), "Not Applying Checkbox is not disabled");
                break;
        }
    }

    @Then("I deselect {string} program type")
    public void iDeselectProgramType(String programType) {
        waitFor(1);
        switch (programType.toUpperCase()) {
            case "ALL":
                createApplication.medicaidCheckbox.click();
                jsClick(createApplication.CHIPCheckbox);
                createApplication.pregnancyAssistanceCheckbox.click();
                programListPI.clear();
                break;
            case "MEDICAID":
                if (createApplication.medicaidCheckbox.isSelected()) {
                    createApplication.medicaidCheckbox.click();
                    programListPI.remove("Medicaid");
                }
                break;
            case "CHIP":
                if (createApplication.CHIPCheckbox.isSelected()) {
                    jsClick(createApplication.CHIPCheckbox);
                    programListPI.remove("CHIP");
                }
                break;
            case "PREGNANCY ASSISTANCE":
                if (createApplication.pregnancyAssistanceCheckbox.isSelected()) {
                    jsClick(createApplication.pregnancyAssistanceCheckbox);
                    programListPI.remove("Pregnancy Assistance");
                }
                break;
            case "HCBS":
                if (createApplication.HCBSCheckbox.isSelected()) {
                    jsClick(createApplication.HCBSCheckbox);
                    programListPI.remove("HCBS");
                }
                break;
            case "NOT APPLYING":
                scrollToElement(createApplication.NOTAPPLYINGCheckbox);
                waitFor(5);
                if (createApplication.NOTAPPLYINGCheckbox.isSelected()) {
                  //  createApplication.NOTAPPLYINGCheckbox.click();
                    jsClick(createApplication.NOTAPPLYINGCheckbox);
                }
                break;
        }
    }

    @Then("I verify all the program type checkboxes are enabled and clickable for {string}")
    public void iVerifyAllTheProgramTypeCheckboxesAreEnabledAndClickableFor(String applicationType) {
        switch (applicationType.toUpperCase()) {
            case "MEDICAL ASSISTANCE":
                Assert.assertTrue(createApplication.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not enabled");
                Assert.assertTrue(createApplication.CHIPCheckbox.isEnabled(), "CHIP Checkbox is not enabled");
                Assert.assertTrue(createApplication.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not enabled");
                break;
            case "LONG TERM CARE":
                Assert.assertTrue(createApplication.HCBSCheckbox.isEnabled(), "HCBS Checkbox is not enabled");
                break;
        }
    }

    @Then("I clear the program list for {string} Application Members")
    public void iClearTheProgramListForApplicationMembers(String applicationMemberType) {
        switch (applicationMemberType.toUpperCase()) {
            case "ALL":
                programListPI.clear();
                programList.get().clear();
                break;
            case "PI":
                programListPI.clear();
                break;
            case "AM":
                programList.get().clear();
                break;
        }
    }

    @Then("I verify programs applied for field contains {string} program for {string}")
    public void i_verify_programs_applied_for_field_contains_program_for(String programType, String applicationMemberType) {
        waitFor(2);
        if (applicationMemberType.equalsIgnoreCase("Application Member")) {
            Assert.assertTrue(createApplication.prgramsAppliedForAM.getText().contains(programType), "Programs Applied field for doesn't contain " + programType);
        } else if (applicationMemberType.equalsIgnoreCase("Primary Individual")) {
            Assert.assertTrue(createApplication.savedPrograms.getText().contains(programType), "Programs Applied field for doesn't contain " + programType);
        }
    }

    @Then("I verify user is on the create Application Member Page")
    public void iVerifyUserIsOnTheCreateApplicationMemberPage() {
        Assert.assertTrue(createApplicationMemberPage.applicationMemberHeader.isDisplayed(), "User is not on the Create Application Page");
    }

    @And("I click on continue button for your changes will not be saved")
    public void iClickOnContinueButtonForYourChangesWillNotBeSaved() {
        waitForVisibility(createApplication.changeWillNotBeSavedWarningContinueButton, 10);
        createApplication.changeWillNotBeSavedWarningContinueButton.click();
    }

    @And("I cleared DOB from primary individual on create application page")
    public void iClearedDOBFromPrimaryIndividualOnCreateApplicationPage() {
        createApplication.DOB.clear();
    }

    @And("I verify the Received Language dropdown values with the following")
    public void iVerifyTheReceivedLanguageDropdownValuesWithTheFollowing(List<String> data) {
        waitFor(2);
        assertEquals(createApplication.receivedLanguageLabel.getText(), "RECEIVED LANGUAGE", "Expected RECEIVED LANGUAGE dropdown label but did not find");
        assertEquals(createApplication.receivedLanguageDropdown.getText(), "English", "Mismatch in default RECEIVED LANGUAGE value set as ENGLISH");

        createApplication.receivedLanguageDropdown.click();
        waitFor(1);

        List<String> actualLanguageList = new ArrayList<>();
        List<String> expectedLanguage = new ArrayList<>();
        data.forEach(each -> expectedLanguage.add(each));
        createApplication.receivedLanguageList.forEach(each -> actualLanguageList.add(each.getText()));
        Collections.sort(actualLanguageList);
        Collections.sort(expectedLanguage);
        Assert.assertEquals(actualLanguageList, expectedLanguage, "List of Received language Dropdown Values did not meet expected");
        createApplication.receivedLanguageList.get(1).click();
        waitFor(1);
    }

    @And("I save and store Application Code from application tab after UI application is created")
    public void iSaveAndStoreApplicationCodeFromApplicationTabAfterUIApplicationIsCreated() {
        waitForVisibility(createApplication.savedApplicationCode, 10);
        applicationCode = createApplication.savedApplicationCode.getText();
    }

    @When("I click on application tab page after creating application")
    public void iClickOnApplicationTabPageAfterCreatingApplication(){
        waitForClickablility(createApplication.applicationTab,3);
        createApplication.applicationTab.click();
    }

    @And("I click on {string} button in Create Application Page")
    public void iClickOnNavigationButtonInCreateApplicationPage(String pageName) {
        waitFor(10);
        assertTrue(createApplication.statusFetchedMessage.isDisplayed(),"Status fetched message isnt displayed");
        switch (pageName) {
            case "GO TO APPLICATION TAB":
                waitForVisibility(createApplication.goToApplicationTabBtn,5);
                scrollToElement(createApplication.goToApplicationTabBtn);
                assertTrue(createApplication.determiningStatusOnWarningMessage.isDisplayed());
                createApplication.goToApplicationTabBtn.click();
                break;
            case "GO TO MEMBER MATCHING TAB":
                waitForVisibility(createApplication.goToMemberMatchingTabBtn,5);
                scrollToElement(createApplication.goToMemberMatchingTabBtn);
                assertTrue(createApplication.targetsUnidentifiedStatusOnWarningMessage.isDisplayed());
                createApplication.goToMemberMatchingTabBtn.click();
                break;

        }


    }

    @Then("I verify that application signature ind is selected {string}")
    public void i_verify_that_application_signature_ind_is_selected(String appSignatureInd) {
        scrollToElement(createApplication.appSignatureLabel);
        assertTrue(createApplication.appSignatureYes.getAttribute("value").equals(appSignatureInd),"Application signature isnt selected by default");

    }
}