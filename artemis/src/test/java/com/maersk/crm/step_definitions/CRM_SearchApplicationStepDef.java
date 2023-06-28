package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController;
import com.maersk.crm.api_step_definitions.APIContactRecordController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Stream;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.externalAppID;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.applicationCodeAPI;
import static com.maersk.crm.step_definitions.LoginStepDef.projectName1;
import static com.maersk.crm.utilities.BrowserUtils.*;
import static java.util.Collections.sort;
import static org.testng.Assert.*;

public class CRM_SearchApplicationStepDef extends  BrowserUtils {
    CRMApplicationSearchPage crmSearchApplicationPage = new CRMApplicationSearchPage();
    CRMDashboardPage crmDashboardPage = new CRMDashboardPage();
    APIATSApplicationController saveSubmit = new APIATSApplicationController();
    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    final ThreadLocal<APIATSSendEventAndCreateLinksController> inbCorrApplication = ThreadLocal.withInitial(APIATSSendEventAndCreateLinksController::new);
    public static final ThreadLocal<String> apiApplicationId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> applicationId = ThreadLocal.withInitial(String::new);
    final ThreadLocal<CRM_CreateApplicationStepDef> crmCreateApplicationStepDef = ThreadLocal.withInitial(CRM_CreateApplicationStepDef::new);
    CRMSavedTaskSearchPage savedTaskSrchPage = new CRMSavedTaskSearchPage();
//    private List<String> memberMatchingAppIdForUI = new ArrayList<>();
    private final ThreadLocal<List<String>> allActual = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<List<String>> allExpected = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<String> receivedTo = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> receivedFrom = ThreadLocal.withInitial(String::new);

    @Given("I navigate to {string} and then to {string} in the left menu")
    public void i_navigate_to_and_then_to(String menuItem, String subMenuItem) {
        navigateToLeftMenu(menuItem, subMenuItem);
    }

    @Given("I search for a specific application in Application Tracking search by")
    public void i_search_for_a_specific_application_in_Application_Tracking_search_by(List<Map<String, String>> data) {
        Map<String, String> searchData = data.get(0);
        System.out.println("Search key:");
        for (String searchKey : searchData.keySet()) {
            System.out.println(searchKey + " : " + searchData.get(searchKey));
            addSearchBy(searchKey, searchData.get(searchKey));
        }
    }

    @When("I click on search button in search application page")
    public void i_click_on_search_button_in_search_application_page() {
        crmSearchApplicationPage.searchBtn.click();
        waitFor(2);
    }

    @Then("I can see more than {string} rows as search result in Application Tracking Search")
    public void i_can_see_more_than_rows_as_search_result_in_Application_Tracking_Search(String minNumOfRows) {
        int minNumberOfRows = Integer.parseInt(minNumOfRows);
        int actualNumberOfRows = getNumberOfResultsInCurrentPagination();
        Assert.assertTrue(actualNumberOfRows >= minNumberOfRows, "Different number of rows expected.");
    }

    @Then("I see first {string} rows")
    public void i_see_first_rows(String numberOfRowsInFirstPageResult) {
        int minNumberOfRows = Integer.parseInt(numberOfRowsInFirstPageResult);
        int actualNumberOfRows = getNumberOfResultsInCurrentPagination();
        Assert.assertEquals(actualNumberOfRows, minNumberOfRows, "Different number of rows expected.");
    }

    @Then("I see this results are part of the search results in Application Tracking Search:")
    public void i_see_this_results_are_part_of_the_search_results_in_Application_Tracking_Search(List<Map<String, String>> expectedData) {
        List<Map<String, String>> actualResultInCurrentPagination = getSearchResultsInCurrentPagination();

        boolean isAllExpectedDataInActual = true;

        for (Map<String, String> expectedRow : expectedData) {
            boolean isCurrentRowValid = true;
            for (Map<String, String> actualRow : actualResultInCurrentPagination) {
                isCurrentRowValid = true;
                for (String key : expectedRow.keySet()) {

                    if (!expectedRow.get(key).trim().equalsIgnoreCase(actualRow.get(key).trim())) {
                        isCurrentRowValid = false;
                        break;
                    }
                }

                if (isCurrentRowValid) {
                    break;
                }
            }

            if (isCurrentRowValid) {
                System.out.println("Founded:");
                System.out.println(expectedRow);
            } else {
                System.out.println("Missing result:");
                System.out.println(expectedRow);
                isAllExpectedDataInActual = false;
                break;
            }
        }

        Assert.assertTrue(isAllExpectedDataInActual, "Not all expected data were among the search result");
    }

