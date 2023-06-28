
package com.maersk.crm.beans.searchProviderRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "firstName",
    "lastName",
    "groupName",
    "phoneNumber",
    "planName",
    "providerTypeCd",
    "middleName",
    "planCode",
    "specialityCd",
    "zipCode",
    "handicappedAccesibleInd",
    "city",
    "nameSuffix",
    "titleName",
    "npi",
    "stateProviderId",
    "genderCd",
    "sexLimitsCds",
    "acceptObInd",
    "ageLowLimit",
    "ageHighLimit",
    "languageTypeCd",
    "addressLine1",
    "countyCd",
    "state",
    "faxNumber",
    "openFrom",
    "closeAt"
})
public class ProviderSearch {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("planName")
    private String planName;
    @JsonProperty("providerTypeCd")
    private String providerTypeCd;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("planCode")
    private Object planCode;
    @JsonProperty("specialityCd")
    private String specialityCd;
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("handicappedAccesibleInd")
    private Boolean handicappedAccesibleInd;
    @JsonProperty("city")
    private Object city;
    @JsonProperty("nameSuffix")
    private String nameSuffix;
    @JsonProperty("titleName")
    private String titleName;
    @JsonProperty("npi")
    private Object npi;
    @JsonProperty("stateProviderId")
    private String stateProviderId;
    @JsonProperty("genderCd")
    private String genderCd;
    @JsonProperty("sexLimitsCds")
    private List<String> sexLimitsCds = null;
    @JsonProperty("acceptObInd")
    private Boolean acceptObInd;
    @JsonProperty("ageLowLimit")
    private String ageLowLimit;
    @JsonProperty("ageHighLimit")
    private String ageHighLimit;
    @JsonProperty("languageTypeCd")
    private String languageTypeCd;
    @JsonProperty("addressLine1")
    private Object addressLine1;
    @JsonProperty("countyCd")
    private Object countyCd;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("faxNumber")
    private String faxNumber;
    @JsonProperty("openFrom")
    private String openFrom;
    @JsonProperty("closeAt")
    private String closeAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("planName")
    public String getPlanName() {
        return planName;
    }

    @JsonProperty("planName")
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @JsonProperty("providerTypeCd")
    public String getProviderTypeCd() {
        return providerTypeCd;
    }

    @JsonProperty("providerTypeCd")
    public void setProviderTypeCd(String providerTypeCd) {
        this.providerTypeCd = providerTypeCd;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }

    @JsonProperty("middleName")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonProperty("planCode")
    public Object getPlanCode() {
        return planCode;
    }

    @JsonProperty("planCode")
    public void setPlanCode(Object planCode) {
        this.planCode = planCode;
    }

    @JsonProperty("specialityCd")
    public String getSpecialityCd() {
        return specialityCd;
    }

    @JsonProperty("specialityCd")
    public void setSpecialityCd(String specialityCd) {
        this.specialityCd = specialityCd;
    }

    @JsonProperty("zipCode")
    public String getZipCode() {
        return zipCode;
    }

    @JsonProperty("zipCode")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @JsonProperty("handicappedAccesibleInd")
    public Boolean getHandicappedAccesibleInd() {
        return handicappedAccesibleInd;
    }

    @JsonProperty("handicappedAccesibleInd")
    public void setHandicappedAccesibleInd(Boolean handicappedAccesibleInd) {
        this.handicappedAccesibleInd = handicappedAccesibleInd;
    }

    @JsonProperty("city")
    public Object getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(Object city) {
        this.city = city;
    }

    @JsonProperty("nameSuffix")
    public String getNameSuffix() {
        return nameSuffix;
    }

    @JsonProperty("nameSuffix")
    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    @JsonProperty("titleName")
    public String getTitleName() {
        return titleName;
    }

    @JsonProperty("titleName")
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @JsonProperty("npi")
    public Object getNpi() {
        return npi;
    }

    @JsonProperty("npi")
    public void setNpi(Object npi) {
        this.npi = npi;
    }

    @JsonProperty("stateProviderId")
    public String getStateProviderId() {
        return stateProviderId;
    }

    @JsonProperty("stateProviderId")
    public void setStateProviderId(String stateProviderId) {
        this.stateProviderId = stateProviderId;
    }

    @JsonProperty("genderCd")
    public String getGenderCd() {
        return genderCd;
    }

    @JsonProperty("genderCd")
    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    @JsonProperty("sexLimitsCds")
    public List<String> getSexLimitsCds() {
        return sexLimitsCds;
    }

    @JsonProperty("sexLimitsCds")
    public void setSexLimitsCds(List<String> sexLimitsCds) {
        this.sexLimitsCds = sexLimitsCds;
    }

    @JsonProperty("acceptObInd")
    public Boolean getAcceptObInd() {
        return acceptObInd;
    }

    @JsonProperty("acceptObInd")
    public void setAcceptObInd(Boolean acceptObInd) {
        this.acceptObInd = acceptObInd;
    }

    @JsonProperty("ageLowLimit")
    public String getAgeLowLimit() {
        return ageLowLimit;
    }

    @JsonProperty("ageLowLimit")
    public void setAgeLowLimit(String ageLowLimit) {
        this.ageLowLimit = ageLowLimit;
    }

    @JsonProperty("ageHighLimit")
    public String getAgeHighLimit() {
        return ageHighLimit;
    }

    @JsonProperty("ageHighLimit")
    public void setAgeHighLimit(String ageHighLimit) {
        this.ageHighLimit = ageHighLimit;
    }

    @JsonProperty("languageTypeCd")
    public String getLanguageTypeCd() {
        return languageTypeCd;
    }

    @JsonProperty("languageTypeCd")
    public void setLanguageTypeCd(String languageTypeCd) {
        this.languageTypeCd = languageTypeCd;
    }

    @JsonProperty("addressLine1")
    public Object getAddressLine1() {
        return addressLine1;
    }

    @JsonProperty("addressLine1")
    public void setAddressLine1(Object addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @JsonProperty("countyCd")
    public Object getCountyCd() {
        return countyCd;
    }

    @JsonProperty("countyCd")
    public void setCountyCd(Object countyCd) {
        this.countyCd = countyCd;
    }

    @JsonProperty("state")
    public Object getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Object state) {
        this.state = state;
    }

    @JsonProperty("faxNumber")
    public String getFaxNumber() {
        return faxNumber;
    }

    @JsonProperty("faxNumber")
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
