
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
    "address",
    "fullAddress",
    "city",
    "state",
    "zipCode",
    "countyCode",
    "lattitude",
    "longitude",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy"
})
public class ProviderNormalizedAddress {

    @JsonProperty("address")
    private Object address;
    @JsonProperty("fullAddress")
    private Object fullAddress;
    @JsonProperty("city")
    private Object city;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("zipCode")
    private Object zipCode;
    @JsonProperty("countyCode")
    private Object countyCode;
    @JsonProperty("lattitude")
    private Object lattitude;
    @JsonProperty("longitude")
    private Object longitude;
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

    @JsonProperty("address")
    public Object getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Object address) {
        this.address = address;
    }

    @JsonProperty("fullAddress")
    public Object getFullAddress() {
        return fullAddress;
    }

    @JsonProperty("fullAddress")
    public void setFullAddress(Object fullAddress) {
        this.fullAddress = fullAddress;
    }

    @JsonProperty("city")
    public Object getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(Object city) {
        this.city = city;
    }

    @JsonProperty("state")
    public Object getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Object state) {
        this.state = state;
    }

    @JsonProperty("zipCode")
    public Object getZipCode() {
        return zipCode;
    }

    @JsonProperty("zipCode")
    public void setZipCode(Object zipCode) {
        this.zipCode = zipCode;
    }

    @JsonProperty("countyCode")
    public Object getCountyCode() {
        return countyCode;
    }

    @JsonProperty("countyCode")
    public void setCountyCode(Object countyCode) {
        this.countyCode = countyCode;
    }

    @JsonProperty("lattitude")
    public Object getLattitude() {
        return lattitude;
    }

    @JsonProperty("lattitude")
    public void setLattitude(Object lattitude) {
        this.lattitude = lattitude;
    }

    @JsonProperty("longitude")
    public Object getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
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
