package com.maersk.dms.steps;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.DPBIEventsStepDefs;
import com.maersk.dms.step_definitions.InboundCorrespondenceNoteEventStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceDefinitionStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class DPBIEventsSteps extends CRMUtilities implements ApiBase {
    private final ThreadLocal<DPBIEventsStepDefs> dpbiEventsStepDefs = ThreadLocal.withInitial(DPBIEventsStepDefs::new);
    private final ThreadLocal<OutboundCorrespondenceDefinitionStepDefs> outboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionStepDefs::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private final ThreadLocal<String> traceId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<InboundCorrespondenceNoteEventStepDefs> inboundCorrespondenceNoteEventStepDefs = ThreadLocal.withInitial(InboundCorrespondenceNoteEventStepDefs::new);
    private final ThreadLocal<OutboundCorrespondenceStepDefs> outboundCorrespondeneStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceStepDefs::new);

    /**
     * get correspondence settings, update settings and save changed
     * value in World.save outboundCorrespondenceEcmsDocumentType value
     * for OUTBOUND_CORRESPONDENCE_SETTING_UPDATE_EVENT
     */
    @Given("that I have configured the Outbound Correspondence Settings for a project")
    public void saveCorrespondenceSettingsStep() {
        World.save.get().put("outboundCorrespondenceEcmsDocumentType", RandomStringUtils.random(5, true, false)); //save in World save map to verify in payload
        dpbiEventsStepDefs.get().getCorrespondenceSettings();
        dpbiEventsStepDefs.get().changeSettings("outboundCorrespondenceEcmsDocumentType", "outboundCorrespondenceEcmsDocumentType", World.save);
        dpbiEventsStepDefs.get().updateCorrespondenceSettings();
    }

    @When("I search the Get Events endpoint for {string} event name")
    public void iSearchTheGetEventsEndpoint(String eventName) {
        switch (eventName) {
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT":
                dpbiEventsStepDefs.get().searchEvents(eventName, "Name");
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTING_UPDATE_EVENT":
                dpbiEventsStepDefs.get().searchEvents(eventName, "outboundCorrespondenceEcmsDocumentType");
                break;
        }
    }

    /**
     * verify event that was created contains value
     * from World.save in the payload
     */
    @Then("I should see the {string} that was created")
    public void iShouldSeeTheThatWasCreated(String event) {
        switch (event) {
            case "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT":
                dpbiEventsStepDefs.get().verifyEvent(event, "Name");
                break;
            case "OUTBOUND_CORRESPONDENCE_SETTING_UPDATE_EVENT":
                dpbiEventsStepDefs.get().verifyEvent(event, "outboundCorrespondenceEcmsDocumentType");
                break;
        }
    }

    @Given("that I have created a Correspondence Definition through api for {string} project")
    public void thatIHaveCreatedACorrespondenceDefinitionThroughApi(String project) {
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        apiAutoUtilities.postOutboundCorrespondenceDefinitionToServer(project);
    }

    @And("I retrieve the {string} event that is produced by trace id")
    public void iRetrieveTheEventThatIsProducedByTraceId(String event) {
        switch (event) {
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                traceId.set(inboundCorrespondenceNoteEventStepDefs.get().getTraceId());
                break;
            case "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT":
                traceId.set(inboundCorrespondenceNoteEventStepDefs.get().getTraceId("documentType"));
                World.save.get().put("INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT", traceId.get());
                break;
            case "INBOUND_CORRESPONDENCE_UPDATE_EVENT":
                traceId.set(inboundCorrespondenceNoteEventStepDefs.get().getTraceId("receivedDate"));
                World.save.get().put("INBOUND_CORRESPONDENCE_UPDATE_EVENT_TRACEID", traceId.get());
                break;
            case "NOTIFICATION_UPDATE_EVENT":
                traceId.set(outboundCorrespondeneStepDefs.get().getTraceId());
                break;
            case "NOTIFICATION_UPDATE_EVENT_API":
                traceId.set(String.valueOf(World.generalSave.get().get("updateNidTraceId" + Hooks.nameAndTags.get())));
                System.out.println("NOTIFICATION_UPDATE_EVENT_API - " + traceId.get());
                Assert.assertNotNull(traceId.get());
                break;
            case "CONSUMER_NOTIFICATION_RETURNED":
                traceId.set(String.valueOf(World.generalSave.get().get("updateNidTraceId" + Hooks.nameAndTags.get())));
                System.out.println("NOTIFICATION_UPDATE_EVENT_API - " + traceId.get());
                Assert.assertNotNull(traceId.get());
                break;
            case "APPLICATION_CONSUMER_NOTIFICATION_RETURNED":
                traceId.set(String.valueOf(World.generalSave.get().get("updateNidTraceId" + Hooks.nameAndTags.get())));
                System.out.println("NOTIFICATION_UPDATE_EVENT_API - " + traceId.get());
                Assert.assertNotNull(traceId.get());
                break;
            case "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH":
                traceId.set( String.valueOf(World.generalSave.get().get("PatchTraceId" + Hooks.nameAndTags.get())));
                System.out.println("NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH - " + traceId.get());
                Assert.assertNotNull(traceId.get());
                break;
            case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                traceId.set( outboundCorrespondeneStepDefs.get().getTraceId_correspondence());
                break;
            case "OUTBOUND_CORRESPONDENCE_SAVE_EVENT":
                traceId.set( outboundCorrespondeneStepDefs.get().getTraceId_CreateCorrespondence());
                break;
            case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API":
                traceId.set( String.valueOf(World.generalSave.get().get("updateNidTraceId" + Hooks.nameAndTags.get())));
                System.out.println("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API - " + traceId.get());
                break;
            case "LINK_EVENT_CASE_IB_CORRESPONDENCE":
                traceId.set( inboundCorrespondenceNoteEventStepDefs.get().getTraceId("link", "case"));
                break;
            case "LINK_EVENT_CONSUMER_IB_CORRESPONDENCE":
                traceId.set( inboundCorrespondenceNoteEventStepDefs.get().getTraceId("link", "consumer"));
                break;
            case "LINK_EVENT_CASECONSUMER_IB_CORRESPONDENCE":
                traceId.set(inboundCorrespondenceNoteEventStepDefs.get().getTraceId("link", "caseandconsumer"));
                break;
            case "LINK_EVENT_TASK_TO_APPLICATION":
                traceId.set(World.save.get().get("taskApplicationTraceID"));
                break;
            case "LINK_EVENT_TASK_TO_CASES":
                traceId.set( World.save.get().get("taskCaseTraceID"));
                break;
            case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT":
            case "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT":
                traceId.set(String.valueOf(World.generalSave.get().get("postNotificationTraceId" + Hooks.nameAndTags.get())));
                break;
            case "LETTER_DATA_SAVE_EVENT":
            case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT_EXTERNALIDS":
                traceId.set( String.valueOf(World.generalSave.get().get("OBREQUESTTRACEID" + Hooks.nameAndTags.get())));
                Assert.assertNotNull(traceId.get(), "tracied null");
                break;

            default:
                //traceId.get() saved from send event step def
                traceId.set(String.valueOf(World.generalSave.get().get("sendEventTraceId")));
        }
        Assert.assertNotNull(traceId.get());
        waitFor(15);
    }

    @Then("I should see the payload for the {string} is as expected")
    public void iShouldSeeThePayloadForTheIsAsExpected(String event) {
        switch (event) {
            case "NOTIFICATION_UPDATE_EVENT":
                outboundCorrespondenceDefinitionStepDefs.get().verifyNotificationUpdateEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "NOTIFICATION_UPDATE_EVENT_PATCH":
                outboundCorrespondenceDefinitionStepDefs.get().verifyNotificationUpdateEventPatch(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "NOTIFICATION_UPDATE_EVENT_VIEWED_PATCH":
                outboundCorrespondenceDefinitionStepDefs.get().verifyNotificationUpdateEventViewedPatch(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get());
                inboundCorrespondenceNoteEventStepDefs.get().verifyInbNoteSaveEvent(response);
                break;
            case "INBOUND_CORRESPONDENCE_UPDATE_EVENT":
                JsonPath response1 = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get());
                inboundCorrespondenceNoteEventStepDefs.get().verifyInbCoresspondenceUpdateEvent(response1);
                break;
            case "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT":
                JsonPath response2 =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get());
                inboundCorrespondenceNoteEventStepDefs.get().verifyInbCoresspondenceTypeUpdateEvent(response2);
                break;
            case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                outboundCorrespondenceDefinitionStepDefs.get().verifyCorrespondenceUpdateEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT_API":
                outboundCorrespondenceDefinitionStepDefs.get().verifyCorrespondenceUpdateEventAPI(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "LINK_EVENT_OB_CONTACTRECORD":
                outboundCorrespondenceDefinitionStepDefs.get().verifyLinksCreatedOBCR(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "LINK_EVENT_CASE_IB_CORRESPONDENCE":
                inboundCorrespondenceNoteEventStepDefs.get().verifyLinksCreatedIBCorrepondence(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()), "CASE");
                break;
            case "LINK_EVENT_CONSUMER_IB_CORRESPONDENCE":
                inboundCorrespondenceNoteEventStepDefs.get().verifyLinksCreatedIBCorrepondence(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()), "CONSUMER");
                break;
            case "LINK_EVENT_TASK_TO_APPLICATION":
                inboundCorrespondenceNoteEventStepDefs.get().verifyLinkEventTaskToApplication(traceId.get());
                break;
            case "LINK_EVENT_TASK_TO_CASES":
                inboundCorrespondenceNoteEventStepDefs.get().verifyLinkEventTaskToCases(traceId.get());
                break;
            case "LINK_EVENT_MISSING_ITEM":
                inboundCorrespondenceNoteEventStepDefs.get().verifyLinkEventInbToMiItem(traceId.get());
                break;
            case "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyCorrespondenceUpdatedEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyCorrespondenceSaveEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "LETTER_DATA_SAVE_EVENT_EXTERNALIDS":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyLetterDataExternalIds(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT_EXTERNALIDS":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyCorrespondenceSaveEventExternalIds(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "CONSUMER_NOTIFICATION_RETURNED":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyConsumerNotificationwCONSUMERIDReturnedEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;
            case "APPLICATION_CONSUMER_NOTIFICATION_RETURNED":
                outboundCorrespondenceDefinitionStepDefs.get().verfifyAPPLICATION_CONSUMERNotificationReturnedEvent(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(traceId.get()));
                break;

            default:
                Assert.fail("no matching events in case");
        }
    }

    @And("I should see that the {string} record is produced to DPBI")
    public void iShouldSeeThatTheRecordIsProducedToDPBI(String event) {
        switch (event) {
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                inboundCorrespondenceNoteEventStepDefs.get().verifyEventSentDPBI(event);
                break;
            case "INBOUND_CORRESPONDENCE_UPDATE_EVENT":
                inboundCorrespondenceNoteEventStepDefs.get().verifyEventSentDPBI(event);
                break;
            case "INBOUND_CORRESPONDENCE_TYPE_UPDATE_EVENT":
                inboundCorrespondenceNoteEventStepDefs.get().verifyEventSentDPBI(event);
                break;
            case "NOTIFICATION_UPDATE_EVENT":
                new OutboundCorrespondenceDefinitionStepDefs().verifyEventSentDPBI(event);
                break;
            case "NOTIFICATION_UPDATE_EVENT_PATCH":
                new OutboundCorrespondenceDefinitionStepDefs().verifyEventSentDPBIPATCH();
                break;
            case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                outboundCorrespondenceDefinitionStepDefs.get().verifyEventSentDPBI(event);
                break;
        }

    }

    @And("I should see that the mailFlag for {string} exists as {string}")
    public void iShouldSeeThatTheMailFlagForExistsAs(String eventName, String mailFlag) {
        switch (eventName.toUpperCase()) {
            case "LETTER_DATA_SAVE_EVENT":
                if (!mailFlag.equalsIgnoreCase("no letter data")) {
                    outboundCorrespondeneStepDefs.get().verifyMailFlagForLetterDataSaveEvent(mailFlag);
                }
                break;
        }
    }

    @Then("I should not see any of the following events that is produced by trace id")
    public void iShouldNotSeeAnyOfTheFollowingEventsThatIsProducedByTraceId(List<String> events) {
        dpbiEventsStepDefs.get().verifyEventNOTPRODUCED(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getEventbyTraceId(String.valueOf(World.generalSave.get().get("updateNidTraceId" + Hooks.nameAndTags.get()))), events);
    }
}
