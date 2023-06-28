package com.maersk.crm.step_definitions;

import com.google.common.collect.Ordering;
import com.maersk.crm.api_step_definitions.APIConsumerRestController;
import com.maersk.crm.api_step_definitions.APIContactRecordEventsController;
import com.maersk.crm.pages.crm.CRMAddContactInfoPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.pages.crm.CRMDemographicMemberPage;
import com.maersk.crm.pages.tm.TMAddTeamPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.step_definitions.ShowOutboundCorrespondenceDetailsStepDefs;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.apache.commons.lang3.RandomStringUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.*;

public class CRM_ViewAddressInformationStepDef extends CRMUtilities implements ApiBase {
    CRMDemographicMemberPage demographicMemberPage = new CRMDemographicMemberPage();
    CRMDemographicContactInfoPage contactInfo = new CRMDemographicContactInfoPage();
    CRMAddContactInfoPage addContactInfo = new CRMAddContactInfoPage();

    final ThreadLocal<APIConsumerRestController> apiController = ThreadLocal.withInitial(APIConsumerRestController::new);
    final ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(BrowserUtils::getNewTestData2);
    public final ThreadLocal<String> expectedAddressOne = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedAddressTwo = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedCity = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedState = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedZip = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedCounty = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> expectedType = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> expectedFullAddress = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> expectedFullAddress1 = ThreadLocal.withInitial(String::new);

    final ThreadLocal<List<String>> addressActiveStartDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> addressInactiveEndDate = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> addressFutureStartDate = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<String> editedAddressOne = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedAddressTwo = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedCity = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedState = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedZip = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedCounty = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedType = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> editedFullAddress = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> effectiveStartDateUI = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> effectiveEndDateUI = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedEffectiveStartDate = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> editedEffectiveEndDate = ThreadLocal.withInitial(String::new);
    static final ThreadLocal<String> addLineOneRand = ThreadLocal.withInitial(BrowserUtils::generateRandomNumberChars);
    static final ThreadLocal<String> conAddLineOne = ThreadLocal.withInitial(() -> "1 Test Ct3." + addLineOneRand.get());
    static final ThreadLocal<String> addLineTwo = ThreadLocal.withInitial(() -> "Test Line 2");
    static final ThreadLocal<String> addCity = ThreadLocal.withInitial(() -> "Test City");
    static final ThreadLocal<String> addCountyLine = ThreadLocal.withInitial(() -> "Test County");
    static final ThreadLocal<String> selState = ThreadLocal.withInitial(() -> "Maryland");
    static final ThreadLocal<String> zipCode = ThreadLocal.withInitial(() -> "12211");

    @And("I click on Add new address button on Contact Info Tab Page")
    public void i_click_on_add_new_address_button_on_contact_info_tab_page() {
        waitFor(1);
        scrollDown();
        try {
            contactInfo.addAddressButton.click();
        } catch (Exception e) {
            scrollDown();
            jsClick(contactInfo.addAddressButton);
        }
    }

    @When("I add a new {string} {string} address to a consumer profile")
    public void i_add_a_new_address_to_a_consumer_profile(String status, String type) {
        expectedAddressOne.set(newConsumer.get().get("name").toString() + "123 St.");
        expectedAddressOne.set("Apt. #" + newConsumer.get().get("zip").toString().charAt(1));
        expectedCity.set(newConsumer.get().get("surname").toString() + " City");
        expectedState.set("Florida");
        expectedZip.set(newConsumer.get().get("zip").toString());
        System.out.println(expectedZip.get());
        expectedCounty.set(newConsumer.get().get("surname").toString());
        expectedType.set(type);
        expectedFullAddress.set(expectedAddressOne.get() + ", " + expectedAddressOne.get() + ", " + expectedCity.get() +
                ", " + expectedState.get() + ", " + expectedZip.get().replace("-", ""));
        System.out.println(expectedFullAddress.get());

        clearAndFillText(addContactInfo.addressLineOne, expectedAddressOne.get());
        clearAndFillText(addContactInfo.addressLineTwo, expectedAddressOne.get());
        clearAndFillText(addContactInfo.city, expectedCity.get());
        waitFor(3);
        selectDropDown(addContactInfo.state, expectedState.get());
        waitFor(1);
        clearAndFillText(addContactInfo.zip, expectedZip.get());
        clearAndFillText(addContactInfo.county, expectedCounty.get());
        if (status.equalsIgnoreCase("active"))//Made changes from active to inactive
        {
            clearAndFillText(addContactInfo.startDate, getPriorDate(3));
            clearAndFillText(addContactInfo.endDate, getGreaterDate(2));
        } else {
            clearAndFillText(addContactInfo.startDate, getPriorDate(23));
            clearAndFillText(addContactInfo.endDate, getPriorDate(20));
        }
        effectiveStartDateUI.set(addContactInfo.startDate.getAttribute("value"));
        effectiveEndDateUI.set(addContactInfo.endDate.getAttribute("value"));
        selectDropDown(addContactInfo.addressType, type);
        waitForClickablility(addContactInfo.saveButton, 10);
        addContactInfo.saveButton.click();
//        waitForVisibility(contactInfo.contactInfoTab, 5);
//        contactInfo.contactInfoTab.click();
        for (int i = 0; i < 3; i++) {
            try {
                assertTrue(contactInfo.addressLabel.isDisplayed(), "Not navigated to Contact Info Page after clicking on Save an Address bottom");
            } catch (StaleElementReferenceException e) {
            }
        }
    }

