package com.maersk.dms.beans.outboundCorrespondence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceBean {
    public String caseId;
    public List<String> consumerIdList = new ArrayList<>();
    public String correspondenceId;
    public String correspondenceDefinitionMMSCode;
    public Notification[] notificationList;
    public String status;
    public String language;
    public String creationDate;
    private Map<String, String> outBoundCorrespondenceFields;
    public Requester requester;
    public email email;
    public mail mail;
    public fax fax;
    public textMessage textMessage;

    public void returnValues() {
        outBoundCorrespondenceFields = new HashMap<>();
        Field[] randomValues = getWorld().outboundCorrespondenceBean.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                outBoundCorrespondenceFields.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                outBoundCorrespondenceFields.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
    }
}
