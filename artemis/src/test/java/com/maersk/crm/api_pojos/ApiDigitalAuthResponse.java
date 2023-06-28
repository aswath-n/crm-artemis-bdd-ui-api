
package com.maersk.crm.api_pojos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ApiDigitalAuthResponse implements Serializable {

    @SerializedName("authenticationStatus")
    @Expose
    private String authenticationStatus;
    @SerializedName("authenticationId")
    @Expose
    private int authenticationId;
    @SerializedName("consumer")
    @Expose
    private Consumer consumer;
    @SerializedName("caseId")
    @Expose
    private int caseId;
    @SerializedName("errorResponse")
    @Expose
    private String errorResponse;
    private final static long serialVersionUID = 4324574846875135384L;

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public int getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(int authenticationId) {
        this.authenticationId = authenticationId;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("authenticationStatus", authenticationStatus).append("authenticationId", authenticationId).append("consumer", consumer).append("caseId", caseId).append("errorResponse", errorResponse).toString();
    }

}