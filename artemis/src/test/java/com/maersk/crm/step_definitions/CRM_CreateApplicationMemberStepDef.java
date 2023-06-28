package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateApplicationMemberPage;
import com.maersk.crm.pages.crm.CRMCreateApplicationPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class CRM_CreateApplicationMemberStepDef extends CRMUtilities implements ApiBase {

    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    CRMCreateApplicationMemberPage appMember = new CRMCreateApplicationMemberPage();
    final ThreadLocal<CRM_CreateApplicationStepDef> applicationStepDef = ThreadLocal.withInitial(CRM_CreateApplicationStepDef::new);
    public static final ThreadLocal<List<String>> programList = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<String> firstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> mi = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> lastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> suffix = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> dob = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> gender = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> numOfBabies = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> areYouPregnant = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> dueDate = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> ssn = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> appPanelAgeGender = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> appPanelProgram = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> requestParams = new ThreadLocal<>();
    private final ThreadLocal<String> nullValue = ThreadLocal.withInitial(() -> "-- --");
    public static final ThreadLocal<String> age = new ThreadLocal<>();

    @Then("I click on + Add Application Member button")
    public void iClickOnAddApplicationMemberButton() {
        waitForVisibility(createApplication.addApplicationMemberButton, 5);
        waitFor(2);
        scrollToElement(createApplication.addApplicationMemberButton);
        jsClick(createApplication.addApplicationMemberButton);
     //   createApplication.addApplicationMemberButton.click();
        waitFor(5);
    }

    @And("I verify Application Member header on create Application Member Page")
    public void iVerifyApplicationMemberHeaderOnCreateApplicationMemberPage() {
        waitFor(2);
        Assert.assertTrue(appMember.applicationMemberHeader.getText().contains("APPLICATION MEMBER"), "Did not navigate to ADD APPLICATION MEMBER page");
    }

    @Then("I verify the labels for add application {string}")
    public void iVerifyTheLabelsForAddApplication(String appType) {
        Assert.assertEquals(filterTextFor(appMember.firstNameLabel.getText()), "FIRST NAME", "Label FIRST NAME expected, Actual: " + appMember.firstNameLabel.getText());
        Assert.assertEquals(appMember.middleInitialLabel.getText(), "MI", "Label MI expected, Actual: " + appMember.middleInitialLabel.getText());
        Assert.assertEquals(filterTextFor(appMember.lastNameLabel.getText()), "LAST NAME", "Label LAST NAME * expected, Actual: " + appMember.lastNameLabel.getText());
        Assert.assertEquals(appMember.suffixLabel.getText(), "SUFFIX", "Label SUFFIX expected, Actual: " + appMember.suffixLabel.getText());
        Assert.assertEquals(appMember.dobLabel.getText(), "DATE OF BIRTH", "Label DATE OF BIRTH expected, Actual: " + appMember.dobLabel.getText());
        Assert.assertEquals(appMember.ageLabel.getText(), "AGE", "Label AGE expected, Actual: " + appMember.ageLabel.getText());
        Assert.assertEquals(appMember.genderLabel.getText(), "GENDER", "Label GENDER expected, Actual: " + appMember.genderLabel.getText());
        Assert.assertEquals(appMember.ssnLabel.getText(), "SSN", "Label SSN expected, Actual: " + appMember.ssnLabel.getText());
        Assert.assertEquals(appMember.externalConsumerIDLabel.getText(), "EXTERNAL CONSUMER ID", "Label EXTERNAL CONSUMER ID expected, Actual: " + appMember.externalConsumerIDLabel.getText());
        Assert.assertEquals(appMember.externalIDTypeLabel.getText(), "EXTERNAL ID TYPE", "Label EXTERNAL ID TYPE expected, Actual: " + appMember.externalIDTypeLabel.getText());
        Assert.assertEquals(appMember.setPrimaryLabel.getText(), "SET PRIMARY INDIVIDUAL", "Label SET PRIMARY INDIVIDUAL expected, Actual: " + appMember.setPrimaryLabel.getText());
        if ("MEDICAL ASSISTANCE".equals(appType)) {
            Assert.assertEquals(appMember.medicaidLabel.getText(), "MEDICAID", "Label MEDICAID expected, Actual: " + appMember.medicaidLabel.getText());
            Assert.assertEquals(appMember.chipLabel.getText(), "CHIP", "Label CHIP expected, Actual: " + appMember.chipLabel.getText());
            Assert.assertEquals(appMember.pregnancyAssistanceLabel.getText(), "PREGNANCY ASSISTANCE", "Label PREGNANCY ASSISTANCE expected, Actual: " + appMember.pregnancyAssistanceLabel.getText());
        } else if ("LONG TERM CARE".equals(appType)) {
            Assert.assertEquals(appMember.hcbsLabel.getText(), "HCBS", "Label HCBS expected, Actual: " + appMember.hcbsLabel.getText());
        } else {
            Assert.fail("Application type does not match MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @Then("I fill in the following application member values")
    public void iFillInTheFollowingApplicationMemberValuesFor(Map<String, String> data) {
        for (String eachVerifyValue : data.keySet()) {
            switch (eachVerifyValue.toUpperCase()) {
                case "FIRST NAME":
                    if ("RANDOM 30".equals(data.get("FIRST NAME"))) {
                        synchronized (firstName){
                            firstName.set(getRandomString(30));
                        }
                        clearAndFillTextWithSendKeys(appMember.firstName, firstName.get());
                    } else if ("Random 5".equals(data.get("FIRST NAME"))) {
                        synchronized (firstName){
                            firstName.set(getRandomString(5));
                        }
                        clearAndFillTextWithSendKeys(appMember.firstName, firstName.get());
                    } else {
                        synchronized (firstName){
                            firstName.set(data.get("FIRST NAME"));
                        }
                        clearAndFillTextWithSendKeys(appMember.firstName, firstName.get());
                    }
                    break;
                case "MI":
                    if ("RANDOM 1".equals(data.get("MI"))) {
                        synchronized (mi){
                            mi.set(getRandomString(1));
                        }
                        clearAndFillTextWithSendKeys(appMember.middleInitial, mi.get());
                    } else {
                        synchronized (firstName){
                            firstName.set(data.get("FIRST NAME"));
                        }
                        clearAndFillTextWithSendKeys(appMember.middleInitial, mi.get());
                    }
                    break;
                case "LAST NAME":
                    if ("RANDOM 30".equals(data.get("LAST NAME"))) {
                        synchronized (lastName){
                            lastName.set(getRandomString(30));
                        }
                        clearAndFillTextWithSendKeys(appMember.lastName, lastName.get());
                    } else if ("Random 5".equals(data.get("LAST NAME"))) {
                        synchronized (lastName){
                            lastName.set(getRandomString(5));
                        }
                        clearAndFillTextWithSendKeys(appMember.lastName, lastName.get());
                    } else {
                        synchronized (lastName){
                            lastName.set(data.get("LAST NAME"));
                        }
                        clearAndFillTextWithSendKeys(appMember.lastName, lastName.get());
                    }
                    break;
                case "SUFFIX":
                    if ("RANDOM 3".equals(data.get("SUFFIX"))) {
                        synchronized (suffix){
                            suffix.set(getRandomString(3));
                        }
                        clearAndFillTextWithSendKeys(appMember.suffix, suffix.get());
                    } else {
                        synchronized (firstName){
                            firstName.set(data.get("FIRST NAME"));
                        }
                        clearAndFillTextWithSendKeys(appMember.suffix, suffix.get());
                    }
                    break;
                case "DOB":
                    if ("random past date".equals(data.get("DOB"))) {
                        int randomNum = Integer.parseInt(getRandomNumber(3));
                        synchronized (dob){
                            dob.set(getPriorDateFormatMMddyyyy(randomNum));
                        }
                        clearAndFillTextWithSendKeys(appMember.dob, dob.get());
                        synchronized (age){
                            age.set(String.valueOf(calculateAge(dob.get())));
                        }
                    } else {
                        synchronized (dob){
                            dob.set(data.get("DOB"));
                        }
                        clearAndFillTextWithSendKeys(appMember.dob, dob.get());
                    }
                    break;
                case "GENDER":
                    synchronized (gender){
                        gender.set(data.get("GENDER"));
                    }
                    selectDropDown(appMember.genderDropdown, gender.get());
                    waitFor(1);
                    break;
                case "ARE YOU PREGNANT":
                    synchronized (areYouPregnant){
                        areYouPregnant.set(data.get("ARE YOU PREGNANT"));
                        if ("YES".equals(areYouPregnant.get())) {
                            areYouPregnant.set("YES");
                            appMember.yesAreYouPregnant.click();
                            waitFor(1);
                        } else if ("NO".equals(areYouPregnant.get())) {
                            areYouPregnant.set("NO");
                            appMember.noAreYouPregnant.click();
                            waitFor(1);
                        } else {
                            fail("Value for Are you Pregnant is not YES or NO");
                        }
                    }
                    break;
                case "NO. OF BABIES":
                    waitForClickablility(appMember.numBabiesDropdown, 5);
                    synchronized (numOfBabies){
                        numOfBabies.set(data.get("NO. OF BABIES"));
                    }
                    selectDropDown(appMember.numBabiesDropdown, numOfBabies.get());
                    break;
                case "EXPECTED DUE DATE":
                  synchronized (dueDate){
                      if ("RANDOM".equals(data.get("EXPECTED DUE DATE"))) {
                          int random = Integer.parseInt(getRandomNumber(2));
                          dueDate.set(getGreaterDateFormatMMddyyyy(random));
                          clearAndFillTextWithSendKeys(appMember.expectedDueDate, dueDate.get());
                          break;
                      } else {
                          dueDate.set(data.get("EXPECTED DUE DATE"));
                          clearAndFillTextWithSendKeys(appMember.expectedDueDate, dueDate.get());
                      }
                  }
                case "SSN":
                   synchronized (ssn){
                       if ("Numeric 9".equals(data.get("SSN"))) {
                           ssn.set(getRandomNumber(9));
                           clearAndFillTextWithSendKeys(appMember.ssn, ssn.get());
                       } else if ("Edit Numeric 9".equals(data.get("SSN"))) {
                           appMember.ssnRevealIcon.click();
                           waitFor(2);
                           ssn.set(getRandomNumber(9));
                           clearAndFillTextWithSendKeys(appMember.ssn, ssn.get());
                       } else {
                           ssn.set(data.get("SSN"));
                           clearAndFillTextWithSendKeys(appMember.ssn, ssn.get());
                       }
                   }
                    break;
                case "EXTERNAL CONSUMER ID":
                    waitForClickablility(appMember.externalConsumerId, 5);
                   synchronized (consumerID){
                       if ("RANDOM".equals(data.get("EXTERNAL CONSUMER ID"))) {
                           consumerID.set(RandomStringUtils.random(15, true, true));
                           clearAndFillTextWithSendKeys(appMember.externalConsumerId, consumerID.get());
                       } else {
                           consumerID.set(data.get("EXTERNAL CONSUMER ID"));
                           clearAndFillTextWithSendKeys(appMember.externalConsumerId, consumerID.get());
                       }
                   }
                    break;
                case "EXTERNAL ID TYPE":
                    waitFor(1);
                   synchronized (consumerType){
                       if ("random dropdown".equals(data.get("EXTERNAL ID TYPE"))) {
                           selectRandomDropDownOption(appMember.getExternalIdType);
                           consumerType.set(appMember.getExternalIdType.getText());
                       } else {
                           selectDropDown(appMember.getExternalIdType, data.get("EXTERNAL ID TYPE"));
                           consumerType.set(appMember.getExternalIdType.getText());
                       }
                   }
                    break;
            }
        }
    }

    @And("Select the {string} Program\\(s) for application member")
    public void selectTheProgramSForApplicationMember(String programType) {
        waitFor(1);
        switch (programType.toUpperCase()) {
            case "MEDICAID":
                appMember.medicaidCheckbox.click();
                programList.get().add("Medicaid");
                break;
            case "CHIP":
                appMember.chipCheckbox.click();
                programList.get().add("CHIP");
                break;
            case "PREGNANCY ASSISTANCE":
                appMember.pregnancyAssistanceCheckbox.click();
                programList.get().add("Pregnancy Assistance");
                break;
            case "HCBS":
                appMember.hcbsCheckbox.click();
                programList.get().add("HCBS");
                break;
            case "NOT APPLYING":
                createApplication.NOTAPPLYINGCheckbox.click();
                programList.get().clear();
                break;
        }
    }

    @Then("I deselect {string} program type for Application Member")
    public void iDeselectProgramType(String programType) {
        switch (programType.toUpperCase()) {
            case "ALL":
                appMember.medicaidCheckbox.click();
                jsClick(appMember.chipCheckbox);
                appMember.pregnancyAssistanceCheckbox.click();
                programList.get().clear();
                break;
            case "MEDICAID":
                if (appMember.medicaidCheckbox.isSelected()) {
                    appMember.medicaidCheckbox.click();
                    programList.get().remove("Medicaid");
                }
                break;
            case "CHIP":
                if (appMember.chipCheckbox.isSelected()) {
                    jsClick(appMember.chipCheckbox);
                    programList.get().remove("CHIP");
                }
                break;
            case "PREGNANCY ASSISTANCE":
                if (appMember.pregnancyAssistanceCheckbox.isSelected()) {
                    appMember.pregnancyAssistanceCheckbox.click();
                    programList.get().remove("Pregnancy Assistance");
                }
                break;
            case "HCBS":
                if (appMember.hcbsCheckbox.isSelected()) {
                    appMember.hcbsCheckbox.click();
                    programList.get().remove("HCBS");
                }
                break;
            case "NOT APPLYING":
                if (appMember.NOTAPPLYINGCheckBox.isSelected()) {
                    appMember.NOTAPPLYINGCheckBox.click();
                }
                break;
        }
    }

    @And("click on save button for add application member")
    public void clickOnSaveButtonForAddApplicationMember() {
        waitForVisibility(appMember.saveButton, 10);
        appMember.saveButton.click();
    }

    @And("I verify the save successfully updated message")
    public void thenIVerifyTheSaveSuccessfullyUpdatedMessage() {
        waitForVisibility(appMember.appSuccesfullyUpdatedMessage, 5);
        assertTrue(appMember.appSuccesfullyUpdatedMessage.isDisplayed());
        applicationStepDef.get().applicationId = createApplication.createdAppId.getText();
        System.out.println("Application id for updated application:" + applicationStepDef.get().applicationId);
    }

    @Then("I verify the First and last name required warning message in Add application")
    public void iVerifyTheFirstAndLastNameRequiredWarningMessageInAddApplication() {
        String fNamemsg = "FIRST NAME is required and cannot be left blank";
        String lNamemsg = "LAST NAME is required and cannot be left blank";
        waitFor(2);
        Assert.assertEquals(appMember.firstNameRequiredMsg.getText(), fNamemsg, "Expected " + fNamemsg + "does not match actual: " + appMember.firstNameRequiredMsg.getText());
        Assert.assertEquals(appMember.lastNameRequiredMsg.getText(), lNamemsg, "Expected " + lNamemsg + "does not match actual: " + appMember.lastNameRequiredMsg.getText());
    }

    @Then("I verify the labels in application members panel collapsed View")
    public void iVerifyTheLabelsInApplicationMembersPanelCollapsedView() {
        scrollToElement(createApplication.appMemFullNameLabel);
        waitFor(2);
        assertEquals(createApplication.appMemFullNameLabel.getText(), "FULL NAME", "Expected Application Member panel label: Full Name but found " + createApplication.appMemFullNameLabel.getText());
        assertEquals(createApplication.appMemDOBLabel.getText(), "DATE OF BIRTH", "Expected Application Member panel label: DATE OF BIRTH but found " + createApplication.appMemDOBLabel.getText());
        assertEquals(filterTextFor(createApplication.appMemSSNLabel.getText()), "SSN visibility", "Expected Application Member panel label: SSN but found " + createApplication.appMemSSNLabel.getText());
        assertEquals(createApplication.appMemAgeGenderLabel.getText(), "AGE/GENDER", "Expected Application Member panel label: AGE/GENDER but found " + createApplication.appMemSSNLabel.getText());
        assertEquals(createApplication.appMemProgramsLabel.getText(), "PROGRAM(S) APPLIED FOR", "Expected Application Member panel label: PROGRAM(S) APPLIED FOR but found " + createApplication.appMemSSNLabel.getText());
        assertTrue(createApplication.appMemSetAsPIButton.isDisplayed(), "Application member panel SET AS PI Button is not displayed");
        assertTrue(createApplication.appMemDeleteIcon.isDisplayed(), "Application member panel DELETE Button is not displayed");
    }

    @And("I verify the labels in application members panel expanded view")
    public void iVerifyTheLabelsInApplicationMembersPanelExpandedView() {
        scrollToElement(createApplication.appMemFullNameLabel);
        waitFor(1);
        createApplication.appMemberPanelCarrot.click();
        waitFor(1);
        assertEquals(createApplication.appMemberAreYouPregnantLabel.getText(), "ARE YOU PREGNANT", "Expected Application Member panel label: ARE YOU PREGNANT but found " + createApplication.appMemberAreYouPregnantLabel.getText());
        assertEquals(createApplication.appMemberNumBaibiesExpectedLabel.getText(), "NO.OF BABIES EXPECTED", "Expected Application Member panel label: NO.OF BABIES EXPECTED but found " + createApplication.appMemberNumBaibiesExpectedLabel.getText());
        assertEquals(createApplication.appMemberExpectedDueDateLabel.getText(), "EXPECTED DUE DATE", "Expected Application Member panel label: EXPECTED DUE DATE but found " + createApplication.appMemberExpectedDueDateLabel.getText());
        assertEquals(createApplication.appMemberExConIDLabel.getText(), "EXTERNAL CONSUMER ID", "Expected Application Member panel label: EXTERNAL CONSUMER ID but found " + createApplication.appMemberExConIDLabel.getText());
        assertEquals(createApplication.appMemberExConTypeLabel.getText(), "EXTERNAL ID TYPE", "Expected Application Member panel label: EXTERNAL ID TYPE but found " + createApplication.appMemberExConTypeLabel.getText());
    }

    @Then("I verify the saved application values in Application Member\\(s) panel collapsed view")
    public void iVerifyTheSavedApplicationValuesInApplicationMemberSPanelCollapsedView() {
        waitFor(2);
        String appMemFullName = firstName + " " + mi.get() + " " + lastName.get() + " " + suffix.get();
        assertEquals(createApplication.appMemGetFullName.getText(), appMemFullName, "Actual full name: " + createApplication.appMemGetFullName.getText() + "in Application Member Panel does not equal expected: " + appMemFullName);
        assertEquals(createApplication.appMemGetDoB.getText(), dob.get(), "Actual DoB: " + createApplication.appMemGetDoB.getText() + " does not match expected DoB: " + dob);
        scrollToElement(createApplication.appMemFullNameLabel);
        createApplication.appMemSSNRevealIcon.click();
        waitFor(1);
        String valueSSN = ssn.get().substring(0, 3) + "-" + ssn.get().substring(3, 5) + "-" + ssn.get().substring(5);
        assertEquals(createApplication.appMemGetSSN.getText(), valueSSN, "Actual SSN: " + createApplication.appMemGetSSN.getText() + " does not match expected SSN: " + valueSSN);
        String ageGender = age + " / " + gender.get().substring(0, 1).toUpperCase();
        assertEquals(createApplication.appMemGetAgeGender.getText(), ageGender, "Actual age/gender: " + createApplication.appMemGetAgeGender.getText() + " does not match expected: " + ageGender);
        String actualPrograms = createApplication.appMemGetPrograms.getText();
        for (String eachProgram : programList.get()) {
            Assert.assertTrue(actualPrograms.contains(eachProgram), "Actual Program List: " + programList.get() + " does not contain expected program: " + eachProgram);
        }
    }

    @And("I verify the saved application values in Application Member\\(s) panel expanded view")
    public void iVerifyTheSavedApplicationValuesInApplicationMemberSPanelExpandedView() {
        createApplication.appMemberPanelCarrot.click();
        waitFor(2);
        assertEquals(createApplication.appMemberAreYouPregnantValue.getText(), "Yes", "Actual Are you Pregnant value: " + createApplication.appMemberAreYouPregnantValue.getText() + " does not match expected: Yes");
        assertEquals(createApplication.appMemberNumBaibiesExpectedValue.getText(), numOfBabies.get(), " Actual No.OF BABIES EXPECTED: " + createApplication.appMemberNumBaibiesExpectedValue.getText() + "expected value: " + numOfBabies);
        assertEquals(createApplication.appMemberExpectedDueDateValue.getText(), dueDate.get(), " Actual Expected Due Date: " + createApplication.appMemberExpectedDueDateValue.getText() + "expected value: " + dueDate);
        assertEquals(createApplication.appMemberExConIDValue.getText(), consumerID.get(), " Actual Expected External Consumer Id: " + createApplication.appMemberExConIDValue.getText() + "expected value: " + consumerID);
        assertEquals(createApplication.appMemberExConTypeValue.getText(), consumerType.get(), " Actual Expected External Consumer Type: " + createApplication.appMemberExConTypeValue.getText() + "expected value: " + consumerType);
    }

    @Then("I verify the {string} message is displayed")
    public void iVerifyTheMessageIsDisplayed(String warningMsg) {
        waitForVisibility(appMember.piSetWarningMsg, 10);
        assertEquals(appMember.piSetWarningMsg.getText(), warningMsg, "Actual warning message: " + appMember.piSetWarningMsg.getText() + " Expected warning message: " + warningMsg);
    }

    @And("I select continue to change application member to Primary Individual")
    public void iSelectContinueToChangeApplicationMemberToPrimaryIndividual() {
        waitFor(1);
        waitForVisibility(appMember.piSetWarningContinue, 10);
        appMember.piSetWarningContinue.click();
    }

    @And("I verify the the application member is primary member and the previous PI is an application member")
    public void iVerifyTheTheApplicationMemberIsPrimaryMemberAndThePreviousPIIsAnApplicationMember() {
        waitFor(2);
        String newPIname = createApplication.savedFullName.getText();
        String expectedFullName = firstName + " " + mi.get() + " " + lastName.get() + " " + suffix.get();
        assertEquals(newPIname, expectedFullName, "Expected new Primary Individual Name to be: " + expectedFullName + " but got: " + newPIname);
    }

    @And("I click on Set PI checkbox in add application member page")
    public void iClickOnSetPICheckboxInAddApplicationMemberPage() {
        appMember.setPrimaryIndividualCheckbox.click();
        waitFor(1);
    }

    @And("I click on application member name hyperlink to go to Application details page")
    public void iClickOnApplicationMemberNameHyperlinkToGoToApplicationDetailsPage() {
        waitFor(2);
        scrollToElement(createApplication.appMemGetFullName);
        jsClick(createApplication.appMemGetFullName);
      //  createApplication.appMemGetFullName.click();
        Assert.assertTrue(appMember.applicationMemberHeader.getText().contains("APPLICATION MEMBER"), "Did not navigate to ADD APPLICATION MEMBER page");
    }

    @And("I verify edit button is displayed and clickable in Add Application member")
    public void iVerifyEditButtonIsDisplayedAndClickableInAddApplicationMember() {
        waitFor(2);
        assertTrue(appMember.appMemEditButton.isDisplayed(), "No Edit Button is displayed for Add Application member");
        assertTrue(appMember.appMemEditButton.isEnabled(), "Edit Button is clickable for Add Application member");
    }

    @Then("I click on the back arrow button on add application member page")
    public void iClickOnTheBackArrowButtonOnAddApplicationMemberPage() {
        waitFor(2);
        appMember.backArrow.click();
    }

    @And("verify the {string} warning message")
    public void verifyTheWarningMessage(String navigateAwayWarningMsg) {
        waitForVisibility(appMember.navigateAwayWarningMsg, 10);
        assertEquals(appMember.navigateAwayWarningMsg.getText(), navigateAwayWarningMsg, "Actual warning message: " + appMember.navigateAwayWarningMsg.getText() + " Expected Warning Message: " + navigateAwayWarningMsg);
    }

    @And("I verify I am on application details tab by clicking on continue")
    public void iVerifyIAmOnApplicationDetailsTabByClickingOnContinue() {
        appMember.navigatAwayWarningContinue.click();
        waitFor(2);
        assertEquals(createApplication.applicationTab.getText(), "APPLICATION", "No Application tab page found");
    }

    @And("I verify no {string} warning message has appeared")
    public void iVerifyNoWarningMessageHasAppeared(String navigateAwayWarningMsg) {
        assertEquals(textIsNotPresent(navigateAwayWarningMsg), true, "Warning Message: " + navigateAwayWarningMsg + " appeared");
        assertEquals(createApplication.applicationTab.getText(), "APPLICATION", "No Application tab page found after clicking backarrow from Add Application member page");
    }

    @Then("I click on the Cancel Button in add application member page")
    public void iClickOnTheCancelButtonInAddApplicationMemberPage() {
        waitFor(1);
        appMember.cancelButton.click();
    }

    @And("I verify all fields in add application member page has cleared for {string}")
    public void iVerifyAllFieldsInAddApplicationMemberPageHasClearedFor(String appType) {
        Assert.assertTrue(appMember.firstName.getAttribute("value").length() < 1, "First Name field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.middleInitial.getAttribute("value").length() < 1, "Middle Initial field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.lastName.getAttribute("value").length() < 1, "Last Name field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.suffix.getAttribute("value").length() < 1, "Suffix field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.dob.getAttribute("value").length() < 1, "DOB field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.getGenderValue.getAttribute("value").length() < 1, "Gender field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.ssn.getAttribute("value").length() < 1, "SSN field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.externalConsumerId.getAttribute("value").length() < 1, "External Consumer ID field value still present after Cancel button clicked");
        Assert.assertTrue(appMember.externalIdType.getAttribute("value").length() < 1, "External ID Type field value still present after Cancel button clicked");
        Assert.assertFalse(appMember.getSetPICheckbox.getAttribute("class").contains("Mui-checked"), "Set Primary Individual still selected after Cancel button clicked");
        if ("MEDICAL ASSISTANCE".equals(appType)) {
            Assert.assertFalse(appMember.getMedacaidCheckbox.getAttribute("class").contains("Mui-checked"), "Medicaid Checkbox still selected after Cancel button clicked");
            Assert.assertFalse(appMember.getChipCheckbox.getAttribute("class").contains("Mui-checked"), "CHIP Checkbox still selected after Cancel button clicked");
            Assert.assertFalse(appMember.getPregnancyAssistanceCheckboxCheckbox.getAttribute("class").contains("Mui-checked"), "Pregnancy Assistance Checkbox still selected after Cancel button clicked");
        } else if ("LONG TERM CARE".equals(appType)) {
            Assert.assertFalse(appMember.getHCBSCheckbox.getAttribute("class").contains("Mui-checked"), "HCBS Checkbox still selected after Cancel button clicked");
        } else {
            System.out.println("Application type chosen does not match MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @Then("I verify selected application member program is shown in the application member panel")
    public void iVerifySelectedApplicationMemberProgramIsShownInTheApplicationMemberPanel() {
        String actualPrograms = createApplication.appMemGetPrograms.getText();
        for (String eachProgram : programList.get())
            Assert.assertTrue(actualPrograms.contains(eachProgram), "Actual Program List: " + programList.get() + " does not contain expected program: " + eachProgram);
    }

    @And("I click on the delete application member button in the application member button panel")
    public void iClickOnTheDeleteApplicationMemberButtonInTheApplicationMemberButtonPanel() {
        scrollToElement(appMember.applicationMemberHeader);
        createApplication.applicationPanelDeleteButton.click();
        waitFor(2);
        createApplication.continueBtn.click();
    }

    @Then("I verify the application member is deleted from the application member\\(s) panel")
    public void iVerifyTheApplicationMemberIsDeletedFromTheApplicationMemberSPanel() {
        scrollToElement(appMember.applicationMemberHeader);
        waitFor(2);
        assertEquals(createApplication.applicationPanelNoRecordsMessage.getText(), "No Records Available", "Application member was not removed. Application member still remains in panel grid");
    }

    @And("I click on the remove application member from add application member screen")
    public void iClickOnTheRemoveApplicationMemberFromAddApplicationMemberScreen() {
        waitFor(2);
        appMember.removeButton.click();
        waitFor(2);
        appMember.removeMemberMsgContinue.click();
    }

    @And("I click on the edit button for Add application Member")
    public void iClickOnTheEditButtonForAddApplicationMember() {
        waitFor(2);
        jsClick(appMember.appMemEditButton);
    }

    @And("I set Application Member Values from the sent application request payload to be checked")
    public void iSetApplicationMemberValuesFromTheSentApplicationRequestPayloadToBeChecked() {
        synchronized (requestParams){
            requestParams.set(APIATSApplicationController.appPayload.get());
        }

        firstName.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerFirstName").getAsString());
        mi.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerMiddleName").getAsString());
        lastName.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerLastName").getAsString());
        suffix.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("consumerSuffix").getAsString());
        ssn.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("ssn").getAsString());
        gender.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("genderCode").getAsString());
        dob.set(convertyyyyMMddToMMddyyyy(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("dateOfBirth").getAsString().substring(0, 10)));
        age.set(String.valueOf(calculateAge(dob.get())));
        programList.get().add(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("programs").get(0).getAsJsonObject().get("programType").getAsString());
        programList.get().add(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().getAsJsonArray("programs").get(1).getAsJsonObject().get("programType").getAsString());
        if (requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("pregnancyInd").getAsBoolean()) {
            areYouPregnant.set("Yes");
        } else {
            areYouPregnant.set("No");
        }
        numOfBabies.set(String.valueOf(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("expectedBabies").getAsInt()));
        dueDate.set(convertyyyyMMddToMMddyyyy(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("expectedDueDate").getAsString()));
        consumerID.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("externalConsumerId").getAsString());
        consumerType.set(requestParams.get().getAsJsonArray("applicationConsumers").get(1).getAsJsonObject().get("externalConsumerIdType").getAsString());
    }

    @Then("I verify double dash is represented for NULL values for Application Member in detail panel information")
    public void iVerifyDoubleDashIsRepresentedForNULLValuesForApplicationMemberInDetailPanelInformation() {
        waitFor(2);
        scrollToElement(createApplication.appMemFullNameLabel);
        assertEquals(createApplication.appMemGetDoB.getText(), nullValue.get(), "Actual DoB does not match expected DoB: ");
        assertEquals(createApplication.appMemGetSSN.getText(), nullValue.get(), "Actual SSN does not match expected SSN: ");
        assertEquals(createApplication.appMemGetAgeGender.getText(), nullValue.get(), "Actual age/gender does not match expected Age/Gender: ");
        assertEquals(createApplication.appMemGetPrograms.getText(), nullValue.get(), "Actual Program(S) Applied For does not contain expected program: ");
        waitFor(1);
        createApplication.appMemberPanelCarrot.click();
        waitFor(1);
        assertEquals(createApplication.appMemberNumBaibiesExpectedValue.getText(), nullValue.get(), "Actual NO.OF BABIES EXPECTED does not match expected NO.OF BABIES EXPECTED: ");
        assertEquals(createApplication.appMemberExpectedDueDateValue.getText(), nullValue.get(), "Actual EXPECTED DUE DATE does not match expected EXPECTED DUE DATE: ");
        assertEquals(createApplication.appMemberExConIDValue.getText(), nullValue.get(), "Actual EXTERNAL CONSUMER ID does not match expected EXTERNAL CONSUMER ID: ");
        assertEquals(createApplication.appMemberExConTypeValue.getText(), nullValue.get(), "Actual EXTERNAL ID TYPE does not match expected EXTERNAL ID TYPE: ");
    }

    @Then("I verify Application Member detail page has correct labels for {string}")
    public void iVerifyApplicationMemberDetailPageHasCorrectLabelsFor(String appType) {
        assertEquals(appMember.savedFirstNameLabel.getText(), "FIRST NAME");
        assertEquals(appMember.savedMiLabel.getText(), "MI");
        assertEquals(appMember.savedLastNameLabel.getText(), "LAST NAME");
        assertEquals(appMember.savedSuffixLabel.getText(), "SUFFIX");
        assertEquals(appMember.savedDOBLabel.getText(), "DATE OF BIRTH");
        assertEquals(appMember.savedAgeLabel.getText(), "AGE");
        assertEquals(appMember.savedGenderLabel.getText(), "GENDER");
        assertEquals(appMember.savedAreYouPregnantLabel.getText(), "ARE YOU PREGNANT");
        assertEquals(appMember.savedNoOfBabiesLabel.getText(), "NO. OF BABIES EXPECTED");
        assertEquals(appMember.savedSSNLabel.getText(), "SSN");
        assertEquals(appMember.savedExConIdLabel.getText(), "EXTERNAL CONSUMER ID");
        assertEquals(appMember.savedExIdTypeLabel.getText(), "EXTERNAL ID TYPE");
        assertEquals(appMember.savedSetPrimaryIndLabel.getText(), "SET PRIMARY INDIVIDUAL");
        assertEquals(appMember.savedProgramsAppliedLabel.getText(), "PROGRAM(S) APPLIED FOR");
        if ("LONG TERM CARE".equals(appType)) {
            assertEquals(appMember.savedHCBSLabel.getText(), "HCBS");
        } else if ("MEDICAL ASSISTANCE".equals(appType)) {
            assertEquals(appMember.savedMedicaidLabel.getText(), "MEDICAID");
            assertEquals(appMember.savedCHIPLabel.getText(), "CHIP");
            assertEquals(appMember.savedPregAssistLabel.getText(), "PREGNANCY ASSISTANCE");
        } else {
            fail("Provided Application type did not match MEDICAL ASSISTANCE or LONG TERM CARE");
        }
    }

    @Then("I verify the Application member Values in the application member details page")
    public void iVerifyTheApplicationMemberValuesInTheApplicationMemberDetailsPage(List<String> data) {
        for (String eachValue : data) {
            switch (eachValue.toUpperCase()) {
                case "FIRST":
                    assertEquals(appMember.savedFirstName.getText(), firstName.get(), "Expected First Name in App Mem details page did not match: ");
                    break;
                case "MI":
                    assertEquals(appMember.savedMi.getText(), mi.get(), "Expected Middle Initial in App Mem details page did not match: ");
                    break;
                case "LAST":
                    assertEquals(appMember.savedLastName.getText(), lastName.get(), "Expected lastName in App Mem details page did not match: ");
                    break;
                case "SUFFIX":
                    assertEquals(appMember.savedSuffix.getText(), suffix.get(), "Expected suffix in App Mem details page did not match: ");
                    break;
                case "DOB":
                    assertEquals(appMember.savedDOB.getText(), dob.get(), "Expected dob in App Mem details page did not match: ");
                    break;
                case "AGE":
                    String expectedAge = String.valueOf(calculateAge(dob.get()));
                    assertEquals(appMember.savedAge.getText(), expectedAge, "Expected age in App Mem details page did not match: ");
                    break;
                case "GENDER":
                    assertEquals(appMember.savedGender.getText(), gender.get(), "Expected gender in App Mem details page did not match: ");
                    break;
                case "ARE YOU PREGNANT":
                    if ("YES".equals(areYouPregnant.get())) {
                        assertTrue(appMember.savedAreYouPregnantYes.getAttribute("class").contains("checked"), "Expected \"Yes\" for ARE YOU PREGNANT in App Mem details page did not match: ");
                    } else if ("NO".equals(areYouPregnant.get())) {
                        assertTrue(appMember.savedAreYouPregnantNo.getAttribute("class").contains("checked"), "Expected \"No\" for ARE YOU PREGNANT in App Mem details page did not match: ");
                    } else {
                        fail("Value for Are you Pregnant is not YES or NO");
                    }
                    break;
                case "NO OF BABIES":
                    assertEquals(appMember.savedNoOfBabies.getText(), numOfBabies.get(), "Expected numOfBabies in App Mem details page did not match: ");
                    break;
                case "DUE DATE":
                    assertEquals(appMember.savedExpectedDueDate.getText(), dueDate.get(), "Expected dueDate in App Mem details page did not match: ");
                    break;
                case "SSN":
                    jsClick(appMember.ssnRevealIcon);
                    waitFor(1);
                    assertEquals(appMember.savedSSN.getText().substring(0, 11).replaceAll("-", ""), ssn.get(), "Expected ssn in App Mem details page did not match: ");
                    break;
                case "EXID":
                    assertEquals(appMember.savedExConId.getText(), consumerID.get(), "Expected consumerID in App Mem details page did not match: ");
                    break;
                case "EXTYPE":
                    assertEquals(appMember.savedExIdType.getText(), consumerType.get(), "Expected consumerType in App Mem details page did not match: ");
                    break;
                case "PROGRAMS":
                    for (String eachProg : programList.get()) {
                        switch (eachProg) {
                            case "Medicaid":
                                assertTrue(appMember.savedMedicaidselectBox.getAttribute("class").contains("checked"), "Expected Medicaid in Program List but not found");
                                break;
                            case "CHIP":
                                assertTrue(appMember.savedCHIPselectBox.getAttribute("class").contains("checked"), "Expected CHIP in Program List but not found");
                                break;
                            case "Pregnancy Assistance":
                                assertTrue(appMember.savedPregAssistCheckBox.getAttribute("class").contains("checked"), "Expected Pregnancy Assistance in Program List but not found");
                                break;
                            case "HCBS":
                                assertTrue(appMember.savedHCBSCheckBox.getAttribute("class").contains("checked"), "Expected HCBS in Program List but not found");
                                break;
                            default:
                                fail("does not contain expected app member programs");
                        }
                    }
            }
        }
    }

    @Then("I validate double dashes are used for null values in Application members detail page")
    public void iValidateDoubleDashesAreUsedForNullValuesInApplicationMembersDetailPage(List<String> data) {
        for (String eachValue : data) {
            switch (eachValue.toUpperCase()) {
                case "MI":
                    assertEquals(appMember.savedMi.getText(), nullValue.get(), "Expected \"-- --\" for Application Member MI null value but found: ");
                    break;
                case "SUFFIX":
                    assertEquals(appMember.savedSuffix.getText(), nullValue.get(), "Expected \"-- --\" for Application Member SUFFIX null value but found: ");
                    break;
                case "DOB":
                    assertEquals(appMember.savedDOB.getText(), nullValue.get(), "Expected \"-- --\" for Application Member DATE OF BIRTH null value but found: ");
                    break;
                case "AGE":
                    assertEquals(appMember.savedAge.getText(), nullValue.get(), "Expected \"-- --\" for Application Member AGE null value but found: ");
                    break;
                case "GENDER":
                    assertEquals(appMember.savedGender.getText(), nullValue.get(), "Expected \"-- --\" for Application Member GENDER null value but found: ");
                    break;
                case "NO OF BABIES":
                    assertEquals(appMember.savedNoOfBabies.getText(), nullValue.get(), "Expected \"-- --\" for Application Member NO. OF BABIES EXPECTED null value but found: ");
                    break;
                case "DUE DATE":
                    assertEquals(appMember.savedExpectedDueDate.getText(), nullValue.get(), "Expected \"-- --\" for Application Member EXPECTED DUE DATE null value but found: ");
                    break;
                case "SSN":
                    assertEquals(appMember.savedSSN.getText(), nullValue.get(), "Expected \"-- --\" for Application Member SSN null value but found: ");
                    break;
                case "EXID":
                    assertEquals(appMember.savedExConId.getText(), nullValue.get(), "Expected \"-- --\" for Application Member EXTERNAL CONSUMER ID null value but found: ");
                    break;
                case "EXTYPE":
                    assertEquals(appMember.savedExIdType.getText(), nullValue.get(), "Expected \"-- --\" for Application Member EXTERNAL ID TYPE null value but found: ");
                    break;
                default:
                    fail("Entered Value does not match");
            }
        }
    }

    @Then("I see application member\\(s) details displayed in this order in the Application tab")
    public void i_see_application_member_s_details_displayed_in_this_order_in_the_Application_tab(List<Map<String, String>> data) {
        BrowserUtils.waitFor(2);
        Map<String, String> expectedDetails = data.get(0);
        List<String> actualDetails = new ArrayList<>();

        // remove first element is expander and remove last element because its empty col
        for (int i = 1; i < createApplication.applicationMemberSinApplicationTab.size() - 3; i++) {
            actualDetails.add(createApplication.applicationMemberSinApplicationTab.get(i).getText());
        }

        System.out.println("Expected: " + expectedDetails.size());
        System.out.println("Actual: " + actualDetails.size());

        System.out.println("Expected: " + expectedDetails);
        System.out.println("Actual: " + actualDetails);

        Assert.assertEquals(actualDetails.size(), expectedDetails.size());

        int i = 0;
        for (String key : expectedDetails.keySet()) {
            String value = expectedDetails.get(key);
            if (value.equalsIgnoreCase("base")) {
                switch (key.toUpperCase().trim()) {
                    case "FULL NAME":
                        value = firstName.get() + " " + mi.get() + " " + lastName.get() + " " + suffix.get();
                        break;
                    default:
                        throw new IllegalArgumentException(key + " does not support base value(from dynamic creation)");
                }
            }

            Assert.assertEquals(value.toLowerCase().trim(), actualDetails.get(i).toLowerCase().trim());

            i++;
        }
    }

    @Then("I verify all the program type checkboxes are enabled and clickable for {string} for Application Member")
    public void iVerifyAllTheProgramTypeCheckboxesAreEnabledAndClickableFor(String applicationType) {
        switch (applicationType.toUpperCase()) {
            case "MEDICAL ASSISTANCE":
                Assert.assertTrue(appMember.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not enabled");
                Assert.assertTrue(appMember.chipCheckbox.isEnabled(), "CHIP Checkbox is not enabled");
                Assert.assertTrue(appMember.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not enabled");
                break;
            case "LONG TERM CARE":
                Assert.assertTrue(appMember.hcbsCheckbox.isEnabled(), "HCBS Checkbox is not enabled");
                break;
        }
    }

    @Then("I verify {string} check box are disabled for Application Member")
    public void iVerifyCheckBoxAreDisabled(String checkBoxType) {
        waitFor(2);
        switch (checkBoxType.toUpperCase()) {
            case "PROGRAMS":
                Assert.assertFalse(appMember.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not disabled for Application Member");
                Assert.assertFalse(appMember.chipCheckbox.isEnabled(), "Chip Checkbox is not disabled for Application Member");
                Assert.assertFalse(appMember.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not disabled for Application Member");
                break;
            case "MEDICAID":
                Assert.assertFalse(appMember.medicaidCheckbox.isEnabled(), "Medicaid Checkbox is not disabled for Application Member");
                break;
            case "CHIP":
                Assert.assertFalse(appMember.chipCheckbox.isEnabled(), "Chip Checkbox is not disabled for Application Member");
                break;
            case "PREGNANCY ASSISTANCE":
                Assert.assertFalse(appMember.pregnancyAssistanceCheckbox.isEnabled(), "Pregnancy Assistance Checkbox is not disabled for Application Member");
                break;
            case "HCBS":
                Assert.assertFalse(appMember.hcbsCheckbox.isEnabled(), "HCBS Checkbox is not disabled for Application Member");
                break;
            case "NOT APPLYING":
                Assert.assertFalse(appMember.NOTAPPLYINGCheckBox.isEnabled(), "Not Applying Checkbox is not disabled for Application Member");
                break;
        }
    }
}