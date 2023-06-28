package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ETLTalendJobExecutor implements ETLBaseClass {
    private static final ThreadLocal<String> workSpaceID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> environmentID = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> executableIDPost = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<String> executionID = ThreadLocal.withInitial(String::new);

    @Given("Workspace and Environment id is taken from workspace api {string} , {string}")
    public void workspace_and_Environment_id_is_taken_from_workspace_api(String workSpaceName, String environmentName)
            throws IOException {

        List<String> IDS = apiHelper.workSpaceCall(workSpaceName, environmentName);
        workSpaceID.set(IDS.get(0));
        environmentID.set(IDS.get(1));
    }

    @Given("get request is sent on executable with environmentID, workSpaceID and {string}")
    public void get_request_is_sent_on_executable_with_environmentID_workSpaceID_and(String jobName) throws IOException {
        System.out.println(jobName);
        System.out.println(workSpaceID.get());
        System.out.println(environmentID.get());
        executableIDPost.set(apiHelper.getExecutableCall(jobName, workSpaceID.get(), environmentID.get()));
    }

    @Given("Executable id from executable api is executed on executions")
    public void executable_id_from_executable_api_is_executed_on_executions() throws IOException {
        executionID.set(apiHelper.executablePostCall1(executableIDPost.get()));

    }

    @Then("Execution status is succesful on executions api")
    public void execution_status_is_succesful_on_executions_api() throws Exception {
        apiHelper.executionAssertion(executionID.get());

    }

}
