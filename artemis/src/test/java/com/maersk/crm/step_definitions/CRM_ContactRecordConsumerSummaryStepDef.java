package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Map;
import java.util.HashMap;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static org.testng.Assert.*;

public class CRM_ContactRecordConsumerSummaryStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);

    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public static final ThreadLocal<String> consumerName = ThreadLocal.withInitial(String::new);
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<String> consumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> contactType =ThreadLocal.withInitial(() -> "Inbound"); 
    private final ThreadLocal<String> contactChannelType =ThreadLocal.withInitial(() -> "Phone"); 
    private final ThreadLocal<String> contactDispositionsValue =ThreadLocal.withInitial(() -> "Complete"); 
    private final ThreadLocal<String> contactSearchRecordType = ThreadLocal.withInitial(() -> "General");

    @When("I create consumer for Contact Record Consumer View")
    public void createConsumerForContactRecordConsumerView() {
        createNewConsumerForContactRecord();
    }

    @When ("I click contact search tab for Contact Record Consumer View")
    public void clickContactSearchTab() {
        waitFor(1);
        click(dashBoard.contactRecordSearchTab);
    }

    @And("I search and view results for Contact Record Consumer View")
    public void searchAndViewResultsForContactRecord() {
        contactRecord.btnAdvancedSearch.click();
        contactRecord.btnAdvancedSearch.click();
        contactRecord.contactEditFN.sendKeys(firstName.get());
        waitFor(3);
        contactRecord.searchButton.click();
        waitFor(2);
    }

    @Then("I am able to select either the Case ID or Consumer ID hyperlink to view the Summary Tab information for the associated Case or Consumer Profile")
    public void viewConsumerProfileFromContactRecord() {
        contactRecord.contactIdEditFirstRecord.click();
        waitFor(3);

        scrollUpUsingActions(6);
        contactRecord.consumerIdLink.click();
        waitFor(2);

        String consumerIdText = contactRecord.consumerIdText.getText();
        System.out.println(consumerIdText);
        assertNotNull(consumerIdText);
    }

    @Then("I will be able to navigate back to the Contact Record Details from consumer profile viewing")
    public void navigateBackToContactRecordFromConsumerProfileViewing() {
        contactRecord.contactIdEditFirstRecord.click();
        waitFor(3);

        scrollUpUsingActions(6);
        contactRecord.consumerIdLink.click();
        waitFor(2);

        String consumerIdText = contactRecord.consumerIdText.getText();
        System.out.println(consumerIdText);
        assertNotNull(consumerIdText);

        contactRecord.backToContactRecordButton.click();
        waitFor(4);

        String contactName = contactRecord.contactRecordNameText.getText();
        System.out.println(contactName);
        String[] split2ContactName = contactName.split("\\s+");

        assertEquals(firstName.get(), split2ContactName[0]);
        assertEquals(lastName.get(), split2ContactName[1]);
    }

    public Map<String, String> createNewConsumerForContactRecord() {
        waitFor(3);
        contactRecord.initContact.click();
        assertTrue(contactRecord.contactInProgressGreenSign.isDisplayed());

        String firstName = newConsumer.get().get("name").toString();
        String lastName = newConsumer.get().get("surname").toString();
        Map<String, String> consumerNames = new HashMap<>();
        consumerNames.put("firstName", firstName);
        consumerNames.put("lastName", lastName);
        System.out.println(firstName + " " + lastName);
        clearAndFillText(contactRecord.firstName, firstName);

        clearAndFillText(contactRecord.firstName, firstName);
        clearAndFillText(contactRecord.lastName, lastName);
        consumerName.set(firstName + " " + lastName);
        System.out.println(firstName + " " + lastName);

        waitFor(2);
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitFor(2);

        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(3);
        assertTrue(createConsumer.createConsumerButton.isDisplayed(), "Not navigated to Create Consumer page");

        clearAndFillText(createConsumer.consumerFN, firstName);
        clearAndFillText(createConsumer.consumerLN, lastName);
        clearAndFillText(createConsumer.consumerMI, (newConsumer.get().get("name").toString().substring(1)));
        selectRandomDropDownOption(createConsumer.consumerGender);
        clearAndFillText(createConsumer.consumerPhoneNum, (newConsumer.get().get("phone").toString()));
        selectDropDown(createConsumer.phoneType, "Home");
        waitFor(1);
        selectDropDown(createConsumer.consumerAddressType, "Mailing");
        waitFor(1);
        clearAndFillText(createConsumer.consumerAddress1, (radomNumber(3) + " " + getRandomString(5) + " Ct."));
        clearAndFillText(createConsumer.consumerCity, (getRandomString(6) + " City"));
        selectRandomDropDownOption(createConsumer.consumerState);
        selectRandomDropDownOption(createConsumer.consumerCountyField);
        clearAndFillText(createConsumer.consumerZipCode, (newConsumer.get().get("zip").toString()));

        staticWait(300);
        createConsumer.createConsumerButton.click();
        waitFor(1);
        waitForVisibility(contactRecord.lblCrmConsumerId, 10);
        tdm.put("CrmConsumerId", contactRecord.lblCrmConsumerId.getText());
        consumerId.set(contactRecord.lblCrmConsumerId.getText());
        return consumerNames;
    }


    @And("I make sure preferred language is not displayed")
    public void iMakeSurePreferredLanguageIsNotDisplayed() {
        Assert.assertFalse(pubCorr.get().elementIsDisplayed(contactRecord.preferredLanguageTH));
    }


    @When("I make sure spoken\\/written language is not displayed")
    public void i_make_sure_spoken_written_language_is_not_displayed() {
        Assert.assertFalse(pubCorr.get().elementIsDisplayed(contactRecord.spokenLanguage));
        Assert.assertFalse(pubCorr.get().elementIsDisplayed(contactRecord.writtenLanguage));
    }


    @And("I ensure Channel of the contact is {string}")
    public void iEnsureChannelOfTheContactIs(String arg0) {
        if(arg0.compareToIgnoreCase("Phone")==0){
            Assert.assertTrue(pubCorr.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//span[text()='PHONE NUMBER']"))));
            String st = pubCorr.get().getTextIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//span[text()='PHONE NUMBER']/parent::th/parent::tr/parent::thead/following::tbody/tr/td[9]")));
            Assert.assertTrue(!st.isEmpty());
            Assert.assertNotEquals(st,"--");
        }

    }

    @Then("{string} field and the value is Displayed")
    public void fieldAndTheValueIsDisplayed(String arg0) {
        if (arg0.contains("Spoken")) {
            BrowserUtils.waitFor(3);
            Assert.assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.spokenLanguage));
            String st = pubCorr.get().getTextIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//th[text()='SPOKEN LANGUAGE']/parent::tr/parent::thead/following::tbody/tr/td[7]")));
            Assert.assertTrue(!st.isEmpty());
            Assert.assertNotEquals(st, "--");
        }else if (arg0.contains("Written")){
            BrowserUtils.waitFor(3);
            Assert.assertTrue(pubCorr.get().elementIsDisplayed(contactRecord.writtenLanguage));
            String st = pubCorr.get().getTextIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//th[text()='WRITTEN LANGUAGE']/parent::tr/parent::thead/following::tbody/tr/td[7]")));
            Assert.assertTrue(!st.isEmpty());
            Assert.assertNotEquals(st, "--");
        }
    }


  @And("I verify the {string} is not Displayed")
   public void iVerifyTheIsNotDisplayed(String arg0) {
      if (arg0.contains("Written")) {
          BrowserUtils.waitFor(3);
          Assert.assertFalse(pubCorr.get().elementIsDisplayed(contactRecord.writtenLanguage));
      }else if (arg0.contains("Spoken")) {
          BrowserUtils.waitFor(3);
          Assert.assertFalse(pubCorr.get().elementIsDisplayed(contactRecord.spokenLanguage));
      }

   }

    @And("I click link consumer on third party page")
    public void iClickLinkConsumerOnThirdPartyPage() {
    try{
        WebElement e = Driver.getDriver().findElement(By.xpath("//strong[text()='PRIMARY INDIVIDUAL']/parent::p/parent::fieldset/div/label/span/span"));
        pubCorr.get().clickIfElementIsDisplayed(e);
    }catch (NoSuchElementException e){
        System.out.println("PRIMARY INDIVIDUAL not displayed");
    }
        pubCorr.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//span[contains(text(),'LINK')]/span")));
    }

    @And("I change the contact details channel to {string}")
    public void iChangeTheContactDetailsChannelTo(String arg0) {
        pubCorr.get().clickIfElementIsDisplayed(contactRecord.contactChannelType);
        pubCorr.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='" + arg0 + "']")));
    }

    @And("I enter {string} for Contact Details Phone Field with {string}")
    public void iEnterForContactDetailsPhoneFieldWith(String arg0, String arg1) {
        BrowserUtils.waitFor(3);
        clearAndFillText(contactRecord.phoneNumber, arg0);
        BrowserUtils.waitFor(3);
        selectOptionFromMultiSelectDropDown(contactRecord.lstSelectProgram, arg1);
    }

    @And("I click on the SMS text contact id")
    public void iClickOnTheSMSTextContactId() {
        click(Driver.getDriver().findElement(By.xpath("//i[@title='Inbound SMS Text']/parent::td/following::td[1]")));

    }

    @Then("I click linked First Record")
    public void iClickLinkedFirstRecord() {
        waitForVisibility(contactRecord.linkedConsumerId,5);
        contactRecord.linkedConsumerId.click();
    }

    @And("Verify Demographic Info page for Consumer ID")
    public void verifyDemographicInfoPageForConsumerId() {
        waitForVisibility(contactRecord.profileDetailsHeaders,5);
        assertTrue(contactRecord.profileDetailsHeaders.isDisplayed());
        assertTrue(contactRecord.comOptInInfoHeader.isDisplayed());
        assertTrue(contactRecord.addressHeader.isDisplayed());
        assertTrue(contactRecord.phoneNumberHeader.isDisplayed());
        assertTrue(contactRecord.emailHeader.isDisplayed());
    }

    @And("Verify Demographic Info page for Case ID")
    public void verifyDemographicInfoPageForCaseID() {
        waitForVisibility(contactRecord.primaryIndividualsHeader,5);
        assertTrue(contactRecord.primaryIndividualsHeader.isDisplayed());
        assertTrue(contactRecord.caseMembersHeader.isDisplayed());
        assertTrue(contactRecord.authorizedRepresentativeHeader.isDisplayed());
    }
}