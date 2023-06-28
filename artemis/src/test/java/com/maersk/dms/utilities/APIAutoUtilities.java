package com.maersk.dms.utilities;

import com.github.javafaker.Faker;
import com.google.gson.*;
import com.maersk.crm.api_step_definitions.APIConsumerRestController;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.beans.outboundCorrespondence.OutboundCorrespondenceDefinition;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.testng.Assert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.mail.*;
import java.io.File;
import java.text.DateFormat;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
//import static com.maersk.crm.api_step_definitions.APIContactRecordController.apicontactRecordId;
//import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.api_step_definitions.APIContactRecordController.apicontactRecordId;
import static com.maersk.crm.utilities.World.*;
import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.assertEquals;

public class APIAutoUtilities extends CRMUtilities implements ApiBase {
    private static final ThreadLocal<String> traceId = ThreadLocal.withInitial(String::new);
    private String spanId;
    private ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private static ApiTestDataUtil uploadInb = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private String projectId = String.valueOf(parseProjectNumber(ConfigurationReader.getProperty("projectName")));
    private String mmsCodeEndPointBase = ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition") + "/correspondencelist/";
    private String mmsCodeEndPoint = projectId + "?size=100&page=0&sort=mmsId";
    public JsonObject requestParams;
    public String correspondenceOrder = ConfigurationReader.getProperty("apiDMSCorrespondence");
    public String correspondences = "/correspondences";
    private String projectIdNJ = ConfigurationReader.getProperty("njProjectName").split(" ")[0];
    public Object response;
    public int statusCode;
    public ResponseBody responseBody;
    public String responseString;
    public JsonPath jsonPathEvaluator;
    private String inbDocumentStatusDate;
    public Response correspondenceResponse;
    public Object inboundCorrespondenceDefinitionResponse;
    public Message[] messages;
    public Folder emailFolder;
    public Store mailStore;
    public static final ThreadLocal<Integer> cucumberOutlineCounter = ThreadLocal.withInitial(()->0);


    public int parseProjectNumber(String projectName) {
        char[] name = projectName.toCharArray();
        String result = "";
        for (char c : name) {
            if (Character.isDigit(c)) {
                result += c;
            } else {
                if (Character.isWhitespace(c)) {
                    break;
                }
            }
        }
        return Integer.parseInt(result);
    }

