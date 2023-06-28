package com.maersk.crm.api_step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMAddAuthorizedRepresentativePage;
import com.maersk.crm.pages.crm.CRM_CORE_CommunityOutreachPage;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static org.testng.Assert.*;

public class APICoreCommunityOutreachController extends CRMAddAuthorizedRepresentativePage implements ApiBase {

    BrowserUtils browserUtils = new BrowserUtils();

    private String communityOutreachURI = ConfigurationReader.getProperty("apiCommunityOutreachURI");
    private String createCommunityOutreach = "/save";

    private JsonObject communityOutreachSaveRequest = getRequestbodyFromTestData("crm/contactrecords/communityOutreachCreateTwo.json");
    private JsonObject dcebCommunityOutreachSaveRequest = getRequestbodyFromTestData("crm/contactrecords/communityOutreachDCEBCreate.json");
    private final ThreadLocal <String> currentTimeStamp = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> communityOutreachId = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> communityId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal <String> communityOutreachsiteName = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal <String> siteName = ThreadLocal.withInitial(String::new);

    CRM_CORE_CommunityOutreachPage communityOutreach = new CRM_CORE_CommunityOutreachPage();


    public JsonObject getRequestbodyFromTestData(String filePath) {
        API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile(filePath);
        return API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().jsonElement.getAsJsonObject();
    }

