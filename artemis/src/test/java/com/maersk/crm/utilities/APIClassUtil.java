package com.maersk.crm.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.api_step_definitions.APITaskManagementController;
import com.maersk.dms.utilities.EventsUtilities;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.maersk.crm.api_step_definitions.APIContactRecordController.oneLoginJWT_token;
import static com.maersk.crm.step_definitions.LoginStepDef.loginUserName;
import static org.testng.Assert.assertEquals;

public final class APIClassUtil {

    public String baseUri;
    public String endPoint;
    public String request;
    public Object response;
    public String authUri;

    public String apiprojectId = "";
    private static ThreadLocal<String> apiprojectName = ThreadLocal.withInitial(String::new);
    public String apiMaxId = "";
    public String apiPassword = "";
    public String userId = "";
    public static ThreadLocal<String> jwtToken = ThreadLocal.withInitial(()->EventsUtilities.oneloginJWTValue);
    public static final ThreadLocal<HashMap<String, String>> userIds = ThreadLocal.withInitial(HashMap::new);

    public int statusCode;
    public ResponseBody responseBody;
    public String responseString;
    public JsonPath jsonPathEvaluator;
    public JsonObject apiJsonObject;
    public JsonArray apiJsonArray;
    public Map apiJsonObjectMap;
    JsonParser parser = new JsonParser();
    public String apiUserId = ConfigurationReader.getProperty("login");
    public String tmOauthEndPoint = "/mars/security/tm/login/apigee";
    public String crmOauthEndPoint = "/mars/security/login/apigee";
    public String genricOauthEndPoint = "/client/accesstoken?grant_type=client_credentials";
    public String dmsOauthEndPoint = "/mars-ecms-oauth/token?grant_type=client_credentials";
    public String userSearchEndPoint = "/mars/tm/project/users";
    public String getTmUserIdEndPoint = "/mars/tm/project/tenantmanager/user/" + apiUserId;
    //public String getTmUserIdEndPoint = "/mars/tm/project/tenantmanager/user/"+ apiUserId;
    public String oauthURI = "";
    public String apiTMURI = ConfigurationReader.getProperty("apiTMURI");
    public String genricOauthURI = ConfigurationReader.getProperty("apiOauth2");
    public String clientToken = ConfigurationReader.getProperty("clientToken");
    public static ThreadLocal<String> projectToConsider = ThreadLocal.withInitial(String::new);
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    public static ThreadLocal<String> traceId = ThreadLocal.withInitial(ApiTestDataUtil::generate_random_trace_id);
    private String spanId = traceId.get().substring(16, 32);
    private final static ThreadLocal<String> token = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<Date> tokenTakenTime = new ThreadLocal<>();
    public Api_Body apires = Api_Body.getInstance();
    Api_Storage stg = Api_Storage.getInstance();
    private JsonObject requestParams;
    private static ThreadLocal<String> applicationName = ThreadLocal.withInitial(String::new);
    private static ThreadLocal<String> tokenUrl = ThreadLocal.withInitial(()->"https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com/mars-cp-web/token");
    private static final ThreadLocal<APIClassUtil> apiClassUtilThreadLocal = ThreadLocal.withInitial(APIClassUtil::new);

    private APIClassUtil(){
    }

    public static APIClassUtil getApiClassUtilThreadLocal() {
        if(apiClassUtilThreadLocal.get()==null) {
            synchronized (apiClassUtilThreadLocal){
                apiClassUtilThreadLocal.set(new APIClassUtil());
            }
        }
        return apiClassUtilThreadLocal.get();
    }

    public synchronized static APIClassUtil getApiClassUtilThreadLocal(boolean forceCreate) {
        if(forceCreate) {
            return new APIClassUtil();
        }
        return apiClassUtilThreadLocal.get();
    }

    public String getTokenThreadLocal(){
        return token.get();
    }

    public Date getTokenTakenTime(){
        return tokenTakenTime.get();
    }

    public String getApiProjectNameThreadLocal(){
        if(apiprojectName.get()==null){
            apiprojectName.set("");
        }
        return apiprojectName.get();
    }


