
package com.maersk.crm.api_pojos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConsumerIdentificationNumber implements Serializable {

    @SerializedName("externalConsumerId")
    @Expose
    private String externalConsumerId;
    @SerializedName("identificationNumberType")
    @Expose
    private String identificationNumberType;
    private final static long serialVersionUID = -7223945251617781344L;

    public String getExternalConsumerId() {
        return externalConsumerId;
    }

    public void setExternalConsumerId(String externalConsumerId) {
        this.externalConsumerId = externalConsumerId;
    }

    public String getIdentificationNumberType() {
        return identificationNumberType;
    }

    public void setIdentificationNumberType(String identificationNumberType) {
        this.identificationNumberType = identificationNumberType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("externalConsumerId", externalConsumerId).append("identificationNumberType", identificationNumberType).toString();
    }

}