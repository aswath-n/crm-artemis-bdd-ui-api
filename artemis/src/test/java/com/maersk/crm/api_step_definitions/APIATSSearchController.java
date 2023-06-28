package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.step_definitions.CRMApplicationTrackingStepDef;
import com.maersk.crm.step_definitions.CRM_CreateApplicationStepDef;
import com.maersk.crm.utilities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.*;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.externalAppID;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.*;
import static com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController.apiApplicationID;
import static org.testng.Assert.*;

public class APIATSSearchController extends CRMUtilities implements ApiBase {

    private final String baseApplicationDataUrl = ConfigurationReader.getProperty("apiApplicationData");
    private final String createApp = "/mars/applicationdata/applications";
    private final String searchApp = "/mars/applicationdata/applications/search";
    private final String addAppMem = "/consumers";
    //   APIATSApplicationController applicationController=new APIATSApplicationController();

    private final ThreadLocal <JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal <String> firstName = ThreadLocal.withInitial(()->"Fn" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal <String> lastName = ThreadLocal.withInitial(()->"Ln" + RandomStringUtils.random(5, true, false));
    private final ThreadLocal <String> ssn = ThreadLocal.withInitial(()->RandomStringUtils.random(9, false, true));
    private final ThreadLocal <String> cellNum = ThreadLocal.withInitial(()->"9" + RandomStringUtils.random(9, false, true));
    private final ThreadLocal <String> homeNum = ThreadLocal.withInitial(()->"7" + RandomStringUtils.random(9, false, true));
    private final ThreadLocal <String> appReceivedFrom = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> appReceivedTo = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> appDeadlineFrom = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> appDeadlineTo = ThreadLocal.withInitial(String::new);

    private String dob = "1997-11-14";
    private List<String> channnels = Arrays.asList("Email", "Fax", "Mail", "Phone", "Web");
    private final ThreadLocal <String> applicationId = ThreadLocal.withInitial(String::new);
    BrowserUtils browserUtils = new BrowserUtils();

    @When("I initiated create application api for ats")
    public void initiateCreateApplicationForAts() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp);
    }

    @When("I initiated create application member api for ats")
    public void initiateCreateApplicationMemberForAts() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createApp + "/" + apiApplicationID.get() + addAppMem);
    }


    @And("I run create application api for ats with type {string}")
    public String runCreateApplicationForAts(String interactiveType) {
        requestParams.set(getCreateApplicationRequest(interactiveType));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Create Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);

        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        HashMap appData = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("data");
        applicationId.set(String.valueOf(appData.get("applicationId")));

        System.out.println("Application id is: " + applicationId);
        return applicationId.get();
    }

    @When("I initiated search application api for ats")
    public void initiateSearchApplicationForAts() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(baseApplicationDataUrl);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(searchApp);
    }

    @And("I search for the created application using search api by {string} for ats")
    public void searchCreateApplicationForAts(String type) {
        requestParams.set(getSearchApplicationRequest(type));

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @Then("I verify all the fields retrieved for ats from the search response")
    public void verifyFieldsSearchResponseForAts() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.results");
        for (Object o : dataList) {
            HashMap dataValues = (HashMap) o;
            assertTrue(dataValues.get("applicationId").toString().chars().allMatch(Character::isDigit));
            assertTrue(channnels.contains(dataValues.get("channelId").toString()));
            assertEquals(dataValues.get("applicationCycle"), "New");
            assertEquals(dataValues.get("consumerDateOfBirth"), piDOB);
            assertEquals(dataValues.get("consumerFirstName"), piFirstName);
            assertEquals(dataValues.get("consumerLastName"), piLastName);
            assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
        }
    }

    @Then("I verify matching records for ats by {string} from the search response")
    public void verifyRecordsSearchResponseForAts(String type) throws ParseException {
        if (!(type.contains("APPLICATION RECEIVED DATE RANGE") || type.contains("APPLICATION DEADLINE DATE RANGE"))) {
            assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        }
        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.results");
        if (type.contains("multiple individuals")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertTrue(dataValues.get("applicationId").toString().chars().allMatch(Character::isDigit));
                System.out.println("Found app id from response: " + dataValues.get("applicationId"));
                System.out.println("Expected app id from create app: " + applicationIdAPI.get());
                //   assertTrue(dataValues.get("applicationId").toString().equals(applicationIdAPI)); because there are more than one application
                assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
                assertTrue(dataValues.get("consumerFirstName").toString().toLowerCase().contains(piFirstName.get().substring(0, 3).toLowerCase()));
            }
        } else if (type.contains("APPLICATION RECEIVED DATE RANGE")) {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                LocalDate date_actual = LocalDate.parse(dataValues.get("applicationReceivedDate").toString(), sdf);
                if (!(appReceivedFrom.get().isEmpty())) {
                    LocalDate date1 = LocalDate.parse(appReceivedFrom.get(), sdf);
                    assertTrue(date_actual.isAfter(date1) || date1.isEqual(date_actual));
                }
                if (!(appReceivedTo.get().isEmpty()) || appReceivedTo == null) {
                    LocalDate date2 = LocalDate.parse(appReceivedTo.get(), sdf);
                    assertTrue(date_actual.isBefore(date2) || date2.isEqual(date_actual));
                }
            }
        } else if (type.contains("APPLICATION DEADLINE DATE RANGE")) {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                LocalDate date_actual = LocalDate.parse(dataValues.get("applicationDeadlineDate").toString(), sdf);
                if (!(appDeadlineFrom.get().isEmpty())) {
                    LocalDate date1 = LocalDate.parse(appDeadlineFrom.get(), sdf);
                    assertTrue(date_actual.isAfter(date1) || date1.isEqual(date_actual));
                }
                if (!(appDeadlineTo.get().isEmpty()) || appReceivedTo == null) {
                    LocalDate date2 = LocalDate.parse(appDeadlineTo.get(), sdf);
                    assertTrue(date_actual.isBefore(date2) || date2.isEqual(date_actual));
                }
            }
        } else if (type.contains("single field")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertTrue(dataValues.get("applicationId").toString().chars().allMatch(Character::isDigit));
                assertTrue(dataValues.get("applicationId").toString().equals(applicationIdAPI.get()));
                assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
                assertEquals(dataValues.get("consumerFirstName"), piFirstName);
            }
        } else if (type.contains("multiple field")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertTrue(dataValues.get("applicationId").toString().chars().allMatch(Character::isDigit));
                assertTrue(dataValues.get("applicationId").toString().equals(applicationIdAPI.get()));
                assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
                assertEquals(dataValues.get("consumerFirstName"), piFirstName);
            }
        } else if (type.contains("partial parameters")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertTrue(dataValues.get("applicationId").toString().chars().allMatch(Character::isDigit));
                assertTrue(dataValues.get("applicationId").toString().equals(applicationIdAPI.get()));
                assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
                assertTrue(dataValues.get("consumerFirstName").toString().toLowerCase().contains(piFirstName.get().substring(0, 3).toLowerCase()));
                assertTrue(dataValues.get("consumerLastName").toString().toLowerCase().contains(piLastName.get().substring(0, 3).toLowerCase()));
            }
        } else if (type.contains("EXTERNAL APPLICATION ID")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertEquals(dataValues.get("externalApplicationId"), externalAppID, "External Applicaiton ID doesn't match for Search Results");
            }
        } else if (type.equalsIgnoreCase("APPLICATION CODE")) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                if (!applicationIdAPI.get().equals("")) {
                    assertEquals("" + dataValues.get("applicationId"), applicationIdAPI.get(), "Applicaiton ID doesn't match for Search Results");
                    assertEquals(dataValues.get("channelId"), "Web", "Channel Id doesn't match for Search Results");
                    assertEquals(dataValues.get("applicationCode"), APIATSSendEventAndCreateLinksController.apiApplicationCode, "Applicaiton code doesn't match for Search Results");
                    if (APIATSSendEventAndCreateLinksController.apiProgramType.get().equalsIgnoreCase("Medicaid")) {
                        assertEquals(dataValues.get("programId"), "Medicaid", "Application program isnt verified for application program eligibility status update");
                    } else if (APIATSSendEventAndCreateLinksController.apiProgramType.get().equalsIgnoreCase("HCBS")) {
                        assertEquals(dataValues.get("programId"), "HCBS", "Application program isnt verified for application program eligibility status update");
                    }
                    assertEquals(dataValues.get("consumerDateOfBirth"), APIATSApplicationController.piDOB, "Consumer Date Of Birth doesn't match for Search Results");
                    assertEquals(dataValues.get("consumerFirstName"), APIATSApplicationController.piFirstName, "Consumer Firdt Name doesn't match for Search Results");
                    assertEquals(dataValues.get("consumerLastName"), APIATSApplicationController.piLastName, "Consumer Last Name doesn't match for Search Results");
                } else {
                    assertEquals("" + dataValues.get("applicationId"), CRM_CreateApplicationStepDef.applicationId, "Application Id isnt valid for application withdrawn event");
                    assertEquals(dataValues.get("channelId"), CRM_CreateApplicationStepDef.channel, "Channel Id doesn't match for Search Results");
                    assertEquals(dataValues.get("applicationCode"), CRMApplicationTrackingStepDef.applicationCodeFromUI, "Applicaiton ID doesn't match for Search Results");
                    if (CRM_CreateApplicationStepDef.applicatonType.equalsIgnoreCase("Medical Assistance")) {
                        assertEquals(dataValues.get("programId"), "Medicaid", "Application program isnt verified for application program eligibility status update");
                    } else if (CRM_CreateApplicationStepDef.applicatonType.equalsIgnoreCase("Long Term Care")) {
                        assertEquals(dataValues.get("programId"), "HCBS", "Application program isnt verified for application program eligibility status update");
                    }
                    Date consumerDob = new SimpleDateFormat("MM/dd/yyyy").parse(CRM_CreateApplicationStepDef.piDOB);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String consumerDateOfBirth = sdf.format(consumerDob);
                    assertEquals(dataValues.get("consumerDateOfBirth"), "" + consumerDateOfBirth, "Consumer Date Of Birth doesn't match for Search Results");
                    assertEquals(dataValues.get("consumerFirstName"), CRM_CreateApplicationStepDef.piFirstName, "Consumer Firdt Name doesn't match for Search Results");
                    assertEquals(dataValues.get("consumerLastName"), CRM_CreateApplicationStepDef.piLastName, "Consumer Last Name doesn't match for Search Results");
                }
                assertEquals(dataValues.get("applicationCycle"), "New", "Applicaiton cycle doesn't match for Search Results");
                assertTrue(dataValues.get("applicationConsumerId").toString().chars().allMatch(Character::isDigit));
                if (("" + dataValues.get("submittedInd")).equals("true")) {
                    assertEquals(dataValues.get("applicationStatus"), "Determining", "Application Status doesn't match for Search Results");
                } else if (("" + dataValues.get("submittedInd")).equals("false")) {
                    assertEquals(dataValues.get("applicationStatus"), "Entering Data", "app status isnt verified when submittedInd is false");
                }
                assertEquals("" + dataValues.get("externalApplicationId"), "null", "External Application Id doesn't match for Search Results");
                assertEquals(dataValues.get("consumerRoleType"), "Primary Individual", "Consumer Role Type doesn't match for Search Results");
            }
        } else if ("REFERENCE ID".equals(type)) {
            for (Object o : dataList) {
                HashMap dataValues = (HashMap) o;
                assertEquals(dataValues.get("applicationId").toString(), applicationIdAPI.get(), "Mismatch in Reference Type search result request for applicationId");
                assertEquals(dataValues.get("consumerFirstName").toString(), piFirstName.get(), "Mismatch in Reference Type search result request for consumerFirstName");
                assertEquals(dataValues.get("consumerLastName").toString(), piLastName.get(), "Mismatch in Reference Type search result request for consumerLastName");
                assertEquals(dataValues.get("consumerDateOfBirth").toString(), piDOB.get(), "Mismatch in Reference Type search result request for consumerDateOfBirth");
                assertEquals(dataValues.get("applicationCycle").toString(), "New", "Mismatch in Reference Type search result request for applicationCycle");
                assertEquals(dataValues.get("applicationConsumerId").toString(), appconsumerIdList.get().get(0), "Mismatch in Reference Type search result request for applicationConsumerId");
                assertEquals(dataValues.get("programId").toString(), "Medicaid", "Mismatch in Reference Type search result request for programId");
                assertEquals(dataValues.get("applicationStatus").toString(), "Determining", "Mismatch in Reference Type search result request for applicationStatus");
                assertEquals(dataValues.get("consumerRoleType").toString(), "Primary Individual", "Mismatch in Reference Type search result request for consumerRoleType");
                assertEquals(dataValues.get("applicationCode").toString(), applicationCode.get(), "Mismatch in Reference Type search result request for applicationCode");
            }
        } else if ("INVALID REFERENCE ID".equals(type)) {
            assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("errors[0].message"), "No Search Results Found", "Mismatch in expected errors message");
        }
    }


    @Then("I verify order of records for ats from the search response")
    public void verifyRecordsOrderSearchResponseForAts() {
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List<Integer> applicationIds = new ArrayList<>();

        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.results");
        for (Object o : dataList) {
            HashMap dataValues = (HashMap) o;
            applicationIds.add((Integer) dataValues.get("applicationId"));
        }

        boolean sortDesc = true;
        for (int i = 0; i < applicationIds.size() - 1; i++) {
            if (applicationIds.get(i) < applicationIds.get(i + 1)) {
                sortDesc = false;
                break;
            }
        }
        assertTrue(sortDesc);
    }

    @Then("I verify {string} message from the search response")
    public void verifyMessageSearchResponseForAts(String type) {
        switch (type) {
            case "excessive result": {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("error"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "error");

                ArrayList errorList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("errors");
                for (Object o : errorList) {
                    HashMap errorData = (HashMap) o;
                    assertEquals(errorData.get("message"), "Search results in excess of 200 results, please enter additional search criteria to limit search results");
                }
                break;
            }
            case "no result":
            case "invalid search": {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

                ArrayList errorList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("errors");
                for (Object o : errorList) {
                    HashMap errorData = (HashMap) o;
                    assertEquals(errorData.get("message"), "No Search Results Found");
                }
                break;
            }
            case "no search": {
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("INVALID"));
                assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "INVALID");

                ArrayList errorList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("errors");
                for (Object o : errorList) {
                    HashMap errorData = (HashMap) o;
                    assertEquals(errorData.get("message"), "Enter search parameters");
                }
                break;
            }
        }
    }

    private JsonObject getCreateApplicationRequest(String interactiveType) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/createApplication.json");

        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("applicationConsumers").getAsJsonArray().get(0).getAsJsonObject().addProperty("consumerFirstName", firstName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("applicationConsumers").getAsJsonArray().get(0).getAsJsonObject().addProperty("consumerLastName", lastName.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("applicationConsumers").getAsJsonArray().get(0).getAsJsonObject().addProperty("ssn", ssn.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("applicationConsumers").getAsJsonArray().get(0).getAsJsonObject().get("applicationConsumerPhone").getAsJsonArray().get(0).getAsJsonObject().addProperty("phoneNumber", cellNum.get());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().get("applicationConsumers").getAsJsonArray().get(0).getAsJsonObject().get("applicationConsumerPhone").getAsJsonArray().get(2).getAsJsonObject().addProperty("phoneNumber", homeNum.get());

        if (interactiveType.equals("false")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("interactiveInd", false);
        } else if (interactiveType.equals("true")) {
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("interactiveInd", true);
        }

        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @And("I post application search api with application received date from {string} to {string}")
    public void setApplicationReceivedFromTo(String appReceivedFrom, String appReceivedTo) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/searchApplication.json");
        if (appReceivedFrom.equalsIgnoreCase("TODAY")) {
            this.appReceivedFrom.set(getCurrentDateInYearFormat());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateFrom", this.appReceivedFrom.get());
        } else if (appReceivedFrom.equalsIgnoreCase("YESTERDAY")) {
            this.appReceivedFrom.set(LocalDate.now().minusDays(1).toString());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateFrom", this.appReceivedFrom.get());
        } else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateFrom", appReceivedFrom);
        if (appReceivedTo.equalsIgnoreCase("TODAY")) {
            this.appReceivedTo.set(getCurrentDateInYearFormat());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateTo", this.appReceivedTo.get());
        } else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateTo", appReceivedTo);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @And("I post application search api with application deadline date from {string} to {string}")
    public void setApplicationDeadlineFromTo(String appDeadlineFrom, String appDeadlineTo) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/searchApplication.json");
        if (appDeadlineFrom.equalsIgnoreCase("TODAY")) {
            this.appDeadlineFrom.set(getCurrentDateInYearFormat());
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateFrom", this.appDeadlineFrom.get());
        } else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateFrom", appDeadlineFrom);
        if (appDeadlineTo.equalsIgnoreCase("FUTURE 90")) {
            this.appDeadlineTo.set(getGreaterDateInYearFormat(90));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateTo", this.appDeadlineTo.get());
        } else if (appDeadlineTo.equalsIgnoreCase("FUTURE 45")) {
            this.appDeadlineTo.set(getGreaterDateInYearFormat(45));
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateTo", this.appDeadlineTo.get());
        } else
            API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateTo", appDeadlineTo);
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
         API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
         System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
         assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
    }

    @When("I post application search api with application deadline date and application received date")
    public void iPostApplicationSearchApiWithApplicationDeadlineDateAndApplicationReceivedDate() {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/searchApplication.json");
        this.appDeadlineFrom.set(getCurrentDateInYearFormat());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationDeadlineDateFrom", this.appDeadlineFrom.get());
        this.appReceivedTo.set(getCurrentDateInYearFormat());
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationReceivedDateTo", this.appReceivedTo.get());
        requestParams.set(API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject());
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(requestParams.get());
        System.out.println("Search Application Response: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
    }

    private JsonObject getSearchApplicationRequest(String type) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("crm/ats/searchApplication.json");

        switch (type) {
            case "created individual":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", piFirstName.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("lastName", piLastName.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dob", piDOB.get());
                break;
            case "other search params":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", "");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("dob", piDOB.get());

                JsonArray programList = new JsonArray();
                programList.add("CHIP");

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("program", programList);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationId", applicationId.get());

                JsonArray channelListPhone = new JsonArray();
                channelListPhone.add("Phone");

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("channel", channelListPhone);

                JsonArray cycleListNew = new JsonArray();
                cycleListNew.add("New");

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("applicationCycle", cycleListNew);
                break;
            case "multiple individuals":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", piFirstName.get().substring(0, 3));
                break;
            case "single field":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", piFirstName.get());
                break;
            case "multiple field":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", piFirstName.get());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("lastName", piLastName.get());
                break;
            case "partial parameters":
            case "default sort":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", piFirstName.get().substring(0, 3));
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("lastName", piLastName.get().substring(0, 3));
                break;
            case "excessive result":
                JsonArray cycleList = new JsonArray();
                cycleList.add("New");

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("applicationCycle", cycleList);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", "j");
                break;
            case "no result":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", "dsflk");
                break;
            case "no search":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", "");
                break;
            case "invalid search":
                JsonArray channelList = new JsonArray();
                channelList.add("invalid");

                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("channel", channelList);
                break;
            case "EXTERNAL APPLICATION ID":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("externalApplicationId", externalAppID.get());
                break;
            case "case id":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refId", World.generalSave.get().get("caseConsumerCaseId").toString());
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refIdType", "Internal");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refType", "Case");
                break;
            case "APPLICATION CODE":
                if (APIATSSendEventAndCreateLinksController.apiApplicationCode != null) {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationCode", APIATSSendEventAndCreateLinksController.apiApplicationCode.get());
                } else {
                    API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("applicationCode", CRMApplicationTrackingStepDef.applicationCodeFromUI);
                }
                break;
            case "REFERENCE ID":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refIdType", "Internal");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refType", "Case");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refId", searchCaseIdList.get().get(0));
                break;
            case "INVALID REFERENCE ID":
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refIdType", "Internal");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refType", "Case");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("refId", "11");
                break;
            case "duplicate status":
                JsonArray statusList = new JsonArray();
                statusList.add("Duplicate");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("status", statusList);
                break;
            case "complete status":
                JsonArray statusComplete = new JsonArray();
                statusComplete.add("Complete");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("status", statusComplete);
                break;
            case "closed status with first name":
                JsonArray statusExpired = new JsonArray();
                statusExpired.add("Closed");
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().add("status", statusExpired);
                API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject().addProperty("firstName", "dfv");
                break;
        }
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Then("I verify {string} 200 records in descending order on application search page")
    public void verifyTptalNoOfSearchResponseForAts(String records) {
        int recordsSize = 200;
        List<Integer> applicationIds = new ArrayList<>();
        ArrayList dataList = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("data.results");
        System.out.println("size--" + dataList.size());
        switch (records) {
            case "equals":
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("error"));
                assertEquals(dataList.size(), recordsSize, "Records Size is not equal to 200");
                break;
            case "lessThan":
                assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status").equals("success"));
                Assert.assertTrue(dataList.size() < recordsSize, "200 records are displaying");
                break;
            default:
                fail("More Than 200 records are displaying");
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200, "wrong status code");

        for (Object o : dataList) {
            HashMap dataValues = (HashMap) o;
            applicationIds.add((Integer) dataValues.get("applicationId"));
        }
        boolean sortDesc = true;
        for (int i = 0; i < applicationIds.size() - 1; i++) {
            if (applicationIds.get(i) < applicationIds.get(i + 1)) {
                sortDesc = false;
                break;
            }
        }
        assertTrue(sortDesc, "Records is not in Descending order");
    }
}
