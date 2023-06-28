package com.maersk.crm.utilities;

import com.maersk.crm.step_definitions.CRM_CreateConsumerProfileStepDef;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;

public final class StepDefinitionThreadLocalFactory {
    private static final ThreadLocal<StepDefinitionThreadLocalFactory> stepDefTreadLocalFactory = ThreadLocal.withInitial(StepDefinitionThreadLocalFactory::new);
    private StepDefinitionThreadLocalFactory(){
    }
    public static StepDefinitionThreadLocalFactory getStepDefinitionThreadLocalFactoryInstance(){
        return stepDefTreadLocalFactory.get();
    }

    private static final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorrEventsThreadLocal = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    public PublishDPBIEventsOutboundCorrespondenceChangesStepDefs getOutCorrEventsThreadLocal(){
        if (outCorrEventsThreadLocal.get() == null){
            outCorrEventsThreadLocal.set(new PublishDPBIEventsOutboundCorrespondenceChangesStepDefs());
        }
        return outCorrEventsThreadLocal.get();
    }

    private static final ThreadLocal<CRM_CreateConsumerProfileStepDef> crmCreateConsumerProfileStepDefThreadLocal = ThreadLocal.withInitial(CRM_CreateConsumerProfileStepDef::new);
    public CRM_CreateConsumerProfileStepDef getCrmCreateConsumerProfileStepDefThreadLocal(){
        if (crmCreateConsumerProfileStepDefThreadLocal.get() == null){
            crmCreateConsumerProfileStepDefThreadLocal.set(new CRM_CreateConsumerProfileStepDef());
        }
        return crmCreateConsumerProfileStepDefThreadLocal.get();
    }
}
