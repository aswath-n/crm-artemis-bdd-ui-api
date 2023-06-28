package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.api_step_definitions.APIATSConsumersController;
import com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController;
import com.maersk.crm.api_step_definitions.APIContactRecordController;
import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMMemberMatchingPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.duplicateFirstname;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.duplicateLastName;
import static com.maersk.crm.api_step_definitions.APIATSConsumersController.applicationConsumerIdList;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.*;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationMemberStepDef.*;
import static com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef.*;
import static org.testng.Assert.*;

public class CRM_MemberMatchingStepDef extends CRMUtilities implements ApiBase {
    CRMMemberMatchingPage memberMatchingPage = new CRMMemberMatchingPage();
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    CRM_CreateApplicationMemberStepDef crm_createApplicationMemberStepDef = new CRM_CreateApplicationMemberStepDef();
    final ThreadLocal<APIATSApplicationController> saveSubmit = ThreadLocal.withInitial(APIATSApplicationController::new);
    final ThreadLocal<APIATSConsumersController> apiatsConsumersController = ThreadLocal.withInitial(APIATSConsumersController::new);
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController=new APIATSSendEventAndCreateLinksController();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMContactRecordUIPage contactRecordUIPage=new CRMContactRecordUIPage();
    public static final ThreadLocal<String> linkedAppID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> linkedAppStatus = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> researchReasonNotes = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> researchReason = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerNotOnACaseID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> appMemberOneID = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> onlyCaseMemberID = ThreadLocal.withInitial(String::new);
    private CRMCreateConsumerProfilePage crmCreateConsumerProfilePage = new CRMCreateConsumerProfilePage();


