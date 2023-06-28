
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
    "providerTypeId",
    "planProviderId",
    "providerTypeCd",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy"
})
public class ProviderType {

    @JsonProperty("providerTypeId")
    private Integer providerTypeId;
    @JsonProperty("planProviderId")
    private Integer planProviderId;
    @JsonProperty("providerTypeCd")
    private String providerTypeCd;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedOn")
    private String updatedOn;
    @JsonProperty("updatedBy")
    private String updatedBy;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("providerTypeId")
    public Integer getProviderTypeId() {
        return providerTypeId;
    }

    @JsonProperty("providerTypeId")
    public void setProviderTypeId(Integer providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    @JsonProperty("planProviderId")
    public Integer getPlanProviderId() {
        return planProviderId;
    }

    @JsonProperty("planProviderId")
    public void setPlanProviderId(Integer planProviderId) {
        this.planProviderId = planProviderId;
    }

    @JsonProperty("providerTypeCd")
    public String getProviderTypeCd() {
        return providerTypeCd;
    }

    @JsonProperty("providerTypeCd")
    public void setProviderTypeCd(String providerTypeCd) {
        this.providerTypeCd = providerTypeCd;
    }

    @JsonProperty("createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("createdOn")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("updatedOn")
    public String getUpdatedOn() {
        return updatedOn;
    }

    @JsonProperty("updatedOn")
    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @JsonProperty("updatedBy")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updatedBy")
    public void setUpdatedBy(String updatedBy) {
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
