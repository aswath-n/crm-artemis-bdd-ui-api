package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CRM_CaseMemberStepDef extends CRMUtilities implements ApiBase {


//    CRMUtilities crmUtils = new CRMUtilities();
    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMConsumerSearchResultPage searchResult = new CRMConsumerSearchResultPage();
    CRMDemographicMemberPage memberPage = new CRMDemographicMemberPage();
    CRMAddCaseMemberPage addCaseMemberPage = new CRMAddCaseMemberPage();
    ThreadLocal<Map<String, Object>> newConsumer = ThreadLocal.withInitial(() -> getNewTestData2());
    CRMManualCaseConsumerSearchPage manualCaseConsumerSearch = new CRMManualCaseConsumerSearchPage();
    PublishDPBIEventsOutboundCorrespondenceChangesStepDefs pubCorr = new PublishDPBIEventsOutboundCorrespondenceChangesStepDefs();
    CRMDashboardPage dashBoard = new CRMDashboardPage();
    CRMAddPrimaryIndividualPage crmAddPrimaryIndividualPage = new CRMAddPrimaryIndividualPage();
    CRMContactRecordDashboardPage dashboardPage = new CRMContactRecordDashboardPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    CRMCaseAndConsumerSearchPage caseConsumerSrhPage = new CRMCaseAndConsumerSearchPage();

    private final ThreadLocal<String> firstName = ThreadLocal.withInitial(() -> newConsumer.get().get("name").toString());
    private final ThreadLocal<String> lastName = ThreadLocal.withInitial(() -> newConsumer.get().get("surname").toString());
    private final ThreadLocal<List<String>> firstrowData = ThreadLocal.withInitial(ArrayList::new);
    private ThreadLocal<Map<Number, List<String>>> caseMemberTableData = ThreadLocal.withInitial(LinkedHashMap::new);
    private ThreadLocal<String> updatedFirstName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedLastName = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedDob = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedGender = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedRelationShip = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedStartDate = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedEndDate = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerIdEditingCaseMember = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> updatedSsn = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> fullNameTab = ThreadLocal.withInitial(String::new);
    private ThreadLocal<String> consumerInsuredCaseID = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> linkedGetInsuredCaseID = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<String> linkedGetInsuredConsumerID = ThreadLocal.withInitial(String::new);

    @When("I searched customer have First Name as {string} and Last Name as {string} in DCEB")
    public void i_searched_customer_have_First_Name_as_and_Last_Name_as_in_DCEB(String firstname, String lastname) {
        waitFor(4);
        waitForVisibility(contactRecord.firstName, 3);
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.lastName, lastname);
        CRM_WorkQueueStepDef.consumerName.set(firstname + " " + lastname);
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(3);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        jsClick(contactRecord.searchButtonIniContact);
        waitFor(5);
    }

    @When("I searched customer have First Name as {string} and Last Name as {string}")
    public void i_searched_customer_have_First_Name_as_and_Last_Name_as(String firstname, String lastname) {
        //browserUtils.hover(dashBoard.btnMenuList);
        waitFor(4);
        waitForVisibility(contactRecord.firstName, 1);
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.lastName, lastname);
        CRM_WorkQueueStepDef.consumerName.set(firstname + " " + lastname);
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(3);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        waitFor(2);
        contactRecord.searchButton.click();
        waitFor(3);
    }


    public void i_searched_customer_have_First_Name_Last_Name_in_name_field(String firstname, String lastname) {
        //BrowserUtils.hover(dashBoard.btnMenuList);
        waitFor(4);
        waitForVisibility(caseConsumerSrhPage.Name, 1);
        caseConsumerSrhPage.Name.click();
        caseConsumerSrhPage.Name.sendKeys(lastname+","+firstname);
       // caseConsumerSrhPage.Name.sendKeys(lastname);
        caseConsumerSrhPage.searchButton.click();
    }

    @When("I search customer have First Name as {string} and Last Name as {string} and Contact Type as {string}")
    public void i_search_customer_have_First_Name_as_and_Last_Name_as_and_Contact_Type_as(String firstname, String lastname, String type) {
        waitFor(4);
        waitForVisibility(contactRecord.firstName, 3);
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.lastName, lastname);
        CRM_WorkQueueStepDef.consumerName.set(firstname + " " + lastname);
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(3);
        singleSelectFromDropDown(manualContactRecordSearchPage.consumerTypeDropdown, type);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        jsClick(contactRecord.searchButton);
        waitFor(5);
    }

    @When("I searched customer have First Name as {string} and Last Name as {string} in third party")
    public void i_searched_customer_have_First_Name_as_and_Last_Name_as_in_third_party(String firstname, String lastname) {
        waitFor(4);
        waitForVisibility(contactRecord.firstNameThirdParty.get(1), 3);
        clearAndFillText(contactRecord.firstNameThirdParty.get(1), firstname);
        clearAndFillText(contactRecord.lastNameThirdParty.get(1), lastname);
        CRM_WorkQueueStepDef.consumerName.set(firstname + " " + lastname);
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(3);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        jsClick(contactRecord.searchButton);
        waitFor(5);
    }

    @And("I click on first user radio button and {string} {string} check boxes")
    public void iClickOnFirstUserRadioButtonAndCheckBoxes(String checkbox, String checkbox1) {
        waitFor(3);
        click(contactRecord.expandFistConsumer);
        click(contactRecord.lstFirstConsumerNameRadioButton);
        if (checkbox.compareToIgnoreCase("DOB") == 0 || checkbox1.compareToIgnoreCase("DOB") == 0)
            contactRecord.DOBradioButton.click();
        waitFor(2);
        if (checkbox.compareToIgnoreCase("SSN") == 0 || checkbox1.compareToIgnoreCase("SSN") == 0) {
            try {
                contactRecord.SSNradioButton.click();
            } catch (Exception NoSuchElementException) {
                contactRecord.labelPhoneNum.click();
            }
        }
        waitFor(2);
        if (checkbox.compareToIgnoreCase("PHONE NUMBER") == 0 || checkbox1.compareToIgnoreCase("PHONE NUMBER") == 0)
            contactRecord.labelPhoneNum.click();
    }

    @And("I click on first user second radio button and {string} {string} check boxes")
    public void iClickOnSecondUserRadioButtonAndCheckBoxes(String checkbox, String checkbox1) {
        waitFor(3);
        click(contactRecord.expandFistConsumer);
        click(contactRecord.secondConsumerRadioBtn);
        if (checkbox.compareToIgnoreCase("DOB") == 0 || checkbox1.compareToIgnoreCase("DOB") == 0) {
            contactRecord.DOBradioButton.click();
        }
        waitFor(2);
        if (checkbox.compareToIgnoreCase("SSN") == 0 || checkbox1.compareToIgnoreCase("SSN") == 0) {
            contactRecord.SSNradioButton.click();
        }
        waitFor(2);
        if (checkbox.compareToIgnoreCase("PHONE NUMBER") == 0 || checkbox1.compareToIgnoreCase("PHONE NUMBER") == 0) {
            contactRecord.labelPhoneNum.click();
        }
    }

    @And("I click to expand on primary individual")
    public void iClickToExpandOnPrimaryIndividual() {
        waitFor(3);
            contactRecord.expandFistConsumer.click();

    }

    @Then("I verify validate&link and Consumer Authenticated is displayed")
    public void iVerifyValidateLinkAndConsumerAuthenticatedIsDisplayed() {
        waitFor(3);
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.validateAndLink));
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.consumerAuth));
    }

    @And("I click on primary individual record in search result")
    public void iClickOnPrimaryIndividualRecordInSearchResult() {
        contactRecord.expandFistConsumer.click();
        waitFor(3);
        contactRecord.fistConsumerRadioBtn.click();
    }

    @And("I click on primary individual Link ID")
    public void iClickOnPrimaryIndividualLinkID() {
        click(contactRecord.individualLinkID);
        waitFor(3);
        click(contactRecord.editIcon);
    }

    @Then("I verify validate&link is displayed")
    public void iVerifyValidateLinkIsDisplayed() {
        BrowserUtils.waitFor(5);
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.validateAndLink));
        assertEquals(pubCorr.getTextIfElementIsDisplayed(contactRecord.validateAndLink), "link\n" +
                "VALIDATE & LINK");
    }

    @Then("I verify validate is displayed")
    public void iVerifyValidateIsDisplayed() {
        BrowserUtils.waitFor(5);
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.validate));
        System.out.println(pubCorr.getTextIfElementIsDisplayed(contactRecord.validate));
        assertEquals(pubCorr.getTextIfElementIsDisplayed(contactRecord.validate), "link\n" +
                "VALIDATE");
    }

    @Then("I click validate and I verify after Action")
    public void iClickValidateAndIVerifyAfterAction() {
        click(contactRecord.validate);
        BrowserUtils.waitFor(12);
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.linkTaskToCaseOnly));
        assertEquals(pubCorr.getTextIfElementIsDisplayed(contactRecord.linkTaskToCaseOnly), "LINK TO CASE ONLY");
        assertTrue(pubCorr.elementIsDisplayed(contactRecord.linkCaseConsumer));
        assertEquals(pubCorr.getTextIfElementIsDisplayed(contactRecord.linkCaseConsumer), "link\n" +
                "LINK CASE/CONSUMER");
    }


    @And("I select {string} to create a Task")
    public void iSelectToCreateATask(String type) {
        Driver.getDriver().findElement(By.xpath("//span[text()='" + type + "']")).click();
    }

    @Given("I searched existing case where First Name as {string} and Last Name as {string}")
    public void i_searched_existing_case_where_First_Name_as_and_Last_Name_as(String firstname, String lastname) {
        try {
            clearAndFillText(contactRecord.thirdPartyFirstName.get(0), firstname);
            clearAndFillText(contactRecord.thirdPartyLastName.get(0), lastname);
        } catch (Exception e) {
            clearAndFillText(contactRecord.thirdPartyFirstName.get(1), firstname);
            clearAndFillText(contactRecord.thirdPartyLastName.get(1), lastname);
        }
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        contactRecord.searchButton.click();
    }

    @Then("I verify Case Member section displayed")
    public void i_verify_Case_Member_section_displayed() {
        assertTrue(memberPage.CaseMemberLabel.isDisplayed(), "no Lebel found");
        assertTrue(memberPage.addCaseMemeberButton.isDisplayed(), "no add button found");
    }

    @When("I click on Add Button for Case Member")
    public void i_click_on_Add_Button_for_Case_Member() {
        // scrollToElement(memberPage.addCaseMemeberButton);
        waitFor(1);
        memberPage.addCaseMemeberButton.click();
    }

    @Then("I should be navigated to Add Case Members page")
    public void i_should_be_navigated_to_Add_Case_Members_page() {
        addCaseMemberPage.caseMembersHeader.isDisplayed();
        addCaseMemberPage.caseNavigationHeader.isDisplayed();
    }

    @Then("I verify first name and last name fields doesn't accept more than fifty characters")
    public void i_verify_first_name_and_last_name_fields_doesn_t_accept_more_than_characters() {
        // Write code here that turns the phrase above into concrete actions
        //crmAddPrimaryIndividualPage.piFirstNameField.sendKeys("");
        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, "abcdefghijklmopqrstuvwxyzzxczxczxczxczxczxczxczxccc");
        String temp = crmAddPrimaryIndividualPage.piFirstNameField.getText();
        assertTrue(temp.length() <= 50, "First have more then 50 Charaters");
        clearAndFillText(addCaseMemberPage.lastNameInput, "abcdefghijklmopqrstuvwxyzzxczxczxczxczxczxczxczxccc");
        String temp2 = addCaseMemberPage.lastNameInput.getText();
        assertTrue(temp2.length() <= 50, "Last Name Field allows more then 50 Characters");
        waitFor(1);
        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, "12345&*(%$#@");
        String temp3 = crmAddPrimaryIndividualPage.piFirstNameField.getText();
        assertTrue(temp3.length() <= 0, "First name field allows numbers and special characters");
    }

    @Then("I verify all the fields displayed on the Add Case Member page")
    public void i_verify_all_the_fields_displayed_on_the_Add_Case_Member_page() {
        crmAddPrimaryIndividualPage.piFirstNameField.isDisplayed();
        addCaseMemberPage.lastNameInput.isDisplayed();
        addCaseMemberPage.middleNameInput.isDisplayed();
        addCaseMemberPage.dobInput.isDisplayed();
        addCaseMemberPage.genderInput.isDisplayed();
        addCaseMemberPage.ageInput.isDisplayed();
        addCaseMemberPage.relationshipInput.isDisplayed();
        addCaseMemberPage.preferredLanguageField.isDisplayed();
        addCaseMemberPage.ssnInput.isDisplayed();
        crmAddPrimaryIndividualPage.piStartDate.isDisplayed();
        crmAddPrimaryIndividualPage.piEndDate.isDisplayed();
        addCaseMemberPage.SaveButton.isDisplayed();
        addCaseMemberPage.CancelButton.isDisplayed();
    }

    @Then("I verify calculated Age as per {string}")
    public void i_verify_calculated_Age_as_per(String date) {
        clearAndFillText(addCaseMemberPage.dobInput, date);
        addCaseMemberPage.SaveButton.click();
        assertTrue(addCaseMemberPage.ageInput.getAttribute("value").equals("27"), "calculated age is wrong");
    }

    @When("I enter the mandatory fields details {string}, {string}, {string}, {string}, {string},{string} {string},{string},{string}")
    public void i_enter_the_mandatory_fields_details(String str1, String str2, String dob, String gender, String startdate, String enddate, String language, String relation, String ssn) {
        waitForVisibility(crmAddPrimaryIndividualPage.piFirstNameField, 5);
        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, firstName.get());
        clearAndFillText(addCaseMemberPage.lastNameInput, lastName.get());
        clearAndFillText(addCaseMemberPage.dobInput, dob);
        clearAndFillText(addCaseMemberPage.ssnInput, ssn);
        waitFor(2);
        selectDropDown(crmAddPrimaryIndividualPage.labelReceiveCorrespondence, "Yes");
        if (startdate.equalsIgnoreCase("today"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getCurrentDate());
        else if (startdate.equalsIgnoreCase("future"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getGreaterDate(100));
        else if (startdate.equalsIgnoreCase("past"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, getPriorDate(100));
        else
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, startdate);
        if (enddate.equalsIgnoreCase("today"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getCurrentDate());
        else if (enddate.equalsIgnoreCase("future"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getGreaterDate(100));
        else if (enddate.equalsIgnoreCase("past"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, getPriorDate(10));
        else
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, enddate);
        click(addCaseMemberPage.relationshipInput);
        if (relation.equalsIgnoreCase("child"))
            click(addCaseMemberPage.relationshipChildOption);
        else if (relation.equalsIgnoreCase("spouse"))
            click(addCaseMemberPage.relationshipISpouseOption);
        else
            click(addCaseMemberPage.relationshipGuardianOption);
        waitFor(1);
        click(addCaseMemberPage.genderInput);
        if (gender.equalsIgnoreCase("Female"))
            click(addCaseMemberPage.genderFemaleOption);
        else
            click(addCaseMemberPage.genderMaleOption);
        waitFor(1);
        //click(addCaseMemberPage.preferredLanguageField);
//        if (language.equalsIgnoreCase("English"))
//            click(addCaseMemberPage.languageEnglishOption);
//        else
//            click(addCaseMemberPage.langguageSpanishOption);
        waitFor(1);
        click(addCaseMemberPage.SaveButton);
    }

    @Then("I verify that case member is added successfully")
    public void i_verify_that_case_member_is_added_successfully() {
        waitForVisibility(memberPage.CaseMemberLabel, 10);
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);

        int count = 0;
        String name = firstName.get().concat(" " + lastName.get());
        boolean flag = false;
        System.out.println("number of pages =>" + pages.size());
        List<String> cellData = new ArrayList<String>();
        if (pages.size() == 1) {
            for (WebElement row : memberPage.caseMemberEachRows) {
                if (row.findElement(xpath("./td[3]")).getText().equalsIgnoreCase(name)) {
                    flag = true;
                    break;
                }
            }

        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                for (WebElement row : memberPage.caseMemberEachRows) {
                    if (row.findElement(xpath("./td[3]")).getText().equalsIgnoreCase(name)) {
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
                Driver.getDriver().findElements(memberPage.pages).get(pagenumber).click();
                waitFor(2);

            }
        }
        assertTrue(flag);
    }


    @Then("I verify the case member {string}")
    public void i_verify_the_case_member(String status) {
        memberPage.CaseMemberLabel.isDisplayed();
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);
        List<WebElement> cells = new ArrayList<WebElement>();
        Map<Number, List<String>> tableData = new LinkedHashMap<Number, List<String>>();
        List<String> cellData = new ArrayList<String>();

        int count = 0;
        int row = 0;
        boolean flag = false;
        String name = firstName.get().concat(" " + lastName.get());
        //to get all table date in list of string
        System.out.println("number of pages =>" + pages.size());
        if (pages.size() == 1) {
            cells.addAll(Driver.getDriver().findElements(memberPage.tablecells));
            for (int temp = 0; temp < cells.size(); temp++) {
                cellData.add(cells.get(temp).getText());
            }
            System.out.println("number of cells=>" + cells.size() + " on page");

        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                cells.addAll(Driver.getDriver().findElements(memberPage.tablecells));
                for (int temp = 0; temp < cells.size(); temp++) {
                    cellData.add(cells.get(temp).getText());
                }
                System.out.println("number of cells=>" + cells.size() + " on page =>" + pagenumber);
                Driver.getDriver().findElements(memberPage.pages).get(pagenumber).click();
                cells.clear();
            }
        }

        int expectedrows = cellData.size() / 8;
        System.out.println("total cells =>" + cellData.size() + " Expected Rows=>" + expectedrows);
        //System.out.println(cellData);
        //to get all List data in to map
        for (int i = 0; i < expectedrows; i++) {
            tableData.put(i, new ArrayList<String>());
            for (int j = 0; j < 8; j++) {
                tableData.get(i).add(cellData.get(count));//Stale element
                count++;
            }
        }

        System.out.println("total number of rows =>" + tableData.size());
        //to compare data from map
        for (int rownumber = 0; rownumber < expectedrows; rownumber++) {
            System.out.print(tableData.get(rownumber).get(1));
            System.out.println(" ==> " + name);
            if (name.equalsIgnoreCase(tableData.get(rownumber).get(1))) {
                row = rownumber;
                flag = true;
                System.out.println("flage => " + flag);
                break;
            }
        }
        System.out.println(tableData);
        assertTrue(name.equalsIgnoreCase(tableData.get(row).get(1)));
        assertTrue(tableData.get(row).get(7).equalsIgnoreCase(status));
    }


    @When("I click on added Case Member")
    public void i_click_on_added_Case_Member() {
        memberPage.CaseMemberLabel.isDisplayed();
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);
        List<WebElement> cells = new ArrayList<WebElement>();
        Map<Number, List<String>> tableData = new LinkedHashMap<Number, List<String>>();
        List<String> cellData = new ArrayList<String>();

        int count = 0;
        int row = 0;
        boolean flag = false;
        String name = firstName.get().concat(" " + lastName.get());
        //to get all table date in list of string
        System.out.println("number of pages =>" + pages.size());
        if (pages.size() == 1) {
            List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
            for (int i = 0; i < tablerowlist.size(); i++) {
                if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                    Driver.getDriver().findElements(memberPage.tableRows).get(i).click();
                    break;
                }
            }
        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
                for (int i = 0; i < tablerowlist.size(); i++) {
                    if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                        Driver.getDriver().findElements(memberPage.tableRows).get(i).click();
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
                else
                    Driver.getDriver().findElements(memberPage.pages).get(pagenumber).click();
            }
        }
    }


    public List<WebElement> caseMemeberTableData(List<WebElement> pagelist) {
        System.out.println("number of pages =>" + pagelist.size());
        List<WebElement> tablescells = new ArrayList<WebElement>();
        for (int pagenumber = 1; pagenumber < pagelist.size(); pagenumber++) {
            // cells= Driver.getDriver().findElements(memberPage.tablecells);
            tablescells.addAll(Driver.getDriver().findElements(memberPage.tablecells));
            System.out.println("number of cells=>" + tablescells.size() + " on page =>" + pagenumber);
            Driver.getDriver().findElements(memberPage.pages).get(pagenumber).click();
        }
        return tablescells;
    }

    @When("I click on any existing Case Member")
    public void i_click_on_any_existing_Case_Member() {
        waitFor(2);
        memberPage.CaseMemberLabel.isDisplayed();
        List<String> rowdata = new ArrayList<String>();
        firstrowData.set(getFirstRowData());
        consumerIdEditingCaseMember.set(Driver.getDriver().findElements(memberPage.tableRows).get(1).findElement(By.xpath("./td[2]")).getText());
        Driver.getDriver().findElements(memberPage.tableRows).get(1).click();
    }

    @When("I update the following fields {string}, {string}, {string}, {string}, {string},{string} {string},{string},{string}")
    public void i_update_the_following_fields(String str1, String str2, String dob, String gender, String startdate, String enddate, String language, String relation, String ssn) {
        waitFor(2);
        String temp = getText(crmAddPrimaryIndividualPage.piFirstNameField);
        clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, "Update" + temp);
        clearAndFillText(addCaseMemberPage.lastNameInput, lastName.get());
        // clearAndFillText(addCaseMemberPage.dobInput,dob);
        if (!ssn.equalsIgnoreCase("<SSN>"))
            clearAndFillText(addCaseMemberPage.ssnInput, ssn);
       /* if(startdate.equalsIgnoreCase("today"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate,getCurrentDate());
        else if(startdate.equalsIgnoreCase("future"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate,getGreaterDate(100));
        else if(startdate.equalsIgnoreCase("past"))
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate,getPriorDate(100));
        else
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate,startdate);
        if(enddate.equalsIgnoreCase("today"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate,getCurrentDate());
        else if(enddate.equalsIgnoreCase("future"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate,getGreaterDate(100));
        else if(enddate.equalsIgnoreCase("past"))
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate,getPriorDate(10));
        else
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate,enddate);
            */

        String rinput = addCaseMemberPage.relationshipInput.getText();
        click(addCaseMemberPage.relationshipInput);
        if (rinput.equalsIgnoreCase("child"))
            click(addCaseMemberPage.relationshipISpouseOption);
        else if (rinput.equalsIgnoreCase("spouse"))
            click(addCaseMemberPage.relationshipGuardianOption);
        else
            click(addCaseMemberPage.relationshipChildOption);
        waitFor(1);

        String gInput = addCaseMemberPage.genderInput.getText();
        click(addCaseMemberPage.genderInput);
        if (gInput.equalsIgnoreCase("Female"))
            click(addCaseMemberPage.genderMaleOption);
        else
            click(addCaseMemberPage.genderFemaleOption);
        waitFor(1);

        String lInput = addCaseMemberPage.preferredLanguageField.getText();
        click(addCaseMemberPage.preferredLanguageField);
        if (lInput.equalsIgnoreCase("Spanish"))
            click(addCaseMemberPage.languageEnglishOption);
        else
            click(addCaseMemberPage.langguageSpanishOption);
        waitFor(1);
        click(addCaseMemberPage.SaveButton);
    }

    @Then("I verify that case member is updated successfully")
    public void i_verify_that_case_member_is_updated_successfully() {
        // Write code here that turns the phrase above into concrete actions
        memberPage.CaseMemberLabel.isDisplayed();
        caseMemberTableData.set(getTableDataByRow());
        int rowNumber = 0;
        for (int i = 0; i < caseMemberTableData.get().size(); i++) {
            if (caseMemberTableData.get().get(i).get(1).contains("Update")) {
                rowNumber = i;
                break;
            }
        }
        assertFalse(firstrowData.get().contains(caseMemberTableData.get().get(rowNumber)));
    }


    @When("I inactivate Case Member")
    public void i_inactivate_Case_Member() {
        addCaseMemberPage.checkboxLabelInactive.isDisplayed();
        click(addCaseMemberPage.checkboxInactive);
        assertTrue(getText(crmAddPrimaryIndividualPage.piEndDate).contains(""));
        click(addCaseMemberPage.SaveButton);
    }

    @Then("I verify the status as inactive")
    public void i_verify_the_status_as_inactive() {
        memberPage.CaseMemberLabel.isDisplayed();
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);
        Map<Number, List<String>> tableData = new LinkedHashMap<Number, List<String>>();
        int count = 0;
        int row = 0;
        boolean flag = false;
        String name = firstName.get().concat(" " + lastName.get());
        //to get all table date in list of string
        System.out.println("number of pages =>" + pages.size());
        if (pages.size() == 1) {
            List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
            for (int i = 0; i < tablerowlist.size(); i++) {
                if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                    count = i;
                    break;
                }
            }
        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
                for (int i = 0; i < tablerowlist.size(); i++) {
                    if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                        count = i;
                        flag = true;
                        break;
                    }
                }
                System.out.println();
                if (flag) {
                    assertTrue(Driver.getDriver().findElements(memberPage.tableRows).get(count).getText().contains("INACTIVE"));
                    hover(Driver.getDriver().findElements(memberPage.tablecells).get(count * 8));
                    memberPage.inactiveEndDateTooltipText.isDisplayed();
                    System.out.println(memberPage.inactiveEndDateTooltipVaule.getText());
                    assertEquals(getCurrentDate(), memberPage.inactiveEndDateTooltipVaule.getText());
                    break;
                } else {
                    click(pages.get(pagenumber));
                    waitFor(2);
                }
            }
        }

    }


    List<String> getFirstRowData() {
        List<String> cellData = new ArrayList<String>();
        for (int temp = 0; temp < 8; temp++) {
            cellData.add(Driver.getDriver().findElements(memberPage.tablecells).get(temp).getText());
        }
        System.out.println(cellData);
        return cellData;
    }

    Map<Number, List<String>> getTableDataByRow() {
        Map<Number, List<String>> tableData = new LinkedHashMap<Number, List<String>>();
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);
        List<WebElement> cells = new ArrayList<WebElement>();
        int count = 0;
        int row = 0;
        boolean flag = false;
        System.out.println("number of pages =>" + pages.size());
        List<String> cellData = new ArrayList<String>();
        if (pages.size() == 1) {
            cells.addAll(Driver.getDriver().findElements(memberPage.tablecells));
            for (int temp = 0; temp < cells.size(); temp++) {
                cellData.add(cells.get(temp).getText());
            }
            System.out.println("number of cells=>" + cells.size() + " on page");

        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                cells.addAll(Driver.getDriver().findElements(memberPage.tablecells));
                for (int temp = 0; temp < cells.size(); temp++) {
                    cellData.add(cells.get(temp).getText());
                }
                System.out.println("number of cells=>" + cells.size() + " on page =>" + pagenumber);
                Driver.getDriver().findElements(memberPage.pages).get(pagenumber).click();
                cells.clear();
            }
        }
        int expectedrows = cellData.size() / 8;
        System.out.println("total cells =>" + cellData.size() + " Expected Rows=>" + expectedrows);
        System.out.println(cellData);
        for (int i = 0; i < expectedrows; i++) {
            tableData.put(i, new ArrayList<String>());
            for (int j = 0; j < 8; j++) {
                tableData.get(i).add(cellData.get(count));//Stale element
                count++;
            }
        }
        System.out.println("total number of rows =>" + tableData.size());
        System.out.println(tableData);
        return tableData;
    }

    @Then("I hover over the status and verify end date is current date")
    public void i_hover_over_the_status_and_verify_end_date_is_current_date() {
        memberPage.CaseMemberLabel.isDisplayed();
        List<WebElement> pages = Driver.getDriver().findElements(memberPage.pages);
        Map<Number, List<String>> tableData = new LinkedHashMap<Number, List<String>>();
        int count = 0;
        int row = 0;
        boolean flag = false;
        String name = firstName.get().concat(" " + lastName.get());
        //to get all table date in list of string
        System.out.println("number of pages =>" + pages.size());
        if (pages.size() == 1) {
            List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
            for (int i = 0; i < tablerowlist.size(); i++) {
                if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                    count = i;
                    break;
                }
            }
        } else {
            for (int pagenumber = 1; pagenumber < pages.size(); pagenumber++) {
                List<WebElement> tablerowlist = Driver.getDriver().findElements(memberPage.tableRows);
                for (int i = 0; i < tablerowlist.size(); i++) {
                    if (Driver.getDriver().findElements(memberPage.tableRows).get(i).getText().contains(name)) {
                        count = i;
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
                assertTrue("INACTIVE".contains(Driver.getDriver().findElements(memberPage.tableRows).get(count).getText()));
                waitFor(1);
                hover(Driver.getDriver().findElements(memberPage.tablecells).get(count * 8));
                waitFor(1);
                highLightElement(memberPage.inactiveEndDateTooltipText);
                memberPage.inactiveEndDateTooltipText.isDisplayed();
                System.out.println(memberPage.inactiveEndDateTooltipVaule.getText());
                assertTrue(getCurrentDate().equalsIgnoreCase(memberPage.inactiveEndDateTooltipVaule.getText()));

            }
        }
    }

    @When("I update one or more of the following fields {string}, {string}, {string}, {string}, {string},{string} {string},{string},{string}")
    public void updateOneOrMoreFields(String firstName, String lastName, String dob, String gender, String startdate, String enddate, String language, String relation, String ssn) {
        waitFor(2);
        String stringToReplace = "";
        if (firstName.contains("random")) {
            updatedFirstName.set(getTextFromInputField(crmAddPrimaryIndividualPage.piFirstNameField));
            stringToReplace = updatedFirstName.get().substring(0, 2);
            updatedFirstName.set(updatedFirstName.get().replaceAll(stringToReplace, RandomStringUtils.randomAlphabetic(2)));

            clearAndFillText(crmAddPrimaryIndividualPage.piFirstNameField, updatedFirstName.get());
        }
        if (lastName.contains("random")) {
            updatedLastName.set(getTextFromInputField(addCaseMemberPage.lastNameInput));
            stringToReplace = updatedLastName.get().substring(0, 2);
            updatedLastName.set(updatedLastName.get().replaceAll(stringToReplace, RandomStringUtils.randomAlphabetic(2)));
            clearAndFillText(addCaseMemberPage.lastNameInput, updatedLastName.get());
        }

        if (!dob.isEmpty()) {
            if (dob.contains("random")) {
                updatedDob.set(getPriorDate(3650));
            } else {
                updatedDob.set(dob);
            }
            clearAndFillText(addCaseMemberPage.dobInput, updatedDob.get());
        }


        if (startdate.equalsIgnoreCase("today"))
            updatedStartDate.set(getCurrentDate());
        else if (startdate.equalsIgnoreCase("future"))
            updatedStartDate.set(getGreaterDate(100));
        else if (startdate.equalsIgnoreCase("past"))
            updatedStartDate.set(getPriorDate(2));
        else if (!startdate.isEmpty())
            updatedStartDate.set(startdate);
        if (!updatedStartDate.get().isEmpty())
            clearAndFillText(crmAddPrimaryIndividualPage.piStartDate, updatedStartDate.get());

        if (enddate.equalsIgnoreCase("today"))
            updatedEndDate.set(getCurrentDate());
        else if (enddate.equalsIgnoreCase("future"))
            updatedEndDate.set(getGreaterDate(100));
        else if (enddate.equalsIgnoreCase("past"))
            updatedEndDate.set(getPriorDate(2));
        else if (!enddate.isEmpty())
            updatedEndDate.set(enddate);
        if (!updatedEndDate.get().isEmpty())
            clearAndFillText(crmAddPrimaryIndividualPage.piEndDate, updatedEndDate.get());

        String rinput = addCaseMemberPage.relationshipInput.getText();
        click(addCaseMemberPage.relationshipInput);
        if (rinput.equalsIgnoreCase("child"))
            click(addCaseMemberPage.relationshipISpouseOption);
        else if (rinput.equalsIgnoreCase("spouse"))
            click(addCaseMemberPage.relationshipGuardianOption);
        else
            click(addCaseMemberPage.relationshipChildOption);
        waitFor(1);
        updatedRelationShip.set(addCaseMemberPage.relationshipInput.getText());

        String gInput = addCaseMemberPage.genderInput.getText();
        click(addCaseMemberPage.genderInput);
        if (gInput.equalsIgnoreCase("Female"))
            click(addCaseMemberPage.genderMaleOption);
        else
            click(addCaseMemberPage.genderFemaleOption);
        waitFor(1);
        updatedGender.set(addCaseMemberPage.genderInput.getText());

        waitFor(1);
        click(addCaseMemberPage.SaveButton);
    }

    @Then("I verify that case member name is updated successfully")
    public void i_verify_that_case_member_name_updated_successfully() {

        memberPage.CaseMemberLabel.isDisplayed();
        caseMemberTableData.set(getTableDataByRow());
        boolean recordFound = false;
        for (int i = 0; i < caseMemberTableData.get().size(); i++) {
            if (caseMemberTableData.get().get(i).get(1).equalsIgnoreCase(consumerIdEditingCaseMember.get())
                    && caseMemberTableData.get().get(i).get(2).equalsIgnoreCase(updatedFirstName + " " + updatedLastName)) {
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    @Then("I verify that case member date of birth is updated successfully")
    public void i_verify_that_case_member_dob_updated_successfully() {
        // Write code here that turns the phrase above into concrete actions
        memberPage.CaseMemberLabel.isDisplayed();
        memberPage.caseMemberDobEyeIcon.click();
        caseMemberTableData.set(getTableDataByRow());
        waitFor(1);
        boolean recordFound = false;
        for (int i = 0; i < caseMemberTableData.get().size(); i++) {
            if (updatedDob.get().contains("/"))
                updatedDob.set(updatedDob.get().replaceAll("\\/", ""));
            if (caseMemberTableData.get().get(i).get(0).equalsIgnoreCase(consumerIdEditingCaseMember.get())
                    && caseMemberTableData.get().get(i).get(2).replaceAll("\\/", "").equalsIgnoreCase(updatedDob.get())) {
                recordFound = true;
                break;
            }
        }
    }

    @Then("I verify that case member gender is updated successfully")
    public void i_verify_that_case_member_gender_updated_successfully() {
        // Write code here that turns the phrase above into concrete actions
        memberPage.CaseMemberLabel.isDisplayed();
        caseMemberTableData.set(getTableDataByRow());
        boolean flag = false;
        for (int i = 0; i < caseMemberTableData.get().size(); i++) {
            String ageGender = caseMemberTableData.get().get(i).get(4);
            System.out.println(ageGender.substring(ageGender.length() - 1, ageGender.length()));
            if (caseMemberTableData.get().get(i).get(1).equalsIgnoreCase(consumerIdEditingCaseMember.get())
                    && updatedGender.get().startsWith(ageGender.substring(ageGender.length() - 1, ageGender.length()))) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @When("I clear mandatory case member fields and click on save")
    public void clearMandatoryFieldsCaseMember() {
        waitFor(2);
        updatedFirstName.set(getTextFromInputField(crmAddPrimaryIndividualPage.piFirstNameField));
        clearFiled(crmAddPrimaryIndividualPage.piFirstNameField);

        updatedLastName.set(getTextFromInputField(addCaseMemberPage.lastNameInput));
        clearFiled(addCaseMemberPage.lastNameInput);

        updatedDob.set(getTextFromInputField(addCaseMemberPage.dobInput));
        clearFiled(addCaseMemberPage.dobInput);

        updatedStartDate.set(getTextFromInputField(crmAddPrimaryIndividualPage.piStartDate));
        clearFiled(crmAddPrimaryIndividualPage.piStartDate);

        updatedRelationShip.set(getTextFromInputField(addCaseMemberPage.relationshipInput));
        addCaseMemberPage.relationshipInput.click();
        waitFor(2);
        Driver.getDriver().findElement(xpath("//div[contains(@id, 'relationship')]//ul/li[1]")).click();
        waitFor(2);

        updatedGender.set(getTextFromInputField(addCaseMemberPage.genderInput));
        addCaseMemberPage.genderInput.click();
        waitFor(2);
        Driver.getDriver().findElement(xpath("//div[contains(@id, 'gender')]//ul/li[1]")).click();
        waitFor(1);

        updatedSsn.set(getTextFromInputField(addCaseMemberPage.ssnInput));
        clearFiled(addCaseMemberPage.ssnInput);
        waitFor(1);
        click(addCaseMemberPage.SaveButton);
    }

    @Then("I verify that case member mandatory field error messages")
    public void verifyErrorMEssages() {
        waitFor(5);
        // assertEquals(addCaseMemberPage.firstNameInputErrorMessage.getText().toLowerCase(), "Please enter A First Name".toLowerCase());
        assertTrue(addCaseMemberPage.firstNameInputErrorMessage.getText().toLowerCase().contains("Please enter".toLowerCase())
                && addCaseMemberPage.firstNameInputErrorMessage.getText().toLowerCase().contains("First Name".toLowerCase()));

        //assertEquals(addCaseMemberPage.lastNameInputErrorMessage.getText().toLowerCase(), "Please enter A Last Name".toLowerCase());
        assertTrue(addCaseMemberPage.lastNameInputErrorMessage.getText().toLowerCase().contains("Please enter".toLowerCase())
                && addCaseMemberPage.lastNameInputErrorMessage.getText().toLowerCase().contains("Last Name".toLowerCase()));

        //assertEquals(addCaseMemberPage.dobInputErrorMessage.getText().toLowerCase(), "Please select DOB".toLowerCase());
        assertTrue(addCaseMemberPage.dobInputErrorMessage.getText().toLowerCase().contains("Please select".toLowerCase())
                && addCaseMemberPage.dobInputErrorMessage.getText().toLowerCase().contains("DOB".toLowerCase()));

        // assertEquals(addCaseMemberPage.startDateInputErrorMessage.getText().toLowerCase(), "Please select start date".toLowerCase());
        assertTrue(addCaseMemberPage.startDateInputErrorMessage.getText().toLowerCase().contains("Please select".toLowerCase())
                && addCaseMemberPage.startDateInputErrorMessage.getText().toLowerCase().contains("start date".toLowerCase()));

        //assertEquals(addCaseMemberPage.relationshipInputErrorMessage.getText().toLowerCase(), "Please select Relationship to PI".toLowerCase());
        assertTrue(addCaseMemberPage.relationshipInputErrorMessage.getText().toLowerCase().contains("Please select".toLowerCase())
                && addCaseMemberPage.relationshipInputErrorMessage.getText().toLowerCase().contains("Relationship".toLowerCase()));

        //assertEquals(addCaseMemberPage.genderInputErrorMessage.getText().toLowerCase(), "Please select gender".toLowerCase());
        assertTrue(addCaseMemberPage.genderInputErrorMessage.getText().toLowerCase().contains("Please select".toLowerCase())
                && addCaseMemberPage.genderInputErrorMessage.getText().toLowerCase().contains("gender".toLowerCase()));

        //assertEquals(addCaseMemberPage.ssnInputErrorMessage.getText().toLowerCase(), "Please enter SSN".toLowerCase());
        assertTrue(addCaseMemberPage.ssnInputErrorMessage.getText().toLowerCase().contains("Please enter".toLowerCase())
                && addCaseMemberPage.ssnInputErrorMessage.getText().toLowerCase().contains("SSN".toLowerCase()));

    }

    @When("I click cancel button on update case member page")
    public void clickCancelUpdateCaseMember() {
        waitFor(1);
        addCaseMemberPage.CancelButton.click();
        waitFor(2);
    }

    @Then("I verify previous values for the case member fileds remains after cancel")
    public void verifyPreviousValues() {
        waitFor(3);
        assertEquals(getTextFromInputField(crmAddPrimaryIndividualPage.piFirstNameField), updatedFirstName.get());
        assertEquals(getTextFromInputField(addCaseMemberPage.lastNameInput), updatedLastName.get());
        assertEquals(getTextFromInputField(addCaseMemberPage.dobInput), updatedDob.get());
        assertEquals(getTextFromInputField(crmAddPrimaryIndividualPage.piStartDate), updatedStartDate.get());
        assertEquals(getTextFromInputField(addCaseMemberPage.relationshipInput), updatedRelationShip.get());
        assertEquals(getTextFromInputField(addCaseMemberPage.genderInput), updatedGender.get());
        assertEquals(getTextFromInputField(addCaseMemberPage.ssnInput), updatedSsn.get());

    }

    @Then("I verify that case member start date and end date is updated successfully")
    public void iVerifyStartDateAndEndDate() {
        // Write code here that turns the phrase above into concrete actions
        waitFor(3);
        memberPage.CaseMemberLabel.isDisplayed();

        List<WebElement> rows = Driver.getDriver().findElements(memberPage.tableRows);
        String endDate = "";
        String startDate = "";
        int count = 1;
        for (WebElement row : rows) {
            if (count == 1) {
                count++;
                continue;
            }

            String consumeId = row.findElement(By.xpath("./td[2]")).getText();
            if (consumeId.equalsIgnoreCase(consumerIdEditingCaseMember.get())) {
                hover(row.findElement(By.xpath("./td[9]")));
                waitFor(1);
                endDate = memberPage.inactiveEndDateTooltipVaule.getText();
                startDate = memberPage.inactiveStartDateTooltipVaule.getText();
                break;
            }

        }
        if (endDate.contains("/"))
            endDate = endDate.replaceAll("\\/", "");
        if (startDate.contains("/"))
            startDate = startDate.replaceAll("\\/", "");
        if (updatedEndDate.get().contains("/"))
            updatedEndDate.set(updatedEndDate.get().replaceAll("\\/", ""));
        if (updatedStartDate.get().contains("/"))
            updatedStartDate.set(updatedStartDate.get().replaceAll("\\/", ""));
        assertEquals(endDate, updatedEndDate);
        assertEquals(startDate, updatedStartDate);

    }

    @When("I verify all the Field Labels are Mixed Case")
    public void verifyfieldLabelTextForAddCM() {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z]).+$";
        String lblFname = crmAddPrimaryIndividualPage.piFirstNameField.findElement(xpath("./ancestor::div[2]/label")).getText();
        lblFname = lblFname.replaceAll("\\*", "").replaceAll(" ", "");
        assertTrue(lblFname.matches(pattern));


        lblFname = addCaseMemberPage.lastNameInput.findElement(xpath("./ancestor::div[2]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = addCaseMemberPage.ssnInput.findElement(xpath("./ancestor::div[2]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = crmAddPrimaryIndividualPage.piStartDate.findElement(xpath("./ancestor::div[2]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = crmAddPrimaryIndividualPage.piEndDate.findElement(xpath("./ancestor::div[2]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = addCaseMemberPage.relationshipInput.findElement(xpath("./ancestor::div[3]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = addCaseMemberPage.genderInput.findElement(xpath("./ancestor::div[3]/label")).getText();
        assertTrue(lblFname.matches(pattern));
        lblFname = addCaseMemberPage.preferredLanguageField.findElement(xpath("./ancestor::div[3]/label")).getText();
        assertTrue(lblFname.matches(pattern));

    }

    @Then("I see Consumer Name is next to Case Icon within an Active Contact")
    public void Consumer_Name_next_to_Case_Icon_within_an_Active_Contact() {
        waitFor(1);
        assertEquals(manualCaseConsumerSearch.nameNextToCaseIcon.getText(), fullNameTab,
                "Viewed Consumer Name is not next to Case Icon");

    }

    @Then("I click on Save button for Case Member")
    public void i_click_on_Save_button_for_Case_Member() {
        jsClick(addCaseMemberPage.SaveButton);
        waitFor(5);
    }

    @And("I update case member's middle name")
    public void i_update_authorized_representatives_middle_name() {
        scrollUpUsingActions(2);
        clearAndFillText(addCaseMemberPage.middleNameInput, "G");
        scrollDownUsingActions(3);

    }

    @And("I link the contact to an existing Case or Consumer Profile in NJ-SBE")
    public void linkTheContactRecordToExistingCase() {
        iClickOnFirstUserRadioButtonAndCheckBoxes("DOB", "SSN");
        iVerifyValidateLinkAndConsumerAuthenticatedIsDisplayed();
        waitFor(7);
        contactRecord.validateAndLink.click();
        waitFor(2);
    }

    @And("I click validate and link button")
    public void iClickValidateAndLinkButton() {
        click(contactRecord.validateAndLink);
        waitFor(3);
    }

    @Given("I searched existing case where First Name as {string} and CaseId as {string}")
    public void i_searched_existing_case_where_First_Name_as_and_CaseId_as(String firstname, String caseId) {
        contactRecord.btnAdvancedSearch.click();
        waitFor(1);
        clearAndFillText(contactRecord.firstName, firstname);
        waitFor(1);
        clearAndFillText(contactRecord.contactCaseId, caseId);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + caseId);
        }
        System.out.println(fullNameTab);
        click(contactRecord.searchButton);
    }

    @Given("I searched existing case where First Name as {string} and ConsumerId as {string}")
    public void i_searched_existing_case_where_First_Name_as_and_ConsumerId_as(String firstname, String ConsumerId) {
        clearAndFillText(contactRecord.firstName, firstname);
        clearAndFillText(contactRecord.consumerIDTextbox, ConsumerId);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + ConsumerId);
        }
        System.out.println(fullNameTab);
        click(contactRecord.searchButton);
    }

    @And("GetInsured is Displayed as {string}")
    public void getinsuredIsDisplayedAs(String arg0) {
        assertTrue(isElementDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + arg0 + "']"))));
    }

    @And("I verify GetInsure Consumer Id in Consumer in Contact is {string}")
    public void iVerifyGetInsureConsumerIdInConsumerInContactIs(String arg0) {
        String txt = Driver.getDriver().findElement(By.xpath("//td[text()='Consumer Profile']/parent::tr/td[2]")).getText();
        assertEquals(arg0, txt);
    }

    @And("I verify Link component Consumer Profile Id is {string}")
    public void iVerifyLinkComponentConsumerProfileIdIs(String arg0) {
        waitFor(3);
        String txt = contactRecord.consumerIdLink.getText();
        assertEquals(arg0, txt);
    }

    @When("I searched consumer created through api with First Name as {string} and Last Name as {string}")
    public void searchConsumerCreatedThroughApi(String firstName, String lastName) {
        String firstNameRes = firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString();
        String lastNameRes = lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString();
        i_searched_customer_have_First_Name_as_and_Last_Name_as(firstNameRes, lastNameRes);
    }

    @And("I capture GetInsured Case ID in active contact tab")
    public void iCaptureGetInsuredCaseIdinActiveContactWidget() {
        waitFor(2);
        consumerInsuredCaseID.set(Driver.getDriver().findElement(By.xpath("//th[text()='GetInsured CASE ID']/parent::tr/parent::thead/following::tbody/tr/td[1]")).getText());

    }

    @Then("I verify Case ID, First Name as {string} and Last Name as {string} in Active Contact Widget")
    public void iVerifyCaseIDFirstNameAsAndLastNameAsPotterInActiveContactWidget(String firstName, String lastName) throws Throwable {
        assertTrue(Driver.getDriver().findElement(By.partialLinkText(consumerInsuredCaseID.get())).isDisplayed());
        assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+firstName + "  " + lastName+"')]")).isDisplayed());
    }

    @When("I searched consumer created by api script")
    public void iSearchConsumerCreatedByApiScripts() {
        hover(dashBoard.btnMenuList);
        waitForVisibility(contactRecord.firstName, 400);
        clearAndFillText(contactRecord.firstName, APIUtilitiesForUIScenarios.consumerFirstName.get());
        clearAndFillText(contactRecord.lastName, APIUtilitiesForUIScenarios.consumerLastName.get());
        CRM_WorkQueueStepDef.consumerName.set(APIUtilitiesForUIScenarios.consumerFirstName.get() + " " + APIUtilitiesForUIScenarios.consumerLastName.get());
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(1);
        jsClick(contactRecord.searchButton);
        waitFor(2);
    }

    @When("I searched case and consumer created by api")
    public void iSearchForCaseAndConsumerCreatedByApi() {
        hover(dashBoard.btnMenuList);
        waitForVisibility(contactRecord.firstName, 500);
        clearAndFillText(contactRecord.firstName, APIUtilitiesForUIScenarios.consumerFirstName.get());
        clearAndFillText(contactRecord.lastName, APIUtilitiesForUIScenarios.consumerLastName.get());
        CRM_WorkQueueStepDef.consumerName.set(APIUtilitiesForUIScenarios.consumerFirstName.get() + " " + APIUtilitiesForUIScenarios.consumerLastName.get());
        CRM_GeneralTaskStepDef.taskValues.get().put("consumerName", CRM_WorkQueueStepDef.consumerName.get());
        waitFor(1);
        fullNameTab.set(APIUtilitiesForUIScenarios.consumerFirstName.get() + " " + APIUtilitiesForUIScenarios.consumerLastName.get());
        click(contactRecord.searchButton);
        waitFor(2);
    }

    @When("I save GetInsured caseid and consumerId from Linked Consumer Details")
    public void iSaveGetInsuredCaseIdConsumerIdfromLinkedConsumerDetails() {
        linkedGetInsuredCaseID.set(contactRecord.getInsuredCaseId.getText());
        linkedGetInsuredConsumerID.set(contactRecord.getInsuredConsumerId.getText());
    }

    @Then("I see {string} external ID value {string} is displayed")
    public void iSeeExternalIDValueIsDisplayed(String consumerType, String externalId) {
        String expectedExternalId = "";
        if (consumerType.equalsIgnoreCase("PI")) {
            expectedExternalId = memberPage.firstPIConsumerID.getText();
        } else {
            expectedExternalId = memberPage.firstCMConsumerID.getText();
        }
        System.out.println("expectedExternalId = " + expectedExternalId);
        assertTrue(expectedExternalId.equals(externalId));
    }


    @When("I search for customer with First Name as {string} and Last Name as {string}")
    public void i_search_for_customer_with_First_Name_as_and_Last_Name_as(String firstName, String lastName) {
        String firstNameRes = firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString();
        String lastNameRes = lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString();
        i_searched_customer_have_First_Name_as_and_Last_Name_as(firstNameRes, lastNameRes);
    }



    @When("I search for customer with First Name as {string},Last Name as {string} in name")
    public void i_search_for_customer_with_First_Name_Last_Name_in_name_field(String firstName, String lastName) {
        String firstNameRes = firstName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerFirstName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(firstName).toString();
        String lastNameRes = lastName.equals("fromCaseLoaderApi") ? APIConsumerPopulationDmnController.consumerLastName.get()
                : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(lastName).toString();
        i_searched_customer_have_First_Name_Last_Name_in_name_field(firstNameRes, lastNameRes);
    }


    @When("I click on the crm case id")
    public void i_click_on_the_crm_case_id() {
        BrowserUtils.waitFor(3);
        manualCaseConsumerSearch.caseIdCreated.click();
        BrowserUtils.waitFor(3);
    }

    @And("I click on first consumerID of consumer profile on manual search result")
    public void clickOnConsumerIDforConsumerProfileLink() {

        for (int i = 0; i < searchResult.listOfConsumerIdsFromManualSearchResult.size(); i++) {
            if (searchResult.listOfCaseIdsFromManualSearchResult.get(i).getText().contains("--")) {
                searchResult.listOfConsumerIdsFromManualSearchResult.get(i).click();
                break;
            }
        }
    }

    @Given("I searched existing from third party search case where First Name as {string} and Last Name as {string}")
    public void i_searched_existing_case_where_First_Name_as_and_Last_Name_as_thirdParty(String firstname, String lastname) {
        clearAndFillText(contactRecord.thirdPartyFirstName.get(1), firstname);
        clearAndFillText(contactRecord.thirdPartyLastName.get(1), lastname);
        contactRecord.searchButton.click();
    }

    @Then("I click validate and I verify GetInsured CASE ID is display")
    public void iClickValidateAndIVerifyGetInsured() {
        click(contactRecord.validate);
        waitForVisibility(contactRecord.getInsuredCASEID, 10);
        assertTrue(isElementDisplayed(contactRecord.getInsuredCASEID), "GetInsured CASE ID not displayed");

    }

    @Given("I searched existing case where First Name as {string} and Last Name as {string} on third party page")
    public void i_searched_existing_case_where_First_Name_as_and_Last_Name_as_third_party_page(String firstname, String lastname) {
        clearAndFillText(contactRecord.thirdPartyFirstName.get(1), firstname);
        clearAndFillText(contactRecord.thirdPartyLastName.get(1), lastname);
        synchronized (fullNameTab){
            fullNameTab.set(firstname + " " + lastname);
        }
        System.out.println(fullNameTab);
        contactRecord.searchButton.click();
    }
    @And("I verify Link component Consumer Profile Id is {string} on active page")
    public void iVerifyLinkComponentConsumerProfileIdIs_On_active_page(String arg0) {
        String txt = contactRecord.linkSectionID.getText();
        assertEquals(arg0, txt);
    }

}


