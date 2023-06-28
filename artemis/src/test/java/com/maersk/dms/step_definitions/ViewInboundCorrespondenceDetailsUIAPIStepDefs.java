package com.maersk.dms.step_definitions;

import com.google.common.collect.ArrayListMultimap;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.TenantManagerOutboundCorrespondeceDefinitionDetailsPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ViewInboundCorrespondenceDetailsUIAPIStepDefs extends CRMUtilities implements ApiBase {

    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubDPB = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    TenantManagerOutboundCorrespondeceDefinitionDetailsPage tMOCD = new TenantManagerOutboundCorrespondeceDefinitionDetailsPage();
    ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);

    public void clickOnGivenID(String id) {
        BrowserUtils.waitFor(2);
//        pubDPB.get().clickIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrShow5Default);
//        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='Show 20']")));
        int i = 1;
        for (WebElement element : viewInboundCorrespondenceDetailsUIAPIPage.corrPageNums) {
            browserUtils.get().hover(viewInboundCorrespondenceDetailsUIAPIPage.footer);
            try {
                WebElement eID = Driver.getDriver().findElement(By.xpath("//td[text()='" + id + "']"));
                browserUtils.get().hover(eID);
                browserUtils.get().jsClick(eID);
                break;
            } catch (NoSuchElementException e) {
                browserUtils.get().hover(viewInboundCorrespondenceDetailsUIAPIPage.corrPageNums.get(i));
                pubDPB.get().clickIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.corrPageNums.get(i));
                continue;
            } finally {
                i++;
            }
        }
        BrowserUtils.waitFor(2);
        Assert.assertTrue(inboundCorrDetHeaderDisplayed());
    }

    public boolean inboundCorrDetHeaderDisplayed() {
        return pubDPB.get().elementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.inboundCorrDetHeader);
    }

    public Map<String, String> getAllInfoViewInboundCorrDet() {
        BrowserUtils.waitFor(4);
        Map<String, String> infoUI = new HashMap<>();
        infoUI.put("cID", "\"id\":" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.cID).replace("CID:", ""));
        infoUI.put("createdBy", "\"createdBy\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.createdBy));
        infoUI.put("createdOn", "\"createdOn\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.createdOn));
        infoUI.put("updatedBy", "\"updatedBy\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.updatedBy));
        infoUI.put("updatedOn", "\"updatedOn\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.updatedOn));
        infoUI.put("correspondenceType", "\"correspondenceType\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.correspondenceType));
        infoUI.put("language", "\"language\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.language));
        infoUI.put("receivedDate", "\"receivedDate\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.receivedDate));
        infoUI.put("pageCount", "\"pageCount\":" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.pageCount));
        infoUI.put("status", "\"status\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.status));
        infoUI.put("statusDate", "\"statusDate\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.statusDate));
        infoUI.put("setId", "\"setId\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.setId));
        infoUI.put("thirdPartyReceivedDate", "\"thirdPartyReceivedDate\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.thirdPartyRD));
        infoUI.put("formVersion", "\"formVersion\":" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.formVersion));
        infoUI.put("returnedNotificationId", "\"returnedNotificationId\":" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.returnedNotificationId));
        infoUI.put("replacementCorrespondenceId", "\"replacementCorrespondenceId\":" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.replacementCorrespondenceId));
        infoUI.put("channel", "\"channel\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.channel));
        infoUI.put("origin", "\"origin\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.origin));
        infoUI.put("originItemId", "\"originItemId\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.originItemId));
        infoUI.put("originSetId", "\"originSetId\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.originSetId));
        infoUI.put("batchNumber", "\"batchNumber\":" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.batchNumber).trim());
        infoUI.put("processType ", "\"processType\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.scanBatchClass));
        infoUI.put("scannedOn", "\"scannedOn\":\"" + getTextIfElementIsDisplayedRemoveDDMMZeros(viewInboundCorrespondenceDetailsUIAPIPage.scannedOn).substring(0, 9));
        infoUI.put("fromPhoneNumber", "\"fromPhoneNumber\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.fromPhoneNumber).replace("-", "").trim());
        infoUI.put("fromEmailAddress", "\"fromEmailAddress\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.fromEmailAddress).trim());
        infoUI.put("fromName", "\"fromName\":\"" + pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.fromName).trim());

        String signed = "\"name\":\"Signed\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.signed);
        String mailAttachmentCount = "\"name\":\"MAIL Attachment Count\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailAttachmentCount);
        String mailAttachmentName = "\"name\":\"MAIL Attachment Name\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailAttachmentName);
        String mailDTReceived = "\"name\":\"MAIL Date Time Received\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailDTReceived);
        String mailFrom = "\"name\":\"MAIL From\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailFrom);
        String mailSubject = "\"name\":\"MAIL Subject\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailSubject);
        String mailTo = "\"name\":\"MAIL To\",\"value\":\"" + getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.mailTo);

        infoUI.put("signed", signed);
        infoUI.put("mailAttachmentCount", mailAttachmentCount);
        infoUI.put("mailAttachmentName", mailAttachmentName);
        infoUI.put("mailDTReceived", mailDTReceived);
        infoUI.put("mailFrom", mailFrom);
        infoUI.put("mailSubject", mailSubject);
        infoUI.put("mailTo", mailTo);

        ArrayList<String> statusHistoryStatuses = getTextWebElements(Driver.getDriver().findElements(By.xpath("//th[text()='STATUS']/parent::thead/following::tbody[1]/tr/td[1]")));
        ArrayList<String> statusHistoryReasons = getTextWebElements(Driver.getDriver().findElements(By.xpath("//th[text()='STATUS']/parent::thead/following::tbody[1]/tr/td[2]")));
        ArrayList<String> statusHistoryCreatedOnDates = getTextWebElements(Driver.getDriver().findElements(By.xpath("//th[text()='STATUS']/parent::thead/following::tbody[1]/tr/td[3]")));
        ArrayList<String> statusHistoryCreatedByValues = getTextWebElements(Driver.getDriver().findElements(By.xpath("//th[text()='STATUS']/parent::thead/following::tbody[1]/tr/td[4]")));
        for (int i = 0; i <= statusHistoryStatuses.size() - 1; i++) {
            String statusHistory = "\"createdOn\":\"" + statusHistoryCreatedOnDates.get(i) + "\"," +
                    "\"createdBy\":\"" + statusHistoryCreatedByValues.get(i) + "\"," +
                    "\"action\":\"" + statusHistoryReasons.get(i) + "\"," +
                    "\"status\":\"" + statusHistoryStatuses.get(i) + "\"";

            infoUI.put("statusHistory " + i, statusHistory);
        }

        ArrayList<String> notesCreatedBy = getTextWebElements(Driver.getDriver().findElements(By.xpath("//p[contains(text(),'NOTE  ')]/../h6")));
        ArrayList<String> notesCreatedON = getTextIfElementIsDisplayedRemoveDDMMZeros(Driver.getDriver().findElements(By.xpath("//p[contains(text(),'NOTE  ')]/../p[1]")));
        for (int i = 0; i <= notesCreatedBy.size() - 1; i++) {
            String statusHistory = "\"createdBy\":\"" + notesCreatedBy.get(i) + "\"," +
                    "\"createdOn\":\"" + notesCreatedON.get(i).substring(0, 14);

            infoUI.put("statusHistory " + i, statusHistory);

        }

        return infoUI;
    }

    public String inboundDocumentDetailsDocId(String accessToken, String iD) {
        String responseString = null;
        Response responseBody = given()
                .header("project-name", "BLCRM")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .get("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/correspondence/inbound/document/" + iD);
        if (responseBody.getStatusCode() == 200) {
            responseString = responseBody.asString();
        }
        return responseString;
    }

    public ArrayList<String> getTextWebElements(List<WebElement> el) {
        ArrayList<String> returnedText = new ArrayList<>();
        for (WebElement e : el) {
            if (pubDPB.get().elementIsDisplayed(e)) {
                if (e.getText().compareTo("-- --") == 0) {
                    returnedText.add("Add Keyword");
                } else if (e.getText().contains("account_circle\n")) {
                    returnedText.add(pubDPB.get().getTextIfElementIsDisplayed(e).replace("account_circle\n", ""));
                } else {
                    returnedText.add(pubDPB.get().getTextIfElementIsDisplayed(e));
                }
            }
        }
        return returnedText;
    }

    public String getTextIfElementIsDisplayedRemoveDDMMZeros(WebElement el) {
        String formatted = null, strict;
        try {
            if (el.isDisplayed()) {
                strict = el.getText();
                String[] ddmm = strict.split("/");

                if (ddmm[0].startsWith("0")) {
                    formatted = ddmm[0].replace("0", "") + "/";
                } else {
                    formatted = ddmm[0] + "/";
                }

                if (ddmm[1].startsWith("0")) {
                    formatted = formatted + (ddmm[1].replace("0", "") + "/");
                } else {
                    formatted = formatted + ddmm[1] + "/";
                }

                if (ddmm[2].contains(":")) {
                    String[] time = ddmm[2].split(" ");
                    if (time[1].startsWith("0")) {
                        String[] timeHr = time[1].split(":");
                        formatted = formatted + time[0] + " " + timeHr[0].replace("0", "") + ":" + timeHr[1];
                    }
                } else {
                    formatted = formatted + ddmm[2];
                }

            }
        } catch (NoSuchElementException e) {
            return String.valueOf(false);
        }
        return formatted;
    }

    public ArrayList<String> getTextIfElementIsDisplayedRemoveDDMMZeros(List<WebElement> el) {
        String formatted = null, strict;
        ArrayList<String> dates = new ArrayList<>();
        try {
            for (WebElement e : el)
                if (e.isDisplayed()) {
                    strict = e.getText();
                    String[] ddmm = strict.split("/");

                    if (ddmm[0].startsWith("0")) {
                        formatted = ddmm[0].replace("0", "") + "/";
                    } else {
                        formatted = ddmm[0] + "/";
                    }

                    if (ddmm[1].startsWith("0")) {
                        formatted = formatted + (ddmm[1].replace("0", "") + "/");
                    } else {
                        formatted = formatted + ddmm[1] + "/";
                    }

                    if (ddmm[2].contains(":")) {
                        String[] time = ddmm[2].split(" ");
                        if (time[1].startsWith("0")) {
                            String[] timeHr = time[1].split(":");
                            formatted = formatted + time[0] + " " + timeHr[0].replace("0", "") + ":" + timeHr[1];
                        }
                    } else {
                        formatted = formatted + ddmm[2];
                    }

                    dates.add(formatted);
                }
        } catch (NoSuchElementException e) {
        }
        return dates;
    }


    public String getTextIfElementIsDisplayed(WebElement el) {
        String strict = null;
        try {
            if (el.isDisplayed()) {
                strict = el.getText();
                if (strict.compareTo("-- --") == 0 || strict.isEmpty()) {
                    return "0";
                } else if (strict.contains(" MAIL Date Time Received :  ")) {
                    return strict.replace(" MAIL Date Time Received :  ", "").trim();
                } else if (strict.contains(" MAIL Attachment Count :  ")) {
                    return strict.replace(" MAIL Attachment Count :  ", "").trim();
                } else if (strict.contains(" MAIL Attachment Name :  ")) {
                    return strict.replace(" MAIL Attachment Name :  ", "").trim();
                } else if (strict.contains(" MAIL To :  ")) {
                    return strict.replace(" MAIL To :  ", "").trim();
                } else if (strict.contains(" MAIL From :  ")) {
                    return strict.replace(" MAIL From :  ", "").trim();
                } else if (strict.contains(" MAIL Subject :  ")) {
                    return strict.replace(" MAIL Subject :  ", "").trim();
                } else if (strict.contains(" Signed :  ")) {
                    return strict.replace(" Signed :  ", "").trim();
                }
            }
        } catch (NoSuchElementException e) {
            return String.valueOf(false);
        }
        return strict;
    }

    public void selectFirstInListFromInboundCorrespondenceComponent() {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(5);
        browserUtils.get().scrollToElement(viewInboundCorrespondenceDetailsUIAPIPage.inboundCIDs.get(0));
        BrowserUtils.waitFor(1);
        browserUtils.get().hover(viewInboundCorrespondenceDetailsUIAPIPage.inboundCIDs.get(0));
        BrowserUtils.waitFor(1);
        viewInboundCorrespondenceDetailsUIAPIPage.inboundCIDs.get(0).click();

    }

    public void clickViewIconInbDocumentGenerated() {
        if (World.generalSave.get().get("InboundDocumentId").toString().length() > 1) {
            browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.cIDValueNumber, 10);
        }
        BrowserUtils.waitFor(2);
        browserUtils.get().hover(viewInboundCorrespondenceDetailsUIAPIPage.viewIcon);
        BrowserUtils.waitFor(2);
        viewInboundCorrespondenceDetailsUIAPIPage.viewIcon.click();
        BrowserUtils.waitFor(7);
    }

    public void verifyTabsNames() {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab.isDisplayed(), "source details tab not found");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.documentSetDetailsTab.isDisplayed(), "documentSetDetailsTab tab not found");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.metadataTab.isDisplayed(), "metadataTab not found");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.statusHistoryTab.isDisplayed(), "statusHistoryTab not found");
    }

    public void verifyValuesSourceDetailsTabFromApi(Map<String, String> dataTable) {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        String cid = "--:--";
        for (int timeout = 0; timeout < 7; timeout++) {
            cid = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.cID.getText().trim());
            if (!"--:--".equalsIgnoreCase(cid) && cid.trim().length() > 1) {
                break;
            } else {
                BrowserUtils.waitFor(4);
            }
        }
        if (BrowserUtils.validNumberFilter(cid).length() < 1) {
            Assert.fail("failed to get cid from text | " + cid);
        }
        JsonPath inbDetails =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(BrowserUtils.validNumberFilter(cid));
        for (String uiValue : dataTable.keySet()) {
            switch (uiValue.toUpperCase()) {
                case "SOURCE BATCH ID":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.sourceBatchIdValue.getText().trim(), inbDetails.getString("inboundCorrespondence.batchNumber"), "SOURCE BATCH ID NOT MATCHING");
                    break;
                case "SCAN BATCH CLASS":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.scanBatchClassValue.getText().trim(), inbDetails.getString("inboundCorrespondence.processType"), "SCAN BATCH CLASS NOT MATCHING");
                    break;
                case "SCAN TIMESTAMP":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.scanTimestampValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateTimeToDateTimeWith12HrClock(inbDetails.getString("inboundCorrespondence.scannedOn"),ZoneOffset.ofHours(-5)), "SCAN TIMESTAMP NOT MATCHING");
                    break;
                case "ORIGIN":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originValue.getText().trim(), inbDetails.getString("inboundCorrespondence.origin"), "ORIGIN NOT MATCHING");
                    break;
                case "ORIGIN ID":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originIdValue.getText().trim(), inbDetails.getString("inboundCorrespondence.originItemId"), "ORIGIN ID NOT MATCHING");
                    break;
                case "ORIGIN SET ID":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originSetIdValue.getText().trim(), inbDetails.getString("inboundCorrespondence.originSetId"), "ORIGIN SET ID NOT MATCHING");
                    break;
                case "THIRD PARTY RECEIVED DATE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.thirdPartyReceivedDateValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(inbDetails.getString("inboundCorrespondence.thirdPartyReceivedDate")), "thirdPartyReceivedDate NOT MATCHING");
                    break;
                case "FROM EMAIL":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fromEmailAddressValue.getText().trim(), inbDetails.getString("inboundCorrespondence.fromEmailAddress"), "FROM EMAIL NOT MATCHING");
                    break;
                case "FROM PHONE NUMBER":
                    Assert.assertEquals(BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.fromPhoneNumberValue.getText().trim()), inbDetails.getString("inboundCorrespondence.fromPhoneNumber"), "FROM PHONE NUMBER NOT MATCHING");
                    break;
                case "FROM NAME":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fromNameValue.getText().trim(), inbDetails.getString("inboundCorrespondence.fromName"), "FROM NAME NOT MATCHING");
                    break;
                case "CHANNEL":
                    Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.channel.getText().trim().contains(inbDetails.getString("inboundCorrespondence.channel")), "channel on source details tab is missing | " + viewInboundCorrespondenceDetailsUIAPIPage.channel.getText().trim());
                    break;
            }
        }
    }

    public void verifyValuesMetadataTabFromApi(Map<String, String> dataTable) {

    }

    public void verifyValuesStatusHistoryTabFromApi(Map<String, String> dataTable) {

    }

    public void verifyLabelsSourceDetailsTabFromApi(Map<String, String> dataTable) {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        String cid = "--:--";
        for (int timeout = 0; timeout < 7; timeout++) {
            cid = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.cID.getText().trim());
            if (!"--:--".equalsIgnoreCase(cid) && cid.trim().length() > 1) {
                break;
            } else {
                BrowserUtils.waitFor(4);
            }
        }
        if (BrowserUtils.validNumberFilter(cid).length() < 1) {
            Assert.fail("failed to get cid from text | " + cid);
        }
        JsonPath inbDetails =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(BrowserUtils.validNumberFilter(cid));
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.sourceBatchIdLabel.getText().trim(), "SOURCE BATCH ID", "SOURCE BATCH ID label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.scanBatchClassLabel.getText().trim(), "SCAN BATCH CLASS", "SCAN BATCH CLASS label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.scanTimestampLabel.getText().trim(), "SCAN TIMESTAMP", "SCAN TIMESTAMP label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originLabel.getText().trim(), "ORIGIN", "ORIGIN label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originIdLabel.getText().trim(), "ORIGIN ID", "ORIGIN ID label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.originSetIdLabel.getText().trim(), "ORIGIN SET ID", "ORIGIN SET ID label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.thirdPartyReceivedDateLabel.getText().trim(), "THIRD PARTY RECEIVED DATE", "THIRD PARTY RECEIVED DATE label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fromEmailAddressLabel.getText().trim(), "FROM EMAIL", "FROM EMAIL label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fromPhoneNumberLabel.getText().trim(), "FROM PHONE NUMBER", "FROM PHONE NUMBER label not found");
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fromNameLabel.getText().trim(), "FROM NAME", "FROM NAME label not found");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.viewIcon.isDisplayed(), "view icon is not displayed");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.channel.getText().trim().contains(inbDetails.getString("inboundCorrespondence.channel")), "channel on source details tab is missing | " + viewInboundCorrespondenceDetailsUIAPIPage.channel.getText().trim());
        String status = inbDetails.getString("inboundCorrespondence.status");
        if (!inbDetails.getString("inboundCorrespondence.status").equalsIgnoreCase("RESCAN REQUESTED")) {
            Assert.assertTrue((!inbDetails.getString("inboundCorrespondence.status").equalsIgnoreCase("RESCAN REQUESTED")) && (viewInboundCorrespondenceDetailsUIAPIPage.rescanButton.getText().trim().contains("RE-SCAN")), "Rescan button issue | status - " + status);
        } else {
            Assert.assertFalse((!inbDetails.getString("inboundCorrespondence.status").equalsIgnoreCase("RESCAN REQUESTED")) && (viewInboundCorrespondenceDetailsUIAPIPage.rescanButton.getText().trim().contains("RE-SCAN")), "Rescan button issue | status - " + status);
        }
    }

    public void verifyLabelsMetadataTabFromApi(Map<String, String> dataTable) {

    }

    public void verifyLabelsHistoryTabFromApi(Map<String, String> dataTable) {
        for (String labels : dataTable.keySet()) {
            switch (labels){
                case "EDITED ON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.editedOnLabel.getText().trim(), "EDITED ON", "EDITED ON label not found");
                    break;

                case "EDITED BY":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.editedByLabel.getText().trim(), "EDITED BY", "EDITED BY label not found");
                    break;

                case "FIELD LABEL":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.fieldLabel.getText().trim(), "FIELD LABEL", "FIELD LABEL label not found");
                    break;

                case "PREVIOUS VALUE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.previousValueLabel.getText().trim(), "PREVIOUS VALUE", "PREVIOUS VALUE label not found");
                    break;

                case "UPDATED VALUE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedValueLabel.getText().trim(), "UPDATED VALUE", "UPDATED VALUE label not found");
                    break;


            }
        }
    }

    public void verifyLabelsUPPERSECTIONFromApi(Map<String, String> dataTable) {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        String cid = "--:--";
        for (int timeout = 0; timeout < 7; timeout++) {
            cid = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.cID.getText().trim());
            if (!"--:--".equalsIgnoreCase(cid) && cid.trim().length() > 1) {
                break;
            } else {
                BrowserUtils.waitFor(4);
            }
        }
        if (BrowserUtils.validNumberFilter(cid).length() < 1) {
            Assert.fail("failed to get cid from text | " + cid);
        }
        JsonPath inbDetails =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(BrowserUtils.validNumberFilter(cid));
        for (String label : dataTable.keySet()) {
            switch (label) {
                case "CREATED BY":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.createdbyLabel.getText().trim(), "CREATED BY", "CREATED BY label not found");
                    break;
                case "CREATED ON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.createdonlabel.getText().trim(), "CREATED ON", "CREATED ON label not found");
                    break;
                case "UPDATED BY":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedbyLabel.getText().trim(), "UPDATED BY", "UPDATED BY label not found");
                    break;
                case "UPDATED ON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedonLablel.getText().trim(), "UPDATED ON", "UPDATED ON label not found");
                    break;
                case "TYPE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeLabel.getText().trim(), "TYPE", "TYPE label not found");
                    break;
                case "PAGE COUNT":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.pageCountLabel.getText().trim(), "PAGE COUNT", "PAGE COUNT label not found");
                    break;
                case "RECEIVED DATE ":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.receivedDateLabel.getText().trim(), "RECEIVED DATE", "RECEIVED DATE label not found");
                    break;
                case "STATUS":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.statusLabel.getText().trim(), "STATUS", "STATUS label not found");
                    break;
                case "STATUS DATE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.statusdateLabel.getText().trim(), "STATUS DATE", "STATUS DATE label not found");
                    break;

                //below values are conditionally hidden when not populated
                case "RESCANNED AS CID":
                    if (inbDetails.getString("inboundCorrespondence.replacementCorrespondenceId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.rescannedAsCidLabel.getText().trim(), "RESCANNED AS CID", "RESCANNED AS CID label not found");
                    }
                    break;
                case "RESCAN OF CID":
                    if (inbDetails.getString("inboundCorrespondence.rescanOfCorrespondenceId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.rescannedOfCidLabel.getText().trim(), "RESCAN OF CID", "RESCAN OF CID label not found");
                    }
                    break;
                case "RETURNED NOTIFICATION ID":
                    if (inbDetails.getString("inboundCorrespondence.returnedNotificationId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.returnedNidLabel.getText().trim(), "RETURNED NOTIFICATION ID", "RETURNED NOTIFICATION ID label not found");
                    }
                    break;
                case "RETURNED REASON":
                    if (inbDetails.getString("inboundCorrespondence.returnedReason").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.returnedReasonLabel.getText().trim(), "RETURNED REASON", "RETURNED REASON label not found");
                    }
                    break;
                case "LANGUAGE":
                    if (inbDetails.getString("inboundCorrespondence.language").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.languageLabel.getText().trim(), "LANGUAGE", "LANGUAGE label not found");
                    }
                    break;
                case "FORM VERSION":
                    if (inbDetails.getString("inboundCorrespondence.correspondenceType").equalsIgnoreCase("maersk Case + Consumer") && inbDetails.getString("inboundCorrespondence.formVersion").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.formVersionLabel.getText().trim(), "FORM VERSION", "FORM VERSION label not found");
                    }
                    break;
            }
        }
    }

    public void verifyValuesUPPERSECTIONFromApi(Map<String, String> dataTable) {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(2);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        String cid = "--:--";
        for (int timeout = 0; timeout < 7; timeout++) {
            cid = BrowserUtils.validNumberFilter(viewInboundCorrespondenceDetailsUIAPIPage.cID.getText().trim());
            if (!"--:--".equalsIgnoreCase(cid) && cid.trim().length() > 1) {
                break;
            } else {
                BrowserUtils.waitFor(4);
            }
        }
        if (BrowserUtils.validNumberFilter(cid).length() < 1) {
            Assert.fail("failed to get cid from text | " + cid);
        }
        JsonPath inbDetails =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(BrowserUtils.validNumberFilter(cid));
        for (String values : dataTable.keySet()) {
            switch (values) {
                case "CREATED BY":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.createdbyValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createdByInbDoc(inbDetails.getString("inboundCorrespondence.createdBy")), "CREATED BY Value not found");
                    break;
                case "CREATED ON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.createdonValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateTimeToDateTimeWith12HrClock(inbDetails.getString("inboundCorrespondence.createdOn")).substring(0, 10).trim(), "CREATED ON Value not found");
                    break;
                case "UPDATED BY":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedbyValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createdByInbDoc(inbDetails.getString("inboundCorrespondence.updatedBy")), "UPDATED BY Value not found");
                    break;
                case "UPDATED ON":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.updatedonValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateTimeToDateTimeWith12HrClock(inbDetails.getString("inboundCorrespondence.updatedOn")).substring(0, 10).trim(), "UPDATED ON Value not found");
                    break;
                case "TYPE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.typeValue.getText().trim(), inbDetails.getString("inboundCorrespondence.correspondenceType"), "TYPE Value not found");
                    break;
                case "PAGE COUNT":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.pageCountValue.getText().trim(), inbDetails.getString("inboundCorrespondence.pageCount"), "Page count Value not found");
                    break;
                case "RECEIVED DATE ":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.receivedDateValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(inbDetails.getString("inboundCorrespondence.receivedDate")).substring(0, 10).trim(), "RECEIVED DATE Value not found");
                    break;
                case "STATUS":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.statusValue.getText().trim(), inbDetails.getString("inboundCorrespondence.status"), "STATUS Value not found");
                    break;
                case "STATUS DATE":
                    Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.statusdateValue.getText().trim(),API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().convertDateyyyyMMddToMMddyyyy(inbDetails.getString("inboundCorrespondence.statusDate")).substring(0, 10).trim(), "STATUS DATE Value not found");
                    break;
                //below values are conditionally hidden when not populated
                case "RESCANNED AS CID":
                    if (inbDetails.getString("inboundCorrespondence.replacementCorrespondenceId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.rescannedAsCidValue.getText().trim(), inbDetails.getString("inboundCorrespondence.replacementCorrespondenceId"), "RESCANNED AS CID Value not found");
                    }
                    break;
                case "RESCAN OF CID":
                    if (inbDetails.getString("inboundCorrespondence.rescanOfCorrespondenceId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.rescannedOfCidValue.getText().trim(), inbDetails.getString("inboundCorrespondence.rescanOfCorrespondenceId"), "RESCAN OF CID Value not found");
                    }
                    break;
                case "RETURNED NOTIFICATION ID":
                    if (inbDetails.getString("inboundCorrespondence.returnedNotificationId").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.returnedNidValue.getText().trim(), inbDetails.getString("inboundCorrespondence.returnedNotificationId"), "RETURNED NOTIFICATION ID Value not found");
                    }
                    break;
                case "RETURNED REASON":
                    if (inbDetails.getString("inboundCorrespondence.returnedReason").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.returnedReasonValue.getText().trim(), inbDetails.getString("inboundCorrespondence.returnedReason"), "RETURNED REASON Value not found");
                    }
                    break;
                case "LANGUAGE":
                    if (inbDetails.getString("inboundCorrespondence.language").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.languageValue.getText().trim(), inbDetails.getString("inboundCorrespondence.language"), "LANGUAGE Value not found");
                    }
                    break;
                case "FORM VERSION":
                    if (inbDetails.getString("inboundCorrespondence.correspondenceType").equalsIgnoreCase("maersk Case + Consumer") && inbDetails.getString("inboundCorrespondence.formVersion").length() > 0) {
                        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.formVersionValue.getText().trim(), inbDetails.getString("inboundCorrespondence.formVersion"), "Form Version Value not found");
                    }
                    break;
            }
        }
    }

    public void verifyHiddenLabels(Map<String, String> dataTable) {
        browserUtils.get().waitForPageToLoad(15);
        BrowserUtils.waitFor(15);
        browserUtils.get().waitForClickablility(viewInboundCorrespondenceDetailsUIAPIPage.sourceDetailsTab, 10);
        for (String label : dataTable.keySet()) {
            switch (label) {
                case "RESCANNED AS CID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.rescannedAsCidLabel), "RESCANNED AS CID label not hidden");
                    break;
                case "RESCAN OF CID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.rescannedOfCidLabel), "RESCAN OF CID label not hidden");
                    break;
                case "RETURNED NOTIFICATION ID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.returnedNidLabel), "RETURNED NOTIFICATION ID label not hidden");
                    break;
                case "RETURNED REASON":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.returnedReasonLabel), "RETURNED REASON label not hidden");
                    break;
                case "LANGUAGE":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.languageLabel), "LANGUAGE label not hidden");
                    break;
                case "FORM VERSION":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.formVersionLabel), "FORM VERSION label not hidden");
                    break;
            }
        }
    }

    public void clickMetaDataTab(){
        System.out.println("Correspondence Id  " + World.generalSave.get().get("InboundDocumentIdDigital")); //TO remove
        Assert.assertTrue(Driver.getDriver().getTitle().contains("View Inbound Correspondence Details"), "Page title for View Inbound Correspondence Details does not matched");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.metadataTab.isDisplayed(), "metadataTab not found");
        viewInboundCorrespondenceDetailsUIAPIPage.metadataTab.click();
    }

    public void getMetaDataInformation(){
        ArrayListMultimap<String, String> map = ArrayListMultimap.create();
        String key="",value="";
        List<WebElement> infoElements = Driver.getDriver().findElements(By.xpath("//div[@id='MetaData-Tab']//div[@class='clearfix']//following-sibling::span//p"));
        for (int i=0;i<infoElements.size(); i++){
            Assert.assertTrue(infoElements.get(i).isDisplayed());
            key =infoElements.get(i).getText().substring(0,infoElements.get(i).getText().indexOf(':')).trim().toUpperCase();
            value =infoElements.get(i).getText().substring(infoElements.get(i).getText().indexOf(':')+1).trim().toUpperCase();
            map.put(key,value);
        }
        World.generalSave.get().put("METADATAMAP" , map);
    }

    public void getMetaDataRecords(){
        List<Map<String,String>> list= new ArrayList<>();
        String key="",value="";
        List<WebElement> metaRecordHeader = Driver.getDriver().findElements(By.xpath("//div[@id='MetaData-Tab']//tbody//tr//th"));
        for(int i=0; i<metaRecordHeader.size();i++){
            List<WebElement> metaRecordData = Driver.getDriver().findElements(By.xpath("//div[@id='MetaData-Tab']//tbody//tr["+(i+1)+"]//td//p"));
            Map<String, String> metaRecordRowData = new HashMap<>();
            for(int j=0;j<metaRecordData.size();j++){
                key=metaRecordData.get(j).getText().substring(0,metaRecordData.get(j).getText().indexOf(':')).trim();
                value=metaRecordData.get(j).getText().substring(metaRecordData.get(j).getText().indexOf(':')+1).trim().toUpperCase();
                metaRecordRowData.put(key,value);
            }
            list.add(metaRecordRowData);
        }
        World.generalSave.get().put("METADATARECORDSMAP" , list);
    }

    public void verifyStatusHistoryGone() {
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.oldStatusHistoryTab));
    }
}
