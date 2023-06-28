package com.maersk.dms.steps;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.step_definitions.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.json.Json;
import org.testng.Assert;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class DPBIOutboundCorrespondenceConfigEventsSteps extends TMUtilities {


    private APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private ThreadLocal<Response> events = new ThreadLocal<>();
    TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    final ThreadLocal<OutboundCorrespondenceSettingsStepDefs> outboundCorrespondenceSettingsStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceSettingsStepDefs::new);
    final ThreadLocal<DPBIOutboundCorrespondenceConfigEventsStepDefs> dPBIOC = ThreadLocal.withInitial(DPBIOutboundCorrespondenceConfigEventsStepDefs::new);
    final ThreadLocal<OutboundCorrespondenceDefinitionStepDefs> outboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionStepDefs::new);
    final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    final ThreadLocal<ArrayList<String>> givenInfo = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> startEndDates = ThreadLocal.withInitial(ArrayList::new);
    public static ThreadLocal<ArrayList<String>> infoForEndpoint = ThreadLocal.withInitial(ArrayList::new);
    public final ThreadLocal<String> accessToken = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> endpointID = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> endpointNID = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> formCode = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> barcode = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> NIDResponse = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> bodySetName = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public final ThreadLocal<String> bodyItemName = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public final ThreadLocal<String> bodyItemValue = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public final ThreadLocal<String> bodySubsetName = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public final ThreadLocal<String> bodySubsetItemName = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public ThreadLocal<String> bodySubsetItemValue = ThreadLocal.withInitial(()->RandomStringUtils.random(10, true, true));
    public static final ThreadLocal<List<String>> notificationIds = ThreadLocal.withInitial(ArrayList::new);
    public static final ThreadLocal<Map<String, String>> updatedInfo =  ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<String> currentCID = ThreadLocal.withInitial(()->"");
    private final ThreadLocal<JsonPath> getCID = new ThreadLocal<>();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();



    @When("I add a role {string},{string},{string},{string}")
    public void i_add_a_role(String projectId, String projectName, String action, String recordType) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"action\":\"" + action);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        String roleName = RandomStringUtils.random(10, true, true);
        String roleDesc = RandomStringUtils.random(10, true, true);
        givenInfo.get().add("\"roleName\":\"" + roleName + "\"");
        givenInfo.get().add("\"roleDesc\":\"" + roleDesc + "\"");

        startEndDates.set(dPBIOC.get().addRole(projectId, projectName, roleName, roleDesc));
    }

    @When("I update a role {string},{string},{string},{string}")
    public void i_update_a_role(String projectId, String projectName, String action, String recordType) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"action\":\"" + action);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        String roleName = RandomStringUtils.random(10, true, true);
        String roleDesc = RandomStringUtils.random(10, true, true);
        givenInfo.get().add("\"roleName\":\"" + roleName + "\"");
        givenInfo.get().add("\"roleDesc\":\"" + roleDesc + "\"");

        startEndDates.set(dPBIOC.get().updateRole(projectId, projectName, roleName, roleDesc));
    }

    @When("I update a role with Role Name Only {string},{string},{string},{string}")
    public void i_update_a_role_with_role_name_only(String projectId, String projectName, String action, String recordType) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"action\":\"" + action);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        String roleName = RandomStringUtils.random(10, true, true);
        givenInfo.get().add("\"roleName\":\"" + roleName + "\"");

        startEndDates.set(dPBIOC.get().updateRoleName(projectId, projectName, roleName));
    }

    @When("I update a role with Role Description Only {string},{string},{string},{string}")
    public void i_update_a_role_with_role_description_only(String projectId, String projectName, String action, String recordType) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"action\":\"" + action);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        String roleDesc = RandomStringUtils.random(10, true, true);
        givenInfo.get().add("\"roleDesc\":\"" + roleDesc + "\"");

        startEndDates.set(dPBIOC.get().updateRoleDesc(projectId, projectName, roleDesc));
    }

    @When("I add a channel {string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_add_a_channel(String projectId, String projectName, String recordType, String channelType, String endReason, String senderEmailId, String sendImmediately, String mandatory) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        givenInfo.get().add("\"channelType\":\"" + channelType + "\"");
        givenInfo.get().add("\"endReason\":\"" + endReason + "\"");
        if (senderEmailId.compareToIgnoreCase("email") == 0)
            givenInfo.get().add("\"senderEmailId\":\"" + senderEmailId + "\"");
        givenInfo.get().add("\"sendImmediately\":" + sendImmediately);
        givenInfo.get().add("\"mandatory\":" + mandatory);
        startEndDates.set(dPBIOC.get().addOutboundCorrChannel(channelType, sendImmediately, mandatory, endReason, senderEmailId));
    }

    @And("I add a Template to the existing channel")
    public void iAddATemplateToTheExistingChannel() throws Exception {
        infoForEndpoint.set( dPBIOC.get().addTemplate());
    }

    public ArrayList<String> infoForEndpoint() {
        return this.infoForEndpoint.get();
    }

    @And("I Create Correspondence with the newly added definition")
    public void iCreateCorrespondenceWithTheNewlyAddedDefinition() throws IOException {
        String rawBody = outCorr.get().rawBodyCreateCorrespondence1(infoForEndpoint.get().get(0), infoForEndpoint.get().get(1), infoForEndpoint.get().get(2));
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), rawBody));
        barcode.set(outCorr.get().searchInboundTypeBarcode(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), infoForEndpoint.get().get(3)));
    }

    @And("I get Correspondence by ID")
    public void iGetCorrespondenceByID() throws IOException {
        endpointNID.set(outCorr.get().getCorrespondence(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), endpointID.get()));
        if (endpointNID.get().contains("[[")) {
            endpointNID.set(endpointNID.get().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
    }

    @And("I call letter data by NID")
    public void iCallLetterDataByNID() throws IOException {
        formCode.set(outCorr.get().getLetterDataByNID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), endpointNID.get()));
    }

    @Then("form must match the barcode from Search Inbound Type")
    public void formMustMatchTheBarcodeFromSearchInboundType() {
        Assert.assertEquals(barcode.get(), formCode.get());
    }

    @When("I update a channel {string},{string},{string},{string},{string},{string},{string},{string},{string}")
    public void i_update_a_channel(String projectId, String projectName, String recordType, String channelTypeEdit, String channelType, String endReason, String senderEmailId, String sendImmediately, String mandatory) throws ParseException {
        givenInfo.get().add("\"projectId\":" + projectId);
        givenInfo.get().add("\"projectName\":\"" + projectName);
        givenInfo.get().add("\"recordType\":\"" + recordType + "\"");
        givenInfo.get().add("\"channelType\":\"" + channelType + "\"");
        givenInfo.get().add("\"endReason\":\"" + endReason + "\"");
        if (senderEmailId.compareToIgnoreCase("email") == 0)
            givenInfo.get().add("\"senderEmailId\":\"" + senderEmailId + "\"");
        givenInfo.get().add("\"sendImmediately\":" + sendImmediately);
        givenInfo.get().add("\"mandatory\":" + mandatory);
        startEndDates.set(dPBIOC.get().updateOutboundCorrChannel(channelTypeEdit, channelType, sendImmediately, mandatory, endReason, senderEmailId));
    }

    @When("I fill out OutBound Correspondence Configuration fields")
    public void i_fill_out_OutBound_Correspondence_Configuration_fields() {
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
    }

    @When("I edit and fill out OutBound Correspondence Configuration fields")
    public void i_edit_and_fill_out_OutBound_Correspondence_Configuration_fields() {
        outboundCorrespondenceSettingsStepDefs.get().ifEditButtonThenClick();
        outboundCorrespondenceSettingsStepDefs.get().createRandomValues();
        outboundCorrespondenceSettingsStepDefs.get().fillOutUIFields();
    }

    @When("I save changes in Configuration screen")
    public void i_save_changes_in_Configuration_screen() {
        dPBIOC.get().clickSaveButtonVerifySuccessMessage();
    }

    @When("I click on given CorrespondenceDefinition ID {string}")
    public void i_click_on_given_CorrespondenceDefinition_ID(String string) {
        uiAutoUitilities.get().searchForOutboundDefinitionByID(string);
    }

    @When("I update existing Correspondence Definition without a {string}")
    public void i_update_existing_Correspondence_Definition_without_a(String string) {
        dPBIOC.get().clickCorrDefIDFirst();
        BrowserUtils.waitFor(2);
        outboundCorrespondenceDefinitionStepDefs.get().fillRandomOutboundCorrespondenceDefUI(string);
    }

    @When("I search {string} in the Get Events endpoint for DBPI")
    public void i_search_in_the_Get_Events_endpoint_for_DBPI(String eventName) throws IOException, ParseException {
        accessToken.set(outCorr.get().getAccessToken());
        String searchQAEvent = outCorr.get().searchQAEvents(accessToken.get(), eventName).replaceAll("\\\\", "");
        switch (eventName) {
            case "PROJECT_ROLE_SAVE_EVENT":
            case "PROJECT_ROLE_UPDATE_EVENT":
                for (int i = 0; i <= givenInfo.get().size() - 1; i++) {
                    Assert.assertTrue(searchQAEvent.toLowerCase().contains(givenInfo.get().get(i).toLowerCase()));
                }
                Assert.assertTrue(searchQAEvent.toLowerCase().contains(startEndDates.get().get(1).replaceAll("/", "-")));
                break;
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT":
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT":
                for (int i = 0; i <= givenInfo.get().size() - 1; i++) {
                    Assert.assertTrue(searchQAEvent.toLowerCase().contains(givenInfo.get().get(i).toLowerCase()));
                }
                Assert.assertTrue(searchQAEvent.toLowerCase().contains(startEndDates.get().get(1).replaceAll("/", "-")));
                Assert.assertTrue(searchQAEvent.toLowerCase().contains(startEndDates.get().get(3).replaceAll("/", "-")));
                break;
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT":
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().Name));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().ID));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().description));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().endReason));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().bodyDataSoource));
                ArrayList<String> startEndDates = dPBIOC.get().startEndDatesFormatForEventPayload(World.getWorld().outboundCorrespondenceDefinition.get().startDate, World.getWorld().outboundCorrespondenceDefinition.get().endDate);
                Assert.assertTrue(searchQAEvent.contains(startEndDates.get(0).replaceAll("/", "-")));
                Assert.assertTrue(searchQAEvent.contains(startEndDates.get(1).replaceAll("/", "-")));
                break;
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT":
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().Name));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().ID));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().description));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceDefinition.get().bodyDataSoource));
                ArrayList<String> startEndDates1 = dPBIOC.get().startEndDatesFormatForEventPayload(World.getWorld().outboundCorrespondenceDefinition.get().startDate, World.getWorld().outboundCorrespondenceDefinition.get().endDate);
                Assert.assertTrue(searchQAEvent.contains(startEndDates1.get(0).replaceAll("/", "-")));
                Assert.assertTrue(searchQAEvent.contains(startEndDates1.get(1).replaceAll("/", "-")));
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT":
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().defaultLanguage));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().otherLanguages));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondence));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondenceTemplate));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorContactName));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorPhone));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorEmail));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpHost));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpUser));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpPassword));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpPort));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpFolder));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpHost));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpUser));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpPassword));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpPort));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName));
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT":
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().defaultLanguage));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondence));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondenceTemplate));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorContactName));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorPhone));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().vendorEmail));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpHost));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpUser));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpPassword));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpPort));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().ftpFolder));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpHost));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpUser));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpPassword));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpPort));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail));
                Assert.assertTrue(searchQAEvent.contains(World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName));
                break;
        }
    }

    @When("I return to the Project List page")
    public void i_return_to_the_Project_List_page() {
        highLightElement(tmListOfProjectsPage.returnToProjectSearch);
        staticWait(1000);
        tmListOfProjectsPage.returnToProjectSearch.click();
    }

