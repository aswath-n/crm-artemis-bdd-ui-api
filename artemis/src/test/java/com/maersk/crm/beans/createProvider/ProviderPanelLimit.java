
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
    "panelSize",
    "enrolledCount",
    "panelStatus",
    "holdCd",
    "panelTypeCd",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy"
})
public class ProviderPanelLimit {

    @JsonProperty("panelSize")
    private Object panelSize;
    @JsonProperty("enrolledCount")
    private Object enrolledCount;
    @JsonProperty("panelStatus")
    private String panelStatus;
    @JsonProperty("holdCd")
    private Object holdCd;
    @JsonProperty("panelTypeCd")
    private Object panelTypeCd;
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

    @JsonProperty("panelSize")
    public Object getPanelSize() {
        return panelSize;
    }

    @JsonProperty("panelSize")
    public void setPanelSize(Object panelSize) {
        this.panelSize = panelSize;
    }

    @JsonProperty("enrolledCount")
    public Object getEnrolledCount() {
        return enrolledCount;
    }

    @JsonProperty("enrolledCount")
    public void setEnrolledCount(Object enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    @JsonProperty("panelStatus")
    public String getPanelStatus() {
        return panelStatus;
    }

    @JsonProperty("panelStatus")
    public void setPanelStatus(String panelStatus) {
        this.panelStatus = panelStatus;
    }

    @JsonProperty("holdCd")
    public Object getHoldCd() {
        return holdCd;
    }

    @JsonProperty("holdCd")
    public void setHoldCd(Object holdCd) {
        this.holdCd = holdCd;
    }

    @JsonProperty("panelTypeCd")
    public Object getPanelTypeCd() {
        return panelTypeCd;
    }

    @JsonProperty("panelTypeCd")
    public void setPanelTypeCd(Object panelTypeCd) {
        this.panelTypeCd = panelTypeCd;
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
