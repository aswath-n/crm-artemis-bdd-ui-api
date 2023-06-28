package com.maersk.dms.step_definitions;

import com.github.javafaker.Faker;
import com.google.common.collect.ArrayListMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMSRViewEditPage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.AugmentOutboundCorrespondenceSettingsPage;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.OutboundCorrespondenceSettingsPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.*;

import static org.testng.Assert.assertTrue;


public class InboundCorrespondenceStepDefs extends CRMUtilities implements ApiBase {
    //    private static ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(Driver::getDriver);
    InboundCorrespondencePage inbound = new InboundCorrespondencePage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    final ThreadLocal<ViewOutboundCorrespondenceStepDefs> viewOutboundCorrespondenceStepDefs = ThreadLocal.withInitial(ViewOutboundCorrespondenceStepDefs::new);
    private ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    private CRMCreateGeneralTaskPage crmCreateGeneralTaskPage = new CRMCreateGeneralTaskPage();
    final ThreadLocal<Map<String, String>> newConsumer = new ThreadLocal<>();
    //private APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
    WebDriverWait wait;
    Actions actions;
    private final ThreadLocal<JsonObject> inbRequest = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<String> expected_editedon = ThreadLocal.withInitial(String::new);
    private AugmentOutboundCorrespondenceSettingsPage correspondenceSettingsPage = new AugmentOutboundCorrespondenceSettingsPage();
    public static final ThreadLocal<Map<String, Object>> localGeneralSave = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<Map<String, Object>> localRandomMap = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<String> date = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<Map<String, String>> updatedValues = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<String> taskIdFromLinks = ThreadLocal.withInitial(String::new);
    private CRMSRViewEditPage crmSRViewEditPage = new CRMSRViewEditPage();

    public void selectInboundContactType(String type) {
        uiAutoUitilities.get().selectDropDown(inbound.contactType, type);
    }

    public void selectInboundChannelType(String channel) {
        switch (channel) {
            case "Email":
                uiAutoUitilities.get().selectDropDown(inbound.channelType, "Email");
                inbound.channelInput.sendKeys(newConsumer.get().get("name") + "@" + uiAutoUitilities.get().getRandomString(5) + ".com");
                break;
            case "Phone":
                uiAutoUitilities.get().selectDropDown(inbound.channelType, "Phone");
                uiAutoUitilities.get().selectDropDown(inbound.inboundCallQueue, "General Program Questions");
                inbound.channelInput.sendKeys(newConsumer.get().get("phone"));
                break;
            case "SMS Text":
                uiAutoUitilities.get().selectDropDown(inbound.channelType, "SMS Text");
                inbound.channelInput.sendKeys(newConsumer.get().get("phone"));
                break;
            case "Web Chat":
                uiAutoUitilities.get().selectDropDown(inbound.channelType, "Web Chat");
                inbound.channelInput.sendKeys(newConsumer.get().get("name") + "@" + uiAutoUitilities.get().getRandomString(5) + ".com");
                break;
        }

    }

    public void selectProgramType(String abc) {
        switch (abc.toLowerCase()) {
            case "a":
                uiAutoUitilities.get().selectDropDown(inbound.programABC, "Program A");
                break;
            case "b":
                uiAutoUitilities.get().selectDropDown(inbound.programABC, "Program B");
                break;
            case "c":
                uiAutoUitilities.get().selectDropDown(inbound.programABC, "Program C");
                break;
        }
    }

    public void selectDisposition(String result) {
        switch (result) {
            case "Complete":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program A");
                break;
            case "Dropped":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program B");
                break;
            case "Escalate":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program C");
                break;
            case "Outbound Incomplete":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program A");
                break;
            case "Requested Call Back":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program B");
                break;
            case "Transfer":
                uiAutoUitilities.get().selectDropDown(inbound.contactDisposition, "Program C");
                break;
        }
    }

    public void endContact() {
        waitForVisibility(inbound.endContactButton, 7);
        inbound.endContactButton.click();
    }

    public void saveInboundCorrespondence() {
        waitForVisibility(inbound.contractDetailsSaveButton, 7);
        inbound.contractDetailsSaveButton.click();
    }

    public void verifyCidHyperLink() {
        waitForVisibility(inbound.inboundCIDValues.get(0), 7);
        assertTrue(inbound.inboundCIDValues.get(0).isDisplayed());
        assertTrue(inbound.inboundCIDValues.get(0).isEnabled());
    }

    public void consumerTypeResultVisible() {
        for (WebElement e : inbound.inboundTypeValues) {
            assertTrue(e.getText().length() > 0);
        }
    }

    public void consumerResultVisible() {
        for (WebElement e : inbound.inboundRecipientValues) {
            assertTrue(e.getText().length() > 0);
        }
    }

    public void verifyFiveMostRecent() {
        scrollDownUsingActions(5);
        List<Date> list = uiAutoUitilities.get().getDatesFromWebElements(inbound.inboundDateReceivedValues);
        assertTrue(uiAutoUitilities.get().verifyDatesDescendingorder(list));
    }

    public void verifyDateFormat() {
        for (WebElement e : inbound.dateFormatResult) {
            assertTrue(e.getText().matches("[0-1][0-9]/[0-3][0-9]/[0-9]{4}"));
        }
    }

    public void navigateToCaseContactDetailsScreen() {
        waitForVisibility(inbound.caseContactDetailsTab, 7);
        inbound.caseContactDetailsTab.click();
        waitFor(20);
    }

    public void selectcontactOutcome(String outcome) {
        switch (outcome) {
            case "Did Not Reach/Left Voicemail":
                uiAutoUitilities.get().selectDropDown(inbound.contactOutcome, "Did Not Reach/Left Voicemail");
                break;
            case "Did Not Reach/No Voicemail":
                uiAutoUitilities.get().selectDropDown(inbound.contactOutcome, "Program B");
                break;
            case "Invalid Phone Number":
                uiAutoUitilities.get().selectDropDown(inbound.contactOutcome, "Program C");
                break;
            case "Reached Successfully":
                uiAutoUitilities.get().selectDropDown(inbound.contactOutcome, "Program C");
                break;
        }
    }

    public void consumerFirstName(String firstName) {
        String newConsumer;
        switch (firstName) {
            case "Guy":
                newConsumer = "Guy";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
            case "InboundCorres":
                newConsumer = "InboundCorres";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
            case "Corres":
                newConsumer = "Corres";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
            case "JoeEcms":
                newConsumer = "JoeEcms";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
            case "Perftest":
                newConsumer = "Perftest";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
            case "CrazyCase":
                newConsumer = "CrazyCase";
                fillTheFiled(inbound.consumerFirstName, newConsumer);
                assertTrue(newConsumer.equalsIgnoreCase(inbound.consumerFirstName.getAttribute("value")), "Consumer first name is not correct");
                break;
        }
    }

