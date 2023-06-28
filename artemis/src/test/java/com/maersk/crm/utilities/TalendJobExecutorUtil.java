package com.maersk.crm.utilities;

import com.google.gson.JsonArray;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class TalendJobExecutorUtil extends CRMUtilities implements ETLBaseClass, ApiBase {
    public static String traceId = ApiTestDataUtil.generate_random_trace_id();
    public static String apiprojectName = "";
    private String spanId = traceId.substring(16, 32);

    public List<String> workSpaceCall(String workSpaceName, String environmentName) {
        RequestSpecification request = RestAssured.given();
        Response responseWorkSpaces = request
                .header("Content-Type", "application/json")
                .header("Authorization",ConfigurationReader.getProperty("talendAuthorization") )
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId)
                .when().get("https://api.us.cloud.talend.com/tmc/v2.4/workspaces");
        Assert.assertEquals(responseWorkSpaces.getStatusCode(), 200);
        String environmentID = "";
        String workSpaceID = "";
        JsonArray projects = parser.parse(responseWorkSpaces.getBody().asString()).getAsJsonArray();
        System.out.println(responseWorkSpaces.getBody().toString());
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getAsJsonObject().get("name").getAsString().equalsIgnoreCase(workSpaceName)) {
                if ((projects.get(i).getAsJsonObject().get("environment").getAsJsonObject().get("name").getAsString().equalsIgnoreCase(environmentName))) {
                    environmentID = projects.get(i).getAsJsonObject().get("environment").getAsJsonObject().get("id").getAsString();
                    workSpaceID = projects.get(i).getAsJsonObject().get("id").getAsString();
                }
            }
        }
        return new ArrayList<>(Arrays.asList(workSpaceID, environmentID));
    }

    public String getExecutableCall(String jobName, String workSpaceID, String environmentID) {
        Response executableGetResponse = given()
                .header("Content-Type", "application/json")
                .header("Authorization", ConfigurationReader.getProperty("talendAuthorization"))
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId)
                .queryParam("environmentId", environmentID)
                .queryParam("workspaceId", workSpaceID)
                .queryParam("name", jobName)
                .when().get("https://api.us.cloud.talend.com/tmc/v2.4/executables/tasks").prettyPeek();
        System.out.println(executableGetResponse.getBody().toString());
        Assert.assertEquals(executableGetResponse.getStatusCode(), 200);
        if(jobName.equals("DCEB_dailyEligibleJob"))return parser.parse(executableGetResponse.getBody().asString()).getAsJsonObject().get("items").getAsJsonArray().get(1)
                .getAsJsonObject().get("executable").getAsString();
        else  return parser.parse(executableGetResponse.getBody().asString()).getAsJsonObject().get("items").getAsJsonArray().get(0)
                .getAsJsonObject().get("executable").getAsString();

    }

    public String executablePostCall1(String executableIDPost) {

        Response executablePost = given()
                .header("Content-Type", "application/json")
                .header("Authorization", ConfigurationReader.getProperty("talendAuthorization"))
                .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId)
                .when().body("{\"executable\": \"" + executableIDPost + "\"}")
                .post("https://api.us.cloud.talend.com/tmc/v2.4/executions");
        waitFor(30);
        return parser.parse(executablePost.getBody().asString()).getAsJsonObject().get("executionId").getAsString();
    }

    public String executionAssertion(String executionID){

        String executionsBaseUri = "https://api.us.cloud.talend.com/tmc/v2.4/executions";
        String executionStatusURL = executionsBaseUri + "/" + executionID;
        waitFor(20);
        Response executionStatusResponse = given()
                .header("Content-Type", "application/json")
                .header("Authorization",  ConfigurationReader.getProperty("talendAuthorization"))
//                .header("Project-Na org.junit.Assert.assertEquals(executablePost.getStatusCode(),200);me", getAPIClassUtil().getApiProjectNameThreadLocal())
                .header("x-b3-spanid", spanId)
                .header("x-b3-traceid", traceId)
                .when().get(executionStatusURL);
        return parser.parse(executionStatusResponse.getBody().asString()).getAsJsonObject().get("status").getAsString();
    }


}
