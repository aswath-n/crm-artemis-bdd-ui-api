package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.text.*;
//import java.text.DateFormat;
//import java.text.NumberFormat;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


public class APICaseRestController extends CRMUtilities implements ApiBase {
    private String baseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String baseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String createConsumerEndPoint = "app/crm/consumer";
    private String createCaseMemberEndPoint = "app/crm/casemember";
    private String caseLoaderEndPoint = "app/crm/caseloader";
    private String caseConsumerEndPoint = "app/crm/caseConsumer";
    private String searchCaseEndPoint = "app/crm/case/consumers";
    private String getCaseMemberEndPoint = "app/crm/casemember/{caseId}";
    private String getConsumersforCaseEndPoint = "app/crm/casemember/{caseId}";
    private String getCaseEndPoint = "app/crm/case/{caseId}";
    private String searchCaseRecordsEndPoint = "app/crm/cases";
    private String getCaseByCorrelationId = "app/crm/correlation/{correlationType}/{correlationId}";
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> apiconsumerFirstName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> apiconsumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonObject> caseMemberRequestParams = ThreadLocal.withInitial(JsonObject::new);
    public final ThreadLocal<String> apiconsumerId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<HashMap<String, String>>> caseMembersData = ThreadLocal.withInitial(ArrayList<HashMap<String, String>>::new);
    public final ThreadLocal<String> createdCaseId = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> caseCorrelationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerDateOfBirth = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> apiConsumerSSN = ThreadLocal.withInitial(String::new);


    @Given("I initiated Create Consumer API to update case member")
    public void initiateCreateConsumer() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createConsumerEndPoint);
    }

    @Given("I initiated Create Consumer API for case search")
    public void i_initiated_create_consumer_api() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_initiated_create_consumer_api();
    }

    @When("I can provide consumer information for case search with {string} {string} {string} {string} {string} and {string}")
    public void i_can_provide_consumer_information_with(String consumerFirstName, String consumerLastName, String phoneNumber, String addressZip, String consumerSSN, String consumerDob) {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_provide_consumer_information_with(consumerFirstName, consumerLastName, phoneNumber, addressZip);
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_provide_consumer_ssn_dob_with(consumerSSN, consumerDob);
    }

    @And("I can run create consumer API for case search")
    public void i_can_run_create_consumer_api_case() {
        API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().i_can_run_create_consumer_api();
    }

    public class WordContainsException extends Exception {
        // Parameterless Constructor
        public WordContainsException() {
        }

        // Constructor that accepts a message
        public WordContainsException(String message) {
            super(message);
        }
    }

    @Given("I initiated create case loader API")
    public void i_initiated_create_case_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseLoaderEndPoint);
    }

    @Given("I initiated create case consumer API")
    public void i_initiated_create_case_consumer_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseConsumerEndPoint);
    }

    @Given("I initiated Create case member API")
    public void i_initiated_create_case_member_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseMemberEndPoint);
    }

    @Given("I initiated Search Case API")
    public void i_initiated_search_case_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseEndPoint);
    }

    @Given("I initiated get Case API by case id {string}")
    public void initiateGetCase(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        if (!caseId.isEmpty()) {
            getCaseEndPoint = getCaseEndPoint.replace("{caseId}", caseId);
        } else {
            getCaseEndPoint = getCaseEndPoint.replace("{caseId}", createdCaseId.get());
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseEndPoint);
    }

    @Given("I initiated get case member API for case {string}")
    public void i_initiated_get_case_member_api(String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseMemberEndPoint.replace("{caseId}", caseId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I can provide case information to create a case")
    public void i_can_information_for_case_creation() {
        i_initiated_create_case_api();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiCreateCase.json");
        String identificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber;
        caseCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        String caseUiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber;
        int identificationNumberType = (int) (Math.random() * (1 - 0)) + 0;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty("externalCaseId", identificationNumber);
        if (identificationNumberType == 0)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty("identificationNumberType", "Medicaid");
        else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseIdentificationNumber").get(0).getAsJsonObject().addProperty("identificationNumberType", "Chip");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("caseRequest").getAsJsonObject().addProperty("caseIdentificationNumber",
                identificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseHeadId", apiconsumerId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", caseCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", caseUiid);


        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @When("I can provide case and consumer information to create a case")
    public void provide_case_and_consumer_info_for_case_creation() {
        // i_initiated_create_case_consumer_api();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader_EE.json");
        String identificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(9).randomString;
        caseCorrelationId.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber);
        String caseUiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber;
        int identificationNumberType = (int) (Math.random() * (1 - 0)) + 0;
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty(
                "caseIdentificationNumber", identificationNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("correlationId", caseCorrelationId.get());
        if (identificationNumberType == 0)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("caseIdentificationNumberType", "MEDICAID");
        else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case").addProperty("caseIdentificationNumberType", "CHIP");

        String consumerCorrelationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16).randomNumber;
        String consumerUiid = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12).randomNumber;
        apiConsumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);

        apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());
        apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().genRandomName());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", consumerCorrelationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("uiid", consumerUiid);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", apiConsumerSSN.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("effectiveEndDate", OffsetDateTime.now(ZoneOffset.UTC).plusDays(3).toString());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", addDaysToCurrentDate("yyyy-MM-dd", -8000));
        identificationNumber = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomAlphaNumeric(8).randomString;
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0)
                .getAsJsonObject().addProperty("externalConsumerId", identificationNumber);

        if (identificationNumberType == 0)
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                    .getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0)
                    .getAsJsonObject().addProperty("identificationNumberType", "MEDICAID");
        else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                    .getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber").get(0)
                    .getAsJsonObject().addProperty("identificationNumberType", "CHIP");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject().getAsJsonObject("case")
                .getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonObject("contacts").getAsJsonObject("phone").addProperty("phoneNumber", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I can input more {string} and {string}")
    public void i_can_input_more(String consumerDateOfBirth, String consumerSSN) {
        try {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("caseConsumers").getAsJsonArray().get(0).getAsJsonObject()
                    .getAsJsonObject("caserequest").addProperty("consumerDateOfBirth", NumberFormat.getInstance().parse(consumerDateOfBirth));
        } catch (java.text.ParseException e) {
            System.out.println("Date of Birth is not a string input.");
        }
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("caseConsumers").getAsJsonArray().get(0).getAsJsonObject()
                .getAsJsonObject("caserequest").addProperty("consumerSSN", consumerSSN);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I can input additional {string}")
    public void i_can_input_additional(String consumerIdentificationNo) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("caseConsumers").getAsJsonArray().get(0).getAsJsonObject()
                .getAsJsonObject("caserequest").addProperty("consumerIdentificationNo", consumerIdentificationNo);

        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.toString());
    }

    @And("I can run create case loader API")
    public void i_can_run_create_case_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        initiateSearchCaseRecords();
        searchCaseByCorrelationId();
        HashMap<String, Object> caselist = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList");
        HashMap<String, Object> content = (HashMap<String, Object>) ((ArrayList) caselist.get("content")).get(0);
        createdCaseId.set(content.get("caseId").toString());
        System.out.println("Case Id created is:" + createdCaseId.get());
    }

    @Then("I can verify case exists by {string} is {string} on API response")
    public void i_can_verify_case_submission_case_api(String caseLastName, String Success) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseEndPoint);
        i_can_search_case_by("consumerLastName", caseLastName);
        i_run_the_case_search_api();
