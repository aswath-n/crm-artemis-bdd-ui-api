package com.maersk.dms.steps;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionChannelPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionListPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionPage;
import com.maersk.dms.pages.TenantManagerDMSProjectListPage;
import com.maersk.dms.step_definitions.OutboundCorrespondenceDefinitionChannelStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceDefinitionStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class OutboundCorrespondenceDefinitionChannelSteps extends CRMUtilities implements ApiBase {
    private TenantManagerDMSProjectListPage tmListOfProjectsPage = new TenantManagerDMSProjectListPage();
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private OutboundCorrespondenceDefinitionChannelPage outboundCorrespondenceDefinitionChannelPage = new OutboundCorrespondenceDefinitionChannelPage();
    private final ThreadLocal<OutboundCorrespondenceDefinitionStepDefs> outboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionStepDefs::new);
    private String userName = ConfigurationReader.getProperty("login");
    private String password = ConfigurationReader.getProperty("password");
    public static final ThreadLocal<Map<String, String>> save = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private final ThreadLocal<OutboundCorrespondenceDefinitionChannelStepDefs> outboundCorrespondenceDefinitionChannelStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionChannelStepDefs::new);
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    @And("I select an Outbound Correspondence Definition with no Channels")
    public void iSelectAnOutboundCorrespondenceDefinitionWithNoChannels() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().selectDefLackingChannel();
    }

    @When("I add a Outbound Correspondence Channel Definition")
    public void addChannelRandomValuesStep() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
    }

    @When("I add a Outbound Correspondence Channel Definition without {string}")
    public void addChannelSkipFieldStep(String skipField) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(skipField);
    }

    @Given("I have specified the following values in the Outbound Correspondence Channel Definition")
    public void i_have_specified_the_following_values_in_the_Outbound_Correspondence_Channel_Definition(Map<String, String> dataTable) {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(dataTable);
    }

    @Then("the Correspondence Definition Channel should save successfully")
    public void theCorrespondenceDefinitionChannelShouldSaveSuccessfully() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifySuccessfulChannelSaved();
    }

    @When("I add a Correspondence Channel Definition with the channel type of {string}")
    public void iAddACorrespondenceChannelDefinitionWithTheChannelTypeOf(String channelType) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel("channelType", channelType);
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(true);//fill in fields, but no save.get().get()
    }

    @Then("I should see the field {string} is enabled")
    public void iShouldSeeTheFieldIsEnabled(String field) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifyFieldEnabled(field);
    }

    @When("I add a second Correspondence Channel Definition with the same {string}")
    public void iAddASecondCorrespondenceChannelDefinitionWithTheSameChannel(String field) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        String tempValue = World.getWorld().outboundCorrespondenceDefinitionChannel.get().returnValues().get(field);
        save.get().put(field, tempValue);
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel(field, save.get().get(field));
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
    }

    @Then("I should see the save fail with the message {string}")
    public void iShouldSeeTheSaveFailWithTheMessage(String message) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().assertChannelFailMessage(message);
    }

    @When("I add an Outbound Correspondence Channel Definition with a {string} of {string}")
    public void iAddAnActiveCorrespondenceChannelDefinitionWithAOf(String field, String value) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        save.get().put("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
        if ("null".equalsIgnoreCase(value)) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(field);
        } else if (value.contains("Current")) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate(field, value);
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
        } else {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel(field, value);
            waitFor(1);
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
        }
    }

    @When("I add an Outbound Correspondence Channel Definition with an End Date prior to Start Date")
    public void iAddAnActiveCorrespondenceChannelDefinitionWithAnEndDatePriorToStartDate() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", "Current_SysDatePlusOneMonth");
       // outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(true, "endReason");
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(false, "");
    }

    @When("I add a Outbound Correspondence Channel Definition with {int} items from the All Fields Box to Selected Fields")
    public void iAddAOutboundCorrespondenceChannelDefinitionWithItemsFromTheAllFieldsBoxToSelectedFields(int fields) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionPage.addChannel.click();
        browserUtils.get().scrollUpRobot();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
        outboundCorrespondenceDefinitionChannelStepDefs.get().moveFromAllFields(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @Then("the Correspondence Definition Channel save successfully pop up message should appear")
    public void theCorrespondenceDefinitionChannelSaveSuccessfullyPopUpMessageShouldAppear() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifySuccessfulChannelSaved();
    }

    @When("I attempt to navigate away")
    public void iAttemptToNavigateAway() {
        outboundCorrespondenceDefinitionPage.backButton = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'keyboard_backspace')]/../.."));
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            browserUtils.get().scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
            outboundCorrespondenceDefinitionChannelPage.cancel.click();
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        } catch (Exception exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.backButton).perform();
        }
        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionListPage.header)) {
            Assert.fail();
            return;
        }
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.discardPopUp, 7);
    }

    @Then("I should see a warning message allowing to cancel or discard changes")
    public void iShouldSeeAWarningMessageAllowingToCancelOrDiscardChanges() {
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionListPage.discardPopUpMessage,3);
        // Assert.assertEquals(outboundCorrespondenceDefinitionListPage.cancelPopUpMessage.getText(), "Your Correspondence Channel will not be saved.");
        Assert.assertEquals(outboundCorrespondenceDefinitionListPage.discardPopUpMessage.getText(), "If you navigate away, your information will not be saved");

    }

    @And("I select an Outbound Correspondence Definition with a Channel with no {string}")
    public void iSelectAnOutboundCorrespondenceDefinitionWithAChannelWithNo(String field) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().selectTDOneChannel();
