package com.maersk.crm.api_step_definitions;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.maersk.crm.etl_step_definitions.EE_ETL_StepDefs;
import com.maersk.crm.pages.crm.CRMContactRecordDashboardPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMManualCaseConsumerSearchPage;
import com.maersk.crm.step_definitions.CRMEnrollmentUpdateStepDef;
import com.maersk.crm.utilities.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static org.testng.Assert.*;

public class APIConsumerPopulationDmnController extends CRMUtilities implements ApiBase {

    private String enrollmentBaseUri = ConfigurationReader.getProperty("apiEligibilityURI");
    private String createEnrollmentAndEligibilityEndPoint = "mars/eb/enrollment/start";
    private String enrollmentStatus = "mars/eb/enrollments/status/SELECTION_MADE?from=0&size=100";
    private String enrollmentUpdateBase = ConfigurationReader.getProperty("apiEnrollment");
    private String updateEnrollment = "mars/eb/enrollment/new";
    private String updateOutbound = "mars/eb/enrollment";
    private String updateOutboundEnrollments = "mars/eb/enrollments";
    private String transferOutboundEnrollments = "mars/eb/enrollments/transfer";
    private String otherSegments = "mars/eb/enrollments/otherSegments";

    //    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    Api_Storage stg = Api_Storage.getInstance();

    private static final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal<String> consumerSSN = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerId = ThreadLocal.withInitial(() -> "6271");
    public final ThreadLocal<String> caseCorrelationId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerDateOfBirth = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerFirstName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> consumerLastName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> externalConsumerId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> caseIdentificationNo = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<JsonObject> caseLoaderReq = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal<JsonObject> caseLoaderResponse = ThreadLocal.withInitial(JsonObject::new);
    public static final ThreadLocal<String> externalConsumerIdToOverride = ThreadLocal.withInitial(String::new);

    public static final ThreadLocal<String> subProgramTypeCode = ThreadLocal.withInitial(String::new);
    final ThreadLocal<HashMap<String, HashMap<String, String>>> regionDetails = ThreadLocal.withInitial(HashMap::new);
    CRMContactRecordDashboardPage contactRecordDashboard = new CRMContactRecordDashboardPage();
    CRMContactRecordDashboardPage dashboardPage = new CRMContactRecordDashboardPage();
    public final ThreadLocal<String> newPlanCode = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> newPlanName = ThreadLocal.withInitial(String::new);
    private CRMManualCaseConsumerSearchPage manualCaseConsumerSearchPage = new CRMManualCaseConsumerSearchPage();
    private CRMContactRecordUIPage crmContactRecordUIPage1 = new CRMContactRecordUIPage();

    public void setRegionDetails() {
        HashMap<String, String> Atlanta = new HashMap<String, String>();
        Atlanta.put("state", "GA");
        Atlanta.put("county", "Columbia");
        Atlanta.put("city", "Augusta");
        Atlanta.put("zip", "30917");
        Atlanta.put("countyCode", "1236");
        regionDetails.get().put("Atlanta", Atlanta);
        HashMap<String, String> Central = new HashMap<String, String>();
        //details.put("state", "");
        Central.put("county", "Crawford");
        Central.put("city", "Knoxville");
        Central.put("zip", "31050");
        Central.put("countyCode", "1261");
        regionDetails.get().put("Central", Central);
        HashMap<String, String> East = new HashMap<String, String>();
        East.put("state", "GA");
        East.put("county", "Columbia");
        East.put("city", "Evans");
        East.put("zip", "30809");
        East.put("countyCode", "036");
        regionDetails.get().put("East", East);
        HashMap<String, String> Southwest = new HashMap<String, String>();
        Southwest.put("state", "");
        Southwest.put("county", "Clinch");
        Southwest.put("city", "Fargo");
        Southwest.put("zip", "31631");
        Southwest.put("countyCode", "1366");
        regionDetails.get().put("Southwest", Southwest);
        HashMap<String, String> Southeast = new HashMap<String, String>();
        Southeast.put("state", "");
        Southeast.put("county", "Montgomery");
        Southeast.put("city", "Tarrytown");
        Southeast.put("zip", "30470");
        Southeast.put("countyCode", "1352");
        regionDetails.get().put("Southeast", Southeast);
        HashMap<String, String> North = new HashMap<String, String>();
        North.put("county", "Dade");
        North.put("city", "Wildwood");
        North.put("zip", "30757");
        North.put("countyCode", "1310");
        regionDetails.get().put("North", North);
        HashMap<String, String> Invalid = new HashMap<String, String>();
        Invalid.put("county", "HOUSTON");
        Invalid.put("city", "Arabi");
        Invalid.put("zip", "58078");
        regionDetails.get().put("Invalid", Invalid);
        HashMap<String, String> Statewide = new HashMap<String, String>();
        Statewide.put("state", "Indiana");
        Statewide.put("county", "Adams");
        Statewide.put("city", "Preble");
        Statewide.put("zip", "46782");
        regionDetails.get().put("Statewide", Statewide);
        HashMap<String, String> Northeast = new HashMap<String, String>();
        Northeast.put("state", "DC");
        Northeast.put("county", "Northeast");
        Northeast.put("city", "Washington DC");
        Northeast.put("zip", "20510");
        Northeast.put("countyCode", "002");
        regionDetails.get().put("Northeast", Northeast);

    }

    public HashMap<String, String> getRegionDetails(String regionName) {
        return regionDetails.get().get(regionName);
    }

    @Given("I created a consumer with {string} ,{string}, {string}, {string}")
    public void i_created_a_consumer_for_eligibility_record(String dob, String PregnancyIndicator, String pregnancyDueDate, String serviceRegion) {
        setRegionDetails();
        APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
        caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");
        JsonObject caseLoaderReq = caseLoader.generateCaseLoaderRequest();
        //Modify the details
        String dateOfBirth = "";
        int numOfMonths = 0;
        int numOfYears = 0;
        int numOfDays = 0;
        if (dob.endsWith("months"))
            numOfMonths = Integer.parseInt(dob.split(" ")[0]);
        if (dob.endsWith("years"))
            numOfYears = Integer.parseInt(dob.split(" ")[0]);
        if (dob.endsWith("days"))
            numOfDays = Integer.parseInt(dob.split(" ")[0]);
        dateOfBirth = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", -numOfDays, -numOfMonths, -numOfYears);
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", dateOfBirth);
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyInd", PregnancyIndicator);
        if (!pregnancyDueDate.equalsIgnoreCase((String) null)) {
            if (pregnancyDueDate.endsWith("months"))
                numOfMonths = Integer.parseInt(pregnancyDueDate.split(" ")[0]);
            else
                numOfMonths = 2;
            pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, numOfMonths, 0);
        }
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyDueDate", pregnancyDueDate);

