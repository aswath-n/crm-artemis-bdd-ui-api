
package com.maersk.dms.step_definitions;

import com.maersk.crm.step_definitions.CRM_TaskManagementStepDef;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.InboundCorrespondencePage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static com.maersk.crm.api_step_definitions.APIContactRecordController.apicontactRecordId;
import static com.maersk.crm.utilities.World.generalSave;
import static org.testng.Assert.*;

public class ShowOutboundCorrespondenceDetailsStepDefs extends CRMUtilities implements ApiBase {
    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubDPB = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    private ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<BrowserUtils> browserUtils= ThreadLocal.withInitial(BrowserUtils::new);

    public void correspondenceDetailsCaratCID(String cid) {
        System.out.println("checking cid value");
        if("previouslyCreated".equalsIgnoreCase(cid)||"fromRequest".equalsIgnoreCase(cid)){
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        browserUtils.get().hover(caseAndContactDetailsPage.getOutboundCorrCaratByCid(cid));
        BrowserUtils.waitFor(1);
        caseAndContactDetailsPage.getOutboundCorrCaratByCid(cid).click();
        BrowserUtils.waitFor(2);
    }

    public void requiredCorrespondenceDetailsFields(String field, String cid) throws ParseException {
        System.out.println("checking cid value");
        if("previouslyCreated".equalsIgnoreCase(cid)||"fromRequest".equalsIgnoreCase(cid)){
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        switch (field) {
            case "Created On":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsCreatedOnInfo.getAttribute("innerHTML").trim().matches("\\d{2}/\\d{2}/\\d{4}\\s+\\d{2}:\\d{2}\\s+(AM|PM)"));
                break;
            case "Created By":
                assertTrue(caseAndContactDetailsPage.outboundCorresCreatedByInfo.isDisplayed());
                break;
            case "Status Reason":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailStatusReasonInfo.isDisplayed());
                break;
            case "Recipient":
                waitForVisibility(caseAndContactDetailsPage.outboundCorresDetailsRecipientInfo,20);
                assertEquals(inboundCorrespondencePage.outboundCorresRecipientByCid(cid), caseAndContactDetailsPage.outboundCorresDetailsRecipientInfo.getText().trim());
                break;
            case "Channel":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsChannelDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsChannelInfo.isDisplayed());
                break;
            case "Language":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsLanguageDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsLanguageInfo.isDisplayed());
                break;
            case "Destination":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsDestinationDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsDestinationInfo.isDisplayed());
                assertTrue(verifyDestinationByChannel(caseAndContactDetailsPage.outboundCorresDetailsChannelInfo.getText().trim()));
                break;
            case "Status":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsStatusDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsStatusInfo.isDisplayed());
                break;
            case "Status Date":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsStatusDateDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsStatusDateInfo.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsStatusDateInfo.getText().trim().matches("(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02/(0[1-9]|[1-2][0-9]))|((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))/[0-9]{4}"));
                break;
            case "NID":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsNIDDisplayed.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsNIDInfo.isDisplayed());
                break;
            case "View Icon":
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsViewIcon.isDisplayed());
                assertTrue(caseAndContactDetailsPage.outboundCorresDetailsViewIcon.isEnabled());
                break;
        }
    }

    public boolean verifyDestinationByChannel(String channel) {
        boolean isValid = false;
        switch (channel) {
            case "Mail":
                System.out.println("mail");
                isValid = caseAndContactDetailsPage.outboundCorresDetailsDestinationInfo.isDisplayed();
                break;
            case "Email":
                isValid = validEmail(caseAndContactDetailsPage.outboundCorresDetailsDestinationInfo.getText().trim());
                break;
            case "Text":
            case "Fax":
            case "Mobile App":
                isValid = validPhone(caseAndContactDetailsPage.outboundCorresDetailsDestinationInfo.getText().trim());
                break;
        }
        return isValid;
    }

    public boolean validPhone(String number) {
        boolean isValid = false;
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public boolean validEmail(String email) {
        boolean isValid = false;
        String expression = "^[-a-z0-9~!$%^&*_=+}{'?]+(\\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void verifyLinksCaseRowInfo(String status, String id, String type, String date) {
        Map<String, String> caseConsumerIds = (Map<String, String>) generalSave.get().get("caseConsumerId");
        if(id.equalsIgnoreCase("previouslyCreated")){
            id = caseConsumerIds.get("caseId");
        }
        String caseStatus = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksCaseStatus);
        String caseID = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksCaseID);
        String caseType = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksCaseType);
        String caseDate = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksCaseDate);
        Assert.assertEquals(status, caseStatus);
        Assert.assertEquals(id, caseID);
        Assert.assertEquals(type, caseType);
        Assert.assertEquals(date, caseDate);
    }

    public void verifyLinksConsumerProfileRowInfo(String status, String id, String type, String date) {
        Map<String, String> caseConsumerIds = (Map<String, String>) generalSave.get().get("caseConsumerId");
        if(id.equalsIgnoreCase("previouslyCreated")){
            id = caseConsumerIds.get("consumerId");
        }
        String consumerProfileStatus = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksConsumerProfileStatus);
        String consumerProfileID = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksConsumerProfileID);
        String consumerProfileType = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksConsumerProfileType);
        String consumerProfileDate = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linksConsumerProfileDate);
        Assert.assertEquals(status, consumerProfileStatus);
        Assert.assertEquals(id, consumerProfileID);
        Assert.assertEquals(type, consumerProfileType);
        Assert.assertEquals(date, consumerProfileDate);
    }

    public void verifyOutboundLinkedToCaseUI(String cid, String caseId) {
        List<WebElement> ids = viewOutboundCorrespondenceDetailsPage.firstColumnLinks;
        List<WebElement> names = viewOutboundCorrespondenceDetailsPage.secondColumnLinks;
        if ("previouslyCreated".equalsIgnoreCase(cid)) {
            cid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(caseId)) {
            Map<String,String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
            caseId =caseConsumerId.get("caseId");
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().trim(), cid);
        boolean found = false;
        for (int index = 0; index < ids.size(); index++) {
            if ("Case".equalsIgnoreCase(names.get(index).getText().trim())) {
                found = true;
                Assert.assertEquals(ids.get(index).getText().trim(), caseId);
            }
        }
        Assert.assertTrue(found,"link not found");
    }

    public void verifyOutboundLinkedToInboundDocumentUI(String outCid, String inbCid) {
        List<WebElement> ids = viewOutboundCorrespondenceDetailsPage.firstColumnLinks;
        List<WebElement> names = viewOutboundCorrespondenceDetailsPage.secondColumnLinks;
        if ("previouslyCreated".equalsIgnoreCase(outCid) || "fromRequest".equalsIgnoreCase(outCid)) {
            outCid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(inbCid) || "fromRequest".equalsIgnoreCase(inbCid)) {
            inbCid = World.generalSave.get().get("InboundDocumentId").toString();
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().trim(), outCid);
        boolean found = false;
        for (int index = 0; index < ids.size(); index++) {
            if ("Inbound Correspondence".equalsIgnoreCase(names.get(index).getText().trim())) {
                found = true;
                Assert.assertEquals(ids.get(index).getText().trim(), inbCid);
            }
        }
        Assert.assertTrue(found, "link not found");
    }

    public void verifyOutboundLinkedToApplicationUI(String outCid, String applicationId) {
        List<WebElement> ids = viewOutboundCorrespondenceDetailsPage.firstColumnLinks;
        List<WebElement> names = viewOutboundCorrespondenceDetailsPage.secondColumnLinks;
        if ("previouslyCreated".equalsIgnoreCase(outCid) || "fromRequest".equalsIgnoreCase(outCid)) {
            outCid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(applicationId) || "fromRequest".equalsIgnoreCase(applicationId)) {
            applicationId = applicationIdAPI.get();
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().trim(), outCid);
        boolean found = false;
        for (int index = 0; index < ids.size(); index++) {
            if ("Application".equalsIgnoreCase(names.get(index).getText().trim())) {
                found = true;
                Assert.assertEquals(ids.get(index).getText().trim(), applicationId);
            }
        }
        Assert.assertTrue(found, "link not found");
    }

    public void verifyOutboundLinkedToContactRecordUI(String outCid, String contactRecordId) {
        List<WebElement> ids = viewOutboundCorrespondenceDetailsPage.firstColumnLinks;
        List<WebElement> names = viewOutboundCorrespondenceDetailsPage.secondColumnLinks;
        if ("previouslyCreated".equalsIgnoreCase(outCid) || "fromRequest".equalsIgnoreCase(outCid)) {
            outCid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(contactRecordId) || "fromRequest".equalsIgnoreCase(contactRecordId)) {
            contactRecordId = apicontactRecordId.get().toString();
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().trim(), outCid);
        boolean found = false;
        for (int index = 0; index < ids.size(); index++) {
            if ("Application".equalsIgnoreCase(names.get(index).getText().trim())) {
                found = true;
                Assert.assertEquals(ids.get(index).getText().trim(), contactRecordId);
            }
        }
        Assert.assertTrue(found, "link not found");
    }

    public void verifyOutboundLinkedToMissingInformation(String outCid, String missingInformationId) {
        List<WebElement> ids = viewOutboundCorrespondenceDetailsPage.firstColumnLinks;
        List<WebElement> names = viewOutboundCorrespondenceDetailsPage.secondColumnLinks;
        if ("previouslyCreated".equalsIgnoreCase(outCid) || "fromRequest".equalsIgnoreCase(outCid)) {
            outCid = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
        }
        if ("previouslyCreated".equalsIgnoreCase(missingInformationId) || "fromRequest".equalsIgnoreCase(missingInformationId)) {
            missingInformationId = String.valueOf(World.generalSave.get().get("MissingInformationItemId"));
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.CIDByEditButton.getText().trim(), outCid);
        boolean found = false;
        for (int index = 0; index < ids.size(); index++) {
            if ("Application".equalsIgnoreCase(names.get(index).getText().trim())) {
                found = true;
                Assert.assertEquals(ids.get(index).getText().trim(), missingInformationId);
            }
        }
        Assert.assertTrue(found, "link not found");
    }

    public boolean verifyOnCaseAndContactScreen(){
        return caseAndContactDetailsPage.outboundCorresHeader.isDisplayed();
    }

    public void verifyRegardingCaseIdUI(String caseId) {
        Assert.assertEquals(BrowserUtils.validNumberFilter(viewOutboundCorrespondenceDetailsPage.regardingCaseId.getText()).trim(),caseId,"case id not found");
    }

    public void verifyRegardingConsumerIdUI(String consumerId) {
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.regardingConsumerId.getText().contains( consumerId),"consumer id not found");

    }

    public void navigatefromOBtoLinkedId(String Id,String action) {
       boolean status=false;
        BrowserUtils.waitFor(5);
        if (Id.equalsIgnoreCase("previouslycreatedtaskId")) {
            Id = CRM_TaskManagementStepDef.srID.get();
        }
        else if (Id.equalsIgnoreCase("previouslyCreatedConsumerId")) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            Id = caseConsumerId.get("consumerId");
        }
        else if (Id.equalsIgnoreCase("previouslyCreatedCaseId")) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            Id = caseConsumerId.get("caseId");
        }
        else if (Id.equalsIgnoreCase("previouslyCreatedStandaloneConsumerId")) {
            Map<String, String> satndaloneConsumerId = (Map<String, String>) World.generalSave.get().get("ConsumerId");
            Id = satndaloneConsumerId.get("consumerId");
        }
        else if (Id.contains("multipleconsumers")) {
            List<String> ConsumerIds = (List<String>) generalSave.get().get("consumerIds");
            char pos = Id.charAt(18);
            switch(pos) {
                case '0':
                    Id = ConsumerIds.get(0);
                    break;
                case '1':
                    Id = ConsumerIds.get(1);
                    break;
                case '2':
                    Id = ConsumerIds.get(2);
                    break;
                default: Assert.assertFalse(false,"no valid id");
            }
        }

        uiAutoUitilities.get().ItemsPerPage(20);
        List<WebElement> listofids = viewOutboundCorrespondenceDetailsPage.oblinkids;
        for (WebElement e : listofids) {

            if (e.getText().equalsIgnoreCase(Id)) {
                status=true;

                if (action.equalsIgnoreCase("click")) {
                    e.click();
                    BrowserUtils.waitFor(5);
                }
                else if (action.equalsIgnoreCase("validate")) {
                    browserUtils.get().waitForVisibility(e,3);
                }
                System.out.println("clicked on Link ID in OB: " + Id);
                break;
            }
        }
        Assert.assertTrue(status,"unable to find OB Link "+Id);

    }

    public void navigatefromOBtoTask(String Id,String header) {

        if (Id.equalsIgnoreCase("previouslycreatedtaskId")) {
            Id = "TASK ID: " + CRM_TaskManagementStepDef.srID.get();
            header="TASK DETAILS";
        } else if (Id.equalsIgnoreCase("previouslyCreatedServicetaskId")) {
            Id = "SR ID: " + CRM_TaskManagementStepDef.srID.get();
            header="SERVICE REQUEST DETAILS";
        }
        Assert.assertEquals(viewOutboundCorrespondenceDetailsPage.taskid.getText(), Id);
        Assert.assertTrue(viewOutboundCorrespondenceDetailsPage.taskpageheader.getText().contains(header));
    }

    public void navigation_ConsumerProfile_Demographicpage(String conid) {
        if ("previouslyCreatedConsumerId".equalsIgnoreCase(conid)) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            conid = caseConsumerId.get("consumerId");
        }
        else if (conid.equalsIgnoreCase("previouslyCreatedStandaloneConsumerId")) {
            Map<String, String> standaloneConsumerId = (Map<String, String>) World.generalSave.get().get("ConsumerId");
            conid = standaloneConsumerId.get("consumerId");
        }
        else if (conid.contains("multipleconsumers")) {
            List<String> ConsumerIds = (List<String>) generalSave.get().get("consumerIds");
            char pos = conid.charAt(18);
            switch(pos) {
                case '0':
                    conid = ConsumerIds.get(0);
                    break;
                case '1':
                    conid = ConsumerIds.get(1);
                    break;
                case '2':
                    conid = ConsumerIds.get(2);
                    break;
                default: Assert.assertFalse(false,"no valid consumer id");
            }
        }
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//a[contains(text(),'"+conid+"')]")).isDisplayed());
    }

    public void verifyLinksServiceRequestRowInfo(String serviceRequestType) {
        JsonPath serviceRequestJsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getTaskByTaskId(generalSave.get().get("externalRefId").toString());

        String serviceRequestID = serviceRequestJsonPath.getString("tasks[0].taskVO.taskId");
        String serviceRequestStatusDate = serviceRequestJsonPath.getString("tasks[0].taskVO.statusDate");
        serviceRequestStatusDate = serviceRequestStatusDate.substring(0,10).replace("-","");
        String serviceRequestYear = serviceRequestStatusDate.substring(0,4);
        serviceRequestStatusDate = serviceRequestStatusDate.substring(4)  + serviceRequestYear;

        String serviceRequestStatus = serviceRequestJsonPath.getString("tasks[0].taskVO.taskStatus");
        String serviceRequestStatusUI = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestStatusColumn.get(0));
        String serviceRequestTypeUI = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestTypeColumn.get(0));
        String serviceRequestStatusDateUI = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestDateColumn.get(0));
        String serviceRequestIDUI = pubDPB.get().getTextIfElementIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.linkServiceRequestIdColumn.get(0));

        Assert.assertEquals(serviceRequestIDUI, serviceRequestID , "ServiceRequestID field mismatched in Links component for OB");
        Assert.assertEquals(serviceRequestTypeUI, serviceRequestType,"ServiceRequestType field mismatched in Links component for OB");

        Assert.assertEquals(serviceRequestStatusDateUI.replace("/",""), serviceRequestStatusDate,"ServiceRequestDate field mismatched in Links component for OB");
        Assert.assertEquals(serviceRequestStatusUI, serviceRequestStatus,"ServiceRequestStatus field mismatched in Links component for OB");
    }
}

