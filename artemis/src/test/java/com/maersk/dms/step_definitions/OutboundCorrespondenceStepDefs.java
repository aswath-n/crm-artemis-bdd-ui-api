package com.maersk.dms.step_definitions;

import com.github.javafaker.Faker;
import com.google.gson.*;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.beans.outboundCorrespondence.*;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.maersk.crm.utilities.World.*;
import static com.maersk.dms.utilities.APIAutoUtilities.cucumberOutlineCounter;
//import static com.maersk.dms.utilities.getAPIAutoUtilities().cucumberOutlineCounter;

public class OutboundCorrespondenceStepDefs extends EventBaseClass {
    private String baseURI = ConfigurationReader.getProperty("apiDMSCorrespondence");
    private APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    private APIClassUtil apiClassUtilGet = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<String> correspondenceEndPoint = ThreadLocal.withInitial(() -> "/correspondences");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<Faker> faker = ThreadLocal.withInitial(Faker::new);
    private APIAutoUtilities apiAuto = APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private ApiTestDataUtil sendEventTdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<String> correspondenceId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> matchedEventIDs = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> adjusted = ThreadLocal.withInitial(String::new);
    private ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private CreateOutboundCorrespondencePage createOutboundCorrespondencePage = new CreateOutboundCorrespondencePage();
    private final static ThreadLocal<Map<String, String>> expectedBodyDataElement = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<Map<String, String>> recipientMap = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<List<String>> channelList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> requesterId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> createdTime = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    final ThreadLocal<String> stateCode = ThreadLocal.withInitial(String::new);
    final ThreadLocal<JsonPath> getLetterResponse = new ThreadLocal<>();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    /**
     * Saves values to Outbound Correspondence bean. Add cases to switch as necessary.
     *
     * @param dataTable to be send in key value pair from cucumber scenario step
     */
    public void saveDataTableValuesToWorld(Map<String, String> dataTable) {
        getWorld().outboundCorrespondenceBean.get().requester = new Requester();
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "correspondenceDefinitionMMSCode":
                    getWorld().outboundCorrespondenceBean.get().correspondenceDefinitionMMSCode = dataTable.get("correspondenceDefinitionMMSCode");
                    break;
                case "language":
                    getWorld().outboundCorrespondenceBean.get().language = dataTable.get("language");
                    break;
                case "caseId":
                    getWorld().outboundCorrespondenceBean.get().caseId = dataTable.get("caseId");
                    break;
                case "requesterId":
                    getWorld().outboundCorrespondenceBean.get().requester.requesterId = dataTable.get("requesterId");
                    break;
                case "requesterType":
                    getWorld().outboundCorrespondenceBean.get().requester.requesterType = dataTable.get("requesterType");
                    break;
                case "consumerId":
                    getWorld().outboundCorrespondenceBean.get().consumerIdList.add(dataTable.get("consumerId"));
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing case, cases must match keys in table | " + "dataTable value = " + data);
            }
        }
    }

    /**
     * Adds values to json. Add cases to switch as necessary
     *
     * @param dataTable to be sent in key value pair from cucumber scenario step
     */
    public void addDataTableValuesToJson(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "correspondenceDefinitionMMSCode":
                    String type = dataTable.get("correspondenceDefinitionMMSCode");
                    if ("default".equalsIgnoreCase(dataTable.get(data))) {
                        type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
                    }
                    apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", type);
                    break;
                case "language":
                    apitdu.jsonElement.getAsJsonObject().addProperty("language", dataTable.get("language"));
                    break;
                case "channelType":
                    apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("channelType", dataTable.get(data));
                    break;
                case "caseId":
                    String caseId = dataTable.get("caseId");
                    if ("Random".equalsIgnoreCase(dataTable.get("caseId"))) {
                        caseId = RandomStringUtils.random(5, false, true);
                        generalSave.get().put("caseId", caseId);
                    } else if ("previouslyCreated".equalsIgnoreCase(dataTable.get("caseId"))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseId = caseConsumerId.get("caseId");
                        Assert.assertTrue(caseId.length() > 0, "case Id length not greater than zero");
                        generalSave.get().put("caseId", caseId);
                    } else if ("null".equalsIgnoreCase(dataTable.get("caseId"))) {
                        break;
                    }
                    apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", caseId);
                    break;
                case "requesterId":
                    addRequesterId(dataTable.get(data));
                    break;
                case "faxNumber":
                    apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("faxNumber", dataTable.get(data));
                    break;
                case "requesterType":
                    addRequesterType(dataTable.get(data));
                    break;
                case "regardingConsumerId":
                    String regardingConsumerId = dataTable.get("regardingConsumerId");
                    int count = 0;
                    if (regardingConsumerId.contains(",")) {
                        count = regardingConsumerId.split(",").length;
                        regardingConsumerId = "";
                        for (int index = count; index > 0; index--) {
                            if (index == 1) {
                                regardingConsumerId = regardingConsumerId + RandomStringUtils.random(5, false, true);
                            } else {
                                regardingConsumerId = regardingConsumerId + RandomStringUtils.random(5, false, true) + ",";
                            }
                        }
                        generalSave.get().put("regardingConsumerId", regardingConsumerId);
                        String[] temp = regardingConsumerId.split(",");
                        for (String s : temp) {
                            apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(s);
                        }
                    } else if ("Random".equalsIgnoreCase(dataTable.get("regardingConsumerId"))) {
                        regardingConsumerId = RandomStringUtils.random(5, false, true);
                        generalSave.get().put("regardingConsumerId", regardingConsumerId);
                        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
                    } else if ("previouslyCreated".equalsIgnoreCase(dataTable.get("regardingConsumerId"))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        regardingConsumerId = caseConsumerId.get("consumerId");
                        generalSave.get().put("regardingConsumerId", regardingConsumerId);
                        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
                    } else if (dataTable.get("regardingConsumerId").startsWith("invalid")) {
                        List<String> invalidValues = Arrays.asList(null, "", RandomStringUtils.random(5, true, false));
                        if (dataTable.get("regardingConsumerId").contains(";")) {
                            int iteration = regardingConsumerId.split(";").length;
                            for (int i = 0; i < iteration; i++) {
                                regardingConsumerId = invalidValues.get(new Random().nextInt(invalidValues.size()));
                                apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
                            }
                        } else {
                            regardingConsumerId = invalidValues.get(new Random().nextInt(invalidValues.size()));
                            apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
                        }
                    }

                    break;
                case "consumerId":
                    apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("consumerId", dataTable.get(data));
                    break;
                case "bodyData":
                    String[] bodyDataElements = dataTable.get("bodyData").split(",");
                    JsonObject jsonBodyElements = new JsonObject();
                    for (String bodyElement : bodyDataElements) {
                        String randomValue = RandomStringUtils.random(10, true, false);
                        expectedBodyDataElement.get().put(bodyElement, randomValue);
                        jsonBodyElements.addProperty(bodyElement, randomValue);
                    }
                    generalSave.get().put("expectedBodyDataElement.", expectedBodyDataElement.get());

                    apitdu.jsonElement.getAsJsonObject().add("bodyData", jsonBodyElements);
                    break;
                case "address":
                    if ("random".equalsIgnoreCase(dataTable.get(data))) {
                        apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("streetAddress", faker.get().address().streetAddress());
                        apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("streetAddionalLine1", "");
                        apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("city", faker.get().address().city());
                        apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", faker.get().address().stateAbbr());
                        apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("zipcode", faker.get().address().zipCode().substring(0, 5));
                    }
                    break;
                case "applicationID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get("applicationID"))) {
                        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("applicationId", save.get().get("appID"));
                    } else if ("previouslyCreatedATS".equalsIgnoreCase(dataTable.get("applicationID"))) {
                        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("applicationId", APIATSApplicationController.applicationIdAPI.get());
                    } else
                        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("applicationId", dataTable.get("applicationID"));
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing case, cases must match keys in table | " + "dataTable value = " + data);
            }
        }
    }

    /**
     * sets request params and sets endpoint. Add cases to switch as necessary
     *
     * @param endPoint - sets endpoint
     */
    public void setEndPoint(String endPoint) {
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        apiClassUtilGet.setbaseUri(baseURI);
        switch (endPoint) {
            case "getCorrespondence":
                endpoint.set(correspondenceEndPoint.get() + "/" + getWorld().outboundCorrespondenceBean.get().correspondenceId);
                apiClassUtilGet.setbaseUri(baseURI + correspondenceEndPoint.get());
                apiClassUtilGet.setEndPoint(getWorld().outboundCorrespondenceBean.get().correspondenceId);

                break;
            case "correspondence":
                apiClassUtilGet.setbaseUri(baseURI + correspondenceEndPoint.get());
                apiClassUtilGet.setEndPoint(correspondenceEndPoint.get());
                break;
            default:
                throw new NotImplementedException("Endpoint did not match an existing case, cases must match endpoint | " + "endpoint value = " + endPoint);

        }
    }

    public void setEndPoint() {
        apiClassUtil.setbaseUri(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceOrder);
        apiClassUtil.setEndPoint(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondences);
    }

    public void sendPostRequest() {
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        apiClassUtil.jsonPathEvaluator = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondence(requestParams.get(), ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
    }

    public void sendGetRequest() {
        apiClassUtilGet.getAPI();
        Assert.assertEquals(apiClassUtilGet.statusCode, 200);
    }

    public int getStatusCodeFromResponse() {
        return apiClassUtil.statusCode;
    }

    /**
     * creates new world instance and OutboundCorrespondence bean instance
     */
    public void createOutboundCorrespondenceInstance() {
        createNewWorld();
        synchronized (getWorld().outboundCorrespondenceBean) {
            getWorld().outboundCorrespondenceBean.set(new OutboundCorrespondenceBean());
        }
    }

    public void startJsonFromCorrespondenceFile() {
        apitdu.getJsonFromFile("dms/correspondence2.json");
    }

    public void startJsonFromCorrespondenceFileNoBodyData() {
        apitdu.getJsonFromFile("dms/correspondence2NoBodyData.json");
    }

    public void startJsonFromCorrespondenceFile(String json) {
        apitdu.getJsonFromFile(json);
    }

    public void addMultipleConsumerIDSToJson() {
        apitdu.jsonElement.getAsJsonObject().get("regardingConsumerIds").getAsJsonArray().add("5162");
        apitdu.jsonElement.getAsJsonObject().get("regardingConsumerIds").getAsJsonArray().add("5151");
    }

    /**
     * creates empty notifications in notifications list
     *
     * @param notifications - how many notications to add to json
     */
    public void addMoreThanOneFaxNotification(int notifications) {
        for (int i = 0; i < notifications; i++) {
            apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").add(new JsonObject());
        }
    }

    /**
     * creates requesterId.get() and assigns value in Requester
     *
     * @param requesterId - value of requester Id
     */
    public void addRequesterId(String requesterId) {
        apitdu.jsonElement.getAsJsonObject().get("requester").getAsJsonObject().addProperty("requesterId", requesterId);
    }

    /**
     * creates requesterType and assigns value in Requester
     *
     * @param requesterType - value of requesterType
     */
    public void addRequesterType(String requesterType) {
        apitdu.jsonElement.getAsJsonObject().get("requester").getAsJsonObject().addProperty("requesterType", requesterType);
    }

    /**
     * adds fax channel with random values to json and saves them to Outbound Correspondence bean
     */
    public void addFaxChannelsToNotificaitonToJson() {
        generateRandomFaxDetails();
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("countryCode", getWorld().outboundCorrespondenceBean.get().fax.faxDetails.countryCode);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("faxNumber", getWorld().outboundCorrespondenceBean.get().fax.faxDetails.faxNumber);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("faxReferenceId", getWorld().outboundCorrespondenceBean.get().fax.faxDetails.faxReferenceId);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("toCompanyName", getWorld().outboundCorrespondenceBean.get().fax.faxDetails.toCompanyName);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("toName", getWorld().outboundCorrespondenceBean.get().fax.faxDetails.toName);
    }

    public void generateRandomFaxDetails() {
        getWorld().outboundCorrespondenceBean.get().fax = new fax();
        getWorld().outboundCorrespondenceBean.get().fax.faxDetails.countryCode = RandomStringUtils.random(3, false, true);
        getWorld().outboundCorrespondenceBean.get().fax.faxDetails.faxNumber = RandomStringUtils.random(10, false, true);
        getWorld().outboundCorrespondenceBean.get().fax.faxDetails.faxReferenceId = RandomStringUtils.random(3, false, true);
        getWorld().outboundCorrespondenceBean.get().fax.faxDetails.toCompanyName = RandomStringUtils.random(5, true, false);
        getWorld().outboundCorrespondenceBean.get().fax.faxDetails.toName = RandomStringUtils.random(5, true, false);
    }

    /**
     * adds mail channel with random values to json and saves them to Outbound Correspondence bean
     */
    public void addMailChannelsToNotificaitonToJson() {
        generateRandomMailDetails();
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("additionalLine", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.additionalLine);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("city", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.city);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("country", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.country);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("state", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.state);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("streetAddress", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.streetAddress);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("mail").getAsJsonObject("addressDetails").addProperty("zipCode", getWorld().outboundCorrespondenceBean.get().mail.addressDetails.zipCode);
    }

    public void generateRandomMailDetails() {
        getWorld().outboundCorrespondenceBean.get().mail = new mail();
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.additionalLine = "Apt " + RandomStringUtils.random(5, false, true);
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.city = faker.get().address().city();
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.country = "USA";
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.state = faker.get().address().state();
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.streetAddress = RandomStringUtils.random(4, false, true) + faker.get().address().streetAddress();
        getWorld().outboundCorrespondenceBean.get().mail.addressDetails.zipCode = RandomStringUtils.random(5, false, true);
    }

    /**
     * adds email channel with random values to json and saves them to Outbound Correspondence bean
     */
    public void addEmailChannelsToNotificaitonToJson() {
        generateRandomEmailDetails();
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("email").getAsJsonObject("emailDetails").getAsJsonArray("emailAddresses").add(getWorld().outboundCorrespondenceBean.get().email.emailDetails.emailAddresses.get(0));
    }

    public void generateRandomEmailDetails() {
        getWorld().outboundCorrespondenceBean.get().email = new email();
        getWorld().outboundCorrespondenceBean.get().email.emailDetails.emailAddresses.add(RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(7, true, false) + ".com");
    }

    /**
     * adds textmessage channel with random values to json and saves them to Outbound Correspondence bean
     */
    public void addTextChannelsToNotificaitonToJson() {
        generateRandomTextDetails();
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("textMessage").getAsJsonObject("textNumberDetails").addProperty("countryCode", getWorld().outboundCorrespondenceBean.get().textMessage.textNumberDetails.countryCode);
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("textMessage").getAsJsonObject("textNumberDetails").addProperty("textNumber", getWorld().outboundCorrespondenceBean.get().textMessage.textNumberDetails.textNumber);
    }

    public void generateRandomTextDetails() {
        getWorld().outboundCorrespondenceBean.get().textMessage = new textMessage();
        getWorld().outboundCorrespondenceBean.get().textMessage.textNumberDetails.countryCode = RandomStringUtils.random(3, false, true);
        getWorld().outboundCorrespondenceBean.get().textMessage.textNumberDetails.textNumber = RandomStringUtils.random(10, false, true);
    }

    /**
     * add multiple channels to notification
     *
     * @param channels - what channels to add, must match cases in switch case
     */
    public void addChannelsToNotificaitonToJson(List<String> channels) {
        for (String channel :
                channels) {
            switch (channel) {
                case "Mail":
                    addMailChannelsToNotificaitonToJson();
                    break;
                case "Email":
                    addEmailChannelsToNotificaitonToJson();
                    break;
                case "Text":
                    addTextChannelsToNotificaitonToJson();
                    break;
                case "Fax":
                    addFaxChannelsToNotificaitonToJson();
                    break;
                default:
                    throw new NotImplementedException("Channel did not match an existing case, cases must match channels in list | " + "List value = " + channel);
            }
        }
    }

    /**
     * creates random values for Outbound Correspondence and saves to bean
     */
    public void createValidRandomValuesOutboundCorrespondence() {
        getWorld().outboundCorrespondenceBean.get().caseId = RandomStringUtils.random(4, false, true);
        apitdu.jsonElement.getAsJsonObject().addProperty("caseId", getWorld().outboundCorrespondenceBean.get().caseId);
        getWorld().outboundCorrespondenceBean.get().correspondenceDefinitionMMSCode = "Outbound";
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", getWorld().outboundCorrespondenceBean.get().correspondenceDefinitionMMSCode);
        getWorld().outboundCorrespondenceBean.get().language = "English";
        apitdu.jsonElement.getAsJsonObject().addProperty("language", getWorld().outboundCorrespondenceBean.get().language);
        getWorld().outboundCorrespondenceBean.get().status = "RECEIVED";
        apitdu.jsonElement.getAsJsonObject().addProperty("status", getWorld().outboundCorrespondenceBean.get().status);
        getWorld().outboundCorrespondenceBean.get().consumerIdList.add(RandomStringUtils.random(4, false, true));
        apitdu.jsonElement.getAsJsonObject().get("consumerIdList").getAsJsonArray().add(getWorld().outboundCorrespondenceBean.get().consumerIdList.get(0));
        getWorld().outboundCorrespondenceBean.get().requester = new Requester();
        getWorld().outboundCorrespondenceBean.get().requester.requesterId = RandomStringUtils.random(4, false, true);
        addRequesterId(getWorld().outboundCorrespondenceBean.get().requester.requesterId);
        getWorld().outboundCorrespondenceBean.get().requester.requesterType = RandomStringUtils.random(4, true, false);
        addRequesterType(getWorld().outboundCorrespondenceBean.get().requester.requesterType);
        getWorld().outboundCorrespondenceBean.get().status = "RECEIVED";
        addChannelsToNotificaitonToJson(Arrays.asList("Mail", "Email", "Text", "Fax"));
    }

    /**
     * save correspondence id to bean
     */
    public void saveCorrespondenceId() {
        getWorld().outboundCorrespondenceBean.get().correspondenceId = apiClassUtil.jsonPathEvaluator.get("data.id");
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), getWorld().outboundCorrespondenceBean.get().correspondenceId);

    }

    public void saveDateTime() {
        getWorld().outboundCorrespondenceBean.get().creationDate = BrowserUtils.timeNow();
    }

    public void verifyCorrespondenceIdInResponse() {
        Assert.assertEquals(getWorld().outboundCorrespondenceBean.get().correspondenceId, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("data.id"));
    }

    public void verifySuccessStatusInResponse() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode(), 201);
    }

    public void verifyDateTimeStored() {
        Assert.assertTrue(EventsUtilities.genericValidator(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("data.createdDatetime"), "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"), "CreatedDateTime Value - " + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("data.createdDateTime") + " | ");
    }

    public void verifyStatusInResponse() {
        Assert.assertTrue(apiClassUtilGet.jsonPathEvaluator.get("status").toString().equalsIgnoreCase("Requested"));
//        Assert.assertTrue(getWorld().response.get().body().print().contains("New"));
    }

    /**
     * verify correspondence values from response.get() with values in bean
     *
     * @param dataTable - values to be verified
     */
    public void verifyOutboundCorrespondenceValues(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "correspondenceDefinitionMMSCode":
                    String type = dataTable.get("correspondenceDefinitionMMSCode");
                    if ("default".equalsIgnoreCase(dataTable.get(data))) {
                        type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
                    }
                    Assert.assertEquals(type, apiClassUtilGet.jsonPathEvaluator.get("correspondenceDefinitionMMSCode").toString());
                    break;
                case "language":
                    Assert.assertEquals(dataTable.get("language"), apiClassUtilGet.jsonPathEvaluator.get("language").toString());
                    break;
                case "caseId":
                    String caseId = dataTable.get("caseId");
                    if ("Random".equalsIgnoreCase(dataTable.get("caseId"))) {
                        caseId = String.valueOf(generalSave.get().get("caseId"));
                    } else if ("previouslyCreated".equalsIgnoreCase(dataTable.get("caseId"))) {
                        caseId = String.valueOf(generalSave.get().get("caseId"));
                    }
                    Assert.assertEquals(caseId, apiClassUtilGet.jsonPathEvaluator.get("anchor.caseId").toString());
                    break;
                case "consumerId":
                    Assert.assertEquals(dataTable.get(data), apiClassUtilGet.jsonPathEvaluator.get("recipients[0].recipientInfo.consumerId").toString());
                    break;
                case "requesterId":
                    Assert.assertEquals(dataTable.get("requesterId"), apiClassUtil.jsonPathEvaluator.get("requester.requesterId.").toString());
                    break;
                case "requesterType":
                    Assert.assertEquals(dataTable.get("requesterType"), apiClassUtil.jsonPathEvaluator.get("requester.requesterType").toString());
                    break;
                case "regardingConsumerId":
                    String regardingConsumerId = dataTable.get("regardingConsumerId");
                    if ("Random".equalsIgnoreCase(dataTable.get("regardingConsumerId"))) {
                        regardingConsumerId = String.valueOf(generalSave.get().get("regardingConsumerId"));
                        Assert.assertEquals(regardingConsumerId, validNumberFilter(apiClassUtilGet.jsonPathEvaluator.get("anchor.regardingConsumerId[0]").toString()));
                    } else if (regardingConsumerId.contains(",")) {
                        regardingConsumerId = String.valueOf(generalSave.get().get("regardingConsumerId"));
                        String[] consumers = regardingConsumerId.split(",");
                        List<String> expected = new ArrayList<>();
                        expected.addAll(Arrays.asList(consumers));
                        List<String> actual = apiClassUtilGet.jsonPathEvaluator.getList("anchor.regardingConsumerId");
                        Collections.sort(expected);
                        Collections.sort(actual);
                        for (int index = 0; index < actual.size(); index++) {
                            Assert.assertEquals(actual, expected);
                        }
                    }
                    break;
                case "channelType":
                    Assert.assertEquals(dataTable.get("channelType"), apiClassUtilGet.responseBody.jsonPath().getList("recipients.notifications[0].channelType").get(0));
                    break;
                case "faxNumber":
                    Assert.assertEquals(validNumberFilter(dataTable.get("faxNumber")), validNumberFilter(apiClassUtilGet.jsonPathEvaluator.get("recipients.recipientInfo.faxNumber").toString()));
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing case, cases must match keys in table | " + "dataTable value = " + data);
            }
        }

    }

    public void addNotificationValuesToOutboundCorrespondence(Map<String, String> dataTable) {
        switch (dataTable.get("channel")) {
            case "fax":
                addFaxValuesToNotificaitonToJson(dataTable);
                break;
        }
    }

    /**
     * add fax to notification with specific values
     *
     * @param dataTable - values of fax channel
     */
    public void addFaxValuesToNotificaitonToJson(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "channel":
                    break;
                case "countryCode":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("countryCode", dataTable.get("countryCode"));
                    break;
                case "faxNumber":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("faxNumber", dataTable.get("faxNumber"));
                    break;
                case "faxReferenceId":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("faxReferenceId", dataTable.get("faxReferenceId"));
                    break;
                case "toCompanyName":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("toCompanyName", dataTable.get("toCompanyName"));
                    break;
                case "toName":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().getAsJsonObject("channel").getAsJsonObject("fax").getAsJsonObject("faxDetails").addProperty("toName", dataTable.get("toName"));
                    break;
                case "consumerId":
                    apitdu.jsonElement.getAsJsonObject().getAsJsonArray("notificationList").get(0).getAsJsonObject().addProperty("consumerId", dataTable.get("consumerId"));
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing case, cases must match keys in table | " + "dataTable value = " + data);
            }
        }
    }

    public void verifyNotificationValuesStored(Map<String, String> dataTable) {
        switch (dataTable.get("channelType").toLowerCase()) {
            case "fax":
                verifyFaxValuesOfNotificationToJson(dataTable);
                break;
            case "mail":
                verifyFaxValuesOfNotificationToJson(dataTable);
                break;
            case "email":
                verifyFaxValuesOfNotificationToJson(dataTable);
                break;
            case "text":
                verifyFaxValuesOfNotificationToJson(dataTable);
                break;
            default:
                Assert.fail("No such channel type - " + dataTable.get("channelType"));
        }
    }

    /**
     * verify fax values of notification
     *
     * @param dataTable - values to verify from response.get() body
     */
    public void verifyFaxValuesOfNotificationToJson(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "channelType":
                    Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().getList("data.recipients.notifications[0].channelType").get(0), dataTable.get("channelType"));
                    break;
                case "countryCode":
                    Assert.assertEquals(dataTable.get(data), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("recipients.notifications[0].channel.fax.faxDetails.countryCode"));
                    break;
                case "faxNumber":
                    Assert.assertEquals(
                            validNumberFilter(apiClassUtil.jsonPathEvaluator.get("data.recipients.recipientInfo.faxNumber").toString()), dataTable.get("faxNumber"));
                    break;
                case "faxReferenceId":
                    Assert.assertEquals(dataTable.get(data), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("recipients.notifications[0].channel.fax.faxDetails.faxReferenceId"));
                    break;
                case "toCompanyName":
                    Assert.assertEquals(dataTable.get(data), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("recipients.notifications[0].channel.fax.faxDetails.toCompanyName"));
                    break;
                case "toName":
                    Assert.assertEquals(dataTable.get(data), API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("recipients.notifications[0].channel.fax.faxDetails.toName"));
                    break;
                case "consumerId":
                    Assert.assertEquals(
                            validNumberFilter(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("data.recipients.recipientInfo.consumerId").toString()), dataTable.get("consumerId"));
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing case, cases must match keys in table | " + "dataTable value = " + data);
            }
        }
    }

    public void nocorrespondenceDefinitionMMSCodeFromJson() {
        apitdu.jsonElement.getAsJsonObject().addProperty("language", "");
        apitdu.jsonElement.getAsJsonObject().addProperty("caseId", "");
        apitdu.jsonElement.getAsJsonObject().add("requester", new Gson().toJsonTree(new Requester()));
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceId", "");
    }

    public void noLanguageFromJson() {
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "");
        apitdu.jsonElement.getAsJsonObject().addProperty("caseId", "");
        apitdu.jsonElement.getAsJsonObject().add("requester", new Gson().toJsonTree(new Requester()));
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceId", "");
    }

    public void verifyBadRequest() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode(), 400);
    }

    public void verifyBadRequestDetails(Map<String, String> map) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode(), 400);
        int errorSize = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().getInt("errors.size");
        Assert.assertTrue(map.get("status").equals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("status").toString()));
        List<String> actualErrors = new ArrayList<>();
        List<String> expectedErrors = Arrays.stream(map.get("message").split(" AND ")).map(String::valueOf).collect(Collectors.toList());
        for (int i = 0; i < errorSize; i++) {
            actualErrors.add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.jsonPath().get("errors[" + i + "].message").toString().trim());
        }
        System.out.println(actualErrors);
        System.out.println(expectedErrors);
        Assert.assertTrue(actualErrors.containsAll(expectedErrors));
    }

    public void addProjectId(String projectName) {
        int projectNum = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().parseProjectNumber(projectName);
        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("projectId", projectNum);
    }

    public void addMMSCode() {
        int projectNum = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().parseProjectNumber(ConfigurationReader.getProperty("projectName"));
//        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", "teste");
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());

    }

    public void addRequiredKeys(String mmsId) {
        apiAuto.addRequiredKeysBaseOnTypeToRequest(mmsId, apitdu.jsonElement.getAsJsonObject());
    }

    public void addRequiredKeys(String mmsId, JsonObject jsonObject) {
        apiAuto.addRequiredKeysBaseOnTypeToRequest(mmsId, jsonObject);
    }


    public void addChannelDefIdForNotification(int notificationIndex) {
        int channId = Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getActiveChannelIds("Mail").get("Mail"));
        apitdu.jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(notificationIndex).getAsJsonObject().addProperty("channelDefinitionId", channId);

    }

    public void sendoutboundWithOnerecipient() {
        requestParams.set(sendEventTdu.getJsonFromFile("dms/correspondenceWithOneRecipient.json").jsonElement.getAsJsonObject());
        requestParams.get().getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
        String caseId = "";
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        caseId = caseConsumerId.get("caseId");
        generalSave.get().put("caseId", caseId);
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", caseId);
        String regardingConsumerId = RandomStringUtils.random(5, false, true);
        generalSave.get().put("regardingConsumerId", regardingConsumerId);
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
        String base = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/";
        String end = "correspondences";
        apiAuto.postOutboundCorrWithOneRecipient(base + end, requestParams.get().toString());
    }

    public void getCorrespondenceWithID() {

        correspondenceId.set(apiAuto.jsonPathEvaluator.get("data.id").toString());
        String base = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/correspondences/";
        String end = correspondenceId.get();
        apiAuto.getClassAPI(base + end);
        Assert.assertEquals(apiAuto.statusCode, 200);
    }

    public void getLetterDataByNId() {
        waitFor(3);
        String getNID = apiAuto.jsonPathEvaluator.get("recipients[0].notifications[0].notificationId").toString().replace("[", "").replace("]", "");
        String base = "https://mars-ecmcor-correspondenceorderservice-qa.apps.non-prod.pcf-maersk.com/notifications/";
        String end = getNID + "/letterdata";
        apiAuto.getClassAPI(base + end);
        Assert.assertEquals(apiAuto.statusCode, 200);
    }

    public void verifyBodyInLetterData() {
        Assert.assertNotNull(getLetterResponse.get().get("notification"), "Letter Body Data is Empty");
    }

    public void getEventIDfromCorrespondenceID(String eventType) {
        //getting EVENT ID by passing Correspondence ID
        matchedEventIDs.set(apiAuto.getEventId(eventType, correspondenceId.get()));
    }

    public void findEventId() {
        JsonPath eventByEventId = apiAuto.getEventByEventId(matchedEventIDs.get().get(0));
        Assert.assertTrue(apiAuto.getEventByEventId(matchedEventIDs.get().get(0)) != null, "No matching Event Id found");
        adjusted.set(eventByEventId.get("eventsList.content[0].payload").toString());
        adjusted.set(adjusted.get().replace("\"", "").replace("\\", ""));
    }

    public void verifyCreatedCorrIdWithEventId() {
        Assert.assertTrue(adjusted.get().contains(correspondenceId.get()));
    }

    public void sendoutboundWithNoRecipient() {
        requestParams.set(sendEventTdu.getJsonFromFile("dms/correspondenceWithNoRecipient.json").jsonElement.getAsJsonObject());
        requestParams.get().getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
        String caseId = "";
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        caseId = caseConsumerId.get("caseId");
        generalSave.get().put("caseId", caseId);
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId", caseId);
        String regardingConsumerId = RandomStringUtils.random(5, false, true);
        generalSave.get().put("regardingConsumerId", regardingConsumerId);
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(regardingConsumerId);
        String base = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/";
        String end = "correspondences";
        apiAuto.postOutboundCorrWithOneRecipient(base + end, requestParams.get().toString());
    }

    public void verifyNavigatedToOutboundDetailsPage(String cid) {
        BrowserUtils.waitFor(5);
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.viewOutboundCorrespondenceDetailsHeader.isDisplayed());
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().equalsIgnoreCase(cid));

    }

    public void selectOutboundtypeUI(String type) {
        browserUtils.get().selectDropDown(createOutboundCorrespondencePage.typeDropdown, type);
        waitFor(3);
    }

    public void selectRandomRecipientUI() {
        createOutboundCorrespondencePage.firstRecipient.click();
        waitFor(5);
    }

    public void selectRecipientUI(String recipient) {
        Driver.getDriver().findElement(By.xpath("(//*[contains(text(),'SEND TO')]/../following-sibling::div/div/following-sibling::div/div/ul/li/label/span/span/input)[" + recipient + "]")).click();
    }

    public void selectRandomChannelUI() {
        createOutboundCorrespondencePage.firstChannel.click();
        waitFor(5);
    }

    public void selectRandomChannelUI(String channel) {
        Driver.getDriver().findElement(By.xpath("(//*[@id='checkMail'])[" + channel + "]"));
    }

    public void selectOtherDestination() {
        browserUtils.get().selectDropDown(createOutboundCorrespondencePage.firstDestination, "Other");
        waitFor(4);

    }

    public void fillInFirstRecipRandomMailAddress() {
        createOutboundCorrespondencePage.firstAddressLine1.sendKeys(RandomStringUtils.random(3, false, true) + " " + RandomStringUtils.random(5, true, false));
        waitFor(1);
        browserUtils.get().selectRandomDropDownOption(createOutboundCorrespondencePage.firstCity);
        waitFor(1);
        browserUtils.get().selectRandomDropDownOption(createOutboundCorrespondencePage.firstState);
        waitFor(1);
        browserUtils.get().selectRandomDropDownOption(createOutboundCorrespondencePage.firstzip);
        waitFor(1);

    }

    public void verifyOutboundLinkedToTask() {
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        String taskId = String.valueOf(generalSave.get().get("TASKID"));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(cid);
        List<Map<String, String>> links = response.getList("externalLinkDetails.content");
        boolean found = false;
        for (Map<String, String> link : links) {
            if ("Task".equalsIgnoreCase(link.get("name"))) {
                found = true;
                Assert.assertEquals(taskId, String.valueOf(link.get("id")));
            }
        }
        Assert.assertTrue(found);
    }

    public String getTraceId() {
        String result = "";
        List<String> trid = EventsUtilities.getLogs("traceId", "notifications");
        for (int i = 0; i < trid.size(); i++) {
            System.out.println(" trace id at " + i + " position is : " + trid.get(i));
            if (!trid.get(i).isEmpty()) {
                result = trid.get(i);
                break;
            }
        }
        System.out.println(" result trace id is : " + result);
        return result;
    }


    public String getTraceId_correspondence() {
        String result = "";
        ArrayList<String> tridcollections = new ArrayList<String>();

        List<String> trid = EventsUtilities.getLogs("traceId", "correspondences");
        for (int i = 0; i < trid.size(); i++) {
            System.out.println(" trace id at " + i + " position is : " + trid.get(i));
            if (!trid.get(i).isEmpty()) {
                tridcollections.add(trid.get(i));
            }
        }
        result = tridcollections.get(tridcollections.size() - 3);
        System.out.println(" result trace id is : " + result);
        return result;
    }

    public void sendPostRequestAndSaveTraceID() {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apitdu.getJsonFromFile("dms/correspondence5.json");
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        apiClassUtil.jsonPathEvaluator = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondence(requestParams.get(), ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
    }

    public void verifyOutboundEvent(List<String> listOfEvents) {
        JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(World.save.get().get("PostTraceId"));
        waitFor(10);
        int eventsSize = jsonPath.getInt("events.size()");
        String eventType = "";
        String payload = "";
        String expectedRecordType = "";
        boolean isContainAllKeys = true;
        boolean isRecordTypeMatch = true;
        boolean isEmptyString = false;
        boolean isValidISOFormat = true;
        List<String> expectedKeysForOCSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "outboundCorrespondenceId", "definitionName", "caseId", "receivedPayload", "createdOn", "createdBy", "updatedOn", "updatedBy", "statusId", "statusMessage", "statusDatetime", "responseDueDate", "language", "anchor");
        List<String> expectedKeysForNotificationSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "notificationId", "channelDefinitionId", "createdBy", "createdOn", "language", "resendFlag", "returnDate", "statusChangeBy", "statusId", "statusDatetime", "statusMessage", "statusDetails", "channel", "updatedBy", "updatedOn", "objectReceivedParentFileId", "objectReceivedOn", "templateId");
        List<String> expectedKeysForCorRecipientSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "correspondenceRecipientId", "outboundCorrespondenceId", "consumerId", "createdOn", "createdBy", "updatedOn", "updatedBy", "firstName", "lastName", "addressLine1", "addressLine2", "addressCity", "addressStateAbbr", "addressZip", "emailAddress", "faxNumber", "textNumber");
        List<String> expectedKeysForLetterSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "letterDataId", "notificationId", "letterData", "processedDatetime", "createdBy", "createdOn", "updatedBy", "updatedOn");
        for (int i = 0; i < eventsSize; i++) {
            eventType = jsonPath.getString("events[" + i + "].eventName");
            switch (eventType) {
                case "OUTBOUND_CORRESPONDENCE_SAVE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "CorrespondenceOrderVO";
                    JsonPath saveOCEventJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForOCSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForOCSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(saveOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForOCSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = saveOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForOCSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(saveOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForOCSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForOCSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForOCSaveEvent.get(j).equals("createdOn") || expectedKeysForOCSaveEvent.get(j).equals("updatedOn") || expectedKeysForOCSaveEvent.get(j).equals("statusDatetime")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(saveOCEventJson.getString("dataObject." + expectedKeysForOCSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForOCSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(saveOCEventJson.getString("dataObject." + expectedKeysForOCSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + saveOCEventJson.getString("recordType"));
                    break;
                case "NOTIFICATION_SAVE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "NotificationOrderVO";
                    JsonPath saveNotificationJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForNotificationSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForNotificationSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(saveNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForNotificationSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = saveNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForNotificationSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(saveNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForNotificationSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForNotificationSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForNotificationSaveEvent.get(j).equals("createdOn") || expectedKeysForNotificationSaveEvent.get(j).equals("updatedOn") || expectedKeysForNotificationSaveEvent.get(j).equals("statusDatetime")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(saveNotificationJson.getString("dataObject." + expectedKeysForNotificationSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForNotificationSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(saveNotificationJson.getString("dataObject." + expectedKeysForNotificationSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + saveNotificationJson.getString("recordType"));
                    break;
                case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "CorrespondenceRecipientVO";
                    JsonPath saveCorRecipientJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForCorRecipientSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForCorRecipientSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(saveCorRecipientJson.getString(expectedKeysForCorRecipientSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForCorRecipientSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = saveCorRecipientJson.getString(expectedKeysForCorRecipientSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForCorRecipientSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(saveCorRecipientJson.getString(expectedKeysForCorRecipientSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForCorRecipientSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForCorRecipientSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForCorRecipientSaveEvent.get(j).equals("createdOn") || expectedKeysForCorRecipientSaveEvent.get(j).equals("updatedOn")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(saveCorRecipientJson.getString("dataObject." + expectedKeysForCorRecipientSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForCorRecipientSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(saveCorRecipientJson.getString("dataObject." + expectedKeysForCorRecipientSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + saveCorRecipientJson.getString("recordType"));
                    break;
                case "LETTER_DATA_SAVE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "LetterDataVO";
                    JsonPath saveLetterJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForLetterSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForLetterSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(saveLetterJson.getString(expectedKeysForLetterSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForLetterSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = saveLetterJson.getString(expectedKeysForLetterSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForLetterSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(saveLetterJson.getString(expectedKeysForLetterSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForLetterSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForLetterSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForLetterSaveEvent.get(j).equals("createdOn") || expectedKeysForLetterSaveEvent.get(j).equals("updatedOn") || expectedKeysForLetterSaveEvent.get(j).equals("processedDatetime")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(saveLetterJson.getString("dataObject." + expectedKeysForLetterSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForLetterSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(saveLetterJson.getString("dataObject." + expectedKeysForLetterSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + saveLetterJson.getString("recordType"));
                    break;
                case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "CorrespondenceOrderVO";
                    JsonPath updateOCEventJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForOCSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForOCSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(updateOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForOCSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = updateOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForOCSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(updateOCEventJson.getString(expectedKeysForOCSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForOCSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForOCSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForOCSaveEvent.get(j).equals("createdOn") || expectedKeysForOCSaveEvent.get(j).equals("updatedOn") || expectedKeysForOCSaveEvent.get(j).equals("statusDatetime")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(updateOCEventJson.getString("dataObject." + expectedKeysForOCSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForOCSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(updateOCEventJson.getString("dataObject." + expectedKeysForOCSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + updateOCEventJson.getString("recordType"));
                    break;
                case "NOTIFICATION_UPDATE_EVENT":
                    payload = jsonPath.getString("events[" + i + "].payload");
                    expectedRecordType = "NotificationOrderVO";
                    JsonPath updateNotificationJson = new JsonPath(payload);
                    for (int j = 0; j < expectedKeysForNotificationSaveEvent.size(); j++) {
                        if (!payload.contains(expectedKeysForNotificationSaveEvent.get(j))) {
                            isContainAllKeys = false;
                            break;
                        }
                        if (j < 5) {
                            if (EventsUtilities.isValueEmptyString(updateNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)))) {
                                isEmptyString = true;
                                break;
                            }
                            if (expectedKeysForNotificationSaveEvent.get(j).equals("recordType")) {
                                isRecordTypeMatch = updateNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)).equals(expectedRecordType);
                            }
                            if (expectedKeysForNotificationSaveEvent.get(j).equals("eventCreatedOn")) {
                                isValidISOFormat = EventsUtilities.isValidDateZulu(updateNotificationJson.getString(expectedKeysForNotificationSaveEvent.get(j)));
                                Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForNotificationSaveEvent.get(j));
                            }
                        } else {
                            if (!expectedKeysForNotificationSaveEvent.get(j).equals("dataObject")) {
                                if (expectedKeysForNotificationSaveEvent.get(j).equals("createdOn") || expectedKeysForNotificationSaveEvent.get(j).equals("updatedOn") || expectedKeysForNotificationSaveEvent.get(j).equals("statusDatetime")) {
                                    isValidISOFormat = EventsUtilities.isValidDate(updateNotificationJson.getString("dataObject." + expectedKeysForNotificationSaveEvent.get(j)));
                                    Assert.assertTrue(isValidISOFormat, "Invalid format for " + expectedKeysForNotificationSaveEvent.get(j));
                                }
                                if (EventsUtilities.isValueEmptyString(updateNotificationJson.getString("dataObject." + expectedKeysForNotificationSaveEvent.get(j)))) {
                                    isEmptyString = true;
                                    break;
                                }
                            }
                        }
                    }
                    Assert.assertFalse(isEmptyString, "Some of the fields are empty String");
                    Assert.assertTrue(isContainAllKeys, "Some fields are missed in Json");
                    Assert.assertTrue(isRecordTypeMatch, "Record type verification failed for " + eventType + " expected - " + expectedRecordType + " actual - " + updateNotificationJson.getString("recordType"));
                    break;
            }
        }
    }

    public void verifyBodyDataStructureInResponse(Integer expectedVersion) {
        Assert.assertEquals(Integer.valueOf(World.save.get().get("actualBodyVersion")), expectedVersion, "Body Data Structure version verification failed");
    }

    public void postOutboundCorrespondenceToServerWithCreatedDef() {
        apitdu.getJsonFromFile("dms/Correspondence.json");
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", save.get().get("mmsId"));
        addRequiredKeys(save.get().get("mmsId"));
        apitdu.jsonElement.getAsJsonObject().addProperty("bodyDataSchemaVersion", World.save.get().get("definitionBodyVersion").toString());
        apitdu.jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().parseProjectNumber(ConfigurationReader.getProperty("projectName")));
        Response response = apiAuto.postAPI(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences", apitdu.jsonElement.getAsJsonObject());
        System.out.println(response.asString());
        World.save.get().put("actualBodyVersion", response.jsonPath().getString("data.bodyDataSchemaVersion"));
        World.save.get().put("correspondenceId", response.jsonPath().getString("data.id"));
    }

    public void getCorrespondenceWithID(String correspondenceId) {
        correspondenceId = save.get().get("correspondenceId");
        String base = ConfigurationReader.getProperty("apiDMSCorrespondence");
        String end = "/correspondences/" + correspondenceId;
        apiAuto.getClassAPI(base + end);
        System.out.println(apiAuto.responseString);
        Assert.assertEquals(apiAuto.statusCode, 200);
        World.save.get().put("actualVersionFromGet", apiAuto.jsonPathEvaluator.get("bodyDataSchemaVersion") + "");
    }

    public ArrayList<String> getCoverVaSendNowbdyData() {
        ArrayList<String> result = new ArrayList<>();
        result.add("item 1");
        result.add("item 2");
        result.add(String.valueOf(generalSave.get().get("firstName")));
        result.add("smith");
        result.add("213 main st");
        result.add("burke");
        result.add("VA");
        result.add("22030");
        result.add("Jerry");
        result.add("Garcia");
        result.add("2021-09-19");
        return result;
    }

    public String downloadOutboundCorrespondence(String onbaseType, String sendNowNID) {
        String actual = "";
        try {
            actual = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().extractContent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().downloadCorrespondence(sendNowNID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actual;
    }

    public boolean verifyNotificationStatusHistoryPastHistory(JsonPath response, String status) {
        boolean result = false;
        List<String> statuses = response.getList("previousStatus.status");
        for (String actual : statuses) {
            if (status.equalsIgnoreCase(actual)) {
                result = true;
            }
        }
        return result;
    }

    public long getLatestOnbaseID(JsonPath onbaseResults) {
        List<Integer> results = onbaseResults.getList("Documents.DocumentHandle");
        Collections.sort(results, Collections.reverseOrder());
        return results.get(0);
    }

    public void verifyContactRecordLinkedOBCorrespondence(JsonPath contactRecordLinks) {
        List<Map<String, Object>> response = contactRecordLinks.getList("externalLinkDetails.content");
        boolean found = false;
        for (Map<String, Object> link : response) {
            if (link.get("name").toString().equalsIgnoreCase("Outbound Correspondence")) {
                generalSave.get().put("OBID", String.valueOf(link.get("id")));
                found = true;
            }
        }
        Assert.assertTrue(found, "link to ob correspondence not found on contact record");
    }

    public void verifyOBLinkedToContactRecord(JsonPath obLinks) {
        List<Map<String, Object>> response = obLinks.getList("externalLinkDetails.content");
        boolean found = false;
        for (Map<String, Object> link : response) {
            if (link.get("name").toString().equalsIgnoreCase("Contact Record")) {
                generalSave.get().put("OBID", String.valueOf(link.get("id")));
                found = true;
            }
        }
        Assert.assertTrue(found, "link to Contact Record not found on OB Correspondence");
    }

    public String getTraceId_CreateCorrespondence() {
        List<String> list = EventsUtilities.getLogs("traceId", "correspondences");
        String result = list.get(6);
        System.out.println(result);
        return result;
    }

    public JsonObject createObRequestwithSpecifiedChannel(String channel) {
        JsonObject request = new JsonObject();
        switch (channel) {
            case "Email":
                request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateEmailObRequest();
                break;
            case "Mail":
                request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateMailObRequest();
                break;
            case "Fax":
                request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateFaxObRequest();
                break;
            case "Text":
                request = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateTextObRequest();
                break;
            default:
                Assert.fail("no matching case");
        }
        return request;
    }

    public void verifyNotificationStatusandReason(String status, String reason) {
        Assert.assertEquals(status, viewOutboundCorrespondenceDetailsPage.notificationStatusValue.getText().trim());
        Assert.assertEquals(reason, viewOutboundCorrespondenceDetailsPage.notificationreasonvalue.getText().trim());
    }

    public void verifyDestinationInformation(JsonPath response, String channel) {
        switch (channel) {
            case "Email":
                Assert.assertEquals(String.valueOf(generalSave.get().get("emailAddress")), response.getString("recipient.emailAddress"));
                break;
            case "Mail":
                Assert.assertEquals(String.valueOf(generalSave.get().get("streetAddress")), response.getString("recipient.addressLine1"));
                Assert.assertEquals(String.valueOf(generalSave.get().get("city")), response.getString("recipient.addressCity"));
                Assert.assertEquals(String.valueOf(generalSave.get().get("state")), response.getString("recipient.addressStateAbbr"));
                Assert.assertEquals(String.valueOf(generalSave.get().get("zipcode")), response.getString("recipient.addressZip"));
                break;
            case "Fax":
                Assert.assertEquals(String.valueOf(generalSave.get().get("faxNumber")), response.getString("recipient.faxNumber"));
                break;
            case "Text":
                Assert.assertEquals(String.valueOf(generalSave.get().get("textNumber")), response.getString("recipient.smsNumber"));
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    public void getCorrespondenceNameByMMSId() {
        String mmsId;
        String correspondencename = "";
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getActiveOutbDefs();
        String type = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        int size = response.getInt("listCorrespondence.size()");
        for (int i = 0; i < size; i++) {
            mmsId = response.getString("listCorrespondence[" + i + "].mmsId");
            if (mmsId.equals(type)) {
                correspondencename = response.getString("listCorrespondence[" + i + "].correspondenceName");
            }
        }
        if (!correspondencename.isEmpty()) {
            World.generalSave.get().put("CORRESPONDENCENAME", correspondencename);
        }
    }

    public void verifyBodyDataAttribute(String value, String method) {
        Set<String> expectedKey = expectedBodyDataElement.get().keySet();
        if (method.equalsIgnoreCase("GET")) {
            for (String keyElement : expectedKey) {
                Assert.assertTrue(apiClassUtil.jsonPathEvaluator.prettyPrint().contains(keyElement), "Verification failed, response.get() doesn't contains " + keyElement);
                Assert.assertTrue(apiClassUtil.jsonPathEvaluator.prettyPrint().contains(expectedBodyDataElement.get().get(keyElement)), "Verification failed, response.get() doesn't contains " + expectedBodyDataElement.get().get(keyElement));
            }
        } else {
            String CID = getWorld().outboundCorrespondenceBean.get().correspondenceId;
            int size = apiClassUtil.jsonPathEvaluator.getInt("data.content.size()");
            for (int i = 0; i < size; i++) {
                if (apiClassUtil.jsonPathEvaluator.getString("data.content[" + i + "].id").equals(CID)) {
                    for (String keyElement : expectedKey) {
                        Assert.assertTrue(apiClassUtil.jsonPathEvaluator.getString("data.content[" + i + "]").contains(keyElement), "Verification failed, response.get() doesn't contains " + keyElement);
                        Assert.assertTrue(apiClassUtil.jsonPathEvaluator.getString("data.content[" + i + "]").contains(expectedBodyDataElement.get().get(keyElement)), "Verification failed, response.get() doesn't contains " + expectedBodyDataElement.get().get(keyElement));
                    }
                    break;
                }
            }
        }
    }

    public void verifysearchcrtieria(Map<String, String> datatable) {

        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toLowerCase()) {
                case "middlename":
                    String actualmiddlename = apiAuto.jsonPathEvaluator.getString("data.content[0].recipients[0].recipientInfo.middleName");
                    if (datatable.get("middleName").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualmiddlename == null);
                    else
                        Assert.assertEquals(actualmiddlename, datatable.get("middleName"));
                    break;
                case "namesuffix":
                    String actualnamesuffix = apiAuto.jsonPathEvaluator.getString("data.content[0].recipients[0].recipientInfo.nameSuffix");
                    if (datatable.get("nameSuffix").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualnamesuffix == null);
                    else
                        Assert.assertEquals(actualnamesuffix, datatable.get("nameSuffix"));
                    break;
                default:
                    Assert.fail("Search criteria does not match " + eachVerifyValue);
            }
        }
    }


    public void sendSearchRequestByValue(String searchCriteria) {
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        apiClassUtil.setEndPoint("correspondences/ouboundcorrespondence?page=0&size=2000");
        JsonObject request = new JsonObject();
        Random random = new Random();
        List<String> searchCriteriaList = Arrays.asList("caseId", "regardingConsumerId", "correspondenceId", "requestDate", "language", "notificationStatus", "channel");
        int randomValue;
        int iteration;
        int boundaryOfList;

        if (searchCriteria.equalsIgnoreCase("single")) {
            iteration = 1;
            boundaryOfList = 4;
        } else {
            iteration = random.nextInt(searchCriteriaList.size() - 1) + 2;
            boundaryOfList = searchCriteriaList.size();
        }
        for (int i = 0; i < iteration; i++) {
            randomValue = random.nextInt(boundaryOfList);
            switch (searchCriteriaList.get(randomValue)) {
                case "caseId":
                    request.addProperty("caseId", apiClassUtil.jsonPathEvaluator.getString("data.anchor.caseId"));
                    break;
                case "regardingConsumerId":
                    request.addProperty("regardingConsumerId", apiClassUtil.jsonPathEvaluator.getString("data.anchor.regardingConsumerId[0]"));
                    break;
                case "correspondenceId":
                    request.addProperty("correspondenceId", apiClassUtil.jsonPathEvaluator.getString("data.id"));
                    break;
                case "notificationStatus":
                    JsonArray notificationArray = new JsonArray();
                    notificationArray.add(apiClassUtil.jsonPathEvaluator.getString("data.status"));
                    request.add("notificationStatus", notificationArray);
                    break;
                case "language":
                    JsonArray languageArray = new JsonArray();
                    languageArray.add(apiClassUtil.jsonPathEvaluator.getString("data.language"));
                    request.add("language", languageArray);
                    break;
                case "channel":
                    JsonArray channelArray = new JsonArray();
                    channelArray.add(apiClassUtil.jsonPathEvaluator.getString("data.recipients[0].notifications[0].channelType"));
                    request.add("channel", channelArray);
                    break;
                case "requestDate":
                    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    Date todayDate = new Date();
                    String currentDate = dateForm.format(todayDate);
                    request.addProperty("requestDate", currentDate);
                    request.addProperty("requestDateOperator", "equalTo");
                    break;
            }
        }
        apiClassUtil.PostAPIWithParameter(request);
    }

    public void searchForCorByCaseId(String caseId) {
        if (caseId.equalsIgnoreCase("previouslyCreated")) {
            Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
            caseId = caseConsumerId.get("caseId");
        }
        String base = ConfigurationReader.getProperty("apiDMSCorrespondence");
        String end = "/searchcorrespondence?caseId=" + caseId + "&page=0&size=5";
        apiAuto.getClassAPI(base + end);
        System.out.println(apiAuto.responseString);
        Assert.assertEquals(apiAuto.statusCode, 200);
    }

    public boolean verifyLinksSectionIsDisplayed() {
        return uiAutoUitilities.get().quickIsDisplayed(createOutboundCorrespondencePage.linksLabel);
    }

    public Map<String, List<String>> getStatusAndReasonFromCorrespondence(String cid) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<String> notificationIds = response.getList("recipients[0].notifications.notificationId");
        int size = response.getInt("recipients[0].notifications.size()");
        Map<String, List<String>> statusReasonMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            List<String> statusAndReason = new ArrayList<>();
            String nid = response.get("recipients[0].notifications[" + i + "].notificationId");
            statusAndReason.add(response.get("recipients[0].notifications[" + i + "].notificationStatus.status"));
            statusAndReason.add(response.get("recipients[0].notifications[" + i + "].notificationStatus.statusReason"));
            statusReasonMap.put(nid, statusAndReason);
        }
        List<String> cidStatusAndReason = new ArrayList<>();
        cidStatusAndReason.add(response.get("status"));
        cidStatusAndReason.add(response.get("statusMessage"));
        statusReasonMap.put("CorrespondenceStatusAndResponse", cidStatusAndReason);
        return statusReasonMap;
    }

    public void getChannelByMMSIDAndVerifyChannelTemplate() {
        JsonPath jsonPathChannelResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getAllChannelsByMMSId();
        int channelListSize = jsonPathChannelResponse.getInt("channelDefinitionResponseList.size");
        Map<String, Map<String, Integer>> expectedChannelValues = new HashMap<>();
        for (int i = 0; i < channelListSize; i++) {
            for (int j = 0; j < jsonPathChannelResponse.getInt("channelDefinitionResponseList[" + i + "].languageTemplate.size"); j++) {
                if (jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].language").equals("English")) {
                    expectedChannelValues.put(jsonPathChannelResponse.getString("channelDefinitionResponseList[" + i + "].channelType"), jsonPathChannelResponse.getMap("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "]"));
                }
            }
        }
        int corNotificationsSize = ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getInt("recipients[0].notifications.notificationId.size");
        String channelType = "";
        for (int i = 0; i < corNotificationsSize; i++) {
            JsonPath getLetterDataResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].notificationId"));
            channelType = ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].channelType");

            // Verify templateID
            Assert.assertTrue(ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].template").equals(String.valueOf(expectedChannelValues.get(channelType).get("templateId"))) && ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].template").equals(getLetterDataResponse.getString("sections[0].template.languageTemplateId")), "Template Id verification failed");
            //Verify channel definition id
            Assert.assertEquals(ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].channelDefinitionId"), String.valueOf(expectedChannelValues.get(channelType).get("channelDefinitionId")), "Channel definition Id verification failed");
            //Verify template version
            Assert.assertTrue(ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].version").equals(String.valueOf(expectedChannelValues.get(channelType).get("version"))) && ViewOutboundCorrespondenceStepDefs.getCorrespondenceResponse.get().getString("recipients[0].notifications[" + i + "].version").equals(getLetterDataResponse.getString("sections[0].template.languageTemplateVersion")), "Template version definition Id verification failed");
        }
    }


    public void updateStateInRequest(String stateValue) {
        stateCode.set("");
        switch (stateValue) {
            case "null":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().add("state", JsonNull.INSTANCE);
                break;
            case "stateFullName":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", faker.get().address().state());
                break;
            case "correctStateAbbreviationLowerCase":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", getListOfStateAbbreviation().get(radomNumber(getListOfStateAbbreviation().size())).toLowerCase());
                break;
            case "anyTwoUpperCase":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", RandomStringUtils.randomAlphabetic(2).toUpperCase());
                break;
            case "numeric":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", RandomStringUtils.randomNumeric(2));
                break;
            case "correctStateAbbreviationCamelCase":
                stateCode.set(getListOfStateAbbreviation().get(radomNumber(getListOfStateAbbreviation().size())));
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", stateCode.get().substring(0, 1).toLowerCase() + stateCode.get().substring(1));
                break;
            case "emptyString":
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", "");
                break;
            case "correctStateAbbreviation":
                stateCode.set(getListOfStateAbbreviation().get(radomNumber(getListOfStateAbbreviation().size())));
                apitdu.jsonElement.getAsJsonObject().get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("state", stateCode.get());
                break;
        }
    }

    private static List<String> getListOfStateAbbreviation() {
        return Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "AS", "GU", "FM", "MH", "MP", "PR", "PW", "UM", "VI", "AA", "AE", "AP");
    }

    public void verifyResponseMessage(String message) {
        switch (message) {
            case "ErrorInvalidStateCode":
                Assert.assertEquals(apiClassUtil.jsonPathEvaluator.getString("errors[0].message"), "If a state is provided it must be a valid 2 digit postal state or territory abbreviation");
                break;
            case "ErrorStateNotProvided":
                Assert.assertEquals(apiClassUtil.jsonPathEvaluator.getString("errors[0].message"), "If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided");
                break;
            case "Success":
                Assert.assertEquals(apiClassUtil.jsonPathEvaluator.getString("status"), "SUCCESS");
                Assert.assertEquals(apiClassUtil.jsonPathEvaluator.getString("data.recipients[0].recipientInfo.state"), stateCode.get());
                break;
        }
    }

    public void verifyResponseStatusCode(String statusCode) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().correspondenceResponse.statusCode(), Integer.parseInt(statusCode), "Status code verification failed");
    }

    public void sendPostRequestWithEmptyStringsAndSaveTraceID() {
        apitdu.getJsonFromFile("dms/OBCorrespondenceEmptyStrings.json");
        apitdu.jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
        requestParams.set(apitdu.jsonElement.getAsJsonObject());
        requestParams.get().getAsJsonObject("anchor").addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        addRequiredKeys(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType());
        JsonPath resp = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createCorrespondence(requestParams.get(), ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
        System.out.println("Trace id " + World.save.get().get("PostTraceId"));
        String cid = resp.getString("data.id");
        JsonPath getCID = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<String> nids = getCID.getList("recipients[0].notifications.notificationId");
        generalList.get().clear();

        for (int i = 0; i < nids.size(); i++) {
            generalList.get().add(nids.get(i));
        }
        System.out.println(generalList.get());
    }

    public void verifyOutboundEventForEmptyStrings(List<String> listOfEvents) {
        try {
            waitFor(15);
            JsonPath jsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(World.save.get().get("PostTraceId"));
            int eventsSize = jsonPath.getInt("events.size()");
            List events = jsonPath.getJsonObject("events");
            List<String> actualEvents = new ArrayList<>();
            String eventType = "";
            for (int i = 0; i < eventsSize; i++) {
                eventType = jsonPath.getString("events[" + i + "].eventName");
                if (getListOfEventsThreadLocal().contains(eventType)) {
                    actualEvents.add(eventType.toUpperCase().trim());
                    Map event = (HashMap) events.get(i);
                    JsonObject payLoadData = new JsonParser().parse(event.get("payload").toString()).getAsJsonObject();
                    isJsonObjWithEmptyString(payLoadData);
                }
            }
            System.out.println("Actual Events Tested " + actualEvents);
            Assert.assertTrue(actualEvents.containsAll(listOfEvents), " Missing request validation " + actualEvents + " Expected Events " + listOfEvents);
        } catch (Exception e) {
            Assert.fail("Failure in verifyOutboundEventForEmptyStrings" + e.getMessage());
        }
    }

    public Map<String, JsonElement> getJsonObjectAsMap(JsonElement jsonElement) {
        Map<String, JsonElement> map = new HashMap<>();
        Iterator iterator = jsonElement.getAsJsonObject().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = (Map.Entry) iterator.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public boolean hasEmptyString(JsonElement jsonEl) {
        boolean hasEmptyStirng = false;
        if (jsonEl.toString().replaceAll("\\\\", "").contains("\"\"")) {
            hasEmptyStirng = true;
        }
        return hasEmptyStirng;
    }

    public void isJsonObjWithEmptyString(JsonObject jsonObj) {
        Map<String, JsonElement> map = getJsonObjectAsMap(jsonObj);
        for (Map.Entry<String, JsonElement> me : map.entrySet()) {
            if (!me.getKey().equalsIgnoreCase("receivedPayload") && !me.getValue().isJsonObject()) {
                Assert.assertFalse(hasEmptyString(me.getValue()), " Empty String found in payload " + me.getValue().toString().replaceAll("\\\\", ""));
            } else if (!me.getKey().equalsIgnoreCase("receivedPayload")) {
                JsonObject mapValJsonObj = map.get(me.getKey()).getAsJsonObject();
                isJsonObjWithEmptyString(mapValJsonObj);
            } else if (me.getKey().equalsIgnoreCase("receivedPayload")) {
                System.out.println("SUCCESSFULLY IGNORED received payload");
            }
        }
    }

    public void getLetterDataByNotificationId() {
        waitFor(5);
        String NID = apiClassUtilGet.jsonPathEvaluator.getString("recipients[0].notifications[0].notificationId");
        getLetterResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(NID));
    }

    public void verifyLetterDataResponseContainsBodyData() {
        // String expectedBodyData = save.get().get("expectedBodyData");
        String expectedBodyData = save.get().get("expectedBodyData").replaceAll("null", "");
        System.out.println("expectedBodyData : " + expectedBodyData);
        System.out.println("actualBodyData : " + getLetterResponse.get().getString("sections[0].body"));
        Assert.assertEquals(getLetterResponse.get().getString("sections[0].body"), expectedBodyData);
    }

    public void postCustomCorrespondence(Map<String, String> dataTable) {
        JsonObject obRequest = (JsonObject) apitdu.getJsonFromFile("dms/OBCorrespondence1.json").jsonElement;
        this.requesterId.set(obRequest.getAsJsonObject("requester").get("requesterId").toString());
        String channelTypes = "";
        recipientMap.get().clear();
        for (String keyword : dataTable.keySet()) {
            switch (keyword.toUpperCase()) {
                case "FIRSTNAME":
                case "LASTNAME":
                case "MIDDLENAME":
                case "NAMESUFFIX":
                case "EMAILADDRESS":
                case "TEXTNUMBER":
                case "FAXNUMBER":
                case "STREETADDRESS":
                case "STREETADDIONALLINE1":
                case "CITY":
                case "STATE":
                case "ZIPCODE":
                case "CONSUMERID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                        String consumerId = caseConsumerId.get("consumerId");
                        recipientMap.get().put(keyword, consumerId);
                    } else
                        recipientMap.get().put(keyword, dataTable.get(keyword));
                    break;
                case "CORRESPONDENCEDEFINITIONMMSCODE":
                case "CONTACTID":
                case "TASKID":
                    obRequest.addProperty(keyword, dataTable.get(keyword));
                    break;
                case "CASEID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get("caseId"))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                        String caseId = caseConsumerId.get("caseId");
                        Assert.assertNotNull(caseId);
                        obRequest.getAsJsonObject("anchor").addProperty("caseId", caseId);
                    } else if ("DCEBcaseid".equalsIgnoreCase(dataTable.get("caseId"))) {
                        String DcCaseId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
                        obRequest.getAsJsonObject("anchor").addProperty("caseId", DcCaseId);
                    } else if ("empty".equals(dataTable.get("caseId"))) {
                        obRequest.getAsJsonObject("anchor").remove("caseId");
                    } else if ("empty".equals(dataTable.get("CaseID"))) {
                        obRequest.getAsJsonObject("anchor").remove("caseId");
                    } else {
                        obRequest.getAsJsonObject("anchor").addProperty("caseId", dataTable.get(keyword));

                    }
                    System.out.println(dataTable.get(keyword));
                    break;
                case "PROJECTID":
                    if (obRequest.getAsJsonObject("anchor").has("projectId")) {
                        obRequest.getAsJsonObject("anchor").remove("projectId");
                    }
                    if (!("empty".equalsIgnoreCase(dataTable.get("projectId")))) {
                        obRequest.getAsJsonObject("anchor").addProperty("projectId", dataTable.get(keyword));
                    }
                    break;
                case "REGARDINGCONSUMERID":
                    String consumerNum = dataTable.get(keyword);
                    if (consumerNum.equalsIgnoreCase("random10")) {
                        consumerNum = RandomStringUtils.randomNumeric(10);
                        generalSave.get().put("regardingConsumerId", consumerNum);
                        obRequest.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(consumerNum);
                    } else if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                        String consumerId = caseConsumerId.get("consumerId");
                        obRequest.getAsJsonObject().get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(consumerId);
                    } else {
                        List<Integer> list = Arrays.stream(consumerNum.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                        for (int i : list) {
                            obRequest.getAsJsonObject("anchor").getAsJsonArray("regardingConsumerId").add(i);
                        }
                    }
                    break;
                case "APPLICATIONID":
                    String applicationId;
                    if (obRequest.getAsJsonObject("anchor").has("applicationId")) {
                        obRequest.getAsJsonObject("anchor").remove("applicationId");
                    }
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        applicationId = World.save.get().get("appID");
                    } else {
                        applicationId = dataTable.get(keyword);
                    }
                    obRequest.getAsJsonObject("anchor").addProperty("applicationId", applicationId);
                    break;
                case "LANGUAGE":
                    if (("empty".equalsIgnoreCase(dataTable.get(keyword)))) {
                        obRequest.remove("language");
                    } else if (!("ENGLISH".equalsIgnoreCase(dataTable.get(keyword)))) {
                        obRequest.addProperty(keyword, dataTable.get(keyword));
                    }
                    break;
                case "REQUESTERID":
                    if (!("empty".equalsIgnoreCase(dataTable.get(keyword)))) {
                        obRequest.getAsJsonObject("requester").addProperty("requesterId", dataTable.get(keyword));
                        this.requesterId.set(dataTable.get(keyword));
                    } else {
                        obRequest.getAsJsonObject("requester").remove("requesterId");
                    }
                    break;
                case "CHANNELTYPE":
                    channelTypes = dataTable.get(keyword);
                    channelList.set(Arrays.stream(channelTypes.split(",")).map(String::valueOf).collect(Collectors.toList()));
                    obRequest = buildChannelInfo(obRequest);
                    break;
                case "BODYDATA":
                    if (("null".equalsIgnoreCase(dataTable.get(keyword)) && obRequest.has(keyword))) {
                        obRequest.add(keyword, null);
                    } else if (dataTable.get(keyword).contains("&")) {
                        String bodyDataVal = dataTable.get(keyword);
                        List<String> bodyDataList = Arrays.stream(bodyDataVal.split("&")).collect(Collectors.toList());
                        for (int i = 0; i < bodyDataList.size(); i++) {
                            JsonArray arr = new JsonArray();
                            arr.add("Test");
                            String[] val = bodyDataList.get(i).split("-");
                            switch (val[1]) {
                                case "String":
                                    obRequest.getAsJsonObject("bodyData").addProperty(val[0], apitdu.getRandomString(5).randomString);
                                    break;
                                case "Date":
                                    obRequest.getAsJsonObject("bodyData").addProperty(val[0], LocalDate.now().toString());
                                    break;
                                case "Number":
                                    obRequest.getAsJsonObject("bodyData").addProperty(val[0], 12);
                                    break;
                                case "Checkbox":
                                    obRequest.getAsJsonObject("bodyData").addProperty(val[0], true);
                                    break;
                                case "Array":
                                    obRequest.getAsJsonObject("bodyData").add(val[0], arr);
                                    break;
                            }
                        }
                    } else if (!("empty".equalsIgnoreCase(dataTable.get(keyword)))) {
                        obRequest.getAsJsonObject("bodyData").addProperty(dataTable.get(keyword), apitdu.getRandomString(5).randomString);
                    }
                    break;
                default:
                    if (("empty".equalsIgnoreCase(dataTable.get(keyword)) && obRequest.has(keyword))) {
                        obRequest.remove(keyword);
                    } else if (("null".equalsIgnoreCase(dataTable.get(keyword)) && obRequest.has(keyword))) {
                        obRequest.add(keyword, null);
                    }
                    System.out.println("Invalid key " + keyword);
                    break;
            }
        }
        obRequest = buildRecipientInfo(obRequest);
        createdTime.set(OffsetDateTime.now(ZoneOffset.UTC).toString().substring(0, 16).replace("T", " "));
        response.set(apiAuto.createCorrespondence(obRequest, ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences"));
        World.generalSave.get().put("OutboundCorrespondenceResponse", response.get());
        try {
            save.get().put("CorrespondenceId", response.get().getString("data.id"));
        } catch (NullPointerException e) {
            System.out.println(" There is a problem in creation of Correspondence " + response.get().getString("message"));
        }
    }

    public JsonObject buildRecipientInfo(JsonObject obRequest) {
        JsonObject recipientInfoObj = new JsonObject();
        for (String k : recipientMap.get().keySet()) {
            switch (k) {
                case "firstName":
                case "lastName":
                case "middleName":
                case "nameSuffix":
                case "emailAddress":
                case "textNumber":
                case "faxNumber":
                case "streetAddress":
                case "streetAddionalLine1":
                case "city":
                case "state":
                case "zipcode":
                case "consumerId":
                    recipientInfoObj.addProperty(k, String.valueOf(recipientMap.get().get(k)));
                    break;
                default:
                    Assert.fail("Invalid recipient Info key");
                    break;
            }
        }
        if (recipientMap.get().size() > 0) {
            obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().add("recipientInfo", recipientInfoObj);
        } else if (recipientMap.get().size() == 0 && channelList.get().size() == 0) {
            obRequest.remove("recipients");
        } else if (channelList.get().size() > 0 && recipientMap.get().size() == 0) {
            System.out.println("Testing negative scenario for channel destination");
        }
        return obRequest;
    }

    public JsonObject buildChannelInfo(JsonObject obRequest) {
        JsonObject channelObj = new JsonObject();
        if (channelList.get().get(0).equalsIgnoreCase("null")) {
            channelObj.add("channelType", null);
            channelObj.addProperty("language", "English");
            obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj);
        } else if (channelList.get().get(0).equalsIgnoreCase("emptyjson")) {
            obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj);
        } else {
            for (int i = 0; i < channelList.get().size(); i++) {
                JsonObject channelObj2 = new JsonObject();
                channelObj2.addProperty("channelType", channelList.get().get(i));
                channelObj2.addProperty("language", "English");
                obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj2);
            }
        }
        return obRequest;
    }

    public JsonObject buildBodyDataInfo(JsonObject obRequest) {
        JsonObject channelObj = new JsonObject();
        if (channelList.get().get(0).equalsIgnoreCase("null")) {
            channelObj.add("channelType", null);
            channelObj.addProperty("language", "English");
            obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj);
        } else if (channelList.get().get(0).equalsIgnoreCase("emptyjson")) {
            obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj);
        } else {
            for (int i = 0; i < channelList.get().size(); i++) {
                JsonObject channelObj2 = new JsonObject();
                channelObj2.addProperty("channelType", channelList.get().get(i));
                channelObj2.addProperty("language", "English");
                obRequest.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(channelObj2);
            }
        }
        return obRequest;
    }


    public void verifyCreatedByAndDateDetails() {
        String actualCreatedDatetime = (response.get().getString("data.createdDatetime")).substring(0, 16);
        String actualUpdatedDatetime = (response.get().getString("data.updatedDatetime")).substring(0, 16);
        Assert.assertEquals(this.requesterId.get(), response.get().getString("data.createdBy"));
        Assert.assertEquals(this.requesterId.get(), response.get().getString("data.updatedBy"));
        Assert.assertEquals(actualCreatedDatetime, createdTime.get());
        Assert.assertEquals(actualUpdatedDatetime, createdTime.get());
    }

    public void verifyLinkBetweenContactAndCorrId(Object jsonResponse) {
        JsonPath jsonObjResp = (JsonPath) jsonResponse;
        boolean contactFound = false;
        String expectedContactId = response.get().getString("data.contactId");
        int contentNum = jsonObjResp.get("externalLinkDetails.content.id.size");
        for (int i = 0; i < contentNum; i++) {
            String id = jsonObjResp.getString("externalLinkDetails.content[" + i + "].id");
            String name = jsonObjResp.getString("externalLinkDetails.content[" + i + "].name");
            if (id.equalsIgnoreCase(expectedContactId) && name.equalsIgnoreCase("Contact Record")) {
                contactFound = true;
                break;
            }
        }
        Assert.assertTrue(contactFound);
    }


    public void verifyLinkBetweenContactAndTaskId(Object jsonResponse) {
        JsonPath jsonObjResp = (JsonPath) jsonResponse;
        boolean taskFound = false;
        String expectedContactId = response.get().getString("data.taskId");
        int contentNum = jsonObjResp.get("externalLinkDetails.content.id.size");
        for (int i = 0; i < contentNum; i++) {
            String id = jsonObjResp.getString("externalLinkDetails.content[" + i + "].id");
            String name = jsonObjResp.getString("externalLinkDetails.content[" + i + "].name");
            if (id.equalsIgnoreCase(expectedContactId) && name.equalsIgnoreCase("Task")) {
                taskFound = true;
                break;
            }
        }
        Assert.assertTrue(taskFound);
    }

    public void verifyLetterDataResponseContainsSection(Map<String, String> dataTable) {
        Assert.assertEquals(getLetterResponse.get().getInt("sections.size"), 1);
        Map m = getLetterResponse.get().getMap("sections.get(0)");
        Assert.assertEquals(m.get("sectionNumber"), 1);
        Assert.assertTrue(m.containsKey("body"));
        Assert.assertTrue(m.containsKey("template"));
        Map template = (Map) m.get("template");
        Assert.assertEquals(template.get("languageCode"), dataTable.get("languageCode"));
        Assert.assertFalse(getLetterResponse.get().prettyPrint().contains("regarding"));
        try {
            getLetterResponse.get().get("body").toString();
        } catch (NullPointerException e) {
            System.out.println("Body Element is not a top level element");
        }
        try {
            getLetterResponse.get().get("template").toString();
        } catch (NullPointerException e) {
            System.out.println("Template Element is not a top level element");
        }

    }

    public void getLetterDataByNotificationIdFromObcResponse() {
        waitFor(5);
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath getCID = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<String> notificationIds = getCID.getList("recipients[0].notifications.notificationId");
        getLetterResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(notificationIds.get(0)));
    }

    public void getLetterDataByNotificationId(String NID) {
        waitFor(5);
        if (NID.equalsIgnoreCase("RESENDNOTIFICATIONID"))
            NID = World.generalSave.get().get("RESENDNOTIFICATIONID").toString();
        getLetterResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(NID));
    }

    public void verifyLetterDataResponseContainsExpectedData(Map<String, String> dataTable) {

        // try {
        if (dataTable.keySet().contains("recipientMiddleName") || dataTable.keySet().contains("recipientNameSuffix")) {
            Assert.assertEquals(getLetterResponse.get().get("recipient.recipientMiddleName").toString(), dataTable.get("recipientMiddleName"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.recipientNameSuffix").toString(), dataTable.get("recipientNameSuffix"));
        } else {
            Assert.assertEquals(getLetterResponse.get().get("recipient.recipientFirstName").toString(), dataTable.get("recipientFirstName"));
//        Assert.assertEquals(getLetterResponse.get().get("recipient.recipientMiddleName").toString(),dataTable.get("recipientFirstName"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.recipientLastName").toString(), dataTable.get("recipientLastName"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.addressLine1").toString(), dataTable.get("addressLine1"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.addressLine2").toString(), dataTable.get("addressLine2"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.addressCity").toString(), dataTable.get("addressCity"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.addressStateAbbr").toString(), dataTable.get("addressStateAbbr"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.addressZip").toString(), dataTable.get("addressZip"));
//        Assert.assertEquals(getLetterResponse.get().get("recipient.addressZip4").toString(),dataTable.get("addressZip4"));
            Assert.assertEquals(getLetterResponse.get().get("recipient.emailAddress").toString(), dataTable.get("emailAddress"));
//        Assert.assertEquals(getLetterResponse.get().get("recipient.faxNumber").toString(),dataTable.get("faxNumber"));
//        Assert.assertEquals(getLetterResponse.get().get("recipient.smsNumber").toString(),dataTable.get("smsNumber"));
            Assert.assertEquals(getLetterResponse.get().getString("recipient.careOfLine"), null);
        }
        /*} catch (NullPointerException e) {
            System.out.println("Element is missing");
        }*/
    }

    public void createApplicationRequest(String appType, String appName) {
        Faker faker = new Faker();
        requestParams.set((JsonObject) apitdu.getJsonFromFile("dms/createApplication.json").jsonElement);

        requestParams.get().addProperty("applicationType", appType);
        requestParams.get().addProperty("applicationName", appName);
        requestParams.get().addProperty("applicationSignatureDate", LocalDate.now().toString());
        requestParams.get().addProperty("applicationReceivedDate", LocalDate.now().toString());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().getAsJsonArray("applicationConsumerEmail").get(0)
                .getAsJsonObject().addProperty("emailAddress", faker.internet().emailAddress());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().getAsJsonArray("applicationConsumerAddress").get(0)
                .getAsJsonObject().addProperty("addressStreet1", faker.address().streetAddress());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().getAsJsonArray("applicationConsumerPhone").get(0)
                .getAsJsonObject().addProperty("phoneNumber", faker.random().nextInt(1000000000, 1999999999));
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().addProperty("consumerFirstName", faker.name().firstName());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().addProperty("consumerLastName", faker.name().lastName());
        requestParams.get().getAsJsonArray("applicationConsumers").get(0)
                .getAsJsonObject().addProperty("ssn", faker.random().nextInt(100000000, 999999999));
    }

    public void sendApplicationRequest() {
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiApplicationData"));
        apiClassUtil.setEndPoint("/mars/applicationdata/applications");
        apiClassUtil.PostAPIWithParameter(requestParams.get());
        response.set(apiClassUtil.jsonPathEvaluator);
        save.get().put("appID", apiClassUtil.jsonPathEvaluator.getString("data.applicationId"));
        save.get().put("appConsumerID", apiClassUtil.jsonPathEvaluator.getString("data.applicationConsumers[0].applicationConsumerId"));
    }

    public void retrieveApplicationDetailsByID() {
        BrowserUtils.waitFor(3);
        String applicationID = save.get().get("appID");
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiApplicationData"));
        apiClassUtil.setEndPoint("/mars/applicationdata/applications/applicationdetails/" + applicationID);
        apiClassUtil.getAPI();
        response.set(apiClassUtil.jsonPathEvaluator);
        save.get().put("appName", response.get().getString("data.name"));
        save.get().put("appType", response.get().getString("data.type"));
        save.get().put("appStatusDate", response.get().getString("data.statusDate"));
        save.get().put("appStatus", response.get().getString("data.status"));

    }

    public void verifyApplicationValuesUnderLinksSection(Map<String, String> dataTable) {
        waitFor(3);
        browserUtils.get().scrollToElement(viewOutboundCorrespondenceDetailsPage.applicationID);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "ApplicationID":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.applicationID.getText(), save.get().get("appID"), "Application ID verification failed");
                    break;
                case "Name":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.applicationName.getText(), save.get().get("appName"), "Application Name verification failed");
                    break;
                case "Type":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.applicationType.getText(), save.get().get("appType"), "Application Type verification failed");
                    break;
                case "StatusDate":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.applicationStatusDate.getText(), BrowserUtils.convertyyyyMMddToMMddyyyy(save.get().get("appStatusDate")), "Application Status date verification failed");
                    break;
                case "Status":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.applicationStatus.getText(), save.get().get("appStatus"), "Application Status verification failed");
                    break;
            }
        }
    }

    public void updateApplicationStatus(String status, String reason) {
        String applicationId = save.get().get("appID");
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("applicationId", applicationId);
        requestParams.get().addProperty("newApplicationStatus", status);
        requestParams.get().addProperty("applicationChangeReason", reason);
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiApplicationData"));
        apiClassUtil.setEndPoint("/mars/applicationdata/applications/status");
        try {
            apiClassUtil.PostAPIWithParameter(requestParams.get());
        } catch (Exception ex) {
            System.out.println("No response.get() body for Application status update");
        }
        save.get().put("appStatus", status);
    }


    public Map<String, String> getCaseLoaderRandomMailAddress(Map<String, String> data) {
        Faker faker = new Faker();
        Map<String, Object> mailAddress = new HashMap<>();
        mailAddress.put("addressCity", faker.address().city());
        mailAddress.put("addressCounty", faker.address().cityName());
        mailAddress.put("addressState", faker.address().stateAbbr());
        mailAddress.put("addressStreet1", faker.address().streetAddress());
        mailAddress.put("addressStreet2", "Apt " + RandomStringUtils.random(4, false, true));
        mailAddress.put("addressType", "Mailing");
        mailAddress.put("addressVerified", "true");
        mailAddress.put("primaryIndicator", "true"); //prefix here bc multiple primaryIndicator values in same map, but for different channels
        mailAddress.put("addressZip", faker.address().zipCode());
        mailAddress.put("addressZipFour", RandomStringUtils.random(4, false, true));
        generalSave.get().put("mailAddress", mailAddress);
        data.put("mailaddressCity", String.valueOf(mailAddress.get("addressCity")));
        data.put("mailaddressCounty", String.valueOf(mailAddress.get("addressCounty")));
        data.put("mailaddressState", String.valueOf(mailAddress.get("addressState")));
        data.put("mailaddressStreet1", String.valueOf(mailAddress.get("addressStreet1")));
        data.put("mailaddressStreet2", String.valueOf(mailAddress.get("addressStreet2")));
        data.put("mailaddressType", "Mailing");
        data.put("mailaddressVerified", "true");
        data.put("mailprimaryIndicator", "true"); //prefix here bc multiple primaryIndicator values in same map, but for different channels
        data.put("mailaddressZip", String.valueOf(mailAddress.get("addressZip")));
        data.put("mailaddressZipFour", String.valueOf(mailAddress.get("addressZipFour")));
        return data;
    }

    public Map<String, String> getCaseLoaderRandomEmailAddress(Map<String, String> data) {
        Faker faker = new Faker();
        String emailAddress = faker.internet().emailAddress();
        Map<String, Object> email = new HashMap<>();
        email.put("emailAddress", emailAddress);
        email.put("emailType", "Primary");
        email.put("primaryIndicator", "true");//prefix here bc multiple primaryIndicator values in same map, but for different channels
        generalSave.get().put("emailAddress", email);
        data.put("emailemailAddress", emailAddress);
        data.put("emailemailType", "Primary");
        data.put("emailprimaryIndicator", "true");//prefix here bc multiple primaryIndicator values in same map, but for different channels
        return data;
    }

    public Map<String, String> getCaseLoaderRandomFax(Map<String, String> data) {
        Map<String, Object> fax = new HashMap<>();
        String phoneNumber = "";
        do {
            phoneNumber = RandomStringUtils.random(10, false, true);
        } while (phoneNumber.startsWith("0") || phoneNumber.startsWith("1"));
        fax.put("phoneNumber", phoneNumber);
        fax.put("phoneType", "Fax");
        fax.put("smsEnabledInd", "false");
        fax.put("primaryIndicator", "true");
        generalSave.get().put("faxAddress", fax);
        data.put("faxphoneNumber", String.valueOf(fax.get("phoneNumber")));
        data.put("faxphoneType", "Fax");
        data.put("faxsmsEnabledInd", "false");
        data.put("faxprimaryIndicator", "true");
        return data;
    }

    public Map<String, String> getCaseLoaderRandomText(Map<String, String> data) {
        Map<String, Object> text = new HashMap<>();
        String phoneNumber = "";
        do {
            phoneNumber = RandomStringUtils.random(10, false, true);
        } while (phoneNumber.startsWith("0") || phoneNumber.startsWith("1"));
        text.put("phoneNumber", phoneNumber);
        text.put("phoneType", "Cell");
        text.put("smsEnabledInd", "true");
        text.put("primaryIndicator", "true");
        generalSave.get().put("textAddress", text);
        data.put("textphoneNumber", String.valueOf(text.get("phoneNumber")));
        data.put("textphoneType", "Cell");
        data.put("textsmsEnabledInd", "true");
        data.put("textprimaryIndicator", "true");
        return data;
    }

    public JsonObject editCaseLoaderRandomMailAddress(JsonObject request) {
        JsonObject newAddress = new JsonObject();
        Map<String, Object> randomNewData = generateRandomeNewMail();
        generalSave.get().put("mailAddress", randomNewData);
        for (Map.Entry<String, Object> address : randomNewData.entrySet()) {
            if (address.getValue() instanceof Boolean) {
                newAddress.addProperty(address.getKey(), Boolean.valueOf(String.valueOf(address.getValue())));
            } else {
                newAddress.addProperty(address.getKey(), String.valueOf(address.getValue()));
            }
        }
        request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").add("address", newAddress);
        return request;
    }

    private Map<String, Object> generateRandomeNewMail() {
        Map<String, Object> address = new HashMap<>();
        Faker faker = new Faker();
        address.put("addressCity", faker.address().city());
        address.put("addressCounty", faker.address().cityName());
        address.put("addressState", faker.address().stateAbbr());
        address.put("addressStreet1", faker.address().streetAddress());
        address.put("addressStreet2", "Apt " + RandomStringUtils.random(4, false, true));
        address.put("addressType", "Mailing");
        address.put("effectiveStartDate", OffsetDateTime.now(ZoneOffset.UTC).toString());
        address.put("addressVerified", true);
        address.put("primaryIndicator", true);
        address.put("addressZip", faker.address().zipCode());
        address.put("addressZipFour", RandomStringUtils.random(4, false, true));
        return address;
    }

    public JsonObject editCaseLoaderRandomEmailAddress(JsonObject request) {
        JsonObject newAddress = new JsonObject();
        Map<String, Object> randomNewData = generateRandomeNewEmail();
        generalSave.get().put("emailAddress", randomNewData);
        for (Map.Entry<String, Object> address : randomNewData.entrySet()) {
            if (address.getValue() instanceof Boolean) {
                newAddress.addProperty(address.getKey(), Boolean.valueOf(String.valueOf(address.getValue())));
            } else {
                newAddress.addProperty(address.getKey(), String.valueOf(address.getValue()));
            }
        }
        request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").add("email", newAddress);
        return request;
    }

    private Map<String, Object> generateRandomeNewEmail() {
        Map<String, Object> data = new HashMap<>();
        Faker faker = new Faker();
        data.put("emailAddress", RandomStringUtils.random(7, true, false) + "@" + RandomStringUtils.random(7, true, false) + ".com");
        data.put("emailType", "Primary");
        data.put("primaryIndicator", Boolean.TRUE);//prefix here bc multiple primaryIndicator values in same map, but for different channels
        return data;
    }

    public JsonObject editCaseLoaderRandomFax(JsonObject request) {
        JsonObject newAddress = new JsonObject();
        Map<String, Object> randomNewData = generateRandomeNewFax();
        for (Map.Entry<String, Object> address : randomNewData.entrySet()) {
            if (address.getValue() instanceof Boolean) {
                newAddress.addProperty(address.getKey(), Boolean.valueOf(String.valueOf(address.getValue())));
            } else {
                newAddress.addProperty(address.getKey(), String.valueOf(address.getValue()));
            }
        }
        request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").add("phone", newAddress);
        return request;
    }

    private Map<String, Object> generateRandomeNewFax() {
        Map<String, Object> secondFax = new HashMap<>();
        String number = RandomStringUtils.random(10, false, true);
        secondFax.put("secondFaxphoneNumber", number);
        secondFax.put("secondFaxphoneType", "Fax");
        secondFax.put("secondFaxsmsEnabledInd", "false");
        secondFax.put("secondFaxprimaryIndicator", "true");
        generalSave.get().put("secondFaxAddress", secondFax);
        Map<String, Object> data = new HashMap<>();
        data.put("phoneNumber", number);
        data.put("phoneType", "Fax");
        data.put("smsEnabledInd", "false");
        data.put("primaryIndicator", "true");
        return data;
    }

    public JsonObject editCaseLoaderRandomText(JsonObject request) {
        JsonObject newAddress = new JsonObject();
        Map<String, Object> randomNewData = generateRandomeNewText();
        for (Map.Entry<String, Object> address : randomNewData.entrySet()) {
            if (address.getValue() instanceof Boolean) {
                newAddress.addProperty(address.getKey(), Boolean.valueOf(String.valueOf(address.getValue())));
            } else {
                newAddress.addProperty(address.getKey(), String.valueOf(address.getValue()));
            }
        }
        request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").add("phone", newAddress);
        return request;
    }

    private Map<String, Object> generateRandomeNewText() {
        Map<String, Object> secondText = new HashMap<>();
        String number = RandomStringUtils.random(10, false, true);
        secondText.put("secondTextphoneNumber", number);
        secondText.put("secondTextphoneType", "Cell");
        secondText.put("secondTextsmsEnabledInd", "true");
        secondText.put("secondTextprimaryIndicator", "true");
        generalSave.get().put("secondTextAddress", secondText);
        Map<String, Object> data = new HashMap<>();
        data.put("phoneNumber", number);
        data.put("phoneType", "Cell");
        data.put("smsEnabledInd", "true");
        data.put("primaryIndicator", "true");
        return data;
    }

    public JsonObject editCaseLoaderAddConsumer(JsonObject request, String role) {
        Faker faker = new Faker();
        Map<String, String> newConsumer = new HashMap<>();
        newConsumer.put("consumerSSN", String.valueOf(faker.random().nextInt(100000000, 999999999)));
        newConsumer.put("consumerDateOfBirth", faker.random().nextInt(1920, 2000) + "-" + faker.random().nextInt(10, 12) + "-" + faker.random().nextInt(11, 28));
        newConsumer.put("consumerFirstName", RandomStringUtils.random(9, true, false));
        newConsumer.put("consumerLastName", RandomStringUtils.random(9, true, false));
        newConsumer.put("consumerRole", role);
        generalSave.get().put("secondConsumer", newConsumer);
        for (String consAttrib : newConsumer.keySet()) {
            request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(consAttrib, newConsumer.get(consAttrib));
        }
        request.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty("externalConsumerId", RandomStringUtils.random(15, true, true));
        return request;
    }

    public void createObLinkedToTask(String taskId) {
        List<String> consumerIds = new ArrayList<>();
        for (int index = 0; index < Integer.parseInt("1"); index++) {
            Map<String, String> caseConsumerIds = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId();
            consumerIds.add(caseConsumerIds.get("consumerId"));
            generalSave.get().put("caseId", caseConsumerIds.get("caseId"));
            APIUtilitiesForUIScenarios.consumerFirstName.set(String.valueOf(World.generalSave.get().get("firstName")));
            APIUtilitiesForUIScenarios.consumerLastName.set(String.valueOf(World.generalSave.get().get("lastName")));
        }
        generalSave.get().put("consumerIds", consumerIds);
        JsonArray recipients = new JsonArray();
        JsonArray regardingConsumerIds = new JsonArray();
        consumerIds.stream()
                .forEach(consumerId ->
                {
                    recipients.add(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateObRecipient(consumerId, "English"));
                    regardingConsumerIds.add(consumerId);
                });
        JsonObject request = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonObject anchor = new JsonObject();
        anchor.addProperty("caseId", String.valueOf(generalSave.get().get("caseId")));
        anchor.add("regardingConsumerId", regardingConsumerIds);
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.add("requester", requester);
        request.addProperty("correspondenceDefinitionMMSCode", "CCONLY");
        request.add("anchor", anchor);
        request.addProperty("taskId", taskId);
        request.add("recipients", recipients);
        JsonPath obTaskResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOBCorrespondenceLinkedToTask(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 201);
        String cid = obTaskResponse.getString("data.id");
        System.out.println("obtask cid - " + cid);
        generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), cid);
    }

    public Map<String, String> getCaseLoaderSecondRandomMailAddress(Map<String, String> data) {
        Faker faker = new Faker();
        Map<String, Object> secondMailAddress = new HashMap<>();
        secondMailAddress.put("secondMailaddressCity", faker.address().city());
        secondMailAddress.put("secondMailaddressCounty", faker.address().cityName());
        secondMailAddress.put("secondMailaddressState", faker.address().stateAbbr());
        secondMailAddress.put("secondMailaddressStreet1", faker.address().streetAddress());
        secondMailAddress.put("secondMailaddressStreet2", "Apt " + RandomStringUtils.random(4, false, true));
        secondMailAddress.put("secondMailaddressType", "Mailing");
        secondMailAddress.put("secondMailaddressVerified", "true");
        secondMailAddress.put("secondMailprimaryIndicator", "true"); //prefix here bc multiple primaryIndicator values in same map, but for different channels
        secondMailAddress.put("secondMailaddressZip", faker.address().zipCode());
        secondMailAddress.put("secondMailaddressZipFour", RandomStringUtils.random(4, false, true));
        generalSave.get().put("secondMailAddress", secondMailAddress);
        data.put("secondMailaddressCity", String.valueOf(secondMailAddress.get("addressCity")));
        data.put("secondMailaddressCounty", String.valueOf(secondMailAddress.get("secondMailaddressCounty")));
        data.put("secondMailaddressState", String.valueOf(secondMailAddress.get("secondMailaddressState")));
        data.put("secondMailaddressStreet1", String.valueOf(secondMailAddress.get("secondMailaddressStreet1")));
        data.put("secondMailaddressStreet2", String.valueOf(secondMailAddress.get("secondMailaddressStreet2")));
        data.put("secondMailaddressType", "Mailing");
        data.put("secondMailaddressVerified", "true");
        data.put("secondMailprimaryIndicator", "true"); //prefix here bc multiple primaryIndicator values in same map, but for different channels
        data.put("secondMailaddressZip", String.valueOf(secondMailAddress.get("secondMailaddressZip")));
        data.put("secondMailaddressZipFour", String.valueOf(secondMailAddress.get("secondMailaddressZipFour")));
        return data;
    }

    public Map<String, String> getCaseLoaderSecondRandomFax(Map<String, String> data) {
        Map<String, Object> secondFax = new HashMap<>();
        String phoneNumber = "";
        do {
            phoneNumber = RandomStringUtils.random(10, false, true);
        } while (phoneNumber.startsWith("0") || phoneNumber.startsWith("1"));
        secondFax.put("secondFaxphoneNumber", phoneNumber);
        secondFax.put("secondFaxphoneType", "Fax");
        secondFax.put("secondFaxsmsEnabledInd", "false");
        secondFax.put("secondFaxprimaryIndicator", "true");
        generalSave.get().put("secondFaxAddress", secondFax);
        data.put("secondFaxphoneNumber", phoneNumber);
        data.put("secondFaxphoneType", "Fax");
        data.put("secondFaxsmsEnabledInd", "false");
        data.put("secondFaxprimaryIndicator", "true");
        return data;
    }

    public Map<String, String> getCaseLoaderSecondRandomText(Map<String, String> data) {
        Map<String, Object> secondText = new HashMap<>();
        String phoneNumber = "";
        do {
            phoneNumber = RandomStringUtils.random(10, false, true);
        } while (phoneNumber.startsWith("0") || phoneNumber.startsWith("1"));
        secondText.put("secondTextphoneNumber", phoneNumber);
        secondText.put("secondTextphoneType", "Cell");
        secondText.put("secondTextsmsEnabledInd", "true");
        secondText.put("secondTextprimaryIndicator", "true");
        generalSave.get().put("secondTextAddress", secondText);
        data.put("secondTextphoneNumber", phoneNumber);
        data.put("secondTextphoneType", "Cell");
        data.put("secondTextsmsEnabledInd", "true");
        data.put("secondTextprimaryIndicator", "true");
        return data;
    }


    public void setRequestParams(JsonObject requestParams) {
        this.requestParams.set(requestParams);
    }

    public JsonObject addRecipientToObRequestFromCase(String caseId, List<String> channel, Map<String, String> requestData, JsonObject request) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRecipientsInfoByCaseNumber(caseId);
        List<Map<String, Object>> notificationList = new ArrayList<>();
        Map<String, Object> recipientKeys = new HashMap<>();
        //add consumer info
        Assert.assertNotNull(request);
        if (requestData.containsKey("consumerId") && requestData.get("consumerId").equalsIgnoreCase("null")) {
            //do not add
        } else {
            recipientKeys.put("consumerId", response.getString("consumers[0].consumerId"));
        }
        if (requestData.containsKey("externalRefType")) {
            recipientKeys.put("externalRefType", requestData.get("externalRefType"));
            generalSave.get().put("ReturnedNotificationExternalRefType" + Hooks.nameAndTags.get(), requestData.get("externalRefType"));
        }
        if (requestData.containsKey("externalRefId")) {
            String externalRefId = requestData.get("externalRefId");
            if (externalRefId.equalsIgnoreCase("random")) {
                externalRefId = RandomStringUtils.random(9, false, true);
            }
            recipientKeys.put("externalRefId", externalRefId);
            generalSave.get().put("ReturnedNotificationExternalRefID" + Hooks.nameAndTags.get(), externalRefId);
        }
        recipientKeys.put("firstName", BrowserUtils.filterForLettersOnly(response.getString("consumers[0].firstName")));
        recipientKeys.put("middleName", response.getString("consumers[0].middleName"));
        recipientKeys.put("lastName", response.getString("consumers[0].lastName"));
        //currently null from cc
//        recipientKeys.put("nameSuffix", String.valueOf(generalSave.get().get("consumerSuffix")));
        recipientKeys.put("nameSuffix", response.getString("consumers[0].suffix"));
        recipientKeys.put("role", response.getString("consumers[0].consumerRole"));
        if (requestData.containsKey("externalCaseIdCHIP") || requestData.containsKey("externalConsumerIdCHIP") || requestData.containsKey("externalCaseIdMedicaid") || requestData.containsKey("externalConsumerIdMedicaid")) {
            for (String externalId : requestData.keySet()) {
                switch (externalId.toLowerCase()) {
                    case "externalcaseidchip":
                        recipientKeys.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
                        break;
                    case "externalconsumeridchip":
                        recipientKeys.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
                        break;
                    case "externalcaseidmedicaid":
                        recipientKeys.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
                        break;
                    case "externalconsumeridmedicaid":
                        recipientKeys.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
                        break;
                }
            }
        }
        Faker faker = new Faker();
        for (String chan : channel) {
            switch (chan.toLowerCase()) {
                case "email":
                    if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
                        if (requestData.containsKey("emailAddress"))
                            recipientKeys.put("emailAddress", requestData.get("emailAddress"));
                        else
                            recipientKeys.put("emailAddress", faker.internet().emailAddress());
                        break;
                    }
                    if (requestData.containsKey("emailAddress")) {
                        recipientKeys.put("emailAddress", "workpcautomation@outlook.com");
                    } else if (requestData.containsKey("emailAddress2")) {
                        recipientKeys.put("emailAddress", "workpcautomation2@outlook.com");
                    } else {
                        recipientKeys.put("emailAddress", response.getString("consumers[0].channels.email.emailAddresses[0].emailAddress"));
                    }
                    break;
                case "fax":
                    if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
                        recipientKeys.put("faxNumber", BrowserUtils.validNumberFilter(RandomStringUtils.random(10, false, true)).replaceAll("0", "4").replaceAll("1", "5"));
                        break;
                    }
                    recipientKeys.put("faxNumber", response.getString("consumers[0].channels.fax.phoneNumbers[0].phoneNumber"));
                    break;
                case "text":
                    if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName().equalsIgnoreCase("DC-EB")) {
                        recipientKeys.put("textNumber", "9856326984");
                    } else if ("receiveSMS".equalsIgnoreCase(requestData.get("textNumber"))) {
                        recipientKeys.put("textNumber", String.valueOf(generalSave.get().get("receiveSMS")));
                    } else {
                        recipientKeys.put("textNumber", response.getString("consumers[0].channels.text.phoneNumbers[0].phoneNumber"));
                    }
                    break;
                case "mail":
                    if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
                        recipientKeys.put("streetAddress", faker.address().streetAddress());
                        recipientKeys.put("city", faker.address().cityName());
                        recipientKeys.put("state", faker.address().stateAbbr());
                        recipientKeys.put("zipcode", faker.address().zipCode());
                        recipientKeys.put("streetAddionalLine1", faker.address().streetAddress());
                        break;
                    }
                    recipientKeys.put("streetAddress", response.getString("consumers[0].channels.mail.addresses[0].addressLine1"));
                    recipientKeys.put("city", response.getString("consumers[0].channels.mail.addresses[0].city"));
                    recipientKeys.put("state", response.getString("consumers[0].channels.mail.addresses[0].state"));
                    if (requestData.containsKey("zip")) {
                        recipientKeys.put("zipcode", requestData.get("zip"));
                    } else {
                        recipientKeys.put("zipcode", response.getString("consumers[0].channels.mail.addresses[0].zip"));
                    }
                    if (null != response.getString("consumers[0].channels.mail.addresses[0].addressLine2")) {
                        recipientKeys.put("streetAddionalLine1", response.getString("consumers[0].channels.mail.addresses[0].addressLine2"));
                    }
                    break;
                case "zip":
                    //do nothing here
                    break;
                default:
                    Assert.fail("no matching channel for - \"" + chan + "\"");
            }
            Map<String, Object> temp = new HashMap<>();
            temp.put("channelType", chan);
            if (requestData.containsKey("digitallyRead")) {
                temp.put("digitallyRead", String.valueOf(generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get())));
            }
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName().equalsIgnoreCase("DC-EB")) {
                System.out.println("no need to add language at notification level");
            } else {
                temp.put("language", response.getString("consumers[0].preferredWrittenLanguage"));
            }
            notificationList.add(temp);
        }
        JsonObject recipient = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().buildObRecipientRequest(notificationList, recipientKeys);
        JsonArray recipients = new JsonArray();
        recipients.add(recipient);
        if (requestData.containsKey("recipients2")) {
            switch (requestData.get("recipients2")) {
                case "email":
                    recipientKeys.replace("emailAddress", "workpcautomation@outlook.com");
                    break;
                case "email2":
                    recipientKeys.replace("emailAddress", "workpcautomation2@outlook.com");
                    break;
                case "newExternalIds":
                    String externalCaseIdCHIP = RandomStringUtils.random(30, true, true);
                    recipientKeys.put("externalCaseIdCHIP", externalCaseIdCHIP);
                    generalSave.get().put("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get(), externalCaseIdCHIP);

                    String externalConsumerIdCHIP = RandomStringUtils.random(30, true, true);
                    recipientKeys.put("externalConsumerIdCHIP", externalConsumerIdCHIP);
                    generalSave.get().put("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get(), externalConsumerIdCHIP);

                    String externalCaseIdMedicaid = RandomStringUtils.random(30, true, true);
                    recipientKeys.put("externalCaseIdMedicaid", externalCaseIdMedicaid);
                    generalSave.get().put("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get(), externalCaseIdMedicaid);

                    String externalConsumerIdMedicaid = RandomStringUtils.random(30, true, true);
                    recipientKeys.put("externalConsumerIdMedicaid", externalConsumerIdMedicaid);
                    generalSave.get().put("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get(), externalConsumerIdMedicaid);

                    generalSave.get().put("externalIdsSecondRecipient" + Hooks.nameAndTags.get(), 1);
                    break;
                default:
                    Assert.fail("Updated field didn't match");
            }
            JsonObject recipient2 = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().buildObRecipientRequest(notificationList, recipientKeys);
            recipients.add(recipient2);
        }
        request.add("recipients", recipients);
        return request;
    }

    public JsonObject addRecipientToObRequestFromConsumerNumber(String
                                                                        consumerId, List<String> channel, Map<String, String> requestData, JsonObject request) {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRecipientsInfoByConsumerNumber(consumerId);
        List<Map<String, Object>> notificationList = new ArrayList<>();
        Map<String, Object> recipientKeys = new HashMap<>();
        //add consumer info
        recipientKeys.put("consumerId", response.getString("consumers[0].consumerId"));
        recipientKeys.put("firstName", response.getString("consumers[0].firstName"));
        recipientKeys.put("lastName", response.getString("consumers[0].lastName"));
        recipientKeys.put("role", response.getString("consumers[0].consumerRole"));
        for (String chan : channel) {
            switch (chan.toLowerCase()) {
                case "email":
                    if (requestData.containsKey("emailAddress")) {
                        recipientKeys.put("emailAddress", "workpcautomation@outlook.com");
                    } else {
                        recipientKeys.put("emailAddress", response.getString("consumers[0].channels.email.emailAddresses[0].emailAddress"));
                    }
                    break;
                case "fax":
                    recipientKeys.put("faxNumber", response.getString("consumers[0].channels.fax.phoneNumbers[0].phoneNumber"));
                    break;
                case "text":
                    if (requestData.containsKey("textNumber")) {
                        if (requestData.get("textNumber").equalsIgnoreCase("random")) {
                            String phoneNumber = "";
                            do {
                                phoneNumber = RandomStringUtils.random(10, false, true);
                            } while (phoneNumber.startsWith("0") || phoneNumber.startsWith("1"));
                            recipientKeys.put("textNumber", phoneNumber);
                        }
                    }
                    recipientKeys.put("emailAddress", response.getString("consumers[0].channels.text.phoneNumbers[0].phoneNumber"));
                    break;
                case "mail":
                    recipientKeys.put("streetAddress", response.getString("consumers[0].channels.mail.addresses[0].addressLine1"));
                    recipientKeys.put("city", response.getString("consumers[0].channels.mail.addresses[0].city"));
                    recipientKeys.put("state", response.getString("consumers[0].channels.mail.addresses[0].state"));
                    if (requestData.containsKey("zip")) {
                        recipientKeys.put("zipcode", requestData.get("zip"));
                    } else {
                        recipientKeys.put("zipcode", response.getString("consumers[0].channels.mail.addresses[0].zip"));
                    }
                    if (null != response.getString("consumers[0].channels.mail.addresses[0].addressLine2")) {
                        recipientKeys.put("streetAddionalLine1", response.getString("consumers[0].channels.mail.addresses[0].addressLine2"));
                    }
                    break;
                case "zip":
                    //do nothing here
                    break;
                default:
                    Assert.fail("no matching channel for - \"" + chan + "\"");
            }
            Map<String, Object> temp = new HashMap<>();
            temp.put("channelType", chan);
            temp.put("language", response.getString("consumers[0].preferredWrittenLanguage"));
            notificationList.add(temp);
        }
        JsonObject recipient = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().buildObRecipientRequest(notificationList, recipientKeys);
        JsonArray recipients = new JsonArray();
        recipients.add(recipient);
        request.add("recipients", recipients);
        return request;
    }

    public Message retrieveEmailByRecipientName(String consumerFirstName, String consumerLastName, String
            emailAccount) {
        Message message = null;
        switch (emailAccount) {
            case "workpcautomation@outlook.com":
                BrowserUtils.waitFor(30);
                Message[] messages = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(emailAccount);
                if (messages.length < 1) {
                    for (int retries = 5; retries > 0; retries--) {
                        BrowserUtils.waitFor(30);
                        System.out.println("############ retrying email fetch - retries left > " + retries + " ########");
                        messages = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(String.valueOf(generalSave.get().get("emailAddress")));
                        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().messages = messages;
                        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().closeEmailResources();
                    }
                }
                message = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().findMessageByRecipientName(consumerFirstName, consumerLastName, messages);
                break;
            case "workpcautomation2@outlook.com":
                BrowserUtils.waitFor(30);
                Message[] messages2 = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(emailAccount);
                if (messages2.length < 1) {
                    for (int retries = 5; retries > 0; retries--) {
                        BrowserUtils.waitFor(30);
                        System.out.println("############ retrying email fetch - retries left > " + retries + " ########");
                        messages2 = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(String.valueOf(generalSave.get().get("emailAddress")));
                        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().messages = messages2;
                        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().closeEmailResources();
                    }
                }
                message = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().findMessageByRecipientName(consumerFirstName, consumerLastName, messages2);
                break;
            default:
                Assert.fail("no matching cases for - \"" + emailAccount + "\"");

        }
        return message;
    }

    public void clearOldEmails(String email) {
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().fetchEmails(email);
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().deleteAllMessages();
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().closeEmailResources();
    }

    public void verifySenderEmailFromSettings() {
        JsonPath obSettingsResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        //verify sender email address
        Message message = (Message) generalSave.get().get("emailMessage");
        Assert.assertNotNull(message);
        try {
            Assert.assertEquals(BrowserUtils.filterForLettersOnly(message.getFrom()[0].toString()).substring(10), BrowserUtils.filterForLettersOnly(obSettingsResponse.getString("correspondenceSettingsResponse.outboundSMTPDetails[0].senderEmailAddress")));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /*
    relies on ob def setup for "emailOnly2" in blcrm
     */
    public void verifySenderEmailFromChannelDef() {
        String emailOnly2Cid = "1328";
        if (System.getProperty("env").equalsIgnoreCase("qa")) {
            emailOnly2Cid = "8972";
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObDefinition(emailOnly2Cid);
        Message message = (Message) generalSave.get().get("emailMessage");
        Assert.assertNotNull(message);
        try {
            Assert.assertEquals(BrowserUtils.filterForLettersOnly(message.getFrom()[0].toString()).substring(10), BrowserUtils.filterForLettersOnly(response.getString("correspondenceData.channelDefinitions[0].senderEmailId")));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void verifyZipCodeInRecipientInfo(JsonPath response, Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "zip":
                    Assert.assertEquals(response.getString("recipients[0].recipientInfo.zipcode"), data.get(key));
                    break;
                default:
                    Assert.assertEquals(response.getString("recipients[0].recipientInfo." + key), data.get(key));
            }
        }
    }

    public void verifyZipCodeInLetterData(JsonPath letterData, Map<String, String> data) {
        for (String key : data.keySet()) {
            switch (key) {
                case "zip":
                    Assert.assertEquals(letterData.getString("recipient.addressZip"), data.get(key));
                    break;
                default:
                    Assert.assertEquals(letterData.getString("recipient." + key), data.get(key));
            }
        }
    }

    public void verifyMailAddressObSearchResultsInUI(JsonPath response) {
        BrowserUtils.waitFor(5);
        Assert.assertNotNull(response);
        String expected = "";
        StringBuilder recipAddress = new StringBuilder();
        recipAddress.append(response.getString("recipients[0].recipientInfo.streetAddress")).append(",");
        if (response.getString("recipients[0].recipientInfo.streetAddionalLine1") != null) {
            recipAddress.append(response.getString("recipients[0].recipientInfo.streetAddionalLine1")).append(",");
        }
        recipAddress.append(response.getString("recipients[0].recipientInfo.city")).append(",");
        recipAddress.append(response.getString("recipients[0].recipientInfo.state")).append(",");
        recipAddress.append(response.getString("recipients[0].recipientInfo.zipcode"));
        expected = recipAddress.toString();
        Assert.assertEquals(expected, Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + expected + "')]")).getText());
    }

    public void verifyMailAddressObDetailsPageInUI(JsonPath response) {
        browserUtils.get().waitForPageToLoad(10);
        BrowserUtils.waitFor(5);
        Assert.assertNotNull(response);
        String expected = "";
        StringBuilder recipAddress = new StringBuilder();
        recipAddress.append(response.getString("recipients[0].recipientInfo.streetAddress")).append(" ");
        if (response.getString("recipients[0].recipientInfo.streetAddionalLine1") != null) {
            recipAddress.append(response.getString("recipients[0].recipientInfo.streetAddionalLine1")).append(" ");
        }
        recipAddress.append(response.getString("recipients[0].recipientInfo.city")).append(",");
        recipAddress.append(response.getString("recipients[0].recipientInfo.state")).append(" ");
        recipAddress.append(response.getString("recipients[0].recipientInfo.zipcode"));
        expected = recipAddress.toString();
        Assert.assertEquals(expected, Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + expected + "')]")).getText());
    }

    public boolean verifyReceivedSMSText(String phoneNumber) {
        boolean found = false;
        uiAutoUitilities.get().navToReceiveSmsTextPage();
        String senderNumber = BrowserUtils.validNumberFilter(Driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td")).getText().trim());
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath obDetails = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        String mmsCode = obDetails.getString("correspondenceDefinitionMMSCode");
        switch (mmsCode) {
            case "textOnly":
                if ("this is a test twilio template".equalsIgnoreCase(Driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText().trim()) && senderNumber.equalsIgnoreCase(phoneNumber)) {
                    found = true;
                }
                break;
            case "textPlus":
                System.out.println(mmsCode);
                if ("this is a test twilio template".equalsIgnoreCase(Driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText().trim()) && senderNumber.equalsIgnoreCase(phoneNumber)) {
                    found = true;
                }
                break;
            case "nowAvail1":
            case "nowAvail2":
                System.out.println(mmsCode);
                if (Driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText().contains("this is a test twilio template") && senderNumber.equalsIgnoreCase(phoneNumber)) {
                    found = true;
                }
                break;
            default:
                Assert.fail("no matching cases");
        }
        return found;
    }

    public void validateSearchCorrespondenceDigitallyRead(String nid) {
        String caseId = "";
        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        caseId = caseConsumerId.get("caseId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchCorrespondence(caseId);
        List<Map<String, Object>> content = response.getList("data.content");
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        String actual = "";
        boolean found = false;
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                List<Map<String, Object>> temp2 = (List<Map<String, Object>>) temp.get(0).get("notifications");
                for (Map<String, Object> nids : temp2) {
                    if (nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                        actual = String.valueOf(nids.get("digitallyRead"));
                        found = true;
                        break;
                    }
                }

            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(String.valueOf(generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get())), actual);
    }

    public void validateGlobalSearchCorrespondenceDigitallyRead(String nid) {
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request);
        List<Map<String, Object>> content = response.getList("data.content");
        String actual = "";
        boolean found = false;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                List<Map<String, Object>> temp2 = (List<Map<String, Object>>) temp.get(0).get("notifications");
                for (Map<String, Object> nids : temp2) {
                    if (nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                        actual = String.valueOf(nids.get("digitallyRead"));
                        found = true;
                        break;
                    }
                }

            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(String.valueOf(generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get())), actual);
    }

    public void validateGetCorrespondenceDigitallyRead(String nid) {
        String cid = "";
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("newlyCreatedNotification")) {
            nid = String.valueOf(generalSave.get().get("newNid" + Hooks.nameAndTags.get()));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            nid = response.getString("recipients[0].notifications[0].notificationId");
        }
        String actual = "";
        boolean found = false;
        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients");
        for (Map<String, Object> recips : temp) {
            List<Map<String, Object>> temp2 = (List<Map<String, Object>>) recips.get("notifications");
            for (Map<String, Object> nids : temp2) {
                if (nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                    actual = String.valueOf(nids.get("digitallyRead"));
                    found = true;
                    break;
                }
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(String.valueOf(generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get())), actual);
    }

    public void createNotification(String cid, String nid, Map<String, String> data) {
        JsonObject request = new JsonObject();
        JsonObject recipient = new JsonObject();
        BrowserUtils.waitFor(10);
        String recipientId = "invalid never assigned";
        JsonPath getResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        boolean newRecipientRecordExpected = false;
        for (String key : data.keySet()) {
            switch (key) {
                case "recipient":
                    if ("previouslyCreated".equalsIgnoreCase(data.get(key))) {
                        recipientId = getResponse.getString("recipients[0].recipientInfo.correspondenceRecipientId");
                        recipient.addProperty("correspondenceRecipientId", recipientId);
                        generalSave.get().put("correspondenceRecipientId" + Hooks.nameAndTags.get(), recipientId);
                    }
                    break;
                case "NewNotificationChannel":
                    request.addProperty("notificationChannelType", data.get(key));
                    generalSave.get().put("NewNotificationChannel" + Hooks.nameAndTags.get(), data.get(key));
                    break;
                case "notificationsExpected":
                    generalSave.get().put("notificationsExpected" + Hooks.nameAndTags.get(), data.get(key));
                    break;
                case "destination":
                    if ("sameDestination".equalsIgnoreCase(data.get(key))) {
                        JsonObject recipientTemp = ((JsonObject) World.generalSave.get().get("ObFullRequestJson")).getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo");
                        Map<String, String> ObFullRequestJson = new Gson().fromJson(recipientTemp, HashMap.class);
                        for (String recipInfo : ObFullRequestJson.keySet()) {
                            if (recipInfo.equalsIgnoreCase("externalIds")) {
                                recipient.add("externalIds", new Gson().toJsonTree(ObFullRequestJson.get("externalIds")).getAsJsonObject());
                                continue;
                            }
                            recipient.addProperty(recipInfo, ObFullRequestJson.get(recipInfo));
                        }
                    } else if ("NewNotificationDestination".equalsIgnoreCase(data.get(key))) {
                        JsonObject recipientTemp = ((JsonObject) World.generalSave.get().get("ObFullRequestJson")).getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo");
                        Map<String, String> ObFullRequestJson = new Gson().fromJson(recipientTemp, HashMap.class);
                        for (String recipInfo : ObFullRequestJson.keySet()) {
                            if (recipInfo.equalsIgnoreCase("externalIds")) {
                                recipient.add("externalIds", new Gson().toJsonTree(ObFullRequestJson.get("externalIds")).getAsJsonObject());
                                continue;
                            }
                            recipient.addProperty(recipInfo, ObFullRequestJson.get(recipInfo));
                        }
                        Map<String, String> newDestination = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().generateRandomDestinationInformation(data.get("NewNotificationChannel"));
                        generalSave.get().put("NewNotificationDestination" + Hooks.nameAndTags.get(), newDestination);
                        Assert.assertNotNull(newDestination);
                        for (String dest : newDestination.keySet()) {
                            recipient.addProperty(dest, newDestination.get(dest));
                        }
                    }
                    break;
                case "NewlyCreatedCorrespondenceRecipientId":
                    newRecipientRecordExpected = Boolean.parseBoolean(data.get(key));
                    break;
                case "externalCaseIdCHIP":
                case "externalConsumerIdCHIP":
                case "externalCaseIdMedicaid":
                case "externalConsumerIdMedicaid":
                    //do nothing here
                    break;
                case "digitallyRead":
                    request.addProperty("digitallyRead", data.get(key));
                    break;
                case "language":
                    request.addProperty("language", data.get(key));
                    break;

                default:
                    Assert.fail("no matching cases - " + key);
            }
        }
        //include invalid external ids
        if (data.containsKey("externalCaseIdCHIP") || data.containsKey("externalConsumerIdCHIP") || data.containsKey("externalCaseIdMedicaid") || data.containsKey("externalConsumerIdMedicaid")) {
            JsonObject externalIds = new JsonObject();
            for (String externalId : data.keySet()) {
                switch (externalId) {
                    case "externalCaseIdCHIP":
                    case "externalConsumerIdCHIP":
                    case "externalCaseIdMedicaid":
                    case "externalConsumerIdMedicaid":
                        externalIds.addProperty(externalId, data.get(externalId));
                        break;
                }
            }
            recipient.add("externalIds", externalIds);
        }
        request.addProperty("correspondenceId", cid);
        request.addProperty("createdBy", "4444");
        String channelDefinitionId = getResponse.getString("recipients[0].notifications[0].channelDefinitionId");
        Assert.assertNotNull(channelDefinitionId);
        request.addProperty("channelDefinitionId", channelDefinitionId);
        request.addProperty("updatedBy", "5555");
        request.addProperty("notificationId", nid);
        request.add("recipient", recipient);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().postNotification(cid, request);
        if (newRecipientRecordExpected) {
            recipientId = response.getString("recipient.correspondenceRecipientId");
            Assert.assertNotNull(recipientId);
            generalSave.get().put("NewlyCreatedCorrespondenceRecipientId" + Hooks.nameAndTags.get(), recipientId);
        }
        Assert.assertNotNull(response.getString("notificationId"));
        generalSave.get().put("newNid" + Hooks.nameAndTags.get(), response.getString("notificationId"));
        generalSave.get().put("newNidResponse" + Hooks.nameAndTags.get(), response);
        generalSave.get().put("newNotificationFeatureFileData" + Hooks.nameAndTags.get(), data);
    }

    public void validateGetNotificationsCorrespondenceDigitallyRead(String nid) {
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnotifications(cid);
        List<Map<String, Object>> content = response.get();
        String actual = "";
        boolean found = false;
        for (Map<String, Object> nids : content) {
            if (nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                actual = String.valueOf(nids.get("digitallyRead"));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(String.valueOf(generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get())), actual);
    }

    public void getNotificationFromOrderService(String notificationId) {
        response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getNotificationOrderService(Integer.parseInt(notificationId)));
    }

    public boolean verifyGetNotificationResponseIsBase64Encoded() {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        String data = response.get().getString("data");
        String regex = "([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        Pattern mp = Pattern.compile(regex);
        Matcher mm = mp.matcher(data);
        Boolean flag = mm.matches();
        return flag;

    }

    public void verifyGetNotificationFromOrderServiceErrorMessage(String errorMessage) {
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 404, "Expected Status Code mismatched");
        String actualErrorMessage = response.get().get("errors[0].message");
        switch (errorMessage) {
            case "document_does_not_exist_for_notification_id":
                Assert.assertEquals(actualErrorMessage, "document does not exist for Notification id : " + generalSave.get().get("notificationId").toString());
                break;
            default:
                Assert.assertEquals(actualErrorMessage, errorMessage);
        }
    }

    public void verifyDecodedNotificationString(String expectedString) {
        String data = response.get().getString("data");
        if (expectedString.equalsIgnoreCase("TwilioTemplateWithSpecialCharacters")) {
            expectedString = "Ñáéíóúñü¿¡~`!@#$%^&*()_-=+[{]}\\|;:'\",<.>/?";
        }
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getDecryptedString(data).trim().replace(" ", "").contains(expectedString.replace(" ", "")), "Decoded String mismatched for get Notification response.");
    }

    /**
     * verify new recipient created for scenarios with existing ob has only 1 recipient
     */
    public void verifyNewRecipientRecordCreated() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath actualJson = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, String>> recipients = actualJson.getList("recipients.recipientInfo");
        Assert.assertEquals(recipients.size(), 2);
        //verify new recipient record copying from old
        String oldRecipientId = String.valueOf(generalSave.get().get("correspondenceRecipientId" + Hooks.nameAndTags.get()));
        Assert.assertNotNull(oldRecipientId);
        verifyRecipientInformationCopiedFromOld(recipients, oldRecipientId);
        String channel = String.valueOf(generalSave.get().get("NewNotificationChannel" + Hooks.nameAndTags.get()));
        List<Map<String, Object>> notifications1 = actualJson.getList("recipients[0].notifications");
        List<Map<String, Object>> notifications2 = actualJson.getList("recipients[1].notifications");
        Assert.assertEquals(notifications1.size() + notifications2.size(), Integer.parseInt(String.valueOf(generalSave.get().get("notificationsExpected" + Hooks.nameAndTags.get()))));
        String oldNid = String.valueOf(generalSave.get().get("nid"));
        Map<String, String> data = (Map<String, String>) generalSave.get().get("newNotificationFeatureFileData" + Hooks.nameAndTags.get());
        for (Map<String, Object> notification : notifications2) {
            if (!oldNid.equalsIgnoreCase(String.valueOf(notification.get("notificationId")))) {
                Assert.assertEquals(String.valueOf(notification.get("createdBy")), "4444");
                Assert.assertEquals(String.valueOf(notification.get("updatedBy")), "5555");
                Assert.assertEquals(String.valueOf(notification.get("channelType")), data.get("NewNotificationChannel"));
            }
        }
        Map<String, String> newExpectedDestination = (Map<String, String>) generalSave.get().get("NewNotificationDestination" + Hooks.nameAndTags.get());
        Assert.assertNotNull(newExpectedDestination);
        //get both recipients
        Map<String, String> actualRecipientInfoOld = actualJson.getMap("recipients[0].recipientInfo");
        Map<String, String> actualRecipientInfoNew = actualJson.getMap("recipients[1].recipientInfo");
        Assert.assertNotNull(actualRecipientInfoOld);
        Assert.assertNotNull(actualRecipientInfoNew);
        verifyRecipientOnlyContainsOneDestination(newExpectedDestination, actualRecipientInfoNew, channel);
    }

    private void verifyRecipientOnlyContainsOneDestination22(String
                                                                     channel, Map<String, String> data, Map<String, String> expectedDestination, Map<String, String> actualRecipientInfo) {
        switch (channel) {
            case "Mail":
                Assert.assertEquals(actualRecipientInfo.get("zipcode"), expectedDestination.get("zipcode"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddionalLine1"), expectedDestination.get("streetAddionalLine1"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddress"), expectedDestination.get("streetAddress"));
                Assert.assertEquals(actualRecipientInfo.get("city"), expectedDestination.get("city"));
                Assert.assertEquals(actualRecipientInfo.get("state"), expectedDestination.get("state"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }

                break;
            case "Email":
                Assert.assertEquals(actualRecipientInfo.get("emailAddress"), expectedDestination.get("emailAddress"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }
                break;
            case "Fax":
                Assert.assertEquals(actualRecipientInfo.get("faxNumber"), expectedDestination.get("faxNumber"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                break;
            case "Text":
                Assert.assertEquals(actualRecipientInfo.get("textNumber"), expectedDestination.get("textNumber"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    private void verifyRecipientInformationCopiedFromOld(List<Map<String, String>> recipients, String
            oldRecipientId) {
        JsonObject recipientTemp = ((JsonObject) World.generalSave.get().get("ObFullRequestJson")).getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo");
        Map<String, String> expectedRecipientInfo = new Gson().fromJson(recipientTemp, HashMap.class);
        String postNotificationNewRecipientIdFromResponse = String.valueOf(generalSave.get().get("NewlyCreatedCorrespondenceRecipientId" + Hooks.nameAndTags.get()));
        boolean foundNewRecipient = false;
        for (Map<String, String> recipient : recipients) {
            if (Integer.parseInt(recipient.get("correspondenceRecipientId")) > Integer.parseInt(oldRecipientId) && Integer.parseInt(recipient.get("correspondenceRecipientId")) == Integer.parseInt(postNotificationNewRecipientIdFromResponse)) {
                foundNewRecipient = true;
                Assert.assertEquals(recipient.get("firstName"), expectedRecipientInfo.get("firstName"));
                Assert.assertEquals(recipient.get("lastName"), expectedRecipientInfo.get("lastName"));
                Assert.assertEquals(recipient.get("middleName"), expectedRecipientInfo.get("middleName"));
                Assert.assertEquals(recipient.get("nameSuffix"), expectedRecipientInfo.get("nameSuffix"));
                Assert.assertEquals(recipient.get("role"), expectedRecipientInfo.get("role"));
                Assert.assertEquals(recipient.get("consumerId"), expectedRecipientInfo.get("consumerId"));
            }
        }
        Assert.assertTrue(foundNewRecipient);
    }

    public void verifyOldRecipientRecordUpdated() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath actualJson = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, String>> recipients = actualJson.getList("recipients");
        Map<String, String> actualRecipientInfo = actualJson.getMap("recipients[0].recipientInfo");
        Assert.assertNotNull(actualRecipientInfo);
        JsonObject recipientTemp = ((JsonObject) World.generalSave.get().get("ObFullRequestJson")).getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo");
        Map<String, String> expectedRecipientInfo = new Gson().fromJson(recipientTemp, HashMap.class);
        String channel = actualJson.getString("recipients[0].notifications[0].channelType");
        List<Map<String, Object>> notifications = actualJson.getList("recipients[0].notifications");
        Assert.assertEquals(notifications.size(), 2);
        String oldNid = String.valueOf(generalSave.get().get("nid"));
        Map<String, String> data = (Map<String, String>) generalSave.get().get("newNotificationFeatureFileData" + Hooks.nameAndTags.get());
        Assert.assertNotNull(data);
        for (Map<String, Object> notification : notifications) {
            if (!oldNid.equalsIgnoreCase(String.valueOf(notification.get("notificationId")))) {
                Assert.assertEquals(String.valueOf(notification.get("createdBy")), "4444");
                Assert.assertEquals(String.valueOf(notification.get("updatedBy")), "5555");
                Assert.assertEquals(String.valueOf(notification.get("channelType")), data.get("NewNotificationChannel"));
            }
        }
        switch (channel) {
            case "Mail":
                Assert.assertEquals(actualRecipientInfo.get("zipcode"), expectedRecipientInfo.get("zipcode"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddionalLine1"), expectedRecipientInfo.get("streetAddionalLine1"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddress"), expectedRecipientInfo.get("streetAddress"));
                Assert.assertEquals(actualRecipientInfo.get("city"), expectedRecipientInfo.get("city"));
                Assert.assertEquals(actualRecipientInfo.get("state"), expectedRecipientInfo.get("state"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }

                break;
            case "Email":
                Assert.assertEquals(actualRecipientInfo.get("emailAddress"), expectedRecipientInfo.get("emailAddress"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }
                break;
            case "Fax":
                Assert.assertEquals(actualRecipientInfo.get("faxNumber"), expectedRecipientInfo.get("faxNumber"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Text")) {
                    Assert.assertNull(actualRecipientInfo.get("textNumber"));
                }
                break;
            case "Text":
                Assert.assertEquals(actualRecipientInfo.get("textNumber"), expectedRecipientInfo.get("textNumber"));
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Fax")) {
                    Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Email")) {
                    Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                }
                if (!data.get("NewNotificationChannel").equalsIgnoreCase("Mail")) {
                    Assert.assertNull(actualRecipientInfo.get("zipcode"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                    Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                    Assert.assertNull(actualRecipientInfo.get("city"));
                    Assert.assertNull(actualRecipientInfo.get("state"));
                }
                break;
            default:
                Assert.fail("no matching case");
        }
        Assert.assertEquals(recipients.size(), 1);
    }

    public void verifyOldRecipientRecordStayedSame() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath actualJson = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, String>> recipients = actualJson.getList("recipients");
        Map<String, String> actualRecipientInfo = actualJson.getMap("recipients[0].recipientInfo");
        Assert.assertNotNull(actualRecipientInfo);
        JsonObject recipientTemp = ((JsonObject) World.generalSave.get().get("ObFullRequestJson")).getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo");
        Map<String, String> expectedRecipientInfo = new Gson().fromJson(recipientTemp, HashMap.class);
        String channel = actualJson.getString("recipients[0].notifications[0].channelType");
        List<Map<String, Object>> notifications = actualJson.getList("recipients[0].notifications");
        Assert.assertEquals(notifications.size(), 2);
        String oldNid = String.valueOf(generalSave.get().get("nid"));
        for (Map<String, Object> notification : notifications) {
            if (!oldNid.equalsIgnoreCase(String.valueOf(notification.get("notificationId")))) {
                Assert.assertEquals(String.valueOf(notification.get("createdBy")), "4444");
                Assert.assertEquals(String.valueOf(notification.get("updatedBy")), "5555");
                Assert.assertEquals(String.valueOf(notification.get("channelType")), channel);
            }
        }
        verifyRecipientOnlyContainsOneDestination(actualRecipientInfo, expectedRecipientInfo, channel);
        Assert.assertEquals(recipients.size(), 1);
    }

    private void verifyRecipientOnlyContainsOneDestination
            (Map<String, String> actualRecipientInfo, Map<String, String> expectedRecipientInfo, String channel) {
        switch (channel) {
            case "Mail":
                Assert.assertEquals(actualRecipientInfo.get("zipcode"), expectedRecipientInfo.get("zipcode"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddionalLine1"), expectedRecipientInfo.get("streetAddionalLine1"));
                Assert.assertEquals(actualRecipientInfo.get("streetAddress"), expectedRecipientInfo.get("streetAddress"));
                Assert.assertEquals(actualRecipientInfo.get("city"), expectedRecipientInfo.get("city"));
                Assert.assertEquals(actualRecipientInfo.get("state"), expectedRecipientInfo.get("state"));
                Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                Assert.assertNull(actualRecipientInfo.get("textNumber"));
                Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                break;
            case "Email":
                Assert.assertEquals(actualRecipientInfo.get("emailAddress"), expectedRecipientInfo.get("emailAddress"));
                Assert.assertNull(actualRecipientInfo.get("zipcode"));
                Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                Assert.assertNull(actualRecipientInfo.get("city"));
                Assert.assertNull(actualRecipientInfo.get("state"));
                Assert.assertNull(actualRecipientInfo.get("textNumber"));
                Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                break;
            case "Fax":
                Assert.assertEquals(actualRecipientInfo.get("faxNumber"), expectedRecipientInfo.get("faxNumber"));
                Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                Assert.assertNull(actualRecipientInfo.get("zipcode"));
                Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                Assert.assertNull(actualRecipientInfo.get("city"));
                Assert.assertNull(actualRecipientInfo.get("state"));
                Assert.assertNull(actualRecipientInfo.get("textNumber"));
                break;
            case "Text":
                Assert.assertEquals(actualRecipientInfo.get("textNumber"), expectedRecipientInfo.get("textNumber"));
                Assert.assertNull(actualRecipientInfo.get("faxNumber"));
                Assert.assertNull(actualRecipientInfo.get("emailAddress"));
                Assert.assertNull(actualRecipientInfo.get("zipcode"));
                Assert.assertNull(actualRecipientInfo.get("streetAddionalLine1"));
                Assert.assertNull(actualRecipientInfo.get("streetAddress"));
                Assert.assertNull(actualRecipientInfo.get("city"));
                Assert.assertNull(actualRecipientInfo.get("state"));
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    public void verifyDestinationInformationInLetterDataPostNotification
            (Map<String, String> expectedRecipientInformation, JsonPath letterDataResponse, String channel) {
        Assert.assertEquals(expectedRecipientInformation.get("firstName"), letterDataResponse.getString("recipient.recipientFirstName"));
        Assert.assertEquals(expectedRecipientInformation.get("middleName"), letterDataResponse.getString("recipient.recipientMiddleName"));
        Assert.assertEquals(expectedRecipientInformation.get("lastName"), letterDataResponse.getString("recipient.recipientLastName"));
        Assert.assertEquals(expectedRecipientInformation.get("nameSuffix"), letterDataResponse.getString("recipient.recipientNameSuffix"));
        switch (channel) {
            case "Email":
                Assert.assertEquals(expectedRecipientInformation.get("emailAddress"), letterDataResponse.getString("recipient.emailAddress"));
                break;
            case "Mail":
                Assert.assertEquals(expectedRecipientInformation.get("streetAddress"), letterDataResponse.getString("recipient.addressLine1"));
                Assert.assertEquals(expectedRecipientInformation.get("streetAddionalLine1"), letterDataResponse.getString("recipient.addressLine2"));
                Assert.assertEquals(expectedRecipientInformation.get("city"), letterDataResponse.getString("recipient.addressCity"));
                Assert.assertEquals(expectedRecipientInformation.get("state"), letterDataResponse.getString("recipient.addressStateAbbr"));
                Assert.assertEquals(expectedRecipientInformation.get("zipcode"), letterDataResponse.getString("recipient.addressZip"));
                break;
            case "Fax":
                Assert.assertEquals(expectedRecipientInformation.get("faxNumber"), letterDataResponse.getString("recipient.faxNumber"));
                break;
            case "Text":
                Assert.assertEquals(expectedRecipientInformation.get("textNumber"), letterDataResponse.getString("recipient.smsNumber"));
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    public Map<String, String> getRecipientInformation(JsonPath actualJson, String recipientId) {
        Map<String, String> recipientInfo = new HashMap<>();
        List<Map<String, String>> recipients = actualJson.getList("recipients.recipientInfo");
        boolean foundNewRecipient = false;
        for (Map<String, String> recipient : recipients) {
            if (Integer.parseInt(recipient.get("correspondenceRecipientId")) == Integer.parseInt(recipientId)) {
                foundNewRecipient = true;
                recipientInfo.put("firstName", recipient.get("firstName"));
                recipientInfo.put("lastName", recipient.get("lastName"));
                recipientInfo.put("middleName", recipient.get("middleName"));
                recipientInfo.put("nameSuffix", recipient.get("nameSuffix"));
            }
        }
        Assert.assertTrue(foundNewRecipient);
        return recipientInfo;
    }

    public void addDestinationInformation(String channel, Map<String, String> expectedRecipientInformation) {
        Map<String, String> newDestination = (Map<String, String>) generalSave.get().get("NewNotificationDestination" + Hooks.nameAndTags.get());
        Assert.assertNotNull(newDestination);
        switch (channel) {
            case "Email":
                expectedRecipientInformation.put("emailAddress", newDestination.get("emailAddress"));
                break;
            case "Mail":
                expectedRecipientInformation.put("streetAddress", newDestination.get("streetAddress"));
                expectedRecipientInformation.put("streetAddionalLine1", newDestination.get("streetAddionalLine1"));
                expectedRecipientInformation.put("city", newDestination.get("city"));
                expectedRecipientInformation.put("state", newDestination.get("state"));
                expectedRecipientInformation.put("zipcode", newDestination.get("zipcode"));
                break;
            case "Fax":
                expectedRecipientInformation.put("faxNumber", newDestination.get("faxNumber"));
                break;
            case "Text":
                expectedRecipientInformation.put("textNumber", newDestination.get("textNumber"));
                break;
            default:
                Assert.fail("no matching case");
        }
    }

    public void validateSearchCorrespondenceExternalids(String nid) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        int recipientIndex = 0;
        String caseId = "";
        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        caseId = caseConsumerId.get("caseId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchCorrespondence(caseId);
        List<Map<String, Object>> content = response.getList("data.content");
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        boolean found = false;
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                found = true;
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                Map<String, Object> temp2 = (Map<String, Object>) temp.get(recipientIndex).get("recipientInfo");
                actualIds = (Map<String, String>) temp2.get("externalIds");
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateSearchCorrespondenceExternalids(String nid, int recipientIndex) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        String caseId = "";
        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        caseId = caseConsumerId.get("caseId");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().searchCorrespondence(caseId);
        List<Map<String, Object>> content = response.getList("data.content");
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        boolean found = false;
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                found = true;
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                Map<String, Object> temp2 = (Map<String, Object>) temp.get(recipientIndex).get("recipientInfo");
                actualIds = (Map<String, String>) temp2.get("externalIds");
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGetCorrespondenceExternalids(String nid) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        int recipientIndex = 0;
        String cid = "";
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("newlyCreatedNotification")) {
            nid = String.valueOf(generalSave.get().get("newNid" + Hooks.nameAndTags.get()));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            nid = response.getString("recipients[0].notifications[0].notificationId");
        }

        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients.recipientInfo");
        actualIds = (Map<String, String>) temp.get(recipientIndex).get("externalIds");
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGetCorrespondenceExternalids() {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        int recipientIndex = 0;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        Assert.assertNotNull(cid, "cid is null");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients.recipientInfo");
        for (Map<String, Object> recipientRecord : temp) {
            actualIds = (Map<String, String>) recipientRecord.get("externalIds");
            Assert.assertEquals(actualIds, expectedIds);
        }
    }

    public void validateGetCorrespondenceExternalids(boolean isOnlyMedicaid) {
        Map<String, String> expectedIds = new HashMap<>();
        if (isOnlyMedicaid) {
            expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        } else {
            if (null != generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())) {
                expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())) {
                expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
            }
            if (null != generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())) {
                expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())) {
                expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
            }
        }
        Map<String, String> actualIds;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        Assert.assertNotNull(cid, "cid is null");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients.recipientInfo");
        for (Map<String, Object> recipientRecord : temp) {
            actualIds = (Map<String, String>) recipientRecord.get("externalIds");
            Assert.assertEquals(actualIds, expectedIds);
        }
    }

    public void validateGetCorrespondenceExternalidsOutline(boolean isOnlyMedicaid) {
        // this method specifically for CP-42588-01 which is a scenario outline
        Map<String, String> expectedIds = new HashMap<>();
        if (isOnlyMedicaid) {
            expectedIds.put("externalCaseIdMEDICAID", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            expectedIds.put("externalConsumerIdMEDICAID", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
        } else {
            if (null != generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
        }
        Map<String, String> actualIds;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        Assert.assertNotNull(cid, "cid is null");
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients.recipientInfo");
        for (Map<String, Object> recipientRecord : temp) {
            actualIds = (Map<String, String>) recipientRecord.get("externalIds");
            Assert.assertEquals(actualIds, expectedIds);
        }
    }

    public void validateGetCorrespondenceExternalids(String nid, int recipientIndex) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        String cid = "";
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("newlyCreatedNotification")) {
            nid = String.valueOf(generalSave.get().get("newNid" + Hooks.nameAndTags.get()));
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        } else {
            cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        if (nid.equalsIgnoreCase("previouslyCreatedBulk")) {
            nid = response.getString("recipients[0].notifications[0].notificationId");
        }

        List<Map<String, Object>> temp = (List<Map<String, Object>>) response.get("recipients.recipientInfo");
        actualIds = (Map<String, String>) temp.get(recipientIndex).get("externalIds");
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGlobalSearchCorrespondenceExternalids(String nid) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        int recipientIndex = 0;
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request);
        List<Map<String, Object>> content = response.getList("data.content");
        boolean found = false;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                found = true;
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                Map<String, Object> temp2 = (Map<String, Object>) temp.get(recipientIndex).get("recipientInfo");
                actualIds = (Map<String, String>) temp2.get("externalIds");

            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGetNotificationsCorrespondenceExternalids(String nid) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        int recipientIndex = 0;
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnotifications(cid);
        List<Map<String, Object>> content = response.get();
        boolean found = false;
        for (Map<String, Object> nids : content) {
            if (nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                Map<String, Object> temp = (Map<String, Object>) nids.get("recipient");
                actualIds = (Map<String, String>) temp.get("externalIds");
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGlobalSearchCorrespondenceExternalids(String nid, int recipientIndex) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request);
        List<Map<String, Object>> content = response.getList("data.content");
        boolean found = false;
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        for (Map<String, Object> cids : content) {
            if (String.valueOf(cids.get("id")).equalsIgnoreCase(cid)) {
                found = true;
                List<Map<String, Object>> temp = (List<Map<String, Object>>) cids.get("recipients");
                Map<String, Object> temp2 = (Map<String, Object>) temp.get(recipientIndex).get("recipientInfo");
                actualIds = (Map<String, String>) temp2.get("externalIds");

            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void validateGetNotificationsCorrespondenceExternalids(String nid, int recipientIndex) {
        Map<String, String> expectedIds = new HashMap<>();
        expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get())));
        expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get())));
        Map<String, String> actualIds = new HashMap<>();
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            nid = String.valueOf(generalSave.get().get("nid"));
        }
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getnotifications(cid);
        List<Map<String, Object>> content = response.get();
        boolean found = false;
        for (Map<String, Object> nids : content) {
            if (!nid.equalsIgnoreCase(String.valueOf(nids.get("notificationId")))) {
                Map<String, Object> temp = (Map<String, Object>) nids.get("recipient");
                actualIds = (Map<String, String>) temp.get("externalIds");
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(actualIds, expectedIds);
    }

    public void verifyLetterDataHasExternalIds(int index) {
        int recipientIndex = 0;
        Map<String, String> expectedIds = new HashMap<>();
        if (index == 0) {
            expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
            expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        } else if (index == 1) {
            expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIPSecond" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIPSecond" + Hooks.nameAndTags.get())));
            expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaidSecond" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaidSecond" + Hooks.nameAndTags.get())));
            recipientIndex = 1;
        } else if (index == 3) {
            expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get())));
            expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
            recipientIndex = 1;
        }  else if (index == 5) {
            expectedIds.put("externalCaseIdMEDICAID", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get())));
            expectedIds.put("externalConsumerIdMEDICAID", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get())));
        }else if (index == 4) {// this if bracket specifically for CP-42588-01 which is a scenario outline
            if (null != generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalCaseIdCHIP", String.valueOf(generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalConsumerIdCHIP", String.valueOf(generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalCaseIdMedicaid", String.valueOf(generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
            if (null != generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())) {
                expectedIds.put("externalConsumerIdMedicaid", String.valueOf(generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get())));
            }
        } else {
            Assert.fail("unexpected index value");
        }
        String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        String nid = response.getString("recipients[" + recipientIndex + "].notifications[0].notificationId");
        JsonPath letterData = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
        Map<String, String> actualIds = letterData.getMap("recipient.externalIds");
        Assert.assertNotNull(actualIds, "letter data map is null");
        Assert.assertEquals(actualIds, expectedIds);
    }

    public JsonPath getCaseCorrespondence(String caseOrConsumerId, List<String> channels) {
        JsonObject request = new JsonObject();
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        Assert.assertNotNull(caseConsumerId);
        if ("caseId".equalsIgnoreCase(caseOrConsumerId)) {
            String caseTemp = caseConsumerId.get("caseId");
            Assert.assertNotNull(caseTemp, "case id missing");
            request.addProperty("caseId", caseTemp);
        } else if ("consumerId".equalsIgnoreCase(caseOrConsumerId)) {
            String consumerTemp = caseConsumerId.get("consumerId");
            Assert.assertNotNull(consumerTemp, "consumerId id missing");
            request.addProperty("caseId", consumerTemp);
        } else {
            Assert.fail("unexpected caseOrConsumerId value");
        }
        request.addProperty("includeNonDefaultRecipients", false);
        JsonArray channelsList = new JsonArray();
        for (String channel : channels) {
            channelsList.add(channel);
        }
        request.add("channels", channelsList);
        return API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().caseCorrespondence(request);
    }

    /**
     * use this method for scenario outlines
     */
    public void storeValuesFromCaseCorrespondence(JsonPath caseCorrespondenceResponse, List<String> data) {
        // this method specifically for CP-42588-01 which is a scenario outline
        cucumberOutlineCounter.set(cucumberOutlineCounter.get() + 1);
        Map<String, String> externalCaseIds = caseCorrespondenceResponse.getMap("externalIds[0]");
        Map<String, String> externalConsumerIds = caseCorrespondenceResponse.getMap("consumers[0].externalIds[0]");
        Assert.assertNotNull(externalCaseIds, "external case ids not found");
        Assert.assertNotNull(externalConsumerIds, "external consumer ids not found");
        for (String caseCorrespondenceValue : data) {
            switch (caseCorrespondenceValue.trim()) {
                case "externalCaseIdCHIP":
                    String externalCaseIdCHIP = externalCaseIds.get("id");
                    generalSave.get().put("externalCaseIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get(), externalCaseIdCHIP);
                    break;
                case "externalConsumerIdCHIP":
                    String externalConsumerIdCHIP = externalConsumerIds.get("id");
                    generalSave.get().put("externalConsumerIdCHIP" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get(), externalConsumerIdCHIP);
                    break;
                case "externalCaseIdMedicaid":
                    String externalCaseIdMedicaid = externalCaseIds.get("id");
                    generalSave.get().put("externalCaseIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get(), externalCaseIdMedicaid);
                    break;
                case "externalConsumerIdMedicaid":
                    String externalConsumerIdMedicaid = externalConsumerIds.get("id");
                    generalSave.get().put("externalConsumerIdMedicaid" + Hooks.nameAndTags.get() + cucumberOutlineCounter.get(), externalConsumerIdMedicaid);
                    break;
                default:
                    Assert.fail("no matching cases - " + caseCorrespondenceValue);
            }
        }
    }

    public void verifyMailFlagForLetterData(String nid, String expectedMailFlag) {
        if (nid.equalsIgnoreCase("previouslyCreated")) {
            String cid = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
            JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
            for (int i = 0; i < response.getList("recipients[0].notifications").size(); i++) {
                nid = response.getString("recipients[0].notifications[" + i + "].notificationId");
                generalSave.get().put("NOTIFICATIONID", nid);
                JsonPath letterData = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid);
                if (expectedMailFlag.equalsIgnoreCase("NO_MAIL_FLAG")) {
                    Assert.assertTrue(!letterData.prettyPrint().contains("\"mail\":"), "LetterData Contains mail flag for non-Mail channel");
                } else {
                    String actualMailFlag = String.valueOf(letterData.getBoolean("notification.mail"));
                    Assert.assertTrue(expectedMailFlag.equalsIgnoreCase(actualMailFlag), "MailFlag didn't match.Expected " + expectedMailFlag + ".but found " + actualMailFlag);
                }
            }
        }
    }

    public void verifyMailFlagForLetterDataSaveEvent(String expectedMailFlag) {
        matchedEventIDs.set(apiAuto.getEventId("LETTER_DATA_SAVE_EVENT", String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"))));
        Assert.assertTrue(apiAuto.getEventByEventId(matchedEventIDs.get().get(0)) != null, "No matching Event Id found");

        for (int i = 0; i < matchedEventIDs.get().size(); i++) {
            if (matchedEventIDs.get().get(i).contains("notificationId\\\\\\\":\\\\\\\" " + generalSave.get().get("NOTIFICATIONID").toString() + "\\\\\\")) {
                JsonPath eventByEventId = apiAuto.getEventByEventId(matchedEventIDs.get().get(i));
                String eventPayload = eventByEventId.getString("eventsList.content[0].payload");
                if (expectedMailFlag.equalsIgnoreCase("NO_MAIL_FLAG")) {
                    Assert.assertTrue(!eventPayload.contains("\\\"mail\\\\"), "LetterDataEvent Contains mail flag for non-Mail channel");
                } else {
                    eventPayload = eventPayload.replace("\"", "").replace("\\", "");
                    System.out.println(eventPayload);
                    Assert.assertTrue(eventPayload.contains("mail:" + (expectedMailFlag)));
                }
            }
        }
    }

    public void storeReturnedNotificationDestinationValues(String channel, JsonPath obDetails) {
        switch (channel.toLowerCase()) {
            case "mail":
                generalSave.get().put("ReturnedNotificationConsumerIdaddressLine1" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.streetAddress"));
                generalSave.get().put("ReturnedNotificationConsumerIdaddressLine2" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.streetAddionalLine1"));
                generalSave.get().put("ReturnedNotificationConsumerIdcity" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.city"));
                generalSave.get().put("ReturnedNotificationConsumerIdState" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.state"));
                generalSave.get().put("ReturnedNotificationConsumerIdzip" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.zipcode"));
                break;
            case "email":
                generalSave.get().put("ReturnedNotificationConsumerIdemailAddress" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.emailAddress"));
                break;
            case "text":
                generalSave.get().put("ReturnedNotificationConsumerIdtextNumber" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.textNumber"));
                break;
            case "fax":
                generalSave.get().put("ReturnedNotificationConsumerIdfaxNumber" + Hooks.nameAndTags.get(), obDetails.getString("recipients[0].recipientInfo.faxNumber"));
                break;
            default:
                Assert.fail("channel does not match switch cases - " + channel);
        }
    }

    public String getRandomStatusReason(String nidStatus) {
        String reason = "invalid";
        switch (nidStatus) {
            case "Returned":
                List<String> returnedReasons = Arrays.asList("Undeliverable", "Refused", "Mailbox Full", "Destination agent unresponsive", "Destination agent rejected message", "Destination invalid", "Change of Address");
                Random rand = new Random();
                reason = returnedReasons.get(rand.nextInt(returnedReasons.size()));
                break;
            default:
                Assert.fail("invalid nid status - " + nidStatus);
        }
        return reason;
    }
}