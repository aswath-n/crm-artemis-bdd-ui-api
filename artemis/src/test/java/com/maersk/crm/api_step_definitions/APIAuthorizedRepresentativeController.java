package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIAuthorizedRepresentativeController extends CRMUtilities implements ApiBase {

    private String caseLoaderBaseURI = ConfigurationReader.getProperty("apiCaseLoaderURI");
    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private String createCaseConsumerForEvents = "app/crm/caseloader";
    private String eventsCorrelationIdEndPoint="/app/crm/event/correlation/";
    private String caseConsumerURI = ConfigurationReader.getProperty("apiCaseConsumerURI");
    private String caseMemberEndPoint = "app/crm/casemember";
    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);;
    private final ThreadLocal <String> consumerFirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> consumerLastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> consumerSSN = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> consumerDob = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> consumerRole = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> NULL_CHECK = ThreadLocal.withInitial(()->"null");;
    private final ThreadLocal <String> CONSUMER = ThreadLocal.withInitial(()->"Consumer");;
    private final ThreadLocal <String> ENGLISH = ThreadLocal.withInitial(()->"English");;

    private final ThreadLocal <String> apiconsumerFirstName = ThreadLocal.withInitial(()->"fnar" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal <String> apiconsumerLastName = ThreadLocal.withInitial(()->"lnar" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal <String> apiconsumerLastNameUpdated = ThreadLocal.withInitial(()->"lnar" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal <String> apiConsumerSSN = ThreadLocal.withInitial(()->RandomStringUtils.random(9, false, true));

    @Given("I initiate case loader API for Authorized Representative")
    public void initiatedConsumerCreateAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseLoaderBaseURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCaseConsumerForEvents);
    }

    @When("I create case from case loader for Authorized Representative with consumer role {string}")
    public void createCaseFromCaseLoaderForAR(String consumerRoleInput) {
        createCaseFromCaseLoader(consumerRoleInput);
    }

    @When("I initiated Authorized Representative API")
    public void initiatedAuthorizedRepresentativeAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseMemberEndPoint);
    }

    @When("I {string} Authorized Representative API based on {string} and {string}")
    public void runAuthorizedRepresentativeAPI(String createOrUpdtate, String startDate, String endDate) {
        createOrUpdateAR("create", startDate, endDate);

        if (createOrUpdtate.equals("update")) {
            createOrUpdateAR(createOrUpdtate, startDate, endDate);
        }
    }

    @Then("I verify the status of {string} Authorized RepresentativeStatus in API")
    public void verifyTheAuthorizedRepresentativeAPIStatus(String createOrUpdate) throws ParseException {
        ArrayList results = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("result");

        HashMap authorizedReps = (HashMap) results.get(2);
        assertEquals(authorizedReps.get("consumerRole"), "Authorized Representative");
        ArrayList authorizedRepConsumers = (ArrayList) authorizedReps.get("consumers");
        assertTrue(authorizedRepConsumers.size() > 0);

        boolean memberFound = false;
        for (int i = 0; i < authorizedRepConsumers.size(); i++) {
            HashMap consumer = (HashMap) authorizedRepConsumers.get(i);
            if(consumer.get("consumerFirstName").equals(apiconsumerFirstName)){
                memberFound = true;
                assertEquals(consumer.get("consumerSSN"), apiConsumerSSN);

                if (createOrUpdate.equals("created")) {
                    assertEquals(consumer.get("consumerLastName"), apiconsumerLastName);
                }
                else if (createOrUpdate.equals("updated")) {
                    assertEquals(consumer.get("consumerLastName"), apiconsumerLastNameUpdated);
                    assertEquals(consumer.get("updatedBy"), "1457");
                }

                assertEquals(consumer.get("consumerDateOfBirth").toString(), consumerDob);
                assertEquals(consumer.get("consumerType").toString(), CONSUMER);
                assertEquals(consumer.get("preferredLanguage").toString(), ENGLISH);
                assertTrue(verifyDatetimeFormat(consumer.get("effectiveStartDate").toString()));

                String startDate = consumer.get("effectiveStartDate").toString();
                String endDate = consumer.get("effectiveEndDate") != null ? consumer.get("effectiveEndDate").toString() : null;
                String status = consumer.get("consumerStatus").toString();
                verifyStatus(startDate, endDate, status);

                break;
            }
        }
        assertTrue(memberFound);
    }

    private void createOrUpdateAR(String createOrUpdtate, String startDate, String endDate) {
        requestParams.set(generateCaseMemberRequest(createOrUpdtate, startDate, endDate));
        consumerDob.set(requestParams.get().get("consumerDateOfBirth").getAsString());
        System.out.println(requestParams.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(caseConsumerURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(caseMemberEndPoint+"/"+caseId.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
    }

    private JsonObject generateCaseMemberRequest(String createOrUpdate, String startDate, String endDate) {
        if (createOrUpdate.equals("create")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiCreateAuthorizedRepresentative.json");

            if (startDate.equals("Future"))
                startDate = getDate("startDate");
            else if (!startDate.isEmpty())
                startDate = startDate.concat("T00:00:01Z");

            if (endDate.equals("Future"))
                endDate = getDate("endDate");
            else if (!endDate.isEmpty())
                endDate = endDate.concat("T00:00:01Z");

            System.out.println("CaseId :"+caseId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", caseId.get());
            System.out.println("Consumer First Name :"+apiconsumerFirstName.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
            System.out.println("Consumer Last Name :"+apiconsumerLastName.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastName.get());
            System.out.println("Consumer SSN :"+apiConsumerSSN.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", apiConsumerSSN.get());
            System.out.println("Consumer Start Date :"+startDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", startDate);
            System.out.println("Consumer End Date :"+endDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", endDate);

            return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        } else {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/consumer/apiUpdateAuthorizedRepresentative.json");

            if (startDate.equals("Future"))
                startDate = getDate("startDate");
            else if (!startDate.isEmpty())
                startDate = startDate.concat("T00:00:01Z");

            if (endDate.equals("Future"))
                endDate = getDate("endDate");
            else if (!endDate.isEmpty())
                endDate = endDate.concat("T00:00:01Z");

            String currDate = getDate("");

            System.out.println("CaseId :"+caseId);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("caseId", caseId.get());
            System.out.println("Consumer First Name :"+apiconsumerFirstName.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerFirstName", apiconsumerFirstName.get());
            System.out.println("Consumer Last Name :"+apiconsumerLastNameUpdated.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerLastName", apiconsumerLastNameUpdated.get());
            System.out.println("Consumer SSN Updated :"+apiConsumerSSN.get());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("consumerSSN", apiConsumerSSN.get());
            System.out.println("Consumer Start Date :"+startDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveStartDate", startDate);
            System.out.println("Consumer End Date :"+endDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("effectiveEndDate", endDate);
            System.out.println("Consumer Updated Date :"+currDate);
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("updatedOn", currDate);

            return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
        }
    }

    private void createCaseFromCaseLoader(String consumerRoleInput) {
        synchronized (consumerFirstName){
            consumerFirstName.set("fn".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }

        synchronized (consumerLastName){
            consumerLastName.set("ln".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomString(5).randomString));
        }

        synchronized (consumerSSN){
            consumerSSN.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(9).randomNumber);
        }

        String[] consumerRoleSplit = consumerRoleInput.split("-");
        String traceIdOrNot = consumerRoleSplit[0];
        consumerRole.set(consumerRoleSplit[1]);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        consumerDob.set(dateFormat.format(todayDate));

        requestParams.set(generateCaseLoaderRequestWithSpecifiedParameters("", consumerFirstName.get(), consumerLastName.get(), consumerSSN.get(),
                consumerDob.get(), "", "", consumerRole.get()));
        System.out.println(requestParams);

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(requestParams.get());
        if (traceIdOrNot.equals("correlation")) {
            correlationId.set(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTraceId());
            eventsCorrelationIdEndPoint=eventsCorrelationIdEndPoint+correlationId;
        } else if (traceIdOrNot.equals("case")) {
            JsonElement caseLoaderReq = requestParams.get().getAsJsonArray("caseLoaderRequest").get(0);
            JsonObject caseObj = caseLoaderReq.getAsJsonObject().get("case").getAsJsonObject();
            correlationId.set(caseObj.get("correlationId").getAsString());
            eventsCorrelationIdEndPoint=eventsCorrelationIdEndPoint+correlationId.get();
        }

        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");


        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint);
        //////apitmEventController.getAuthenticationToken("BLCRM","CRM");

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: "+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);
        boolean recordFound = false;

        for(int i=0;i<eventsContent.size();i++) {
            HashMap eventsData = (HashMap)eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            if(eventsData.get("eventName").equals("CASE_SAVE_EVENT")) {
                assertEquals(eventsData.get("status"), "success");
                assertEquals(payLoadData.get("recordType").getAsString(), "Case");
                caseId.set(dataObjectData.get("caseId").getAsString());
                System.out.println("Case Id of created Case: "+caseId.get());
                recordFound = true;
                break;
            }
        }
        assertTrue(recordFound);
    }

    private JsonObject generateCaseLoaderRequestWithSpecifiedParameters(String consumerType, String consumerFirstName, String consumerLastName,
                                                                       String consumerSSN, String consumerDateOfBirth, String GenderCode, String relationShip, String consumerRole) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/caseloader/apiCaseLoader.json");

        String caseIdentificationNo = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(8).randomNumber;
        System.out.println("caseIdentificationNo " +caseIdentificationNo);
        String correlationId = API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(10).randomNumber;
        String externalConsumerId = "x".concat(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getRandomNumber(6).randomNumber);

        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date todayDate = new Date();
        String currentDate = dateForm.format(todayDate);

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("caseIdentificationNumber", caseIdentificationNo);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerFirstName", consumerFirstName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerLastName", consumerLastName);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerSSN", consumerSSN);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerDateOfBirth", consumerDateOfBirth);
        if (!consumerRole.isEmpty())
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                    .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("consumerRole", consumerRole);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("correlationId", correlationId);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("createdOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("dateOfSsnValidation", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().addProperty("updatedOn", currentDate);
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().getAsJsonArray("caseLoaderRequest").get(0).getAsJsonObject()
                .getAsJsonObject("case").getAsJsonArray("consumers").get(0).getAsJsonObject().getAsJsonArray("consumerIdentificationNumber")
                .get(0).getAsJsonObject().addProperty("externalConsumerId", externalConsumerId);

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    private void verifyStatus(String startDate, String endDate, String status) throws ParseException {
        DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
        LocalDateTime curDateFormatted = LocalDateTime.now();

        System.out.println("start Date from Response:"+startDate);
        LocalDateTime startDateFormatted = dtf.parseLocalDateTime(startDate);
        System.out.println("status:"+status);

        if(endDate != null) {
            LocalDateTime endDateFormatted = dtf.parseLocalDateTime(endDate);
            System.out.println("end Date from Response:"+endDate);

            if(startDateFormatted.compareTo(curDateFormatted) > 0) {
                assertEquals(status, "Future");
            } else if(startDateFormatted.compareTo(curDateFormatted) < 0) {
                if(endDateFormatted.compareTo(curDateFormatted) < 0) {
                    assertEquals(status, "Inactive");
                } else
                    assertEquals(status, "Active");
            }
        } else {
            assertEquals(status, "Active");
        }
    }

    public String getDate(String dateType) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        if (dateType.equals("startDate"))
            cal.add(Calendar.DATE, 2);
        else if (dateType.equals("endDate"))
            cal.add(Calendar.DATE, 10);
        String date = dateFormat.format(cal.getTime()).concat("T00:00:01.000Z");
        System.out.println(dateType + " Future: " + date);

        return date;
    }

    private boolean verifyDatetimeFormat(String datetimeField) {
        if (datetimeField.equals(NULL_CHECK))
            return true;
        boolean datetimeFormatCheck = false;
        DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
        System.out.println(datetimeField);

        try {
            LocalDateTime parsedDate = dtf.parseLocalDateTime(datetimeField);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }
}
