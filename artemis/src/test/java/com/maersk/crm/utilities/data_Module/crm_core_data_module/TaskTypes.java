package com.maersk.crm.utilities.data_Module.crm_core_data_module;

public class TaskTypes {
    //COVER VA TASK TYPES
    public static final String APP_SR_V1 = "App SR V1";
    public static final String PROCESS_APP_V1 = "Process App V1";
    public static final String APP_RENEWAL_SR_V1 = "App Renewal SR V1";
    public static final String APPEALS_SR = "Appeals SR";
    public static final String REVIEW_APPEAL = "Review Appeal";
    public static final String COMPLAINT_SR = "Complaint SR";
    public static final String REVIEW_COMPLAINT = "Review Complaint";
    public static final String ALL_FIELDS = "All Fields";
    public static final String TRANSFER_TO_LDSS = "Transfer to LDSS";
    public static final String VERIFICATION_DOCUMENT = "Verification Document";
    public static final String MISSING_INFORMATION = "Missing Information";
    public static final String CVIU_PROVIDER_REQUEST = "CVIU Provider Request";
    public static final String TRANSLATION_REQUEST = "Translation Request";
    public static final String INBOUND_DOCUMENT = "Inbound Document";
    public static final String PRE_HEARING_CONFERENCE = "Pre-Hearing Conference";
    public static final String PROCESS_RETURNED_MAIL = "Process Returned Mail";
    public static final String CVIU_LDSS_COMMUNICATION_FORM = "CVIU LDSS Communication Form";
    public static final String AUTHORIZED_REPRESENTATIVE_DESIGNATION = "Authorized Representative Designation";
    public static final String LDSS_COMMUNICATION_FORM = "LDSS Communication Form";
    public static final String OUTBOUND_CALL = "Outbound Call";
    public static final String HOSPITAL_AUTHORIZED_REPRESENTATIVE_REQUEST = "Hospital Authorized Representative Request";
    public static final String COMPLAINT_ESCALATION = "Complaint Escalation";
    public static final String CVIU_ELIGIBILITY_TO_DMAS_ESCALATION = "CVIU Eligibility to DMAS Escalation";
    public static final String CPU_ELIGIBILITY_TO_DMAS_ESCALATION = "CPU Eligibility to DMAS Escalation";
    public static final String REVIEW_APPEAL_NOTIFICATION = "Review Appeal Notification";
    public static final String APPEAL_WITHDRAWAL = "Appeal Withdrawal";
    public static final String DMAS_TO_CPU_ESCALATION = "DMAS to CPU Escalation";
    public static final String FAIR_HEARING = "Fair Hearing";
    public static final String APPEAL_REMAND = "Appeal Remand";
    public static final String CVCC_TO_DMAS_ESCALATION = "CVCC to DMAS Escalation";
    public static final String INCOMPLETE_CONTACT_RECORD = "Incomplete Contact Record";
    public static final String HPE_FINAL_DECISION = "HPE Final Decision";
    public static final String HPE_INTERIM_DECISION = "HPE Interim Decision";
    public static final String DMAS_TO_APPEALS_ESCALATION = "DMAS to Appeals Escalation";
    public static final String CVIU_CC_TO_DMAS_ESCALATION = "CVIU CC to DMAS Escalation";
    public static final String APPEALS_TO_DMAS_ESCALATION = "Appeals to DMAS Escalation";
    public static final String DMAS_TO_MAILROOM_ESCALATION = "DMAS to Mailroom Escalation";
    public static final String DMAS_TO_CVIU_CC_ESCALATION = "DMAS to CVIU CC Escalation";
    public static final String REPORT_OF_NEWBORN = "Report of Newborn";
    public static final String HPE_INQUIRIES = "HPE Inquiries";
    public static final String NEWBORN_INQUIRIES = "Newborn Inquiries";
    public static final String REPORT_MY_CHANGE = "Report My Change";
    public static final String DMAS_TO_CVCC_ESCALATION = "DMAS to CVCC Escalation";
    public static final String DMAS_TO_QA_ESCALATION = "DMAS to QA Escalation";
    public static final String DMAS_TO_CVIU_ELIGIBILITY_ESCALATION = "DMAS to CVIU Eligibility Escalation";
    public static final String ALL_FIELDS_2 = "All Fields 2";
    public static final String GENERAL_ESCALATION = "General Escalation";
    public static final String FINAL_APPLICATION_REVIEW = "Final Application Review";
    public static final String APPLICATION_SR = "Application SR";
    public static final String RENEWAL_SR = "Renewal SR";
    public static final String PROCESS_APPLICATION = "Process Application";
    public static final String LINK_CORRESPONDENCE = "Link Correspondence";
    public static final String PROCESS_APPLICATION_RENEWAL = "Process Application Renewal";
    public static final String INBOUND_LDSS_COMMUNICATION_FORM = "Inbound LDSS Communication Form";
    public static final String HPE_NEWBORN_EMAIL = "HPE/Newborn Email";
    public static final String REVIEW_APPEAL_INVALID = "Review Appeal Invalid";
    public static final String FOLLOW_UP_ON_APPEAL = "Follow-Up on Appeal";
    public static final String JUST_CAUSE_DECISION_LETTER = "Just Cause Decision Letter";

