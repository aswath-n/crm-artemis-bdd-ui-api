package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APICaseLoaderEligibilityEnrollmentController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;


public class CRM_EditAddressInfoStepDef extends CRMUtilities implements ApiBase {

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMAddContactInfoPage addContactInfo = new CRMAddContactInfoPage();
    CRMCaseContactDetailsPage caseContactDetails = new CRMCaseContactDetailsPage();
    CRMDemographicContactInfoPage contactInfo = new CRMDemographicContactInfoPage();
    CRMDemographicContactInfoPage demographicContactInfo = new CRMDemographicContactInfoPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMCreateConsumerProfilePage createConsumerProfilePage = new CRMCreateConsumerProfilePage();
    public String conAddLineOne;
    final ThreadLocal<String> fullAddress1 = ThreadLocal.withInitial(String::new);
    final String expectedFullAddress = "1 Test Ct., Test City, Maryland";
    static final ThreadLocal<String> expectedPreviousFullAddress = ThreadLocal.withInitial(String::new);
    String expectedFullAddress1 = new String();
    public final ThreadLocal<String> ExpectedStartDate = new ThreadLocal<>();
    public final ThreadLocal<String> ExpectedEndDate = new ThreadLocal<>();

    @And("I navigate to Contact Info Page")
    public void i_navigate_to_Contact_Info_Page() {
        waitFor(3);
        assertTrue(demographicContactInfo.demographicInfoTab.isDisplayed());
        waitForVisibility(demographicContactInfo.demographicInfoTab, 3);
        demographicContactInfo.demographicInfoTab.click();
        scrollUpUsingActions(3);



        //they all need to be deleted before pr
//        assertTrue(contactRecord.contactRecordSign.isDisplayed());
        //     waitFor(3);
        //assertTrue(demographicContactInfo.demographicInfoTab.isDisplayed());
        //waitForVisibility(demographicContactInfo.demographicInfoTab, 10);
        //    demographicContactInfo.demographicInfoTab.click();
        //   scrollUpUsingActions(6);
//        waitForVisibility(demographicContactInfo.contactInfoTab, 2);
//        assertTrue(demographicContactInfo.contactInfoTab.isDisplayed());
//        waitFor(2);
//        scrollUpUsingActions(3);
        //   demographicContactInfo.contactInfoTab.click();
    }

    @When("I expend the Address record to verify all fields are present")
    public void i_expend_the_Address_record_to_verify_all_fields_are_present() {
        for(int i=0; i<3; i++){
            try{
            demographicContactInfo.addressesStatuses.get(0).click();
            break;
            }catch (Exception e){}
        }
        waitFor(1);
        assertTrue(addContactInfo.addressLineOne.isDisplayed());
        assertTrue(addContactInfo.addressLineTwo.isDisplayed());
        assertTrue(addContactInfo.city.isDisplayed());
        assertTrue(addContactInfo.county.isDisplayed());
        assertTrue(addContactInfo.state.isDisplayed());
        assertTrue(addContactInfo.zip.isDisplayed());
        assertTrue(addContactInfo.addressType.isDisplayed());
        assertTrue(addContactInfo.startDate.isDisplayed());
        assertTrue(addContactInfo.endDate.isDisplayed());
        assertTrue(addContactInfo.addressStatus.isDisplayed());

    }

    @When("I verify {string} fields being marked mandatory on edit Address page")
    public void i_verify_fields_being_marked_mandatory_on_edit_Address_page(String string, List<String> options) {
        String actual = "";
        for (String option : options) {
            actual = "";
            switch (option) {
                case "AddressLineOne":
                    actual = addContactInfo.addressLineOneLabel.getText();
                    break;
                case "City":
                    actual = addContactInfo.cityLabel.getText();
                    break;
                case "County":
                    actual = addContactInfo.countyLabel.getText();
                    break;
                case "State":
                    actual = addContactInfo.stateLabel.getText();
                    break;
                case "Zip":
                    actual = addContactInfo.zipLabel.getText();
                    break;
                case "AddressType":
                    actual = addContactInfo.addressTypeLabel.getText();
                    break;
                case "StartDate":
                    actual = addContactInfo.startDateLabel.getText();
                    break;

            }
            assertTrue(actual.endsWith("*"), option + " field is not marked mandatory");
        }
    }

