package com.maersk.dms.step_definitions;

import com.google.gson.JsonArray;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class AutoLinkMappingStepDefs extends CRMUtilities implements ApiBase {

    private final String baseURI = ConfigurationReader.getProperty("apiDefinitionService");
    private final String autolinkMapEndPoint = "/autolinkmap";
    private final ThreadLocal<ApiTestDataUtil> apitdu = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    public static final ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    public static ThreadLocal<Integer> autolinkMapId = ThreadLocal.withInitial(() -> 0);

    public void addDataTableValuesToJson(Map<String, String> dataTable) {
        if (null != dataTable.get("inboundCorrespondenceTypes") && dataTable.get("inboundCorrespondenceTypes").contains(",")) {
            apiAutoUtilities.get().requestParams = apitdu.get().getJsonFromFile("dms/autolinkMap2.json").jsonElement.getAsJsonObject();
        } else {
            if (null != dataTable.get("correspondenceMetadataName") && dataTable.get("correspondenceMetadataName").contains(",")) {
                apiAutoUtilities.get().requestParams = apitdu.get().getJsonFromFile("dms/autolinkMap3.json").jsonElement.getAsJsonObject();
            } else
                apiAutoUtilities.get().requestParams = apitdu.get().getJsonFromFile("dms/autolinkMap.json").jsonElement.getAsJsonObject();
        }
        for (String field : dataTable.keySet()) {
            if (null==dataTable.get(field)||dataTable.get(field).equalsIgnoreCase("null")) {
                continue;
            }
            switch (field) {
                case "inboundCorrespondenceTypes":
                    JsonArray jsonArrayIB = new JsonArray();
                    String[] elementsIB = dataTable.get("inboundCorrespondenceTypes").split(",");
                    for (int i = 0; i < elementsIB.length; i++) {
                        jsonArrayIB.add(elementsIB[i]);
                    }
                    apiAutoUtilities.get().requestParams.add("inboundCorrespondenceTypes", jsonArrayIB);
                    break;
                case "serviceRequestTypeIDs":
                    JsonArray jsonArrayID = new JsonArray();
                    String[] elementsSR = dataTable.get("serviceRequestTypeIDs").split(",");
                    for (int i = 0; i < elementsSR.length; i++) {
                        if (elementsSR[i].equalsIgnoreCase("Random")) {
                            jsonArrayID.add(BrowserUtils.radomNumber(10000));
                        } else {
                            jsonArrayID.add(elementsSR[i]);
                        }
                    }
                    apiAutoUtilities.get().requestParams.add("serviceRequestTypeIDs", jsonArrayID);
                    break;
                case "correspondenceMetadataName":
                    String[] elementsCMD = dataTable.get("correspondenceMetadataName").split(",");
                    for (int i = 0; i < elementsCMD.length; i++) {
                        apitdu.get().jsonElement.getAsJsonObject().get("fieldMappings").getAsJsonArray().get(i).getAsJsonObject().addProperty("correspondenceMetadataName", elementsCMD[i]);
                    }
                    break;
                case "serviceRequestFieldName":
                    String[] elementsSRF = dataTable.get("serviceRequestFieldName").split(",");
                    for (int i = 0; i < elementsSRF.length; i++) {
                        apitdu.get().jsonElement.getAsJsonObject().get("fieldMappings").getAsJsonArray().get(i).getAsJsonObject().addProperty("serviceRequestFieldName", elementsSRF[i]);
                    }
                    break;
            }
        }
    }

    public APIClassUtil postAutoLinkRequest(String projectID) {
        this.projectId.set(projectID);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autolinkMapEndPoint);
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Project-Id", projectID);
        headers.put("Content-Type", "application/json");
        headers.put("domain", "la");
        headers.put("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal());
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostCustomHeaders(apiAutoUtilities.get().requestParams, headers);
    }

    public void saveAutoLinkMapID() {
        autolinkMapId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("autolinkMapId"));
    }

    public List<Map<String, Object>> getAutoLinkMappingsByProjectID(String projectId) {
        APIAutoUtilities api = new APIAutoUtilities();
        String baseUri = baseURI + autolinkMapEndPoint + "/";
        Response response = api.getAPI(baseUri, projectId);
        Assert.assertEquals(response.statusCode(), 200);
        List<Map<String, Object>> keyList = response.jsonPath().get("data");
        System.out.println(keyList);
        return keyList;
    }

    public Map<String, Object> returnAutolinkMappingByID(int mappingId, String projectId) {
        List<Map<String, Object>> autolinkMappingLists = getAutoLinkMappingsByProjectID(projectId);
        for (int i = 0; i < autolinkMappingLists.size(); i++) {
            if (Integer.parseInt((String) autolinkMappingLists.get(i).get("autoLinkMapId")) == mappingId) {
                return autolinkMappingLists.get(i);
            }
        }
        return null;
    }

    public Map<String, Object> returnCreatedAutolinkMappingByProjectId(String projectId) {
        List<Map<String, Object>> autolinkMappingLists = getAutoLinkMappingsByProjectID(projectId);
        for (int i = 0; i < autolinkMappingLists.size(); i++) {
            if (autolinkMappingLists.get(i).get("autoLinkMapId").equals(autolinkMapId.get())) {
                return autolinkMappingLists.get(i);
            }
        }
        return null;
    }

    public void verifyCreatedAutoLinkMappingDetails() {
        Map<String, Object> createdAutoLinkFromRetrieve = returnCreatedAutolinkMappingByProjectId(projectId.get());
        Assert.assertTrue(Integer.parseInt(createdAutoLinkFromRetrieve.get("autoLinkMapId").toString()) == autolinkMapId.get());
        createdAutoLinkFromRetrieve.remove("autoLinkMapId");
        Assert.assertEquals(createdAutoLinkFromRetrieve.toString().replace(" ", "").replace("=", ""), apiAutoUtilities.get().requestParams.toString().replace("\"", "").replace(" ", "").replace(":", ""));
    }

    public APIClassUtil deleteAutoLinkById(int autoLinkMapId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDefinitionService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(autolinkMapEndPoint + "/" + autoLinkMapId);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().deleteAPI();
    }

    public boolean searchForExactMatch(String projectId) {
        boolean foundInProject = false;
        List<Map<String, Object>> autoLinkMappingsList = getAutoLinkMappingsByProjectID(projectId);
        for (Map<String, Object> autoLinkMapping : autoLinkMappingsList
        ) {
            autoLinkMapping.remove("autoLinkMapId");
            if (autoLinkMapping.toString().replace(" ", "").replace("=", "").equals(apiAutoUtilities.get().requestParams.toString().replace("\"", "").replace(" ", "").replace(":", ""))) {
                foundInProject = true;
                System.out.println("Exact AutoLink Mapping Match found for " + projectId + " project.");
            }
        }
        return foundInProject;
    }
}