    @Then("I verify Search Application basic page fields")
    public void i_verify_Search_Application_basic_page_fields() {
        Assert.assertTrue(crmSearchApplicationPage.applicationID.isDisplayed(), "application id field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.firstName.isDisplayed(), "firstName field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.lastName.isDisplayed(), "lastName field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.applicantDOB.isDisplayed(), "DOB field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.applicationCodeInput.isDisplayed(), "Application Code field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.statusDropDown.isDisplayed(), "Status field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.applicationCycle.isDisplayed(), "Application Cycle field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.dateRangeDropdown.isDisplayed(), "App received date dropdown is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.applicationReceivedDateFrom.isDisplayed(), "App Received Date From field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.applicationReceivedDateTo.isDisplayed(), "App Received Date To field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.program.isDisplayed(), "Program field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.channel.isDisplayed(), "Channel field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.externalAppIdInput.isDisplayed(), "External App ID field is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.caseIdInput.isDisplayed(), "Case ID field is not displayed");
    }

    @Then("I verify search and cancel buttons are displayed in search application page")
    public void i_verify_search_and_cancel_buttons_are_displayed_in_search_application_page() {
        Assert.assertTrue(crmSearchApplicationPage.searchBtn.isDisplayed(), "Search button is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.cancelBtn.isDisplayed(), "Cancel button is not displayed");
    }

    @Then("all search inputs in search application page should be cleared up")
    public void all_search_inputs_in_search_application_page_should_be_cleared_up() {
        BrowserUtils.waitFor(2);
        String applicationId = crmSearchApplicationPage.applicationID.getAttribute("value").trim();
        String firstName = crmSearchApplicationPage.firstName.getAttribute("value").trim();
        String lastName = crmSearchApplicationPage.lastName.getAttribute("value").trim();
        String applicationDob = crmSearchApplicationPage.applicantDOB.getAttribute("value").trim();
        String status = crmSearchApplicationPage.statusDropDownInput.getAttribute("value").trim();
        String appCycle = crmSearchApplicationPage.applicationCycleInput.getAttribute("value").trim();
        String program = crmSearchApplicationPage.programInput.getAttribute("value").trim();
        String channel = crmSearchApplicationPage.channelInput.getAttribute("value").trim();

        Assert.assertEquals(applicationId, "", "applicationId should be empty");
        Assert.assertEquals(firstName, "", "firstName should be empty");
        Assert.assertEquals(lastName, "", "lastName should be empty");
        Assert.assertEquals(applicationDob, "", "applicationDob should be empty");
        Assert.assertEquals(status, "", "status should be empty");
        Assert.assertEquals(appCycle, "", "appCycle should be empty");
        Assert.assertEquals(program, "", "program should be empty");
        Assert.assertEquals(channel, "", "channel should be empty");
    }

    @Then("I verify options for dropdown {string} are present")
    public void i_verify_options_for_dropdown_are_present(String dropdownKey, List<List<String>> data) {
        List<String> expectedOptions = data.get(0);
        List<String> actualOptions = getDropDownOptionsFor(dropdownKey);

        Assert.assertEquals(actualOptions.size(), expectedOptions.size());

        sort(expectedOptions);
        sort(actualOptions);

        for (int i = 0; i < expectedOptions.size(); i++) {
            Assert.assertEquals(actualOptions.get(i), expectedOptions.get(i));
        }
    }

    @Given("I navigate to {string} in left menu")
    public void i_navigate_to_in_left_menu(String menuItem) {
        navigateToLeftMenu(menuItem);
    }

    @Then("I will see a sub-menu as {string} under {string}")
    public void i_will_see_a_sub_menu_as_under(String subMenu, String mainMenu) {
        WebElement subMenuEl = getSubLink(mainMenu, subMenu);
        Assert.assertEquals(subMenu, subMenuEl.getText());
    }

    @Then("the UI will display the SEARCH APPLICATION screen.")
    public void the_UI_will_display_the_SEARCH_APPLICATION_screen() {
        Assert.assertTrue(crmSearchApplicationPage.searchTitle.isDisplayed(), "SEARCH APPLICATION is not displayed");
    }

    @When("I provide application id as {string} and it should accept it\\(shouldn't be empty): {string}")
    public void i_provide_application_id_as_and_it_should_accept_it_shouldn_t_be_empty(String id, String isAcceptedStr) {
        crmSearchApplicationPage.applicationID.sendKeys(id);
        boolean isAccepted = Boolean.parseBoolean(isAcceptedStr);
        BrowserUtils.waitFor(2);
        String value = crmSearchApplicationPage.applicationID.getAttribute("value");

        if (isAccepted) {
            Assert.assertEquals(id, value, id + " is valid id");
        } else {
            Assert.assertNotEquals(id, value, "Input id should not accept fully " + id);
        }

    }

    @Then("I provide first name as {string} and it should accept it\\(shouldn't be empty): {string}")
    public void i_provide_first_name_as_and_it_should_accept_it_shouldn_t_be_empty(String firstName, String isAcceptedStr) {
        crmSearchApplicationPage.firstName.sendKeys(firstName);
        boolean isAccepted = Boolean.parseBoolean(isAcceptedStr);
        BrowserUtils.waitFor(2);
        String value = crmSearchApplicationPage.firstName.getAttribute("value");

        if (isAccepted) {
            Assert.assertEquals(firstName, value, firstName + " is valid id");
        } else {
            Assert.assertNotEquals(firstName, value, "Input id should not accept fully " + firstName);
        }
    }

    @Then("I provide last name as {string} and it should accept it\\(shouldn't be empty): {string}")
    public void i_provide_last_name_as_and_it_should_accept_it_shouldn_t_be_empty(String lastName, String isAcceptedStr) {
        crmSearchApplicationPage.lastName.sendKeys(lastName);
        boolean isAccepted = Boolean.parseBoolean(isAcceptedStr);
        BrowserUtils.waitFor(2);
        String value = crmSearchApplicationPage.lastName.getAttribute("value");

        if (isAccepted) {
            Assert.assertEquals(lastName, value, lastName + " is valid id");
        } else {
            Assert.assertNotEquals(lastName, value, "Input id should not accept fully " + lastName);
        }
    }

    @Then("I provide applicant date of birth as {string} and it should accept it\\(shouldn't be empty): {string}")
    public void i_provide_applicant_date_of_birth_as_and_it_should_accept_it_shouldn_t_be_empty(String dob, String isAcceptedStr) {
        crmSearchApplicationPage.applicantDOB.sendKeys(dob + Keys.TAB);
        boolean isAccepted = Boolean.parseBoolean(isAcceptedStr);
        BrowserUtils.waitFor(2);

        List<WebElement> errorMsg = Driver.getDriver().findElements(By.xpath("//label[text()='APPLICANT DOB']/following-sibling::p"));


        if (isAccepted) {
            Assert.assertTrue(errorMsg.size() == 0, dob + " is valid input");

            String actualInputValue = crmSearchApplicationPage.applicantDOB.getAttribute("value");
            String expectedInputValue = formatDob(dob);
            Assert.assertEquals(expectedInputValue, actualInputValue);
        } else {
            Assert.assertTrue(errorMsg.size() > 0 && errorMsg.get(0).isDisplayed() && !errorMsg.get(0).getText().isEmpty(),
                    dob + " is invalid input. Expected error msg");
        }
    }

    private String formatDob(String dob) {
        if (dob.length() != 8) {
            throw new RuntimeException("Date of birth format is MM/DD/YYYY");
        }

        StringBuilder sbDob = new StringBuilder(dob);
        sbDob.insert(2, "/");
        sbDob.insert(5, "/");
        return sbDob.toString();
    }


    @Then("I print description for test case {string}")
    public void i_print_description_for_test_case(String desc) {
        System.out.println("-- Input description --");
        System.out.println(desc);
        System.out.println("-----------------------");
    }

    @Then("I should get pop up with error message {string}")
    public void i_should_get_pop_up_with_error_message(String errorMsg) {
        List<WebElement> errorMsgPopUp = Driver.getDriver().findElements(By.xpath("//span[@id='client-snackbar' and text()='" + errorMsg + "']"));
        Assert.assertTrue(errorMsgPopUp.size() > 0, errorMsg + " should be displayed");
        Assert.assertTrue(errorMsgPopUp.get(0).isDisplayed(), errorMsg + " should be displayed");
    }

    @When("I click on cancel button in Search Application page")
    public void i_click_on_cancel_button_in_Search_Application_page() {
        scrollToTop();
        waitFor(1);
        crmSearchApplicationPage.cancelBtn.click();
    }

    @Then("The search result in Search Application page should be clear")
    public void the_search_result_in_Search_Application_page_should_be_clear() {
        List<Map<String, String>> actualResultInCurrentPagination = getSearchResultsInCurrentPagination();
        Assert.assertEquals(0, actualResultInCurrentPagination.size());
    }

    @Then("I validate no search result message")
    public void i_validate_no_search_result_message() {
        Assert.assertTrue(crmSearchApplicationPage.noSrchResMsg.isDisplayed(), "'No Search Results Found' didn't displayed");
    }

    @Then("I see  results in given order in the search results in Application Tracking Search:")
    public void i_see_results_in_given_order_in_the_search_results_in_Application_Tracking_Search(List<List<String>> data) {
        List<String> expectedHeaders = data.get(0);
        List<WebElement> actualResTableHeaders = crmSearchApplicationPage.resultTableHeaders;

        Assert.assertEquals(expectedHeaders.size(), actualResTableHeaders.size(), "Headers are not matching");

        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (!expectedHeaders.get(i).equalsIgnoreCase(actualResTableHeaders.get(i).getText().trim())) {
                System.out.println("ACTUAL: " + actualResTableHeaders.get(i).getText().trim());
                System.out.println("EXPECTED: " + expectedHeaders.get(i));
                Assert.fail("Headers are not matching or order is different");
            }
        }
    }

    @Then("I see forward Arrow & number of pages for retrieved results is displayed at the bottom of the result list")
    public void i_see_forward_Arrow_number_of_pages_for_retrieved_results_is_displayed_at_the_bottom_of_the_result_list() {
        WebElement pagination = crmSearchApplicationPage.pagination;
        Assert.assertTrue(pagination.isDisplayed(), "Pagination of search result table is not displayed");
        List<WebElement> listOfPaginationActions = pagination.findElements(By.tagName("a"));
        Assert.assertTrue(listOfPaginationActions.size() > 0, "There is no pagination actions");

        boolean isForwardArrowDisplayed = false;
        for (WebElement el : listOfPaginationActions) {
            if (el.getAttribute("aria-label").equalsIgnoreCase("Go to last page")) {
                isForwardArrowDisplayed = true;
            }
        }

        Assert.assertTrue(isForwardArrowDisplayed, "forward link is not displayed in the search result table");
    }

    @Then("I see Item Count per page dropdown is displayed at the bottom of the result list")
    public void i_see_Item_Count_per_page_dropdown_is_displayed_at_the_bottom_of_the_result_list() {
        Assert.assertTrue(crmSearchApplicationPage.itemCountDD.isDisplayed(), "Item Count is not displayed");
    }

    @Then("I select Item Count in search result page as {string}")
    public void i_select_Item_Count_in_search_result_page_as(String itemCount) {
        waitFor(3);
        scrollToElement(crmSearchApplicationPage.itemCountDD);
        //WF TEAM: Commented these two lines because our locator is different. If you need similar step, please create one for you
        //   scrollToElement(savedTaskSrchPage.pageNationDD);
        //  savedTaskSrchPage.pageNationDD.click();
        crmSearchApplicationPage.itemCountDD.click();
        crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(itemCount)).findFirst().ifPresent(WebElement::click);
    }

