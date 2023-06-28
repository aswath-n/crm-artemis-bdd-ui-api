
package com.maersk.crm.api_pojos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Consumer implements Serializable {

    @SerializedName("consumerId")
    @Expose
    private int consumerId;
    @SerializedName("caseConsumers")
    @Expose
    private CaseConsumers caseConsumers;
    @SerializedName("consumerIdentificationNumber")
    @Expose
    private ConsumerIdentificationNumber consumerIdentificationNumber;
    @SerializedName("consumerStatus")
    @Expose
    private String consumerStatus;
    @SerializedName("consumerSSN")
    @Expose
    private String consumerSSN;
    @SerializedName("consumerFirstName")
    @Expose
    private String consumerFirstName;
    @SerializedName("consumerLastName")
    @Expose
    private String consumerLastName;
    @SerializedName("consumerDateOfBirth")
    @Expose
    private String consumerDateOfBirth;
    private final static long serialVersionUID = -1021467155279514678L;

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public CaseConsumers getCaseConsumers() {
        return caseConsumers;
    }

    public void setCaseConsumers(CaseConsumers caseConsumers) {
        this.caseConsumers = caseConsumers;
    }

    public ConsumerIdentificationNumber getConsumerIdentificationNumber() {
        return consumerIdentificationNumber;
    }

    public void setConsumerIdentificationNumber(ConsumerIdentificationNumber consumerIdentificationNumber) {
        this.consumerIdentificationNumber = consumerIdentificationNumber;
    }

    public String getConsumerStatus() {
        return consumerStatus;
    }

    public void setConsumerStatus(String consumerStatus) {
        this.consumerStatus = consumerStatus;
    }

    public String getConsumerSSN() {
        return consumerSSN;
    }

    public void setConsumerSSN(String consumerSSN) {
        this.consumerSSN = consumerSSN;
    }

    public String getConsumerFirstName() {
        return consumerFirstName;
    }

    public void setConsumerFirstName(String consumerFirstName) {
        this.consumerFirstName = consumerFirstName;
    }

    public String getConsumerLastName() {
        return consumerLastName;
    }

    public void setConsumerLastName(String consumerLastName) {
        this.consumerLastName = consumerLastName;
    }

    public String getConsumerDateOfBirth() {
        return consumerDateOfBirth;
    }

    public void setConsumerDateOfBirth(String consumerDateOfBirth) {
        this.consumerDateOfBirth = consumerDateOfBirth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("consumerId", consumerId).append("caseConsumers", caseConsumers).append("consumerIdentificationNumber", consumerIdentificationNumber).append("consumerStatus", consumerStatus).append("consumerSSN", consumerSSN).append("consumerFirstName", consumerFirstName).append("consumerLastName", consumerLastName).append("consumerDateOfBirth", consumerDateOfBirth).toString();
    }

}
