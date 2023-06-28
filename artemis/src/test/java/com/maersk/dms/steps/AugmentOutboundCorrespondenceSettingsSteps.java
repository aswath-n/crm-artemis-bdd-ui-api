package com.maersk.dms.steps;


import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.AugmentOutboundCorrespondenceSettingsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.ApiBase.API_THREAD_LOCAL_FACTORY;

/*
author sean thorson
 */
public class AugmentOutboundCorrespondenceSettingsSteps extends BrowserUtils {
    final ThreadLocal<String> CorrespondenceEnddate = ThreadLocal.withInitial(String::new);
    public AugmentOutboundCorrespondenceSettingsPage correspondenceSettingsPage = new AugmentOutboundCorrespondenceSettingsPage();
    private final ThreadLocal<String> userName = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    private final ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));
    public static ThreadLocal<Map<String, String>> save = ThreadLocal.withInitial(HashMap::new);

    @And("I navigate to the Correspondence Outbound Configuration Screen of Project:{string}")
    public void i_navigate_to_the_Correspondence_Outbound_Configuration_Screen_of_Project(String string) {
        correspondenceSettingsPage.leftDrawer.click();
        waitFor(5);

        correspondenceSettingsPage.outBoundSettingsLink.click();
    }

    @Then("Navigate to correspondence outbound defination page")
    public void navigate_to_correspondence_outbound_defination_page() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(5);
        jsClick(correspondenceSettingsPage.outBounddefinationLink);
    }

    @Then("Navigate to correspondence outbound Configuration page")
    public void navigate_to_correspondence_outbound_Configuration_page() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(5);
        jsClick(correspondenceSettingsPage.outBoundSettingsLink);

        waitFor(5);
    }

    @Given("Click at edit button")
    public void click_at_edit_btton() {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(correspondenceSettingsPage.editsettings, 15);
        jsClick(correspondenceSettingsPage.editsettings);

    }

    @Then("Validate the all settings fields for length")
    public void validate_the_all_settings_fiels() {
        String doctype_alphaNum50_failLength = "jkasdhasjkdhasjkdhkasjkdhasjkdhjaksjdhasj98786786khjjhjbjhbjhgjhgjhgbjhddsfsdfjdfkjdfjk";
        clearAndFillText(correspondenceSettingsPage.ecmsoutboundCorrespondence, doctype_alphaNum50_failLength);
        Assert.assertFalse(correspondenceSettingsPage.ecmsoutboundCorrespondence.getAttribute("value").equalsIgnoreCase(doctype_alphaNum50_failLength), "Fail:Incorrect Length");
        String doctype_alphaNum_sithSpecial_pass = "abc_123$";
        clearAndFillText(correspondenceSettingsPage.ecmsoutboundCorrespondence, doctype_alphaNum_sithSpecial_pass);
        Assert.assertTrue(correspondenceSettingsPage.ecmsoutboundCorrespondence.getAttribute("value").equalsIgnoreCase(doctype_alphaNum_sithSpecial_pass), "Fail:Incorrect Length");


        String doctemplate_alphaNum50_failLength = "jkasdhasjkdhasjkdhkasjkdhasjkdhjaksjdhasj98786786khjjhjbjhbjhgjhgjhgbjhddsfsdfjdfkjdfjk";
        clearAndFillText(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, doctemplate_alphaNum50_failLength);
        Assert.assertFalse(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate.getAttribute("value").equalsIgnoreCase(doctemplate_alphaNum50_failLength), "Fail:Incorrect Length");

        String doctemplate_alphaNum_sithSpecial_pass = "abc_123$";
        clearAndFillText(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, doctype_alphaNum_sithSpecial_pass);
        Assert.assertTrue(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate.getAttribute("value").equalsIgnoreCase(doctype_alphaNum_sithSpecial_pass), "Fail:Incorrect Length");

        String filename_alphaNum15_failLength = "jkasdhasjkdhasjkdhkasjkdhasjkdhjaksjdhasj98786786khjjhjbjhbjhgjhgjhgbjhddsfsdfjdfkjdfjk";
        clearAndFillText(correspondenceSettingsPage.outboundFilenamePrefix, filename_alphaNum15_failLength);
        Assert.assertFalse(correspondenceSettingsPage.outboundFilenamePrefix.getAttribute("value").equalsIgnoreCase(filename_alphaNum15_failLength), "Fail:Incorrect Length");

        String filename_alphaNum_sithSpecial_pass = "abc_123-";
        clearAndFillText(correspondenceSettingsPage.outboundFilenamePrefix, filename_alphaNum_sithSpecial_pass);
        Assert.assertTrue(correspondenceSettingsPage.outboundFilenamePrefix.getAttribute("value").equalsIgnoreCase(filename_alphaNum_sithSpecial_pass), "Fail:Incorrect Length");


        String phone_Num10_failLength = "123456789321";
        clearAndFillText(correspondenceSettingsPage.phoneNumber, phone_Num10_failLength);
        Assert.assertFalse(correspondenceSettingsPage.phoneNumber.getAttribute("value").equalsIgnoreCase(phone_Num10_failLength), "Fail:Incorrect Length");


        String fax_Num10_failLength = "123456789321";
        clearAndFillText(correspondenceSettingsPage.faxNumber, fax_Num10_failLength);
        Assert.assertFalse(correspondenceSettingsPage.faxNumber.getAttribute("value").equalsIgnoreCase(fax_Num10_failLength), "Fail:Incorrect Length");


        String mobileapp_alphaNum50_failLength = "jkasdhasjkdhasjkdhkasjkdhasjkdhjaksjdhasj98786786khjjhjbjhbjhgjhgjhgbjhddsfsdfjdfkjdfjk";
        clearAndFillText(correspondenceSettingsPage.mobileAppName, mobileapp_alphaNum50_failLength);
        Assert.assertFalse(correspondenceSettingsPage.mobileAppName.getAttribute("value").equalsIgnoreCase(mobileapp_alphaNum50_failLength), "Fail:Incorrect Length");


    }


    @Given("I will enter {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values")
    public void i_will_enter_fields_values(String SendCorrespondenceToAdultConsumersRegarding, String DoNotCombineRecipientsAtSameAddress, String FileNamePrefix, String ReplyEMail, String CustomerPhone, String CustomerFax, String CustomerEmail, String CustomerWeb, String MobileAppName, String PrintFileFormat, String ECMDocumentType, String TemplateECMDocumentType) {

        if (Boolean.valueOf(SendCorrespondenceToAdultConsumersRegarding) == Boolean.TRUE) {
            if (!correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }

        } else {
            if (correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }
        }
        if (Boolean.valueOf(DoNotCombineRecipientsAtSameAddress) == Boolean.TRUE) {

            if (!correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }

        } else {
            if (correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }
        }
//        selectRandomDropDownOption(correspondenceSettingsPage.fileFormat);
        selectDropDown(correspondenceSettingsPage.fileFormat, PrintFileFormat);
        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondence, ECMDocumentType);

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, TemplateECMDocumentType);

        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, FileNamePrefix);
        fillTheFiled(correspondenceSettingsPage.replyToEmailAddress, ReplyEMail);
        fillTheFiled(correspondenceSettingsPage.phoneNumber, CustomerPhone);
        fillTheFiled(correspondenceSettingsPage.faxNumber, CustomerFax);
        fillTheFiled(correspondenceSettingsPage.emailAddress, CustomerEmail);
        fillTheFiled(correspondenceSettingsPage.webAddress, CustomerWeb);
        fillTheFiled(correspondenceSettingsPage.mobileAppName, MobileAppName);

    }

    @Given("I will enter mandatory values {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values")
    public void i_will_enter_mandatory_values(String SendCorrespondenceToAdultConsumersRegarding, String DoNotCombineRecipientsAtSameAddress, String FileNamePrefix, String ReplyEMail, String CustomerPhone, String CustomerFax, String CustomerEmail, String CustomerWeb, String MobileAppName, String PrintFileFormat, String ECMDocumentType, String TemplateECMDocumentType) {
        // Write code here that turns the phrase above into concrete actions


        if (Boolean.valueOf(SendCorrespondenceToAdultConsumersRegarding) == Boolean.TRUE) {
            if (!correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }

        } else {
            if (correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }
        }
        if (Boolean.valueOf(DoNotCombineRecipientsAtSameAddress) == Boolean.TRUE) {

            if (!correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }

        } else {
            if (correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }
        }

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondence, ECMDocumentType);

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, TemplateECMDocumentType);

        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, FileNamePrefix);
        waitFor(10);
        //    cp_2929page.outboundFilenamePrefix.clear();
        correspondenceSettingsPage.outboundFilenamePrefix.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        //  cp_2929page.replyToEmailAddress.clear();
        correspondenceSettingsPage.replyToEmailAddress.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        //cp_2929page.phoneNumber.clear();
        correspondenceSettingsPage.phoneNumber.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        //     cp_2929page.faxNumber.clear();
        correspondenceSettingsPage.faxNumber.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        //     cp_2929page.emailAddress.clear();
        correspondenceSettingsPage.emailAddress.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        //     cp_2929page.mobileAppName.clear();
        correspondenceSettingsPage.mobileAppName.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        correspondenceSettingsPage.webAddress.sendKeys(Keys.CONTROL, "a", Keys.DELETE);

    }


    @Given("I will enter all except doc type {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values")
    public void i_will_enter_fields_values_except_doctype(String SendCorrespondenceToAdultConsumersRegarding, String DoNotCombineRecipientsAtSameAddress, String FileNamePrefix, String ReplyEMail, String CustomerPhone, String CustomerFax, String CustomerEmail, String CustomerWeb, String MobileAppName, String PrintFileFormat, String ECMDocumentType, String TemplateECMDocumentType) {
        // Write code here that turns the phrase above into concrete actions


        if (Boolean.valueOf(SendCorrespondenceToAdultConsumersRegarding) == Boolean.TRUE) {
            if (!correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }

        } else {
            if (correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }
        }
        if (Boolean.valueOf(DoNotCombineRecipientsAtSameAddress) == Boolean.TRUE) {

            if (!correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }

        } else {
            if (correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }
        }

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondence, ECMDocumentType);
        waitFor(2);
        //   cp_2929page.ecmsoutboundCorrespondence.clear();
        correspondenceSettingsPage.ecmsoutboundCorrespondence.sendKeys(Keys.CONTROL, "a", Keys.DELETE);

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, TemplateECMDocumentType);
        waitFor(5);
        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, FileNamePrefix);
        fillTheFiled(correspondenceSettingsPage.replyToEmailAddress, ReplyEMail);
        fillTheFiled(correspondenceSettingsPage.phoneNumber, CustomerPhone);
        fillTheFiled(correspondenceSettingsPage.faxNumber, CustomerFax);
        fillTheFiled(correspondenceSettingsPage.emailAddress, CustomerEmail);
        fillTheFiled(correspondenceSettingsPage.webAddress, CustomerWeb);
        fillTheFiled(correspondenceSettingsPage.mobileAppName, MobileAppName);
        //  fillTheFiled(cp_2929page.fileFormat, PrintFileFormat);
        //   fillTheFiled(cp_2929page.ecmsoutboundCorrespondence, ECMDocumentType);
        // fillTheFiled(cp_2929page.ecmsoutboundCorrespondenceTemplate, TemplateECMDocumentType);
    }

    @Given("I will enter all except doc template {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values")
    public void i_will_enter_fields_values_except_doctemplate(String SendCorrespondenceToAdultConsumersRegarding, String DoNotCombineRecipientsAtSameAddress, String FileNamePrefix, String ReplyEMail, String CustomerPhone, String CustomerFax, String CustomerEmail, String CustomerWeb, String MobileAppName, String PrintFileFormat, String ECMDocumentType, String TemplateECMDocumentType) {
        // Write code here that turns the phrase above into concrete actions

        if (Boolean.valueOf(SendCorrespondenceToAdultConsumersRegarding) == Boolean.TRUE) {
            if (!correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }

        } else {
            if (correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.isSelected()) {
                correspondenceSettingsPage.sendCorrespondenceToAdultConsumersRegarding.click();
            }
        }
        if (Boolean.valueOf(DoNotCombineRecipientsAtSameAddress) == Boolean.TRUE) {

            if (!correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }

        } else {
            if (correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.isSelected()) {
                correspondenceSettingsPage.donotCombineRecipientsAtSameAddress.click();
            }
        }

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondence, ECMDocumentType);

        fillTheFiled(correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate, TemplateECMDocumentType);
        waitFor(2);
        correspondenceSettingsPage.ecmsoutboundCorrespondenceTemplate.sendKeys(Keys.CONTROL, "a", Keys.DELETE);

        waitFor(2);
        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, FileNamePrefix);
        fillTheFiled(correspondenceSettingsPage.replyToEmailAddress, ReplyEMail);
        fillTheFiled(correspondenceSettingsPage.phoneNumber, CustomerPhone);
        fillTheFiled(correspondenceSettingsPage.faxNumber, CustomerFax);
        fillTheFiled(correspondenceSettingsPage.emailAddress, CustomerEmail);
        fillTheFiled(correspondenceSettingsPage.webAddress, CustomerWeb);
        fillTheFiled(correspondenceSettingsPage.mobileAppName, MobileAppName);

    }


    @And("Click at Save button")
    public void Click_at_Save() {
        // Write code here that turns the phrase above into concrete actions
        jsClick(correspondenceSettingsPage.savesettings);

    }

    @Then("Verify the success message {string}")
    public void verify_the_success_message(String success) {
        // Write code here that turns the phrase above into concrete actions
        waitForVisibility(correspondenceSettingsPage.successmessage, 2);
        Assert.assertEquals(correspondenceSettingsPage.successmessage.getText(), success);
    }

    @Then("Verify that this error message is showing {string}")
    public void verify_that_outbound_Correspondence_error_message_is_showing(String error) {
        waitFor(3);
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(correspondenceSettingsPage.errormessage.getText(), error);

    }

    @When("the settings are made available to a microservice interested in them")
    public void the_settings_are_made_available_to_a_microservice_interested_in_them() {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("I should see the value that is saved for the {string} \\(boolean)")
    public void i_should_see_the_value_that_is_saved_for_the_boolean(String SendCorrespondenceToAdultConsumersRegarding) {
        // Write code here that turns the phrase above into concrete actions
        waitFor(10);
        Boolean b = Boolean.valueOf(SendCorrespondenceToAdultConsumersRegarding);

        Boolean bol = correspondenceSettingsPage.verifysendCorrespondenceToAdultConsumersRegarding.isSelected();
        Boolean.compare(bol, b);

    }

    @Then("I should see the value that is saved for the {string} DoNotCombineRecipientsAtSameAddress")
    public void i_should_see_the_value_that_is_saved_for_the_DoNotCombineRecipientsAtSameAddress(String DoNotCombineRecipientsAtSameAddress) {
        // Write code here that turns the phrase above into concrete actions
        waitFor(10);
        Boolean b = Boolean.valueOf(DoNotCombineRecipientsAtSameAddress);

        Boolean bol = correspondenceSettingsPage.verifyDoNotCombineRecipientsAtSameAddress.isSelected();
        Boolean.compare(bol, b);

    }


    @Then("I should see the value that is saved for the {string} FileNamePrefix")
    public void i_should_see_the_value_that_is_saved_for_the_text_prefix(String Filename_Prefix) {
        Assert.assertEquals(correspondenceSettingsPage.verifyoutboundFilenamePrefix.getText(), Filename_Prefix);

    }

    @Then("I should see the value that is saved for the {string} DocType")
    public void i_should_see_the_value_that_is_saved_for_the_text_doc_type(String DocType) {
        // Write code here that turns the phrase above into concrete actio
        Assert.assertEquals(correspondenceSettingsPage.verifydoctype.getText(), DocType);

    }

    @Then("I should see the value that is saved for the {string} TemplateType")
    public void i_should_see_the_value_that_is_saved_for_the_text_temp_type(String TemplateType) {
        // Write code here that turns the phrase above into concrete action_

        Assert.assertEquals(correspondenceSettingsPage.verifytemplatetype.getText(), TemplateType);

    }

    @Then("I should see the value that is saved for the  {string} \\(email)")
    public void i_should_see_the_value_that_is_saved_for_the_email(String replytoemail) {
        Assert.assertEquals(correspondenceSettingsPage.verifyreplyToEmailAddress.getText(), replytoemail);
    }

    @Then("I should see the value that is saved for the {string} \\(phone)")
    public void i_should_see_the_value_that_is_saved_for_the_phone(String phone) {
        Assert.assertEquals(correspondenceSettingsPage.verifyphoneno.getText(), phone);


    }

    @Then("I should see the value that is saved for the {string} \\(fax)")
    public void i_should_see_the_value_that_is_saved_for_the_fax(String fax) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(correspondenceSettingsPage.verifyfaxno.getText(), fax);

    }

    @Then("I should see the value that is saved for the {string} \\(customer email)")
    public void i_should_see_the_value_that_is_saved_for_the_customer_email(String custemail) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(correspondenceSettingsPage.verifyemailAddress.getText(), custemail);

    }

    @Then("I should see the value that is saved for the {string} \\(URL text)")
    public void i_should_see_the_value_that_is_saved_for_the_URL_text(String web) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(correspondenceSettingsPage.verifywebaddress.getText(), web);


    }

    @Then("I should see the value that is saved for the {string} Mobile App Name")
    public void i_should_see_the_value_that_is_saved_for_the_mobile_app_name(String app_name) {
        // Write code here that turns the phrase above into concrete action_

        Assert.assertEquals(correspondenceSettingsPage.verifyMobileappname.getText(), app_name);

    }

    @Then("I should see the value that is saved for the {string} format")
    public void i_should_see_the_value_that_is_saved_for_the_format(String file_format) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(correspondenceSettingsPage.verifyfileformat.getText(), file_format);


    }

    @Given("I enter a mix of {int} letters, numbers, and hyphens in Filename Prefix field")
    public void i_enter_a_mix_of_letters_numbers_and_hyphens_in_Filename_Prefix_field(Integer int1) {
        // Write code here that turns the phrase above into concrete action
        waitFor(5);

        scrollUpRobot();
        waitFor(5);
        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, generateRandomSpecialrChars2(7) + "-" + generateRandomSpecialrChars2(7));


    }

    @Given("And I enter a mix of {int} special characters except hyphens")
    public void and_I_enter_a_mix_of_special_characters_except_hyphens(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        waitFor(5);
        scrollUpRobot();
        waitFor(5);
        fillTheFiled(correspondenceSettingsPage.outboundFilenamePrefix, generateRandomSpecialrChars(7) + generateRandomSpecialrChars(1) + generateRandomSpecialrChars(7));
    }
