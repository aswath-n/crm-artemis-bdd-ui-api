package com.maersk.dms.step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.crm.step_definitions.Hooks;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DPBIEventsStepDefs extends CRMUtilities implements ApiBase {
    private final ThreadLocal<APIClassUtil> apiClassUtil = ThreadLocal.withInitial(APIClassUtil::getApiClassUtilThreadLocal);
    private final ThreadLocal<ApiTestDataUtil> apiTestDataUtil = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    private final ThreadLocal<Response> correspondenceSettings = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> settingsParsed = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> settingsRequest = new ThreadLocal<>();
    private final ThreadLocal<Response> events = new ThreadLocal<>();
    private final ThreadLocal<String> eventId = ThreadLocal.withInitial(String::new);

    /**
     * Get correspondence settings
     */
    public void getCorrespondenceSettings() {
        apiClassUtil.get().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition") + "/correspondencesettings/");
        apiClassUtil.get().setEndPoint(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().parseProjectNumber(ConfigurationReader.getProperty("projectName"))));
        synchronized (correspondenceSettings){
            correspondenceSettings.set((Response) apiClassUtil.get().getAPI().responseBody);
        }
        Assert.assertEquals(200, correspondenceSettings.get().statusCode());
    }

    public void changeSettings(String jsonPath, String property, Object value) {
        Map<String, String> map = correspondenceSettings.get().body().jsonPath().get("correspondenceSettingsResponse");
        JsonObject temp = new JsonObject();
        for (Map.Entry<String, String> key : map.entrySet()) {
//            if (key.getValue() instanceof Integer) temp.addProperty(key.getKey(), (Integer) key.getValue());
            if (key.getValue() instanceof String) temp.addProperty(key.getKey(), (String) key.getValue());
//            else if (key.getValue() instanceof Boolean) temp.addProperty(key.getKey(), (Boolean) key.getValue());
        }
        synchronized (settingsParsed){
            settingsParsed.set(temp);
        }
        if (value instanceof String)
            settingsParsed.get().getAsJsonObject(jsonPath).getAsJsonPrimitive(property).getAsJsonObject().addProperty(property, (String) value);
        else if (value instanceof Integer)
            settingsParsed.get().getAsJsonObject(jsonPath).addProperty(property, (Integer) value);
        else if (value instanceof Boolean)
            settingsParsed.get().getAsJsonObject(jsonPath).addProperty(property, (Boolean) value);
        synchronized (settingsParsed){
            settingsParsed.set(settingsParsed.get());
        }
    }

    public void updateCorrespondenceSettings() {
        apiClassUtil.set(APIClassUtil.getApiClassUtilThreadLocal(true));
        apiTestDataUtil.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
        apiClassUtil.get().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition"));
        apiClassUtil.get().setEndPoint("/correspondencesettings");
        synchronized (World.getWorld().response){
            World.getWorld().response.set((Response)apiClassUtil.get().PostAPIWithParameter(settingsRequest.get()).responseBody);
        }
        Assert.assertEquals(200, World.getWorld().response.get().statusCode());
    }

    /**
     * searches events.get() and stores response to "events." variable in this
     *
     * @param eventName - pass event name to search events.get() api
     */
    public void searchEvents(String key, String eventName) {
        apiClassUtil.set(APIClassUtil.getApiClassUtilThreadLocal(true));
        apiTestDataUtil.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
        apiClassUtil.get().setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
        apiClassUtil.get().setEndPoint("/app/crm/events.");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
//        jsonObject.addProperty("createdOn", World.save.get().get("createdOn"));
        jsonObject.addProperty(key, World.save.get().get(key));
        synchronized (events){
            events.set((Response) apiClassUtil.get().PostAPIWithParameter(jsonObject).responseBody);
        }
        Assert.assertEquals(200, events.get().statusCode());

    }

    public void searchEventByEventName( String eventName) {
        apiClassUtil.get().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        apiClassUtil.get().setEndPoint("/app/crm/events.get()?size=100&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        apiClassUtil.get().PostAPIWithParameter(jsonObject) ;
        World.generalSave.get().put("response" ,apiClassUtil.get().jsonPathEvaluator);
        Assert.assertEquals(200, apiClassUtil.get().statusCode);
    }

    public void verifyEvent(String event, String data) {
        List<String> list = events.get().body().jsonPath().getList("eventsList.content.payload");
        boolean found = false;
        List<String> delete = new ArrayList<>();
        for (String load : list) {
            if (load.contains(World.save.get().get(data))) {
                delete.add(load);
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    public void verifyEventNOTPRODUCED(JsonPath eventbyTraceId, List<String> eventNames) {
        List<Map<String, Object>> events = eventbyTraceId.getList("events");
        int index = 0;
        boolean eventFound = false;
        for (index = 0; index < events.size(); index++) {
            for (String eventName : eventNames) {
                if(eventName.equalsIgnoreCase(String.valueOf(events.get(index).get("eventName")))){
                    eventFound=true;
                }
            }
        }
        Assert.assertFalse(eventFound, eventbyTraceId.prettyPrint());
    }
}
