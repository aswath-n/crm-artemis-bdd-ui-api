package com.maersk.crm.utilities.etl_util;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;
import java.util.ArrayList;

public class EDIUtils extends CRMUtilities implements ApiBase {

    private static EDIUtils instance;
    EDIUtils(){
        instance=this;
    }

    public static String newLine = System.getProperty("line.separator");
    public static String fileText834="";
    public static Boolean headerExists=false;
    public static ArrayList<String> records = new ArrayList<String>();
    public static String subscriberNumber;
    public static String eligibilityStartDate;
    public static String address;
    public static String memberMedicaidID;
    public static String caseNumber;
    public static String transactionType;
    public static String priorIdentifierNumber;
    public static String programCode;
    public static String disenrollmentReasonCode;
    public static String maintenanceEffectiveDate;
    public static String planEnrollmentStartDate;
    public static String planEnrollmentEndDate;
    public static String eligibilityEndDate;
    public static String lastName;
    public static String firstName;
    public static String middleNameOrInitial;
    public static String memberSSN;
    public static String phoneNumber;
    public static String cityName;
    public static String zipCode;
    public static String stateOrProvince;
    public static String zipFour;
    public static String memberQuadrants;
    public static String memberBirthDate;
    public static String genderCode;
    public static String raceCode;
    public static String languageCode;
    public static String actionCode;
    public static String sourceCode;
    public static String planCode;
    public static String numberOfISegment;
    public static String fileType;
    public static String monthCode;
    public static String errorCode;
    public static String ediHeader;

    public static String originalEdiHeader = "" +
            "ISA*00*          *00*          *ZZ*100000         *ZZ*91756          *220809*0035*^*00501*003558447*0*T*:~" +
            "GS*BE*77033*91756*20220809*003509*1*X*005010X220A1~" + newLine +
            "ST*834*000000001*005010X220A1~" + newLine +
            "BGN*00*{FileType}*20220809*00344075*ED***4~" + newLine +
            "REF*38*000091756~" + newLine +
            "DTP*382*D8*20220808~" + newLine +
            "N1*P5*DC MEDICAID*FI*536011131~" + newLine +
            "N1*IN*POLICY-STUDIES, INC.*FI*123456789~" + newLine +
            "N1*BO*POLICY-STUDIES, INC.*94*POLICY-STUDIES, INC.~";
    public static String ediTemplate() {
        return  "" +
                "INS*Y*18*030*XN*A***FT~" + newLine +
                "REF*0F*{SubscriberNumber}~  " + newLine +
                "REF*1L*{MemberMedicateID}~  " + newLine +
                "REF*ZZ*SRC={SourceCode}ERR={ErrorCode}MON={MonthCode}ACT={ActionCode}~" + newLine +
                "REF*3H*{CaseNumber}{TransactionType}000~ " + newLine +
                "REF*Q4*{PriorIdentifierNumber}~    " + newLine +
                "REF*DX*PGM={ProgramCode}DISEN={DisenrollmentReasonCode}CAT=  AGE=~" + newLine +
                "DTP*303*D8*{MaintenanceEffectiveDate}~" + newLine +
                "DTP*356*D8*{PlanEnrollmentStartDate}~" + newLine +
                "DTP*357*D8*{PlanEnrollmentEndDate}~" + newLine +
                "DTP*473*D8*{EligibilityStartDate}~" + newLine +
                "DTP*474*D8*{EligibilityEndDate}~" + newLine +
                "NM1*IL*1*{LastName}*{FirstName}*{MiddleNameOrInitial}***34*{MemberSSN}~" + newLine +
                "PER*IP**TE*{PhoneNumber}~" + newLine +
                "N3*{StreetAdress}~" + newLine +
                "N4*{CityName}*{StateOrProvince}*{ZipCode}{0000}**60*{MemberQuadrant}~" + newLine +
                "DMG*D8*{MemberBirthDate}*{GenderCode}**{RaceCode}~" + newLine +
                "LUI*LE*{LanguageCode}**7~" + newLine +
                "HD*030**HMO*C1EB*IND~" + newLine +
                "DTP*303*D8*20230329~" + newLine +
                "LX*1~" + newLine +
                "NM1*Y2*2******SV*{PlanCode}*72~";
    }
    public static String ediFooter = "" +
            "SE*{NumberOfISegment}*000000001~" + newLine +
            "GE*1*1~" + newLine +
            "IEA*1*003558447~" + newLine;