    RestAssuredConfig config = RestAssured.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", 600000)
                    .setParam("http.socket.timeout", 600000)
                    .setParam("http.connection-manager.timeout", 600000));

    public String getProjectId() {
        switch (applicationName.get()) {
            case "Tenant Manager":
                apiprojectId = ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[0];
                break;
            case "CRM":
                if (getApiProjectNameThreadLocal().equalsIgnoreCase("NJ-SBE")) {
                    apiprojectId = ConfigurationReader.getProperty("njProjectName").split(" ")[0];
                } else if (getApiProjectNameThreadLocal().equalsIgnoreCase("CoverVA")) {
                    apiprojectId = ConfigurationReader.getProperty("coverVAProjectName").split(" ")[0];
                } else if (getApiProjectNameThreadLocal().equalsIgnoreCase("IN-EB")) {
                    apiprojectId = ConfigurationReader.getProperty("INEBProjectName").split(" ")[0];
                } else if (getApiProjectNameThreadLocal().equalsIgnoreCase("DC-EB")) {
                    apiprojectId = ConfigurationReader.getProperty("DCEBProjectName").split(" ")[0];
                } else {
                    apiprojectId = ConfigurationReader.getProperty("projectName").split(" ")[0];
                }
                break;
            default:
                apiprojectId = ConfigurationReader.getProperty("projectName").split(" ")[0];
        }
        if (apiprojectId.isEmpty() || apiprojectId == "") {
            throw new java.lang.Error("apiprojectId is empty. Please check 'projectName' to ensure Project Id is included.");
        }
        return apiprojectId;
    }

    public String getProjectName() {
        // apiprojectName.set(ConfigurationReader.getProperty("projectName").split(" ")[1]; // always returning BLCRM as project name even for CoverVA and INEB projects
        if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
            throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
        }
        //apiprojectName = "BLCRM"; // always returning BLCRM as project name even for CoverVA and INEB projects
        return apiprojectName.get();
    }


    public String getTraceId() {
        return traceId.get();
    }

    public APIClassUtil setbaseUri(String base_uri) {
        if (base_uri == null || base_uri == "") {
            throw new NullPointerException("Base URI cannot be empty.");
        }
        if (base_uri.substring(base_uri.length() - 1) == "/") {
            this.baseUri = base_uri.replace(base_uri.substring(base_uri.length() - 1), "");
        } else {
            this.baseUri = base_uri;
        }
        this.baseUri = this.baseUri.replace("\"", "");
        return this;
    }

    public APIClassUtil setEndPoint(String end_point) {
        if (end_point == null || end_point == "") {
            throw new NullPointerException("End point cannot be empty.");
        }
        if (end_point.charAt(0) == '/') {
            this.endPoint = end_point;
        } else {
            this.endPoint = "/" + end_point;
        }
        return this;
    }

    private APIClassUtil SetAndPrintTestInfo() {
        //System.out.println("URL: "+ this.baseUri + this.endPoint);
        RestAssured.baseURI = this.baseUri;
        RestAssured.useRelaxedHTTPSValidation();
        return this;
    }

    /*//modifiedBy:Aswath Date:05/22/2020 Reason:- New endpoint
    public String getOauthToken() {
        String token = null;
        String oauthEndPoint = "";
        String tokenClient = "Basic " + clientToken;
        JsonObject json = new JsonObject();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .header("domain", "la")
                .header("Authorization", tokenClient)
                .log().all()
                .when()
                .post(this.genricOauthURI + genricOauthEndPoint);

        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        System.out.println(this.response);
        token = jsonPathEvaluator.getJsonObject("access_token");
        token = "Bearer " + token;
        System.out.println("Token : " + token);
        return token;
    }
*/

    //reusable method
    public APIClassUtil getAPI() {
        /*traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);*/
        this.SetAndPrintTestInfo();
        //this.getProjectName1("");
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .get(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        System.out.println("Inside GET API");
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        try {
            if (!this.responseString.isEmpty()) {
                //this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
                this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
            }
        } catch (Exception e) {
            System.out.println("response body is JsonArray -> saving as JsonArray");
            this.apiJsonArray = parser.parse(this.responseString).getAsJsonArray();
        }

        return this;
    }

    //reusable method
    public APIClassUtil getAPIAndQuery(Map<String, ?> query_parameters) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-traceid", traceId.get())
                .header("x-b3-spanid", spanId)
                .queryParams(query_parameters)
                .log().all()
                .when()
                .get(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil GetAPIWithParameter(JsonObject jsonObject) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(jsonObject.toString())
                .log().all()
                .when()
                .get(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        JsonParser parser = new JsonParser();
        try {
            this.apiJsonObject = parser.parse(this.responseBody.asString()).getAsJsonObject();
        } catch (Exception e) {
            this.apiJsonObject = null;
        }
        try {
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        } catch (Exception e) {
            this.apiJsonObjectMap = null;
        }
        return this;
    }

    //reusable method
    public APIClassUtil GetAPIWithParameterAndQuery(JsonObject jsonObject, Map<String, ?> query_parameters) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .queryParams(query_parameters)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .get(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil PostAPIWithParameterAndQuery(JsonObject jsonObject, Map<String, ?> query_parameters) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .queryParams(query_parameters)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil PostAPIWithParameter(JsonObject jsonObject) {
        String traceId1 = apitdu.generate_random_trace_id();
        APITaskManagementController.traceId.set(traceId1);
        String spanId1 = traceId1.substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                // .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId1)
                .header("x-b3-traceid", traceId1)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
//        System.out.println(responseString);
//        It's logging all anyway
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        try {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        } catch (IllegalStateException e) {
            System.out.println("response body is JsonArray -> saving as JsonArray");
            this.apiJsonArray = parser.parse(this.responseString).getAsJsonArray();
        }
        return this;
    }

    public APIClassUtil PostAPIWithParameter(String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil PostAPIWithParameterForProject(JsonObject jsonObject, String projectId) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                // .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", projectId)
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil PostAPIWithKey(String Key, String Value) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .contentType("multipart/form-data")
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("Authorization", getTokenThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .header("created-by", "2662")
                //.header("created-by", getApiTMUserInfo()) -- Need Refactor get users
                .header("onelogin-jwt", EventsUtilities.oneloginJWTValue)
                .multiPart("file", new File(Value))
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        //this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        //this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //reusable method
    public APIClassUtil PutAPI(JsonObject jsonObject) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body(jsonObject.toString())
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
        System.out.println("span id " + spanId);
        System.out.println("trace id " + traceId.get());
        return this;
    }

    public APIClassUtil PutAPIwithoutBody(JsonObject jsonObject) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();

        Assert.assertTrue(this.responseString.equals("Success"));
        System.out.println("span id " + spanId);
        System.out.println("trace id " + traceId.get());
        return this;
    }

    //modifiedBy:-Vidya Date:-12/11/2019 Reason:- make it as Public because I need this method in to get staffId so
    public String getEncryptedString(String str) {
        byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
        String encodedString = new String(bytesEncoded);
        return encodedString;
    }

    public String getDecryptedString(String str) {
        byte[] decryptedString = Base64.decodeBase64(str.getBytes());
        String decodedString = new String(decryptedString);
        return decodedString;
    }

    public String getEncodedAPIMaxId() {
        apiUserId = ConfigurationReader.getProperty("login");
        if (apiUserId.isEmpty() || apiUserId == "") {
            throw new java.lang.Error("maxId is empty. Please check 'login' to ensure Login Id is included.");
        }
        apiMaxId = getEncryptedString(apiUserId);
        return apiMaxId;
    }

    public String getEncodedAPIPassword() {
        String maxPassword = ConfigurationReader.getProperty("password");
        if (maxPassword.isEmpty() || maxPassword == "") {
            throw new java.lang.Error("Password is empty. Please check 'password' to ensure Login Id is included.");
        }
        apiPassword = getEncryptedString(maxPassword);
        return apiPassword;
    }

    //modifiedBy:Vidya Date:01/13/2020 Reason:- Added a lone of code to take userId
    /*public String getOauthToken() {
        String token = null;
        String oauthEndPoint = "";
        JsonObject json = new JsonObject();
        String maxId = ConfigurationReader.getProperty("login");
        this.getEncryptedString(maxId);
        if (projectToConsider.equalsIgnoreCase("tm")) {
            oauthEndPoint = tmOauthEndPoint;
            json.addProperty("loginName", getEncodedAPIMaxId());
            json.addProperty("passwordValue", getEncodedAPIPassword());
        } else {
            oauthEndPoint = crmOauthEndPoint;
            json.addProperty("maxId", getEncodedAPIMaxId());
            json.addProperty("password", getEncodedAPIPassword());
            json.addProperty("projectId", this.getProjectId());
        }

        RequestSpecification request = RestAssured.given();
        Object response = request
                .header("Content-Type", "application/json")
                .body(json.toString())
                .log().all()
                .when()
                .post(this.oauthURI + oauthEndPoint);

        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        System.out.println(this.response);
        token = jsonPathEvaluator.getJsonObject("apigeeResponse.access_token");
        try {
            userId = apiJsonObject.getAsJsonObject("user").getAsJsonObject("user").get("userId").toString();
        } catch (Exception e) {
            userId = apiJsonObject.getAsJsonObject("user").getAsJsonObject("staffVO").
                    getAsJsonObject("user").get("userId").toString();
        }

        token = "Bearer " + token;
        System.out.println("Token : " + token);
        return token;
    }*/

    public APIClassUtil PutAPI(String jsonObject) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body(jsonObject)
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        System.out.println("span id " + spanId);
        System.out.println("trace id " + traceId.get());
        return this;
    }

    public String getProjectName1(String projName) {
        /*apiprojectName  = ConfigurationReader.getProperty("projectName").split(" ")[1];
        if (getApiProjectNameThreadLocal().isEmpty() || apiprojectName==""){
            throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
        }*/
        switch (projName) {
            case "BLCRM":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameBL"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;
            case "FHK":
                String projectName = ConfigurationReader.getProperty("apiProjectNameFHK");
                if (projectName == null || projectName.equals("")) {
                    apiprojectName.set(projName);
                } else {
                    apiprojectName.set(projectName);
                }
                break;
            case "LACRM":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameLA"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "TXCRM":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameTX"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "GACRM":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameGA"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "NJ-SBE":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameNJ"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "CoverVA":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameCoverVA"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "Regression":
                apiprojectName.set(ConfigurationReader.getProperty("tmRegressionProjectName").split(" ")[1]);
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "IN-EB":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameIN"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            case "BLATS2":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameBLATS2"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;
            case "DC-EB":
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameDC"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal() == "") {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                break;

            default:
                apiprojectName.set(ConfigurationReader.getProperty("apiProjectNameBL"));
                if (getApiProjectNameThreadLocal().isEmpty() || getApiProjectNameThreadLocal().equals("")) {
                    throw new java.lang.Error("apiprojectName is empty. Please check 'projectName' to ensure Project Name is included.");
                }
                //apiprojectName = "BLCRM";
                break;

        }
        return getApiProjectNameThreadLocal();
    }

    //modifiedBy:Vidya Date:01/13/2020 Reason:- Added a lone of code to take userId
    //modifiedBy:Aswath Date:06/01/2020 Reason:- new enpoints for fetching user ID's and Oauth endpoint
    //modifiedBy:Aswath Date:10/29/2020 Reason:- added line to check access token is expired or not before taking...
    public String getOauthToken(String app, String projectName) {
        setApplicationName(app);
        String oauthEndPoint = "";
        JsonObject json = new JsonObject();
        if ((getTokenThreadLocal().length() <= 0 && tokenTakenTime.get() == null) || timeCompare()) {
            synchronized (token){
                tokenTakenTime.set(new Date());
                switch (app) {
                    case "Tenant Manager":
                        this.oauthURI = genricOauthURI;
                        oauthEndPoint = genricOauthEndPoint;
                        break;

                    case "CRM":
                        this.oauthURI = genricOauthURI;
                        oauthEndPoint = genricOauthEndPoint;
                        break;

                    case "ATS":
                        this.oauthURI = genricOauthURI;
                        oauthEndPoint = genricOauthEndPoint;
                        break;

                    default:
                        this.oauthURI = genricOauthURI;
                        oauthEndPoint = genricOauthEndPoint;
                }
                String tokenClient = "Basic " + clientToken;
                RequestSpecification request = RestAssured.given();
                Object response = request
                        .header("Content-Type", "application/json")
                        .header("Authorization", tokenClient)
                        .body(json.toString())
                        .log().all()
                        .when()
                        .post(this.oauthURI + oauthEndPoint);

                this.response = ((Response) response).toString();
                this.statusCode = ((Response) response).statusCode();
                this.responseBody = ((Response) response).getBody();
                this.responseString = this.responseBody.asString();
                this.jsonPathEvaluator = ((Response) response).jsonPath();
                this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
                this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
                System.out.println(this.response);
                token.set(jsonPathEvaluator.getJsonObject("access_token"));
                token.set("Bearer " + token.get());
//            await().atMost(10, SECONDS).until(tokenReceived());
                System.out.println("Token : " + token.get());
            }
        }
        if (userId.length() <= 0) {
            switch (app) {
                case "Tenant Manager":
                    if (!userIds.get().containsKey("tm"))
                        userIds.get().put("tm", getApiTMUserInfo());
                    userId = userIds.get().get("tm");
                    System.out.println("Printing TM UsrId " + userId);
                    break;

                case "CRM":
                    switch (projectName) {
                        case "BLCRM":
                            System.out.println("user id case blcrm start" + "\n TIMESTAMP" + LocalDateTime.now());
                            if (!userIds.get().containsKey("blcrm"))
                                userIds.get().put("blcrm", getApiUserInfo());
                            userId = userIds.get().get("blcrm");
                            System.out.println("user id case blcrm end " + "\n TIMESTAMP" + LocalDateTime.now());
                            break;

                        case "NJ-SBE":
                            if (!userIds.get().containsKey("njsbe"))
                                userIds.get().put("njsbe", getApiUserInfo());
                            userId = userIds.get().get("njsbe");
                            break;

                        case "CoverVA":
                            if (!userIds.get().containsKey("coverva"))
                                userIds.get().put("coverva", getApiUserInfo());
                            userId = userIds.get().get("coverva");
                            break;

                        case "IN-EB":
                            if (!userIds.get().containsKey("ineb"))
                                userIds.get().put("ineb", getApiUserInfo());
                            userId = userIds.get().get("ineb");
                            break;
                        case "DC-EB":
                            if (!userIds.get().containsKey("dceb"))
                                userIds.get().put("dceb", getApiUserInfo());
                            userId = userIds.get().get("dceb");
                            break;

                        default:
                            if (!userIds.get().containsKey("blcrm"))
                                userIds.get().put("blcrm", getApiUserInfo());
                            userId = userIds.get().get("blcrm");
                            break;
                    }
                    System.out.println("Printing CRM UsrId " + userId);
                    break;

                case "DMS":
                    break;
            }
        }
        return getTokenThreadLocal();
    }

    private Callable<Boolean> tokenReceived() {
        return () ->{
            Boolean b = getTokenThreadLocal().contains("Bearer");
            return b;
        };
    }

    public boolean verifyDatetimeFormat(JsonElement datetimeField) {
        boolean datetimeFormatCheck = false;
        if (datetimeField.toString().equals("null"))
            return true;
        String dateTimeFieldFormatted = datetimeField.toString().substring(1, datetimeField.toString().length() - 1);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(dateTimeFieldFormatted, dtf);
            if (parsedDate != null)
                datetimeFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return datetimeFormatCheck;
    }

    public boolean verifyDateFormat(String dateField) {
        if (dateField == null)
            return true;
        boolean dateFormatCheck = false;
        String dateFieldFormatted = dateField.substring(1, dateField.length() - 1);
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFieldFormatted.toString());
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }

    public String getDate(String dateField) {
        String dateFormatted = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = formatter.parse(dateField);
            dateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }
        return dateFormatted;
    }

    public String getDateForISOFormat(String dateElem) {
        String dateInFormat = null;
        if (dateElem.equals("null"))
            return dateInFormat;
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(dateElem, dtf);
            LocalDate localDate = parsedDate.toLocalDate();
//            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateInFormat = localDate.format(formatter);
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateInFormat;
    }

    public void PostWithRequestBody(String requestBody) {
        PostWithRequestBody("", requestBody);
    }

    public void PostWithRequestBody(String name, String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .name(name)
                .build();
        RequestSpecification request = RestAssured.given();
        Response response = request.config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(this.endPoint);
        request.then().log().all();
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.response = ((Response) response).toString();
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
    }


    public APIClassUtil getApiRequest() {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName1("");
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-traceid", traceId.get())
                .header("x-b3-spanid", spanId)
                .log().all()
                .when()
                .get(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        this.response = (response.toString());
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        return this;
    }


    public APIClassUtil getApiRequestWithQuery(Map<String, ?> query_parameters) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-traceid", traceId.get())
                .header("x-b3-spanid", spanId)
                .queryParams(query_parameters)
                .log().all()
                .when()
                .get(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        this.response = (response.toString());
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        return this;
    }

    public void putApiRequest(String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .name("")
                .build();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body(requestBody)
                .log().all()
                .when()
                .put(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
    }

    public void PostWithNameRequest(String name, String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(this.endPoint);
        request.then().log().all();
        //Commenting the lines as we need to handle request body based on its instance type i.e. json array or object
//        requestBody = requestBody.substring(0, requestBody.length() - 1) +
//                ",\"traceid\":\"" + traceId.get() + "\"}";
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .name(name)
                .build();
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.response = ((Response) response).toString();
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
    }

    public void PostRequestSaveResponseByName(String name, String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .post(this.endPoint);
        request.then().log().all();
        requestBody = requestBody.substring(0, requestBody.length() - 1) +
                ",\"traceid\":\"" + traceId.get() + "\"}";
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name(name)
                .build();
        stg.addConsumed(api_res);
        this.request = (request.log().toString());
        this.response = ((Response) response).toString();
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
    }

    public String getApiUserInfo() {
        setbaseUri(apiTMURI);
        System.out.println("base " + apiTMURI);
        setEndPoint(userSearchEndPoint);
        System.out.println("end " + userSearchEndPoint);
        apitdu.getJsonFromFile("tenantmanager/apiGetUserId.json");
        if (loginUserName.get().isEmpty()) {
//            await().atMost(10, SECONDS).until(tokenReceived());
            apiUserId = getApiProjectNameThreadLocal().equals("CoverVA") || getApiProjectNameThreadLocal().equals("IN-EB")
                    ? ConfigurationReader.getProperty("svcTester2") : ConfigurationReader.getProperty("login");
        } else apiUserId = loginUserName.get();
        apitdu.jsonElement.getAsJsonObject().addProperty("maxId", apiUserId);
        apitdu.jsonElement.getAsJsonObject().get("user").getAsJsonObject().addProperty("projectId", this.getProjectId());
        requestParams = apitdu.jsonElement.getAsJsonObject();
        System.out.println("PARAMS " + requestParams);
        PostAPIWithParameter(requestParams);
        assertEquals(statusCode, 200, "token - "+getTokenThreadLocal());
        JsonArray json1 = apiJsonObject.getAsJsonObject().getAsJsonObject("staffList").getAsJsonArray("content").get(0).getAsJsonObject().getAsJsonArray("usersList");
        for (int i = 0; i < json1.size(); i++) {
            JSONObject temp = new JSONObject(json1.get(i).toString());
            if (temp.get("projectId").toString().equals(getProjectId())) {
                userId = temp.get("userId").toString();
            }
        }
        return userId;
    }


    public String getApiTMUserInfo() {
        setbaseUri(apiTMURI);
        setEndPoint(getTmUserIdEndPoint);
        getAPI();
        System.out.println("response " + responseString);
        assertEquals(statusCode, 200);
        userId = jsonPathEvaluator.get("userId").toString();
        return userId;
    }

    //reusable method of put method without traceId ad spanId in header Author:Vidya
    public APIClassUtil PutAPIWithoutTraceId(JsonObject jsonObject) {
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .accept("application/json")
                .body(jsonObject.toString())
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
        return this;
    }

    public void setApplicationName(String appName) {
        applicationName.set(appName);
    }

    public void PutWithNameRequest(String name, String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .name(name)
                .build();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .put(this.endPoint);
        request.then().log().all();
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();

    }

    public boolean timeCompare() {
        boolean withinRange = false;
        Date now = Calendar.getInstance().getTime();

        System.out.println(tokenTakenTime.get() + "  " + now + "  ");

        long millis = Math.abs(now.getTime() - tokenTakenTime.get().getTime());
        System.out.println("Millis: " + millis);

        //1000ms * 60s * 60m * 5h
        if (millis >= (1000 * 60 * 60 * 5)) {
            withinRange = true;
        }
        System.out.println("withinRange=" + withinRange);
        return withinRange;

    }

    public boolean dateComparision(String date1, String date2) throws ParseException {
        boolean flag = false;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(date1);
        Date d2 = sdformat.parse(date2);
        if (d1.compareTo(d2) >= 0) {
            flag = true;
            System.out.println("Date 1 occurs after Date 2");
        } else if (d1.compareTo(d2) < 0) {
            System.out.println("Date 1 occurs before Date 2");
            flag = false;
        }
        return flag;
    }

    public String getJWTToken(String cookie) {


        return jwtToken.get();
    }

    //reusable method
    public APIClassUtil PostAPIWithParameter(JsonArray jsonArray) {
        String traceId1 = apitdu.generate_random_trace_id();
        APITaskManagementController.traceId.set(traceId1);
        String spanId1 = traceId1.substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                // .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId1)
                .header("x-b3-traceid", traceId1)
                .body(jsonArray.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        System.out.println(responseString);
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        try {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        } catch (IllegalStateException e) {
            System.out.println("response body is JsonArray -> saving as JsonArray");
            this.apiJsonArray = parser.parse(this.responseString).getAsJsonArray();
        }
        return this;
    }

    //reusable method
    public APIClassUtil PutAPIWithParameter(JsonObject jsonObject) {
        String traceId1 = apitdu.generate_random_trace_id();
        APITaskManagementController.traceId.set(traceId1);
        String spanId1 = traceId1.substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                // .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId1)
                .header("x-b3-traceid", traceId1)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .put(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        System.out.println(responseString);
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        try {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        } catch (IllegalStateException e) {
            System.out.println("response body is JsonArray -> saving as JsonArray");
            this.apiJsonArray = parser.parse(this.responseString).getAsJsonArray();
        }
        return this;
    }


    /* Method for uploading alert files with 'form-data' parameter
     */
    public APIClassUtil PutMiltiPartUpload(String fileName, String filePath) throws IOException {
        this.SetAndPrintTestInfo();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("./src/test/resources/testData/cc/" + filePath)))
                .addFormDataPart("alertRequest", null,
                        RequestBody.create(MediaType.parse("application/json"), "{ \"createdBy\":3455, \"updatedBy\":3455}".getBytes()))
                .build();
        Request request = new Request.Builder()
                .url(this.baseUri + this.endPoint)
                .method("PUT", body)
                .addHeader("Authorization", getTokenThreadLocal())
                .addHeader("Accept", "application/json,")
                .addHeader("Project-Name", getApiProjectNameThreadLocal())
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("==================+++++++========================");
        this.statusCode = response.code();
        String responseBody = response.body().string();
        this.apiJsonObject = parser.parse(responseBody).getAsJsonObject();
        return this;
    }

    //POST API if requires to use both projectId and projectName on Headers
    public APIClassUtil PostAPIWithParameter(JsonObject jsonObject, String projectName) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("Project-Id", this.apiprojectId)
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        //     this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
        this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        return this;
    }

    //POST API custom headers
    public APIClassUtil PostCustomHeaders(JsonObject jsonObject, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .headers(headers)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public APIClassUtil PostCustomHeadersFormParam(Map<String, String> mapRequest, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .headers(headers)
                .formParams(mapRequest)
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public String getApiUserIdbyUserName(String userName) {
        String userIdForUserName = "";
        setbaseUri(apiTMURI);
        System.out.println("base " + apiTMURI);
        setEndPoint(userSearchEndPoint);
        System.out.println("end " + userSearchEndPoint);
        apitdu.getJsonFromFile("tenantmanager/apiGetUserId.json");
        if (loginUserName.get().isEmpty()) {
            apiUserId = getApiProjectNameThreadLocal().equals("CoverVA") || getApiProjectNameThreadLocal().equals("IN-EB")
                    ? ConfigurationReader.getProperty("svcTester2") : ConfigurationReader.getProperty("login");
        } else apiUserId = loginUserName.get();
        apitdu.jsonElement.getAsJsonObject().addProperty("firstName", userName);
        apitdu.jsonElement.getAsJsonObject().get("user").getAsJsonObject().addProperty("projectId", this.getProjectId());
        requestParams = apitdu.jsonElement.getAsJsonObject();
        System.out.println("PARAMS " + requestParams);
        PostAPIWithParameter(requestParams);
        assertEquals(statusCode, 200);
        JsonArray json1 = apiJsonObject.getAsJsonObject().getAsJsonObject("staffList").getAsJsonArray("content").get(0).getAsJsonObject().getAsJsonArray("usersList");
        for (int i = 0; i < json1.size(); i++) {
            JSONObject temp = new JSONObject(json1.get(i).toString());
            if (temp.get("projectId").toString().equals(getProjectId())) {
                userIdForUserName = temp.get("userId").toString();
            }
        }
        return userIdForUserName;
    }

    public String getApiUserIdbyFirstNameAndLastname(String userFirstName, String userLastName) {
        String userIdForUserName = "";
        setbaseUri(apiTMURI);
        System.out.println("base " + apiTMURI);
        setEndPoint(userSearchEndPoint);
        System.out.println("end " + userSearchEndPoint);
        apitdu.getJsonFromFile("tenantmanager/apiGetUserId.json");
        if (loginUserName.get().isEmpty()) {
            apiUserId = getApiProjectNameThreadLocal().equals("CoverVA") || getApiProjectNameThreadLocal().equals("IN-EB")
                    ? ConfigurationReader.getProperty("svcTester2") : ConfigurationReader.getProperty("login");
        } else apiUserId = loginUserName.get();
        apitdu.jsonElement.getAsJsonObject().addProperty("firstName", userFirstName);
        apitdu.jsonElement.getAsJsonObject().addProperty("lastName", userLastName);
        apitdu.jsonElement.getAsJsonObject().get("user").getAsJsonObject().addProperty("projectId", this.getProjectId());
        requestParams = apitdu.jsonElement.getAsJsonObject();
        PostAPIWithParameter(requestParams);
        assertEquals(statusCode, 200);
        JsonArray json1 = apiJsonObject.getAsJsonObject().getAsJsonObject("staffList").getAsJsonArray("content").get(0).getAsJsonObject().getAsJsonArray("usersList");
        for (int i = 0; i < json1.size(); i++) {
            JSONObject temp = new JSONObject(json1.get(i).toString());
            if (temp.get("projectId").toString().equals(getProjectId())) {
                userIdForUserName = temp.get("userId").toString();
            }
        }
        return userIdForUserName;
    }

    public APIClassUtil PutAPIWithoutJsonResponse(JsonObject jsonObject) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body(jsonObject.toString())
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();

        System.out.println("span id " + spanId);
        System.out.println("trace id " + traceId.get());
        return this;
    }

    public String getCurentTimeFormatUntillSec() {
        Clock clock = Clock.system(ZoneId.of("GMT"));
        ZonedDateTime now = ZonedDateTime.now(clock);
        String expectedDateAndTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now);
        expectedDateAndTime = expectedDateAndTime.substring(0, 19);
        return expectedDateAndTime;
    }

    public String convertUIDateToAPI(String uiFormatDate) throws ParseException { // "MM/dd/yyyy"
        DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = originalFormat.parse(uiFormatDate);
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public void GETWithRequestBody(String requestBody) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .build();
        RequestSpecification request = RestAssured.given();
        Response response = request.config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .body(requestBody)
                .log().all()
                .when()
                .get(this.endPoint);
        request.then().log().all();
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.response = ((Response) response).toString();
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
    }

    public APIClassUtil deleteAPI() {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName1("");
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Authorization", getTokenThreadLocal())
                .header("Content-Type", "application/json")
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("Project-Id", this.apiprojectId)
                .header("x-b3-traceid", traceId.get())
                .header("x-b3-spanid", spanId)
                .log().all()
                .when()
                .delete(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        try {
            if (!this.responseString.isEmpty()) {
                this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
                this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
            }
        } catch (Exception e) {
            System.out.println("response body is JsonArray -> saving as JsonArray");
            this.apiJsonArray = parser.parse(this.responseString).getAsJsonArray();
        }
        return this;
    }

    public APIClassUtil PutApiWithCustomeHeaders(JsonObject jsonObject, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        this.SetAndPrintTestInfo();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .headers(headers)
                .body(jsonObject.toString())
                .log().all()
                .when()
                .put(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public APIClassUtil PostApiWithCustomeHeaders(JsonArray jsonArray, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        this.SetAndPrintTestInfo();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .headers(headers)
                .body(jsonArray.toString())
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public APIClassUtil PatchApiWithCustomHeaders(JsonObject jsonRequest, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        this.SetAndPrintTestInfo();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .headers(headers)
                .body(jsonRequest.toString())
                .log().all()
                .when()
                .patch(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    public APIClassUtil PostWithHeaders(String jsonBody, Map<String, String> headers) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .headers(headers)
                .body(jsonBody)
                .log().all()
                .when()
                .post(this.endPoint);
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        this.responseString = this.responseBody.asString();
        Api_Body api_res = new EventBuilder()
                .body(responseString)
                .name("")
                .build();
        stg.addConsumed(api_res);
        return this;
    }

    public void putApiRequest(String requestBody, String oneLoginJWTToken) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        Api_Body api_req = new EventBuilder()
                .body(requestBody)
                .name("")
                .build();
        RequestSpecification request = RestAssured.given();
        Response response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", oneLoginJWTToken)
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body(requestBody)
                .log().all()
                .when()
                .put(this.endPoint);
        Api_Body api_res = new EventBuilder()
                .body(response.getBody().prettyPrint())
                .name("")
                .build();
        stg.addConsumed(api_res);
        stg.addProduced(api_req);
        this.request = (request.log().toString());
        this.statusCode = (response.statusCode());
        this.responseBody = (response.getBody());
        this.responseString = this.responseBody.asString();
        this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
    }

    public void tokenRequestWithOneLoginJWTToken() {
        apitdu.getJsonFromFile("crm/one_login_jwt/tokenByOneLoginJWTBody.json");
        String json = apitdu.jsonElement.toString();

        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Content-Type", "application/json")
                .header("onelogin-jwt", oneLoginJWT_token.get())
                .header("Accept", "*/*")
                .accept("application/json")
                .body(json)
                .log().all()
                .when()
                .post(tokenUrl.get())
                .then()
                .statusCode(200)
                .extract().response();

        token.set(response.jsonPath().get("accessToken.access_token"));
        token.set("Bearer " + token.get());
        System.out.println("Token : " + token.get());
    }

    //reusable method with multipart
    public APIClassUtil PostApiWithMultiparts(Map<String, Object> multiparts) {
        traceId.set(apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        this.getProjectName();
        this.SetAndPrintTestInfo();
        RequestSpecification request = RestAssured.given();
        request.contentType("multipart/form-data")
                .header("Authorization", APIClassUtil.token.get())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get());
        for (String multipart : multiparts.keySet()) {
            request.multiPart(multipart, multiparts.get(multipart));
        }
        Object response = request.log().all()
                .when()
                .post(this.endPoint);
        ;
        this.request = ((RequestSpecification) request).log().toString();
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        return this;
    }

    //reusable method and storing response in original format
    public void getAPIdefault() {
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("domain", "la")
                .header("Authorization", APIClassUtil.token.get())
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .get(endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();

    }

    public APIClassUtil PutArrAPI(JsonObject jsonObject) {
        traceId.set( apitdu.generate_random_trace_id());
        spanId = traceId.get().substring(16, 32);
        this.SetAndPrintTestInfo();
        //this.getProjectName();
        RequestSpecification request = RestAssured.given();
        Object response = request
                .config(config)
                .header("domain", "la")
                .header("Content-Type", "application/json")
                .header("Authorization", getTokenThreadLocal())
                .header("Project-Name", this.getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId.get())
                .accept("application/json")
                .body("["+jsonObject.toString()+"]")
                .log().all()
                .when()
                .put(this.endPoint);
        this.response = ((Response) response).toString();
        this.statusCode = ((Response) response).statusCode();
        this.responseBody = ((Response) response).getBody();
        this.responseString = this.responseBody.asString();
        this.jsonPathEvaluator = ((Response) response).jsonPath();
        if (!this.responseString.isEmpty()) {
            this.apiJsonObject = parser.parse(this.responseString).getAsJsonObject();
            this.apiJsonObjectMap = ((Response) response).body().as(Map.class);
        }
        System.out.println("span id " + spanId);
        System.out.println("trace id " + traceId.get());
        return this;
    }
}
