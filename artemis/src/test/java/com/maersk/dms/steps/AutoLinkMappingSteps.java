package com.maersk.dms.steps;

import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.dms.step_definitions.AutoLinkMappingStepDefs;
import io.cucumber.java.en.And;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.maersk.dms.step_definitions.AutoLinkMappingStepDefs.autolinkMapId;

public class AutoLinkMappingSteps extends CRMUtilities implements ApiBase {

    private final ThreadLocal<AutoLinkMappingStepDefs> autoLinkMappingStepDefs =  ThreadLocal.withInitial(AutoLinkMappingStepDefs::new);
    private ApiTestDataUtil apitdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
    private final ThreadLocal<APIClassUtil> apiClassUtilLocalCopy = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    public ThreadLocal<String> projectId = ThreadLocal.withInitial(String::new);
    private ThreadLocal<List<Map<String, Object>>> retrieveAutoLinkList = ThreadLocal.withInitial(ArrayList::new);

    @And("I will initiate and post autolinkMap API with following values for {string} project ID")
    public void iWillInitiateAndPostAutolinkMapAPIWithFollowingValuesForProjectID(String projectId, Map<String, String> dataTable) {
        if (projectId.equalsIgnoreCase("CURRENT")) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        } else {
            this.projectId.set(projectId);
        }
        autoLinkMappingStepDefs.get().addDataTableValuesToJson(dataTable);
        apiClassUtilLocalCopy.set(autoLinkMappingStepDefs.get().postAutoLinkRequest(this.projectId.get()));
    }

    @And("I will initiate and post autolinkMap API with following values for {string} project ID if there is no exact match")
    public void iWillInitiateAndPostAutolinkMapAPIWithFollowingValuesForProjectIDIfThereIsNoExactMatch(String projectId, Map<String, String> dataTable) {
        if (projectId.equalsIgnoreCase("CURRENT")) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        } else {
            this.projectId.set(projectId);
        }
        autoLinkMappingStepDefs.get().addDataTableValuesToJson(dataTable);
        if (!autoLinkMappingStepDefs.get().searchForExactMatch(this.projectId.get())) {
            apiClassUtilLocalCopy.set(autoLinkMappingStepDefs.get().postAutoLinkRequest(this.projectId.get()));
        }
    }

    @And("I verify success message along with created AutoLinkMap ID")
    public void iVerifySuccessMessageAlongWithCreatedAutoLinkMapID() {
        Assert.assertEquals(apiClassUtilLocalCopy.get().statusCode, 201);
        Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.get("status"), "success");
        Assert.assertTrue((apiClassUtilLocalCopy.get().jsonPathEvaluator.get("autolinkMapId").toString().chars().allMatch(Character::isDigit)));
    }

    @And("I store the created AutoLinkMap ID")
    public void iStoreTheCreatedAutoLinkMapID() {
        autoLinkMappingStepDefs.get().saveAutoLinkMapID();
    }

    @And("I retrieve the AutoLink Maps for {string} project ID")
    public void iRetrieveTheAutoLinkMapsForProjectID(String projectId) {
        if (projectId.equalsIgnoreCase("CURRENT")) {
            this.projectId.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        } else {
            this.projectId.set(projectId);
        }
        retrieveAutoLinkList.set(autoLinkMappingStepDefs.get().getAutoLinkMappingsByProjectID(this.projectId.get()));
    }

    @And("I verify created AutoLink Map Details")
    public void iVerifyCreatedAutoLinkMapDetails() {
        autoLinkMappingStepDefs.get().verifyCreatedAutoLinkMappingDetails();
    }

    @And("I delete created AutoLink Map ID and verify successfully deleted message")
    public void iDeleteCreatedAutolinkMapIDAndVerifySuccessfullyDeletedMessage() {
        apiClassUtilLocalCopy.set(autoLinkMappingStepDefs.get().deleteAutoLinkById(autolinkMapId.get()));
        Assert.assertEquals(apiClassUtilLocalCopy.get().statusCode, 200);
        Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.get("status"), "success");
        Assert.assertTrue((apiClassUtilLocalCopy.get().jsonPathEvaluator.get("message").toString().equalsIgnoreCase("Successfully deleted autolinkmap id " + autolinkMapId.get())));
    }

    @And("I verify created AutoLink Map doesn't exist on the list")
    public void iVerifyCreatedAutoLinkMapDoesnTExistOnTheList() {
        Assert.assertNull(autoLinkMappingStepDefs.get().returnCreatedAutolinkMappingByProjectId(projectId.get()), "AutoLink is not deleted.Found in retrieved List");
    }

    @And("I attempt the delete same AutoLink ID and verify error message")
    public void iAttemptTheDeleteSameAutoLinkIDAndVerifyErrorMessage() {
        apiClassUtilLocalCopy.set(autoLinkMappingStepDefs.get().deleteAutoLinkById(autolinkMapId.get()));
        Assert.assertEquals(apiClassUtilLocalCopy.get().statusCode, 400);
        Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.get("status"), "fail");
        Assert.assertTrue((apiClassUtilLocalCopy.get().jsonPathEvaluator.get("message").toString().equalsIgnoreCase("Autolinkmap id not found")));
    }

    @And("I verify retrieve AutoLink Map Endpoint returns empty array")
    public void iVerifyRetrieveAutolinkMapEndpointReturnsEmptyArray() {
        Assert.assertTrue(retrieveAutoLinkList.get().isEmpty(), "Retrieve Auto Link API doesn't return empty array");
    }

    @And("I verify error message for {string} as {string}")
    public void iVerifyErrorMessageForAs(String fieldName, String errorMessage) {
        Assert.assertEquals(apiClassUtilLocalCopy.get().statusCode, 400);
        Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.get("status"), "fail");
        String[] fieldNameArray = fieldName.split(",");
        String[] errorMessageArray = errorMessage.split(",");
        for (int i = 0; i < fieldNameArray.length; i++) {
            Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.getString("validationList[" + i + "].fieldName"), fieldNameArray[i]);
            Assert.assertEquals(apiClassUtilLocalCopy.get().jsonPathEvaluator.getString("validationList[" + i + "].validationErrorMessage"), errorMessageArray[i]);
        }
    }
}
