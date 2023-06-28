package com.maersk.crm.step_definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMManualContactRecordSearchPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.dms.utilities.EventsUtilities;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;

import static com.maersk.crm.api_step_definitions.APIContactRecordController.apicontactRecordId;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CRM_WrapUpTimesStepDef extends CRMUtilities implements ApiBase {

    private String eventURI = ConfigurationReader.getProperty("apiEventsURI");
    private final ThreadLocal<String> eventsCorrelationIdEndPoint = ThreadLocal.withInitial(() -> "/app/crm/event/correlation/");

    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContactPage = new CRMActiveContactPage();
    CRMManualContactRecordSearchPage manualContactRecordSearchPage = new CRMManualContactRecordSearchPage();
    //private APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();

    private final ThreadLocal<String> phoneNum = ThreadLocal.withInitial(() -> "1234567890");
    private final ThreadLocal<String> contactDispositionsValue = ThreadLocal.withInitial(() -> "Complete");
    private final ThreadLocal<String> correlationId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<Integer> wrapUpTime = ThreadLocal.withInitial(() -> 70);
    private final ThreadLocal<String> contactRecordId = ThreadLocal.withInitial(String::new);

    @When("I click End Contact and click {string} after few seconds")
    public void endContactAndCancelOrSave(String clickType) {
        if (clickType.equals("End-Save")) {
            clearAndFillText(contactRecord.firstName, "Ethan");
            clearAndFillText(contactRecord.lastName, "Hunt");
            waitFor(1);
            contactRecord.searchButton.click();
            waitFor(2);

            jsClick(contactRecord.expandButtonforLink);
            waitFor(2);
            jsClick(contactRecord.fullNameRadioButton);
            jsClick(contactRecord.DOBradioButton);
            jsClick(contactRecord.consumerIDcheckButton);
            waitForVisibility(contactRecord.linkRecordButtonOnContactRecordUI, 10);
            contactRecord.linkRecordButtonOnContactRecordUI.click();
            waitFor(2);

            clearAndFillText(activeContactPage.contactPhoneNumber, phoneNum.get());
            waitFor(1);
            selectDropDown(activeContactPage.disposition, contactDispositionsValue.get());
            waitFor(1);
            selectOptionFromMultiSelectDropDown(activeContactPage.programTypes, "Program A");
            waitFor(1);
            selectDropDown(contactRecord.contactReason, "Information Request");
            selectOptionFromMultiSelectDropDown(contactRecord.contactAction, "Provided Case Status/Information");
            waitFor(1);
            contactRecord.saveReasonButton.click();
            waitFor(1);
            contactRecord.endContactButton.click();
            waitFor(wrapUpTime.get());
            contactRecord.saveEditContactRecordBtn.click();
            waitFor(30);
        } else if (clickType.equals("End-Cancel")) {
            contactRecord.endContactButton.click();
            waitFor(wrapUpTime.get());
            contactRecord.cancelButton.click();
            waitForVisibility(contactRecord.cancelWarningContinueButton, 10);
            contactRecord.cancelWarningContinueButton.click();
        } else if (clickType.equals("Cancel")) {
            waitFor(1);
            contactRecord.cancelButton.click();
            waitForVisibility(contactRecord.cancelWarningContinueButton, 10);
            contactRecord.cancelWarningContinueButton.click();
        }
    }

    @When("I {string} End Contact and be inactive for session to time out")
    public void inactiveSession(String clickType) {
        if (clickType.equals("Click")) {
            contactRecord.endContactButton.click();
            waitForVisibility(contactRecord.sessionExpired, 500);
            waitFor(1);
            System.out.println("Session Expired");
        } else if (clickType.equals("DoNotClick")) {
            waitForVisibility(contactRecord.sessionExpired, 500);
            waitFor(1);
            System.out.println("Session Expired");
        }
    }

    @When("I will take {string} from logs of contact for {string}")
    public void i_will_take_trace_Id_for_Events(String idType, String val) {
        String[] valSplit = val.split("-");
        String value = valSplit[0];
        String valueType = valSplit[1];
        if (valueType.equals("save")) {
            correlationId.set(EventsUtilities.getLogs(idType, value).get(1));
        } else {
            correlationId.set(EventsUtilities.getLogs(idType, value).get(0));
        }

        System.out.println("Log gets correlation id:  " + correlationId.get());
        eventsCorrelationIdEndPoint.set(eventsCorrelationIdEndPoint.get() + correlationId.get());
    }

    @Then("I verify {string} in the Contact Record event")
    public void verifyWrauUpTime(String val) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            String[] wrapUpTimeEventSplit = dataObjectData.get("wrapUpTime").getAsString().split(":");
            int wrapUpTimeEvent = Integer.parseInt(wrapUpTimeEventSplit[wrapUpTimeEventSplit.length - 1]);
            int wrapUpTimeEventMin = Integer.parseInt(wrapUpTimeEventSplit[wrapUpTimeEventSplit.length - 2]);
            System.out.println("Wrap Up Time from event: " + wrapUpTimeEvent);
            if (val.equals("wrapUpTime-Zero")) {
                assertEquals(wrapUpTimeEvent, 0);
            } else if (val.equals("wrapUpTime")) {
                int time = (wrapUpTimeEventMin * 60) + wrapUpTimeEvent;
                assertTrue((time - 1) <= wrapUpTime.get());
                assertTrue(wrapUpTime.get() <= (time + 1));
            } else {
                assertTrue(wrapUpTimeEventMin > 3);
            }
        }
    }

    @When("I click End Contact")
    public void iclickEndContact() {
        click(contactRecord.endContactButton);
        waitFor(3);
    }


    @Then("I get and save contact record id from data object")
    public void i_get_and_save_contact_record_id_from_data_object() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(eventURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(eventsCorrelationIdEndPoint.get());

        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        System.out.println("Response for Event: " + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);

        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");

        List eventsContent = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getJsonObject("events");
        assertTrue(eventsContent.size() > 0);

        for (int i = 0; i < eventsContent.size(); i++) {
            HashMap eventsData = (HashMap) eventsContent.get(i);
            System.out.println(eventsData.get("payload"));

            JsonObject payLoadData = new JsonParser().parse(eventsData.get("payload").toString()).getAsJsonObject();
            JsonObject dataObjectData = payLoadData.get("dataObject").getAsJsonObject();

            contactRecordId.set(dataObjectData.get("contactRecordId").getAsString());
            System.out.println("contactRecordId " + contactRecordId.get());

            String[] wrapUpTimeEventSplit = dataObjectData.get("wrapUpTime").getAsString().split(":");
            int wrapUpTimeEvent = Integer.parseInt(wrapUpTimeEventSplit[wrapUpTimeEventSplit.length - 1]);
            int wrapUpTimeEventMin = Integer.parseInt(wrapUpTimeEventSplit[wrapUpTimeEventSplit.length - 2]);

            System.out.println("wrapUpTimeEvent " + wrapUpTimeEvent);
            System.out.println("wrapUpTimeEventMin " + wrapUpTimeEventMin);
        }
    }

    @When("I search for  {string} in Manual Contact Record Search")
    public void i_search_for_in_Manual_Contact_Record_Search(String contactRecordIdExample) {
        if (contactRecordIdExample.equalsIgnoreCase("random")) {
            contactRecordIdExample = contactRecordId.get();
            System.out.println("contactRecordId " + contactRecordIdExample);
            manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(contactRecordIdExample);
            System.out.println("contact record id sent");

        } else {
            System.out.println("contactRecordId " + contactRecordIdExample);
            manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(contactRecordIdExample);
            System.out.println("contact record id sent");

        }

        manualContactRecordSearchPage.searchButton.click();
    }

    @When("I search for newly created Contact Record in Manual Contact Record Search")
    public void i_search_for_newly_created_Contact_Record_in_Manual_Contact_Record_Search() {
        System.out.println("contactRecordId " + apicontactRecordId.get());
        manualContactRecordSearchPage.manualSearchContactRecordId.sendKeys(apicontactRecordId.toString());
        System.out.println("contact record id sent");
        manualContactRecordSearchPage.searchButton.click();
    }
}