// @keerthi


    @Given("I entered {string} data in the TTY Number field")
    public void enter_TTY_Number_field(String data) {
        waitFor(3);
        correspondenceSettingsPage.ttynumber.sendKeys(Keys.CONTROL, Keys.BACK_SPACE);
        waitFor(2);
        correspondenceSettingsPage.ttynumber.sendKeys(data);
        waitFor(2);

    }

    @Given("I validate {string} data in the TTY Number field")
    public void validate_TTY_Number_field(String actual_TTYNum) {
        waitFor(3);
        String expected_TTYNum = correspondenceSettingsPage.ttynumber.getAttribute("value");
        Assert.assertEquals(expected_TTYNum, actual_TTYNum);

    }

    @Given("I validate warning message for TTY Number field format")
    public void validate_TTY_Number_field_warning_message() {
        waitFor(3);
        correspondenceSettingsPage.ttynumber.sendKeys(Keys.CONTROL, Keys.BACK_SPACE);
        waitFor(2);
        correspondenceSettingsPage.ttynumber.sendKeys("9123456");
        correspondenceSettingsPage.savesettings.click();
        waitFor(2);
        String act_msg = "tty Number format is invalid. Please enter it in format ##########";
        String exp_msg = correspondenceSettingsPage.ttynumber_errormsg.getText();
        Assert.assertEquals(exp_msg, act_msg);
    }

    @Given("I validate OUTBOUND PRINT FILE FTP DETAILS are removed")
    public void validate_print_FTP_removed() {
        try {
            correspondenceSettingsPage.ftpheader.isDisplayed();
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Given("I validate SMTP Email {string} field with data {string}")
    public void validate_SMTP_email_field_valid_data(String type, String data) throws Exception {
        switch (type.toLowerCase()) {
            case "user":
                correspondenceSettingsPage.smtpemailuser.sendKeys(Keys.CONTROL, Keys.BACK_SPACE);
                correspondenceSettingsPage.smtpemailuser.sendKeys(data);
                break;
            case "password":
                correspondenceSettingsPage.smtppassworduser.sendKeys(Keys.CONTROL, Keys.BACK_SPACE);
                correspondenceSettingsPage.smtppassworduser.sendKeys(data);
                break;
            default:
                throw new Exception(" not valid field : " + type);

        }
        waitFor(1);
        correspondenceSettingsPage.savesettings.click();

    }

    @Given("I validate SMTP Email {string} field with invalid data")
    public void validate_SMTP_email_field_invalid_data(String type) throws Exception {
        switch (type.toLowerCase()) {
            case "user":
                waitFor(3);
                boolean eb = correspondenceSettingsPage.smtpemailuser.getAttribute("value").contains(" ");
                Assert.assertTrue(!eb);
                break;
            case "password":
                waitFor(3);
                boolean pb = correspondenceSettingsPage.smtppassworduser.getAttribute("value").contains(" ");
                Assert.assertTrue(!pb);
                break;
            default:
                throw new Exception(" not valid field : " + type);

        }

    }

    @Given("I validate Receipent Setting field label")
    public void validate_Receipent_Setting_field_label() {
        waitFor(3);
        String expected_recipient_labelText = "RECIPIENT SETTINGS";
        String actual_recipient_labelText = correspondenceSettingsPage.recipientsettings_label.getText();
        Assert.assertEquals(actual_recipient_labelText, expected_recipient_labelText);

    }

    @Given("I validate update By field value")
    public void validate_updatedBy_field_value() {
        waitFor(3);
        String expected_updatedBy = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId;
        String actual_updatedBy = Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + expected_updatedBy + "')]")).getText();
        Assert.assertEquals(actual_updatedBy, expected_updatedBy);

    }

    @Given("I validate update On field value")
    public void validate_updatedOn_field_value() {
        waitFor(3);
        String expected_updatedBy = correspondenceSettingsPage.topheader_date.getText() + " " + correspondenceSettingsPage.topheader_time.getText();
        String actual_updatedBy = Driver.getDriver().findElement(By.xpath("//h6[contains(text(),'" + expected_updatedBy + "')]")).getText();
        Assert.assertEquals(actual_updatedBy, expected_updatedBy);

    }
}
