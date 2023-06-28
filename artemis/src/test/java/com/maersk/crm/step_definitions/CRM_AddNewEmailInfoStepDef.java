package com.maersk.crm.step_definitions;

import com.google.common.collect.Ordering;
import com.maersk.crm.api_step_definitions.APIContactRecordEventsController;
import com.maersk.crm.pages.crm.CRMAddContactInfoPage;
import com.maersk.crm.pages.crm.CRMAddNewEmailPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;


public class CRM_AddNewEmailInfoStepDef extends BrowserUtils {


    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage addContactInfo = new CRMAddContactInfoPage();
    //CRMAddNewEmailPage addNewEmailPage = CRMAddNewEmailPage();
    private String emailAddressConInfo, emailStatusConInfo;
    CRMAddContactInfoPage crmAddContactInfoPage = new CRMAddContactInfoPage();
    CRMAddNewEmailPage crmAddNewEmailPage = new CRMAddNewEmailPage();
    private final ThreadLocal<Map<String, Object>> randomdata = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    private final ThreadLocal<String> emailIDToBeValidated = ThreadLocal.withInitial(String::new);
    String newStartDate = new String();
    String newEndDate = new String();
    String existingMailID = new String();
    private final ThreadLocal<HashMap<String, String>> emailIdsAndStatus = ThreadLocal.withInitial(HashMap::new);
    public String longEmailAddress;
    List<String> emailActiveStartDate = new ArrayList<>();
    List<String> emailInactiveEndDate = new ArrayList<>();
    List<String> emailFutureStartDate = new ArrayList<>();
    public final ThreadLocal<String> emailUIprovided1 = ThreadLocal.withInitial(String::new);
    public String date;
    public String endDate;

    @Then("I should see the EmailAddress label displayed")
    public void i_should_see_the_EmailAddress_label_displayed() {
        Assert.assertTrue(demographicContactInfoPage.emailLabel.isDisplayed());
    }

    @Then("I should see the Add button displayed for Email address")
    public void i_should_see_the_Add_button_displayed_for_Email_address() {
        Assert.assertTrue(demographicContactInfoPage.addEmailButton.isDisplayed());
    }

    @When("I click on the Add button for Email Address")
    public void i_click_on_the_Add_button_for_Email_Address() {
        jsClick(demographicContactInfoPage.addEmailButton);
    }

    @And("I add emailId's with future status")
    public void i_add_emailids_with_future_status() {
        //Adding future  emailID - Future start and end dates - Future


    }

    /* Verifies the active status are sorted in ascending order
        Author - Aswath
        Date - 02/24/2019
        Parameters -
     */
    @Then("Active Email are sorted by start date in ascending order")
    public void active_email_are_sorted_by_start_date_in_ascending_order() {
        getEmailDates();
        assertTrue(ascendingOrderDatesString(emailActiveStartDate));

    }

    /* Verifies the Future status are sorted in ascending order
        Author - Aswath
        Date - 02/24/2019
        Parameters -
     */
    @Then("Future Email are sorted by start date in ascending order")
    public void future_email_are_sorted_by_start_date_in_ascending_order() {
        assertTrue(ascendingOrderDatesString(emailFutureStartDate));
    }
    /* Verifies the inactive status are sorted in descending order
        Author - Aswath
        Date - 02/24/2019
        Parameters -
     */

    @And("Inactive Email are sorted by end date in descending order")
    public void inactive_email_are_sorted_by_end_date_in_descending_order() {
        assertTrue(descendingOrderDatesString(emailInactiveEndDate));
    }

    @When("I verify that I am in the Add Email Address Page")
    public void i_verify_that_I_am_in_the_Add_Email_Address_Page() {
        //Changed from EmailLabel to  Email Field
        Assert.assertTrue(crmAddContactInfoPage.emailAddressField.isDisplayed());
    }

    @Then("I verify the fields displayed on the Add Email Address Page")
    public void i_verify_the_fields_displayed_on_the_Add_Email_Address_Page() {
        waitFor(1);
        Assert.assertTrue(crmAddContactInfoPage.emailAddressField.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.startDate.isDisplayed());
        //Assert.assertTrue(crmAddContactInfoPage.associatedCaseMember.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.endDate.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.saveButton.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.cancelButton.isDisplayed());
    }