    @When("I edit all address fields with data")
    public void I_edit_all_address_fields_with_data(Map<String, String> data) {
        waitForVisibility(addContactInfo.addressLine1Input, 5);
        for (String key : data.keySet()) {
            switch (key) {
                case "AddressLineOne":
                    clearAndFillText(addContactInfo.addressLine1Input, data.get("AddressLineOne"));
                    System.out.println("Update AddressLineOne with value: " + data.get("AddressLineOne"));
                    waitFor(1);
                    break;
                case "AddressLineTwo":
                    clearAndFillText(addContactInfo.addressLine2Input, data.get("AddressLineTwo"));
                    System.out.println("Update AddressLineTwo with value: " + data.get("AddressLineTwo"));
                    waitFor(1);
                    break;
                case "City":
                    clearAndFillText(addContactInfo.addressCityInput, data.get("City"));
                    System.out.println("Update City with value: " + data.get("City"));
                    waitFor(1);
                    break;
                case "County":
                    clearAndFillText(addContactInfo.addressCountyInput, data.get("County"));
                    System.out.println("Update County with value: " + data.get("County"));
                    waitFor(1);
                    break;
                case "State":
                    addContactInfo.addressStateDropdownButton.click();
                    waitForVisibility(addContactInfo.addressStateDropdownList, 2);
                    addContactInfo.getAddressStateSelectionBy(data.get("State")).click();
                    System.out.println("Update State with value: " + data.get("State"));
                    waitFor(1);
                    break;
                case "Zip":
                    clearAndFillText(addContactInfo.addressZipInput, data.get("Zip"));
                    System.out.println("Update Zip with value: " + data.get("Zip"));
                    waitFor(1);
                    break;
                case "AddressType":
                    clearAndFillText(addContactInfo.addressTypeInput, data.get("AddressType"));
                    System.out.println("Update AddressType with value: " + data.get("AddressType"));
                    waitFor(1);
                    break;
                case "StartDate":
                    clearAndFillText(addContactInfo.effectiveStartDateInput, data.get("StartDate"));
                    System.out.println("Update StartDate with value: " + data.get("StartDate"));
                    waitFor(1);
                    break;
            }
        }
    }

