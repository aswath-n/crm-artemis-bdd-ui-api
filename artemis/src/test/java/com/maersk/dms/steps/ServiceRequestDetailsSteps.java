package com.maersk.dms.steps;

import com.maersk.dms.step_definitions.ServiceRequestDetailsStepDefs;
import io.cucumber.java.en.Given;
import java.util.Map;

public class ServiceRequestDetailsSteps {
    ServiceRequestDetailsStepDefs serviceRequestStepDefs = new ServiceRequestDetailsStepDefs();

    @Given("I will provide following information on Service Request details page")
    public void i_will_provide_following_information_on_Service_Request_details_page(Map<String, String> dataTable) {
        serviceRequestStepDefs.fillOutServiceRequestWithRandomData(dataTable);
    }

}
