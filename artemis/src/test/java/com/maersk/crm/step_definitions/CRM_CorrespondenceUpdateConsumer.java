package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMEditConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMUnidentifiedContactPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CRM_CorrespondenceUpdateConsumer extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = new ThreadLocal<>();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMUnidentifiedContactPage crmUnidentifiedContactPage = new CRMUnidentifiedContactPage();
    CRMEditConsumerProfilePage editConsumer=new CRMEditConsumerProfilePage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> dob = ThreadLocal.withInitial(() -> "12/01/1991");
    private final ThreadLocal<String> zip = ThreadLocal.withInitial(() -> "12345");
    private final ThreadLocal<String> ssn = ThreadLocal.withInitial(() -> getRandomNumber(9));
    private final ThreadLocal<String> consumerId = new ThreadLocal<>();

    @When("I create a consumer for Correspondence preference from consumer profile")
    public void createACaseUsingCaseLoaderAPIForCorrespondencePref() {
        createNewConsumerForCorrespondencePref();
    }

    @And("I search consumer with first name and last name for Correspondence preference from consumer profile")
    public void searchedForCustomerWithFirstNameAndLastNameForCorrespondencePref() {
        contactRecord.firstName.sendKeys(firstName.get());
        contactRecord.lastName.sendKeys(lastName.get());
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(2);
        contactRecord.consumerIdFirstRecord.click();
        waitForVisibility(editConsumer.consumerEditButton, 10);
    }

    @And("I edit the correspondence preferences from consumer profile")
    public void editCorrespondencePref() {
        editConsumer.consumerEditButton.click();
        waitForVisibility(createConsumer.consumerEditCorrespondencePref, 10);
        createConsumer.correspondencePrefSelect.click();
        createConsumer.correspondencePaperlessValue.click();
        waitFor(1);
        selectDropDown(createConsumer.consumerEditPreflang, "English");
        waitFor(1);
        crmUnidentifiedContactPage.saveBtn.click();
        waitFor(3);
        assertTrue(editConsumer.consumerEditButton.isDisplayed(), "Not mavigater back to View COnsumer Profile screen");
    }

    @And("I edit to view correspondence preferences from consumer profile")
    public void editProfileToViewCorrespondencePref() {
        editConsumer.consumerEditButton.click();
        waitForVisibility(createConsumer.consumerEditCorrespondencePref, 10);
        createConsumer.consumerEditCorrespondencePref.click();
        waitForVisibility(createConsumer.correspondencePrefSelect, 10);
    }

    @Then("I verify that Consumers Correspondence Preference is {string} in consumer profile")
    public void verifyCorrespondencePref(String correspondencePrefType) {
        String correspondencePref = editConsumer.consumerCorrespondencePref.getText();
        System.out.println("Correspondence Preference Type: "+correspondencePref);
        assertEquals(correspondencePref, correspondencePrefType);
    }

    @Then("I verify Consumers Correspondence Preference baseline select dropdown option for consumer profile")
    public void verifyConsumerCorrespodencePrefsOptions() {
        String correspondencePrefOption = createConsumer.correspondencePrefSelect.getText();
        System.out.println("Correspondence Preference: "+correspondencePrefOption);
        assertEquals(correspondencePrefOption, "Paperless");
    }

    @Then("I verify Consumers Correspondence Preference dropdown option is multi select from editing consumer profile")
    public void verifyMultiSelectCorrespondencePrefs() {
        changeCorrespondencePrefs();
        selectFromMultiSelectDropDown(createConsumer.consumerEditCorrespondencePref, "Paperless");
        assertTrue(createConsumer.consumerEditCorrespondencePref.isDisplayed());
    }

    private void createNewConsumerForCorrespondencePref() {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        clearAndFillText(contactRecord.firstName, firstName.get());
        clearAndFillText(contactRecord.lastName, lastName.get());
        synchronized (consumerName){
            consumerName.set(firstName.get() + " " + lastName.get());
        }
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

        clearAndFillText(createConsumer.consumerFN, firstName.get());
        clearAndFillText(createConsumer.consumerLN, lastName.get());
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        clearAndFillText(createConsumer.consumerDB, dob.get());
        clearAndFillText(createConsumer.consumerSSN, ssn.get());
        waitFor(2);
        selectRandomDropDownOption(createConsumer.consumerGender);
        waitFor(2);
        selectDropDown(createConsumer.consumerCorrespondencePref, "Paperless");
        waitFor(1);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        waitFor(2);
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, zip.get());

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        synchronized (consumerId){
            consumerId.set(contactRecord.lblCrmConsumerId.getText());
        }
    }

    private void changeCorrespondencePrefs() {
        editConsumer.consumerEditButton.click();
        waitForVisibility(createConsumer.consumerEditCorrespondencePref, 10);
        System.out.println("Correspondence Pref Value: "+ createConsumer.consumerEditCorrespondencePref.getText());
        createConsumer.consumerEditCorrespondencePref.click();
        waitForVisibility(createConsumer.correspondencePrefSelect, 10);
        createConsumer.correspondencePaperlessValue.click();
        waitFor(2);
    }
}