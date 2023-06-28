
package com.maersk.crm.beans.searchProviderResponse;

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
    "validationList",
    "errorResponse",
    "status",
    "providerSearchResponse",
    "totalRecords"
})
public class ProviderSearchResponse {

    @JsonProperty("validationList")
    private Object validationList;
    @JsonProperty("errorResponse")
    private Object errorResponse;
    @JsonProperty("status")
    private String status;
    @JsonProperty("providerSearchResponse")
    private ProviderSearchResponse_ providerSearchResponse;
    @JsonProperty("totalRecords")
    private Integer totalRecords;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("providerSearchResponse")
    public ProviderSearchResponse_ getProviderSearchResponse() {
        return providerSearchResponse;
    }

    @JsonProperty("providerSearchResponse")
    public void setProviderSearchResponse(ProviderSearchResponse_ providerSearchResponse) {
        this.providerSearchResponse = providerSearchResponse;
    }

    @JsonProperty("totalRecords")
    public Integer getTotalRecords() {
        return totalRecords;
    }

    @JsonProperty("totalRecords")
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
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
