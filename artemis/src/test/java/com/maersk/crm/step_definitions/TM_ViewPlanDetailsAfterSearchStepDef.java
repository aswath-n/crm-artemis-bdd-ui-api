package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMConfigurationPage;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TM_ViewPlanDetailsAfterSearchStepDef extends TMUtilities {

    TMConfigurationPage configPage = new TMConfigurationPage();
    public final ThreadLocal<String> project = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("projectName").split(" ")[1]);

    final ThreadLocal<LocalDate> contractStart = ThreadLocal.withInitial(LocalDate::now);
    final ThreadLocal<LocalDate> enrollmentStart = ThreadLocal.withInitial(LocalDate::now);
    final ThreadLocal<LocalDate> contractEnd = ThreadLocal.withInitial(LocalDate::now);
    final ThreadLocal<LocalDate> enrollmentEnd = ThreadLocal.withInitial(LocalDate::now);
    final ThreadLocal<LocalDate> capEnd = ThreadLocal.withInitial(LocalDate::now);
    final ThreadLocal<LocalDate> capStart = ThreadLocal.withInitial(LocalDate::now);


    final ThreadLocal<String> address1_alphaNum25 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address2_alphaNum10 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address1_alphaNum25_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address1_alphaNum25_failSpecialCharacter = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address2_alphaNum10_failSpecialCharacter = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> address2_alphaNum10_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneFailed = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneFormatted = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failSpecialCharacters = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15_failNums = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> name_alpha15Failed = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> urlFileExLc_alphaNumSpcCharcters70 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> urlFileExLc_alphaNumSpcCharcters70_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> email_alphaNumSpcCharcters50 = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> email_alphaNumSpcCharcters50_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw_failAlpha = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw_failLength = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> phoneRaw_failSpcCharacters = ThreadLocal.withInitial(String::new);


    @And("I see three tabs dividing the Plan Details screen")
    public void iSeeThreeTabsDividingThePlanDetailsScreen() {
        assertTrue(configPage.planDetailsTab.isDisplayed(), "Plan Details Tab Not Present");
        assertTrue(configPage.contactDetailsTab.isDisplayed(), "Contact Details Tab Not Present");
        assertTrue(configPage.valueAddedServicesTab.isDisplayed(), "Value Added Services Tab Not Present");
    }

    @Then("I am on the Plan Details Tab by default")
    public void iAmOnThePlanDetailsTabByDefault() {
        assertTrue(configPage.planDetailsButton.getAttribute("aria-selected").equals("true"));

    }

    @Then("I see the appropriate fields in the Plan Information container")
    public void iSeeTheAppropriateFieldsInThePlanInformationContainer() {
        waitForVisibility(configPage.picEligibility, 3);
        assertTrue(configPage.picPlanName.isDisplayed(), "Plan Name Not Displayed");
        assertTrue(configPage.picPlanCode.isDisplayed(), "Plan Code Not Displayed");
        assertTrue(configPage.picPlanShortName.isDisplayed(), "Short Name Not Displayed");
        assertTrue(configPage.picServiceRegion.isDisplayed(), "Service Region Not Displayed");
        assertTrue(configPage.picPlanType.isDisplayed(), "Plan Type Not Displayed");
        assertTrue(configPage.picProgramType.isDisplayed(), "Program Type Not Displayed");
        assertTrue(configPage.picSubType.isDisplayed(), "Sub-Type Not Displayed");
        assertTrue(configPage.picEligibility.isDisplayed(), "Eligibility Not Displayed");
        assertTrue(configPage.picExcludeAutoCheckBx.isDisplayed(), "Exclude Form Auto-Assignment Not Displayed ");

    }

    @Then("I see the appropriate fields in the Contract Information container")
    public void iSeeTheAppropriateFieldsInTheContractInformationContainer() {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(configPage.contractStartDate.getText().trim());
        assertTrue(configPage.contractStartDateField.isDisplayed(), "Contract Start Date Not Displayed");
        assertTrue(configPage.contractEndDateField.isDisplayed(), "Contract End Date Not Displayed");
        if (configPage.contractEndDate.getText().trim().isEmpty() || configPage.contractEndDate.getText().trim().equals("--")) {
            System.out.println("NULL == Active Plan");
            assertTrue(configPage.contractEndDate.getText().trim().equalsIgnoreCase("--") || configPage.contractEndDate.getText().trim().isEmpty());
        } else {
            contractStart.set(LocalDate.parse(configPage.contractStartDate.getText().trim(), sdf));
            contractEnd.set(LocalDate.parse(configPage.contractEndDate.getText().trim(), sdf));
            assertTrue(contractStart.get().isBefore(contractEnd.get()), "Contract Start Date Does Not Precede Contract End Date");
        }

    }

    @Then("I see the appropriate fields in the Enrollment Information container")
    public void iSeeTheAppropriateFieldsInTheEnrollmentInformationContainer() {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        assertTrue(configPage.enrollmentStartDateField.isDisplayed(), "Enrollment Start Date Not Displayed");
        assertTrue(configPage.enrollmentEndDateField.isDisplayed(), "Enrollment End Date Not Displayed");
        assertTrue(configPage.enrollmentCap.isDisplayed(), "Enrollment Cap Not Displayed");
        assertTrue(configPage.enrollmentCapStartDateField.isDisplayed(), "Enrollment Cap Start Date Not Displayed");
        assertTrue(configPage.enrollmentCapEndDateField.isDisplayed(), "Enrollment Cap End Date Not Displayed");
        assertTrue(configPage.pcpRequiredCheckBx.isDisplayed(), "PCP Required Checkbox Not Displayed");

        if (configPage.enrollmentEndDate.getText().trim().isEmpty() || configPage.enrollmentEndDate.getText().trim().equals("--")) {
            System.out.println("NULL == Plan Open for Enrollment");
            assertTrue(configPage.enrollmentEndDate.getText().trim().isEmpty() || configPage.enrollmentEndDate.getText().trim().equals("--"));
        } else {
            enrollmentStart.set(LocalDate.parse(configPage.enrollmentStartDate.getText().trim(), sdf));
            enrollmentEnd.set(LocalDate.parse(configPage.enrollmentEndDate.getText().trim(), sdf));
            assertTrue(enrollmentStart.get().isBefore(enrollmentEnd.get()), "Enrollment Start Date Does Not Precede Enrollment End Date");
        }
        if (configPage.enrollmentCapEndDate.getText().trim().isEmpty() || configPage.enrollmentCapEndDate.getText().trim().equals("--")) {

            System.out.println("NULL == Cap Open ");
            assertTrue(configPage.enrollmentCapEndDate.getText().trim().isEmpty() || configPage.enrollmentCapEndDate.getText().trim().equals("--"));
        } else {
            capStart.set( LocalDate.parse(configPage.enrollmentCapStartDate.getText().trim(), sdf));
            capEnd.set(LocalDate.parse(configPage.enrollmentCapEndDate.getText().trim(), sdf));
            assertTrue(capStart.get().isBefore(capEnd.get()), "Enrollment Cap Start Date Does Not Precede Enrollment Cap End Date");
        }


    }

    @And("I click on the Contact Details tab")
    public void iClickOnTheContactDetailsTab() {
        jsClick(configPage.contactDetailsScreen);
        waitForVisibility(configPage.contactInfoContainerHeader, 3);
        assertTrue(configPage.contactInfoContainerHeader.isDisplayed(), "Did Not Reach Contact Details Screen");

    }

    @And("I am brought to the Contact Details screen")
    public void iAmBroughtToTheContactDetailsScreen() {
        waitForVisibility(configPage.contactInfoContainerHeader, 3);
        assertTrue(configPage.contactInfoContainerHeader.isDisplayed(), "Did Not Reach Contact Details Screen");

    }

    @Then("I see the appropriate fields in the Plan Mailing Address container")
    public void iSeeTheAppropriateFieldsInThePlanMailingAddressContainer() {

        /*  fields present and accounted  */
        try {
            jsClick(configPage.closeContactDetailsErroMsg);
        } catch (NoSuchElementException a) {
        }
        assertTrue(configPage.addressLine1.isDisplayed(), "Address LineOne Not Displayed");
        assertTrue(configPage.addressLine2.isDisplayed(), "Address LineTwo Not Displayed");
        assertTrue(configPage.city.isDisplayed(), "City Not Displayed");
        assertTrue(configPage.state.isDisplayed(), "State Not Displayed");
        assertTrue(configPage.zipCode.isDisplayed(), "ZipCode Not Displayed");
        /* Verifying input requirements  */
        address1_alphaNum25.set("1a2b3c4d5e6f7g8h9i1j2k3l4");
        address1_alphaNum25_failSpecialCharacter.set("1a2b3c4d5e6f7g8h9i1j2k3##");
        address1_alphaNum25_failLength.set("1a2b3c4d5e6f7g8h9i1j2k3l4XXX");
        address2_alphaNum10.set("1a2b3c4d5c");
        address2_alphaNum10_failSpecialCharacter.set("1a2b3c4d##");
        address2_alphaNum10_failLength.set("1a2b3c4d5cXX");

        jsClick(configPage.cdMailingAddressEditBtn);
        waitForClickablility(configPage.addressLine1Input, 3);
        hover(configPage.addressLine1Input);
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25_failSpecialCharacter.get());
        assertFalse(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25_failSpecialCharacter.get()), "Fail:SpecialCharacter Accepted");
        assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase("1a2b3c4d5e6f7g8h9i1j2k3"));
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25_failLength.get());
        assertFalse(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25_failLength.get()), "Fail:Incorrect Length");
        assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase("1a2b3c4d5e6f7g8h9i1j2k3l4"));
        clearAndFillText(configPage.addressLine1Input, address1_alphaNum25.get());
        assertTrue(configPage.addressLine1Input.getAttribute("value").equalsIgnoreCase(address1_alphaNum25.get()), "Fail:Incorrect Value");

        clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10_failSpecialCharacter.get());
        assertFalse(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase(address2_alphaNum10_failSpecialCharacter.get()), "Fail:SpecialCharacter Accepted");
        assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase("1a2b3c4d"));
        clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10_failLength.get());
        try {
            assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").isEmpty());
        } catch (AssertionError a) {
            clearAndFillText(configPage.addressLine2InputDEUX, address1_alphaNum25_failLength.get());
            assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase("1a2b3c4d5e6f7g8h9i1j2k3l4"));
            System.out.println("Input Character Length Incorrect");
        }
        clearAndFillText(configPage.addressLine2InputDEUX, address2_alphaNum10.get());
        assertTrue(configPage.addressLine2InputDEUX.getAttribute("value").equalsIgnoreCase(address2_alphaNum10.get()), "Fail:Incorrect Value");

    }

    @Then("I see the appropriate fields in the Contact Information container")
    public void iSeeTheAppropriateFieldsInTheContactInformationContainer() {
        phoneRaw.set("1231231234");
        phoneRaw_failAlpha.set("123123abCD");
        phoneRaw_failLength.set("123123123456");
        phoneRaw_failSpcCharacters.set("123123!@#$");
        phoneFormatted.set("123-123-1234");
        phoneFailed.set("123-123-");
        name_alpha15_failLength.set("aBcDeFgHiJkLmNoXXX");
        name_alpha15_failSpecialCharacters.set("aBcDeFgHiJkL@#$");
        name_alpha15_failNums.set("aBcDeFgHiJkL123");
        name_alpha15.set("aBcDeFgHiJkLmNo");
        name_alpha15Failed.set("aBcDeFgHiJkL");
        urlFileExLc_alphaNumSpcCharcters70.set("1234567890asdfghjklzASGRHXUELK!@#$%^&*()-=;,.vsuhbde{}3377eyhsw!@#sh67");
        urlFileExLc_alphaNumSpcCharcters70_failLength.set("1234567890asdfghjklzASGRHXUELK!@#$%^&*()-=;,.vsuhbde{}3377eyhsw!@#sh67XXX");
        email_alphaNumSpcCharcters50.set("12345asdfgqwertcndASDRTF,.;'[]{}/-=!@#$%^&*(().cde");
        email_alphaNumSpcCharcters50_failLength.set("12345asdfgqwertcndASDRTF,.;'[]{}/-=!@#$%^&*(().cdeXXX");


        try {
            jsClick(configPage.closeContactDetailsErroMsg);
        } catch (NoSuchElementException a) {
        }
        waitFor(2);
        assertTrue(configPage.memberPhone1Field.isDisplayed(), "MemberPhoneOne Not Displayed");
        waitFor(10);
        assertTrue(configPage.viewProviderSerPhone1.isDisplayed(), "ProviderPhoneOne Not Displayed");
        assertTrue(configPage.viewProviderSerPhone2.isDisplayed(), "ProviderPhoneTwo Not Displayed");
        assertTrue(configPage.viewProviderSerPhone3.isDisplayed(), "ProviderPhoneThree Not Displayed");
        assertTrue(configPage.memberServiceURLField.isDisplayed(), "MemberServiceURL Not Displayed");
        assertTrue(configPage.contactFirstNameField.isDisplayed(), "Contact FirstName Not Displayed");
        assertTrue(configPage.contactLastNameField.isDisplayed(), "Contact LastName Not Displayed");
        assertTrue(configPage.contactPhoneField.isDisplayed(), "Contact Phone Not Displayed");
        assertTrue(configPage.contactEmailField.isDisplayed(), "Contact Email Not Displayed");
        assertTrue(configPage.fileLocationField.isDisplayed(), "File Location Not Displayed");
        assertTrue(configPage.memberPhone2Field.isDisplayed(), "MemberPhoneTwo Not Displayed");
        assertTrue(configPage.memberPhone3Field.isDisplayed(), "MemberPhoneThree Not Displayed");
        assertTrue(configPage.memberPhone4Field.isDisplayed(), "MemberPhoneFour Not Displayed");
        assertTrue(configPage.memberPhone5Field.isDisplayed(), "MemberPhoneFive Not Displayed");


        jsClick(configPage.cdContactInformationEditBtn);

        /*    phones    */
        clearAndFillText(configPage.memberPhone1, phoneRaw.get());
        assertFalse(configPage.memberPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.memberPhone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.memberPhone1, phoneRaw_failAlpha.get());
        assertFalse(configPage.memberPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.memberPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone1, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.memberPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.memberPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone1, phoneRaw_failLength.get());
        assertFalse(configPage.memberPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.viewMemeberSerPhone2, phoneRaw.get());
        assertFalse(configPage.viewMemeberSerPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.viewMemeberSerPhone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.memberPhone2, phoneRaw_failAlpha.get());
        assertFalse(configPage.memberPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.memberPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone2, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.memberPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.memberPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone2, phoneRaw_failLength.get());
        assertFalse(configPage.memberPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.memberPhone3, phoneRaw.get());
        assertFalse(configPage.memberPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.memberPhone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.memberPhone3, phoneRaw_failAlpha.get());
        assertFalse(configPage.memberPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.memberPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone3, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.memberPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.memberPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone3, phoneRaw_failLength.get());
        assertFalse(configPage.memberPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.memberPhone4, phoneRaw.get());
        assertFalse(configPage.memberPhone4.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.memberPhone4.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.memberPhone4, phoneRaw_failAlpha.get());
        assertFalse(configPage.memberPhone4.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.memberPhone4.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone4, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.memberPhone4.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.memberPhone4.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone4, phoneRaw_failLength.get());
        assertFalse(configPage.memberPhone4.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberPhone4.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.memberPhone5, phoneRaw.get());
        assertFalse(configPage.memberPhone5.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.memberPhone5.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.memberPhone5, phoneRaw_failAlpha.get());
        assertFalse(configPage.memberPhone5.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.memberPhone5.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone5, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.memberPhone5.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.memberPhone5.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.memberPhone5, phoneRaw_failLength.get());
        assertFalse(configPage.memberPhone5.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberPhone5.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.providerPhone1, phoneRaw.get());
        assertFalse(configPage.providerPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.providerPhone1.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.providerPhone1, phoneRaw_failAlpha.get());
        assertFalse(configPage.providerPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.providerPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone1, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.providerPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.providerPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone1, phoneRaw_failLength.get());
        assertFalse(configPage.providerPhone1.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.providerPhone1.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.providerPhone2, phoneRaw.get());
        assertFalse(configPage.providerPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.providerPhone2.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.providerPhone2, phoneRaw_failAlpha.get());
        assertFalse(configPage.providerPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.providerPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone2, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.providerPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.providerPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone2, phoneRaw_failLength.get());
        assertFalse(configPage.providerPhone2.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.providerPhone2.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        clearAndFillText(configPage.providerPhone3, phoneRaw.get());
        assertFalse(configPage.providerPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw.get()), "Fail:AutoFormat Missing");
        assertTrue(configPage.providerPhone3.getAttribute("value").equalsIgnoreCase(phoneFormatted.get()));
        clearAndFillText(configPage.providerPhone3, phoneRaw_failAlpha.get());
        assertFalse(configPage.providerPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failAlpha.get()), "Fail:IncorrectCharacterPresent-Alphabetic");
        assertTrue(configPage.providerPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone3, phoneRaw_failSpcCharacters.get());
        assertFalse(configPage.providerPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failSpcCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.providerPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFailed.get()));
        clearAndFillText(configPage.providerPhone3, phoneRaw_failLength.get());
        assertFalse(configPage.providerPhone3.getAttribute("value").equalsIgnoreCase(phoneRaw_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.providerPhone3.getAttribute("value").trim().equalsIgnoreCase(phoneFormatted.get()));

        /*  names */
        clearAndFillText(configPage.contactFirstName, name_alpha15_failLength.get());
        assertFalse(configPage.contactFirstName.getAttribute("value").equalsIgnoreCase(name_alpha15_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.contactFirstName.getAttribute("value").trim().equalsIgnoreCase(name_alpha15.get()));
        clearAndFillText(configPage.contactFirstName, name_alpha15_failSpecialCharacters.get());
        assertFalse(configPage.contactFirstName.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.contactFirstName.getAttribute("value").trim().equalsIgnoreCase(name_alpha15Failed.get()));
        clearAndFillText(configPage.contactFirstName, name_alpha15_failNums.get());
        assertFalse(configPage.contactFirstName.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:IncorrectCharacterPresent-Numeric");
        assertTrue(configPage.contactFirstName.getAttribute("value").trim().equalsIgnoreCase(name_alpha15Failed.get()));
        clearAndFillText(configPage.contactLastName, name_alpha15.get());
        assertTrue(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));

        clearAndFillText(configPage.contactLastName, name_alpha15_failLength.get());
        assertFalse(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        clearAndFillText(configPage.contactLastName, name_alpha15_failSpecialCharacters.get());
        assertFalse(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:IncorrectCharacterPresent-SpecialCharacter");
        assertTrue(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15Failed.get()));
        clearAndFillText(configPage.contactLastName, name_alpha15_failNums.get());
        assertFalse(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15_failSpecialCharacters.get()), "Fail:IncorrectCharacterPresent-Numeric");
        assertTrue(configPage.contactLastName.getAttribute("value").trim().equalsIgnoreCase(name_alpha15Failed.get()));
        clearAndFillText(configPage.contactLastName, name_alpha15.get());
        assertTrue(configPage.contactLastName.getAttribute("value").equalsIgnoreCase(name_alpha15.get()));
        /*    url   */
        clearAndFillText(configPage.memberServiceURL, urlFileExLc_alphaNumSpcCharcters70.get());
        assertTrue(configPage.memberServiceURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        clearAndFillText(configPage.memberServiceURL, urlFileExLc_alphaNumSpcCharcters70_failLength.get());
        assertFalse(configPage.memberServiceURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.memberServiceURL.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        /*  fileExLoc */
        clearAndFillText(configPage.fileLocation, urlFileExLc_alphaNumSpcCharcters70.get());
        assertTrue(configPage.fileLocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        clearAndFillText(configPage.fileLocation, urlFileExLc_alphaNumSpcCharcters70_failLength.get());
        assertFalse(configPage.fileLocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.fileLocation.getAttribute("value").equalsIgnoreCase(urlFileExLc_alphaNumSpcCharcters70.get()));
        /*  email  */
        clearAndFillText(configPage.contactEmail, email_alphaNumSpcCharcters50.get());
        assertTrue(configPage.contactEmail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50.get()));
        clearAndFillText(configPage.contactEmail, email_alphaNumSpcCharcters50_failLength.get());
        assertFalse(configPage.contactEmail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50_failLength.get()), "Fail:IncorrectLength");
        assertTrue(configPage.contactEmail.getAttribute("value").equalsIgnoreCase(email_alphaNumSpcCharcters50.get()));

    }
}


