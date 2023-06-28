package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static com.maersk.crm.utilities.World.getWorld;
import static org.testng.Assert.assertTrue;

public class CRM_ViewSearchResultInfoStepDef extends CRMUtilities implements ApiBase {


    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage ();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage ();
    CRMActiveContactPage activeContact=new CRMActiveContactPage();

    private final ThreadLocal<List<String>> searchResults = ThreadLocal.withInitial(ArrayList::new);
//    private WebDriver driver = Driver.getDriver ();
    private final ThreadLocal<List<String>> caseIdVal = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<List<String>> consumerName = ThreadLocal.withInitial(ArrayList::new);
    @Then("I see all Search results have SSN field value masked in {string} format")
    public void i_see_all_Search_results_have_SSN_field_value_masked_in_format(String format) {
        waitFor(2);
        for (WebElement e : searchResult.SSNs) {
            if (e.getText().equalsIgnoreCase("--")) {
                continue;
            }
            searchResults.get().add(e.getText());
        }
        for (String ssn : searchResults.get()) {
            System.out.println(ssn);
            assertTrue(ssn.equals(format), "SSN has to be displayed in " + format + ".");
        }
    }


    @Then("I see unmasking button displayed")
    public void i_see_unmasking_button_displayed() {
        assertTrue(searchResult.ssnEyeUnMaskButton.isDisplayed());
        waitFor(2);
    }

    @When("I and click on unmasking button")
    public void i_and_click_on_unmasking_button() {
        waitFor(2);
        searchResult.ssnEyeUnMaskButton.click();
        assertTrue(searchResult.ssnEyeMaskButton.isDisplayed());
    }

    @Then("I see SSN {int} digits value unmasked")
    public void i_see_SSN_digits_value_unmasked(Integer snnLength) {
        waitFor(2);
        for (WebElement e : searchResult.SSNs) {
            if (e.getText().equalsIgnoreCase("--")) {
                continue;
            }
            searchResults.get().add(e.getText());
        }
        for (String ssn : searchResults.get()) {
            ssn= ssn.substring(7,10);
            System.out.println("&&&&&&&&&&&&&&&&&"+ssn);
            assertTrue(ssn.matches("[0-9]*"), "SSN has to contain only digits. actual SSN = "+ssn);
            assertTrue(ssn.length() == snnLength, "SSN has to have " + snnLength + " digits.");
        }

    }


    @When("I search for an existing contact by SSN {string}")
    public void i_search_for_an_existing_contact_by_SSN(String expected) {
        waitFor(3);
        click(contactRecord.ssnSearch);
        waitFor(2);
        clearAndFillText(contactRecord.ssnSearch, expected);
    }