    @When("I enter the mandatory fields on the add email page and click on save")
    public void i_enter_the_mandatory_fields_on_the_add_email_page_and_click_on_save() {
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, (randomdata.get().get("email").toString()));
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        String date = getCurrentDate();
        clearAndFillText(crmAddContactInfoPage.startDate, date);
        crmAddContactInfoPage.saveButton.click();

    }

    @Then("I am navigated back to Demographic page")
    public void i_am_navigated_back_to_Demographic_page() {
        Assert.assertTrue(demographicContactInfoPage.emailTable.isDisplayed());
    }

    @When("I save the fields and give start date as current and end date in future")
    public void i_save_the_fields_and_give_start_date_as_current_and_end_date_in_future() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        //Changed to clearAndFill --aswath
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
//        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        //clearAndFillText2 (crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        String date = getCurrentDate();
        waitFor(2);
        clearAndFillText(crmAddContactInfoPage.startDate, date);
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(100));
        waitFor(10);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        waitForVisibility(crmAddContactInfoPage.emailAddressHeader, 10);
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
    }

    @And("I save the fields and give start date as future")
    public void i_save_the_fields_and_give_start_date_as_future() {
        waitForPageToLoad(10);
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        // crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
       /* scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(1);
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(100));
        //Added Wait --aswath
        waitFor(5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
    }

    @And("I verify status of Email Address as {string}")
    public void i_verify_status_of_email_address_as_something(String status) {
        WebElement emailStatus = Driver.getDriver().findElement(By.xpath("//td[.='" + emailIDToBeValidated.get() + "']/following-sibling::td[1]"));
        Assert.assertEquals(emailStatus.getText(), status);
    }

    @Then("New Email address should be added to the Email list")
    public void new_email_address_should_be_added_to_the_email_list() {
        boolean found = false;
        int i = 1;
        while (!found) {

            for (WebElement email : crmAddNewEmailPage.listOfEMail) {
                List<WebElement> listEmail = email.findElements(By.xpath("td[1]"));
                for (WebElement e : listEmail) {

                    if (emailIDToBeValidated.get().equalsIgnoreCase(e.getText())) {
                        found = true;
                    }
                    break;
                }
                if (found) break;
            }
            if (!found) {
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a[text()='" + i + "']")).click();
                i++;
            } else break;
        }
        assertTrue(found, "Email added is not found");
    }

    @And("I add new Email with Active, Future and Inactive status")
    public void i_add_new_New_Email_with_Active_Future_and_Inactive_status(List<Map<String, String>> data) {

        for (Map<String, String> record : data) {

            waitFor(2);
            jsClick(demographicContactInfoPage.addEmailButton);
            waitFor(2);
            selectRandomDropDownOption(crmAddContactInfoPage.consumer);
            emailIDToBeValidated.set(randomdata.get().get("email").toString());
            clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());

            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                if (sDate.contains("CD"))
                    sDate = getCurrentDate();
                else
                    sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.startDate, sDate);
            }
            if (record.get("endDate") != null && !record.get("endDate").isEmpty()) {
                String eDate = record.get("endDate");
                if (eDate.contains("CD"))
                    eDate = getCurrentDate();
                else
                    eDate = eDate.contains("P") ? getPriorDate(Integer.parseInt(eDate.substring(1))) : getGreaterDate(Integer.parseInt(eDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.endDate, eDate);
            }
            crmAddContactInfoPage.saveButton.click();

            waitFor(2);
        }

    }


    @When("I save the fields and give start date in the past and end date as current date")
    public void i_save_the_fields_and_give_start_date_in_the_past_and_end_date_as_current_date() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
//        crmAddContactInfoPage.associatedCaseMember.click();
//        waitFor(1);
//        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
//        hover(crmAddContactInfoPage.dropdownLastItem);
//        crmAddContactInfoPage.dropdownLastItem.click();
        waitFor(2);
        String date = getCurrentDate();
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(150));
        clearAndFillText(crmAddContactInfoPage.endDate, date);
        crmAddContactInfoPage.saveButton.click();
    }

    @When("I save the fields and give start date in the future and end date in future")
    public void i_save_the_fields_and_give_start_date_in_the_future_and_end_date_in_future() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        waitFor(2);
        clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(10));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(200));
        crmAddContactInfoPage.saveButton.click();
        waitFor(2);
        scrollDownUsingActions(2);
    }

    @When("I save the fields and give start date in future and end date in future")
    public void i_save_the_fields_and_give_start_date_in_future_and_end_date_in_future() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();
        waitFor(2);
        clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(10));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(100));
        crmAddContactInfoPage.saveButton.click();
        waitFor(2);
        scrollDownUsingActions(2);
    }

    @When("I save the fields and give start date in past and end date in future")
    public void i_save_the_fields_and_give_start_date_in_past_and_end_date_in_future() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        waitFor(2);
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(100));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(100));
        crmAddContactInfoPage.saveButton.click();
        waitFor(2);
        scrollDownUsingActions(2);

    }

    @When("I save the fields and give start date in the past and end date as past")
    public void i_save_the_fields_and_give_start_date_in_the_past_and_end_date_as_past() {
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
       /* crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(2);
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(100));
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
        crmAddContactInfoPage.saveButton.click();
        waitFor(2);
        scrollDownUsingActions(2);
    }

    @Then("I verify status of email as {string}")
    public void verifyEmailStatus(String status) {
        System.out.println(emailIDToBeValidated.get());
        String emailStatus = null;
        int count = 0;
        while (count<2) {
            try {
                emailStatus = Driver.getDriver().findElement(By.xpath("//td[.='" + emailIDToBeValidated.get() + "']/following-sibling::td[2]")).getText();
            }catch (Exception e){
                emailStatus = Driver.getDriver().findElement(By.xpath("//td[.='" + emailIDToBeValidated.get() + "']/following-sibling::td[2]")).getText();
            }
            count++;
        }
        Assert.assertEquals(emailStatus, status);
     }

    @When("I provide more than thirty characters in email address field {string}")
    public void i_provide_more_than_thirty_characters_in_email_address_field(String invalid) {
        longEmailAddress = invalid;
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, invalid);
    }

    @Then("I verify email address more than {int} characters is not allowed")
    public void i_verify_email_address_more_than_characters_is_not_allowed(int arg1) throws Throwable {

        String actual = crmAddContactInfoPage.emailAddressField.getAttribute("value");
        Assert.assertNotEquals(longEmailAddress, actual);

    }

    @When("I click on save without entering the mandatory fields")
    public void i_click_on_save_without_entering_the_mandatory_fields() {
        crmAddContactInfoPage.saveButton.click();

    }

    @Then("I see error message populated below each field")
    public void i_see_error_message_populated_below_each_field() {
        crmAddNewEmailPage.emptyEmailAddressError.isDisplayed();
        // newEmailErrors.emptyAssociatedCaseMemberError.isDisplayed();
        waitFor(2);
        crmAddNewEmailPage.emptyStartDateError.isDisplayed();
    }

    @When("I provide invalid data for emailAddress,StartDate and EndDate")
    public void i_provide_invalid_data_for_emailAddress_StartDate_and_EndDate() {
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, "test@abc.");
        clearAndFillText(crmAddContactInfoPage.startDate, "02/32/2109");
        clearAndFillText(crmAddContactInfoPage.endDate, "09/31/201");
        crmAddContactInfoPage.saveButton.click();
    }

    @Then("I see valid field error message populated below each field")
    public void i_see_valid_field_error_message_populated_below_each_field() {
        Assert.assertTrue(crmAddNewEmailPage.invalidEmailFieldError.isDisplayed(), "EMAIL error is not displayed");
        // Assert.assertTrue(newEmailErrors.incorrectDateError.isDisplayed(), "Date Error is not displayed");
        Assert.assertTrue(crmAddNewEmailPage.invalidDateError.isDisplayed(), "Invalid date format");

    }

    @When("I add three emailId's with start date as current and end date in future")
    public void i_add_three_emailId_s_with_start_date_as_current_and_end_date_in_future() {
        String emailID1 = "";
        synchronized (emailID1){
            emailID1 = randomEmailId();
        }
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID1);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }

        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(90));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(90));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID1, 3 + "ACTIVE");
        demographicContactInfoPage.addEmailButton.click();
        //Adding Email 2
        String emailID2 = randomEmailId();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID2);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(60));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(60));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID2, 2 + "ACTIVE");
        demographicContactInfoPage.addEmailButton.click();
        //Adding Email 3
        String emailID3 = randomEmailId();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID3);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(10));
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(355));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID2, 1 + "ACTIVE");
    }

    @Then("I verify the order of emailID's and the respective status")
    public void i_verify_the_order_of_emailID_s_and_the_respective_status() {
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
        HashMap<String, String> actualEmailOrder = getEmailIdsWithStatus();
        System.out.println(actualEmailOrder.toString() + " ++++ACTUAL");
        System.out.println(emailIdsAndStatus.toString() + " ====EXPECTED");
        Assert.assertEquals(compareHashMaps(actualEmailOrder, emailIdsAndStatus.get()), true);
    }

    @When("I add three emailId's with start date and end date in past")
    public void i_add_three_emailId_s_with_start_date_as_current_and_end_date_in_past() {
        //Adding first emailID
        String emailID1 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID1);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        clearAndFillText(crmAddContactInfoPage.startDate, "10122016");
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID1, 1 + "INACTIVE");
        demographicContactInfoPage.addEmailButton.click();

        // Adding second EmailID
        String emailID2 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID2);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        clearAndFillText(crmAddContactInfoPage.startDate, "10022016");
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(30));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID2, 2 + "INACTIVE");
        demographicContactInfoPage.addEmailButton.click();

        // Adding third EmailID
        String emailID3 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID3);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        clearAndFillText(crmAddContactInfoPage.startDate, "10042016");
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(100));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID3, 3 + "INACTIVE");
    }

    @When("I add three emailId's with active and inactive status")
    public void i_add_three_emailId_s_with_active_and_inactive_status() {
        // Adding first EmailID
        String emailID1 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID1);
        if (isElementDisplayed(crmAddContactInfoPage.associatedCaseMember)) {
            crmAddContactInfoPage.associatedCaseMember.click();
            waitFor(1);
            scrollToElement(crmAddContactInfoPage.dropdownLastItem);
            hover(crmAddContactInfoPage.dropdownLastItem);
            crmAddContactInfoPage.dropdownLastItem.click();
        }
        String date = getCurrentDate();
        waitFor(1);
        clearAndFillText(crmAddContactInfoPage.startDate, "10122017");
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(30));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID1, 2 + "ACTIVE");
        demographicContactInfoPage.addEmailButton.click();

        // Adding second EmailID
        String emailID2 = "a" + emailID1;
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID2);
       /* crmAddContactInfoPage.associatedCaseMember.click();
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/

        clearAndFillText(crmAddContactInfoPage.startDate, "10122018");
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(365));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID2, 1 + "ACTIVE");
        demographicContactInfoPage.addEmailButton.click();

        // Adding third EmailID
        String emailID3 = "b" + emailID1;
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID3);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        clearAndFillText(crmAddContactInfoPage.startDate, "10042016");
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(100));
        crmAddContactInfoPage.saveButton.click();
        emailIdsAndStatus.get().put(emailID3, 3 + "INACTIVE");
    }

    @Then("Then I verify the order of emailIDs and the respective status")
    public void then_I_verify_the_order_of_emailIDs_and_the_respective_status() throws Throwable {
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
        HashMap<String, String> actualEmailIdOrder = getEmailIdsWithStatus();
        Assert.assertEquals(compareHashMaps(actualEmailIdOrder, emailIdsAndStatus.get()), true);
    }

    @Then("I click on added emailID")
    public void i_click_on_added_emailID() throws Throwable {
        clickOnFirstEmaiId();
    }

    @Then("I update the emailID with {string}")
    public void i_update_the_emailID_with(String updatedemail) throws Throwable {
        existingMailID = getText(crmAddContactInfoPage.emailAddressField);
       /* updateEmailID(crmAddContactInfoPage.emailAddressField);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        clearAndFillText(crmAddContactInfoPage.emailAddressField, updatedemail);
        waitFor(10);
        crmAddContactInfoPage.saveButton.click();
    }

    @When("I hower over status of the newly email added")
    public void i_hower_over_status_of_the_newly_email_added() {
        hover(crmAddContactInfoPage.firstEmailIDStatus);
    }

    @Then("I verify that the start and end dates are displayed for email address")
    public void i_verify_that_the_start_and_end_dates_are_displayed_for_email_address() {
        Assert.assertTrue(getHoverText().containsKey("START DATE"));
        Assert.assertTrue(getHoverText().containsKey("END DATE"));
    }

    @Then("I verify that emailID has been updated")
    public void i_verify_that_emailID_has_been_updated() throws Throwable {
        Assert.assertNotEquals(getText(crmAddContactInfoPage.firstEmailID), existingMailID);
    }


    @Then("I verify that deactivate immediately checkbox is available")
    public void i_verify_that_deactivate_immediately_checkbox_is_available() throws Throwable {
        Assert.assertTrue(crmAddContactInfoPage.inactiveImmediatelyOption.isDisplayed());
    }

    @Then("I verify that on clicking the deactivate immediately button end date goes blank")
    public void i_verify_that_on_clicking_the_deactivate_immediately_button_end_date_goes_blank() throws Throwable {
        click(crmAddContactInfoPage.inactiveImmediatelyCheckbox);
        System.out.println("value of email id field is " + getText(crmAddContactInfoPage.endDate));
        Assert.assertEquals(getText(crmAddContactInfoPage.endDate), "");
    }


    @Then("I update the end date of emailID")
    public void i_update_the_emd_date_of_emailID() throws Throwable {
        newEndDate = getGreaterDate(300);
        clearAndFillText(crmAddContactInfoPage.endDate, newEndDate);
        crmAddContactInfoPage.saveButton.click();
    }

    @Then("I verify that end date has been updated")
    public void i_verify_that_end_date_has_been_updated() throws Throwable {
        hover(crmAddContactInfoPage.firstEmailIDStatus);
        System.out.println("hover TExt " + getHoverText().get("END DATE").replaceAll("/", ""));
        Assert.assertEquals(getHoverText().get("END DATE").replaceAll("/", ""), newEndDate);
    }

    @Then("I update the start date of emailID")
    public void i_update_the_start_date_of_emailID() throws Throwable {
        newStartDate = getPriorDate(300);
        clearAndFillText(crmAddContactInfoPage.startDate, newStartDate);
        crmAddContactInfoPage.saveButton.click();
    }

    @Then("I verify that start date has been updated")
    public void i_verify_that_end_start_has_been_updated() throws Throwable {
        hover(crmAddContactInfoPage.firstEmailIDStatus);
        System.out.println("hover TExt " + getHoverText().get("START DATE").replaceAll("/", ""));
        Assert.assertEquals(getHoverText().get("START DATE").replaceAll("/", ""), newStartDate);
    }

    @Then("I verify that the active emails are displayed on the top")
    public void i_verify_that_the_active_emails_are_displayed_on_the_top() {
        //Adding first emailID with inactive status
       /* String emailID1 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID1);
       *//* crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*//*
        clearAndFillText(crmAddContactInfoPage.startDate, "10122016");
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
        crmAddContactInfoPage.saveButton.click();
        demographicContactInfoPage.addEmailButton.click();

        // Adding second EmailID with active status
        String emailID2 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID2);
       *//* crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*//*
        clearAndFillText(crmAddContactInfoPage.startDate, "10122018");
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(365));
        crmAddContactInfoPage.saveButton.click();
        demographicContactInfoPage.addEmailButton.click();

        // Adding third EmailID with active status
        String emailID3 = randomdata.get().get("email").toString();
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailID3);
        *//*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*//*
        clearAndFillText(crmAddContactInfoPage.startDate, "10122018");
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(365));
        crmAddContactInfoPage.saveButton.click();*/

        //verify that Active mails are on top
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
        TreeMap<String, String> recordsOnUI = getRecordsOrder();
        int i = 1;
        for (Map.Entry<String, String> entry : recordsOnUI.entrySet()) {
            if (i == 1 || i == 2)
                Assert.assertEquals(entry.getValue(), "ACTIVE");
            else
                Assert.assertEquals(entry.getValue(), "INACTIVE");
            i++;
        }
    }


    @Then("Active primary individuals are displayed on top followed by future and inactive")
    public void active_primary_individuals_are_displayed_on_top_followed_by_future_and_inactive() throws StaleElementReferenceException {
        int i = 1;
        String previous = "ACTIVE";
        String previous1 = "FUTURE";
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[1]/li/a"));
        System.out.println(pages.size());
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));

        try {
            while (next.isDisplayed()) {

                for (WebElement email : crmAddNewEmailPage.listOfEMail) {
                    List<WebElement> listEmailOfStatus = email.findElements(By.xpath("td[3]"));

                    for (WebElement e : listEmailOfStatus) {
                        String status = e.getText();
                        //System.out.println(previous + "    " + status);
                        //System.out.println(previous1 + "    " + status);
                        assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") ||
                                previous.equals("ACTIVE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("INACTIVE") ||
                                previous1.equals("INACTIVE") && status.equals("INACTIVE"));
                        assertFalse(previous1.equals("INACTIVE") && previous.equals("FUTURE") && status.equals("ACTIVE"));
                        previous = status;
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }

    }

    /* Verifies the order of the status are sorted in active, future and inactive status
        Author - Aswath
        Date - 03/04/2019
        Parameters -
     */


    @Then("Active Emails are displayed on top followed by future and inactive")
    public void active_emails_are_displayed_on_top_followed_by_future_and_inactive() throws StaleElementReferenceException {
        int i = 1;
        String previous = "ACTIVE";
        String previous1 = "FUTURE";
        String previous2 = "INACTIVE";
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[3]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a/span[text()='arrow_forward']"));
        try {
            while (next.isDisplayed()) {
                for (WebElement email : crmAddNewEmailPage.listOfEMail) {
                    List<WebElement> listEmailOfStatus = email.findElements(By.xpath("td[3]"));
                    for (WebElement e : listEmailOfStatus) {
                        String status = e.getText();
                        assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") ||
                                previous.equals("ACTIVE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("INACTIVE") ||
                                previous2.equals("INACTIVE") && status.equals("INACTIVE"));
                        assertFalse(previous1.equals("INACTIVE") && previous.equals("FUTURE") && status.equals("ACTIVE"));
                        previous = status;
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }
    }

    /* Adds four new email ID with Active and Inactive status
    Author - Aswath
    Date - 02/25/2019
    Parameters - NA
    */
    //Review - reduce wait time to max 5 seconds
    @When("I add emailId's with active and inactive status")
    public void i_add_emailId_s_with_active_and_inactive_status() {

        //Adding first emailID with past start date and no end date - Active
        String emailID1 = randomdata.get().get("email").toString();
        String conEmailID1 = getRandomString(5) + emailID1;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID1);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(2));
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        demographicContactInfoPage.addEmailButton.click();

//Adding 2 emailID with past start date and no end date - Active
        String emailID2 = randomdata.get().get("email").toString();
        String conEmailID2 = getRandomString(5) + emailID2;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID2);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getCurrentDate());
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        demographicContactInfoPage.addEmailButton.click();

//Adding 3 emailID with past start date and no end date - Future
        String emailID3 = randomdata.get().get("email").toString();
        String conEmailID3 = getRandomString(5) + emailID3;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID3);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(12));
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        demographicContactInfoPage.addEmailButton.click();

