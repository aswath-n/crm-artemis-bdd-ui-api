package com.maersk.crm.utilities;

import com.maersk.crm.api_step_definitions.*;
import com.maersk.crm.step_definitions.CRMBusinessEvents;
import com.maersk.crm.step_definitions.CRM_CreateConsumerProfileStepDef;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;

public final class ApiThreadLocalFactory {
    private static final ThreadLocal<ApiThreadLocalFactory> apiThreadLocalFactory = ThreadLocal.withInitial(ApiThreadLocalFactory::new);

    private ApiThreadLocalFactory() {
    }

    public static ApiThreadLocalFactory getApiThreadLocalFactoryInstance() {
        return apiThreadLocalFactory.get();
    }

    private static final ThreadLocal<APIClassUtil> apiClassUtilThreadLocal = ThreadLocal.withInitial(APIClassUtil::getApiClassUtilThreadLocal);

    public APIClassUtil getAPIClassUtilThreadLocal() {
        if (apiClassUtilThreadLocal.get() == null) {
            apiClassUtilThreadLocal.set(APIClassUtil.getApiClassUtilThreadLocal(true));
        }
        return apiClassUtilThreadLocal.get();
    }

    private static final ThreadLocal<APIAutoUtilities> apiAutoUtilitiesThreadLocal = ThreadLocal.withInitial(APIAutoUtilities::new);

    public APIAutoUtilities getAPIAutoUtilities() {
        if (apiAutoUtilitiesThreadLocal.get() == null) {
            apiAutoUtilitiesThreadLocal.set(new APIAutoUtilities());
        }
        return apiAutoUtilitiesThreadLocal.get();
    }

    private static final ThreadLocal<APICaseLoaderEligibilityEnrollmentController> apiCaseLoaderEligibilityEnrollmentControllerThreadLocal = ThreadLocal.withInitial(APICaseLoaderEligibilityEnrollmentController::new);

    public APICaseLoaderEligibilityEnrollmentController getAPICaseLoaderEligibilityEnrollmentController() {
        if (apiCaseLoaderEligibilityEnrollmentControllerThreadLocal.get() == null) {
            apiCaseLoaderEligibilityEnrollmentControllerThreadLocal.set(new APICaseLoaderEligibilityEnrollmentController());
        }
        return apiCaseLoaderEligibilityEnrollmentControllerThreadLocal.get();
    }