    @Then("I see this results in search results in Application Tracking Search:")
    public void i_see_this_results_in_search_results_in_Application_Tracking_Search(List<Map<String, String>> expectedData) {
        List<Map<String, String>> actualResultInCurrentPagination = getSearchResultsInCurrentPagination();

        Assert.assertEquals(expectedData.size(), actualResultInCurrentPagination.size(), "Result size different");
        for (int i = 0; i < expectedData.size(); i++) {
            Map<String, String> expectedRow = expectedData.get(0);
            Map<String, String> actualRow = actualResultInCurrentPagination.get(0);
            Assert.assertEquals(expectedRow, actualRow, "Result search data is not matching");
        }
    }

    @When("I sorted in the results in the application tracking search by column {string}")
    public void i_sorted_in_the_results_in_the_application_tracking_search_by_column(String columnName) {
        for (WebElement column : crmSearchApplicationPage.resultTableHeaders) {
            if (column.getText().toLowerCase().contains(columnName.toLowerCase())) {
                column.click();
                System.out.println("Sorted by: " + columnName);
                break;

            }
        }

        BrowserUtils.waitFor(3);
    }

    @When("I click on first APPLICATION ID {string}")
    public void i_click_on_first_APPLICATION_ID(String applicationId) {
        if (applicationId.equalsIgnoreCase("apiApplicationID"))
            applicationId = apiApplicationId.get();
        List<WebElement> applicationIdLinks = getApplicationIdLinks(applicationId);
        applicationIdLinks.get(0).click();
        waitFor(2);
    }


