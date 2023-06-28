package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMApplicationTrackingPage;
import com.maersk.crm.pages.crm.CRMCreateApplicationPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class CRM_WithdrawnApplicationConfiguringPermissionsSetpDef extends CRMUtilities implements ApiBase {
    CRMApplicationTrackingPage applicationTrackingPage = new CRMApplicationTrackingPage();
    CRMCreateApplicationPage createApplicationPage = new CRMCreateApplicationPage();

    public static final ThreadLocal<String> alreadyReceivingServiceReason = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> notInterestedInServicesReason = ThreadLocal.withInitial(String::new);

    @Then("I verify I see Withdraw button displayed in Application Information panel")
    public void i_verify_I_see_Withdraw_button_displayed_in_Application_Information_panel() {

        Assert.assertTrue(applicationTrackingPage.withdrawLink.isDisplayed(), "Withdraw link is not displayed");
    }

    @Then("I click withdraw dropdown to withdraw the application from the Application Tracking tab")
    public void i_click_withdraw_dropdown_to_withdraw_the_application_from_the_Application_Tracking_tab() {
        scrollUpRobot();
        jsClick(applicationTrackingPage.withdrawLink);
        //   applicationTrackingPage.withdrawLink.click();
    }

    @Then("I see APPLICATION WITHDRAW REASON in Application Information panel")
    public void i_see_APPLICATION_WITHDRAW_REASON_in_Application_Information_panel() {
        Assert.assertTrue(applicationTrackingPage.applicationWithdrawReasonText.isDisplayed(), "application Withdraw Reason not displayed");
    }

    @Then("I  select {string} from select reason dropdown")
    public void i_select_from_select_reason_dropdown(String reason) {
        waitFor(5);
        applicationTrackingPage.applicationWithdrawReasonDropdown.click();
        if (reason.equalsIgnoreCase("Already Receiving Services")) {
            applicationTrackingPage.withdrawReasonList.get(1).click();
            alreadyReceivingServiceReason.set(reason);
        } else if (reason.equalsIgnoreCase("Not Interested in Services")) {
            applicationTrackingPage.withdrawReasonList.get(2).click();
            notInterestedInServicesReason.set(reason);
        } else {
            throw new RuntimeException("Unsupported reason");
        }
    }


    @Then("I click save button in the select reason")
    public void i_click_save_buttonin_the_select_reason() {
        applicationTrackingPage.withdrawSaveButton.click();
    }


    @Then("I verify I see warning message {string}")
    public void i_verify_I_see_warning_message(String expectedWarning) {
        waitFor(2);
        String actualWarning = applicationTrackingPage.warningMessageForWithdaw.getText();
        Assert.assertEquals(actualWarning, expectedWarning, "Warning message do not match");
    }

    @When("I click Continue button inside the warning message")
    public void i_click_Continue_button_inside_the_warning_message() {
        BrowserUtils.waitFor(2);
        applicationTrackingPage.withdrawContinueButtonInsideWarning.click();
        waitFor(10);
    }

    @When("I click Cancel button inside the warning message")
    public void i_click_Cancel_button_inside_the_warning_message() {
        applicationTrackingPage.withdrawCancelButtonInsideWarning.click();
    }

    @Then("I see these options as reasons for withdrawing:")
    public void i_see_these_options_as_reasons_for_withdrawing(List<List<String>> data) {
        waitForClickablility(applicationTrackingPage.applicationWithdrawReasonDropdown, 8);
        applicationTrackingPage.applicationWithdrawReasonDropdown.click();

        List<WebElement> actualData = applicationTrackingPage.withdrawReasonList;
        List<String> expectedData = data.get(0);
        expectedData.add(0, "");

        for (int i = 1; i < actualData.size(); i++) {
            Assert.assertEquals(actualData.get(i).getText(), expectedData.get(i));
        }
    }

    @When("I click on the cancel button in the select reason")
    public void i_click_on_the_cancel_button_in_the_select_reason() {
        applicationTrackingPage.withdrawCancelButton.click();
    }

    @Then("the Application Withdraw Reason panel will be cleared and the Withdraw button will be Active")
    public void the_Application_Withdraw_Reason_panel_will_be_cleared_and_the_Withdraw_button_will_be_Active() {
        List<WebElement> list = Driver.getDriver().findElements(By.xpath("//h5[.='APPLICATION WITHDRAW REASON']"));
        Assert.assertEquals(list.size(), 0, "application Withdraw Reason is still displayed");
        String disabled = applicationTrackingPage.withdrawLink.getAttribute("disabled");
        System.out.println("Disabled: " + disabled);
        // if null then button is available
        Assert.assertNull(disabled, "WITHDRAW is not available");
    }

    @And("I verify applicant consumers status is changed to {string}")
    public void iVerifyApplicantConsumersStatusIsChangedTo(String statusType) {
        for (int i = 0; i < applicationTrackingPage.applicantStatusValues.size(); i++) {
            Assert.assertEquals(applicationTrackingPage.applicantStatusValues.get(i).getText(), statusType, "Expected MEMBER(S) INFO panel Applicant status to be: " + statusType + " But found: " + applicationTrackingPage.applicantStatusValues.get(i).getText());
        }
    }

    @Then("with this role I was able to withdraw application {string}")
    public void with_this_role_I_was_able_to_withdraw_application(String isAbleToWithdraw) {
        boolean ableToWithdraw = Boolean.parseBoolean(isAbleToWithdraw);

        if (ableToWithdraw) {
            // withdrawing steps
            i_click_withdraw_dropdown_to_withdraw_the_application_from_the_Application_Tracking_tab();
            i_see_APPLICATION_WITHDRAW_REASON_in_Application_Information_panel();
            i_select_from_select_reason_dropdown("Already Receiving Services");
            i_click_save_buttonin_the_select_reason();
            waitFor(2);
            i_verify_I_see_warning_message("Are you sure you want to Withdraw this Application?");
            i_click_Continue_button_inside_the_warning_message();
            waitFor(2);
            new CRMApplicationTrackingStepDef().i_see_application_Status_as_in_the_application_information("WITHDRAWN");
        } else {
            // verify withdraw is disabled
            new CRMApplicationTrackingStepDef().i_verify_I_see_Withdraw_button_displayed_and_disabled_in_Application_Information_panel();
        }
    }
}