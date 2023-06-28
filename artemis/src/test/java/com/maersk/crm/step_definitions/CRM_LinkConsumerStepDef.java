package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMCaseContactDetailsPage;
import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.*;

public class CRM_LinkConsumerStepDef extends BrowserUtils {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMCaseContactDetailsPage caseContactDetails = new CRMCaseContactDetailsPage();
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    public final ThreadLocal<String> caseId = new ThreadLocal<>();
    private final ThreadLocal<String> fullName = ThreadLocal.withInitial(String::new);


    @And("I click on the Case Contact Details Tab")
    public void i_click_on_the_case_contact_details_tab() {
        highLightElement(contactRecord.caseContactDetailsTab);
        contactRecord.caseContactDetailsTab.click();
    }

    @Then("I should see Contact Id Present")
    public void i_should_see_contact_id_present() {
        assertTrue(caseContactDetails.contactId.isDisplayed());
    }

    @And("I should see the first name and Last name {string}")
    public void i_should_see_the_first_name(String expected) {
        waitFor(1);
        System.out.println(contactRecord.linkedConsumerNameOnHeader.getText());
        assertTrue(contactRecord.linkedConsumerNameOnHeader.getText().equalsIgnoreCase(expected), "After linking Header does not display expected linked consumer's name");
    }

    @Then("I see the Contact Record linked to the new CaseConsumer Profile")
    public void i_see_the_Contact_Record_linked_to_the_new_Case_Consumer_Profile() {
        assertTrue(contactRecord.linkedConsumerInContact.isDisplayed(), "Linked - General Contact section header is not displayed");
    }

    /**
     * This method expends the first search Case/Consumer result
     */

    @When("I expend the Case Consumer this contact relates to in search result")
    public void i_expend_the_Case_Consumer_this_contact_relates_to_in_search_result() {
        waitFor(4);
        contactRecord.expandFistConsumer.click();
        waitFor(2);
        synchronized (caseId){caseId.set(contactRecord.caseIdFirstRecord.getText());}
    }

    /**
     * This method accepts a String consumer role and searches through search result for this specific Consumer Role
     */
    @Then("I can select the individual {string} from the search that is making a contact")
    public void i_can_select_the_individual_from_the_search_that_is_making_a_contact(String consumerRole) {
        waitFor(2);
        contactRecord.fullNameRadioButton.click();
    }

    /**
     * @Muhabbat This method selects specific consumer role nd links from search results section
     * Added scroll and wait --aswath-04042019
     */
    @Then("I am able to select radio buttons for {string} and link to a Consumer Profile")
    public void i_am_able_to_select_radio_buttons_for_and_link_to_a_Consumer_Profile(String consumerRole) {
        waitFor(5);
        contactRecord.fullNameRadioButton.click();
        contactRecord.DOBcheckBox.click();
        contactRecord.consumerIDcheckButton.click();
        scrollUp();
        assertTrue(contactRecord.linkRecordButton.isDisplayed(), "Link Consumer button should be displayed");
        waitFor(5);
        contactRecord.linkRecordButton.click();
        waitForVisibility(contactRecord.unLink, 5);
        assertTrue(contactRecord.unLink.isDisplayed(), "Consumer was not liked to a Case or Consumer");
    }

    /**
     * @Muhabbat This method verifies that user has been navigated to Active Contact Record Page and Consumer's data has been systematically populated
     */
    @Then("I see Consumer Name, Type ID, SSN, DOB and Preferred Language systematically populated")
    public void i_see_Consumer_Name_Type_ID_SSN_DOB_and_Preferred_Language_systematically_populated() {
        assertTrue(activeContact.fullName.isDisplayed(), "Consumer Name has not been populated");
        assertTrue(activeContact.defaultConsumerRole.isDisplayed(), "Consumer Role has not been populated");
        assertTrue(activeContact.consumerID.isDisplayed(), "Consumer ID has not been populated");
        assertTrue(activeContact.ssn.isDisplayed(), "Consumer SSN has not been populated");
        assertTrue(activeContact.dob.isDisplayed(), "Consumer DOB has not been populated");
    }