    @Given("I initiated Community Outreach Save API")
    public void iInitiatedCommunityOutreachSaveAPI() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(communityOutreachURI);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint(createCommunityOutreach);
    }


    @And("I fill in the following data for {string} in Community Outreach Save API")
    public void iFillInTheFollowingDataForInCommunityOutreachSaveAPI(String type, List<Map<String, String>> data) {

        Map<String, String> keysToOverride = data.get(0);
        String typeKey;
        switch (type) {
            case "Session":
                typeKey = "communityOutreachSession";
                keyFiller(typeKey,keysToOverride);
                break;
            case "Session Schedule":
                typeKey = "communityOutreachSessionSchedule";
                keyFiller(typeKey,keysToOverride);
                break;
            case "Session Site":
                typeKey = "communityOutreachSessionSite";
                keyFiller(typeKey,keysToOverride);
                break;
            case "Site Contact":
                typeKey = "communityOutreachSiteContact";
                keyFiller(typeKey,keysToOverride);
                break;
            case "DCEB Session":
                typeKey = "communityOutreachSession";
                dcebKeyFiller(typeKey,keysToOverride);
                break;
            case "DCEB Session Schedule":
                typeKey = "communityOutreachSessionSchedule";
                dcebKeyFiller(typeKey,keysToOverride);
                break;
            case "DCEB Session Site":
                typeKey = "communityOutreachSessionSite";
                dcebKeyFiller(typeKey,keysToOverride);
                break;
            case "DCEB Site Contact":
                typeKey = "communityOutreachSiteContact";
                dcebKeyFiller(typeKey,keysToOverride);
                break;
        }
    }

    private void keyFiller(String typeKey, Map<String,String> keysToOverride){
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "createdOn":
                    currentTimeStamp.set(browserUtils.getCurrentDateInYearFormat() + "T01:26:33.032Z");
                    communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,currentTimeStamp.get());
                    break;
                case "languages":
                   String[] languageList = keysToOverride.get(key).split(",");
                    for (String each : languageList) {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).getAsJsonArray("languages").add(each);
                    }
                    break;
                case "sessionDate":
                    if ("TOMORROW".equals(keysToOverride.get(key))){
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T05:00:00.000Z");
                    }else {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "sessionStartTime":
                    if ("TOMORROW AT 2PM".equals(keysToOverride.get(key))){
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T14:00:00.000Z");
                    }else {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "sessionEndTime":
                    if ("TOMORROW AT 3PM".equals(keysToOverride.get(key))){
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T15:00:00.000Z");
                    }else {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "siteName":
                    if ("RANDOM SITE".equals(keysToOverride.get(key))){
                        synchronized (communityOutreachsiteName){
                            communityOutreachsiteName.set(RandomStringUtils.randomAlphabetic(7));
                        }

                        siteName.set(communityOutreachsiteName.get());
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key, communityOutreachsiteName.get());
                    }else {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "addressLine1":
                    if ("RANDOM ADDRESS".equals(keysToOverride.get(key))){
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,"123 "+ RandomStringUtils.randomAlphabetic(7));
                    }else {
                        communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                default:
                    communityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
            }
        }
    }

    private void dcebKeyFiller(String typeKey, Map<String,String> keysToOverride){
        for (String key : keysToOverride.keySet()) {
            switch (key) {
                case "createdOn":
                    currentTimeStamp.set(browserUtils.getCurrentDateInYearFormat() + "T01:26:33.032Z");
                    dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,currentTimeStamp.get());
                    break;
                case "languages":
                    String[] languageList = keysToOverride.get(key).split(",");
                    for (String each : languageList) {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).getAsJsonArray("languages").add(each);
                    }
                    break;
                case "sessionDate":
                    if ("TOMORROW".equals(keysToOverride.get(key))){
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T05:00:00.000Z");
                    }else {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "sessionStartTime":
                    if ("TOMORROW AT 2PM".equals(keysToOverride.get(key))){
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T14:00:00.000Z");
                    }else {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "sessionEndTime":
                    if ("TOMORROW AT 3PM".equals(keysToOverride.get(key))){
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,getFutureDateFormatYYYYMMdd(1)+"T15:00:00.000Z");
                    }else {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "siteName":
                    if ("RANDOM SITE".equals(keysToOverride.get(key))){
                        communityOutreachsiteName .set( RandomStringUtils.randomAlphabetic(7));
                        siteName .set(communityOutreachsiteName.get());
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key, communityOutreachsiteName.get());
                    }else {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "addressLine1":
                    if ("RANDOM ADDRESS".equals(keysToOverride.get(key))){
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,"123 "+ RandomStringUtils.randomAlphabetic(7));
                    }else {
                        dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
                    }
                    break;
                case "ward":
                    dcebCommunityOutreachSaveRequest.getAsJsonObject().get(typeKey).getAsJsonObject().get("customAttributes").getAsJsonObject().addProperty(key,keysToOverride.get(key));
                    break;
                default:
                    dcebCommunityOutreachSaveRequest.getAsJsonObject(typeKey).addProperty(key,keysToOverride.get(key));
            }
        }
    }

    @And("I send PUT {string} Community Outreach Save API")
    public void iSendPUTCommunityOutreachSaveAPI(String type) {
        switch (type){
            case "BLCRM":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(communityOutreachSaveRequest);
                break;
            case "DCEB":
                API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PutAPI(dcebCommunityOutreachSaveRequest);
                break;
            default:
                fail("Mismatch in case provided");
        }
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().statusCode, 200);
        assertTrue(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString.contains("success"));
        assertEquals(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("status"), "success");
        communityOutreachId.set(String.valueOf(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("communityOutreachSession[0].communityOutreachId")));
        communityId.set(communityOutreachId.get());
    }
    @Then("I verify the saved session will present the {string} required information in their individual calendar view")
    public void i_verify_the_saved_session_will_present_the_required_information_in_their_individual_calendar_view(String type) {
        System.out.println(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionStatus").toString());
        switch (type){
            case"BLCRM":
                if(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionStatus").getAsString().equals("Cancelled")){
                    assertFalse(browserUtils.isElementDisplayed(communityOutreach.communityOutreachSessionCalendarViewActualData));
                }else{
                    String sessionData = communityOutreach.communityOutreachSessionCalendarViewActualData.getText();
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionType").getAsString()));
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("siteName").getAsString()));
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("addressLine1").getAsString()));
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("notes").getAsString()));
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionStatus").getAsString()));
                    assertTrue(sessionData.contains(communityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").getAsJsonArray("languages").getAsString()));
                }
                break;
            case"DC-EB":
                if(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionStatus").getAsString().equals("Cancelled")){
                    assertFalse(browserUtils.isElementDisplayed(communityOutreach.communityOutreachSessionCalendarViewActualData));
                }else{
                    String sessionData = communityOutreach.communityOutreachSessionCalendarViewActualData.getText();
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionType").getAsString()));
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("siteName").getAsString()));
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("addressLine1").getAsString()));
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSessionSite").get("notes").getAsString()));
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").get("sessionStatus").getAsString()));
                    assertTrue(sessionData.contains(dcebCommunityOutreachSaveRequest.getAsJsonObject("communityOutreachSession").getAsJsonArray("languages").getAsString()));
                }
                break;
            default:
                fail("Mismatch in case provided");
        }
    }
}
