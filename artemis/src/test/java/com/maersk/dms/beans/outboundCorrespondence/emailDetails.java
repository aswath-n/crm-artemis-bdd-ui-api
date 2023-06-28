package com.maersk.dms.beans.outboundCorrespondence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class emailDetails implements Serializable {
    public List<String> emailAddresses  = new ArrayList<>();
}
