package com.maersk.dms.steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionListPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionPage;
import com.maersk.dms.pages.TenantManagerDMSProjectListPage;
import com.maersk.dms.step_definitions.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.save;
import static org.junit.Assert.fail;

public class OutboundCorrespondenceDefinitionSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<OutboundCorrespondenceDefinitionStepDefs> outboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionStepDefs::new);
    private final ThreadLocal<InboundCorrespondenceDefinitionStepDefs> inboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(InboundCorrespondenceDefinitionStepDefs::new);
    private TenantManagerDMSProjectListPage tmListOfProjectsPage = new TenantManagerDMSProjectListPage();
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    private String userName = ConfigurationReader.getProperty("login");
    private String password = ConfigurationReader.getProperty("password");
    public final ThreadLocal<Map<String, String>> save = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities=ThreadLocal.withInitial(UIAutoUitilities::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    APIClassUtil getOutCorrDefinitonList = APIClassUtil.getApiClassUtilThreadLocal();
    private OutboundCorrespondenceSettingsStepDefs outboundCorrespondenceSettingsStepDefs = new OutboundCorrespondenceSettingsStepDefs();
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    @Given("I log into Tenant Manager - DMS")
    public void logIntoTMStepDMS() {
        Driver.getDriver().get(ConfigurationReader.getProperty("DMStmPageURL"));
        outboundCorrespondenceDefinitionStepDefs.get().login(userName, password);
        waitFor(2);
        browserUtils.get().waitForClickablility(tmListOfProjectsPage.arrowClick, 7);
    }


    @And("I navigate to the Correspondence Definitions Screen of Project:{string}")
    public void navigateToCorrespondenceDefinitionScreenStep(String project) {
//        if("current".equalsIgnoreCase(project)){
//            project = ConfigurationReader.getProperty("projectName").split(" ")[1];
//        }
//        waitForVisibility(tmListOfProjectsPage.projectInput, 7);
//        staticWait(3000);
//        uiAutoUitilities.get().clearFillWithActions(tmListOfProjectsPage.projectInput, project);
//        staticWait(3000);
////        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProjectList().get(0),7);
//        tmListOfProjectsPage.searchButton.click();
//        staticWait(3000);
//        browserUtils.get().waitForClickablility(tmListOfProjectsPage.getProject(project),7);
//        tmListOfProjectsPage.getProject(project).click();
////        waitForVisibility(tmListOfProjectsPage.leftDrawer,7);
        staticWait(4000);
//        Actions actions = new Actions(Driver.getDriver());
//
//        actions.click(tmListOfProjectsPage.leftDrawer);
//        actions.perform();
        tmListOfProjectsPage.leftDrawer.click();
        staticWait(3000);
//        waitForVisibility(tmListOfProjectsPage.outBoundCorrespondenceLink,7);
        tmListOfProjectsPage.outBoundCorrespondenceLink.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition, 7);
    }

    @And("I add a new Outbound Correspondence Definition with Random Values except {string} has the {string}")
    public void addOutboundCorrespondenceDefinitionRandomValuesStep(String field, String value) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, value);
        outboundCorrespondenceDefinitionStepDefs.get().fillRandomOutboundCorrespondenceDefUI();
    }

    @Then("I should see a message that says {string}")
    public void verifyCorrespondenceDefinitionMessageStep(String message) {
        browserUtils.get().scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.outboundCorrespondenceMessage.getAttribute("innerText").contains(message));
    }


    @When("I add another Outbound Correspondence Definition with the same {string} with the same {string}")
    public void addIdenticalCorrespondenceDefinitionStep(String field, String value) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
        if (field.equalsIgnoreCase("Id")) {
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Name", RandomStringUtils.random(10, true, true));
        } else {
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Id", RandomStringUtils.random(10, true, true));
        }
        outboundCorrespondenceDefinitionStepDefs.get().fillRandomOutboundCorrespondenceDefUI();
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @Then("I should fail attempting to add a Correspondence Definition")
    public void iShouldFailAttemptingToAddACorrespondenceDefinition() {
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionPage.failMessage, 7);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.failMessage.isDisplayed());
    }

    @When("I add a new Correspondence Definition without a {string}")
    public void iAddANewCorrespondenceDefinitionWithoutA(String field) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
        outboundCorrespondenceDefinitionStepDefs.get().fillRandomOutboundCorrespondenceDefUI(field);
    }

    @Then("I should see a failure message for the required : {string}")
    public void iShouldSeeAFailureMessageSaying(String message) {
        outboundCorrespondenceDefinitionStepDefs.get().assertErrorMessages(message);
    }

    @When("I add values to the following Fields in the add Correspondence Definition screen; {string}, {string}")
    public void iAddValuesToTheFollowingFieldsInTheAddCorrespondenceDefintionScreen(String id, String stateId) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Id", id);
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("stateId", stateId);
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
    }

    @Then("I should only the following values was allowed in each field; {string}, {string}")
    public void iShouldOnlyTheFollowingValuesWasAllowedInEachField(String idStored, String stateIdStored) {
        outboundCorrespondenceDefinitionStepDefs.get().assertFieldValue("Id", idStored);
        outboundCorrespondenceDefinitionStepDefs.get().assertFieldValue("stateId", stateIdStored);

    }

    @Then("I should the Correspondence Definition was successfully added")
    public void iShouldTheCorrespondenceDefinitionWasSuccessfullyAdded() {
        outboundCorrespondenceDefinitionStepDefs.get().verifySavePopup();
    }

    @When("I add a Description to the Correspondence Definition containing {string} free text characters")
    public void iAddADescriptionToTheCorrespondenceDefinitionContainingFreeTextCharacters(String characters) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Description", RandomStringUtils.randomAscii(Integer.valueOf(characters)) + "!");
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
    }

    @Then("I should be able to all {string} characters accommodated in the {string} field")
    public void iShouldBeAbleToAllCharactersAccommodatedInTheField(String characters, String field) {
        switch (field) {
            case "Description":
                outboundCorrespondenceDefinitionStepDefs.get().assertDescriptionStringCount(Integer.valueOf(characters));
                outboundCorrespondenceDefinitionStepDefs.get().assertDescriptionDoesNotEndWith("!");
                break;
            case "End Reason":
                outboundCorrespondenceDefinitionStepDefs.get().assertEndReasonStringCount(Integer.valueOf(characters));
                outboundCorrespondenceDefinitionStepDefs.get().assertEndReasonDoesNotEndWith("!");
                break;
        }
    }

    @When("I add {string} and {string} to Correspondence Definition")
    public void iAddAndToCorrespondenceDefinition(String startDate, String endDate) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        if (startDate.contains("Current") || endDate.contains("Current")) {
            outboundCorrespondenceDefinitionStepDefs.get().changeStartEndDate(startDate, endDate);
        } else {
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("startDate", startDate);
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("endDate", endDate);
        }
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();

    }

    @Then("I should see the following {string} appear for the Start Date field and {string} for End Date")
    public void iShouldSeeTheFollowingAppearForTheStartDateFieldAndForEndDate(String startDateMessage, String endDateMessage) {
        outboundCorrespondenceDefinitionStepDefs.get().assertStartDateEndDateErrorMessages(startDateMessage, endDateMessage);
    }

    @Then("I should see the {string} appear")
    public void iShouldSeeTheAppear(String message) {
        outboundCorrespondenceDefinitionStepDefs.get().assertErrorMessages(message);
    }

    @When("I add an {string} to the Correspondence Definition")
    public void iAddAnToTheCorrespondenceDefinition(String endDate) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "startDate", "endDate");
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I add an {string} to the Correspondence Definition in addition to all required fields")
    public void addToOBDefinition(String endDate) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @Then("I should see that {string} is required message")
    public void iShouldSeeThatIsRequiredMessage(String endReason) {
        outboundCorrespondenceDefinitionStepDefs.get().assertErrorMessages(endReason);
    }

    @When("I add an {string} to the Correspondence Definition with {string} free text characters")
    public void iAddAnEndReasonToTheCorrespondenceDefinitionWithFreeTextCharacters(String field, String characters) {
        waitFor(2);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        waitFor(2);
        outboundCorrespondenceDefinitionStepDefs.get().clearFields(field);
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, RandomStringUtils.random(Integer.valueOf(characters), true, false) + "!");
        waitFor(2);
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, field);
    }

    @Then("I should see the Add Channel button present")
    public void iShouldSeeTheAddChannelButtonPresent() {
        browserUtils.get().scrollDownUsingActions(10);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.addChannel.isDisplayed());
    }

    @And("I begin to add a Correspondence Definition")
    public void iBeginToAddACorrespondenceDefinition() {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
        browserUtils.get().scrollDown();
    }

    @And("I add a new Outbound Correspondence Definition with Random Values")
    public void iAddANewOutboundCorrespondenceDefinitionWithRandomValues() {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 6);
