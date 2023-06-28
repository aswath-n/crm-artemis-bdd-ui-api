
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
    "officeHoursId",
    "planProviderId",
    "dayOfWeek",
    "openFrom",
    "closeAt",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy"
})
public class ProviderOfficeHour {

    @JsonProperty("officeHoursId")
    private Integer officeHoursId;
    @JsonProperty("planProviderId")
    private Integer planProviderId;
    @JsonProperty("dayOfWeek")
    private String dayOfWeek;
    @JsonProperty("openFrom")
    private String openFrom;
    @JsonProperty("closeAt")
    private String closeAt;
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

    @JsonProperty("officeHoursId")
    public Integer getOfficeHoursId() {
        return officeHoursId;
    }

    @JsonProperty("officeHoursId")
    public void setOfficeHoursId(Integer officeHoursId) {
        this.officeHoursId = officeHoursId;
    }

    @JsonProperty("planProviderId")
    public Integer getPlanProviderId() {
        return planProviderId;
    }

    @JsonProperty("planProviderId")
    public void setPlanProviderId(Integer planProviderId) {
        this.planProviderId = planProviderId;
    }

    @JsonProperty("dayOfWeek")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("dayOfWeek")
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @JsonProperty("openFrom")
    public String getOpenFrom() {
        return openFrom;
    }

    @JsonProperty("openFrom")
    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    @JsonProperty("closeAt")
    public String getCloseAt() {
        return closeAt;
    }

    @JsonProperty("closeAt")
    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
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
