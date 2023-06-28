package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import com.maersk.crm.utilities.*;
import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.*;

public class CRM_ConsumerOptInOut extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());

    @When("I search for a consumer and able to click on Add Consumer button")
    public void searchForConsumerAndClickOnButton() {
        clearAndFillText(contactRecord.firstName, firstName.get());
        assertTrue(contactRecord.searchButton.isDisplayed());
        contactRecord.searchButton.click();
        waitForVisibility(createConsumer.addConsumerButton, 5);
        assertTrue(createConsumer.addConsumerButton.isDisplayed(), "Add Consumer button is not displayed");
        createConsumer.addConsumerButton.click();
        waitFor(1);
    }

    @Then("I will see check boxes for the consumer opt-in")
    public void willSeeCheckBoxesForTheConsumerOptIn() {
        assertTrue(createConsumer.commOptEmail.isDisplayed());
        assertTrue(createConsumer.commOptMail.isDisplayed());
        assertTrue(createConsumer.commOptPhone.isDisplayed());
        assertTrue(createConsumer.commOptFax.isDisplayed());
        assertTrue(createConsumer.commOptText.isDisplayed());
    }

    @Then("I verify no Communication Opt-In Preferences component displayed")
    public void i_verify_no_Communication_Opt_In_Preferences_component_displayed() {
        assertTrue(!isElementDisplayed(createConsumer.commOptEmail));
        assertTrue(!isElementDisplayed(createConsumer.commOptMail));
        assertTrue(!isElementDisplayed(createConsumer.commOptPhone));
        assertTrue(!isElementDisplayed(createConsumer.commOptFax));
        assertTrue(!isElementDisplayed(createConsumer.commOptText));
    }

    @Then("I will see the default configured channels of Opted-in as Checked")
    public void willSeeTheDefaultConfiguredChannels() {
        waitFor(1);
        assertTrue(createConsumer.checkedOptMail.isSelected());
        waitFor(1);
        assertTrue(createConsumer.checkedOptPhone.isSelected());
    }

    @Then("I will be able to check or uncheck any of the Opt-in check boxes")
    public void willBeAbleToCheckOrUncheckAnyOfTheOptInCheckBoxes() {
        waitFor(1);
        createConsumer.commOptEmail.click();
        assertTrue(createConsumer.checkedOptEmail.isSelected());
        waitFor(1);
        createConsumer.commOptFax.click();
        assertTrue(createConsumer.checkedOptFax.isSelected());
        waitFor(1);
        createConsumer.commOptMail.click();
        assertTrue(createConsumer.commOptMail.isDisplayed());
        waitFor(1);
        createConsumer.commOptPhone.click();
        assertTrue(createConsumer.commOptPhone.isDisplayed());
        waitFor(1);
        createConsumer.commOptText.click();
        assertTrue(createConsumer.checkedOptText.isSelected());
    }

    @Then("I verify that all the required checkboxes are present")
    public void verifyAllTheRequiredCheckboxesArePresent() {
        waitFor(2);

        String arEmail = createConsumer.optInEmailText.getText();
        String arFax = createConsumer.optInFaxText.getText();
        String arMail = createConsumer.optInMailText.getText();
        String arPhone = createConsumer.optInPhoneText.getText();
        String arText = createConsumer.optInTextData.getText();

        System.out.println(arEmail);
        System.out.println(arFax);
        System.out.println(arMail);
        System.out.println(arPhone);
        System.out.println(arText);

        assertEquals(arEmail, "EMAIL");
        assertEquals(arFax, "FAX");
        assertEquals(arMail, "MAIL");
        assertEquals(arPhone, "PHONE");
        assertEquals(arText, "TEXT");
    }

    @Then("I see OptIn options displayed in the following order")
    public void i_see_OptIn_options_displayed_in_the_following_order() {
        assertEquals(createConsumer.optInOptions.get(0).getText(), "MAIL");
        assertEquals(createConsumer.optInOptions.get(1).getText(), "PHONE");
        assertEquals(createConsumer.optInOptions.get(2).getText(), "EMAIL");
        assertEquals(createConsumer.optInOptions.get(3).getText(), "TEXT");
        assertEquals(createConsumer.optInOptions.get(4).getText(), "FAX");
    }
}