    //BLCRM TASK TYPES
    public static final String REVIEW_INCOMPLETE_APPLICATION = "Review Incomplete Application";

    //NJ-SBE TASK TYPES
    public static final String PROCESS_INCOMPLETE_DOCUMENT = "Process Inbound Document";

    public static String convertTaskTypeNameToVariable(String taskTypeName) {
        switch (taskTypeName) {
            case "APP_SR_V1":
                return APP_SR_V1;
            case "PROCESS_APP_V1":
                return PROCESS_APP_V1;
            case "APP_RENEWAL_SR_V1":
                return APP_RENEWAL_SR_V1;
            case "APPEALS_SR":
                return APPEALS_SR;
            case "REVIEW_APPEAL":
                return REVIEW_APPEAL;
            case "COMPLAINT_SR":
                return COMPLAINT_SR;
            case "REVIEW_COMPLAINT":
                return REVIEW_COMPLAINT;
            case "ALL_FIELDS":
                return ALL_FIELDS;
            case "TRANSFER_TO_LDSS":
                return TRANSFER_TO_LDSS;
            case "VERIFICATION_DOCUMENT":
                return VERIFICATION_DOCUMENT;
            case "MISSING_INFORMATION":
                return MISSING_INFORMATION;
            case "CVIU_PROVIDER_REQUEST":
                return CVIU_PROVIDER_REQUEST;
            case "TRANSLATION_REQUEST":
                return TRANSLATION_REQUEST;
            case "INBOUND_DOCUMENT":
                return INBOUND_DOCUMENT;
            case "PRE_HEARING_CONFERENCE":
                return PRE_HEARING_CONFERENCE;
            case "PROCESS_RETURNED_MAIL":
                return PROCESS_RETURNED_MAIL;
            case "CVIU_LDSS_COMMUNICATION_FORM":
                return CVIU_LDSS_COMMUNICATION_FORM;
            case "AUTHORIZED_REPRESENTATIVE_DESIGNATION":
                return AUTHORIZED_REPRESENTATIVE_DESIGNATION;
            case "LDSS_COMMUNICATION_FORM":
                return LDSS_COMMUNICATION_FORM;
            case "OUTBOUND_CALL":
                return OUTBOUND_CALL;
            case "HOSPITAL_AUTHORIZED_REPRESENTATIVE_REQUEST":
                return HOSPITAL_AUTHORIZED_REPRESENTATIVE_REQUEST;
            case "COMPLAINT_ESCALATION":
                return COMPLAINT_ESCALATION;
            case "CVIU_ELIGIBILITY_TO_DMAS_ESCALATION":
                return CVIU_ELIGIBILITY_TO_DMAS_ESCALATION;
            case "CPU_ELIGIBILITY_TO_DMAS_ESCALATION":
                return CPU_ELIGIBILITY_TO_DMAS_ESCALATION;
            case "REVIEW_APPEAL_NOTIFICATION":
                return REVIEW_APPEAL_NOTIFICATION;
            case "APPEAL_WITHDRAWAL":
                return APPEAL_WITHDRAWAL;
            case "DMAS_TO_CPU_ESCALATION":
                return DMAS_TO_CPU_ESCALATION;
            case "FAIR_HEARING":
                return FAIR_HEARING;
            case "APPEAL_REMAND":
                return APPEAL_REMAND;
            case "CVCC_TO_DMAS_ESCALATION":
                return CVCC_TO_DMAS_ESCALATION;
            case "INCOMPLETE_CONTACT_RECORD":
                return INCOMPLETE_CONTACT_RECORD;
            case "HPE_FINAL_DECISION":
                return HPE_FINAL_DECISION;
            case "HPE_INTERIM_DECISION":
                return HPE_INTERIM_DECISION;
            case "DMAS_TO_APPEALS_ESCALATION":
                return DMAS_TO_APPEALS_ESCALATION;
            case "CVIU_CC_TO_DMAS_ESCALATION":
                return CVIU_CC_TO_DMAS_ESCALATION;
            case "APPEALS_TO_DMAS_ESCALATION":
                return APPEALS_TO_DMAS_ESCALATION;
            case "DMAS_TO_MAILROOM_ESCALATION":
                return DMAS_TO_MAILROOM_ESCALATION;
            case "DMAS_TO_CVIU_CC_ESCALATION":
                return DMAS_TO_CVIU_CC_ESCALATION;
            case "REPORT_OF_NEWBORN":
                return REPORT_OF_NEWBORN;
            case "HPE_INQUIRIES":
                return HPE_INQUIRIES;
            case "NEWBORN_INQUIRIES":
                return NEWBORN_INQUIRIES;
            case "REPORT_MY_CHANGE":
                return REPORT_MY_CHANGE;
            case "DMAS_TO_CVCC_ESCALATION":
                return DMAS_TO_CVCC_ESCALATION;
            case "DMAS_TO_QA_ESCALATION":
                return DMAS_TO_QA_ESCALATION;
            case "DMAS_TO_CVIU_ELIGIBILITY_ESCALATION":
                return DMAS_TO_CVIU_ELIGIBILITY_ESCALATION;
            case "ALL_FIELDS_2":
                return ALL_FIELDS_2;
            case "GENERAL_ESCALATION":
                return GENERAL_ESCALATION;
            case "FINAL_APPLICATION_REVIEW":
                return FINAL_APPLICATION_REVIEW;
            case "APPLICATION_SR":
                return APPLICATION_SR;
            case "RENEWAL_SR":
                return RENEWAL_SR;
            case "PROCESS_APPLICATION":
                return PROCESS_APPLICATION;
            case "LINK_CORRESPONDENCE":
                return LINK_CORRESPONDENCE;
            case "PROCESS_APPLICATION_RENEWAL":
                return PROCESS_APPLICATION_RENEWAL;
            case "INBOUND_LDSS_COMMUNICATION_FORM":
                return INBOUND_LDSS_COMMUNICATION_FORM;
            case "HPE_NEWBORN_EMAIL":
                return HPE_NEWBORN_EMAIL;
            case "REVIEW_APPEAL_INVALID":
                return REVIEW_APPEAL_INVALID;
            case "REVIEW_INCOMPLETE_APPLICATION":
                return REVIEW_INCOMPLETE_APPLICATION;
            case "PROCESS_INCOMPLETE_DOCUMENT":
                return PROCESS_INCOMPLETE_DOCUMENT;
            case "FOLLOW_UP_ON_APPEAL":
                return FOLLOW_UP_ON_APPEAL;
            case "JUST_CAUSE_DECISION_LETTER":
                return JUST_CAUSE_DECISION_LETTER;
            default:
                return taskTypeName;
        }
    }
}