//        if (outboundCorrespondenceDefinitionChannelStepDefs.get().checkTestDataChannelNoEndDate() == 2) {
//            outboundCorrespondenceDefinitionChannelStepDefs.get().selectTDOneChannel();
//        }
    }

    @When("I add a second Correspondence Channel Definition with; same channel and Start Date prior to End Date of first channel")
    public void iAddASecondCorrespondenceChannelDefinitionWithSameChannelAndStartDatePriorToEndDateOfFirstChannel() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel("channelType", save.get().get("channelType"));
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
    }

    @And("I add a future Correspondence Channel Definition with {string} at a future date of {string}")
    public void addChannelWithDateInFutureStep(String field, String value) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate(field, value);
        save.get().put("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI("endDate", "endReason");
    }

    @When("I add a second Correspondence Channel Definition with; same channel and {string} of {string}")
    public void addSecondChannelWithDateValueStep(String field, String value) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel("channelType", save.get().get("channelType"));
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate(field, value);
        outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
    }

    @When("I add a Outbound Correspondence Channel Definition without saving")
    public void iAddAOutboundCorrespondenceChannelDefinitionWithoutSaving() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionPage.addChannel.click();
        waitFor(2);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
    }

    @Then("the Correspondence Definition Channel should see the system display time stamp as Created On and User Id as Created By")
    public void theCorrespondenceDefinitionChannelShouldSeeTheSystemDisplayTimeStampAsCreatedOnAndUserIdAsCreatedBy() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().clickOnFirstChannel();
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifyCreatedByAndCreatedOn();
    }

    @When("I add an Outbound Correspondence Channel Definition with a {string} of {string} plus {string} of {string}")
    public void iAddAnOutboundCorrespondenceChannelDefinitionWithAOfPlusOf(String field, String value, String field2, String value2) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        save.get().put("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
        if ("null".equalsIgnoreCase(value)) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI(field);
        } else if (value.contains("Current")) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate(field, value);
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
        } else {
            outboundCorrespondenceDefinitionChannelStepDefs.get().addChannelUI();
            waitFor(5);
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel(field, value);
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel(field2, value2);
            outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
            outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
        }
    }

    @Given("I have random valid data for an Outbound Correspondence Definition Channel")
    public void iHaveRandomValidDataForAnOutboundCorrespondenceDefinitionChannel() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
    }

    @When("I send the Outbound Correspondence Definition Channel to the server for project for the previously created project: {string}")
    public void iSendTheOutboundCorrespondenceDefinitionChannelToTheServer(String project) {
        apiAutoUtilities.postChannelDefinitionToServer(project, Integer.valueOf(save.get().get("correspondenceId")));
    }

    @Then("I should see a Correspondence Channel Definition Id is generated, valid data has been saved, Created by has been saved, and Created On save time stamp as Created On and User Id as Created By has been saved in the response")
    public void verifyResponseFromAddingChannelStep() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifyChannelResponse(World.getWorld().response.get());
    }

    @And("I create an Outbound Correspondence Definition through the API for project: {string}")
    public void iCreateAnOutboundCorrespondenceDefinitionThroughTheAPI(String project) {
        if ("current".equalsIgnoreCase(project)) {
            project = ConfigurationReader.getProperty("projectName");
        }
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServer(project);
        save.get().put("correspondenceId", World.getWorld().response.get().body().jsonPath().getString("correspondence.correspondenceId"));
    }

    @And("I select an Outbound Correspondence Definition with a Channel")
    public void selectAnOutboundCorrespondenceDefinitionWithAChannel() {
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(1);
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionPage.addChannel.click();
        browserUtils.get().scrollUpRobot();
        waitFor(1);
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit a Correspondence Channel Definition to make the {string} to be {string}")
    public void iEditACorrespondenceChannelDefinitionToMakeTheBe(String field, String value) {
        Map<String, String> fields = new HashMap<>();
        fields.put(field, value);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
//        BrowserUtils.waitFor(1);
    }

    @Then("I should the save fail with error message {string}")
    public void iShouldTheSaveFailWithErrorMessage(String message) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().assertChannelFailMessage(message);
    }

    @And("I select an Outbound Correspondence Definition with a {string} Channel")
    public void iSelectAnOutboundCorrespondenceDefinitionWithAChannel(String channel) {
//        if (!outboundCorrespondenceDefinitionChannelStepDefs.get().findCorrAtLeastOneChannel(channel)) {
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(1);
        waitFor(2);
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel("channelType", channel);
        outboundCorrespondenceDefinitionPage.addChannel.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
        waitFor(3);
        outboundCorrespondenceDefinitionChannelPage.backButton.click();
        waitFor(5);
        browserUtils.get().scrollDown();
        Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + channel + "')]")).click();