//    @When("I search for the Newly Created Project")
//    public void i_search_for_the_Newly_Created_Project() {
//        String projectName1 = TMUtilities.projectName1;
//        System.out.println(projectName1);
//        List<WebElement> li = driver.findElements(By.xpath("//input[contains(@class, 'MuiInputBase-input MuiOutlinedInput-input')]"));
//        li.get(1).sendKeys(projectName1);
//        waitFor(4);
//        driver.findElement(By.xpath("//li[contains(@id, 'react-autowhatever-1--item-0')]")).click();
//        waitFor(4);
//
//        driver.findElement(By.xpath("//span[contains(@class, 'MuiButton-label')]")).click();
//    }

    public void searchEvents(String key, String eventName) {
        String samplePayload;
        apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiClassUtil.setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
        apiClassUtil.setEndPoint("/app/crm/events");
//            samplePayload=
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
//        jsonObject.addProperty("createdOn", World.save.get().get("createdOn"));
        jsonObject.addProperty(key, World.save.get().get(key));
        events.set ((Response)apiClassUtil.PostAPIWithParameter(jsonObject).responseBody);
        Assert.assertEquals(200, events.get().statusCode());
    }


    @Given("that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data")
    public void thatIHaveCreatedRandomOutboundCorrespondenceThroughThePostCorrespondenceEndpointWithBodyData() {
        String rawBody = outCorr.get().rawBodyCreateCorrespondenceWithBodyData(bodySetName.get(), bodyItemName.get(), bodyItemValue.get(), bodySubsetName.get(), bodySubsetItemName.get(), bodySubsetItemValue.get());
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), rawBody));
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(),endpointID.get());
        endpointNID.set(outCorr.get().getCorrespondence(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), endpointID.get()));
        if (endpointNID.get().contains("[[")) {
            endpointNID.set(endpointNID.get().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
    }

    @Given("that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and recipients as null")
    public void thatIHaveCreatedRandomOutboundCorrespondenceThroughThePostCorrespondenceEndpointWithBodyDatawithRecipNull() {
        String rawBody = outCorr.get().rawBodyCreateCorrespondenceWithBodyDataRecipNull(bodySetName.get(), bodyItemName.get(), bodyItemValue.get(), bodySubsetName.get(), bodySubsetItemName.get(), bodySubsetItemValue.get());
//        endpointID.get() = String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOBCorrespondence(rawBody));
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), rawBody));
        String cid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(endpointID.get()).getString("id");
        System.out.println(Hooks.nameAndTags.get() + " - Outbound Correspondence created - " + cid);
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(), cid);
    }

    @And("I call letter data by NID to get the full response")
    public void iCallLetterDataByNIDToGetTheFullResponse() {
        waitFor(15);
        String nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()))).getString("recipients[0].notifications.notificationId[0]");
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        NIDResponse.set(String.valueOf(response.prettify()));
    }

    @And("I verify created Body Data {string} that was sent")
    public void iVerifyCreatedBodyDataThatWasSent(String arg0) {
        switch (arg0) {
            case "full":
                Assert.assertTrue(NIDResponse.get().contains(bodySetName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemValue.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodySubsetName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodySubsetItemName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodySubsetItemValue.get()));
                break;
            case "NoBodySubsets":
                Assert.assertTrue(NIDResponse.get().contains(bodySetName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemValue.get()));
                break;
            case "NoBodySubsetItems":
                Assert.assertTrue(NIDResponse.get().contains(bodySetName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemName.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodyItemValue.get()));
                Assert.assertTrue(NIDResponse.get().contains(bodySubsetName.get()));
                break;
            default:
                Assert.fail("no matching cases");

        }
    }

    @Given("that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and no bodySubsets")
    public void thatIHaveCreatedRandomOutboundCorrespondenceThroughThePostCorrespondenceEndpointWithBodyDataAndNoBodySubsets() {
        String rawBody = outCorr.get().rawBodyCreateCorrespondenceWithBodyDataNoBodySubsets(bodySetName.get(), bodyItemName.get(), bodyItemValue.get());
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), rawBody));
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(),endpointID.get());
        endpointNID.set(outCorr.get().getCorrespondence(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), endpointID.get()));
        if (endpointNID.get().contains("[[")) {
            endpointNID.set(endpointNID.get().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
    }

    @Given("that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and no bodySubsetItems")
    public void thatIHaveCreatedRandomOutboundCorrespondenceThroughThePostCorrespondenceEndpointWithBodyDataAndNoBodySubsetItems() {
        String rawBody = outCorr.get().rawBodyCreateCorrespondenceWithBodyDataNobodySubsetItems(bodySetName.get(), bodyItemName.get(), bodyItemValue.get(), bodySubsetName.get());
        endpointID.set(outCorr.get().createCorrespondenceRecipientsID(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), rawBody));
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(),endpointID.get());
        endpointNID.set(outCorr.get().getCorrespondence(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), endpointID.get()));
        if (endpointNID.get().contains("[[")) {
            endpointNID.set(endpointNID.get().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
    }

    @And("I update previous notification")
    public void iUpdatePreviousNotification(List<String> statuses) {
        if(notificationIds.get().size()<1){
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
            JsonPath responseCid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
            DPBIOutboundCorrespondenceConfigEventsSteps.notificationIds.set(responseCid.getList("recipients[0].notifications.notificationId"));
        }
        if (statuses.size() == notificationIds.get().size()) {
            for (int i = 0; i <= statuses.size() - 1; i++) {
                updatedInfo.get().put("NID" + i, notificationIds.get().get(i));
                updatedInfo.get().put("status" + i, statuses.get(i));
                String rawBody = outCorr.get().rawBodyUpdateNotificationStatus(statuses.get(i));
                String response = outCorr.get().updateNotificationStatus(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal(), notificationIds.get().get(i), rawBody);
            }
        } else {
            Assert.assertTrue(false, "NotificationIds size doesn't match status sized to update");
        }

    }

    @And("I Verify Notification Statuses have been updated")
    public void iVerifyNotificationStatusesHaveBeenUpdated() {
        String presentTimeStamp = apitdu.getCurrentDateAndTime("yyyy-MM-dd'T'");
        synchronized (getCID){
            getCID.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(currentCID.get()));
        }
        List<Map<String, String>> getCIDUpdatedInfo = getCID.get().getList("recipients[0].notifications.notificationStatus");
        for (int i = 0; i <= getCIDUpdatedInfo.size() - 1; i++) {
            Assert.assertTrue(getCIDUpdatedInfo.get(i).containsValue(updatedInfo.get().get("status" + i)));
            Assert.assertTrue(getCIDUpdatedInfo.get(i).containsValue(updatedInfo.get().get("set by postman")));
            Assert.assertTrue(getCIDUpdatedInfo.get(i).containsValue(updatedInfo.get().get(presentTimeStamp)));
        }
    }
}