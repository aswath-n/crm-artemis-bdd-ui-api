package com.maersk.dms.step_definitions;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.CorrespondenceDetailsPage;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

public class PublishDPBIEventsOutboundCorrespondenceChangesStepDefs extends CRMUtilities implements ApiBase {

    CorrespondenceDetailsPage cDP = new CorrespondenceDetailsPage();
    CreateOutboundCorrespondencePage cOCP = new CreateOutboundCorrespondencePage();
    CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);
    final ThreadLocal<InboundCorrespondenceStepDefs> inboundStepDefs = ThreadLocal.withInitial(InboundCorrespondenceStepDefs::new);

    public void clickMenuIconDropDown() {
        browserUtils.get().hover(cOCP.menuIconDropDown);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cOCP.menuIconDropDown);
    }

    public void clickMenuIconCreateCorrOpt() {
        browserUtils.get().hover(cOCP.menuIconCreateCorrOpt);
        browserUtils.get().waitFor(3);
        clickIfElementIsDisplayed(cOCP.menuIconCreateCorrOpt);
        browserUtils.get().waitFor(3);
    }

    public void verifyOnCreateOutboundCorrespondenceSection() {
        browserUtils.get().waitFor(5);
        Assert.assertTrue(elementIsDisplayed(cOCP.createCorrHeader));
    }

    public String selectFirstOptionTypeStoreOpt() {
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        browserUtils.get().hover(cOCP.type);
        clickIfElementIsDisplayed(cOCP.type);
        browserUtils.get().waitFor(2);
        executor.executeScript("arguments[0].click();", Driver.getDriver().findElement(By.xpath("//li[contains(text(),'newName')]")));
        return getTextIfElementIsDisplayed(cOCP.type);
    }

    public String selectFirstOptionLanguageStoreOpt() {
        browserUtils.get().hover(cOCP.language);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cOCP.language);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cOCP.presentationOpt);
        return getTextIfElementIsDisplayed(cOCP.language);
    }

    public void clickOnFirstIndividual() {
        browserUtils.get().hover(cOCP.firstIndividual);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cOCP.firstIndividual);
    }

    public void hoverToSpecificIndividual(Integer ind) {
        browserUtils.get().hover(cOCP.listIndividuals.get(ind));
    }

    public void linkFaxStoreNumber(String number) {
        if (elementIsDisplayed(cOCP.checkBoxFax)) {
            browserUtils.get().hover(cOCP.checkBoxFax);
            clickIfElementIsDisplayed(cOCP.checkBoxFax);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.faxDestination);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.presentationOpt);
            cOCP.faxNumber.sendKeys(number);
            browserUtils.get().waitFor(2);
            Assert.assertTrue(cOCP.faxNumber.getAttribute("value").equals(number));
        }
    }

    public void linkEmailStoreNumber(String email) {
        if (elementIsDisplayed(cOCP.checkBoxEmail)) {
            browserUtils.get().hover(cOCP.checkBoxEmail);
            clickIfElementIsDisplayed(cOCP.checkBoxEmail);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.emailDestination);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.presentationOpt);
            cOCP.emailAddress.sendKeys(email);
            browserUtils.get().waitFor(2);
            Assert.assertTrue(cOCP.emailAddress.getAttribute("value").equals(email));
        }
    }

    public Map<String, String> linkEmailAndAdditionalInfoStoreMap(String streetAddress, String streetAdditionalLine1) {
        Map<String, String> mailMap = new HashMap<String, String>();
        if (elementIsDisplayed(cOCP.checkBoxMail)) {
            String[] fullName = getTextIfElementIsDisplayed(cOCP.fullName).split(" ");
            String firstName = fullName[0];
            String lastName = fullName[1];
            String caseID = getTextIfElementIsDisplayed(cOCP.caseID);
            String role = getTextIfElementIsDisplayed(cOCP.role);
            hoverToSpecificIndividual(1);
            browserUtils.get().hover(cOCP.checkBoxMail);
            clickIfElementIsDisplayed(cOCP.checkBoxMail);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.mailDestination);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.presentationOpt);
            browserUtils.get().waitFor(2);
            clickIfElementIsDisplayed(cOCP.consumerAddress1);
            cOCP.consumerAddress1.sendKeys(streetAddress);

            browserUtils.get().waitFor(2);
            String address1 = cOCP.consumerAddress1.getAttribute("value");
            clickIfElementIsDisplayed(cOCP.consumerAddress2);
            cOCP.consumerAddress2.sendKeys(streetAdditionalLine1);

            browserUtils.get().waitFor(2);
            String address2 = cOCP.consumerAddress2.getAttribute("value");
            clickIfElementIsDisplayed(cOCP.consumerCity);
            clickIfElementIsDisplayed(cOCP.presentationOpt);

            browserUtils.get().waitFor(2);
            String city = getTextIfElementIsDisplayed(cOCP.consumerCity);
            clickIfElementIsDisplayed(cOCP.consumerState);
            clickIfElementIsDisplayed(cOCP.presentationOpt);

            browserUtils.get().waitFor(2);
            String state = getTextIfElementIsDisplayed(cOCP.consumerState);
            clickIfElementIsDisplayed(cOCP.zipCode);
            clickIfElementIsDisplayed(cOCP.presentationOpt);

            browserUtils.get().waitFor(2);
            String zipCode = getTextIfElementIsDisplayed(cOCP.zipCode);

            mailMap.put("Address1", address1);
            mailMap.put("address2", address2);
            mailMap.put("city", city);
            mailMap.put("state", state);
            mailMap.put("zipCode", zipCode);
            mailMap.put("role", role);
            mailMap.put("caseID", caseID);
            mailMap.put("firstName", firstName);
            mailMap.put("lastName", lastName);
        }
        return mailMap;
    }

    public void clickSaveButton() {
        if (elementIsDisplayed(cOCP.saveButton)) clickIfElementIsDisplayed(cOCP.saveButton);
        else if (elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]"))))
            clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")));
    }

    public void verifySuccessMessageVisible() {
        Assert.assertTrue(elementIsDisplayed(cOCP.successMessage));
        browserUtils.get().waitFor(2);
    }

    public void verifyUpdateSuccessMessageVisible() {
        Assert.assertTrue(elementIsDisplayed(cOCP.updateSuccessMessage));
        browserUtils.get().waitFor(2);
    }

    public String outboundCorrespondenceStatusSelect(String status) {
        browserUtils.get().hover(cDP.outboundCorrespondenceStatus);
        clickIfElementIsDisplayed(cDP.outboundCorrespondenceStatus);
        WebElement el = Driver.getDriver().findElement(By.xpath("//li[text()='" + status + "']"));
        browserUtils.get().hover(el);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(el);
        return getTextIfElementIsDisplayed(cDP.outboundCorrespondenceStatus);
    }

    public String firstOptCorrespondenceNotificationsStatusReason() {
        browserUtils.get().hover(cDP.correspondenceNotificationsStatusReasonEdit);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsStatusReasonEdit);
        browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cOCP.presentationOpt);
        browserUtils.get().waitFor(2);
        String reason = cDP.correspondenceNotificationsStatusReasonEdit.getAttribute("value");
        return reason;
    }

    public void clickCorrespondenceNotificationsCancelButton() {
        browserUtils.get().hover(cDP.correspondenceNotificationsCancelButton);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsCancelButton);
    }

    public void clickCorrespondenceNotificationsOpt(String option) {
        WebElement e = Driver.getDriver().findElement(By.xpath("//span[text()='" + option.toUpperCase() + "']"));
        browserUtils.get().hover(e);
        clickIfElementIsDisplayed(e);
    }

    public String correspondenceNotificationsStatusReasonTXT() {
        browserUtils.get().hover(cDP.correspondenceNotificationsStatusReason);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsStatusReason);
        String reason = getTextIfElementIsDisplayed(cDP.correspondenceNotificationsStatusReason);
        return reason;
    }

    public String correspondenceNotificationsDestinationTXT() {
        browserUtils.get().hover(cDP.correspondenceNotificationsDestination);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsDestination);
        String reason = getTextIfElementIsDisplayed(cDP.correspondenceNotificationsDestination);
        return reason;
    }

    public String outboundCorrespondenceStatusReasonSelect() {
        browserUtils.get().hover(cDP.outboundCorrespondenceStatusReason);
        clickIfElementIsDisplayed(cDP.outboundCorrespondenceStatusReason);
        clickIfElementIsDisplayed(cOCP.presentationOpt);
        return getTextIfElementIsDisplayed(cDP.outboundCorrespondenceStatusReason);
    }

    public void clickYesPopUpMessage() {
        //  browserUtils.get().hover(cDP.yesPopUpMessage);
        //  browserUtils.get().waitFor(2);
        clickIfElementIsDisplayed(cDP.yesPopUpMessage);
    }

    public void continuePopUpMessage() {
        browserUtils.get().hover(cDP.continuePopUpMessage);
        clickIfElementIsDisplayed(cDP.continuePopUpMessage);
    }

    public void clickCorrespondenceEditButton() {
        browserUtils.get().hover(cDP.correspondenceEditButton);
        clickIfElementIsDisplayed(cDP.correspondenceEditButton);
    }

    public void verifyCorrespondenceDetailsHeader() {
        browserUtils.get().hover(cDP.correspondenceDetailsHeader);
        Assert.assertTrue(elementIsDisplayed(cDP.correspondenceDetailsHeader));
    }

    public String correspondenceDetailsCreatedByID() {
        browserUtils.get().hover(cDP.createdByID);
        String createdByID = getTextIfElementIsDisplayed(cDP.createdByID);
        return createdByID;
    }

    public String correspondenceDetailsType() {
        browserUtils.get().hover(cDP.correspondenceType);
        String correspondenceType = getTextIfElementIsDisplayed(cDP.correspondenceType);
        return correspondenceType;
    }

    public String correspondenceDetailsStatus() {
        browserUtils.get().hover(cDP.correspondenceStatus);
        String correspondenceStatus = getTextIfElementIsDisplayed(cDP.correspondenceStatus);
        return correspondenceStatus;
    }

    public String correspondenceDetailsLanguage() {
        browserUtils.get().hover(cDP.correspondenceLanguage);
        String correspondenceLanguage = getTextIfElementIsDisplayed(cDP.correspondenceLanguage);
        return correspondenceLanguage;
    }

    public String correspondenceDetailsCaseID() {
        browserUtils.get().hover(cDP.correspondenceCaseID);
        String correspondenceCaseID = getTextIfElementIsDisplayed(cDP.correspondenceCaseID);
        return correspondenceCaseID;
    }

    public void selectOutboundCorresPageNum(String num) {
        WebElement e = Driver.getDriver().findElement(By.xpath("//h5[text()='OUTBOUND CORRESPONDENCES']/following::div/ul/li/a[@aria-label='Go to page number " + num + "']"));
        clickIfElementIsDisplayed(e);
    }

    public String correspondenceNotificationsResendChannelField() {
        browserUtils.get().hover(cDP.correspondenceNotificationsResendChannelField);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsResendChannelField);
        clickIfElementIsDisplayed(cOCP.presentationOpt);
        String resendChannelField = cDP.correspondenceNotificationsResendChannelField.getAttribute("value");
        return resendChannelField;
    }

    public String correspondenceNotificationsResendDestinationField() {
        browserUtils.get().hover(cDP.correspondenceNotificationsResendDestinationField);
        clickIfElementIsDisplayed(cDP.correspondenceNotificationsResendDestinationField);
        clickIfElementIsDisplayed(cOCP.presentationOpt);
        String resendDestinationField = cDP.correspondenceNotificationsResendDestinationField.getAttribute("value");
        return resendDestinationField;
    }

    public void verifyCorrespondenceNotificationsStatusResendHeader() {
        browserUtils.get().hover(cDP.correspondenceNotificationsStatusResendHeader);
        Assert.assertTrue(elementIsDisplayed(cDP.correspondenceNotificationsStatusResendHeader));
    }

    public Map<String, String> updateCIDNotificationByStatus(String status) {
        Map<String, String> infoUI = new HashMap<>();
        String caseIDUI, statusUI, typeUI, createdByIDUI, languageUI, statusReason;
        for (int i = 0; i <= 4; i++) {
            browserUtils.get().hover(caseAndContactDetailsPage.correspondenceIDs.get(i));
            clickIfElementIsDisplayed(caseAndContactDetailsPage.correspondenceIDs.get(i));
            verifyCorrespondenceDetailsHeader();
            statusUI = correspondenceDetailsStatus();
            if (statusUI.compareTo(status) == 0) {
                clickIfElementIsDisplayed(cDP.correspondenceHeader);
                caseAndContactDetailsPage.navigateToTaskAndServiceRequestLink();
                inboundStepDefs.get().navigateToCaseContactDetailsScreen();
                browserUtils.get().waitFor(2);
                continue;
            } else {
                caseIDUI = correspondenceDetailsCaseID();
                typeUI = correspondenceDetailsType();
                createdByIDUI = correspondenceDetailsCreatedByID();
                languageUI = correspondenceDetailsLanguage();
                clickCorrespondenceEditButton();
                statusUI = outboundCorrespondenceStatusSelect(status);
                statusReason = outboundCorrespondenceStatusReasonSelect();
                clickSaveButton();
                clickYesPopUpMessage();
                infoUI.put("caseIDUI", caseIDUI);
                infoUI.put("typeUI", typeUI);
                infoUI.put("createdByIDUI", createdByIDUI);
                infoUI.put("languageUI", languageUI);
                infoUI.put("statusUI", statusUI);
                infoUI.put("statusReason", statusReason);
                break;
            }
        }
        return infoUI;
    }

    public List<String> saveNotificationByStatus(String option) {
        List<String> infoUI = new ArrayList<>();
        String notificationsDestinationUI[];
        String statusUI, notificationsStatusUI, statusReason, notificationsChannelUI;
        for (int i = 0; i <= 4; i++) {
            browserUtils.get().hover(caseAndContactDetailsPage.listMailCIDButtons.get(i));
            clickIfElementIsDisplayed(caseAndContactDetailsPage.listMailCIDButtons.get(i));
            verifyCorrespondenceDetailsHeader();
            browserUtils.get().hover(cDP.correspondenceNotificationsScreen);
            statusUI = cDP.correspondenceNotificationsStatus.getText();
            if (statusUI.compareToIgnoreCase("Canceled") == 0) {
                clickIfElementIsDisplayed(cDP.correspondenceHeader);
                browserUtils.get().waitFor(2);
                if (caseAndContactDetailsPage.activePageNum.getText().compareTo("1") != 0) {
                    selectOutboundCorresPageNum("1");
                }
                continue;
            } else {
                notificationsChannelUI = getTextIfElementIsDisplayed(cDP.correspondenceNotificationsChannel);
                notificationsDestinationUI = correspondenceNotificationsDestinationTXT().replace(",", " ").split(" ");
                if (option.compareToIgnoreCase("cancel") == 0) {
                    clickCorrespondenceNotificationsCancelButton();
                    clickCorrespondenceNotificationsOpt(option);
                    notificationsStatusUI = cDP.correspondenceNotificationsStatus.getText();
                    statusReason = firstOptCorrespondenceNotificationsStatusReason();
                    clickSaveButton();
                    clickYesPopUpMessage();
                    for (i = 0; i <= notificationsDestinationUI.length; i++) {
                        infoUI.add(notificationsDestinationUI[i]);
                    }
                    infoUI.add(notificationsChannelUI);
                    infoUI.add(statusReason);
                    infoUI.add(notificationsStatusUI);
                    break;
                } else if (option.compareToIgnoreCase("resend") == 0) {
                    verifyCorrespondenceNotificationsStatusResendHeader();
                    String channel = correspondenceNotificationsResendChannelField();
                    String destination = correspondenceNotificationsResendDestinationField();
                    clickSaveButton();
                    clickYesPopUpMessage();
                    infoUI.add(channel);
                    infoUI.add(destination);
                }
            }
        }
        return infoUI;
    }

    public void clickIfElementIsDisplayed(WebElement el) {
        try {
            BrowserUtils.waitFor(5);
            if (el.isDisplayed()) {
                browserUtils.get().hover(el);
                el.click();
            }
        } catch (NoSuchElementException e) {
        }
    }

    public void sendKeysToAnElement(WebElement e, String st) {
        clickIfElementIsDisplayed(e);
        e.sendKeys(st);
    }

    public boolean elementIsDisplayed(WebElement el) {
        try {
            if (el.isDisplayed()) {
                browserUtils.get().hover(el);
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTextIfElementIsDisplayed(WebElement el) {
        try {
            if (elementIsDisplayed(el)) {
                return el.getText();
            } else {
                return String.valueOf(false);
            }
        } catch (NoSuchElementException e) {
            return String.valueOf(false);
        }
    }

    public static String getAccessToken() throws IOException {
        String token = null;
        Response responseToken = given()
                .contentType("application/json")
                .contentType("application/x-www-form-urlencoded")
                .body("client_id=0GJtlsx4XTpNzAIRIFE82rwkVLqAqcS0" + "&client_secret=IlASqed8mbe7LT35")
                .post(ConfigurationReader.getProperty("apiDmsToken") + "/mars-ecms-oauth/token" + "?grant_type=client_credentials");
        if (responseToken.getStatusCode() == 200) {
            token = responseToken.jsonPath().get("access_token");
        }
        return token;
    }

    public static String getOAuthQA() throws IOException {
        String token = null;
        Response responseToken = given()
                .header("Authorization", "Basic d2ZUdTA5VTQ4SHNTWXY2ODhub0dVeG9adW5ESVRQV2M6b0pRQ1A3TVphRTBZWmNzZA==")
                .contentType("application/json")
                .contentType("application/x-www-form-urlencoded")
                .body("username=SVC_mars_user_1" + "&password=6Y88Uwcw")
                .post(ConfigurationReader.getProperty("apiOauth2") + "/client/accesstoken?grant_type=client_credentials");
        if (responseToken.getStatusCode() == 200) {
            token = responseToken.jsonPath().get("access_token");
        }
        return token;
    }

    public String searchQAEvents(String accessToken, String eventName) {
        String rawBody = "{\"eventName\": \"" + eventName + "\"}";
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiMarsEvent") + "?size=1&page=0&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

    public Map<String, String> searchQAEventsPayloadDates(String accessToken, String eventName) {
        JSONObject payloadObject;
        String rawBody = "{\"eventName\": \"" + eventName + "\"}";
        Map<String, String> searchQAEventsDatesPayload = new HashMap<String, String>();
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiMarsEvent") + "?size=1&page=0&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            if (responseBody.asString().contains("payload")) {
                searchQAEventsDatesPayload.put("createdOn", responseBody.jsonPath().get("eventsList.content.createdOn").toString());
                searchQAEventsDatesPayload.put("updatedOn", responseBody.jsonPath().get("eventsList.content.updatedOn").toString());
                payloadObject = new JSONObject(responseBody.jsonPath().get("eventsList.content.payload").toString().substring(1).replace("\\}\\}\\]", "}}"));
                searchQAEventsDatesPayload.put("dataObject", payloadObject.get("dataObject").toString());
                if (responseBody.asString().contains("eventCreatdOn")) {
                    searchQAEventsDatesPayload.put("eventCreatdOn", payloadObject.get("eventCreatdOn").toString());
                } else if (responseBody.asString().contains("eventCreatedOn")) {
                    searchQAEventsDatesPayload.put("eventCreatedOn", payloadObject.get("eventCreatedOn").toString());
                }
            }
        }
        return searchQAEventsDatesPayload;
    }

    public String createCorrespondence(String accessToken, String rawBody) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "BLCRM")
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

    public String searchQAEventsEventID(String accessToken, String eventName) {
        String raw = "{\"eventName\": \"" + eventName + "\"}";
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(raw)
                .post(ConfigurationReader.getProperty("apiMarsEvent") + "?size=1&page=0&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.jsonPath().get("eventsList.content.eventId").toString();
        }
        return responseString;
    }

    public String getEventSubscriberRecordId(String accessToken, String eventID) {
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .get(ConfigurationReader.getProperty("apiEventSubRecords") + "/12700/" + eventID);
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.jsonPath().get("eventSubscriberRecords.eventName").toString();
        }
        return responseString;
    }

    public String createCorrespondenceRecipientsID(String accessToken, String rawBody) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name",API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName())
                .header("Project-Id",API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId())
                .header("Content-Type", "application/json")
                .header("Authorization",accessToken)
                .contentType("application/json")
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences");
        System.out.println(responseBody.asString());
        if (responseBody.getStatusCode() == 201) {
            responseString = responseBody.jsonPath().get("data.id").toString();
            System.out.println(Hooks.nameAndTags.get()+"ob created cid - "+responseString);
        } else {
            Assert.fail("Status Code is not 201");
        }
        return responseString;
    }

    public String searchInboundTypeBarcode(String accessToken, String type) {
        String responseString = null;
        String rawBody = "{\n" + "\"name\": \"" + type + "\"\n" + "}";
        Response responseBody = given()
                .header("Authorization",accessToken)
                .contentType("application/json")
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiDMSCorrespondenceDefinition") + "/inboundCorrespondenceDefinition");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.jsonPath().get("inboundCorrespondence.barcode").toString();
        }
        return responseString;
    }

    public String getCorrespondence(String accessToken, String id) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "BLCRM")
                .contentType("application/json")
                .header("Authorization",accessToken)
                .get(ConfigurationReader.getProperty("apiDMSCorrespondence") + "/correspondences/" + id);
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.jsonPath().get("recipients.notifications.notificationId").toString();
        }
        return responseString;
    }

    public String getLetterDataByNID(String accessToken, String id) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "BLCRM")
                .contentType("application/json")
                .header("Authorization",accessToken)
                .get(ConfigurationReader.getProperty("apiECMSLetterData") + "/notifications" + "/" + id + "/letterdata");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.jsonPath().get("data.notificationDataObject.template.form").toString();
        }
        return responseString;
    }

    public String getLetterDataByNIDString(String accessToken, String id) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "BLCRM")
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .get(ConfigurationReader.getProperty("apiECMSLetterData") + "/notifications" + "/" + id + "/letterdata");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

    public String rawBodyCreateCorrespondence(String caseId, String MMSCode, String language, String channelType, String createdBy,
                                              String firstName, String lastName, String role, String streetAddress, String streetAddionalLine1, String city, String state, String zipcode) {
        String rawBody = "{ \"anchor\": {\"caseId\": " + caseId + ",\"projectId\": 6203,\"regardingConsumerId\": [\"7589\"] }, \"correspondenceDefinitionMMSCode\": \"" + MMSCode + "\"," +
                " \"id\": \"\",\"language\": \"" + language + "\",\"recipients\": [ { \"notifications\": [ { \"channelType\": \"" + channelType + "\", \"channelDefinitionId\": 1039," +
                " \"language\": \"English\", \"returnedDate\": null, \"template\": null, \"version\": null, \"createdBy\": " + createdBy + ", \"createdDatetime\": \"2020-04-08T13:47:21.057Z\" } ]," +
                " \"recipientInfo\": { \"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\", \"role\": \"" + role + "\", \"streetAddress\": \"" + streetAddress + "\", \"streetAddionalLine1\": \"" + streetAddionalLine1 + "\", \"city\": \"" + city + "\"," +
                "\"state\": \"" + state + "\", \"zipcode\": \"" + zipcode + "\", \"emailAddress\": \"\", \"faxNumber\": \"\", \"textNumber\": \"\" } }, { \"notifications\": [ {" +
                "\"channelType\": \"Email\", \"channelDefinitionId\": 1038, \"language\": \"English\", \"returnedDate\": null, \"template\": null, \"version\": null, \"createdBy\": 2132," +
                "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\" } ], \"recipientInfo\": { \"firstName\": \"Optimus\", \"lastName\": \"Prime\", \"role\": \"Primary Individual\"," +
                "\"emailAddress\": \"j32lkj2@kjadfs.com\", \"faxNumber\": \"\", \"textNumber\": \"\" } } ], \"requester\": { \"requesterId\": \"\", \"requesterType\": \"ConnectionPoint\" }," +
                "\"status\": \"\", \"createdBy\": 2132, \"statusMessage\": \"\", \"createdDatetime\": \"2020-04-08T13:47:21.057Z\" }";
        return rawBody;
    }

    public String rawBodyCreateCorrespondence1(String MMSCode, String channelType, String channelDefinitionId) {
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": 68,\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"" + MMSCode + "\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"" + channelType + "\",\n" +
                "\"channelDefinitionId\": " + channelDefinitionId + ",\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}";
        return st;
    }

    public String rawBodyCreateCorrespondenceWithCaseId(String caseId) {
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}";
        return st;
    }

    public String rawBodyCreateCorrespondenceWithBodyData(String bodySetName, String bodyItemName, String bodyItemValue, String bodySubsetName, String bodySubsetItemName, String bodySubsetItemValue) {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseId = caseConsumerId.get("caseId");
//        String caseId = "123";
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\",\n" + "\"bodyData\": {\n" +
                "  \"bodySetName\": \"" + bodySetName + "\",\n" + "  \"bodyItems\": [{\n" + "    \"bodyItemName\": \"" + bodyItemName + "\",\n" +
                "    \"bodyItemValue\": \"" + bodyItemValue + "\",\n" + "    \"bodySubsets\": [{\n" + "      \"bodySubsetName\": \"" + bodySubsetName + "\",\n" + "      \"bodySubsetItems\": [{\n" +
                "        \"bodySubsetItemName\": \"" + bodySubsetItemName + "\",\n" + "        \"bodySubsetItemValue\": \"" + bodySubsetItemValue + "\"\n" + "      }]\n" + "    }]\n" + "  }]\n" + "}" + "}";
        System.out.println("printing request with body data \n" + st);
        return st;
    }

    public String rawBodyCreateCorrespondenceWithBodyDataRecipNull(String bodySetName, String bodyItemName, String bodyItemValue, String bodySubsetName, String bodySubsetItemName, String bodySubsetItemValue) {
        String caseId =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRandomCaseId().get("caseId");
        System.out.println("case id - " + caseId);
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\",\n" + "\"bodyData\": {\n" +
                "  \"bodySetName\": \"" + bodySetName + "\",\n" + "  \"bodyItems\": [{\n" + "    \"bodyItemName\": \"" + bodyItemName + "\",\n" +
                "    \"bodyItemValue\": \"" + bodyItemValue + "\",\n" + "    \"bodySubsets\": [{\n" + "      \"bodySubsetName\": \"" + bodySubsetName + "\",\n" + "      \"bodySubsetItems\": [{\n" +
                "        \"bodySubsetItemName\": \"" + bodySubsetItemName + "\",\n" + "        \"bodySubsetItemValue\": \"" + bodySubsetItemValue + "\"\n" + "      }]\n" + "    }]\n" + "  }]\n" + "}" + "}";
        System.out.println("request - \n"+st);
        return st;
    }

    public String rawBodyCreateCorrespondenceWithCIDConsumerId(String caseId, String ConsumerId) {
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"" + ConsumerId + "\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}";
        return st;
    }

    public int getIntCaseID() {
        return Integer.parseInt(getTextIfElementIsDisplayed(cDP.caseIdFirstRecord));
    }

    public String searchQAEventsRightPayload(String accessToken, String eventName, int caseID) {
        String rawBody = "{\"eventName\": \"" + eventName + "\"}";
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .header("Project-Name", "BLCRM")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiMarsEvent") + "?size=200&page=0&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            ArrayList<String> payloads = responseBody.jsonPath().get("eventsList.content.payload");
            for (int i = 0; i <= payloads.size() - 1; i++) {
                if (payloads.get(i).contains("\"caseId\":" + caseID + ",")) {
                    responseString = payloads.get(i);
                    break;
                }
            }
        } else Assert.fail("Status code is not 200");
        if (responseString == null)
            Assert.fail("could not find payload for case_save_event with " + caseID + " CaseId");
        return responseString;
    }

    public String searchQAEventsRightPayloadNJ(String accessToken, String eventName, int caseID) {
        String rawBody = "{\"eventName\": \"" + eventName + "\"}";
        String responseString = null;
        Response responseBody = given()
                .contentType("application/json")
                .header("Project-Name", "NJ-SBE")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiMarsEvent") + "?size=1000&page=0&sort=eventId,desc");
        if (responseBody.getStatusCode() == 200) {
            ArrayList<String> payloads = responseBody.jsonPath().get("eventsList.content.payload");
            for (int i = 0; i <= payloads.size() - 1; i++) {
                if (payloads.get(i).contains("\"caseId\":" + caseID + ",")) {
                    responseString = payloads.get(i);
                    break;
                }
            }
        } else Assert.fail("Status code is not 200");
        if (responseString == null)
            Assert.fail("could not find the payload for case_save_event with " + caseID + " CaseId");
        return responseString;
    }

    public Map<String, String> convertPayloadToMap(String payload) {
        JSONObject payloadObject;
        Map<String, String> searchQAEventsDatesPayload = new HashMap<String, String>();
        payloadObject = new JSONObject(payload);
        searchQAEventsDatesPayload.put("projectId", payloadObject.get("projectId").toString());
        searchQAEventsDatesPayload.put("projectName", payloadObject.get("projectName").toString());
        searchQAEventsDatesPayload.put("action", payloadObject.get("action").toString());
        searchQAEventsDatesPayload.put("recordType", payloadObject.get("recordType").toString());
        searchQAEventsDatesPayload.put("eventCreatedOn", payloadObject.get("eventCreatedOn").toString());
        JSONObject payloadDataObject = new JSONObject(payloadObject.get("dataObject").toString());
        searchQAEventsDatesPayload.put("createdBy", payloadDataObject.get("createdBy").toString());
        searchQAEventsDatesPayload.put("effectiveEndDate", payloadDataObject.get("effectiveEndDate").toString());
        searchQAEventsDatesPayload.put("updatedBy", payloadDataObject.get("updatedBy").toString());
        JSONObject caseIdentificationNumberObject = new JSONObject(payloadDataObject.get("caseIdentificationNumber").toString().replace("[", "").replace("]", ""));
        searchQAEventsDatesPayload.put("caseIdentificationNumberId", caseIdentificationNumberObject.get("caseIdentificationNumberId").toString());
        searchQAEventsDatesPayload.put("caseIdentificationNumberTypeReportLabel", caseIdentificationNumberObject.get("caseIdentificationNumberTypeReportLabel").toString());
        searchQAEventsDatesPayload.put("externalCaseId", caseIdentificationNumberObject.get("externalCaseId").toString());
        searchQAEventsDatesPayload.put("updatedBy", caseIdentificationNumberObject.get("updatedBy").toString());
        searchQAEventsDatesPayload.put("caseIdentificationNumberTypeId", caseIdentificationNumberObject.get("caseIdentificationNumberTypeId").toString());
        searchQAEventsDatesPayload.put("createdBy", caseIdentificationNumberObject.get("createdBy").toString());
        searchQAEventsDatesPayload.put("caseId", caseIdentificationNumberObject.get("caseId").toString());
        searchQAEventsDatesPayload.put("effectiveStartDate", caseIdentificationNumberObject.get("effectiveStartDate").toString());
        searchQAEventsDatesPayload.put("updatedOn", caseIdentificationNumberObject.get("updatedOn").toString());
        searchQAEventsDatesPayload.put("createdOn", caseIdentificationNumberObject.get("createdOn").toString());
        searchQAEventsDatesPayload.put("agencyName", caseIdentificationNumberObject.get("agencyName").toString());

        return searchQAEventsDatesPayload;
    }

    public String rawBodyCreateCorrespondenceWithFirstNameAndCaseId(String frstName, String caseId) {
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"AGkPhv42ph\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"" + frstName + "\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}";
        return st;
    }

    public String rawBodyCreateCorrespondenceWithFirstNameAndConsumerId(String frstName, String consumerId) {
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": 68,\n" + "\"regardingConsumerId\": [\n" +
                "\"" + consumerId + "\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"AGkPhv42ph\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"" + frstName + "\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}";
        return st;
    }

    public String getNetworkLocationByID(String accessToken, String id) {
        String responseString = null;
        Response responseBody = given()
                .auth().preemptive().oauth2(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal()).header("Project-Name",API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectName())
                .get(ConfigurationReader.getProperty("apiNetworkLocation") + "/" + String.valueOf(World.generalSave.get().get("userId")) + "/" + id);
        responseString = responseBody.prettyPrint();
        return responseString;
    }

    public String rawBodyCreateCorrespondenceWithBodyDataNobodySubsetItems(String bodySetName, String bodyItemName, String bodyItemValue, String bodySubsetName) {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
//        String caseId = "43345";
        String caseId = caseConsumerId.get("caseId");
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\",\n" + "\"bodyData\": {\n" +
                "  \"bodySetName\": \"" + bodySetName + "\",\n" + "  \"bodyItems\": [{\n" + "    \"bodyItemName\": \"" + bodyItemName + "\",\n" +
                "    \"bodyItemValue\": \"" + bodyItemValue + "\",\n" + "    \"bodySubsets\": [{\n" + "      \"bodySubsetName\": \"" + bodySubsetName + "\"\n" + "    }]\n" + "  }]\n" + "}" + "}";
        return st;
    }

    public String rawBodyCreateCorrespondenceWithBodyDataNoBodySubsets(String bodySetName, String bodyItemName, String bodyItemValue) {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
//        String caseId = "234";
        String caseId = caseConsumerId.get("caseId");
        String st = "{\n" + "\"anchor\": {\n" + "\"caseId\": " + caseId + ",\n" + "\"regardingConsumerId\": [\n" +
                "\"287\"\n" + "]\n" + "},\n" + "\"correspondenceDefinitionMMSCode\": \"allChCC\",\n" + "\"id\": \"\",\n" +
                "\"language\": \"English\",\n" + "\"recipients\": [\n" + "{\n" + "\"notifications\": [\n" + "{\n" + "\"channelType\": \"Text\",\n" +
                "\"channelDefinitionId\": 388,\n" + "\"language\": \"English\",\n" + "\"returnedDate\": null,\n" + "\"template\": null,\n" +
                "\"version\": null,\n" + "\"createdBy\": 2132,\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\"\n" + "}\n" + "],\n" + "\"recipientInfo\": {\n" +
                "\"firstName\": \"Many\",\n" + "\"lastName\": \"Correspondence\",\n" + "\"role\": \"Primary Individual\",\n" + "\"emailAddress\": \"test@test.com\",\n" +
                "\"faxNumber\": \"3453523443\",\n" + "\"textNumber\": \"3453523443\"\n" + "}\n" + "}\n" + "],\n" + "\"requester\": {\n" + "\"requesterId\": \"2425\",\n" +
                "\"requesterType\": \"ConnectionPoint\"\n" + "},\n" + "\"status\": \"\",\n" + "\"createdBy\": 2425,\n" + "\"statusMessage\": \"\",\n" + "\"createdDatetime\": \"2020-04-08T13:47:21.057Z\",\n" + "\"bodyData\": {\n" +
                "  \"bodySetName\": \"" + bodySetName + "\",\n" + "  \"bodyItems\": [{\n" + "    \"bodyItemName\": \"" + bodyItemName + "\",\n" +
                "    \"bodyItemValue\": \"" + bodyItemValue + "\"\n" + "  }]\n" + "}" + "}";
        return st;
    }

    public String rawBodyUpdateNotificationStatus(String status) {
        String st = "{\n" +
                "    \"changedBy\": \"2492\",\n" +
                "    \"returnDate\": null,\n" +
                "    \"status\": \"" + status + "\",\n" +
                "    \"statusReason\": \"set by postman\",\n" +
                "    \"errorDetail\": \"errorDetails\"\n" +
                "}";
        return st;
    }

    public String updateNotificationStatus(String accessToken, String nID, String rawBody) {
        String responseString = null;
        Response responseBody = given()
                .header("Project-Name", "BLCRM")
                .header("projectId", "6203")
                .header("Authorization", accessToken)
                .contentType("application/json")
                .auth().preemptive().oauth2(accessToken)
                .body(rawBody)
                .post(ConfigurationReader.getProperty("apiECMSLetterData") + "/notifications" + "/" + nID + "/statuses");
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

}
