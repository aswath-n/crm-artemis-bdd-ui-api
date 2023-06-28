package com.maersk.dms.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.loggedInUserId;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.loggedInUserName;
import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.generalSave;
import static com.maersk.crm.utilities.World.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OutboundCorrespondenceRequestsStepDefs extends CRMUtilities implements ApiBase {

    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<JsonObject> sendEventRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<ApiTestDataUtil> sendEventTdu = ThreadLocal.withInitial(ApiTestDataUtil::getApiTestDataUtilThreadLocal);
    private final ThreadLocal<APIClassUtil> getCId = ThreadLocal.withInitial(APIClassUtil::getApiClassUtilThreadLocal);
    private ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private Actions action = new Actions(Driver.getDriver());
    private final ThreadLocal<ZonedDateTime> nowUTC = ThreadLocal.withInitial(()->ZonedDateTime.now(ZoneOffset.UTC));
    private final ThreadLocal<LocalDateTime> localDateTime = ThreadLocal.withInitial(()->LocalDateTime.ofInstant(nowUTC.get().toInstant(), ZoneOffset.UTC));
    private final ThreadLocal<String> expectedDateAndHour =ThreadLocal.withInitial(()-> localDateTime.get().toString().substring(0, 13));
    private final ThreadLocal<JsonObject> requestParams= ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> cId = ThreadLocal.withInitial(String::new);
    private OutboundCorrespondenceSearchPage searchPage = new OutboundCorrespondenceSearchPage();
    private CRMContactRecordUIPage init = new CRMContactRecordUIPage();
    private APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<List<String>> nIdList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outcorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    CorrespondenceDetailsPage correspondenceDetailsPage = new CorrespondenceDetailsPage();
    private static final ThreadLocal<String> expectedUserName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<Response> response = new ThreadLocal<>();
    private static final ThreadLocal<String> correspondenceStatus = ThreadLocal.withInitial(String::new);

    public void sendStatusupdate(String cId, String status) {
        sendEventRequest.set( sendEventTdu.get().getJsonFromFile("dms/outboundCorrespondenceStatusUpdate.json").jsonElement.getAsJsonObject());
        sendEventRequest.get().addProperty("status", status);
        //String baseUri = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/correspondences/";
        String baseUri = ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences/";
        String endPoint = cId + "/statuses";
        String url = baseUri + endPoint;
        System.out.println("url = " + url);
        Response response = apiAutoUtilities.get().postAPI(url, sendEventRequest.get());
        Assert.assertEquals(response.statusCode(), 200);
    }

    public void clickOnCId(String cId) {
        if (cId.equalsIgnoreCase("previouslyCreated")) {
            cId = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        }
        viewOutboundCorrespondenceDetailsPage.getOutboundCorrCID(cId).click();
        waitFor(2);
        String actualCIdValue = viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText();
        Assert.assertEquals(actualCIdValue, cId);
    }

    public void selectOnStatusDropdown() {
        viewOutboundCorrespondenceDetailsPage.statusDropdown.click();
    }

    public void verifyStatusDropdownValue(String dropdownValue) {
        String actualDropdownValue = "";
        if ("On Hold".equals(dropdownValue)) {
            actualDropdownValue = viewOutboundCorrespondenceDetailsPage.statusDropdownOnHold.getText();
        } else if ("Requested".equals(dropdownValue)) {
            actualDropdownValue = viewOutboundCorrespondenceDetailsPage.statusDropdownRequested.getText();
        } else if ("Sent".equals(dropdownValue)) {
            actualDropdownValue = viewOutboundCorrespondenceDetailsPage.statusDropdownSent.getText();
        } else if ("Canceled".equals(dropdownValue)) {
            actualDropdownValue = viewOutboundCorrespondenceDetailsPage.statusDropdownCanceled.getText();
        } else {
            Assert.fail(dropdownValue + " is not an status dropdown value");
        }
        Assert.assertEquals(actualDropdownValue, dropdownValue, "Actual dropdown value: " + actualDropdownValue + " Expected Value: " + dropdownValue);
    }

    public void selectOnStatusReasonDropdown() {
        viewOutboundCorrespondenceDetailsPage.statusReasonDropdown.click();
    }

    public void verifyStatusReasonDropdownValuesForCanceledStatus() {
        String expectedFirst = viewOutboundCorrespondenceDetailsPage.statusReasonDropdownNoLongerAppropriate.getText();
        String expectedSecond = viewOutboundCorrespondenceDetailsPage.statusReasonDropdownRequestedInError.getText();
        String expectedThird = viewOutboundCorrespondenceDetailsPage.statusReasonDropdownUnresolvableError.getText();
        Assert.assertEquals(expectedFirst, "No longer appropriate");
        Assert.assertEquals(expectedSecond, "Requested in error");
        Assert.assertEquals(expectedThird, "Unresolvable error");
    }

    public void clickOnSaveEditButton() {
        waitFor(1);
        viewOutboundCorrespondenceDetailsPage.saveOutboundButton.click();
        waitFor(8);
    }

    public void verifyStatusRequiredErrorMsg() {
        String actualErrorMsg = "";
        String expectedErrorMsg = "";
        if (correspondenceStatus.get().equalsIgnoreCase("Canceled")) {
            expectedErrorMsg = "Status Reason is required when Status is Canceled";
            actualErrorMsg = Driver.getDriver().findElement(By.xpath("//p[.='" + expectedErrorMsg + "']")).getText();
        } else {
            expectedErrorMsg = "Status Reason is required and cannot be left blank";
            actualErrorMsg = viewOutboundCorrespondenceDetailsPage.statusReasonErrorMsg.getText();
        }
        String assertmsg = "Actual error msg: " + actualErrorMsg + " Expected error msg: " + expectedErrorMsg;
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, assertmsg);
    }

    public void clickOnEditButton() {
        viewOutboundCorrespondenceDetailsPage.editButton.click();
        waitFor(3);
    }

    public void clickOnStatusValue(String statusDropdownValue) {

        if ("On Hold".equals(statusDropdownValue)) {
            viewOutboundCorrespondenceDetailsPage.statusDropdownOnHold.click();
        } else if ("Requested".equals(statusDropdownValue)) {
            viewOutboundCorrespondenceDetailsPage.statusDropdownRequested.click();
        } else if ("Sent".equals(statusDropdownValue)) {
            viewOutboundCorrespondenceDetailsPage.statusDropdownSent.click();
        } else if ("Canceled".equals(statusDropdownValue)) {
            viewOutboundCorrespondenceDetailsPage.statusDropdownCanceled.click();
        } else {
            Assert.fail(statusDropdownValue + " is not an status dropdown value");
        }
        waitFor(2);
    }

    public void clickOnStatusReasonValue(String reasonValue) {
        if ("No longer appropriate".equals(reasonValue)) {
            viewOutboundCorrespondenceDetailsPage.statusReasonDropdownNoLongerAppropriate.click();
        } else if ("Requested in error".equals(reasonValue)) {
            viewOutboundCorrespondenceDetailsPage.statusReasonDropdownRequestedInError.click();
        } else if ("Unresolvable error".equals(reasonValue)) {
            viewOutboundCorrespondenceDetailsPage.statusReasonDropdownUnresolvableError.click();
        } else {
            Assert.fail(reasonValue + " is not an status reason dropdown value");
        }
    }

    public void verifyCancelWarningMsg() {
        String expectedCancelingmsg = "Are you sure, you wish to cancel this correspondence?";
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.cancelingWarningMsg.getText(), expectedCancelingmsg, "No cancel waring message displayed");
    }

    public void getOutCorrWithCId(String cId) {

        getCId.get().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences/");
        // getCId.get().setbaseUri("https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/correspondences/");
        getCId.get().setEndPoint(cId);
        getCId.get().getAPI();
    }

    public void verifyGetExpectedValues(Map<String, String> datatable) {
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toLowerCase()) {
                case "status":
                    String actualStatus = getCId.get().jsonPathEvaluator.get("status").toString();
                    Assert.assertEquals(actualStatus, datatable.get("Status"));
                    break;
                case "consumerid":
                    String actualConsumerId = getCId.get().jsonPathEvaluator.get("recipients[0].recipientInfo.consumerId").toString();
                    if(datatable.get("consumerId").equalsIgnoreCase("previouslyCreated")){
                        datatable.replace("consumerId",generalSave.get().get("caseConsumerId").toString());
                    }
                    Assert.assertEquals(actualConsumerId, datatable.get("consumerId"));
                    break;
                case "status reason":
                    String actualStatusMsg = getCId.get().jsonPathEvaluator.get("statusMessage").toString();
                    Assert.assertEquals(actualStatusMsg, datatable.get("Status Reason"));
                    break;
                case "updated by":
                    String actualupdatedBy = getCId.get().jsonPathEvaluator.get("updatedBy").toString();
                    if (datatable.get("Updated By").equalsIgnoreCase("UI User")) {
                        Assert.assertEquals(actualupdatedBy, loggedInUserId);
                    } else if (datatable.get("Updated By").equalsIgnoreCase(datatable.get("Updated By"))) {
                        Assert.assertEquals(actualupdatedBy, datatable.get("Updated By"));
                    } else {
                        Assert.assertEquals(actualupdatedBy, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                    }
                    break;
                case "created by":
                    String actualcreatedBy = getCId.get().jsonPathEvaluator.get("createdBy").toString();
                    if (datatable.get("Created By").equalsIgnoreCase("UI User")) {
                        Assert.assertEquals(actualcreatedBy, loggedInUserId);
                    } else {
                        Assert.assertEquals(actualcreatedBy, datatable.get("Created By"));
                    }
                    break;
                case "requester id":
                    String actualrequesterId = getCId.get().jsonPathEvaluator.get("requester.requesterId").toString();
                    Assert.assertEquals(actualrequesterId, datatable.get("Requester Id"));
                    break;
                case "updated date":
                    String actualUpdatedTime = getCId.get().jsonPathEvaluator.get("updatedDatetime").toString().substring(0, 13);
                    if (!datatable.get("Updated Date").equalsIgnoreCase("current"))
                        Assert.assertEquals(actualUpdatedTime, datatable.get("Updated Date").substring(0, 13));
                    else
                        Assert.assertEquals(actualUpdatedTime, expectedDateAndHour.get());
                    break;
                case "created date":
                    String actualCreatedTime = getCId.get().jsonPathEvaluator.get("createdDatetime").toString().substring(0, 13);
                    if (!datatable.get("Created Date").equalsIgnoreCase("current"))
                        Assert.assertEquals(actualCreatedTime, datatable.get("Created Date").substring(0, 13));
                    else
                        Assert.assertEquals(actualCreatedTime, expectedDateAndHour.get());
                    break;
                case "status date":
                    String actualStatusTime = getCId.get().jsonPathEvaluator.get("statusDatetime").toString().substring(0, 13);
                    if (!datatable.get("Status Date").equalsIgnoreCase("current"))
                        Assert.assertEquals(actualStatusTime, datatable.get("Status Date").substring(0, 13));
                    else
                        Assert.assertEquals(actualStatusTime, expectedDateAndHour.get());
                    break;
                case "responsedue date":
                    String actualresponsedate = getCId.get().jsonPathEvaluator.get("responseDueDate").toString().substring(0, 13);
                    if (!datatable.get("ResponseDue Date").equalsIgnoreCase("current"))
                        Assert.assertEquals(actualresponsedate, datatable.get("ResponseDue Date").substring(0, 13));
                    else
                        Assert.assertEquals(actualresponsedate, expectedDateAndHour.get());
                    break;
                case "language":
                    String actualLanguage = getCId.get().jsonPathEvaluator.get("language").toString();
                    Assert.assertEquals(actualLanguage, datatable.get("Language"));
                    break;
                case "correspondencedefinitionmmscode":
                    String actualmmscode = getCId.get().jsonPathEvaluator.get("correspondenceDefinitionMMSCode").toString();
                    Assert.assertEquals(actualmmscode, datatable.get("CorrespondenceDefinitionMMSCode"));
                    break;
                case "middlename":
                    String actualmiddlename = getCId.get().jsonPathEvaluator.get("recipients[0].recipientInfo.middleName");
                    if (datatable.get("MiddleName").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualmiddlename == null);
                    else
                        Assert.assertEquals(actualmiddlename.toString(), datatable.get("MiddleName"));
                    break;
                case "namesuffix":
                    String actualnamesuffix = getCId.get().jsonPathEvaluator.get("recipients[0].recipientInfo.nameSuffix");
                    if (datatable.get("NameSuffix").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualnamesuffix == null);
                    else
                        Assert.assertEquals(actualnamesuffix.toString(), datatable.get("NameSuffix"));
                    break;
                default:
                    Assert.assertEquals(getCId.get().jsonPathEvaluator.get(eachVerifyValue).toString(), datatable.get(eachVerifyValue));
                    try {
                    } catch (NullPointerException e) {
                        Assert.fail("Search criteria does not match " + eachVerifyValue);
                    }
            }
        }
    }

    public void clickYesOnCancelConfirmMsg() {
        waitFor(2);
        viewOutboundCorrespondenceDetailsPage.warningMsgCheckYesbttn.click();
        waitFor(5);
    }

    public void clickNADandSelect(String nadValue) {
        waitFor(1);
        viewOutboundCorrespondenceDetailsPage.notificationActionDropdown.click();
        waitFor(1);
        if ("RETURNED".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownReturned.click();
        } else if ("SENT".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownSent.click();
        } else if ("HOLD".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownHold.click();
        } else if ("RETRY".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownRetry.click();
        } else if ("RESUME".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownResume.click();
        } else if ("CANCEL".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownCancel.click();
        } else if ("RESEND".equals(nadValue.toUpperCase())) {
            viewOutboundCorrespondenceDetailsPage.notificationDropdownResend.click();
        } else {
            Assert.fail("Entered value does not match the value in the Notification Action dropdown");
        }
    }

    public void verifyNotificationStatusValue(String notificationStatusValue) {
        String actualStatusValue = viewOutboundCorrespondenceDetailsPage.notificationStatusValue.getText();
        Assert.assertTrue(actualStatusValue.equals(notificationStatusValue), "Actual Notification status: " + actualStatusValue + " Expected Notification Status " + notificationStatusValue);
    }

    public void accessToReasonAndReturnDateDropdown() {
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.notificationReasonDropdown.isEnabled(), "Reasons dropdown is not available");
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.notificationReturnDateDropdown.isEnabled(), "Returned date dropdown is not available");

    }

    public void selectOnNotificationReasonDropdown() {
        viewOutboundCorrespondenceDetailsPage.notificationReasonDropdown.click();
        waitFor(3);
        List<String> actualValues = new ArrayList<>();
        for (WebElement each : viewOutboundCorrespondenceDetailsPage.notificationReasonsValues) {
            actualValues.add(each.getText());
        }
        System.out.println("actualValues = " + actualValues);
    }

    public void VerifyReturnedNotiReasonsValues(List<String> reasonValues) {
        List<String> actualValues = new ArrayList<>();
        List<String> expectedlValues = new ArrayList<>();
        for (WebElement each : viewOutboundCorrespondenceDetailsPage.notificationReasonsValues) {
            actualValues.add(each.getText());
        }
        for (String each : reasonValues) {
            expectedlValues.add(each);
        }
        Collections.sort(actualValues);
        Collections.sort(expectedlValues);
        Assert.assertEquals(actualValues, expectedlValues);
    }

    public void checkReasonsBlankErrorMsg(String errorMsg) {
        String actualMsg = viewOutboundCorrespondenceDetailsPage.notificationReasonBlankErrorMsg.getText();
        Assert.assertEquals(actualMsg, errorMsg, "Actual Error messgae: " + actualMsg + " Expected Error message: " + errorMsg);
    }

    public void checkReturnDatesBlankErrorMsg(String errorMsg) {
        String actualMsg = viewOutboundCorrespondenceDetailsPage.notificationReturnDateBlankErrorMsg.getText();
        Assert.assertEquals(actualMsg, errorMsg, "Actual Error messgae: " + actualMsg + " Expected Error message: " + errorMsg);
    }

    public void selectReturnedReasons(String reasons) {
        viewOutboundCorrespondenceDetailsPage.notificationReasonDropdown.click();
        waitFor(1);
        switch (reasons) {
            case "Undeliverable":
                viewOutboundCorrespondenceDetailsPage.notiValUndeliverable.click();
                break;
            case "Refused":
                viewOutboundCorrespondenceDetailsPage.notiValRefused.click();
                break;
            case "Mailbox full":
                viewOutboundCorrespondenceDetailsPage.notiValMailbox.click();
                break;
            case "Destination agent unresponsive":
                viewOutboundCorrespondenceDetailsPage.notiValagentUnresponsive.click();
                break;
            case "Destination agent rejected message":
                viewOutboundCorrespondenceDetailsPage.notiValrejectedMessage.click();
                break;
            case "Destination invalid":
                viewOutboundCorrespondenceDetailsPage.notiValInvalid.click();
                break;
            default:
                Assert.fail("The value does not match a reason in the Returned Reason");
        }
        waitFor(2);
    }

    public void selectReturnedReturnDate(String date) {
        if ("today's date".equals(date)) {
            LocalDate datePriorNDays = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String today = datePriorNDays.format(formatter).split("/")[1];
            if (today.charAt(0) == '0') {
                today = today.substring(1);
            }
            viewOutboundCorrespondenceDetailsPage.calendar.click();
            waitFor(4);
            action.moveToElement(Driver.getDriver().findElement(By.xpath("//button/span/p[.='" + today + "']"))).click().build().perform();
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("//button/span[.='OK']")).click();
            waitFor(1);
        } else {
            viewOutboundCorrespondenceDetailsPage.notificationReturnDateDropdown.sendKeys(date);
        }
    }

    public void verifyGetNotification(Map<String, String> datatable) {
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toLowerCase()) {
                case "status":
                    String actualStatus = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.status").toString();
                    Assert.assertEquals(actualStatus, datatable.get("Status"));
                    break;
                case "status reason":
                    String actualStatusReason = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.statusReason").toString();
                    Assert.assertEquals(actualStatusReason, datatable.get("Status Reason"));
                    break;
                case "updated by":
                    // String actualupdatedBy = getCId.get().jsonPathEvaluator.get("updatedBy").toString();
                    String actualupdatedBy = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].updatedBy").toString();
                    if (datatable.get("Updated By").equalsIgnoreCase("ECMS Service"))
                        Assert.assertEquals(actualupdatedBy,API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service"));
                    else if (datatable.get("Updated By").equalsIgnoreCase("UI User")) {
                        Assert.assertEquals(actualupdatedBy, loggedInUserId.get());
                    } else if (datatable.get("Updated By").matches("\\d+")) {
                        Assert.assertEquals(actualupdatedBy, datatable.get("Updated By"));
                    } else
                        Assert.assertEquals(actualupdatedBy, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                    break;
                case "changedby":
                    String actualchangedBy = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.changedBy").toString();
                    if (datatable.get("changedBy").equalsIgnoreCase("ECMS Service"))
                        Assert.assertEquals(actualchangedBy,API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service"));
                    else
                        Assert.assertEquals(actualchangedBy, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                    break;
                case "updated date":
                    //String actualUpdatedTime = getCId.get().jsonPathEvaluator.get("updatedDatetime").toString().substring(0, 13);
                    String actualUpdatedTime = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].updatedDatetime").toString().substring(0, 13);
                    Assert.assertEquals(actualUpdatedTime, expectedDateAndHour.get());
                    break;
                case "statuschangeddate":
                    String actualstatusChangedDate = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.statusChangedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualstatusChangedDate, expectedDateAndHour.get());
                    break;
                case "createddatetime":
                    if(!datatable.get(eachVerifyValue).equalsIgnoreCase("current date and hour")){
                        expectedDateAndHour.set(datatable.get(eachVerifyValue));
                    }
                    String actualcreatedDatetime = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].createdDatetime").toString().substring(0, 13);
                    Assert.assertEquals(actualcreatedDatetime, expectedDateAndHour.get());
                    break;
                case "returned date":
                    String actualreturnedTime = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].returnedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualreturnedTime, expectedDateAndHour.get());
                    break;
                case "sent date":
                    String actualsentdateTime = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.statusChangedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualsentdateTime, expectedDateAndHour.get());
                    break;
                case "errordetail":
                    String actualerrordetail = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.errorDetail").toString();
                    Assert.assertEquals(actualerrordetail, datatable.get("ErrorDetail"));
                    break;
                case "objectreceivedon":
                    String actualobjectreceivedon = "";
                    if (datatable.get("objectReceivedOn").equalsIgnoreCase("updatedFromAssembly")) {
                        JsonPath response = (JsonPath) World.generalSave.get().get("sendNowResponse");
                        actualobjectreceivedon = response.get("recipients[0].notifications[0].objectReceivedOn");
                    } else {
                        actualobjectreceivedon = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].objectReceivedOn");
                    }
                    if (datatable.get("objectReceivedOn").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualobjectreceivedon == null);
                    else
                        Assert.assertEquals(actualobjectreceivedon.substring(0, 13), expectedDateAndHour.get().replace("T", " "));
                    break;
                case "objectparentfileid":
                    String actualobjectparentfileid = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].objectParentFileId");
                    if (datatable.get("objectParentFileId").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualobjectparentfileid == null);
                    else if (datatable.get("objectParentFileId").equalsIgnoreCase("previouslyCreated"))
                        Assert.assertEquals(actualobjectparentfileid, CorrespondenceETLStepDefs.fileName.get());
                    break;
                default:
                    try {
                        if (datatable.get(eachVerifyValue).equalsIgnoreCase("null")) {
                            Assert.assertNull(getCId.get().jsonPathEvaluator.get(eachVerifyValue));
                        } else {
                            Assert.assertEquals(getCId.get().jsonPathEvaluator.get(eachVerifyValue).toString(), datatable.get(eachVerifyValue));
                        }
                    } catch (NullPointerException e) {
                        Assert.fail("Search criteria does not match " + eachVerifyValue);
                    }
            }
        }
    }

    public void verifyGetNotificationForSecondRecipient(Map<String, String> datatable) {
        for (String eachVerifyValue : datatable.keySet()) {
            switch (eachVerifyValue.toLowerCase()) {
                case "status":
                    String actualStatus = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.status").toString();
                    Assert.assertEquals(actualStatus, datatable.get("Status"));
                    break;
                case "status reason":
                    String actualStatusReason = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.statusReason").toString();
                    Assert.assertEquals(actualStatusReason, datatable.get("Status Reason"));
                    break;
                case "updated by":
                    // String actualupdatedBy = getCId.get().jsonPathEvaluator.get("updatedBy").toString();
                    String actualupdatedBy = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].updatedBy").toString();
                    if (datatable.get("Updated By").equalsIgnoreCase("ECMS Service"))
                        Assert.assertEquals(actualupdatedBy,API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service"));
                    else if (datatable.get("Updated By").equalsIgnoreCase("UI User")) {
                        Assert.assertEquals(actualupdatedBy, loggedInUserId);
                    } else if (datatable.get("Updated By").matches("\\d+")) {
                        Assert.assertEquals(actualupdatedBy, datatable.get("Updated By"));
                    } else
                        Assert.assertEquals(actualupdatedBy, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                    break;
                case "changedby":
                    String actualchangedBy = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.changedBy").toString();
                    if (datatable.get("changedBy").equalsIgnoreCase("ECMS Service"))
                        Assert.assertEquals(actualchangedBy,API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service"));
                    else
                        Assert.assertEquals(actualchangedBy, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId);
                    break;
                case "updated date":
                    //String actualUpdatedTime = getCId.get().jsonPathEvaluator.get("updatedDatetime").toString().substring(0, 13);
                    String actualUpdatedTime = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].updatedDatetime").toString().substring(0, 13);
                    Assert.assertEquals(actualUpdatedTime, expectedDateAndHour.get());
                    break;
                case "statuschangeddate":
                    String actualstatusChangedDate = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.statusChangedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualstatusChangedDate, expectedDateAndHour.get());
                    break;
                case "returned date":
                    String actualreturnedTime = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].returnedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualreturnedTime, expectedDateAndHour.get());
                    break;
                case "sent date":
                    String actualsentdateTime = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.statusChangedDate").toString().substring(0, 13);
                    Assert.assertEquals(actualsentdateTime, expectedDateAndHour.get());
                    break;
                case "errordetail":
                    String actualerrordetail = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].notificationStatus.errorDetail").toString();
                    Assert.assertEquals(actualerrordetail, datatable.get("ErrorDetail"));
                    break;
                case "objectreceivedon":
                    String actualobjectreceivedon = "";
                    if (datatable.get("objectReceivedOn").equalsIgnoreCase("updatedFromAssembly")) {
                        JsonPath response = (JsonPath) World.generalSave.get().get("sendNowResponse");
                        actualobjectreceivedon = response.get("recipients[1].notifications[0].objectReceivedOn");
                    } else {
                        actualobjectreceivedon = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].objectReceivedOn");
                    }
                    if (datatable.get("objectReceivedOn").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualobjectreceivedon == null);
                    else
                        Assert.assertEquals(actualobjectreceivedon.substring(0, 13), expectedDateAndHour.get().replace("T", " "));
                    break;
                case "objectparentfileid":
                    String actualobjectparentfileid = getCId.get().jsonPathEvaluator.get("recipients[1].notifications[0].objectParentFileId");
                    if (datatable.get("objectParentFileId").equalsIgnoreCase("null"))
                        Assert.assertTrue(actualobjectparentfileid == null);
                    else if (datatable.get("objectParentFileId").equalsIgnoreCase("previouslyCreated"))
                        Assert.assertEquals(actualobjectparentfileid, CorrespondenceETLStepDefs.fileName.get());
                    break;
                default:
                    Assert.fail("Search criteria does not match");
            }
        }
    }

    public void verifyCorrespondenceStatus(String corrStatus) {
        waitFor(15);
        String actualCorrStatus = viewOutboundCorrespondenceDetailsPage.correspondenceStatusValue.getText();
        Assert.assertEquals(actualCorrStatus, corrStatus, "Actual Correspondence Status: " + actualCorrStatus + " Expected Status: " + corrStatus);
    }

    public void verifyCorrespondenceLanguage(String corrlang) {
        waitFor(15);
        String actualCorrLanguage = viewOutboundCorrespondenceDetailsPage.correspondenceLanguageValue.getText();
        Assert.assertEquals(actualCorrLanguage, corrlang, "Actual Correspondence Language: " + actualCorrLanguage + " Expected Status: " + corrlang);
    }

    public void clickOnNotificationCancel() {
        viewOutboundCorrespondenceDetailsPage.firstNotificationCancelBttn.click();
        waitFor(1);
    }

    public void verifyNavAwayMsg(String warning) {
        waitForVisibility(viewOutboundCorrespondenceDetailsPage.navigateAwayWarningMsg,10);
        String actualwarning = viewOutboundCorrespondenceDetailsPage.navigateAwayWarningMsg.getText();
        Assert.assertEquals(actualwarning, warning, "Actual warning message: " + actualwarning);
    }

    public void clickOnBackArrowBtn() {
        waitFor(1);
        viewOutboundCorrespondenceDetailsPage.outboundDetailsBackArrow.click();
        waitFor(2);
    }

    public void verifyReturnedNotContained() {
        scrollToElement(viewOutboundCorrespondenceDetailsPage.notificationActionDropdown);
        viewOutboundCorrespondenceDetailsPage.notificationActionDropdown.click();
        List<String> dropdownValues = new ArrayList<>();
        for (WebElement each : viewOutboundCorrespondenceDetailsPage.notiActionDropValues) {
            dropdownValues.add(each.getText());
        }
        if (dropdownValues.contains("RETURNED")) {
            Assert.fail("Notification Actions dropdown contains Returned option");
        }
    }

    public void verifyReturnedContained() {
        scrollToElement(viewOutboundCorrespondenceDetailsPage.notificationActionDropdown);
        viewOutboundCorrespondenceDetailsPage.notificationActionDropdown.click();
        List<String> dropdownValues = new ArrayList<>();
        for (WebElement each : viewOutboundCorrespondenceDetailsPage.notiActionDropValues) {
            dropdownValues.add(each.getText());
        }
        if (!dropdownValues.contains("RETURNED")) {
            Assert.fail("Notification Actions dropdown doesn't contain Returned option");
        }
    }

    public void clickOnStatusHistorBtn() {
        waitFor(1);
        viewOutboundCorrespondenceDetailsPage.statusHistory.click();
        waitFor(2);
    }

    public void verifyReturnedIsVisibleInHistory() {
        List<String> historyStatusList = new ArrayList<>();
        for (WebElement each : viewOutboundCorrespondenceDetailsPage.statusHistoryStatus) {
            scrollToElement(each);
            historyStatusList.add(each.getText());
        }
        int last = historyStatusList.size() - 1;
        String lastOnList = historyStatusList.get(last);
        Assert.assertEquals(lastOnList, "Returned", "Returned is not shown in Status History. Actual shown status: " + lastOnList);
    }

    public void postOutboundCorrWithFour() {
        requestParams.set(sendEventTdu.get().getJsonFromFile("dms/correspondenceWithFourRecipient.json").jsonElement.getAsJsonObject());
        String base = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/";
        String end = "correspondences";
        apiAutoUtilities.get().postOutboundCorrWithOneRecipient(base + end, requestParams.get().toString());
        waitFor(2);
        cId.set(apiAutoUtilities.get().jsonPathEvaluator.get("data.id").toString());
    }

    public void searchWithcreatedCId() {
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        searchPage.correspondenceId.click();
        waitFor(1);
        searchPage.correspondenceId.sendKeys(cId.get());
        waitFor(2);
        action.moveToElement(init.initContact).build().perform();
        waitForClickablility(searchPage.outboundSearchButton, 2);
        searchPage.outboundSearchButton.click();
        waitFor(1);
        Assert.assertTrue(searchPage.outboundSearchButton.isEnabled());
        searchPage.outboundSearchButton.click();
    }

    public void clickOnCidResult() {
        searchPage.firstCIdFromSearch.click();
        waitFor(2);
    }

    public void verifyCanceledEventPublished() {
        waitFor(8);
        apiClassUtil.setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com/app/crm/");
        apiClassUtil.setEndPoint("events?size=10&page=0&sort=eventId,desc");
        String eventName = "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventName);
        apiClassUtil.PostAPIWithParameter(jsonObject);
        String adjusted = "";
        for (int i = 0; i < 9; i++) {
            adjusted += apiClassUtil.jsonPathEvaluator.get("eventsList.content[ " + i + "].payload").toString();
        }
        Assert.assertTrue(adjusted.contains(cId.get()), " Created Correspondenc Id: " + cId.get() + " not found in events");
    }

    public void getNotificationID() {
        getOutCorrWithCId(cId.get());
        for (int i = 0; i < 3; i++) {
            nIdList.get().add(getCId.get().jsonPathEvaluator.get("recipients[0].notifications[" + i + "].notificationId").toString());
        }
    }

    public void updatedNotiStatuswithNId(List<String> notificationStatus) {
        List<String> nStatusList = new ArrayList<>(notificationStatus);
        for (int i = 0; i < nStatusList.size() - 1; i++) {
           API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nIdList.get().get(i), nStatusList.get(i));
            waitFor(2);
        }
    }

    public void verifyAllNotificationStatus(List<String> expectedStatusList) {
        waitFor(2);
        List<String> actualNStatusList = new ArrayList<>();
        actualNStatusList.add(viewOutboundCorrespondenceDetailsPage.firstNotificationStatusValue.getText());
        actualNStatusList.add(viewOutboundCorrespondenceDetailsPage.secondNotificationStatusValue.getText());
        waitFor(1);
        scrollDownUsingActions(3);
        waitFor(1);
        actualNStatusList.add(viewOutboundCorrespondenceDetailsPage.thirdNotificationStatusValue.getText());
        actualNStatusList.add(viewOutboundCorrespondenceDetailsPage.fourthNotificationStatusValue.getText());
        List<WebElement> nStatusElementList = new ArrayList<>();
        nStatusElementList.add(viewOutboundCorrespondenceDetailsPage.firstNotificationStatusValue);
        nStatusElementList.add(viewOutboundCorrespondenceDetailsPage.secondNotificationStatusValue);
        nStatusElementList.add(viewOutboundCorrespondenceDetailsPage.thirdNotificationStatusValue);
        nStatusElementList.add(viewOutboundCorrespondenceDetailsPage.fourthNotificationStatusValue);
        for (int i = 0; i < expectedStatusList.size() - 1; i++) {
            hover(nStatusElementList.get(i));
            waitFor(1);
            Assert.assertEquals(expectedStatusList.get(i), actualNStatusList.get(i), "Expected status: " + expectedStatusList.get(i) + " Actual status: " + actualNStatusList.get(i));
        }
    }

    public void changeCreatedCorrespondenceStatus(String status) {
        sendEventRequest.set(sendEventTdu.get().getJsonFromFile("dms/outboundCorrespondenceStatusUpdate.json").jsonElement.getAsJsonObject());
        sendEventRequest.get().addProperty("status", status);
        //String baseUri = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-correspondence-order-microservices-api/correspondences/";
        String baseUri = ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences/";
        String endPoint = cId.get() + "/statuses";
        String url = baseUri + endPoint;
        System.out.println("url = " + url);
        Response response = apiAutoUtilities.get().postAPI(url, sendEventRequest.get());
        Assert.assertEquals(response.statusCode(), 200);
    }

    public void getCreatedOutCorr() {
        getOutCorrWithCId(cId.get());
    }

    public void checkNotiStatusUnchanged() {
        String actualNStatus = getCId.get().jsonPathEvaluator.get("recipients[0].notifications[0].notificationStatus.status").toString();
        Assert.assertEquals(actualNStatus, "Precluded", "Actual Notification Status: " + actualNStatus);
    }

    public void postNotificationStatusToPrecluded() {
       API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(nIdList.get().get(0), "Precluded");
    }

    public void verifyOutboundLinkedToCase(String cid, String caseId) {
        boolean linkFound = false;
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Case")) {
                Assert.assertEquals(link.get("internalId").toString().trim(), cid);
                Assert.assertEquals(link.get("id").toString().trim(), caseId);
                linkFound = true;
            }
        }
        Assert.assertTrue(linkFound, "Link is not found in OB links for Case");
    }

    public void verifyEntityLinkedToOutbound(String entityType, String cid, String entityId) {
        boolean linkFound = false;
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase(entityType)) {
                Assert.assertEquals(link.get("internalId").toString().trim(), cid);
                Assert.assertEquals(link.get("id").toString().trim(), entityId);
                Assert.assertEquals(link.get("effectiveStartDate").toString().substring(0, 13), expectedDateAndHour.get(), "Effective startData mismatched for Link");
                linkFound = true;
            }
        }
        Assert.assertTrue(linkFound, "Link is not found in OB links for " + entityType);
    }

    public void verifyEntityNotLinkedToOutbound(String entityType, String cid, String entityId) {
        boolean linkFound = false;
        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase(entityType)) {
                Assert.assertEquals(link.get("internalId").toString().trim(), cid);
                Assert.assertEquals(link.get("id").toString().trim(), entityId);
                linkFound = true;
            }
        }
        Assert.assertFalse(linkFound, "Link is found in OB links for " + entityType);
    }

    public void verifyOutboundLinkedToEntity(String entityType, String cid, String entityId) {
        JsonPath eventsResponse = null;
        switch (entityType.toUpperCase()) {
            case "CASE":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCaseLinks(entityId);
                break;
            case "CONSUMER PROFILE":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getConsumerLinks(entityId);
                break;
            case "TASK":
            case "SERVICE REQUEST":
                eventsResponse = apiAutoUtilities.get().getTaskByTaskId(entityId);
                break;
            case "INBOUND CORRESPONDENCE":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(entityId);
                break;
            case "CONTACT RECORD":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getContactRecordLinks(entityId);
                break;
            case "OUTBOUND CORRESPONDENCE":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(entityId);
                break;
            case "APPLICATION":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getApplicationLinks(entityId);
                break;
            case "MISSING INFORMATION":
                eventsResponse =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getMissingItemLinks(entityId);
                break;
            default:
                Assert.fail("No matching case for " + entityType);
        }
        boolean linkFound = false;
        if (entityType.equalsIgnoreCase("TASK") || entityType.equalsIgnoreCase("SERVICE REQUEST")) {
            List<Map<String, Object>> links = eventsResponse.getList("tasks[0].taskLinksVOS");
            for (Map<String, Object> link : links) {
                if (link.get("externalLinkRefType").toString().equalsIgnoreCase("OUTBOUND_CORRESPONDENCE") && link.get("externalLinkRefId").toString().equalsIgnoreCase(cid)) {
                    Assert.assertEquals(link.get("internalRefId").toString(), entityId);
                    linkFound = true;
                }
            }
        } else {
            List<Map<String, Object>> links = eventsResponse.getList("externalLinkDetails.content");
            for (Map<String, Object> link : links) {
                if (link.get("name").toString().equalsIgnoreCase("OUTBOUND CORRESPONDENCE")) {
                    Assert.assertEquals(link.get("internalId").toString().trim(), entityId);
                    Assert.assertEquals(link.get("id").toString().trim(), cid);
                    Assert.assertEquals(link.get("effectiveStartDate").toString().substring(0, 13), expectedDateAndHour.get(), "Effective startData mismatched for Link");
                    linkFound = true;
                }
            }
        }
        Assert.assertTrue(linkFound, "OB link not found for " + entityType);
    }

    public void clickAndSelectCancel() {
        viewOutboundCorrespondenceDetailsPage.notificationActionDropdown.click();
        waitFor(1);
        viewOutboundCorrespondenceDetailsPage.cancelOpt.click();
    }

    public void verifyCancelNotDisplayed() {
        List<WebElement> el = Driver.getDriver().findElements(By.xpath("//i[@title='Read More']"));
        for (int i = 0; i <= el.size() - 1; i++) {
            el.get(i).click();
            try {
                WebElement elem = Driver.getDriver().findElement(By.xpath("//span[.='CANCEL']"));
                Assert.assertFalse(outcorr.get().elementIsDisplayed(elem));
            } catch (NoSuchElementException e) {
            }
        }
    }

    public void verifyNotificationReasonError() {
        String expected = "Status Reason is required and cannot be left blank";  //updated error message from newer story CP-2935
        String actual = viewOutboundCorrespondenceDetailsPage.notificationReasonBlankErrorMsg.getText();
        Assert.assertEquals(expected, actual, "Actual error message is " + actual + " --- " + expected + " is Expected");
    }

    public String notificationStatus() {
        String actualStatusValue = viewOutboundCorrespondenceDetailsPage.notificationStatusValue.getText();
        return actualStatusValue;
    }

    public void iVerifyThecOutboundCorrespondenceDropdownValuesFor(String ddname, List<String> ddvalues) {
        waitFor(2);
        Actions action = new Actions(Driver.getDriver());
        switch (ddname) {
            case "action notification":
                viewOutboundCorrespondenceDetailsPage.notificationActionDropdown.click();
                waitFor(1);
                ValidatingdropdownValues(ddvalues);
                action.sendKeys(Keys.SPACE).build().perform();
                waitFor(1);
                break;
            case "Notification Reason":
                viewOutboundCorrespondenceDetailsPage.notificationReasonDropdown.click();
                waitFor(1);
                ValidatingdropdownValues(ddvalues);
                action.sendKeys(Keys.ESCAPE).build().perform();
                waitFor(1);
                break;
            case "Correspondence status":
                viewOutboundCorrespondenceDetailsPage.statusDropdown.click();
                waitFor(1);
                ValidatingdropdownValues(ddvalues);
                action.sendKeys(Keys.ESCAPE).build().perform();
                waitFor(1);
                break;
            case "Correspondence Reason":
                viewOutboundCorrespondenceDetailsPage.statusReasonDropdown.click();
                waitFor(1);
                ValidatingdropdownValues(ddvalues);
                action.sendKeys(Keys.ESCAPE).build().perform();
                waitFor(1);
                break;
        }
    }

    public void ValidatingdropdownValues(List<String> options) {
        WebElement el;
        for (String option : options) {

            if (option.equalsIgnoreCase("empty")) {
                boolean b = new UIAutoUitilities().quickIsDisplayed(viewOutboundCorrespondenceDetailsPage.dropdownlist);
                Assert.assertTrue(!b);
            } else {
                el = Driver.getDriver().findElement(By.xpath("//li[.='" + option + "']"));
                Assert.assertTrue(el.isDisplayed());
                waitFor(1);
            }
        }
    }

    public void verifyNotificationStatusReasonErrorMsg() {
        String expected = "Status Reason is required and cannot be left blank";
        String actual = viewOutboundCorrespondenceDetailsPage.notificationReasonBlankErrorMsg.getText();
        Assert.assertEquals(expected, actual, "Actual error message is " + actual + " --- " + expected + " is Expected");
    }

    public void verifyNotificationStatusdate() {
        String actual = viewOutboundCorrespondenceDetailsPage.statusdate.getAttribute("value");
        String expected = ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("MM/dd/YYYY");
        Assert.assertEquals(expected, actual, "Actual status date is " + actual + " --- " + expected + " is Expected");

    }

    public void verifyNotificationStatusDateErrorMsg() {
        String expected = "Sent Date is required and cannot be left blank";
        String actual = viewOutboundCorrespondenceDetailsPage.notificationsentdate.getText();
        Assert.assertEquals(expected, actual, "Actual error message is " + actual + " --- " + expected + " is Expected");
    }

    public void verifydisableNotificationStatus() {
        String b = viewOutboundCorrespondenceDetailsPage.notificationreason.getAttribute("aria-disabled");
        Assert.assertEquals("true", b, "Status Reason should be in disable mode");
    }

    public void verifyNotificationReturnedDateErrorMsg() {
        String expected = "RETURNED DATE is required when STATUS is Returned.";
        String actual = viewOutboundCorrespondenceDetailsPage.notificationreturneddate.getText();
        Assert.assertEquals(actual, expected, "Actual error message is " + actual + " --- " + expected + " is Expected");
    }

    public void updateNotificationReturndate(String date) {
        String datevalue = "";
        if (date.toLowerCase().equals("currentdate")) {
            datevalue = ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("MM/dd/YYYY");
        } else {
            datevalue = date; // MM/dd/YYYY format
        }
        viewOutboundCorrespondenceDetailsPage.returndate.click();
        viewOutboundCorrespondenceDetailsPage.returndate.sendKeys(datevalue);
    }

    public void clearcalendardate() {
        viewOutboundCorrespondenceDetailsPage.calendaricon.click();
        waitFor(2);
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(viewOutboundCorrespondenceDetailsPage.clearcalendaricon);
        actions.click().build().perform();

    }

    public void iSelectcustomvaluefromdropdown(String dvalue, String dname) {
        waitFor(2);
        switch (dname) {
            case "Correspondence status":
                correspondenceStatus.set(dvalue);
                viewOutboundCorrespondenceDetailsPage.statusDropdown.click();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//li[text()='" + dvalue + "']")).click();
                waitFor(1);
                break;
            case "Correspondence Reason":
                viewOutboundCorrespondenceDetailsPage.statusReasonDropdown.click();
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//li[text()='" + dvalue + "']")).click();
                waitFor(1);
                break;
        }
    }

    public void verifydisablecorrespndenceStatusreason() {
        String b = viewOutboundCorrespondenceDetailsPage.statusReasonDropdown.getAttribute("aria-disabled");
        Assert.assertEquals("true", b, "Status Reason should be in disable mode");

    }

    public void postOutboundCorrWithFourChannels() {
        requestParams.set(sendEventTdu.get().getJsonFromFile("dms/correspondenceWithFourRecipient.json").jsonElement.getAsJsonObject());
        String channelType =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType();
        requestParams.get().getAsJsonObject().addProperty("correspondenceDefinitionMMSCode", channelType);
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().addProperty("caseId",API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId"));
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().addProperty("projectId", apiClassUtil.getProjectId());
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().getAsJsonArray("regardingConsumerId").getAsJsonArray().remove(0);
        String consumerId =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("consumerId");
        World.save.get().put("consumerIdWithName", consumerId + " " + generalSave.get().get("firstName") + " " + generalSave.get().get("lastName"));
        requestParams.get().getAsJsonObject().get("anchor").getAsJsonObject().getAsJsonArray("regardingConsumerId").getAsJsonArray().add(consumerId);
        int userId = Integer.parseInt(apiClassUtil.getApiUserInfo());
        int notificationsSize = requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().size();
        for (int i = 0; i < notificationsSize; i++) {
            requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(i).getAsJsonObject().addProperty("createdBy", userId);
            requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(i).getAsJsonObject().addProperty("template", randomNumberBetweenTwoNumbers(1, 99));
            requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("notifications").getAsJsonArray().get(i).getAsJsonObject().addProperty("version", randomNumberBetweenTwoNumbers(100, 999));
        }
        requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("firstName", generalSave.get().get("firstName").toString());
        requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("lastName", generalSave.get().get("lastName").toString());
        requestParams.get().getAsJsonObject().getAsJsonArray("recipients").getAsJsonArray().get(0).getAsJsonObject().get("recipientInfo").getAsJsonObject().addProperty("consumerId", consumerId);
        requestParams.get().getAsJsonObject().get("requester").getAsJsonObject().addProperty("requesterId", userId);
        requestParams.get().getAsJsonObject().getAsJsonObject().addProperty("createdBy", userId);
        requestParams.get().getAsJsonObject().getAsJsonObject().addProperty("responseDueDate", LocalDate.now().plusYears(1).toString());
        response.set(apiAutoUtilities.get().postAPI(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences", requestParams.get().getAsJsonObject()));
        System.out.println(response.get().asString());
        generalSave.get().put("JsonRequest", requestParams.get());
        cId.set(response.get().jsonPath().getString("data.id"));
        System.out.println("Correspondence ID " + cId.get());
    }

    public void verifyCorrespondenceId() {
        Assert.assertTrue(correspondenceDetailsPage.corIdLabel.isDisplayed(), "CID label verification failed");
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.corIdValue, 10), "CID length verification failed");
        Assert.assertTrue(BrowserUtils.hasOnlyDigits(correspondenceDetailsPage.corIdValue.getText()), "CID contains letter or special characters");
    }

    public void verifyCreatedByLabelAndFormat() {
        expectedUserName.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
        System.out.println("Full Name " + expectedUserName.get());
        Assert.assertEquals(correspondenceDetailsPage.createdByID.getText(), expectedUserName.get());
        Assert.assertEquals(correspondenceDetailsPage.createdByLabel.getText(), "CREATED BY", "Create By label verification failed");
        Assert.assertTrue(UIAutoUitilities.isAlphanumericWithSpace(correspondenceDetailsPage.createdByID.getText()));
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.createdByID, 30), "Created by value length verification failed");
    }

    public void verifyCreatedOnLabelAndFormat() {
        Assert.assertEquals(correspondenceDetailsPage.createdOnLabelTopPortion.getText(), "CREATED ON", "Create On label verification failed");
        Assert.assertTrue(UIAutoUitilities.isMM_DD_YYYY_HHMM_XM_format(correspondenceDetailsPage.createdOnValueTopPortion.getText()), "Date format verification failed");
    }

    public void verifyUpdatedByLabelAndFormat() {
        Assert.assertEquals(correspondenceDetailsPage.updatedByValue.getText(), expectedUserName.get());
        Assert.assertEquals(correspondenceDetailsPage.updatedByLabel.getText(), "UPDATED BY", "Updated By label verification failed");
        Assert.assertTrue(UIAutoUitilities.isAlphanumericWithSpace(correspondenceDetailsPage.updatedByValue.getText()));
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.updatedByValue, 30), "Updated by value length verification failed");
    }

    public void verifyUpdatedOnLabelAndFormat() {
        Assert.assertEquals(correspondenceDetailsPage.updatedOnLabel.getText(), "UPDATED ON", "Updated On label verification failed");
        Assert.assertTrue(UIAutoUitilities.isMM_DD_YYYY_HHMM_XM_format(correspondenceDetailsPage.updatedOnValue.getText()), "Date format verification failed");
    }

    public void verifyUpdatedByValue() {
        Assert.assertEquals(correspondenceDetailsPage.updatedByValue.getText(), loggedInUserName, "Updated By Value verification failed");
    }

    public void verifyUpdatedOnValue() {
        Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(correspondenceDetailsPage.updatedOnValue.getText(), getCurrentSystemDateTime()), "Updated On Value verification failed."
                + "Expected " + getCurrentSystemDateTime() + " But found " + correspondenceDetailsPage.updatedOnValue.getText());
    }

    public void verifyStatusDateLabelAndFormat() {
        Assert.assertEquals(correspondenceDetailsPage.statusDateLabelTopPortion.getText(), "STATUS DATE", "Status Date label verification failed");
        Assert.assertTrue(UIAutoUitilities.isMM_DD_YYYY_HHMM_XM_format(correspondenceDetailsPage.statusDateValueTopPortion.getText()), "Date format verification failed");
    }

    public void verifyResponseDueDateLabelAndFormat() {
        String expectedResponseDueDate = response.get().jsonPath().getString("data.responseDueDate");
        Assert.assertEquals(correspondenceDetailsPage.responseDueDateValue.getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(expectedResponseDueDate));
        Assert.assertEquals(correspondenceDetailsPage.responseDueDateLabel.getText(), "RESPONSE DUE DATE", "Status Date label verification failed");
        Assert.assertTrue(BrowserUtils.isMMddYYYYformat(correspondenceDetailsPage.responseDueDateValue.getText()), "Date format verification failed");
    }

    public void verifyCorrespondenceTypeLabelAndFormat() {
        String expectedOCType = "";
        if (API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getDefaultOutboundType().equalsIgnoreCase("ALLCHANNEL")) {
            expectedOCType = "GENERIC WITH ALL CHANNELS - ALLCHANNEL";
        } else {
            expectedOCType = "SAMPLE CASE LETTER - BL01";
        }
        Assert.assertEquals(correspondenceDetailsPage.corTypeValue.getText(), expectedOCType);
        Assert.assertEquals(correspondenceDetailsPage.corTypeLabel.getText(), "CORRESPONDENCE TYPE", "Correspondence label verification failed");
    }

    public void verifyLanguageLabelAndFormat() {
        String expectedLanguage = response.get().jsonPath().getString("data.language");
        Assert.assertEquals(correspondenceDetailsPage.languageLabelTopPortion.getText(), "LANGUAGE", "Language label verification failed");
        Assert.assertEquals(correspondenceDetailsPage.languageValueTopPortion.getText(), expectedLanguage);
        Assert.assertTrue(BrowserUtils.isAlphanumeric(correspondenceDetailsPage.languageValueTopPortion.getText()), "Language value contains numbers or special characters");
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.languageValueTopPortion, 30), "Language value length verification failed");
    }

    public void verifyStatusLabelAndFormat() {
        String expectedStatus = response.get().jsonPath().getString("data.status");
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusLabels.get(0).getText(), "STATUS", "Status label verification failed");
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusValues.get(0).getText(), expectedStatus);
        Assert.assertTrue(BrowserUtils.hasOnlyLettersSpaces(correspondenceDetailsPage.listOfStatusValues.get(0).getText()), "Status value contains numbers or special characters");
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.listOfStatusValues.get(0), 30), "Status value length verification failed");
    }

    public void verifyEditButtonAndStatusDropdowns() {
        Assert.assertTrue(correspondenceDetailsPage.correspondenceEditButton.isEnabled(), "Edit button verification failed");
        correspondenceDetailsPage.correspondenceEditButton.click();
        waitFor(1);
        Assert.assertTrue(correspondenceDetailsPage.outboundCorrespondenceStatus.isDisplayed(), "Status dropdown verification failed");
        Assert.assertTrue(correspondenceDetailsPage.outboundCorrespondenceStatusReason.isDisplayed(), "Status reason dropdown verification failed");
        Assert.assertTrue(correspondenceDetailsPage.topPortionSaveButton.isEnabled(), "Save button verification failed");
        Assert.assertTrue(correspondenceDetailsPage.topPortionCancelButton.isEnabled(), "Cancel button verification failed");

        correspondenceDetailsPage.outboundCorrespondenceStatus.click();
        int sizeOfStatusDropdown = correspondenceDetailsPage.statusDropDownValues.size();
        correspondenceDetailsPage.statusDropDownValues.get(new Random().nextInt(sizeOfStatusDropdown - 1)).click();
        String updatedStatus = correspondenceDetailsPage.outboundCorrespondenceStatus.getText();
        System.out.println("Selected status " + updatedStatus);
        waitFor(1);
        correspondenceDetailsPage.topPortionSaveButton.click();
        Assert.assertTrue(correspondenceDetailsPage.statusReasonRequiredMessage.isDisplayed(), "Warning message verification failed");
        correspondenceDetailsPage.outboundCorrespondenceStatusReason.click();
        waitFor(1);
        int sizeOfReasonStatusDropdown = correspondenceDetailsPage.statusReasonDropDownValues.size();
        correspondenceDetailsPage.statusReasonDropDownValues.get(new Random().nextInt(sizeOfReasonStatusDropdown - 1)).click();
        String updatedStatusReason = correspondenceDetailsPage.outboundCorrespondenceStatusReason.getText();
        System.out.println("Selected status reason " + updatedStatusReason);
        correspondenceDetailsPage.topPortionSaveButton.click();
        //verify new Status and Status Reason saved
        waitForVisibility(correspondenceDetailsPage.statusReasonValue, 5);
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusValues.get(0).getText(), updatedStatus);
        Assert.assertEquals(correspondenceDetailsPage.statusReasonValue.getText(), updatedStatusReason);
    }

    public void verifyRegardingSection() {
        Assert.assertEquals(correspondenceDetailsPage.regardingLabel.getText(), "REGARDING", "Regarding label verification failed");
        Assert.assertEquals(correspondenceDetailsPage.caseIdLabel.getText(), "CASE ID", "Case ID label verification failed");
        Assert.assertTrue(BrowserUtils.hasOnlyDigits(correspondenceDetailsPage.caseIdValue.getText()));
        Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.caseIdValue, 10), "Case ID value length verification failed");
        String expectedCaseId = response.get().jsonPath().getString("data.anchor.caseId");
        Assert.assertEquals(correspondenceDetailsPage.caseIdValue.getText(), expectedCaseId);
        Assert.assertEquals(correspondenceDetailsPage.consumersLabel.getText(), "CONSUMER(S)", "Consumers label verification failed");
        String consumerIdWithFullName = World.save.get().get("consumerIdWithName");
        Assert.assertEquals(correspondenceDetailsPage.listOfConsumer.get(0).getText(), consumerIdWithFullName);
    }

    public void verifyRecipientsInfoInNotifications() {
        Assert.assertEquals(correspondenceDetailsPage.listOfConsumerIdsNotifications.get(0).getText(), World.save.get().get("consumerIdWithName").split(" ")[0], "Consumer id verification failed");
        String consumerName = World.save.get().get("consumerIdWithName").split(" ")[1] + " " + World.save.get().get("consumerIdWithName").split(" ")[2];
        Assert.assertEquals(correspondenceDetailsPage.listOfConsumerNamesNotifications.get(0).getText(), consumerName, "Consumer Name verification failed");
        Assert.assertEquals(correspondenceDetailsPage.listOfConsumerRolesNotifications.get(0).getText(), "Primary Individual", "Consumer Role verification failed");
        int channelSize = response.get().jsonPath().getInt("data.recipients[0].notifications.size");
        String expectedAddress = response.get().jsonPath().getString("data.recipients[0].recipientInfo.streetAddress")
                + " " + response.get().jsonPath().getString("data.recipients[0].recipientInfo.streetAddionalLine1")
                + " " + response.get().jsonPath().getString("data.recipients[0].recipientInfo.city")
                + " " + response.get().jsonPath().getString("data.recipients[0].recipientInfo.state")
                + " " + response.get().jsonPath().getString("data.recipients[0].recipientInfo.zipcode");
        String expectedEmail = response.get().jsonPath().getString("data.recipients[0].recipientInfo.emailAddress");
        String expectedPhone = response.get().jsonPath().getString("data.recipients[0].recipientInfo.textNumber");
        String expectedFax = response.get().jsonPath().getString("data.recipients[0].recipientInfo.faxNumber");
        for (int i = 0; i < channelSize; i++) {
            Assert.assertEquals(correspondenceDetailsPage.listOfChannelsNotifications.get(i).getText(), response.get().jsonPath().getString("data.recipients[0].notifications[" + i + "].channelType"));
            switch (correspondenceDetailsPage.listOfDestinationsNotifications.get(i).getText()) {
                case "Mail":
                    Assert.assertEquals(correspondenceDetailsPage.listOfDestinationsNotifications.get(i).getText(), expectedAddress);
                    break;
                case "Email":
                    Assert.assertEquals(correspondenceDetailsPage.listOfDestinationsNotifications.get(i).getText(), expectedEmail);
                    break;
                case "Text":
                    Assert.assertEquals(correspondenceDetailsPage.listOfDestinationsNotifications.get(i).getText(), expectedPhone);
                    break;
                case "Fax":
                    Assert.assertEquals(correspondenceDetailsPage.listOfDestinationsNotifications.get(i).getText(), expectedFax);
                    break;
            }
            Assert.assertTrue(BrowserUtils.hasOnlyDigits(correspondenceDetailsPage.listOfNIDNotifications.get(i).getText()));
            Assert.assertTrue(UIAutoUitilities.verifyWebElementTextLength(correspondenceDetailsPage.listOfNIDNotifications.get(i), 10));
            Assert.assertEquals(correspondenceDetailsPage.listOfLanguagesNotifications.get(i).getText(), response.get().jsonPath().getString("data.recipients[0].notifications[" + i + "].language"));
            Assert.assertEquals(correspondenceDetailsPage.listOfTemplatesNotifications.get(i).getText(), response.get().jsonPath().getString("data.recipients[0].notifications[" + i + "].template"));
            Assert.assertEquals(correspondenceDetailsPage.listOfVersionsNotifications.get(i).getText(), response.get().jsonPath().getString("data.recipients[0].notifications[" + i + "].version"));
            Assert.assertTrue(BrowserUtils.isMMddYYYYformat(correspondenceDetailsPage.listOfCreatedOnNotifications.get(i).getText()));
            Assert.assertTrue(BrowserUtils.isMMddYYYYformat(correspondenceDetailsPage.listOfStatusDateNotifications.get(i).getText()));
            Assert.assertTrue(correspondenceDetailsPage.listOfStatusHistories.get(i).isDisplayed());
            Assert.assertTrue(correspondenceDetailsPage.listOfBurgerOptions.get(i).isDisplayed());
            Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(i).getText(), response.get().jsonPath().getString("data.status"));
        }
    }

    public void verifyStatusHistoryAndBurgerOptionsFunctionality() throws ParseException {
        Random random = new Random();
        String reasonValue = "";
        int indexForRandomReason = 0;
        List<String> expectedValues = Arrays.asList("RETURNED", "RESEND");
        int indexForRandomChannel = random.nextInt(correspondenceDetailsPage.listOfStatusHistories.size());
        waitFor(1);
        correspondenceDetailsPage.listOfBurgerOptions.get(indexForRandomChannel).click();
        int indexForRandomOption = random.nextInt(correspondenceDetailsPage.listOfOptionsInBurgerMenu.size());
        String selectedOption = correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(indexForRandomOption).getText();
        waitFor(1);
        correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(indexForRandomOption).click();
        switch (selectedOption) {
            case "RETURNED":
                Assert.assertTrue(correspondenceDetailsPage.reasonDropdownNotifications.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.returnDatePickerNotifications.isDisplayed());
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                reasonValue = correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).getText();
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                String returnDate =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().minusDays(1).toString());
                correspondenceDetailsPage.returnDatePickerNotifications.sendKeys(returnDate);
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitForVisibility(correspondenceDetailsPage.reasonNotifications, 10);
                if (!correspondenceDetailsPage.listOfStatusNotifications.get(indexForRandomChannel).getText().equalsIgnoreCase("Returned")) {
                    waitFor(3);
                }
                Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(indexForRandomChannel).getText(), "Returned");
                Assert.assertEquals(correspondenceDetailsPage.reasonNotifications.getText(), reasonValue);
                Assert.assertEquals(correspondenceDetailsPage.returnDateNotifications.getText(), returnDate);
                waitFor(2);
                System.out.println("waiting to click on Status history");
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(indexForRandomChannel)).build().perform();
                waitFor(2);
                waitForVisibility(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (indexForRandomChannel + 1) + "]/../div/table/tbody//td)[3]")), 10);
                Assert.assertTrue(Driver.getDriver().findElement(By.xpath("(//h5[text()='STATUS HISTORY'])[" + (indexForRandomChannel + 1) + "]")).isDisplayed());
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (indexForRandomChannel + 1) + "]/../div/table/tbody//td)[3]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (indexForRandomChannel + 1) + "]/../div/table/tbody//td)[4]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (indexForRandomChannel + 1) + "]/../div/table/tbody//td)[5]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));

                correspondenceDetailsPage.listOfBurgerOptions.get(indexForRandomChannel).click();
                int newSizeOfBurgerMenuResend = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                for (int i = 0; i < newSizeOfBurgerMenuResend; i++) {
                    if (!expectedValues.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for Resend");
                    }
                }
                break;
            case "RESEND":
                Assert.assertTrue(correspondenceDetailsPage.resendLabel.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.resendChannelDropdown.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.resendDestinationDropdown.isDisplayed());
                waitForVisibility(correspondenceDetailsPage.saveButtonForResend, 5);
                correspondenceDetailsPage.saveButtonForResend.click();
                waitForVisibility(correspondenceDetailsPage.resendFlag, 10);
                Assert.assertTrue(correspondenceDetailsPage.resendFlag.isDisplayed(), "Resend flag verification failed");
                break;
            case "SENT":
                String actualDate = correspondenceDetailsPage.sentStatusDatePickerNotifications.getAttribute("value");
                String todayDate =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Assert.assertTrue(sdf.parse(actualDate).compareTo((sdf.parse(todayDate))) <= 0);
                waitForVisibility(correspondenceDetailsPage.saveButtonNotifications, 5);
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitFor(10);
                Assert.assertTrue(correspondenceDetailsPage.listOfStatusNotifications.get(indexForRandomChannel).getText().equalsIgnoreCase("Sent"));
                correspondenceDetailsPage.listOfBurgerOptions.get(indexForRandomChannel).click();
                int newSizeOfBurgerMenuSent = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                for (int i = 0; i < newSizeOfBurgerMenuSent; i++) {
                    if (!expectedValues.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for Sent");
                    }
                }
                break;
            case "HOLD":
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                reasonValue = correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).getText();
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitFor(10);
                Assert.assertTrue(correspondenceDetailsPage.listOfStatusNotifications.get(indexForRandomChannel).getText().equalsIgnoreCase("On Hold"));

                correspondenceDetailsPage.listOfBurgerOptions.get(indexForRandomChannel).click();
                int newSizeOfBurgerMenuOnHold = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                List<String> expectedValuesOnHold = Arrays.asList("RESUME", "CANCEL");
                for (int i = 0; i < newSizeOfBurgerMenuOnHold; i++) {
                    if (!expectedValuesOnHold.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for On Hold");
                    }
                }
                break;
            case "CANCEL":
                Assert.assertTrue(correspondenceDetailsPage.reasonDropdownNotifications.isDisplayed());
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitForVisibility(correspondenceDetailsPage.yesPopUpMessage, 5);
                correspondenceDetailsPage.yesPopUpMessage.click();
                waitFor(10);
                Assert.assertTrue(correspondenceDetailsPage.listOfStatusNotifications.get(indexForRandomChannel).getText().equalsIgnoreCase("Canceled"));
                break;
        }
    }

    public void verifyStatusHistoryForNotificationStatus(String notificationStatus) throws ParseException {
        List<String> expectedValues = Arrays.asList("RETURNED", "RESEND");
        Random random = new Random();
        switch (notificationStatus) {
            case "RETURNED":
                Assert.assertTrue(correspondenceDetailsPage.reasonDropdownNotifications.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.returnDatePickerNotifications.isDisplayed());
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                int indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                String reasonValue = correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).getText();
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                String returnDate =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString());
                correspondenceDetailsPage.returnDatePickerNotifications.sendKeys(returnDate);
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitForVisibility(correspondenceDetailsPage.reasonNotifications, 10);
                if (!correspondenceDetailsPage.listOfStatusNotifications.get(0).getText().equalsIgnoreCase("Returned")) {
                    waitFor(3);
                }
                Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText(), "Returned");
                Assert.assertEquals(correspondenceDetailsPage.reasonNotifications.getText(), reasonValue);
                Assert.assertEquals(correspondenceDetailsPage.returnDateNotifications.getText(), returnDate);
                waitFor(2);
                System.out.println("waiting to click on Status history");
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(0)).build().perform();
                waitFor(2);
                waitForVisibility(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")), 10);
                Assert.assertTrue(Driver.getDriver().findElement(By.xpath("(//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]")).isDisplayed());
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[4]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[5]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));

                correspondenceDetailsPage.listOfBurgerOptions.get(0).click();
                int newSizeOfBurgerMenuResend = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                for (int i = 0; i < newSizeOfBurgerMenuResend; i++) {
                    if (!expectedValues.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for Resend");
                    }
                }
                break;
            case "RESEND":
                Assert.assertTrue(correspondenceDetailsPage.resendLabel.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.resendChannelDropdown.isDisplayed());
                Assert.assertTrue(correspondenceDetailsPage.resendDestinationDropdown.isDisplayed());
                waitForVisibility(correspondenceDetailsPage.saveButtonForResend, 5);
                correspondenceDetailsPage.saveButtonForResend.click();
                waitForVisibility(correspondenceDetailsPage.resendFlag, 10);
                Assert.assertTrue(correspondenceDetailsPage.resendFlag.isDisplayed(), "Resend flag verification failed");
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(0)).build().perform();
                waitFor(2);
                break;
            case "SENT":
                String actualDate = correspondenceDetailsPage.sentStatusDatePickerNotifications.getAttribute("value");
                String todayDate =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Assert.assertTrue(sdf.parse(actualDate).compareTo((sdf.parse(todayDate))) <= 0);
                waitForVisibility(correspondenceDetailsPage.saveButtonNotifications, 5);
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitFor(10);
                Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText(), "Sent");
                int newSizeOfBurgerMenuSent = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                for (int i = 0; i < newSizeOfBurgerMenuSent; i++) {
                    if (!expectedValues.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for Sent");
                    }
                }
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(0)).build().perform();
                waitFor(2);
                waitForVisibility(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")), 10);
                Assert.assertTrue(Driver.getDriver().findElement(By.xpath("(//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]")).isDisplayed());
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[4]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[5]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                break;
            case "HOLD":
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                reasonValue = correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).getText();
                waitFor(1);
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitFor(10);
                Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText(), "On Hold");
                int newSizeOfBurgerMenuOnHold = correspondenceDetailsPage.listOfOptionsInBurgerMenu.size();
                List<String> expectedValuesOnHold = Arrays.asList("RESUME", "CANCEL");
                for (int i = 0; i < newSizeOfBurgerMenuOnHold; i++) {
                    if (!expectedValuesOnHold.get(i).equalsIgnoreCase(correspondenceDetailsPage.listOfOptionsInBurgerMenu.get(i).getText())) {
                        Assert.assertTrue(false, "New options values verification failed for On Hold");
                    }
                }
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(0)).build().perform();
                waitFor(2);
                waitForVisibility(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")), 10);
                Assert.assertTrue(Driver.getDriver().findElement(By.xpath("(//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]")).isDisplayed());
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[4]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[5]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                break;
            case "CANCEL":
                Assert.assertTrue(correspondenceDetailsPage.reasonDropdownNotifications.isDisplayed());
                correspondenceDetailsPage.reasonDropdownNotifications.click();
                indexForRandomReason = random.nextInt(correspondenceDetailsPage.reasonDropdownValuesNotifications.size());
                correspondenceDetailsPage.reasonDropdownValuesNotifications.get(indexForRandomReason).click();
                correspondenceDetailsPage.saveButtonNotifications.click();
                waitForVisibility(correspondenceDetailsPage.yesPopUpMessage, 5);
                correspondenceDetailsPage.yesPopUpMessage.click();
                waitFor(10);
                Assert.assertTrue(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText().equalsIgnoreCase("Canceled"));
                action.click(correspondenceDetailsPage.listOfStatusHistories.get(0)).build().perform();
                waitFor(2);
                waitForVisibility(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")), 10);
                Assert.assertTrue(Driver.getDriver().findElement(By.xpath("(//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]")).isDisplayed());
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[3]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[4]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getUserAccountNameById(response.get().jsonPath().getString("data.createdBy")));
                Assert.assertEquals(Driver.getDriver().findElement(By.xpath("((//h5[text()='STATUS HISTORY'])[" + (0 + 1) + "]/../div/table/tbody//td)[5]")).getText(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(LocalDate.now().toString()));
                break;
        }
    }

    public void updateStatusToPrecluded() {
        List<String> listOfnIds = getElementsText(correspondenceDetailsPage.listOfNIDNotifications);
        List<String> listOfChannels = getElementsText(correspondenceDetailsPage.listOfChannelsNotifications);
        int precludedIndex = new Random().nextInt(listOfnIds.size() - 1);
        World.save.get().put("channelName", listOfChannels.get(precludedIndex));
        World.save.get().put("channelNId", listOfnIds.get(precludedIndex));
       API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().updateNotification(listOfnIds.get(precludedIndex), "Precluded");
    }

    public void verifyChannelUpdatedToPrecludeNotDisplayed() {
        Assert.assertFalse(new UIAutoUitilities().quickIsDisplayed("//h6[text()='" + World.save.get().get("channelName") + "']"), "Channel " + World.save.get().get("channelName") + " displayed, verification failed");
    }

    public void clickNOOnCancelConfirmMsg() {
        waitFor(2);
        viewOutboundCorrespondenceDetailsPage.warningMsgChecknobttn.click();
        waitFor(5);
    }

    public void verifyCorrespondenceStatusReason(String corrStatusReason) {
        String actualCorrStatus = viewOutboundCorrespondenceDetailsPage.correspondenceStatusReasonValue.getText();
        Assert.assertEquals(actualCorrStatus, corrStatusReason, "Actual Correspondence Status Reason: " + actualCorrStatus + " Expected Status Reason: " + corrStatusReason);
    }

    public String notificationStatusreason() {
        String actualStatusValue = viewOutboundCorrespondenceDetailsPage.notificationStatusreasonValue.getText();
        return actualStatusValue;
    }

    public void validateunprovisionedcid(String cId) {
        getOutCorrWithCId(cId);
        String actualCorrStatus = getCId.get().jsonPathEvaluator.get("status").toString();
        Assert.assertEquals(actualCorrStatus, "Requested");
        List<Integer> recipients = new ArrayList<>(getCId.get().jsonPathEvaluator.getList("recipients"));
        Assert.assertTrue(recipients.isEmpty(), "outbound correspondence is processed with default recipients and channels: " + cId);
    }

    public boolean validateprovisionedcid(String cId) {
        getOutCorrWithCId(cId);
        List<Integer> recipients = new ArrayList<>(getCId.get().jsonPathEvaluator.getList("recipients"));
        return recipients.isEmpty();
    }

    public void verifyNotificationDeatailsUI(Map<String, String> dataTable) {
        String NID, Language, Status, Reason;
        String TemplateId = null, Version = null;
        for (String data : dataTable.keySet()) {
            switch (data.toLowerCase()) {
                case "previouslycreated_mail_nid_templateid":
                case "previouslycreated_mail_nid_version":
                case "previouslycreated_mail_nid_status":
                case "previouslycreated_mail_nid_language":
                case "previouslycreated_mail_nid_reason":
                    NID = World.save.get().get("Mail");
                    Language = dataTable.get("previouslycreated_mail_nid_language");
                    Status = dataTable.get("previouslycreated_mail_nid_status");
                    Reason = dataTable.get("previouslycreated_mail_nid_reason");

                    if (Language != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Language).isDisplayed());
                    if (Status != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Status).isDisplayed());
                    if (Reason != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Reason).isDisplayed());
                    if (dataTable.get("previouslycreated_mail_nid_templateid") != null) {
                        if (dataTable.get("previouslycreated_mail_nid_templateid").equalsIgnoreCase("fromAPI")) {
                            Map<String, Object> values = (Map<String, Object>) random.get().get("Mail");
                            Map<String, Object> values1 = (Map<String, Object>) values.get(Language);
                            System.out.println("values1 : " + values1);
                            TemplateId = values1.get("templateId").toString();
                            Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, TemplateId).isDisplayed());
                        }
                    }
                    if (dataTable.get("previouslycreated_mail_nid_version") != null) {
                        if (dataTable.get("previouslycreated_mail_nid_version").equalsIgnoreCase("fromAPI")) {
                            Map<String, Object> values = (Map<String, Object>) random.get().get("Mail");
                            Map<String, Object> values1 = (Map<String, Object>) values.get(Language);
                            Version = values1.get("version").toString();
                            Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Version).isDisplayed());
                        }
                    }
                    break;
                case "previouslycreated_email_nid_status":
                case "previouslycreated_email_nid_language":
                case "previouslycreated_email_nid_reason":
                    NID = World.save.get().get("Email");
                    Language = dataTable.get("previouslycreated_email_nid_language");
                    Status = dataTable.get("previouslycreated_email_nid_status");
                    Reason = dataTable.get("previouslycreated_email_nid_reason");
                    if (Language != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Language).isDisplayed());
                    if (Status != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Status).isDisplayed());
                    if (Reason != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Reason).isDisplayed());
                    break;
                case "previouslycreated_text_nid_templateid":
                case "previouslycreated_text_nid_version":
                case "previouslycreated_text_nid_status":
                case "previouslycreated_text_nid_language":
                case "previouslycreated_text_nid_reason":
                    NID = World.save.get().get("Text");
                    Language = dataTable.get("previouslycreated_text_nid_language");
                    Status = dataTable.get("previouslycreated_text_nid_status");
                    Reason = dataTable.get("previouslycreated_text_nid_reason");
                    if (Language != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Language).isDisplayed());
                    if (Status != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Status).isDisplayed());
                    if (Reason != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Reason).isDisplayed());
                    if (dataTable.get("previouslycreated_text_nid_templateid") != null && dataTable.get("previouslycreated_text_nid_templateid").equalsIgnoreCase("fromAPI")) {
                        Map<String, Object> values = (Map<String, Object>) random.get().get("Text");
                        Map<String, Object> values1 = (Map<String, Object>) values.get(Language);
                        TemplateId = values1.get("templateId").toString();
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, TemplateId).isDisplayed());
                    }
                    if (dataTable.get("previouslycreated_text_nid_version") != null && dataTable.get("previouslycreated_text_nid_version").equalsIgnoreCase("fromAPI")) {
                        Map<String, Object> values = (Map<String, Object>) random.get().get("Text");
                        Map<String, Object> values1 = (Map<String, Object>) values.get(Language);
                        Version = values1.get("version").toString();
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Version).isDisplayed());
                    }
                    break;
                case "previouslycreated_fax_nid_status":
                case "previouslycreated_fax_nid_language":
                case "previouslycreated_fax_nid_reason":
                    NID = World.save.get().get("Fax");
                    Language = dataTable.get("previouslycreated_fax_nid_language");
                    Status = dataTable.get("previouslycreated_fax_nid_status");
                    Reason = dataTable.get("previouslycreated_fax_nid_reason");
                    if (Language != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Language).isDisplayed());
                    if (Status != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Status).isDisplayed());
                    if (Reason != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Reason).isDisplayed());
                    break;
                default:
                    NID = data;
                    Language = dataTable.get(data);
                    Status = dataTable.get(data);
                    Reason = dataTable.get(data);
                    if (Language != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Language).isDisplayed());
                    if (Status != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Status).isDisplayed());
                    if (Reason != null)
                        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.getOutboundCorrNIDValues(NID, Reason).isDisplayed());


            }
        }

    }


    public String returnExpectedErrorMessageForBulkAPI(String field) {
        String errorMessage = "";
        switch (field.toLowerCase(Locale.ROOT)) {
            case "mmscode":
                errorMessage = "CorrespondenceDefinitionMMSCode is required";
                break;
            case "notificationlanguage":
                errorMessage = "notification[0]-Notification language cannot be null or empty";
                break;
            case "status":
                errorMessage = "Status cannot be null or empty";
                break;
            case "notificationstatus":
                errorMessage = "notification[0]-Notification Status cannot be null or empty";
                break;
            case "createdby":
                errorMessage = "Created By cannot be null or empty";
                break;
            case "language":
                errorMessage = "Language is required";
                break;
            case "externalreftype":
                errorMessage = "externalLinks[0].externalRefType can not be null or empty";
                break;
            case "externalrefid":
                errorMessage = "externalLinks[0].externalRefId can not be null or empty";
                break;
            default:
                Assert.fail(field + " didn't match.");
        }
        return errorMessage;
    }

    public void setJsonPathEvaluator(JsonPath evaluator) {
        apiClassUtil.jsonPathEvaluator = evaluator;
    }


    public void resendropdown(String Value) {
        waitFor(3);
        switch (Value) {
            case "language":
                Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.notificationlanguageDropdown.getText().equalsIgnoreCase(""));
                break;
        }
    }

    public void add_resend_notification(Map<String, String> data) {
        waitFor(3);
        Actions action = new Actions(Driver.getDriver());
        waitFor(2);
        for (String keyword : data.keySet()) {
            switch (keyword) {
                case "ChannelList":
                    List<String> expected_channeldropdown = new ArrayList<>(random.get().keySet());
                    viewOutboundCorrespondenceDetailsPage.notificationchannelDropdown.click();
                    List<WebElement> channellist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    List<String> actual_Channelropdown = new ArrayList<String>();
                    for (WebElement channel : channellist) {
                        actual_Channelropdown.add(channel.getText());
                    }
                    Collections.sort(expected_channeldropdown);
                    Collections.sort(actual_Channelropdown);
                    System.out.println("expected_channeldropdown " + expected_channeldropdown.toString());
                    System.out.println("actual_Channelropdown " + actual_Channelropdown.toString());
                    Assert.assertEquals(actual_Channelropdown, expected_channeldropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "SelectChannel":
                    viewOutboundCorrespondenceDetailsPage.notificationchannelDropdown.click();
                    List<WebElement> channels = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement channel : channels) {
                        if (channel.getText().contains(data.get(keyword))) {
                            channel.click();
                            break;
                        }
                    }
                    break;
                case "LanguageList":
                    String channel = data.get(keyword);
                    Map<String, Object> values = (Map<String, Object>) random.get().get(channel);
                    List<String> expected_Languagedropdown = new ArrayList<>(values.keySet());
                    List<String> actual_Languagedropdown = new ArrayList<String>();
                    viewOutboundCorrespondenceDetailsPage.notificationlanguageDropdown.click();
                    waitFor(2);
                    List<WebElement> Languageslist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement lang : Languageslist) {
                        String lan = lang.getText().trim();
                        if (lan.contains("Preffered"))
                            lan = lan.replace("(Preffered)", "").trim();

                        actual_Languagedropdown.add(lan);
                    }
                    Collections.sort(expected_Languagedropdown);
                    Collections.sort(actual_Languagedropdown);
                    System.out.println("expected_Languagedropdown : " + expected_Languagedropdown);
                    System.out.println("actual_Languagedropdown   : " + actual_Languagedropdown);
                    Assert.assertEquals(actual_Languagedropdown, expected_Languagedropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "AddNotification_MailDestinationList":
                    List<String> expected_destinationdropdown = new ArrayList<String>();
                    List<String> actual_destinationdropdown = new ArrayList<String>();

                    if (data.get(keyword).equalsIgnoreCase("Active Destinations from casecorrespondence")) {
                        Map<String, Object> mailAddress = (Map<String, Object>) generalSave.get().get("mailAddress");
                        String casecorrespondence_mailaddress = "Mailing " + mailAddress.get("addressStreet1") + " " + mailAddress.get("addressStreet2") + " " + mailAddress.get("addressCity") + " " + mailAddress.get("addressState") + " " + mailAddress.get("addressZip");
                        expected_destinationdropdown.add(casecorrespondence_mailaddress);
                        expected_destinationdropdown.add("Other");
                    } else {
                        expected_destinationdropdown = Arrays.asList(data.get(keyword).split(","));
                      //  expected_destinationdropdown.add("Other");
                    }
                    viewOutboundCorrespondenceDetailsPage.notificationdestinationDropdown.click();
                    waitFor(2);
                    List<WebElement> destinationlist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement dest : destinationlist) {
                        actual_destinationdropdown.add(dest.getText());
                    }
                    Collections.sort(expected_destinationdropdown);
                    Collections.sort(actual_destinationdropdown);
                    System.out.println("expected_destinationdropdown : " + expected_destinationdropdown);
                    System.out.println("actual_destinationdropdown   : " + actual_destinationdropdown);
                    Assert.assertEquals(actual_destinationdropdown, expected_destinationdropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "AddNotification_EmailDestinationList":

                    List<String> expected_emaildestinationdropdown = new ArrayList<String>();
                    List<String> actual_emaildestinationdropdown = new ArrayList<String>();

                    if (data.get(keyword).equalsIgnoreCase("Active Destinations from casecorrespondence")) {
                        Map<String, Object> emailAddress = (Map<String, Object>) generalSave.get().get("emailAddress");
                        String casecorrespondence_emailaddress = "Primary " + emailAddress.get("emailAddress");
                        expected_emaildestinationdropdown.add(casecorrespondence_emailaddress);
                        expected_emaildestinationdropdown.add("Other");
                    } else
                        expected_emaildestinationdropdown = Arrays.asList(data.get(keyword).split(","));
                       // expected_emaildestinationdropdown.add("Other");

                    viewOutboundCorrespondenceDetailsPage.notificationdestinationDropdown.click();
                    waitFor(2);
                    List<WebElement> emaildestinationlist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement dest : emaildestinationlist) {
                        actual_emaildestinationdropdown.add(dest.getText());
                    }
                    Collections.sort(expected_emaildestinationdropdown);
                    Collections.sort(actual_emaildestinationdropdown);
                    System.out.println("expected_destinationdropdown : " + expected_emaildestinationdropdown);
                    System.out.println("actual_destinationdropdown   : " + actual_emaildestinationdropdown);
                    Assert.assertEquals(actual_emaildestinationdropdown, expected_emaildestinationdropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "AddNotification_TextDestinationList":
                    List<String> expected_textdestinationdropdown = new ArrayList<String>();
                    List<String> actual_textdestinationdropdown = new ArrayList<String>();

                    if (data.get(keyword).equalsIgnoreCase("Active Destinations from casecorrespondence")) {
                        Map<String, Object> textAddress = (Map<String, Object>) generalSave.get().get("textAddress");
                        String casecorrespondence_textaddress = textAddress.get("phoneNumber").toString();
                        expected_textdestinationdropdown.add(casecorrespondence_textaddress);
                        expected_textdestinationdropdown.add("Other");
                    } else
                        expected_textdestinationdropdown = Arrays.asList(data.get(keyword).split(","));
                    //expected_textdestinationdropdown.add("Other");


                    viewOutboundCorrespondenceDetailsPage.notificationdestinationDropdown.click();
                    waitFor(2);
                    List<WebElement> textdestinationlist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement dest : textdestinationlist) {
                        actual_textdestinationdropdown.add(dest.getText().replaceAll("-", ""));
                    }
                    Collections.sort(expected_textdestinationdropdown);
                    Collections.sort(actual_textdestinationdropdown);
                    System.out.println("expected_destinationdropdown : " + expected_textdestinationdropdown);
                    System.out.println("actual_destinationdropdown   : " + actual_textdestinationdropdown);
                    Assert.assertEquals(actual_textdestinationdropdown, expected_textdestinationdropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "AddNotification_FaxDestinationList":
                    List<String> expected_faxdestinationdropdown = new ArrayList<String>();
                    List<String> actual_faxdestinationdropdown = new ArrayList<String>();


                    if (data.get(keyword).equalsIgnoreCase("Active Destinations from casecorrespondence")) {
                        Map<String, Object> faxAddress = (Map<String, Object>) generalSave.get().get("faxAddress");
                        String casecorrespondence_faxaddress = faxAddress.get("phoneNumber").toString();
                        expected_faxdestinationdropdown.add(casecorrespondence_faxaddress);
                        expected_faxdestinationdropdown.add("Other");
                    } else
                        expected_faxdestinationdropdown = Arrays.asList(data.get(keyword).split(","));

                    //expected_faxdestinationdropdown.add("Other");

                    viewOutboundCorrespondenceDetailsPage.notificationdestinationDropdown.click();
                    waitFor(2);
                    List<WebElement> faxdestinationlist = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement dest : faxdestinationlist) {
                        actual_faxdestinationdropdown.add(dest.getText().replaceAll("-", ""));
                    }
                    Collections.sort(expected_faxdestinationdropdown);
                    Collections.sort(actual_faxdestinationdropdown);
                    System.out.println("expected_destinationdropdown : " + expected_faxdestinationdropdown);
                    System.out.println("actual_destinationdropdown   : " + actual_faxdestinationdropdown);
                    Assert.assertEquals(actual_faxdestinationdropdown, expected_faxdestinationdropdown);
                    action.sendKeys(Keys.ESCAPE).build().perform();
                    break;
                case "SelectLanguage":
                    viewOutboundCorrespondenceDetailsPage.notificationlanguageDropdown.click();
                    waitFor(2);
                    List<WebElement> Languages = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement lang : Languages) {
                        if (lang.getText().trim().contains(data.get(keyword))) {
                            lang.click();
                            break;
                        }
                    }
                    break;
                case "SelectDestination":
                    viewOutboundCorrespondenceDetailsPage.notificationdestinationDropdown.click();
                    waitFor(2);
                    List<WebElement> destinations = viewOutboundCorrespondenceDetailsPage.typedropdownvalues;
                    for (WebElement dest : destinations) {
                        if (dest.getText().trim().equalsIgnoreCase(data.get(keyword))) {
                            dest.click();
                            break;
                        }
                    }
                    break;
                case "OtherPhoneNumber":
                    waitFor(2);
                    clearTextField(viewOutboundCorrespondenceDetailsPage.phonefield);
                    viewOutboundCorrespondenceDetailsPage.phonefield.sendKeys(data.get(keyword));
                    break;
                case "OtherFaxNumber":
                    waitFor(2);
                    clearTextField(viewOutboundCorrespondenceDetailsPage.faxfield);
                    viewOutboundCorrespondenceDetailsPage.faxfield.sendKeys(data.get(keyword));
                    break;
                case "OtherEmail":
                    waitFor(2);
                    clearTextField(viewOutboundCorrespondenceDetailsPage.emailfield);
                    viewOutboundCorrespondenceDetailsPage.emailfield.sendKeys(data.get(keyword));
                    break;
                default:
                    Assert.fail("no matching key");
            }
        }

    }

    public void add_resend_warningmsgs(Map<String, String> data) {
        waitFor(3);
        for (String keyword : data.keySet()) {
            switch (keyword) {
                case "CHANNEL":
                case "Language":
                case "DESTINATION":
                case "Phone Number":
                case "EMAIL":
                case "ADDRESS LINE 1":
                case "City":
                case "STATE":
                case "ZIP CODE":
                case "PhoneNumberInvalidLength":
                case "PhoneNumberInvalidFormat":
                case "EmailInvalidFormat":
                    Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.requiredlabel(data.get(keyword)).getText(), data.get(keyword));
                    break;
                default:
                    Assert.fail("no matching key");
            }
        }

    }

}
