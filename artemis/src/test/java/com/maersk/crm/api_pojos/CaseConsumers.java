
package com.maersk.crm.api_pojos;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class CaseConsumers implements Serializable {

    @SerializedName("consumerRole")
    @Expose
    private String consumerRole;
    private final static long serialVersionUID = 8978012444384354567L;

    public String getConsumerRole() {
        return consumerRole;
    }

    public void setConsumerRole(String consumerRole) {
        this.consumerRole = consumerRole;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("consumerRole", consumerRole).toString();
    }

}