//        if (Success == "True"):
//            Assert.assertTrue(api.responseString.contains("success"));

        System.out.println("I can verify case submission {string} on API response");
    }

    @When("I can search case by {string} with value {string}")
    public void i_can_search_case_by(String item, String value) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        // -- Make consumer search by specific property. --
        switch (item) {
            case "consumerDateOfBirth":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}")) {
                    requestParams.get().addProperty("consumerDateOfBirth", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerDob.get());
                } else {
                    requestParams.get().addProperty("consumerDateOfBirth", value);
                }
                break;
            case "consumerFirstName":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}")) {
                    requestParams.get().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get());
                } else {
                    requestParams.get().addProperty("consumerFirstName", value);
                }
                break;
            case "consumerIdentificationNo":
                requestParams.get().addProperty("consumerIdentificationNo", value);
                break;
            case "consumerLastName":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}")) {
                    requestParams.get().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get());
                } else {
                    requestParams.get().addProperty("consumerLastName", value);
                }
                break;
            case "consumerMiddleName":
                requestParams.get().addProperty("consumerMiddleName", value);
                break;
            case "consumerSSN":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}")) {
                    requestParams.get().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerSsn.get());
                } else {
                    requestParams.get().addProperty("consumerSSN", value);
                }
                break;
        }
        System.out.println(requestParams.get());
    }

    private void search_case_by(JsonObject requestParams, String item, String value) {
        // -- Make consumer search by specific property. --
        switch (item) {
            case "consumerFirstName":
                if (value.isEmpty())
                    value = apiconsumerFirstName.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerFirstName", value);
                break;
            case "consumerIdentificationNo":
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerIdentificationNo", value);
                break;
            case "consumerLastName":
                if (value.isEmpty())
                    value = apiconsumerLastName.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                requestParams.addProperty("consumerLastName", value);
                break;
            case "consumerMiddleName":
                requestParams.addProperty("consumerMiddleName", value);
                break;
            case "consumerSSN":
                if (value.equalsIgnoreCase("NA")) ;

                if (value.isEmpty()) {
                    value = apiConsumerSSN.get();
                    requestParams.addProperty("consumerSSN", value);
                }
                break;
            case "consumerDateOfBirth":
                if (value.isEmpty())
                    value = apiConsumerDateOfBirth.get();
                if (value.equalsIgnoreCase("NA"))
                    value = "";
                try {
                    Date dob_date = null;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    dob_date = formatter.parse(value);
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter1.format(dob_date);
                    requestParams.addProperty("consumerDateOfBirth", dateString);
                    //System.out.println(requestParams);
                } catch (java.text.ParseException e) {
                    requestParams.addProperty("consumerDateOfBirth", value);
                    System.out.println("Date of Birth is not a string input.");
                }
                break;
        }
    }


    @When("I can Search Case by {string} with value {string}, {string} with value {string}, {string} with value {string}, {string} with value {string} and {string} with value {string}")
    public void i_can_search_case_by_node_and_value(String node1, String value1, String node2, String value2, String node3, String value3, String node4, String value4, String node5, String value5) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiSearchCase.json");
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        if (node1 != null && !node1.isEmpty()) {
            search_case_by(requestParams.get(), node1, value1);
        }
        if (node2 != null && !node2.isEmpty()) {
            search_case_by(requestParams.get(), node2, value2);
        }
        if (node3 != null && !node3.isEmpty()) {
            search_case_by(requestParams.get(), node3, value3);
        }
        if (node4 != null && !node4.isEmpty()) {
            search_case_by(requestParams.get(), node4, value4);
        }
        if (node5 != null && !node5.isEmpty()) {
            search_case_by(requestParams.get(), node5, value5);
        }
        System.out.println(requestParams.get());
    }


    @And("I run the case search API")
    public void i_run_the_case_search_api() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

    }

    private boolean check_record_exists(List<List> consumers, String field, String value) {
        Boolean recordFound = false;
        List<String> consumersFirstList = consumers.get(0);
        System.out.println(consumersFirstList);
        for (String item : consumersFirstList) {
            if (item.toString().toLowerCase().contains(value.toLowerCase())) {
                System.out.println("Consumer " + field + " : " + item.toString());
                recordFound = true;
                break;
            }
        }
        return recordFound;
    }


    @Then("I can verify case {string} with value {string} on API response")
    public void i_can_verify_the_consumer_search_api(String field, String value) {
        List<List> consumerList;
        int consumerListCount;
        try {
            consumerList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers");
            System.out.println(consumerList);
            consumerListCount = ((List) consumerList).size();
        } catch (NullPointerException e) {
            consumerList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.cases");
            System.out.println(consumerList);
            consumerListCount = ((List) consumerList).size();
        }
        assertTrue(consumerListCount > 0, "Consumers list is not greater than zero(0).");
        Boolean consumerFound = false;
        int listCount;
        switch (field) {
            case "consumerDateOfBirth":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerDob.get();
                List<List> consumerListDOBs;
                try {
                    consumerListDOBs = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerDateOfBirth");
                } catch (NullPointerException e) {
                    consumerListDOBs = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerDateOfBirth");
                }
                System.out.println(consumerListDOBs);
                boolean valueMatch = false;
                for (List consumerListDOB : consumerListDOBs) {
                    if (consumerListDOB.get(0).equals(value)) {
                        valueMatch = true;
                        break;
                    }
                }
                try {
                    if (valueMatch == false) {
                        throw new WordContainsException();
                    }
                } catch (WordContainsException ex) {
                    System.out.println("Search by Date of Birth is not working.");
                }
                break;

            case "consumerFirstName":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerFirstName.get();
                List<List> consumerListFN;
                try {
                    consumerListFN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerFirstName");
                    listCount = ((List) consumerListFN).size();
                } catch (NullPointerException e) {
                    consumerListFN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerFirstName");
                    listCount = ((List) consumerListFN).size();
                }
                consumerFound = check_record_exists(consumerListFN, field, value);
                assertTrue(consumerFound);
                break;
            case "consumerLastName":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().apiconsumerLastName.get();
                List<List> consumerListLN;
                try {
                    consumerListLN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerLastName");
                    listCount = ((List) consumerListLN).size();
                } catch (NullPointerException e) {
                    consumerListLN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerLastName");
                    listCount = ((List) consumerListLN).size();
                }
                consumerFound = check_record_exists(consumerListLN, field, value);
                assertTrue(consumerFound);
                break;
            case "consumerMiddleName":
                List<List> consumerListMN;
                try {
                    consumerListMN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerMiddleName");
                    listCount = ((List) consumerListMN).size();
                } catch (NullPointerException e) {
                    consumerListMN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerMiddleName");
                    listCount = ((List) consumerListMN).size();
                }
                consumerFound = check_record_exists(consumerListMN, field, value);
                assertTrue(consumerFound);
                break;
            case "consumerSSN":
                if (value.isEmpty() || value.equalsIgnoreCase("{random}"))
                    value = API_THREAD_LOCAL_FACTORY.getConsumerControllerThreadLocal().consumerSsn.get();
                List<List> consumerListSSN;
                try {
                    consumerListSSN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerSSN");
                    listCount = ((List) consumerListSSN).size();
                } catch (NullPointerException e) {
                    consumerListSSN = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerSSN");
                    listCount = ((List) consumerListSSN).size();
                }
                System.out.println(consumerListSSN);
                consumerFound = check_record_exists(consumerListSSN, field, value);
                assertTrue(consumerFound);
                break;
            case "consumerId":
                List<List> consumerListID;
                try {
                    consumerListID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result.consumers.consumerId");
                    listCount = ((List) consumerListID).size();
                } catch (NullPointerException e) {
                    consumerListID = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.consumers.consumerId");
                    listCount = ((List) consumerListID).size();
                }
                consumerFound = check_record_exists(consumerListID, field, value);
                assertTrue(consumerFound);
                break;
        }
    }

    private void verify_case_search_response(String success) {
        Object caseList;
        int caseListCount;
        try {
            caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("object.result");
            System.out.println(caseList);
            caseListCount = ((List) caseList).size();
        } catch (NullPointerException e) {
            caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("result.cases");
            System.out.println(caseList);
            caseListCount = ((List) caseList).size();
        }
        System.out.println(caseListCount);
        System.out.println(success);
        if (caseListCount > 0) {
            assertEquals(Boolean.TRUE, Boolean.valueOf(success));
        } else {
            assertEquals(Boolean.FALSE, Boolean.valueOf(success));
            // System.out.println(api_step_definitions.responseString);
        }
    }

    @Then("I can verify on case search API response will be {string}")
    public void i_can_verify_the_case_search_api_response(String success) {
        verify_case_search_response(success);
    }

    @When("I can provide case member information")
    public void i_can_provide_case_member_information_with(DataTable caseMember) {
        for (Map<String, String> data : caseMember.asMaps(String.class, String.class)) {
            //apitdu.getJsonFromFile("crm/case/apiCreateCaseMember.json");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiNewCreatePrimaryIndividual.json");
            String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
            HashMap<String, String> memberData = new HashMap<String, String>();
            if (data.containsKey("consumerFirstName")) {
                String firstName = data.get("consumerFirstName").toString();
                if (firstName.isEmpty() || firstName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName);
                }
                memberData.put("consumerFirstName", apiconsumerFirstName.get());
            }

            if (data.containsKey("consumerLastName")) {
                String lastName = data.get("consumerLastName").toString();
                if (lastName.isEmpty() || lastName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName);
                }
                memberData.put("consumerLastName", apiconsumerLastName.get());
            }

            if (data.containsKey("consumerDateOfBirth")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", data.get("consumerDateOfBirth").toString());
            }

            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.equals("{random}") || ssn.isEmpty()) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
                }
            }

            if (data.containsKey("genderCode")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", data.get("genderCode").toString());
            }
            //
            if (data.containsKey("effectiveStartDate")) {
                //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveStartDate",getDateInMilli(data.get("effectiveStartDate").toString()));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            }

            //if( data.containsKey("effectiveEndDate")){
            //  apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", getDateInMilli(data.get("effectiveEndDate").toString()));
            //}

            if (data.containsKey("caseId")) {
                if (!data.get("caseId").toString().isEmpty())
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", Integer.parseInt(data.get("caseId").toString()));
                else
                    //apitdu.jsonElement.getAsJsonObject().addProperty("caseId", Integer.parseInt(createdCaseId));
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", createdCaseId.get());
            }

            if (data.containsKey("consumerRole")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerRole", data.get("consumerRole").toString());
            }

            if (data.containsKey("relationShip")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("relationShip", data.get("relationShip").toString());
                memberData.put("relationShip", data.get("relationShip").toString());
            }

            if (data.containsKey("consumerStatus")) {
                memberData.put("consumerStatus", data.get("consumerStatus").toString());
            }

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(16);
            String apiCorrelationId = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("correlationId", apiCorrelationId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(12);
            String apiuiid = (API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber).toString();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("uiid", apiuiid);
            //apitdu.jsonElement.getAsJsonObject().addProperty("createdOn", System.currentTimeMillis());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("createdOn", offsetTime);


            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);


            caseMemberRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(caseMemberRequestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"), "call is failed");
            caseMembersData.get().add(memberData);
            System.out.println("Case Member created");
        }
    }

    private long getDateInMilli(String date) {
        long dateValueToReturn = 0;
        int days = 0;
        boolean isDateFuture = false;
        if (date.contains("+")) {
            days = Integer.parseInt(date.split("\\+")[1]);
            date = date.split("\\+")[0];
            isDateFuture = true;
        } else if (date.contains("-")) {
            days = Integer.parseInt(date.split("-")[1]);
            date = date.split("-")[0];
        }

        if (isDateFuture) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(days);
            dateValueToReturn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().futureDates;
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(days);
            dateValueToReturn = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().pastDates;
        }
        return dateValueToReturn;
    }

    /**
     * This will verify the case member detail using API
     *
     * @param dataTable
     */
    @Then("I verify the case member details using API")
    public void i_can__verify_case_member_details(DataTable dataTable) {
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println("results last step " + results);
        System.out.println(results);
        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {
            HashMap consumerRole = (HashMap) results.get(1);
            System.out.println(consumerRole);
            for (int i = 0; i < results.size(); i++) {
                consumerRole = (HashMap) results.get(i);
                if (consumerRole.get("consumerRole").toString().equalsIgnoreCase(data.get("consumerRole").toString()))
                    break;
            }
            String elementToBeValidated = "";
            if (data.containsKey("relationShip")) {
                elementToBeValidated = "relationShip";
            } else if (data.containsKey("consumerStatus")) {
                elementToBeValidated = "consumerStatus";
            }
            String expValue = data.get(elementToBeValidated).toString();
            ArrayList consumers = (ArrayList) consumerRole.get("consumers");
            boolean memberFound = false;
            if (consumers.size() > 0) {
                for (HashMap<String, String> member : caseMembersData.get()) {
                    for (int i = 0; i < consumers.size(); i++) {
                        HashMap consumer = (HashMap) consumers.get(i);
                        if (consumer.get("consumerFirstName").toString().toLowerCase().contains(member.get("consumerFirstName").toLowerCase())
                                && consumer.get("consumerLastName").toString().toLowerCase().contains(member.get("consumerLastName").toLowerCase())
                                && consumer.get(elementToBeValidated).toString().toLowerCase().contains(member.get(elementToBeValidated).toLowerCase()))              // --- Not working ---
                        {
                            apiconsumerFirstName.set(consumer.get("consumerFirstName").toString());
                            apiconsumerLastName.set(consumer.get("consumerLastName").toString());
                            memberFound = true;
                            break;
                        }
                    }
                    if (memberFound) break;
                }
            }
            assertTrue(memberFound, "member not found for the " + elementToBeValidated + ": " + expValue);
        }
    }

    //Updated on 29Nov
    @When("I Update case member details using API")
    public void i_Update_case_member_details_using_API(DataTable caseMember) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        ArrayList<Object> results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println("results " + results);
        //System.out.println("Debug :"+results);
        //HashMap dataconsumerRole = (HashMap) results.get(1);
        HashMap dataconsumerRole = (HashMap) results.get(0);
        System.out.println("dataconsumerRole " + dataconsumerRole);
        System.out.println(dataconsumerRole.get("consumers"));
        ArrayList<Object> newlist = (ArrayList<Object>) dataconsumerRole.get("consumers");
        System.out.println("newList " + newlist);
        HashMap newmap = (HashMap) newlist.get(0);
        System.out.println("consumerId : " + newmap.get("consumerId"));
        Integer getconsumerId = (Integer) newmap.get("consumerId");
        for (Map<String, String> data : caseMember.asMaps(String.class, String.class)) {
            // apitdu.getJsonFromFile("crm/case/apiCreateCaseMember.json");
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/NewCreateCaseMember.json");

            String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

            HashMap<String, String> memberData = new HashMap<String, String>();
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", getconsumerId.toString());
            if (data.containsKey("consumerFirstName")) {
                String FirstName = data.get("consumerFirstName").toString();
                if (FirstName.isEmpty() || FirstName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    System.out.println("Updated Consumer First Name: " + apiconsumerFirstName.get());
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
                } else if (FirstName.equals("{update}")) {
                    apiconsumerFirstName.set("Update");
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", "Update");
                } else {
                    apiconsumerFirstName.set(FirstName);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", FirstName);
                }
                memberData.put("consumerFirstName", apiconsumerFirstName.get());
            }

            if (data.containsKey("consumerLastName")) {
                String LastName = data.get("consumerLastName").toString();
                if (LastName.isEmpty() || LastName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else if (LastName.equals("{update}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", "Update");
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", LastName);
                }
                memberData.put("consumerLastName", apiconsumerLastName.get());
            }

            if (data.containsKey("consumerDateOfBirth")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", data.get("consumerDateOfBirth").toString());
            }

            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.equals("{random}") || ssn.isEmpty()) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
                }
            }

            if (data.containsKey("genderCode")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", data.get("genderCode").toString());
            }

            if (data.containsKey("effectiveStartDate")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            }

            // if (data.containsKey("effectiveEndDate")) {
            //   apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", getDateInMilli(data.get("effectiveEndDate").toString()));
            //}

            if (data.containsKey("caseId")) {
                String value = data.get("caseId").toString();
                if (value.isEmpty())
                    value = createdCaseId.get();
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", Integer.parseInt(value));
            }

            if (data.containsKey("consumerType")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", data.get("consumerType").toString());
            }

            if (data.containsKey("consumerRole")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerRole", data.get("consumerRole").toString());
            }

            if (data.containsKey("relationShip")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("relationShip", data.get("relationShip").toString());
                memberData.put("relationShip", data.get("relationShip").toString());
            }

            if (data.containsKey("consumerStatus")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerStatus", data.get("consumerStatus").toString());
                memberData.put("consumerStatus", data.get("consumerStatus").toString());
            }


            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("correspondencePreference").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("correspondencePreference").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);

            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseMemberEndPoint);
            caseMemberRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(caseMemberRequestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            caseMembersData.get().add(memberData);

        }
    }

    @Then("I verify status for case member details using API")
    public void i_verify_status_for_case_member_details_using_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        ArrayList<Object> results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        HashMap dataconsumerRole = (HashMap) results.get(1);
        ArrayList<Object> newlist = (ArrayList<Object>) dataconsumerRole.get("consumers");
        HashMap newmap = (HashMap) newlist.get(newlist.size() - 1);
        System.out.println("consumerId : " + newmap.get("consumerId"));
        System.out.println("consumerStatus : " + newmap.get("consumerStatus"));
        assertTrue("Inactive".equalsIgnoreCase(newmap.get("consumerStatus").toString()), "status is not updated");
        System.out.println("Update on  : " + newmap.get("updatedOn"));

    }

    @Then("I verify updates for case member details using API")
    public void i_verify_updates_for_case_member_details_using_API() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        ArrayList<Object> results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");

        HashMap dataconsumerRole = (HashMap) results.get(0);
        ArrayList<Object> newlist = (ArrayList<Object>) dataconsumerRole.get("consumers");
        HashMap newmap = (HashMap) newlist.get(0);
        System.out.println("consumerId : " + newmap.get("consumerId"));
        System.out.println("consumerFirstName : " + newmap.get("consumerFirstName"));
        System.out.println("Expected consumerFirstName : " + new APICaseLoaderEligibilityEnrollmentController().getConsumerLastName());
        assertTrue(newmap.get("consumerFirstName").toString().contains(new APICaseLoaderEligibilityEnrollmentController().getConsumerLastName()), "Consumer First name is not updated");
        System.out.println("Update on  : " + newmap.get("updatedOn"));
    }

    @Then("I can run create case member API and validate the status is success")
    public void i_can_run_create_case_member_API_and_validate_the_status_is_success() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    @Given("I created a case head")
    @Deprecated
    public void createCaseHead() {
       /* consumerRestController.i_initiated_create_consumer_api();
        consumerRestController.i_can_provide_consumers_information_randomly();
        consumerRestController.i_can_run_create_consumer_api();
        consumerRestController.i_can_verify_the_case_search_api_response();
        apiconsumerId = consumerRestController.get_the_consumer_id_from_response(consumerRestController.apiconsumerFirstName);
        System.out.println(apiconsumerId);*/
    }

    @Then("I verify case id created")
    public void verifyCaseIdCreated() {
        assertTrue(createdCaseId.get() != null);
    }

    @Then("I verify case details are retrieved {string}")
    public void verifyCaseIdRetrieved(String caseId) {
        String caseIDRetrieved = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseObject.caseId").toString();
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        assertEquals(caseIDRetrieved, caseId);
    }

    @And("I can run Get case by case ID API")
    public void runGetCaseApi() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Given("I initiated Search Case records api")
    public void initiateSearchCaseRecords() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchCaseRecordsEndPoint);
    }

    @When("I provide case id as {string} case status as {string} and case type as {string} for Search Case records")
    public void provideCaseRecordsSearchDetails(String caseId, String caseStatus, String caseType) {
        requestParams.set(new JsonObject());
        if (caseId.equalsIgnoreCase("{random}"))
            caseId = createdCaseId.get();
        requestParams.get().addProperty("caseId", caseId);
        requestParams.get().addProperty("caseStatus", caseStatus);
        requestParams.get().addProperty("caseType", caseType);
    }

    @And("I can run search case records api")
    public void runPostCaseRecords() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
    }

    @Then("I verify case records are retrieved")
    public void verifyCaseRecordsRetrieved() {
        ArrayList caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content.caseId");
        assertTrue(caseList.size() > 0);
        int upper = caseList.size();
        int lower = 0;
        int randomIndex = (int) (Math.random() * (upper - lower)) + lower;
        createdCaseId.set(caseList.get(randomIndex).toString());
    }

    @Then("I verify case member is created")
    public void verifyCaseMemberCreated() {
        boolean recordFound = false;
        ArrayList consumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("consumers");
        for (int i = 0; i < consumers.size(); i++) {
            HashMap consumerData = (HashMap) consumers.get(1);
            if (consumerData.get("consumerLastName").toString().equalsIgnoreCase(apiconsumerLastName.get())
                    && consumerData.get("consumerFirstName").toString().equalsIgnoreCase(apiconsumerFirstName.get())) {
                recordFound = true;
                break;
            }
        }

        assertTrue(recordFound);
    }

    @Then("I can verify case member is created")
    public void i_can_verify_case_member_is_created() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println(results);
        HashMap consumerRole = (HashMap) results.get(1);
        ArrayList consumers = (ArrayList) consumerRole.get("consumers");

        boolean memberFound = false;
        if (consumers.size() > 0) {
            for (int i = 0; i < consumers.size(); i++) {
                HashMap consumer = (HashMap) consumers.get(i);
                if (consumer.get("consumerFirstName").equals(apiconsumerFirstName.get())
                        && consumer.get("consumerLastName").equals(apiconsumerLastName.get())) {
                    try {
                        memberFound = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        assertTrue(memberFound);
    }

    @Given("I initiated search case by correlation id {string} and {string}")
    public void i_initiated_get_case_by_correlationId(String correlationType, String correlationId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI+"");
        if (correlationId.isEmpty())
            correlationId = caseCorrelationId.get();
        getCaseByCorrelationId = getCaseByCorrelationId.replace("{correlationType}", correlationType);
        getCaseByCorrelationId = getCaseByCorrelationId.replace("{correlationId}", correlationId);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseByCorrelationId);
    }

    @Given("I initiated get consumers for case {string}")
    public void i_initiated_get_consumer_for_case(String caseId) {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getConsumersforCaseEndPoint.replace("{caseId}", caseId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        //Added logic to extract consumer details in step-I verify case consumer created
       /* ArrayList results = api.jsonPathEvaluator.get("result");
        HashMap result = (HashMap)results.get(0);

        ArrayList consumers = (ArrayList)result.get("consumers");
        HashMap consumerData = (HashMap) consumers.get(consumers.size()-1);

        apiconsumerFirstName = consumerData.get("consumerFirstName").toString();
        apiconsumerLastName = consumerData.get("consumerLastName").toString();
        apiConsumerDateOfBirth = consumerData.get("consumerDateOfBirth").toString();
        apiConsumerSSN = consumerData.get("consumerSSN").toString();*/


    }

    @Then("I verify the case details in the response")
    public void verifyCaseDetailsInResponse() {
        ArrayList cases = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("cases.caseId");
        assertEquals(cases.get(0).toString(), createdCaseId.get());
    }

    @Then("I verify family member records are retrieved by case id")
    public void verifyConsumersSearchCaseId() {
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        ArrayList consumers = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result.consumers");
        assertTrue(consumers.size() > 0);
    }

    @Given("I created a case required for validations")
    public void createaCaseTestData() {
        // createCaseHead();
        i_initiated_create_case_api();
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");
        // i_can_information_for_case_creation();
        provide_case_and_consumer_info_for_case_creation();
        //i_can_run_create_case_api();
    }

    /**
     * <Description> This method valdate the order of status for given consumer type</>
     *
     * @param consumerRole
     */
    @Then("I verify active case members fetched followed by inactive for consumerRole {string} using API")
    public void i_can__verify_case_member_order(String consumerRole) {
        // api.getAPI();
        //System.out.println(api.responseString);
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");

        HashMap dataConsumerRole = null;
        if (consumerRole.equalsIgnoreCase("Primary Individual"))
            dataConsumerRole = (HashMap) results.get(0);
        else if (consumerRole.equalsIgnoreCase("Member"))
            dataConsumerRole = (HashMap) results.get(1);
        ArrayList consumers = (ArrayList) dataConsumerRole.get("consumers");

//        HashMap dataconsumerRole = (HashMap) results.get(0);

        for (int i = 0; i < results.size(); i++) {
            dataConsumerRole = (HashMap) results.get(i);
            if (dataConsumerRole.get("consumerRole").toString().equalsIgnoreCase(consumerRole))
                break;
        }

        boolean flag = true;
        boolean isInactive = false;
        if (consumers.size() > 0) {
            for (int i = 0; i < consumers.size(); i++) {
                HashMap consumer = (HashMap) consumers.get(i);
                if (consumer.get("consumerStatus").toString().equalsIgnoreCase("Active")) {
                    if (isInactive) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                    }
                } else {
                    isInactive = true;
                    flag = true;
                }
            }
        }
        assertTrue(flag, "Records not fetched Active first followed by inactive");

    }

    @Then("I verify case single case record is retrieved")
    public void verifySingleCaseRetrieved() {
        ArrayList caseList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList.content.caseId");
        assertTrue(caseList.size() == 1);
    }

    @When("I verify Search Case by {string} with value {string}, {string} with value {string}, {string} with value {string}, {string} with value {string} and {string} with value {string}")
    public void verifyRetrivedConsumerValues(String node1, String value1, String node2, String value2, String node3, String value3, String node4, String value4, String node5, String value5) {
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("object.result");
        boolean recordFound = false;
        for (int i = 0; i < results.size(); i++) {
            HashMap<String, Object> res = (HashMap) results.get(0);
            HashMap<String, Object> cases = (HashMap) res.get("cases");
            System.out.println(cases.get("caseId"));
            if (cases.get("caseId").toString().equalsIgnoreCase(createdCaseId.get())) {
                recordFound = true;
                break;
            }


        }

        assertTrue(recordFound);
       /* boolean firstNameFound = false;
        boolean lastNameFound = false;
        boolean ssnFound = false;
        boolean dobFound = false;

        for(int i=0;i<consumers.size();i++){
            HashMap consumerData =(HashMap) consumers.get(i);
            if (!value1.equalsIgnoreCase("NA")) {
                if(consumerData.get(node1).toString().equalsIgnoreCase(value1));
            }
            if (node2 != null && !node2.isEmpty()) {
                search_case_by(requestParams, node2, value2);
            }
            if (node3 != null && !node3.isEmpty()) {
                search_case_by(requestParams, node3, value3);
            }
            if (node4 != null && !node4.isEmpty()) {
                search_case_by(requestParams, node4, value4);
            }

        }*/

    }

    @Then("I verify case consumer created")
    public void verifycaseConsumerCreated() {
        //createdCaseId = api.jsonPathEvaluator.getJsonObject("caseObject.caseId").toString();
        boolean consumerFound = false;
        assertTrue(createdCaseId.get() != null);
        System.out.println(((ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result")).size());
        System.out.println("Expected FirstName:" + apiconsumerFirstName.get());
        System.out.println("Expected LastName:" + apiconsumerLastName.get());
        HashMap<String, Object> caselist = (HashMap<String, Object>) ((ArrayList) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("result")).get(0);
        for (int i = 0; i < ((ArrayList) caselist.get("consumers")).size(); i++) {
            HashMap<String, Object> consumerData = (HashMap<String, Object>) ((ArrayList) caselist.get("consumers")).get(i);
            System.out.println("Actual FirstName:" + consumerData.get("consumerFirstName").toString());
            System.out.println("Actual LastName:" + consumerData.get("consumerLastName").toString());
            if (consumerData.get("consumerLastName").toString().equalsIgnoreCase(apiconsumerLastName.get())
                    && consumerData.get("consumerFirstName").toString().equalsIgnoreCase(apiconsumerFirstName.get())) {
                consumerFound = true;
                apiConsumerDateOfBirth.set(consumerData.get("consumerDateOfBirth").toString());
                apiConsumerSSN.set(consumerData.get("consumerSSN").toString());
                break;
            }
        }
        assertTrue(consumerFound);
    }

    @When("I provide primary individual information {string} {string} {string} {string} {string}")
    public void i_provide_primary_individual_information(String firstName, String lastName, String startDate, String endDate, String caseId) {
        //apitdu.getJsonFromFile("crm/case/apiCreatePrimaryIndividual.json");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiNewCreatePrimaryIndividual.json");
        String offsetTime = OffsetDateTime.now(ZoneOffset.UTC).toString();

        HashMap<String, String> memberData = new HashMap<String, String>();
        if (firstName.isEmpty() || firstName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", firstName);
        }
        memberData.put("consumerFirstName", apiconsumerFirstName.get());

        if (lastName.isEmpty() || lastName.equals("{random}")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
            apiconsumerLastName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", lastName);
        }
        memberData.put("consumerLastName", apiconsumerLastName.get());

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", "1989-02-02");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", "Female");
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", "Consumer");
        if (startDate.equalsIgnoreCase("past")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveStartDate", apitdu.pastDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", OffsetDateTime.now(ZoneOffset.UTC).minusDays(3).toString());

        } else if (startDate.equalsIgnoreCase("future")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveStartDate", apitdu.futureDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", OffsetDateTime.now(ZoneOffset.UTC).plusDays(3).toString());
        } else if (startDate.equalsIgnoreCase("today")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveStartDate", apitdu.pastDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", OffsetDateTime.now(ZoneOffset.UTC).minusDays(3).toString());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        }
        if (endDate.equalsIgnoreCase("past")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", apitdu.pastDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", OffsetDateTime.now(ZoneOffset.UTC).minusDays(3).toString());
        } else if (endDate.equalsIgnoreCase("future")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getFutureDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", apitdu.futureDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", OffsetDateTime.now(ZoneOffset.UTC).plusDays(3).toString());
        } else if (endDate.equalsIgnoreCase("today")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getPastDates(100);
            //apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", apitdu.pastDates);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", OffsetDateTime.now(ZoneOffset.UTC).minusDays(3).toString());
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", OffsetDateTime.now(ZoneOffset.UTC).toString());
        }
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", caseId);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(1).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(2).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(3).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("consumerConsent").get(4).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().getAsJsonObject().addProperty("effectiveStartDate", offsetTime);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("communicationPreferences").get(0).getAsJsonObject().getAsJsonObject().addProperty("createdOn", offsetTime);

        caseMemberRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        System.out.println(caseMemberRequestParams.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        caseMembersData.get().add(memberData);
        System.out.println("Primary Individual created");

    }

    //this method will work for both type of consumers
    @Then("I can verify {string} is created on API response with {string}")
    public void i_can_verify_is_created_on_API_response_with(String consumerTypeVaule, String status) {
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println(results);
        HashMap consumerType = null;
        if (consumerTypeVaule.equalsIgnoreCase("Primary Individual"))
            consumerType = (HashMap) results.get(0);
        else if (consumerTypeVaule.equalsIgnoreCase("Member"))
            consumerType = (HashMap) results.get(1);
        ArrayList consumers = (ArrayList) consumerType.get("consumers");
        String tempStatus = null;
        boolean memberFound = false;
        if (consumers.size() > 0) {
            for (int i = 0; i < consumers.size(); i++) {
                HashMap consumer = (HashMap) consumers.get(i);
                if (consumer.get("consumerFirstName").equals(apiconsumerFirstName.get())
                        && consumer.get("consumerLastName").equals(apiconsumerLastName.get())) {
                    memberFound = true;
                    tempStatus = consumer.get("consumerStatus").toString();
                    break;
                }
            }
        }
        assertTrue(memberFound);
        assertEquals(tempStatus, status);
    }

    @Given("I initiated get {string} from API for {string}")
    public void i_initiated_get_from_API_for(String consumerTypeVaule, String caseId) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        if (caseId.isEmpty())
            caseId = createdCaseId.get();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(getCaseMemberEndPoint.replace("{caseId}", caseId));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    @When("I Update primary Individual  using API")
    public void i_Update_primary_Individual_using_API(DataTable primaryIndividual) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        ArrayList<Object> results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println("Debug :" + results);
        HashMap dataConsumerType = (HashMap) results.get(0);
        //System.out.println(dataConsumerType.get("consumers"));
        ArrayList<Object> newlist = (ArrayList<Object>) dataConsumerType.get("consumers");
        HashMap newmap = null;
        try {
            newmap = (HashMap) newlist.get(0);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(false, "Array is empty, Consumer not found");
        }
        System.out.println("consumerId : " + newmap.get("consumerId"));
        Integer getconsumerId = (Integer) newmap.get("consumerId");
        String consumerCorrelationId = newmap.get("correlationId").toString();
        String consumerUiid = newmap.get("uiid").toString();
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseMemberEndPoint);
        for (Map<String, String> data : primaryIndividual.asMaps(String.class, String.class)) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/case/apiUpdatePrimaryIndividual.json");
            HashMap<String, String> memberData = new HashMap<String, String>();

            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerId", getconsumerId.toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", System.currentTimeMillis());
            //apitdu.jsonElement.getAsJsonObject().addProperty("correlationId", consumerCorrelationId);
            //apitdu.jsonElement.getAsJsonObject().addProperty("uiid", consumerUiid);

            if (data.containsKey("consumerFirstName")) {
                String FirstName = data.get("consumerFirstName").toString();
                if (FirstName.isEmpty() || FirstName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else if (FirstName.equals("{update}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", "Update");
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", FirstName);
                }
                memberData.put("consumerFirstName", apiconsumerFirstName.get());
            }

            if (data.containsKey("consumerLastName")) {
                String LastName = data.get("consumerLastName").toString();
                if (LastName.isEmpty() || LastName.equals("{random}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(10);
                    apiconsumerFirstName.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomString);
                } else if (LastName.equals("{update}")) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", "Update");
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", LastName);
                }
                memberData.put("consumerFirstName", apiconsumerFirstName.get());
            }

            if (data.containsKey("consumerDateOfBirth")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerDateOfBirth", getDateInMilli(data.get("consumerDateOfBirth").toString()));
            }

            if (data.containsKey("consumerSSN")) {
                String ssn = data.get("consumerSSN").toString();
                if (ssn.equals("{random}") || ssn.isEmpty()) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9);
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().randomNumber);
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", ssn);
                }
            }

            if (data.containsKey("genderCode")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("genderCode", data.get("genderCode").toString());
            }

            if (data.containsKey("effectiveStartDate")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", "2022-08-01T18:26:53.406Z");
            }

            /*if (data.containsKey("effectiveEndDate")) {
                apitdu.jsonElement.getAsJsonObject().addProperty("effectiveEndDate", getDateInMilli(data.get("effectiveEndDate").toString()));
            }*/

            /*if (data.containsKey("caseId")) {
                String caseId = data.get("caseId").toString();
                if(caseId.isEmpty())
                    caseId = createdCaseId;
                apitdu.jsonElement.getAsJsonObject().addProperty("caseId", caseId);
            }*/

            if (data.containsKey("consumerType")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerType", data.get("consumerType").toString());
            }

            if (data.containsKey("relationShip")) {
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("relationShip", data.get("relationShip").toString());
                memberData.put("relationShip", data.get("relationShip").toString());
            }

            if (data.containsKey("consumerStatus")) {
                memberData.put("consumerStatus", data.get("consumerStatus").toString());
            }

            caseMemberRequestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
            System.out.println(caseMemberRequestParams.get());
            API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(caseMemberRequestParams.get());
            System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
            caseMembersData.get().add(memberData);
        }
    }

    @Then("I verify updates for primary Individual using API")
    public void i_verify_updates_for_primary_Individual_using_API() {
        ArrayList<Object> results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");
        System.out.println(results);
        HashMap dataConsumerType = (HashMap) results.get(0);
        ArrayList<Object> newlist = (ArrayList<Object>) dataConsumerType.get("consumers");
        HashMap newmap = (HashMap) newlist.get(0);
        System.out.println("consumerId : " + newmap.get("consumerId"));
        System.out.println("consumerFirstName : " + newmap.get("consumerFirstName"));
        // assertTrue("Update".equalsIgnoreCase(newmap.get("consumerFirstName").toString()),"name is not updated");
        assertEquals(newmap.get("consumerFirstName").toString().toLowerCase(), "Update".toLowerCase());
        System.out.println("Update on  : " + newmap.get("updatedOn"));
        // assertFalse(newmap.get("updatedOn").toString().isEmpty(),"updated on value is null");
    }

    @When("I search case by case correlation id")
    public JsonObject searchCaseByCorrelationId() {
        requestParams.set(new JsonObject());
        requestParams.get().addProperty("correlationId", caseCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject;
    }

    @Then("I verify case event by searching events with correlation id")
    public void verifyEvents() {
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().initiateSearchEvents();
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().prepareRequestBodyWithCorrelationIdForSearchEvents(caseCorrelationId.get());
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().runSearchEvent();
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().verifyCaseIdInEvents("CASE_SAVE_EVENT", createdCaseId.get());
        API_THREAD_LOCAL_FACTORY.getApiEventsThreadLocal().verifyCaseIdInEvents("CASE_MEMBER_SAVE_EVENT", createdCaseId.get());
    }

    public static String addDaysToCurrentDate(String format, int numOfDaysoAdd) {
        String dateToReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, numOfDaysoAdd);
        dateToReturn = sdf.format(cal.getTime());
        return dateToReturn;
    }

    public void setCaseCorrelationId(String caseCorrelationId) {
        this.caseCorrelationId.set(caseCorrelationId);
    }

    public String getCreatedCaseId() {
        HashMap<String, Object> caselist = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("caseList");
        if (API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().apiJsonObject.getAsJsonObject("caseList").getAsJsonArray("content").size() == 0)
            return null;
        HashMap<String, Object> content = (HashMap<String, Object>) ((ArrayList) caselist.get("content")).get(0);
        createdCaseId.set(content.get("caseId").toString());
        System.out.println("Case Id created is:" + createdCaseId.get());
        return createdCaseId.get();
    }

}