    public String makeTemplate(Map<String, String> data) {

        address=(data.containsKey("StreetAdress") && data.get("StreetAdress").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("StreetAdress","address::" ))));
        subscriberNumber=(data.containsKey("SubscriberNumber") && data.get("SubscriberNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("SubscriberNumber",RandomStringUtils.random(8,false,true)))));
        memberMedicaidID=(data.containsKey("MemberMedicateID") && data.get("MemberMedicateID").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberMedicateID",subscriberNumber))));
        caseNumber=(data.containsKey("CaseNumber") && data.get("CaseNumber").equals("null") ? "        " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CaseNumber", RandomStringUtils.random(8,false,true)))));
        transactionType=(data.containsKey("TransactionType") && data.get("TransactionType").equals("null") ? " " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("TransactionType","A"))));
        priorIdentifierNumber=(data.containsKey("PriorIdentifierNumber") && data.get("PriorIdentifierNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PriorIdentifierNumber",RandomStringUtils.random(8,false,true)))));
        programCode=(data.containsKey("ProgramCode") && data.get("ProgramCode").equals("null") ? "    " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ProgramCode","221Q"))));
        disenrollmentReasonCode=(data.containsKey("DisenrollmentReasonCode") && data.get("DisenrollmentReasonCode").equals("null") ? "  " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("DisenrollmentReasonCode","1E"))));
        maintenanceEffectiveDate= (data.containsKey("MaintenanceEffectiveDate") && !data.get("MaintenanceEffectiveDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("MaintenanceEffectiveDate")) : "");
        planEnrollmentStartDate= (data.containsKey("PlanEnrollmentStartDate") && !data.get("PlanEnrollmentStartDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("PlanEnrollmentStartDate")) : "");
        planEnrollmentEndDate=  (data.containsKey("PlanEnrollmentEndDate") && !data.get("PlanEnrollmentEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("PlanEnrollmentEndDate")) : "");
        eligibilityStartDate=(data.containsKey("EligibilityStartDate")) && String.valueOf(data.get("EligibilityStartDate")).matches("(.*)\\.(.*)") ? String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("EligibilityStartDate",""))) : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("EligibilityStartDate"));
        eligibilityEndDate= (data.containsKey("EligibilityEndDate") && !data.get("EligibilityEndDate").equals("null") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("EligibilityEndDate")) : "        ");
        lastName=(data.containsKey("LastName") && data.get("LastName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LastName","name::"))));
        firstName=(data.containsKey("FirstName") && data.get("FirstName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FirstName","name::"))));
        middleNameOrInitial = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MiddleNameOrInitial ", "")));
        memberSSN=(data.containsKey("MemberSSN") && data.get("MemberSSN").equals("null") ? "         " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberSSN", RandomStringUtils.random(9,false,true)))));
        phoneNumber=(data.containsKey("PhoneNumber") && data.get("PhoneNumber").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PhoneNumber","phone::" ))));
        cityName=(data.containsKey("CityName") && data.get("CityName").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("CityName","Washington" ))));
        stateOrProvince=(data.containsKey("StateOrProvince") && data.get("StateOrProvince").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("StateOrProvince","DC" ))));
        zipCode=(data.containsKey("ZipCode") && data.get("ZipCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ZipCode",RandomStringUtils.random(5, false, true)))));
        zipFour = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("0000", "0000")));
        memberQuadrants=(data.containsKey("MemberQuadrant") && data.get("MemberQuadrant").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberQuadrant", "NE"))));
        memberBirthDate=(data.containsKey("MemberBirthDate") && data.get("MemberBirthDate").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MemberBirthDate",  API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateRandomDOB()))));
        genderCode=(data.containsKey("GenderCode") && data.get("GenderCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("GenderCode",  API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().generateGender()))));
        raceCode=(data.containsKey("RaceCode") && data.get("RaceCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("RaceCode",  "C"))));
        languageCode = String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("LanguageCode", "ENG")));
        planCode=(data.containsKey("PlanCode") && data.get("PlanCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("PlanCode",  "087358900"))));
        actionCode=(data.containsKey("ActionCode") && data.get("ActionCode").equals("null") ? "" : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ActionCode","E"))));
        sourceCode=(data.containsKey("SourceCode") && data.get("SourceCode").equals("null") ? " " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("SourceCode","M"))));
        monthCode=(data.containsKey("MonthCode") && data.get("MonthCode").equals("null") ? " " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("MonthCode","A"))));
        errorCode=(data.containsKey("ErrorCode") && data.get("ErrorCode").equals("null") ? " " : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("ErrorCode","   "))));


        String template = ediTemplate();
        template = template.replace("{SubscriberNumber}", subscriberNumber);
        template = template.replace("{MemberMedicateID}", memberMedicaidID);
        template = template.replace("{CaseNumber}", caseNumber);
        template = template.replace("{TransactionType}", transactionType);
        template = template.replace("{PriorIdentifierNumber}", priorIdentifierNumber);
        template = template.replace("{ProgramCode}", programCode);
        template = template.replace("{DisenrollmentReasonCode}", disenrollmentReasonCode);
        template = template.replace("{MaintenanceEffectiveDate}", maintenanceEffectiveDate);
        template = template.replace("{PlanEnrollmentStartDate}", planEnrollmentStartDate);
        template = template.replace("{PlanEnrollmentEndDate}", planEnrollmentEndDate);
        template = template.replace("{EligibilityStartDate}", eligibilityStartDate);
        template = template.replace("{EligibilityEndDate}", eligibilityEndDate);
        template = template.replace("{LastName}", lastName);
        template = template.replace("{FirstName}", firstName);
        template = template.replace("{MiddleNameOrInitial}", middleNameOrInitial);
        template = template.replace("{MemberSSN}", memberSSN);
        template = template.replace("{PhoneNumber}", phoneNumber);
        template = template.replace("{StreetAdress}",address);
        template = template.replace("{CityName}", cityName);
        template = template.replace("{StateOrProvince}", stateOrProvince);
        template = template.replace("{ZipCode}", zipCode);
        template = template.replace("{0000}", zipFour);
        template = template.replace("{MemberQuadrant}", memberQuadrants);
        template = template.replace("{MemberBirthDate}", memberBirthDate);
        template = template.replace("{GenderCode}", genderCode);
        template = template.replace("{RaceCode}", raceCode);
        template = template.replace("{PlanCode}", planCode);
        template = template.replace("{LanguageCode}", languageCode);
        template = template.replace("{ActionCode}", actionCode);
        template = template.replace("{MonthCode}", monthCode);
        template = template.replace("{SourceCode}", sourceCode);
        template = template.replace("{ErrorCode}", errorCode);
        if(data.get(planEnrollmentStartDate) == null){
            template=template.replace("DTP*356*D8*~" + newLine  ,"");
        }
        if (data.get(planEnrollmentEndDate) == null){
                template=template.replace( "DTP*357*D8*~" + newLine  ,"");
            }
         if(data.get(planCode) == null){
            template=template.replace( newLine + "NM1*Y2*2******SV**72~",  "");
        }

            return template;
    }
    public String makeFooter(Map<String, String> data){
        numberOfISegment= String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("NumberOfISegment", "4")));
        ediFooter = ediFooter.replace("{NumberOfISegment}", numberOfISegment);
        return ediFooter;
    }
    public String makeHeader(Map<String, String> data){
        ediHeader=originalEdiHeader;
        fileType= String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.getOrDefault("FileType", "2439999982"))); //**for response file pass PSI.202208 as value in feature file
        ediHeader = ediHeader.replace("{FileType}", fileType);
        return ediHeader;
    }
    public static JsonObject makeTemplate2(Map<String, String> data) {
        System.out.println("make templete data:"+data);
        JsonObject fileLine = new JsonObject();
        fileLine.addProperty("SubscriberNumber", subscriberNumber);
        fileLine.addProperty("MemberMedicateID", memberMedicaidID);
        fileLine.addProperty("CaseNumber", caseNumber);
        fileLine.addProperty("TransactionType", transactionType);
        fileLine.addProperty("PriorIdentifierNumber", priorIdentifierNumber);
        fileLine.addProperty("ProgramCode", programCode);
        fileLine.addProperty("DisenrollmentReasonCode", disenrollmentReasonCode);
        fileLine.addProperty("MaintenanceEffectiveDate", maintenanceEffectiveDate);
        fileLine.addProperty("PlanEnrollmentStartDate", planEnrollmentStartDate);
        fileLine.addProperty("PlanEnrollmentEndDate", planEnrollmentEndDate);
        fileLine.addProperty("EligibilityStartDate", eligibilityStartDate);
        fileLine.addProperty("EligibilityEndDate", eligibilityEndDate);
        fileLine.addProperty("LastName", lastName);
        fileLine.addProperty("FirstName", firstName);
        fileLine.addProperty("MiddleNameOrInitial", middleNameOrInitial);
        fileLine.addProperty("{MemberSSN}", memberSSN);
        fileLine.addProperty("PhoneNumber", phoneNumber);
        fileLine.addProperty("StreetAdress", address);
        fileLine.addProperty("StateOrProvince",stateOrProvince);
        fileLine.addProperty("CityName", cityName);
        fileLine.addProperty("ZipCode", zipCode);
        fileLine.addProperty("0000", zipFour);
        fileLine.addProperty("MemberQuadrant", memberQuadrants);
        fileLine.addProperty("MemberBirthDate", memberBirthDate);
        fileLine.addProperty("GenderCode", genderCode);
        fileLine.addProperty("RaceCode", raceCode);
        fileLine.addProperty("PlanCode", planCode);
        fileLine.addProperty("LanguageCode", languageCode);
        fileLine.addProperty("NumberOfISegment", numberOfISegment);
        fileLine.addProperty("FileType",fileType);
        fileLine.addProperty("SourceCode",fileType);
        fileLine.addProperty("MonthCode",fileType);
        fileLine.addProperty("ActionCode",fileType);
        fileLine.addProperty("ErrorCode",fileType);

        return fileLine;
    }
    public  ArrayList<String> makeRecords(Map<String,String> data) {
        if(!headerExists){records.add(ediHeader);headerExists=true;}
        records.add(makeTemplate(data));
        System.out.println("records = " + records);
        return records;
    }
}