    public int getNumberOfResultsInCurrentPagination() {
        return crmSearchApplicationPage.resultTableRowsTr.size();
    }

    public List<Map<String, String>> getSearchResultsInCurrentPagination() {
        List<Map<String, String>> actualData = new ArrayList<>();
        System.out.println("Number of rows: " + crmSearchApplicationPage.resultTableRowsTr.size());
        for (WebElement rowHtml : crmSearchApplicationPage.resultTableRowsTr) {
            List<WebElement> row = rowHtml.findElements(By.tagName("td"));

            if (row.size() < 9) {
                throw new RuntimeException("Incorrect table structure in the html");
            }

            Map<String, String> currentRowData = new HashMap<>();
            currentRowData.put("APPLICATION ID", row.get(1).getText());
            currentRowData.put("APPLICATION CODE", row.get(2).getText());
            currentRowData.put("LAST NAME", row.get(3).getText());
            currentRowData.put("FIRST NAME", row.get(4).getText());
            currentRowData.put("APPLICATION DOB", row.get(5).getText());
            currentRowData.put("STATUS", row.get(6).getText());
            currentRowData.put("APPLICATION CYCLE", row.get(7).getText());
            currentRowData.put("APPLICATION RECEIVED DATE", row.get(8).getText());
            currentRowData.put("PROGRAM", row.get(9).getText());
            currentRowData.put("CHANNEL", row.get(10).getText());
            currentRowData.put("EXTERNAL APP ID", row.get(11).getText());
            actualData.add(currentRowData);
        }

        return actualData;
    }