    @Then("I can view full address column has Address Line one, Address Line two, State, Zip in one line displayed")
    public void i_can_view_full_address_column_has_Address_Line_one_Address_Line_two_State_Zip_in_one_line_displayed() {
        System.out.println("Expected full address is = " + expectedFullAddress.get());
        String address = contactInfo.fullAddresses.get(0).getText().replace("-", "");
        System.out.println("actual full address is = " + address);
        assertTrue(expectedFullAddress.get().equalsIgnoreCase(address),
                "Full Address does not match expected value");
    }

    @Then("I see {string} column has expected value displayed")
    public void i_see_column_has_expected_value_displayed(String column) {

        switch (column) {
            case "County":
                assertTrue(expectedCounty.get().equalsIgnoreCase(contactInfo.addressCounties.get(1).getText()),
                        "County name does not match expected value " + expectedCounty.get() + " but found " + contactInfo.addressCounties.get(1).getText());
                break;
            case "Type":
                assertTrue(expectedType.get().equalsIgnoreCase(contactInfo.addressTypes.get(1).getText()),
                        "Address Type does not match expected value");
                break;
            case "Source":
                assertTrue(contactInfo.addressSources.get(1).getText().equals("Consumer Reported"),
                        "Address Source does not match expected value");
                break;
            case "Status":
                System.out.println(contactInfo.addressesStatuses.get(1).getText());
                assertTrue(contactInfo.addressesStatuses.get(1).getText().equalsIgnoreCase("Active"),
                        "Address Status does not match expected value");
                break;
        }
    }

    @When("I hover over the status of the address")
    public void i_hover_over_the_status_of_the_address() {
        hover(contactInfo.addressesStatuses.get(0));
        waitFor(1);
        assertTrue(contactInfo.statusStartDate.isDisplayed());
    }

    @Then("I see Address Start Date and End Date in MM\\/DD\\/YYYY format")
    public void i_see_Address_Start_Date_and_End_Date_in_MM_DD_YYYY_format() {
        System.out.println(contactInfo.statusStartDate.getText());
        System.out.println(contactInfo.statusEndDate.getText());
        assertTrue(isMMddYYYYformat(contactInfo.statusStartDate.getText()));
        assertTrue(isMMddYYYYformat(contactInfo.statusEndDate.getText()));
    }

    @When("I can navigate to view additional Address records")
    public void i_can_navigate_to_view_additional_Address_records() {
        contactInfo.pagination.get(contactInfo.pagination.size() - 1).click();
    }

    @Then("I see no more {int} Address records displayed on the page")
    public void i_see_no_more_Address_records_displayed_on_the_page(Integer records) {
        assertTrue(contactInfo.addressesStatuses.size() != 0);
        assertTrue(contactInfo.addressesStatuses.size() <= records);
    }

    @Then("I see all Active addresses are displayed on the top and followed by Inactive")
    public void i_see_all_Active_addresses_are_displayed_on_the_top_and_followed_by_Inactive() {
        System.out.println(contactInfo.addressesStatuses.get(1).getText());
        //assertTrue(contactInfo.addressesStatuses.get(0).getText().equalsIgnoreCase("Active"));
        assertTrue(contactInfo.addressesStatuses.get(1).getText().equalsIgnoreCase("Inactive"), "actual status = " + contactInfo.addressesStatuses.get(1).getText());
    }

    @When("I change the status of top Address record to Inactive")
    public void i_change_the_status_of_top_Address_record_to_Inactive() {
        try {
            contactInfo.addressesStatuses.get(0).click();
        } catch (StaleElementReferenceException e) {
            jsClick(contactInfo.addressesStatuses.get(0));
        }
        waitFor(1);
        expectedAddressOne.set(newConsumer.get().get("name").toString() + " Ave.44");
        expectedAddressOne.set("Unit #" + radomNumber(10));
        expectedCity.set(newConsumer.get().get("surname").toString() + " City");
        expectedState.set("Virginia");
        expectedCounty.set(newConsumer.get().get("surname").toString());
        clearAndFillText(addContactInfo.addressLineOne, expectedAddressOne.get() + " 1");
        clearAndFillText(addContactInfo.addressLineTwo, expectedAddressOne.get());
        clearAndFillText(addContactInfo.city, expectedCity.get());
        clearAndFillText(addContactInfo.county, expectedCounty.get());
        waitFor(1);
        selectDropDown(addContactInfo.state, expectedState.get());
        waitFor(1);
        clearAndFillText(addContactInfo.startDate, getPriorDate(15));
        clearAndFillText(addContactInfo.endDate, getPriorDate(12));
        waitFor(1);
        addContactInfo.saveButton.click();
        waitForVisibility(addContactInfo.inactivateReasonType, 5);
        selectDropDown(addContactInfo.inactivateReasonType, "Invalid Address");
        waitFor(1);
        addContactInfo.saveButton.click();
        waitFor(5);
        if (isElementDisplayed(addContactInfo.popupAlertContinueButton)) {
            jsClick(addContactInfo.popupAlertContinueButton);
        }
        waitFor(2);
        assertTrue(contactInfo.addressLabel.isDisplayed(),
                "Not navigated to Contact Info Page after clicking on Save an Address bottom");
    }