    public void multipleCaseMembers() throws AWTException {
        actions = new Actions(Driver.getDriver());
        actions.moveToElement(inbound.caseAndContactDetailsInboundCorrespondencesHeader);
        hover(inbound.caseAndContactDetailsInboundCorrespondencesHeader);
        waitForPageToLoad(10);
        Robot robot = new Robot();
        int z = 8;
        while (z > 0) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            z -= 1;
        }
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan((By.xpath("(//tbody[@class='MuiTableBody-root'])[3]/tr/td[5]/a")), 3));
        wait.until(ExpectedConditions.visibilityOf(inbound.inboundMultipleConsumerResults));
        List<WebElement> results = Driver.getDriver().findElements(By.xpath("(//tbody[@class='MuiTableBody-root'])[3]/tr/td[5]/a"));
        assertTrue(results.size() > 3);
        assertTrue(inbound.inboundMultipleConsumerResults.isDisplayed());
    }

    public void verifyOnbase() {
        waitForPageToLoad(10);
        waitForVisibility(inbound.onbaseCid, 7);
        assertTrue(inbound.onbaseCid.isDisplayed());
    }

    public void linkContactByFirstName(String firstName) throws AWTException {
        wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        try {
            waitFor(5);
            Robot robot = new Robot();
            int z = 4;
            while (z > 0) {
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                z -= 1;
            }
            waitForVisibility(inbound.firstContactBtn, 20);
            uiAutoUitilities.get().clickJS(inbound.firstContactBtn);
            hover(inbound.contactDetailsHeader);
            uiAutoUitilities.get().clickJS(inbound.nameConsumer1);
            uiAutoUitilities.get().clickJS(inbound.dobConsumer1);
            uiAutoUitilities.get().clickJS(inbound.crmConsumerID1);
            uiAutoUitilities.get().clickJS(inbound.linkRecordBtn);
        } catch (AWTException | NoSuchElementException | StaleElementReferenceException z) {
            System.out.println("deux");
            waitFor(2);
            uiAutoUitilities.get().clickJS(inbound.firstContactBtn);
            hover(inbound.contactDetailsHeader);
            uiAutoUitilities.get().clickJS(inbound.nameConsumer1);
            uiAutoUitilities.get().clickJS(inbound.dobConsumer1);
            uiAutoUitilities.get().clickJS(inbound.crmConsumerID1);
            uiAutoUitilities.get().clickJS(inbound.linkRecordBtn);
        }
    }

    public void searchCorrespondenceApiWasCalled() {
        //search event logs to check if proper api was called
        String api = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/search/correspondence";
        Assert.assertTrue(EventsUtilities.getRawLogs(api).size() > 0);
    }

    public void verifyCaseLinkedToInbDocument(String caseId, String cid) {
        System.out.println("Verifying Links ");
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        } else if ("InboundDocumentIdDigital".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentIdDigital").toString();
        }
        if ("RANDOM".equalsIgnoreCase(caseId)) {
            caseId = World.generalSave.get().get("CASE").toString();
            if (caseId.charAt(0) == '0')
                caseId = caseId.substring(1, caseId.length());
        } else if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseId = caseConsumerId.get("caseId");
        }
        boolean found = false;
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Case")) {
                found = true;
                Assert.assertEquals(link.get("id").toString(), caseId, "case id expected - " + caseId + ", case id found - " + link.get("id").toString());
                Assert.assertEquals(link.get("internalId").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
                Assert.assertEquals("Case", link.get("name"));
                Assert.assertEquals("CASE", link.get("externalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertEquals(link.get("createdOn"), link.get("updatedOn"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
                Assert.assertEquals(link.get("createdOn").toString().substring(0, 11), link.get("effectiveStartDate").toString().substring(0, 11));
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public void verifyInbDocmentLinkedToCase(String cid, String caseId) {
        System.out.println("Verifying Links ");
        if ("RANDOM".equalsIgnoreCase(caseId)) {
            caseId = World.generalSave.get().get("CASE").toString();
            if (caseId.charAt(0) == '0')
                caseId = caseId.substring(1, caseId.length());
        } else if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseId = caseConsumerId.get("caseId");
        }
        if ("ApplicationCaseId".equalsIgnoreCase(caseId)) {
            caseId = World.generalSave.get().get("ApplicationCaseId").toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        } else if ("InboundDocumentIdDigital".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentIdDigital").toString();
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCaseLinks(caseId);
        boolean found = false;
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Inbound Correspondence")) {
                found = true;
                Assert.assertEquals(link.get("id").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
                Assert.assertEquals(link.get("internalId").toString(), caseId, "case id expected - " + caseId + ", case id found - " + link.get("id").toString());
                Assert.assertEquals("Inbound Correspondence", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("externalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertEquals(link.get("createdOn"), link.get("updatedOn"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
                Assert.assertEquals(link.get("createdOn").toString().substring(0, 11), link.get("effectiveStartDate").toString().substring(0, 11));
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public void verifyInboundLinkedToConsumer(String cid, String consumerId) {
        System.out.println("Verifying Links ");
        if ("RANDOM".equalsIgnoreCase(consumerId)) {
            consumerId = World.generalSave.get().get("CONSUMER").toString();
        } else if ("previouslyCreated".equalsIgnoreCase(consumerId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            consumerId = caseConsumerId.get("consumerId");
        }

        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        } else if ("InboundDocumentIdDigital".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentIdDigital").toString();
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getConsumerLinks(consumerId);
        boolean found = false;
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Inbound Correspondence")) {
                found = true;
                Assert.assertEquals(link.get("id").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
                Assert.assertEquals(link.get("internalId").toString(), consumerId, "consumer id expected - " + consumerId + ", consumer id found - " + link.get("id").toString());
                Assert.assertEquals("Inbound Correspondence", link.get("name"));
                Assert.assertEquals("INBOUND_CORRESPONDENCE", link.get("externalRefType"));
                Assert.assertEquals(link.get("createdBy"), link.get("updatedBy"));
                Assert.assertEquals(link.get("createdOn"), link.get("updatedOn"));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("effectiveStartDate"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("createdOn"))));
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(link.get("updatedOn"))));
                Assert.assertEquals(link.get("createdOn").toString().substring(0, 11), link.get("effectiveStartDate").toString().substring(0, 11));
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public void verifyConsumerLinkedToInboundDocument(String consumerId, String cid) {
        System.out.println("Verifying Links ");
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        if ("RANDOM".equalsIgnoreCase(consumerId)) {
            consumerId = World.generalSave.get().get("CONSUMER").toString();
            if (consumerId.charAt(0) == '0')
                consumerId = consumerId.substring(1, consumerId.length());
        } else if ("previouslyCreated".equalsIgnoreCase(consumerId)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            consumerId = caseConsumerId.get("consumerId");
        }
        boolean found = false;
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Consumer Profile")) {
                found = true;
                Assert.assertEquals(link.get("id").toString(), consumerId, "consumer id expected - " + consumerId + ", consumer id found - " + link.get("id").toString());
                Assert.assertEquals(link.get("internalId").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public void verifyInbLinkedToOutboundDocument(String nid, String cid) {
        JsonPath inboundLinks = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        List<Map<String, Object>> inbLinks = inboundLinks.getList("externalLinkDetails.content");
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = World.generalSave.get().get("NOTIFICATIONID").toString();
        }
        JsonPath tempNotif = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutbCIDFromNID(nid);
        List<Map<String, Object>> notifications = tempNotif.getList("data.content");
        String outboundCID = notifications.get(0).get("id").toString();
        boolean found = false;
        for (Map<String, Object> inbLink : inbLinks) {
            if ("Outbound Correspondence".equalsIgnoreCase(inbLink.get("name").toString())) {
                found = true;
                Assert.assertEquals(inbLink.get("id").toString(), outboundCID);
                Assert.assertEquals(inbLink.get("internalId").toString(), cid);
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public void verifyInbLinkedToOutboundDocumentUI(String inbCid, String nid) {
        if ("previouslyCreated".equalsIgnoreCase(inbCid) || "fromRequest".equalsIgnoreCase(inbCid)) {
            inbCid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(nid) || "fromRequest".equalsIgnoreCase(nid)) {
            nid = World.generalSave.get().get("NOTIFICATIONID").toString();
        }
        JsonPath tempNotif = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutbCIDFromNID(nid);
        List<Map<String, Object>> notifications = tempNotif.getList("data.content");
        String outboundCID = notifications.get(0).get("id").toString();
        waitFor(4);
        waitForPageToLoad(10);
        if ("Outbound Correspondence".equalsIgnoreCase(inbound.firstLink.get(2).getText().trim())) {
            Assert.assertTrue(inbound.cid.isDisplayed());
            Assert.assertTrue(inbound.cid.getText().trim().equalsIgnoreCase(inbCid));
            scrollToElement(inbound.firstLink.get(2));
            waitFor(1);
            Assert.assertTrue(inbound.firstLink.get(2).getText().trim().equalsIgnoreCase("Outbound Correspondence"));
            Assert.assertTrue(inbound.firstLink.get(1).getText().trim().equalsIgnoreCase(outboundCID));
        } else if ("Outbound Correspondence".equalsIgnoreCase(inbound.secondLink.get(2).getText().trim())) {
            Assert.assertTrue(inbound.cid.isDisplayed());
            Assert.assertTrue(inbound.cid.getText().trim().equalsIgnoreCase(inbCid));
            scrollToElement(inbound.firstLink.get(2));
            waitFor(1);
            Assert.assertTrue(inbound.secondLink.get(2).getText().trim().equalsIgnoreCase("Outbound Correspondence"));
            Assert.assertTrue(inbound.secondLink.get(1).getText().trim().equalsIgnoreCase(outboundCID));
        } else if ("Outbound Correspondence".equalsIgnoreCase(inbound.thirdLink.get(2).getText().trim())) {
            Assert.assertTrue(inbound.cid.isDisplayed());
            Assert.assertTrue(inbound.cid.getText().trim().equalsIgnoreCase(inbCid));
            scrollToElement(inbound.firstLink.get(2));
            waitFor(1);
            Assert.assertTrue(inbound.thirdLink.get(2).getText().trim().equalsIgnoreCase("Outbound Correspondence"));
            Assert.assertTrue(inbound.thirdLink.get(1).getText().trim().equalsIgnoreCase(outboundCID));
        } else {
            Assert.fail("Link not found");
        }
    }

    public void verifyOutboundLinkedToInboundDocument(String outbCid, String inbCid) {
        System.out.println("Verifying Links");
        if ("previouslyCreated".equalsIgnoreCase(outbCid) || "fromRequest".equalsIgnoreCase(outbCid)) {
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
            String nid = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid).getString("recipients[0].notifications[0].notificationId");
            JsonPath tempNotif = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutbCIDFromNID(nid);
            List<Map<String, Object>> notifications = tempNotif.getList("data.content");
            outbCid = notifications.get(0).get("id").toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(inbCid) || "fromRequest".equalsIgnoreCase(inbCid)) {
            inbCid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getOutboundCorrespondenceLinks(outbCid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        boolean found = false;
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Inbound Correspondence")) {
                found = true;
                Assert.assertEquals(link.get("internalId").toString().trim(), outbCid);
                Assert.assertEquals(link.get("id").toString().trim(), inbCid);
            }
        }
        Assert.assertTrue(found, "link not found");
    }

    public void verifyRescannedLinksFromOriginalDocument() {
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        JsonPath rescan = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("GETNEWRESCANINBOUNDDOCID").toString());
        List<Map<String, Object>> originalDocumentLinks = original.getList("externalLinkDetails.content");
        List<Map<String, Object>> rescannedDocumentLinks = rescan.getList("externalLinkDetails.content");
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();
        for (Map<String, Object> originalDocumentLink : originalDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name"))))) {
                expected.add(Integer.parseInt(String.valueOf(originalDocumentLink.get("id"))));
            }
        }
        for (Map<String, Object> rescannedDocumentLink : rescannedDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(rescannedDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(rescannedDocumentLink.get("name"))))) {
                actual.add(Integer.parseInt(String.valueOf(rescannedDocumentLink.get("id"))));
            }
        }
        Assert.assertTrue(expected.size() > 0 && actual.size() > 0);
        Collections.sort(expected);
        Collections.sort(actual);
        Assert.assertEquals(actual, expected, " list not the same | " + actual + "\n" + expected);

    }

    public void createMetaDataInboundDocumentDigitalRequest(Map<String, String> dataTable) {
        inbRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/Digital.json").jsonElement);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "documentType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "CaseID":
                    String caseNumb = dataTable.get(keyword);
                    if ("PreviouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseNumb = caseConsumerId.get("caseId");
                    } else if ("Random".equalsIgnoreCase(dataTable.get(keyword))) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseNumb = caseConsumerId.get("caseId");
                    } else if ("previouslyCreatedForApplication".equalsIgnoreCase(dataTable.get(keyword))) {
                        caseNumb = InboundDocumentTaskStepDefs.listOfCases.get().get(new Random().nextInt(InboundDocumentTaskStepDefs.listOfCases.get().size()));
                        InboundDocumentTaskStepDefs.listOfCases.get().clear();
                        InboundDocumentTaskStepDefs.listOfCases.get().add(caseNumb);
                    }
                    JsonObject caseId = new JsonObject();
                    caseId.addProperty("name", keyword);
                    caseId.addProperty("value", caseNumb);
                    inbRequest.get().getAsJsonArray("metaData").add(caseId);
                    break;
                case "fileType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "thirdPartReceivedDate":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "NotificationID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        List<Map<String, Object>> notifications = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString()).getList("recipients[0].notifications");
                        String nid = notifications.get(0).get("notificationId").toString();
                        World.generalSave.get().put("NOTIFICATIONID", nid);
                        JsonObject metaDataJson = new JsonObject();
                        metaDataJson.addProperty("name", keyword);
                        metaDataJson.addProperty("value", nid);
                        inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
                    } else {
                        JsonObject metaDataJson = new JsonObject();
                        metaDataJson.addProperty("name", keyword);
                        metaDataJson.addProperty("value", dataTable.get(keyword));
                        inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
                    }
                    break;
                case "SetId":
                    String set = dataTable.get(keyword);
                    if ("random".equalsIgnoreCase(dataTable.get(keyword))) {
                        set = RandomStringUtils.random(9, false, true);
                        World.generalSave.get().put("setId", set);
                    } else if ("PreviouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        set = String.valueOf(World.generalSave.get().get("setId"));
                    }
                    JsonObject setMetadata = new JsonObject();
                    setMetadata.addProperty("name", keyword);
                    setMetadata.addProperty("value", set);
                    inbRequest.get().getAsJsonArray("metaData").add(setMetadata);
                    break;
                case "ConsumerID":
                    String[] consumers;
                    if (dataTable.get(keyword).contains(",")) {
                        consumers = dataTable.get(keyword).split(",");
                        for (String consumer : consumers) {
                            JsonObject metaDataJson = new JsonObject();
                            metaDataJson.addProperty("name", keyword);
                            metaDataJson.addProperty("value", consumer);
                            inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
                        }
                        break;
                    } else {
                        String cons = "";
                        if ("random".equalsIgnoreCase(dataTable.get(keyword)) || "previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                            cons = caseConsumerId.get("consumerId");
                        } else {
                            cons = dataTable.get(keyword);
                        }
                        JsonObject metaDataJson = new JsonObject();
                        metaDataJson.addProperty("name", keyword);
                        metaDataJson.addProperty("value", cons);
                        inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
                        break;
                    }
                case "ApplicationID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        JsonObject application = new JsonObject();
                        application.addProperty("name", keyword);
                        application.addProperty("value", World.save.get().get("appID"));
                        inbRequest.get().getAsJsonArray("metaData").add(application);
                    }
                    break;

                case "Missing Information Item ID":
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get(keyword))) {
                        JsonObject miId = new JsonObject();
                        miId.addProperty("name", keyword);
                        World.generalSave.get().put("MissingInformationItemId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].missingInfoItemId"));
                        miId.addProperty("value", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].missingInfoItemId"));
                        inbRequest.get().getAsJsonArray("metaData").add(miId);
                    }
                    break;

                case "FromPhoneNumber":
                    String number = dataTable.get(keyword);
                    if ("previouslyCreated".equalsIgnoreCase(number)) {
                        number = RandomStringUtils.random(10, false, true);
                    }
                    JsonObject phoneNumber = new JsonObject();
                    phoneNumber.addProperty("name", keyword);
                    phoneNumber.addProperty("value", number);
                    inbRequest.get().getAsJsonArray("metaData").add(phoneNumber);
                    break;

                default:
                    String value = dataTable.get(keyword);
                    if ("Random".equalsIgnoreCase(dataTable.get(keyword))) {
                        value = RandomStringUtils.random(5, false, true);
                        World.generalSave.get().put(keyword, value);
                    } else if ("Same".equalsIgnoreCase(dataTable.get(keyword))) {
                        value = String.valueOf(World.generalSave.get().get(keyword));
                    }
                    JsonObject metaDataJson = new JsonObject();
                    metaDataJson.addProperty("name", keyword);
                    metaDataJson.addProperty("value", value);
                    inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
            }
        }
    }

    public void createMetaDataRecordsInboundDocumentDigitalRequest(Map<String, String> dataTable) {
        inbRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/Digital.json").jsonElement);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "documentType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                case "fileType":
                    inbRequest.get().addProperty(keyword, dataTable.get(keyword));
                    break;
                default:
                    JsonObject metaDataJson = new JsonObject();
                    metaDataJson.addProperty("name", keyword);
                    metaDataJson.addProperty("value", dataTable.get(keyword));
                    inbRequest.get().getAsJsonArray("metaData").add(metaDataJson);
            }
        }
    }

    public void createMetaDataRecordsForIBRequest(String docType, Map<String, String> dataTable) {
        JsonArray jsonArr = new JsonArray();
        JsonObject nameObj = new JsonObject();
        nameObj.addProperty("name", docType);
        inbRequest.get().getAsJsonArray("metaDataRecords").add(nameObj);
        inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().add("metaData", jsonArr);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                default:
                    JsonObject metaDataJson = new JsonObject();
                    metaDataJson.addProperty("name", keyword);
                    metaDataJson.addProperty("value", dataTable.get(keyword));
                    inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").add(metaDataJson);
                    break;
            }
        }
    }

    public void sendRequestInboundDocumentDigitalAPI() {
        World.generalSave.get().put("JsonInbRequest", inbRequest.get());
        World.generalSave.get().put("InboundDocumentIdDigital", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
        System.out.println("Ib doc id : " + String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")));
    }

    public void sendAnotherInboundDocumentDigitalAPI() {
        World.generalSave.get().put("SecondInboundDocumentId", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
    }

    /**
     * @param dataTable how many random Consumer arrays
     */
    public void addRandomConsumerMetaDataRecords(Map<String, String> dataTable) {
        JsonObject metaDataRecord = new JsonObject();
        JsonArray metaData = new JsonArray();
        JsonObject consumer = new JsonObject();
        JsonObject consumerHealthPlan = new JsonObject();
        JsonObject consumerDentalPlan = new JsonObject();
        List<Map<String, String>> plans = new ArrayList<>();
        for (int instances = 0; instances < Integer.parseInt(dataTable.get("Consumer")); instances++) {
            Map<String, String> plan = new HashedMap();
            metaDataRecord = new JsonObject();
            consumer = new JsonObject();
            consumerHealthPlan = new JsonObject();
            consumerDentalPlan = new JsonObject();
            metaData = new JsonArray();
            consumer.addProperty("name", "ConsumerID");
            plan.put("ConsumerID", RandomStringUtils.random(4, false, true));
            consumer.addProperty("value", plan.get("ConsumerID"));
            //add health plan
            consumerHealthPlan.addProperty("name", "ConsumerHealthPlanChoice");
            plan.put("ConsumerHealthPlanChoice", (new Random().nextBoolean()) ? "MP01" : "MP02");
            consumerHealthPlan.addProperty("value", plan.get("ConsumerHealthPlanChoice"));
            //add dental plan
            consumerDentalPlan.addProperty("name", "ConsumerDentalPlanChoice");
            plan.put("ConsumerDentalPlanChoice", (new Random().nextBoolean()) ? "DP01" : "DP02");
            consumerDentalPlan.addProperty("value", plan.get("ConsumerDentalPlanChoice"));
            metaDataRecord.addProperty("name", "Consumer");
            metaData.add(consumer);
            metaData.add(consumerHealthPlan);
            metaData.add(consumerDentalPlan);
            metaDataRecord.add("metaData", metaData);
            inbRequest.get().getAsJsonArray("metaDataRecords").add(metaDataRecord);
            plans.add(plan);
        }
        World.generalSave.get().put("PLANS", plans);
    }

    public void verifyInbDocumentKeywords(JsonPath retrieveInbResponse, Map<String, String> dataTable) {
        int counter = 0;
        for (String keyword : dataTable.keySet()) {
            switch (keyword.toUpperCase()) {
                case "CHANNEL":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.channel"), dataTable.get(keyword));
                    counter++;
                    break;
                case "STATUS":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.status"), dataTable.get(keyword));
                    counter++;
                    break;
                case "STATUSDATE":
                    Assert.assertTrue(EventsUtilities.isValidOnbaseDate(retrieveInbResponse.getString("inboundCorrespondence.statusDate")));
                    counter++;
                    break;
                case "RECEIVEDDATE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.receivedDate"), dataTable.get(keyword));
                    counter++;
                    break;
                case "CASEID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.caseId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "CONSUMERID":
                    if (dataTable.get(keyword).contains(",")) {
//                        String[] actual = dataTable.get(keyword).split(",");
//                        Arrays.sort(actual);
//                        String[] expected = retrieveInbResponse.getString("inboundCorrespondence.consumerId").replace("[","").replace("]","").split(",");
//                        Arrays.sort(expected);
                        int[] expected = Arrays.stream(dataTable.get(keyword).split(",")).mapToInt(Integer::parseInt).toArray();
                        int[] actual = Arrays.stream(retrieveInbResponse.getString("inboundCorrespondence.consumerId").replace("[", "").replace("]", "").replace(" ", "").split(",")).mapToInt(Integer::parseInt).toArray();
                        Arrays.sort(actual);
                        Arrays.sort(expected);
                        Assert.assertEquals(actual, expected, "actual | " + Arrays.toString(actual) + "\n" + "expected | " + Arrays.toString(expected));
                        break;
                    } else {
                        Assert.assertEquals(BrowserUtils.validNumberFilter(retrieveInbResponse.getString("inboundCorrespondence.consumerId")), dataTable.get(keyword));
                        counter++;
                        break;
                    }
                case "ORIGIN":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.origin"), dataTable.get(keyword));
                    counter++;
                    break;
                case "ORIGINITEMID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.originItemId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "ORIGINSETID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.originSetId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "FROMNAME":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.fromName"), dataTable.get(keyword));
                    counter++;
                    break;
                case "FROMEMAILADDRESS":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.fromEmailAddress"), dataTable.get(keyword));
                    counter++;
                    break;
                case "FROMPHONENUMBER":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.fromPhoneNumber"), dataTable.get(keyword));
                    counter++;
                    break;
                case "NOTIFICATIONID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.returnedNotificationId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "FORMVERSION":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.formVersion"), dataTable.get(keyword));
                    counter++;
                    break;
                case "RESCANOFDOCUMENTID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.rescanOfCorrespondenceId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "REPLACEMENTDOCUMENTID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.replacementCorrespondenceId"), dataTable.get(keyword));
                    counter++;
                    break;
                case "SCANNEDON":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.scannedOn"), dataTable.get(keyword));
                    counter++;
                    break;
                case "RETURNEDREASON":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.returnedReason"), dataTable.get(keyword));
                    counter++;
                    break;
                case "LANGUAGE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.language"), dataTable.get(keyword));
                    counter++;
                    break;
                case "THIRDPARTYRECEIVEDDATE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.thirdPartyReceivedDate"), dataTable.get(keyword));
                    counter++;
                    break;
                case "BARCODE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.barcode"), dataTable.get(keyword));
                    counter++;
                    break;
                case "CONSUMERS":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.consumers"), dataTable.get(keyword));
                    counter++;
                    break;
                case "PAGECOUNT":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.pageCount"), dataTable.get(keyword));
                    counter++;
                    break;
                case "DOCUMENTTYPE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.correspondenceType"), dataTable.get(keyword));
                    counter++;
                    break;
                case "CREATEDON":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.createdOn"), dataTable.get(keyword));
                    counter++;
                    break;
                case "CREATEDBY":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.createdBy"), dataTable.get(keyword));
                    counter++;
                    break;
                case "UPDATEDBY":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.updatedBy"), dataTable.get(keyword));
                    counter++;
                    break;
                case "PROCESSTYPE":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.processType"), dataTable.get(keyword));
                    counter++;
                    break;
                case "ID":
                    Assert.assertEquals(retrieveInbResponse.getString("inboundCorrespondence.id"), dataTable.get(keyword));
                    counter++;
                    break;
                case "METADATA":
                    JsonArray expectedJsonArray = (JsonArray) World.generalSave.get().get("metaDataJsonArray");
                    if (dataTable.get(keyword).equalsIgnoreCase("AddKeyword") || dataTable.get(keyword).equalsIgnoreCase("ReplaceKeyword")) {
                        for (int i = 0; i < expectedJsonArray.size(); i++) {
                            Assert.assertTrue(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("name").getAsString()));
                            Assert.assertTrue(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("value").getAsString().toUpperCase()));
                        }
                    } else if (dataTable.get(keyword).equalsIgnoreCase("DeleteKeyword")) {
                        for (int i = 0; i < expectedJsonArray.size(); i++) {
                            Assert.assertFalse(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("name").getAsString()));
                            Assert.assertFalse(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("value").getAsString()));
                        }
                    } else if (dataTable.get(keyword).equalsIgnoreCase("DeleteKeyword*")) {
                        for (int i = 0; i < expectedJsonArray.size(); i++) {
                            Assert.assertFalse(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("name").getAsString()));
                        }
                    } else if (dataTable.get(keyword).equalsIgnoreCase("AddKeywordDeleteKeyword")) {
                        for (int i = 0; i < expectedJsonArray.size(); i++) {
                            if (expectedJsonArray.get(i).getAsJsonObject().get("action").getAsString().equals("AddKeyword")) {
                                Assert.assertTrue(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("name").getAsString()));
                                Assert.assertTrue(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("value").getAsString().toUpperCase()));
                            } else {
                                Assert.assertFalse(retrieveInbResponse.prettyPrint().contains(expectedJsonArray.get(i).getAsJsonObject().get("name").getAsString()));
                            }
                        }
                    }
                    break;
                default:
                    List<Map<String, String>> metadataArray = retrieveInbResponse.getList("inboundCorrespondence.metaData");
                    try {
                        for (Map<String, String> metadata : metadataArray) {
                            if (keyword.equalsIgnoreCase(metadata.get("name"))) {
                                Assert.assertEquals(metadata.get("value"), dataTable.get(keyword));
                                counter++;
                            }
                        }
                    } catch (NullPointerException noMeta) {
                        Assert.fail("keyword not found | Keyword - " + keyword);
                    }
                    Assert.assertEquals(metadataArray.size(), counter, "item missed in dataTable");
            }
        }
    }

    /**
     * verifies Consumer keyword record containing ConsumerID, ConsumerHealthPlanChoice, ConsumerDentalPlanChoice
     * in inb doc retrieval response
     */
    public void verifyConsumerKeywordRecord(JsonPath retrieveInbResponse, Map<String, String> dataTable) {
        List<Map<String, String>> actual = retrieveInbResponse.getList("inboundCorrespondence.consumers");
        Assert.assertEquals(actual.size(), Integer.parseInt(dataTable.get("Consumer")), "consumers not equal");
        List<Map<String, String>> expected = (List<Map<String, String>>) World.generalSave.get().get("PLANS");
        Assert.assertEquals(actual.size(), expected.size(), "consumers not equal");
        boolean found = false;
        for (Map<String, String> consumer : expected) {
            for (Map<String, String> multiInstanceKeywordRecord : actual) {
                if (consumer.get("ConsumerID").equalsIgnoreCase(multiInstanceKeywordRecord.get("consumerId"))) {
                    found = true;
                    Assert.assertEquals(multiInstanceKeywordRecord.get("healthPlanChoice"), consumer.get("ConsumerHealthPlanChoice"));
                    Assert.assertEquals(multiInstanceKeywordRecord.get("dentalPlanChoice"), consumer.get("ConsumerDentalPlanChoice"));
                }
            }
        }
        Assert.assertTrue(found, "no matches found, no comparisons made");
    }

    /**
     * @param dataTable add vacv newborn form members
     */
    public void addVACVNewbornFormMember(Map<String, String> dataTable) {
        List<Map<String, String>> members = new ArrayList<>();
        for (int instances = 0; instances < Integer.parseInt(dataTable.get("VACVNewborn")); instances++) {
            inbRequest.get().getAsJsonArray("metaDataRecords").add(generateRandomNewborn(members));
        }
        for (int instances = 0; instances < Integer.parseInt(dataTable.get("VACVNewbornParents")); instances++) {
            inbRequest.get().getAsJsonArray("metaDataRecords").add(generateMotherOfNewborn(members));
        }
        World.generalSave.get().put("VACVNEWBORNS", members);
    }

    private JsonObject generateRandomNewborn(List<Map<String, String>> newborns) {
        Map<String, String> member = new HashedMap();
        JsonObject metaDataRecord = new JsonObject();
        JsonArray metaData = new JsonArray();
        Faker faker = new Faker();
        JsonObject firstName = new JsonObject();
        JsonObject middleInitial = new JsonObject();
        JsonObject lastName = new JsonObject();
        JsonObject dob = new JsonObject();
        JsonObject isPrimary = new JsonObject();
        JsonObject ssn = new JsonObject();
        JsonObject exconsId = new JsonObject();
        JsonObject incarcerated = new JsonObject();
        JsonObject mcoName = new JsonObject();
        JsonObject resident = new JsonObject();
        JsonObject gender = new JsonObject();
        JsonObject race = new JsonObject();
        JsonObject accountNumber = new JsonObject();
        JsonObject dependentOfVeteran = new JsonObject();
        JsonObject taxFilingStatus = new JsonObject();
        JsonObject responsiblePartyName = new JsonObject();
        JsonObject relationship = new JsonObject();

        firstName.addProperty("name", "First Name");
        member.put("First Name", faker.name().firstName());
        firstName.addProperty("value", member.get("First Name"));

        middleInitial.addProperty("name", "Middle Initial");
        member.put("Middle Initial", String.valueOf(faker.name().nameWithMiddle().charAt(0)).toUpperCase());
        middleInitial.addProperty("value", member.get("Middle Initial"));

        lastName.addProperty("name", "Last Name");
        member.put("Last Name", faker.name().lastName());
        lastName.addProperty("value", member.get("Last Name"));

        dob.addProperty("name", "DOB");
        SimpleDateFormat db = new SimpleDateFormat("MM/dd/yyyy");
        String formatted = db.format(faker.date().birthday(0, 2));
        member.put("DOB", formatted);
        dob.addProperty("value", member.get("DOB"));

        member.put("Is Primary", "NO");
        isPrimary.addProperty("name", "Is Primary");
        isPrimary.addProperty("value", member.get("Is Primary"));

        ssn.addProperty("name", "SSN");
        member.put("SSN", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        ssn.addProperty("value", member.get("SSN"));

        exconsId.addProperty("name", "External Consumer ID");
        member.put("External Consumer ID", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        exconsId.addProperty("value", member.get("External Consumer ID"));

        member.put("Incarcerated", (new Random().nextBoolean()) ? "Y" : "N");
        incarcerated.addProperty("name", "Incarcerated");
        incarcerated.addProperty("value", member.get("Incarcerated"));

        member.put("MCO Name", (new Random().nextBoolean()) ? "USVA Health" : "OTHER");
        mcoName.addProperty("name", "MCO Name");
        mcoName.addProperty("value", member.get("MCO Name"));

        member.put("Resident Of State", (new Random().nextBoolean()) ? "Y" : "N");
        resident.addProperty("name", "Resident Of State");
        resident.addProperty("value", member.get("Resident Of State"));

        member.put("Gender", (new Random().nextBoolean()) ? "M" : "F");
        gender.addProperty("name", "Gender");
        gender.addProperty("value", member.get("Gender"));

        member.put("Race", faker.demographic().race());
        race.addProperty("name", "Race");
        race.addProperty("value", member.get("Race"));

        accountNumber.addProperty("name", "Account Number");
        member.put("Account Number", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        accountNumber.addProperty("value", member.get("Account Number"));

        member.put("Dependent Of Veteran", (new Random().nextBoolean()) ? "Y" : "N");
        dependentOfVeteran.addProperty("name", "Dependent Of Veteran");
        dependentOfVeteran.addProperty("value", member.get("Dependent Of Veteran"));

        member.put("Tax Filing Status", (new Random().nextBoolean()) ? "SINGLE" : "MARRIED");
        taxFilingStatus.addProperty("name", "Tax Filing Status");
        taxFilingStatus.addProperty("value", member.get("Tax Filing Status"));

        responsiblePartyName.addProperty("name", "Responsible Party Name");
        member.put("Responsible Party Name", faker.name().firstName());
        responsiblePartyName.addProperty("value", member.get("Responsible Party Name"));

        member.put("Relationship", (new Random().nextBoolean()) ? "PARENT" : "SIBLING");
        relationship.addProperty("name", "Relationship");
        relationship.addProperty("value", member.get("Relationship"));

        metaDataRecord.addProperty("name", "VACV Newborn Form Member");

        metaData.add(firstName);
        metaData.add(middleInitial);
        metaData.add(lastName);
        metaData.add(dob);
        metaData.add(isPrimary);
        metaData.add(ssn);
        metaData.add(exconsId);
        metaData.add(incarcerated);
        metaData.add(mcoName);
        metaData.add(resident);
        metaData.add(gender);
        metaData.add(race);
        metaData.add(accountNumber);
        metaData.add(dependentOfVeteran);
        metaData.add(taxFilingStatus);
        metaData.add(responsiblePartyName);
        metaData.add(relationship);

        metaDataRecord.add("metaData", metaData);
        newborns.add(member);//to be stored in general save
        return metaDataRecord;
    }

    private JsonObject generateMotherOfNewborn(List<Map<String, String>> mothers) {
        Map<String, String> member = new HashedMap();
        JsonObject metaDataRecord = new JsonObject();
        JsonArray metaData = new JsonArray();
        Faker faker = new Faker();
        JsonObject firstName = new JsonObject();
        JsonObject middleInitial = new JsonObject();
        JsonObject lastName = new JsonObject();
        JsonObject dob = new JsonObject();
        JsonObject isPrimary = new JsonObject();
        JsonObject ssn = new JsonObject();
        JsonObject exconsId = new JsonObject();
        JsonObject incarcerated = new JsonObject();
        JsonObject mcoName = new JsonObject();
        JsonObject resident = new JsonObject();
        JsonObject gender = new JsonObject();
        JsonObject race = new JsonObject();
        JsonObject accountNumber = new JsonObject();
        JsonObject dependentOfVeteran = new JsonObject();
        JsonObject taxFilingStatus = new JsonObject();
        JsonObject responsiblePartyName = new JsonObject();
        JsonObject relationship = new JsonObject();

        firstName.addProperty("name", "First Name");
        member.put("First Name", faker.name().firstName());
        firstName.addProperty("value", member.get("First Name"));

        middleInitial.addProperty("name", "Middle Initial");
        member.put("Middle Initial", String.valueOf(faker.name().nameWithMiddle().charAt(0)).toUpperCase());
        middleInitial.addProperty("value", member.get("Middle Initial"));

        lastName.addProperty("name", "Last Name");
        member.put("Last Name", faker.name().lastName());
        lastName.addProperty("value", member.get("Last Name"));

        dob.addProperty("name", "DOB");
        SimpleDateFormat db = new SimpleDateFormat("MM/dd/yyyy");
        String formatted = db.format(faker.date().birthday(18, 70));
        member.put("DOB", formatted);
        dob.addProperty("value", member.get("DOB"));

        member.put("Is Primary", "YES");
        isPrimary.addProperty("name", "Is Primary");
        isPrimary.addProperty("value", member.get("Is Primary"));

        ssn.addProperty("name", "SSN");
        member.put("SSN", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        ssn.addProperty("value", member.get("SSN"));

        exconsId.addProperty("name", "External Consumer ID");
        member.put("External Consumer ID", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        exconsId.addProperty("value", member.get("External Consumer ID"));

        member.put("Incarcerated", (new Random().nextBoolean()) ? "Y" : "N");
        incarcerated.addProperty("name", "Incarcerated");
        incarcerated.addProperty("value", member.get("Incarcerated"));

        member.put("MCO Name", (new Random().nextBoolean()) ? "USVA Health" : "OTHER");
        mcoName.addProperty("name", "MCO Name");
        mcoName.addProperty("value", member.get("MCO Name"));

        member.put("Resident Of State", (new Random().nextBoolean()) ? "Y" : "N");
        resident.addProperty("name", "Resident Of State");
        resident.addProperty("value", member.get("Resident Of State"));

        member.put("Gender", (new Random().nextBoolean()) ? "M" : "F");
        gender.addProperty("name", "Gender");
        gender.addProperty("value", member.get("Gender"));

        member.put("Race", faker.demographic().race());
        race.addProperty("name", "Race");
        race.addProperty("value", member.get("Race"));

        accountNumber.addProperty("name", "Account Number");
        member.put("Account Number", String.valueOf(new Random().nextInt(999999999 - 100000000) + 100000000));
        accountNumber.addProperty("value", member.get("Account Number"));

        member.put("Dependent Of Veteran", (new Random().nextBoolean()) ? "Y" : "N");
        dependentOfVeteran.addProperty("name", "Dependent Of Veteran");
        dependentOfVeteran.addProperty("value", member.get("Dependent Of Veteran"));

        member.put("Tax Filing Status", (new Random().nextBoolean()) ? "SINGLE" : "MARRIED");
        taxFilingStatus.addProperty("name", "Tax Filing Status");
        taxFilingStatus.addProperty("value", member.get("Tax Filing Status"));

        responsiblePartyName.addProperty("name", "Responsible Party Name");
        member.put("Responsible Party Name", faker.name().firstName());
        responsiblePartyName.addProperty("value", member.get("Responsible Party Name"));

        member.put("Relationship", (new Random().nextBoolean()) ? "PARENT" : "SIBLING");
        relationship.addProperty("name", "Relationship");
        relationship.addProperty("value", member.get("Relationship"));

        metaDataRecord.addProperty("name", "VACV Newborn Form Member");

        metaData.add(firstName);
        metaData.add(middleInitial);
        metaData.add(lastName);
        metaData.add(dob);
        metaData.add(isPrimary);
        metaData.add(ssn);
        metaData.add(exconsId);
        metaData.add(incarcerated);
        metaData.add(mcoName);
        metaData.add(resident);
        metaData.add(gender);
        metaData.add(race);
        metaData.add(accountNumber);
        metaData.add(dependentOfVeteran);
        metaData.add(taxFilingStatus);
        metaData.add(responsiblePartyName);
        metaData.add(relationship);

        metaDataRecord.add("metaData", metaData);
        mothers.add(member);
        return metaDataRecord;
    }

    public void verifyNewbornMember(JsonPath retrieveInbResponse, Map<String, String> dataTable) {
        List<Map<String, Object>> info = retrieveInbResponse.getList("inboundCorrespondence.metaDataRecords");
        List<Map<String, String>> expected = (List<Map<String, String>>) World.generalSave.get().get("VACVNEWBORNS");
        List<List<Map<String, String>>> actual = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : info) {
            actual.add((List<Map<String, String>>) stringObjectMap.get("metaData"));
        }
        boolean found = false;
        for (Map<String, String> expectedInfo : expected) {
            for (List<Map<String, String>> maps : actual) {
                for (Map<String, String> map : maps) {
                    //below if matches on ssn, map structure is map name:ssn, value: xxxx
                    if (map.containsValue("SSN") && (expectedInfo.get("SSN").equalsIgnoreCase(map.get("value")))) {
                        found = true;
                        for (Map<String, String> validations : maps) {
                            String exp = expectedInfo.get(validations.get("name"));
                            String act = validations.get("value");
                            if (validations.get("name").equalsIgnoreCase("DOB")) {
                                Assert.assertTrue(expectedInfo.get(validations.get("name")).equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(validations.get("value").trim())), "expected - " + exp + "\n " + "actual - " + act);
                            } else {
                                Assert.assertTrue(validations.get("value").equalsIgnoreCase(expectedInfo.get(validations.get("name"))), "expected - " + exp + "\n " + "actual - " + act);
                            }
                        }

                    }
                }
            }
        }
        Assert.assertTrue(found, "no matches found, no comparisons made");
    }

    public void verifyCaseLinkedToInbDocumentUI() {
        waitFor(7);
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        List<Map<String, Object>> originalDocumentLinks = original.getList("externalLinkDetails.content");
        List<WebElement> rescannedDocumentLinks = viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn;
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();
        for (Map<String, Object> originalDocumentLink : originalDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name"))))) {
                expected.add(Integer.parseInt(String.valueOf(originalDocumentLink.get("id"))));
            }
        }
        for (WebElement rescannedDocumentLink : rescannedDocumentLinks) {
            actual.add(Integer.valueOf(validNumberFilter(rescannedDocumentLink.getText().trim())));
        }
        Assert.assertTrue(expected.size() > 0 && actual.size() > 0);
        Collections.sort(expected);
        Collections.sort(actual);
        Assert.assertEquals(actual, expected, " list not the same | " + actual + "\n" + expected);
    }

    public void verifyOriginalDocumentUI(Map<String, String> dataTable) {
        waitFor(9);
        for (String s : dataTable.keySet()) {
            switch (s.toUpperCase()) {
                case "STATUS":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.statusValue.getText(), dataTable.get("status"));
                    break;
                case "UPDATEDON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedonValue.getText().trim(), getCurrentDate());
                    break;
                case "UPDATEDBY":
                    Assert.assertTrue(dataTable.get("updatedBy").equalsIgnoreCase(viewInboundCorrespondenceDetailsUIAPIPage.updatedbyValue.getText().trim()));
                    break;
                case "REPLACEMENTDOCUMENTID":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.rescannedAsCidValue.getText(), World.generalSave.get().get(dataTable.get("ReplacementDocumentId")).toString());
                    break;
            }
        }
    }

    public void clickFirstTask() {
        waitFor(9);
        scrollDown();
        waitFor(1);
        String id = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn.get(0).getText().trim());
        viewInboundCorrespondenceDetailsUIAPIPage.linkTaskIdColumn.get(0).click();
        waitFor(5);
        waitForPageToLoad(10);
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'description')]")).isDisplayed());
    }

    public void clickFirstSR() {
        waitFor(9);
        scrollDown();
        waitFor(1);
        String id = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestIdColumn.get(0).getText().trim());
        viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestIdColumn.get(0).click();
        waitFor(5);
        waitForPageToLoad(10);
        Assert.assertTrue(crmSRViewEditPage.btnEditSR.isDisplayed());
    }

    /**
     * Inbound Task - 12542 (BLCRM)
     *
     * @param taskType
     */
    public void cancelTask(String taskType) {
        switch (taskType.toUpperCase()) {
            case "INBOUND TASK":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Cancelled");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(1);
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "Translation Request":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Cancelled");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.informationType, "Other");
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "LDSS Communication Form":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Cancelled");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.informationType, "Other");
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "AUTHORIZEDREPRESENTATIVEFORM":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Cancelled");
                System.out.println("status to cancelled");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                System.out.println("cancel reason to duplicate task");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.channel, "Phone");
                System.out.println("channel to phone");
                waitFor(1);
                scrollDown();
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            default:
                Assert.fail("no matching cases");

        }

    }

    public void completeTask(String status) {
        switch (status.toUpperCase()) {
            case "INBOUND TASK":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Complete");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(1);
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "COMPLETE TASK":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Complete");
                waitFor(3);
                selectFromMultiSelectDropDown(crmCreateGeneralTaskPage.reasonForEditDrpDn, "Corrected Data Entry");
                waitFor(3);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(2);
                selectDropDown(crmCreateGeneralTaskPage.disposition, "User closed");
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(1);
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "Translation Request":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Complete");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.informationType, "Other");
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "LDSS Communication Form":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Complete");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.cancelReason, "Duplicate Task");
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.informationType, "Other");
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            case "AUTHORIZEDREPRESENTATIVEFORM":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Complete");
                waitFor(3);
                selectFromMultiSelectDropDown(crmCreateGeneralTaskPage.reasonForEditDrpDn, "Corrected Data Entry");
                waitFor(3);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.channel, "Phone");
                waitFor(3);
                selectFromMultiSelectDropDown(crmCreateGeneralTaskPage.actionTaken, "None");
                waitFor(3);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(3);
                selectDropDown(crmCreateGeneralTaskPage.disposition, "Resolved");
                waitFor(1);
                scrollDown();
                crmCreateGeneralTaskPage.btnSave.click();
                break;
            default:
                Assert.fail("no matching cases");

        }
    }

    public void updateInboundDocument(String cid, Map<String, String> dataTable) {
        JsonObject request = new JsonObject();
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }

        request.addProperty("id", cid);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "consumerId":
                    if (dataTable.get(keyword).equalsIgnoreCase("null")) {
                        request.add("consumerId", JsonNull.INSTANCE);
                    } else {
                        JsonArray consumers = new JsonArray();
                        String temp = dataTable.get(keyword).trim().replace(" ", "");
                        String[] con = temp.split(",");
                        for (String s : con) {
                            consumers.add(s);
                        }
                        request.add(keyword, consumers);
                    }
                    break;
                case "receivedDate":
                case "documentType":
                case "note":
                    request.addProperty(keyword, dataTable.get(keyword));
                    break;
                case "metaData":
                    String randomValue = "";
                    if (dataTable.get(keyword).equalsIgnoreCase("null")) {
                        request.add("metaData", JsonNull.INSTANCE);
                    } else {
                        String actionKeyword = dataTable.get(keyword).trim().split("_")[1];
                        JsonArray metaData = new JsonArray();
                        String withoutActionKeyword = dataTable.get(keyword).trim().replace("_" + actionKeyword, "");
                        String[] listOfNames = withoutActionKeyword.split(",");
                        if (actionKeyword.equalsIgnoreCase("AddKeyword") || actionKeyword.equalsIgnoreCase("ReplaceKeyword")) {
                            for (String name : listOfNames) {
                                JsonObject metaDataElement = new JsonObject();
                                if (name.startsWith("VACV Newman Extract Status")) {
                                    String value = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
                                    metaDataElement.addProperty("name", "VACV Newman Extract Status");
                                    metaDataElement.addProperty("value", value);
                                } else {
                                    metaDataElement.addProperty("name", name);
                                    randomValue = RandomStringUtils.random(10, true, false);
                                    metaDataElement.addProperty("value", randomValue);
                                    updatedValues.get().put(name, randomValue);
                                }
                                metaDataElement.addProperty("action", actionKeyword);
                                metaData.add(metaDataElement);
                            }
                            World.generalSave.get().put("metaDataJsonArray", metaData);
                            request.add(keyword, metaData);
                        } else if (actionKeyword.equalsIgnoreCase("DeleteKeyword")) {
                            for (String name : listOfNames) {
                                JsonObject metaDataElement = new JsonObject();
                                metaDataElement.addProperty("name", name);
                                metaDataElement.addProperty("value", updatedValues.get().get(name));
                                metaDataElement.addProperty("action", actionKeyword);
                                metaData.add(metaDataElement);
                            }
                            World.generalSave.get().put("metaDataJsonArray", metaData);
                            request.add(keyword, metaData);
                        } else if (actionKeyword.equalsIgnoreCase("DeleteKeyword*")) {
                            for (String name : listOfNames) {
                                JsonObject metaDataElement = new JsonObject();
                                metaDataElement.addProperty("name", name);
                                metaDataElement.addProperty("value", "*");
                                metaDataElement.addProperty("action", actionKeyword.replace("*", ""));
                                metaData.add(metaDataElement);
                            }
                            World.generalSave.get().put("metaDataJsonArray", metaData);
                            request.add(keyword, metaData);
                        } else if (actionKeyword.equalsIgnoreCase("AddKeywordDeleteKeyword")) {
                            for (String name : listOfNames) {
                                if (radomNumber(10) % 2 == 0) {
                                    JsonObject metaDataElement = new JsonObject();
                                    metaDataElement.addProperty("name", name);
                                    metaDataElement.addProperty("value", RandomStringUtils.random(10, true, false));
                                    metaDataElement.addProperty("action", "AddKeyword");
                                    metaData.add(metaDataElement);

                                } else {
                                    JsonObject metaDataElement = new JsonObject();
                                    metaDataElement.addProperty("name", name);
                                    metaDataElement.addProperty("value", "*");
                                    metaDataElement.addProperty("action", "DeleteKeyword");
                                    metaData.add(metaDataElement);
                                }
                            }
                            World.generalSave.get().put("metaDataJsonArray", metaData);
                            request.add(keyword, metaData);
                        }
                    }
                    break;
                default:
                    request.addProperty(keyword, dataTable.get(keyword));
            }
        }
        APIAutoUtilities api = new APIAutoUtilities();
        api.postInboundDocument(ConfigurationReader.getProperty("apiBffCorrespondence") + "/ecms/correspondence/inbound/document", String.valueOf(request));
        Assert.assertEquals(api.statusCode, 200);
    }

    public void clickEditButton() {
        waitFor(7);
        inbound.editButton.click();
        waitFor(7);
    }

    public void selectInboundTypeUI(String type) {
        waitFor(2);
        selectFromMultiSelectDropDown(inbound.inboundTypeDropDown, type);
        waitFor(2);
    }

    public void verifyTypePopupMessage(String message) {
        Assert.assertTrue(inbound.getTypePop(message).isDisplayed());
    }

    public void clickSaveButton() {
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(2);
        inbound.saveButton.click();
    }

    public void clickGetImageButton() {
        waitFor(7);
        inbound.getImageButton.click();
        waitFor(12);
        Driver.getDriver().findElement(By.xpath("//body")).click();
    }

    public void verifyDocumentSetDetailsIcon() {
        waitFor(15);
        Assert.assertTrue(inbound.documentSetDetailsIcon.isDisplayed());

    }

    public void verifyDocumentsInSetUI() {
        Assert.assertEquals(inbound.documentSetViewIcons.size(), 2);
    }

    public void navToDocumentSetDetailsTab() {
        waitFor(7);
        inbound.documentSetDetailstab.click();
        waitFor(7);
    }

    public void verifyDocumentSetDetailsTab() {
        waitFor(7);
        Assert.assertTrue(inbound.documentSetDetailstab.isDisplayed());
        waitFor(7);
    }

    public void verifyViewIcon() {
        waitFor(7);
        Assert.assertTrue(inbound.viewIcon.isDisplayed());
    }

    public void verifyTypeDropDownValuesAlphabeticalOrder() {
        waitFor(7);
        inbound.inboundTypeDropDown.click();
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(2);
        inbound.inboundTypeDropDown.click();
        boolean found = false;
        waitFor(2);
        List<String> actual = new ArrayList<>();
        inbound.typeDropDownOptions.stream().forEach((k) -> {
            actual.add(k.getText());
        });
        List<String> expected = new ArrayList<>();
        expected.addAll(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    public void verifyRequestRescanButtonVisible() {
        waitFor(7);
        Assert.assertTrue(inbound.requestRescan.isDisplayed());
        waitFor(2);
    }

    public void clickRescanButton() {
        waitFor(7);
        inbound.requestRescan.click();
        waitFor(7);
    }

    public void verifyCancelRequestButton() {
        waitFor(7);
        Assert.assertTrue(inbound.cancelRescanButton.isDisplayed());
        waitFor(2);
    }

    public void clickCancelRescanButton() {
        waitFor(7);
        inbound.cancelRescanButton.click();
        waitFor(2);
        Assert.assertTrue(inbound.getTypePop("Successfully cancelled Re-Scan request").isDisplayed());
        waitFor(7);
    }

    public void verifyDocumentSetDetailsLabels() {
        waitFor(7);
        Assert.assertTrue(inbound.documentSetDetailsCIDHeader.isDisplayed());
        Assert.assertTrue(inbound.documentSetDetailsTypeHeader.isDisplayed());
        Assert.assertTrue(inbound.documentSetDetailsCIDHeader.isDisplayed());
    }

    public void verifyDocumentSetDetailsMessage(String message) {
        waitFor(7);
        Assert.assertTrue(inbound.noRecordsFound.isDisplayed());
        Assert.assertEquals(inbound.noRecordsFound.getText().trim(), message);
    }

    public void verifyMultipleConsumersEllipsis() {
        waitFor(7);
        Assert.assertTrue(inbound.multipleConsumersEllipsis.isDisplayed());
    }

    public void sendDigitalSameSetInbDocument() {
        World.generalSave.get().put("SameSetInboundDocument", Integer.parseInt(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createInboundDocumentDigital(inbRequest.get())));
    }

    public void clickOnPreviouslyCreatedOutboundCorrespondence() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        scrollDown();
        BrowserUtils.waitFor(10);
        List<WebElement> firstLink = inbound.firstLink;
        List<WebElement> secondLink = inbound.secondLink;
        List<WebElement> thirdLink = inbound.thirdLink;
        List<List<WebElement>> links = new ArrayList<>();
        links.add(firstLink);
        links.add(secondLink);
        links.add(thirdLink);
        try {
            for (List<WebElement> link : links) {
                for (WebElement element : link) {
                    if (element.getText().trim().equalsIgnoreCase(cid)) {
                        element.click();
                        waitFor(5);
                        return;
                    }
                }
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("no text found");
        }
    }

    public void clickOnPreviouslyCreatedOutboundCorrespondence(String cid) {
        BrowserUtils.waitFor(10);
        List<WebElement> firstLink = inbound.firstLink;
        List<WebElement> secondLink = inbound.secondLink;
        List<WebElement> thirdLink = inbound.thirdLink;
        List<List<WebElement>> links = new ArrayList<>();
        links.add(firstLink);
        links.add(secondLink);
        links.add(thirdLink);
        try {
            for (List<WebElement> link : links) {
                for (WebElement element : link) {
                    if (element.getText().trim().equalsIgnoreCase(cid)) {
                        element.click();
                        waitFor(5);
                        return;
                    }
                }
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("no text found");
        }
    }

    public void verifyNavigatedToInboundDetailsPage(String cid) {
        waitFor(10);
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        }
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.cIDValueNumber.getText().trim().equalsIgnoreCase(cid));
    }

    private JsonPath getLatestEventByType(String eventType) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        apiClassUtil.setEndPoint("/app/crm/events?size=1&page=0&sort=eventId,desc");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("eventName", eventType);
        apiClassUtil.PostAPIWithParameter(jsonObject);
        return apiClassUtil.jsonPathEvaluator;
    }

    public void verifyLatestInboundEvent(String eventType) {
        String payload = getLatestEventByType(eventType).getString("eventsList.content[0].payload");
        JsonPath jsonPayload = new JsonPath(payload);
        String expectedRecordType = "";
        String expectedAction = "";
        List<String> expectedKeysForSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "inboundCorrespondenceId", "receivedDate", "pageCount", "correspondenceType", "batchNumber", "createdOn", "scannedOn", "setId", "channel", "replacementCorrespondenceId", "rescanOfCorrespondenceId", "returnedReason", "returnedNotificationId", "status", "createdBy", "updatedBy", "updatedOn", "fromName", "fromPhoneNumber", "fromEmailAddress", "formVersion", "statusDate", "origin", "originItemId", "originSetId", "language", "thirdPartyReceivedDate", "barcode", "processType", "caseId", "consumerId", "consumers", "metadata", "metadataRecords");
        List<String> expectedKeysForNoteSaveEvent = Arrays.asList("projectId", "projectName", "action", "recordType", "eventCreatedOn", "dataObject", "inboundCorrespondenceNoteId", "inboundCorrespondenceId", "createdBy", "createdOn", "text");
        switch (eventType) {
            case "INBOUND_CORRESPONDENCE_SAVE_EVENT":
                expectedRecordType = "InboundCorrespondence";
                expectedAction = "Create";
                for (int i = 0; i < expectedKeysForSaveEvent.size(); i++) {
                    if (i < 5) {
                        switch (expectedKeysForSaveEvent.get(i)) {
                            case "action":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForSaveEvent.get(i)), expectedAction, "Action verification failed");
                                break;
                            case "recordType":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForSaveEvent.get(i)), expectedRecordType, "Record type verification failed");
                                break;
                            case "eventCreatedOn":
                                //COMMENTING OUT BC CP-41613 defect rejected
