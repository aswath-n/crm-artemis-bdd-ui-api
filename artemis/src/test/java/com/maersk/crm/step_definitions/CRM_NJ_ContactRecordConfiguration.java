package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.InboundCorrespondencePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 6/8/2020
 */
public class CRM_NJ_ContactRecordConfiguration extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();

    public final ThreadLocal<String> userNameData = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("login"));
    public ThreadLocal<String> password = ThreadLocal.withInitial(() -> ConfigurationReader.getProperty("password"));
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();
    CRMThirdPartyContactRecPage contactRecordTP = new CRMThirdPartyContactRecPage();
    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    private final ThreadLocal<String> tPPhone = ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> tPFirstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> tPLastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();
    Actions actions = new Actions(Driver.getDriver());


    @Given("I logged into CRM choosing project {string} click on initiate contact")
    public void i_logged_into_CRM_choosing_project_and_click_on_initiate_contact(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData.get(), password.get(), projectName);
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 10);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());
    }

    @Then("Verify I see in Active Contact page")
    public void verifyISeeInActiveContactPage(List<String> tabs) {
        waitFor(2);
        for (String tab : tabs) {
            if (tab.equalsIgnoreCase("GENERAL CONTACT")) {
                Assert.assertEquals(activeContact.genCon.getText(), tab);
            } else if (tab.equalsIgnoreCase("THIRD PARTY CONTACT")) {
                Assert.assertEquals(activeContact.thirdPartyContactTab.getText(), tab);
            } else if (tab.equalsIgnoreCase("UNIDENTIFIED CONTACT")) {
                Assert.assertEquals(activeContact.unidentfied.getText(), tab);
            } else {
                throw new CucumberException("Tab " + tab + " Not found");
            }
        }
    }

    @And("I will see INBOUND CALL QUEUE with options")
    public void iWillSeeINBOUNDCALLWithOptions(List<String> options) {
        for (String opt : options) {
            waitFor(1);
            selectDropDown(activeContact.inboundCallQueue, opt);
        }
    }

    @And("I will verify the PROGRAM options {string}")
    public void iWillVerifyThePROGRAMOptions(String options) {
        selectDropDown(activeContact.programs, options);
    }

    @Then("Verify Call Campaign has values")
    public void verifyCallCampaignValues(List<String> call) {
        for (String opt : call) {
            if (opt.equalsIgnoreCase("DMI")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Manual Outbound Call")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Verification Documents")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else {
                throw new CucumberException("Call Campaign Option " + opt + " Not found");
            }
        }
    }

    @Then("Verify disposition field values are exist")
    public void verifyDispositionFieldValuesAreExist(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Cancelled")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Complete")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Dropped")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Escalate")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Requested Call Back")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Outbound Incomplete")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Transfer")) {
                selectDropDown(activeContact.disposition, opt);
            } else if (opt.equalsIgnoreCase("Voicemail")) {
                selectDropDown(activeContact.disposition, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @And("I verify Contact Reason and associated Contact Action for NJ-SBE")
    public void iVerifyContactReasonAndAssociatedContactActionForNJ(List<String> options) {

        for (String opt : options) {
            if (opt.equalsIgnoreCase("Appeal")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Follow Up",
                        "Unable to Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Application")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "New Application - Incomplete",
                        "New Application - Complete",
                        "Update Application - Incomplete",
                        "Update Application - Complete",
                        "Consumer Failed Identity Proofing",
                        "Advised of OEP/SEP timeframe",
                        "Unable to Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Broker Support")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Certification Status",
                        "Contact Information Update",
                        "Referred to GCNJ email",
                        "Referred to LMS Support",
                        "Training Question",
                        "Unable To Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Change of Email or USPS Address")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Unable to Resolve Issue",
                        "Updated Address");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Complaint")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Created Review Complaint Task",
                        "Escalated to Supervisor",
                        "Referred to Third Party",
                        "Repeat Callers",
                        "Resolved");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("DMI")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Verification Document Valid",
                        "Unable to Resolve Issue",
                        "Verification Document Invalid");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Inbound Document Inquiry")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Resolved issue",
                        "Unable to Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Miscellaneous Inquiry")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Referred to Broker",
                        "Referred to Carrier",
                        "Referred to Exchange Org",
                        "Referred to FFM",
                        "Referred to Medicaid",
                        "Referred to Treasury",
                        "Other",
                        "Direct Transfer to NJFC SBE Unit");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Technical Assistance/Password Reset")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Provided General Technical Assistance",
                        "Sent Activation Link",
                        "Sent Password Reset Link",
                        "Unable To Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Enrollment, Disenrollment, Transfer")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Backdating Enrollment Request",
                        "Backdating Subsidies Request",
                        "Completed Plan Selection",
                        "Disenrollment Request",
                        "Plan Change Request",
                        "Reinstatement Request",
                        "Retro Disenrollment Request",
                        "Unable To Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("General Program Information")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Resolved");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("New Consumer")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Completed Application",
                        "Created Account");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Referral")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Direct Transfer to NJFC SBE Unit",
                        "Referred to Broker or Assister",
                        "Referred to Carrier",
                        "Referred to FFM",
                        "Referred to Medicaid",
                        "Referred to Other State/Federal Agency",
                        "Referred to Treasury");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Status")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Unable To Resolve Issue",
                        "Verify Enrollment Status",
                        "Verify Premium/Subsidy Status",
                        "Verify Recent Change to Application");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Update Application")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Address or Email Change",
                        "Coverage Change",
                        "Household Change",
                        "Income Change",
                        "Other Change",
                        "Report QLE",
                        "Unable To Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Verification Document Inquiry")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "DMI Ticket",
                        "Other Document",
                        "QLE Ticket",
                        "RIDP",
                        "Unable To Resolve Issue");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            } else if (opt.equalsIgnoreCase("Loss of NJFC - Transferred") ||
                       opt.equalsIgnoreCase("Loss of NJFC - Not Transferred")) {
                selectDropDown(activeContact.contactRes, opt);
                List<String> complaintActions = Arrays.asList(
                        "Backdate Enrollment",
                        "Complete Plan Selection",
                        "Disenrollment",
                        "General Inquiry",
                        "New Application Request",
                        "Retro Disenrollment",
                        "Unable to Resolve",
                        "Update Application");
                verifyMultiSelectDropDownValues("contactAction", complaintActions);
            }
        }
    }

    @Then("I navigate to Third Party page")
    public void iNavigateToThirdPartyPage() {
        waitForClickablility(activeContact.thirdPartyContactTab, 10);
        waitFor(1);
        scrollToElement(activeContact.thirdPartyContactTab);
        scrollUpUsingActions(1);
        jsClick(activeContact.thirdPartyContactTab);
    }

    @And("Verify consumer Type field with the options")
    public void verifyConsumerTypeFieldWithTheOptions(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Agency")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Assister")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Authorized Representative")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Broker")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Carrier")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Consumer")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Media")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Navigator")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Provider")) {
                selectDropDown(activeContact.consumerType, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("Verify Outcome of Contact field with the options")
    public void verifyOutcomeOfContactFieldWithTheOptions(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Did Not Reach/Left Voicemail")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Did not reach/No voicemail")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Invalid Phone Number")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Reached Successfully/spoke to CAC")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Reached Successfully/did not speak to CAC")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("Verify Outcome of Contact field with the options for NJ-SBE")
    public void verify_Outcome_of_Contact_field_with_the_options_for_NJ_SBE(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Did Not Reach/Left Voicemail")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Did not reach/No voicemail")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Invalid Phone Number")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Reached Successfully")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else if (opt.equalsIgnoreCase("Call Skipped, Issue Resolved")) {
                selectDropDown(activeContact.outcomeOfContact, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }


    @Then("I will see the Channel field with the options")
    public void iWillSeeTheChannelFieldWithTheOptions(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Phone")) {
                selectDropDown(activeContact.channelField, opt);
            }
            if (opt.equalsIgnoreCase("Email")) {
                selectDropDown(activeContact.channelField, opt);
            }
            if (opt.equalsIgnoreCase("Mobile App")) {
                selectDropDown(activeContact.channelField, opt);
            }
            if (opt.equalsIgnoreCase("SMS Text")) {
                selectDropDown(activeContact.channelField, opt);
            }
            if (opt.equalsIgnoreCase("Web Chat")) {
                selectDropDown(activeContact.channelField, opt);
            }
            if (opt.equalsIgnoreCase("Web Portal")) {
                selectDropDown(activeContact.channelField, opt);
            }
             else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I navigate to the case and contact details")
    public void iNavigateToTheCaseAndContactDetails() {
        waitFor(1);
        activeContact.caseContact.click();
        waitFor(2);
    }

    @And("Click contactId and edit contact")
    public void clickContactIdAndEditContact() {
        waitFor(2);
        scrollUpUsingActions(3);
        waitFor(1);
        activeContact.contactIdContactHis.click();
        activeContact.editContactContactDetails.click();
        waitFor(2);
    }

    @Then("Verify Reason for Edit options")
    public void verifyReasonForEditOptions(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Adding Additional Comment")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Did not reach/Left voicemail")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Additional Comment")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Adding Contact Reason/Action")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Contact Reason/Action")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Contact Details")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Case/Consumer Link")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Third Party Information")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I logged into CRM choosing project {string}")
    public void iLoggedIntoCRMChoosingProject(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        loginCRM(userNameData.get(), password.get(), projectName);
        waitFor(3);
        waitForVisibility(contactRecord.initContact, 10);

    }

    @When("I verify Preferred Language drop down with options below:")
    public void i_verify_Preferred_Language_drop_down_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("English")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Spanish")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Mandarin")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Portuguese")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Tagalog")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Italian")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Korean")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Gujarati")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Polish")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Hindi")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Arabic")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I search and validate&link consumer with fist name {string} and last name {string}")
    public void i_search_and_validate_link_consumer_with_fist_name_and_last_name(String firstname, String lastname) {
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.lastName, lastname);
        waitFor(5);
        click(contactRecord.searchButton);
        waitFor(6);

        waitForVisibility(contactRecord.expandFistConsumer, 10);
        contactRecord.expandFistConsumer.click();
        waitFor(3);
        contactRecord.thirdPartyConsumerRadioButton.click();
        waitFor(3);
        contactRecord.validateLinkButton.click();
    }

    @When("I create third party contact record")
    public void i_create_third_party_contact_record() {
        waitForVisibility(thirdPartyDetails.txtThirdPartyFirstName, 5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyFirstName, tPFirstName.get());
        waitForVisibility(thirdPartyDetails.txtThirdPartyLastName, 5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyLastName, tPLastName.get());
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, "tpOrg");
        selectDropDown(contactRecord.consumerType, "Agency");
        selectDropDown(contactRecord.preferredLanguage, "English");

        selectDropDown(contactRecordTP.trContactReason, "Appeal");
        waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecordTP.trContactAction, "Answered Inquiry");
        //   selectDropDown(contactRecordTP.trContactAction, "Escalated");
        //   contactRecordTP.contactMenuContactAction.click();
        waitFor(1);
        clearAndFillText(contactRecordTP.contactReasonComments, "New Reason Comment");
        contactRecord.reasonSaveButton.click();
        //   clearAndFillText(contactRecordTP.contactReasonAddComments, "New Additional Comment");
        //    contactRecord.contactAddCommentsSave.click();


        selectDropDown(contactRecord.callQueueType, "Inbound - English");
        selectDropDown(contactRecord.contactChannelType, "Phone");
        clearAndFillText(contactRecord.phoneNumber, tPPhone.get());
        System.out.println("phone third party " + tPPhone.get());
        selectDropDown(contactRecordTP.contactProgramTypes, "GetCovered NJ");
        contactRecordTP.contactMenuProgramTypes.click();
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecordTP.endContact.click();
        contactRecordTP.thirdPartySaveContact.click();
    }

    @When("I search and select third party contact record created above with phone number")
    public void i_search_and_select_third_party_contact_record_created_above_with_phone_number() {
        contactRecord.btnAdvancedSearch.click();
        waitFor(3);
        contactRecord.contactPhoneField.sendKeys(tPPhone.get());
        waitFor(3);
        contactRecord.searchButton.click();
        waitFor(5);
        contactRecord.contactIdFirstRecord.click();
    }

    @And("I click save button Active contact")
    public void iClickSaveButtonActiveContact() {
        waitFor(3);
        click(Driver.getDriver().findElement(By.xpath("//span[text()='Save']")));
    }

    @And("I click on CaseId on Links section")
    public void iClickOnCaseIdOnLinksSection() {
        waitFor(3);
        List<WebElement> els = Driver.getDriver().findElements(By.xpath("//td[text()='Case']/preceding-sibling::td"));
        if (els.size() == 3) {
            jsClick(els.get(2));
        } else {
            jsClick(els.get(1));
        }
        waitFor(3);
    }

    @Then("I verify Outbound Correspondence Component is hidden")
    public void iVerifyOutboundCorrespondenceComponentIsHidden() {
        Assert.assertFalse(isElementDisplayed(inboundCorrespondencePage.outboundCorrespondencesHeader));
    }

    @Then("I click on {string} Contact Type")
    public void i_click_on_Contact_Type(String type) {
        waitFor(2);
        selectDropDown(contactRecord.contactType, type);
        waitFor(1);
    }

    @Then("I navigate to {string} Contact page")
    public void iNavigateToContactPage(String page) {
        if (page.equalsIgnoreCase("ThirdParty")) {
            waitForClickablility(activeContact.thirdPartyContactTab, 10);
            waitFor(1);
            scrollToElement(activeContact.thirdPartyContactTab);
            scrollUpUsingActions(1);
            jsClick(activeContact.thirdPartyContactTab);
        } else if (page.equalsIgnoreCase("Unidentified")) {
            waitForClickablility(activeContact.unidentfied, 10);
            waitFor(1);
            scrollToElement(activeContact.unidentfied);
            scrollUpUsingActions(1);
            jsClick(activeContact.unidentfied);
        }else if (page.equalsIgnoreCase("General")){
            waitForClickablility(activeContact.genCon, 10);
            waitFor(1);
            scrollToElement(activeContact.genCon);
            scrollUpUsingActions(1);
            jsClick(activeContact.genCon);
        }
        else{
            throw new CucumberException("Page <" + page + "> Not found");
        }
    }
    @And("I click on ConsumerId on Links section")
    public void iClickOnConsumerIdOnLinksSection(){
        waitFor(3);
        List<WebElement> els = Driver.getDriver().findElements(By.xpath("//td[text()='Consumer']/preceding-sibling::td"));
        if (els.size() == 4) {
            jsClick(els.get(2));
        } else {
            jsClick(els.get(1));
        }
        waitFor(3);
    }

    @Then("Verify below reasons for edit values exist")
    public void verify_below_reasons_for_edit_values_exist(List <String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Adding Additional Comment")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Adding Contact Reason/Action")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Additional Comment")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Case/Consumer Link")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Contact Reason/Action")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Contact Details")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Third Party Information")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else if (opt.equalsIgnoreCase("Correcting Disposition")) {
                selectDropDown(activeContact.reasonForEditContactHis, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
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
        actions.moveToElement(contactRecord.generalContact).click().perform();
    }
}

