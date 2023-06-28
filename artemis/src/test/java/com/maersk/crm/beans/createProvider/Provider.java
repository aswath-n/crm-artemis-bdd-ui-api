
package com.maersk.crm.beans.createProvider;

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
    "planCode",
    "planName",
    "mcoId",
    "programTypeCd",
    "effectiveStartDate",
    "effectiveEndDate",
    "npi",
    "stateProviderId",
    "pcpFlag",
    "genderCd",
    "sexLimitsCd",
    "ageLowLimit",
    "ageHighLimit",
    "classificationCd",
    "groupFlag",
    "groupName",
    "firstName",
    "lastName",
    "nameSuffix",
    "titleName",
    "ssn",
    "fein",
    "emailAddress",
    "middleName",
    "acceptObInd",
    "acceptNewPatientsInd",
    "handicappedAccesibleInd",
    "reasonForChange",
    "createdOn",
    "createdBy",
    "updatedOn",
    "updatedBy",
    "providerLanguages",
    "providerSpeciality",
    "providerType",
    "providerHospitalAffilation",
    "providerOfficeHours",
    "providerNormalizedAddress",
    "providerPanelLimit",
    "providerAddress"
})
public class Provider {

    @JsonProperty("planCode")
    private String planCode;
    @JsonProperty("planName")
    private String planName;
    @JsonProperty("mcoId")
    private String mcoId;
    @JsonProperty("programTypeCd")
    private String programTypeCd;
    @JsonProperty("effectiveStartDate")
    private Object effectiveStartDate;
    @JsonProperty("effectiveEndDate")
    private Object effectiveEndDate;
    @JsonProperty("npi")
    private String npi;
    @JsonProperty("stateProviderId")
    private String stateProviderId;
    @JsonProperty("pcpFlag")
    private String pcpFlag;
    @JsonProperty("genderCd")
    private String genderCd;
    @JsonProperty("sexLimitsCd")
    private String sexLimitsCd;
    @JsonProperty("ageLowLimit")
    private String ageLowLimit;
    @JsonProperty("ageHighLimit")
    private String ageHighLimit;
    @JsonProperty("classificationCd")
    private Object classificationCd;
    @JsonProperty("groupFlag")
    private String groupFlag;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("nameSuffix")
    private Object nameSuffix;
    @JsonProperty("titleName")
    private Object titleName;
    @JsonProperty("ssn")
    private String ssn;
    @JsonProperty("fein")
    private String fein;
    @JsonProperty("emailAddress")
    private Object emailAddress;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("acceptObInd")
    private Boolean acceptObInd;
    @JsonProperty("acceptNewPatientsInd")
    private Boolean acceptNewPatientsInd;
    @JsonProperty("handicappedAccesibleInd")
    private Boolean handicappedAccesibleInd;
    @JsonProperty("reasonForChange")
    private Object reasonForChange;
    @JsonProperty("createdOn")
    private String createdOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedOn")
    private String updatedOn;
    @JsonProperty("updatedBy")
    private Object updatedBy;
    @JsonProperty("providerLanguages")
    private List<ProviderLanguage> providerLanguages = null;
    @JsonProperty("providerSpeciality")
    private List<ProviderSpeciality> providerSpeciality = null;
    @JsonProperty("providerType")
    private List<ProviderType> providerType = null;
    @JsonProperty("providerHospitalAffilation")
    private List<ProviderHospitalAffilation> providerHospitalAffilation = null;
    @JsonProperty("providerOfficeHours")
    private List<ProviderOfficeHour> providerOfficeHours = null;
    @JsonProperty("providerNormalizedAddress")
    private List<ProviderNormalizedAddress> providerNormalizedAddress = null;
    @JsonProperty("providerPanelLimit")
    private List<ProviderPanelLimit> providerPanelLimit = null;
    @JsonProperty("providerAddress")
    private List<ProviderAddress> providerAddress = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("planCode")
    public String getPlanCode() {
        return planCode;
    }

    @JsonProperty("planCode")
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    @JsonProperty("planName")
    public String getPlanName() {
        return planName;
    }

    @JsonProperty("planName")
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @JsonProperty("mcoId")
    public String getMcoId() {
        return mcoId;
    }

    @JsonProperty("mcoId")
    public void setMcoId(String mcoId) {
        this.mcoId = mcoId;
    }

    @JsonProperty("programTypeCd")
    public String getProgramTypeCd() {
        return programTypeCd;
    }