//        outboundCorrespondenceDefinitionPage.saveButon.click();
        new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.saveButon).perform();
        BrowserUtils.waitFor(6);
//        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
//        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }


    @Given("I add a new Outbound Correspondence Definition with Random Values user may request {string}, approval required {string}, user may enter {string}")
    public void i_add_a_new_Outbound_Correspondence_Definition_with_Random_Values_user_may_request_approval_required(String userMayRequest, String approvalRequired, String userMayEnter) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        if (userMayRequest.equalsIgnoreCase("true"))
            outboundCorrespondenceDefinitionPage.userMayRequest.click();
        BrowserUtils.waitFor(2);
        if (approvalRequired.equalsIgnoreCase("true"))
            outboundCorrespondenceDefinitionPage.approvalRequired.click();
        BrowserUtils.waitFor(2);
        if (userMayEnter.equalsIgnoreCase("true"))
            outboundCorrespondenceDefinitionPage.userMayEnter.click();
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 6);
        new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.saveButon).perform();
        BrowserUtils.waitFor(6);
    }

    @Given("I will find eligible Correspondence Definition with Channel {string} and set")
    public void i_will_find_eligible_Correspondence_Definition_with_Channel_and_set(String channel, Map<String, String> dataTable) {
        World.save.get().put("idAndNameOfOCD", uiAutoUitilities.get().findCorrespondenceDefinitionWithChannel(channel, dataTable));
    }

    @Given("I find previously used Correspondence Definition and set required keys")
    public void i_find_previously_used_Correspondence_Definition_and_set_required_keys(Map<String, String> dataTable) {
        uiAutoUitilities.get().searchForOutboundDefinitionAndSetRequiredKeys(dataTable);
    }

    @And("After already authenticated I will log into CP {string}")
    public void i_selected_in_CRM(String projectName) {
        Driver.getDriver().get(ConfigurationReader.getProperty("crmPageURL"));
        outboundCorrespondenceDefinitionStepDefs.get().selectCRMProjectAfterAuthenticated();
    }

    @When("edit the previously created Outbound Correspondence Definition")
    public void editThePreviouslyCreatedOutboundCorrespondenceDefinition() {
        outboundCorrespondenceDefinitionStepDefs.get().selectPreviouslyCreatedOutCorrDef();
    }

    @When("I Edit the Outbound Correspondence Definition with the same {string} with the same value as another Outbound Correspondence Definition")
    public void iEditTheOutboundCorrespondenceDefinitionWithTheSameWithTheSameAsAnotherOutboundCorrespondenceDefinition(String field) {
        if (field.equalsIgnoreCase("Name")) {
            outboundCorrespondenceDefinitionStepDefs.get().clearFields("Name");
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Name", save.get().get(field));
            outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "Name");
        } else {
            outboundCorrespondenceDefinitionStepDefs.get().clearFields("Id");
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Id", save.get().get("ID"));
            outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "Id");
        }
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I Edit the Correspondence Definition {string} to be blank")
    public void iEditTheCorrespondenceDefinitionToBeBlank(String field) {
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, "");
        outboundCorrespondenceDefinitionStepDefs.get().clearFields(field);
        BrowserUtils.waitFor(1);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit an {string} of the Correspondence Definition to be blank")
    public void iEditAnOfTheCorrespondenceDefinitionToBeBlank(String field) {
        BrowserUtils.waitFor(2);
        outboundCorrespondenceDefinitionStepDefs.get().clearFields(field);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }


    @Given("I have random valid data for an Outbound Correspondence Definition")
    public void iHaveRandomValidDataForAnOutboundCorrespondenceDefinition() {
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
    }

    @When("I send the Outbound Correspondence Definition to the server for project: {string}")
    public void sendCorrespondenceDefinitionToServerStep(String project) {
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServer(project);
    }

    @When("I edit {string} and {string} in Correspondence Definition")
    public void iEditAndInCorrespondenceDefinition(String startDate, String endDate) {
        outboundCorrespondenceDefinitionStepDefs.get().changeStartEndDate(startDate, endDate);
        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionPage.startDate, World.getWorld().outboundCorrespondenceDefinition.get().startDate);
        BrowserUtils.waitFor(2);
        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionPage.endDate, World.getWorld().outboundCorrespondenceDefinition.get().endDate);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @And("I have at least {int} Outbound Correspondence Definition with {int} characters for Id")
    public void iHaveAtLeastOutboundCorrespondenceDefinitionWithCharacters(int definitions, int characters) {
        String value = RandomStringUtils.random(characters, true, true);
        Map<String, String> customFields = new HashMap<>();
        customFields.put("Id", value);
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(definitions, customFields);
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
    }

    @And("I have at least {int} Outbound Correspondence Definition with {int} characters for Name")
    public void iHaveAtLeastOutboundCorrespondenceDefinitionWithCharactersForName(int definitions, int characters) {
        String value = RandomStringUtils.randomAscii(40);
        Map<String, String> customFields = new HashMap<>();
        customFields.put("Name", value);
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(definitions, customFields);
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
    }

    @Then("I should see ID is displayed as a hyperlink")
    public void iShouldSeeIDIsDisplayedAsAHyperlink() {
        uiAutoUitilities.get().findInList(World.getWorld().outboundCorrespondenceDefinition.get().ID);
        Assert.assertEquals(outboundCorrespondenceDefinitionPage.Id.getAttribute("value").length(), 10);
    }

    @Then("I should see Correspondence Name is {int} characters displayed")
    public void iShouldSeeCorrespondenceNameIsCharactersDisplayed(int name) {
        outboundCorrespondenceDefinitionStepDefs.get().assertNameLength(name);

    }

    @And("I have at least {int} Outbound Correspondence Definition with Start Date is on or before today and End Date is on current date")
    public void iHaveAtLeastOutboundCorrespondenceDefinitionWithStartDateIsOnOrBeforeTodayAndEndDateIsOnCurrentDate(int definitions) {
        Map<String, String> customFields = new HashMap<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        customFields.put("endDate", dtf.format(now));
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(definitions, customFields);
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
    }

    @Then("I should see the Outbound Correspondence Definition status displayed as {string}")
    public void iShouldSeeTheOutboundCorrespondenceDefinitionStatusDisplayedAs(String status) {
        outboundCorrespondenceDefinitionStepDefs.get().assertStatus(status);
    }

    @And("I have an Outbound Correspondence Definition with End Date is earlier than today in project: {string}")
    public void iHaveAtLeastOutboundCorrespondenceDefinitionWithEndDateIsEarlierThanToday(String project) {
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        Map<String, Object> map = new HashMap<>();
        map.put("endDate", LocalDateTime.now().minusMonths(1).toString());
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServer(project, map);
    }

    @And("I have at least {int} Outbound Correspondence Definition with Start Date later than today")
    public void iHaveAtLeastOutboundCorrespondenceDefinitionWithStartDateLaterThanToday(int definitions) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Map<String, String> customFields = new HashMap<>();
        customFields.put("startDate", dtf.format(now.plusMonths(1)));
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(definitions, customFields);
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
    }

    @And("I have at least {int} Outbound Correspondence Definition")
    public void verifyAtLeastThreeDefinitionsExistStep(int definitions) {
        if (outboundCorrespondenceDefinitionListPage.idColumn().size() < 3) {
            Map<String, String> customFields = new HashMap<>();
            outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(definitions, customFields);
            outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
        }
    }

    @When("I sort by the {string}")
    public void sortOutboundCorrespondenceDefinitionsByColumnStep(String column) {
        outboundCorrespondenceDefinitionStepDefs.get().sortOutboundCorrespondenceList(column);
    }

    @Then("I should see the Outbound Correspondence Definition records are sorted by that {string}")
    public void verifySortedByColumnStep(String column) {
        outboundCorrespondenceDefinitionStepDefs.get().assertSortedByColumn(column);
    }


    @Then("I should see the add an Outbound Correspondence Definition button")
    public void iShouldSeeTheAddAnOutboundCorrespondenceDefinitionButton() {
        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.isDisplayed());
    }

    @Then("I should see the {int} Correspondence Definition details displayed")
    public void iShouldSeeTheCorrespondenceDefinitionDetailsDisplayed(int definitions) {
        Assert.assertEquals(outboundCorrespondenceDefinitionListPage.idColumn().size(), 10);
    }

    @When("I Edit values to the following Fields in the Correspondence Definition screen; {string}, {string}")
    public void iEditValuesToTheFollowingFieldsInTheCorrespondenceDefinitionScreen(String id, String stateId) {
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Id", id);
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("stateId", stateId);
        outboundCorrespondenceDefinitionStepDefs.get().clearFields("Id");
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "Id");
        outboundCorrespondenceDefinitionStepDefs.get().clearFields("stateId");
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "stateId");
//        fillTheFiled(outboundCorrespondenceDefinitionPage.Id, World.getWorld().outboundCorrespondenceDefinition.get().ID);
//        fillTheFiled(outboundCorrespondenceDefinitionPage.stateId, World.getWorld().outboundCorrespondenceDefinition.get().stateID);
    }

    @When("I attempt to navigate away from Outbound Correspondence Definition page")
    public void iAttemptToNavigateAwayFromOutboundCorrespondenceDefinitionPage() {
        outboundCorrespondenceDefinitionPage.backButton.click();
    }

    @Then("I should see a warning message allowing me to either discard or cancel changes - DMS")
    public void iShouldSeeAWarningMessageAllowingMeToEitherDiscardOrCancelChangesDMS() {
        browserUtils.get().waitForClickablility(outboundCorrespondenceDefinitionPage.discardFailMessage, 7);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.discardFailMessage.isDisplayed());
    }

    @When("I click on the {string} hyperlink")
    public void iClickOnTheHyperlink(String hyperlink) {
        switch (hyperlink.toUpperCase()) {
            case "ID":
                outboundCorrespondenceDefinitionStepDefs.get().clickFirstRecord();
                break;
            case "NAME":
                inboundCorrespondenceDefinitionStepDefs.get().clickFirstRecord();
                break;
            default:
                fail("HyperLink Type didn't match");
        }
    }

    @Then("I should see the Correspondence Definition details displayed")
    public void iShouldSeeTheCorrespondenceDefinitionDetailsDisplayed() {
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader.isDisplayed());
    }

    @When("I Edit the new Correspondence Definition without a {string}")
    public void iEditTheNewCorrespondenceDefinitionWithoutA(String field) {
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, "");
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, field);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
        waitFor(2);
    }

    @When("I edit an {string} of the Correspondence Definition to be {string}")
    public void iEditAnOfTheCorrespondenceDefinitionToBe(String field, String value) {
        if ("random".equalsIgnoreCase(value)) {
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, RandomStringUtils.random(5, true, true));
        } else {
            outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, value);

        }
        outboundCorrespondenceDefinitionStepDefs.get().clearFields(field);
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, field);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @Then("Then I should see valid data has been saved, Updated by and Updated On has been saved and displayed")
    public void thenIShouldSeeValidDataHasBeenSavedUpdatedByAndUpdatedOnHasBeenSavedAndDisplayed() {
        outboundCorrespondenceDefinitionStepDefs.get().assertUpdatedByOn();
    }

    @And("I add another Outbound Correspondence Definition with Random Values")
    public void iAddAnotherOutboundCorrespondenceDefinitionWithRandomValues() {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        waitFor(4);
        outboundCorrespondenceDefinitionPage.saveButon.click();
        BrowserUtils.waitFor(1);
        // outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
        // browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }

    @And("I add a new Outbound Correspondence Definition with Random Values with {string} with {string}")
    public void createRandomOutboundCorrespondenceDefinitionSaveFieldsStep(String field, String value) {
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        if ("Id".equalsIgnoreCase(field)) save.get().put(field, World.getWorld().outboundCorrespondenceDefinition.get().ID);
        else save.get().put(field, World.getWorld().outboundCorrespondenceDefinition.get().Name);
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        waitFor(2);
        outboundCorrespondenceDefinitionPage.saveButon.click();
        BrowserUtils.waitFor(1);
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }

    @And("I Edit the Correspondence Definition {string} to be blank but don't save")
    public void iEditTheCorrespondenceDefinitionToBeBlankButDonTSave(String field) {
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence(field, "");
        outboundCorrespondenceDefinitionStepDefs.get().clearFields(field);
        BrowserUtils.waitFor(1);
    }

    @When("I Edit a Description to the Correspondence Definition containing {string} free text characters")
    public void iEditADescriptionToTheCorrespondenceDefinitionContainingFreeTextCharacters(String characters) {
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Description", RandomStringUtils.random(Integer.valueOf(characters), true, false) + "!");
        outboundCorrespondenceDefinitionStepDefs.get().clearFields("Description");
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence(true, "Description");

    }

    @When("I edit {string} and {string} to Correspondence Definition")
    public void iEditAndToCorrespondenceDefinition(String startDate, String endDate) {
        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionPage.startDate, startDate);
        BrowserUtils.waitFor(2);
        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionPage.endDate, endDate);
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I select an Outbound Correspondence Definition randomly")
    public void iSeletAnOutboundCorrespondenceDefinitionRandomly() {
        outboundCorrespondenceDefinitionListPage.idColumn().get(0).click();
    }

    @Then("I should the Correspondence Definition was successfully updated")
    public void iShouldTheCorrespondenceDefinitionWasSuccessfullyUpdated() {
        outboundCorrespondenceDefinitionStepDefs.get().assertUpdatedByOn();
    }

    @Then("I should see a Correspondence ID is generated, valid data has been saved, Created by has been saved, and Created On has been saved")
    public void iShouldSeeACorrespondenceIDIsGeneratedValidDataHasBeenSavedCreatedByHasBeenSavedAndCreatedOnHasBeenSaved() {
        JsonPath response = (JsonPath) World.generalSave.get().get("OutboundDefResponse");
        String cid = response.getString("correspondence.correspondenceId");
        Assert.assertTrue(response.getString("correspondence.correspondenceId").length() > 0, "cid - " + cid);
    }

    @And("I find the previously created Outbound Correspondence Definition")
    public void iFindThePreviouslyCreatedOutboundCorrespondenceDefinition() {
        uiAutoUitilities.get().findInList(World.getWorld().outboundCorrespondenceDefinition.get().ID, true);
    }

    @And("I click add button on Outbound Correspondence Definition")
    public void iClickAddButtonOnOutboundCorrespondenceDefinition() {
        waitFor(3);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
    }

    @And("I click Save button on Outbound Correspondence Definition")
    public void iClickSaveButtonOnOutboundCorrespondenceDefinition() {
        waitFor(3);
        outboundCorrespondenceDefinitionPage.saveButton.click();
    }

    @Then("I verify Inbound Correspondence Type warning message")
    public void iVerifyInboundCorrespondenceTypeWarningMessage() {
        waitFor(3);
        Assert.assertTrue(pubCorr.get().elementIsDisplayed(outboundCorrespondenceDefinitionPage.inboundCorrTypeWarningMessage));
    }

    @And("I update Outbound Correspondence Definition Type")
    public void iUpdateOutboundCorrespondenceDefinitionType() {
        browserUtils.get().selectRandomDropDownOption(outboundCorrespondenceDefinitionPage.inboundCorrespondenceDropdown);
    }

    @Then("Verify panel populated with records related to the Case ID")
    public void verifyPanelPopulatedWithRecordsRelatedToTheCaseID() {
        outboundCorrespondenceDefinitionStepDefs.get().PopulatedWithRecordsRelatedToTheCaseID();
    }

    @Then("Verify panel populated with records related to the Consumer ID")
    public void verifyPanelPopulatedWithRecordsRelatedToTheConsumerID() {
        outboundCorrespondenceDefinitionStepDefs.get().PopulatedWithRecordsRelatedToTheCaseID();
    }

    @Then("I should see Inbound Correspondence definition ID in the response of outbound Correspondence definition POST")
    public void iShouldSeeInboundCorrespondenceDefinitionIDInTheResponseOfOutboundCorrespondenceDefinitionPOST() {
        outboundCorrespondenceDefinitionStepDefs.get().verifyInboundCorrespondenceIDisCreated();
    }

    @When("I initiate Get Outbound Correspondence definition")
    public void iInitiateGetOutboundCorrespondenceDefinition() {

        getOutCorrDefinitonList.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api/correspondencelist");
        getOutCorrDefinitonList.setEndPoint("/6203?size=10");
        getOutCorrDefinitonList.getAPI();
    }

    @Then("I verify Outbound Correspondence contains Inbound Correspondence Definition ID")
    public void iVerifyOutboundCorrespondenceContainsInboundCorrespondenceDefinitionID() {

        int actualId = getOutCorrDefinitonList.jsonPathEvaluator.get("pageListCorrespondence.content[0].inboundCorrespondenceDefinitionId");
        Assert.assertTrue(getOutCorrDefinitonList.jsonPathEvaluator.get("pageListCorrespondence.content[0].inboundCorrespondenceDefinitionId") != null);
        Assert.assertTrue(actualId >= 1);

    }

    @When("I send the request to create an Outbound Correspondence Definition")
    public void iSendTheRequestToCreateAnOutboundCorrespondenceDefinition() {
        World.generalSave.get().put("OutboundDefResponse", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOutboundCorrespondenceDefinition((Map<String, String>) World.generalSave.get().get("OutboundDefRequest")));
    }

    @Then("I should see the request to create an Outbound Correspondence Definition was successful")
    public void iShouldSeeTheRequestToCreateAnOutboundCorrespondenceDefinitionWasSuccessful() {
        JsonPath response = (JsonPath) World.generalSave.get().get("OutboundDefResponse");
        Assert.assertTrue("success".equalsIgnoreCase(response.getString("status")));
        Assert.assertTrue(response.getString("correspondence.correspondenceId").length() > 0);
    }

    @And("I have a request to create an Outbound Correspondence Definition with random values for just the required values")
    public void iHaveARequestToCreateAnOutboundCorrespondenceDefinitionWithRandomValuesForJustTheRequiredValues() {
        Map<String, String> randomValues = new HashedMap();
        randomValues.put("mmsId", RandomStringUtils.random(9, true, true));
        randomValues.put("correspondenceName", RandomStringUtils.random(20, true, true));
        randomValues.put("description", RandomStringUtils.random(10, true, false));
        randomValues.put("bodyDataSource", RandomStringUtils.random(10, true, false));
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        randomValues.put("startDate", nowAsISO);
        World.generalSave.get().put("OutboundDefRequest", randomValues);
    }

    @And("I navigate away from the Outbound Correspondence Definition")
    public void iNavigateAwayFromTheOutboundCorrespondenceDefinition() {
        outboundCorrespondenceDefinitionStepDefs.get().navigateAwayDiscardChanges();
    }

    @And("I click on edit button on Outbound Settings Page")
    public void iClickOnEditButtonOnOutboundSettingsPage() {
        waitFor(3);
        browserUtils.get().scrollUpUsingActions(2);
        waitFor(2);
        outboundCorrespondenceSettingsStepDefs.ifEditButtonThenClick();
    }

    @When("I send the Outbound Correspondence Definition to the server for project: {string} with random valid data and Body Data Structure")
    public void i_send_the_Outbound_Correspondence_Definition_to_the_server_for_project_with_random_valid_data_and_Body_Data_Structure(String project) {
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServerWithBodyElement(project);
    }

    @Given("I will add Channel {string} to the Outbound Correspondence Definition")
    public void i_will_add_Channel_to_the_Outbound_Correspondence_Definition(String channelType) {
        apiAutoUtilities.addChannelToOutboundDefinition(channelType);
    }

    @Then("I verify the Outbound Correspondence definition response body has Body Data Structure Version is {int}")
    public void i_verify_the_Outbound_Correspondence_definition_response_body_has_Body_Data_Structure_Version_is(Integer expectedVersion) {
        Integer actualVersion = Integer.valueOf(World.save.get().get("definitionBodyVersion"));
        Assert.assertEquals(actualVersion, expectedVersion, "Body Data Structure Version verification failed");
    }

    @When("I update the Outbound Correspondence Definition and add body element")
    public void i_update_the_Outbound_Correspondence_Definition_and_add_body_element(List<String> bodyElements) {
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServerWithBodyElement(bodyElements);
    }

    @When("I retrieve the previously created Outbound Correspondence Definition")
    public void iRetrieveThePreviouslyCreatedOutboundCorrespondenceDefinition() {
        JsonPath outbDefResponse = (JsonPath) World.generalSave.get().get("OutboundDefResponse");
        String cid = String.valueOf(Integer.valueOf(outbDefResponse.getString("correspondence.correspondenceId")));
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundDefinition(cid));
    }

    @Then("I should see that the approvalRequired value was saved on the Outbound Correspondence Definition")
    public void iShouldSeeThatTheApprovalRequiredValueWasSavedOnTheOutboundCorrespondenceDefinition() {
        Assert.assertTrue(response.get().prettyPrint().contains("approvalRequired"));
    }

    @And("I should see that the approvalRequired in the response for the Outbound Correspondence Definition")
    public void iShouldSeeThatTheApprovalRequiredInTheResponseForTheOutboundCorrespondenceDefinition() {
        JsonPath outbDefResponse = (JsonPath) World.generalSave.get().get("OutboundDefResponse");
        Assert.assertTrue(outbDefResponse.prettyPrint().contains("approvalRequired"));
    }

    @When("I have a request to retrieve a Outbound Correspondence Definition by mmsCode")
    public void iHaveARequestToRetrieveAOutboundCorrespondenceDefinitionByMmsCode() {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundDefinitionByMmsCode(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType()));
    }

    @When("I have a request to retrieve a Outbound Correspondence Definition by created mmsCode {string}")
    public void iHaveARequestToRetrieveAOutboundCorrespondenceDefinitionByMmsCode(String mmsCode) {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundDefinitionByMmsCode(mmsCode));
        World.save.get().put("CorrDefinitionCorrId", response.get().getString("correspondenceData.correspondenceId"));
    }

    @Then("I should see approvalRequired value is returned in the response Outbound Correspondence Definition by mmsCode")
    public void iShouldSeeApprovalRequiredValueIsReturnedInTheResponseOutboundCorrespondenceDefinitionByMmsCode() {
        Assert.assertTrue(response.get().prettyPrint().contains("approvalRequired"));
    }

    @When("I have a request to retrieve a Outbound Correspondence list by project")
    public void iHaveARequestToRetrieveAOutboundCorrespondenceListByProject() {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOBDefinitionListByProject());
    }

    @Then("I should see approvalRequired value is returned in the response Outbound Correspondence Definition list by project")
    public void iShouldSeeApprovalRequiredValueIsReturnedInTheResponseOutboundCorrespondenceDefinitionListByProject() {
        Assert.assertTrue(response.get().prettyPrint().contains("approvalRequired"));
    }

    @When("I validate the Inbound Correspondence Type field is not Mandatory")
    public void ivalidateinboundcorresppondencetypeisnonmandatoty() {
        outboundCorrespondenceDefinitionStepDefs.get().inboundcorrtypenonmandatory();

    }

    @Then("I retrieve the previously created Outbound Correspondence Definition by correspondence ID")
    public void i_retrieve_the_previously_created_Outbound_Correspondence_Definition_by_correspondence_ID() {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundDefinition(World.save.get().get("correspondenceDefId")));
    }

    @Then("I verify the Outbound Correspondence definition response body not contains {string}")
    public void i_verify_the_Outbound_Correspondence_definition_response_body_not_contains(String value) {
        Assert.assertFalse(response.get().prettyPrint().contains(value));
    }

    @And("I verify the correspondence definition has a required key as {string} for mmsCode {string}")
    public void iVerifyCorrDefinitionHasRequiredKey(String expectedKey, String mmsCode) {
        Map map = (Map) response.get().get("correspondenceData");
        Map m2 = new HashMap();
        List corrList = (List) map.get("correspondenceRequiredKeys");
        if (corrList.size() > 0) {
            m2 = (Map) corrList.get(0);
        }
        String actualKey = String.valueOf(m2.get("requiredKeyValue"));
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("mmsId", mmsCode);
        inputs.put("correspondenceId", World.save.get().get("CorrDefinitionCorrId"));
        inputs.put("correspondenceName", "5639");
        if (!actualKey.equalsIgnoreCase(expectedKey)) {
            inputs.put("requiredKeyValue", expectedKey);
        } else {
            inputs.put("requiredKeyValue", "");
        }
        apiAutoUtilities.postOutboundCorrespondenceDefinitionCustom(inputs);
        Assert.assertTrue(("success").equalsIgnoreCase(World.getWorld().jsonPath.get().getString("status")));
        save.get().put("correspondenceDefId", World.save.get().get("CorrDefinitionCorrId"));
    }

    @And("I verify Channel Exists as active on correspondence definition")
    public void getActiveChannelsByCorrIdAndVerifyChannelExists(List<String> expectedChannels) {
        try {
            String corrId = World.save.get().get("CorrDefinitionCorrId");
            JsonPath jsonPathChannelResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getActiveChannelsByCorrId(corrId);
            int channelListSize = jsonPathChannelResponse.getInt("channelDefinitionResponseList.size");
            List<String> actualChannelValues = new ArrayList<>();
            for (int i = 0; i < channelListSize; i++) {
                String actualChannelStatus = jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].status");
                if ("Active".equalsIgnoreCase(actualChannelStatus)) {
                    System.out.println(jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].channelType"));
                    actualChannelValues.add(jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].channelType"));
                }
            }
            Assert.assertTrue(actualChannelValues.containsAll(expectedChannels), "Expected Channel is missing in Correspondence Definition");
        } catch (Exception e) {
            System.out.println("Error in getActiveChannelsByCorrIdAndVerifyChannels " + e.getMessage());
        }
    }

    @And("I verify Channel Does Not Exists as active on correspondence definition")
    public void getActiveChannelsByCorrIdAndVerifyChannelNotExists(List<String> expectedChannels) {
        try {
            String corrId = World.save.get().get("CorrDefinitionCorrId");
            JsonPath jsonPathChannelResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getActiveChannelsByCorrId(corrId);
            int channelListSize = jsonPathChannelResponse.getInt("channelDefinitionResponseList.size");
            List<String> actualChannelValues = new ArrayList<>();
            for (int i = 0; i < channelListSize; i++) {
                String actualChannelStatus = jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].status");
                if ("Active".equalsIgnoreCase(actualChannelStatus)) {
                    actualChannelValues.add(jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].channelType"));
                }
            }
            Assert.assertTrue(!actualChannelValues.containsAll(expectedChannels), "Expected Channel is available in Correspondence Definition");
        } catch (Exception e) {
            System.out.println("Error in getActiveChannelsByCorrIdAndVerifyChannelNotExists " + e.getMessage());
        }

    }

    @And("I verify events are not created for the following types")
    public void iVerifyEventsAreNotCreated(List<String> expectedEventNames) {
        try {
            DPBIEventsStepDefs dpbiEventsStepDefs = new DPBIEventsStepDefs();
            for (int i = 0; i < expectedEventNames.size(); i++) {
                dpbiEventsStepDefs.searchEventByEventName(expectedEventNames.get(i));
                JsonPath response = (JsonPath) World.generalSave.get().get("response.");
                outboundCorrespondenceDefinitionStepDefs.get().validateEventNotCreated(response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @When("I successfully update approval required checkbox to {string}")
    public void iUpdateApprovalReqdCheckbox(String isChecked) {
        outboundCorrespondenceDefinitionStepDefs.get().updateApprovalRequired(isChecked);
    }

    @Then("I retrieve payload from get event by traceId for event name {string}")
    public void iRetrievePayLoadFromGetEventByTraceId(String eventName) {
        String payload = "";
        JsonPath responseByTrace = (JsonPath)API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(APIClassUtil.traceId.get());
        List events = responseByTrace.getJsonObject("events.eventName");
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).toString().equalsIgnoreCase(eventName)) {
                payload = responseByTrace.getString("events[" + i + "].payload");
                break;
            }
        }
        JsonObject payLoadData = new JsonParser().parse(payload).getAsJsonObject();
        World.generalSave.get().put("payLoadData", payLoadData);
    }

    @And("I verify Outbound Correspondence Definition Header Displays as Outbound Correspondence Definition")
    public void iVerifyOutboundCorrespondenceDefinitionHeaderDisplaysAsOutboundCorrespondenceDefinition() {
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 3);
        Assert.assertEquals(outboundCorrespondenceDefinitionListPage.header.getText(), "OUTBOUND CORRESPONDENCE DEFINITIONS", "OUTBOUND CORRESPONDENCE DEFINITIONS LIST PAGE header is not displayed as OUTBOUND CORRESPONDENCE DEFINITIONS");
    }

    @When("I validate following IB types available in Outbound Correspondence Definition page")
    public void ivalidateinboundcorresppondencetypevalues(List<String> dataTable) {
        outboundCorrespondenceDefinitionStepDefs.get().validateibfieldvaluesinOBDefinitions(dataTable);
    }

    @Given("I will update Outbound Correspondence Definition with following values")
    public void i_will_update_Outbound_Correspondence_Definition(Map<String, String> dataTable) {
        apiAutoUtilities.updateOutboundDefinitionRequest(dataTable);
    }

    @Then("I verify the Outbound Correspondence Channel definition post response body with following values")
    public void i_verify_the_Outbound_Correspondence_Channel_definition_response_body(Map<String, String> dataTable) {
        apiAutoUtilities.validateOutboundChannelDefinitionPOSTResponse(dataTable);
    }

    @Then("I have a request to retrieve an Outbound Correspondence Channel Definition by correspondenceId {string}")
    public void i_verify_the_Outbound_Correspondence_definition_response_body(String CorrespondenceID) {

        apiAutoUtilities.getOutboundChannelDefinitionBycorrespondenceId(CorrespondenceID);

    }

    @Then("I verify the Outbound Correspondence Channel definition get response body with following values")
    public void i_verify_the_Outbound_Correspondence_Channel_definition_get_response_body(Map<String, String> dataTable) {
        apiAutoUtilities.validateOutboundChannelDefinitionGetResponse(dataTable);
    }

    @And("I {string} Add Template button options")
    public void validateAddTemplateValues(String action, List<String> dropdownoptions) {
        outboundCorrespondenceDefinitionStepDefs.get().validateAddTemplateValues(action, dropdownoptions);

    }

    @Then("I select {string} file for upload and validate add template")
    public void iSelectTemplateFileForUpload(String fpath) throws Exception {
        outboundCorrespondenceDefinitionStepDefs.get().iSelectFileForUpload(fpath);
    }

    @Then("I validate following fields in OB channel definition Templates grid")
    public void i_verify_upload_template_file_grid(Map<String, String> dataTable) {
        outboundCorrespondenceDefinitionStepDefs.get().validateTemplatesgridfieldvalues(dataTable);
    }

    @Then("I {string} following fields in OB channel definition Templates grid for External Template ID option")
    public void i_verify_external_templateid_grid(String action,Map<String, String> dataTable) {
        outboundCorrespondenceDefinitionStepDefs.get().validateTemplatesgridfieldvaluesForExternalTempalteId(action,dataTable);
    }

    @And("I have active languages for the project {string}")
    public void iTheActiveLanguagesForTheProjectIs(String project) {
        apiAutoUtilities.getActiveLanguagesForTheProject(project);
    }

    @Then("I validate Required fields for External Template ID option")
    public void i_validate_req_fields_External_TemplateID_option() {
        outboundCorrespondenceDefinitionStepDefs.get().validateRequiredfieldsForExternalTempalteId();
    }

    @Then("I select following fields in OB channel definition Templates grid")
    public void i_select_values_template_grid(Map<String, String> dataTable) {
        outboundCorrespondenceDefinitionStepDefs.get().selectTemplatesgridfieldvalues(dataTable);
    }

    @And("I have active languages and channels for {string}")
    public void iTheActiveLanguagesForThechannel(String mmsid) {
        apiAutoUtilities.getActiveChannelsandLanguagesForMMSID(mmsid);
    }
}