    @Then("I see all search results according to SSN {string}")
    public void i_see_all_search_results_according_to_SSN(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.SSNs) {
            searchResults.get().add(e.getText());
        }
        for (String actualSsn : searchResults.get()) {
            assertTrue(actualSsn.equals(expected), "SSN search result is incorrect");
        }
    }

    @When("I search for an existing contact by {string} and value {string}")
    public void i_search_for_an_existing_contact_by_node(String search_element, String element_value) {
        if (search_element.trim().equalsIgnoreCase("firstname")) {
            clearAndFillText(contactRecord.firstName, element_value);
        } else if (search_element.trim().equalsIgnoreCase("lastname")) {
            clearAndFillText(contactRecord.lastName, element_value);
        } else {
            assert false : "Wrong Element Selection " + search_element;
        }
    }

    @When("I search for an existing contact by first name {string}")
    public void i_search_for_an_existing_contact_by_first_name(String expected) {
        waitFor(4);
        //clearAndFillText(contactRecord.firstName, expected);
        contactRecord.firstName.sendKeys(expected);
        waitFor(1);
        String actual = contactRecord.firstName.getAttribute("value");
        assertTrue(expected.equalsIgnoreCase(actual.substring(0, expected.length())), "Searching wrong first name criteria");
    }

    @Then("I see all search results according to the first name {string}")
    public void i_see_all_search_results_according_to_the_first_name(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.FirstNames) {
            searchResults.get().add(e.getText());
        }
        for (String actualFName : searchResults.get()) {
            assertTrue(expected.equalsIgnoreCase(actualFName.substring(0, expected.length())), "First name search result is incorrect");
        }
    }

    @When("I search for an existing contact by last name {string}")
    public void i_search_for_an_existing_contact_by_last_name(String expected) {
        clearAndFillText(contactRecord.lastName, expected);
        waitFor(2);
        String actual = contactRecord.lastName.getAttribute("value");
        assertTrue(expected.equalsIgnoreCase(actual.substring(0, expected.length())), "Searching wrong last name criteria");
    }

    @Then("I see all search results according to the last name {string}")
    public void i_see_all_search_results_according_to_the_last_name(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.LastNames) {
            searchResults.get().add(e.getText());
        }
        for (String actualLName : searchResults.get()) {
            System.out.println("actualLName "+actualLName);
            assertTrue(expected.equalsIgnoreCase(actualLName.substring(0, expected.length())), "Last name search result is incorrect");
        }
    }

    @When("I search for an existing contact by DOB {string}")
    public void i_search_for_an_existing_contact_by_DOB(String expected) {
        clearAndFillText(contactRecord.dobSearch, expected);
        waitFor(1);
        String actual = contactRecord.dobSearch.getAttribute("value");
        assertTrue(expected.equals(actual), "Searching wrong DOB criteria");
    }

    @Then("I see all search results according to DOB {string}")
    public void i_see_all_search_results_according_to_DOB(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.DOBs) {
            searchResults.get().add(e.getText());
        }
        for (String actualDOB : searchResults.get()) {
            assertEquals(actualDOB, expected, "DOB search result is incorrect");
        }
    }

    @When("I search for an existing Case ID {string}")
    public void i_search_for_an_existing_contact_by_Case_ID(String expected) {
        clearAndFillText(contactRecord.uniqueIDSearch, expected);
        waitFor(1);
        String actual = contactRecord.uniqueIDSearch.getAttribute("value");
        assertTrue(expected.equals(actual), "Searching wrong Unique ID criteria");
    }

    @Then("I see all search results according to Unique ID {string}")
    public void i_see_all_search_results_according_to_Unique_ID(String expected) {
        waitFor(2);
        for (WebElement e : searchResult.LastNames) {
            searchResults.get().add(e.getText());
        }
        for (String actualID : searchResults.get()) {
            assertTrue(actualID.equals(expected), "Unique ID search result is incorrect");
        }
    }

    @When("I search for a Consumer with blank fields")
    public void i_search_for_a_Consumer_with_blank_fields() {
        assertTrue(contactRecord.firstName.getAttribute("value").isEmpty(), "First name field has value");
        assertTrue(contactRecord.lastName.getAttribute("value").isEmpty(), "Last name field has value");
        assertTrue(contactRecord.ssnSearch.getAttribute("value").isEmpty(), "SSN field has value");
        assertTrue(contactRecord.dobSearch.getAttribute("value").isEmpty(), "DOB field has value");
       //refactoring 10/25/18 till the Unique ID field not being displayed clarified
        // assertTrue(contactRecord.uniqueIDSearch.getAttribute("value").isEmpty(), "Unique ID field has value");
    }

    @Then("I see warning to enter search parameter")
    public void i_see_warning_to_enter_search_parameter() {
       assertTrue(contactRecord.enterSearchParametersWarning.isDisplayed(), "Warning: 'Please enter the search parameters' is not displayed");
    }

    @Then("I see No Records Available message on Search Consumer page is displayed")
    public void i_see_no_records_available_message_on_search_consumer_page_is_displayed() {
       assertTrue(textIsNotPresent("contactRecord.noRecordsAvailableMessage"), "'No Records Available' message is not displayed");
    }

    @Then("I see no results are displayed")
    public void i_see_no_results_are_displayed() {
        assertTrue(contactRecord.noRecordsAvailableMessage.isDisplayed(), "'No Records Available' message is not displayed");
    }

    @And("I search for the consumer created using API")
    public void searchConsumerCreatedByAPIStep(){
        clearAndFillText(contactRecord.lastName, getWorld().apiConsumerRestController.get().apiconsumerLastName.get());

    }

    /* Verifies the Individual records are displayed in the search results
    Author - Aswath
    Date - 03/19/2019
    Parameters -NA
    */
    @Then("I verify the only individual records are listed")
    public void i_verify_the_only_individual_records_are_listed() {
        for (WebElement cid : contactRecord.serachHdr) {
            List<WebElement> listOfHdr = cid.findElements ( By.xpath ( "span" ) );
            for (WebElement e : listOfHdr) {
                caseIdVal.get().add ( e.getText () );
                if (!caseIdVal.get().equals ( "Consumer Role" ))
                    continue;
            }
        }
    }

     /* Verifies the case is linked to consumer
    Author - Aswath
    Date - 03/19/2019
    Parameters -NA
    */
     @Then("I verify the case is linked to consumer")
     public void i_verify_the_case_is_linked_to_consumer() {

        for (WebElement cid : contactRecord.listOfCaseID) {
            List<WebElement> listOfcases = cid.findElements ( By.xpath ( "td[2]" ) );
            for (WebElement e : listOfcases) {
                waitForVisibility ( e, 10 );
                String casIDvalue = e.getText ();
                caseIdVal.get().add ( casIDvalue );
                if (!casIDvalue.isEmpty ()) {
                    continue;
                }
            }
        }
        Verify_the_PI_record();
    }

    /* Verify the Primary Individual record of the consumer search
    Author - Aswath
    Date - 03/19/2019
    Parameters -NA
    */

    public void Verify_the_PI_record()
    {
        scrollToElement (  contactRecord.expandSearchResult);
        contactRecord.expandSearchResult.click();
        for (WebElement lsd : contactRecord.listOfSearchhDtlTbl) {
            List<WebElement> listOfresults = lsd.findElements ( By.xpath ( "td[1]"));
            for (WebElement r : listOfresults) {

                String actualConName= r.getText ();
                String searchConName1 = contactRecord.firstName.getAttribute ( "value" );
                String searchConName2 = contactRecord.lastName.getAttribute ( "value" );
                String conSearchFullName= searchConName1+" "+searchConName2;
                int i;
                for(i=1;i<=listOfresults.size ();i++) {
                    if (actualConName.equalsIgnoreCase ( conSearchFullName )) {
                    WebElement PI=Driver.getDriver().findElement ( By.xpath ( "//div[@class='collapse show']//tr['"+i+"']//td[2]" ));
                    waitForVisibility ( PI,10 );
                    String PIval= PI.getText ();
                    if("Primary Individual"==PIval)
                        break;

                    }
                }


    }}}

    /* Verifies the case is linked to consumer for Authorized record
    Author - Aswath
    Date - 03/19/2019
    Parameters -NA
    */
    @Then("I verify the case is linked to consumer for Authorized record")
    public void i_verify_the_case_is_linked_to_consumer_for_authorized_record() {

        for (WebElement cid : contactRecord.listOfCaseID) {
            List<WebElement> listOfcases = cid.findElements ( By.xpath ( "td[2]" ) );
            for (WebElement e : listOfcases) {
                waitForVisibility ( e, 10 );
                String casIDvalue = e.getText ();
                caseIdVal.get().add ( casIDvalue );
                if (!casIDvalue.isEmpty ()) {
                    continue;
                }
            }
        }

    }
    @Then("I see all Search results with first name as {string}, last name as {string}, ssn as {string} and dob as {string}")
    public void verifySearchResult(String firstName, String lastName, String ssn, String dob) {
        List<String> fnames = new ArrayList<String>();
        List<String> lnames = new ArrayList<String>();
        List<String> ssns = new ArrayList<String>();
        List<String> dobs = new ArrayList<String>();
        boolean isNextExists = true;

        List<WebElement> eyeIcons = Driver.getDriver().findElements(By.xpath("//h6[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/thead//span[text()='visibility']"));
        for(WebElement eyeIcon:eyeIcons){
            waitFor(1);
            try{
                eyeIcon.click();
            }catch (Exception e){
                jsClick(eyeIcon);
            }
        }

        do{
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//h6[contains(text(),'SEARCH RESULT')]/..//table[contains(@class, 'mt-4')]/tbody/tr[not(contains(@class,'collapse'))]"));
            for(WebElement row:rows){
                fnames.add(row.findElement(By.xpath("./td[4]")).getText());
                lnames.add(row.findElement(By.xpath("./td[5]")).getText());
                ssns.add(row.findElement(By.xpath("./td[7]")).getText());
                dobs.add(row.findElement(By.xpath("./td[6]")).getText());
            }
            try{
                String attrVal = searchResult.btnNext.getAttribute("disabled");
                if(attrVal==null){
                    isNextExists = true;
                    searchResult.btnNext.click();
                    waitFor(1);
                }else{
                    isNextExists = false;
                    break;
                }

            }catch(Exception e){
                isNextExists = false;
            }
        }while (isNextExists);
        if(!firstName.isEmpty()){
            for(String fname:fnames){
                assertTrue(fname.startsWith(firstName));
            }
        }

        if(!lastName.isEmpty()){
            for(String lname:lnames){
                assertTrue(lname.startsWith(lastName));
            }
        }

        if(!dob.isEmpty()){
            for(String adob:dobs){
                assertEquals(adob, dob);
            }
        }

        if(!ssn.isEmpty()){
            for(String assn:ssns){
                assertEquals(assn, ssn);
            }
        }
    }

    @Then("I verify {string} field {string} format")
    public void i_verify_field_format(String field, String fieldFormat) {
        if(field.equals("SSN")) {
            waitForVisibility(contactRecord.ssnSearch, 5);
            assertTrue(contactRecord.ssnSearch.getAttribute("value").equals(fieldFormat), "SSN field format isnt verified");
            waitFor(2);
        } else if (field.equals("DOB")){
            waitForVisibility(contactRecord.dobSearch, 5);
            assertTrue(contactRecord.dobSearch.getAttribute("value").equals(fieldFormat), "DOB field format isnt verified");
            waitFor(2);
        }
    }

    @Then("I and click on unmask button for {string} on Active Contact Record Page")
    public void i_and_click_on_unmask_button_for_on_Active_Contact_Record_Page(String value) {
        if(value.equals("SSN")) {
            searchResult.ssnMaskButtonSearchParameter.click();
            waitFor(2);
        }else if(value.equals("DOB")){
            searchResult.dobMaskButtonSearchParameter.click();
            waitFor(2);
        }
    }

    @Then("I verify that {string} field is disabled")
    public void i_verify_that_field_is_disabled(String field) {
        if(field.equals("SSN")) {
            assertFalse(contactRecord.ssnSearch.isEnabled(), "SSN field isnt disabled");
            waitFor(2);
        }else if(field.equals("DOB")){
            assertFalse(contactRecord.dobSearch.isEnabled(), "DOB field isnt disabled");
            waitFor(2);
        }
    }



    @Then("I verify that SSN in asteriks format unmasked on Authentication Grid")
    public void i_verify_that_SSN_in_asteriks_format_unmasked_on_Authentication_Grid() {
        assertTrue((contactRecord.ConsumerSSNVal.getText().equals("***-**-2473")),"SSN format isnt correct on authentication grid");
    }

    @Then("I verify {string} format on General Consumer in Contact")
    public void i_verify_format_on_General_Consumer_in_Contact(String field) {
        if(field.equals("SSN")) {
            waitFor(1);
            assertTrue((activeContact.ssn.getText().equals("***-**-2473")), "SSN format isnt correct on General Consumer in Contact, actual SSN = "+activeContact.ssn.getText());
        }
        else if(field.equals("DOB")){
            assertTrue(isMMddYYYYformat(activeContact.dob.getText()),"DOB format isnt correct on General Consumer in Contact actual DoB = " +activeContact.dob.getText());
        }

    }

    @Then("I verify search circle is display")
    public void I_verify_search_circle_is_display(){
        jsClick(contactRecord.ssnSearch);
        boolean isVisible = false;
        int count =0;
        waitFor(1);
        waitForClickablility(contactRecord.searchButton,5);
        do {
            jsClick(contactRecord.searchButton);
            isVisible = isElementDisplayed(activeContact.MuiCircularOnSearchPage);
            count++;
        }while (isVisible==false && count<10);
        if(count==10){
            clearText(contactRecord.firstName);
            waitFor(2);
            jsClick(contactRecord.btnAdvancedSearch);
            contactRecord.address1.sendKeys("ma");
            do {
                contactRecord.searchButton.click();
                isVisible = isElementDisplayed(activeContact.MuiCircularOnSearchPage);
                count++;
            }while (isVisible==false && count<10);
        }

           assertTrue(isVisible, "Circular not showing");

    }




}




