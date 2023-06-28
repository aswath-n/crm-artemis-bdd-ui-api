package com.maersk.crm.api_step_definitions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.TM_CreateProjectStepDef;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

class DateUtil {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}

public class APIPlanManagementController extends CRMUtilities implements ApiBase {
    //
    private static String planManagementBaseURI = ConfigurationReader.getProperty("apiPlanManagementURI");
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<JsonObject> newRequestParams = ThreadLocal.withInitial(JsonObject::new);
    /*private static APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();*/
    //private static BrowserUtils browserUtils = new BrowserUtils();
    private final ThreadLocal<String> countyCode = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> planId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> planName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> autoAssignmentInd = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> endDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> enrollmentStartDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> enrollmentEndDate = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> newDate = ThreadLocal.withInitial(String::new);
    private String apiUserID = ConfigurationReader.getProperty("apiMaxId");
    private String apiPassword = ConfigurationReader.getProperty("apiPassword");
    private String apiProjectId = ConfigurationReader.getProperty("apiProjectId");
    private String baseURI = ConfigurationReader.getProperty("apiPlanManagementURI");

    private String getCitiesById = "mars/eb/region/cities/{sdaId}";
    private String getCounties = "mars/eb/region/counties";
    private String getCountyById = "mars/eb/region/counties/{sdaId}";
    private String geographicalinfo = "mars/eb/region/geographicalinfo";
    private String geographicalinfoById = "mars/eb/region/geographicalinfo/{sdaId}";
    private String planDetailByPlanId = "mars/eb/plan/plan/{planId}";
    private static String planFile = "mars/eb/plan/planfile";

    private String getPlanNames = "mars/eb/plan/plannames";
    private String searchplans = "mars/eb/plan/plans";
    // --  Updating and saving Plan Details
    private String planUpdate = "mars/eb/plan/planupdate";
    private String getProgramType = "mars/eb/region/programtype";
    private String getProgramTypeById = "mars/eb/region/programtype/{sdaId}";
    private static String regionFile = "mars/eb/region/regionfile";
    private String getServiceDeliveryAreas = "mars/eb/region/servicedeliveryareas";
    private String getServiceRegionInfoById = "mars/eb/region/serviceregioninfo/{sdaId}";
    private String searchServiceRegions = "mars/eb/region/serviceregions";
    //POST /mars/eb/region/serviceregions/counties
    private String searchStatewideServiceRegions = "mars/eb/plan/statewide";
    private String getSubProgramType = "mars/eb/region/subprogramtype";
    private String searchplanzipcodes = "mars/eb/region/zipcodes";
    private String getPLanZipcodesByCityName = "mars/eb/region/zipcodes/cities/{city}";
    private final ThreadLocal<String> region_id = ThreadLocal.withInitial(String::new);
    TMUtilities tmUtility = new TMUtilities();