    /**
     * @Muhabbat This method  unlinks current Consumer from Active Contact Record
     * Added scroll and wait --aswath-04042019
     */
    @When("I unlink previously selected Consumer from Active Contact Record")
    public void i_unlink_previously_selected_Consumer_from_Active_Contact_Record() {
        waitFor(1);
        waitForPageToLoad(10);
        scrollToTop();
        scrollUpRobot();
        activeContact.unlinkContactRecord.click();
        assertTrue(contactRecord.searchButton.isDisplayed(), "Consumer was not unliked from current Active Contact Record");
    }

    /* Validates the case is linked to contact
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */

    @Then("I verify the case is linked to contact")
    public void i_verify_the_case_is_linked_to_contact() {
        synchronized (caseId){caseId.set(contactRecord.caseIdFirstRecord.getText());}
        assertTrue(caseId.get() != null, "Case ID is not linked to consumer");
    }

    /* Selects individual record
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I can select the individual from the search that is making a contact")
    public void i_can_select_the_individual_record() {
        waitFor(2);
        scrollDown();
        for (int i = 0; i < searchResult.relatedConsumerNames.size(); i++) {
            String firstname = contactRecord.firstName.getAttribute("value");
            String lastname = contactRecord.lastName.getAttribute("value");
            synchronized (fullName){fullName.set(firstname + " " + lastname);}
            if (searchResult.relatedConsumerNames.get(i).getText().equalsIgnoreCase(fullName.get())) {
                assertTrue(searchResult.relatedConsumerNames.size() > 0, "There is no consumer with searched role is related to Case");
            }
        }
    }

    /* Selects individual record authroize the consumer details
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I am able to select radio buttons for indivdual and Consumer Profile")
    public void i_am_able_to_select_radio_buttons_for_and_link_to_Consumer_Profile() {
        for (int i = 0; i < searchResult.relatedConsumerNames.size(); i++) {
            if (searchResult.relatedConsumerNames.get(i).getText().equalsIgnoreCase(fullName.get())) {
                String defaultValDOBcheckBox = searchResult.relatedConsumerDOBs.get(i).getAttribute("value");
                assertEquals(defaultValDOBcheckBox, "false", "Check box is enabled, is not unchecked");
                searchResult.relatedConsumerDOBs.get(i).click();
                boolean verifyDOB = searchResult.relatedConsumerDOBs.get(i).isEnabled();
                assertTrue(verifyDOB, "DOB is enabled");
                break;
            }
        }
    }

    /* Validates the all check boxes are unchecked
       Author - Aswath
       Date - 04/08/2019
       Parameters -
      */
    @Then("I verify all check boxes are unchecked")
    public void i_verify_all_check_boxes_are_uncheck() {
        for (int i = 0; i < searchResult.relatedConsumerNames.size(); i++) {
            if (searchResult.relatedConsumerNames.get(i).getText().equalsIgnoreCase(fullName.get())) {
                String defaultValDOBcheckBox = searchResult.relatedConsumerDOBs.get(i).getAttribute("value");
                assertEquals(defaultValDOBcheckBox, "false", "DOB Check box is enabled, is not unchecked");
                String defaultValSSNcheckBox = searchResult.relatedConsumerSSNs.get(i).getAttribute("value");
                assertEquals(defaultValSSNcheckBox, "false", "SSN Check box is enabled, is not unchecked");
                String defaultValConsumerIDcheckBox = searchResult.relatedConsumerIDs.get(i).getAttribute("value");
                assertEquals(defaultValConsumerIDcheckBox, "false", "SSN Check box is enabled, is not unchecked");
                String defaultValrelatedPrgConsumerIDscheckBox = searchResult.relatedPrgConsumerIDs.get(i).getAttribute("value");
                assertEquals(defaultValrelatedPrgConsumerIDscheckBox, "false", "SSN Check box is enabled, is not unchecked");
                break;
            }
        }
    }

