

package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.testng.Assert.*;

public class TM_EditPlanDetailsStepDef extends CRMUtilities implements ApiBase {

    TMConfigurationPage configPage = new TMConfigurationPage();
    final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    final DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("MMddyyyy");
    final String DATE_FORMAT = "MM/dd/yyyy";
//    SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT);

    LocalDate contractStart, contractEnd, enrollmentStart, enrollmentEnd, capStart, capEnd;
    final ThreadLocal<String> newContractEndDate = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> newEnrollmentEndDate = ThreadLocal.withInitial(String::new);
    // public APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    private final ThreadLocal<String> filePath = ThreadLocal.withInitial(String::new);

    final ThreadLocal<String> address1_alphaNum25 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address2_alphaNum10 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address2_alphaNum10_failSpecialCharacter = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address1_alphaNum25_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address1_alphaNum25_failSpecialCharacter = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneFormatted = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failSpecialCharacters = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failNums = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> urlFileExLc_alphaNumSpcCharcters70 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> urlFileExLc_alphaNumSpcCharcters70_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> email_alphaNumSpcCharcters50 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> email_alphaNumSpcCharcters50_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw_failAlpha = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw_failLength = ThreadLocal.withInitial(String::new);


    @Then("I will see an edit button within each of the Plan Details containers")
    public void iWillSeeAnEditButtonWithinEachOfThePlanDetailsContainers() {
        assertTrue(configPage.planInfoEditBtn.isDisplayed());
        assertTrue(configPage.contractInfoEditBtn.isDisplayed());
        assertTrue(configPage.enrollmentInfoEditBtn.isDisplayed());
    }


    @And("I can edit fields in only one container at a time")
    public void iCanEditFieldsInOnlyOneContainerAtATime() {
        jsClick(configPage.planInfoEditBtn);
        assertFalse(configPage.contractInfoEditBtn.isEnabled());
        assertFalse(configPage.enrollmentInfoEditBtn.isEnabled());
        jsClick(configPage.detailsContainerCancelBtn);
        assertTrue(configPage.planInfoEditBtn.isEnabled());
        assertTrue(configPage.contractInfoEditBtn.isEnabled());
        assertTrue(configPage.enrollmentInfoEditBtn.isEnabled());
        jsClick(configPage.contractInfoEditBtn);
        assertFalse(configPage.planInfoEditBtn.isEnabled());
        assertFalse(configPage.enrollmentInfoEditBtn.isEnabled());
        jsClick(configPage.detailsContainerCancelBtn);
        assertTrue(configPage.planInfoEditBtn.isDisplayed());
        assertTrue(configPage.contractInfoEditBtn.isDisplayed());
        assertTrue(configPage.enrollmentInfoEditBtn.isDisplayed());
        jsClick(configPage.enrollmentInfoEditBtn);
        assertFalse(configPage.planInfoEditBtn.isEnabled());
        assertFalse(configPage.contractInfoEditBtn.isEnabled());

    }


    @And("I click on the Plan Information container edit button")
    public void iClickOnThePlanInformationContainerEditButton() {

        jsClick(configPage.planInfoEditBtn);
    }

    @Then("I will be able to Exclude Plan From Auto-Assignment")
    public void iWillBeAbleToExcludePlanFromAutoAssignment() {
        waitFor(5);
        jsClick(configPage.planInfoExclusionCheckBox);
        jsClick(configPage.detailsContainerSaveBtn);
        waitForVisibility(configPage.successfulUpdateMsg, 30);
        assertTrue(configPage.successfulUpdateMsg.isDisplayed(), "Fail:Update Not Successful");
        waitFor(5);

    }

    @And("I click on the Contract Information container edit button")
    public void iClickOnTheContractInformationContainerEditButton() {
        waitForVisibility(configPage.contractInfoEditBtn, 30);
        jsClick(configPage.contractInfoEditBtn);
        waitFor(2);
    }

    @Then("I can update the Contract End Date")
    public void iCanUpdateTheContractEndDate() {
        newContractEndDate.set(getGreaterDate(1));
        configPage.contractInfoEndDate.clear();
        configPage.contractInfoEndDate.sendKeys(newContractEndDate.get());
        jsClick(configPage.detailsContainerSaveBtn);
        waitForVisibility(configPage.successfulUpdateMsg, 30);
        assertTrue(configPage.successfulUpdateMsg.isDisplayed(), "Fail:Update Not Successful");
    }


