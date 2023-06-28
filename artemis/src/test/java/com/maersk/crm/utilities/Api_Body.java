package com.maersk.crm.utilities;

import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.commons.lang3.StringUtils;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import io.cucumber.datatable.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;
import static com.maersk.crm.api_step_definitions.APITaskManagementController.*;
import static com.maersk.crm.utilities.data_Module.crm_core_data_module.TaskFields.*;

import java.util.UUID;


/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/19/2020
 */
public class Api_Body {
    private static Api_Body instance;
    private String body;
    private String name;

    public Api_Body() {
        instance = this;
    }

    public static Api_Body getInstance() {
        return instance == null ? new Api_Body() : instance;
    }

    Api_Body(String name, String body) {
        this.name = StringUtils.isEmpty(name)? UUID.randomUUID().toString() : name;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public <T> T getBodyAttribute(String key) {
        T value = JsUtill.getJsonValue(getBody(), key);
        return value instanceof NullNode ? null : value;
    }


    public <T> T getBodyAttribute(String key, Class<T> aClass) {
        return JsUtill.getJsonValue(getBody(), key, aClass);
    }


    public static Map<String, String> params;

    public static JSONObject createMainBody(String taskTypeId, int priority, int dueInDays, String createdBy, Map<String, String> params, boolean assignIt) {
        APITaskManagementController.taskTypeId.set(taskTypeId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime now = LocalDateTime.now();
        String taskInfo = null;
        String taskNotes = null;
        String tempDate = (String) ApiTestDataUtil.calculateDateTime("+1h", 5);
        String X = tempDate.substring(0, 10);
        LocalDate localDate = addDaysSkippingWeekends(LocalDate.parse(X), dueInDays);
        String dueDate = localDate + "T23:59:05.755Z";
        for (Map.Entry<String, String> taskFields : params.entrySet()) {
            String key = taskFields.getKey();
            if (key.equalsIgnoreCase(TASK_INFO)) {
                taskInfo = taskFields.getValue();
            } else if (key.equalsIgnoreCase(TASK_NOTES)) {
                taskNotes = taskFields.getValue();
            }
        }
        return new JSONObject()
                .put("action", JSONObject.NULL)
                .put("actionTaken", JSONObject.NULL)
                .put("actionedOn", JSONObject.NULL)
                .put("cancelReason", JSONObject.NULL)
                .put("correlationId", JSONObject.NULL)
                .put("createdBy", createdBy)
                .put("createdOn", dtf.format(now))
                .put("defaultDueDate", dueDate)
                .put("defaultPriority", priority)
                .put("dueIn", JSONObject.NULL)
                .put("dueInDays", dueInDays)
                .put("editReason", JSONObject.NULL)
                .put("editReasons", new JSONArray())
                .put("escalatedFlag", false)
                .put("holdReason", JSONObject.NULL)
                .put("overrideDueDate", JSONObject.NULL)
                .put("overridePerformedBy", JSONObject.NULL)
                .put("overridePerformedDate", JSONObject.NULL)
                .put("overridePriority", JSONObject.NULL)
                .put("source", "User")
                .put("staffAssignedTo", assignIt ? createdBy : JSONObject.NULL) // Make use of params
                .put("staffForwardBy", JSONObject.NULL)
                .put("staffWorkedBy", JSONObject.NULL)
                .put("statusDate", dtf.format(now))
                .put("taskDisposition", JSONObject.NULL)
                .put("taskId", JSONObject.NULL)
                .put("taskInfo", taskInfo != null ? taskInfo : JSONObject.NULL)
                .put("taskNotes", taskNotes != null ? taskNotes : JSONObject.NULL)
                .put("taskStatus", "Created")
                .put("taskTypeId", taskTypeId)
                .put("uiid", JSONObject.NULL)
                .put("updatedOn", dtf.format(now))
                .put("updatedBy", createdBy);
    }

    public static JSONArray buildTaskDetails(Map<String, String> params) {
        String dateRegex = "\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        JSONArray finalTaskDetails = new JSONArray();
        Integer mappedTaskFieldId = 0;
        initiateTaskFieldParams();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime now = LocalDateTime.now();
        JSONObject temp;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String expectedTaskFieldName = entry.getKey();
            String expectedTaskFieldValue = entry.getValue();
            for (Map.Entry<String, Integer> mappedTaskFields : taskFieldNameParams.entrySet()) {
                if (mappedTaskFields.getKey().equalsIgnoreCase(expectedTaskFieldName)) {
                    mappedTaskFieldId = mappedTaskFields.getValue();
                    break;
                }
            }
            boolean isValueTypeBoolean = checkInstanceOfValue(expectedTaskFieldValue, false, true);
            boolean isValueTypeInteger = checkInstanceOfValue(expectedTaskFieldValue, true, false);
            boolean isValueDate = false;
            if (expectedTaskFieldValue.matches(dateRegex)) {
                isValueDate = isDateValid(expectedTaskFieldValue);
            }
            if (!expectedTaskFieldName.equalsIgnoreCase(TASK_NOTES) && !expectedTaskFieldName.equalsIgnoreCase(TASK_INFO)) {
                temp = new JSONObject()
                        .put("uiid", JSONObject.NULL)
                        .put("correlationId", JSONObject.NULL)
                        .put("createdBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId)
                        .put("createdOn", dtf.format(now))
                        .put("fieldGroup", getFieldGroupName(expectedTaskFieldName))
                        .put("selectionBoolean", isValueTypeBoolean ? expectedTaskFieldValue : JSONObject.NULL)
                        .put("selectionDate", isValueDate ? expectedTaskFieldValue : JSONObject.NULL)
                        .put("selectionDateTime", JSONObject.NULL)
                        .put("selectionFieldName", expectedTaskFieldName)
                        .put("selectionNumeric", isValueTypeInteger ? expectedTaskFieldValue : JSONObject.NULL)
                        .put("selectionVarchar", !isValueTypeBoolean && !isValueTypeInteger && !isValueDate ? expectedTaskFieldValue : JSONObject.NULL)
                        .put("selectionVarchar2", JSONObject.NULL)
                        .put("taskDetailsId", JSONObject.NULL)
                        .put("taskFieldId", mappedTaskFieldId)
                        .put("taskId", JSONObject.NULL)
                        .put("updatedBy", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId)
                        .put("updatedOn", dtf.format(now));
                finalTaskDetails.put(temp);
            }
        }
        return finalTaskDetails;
    }




//    public static String removeUnderscoreAndDecapitalize(String value) {
//        int pos = 0;
//        StringBuilder sb = new StringBuilder(value);
//        while (pos < sb.length()) {
//            char c = sb.charAt(pos);
//            sb.setCharAt(pos, Character.toUpperCase(c));
//            pos++;
//            for (; pos < sb.length() && value.charAt(pos) != '.' && value.charAt(pos) != ' '; pos++)
//                sb.setCharAt(pos, Character.toLowerCase(sb.charAt(pos)));
//            for (; pos < sb.length() && value.charAt(pos) == ' '; pos++) ;
//        }
//
//        return sb.toString();
//    }


    public static String addUnderscoreAndCapitalize(String value) {
        String[] splitValues = value.split(" ");
        StringBuilder finalValue = new StringBuilder();
        for (int i = 0; i < splitValues.length - 1; i++) {
            finalValue.append(splitValues[i].toUpperCase().concat("_"));
        }
        return finalValue.append(splitValues[splitValues.length - 1].toUpperCase()).toString();
    }


    public static boolean checkInstanceOfValue(String value, boolean checkInteger, boolean checkBoolean) {
        if (value != null) {
            if (checkInteger) {
                String exceptionMessage = "";
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    exceptionMessage = e.toString();
                }
                if (exceptionMessage.isEmpty()) {
                    return true;
                }
            } else if (checkBoolean) {
                return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
            }
        }
        return false;
    }


    public static boolean isDateValid(String date) {
        boolean valid = false;
        if (date != null) {
            try {
                // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
                LocalDate.parse(date,
                        DateTimeFormatter.ofPattern("uuuu-M-d")
                                .withResolverStyle(ResolverStyle.STRICT)
                );
                valid = true;
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                valid = false;
            }
        }
        return valid;
    }

    public static JSONObject createRequestBodyForSpecificTask(String createdBy, Map<String, String> params, boolean assignIt) {
        if (taskTypeId.get() != null && taskPriority != null && dueInDays.get() != null) {
            return createMainBody(
                    taskTypeId.get(),
                    Integer.parseInt(taskPriority.get()),
                    Integer.parseInt(dueInDays.get()),
                    createdBy, params, assignIt)
                    .put("taskDetails", buildTaskDetails(params));
        } else {
            return new JSONObject();
        }
    }
}
