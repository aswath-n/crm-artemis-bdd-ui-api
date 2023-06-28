package com.maersk.crm.utilities.ee_data_module;

import com.maersk.crm.api_step_definitions.APIConsumerPopulationDmnController;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.en.Given;

import java.util.HashMap;
import java.util.Map;

public class APIUtilitiesForEEScenarios extends CRMUtilities implements ApiBase {

    APIConsumerPopulationDmnController acpdc = new APIConsumerPopulationDmnController();
    Map<String, String> data = new HashMap<>();
    private static String caseIdentificationNum;

    @Given("I create consumer of MEDICAID-GENERAL population with eligibility")
    public void i_create_consumer_of_MEDICAID_GENERAL_population_with_eligibility() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("MEDICAID-GENERAL", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }


    @Given("I create consumer of MEDICAID-GENERAL population with eligibility and enrollment")
    public void i_create_consumer_of_MEDICAID_GENERAL_population_with_eligibility_and_enrollment() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("MEDICAID-GENERAL", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        acpdc.initiateEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }




    @Given("I create consumer of PREGNANT-WOMEN population with eligibility")
    public void i_create_consumer_of_PREGNANT_WOMEN_population_with_eligibility() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("PREGNANT-WOMEN", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("programCode","H");
        data.put("subProgramTypeCode","MEDICAIDMCHB");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }


    @Given("I create consumer of PREGNANT-WOMEN population with eligibility and enrollment")
    public void i_create_consumer_of_PREGNANT_WOMEN_population_with_eligibility_and_enrollment() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("PREGNANT-WOMEN", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("programCode","H");
        data.put("subProgramTypeCode","MEDICAIDMCHB");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }





    @Given("I create consumer of NEWBORN population with eligibility")
    public void i_create_consumer_of_NEWBORN_population_with_eligibility() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("NEWBORN", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }


    @Given("I create consumer of NEWBORN population with eligibility and partial enrollment")
    public void i_create_consumer_of_NEWBORN_population_with_eligibility_and_enrollment() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo","new");
        acpdc.i_created_a_consumer_for_eligibility_record_v2("NEWBORN", data);
        caseIdentificationNum = acpdc.getCaseIdentificationNo();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }

    @Given("I add consumer of MEDICAID-GENERAL population with eligibility to the existing case")
    public void i_add_consumer_of_MEDICAID_GENERAL_population_with_eligibility_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("MEDICAID-GENERAL", data);
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }


    @Given("I add consumer of MEDICAID-GENERAL population with eligibility and enrollment to the existing case")
    public void i_add_consumer_of_MEDICAID_GENERAL_population_with_eligibility_and_enrollment_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("MEDICAID-GENERAL", data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }




    @Given("I add consumer of PREGNANT-WOMEN population with eligibility to the existing case")
    public void i_add_consumer_of_PREGNANT_WOMEN_population_with_eligibility_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("PREGNANT-WOMEN", data);
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("programCode","H");
        data.put("subProgramTypeCode","MEDICAIDMCHB");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }

    @Given("I add consumer of PREGNANT-WOMEN population with eligibility and enrollment to the existing case")
    public void i_add_consumer_of_PREGNANT_WOMEN_population_with_eligibility_and_enrollment_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("PREGNANT-WOMEN", data);
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("programCode","H");
        data.put("subProgramTypeCode","MEDICAIDMCHB");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }

    @Given("I add consumer of NEWBORN population with eligibility to the existing case")
    public void i_add_consumer_of_NEWBORN_population_with_eligibility_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("NEWBORN", data);
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
    }


    @Given("I add consumer of NEWBORN population with eligibility and partial enrollment to the existing case")
    public void i_add_consumer_of_NEWBORN_population_with_eligibility_and_enrollment_to_the_existing_case() {
        getToken();
        data.clear();
        data.put("caseIdentificationNo",caseIdentificationNum);
        acpdc.i_created_a_consumer_for_eligibility_record_v2("NEWBORN", data);
        data.put("isEligibilityRequired", "yes");
        data.put("isEnrollemntRequired", "no");
        data.put("eligibilityStartDate", "1stDayofLastMonth");
        data.put("enrollmentStartDate", "1stDayofLastMonth");
        data.put("recordId", "3");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.initiateEnrollmentEligibilityCreateApi();
        acpdc.runEnrollmentEligibilityCreateApi();
        data.put("isEligibilityRequired", "no");
        data.put("isEnrollemntRequired", "yes");
        data.put("isEnrollmentProviderRequired", "no");
        data.put("recordId", "4");
        acpdc.createEligibilityAndEnrollemntRecord(data);
        acpdc.runEnrollmentEligibilityCreateApi();
    }
    public void getToken(){
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal() == null || API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal() == ""){
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName1("BLCRM");
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getOauthToken("CRM", "BLCRM");
        }
    }

}
