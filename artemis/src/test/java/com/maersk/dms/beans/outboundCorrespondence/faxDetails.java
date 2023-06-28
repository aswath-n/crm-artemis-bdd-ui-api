package com.maersk.dms.beans.outboundCorrespondence;

import java.io.Serializable;

public class faxDetails implements Serializable {
    public String countryCode ="String";
    public String faxNumber = "String";
    public String faxReferenceId = "String";
    public String toCompanyName = "String";
    public String toName = "String";

    @Override
    public String toString()
    {
        return "faxDetails [countryCode=" + countryCode + ", faxNumber=" + faxNumber + ", " +
                "faxReferenceId=" + faxReferenceId + ", toCompanyName=" + toCompanyName + ", toName=" + toName + "]";
    }
}
