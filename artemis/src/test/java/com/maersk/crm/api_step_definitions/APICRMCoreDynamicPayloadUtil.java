package com.maersk.crm.api_step_definitions;


import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class APICRMCoreDynamicPayloadUtil extends CRMUtilities implements ApiBase {

    public static final String USER_ID = "userId";
    public static final String ACTION_ID = "actionId";
    public static final String REFERENCE_OBJECT_ID = "referenceobjectId";
    public static final String SESSION_TIME = "sessionTime";
    public static final String APPLICATION_TYPE = "applicationType";

    public static JSONObject getUserActivityPayload(int actionId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime now = LocalDateTime.now();

        return new JSONObject()
                .put(USER_ID, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId)
                .put(ACTION_ID, actionId)
                .put(REFERENCE_OBJECT_ID, 1)
                .put(SESSION_TIME, dtf.format(now))
                .put(APPLICATION_TYPE, "ConnectionPoint");
    }




}
