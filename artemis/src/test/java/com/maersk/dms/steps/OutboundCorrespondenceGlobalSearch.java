package com.maersk.dms.steps;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APIATSCorrespondenceController;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.OutboundCorrespondenceSearchPage;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import com.maersk.dms.step_definitions.OutboundCorrespondenceGlobalSearchStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceGlobalSearch extends CRMUtilities implements ApiBase {
    private CRMDashboardPage dashboard = new CRMDashboardPage();
    private OutboundCorrespondenceSearchPage searchPage = new OutboundCorrespondenceSearchPage();
    private CRMContactRecordUIPage init = new CRMContactRecordUIPage();
    public Actions action = new Actions(Driver.getDriver());
    private ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private final ThreadLocal<String> actualErrorMessage = ThreadLocal.withInitial(()->"");
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<OutboundCorrespondenceGlobalSearchStepDefs> obcorrglobalsearch = ThreadLocal.withInitial(OutboundCorrespondenceGlobalSearchStepDefs::new);
    private final ThreadLocal<JsonObject> request =ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    private final ThreadLocal<String> expectedCorID = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> expectedSearchListCorID = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> expectedPage = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> currentWindowHandle = ThreadLocal.withInitial(String::new);
    APIATSCorrespondenceController apiatsCorrespondenceController = new APIATSCorrespondenceController();
    OutboundCorrespondenceGlobalSearchStepDefs outboundCorrespondenceGlobalSearchStepDefs = new OutboundCorrespondenceGlobalSearchStepDefs();
//    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    @When("I click on the main navigation menu")
    public void iClickOnTheMainNavigationMenu() {
        try {
            dashboard.sideNavMenu = Driver.getDriver().findElement(By.xpath("//div[@class='mx-layout-left-drawer-tm mx-sidebar-menu']"));
            hover(dashboard.sideNavMenu);
//            waitFor(30);
            waitForClickablility(dashboard.expandCorrespondenceSideTab, 60);
            hover(dashboard.expandCorrespondenceSideTab);
            waitForClickablility(dashboard.expandCorrespondenceSideTab, 60);
            dashboard.expandCorrespondenceSideTab.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I should see the Outbound Correspondence Search Icon")
    public void iShouldSeeTheOutboundCorrespondenceSearchIcon() {
        Assert.assertTrue(dashboard.correspondenceMainNavigationButton.isDisplayed());
    }

    @Then("I should NOT see the Outbound Correspondence Search Icon")
    public void verifyOutboundSearchIsHidden() {
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(dashboard.outboundCorrespondenceMainNavigationButton));
    }

    @When("I click on the Outbound Correspondence Search icon")
    public void iClickOnTheOutboundCorrespondenceSearchIcon() {
        boolean successful = false;
        try {
//            waitForClickablility(dashboard.outboundCorrespondenceMainNavigationButton, 60);
            waitForClickablility(dashboard.sideMenuCorrespondenceArrowUpButton, 60);
            dashboard.outboundCorrespondenceMainNavigationButton.click();
            waitForPageToLoad(60);
            Driver.getDriver().findElement(By.xpath("//body")).click();
            waitFor(10);
            successful = dashboard.outboundCorrespondenceGlobalSearchHeader.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!successful) {
            new OutboundCorrespondenceGlobalSearch().iClickOnTheMainNavigationMenu();
            clickOutboundSearch();
        }
        Assert.assertTrue(dashboard.outboundCorrespondenceGlobalSearchHeader.isDisplayed());
    }

    public void clickOutboundSearch() {
        try {
            waitForClickablility(dashboard.sideMenuCorrespondenceArrowUpButton, 60);
//            waitForClickablility(dashboard.outboundCorrespondenceMainNavigationButton, 5);
            dashboard.outboundCorrespondenceMainNavigationButton.click();
            waitForPageToLoad(60);
            Driver.getDriver().findElement(By.xpath("//body")).click();
            waitFor(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I should be navigated to the Outbound Correspondence Global Search Page")
    public void iShouldBeNavigatedToTheOutboundCorrespondenceGlobalSearchPage() {
        waitFor(2);
        Assert.assertTrue(dashboard.outboundCorrespondenceGlobalSearchHeader.isDisplayed());
    }

    @And("I enter correspondence ID to search for Outbound Correspondence")
    public void iEnterCorrespondenceIDToSearchForOutboundCorrespondence(String corrID) {
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        searchPage.correspondenceId.click();
        waitFor(1);
        if ("previouslyCreated".equalsIgnoreCase(corrID)) {
            corrID = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        } else if ("outboundCorrId".equalsIgnoreCase(corrID)) {
            corrID = World.save.get().get("outboundCorrId");
        } else if ("randomCorrespondence".equalsIgnoreCase(corrID)) {
            corrID = getWorld().outboundCorrespondenceBean.get().correspondenceId;
        }
        searchPage.correspondenceId.sendKeys(corrID);
        waitFor(2);
        searchPage.outboundSearchButton.click();
        waitFor(1);
        Assert.assertTrue(searchPage.outboundSearchButton.isEnabled());
        searchPage.outboundSearchButton.click();
        waitFor(5);
    }

    @When("I select the show details caret for an Outbound Correspondence")
    public void iSelectTheShowDetailsCaretForAnOutboundCorrespondence() {
        Assert.assertTrue(searchPage.carrotIcon.isDisplayed(), "Unable to find carrot icon");
        searchPage.carrotIcon.click();
        waitFor(5);
    }

    @Then("I should see fields with correct value:")
    public void i_should_see_fields_with_correct_value(List<String> dataTable) {
        List<String> fieldList = new ArrayList<>();
        fieldList.add(searchPage.createdOnLabel.getText());
        fieldList.add(searchPage.createdByLabel.getText());
        fieldList.add(searchPage.recipientLabel.getText());
        fieldList.add(searchPage.statusReasonLabel.getText());
        List<String> expectedList = new ArrayList<>(dataTable);
        Collections.sort(expectedList);
        Collections.sort(fieldList);
        Assert.assertEquals(fieldList, expectedList, "Field list: " + fieldList + " Expected list: " + expectedList);
    }

    @And("I should see correct values for the fields")
    public void iShouldSeeCorrectValuesForTheFields(List<String> dataTable) {
        List<String> fieldvalue = new ArrayList<>();
        fieldvalue.add(searchPage.createdonValue.getText());
        fieldvalue.add(searchPage.createdByValue.getText());
        fieldvalue.add(searchPage.recipientValue.getText());
        fieldvalue.add(searchPage.statusReasonValue.getText());
        List<String> expectedList = new ArrayList<>(dataTable);
        expectedList.add(1, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDateTimeNow());
        Collections.sort(expectedList);
        Collections.sort(fieldvalue);
        Assert.assertEquals(fieldvalue, expectedList, "Field list: " + fieldvalue + " Expected list: " + expectedList);
    }

    @Then("I should see in the Notification field with following fields")
    public void iShouldSeeInTheNotificationFieldWithFollowingFields(List<String> dataTable) {
        ArrayList<String> notificationFieldList = new ArrayList<>();
        notificationFieldList.add(searchPage.detailChannelLabel.getText());
        notificationFieldList.add(searchPage.detailDestinationLabel.getText());
        notificationFieldList.add(searchPage.detailLanguageLabel.getText());
        notificationFieldList.add(searchPage.detailStatusLabel.getText());
        notificationFieldList.add(searchPage.detailStatusDateLabel.getText());
        notificationFieldList.add(searchPage.detailNIDLabel.getText());
        Assert.assertEquals(dataTable, notificationFieldList, "Expected Notification fields: " + dataTable + " Actual Notification Fields: " + notificationFieldList);
    }

    @Then("I should see {string} {string} {string} {string} have appropriate Destination values listed")
    public void iShouldSeeHaveAppropriateDestinationValuesListed(String mail, String email, String text, String fax) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        if ("previouslyCreated".equalsIgnoreCase(mail)) {
            mail = response.getString("recipients[0].recipientInfo.streetAddress") + "," + response.getString("recipients[0].recipientInfo.streetAddionalLine1") + "," + response.getString("recipients[0].recipientInfo.city") + "," + response.getString("recipients[0].recipientInfo.state") + "," + response.getString("recipients[0].recipientInfo.zipcode");
        }
        if ("previouslyCreated".equalsIgnoreCase(email)) {
            email = response.getString("recipients[0].recipientInfo.emailAddress");
        }
        if ("previouslyCreated".equalsIgnoreCase(text)) {
            text = response.getString("recipients[0].recipientInfo.textNumber");
        }
        if ("previouslyCreated".equalsIgnoreCase(fax)) {
            fax = response.getString("recipients[0].recipientInfo.faxNumber");
        }
        ArrayList<String> destinationValue = new ArrayList<>();
        destinationValue.add(searchPage.getWebElementContaining(mail).getText());
        destinationValue.add(searchPage.getWebElementContaining(email).getText());
        destinationValue.add(searchPage.getWebElementContaining(text).getText());
        destinationValue.add(searchPage.getWebElementContaining(fax).getText());
        ArrayList<String> expectedDestValue = new ArrayList<>();
        expectedDestValue.add(mail);
        expectedDestValue.add(email);
        expectedDestValue.add(text);
        expectedDestValue.add(fax);
        Collections.sort(destinationValue);
        Collections.sort(expectedDestValue);
        Assert.assertEquals(expectedDestValue, destinationValue, "Expected Destination values: " + expectedDestValue + " Actual Destination values: " + destinationValue);
    }

    @Then("I should see {string} {string} {string} {string} have appropriate Status values listed")
    public void iShouldSeeHaveAppropriateStatusValuesListed(String mail, String email, String text, String fax) {
        ArrayList<String> expectedStatusValue = new ArrayList<>();
        expectedStatusValue.add(mail);
        expectedStatusValue.add(email);
        expectedStatusValue.add(text);
        expectedStatusValue.add(fax);
        ArrayList<String> statusValue = new ArrayList<>();
        statusValue.add(searchPage.detailMailStaValue.getText());
        statusValue.add(searchPage.detailEmailStaValue.getText());
        statusValue.add(searchPage.detailTextStaValue.getText());
        statusValue.add(searchPage.detailFaxStaValue.getText());
        Collections.sort(expectedStatusValue);
        Collections.sort(statusValue);
        Assert.assertEquals(expectedStatusValue, statusValue, "Expected Status values: " + expectedStatusValue + " Actual Status values: " + statusValue);
    }

    @When("I hover my mouse over the red triangle error icon")
    public void iHoverMyMouseOverTheRedTriangleErrorIcon() {
        Assert.assertTrue(searchPage.errorTooltip.isDisplayed());
        scrollToElement(searchPage.errorTooltip);
        action.moveToElement(searchPage.errorTooltip).build().perform();
        waitFor(2);
        actualErrorMessage.set(searchPage.hoverErrorMessage.getText());
    }

    @Then("I should see the status reason text {string} for the error")
    public void iShouldSeeTheStatusReasonTextForTheError(String message) {
        System.out.println(message);
        Assert.assertEquals(actualErrorMessage.get(), message, "Actual Error message: " + actualErrorMessage + " Expected error message: " + message);
    }

    @Then("I verify Notification details contains view Icon")
    public void iVerifyNotificationDeatilsContainsViewIcon() {
        Assert.assertTrue(searchPage.mailHyperLink.isDisplayed());
    }

    @Then("I should see {string} {string} {string} {string} have appropriate Notification detail values listed")
    public void iShouldSeeHaveAppropriateNotificationDetailValuesListed(String createdON, String createdBy, String statusReason, String recipients) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get())));
        ArrayList<String> expectedStatusValue = new ArrayList<>();
        if ("previouslyCreated".equalsIgnoreCase(createdON)) {
            createdON = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateTimeToDateTimeWith12HrClock(response.getString("createdDatetime")).substring(0, 10);
        }
        if ("previouslyCreated".equalsIgnoreCase(createdBy)) {
            createdBy = response.getString("createdBy");
        }
        if ("previouslyCreated".equalsIgnoreCase(statusReason)) {
            statusReason = response.getString("recipients[0].notifications[0].notificationStatus.statusReason");
        }
        if ("previouslyCreated".equalsIgnoreCase(recipients)) {
            recipients = response.getString("recipients[0].recipientInfo.firstName") + " " + response.getString("recipients[0].recipientInfo.middleName") + " " + response.getString("recipients[0].recipientInfo.lastName");
        }
        expectedStatusValue.add(createdON);
        expectedStatusValue.add(createdBy);
        expectedStatusValue.add(statusReason);
        expectedStatusValue.add(recipients);
        ArrayList<String> statusValue = new ArrayList<>();
        statusValue.add(searchPage.getWebElementContaining(createdON).getText().trim().substring(0, 10));
        statusValue.add(searchPage.getWebElementContaining(createdBy).getText().trim());
        statusValue.add(searchPage.getWebElementContaining(statusReason).getText().trim());
        statusValue.add(searchPage.getWebElementContaining(recipients).getText().trim());
        Collections.sort(expectedStatusValue);
        Collections.sort(statusValue);
        Assert.assertEquals(expectedStatusValue, statusValue, "Expected Status values: " + expectedStatusValue + " Actual Status values: " + statusValue);
    }

    @Then("I should see the Outbound Correspondence CID is displayed as a hyperlink")
    public void iShouldSeeTheOutboundCorrespondenceCIDIsDisplayedAsAHyperlink() {
        try {
            waitForVisibility(searchPage.cidColumnOutbSearch.get(0), 10);
            Assert.assertTrue(searchPage.cidColumnOutbSearch.get(0).isDisplayed());
        } catch (Exception e) {
            Assert.fail("NO SEARCH RESULTS");
        }
    }

    @When("I click on the {string} Outbound Correspondence")
    public void iClickOnTheOutboundCorrespondence(String document) {
        try {
            waitForVisibility(searchPage.cidColumnOutbSearch.get(0), 10);
        } catch (Exception e) {
            Assert.fail("NO SEARCH RESULTS");
        }
        switch (document.toUpperCase()) {
            case "FIRST AVAILABLE":
                searchPage.cidColumnOutbSearch.get(0).click();
                break;
            case "PREVIOUSLYCREATED":
                Driver.getDriver().findElement(By.xpath("//h5[contains(text(),' SEARCH RESULT')]/following::table/tbody/tr/td[contains(text(),'" + World.generalSave.get().get("nid").toString() + "')]")).click();
                break;
            case "FIRSTPAGE":
                List<String> beforePage = getElementsText(searchPage.cidColumnOutbSearch);
                World.generalSave.get().put("OutboundSearchResults", beforePage);
                int randomNumber = RandomUtils.nextInt(1,searchPage.cidColumnOutbSearch.size());
                World.generalSave.get().put("CIDOutbCorrespondence", searchPage.cidColumnOutbSearch.get(randomNumber));
                searchPage.cidColumnOutbSearch.get(randomNumber).click();
                break;
            case "RANDOMPAGE":
                int randomPage = RandomUtils.nextInt(1,5);
                Driver.getDriver().findElement(By.xpath("//a[starts-with(@aria-label,'Go to page number "+randomPage+"')]")).click();
                waitForPageToLoad(10);
                waitFor(5);
                int randomNumber1 = RandomUtils.nextInt(1,searchPage.cidColumnOutbSearch.size());
                List<String> beforePage2 = getElementsText(searchPage.cidColumnOutbSearch);
                World.generalSave.get().put("OutboundSearchResults", beforePage2);
                World.generalSave.get().put("CIDOutbCorrespondence", searchPage.cidColumnOutbSearch.get(randomNumber1));
                searchPage.cidColumnOutbSearch.get(randomNumber1).click();
                break;
        }
    }

    @Then("I should see the details page for the Outbound Correspondence")
    public void iShouldSeeTheDetailsPageForTheOutboundCorrespondence() {
        waitForVisibility(viewOutboundCorrespondenceDetailsPage.CIDByEditButton, 10);
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.viewOutboundCorrespondenceDetailsHeader.isDisplayed());
    }

    @And("I click on the navigate back from Outbound Correspondence Details")
    public void iClickOnTheNavigateBackFromOutboundCorrespondenceDetails() {
        waitForPageToLoad(10);
        searchPage.navigateBackwards.click();
        waitFor(10);
    }

    @Then("I should see the same Outbound Correspondence Search results as before")
    public void iShouldSeeTheSameOutboundCorrespondenceSearchResultsAsBefore() {
        List<String> before = (List<String>) World.generalSave.get().get("OutboundSearchResults");
        List<String> after = getElementsText(searchPage.cidColumnOutbSearch);
        Assert.assertEquals(before.size(),after.size());
        Assert.assertEquals(before, after);
    }

    @And("I search Outbound Correspondence by the following values")
    public void iSearchOutboundCorrespondenceByTheFollowingValues(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            fillInSearchCriteria(searchCriteria, dataTable.get(searchCriteria));
        }
        waitFor(3);
        searchPage.outboundSearchButton.click();
        waitFor(2);
        try {
            waitForVisibility(searchPage.cidColumnOutbSearch.get(0), 15);
        } catch (Exception exception) {
            org.junit.Assert.fail("No results");

        }
    }

    private void fillInSearchCriteria(String searchCriteria, String searchValue) {
        switch (searchCriteria.toUpperCase()) {
            case "CASEID":
                searchPage.caseId.click();
                waitFor(1);
                searchPage.caseId.sendKeys(searchValue);
                waitFor(2);
                break;
            case "NOTIFICATIONID":
                searchPage.notificationId.click();
                waitFor(1);
                searchPage.notificationId.sendKeys(searchValue);
                waitFor(2);
                break;
            case "CORRESPONDENCEID":
                if ("previouslyCreated".equalsIgnoreCase(searchValue)) {
                    searchValue = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
                }
                searchPage.correspondenceId.click();
                waitFor(1);
                searchPage.correspondenceId.sendKeys(searchValue);
                waitFor(2);
                break;
            case "REGARDINGCONSUMERID":
                searchPage.regardingConsumerId.click();
                waitFor(1);
                searchPage.regardingConsumerId.sendKeys(searchValue);
                waitFor(2);
                break;
            case "RECIPIENTCONSUMERID":
                searchPage.recipientConsumerId.click();
                waitFor(1);
                searchPage.recipientConsumerId.sendKeys(searchValue);
                waitFor(2);
                break;
        }
    }

    @And("I should see that I am navigated to the Outbound Correspondence Details page of {string}")
    public void iShouldSeeThatIAmNavigatedToTheOutboundCorrespondenceDetailsPageOf(String cid) {
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        }
        waitForPageToLoad(15);
        BrowserUtils.waitFor(5);
        Assert.assertTrue(searchPage.cidOutbDetails.getText().contains(cid));
    }

    // global search part2 @keerthi
    @When("I validate {string} field label and maxlength")
    public void ivalidatefieldlabelandmaxlength(String fieldname) {

        String actualfieldname, actdate, actdateformat;
        int actualfieldlen;
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        switch (fieldname.toUpperCase()) {
            case "CORRESPONDENCE ID":
                actualfieldname = searchPage.correspondenceIdfieldlabel.getText();
                searchPage.correspondenceId.click();
                searchPage.correspondenceId.sendKeys("12345678901");
                waitFor(1);
                actualfieldlen = searchPage.correspondenceId.getAttribute("value").length();
                Assert.assertEquals(actualfieldname, "CORRESPONDENCE ID");
                Assert.assertEquals(actualfieldlen, 10);
                break;
            case "NOTIFICATION ID":
                actualfieldname = searchPage.notificationIdfieldlabel.getText();
                searchPage.notificationId.click();
                searchPage.notificationId.sendKeys("12345678901");
                waitFor(1);
                actualfieldlen = searchPage.notificationId.getAttribute("value").length();
                Assert.assertEquals(actualfieldname, "NOTIFICATION ID");
                Assert.assertEquals(actualfieldlen, 10);
                break;
            case "CASE ID":
                actualfieldname = searchPage.caseIdfieldlabel.getText();
                searchPage.caseId.click();
                searchPage.caseId.sendKeys("12345678901");
                waitFor(1);
                actualfieldlen = searchPage.caseId.getAttribute("value").length();
                Assert.assertEquals(actualfieldname, "CASE ID");
                Assert.assertEquals(actualfieldlen, 10);
                break;
            case "REGARDING CONSUMER ID":
                actualfieldname = searchPage.regardingconsumerIdfieldlabel.getText();
                searchPage.regardingConsumerId.click();
                searchPage.regardingConsumerId.sendKeys("12345678901");
                waitFor(1);
                actualfieldlen = searchPage.regardingConsumerId.getAttribute("value").length();
                Assert.assertEquals(actualfieldname, "REGARDING CONSUMER ID");
                Assert.assertEquals(actualfieldlen, 10);
                break;
            case "RECIPIENT CONSUMER ID":
                actualfieldname = searchPage.recipientconsumerIdfieldlabel.getText();
                searchPage.recipientConsumerId.click();
                searchPage.recipientConsumerId.sendKeys("12345678901");
                waitFor(1);
                actualfieldlen = searchPage.recipientConsumerId.getAttribute("value").length();
                Assert.assertEquals(actualfieldname, "RECIPIENT CONSUMER ID");
                Assert.assertEquals(actualfieldlen, 10);
                break;
            case "REQUEST DATE":
                actualfieldname = searchPage.requestdatefieldlabel.getText();
                actdateformat = searchPage.requestdate.getAttribute("placeholder");
                searchPage.requestdate.click();
                searchPage.requestdate.sendKeys("09/21/2021");
                waitFor(1);
                actdate = searchPage.requestdate.getAttribute("value");
                Assert.assertEquals(actualfieldname, "REQUEST DATE");
                Assert.assertEquals(actdateformat, "MM/DD/YYYY");
                Assert.assertEquals(actdate, "09/21/2021");
                break;
            case "SENT DATE":
                actualfieldname = searchPage.sentdatefieldlabel.getText();
                actdateformat = searchPage.sentdate.getAttribute("placeholder");
                searchPage.sentdate.click();
                searchPage.sentdate.sendKeys("09/21/2021");
                waitFor(1);
                actdate = searchPage.sentdate.getAttribute("value");
                Assert.assertEquals(actualfieldname, "SENT DATE");
                Assert.assertEquals(actdateformat, "MM/DD/YYYY");
                Assert.assertEquals(actdate, "09/21/2021");
                break;

        }
    }

    @When("validate Outbound Request date default operator value is {string}")
    public void ivalidaterequestdatedefaultoperator(String operator) {
        Assert.assertEquals(searchPage.requestdatedefaultoperator.getText(), operator);
    }

    @When("I validate Outbound Request date disabling feature")
    public void ivalidaterequestdatedisablefeature() {
        searchPage.sentdate.click();
        searchPage.sentdate.sendKeys("09/21/2021");
        String actstatus = searchPage.requestdate.getAttribute("disabled");
        Assert.assertEquals(actstatus, "true");

    }

    @When("I validate Outbound Sent date disabling feature")
    public void ivalidatesentdatedisablefeature() {
        searchPage.requestdate.click();
        searchPage.requestdate.sendKeys("09/21/2021");
        String actstatus = searchPage.sentdate.getAttribute("disabled");
        Assert.assertEquals(actstatus, "true");

    }

    @When("validate Outbound Sent date default operator value is {string}")
    public void ivalidatesentdatedefaultoperator(String operator) {
        Assert.assertEquals(searchPage.sentdatedefaultoperator.getText(), operator);
    }

    @When("I click on cancel button in Outbound search page")
    public void iclickoncancelbutton() {
        searchPage.cancel.click();
        waitFor(1);
    }

    @When("I validate {string} dropdown values")
    public void ivalidatedatedropdowns(String fieldname, List<String> dropdownoptions) {
        List<String> actual = new ArrayList<>();
        List<String> expected_sortedList = dropdownoptions.stream().sorted().collect(Collectors.toList());
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        switch (fieldname.toUpperCase()) {
            case "REQUEST DATE":
                searchPage.requestdatedefaultoperator.click();
                waitFor(2);
                break;
            case "SENT DATE":
                searchPage.sentdatedefaultoperator.click();
                waitFor(2);
                break;
            case "CORRESPONDENCE STATUS":
                searchPage.correspondenceStatusdrpdown.click();
                waitFor(2);
                break;
            case "CORRESPONDENCE STATUS REASON":
                searchPage.obglobalsearchcorrstatusreasondropdown.click();
                waitFor(2);
                break;
            case "NOTIFICATION STATUS":
                searchPage.notificationStatusDropdown.click();
                waitFor(2);
                break;
            case "NOTIFICATION STATUS REASON":
                searchPage.obglobalsearchnotificationstatusreasondropdown.click();
                waitFor(2);
                break;
            default:
                Assert.fail("Name of field from feature file does not match up to any case ");
        }

        searchPage.DropDownOptions.stream().forEach((k) -> {
            actual.add(k.getText());
        });
        List<String> actual_sortedList = actual.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(actual_sortedList, expected_sortedList);

    }

    @When("I search for an Outbound Correspondence by the following values")
    public void outboundSearchUI(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            obcorrglobalsearch.get().fillInSearchCriteria(searchCriteria, dataTable.get(searchCriteria));
            waitFor(2);
            searchPage.outboundSearchButton.click();
            //waitFor(8);
        }
    }

    @When("I search for an Outbound Correspondence by the following values Without Wait")
    public void outboundSearchUINoWait(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            obcorrglobalsearch.get().fillInSearchCriteria(searchCriteria, dataTable.get(searchCriteria));
            waitFor(2);
            searchPage.outboundSearchButton.click();
        }
    }

    @When("I click on the Outbound Correspondence global Search icon")
    public void outboundglobalSearchUI() {
        searchPage.outboundSearchButton.click();
    }

    @When("I validate search limited to two hundred records")
    public void totalrecordscount() {
        int totalrecordscount = obcorrglobalsearch.get().rowcount();
        Assert.assertTrue(totalrecordscount <= 200);
    }

    @Then("I should see that all the outbound correspondence search results have the following values")
    public void verifySearchResults(Map<String, String> dataTable) {
        boolean isOnLastPage = false;
        do {
            obcorrglobalsearch.get().verifyColumnValuesFromResults(dataTable);
            if (obcorrglobalsearch.get().isOnLastPage()) {
                isOnLastPage = true;
            }
            obcorrglobalsearch.get().goToNextPage();
        } while (!isOnLastPage);
        System.out.println("finish");
    }

    @When("I have a request to search Outbound Correspondence for the following values")
    public void iHaveARequestToSearchOutboundCorrespondenceForTheFollowingValues(Map<String, String> searchCriteria) {
        for (String search : searchCriteria.keySet()) {
            switch (search) {
                case "channel":
                    JsonArray channel = new JsonArray();
                    channel.add(searchCriteria.get(search));
                    request.get().add("channel", channel);
                    break;
                case "status":
                    JsonArray status = new JsonArray();
                    status.add(searchCriteria.get(search));
                    request.get().add("correspondenceStatus", status);
                    break;
                case "notificationStatus":
                    JsonArray notificationStatus = new JsonArray();
                    notificationStatus.add(searchCriteria.get(search));
                    request.get().add("notificationStatus", notificationStatus);
                    break;
                case "notificationStatusReason":
                    JsonArray notificationStatusReason = new JsonArray();
                    notificationStatusReason.add(searchCriteria.get(search));
                    request.get().add("notificationStatusReason", notificationStatusReason);
                    break;
                case "correspondenceStatusReason":
                    JsonArray correspondenceStatusReason = new JsonArray();
                    correspondenceStatusReason.add(searchCriteria.get(search));
                    request.get().add("correspondenceStatusReason", correspondenceStatusReason);
                    break;
                case "correspondenceId":
                    String CID;
                    if ("previouslyCreated".equalsIgnoreCase(searchCriteria.get(search)))
                        CID = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
                    else
                        CID = searchCriteria.get(search);
                    request.get().addProperty("correspondenceId", CID);
                    break;
                case "language":
                    JsonArray language = new JsonArray();
                    language.add(searchCriteria.get(search));
                    request.get().add("language", language);
                    break;
                default:
                    request.get().addProperty(search, searchCriteria.get(search));
            }
        }
        synchronized (response) {
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
        }
    }

    @Then("I should see that the results from Outbound Correspondence will only contain the following values")
    public void iShouldSeeThatTheResultsFromOutboundCorrespondenceWillOnlyContainTheFollowingValues(Map<String, String> searchCriteria) {
        JsonObject ids = new JsonParser().parse(response.get().prettyPrint()).getAsJsonObject();
        for (String search : searchCriteria.keySet()) {
            switch (search) {
                case "channel":
                    validateOBNotificationSearchCriteria(searchCriteria, ids, search, "channelType");
                    break;
                case "notificationStatus":
                    validateOBNotificationSearchCriteria(searchCriteria, ids, search, "notificationStatus.status");
                    break;
                case "notificationStatusReason":
                    validateOBNotificationSearchCriteria(searchCriteria, ids, search, "notificationStatus.statusReason");
                    break;
                case "status":
                    validateOBCorrespondenceSearchCriteria(searchCriteria, ids, search, "status");
                    break;
                case "correspondenceStatusReason":
                    validateOBCorrespondenceSearchCriteria(searchCriteria, ids, search, "statusMessage");
                    break;
                case "language":
                    validateOBNotificationSearchCriteria(searchCriteria, ids, search, "language");
                    break;
                case "nameSuffix":
                case "middleName":
                    validateOBRecipientsSearchCriteria(ids, search, searchCriteria.get(search));
                    break;
                default:
                    Assert.fail("no matching cases");

            }
        }

    }

    private void validateOBNotificationSearchCriteria(Map<String, String> searchCriteria, JsonObject ids, String search, String attribute) {
        boolean found = false;
        NEXT_CORRESPONDENCE:
        for (JsonElement correspondence : ids.getAsJsonObject("data").getAsJsonArray("content")) {
            JsonArray recipients = ((JsonObject) correspondence).getAsJsonArray("recipients");
            for (JsonElement recipient : recipients) {
                JsonArray notifications = recipient.getAsJsonObject().getAsJsonArray("notifications");
                //for each correspondence, checking each notification
                for (JsonElement notification : notifications) {
                    if (null == notification.getAsJsonObject().get(attribute)) {
                        continue NEXT_CORRESPONDENCE;
                    } else if (notification.getAsJsonObject().get(attribute).getAsString().equalsIgnoreCase(searchCriteria.get(search))) {
                        found = true;
                        continue NEXT_CORRESPONDENCE;
                    }
                }
            }
            Assert.assertTrue(found);
            found = false;
        }
    }

    private void validateOBRecipientsSearchCriteria(JsonObject ids, String attribute, String attributevalue) {
        for (JsonElement correspondence : ids.getAsJsonObject("data").getAsJsonArray("content")) {
            JsonArray recipients = ((JsonObject) correspondence).getAsJsonArray("recipients");
            for (JsonElement recipient : recipients) {
                Assert.assertEquals(recipient.getAsJsonObject().get("recipientInfo").getAsJsonObject().get(attribute).getAsString(), attributevalue);
            }
        }
    }


    private void validateOBCorrespondenceSearchCriteria(Map<String, String> searchCriteria, JsonObject ids, String search, String attribute) {
        for (JsonElement correspondence : ids.getAsJsonObject("data").getAsJsonArray("content")) {
            String actual = correspondence.getAsJsonObject().get(attribute).getAsString();
            String id = correspondence.getAsJsonObject().get("id").getAsString();
            Assert.assertTrue(searchCriteria.get(search).trim().equalsIgnoreCase(actual.trim()), "correspondence Id - " + id);
        }
    }

    @And("I store from search results")
    public void i_store_from_search_results(List<String> elements) {
        for (String element : elements) {
            switch (element) {
                case "correspondenceId":
                    expectedCorID.set(searchPage.correspondenceId.getAttribute("value"));
                    break;
                case "correspondenceSearchResults":
                    expectedSearchListCorID.set(getElementsText(searchPage.cidColumnOutbSearch));
                    break;
                case "searchPage":
                    expectedPage.set(searchPage.activePage.getText());
                    break;
            }
        }
    }

    @And("I should see on Outbound Correspondence Global Search Page the same")
    public void i_should_see_on_Outbound_Correspondence_Global_Search_Page_the_same(List<String> elements) {
        for (String element : elements) {
            switch (element) {
                case "correspondenceId":
                    Assert.assertEquals(searchPage.correspondenceId.getAttribute("value"), expectedCorID);
                    break;
                case "correspondenceSearchResults":
                    Assert.assertEquals(getElementsText(searchPage.cidColumnOutbSearch), expectedSearchListCorID);
                    break;
                case "searchPage":
                    Assert.assertEquals(searchPage.activePage.getText(), expectedPage);
                    break;
            }
        }
    }

    @And("I search api created outbound correspondence from ATS")
    public void I_search_api_created_outbound_correspondence_from_ATS() {
        System.out.println("ATS outbound correspondence id: " + apiatsCorrespondenceController.createdOutboundCorrespondenceID);
        waitFor(5);
        searchPage.caseId.click();
        waitFor(4);
        searchPage.correspondenceId.click();
        searchPage.correspondenceId.sendKeys(apiatsCorrespondenceController.createdOutboundCorrespondenceID.get());
        waitFor(2);
        searchPage.outboundSearchButton.click();
    }

    @And("I click on the view icon for created OB correspondence")
    public void i_click_on_the_view_icon_for_created_OB_correspondence() {
        currentWindowHandle.set(Driver.getDriver().getWindowHandle());
        try {
            searchPage.viewIcon.click();
        } catch (Exception ex) {
            searchPage.outboundSearchButton.click();
            waitForVisibility(searchPage.viewIcon, 15);
            searchPage.viewIcon.click();
        }
        waitFor(5);
    }

    @Then("I should see the Outbound Document is viewable for type {string} and contains following values")
    public void i_should_see_the_Outbound_Document_is_viewable_for_type_and_contains_following_values(String type, List<String> dataTable) throws IOException {
        outboundCorrespondenceGlobalSearchStepDefs.verifyOBNotificationViewedAndContainsValues(type, dataTable);
    }

    @When("The UI will blank out the values in the search fields")
    public void blankoutvaluesinsearchfield(List<String> dropdownoptions) {
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        for (int i = 0; i < dropdownoptions.size(); i++) {

            switch (dropdownoptions.get(i).toUpperCase()) {
                case "CORRESPONDENCE STATUS":
                    Assert.assertTrue(searchPage.correspondenceStatusdrpdown.getText().isEmpty());
                    break;
                case "CORRESPONDENCE STATUS REASON":
                    Assert.assertTrue(searchPage.obglobalsearchcorrstatusreasondropdown.getText().isEmpty());
                    break;
                case "NOTIFICATION STATUS":
                    Assert.assertTrue(searchPage.notificationStatusDropdown.getText().isEmpty());
                    break;
                case "NOTIFICATION STATUS REASON":
                    Assert.assertTrue(searchPage.obglobalsearchnotificationstatusreasondropdown.getText().isEmpty());
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case ");
            }
        }
    }

    @When("I search for an Outbound Correspondence by the following values for invalid combo")
    public void inavlidcombooutboundSearchUI(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            obcorrglobalsearch.get().fillInSearchCriteria(searchCriteria, dataTable.get(searchCriteria));

        }
    }

    @And("I verify all possible {string} search combination for {string} Status {string}")
    public void i_verify_all_possible_invalid_search_combination_for_Status(String searchInput, String type, String status) {
        outboundCorrespondenceGlobalSearchStepDefs.verifySearchCombinationForGivenStatusAndReason(searchInput, type, status);
    }


}