        //Update Address
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCity", getRegionDetails(serviceRegion).get("city"));
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCounty", getRegionDetails(serviceRegion).get("county"));
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressState", getRegionDetails(serviceRegion).get("state"));
        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressZip", getRegionDetails(serviceRegion).get("zip"));
        caseLoader.setRequestParams(caseLoaderReq);
        caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
        consumerSSN.set(caseLoader.getConsumerSSN());
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN.get());
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        consumerSearch.runConsumerSearchAPI("consumerSSN");
        consumerId.set(consumerSearch.getConsumerIdFromSearchResults());

        caseCorrelationId.set(caseLoaderReq.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").get("correlationId").getAsString());
        APICaseRestController caseController = new APICaseRestController();
        caseController.setCaseCorrelationId(caseCorrelationId.get());
        caseController.initiateSearchCaseRecords();
        caseController.searchCaseByCorrelationId();
        caseId.set(caseController.getCreatedCaseId());

        APIEnrollmentController enrollmentController = new APIEnrollmentController();
        enrollmentController.setConsumerId(consumerId.get());

    }

    @When("I create eligibility record for bpm process with {string}")
    public void createEligibilityRecordWithProgramTyp(String programType) {
        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.initiatedEligibilityCreateAPI();
        eligibility.setConsumerId(this.consumerId.get());
        eligibility.setProgramType(programType);
        eligibility.runEligibilityCreateAPI();

        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());

    }

    @When("I create eligibility record for bpm process with other segments {string} and {string}")
    public void createEligibilityRecord(String otherSegments, String categoryCode) {
        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.setCaseId(caseId.get());
        eligibility.initiatedEligibilityCreateAPI();
        eligibility.setConsumerId(this.consumerId.get());
        eligibility.runEligibilityCreateAPI(otherSegments, categoryCode);

        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());

    }

    @When("I create eligibility record for bpm process with {string} timeframe")
    public void createEligibilityRecordWithDates(String eligibilityType) {
        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.initiatedEligibilityCreateAPI();
        eligibility.setConsumerId(this.consumerId.get());
        eligibility.setEligibilityType(eligibilityType);
        eligibility.runEligibilityCreateAPI();

        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());

    }

    @Given("I created a consumer with population type {string}")
    public void i_created_a_consumer_for_eligibility_record(String populationType) {
        i_created_a_consumer_for_eligibility_record_v2(populationType, new HashMap<String, String>());
    }

    @Given("I created a consumer with population type {string} with data")
    public void i_created_a_consumer_for_eligibility_record_v2(String populationType, Map<String, String> data) {
        setRegionDetails();
        String dob = "";
        String PregnancyIndicator = "";
        String pregnancyDueDate = "";
        String serviceRegion = "";
        String countyCode = "";
        switch (populationType) {
            case "NEWBORN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, -8, 0);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                countyCode = "1234";
                break;
            case "PREGNANT-WOMEN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                // serviceRegion = "Atlanta";
                serviceRegion = "East";
                break;
            case "PREGNANT-WOMEN-NORTH":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                serviceRegion = "North";
                break;
            case "MEDICAID-GENERAL":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "MEDICAID-GENERAL-ATLANTA":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                break;
            case "FOSTER-CARE":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "CUSTOM":
                if (data.containsKey("age")) {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, -1, -(Integer.parseInt(data.get("age"))));
                } else {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                }
                if (data.containsKey("pregnancyIndicator") && data.get("pregnancyIndicator").equals("true")) {
                    PregnancyIndicator = "true";
                    pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                } else {
                    PregnancyIndicator = "false";
                    pregnancyDueDate = null;
                }
                serviceRegion = data.getOrDefault("serviceRegion", "Atlanta");
                break;
            case "IN-EB-CONSUMER":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -20);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
            case "DC-EB-CONSUMER-below60":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -21);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Northeast";
                break;
            case "DC-EB-CONSUMER-above60":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -70);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
            case "DC-EB-CONSUMER-newborn":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 1, 0, 0);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
        }
        if (!populationType.contains("DC-EB")) {
            consumerDateOfBirth.set(dob);
            APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
            caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");
            if (data.containsKey("newCaseCreation") && data.get("newCaseCreation").equalsIgnoreCase("yes")) {
                caseLoader.resetScenarioName();
            }
            JsonObject caseLoaderReq = caseLoader.generateCaseLoaderRequest();
            consumerFirstName.set(caseLoader.getConsumerFirstName());
            consumerLastName.set(caseLoader.getConsumerLastName());
            externalConsumerId.set(caseLoader.getExternalConsumerId());
            caseIdentificationNo.set(caseLoader.getCaseIdentificationNo());
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", dob);
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyInd", PregnancyIndicator);
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyDueDate", pregnancyDueDate);

            //Update Address
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCity", getRegionDetails(serviceRegion).get("city"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").
                    get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCounty", getRegionDetails(serviceRegion).get("county"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressState", getRegionDetails(serviceRegion).get("state"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressZip", getRegionDetails(serviceRegion).get("zip"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", data.getOrDefault("consumerRole", "Primary Individual"));

            if (populationType.equals("IN-EB-CONSUMER")) {
                caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").addProperty("caseIdentificationNumberType", "State");
            }

            if (data.containsKey("caseIdentificationNo")) {
                if (data.get("caseIdentificationNo").equalsIgnoreCase("new")) {
                    caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
                    System.out.println("NUMBER GENERATED IS : " + caseIdentificationNo.get());
                    caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
                } else if (data.get("caseIdentificationNo") != null && !data.get("caseIdentificationNo").equals("")) {
                    caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").addProperty("caseIdentificationNumber", data.get("caseIdentificationNo"));
                }
            }


            for (String key : data.keySet()) {
                switch (key) {
                    case "requestedBy":
                        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("requestedBy", data.get("requestedBy"));
                        break;
                }
            }

            caseLoader.setRequestParams(caseLoaderReq);
            caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
            waitFor(3);
            consumerSSN.set(caseLoader.getConsumerSSN());
            APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
            consumerSearch.setConsumerSSN(consumerSSN.get());
            consumerSearch.initiatedCaseConsumerContactSearchAPI();
            consumerSearch.runConsumerSearchAPI("consumerSSN");
            consumerId.set(consumerSearch.getConsumerIdFromSearchResults());

            caseCorrelationId.set(caseLoaderReq.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").get("correlationId").getAsString());
            APICaseRestController caseController = new APICaseRestController();
            caseController.setCaseCorrelationId(caseCorrelationId.get());
            caseController.initiateSearchCaseRecords();
            caseController.searchCaseByCorrelationId();
            String tempCaseID = caseController.getCreatedCaseId();
            if (tempCaseID != null)
                caseId.set(tempCaseID);

        } else {
            consumerDateOfBirth.set(dob);
            APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
            caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPIForDC();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE_DC.json");
            if (data.containsKey("newCaseCreation") && data.get("newCaseCreation").equalsIgnoreCase("yes")) {
                caseLoader.resetScenarioName();
            }
            JsonObject caseLoaderReq = caseLoader.generateCaseLoaderRequestForDC();
            consumerFirstName.set(caseLoader.getConsumerFirstName());
            consumerLastName.set(caseLoader.getConsumerLastName());
            externalConsumerId.set(caseLoader.getExternalConsumerId());
            caseIdentificationNo.set(caseLoader.getCaseIdentificationNo());
            caseLoaderReq.getAsJsonArray("consumerRequests").get(0).getAsJsonObject().addProperty("dateOfBirth", dob);
            if (data.containsKey("consumerProfile.type")) {
                caseLoaderReq.getAsJsonArray("consumerRequests").get(0).getAsJsonObject().getAsJsonObject("consumerProfile").addProperty("type", data.get("consumerProfile.type"));
            }


            caseLoader.setRequestParams(caseLoaderReq);
            caseLoader.runCaseLoaderEligibilityEnrollmentAPIForDC();
            waitFor(3);
            consumerId.set(caseLoader.getConsumerIdFromCaseLoaderRun());
            caseId.set(caseLoader.getCaseIdFromCaseLoaderRun());
            caseLoader.initiateSyncContactInfoForDC();
            caseLoader.runSyncContactInfoForDC();
        }

        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());

        APIEEDigitalIntegrationController digitalApi = new APIEEDigitalIntegrationController();
        digitalApi.setConsumerIds(this.consumerId.get());

        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.setConsumerId(this.consumerId.get());

        // this method is going to save consumer info to storage created by Kamil
        // so we can get these info back later when have multiple consumers in same case (for questions ask Sobir)
        if (data.containsKey("saveConsumerInfo")) {
            Api_Body api_req = new EventBuilder()
                    .body("{\"firstName\": \"" + consumerFirstName.get() + "\", \"lastName\": \"" + consumerLastName.get() + "\", " +
                            "\"consumerSSN\": \"" + consumerSSN.get() + "\", \"consumerId\": \"" + consumerId.get() + "\", " +
                            "\"caseCorrelationId\": \"" + caseCorrelationId.get() + "\", \"caseId\": \"" + caseId.get() + "\", " +
                            "\"externalConsumerId\": \"" + externalConsumerId.get() + "\", " +
                            "\"caseIdentificationNumber\": \"" + caseIdentificationNo.get() + "\"}")
                    .name(data.get("saveConsumerInfo"))
                    .build();
            stg.addProduced(api_req);
            System.out.println("api_common.defineValue(" + data.get("saveConsumerInfo") + ".firstName) = " + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("saveConsumerInfo") + ".firstName"));
        }
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    // Method to create Eligibility & Enrollment and start BPM process
    @When("I provide the enrollment and eligibility information to create enrollment")
    public void createEligibilityAndEnrollemntRecord(Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/enrollment/createEligibilityAndEnrollmentWithBpm.json");

        String presentTimeStamp = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        String eligibilityStartDate = data.get("eligibilityStartDate").equals("null")
                ? null : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("eligibilityStartDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");

        String enrollmentStartDate = null;
        if (data.get("enrollmentStartDate").endsWith("startDate")) {
            enrollmentStartDate = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("enrollmentStartDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        } else {
            enrollmentStartDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("enrollmentStartDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        }

        String openEnrollmentStartDate = null;
        String eligibilityEndDate = null;
        String enrollmentEndDate = null;
        String anniversaryDate = null;
        String openEnrollmentEndDate = null;

        if (data.containsKey("eligibilityEndDate") && !data.get("eligibilityEndDate").equals("null"))
            eligibilityEndDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("eligibilityEndDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        if (data.containsKey("enrollmentEndDate") && !data.get("enrollmentEndDate").equals("null"))
            enrollmentEndDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("enrollmentEndDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        if (data.containsKey("anniversaryDate"))
            anniversaryDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("anniversaryDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        if (data.containsKey("openEnrollmentEndDate"))
            openEnrollmentEndDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("openEnrollmentEndDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");
        if (data.containsKey("openEnrollmentStartDate"))
            openEnrollmentStartDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("openEnrollmentStartDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'");

        if (data.containsKey("consumerId")) {
            String id = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId")).toString();
            System.out.println("id = " + id);
            consumerId.set(id.contains(".") ? id.substring(0, id.indexOf(".")) : id);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId",
                    Integer.parseInt(consumerId.get()));
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", Integer.parseInt(consumerId.get()));
        if (data.containsKey("recordId"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("recordId", Integer.parseInt(data.get("recordId")));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("recordId", Integer.parseInt(data.get("recordId")));
        if (data.containsKey("requestedBy"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("requestedBy", Integer.parseInt(data.get("requestedBy")));

        if (data.get("isEligibilityRequired").equalsIgnoreCase("yes")
                || data.get("isEligibilityRequired").equalsIgnoreCase("true")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(consumerId.get()));

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("startDate", eligibilityStartDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("endDate", eligibilityEndDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber.toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("uiid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber.toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("createdOn", presentTimeStamp);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", presentTimeStamp);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("createdBy", "SYSTEM_INTEGRATION");
            if (data.containsKey("programCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("programCode", data.get("programCode"));
            if (data.containsKey("programTypeCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("programTypeCode", data.get("programTypeCode"));
            if (data.containsKey("subProgramTypeCode")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("subProgramTypeCode", data.get("subProgramTypeCode"));
                subProgramTypeCode.set(data.get("subProgramTypeCode"));
            }
            if (data.containsKey("categoryCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("categoryCode", data.get("categoryCode"));
            if (data.containsKey("exemptionCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("exemptionCode", data.get("exemptionCode"));
            if (data.containsKey("eligibilityStatusCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("eligibilityStatusCode",
                        data.get("eligibilityStatusCode").equals("null") ? null : data.get("eligibilityStatusCode"));
            if (data.containsKey("coverageCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("coverageCode", data.get("coverageCode"));
            if (data.containsKey("eligibilityRecordId"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().addProperty("recordId", data.get("eligibilityRecordId"));
            if (data.containsKey("eligibilityEndReason") && (!data.get("eligibilityEndReason").equals("null") || data.get("eligibilityEndReason").isEmpty()))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("eligibilityEndReason", data.get("eligibilityEndReason"));
            if (data.containsKey("aidCategoryCode") && (!data.get("aidCategoryCode").equals("null") || data.get("aidCategoryCode").isEmpty()))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("aidCategoryCode", data.get("aidCategoryCode"));
            if (data.containsKey("genericFieldText1"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("genericFieldText1",
                        data.get("genericFieldText1").equals("null") ? null : data.get("genericFieldText1"));
            if (data.containsKey("genericFieldText2"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("genericFieldText2",
                        data.get("genericFieldText2").equals("null") ? null : data.get("genericFieldText2"));
            if (data.containsKey("fileSource"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("fileSource", data.get("fileSource"));

            if (data.containsKey("otherSegments") && !data.get("otherSegments").isEmpty()) {
                String otherSegments = data.get("otherSegments");
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, Object>>() {
                }.getType();
                HashMap<String, Object> mapObj = gson.fromJson(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject(), type);
                for (String key : otherSegments.split(",")) {
                    if (key.equalsIgnoreCase("coreEligibility"))
                        continue;
                    if (otherSegments.contains(key)) {
                        if (key.equalsIgnoreCase("facilityInfo")) {
                            JsonObject facilityInfo = new JsonObject();
                            facilityInfo.addProperty("startDate", eligibilityStartDate);
                            facilityInfo.addProperty("placementCode", "123Facility");
                            facilityInfo.addProperty("referralCountyCode", "001");
                            facilityInfo.addProperty("endDate", (String) null);
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("facilityInfo", facilityInfo);
                        } else if (key.equalsIgnoreCase("medicareInfo")) {
                            JsonObject medicareInfo = new JsonObject();
                            medicareInfo.addProperty("partAStartDate", eligibilityStartDate);
                            medicareInfo.addProperty("partBStartDate", eligibilityStartDate);
                            medicareInfo.addProperty("partDStartDate", eligibilityStartDate);
                            medicareInfo.addProperty("titleXVIIIStartDate", (String) null);
                            medicareInfo.addProperty("partAEndDate", eligibilityEndDate);
                            medicareInfo.addProperty("partBEndDate", eligibilityEndDate);
                            medicareInfo.addProperty("partDEndDate", eligibilityEndDate);
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("medicareInfo", medicareInfo);
                        } else if (key.equalsIgnoreCase("financialInfo")) {
                            JsonObject financialInfo = new JsonObject();
                            financialInfo.addProperty("caseId", caseId.get());
                            financialInfo.addProperty("startDate", eligibilityStartDate);
                            financialInfo.addProperty("endDate", (String) null);
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("financialInfo", financialInfo);
                        } else if (key.equalsIgnoreCase("longTermCareInfo")) {
                            JsonObject longTermCareInfo = new JsonObject();
                            longTermCareInfo.addProperty("startDate", eligibilityStartDate);
                            longTermCareInfo.addProperty("endDate", (String) null);
                            longTermCareInfo.addProperty("npiId", "string");
                            longTermCareInfo.addProperty("providerName", "string");
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("longTermCareInfo", longTermCareInfo);
                        } else if (key.equalsIgnoreCase("hospiceInfo")) {
                            JsonObject hospiceInfo = new JsonObject();
                            hospiceInfo.addProperty("hospiceIndicator", "test");
                            hospiceInfo.addProperty("startDate", eligibilityStartDate);
                            hospiceInfo.addProperty("endDate", (String) null);
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("hospiceInfo", hospiceInfo);
                        } else if (key.equalsIgnoreCase("specialPopulationInfo")) {
                            JsonObject specialPopulationInfo = new JsonObject();
                            specialPopulationInfo.addProperty("specialEligibilityCode", "test");
                            specialPopulationInfo.addProperty("startDate", eligibilityStartDate);
                            specialPopulationInfo.addProperty("endDate", (String) null);
                            specialPopulationInfo.addProperty("specialPopulationCaseNumber", caseId.get());
                            specialPopulationInfo.addProperty("specialPopulationIndicator", "Y");
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("specialPopulationInfo", specialPopulationInfo);
                        } else if (key.equalsIgnoreCase("waiverInfo")) {
                            JsonObject waiverInfo = new JsonObject();
                            waiverInfo.addProperty("waiverCode", "WAC001");
                            waiverInfo.addProperty("startDate", eligibilityStartDate);
                            waiverInfo.addProperty("endDate", (String) null);
                            waiverInfo.addProperty("waiverCounty", "Adams");
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("waiverInfo", waiverInfo);
                        } else if (key.equalsIgnoreCase("thirdPartyInsuranceInfo")) {
                            JsonObject thirdPartyInsuranceInfo = new JsonObject();
                            thirdPartyInsuranceInfo.addProperty("insuranceCode", "INS002");
                            thirdPartyInsuranceInfo.addProperty("startDate", eligibilityStartDate);
                            thirdPartyInsuranceInfo.addProperty("endDate", (String) null);
                            thirdPartyInsuranceInfo.addProperty("insuranceGroupNumber", caseId.get());
                            thirdPartyInsuranceInfo.addProperty("insurancePolicyHolderName", "Jane Doe");
                            thirdPartyInsuranceInfo.addProperty("insuranceSource", "Phone");
                            thirdPartyInsuranceInfo.addProperty("insuranceName", "Anthem");

                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().add("thirdPartyInsuranceInfo", thirdPartyInsuranceInfo);
                        } /*else {
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("eligibilities").getAsJsonObject(key).addProperty("startDate", eligibilityStartDate);
                            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("eligibilities").getAsJsonObject(key).addProperty("endDate", (String) null);
                        }*/
                    } else {
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject().addProperty(key, (String) null);
                    }
                }
            }
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().remove("eligibilities");
        }
        if (data.get("isEnrollemntRequired").equalsIgnoreCase("yes")
                || data.get("isEnrollemntRequired").equalsIgnoreCase("true")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("consumerId", Integer.parseInt(consumerId.get()));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("startDate", enrollmentStartDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("endDate", enrollmentEndDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("anniversaryDate", anniversaryDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("openEnrollmentStartDate", openEnrollmentStartDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("openEnrollmentEndDate", openEnrollmentEndDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("updatedBy", "SYSTEM_INTEGRATION");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber.toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", presentTimeStamp);
            if (data.containsKey("createdBy"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("createdBy", data.getOrDefault("createdBy", "SYSTEM_INTEGRATION"));
            if (data.containsKey("txnStatus"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonObject().addProperty("txnStatus", data.get("txnStatus"));

            if (data.containsKey("planEndDateReason"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("planEndDateReason",
                        (data.get("planEndDateReason").equals("") || data.get("planEndDateReason").equals("null")) ? null : data.get("planEndDateReason"));
            if (data.containsKey("enrollmentType"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("enrollmentType", data.get("enrollmentType"));
            if (data.containsKey("planCode")) {
                String planCode = data.get("planCode").equals("getFromUISelected") ? getPlanCodeBasedOnUISelection()
                        : data.get("planCode").equals("planCodeFromEnrollmentDetails") ? APIEnrollmentController.getPlanCodeFromEnrollmentDetails(consumerId.get())
                        : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("planCode")).toString();
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("planCode", planCode);
            }
            if (data.containsKey("planId"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("planId", data.get("planId"));
            //API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("enrollment").getAsJsonObject().addProperty("planId", getPlanId(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonObject("enrollment").getAsJsonObject().get("planCode").getAsString()));
            if (data.containsKey("programTypeCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("programTypeCode", data.get("programTypeCode"));
            if (data.containsKey("subProgramTypeCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("subProgramTypeCode", data.get("subProgramTypeCode"));
            if (data.containsKey("openEnrollmentStatus"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("openEnrollmentStatus", data.get("openEnrollmentStatus"));

            if (data.containsKey("lockInStartDate"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("lockInStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("lockInStartDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));

            if (data.containsKey("lockInEndDate"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("lockInEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("lockInEndDate")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));

            if (data.containsKey("lockInStatusCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("lockInStatusCode", data.get("lockInStatusCode"));

            if (data.containsKey("lockInExemptionReason"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("lockInExemptionReason", data.get("lockInExemptionReason"));

            if (data.containsKey("serviceRegionCode"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("serviceRegionCode", data.get("serviceRegionCode"));
            if (data.containsKey("rejectionReason"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("rejectionReason", data.get("rejectionReason"));
            if (data.containsKey("channel"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("channel", data.get("channel"));
            if (data.containsKey("updatedOn"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("updatedOn", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("updatedOn")) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
            if (data.containsKey("selectionReason"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("selectionReason", data.get("selectionReason"));

            if (data.containsKey("processingMethod"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("processingMethod", data.get("processingMethod"));


            if (data.containsKey("enrollmentRecordId"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("recordId", data.get("enrollmentRecordId"));

            if (data.containsKey("genericFieldText1"))
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("genericFieldText1", String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("genericFieldText1"))));

            if (data.get("isEnrollmentProviderRequired").equalsIgnoreCase("yes")
                    || data.get("isEnrollmentProviderRequired").equalsIgnoreCase("true")) {
                if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal().equalsIgnoreCase("IN-EB")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().remove(("enrollmentProvider"));
                    JsonObject enrollmentProvider = new JsonObject();
                    enrollmentProvider.addProperty("consumerId", Integer.parseInt(consumerId.get()));
                    enrollmentProvider.addProperty("providerStartDate", data.containsKey("providerStartDate") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("providerStartDate")) : enrollmentStartDate);
                    enrollmentProvider.addProperty("providerEndDate", data.containsKey("providerEndDate") ? API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("providerEndDate")) : enrollmentEndDate);
//                    enrollmentProvider.addProperty("providerNpi", data.get("providerNpi"));
                    // Below 3 lines added with CP-33459. Delete above line after 04/15/2022 if no issues
                    if (data.containsKey("providerNpi")) {
                        System.out.println("Provider NPI : " + data.get("providerNpi"));
                        enrollmentProvider.addProperty("providerNpi", data.get("providerNpi").equals("null") ? null : data.get("providerNpi"));
                    }
                    enrollmentProvider.addProperty("providerFirstName", data.get("providerFirstName"));
                    enrollmentProvider.addProperty("providerLastName", data.get("providerLastName"));
                    enrollmentProvider.addProperty("providerMiddleName", data.get("providerMiddleName"));
//                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().add("enrollmentProvider", enrollmentProvider);
                    JsonArray enrollmentProviders = new JsonArray();
                    enrollmentProviders.add(enrollmentProvider);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().add("enrollmentProviders", enrollmentProviders);
                } else {

                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                            .get(0).getAsJsonObject().addProperty("consumerId", Integer.parseInt(consumerId.get()));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                            .get(0).getAsJsonObject().addProperty("providerStartDate", enrollmentStartDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                            .get(0).getAsJsonObject().addProperty("providerEndDate", enrollmentEndDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                            .get(0).getAsJsonObject().addProperty("effectiveStartDate", enrollmentStartDate);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                            .get(0).getAsJsonObject().addProperty("effectiveEndDate", enrollmentEndDate);
                    if (data.containsKey("providerType"))
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders")
                                .get(0).getAsJsonObject().addProperty("providerType", data.get("providerType"));
                    // Below 4 lines added with CP-33459. If providerNpi is provided then all the default enrollment provider info will be deleted. Later name and other provider fields can be added below:

                    if (data.containsKey("providerNpi")) {
                        JsonObject enrollmentProvider = new JsonObject();
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().remove(("enrollmentProviders"));
                        enrollmentProvider.addProperty("consumerId", Integer.parseInt(consumerId.get()));
                        enrollmentProvider.addProperty("providerNpi", data.get("providerNpi").equals("null") ? null : data.get("providerNpi"));
                        JsonArray enrollmentProviders = new JsonArray();
                        enrollmentProviders.add(enrollmentProvider);
                        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().add("enrollmentProviders", enrollmentProviders);
                    }
                }

            } else {
                // API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().addProperty("enrollmentProvider", (String) null);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0).getAsJsonObject().remove(("enrollmentProviders"));
            }

        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().remove("enrollments");
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", presentTimeStamp);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());

        if (data.containsKey("saveEligibilityEventName")) {
            Api_Body api_req = new EventBuilder()
                    .body(requestParams.get().toString())
                    .name(data.get("saveEligibilityEventName"))
                    .build();
            stg.addProduced(api_req);
        }
    }

    @Given("I provide dummy enrollment and eligibility information to create enrollment")
    public void I_provide_dummy_enrollment_and_eligibility_information_to_create_enrollment(Map<String, String> data) {
        if (data.containsKey("consumerId")) {
            consumerId.set(String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId"))).replace(".0", ""));
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("consumerId", consumerId.get());
        payload.addProperty("recordId", Integer.parseInt(data.getOrDefault("recordId", "21")));
        payload.addProperty("requestedBy", data.getOrDefault("requestedBy", "597"));
        payload.addProperty("createdOn", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"));
        if (data.get("isEligibilityRequired").equalsIgnoreCase("yes")
                || data.get("isEligibilityRequired").equalsIgnoreCase("true")) {
            JsonArray eligibilities = new JsonArray();
            eligibilities.add(new JsonObject());
            eligibilities.get(0).getAsJsonObject().add("coreEligibility", new JsonObject());
            for (String key : data.keySet()) {
                switch (key) {
                    case "consumerId":
                        eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("consumerId", consumerId.get());
                        break;
                    case "correlationId":
                        eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
                        break;
                    case "uiid":
                        eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("uiid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
                        break;
                    case "createdBy":
                    case "programCode":
                    case "subProgramTypeCode":
                    case "eligibilityStatusCode":
                    case "categoryCode":
                    case "genericFieldText1":
                    case "fileSource":
                        eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty(key, data.get(key).equals("null") ? null : data.get(key));
                        break;
                    case "recordId":
                        eligibilities.get(0).getAsJsonObject().addProperty(key, Integer.parseInt(data.get(key)));
                        break;
                    default:
                        // to ignore all unrelated keys
                }
            }
            eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
            eligibilities.get(0).getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("uiid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
            eligibilities.get(0).getAsJsonObject().addProperty("dbChanges", data.getOrDefault("dbChanges", "string"));
            payload.add("eligibilities", eligibilities);
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement = payload;
        requestParams.set(payload);
    }

    @Given("I initiated Eligibility and Enrollment Create API")
    public void initiateEnrollmentEligibilityCreateApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createEnrollmentAndEligibilityEndPoint);
    }

    @When("I run create Eligibility and Enrollment API")
    public void runEnrollmentEligibilityCreateApi() {
        System.out.println(requestParams.get());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        //  assertEquals(api.jsonPathEvaluator.get("status"), "success");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject getRequestParams() {
        return requestParams.get();
    }

    @Then("I initiated Enrollment status Api")
    public void iInitiatedEnrollmentStatusApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(enrollmentStatus);

    }

    @Then("I verify consumer and status selection made are displayed on the Outbound basket")
    public void iVerifyConsumerAndStatusSelectionMadeAreDisplayedOnTheOutboundBasket() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");

        assertTrue(json.size() > 1, "Outbound basket is empty");
        assertEquals(json.get(0).getAsJsonObject().get("txnStatus").getAsString(), "SELECTION_MADE", "TextStatus is fail");
        assertNotNull(json.get(0).getAsJsonObject().get("consumerId"));
        assertNotNull(json.get(0).getAsJsonObject().get("planCode"));
    }

    /**
     * <>Description</>   Returns plan id for the given plan code using PP services
     *
     * @param planCode
     * @return plan id for provided plan code
     */
    public String getPlanId(String planCode) {
        String planId = "";

        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_initiated_plan_search_api();
        API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_search_plan_api("false", "", planCode, "", "", "", "", "");
        JsonObject planDetails = API_THREAD_LOCAL_FACTORY.getPlanManagementControllerThreadLocal().i_run_post_plan_search_api();
        JsonArray plans = planDetails.getAsJsonArray("mcoContracts");
        for (JsonElement plan : plans) {
            if (plan.getAsJsonObject().get("planCode").getAsString().equalsIgnoreCase(planCode)) {
                planId = plan.getAsJsonObject().get("planId").getAsString();
                break;
            }
        }
        //Re initiating enrollment api
        initiateEnrollmentEligibilityCreateApi();
        return planId;
    }

    @Then("I verify consumer is not displayed on the Outbound basket")
    public void iVerifyConsumerIsNotDisplayedOnTheOutboundBasket() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        JsonArray json = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonArray("data");
        assertTrue(json.size() > 1, "Outbound basket is empty");
        assertEquals(json.get(0).getAsJsonObject().get("txnStatus").getAsString(), "SELECTION_MADE", "TextStatus is fail");
        assertTrue(json.get(0).getAsJsonObject().get("channel").getAsString().equals("PHONE") || json.get(0).getAsJsonObject().get("channel").getAsString().equals("AUTO_ASSIGNMENT")
                || json.get(0).getAsJsonObject().get("channel").getAsString().equals("WEB"), "Channel is fail");
    }

    @Then("I send API call with name {string} for create Eligibility and Enrollment")
    public void iSendAPICallWithNameForCreateEligibilityAndEnrollment(String name) {
        String json = requestParams.get().toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, json);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        //Waiting for the record to update
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ir) {
        }
    }

    @Then("I send API call with name {string} for update Enrollment information")
    public void iSendAPICallWithNameStringForUpdateEnrollmentInformation(String name, Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/updateEnrollmentInfo.json");
        Map<String, String> modifiedData = new HashMap<>();
        for (String key : data.keySet()) {
            if ((key.contains("planCode") && !key.contains("existingPlanCode"))) {
                if (data.get(key).endsWith("random")) {
                    APIEEDigitalIntegrationController DigIntegrationCntrlr = new APIEEDigitalIntegrationController();
                    List<String> planCodes = DigIntegrationCntrlr.getPlanCodes(false);
                    synchronized (newPlanCode.get()) {
                        newPlanCode.set(planCodes.get(0));
                    }
                } else {
                    synchronized (newPlanCode.get()) {
                        newPlanCode.set(data.get(key));
                    }
                }
                synchronized (newPlanName.get()) {
                    newPlanName.set(APIEnrollmentController.getPlanNameFromPlanCode(newPlanCode.get()));
                }
                modifiedData.put(key, newPlanCode.get());
            } else if (!key.equalsIgnoreCase("enrollmentProviders")) {
                modifiedData.put(key, data.get(key));
            }

        }

        updateRequestWithProviderEnrollmentInfo(data, null);
//        if(!data.containsKey("enrollmentProviders") || !data.get("enrollmentProviders").equalsIgnoreCase("Yes")){
//            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("enrollmentProviders", (String)null); API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("enrollmentProviders", (String)null);
//        }
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        modifiedData.remove("enrollmentProviders");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentUpdateBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateEnrollment);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, modifiedData));
    }

    @And("I send API call with name {string} for process Outbound Update")
    public void iSendAPICallWithNameForProcessOutboundUpdate(String name, Map<String, String> data) {
        Map<String, String> updatedData = new HashMap<>();
        updatedData.putAll(data);
        if (updatedData.containsKey("[0].planCode") && updatedData.get("[0].planCode").equalsIgnoreCase("planCodeFromEnrollmentDetails")) {
            updatedData.put("[0].planCode", APIEnrollmentController.getPlanCodeFromEnrollmentDetails(consumerId.get()));
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentUpdateBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateOutbound);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/outboundUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, updatedData));
    }

    @And("I send API call with name {string} for trigger Enrollment Start")
    public void iSendAPICallWithNameForTriggerEnrollmentStart(String name, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createEnrollmentAndEligibilityEndPoint);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/enrollmentStart.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @And("I send API call with name {string} for Rejected Selection Task process Outbound Update")
    public void iSendAPICallWithNameForRejectedSelectionTaskProcessOutboundUpdate(String name, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentUpdateBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateOutbound);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/rejectedSelectionOutboundUpdate.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));

    }

    @And("I perform plan transfer via API to new plan with data")
    public void i_perform_plan_transfer_via_api_to_new_plan_with_data(Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/updateEnrollmentInfo.json");
        JsonObject detailsToSave = new JsonObject();
        // json = api_common.updateJson(json, data);
        Map<String, String> modifiedData = new HashMap<>();
        for (String key : data.keySet()) {
            if ((key.contains("planCode") && !key.contains("existingPlanCode")) && data.get(key).endsWith("random")) {
                APIEEDigitalIntegrationController DigIntegrationCntrlr = new APIEEDigitalIntegrationController();
                List<String> planCodes = DigIntegrationCntrlr.getPlanCodes(true);
                planCodes.remove(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("existingPlanCode")));
                synchronized (newPlanCode) {
                    newPlanCode.set(planCodes.get(0));
                }
                synchronized (newPlanCode) {
                    newPlanCode.set(APIEnrollmentController.getPlanNameFromPlanCode(newPlanCode.get()));
                }
                modifiedData.put(key, newPlanCode.get());
                detailsToSave.addProperty("planCode", newPlanCode.get());
            } else if (!key.equalsIgnoreCase("enrollmentProviders")) {
                modifiedData.put(key, data.get(key));
            }

        }

        if (!data.containsKey("enrollmentProviders") || data.get("enrollmentProviders").equalsIgnoreCase("No")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("enrollmentProviders", (String) null);
        } else if (data.containsKey("enrollmentProviders") && data.get("enrollmentProviders").equalsIgnoreCase("random")) {
            APIProviderController apiProvider = new APIProviderController();
            apiProvider.iInitiatedPlanManagementForSearchProvider();
            Map<String, String> map = new HashMap<String, String>();
            map.put("providerSearch.planName", newPlanName.get());
            apiProvider.updateProviderSearch("providerSearch", map);
            JsonObject providerSearchResponse = apiProvider.getProviderSearchResponse();
            JsonObject providerObj = providerSearchResponse.getAsJsonObject("providerSearchResponse").getAsJsonArray("content").get(0).getAsJsonObject();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerFirstName", providerObj.get("firstName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerMiddleName", providerObj.get("middleName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerLastName", providerObj.get("lastName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerNpi", providerObj.get("npi").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerType", providerObj.getAsJsonArray("providerType").get(0)
                            .getAsJsonObject().get("providerTypeCd").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerId", providerObj.get("planProviderId").getAsString());
            detailsToSave.addProperty("providerId", providerObj.get("planProviderId").getAsString());
            detailsToSave.addProperty("providerFirstName", providerObj.get("firstName").getAsString());
            detailsToSave.addProperty("providerMiddleName", providerObj.get("middleName").getAsString());
            detailsToSave.addProperty("providerLastName", providerObj.get("lastName").getAsString());
            detailsToSave.addProperty("providerNpi", providerObj.get("npi").getAsString());
            detailsToSave.addProperty("providerType", providerObj.getAsJsonArray("providerType").get(0)
                    .getAsJsonObject().get("providerTypeCd").getAsString());
            detailsToSave.addProperty("providerPhoneNumber", providerObj.getAsJsonArray("providerAddress").get(0)
                    .getAsJsonObject().getAsJsonArray("phoneNumbers").get(0).getAsJsonObject().get("phoneNumber").getAsString());
            detailsToSave.addProperty("pcp", providerObj.get("firstName").getAsString() + " " + providerObj.get("lastName").getAsString());
            detailsToSave.addProperty("mcoPhoneNumber", APIEnrollmentController.getMcoPhoneNumberFromPlanCode(newPlanCode.get()));
        }
        modifiedData.remove("enrollmentProviders");
        modifiedData.remove("saveDetailsWithName");
        modifiedData.remove("existingPlanCode");

        if (data.containsKey("saveDetailsWithName")) {
            Api_Body api_req = new EventBuilder()
                    .body(detailsToSave.toString())
                    .name(data.get("saveDetailsWithName"))
                    .build();
            stg.addProduced(api_req);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(transferOutboundEnrollments);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString(), modifiedData));
    }


    @And("I receive ETL memberId for {string} and perform plan transfer via API to new plan with data")
    public void i_receive_ETL_memberId_and_perform_plan_transfer_via_api_to_new_plan_with_data(String name, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(transferOutboundEnrollments);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/updateEnrollmentInfo.json");
        String member = EE_ETL_StepDefs.nameToMemberId.get(name);
        String consumerId = EE_ETL_StepDefs.memberIdToConsumerId.get(member);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("consumerId", consumerId);
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));
    }

    @And("I send API call with name {string} for process Outbound Update Enrollments")
    public void iSendAPICallWithNameForProcessOutboundUpdateEnrollments(String name, Map<String, String> data) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentUpdateBase);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(updateOutboundEnrollments);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("/crm/enrollment/updateEnrollments.json");
        String json = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutWithNameRequest(name, API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().updateJson(json, data));

    }


    public static void main(String[] args) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        LocalDate originalStart = LocalDate.parse("06-24-2021", formater);
        LocalDate originalEnd = LocalDate.parse("06-23-2022", formater);
        LocalDate changedStart = LocalDate.parse("05-24-2021", formater);
        LocalDate changedEnd = LocalDate.parse("05-24-2022", formater);

        if (originalStart.compareTo(changedStart) > 0 && originalEnd.compareTo(changedEnd) > 0) {
            System.out.println("backward");
        } else if (originalStart.compareTo(changedStart) < 0 && originalEnd.compareTo(changedEnd) < 0) {
            System.out.println("forward");
        } else if (originalStart.compareTo(changedStart) < 0 && originalEnd.compareTo(changedEnd) > 0) {
            System.out.println("shrynk");
        } else if (originalStart.compareTo(changedStart) > 0 && originalEnd.compareTo(changedEnd) < 0) {
            System.out.println("expand");
        }

    }

    @When("User provide one more eligibility details")
    public void User_provide_one_more_eligibility_details(Map<String, String> data) {
        String eligibilityString = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0)
                .getAsJsonObject().getAsJsonObject("coreEligibility").toString();
        JsonObject coreEligibility = new JsonParser().parse(eligibilityString).getAsJsonObject();
        JsonObject eligibility = new JsonObject();
        eligibility.add("coreEligibility", coreEligibility);

        eligibility.getAsJsonObject("coreEligibility").addProperty("uiid", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        eligibility.getAsJsonObject("coreEligibility").addProperty("createdOn", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"));
        eligibility.getAsJsonObject("coreEligibility").addProperty("createdBy", "SYSTEM_INTEGRATION");
//        eligibility.getAsJsonObject("coreEligibility").addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber.toString());

        for (String key : data.keySet()) {
            switch (key) {
                case "eligibilityStartDate":
                    eligibility.getAsJsonObject("coreEligibility").addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
                    break;
                case "eligibilityEndDate":
                    eligibility.getAsJsonObject("coreEligibility").addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get(key)) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
                    break;
                case "programCode":
                case "programTypeCode":
                case "subProgramTypeCode":
                case "categoryCode":
                case "exemptionCode":
                case "eligibilityStatusCode":
                case "coverageCode":
                case "eligibilityEndReason":
                case "aidCategoryCode":
                case "genericFieldText1":
                    eligibility.getAsJsonObject("coreEligibility").addProperty(key,
                            (data.get(key).equals("null") || data.get(key).isEmpty()) ? null : data.get(key));
                    break;
                case "eligibilityRecordId":
                    eligibility.addProperty("recordId", data.get(key));
                    break;
            }
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").add(eligibility);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @When("User provide other eligibility segments details")
    public void user_provide_other_eligibility_segments_details(Map<String, String> data) {
        JsonObject otherEligibilitySegment = new JsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "uiid":
                case "segmentDetailValue3":
                case "segmentDetailValue4":
                    otherEligibilitySegment.addProperty(key, (data.get(key).equals("null") || data.get(key).isEmpty()) ? null : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))));
                    break;
                case "createdOn":
                case "updatedOn":
                    otherEligibilitySegment.addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("startDate")) + "T11:14:26Z");
                    break;
                case "startDate":
                    otherEligibilitySegment.addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("startDate")));
                    break;
                case "endDate":
                    otherEligibilitySegment.addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("endDate")));
                    break;
                case "segmentDetailValue1":
                case "segmentDetailValue2":
                case "segmentDetailValue5":
                case "createdBy":
                case "updatedBy":
                    otherEligibilitySegment.addProperty(key, (data.get(key).equals("null") || data.get(key).isEmpty()) ? null : data.get(key));
                    break;
                case "segmentTypeCode":
                    otherEligibilitySegment.addProperty(key, data.get(key));
                    break;
            }
        }
        int lastIndexOfCoreEligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").size() - 1;
        if (!API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(lastIndexOfCoreEligibility).getAsJsonObject().has("otherEligibilitySegments")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(lastIndexOfCoreEligibility).getAsJsonObject()
                    .add("otherEligibilitySegments", new JsonArray());
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(lastIndexOfCoreEligibility).getAsJsonObject()
                .get("otherEligibilitySegments").getAsJsonArray().add(otherEligibilitySegment);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @When("User provide other enrollment segments details")
    public void User_provide_other_enrollment_segments_details(Map<String, String> data) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testData/api/crm/enrollment/otherEnrollmentSegment.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonArray enrollments = new JsonParser().parse(reader).getAsJsonArray();
        enrollments.get(0).getAsJsonObject().addProperty("correlationId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber.toString());
        for (String key : data.keySet()) {
            switch (key) {
                case "recordId":
                    enrollments.get(0).getAsJsonObject().addProperty(key, data.get(key));
                    break;
                case "consumerId":
                    enrollments.get(0).getAsJsonObject().addProperty(key,
                            data.get(key).isEmpty()
                                    ? consumerId.get()
                                    : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().replace(".0", ""));
                    break;
                case "segmentTypeCode":
                    enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments")
                            .get(0).getAsJsonObject().addProperty(key, data.get(key));
                    break;
                case "genericFieldText1":
                case "genericFieldText2":
                case "genericFieldText3":
                case "genericFieldText5":
                case "uiid":
                    enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments")
                            .get(0).getAsJsonObject().addProperty(key,
                                    (data.get(key).isEmpty() || data.get(key).equals("null"))
                                            ? null
                                            : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().replace(".0", ""));
                    break;
                case "startDate":
                case "genericFieldDate1":
                    enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments")
                            .get(0).getAsJsonObject().addProperty(key,
                                    (data.get(key).isEmpty() || data.get(key).equals("null"))
                                            ? null
                                            : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)));
                    break;
                case "endDate":
                    enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments")
                            .get(0).getAsJsonObject().addProperty("endDate",
                                    (data.get(key).isEmpty() || data.get(key).equals("null"))
                                            ? null
                                            : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get(key)));
                    break;
            }
        }
        if (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().has("enrollments")) {
            int lastEnrolmentIndex = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").size() - 1;
            if (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(lastEnrolmentIndex).getAsJsonObject().has("otherEnrollmentSegments")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(lastEnrolmentIndex).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments")
                        .addAll(enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments"));
            } else {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(lastEnrolmentIndex).getAsJsonObject().add(
                        "otherEnrollmentSegments", enrollments.get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments"));
            }
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("enrollments", enrollments);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @Then("I verify case loader error message for multiple ssn matching {string}")
    public void verifyMoreThanOneConsumerError(String expErrorMessage) {
        assertTrue(caseLoaderResponse.get().getAsJsonArray("validationList").get(0).getAsJsonObject().get("validationErrorCode").getAsString().contains(expErrorMessage));
    }

    @Then("I verify effective end date is populated for old case")
    public void verifyEffectiveEndDatePopulatedForOldCase() {
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN.get());
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        JsonObject responseObject = consumerSearch.runConsumerSearchAPI("consumerSSN");
        JsonArray caseConsumersArray = responseObject.getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("caseConsumers");
        JsonObject oldCaseConsumer = null;
        JsonObject newCaseConsumer = null;
        for (int i = 0; i < caseConsumersArray.size(); i++) {
            if (caseConsumersArray.get(i).getAsJsonObject().get("effectiveEndDate").isJsonNull())
                newCaseConsumer = caseConsumersArray.get(i).getAsJsonObject();
            else
                oldCaseConsumer = caseConsumersArray.get(i).getAsJsonObject();
        }

        assertEquals(oldCaseConsumer.get("effectiveEndDate").getAsString().split("T")[0], API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getYesterdayDate("yyyy-MM-dd"));
        assertTrue(newCaseConsumer.get("effectiveEndDate").isJsonNull());
    }

    @Then("I verify new case id created when different CaseIdentificationNumber is passed")
    public void verifyCaseIdCreatedUsingNewCaseLoader() {
        APICaseRestController caseController = new APICaseRestController();
        caseController.setCaseCorrelationId(caseCorrelationId.get());
        caseController.initiateSearchCaseRecords();
        JsonObject responseObject = caseController.searchCaseByCorrelationId();
        String tempCaseID = caseController.getCreatedCaseId();
        System.out.println(tempCaseID);
        assertTrue(tempCaseID != null);
    }

    @Then("I will verify External Consumer ID {string} is updated for the consumer with ssn {string}")
    public void verifyExternalConsumerIdUpdated(String expExternalConsumerId, String consumerSSN) {
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN);
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        JsonObject responseObject = consumerSearch.runConsumerSearchAPI("consumerSSN");
        String actExternalConsumerId = responseObject.getAsJsonObject().getAsJsonArray("consumers").get(0).getAsJsonObject()
                .getAsJsonArray("consumerIdentificationNumber").get(0).getAsJsonObject().get("externalConsumerId").getAsString();
        if (expExternalConsumerId.equalsIgnoreCase("random"))
            expExternalConsumerId = externalConsumerIdToOverride.get();
        assertEquals(actExternalConsumerId, expExternalConsumerId);
    }

    @Given("I created a consumer with population type {string} with enrollment and eligibility information")
    public void i_created_a_consumer_with_eligibility_enrollment_records(String populationType, Map<String, String> data) {

        setRegionDetails();
        String dob = "";
        String PregnancyIndicator = "";
        String pregnancyDueDate = "";
        String serviceRegion = "";
        String countyCode = "";
        switch (populationType) {
            case "NEWBORN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, -8, 0);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                countyCode = "1234";
                break;
            case "PREGNANT-WOMEN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                // serviceRegion = "Atlanta";
                serviceRegion = "East";
                break;
            case "PREGNANT-WOMEN-NORTH":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                serviceRegion = "North";
                break;
            case "MEDICAID-GENERAL":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "MEDICAID-GENERAL-ATLANTA":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                break;
            case "FOSTER-CARE":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "CUSTOM":
                if (data.containsKey("age")) {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -(Integer.parseInt(data.get("age"))));
                } else {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                }
                if (data.containsKey("pregnancyIndicator") && data.get("pregnancyIndicator").equals("true")) {
                    PregnancyIndicator = "true";
                    pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                } else {
                    PregnancyIndicator = "false";
                    pregnancyDueDate = null;
                }
                serviceRegion = data.getOrDefault("serviceRegion", "Atlanta");
                break;
            case "IN-EB-CONSUMER":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -20);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
        }
        consumerDateOfBirth.set(dob);
        APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
        caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE_v2.json");
        caseLoaderReq.set(caseLoader.generateCaseLoaderRequest());

        consumerFirstName.set(caseLoader.getConsumerFirstName());
        consumerLastName.set(caseLoader.getConsumerLastName());
        if (data.containsKey("recordId") && !data.get("recordId").equalsIgnoreCase("100")) {
            externalConsumerId.set(caseLoader.getExternalConsumerId());
        }
        caseIdentificationNo.set(caseLoader.getCaseIdentificationNo());
        String correlationId = caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").get("correlationId").getAsString();
        APICreateCaseController.correlationId.set(correlationId);
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", dob);
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyInd", PregnancyIndicator);
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyDueDate", pregnancyDueDate);

        //Update Address
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCity", getRegionDetails(serviceRegion).get("city"));
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").
                get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCounty", getRegionDetails(serviceRegion).get("county"));
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressState", getRegionDetails(serviceRegion).get("state"));
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressZip", getRegionDetails(serviceRegion).get("zip"));
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                .getAsJsonObject().get("address").getAsJsonObject().addProperty("outOfStateIndicator", false);


        for (String key : data.keySet()) {
            switch (key) {
                case "requestedBy":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").addProperty("requestedBy", data.get("requestedBy"));
                    break;
            }
        }

        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("eligibility", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility"));
        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().add("enrollment", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment"));
        //recordId
        if (data.containsKey("case.recordId") && data.get("case.recordId").equalsIgnoreCase("100")) {
            //.getAsJsonArray("consumers").get(0).getAsJsonObject()
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").addProperty("recordId", data.get("recordId"));
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                    .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId.get());

            List<String> caseAttributes = Arrays.asList("caseIdentificationNumber", "correlationId", "caseIdentificationNumberType", "caseStatus", "caseType");
            for (String attribute : caseAttributes) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").addProperty(attribute, (String) null);
            }
            List<String> consumerAttributres = Arrays.asList("consumerDateOfBirth", "consumerLastName", "consumerFirstName", "consumerMiddleName", "consumerPrefix",
                    "consumerSSN", "consumerSuffix", "consumerType", "correspondencePreference", "dateOfDeath", "dateOfDeathNotifiedBy", "dateOfDeathNotifiedDate", "dateOfSsnValidation",
                    "effectiveEndDate", "genderCode", "relationShip", "pregnancyInd", "effectiveStartDate", "contacts", "consumerConsent");
            for (String attribute : consumerAttributres) {
                caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty(attribute, (String) null);
            }

        }
        if (data.containsKey("case.recordId")) {
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").addProperty("recordId", data.get("case.recordId"));
        }

        //Add Eligibility data
        if (data.get("isEligibilityRequired").equalsIgnoreCase("yes")) {
            for (String key : data.keySet()) {
                switch (key) {
                    case "eligibilityStatusCode":
                    case "programCode":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                                .getAsJsonObject().getAsJsonObject("coreEligibility").addProperty(key, data.get(key));
                        break;
                    case "eligibilityStartDate":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                                .getAsJsonObject().getAsJsonObject("coreEligibility").addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("eligibilityStartDate")));
                        break;
                    case "eligibilityRecordId":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                                .getAsJsonObject().addProperty("recordId", data.get("eligibilityRecordId"));
                        break;
                }
            }
            //Add other eligibility segment data
            addOtherEligibilitySegments(data);

        } else {
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                    .addProperty("eligibility", (String) null);
        }


        //Add Enrollment data
        if (data.get("isEnrollmentRequired").equalsIgnoreCase("yes")) {
            for (String key : data.keySet()) {
                switch (key) {
                    case "programTypeCode":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                                .getAsJsonObject().addProperty("programTypeCode", data.get("programTypeCode"));
                        break;
                    case "subProgramTypeCode":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                                .getAsJsonObject().addProperty("subProgramTypeCode", data.get("subProgramTypeCode"));
                        break;
                    case "enrollmentStartDate":
                        caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                                .getAsJsonObject().addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("enrollmentStartDate")));
                        break;
                }
            }
            //Adding other enrollment segment
            addOtherEnrollmentSegments(data);
        } else if (data.get("isEnrollmentRequired").equalsIgnoreCase("no")
                && data.containsKey("isOtherEnrollmentSegmentsRequired")
                && data.get("isOtherEnrollmentSegmentsRequired").equalsIgnoreCase("yes")) {


            List<String> attributesWithNull = Arrays.asList("uiid", "subProgramTypeCode", "startDate",
                    "programTypeCode", "planEndDateReason", "planCode", "planId", "enrollmentProvider", "endDate", "correlationId");
            for (String attribute : attributesWithNull) {
                caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                        .getAsJsonObject().addProperty(attribute, (String) null);
            }

            List<String> attributesToRemove = Arrays.asList("anniversaryDate", "autoAssignIndicator", "lockInStartDate",
                    "lockInEndDate", "lockInStatusCode", "lockInExemptionReason");
            for (String attribute : attributesToRemove) {
                caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                        .getAsJsonObject().remove(attribute);
            }

            //Adding other enrollment segment
            addOtherEnrollmentSegments(data);
        } else if (data.get("isEnrollmentRequired").equalsIgnoreCase("no")) {
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0)
                    .getAsJsonObject().addProperty("enrollment", (String) null);
        }


        //ADDING NEW FIELDS
        for (String key : data.keySet()) {
            switch (key) {
                case "maritalCode":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("maritalCode", data.get("maritalCode"));
                    break;
                case "medicallyFrailConfirmationCode":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("medicallyFrailConfirmationCode", data.get("medicallyFrailConfirmationCode"));
                    break;
                case "medicallyFrailLastConfirmedAssessmentDate":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("medicallyFrailLastConfirmedAssessmentDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("medicallyFrailLastConfirmedAssessmentDate")));
                    break;
                case "recipientCountyWard":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("recipientCountyWard", data.get("recipientCountyWard"));
                    break;
                case "recipientWardType":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("recipientWardType", data.get("recipientWardType"));
                    break;
                case "pregnancyStartDate":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("pregnancyStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("pregnancyStartDate")));
                    break;
                case "pregnancyEndDate":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("pregnancyEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("pregnancyEndDate")));
                    break;
                case "mergeReason":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("mergeReason", data.get("mergeReason"));
                    break;
                case "pmpPaper":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("pmpPaper", data.get("pmpPaper"));
                    break;
                case "spouseExternalConsumerId":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("spouseExternalConsumerId", data.get("spouseExternalConsumerId"));
                    break;
                case "medicaidIdActiveIndicator":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                            .get(0).getAsJsonObject().addProperty("medicaidIdActiveIndicator", data.get("medicaidIdActiveIndicator"));
                    break;
            }
        }
        caseLoader.setRequestParams(caseLoaderReq.get());
        caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
        consumerSSN.set(caseLoader.getConsumerSSN());
        APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
        consumerSearch.setConsumerSSN(consumerSSN.get());
        consumerSearch.initiatedCaseConsumerContactSearchAPI();
        consumerSearch.runConsumerSearchAPI("consumerSSN");


        consumerId.set(consumerSearch.getConsumerIdFromSearchResults());

        caseCorrelationId.set(caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").get("correlationId").getAsString());
        APICaseRestController caseController = new APICaseRestController();
        caseController.setCaseCorrelationId(caseCorrelationId.get());
        caseController.initiateSearchCaseRecords();
        JsonObject response = caseController.searchCaseByCorrelationId();
        String tempCaseID = caseController.getCreatedCaseId();
        if (data.get("case.recordId") != null && data.get("case.recordId").equalsIgnoreCase("101"))
            Assert.assertTrue(tempCaseID == null);
        if (tempCaseID != null)
            caseId.set(tempCaseID);

        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());

        APIEEDigitalIntegrationController digitalApi = new APIEEDigitalIntegrationController();
        digitalApi.setConsumerIds(this.consumerId.get());

        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.setConsumerId(this.consumerId.get());

        // this method is going to save consumer info to storage created by Kamil
        // so we can get these info back later when have multiple consumers in same case (for questions ask Sobir)
        if (data.containsKey("saveConsumerInfo")) {
            Api_Body api_req = new EventBuilder()
                    .body("{\"firstName\": \"" + consumerFirstName.get() + "\", \"lastName\": \"" + consumerLastName.get() + "\", " +
                            "\"consumerSSN\": \"" + consumerSSN.get() + "\", \"consumerId\": \"" + consumerId.get() + "\", " +
                            "\"caseCorrelationId\": \"" + caseCorrelationId.get() + "\", \"caseId\": \"" + caseId.get() + "\", " +
                            "\"externalConsumerId\": \"" + externalConsumerId.get() + "\", " +
                            "\"correlationId\": \"" + correlationId + "\"}")
                    .name(data.get("saveConsumerInfo"))
                    .build();
            stg.addProduced(api_req);
            System.out.println("api_common.defineValue(" + data.get("saveConsumerInfo") + ".firstName) = " + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("saveConsumerInfo") + ".firstName"));
        }
    }

    //this method will create consumer with previuosly existing consumer details
    @When("I Match Demographic Record to Consumer in ConnectionPoint")
    public void matchDemographicRecordtoConsumerinCP(Map<String, String> data) {
        APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
        caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();

        for (String key : data.keySet()) {
            switch (key) {
                case "consumerSSN":
                    if (data.containsKey("consumerSSN") && !data.get("consumerSSN").isEmpty() && !data.get("consumerSSN").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", data.get(key));
                    else if (data.get("consumerSSN").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                    else
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN.get());
                    break;
                case "externalConsumerId":
                    externalConsumerIdToOverride.set("x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber));
                    if (data.containsKey("externalConsumerId") && !data.get("externalConsumerId").isEmpty() && !data.get("externalConsumerId").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                                .get(0).getAsJsonObject().addProperty("externalConsumerId", data.get(key));
                    else if (data.get("externalConsumerId").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerIdToOverride.get());
                    else
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId.get());
                    break;
                case "consumerFirstName":
                    if (data.get("consumerFirstName").equalsIgnoreCase("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", "Fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                    else
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", data.get("consumerFirstName"));
                    break;
                case "consumerLastName":
                    if (data.get("consumerLastName").equalsIgnoreCase("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", "Ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
                    else
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", data.get("consumerFirstName"));
                    break;
                case "caseIdentificationNumber":
                    if (data.containsKey("caseIdentificationNumber") && !data.get("caseIdentificationNumber").isEmpty() && !data.get("caseIdentificationNumber").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("caseIdentificationNumber", data.get(key));
                    else if (data.get("caseIdentificationNumber").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("caseIdentificationNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                    else
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
                    break;
                case "case.recordId":
                    if (data.containsKey("case.recordId") && !data.get("case.recordId").isEmpty() && !data.get("case.recordId").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("recordId", data.get(key));
                    else if (data.get("case.recordId").contains("random"))
                        caseLoaderReq.get().getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("recordId", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
                    break;
                case "medicaidIdActiveIndicator":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                            .get(0).getAsJsonObject().addProperty("medicaidIdActiveIndicator", data.get("medicaidIdActiveIndicator"));
                    break;
                case "pregnancyStartDate":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("pregnancyStartDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("pregnancyStartDate")));
                    break;
                case "pregnancyEndDate":
                    caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject()
                            .addProperty("pregnancyEndDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("pregnancyEndDate")));
                    break;
            }
        }


        caseLoader.setRequestParams(caseLoaderReq.get());
        caseLoaderResponse.set(caseLoader.runCaseLoaderEligibilityEnrollmentAPI());
    }

    @When("User provide one more enrollment details")
    public void User_provide_one_more_enrollment_details(Map<String, String> data) {
        String enrollmentString = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").get(0)
                .getAsJsonObject().toString();
        JsonObject enrollment = new JsonParser().parse(enrollmentString).getAsJsonObject();
        if (enrollment.has("otherEnrollmentSegments")) {
            enrollment.remove("otherEnrollmentSegments");
        }
        for (String key : data.keySet()) {
            switch (key) {
                case "enrollmentStartDate":
                    enrollment.addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
                    break;
                case "enrollmentEndDate":
                    enrollment.addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get(key)) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
                    break;
                case "anniversaryDate":
                    enrollment.addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)) + "T" + API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getCurrentDateAndTime("HH:mm:ss.sss'Z'"));
                    break;
                case "planCode":
                case "planId":
                case "programTypeCode":
                case "subProgramTypeCode":
                case "serviceRegionCode":
                case "txnStatus":
                case "rejectionReason":
                case "planEndDateReason":
                case "channel":
                case "genericFieldText1":
                    enrollment.addProperty(key,
                            (data.get(key).equals("null") || data.get(key).isEmpty()) ? null : data.get(key));
                    break;
                case "enrollmentRecordId":
                case "recordId":
                    enrollment.addProperty("recordId", data.get("enrollmentRecordId"));
                    break;
                case "eligibilityRecordId":
                    enrollment.addProperty("recordId", data.get(key));
                    break;
            }
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("enrollments").add(enrollment);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
    }

    @When("I send independent API call with name {string} to create other enrollment segment with data")
    public void I_send_independent_API_call_to_create_other_enrollment_segment_with_data(String name, Map<String, String> data) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testData/api/crm/enrollment/independentOtherEnrollmentSegment.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonArray enrollments = new JsonParser().parse(reader).getAsJsonArray();
        for (String key : data.keySet()) {
            switch (key) {
                case "consumerId":
                    enrollments.get(0).getAsJsonObject().addProperty(key,
                            data.get(key).isEmpty() ? consumerId.get() : API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key)).toString().replace(".0", ""));
                    break;
                case "startDate":
                case "genericFieldDate1":
                    enrollments.get(0).getAsJsonObject().addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)));
                    break;
                case "endDate":
                    enrollments.get(0).getAsJsonObject().addProperty(key, API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get(key)));
                    break;
                case "genericFieldText1":
                case "segmentTypeCode":
                    enrollments.get(0).getAsJsonObject().addProperty(key, data.get(key));
                    break;
            }
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(enrollmentBaseUri);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(otherSegments);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostWithNameRequest(name, enrollments.toString());
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I save pre created consumer info with name {string} with data")
    public void I_save_pre_created_consumer_info_with_name_with_data(String name, Map<String, String> data) {
        JsonObject result = new JsonObject();
        for (String key : data.keySet()) {
            result.addProperty(key, data.get(key));
//   possible keys: firstName, lastName, consumerSSN, consumerId, caseCorrelationId, externalConsumerId, caseId, caseIdentificationNumber
        }
        Api_Body api_req = new EventBuilder()
                .body(result.toString())
                .name(name)
                .build();
        stg.addProduced(api_req);
    }

    private void addOtherEnrollmentSegments(Map<String, String> data) {
        JsonArray array = new JsonArray();
        JsonObject otherEnrollmentSegment = new JsonObject();
        otherEnrollmentSegment = caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                .getAsJsonObject().getAsJsonArray("otherEnrollmentSegments").get(0).getAsJsonObject();
        if (data.containsKey("isOtherEnrollmentSegmentsRequired")
                && data.get("isOtherEnrollmentSegmentsRequired").equalsIgnoreCase("yes")) {
            int size = getMaxSize(data.keySet(), "otherEnrollSegment");
            int count = 0;
            for (int i = 0; i < size; i++) {
                for (String key : data.keySet()) {
                    if (key.startsWith("otherEnrollSegment")) {
                        switch (key.split("\\.")[1]) {
                            case "startDate":
                                otherEnrollmentSegment.addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("otherEnrollSegment[" + i + "].startDate")));
                                break;
                            case "endDate":
                                otherEnrollmentSegment.addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("otherEnrollSegment[" + i + "].endDate")));
                                break;
                            case "segmentTypeCode":
                                otherEnrollmentSegment.addProperty("segmentTypeCode", data.get("otherEnrollSegment[" + i + "].segmentTypeCode"));
                                break;
                        }
                    }
                }
                array.add(otherEnrollmentSegment);
            }

            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                    .getAsJsonObject().getAsJsonArray("otherEnrollmentSegments").addAll(array);
        } else {
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("enrollment").get(0)
                    .getAsJsonObject().remove("otherEnrollmentSegments");
        }
    }

    private void addOtherEligibilitySegments(Map<String, String> data) {
        JsonArray array = new JsonArray();
        JsonObject otherEligibilitySegment = new JsonObject();
        otherEligibilitySegment = caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                .getAsJsonObject().getAsJsonArray("otherEligibilitySegments").get(0).getAsJsonObject();
        if (data.containsKey("isOtherEligibilitySegmentsRequired")
                && data.get("isOtherEligibilitySegmentsRequired").equalsIgnoreCase("yes")) {
            int size = getMaxSize(data.keySet(), "otherElgSegment");
            int count = 0;
            for (int i = 0; i < size; i++) {
                for (String key : data.keySet()) {
                    if (key.startsWith("otherElgSegment")) {
                        switch (key.split("\\.")[1]) {
                            case "startDate":
                                otherEligibilitySegment.addProperty("startDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get("otherElgSegment[" + i + "].startDate")));
                                break;
                            case "endDate":
                                otherEligibilitySegment.addProperty("endDate", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get("otherElgSegment[" + i + "].endDate")));
                                break;
                            case "segmentTypeCode":
                                otherEligibilitySegment.addProperty("segmentTypeCode", data.get("otherElgSegment[" + i + "].segmentTypeCode"));
                                break;
                            case "segmentDetailValue2":
                                otherEligibilitySegment.addProperty("segmentDetailValue2", data.get("otherElgSegment[" + i + "].segmentDetailValue2"));
                                break;
                        }
                    }
                }
                array.add(otherEligibilitySegment);
            }

            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                    .getAsJsonObject().getAsJsonArray("otherEligibilitySegments").addAll(array);
        } else {
            caseLoaderReq.get().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("eligibility").get(0)
                    .getAsJsonObject().remove("otherEligibilitySegments");
        }
    }

    private int getMaxSize(Set<String> keyset, String keyWord) {
        int max = 0;
        for (String key : keyset) {
            if (key.startsWith(keyWord)) {
                int index = Integer.parseInt(key.split("\\.")[0].replace(keyWord + "[", "").replace("]", ""));
                if (index > max)
                    max = index;
            }
        }
        return max;
    }

    public String getPlanCodeBasedOnUISelection() {
        switch (CRMEnrollmentUpdateStepDef.selectedPlanName.get()) {
            case "AMERIGROUP COMMUNITY CARE":
                return "84";
            case "CARESOURCE GEORGIA":
                return "87";
            case "PEACH STATE":
                return "85";
            case "WELLCARE":
                return "86";
            case "CARESOURCE HIP":
                return "755726440";
            case "ANTHEM HIP":
                return "455701400";
            case "MANAGED HEALTH SERVICES HIP":
                return "355787430";
            case "MDWISE AMERICHOICE HIP":
                return "555763410";
            case "UNITED HEALTHCARE HCC":
                return "699842000";
            case "MANAGED HEALTH SERVICES HCC":
                return "399243310";
            case "ANTHEM HCC":
                return "499254630";
            case "MDWISE HH":
                return "500307680";
            case "MANAGED HEALTH SERVICES":
                return "300119960";
            case "CARESOURCE":
                return "700410350";
            case "ANTHEM":
                return "400752220";
        }
        return "Not found! Please update plans based on your tenant Plans Configuration file";
    }

    @When("User provide one more other enrollment segments details")
    public void User_provide_one_more_other_eligibility_segments_details(Map<String, String> data) {

        JsonArray otherEnrollmentSegmentsArray = requestParams.get().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments");

        JsonObject otherEnrollmentSegment = new JsonObject();
        //otherEnrollmentSegment = otherEnrollmentSegmentsArray.get(0).getAsJsonObject();
        for (String key : data.keySet()) {
            switch (key) {
                case "startDate":
                    otherEnrollmentSegment.addProperty("startDate",
                            data.get(key).equals("null")
                                    ? null
                                    : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)));
                    break;
                case "endDate":
                    otherEnrollmentSegment.addProperty("endDate",
                            data.get(key).equals("null")
                                    ? null
                                    : API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getEndDate(data.get(key)));
                    break;
                case "genericFieldText1":
                    otherEnrollmentSegment.addProperty("genericFieldText1",
                            data.get(key).equals("null")
                                    ? null
                                    : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""));
                    break;
                case "genericFieldDate1":
                    otherEnrollmentSegment.addProperty("genericFieldDate1", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getStartDate(data.get(key)));
                    break;
                case "segmentTypeCode":
                    otherEnrollmentSegment.addProperty("segmentTypeCode", data.get(key));
                    break;
                case "genericFieldText2":
                    otherEnrollmentSegment.addProperty("genericFieldText2",
                            data.get(key).equals("null")
                                    ? null
                                    : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""));
                    break;
                case "genericFieldText3":
                    otherEnrollmentSegment.addProperty("genericFieldText3",
                            data.get(key).equals("null")
                                    ? null
                                    : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""));
                    break;
                case "genericFieldText5":
                    otherEnrollmentSegment.addProperty("genericFieldText5",
                            data.get(key).equals("null")
                                    ? null
                                    : String.valueOf(API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get(key))).replace(".0", ""));
                    break;
            }
        }
        requestParams.get().getAsJsonArray("enrollments").get(0).getAsJsonObject().getAsJsonArray("otherEnrollmentSegments").add(otherEnrollmentSegment);

    }

    @Given("I search created consumer for business events")
    public void i_search_created_consumer_for_business_events() {
        manualCaseConsumerSearchPage.firstName.sendKeys(consumerFirstName.get());
        manualCaseConsumerSearchPage.lastName.sendKeys(consumerLastName.get());
        waitFor(4);
        manualCaseConsumerSearchPage.manCaseConsumerSearchButton.click();
        waitFor(4);
        crmContactRecordUIPage1.stateCaseIdFirstRecordBusEvents.click();
        waitForVisibility(contactRecordDashboard.historyScreen, 10);
        contactRecordDashboard.historyScreen.click();
    }

    @Given("I verify drop down options are displayed for History screen")
    public void i_verify_drop_down_options_are_displayed_for_History_screen() {
        assertTrue(dashboardPage.historyScreenConsumerNameDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenConsumerNameDropDown.getText().contains(consumerFirstName.get()));

        assertTrue(dashboardPage.historyScreenBusinessEventsDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenBusinessEventsDropDown.getText().contains("All"));

        assertTrue(dashboardPage.historyScreenProcessDateRangeDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenProcessDateRangeDropDown.getText().contains("Last 2 years"));

        assertTrue(dashboardPage.historyScreenChanneleDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenChanneleDropDown.getText().contains("CHANNEL"));

        assertTrue(dashboardPage.resetFilterButton.isDisplayed());

    }

    @Given("I verify drop down options are displayed for History screen for multiple consumers")
    public void i_verify_drop_down_options_are_displayed_for_History_screen_for_multiple_cons() {
        assertTrue(dashboardPage.historyScreenConsumerNameDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenConsumerNameDropDown.getText().isEmpty());

        assertTrue(dashboardPage.historyScreenBusinessEventsDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenBusinessEventsDropDown.getAttribute("aria-disabled").equals("true"));

        assertTrue(dashboardPage.historyScreenProcessDateRangeDropDown.isDisplayed());
        assertTrue(dashboardPage.historyScreenProcessDateRangeDropDown.getAttribute("aria-disabled").equals("true"));

        assertTrue(dashboardPage.historyScreenChanneleDropDown.isDisplayed());
        assertTrue(dashboardPage.channelDropDown.getAttribute("aria-disabled").equals("true"));

        assertTrue(dashboardPage.resetFilterButton.isDisplayed());

        assertTrue(Driver.getDriver().findElement(By.xpath("//h5")).isDisplayed());
    }

    public String getCaseIdentificationNo() {
        return caseIdentificationNo.get();
    }

    @Given("I created a consumer with population type {string} and following")
    public void iCreatedAConsumerWithPopulationTypeAndFollowing(String populationType, Map<String, String> data) {
        createCaseAndConsumersForHistoryTab(populationType, data);
    }

    public void createCaseAndConsumersForHistoryTab(String populationType, Map<String, String> data) {
        setRegionDetails();
        String dob = "";
        String PregnancyIndicator = "";
        String pregnancyDueDate = "";
        String serviceRegion = "";
        String countyCode = "";
        switch (populationType) {
            case "NEWBORN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, -8, 0);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                countyCode = "1234";
                break;
            case "PREGNANT-WOMEN":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                serviceRegion = "East";
                break;
            case "PREGNANT-WOMEN-NORTH":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -32);
                PregnancyIndicator = "true";
                pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                serviceRegion = "North";
                break;
            case "MEDICAID-GENERAL":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "MEDICAID-GENERAL-ATLANTA":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -60);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Atlanta";
                break;
            case "FOSTER-CARE":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "East";
                break;
            case "CUSTOM":
                if (data.containsKey("age")) {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, -1, -(Integer.parseInt(data.get("age"))));
                } else {
                    dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 0, -15);
                }
                if (data.containsKey("pregnancyIndicator") && data.get("pregnancyIndicator").equals("true")) {
                    PregnancyIndicator = "true";
                    pregnancyDueDate = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 0, 5, 0);
                } else {
                    PregnancyIndicator = "false";
                    pregnancyDueDate = null;
                }
                serviceRegion = data.getOrDefault("serviceRegion", "Atlanta");
                break;
            case "IN-EB-CONSUMER":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -20);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
            case "DC-EB-CONSUMER-below60":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -21);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Northeast";
                break;
            case "DC-EB-CONSUMER-above60":
                dob = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().addDaysToPresentDate("yyyy-MM-dd", 10, -8, -70);
                PregnancyIndicator = "false";
                pregnancyDueDate = (String) null;
                serviceRegion = "Statewide";
                countyCode = "1234";
                break;
        }
        if (!populationType.contains("DC-EB")) {
            consumerDateOfBirth.set(dob);
            APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
            caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPI();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");
            JsonObject caseLoaderReq = caseLoader.generateCaseLoaderVersionforCore();
            consumerFirstName.set(caseLoader.getConsumerFirstName());
            consumerLastName.set(caseLoader.getConsumerLastName());
            externalConsumerId.set(caseLoader.getExternalConsumerId());
            caseIdentificationNo.set(caseLoader.getCaseIdentificationNo());
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", dob);
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyInd", PregnancyIndicator);
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("pregnancyDueDate", pregnancyDueDate);
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCity", getRegionDetails(serviceRegion).get("city"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").
                    get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressCounty", getRegionDetails(serviceRegion).get("county"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressState", getRegionDetails(serviceRegion).get("state"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().get("contacts")
                    .getAsJsonObject().get("address").getAsJsonObject().addProperty("addressZip", getRegionDetails(serviceRegion).get("zip"));
            caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", data.getOrDefault("consumerRole", "Primary Individual"));
            if (populationType.equals("IN-EB-CONSUMER")) {
                caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                        .getAsJsonObject("case").addProperty("caseIdentificationNumberType", "State");
            }
            if (data.containsKey("caseIdentificationNo")) {
                if (data.get("caseIdentificationNo").equalsIgnoreCase("new")) {
                    caseIdentificationNo.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber);
                    System.out.println("NUMBER GENERATED IS : " + caseIdentificationNo.get());
                    caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo.get());
                } else if (data.get("caseIdentificationNo") != null && !data.get("caseIdentificationNo").equals("")) {
                    caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                            .getAsJsonObject("case").addProperty("caseIdentificationNumber", data.get("caseIdentificationNo"));
                }
            }
            for (String key : data.keySet()) {
                switch (key) {
                    case "requestedBy":
                        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").addProperty("requestedBy", data.get("requestedBy"));
                        break;
                    case "consumerRole":
                        caseLoaderReq.getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", data.get("consumerRole"));
                        break;
                }
            }
            caseLoader.setRequestParams(caseLoaderReq);
            caseLoader.runCaseLoaderEligibilityEnrollmentAPI();
            waitFor(3);
            consumerSSN.set(caseLoader.getConsumerSSN());
            APIConsumerSearchController consumerSearch = new APIConsumerSearchController();
            consumerSearch.setConsumerSSN(consumerSSN.get());
            consumerSearch.initiatedCaseConsumerContactSearchAPI();
            consumerSearch.runConsumerSearchAPI("consumerSSN");
            consumerId.set(consumerSearch.getConsumerIdFromSearchResults());
            caseCorrelationId.set(caseLoaderReq.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").get("correlationId").getAsString());
            APICaseRestController caseController = new APICaseRestController();
            caseController.setCaseCorrelationId(caseCorrelationId.get());
            caseController.initiateSearchCaseRecords();
            caseController.searchCaseByCorrelationId();
            String tempCaseID = caseController.getCreatedCaseId();
            if (tempCaseID != null)
                caseId.set(tempCaseID);
        } else {
            consumerDateOfBirth.set(dob);
            APICaseLoaderEligibilityEnrollmentController caseLoader = new APICaseLoaderEligibilityEnrollmentController();
            caseLoader.initiatedCaseLoaderEligibilityEnrollmentAPIForDC();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE_DC.json");
            JsonObject caseLoaderReq = caseLoader.generateCaseLoaderRequestForDC();
            consumerFirstName.set(caseLoader.getConsumerFirstName());
            consumerLastName.set(caseLoader.getConsumerLastName());
            externalConsumerId.set(caseLoader.getExternalConsumerId());
            caseIdentificationNo.set(caseLoader.getCaseIdentificationNo());
            caseLoaderReq.getAsJsonArray("consumerRequests").get(0).getAsJsonObject().addProperty("dateOfBirth", dob);
            caseLoader.setRequestParams(caseLoaderReq);
            caseLoader.runCaseLoaderEligibilityEnrollmentAPIForDC();
            waitFor(3);
            consumerId.set(caseLoader.getConsumerIdFromCaseLoaderRun());
            caseId.set(caseLoader.getCaseIdFromCaseLoaderRun());
            caseLoader.initiateSyncContactInfoForDC();
            caseLoader.runSyncContactInfoForDC();
        }
        APIEnrollmentController enrollment = new APIEnrollmentController();
        enrollment.setConsumerId(this.consumerId.get());
        APIEEDigitalIntegrationController digitalApi = new APIEEDigitalIntegrationController();
        digitalApi.setConsumerIds(this.consumerId.get());
        APIEligibilityController eligibility = new APIEligibilityController();
        eligibility.setConsumerId(this.consumerId.get());
        if (data.containsKey("saveConsumerInfo")) {
            Api_Body api_req = new EventBuilder()
                    .body("{\"firstName\": \"" + consumerFirstName.get() + "\", \"lastName\": \"" + consumerLastName.get() + "\", " +
                            "\"consumerSSN\": \"" + consumerSSN.get() + "\", \"consumerId\": \"" + consumerId.get() + "\", " +
                            "\"caseCorrelationId\": \"" + caseCorrelationId.get() + "\", \"caseId\": \"" + caseId.get() + "\", " +
                            "\"externalConsumerId\": \"" + externalConsumerId.get() + "\", " +
                            "\"caseIdentificationNumber\": \"" + caseIdentificationNo.get() + "\"}")
                    .name(data.get("saveConsumerInfo"))
                    .build();
            stg.addProduced(api_req);
            System.out.println("api_common.defineValue(" + data.get("saveConsumerInfo") + ".firstName) = " + API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("saveConsumerInfo") + ".firstName"));
        }
    }

    private JsonObject updateRequestWithProviderEnrollmentInfo(Map<String, String> data, JsonObject detailsToSave) {
        if (!data.containsKey("enrollmentProviders") || data.get("enrollmentProviders").equalsIgnoreCase("No")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("enrollmentProviders", (String) null);
        } else if (data.containsKey("enrollmentProviders") && data.get("enrollmentProviders").equalsIgnoreCase("random")) {
            APIProviderController apiProvider = new APIProviderController();
            apiProvider.iInitiatedPlanManagementForSearchProvider();
            Map<String, String> map = new HashMap<String, String>();
            map.put("providerSearch.planName", newPlanName.get());
            apiProvider.updateProviderSearch("providerSearch", map);
            JsonObject providerSearchResponse = apiProvider.getProviderSearchResponse();
            JsonObject providerObj = providerSearchResponse.getAsJsonObject("providerSearchResponse").getAsJsonArray("content").get(0).getAsJsonObject();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerFirstName", providerObj.get("firstName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerMiddleName", providerObj.get("middleName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerLastName", providerObj.get("lastName").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerNpi", providerObj.get("npi").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerType", providerObj.getAsJsonArray("providerType").get(0)
                            .getAsJsonObject().get("providerTypeCd").getAsString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonArray().get(0).getAsJsonObject().getAsJsonArray("enrollmentProviders").get(0)
                    .getAsJsonObject().addProperty("providerId", providerObj.get("planProviderId").getAsString());

            if (detailsToSave != null) {
                detailsToSave.addProperty("providerId", providerObj.get("planProviderId").getAsString());
                detailsToSave.addProperty("providerFirstName", providerObj.get("firstName").getAsString());
                detailsToSave.addProperty("providerMiddleName", providerObj.get("middleName").getAsString());
                detailsToSave.addProperty("providerLastName", providerObj.get("lastName").getAsString());
                detailsToSave.addProperty("providerNpi", providerObj.get("npi").getAsString());
                detailsToSave.addProperty("providerType", providerObj.getAsJsonArray("providerType").get(0)
                        .getAsJsonObject().get("providerTypeCd").getAsString());
                detailsToSave.addProperty("providerPhoneNumber", providerObj.getAsJsonArray("providerAddress").get(0)
                        .getAsJsonObject().getAsJsonArray("phoneNumbers").get(0).getAsJsonObject().get("phoneNumber").getAsString());
                detailsToSave.addProperty("pcp", providerObj.get("firstName").getAsString() + " " + providerObj.get("lastName").getAsString());
                detailsToSave.addProperty("mcoPhoneNumber", APIEnrollmentController.getMcoPhoneNumberFromPlanCode(newPlanCode.get()));
            }
        }
        return detailsToSave;
    }

    @When("I provide the skeletal enrollment and eligibility information to create enrollment")
    public void createEligibilityAndEnrollemntRecordWithSkeletalPayload(Map<String, String> data) {

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/enrollment/createEligibilityAndEnrollmentSkeletalPayload.json");

        if (data.containsKey("consumerId")) {
            String id = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(data.get("consumerId")).toString();
            System.out.println("id = " + id);
            consumerId.set(id.contains(".") ? id.substring(0, id.indexOf(".")) : id);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId",
                    Integer.parseInt(consumerId.get()));
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", Integer.parseInt(consumerId.get()));
        JsonObject eligibility = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").get(0).getAsJsonObject();
        eligibility.addProperty("consumerId", Integer.parseInt(consumerId.get()));
        eligibility.addProperty("recordId", Integer.parseInt(data.get("eligibilityRecordId")));

        eligibility.getAsJsonObject("coreEligibility").addProperty("consumerId", Integer.parseInt(consumerId.get()));
        eligibility.getAsJsonObject("coreEligibility").addProperty("genericFieldText2", Integer.parseInt(data.get("genericFieldText2")));

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").remove(0);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("eligibilities").add(eligibility);

        if (data.containsKey("recordId"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("recordId", Integer.parseInt(data.get("recordId")));

        if (data.containsKey("requestedBy"))
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("requestedBy", Integer.parseInt(data.get("requestedBy")));


        synchronized (requestParams) {
            requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        }
        if (data.containsKey("saveEligibilityEventName")) {
            Api_Body api_req = new EventBuilder()
                    .body(requestParams.toString())
                    .name(data.get("saveEligibilityEventName"))
                    .build();
            stg.addProduced(api_req);
        }
    }


}



