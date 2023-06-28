package com.maersk.crm.beans;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactRecord {
    private String phoneNumber;
    private String contactType;
    private String contactChannel;
    private String inboundCallQueue;
    private String contactDispositions;
    private String contactID;
    private String userName;
    private String userID;
    private String contactStart;
    private String contactEnd;
    private String uniqueId;
    private String addionalComment;

    public String getContactOutcome() {
        return contactOutcome;
    }

    private String contactOutcome;

    public void setContactCampaignType(String contactCampaignType) {
        this.contactCampaignType = contactCampaignType;
    }

    private String contactCampaignType;

    public ContactRecord() {
        this.contactRecordId = Integer.valueOf(RandomStringUtils.random(5, false, true));
    }

    public int getContactRecordId() {
        return contactRecordId;
    }

    public void setContactRecordId(int contactRecordId) {
        this.contactRecordId = contactRecordId;
    }

    private int contactRecordId;

    public List<Reasons> getReasonsList() {
        if (reasonsList == null) {
            setContactReasonData(null);
        }
        return reasonsList;
    }

    private List<Reasons> reasonsList;
    private String preferredLanguage;
    private String ssn;
    private String dateOfBirth;


    private Map<String, String> contactRecordData;

    public String getContactCampaignType() {
        return this.contactCampaignType;
    }

    public void setContactOutcome(String s) {
        this.contactOutcome = s;
    }

    public class Reasons {
        private String contactReason;
        private String contactAction;
        private String reasonComments;

        public Reasons(String contactReason, String contactAction, String reasonComments) {
            this.contactReason = contactReason;
            this.contactAction = contactAction;
            this.reasonComments = reasonComments;
        }

        public String getContactReason() {
            return contactReason;
        }

        public void setContactReason(String contactReason) {
            this.contactReason = contactReason;
        }

        public String getContactAction() {
            return contactAction;
        }

        public void setContactAction(String contactAction) {
            this.contactAction = contactAction;
        }

        public String getReasonComments() {
            return reasonComments;
        }

        public void setReasonComments(String reasonComments) {
            this.reasonComments = reasonComments;
        }
    }

    public void setContactReasonData(Map<String, String> dataTable) {
        /*
            Send values of Reasons with all a reason, action, and comment (even if empty)
            if dataTable is null, will generate random data
         */


        int amount = dataTable.size() / 3;
        reasonsList = new ArrayList<>();
        for (int index = 1; index <= 3; index++) {
            reasonsList.add(new Reasons(dataTable.get("contactReason" + index), dataTable.get("contactAction" + index), dataTable.get("reasonComments" + index)));
        }
    }

    public void setSingleContactReasonData(Map<String, String> dataTable) {
        reasonsList = new ArrayList<>();
        reasonsList.add(new Reasons(dataTable.get("contactReason"), dataTable.get("contactAction"), dataTable.get("reasonComments")));
    }

    public Map<String, String> getRandomContactReason() {
        if (contactRecordData == null) {
            createRandomValues();
        }
        return contactRecordData;
    }

    private void createRandomValues() {

    }

    public enum ContactDisposition {
        COMPLETE("Complete"), DROPPED("Dropped"), ESCALATE("Escalate"), REQUESTCALLBACK("Request Call Back"), TRANSFER("Transfer");
        private String disposition;

        private ContactDisposition(String value) {
            this.disposition = value;
        }

        public String getDisposition() {
            return disposition;
        }
    }

    public enum ContactType {
        INBOUND("Inbound"), OUTBOUND("Outbound");
        private String contactType;

        private ContactType(String value) {
            this.contactType = value;
        }

        public String getContactType() {
            return contactType;
        }
    }

    public enum InboundCallQueue {
        ELIGIBILITY("Eligibility"), ENGLISH("English"), ENROLLMENT("Enrollment"), GENERALPROGRAMQUESTIONS("General Program Questions"), NATIVEAMERICAN_ALASKANNATIVE("Native American/Alaskan Native"), SPANISH("Spanish");
        private String inboundCallQueue;

        private InboundCallQueue(String value) {
            this.inboundCallQueue = value;
        }

        public String getInboundCallQueue() {
            return inboundCallQueue;
        }
    }

    public enum ContactChannelType {
        EMAIL("Email"), PHONE("Phone"), SMSTEXT("SMS Text"), WEBCHAT("Web Chat");
        private String contactChannelType;

        private ContactChannelType(String value) {
            this.contactChannelType = value;
        }

        public String getContactChannelType() {
            return contactChannelType;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType.getContactType();
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactChannel() {
        return contactChannel;
    }

    public void setContactChannel(ContactChannelType contactChannel) {
        this.contactChannel = contactChannel.getContactChannelType();
    }

    public void setContactChannel(String contactChannel) {
        this.contactChannel = contactChannel;
    }

    public String getInboundCallQueue() {
        return inboundCallQueue;
    }

    public void setInboundCallQueue(InboundCallQueue inboundCallQueue) {
        this.inboundCallQueue = inboundCallQueue.getInboundCallQueue();
    }

    public void setInboundCallQueue(String inboundCallQueue) {
        this.inboundCallQueue = inboundCallQueue;
    }

    public String getContactDispositions() {
        return contactDispositions;
    }

    public void setContactDispositions(ContactDisposition disposition) {
        this.contactDispositions = disposition.getDisposition();
    }

    public void setContactDispositions(String disposition) {
        this.contactDispositions = disposition;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContactStart() {
        return contactStart;
    }

    public void setContactStart(String contactStart) {
        this.contactStart = contactStart;
    }

    public String getContactEnd() {
        return contactEnd;
    }

    public void setContactEnd(String contactEnd) {
        this.contactEnd = contactEnd;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAddionalComment() {
        return addionalComment;
    }

    public void setAddionalComment(String addionalComment) {
        this.addionalComment = addionalComment;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