    @And("I provide a Contract End Date that is not greater than the Contract Start Date")
    public void iProvideAContractEndDateThatIsNotGreaterThanTheContractStartDate() {
        waitForVisibility(configPage.contractInfoStartDate, 3);
        String startDate2 = configPage.contractInfoStartDate.getAttribute("textContent").trim();
        String substring = startDate2.substring(startDate2.length() - 4);
        int anteYear = Integer.parseInt(substring);
        anteYear = anteYear - 1;
        configPage.contractInfoEndDate.sendKeys(getInputYearWithCurrentdate(anteYear));

    }

    @Then("I will receive the appropriate Contract Start Date error message")
    public void iWillReceiveTheAppropriateContractStartDateErrorMessage() {
        waitForVisibility(configPage.contractEndDateErrorMsg, 30);
        assertTrue(configPage.contractEndDateErrorMsg.isDisplayed(), "Fail:Date Input Not Earlier than System Date / Start Date");
    }

    @And("I provide a Contract End Date that precedes the System Date")
    public void iProvideAContractEndDateThatPrecedesTheSystemDate() {
        waitForVisibility(configPage.contractInfoStartDate, 30);
        String startDate2 = configPage.contractInfoStartDate.getAttribute("textContent").trim();
        contractStart = LocalDate.parse(configPage.contractInfoStartDate.getAttribute("textContent").trim(), sdf);
        String substring = startDate2.substring(3, 5);
        int anteDay = Integer.parseInt(substring);
        anteDay = anteDay - 1;
        configPage.contractInfoEndDate.sendKeys(getPastDayWithCurrentdate(anteDay));
    }

    @And("I click on the Enrollment Information container edit button")
    public void iClickOnTheEnrollmentInformationContainerEditButton() {
        waitForClickablility(configPage.enrollmentInfoEditBtn, 30);
        jsClick(configPage.enrollmentInfoEditBtn);
        waitFor(2);

    }

    @Then("I will be able to update the appropriate fields in the Enrollment Information container")
    public void iWillBeAbleToUpdateTheAppropriateFieldsInTheEnrollmentInformationContainer() {
        waitForVisibility(configPage.enrollmentInfoStartDate, 30);
        waitForVisibility(configPage.contractInfoStartDate, 30);
        contractStart = LocalDate.parse(configPage.contractInfoStartDate.getAttribute("textContent").trim(), sdf);
        enrollmentStart = LocalDate.parse(configPage.enrollmentInfoStartDate.getAttribute("value").trim(), sdf);
        assertTrue(enrollmentStart.isEqual(contractStart) || enrollmentStart.isAfter(contractStart), "Fail:Enrollment Start Date Is Earlier Than Contract Start Date");

        if (configPage.enrollmentInfoEndDate.getAttribute("textContent").trim().isEmpty() || configPage.enrollmentInfoEndDate.getAttribute("textContent").trim().equals("--")) {
            assertTrue(configPage.enrollmentInfoEndDate.getText().trim().isEmpty() || configPage.enrollmentInfoEndDate.getText().trim().equals("--"));
            String startDate2 = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
            String substring = startDate2.substring(startDate2.length() - 4);
            int nextYear = Integer.parseInt(substring);
            nextYear = nextYear + 4;
            newEnrollmentEndDate.set( getInputYearWithCurrentdate(nextYear));
            configPage.enrollmentInfoEndDate.clear();
            configPage.enrollmentInfoEndDate.sendKeys(newEnrollmentEndDate.get()); //need to assert
            enrollmentEnd = LocalDate.parse(configPage.enrollmentInfoEndDate.getAttribute("value").trim(), sdf);
            assertTrue(enrollmentStart.isBefore(enrollmentEnd), "Enrollment Start Date Does Not Precede Enrollment End Date");
        } else {
            enrollmentEnd = LocalDate.parse(configPage.enrollmentInfoEndDate.getAttribute("value").trim(), sdf);
            assertTrue(enrollmentStart.isBefore(enrollmentEnd), "Enrollment Start Date Does Not Precede Enrollment End Date");
        }
        if (configPage.enrollmentInfoCap.getAttribute("textContent").trim().equals("--") || configPage.enrollmentInfoCap.getAttribute("textContent").trim().isEmpty()) {
            assertTrue(configPage.enrollmentInfoCapStartDate.getAttribute("textContent").trim().isEmpty() || configPage.enrollmentInfoCapStartDate.getAttribute("textContent").trim().equals("--"), "Fail:Cap is Null but Cap Dates Have Values");
            assertTrue(configPage.enrollmentInfoCapEndDate.getAttribute("textContent").trim().isEmpty() || configPage.enrollmentInfoCapEndDate.getAttribute("textContent").trim().equals("--"), "Fail:Cap is Null but Cap Dates Have Values");
        } else {
            capStart = LocalDate.parse(configPage.enrollmentInfoCapStartDate.getAttribute("textContent").trim(), sdf);
            capEnd = LocalDate.parse(configPage.enrollmentInfoCapEndDate.getAttribute("textContent").trim(), sdf);
            assertTrue(capStart.isBefore(capEnd), "Enrollment Cap Start Date Does Not Precede Enrollment Cap End Date");
        }
        jsClick(configPage.enrollmentInfoPcpCheckBox);
        jsClick(configPage.detailsContainerSaveBtn);
        waitForVisibility(configPage.successfulUpdateMsg, 40);
        assertTrue(configPage.successfulUpdateMsg.isDisplayed(), "Fail:Update Not Successful");
    }