    @JsonProperty("programTypeCd")
    public void setProgramTypeCd(String programTypeCd) {
        this.programTypeCd = programTypeCd;
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

    @JsonProperty("npi")
    public String getNpi() {
        return npi;
    }

    @JsonProperty("npi")
    public void setNpi(String npi) {
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

    @JsonProperty("pcpFlag")
    public String getPcpFlag() {
        return pcpFlag;
    }

    @JsonProperty("pcpFlag")
    public void setPcpFlag(String pcpFlag) {
        this.pcpFlag = pcpFlag;
    }

    @JsonProperty("genderCd")
    public String getGenderCd() {
        return genderCd;
    }

    @JsonProperty("genderCd")
    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    @JsonProperty("sexLimitsCd")
    public String getSexLimitsCd() {
        return sexLimitsCd;
    }

    @JsonProperty("sexLimitsCd")
    public void setSexLimitsCd(String sexLimitsCd) {
        this.sexLimitsCd = sexLimitsCd;
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

    @JsonProperty("classificationCd")
    public Object getClassificationCd() {
        return classificationCd;
    }

    @JsonProperty("classificationCd")
    public void setClassificationCd(Object classificationCd) {
        this.classificationCd = classificationCd;
    }

    @JsonProperty("groupFlag")
    public String getGroupFlag() {
        return groupFlag;
    }

    @JsonProperty("groupFlag")
    public void setGroupFlag(String groupFlag) {
        this.groupFlag = groupFlag;
    }

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

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

    @JsonProperty("nameSuffix")
    public Object getNameSuffix() {
        return nameSuffix;
    }

    @JsonProperty("nameSuffix")
    public void setNameSuffix(Object nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    @JsonProperty("titleName")
    public Object getTitleName() {
        return titleName;
    }

    @JsonProperty("titleName")
    public void setTitleName(Object titleName) {
        this.titleName = titleName;
    }

    @JsonProperty("ssn")
    public String getSsn() {
        return ssn;
    }

    @JsonProperty("ssn")
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @JsonProperty("fein")
    public String getFein() {
        return fein;
    }

    @JsonProperty("fein")
    public void setFein(String fein) {
        this.fein = fein;
    }

    @JsonProperty("emailAddress")
    public Object getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(Object emailAddress) {
        this.emailAddress = emailAddress;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }

    @JsonProperty("middleName")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonProperty("acceptObInd")
    public Boolean getAcceptObInd() {
        return acceptObInd;
    }

    @JsonProperty("acceptObInd")
    public void setAcceptObInd(Boolean acceptObInd) {
        this.acceptObInd = acceptObInd;
    }

    @JsonProperty("acceptNewPatientsInd")
    public Boolean getAcceptNewPatientsInd() {
        return acceptNewPatientsInd;
    }

    @JsonProperty("acceptNewPatientsInd")
    public void setAcceptNewPatientsInd(Boolean acceptNewPatientsInd) {
        this.acceptNewPatientsInd = acceptNewPatientsInd;
    }

    @JsonProperty("handicappedAccesibleInd")
    public Boolean getHandicappedAccesibleInd() {
        return handicappedAccesibleInd;
    }

    @JsonProperty("handicappedAccesibleInd")
    public void setHandicappedAccesibleInd(Boolean handicappedAccesibleInd) {
        this.handicappedAccesibleInd = handicappedAccesibleInd;
    }

    @JsonProperty("reasonForChange")
    public Object getReasonForChange() {
        return reasonForChange;
    }

    @JsonProperty("reasonForChange")
    public void setReasonForChange(Object reasonForChange) {
        this.reasonForChange = reasonForChange;
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
    public Object getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updatedBy")
    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonProperty("providerLanguages")
    public List<ProviderLanguage> getProviderLanguages() {
        return providerLanguages;
    }

    @JsonProperty("providerLanguages")
    public void setProviderLanguages(List<ProviderLanguage> providerLanguages) {
        this.providerLanguages = providerLanguages;
    }

    @JsonProperty("providerSpeciality")
    public List<ProviderSpeciality> getProviderSpeciality() {
        return providerSpeciality;
    }

    @JsonProperty("providerSpeciality")
    public void setProviderSpeciality(List<ProviderSpeciality> providerSpeciality) {
        this.providerSpeciality = providerSpeciality;
    }

    @JsonProperty("providerType")
    public List<ProviderType> getProviderType() {
        return providerType;
    }

    @JsonProperty("providerType")
    public void setProviderType(List<ProviderType> providerType) {
        this.providerType = providerType;
    }

    @JsonProperty("providerHospitalAffilation")
    public List<ProviderHospitalAffilation> getProviderHospitalAffilation() {
        return providerHospitalAffilation;
    }

    @JsonProperty("providerHospitalAffilation")
    public void setProviderHospitalAffilation(List<ProviderHospitalAffilation> providerHospitalAffilation) {
        this.providerHospitalAffilation = providerHospitalAffilation;
    }

    @JsonProperty("providerOfficeHours")
    public List<ProviderOfficeHour> getProviderOfficeHours() {
        return providerOfficeHours;
    }

    @JsonProperty("providerOfficeHours")
    public void setProviderOfficeHours(List<ProviderOfficeHour> providerOfficeHours) {
        this.providerOfficeHours = providerOfficeHours;
    }

    @JsonProperty("providerNormalizedAddress")
    public List<ProviderNormalizedAddress> getProviderNormalizedAddress() {
        return providerNormalizedAddress;
    }

    @JsonProperty("providerNormalizedAddress")
    public void setProviderNormalizedAddress(List<ProviderNormalizedAddress> providerNormalizedAddress) {
        this.providerNormalizedAddress = providerNormalizedAddress;
    }

    @JsonProperty("providerPanelLimit")
    public List<ProviderPanelLimit> getProviderPanelLimit() {
        return providerPanelLimit;
    }

    @JsonProperty("providerPanelLimit")
    public void setProviderPanelLimit(List<ProviderPanelLimit> providerPanelLimit) {
        this.providerPanelLimit = providerPanelLimit;
    }

    @JsonProperty("providerAddress")
    public List<ProviderAddress> getProviderAddress() {
        return providerAddress;
    }

    @JsonProperty("providerAddress")
    public void setProviderAddress(List<ProviderAddress> providerAddress) {
        this.providerAddress = providerAddress;
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
