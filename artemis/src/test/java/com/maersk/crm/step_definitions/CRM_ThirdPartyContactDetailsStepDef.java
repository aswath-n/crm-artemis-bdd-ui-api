package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.pages.crm.CRMThirdPartyContactRecPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

public class CRM_ThirdPartyContactDetailsStepDef extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMThirdPartyContactRecPage contactRecordTP = new CRMThirdPartyContactRecPage();
    CRMThirdPartyContactRecPage thirdPartyDetails = new CRMThirdPartyContactRecPage();

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() ->newConsumer.get().get("name").toString() );
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() ->newConsumer.get().get("surname").toString() );
    private final ThreadLocal<String> tPFirstName =ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> tPLastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> tPPhone=ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> phone =ThreadLocal.withInitial(() -> newConsumer.get().get("phone").toString());
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactChannelType = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactDispositionsValue = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactSearchRecordType = ThreadLocal.withInitial(String::new);

    @When("I create consumer for {string} Third Party Contact Record")
    public void createConsumerForThirdPartyContactRecord(String contactType) {
     //   contactRecord.initContact.click();
     //contactRecordTP.thirdPartyContactType.click();
       // assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName.get());
        clearAndFillText(contactRecord.lastName, lastName.get());
        consumerName.set(firstName.get() + " " + lastName.get());
        System.out.println(firstName.get() + " " + lastName.get());

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        scrollUpUsingActions(2);
        waitFor(1);
        clearAndFillText(createConsumer.consumerFN, firstName.get());
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, phone.get());
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        clearAndFillText(createConsumer.consumerDB, (newConsumer.get().get("birthday").toString()));
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        waitFor(2);
        createConsumer.createConsumerButton.click();
        try {
            createConsumer.optInWarningContinueButton.click();
        } catch (Exception e) {
            System.out.println("Click after continue button is not needed");
        }
        waitFor(1);

        staticWait(300);
        //createConsumer.createConsumerButton.click();
        //waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());
        scrollUpUsingActions(2);
        waitForVisibility(thirdPartyDetails.txtThirdPartyFirstName, 5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyFirstName, tPFirstName.get());
        waitForVisibility(thirdPartyDetails.txtThirdPartyLastName,5);
        clearAndFillText(thirdPartyDetails.txtThirdPartyLastName, tPLastName.get());
        clearAndFillText(thirdPartyDetails.txtThirdPartyOrganizationName, "tpOrg");
        selectDropDown(contactRecord.consumerType, "Agency");
        selectDropDown(contactRecord.preferredLanguage, "English");

        selectDropDown(contactRecordTP.trContactReason, "Complaint - Account Access"); waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecordTP.trContactAction,"Escalated");
     //   selectDropDown(contactRecordTP.trContactAction, "Escalated");
     //   contactRecordTP.contactMenuContactAction.click();
        waitFor(1);
        clearAndFillText(contactRecordTP.contactReasonComments, "New Reason Comment");
        contactRecord.reasonSaveButton.click();
     //   clearAndFillText(contactRecordTP.contactReasonAddComments, "New Additional Comment");
    //    contactRecord.contactAddCommentsSave.click();

        selectDropDown(contactRecord.contactType, contactType);
        if(contactType.equals("Inbound")) {
            selectDropDown(contactRecord.callQueueType, "Eligibility");
        } else {
            selectDropDown(contactRecord.callCampaignReference, "Enrollment Reminder");
            selectDropDown(contactRecord.outcomeOfContact, "Reached Successfully");
        }
        selectDropDown(contactRecord.contactChannelType, "Phone");
        clearAndFillText(contactRecord.phoneNumber, tPPhone.get());
        System.out.println("phone third party "+ tPPhone.get());
        selectDropDown(contactRecordTP.contactProgramTypes, "Program A");
        contactRecordTP.contactMenuProgramTypes.click();
        waitFor(1);
        selectDropDown(contactRecord.contactDispositions, "Complete");
        contactRecordTP.endContact.click();
        contactRecordTP.thirdPartySaveContact.click();
    }



    @And("I search for a contact and select to view a third party contact record details")
    public void select_to_view_third_party() {
        waitFor(1);
   //     dashBoard.contactRecordSearchTab.click();
        waitFor(1);
        clearAndFillText(contactRecord.contactFN, firstName.get());
        clearAndFillText(contactRecord.contactLN, lastName.get());
        contactRecord.contactCaseId.click();
        waitFor(3);
        contactRecord.searchButton.click();
        waitForVisibility(contactRecord.contactIdFirstRecord,10);
        waitFor(1);
        contactRecord.contactIdFirstRecord.click();
    }

    @Then("I verify it will open in read-only view")
    public void verifyItWillOpenInReadOnlyView() {
        assertTrue(contactRecord.editContactButton.isDisplayed(), "System is in Read-Only View");
    }

    private boolean verifyDateFormat(String dateField) {
        if (dateField == null)
            return true;
        boolean dateFormatCheck = false;
        String dateFieldFormatted = dateField.substring(0, 10);
        try {
            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(dateFieldFormatted);
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }

    @When("I click Edit button for third party contact record")
    public void i_click_Edit_button_for_third_party_contact_record() {
        contactRecord.editContactButton.click();
        waitFor(3);
    }

}