    @And("I provide an Enrollment Start Date that precedes the Contract Start Date")
    public void iProvideAnEnrollmentStartDateThatPrecedesTheContractStartDate() {
        waitForVisibility(configPage.enrollmentInfoStartDate, 30);
        String startDate2 = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
        contractStart = LocalDate.parse(configPage.contractInfoStartDate.getAttribute("textContent").trim(), sdf);
        String substring = startDate2.substring(startDate2.length() - 4);
        int anteYear = Integer.parseInt(substring);
        System.out.println(anteYear + "  initial int");
        anteYear = anteYear - 2;
        configPage.enrollmentInfoStartDate.clear();
        configPage.enrollmentInfoStartDate.sendKeys(getInputYearWithCurrentdate(anteYear));

    }

    @Then("I will receive the appropriate Enrollment Start Date error message")
    public void iWillReceiveTheAppropriateEnrollmentStartDateErrorMessage() {
        waitForVisibility(configPage.enrollmentInfoStartDateErrorMsg, 30);
        assertTrue(configPage.enrollmentInfoStartDateErrorMsg.isDisplayed(), "Fail:Date Input Is Equal or Later Than The Contract Start Date");
    }


    @And("I provide an Enrollment End Date that is not greater then the Enrollment Start Date")
    public void iProvideAnEnrollmentEndDateThatIsNotGreaterThenTheEnrollmentStartDate() {
        waitForVisibility(configPage.enrollmentInfoStartDate, 30);
        enrollmentStart = LocalDate.parse(configPage.enrollmentInfoStartDate.getAttribute("value").trim(), sdf);
        String startDate = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
        String substring = startDate.substring(startDate.length() - 4);
        int anteYear = Integer.parseInt(substring);
        System.out.println(anteYear + "  initial int");
        anteYear = anteYear - 2;
        configPage.enrollmentInfoEndDate.sendKeys(getInputYearWithCurrentdate(anteYear));
        scrollToElement(configPage.enrollmentInfoPcpCheckBox);
        jsClick(configPage.enrollmentInfoPcpCheckBox);
    }

    @Then("I will receive the appropriate Enrollment End Date error message")
    public void iWillReceiveTheAppropriateEnrollmentEndDateErrorMessage() {
        jsClick(configPage.detailsContainerSaveBtn);
        waitFor(1);
        waitForVisibility(configPage.enrollmentInfoEndDateErrorMsg, 40);
        assertTrue(configPage.enrollmentInfoEndDateErrorMsg.isDisplayed(), "Fail:Appropriate Error Message Not Displayed");

    }


    @And("I input and save changes to the given Plan Information fields")
    public void iInputAndSaveChangesToTheGivenPlanInformationFields() {
        // iClickOnThePlanInformationContainerEditButton();
        iWillBeAbleToExcludePlanFromAutoAssignment();

    }

    @Then("I see the Plan Information changes were saved")
    public void iSeeThePlanInformationChangesWereSaved() {
        waitFor(2);
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Plan Information Changes Are Not Displayed");
    }

    @And("I am returned to the Plan Detail read-only screen")
    public void iAmReturnedToThePlanDetailReadOnlyScreen() {
        iWillSeeAnEditButtonWithinEachOfThePlanDetailsContainers();

    }


    @And("I input and save changes to the given Contract Information fields")
    public void iInputAndSaveChangesToTheGivenContractInformationFields() {
        iCanUpdateTheContractEndDate();
    }

    @Then("I see the Contract Information changes were saved")
    public void iSeeTheContractInformationChangesWereSaved() {
        waitForVisibility(configPage.contractEndDate, 30);
        System.out.println(newContractEndDate.get() + "  contractEndDate");
        System.out.println(configPage.contractEndDate.getAttribute("textContent") + "  top");
        System.out.println(configPage.contractEndDate.getText() + "  middle");
        System.out.println(configPage.contractEndDate.getAttribute("innerHTML") + "  bottom");
        System.out.println(LocalDate.parse(configPage.contractEndDate.getAttribute("textContent").trim(), sdf));
        waitFor(5);
        assertEquals(LocalDate.parse(newContractEndDate.get(), sdf1), LocalDate.parse(configPage.contractEndDate.getAttribute("textContent").trim(), sdf));
    }