    @Then("I verify following details on Member Matching Page for {string}")
    public void i_verify_following_details_on_Member_Matching_Page_for(String type, List<String> values) {
        waitFor(5);
        if (type.equalsIgnoreCase("Application Details")) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "APPLICATION ID":
                        assertTrue(memberMatchingPage.applicationIDText.getText().contains(applicationId), "Expected application ID" + applicationId + "does not match actual: " + memberMatchingPage.applicationIDText.getText());
                        break;
                    case "APPLICATION STATUS":
                        assertEquals(memberMatchingPage.applicationStatusText.getText(), "SUBMITTED", "Expected application status SUBMITTED does not match actual: " + memberMatchingPage.applicationStatusText.getText());
                        break;
                    case "ADDRESS LINE 1 AND 2":
                        assertEquals(memberMatchingPage.addressLineOneTwo.getText(), residenceAddressLineOne + ", " + residenceAddressLineTwo, "Expected address line " + residenceAddressLineOne + ", " + residenceAddressLineTwo + "does not match actual: " + memberMatchingPage.addressLineOneTwo.getText());
                        break;
                    case "CITY STATE ZIP":
                        assertEquals(memberMatchingPage.cityStateZipText.getText(), residenceCity + ", " + residenceState + ", " + residenceZipcode, "Expected city , state , zipCode" + residenceCity + ", " + residenceState + ", " + residenceZipcode + "does not match actual: " + memberMatchingPage.cityStateZipText.getText());
                        break;
                }
            }

        } else  if (type.equalsIgnoreCase("API Created Application Details")) {
            scrollToTop();
                for (String label : values) {
                    switch (label.toUpperCase()) {
                        case "APPLICATION ID":
                            assertTrue(memberMatchingPage.applicationIDText.getText().contains(APIATSApplicationController.applicationIdAPI.get()), "Expected application ID" + APIATSApplicationController.applicationIdAPI.get() + "does not match actual: " + memberMatchingPage.applicationIDText.getText());
                            break;
                        case "APPLICATION STATUS":
                            assertEquals(memberMatchingPage.applicationStatusText.getText(), "TARGETS UNIDENTIFIED", "Expected application status TARGETS UNIDENTIFIED does not match actual: " + memberMatchingPage.applicationStatusText.getText());
                            break;
                        case "ADDRESS LINE 1 AND 2":
                            assertEquals(memberMatchingPage.addressLineOneTwo.getText(), "123 Auto Created" + ", " + "Suite 2", "Expected address line " + "123 Auto Created" + ", " + "Suite 2" + "does not match actual: " + memberMatchingPage.addressLineOneTwo.getText());
                            break;
                        case "CITY STATE ZIP":
                            assertEquals(memberMatchingPage.cityStateZipText.getText(), "Houston" + ", " + "Texas" + ", " + "77055", "Expected city , state , zipCode" + "Houston" + ", " + "Texas" + ", " + "77055" + "does not match actual: " + memberMatchingPage.cityStateZipText.getText());
                            break;
                    }
                }



        } else if (type.equalsIgnoreCase("PI Details")) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "FULL NAME":
                        String piFullNameString = piFirstName + " " + piMiddleInitial + " " + piLastName + " " + piSuffix;
                        assertTrue(memberMatchingPage.firstConsumerDetails.get(1).getText().substring(6).contains(piFullNameString), "Expected full name " + piFullNameString + "does not match actual: " + memberMatchingPage.firstConsumerDetails.get(1).getText());
                        break;
                    case "SSN":
                        String ssnWithoutDash = memberMatchingPage.firstConsumerDetails.get(2).getText().replace("-", "");
                        assertEquals(ssnWithoutDash, piSSN, "Expected Applicant SSN" + " " + "does not match actual: " + memberMatchingPage.firstConsumerDetails.get(2).getText());
                        break;
                    case "DOB":
                        assertEquals(memberMatchingPage.firstConsumerDetails.get(3).getText(), piDOB, "Expected Applicant DOB" + " " + "does not match actual: " + memberMatchingPage.firstConsumerDetails.get(3).getText());
                        break;
                }
            }
        } else if (type.equalsIgnoreCase("Application Member Details")) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "FULL NAME":
                        String appMemberFullName = firstName.get() + " " + mi.get() + " " + lastName.get() + " " + suffix.get();
                        assertEquals(memberMatchingPage.secondConsumerDetails.get(1).getText(), appMemberFullName, "Expected full name " + appMemberFullName + "does not match actual: " + memberMatchingPage.secondConsumerDetails.get(1).getText());
                        break;
                    case "SSN":
                        String ssnWithoutDash = memberMatchingPage.secondConsumerDetails.get(2).getText().replace("-", "");
                        assertEquals(ssnWithoutDash, ssn.get(), "Expected Applicant SSN" + " " + "does not match actual: " + memberMatchingPage.secondConsumerDetails.get(2).getText());
                        break;
                    case "DOB":
                        assertEquals(memberMatchingPage.secondConsumerDetails.get(3).getText(), dob.get(), "Expected Applicant DOB" + " " + "does not match actual: " + memberMatchingPage.secondConsumerDetails.get(3).getText());
                        break;
                }
            }
        } else if ("MEMBER MATCHING".equals(type)) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "NAME":
                        for (int i = 2; i < 5; i++) {
                            assertEquals(memberMatchingPage.firstRowFullNameList.get(i).getText(), memberMatchingPage.firstRowFullNameList.get(1).getText());
                            assertTrue(memberMatchingPage.firstRowFullNameList.get(i).getText().contains("person"));
                        }
                        break;
                    case "SSN":
                        for (int i = 2; i < 5; i++)
                            assertEquals(memberMatchingPage.firstRowSSNlist.get(i).getText(), memberMatchingPage.firstRowSSNlist.get(1).getText());
                        break;
                    case "APPLICANT STATUS":
                        // Expected status "ENTERING DATA" changed to another "SUBMITTED" due to new functionality
                        List<String> expectedAppStatusList = new ArrayList<>(Arrays.asList("SUBMITTED", "TARGETS UNIDENTIFIED", "DETERMINING"));
                        List<String> actualAppStatusList = new ArrayList<>();
                        for (int i = 1; i < 4; i++) {
                            actualAppStatusList.add(memberMatchingPage.firstRowAppStatusList.get(i).getText());
                        }
                        Collections.sort(expectedAppStatusList);
                        Collections.sort(actualAppStatusList);
                        assertEquals(actualAppStatusList, expectedAppStatusList, "Mismatch in the List of Application Status");
                        break;
                    case "ADDRESS LINE ONE":
                        for (int i = 2; i < 5; i++)
                            assertEquals(memberMatchingPage.listAddressLineOne.get(i).getText(), memberMatchingPage.listAddressLineOne.get(1).getText());
                        break;
                    case "ADDRESS LINE TWO":
                        for (int i = 1; i < 4; i++)
                            assertEquals(memberMatchingPage.listAddressLineTwo.get(i).getText(), memberMatchingPage.listAddressLineTwo.get(0).getText());
                        break;
                    case "APP ID":
                        List<String> expectedAppIdList = new ArrayList<>();
                        List<String> actualAppIdList = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            expectedAppIdList.add(saveSubmit.get().getMemberMatchingAppIdForUI().get(i));
                        }
                        memberMatchingPage.potentialListOfMatchingAppId.forEach(each -> actualAppIdList.add(each.getText()));
                        Collections.sort(actualAppIdList);
                        Collections.sort(expectedAppIdList);
                        assertEquals(actualAppIdList, expectedAppIdList);
                        break;
                    case "LTC PROGRAMS":
                        for (int i = 1; i < 5; i++) {
                            assertEquals(memberMatchingPage.firstRowProgramsList.get(i).getText(), "HCBS", "Expected list of Programs HCBS for LTC Applications");
                        }
                        break;
                    case "MA PROGRAMS":
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(2).getText().replace("fiber_manual_record", ""), "Medicaid", "Mismatch in expected programs");
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(3).getText().replace("fiber_manual_record", ""), "CHIP, Medicaid, Pregnancy Assistance", "Mismatch in expected programs");
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(4).getText().replace("fiber_manual_record", ""), "CHIP, Medicaid", "Mismatch in expected programs");
                        break;
                }
            }
        } else if ("MEMBER MATCHING from API".equals(type)) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "NAME":
                        for (int i = 2; i < 5; i++) {
                            assertEquals(memberMatchingPage.firstRowFullNameList.get(i).getText(), memberMatchingPage.firstRowFullNameList.get(1).getText());
                            assertTrue(memberMatchingPage.firstRowFullNameList.get(i).getText().contains("person"));
                        }
                        break;
                    case "SSN":
                        for (int i = 2; i < 5; i++)
                            assertEquals(memberMatchingPage.firstRowSSNlist.get(i).getText(), memberMatchingPage.firstRowSSNlist.get(1).getText());
                        break;
                    case "APPLICANT STATUS":
                        // Expected status "ENTERING DATA" changed to another "SUBMITTED" due to new functionality
                        List<String> expectedAppStatusList = new ArrayList<>(Arrays.asList("SUBMITTED", "TARGETS UNIDENTIFIED", "DETERMINING"));
                        List<String> actualAppStatusList = new ArrayList<>();
                        for (int i = 1; i < 4; i++) {
                            actualAppStatusList.add(memberMatchingPage.firstRowAppStatusList.get(i).getText());
                        }
                        Collections.sort(expectedAppStatusList);
                        Collections.sort(actualAppStatusList);
                        assertEquals(actualAppStatusList, expectedAppStatusList, "Mismatch in the List of Application Status");
                        break;
                    case "ADDRESS LINE ONE":
                        for (int i = 2; i < 5; i++)
                            assertEquals(memberMatchingPage.listAddressLineOne.get(i).getText(), memberMatchingPage.listAddressLineOne.get(1).getText());
                        break;
                    case "ADDRESS LINE TWO":
                        for (int i = 1; i < 4; i++)
                            assertEquals(memberMatchingPage.listAddressLineTwo.get(i).getText(), memberMatchingPage.listAddressLineTwo.get(0).getText());
                        break;
                    case "APP ID":
                        List<String> expectedAppIdList = new ArrayList<>();
                        List<String> actualAppIdList = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            expectedAppIdList.add(saveSubmit.get().getMemberMatchingAppIdForUI().get(i));
                        }
                        memberMatchingPage.potentialListOfMatchingAppId.forEach(each -> actualAppIdList.add(each.getText()));
                        Collections.sort(actualAppIdList);
                        Collections.sort(expectedAppIdList);
                        assertEquals(actualAppIdList, expectedAppIdList);
                        break;
                    case "LTC PROGRAMS":
                        for (int i = 1; i < 5; i++) {
                            assertEquals(memberMatchingPage.firstRowProgramsList.get(i).getText(), "HCBS", "Expected list of Programs HCBS for LTC Applications");
                        }
                        break;
                    case "MA PROGRAMS":
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(2).getText().replace("fiber_manual_record", ""), "Medicaid", "Mismatch in expected programs");
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(3).getText().replace("fiber_manual_record", ""), "CHIP, Medicaid, Pregnancy Assistance", "Mismatch in expected programs");
                        assertEquals(memberMatchingPage.firstRowProgramsList.get(4).getText().replace("fiber_manual_record", ""), "CHIP, Medicaid", "Mismatch in expected programs");
                        break;
                }
            }
        }

        else  if (type.equalsIgnoreCase("Member Matching Research")) {
            for (String label : values) {
                switch (label.toUpperCase()) {
                    case "APPLICATION ID":
                        assertTrue(memberMatchingPage.applicationIDText.getText().contains(saveSubmit.get().applicationIdAPI.get()), "Expected application ID" + saveSubmit.get().applicationIdAPI + "does not match actual: " + memberMatchingPage.applicationIDText.getText());
                        break;
                    case "APPLICATION STATUS":
                        assertEquals(memberMatchingPage.applicationStatusText.getText(), "SUBMITTED", "Expected application status SUBMITTED does not match actual: " + memberMatchingPage.applicationStatusText.getText());
                        break;
                    case "ADDRESS LINE 1 AND 2":
                        assertEquals(memberMatchingPage.addressLineOneTwo.getText(), "9 Metro Ave, 2nd apt", "Expected address line " + residenceAddressLineOne + ", " + residenceAddressLineTwo + "does not match actual: " + memberMatchingPage.addressLineOneTwo.getText());
                        break;
                    case "CITY STATE ZIP":
                        assertEquals(memberMatchingPage.cityStateZipText.getText(),  "Herndon, Virginia, 20171", "Expected city , state , zipCode" + residenceCity + ", " + residenceState + ", " + residenceZipcode + "does not match actual: " + memberMatchingPage.cityStateZipText.getText());
                        break;
                    case "APPLICANT ID":
                        assertEquals(getKeyFromValue(createdConsumerDetails.get(), "Primary Individual"),memberMatchingPage.firstConsumerDetails.get(0).getText(),"Application id isnt verified");
                        break;
                    case "FULL NAME":
                        assertTrue(memberMatchingPage.firstConsumerDetails.get(1).getText().contains(saveSubmit.get().piFirstName+" "+saveSubmit.get().piLastName),"Full name isnt verified");
                        break;
                    case "SSN":
                        String ssnWithoutDash = memberMatchingPage.firstConsumerDetails.get(2).getText().replace("-", "");
                        assertEquals(ssnWithoutDash, saveSubmit.get().piSSN, "SSN isnt verified");
                        break;
                    case "DOB":
                        assertEquals(BrowserUtils.convertMMddyyyyToyyyyMMdd(memberMatchingPage.firstConsumerDetails.get(3).getText()), saveSubmit.get().piDOB, "DOB isnt verified");
                        break;
                    case "PROGRAMS":
                        assertEquals(memberMatchingPage.firstConsumerDetails.get(4).getText(),"CHIP, Medicaid","Programs arent verified");
                        break;
                }
            }
        }
    }

    @Then("I verify {string} details displayed as {string} on Member Matching Page")
    public void iVerifyDetailsDisplayedAsOnMemberMatchingPage(String verifyLabel, String expectedNullValue) {
        waitFor(5);
        switch (verifyLabel.toUpperCase()) {
            case "ADDRESS":
                assertEquals(memberMatchingPage.addressLineOneTwo.getText(), expectedNullValue, "Expected Address Line one and two" + expectedNullValue + " does not match actual: " + memberMatchingPage.addressLineOneTwo.getText());
                assertEquals(memberMatchingPage.cityStateZipText.getText(), expectedNullValue, "Expected City ,State ,Zip" + expectedNullValue + " does not match actual: " + memberMatchingPage.cityStateZipText.getText());
                break;
            case "PI":
                memberMatchingPage.firstConsumerDetails = Driver.getDriver().findElements(memberMatchingPage.firstConsumerDetailsBy);
                assertEquals(memberMatchingPage.firstConsumerDetails.get(2).getText(), expectedNullValue, "Expected SSN" + expectedNullValue + " does not match actual: " + memberMatchingPage.firstConsumerDetails.get(2).getText());
                // DoB assert "-- --" commented out due to new functionality. CP-28809
                // assertEquals(memberMatchingPage.firstConsumerDetails.get(3).getText(), expectedNullValue, "Expected DOB" + expectedNullValue + " does not match actual: " + memberMatchingPage.firstConsumerDetails.get(3).getText());
                assertEquals(memberMatchingPage.firstConsumerDetails.get(4).getText(), expectedNullValue, "Expected Program(s)" + expectedNullValue + " does not match actual: " + memberMatchingPage.firstConsumerDetails.get(4).getText());
                break;
            case "APPLICATION MEMBER":
                memberMatchingPage.secondConsumerDetails = Driver.getDriver().findElements(memberMatchingPage.secondConsumerDetailsBy);
                assertEquals(memberMatchingPage.secondConsumerDetails.get(2).getText(), expectedNullValue, "Expected SSN" + expectedNullValue + " does not match actual: " + memberMatchingPage.secondConsumerDetails.get(2).getText());
                // DoB assert "-- --" commented out due to new functionality. CP-28809
                // assertEquals(memberMatchingPage.secondConsumerDetails.get(3).getText(), expectedNullValue, "Expected DOB" + expectedNullValue + " does not match actual: " + memberMatchingPage.secondConsumerDetails.get(3).getText());
                assertEquals(memberMatchingPage.secondConsumerDetails.get(4).getText(), expectedNullValue, "Expected Program(s)" + expectedNullValue + " does not match actual: " + memberMatchingPage.secondConsumerDetails.get(4).getText());
                break;
        }
    }

    @Then("I verify following Program\\(s) displayed in Alphabetical order on the Member Matching Consumer Details for {string}")
    public void i_verify_following_Program_s_displayed_in_Alphabetical_order_on_the_Member_Matching_Consumer_Details_for
            (String memberType, List<String> programList) {
        Set<String> sortedProgramList = new TreeSet<>(programList); // Store programs to the TreeSet to get ascending order
        String sortedStringPrograms = "";
        for (String program : sortedProgramList) {
            sortedStringPrograms += program + ", "; // Store each program in order in the string
        }
        sortedStringPrograms = sortedStringPrograms.substring(0, sortedStringPrograms.length() - 2);//Substring the last program's ", "
        if (memberType.equalsIgnoreCase("PI"))
            assertEquals(memberMatchingPage.firstConsumerDetails.get(4).getText(), sortedStringPrograms, "Expected program(s)" + sortedStringPrograms + " does not match actual: " + memberMatchingPage.firstConsumerDetails.get(4).getText());
        else if (memberType.equalsIgnoreCase("Application Member"))
            assertEquals(memberMatchingPage.secondConsumerDetails.get(4).getText(), sortedStringPrograms, "Expected program(s)" + sortedStringPrograms + " does not match actual: " + memberMatchingPage.firstConsumerDetails.get(4).getText());
    }

    @Then("I verify the New button is enabled for inbound application in Member Matching page")
    public void iVerifyTheNewButtonIsEnabledForInboundApplicationInMemberMatchingPage() {
        assertTrue(memberMatchingPage.newButton.isEnabled(), "NEW button is not enabled in member matching page");
    }

    @And("I verify Duplicate button is disabled for inbound application in Member Matching page")
    public void iVerifyDuplicateButtonIsDisabledForInboundApplicationInMemberMatchingPage() {
        assertTrue(memberMatchingPage.duplicateButton.getAttribute("class").endsWith("disabled"), "Duplicate button is enabled in member matching page");
    }

    @And("I verify Duplicate button is enabled for inbound application in Member Matching page")
    public void iVerifyDuplicateButtonIsEnabledForInboundApplicationInMemberMatchingPage() {
        assertTrue(memberMatchingPage.duplicateButton.isEnabled(), "Duplicate button is disabled in member matching page");
    }

    @When("I click on the New button in the Application Member Matching Page")
    public void iClickOnTheNewButtonInTheApplicationMemberMatchingPage() {
        waitFor(5);
        waitForClickablility(memberMatchingPage.newButton, 10);
        memberMatchingPage.newButton.click();
        waitFor(5);
    }

    @And("I click on the {string} select box for matching application in Member Matching page")
    public void iClickOnTheGivenSelectBoxForMatchingApplicationInMemberMatchingPage(String index) {
        waitFor(4);
        scrollToTop();
        scrollToElement(memberMatchingPage.matchingSelectBox.get(0));
        highLightElement(memberMatchingPage.matchingSelectBox.get(0));
        scrollToElement(memberMatchingPage.applicationIDText);
        jsClick(memberMatchingPage.matchingSelectBox.get(Integer.parseInt(index)));
      //  memberMatchingPage.matchingSelectBox.get(Integer.parseInt(index)).click();
    }

    @Then("I verify number of Potential Application selection is {string}")
    public void iVerifySelectionIsRestrictedToOnlyOnePotentialMatchApplication(String selection) {
        int count = 0;
        for (WebElement ele : memberMatchingPage.matchingSelectBox) {
            if (ele.isSelected()) count++;
        }
        System.out.println(count);
        assertTrue(count == Integer.parseInt(selection), "Selection is not same as expected");
    }

    @And("I click on the Duplicate button on Member Matching page & Continue")
    public void iClickOnTheDuplicateButtonOnMemberMatchingPageContinue() {
        waitFor(3);
        memberMatchingPage.duplicateButton.click();
        waitFor(1);
        memberMatchingPage.duplicateButtonContinue.click();
        waitFor(1);
    }

    @And("I click on Cancel button on Duplicate Application Warning Message")
    public void iClickOnTheDuplicateButtonOnMemberMatchingPageCancel() {
        waitFor(3);
        memberMatchingPage.duplicateButtonCancel.click();
        waitFor(1);
    }

    @And("I click on the Duplicate button on Member Matching page")
    public void iClickOnTheDuplicateButtonOnMemberMatchingPage() {
        waitFor(3);
        memberMatchingPage.duplicateButton.click();
        waitFor(1);
    }


    @Then("I verify I see the Primary Individual Indicator for the associated Primary Individual Record")
    public void i_verify_I_see_the_Primary_Individual_Indicator_for_the_associated_Primary_Individual_Record() {
        assertTrue(memberMatchingPage.firstConsumerDetails.get(1).getText().startsWith(memberMatchingPage.piIcon.getText()), "Expected PI Icon name" + piFirstName + " " + piMiddleInitial + " " + piLastName + " does not match actual: " + memberMatchingPage.piIcon.getText());
    }


    @And("I verify Application Members sorted on Member Matching Page as expected")
    public void iVerifyApplicationMembersSortedOnMemberMatchingPageAsExpected() {
        assertTrue(memberMatchingPage.firstConsumerDetails.get(1).getText().startsWith(memberMatchingPage.piIcon.getText()), "Application Members not sorted as  expected , Primary Individual is not first in order");
    }

    @Then("I verify warning message displayed in Member Matching Page when application is marked Duplicate")
    public void iVerifyWarningMessageDisplayedInMemberMatchingPage() {
        waitFor(2);
        assertTrue(memberMatchingPage.duplicateApplicationWarningMessage.isDisplayed(), "Duplicate Warning message is not displayed or is incorrect ");
    }

    @Then("the Applications will be displayed in order of the Score highest to lowest left to right on the UI")
    public void theApplicationsWillBeDisplayedInOrderOfTheScoreHighestToLowestLeftToRightOnTheUI() {
        waitFor(2);
        List<String> expectedAppList = new ArrayList<>(new LinkedHashSet<>(saveSubmit.get().getMemberMatchingAppIdForUI()));
        Collections.reverse(expectedAppList);
        expectedAppList.remove(0);
        List<String> actualOrderedAppList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            waitForVisibility(memberMatchingPage.potentialListOfMatchingAppId.get(i), 10);
        }
        for (WebElement each : memberMatchingPage.potentialListOfMatchingAppId) {
            actualOrderedAppList.add(each.getText());
        }
        System.out.println("Actual Ordered App List = " + actualOrderedAppList);
        System.out.println("Expected order App List = " + expectedAppList);
        assertEquals(actualOrderedAppList, expectedAppList, "Mis match in Member Matching Application Id list");
    }

    @Then("the Applications with the SAME score will be displayed based on the Application ID in Chronological Order from left to right")
    public void theApplicationsWithTheSAMEScoreWillBeDisplayedBasedOnTheApplicationIDInChronologicalOrderFromLeftToRight() {
        waitFor(2);
        List<String> expectedAppList = new ArrayList<>();
        for (int i = 3; i <= 5; i++) {
            expectedAppList.add(saveSubmit.get().getMemberMatchingAppIdForUI().get(i));
        }
        for (int i = 0; i <= 2; i++) {
            expectedAppList.add(saveSubmit.get().getMemberMatchingAppIdForUI().get(i));
        }
        for (int i = 0; i < 4; i++) {
            waitForVisibility(memberMatchingPage.potentialListOfMatchingAppId.get(i), 10);
        }
        List<String> actualOrderedAppList = new LinkedList<>();
        for (WebElement each : memberMatchingPage.potentialListOfMatchingAppId) {
            actualOrderedAppList.add(each.getText());
        }
        System.out.println("Actual Ordered App List = " + actualOrderedAppList);
        System.out.println("Expected order App List = " + expectedAppList);
        assertEquals(actualOrderedAppList, expectedAppList, "Mis match in Member Matching Application Id list");
    }

    @Then("I verify {string} is displayed in member matching page in Application")
    public void iVerifyIsDisplayedInMemberMatchingPageInApplication(String displayText) {
        waitForVisibility(memberMatchingPage.noMatchesFoundMsg, 10);
        assertEquals(memberMatchingPage.noMatchesFoundMsg.getText(), displayText);
    }

    @Then("I see non matching consumers listed in chronological order after the matching consumer")
    public void iSeeNonMatchingConsumersListedInChronologicalOrderAfterTheMatchingConsumer() {
        long secondApplicantId = Long.parseLong(memberMatchingPage.secondRowApplicantIdList.get(2).getText());
        long thirdApplicantId = Long.parseLong(memberMatchingPage.thirdRowApplicantIdList.get(2).getText());
        long fourthApplicantId = Long.parseLong(memberMatchingPage.fourthRowApplicantIdList.get(2).getText());
        assertTrue(secondApplicantId < thirdApplicantId && thirdApplicantId < fourthApplicantId, "Found Applicant Id Order in non chronological order");
    }

    @Then("I identify Data that has not matched for each Application Consumer is marked by red text and a symbol")
    public void iIdentifyDataThatHasNotMatchedForEachApplicationConsumerIsMarkedByRedTextAndASymbol() {
        waitForVisibility(memberMatchingPage.firstRowFullNameList.get(2), 10);
        waitForVisibility(memberMatchingPage.firstRowFullNameList.get(3), 10);
        // First Application mismatch for symbol and red text for name dob and programs
        String appOnePIFullName = memberMatchingPage.firstRowFullNameList.get(2).getText();
        assertTrue(appOnePIFullName.contains("fiber_manual_recordperson"), "Expected mismatch symbol but found different");
        appOnePIFullName = appOnePIFullName.replace("fiber_manual_recordperson", "").replaceAll(" ", "");
        assertEquals(appOnePIFullName.length(), 64, "Mis match in expected Full name length");
        assertTrue(memberMatchingPage.firstRowFullNameList.get(2).getAttribute("class").contains("red"), "Expected mismatch red text but found different");
        assertTrue(memberMatchingPage.firstRowDOBlist.get(2).getText().contains("fiber_manual_record"), "Expected mismatch symbol but found different");
        assertTrue(memberMatchingPage.firstRowDOBlist.get(2).getAttribute("class").contains("red"), "Expected mismatch red text but found different");
        assertTrue(memberMatchingPage.firstRowProgramsList.get(2).getText().contains("fiber_manual_record"), "Expected mismatch symbol but found different");
        assertTrue(memberMatchingPage.firstRowProgramsList.get(2).getAttribute("class").contains("red"), "Expected mismatch red text but found different");
        // Second Application mismatch for symbol and red text for ssn and programs
        assertTrue(memberMatchingPage.firstRowSSNlist.get(3).getText().contains("fiber_manual_record"), "Expected mismatch symbol but found different");
        assertTrue(memberMatchingPage.firstRowSSNlist.get(3).getAttribute("class").contains("red"), "Expected mismatch red text but found different");
        assertTrue(memberMatchingPage.firstRowProgramsList.get(3).getText().contains("fiber_manual_record"), "Expected mismatch symbol but found different");
        assertTrue(memberMatchingPage.firstRowProgramsList.get(3).getAttribute("class").contains("red"), "Expected mismatch red text but found different");
    }

    @Then("I verify existing applicant potential matches when {string} matches")
    public void iVerifyExistingApplicantPotentialMatchesWhenMatches(String matchType) {
        waitForVisibility(memberMatchingPage.potentialMatchAppColumnOne.get(1), 10);
        // inbound duplicate match data
        String inbFullName = memberMatchingPage.firstRowFullNameList.get(1).getText().replace("person", "");
        String inbDOB = memberMatchingPage.firstRowDOBlist.get(1).getText();
        String inbssn = memberMatchingPage.firstRowSSNlist.get(1).getText();
        String doubleDash = "-- --";
        String mismatchIcon = "fiber_manual_record";
        waitFor(5);
        switch (matchType) {
            case "ONE INBOUND CONSUMER":
                //consumer one match all criteria for 100 match pts
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(1).getText().replace("person", ""), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(2).getText(), inbssn, "Mismatch in expected SSN");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(3).getText(), inbDOB, "Mismatch in expected DOB");
                //consumer two match first name, last name, and ssn criteria for 90 match pts
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(6).getText(), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(7).getText(), inbssn, "Mismatch in expected SSN");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(8).getText(), mismatchIcon + doubleDash, "Mismatch in expected DOB");
                //consumer three match first name, last name, and dob criteria for 50 match pts
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(11).getText(), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(12).getText(), mismatchIcon + doubleDash, "Mismatch in expected SSN");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(13).getText(), inbDOB, "Mismatch in expected DOB");
                //consumer four match first name and last name criteria for 40 match pts
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(16).getText(), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(17).getText(), mismatchIcon + doubleDash, "Mismatch in expected SSN");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(18).getText(), mismatchIcon + doubleDash, "Mismatch in expected DOB");
                break;
            case "SINGLE EXISTING MATCH TWO":
                // Inb Application Member verification
                assertEquals(memberMatchingPage.inbApplicationColumnList.get(7).getText().replace("person", ""), inbFullName, "Mismatch in expected Full Name");
                assertTrue(memberMatchingPage.inbApplicationColumnList.get(6).getText() != memberMatchingPage.inbApplicationColumnList.get(1).getText(), "Expected different APPLICANT ID but found same APPLICANT ID");
                assertEquals(memberMatchingPage.inbApplicationColumnList.get(8).getText(), doubleDash, "Mismatch in expected SSN");
                // Existing member gets duplicated for inline match to inbound verfication
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(0).getText(), memberMatchingPage.potentialMatchAppColumnOne.get(5).getText(), "Mismatch in expected APPLICANT ID");
                //Potential Matches the Inbound Consumer data
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(1).getText().replace("person", ""), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(6).getText().replace("person", ""), inbFullName, "Mismatch in expected Full Name");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(3).getText(), inbDOB, "Mismatch in expected DOB");
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(8).getText(), inbDOB, "Mismatch in expected DOB");
                break;
            case "SAME SCORE APPLICANTS":
                //Existing Applicant one
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(1).getText().replace("person", ""), inbFullName, "Mismatch in expected Full Name");
                //Existing Applicant two
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(6).getText(), inbFullName, "Mismatch in expected Full Name");
                //Existing Applicant three
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(11).getText(), inbFullName, "Mismatch in expected Full Name");
                //Existing Applicant four
                assertEquals(memberMatchingPage.potentialMatchAppColumnOne.get(16).getText(), inbFullName, "Mismatch in expected Full Name");
                //Applicant ID in chronological order
                assertTrue(Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(0).getText()) < Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(5).getText()), "Unexpected chronological order for Applicant ID");
                assertTrue(Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(5).getText()) < Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(10).getText()), "Unexpected chronological order for Applicant ID");
                assertTrue(Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(10).getText()) < Long.parseLong(memberMatchingPage.potentialMatchAppColumnOne.get(15).getText()), "Unexpected chronological order for Applicant ID");
                break;
            default:
                fail("Unexpected value for matchNum");
        }
    }

    @Then("I store linked application id and status from {string} row")
    public void i_store_linked_application_id_and_status_from_row(String index) {
        linkedAppID.set(memberMatchingPage.potentialListOfMatchingAppId.get(Integer.parseInt(index)).getText());
        linkedAppStatus.set(memberMatchingPage.firstRowAppStatusList.get((Integer.parseInt(index))+1).getText());
    }

    @Then("I click on the Research button in the Application Member Matching Page")
    public void iClickOnTheResearchButtonInTheApplicationMemberMatchingPage() {
        waitFor(5);
        memberMatchingPage.researchButton.click();
    }

    @And("I select following reason for sending application to Research")
    public void iSelectFollowingReasonForSendingApplicationToResearch(List<String> reasonList) {
        waitFor(1);
        researchReason.set("");
        for (int i = 0; i < reasonList.size(); i++) {
            selectOptionFromMultiSelectDropDown(memberMatchingPage.researchReasons,reasonList.get(i));
            if(i==0)
                researchReason.set(researchReason.get()+reasonList.get(i));
            else
                researchReason.set(researchReason.get()+","+reasonList.get(i));
        }
        waitFor(1);
    }

    @And("I click save for sending application to Research")
    public void iClickSaveForSendingApplicationToResearch() {
        memberMatchingPage.sendToResearchSaveButton.click();
        waitFor(2);
    }

    @And("I enter notes as {string} Characters for Send Application For Research Panel")
    public void iEnterNotesAsCharactersForSendApplicationForResearchPanel(String notes) {
        researchReasonNotes.set("");
        switch (notes.toUpperCase()){
            case "RANDOM 250":
                researchReasonNotes.set(generateRandomSpecialrChars(250));
                memberMatchingPage.sendToResearchNotes.sendKeys(researchReasonNotes.get());
                break;
            case "":
                //Resetting static variable
                break;
            default:
                researchReasonNotes.set(notes);
                memberMatchingPage.sendToResearchNotes.sendKeys(notes);
        }
    }

    @And("I verify Back Arrow warning warning message displayed on Member Matching Page")
    public void iVerifyBackArrowWarningWarningMessageDisplayedOnMemberMatchingPage() {
        waitForVisibility(memberMatchingPage.backArrowWarningMessage,3);
        Assert.assertTrue(memberMatchingPage.backArrowWarningMessage.isDisplayed(),"Back Arrow Warning Message is not displayed");
    }

    @And("I click {string} button inside the warning message on Member Matching Page")
    public void iClickButtonInsideTheWarningMessageOnMemberMatchingPage(String button) {
        waitForVisibility(memberMatchingPage.warningMessageCancelButton,3);
        switch (button.toUpperCase()) {
            case "CANCEL":
                memberMatchingPage.warningMessageCancelButton.click();
                break;
            case "CONTINUE":
                memberMatchingPage.warningMessageContinueButton.click();
                break;
            default:
                fail("Button type didn't match");
        }
    }

    @And("I click cancel button for Send Application For Research Panel")
    public void iClickCancelButtonForSendApplicationForResearchPanel() {
        memberMatchingPage.sendToResearchCancelButton.click();
    }

    @And("I verify the Reason is required message is display on Member Matching Page")
    public void iVerifyTheReasonIsRequiredMessageIsDisplayOnMemberMatchingPage() {
        Assert.assertTrue(memberMatchingPage.reasonRequiredMessage.isDisplayed(),"REASON is required and cannot be left blank message is not displayed");
    }

    @Then("I verify Research button is disabled for inbound application in Member Matching page")
    public void iVerifyResearchButtonIsDisabledForInboundApplicationInMemberMatchingPage() {
        assertTrue(memberMatchingPage.researchButton.getAttribute("class").endsWith("disabled"), "Duplicate button is enabled in member matching page");
    }

    @When("I click on the {string} button on Member Matching")
    public void iClickOnTheButtonOnMemberMatching(String buttonType) {
        switch (buttonType.toUpperCase()) {
            case "LINK":
                waitForClickablility(memberMatchingPage.linkButton,10);
                memberMatchingPage.linkButton.click();
                break;
            case "NEW CASE":
                waitForClickablility(memberMatchingPage.newCaseButton,10);
                memberMatchingPage.newCaseButton.click();
                break;
            case "SAVE_ACTION":
                waitForClickablility(memberMatchingPage.actionsSaveButton,10);
                memberMatchingPage.actionsSaveButton.click();
                waitFor(3);
                break;
            default:
                fail("Button Type didn't match");
        }
    }

    public void verifySummarySectionForApplicationMember(String memberID, String actionType) {
        switch (actionType) {
            case "CREATE_NEW":
                System.out.println("ID list" + memberMatchingPage.createNewConsumerProfileIDList);
                List<String> createNewIDTextList = new ArrayList<>();
                for (WebElement createNewConsumerProfileID : memberMatchingPage.createNewConsumerProfileIDList) {
                    createNewIDTextList.add(createNewConsumerProfileID.getText());
                }
                Assert.assertTrue(createNewIDTextList.contains(memberID), "MemberID couldn't find in the summary section for " + actionType);
                break;
            case "CREATE_NEW_AND_LINK":
                assertTrue(memberMatchingPage.createNewConsumerProfileIDList.get(0).getText().equals(memberID),"Consumer id didnt match");
                assertTrue(("person"+memberMatchingPage.linkToCaseIDList.get(0).getText()).contains(memberMatchingPage.newCaseIDValue.getText()),"Case id didnt match");
                break;
            case "ADD_EXISTING":
                //will be implemented for future stories
                break;
            case "LINK CONSUMER":
                assertTrue(memberMatchingPage.linkConsumerIDList.get(0).getText().equals(memberID),"Consumer not on a case id dint match");
                assertTrue(("person"+memberMatchingPage.toCaseIDlist.get(1).getText()).equals(memberMatchingPage.newCaseIDValue.getText()),"Case id for consumer not on a case didnt match");
                break;
            case "LINK CONSUMER to NEW CASE 1":
                assertTrue(memberMatchingPage.linkConsumerIDList.get(0).getText().equals(memberID),"Consumer not on a case id dint match");
                break;
            case "LINK CONSUMER to NEW CASE 2":
                assertTrue(memberMatchingPage.linkConsumerIDList.get(1).getText().equals(memberID),"Consumer not on a case id dint match");
                break;
            case "CREATE_NEW_AND_LINK_TO_NEW":
                assertTrue(memberMatchingPage.createNewConsumerProfileIDList.get(0).getText().equals(memberID),"Consumer id didnt match");
                break;
            default:
                fail("Action Type didn't match");
        }
    }

    @And("I verify summary section of {string} for {string} action")
    public void iVerifySummarySectionOfForAction(String role, String actionType) {
        String consumerID;
        switch (role.toUpperCase()) {
            case "PRIMARY INDIVIDUAL":
                consumerID = apiatsConsumersController.get().getAppConsumerIDByRole("Primary Individual");
                verifySummarySectionForApplicationMember(consumerID, actionType);
                break;
            case "APPLICATION MEMBER":
                consumerID = apiatsConsumersController.get().getAppConsumerIDByRole("Application Member");
                System.out.println("Consumer ids for application member role: "+consumerID);
                verifySummarySectionForApplicationMember(consumerID, actionType);
                break;
            case "AUTHORIZED REPRESENTATIVE":
                consumerID = apiatsConsumersController.get().getAppConsumerIDByRole("Authorized Rep");
                verifySummarySectionForApplicationMember(consumerID, actionType);
                break;
            case "APPLICATION MEMBER 1":
                consumerID=appMemberOneID.get();
                verifySummarySectionForApplicationMember(consumerID,actionType);
                break;
            case "CONS NOT ON A CASE":
                consumerID=consumerNotOnACaseID.get();
                verifySummarySectionForApplicationMember(consumerID,actionType);
                break;
            case "ONLY CASE MEMBER":
                consumerID=onlyCaseMemberID.get();
                verifySummarySectionForApplicationMember(consumerID,actionType);
                break;
            case "APPLICATION":
                if(actionType.equalsIgnoreCase("LINK")){
                    memberMatchingPage.linkIncomingApplication.isDisplayed();
                    Assert.assertEquals(memberMatchingPage.linkIncomingApplicationApplicationID.getText(), saveSubmit.get().applicationIdAPI, "Application ID mismatched for summary Section");
                }
            case "CASE MEMBER 1":
                //will be implemented for future stories
                break;
            default:
                fail("Role Type didn't match");
        }
    }

    @And("I verify summary section of {string} with following values")
    public void iVerifySummarySectionOfWithFollowingValues(String fieldName , Map<String,String> data) {
        waitFor(5);
        String pIFullName = "";

        if(duplicateFirstname!=null){
            pIFullName = duplicateFirstname + " " + duplicateLastName;
        }
        else{
            pIFullName = piFirstName + " " + piLastName;
        }

        switch (fieldName.toUpperCase()) {
            case "MATCHED MEMBERS":
                for (String role : data.keySet()) {
                    if (role.equalsIgnoreCase("PRIMARY INDIVIDUAL")) {
                        if (data.get(role).equalsIgnoreCase("NAME")) {
                            Assert.assertEquals(memberMatchingPage.matchedMemberPIDetails.get(2).getText(), "person" + pIFullName, "PI Name didn't match");
                        } else if (data.get(role).equalsIgnoreCase("NO SELECTION MADE")) {
                            Assert.assertTrue(memberMatchingPage.matchedMemberPINoSelection.isDisplayed(), "No Selection Made didn't display for AM");
                        }
                    } else if (role.equalsIgnoreCase("APPLICATION MEMBER")) {
                        if (data.get(role).equalsIgnoreCase("NO SELECTION MADE")) {
                            Assert.assertTrue(memberMatchingPage.matchedMemberAMNoSelection.isDisplayed(), "No Selection Made didn't display for AM");
                        }
                    }
                    else if (role.equalsIgnoreCase("APPLICATION MEMBER 2")) {
                        if(data.get(role).equalsIgnoreCase("NAME")){
                            Assert.assertTrue(memberMatchingPage.secondMatchedMemberAMDetails.get(2).getText().equalsIgnoreCase("person" + consumerNotOnCaseFN+" "+consumerNotOnCaseLN), "Second matched AM Name didn't match");

                        }
                    }
                }
                break;
            case "MATCHED CASE":
               Assert.assertEquals(memberMatchingPage.matchedCaseSummarySection.getText(),data.get("CASE"),"Matched Case Text didn't match");
                break;
        }
    }

    @And("I verify {string} displays {int} times in the summary section")
    public void iVerifyDisplaysTimesInTheSummarySection(String caseType, int count) {
        switch (caseType.toUpperCase()) {
            case "NEW CASE":
                Assert.assertTrue(memberMatchingPage.newCaseTextList.size() == count, "New Case didn't display " + count + " times.Displayed " + memberMatchingPage.newCaseTextList.size() + " instead.");
                break;
            case "EXISTING CASE":
                //Will be modified for future stories
                Assert.assertTrue(memberMatchingPage.existingTextList.size() == count, "New Case didn't display " + count + " times");
                break;
            default:
                fail("Case Type didn't match");
        }
    }

    @And("I verify {string} checkbox is selected for {string} on member matching page")
    public void iVerifyCheckboxIsSelectedForOnMemberMatchingPage(String checkBoxType, String roleType) {
        waitFor(2);
        String memberId;
        if (roleType.startsWith("CONSUMER")) {
            memberId = String.valueOf(applicationConsumerIdList.get().get(Integer.parseInt(roleType.substring(9))));
        } else {
            memberId = apiatsConsumersController.get().getAppConsumerIDByRole(roleType);
        }
        WebElement temp = getAddOrRemoveWebElementWithMemberID(memberId, checkBoxType);
        Assert.assertEquals(temp.getAttribute("aria-pressed"), "true", roleType + checkBoxType + " button is not selected");
    }

    @And("I verify {string} checkbox is not selected for {string} on member matching page")
    public void iVerifyCheckboxIsNotSelectedForOnMemberMatchingPage(String checkBoxType, String roleType) {
        String memberId;
        if (roleType.startsWith("CONSUMER")) {
            memberId = String.valueOf(applicationConsumerIdList.get().get(Integer.parseInt(roleType.substring(9))));
        } else {
            memberId = apiatsConsumersController.get().getAppConsumerIDByRole(roleType);
        }
        WebElement temp = getAddOrRemoveWebElementWithMemberID(memberId, checkBoxType);
        Assert.assertEquals(temp.getAttribute("aria-pressed"), "false", roleType + checkBoxType + " button is  selected");
    }

    //Use this method to get add or remove checkbox WebElement of consumers
    public WebElement getAddOrRemoveWebElementWithMemberID(String memberId, String buttonType) {
        waitFor(1);
        WebElement webElement;
        if (buttonType.equalsIgnoreCase("ADD")) {
            webElement = Driver.getDriver().findElement(By.xpath("//button[@value='addConsumer_" + memberId + "']"));
        } else {
            webElement = Driver.getDriver().findElement(By.xpath("//button[@value='deleteConsumer_" + memberId + "']"));
        }
        return webElement;
    }

    @And("I verify {string} button is disabled on member matching page")
    public void iVerifyButtonIsDisabledOnMemberMatchingPage(String button) {
        staticWait(3);
        switch (button.toUpperCase()) {
            case "LINK":
                Assert.assertEquals(memberMatchingPage.linkButton.getAttribute("tabindex"), "-1");
                break;
            case "NEW CASE":
                Assert.assertEquals(memberMatchingPage.newCaseButton.getAttribute("tabindex"), "-1");
                break;
            default:
                fail("Button Type didn't match");
        }
    }

    @And("I select {string} checkbox for {string} on member matching page")
    public void iSelectCheckboxForOnMemberMatchingPage(String checkBoxType, String roleType) {
        String memberId;
        if (roleType.startsWith("CONSUMER")) {
            memberId = String.valueOf(applicationConsumerIdList.get().get(Integer.parseInt(roleType.substring(9))));
            System.out.println("Consumer id is: "+memberId);
        }
        else if(roleType.equals("CONS NOT ON A CASE")){
            memberId =consumerNotOnACaseID.get();
            System.out.println("Consumer not on a caseId: "+consumerNotOnACaseID.get());
        }
        else if (roleType.equals("APPLICATION MEMBER 1") && memberMatchingPage.firstAMProgramDetails.getText().equals("Medicaid")){
            memberId =memberMatchingPage.firstAMMemberID.getText();
            appMemberOneID.set(memberId);
            System.out.println("App member id with Medicaid is: "+appMemberOneID.get());
        }
        else if(roleType.equals("ONLY CASE MEMBER")){
            memberId=memberMatchingPage.onlyCaseMemberID.getText();
            onlyCaseMemberID.set(memberId);
            System.out.println("Only case member id is: "+onlyCaseMemberID.get());
        }
        else {
            scrollDownUsingActions(4);
            memberId = apiatsConsumersController.get().getAppConsumerIDByRole(roleType);
        }
        WebElement temp = getAddOrRemoveWebElementWithMemberID(memberId, checkBoxType);
        if (!temp.isSelected())
            jsClick(temp);
    }

    //Can be modified to be able to handle selecting from multiple matched cases
    @And("I select the case Id from the potential matches")
    public void iSelectTheCaseIdFromThePotentialMatches() {
            waitFor(2);
            WebElement caseIdcheckbox = Driver.getDriver().findElement(By.xpath("//h5[text()='POTENTIAL MATCHES']//ancestor::tr//following-sibling::tr//td//input[@name='" + APIContactRecordController.caseID.get() + "']"));
            scrollToElement(caseIdcheckbox);
            jsClick(caseIdcheckbox);
    }

    @And("I provide matching application member details for consumers not on a case")
    public void iProvideMatchingApplicationMemberDetailsForConsumersNotOnACase() {

        clearAndFillText(createConsumer.consumerFN,consumerNotOnCaseFN.get());
        clearAndFillText(createConsumer.consumerLN, consumerNotOnCaseLN.get());
        System.out.println("First name is: " + consumerNotOnCaseFN);
        System.out.println("Last name is " + consumerNotOnCaseLN);
        waitFor(2);
        String DOBConverted=convertyyyyMMddToMMddyyyy(consumerNotOnCaseDOB.get());
        clearAndFillText(createConsumer.consumerDB, DOBConverted);
        clearAndFillText(createConsumer.consumerSSN,crmCreateConsumerProfilePage.getRandomNumber(9));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, crmCreateConsumerProfilePage.getRandomNumber(10));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        waitFor(4);
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        waitFor(2);
        clearAndFillText(createConsumer.consumerZipCode, "12345");
        waitFor(2);
        if(createConsumer.checkedOptMail.getAttribute("checked") != null)
            click(createConsumer.optInMailText);
    }

    @When("I verify the new case button visibility and click on the NEW CASE button")
    public void iClickOnTheNewCaseButtonOnMemberMatching() {
        for(int i = 0; i< memberMatchingPage.matchingConsumerId.size(); i++){
            memberMatchingPage.matchingConsumerId.get(i).click();
        }
    }

    @Then("I get consumer not on a case Id from header")
    public void IgetConsumerNotOnACaseIdFromHeader(){
        waitFor(1);
        consumerNotOnACaseID.set(contactRecordUIPage.activeContactCaseConsumerIdHeader.getText());
        System.out.println("Consumer not on a case id is: "+consumerNotOnACaseID.get());

    }

    @Then("I verify Edit icon is displayed for {string} on Member Matching Page")
    public void i_verify_Edit_icon_is_displayed_on_Member_Matching_Page(String editType) {
        staticWait(3);
        switch (editType) {
            case "Application":
                waitForVisibility(memberMatchingPage.editIconForApplication,4);
                assertTrue(memberMatchingPage.editIconForApplication.isDisplayed());
                break;
            case "Primary Individual":
                assertTrue(memberMatchingPage.editIconForPrimaryIndividual.isDisplayed());
                break;
            case "1st Application Member":
                try {
                    assertTrue(memberMatchingPage.editIconFor1stApplicationMember.isDisplayed());
                } catch (StaleElementReferenceException e) {
                    WebElement editIconFor1stApplicationMember = Driver.getDriver().findElement(By.xpath("(//i[contains(text(), 'edit')])[3]"));
                    assertTrue(editIconFor1stApplicationMember.isDisplayed());
                }
                break;
            default:
                fail("Edit button type didn't match");
        }
    }

    @Then("I click on Edit button for {string} on Member Matching Page")
    public void i_click_on_Edit_button_for_on_Member_Matching_Page(String editType) {
        staticWait(3);
        switch (editType) {
            case "Application":
                jsClick(memberMatchingPage.editIconForApplication);
                break;
            case "Primary Individual":
                jsClick(memberMatchingPage.editIconForPrimaryIndividual);
                break;
            case "1st Application Member":
                jsClick(memberMatchingPage.editIconFor1stApplicationMember);
                break;
            default:
                fail("Edit button type didn't match to click on it");
        }
    }

    @And("I verify that {string} is displayed on Potential Match Data")
    public void i_Verify_That_Id_and_data_Is_Displayed_OnMatching_Data(String consumerIDType) {
        staticWait(3);
        switch (consumerIDType) {
            case "case id":
                assertFalse(memberMatchingPage.matchedCaseID.getText().isEmpty());
                //checks matched case data
                break;
            case "consumer id":
                assertTrue(memberMatchingPage.matchedConsumerID.getText().contains("-- --"));
                //checks matched consumer only data, not on the case
                break;
            case "demographic data for case":
                assertFalse(memberMatchingPage.firstRowApplicantIdList.get(2).getText().isEmpty());
                assertTrue(memberMatchingPage.firstRowFullNameList.get(2).getText().contains(duplicateFirstname + " " + duplicateLastName));
                assertFalse(memberMatchingPage.firstRowSSNlist.get(2).getText().isEmpty());
                assertTrue(convertMMddyyyyToyyyyMMdd(memberMatchingPage.firstRowDOBlist.get(2).getText()).equals(saveSubmit.get().duplicateDOB));
                break;
            case "demographic data for consumer":
                assertTrue(memberMatchingPage.matchedConsumerIdOnMatchingData.get(3).getText().equals(consumerNotOnACaseID.get()));
                assertTrue(memberMatchingPage.matchedConsumerNameOnMatchingData.get(3).getText().equalsIgnoreCase(consumerNotOnCaseFN + " " + consumerNotOnCaseLN));
                assertFalse(memberMatchingPage.matchedConsumerSSNonMatchingData.get(3).getText().isEmpty());
                assertTrue(memberMatchingPage.matchedConsumerDOBonMatchingData.get(3).getText().equals(convertyyyyMMddToMMddyyyy(consumerNotOnCaseDOB.get())));
                break;
            case "existing application":
                assertTrue(memberMatchingPage.existingAppCount.isDisplayed());
                hover(memberMatchingPage.existingAppCount);
                assertNotNull(getHoverText().get("APPLICATION ID"),"Application ID of existing application is not displayed");
                assertNotNull(getHoverText().get("STATUS"),"Status of existing application is not displayed");
                break;
        }

    }

    @Then("I verify that benefit status is displayed {string} on incoming data")
    public void i_verify_that_benefit_status_is_displayed_on_incoming_data(String benefitStatus) {
        staticWait(5);
        switch (benefitStatus) {
            case "Newborn Eligible":
                assertTrue(memberMatchingPage.programType.getText().contains("NEWBORN"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusEligible.getText().equals("ELI"), "Found from UI as program status: "+memberMatchingPage.programStatusEligible.getText());
                break;
            case "Medicaid Eligible":
                assertTrue(memberMatchingPage.programType.getText().contains("MEDICAID-GENERAL"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusEligible.getText().equals("ELI"), "Found from UI as program status: "+memberMatchingPage.programStatusEligible.getText());
                break;
            case "Pregnant Woman Eligible":
                assertTrue(memberMatchingPage.programType.getText().contains("PREGNANT-WOMEN"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusEligible.getText().equals("ELI"), "Found from UI as program status: "+memberMatchingPage.programStatusEligible.getText());
                break;
            case "Newborn Partially Enrolled":
                assertTrue(memberMatchingPage.programType.getText().contains("NEWBORN"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusPartiallyEnrolled.getText().equals("PE"), "Found from UI as program status: "+memberMatchingPage.programStatusPartiallyEnrolled.getText());
                break;
            case "Pregnant Women Enrolled":
                assertTrue(memberMatchingPage.programType.getText().contains("PREGNANT-WOMEN"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusEnrolled.getText().equals("ENR"), "Found from UI as program status: "+memberMatchingPage.programStatusEnrolled.getText());
                break;
            case "Medicaid-General Enrolled":
                assertTrue(memberMatchingPage.programType.getText().contains("MEDICAID-GENERAL"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                assertTrue(memberMatchingPage.programStatusEnrolled.getText().equals("ENR"), "Found from UI as program status: "+memberMatchingPage.programStatusEnrolled.getText());
                break;
            case "No Benefit":
                assertTrue(memberMatchingPage.programType.getText().contains("-- --"), "Found from UI as program type: "+memberMatchingPage.programType.getText());
                break;
        }
    }



}