    @Given("I initiated Plan Management for service region API")
    public void i_initiated_plan_controller_for_service_region_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(regionFile);
    }


    @When("I upload the service regions file {string} with fileStatus {string}")
    public void i_upload_the_service_regions_file(String fileName, String filestatus) {

        try {
            BrowserUtils.classLoader("testData/tm/planAndRegionConfig/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String absPath = BrowserUtils.replaceTrimURL.get();
        System.out.println("Printing Abs path: " + absPath);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithKey("File", absPath);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

    }


    @Then("I verify the file upload message {string}")
    public void i_verify_the_upload_message(String uploadMessage) {
        String resposneMsg = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message");
        assertTrue(resposneMsg.contains(uploadMessage));

    }


    @Given("I initiated Plan Management for Plan API")
    public void i_initiated_plan_controller__for_plan_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planFile);
    }

    /**
     * Upload file
     */
    public void uploadFile(String fileName) {
        try {
            BrowserUtils.classLoader("testData/tm/planAndRegionConfig/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String absPath = BrowserUtils.replaceTrimURL.get();
        System.out.println("Printing Abs path: " + absPath);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithKey("File", absPath);
    }

    /**
     * upload and veriy the status
     */
    @When("I upload the plan regions file {string} with fileStatus {string}")
    public void i_upload_the_plan_file_1(String fileName, String filestatus) {
        switch (fileName) {
            case "File Sheet Name":
                uploadFile("Plan_Configuration_SheetName.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "File Sheet order":
                uploadFile("Plan_Configuration_SheetOrder.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "Plan TestFile Success":
                uploadFile("Plan_Configuration_Success.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "Plan TestFile Success BLCRM":
                uploadFile("Plan_Configuration_BLCRM.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "File Failure EL Type":
                uploadFile("Plan_FailureTest_EL-PlanCode.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "Plan Config INEB":
                uploadFile("Plan_Configuration_INEB.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "Plan SubProgram Type different characters BLCRM":
                uploadFile("Plan_Configuration_BLCRM_TestFile_FAIL_subPrgmType_formatLength.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;

            case "Plan Program Type different characters BLCRM":
                uploadFile("Plan_Configuration_BLCRM_TestFile_FAIL_PrgmType_formatLength.xlsx");
                System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(filestatus));
                break;
        }
    }

    /**
     * Verify the message
     */
    @Then("I verify the plan file upload message {string}")
    public void i_verify_the_plan_upload_message(String uploadMessage) {
        switch (uploadMessage) {
            case "Upload Successful":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message"), "Upload Successful");
                break;

            case "Sheet Name":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message"), "File sheets are not named correctly");
                break;

            case "Sheet Order":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message"), "File sheets are not in the correct order");
                break;

            case "Eligiblity limit Missing":
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("message"), "The Eligibility Limitations sheet is missing required information");
                break;

        }
    }

    @Given("I initiated get Service Delivery Areas via API")
    public void i_initiated_get_service_delivery_areas_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getServiceDeliveryAreas);
    }

    @When("I run get Service Delivery Areas")
    public void i_run_get_service_areas() {
        String url = baseURI + "/" + getServiceDeliveryAreas;
        System.out.println("SDA URL" + url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("Printing Status" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can verify get Service Delivery Areas API response is not empty")
    public void i_can_verify_get_service_delivery_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList serviceRegions = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRegions");
        //System.out.println(serviceRegions);
        boolean serviceRegionsFound = false;
        if (serviceRegions.size() > 0) {
            serviceRegionsFound = true;
        }
        assertTrue(serviceRegionsFound);
    }

    @Then("I can verify get Service Delivery Areas API response is empty")
    public void i_can_verify_get_service_delivery_api_response_is_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList serviceRegions = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRegions");
        System.out.println(serviceRegions);
        boolean serviceRegionsFound = false;
        if (serviceRegions.size() == 0) {
            serviceRegionsFound = true;
        }
        assertTrue(serviceRegionsFound);
    }

    private String get_region_id(String regionName) {
        String region_id = "";
        if (!regionName.isEmpty()) {
            i_initiated_get_service_delivery_areas_api();
            i_run_get_service_areas();
            JsonArray serviceRegions = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("serviceRegions").getAsJsonArray();
            System.out.println("Printing Service regions" + serviceRegions);
            try {
                for (int k = 0; k < serviceRegions.size(); k++) {
                    System.out.println(serviceRegions.get(k).getAsJsonObject().get("sdaName").toString());
                    if (serviceRegions.get(k).getAsJsonObject().get("sdaName").toString().trim().contains(regionName)) {
                        region_id = serviceRegions.get(k).getAsJsonObject().get("sdaId").toString();
                        break;
                    }
                }
                System.out.println(regionName + " Region ID found " + region_id);
            } catch (Exception e) {
                System.out.println("There is no Region ID found for '" + regionName + "'.");
            }
        } else {
            throw new java.lang.RuntimeException("parameter 'regionName' cannot be empty.");
        }
        return region_id;
    }

    @Given("I initiated get counties via API")
    public void i_initiated_get_counties_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCounties);
    }

    @When("I run get Counties")
    public void i_run_get_counties() {
        String url = baseURI + "/" + getCounties;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get Counties API response is not empty")
    public void i_can_verify_get_counties_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList counties = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("countys");
        System.out.println(counties);
        boolean countiesFound = false;
        if (counties.size() > 0) {
            countiesFound = true;
        }
        assertTrue(countiesFound);
    }

    private String get_county_code(String countyName) {
        countyCode.set("");
        if (!countyName.isEmpty()) {
            i_initiated_get_counties_api();
            i_run_get_counties();
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            JsonArray counties = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("countys").getAsJsonArray();
            //System.out.println(counties);
            try {
                for (int k = 0; k < counties.size(); k++) {
                    String county_name = counties.get(k).toString().split("-")[0].trim().replace("\"", "");
                    String county_code = counties.get(k).toString().split("-")[1].trim().replace("\"", "");
                    //System.out.println(county_name+":"+county_code);
                    if (county_name.contains(countyName)) {
                        countyCode.set(county_code);
                        break;
                    }
                }
                System.out.println("County Name:County Code = " + countyName + ":" + countyCode.get());
            } catch (Exception e) {
                System.out.println("There is no Region ID found countyCodefor '" + countyName + "'.");
            }
        } else {
            throw new java.lang.RuntimeException("parameter 'countyName' cannot be empty.");
        }
        if (countyCode.get().length() > 0) {
            return countyCode.get();
        } else {
            throw new java.lang.RuntimeException("parameter 'countyName' as '" + countyName + "' cannot be found.");
        }
    }

    @Given("I initiated get plan name via API")
    public void i_initiated_get_plan_names_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPlanNames);
    }

    @When("I run get Plan Names")
    public void i_run_get_plan_names() {
        String url = baseURI + "/" + getPlanNames;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get Plan Name API response is not empty")
    public void i_can_verify_get_plan_name_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList mcoContacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("mcoContracts");
        System.out.println(mcoContacts);
        boolean mcoContactsFound = false;
        if (mcoContacts.size() > 0) {
            mcoContactsFound = true;
        }
        assertTrue(mcoContactsFound);
    }

    @Then("I can verify plan code is not empty")
    public void i_can_verify_plan_code_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList mcoContacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("mcoContracts");
        List<String> plancode = new ArrayList<>();
        for (int i = 0; i < mcoContacts.size(); i++) {
            for (Object name : mcoContacts) {
                plancode.add(name.toString().split("planCode=")[1].substring(0, 8));
            }
        }
        boolean plancodefound = false;
        if (plancode.size() > 0) {
            plancodefound = true;
        }
        assertTrue(plancodefound);
    }

    @Then("I can verify get Plan Name API response is empty")
    public void i_can_verify_get_plan_name_api_response_is_empty() {
        ArrayList mcoContacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("mcoContracts");
        System.out.println(mcoContacts);
        boolean mcoContactsFound = false;
        if (mcoContacts.size() == 0) {
            mcoContactsFound = true;
        }
        assertTrue(mcoContactsFound);
    }

    @Given("I initiated get program type via API")
    public void i_initiated_get_program_type_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProgramType);
    }


    @When("I run get subprogram Type")
    public void i_run_get_subprogram_Type() {
        String url = baseURI + "/" + getSubProgramType;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        // api.getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I run get Program Type")
    public void i_run_get_program_type() {
        String url = baseURI + "/" + getProgramType;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get Program Type API response is not empty")
    public void i_can_verify_get_program_type_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList programTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("programType");
        System.out.println(programTypes);
        boolean programTypesFound = false;
        if (programTypes.size() > 0) {
            programTypesFound = true;
        }
        assertTrue(programTypesFound);
    }

    @Then("I verify no. of program types records match with uploaded file")
    public void i_can_verify_program_type_records() {
        ArrayList programTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("programType");
        int listsize = programTypes.size();
        assertEquals(listsize, tmUtility.getProgramTypeColumnRecords("Sheet1", "Program Type"));
    }

    @Then("I verify no. of subprogram types records match with uploaded file")
    public void i_can_verify_subprogram_type_records() {
        ArrayList subprogramTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("subProgramType");
        int listsize = subprogramTypes.size();
        assertEquals(listsize, tmUtility.getsubProgramTypeColumnRecords("Sheet1", "Sub Program Type"));
    }

    @Given("I initiated sub get program type via API")
    public void i_initiated_get_sub_program_type_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getSubProgramType);
    }

    @When("I can provide details with following information to get sub program type")
    public void i_can_provide_information_to_get_sub_rogram_type(Map<String, String> dataTable) {
        requestParams.set(new JsonObject());
        for (String data : dataTable.keySet()) {
            switch (data) {
                default:
                    if (data.contains("serviceRegion")) {
                        if (!dataTable.get(data).isEmpty()) {
                            requestParams.get().addProperty("serviceRegion", "");
                        } else {
                            requestParams.get().addProperty("serviceRegion", dataTable.get(data));
                        }
                    } else if (data.contains("serviceRegion")) {
                        if (!dataTable.get(data).isEmpty()) {
                            requestParams.get().addProperty("programType", "");
                        } else {
                            requestParams.get().addProperty("programType", dataTable.get(data));
                        }
                    } else {
                        requestParams.get().addProperty(data, dataTable.get(data));
                    }
            }
        }
        System.out.println(requestParams.get());
    }


    @When("I run post sub Program Type")
    public void i_run_post_sub_program_type_api() {
        String url = baseURI + "/" + getSubProgramType;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get Sub Program Type API response is not empty")
    public void i_can_verify_get_sub_program_type_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList subProgramTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("subProgramType");
        System.out.println(subProgramTypes);
        boolean subProgramTypesFound = false;
        if (subProgramTypes.size() > 0) {
            subProgramTypesFound = true;
        }
        assertTrue(subProgramTypesFound);
    }

    @Given("I initiated get county via API with county name {string}")
    public void i_initiated_get_county_name_api(String countyName) {
        if (!countyName.isEmpty()) {
            String county_id = "";
            county_id = get_region_id(countyName);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
            getCountyById = getCountyById.replace("{sdaId}", county_id);
        } else {
            throw new java.lang.RuntimeException("parameter 'countyName' cannot be empty.");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCountyById);
    }

    @When("I run get county name")
    public void i_run_get_county_name() {
        String url = baseURI + "/" + getCountyById;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Given("I initiated search Service Regions via API")
    public void i_initiated_search_service_regions_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchServiceRegions);
    }

    @When("I search service region with region name '{string}', County Name '{string}' and Zip Code '{string}' via API")
    public void i_search_service_region_api(String regionName, String countyName, String zipCode) {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("serviceDeliveryArea", regionName);
        requestParams.get().addProperty("countyCode", countyName);
        requestParams.get().addProperty("zipCode", zipCode);
        System.out.println(requestParams.get());
    }

    @When("I run post Service Region Search")
    public void i_run_post_service_region_search_api() {
        String url = baseURI + "/" + searchServiceRegions;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Given("I initiated get statewide region search via API")
    public void i_initiated_get_statewide_region_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchStatewideServiceRegions);
    }

    @When("I run get Statewide Service Region Search via API")
    public void i_run_get_statewide_service_region_search_api() {
        String url = baseURI + "/" + searchStatewideServiceRegions;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Given("I initiated plan search via API")
    public void i_initiated_plan_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchplans);
    }

    @When("I search plan via API with '{string}','{string}','{string}','{string}','{string}','{string}','{string}' and '{string}'")
    public void i_search_plan_api(String stateWide, String planName, String planCode, String serviceRegion, String programType, String subProgramType, String countyCode, String zipCode) {

        if (!serviceRegion.isEmpty()) {
            region_id.set(get_region_id(serviceRegion));
            System.out.println("Printing Region ID Serach " + region_id.get());
        }

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("tenantmanager/PlanController/planSearch.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("stateWide", Boolean.parseBoolean(stateWide));
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("planName", planName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("planCode", planCode);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("serviceRegion", serviceRegion);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("programType", programType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("subProgramType", subProgramType);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("countyCode", countyCode);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("zipCode", zipCode);
        if (region_id.get() != "") {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("sdaId", Integer.valueOf(region_id.get()));
            System.out.println("Printing Region ID" + region_id.get());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("planSearch").getAsJsonObject().addProperty("sdaId", (String) null);
        }
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(requestParams.get());
    }

    @When("I run post Plan Search")
    public JsonObject i_run_post_plan_search_api() {
        i_initiated_plan_search_api();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    @Given("I initiated Service Region Info API with region name {string}")
    public void i_initiated_service_region_info_by_region_name_api(String regionName) {
        String region_id = "";
        if (!regionName.isEmpty()) {
            region_id = get_region_id(regionName);
            getServiceRegionInfoById = getServiceRegionInfoById.replace("{sdaId}", region_id);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getServiceRegionInfoById);
    }

    @When("I run get service region info by region name")
    public void i_run_get_service_region_info_by_region_name() {
        String url = baseURI + "/" + getServiceRegionInfoById;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get service region information API response is not empty")
    public void i_can_verify_service_region_info_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean serviceRegionDetailFound = false;
        ArrayList serviceRegionProgramTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("serviceRegionDetail.Medicaid");
        System.out.println(serviceRegionProgramTypes);
        if (serviceRegionProgramTypes.size() > 0) {
            serviceRegionDetailFound = true;
        }
        assertTrue(serviceRegionDetailFound);
    }

    @Given("I initiated geographical info via API")
    public void i_initiated_geographical_info_via_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(geographicalinfo);
    }

    @When("I can provide geographical information with county {string}, ZipCode {string} and city {string}")
    public void i_can_provide_geopgraphical_information_by_fields(String countyName, String zipCode, String city) {
        newRequestParams.set(new JsonObject());
        countyCode.set("");
        if (!countyName.isEmpty()) {
            countyCode.set(get_county_code(countyName));
        }
        newRequestParams.get().addProperty("countyCode", countyCode.get());
        if (zipCode.isEmpty()) {
            newRequestParams.get().addProperty("zipCode", "");
        } else {
            newRequestParams.get().addProperty("zipCode", zipCode);
        }

        if (city.isEmpty()) {
            newRequestParams.get().addProperty("city", "");
        } else {
            newRequestParams.get().addProperty("city", city);
        }
        System.out.println(newRequestParams.get());
        i_initiated_geographical_info_via_api();
        requestParams.set(newRequestParams.get());
    }

    @When("I run post geographical info")
    public void i_run_post_geographical_info_api() {
        String url = baseURI + "/" + geographicalinfo;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify geographical info API response is not empty")
    public void i_can_verify_geographical_info_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList geoGraphicalDetails = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("geoGraphicalDetails");
        System.out.println(geoGraphicalDetails);
        boolean geoGraphicalDetailsFound = false;
        if (geoGraphicalDetails.size() > 0) {
            geoGraphicalDetailsFound = true;
        }
        assertTrue(geoGraphicalDetailsFound);
    }

    @Given("I initiated cities using API based on service delivery area {string}")
    public void i_initiated_geographical_info_by_county_name_api(String regionName) {
        if (!regionName.isEmpty()) {
            String region_id = get_region_id(regionName);
            getCitiesById = getCitiesById.replace("{sdaId}", region_id);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCitiesById);
    }

    @When("I run get cities by service delivery area via API")
    public void i_run_get_cities_by_service_delivery_area_api() {
        String url = baseURI + "/" + getCitiesById;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get Cities API response is not empty")
    public void i_can_verify_get_cities_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean citiesDetailFound = false;
        ArrayList citiesDetails = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("cities");
        System.out.println(citiesDetails);
        if (citiesDetails.size() > 0) {
            citiesDetailFound = true;
        }
        assertTrue(citiesDetailFound);
    }

    @Given("I initiated geographical info using API based on service delivery area {string}")
    public void i_initiated_geographical_info_api_by_region(String regionName) {
        if (!regionName.isEmpty()) {
            String region_id = get_region_id(regionName);
            geographicalinfoById = geographicalinfoById.replace("{sdaId}", region_id);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(geographicalinfoById);
    }

    @When("I run get geographical info by service delivery area via API")
    public void i_run_get_geographical_info_by_service_delivery_area_api() {
        String url = baseURI + "/" + getCitiesById;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get geographical info API response is not empty")
    public void i_can_verify_get_geographical_info_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean geoGraphicalDetailsFound = false;
        ArrayList geoGraphicalDetails = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("geoGraphicalDetails");
        System.out.println(geoGraphicalDetails);
        if (geoGraphicalDetails.size() > 0) {
            geoGraphicalDetailsFound = true;
        }
        assertTrue(geoGraphicalDetailsFound);
    }

    @Given("I initiated program type using API based on service delivery area {string}")
    public void i_initiated_program_type_api_by_region(String regionName) {
        if (!regionName.isEmpty()) {
            String region_id = get_region_id(regionName);
            getProgramTypeById = getProgramTypeById.replace("{sdaId}", region_id);
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getProgramTypeById);
    }

    @When("I run get program type by service delivery area via API")
    public void i_run_get_program_type_by_service_delivery_area_api() {
        String url = baseURI + "/" + getCitiesById;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify get program type API response by Region Id is not empty")
    public void i_can_verify_get_program_type_api_response__by_id_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean geoGraphicalDetailsFound = false;
        ArrayList geoGraphicalDetails = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("programType");
        System.out.println(geoGraphicalDetails);
        if (geoGraphicalDetails.size() > 0) {
            geoGraphicalDetailsFound = true;
        }
        assertTrue(geoGraphicalDetailsFound);
    }

    @Given("I initiated plan zipcodes search via API")
    public void i_initiated_plan_zipcodes_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchplanzipcodes);
    }

    @When("I run get plan zipcodes search via API")
    public void i_run_get_plan_zipcodes_search_api() {
        String url = baseURI + "/" + searchplanzipcodes;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can verify get plan zipcodes search API response is not empty")
    public void i_can_verify_plan_zipcodes_search_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean serviceRegionDetailFound = false;
        ArrayList serviceRegionProgramTypes = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("zipcodes");
        System.out.println(serviceRegionProgramTypes);
        if (serviceRegionProgramTypes.size() > 0) {
            serviceRegionDetailFound = true;
        }
        assertTrue(serviceRegionDetailFound);
    }

    @Given("I initiated plan Zipcodes using API based on Cities {string}")
    public void i_initiated_plan_zipcodes_api_by_cities(String cityName) {
        if (!cityName.isEmpty()) {
            getPLanZipcodesByCityName = getPLanZipcodesByCityName.replace("{city}", cityName);
        } else {
            throw new java.lang.RuntimeException("parameter 'cityName' cannot be empty.");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getPLanZipcodesByCityName);
    }

    @When("I run get Plan Cities by city name via API")
    public void i_run_get_plan_ctiries_by_city_name_api() {
        String url = baseURI + "/" + getPLanZipcodesByCityName;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify  get Plan Cities by city name is not empty")
    public void i_can_verify_get_plan_cities_api_response__by_city_name_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        boolean geoGraphicalDetailsFound = false;
        ArrayList geoGraphicalDetails = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("zipcodes");
        System.out.println(geoGraphicalDetails);
        if (geoGraphicalDetails.size() > 0) {
            geoGraphicalDetailsFound = true;
        }
        assertTrue(geoGraphicalDetailsFound);
    }

    @Given("I initiated a plan detail via API by plan name {string}")
    public void i_initiated_a_plan_detail_api(String plan_Name) {
        planId.set("");
        planName.set(plan_Name);
        i_initiated_plan_search_api();
        i_search_plan_api("False", planName.get(), "", "", "", "", "", "");
        i_run_post_plan_search_api();
        JsonArray mcoContacts = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("mcoContracts").getAsJsonArray();
        //System.out.println(mcoContacts);
        try {
            for (int k = 0; k < mcoContacts.size(); k++) {
                //System.out.println(mcoContacts.get(k).getAsJsonObject().get("planName").toString());
                if (mcoContacts.get(k).getAsJsonObject().get("planName").toString().trim().contains(planName.get())) {
                    planId.set(mcoContacts.get(k).getAsJsonObject().get("planId").toString());
                    break;
                }
            }
            System.out.println(planId.get());
        } catch (Exception e) {
            System.out.println("There is no Region ID found for '" + planName.get() + "'.");
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        planDetailByPlanId = planDetailByPlanId.replace("{planId}", planId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planDetailByPlanId);
    }

    @When("I run get plan detail via API")
    public void i_run_get_plan_detail_api() {
        String url = baseURI + "/" + planDetailByPlanId;
        System.out.println(url);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I can verify get plan detail API response is not empty")
    public void i_can_verify_get_plan_detail_api_response_is_not_empty() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        String actual_plan_id = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("plan").getAsJsonObject().get("planId").toString();
        String actual_plan_name = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("plan").getAsJsonObject().get("planName").toString();
        System.out.println("Actual Plan Id:" + actual_plan_id);
        System.out.println("Actual Plan Name:" + actual_plan_name);
        assertEquals(actual_plan_id, planId.get().toString());
        assert(actual_plan_name.contains(planName.get()));
    }

    @Given("I initiated plan update via API for plan name {string}")
    public void i_initiated_plan_update_for_a_plan_api(String plan_Name) {
        i_initiated_a_plan_detail_api(plan_Name);
        i_run_get_plan_detail_api();
        requestParams.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject);
        System.out.println("Get Here" + requestParams.get().toString());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planUpdate);
    }

    private String get_day_forward(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date end_Date = sdf.parse(dateString);
        Date new_Date = DateUtil.addDays(end_Date, 1);
        String nDate = sdf.format(new_Date);
        System.out.println(nDate);
        return nDate;
    }

    @When("I update a plan with parameter {string} with value {string}")
    public void i_update_a_plan_api(String param, String value) throws ParseException {
        switch (param) {
            case "autoAssignmentInd":
                autoAssignmentInd.set(requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("autoAssignmentInd").toString());
                System.out.println(autoAssignmentInd.get());
                if (autoAssignmentInd.get().toLowerCase().contains("false")) {
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("autoAssignmentInd", true);
                    autoAssignmentInd.set("true");
                } else {
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("autoAssignmentInd", false);
                    autoAssignmentInd.set("false");
                }
                break;
            case "endDate":
                endDate.set(requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("endDate").toString().replace("\"", ""));
                System.out.println("Existing Contract End Date:" + endDate.get());
                if (endDate.get().contains("null")) {
                    newDate.set("12/12/2023");
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("endDate", newDate.get());
                } else {
                    newDate.set(get_day_forward(endDate.get()));
                    System.out.println(newDate.get());
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("endDate", newDate.get());
                }
                break;
            case "enrollmentStartDate":
                enrollmentStartDate.set(requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("enrollmentStartDate").toString().replace("\"", ""));
                System.out.println("Existing Enrollment Start Date:" + enrollmentStartDate.get());
                if (enrollmentStartDate.get().contains("null")) {
                    newDate.set("12/12/2023");
                    System.out.println("New Enrollment Start Date:" + newDate.get());
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("enrollmentStartDate", newDate.get());
                } else {
                    Date date = new Date();
                    newDate.set(new SimpleDateFormat("MM/dd/yyyy").format(date));
                    System.out.println("New Enrollment Start Date:" + newDate.get());
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("enrollmentStartDate", newDate.get());
                }
                break;
            case "enrollmentEndDate":
                enrollmentEndDate.set(requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("enrollmentEndDate").toString().replace("\"", ""));
                System.out.println("Existing Entollment End Date:" + enrollmentEndDate.get());
                if (enrollmentEndDate.get().contains("null")) {
                    newDate.set("12/12/2023");
                    System.out.println("New Enrollment End Date:" + newDate.get());
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("enrollmentEndDate", newDate.get());
                } else {
                    newDate.set(get_day_forward(enrollmentEndDate.get()));
                    System.out.println("New Enrollment End Date:" + newDate.get());
                    requestParams.get().getAsJsonObject("plan").getAsJsonObject().addProperty("enrollmentEndDate", newDate.get());
                }
                break;
        }
        System.out.println(requestParams.get());
    }

    @When("I run post plan update")
    public void i_run_post_plan_updated_api() {
        String url = baseURI + "/" + planUpdate;
        System.out.println("URL: " + url);
        //System.out.println("here1"+"{\"plan\" "+":"+ requestParams.getAsJsonObject("plan")+"}");

        String requestParams1 = "{\"plan\" " + ":" + requestParams.get().getAsJsonObject("plan") + "}";
        //System.out.println("here2"+requestParams1);
        newRequestParams.set(new Gson().fromJson(requestParams1, JsonObject.class));
        System.out.println("Request params" + newRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(newRequestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I can verify plan update message via API with expected message {string}")
    public void i_can_verify_plan_update_api_response_message(String expected_message) {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        String actual_message = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.get("message").toString();
        assert (actual_message.trim().contains(expected_message));
    }

    @Then("I can verify plan update via API updated the parameter {string} with value {string}")
    public void i_can_verify_plan_update_api_response_message(String param, String value) {
        switch (param) {
            case "autoAssignmentInd":
                String param_value = requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("autoAssignmentInd").toString();
                System.out.println("Actual autoAssignmentInd : " + param_value);
                System.out.println("Expected autoAssignmentInd : " + autoAssignmentInd.get());
                assert (param_value.trim().contains(autoAssignmentInd.get()));
                break;
            case "endDate":
                endDate.set(requestParams.get().getAsJsonObject("plan").getAsJsonObject().get("endDate").toString().replace("\"", ""));
                System.out.println("Existing Contract End Date:" + endDate.get());
                assert (endDate.get().trim().contains(newDate.get()));
                break;
        }
    }


    /*
     * I upload the service region file prior to plan file--Sucessful file
     *
     * */

    @Given("I upload the service region file prior to plan file")
    public void i_upload_the_service_region_prior_to_Planfile() {

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(regionFile);
        try {
            BrowserUtils.classLoader("testData/tm/planAndRegionConfig/Region_success.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String absPath = BrowserUtils.replaceTrimURL.get();
        System.out.println("Printing Abs path: " + absPath);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithKey("File", absPath);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        waitFor(3);
    }

    @Given("I upload the Plan file")
    public void i_upload_the_Planfile() {
        //Uploading Service region prior to Plan
        i_upload_the_service_region_prior_to_Planfile();

        //Uploading the Plan config file
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planFile);
        uploadFile("Plan_Configuration_Success.xlsx");
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Given("I will receive a success message {string}")
    public void I_will_receive_a_success_message(String mess) {
        org.testng.Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(mess));
    }

    @Given("I search plan via API with {string}")
    public void i_search_plan_via_API_with(String planName) {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("planName", planName);
    }

    @When("I run the post Plan Search")
    public void i_run_the_post_Plan_Search() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode != 200) {
            System.out.println("API Response:");
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @Then("I verify the response status code {string}")
    public void i_verify_the_response_status_code(String statusCode) {
        org.junit.Assert.assertEquals(statusCode, String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode));
    }

    @Given("I initiated service region plan API services")
    public void i_initiated_service_region_plan_API_services() {
//        api.setbaseUri(planManagementBaseURI);
//        api.setEndPoint(regionFile);
    }

    String absPath = "";

    @Given("I do not choose the file to upload")
    public void i_do_not_choose_the_file_to_upload() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(regionFile);
        try {
            BrowserUtils.classLoader("testData/tm/planAndRegionConfig/File_fail.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String absPath = BrowserUtils.replaceTrimURL.get();
        System.out.println("Printing Abs path: " + absPath);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithKey("File", absPath);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

    }

    @When("I run the post region file upload")
    public void i_run_the_post_region_file_upload() {
//        api.PostAPIWithKey ( "File",absPath );
    }

    @Then("the file upload status is {string}")
    public void the_file_upload_status_is(String string) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(string));
    }

    @Then("the message is {string}")
    public void the_message_is(String string) {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains(string));
    }

    @Given("I upload the success service region and plan file")
    public void upload_service_region_and_Planfile() {
        i_upload_the_service_region_prior_to_Planfile();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(planManagementBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(planFile);
        i_upload_the_plan_file_1("Plan TestFile Success BLCRM", "success");
    }


    @Given("I get the jwt token")
    public void iGetTheJWTToken() {
        new TM_CreateProjectStepDef().i_logged_into_Tenant_Manager_Project_list_page();
        //crmContactRecordUiStepDef.i_logged_into_CRM_and_click_on_initiate_contact();
        waitFor(5);
        //apitmEventController.iWillGetCorrelationIdAsTraceIdAndPassItAsEndPoint("cookie");
        API_THREAD_LOCAL_FACTORY.getApitmEventControllerThreadLocal().iWillGetCorrelationIdAsTraceIdAndPassItAsEndPoint("onelogin-jwt");
        //waitFor(160); waitFor(80);
    }

    @Given("I get the jwt token TM")
    public void i_get_the_jwt_token_TM() {
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getApitmEventControllerThreadLocal().iWillGetCorrelationIdAsTraceIdAndPassItAsEndPoint("onelogin-jwt");

    }

    @Given("I get the jwt token TM with GET method")
    public void i_get_the_jwt_token_TM_with_GET_Method() {
        waitFor(5);
        API_THREAD_LOCAL_FACTORY.getApitmEventControllerThreadLocal().iWillGetCorrelationIdAsTraceIdAndPassItAsEndPoint("onelogin-jwt-GET");

    }


}