    @When("I expend the Address Record on Contact Info Page")
    public void i_expend_the_Address_Record_on_Contact_Info_Page() {
        for(int i=0; i<2; i++) {
            try{
                expectedPreviousFullAddress.set(demographicContactInfo.fullAddresses.get(0).getText());
            }catch(Exception e){}
      }
        scrollDown();
        scrollDown();
        System.out.println("here");
        if(isElementDisplayed(demographicContactInfo.contactInfoTab)){
        jsClick(demographicContactInfo.contactInfoTab);
        }
        for(int i=0; i<=2;i++){
            try{
                demographicContactInfo.fullAddresses.get(0).click();
                break;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        waitFor(1);
    }


    @When("I see {string} field on edit Address page accept {int} characters in total")
    public void i_see_field_on_edit_Address_page_accept_characters_in_total(String field, Integer stringLength) {
        String actual = "";
        String sentText = createTextString(stringLength * 4);
        switch (field) {
            case "addressLine1":
                clearAndFillText(addContactInfo.addressLineOne, sentText);
                waitFor(1);
                actual = addContactInfo.addressLineOne.getAttribute("value");
                break;
            case "addressLine2":
                clearAndFillText(addContactInfo.addressLineTwo, sentText);
                waitFor(1);
                actual = addContactInfo.addressLineTwo.getAttribute("value");
                break;
        }
        assertTrue(actual.length() <= stringLength);


    }

    @When("I should be able to see all states in State dropdown on edit Address page are available")
    public void i_should_be_able_to_see_all_states_in_State_dropdown_on_edit_Address_page_are_available() {
        assertTrue(stateDropdownHasAll(addContactInfo.state), "State dropdown has incorrect value/s");
    }


    @When("I should see following dropdown options for {string} field on edit Address page displayed")
    public void i_should_see_following_dropdown_options_for_field_on_edit_Address_page_displayed(String field, List<String> options) {
        for (String option : options) {
            selectDropDown(addContactInfo.addressType, option);
        }
    }

    @When("I see Address Start Date and End Date on edit Address page in MM\\/DD\\/YYYY format")
    public void i_see_Address_Start_Date_and_End_Date_on_edit_Address_page_in_MM_DD_YYYY_format() {
        waitFor(2);
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        String actual = addContactInfo.startDate.getAttribute("value");
        assertTrue(isMMddYYYYformat(actual), "Incorrect Date format");
    }

    @Then("I see Address status on edit Address page is {string} or {string}")
    public void i_see_Address_status_on_edit_Address_page_is_or(String status1, String status2) {
        String actualStatus = addContactInfo.addressStatus.getText();
        System.out.println(actualStatus + "   " + status1 + " + " + status2);
        assertTrue(actualStatus.equals(status1) || actualStatus.equals(status2));
    }

    @When("I see {string} field on edit Address page accepts up to {int} digits")
    public void i_see_field_on_edit_Address_page_accepts_up_to_digits(String field, Integer length) {
        waitFor(3);
        clearAndFillText(addContactInfo.zip, "qaws234dfert567hfug999jg044k-jj55");
        assertTrue(hasOnlyDigits(addContactInfo.zip.getAttribute("value")),
                field + " has to accept digits only.");
        assertTrue(addContactInfo.zip.getAttribute("value").replace("-", "").length() <= 9,
                field + " has to accept up to " + length + " digits only.");
    }

    @When("I enter invalid {int} digit value into Zip Code field")
    public void i_enter_invalid_digit_value_into_Zip_Code_field(Integer invalidZipLength) {
        addContactInfo.zip.sendKeys(Keys.DELETE);
        addContactInfo.zip.sendKeys(Keys.DELETE);
        addContactInfo.zip.sendKeys(Keys.DELETE);
        addContactInfo.zip.sendKeys(Keys.DELETE);
        addContactInfo.zip.sendKeys(Keys.DELETE);
        clearAndFillText(addContactInfo.zip, getRandomNumber(invalidZipLength));
        waitFor(1);
    }

    @When("I enter invalid Date value into Start Date and End Date fields")
    public void i_enter_invalid_Date_value_into_Start_Date_and_End_Date_fields() {
        clearAndFillText(addContactInfo.startDate, getAllDigitString(7));
        waitFor(1);
        clearAndFillText(addContactInfo.endDate, getAllDigitString(7));
        waitFor(1);
    }

    @When("I click on Save button on Edit Address Page")
    public void i_click_on_Save_button_on_Edit_Address_Page() {
        waitFor(5);
        addContactInfo.saveButton.click();
        if(isElementDisplayed(addContactInfo.addressCityInput) && false==isElementDisplayed(addContactInfo.popupAlertContinueButton)){
            addContactInfo.saveButton.click();
        }
        waitFor(3);
        if(isElementDisplayed(addContactInfo.popupAlertContinueButton)) {
            jsClick(addContactInfo.popupAlertContinueButton);
        }


//        //todo delete tryCatch block after bug is fixed
//        try {
//            if (addContactInfo.saveButton.isDisplayed()) {
//                addContactInfo.saveButton.click();
//                waitFor(1);
//            }
//        } catch (Exception e) {
//            System.out.println("Second Click on save button on edit address page is not needed. " + e.getMessage());
//        }
    }


    @Then("New Address should be added to the address list")
    public void new_address_should_be_added_to_the_addrees_list() {
        boolean found = false;
        int i = 2;
        boolean nextButton = false;
        List<WebElement> pages = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[1]/li/a"));
        WebElement next = Driver.getDriver().findElement(By.xpath("(//ul[@class='pagination'])[1]/li/a/span[text()='arrow_forward']"));
        while (!found) {

            for (WebElement fa : addContactInfo.listOfAddress) {
                List<WebElement> fAddress = fa.findElements(By.xpath("td[1]"));
                for (WebElement add : fAddress) {
                    //System.out.println(name.getText());
                    if (fullAddress1.get().equalsIgnoreCase(add.getText()))

                        System.out.println(add.getText());
                    {
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


    @Then("I see error messages {string} with {string}")
    public void i_see_error_messages_with(String expectedZipError, String expectedDateError) {
        assertTrue(addContactInfo.zipError.isDisplayed(), "Zip Error message is not displayed");
        assertTrue(addContactInfo.zipError.getText().equalsIgnoreCase(expectedZipError), "Incorrect message for Zip field");
        assertTrue(addContactInfo.startDateFormatError.isDisplayed(), "Start Date Error message is not displayed");
        assertTrue(addContactInfo.startDateFormatError.getText().equalsIgnoreCase(expectedDateError), "Incorrect message for Start Date field");
        assertTrue(addContactInfo.endDateFormatError.isDisplayed(), "End Date Error message is not displayed");
        assertTrue(addContactInfo.endDateFormatError.getText().equalsIgnoreCase(expectedDateError), "Incorrect message for End Date");
    }

    @When("I clear all previously populated Edit Address Page mandatory fields")
    public void i_clear_all_previously_populated_Edit_Address_Page_mandatory_fields() {
        addContactInfo.clearEditedAddressMandatoryFields();

    }

    @Then("I see {string} with all mandatory fields error messages displayed")
    public void i_see_with_all_mandatory_fields_error_messages_displayed(String string) {
//        assertTrue(addContactInfo.mainFillFieldMessage.isDisplayed());
        assertTrue(addContactInfo.enterCityError.isDisplayed());
        assertTrue(addContactInfo.enterAddressOneError.isDisplayed());
        assertTrue(addContactInfo.enterStartDateError.isDisplayed());
//        assertTrue(addContactInfo.zipMandatoryError.isDisplayed());
    }

    @Then("I see address {string} on view address page")
    public void i_see_address_on_view_address_page(String status) {
        System.out.println(status);
        waitFor(1);
        //scrollUpUsingActions(2);
        if(status.equalsIgnoreCase("Source")){
            assertTrue(demographicContactInfo.sourceTypeConsumerReported.getText().equalsIgnoreCase("Consumer Reported"));
        }
        if (status.equals("FUTURE") || status.equals("INACTIVE")) {
            System.out.println(demographicContactInfo.addressesStatuses.get(1).getText());
            assertTrue(demographicContactInfo.addressesStatuses.get(1).getText().equalsIgnoreCase(status));
        } else if (!status.equalsIgnoreCase("Source")) {
            System.out.println(demographicContactInfo.addressesStatuses.get(0).getText());
            assertTrue(demographicContactInfo.addressesStatuses.get(0).getText().equalsIgnoreCase(status));
        }

    }

    @Then("I see address value Address Source on view address page")
    public void i_see_address_value_Address_Source_on_view_address_page() {
        waitFor(1);
        assertTrue(demographicContactInfo.source.isDisplayed());
    }

//    @When("I edit all required fields along with {string} as {string}")
//    public void i_edit_all_required_fields_along_with_as(String desireStartDate, String desireEndDate) {
//        addContactInfo.addressLineOne.sendKeys("1 Test Ct.");
//        addContactInfo.city.sendKeys("Test City");
//        addContactInfo.county.sendKeys("Test County");
//        selectDropDown(addContactInfo.state, "Maryland");
//        waitFor(2);
//        clearFiled(addContactInfo.startDate);
//        addContactInfo.startDate.sendKeys(getDesireDate(desireStartDate));
//        if (!desireEndDate.equals("")) {
//            addContactInfo.endDate.sendKeys(getDesireDate(desireEndDate));
//        }
//    }


    @Then("I see edited address {string} on view address page")
    public void i_see_edited_address_on_view_address_page(String status) {
        String actual=null;
        for(int i=0; i<=2;i++){
            try{
                actual = demographicContactInfo.addressesStatuses.get(0).getText();
                break;
            }
            catch(Exception e){}
        }
    assertEquals(actual,status);
    }

    @Then("I see edited address {string} on view address page manual consumer search")
    public void i_see_edited_address_on_view_address_page_manual_consumer_search(String status) {
        scrollDownUsingActions(6);
        System.out.println("Printing Status actual " + demographicContactInfo.addressesStatuses.get(0).getText() + "  expected " + status);
        assertEquals(demographicContactInfo.addressesStatuses.get(0).getText(), status);
    }

    @When("I edit all mandatory fields with new data on Edit Address Page")
    public void i_edit_all_mandatory_fields_with_new_data_on_Edit_Address_Page() {
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct.");
        clearAndFillText(addContactInfo.city, "Test City");
        clearAndFillText(addContactInfo.county, "Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getCurrentDate());

    }


    @Then("I verify the error message on invalid detials")
    public void I_verfiy_the_error_message_on__invalid_detials() {
        addContactInfo.inactiveImmediatelyCheckbox.click();
        i_click_on_Save_button_on_Edit_Address_Page();
        waitFor(10);
        assertTrue(addContactInfo.inactivateReasonType.isDisplayed(), "error message not displayed");

    }

    @When("I edit with new data on Edit Address Page")
    public void i_edit_with_new_data_on_Edit_Address_Page() {

        String addOne = "1 Test Ct.";
        clearAndFillText(addContactInfo.addressLineOne, addOne);
        String addtwo = "Test City";
        addContactInfo.city.sendKeys(addtwo);

        clearAndFillText(addContactInfo.county, ("Test County"));
        String stateDD = "Maryland";
        selectDropDown(addContactInfo.state, stateDD);
        waitFor(2);
        String zipCode = "12345";
        clearFiled(addContactInfo.zip);
        addContactInfo.zip.sendKeys(zipCode);
        selectDropDown(addContactInfo.addressType, "Physical");
        clearAndFillText(addContactInfo.startDate, getCurrentDate());
        fullAddress1.set(addOne + ", " + addtwo + ", " + stateDD + ", " + zipCode);
        String inactiveCB = addContactInfo.inactiveImmediatelyCheckbox.getText();
        assertTrue("INACTIVATE IMMEDIATELY".equals(inactiveCB));
        addContactInfo.inactiveImmediatelyCheckbox.click();
    }

    @When("I am navigated to the read-only view of saved information on Address Information page")
    public void i_am_navigated_to_the_read_only_view_of_saved_information_on_Address_Information_page() {
        waitFor(3);
        assertTrue(demographicContactInfo.addAddressButton.isDisplayed());
        String actual = demographicContactInfo.fullAddresses.get(0).getText();
        System.out.println(actual);
        System.out.println(expectedFullAddress);
        assertTrue(actual.contains(expectedFullAddress));
    }

    @When("I click on Cancel button on Edit Address Page")
    public void i_click_on_Cancel_button_on_Edit_Address_Page() {
        assertTrue(addContactInfo.cancelButton.isDisplayed());
        addContactInfo.cancelButton.click();
        demographicContactInfo.continueBtnOnWrnMsg.click();
    }

    @When("I am navigated to the read-only view of previously captured address on Address Information page")
    public void i_am_navigated_to_the_read_only_view_of_previously_captured_address_on_Address_Information_page() {
        String actual=null;
        for(int i=0; i<2; i++) {
            try{
               actual = demographicContactInfo.fullAddresses.get(0).getText();
            }catch(Exception e){}
        }
        System.out.println("Actual :" + actual + " Expected :" + expectedPreviousFullAddress.get());
        assertTrue(actual.equals(expectedPreviousFullAddress.get()));
    }

    @When("I verify current Address Record has {string} status")
    public void i_verify_current_Address_Record_has_status(String status) {
        waitFor(2);
        scrollDownUsingActions(6);
        assertTrue(demographicContactInfo.addressesStatuses.get(0).getText().equalsIgnoreCase(status));
    }

    @When("I click on inactivate immediately button on Edit Address page")
    public void i_click_on_inactivate_immediately_button_on_Edit_Address_page() {
        addContactInfo.inactiveImmediatelyCheckbox.click();
        waitForVisibility(addContactInfo.inactivateReasonType, 10);
        selectDropDown(addContactInfo.inactivateReasonType, "Invalid Address");
        waitFor(5);

    }

    @Then("I verify Address Source is {string}")
    public void i_verify_Address_Source_is(String expectedSource) {
        waitFor(3);
        String actualSource = demographicContactInfo.phnNumberStatus3.get(0).getText();
        assertTrue(actualSource.equalsIgnoreCase(expectedSource), "actual source = "+actualSource);
    }

    @Then("I verify Phone Source value is {string}")
    public void i_verify_Phone_Source_value_is(String expectedSource) {
        waitFor(3);
        String actualSource = demographicContactInfo.phoneSources.get(0).getText();
        System.out.println(actualSource);
        assertTrue(actualSource.equalsIgnoreCase(expectedSource));
    }

    @Then("I verify Address Source is {string} for consumers not on a case")
    public void i_verify_Address_Source_is_for_consumers_not_on_a_case(String expectedSource) {
        waitFor(3);
        String actualSource = demographicContactInfo.addressTypes.get(0).getText();
        assertTrue(actualSource.equalsIgnoreCase(expectedSource));
    }

    @Then("I verify on newly created Address Source is {string}")
    public void i_verify_on_newly_created_Address_Source_is(String expectedSource) {
        waitFor(3);
        String actualSource = demographicContactInfo.addressSources.get(0).getText();

        if(actualSource.equals("-- --")){
            actualSource = demographicContactInfo.addressSources.get(1).getText();
            assertTrue(actualSource.equalsIgnoreCase(expectedSource));
        }else {
            assertTrue(actualSource.equalsIgnoreCase(expectedSource));
        }
    }

    @When("I choose inactivation reason from dropdown on Edit Address page")
    public void i_choose_on_inactivation_reson_from_dropdown_on_Edit_Address_page() {
//        i_click_on_Save_button_on_Edit_Address_Page();
        waitForVisibility(addContactInfo.inactivateReasonType, 10);
        selectDropDown(addContactInfo.inactivateReasonType, "Invalid Address");
    }

    /* Verifies the error message
    Author - Aswath
    Date - 03/07/2019
    Parameters - NA
    */
    @Then("I verify mandatory error is displayed under reason for inactivation dropdown")
    public void i_verify_mandatory_error_is_displayed_under_reason_for_inactivation_dropdown() {

        String expErrorMsg = "REASON FOR INACTIVATION is required and cannot be left blank";
        waitForVisibility(addContactInfo.reasonTypeError, 10);
        String actErrMsg = addContactInfo.reasonTypeError.getText();
        assertEquals(actErrMsg, expErrorMsg);

    }

/* Updates the address with past start date
    Author - Aswath
    Date - 03/07/2019
    Parameters -NA
    */

    @When("I update address and start date in Edit Address Page")
    public void i_update_address_and_end_date_in_Address_Page() {
        addContactInfo.addressLineOne.sendKeys("1 Test Ct.");
        addContactInfo.city.sendKeys("Test City");
        addContactInfo.county.sendKeys("Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        addContactInfo.zip.sendKeys("12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getPriorDate(4));

    }
     /* Selects the Immediately inactivation check box
    Author - Aswath
    Date - 03/07/2019
    Parameters -NA
    */

    @When("I click on inactivate immediately check box on Edit Address page")
    public void i_click_on_inactivate_immediately_check_box_on_Edit_Address_page() {
        String inactiveCB = addContactInfo.addressINACTIVATEIMMEDIATELY.getText();
        System.out.println(inactiveCB);
        String expectedInactiveCB = "INACTIVATE IMMEDIATELY";
        assertTrue(expectedInactiveCB.equals(inactiveCB));
        addContactInfo.inactiveImmediatelyCheckbox.click();

    }


    /* End date is updated to current date
    Author - Aswath
    Date - 03/07/2019
    Parameters - NA
    */
    @When("I enter end date on Edit Address page")
    public void I_enter_end_date_on_Edit_Address_page() {
        addContactInfo.endDate.sendKeys(getPriorDate(2));
        addContactInfo.clickOutside.click();
        waitFor(2);
    }

    /* Verifies the Reason in the Inactivation dropdoen list
    Author - Aswath
    Date - 03/07/2019
    Parameters - Accepts list of reason to inactivate the address
    */
    @When("I verify values in {string} for inactivation dropdown")
    public void i_verify_values_in_reason_for_inactivation_dropdown(String arg1, List<String> reason) {

        for (String option : reason) {
            waitForVisibility(addContactInfo.inactivateReasonType, 10);
            selectDropDown(addContactInfo.inactivateReasonType, option);
            waitFor(2);
        }
    }

    @When("I select inactivation reason")
    public void i_select_inactivation_reason() {
        selectDropDown(addContactInfo.inactivateReasonType, "Invalid Address");
    }

    @And("I edit all mandatory fields with new data on Edit Address Page within a case")
    public void i_edit_all_mandatory_fields_with_new_data_on_Edit_Address_Page_within_a_case() {
        selectRandomDropDownOption(addContactInfo.associatedCaseMember);
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct."+getRandomNumber(5));
        clearAndFillText(addContactInfo.city, "Test City");
        clearAndFillText(addContactInfo.county, "Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getCurrentDate());
    }

    @When("I edit all mandatory fields with new data on Edit Address Page within a case for INEB")
    public void i_edit_all_mandatory_fields_with_new_data_on_Edit_Address_Page_within_a_case_for_INEB() {
        selectRandomDropDownOption(addContactInfo.associatedCaseMember);
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct."+getRandomNumber(5));
        waitFor(2);
        clearAndFillText(addContactInfo.county, "Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        selectDropDown(addContactInfo.cityINEB, "Alamo");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getCurrentDate());
    }

    @When("I edit all mandatory fields with past date on Edit Address Page within a case")
    public void i_edit_all_mandatory_fields_with_past_date_on_Edit_Address_Page_within_a_case() {
        selectRandomDropDownOption(addContactInfo.associatedCaseMember);
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct."+getRandomNumber(4));
        clearAndFillText(addContactInfo.city, "Test City");
        clearAndFillText(addContactInfo.county, "Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getPriorDate(5));
        addContactInfo.endDate.sendKeys(getPriorDate(2));
    }

    @When("I edit all mandatory fields with future date on Edit Address Page within a case")
    public void i_edit_all_mandatory_fields_with_future_date_on_Edit_Address_Page_within_a_case() {
        selectRandomDropDownOption(addContactInfo.associatedCaseMember);
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct."+getRandomNumber(5));
        clearAndFillText(addContactInfo.city, "Test City");
        clearAndFillText(addContactInfo.county, "Test County");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getFutureDate(2));
        addContactInfo.endDate.sendKeys(getFutureDate(5));
    }


    @And("I navigated to newly created address edit page")
    public void iNavigatedToNewlyCreatedAddressEditPage() {

        List<WebElement> addresses = contactInfo.addresses;
        for (WebElement address : addresses) {
            if (address.getText().contains("1TestCt")) {
                address.click();
            }
            waitFor(2);
        }
    }

    @And("I edit all fields with past date on Edit Address Page within a case")
    public void iEditAllFieldsWithPastDateOnEditAddressPageWithinACase() {
        selectRandomDropDownOption(addContactInfo.associatedCaseMember);
        clearAndFillText(addContactInfo.addressLineOne, "1TestCt");
        clearAndFillText(addContactInfo.addressLineTwo, "2TestCt");
        clearAndFillText(addContactInfo.city, "Test City");
        clearAndFillText(addContactInfo.county, "TestCounty");
        selectDropDown(addContactInfo.state, "Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        int startDatePlus = 2;
        int endDatePlus = 5;
        ExpectedStartDate.set(getFutureDate(startDatePlus));
        ExpectedEndDate.set(getFutureDate(endDatePlus));
        addContactInfo.startDate.sendKeys(ExpectedStartDate.get());
        addContactInfo.endDate.sendKeys(ExpectedEndDate.get());

    }

    @Then("I verify fields displayed under Address edit page")
    public void iVerifyFieldsDisplayedUnderAddressEditPage() {
        String addressLineOneText = addContactInfo.addressLineOne.getAttribute("value").toLowerCase().trim();
        String addressLineTwoText = addContactInfo.addressLineTwo.getAttribute("value").toLowerCase().trim();
        String County = addContactInfo.county.getAttribute("value").toLowerCase().trim();
        String State = addContactInfo.stateDropdown.getAttribute("class").toLowerCase().trim();
        String Zipcode = addContactInfo.zip.getAttribute("value").toLowerCase().trim();
        String Type = addContactInfo.addressTypeDropdown.getAttribute("class").toLowerCase().trim();
        String TypeValue = addContactInfo.addressType.getText().toLowerCase().trim();
        String Status = addContactInfo.statusOfAddress.getText().toLowerCase().trim();
        Assert.assertEquals(addressLineOneText != null && addressLineOneText.chars().allMatch(Character::isLetterOrDigit), true);
        Assert.assertEquals(addressLineTwoText != null && addressLineTwoText.chars().allMatch(Character::isLetterOrDigit), true);
        Assert.assertEquals(County != null && County.chars().allMatch(Character::isLetter), true);
        Assert.assertEquals(Zipcode != null && Zipcode.chars().allMatch(Character::isDigit), true);
        assertTrue(State.contains("select"));
        assertTrue(Type.contains("select"));
        assertTrue(TypeValue.contains("physical") || TypeValue.contains("mailing"));
        assertTrue(Status.contains("active") || Status.contains("inactive") || Status.contains("future"));
    }

    @Then("I mouse over on the Status of the newly added Address")
    public void iMouseOverOnTheStatusOfTheNewlyAddedAddress() {
        //new Actions(Driver.getDriver()).moveToElement(addContactInfo.statusOfAddress).build().perform();
        Actions builder = new Actions(Driver.getDriver());
       // WebElement status  addContactInfo.statusOfAddrss;s
        WebElement status = Driver.getDriver().findElement(By.xpath("(//*[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric  mdl-color-text--orange-800 mx-fontweight-status MuiTableCell-sizeSmall'])[1]"));

        builder.moveToElement(status).perform();
    }

    @And("I verify data displayed on tooltip")
    public void iVerifyDataDisplayedOnTooltip() {
        WebElement tooltip = Driver.getDriver().findElement(By.xpath("(//*[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric  mdl-color-text--orange-800 mx-fontweight-status MuiTableCell-sizeSmall'])[1]"));
        //String TooltipText = tooltip.getAttribute("aria-describedby");
        tooltip.isDisplayed();
        String StartDate = contactInfo.statusStartDate.getText().replaceAll("/","").trim();
        String EndDate = contactInfo.statusEndDate.getText().replaceAll("/","").trim();
        assertEquals(ExpectedStartDate,StartDate, "Expected = "+ExpectedStartDate+" but found "+StartDate);
        assertEquals(ExpectedEndDate,EndDate);
        System.out.println("Start date displayed on tooltip as" + StartDate);
        System.out.println("End date displayed on tooltip as" + EndDate);
     }


    @Then("I verify pagination and number of address displayed")
    public void iVerifyPaginationAndNumberOfAddressDisplayed() {
        if(addContactInfo.addressPagination.size()>1){
            List<WebElement> pagination = addContactInfo.addressPagination;
            int paginationCount= addContactInfo.addressPagination.size();
           for(int i=0;i<paginationCount-1;i++){
                pagination.get(i).click();
                int numberOfAddressDisplayed = contactInfo.addresses.size();
                Assert.assertEquals(numberOfAddressDisplayed,2);
            }
        }
    }

    @Then("I verify the address is disabled to be edited")
    public void i_verify_the_address_is_disabled_to_be_edited() {
        Assert.assertTrue(!isElementDisplayed(addContactInfo.addressConsumer));
    }

    @Then("I see error messages {string} displayed")
    public void i_see_with_all_mandatory_fields_error_messages_displayed2(String string) {
        waitForVisibility(addContactInfo.errorMessageAlreadyExists,1);
        assertTrue(addContactInfo.errorMessageAlreadyExists.isDisplayed());
    }

    @When("I verify error message {string}")
    public void i_verify_error_message(String message) {
        waitForVisibility(addContactInfo.sameTypeErrorMsg,4);
        assertTrue(addContactInfo.sameTypeErrorMsg.isDisplayed());
    }

    @And("I will search case by state caseID")
    public void searchByStateID(){
        String stateCaseID = new APICaseLoaderEligibilityEnrollmentController(). getCaseIdentificationNo();
        clearAndFillText(manualContactRecordSearchPage.StateCaseIdSeach, stateCaseID );
        manualContactRecordSearchPage.searchButton.click();
    }

    @And("I edit all mandatory fields with new data on Edit Address Page within a case no county")
    public void i_edit_all_mandatory_fields_with_new_data_on_Edit_Address_Page_within_a_caseNoCounty() {
        clearAndFillText(addContactInfo.addressLineOne, "1 Test Ct.");
        selectDropDown(createConsumerProfilePage.consumerCityDropdown, "Brown");
        selectDropDown(addContactInfo.state, "MD Maryland");
        waitFor(2);
        clearFiled(addContactInfo.zip);
        clearAndFillText(addContactInfo.zip, "12345");
        selectDropDown(addContactInfo.addressType, "Physical");
        clearFiled(addContactInfo.startDate);
        addContactInfo.startDate.sendKeys(getCurrentDate());
    }

    @When("I expend the Address record on INEB contact page")
    public void i_expend_the_Address_record_INEB() {
        for(int i=0; i<=2;i++){
            try{
                demographicContactInfo.addressesStatuses.get(0).click();
                break;
            }
            catch(Exception e){}
        }
        }

    @When("I verify error message on add address page {string}")
    public void i_verify_error_message_add_address(String message) {
        jsClick(addContactInfo.saveButton);
        waitForVisibility(addContactInfo.listOfErrorPopUpMessagesONAddAddressPage.get(1),4);
        String actualError = addContactInfo.listOfErrorPopUpMessagesONAddAddressPage.get(1).getText();
        assertTrue(actualError.contains(message), actualError + " not displayed found " );
    }

    @When("I am navigated to the read-only view Address Information page")
    public void i_am_navigated_to_the_read_only_view_Address_Information_page() {
        waitFor(3);
        assertTrue(demographicContactInfo.addAddressButton.isDisplayed());
        }

}