//                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString(expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                                break;
                        }
                        Assert.assertEquals(jsonPayload.getString("projectId"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id verification failed");
                        Assert.assertEquals(jsonPayload.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName(), "Project name verification failed");
                        Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString(expectedKeysForSaveEvent.get(i))));
                    } else {
                        if (!expectedKeysForSaveEvent.get(i).equals("dataObject")) {
                            if (expectedKeysForSaveEvent.get(i).equals("createdOn") || expectedKeysForSaveEvent.get(i).equals("updatedOn") || expectedKeysForSaveEvent.get(i).equals("statusDate")) {
                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                            } else if (expectedKeysForSaveEvent.get(i).equals("receivedDate")) {
                                Assert.assertTrue(EventsUtilities.isOnlyValidDate(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                            }
                            Assert.assertEquals(jsonPayload.getString("projectId"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id verification failed");
                            Assert.assertEquals(jsonPayload.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName(), "Project name verification failed");
                            Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))));
                        }
                    }
                }
                break;
            case "INBOUND_CORRESPONDENCE_NOTE_SAVE_EVENT":
                expectedRecordType = "CorrespondenceNote";
                expectedAction = "Create";
                for (int i = 0; i < expectedKeysForNoteSaveEvent.size(); i++) {
                    if (i < 5) {
                        switch (expectedKeysForNoteSaveEvent.get(i)) {
                            case "action":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForNoteSaveEvent.get(i)), expectedAction, "Action verification failed");
                                break;
                            case "recordType":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForNoteSaveEvent.get(i)), expectedRecordType, "Record type verification failed");
                                break;
                            case "eventCreatedOn":
                                //removing assertion bc CP-41613 is rejected