//Adding 4 emailID with past start date and end date - Future
        String emailID4 = randomdata.get().get("email").toString();
        String conEmailID4 = getRandomString(5) + emailID4;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID4);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(4));
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(14));
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        demographicContactInfoPage.addEmailButton.click();

//Adding 5 emailID with past start date and end date - inactive
        String emailID5 = randomdata.get().get("email").toString();
        String conEmailID5 = getRandomString(5) + emailID5;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID5);
       /* crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(10));
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
        demographicContactInfoPage.addEmailButton.click();

//Adding 6 emailID with past start date and no end date - Inactive
        String emailID6 = randomdata.get().get("email").toString();
        String conEmailID6 = getRandomString(5) + emailID6;
        waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID6);
       /* crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(4));
        waitFor(3);
        clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
        waitForVisibility(crmAddContactInfoPage.saveButton, 5);
        crmAddContactInfoPage.saveButton.click();
        waitForPageToLoad(10);
    }

    @Then("I verify that {int} emails are display at first glance")
    public void i_verify_that_three_emails_are_display_at_first_glance(int numOfRecords) {
        scrollToElement(crmAddContactInfoPage.emailAddressHeader);
        List<WebElement> emailIDRows = Driver.getDriver().findElements(By.xpath("//th[contains(text(),'EMAIL ADDRESS')]/ancestor::table/tbody"));
        Assert.assertEquals(emailIDRows.size(), numOfRecords);
    }

    @Then("I verify pagination is available when more than three records are added")
    public void validatePaginationOnRecordsPage() {

        Assert.assertEquals(getPaginationSize("email"), 3);
    }

    public void getEmailDates() {

        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a/span[text()='arrow_forward']"));
        int i = 1;
        try {
            do {
                if (i != 1)
                    next.click();
                for (WebElement pi : crmAddNewEmailPage.listOfEMail) {
                    waitForVisibility(pi, 5);
                    //scrollDown ();
                    scrollToElement(pi);
                    WebElement eleEmailStatus = pi.findElement(By.xpath("./td[3]"));
                    waitForVisibility(eleEmailStatus, 5);
                    String emailStatus = eleEmailStatus.getText();
                    hover(eleEmailStatus);
                    waitForVisibility(Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")), 5);
                    String startDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")).getText();
                    String endDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[2]/h6")).getText();
                    if (emailStatus.equals("ACTIVE"))
                        emailActiveStartDate.add(startDate);
                    else if (emailStatus.equals("FUTURE"))
                        emailFutureStartDate.add(startDate);
                    else if (emailStatus.equals("INACTIVE")) {
                        emailInactiveEndDate.add(endDate);
                    }
                }
                // Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[3]/li/a[text()='" + i + "']")).click();
                i++;
            } while (next.isDisplayed());
            System.out.println("Reading dates is completed");
        } catch (Exception e) {
        }


    }

    @Then("I edit already created email and click on save")
    public void i_edit_already_created_email_and_click_on_save() {

        emailUIprovided1.set(randomdata.get().get("email").toString());
        waitFor(6);
        clearAndFillText(crmAddContactInfoPage.emailAddressField, emailUIprovided1.get());
        waitFor(2);
        date = getGreaterDate(10);
        endDate = getGreaterDate(50);
        clearAndFillText(crmAddContactInfoPage.startDate, date);
        clearAndFillText(crmAddContactInfoPage.endDate, endDate);
        crmAddContactInfoPage.saveButton.click();
        waitFor(5);
    }

    @When("I add {int} random emailId's with random status")
    public void addMultipleEmailIds(int noOfEmailIds) {

        for (int i = 0; i < noOfEmailIds; i++) {
            demographicContactInfoPage.addEmailButton.click();
            String emailID1 = randomdata.get().get("email").toString();
            String conEmailID1 = getRandomString(3) + emailID1;
            waitForVisibility(crmAddContactInfoPage.emailAddressField, 10);
            clearAndFillText(crmAddContactInfoPage.emailAddressField, conEmailID1);
        /*crmAddContactInfoPage.associatedCaseMember.click();
        waitFor(1);
        scrollToElement(crmAddContactInfoPage.dropdownLastItem);
        hover(crmAddContactInfoPage.dropdownLastItem);
        crmAddContactInfoPage.dropdownLastItem.click();*/
            waitFor(3);
            int flag = Integer.parseInt(getRandomNumber(2)) % 3;
            if (flag == 0) {
                clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(2));
                clearAndFillText(crmAddContactInfoPage.endDate, getPriorDate(2));
            } else if (flag == 1) {
                clearAndFillText(crmAddContactInfoPage.startDate, getGreaterDate(2));
                clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(10));
            } else {
                clearAndFillText(crmAddContactInfoPage.startDate, getPriorDate(2));
                clearAndFillText(crmAddContactInfoPage.endDate, getGreaterDate(10));
            }

            waitForVisibility(crmAddContactInfoPage.saveButton, 5);
            crmAddContactInfoPage.saveButton.click();
            waitForPageToLoad(10);
        }


    }

    @Then("I am able to view the following {string} for each Email listed")
    public void i_am_able_to_view_the_following_for_each_Email_listed(String string, List<String> details) {
        Assert.assertTrue(crmAddContactInfoPage.emailAddressHeader.isDisplayed(), "Details not visible");
        Assert.assertEquals(crmAddContactInfoPage.emailAddressHeader.getText(), details.get(0), "Details do not match expected");
        Assert.assertTrue(crmAddContactInfoPage.emailStatusHeader.isDisplayed(), "Details not visible");
        Assert.assertEquals(crmAddContactInfoPage.emailStatusHeader.getText(), details.get(1), "Details do not match expected");
    }

    @And("I select the first Email listed")
    public void iSelectTheFirstEmailListed() {
        click(crmAddContactInfoPage.firstEmailRecord);
    }

    @Then("I am able to view Email Start and End Date")
    public void iAmAbleToViewEmailStartAndEndDate() {
        Assert.assertTrue(crmAddContactInfoPage.startDate.isDisplayed(), "Field is not visible");
        Assert.assertTrue(crmAddContactInfoPage.endDate.isDisplayed(), "Field is not visible");
    }

    @Then("I see {int} records visible per page of Emails")
    public void i_see_records_visible_per_page_of_Emails(int recordCount) {
        Assert.assertTrue(crmAddContactInfoPage.emailRecordDetails.size() <= recordCount, "Amount of records does not patch expected");
    }

    @Then("I see Email records in the following order by {string}")
    public void i_see_Email_records_in_the_following_order_by(String string, List<String> statuses) {
        List<String> allStatus = new ArrayList<>();
        for (int i = 0; i < crmAddContactInfoPage.emailPagination.size() - 1; i++) {
            for (WebElement recordStatus : crmAddContactInfoPage.emailRecordStatuses) {
                if (recordStatus.getText().equalsIgnoreCase(statuses.get(0))) {
                    String status = recordStatus.getText();
                    allStatus.add(status);
                    Assert.assertFalse(allStatus.contains(statuses.get(1)), "Status not in expected order");
                    Assert.assertFalse(allStatus.contains(statuses.get(2)), "Status not in expected order");
                } else if (recordStatus.getText().equalsIgnoreCase(statuses.get(1))) {
                    String status = recordStatus.getText();
                    allStatus.add(status);
                    Assert.assertFalse(allStatus.contains(statuses.get(2)), "Status not in expected order");
                }
            }
            click(crmAddContactInfoPage.emailPagination.get(i + 1));
        }
    }

    @Then("I verify consumer field in the Add email page")
    public void verify_consumer_field_in_the_add_email_page() {
        waitFor(2);
        Assert.assertTrue(crmAddContactInfoPage.consumer.isDisplayed(), "Primary case email check box missing in add Phone Number page");
    }

    @Then("I verify primary case email check box is available and disabled")
    public void verify_primary_case_email_checkbox_and_its_disabled_status_in_add_phone_num_page() {
        Assert.assertTrue(addContactInfo.primaryCasePhoneOrEmailCheckBox.isDisplayed(), "Primary case phone check box missing in add Phone Number page");
        Assert.assertTrue(addContactInfo.primaryCasePhoneOrEmailCheckBox.getAttribute("aria-disabled").contains("true"), "Primary case phone check box is not disabled in add Phone Number page");
    }

    @Then("I verify primary case email check box is enabled by entering test data")
    public void verify_primary_case_email_check_box_is_enabled_by_entering_test_data(List<Map<String, String>> data) {
        for (Map<String, String> record : data) {
            waitFor(3);
            selectRandomDropDownOption(crmAddContactInfoPage.consumer);
            clearAndFillText(crmAddContactInfoPage.emailAddressField, "testPrimaryIndicator@test.com");
            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                if (sDate.contains("CD"))
                    sDate = getCurrentDate();
                else
                    sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.startDate, sDate);
                crmAddContactInfoPage.emailAddressField.click();
            }
            if (record.get("endDate") != null && !record.get("endDate").isEmpty()) {
                String eDate = record.get("endDate");
                if (eDate.contains("CD"))
                    eDate = getCurrentDate();
                else
                    eDate = eDate.contains("P") ? getPriorDate(Integer.parseInt(eDate.substring(1))) : getGreaterDate(Integer.parseInt(eDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.endDate, eDate);
            }
        }
        waitFor(2);
        Assert.assertTrue(addContactInfo.primaryCaseEnabledCheckBox.isEnabled(), "Primary case phone check box is disabled in add Phone Number page");
    }

    @When("I add new Email")
    public void i_add_new_Email() {
        selectRandomDropDownOption(crmAddContactInfoPage.consumer);
        emailIDToBeValidated.set(randomdata.get().get("email").toString());
        APIContactRecordEventsController.emailIDToBeValidated.set(emailIDToBeValidated.get());
        clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());
    }

    @And("I add new Email with Active, Future and Inactive status with primary case checked")
    public void i_add_new_New_Email_with_Active_Future_and_Inactive_status_with_primary_case_checked(List<Map<String, String>> data) {
        int counter = 0;
        for (Map<String, String> record : data) {
            waitFor(2);
            if ((counter > 0) && demographicContactInfoPage.addEmailButton.isDisplayed()) {
                jsClick(demographicContactInfoPage.addEmailButton);
                waitFor(2);
            }

            selectRandomDropDownOption(crmAddContactInfoPage.consumer);
            emailIDToBeValidated.set(randomdata.get().get("email").toString());
            APIContactRecordEventsController.emailIDToBeValidated.set(emailIDToBeValidated.get());
            clearAndFillTextWithActionClass(crmAddContactInfoPage.emailAddressField, emailIDToBeValidated.get());

            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                if (sDate.contains("CD"))
                    sDate = getCurrentDate();
                else
                    sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.startDate, sDate);
            }
            if (record.get("endDate") != null && !record.get("endDate").isEmpty()) {
                String eDate = record.get("endDate");
                if (eDate.contains("CD"))
                    eDate = getCurrentDate();
                else
                    eDate = eDate.contains("P") ? getPriorDate(Integer.parseInt(eDate.substring(1))) : getGreaterDate(Integer.parseInt(eDate.substring(1)));
                clearAndFillText(crmAddContactInfoPage.endDate, eDate);
            } else {
                crmAddContactInfoPage.endDate.click();
                crmAddContactInfoPage.endDate.sendKeys(Keys.TAB);
                crmAddContactInfoPage.endDate.sendKeys(Keys.TAB);
            }
            waitFor(1);
            //add a tab
            if (addContactInfo.primaryCaseEnabledCheckBox.isEnabled()) {
                addContactInfo.primaryCaseEnabledCheckBox.click();
                waitFor(1);
            }

            waitFor(2);
            if (crmAddContactInfoPage.popupAlertContinueButton1.size() > 0) {
                if (crmAddNewEmailPage.isPopupAlertContinueButtonExist()) {
                    waitFor(1);
                    System.out.println("Observed that Continue button exist on alert popup");
                    if (crmAddNewEmailPage.isPopupAlertCancelButtonExist()) {
                        System.out.println("Observed that Cancel button exist on alert popup");
                        crmAddContactInfoPage.popupAlertCancelButton.click();
                        waitFor(1);
                        Assert.assertTrue(crmAddContactInfoPage.saveButton.isDisplayed(), "User is not in Add Email page");
                        waitFor(2);
                        crmAddContactInfoPage.saveButton.click();
                        waitFor(2);
                        if (crmAddNewEmailPage.isPopupAlertContinueButtonExist()) {
                            jsClick(crmAddContactInfoPage.popupAlertContinueButton);
                        }
                    }
                }

            } else {
                crmAddContactInfoPage.saveButton.click();
            }

            waitFor(2);
            if (crmAddNewEmailPage.isPopupAlertContinueButtonExist()) {
                jsClick(crmAddContactInfoPage.popupAlertContinueButton);
            }
            waitFor(2);
            Assert.assertTrue(demographicContactInfoPage.addEmailButton.isDisplayed(), "User is not in contact detail page");
            counter++;
        }
    }

    @Then("I verify Email section fields displayed")
    public void i_verify_Email_section_fields_displayed() {
        Assert.assertTrue(crmAddContactInfoPage.consumer.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.startDate.isDisplayed());
        Assert.assertTrue(crmAddContactInfoPage.endDate.isDisplayed());
    }

    @Then("I verify primary email should be first one with primary indicator")
    public void I_verify_primary_email_should_be_first_one_with_primary_indicator() {
        waitFor(2);
        assertTrue(addContactInfo.chkFirstEmailWithStar.isDisplayed(), "First Email is not primary since no star displayed");
    }

    @Then("I verify primary email alt text should have Primary case email address on mouse hover")
    public void I_verify_primary_email_alt_text_should_have_Primary_case_email_address_on_mouse_hover(List<Map<String, String>> data) {
        waitFor(2);
        hover(addContactInfo.chkFirstEmailWithStar);
        assertEquals(addContactInfo.chkFirstEmailWithStar.getAttribute("title"), data.get(0).get("Indicator_text"));
    }

    @Then("I verify Email addresses are displayed in sorted order")
    public void I_verify_Email_addresses_are_displayed_in_sorted_order(List<Map<String, String>> data) {
        ArrayList<String> allPageStatuses = new ArrayList<String>();
        List<WebElement> pagination = Driver.getDriver().findElements(addContactInfo.paginationEmailAddress);
        for (int i = 0; i < pagination.size() - 1; i++) {
            List<String> statues = null;
            if (i > 0)
                Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//ul/li/a[text()='" + (i + 1) + "']")).click();
            statues = getElementsText(addContactInfo.emailAddressStatus);
            for (String status : statues) {
                allPageStatuses.add(status.toString());
            }
        }
        System.out.println("Email Address statuses are -->" + allPageStatuses);
        Assert.assertTrue(Ordering.natural().isOrdered(allPageStatuses), " Statuses are not sorted -->" + allPageStatuses);

    }

    @Then("I verify newly added Email contact info fields")
    public void I_verify_newly_added_Email_contact_info_fields(List<Map<String, String>> data) {

        for (Map<String, String> field : data) {
            Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//table//thead//th[text()='" + field.get("Fields") + "']")).isDisplayed(), field.get("Fields") + " filed is missing");
        }
    }

    @Then("I verify if email input field does not accept below invalid test data")
    public void iVerifyIfEmailInputFieldDoesNotAcceptBelowInvalidTestData(List<Map<String, String>> data) {

        waitFor(2);
        for (Map<String, String> column : data) {
            boolean isFound = false;
            waitFor(1);
            clearAndFillText(crmAddContactInfoPage.emailAddressField, column.get("InvalidEmail"));
            click(crmAddContactInfoPage.saveButton);
            waitFor(1);

            try {
                isFound = crmAddContactInfoPage.invalidEmailAddressError.isDisplayed();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            assertTrue(isFound, "Email Address invalid error message didn't appeared::" + column);
        }

    }

    @Then("I verify email input field accepts below valid test data.")
    public void iVerifyEmailInputFieldAcceptsBelowValidTestData(List<Map<String, String>> data) {

        waitFor(2);
        for (Map<String, String> column : data) {
            boolean isFound = false;
            waitFor(1);
            clearAndFillText(crmAddContactInfoPage.emailAddressField, column.get("ValidEmail"));
            click(crmAddContactInfoPage.saveButton);
            waitFor(1);
            try {
                isFound = crmAddContactInfoPage.invalidEmailAddressError.isDisplayed();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            assertFalse("Email Address invalid error message appeared::" + column, isFound);
        }
    }

    @When("I select first consumer with email")
    public void i_select_first_consumer_with_email() {
        waitFor(1);
        addContactInfo.chkFirstEmailWithStar.click();
    }

    @Then("I verify email source dropdown is displayed and has only one value Consumer Reported")
    public void verifyEmailSource(){
        waitFor(2);
        assertTrue(isElementDisplayed(addContactInfo.emailSourceDropDown), "emailSourceDropDown not displayed");
    }

}