    private static final ThreadLocal<ApiTestDataUtil> apitduThreadLocal = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);

    public ApiTestDataUtil getApitduThreadLocal() {
        if (apitduThreadLocal.get() == null) {
            apitduThreadLocal.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
        }
        return apitduThreadLocal.get();
    }

    private static final ThreadLocal<Api_CommonSteps> api_commonThreadLocal = ThreadLocal.withInitial(Api_CommonSteps::new);

    public Api_CommonSteps getApi_commonThreadLocal() {
        if (api_commonThreadLocal.get() == null) {
            api_commonThreadLocal.set(new Api_CommonSteps());
        }
        return api_commonThreadLocal.get();
    }

    private static final ThreadLocal<APIConsumerRestController> consumerControllerThreadLocal = ThreadLocal.withInitial(APIConsumerRestController::new);

    public APIConsumerRestController getConsumerControllerThreadLocal() {
        if (consumerControllerThreadLocal.get() == null) {
            consumerControllerThreadLocal.set(new APIConsumerRestController());
        }
        return consumerControllerThreadLocal.get();
    }

    private static final ThreadLocal<APIContactRecordController> contactRecordControllerThreadLocal = ThreadLocal.withInitial(APIContactRecordController::new);

    public APIContactRecordController getContactRecordControllerThreadLocal() {
        if (contactRecordControllerThreadLocal.get() == null) {
            contactRecordControllerThreadLocal.set(new APIContactRecordController());
        }
        return contactRecordControllerThreadLocal.get();
    }

    private static final ThreadLocal<APICaseRestController> apiCaseControllerThreadLocal = ThreadLocal.withInitial(APICaseRestController::new);

    public APICaseRestController getApiCaseControllerThreadLocal() {
        if (apiCaseControllerThreadLocal.get() == null) {
            apiCaseControllerThreadLocal.set(new APICaseRestController());
        }
        return apiCaseControllerThreadLocal.get();
    }

    private static final ThreadLocal<APITMEventController> apitmEventControllerThreadLocal = ThreadLocal.withInitial(APITMEventController::new);

    public APITMEventController getApitmEventControllerThreadLocal() {
        if (apitmEventControllerThreadLocal.get() == null) {
            apitmEventControllerThreadLocal.set(new APITMEventController());
        }
        return apitmEventControllerThreadLocal.get();
    }

    private static final ThreadLocal<APIEventsRestController> apiEventsThreadLocal = ThreadLocal.withInitial(APIEventsRestController::new);

    public APIEventsRestController getApiEventsThreadLocal() {
        if (apiEventsThreadLocal.get() == null) {
            apiEventsThreadLocal.set(new APIEventsRestController());
        }
        return apiEventsThreadLocal.get();
    }

    private static final ThreadLocal<APITMProjectRestController> projectRestCntrlThreadLocal = ThreadLocal.withInitial(APITMProjectRestController::new);

    public APITMProjectRestController getProjectRestCntrlThreadLocal() {
        if (projectRestCntrlThreadLocal.get() == null) {
            projectRestCntrlThreadLocal.set(new APITMProjectRestController());
        }
        return projectRestCntrlThreadLocal.get();
    }

    private static final ThreadLocal<APITaskManagementController> taskManagementControllerThreadLocal = ThreadLocal.withInitial(APITaskManagementController::new);

    public APITaskManagementController getTaskManagementControllerTheadLocal() {
        if (taskManagementControllerThreadLocal.get() == null) {
            taskManagementControllerThreadLocal.set(new APITaskManagementController());
        }
        return taskManagementControllerThreadLocal.get();
    }

    private static final ThreadLocal<APIPlanManagementController> planManagementControllerThreadLocal = ThreadLocal.withInitial(APIPlanManagementController::new);

    public APIPlanManagementController getPlanManagementControllerThreadLocal() {
        if (planManagementControllerThreadLocal.get() == null) {
            planManagementControllerThreadLocal.set(new APIPlanManagementController());
        }
        return planManagementControllerThreadLocal.get();
    }

    private static final ThreadLocal<Api_CommonSteps> api_commonStepsThreadLocal2 = ThreadLocal.withInitial(Api_CommonSteps::new);

    public Api_CommonSteps getApi_commonStepsThreadLocal2() {
        if (api_commonStepsThreadLocal2.get() == null) {
            api_commonStepsThreadLocal2.set(new Api_CommonSteps());
        }
        return api_commonStepsThreadLocal2.get();
    }

    private static final ThreadLocal<APIProviderController> providerControllerThreadLocal = ThreadLocal.withInitial(APIProviderController::new);

    public APIProviderController getProviderControllerThreadLocal() {
        if (providerControllerThreadLocal.get() == null) {
            providerControllerThreadLocal.set(new APIProviderController());
        }
        return providerControllerThreadLocal.get();
    }

    private static final ThreadLocal<APITMHolidayController> tmHolidayThreadLocal = ThreadLocal.withInitial(APITMHolidayController::new);

    public APITMHolidayController getTmHolidayThreadLocal() {
        if (tmHolidayThreadLocal.get() == null) {
            tmHolidayThreadLocal.set(new APITMHolidayController());
        }
        return tmHolidayThreadLocal.get();
    }

    private static final ThreadLocal<APITMBusinessDayRestController> businessDayThreadLocal = ThreadLocal.withInitial(APITMBusinessDayRestController::new);

    public APITMBusinessDayRestController getBusinessDayThreadLocal() {
        if (businessDayThreadLocal.get() == null) {
            businessDayThreadLocal.set(new APITMBusinessDayRestController());
        }
        return businessDayThreadLocal.get();
    }

    private static final ThreadLocal<APIConsumerEventsCheckController> consumerEventThreadLocal = ThreadLocal.withInitial(APIConsumerEventsCheckController::new);

    public APIConsumerEventsCheckController getConsumerEventThreadLocal() {
        if (consumerEventThreadLocal.get() == null) {
            consumerEventThreadLocal.set(new APIConsumerEventsCheckController());
        }
        return consumerEventThreadLocal.get();
    }

    private static final ThreadLocal<EventsUtilities> eventsUtilitiesThreadLocal = ThreadLocal.withInitial(EventsUtilities::new);

    public EventsUtilities getEventsUtilitiesThreadLocal() {
        if (eventsUtilitiesThreadLocal.get() == null) {
            eventsUtilitiesThreadLocal.set(new EventsUtilities());
        }
        return eventsUtilitiesThreadLocal.get();
    }

    private static final ThreadLocal<APITaskSearchController> taskSearchThreadLocal = ThreadLocal.withInitial(APITaskSearchController::new);

    public APITaskSearchController getTaskSearchThreadLocal() {
        if (taskSearchThreadLocal.get() == null) {
            taskSearchThreadLocal.set(new APITaskSearchController());
        }
        return taskSearchThreadLocal.get();
    }

    private static final ThreadLocal<CRMBusinessEvents> businessEventsThreadLocal = ThreadLocal.withInitial(CRMBusinessEvents::new);

    public CRMBusinessEvents getBusinessEventsThreadLocal() {
        if (businessEventsThreadLocal.get() == null) {
            businessEventsThreadLocal.set(new CRMBusinessEvents());
        }
        return businessEventsThreadLocal.get();
    }

    private static final ThreadLocal<APICreateConsumerContactController> createConsumerContactThreadLocal = ThreadLocal.withInitial(APICreateConsumerContactController::new);

    public APICreateConsumerContactController getCreateConsumerContactThreadLocal() {
        if (createConsumerContactThreadLocal.get() == null) {
            createConsumerContactThreadLocal.set(new APICreateConsumerContactController());
        }
        return createConsumerContactThreadLocal.get();
    }


}
