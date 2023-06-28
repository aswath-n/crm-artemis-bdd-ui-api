
package com.maersk.crm.beans.createProvider;

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
    "specialityCd",
    "primarySpecialityInd",
    "effectiveStartDate",
    "effectiveEndDate",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy"
})
public class ProviderSpeciality {

    @JsonProperty("specialityCd")
    private String specialityCd;
    @JsonProperty("primarySpecialityInd")
    private Object primarySpecialityInd;
    @JsonProperty("effectiveStartDate")
    private Object effectiveStartDate;
    @JsonProperty("effectiveEndDate")
    private Object effectiveEndDate;
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

    @JsonProperty("specialityCd")
    public String getSpecialityCd() {
        return specialityCd;
    }

    @JsonProperty("specialityCd")
    public void setSpecialityCd(String specialityCd) {
        this.specialityCd = specialityCd;
    }

    @JsonProperty("primarySpecialityInd")
    public Object getPrimarySpecialityInd() {
        return primarySpecialityInd;
    }

    @JsonProperty("primarySpecialityInd")
    public void setPrimarySpecialityInd(Object primarySpecialityInd) {
        this.primarySpecialityInd = primarySpecialityInd;
    }

    @JsonProperty("effectiveStartDate")
    public Object getEffectiveStartDate() {
        return effectiveStartDate;
    }

    @JsonProperty("effectiveStartDate")
    public void setEffectiveStartDate(Object effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    @JsonProperty("effectiveEndDate")
    public Object getEffectiveEndDate() {
        return effectiveEndDate;
    }

    @JsonProperty("effectiveEndDate")
    public void setEffectiveEndDate(Object effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
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
