package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMEnrollmentUpdatePage;
import com.maersk.crm.pages.crm.CRMProgramAndBenefitInfoPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static org.testng.Assert.*;

public class CRM_ConsumerDetailPanelOnEnrollmentUpdateScreenStepDef extends CRMUtilities implements ApiBase {
    CRMEnrollmentUpdatePage enrollmentUpdatePage = new CRMEnrollmentUpdatePage();
    CRMProgramAndBenefitInfoPage programAndBenefitInfoPage = new CRMProgramAndBenefitInfoPage();
    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();


    public final ThreadLocal<String> name = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> age_gender = ThreadLocal.withInitial(String::new);


    @Then("I verify I am navigated/still to/on {string} Page")
    public void i_verify_I_am_navigated_to_Page(String page) {
        page = page.toUpperCase();
        try {
            switch (page) {
                case "ENROLLMENT OVERRIDE":
                    assertTrue(enrollmentUpdatePage.enrollmentOverridePageHead.isDisplayed());
                    break;
                case "ENROLLMENT UPDATE":
                    assertTrue(enrollmentUpdatePage.enrollmentUpdatePageHead.isDisplayed());
                    break;
                case "ENROLLMENT EDIT":
                    assertTrue(enrollmentUpdatePage.enrollmentEdit.isDisplayed());
                    break;
                case "PROGRAM & BENEFIT INFO":
                    assertTrue(programAndBenefitInfoPage.headTabProgramAndBenefit.isDisplayed());
                    break;
                case "CONTACT RECORD":
                    assertTrue(crmContactRecordUIPage.pageHeader.getAttribute("title").equalsIgnoreCase("CONTACT RECORD"),
                            "The Contact Record Page is not displayed");
                    break;
                case "CASE BENEFICIARY INFORMATION":
                    assertTrue(programAndBenefitInfoPage.programAndBenefitInfoHeader.isDisplayed());
                    break;
                default:
                    Assert.fail("Page is not defined");
            }
        } catch (Exception e) {
            fail("This is not " + page + " page! - FAIL!");
        }
        System.out.println("This is " + page + " page! - PASS!");
    }

    @Then("I verify all fields on the Consumer Details Panel are displayed")
    public void i_verify_all_fields_on_the_Consumer_Details_Panel_are_displayed() {
        scrollUpUsingActions(1);
        for (int i = 1; i < enrollmentUpdatePage.consumerPanelFieldsList.size() - 1; i++) {
            waitFor(2);
            assertTrue(enrollmentUpdatePage.consumerPanelFieldsList.get(i).isDisplayed());
        }
    }


    @Then("I verify the field {string} has same data as on the Case Beneficiary Information Page")
    public void i_verify_the_field_has_same_data_as_on_the_Case_Beneficiary_Information_Page(String fieldName) {
        fieldName = fieldName.toUpperCase();
        switch (fieldName) {
            case "NAME":
                assertEquals(name.get(), enrollmentUpdatePage.enrollmentUpdatePageBeneficiaryName.getText(),
                        "The name of the beneficiary does not much the name from Case Beneficiry Information Page");
                break;
            case "AGE/GENDER":
                assertEquals(enrollmentUpdatePage.enrolmentUpdatePageAgeGenderField.getText().replace("/", ""),
                        age_gender.get().replace("Yrs | ", ""),
                        "The age/gender field does not much the name from Case Beneficiry Information Page");
                break;
            case "RESIDENTIAL ADDRESS":
                assertEquals(enrollmentUpdatePage.enrolmentUpdatePageAddressField.getText(), "22 main st , Adairsville GA 30003-4345");
                break;
            default:
                Assert.fail("The field Name is not defined");
        }
    }

    @Then("I review Case Beneficiary Information")
    public void iReviewCaseBeneficiaryInformation() {
        name.set(programAndBenefitInfoPage.BeneficiaryName.get(0).getText());
        age_gender.set(programAndBenefitInfoPage.listOfYearsAndGenderOfBeneficiary.get(0).getText());
    }

    @Then("I verify the Eligibility Details date format is correct")
    public void i_verify_the_Eligibility_Details_date_format_is_correct() {
        assertTrue(isMMddYYYYformat(enrollmentUpdatePage.consumerPanelFieldsList.get(6).getText()));
    }

    @When("I navigate away by clicking on back arrow on Enrollment Update Page")
    public void i_navigate_away_by_clicking_on_back_arrow_on_Enrollment_Update_Page() {
        enrollmentUpdatePage.enrolmentUpdatePageArrowNavigateBack.click();
        System.out.println("Navigating away by clicking back button");
        waitFor(1);
    }

    @Then("I see warning message pop up with an option to cancel or continue")
    public void i_see_warning_message_pop_up_with_an_option_to_cancel_or_continue() {
        waitForVisibility(enrollmentUpdatePage.enrolmentUpdatePageWarningMessage, 7);
        assertTrue(enrollmentUpdatePage.enrolmentUpdatePageWarningMessage.isDisplayed(), "Warning message is not displayed");

    }

    @Then("I click on {string} on the warning message")
    public void i_click_on_on_the_warning_message(String button) {
        button = button.toUpperCase();
        switch (button) {
            case "CANCEL":
                enrollmentUpdatePage.listOfButtonsWarningMsg.get(1).click();
                waitFor(1);
                break;
            case "CONTINUE":
                enrollmentUpdatePage.listOfButtonsWarningMsg.get(0).click();
                waitFor(1);
                break;
            default:
                Assert.fail("Button is not defined");
        }
    }

    @When("I hover over the side bar and click on {string} field")
    public void i_hover_over_the_side_bar_and_click_on_field(String sideMenuOption) {
        sideMenuOption = sideMenuOption.toUpperCase();
        switch (sideMenuOption) {
            case "CASE/CONSUMER SEARCH":
                enrollmentUpdatePage.listSideBarMenu.get(1).click();
                break;
            case "CONTACT RECORD SEARCH":
                hover(enrollmentUpdatePage.listSideBarMenu.get(2));
                waitFor(1);
                enrollmentUpdatePage.listSideBarMenu.get(2).click();
                break;
        }

    }


    @Then("I verify plan selection reason is not displayed")
    public void iVerifyPlanSelectionNotDisplayed() {
        try {
            Assert.assertTrue(enrollmentUpdatePage.reasonDropDownBttn.isDisplayed());
            fail("Plan Selection Reason is displayed! - FAIL !");
        } catch (Exception e) {
            System.out.println("Plan Selection Reason is not displayed! - PASS !");
        }
    }


}
