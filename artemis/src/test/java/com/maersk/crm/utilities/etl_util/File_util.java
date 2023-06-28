package com.maersk.crm.utilities.etl_util;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import com.maersk.crm.etl_step_definitions.EE_ETL_StepDefs;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ExcelReader;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class File_util extends CRMUtilities implements ApiBase {
    private static String fName;
    private static String lName;
    private static String birthday;
    Faker faker= new Faker();

//    String addressLine=faker.address().streetAddress();
//    String birthday=String.valueOf(LocalDate.now().minusDays(new Random().nextInt(200))).replace("-","");
//    String birthday="";

    private String memberId;
    private String recipientId;

    public String getMemberId() {
        return memberId;
    }
    public String getRecipientId() {
        return recipientId;
    }

    public JsonObject allFileJsonCreator(Map<String, String> data) {
        JsonObject fileLine = new JsonObject();
        fileLine.addProperty("ProviderPMPId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderPMPId", "stateProviderId::"))));
        fileLine.addProperty("GroupId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("GroupId", "providerGroupId::"))));
        fileLine.addProperty("GroupName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("GroupName", "name::"))));
        fileLine.addProperty("County", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("County", "45"))));
        fileLine.addProperty("ProviderName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderName", "name::"))));
        fileLine.addProperty("FirstName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FirstName", "name::"))));
        fileLine.addProperty("LastName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LastName", "name::"))));
        fileLine.addProperty("MiddleName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MiddleName", "D"))));
        fileLine.addProperty("ServiceLocationStreet1", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationStreet1", "Rendering Provider No Addr"))));
        fileLine.addProperty("ServiceLocationStreet2", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationStreet2", " "))));
        fileLine.addProperty("ServiceLocationCity", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationCity", "Indianapolis"))));
        fileLine.addProperty("ServiceLocationState", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationState", "IN"))));
        fileLine.addProperty("ServiceLocationZip", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationZip", "46204"))).replace(".0", ""));
        fileLine.addProperty("ServiceLocationZip4", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ServiceLocationZip4", "1034"))).replace(".0", ""));
        fileLine.addProperty("AfterHrsPhone", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AfterHrsPhone", "phone::"))).replace(".0", ""));
        fileLine.addProperty("ProviderPrimarySpecialty", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderPrimarySpecialty", "322"))).replace(".0", ""));
        fileLine.addProperty("ProviderType", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderType", "31"))).replace(".0", ""));
        fileLine.addProperty("ProviderSubSpecialty ", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderSubSpecialty ", "315"))).replace(".0", ""));
        fileLine.addProperty("PanelSize", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PanelSize", "1000"))).replace(".0", ""));
        fileLine.addProperty("CurrentPanelMembers", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CurrentPanelMembers", "10"))).replace(".0", ""));
        fileLine.addProperty("PanelSizeHold", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PanelSizeHold", "N"))));
        fileLine.addProperty("UPIN", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("UPIN", "C24211"))));
        fileLine.addProperty("ProviderClassification", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderClassification", "R"))));
        fileLine.addProperty("PMPIndicator", (data.containsKey("PMPIndicator") && data.get("PMPIndicator").equals("null") ? "" : data.getOrDefault("PMPIndicator", "Y")));
        fileLine.addProperty("LocationProgram", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LocationProgram", "H"))));
        fileLine.addProperty("LocationEffectiveDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LocationEffectiveDate", "20180101"))).replace(".0", ""));
        fileLine.addProperty("LocationEndDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LocationEndDate", "22991231"))).replace(".0", ""));
        fileLine.addProperty("AdmitPrivileges", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AdmitPrivileges", "P"))));
        fileLine.addProperty("DeliveryPrivileges", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DeliveryPrivileges", "A"))));
        fileLine.addProperty("AcceptsObstetricsIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AcceptsObstetricsIndicator", "N"))));
        fileLine.addProperty("AgeRange", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AgeRange", "EE"))));
        fileLine.addProperty("GenderAcceptedByDoctor", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("GenderAcceptedByDoctor", "B"))));
        fileLine.addProperty("AllWomenOBGYNOnlyIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AllWomenOBGYNOnlyIndicator", "N"))));
        fileLine.addProperty("StateRegionCode", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("StateRegionCode", "S"))));
        fileLine.addProperty("Filler", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Filler", " "))));
        fileLine.addProperty("filler", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("filler", " "))));
        recipientId = RandomStringUtils.random(12, false, true);
        fileLine.addProperty("RecipientID", (data.containsKey("RecipientID") && data.get("RecipientID").equals("null") ? "            " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipientID", recipientId)))));
        fileLine.addProperty("RecipLastName", (data.containsKey("RecipLastName") && data.get("RecipLastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName())))));
        fileLine.addProperty("RecipFirstName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipFirstName", "name::"))));
        fileLine.addProperty("RecipMiddleInitial", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipMiddleInitial", RandomStringUtils.random(1, true, false)))));
        fileLine.addProperty("RecipAddrCity", (data.containsKey("RecipAddrCity") && data.get("RecipAddrCity").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipAddrCity", "Indianapolis")))));
        fileLine.addProperty("RecipAddrState", (data.containsKey("RecipAddrState") && data.get("RecipAddrState").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipAddrState", "IN")))));
        fileLine.addProperty("RecipAddrZipCode", (data.containsKey("RecipAddrZipCode") && data.get("RecipAddrZipCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipAddrZipCode", RandomStringUtils.random(5, false, true))))));
        fileLine.addProperty("RecipAddrZipCodeExt", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipAddrZipCodeExt", "0000"))));
        fileLine.addProperty("RecipSSN", (data.containsKey("RecipSSN") && data.get("RecipSSN").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipSSN", "ssn::")))));
        fileLine.addProperty("RecipBirthDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipBirthDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateRandomDOB()))));
        fileLine.addProperty("RecipDeathDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipDeathDate", ""))));
        fileLine.addProperty("RecipSex", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipSex", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateGender()))));
        fileLine.addProperty("IllegalIndicator", (data.containsKey("IllegalIndicator") && !data.get("IllegalIndicator").equals(null) ? data.get("IllegalIndicator") : ""));
        fileLine.addProperty("RecipMaritalCode", (data.containsKey("RecipMaritalCode") && data.get("RecipMaritalCode").equals("null") ? "" : data.getOrDefault("RecipMaritalCode", "R")));
        fileLine.addProperty("RecipCounty", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipCounty", "49"))));
        fileLine.addProperty("RecipLanguage", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipLanguage", "ENG"))));
        fileLine.addProperty("RecipWardType", (data.containsKey("RecipWardType") && data.get("RecipWardType").equals("null") ? "" : data.getOrDefault("RecipWardType", "N")));
        fileLine.addProperty("RecipCountyWard", (data.containsKey("RecipCountyWard") && data.get("RecipCountyWard").equals("null") ? "" : data.getOrDefault("RecipCountyWard", "")));
        fileLine.addProperty("RecipIndActive", (data.containsKey("RecipIndActive") && data.get("RecipIndActive").equals("null") ? "" : data.getOrDefault("RecipIndActive", "Y")));
        fileLine.addProperty("CaseNumber", (data.containsKey("CaseNumber") && data.get("CaseNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CaseNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generate_random_CaseNumber())))));
        fileLine.addProperty("StreetAddress1", (data.containsKey("StreetAddress1") && data.get("StreetAddress1").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("StreetAddress1", "address::")))));
        fileLine.addProperty("StreetAddress2", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("StreetAddress2", ""))));
        fileLine.addProperty("CurrentHealthCareProgram", (data.containsKey("CurrentHealthCareProgram") && data.get("CurrentHealthCareProgram").equals("null") ? "" : data.getOrDefault("CurrentHealthCareProgram", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genNonHipPlanCode())));
        fileLine.addProperty("FutureHealthCareProgram", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FutureHealthCareProgram", ""))));
        fileLine.addProperty("CurrentManagedCareStatusInCurrentProgram", (data.containsKey("CurrentManagedCareStatusInCurrentProgram") && data.get("CurrentManagedCareStatusInCurrentProgram").equals("null") ? "" : data.getOrDefault("CurrentManagedCareStatusInCurrentProgram", "M")));
        fileLine.addProperty("CurrentRecipAidCategory", (data.containsKey("CurrentRecipAidCategory") && data.get("CurrentRecipAidCategory").equals("null") ? "" : data.getOrDefault("CurrentRecipAidCategory", "2")));
        fileLine.addProperty("CurrentAidEligEffectiveDate", (data.containsKey("CurrentAidEligEffectiveDate") && !data.get("CurrentAidEligEffectiveDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("CurrentAidEligEffectiveDate")) : ""));
        fileLine.addProperty("PhoneNumber", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneNumber", "phone::"))));
        fileLine.addProperty("CurrentAidEligEndDate", (data.containsKey("CurrentAidEligEndDate") && !data.get("CurrentAidEligEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("CurrentAidEligEndDate")) : ""));
        fileLine.addProperty("FutureManagedCareStatusInFutureProgram", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FutureManagedCareStatusInFutureProgram", ""))));
        fileLine.addProperty("FutureRecipAidCategory", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FutureRecipAidCategory", ""))));
        fileLine.addProperty("FutureAidEligEffectiveDate", (data.containsKey("FutureAidEligEffectiveDate") && !data.get("FutureAidEligEffectiveDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("FutureAidEligEffectiveDate")) : ""));
        fileLine.addProperty("FutureAidEligEndDate", (data.containsKey("FutureAidEligEndDate") && !data.get("FutureAidEligEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("FutureAidEligEndDate")) : ""));
        fileLine.addProperty("OpenEnrollmentEffectiveDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("OpenEnrollmentEffectiveDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate("1stDayofPresentMonthETLver")))));
        fileLine.addProperty("OpenEnrollmentEndDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("OpenEnrollmentEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("1stDayofPresentMonthETLver")))));
        fileLine.addProperty("OpenEnrollmentStatus", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("OpenEnrollmentStatus", "C"))));
        fileLine.addProperty("OpenEnrollmentLockinCompletedDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("OpenEnrollmentLockinCompletedDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("lastDayofPresentMonthETLver")))));
        fileLine.addProperty("RedeterminationDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RedeterminationDate", ""))));
        fileLine.addProperty("PMPPaper", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PMPPaper", "N"))));
        fileLine.addProperty("NativeAmerican", (data.containsKey("NativeAmerican") && !data.get("NativeAmerican").equals(null) ? data.get("NativeAmerican") : ""));
        memberId = RandomStringUtils.random(12, false, true);
        fileLine.addProperty("MemberId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberId", memberId))));
        lName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName();
        fileLine.addProperty("MemberLastName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberLastName", lName))));
        fName = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName();
        fileLine.addProperty("MemberFirstName", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberFirstName", fName))));
        fileLine.addProperty("MemberMiddleInitial", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberMiddleInitial", RandomStringUtils.random(1, true, false)))));
        fileLine.addProperty("MemberAddrCity", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberAddrCity", "Indianapolis"))));
        fileLine.addProperty("MemberAddrState", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberAddrState", "IN"))));
        fileLine.addProperty("MemberAddrZipCode", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberAddrZipCode", RandomStringUtils.random(5, false, true)))));
        fileLine.addProperty("MemberAddrZipCodeExt", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberAddrZipCodeExt", "0000"))));
        fileLine.addProperty("MemberSSN", (data.containsKey("MemberSSN") && data.get("MemberSSN").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberSSN", "ssn::")))));
        fileLine.addProperty("MemberBirthDate", (data.containsKey("MemberBirthDate") && data.get("MemberBirthDate").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberBirthDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateRandomDOB())))));
        fileLine.addProperty("MemberDeathDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberDeathDate", ""))));
        fileLine.addProperty("MemberSex", (data.containsKey("MemberSex") && data.get("MemberSex").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberSex", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateGender())))));
        fileLine.addProperty("MemberMaritalCode", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberMaritalCode", "R"))));
        fileLine.addProperty("MemberCounty", (data.containsKey("MemberCounty") && data.get("MemberCounty").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberCounty", "49")))));
        fileLine.addProperty("MemberLanguage", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberLanguage", "ENG"))));
        fileLine.addProperty("MemberWardType", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberWardType", "N"))));
        fileLine.addProperty("MemberCountyWard", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberCountyWard", ""))));
        fileLine.addProperty("MemberIndActive", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberIndActive", "Y"))));
        fileLine.addProperty("CurrentMemberAidCategory", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CurrentMemberAidCategory", "2"))));
        fileLine.addProperty("FutureMemberAidCategory", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FutureMemberAidCategory", ""))));
        fileLine.addProperty("HIPStatus", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HIPStatus", "E"))));
        fileLine.addProperty("SpouseMemberId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("SpouseMemberId", ""))));
        fileLine.addProperty("DebtIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DebtIndicator", "N"))));
        fileLine.addProperty("ConditionalEligDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ConditionalEligDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().todayETL()))));
        fileLine.addProperty("BenefitEffectiveDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("BenefitEffectiveDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().todayETL()))));
        fileLine.addProperty("BenefitEndDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("BenefitEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate("highDateETLver")))));
        fileLine.addProperty("IndPMPPaper", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("IndPmpPaper", "N"))));
        fileLine.addProperty("PotentialPlusIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PotentialPlusIndicator", "Y"))));
        fileLine.addProperty("PregnancyIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PregnancyIndicator", "N"))));
        fileLine.addProperty("PregnancyStartDate", (data.containsKey("PregnancyStartDate") && !data.get("PregnancyStartDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("PregnancyStartDate")) : ""));
        fileLine.addProperty("PregnancyEndDate", (data.containsKey("PregnancyEndDate") && !data.get("PregnancyEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("PregnancyEndDate")) : ""));
        fileLine.addProperty("PregnancyEstimatedDeliveryDate", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PregnancyEstimatedDeliveryDate", ""))));
        fileLine.addProperty("MedicallyFrailConfirmationCode", (data.containsKey("MedicallyFrailConfirmationCode") && !data.get("MedicallyFrailConfirmationCode").equals(null) ? data.get("MedicallyFrailConfirmationCode") : ""));
        fileLine.addProperty("MedicallyFrailLastConfirmedAssessmentDate", (data.containsKey("MedicallyFrailLastConfirmedAssessmentDate")
                && !data.get("MedicallyFrailLastConfirmedAssessmentDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("MedicallyFrailLastConfirmedAssessmentDate")) : ""));
        fileLine.addProperty("IdMedicaid", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("IdMedicaid", RandomStringUtils.random(12, false, true)))));
        fileLine.addProperty("DateEffective", (data.containsKey("DateEffective") && !data.get("DateEffective").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("DateEffective")) : ""));
        fileLine.addProperty("CodeReasonStart", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CodeReasonStart", "A7"))));
        fileLine.addProperty("DateEnd", (data.containsKey("DateEnd") && !data.get("DateEnd").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("DateEnd")) : ""));
        fileLine.addProperty("CodeReasonStop", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CodeReasonStop", "99"))));
        fileLine.addProperty("ProviderId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderId", "stateProviderId::"))));
        fileLine.addProperty("CodeHealthSubpgm", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CodeHealthSubpgm", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genNonHipPlanCode()))));
        fileLine.addProperty("ProviderGroupId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderGroupId", "999999999"))));
        fileLine.addProperty("MCEId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MCEId", "455701400"))));
        fileLine.addProperty("CodeStateRegion", (data.containsKey("CodeStateRegion") && !data.get("CodeStateRegion").equals("null") ? data.get("CodeStateRegion") : ""));
        fileLine.addProperty("EBTransId", (data.containsKey("EBTransId") && !data.get("EBTransId").equals("null") ? data.get("EBTransId") : ""));
        fileLine.addProperty("TransactionDisposition", (data.containsKey("TransactionDisposition") && !data.get("TransactionDisposition").equals("null") ? data.get("TransactionDisposition") : ""));
        fileLine.addProperty("RecipMI", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipMI", "M"))));
        fileLine.addProperty("EnrollmentIdTransactionId", (data.containsKey("EnrollmentIdTransactionId") && !data.get("EnrollmentIdTransactionId").equals("null") ? EE_ETL_StepDefs.nameToNewEnrollmentId.get(data.get("EnrollmentIdTransactionId")) : ""));
        fileLine.addProperty("TransactionType", (data.containsKey("TransactionType") && !data.get("TransactionType").equals("null") ? data.get("TransactionType") : ""));
        fileLine.addProperty("MedicaidId", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MedicaidId", RandomStringUtils.random(12, false, true)))));
        fileLine.addProperty("MemberDateEffective", (data.containsKey("MemberDateEffective") && !data.get("MemberDateEffective").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("MemberDateEffective")) : ""));
        fileLine.addProperty("MemberDateEnd", (data.containsKey("MemberDateEnd") && !data.get("MemberDateEnd").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("MemberDateEnd")) : ""));
        fileLine.addProperty("ProviderTypeCode", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderTypeCode", "M"))));
        fileLine.addProperty("ProviderDateEffective", (data.containsKey("ProviderDateEffective") && !data.get("ProviderDateEffective").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("ProviderDateEffective")) : ""));
        fileLine.addProperty("ProviderDateEnd", (data.containsKey("ProviderDateEnd") && !data.get("ProviderDateEnd").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("ProviderDateEnd")) : ""));
        fileLine.addProperty("RestrictionIndicator", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RestrictionIndicator", "I"))));
        fileLine.addProperty("ProviderSpecialty", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderSpecialty", ""))));
        fileLine.addProperty("LOCDateEffective", (data.containsKey("LOCDateEffective") && !data.get("LOCDateEffective").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("LOCDateEffective")) : ""));
        fileLine.addProperty("LOCDateEnd", (data.containsKey("LOCDateEnd") && !data.get("LOCDateEnd").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("LOCDateEnd")) : ""));
        fileLine.addProperty("CodeLevelOfCare", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CodeLevelOfCare", "53H"))));
        fileLine.addProperty("DteAdded", (data.containsKey("DteAdded") && !data.get("DteAdded").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("DteAdded")) : ""));
        fileLine.addProperty("IndMcAssign", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("IndMcAssign", ""))));
        fileLine.addProperty("CdeReason", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CdeReason", "C"))));
        fileLine.addProperty("cdeRsnMCDet", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("cdeRsnMCDet", "R"))));
        fileLine.addProperty("CdeHealthSubpgm", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CdeHealthSubpgm", "A"))));
        fileLine.addProperty("CdeRsnDel", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CdeRsnDel", ""))));
        // ******** NEWBORN LINES **************************************
//        fileLine.addProperty("ProviderNumber", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderNumber", "0055558200"))));
        fileLine.addProperty("ProviderNumber",data.containsKey("ProviderNumber") && data.get("ProviderNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderNumber","0055558200"))));
        fileLine.addProperty("ProviderType",data.containsKey("ProviderType") && data.get("ProviderType").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderType","P"))));
        fileLine.addProperty("ProviderType",data.containsKey("ProviderType") && data.get("ProviderType").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderType","P"))));
        fileLine.addProperty("PlanProviderNumber",data.containsKey("PlanProviderNumber") && data.get("PlanProviderNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PlanProviderNumber",RandomStringUtils.random(16,false,true)))));
        fileLine.addProperty("LicenseNumber",data.containsKey("LicenseNumber") && data.get("LicenseNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LicenseNumber","MD"+RandomStringUtils.random(9,false,true)))));
        fileLine.addProperty("ProviderLastName",data.containsKey("ProviderLastName") && data.get("ProviderLastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderLastName",genRandomLastNameETL()))));
        fileLine.addProperty("ProviderFirstName",data.containsKey("ProviderFirstName") && data.get("ProviderFirstName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderFirstName",genRandomFirstNameETL()))));
        fileLine.addProperty("ProviderMiddleName",data.containsKey("ProviderMiddleName") && data.get("ProviderMiddleName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderMiddleName","M"))));
        fileLine.addProperty("PhoneAreaCode",data.containsKey("PhoneAreaCode") && data.get("PhoneAreaCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneAreaCode","202"))));
        fileLine.addProperty("ProviderPhoneNumber",data.containsKey("ProviderPhoneNumber") && data.get("ProviderPhoneNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderPhoneNumber",RandomStringUtils.random(7,false,true)))));
        fileLine.addProperty("PhoneExt",data.containsKey("PhoneExt") && data.get("PhoneExt").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneExt","0000"))));
        fileLine.addProperty("CaseNumberNewborn",data.containsKey("CaseNumberNewborn") && data.get("CaseNumberNewborn").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CaseNumberNewborn",RandomStringUtils.random(8,false,true)))));
        fileLine.addProperty("OriginalRecipId",data.containsKey("OriginalRecipId") && data.get("OriginalRecipId").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("OriginalRecipId",RandomStringUtils.random(8,false,true)))));
        fileLine.addProperty("CurrentRecipId",data.containsKey("CurrentRecipId") && data.get("CurrentRecipId").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CurrentRecipId",RandomStringUtils.random(8,false,true)))));
        fileLine.addProperty("MotherMedicaidId",data.containsKey("MotherMedicaidId") && data.get("MotherMedicaidId").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherMedicaidId",RandomStringUtils.random(8,false,true)))));
        fileLine.addProperty("MotherSSN",data.containsKey("MotherSSN") && data.get("MotherSSN").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherSSN",RandomStringUtils.random(9,false,true)))));
        fileLine.addProperty("MotherFirstName",data.containsKey("MotherFirstName") && data.get("MotherFirstName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherFirstName",genRandomFirstNameETL()))));
        fileLine.addProperty("MotherLastName",data.containsKey("MotherLastName") && data.get("MotherLastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherLastName",genRandomLastNameETL()))));
        fileLine.addProperty("MotherMiddleName",data.containsKey("MotherMiddleName") && data.get("MotherMiddleName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherMiddleName","name::"))));
        fileLine.addProperty("MotherPhoneNumber",data.containsKey("MotherPhoneNumber") && data.get("MotherPhoneNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherPhoneNumber","202"+RandomStringUtils.random(7,false,true)))));
        fileLine.addProperty("MotherAddressLine", (data.containsKey("MotherAddressLine") && data.get("MotherAddressLine").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherAddressLine", genRandomAddress1())))));
        fileLine.addProperty("MotherCity",data.containsKey("MotherCity") && data.get("MotherCity").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherCity","Washington"))));
        fileLine.addProperty("MotherState",data.containsKey("MotherState") && data.get("MotherState").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherState","DC"))));
        fileLine.addProperty("MotherZipCode",data.containsKey("MotherZipCode") && data.get("MotherZipCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherZipCode","20003"))));
        fileLine.addProperty("MotherQuadrant",data.containsKey("MotherQuadrant") && data.get("MotherQuadrant").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherQuadrant","NW"))));
        fileLine.addProperty("MotherWard",data.containsKey("MotherWard") && data.get("MotherWard").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MotherWard","01"))));
        fileLine.addProperty("FatherFirstName",data.containsKey("FatherFirstName") && data.get("FatherFirstName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FatherFirstName","name::"))));
        fileLine.addProperty("FatherLastName",data.containsKey("FatherLastName") && data.get("FatherLastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FatherLastName","name::"))));
        fileLine.addProperty("FatherMiddleName",data.containsKey("FatherMiddleName") && data.get("FatherMiddleName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FatherMiddleName","name::"))));
        fileLine.addProperty("FatherPhoneNum",data.containsKey("FatherPhoneNum") && data.get("FatherPhoneNum").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FatherPhoneNum","202"+RandomStringUtils.random(7,false,true)))));
        fileLine.addProperty("RecipMedicaidId",data.containsKey("RecipMedicaidId") && data.get("RecipMedicaidId").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipMedicaidId",RandomStringUtils.random(8,false,true)))));
        fileLine.addProperty("RecipLastName",data.containsKey("RecipLastName") && data.get("RecipLastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipLastName",genRandomLastNameETL()))));
        fileLine.addProperty("RecipFirstName",data.containsKey("RecipFirstName") && data.get("RecipFirstName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipFirstName", genRandomFirstNameETL()))));
        fileLine.addProperty("RecipMiddleName",data.containsKey("RecipMiddleName") && data.get("RecipMiddleName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipMiddleName","name::"))));
        fileLine.addProperty("RecipDateOfBirth", (data.containsKey("RecipDateOfBirth") && data.get("RecipDateOfBirth").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipDateOfBirth", genNewbornBirthDate())))));
        fileLine.addProperty("RecipSex", (data.containsKey("RecipSex") && data.get("RecipSex").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipSex", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateGender())))));
        fileLine.addProperty("RecipRaceCode",data.containsKey("RecipRaceCode") && data.get("RecipRaceCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RecipRaceCode","1"))));
        fileLine.addProperty("MCOHospitalNumber",data.containsKey("MCOHospitalNumber") && data.get("MCOHospitalNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MCOHospitalNumber","0100040"))));
        fileLine.addProperty("RecipEligBegDate", (data.containsKey("RecipEligBegDate") && !data.get("RecipEligBegDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("RecipEligBegDate")) : ""));
        fileLine.addProperty("RecipEligEndDate", (data.containsKey("RecipEligEndDate") && !data.get("RecipEligEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("RecipEligEndDate")) : ""));
       // **** NEWBORN LINES END************************
        fileLine.addProperty("SuiteNumber", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("SuiteNumber", ""))));
        fileLine.addProperty("ProviderAddressLine1", (data.containsKey("ProviderAddressLine1") && data.get("ProviderAddressLine1").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderAddressLine1",(genRandomAddress1()+ " NW"))))));
        fileLine.addProperty("ProviderAddressLine2", (data.containsKey("ProviderAddressLine2") && data.get("ProviderAddressLine2").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderAddressLine2", "Ste "+RandomStringUtils.random(3,false,true))))));
        fileLine.addProperty("ProviderQuadrant",data.containsKey("ProviderQuadrant") && data.get("ProviderQuadrant").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderQuadrant","01"))));
        fileLine.addProperty("ProviderCity",data.containsKey("ProviderCity") && data.get("ProviderCity").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderCity","Washington"))));
        fileLine.addProperty("ProviderStateCode",data.containsKey("ProviderStateCode") && data.get("ProviderStateCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderStateCode","DC"))));
        fileLine.addProperty("ProviderZipCode",data.containsKey("ProviderZipCode") && data.get("ProviderZipCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderZipCode","20003" + RandomStringUtils.random(4,false,true)))));
        fileLine.addProperty("PhoneAreaCode",data.containsKey("PhoneAreaCode") && data.get("PhoneAreaCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneAreaCode","202"))));
        fileLine.addProperty("ProviderPhoneNumber",data.containsKey("ProviderPhoneNumber") && data.get("ProviderPhoneNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderPhoneNumber",RandomStringUtils.random(7,false,true)))));
        fileLine.addProperty("PhoneExt",data.containsKey("PhoneExt") && data.get("PhoneExt").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneExt",RandomStringUtils.random(4,false,true)))));
        fileLine.addProperty("ProviderSex", (data.containsKey("ProviderSex") && data.get("ProviderSex").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderSex", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateGender())))));
        fileLine.addProperty("ProviderLimitation",data.containsKey("ProviderLimitation") && data.get("ProviderLimitation").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProviderLimitation","X"))));
        fileLine.addProperty("GenderLimitation",data.containsKey("GenderLimitation") && data.get("GenderLimitation").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("GenderLimitation","M"))));
        fileLine.addProperty("EveningHours",data.containsKey("EveningHours") && data.get("EveningHours").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("EveningHours","Y"))));
        fileLine.addProperty("SaturdayHours",data.containsKey("SaturdayHours") && data.get("SaturdayHours").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("SaturdayHours","Y"))));
        fileLine.addProperty("AgeFrom",data.containsKey("AgeFrom") && data.get("AgeFrom").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AgeFrom","0"))));
        fileLine.addProperty("AgeTo",data.containsKey("AgeTo") && data.get("AgeTo").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AgeTo","120"))));
        fileLine.addProperty("PCPFlag",data.containsKey("PCPFlag") && data.get("PCPFlag").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PCPFlag","Y"))));
        fileLine.addProperty("PrimarySpeciality1",data.containsKey("PrimarySpeciality1") && data.get("PrimarySpeciality1").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PrimarySpeciality1","070"))));
        fileLine.addProperty("Speciality2",data.containsKey("Speciality2") && data.get("Speciality2").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Speciality2","060"))));
        fileLine.addProperty("Speciality3",data.containsKey("Speciality3") && data.get("Speciality3").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Speciality3","24A"))));
        fileLine.addProperty("Language1",data.containsKey("Language1") && data.get("Language1").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Language1","ENG"))));
        fileLine.addProperty("Language2",data.containsKey("Language2") && data.get("Language2").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Language2","SPA"))));
        fileLine.addProperty("Language3",data.containsKey("Language3") && data.get("Language3").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Language3","ARA"))));
        fileLine.addProperty("Language4",data.containsKey("Language4") && data.get("Language4").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Language4","HIN"))));
        fileLine.addProperty("Language5",data.containsKey("Language5") && data.get("Language5").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Language5","POL"))));
        fileLine.addProperty("HospitalAffl1",data.containsKey("HospitalAffl1") && data.get("HospitalAffl1").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HospitalAffl1","100001"))));
        fileLine.addProperty("HospitalAffl2",data.containsKey("HospitalAffl2") && data.get("HospitalAffl2").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HospitalAffl2","100030"))));
        fileLine.addProperty("HospitalAffl3",data.containsKey("HospitalAffl3") && data.get("HospitalAffl3").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HospitalAffl3","100040"))));
        fileLine.addProperty("HospitalAffl4",data.containsKey("HospitalAffl4") && data.get("HospitalAffl4").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HospitalAffl4","100060"))));
        fileLine.addProperty("HospitalAffl5",data.containsKey("HospitalAffl5") && data.get("HospitalAffl5").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("HospitalAffl5","100070"))));
        fileLine.addProperty("WheelChairAccess",data.containsKey("WheelChairAccess") && data.get("WheelChairAccess").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("WheelChairAccess","Y"))));
        fileLine.addProperty("DCHFActiveMembers",data.containsKey("DCHFActiveMembers") && data.get("DCHFActiveMembers").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DCHFActiveMembers",""))));
        fileLine.addProperty("AllianceActiveMembers",data.containsKey("AllianceActiveMembers") && data.get("AllianceActiveMembers").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AllianceActiveMembers",""))));
        fileLine.addProperty("DCHF",data.containsKey("DCHF") && data.get("DCHF").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DCHF","Y"))));
        fileLine.addProperty("Alliance",data.containsKey("Alliance") && data.get("Alliance").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("Alliance","N"))));
        fileLine.addProperty("DCHF21UnderACTMembs",data.containsKey("DCHF21UnderACTMembs") && data.get("DCHF21UnderACTMembs").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DCHF21UnderACTMembs",""))));
        fileLine.addProperty("All21UnderACTMembs",data.containsKey("All21UnderACTMembs") && data.get("All21UnderACTMembs").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("All21UnderACTMembs",""))));
        fileLine.addProperty("DCHFOver21ActMembs",data.containsKey("DCHFOver21ActMembs") && data.get("DCHFOver21ActMembs").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DCHFOver21ActMembs",""))));
        fileLine.addProperty("AllOver21ActMembs",data.containsKey("AllOver21ActMembs") && data.get("AllOver21ActMembs").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("AllOver21ActMembs",""))));
        fileLine.addProperty("NPINumber",data.containsKey("NPINumber") && data.get("NPINumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("NPINumber",RandomStringUtils.random(10,false,true)))));

        return fileLine;
    }


    //    public String providerLineCreatorFromJson(JsonObject providerLine) {
    public String allFileLineCreatorFromJson(String filePath, String sheetName, JsonObject providerLine) {
        int rowNum = 2;
        int colNum = 1;
        if (filePath.equals("src/test/resources/testData/ETL_data/HHW HCC Member Demographic File to EB.xlsx")) {
            rowNum = 3;

        }

        List<String> fields = new ArrayList<>();
        List<String> formats = new ArrayList<>();

        ExcelReader exc = new ExcelReader(filePath);
        System.out.println("reading this file" + filePath);
        System.out.println("Excel Data is : " + exc.getCellData(sheetName, 5, 5));
        while (exc.getCellData(sheetName, rowNum, colNum) != "") {
            fields.add(exc.getCellData(sheetName, rowNum, colNum));
            formats.add(exc.getCellData(sheetName, rowNum, colNum + 1));
            ++rowNum;
        }

        String line = "";

        for (int i = 0; i < fields.size(); i++) {
            System.out.println("field:" + fields.get(i));
//            if (fields.get(i).equals("empty")) {
//                line += String.format("%-" + formats.get(i) + "s", "");
//            } else {
//                System.out.println(providerLine.get(fields.get(i)).getAsString());
            line += String.format("%-" + formats.get(i) + "s", providerLine.get(fields.get(i)).getAsString());

//            }
            System.out.println("line is:" + line);
        }
        return line.trim();

//        return String.format("%-20s", providerLine.get("stateProviderId").getAsString()) + " " +
//                String.format("%-15s", providerLine.get("groupId").getAsString()) + " " +
//                String.format("%-38s", providerLine.get("groupName").getAsString()) + " " +
//                String.format("%-15s", providerLine.get("planCode").getAsString()) + " " +
//                String.format("%-2s", providerLine.get("countyCd").getAsString()) + " " +
//                String.format("%-25s", providerLine.get("firstName").getAsString()) +
//                String.format("%-13s", providerLine.get("lastName").getAsString()) +
//                String.format("%-1s", providerLine.get("middleName").getAsString()) + " " +
//                String.format("%-30s", providerLine.get("addressLine1").getAsString()) + " " +
//                String.format("%-30s", providerLine.get("addressLine2").getAsString()) + " " +
//                String.format("%-15s", providerLine.get("city").getAsString()) + " " +
//                String.format("%-2s", providerLine.get("state").getAsString()) + " " +
//                String.format("%-5s", providerLine.get("zipCode").getAsString()) + " " +
//                String.format("%-4s", providerLine.get("zip4").getAsString()) + " " +
//                String.format("%-10s", providerLine.get("phoneNumber").getAsString()) + " " +
//                String.format("%-3s", providerLine.get("primarySpecialityCd").getAsString()) + " " +
//                String.format("%-2s", providerLine.get("providerTypeCd").getAsString()) + " " +
//                String.format("%-3s", providerLine.get("specialityCd").getAsString()) + " " +
//                String.format("%-9s", providerLine.get("panelSize").getAsString()) + " " +
//                String.format("%-9s", providerLine.get("panelCount").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("panelHoldCd").getAsString()) + " " +
//                String.format("%-6s", providerLine.get("upin").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("classificationCd").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("pcpFlag").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("subProgramTypeCd").getAsString()) + " " +
//                String.format("%-8s", providerLine.get("effectiveStartDate").getAsString()) + " " +
//                String.format("%-8s", providerLine.get("effectiveEndDate").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("admitPrivileges").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("deliveryPrivileges").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("acceptObInd").getAsString()) + " " +
//                String.format("%-2s", providerLine.get("ageRange").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("sexLimitsCd").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("allWomanInd").getAsString()) + " " +
//                String.format("%-1s", providerLine.get("stateRegionCd").getAsString());
    }

    public String providerLineCreator(Map<String, String> data) {
        return String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("stateProviderId", "stateProviderId::"))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("groupId", "providerGroupId::"))) + " " +
                String.format("%-38s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("groupName", "BLUE SKY INC"))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("planCode", "400752220"))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("countyCd", "45"))) + " " +
                String.format("%-25s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("firstName", "name::"))) +
                String.format("%-13s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("lastName", "name::"))) +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("middleName", "D"))) + " " +
                String.format("%-30s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("addressLine1", "address::"))) + " " +
                String.format("%-30s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("addressLine2", " "))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("city", "Indianapolis"))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("state", "IN"))) + " " +
                String.format("%-5s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("zipCode", "46204"))) + " " +
                String.format("%-4s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("zip4", "1034"))) + " " +
                String.format("%-10s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("phoneNumber", "phone::"))) + " " +
                String.format("%-3s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("primarySpecialityCd", "316"))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("providerTypeCd", "31"))) + " " +
                String.format("%-3s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("specialityCd", "   "))) + " " +
                String.format("%-9s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelSize", "1000"))) + " " +
                String.format("%-9s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelCount", "10"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelHoldCd", "N"))) + " " +
                String.format("%-6s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("upin", "C24211"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("classificationCd", "R"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("pcpFlag", "Y"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("subProgramTypeCd", "H"))) + " " +
                String.format("%-8s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("effectiveStartDate", "20180101"))) + " " +
                String.format("%-8s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("effectiveEndDate", "22991231"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("admitPrivileges", "P"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("deliveryPrivileges", "A"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("acceptObInd", "N"))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ageRange", "EE"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("sexLimitsCd", "B"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("allWomanInd", "N"))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("stateRegionCd", "S")));
    }

    public String providerLineUpdater(String inputLine, Map<String, String> data) {
        return String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("stateProviderId", inputLine.substring(0, 15)))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("groupId", inputLine.substring(16, 31)))) + " " +
                String.format("%-38s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("groupName", inputLine.substring(32, 70)))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("planCode", inputLine.substring(71, 86)))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("countyCd", inputLine.substring(87, 89)))) + " " +
                String.format("%-25s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("firstName", inputLine.substring(90, 115)))) +
                String.format("%-13s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("lastName", inputLine.substring(115, 128)))) +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("middleName", inputLine.substring(128, 129)))) + " " +
                String.format("%-30s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("addressLine1", inputLine.substring(130, 160)))) + " " +
                String.format("%-30s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("addressLine2", inputLine.substring(161, 191)))) + " " +
                String.format("%-15s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("city", inputLine.substring(192, 207)))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("state", inputLine.substring(208, 210)))) + " " +
                String.format("%-5s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("zipCode", inputLine.substring(211, 216)))) + " " +
                String.format("%-4s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("zip4", inputLine.substring(217, 221)))) + " " +
                String.format("%-10s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("phoneNumber", inputLine.substring(222, 232)))) + " " +
                String.format("%-3s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("primarySpecialityCd", inputLine.substring(233, 236)))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("providerTypeCd", inputLine.substring(237, 239)))) + " " +
                String.format("%-3s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("specialityCd", inputLine.substring(240, 243)))) + " " +
                String.format("%-9s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelSize", inputLine.substring(244, 253)))) + " " +
                String.format("%-9s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelCount", inputLine.substring(254, 263)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("panelHoldCd", inputLine.substring(264, 265)))) + " " +
                String.format("%-6s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("upin", inputLine.substring(266, 272)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("classificationCd", inputLine.substring(273, 274)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("pcpFlag", inputLine.substring(275, 276)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("subProgramTypeCd", inputLine.substring(277, 278)))) + " " +
                String.format("%-8s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("effectiveStartDate", inputLine.substring(279, 287)))) + " " +
                String.format("%-8s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("effectiveEndDate", inputLine.substring(288, 296)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("admitPrivileges", inputLine.substring(297, 298)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("deliveryPrivileges", inputLine.substring(299, 300)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("acceptObInd", inputLine.substring(301, 302)))) + " " +
                String.format("%-2s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ageRange", inputLine.substring(303, 305)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("sexLimitsCd", inputLine.substring(306, 307)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("allWomanInd", inputLine.substring(308, 309)))) + " " +
                String.format("%-1s", API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("stateRegionCd", inputLine.substring(310, 311))));
    }

    public String genRandomFirstNameETL() {
        return faker.name().firstName();
    }
    public String genRandomLastNameETL() {
        return faker.name().lastName();
    }
    public String genRandomAddress1(){return faker.address().streetAddress();}
    public String genNewbornBirthDate(){
        birthday= String.valueOf(LocalDate.now().minusDays(new Random().nextInt(200))).replace("-","");
        return birthday;
    }

    public void createFileForS3(String fileName, String text) {
        try {
            String path = new File("target/" + fileName).getCanonicalPath();
            FileWriter myWrite = new FileWriter(path);
            myWrite.write(text);
            myWrite.close();
            System.out.println("file " + fileName + " created");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}