    @Then("I see Active record with most in the past Start Date displayed on the top")
    public void i_see_Active_record_with_most_in_the_past_Start_Date_displayed_on_the_top() {
        for (int i = 0; i < 3; i++) {
            try {
                hover(contactInfo.addressesStatuses.get(0));
                break;
            } catch (StaleElementReferenceException e) {
            }
        }
        assertTrue(contactInfo.statusStartDate.getText().replace("/", "").equalsIgnoreCase(getPriorDate(3)));
    }


    @When("I change the status of second Address record to Inactive")
    public void i_change_the_status_of_second_Address_record_to_Inactive() {
        contactInfo.addressesStatuses.get(1).click();
        waitFor(1);
        expectedAddressOne.set(newConsumer.get().get("name").toString() + " Ct.");
        expectedCity.set(newConsumer.get().get("surname").toString() + " City");
        expectedCounty.set(newConsumer.get().get("surname").toString());
        clearAndFillText(addContactInfo.addressLineOne, expectedAddressOne.get());
        clearAndFillText(addContactInfo.addressLineTwo, expectedAddressOne.get());
        clearAndFillText(addContactInfo.city, expectedCity.get());
        clearAndFillText(addContactInfo.county, expectedCounty.get());
        waitFor(1);
        selectDropDown(addContactInfo.state, "Washington");
        waitFor(1);
        clearAndFillText(addContactInfo.startDate, getPriorDate(17));
        clearAndFillText(addContactInfo.endDate, getPriorDate(1));
        waitFor(1);
        addContactInfo.saveButton.click();
        waitForVisibility(addContactInfo.inactivateReasonType, 5);
        selectDropDown(addContactInfo.inactivateReasonType, "Added New Address");
        waitFor(1);
        addContactInfo.saveButton.click();
        assertTrue(contactInfo.addressLabel.isDisplayed(),
                "Not navigated to Contact Info Page after clicking on Save an Address bottom");
    }

    @Then("I see Inactive record with most recent End Date displayed on the top")
    public void i_see_Inactive_record_with_most_recent_End_Date_displayed_on_the_top() {
        hover(contactInfo.addressesStatuses.get(0));
        System.out.println(contactInfo.statusEndDate.getText().replace("/", "") + getCurrentDate().replace("/", "") + contactInfo.addressesStatuses.get(0).getText());
        assertTrue(contactInfo.statusEndDate.getText().replace("/", "").equalsIgnoreCase(getCurrentDate().replace("/", "")));//getPriorDate(1) made changes
    }