    public void addSearchBy(String key, String value) {
        switch (key.toUpperCase().trim()) {
            case "APPLICATION ID":
                crmSearchApplicationPage.applicationID.sendKeys(value);
                break;
            case "APPLICATION CODE":
                switch (value) {
                    case "API CREATED APPLICATION CODE":
                        crmSearchApplicationPage.applicationCodeInput.sendKeys(applicationCodeAPI.get());
                        break;
                    case "RANDOM CREATED APPLICATION CODE":
                        String randomApplicationCode = applicationCodeAPI.get().substring(0, 7) + generateRandomNumberChars(1);
                        System.out.println(randomApplicationCode + " ///");
                        crmSearchApplicationPage.applicationCodeInput.sendKeys(randomApplicationCode);
                        break;
                    default:
                        crmSearchApplicationPage.applicationCodeInput.sendKeys(value);
                }
                break;
            case "FIRST NAME":
                crmSearchApplicationPage.firstName.sendKeys(value);
                break;
            case "LAST NAME":
                crmSearchApplicationPage.lastName.sendKeys(value);
                break;
            case "APPLICANT DOB":
                crmSearchApplicationPage.applicantDOB.sendKeys(value);
                break;
            case "STATUS":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.statusDropDown, value);
                break;
            case "APPLICATION CYCLE":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.applicationCycle, value);
                break;
            case "PROGRAM":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.program, value);
                break;
            case "CHANNEL":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.channel, value);
                break;
            case "EXTERNAL APPLICATION ID":
                crmSearchApplicationPage.externalAppIdInput.sendKeys(value);
                break;
            case "CASE ID":
                switch (value) {
                    case "AUTO CREATED CASE ID":
                        crmSearchApplicationPage.caseIdInput.sendKeys(String.valueOf(APIContactRecordController.caseID.get()));
                        break;
                    default:
                        crmSearchApplicationPage.caseIdInput.sendKeys(value);
                }
                break;
            case "CASE TYPE":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.caseTypeInput, value);
                break;
            case "DATE RANGE TYPE":
                selectOptionFromMultiSelectDropDown(crmSearchApplicationPage.dateRangeDropdown, value);
                break;
            case "APP RECEIVED DATE FROM":
                switch (value) {
                    case "TODAY":
                        receivedFrom.set(getCurrentDate());
                        break;
                    case "TODAY-1":
                        receivedFrom.set(getPriorDateFormatMMddyyyy(1));
                        break;
                    case "FUTURE":
                        receivedFrom.set(getFutureDateInFormat("2"));
                        break;
                }
                crmSearchApplicationPage.applicationReceivedDateFrom.sendKeys(receivedFrom.get());
                break;
            case "APP RECEIVED DATE TO":
                switch (value) {
                    case "TODAY":
                        receivedTo.set(getCurrentDate());
                        break;
                    case "TODAY-1":
                        receivedTo.set(getPriorDateFormatMMddyyyy(1));
                        break;
                    case "FUTURE":
                        receivedFrom.set(getFutureDateInFormat("5"));
                        break;
                }
                crmSearchApplicationPage.applicationReceivedDateTo.sendKeys(receivedTo.get());
                break;
            default:
                throw new RuntimeException("key - " + key + " not found as search key");
        }
    }

    public List<String> getDropDownOptionsFor(String fieldName) {
        List<String> strOptions;
        Actions action = new Actions(Driver.getDriver());

        switch (fieldName.toUpperCase()) {
            case "STATUS":
                crmSearchApplicationPage.statusDropDown.click();
                strOptions = convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
                waitFor(1);
                action.moveToElement(crmSearchApplicationPage.searchTitle).click().perform();
                waitFor(1);
                return strOptions;

            case "APPLICATION CYCLE":
                crmSearchApplicationPage.applicationCycle.click();
                strOptions = convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
                waitFor(1);
                action.moveToElement(crmSearchApplicationPage.searchTitle).click().perform();
                waitFor(1);
                return strOptions;

            case "PROGRAM":
                crmSearchApplicationPage.program.click();
                strOptions = convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
                waitFor(1);
                action.moveToElement(crmSearchApplicationPage.searchTitle).click().perform();
                waitFor(1);
                return strOptions;

            case "CHANNEL":
                crmSearchApplicationPage.channel.click();
                strOptions = convertToStrList(Driver.getDriver().findElements(By.cssSelector("li[data-value]")));
                waitFor(1);
                action.moveToElement(crmSearchApplicationPage.searchTitle).click().perform();
                waitFor(1);
                return strOptions;

            default:
                throw new RuntimeException("Error. Invalid dropdown field name - " + fieldName);
        }
    }

    private List<String> convertToStrList(List<WebElement> options) {
        List<String> strOptions = new ArrayList<>();
        options.forEach(option -> {
            strOptions.add(option.getText().trim());
        });

        return strOptions;
    }

    private WebElement getSubLink(String menuItem, String subMenuItem) {
        waitFor(2);
        hover(crmDashboardPage.sideNavMenu);
        waitFor(2);
        hover(Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']")));
        Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']/..//span[text()='keyboard_arrow_down']")).click();
        return Driver.getDriver().findElement(By.xpath("//span[text()='" + subMenuItem + "']"));
    }

    public void navigateToLeftMenu(String menuItem) {
        waitFor(2);
        hover(crmDashboardPage.sideNavMenu);
        waitFor(2);
        hover(Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']")));
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']")).click();
    }

    public void navigateToLeftMenu(String menuItem, String subMenuItem) {
        waitFor(2);
      //  hover(crmDashboardPage.sideNavMenu);
        waitFor(2);
        hover(Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']")));
        Driver.getDriver().findElement(By.xpath("//span[text()='" + menuItem + "']/..//span[text()='keyboard_arrow_down']")).click();
        Driver.getDriver().findElement(By.xpath("//span[text()='" + subMenuItem + "']")).click();
        waitFor(1);
        Actions action = new Actions(Driver.getDriver());
        action.moveByOffset(200, 200).perform();
        waitFor(1);
    }

    public List<WebElement> getApplicationIdLinks(String applicationId) {
        List<WebElement> applicationIdLinks = Driver.getDriver().findElements(By.xpath("//td[text()='" + applicationId + "']"));
        return applicationIdLinks;
    }

    @And("I enter created Application ID in the search menu")
    public void iEnterCreatedApplicationIDInTheSearchMenu() {
        if (!saveSubmit.applicationIdAPI.get().equals("")) {
            System.out.println("App id from API create application to put as search parameter: " + saveSubmit.applicationIdAPI.get());
            apiApplicationId.set(saveSubmit.applicationIdAPI.get());
            crmSearchApplicationPage.applicationID.sendKeys(apiApplicationId.get());
        } else {
            System.out.println("App id from UI application passing as search parameter: " + crmCreateApplicationStepDef.get().applicationId);
            applicationId.set(crmCreateApplicationStepDef.get().applicationId);
            crmSearchApplicationPage.applicationID.sendKeys(applicationId.get());
        }
    }

    @And("I select on the hyperlink result from the created application Id result")
    public void iSelectOnTheHyperlinkResultFromTheCreatedApplicationIdResult() {
        if (apiApplicationId.get() != null) {
            List<WebElement> applicationIdLinks = getApplicationIdLinks(apiApplicationId.get());
            applicationIdLinks.get(0).click();
        } else {
            List<WebElement> applicationIdLinks = getApplicationIdLinks(crmCreateApplicationStepDef.get().applicationId);
            applicationIdLinks.get(0).click();
        }

    }

    @And("I navigate to application tab page")
    public void iNavigateToApplicationTabPage() {
        waitFor(2);
        crmSearchApplicationPage.applicationTab.click();
        waitFor(2);
    }

    @When("I enter created application Id in the search application page Search menu")
    public void i_enter_created_application_Id_in_the_search_application_page_Search_menu() {
        BrowserUtils.waitFor(2);
        if (saveSubmit.applicationId == null) {
            crmSearchApplicationPage.applicationID.sendKeys(crmCreateApplicationStepDef.get().applicationId);
        } else
            crmSearchApplicationPage.applicationID.sendKeys(saveSubmit.applicationId.get());
    }

    @When("I click on first APPLICATION ID from created application")
    public void i_click_on_first_APPLICATION_ID_from_created_application() {
        List<WebElement> applicationIdLinks = Driver.getDriver().findElements(By.xpath("//td[text()='" + saveSubmit.applicationId + "']"));
        applicationIdLinks.get(0).click();
    }

    @And("I enter inbound correspondence created application id in the application search page")
    public void iEnterInboundCorrespondenceCreatedApplicationIdInTheApplicationSearchPage() {
        BrowserUtils.waitFor(2);
        crmSearchApplicationPage.applicationID.sendKeys(inbCorrApplication.get().inbCreatedAppId.get());
    }

    @And("I click on the inbound correspondence search Id found in the result")
    public void iClickOnTheInboundCorrespondenceSearchIdFoundInTheResult() {
        List<WebElement> applicationIdLinks = getApplicationIdLinks(inbCorrApplication.get().inbCreatedAppId.get());
        applicationIdLinks.get(0).click();
        System.out.println("applicationIdLinks = " + applicationIdLinks);
    }

    @Then("I verify that latest application is listed as first")
    public void i_verify_that_latest_application_is_listed_as_first() {
        int topId = 0;
        int nextId = 0;
        for (int i = 0; i <= crmSearchApplicationPage.applicationIdColumn.size() - 1; i++) {
            topId = Integer.parseInt((crmSearchApplicationPage.applicationIdColumn.get(i).getText()));
            System.out.println(topId);
            if (i == crmSearchApplicationPage.applicationIdColumn.size() - 1) {
                continue;
            } else {
                nextId = Integer.parseInt((crmSearchApplicationPage.applicationIdColumn.get(i + 1).getText()));
                System.out.println("Next below id:" + nextId);
                if ((i + 1) == crmSearchApplicationPage.applicationIdColumn.size() - 1)
                    break;
            }
        }
        assertTrue(topId > nextId);
    }

    @Then("I verify Warning text displayed for {string} field")
    public void iVerifyWarningTextDisplayedForField(String field) {
        WebElement warningMessage = null;
        switch (field.toUpperCase()) {
            case "EXTERNAL APPLICATION ID":
                warningMessage = crmSearchApplicationPage.warningExternalAppId;
        }
        System.out.println(warningMessage.getText());
        waitForVisibility(warningMessage, 5);
        Assert.assertTrue(warningMessage.isDisplayed(), "Warning message did not display for " + field);
    }

    @And("I entered created External Application ID to search field")
    public void iEnteredCreatedExternalApplicationIDToSearchField() {
        System.out.println("Searching with " + externalAppID + " External Application ID");
        sendKeyToTextField(crmSearchApplicationPage.externalAppIdInput, externalAppID.get());
    }

    @Then("I verify the search result {string} field matches with the search parameter")
    public void iVerifyTheSearchResultFieldMatchesWithTheSearchParameter(String searchField) {
        switch (searchField.toUpperCase()) {
            case "EXTERNAL APPLICATION ID":
                assertEquals(crmSearchApplicationPage.resultFirstExternalAppID.getText(), externalAppID);
                break;
            case "CASE ID":
                List<Map<String, String>> actualResultInCurrentPagination = getSearchResultsInCurrentPagination();

                for (Map<String, String> eachList : actualResultInCurrentPagination) {
                    for (String eachValue : eachList.values()) {
                        allActual.get().add(eachValue);
                    }
                }
                allActual.get().removeAll(Arrays.asList(crmSearchApplicationPage.resultFirstApplicationCode.getText(),crmSearchApplicationPage.resultFirstApplicationReceivedDate.getText(),crmSearchApplicationPage.resultFirstApplicationDeadlineDate.getText(),crmSearchApplicationPage.resultFirstExternalAppID.getText(),"Determining", "New", "CHIP", "Web"));
                allExpected.get().addAll(Arrays.asList(piFirstName.get(), piLastName.get(), convertyyyyMMddToMMddyyyy(piDOB.get()), applicationIdAPI.get(), applicationIdAPI.get(), amFirstName.get(), amLastName.get(), convertyyyyMMddToMMddyyyy(amDOB.get())));
                sort(allActual.get());
                sort(allExpected.get());
                assertEquals(allActual.get(), allExpected.get(), "Mis match in search values returned for Case Id search");
                break;
            case "APPLICATION CODE":
                assertEquals(crmSearchApplicationPage.resultFirstApplicationCode.getText(), applicationCodeAPI);
                break;
            case "APPLICATION RECEIVED DATE":
                assertTrue(isDateBetween(crmSearchApplicationPage.resultFirstApplicationReceivedDate.getText(), receivedFrom.get(), receivedTo.get()));
                break;
            default:
                fail("Entered searchField did not match case value");
        }
    }

    @And("I enter the {string} Application ID from the multiple API created for duplicate applications and click on the retrived HyperLink")
    public void iEnterTheApplicationIDFromTheMultipleAPICreatedForDuplicateApplicationsAndClickOnTheRetrivedHyperLink(String appOrderFromList) {
        switch (appOrderFromList) {
            case "LAST":
                System.out.println("Entering App Id: " + saveSubmit.getMemberMatchingAppIdForUI().get(saveSubmit.getMemberMatchingAppIdForUI().size() - 1));
                crmSearchApplicationPage.applicationID.sendKeys(saveSubmit.getMemberMatchingAppIdForUI().get(saveSubmit.getMemberMatchingAppIdForUI().size() - 1));
                crmSearchApplicationPage.searchBtn.click();
                List<WebElement> applicationIdLinks = getApplicationIdLinks(saveSubmit.getMemberMatchingAppIdForUI().get(saveSubmit.getMemberMatchingAppIdForUI().size() - 1));
                applicationIdLinks.get(0).click();
                break;
            case "FIRST":
                System.out.println("Entering App Id: " + saveSubmit.getMemberMatchingAppIdForUI().get(0));
                crmSearchApplicationPage.applicationID.sendKeys(saveSubmit.getMemberMatchingAppIdForUI().get(0));
                crmSearchApplicationPage.searchBtn.click();
                List<WebElement> appIdLastLinks = getApplicationIdLinks(saveSubmit.getMemberMatchingAppIdForUI().get(0));
                appIdLastLinks.get(0).click();
                break;
            case "SECOND":
                System.out.println("Entering App Id: " + saveSubmit.getMemberMatchingAppIdForUI().get(1));
                crmSearchApplicationPage.applicationID.sendKeys(saveSubmit.getMemberMatchingAppIdForUI().get(1));
                crmSearchApplicationPage.searchBtn.click();
                List<WebElement> appIdSecond = getApplicationIdLinks(saveSubmit.getMemberMatchingAppIdForUI().get(1));
                appIdSecond.get(0).click();
                break;
            default:
                fail("Provided order did not match");
        }
        waitFor(3);
    }

    @Then("I verify the new search fields CASE ID and CASE TYPE is present")
    public void iVerifyTheNewSearchFieldsCASEIDAndCASETYPEIsPresent() {
        Assert.assertTrue(crmSearchApplicationPage.caseIdLabel.isDisplayed(), "Case Id field label is not displayed");
        Assert.assertTrue(crmSearchApplicationPage.caseTypeLabel.isDisplayed(), "Case Type field label is not displayed");

    }

    @Then("I verify the {string} field value is {string}")
    public void iVerifyTheFieldValueIs(String fieldType, String value) {
        switch (fieldType) {
            case "CASE TYPE":
                assertEquals(crmSearchApplicationPage.caseTypeInput.getAttribute("value"), value, "Mismatch in expected CASE TYPE dropdown value");
                break;
            default:
                fail("Entered searchField did not match case value");
        }

    }

    @And("I click on first APPLICATION ID from search Results")
    public void iClickOnFirstAPPLICATIONIDFromSearchResults() {
        waitFor(2);
        crmSearchApplicationPage.resultFirstApplicationID.click();
    }

    @Then("I select item count in search result page as {string}")
    public void i_select_Item_count_in_search_result_page_as(String count) {
        waitFor(3);
        scrollToElement(savedTaskSrchPage.pageNationDD);
        savedTaskSrchPage.pageNationDD.click();
        crmSearchApplicationPage.itemCountList.stream().filter(x -> x.getText().equalsIgnoreCase(count)).findFirst().ifPresent(WebElement::click);
    }

    @And("I see Warning pop-up message not displayed when recods less then 200")
    public void i_see_Warning_pop_up_message_not_displayed() {
        assertFalse(isElementDisplayed(crmSearchApplicationPage.warningMsg), "Warning Message is Displaying");
    }

    @Then("I verify {string} 200 records on Application Search page")
    public void verifyApplicationSearchPageDisplayedLess_MoreThan200Records(String redordSize) {
        waitFor(1);
        int size = 1, sum = 0, rowSize;
        int recordsize = 200;
        if (savedTaskSrchPage.lnkArrowForward.isDisplayed()) {
            savedTaskSrchPage.lnkArrowForward.click();
            waitFor(1);
            size = Integer.parseInt(savedTaskSrchPage.lnkPageNations.get(savedTaskSrchPage.lnkPageNations.size() - 1).getText());
            savedTaskSrchPage.lnkArrowBack.click();
            waitFor(1);

            for (int i = 1; i <= size; i++) {
                if (size <= 20 && crmSearchApplicationPage.applicationIdColumn.size() <= 10) {
                    rowSize = crmSearchApplicationPage.applicationIdColumn.size();
                    sum = sum + rowSize;
                }
                if (i != size) {
                    Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")).click();
                    waitFor(1);
                }
            }
            System.out.println("Sum-> " + sum);
        }
        if (redordSize == "equal")
            assertEquals(sum, recordsize, "200 record is not displaying");
        if (redordSize == "lessThan")
            assertTrue(sum < recordsize, "200 record is displaying");

    }

    @Then("I verify error displayed under app received date from field")
    public void iVerifyErrorDisplayedUnderAppReceivedDateFromField() {
        waitFor(1);
        assertTrue(crmSearchApplicationPage.errorForAppReceivedDateFrom.isDisplayed(), "Error is not displayed for App Received Date From > App Received Date To");
    }

    @Then("I verify error displayed under app received date to field")
    public void iVerifyErrorDisplayedUnderAppReceivedDateToField() {
        waitFor(1);
        assertTrue(crmSearchApplicationPage.errorForAppReceivedDateTo.isDisplayed(), "Error is not displayed for App Received Date From > App Received Date To");
    }

    @And("I verify previously selected row is highlighted")
    public void highlightPreviousSelectedRow() {
        waitFor(1);
        assertTrue(crmSearchApplicationPage.resultFirstRow.getAttribute("class").contains("mx-table-row-highlight"), "Row not highlighted");
    }
}
