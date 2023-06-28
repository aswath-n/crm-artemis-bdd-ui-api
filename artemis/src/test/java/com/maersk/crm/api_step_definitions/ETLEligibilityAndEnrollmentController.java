package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import io.cucumber.java.en.When;

import java.util.Map;

public class ETLEligibilityAndEnrollmentController extends CRMUtilities implements ETLBaseClass, ApiBase {

    @When("I connect to Data Base")
    public void I_connect_to_Data_Base() {
        db.createConnection();
    }

    @When("Verify DB consumer actions for consumer id {string} with data")
    public void Verify_DB_consumer_actions_for_consumer_id_with_data(String consumerId, Map<String, String> data) {
        if (consumerId.isEmpty()) {
            consumerId = APIConsumerPopulationDmnController.consumerId.get();
        } else {
            consumerId = API_THREAD_LOCAL_FACTORY.getApi_commonThreadLocal().defineValue(consumerId).toString().replace(".0", "");
        }

        db.runQuery("SELECT * FROM `mars-enrollment-ineb`.CONSUMER_ACTIONS where CONSUMER_ID = '" + consumerId + "'");

        db.displayAllData();
    }
}