//                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString(expectedKeysForNoteSaveEvent.get(i))), expectedKeysForNoteSaveEvent.get(i) + " date format verification failed");
                                break;
                        }
                        Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString(expectedKeysForNoteSaveEvent.get(i))));
                    } else {
                        if (!expectedKeysForNoteSaveEvent.get(i).equals("dataObject")) {
                            if (expectedKeysForNoteSaveEvent.get(i).equals("eventCreatedOn") || expectedKeysForNoteSaveEvent.get(i).equals("createdOn")) {
                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString("dataObject." + expectedKeysForNoteSaveEvent.get(i))), expectedKeysForNoteSaveEvent.get(i) + " date format verification failed");
                            }
                            Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString("dataObject." + expectedKeysForNoteSaveEvent.get(i))));
                        }
                    }
                }
                break;
            case "INBOUND_CORRESPONDENCE_UPDATE_EVENT":
                expectedRecordType = "InboundCorrespondence";
                expectedAction = "Update";
                for (int i = 0; i < expectedKeysForSaveEvent.size(); i++) {
                    if (i < 5) {
                        switch (expectedKeysForSaveEvent.get(i)) {
                            case "action":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForSaveEvent.get(i)), expectedAction, "Action verification failed");
                                break;
                            case "recordType":
                                Assert.assertEquals(jsonPayload.getString(expectedKeysForSaveEvent.get(i)), expectedRecordType, "Record type verification failed");
                                break;
                            case "eventCreatedOn":
                                //removing assertion bc CP-41613 is rejected
//                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString(expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                                break;
                        }
                        Assert.assertEquals(jsonPayload.getString("projectId"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id verification failed");
                        Assert.assertEquals(jsonPayload.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName(), "Project name verification failed");
                        Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString(expectedKeysForSaveEvent.get(i))));
                    } else {
                        if (!expectedKeysForSaveEvent.get(i).equals("dataObject")) {
                            if (expectedKeysForSaveEvent.get(i).equals("createdOn") || expectedKeysForSaveEvent.get(i).equals("updatedOn") || expectedKeysForSaveEvent.get(i).equals("statusDate")) {
                                //COMMENTING OUT BC CP-41613 defect rejected
//                                Assert.assertTrue(EventsUtilities.isValidDate(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                            } else if (expectedKeysForSaveEvent.get(i).equals("receivedDate")) {
                                Assert.assertTrue(EventsUtilities.isOnlyValidDate(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))), expectedKeysForSaveEvent.get(i) + " date format verification failed");
                            }
                            Assert.assertEquals(jsonPayload.getString("projectId"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId(), "Project id verification failed");
                            Assert.assertEquals(jsonPayload.getString("projectName"), API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName(), "Project name verification failed");
                            Assert.assertFalse(EventsUtilities.isValueEmptyString(jsonPayload.getString("dataObject." + expectedKeysForSaveEvent.get(i))));
                        }
                    }
                }
                break;
        }
    }

    public void navigateStatusHistoryTab() {
        viewInboundCorrespondenceDetailsUIAPIPage.statusHistoryTab.click();
        waitFor(7);
    }

    public void verifyChangedByCPUSER() {
        waitFor(9);
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.serviceCPUserChangedByColumn.isDisplayed(), "service account not displayed");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.serviceCPUserChangedByColumn.getText().trim(), String.valueOf(World.generalSave.get().get("CPUSERNAME")));
    }

    public void verifyChangedByRawValue() {
        waitFor(9);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.serviceCPUserChangedByColumn.getText().trim(), "AUTOMATION");
    }

    public boolean verifyUpdatedByIsCPUserName(boolean userName) {
        boolean result = false;
        waitFor(7);
        if (userName) {
            result = viewInboundCorrespondenceDetailsUIAPIPage.loggedInUserName.getText().trim().equalsIgnoreCase(viewInboundCorrespondenceDetailsUIAPIPage.updatedbyValue.getText().trim());
        } else {
            result = !viewInboundCorrespondenceDetailsUIAPIPage.loggedInUserName.getText().trim().equalsIgnoreCase(viewInboundCorrespondenceDetailsUIAPIPage.updatedbyValue.getText().trim());
        }
        return result;
    }

    public void verifyEditButtonIBDetailsPage() {
        waitFor(7);
        Assert.assertTrue(inbound.editButton.isDisplayed());
        waitFor(7);
    }

    public void verifySaveAndCancel() {
        waitFor(7);
        Assert.assertTrue(inbound.editSaveButton.isDisplayed());
        Assert.assertTrue(inbound.editCancelButton.isDisplayed());
    }

    public void ibcorrdetailspagesavebutton() {
        viewInboundCorrespondenceDetailsUIAPIPage.editibcorrsavebutton.click();
        waitForVisibility(viewInboundCorrespondenceDetailsUIAPIPage.savemsg, 5);
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.savemsg.isDisplayed());
    }

    public void dateandtimeinreqformat() {

        Date date = new Date();
        expected_editedon.set(correspondenceSettingsPage.topheader_date.getText() + " " + correspondenceSettingsPage.topheader_time.getText());
        waitFor(7);
        World.save.get().put("EST", uiAutoUitilities.get().ConverttoTimeZone("EST", date));
        World.save.get().put("EDT", uiAutoUitilities.get().ConverttoTimeZone("EDT", date));
        World.save.get().put("PST", uiAutoUitilities.get().ConverttoTimeZone("PST", date));
        World.save.get().put("PDT", uiAutoUitilities.get().ConverttoTimeZone("PDT", date));
        World.save.get().put("CST", uiAutoUitilities.get().ConverttoTimeZone("CST", date));
        World.save.get().put("CDT", uiAutoUitilities.get().ConverttoTimeZone("CDT", date));

    }


    public void navigateEditHistoryTab() {
        viewInboundCorrespondenceDetailsUIAPIPage.editHistoryTab.click();
        waitFor(7);
    }

    public void editedonasperprojecttimezone() {

        String zone = correspondenceSettingsPage.topheader_time.getText().split(" ")[2];
        String expected_editedOn = World.save.get().get(zone.toUpperCase());
        String actual_EditedOn = viewInboundCorrespondenceDetailsUIAPIPage.editedonvalue.getText();
        Assert.assertEquals(expected_editedOn, actual_EditedOn);
    }


    public boolean isViewImageIconDisplayed() {
        return uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.viewIconInLinks);
    }

    public void validateEditHistoryTabColumnNames(List<String> expected_columnnames) {

        String zone = correspondenceSettingsPage.topheader_time.getText().split(" ")[2];
        World.save.get().put("projectzone", zone.toUpperCase());

        List<WebElement> actual_columnnames = viewInboundCorrespondenceDetailsUIAPIPage.edithistorycolumnnames;
        for (int i = 0; i < expected_columnnames.size(); i++) {
            String act_colname = actual_columnnames.get(i).getText();
            String exp_colname = expected_columnnames.get(i);
            Assert.assertEquals(act_colname, exp_colname);
        }
    }

    public void captureInactiveLinksto2DArray(JsonPath js, List<String> colnames) {

        int actual_no_of_rows = Integer.parseInt(js.get("externalLinkDetails.totalElements").toString());

        for (int rows = 1; rows <= actual_no_of_rows; rows++) {
            int c = 0;
            String[] s = new String[5];

            String strapidate = js.get("externalLinkDetails.content[" + (rows - 1) + "].createdOn").toString().substring(0, 19) + "Z";
            s[c] = uiAutoUitilities.get().UTCtoCustomTimeZone(World.save.get().get("projectzone"), strapidate);
            s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].createdBy").toString().equals("2425") ? "ECMS Service" : js.get("externalLinkDetails.content[" + (rows - 1) + "].createdBy").toString();
            s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].name").toString();
            s[++c] = js.get("externalLinkDetails.content[1].effectiveEndDate") != null ? js.get("externalLinkDetails.content[1].effectiveEndDate").toString() : "-- --";
            s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].id").toString();

            localGeneralSave.get().put(s[2], s);
        }

    }

    public void captureTheTaskIdFromInactiveLinks(JsonPath js) {
        taskIdFromLinks.set(js.get("externalLinkDetails.content[0].id").toString());
    }

    public void captureInactiveUnLinksto2DArray(JsonPath js, List<String> colnames) {

        //World.save.get().put("projectzone", "EST");
        int actual_no_of_rows = Integer.parseInt(js.get("externalLinkDetails.totalElements").toString());

        System.out.println("actual_no_of_rows : " + actual_no_of_rows);
        for (int rows = 1; rows <= actual_no_of_rows; rows++) {
            int c = 0;
            String[] s = new String[5];

            String strapidate = js.get("externalLinkDetails.content[" + (rows - 1) + "].effectiveEndDate").toString().substring(0, 19) + "Z";
            s[c] = uiAutoUitilities.get().UTCtoCustomTimeZone(World.save.get().get("projectzone"), strapidate);

            if (js.get("externalLinkDetails.content[" + (rows - 1) + "].updatedBy").toString().equals("3455"))
                s[++c] = "Service AccountOne";
            else if (js.get("externalLinkDetails.content[" + (rows - 1) + "].updatedBy").toString().equals("2425"))
                s[++c] = "ECMS Service";
            else
                s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].updatedBy").toString();

            s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].name").toString();
            s[++c] = js.get("externalLinkDetails.content[" + (rows - 1) + "].id").toString();
            s[++c] = js.get("externalLinkDetails.content[1].effectiveEndDate") != null ? "-- --" : js.get("externalLinkDetails.content[" + (rows - 1) + "].id").toString();
            localGeneralSave.get().put(s[2], s);
        }


    }

    public void validateLinkHistoryTabColumnValues(List<String> expected_columnnames) {

        List<WebElement> actual_no_of_rows = Driver.getDriver().findElements(By.xpath("//tbody/tr"));

        for (int rows = 1; rows <= actual_no_of_rows.size(); rows++) {
            List<WebElement> actual_column_values = Driver.getDriver().findElements(By.xpath("//tbody/tr[" + rows + "]/td"));
            String[] s = new String[5];
            for (int columns = 0; columns < expected_columnnames.size(); columns++) {
                s[columns] = actual_column_values.get(columns).getText();
            }
            localRandomMap.get().put(s[2], s);
        }

        /*for (Map.Entry<String, Object> entry : random.entrySet()) {
            System.out.println("rKey : " + entry.getKey() + " rValue : " + Arrays.asList((Object[]) entry.getValue()));
        }*/

    }

    public void validateEditHistoryTabColumnValues(int columnOrder, LinkedList<String> columnValues) {
        List<String> actualColumnElements = viewInboundCorrespondenceDetailsUIAPIPage.returnHistoryTabValuesByColumnOrder(columnOrder);
        for (int i = 0; i < columnValues.size(); i++) {
            if (columnValues.get(i).equalsIgnoreCase("CURRENT TIME")) {
                Assert.assertTrue(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().compareDateTimes(actualColumnElements.get(i), BrowserUtils.getCurrentSystemDateTime()));
                columnValues.remove(i);
                actualColumnElements.remove(i);
            } else if (columnValues.get(i).equalsIgnoreCase("TASK ID")) {
                Assert.assertEquals(actualColumnElements.get(i), taskIdFromLinks.get());
                columnValues.remove(i);
                actualColumnElements.remove(i);
            }
        }
        compareListValues(actualColumnElements, columnValues);
    }

    public void comparevalues() {

        System.out.println(".............comparing................. ");


        Map<String, Object> generalsavetreeMap = new TreeMap<String, Object>(localGeneralSave.get());
        Map<String, Object> randomtreeMap = new TreeMap<String, Object>(localRandomMap.get());


        Iterator<Map.Entry<String, Object>> gitr = generalsavetreeMap.entrySet().iterator();
        Iterator<Map.Entry<String, Object>> ritr = randomtreeMap.entrySet().iterator();

        while (gitr.hasNext() && ritr.hasNext()) {
            Map.Entry<String, Object> gentry = gitr.next();
            Map.Entry<String, Object> rentry = ritr.next();

            Assert.assertEquals(gentry.getKey(), rentry.getKey());
            Assert.assertEquals(Arrays.asList((Object[]) gentry.getValue()), Arrays.asList((Object[]) rentry.getValue()));
        }


    }

    public void reversechronologicaloderofeditedcolumn() {

        List<WebElement> actual_no_of_rows = Driver.getDriver().findElements(By.xpath("//tbody/tr"));
        String[] values = new String[(actual_no_of_rows.size())];

        for (int row = 1; row <= actual_no_of_rows.size(); row++) {

            WebElement el = Driver.getDriver().findElement(By.xpath("//tbody/tr[" + row + "]/td[1]"));
            World.generalList.get().add(el.getText());
        }

        for (int i = 0; i < World.generalList.get().size(); i++) {
            values[i] = World.generalList.get().get(i);
        }
        Assert.assertTrue(values[0].compareTo(values[1]) >= 0);
        Assert.assertTrue(values[0].compareTo(values[2]) >= 0);
        Assert.assertTrue(values[1].compareTo(values[2]) >= 0);
    }

    public void verifySRLinkedToInbDocumentUI() {
        waitFor(7);
        JsonPath original = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(World.generalSave.get().get("InboundDocumentId").toString());
        List<Map<String, Object>> originalDocumentLinks = original.getList("externalLinkDetails.content");
        List<WebElement> rescannedDocumentLinks = viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestIdColumn;
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();
        for (Map<String, Object> originalDocumentLink : originalDocumentLinks) {
            if (("Task".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name")))) || ("Service Request".equalsIgnoreCase(String.valueOf(originalDocumentLink.get("name"))))) {
                expected.add(Integer.parseInt(String.valueOf(originalDocumentLink.get("id"))));
            }
        }
        for (WebElement rescannedDocumentLink : rescannedDocumentLinks) {
            actual.add(Integer.valueOf(validNumberFilter(rescannedDocumentLink.getText().trim())));
        }
        Assert.assertTrue(expected.size() > 0 && actual.size() > 0);
        Collections.sort(expected);
        Collections.sort(actual);
        Assert.assertEquals(actual, expected, " list not the same | " + actual + "\n" + expected);
    }


    public void validatereceiveddate() {
        waitFor(2);
        Assert.assertTrue(inbound.receiveddate.isEnabled());
    }

    public void validatesavingwithoutselectingreceiveddate(String action) {
        waitFor(5);
        String before_value = inbound.receiveddatevalue.getText();
        new OutboundCorrespondenceSettingsPage().editInboundSettings.click();
        waitFor(3);
        if (action.equalsIgnoreCase("save"))
            viewInboundCorrespondenceDetailsUIAPIPage.editibcorrsavebutton.click();
        if (action.equalsIgnoreCase("cancel"))
            viewInboundCorrespondenceDetailsUIAPIPage.editibcorrcancelbutton.click();
        waitFor(3);
        String before_after = inbound.receiveddatevalue.getText();
        Assert.assertEquals(before_after, before_value);


    }

    public void updateibfield(String field, String value, String modeofentryvalue) {
        waitFor(3);
        switch (field) {
            case "type":
                selectDropDown(new InboundCorrespondencePage().inboundTypeDropDown, value);
                break;
            case "receiveddate":
                if (value.equalsIgnoreCase("pastdate"))
                    date.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal().getStartDate("firstdayofLastMonth"));
                else if (value.equalsIgnoreCase("currentdate"))
                    date.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal().getEndDate("currentUIver"));
                else if (value.equalsIgnoreCase("futuredate"))
                    date.set(ApiTestDataUtil.getApiTestDataUtilThreadLocal().getStartDate("firstdayofNextMonth"));
                else
                    date.set(value);

                if (modeofentryvalue.equalsIgnoreCase("manualtyping"))
                    validatereceiveddateviamanualtyping();
                else if (modeofentryvalue.equalsIgnoreCase("calendarwidget"))
                    validatereceiveddateviacalendarwidget(value);
                break;
        }
    }

    public void validatereceiveddateviamanualtyping() {
        waitFor(2);
        inbound.receiveddate.click();
        inbound.receiveddate.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        inbound.receiveddate.sendKeys(Keys.BACK_SPACE);
        inbound.receiveddate.sendKeys(date.get());
    }


    public void validatereceiveddateviacalendarwidget(String value) {
        inbound.calendaricon.click();
        waitFor(1);
        if (value.equalsIgnoreCase("pastdate")) {
            inbound.previousmontharrow.click();
            waitFor(1);
            inbound.selectcalendardate(1).click();
        } else if (value.equalsIgnoreCase("currentdate")) {
            inbound.calendarclearbutton.click();
            waitFor(1);
            int currentday = uiAutoUitilities.get().currentday();
            inbound.calendaricon.click();
            waitFor(1);
            inbound.selectcalendardate(currentday).click();
        } else if (value.equalsIgnoreCase("futuredate")) {
            inbound.nextmontharrow.click();
            waitFor(1);
            inbound.selectcalendardate(1).click();
        }
        waitFor(1);
        inbound.calendarokbutton.click();
    }

    public void validatereceiveddatepersistedtoOnBase(String ibcid) {
        waitFor(5);
        String formateddate = convertMMddyyyyToyyyyMMdd(date.get());
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(ibcid);
        Assert.assertEquals(response.get("inboundCorrespondence.id").toString(), ibcid);
        Assert.assertEquals(response.get("inboundCorrespondence.receivedDate").toString(), formateddate);

    }


    public void verifyMetaDataInformation() {
        ArrayListMultimap<String, String> actualDataMapFromUI = (ArrayListMultimap) World.generalSave.get().get("METADATAMAP");
        JsonArray arr = inbRequest.get().getAsJsonArray("metaData");
        ArrayListMultimap<String, String> expectedDataMap = ArrayListMultimap.create();
        String name = "", value = "";
        for (int i = 0; i < arr.size(); i++) {
            name = arr.get(i).getAsJsonObject().get("name").toString().toUpperCase().trim().replaceAll("\"", "");
            value = arr.get(i).getAsJsonObject().get("value").toString().toUpperCase().trim().replaceAll("\"", "");
            expectedDataMap.put(name, value);
        }
        for (String s : actualDataMapFromUI.keySet()) {
            if (expectedDataMap.containsKey(s)) {
                Assert.assertTrue(actualDataMapFromUI.get(s).containsAll(expectedDataMap.get(s)), " Mismatch in Data. Actual data from UI" + actualDataMapFromUI.get(s) + "Expected data from inbound request" + expectedDataMap.get(s));
            } else {
                Assert.fail("CP UI has an extra Mata Data name-value pair that is missing from the inbound request . Actual data from UI for " + s + "  " + actualDataMapFromUI.get(s) + "Expected data from inbound request" + expectedDataMap.get(s));
            }
        }
    }

    public void verifyMetaDataRecords() {
        List<Map<String, String>> actualRecordsUI = (List) World.generalSave.get().get("METADATARECORDSMAP");
        List<Map<String, String>> expectedRecords = (List) World.generalSave.get().get("VACVNEWBORNS");
        Assert.assertEquals(actualRecordsUI.size(), expectedRecords.size(), "Mismatch in number of metadata records from UI and Inbound request sent to Onbase");
        try {
            for (int i = 0; i < actualRecordsUI.size(); i++) {
                for (String s : actualRecordsUI.get(i).keySet()) {
                    if (s.equalsIgnoreCase("DOB")) {
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        Date date = formatter.parse((expectedRecords.get(i).get(s)));
                        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        expectedRecords.get(i).put(s, newDate);
                    }
                    Assert.assertEquals(actualRecordsUI.get(i).get(s), expectedRecords.get(i).get(s).toUpperCase(), " Mismatch in Data. Actual data from UI" + actualRecordsUI.get(i).get(s) + "Expected data from inbound request" + expectedRecords.get(i).get(s));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in verifyMetaDataRecords " + e.getMessage());
        }
    }

    public void verifyEventForEmptyStrings(JsonPath jsonPath, String eventType) {
        String events = jsonPath.getString("events");
        if (events == null) {
            Assert.fail("There are no events for validation");
        }
        int eventsSize = jsonPath.getInt("events.size()");
        if (eventsSize == 0) {
            Assert.fail("There are no events for validation");
        }
        String actualEventType = "";
        String payload = "";
        boolean hasEmptyString = false;
        boolean hasEvent = false;
        try {
            for (int i = 0; i < eventsSize; i++) {
                actualEventType = jsonPath.getString("events[" + i + "].eventName");
                if (eventType.contains(actualEventType)) {
                    hasEvent = true;
                    payload = jsonPath.getString("events[" + i + "].payload").replaceAll("\\\\", "");
                    if (payload.contains("\"\"")) {
                        hasEmptyString = true;
                    }
                    Assert.assertFalse(hasEmptyString, "Some of the fields in " + eventType + " have empty String instead of null");
                }
            }
            Assert.assertTrue(hasEvent, "Expected event is not found");
        } catch (Exception e) {
            Assert.fail("Failure in verifyEventForEmptyStrings" + e.getMessage());
        }
    }

    public Map<String, String> getInboundCorrespondenceData() {
        Map<String, String> map = new HashMap<>();
        if (isElementPresent(By.xpath("//p[contains(.,'TYPE')]/../h6"))) {
            map.put("Type", inbound.typeDropdownValue.getText().trim());
        } else if (isElementPresent(By.id("_TYPE"))) {
            map.put("Type", inbound.inboundTypeDropDown.getAttribute("value").trim());
        }
        return map;
    }

    public void compareMapValues(Map<String, String> actualMap, Map<String, String> expectedMap) {
        Map<String, String> actualMapT = new TreeMap<>(actualMap);
        Map<String, String> expectedMapT = new TreeMap<>(expectedMap);
        Iterator<Map.Entry<String, String>> aitr = actualMapT.entrySet().iterator();
        Iterator<Map.Entry<String, String>> eitr = expectedMapT.entrySet().iterator();
        while (aitr.hasNext() && eitr.hasNext()) {
            Map.Entry<String, String> aEntry = aitr.next();
            Map.Entry<String, String> eEntry = eitr.next();
            Assert.assertEquals(aEntry.getKey(), eEntry.getKey(), "Map Keys do not match");
            Assert.assertEquals(aEntry.getValue(), eEntry.getValue(), "Map values do not match");
        }

    }

    public void compareListValues(List<String> actualList, List<String> expectedList) {
        Collections.sort(actualList);
        Collections.sort(expectedList);
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(actualList.get(i), expectedList.get(i), "List values do not match.Expected : " + expectedList.get(i) + " .But found " + actualList.get(i));
        }
    }

    public void clickCancelButton() {
        inbound.cancelButton.click();
    }

    public boolean isElementPresent(By by) {
        try {
            Driver.getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void validateWarningMessagePopUp() {
        waitForClickablility(inbound.discardFailMessage, 7);
        Assert.assertTrue(inbound.discardFailMessage.isDisplayed());
        Assert.assertTrue(inbound.warningMsgCancelButton.isDisplayed());
        Assert.assertTrue(inbound.warningMessageCancel.isDisplayed());

    }

    public void initiateSendEventRequestWithFieldValuesFromInboundRequest() {
        int size = inbRequest.get().getAsJsonArray("metaData").size();
        int size2 = inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").size();
        JsonObject sendEventRequest;
        ApiTestDataUtil sendEventTdu = ApiTestDataUtil.getApiTestDataUtilThreadLocal();
        sendEventRequest = sendEventTdu.getJsonFromFile("dms/sendEventBasic.json").jsonElement.getAsJsonObject();
        Assert.assertNotNull(World.generalSave.get().get("InboundDocumentIdDigital"), "World.generalSave.get().get(InboundDocumentIdDigital) is null");
        sendEventRequest.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentHandle", Integer.parseInt((String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital")))));
        sendEventRequest.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentType", "VACV Translation Request");
        sendEventRequest.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentName", "VACV Translation Request  - 8/14/2020");
        sendEventRequest.getAsJsonObject("document").getAsJsonObject("properties").addProperty("documentTypeName", "VACV Translation Request");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiBffService"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/ecms/connectionpoint/sendevent");
        for (int i = 0; i < size; i++) {
            JsonObject kwField = new JsonObject();
            kwField.addProperty("keywordTypeName", inbRequest.get().getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString().replace("\"", ""));
            kwField.addProperty("value", inbRequest.get().getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString().replace("\"", ""));
            sendEventRequest.getAsJsonObject("document").getAsJsonArray("keywordFields").add(kwField);
        }
        JsonArray kwRecordArray = new JsonArray();
        for (int i = 0; i < size2; i++) {
            JsonObject kwRecord = new JsonObject();
            kwRecord.addProperty("keywordTypeName", inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString().replace("\"", ""));
            kwRecord.addProperty("value", inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString().replace("\"", ""));
            kwRecordArray.add(kwRecord);
        }
        JsonObject kwRecord = new JsonObject();
        kwRecord.addProperty("name", "Member");
        sendEventRequest.getAsJsonObject("document").getAsJsonArray("keywordRecords").add(kwRecord);
        sendEventRequest.getAsJsonObject("document").getAsJsonArray("keywordRecords").get(0).getAsJsonObject().add("keywords", kwRecordArray);
        JsonPath response = (JsonPath) API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameterForProject(sendEventRequest, "CoverVA").jsonPathEvaluator;
        System.out.println(response.getJsonObject("message").toString());
    }

    public void verifyPayLoadMetaDataMatchesIBRequest() {
        int inbReqMetaDataListSize = inbRequest.get().getAsJsonArray("metaData").size();
        JsonObject payload = (JsonObject) World.generalSave.get().get("payLoadData");
        int payloadSize = payload.getAsJsonObject("dataObject").getAsJsonArray("metaData").size();
        Map<String, String> inbReqMap = new TreeMap<>();
        Map<String, String> payloadMetaDataMap = new TreeMap<>();
        for (int i = 0; i < inbReqMetaDataListSize; i++) {
            inbReqMap.put(inbRequest.get().getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString(), inbRequest.get().getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString().replace("\"", ""));
        }
        for (int i = 0; i < payloadSize; i++) {
            payloadMetaDataMap.put(payload.getAsJsonObject("dataObject").getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString(), payload.getAsJsonObject("dataObject").getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString().replace("\"", ""));
        }
        inbReqMap.remove("\"ProcessType\"");
        inbReqMap.remove("\"Channel\"");
        compareMapValues(inbReqMap, payloadMetaDataMap);
    }

    public void verifyPayLoadMetaDataRecordsMatchesIBRequest() {
        int inbReqMetaDataRecordsSize = inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").size();
        JsonObject payload = (JsonObject) World.generalSave.get().get("payLoadData");
        int payloadSize2 = payload.getAsJsonObject("dataObject").getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").size();
        Assert.assertEquals(payloadSize2, inbReqMetaDataRecordsSize);
        for (int i = 0; i < inbReqMetaDataRecordsSize; i++) {
            String inbReqName = inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString();
            String inbReqValue = inbRequest.get().getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString();
            String payloadName = payload.getAsJsonObject("dataObject").getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("name").toString();
            String payloadValue = payload.getAsJsonObject("dataObject").getAsJsonArray("metaDataRecords").get(0).getAsJsonObject().getAsJsonArray("metaData").get(i).getAsJsonObject().get("value").toString();
            Assert.assertTrue(inbReqName.equalsIgnoreCase(payloadName));
            Assert.assertTrue(inbReqValue.equalsIgnoreCase(payloadValue));
        }
    }

    public void saveUserName() {
        World.generalSave.get().put("CPUSERNAME", viewInboundCorrespondenceDetailsUIAPIPage.loggedInUserName.getText().trim());
    }

    public void createRequestToUpdateInboundDefinition(String inboundCorType, String tenant, Map<String, String> dataTable) {
        inbRequest.set((JsonObject) API_THREAD_LOCAL_FACTORY.getApitduThreadLocal().getJsonFromFile("dms/InboundCorrespondenceType.json").jsonElement);
        String projectId = "";
        String projectName = "";
        String[] arrayToSplit = null;
        switch (tenant) {
            case "NJ-SBE":
                arrayToSplit = ConfigurationReader.getProperty("njProjectName").split(" ");
                break;
            case "CoverVA":
                arrayToSplit = ConfigurationReader.getProperty("coverVAProjectName").split(" ");
                break;
            case "IN-EB":
                arrayToSplit = ConfigurationReader.getProperty("INEBProjectName").split(" ");
                break;
            case "BLCRM":
                arrayToSplit = ConfigurationReader.getProperty("projectName").split(" ");
                break;
        }
        projectId = arrayToSplit[0];
        projectName = arrayToSplit[1];
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "projectId":
                    if (dataTable.get(keyword).equalsIgnoreCase("valid")) {
                        inbRequest.get().addProperty(keyword, projectId);
                    }
                    break;
                case "projectName":
                    if (dataTable.get(keyword).equalsIgnoreCase("valid")) {
                        inbRequest.get().addProperty(keyword, projectName);
                    }
                    break;
                case "definitionName":
                    if (dataTable.get(keyword).equalsIgnoreCase("invalid")) {
                        inbRequest.get().addProperty("name", "Automation");
                    } else if (dataTable.get(keyword).equalsIgnoreCase("valid")) {
                        inbRequest.get().addProperty("name", inboundCorType);
                    }
                    break;
            }
        }
    }

    public void sendRequestToUpdateInboundDefinition() {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiInboundType"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundcorrespondencedefinition");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(inbRequest.get());
    }

    public void verifyServiceIgnoreRequest(String type) {
        inbRequest.set(new JsonObject());
        inbRequest.get().addProperty("name", type);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiInboundType"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/inboundCorrespondenceDefinition");
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(inbRequest.get());
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
        Assert.assertNull(response.getString("inboundCorrespondence"), "Inbound Correspondence attribute value verification failed");
    }

    public void verifyMissingItemStatus(String status) {
        Assert.assertEquals("REVIEW", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data[0].status"));
    }

    public void closeServiceRequest(String type) {
        switch (type) {
            case "Inbound Service Request":
                selectDropDown(crmCreateGeneralTaskPage.taskStatus, "Closed");
                waitFor(3);
                selectFromMultiSelectDropDown(crmCreateGeneralTaskPage.reasonForEditDrpDn, "Corrected Data Entry");
                waitFor(3);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(2);
                selectDropDown(crmCreateGeneralTaskPage.disposition, "General SR Closed");
                waitFor(1);
                Driver.getDriver().findElement(By.xpath("//body")).click();
                waitFor(1);
                crmCreateGeneralTaskPage.btnSave.click();
                break;
        }
    }

    public void verifyCaseUnlinkedToIB(String inboundDocumentIdDigital, String caseId) {
        JsonPath inbResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceInactiveLinks(inboundDocumentIdDigital);
        JsonPath caseLinkResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCaseLinks(caseId);
        List<Map<String, Object>> inbLinks = inbResponse.getList("externalLinkDetails.content");
        List<Map<String, Object>> caseLinks = caseLinkResponse.getList("externalLinkDetails.content");
        boolean inbLinkFound = false;
        for (Map<String, Object> caseLink : caseLinks) {
            if (String.valueOf(caseLink.get("name")).equalsIgnoreCase("Inbound Correspondence") && String.valueOf(caseLink.get("id")).equalsIgnoreCase(inboundDocumentIdDigital)) {
                Assert.fail("case link found \n" + caseLink.toString());
            }
        }
        for (Map<String, Object> inbLink : inbLinks) {
            if (String.valueOf(inbLink.get("name")).equalsIgnoreCase("Case") && String.valueOf(inbLink.get("id")).equalsIgnoreCase(caseId)) {
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(inbLink.get("effectiveEndDate"))));
                inbLinkFound = true;
            }
        }
        Assert.assertTrue(inbLinkFound);
    }

    public void verifyConsumerUnlinkedToIB(String inboundDocumentIdDigital, String consumerId) {
        JsonPath inbResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceInactiveLinks(inboundDocumentIdDigital);
        JsonPath caseLinkResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getConsumerLinks(consumerId);
        List<Map<String, Object>> inbLinks = inbResponse.getList("externalLinkDetails.content");
        List<Map<String, Object>> consumerLinks = caseLinkResponse.getList("externalLinkDetails.content");
        boolean inbLinkFound = false;
        for (Map<String, Object> caseLink : consumerLinks) {
            if (String.valueOf(caseLink.get("name")).equalsIgnoreCase("Inbound Correspondence") && String.valueOf(caseLink.get("id")).equalsIgnoreCase(inboundDocumentIdDigital)) {
                Assert.fail("consumer link found \n" + caseLink.toString());
            }
        }
        for (Map<String, Object> inbLink : inbLinks) {
            if (String.valueOf(inbLink.get("name")).equalsIgnoreCase("Consumer Profile") && String.valueOf(inbLink.get("id")).equalsIgnoreCase(consumerId)) {
                Assert.assertTrue(EventsUtilities.isValidDate(String.valueOf(inbLink.get("effectiveEndDate"))));
                inbLinkFound = true;
            }
        }
        Assert.assertTrue(inbLinkFound);
    }

    public void verifyApplicationSRLinkedToInbDocument(String appsrId, String cid) {

        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        if ("previouslyCreated".equalsIgnoreCase(appsrId))
            appsrId = String.valueOf(World.generalSave.get().get("Application SR ID"));

        boolean found = false;
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Service Request")) {
                found = true;
                Assert.assertEquals(link.get("id").toString(), appsrId, "Application SR id expected - " + appsrId + ", Application SR id found - " + link.get("id").toString());
                Assert.assertEquals(link.get("internalId").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
            }
        }
        Assert.assertTrue(found, "Link not found");
    }

    public boolean verifyInbDocmentLinkedToApplicationSR(String cid, String appsrId) {

        if ("previouslyCreated".equalsIgnoreCase(appsrId)) {
            appsrId = String.valueOf(World.generalSave.get().get("Application SR ID"));
        }
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        JsonPath response = apiAutoUtilities.get().getTaskByTaskId(appsrId);
        boolean status = false;
        List<Map<String, Object>> links = response.getList("tasks[0].taskLinksVOS");
        for (Map<String, Object> link : links) {
            if (link.get("externalLinkRefType").toString().equalsIgnoreCase("INBOUND_CORRESPONDENCE") && link.get("externalLinkRefId").toString().equalsIgnoreCase(cid)) {
                status = true;
                Assert.assertEquals(link.get("internalRefId").toString(), appsrId, "Application SR id expected - " + appsrId + ", Application SR id found - " + link.get("internalRefId").toString());
            }
        }
        return status;
    }

    public boolean verifyInbDocmentLinkedToTask(String taskId, String cid, int linkCount) {
        BrowserUtils.waitFor(10);
        if ("previouslyCreated".equalsIgnoreCase(taskId)) {
            taskId = String.valueOf(World.generalSave.get().get("TASKID"));
        }
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        JsonPath response = apiAutoUtilities.get().getTaskByTaskId(taskId);
        boolean status = false;
        List<Map<String, Object>> links = response.getList("tasks[0].taskLinksVOS");
        sa.set(new SoftAssertions());
        sa.get().assertThat(links.size() == linkCount).as("Link Count didn't match with expected Link Count").isTrue();
        for (Map<String, Object> link : links) {
            if (link.get("externalLinkRefType").toString().equalsIgnoreCase("INBOUND_CORRESPONDENCE") && link.get("externalLinkRefId").toString().equalsIgnoreCase(cid)) {
                status = true;
            }
        }
        return status;
    }

    public boolean verifySRLinkedToInbDocumentAndSrCountLinkedIBDocument(String cid, String taskId, int expectedSrCount) {
        int actualSrCount = 0;
        taskId = String.valueOf(World.generalSave.get().get("TASKID"));
        if ("fromRequest".equalsIgnoreCase(cid) || "previouslyCreated".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        } else if ("inboundDocumentId".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
        } else if ("SameSetInboundDocument".equalsIgnoreCase(cid)) {
            cid = String.valueOf(World.generalSave.get().get("SameSetInboundDocument"));
        }
        if ("previouslyCreated".equalsIgnoreCase(taskId))
            taskId = String.valueOf(World.generalSave.get().get("TASKID"));

        boolean found = false;
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getInboundCorrespondenceLinks(cid);
        List<Map<String, Object>> links = response.getList("externalLinkDetails.content");
        for (Map<String, Object> link : links) {
            if (link.get("name").toString().equalsIgnoreCase("Service Request")) {
                actualSrCount++;
                if (link.get("id").toString().equalsIgnoreCase(taskId)) {
                    found = true;
                    Assert.assertEquals(link.get("id").toString(), taskId, "SR id expected - " + taskId + ", SR id found - " + link.get("id").toString());
                    Assert.assertEquals(link.get("internalId").toString(), cid, "doc id expected - " + cid + ", doc id found - " + link.get("internalId").toString());
                }
            }
        }
        sa.get().assertThat(expectedSrCount == actualSrCount).as("Expected Sr count didn't match with the Actual Sr Count").isTrue();
        return found;
    }

    public boolean validateibfieldvalues(String field, List<String> dataTable) {
        waitFor(3);
        boolean status = false;
        switch (field) {
            case "type":
                viewInboundCorrespondenceDetailsUIAPIPage.typedropdown.click();
                waitFor(3);
                List<WebElement> ibtypes = viewInboundCorrespondenceDetailsUIAPIPage.typedropdownvalues;
                for (String expectedtype : dataTable) {
                    for (WebElement actualtype : ibtypes) {
                        if (actualtype.getText().equalsIgnoreCase(expectedtype)) {
                            status = true;
                            System.out.println(expectedtype + "avaialble in type dropdown");
                        }
                    }
                }
                break;
            default:
                Assert.fail("no matching field");

        }
        return status;
    }

    public void sendIBInvalidDigitalRequest() {
        World.generalSave.get().put("JsonInbRequest", inbRequest.get());
        JsonPath inbResponse = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().sendInboundDigitalRequest(inbRequest.get());
        World.generalSave.get().put("inbResponse", inbResponse);
    }
}
