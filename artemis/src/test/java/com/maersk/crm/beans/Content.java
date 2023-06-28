package com.maersk.crm.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventId",
        "masterEventId",
        "eventName",
        "module",
        "payload",
        "status",
        "errorStack",
        "correlationId",
        "uiid",
        "message",
        "createdOn",
        "updatedOn",
        "createdBy",
        "updatedBy"
})
public class Content {

    @JsonProperty("eventId")
    private Integer eventId;
    @JsonProperty("masterEventId")
    private Integer masterEventId;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("module")
    private String module;
    @JsonProperty("payload")
    private String payload;
    @JsonProperty("status")
    private String status;
    @JsonProperty("errorStack")
    private Object errorStack;
    @JsonProperty("correlationId")
    private String correlationId;
    @JsonProperty("uiid")
    private String uiid;
    @JsonProperty("message")
    private Object message;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("updatedOn")
    private String updatedOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private Object updatedBy;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("eventId")
    public Integer getEventId() {
        return eventId;
    }

    @JsonProperty("eventId")
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @JsonProperty("masterEventId")
    public Integer getMasterEventId() {
        return masterEventId;
    }

    @JsonProperty("masterEventId")
    public void setMasterEventId(Integer masterEventId) {
        this.masterEventId = masterEventId;
    }

    @JsonProperty("eventName")
    public String getEventName() {
        return eventName;
    }

    @JsonProperty("eventName")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @JsonProperty("module")
    public String getModule() {
        return module;
    }

    @JsonProperty("module")
    public void setModule(String module) {
        this.module = module;
    }

    @JsonProperty("payload")
    public String getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("errorStack")
    public Object getErrorStack() {
        return errorStack;
    }

    @JsonProperty("errorStack")
    public void setErrorStack(Object errorStack) {
        this.errorStack = errorStack;
    }

    @JsonProperty("correlationId")
    public String getCorrelationId() {
        return correlationId;
    }

    @JsonProperty("correlationId")
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @JsonProperty("uiid")
    public String getUiid() {
        return uiid;
    }

    @JsonProperty("uiid")
    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    @JsonProperty("message")
    public Object getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Object message) {
        this.message = message;
    }

    @JsonProperty("createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("createdOn")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("updatedOn")
    public String getUpdatedOn() {
        return updatedOn;
    }

    @JsonProperty("updatedOn")
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("updatedBy")
    public Object getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updatedBy")
    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