    @And("I input and save changes to the given Enrollment Information fields")
    public void iInputAndSaveChangesToTheGivenEnrollmentInformationFields() {
        iWillBeAbleToUpdateTheAppropriateFieldsInTheEnrollmentInformationContainer();


    }

    @Then("I see the Enrollment Information changes were saved")
    public void iSeeTheEnrollmentInformationChangesWereSaved() {

        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("true"));
        assertTrue(configPage.enrollmentEndDate.getAttribute("textContent").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
    }

    @And("I input and cancel changes to the given Plan Information fields")
    public void iInputAndCancelChangesToTheGivenPlanInformationFields() {
        jsClick(configPage.planInfoExclusionCheckBox);
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
        jsClick(configPage.detailsContainerCancelBtn);
        waitForVisibility(configPage.cancelEditWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");


    }

    @Then("I see the Plan Information changes were cleared")
    public void iSeeThePlanInformationChangesWereCleared() {
        waitForVisibility(configPage.planInfoExclusionCheckBox, 4);
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Cleared By Cancel");
    }

    @And("I input and cancel changes to the given Contract Information fields")
    public void iInputAndCancelChangesToTheGivenContractInformationFields() {
        newContractEndDate.set(getGreaterDate(1));
        configPage.contractInfoEndDate.clear();
        configPage.contractInfoEndDate.sendKeys(newContractEndDate.get());
        assertTrue(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.detailsContainerCancelBtn);
        waitForVisibility(configPage.cancelEditWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.detailsContainerCancelBtn);
        waitForVisibility(configPage.cancelEditWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);


    }

    @Then("I see the Contract Information changes were cleared")
    public void iSeeTheContractInformationChangesWereCleared() {
        assertFalse(configPage.contractEndDate.getAttribute("textContent").trim().equalsIgnoreCase(newContractEndDate.get()));
    }

    @And("I input and cancel changes to the given Enrollment Information fields")
    public void iInputAndCancelChangesToTheGivenEnrollmentInformationFields() {
        String startDate2 = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
        String substring = startDate2.substring(startDate2.length() - 4);
        int nextYear = Integer.parseInt(substring);
        nextYear = nextYear + 1;
        newEnrollmentEndDate.set( getInputYearWithCurrentdate(nextYear));
        configPage.enrollmentInfoEndDate.clear();
        configPage.enrollmentInfoEndDate.sendKeys(newEnrollmentEndDate.get());
        jsClick(configPage.enrollmentInfoPcpCheckBox);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.detailsContainerCancelBtn);
        waitForVisibility(configPage.cancelEditWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.detailsContainerCancelBtn);
        waitForVisibility(configPage.cancelEditWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);


    }

    @Then("I see the Enrollment Information changes were cleared")
    public void iSeeTheEnrollmentInformationChangesWereCleared() {
        assertFalse(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertFalse(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
    }

    @And("I input changes to the given Plan Information fields and click the Back Arrow")
    public void iInputChangesToTheGivenPlanInformationFieldsAndClickTheBackArrow() {
        jsClick(configPage.planInfoExclusionCheckBox);

        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");

        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);

        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);


    }


    @And("I input changes to the given Contract Information fields and click the Back Arrow")
    public void iInputChangesToTheGivenContractInformationFieldsAndClickTheBackArrow() {
        newContractEndDate.set(getGreaterDate(1));
        configPage.contractInfoEndDate.clear();
        configPage.contractInfoEndDate.sendKeys(newContractEndDate.get());
        assertTrue(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);

    }