    /* Verify the newly added address status in Address table
      Author - Aswath
      Date - 02/27/2019
      Parameters - Status (String)
  */
    @Then("I view the address {string} on address page")
    public void i_view_address_on_address_page(String status) {
        WebElement conInfoAddressStatus = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + expectedFullAddress1.get() + "')]/following-sibling::td[4]"));
        assertEquals(conInfoAddressStatus.getText(), status);
    }

    /* click On save button on edit address page
            Author - Aswath
            Date - 02/27/2019
            Parameters - No
        */
    public void clickOnSaveConInfoAddress() {
        waitForVisibility(addContactInfo.saveButton, 10);
        addContactInfo.saveButton.click();
    }

    /* Adds new address with different status (2 Active, 2 Inactive and 2 future status records) in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
      */
    @And("I add new addresses with all required fields and different status")
    public void i_add_new_addresses_with_all_required_fields_and_different_status() {

        //Future records
        i_click_on_add_new_address_button_on_contact_info_tab_page();
        i_edit_all_required_fields_along_with_as("future", "");
        clearAndFillText(addContactInfo.addressLineOne, "update" + getRandomNumber(5));
        addContactInfo.saveButton.click();
        waitFor(3);
        if (isElementDisplayed(addContactInfo.popupAlertContinueButton)) {
            jsClick(addContactInfo.popupAlertContinueButton);
        }

        //Active records
        i_click_on_add_new_address_button_on_contact_info_tab_page();
        i_edit_all_required_fields_along_with_as("past", "future");
        clearAndFillText(addContactInfo.addressLineOne, "update" + getRandomNumber(5));
        addContactInfo.saveButton.click();
        waitForPageToLoad(3);
        if (isElementDisplayed(addContactInfo.popupAlertContinueButton)) {
            jsClick(addContactInfo.popupAlertContinueButton);
        }
        //Inactive records
        i_click_on_add_new_address_button_on_contact_info_tab_page();
        i_edit_all_required_fields_along_with_as("past", "past2");
        clearAndFillText(addContactInfo.addressLineOne, "update" + getRandomNumber(5));
        addContactInfo.saveButton.click();
        waitForPageToLoad(3);
        if (isElementDisplayed(addContactInfo.popupAlertContinueButton)) {
            jsClick(addContactInfo.popupAlertContinueButton);
        }

    }

    /* Verify the newly added address in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
    */
    @Then("I verify Newly added full address should be in the address list")
    public void i_verify_newly_added_full_address_should_be_added_to_the_address_list() {
        boolean found = false;
        int i = 2;
        while (!found) {

            for (WebElement address : addContactInfo.listOfAddress) {
                waitFor(2);
                List<WebElement> listAddress = address.findElements(By.xpath("td[2]"));
                for (WebElement e : listAddress) {
                    if (expectedFullAddress1.get().equalsIgnoreCase(e.getText())) {
                        found = true;
                    }
                    break;
                }
                if (found) break;
            }
            if (!found) {
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;
            } else break;
        }
        assertTrue(found, "Full Address added is not found");
    }
    /* Verify the newly added address status date are sorted accordingly Active, Future and Inactive
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
    */

    public void getAddressDates() {

        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));
        int i = 2;
        try {
            while (next.isDisplayed()) {
                for (WebElement address : addContactInfo.listOfAddress) {
                    List<WebElement> listAddressOfStatus = address.findElements(By.xpath("td[6]"));
                    for (WebElement e : listAddressOfStatus) {
                        waitFor(2);
                        hover(e);
                        waitForVisibility(e, 10);
                        String startDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[1]/h6")).getText();
                        String endDate = Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[2]/h6")).getText();
                        if (e.getText().equals("ACTIVE")) {
                            addressActiveStartDate.get().add(startDate);
                        }
                        if (e.getText().equals("FUTURE")) {
                            waitFor(1);
                            addressFutureStartDate.get().add(startDate);
                        }
                        if (e.getText().equals("INACTIVE")) {
                            waitFor(1);
                            addressInactiveEndDate.get().add(endDate);
                        }

                    }
                }
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;


            }
        } catch (Exception e) {
        }
    }

    /* Verify the Active status records are sorted accordingly ascending order in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
    */
    @Then("Active address are sorted by start date in ascending order")
    public void active_address_are_sorted_by_start_date_in_ascending_order() {
        waitForPageToLoad(10);
        getAddressDates();
        assertTrue(ascendingOrderDatesString(addressActiveStartDate.get()));


    }

    /* Verify the Future status records are sorted accordingly ascending order in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
    */
    @Then("Future address are sorted by start date in ascending order")
    public void future_address_are_sorted_by_start_date_in_ascending_order() {
        //getAddressDates();
        assertTrue(ascendingOrderDatesString(addressFutureStartDate.get()));
    }

    /* Verify the Inactive status records are sorted accordingly ascending order in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
        Bug
    */
    @And("Inactive address are sorted by end date in descending order")
    public void inactive_address_are_sorted_by_end_date_in_descending_order() {
        assertTrue(descendingOrderDatesString(addressInactiveEndDate.get()));
    }

    /* Verify the order of status sorted in Active, Future, and Inactive in Address table
        Author - Aswath
        Date - 02/27/2019
        Parameters - No
    */
    @Then("Active address are displayed on top followed by future and inactive")
    public void active_address_are_displayed_on_top_followed_by_future_and_inactive() {
        int i = 1;
        String previous = "ACTIVE";
        String previous1 = "FUTURE";
        String previous2 = "INACTIVE";
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));

        try {
            while (next.isDisplayed()) {

                for (WebElement address : addContactInfo.listOfAddress) {
                    List<WebElement> listAddressOfStatus = address.findElements(By.xpath("td[6]"));

                    for (WebElement e : listAddressOfStatus) {
                        String status = e.getText();
                        waitFor(3);
                        assertTrue(previous.equals("ACTIVE") && status.equals("ACTIVE") ||
                                previous.equals("ACTIVE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("FUTURE") ||
                                previous1.equals("FUTURE") && status.equals("INACTIVE") ||
                                previous2.equals("INACTIVE") && status.equals("INACTIVE"));

                        assertFalse(previous1.equals("INACTIVE") && previous2.equals("FUTURE") && status.equals("ACTIVE"));
                        previous = status;
                    }
                }
                Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a[text()='" + i + "']")).click();
                i++;
            }
        } catch (Exception e) {
        }

    }

    @When("I edit all required fields along with {string} as {string}")
    public void i_edit_all_required_fields_along_with_as(String desireStartDate, String desireEndDate) {
        waitForVisibility(addContactInfo.addressLineOne, 5);
        addContactInfo.addressLineOne.sendKeys(conAddLineOne.get());
        waitForVisibility(addContactInfo.addressLineTwo, 5);
        addContactInfo.addressLineTwo.sendKeys(addLineTwo.get());
        waitForVisibility(addContactInfo.city, 5);
        addContactInfo.city.sendKeys(addCity.get());
        waitForVisibility(addContactInfo.county, 5);
        addContactInfo.county.sendKeys(addCountyLine.get());
        waitForVisibility(addContactInfo.state, 5);
        selectDropDown(addContactInfo.state, selState.get());
        waitForVisibility(addContactInfo.zip, 5);
        addContactInfo.zip.sendKeys(zipCode.get());
        expectedFullAddress1.set(conAddLineOne.get() + ", " + addLineTwo.get() + ", " + addCity.get() + ", " + selState.get() + ", " + zipCode.get());
        selectDropDown(addContactInfo.addressType, "Physical");
        waitFor(2);
        clearFiled(addContactInfo.startDate);
        waitForVisibility(addContactInfo.startDate, 5);
        addContactInfo.startDate.sendKeys(getDesireDate(desireStartDate));
        if (!desireEndDate.equals("")) {
            waitForVisibility(addContactInfo.endDate, 5);
            addContactInfo.endDate.sendKeys(getDesireDate(desireEndDate));
        }

    }

    @Then("I navigate thought additional Address records to see no more {int} Address records displayed on each page")
    public void i_navigate_thought_additional_Address_records_to_see_no_more_Address_records_displayed_on_each_page(Integer records) {
        for (int i = contactInfo.pagination.size() - 1; i > 0; i--) {
            contactInfo.pagination.get(i).click();
            assertTrue(contactInfo.addressesStatuses.size() != 0, "there is no record displayed on this pagination");
            assertTrue(contactInfo.addressesStatuses.size() <= records, "More than " + records + " records are displayed on this pagination");
        }
    }

    @Then("I click on newly created address to navigate edit address page")
    public void i_click_on_newly_created_address_to_navigate_edit_address_page() {
        try {
            jsClick(contactInfo.addressesStatusesNotOnCase.get(0));
        } catch (Exception e) {
            contactInfo.addressesStatuses.get(0).click();
        }
    }

    @When("I edit all required fields for address information as {string} and {string}")
    public void i_edit_all_required_fields_for_address_information(String status, String type) {
        editedAddressOne.set(newConsumer.get().get("surname").toString().substring(0, 6) + " way");
        editedAddressTwo.set("apt. #" + newConsumer.get().get("zip").toString().charAt(2));
        editedCity.set(newConsumer.get().get("name").toString().substring(0, 3) + " city");
        editedState.set("Alabama");
        editedZip.set(newConsumer.get().get("zip").toString() + "-" + newConsumer.get().get("zip").toString().substring(0, 4));
        System.out.println(editedZip.get());
        editedCounty.set(newConsumer.get().get("surname").toString().substring(0, 2) + " " + "County");
        editedType.set(type);
        editedFullAddress.set(editedAddressOne.get() + ", " + editedAddressTwo.get() + ", " + editedCity.get() +
                ", " + editedState.get() + ", " + editedZip.get().replace("-", ""));
        System.out.println(editedFullAddress.get());

        clearAndFillText(addContactInfo.addressLineOne, editedAddressOne.get());
        clearAndFillText(addContactInfo.addressLineTwo, editedAddressTwo.get());
        clearAndFillText(addContactInfo.city, editedCity.get());
        waitFor(3);
        selectDropDown(addContactInfo.state, editedState.get());
        waitFor(1);
        clearAndFillText(addContactInfo.zip, editedZip.get());
        clearAndFillText(addContactInfo.county, editedCounty.get());
        if (status.equalsIgnoreCase("active"))//Made changes from active to inactive
        {
            clearAndFillText(addContactInfo.startDate, getPriorDate(3));
            clearAndFillText(addContactInfo.endDate, getGreaterDate(20));
        } else {
            clearAndFillText(addContactInfo.startDate, getPriorDate(23));
            clearAndFillText(addContactInfo.endDate, getPriorDate(36));
        }
        editedEffectiveStartDate.set(addContactInfo.startDate.getAttribute("value"));
        editedEffectiveEndDate.set(addContactInfo.endDate.getAttribute("value"));
        selectDropDown(addContactInfo.addressType, type);

    }


    @Then("I choose reason for inactivation and save edited address")
    public void i_choose_reason_for_inactivation_and_save_edited_address() {
        selectOptionFromMultiSelectDropDown(addContactInfo.inactivateReasonType, "Invalid Address");
        waitForClickablility(addContactInfo.saveButton, 10);
        jsClick(addContactInfo.saveButton);
        //   addContactInfo.saveButton.click();
        waitFor(2);
        waitForVisibility(contactInfo.contactInfoTab, 5);
        waitFor(4);

    }

    @Then("I verify the address status as {string} on address page")
    public void i_verify_the_address_status_as_something_on_address_page(String status) {
        waitFor(2);
        WebElement conInfoAddressStatus = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + expectedFullAddress1.get() + "')]/following-sibling::td[7]"));
        assertEquals(conInfoAddressStatus.getText(), status);
    }


    @Then("I verify old addresses not exist in address table")
    public void I_verify_old_addresses_not_exist_inaddress_table() {
        int count = 0;
        List<WebElement> pages = null;
        waitFor(5);
        pages = Driver.getDriver().findElements(addContactInfo.paginationAddress);

        for (int i = 0; i < pages.size() - 1; i++) {
            List<String> statues = null;
            if (i > 0)
                Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][1]//ul/li/a[text()='" + (i + 1) + "']")).click();

            waitFor(5);
            statues = getElementsText(addContactInfo.addressStatuses);
            count = count + statues.size();
        }
        assertTrue(count <= 4, "Found extra address in table" + count);
    }

    @Then("I verify the address status as on address page")
    public void i_verify_the_address_status_sort_order_on_address_page() {
        List<WebElement> pages = null;
        pages = Driver.getDriver().findElements(addContactInfo.paginationAddress);
        List<String> expectedStatus = new ArrayList<String>();
        waitFor(5);

        for (int i = 0; i < pages.size() - 1; i++) {
            List<String> statues = null;
            if (i > 0)
                Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiTypography-root')]//div[contains(@class,'col-') and contains(@class,'mt-')][1]//ul/li/a[text()='" + (i + 1) + "']")).click();

            waitFor(5);
            statues = getElementsText(addContactInfo.addressStatuses);

            for (String status : statues) {
                if (!expectedStatus.contains(status)) {
                    expectedStatus.add(status.toString());
                }
            }
        }
        assertTrue(Ordering.natural().isOrdered(expectedStatus), "Address Statuses are not sorted -->" + expectedStatus);
    }


    @Then("I view the Last Updated on address section")
    public void i_view_the_last_updated_on_address_section() {
        WebElement conInfoAddressLastUpdated = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + expectedFullAddress1.get() + "')]/following-sibling::td[6]"));
        String lastUpdatedValue = conInfoAddressLastUpdated.getText();
        System.out.println("Last updated value is " + lastUpdatedValue);
        assertTrue((lastUpdatedValue.contains("/") && lastUpdatedValue.contains(":")));
    }

    @Then("I view the consumer on address section")
    public void i_view_the_consumer_on_address_section() {
        WebElement conInfoAddressConsumer = Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + expectedFullAddress1.get() + "')]/following-sibling::td[2]"));
        String lastUpdatedValue = conInfoAddressConsumer.getText();
        assertTrue((lastUpdatedValue.contains(CRMOptInOutDemographicMember.firstNameCaseLoader.get())));
    }

    @And("I verify new address created with County as optional")
    public void iVerifyNewAddressCreatedWithCountyAsOptional() {
        expectedAddressOne.set(newConsumer.get().get("name").toString() + new Random().nextInt(9));
        expectedAddressOne.set("Apt. #" + newConsumer.get().get("zip").toString().charAt(1));
        expectedCity.set(newConsumer.get().get("surname").toString() + " City");
        expectedState.set("Florida");
        expectedZip.set(newConsumer.get().get("zip").toString());
        System.out.println(expectedZip.get());
        expectedFullAddress.set(expectedAddressOne.get() + ", " + expectedAddressOne.get() + ", " + expectedCity.get() +
                ", " + expectedState.get() + ", " + expectedZip.get().replace("-", ""));
        System.out.println(expectedFullAddress.get());

        clearAndFillText(addContactInfo.addressLineOne, expectedAddressOne.get());
        clearAndFillText(addContactInfo.addressLineTwo, expectedAddressOne.get());
        clearAndFillText(addContactInfo.city, expectedCity.get());
        waitFor(3);
        selectDropDown(addContactInfo.state, expectedState.get());
        waitFor(1);
        clearAndFillText(addContactInfo.zip, expectedZip.get());

        clearAndFillText(addContactInfo.startDate, getPriorDate(3));
        clearAndFillText(addContactInfo.endDate, getGreaterDate(2));

        effectiveStartDateUI.set(addContactInfo.startDate.getAttribute("value"));
        effectiveEndDateUI.set(addContactInfo.endDate.getAttribute("value"));
        selectDropDown(addContactInfo.addressType, "Physical");
        waitForClickablility(addContactInfo.saveButton, 10);
        addContactInfo.saveButton.click();
        waitFor(2);
        assertTrue(contactInfo.addressLabel.isDisplayed(), "Not navigated to Contact Info Page after clicking on Save an Address bottom");
    }


    @And("I add new {string} addresses for future")
    public void i_add_new_addresses_for_future(String addresType) {
        i_click_on_add_new_address_button_on_contact_info_tab_page();
        i_edit_all_required_fields("future2", "", addresType);
        clickOnSaveConInfoAddress();
        waitFor(5);
        assertEquals(demographicMemberPage.warningMessageCerentActiveAddress.getText(), "The current active " + addresType + " Address will be systematically end dated the day before the Start Date of this address");
        click(demographicMemberPage.continueMemberBtn);
        waitForPageToLoad(10);

    }

    @Then("verify active address is set to the Start Date of the future address")
    public void address_is_set_to_the_Start_Date_of_the_future_address() {
        scrollDownUsingActions(2);
        waitFor(2);
        try {
            jsClick(addContactInfo.statusOfAddrss.get(0));
        } catch (Exception e) {
            addContactInfo.statusOfAddrss.get(0).click();
        }
        waitForVisibility(addContactInfo.endDate, 3);
        waitFor(2);
        System.out.println("Actual and date - " + addContactInfo.endDate.getAttribute("value").replaceAll("/", ""));
        System.out.println("Expected end date -" + getGreaterDate(3));
        assertTrue(addContactInfo.endDate.getAttribute("value").replaceAll("/", "").equals(getGreaterDate(3)), "date is not equals");

    }

    @When("I edit the future {string} address to have a Start Date within before the Start Date of the active address")
    public void i_edit_future_address(String Physical) {
        scrollDownUsingActions(2);
        jsClick(new TMAddTeamPage().futureStatus);
        waitForVisibility(demographicMemberPage.piStartDate, 2);
        clearFieldCharByChar(demographicMemberPage.piStartDate);
        demographicMemberPage.piStartDate.sendKeys(getDesireDate("future"));
        waitFor(1);
        clickOnSaveConInfoAddress();

    }

    @Then("the System will throw an Error Message {string}")
    public void verify_error_message(String errorMessage) {
        assertTrue(contactInfo.errorMessageFutureAddress.isDisplayed(), "Error message is NOT Displayed");

    }

    public void i_edit_all_required_fields(String desireStartDate, String desireEndDate, String addresType) {
        String addLineOneRand = generateRandomNumberChars();
        //String lineOnerandom  = specficCharctersgenerator(8); Dont remove
        String conAddLineOne = "1 Test Ct." + addLineOneRand;
        waitForVisibility(addContactInfo.addressLineOne, 5);
        addContactInfo.addressLineOne.sendKeys(conAddLineOne);
        String addLineTwo = "Test line 2";
        waitForVisibility(addContactInfo.addressLineTwo, 5);
        addContactInfo.addressLineTwo.sendKeys(addLineTwo);
        String addCity = "Test City";
        waitForVisibility(addContactInfo.city, 5);
        addContactInfo.city.sendKeys(addCity);
        String addCountyLine = "Test county";
        waitForVisibility(addContactInfo.county, 5);
        addContactInfo.county.sendKeys(addCountyLine);
        String selState = "Maryland";
        waitForVisibility(addContactInfo.state, 5);
        selectDropDown(addContactInfo.state, selState);
        String zipCode = "12211";
        waitForVisibility(addContactInfo.zip, 5);
        addContactInfo.zip.sendKeys(zipCode);
        expectedFullAddress1.set(conAddLineOne + ", " + addLineTwo + ", " + addCity + ", " + selState + ", " + zipCode);

        selectDropDown(addContactInfo.addressType, addresType);
        waitFor(2);
        clearFiled(addContactInfo.startDate);
        waitForVisibility(addContactInfo.startDate, 5);
        addContactInfo.startDate.sendKeys(getDesireDate(desireStartDate));
        if (!desireEndDate.equals("")) {
            waitForVisibility(addContactInfo.endDate, 5);
            addContactInfo.endDate.sendKeys(getDesireDate(desireEndDate));
        }
    }

    @And("I change address type in active address to Physical")
    public void AndIchange_address_typePhysical() {
        waitFor(1);
        addContactInfo.addressStatuses.get(0).click();
        i_edit_all_required_fields("present", "", "Physical");
        addContactInfo.addressConsumer.click();
        addContactInfo.emailFirstConsumer.click();
        waitFor(2);
        clickOnSaveConInfoAddress();
    }

    @And("I add new {string} addresses for future in to case")
    public void i_add_new_addresses_for_futurein_to_case(String addresType) {
        waitFor(1);
        contactInfo.addAddressButton.click();
        i_edit_all_required_fields("future2", "", addresType);
        addContactInfo.addressConsumer.click();
        addContactInfo.emailFirstConsumer.click();
        waitFor(2);
        System.out.println(addContactInfo.addressConsumer.getText());
        clickOnSaveConInfoAddress();
        waitForVisibility(demographicMemberPage.warningMessageCerentActiveAddress, 10);
        assertEquals(demographicMemberPage.warningMessageCerentActiveAddress.getText(), "The current active " + addresType + " Address will be systematically end dated the day before the Start Date of this address");
        click(demographicMemberPage.continueMemberBtn);
        waitForPageToLoad(10);

    }

    @When("I update the address type to {string}")
    public void i_update_the_address_type_from_Mailing_to_Physical(String addressType2) {
        waitForVisibility(addContactInfo.addressStatuses.get(0), 3);
        addContactInfo.addressStatuses.get(0).click();
        waitForVisibility(addContactInfo.addressLine1Input, 3);
        selectDropDown(addContactInfo.addressType, addressType2);
        waitFor(2);
        jsClick(addContactInfo.saveButton);
        if (isElementDisplayed(demographicMemberPage.continueMemberBtn)) {
            jsClick(demographicMemberPage.continueMemberBtn);
        }
    }

    @Then("I verify existing {string} Address is end-dated and the new {string} Address is saved")
    public void i_verify_existing_Address_is_end_dated_and_the_new_Address_is_saved(String addressType1, String addressType2) {
        waitFor(3);
        assertEquals(addContactInfo.addressStatuses.get(1).getText(), "INACTIVE");
        assertEquals(addContactInfo.addressStatuses.get(0).getText(), "ACTIVE");
        if (addressType2.equalsIgnoreCase("Physical")) {
            assertTrue(addContactInfo.physicalAddressFromAddressTable.size() == 2);
        } else if (addressType2.equalsIgnoreCase("mailing")) {
            assertTrue(addContactInfo.mailingAddressFromAddressTable.size() == 2);
        }
        for (WebElement addressTypes : addContactInfo.physicalAddressFromAddressTable) {
            assertEquals(addressTypes.getText(), addressType1);
        }
    }

    @Then("I verify consumer to have start date as current date")
    public void i_verify_consumer_to_have_start_date_as_current_date() {
        waitFor(2);
        System.out.println(addContactInfo.startDate.getAttribute("value"));
        assertTrue(addContactInfo.startDate.getAttribute("value").replace("/", "").equalsIgnoreCase(getCurrentDate().replace("/", "")));
    }


    @When("I select  consumer from dropdown in edit email page")
    public void i_select_consumer_from_dropdown_in_edit_email_page() {
        addContactInfo.addressConsumer.click();
//        addContactInfoPage.emailFirstConsumer.click();
//        waitFor(2);
//        consumerID = addContactInfoPage.addressConsumer.getText();
//        System.out.println(consumerID);
//        addContactInfoPage.saveButton.click();
    }

    @And("I add new {string} addresses for future to check warning message")
    public void i_add_new_addresses_for_future_warning(String addresType) {
        i_click_on_add_new_address_button_on_contact_info_tab_page();
        i_edit_all_required_fields("future2", "", addresType);
        clickOnSaveConInfoAddress();

    }

    @When("I update active address end date to have date {string} start day of future")
    public void i_update_active_address_end_date_to_have_date_before_start_day_of_future_address(String date) {
        jsClick(contactInfo.addressesStatusesNotOnCase.get(0));
        switch (date) {
            case "before":
                clearAndFillText(addContactInfo.endDate, getFutureDate(1));
                System.out.println("Updated with before = ====== " + getFutureDate(1));
                break;
            case "after":
                clearAndFillText(addContactInfo.endDate, getFutureDate(1));
                System.out.println("Updated with after= ====== " + getFutureDate(1));
                break;
        }
        selectDropDown(new CRMAddContactInfoPage().inactivateReasonType, "Added New Address");
        new CRMCreateConsumerProfilePage().SaveButton.click();
    }

    @When("I will update {string} and click on save button")
    public void i_update_addressAddress_Page(String field) {
        APIConsumerRestController.setAddressLine1(generateRandomNumberChars() + "22");
        clearAndFillText(addContactInfo.addressLineOne, APIConsumerRestController.addressLine1.get());
        APIConsumerRestController.setAddressLine2(generateRandomNumberChars() + "22");
        clearAndFillText(addContactInfo.addressLineTwo, APIConsumerRestController.addressLine2.get());
        APIConsumerRestController.setAddressCity(RandomStringUtils.random(5, true, false));
        clearAndFillText(addContactInfo.city, APIConsumerRestController.addressCity.get());
        APIConsumerRestController.setAddressCounty(RandomStringUtils.random(5, true, false));
        clearAndFillText(addContactInfo.county, APIConsumerRestController.addressCounty.get());
        APIConsumerRestController.setAddressState("Ohio");
        selectDropDown(addContactInfo.state, APIConsumerRestController.addressState.get());
        APIConsumerRestController.setApiaddressZip(RandomStringUtils.random(5, false, true));
        for (int i = 0; i < 10; i++) {
            addContactInfo.zip.sendKeys(Keys.BACK_SPACE);
        }
        clearAndFillText(addContactInfo.zip, APIConsumerRestController.apiaddressZip.get());
        APIConsumerRestController.setAddressType("Physical");
        selectDropDown(addContactInfo.addressType, APIConsumerRestController.addressType.get());
        APIConsumerRestController.setEffectiveStartDate(getPriorDate(4));
        clearAndFillText(addContactInfo.startDate, APIConsumerRestController.effectiveStartDate.get());
        String title = Driver.getDriver().getTitle();
        jsClick(addContactInfo.saveButton);
        String nextTitle = Driver.getDriver().getTitle();
        if (title.equals(nextTitle)) {
            jsClick(addContactInfo.saveButton);
        }

        waitFor(10);
        APIConsumerRestController.setCorrelationId(EventsUtilities.getLogs("correlationid", "addressType").get(1));
        System.out.println("Correlation id ======================= " + APIConsumerRestController.correlationId.get());
    }

    @Then("I update all fields for existing phone and click save")
    public void I_update_all_fields_for_existing_phone_and_click_save() {

        APIConsumerRestController.setApiphoneNumber(RandomStringUtils.random(10, false, true));
        clearAndFillText(addContactInfo.phoneNumber, APIConsumerRestController.apiphoneNumber.get());
        APIConsumerRestController.setPhoneType("Fax");
        selectDropDown(addContactInfo.phoneType, APIConsumerRestController.phoneType.get());
        APIConsumerRestController.setEffectiveStartDate(getPriorDate(4));
        clearAndFillText(addContactInfo.startDate, APIConsumerRestController.effectiveStartDate.get());
        String title = Driver.getDriver().getTitle();
        addContactInfo.saveButton.click();
        String nextTitle = Driver.getDriver().getTitle();
        if (title.equals(nextTitle)) {
            jsClick(addContactInfo.saveButton);
        }
        waitFor(10);
        APIConsumerRestController.setCorrelationId(EventsUtilities.getLogs("correlationid", "phoneType").get(1));
        System.out.println("Correlation id ======================= " + APIConsumerRestController.correlationId.get());
    }

    @Then("I update all fields for existing email and click save")
    public void I_update_all_fields_for_existing_email_and_click_save() {

        APIConsumerRestController.setEmailAddress(RandomStringUtils.random(5, true, false) + "@gmail.com");
        clearAndFillText(addContactInfo.emailAddressField, APIConsumerRestController.emailAddress.get());
        APIConsumerRestController.setEffectiveStartDate(getPriorDate(4));
        clearAndFillText(addContactInfo.startDate, APIConsumerRestController.effectiveStartDate.get());
        String title = Driver.getDriver().getTitle();
        addContactInfo.saveButton.click();
        String nextTitle = Driver.getDriver().getTitle();
        if (title.equals(nextTitle)) {
            jsClick(addContactInfo.saveButton);
        }
        waitFor(10);
        APIConsumerRestController.setCorrelationId(EventsUtilities.getLogs("correlationid", "emailType").get(1));
        System.out.println("Correlation id ======================= " + APIConsumerRestController.correlationId.get());
    }


    @And("I adding new {string} address to Contact Info")
    public void iAddingNewAddressAndVerifyingTheFieldsAddresType(String addressType) {
        try {
            selectRandomDropDownOption(addContactInfo.addressConsumer);
        } catch (NoSuchElementException e) {
        }
        clearAndFillText(addContactInfo.addressLineOne, "System rd 11");
        clearAndFillText(addContactInfo.city, "Doland");
        clearAndFillText(addContactInfo.county, "TestCounty");
        selectDropDown(addContactInfo.state, "Florida");
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, addressType);
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        waitFor(1);
        addContactInfo.saveButton.click();
        try {
            jsClick(demographicMemberPage.continueMemberBtn);
        } catch (Exception e) {
        }
    }

}
