package com.maersk.crm.beans.eventResponse;

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
        "events",
        "eventsList",
        "validationList",
        "errorResponse",
        "status"
})
public class Example {

    @JsonProperty("events")
    private Object events;
    @JsonProperty("eventsList")
    private EventsList eventsList;
    @JsonProperty("validationList")
    private Object validationList;
    @JsonProperty("errorResponse")
    private Object errorResponse;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("events")
    public Object getEvents() {
        return events;
    }

    @JsonProperty("events")
    public void setEvents(Object events) {
        this.events = events;
    }

    @JsonProperty("eventsList")
    public EventsList getEventsList() {
        return eventsList;
    }

    @JsonProperty("eventsList")
    public void setEventsList(EventsList eventsList) {
        this.eventsList = eventsList;
    }

    @JsonProperty("validationList")
    public Object getValidationList() {
        return validationList;
    }

    @JsonProperty("validationList")
    public void setValidationList(Object validationList) {
        this.validationList = validationList;
    }

    @JsonProperty("errorResponse")
    public Object getErrorResponse() {
        return errorResponse;
    }

    @JsonProperty("errorResponse")
    public void setErrorResponse(Object errorResponse) {
        this.errorResponse = errorResponse;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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