    @Then("I verify all check boxes in authentication grid are present and unchecked")
    public void i_verify_all_check_boxes_in_authentication_grid_are_present_and_unchecked() {
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.HomeAddressCheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.CaseIdCheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.DOBcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.SSNcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerFullNameCheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerIdCheckBox));

        // will add program name when defect is fixed
        assertNotEquals(contactRecord.HomeAddressCheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.CaseIdCheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.DOBcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.SSNcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerFullNameCheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerIdCheckBox.getAttribute("value"), "checked");

    }

    @Then("I verify that phone number checkbox is not available for baseline authentication grid")
    public void i_verify_that_phone_number_checkbox_is_not_available_for_baseline_authentication_grid() {
        assertFalse(UIAutoUitilities.isElementPresent(contactRecord.PhoneNumberCheckBox));

    }

    @Then("I verify that {string} checkbox is available")
    public void i_verify_that_checkbox_is_available(String string) {
        contactRecord.ConsumerFullNameCheckBox.click();
        assertNotEquals(contactRecord.unableToAuthenticate.getAttribute("value"), "checked");
    }


    /* Validates Consumer authenticate message
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I verify consumer authentication message")
    public void i_verify_all_consumer_authentication_message() {
        waitFor(5);
        waitForPageToLoad(10);
        waitForVisibility(searchResult.consumerAuthenticated, 10);
        String conAuth = searchResult.consumerAuthenticated.getText();
        System.out.println(conAuth);
        String expectConAuth = "check_circle_outline" + "CONSUMER AUTHENTICATED";
        assertEquals(conAuth, expectConAuth, "Consumer Authenticated message is not displayed as expected");
    }

    /**
     * This method selects specific consumer role nd links from search results section
     */
    @Then("I am able to select radio buttons for {string}")
    public void i_am_able_to_select_radio_buttons_for(String consumerRole) {
        contactRecord.DOBcheckBox.click();
        contactRecord.consumerIDcheckButton.click();
    }

    /* Validates Consumer and the active contact
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I link the consumer and verify the active contact")
    public void i_link_the_consumer_and_verify_the_active_acontact() {
        assertTrue(contactRecord.linkRecordButton.isDisplayed(), "Link Consumer button should be displayed");
        waitFor(5);
        contactRecord.linkRecordButton.click();
        waitForVisibility(contactRecord.unLink, 5);
        assertTrue(contactRecord.unLink.isDisplayed(), "Consumer was not liked to a Case or Consumer");
    }

    /* Validates anthenticate check box check box
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I verify the unable to authenticate checkbox displayed")
    public void i_verify_the_unable_to_authenticate_check_box_displayed() {
        contactRecord.fullNameRadioButton.click();
        waitFor(2);
        scrollUp();
        waitForVisibility(contactRecord.unableToAuthenticate, 10);
        assertTrue(contactRecord.unableToAuthenticate.isDisplayed(), "Unable to authenticate check box is not displayed");
    }

    @Then("I will authenticate the the customer")
    public void i_will_authenticate_the_the_customer() {
        waitFor(4);
        contactRecord.fullNameRadioButton.click();
        contactRecord.DOBcheckBox.click();
        contactRecord.SSNcheckBox.click();
    }

    @Then("I will verify that the {string} message will appear")
    public void i_will_verify_that_the_message_will_appear(String string) {
        assertTrue(contactRecord.consumerAuthenticatedGreenButton.isDisplayed());
    }

    @Then("I will verify that the The {string} button will be enabled")
    public void i_will_verify_that_the_The_button_will_be_enabled(String string) {
        assertTrue(contactRecord.linkRecordButton.isEnabled());

    }

    /* Selects anthenticate check box
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I click on unable to authenticate consumer")
    public void i_click_on_unable_to_authenticate_consume() {
        waitForVisibility(contactRecord.unableToAuthenticate, 5);
        assertTrue(contactRecord.unableToAuthenticate.isDisplayed(), "Unable to authenticate check box is not displayed");
        contactRecord.unableToAuthenticate.click();
    }

    /* Selects record based on the consumer name
        Author - Aswath
        Date - 04/08/2019
        Parameters -
       */
    @Then("I am able to select radio buttons for indivdual")
    public void i_am_able_to_select_radio_buttons() {
        for (int i = 0; i < searchResult.relatedConsumerNames.size(); i++) {
            if (searchResult.relatedConsumerNames.get(i).getText().equalsIgnoreCase(fullName.get())) {
                searchResult.relatedConsumerNames.get(i).click();
                String defaultValDOBcheckBox = searchResult.relatedConsumerDOBs.get(i).getAttribute("value");
                assertEquals(defaultValDOBcheckBox, "false", "Check box is enabled, is not unchecked");
                searchResult.relatedConsumerDOBs.get(i).click();
                boolean verifyDOB = searchResult.relatedConsumerDOBs.get(i).isEnabled();
                assertTrue(verifyDOB, "DOB is enabled");
                break;
            }
        }
    }

    /* Validates anthenticate error message
         Author - Aswath
         Date - 04/08/2019
         Parameters -
        */
    @Then("I verify unable to authenticate alert message")
    public void i_verify_unable_to_authenticate_alert_message() {
        waitForVisibility(searchResult.errorSnackBar, 10);
        searchResult.errorSnackBar.isDisplayed();
    }

    @Then("I verify all check boxes in authentication grid are present for consumer WITHOUT case and unchecked")
    public void i_verify_all_check_boxes_in_authentication_grid_are_present_for_consumer_WITHOUT_case_and_unchecked() {
        try{
            assertTrue(contactRecord.mailingAddressCheckBox.isDisplayed());
        }catch (NoSuchElementException e){
            assertTrue(UIAutoUitilities.isElementPresent(contactRecord.physicalAddressCheckBox));
        }
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.DOBcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.SSNcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerFullNameCheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerIdCheckBox));
        if(UIAutoUitilities.isElementPresent(contactRecord.CaseIdCheckBox)){
            assertNotEquals(contactRecord.CaseIdCheckBox.getAttribute("value"), "checked");
        }
        assertNotEquals(contactRecord.DOBcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.SSNcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerFullNameCheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerIdCheckBox.getAttribute("value"), "checked");
    }

    @Then("I verify all check boxes in authentication grid are present for consumer ON case and unchecked")
    public void i_verify_all_check_boxes_in_authentication_grid_are_present_for_consumer_ON_case_and_unchecked() {
        try{
            assertTrue(contactRecord.mailingAddressCheckBox.isDisplayed());
        }catch (NoSuchElementException e){
            assertTrue(UIAutoUitilities.isElementPresent(contactRecord.physicalAddressCheckBox));
        }
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.DOBcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.SSNcheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerFullNameCheckBox));
        assertTrue(UIAutoUitilities.isElementPresent(contactRecord.ConsumerIdCheckBox));
        if(UIAutoUitilities.isElementPresent(contactRecord.CaseIdCheckBox)){
            assertNotEquals(contactRecord.CaseIdCheckBox.getAttribute("value"), "checked");
        }
        assertNotEquals(contactRecord.DOBcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.SSNcheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerFullNameCheckBox.getAttribute("value"), "checked");
        assertNotEquals(contactRecord.ConsumerIdCheckBox.getAttribute("value"), "checked");
    }

    @Then("I validate the address on authentication grid displayed without County {string}")
    public void iValidateTheAddressOnAuthenticationGridDisplayedWithoutCounty(String fullAddress) {
        assertTrue(contactRecord.addressForAuthentication.isDisplayed());
        assertTrue(contactRecord.addressForAuthentication.getText().contains(fullAddress));
    }

    @Then("I verify auth grid consumer name {string} is same as consumer name that was searched")
    public void i_verify_auth_grid_consumer_name_is_same_as_consumer_name_that_was_searched(String consumerName) {
        assertTrue(contactRecord.authGridConsumerName(consumerName).isDisplayed());
    }

    @Then("I verify that consumer name {string} on top of active screen is the same consumer name that was in auth grid")
    public void i_verify_that_consumer_name_on_top_of_active_screen_is_the_same_consumer_name_that_was_in_auth_grid(String consumerName) {
        assertTrue(contactRecord.consumerNameOnTopAfterLinkingActiveScreen(consumerName).isDisplayed());
    }
    @Then("I will link the consumer")
    public void i_will_link_the_consumer() {
        contactRecord.linkRecordButton.click();
    }


}