    /**
     * Grabs Active correspondence definitions with userMayRequest as true
     *
     * @return
     */
    public String getCorrespondenceType(int... typeIndex) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(mmsCodeEndPointBase);
        apiClassUtil.setEndPoint(mmsCodeEndPoint);
        apiClassUtil.getAPI();
        List<String> types = new ArrayList<>();
        List<Map<String, Object>> definitions = apiClassUtil.jsonPathEvaluator.getList("pageListCorrespondence.content");
        boolean umr = false;
        for (int index = 0; index < definitions.size(); index++) {
            umr = (boolean) definitions.get(index).get("userMayRequest");
            if (umr && definitions.get(index).get("correspondenceStatus").toString().equalsIgnoreCase("Active")) {
                types.add(definitions.get(index).get("mmsId").toString());
            }
        }
        if (typeIndex.length == 0) {
            return types.get(0);//if no index, send first one
        }
        return String.valueOf(typeIndex[0]);
    }

    public String getnewInboundDocId(String inboundType) {
        String docId = "";
        String baseUri = ConfigurationReader.getProperty("apiBffService");
        String endPoint = "/ecms/correspondence/inbound/document";
        System.out.println("Upload endpoint - " + baseUri + endPoint);
        Response response = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id())
                .header("x-b3-spanid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", inboundType)
                .multiPart("channel", "Text")
                .multiPart("language", "English")
                .multiPart("fromName", "Automation" + RandomStringUtils.random(5, true, true))
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put(baseUri + endPoint);
        Assert.assertEquals(response.statusCode(), 200);
        docId = response.jsonPath().get("documentId");
        System.out.println("Successfully Uploaded new Document|" + " Inbound Type - " + inboundType + " | Inbound Doc Id - " + docId);
        return docId;
    }

    public String getNewRescannedInboundDocId(Map<String, String> request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/documentencoded");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/GetRescannedId.json");
        JsonObject requestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        JsonObject processType = new JsonObject();
        processType.addProperty("name", "ProcessType");
        processType.addProperty("value", request.get("ProcessType"));
        JsonObject rescanDocumentId = new JsonObject();
        rescanDocumentId.addProperty("name", "RescanOfDocumentId");
        rescanDocumentId.addProperty("value", World.generalSave.get().get("InboundDocumentId").toString());
        requestParams.getAsJsonArray("metaData").getAsJsonArray().add(processType);
        requestParams.getAsJsonArray("metaData").getAsJsonArray().add(rescanDocumentId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams);
        World.generalSave.get().put("GETNEWRESCANINBOUNDDOCID", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("documentId"));
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("documentId");
    }

    public String createRescannedInboundDocument(Map<String, String> request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/documentencoded");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/GetRescannedId.json");
        JsonObject requestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        JsonObject processType = new JsonObject();
        JsonObject rescanDocumentId = new JsonObject();
        JsonObject channel = new JsonObject();
        for (String reqs : request.keySet()) {
            switch (reqs.toUpperCase()) {
                case "DOCUMENTTYPE":
                    requestParams.getAsJsonObject().addProperty("documentType", request.get("documentType"));
                    break;
                case "PROCESSTYPE":
                    processType.addProperty("name", "ProcessType");
                    processType.addProperty("value", request.get("ProcessType"));
                    requestParams.getAsJsonArray("metaData").getAsJsonArray().add(processType);
                    break;
                case "RESCANOFDOCUMENTID":
                    rescanDocumentId.addProperty("name", "RescanOfDocumentId");
                    rescanDocumentId.addProperty("value", World.generalSave.get().get("InboundDocumentId").toString());
                    requestParams.getAsJsonArray("metaData").getAsJsonArray().add(rescanDocumentId);
                    break;
                case "CHANNEL":
                    channel.addProperty("name", "Channel");
                    channel.addProperty("value", request.get("Channel"));
                    requestParams.getAsJsonArray("metaData").getAsJsonArray().add(channel);
                    break;
                default:
                    Assert.fail("case did not match - " + reqs);
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams);
        World.generalSave.get().put("GETNEWRESCANINBOUNDDOCID", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("documentId"));
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("documentId");
    }


    public String sendDocumentToOnBase(Map<String, String> request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/connectionpoint/sendevent");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/SendDocumentToOnBase.json");
        JsonObject requestParams = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        requestParams.addProperty("type", request.get("type"));
        requestParams.get("document").getAsJsonObject().get("properties").getAsJsonObject().addProperty("documentHandle", World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
        JsonObject processType = new JsonObject();
        processType.addProperty("keywordTypeName", "ProcessType");
        processType.addProperty("value", request.get("ProcessType"));
        JsonObject rescanDocumentId = new JsonObject();
        rescanDocumentId.addProperty("keywordTypeName", "RescanOfDocumentId");
        rescanDocumentId.addProperty("value", World.generalSave.get().get("InboundDocumentId").toString());
        requestParams.getAsJsonObject("document").getAsJsonArray("keywordFields").getAsJsonArray().add(processType);
        requestParams.getAsJsonObject("document").getAsJsonArray("keywordFields").getAsJsonArray().add(rescanDocumentId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("status");
    }

    public String createCorrespondenceMultipleChannels(List<String> channels) {
        Map<String, String> caseConsumerIds = (Map<String, String>) generalSave.get().get("caseConsumerId");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundCorrespondence.json").jsonElement.getAsJsonObject();
        generalSave.get().put("caseConsumerIds", caseConsumerIds);
        request.get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(caseConsumerIds.get("consumerId"));
        request.get("anchor").getAsJsonObject().addProperty("projectId", getProjectId());
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        request.get("anchor").getAsJsonObject().addProperty("caseId", caseConsumerIds.get("caseId"));
        for (String channel : channels) {
            switch (channel.toUpperCase()) {
                case "EMAIL":
                    JsonObject email = new JsonObject();
                    email.addProperty("channelType", "Email");
                    email.addProperty("language", "English");
                    request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(email);
                    break;
                case "MAIL":
                    JsonObject mail = new JsonObject();
                    mail.addProperty("channelType", "Mail");
                    mail.addProperty("language", "English");
                    mail.addProperty("createdBy", 2492);
                    request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(mail);
                    break;
                case "TEXT":
                    JsonObject text = new JsonObject();
                    text.addProperty("channelType", "Text");
                    text.addProperty("language", "English");
                    text.addProperty("createdBy", 2492);
                    request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(text);
                    break;
                case "FAX":
                    JsonObject fax = new JsonObject();
                    fax.addProperty("channelType", "Fax");
                    fax.addProperty("language", "English");
                    fax.addProperty("createdBy", 2492);
                    request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").getAsJsonArray().add(fax);
                    break;
            }
        }
        Response response = new APIAutoUtilities().postAPI(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint, request);
        System.out.println("Create ob response -------------------- \n" + response.prettyPrint());
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get(), response.jsonPath().get("data.id").toString());
        return response.jsonPath().get("data.id").toString();
    }

    public Response createBulkCorrespondence(Map<String, String> data) {
        Map<String, String> caseconsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
        if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
            String tempDcCaseId = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getCaseId();
            Map<String, String> tempCaseConsumerId = new HashMap<>();
            tempCaseConsumerId.put("caseId", tempDcCaseId);
            caseconsumerId = tempCaseConsumerId;
            generalSave.get().put("caseConsumerId", tempCaseConsumerId);
        }
        JsonArray exlinks = new JsonArray();
        generalSave.get().put("caseConsumerIds", caseconsumerId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondencebulkinsert");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/bulkOutboundCorrespondence.json").jsonElement.getAsJsonObject();
        JsonObject externalIds = new JsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "requesterId":
                    JsonObject requester = new JsonObject();
                    requester.addProperty("requesterType", "");
                    requester.addProperty("requesterId", "ConnectionPoint");
                    request.add("requester", requester);
                    break;
                case "caseId":
                    request.get("anchor").getAsJsonObject().addProperty("caseId", caseconsumerId.get("caseId"));
                    break;
                case "regardingConsumerId":
                    request.get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(caseconsumerId.get("consumerId"));
                    break;
                case "nameSuffix":
                case "middleName":
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty(key, data.get(key));
                    break;
                case "digitallyRead":
                    String digitallyRead = data.get(key);
                    generalSave.get().put("digitallyRead" + Hooks.nameAndTags.get(), digitallyRead);
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("digitallyRead", digitallyRead);
                    break;
                case "notificationLanguage":
                    if (data.get(key).equalsIgnoreCase("empty"))
                        request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("language", "");
                    else
                        request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("language", data.get(key));
                    break;
                case "notificationStatus":
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().get("notificationStatus").getAsJsonObject().addProperty("status", data.get(key));
                    break;
                case "notificationUpdatedBy":
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("updatedBy", data.get(key));
                    break;
                case "notificationCreatedBy":
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("createdBy", data.get(key));
                    break;
                case "notificationCreatedDateTime":
                    request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(0).getAsJsonObject().addProperty("createdDatetime", data.get(key));
                    break;
                case "correspondenceDefinitionMMSCode":
                    if (data.get(key).equalsIgnoreCase("empty"))
                        request.addProperty(key, " ");
                    else if (data.get(key).equalsIgnoreCase("null"))
                        request.addProperty(key, (Boolean) null);
                    else
                        request.addProperty(key, String.valueOf(data.get(key)));
                    break;
                case "externalCaseIdCHIP":
                    String externalCaseIdCHIP = data.get(key);
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        externalCaseIdCHIP = RandomStringUtils.random(9, true, true);
                    }
                    generalSave.get().put("externalCaseIdCHIP" + Hooks.nameAndTags.get(), externalCaseIdCHIP);
                    externalIds.addProperty(key, externalCaseIdCHIP);
                    break;
                case "externalConsumerIdCHIP":
                    String externalConsumerIdCHIP = data.get(key);
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        externalConsumerIdCHIP = RandomStringUtils.random(9, true, true);
                    }
                    generalSave.get().put("externalConsumerIdCHIP" + Hooks.nameAndTags.get(), externalConsumerIdCHIP);
                    externalIds.addProperty(key, externalConsumerIdCHIP);
                    break;
                case "externalCaseIdMedicaid":
                    String externalCaseIdMedicaid = data.get(key);
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        externalCaseIdMedicaid = RandomStringUtils.random(9, true, true);
                    }
                    generalSave.get().put("externalCaseIdMedicaid" + Hooks.nameAndTags.get(), externalCaseIdMedicaid);
                    externalIds.addProperty(key, externalCaseIdMedicaid);
                    break;
                case "externalConsumerIdMedicaid":
                    String externalConsumerIdMedicaid = data.get(key);
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        externalConsumerIdMedicaid = RandomStringUtils.random(9, true, true);
                    }
                    generalSave.get().put("externalConsumerIdMedicaid" + Hooks.nameAndTags.get(), externalConsumerIdMedicaid);
                    externalIds.addProperty(key, externalConsumerIdMedicaid);
                    break;
                case "externallink":
                    JsonObject externallinks = new JsonObject();
                    String extlinks[] = data.get(key).split(",");

                    if (extlinks[0].equalsIgnoreCase("empty"))
                        externallinks.addProperty("externalRefType", "");
                    else if (extlinks[0].equalsIgnoreCase("null")) {
                    } else externallinks.addProperty("externalRefType", extlinks[0]);
                    if (extlinks[1].equalsIgnoreCase("empty"))
                        externallinks.addProperty("externalRefId", "");
                    else if (extlinks[1].equalsIgnoreCase("null")) {
                    } else if (extlinks[1].equalsIgnoreCase("random")) {
                        World.generalSave.get().put("externalRefId", ApiTestDataUtil.getApiTestDataUtilThreadLocal().getRandomNumber(5).randomNumber);
                        externallinks.addProperty("externalRefId", World.generalSave.get().get("externalRefId").toString());
                    } else if (extlinks[1].equalsIgnoreCase("CRMTaskId")) {
                        World.generalSave.get().put("externalRefId", CRM_TaskManagementStepDef.taskId.get());
                        externallinks.addProperty("externalRefId", CRM_TaskManagementStepDef.taskId.get());
                    } else if (extlinks[1].equalsIgnoreCase("caseId")) {
                        World.generalSave.get().put("externalRefId", caseconsumerId.get("caseId"));
                        externallinks.addProperty("externalRefId", caseconsumerId.get("caseId"));
                    } else if (extlinks[1].equalsIgnoreCase("consumerId")) {
                        World.generalSave.get().put("externalRefId", caseconsumerId.get("consumerId"));
                        externallinks.addProperty("externalRefId", caseconsumerId.get("consumerId"));
                    } else if (extlinks[1].equalsIgnoreCase("InboundCorrespondenceDocumentId")) {
                        World.generalSave.get().put("externalRefId", generalSave.get().get("InboundDocumentId"));
                        externallinks.addProperty("externalRefId", World.generalSave.get().get("InboundDocumentId").toString());
                    } else if (extlinks[1].equalsIgnoreCase("applicationId")) {
                        World.generalSave.get().put("externalRefId", generalSave.get().get("applicationId"));
                        externallinks.addProperty("externalRefId", applicationIdAPI.get());
                    } else if (extlinks[1].equalsIgnoreCase("missingInformationId")) {
                        World.generalSave.get().put("externalRefId", String.valueOf(World.generalSave.get().get("MissingInformationItemId")));
                        externallinks.addProperty("externalRefId", String.valueOf(World.generalSave.get().get("MissingInformationItemId")));
                    } else if (extlinks[1].equalsIgnoreCase("contactRecordId")) {
                        World.generalSave.get().put("externalRefId", generalSave.get().get("apicontactRecordId"));
                        externallinks.addProperty("externalRefId", apicontactRecordId.get());
                    } else
                        externallinks.addProperty("externalRefId", extlinks[1]);
                    exlinks.add(externallinks);
                    request.add("externalLinks", exlinks);
                    break;
                default:
                    if (data.get(key).equalsIgnoreCase("empty")) {
                        request.addProperty(key, "");
                    } else request.addProperty(key, String.valueOf(data.get(key)));
            }
        }
        if (data.containsKey("externalCaseIdCHIP") || data.containsKey("externalConsumerIdCHIP") || data.containsKey("externalCaseIdMedicaid") || data.containsKey("externalConsumerIdMedicaid")) {
            request.get("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().add("externalIds", externalIds);
        }
        Response response = new APIAutoUtilities().postAPI(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint, request);
        System.out.println("Create ob response -------------------- \n" + response.prettyPrint());
        World.generalSave.get().put("BULKOUTBOUNDCORRESPONDENCERESPONSE", response.jsonPath());
        return response;
    }

    public Response createObNotification(Map<String, String> data) {
        String cid = null;
        if (data.get("correspondenceId").equalsIgnoreCase("previouslyCreated"))
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        else
            cid = data.get("correspondenceId");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/createObNotification.json").jsonElement.getAsJsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "nameSuffix":
                case "middleName":
                    request.get("recipient").getAsJsonObject().addProperty(key, data.get(key));
                    break;
                case "notificationId":
                    String nid;
                    if (data.get("notificationId").equalsIgnoreCase("previouslyCreated")) {
                        JsonPath getCID = getCorrespondence(cid);
                        List<String> notificationIds = getCID.getList("recipients[0].notifications.notificationId");
                        nid = notificationIds.get(0);
                    } else
                        nid = data.get("notificationId");
                    request.addProperty(key, nid);
                    break;
                case "correspondenceId":
                    request.addProperty(key, cid);
                    break;
                case "recipient":
                    if ("previouslyCreated".equalsIgnoreCase(data.get(key))) {
                        String recipientId = getCorrespondence(cid).getString("recipients[0].recipientInfo.correspondenceRecipientId");
                        request.get("recipient").getAsJsonObject().addProperty("correspondenceRecipientId", recipientId);
                    }
                    break;
                default:
                    Assert.fail("no matching keys for - \"" + key + "\"");
            }
        }
        Response response = new APIAutoUtilities().postAPI(ConfigurationReader.getProperty("apiECMSLetterData") + "/correspondences/" + cid + "/notifications", request);
        System.out.println("Create ob notification response -------------------- \n" + response.prettyPrint());
        World.generalSave.get().put("CREATEOBNOTIFICATIONRESPONSE", response.jsonPath());
        return response;
    }

    public JsonObject buildCorrespondenceRequest(Map<String, Object> anchor) {
        JsonObject request = new JsonObject();
        JsonObject anchorJson = new JsonObject();
        for (String anchorKey : anchor.keySet()) {
            switch (anchorKey) {
                case "regardingConsumerId":
                    JsonArray consumers = new JsonArray();
                    if (anchor.get("regardingConsumerId") instanceof String) {
                        consumers.add(String.valueOf(anchor.get("regardingConsumerId")));
                        anchorJson.add("regardingConsumerId", consumers);
                    } else {
                        List<String> regardingConsumers = new ArrayList<>();
                        try {
                            regardingConsumers = (List<String>) anchor.get("regardingConsumerId");
                        } catch (Exception e) {
                            Assert.fail("pass 'regardingConsumerId' in anchor map as list of strings or single string");
                        }
                        for (String consumer : regardingConsumers) {
                            consumers.add(consumer);
                        }
                    }
                    anchorJson.add("regardingConsumerId", consumers);
                    break;
                case "requesterId":
                    JsonObject requester = new JsonObject();
                    requester.addProperty("requesterType", "");
                    requester.addProperty("requesterId", "ConnectionPoint");
                    request.add("requester", requester);
                    break;
                case "caseId":
                    anchorJson.addProperty(anchorKey, String.valueOf(anchor.get(anchorKey)));
                    break;
                case "language":
                case "correspondenceDefinitionMMSCode":
                    request.addProperty(anchorKey, String.valueOf(anchor.get(anchorKey)));
                    break;
                default:
                    Assert.fail("no matching keys for - \"" + anchorKey + "\"");
            }
        }
        if (!anchor.containsKey("requesterId")) {
            JsonObject requester = new JsonObject();
            requester.addProperty("requesterId", "2492");
            requester.addProperty("requesterType", "ConnectionPoint");
            request.add("requester", requester);
        }
        request.add("anchor", anchorJson);
        return request;
    }

    public JsonObject buildObRecipientRequest(List<Map<String, Object>> notificationList, Map<String, Object> recipientKeys) {
        JsonObject request = new JsonObject();
        JsonObject notification = new JsonObject();
        JsonObject recipientInfo = new JsonObject();
        JsonArray notifications = new JsonArray();
        JsonObject externalIds = new JsonObject();
        for (Map<String, Object> notificationKeys : notificationList) {
            for (String notificationKey : notificationKeys.keySet()) {
                if (notificationKeys.get(notificationKey) instanceof Integer) {
                    notification.addProperty(notificationKey, Integer.valueOf(String.valueOf(notificationKeys.get(notificationKey))));
                } else {
                    notification.addProperty(notificationKey, String.valueOf(notificationKeys.get(notificationKey)));
                }
            }
            notifications.add(notification);
            notification = new JsonObject();
        }
        for (String recipientKey : recipientKeys.keySet()) {
            if (recipientKeys.get(recipientKey) instanceof Integer) {
                recipientInfo.addProperty(recipientKey, Integer.valueOf(String.valueOf(recipientKeys.get(recipientKey))));
            } else if ("externalCaseIdCHIP".equalsIgnoreCase(recipientKey) || "externalConsumerIdCHIP".equalsIgnoreCase(recipientKey) || "externalCaseIdMedicaid".equalsIgnoreCase(recipientKey) || "externalConsumerIdMedicaid".equalsIgnoreCase(recipientKey)) {
                externalIds.addProperty(recipientKey, String.valueOf(recipientKeys.get(recipientKey)));
            } else {
                recipientInfo.addProperty(recipientKey, String.valueOf(recipientKeys.get(recipientKey)));
            }
        }
        if (recipientKeys.containsKey("externalCaseIdCHIP") || recipientKeys.containsKey("externalConsumerIdCHIP") || recipientKeys.containsKey("externalCaseIdMedicaid") || recipientKeys.containsKey("externalConsumerIdMedicaid")) {
            recipientInfo.add("externalIds", externalIds);
        }
        request.add("recipientInfo", recipientInfo);
        request.add("notifications", notifications);
        return request;
    }


    public String getInbDocumentStatus(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/document/" + cid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        inbDocumentStatusDate = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("inboundCorrespondence.statusDate").substring(0, 10);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("inboundCorrespondence.status");
    }

    public String getInbDocumentStatusDate(String cid) {
        if (inbDocumentStatusDate.length() > 1) {
            return inbDocumentStatusDate;
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/document/" + cid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("inboundCorrespondence.statusDate");
    }

    public JsonPath linkInbDocToCaseId(String cid, String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/link");
        if ("fromRequest".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/link.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalId", Integer.valueOf(cid));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalId", Integer.valueOf(caseId));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("externalRefType", "case");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("internalRefType", "INBOUND_CORRESPONDENCE");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("externalLinkPayload").addProperty("effectiveStartDate", LocalDateTime.now(ZoneOffset.UTC).toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getOutboundCorrespondenceLinks(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCorresLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(cid + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getInboundCorrespondenceLinks(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/inbound_correspondence/" + cid + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getCaseLinks(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink") + "/links/case");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + caseId + "?size=20&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getConsumerLinks(String consumerId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiConsumerLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + consumerId + "?size=20&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonObject convertStringArrayToJsonArray(String list) {
        list = list.replace("[", "");
        list = list.replace("]", "");
        List<String> listConverted = new ArrayList<String>(Arrays.asList(list.split(",")));
        JsonObject result = new JsonObject();
        for (String o : listConverted) {
            result.getAsJsonArray().add(o);
        }
        return result;
    }

    /*
    converts yyyy-mm-ddThh:mm:ss.nnnnnn+|-hh:mm
        to
    M/dd/yyyy h:mm:ss a
    NOTE: empty string in, returns -- --
     */
    public String convertDateTimeToDateTimeWith12HrClock(String dateTime) {
        if (dateTime.length() < 1) {
            return "-- --";
        }
        OffsetDateTime odt = OffsetDateTime.parse(dateTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        dateTime = odt.format(f);
        return dateTime;
    }

    public String convertDateTimeToDateTimeWith12HrClock(String dateTime, ZoneOffset timezone) {
        if (dateTime.length() < 1) {
            return "-- --";
        }
        OffsetDateTime odt = OffsetDateTime.parse(dateTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a").withZone(timezone);
        dateTime = odt.format(f);
        return dateTime;
    }

    /*
    converts date yyyy-MM-dd
        to
    MM/dd/yyyy h:mm:ss a
    NOTE: empty string in, returns -- --
     */
    public String convertDateyyyyMMddToMMddyyyy(String date) {
        if (date.length() < 1) {
            return "-- --";
        }
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat out = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date convert = in.parse(date);
            date = out.format(convert);
        } catch (ParseException e) {
            Assert.fail("failed to convert - " + date);
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date;
    }

    public String createdByInbDoc(String user) {
        //finish call to tm for username
//        boolean hasLetters = BrowserUtils.isAlphanumeric(user);
//        if (hasLetters) {
//            return user;
//        }
//        Map<String, String> userId = new HashedMap();
//        userId.put("firstName","Service");
//        userId.put("lastName","AccountOne");
//        JsonPath tmUsers =  getUserAccountNameFromUserId(userId);
        if ("3274".equalsIgnoreCase(user) || "2492".equalsIgnoreCase(user)) {
            return "Service AccountOne";
        }
        return user;
    }

    public JsonPath getUserAccountNameFromUserId(Map<String, String> request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTMURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/tm/project/users?size=5&page=0&sort=firstName");
        JsonObject jsonObject = new JsonObject();
        JsonObject user = new JsonObject();
        for (String key : request.keySet()) {
            switch (key) {
                case "projectId":
                    user.addProperty(key, request.get(key));
                    jsonObject.add("user", user);
                    break;
                default:
                    jsonObject.addProperty(key, request.get(key));
                    break;
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(jsonObject);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String createInboundDocumentDigital(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/documentencoded");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        String message = "no message";
        if (200 != API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode) {
            message = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("message");
        }
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "create inb fail | " + message);
        String id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("documentId");
        Assert.assertTrue(id.length() > 0, "document id is not greater than zero");
        return id;
    }

    public JsonPath sendInboundDigitalRequest(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/documentencoded");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public void setObjectFileReceived(String notificationId) {
        String userId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiTMUserInfo();
        Assert.assertTrue(userId.length() > 0);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/objectreceived");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", notificationId);
        request.addProperty("objectParentFileId", "automation.pdf");
        request.addProperty("updatedBy", userId);
        request.addProperty("objectReceivedOn", OffsetDateTime.now(ZoneOffset.UTC).toString());
        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
//                .header("x-b3-spanid", traceId.get().substring(16,32))
//                .header("x-b3-traceid", traceId.get())
                .body(String.valueOf(request))
                .log().all()
                .when()
                .post(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        Assert.assertEquals(response.statusCode(), 200);
        System.out.println("object received file set for nid - " + notificationId);
    }

    public List<String> getInboundChannels() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundcorrespondencesettings/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        List<String> channels = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("channelNames");
        return channels;
    }

    public JsonPath getActiveOutbDefs() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/activecorrespondences/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public Map<String, String> getRandomConsumerId() {
        APIUtilitiesForUIScenarios apiUtilitiesForUIScenarios = APIUtilitiesForUIScenarios.getInstance();
        apiUtilitiesForUIScenarios.createAConsumerAndGetConsumerID();
        Map<String, String> result = new HashMap<>();
        result.put("consumerId", apiUtilitiesForUIScenarios.getConsumerId());
        return result;
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String type, Map<String, String> anchorElements) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(BrowserUtils.validNumberFilter(anchorElements.get(anchorElement)));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", BrowserUtils.validNumberFilter(anchorElements.get(anchorElement)));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        if ("default".equalsIgnoreCase(type)) {
            type = getDefaultOutboundType();
        }
        request.addProperty("correspondenceDefinitionMMSCode", type);
        anchor.add("regardingConsumerId", regardingConsumerId);
        request.add("requester", requester);
        request.add("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String type, Map<String, String> anchorElements, String language) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(anchorElements.get(anchorElement));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", anchorElements.get(anchorElement));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", type);
        if ("null".equalsIgnoreCase(language)) {
            request.add("language", JsonNull.INSTANCE);
        } else {
            request.addProperty("language", language);
        }
        anchor.add("regardingConsumerId", regardingConsumerId);
        request.add("requester", requester);
        request.add("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String type, Map<String, String> anchorElements, JsonObject bodyData) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        if (generalSave.get().get("bodyData") != null) {
            request.add("bodyData", bodyData);
        }
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(anchorElements.get(anchorElement));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", String.valueOf(anchorElements.get(anchorElement)));
            } else {
                anchor.addProperty(anchorElement, anchorElements.get(anchorElement));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", type);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        request.add("anchor", anchor);
        request.add("requester", requester);
        generalSave.get().put("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String type, Map<String, String> anchorElements, JsonObject bodyData, String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        if (generalSave.get().get("bodyData") != null) {
            request.add("bodyData", bodyData);
        }
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(anchorElements.get(anchorElement));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", String.valueOf(anchorElements.get(anchorElement)));
            } else {
                anchor.addProperty(anchorElement, anchorElements.get(anchorElement));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", type);
        request.addProperty("taskId", taskId);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        request.add("anchor", anchor);
        request.add("requester", requester);
        generalSave.get().put("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String type, Map<String, String> anchorElements, JsonObject bodyData, String taskId, JsonArray extlinks) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        if (generalSave.get().get("bodyData") != null) {
            request.add("bodyData", bodyData);
        }
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(anchorElements.get(anchorElement));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", String.valueOf(anchorElements.get(anchorElement)));
            } else {
                anchor.addProperty(anchorElement, anchorElements.get(anchorElement));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", type);
        request.addProperty("taskId", taskId);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        request.add("anchor", anchor);
        request.add("requester", requester);
        request.add("externalLinks", extlinks);
        generalSave.get().put("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath createOutboundCorrespondenceNoRecip(String forceparameter, String type, Map<String, String> anchorElements, JsonObject bodyData) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        if (generalSave.get().get("bodyData") != null) {
            request.add("bodyData", bodyData);
        }
        for (String anchorElement : anchorElements.keySet()) {
            if (anchorElement.contains("consumer") || anchorElement.contains("Consumer")) {
                regardingConsumerId.add(anchorElements.get(anchorElement));
            } else if (anchorElement.contains("case") || anchorElement.contains("Case")) {
                anchor.addProperty("caseId", String.valueOf(anchorElements.get(anchorElement)));
            } else {
                anchor.addProperty(anchorElement, anchorElements.get(anchorElement));
            }
        }
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", type);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        request.add("anchor", anchor);
        request.add("requester", requester);
        generalSave.get().put("anchor", anchor);
        if (forceparameter.equalsIgnoreCase("false") || forceparameter.equalsIgnoreCase("true")) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
            headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
            headers.put("Force-Create-Indicator", forceparameter);
            return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(request, headers).jsonPathEvaluator;
        } else {
            Map<String, String> headers = new HashMap<>();
            headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
            headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
            headers.put("Force-Create-Indicator", forceparameter);
            return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(request, headers).jsonPathEvaluator;
        }

    }

    public String getDefaultOutboundType() {
        String type = "";
        switch (getProjectId()) {
            case "6203":
                type = "BL01";
                break;
            case "92":
                type = "ALLCHANNEL";
                break;
            case "8861":
                type = "INEBQAREL";
                break;
            case "95":
                type = "INEBQADEV";
                break;

        }
        return type;
    }

    public String getDefaultOutboundTypeName() {
        String type = "";
        switch (getProjectId()) {
            case "6203":
                type = "SAMPLE CASE LETTER";
                break;
            case "92":
                type = "ALLCHANNEL";
                break;
            case "8861":
                type = "GENERIC QAREL";
                break;
            case "95":
                type = "GENERIC QADEV";
                break;

        }
        return type;
    }

    public String getDefaultOutboundType(String definition) {
        String type = getProjectId() + "_" + definition;
        switch (type) {
            case "6203_default":
                type = "BL01";
                break;
            case "92_default":
                type = "ALLCHANNEL";
                break;
            case "6203_bodyData":
                type = "body1";
                break;
            case "92_bodyData":
                type = "body2";
                break;
        }
        return type;
    }

    public String getalternateOutboundType() {
        String type = "";
        switch (getProjectId()) {
            case "6203":
                type = "TEMP";
                break;
            case "92":
                type = "GENERIC1";
                break;

        }
        return type;
    }

    public String getValidTypeBasedOnAnchor(Set<String> anchor) {
        String type = "";
        if (anchor.stream().anyMatch("caseid"::equalsIgnoreCase) && anchor.stream().anyMatch("consumerids"::equalsIgnoreCase)) {
            type = "allChCCS";
        } else if (anchor.stream().anyMatch("caseid"::equalsIgnoreCase) && anchor.stream().anyMatch("consumerid"::equalsIgnoreCase)) {
            type = "allChCC";
        } else if (anchor.stream().anyMatch("caseid"::equalsIgnoreCase)) {
            type = "allChCase";
        } else if (anchor.stream().anyMatch("consumerid"::equalsIgnoreCase)) {
            type = "allChCo";
        } else if (anchor.stream().anyMatch("applicationid"::equalsIgnoreCase)) {
            type = "AppId2";
        }
        return type;
    }

    public void updateCorrespondenceStatus(int cid, String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/" + cid + "/statuses");
        JsonObject request = new JsonObject();
        request.addProperty("changedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiUserId);
        request.addProperty("status", status);
        request.addProperty("statusReason", "set by automation");
        PostWithProjectId(String.valueOf(request));
    }

    public JsonPath getEventbyTraceId(String traceId) {
        //api.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-event-api");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/event/correlation/" + traceId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        waitFor(5);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public Integer getTaskType(Integer taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/taskmanagement/tasks/" + taskId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return Integer.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("tasks[0].taskVO.taskTypeId"));
    }

    public JsonPath barcodeLookup(String barcode) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/notificationsectionkeyvalues");
        JsonObject request = new JsonObject();
        request.addProperty("barcode", barcode);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath barcodeLookup(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/notificationsectionkeyvalues");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath onbaseBarcodeLookup(String barcode) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/onbase/barcode/" + barcode);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonObject generateSendNowOutb(String language) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/CoverVASendNow.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("anchor").getAsJsonObject().addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", getSendNowTypes());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").get(0).getAsJsonObject().addProperty("language", language);
        return (JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement;
    }

    public String getSendNowTypes() {
        String result = "";
        switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()) {
            case "91":
                result = "sendNow";
                break;
            case "8166":
                result = "sendNow";
                break;
            case "95":
                result = "sendNow";
                break;
            case "8861":
                result = "sendNow";
                break;
            case "92":
                result = "sendNow";
                break;
            case "6203":
                result = "sendNow";
                break;
        }
        return result;
    }

    public JsonPath VA_INEBcreateOutboundCorrespondence(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator = PostWithProjectIdVAINEB(String.valueOf(request));
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getNotificationStatusHistory(String sendNowNID) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + sendNowNID + "/statuses");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    /**
     * THIS IS ONBASE API, this does not use regular token, nor is it dependent on CP env
     *
     * @param searchCriteria document keywords and relationship operators, include exactly in map
     * @return returns unfiltered response
     */
    public JsonPath onbaseSearch(Map<String, String> searchCriteria) {
        JsonObject requestBody = generateOnbaseRequest(searchCriteria);
        RequestSpecification request = RestAssured.given();
        Response response = request
                .config(config)
                .header("Content-Type", "application/json")
                .header("Tenant_Hash", EncodingUtil.decode(ConfigurationReader.getProperty("onbaseTenantHash")))
                .header("Tenant_Token", EncodingUtil.decode(ConfigurationReader.getProperty("onbaseTenantToken")))
                .header("Tenant_User", EncodingUtil.decode(ConfigurationReader.getProperty("onbaseTenantUser")))
                .body(String.valueOf(requestBody))
                .log().all()
                .when()
                .post(ConfigurationReader.getProperty("onbaseSearchUT1"));
        System.out.println("\n---------------------------------\n" + response.prettyPrint());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator = response.jsonPath();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonObject generateOnbaseRequest(Map<String, String> searchCriteria) {
        String projectPrefix = "";
        switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()) {
            case "8166":
                projectPrefix = "VACV";
                break;
            case "91":
                projectPrefix = "VACV";
                break;
            case "95":
                projectPrefix = "INEB";
                break;
            case "8861":
                projectPrefix = "INEB";
                break;
            default:
                Assert.fail("project id not matched");
        }
        JsonObject DocumentTypes = new JsonObject();
        JsonObject QueryKeywords = new JsonObject();
        DocumentTypes.addProperty("Name", projectPrefix + " Outbound Correspondence");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/onbaseSearch.json").jsonElement.getAsJsonObject();
        request.getAsJsonArray("DocumentTypes").add(DocumentTypes);
        for (String Name : searchCriteria.keySet()) {
            QueryKeywords = new JsonObject();
            QueryKeywords.addProperty("Name", Name);
            QueryKeywords.addProperty("Value", searchCriteria.get(Name));
            QueryKeywords.addProperty("KWOperator", "Equal");
            QueryKeywords.addProperty("KWRelation", "And");
            request.getAsJsonArray("QueryKeywords").add(QueryKeywords);
        }
        return request;
    }

    public JsonPath getContactRecordLinks(String contactRecord) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/contact_record/" + contactRecord + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public void unlinkfromInboundDocument(String cid, String name, String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/unlink");
        JsonObject request = new JsonObject();
        request.addProperty("inboundCorrespondenceId", cid);
        request.addProperty("userId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
        switch (name.toUpperCase()) {
            case "CASE":
                request.addProperty("caseId", id);
                break;
            case "CONSUMER PROFILE":
                request.addProperty("consumerId", id);
                break;
            case "TASK":
                unlinkTaskfromInboundDocument(cid, id);
                return;
            default:
                Assert.fail("no matching case");
        }
        PostWithProjectId(String.valueOf(request));
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
    }

    public void unlinkTaskfromInboundDocument(String cid, String linkId) {
        JsonPath links = getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> actualResponse = links.getList("externalLinkDetails.content");
        boolean found = false;
        String now = new APIAutoUtilities().getDateTimeNow("Current_SysDate");
        String createdBy = "";
        String createdOn = "";
        String updatedBy = "";
        String updatedOn = "";
        String effectiveStartDate = "";
        String effectiveEndDate = "";
        Integer externalId = 0;
        Integer internalId = 0;
        for (Map<String, Object> link : actualResponse) {
            if (Integer.parseInt(String.valueOf(link.get("id"))) == Integer.parseInt(linkId)) {
                createdBy = String.valueOf(link.get("createdBy"));
                createdOn = String.valueOf(link.get("createdOn"));
                updatedBy = String.valueOf(link.get("updatedBy"));
                updatedOn = String.valueOf(link.get("createdOn"));
                effectiveEndDate = String.valueOf(link.get("effectiveStartDate"));
                effectiveStartDate = String.valueOf(link.get("effectiveStartDate"));
                internalId = Integer.valueOf(String.valueOf(link.get("id")));
                externalId = Integer.valueOf(cid);
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/servicerequest/link");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/unlinkTaskFromInbDocument.json");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        request.getAsJsonObject("externalLinkPayload").addProperty("createdBy", createdBy);
        request.getAsJsonObject("externalLinkPayload").addProperty("createdOn", createdOn);
        request.getAsJsonObject("externalLinkPayload").addProperty("updatedBy", updatedBy);
        request.getAsJsonObject("externalLinkPayload").addProperty("updatedOn", updatedOn);
        request.getAsJsonObject("externalLinkPayload").addProperty("effectiveEndDate", effectiveEndDate);
        request.getAsJsonObject("externalLinkPayload").addProperty("effectiveStartDate", effectiveStartDate);
        request.getAsJsonObject("externalLinkPayload").addProperty("internalId", internalId);
        request.getAsJsonObject("externalLinkPayload").addProperty("externalId", externalId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        System.out.println("\n----------------\n" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "api unlink fail");
    }

    public JsonPath getInboundCorrespondenceInactiveLinks(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/inbound_correspondence/" + cid + "?inactivelinks=true&size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public APIClassUtil generateOutboundPdf(String notificationId, String template) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/downloadFile/" + template + "?notificationId=" + notificationId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal();
    }

    public JsonPath getOutboundDefinition(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondence/" + cid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getOutboundDefinitionByMmsCode(String mmsCode) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondence/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId() + "/" + mmsCode);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getOBDefinitionListByProject() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondencelist/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath outboundGlobalSearch(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/ouboundcorrespondence?page=0&size=200");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath createRandomOBCorrespondenceMultipleChannels(String type, Map<String, String> consumerIds, String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OBCorrespondenceMutipleChannels.json").jsonElement.getAsJsonObject();
        JsonObject anchor = new JsonObject();

        JsonArray regardingConsumerId = new JsonArray();
        for (String consumer : consumerIds.keySet()) {
            regardingConsumerId.add(consumerIds.get(consumer));
        }
        request.addProperty("correspondenceDefinitionMMSCode", type);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("caseId", caseId);
        request.add("anchor", anchor);
        generalSave.get().put("anchor", anchor);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath getObSettings(String projectId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondencesettings/" + projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public void setObSettings() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondencesettings/" + projectId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/ObSettings.json");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
    }

    public JsonPath getLetterData(String nid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + nid + "/letterdata");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getnotifications(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/" + cid + "/notifications");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String getLanguageCode(String language) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/languages");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        String languageCode = "";
        List<Map<String, String>> codes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("languagesList");
        for (Map<String, String> code : codes) {
            if (language.equalsIgnoreCase(code.get("languageName"))) {
                languageCode = code.get("isoCode");
            }
        }
        return languageCode;
    }

    public JsonObject generateEmailObRequest() {
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundCorrespondence.json").jsonElement.getAsJsonObject();
        String emailAddress = RandomStringUtils.random(5, true, false) + "@" + RandomStringUtils.random(5, true, false) + ".com";
        generalSave.get().put("emailAddress", emailAddress);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("emailAddress", emailAddress);
//        request.add("anchor", JsonNull.INSTANCE);
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        JsonObject notification = new JsonObject();
        notification.addProperty("channelType", "Email");
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").add(notification);
        return request;
    }

    public JsonObject generateMailObRequest() {
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundCorrespondence.json").jsonElement.getAsJsonObject();
        Faker faker = new Faker();
        String streetAddress = faker.address().streetAddress();
        generalSave.get().put("streetAddress", streetAddress);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("streetAddress", streetAddress);
        String city = faker.address().city();
        generalSave.get().put("city", city);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("city", city);
        String state = faker.address().stateAbbr();
        generalSave.get().put("state", state);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("state", state);
        String zipcode = faker.address().zipCode().substring(0, 5);
        generalSave.get().put("zipcode", zipcode);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("zipcode", zipcode);
//        request.add("anchor", JsonNull.INSTANCE);
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        JsonObject notification = new JsonObject();
        notification.addProperty("channelType", "Mail");
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").add(notification);
        return request;
    }

    public JsonObject generateFaxObRequest() {
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundCorrespondence.json").jsonElement.getAsJsonObject();
        Faker faker = new Faker();
        String faxNumber = RandomStringUtils.random(10, false, true);
        generalSave.get().put("faxNumber", faxNumber);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("faxNumber", faxNumber);
//        request.add("anchor", JsonNull.INSTANCE);
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        JsonObject notification = new JsonObject();
        notification.addProperty("channelType", "Fax");
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").add(notification);
        return request;
    }

    public JsonObject generateTextObRequest() {
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundCorrespondence.json").jsonElement.getAsJsonObject();
        Faker faker = new Faker();
        String textNumber = RandomStringUtils.random(10, false, true);
        generalSave.get().put("textNumber", textNumber);
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonObject("recipientInfo").addProperty("textNumber", textNumber);
//        request.add("anchor", JsonNull.INSTANCE);
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        JsonObject notification = new JsonObject();
        notification.addProperty("channelType", "Text");
        request.getAsJsonArray("recipients").get(0).getAsJsonObject().getAsJsonArray("notifications").add(notification);
        return request;
    }

    public String getBarcodeFromMMSCode(String correspondenceDefinitionMMSCode) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/activecorrespondences/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        JsonPath defs = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
        List<Map<String, Object>> obDefs = defs.getList("listCorrespondence");
        String ibId = obDefs.stream()
                .filter(def -> def.get("mmsId").toString().equals(correspondenceDefinitionMMSCode))
                .findFirst()
                .get().get("inboundCorrespondenceDefinitionId").toString();
        List<Map<String, Object>> ibDefs = getInboundTypes().get();
        try {
            return ibDefs.stream()
                    .filter(def -> def.get("inboundCorrespondenceDefinitionId").toString().equalsIgnoreCase(ibId))
                    .findFirst()
                    .get().get("barcode").toString();
        } catch (Exception nullPointerException) {
            return "0000";
        }
    }

    public JsonPath getInboundTypes() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundCorrespondenceDefinition/correspondenceType/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonObject generateObRecipient(String consumerId, String language) {
        JsonPath response = getRecipientsInfoByConsumerNumber(consumerId);
        JsonObject result = new JsonObject();
        JsonObject notification = new JsonObject();
        JsonObject recipient = new JsonObject();
        notification.addProperty("channelType", "Mail");
        notification.addProperty("language", language);
        JsonArray notifications = new JsonArray();
        notifications.add(notification);
        result.add("notifications", notifications);
        recipient.addProperty("firstName", response.getString("consumers[0].firstName"));
        recipient.addProperty("lastName", response.getString("consumers[0].lastName"));
        recipient.addProperty("role", response.getString("consumers[0].consumerRole"));
        recipient.addProperty("consumerId", consumerId);
        recipient.addProperty("streetAddress", response.getString("consumers[0].channels.mail.addresses[0].addressLine1"));
        recipient.addProperty("city", response.getString("consumers[0].channels.mail.addresses[0].city"));
        recipient.addProperty("state", response.getString("consumers[0].channels.mail.addresses[0].state"));
        recipient.addProperty("zipcode", response.getString("consumers[0].channels.mail.addresses[0].zip"));
        result.add("recipientInfo", recipient);
        return result;
    }

    public JsonPath createOBCorrespondence(String request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithHeaders(request, headers);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath createOBCorrespondence(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        generalSave.get().put("OBREQUESTTRACEID" + Hooks.nameAndTags.get(), APIClassUtil.traceId.get());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath searchInboundType(String name) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("inboundCorrespondenceDefinition");
        JsonObject request = new JsonObject();
        request.addProperty("name", name);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath retrieveNotifications(String outboundcorrespondenceid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/provision/list");
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        JsonObject request = new JsonObject();
        JsonArray cids = new JsonArray();
        cids.add(Integer.valueOf(outboundcorrespondenceid));
        request.add("cids", cids);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(request, headers);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println("------------ status code - " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode + " ---------------------");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath retrieveNotifications(String[] outboundcorrespondenceid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/provision/list");
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        JsonObject request = new JsonObject();
        JsonArray cids = new JsonArray();
        Arrays.stream(outboundcorrespondenceid).forEach(cids::add);
        request.add("cids", cids);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(request, headers);
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getMissingItemLinks(String missingInformationItemId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/missing_info/" + missingInformationItemId + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getTaskLinks(String taskId) {
        return getTaskByTaskId(taskId);
    }

    public JsonPath getApplicationLinks(String applicationId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiLinksLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/links/application/" + applicationId + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public void addSecondCaseConsumerId() {
        Map<String, String> secondConsumer = (Map<String, String>) generalSave.get().get("secondConsumer");
        secondConsumer = searchConsumer("consumerSSN", secondConsumer.get("consumerSSN"), secondConsumer);
        Assert.assertNotNull(secondConsumer);
    }

    public JsonPath createOBCorrespondenceLinkedToTask(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(request, headers);
        Assert.assertEquals(201, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath exportEmailSendGrid(String nid, String requestId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/sendemail");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", Integer.valueOf(nid));
        request.addProperty("requesterId", requestId);
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutApiWithCustomeHeaders(request, headers);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath processEmailSendGridEvent(Map<String, String> requestData, String notificationid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/sendgridevent");
        JsonArray sendgrideventrequest = new JsonArray();
        JsonObject request = new JsonObject();
        for (String key : requestData.keySet()) {
            switch (key) {
                case "event":
                    request.addProperty("event", requestData.get("event"));
                    break;
                case "type":
                    request.addProperty("type", requestData.get("type"));
                    break;
                case "reason":
                    request.addProperty("reason", requestData.get("reason"));
                    break;
                case "notificationId":
                    request.addProperty("notificationId", Integer.valueOf(notificationid));
                    break;
                case "projectName":
                    request.addProperty("projectName", requestData.get("projectName"));
                    break;
                case "email":
                    request.addProperty("email", requestData.get("email"));
                    break;
                default:
                    Assert.fail("no matching cases");
            }
        }
        sendgrideventrequest.add(request);
        Map<String, String> headers = new HashMap<>();
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostApiWithCustomeHeaders(sendgrideventrequest, headers);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Mismatch in expected 200 status code");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath processTextTwilioEvent(Map<String, String> requestData, String notificationid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        Map<String, String> twilioeventrequest = new HashMap<>();
        if(requestData.containsKey("Event_Type")){
            if(requestData.get("Event_Type").equalsIgnoreCase("CONSUMER_SUBSCRIPTION_UPDATE")){
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("notifications/twilioincomingevent/" + requestData.get("projectName"));
                twilioeventrequest.put("OptOutType", requestData.get("OptOutType"));
                twilioeventrequest.put("From", requestData.get("From"));
            }
        }
        else {
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("notifications/twilioevent/" + requestData.get("projectName") + "/" + notificationid);
            twilioeventrequest.put("MessageStatus", requestData.get("MessageStatus"));
            twilioeventrequest.put("ErrorCode", requestData.get("ErrorCode"));
            twilioeventrequest.put("ErrorMessage", requestData.get("ErrorMessage"));

            twilioeventrequest.put("SmsSid", "123456");
            twilioeventrequest.put("SmsStatus", "123456");
            twilioeventrequest.put("To", "123456");
            twilioeventrequest.put("MessageSid", "123456");
            twilioeventrequest.put("AccountSid", "123456");
            twilioeventrequest.put("From", "123456");
            twilioeventrequest.put("ApiVersion", "123456");
        }

        Map<String, String> headers = new HashMap<>();
        // headers.put("Project-Name", api.getProjectName());
        // headers.put("Project-Id", api.getProjectId());
//        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");//changes implemented for CP-42054
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeadersFormParam(twilioeventrequest, headers);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "Mismatch in expected 200 status code");
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }


    public Message[] fetchEmails(String emailAccount) {
        try {
            Map<String, String> settings = filterEmailDomain(emailAccount);
            Session emailSession = createEmailAccountConnection(settings);
            Store store = connectToStore(emailSession, settings);

            try {
                emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_WRITE);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            messages = emailFolder.getMessages();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Store connectToStore(Session emailSession, Map<String, String> settings) {
        try {
            mailStore = emailSession.getStore();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            mailStore.connect(settings.get("host"), Integer.parseInt(settings.get("port")), settings.get("username"), settings.get("password"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mailStore;
    }

    public Session createEmailAccountConnection(Map<String, String> settings) {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", settings.get("protocol"));
        properties.setProperty("mail.imaps.starttls.enable", settings.get("startTTLS"));
        properties.setProperty("mail.imap.ssl.enable", settings.get("SSL"));
        properties.setProperty("mail.imaps.host", settings.get("host"));
        properties.setProperty("mail.imaps.port", settings.get("port"));
        properties.put("mail.imap.ssl.protocols", settings.get("sslProtocol"));

        Session emailSession = Session.getDefaultInstance(properties);
        emailSession.setDebug(true);
        return emailSession;
    }

    public Map<String, String> filterEmailDomain(String emailAccount) {
        Map<String, String> settings = new HashMap<>();
        switch (EventsUtilities.getGenericMatch(emailAccount, "(?<=@)[^.]+(?=\\.)").toLowerCase()) {
            case "outlook":
                generalSave.get().put("emailAddress", emailAccount);
                settings.put("protocol", ConfigurationReader.getProperty("emailProtocolOutlook"));
                settings.put("startTTLS", ConfigurationReader.getProperty("emailStarttlsOutlook"));
                settings.put("SSL", ConfigurationReader.getProperty("emailSSLOutlook"));
                settings.put("host", ConfigurationReader.getProperty("emailHostOutlook"));
                settings.put("port", ConfigurationReader.getProperty("emailPortOutlook"));
                settings.put("sslProtocol", ConfigurationReader.getProperty("emailsslProtocolOutlook"));
                if (emailAccount.contains("2")) {
                    settings.put("username", ConfigurationReader.getProperty("emailUsernameOutlook2"));
                    settings.put("password", ConfigurationReader.getProperty("emailPasswordOutlook2"));
                } else {
                    settings.put("username", ConfigurationReader.getProperty("emailUsernameOutlook"));
                    settings.put("password", ConfigurationReader.getProperty("emailPasswordOutlook"));
                }
                break;
            default:
                Assert.fail("no match cases");
        }
        return settings;
    }

    public Message findMessageByRecipientName(String consumerFirstName, String consumerLastName, Message[] messages) {
        Message message = null;
        boolean found = false;
        boolean foundmaersk = false;
        if (messages.length < 1) {
            BrowserUtils.waitFor(30);
            messages = fetchEmails(String.valueOf(generalSave.get().get("emailAddress")));
        }
        for (int index = 0; index < messages.length; index++) {
            try {
                if (!EventsUtilities.getGenericMatch(messages[index].getFrom()[0].toString(), "(?<=@)[^.]+(?=\\.)").toLowerCase().equalsIgnoreCase("maersk")) {
                    messages[index].setFlag(Flags.Flag.DELETED, true);
                    System.out.println("non-maersk email found");
                    continue;
                } else {
                    foundmaersk = true;
                }
                System.out.println("***** message - \n" + messages[index].getAllRecipients()[0] + " *********");
                if (messages[index].getAllRecipients()[0].toString().replace("<" + String.valueOf(generalSave.get().get("emailAddress")) + ">", "").trim().equalsIgnoreCase(APIUtilitiesForUIScenarios.consumerFirstName.get() + " " + APIUtilitiesForUIScenarios.consumerLastName.get())) {
                    message = messages[index];
                    found = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(foundmaersk, "maersk sent email not found, or not sent from maersk.com email");
        Assert.assertTrue(found, "email not found, or not sent from maersk.com email");
        return message;
    }

    public boolean ismaerskMessageFound(Message[] messages) {
        if (messages.length < 1) {
            BrowserUtils.waitFor(30);
            messages = fetchEmails(String.valueOf(generalSave.get().get("emailAddress")));
        }
        if (messages.length < 1) {
            return false;
        }
        for (int index = 0; index < messages.length; index++) {
            try {
                if (!EventsUtilities.getGenericMatch(messages[index].getFrom()[0].toString(), "(?<=@)[^.]+(?=\\.)").toLowerCase().equalsIgnoreCase("maersk")) {
                    messages[index].setFlag(Flags.Flag.DELETED, true);
                    System.out.println("non-maersk email found");
                    continue;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void deleteAllMessages() {
        Arrays.stream(messages).forEach(
                mess -> {
                    try {
                        mess.setFlag(Flags.Flag.DELETED, true);

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void closeEmailResources() {
        try {
            emailFolder.close(true);
            mailStore.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertFalse(emailFolder.isOpen());
            Assert.assertFalse(mailStore.isConnected());
        }
    }

    public JsonPath getObDefinition(String emailOnly2Cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondence/" + emailOnly2Cid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath createOBDefintionAPI(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondence/");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonObject generateOBDefinitionRequest(String correspondenceregression) {
        ApiTestDataUtil apt = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apt.getJsonFromFile("dms/createObDef.json");
        JsonObject request = (JsonObject) apt.jsonElement;
        OutboundCorrespondenceDefinition outboundCorrespondenceDefinition = World.getWorld().outboundCorrespondenceDefinition.get();
        request.getAsJsonObject().addProperty("mmsId", outboundCorrespondenceDefinition.ID);
        request.getAsJsonObject().getAsJsonObject("bodyDataSchema").addProperty("$id", "https://maersk.com/CorrespondenceRegression-obcorrbody-" + outboundCorrespondenceDefinition.ID + "-1.schema.json");
        request.getAsJsonObject().getAsJsonObject("bodyDataSchema").addProperty("title", "CorrespondenceRegression OBCorr " + outboundCorrespondenceDefinition.ID + " 1");
        request.getAsJsonObject().getAsJsonObject("bodyDataSchema").addProperty("description", "Outbound correspondence definition body data structure for CorrespondenceRegression " + outboundCorrespondenceDefinition.ID + " 1");
        request.getAsJsonObject().addProperty("correspondenceName", outboundCorrespondenceDefinition.Name);
        request.getAsJsonObject().addProperty("description", outboundCorrespondenceDefinition.description);
        request.getAsJsonObject().addProperty("userMayRequest", Boolean.FALSE);
        request.getAsJsonObject().addProperty("projectId", correspondenceregression);
        request.getAsJsonObject().addProperty("startDate", getDateTimeWithTFormat());
        request.getAsJsonObject().addProperty("approvalRequired", Boolean.FALSE);
        request.getAsJsonObject().addProperty("userMayEnterBodyData", Boolean.FALSE);
        return request;
    }

    public String getChannelProjectId(String channel, String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("channels/" + id);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        List<Map<String, Object>> projectChannels = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("projectChannels");
        String projectId = "";
        boolean found = false;
        for (Map<String, Object> projectChannel : projectChannels) {
            Map<String, String> channelsMap = (Map<String, String>) projectChannel.get("channels");
            String channelsString = new Gson().toJson(channelsMap);
            JsonObject channels = new Gson().fromJson(channelsString, JsonObject.class);
            if (BrowserUtils.filterForLettersOnly(String.valueOf(channels.get("channelName"))).trim().equalsIgnoreCase(channel)) {
                projectId = String.valueOf(projectChannel.get("projectChannelId"));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
        return projectId;
    }

    public JsonObject generateObChannelDefRequest(Map<String, String> data, String projectChannelId, String obDefCid, String channelId, String projectId) {
        JsonObject request = new JsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "channel":
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        request.addProperty("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
                    } else {
                        request.addProperty("channelType", data.get(key));
                    }
                    break;
                default:
                    if ("random".equalsIgnoreCase(data.get(key))) {
                        request.addProperty(key, World.getWorld().outboundCorrespondenceDefinitionChannel.get().returnValues().get(key));
                    } else {
                        request.addProperty(key, data.get(key));
                    }
            }
        }
        request.addProperty("correspondenceId", obDefCid);
        request.addProperty("channelId", channelId);
        request.addProperty("projectChannelId", projectChannelId);
        request.addProperty("projectId", projectId);
        request.addProperty("startDate", getDateTimeWithTFormat());
        return request;
    }

    public JsonPath getObDefinitionNotificationPurposes() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notificationpurposes");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath saveChannelDefinition(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/channeldefinition");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath saveChannelDefinition(JsonObject request, String channelDefinitionId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/channeldefinition");
        request.addProperty("channelDefinitionId", channelDefinitionId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath patchNotificationObjectReceived(String nid, String ecmsDocumentId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notification/" + nid);
        traceId.set(ApiTestDataUtil.generate_random_trace_id());
        Assert.assertEquals(traceId.get().length(), 32);
        spanId = traceId.get().substring(16, 32);
        generalSave.get().put("PostTraceId", traceId.get());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("x-b3-spanid", traceId.get().substring(16, 32));
        headers.put("x-b3-traceid", traceId.get());
        JsonObject request = new JsonObject();
        request.addProperty("ecmsDocumentId", ecmsDocumentId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PatchApiWithCustomHeaders(request, headers);
        World.generalSave.get().put("updateNidTraceId" + Hooks.nameAndTags.get(), traceId.get());
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath patchInvalidNotificationObjectReceived(String nid, String ecmsDocumentId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notification/" + nid);
        traceId.set(ApiTestDataUtil.generate_random_trace_id());
        Assert.assertEquals(traceId.get().length(), 32);
        spanId = traceId.get().substring(16, 32);
        generalSave.get().put("PostTraceId", traceId.get());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("x-b3-spanid", traceId.get().substring(16, 32));
        headers.put("x-b3-traceid", traceId.get());
        JsonObject request = new JsonObject();
        request.addProperty("ecmsDocumentId", ecmsDocumentId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PatchApiWithCustomHeaders(request, headers);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath sendTextTwilio(String phoneNumber, String nid) {
        String expectedPhoneNumber = String.valueOf(generalSave.get().get("receiveSMS"));
        Assert.assertEquals(phoneNumber, expectedPhoneNumber, "fail bc phone number is not same number as expected");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/sendsms");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        request.addProperty("requesterId", "2495");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String getIbDetailSpecificValue(String searchCriteria) {
        JsonPath response = (JsonPath) generalSave.get().get("InboundCidForScenario" + Hooks.nameAndTags.get());
        return response.getString("inboundCorrespondence." + searchCriteria);
    }

    public String getIbDetailSpecificMetaDataValue(String searchCriteria) {
        JsonPath response = (JsonPath) generalSave.get().get("InboundCidForScenario" + Hooks.nameAndTags.get());
        List<Map<String, String>> metaData = response.getList("inboundCorrespondence.metaData");
        return metaData.stream()
                .filter(map -> map.containsKey(searchCriteria))
                .findFirst()
                .get()
                .get("value");
    }

    public JsonPath patNotificationIsViewed(String nid, boolean viewed) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notification/" + nid);
        traceId.set(ApiTestDataUtil.generate_random_trace_id());
        Assert.assertEquals(traceId.get().length(), 32);
        spanId = traceId.get().substring(16, 32);
        generalSave.get().put("PatchTraceId" + Hooks.nameAndTags.get(), traceId.get());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("x-b3-spanid", traceId.get().substring(16, 32));
        headers.put("x-b3-traceid", traceId.get());
        JsonObject request = new JsonObject();
        request.addProperty("digitallyRead", viewed);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PatchApiWithCustomHeaders(request, headers);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath patchInvalidNotificationViewed(String nid, String viewied) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notification/" + nid);
        traceId.set(ApiTestDataUtil.generate_random_trace_id());
        Assert.assertEquals(traceId.get().length(), 32);
        spanId = traceId.get().substring(16, 32);
        generalSave.get().put("PostTraceId", traceId.get());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        headers.put("x-b3-spanid", traceId.get().substring(16, 32));
        headers.put("x-b3-traceid", traceId.get());
        JsonObject request = new JsonObject();
        request.addProperty("digitallyRead", viewied);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PatchApiWithCustomHeaders(request, headers);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath searchCorrespondence(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/searchcorrespondence?caseId=" + caseId + "&page=0&size=5");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath postNotification(String cid, JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences/" + cid + "/notifications");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request.toString());
        generalSave.get().put("postNotificationTraceId" + Hooks.nameAndTags.get(), APIClassUtil.traceId.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.prettyPrint());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public void retryGenerateRandomCaseId(Map<String, String> caseConsumerId, int retries) {
        for (int index = 0; index < retries; index++) {
            System.out.println("Retrying to get case or consumer - " + index);
            caseConsumerId = generateRandomCaseId((JsonObject) generalSave.get().get("previousCaseLoaderRequest"));
            generalSave.get().put("caseConsumerId", caseConsumerId);
            if (null == caseConsumerId) {
                continue;
            }
            if (caseConsumerId.containsKey("caseId") && caseConsumerId.containsKey("consumerId")) {
                return;
            }
            BrowserUtils.waitFor(5);
        }
        Assert.fail("no consumers or cases in caseConsumerId Map found");
    }

    public Map<String, String> generateRandomDestinationInformation(String newNotificationChannel) {
        Map<String, String> destination = new HashMap<>();
        Faker faker = new Faker();
        switch (newNotificationChannel) {
            case "Mail":
                destination.put("streetAddress", faker.address().streetAddress());
                destination.put("city", faker.address().cityName());
                destination.put("state", faker.address().stateAbbr());
                destination.put("streetAddionalLine1", faker.address().buildingNumber());
                destination.put("zipcode", faker.address().zipCode());
                break;
            case "Text":
                destination.put("textNumber", BrowserUtils.validNumberFilter(faker.phoneNumber().cellPhone()));
                break;
            case "Fax":
                destination.put("faxNumber", BrowserUtils.validNumberFilter(faker.phoneNumber().cellPhone()));
                break;
            case "Email":
                destination.put("emailAddress", faker.internet().emailAddress());
                break;
            default:
                Assert.fail("no matching cases - " + newNotificationChannel);
        }
        return destination;
    }

    public String getToken() {
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getOauthToken("CRM", "BLCRM");
    }

    public void postOutboundCorrespondenceDefinitionToServer(String project) {
        Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
        World.save.get().put("Name", World.getWorld().outboundCorrespondenceDefinition.get().Name);//save ID for DPBI events automation
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceDefinition.json");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("mmsId", params.get("ID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", params.get("stateID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceName", params.get("Name"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("description", params.get("description"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", "");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("userMayRequest", false);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("reasonForEnding", "false");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdBy", "Service AccountOne");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdDatetime",getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate",getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("bodyDataSource", params.get("bodyDataSoource"));
        System.out.println(requestParams);
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api");
        apiClassUtil.setEndPoint("/correspondence");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        World.random.get().put("response", apiClassUtil.response);
    }

    public void postOutboundCorrespondenceDefinitionToServer(String project, Map<String, Object> fields) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Project-Name", "la");
        Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
        JsonObject jsonObject = apiTestDataUtil.getJsonFromFile("/dms/OutboundCorrespondenceDefinition.json").jsonElement.getAsJsonObject();
        if ("AUTODMS".equalsIgnoreCase(project)) {
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", 812);
        } else if ("BLCRM".equalsIgnoreCase(project)) {
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", 44);
        } else {
            int pNum = 0;
            pNum = Integer.parseInt(project.substring(0, 3));
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", 44);
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("mmsId", params.get("ID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", params.get("stateID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceName", params.get("Name"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", LocalDateTime.now().toString());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("endDate", LocalDateTime.now().toString());
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            if (entry.getValue() instanceof String) {
                apiTestDataUtil.jsonElement.getAsJsonObject().addProperty(entry.getKey(), entry.getValue().toString());
            } else {
                apiTestDataUtil.jsonElement.getAsJsonObject().addProperty(entry.getKey(), Integer.valueOf(entry.getValue().toString()));
            }
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdDatetime", String.valueOf(LocalDateTime.now().atZone(ZoneId.of("US/Eastern")).toInstant().toEpochMilli()));
        String uri = "https://mars-qa.apps.non-prod.pcf-maersk.com/mars-ecmcor-correspondencedefinitionservice-qa.apps.non-prod.pcf-maersk.com/correspondence";
        synchronized (World.getWorld().response) {
            World.getWorld().response.set(postRequest(headers, params, jsonObject, uri));
        }
    }

    public void postOutboundCorrespondenceDefinitionCustom(Map<String, Object> map) {
        try {
            Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
//        World.save.get().put("Name", World.getWorld().outboundCorrespondenceDefinition.get().Name);//save ID for DPBI events automation
            ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
            apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceDefinition.json");
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("mmsId", map.get("mmsId").toString());
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", map.get("correspondenceId").toString());
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceName", map.get("correspondenceName").toString());
            if (!map.get("requiredKeyValue").toString().isEmpty()) {
                JsonArray requiredKeyArray = new JsonArray();
                JsonObject reqKey = new JsonObject();
                reqKey.addProperty("requiredkeyid", 1);
                reqKey.addProperty("requiredKeyValue", map.get("requiredKeyValue").toString());
                requiredKeyArray.add(reqKey);
                apiTestDataUtil.jsonElement.getAsJsonObject().add("correspondenceRequiredKeys", requiredKeyArray);
            } else {
                apiTestDataUtil.jsonElement.getAsJsonObject().remove("correspondenceRequiredKeys");
            }
            System.out.println(requestParams);
            APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
            apiClassUtil.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api");
            apiClassUtil.setEndPoint("/correspondence");
            synchronized (World.getWorld().jsonPath){
                World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
            }
            World.random.get().put("response", apiClassUtil.response);
            System.out.println(World.getWorld().jsonPath.get().getString("status"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void postChannelDefinitionToServer(String project, int correspondenceId) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
        JsonObject jsonObject = apiTestDataUtil.getJsonFromFile("/dms/OutChannelDefinition.json").jsonElement.getAsJsonObject();
        if ("AUTODMS".equalsIgnoreCase(project)) {
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", 812);
        } else if ("BLCRM".equalsIgnoreCase(project)) {
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", 44);
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", correspondenceId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", LocalDateTime.now().toString());
        String uri = "https://mars-qa.apps.non-prod.pcf-maersk.com/mars-ecmcor-correspondencedefinitionservice-qa.apps.non-prod.pcf-maersk.com/channeldefinition";
        synchronized (World.getWorld().response) {
            World.getWorld().response.set(postRequest(headers, params, jsonObject, uri));
        }
    }

    public JsonPath getCorrespondence(String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences/" + id);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getOutbCIDFromNID(String nid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences/ouboundcorrespondence?page=0&size=20");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public Response postRequest(Map<String, String> headers, Map<String, String> params, String uri) {
        return given().and().headers(headers).params(params).when().post(uri);
    }

    public Response postRequest(Map<String, String> headers, Map<String, String> params, JsonObject jsonObject, String uri) {
        return given().and().headers(headers).body(jsonObject.toString()).when()
                .post(uri);
    }

    public Response postRequest(Map<String, String> headers, JsonObject jsonObject, String uri) {
        return given().and().headers(headers).body(jsonObject.toString()).when()
                .post(uri);
    }

    public Response postRequest(Map<String, String> headers, Map<String, String> params, Map<String, String> body, String uri) {
        return given().and().headers(headers).params(params).body(body).when().post(uri);
    }

    public Response getRequest(Map<String, String> headers, String uri) {
        return given().and().headers(headers).when().get(uri);
    }

    public List<List<Map<String, Object>>> getChannelList() {
        int current = parseProjectNumber(ConfigurationReader.getProperty("projectName"));
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api-qa.apps.non-prod.pcf-maersk.com");
        apiClassUtil.setEndPoint("correspondencelist/" + current + "?size=10&page=0&sort=mmsId");
        apiClassUtil.getAPI();
        return apiClassUtil.jsonPathEvaluator.getList("pageListCorrespondence.content.channelDefinitions");
    }

    /**
     * @param channels send channels you want channel definition Ids for, no dupes
     * @return map with channel definition ids
     */
    public Map<String, String> getActiveChannelIds(String... channels) {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> response = getChannelList().get(0);
        for (String chanType : channels) {
            for (Map<String, Object> map : response) {
                if (map.containsValue(chanType)) {
                    result.put(chanType, map.get("channelDefinitionId").toString());
                }
            }
        }
        return result;
    }

    /**
     * @param taskId pass in task id
     * @return - json path evaluator from api class util
     */
    public JsonPath getTaskByTaskId(String taskId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTaskManagementURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/taskmanagement/tasks/" + taskId);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
    }

    /**
     * @param docId pass in inbound doc id
     * @return json path response from
     */
    public JsonPath bffGetInboundCorrespondenceDetails(String docId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/document/" + docId);
        JsonPath result = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return result;
    }

    public JsonPath createCorrespondence(JsonObject jsonObject, String endPoint) {
        APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
        ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        String traceId = testDataUtil.generate_random_trace_id();
        World.save.get().put("PostTraceId", traceId);
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", api.getProjectName())
                .header("Project-Id", api.getProjectId())
                .header("x-b3-spanid", traceId.substring(16, 32))
                .header("x-b3-traceid", traceId)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(endPoint);
        correspondenceResponse = response;
        JsonPath jsonPathEvaluator = response.jsonPath();
        api.statusCode = response.statusCode();
        generalSave.get().put("statusCode", api.statusCode);
        return jsonPathEvaluator;
    }

    public APIClassUtil PostCorrespondence(JsonObject jsonObject, String endPoint) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", result.getProjectName())
                .header("Project-Id", result.getProjectId())
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public Response getAPI(String baseUri, String endPoint) {
        String url = baseUri + endPoint;
        ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        String traceId = testDataUtil.generate_random_trace_id();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
//                .header("x-b3-spanid", traceId.substring(16,32))
//                .header("x-b3-traceid", traceId)
                .log().all()
                .when()
                .get(url);
        return response;
    }

    public List<Map<String, String>> getDefinitionListByProjectID() {
        APIAutoUtilities api = new APIAutoUtilities();
        String baseUri = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api/inboundCorrespondenceDefinition/correspondenceType";
        ConfigurationReader.getProperty("projectName");
        String number = BrowserUtils.validNumberFilter(ConfigurationReader.getProperty("projectName"));
        String endpoint = "/" + number;
        Response response = api.getAPI(baseUri, endpoint);
        Assert.assertEquals(response.statusCode(), 200);
        List<Map<String, String>> keyList = response.jsonPath().getList("");
        return keyList;
    }

    public List<Map<String, Object>> getDefinitionListByProjectID(String ID) {
        APIAutoUtilities api = new APIAutoUtilities();
        String baseUri = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-definition-microservices-api/inboundCorrespondenceDefinition/correspondenceType";
        String number = ID;
        String endpoint = "/" + number;
        Response response = api.getAPI(baseUri, endpoint);
        Assert.assertEquals(response.statusCode(), 200);
        List<Map<String, Object>> keyList = response.jsonPath().get("content");
        return keyList;
    }

    public String getDateTimeNow(String date) {
        if (date.equalsIgnoreCase("Current_SysDate")) {
            ZonedDateTime nowUTC = ZonedDateTime.now(ZoneOffset.UTC);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(nowUTC.toInstant(), ZoneOffset.UTC);
            return localDateTime.toString();
        }
        return null;
    }

    public String getDateTimeNow() {
        ZonedDateTime nowUTC = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(nowUTC.toInstant(), ZoneOffset.UTC);
        return localDateTime.toString().substring(0, 19) + ".00.000000+00:00";
    }

    public boolean compareDateTimes(String date1, String date2) {
        String compare1 = BrowserUtils.validNumberFilter(date1.substring(0, 10));
        String compare2 = BrowserUtils.validNumberFilter(date2.substring(0, 10));
        return compare1.equalsIgnoreCase(compare2);
    }

    public APIClassUtil PostCorrespondenceNJ(JsonObject jsonObject, String endPoint, String projectName) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();

        Object response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", projectName)
                .header("Project-Id", projectIdNJ)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public APIClassUtil indexInboundDocument(JsonObject jsonObject, String endPoint) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", result.getProjectName())
                .header("Project-Id", result.getProjectId())
                .header("userId", "James")
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public APIClassUtil indexInboundDocumentNJ(JsonObject jsonObject, String endPoint, String projectName) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", projectName)
                .header("Project-Id", projectIdNJ)
                .header("userId", "James")
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    /**
     * @param eventName - search by eventName
     * @param keyWord   send filter
     * @return returning a list of eventIds of that match event name and keywords
     */
    public List<String> getEventId(String eventName, String keyWord) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiMarsEvent"));
        apiClassUtil.setEndPoint("?size=200&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        apiClassUtil.PostAPIWithParameter(jsonObject);
        JsonPath jsonPath = apiClassUtil.jsonPathEvaluator;
        List<Map<String, Object>> before = jsonPath.getList("eventsList.content");
        List<String> result = new ArrayList<>();
        for (Map<String, Object> key : before) {
            if (key.toString().contains(keyWord)) {
                result.add(String.valueOf(key.get("eventId")));
            }
        }
        return result;
    }

    public JsonPath getEventByEventId(String eventId) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiMarsEvent"));
        apiClassUtil.setEndPoint("?size=200&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventId", eventId);
        return apiClassUtil.PostAPIWithParameter(jsonObject).jsonPathEvaluator;
    }

    public String getnewInboundDocId(String inboundType, String caseNum) {
        String docId = "";
        String baseUri = ConfigurationReader.getProperty("apiBffService");
        String endPoint = "/ecms/correspondence/inbound/document";
        System.out.println("Upload endpoint - " + baseUri + endPoint);
        Response response = given()
                .header("projectId", "6203")
                .header("Authorization", "Bearer " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id())
                .header("x-b3-spanid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("correspondenceType", inboundType)
                .multiPart("caseId", Integer.parseInt(caseNum))
                .multiPart("channel", "Text")
                .multiPart("language", "English")
                .multiPart("fromName", "Automation" + RandomStringUtils.random(5, true, true))
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put(baseUri + endPoint);
        Assert.assertEquals(response.statusCode(), 200);
        docId = response.jsonPath().get("documentId");
        System.out.println("Successfully Uploaded new Document|" + " Inbound Type - " + inboundType + " | Inbound Doc Id - " + docId);
        return docId;
    }

    public String uploadOutboundCorrespondence(String notification) {
        String docId = "";
        String baseUri = ConfigurationReader.getProperty("apiBffService");
        String endPoint = "/ecms/correspondence/outbound/notification?notificationId=" + notification;
        System.out.println("Upload endpoint - " + baseUri + endPoint);
        Response response = given()
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Authorization", "Bearer " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id())
                .header("x-b3-spanid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .put(baseUri + endPoint);
        Assert.assertEquals(response.statusCode(), 200);
        docId = response.jsonPath().get("documentId");
        return docId;
    }

    public String uploadOutboundCorrespondence(String notification, String channel) {
        String docId = "";
        String baseUri = ConfigurationReader.getProperty("apiBffService");
        String endPoint = "/ecms/correspondence/outbound/notification?notificationId=" + notification;
        System.out.println("Upload endpoint - " + baseUri + endPoint);
        Response response = given()
                .header("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("Authorization", "Bearer " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id())
                .header("x-b3-spanid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/testData/api/dms/" + channel + ".pdf"))
                .put(baseUri + endPoint);
        Assert.assertEquals(response.statusCode(), 200);
        docId = response.jsonPath().get("documentId");
        return docId;
    }

    public Response postAPI(String url, JsonObject requestbody) {
        ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        String traceId = testDataUtil.generate_random_trace_id();
        save.get().put("TRACE ID", traceId);
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .body(requestbody.toString())
//                .header("x-b3-spanid", traceId.substring(16,32))
//                .header("x-b3-traceid", traceId)
                .log().all()
                .when()
                .post(url);
        return response;
    }

    public APIClassUtil saveOutboundCorrespondence(String notificationId, String endPoint) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Project-Name", getProjectName())
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/testData/api/dms/MultiPageDocumenMail.pdf"))
                .queryParam("notificationId", notificationId)
                .put(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public APIClassUtil saveOutboundCorrespondenceNoInputFile(String notificationId, String endPoint) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("projectId", result.getProjectId())
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("multipart/form-data")
                .queryParam("notificationId", notificationId)
                .put(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public APIClassUtil getCorrespondenceDocumetDetails(String endPoint) {
        APIClassUtil result = APIClassUtil.getApiClassUtilThreadLocal();
        RequestSpecification request = RestAssured.given();
        System.out.println(endPoint);
        Object response = request
                .header("projectId", result.getProjectId())
                .header("Project-Name", result.getProjectName())
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", apiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", apiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .get(endPoint);
        result.request = ((RequestSpecification) request).log().toString();
        result.response = ((Response) response).toString();
        result.statusCode = ((Response) response).statusCode();
        result.responseBody = ((Response) response).getBody();
        result.responseString = result.responseBody.asString();
        result.jsonPathEvaluator = ((Response) response).jsonPath();
        return result;
    }

    public APIAutoUtilities postInboundDocument(String url, String requestbody) {
        traceId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        World.generalSave.get().put("postInboundDocumentTraceId", traceId.get());
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("userId", "automation")
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestbody)
                .log().all()
                .when()
                .post(url);
        this.response = (response).toString();
        this.statusCode = (response).statusCode();
        this.responseBody = (response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = (response).jsonPath();
        return this;
    }

    public APIAutoUtilities postOutboundCorrWithOneRecipient(String url, String requestbody) {
        ApiTestDataUtil testDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        String traceId = testDataUtil.generate_random_trace_id();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", "6203")
                .header("userId", "automation")
                .body(requestbody)
                .log().all()
                .when()
                .post(url);
        this.response = (response).toString();
        this.statusCode = (response).statusCode();
        this.responseBody = (response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = (response).jsonPath();
        return this;
    }

    public APIAutoUtilities getClassAPI(String url) {
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", "6203")
                .header("userId", "automation")
                .log().all()
                .when()
                .get(url);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public JsonPath updateInboundDocument(String cid, String request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/document");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }


    public JsonPath createdReScanedInboundDocument(String cid) {
        JsonObject request = new JsonObject();
        JsonObject rescanID = new JsonObject();
        request = (JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/dms/").jsonElement;
        rescanID.addProperty("name", "RescanOfDocumentId");
        rescanID.addProperty("value", cid);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().putApiRequest(rescanID.toString());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath createOutboundCorrespondenceDefinition(Map<String, String> request) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OutboundDefinition.json");
        for (String req : request.keySet()) {
            switch (req) {
                case "correspondenceRequiredKeys":
                    JsonObject reqKeys = new JsonObject();
                    reqKeys.addProperty(req, request.get(req));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("correspondenceRequiredKeys").add(reqKeys);
                    break;
                default:
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty(req, request.get(req));
            }
        }
        JsonObject payload = (JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondence");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(payload);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String getTodayDateString(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String presentDate = now.format(formatter);
        return presentDate;
    }

    public JsonPath inboundSearch(String request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/document/search/correspondence");
        return PostWithProjectId(request);
    }

    public Map<String, String> getRandomCaseId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseLoaderURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/caseloader");
        String firstName = RandomStringUtils.random(7, true, false);
        String lastName = RandomStringUtils.random(7, true, false);
        generalSave.get().put("firstName", firstName);
        generalSave.get().put("lastName", lastName);
        String ssn = String.valueOf(getRandomWholeNumber(100000000, 999999999));
        String dob = "19" + String.valueOf(getRandomWholeNumber(50, 99)) + "-" + String.valueOf(getRandomWholeNumber(10, 12) + "-" + String.valueOf(getRandomWholeNumber(10, 28)));
        JsonObject request = generateCaseLoaderRequestWithSpecifiedParameters("", firstName, lastName, ssn, dob, "", "", "Primary Individual");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, String> results = new HashedMap();
        results = searchConsumer("consumerSSN", ssn, results);
        Assert.assertNotNull(results);
        return results;
    }

    public Map<String, String> getRandomCaseId(String writtenLanguage) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseLoaderURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/caseloader");
        String firstName = RandomStringUtils.random(7, true, false);
        String lastName = RandomStringUtils.random(7, true, false);
        generalSave.get().put("firstName", firstName);
        generalSave.get().put("lastName", lastName);
        String ssn = String.valueOf(getRandomWholeNumber(100000000, 999999999));
        String dob = "19" + String.valueOf(getRandomWholeNumber(50, 99)) + "-" + String.valueOf(getRandomWholeNumber(10, 12) + "-" + String.valueOf(getRandomWholeNumber(10, 28)));
        JsonObject request = generateCaseLoaderRequestWithSpecifiedParameters("", firstName, lastName, ssn, dob, "", writtenLanguage, "Primary Individual");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, String> results = new HashedMap();
        results = searchConsumer("consumerSSN", ssn, results);
        Assert.assertNotNull(results);
        return results;
    }

    public Map<String, String> getRandomCaseId(Map<String, String> map) {
        Map<String, String> values = new HashMap<>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            values.put(m.getKey(), m.getValue());
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseLoaderURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/caseloader");
        String ssn = String.valueOf(getRandomWholeNumber(100000000, 999999999));
        String dob = "19" + String.valueOf(getRandomWholeNumber(50, 99)) + "-" + String.valueOf(getRandomWholeNumber(10, 12) + "-" + String.valueOf(getRandomWholeNumber(10, 28)));
        values.put("ssn", ssn);
        values.put("dob", dob);
        JsonObject request = generateCaseLoaderRequestWithSpecifiedParameters(values);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, String> results = new HashedMap();
        results = searchConsumer("consumerSSN", ssn, results);
        Assert.assertNotNull(results);
        return results;
    }

    public int getRandomWholeNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Map<String, String> searchConsumer(String criteria, String search, Map<String, String> data) {
        BrowserUtils.waitFor(20);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/case/consumers");
        APIConsumerRestController apiConsumerRestController = new APIConsumerRestController();
        apiConsumerRestController.i_can_search_consumer_by(criteria, search);
        apiConsumerRestController.i_run_the_consumer_search_api();
        if (((List<Object>) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result")).size() < 1) {
            return null;
        }
        data.put("caseId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseId") + "");
        data.put("consumerId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].consumers[0].consumerId") + "");
        return data;
    }

    public String getProjectId() {
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId();
    }

    public JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                       String consumerSSN, String consumerDateOfBirth, String GenderCode, String writtenLanguage, String wantsPaperless) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("writtenLanguage", writtenLanguage);
        if (null != wantsPaperless && wantsPaperless.equalsIgnoreCase("true")) {
            //do nothing, paperless pref is in apiCaseLoader.json
            System.out.println("paperless pref is - " + wantsPaperless);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("communicationPreferences", JsonNull.INSTANCE);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonPath PostWithProjectId(String requestBody) {
        traceId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        World.save.get().put("PostTraceId", traceId.get());
        RequestSpecification request = RestAssured.given();
        Response response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().response = response;
        generalSave.get().put("statusCode", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return response.jsonPath();
    }


    public JsonPath PostWithProjectIdVAINEB(String requestBody) {
        traceId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        RequestSpecification request = RestAssured.given();
        String projectName = getSendNowPName();
        Response response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("Project-Name", projectName)
                .header("Project-Id", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint);
        System.out.println("\n-------------------------------" + response.prettyPrint());
        return response.jsonPath();
    }

    public String getSendNowPName() {
        String result = "";
        switch (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId()) {
            case "91":
                result = "CoverVA";
                break;
            case "95":
                result = "IN-EB";
                break;
            case "8166":
                result = "CoverVA";
                break;
            case "8861":
                result = "IN-EB";
                break;
            case "164":
            case "9559":
                result = "DC-EB";
                break;
        }
        return result;
    }

    public String getProjectName() {
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName();
    }

    public void updateNotification(String nid, String status) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            Map<String, Object> notifications = (Map<String, Object>) getCorrespondence(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString()).getList("recipients[0].notifications").get(0);
            nid = notifications.get("notificationId").toString();
            World.generalSave.get().put("nid", nid);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + nid + "/statuses");
        JsonObject request = new JsonObject();
        request.addProperty("status", status);
        request.addProperty("statusReason", "set by automation");
        request.addProperty("changedBy", "152");
        PostWithProjectId(String.valueOf(request));
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public void updateNotification(String nid, String status, String reason) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            Map<String, Object> notifications = (Map<String, Object>) getCorrespondence(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString()).getList("recipients[0].notifications").get(0);
            nid = notifications.get("notificationId").toString();
            World.generalSave.get().put("nid", nid);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + nid + "/statuses");
        JsonObject request = new JsonObject();
        request.addProperty("status", status);
        request.addProperty("statusReason", reason);
        request.addProperty("changedBy", "152");
        PostWithProjectId(String.valueOf(request));
        World.generalSave.get().put("updateNidTraceId" + Hooks.nameAndTags.get(), traceId.get());
        System.out.println("storing traceId - " + traceId.get() + " |" + Hooks.nameAndTags.get() + " |" + "\n for updating nid - " + nid);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    public void updateNotification(String nid, String status, Date returnDate) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + nid + "/statuses");
        JsonObject request = new JsonObject();
        request.addProperty("status", status);
        request.addProperty("statusReason", "set by automation");
        request.addProperty("returnDate", String.valueOf(returnDate));
        PostWithProjectId(String.valueOf(request));
    }

    public JsonPath getallactivecorrespondances() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/activecorrespondences/" + projectId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        waitFor(5);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String barcodeGenerator(String notification, String barcode) {
        int diff = 12 - notification.length();
        String zeroes = "";
        int barcodediff = 4 - barcode.length();
        String barcodezeroes = "";
        String result = "0101";

        for (; diff > 0; diff--) {
            zeroes = zeroes + "0";
        }
        for (; barcodediff > 0; barcodediff--) {
            barcodezeroes = barcodezeroes + "0";
        }

        result = zeroes + notification + result + barcodezeroes + barcode;
        return result;
    }

    public void postOutboundCorrespondenceDefinitionToServerWithBodyElement(String project) {
        Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
        World.save.get().put("mmsId", params.get("ID"));
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceDefinitionWithBodyData.json");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("mmsId", params.get("ID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", params.get("stateID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceName", params.get("Name"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("description", params.get("description"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", "");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("userMayRequest", false);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("reasonForEnding", "false");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdBy", "Service AccountOne");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdDatetime", getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("updatedDatetime", getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("bodyDataSource", params.get("bodyDataSoource"));
        System.out.println(requestParams);
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/correspondence");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        World.random.get().put("jsonBody", apiTestDataUtil.jsonElement.getAsJsonObject());
        save.get().put("correspondenceDefId", World.getWorld().jsonPath.get().get("correspondence.correspondenceId").toString());
        World.save.get().put("definitionBodyVersion", World.getWorld().jsonPath.get().get("correspondence.bodyDataSchemaVersion").toString());
    }

    public void postInboundCorrespondenceDefinitionToServer(String project) {
        Map<String, String> params = World.getWorld().inboundCorrespondenceDefinition.get().returnValues();
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/InboundCorrespondenceDefinition.json");
        if (project.equalsIgnoreCase("CorrespondenceRegression")) {
            String projectId = ConfigurationReader.getProperty("correspondenceRegressionProjectName").split(" ")[0];
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", projectId);
        } else {
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("barcode", params.get("barcode"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("name", params.get("name"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("description", params.get("description"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdBy", 144);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("updatedBy", 144);

//        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("bodyDataSource", params.get("bodyDataSoource"));
        System.out.println(requestParams);
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/inboundcorrespondencedefinition");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        World.random.get().put("jsonBody", apiTestDataUtil.jsonElement.getAsJsonObject());
        inboundCorrespondenceDefinitionResponse = apiClassUtil.response;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode = apiClassUtil.statusCode;
        generalSave.get().put("statusCode", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(inboundCorrespondenceDefinitionResponse.toString());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
    }


    public JsonPath retrieveInboundCorrespondenceDefinition(String field) {
        World.getWorld().inboundCorrespondenceDefinition.get().ID = World.getWorld().jsonPath.get().getString("inboundCorrespondence.inboundCorrespondenceDefinitionId");
        synchronized (World.getWorld().inboundCorrespondenceTaskRule) {
            World.getWorld().inboundCorrespondenceTaskRule.set(World.getWorld().jsonPath.get().getMap("inboundCorrespondence.inboundCorrespondenceTaskRule[0]"));
        }
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal(true);
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiTestDataUtil.getJsonFromFile("dms/InboundCorrespondenceDefinitionSearch.json");
        apiClassUtil.setEndPoint("/inboundcorrespondencedefinition");
        switch (field.toUpperCase()) {
            case "NAME":
                apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("name", World.getWorld().inboundCorrespondenceDefinition.get().name);
                break;
            case "BARCODE":
                apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("barcode", World.getWorld().inboundCorrespondenceDefinition.get().barcode);
                break;
            case "ID":
                apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("inboundCorrespondenceDefinitionId", World.getWorld().inboundCorrespondenceDefinition.get().ID);
                break;
        }
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        World.random.get().put("jsonBody", apiTestDataUtil.jsonElement.getAsJsonObject());
        System.out.println(apiClassUtil.responseString);
        synchronized (World.getWorld().inboundCorrespondenceTaskRule) {
            World.getWorld().inboundCorrespondenceTaskRule.set(apiClassUtil.jsonPathEvaluator.get("inboundCorrespondence.inboundCorrespondenceTaskRule[0]"));
        }
        return World.getWorld().jsonPath.get();
    }

    public void addChannelToOutboundDefinition(String channelType) {
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceChannelWithTemplate.json");
        int channelId = 0;
        switch (channelType) {
            case "Email":
                channelId = 2;
                break;
            case "Mail":
                channelId = 1;
                break;
            case "Text":
                channelId = 3;
                break;
            case "Fax":
                channelId = 4;
                break;
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", save.get().get("correspondenceDefId"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelId", channelId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelType", channelType);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", LocalDateTime.now().toString());
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/channeldefinition");
        apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject());
    }

    public void addChannelToOutboundCorrDefinition(String channelType, String corrId) {
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceChannelWithTemplate.json");
        int channelId = 0;
        switch (channelType) {
            case "Email":
                channelId = 2;
                break;
            case "Mail":
                channelId = 1;
                break;
            case "Text":
                channelId = 3;
                break;
            case "Fax":
                channelId = 4;
                break;
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", corrId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelId", channelId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelType", channelType);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", LocalDateTime.now().toString());
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/channeldefinition");
        apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject());
        System.out.println((apiClassUtil.apiJsonObject.get("status").toString()));
    }

    public void updateOutboundDefinitionRequest(Map<String, String> dataTable) {
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceChannelWithTemplate.json");
        int channelId = 0;
        switch (dataTable.get("channel")) {
            case "Mail":
                channelId = 1;
                break;
            case "Email":
                channelId = 2;
                break;
            case "Text":
                channelId = 3;
                break;
            case "Fax":
                channelId = 4;
                break;
        }

        if (dataTable.get("correspondenceDefId").equalsIgnoreCase("previouslycreated"))
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", save.get().get("correspondenceDefId"));
        else
            apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceId", dataTable.get("correspondenceDefId"));

        if (dataTable.get("templateSystem").equalsIgnoreCase("random")) {
            save.get().put("templateSystem", RandomStringUtils.randomAlphanumeric(50));
            apiTestDataUtil.jsonElement.getAsJsonObject().getAsJsonArray("languageTemplate").get(0).getAsJsonObject()
                    .addProperty("templateSystem", save.get().get("templateSystem"));
        } else
            apiTestDataUtil.jsonElement.getAsJsonObject().getAsJsonArray("languageTemplate").get(0).getAsJsonObject()
                    .addProperty("templateSystem", dataTable.get("templateSystem"));

        if (dataTable.get("documentId").equalsIgnoreCase("random")) {
            save.get().put("externaldocumentId", RandomStringUtils.randomAlphanumeric(50));
            apiTestDataUtil.jsonElement.getAsJsonObject().getAsJsonArray("languageTemplate").get(0).getAsJsonObject()
                    .addProperty("documentId", save.get().get("externaldocumentId"));
        } else
            apiTestDataUtil.jsonElement.getAsJsonObject().getAsJsonArray("languageTemplate").get(0).getAsJsonObject()
                    .addProperty("documentId", dataTable.get("documentId"));

        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelId", channelId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("channelType", dataTable.get("channel"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", LocalDateTime.now().toString());
        // APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/channeldefinition");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        World.random.get().put("jsonBody", apiTestDataUtil.jsonElement.getAsJsonObject());

    }

    public void validateOutboundChannelDefinitionPOSTResponse(Map<String, String> dataTable) {
        String expected_documentid, expected_templateSystem;
        for (String searchCriteria : dataTable.keySet()) {
            switch (searchCriteria) {
                case "documentId":
                    String actual_documentid = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("channelDefinitionResponse.languageTemplate[0].documentId").toString();
                    if (dataTable.get("documentId").equalsIgnoreCase("random"))
                        expected_documentid = save.get().get("externaldocumentId");
                    else
                        expected_documentid = dataTable.get("documentId");
                    Assert.assertEquals(actual_documentid, expected_documentid);
                    break;

                case "templateSystem":
                    String actual_templateSystem = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("channelDefinitionResponse.languageTemplate[0].templateSystem").toString();
                    if (dataTable.get("templateSystem").equalsIgnoreCase("random"))
                        expected_templateSystem = save.get().get("templateSystem");
                    else
                        expected_templateSystem = dataTable.get("templateSystem");
                    Assert.assertEquals(actual_templateSystem, expected_templateSystem);
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case - " + searchCriteria);
            }

        }

    }

    public void validateOutboundChannelDefinitionGetResponse(Map<String, String> dataTable) {
        String expected_documentid, expected_templateSystem;
        for (String searchCriteria : dataTable.keySet()) {
            switch (searchCriteria) {
                case "documentId":
                    String actual_documentid = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("channelDefinitionResponseList[0].languageTemplate[0].documentId").toString();
                    if (dataTable.get("documentId").equalsIgnoreCase("random"))
                        expected_documentid = save.get().get("externaldocumentId");
                    else
                        expected_documentid = dataTable.get("documentId");
                    Assert.assertEquals(actual_documentid, expected_documentid);
                    break;

                case "templateSystem":
                    String actual_templateSystem = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("channelDefinitionResponseList[0].languageTemplate[0].templateSystem").toString();
                    if (dataTable.get("templateSystem").equalsIgnoreCase("random"))
                        expected_templateSystem = save.get().get("templateSystem");
                    else
                        expected_templateSystem = dataTable.get("templateSystem");
                    Assert.assertEquals(actual_templateSystem, expected_templateSystem);
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case - " + searchCriteria);
            }

        }

    }


    public void postOutboundCorrespondenceDefinitionToServerWithBodyElement(List<String> bodyElements) {
        JsonObject jsonObject = (JsonObject) World.random.get().get("jsonBody");
        jsonObject.getAsJsonObject().addProperty("correspondenceId", save.get().get("correspondenceDefId"));
        for (String bodyElement : bodyElements) {
            switch (bodyElement) {
                case "First Name":
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("required").getAsJsonArray().add("First Name");
                    JsonObject jsonObjectFirstName = new JsonObject();
                    jsonObjectFirstName.addProperty("type", "String");
                    jsonObjectFirstName.addProperty("description", "First Name");
                    jsonObjectFirstName.addProperty("maxLength", 150);
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("properties").getAsJsonObject().add("First Name", jsonObjectFirstName);
                    break;
                case "Last Name":
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("required").getAsJsonArray().add("Last Name");
                    JsonObject jsonObjectLastName = new JsonObject();
                    jsonObjectLastName.addProperty("type", "String");
                    jsonObjectLastName.addProperty("description", "Last Name");
                    jsonObjectLastName.addProperty("maxLength", 150);
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("properties").getAsJsonObject().add("Last Name", jsonObjectLastName);
                    break;
                case "DOB":
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("required").getAsJsonArray().add("DOB");
                    JsonObject jsonObjectDOB = new JsonObject();
                    jsonObjectDOB.addProperty("type", "String");
                    jsonObjectDOB.addProperty("description", "DOB");
                    jsonObjectDOB.addProperty("format", "date");
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("properties").getAsJsonObject().add("DOB", jsonObjectDOB);
                    break;
                case "SSN":
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("required").getAsJsonArray().add("SSN");
                    JsonObject jsonObjectSSN = new JsonObject();
                    jsonObjectSSN.addProperty("type", "");
                    jsonObjectSSN.addProperty("description", "SSN");
                    jsonObject.getAsJsonObject().get("bodyDataSchema").getAsJsonObject().get("properties").getAsJsonObject().add("SSN", jsonObjectSSN);
                    break;
                case "null":
                    System.out.println("Body elements wasn't changed");
                    break;
                default:
                    throw new IllegalArgumentException("Body element not found: " + bodyElement);
            }
        }
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/correspondence");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(jsonObject).jsonPathEvaluator);
        }
        World.save.get().put("correspondenceId", World.getWorld().jsonPath.get().getString("data.id"));
        World.save.get().put("definitionBodyVersion", World.getWorld().jsonPath.get().get("correspondence.bodyDataSchemaVersion").toString());
        World.random.get().put("jsonBody", jsonObject);
    }

    public String getUserAccountNameById(String Id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTMURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/tm/project/user/" + Id);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        String firstname = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("staff.firstName");
        String lastName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("staff.lastName");
        return firstname + " " + lastName;
    }

    public Response lookupexternalconsumerid(String externaltype, String externalvalue) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/onbase/lookupexternalconsumerid");
        JsonObject requestParams = new JsonObject();
        requestParams.addProperty("externalConsumerIdType", externaltype);
        requestParams.addProperty("externalConsumerID", externalvalue);
        Response response = new APIAutoUtilities().postAPI(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint, requestParams);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        return response;
    }

    public Response createAConsumerwithExternalType(String externaltype, String externalvalue) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/consumer");
        requestParams = generateConsumerRequest(externaltype, externalvalue);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams);
        Response response = new APIAutoUtilities().postAPI(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint, requestParams);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //assertTrue(api.responseString.contains("success"));
        //assertEquals(api.jsonPathEvaluator.get("status"), "success");
        searchForConsumer(World.generalSave.get().get("ConsumerFirstName").toString(), World.generalSave.get().get("ConsumerLastName").toString());
        return response;
    }

    private JsonObject generateConsumerRequest(String type, String value) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateConsumer.json");

        if (value.equalsIgnoreCase("RANDOM"))
            value = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();

        String consumerFirstName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString;
        String consumerMiddleName = "M";
        String consumerLastName = "ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString);
        String consumerDOB = dateFormat.format(todayDate);
        String consumerSSN = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;

        World.generalSave.get().put("ExternalConsumerIdValue", value);
        World.generalSave.get().put("ConsumerFirstName", consumerFirstName);
        World.generalSave.get().put("consumerMiddleName", consumerMiddleName);
        World.generalSave.get().put("ConsumerLastName", consumerLastName);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerMiddleName", consumerMiddleName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", consumerDOB);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", consumerSSN);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0).
                getAsJsonObject().addProperty("identificationNumberType", type);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0).
                getAsJsonObject().addProperty("externalConsumerId", value);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private String searchForConsumer(String firstname, String lastname) {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_search_consumer_by_the(firstname, lastname);
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_consumer_search_api();
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_run_the_consumer_search_api();
        World.generalSave.get().put("ConsumerId", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerId.get());
        return API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerId.get();
    }


    public byte[] downloadCorrespondence(String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/document/contents/" + id + "?type=Outbound%Correspondence&format=pdf");
        return given()
                .header("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId())
                .header("projectId", getSendNowPName())
                .header("Authorization", "Bearer " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                .header("x-b3-traceid", ApiTestDataUtil.generate_random_trace_id())
                .header("x-b3-spanid", ApiTestDataUtil.generate_random_trace_id().substring(16, 32))
                .contentType("application/octet-stream")
                .get(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint).asByteArray();
    }

    public void downloadToDirectory(byte[] pdfFile, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(pdfFile);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractContent(byte[] pdf) throws IOException {
        PDDocument document = new PDDocument();
        String result = "";
        try {
            document = PDDocument.load(new ByteArrayInputStream(pdf));
            result = new PDFTextStripper().getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return result;
    }

    public Map<String, String> createOCDefinitionWithChannels(List<String> dataTable) {
        Map<String, String> params = World.getWorld().outboundCorrespondenceDefinition.get().returnValues();
        World.save.get().put("mmsId", params.get("ID"));
        ApiTestDataUtil apiTestDataUtil = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        apiTestDataUtil.getJsonFromFile("dms/OutboundCorrespondenceDefinitionWithoutBodyData.json");
        String mmsId = params.get("ID");
        String corName = params.get("Name");
        Map<String, String> mapOfDefIdAndNames = new HashMap<>();
        mapOfDefIdAndNames.put("mmsId", mmsId);
        mapOfDefIdAndNames.put("corName", corName);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("mmsId", mmsId);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("projectId", APIClassUtil.getApiClassUtilThreadLocal().getProjectId());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", params.get("stateID"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("correspondenceName", corName);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("description", params.get("description"));
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("stateReferenceId", "");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("userMayRequest", true);
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("reasonForEnding", "false");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdBy", "Service AccountOne");
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("createdDatetime", getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("startDate", getDateTimeWithTFormat());
        apiTestDataUtil.jsonElement.getAsJsonObject().addProperty("updatedDatetime", getDateTimeWithTFormat());


        JsonArray requiredKeyArray = new JsonArray();
        for (int i = 0; i < dataTable.size(); i++) {
            JsonObject reqKey = new JsonObject();
            reqKey.addProperty("requiredkeyid", i + 1);
            reqKey.addProperty("requiredKeyValue", dataTable.get(i));
            requiredKeyArray.add(reqKey);
        }
        apiTestDataUtil.jsonElement.getAsJsonObject().add("correspondenceRequiredKeys", requiredKeyArray);

        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        apiClassUtil.setEndPoint("/correspondence");
        synchronized (World.getWorld().jsonPath){
            World.getWorld().jsonPath.set(apiClassUtil.PostAPIWithParameter(apiTestDataUtil.jsonElement.getAsJsonObject()).jsonPathEvaluator);
        }
        save.get().put("correspondenceDefId", World.getWorld().jsonPath.get().get("correspondence.correspondenceId").toString());
        return mapOfDefIdAndNames;
    }

    public void addChannelsToCorrDefinition(List<String> listOfChannels, String corrId) {
        for (String channel : listOfChannels) {
            addChannelToOutboundCorrDefinition(channel, corrId);
        }
    }

    public void addChannelsToDefinition(List<String> listOfChannels) {
        for (String channel : listOfChannels) {
            addChannelToOutboundDefinition(channel);
        }
    }

    public JsonPath getRecipientsInfoByCaseNumber(String caseID) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("caseId", caseID);
        JsonArray channels = new JsonArray();
        channels.add("Mail");
        channels.add("Email");
        channels.add("Fax");
        channels.add("Text");
        requestBody.add("channels", channels);
        requestBody.addProperty("includeNonDefaultRecipients", true);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBody);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath caseCorrespondence(JsonObject requestBody) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBody);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getRecipientsInfoByConsumerNumber(String consumerId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/casecorrespondence");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("consumerId", consumerId);
        JsonArray channels = new JsonArray();
        channels.add("Mail");
        requestBody.add("channels", channels);
        requestBody.addProperty("includeNonDefaultRecipients", false);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestBody);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath createObLinkedToApplication(String applicationId, String consumerId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        regardingConsumerId.add(consumerId);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("applicationId", applicationId);
        request.add("anchor", anchor);
        request.add("requester", requester);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath getNotworkLocation(String userId, String id) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/correspondence/inbound/secureDocument/path/" + userId + "/" + id);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }


    public JSONArray getInboundCorrespondenceEditHistoryType(String cid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/typehistory/" + cid);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiRequest();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        JSONArray jsonArr = new JSONArray(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return jsonArr;
    }

    public JsonPath createAConsumerOnACaseWithExternalType(String externaltype, String externalvalue) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseLoaderURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/caseloader");
        requestParams = generateConsumerOnACaseRequest(externaltype, externalvalue);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams);

        JsonObject jsonRequestObject = new JsonObject();
        jsonRequestObject.addProperty("consumerFirstName", String.valueOf(generalSave.get().get("ConsumerFirstName")));
        jsonRequestObject.addProperty("consumerLastName", String.valueOf(generalSave.get().get("ConsumerLastName")));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseConsumerURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/app/crm/case/consumers");
        return PostWithProjectIdVAINEB(String.valueOf(jsonRequestObject));
    }

    private JsonObject generateConsumerOnACaseRequest(String type, String value) {
        Faker faker = new Faker();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        if (value.equalsIgnoreCase("RANDOM"))
            value = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();

        String consumerFirstName = faker.name().firstName();
        String consumerMiddleName = "M";
        String consumerLastName = faker.name().lastName();
        String consumerSSN = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        String consumerDOB = dateFormat.format(todayDate);

        World.generalSave.get().put("ExternalConsumerIdValue", value);
        World.generalSave.get().put("ConsumerFirstName", consumerFirstName);
        World.generalSave.get().put("consumerMiddleName", consumerMiddleName);
        World.generalSave.get().put("ConsumerLastName", consumerLastName);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().addProperty("caseIdentificationNumber", String.valueOf(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().addProperty("caseIdentificationNumberType", "State");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDOB);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerMiddleName", consumerMiddleName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject()
                .getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts").getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet1", faker.address().streetAddress());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject()
                .getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts").getAsJsonObject().getAsJsonObject("address").addProperty("addressStreet2", faker.address().secondaryAddress());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject()
                .getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts").getAsJsonObject().getAsJsonObject("address").addProperty("addressCity", faker.address().city());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject()
                .getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts").getAsJsonObject().getAsJsonObject("address").addProperty("addressState", faker.address().state());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject()
                .getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts").getAsJsonObject().getAsJsonObject("address").addProperty("addressZip", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(5).randomNumber);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty("identificationNumberType", type);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().get("case").getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().addProperty("externalConsumerId", value);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public JsonPath getAllChannelsByMMSId() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/definitions/channels/" + getDefaultOutboundType());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
    }

    public JsonPath getAllChannelsByMMSId(String mmsid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/definitions/channels/" + mmsid);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
    }

    public JsonPath pdfgenerator(String nid, String uid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/letterData/pdfGenerator");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", nid);
        request.addProperty("userId", uid);
        return PostWithProjectId(String.valueOf(request));
    }

    public JsonPath getActiveChannelsByCorrId(String corrId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/activechannels/" + corrId);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
    }

    public JsonPath getLinkForCorrespondence(String corrId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCorresLink"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/" + corrId + "?size=5&page=0");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public Response createCorrespondenceBodyDataElementsSchema(String type) {
        //Map<String, String> caseConsumerIds = (Map<String, String>) generalSave.get().get("caseConsumerId");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences");
        JsonObject request = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/OBCorrespondenceEmptyStrings.json").jsonElement.getAsJsonObject();
        // request.get("anchor").getAsJsonObject().addProperty("caseId", caseConsumerIds.get("caseId"));
        // request.get("anchor").getAsJsonObject().get("regardingConsumerId").getAsJsonArray().add(caseConsumerIds.get("consumerId"));
        request.get("anchor").getAsJsonObject().addProperty("projectId", getProjectId());
        request.get("anchor").getAsJsonObject().add("caseId", JsonNull.INSTANCE);
        request.addProperty("correspondenceDefinitionMMSCode", "6eUAamS1zP");


        JsonArray jsonArray_string = new JsonArray();
        jsonArray_string.add(RandomStringUtils.random(149, true, true));
        JsonArray jsonArray_num = new JsonArray();
        jsonArray_num.add(-12.369);
        JsonArray jsonArray_date = new JsonArray();
        jsonArray_date.add("2022-01-18");
        JsonArray jsonArray_boolean = new JsonArray();
        jsonArray_boolean.add(true);

        switch (type.toLowerCase()) {
            case "validschema":
                request.get("bodyData").getAsJsonObject().add("ST_1_REQ_AM_FN", jsonArray_string);
                request.get("bodyData").getAsJsonObject().add("Num_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().add("Date_1_REQ_AM_FN", jsonArray_date);
                request.get("bodyData").getAsJsonObject().add("CB_1_REQ_AM_FN", jsonArray_boolean);
                JsonArray jsonArray_lt = new JsonArray();
                jsonArray_lt.add(RandomStringUtils.random(2000, true, true));
                request.get("bodyData").getAsJsonObject().add("LT_1_REQ_AM_FN", jsonArray_lt);
                request.get("bodyData").getAsJsonObject().addProperty("LT_2_FN", "optional long text");
                break;
            case "invalidobjecttype":
                request.get("bodyData").getAsJsonObject().addProperty("ST_1_REQ_AM_FN", "required short text");
                request.get("bodyData").getAsJsonObject().addProperty("Num_1_REQ_AM_FN", 123);
                request.get("bodyData").getAsJsonObject().addProperty("Date_1_REQ_AM_FN", "2022-01-18");
                request.get("bodyData").getAsJsonObject().addProperty("CB_1_REQ_AM_FN", true);
                request.get("bodyData").getAsJsonObject().addProperty("LT_1_REQ_AM_FN", "required long text");
                request.get("bodyData").getAsJsonObject().add("LT_2_FN", jsonArray_string);
                break;
            case "invaliddatatype":
                request.get("bodyData").getAsJsonObject().add("ST_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().add("Num_1_REQ_AM_FN", jsonArray_string);
                request.get("bodyData").getAsJsonObject().add("Date_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().add("CB_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().add("LT_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().addProperty("LT_2_FN", 123);
                break;
            case "invalidshorttextlength":
                JsonArray jsonArray_st = new JsonArray();
                jsonArray_st.add(RandomStringUtils.random(160, true, true));
                request.get("bodyData").getAsJsonObject().add("ST_1_REQ_AM_FN", jsonArray_st);
                request.get("bodyData").getAsJsonObject().add("Num_1_REQ_AM_FN", jsonArray_num);
                request.get("bodyData").getAsJsonObject().add("Date_1_REQ_AM_FN", jsonArray_date);
                request.get("bodyData").getAsJsonObject().add("CB_1_REQ_AM_FN", jsonArray_boolean);
                request.get("bodyData").getAsJsonObject().add("LT_1_REQ_AM_FN", jsonArray_string);
                request.get("bodyData").getAsJsonObject().addProperty("LT_2_FN", "optional long text");
                break;
            case "missingrequiredbodydataelements":
                request.get("bodyData").getAsJsonObject().addProperty("LT_2_FN", "optional long text");
                break;
        }

        Response response = new APIAutoUtilities().postAPI(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().baseUri + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint, request);
        System.out.println("Create ob response -------------------- \n" + response.prettyPrint());
        return response;
    }

    public JsonObject generateCaseLoaderRequestWithSpecifiedParameters(Map<String, String> map) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", map.get("consumerFirstName"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerMiddleName", map.get("consumerMiddleName"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", map.get("consumerLastName"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSuffix", map.get("consumerSuffix"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", map.get("ssn"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", map.get("dob"));
        if (map.containsKey("consumerRole"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", map.get("consumerRole"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressStreet1", map.get("addressStreet1"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressStreet2", map.get("addressStreet2"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressCity", map.get("addressCity"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressState", map.get("addressState"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressZip", map.get("addressZip"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("address").addProperty("addressZipFour", map.get("addressZipFour"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("email").addProperty("emailAddress", map.get("emailAddress"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts")
                .getAsJsonObject("phone").addProperty("phoneNumber", map.get("phoneNumber"));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().remove("communicationPreferences");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("communicationPreferences", null);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    public void addRequiredKeysBaseOnTypeToRequest(String mmsId, JsonObject anchor) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/activecorrespondences/" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        List<String> listOfRequiredKeys = new ArrayList<>();
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        int defCount = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("listCorrespondence.size()");
        for (int i = 0; i < defCount; i++) {
            if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("listCorrespondence[" + i + "].mmsId").equals(mmsId)) {
                listOfRequiredKeys = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("listCorrespondence[" + i + "].correspondenceRequiredKeys.requiredKeyValue");
            }
        }
        JsonArray conIds = new JsonArray();
        if (listOfRequiredKeys.contains("Consumer ID") || listOfRequiredKeys.contains("Consumer IDs")) {
            conIds.add(caseConsumerId.get("consumerId"));
            anchor.getAsJsonObject().get("anchor").getAsJsonObject().add("regardingConsumerId", conIds);
        } else {
            anchor.getAsJsonObject().get("anchor").getAsJsonObject().add("regardingConsumerId", null);
        }
        if (listOfRequiredKeys.contains("Case ID")) {
            anchor.getAsJsonObject().getAsJsonObject("anchor").addProperty("caseId", caseConsumerId.get("caseId"));
        } else {
            anchor.getAsJsonObject().getAsJsonObject("anchor").add("caseId", null);
        }
    }

    public JsonPath getLinksToApplication(String applicationId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiATSApplicationData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("externallinks/" + applicationId);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI().jsonPathEvaluator;
    }

    public JsonArray sortJsonArray(JsonArray jsonArray) {
        JsonParser parser = new JsonParser();
        if (jsonArray != null) {
            List<String> arraysForSorting = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                arraysForSorting.add(jsonArray.get(i).toString());
            }
            Collections.sort(arraysForSorting);
            JsonArray sortedJsonArray = new JsonArray();
            for (int i = 0; i < arraysForSorting.size(); i++) {
                JsonObject jsonObject = (JsonObject) parser.parse(arraysForSorting.get(i));
                sortedJsonArray.add(jsonObject);
            }
            return sortedJsonArray;
        }
        return null;
    }

    /**
     * @param data consumerFirstName,consumerLastName,consumerSSN,consumerDateOfBirth,consumerRole,writtenLanguage
     * @return case loader request
     */
    public JsonObject buildCaseLoaderRequestEcms(Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");
        String caseIdentificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNumber " + caseIdentificationNumber);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);
        String currentDate = OffsetDateTime.now(ZoneOffset.UTC).toString();
        JsonObject request = (JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement;
        if (!data.containsKey("consumerFirstName")) {
            String consumerFirstName = RandomStringUtils.random(8, true, false);
            data.put("consumerFirstName", consumerFirstName);
            generalSave.get().put("consumerFirstName", consumerFirstName);
        }
        if (!data.containsKey("consumerLastName")) {
            String consumerLastName = RandomStringUtils.random(8, true, false);
            data.put("consumerLastName", consumerLastName);
            generalSave.get().put("consumerLastName", consumerLastName);
        }
        if (!data.containsKey("consumerMiddleName")) {
            String consumerMiddleName = RandomStringUtils.random(8, true, false);
            data.put("consumerMiddleName", consumerMiddleName);
            generalSave.get().put("consumerMiddleName", consumerMiddleName);
        }
        if (!data.containsKey("consumerSuffix")) {
            String consumerSuffix = RandomStringUtils.random(2, true, false);
            data.put("consumerSuffix", consumerSuffix);
            generalSave.get().put("consumerSuffix", consumerSuffix);
        }
        if (!data.containsKey("consumerSSN")) {
            data.put("consumerSSN", RandomStringUtils.random(9, false, true));
        }
        if (!data.containsKey("writtenLanguage")) {
            data.put("writtenLanguage", "English");
        }
        if (data.containsKey("correspondencePreference")) {
            JsonObject commPref = new JsonObject();
            JsonArray communicationPreferences = new JsonArray();
            commPref.addProperty("valuePairIdCommPref", "CORRESPONDENCE_PAPERLESS");
            commPref.addProperty("defaultInd", Boolean.TRUE);
            commPref.addProperty("effectiveStartDate", currentDate);
            commPref.add("effectiveEndDate", JsonNull.INSTANCE);
            communicationPreferences.add(commPref);
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("communicationPreferences", communicationPreferences);
        } else {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("communicationPreferences", JsonNull.INSTANCE);
        }

        //adding consumer information
        if (data.containsKey("caseIdentificationNumberType")) {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").addProperty("caseIdentificationNumberType", data.get("caseIdentificationNumberType"));
        }
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNumber);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", data.get("consumerFirstName"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", data.get("consumerLastName"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerMiddleName", data.get("consumerMiddleName"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSuffix", data.get("consumerSuffix"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", data.get("consumerSSN"));
//        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
//                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", data.get("consumerDateOfBirth"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", data.get("consumerRole"));
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", currentDate);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);
        if ("null".equalsIgnoreCase(data.get("writtenLanguage"))) {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("writtenLanguage", JsonNull.INSTANCE);
        } else {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("writtenLanguage", data.get("writtenLanguage"));
        }

        if ("IN-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("caseIdentificationNumberType", "State");
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                    .get(0).getAsJsonObject().addProperty("identificationNumberType", "Medicaid");
        }

        if (data.containsKey("caseIdentificationNumberType")) {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("caseIdentificationNumberType", data.get("caseIdentificationNumberType"));

        }
        if (data.containsKey("identificationNumberType")) {
            request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                    .get(0).getAsJsonObject().addProperty("identificationNumberType", data.get("identificationNumberType"));

        }

        //adding contacts
        JsonObject contacts = new JsonObject();
        JsonObject mail = new JsonObject();
        JsonObject secondMail = new JsonObject();
        JsonObject email = new JsonObject();
        JsonObject text = new JsonObject();
        JsonObject secondText = new JsonObject();
        JsonObject fax = new JsonObject();
        JsonObject secondFax = new JsonObject();
//        JsonArray texts = new JsonArray();
        JsonArray phones = new JsonArray();
        JsonArray mails = new JsonArray();
        boolean useMail = false;
        boolean useSecondMail = false;
        boolean useEmail = false;
        boolean useFax = false;
        boolean useSecondFax = false;
        boolean useText = false;
        boolean useSecondText = false;
        for (Map.Entry<String, String> dataObject : data.entrySet()) {
            if (dataObject.getKey().contains("Destination")) continue;
            if (dataObject.getKey().startsWith("mail")) {
                useMail = true;
                if (dataObject.getKey().contains("addressVerified") || dataObject.getKey().contains("primaryIndicator")) {
                    mail.addProperty(dataObject.getKey().substring(4), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                mail.addProperty(dataObject.getKey().substring(4), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("secondMail")) {
                useSecondMail = true;
                if (dataObject.getKey().contains("addressVerified") || dataObject.getKey().contains("primaryIndicator")) {
                    secondMail.addProperty(dataObject.getKey().substring(9), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                secondMail.addProperty(dataObject.getKey().substring(9), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("email")) {
                useEmail = true;
                if (dataObject.getKey().contains("primaryIndicator")) {
                    email.addProperty(dataObject.getKey().substring(5), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                email.addProperty(dataObject.getKey().substring(5), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("text")) {
                useText = true;
                if (dataObject.getKey().contains("smsEnabledInd") || dataObject.getKey().contains("primaryIndicator")) {
                    text.addProperty(dataObject.getKey().substring(4), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                text.addProperty(dataObject.getKey().substring(4), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("secondText")) {
                useSecondText = true;
                if (dataObject.getKey().contains("smsEnabledInd") || dataObject.getKey().contains("primaryIndicator")) {
                    secondText.addProperty(dataObject.getKey().substring(10), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                secondText.addProperty(dataObject.getKey().substring(10), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("fax")) {
                useFax = true;
                if (dataObject.getKey().contains("smsEnabledInd") || dataObject.getKey().contains("primaryIndicator")) {
                    fax.addProperty(dataObject.getKey().substring(3), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                fax.addProperty(dataObject.getKey().substring(3), String.valueOf(dataObject.getValue()));
            } else if (dataObject.getKey().startsWith("secondFax")) {
                useSecondFax = true;
                if (dataObject.getKey().contains("smsEnabledInd") || dataObject.getKey().contains("primaryIndicator")) {
                    secondFax.addProperty(dataObject.getKey().substring(9), Boolean.valueOf(String.valueOf(dataObject.getValue())));
                    continue;
                }
                secondFax.addProperty(dataObject.getKey().substring(9), String.valueOf(dataObject.getValue()));
            }
        }

        if (useMail) {
            mail.addProperty("effectiveStartDate", currentDate);
            contacts.add("address", mail);
            if (data.get("mailDestination").equalsIgnoreCase("null")) {
                contacts.add("address", JsonNull.INSTANCE);
            }
        }
        if (useSecondMail) {
            secondMail.addProperty("effectiveStartDate", currentDate);
            mails.add(secondMail);
            contacts.add("addresses", mails);
            if (data.get("secondMailDestination").equalsIgnoreCase("null")) {
                contacts.add("address", JsonNull.INSTANCE);
            }
        }
        if (useEmail) {
            email.addProperty("effectiveStartDate", currentDate);
            contacts.add("email", email);
            if (data.get("emailDestination").equalsIgnoreCase("null")) {
                contacts.add("email", JsonNull.INSTANCE);
            }
        }
        if (useText) {
            text.addProperty("effectiveStartDate", currentDate);
            contacts.add("phone", text);
            if (data.get("textDestination").equalsIgnoreCase("null")) {
                contacts.add("phone", JsonNull.INSTANCE);
            }
        }
        if (useSecondText) {
            secondText.addProperty("effectiveStartDate", currentDate);
            phones.add(secondText);
            if (data.get("secondTextDestination").equalsIgnoreCase("null")) {
                contacts.add("phones", JsonNull.INSTANCE);
            }
        }
        if (useFax) {
            fax.addProperty("effectiveStartDate", currentDate);
            contacts.add("phone", fax);
            if (data.get("faxDestination").equalsIgnoreCase("null")) {
                contacts.add("phone", JsonNull.INSTANCE);
            }
        }
        if (useSecondFax) {
            secondFax.addProperty("effectiveStartDate", currentDate);
            phones.add(secondFax);
            if (data.get("secondFaxDestination").equalsIgnoreCase("null")) {
                contacts.add("phones", JsonNull.INSTANCE);
            }
        }
        contacts.add("phones", phones);
        request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("contacts", contacts);

        // updating optIn as false

        if (data.containsKey("consentType_OptIn_false")) {

            JsonArray actualConsentTypeMap = request.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerConsent");

            for (JsonElement consumerConsent : actualConsentTypeMap) {
                if (consumerConsent.getAsJsonObject().get("consentType").getAsString().equals(data.get("consentType_OptIn_false")))
                    consumerConsent.getAsJsonObject().addProperty("optIn", false);
            }
        }

        return request;
    }

    public Map<String, String> generateRandomCaseId(JsonObject request) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiCaseLoaderURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/caseloader");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(request);
        Assert.assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Map<String, String> caseLoaderRequest = (Map<String, String>) generalSave.get().get("caseLoaderRequestMap");
        String ssn = caseLoaderRequest.get("consumerSSN");
        Assert.assertNotNull(ssn);
        Map<String, String> results = new HashedMap();
        results = searchConsumer("consumerSSN", ssn, results);
        return results;
    }

    public JsonPath createObLinkedToTask(String taskId, String consumerId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/correspondences");
        JsonObject request = new JsonObject();
        JsonObject anchor = new JsonObject();
        JsonObject requester = new JsonObject();
        JsonArray regardingConsumerId = new JsonArray();
        requester.addProperty("requesterId", 2492);
        requester.addProperty("requesterType", "ConnectionPoint");
        request.addProperty("correspondenceDefinitionMMSCode", getDefaultOutboundType());
        regardingConsumerId.add(consumerId);
        anchor.add("regardingConsumerId", regardingConsumerId);
        anchor.addProperty("taskId", taskId);
        request.add("anchor", anchor);
        request.add("requester", requester);
        return PostWithProjectId(String.valueOf(request));
    }


    public JsonPath retrieveUnprovisionedObCorrespondences() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences/unprovisioned/list");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public JsonPath getOutboundChannelDefinitionBycorrespondenceId(String corrID) {
        if (corrID.equalsIgnoreCase("previouslycreated"))
            corrID = save.get().get("correspondenceDefId");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/channeldefinition" + "/" + corrID);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        Assert.assertEquals(200, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    public String getuseridbyfirstname(String fn) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTMURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/mars/tm/project/userinfo");
        JsonObject request = new JsonObject();
        request.addProperty("firstName", fn);
        request.addProperty("projectName", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName());
        PostWithProjectId(String.valueOf(request));
        return ((Response) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().response).jsonPath().get("data[0].userId").toString();
    }

    public void getActiveLanguagesForTheProject(String project) {
        project = getProjectId();
        JsonPath response = getObSettings(project);
        List<Map<String, Object>> languages = response.getList("correspondenceSettingsResponse.projectLanguages");
        World.generalList.get().add(response.getString("correspondenceSettingsResponse.defaultLanguageName"));
        for (Map<String, Object> lang : languages) {
            World.generalList.get().add(lang.get("languageName").toString());
        }
    }

    public void getActiveChannelsandLanguagesForMMSID(String mmsid) {
        Date currentdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String todayDate = sdf.format(currentdate);

        JsonPath response = getAllChannelsByMMSId(mmsid);
        int channelListSize = response.getInt("channelDefinitionResponseList.size");
        for (int i = 0; i < channelListSize; i++) {
            Map<String, Object> templatelanguage = new HashMap<>();
            for (int j = 0; j < response.getInt("channelDefinitionResponseList[" + i + "].languageTemplate.size"); j++) {
                Map<String, Object> templatedetails = new HashMap<>();
                String language = response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].language");
                if (response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].endDate") == null) {
                    // storing language, templateid,Version for active templates
                    templatedetails.put("templateId", response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].templateId"));
                    templatedetails.put("version", response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].version"));
                    templatelanguage.put(language, templatedetails);
                } else {
                    try {
                        String enddate = response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].endDate");
                        if (sdf.parse(enddate).after(sdf.parse(todayDate))) {
                            // storing language, templateid,Version for active templates
                            templatedetails.put("templateId", response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].templateId"));
                            templatedetails.put("version", response.getString("channelDefinitionResponseList[" + i + "].languageTemplate[" + j + "].version"));
                            templatelanguage.put(language, templatedetails);

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            World.random.get().put(response.getString("channelDefinitionResponseList[" + i + "].channelType"), (HashMap) templatelanguage);

        }
    }

    public JsonPath getNotificationOrderService(int notificationId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/notifications/" + notificationId + "/object");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }


    public void getlanguagetemplateobject(String templateid) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint = ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition") + "/languagetemplateobject/" + templateid;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPIdefault();
    }

    public void postlanguagetemplateobject(Map<String, String> requestData) {
        File file = null;
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().endPoint = ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition") + "/languagetemplateobject";

        if (requestData.get("templateObject").equalsIgnoreCase("pdf"))
            file = new File("src/test/resources/testData/api/dms/text.pdf");
        if (requestData.get("templateObject").equalsIgnoreCase("txt"))
            file = new File("src/test/resources/testData/api/dms/test.txt");
        else if (requestData.get("templateObject").equalsIgnoreCase("tif"))
            file = new File("src/test/resources/testData/api/dms/test.tif");
        else if (requestData.get("templateObject").equalsIgnoreCase("doc"))
            file = new File("src/test/resources/testData/api/dms/test.doc");
        else if (requestData.get("templateObject").equalsIgnoreCase("docx"))
            file = new File("src/test/resources/testData/api/dms/test.docx");
        else if (requestData.get("templateObject").equalsIgnoreCase("18mbfile"))
            file = new File("src/test/resources/testData/api/dms/18mbfile.pdf");
        else if (requestData.get("templateObject").equalsIgnoreCase("130length"))
            file = new File("src/test/resources/testData/api/dms/GLLq4eFOV0v6ca4T4oNj17XMLOgFR1wFjT0kuHONgEoy9v5lwEMHh4NtqAI7tcYmmgeHP2AmtPsOH6Bpam46NFODIKh7DxfobBtMdOQ2ClfI5fQDXgM8keJzpdqO.docx");

        Map<String, Object> map = new HashMap<>();
        map.put("languageTemplateId", requestData.get("languageTemplateId"));
        map.put("templateObject", file);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostApiWithMultiparts(map);

        System.out.println("response :" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        generalSave.get().put("languagetemplateobjectResponse", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        generalSave.get().put("languagetemplateobjectStatuscode", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);

    }
}
