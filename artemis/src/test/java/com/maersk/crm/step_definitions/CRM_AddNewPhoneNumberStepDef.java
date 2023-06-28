package com.maersk.crm.step_definitions;

import com.google.common.collect.Ordering;
import com.maersk.crm.api_step_definitions.APIContactRecordEventsController;
import com.maersk.crm.pages.crm.CRMAddContactInfoPage;
import com.maersk.crm.pages.crm.CRMCaseAndConsumerSearchPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.*;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class CRM_AddNewPhoneNumberStepDef extends CRMUtilities implements ApiBase {

    CRMCreateConsumerProfilePage createConsumer = new CRMCreateConsumerProfilePage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage addContactInfo = new CRMAddContactInfoPage();
    CRMCaseAndConsumerSearchPage caseConsumerSearchPage = new CRMCaseAndConsumerSearchPage();
    CRMBusinessEvents businessEvents = new CRMBusinessEvents();

    private ThreadLocal<String> getDate = ThreadLocal.withInitial(String::new);
    private String phoneComment;
    public ThreadLocal<String> phoneNumber = ThreadLocal.withInitial(String::new);
    private String conPhoneNumber;
    public static String phoneType = "Work";
    public static String phoneTypeHome = "Home";
    public static String comments = "Edited phone Comments passed";
    List<String> ActiveStartDate = new ArrayList<>();
    final List<String> FutureStartDate = new ArrayList<>();
    List<String> InactiveEndDate = new ArrayList<>();
    private ThreadLocal<String> consumerName = new ThreadLocal<>();
    private boolean isAlreadyPhoneExist = false;
    public String associatedCaseMember;
    public ThreadLocal<String> effectiveStartDate = ThreadLocal.withInitial(String::new);;
    public ThreadLocal<String> effectiveEndDate = ThreadLocal.withInitial(String::new);;

    static String phone;


    @When("I click on the Add Phone Number button on Contact Info Page")
    public void i_click_on_the_Add_Phone_Number_button_on_Contact_Info_Page() {
        waitFor(2);
        demographicContactInfo.addPhoneButton.click();
        waitForVisibility(addContactInfo.saveButton, 10);
        //waitFor(1);
        assertTrue(addContactInfo.saveButton.isDisplayed(), "Not navigated to add Phone Number Page");
    }


    @Then("I see all the fields are present on Add Phone Number Page")
    public void i_see_all_the_fields_are_present_on_Add_Phone_Number_Page() {
        waitForPageToLoad(10);
        assertTrue(addContactInfo.phoneNumber.isDisplayed(), "Phone Number field is not displayed");
        assertTrue(addContactInfo.phoneType.isDisplayed(), "Phone Type field is not displayed");
        assertTrue(addContactInfo.phoneComments.isDisplayed(), "Phone Comments field is not displayed");
        assertTrue(addContactInfo.startDate.isDisplayed(), "Phone Start Date field is not displayed");
        assertTrue(addContactInfo.endDate.isDisplayed(), "Phone End Date field is not displayed");
//        assertTrue(addContactInfo.inactiveImmediatelyCheckbox.isDisplayed(), "Inactivate Immediately check box is not displayed");
        assertTrue(addContactInfo.saveButton.isDisplayed(), "Save Phone Number button is not displayed");
        assertTrue(addContactInfo.cancelButton.isDisplayed(), "Cancel Phone Number button is not displayed");
//        assertTrue(addContactInfo.associatedCaseMember.isDisplayed(), "Consumer dropdown field is not displayed");
    }

    @Then("I do not provide any data in all the fields")
    public void i_do_not_provide_any_data_in_all_the_fields() {
        addContactInfo.phoneNumber.clear();
    }

    @When("I provide invalid data for Phone Number, Start Date and End Date")
    public void i_provide_invalid_data_for_Phone_Number_Start_Date_and_End_Date() {
        scrollUpUsingActions(3);
        waitFor(3);
        System.out.println("will edit a phone number");
        clearAndFillText(addContactInfo.phoneNumber, "111111");
        clearAndFillText(addContactInfo.startDate, "02/32/210");
        clearAndFillText(addContactInfo.endDate, "09/31/201");
        addContactInfo.saveButton.click();
    }

    @Then("I see invalid field error message displayed below Phone Number, Start Date and End Date fields")
    public void i_see_invalid_field_error_message_displayed_below_Phone_Number_Start_Date_and_End_Date_fields() {
        waitForVisibility(addContactInfo.phoneNumberErrorMessage, 10);
        assertTrue(addContactInfo.phoneNumberErrorMessage.isDisplayed(), "Invalid format Phone Number Error is not displayed");
        assertTrue(addContactInfo.startDateFormatError.isDisplayed(), "Incorrect Date Error not displayed");
        assertTrue(addContactInfo.endDateFormatError.isDisplayed(), "invalid Date Error not displayed");
    }

    @When("I provide valid data in all the fields on Add Phone Number page")
    public void i_provide_valid_data_in_all_the_fields_on_Add_Phone_Number_page() {
        phoneNumber.set(getRandomNumber(10));
        waitFor(2);
        scrollUpUsingActions(3);
//        selectRandomDropDownOption(addContactInfo.consumer);
        clearAndFillText(addContactInfo.phoneNumber, phoneNumber.get());
        waitForClickablility(addContactInfo.phoneType, 10);
        selectDropDown(addContactInfo.phoneType, phoneType);
//        addContactInfo.getConsumerDropdownOption(2);
//        associatedCaseMember = addContactInfo.associatedCaseMember.getText();
        waitFor(2);
        clearAndFillText(addContactInfo.phoneComments, comments);
        waitFor(4);
    }

    @When("I set start Date as {string} and end Date {string}")
    public void i_set_start_Date_as_and_end_Date(String startDate, String endDate) {
        if (startDate.equals("past")) {
            clearAndFillText(addContactInfo.startDate, getPriorDate(4));
        } else if (startDate.equals("current")) {
            clearAndFillText(addContactInfo.startDate, getPriorDate(1));
        } else {
            clearAndFillText(addContactInfo.startDate, getGreaterDate(2));
        }
        if (endDate.equals("past")) {
            clearAndFillText(addContactInfo.endDate, getPriorDate(2));
        } else if (endDate.equals("future")) {
            clearAndFillText(addContactInfo.endDate, getGreaterDate(4));
        }
        effectiveStartDate.set(addContactInfo.startDate.getAttribute("value"));
        effectiveEndDate.set(addContactInfo.endDate.getAttribute("value"));
        addContactInfo.saveButton.click();

    }

    @Then("I verify Consumer label in Phone Number details table")
    public void verify_consumer_label_in_phone_num_table() {
        Assert.assertTrue(addContactInfo.phoneNumConsumerLabel.isDisplayed(), "Consumber label  missing in Phone Number details table");
    }

    @Then("I verify Consumer field in Add Phone Number page")
    public void verify_consumer_field_in_add_phone_num_page() {
        Assert.assertTrue(addContactInfo.consumer.isDisplayed(), "Consumber field  missing in add Phone Number page");
    }

    @Then("I verify primary case phone check box is available and disabled")
    public void verify_primary_case_phone_checkbox_and_its_disabled_status_in_add_phone_num_page() {
        Assert.assertTrue(addContactInfo.primaryCasePhoneOrEmailCheckBox.isDisplayed(), "Primary case phone check box missing in add Phone Number page");
        Assert.assertTrue(addContactInfo.primaryCasePhoneOrEmailCheckBox.getAttribute("aria-disabled").contains("true"), "Primary case phone check box is not disabled in add Phone Number page");
    }

    @Then("I verify primary case phone check box is enabled by entering test data")
    public void verify_primary_case_phone_check_box_is_enabled_by_entering_test_data(List<Map<String, String>> data) {
        for (Map<String, String> record : data) {
            phoneNumber.set(getRandomNumber(10));
            APIContactRecordEventsController.phoneNumberToBeValidated.set(phoneNumber.get());
            selectRandomDropDownOption(addContactInfo.consumer);
            clearAndFillText(addContactInfo.phoneNumber, phoneNumber.get());
            waitForClickablility(addContactInfo.phoneType, 10);
            selectDropDown(addContactInfo.phoneType, record.get("phoneType"));

            waitFor(5);
            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(addContactInfo.startDate, sDate);
            }
            waitFor(2);
            clearAndFillText(addContactInfo.phoneComments, record.get("comments"));
            waitFor(1);
            //add a tab
            if (addContactInfo.primaryCaseEnabledCheckBox.isEnabled()) {
                addContactInfo.primaryCaseEnabledCheckBox.click();
                waitFor(1);
            }

            addContactInfo.saveButton.click();
            waitFor(3);
        }
        waitFor(2);
        // Assert.assertTrue(addContactInfo.primaryCaseEnabledCheckBox.isEnabled(), "Primary case phone check box is disabled in add Phone Number page");

        if (addContactInfo.isPopupAlertContinueButtonExist()) {
            addContactInfo.popupAlertContinueButton.click();
        }

    }

    @Then("I verify Last Updated label in Phone Number details table")
    public void verify_last_updated_label_in_phone_num_table() {
        Assert.assertTrue(addContactInfo.phoneNumLastUpdatedLabel.isDisplayed(), "Last Updated label  missing in Phone Number details table");
    }


    /*  Description: Adds the new Phone number with 2 Active, 2 Future and 2 Inactive state record
        Author - Aswath
        Date - 02/22/2019
        Parameters - No input
     */
    @Then("verify the different statuses of {string} are sorted")
    public void verify_the_different_statuses_of_Phone_Number(String field) {

        ArrayList<String> expectedStatus = new ArrayList<String>();
        List<WebElement> pages = null;

        if (field.contains("Email")) {
            pages = Driver.getDriver().findElements(addContactInfo.paginationEmailAddress);
        } else {

            pages = Driver.getDriver().findElements(addContactInfo.paginationPhoneNumber);
        }

        for (int i = 0; i < pages.size() - 1; i++) {

            List<String> statues = null;

            if (field.contains("Email")) {
                if (i > 0)
                    Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][3]//ul/li/a[text()='" + (i + 1) + "']")).click();
                statues = getElementsText(addContactInfo.emailAddressStatus);
            } else {
                if (i > 0)
                    Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-')][2]//ul/li/a[text()='" + (i + 1) + "']")).click();
                statues = getElementsText(addContactInfo.phoneNumberStatus);
            }

            for (String status : statues) {

                if (expectedStatus.contains(status)) {
                    expectedStatus.add(status.toString());
                }
            }

        }

        Assert.assertTrue(Ordering.natural().isOrdered(expectedStatus), field + " Statuses are not sorted -->" + expectedStatus);
    }

    @And("I verify Alt text for Primary Phone Num Icon is {string}")
    public void verify_alt_text_of_primary_phone_num(String alt) {
        waitFor(3);
        String actAlt = addContactInfo.checkPrimaryPhoneNum.getAttribute("title");
        System.out.println("Text of element " + actAlt);
        Assert.assertTrue(actAlt.trim().equalsIgnoreCase(alt), "Alt text property mis-match:::" + actAlt);
    }

    @When("I add new Phone Number")
    public void i_add_new_Phone_Number() {
        waitFor(2);
        demographicContactInfo.addPhoneButton.click();
        waitFor(3);
        clearAndFillText(addContactInfo.phoneNumber, "4126452975");
    }

    @Then("I verify Phone Number section fields displayed")
    public void i_verify_Phone_Number_section_fields_displayed() {
        Assert.assertTrue(addContactInfo.phoneNumber.isDisplayed());
        Assert.assertTrue(addContactInfo.phoneType.isDisplayed());
        Assert.assertTrue(addContactInfo.phoneComments.isDisplayed());
    }

    @And("I add new phone number with Active, Future and Inactive status")
    public void i_add_new_New_Phone_number_with_Active_Future_and_Inactive_status(List<Map<String, String>> data) {

        for (Map<String, String> record : data) {
            i_click_on_the_Add_Phone_Number_button_on_Contact_Info_Page();
            waitFor(3);
            phoneNumber.set(getRandomNumber(10));
//            selectRandomDropDownOption(addContactInfo.consumer); this functionality is only for consumer on case
            clearAndFillText(addContactInfo.phoneNumber, phoneNumber.get());
            waitForClickablility(addContactInfo.phoneType, 10);
            scrollUpUsingActions(3);
            selectDropDown(addContactInfo.phoneType, record.get("phoneType"));
            waitFor(2);
            clearAndFillText(addContactInfo.phoneComments, record.get("comments"));
            waitFor(5);
            if (record.get("startDate") != null && !record.get("startDate").isEmpty()) {
                String sDate = record.get("startDate");
                sDate = sDate.contains("P") ? getPriorDate(Integer.parseInt(sDate.substring(1))) : getGreaterDate(Integer.parseInt(sDate.substring(1)));
                clearAndFillText(addContactInfo.startDate, sDate);
            }
            if (record.get("endDate") != null && !record.get("endDate").isEmpty()) {
                String eDate = record.get("endDate");
                eDate = eDate.contains("P") ? getPriorDate(Integer.parseInt(eDate.substring(1))) : getGreaterDate(Integer.parseInt(eDate.substring(1)));
                clearAndFillText(addContactInfo.endDate, eDate);
            }
            addContactInfo.saveButton.click();
            waitFor(3);
            int cancelButtonCount = Driver.getDriver().findElements(By.xpath("//button[contains(@class, 'mx-btn-cancel')]")).size();
            if (cancelButtonCount > 1) {
                System.out.println("Cancen button exisits with count " + cancelButtonCount);
                addContactInfo.cancelButton.click();
                waitFor(2);
                if (addContactInfo.popupAlertContinueButton.isDisplayed()) {
                    addContactInfo.popupAlertContinueButton.click();
                } else {
                    waitFor(2);
                    addContactInfo.popupAlertContinueButton.click();
                }
            }
        }
    }

    @Then("I verify edited phone number status as {string}")
    public void i_verify_edited_phone_number_status_as(String status) {
        waitFor(3);
        assertTrue(demographicContactInfo.statusOfNumberConsumerProfile.getText().equals(status));
    }

    @Then("I verify phone number status as {string}")
    public void i_verify_phone_number_status_as(String status) {
      //  waitForVisibility(getFirstElement(demographicContactInfo.phoneStatuses), 10);
        waitFor(3);
        for(int i=0; i<3;i++) {
            try {
                assertEquals(demographicContactInfo.phoneStatuses2.get(1).getText(), status);
                break;
            } catch (StaleElementReferenceException e) {
            }
        }
    }

    /* Phone number concatenation with hyphen and Verify the Phone number Status
       Author - Aswath
       Date - 02/22/2019
       Parameters - Accepts Status value
    */
    @Then("verify status of phone number as {string}")
    public void verify_status_of_phone_number_as_something(String status) {

        String pNum = phoneNumber.get();
        String threeNumFirst = pNum.substring(0, 3);
        String threeNumSecond = pNum.substring(3, 6);
        String fourNumLast = pNum.substring(6, 10);
        String conPhoneNumber = threeNumFirst + "-" + threeNumSecond + "-" + fourNumLast;

//        WebElement phoneStatus = Driver.getDriver().findElement(By.xpath("//td[.='" + conPhoneNumber + "']/following-sibling::td[4]"));
        assertEquals(demographicContactInfo.phoneStatuses2.get(1).getText(), status);
    }

    @Then("New Phone Number should be added to the Phone Number list")
    public void new_phone_number_should_be_added_to_the_phone_number_list() {
        boolean found = false;
        int i = 1;
        boolean nextButton = false;
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[2]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a/span[text()='arrow_forward']"));
        while (!found) {
            for (WebElement pn : addContactInfo.listOfPhoneNumbers) {
                List<WebElement> pnName = pn.findElements(By.xpath("td[1]"));
                for (WebElement number : pnName) {
                    if (conPhoneNumber.equalsIgnoreCase(number.getText())) {
                        found = true;
                    }
                    break;
                }
                if (found) break;
            }
            if (!found) {
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a[text()='" + i + "']")).click();
                i++;
            } else break;
        }
        assertTrue(found, "Phone Number added is not found");

    }

    /*Captures the Phone number Status by navigate all the pages
        Author - Aswath
        Date - 02/22/2019
        Parameters - Accepts Status value
        Changed Line 237 Xpath
     */
    public void getPhoneNumberDates() {
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a/span[text()='arrow_forward']"));
        int i = 2;
        try {
            while (next.isDisplayed()) {
                for (WebElement pn : addContactInfo.listOfPhoneNumbers) {
                    List<WebElement> listPhoneOfStatus = pn.findElements(By.xpath("td[6]"));
                    for (WebElement e : listPhoneOfStatus) {
                        hover(e);
                        waitFor(1);
//                        String startDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")).getText();
                        String startDate = Driver.getDriver().findElement(By.xpath("(//*[@role='tooltip']//h6)[1]")).getText();
//                        String endDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[2]/h6")).getText();
                        String endDate = Driver.getDriver().findElement(By.xpath("(//*[@role='tooltip']//h6)[2]")).getText();
                        if (e.getText().equals("ACTIVE")) {
                            waitFor(1);
                            synchronized (ActiveStartDate){
                                ActiveStartDate.add(startDate);
                            }
                        }
                        waitFor(1);
                        if (e.getText().equals("FUTURE")) {
                            waitFor(1);
                            synchronized (FutureStartDate){
                                FutureStartDate.add(startDate);
                            }
                        }

                        waitFor(1);
                        if (e.getText().equals("INACTIVE")) {
                            waitFor(1);
                            InactiveEndDate.add(endDate);
                        }
                    }
                }
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a[text()='" + i + "']")).click();
                i++;
            }

        } catch (Exception e) {
        }

    }
    /*  Verify order of the active state Phone number Status are in ascending order
        Author - Aswath
        Date - 02/22/2019
        Parameters -
        Added scrollToelement
     */

    @Then("Active Phone Number are sorted by start date in ascending order")
    public void active_phone_number_are_sorted_by_start_date_in_ascending_order() {
        scrollToElement(demographicContactInfo.phoneNumberSection);
        scrollDown();
        getPhoneNumberDates();
        assertTrue(ascendingOrderDatesString(ActiveStartDate), "Not Sorted in order");
    }

    /*  Verify order of the future state Phone number Status are in ascending order
        Author - Aswath
        Date - 02/22/2019
        Parameters -
     */

    @Then("Future Phone Number are sorted by start date in ascending order")
    public void future_phone_number_are_sorted_by_start_date_in_ascending_order() {
        assertTrue(ascendingOrderDatesString(FutureStartDate), "Not Sorted in order");
    }

    /*  Verify order of the Inactive state Phone number Status are in descending order
        Author - Aswath
        Date - 02/22/2019
        Bug is there for descending order
     */
    @And("Inactive Phone Number are sorted by end date in descending order")
    public void inactive_phone_number_are_sorted_by_end_date_in_descending_order() {
        assertTrue(descendingOrderDatesString(InactiveEndDate), "Not Sorted in order");
        //assertTrue (ascendingOrderDatesString(InactiveEndDate),"Not Sorted in order");
    }

    /* Verifying the order status as Active, Future and Inactive
    Author - Aswath
    Date - 02/27/2019
    Parameters -
    Changed the xpath as new column added-aswath
    */
    @Then("Active Phone Number are displayed on top followed by future and inactive")
    public void active_Phone_Number_are_displayed_on_top_followed_by_future_and_inactive() throws StaleElementReferenceException {
        scrollToElement(demographicContactInfo.phoneNumberSection);
        int i = 2;
        String previous = "ACTIVE";
        String previous1 = "FUTURE";
        String previous2 = "INACTIVE";

        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[2]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a/span[text()='arrow_forward']"));

        try {
            while (next.isDisplayed()) {
                for (WebElement pn : addContactInfo.listOfPhoneNumbers) {
                    List<WebElement> listOfStatus = pn.findElements(By.xpath("td[6]"));
                    waitFor(2);
                    for (WebElement e : listOfStatus) {
                        String status = e.getText();
                        assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") ||
                                previous.equals("ACTIVE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("INACTIVE") ||
                                previous2.equals("INACTIVE") && status.equals("INACTIVE"));

                        assertFalse(previous1.equals("INACTIVE") && previous2.equals("FUTURE") && status.equals("ACTIVE"));
                        previous = status;
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[2]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }
    }

    @Then("I provide valid data in all the fields and date as{string}")
    public void i_provide_valid_data_in_all_the_fields(String date) {
        scrollUpRobot();
        clearAndFillText(addContactInfo.phoneNumber, getRandomNumber(10));
        waitForClickablility(addContactInfo.phoneType, 10);
        selectDropDown(addContactInfo.phoneType, "Fax");
        waitFor(2);
//        addContactInfo.getConsumerDropdownOption(2);
        waitForClickablility(addContactInfo.phoneComments, 10);
        clearAndFillText2(addContactInfo.phoneComments, "Test phone Comments passed");
        waitFor(2);

        if (date.equalsIgnoreCase("current")) {
            getDate.set(getCurrentDateWithFormat());
            clearAndFillText(addContactInfo.startDate, getDate.get());
        } else if (date.equalsIgnoreCase("future")) {
            getDate.set(getGreaterDate(2));
            clearAndFillText(addContactInfo.startDate, getDate.get());
        } else {
            getDate.set(getPriorDate(2));
            clearAndFillText(addContactInfo.startDate, getDate.get());
        }
    }

    @Then("I click on Save button on add phone number page")
    public void i_click_on_Save_button_on_add_phone_number_page() {
        waitFor(3);
        addContactInfo.saveButton.click();
    }


    @Then("I am navigated back to Contact Info page")
    public void i_am_navigated_back_to_Contact_Info_page() {
        assertTrue(demographicContactInfo.addPhoneButton.isDisplayed(), "Add phone button on Contact Info Page is not displayed");

    }

    @Then("I see all Add New Phone page mandatory fields error messages displayed")
    public void i_see_all_Add_New_Phone_page_mandatory_fields_error_messages_displayed() {
//        waitForVisibility(addContactInfo.mainFillFieldMessage, 5);
//        assertTrue(addContactInfo.mainFillFieldMessage.isDisplayed(), "Main fill in fields message is not displayed");
        assertTrue(addContactInfo.enterPhoneError.isDisplayed(), "Fill Phone number field error message is not displayed");
//        assertTrue(addContactInfo.selectConsumerError.isDisplayed(), "Please select a Consumer error message is not displayed");
        assertTrue(addContactInfo.selectPhoneTypeError.isDisplayed(), "Choose Phone Type error message is not displayed");
        assertTrue(addContactInfo.enterStartDateError.isDisplayed(), "Select Start date error message is not displayed");
    }

    @When("I verify the current consumer has a phone number record displayed with status {string}")
    public void i_verify_the_current_consumer_has_a_phone_number_record_displayed_with_status(String status) {
        waitForVisibility(demographicContactInfo.phoneStatuses.get(0), 10);
        System.out.println(demographicContactInfo.phoneStatuses.size());
        //waitForVisibility(demographicContactInfo.phoneStatuses.get(0), 10);
        assertEquals(getFirstElement(demographicContactInfo.phoneStatuses2).getText(), status);
    }

    @When("I expend current phone number record to inactivate it immediately")
    public void i_expend_current_phone_number_record_to_inactivate_it_immediately() {
        waitForClickablility(demographicContactInfo.phoneStatuses.get(0), 5);
        demographicContactInfo.phoneStatuses2.get(0).click();
        waitForClickablility(addContactInfo.phoneType, 5);
        selectDropDown(addContactInfo.phoneType, "Work");
        addContactInfo.inactiveImmediatelyCheckbox.click();
        waitFor(3);
        addContactInfo.saveButton.click();
        for(int i=0; i<3;i++) {
            try {
                waitForVisibility(demographicContactInfo.phoneStatuses.get(0), 10);
                break;
            } catch (StaleElementReferenceException e) {
            }
        }
        }

    @Then("I see drop-down option for Phone Number Type")
    public void i_see_drop_down_option_for_Phone_Number_Type(List<String> options) {
        for (String option : options) {
            selectDropDown(addContactInfo.phoneType, option);
        }
    }

    @When("I expend current phone number record and navigated to Edit Phone Number Page")
    public void i_expend_current_phone_number_record_and_navigated_to_Edit_Phone_Number_Page() {
        waitFor(5);
        demographicContactInfo.phoneStatuses2.get(0).click();
        waitForPageToLoad(10);
        assertTrue(addContactInfo.addressStatus.isDisplayed(), "Should be navigated to Edit Phone Number Page");
    }

    @Then("I see all the fields are present on Edit Phone Number Page")
    public void i_see_all_the_fields_are_present_on_Edit_Phone_Number_Page() {
        waitForVisibility(addContactInfo.saveButton, 10);
        assertTrue(addContactInfo.phoneNumber.isDisplayed(), "Phone Number field is not displayed");
        assertTrue(addContactInfo.phoneType.isDisplayed(), "Phone Type field is not displayed");
        assertTrue(addContactInfo.phoneComments.isDisplayed(), "Phone Comments field is not displayed");
        assertTrue(addContactInfo.startDate.isDisplayed(), "Phone Start Date field is not displayed");
        assertTrue(addContactInfo.endDate.isDisplayed(), "Phone End Date field is not displayed");
        scrollDown();
        assertTrue(isElementDisplayed(addContactInfo.inactiveImmediatelyCheckbox), "Inactivate Immediately check box is not displayed");
        assertTrue(addContactInfo.saveButton.isDisplayed(), "Save Phone Number button is not displayed");
        assertTrue(addContactInfo.cancelButton.isDisplayed(), "Cancel Phone Number button is not displayed");
    }

    @Then("I verify edited phone number information is displayed on Contact Info Page")
    public void i_verify_edited_phone_number_information_is_displayed_on_Contact_Info_Page() {
        String expectedNumber = "("+phoneNumber.get().substring(0,3)+") "+phoneNumber.get().substring(3);
        String expectedType = phoneType;
        String expectedComments = comments;
        for(int i=0; i<3;i++) {
            try {
                waitForVisibility(demographicContactInfo.phoneNumbers.get(0), 10);
                String actualNumber = demographicContactInfo.phoneNumbers.get(0).getText().replaceAll("-", "");
                assertTrue(actualNumber.equals(expectedNumber), "Expected"+expectedNumber+ " Phone number is not displayed, actual " + actualNumber);
                break;
            } catch (StaleElementReferenceException e) {
            }
        }
        assertTrue(demographicContactInfo.phoneTypes.get(0).getText().equals(expectedType), "Expected Type is not displayed");
        assertTrue(demographicContactInfo.phoneComments.get(0).getText().equals(expectedComments), "Expected Comments are not displayed");
        assertTrue(demographicContactInfo.phoneStatuses2.get(0).getText().equals("ACTIVE"), "Expected Comments are not displayed");
    }

    @When("I set Start and End Date and click on Cancel Button on Edit Phone Number Page")
    public void i_set_Start_and_End_Date_and_click_on_Cancel_Button_on_Edit_Phone_Number_Page() {
        clearAndFillText(addContactInfo.startDate, getGreaterDate(2));
        clearAndFillText(addContactInfo.endDate, getGreaterDate(4));
        addContactInfo.cancelButton.click();
    }

    @Then("I see Phone number record on Contact Info Page has not been edited")
    public void i_see_Phone_number_record_on_Contact_Info_Page_has_not_been_edited() {
        String expectedNumber = phoneNumber.get();
        String expectedType = phoneType;
        String expectedComments = comments;
        assertFalse(demographicContactInfo.phoneNumbers.get(0).getText()
                .replace("-", "").equals(expectedNumber), "Expected Phone number is not displayed");
        assertFalse(demographicContactInfo.phoneTypes.get(0).getText().equals(expectedType), "Expected Type is not displayed");
        assertFalse(demographicContactInfo.phoneComments.get(0).getText().equals(expectedComments), "Expected Comments are not displayed");
        assertFalse(demographicContactInfo.phoneStatuses.get(0).getText().equals("INACTIVE"), "Expected Comments are not displayed");
    }

    @When("I provide phone type as {string} and start date as {string} to add phone number")
    public void providePhoneInformation(String phoneType, String date) {
        scrollUpRobot();
        clearAndFillText(addContactInfo.phoneNumber, getRandomNumber(10));
        waitForClickablility(addContactInfo.phoneType, 10);
        selectDropDown(addContactInfo.phoneType, phoneType);
        waitFor(2);
//        if (consumerName == null)
//            addContactInfo.getConsumerDropdownOption(2);
//        else
//            selectDropDown(addContactInfo.consumerDropDown, consumerName);
//        waitFor(2);
        waitForClickablility(addContactInfo.phoneComments, 10);
        clearAndFillText2(addContactInfo.phoneComments, "Test phone Comments passed");

        if (date.equalsIgnoreCase("current")) {
            getDate.set(getCurrentDateWithFormat());
            clearAndFillText(addContactInfo.startDate, getDate.get());
        } else if (date.equalsIgnoreCase("future")) {
            getDate.set(getGreaterDate(2));
            clearAndFillText(addContactInfo.startDate, getDate.get());
        } else {
            getDate.set(getPriorDate(2));
            clearAndFillText(addContactInfo.startDate, getDate.get());
        }
    }

    @When("I provide phone type as {string} and start date as {string} to add phone number for case consumer and click save")
    public void providePhoneInformationForCaseConsumer(String phoneType, String date) {
        int count = 0;
        if (isAlreadyPhoneExist)
            count = 1;
        else
            count = 2;
        for (int i = 0; i < count; i++) {
            i_click_on_the_Add_Phone_Number_button_on_Contact_Info_Page();
            clearAndFillText(addContactInfo.phoneNumber, getRandomNumber(10));
            waitForClickablility(addContactInfo.phoneType, 10);
            selectDropDown(addContactInfo.phoneType, phoneType);
            waitFor(2);
            if (consumerName.get() == null)
                selectRandomDropDownOption(addContactInfo.associatedCaseMember);
            else
                selectDropDown(addContactInfo.consumerDropDown, consumerName.get());
            waitFor(2);
            waitForClickablility(addContactInfo.phoneComments, 10);
            clearAndFillText2(addContactInfo.phoneComments, "Test phone Comments passed");

            if (date.equalsIgnoreCase("current")) {
                getDate.set(getCurrentDateWithFormat());
                clearAndFillText(addContactInfo.startDate, getDate.get());
            } else if (date.equalsIgnoreCase("future")) {
                getDate.set(getGreaterDate(2));
                clearAndFillText(addContactInfo.startDate, getDate.get());
            } else {
                getDate.set(getPriorDate(2));
                clearAndFillText(addContactInfo.startDate, getDate.get());
            }
            i_click_on_Save_button_on_add_phone_number_page();
        }
    }

    @When("I check already active contact exists for phone type {string}")
    public void isActivePhoneRecordExistForGivenType(String phoneType) {
        waitFor(2);
        if (addContactInfo.phoneNumberRecordRows.size() == 0) {
            isAlreadyPhoneExist = false;
            return;
        }
        for (WebElement row : addContactInfo.phoneNumberRecordRows) {
            if (row.findElement(By.xpath("./td[4]")).getText().equalsIgnoreCase(phoneType)
                    && row.findElement(By.xpath("./td[6]")).getText().equalsIgnoreCase("ACTIVE")) {
                isAlreadyPhoneExist = true;
                consumerName.set(row.findElement(By.xpath("./td[3]")).getText());
                break;
            }
        }
    }

    @Then("I verify error message for phone number as {string}")
    public void verifyErrorMessage(String expMessage) {
        waitForVisibility(demographicContactInfo.lblErrorMessage, 10);
        Assert.assertEquals(demographicContactInfo.lblErrorMessage.getText().toLowerCase(), expMessage.toLowerCase());
        waitFor(2);
    }

    @When("I inactivate an active phone number for phone type {string} and click on save")
    public void inactivateActivePhoneNumberByPhoneType(String phoneType) {
        waitFor(2);
        for (WebElement row : addContactInfo.phoneNumberRecordRows) {
            if (row.findElement(By.xpath("./td[4]")).getText().equalsIgnoreCase(phoneType)
                    && row.findElement(By.xpath("./td[6]")).getText().equalsIgnoreCase("ACTIVE")) {

                row.findElement(By.xpath("./td[6]")).click();
                break;
            }
        }
        waitForVisibility(addContactInfo.inactiveImmediatelyCheckbox, 10);
        addContactInfo.inactiveImmediatelyCheckbox.click();
        addContactInfo.saveButton.click();
    }

    @Then("I click on cancel button on add phone number page")
    public void i_click_on_cancel_button_on_add_phone_number_page() {
        addContactInfo.cancelButton.click();
        addContactInfo.popupAlertContinueButton.click();
    }

    @Then("I verify error message for phone number is not displayed")
    public void verifyErrorMessageNotDisplayed() {
        Assert.assertTrue(!isElementDisplayed(demographicContactInfo.lblErrorMessage));
    }

    @When("I select different consumer from consumer drop down to add phone number")
    public void chooseDifferentConsumer() {
        waitFor(2);
        addContactInfo.associatedCaseMember.click();
        List<WebElement> listOfConsumers = Driver.getDriver().findElements(By.xpath("//*[@id='menu-associatedCaseMember']/div[2]/ul/li"));
        Random rand = new Random();
        listOfConsumers.get(rand.nextInt((listOfConsumers.size() - 2) + 1) + 2).click();
    }

    @When("I provide valid data in all the fields on Add Phone Number page for Work Number")
    public void i_provide_valid_data_in_all_the_fields_on_Add_Phone_Number_page_for_Work_Number() {
        phoneNumber.set(getRandomNumber(10));
        waitFor(2);
        clearAndFillText(addContactInfo.phoneNumber, phoneNumber.get());
        waitForClickablility(addContactInfo.phoneType, 10);
        selectDropDown(addContactInfo.phoneType, "Work");
        addContactInfo.getConsumerDropdownOption(2);
        waitFor(2);
        clearAndFillText(addContactInfo.phoneComments, comments);
    }

    @When("I navigate to Contact Info Page within a case")
    public void i_navigate_to_Contact_Info_Page_within_a_case() {
        assertTrue(demographicContactInfo.demographicInfoTab.isDisplayed());
        waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
        demographicContactInfo.demographicInfoTab.click();
        //scrollUpUsingActions(3);
        waitForVisibility(demographicContactInfo.contactInfoTab, 2);
        assertTrue(demographicContactInfo.contactInfoTab.isDisplayed());
        waitFor(2);
        demographicContactInfo.contactInfoTab.click();
        demographicContactInfo.contactInfoTab.click();
    }

    @When("I click first CRM Case ID in search results on case and consumer search page")
    public void i_click_first_CRM_Case_ID_in_search_results_on_case_and_consumer_search_page() {
        hover(caseConsumerSearchPage.consumerLastName);
        click(caseConsumerSearchPage.consumerIDSearch.get(0));
    }

    @When("I navigate to Contact Info Tab")
    public void i_navigate_to_Contact_Info_Tab() {
        waitFor(3);
        click(demographicContactInfo.contactInfoTab);
    }

    @Then("I am able to view the following {string} for each Phone number listed")
    public void i_am_able_to_view_the_following_for_each_Phone_number_listed(String string, List<String> colHeaders) {
        Assert.assertTrue(demographicContactInfo.phoneNumColumnHeader.isDisplayed(), "Column not displayed");
        Assert.assertEquals(demographicContactInfo.phoneNumColumnHeader.getText(), colHeaders.get(0), "Column Name does not match");
        Assert.assertTrue(demographicContactInfo.typeColumnHeader.isDisplayed(), "Column not displayed");
        Assert.assertEquals(demographicContactInfo.typeColumnHeader.getText(), colHeaders.get(1), "Column Name does not match");
        Assert.assertTrue(demographicContactInfo.commentsColumnHeader.isDisplayed(), "Column not displayed");
        Assert.assertEquals(demographicContactInfo.commentsColumnHeader.getText(), colHeaders.get(2), "Column Name does not match");
        Assert.assertTrue(demographicContactInfo.statusColumnHeader.isDisplayed(), "Column not displayed");
        Assert.assertEquals(demographicContactInfo.statusColumnHeader.getText(), colHeaders.get(3), "Column Name does not match");
    }

    @When("I select the first phone number listed")
    public void i_select_the_first_phone_number_listed() {
        click(demographicContactInfo.phnNumbers2.get(0));
    }

    @Then("I am able to view Phone Number Start and End Date")
    public void iAmAbleToViewPhoneNumberStartAndEndDate() {
        Assert.assertTrue(addContactInfo.startDateLabel.isDisplayed());
        Assert.assertTrue(addContactInfo.endDate.isDisplayed());
    }

    @Then("I see only {int} comment per Phone Number")
    public void iSeeOnlyCommentPerPhoneNumber(int commentCount) {
        Assert.assertEquals(demographicContactInfo.phnNumbers2.size(), demographicContactInfo.phnNumberComments2.size(), "Multiple comments for listed Number");

    }

    @Then("I see {int} records visible per page of Phone Numbers")
    public void i_see_records_visible_per_page_of_Phone_Numbers(int recordCount) {
        Assert.assertEquals(demographicContactInfo.phnNumbers2.size(), recordCount);
        Assert.assertEquals(demographicContactInfo.phnNumberTypes2.size(), recordCount);
        Assert.assertEquals(demographicContactInfo.phnNumberComments2.size(), recordCount);
        Assert.assertEquals(demographicContactInfo.phnNumberStatus3.size(), recordCount);
    }

    @Then("Verify primary number checkbox {string} when following field true")
    public void VerifyPrimaryNumberCheckboxEnablewhenFollowingFieldTrue(String status, Map<String, String> data) {
        waitForPageToLoad(10);

        selectDropDown(addContactInfo.phoneType, data.get("phoneTypeHome"));
        assertTrue(addContactInfo.primaryCaseEnabledCheckBox.isEnabled());
        selectDropDown(addContactInfo.phoneType, data.get("phoneTypeCell"));
        assertTrue(addContactInfo.primaryCaseEnabledCheckBox.isEnabled());
        selectDropDown(addContactInfo.phoneType, data.get("phoneTypeWork"));
        assertTrue(addContactInfo.primaryCaseEnabledCheckBox.isEnabled());

        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        assertTrue(addContactInfo.endDate.getText().equals(""));
        assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
        clearAndFillText(addContactInfo.startDate, getPriorDate(1));
        assertTrue(addContactInfo.endDate.getText().equals(""));
        assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));

        if (status.equals("disable")) {
            clearAndFillText(addContactInfo.endDate, getFutureDate(1));
            assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
            selectDropDown(addContactInfo.phoneType, "Fax");
            assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
        }
    }

    @And("I check primary number checkbox")
    public void ICheckPrimaryNumberCheckbox() {
        click(addContactInfo.primaryCaseEnabledCheckBox);
        click(addContactInfo.saveButton);
    }

    @When("I add new phone number and make it primary to capture warning message")
    public void i_add_new_phone_number_and_make_it_primary_to_capture_warning_message() {
        waitFor(1);
        selectRandomDropDownOption(addContactInfo.addressConsumer);
        clearAndFillText(addContactInfo.phoneNumber, "0123456789");
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        selectDropDown(addContactInfo.phoneType, "Home");
        waitForClickablility(addContactInfo.primaryCasePhoneOrEmailCheckBox, 2);
        jsClick(addContactInfo.primaryCasePhoneOrEmailCheckBox);
        waitFor(2);
        jsClick(addContactInfo.saveButton);
        waitFor(2);

    }

    @Then("I verify warning message {string}")
    public void i_verify_warning_message(String warning) {
        if (warning.contains("Phone")) {
            waitFor(2);
            Assert.assertEquals(this.addContactInfo.warningMessage.getText(), warning, warning + " NOT display");
        } else if (warning.contains("Email")) {
            waitFor(2);
            Assert.assertEquals(this.addContactInfo.warningMessageEmail.getText(), warning, warning + " NOT display");
        }
    }

    @Then("I see existing {string} has no Primary Indicator")
    public void I_see_existing_number_hasnoPrimaryIndicator(String contact) {
        if (contact.equalsIgnoreCase("number")) {
            waitForVisibility(this.addContactInfo.warningMessage, 4);
            this.jsClick(this.addContactInfo.popupAlertContinueButton);
            waitFor(3);
            Assert.assertEquals(this.addContactInfo.primaryCasePhoneNumber.getText(), "(012) 345-6789");
        } else if (contact.equalsIgnoreCase("email")) {
            waitForVisibility(this.addContactInfo.warningMessageEmail, 4);
            this.jsClick(this.addContactInfo.popupAlertContinueButton);
            waitFor(3);
            Assert.assertEquals(this.addContactInfo.primaryCaseEmail.getText(), "testing@gmail.com");
        }
    }

    @And("change consumer dropdown value")
    public void change_consumer_dropdown_value() {
        click(addContactInfo.addressConsumer);
        click(addContactInfo.dropdownLastItem);
        consumerName.set(addContactInfo.addressConsumer.getText());
        waitFor(2);
        jsClick(addContactInfo.saveButton);
    }

    @Then("I see {string} owner is saved as the Consumer Id selected")
    public void i_see_phone_Number_s_owner_is_saved_as_the_Consumer_Id_selected(String contact) {
        waitFor(2);
        String consumerNameValue;
        if (contact.contains("phone")) {
            this.click(this.createConsumer.editPhoneNum);
            waitForVisibility(this.addContactInfo.addressConsumer, 4);
            consumerNameValue = this.addContactInfo.addressConsumer.getText();
            Assert.assertEquals(consumerNameValue, this.consumerName.get());
        } else if (contact.contains("email")) {
            this.click(this.addContactInfo.chkFirstEmailWithStar);
            waitForVisibility(this.addContactInfo.addressConsumer, 4);
            consumerNameValue = this.addContactInfo.addressConsumer.getText();
            Assert.assertEquals(consumerNameValue, this.consumerName.get());
        }
    }

    @When("I populate all field and check the primary email checkbox")
    public void I_populate_all_field_and_check_the_primary_email_checkbox() {
        waitForVisibility(addContactInfo.emailAddressField, 2);
        selectRandomDropDownOption(addContactInfo.addressConsumer);
        clearAndFillText(addContactInfo.startDate, getPriorDate(1));
        clearAndFillText(addContactInfo.emailAddressField, "testing@gmail.com");
        waitForVisibility(addContactInfo.primaryCasePhoneOrEmailCheckBox, 1);
        jsClick(addContactInfo.primaryCasePhoneOrEmailCheckBox);
        waitFor(2);
        jsClick(addContactInfo.saveButton);
        waitFor(2);
    }

    @Then("Verify primary email checkbox {string} when following field true")
    public void VerifyPrimaryEmailCheckboxEnablewhenFollowingFieldTrue(String status, Map<String, String> data) {
        waitForPageToLoad(10);
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        assertTrue(addContactInfo.endDate.getText().equals(""));
        assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
        clearAndFillText(addContactInfo.startDate, getPriorDate(1));
        assertTrue(addContactInfo.endDate.getText().equals(""));
        assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));

        if (status.equals("disable")) {
            clearAndFillText(addContactInfo.endDate, getFutureDate(1));
            assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
            selectDropDown(addContactInfo.phoneType, "Fax");
            assertTrue(isElementDisplayed(addContactInfo.primaryCaseEnabledCheckBox));
        }
    }

    @And("I edit expanded for Phone number and save changes")
    public void iExpendCurrentPhoneNumberAndEditItForPhoneChange() {
        phone = getRandomNumber(10);
        System.out.println("New phone number added: " + phone);
        clearAndFillText(addContactInfo.phoneNumber, "");
        selectDropDown(addContactInfo.phoneType, phoneType);
        addContactInfo.phoneNumber.sendKeys(phone);
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        addContactInfo.saveButton.click();
        waitForVisibility(demographicContactInfo.addPhoneButton, 10);
    }
}