//            browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.getChannels().get(0),7);
//            outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
//        }
    }

    @Then("I should see the endReason field is enabled")
    public void iShouldSeeTheEndReasonFieldIsEnabled() {
        Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.endReason.isEnabled());
    }

    @When("I edit a Correspondence Channel Definition to make the {string} to be {string} and don't save")
    public void iEditACorrespondenceChannelDefinitionToMakeTheToBeAndDonTSave(String field, String value) {
        Map<String, String> fields = new HashMap<>();
        fields.put(field, value);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
    }

    @And("I create an Outbound Correspondence Definition Channel with Channel Type of {string},Start Date of {string}, End Date of {string}")
    public void createChannelCustomChannelStartEndDateStep(String channelType, String startDate, String endDate) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeValueChannel("channelType", channelType);
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", startDate);
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("endDate", endDate);
        outboundCorrespondenceDefinitionPage.addChannel.click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI();
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
        outboundCorrespondenceDefinitionChannelPage.backButton.click();
        waitFor(5);
        save.get().put("channelIndex", String.valueOf(outboundCorrespondenceDefinitionChannelPage.getChannels().size()));
    }

    @When("I edit the previously saved Correspondence Channel Definition to make the {string} to be {string}")
    public void iEditThePreviouslySavedCorrespondenceChannelDefinitionToMakeTheToBe(String field, String value) {
//        outboundCorrespondenceDefinitionChannelPage.getChannels().get(1).click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        Map<String, String> fields = new HashMap<>();
        fields.put(field, value);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit the previously saved Correspondence Channel Definition to make Start Date of {string}, End Date of {string}")
    public void iEditThePreviouslySavedCorrespondenceChannelDefinitionToMakeStartDateOfEndDateOf(String startDate, String endDate) {
        try {
            outboundCorrespondenceDefinitionChannelPage.getChannels().get(1).click();
        } catch (IndexOutOfBoundsException exception) {
            outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
        }
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", startDate);
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("endDate", endDate);
        Map<String, String> fields = new HashMap<>();
        fields.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
        fields.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit the previously saved Correspondence Channel Definition to make Start Date of last month, End Date of {string}")
    public void iEditThePreviouslySavedCorrespondenceChannelDefinitionToMakeStartDateOfEndDateOf(String endDate) {
        if (!Driver.getDriver().getCurrentUrl().contains("channel")) {
            try {
                outboundCorrespondenceDefinitionChannelPage.getChannels().get(1).click();
            } catch (IndexOutOfBoundsException exception) {
                outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
            }
        }
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", "Current_SysDateMinusOneMonth");
        outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("endDate", endDate);
        Map<String, String> fields = new HashMap<>();
        fields.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
        fields.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
//        uiAutoUitilities.clearStartDate();
        waitFor(1);
        outboundCorrespondenceDefinitionChannelPage.startDate.sendKeys(World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit the previously saved Correspondence Channel Definition to make {string} of {string}")
    public void iEditThePreviouslySavedCorrespondenceChannelDefinitionToMakeOf(String date, String value) {
        outboundCorrespondenceDefinitionChannelPage.getChannels().get(1).click();
        Map<String, String> fields = new HashMap<>();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        if (date.equalsIgnoreCase("startDate")) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", value);
            fields.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);

        } else {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("endDate", value);
            fields.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        }

        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I edit the Correspondence Channel Definition to make Start Date of {string}, End Date of {string}")
    public void iEditTheCorrespondenceChannelDefinitionToMakeStartDateOfEndDateOf(String date, String value) {
        Map<String, String> fields = new HashMap<>();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        browserUtils.get().scrollUpRobot();
        if (date.equalsIgnoreCase("startDate")) {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("startDate", value);
            fields.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);

        } else {
            outboundCorrespondenceDefinitionChannelStepDefs.get().changeDate("endDate", value);
            fields.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        }

        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @When("I navigate back to the previously edited Channel")
    public void iNavigateBackToThePreviouslyEditedChannel() {
        outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
        browserUtils.get().waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
    }

    @Then("the Correspondence Definition Channel should see the system display time stamp as Updated On plus User Id as Updated By")
    public void theCorrespondenceDefinitionChannelShouldSeeTheSystemDisplayTimeStampAsUpdatedOnPlusUserIdAsUpdatedBy() {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifyUpdated();
    }

    @When("I edit Correspondence Channel Definition")
    public void iEditCorrespondenceChannelDefinition() {
        outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
        waitFor(1);
    }

    @And("I move {int} items from the All Fields Box to Selected Fields")
    public void iMoveItemsFromTheAllFieldsBoxToSelectedFields(int fields) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().moveFromAllFields(fields);
        outboundCorrespondenceDefinitionChannelStepDefs.get().saveAndVerifyPopUp();
    }

    @Then("I should see {int} more fields are Selected")
    public void iShouldSeeMoreFieldsAreSelected(int fields) {
        Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.getAllFields().size() == 2);
    }

    @And("I click the {string} channel under Outbound Correspondence Definition")
    public void iClickTheChannelUnderOutboundCorrespondenceDefinition(String channelOrder) {
        switch (channelOrder.toUpperCase()) {
            case "FIRST":
                outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
                break;
            case "SECOND":
                outboundCorrespondenceDefinitionChannelPage.getChannels().get(1).click();
                break;
            default:
                Assert.fail("Channel Order didn't match with the case options");
        }
    }

    @And("I create a Outbound Correspondence Definition from api for {string} project")
    public void iCreateAOutboundCorrespondenceDefinitionFromApiForProject(String project) {
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        switch (project.toLowerCase()) {
            case "correspondenceregression":
                String correspondenceregression = "9493";
                if (!System.getProperty("env").equalsIgnoreCase("qa")) {
                    correspondenceregression = "166";
                }
                JsonObject request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateOBDefinitionRequest(correspondenceregression);
                JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOBDefintionAPI(request);
                World.generalSave.get().put("obDefCid", response.getString("correspondence.correspondenceId"));
                break;
            default:
                Assert.fail("no matching cases " + project);

        }
        Assert.assertNotNull(World.generalSave.get().get("obDefCid"));
    }

    @And("I retrieve the details for the {string} Outbound Correspondence Channel Definition")
    public void iRetrieveTheDetailsForTheOutboundCorrespondenceChannelDefinition(String obCid) {
        if ("previouslyCreated".equalsIgnoreCase(obCid)) {
            obCid = String.valueOf(World.generalSave.get().get("obDefCid"));
        }
    }

    @Then("I should see the following values were saved on the {string} Outbound Correspondence Channel Definition")
    public void iShouldSeeTheFollowingValuesWereSavedOnTheOutboundCorrespondenceChannelDefinition(String obDefCid, Map<String, String> data) {
        if ("previouslyCreated".equalsIgnoreCase(obDefCid) || "previouslyUpdated".equalsIgnoreCase(obDefCid)) {
            obDefCid = String.valueOf(World.generalSave.get().get("obDefCid"));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObDefinition(obDefCid);
        Map<String, String> expected = new HashMap<>();
        for (String key : data.keySet()) {
            if ("previouslyCreated".equalsIgnoreCase(data.get(key))) {
                expected = World.getWorld().outboundCorrespondenceDefinitionChannel.get().returnValues();
                Assert.assertEquals(response.getString("correspondenceData.channelDefinitions[0]." + key), expected.get(key));
            } else {
                Assert.assertEquals(response.getString("correspondenceData.channelDefinitions[0]." + key), data.get(key));
            }
        }
    }

    @When("I create a Outbound Correspondence Channel Definition from api for the {string} Outbound Correspondence Definition for {string} project with the following values")
    public void iCreateAOutboundCorrespondenceChannelDefinitionFromApiForTheOutboundCorrespondenceDefinitionWithTheFollowingValues(String obDefCid, String projectId, Map<String, String> data) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().createRandomChannelData();
        if ("previouslyCreated".equalsIgnoreCase(obDefCid)) {
            obDefCid = String.valueOf(World.generalSave.get().get("obDefCid"));
        }
        String correspondenceregression = "9493";
        if ("correspondenceregression".equalsIgnoreCase(projectId)) {
            if (!System.getProperty("env").equalsIgnoreCase("qa")) {
                correspondenceregression = "166";
            }
        } else {
            correspondenceregression = projectId;
        }
        String channel = "";
        String channelId = "";
        if (data.containsKey("channel")) {
            if ("random".equalsIgnoreCase(data.get("channel"))) {
                channel = World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType;
            } else {
                channel = data.get("channel");
            }
        }

        String getChannelProjectIdResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getChannelProjectId(channel, correspondenceregression);
        List<Map<String, Object>> channelIds = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId()).getList("correspondenceSettingsResponse.projectChannel");
        for (Map<String, Object> id : channelIds) {
            if (String.valueOf(id.get("channelName")).equalsIgnoreCase(channel)) {
                channelId = String.valueOf(id.get("channelId"));
            }
        }
        JsonObject request =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateObChannelDefRequest(data, getChannelProjectIdResponse, obDefCid, channelId, correspondenceregression);
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().saveChannelDefinition(request);
        String channelDefinitionId = response.getString("channelDefinitionResponse.channelDefinitionId");
        World.generalSave.get().put("channelDefinitionId", channelDefinitionId);
    }

    @When("I retrieve the notification purpose values for the project")
    public void iRetrieveTheNotificationPurposeValuesForTheProject() {
        JsonPath notificationPurposes =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObDefinitionNotificationPurposes();
        World.generalSave.get().put("notificationPurposes", notificationPurposes);
    }

    @Then("I should see the following values are present in the notification purpose enum values")
    public void iShouldSeeTheFollowingValuesArePresentInTheNotificationPurposeEnumValues(Map<String, String> data) {
        JsonPath notificationPurposesResponses = (JsonPath) World.generalSave.get().get("notificationPurposes");
        List<Map<String, String>> notificationPurposes = notificationPurposesResponses.getList("data");
        Assert.assertEquals(data.size(), notificationPurposes.size());
        int matchesFound = 0;
        for (String expected : data.keySet()) {
            for (Map<String, String> notificationPurpose : notificationPurposes) {
                if (expected.equalsIgnoreCase(notificationPurpose.get("value"))) {
                    matchesFound++;
                }
            }
        }
        Assert.assertEquals(matchesFound, notificationPurposes.size());
    }

    @And("I update the Outbound Correspondence Channel Definition from api for the for the {string} Outbound Correspondence Definition for {string} project with the following values")
    public void iUpdateTheOutboundCorrespondenceChannelDefinitionFromApiForTheForTheOutboundCorrespondenceDefinitionForProjectWithTheFollowingValues(String obDefCid, String projectId, Map<String, String> data) {
        if ("previouslyCreated".equalsIgnoreCase(obDefCid)) {
            obDefCid = String.valueOf(World.generalSave.get().get("obDefCid"));
        }
        String correspondenceregression = "9493";
        if ("correspondenceregression".equalsIgnoreCase(projectId)) {
            if (!System.getProperty("env").equalsIgnoreCase("qa")) {
                correspondenceregression = "166";
            }
        } else {
            correspondenceregression = projectId;
        }
        String channel = "";
        String channelId = "";
        if (data.containsKey("channel")) {
            if ("random".equalsIgnoreCase(data.get("channel"))) {
                channel = World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType;
            } else {
                channel = data.get("channel");
            }
        }

        String getChannelProjectIdResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getChannelProjectId(channel, correspondenceregression);
        List<Map<String, Object>> channelIds =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId()).getList("correspondenceSettingsResponse.projectChannel");
        for (Map<String, Object> id : channelIds) {
            if (String.valueOf(id.get("channelName")).equalsIgnoreCase(channel)) {
                channelId = String.valueOf(id.get("channelId"));
            }
        }
        JsonObject request =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateObChannelDefRequest(data, getChannelProjectIdResponse, obDefCid, channelId, correspondenceregression);
       API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().saveChannelDefinition(request, String.valueOf(World.generalSave.get().get("channelDefinitionId")));
    }

    @When("I select the following values on the Outbound Correspondence Channel Definition Page")
    public void iSelectTheFollowingValuesOnTheOutboundCorrespondenceChannelDefinitionPage(Map<String, String> channelData) {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        waitFor(2);
        outboundCorrespondenceDefinitionChannelStepDefs.get().fillOutChannelUI(channelData);
    }

    @Then("I should see the following values on the Outbound Correspondence Channel Definition Page")
    public void iShouldSeeTheFollowingValuesOnTheOutboundCorrespondenceChannelDefinitionPage(Map<String, String> channelData) {
        outboundCorrespondenceDefinitionChannelStepDefs.get().verifyChannelPageUIValues(channelData);
    }

    @Then("I verify the following labels on the  Correspondence Channel Definition")
    public void iVerifyTheFollowingLabelsOnTheCorrespondenceChannelDefinition(Map<String, String> channelData) {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        waitFor(2);
        for (String label : channelData.keySet()) {
            Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + label + "')]")).isDisplayed());
        }
    }

    @And("I click on the save button Outbound Correspondence Channel Definition Page")
    public void iClickOnTheSaveButtonOutboundCorrespondenceChannelDefinitionPage() {
        outboundCorrespondenceDefinitionChannelPage.saveButton.click();
        waitFor(5);
    }

    @And("I click on the navigate back button from the Outbound Correspondence Channel Definition Page")
    public void iClickOnTheNavigateBackButtonFromTheOutboundCorrespondenceChannelDefinitionPage() {
        outboundCorrespondenceDefinitionChannelPage.backButton.click();
        waitFor(5);
    }

    @And("I navigate back to the  Outbound Correspondence Channel Definition Page")
    public void iNavigateBackToTheOutboundCorrespondenceChannelDefinitionPage() {
        outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
        waitFor(5);
    }
}
