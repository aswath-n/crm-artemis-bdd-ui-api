package com.maersk.crm.utilities.data_Module.crm_core_data_module;


import io.cucumber.datatable.DataTable;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskFields {


    public TaskFields() {
    }

    private int taskFieldId;
    public static Object fieldGroupName = JSONObject.NULL;
    public static Map<String, Integer> taskFieldNameParams = new HashMap<>();

    //Data Table Column Names
    public static final String TASK_FIELD_NAMES = "Task_Field_Names";
    public static final String TASK_FIELD_VALUES = "Task_Field_Values";

    public static final String VACMS_CASE_ID = "External Case ID";
    public static final String MMIS_MEMBER_ID = "External Consumer ID";
    public static final String APPLICATION_ID = "External Application ID";
    public static final String CONNECTIONPOINT_CONTACT_RECORD_ID = "ConnectionPoint Contact Record ID";
    public static final String CONNECTIONPOINT_SR_ID = "ConnectionPoint SR ID";
    public static final String CONNECTIONPOINT_TASK_ID = "ConnectionPoint Task ID";
    public static final String CONTACT_REASON = "Contact Reason";
    public static final String DUE_DATE = "Document Due Date";
    public static final String ACTION_TAKEN_SINGLE = "Action Taken Single";
    public static final String ACTION_TAKEN_MULTI = "Action Taken";
    public static final String RECEIVED_DATE = "Received Date";
    public static final String REMAND_REASON = "Remand Reason";
    public static final String REMAND_DUE_DATE = "Remand Due Date";
    public static final String REMAND_COMPLETION_DATE = "Remand Completion Date";
    public static final String ELIGIBILITY_DECISION = "Eligibility Decision";
    public static final String DISPOSITION = "Disposition";
    public static final String TASK_INFO = "taskInfo"; //This field doesn't have taskFiledId
    public static final String TASK_NOTES = "taskNotes"; //This field doesn't have taskFiledId
    public static final String FACILITY_NAME = "Facility Name";
    public static final String FACILITY_TYPE = "Facility Type";
    public static final String CASE_WORKER_FIRST_NAME = "Case Worker First Name";
    public static final String CASE_WORKER_LAST_NAME = "Case Worker Last Name";
    public static final String EXTERNAL_CASE_ID = "External Case ID";
    public static final String CHANNEL = "Channel";
    public static final String PREFERRED_PHONE = "Preferred Phone";

    public int getTaskFieldId() {
        return taskFieldId;
    }

    public void setTaskFieldId(int taskFieldId) {
        this.taskFieldId = taskFieldId;
    }

    public static void initiateTaskFieldParams() {
        setParams(VACMS_CASE_ID, 91);
        setParams(MMIS_MEMBER_ID, 111);
        setParams(APPLICATION_ID, 55);
        setParams(CONNECTIONPOINT_CONTACT_RECORD_ID, 149);
        setParams(CONNECTIONPOINT_SR_ID, 150);
        setParams(CONNECTIONPOINT_TASK_ID, 151);
        setParams(CONTACT_REASON, 47);
        setParams(DUE_DATE, 109);
        setParams(ACTION_TAKEN_SINGLE, 144);
        setParams(ACTION_TAKEN_MULTI, 42);
        setParams(RECEIVED_DATE, 132);
        setParams(REMAND_REASON, 133);
        setParams(REMAND_DUE_DATE, 134);
        setParams(REMAND_COMPLETION_DATE, 135);
        setParams(ELIGIBILITY_DECISION, 136);
        setParams(DISPOSITION, 71);
        setParams(FACILITY_NAME, 112);
        setParams(FACILITY_TYPE, 113);
        setParams(CASE_WORKER_FIRST_NAME, 3);
        setParams(CASE_WORKER_LAST_NAME, 4);
        setParams(EXTERNAL_CASE_ID, 91);
    }

    private static void setParams(String fieldName, int taskFieldId) {
        taskFieldNameParams.put(fieldName, taskFieldId);

    }

    public static Object getFieldGroupName(String fieldName) {
        switch (fieldName) {
            case CASE_WORKER_FIRST_NAME:
            case CASE_WORKER_LAST_NAME:
                return "CASE_WORKER";
            default:
                return JSONObject.NULL;
        }
    }


    public static Map<String, String> convertFieldNamesToVariableAndMapWithValues(DataTable fieldNamesAndValues) {
        //Cucumber can't covert String to Variable. That's why need to below conversion to be able to use the internal values.
        Map<String, String> params = new HashMap<>();
        for (Map<String, String> data : fieldNamesAndValues.asMaps(String.class, String.class)) {
            String taskFieldName = data.get(TASK_FIELD_NAMES).toString();
            switch (taskFieldName) {
                case "VACMS_CASE_ID":
                    params.put(VACMS_CASE_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "MMIS_MEMBER_ID":
                    params.put(MMIS_MEMBER_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "APPLICATION_ID":
                    params.put(APPLICATION_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CONNECTIONPOINT_CONTACT_RECORD_ID":
                    params.put(CONNECTIONPOINT_CONTACT_RECORD_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CONNECTIONPOINT_SR_ID":
                    params.put(CONNECTIONPOINT_SR_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CONNECTIONPOINT_TASK_ID":
                    params.put(CONNECTIONPOINT_TASK_ID, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CONTACT_REASON":
                    params.put(CONTACT_REASON, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "DUE_DATE":
                    params.put(DUE_DATE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "ACTION_TAKEN_SINGLE":
                    params.put(ACTION_TAKEN_SINGLE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "ACTION_TAKEN_MULTI":
                    params.put(ACTION_TAKEN_MULTI, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "RECEIVED_DATE":
                    params.put(RECEIVED_DATE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "REMAND_REASON":
                    params.put(REMAND_REASON, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "REMAND_DUE_DATE":
                    params.put(REMAND_DUE_DATE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "REMAND_COMPLETION_DATE":
                    params.put(REMAND_COMPLETION_DATE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "ELIGIBILITY_DECISION":
                    params.put(ELIGIBILITY_DECISION, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "DISPOSITION":
                    params.put(DISPOSITION, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "TASK_INFO":
                    params.put(TASK_INFO, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "FACILITY_NAME":
                    params.put(FACILITY_NAME, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "FACILITY_TYPE":
                    params.put(FACILITY_TYPE, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CASE_WORKER_FIRST_NAME":
                    params.put(CASE_WORKER_FIRST_NAME, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CASE_WORKER_LAST_NAME":
                    params.put(CASE_WORKER_LAST_NAME, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "CHANNEL":
                    params.put(CHANNEL, data.get(TASK_FIELD_VALUES).toString());
                    break;
                case "PREFERRED_PHONE":
                    params.put(PREFERRED_PHONE, data.get(TASK_FIELD_VALUES).toString());
                    break;
//                case "":
//                    params.put(, data.get(TASK_FIELD_VALUES).toString());
//                    break;
            }
        }

        return params;
    }


}