    @And("I input changes to the given Enrollment fields and click the Back Arrow")
    public void iInputChangesToTheGivenContractEnrollmentFieldsAndClickTheBackArrow() {
        String startDate2 = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
        String substring = startDate2.substring(startDate2.length() - 4);
        int nextYear = Integer.parseInt(substring);
        nextYear = nextYear + 1;
        newEnrollmentEndDate.set( getInputYearWithCurrentdate(nextYear));
        configPage.enrollmentInfoEndDate.clear();
        configPage.enrollmentInfoEndDate.sendKeys(newEnrollmentEndDate.get());

        jsClick(configPage.enrollmentInfoPcpCheckBox);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.pdBackArrow);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);

    }

    @Then("I will be navigated to the given page")
    public void iWillBeNavigatedToTheGivenPage() {
        waitForPageToLoad(2);
        assertTrue(configPage.userListHeader.isDisplayed(), "Navigation To User List Page Failed");

    }

    @And("I input changes to the given Contract Information fields and navigate away")
    public void iInputChangesToTheGivenContractInformationFieldsAndNavigateAway() {
        newContractEndDate.set(getGreaterDate(1));
        configPage.contractInfoEndDate.clear();
        configPage.contractInfoEndDate.sendKeys(newContractEndDate.get());
        System.out.println("Without convertion" + configPage.contractInfoEndDate.getAttribute("value").trim() + "  " + newContractEndDate.get());
        assertFalse(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 30);
        jsClick(configPage.warningMsgCancelBtn);
        assertFalse(configPage.contractInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newContractEndDate.get()));
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 30);
        jsClick(configPage.warningMsgContinueBtn);
    }

    // // @author Sean Thorson
    @Given("that I have navigated to the Contact Details Tab")
    public void that_I_have_navigated_to_the_Contact_Details_Tab() {
        jsClick(configPage.contactDetailsTab);

    }


    @When("I click on the Edit button next to the Plan Mailing Address Container")
    public void i_click_on_the_Edit_button_next_to_the_Plan_Mailing_Address_Container() {
        jsClick(configPage.cdMailingAddressEditBtn);
    }

    @Then("I will be able to update the following fields:")
    public void i_will_be_able_to_update_the_following_fields() {
        waitForClickablility(configPage.addressLine1Input, 3);
        hover(configPage.addressLine1Input);
        address1_alphaNum25_failSpecialCharacter.set("jhjbj&*^&*KJH");
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25_failSpecialCharacter.get());
        waitFor(2);
        assertFalse(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25_failSpecialCharacter.get()), "Fail:SpecialCharacter Accepted");
        //   assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase("1a2b3c4d5e6f7g8h9i1j2k3"));
        address1_alphaNum25_failLength.set("jkasdhasjkdhasjkdhkasjkdhasjkdhjaksjdhasj");
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25_failLength.get());
        assertFalse(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25_failLength.get()), "Fail:Incorrect Length");
        // assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase("1a2b3c4d5e6f7g8h9i1j2k3l4"));
        address1_alphaNum25.set("abcdefghijklmnopqrstuvwxy");
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25.get());
        assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25.get()), "Fail:Incorrect Value");
        address2_alphaNum10_failSpecialCharacter.set("1a2b3c4d^&");
        clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10_failSpecialCharacter.get());
        assertFalse(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase(address2_alphaNum10_failSpecialCharacter.get()), "Fail:SpecialCharacter Accepted");
        assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase("1a2b3c4d"));
        // clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10_failLength);
        // address1_alphaNum25_failLength="1a2b3c4d5e6f7g8h9i1j2k3l45";
        clearAndFillText(configPage.addressLine2InputDEUX, address1_alphaNum25_failLength.get());
        assertFalse(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase(address1_alphaNum25_failLength.get()));
        address2_alphaNum10.set("1a2b3c4d5e6f7g8h9i1j2k3l4");
        clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10.get());
        assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase(address2_alphaNum10.get()));


    }


    @When("I click on the Edit button next to the Contact Information Container")
    public void i_click_on_the_Edit_button_next_to_the_Contact_Information_Container() {
        jsClick(configPage.cdContactInformationEditBtn);

    }

    @And("I am returned to the Contact Detail read-only screen")
    public void iAmReturnedToTheContactDetailReadOnlyScreen() {
        iWillSeeAnEditButtonWithinEachOfTheContactDetailsContainers();

    }

    @Then("I will see an edit button within each of the Contact Details containers")
    public void iWillSeeAnEditButtonWithinEachOfTheContactDetailsContainers() {
        assertTrue(configPage.cdMailingAddressEditBtn.isDisplayed());
        assertTrue(configPage.cdContactInformationEditBtn.isDisplayed());


    }


    @Then("I will be able to update the given fields")
    public void i_will_be_able_to_update_the_given_fields() {
        waitForClickablility(configPage.memberservicesphone1, 3);
        hover(configPage.memberservicesphone1);
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.memberservicesphone1, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.memberservicesphone1, 3);
        hover(configPage.memberservicesphone1);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.memberservicesphone1, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone1, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.providerservicesphone1, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.providerservicesphone1, 3);
        hover(configPage.providerservicesphone1);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.providerservicesphone1, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone1, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        urlFileExLc_alphaNumSpcCharcters70_failLength.set("https://www.google.com/search?q=google&rlz=1C1EJFA_enPK800PK800&oq=google&aqs=chrome..69i57j69i60l3j69i65j69i60l3.3118j0j4&sourceid=chrome&ie=UTF-8");
        clearAndFillText(configPage.planmemberserviceswebsiteURL, urlFileExLc_alphaNumSpcCharcters70_failLength.get());
        waitFor(2);
        assertFalse(configPage.planmemberserviceswebsiteURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70_failLength.get()), "Fail:length Accepted");
        urlFileExLc_alphaNumSpcCharcters70.set("https://www.google.com/search?q=23423432");
        clearAndFillText(configPage.planmemberserviceswebsiteURL, urlFileExLc_alphaNumSpcCharcters70.get());
        waitFor(2);
        assertTrue(configPage.planmemberserviceswebsiteURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        name_alpha15_failLength.set("abcdefhgijklmnop");
        clearAndFillText(configPage.plancontactfirstname, name_alpha15_failLength.get());
        waitFor(2);
        assertFalse(configPage.plancontactfirstname.getAttribute("value").equalsIgnoreCase(name_alpha15_failLength.get()), "Fail:length Accepted");
        name_alpha15_failSpecialCharacters.set("Richard& Rich@");
        clearAndFillText(configPage.plancontactfirstname, name_alpha15_failSpecialCharacters.get());
        waitFor(2);
        assertFalse(configPage.plancontactfirstname.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:Special char Accepted");
        name_alpha15_failNums.set("Richard123");
        clearAndFillText(configPage.plancontactfirstname, name_alpha15_failNums.get());
        waitFor(2);
        assertFalse(configPage.plancontactfirstname.getAttribute("value").equalsIgnoreCase(name_alpha15_failNums.get()), "Fail:Num Accepted");
        name_alpha15.set("Richard Richard");
        clearAndFillText(configPage.plancontactfirstname, name_alpha15.get());
        waitFor(2);
        assertTrue(configPage.plancontactfirstname.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        name_alpha15_failLength.set("abcdefhgijklmnop");
        clearAndFillText(configPage.plancontactlastname, name_alpha15_failLength.get());
        waitFor(2);
        assertFalse(configPage.plancontactlastname.getAttribute("value").equalsIgnoreCase(name_alpha15_failLength.get()), "Fail:length Accepted");
        name_alpha15_failSpecialCharacters.set("Richard& Rich@");
        clearAndFillText(configPage.plancontactlastname, name_alpha15_failSpecialCharacters.get());
        waitFor(2);
        assertFalse(configPage.plancontactlastname.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:Special char Accepted");
        name_alpha15_failNums.set("Richard123");
        clearAndFillText(configPage.plancontactlastname, name_alpha15_failNums.get());
        waitFor(2);
        assertFalse(configPage.plancontactlastname.getAttribute("value").equalsIgnoreCase(name_alpha15_failNums.get()), "Fail:Num Accepted");
        name_alpha15.set("Richard Richard");
        clearAndFillText(configPage.plancontactlastname, name_alpha15.get());
        waitFor(2);
        assertTrue(configPage.plancontactlastname.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.plancontactphonenumber, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.plancontactphonenumber.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.plancontactphonenumber, 3);
        hover(configPage.plancontactphonenumber);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.plancontactphonenumber, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.plancontactphonenumber.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.plancontactphonenumber, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.plancontactphonenumber.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        email_alphaNumSpcCharcters50_failLength.set("gadchinquiriescaresourcecaresource@caresourcecaresource.com");
        clearAndFillText(configPage.plancontactemail, email_alphaNumSpcCharcters50_failLength.get());
        waitFor(2);
        assertFalse(configPage.plancontactemail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50_failLength.get()), "Fail:length Accepted");
        email_alphaNumSpcCharcters50.set("gadchinquiries@caresource.com");
        clearAndFillText(configPage.plancontactemail, email_alphaNumSpcCharcters50.get());
        waitFor(2);
        assertTrue(configPage.plancontactemail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50.get()));
        urlFileExLc_alphaNumSpcCharcters70_failLength.set("/Health_Services/GA_Families/Provider/GF_Caresource/Health_Services/GA_Families/Provider/GF_Caresource");
        clearAndFillText(configPage.plancontactlocation, urlFileExLc_alphaNumSpcCharcters70_failLength.get());
        waitFor(2);
        assertFalse(configPage.plancontactlocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70_failLength.get()), "Fail:length Accepted");
        urlFileExLc_alphaNumSpcCharcters70.set("/Health_Services/GA_Families/Provider/GF_Caresource");
        clearAndFillText(configPage.plancontactlocation, urlFileExLc_alphaNumSpcCharcters70.get());
        waitFor(2);
        assertTrue(configPage.plancontactlocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.memberservicesphone2, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.memberservicesphone2, 3);
        hover(configPage.memberservicesphone2);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.memberservicesphone2, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone2, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.memberservicesphone3, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.memberservicesphone3, 3);
        hover(configPage.memberservicesphone3);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.memberservicesphone3, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone3, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.memberservicesphone4, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone4.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.memberservicesphone4, 3);
        hover(configPage.memberservicesphone4);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.memberservicesphone4, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone4.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone4, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone4.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.memberservicesphone5, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone5.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.memberservicesphone5, 3);
        hover(configPage.memberservicesphone5);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.memberservicesphone5, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.memberservicesphone5.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone5, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone5.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.providerservicesphone2, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.providerservicesphone2, 3);
        hover(configPage.providerservicesphone2);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.providerservicesphone2, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone2, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneRaw_failAlpha.set("abcdefgh");
        clearAndFillText(configPage.providerservicesphone3, phoneRaw_failAlpha.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:alpha Accepted");
        waitForClickablility(configPage.providerservicesphone3, 3);
        hover(configPage.providerservicesphone3);
        phoneRaw_failLength.set("12345678919");
        clearAndFillText(configPage.providerservicesphone3, phoneRaw_failLength.get());
        waitFor(2);
        assertFalse(configPage.providerservicesphone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:length Accepted");
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone3, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
    }


    @Then("I will add contact information")
    public void i_will_add_contact_information() {
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone1, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone1, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        urlFileExLc_alphaNumSpcCharcters70.set("https://www.google.com/search?q=23423432");
        clearAndFillText(configPage.planmemberserviceswebsiteURL, urlFileExLc_alphaNumSpcCharcters70.get());
        waitFor(2);
        assertTrue(configPage.planmemberserviceswebsiteURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        name_alpha15.set("Richard Richard");
        clearAndFillText(configPage.plancontactfirstname, name_alpha15.get());
        waitFor(2);
        assertTrue(configPage.plancontactfirstname.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        name_alpha15.set("Richard Richard");
        clearAndFillText(configPage.plancontactlastname, name_alpha15.get());
        waitFor(2);
        assertTrue(configPage.plancontactlastname.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.plancontactphonenumber, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.plancontactphonenumber.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        email_alphaNumSpcCharcters50.set("gadchinquiries@caresource.com");
        clearAndFillText(configPage.plancontactemail, email_alphaNumSpcCharcters50.get());
        waitFor(2);
        assertTrue(configPage.plancontactemail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50.get()));
        urlFileExLc_alphaNumSpcCharcters70.set("/Health_Services/GA_Families/Provider/GF_Caresource");
        clearAndFillText(configPage.plancontactlocation, urlFileExLc_alphaNumSpcCharcters70.get());
        waitFor(2);
        assertTrue(configPage.plancontactlocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone2, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone3, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone4, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone4.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.memberservicesphone5, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.memberservicesphone5.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone2, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        phoneFormatted.set("123-123-1234");
        clearAndFillText(configPage.providerservicesphone3, phoneFormatted.get());
        waitFor(2);
        assertTrue(configPage.providerservicesphone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
    }


    @Then("I will Save the information")
    public void save_information() {
        jsClick(configPage.detailsContainerSaveBtn);
        waitForVisibility(configPage.successfulUpdateMsg, 30);
        assertTrue(configPage.successfulUpdateMsg.isDisplayed(), "Fail:Update Not Successful");

    }


    @And("I input and cancel Continue changes to the given Plan Information fields")
    public void iInputAndCancelContinueChangesToTheGivenPlanInformationFields() {
        if (configPage.planInfoExclusionCheckBox.isSelected()) {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Selected");
            jsClick(configPage.detailsContainerCancelBtn);
            waitFor(5);
            waitForVisibility(configPage.cancelEditWarningMsg, 3);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            waitFor(5);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        } else {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
            jsClick(configPage.detailsContainerCancelBtn);
            waitFor(5);
            waitForVisibility(configPage.cancelEditWarningMsg, 3);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            waitFor(5);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"));
        }


    }

    @And("I input and press back arrow Cancel changes to the given Plan Information fields")
    public void iInputAndpressbackarrowCancelChangesToTheGivenPlanInformationFields() {
        if (configPage.planInfoExclusionCheckBox.isSelected()) {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Selected");
            jsClick(configPage.backArrow);
            waitFor(5);
            assertTrue(configPage.navigationWarningMsg.isDisplayed());

            waitFor(5);
            jsClick(configPage.warningMsgCancelBtn);
            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            waitFor(5);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        } else {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
            jsClick(configPage.backArrow);
            waitFor(5);
            assertTrue(configPage.navigationWarningMsg.isDisplayed());

            jsClick(configPage.warningMsgCancelBtn);

            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            waitFor(5);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"));

        }


    }

    @And("I input and press back arrow Continue changes to the given Plan Information fields")
    public void iInputAndpressbackarrowContinueChangesToTheGivenPlanInformationFields() {
        if (configPage.planInfoExclusionCheckBox.isSelected()) {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Selected");
            jsClick(configPage.backArrow);
            waitFor(5);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            waitFor(5);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        } else {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
            jsClick(configPage.backArrow);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            waitFor(5);
//            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
//            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"));
        }


    }

    @And("I input and press Navigation Link from the given Plan Information fields")


    public void iInputAndpressnavigationlinkTheGivenPlanInformationFields() {
        if (configPage.planInfoExclusionCheckBox.isSelected()) {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Selected");
            jsClick(configPage.navigationlink);
            waitFor(5);
            assertTrue(configPage.navigationWarningMsg.isDisplayed());
            //
            //            waitFor(5);
            //            jsClick(configPage.warningMsgCancelBtn);
            //            System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
            //            waitFor(5);
            //            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        } else {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
            jsClick(configPage.navigationlink);
            waitFor(5);
            waitFor(5);
            assertTrue(configPage.navigationWarningMsg.isDisplayed());


 /*
             jsClick(configPage.warningMsgCancelBtn);
             System.out.println(configPage.planInfoExclusionCheckBox.getAttribute("value"));
             waitFor(5);
             assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"));
 */


        }


    }

    @And("I input and navigate to Continue changes to the given Plan Information fields")
    public void iInputAndnavigatetowContinueChangesToTheGivenPlanInformationFields() {
        if (configPage.planInfoExclusionCheckBox.isSelected()) {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("false"), "Exclusion Check Box Not Selected");
            jsClick(configPage.navigationlink);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            waitFor(5);
            assertTrue(configPage.holiday.isDisplayed());
        } else {
            jsClick(configPage.planInfoExclusionCheckBox);
            assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
            jsClick(configPage.navigationlink);
            waitFor(5);
            jsClick(configPage.warningMsgContinueBtn);
            waitFor(5);
            assertTrue(configPage.holiday.isDisplayed());


        }


    }


    // @author Sean Thorson


    @And("I input changes to the given Plan Information fields and navigate away")
    public void iInputChangesToTheGivenPlanInformationFieldsAndNavigateAway() {
        jsClick(configPage.planInfoExclusionCheckBox);
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Not Selected");
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.planInfoExclusionCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);
    }

    @And("I input changes to the given Enrollment Information fields and navigate away")
    public void iInputChangesToTheGivenEnrollmentInformationFieldsAndNavigateAway() {
        String startDate2 = configPage.enrollmentInfoStartDate.getAttribute("value").trim();
        String substring = startDate2.substring(startDate2.length() - 4);
        int nextYear = Integer.parseInt(substring);
        nextYear = nextYear + 1;
        newEnrollmentEndDate.set( getInputYearWithCurrentdate(nextYear));
        configPage.enrollmentInfoEndDate.clear();
        configPage.enrollmentInfoEndDate.sendKeys(newEnrollmentEndDate.get());

        jsClick(configPage.enrollmentInfoPcpCheckBox);
        waitFor(10);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgCancelBtn);
        assertTrue(configPage.enrollmentInfoEndDate.getAttribute("value").trim().equalsIgnoreCase(newEnrollmentEndDate.get()));
        assertTrue(configPage.enrollmentInfoPcpCheckBox.getAttribute("value").equalsIgnoreCase("true"), "Exclusion Check Box Change Cleared By Cancellation of Cancel Edit");
        jsClick(configPage.userListSideBarTab);
        waitForVisibility(configPage.navigationWarningMsg, 3);
        jsClick(configPage.warningMsgContinueBtn);
    }


    @Then("I will receive the appropriate Invalid Format error message")
    public void iWillReceiveTheAppropriateInvalidDateErrorMessage() {
        waitForVisibility(configPage.invalidFormatErrorMsg, 3);
        assertTrue(configPage.invalidFormatErrorMsg.isDisplayed());
    }

    @And("I provide a Contract End Date of incorrect length")
    public void iProvideAContractEndDateOfIncorrectLength() {
        configPage.contractInfoEndDate.clear();
        configPage.contractInfoEndDate.sendKeys("999999");
        hover(configPage.detailsContainerSaveBtn);
        jsClick(configPage.detailsContainerSaveBtn);

    }
}

