package com.maersk.dms.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.step_definitions.CRM_View_Provider_Details_StepDef.projectName;

public class OutboundCorrespondenceDefinitionStepDefs extends CRMUtilities implements ApiBase {
    private TenantManagerDMSLoginPage loginPage = new TenantManagerDMSLoginPage();
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private TenantManagerDMSProjectListPage tenantManagerDMSProjectListPage = new TenantManagerDMSProjectListPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private APIClassUtil classObject = APIClassUtil.getApiClassUtilThreadLocal();
    CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    OutboundCorrespondenceDefinitionChannelPage outboundCorrespondenceDefinitionChannelPage = new OutboundCorrespondenceDefinitionChannelPage();
    private ThreadLocal<String> eventId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<ZonedDateTime> nowUTC = ThreadLocal.withInitial(()->ZonedDateTime.now(ZoneOffset.UTC));
    private final ThreadLocal<LocalDateTime> localDateTime = ThreadLocal.withInitial(()->LocalDateTime.ofInstant(nowUTC.get().toInstant(), ZoneOffset.UTC));
    private final ThreadLocal<String> expectedDateAndHour = ThreadLocal.withInitial(()->localDateTime.get().toString().substring(0, 13));
    LoginPage loginPageCRM = new LoginPage();
    Actions action = new Actions(Driver.getDriver());
    //private OutboundCorrespondenceDefinitionChannelStepDefs outboundCorrespondenceDefinitionChannelStepDefs = new OutboundCorrespondenceDefinitionChannelStepDefs();
//    private ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);


    public void login(String userName, String password) {
        fillTheFiled(loginPage.userName, userName);
        fillTheFiled(loginPage.password, password);
        loginPage.tmLoginButton.click();
    }

    /**
     * Creates random values in Outbound Correspondence Definition in World Class
     */
    public void createRandomOutboundCorrespondenceDef() {
        World.createNewWorld();
        World.getWorld().outboundCorrespondenceDefinition.get().createRandomValues();
    }

    /**
     * Changes one field's value from random values created in Outbound Correspondence Definition instance in World Class
     */
    public void changeValueOutboundCorrespondence(String field, String value) {
        switch (field) {
            case "startDate":
                World.getWorld().outboundCorrespondenceDefinition.get().startDate = value;
                break;
            case "Id":
                World.getWorld().outboundCorrespondenceDefinition.get().ID = value;
                break;
            case "ID":
                World.getWorld().outboundCorrespondenceDefinition.get().ID = value;
                break;
            case "Name":
                World.getWorld().outboundCorrespondenceDefinition.get().Name = value;
                break;
            case "stateID":
                World.getWorld().outboundCorrespondenceDefinition.get().stateID = value;
                break;
            case "endDate":
                World.getWorld().outboundCorrespondenceDefinition.get().endDate = value;
                break;
            case "endReason":
                World.getWorld().outboundCorrespondenceDefinition.get().endReason = value;
                break;
            case "stateId":
                World.getWorld().outboundCorrespondenceDefinition.get().stateID = value;
                break;
            case "Description":
                World.getWorld().outboundCorrespondenceDefinition.get().description = value;
                break;
            case "bodyDataSource":
                World.getWorld().outboundCorrespondenceDefinition.get().bodyDataSoource = value;
                break;
        }
    }

    public void fillRandomOutboundCorrespondenceDefUI() {
        fillFieldsOutboundCorrespondence();
    }

    /**
     * Fills out Outbound Correspondence Definition through UI, skips fields that are sent @Param
     */
    public void fillRandomOutboundCorrespondenceDefUI(String... skipField) {
        createRandomOutboundCorrespondenceDef();
        fillFieldsOutboundCorrespondence(skipField);
        BrowserUtils.waitFor(2);
        waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 6);
        new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.saveButon).perform();
//        waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }

    public void navigateAwayDiscardChanges() {
        tenantManagerDMSProjectListPage.outBoundCorrespondenceLink.click();
        waitForVisibility(tenantManagerDMSProjectListPage.discardPopUp, 7);
        tenantManagerDMSProjectListPage.discardPopUp.click();
        waitForVisibility(tenantManagerDMSProjectListPage.outboundCorrespondenceListHeader, 7);
    }

    public void fillFieldsOutboundCorrespondence(String... removeFields) {
        staticWait(1000);
        List<String> remove = (List<String>) Arrays.asList(removeFields);
        List<String> fields = new ArrayList<>();
        fields.add("Id");
        fields.add("Name");
        fields.add("stateId");
        fields.add("Description");
        fields.add("startDate");
        fields.add("endDate");
        fields.add("endReason");
        fields.add("bodyDataSource");
        fields.add("inboundCorrespondenceType");
        if (removeFields.length > 0) {
            for (int index = 0; index < removeFields.length; index++) {
                for (int count = 0; count < fields.size(); count++) {
                    if (removeFields[index].equalsIgnoreCase(fields.get(count))) {
                        fields.remove(count);
                    }
                }
            }
        }
        for (String field : fields) {
            switch (field) {
                case "Id":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.Id, World.getWorld().outboundCorrespondenceDefinition.get().ID);
                    break;
                case "Name":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.name, World.getWorld().outboundCorrespondenceDefinition.get().Name);
                    break;
                case "Description":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.description, World.getWorld().outboundCorrespondenceDefinition.get().description);
                    break;
                case "startDate":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.startDate, World.getWorld().outboundCorrespondenceDefinition.get().startDate);
                    break;
                case "endDate":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.endDate, World.getWorld().outboundCorrespondenceDefinition.get().endDate);
                    break;
                case "endReason":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.endReason, World.getWorld().outboundCorrespondenceDefinition.get().endReason);
                    break;
                case "stateId":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.stateId, World.getWorld().outboundCorrespondenceDefinition.get().stateID);
                    break;
                case "bodyDataSource":
                    fillTheFiled(outboundCorrespondenceDefinitionPage.bodyDataSource, World.getWorld().outboundCorrespondenceDefinition.get().bodyDataSoource);
                    break;
                case "inboundCorrespondenceType":
//                    selectRandomDropDownOption(outboundCorrespondenceDefinitionPage.inboundCorrespondenceDropdown);
                    break;
            }
        }

    }

    public void fillFieldsOutboundCorrespondence(boolean addOnly, String... removeFields) {
        staticWait(1000);
        List<String> remove = (List<String>) Arrays.asList(removeFields);
        List<String> fields = new ArrayList<>();
        if (addOnly) {
            for (String r : remove) {
                fields.add(r);
            }
        } else {
            fields.add("Id");
            fields.add("Name");
            fields.add("stateId");
            fields.add("Description");
            fields.add("startDate");
            fields.add("endDate");
            fields.add("endReason");
            fields.add("bodyDataSource");
            fields.add("inboundCorrespondenceType");
            fields.add("approvalRequired");
            if (removeFields.length > 0) {
                for (String r : remove) {
                    Collections.sort(fields);
                    fields.remove(Collections.binarySearch(fields, r));
                }
            }
        }
        for (String field : fields) {
            switch (field) {
                case "Id":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.Id);
                    outboundCorrespondenceDefinitionPage.Id.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().ID);
                    break;
                case "Name":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.name);
                    outboundCorrespondenceDefinitionPage.name.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().Name);
                    break;
                case "Description":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.description);
                    outboundCorrespondenceDefinitionPage.description.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().description);
                    break;
                case "startDate":
//                    uiAutoUitilities.get()
//                   .clearStartDate();
                    outboundCorrespondenceDefinitionPage.startDate.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().startDate);
                    break;
                case "endDate":
//                    uiAutoUitilities.get()
//                   .clearEndDate();
                    outboundCorrespondenceDefinitionPage.endDate.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().endDate);
                    break;
                case "endReason":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.endReason);
                    outboundCorrespondenceDefinitionPage.endReason.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().endReason);
                    break;
                case "stateId":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.stateId);
                    outboundCorrespondenceDefinitionPage.stateId.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().stateID);
                    break;
                case "bodyDataSource":
//                    uiAutoUitilities.get()
//                   .clearWithActions(outboundCorrespondenceDefinitionPage.bodyDataSource);
                    outboundCorrespondenceDefinitionPage.bodyDataSource.sendKeys(World.getWorld().outboundCorrespondenceDefinition.get().bodyDataSoource);
                    break;
                case "inboundCorrespondenceType":
//                    selectRandomDropDownOption(outboundCorrespondenceDefinitionPage.inboundCorrespondenceDropdown);
                    break;
                case "approvalRequired":
                    outboundCorrespondenceDefinitionPage.approvalRequired.click();
                    break;
            }
        }

    }

    public void clearFields(String... removeFields) {
        staticWait(1000);
        List<String> remove = (List<String>) Arrays.asList(removeFields);
        List<String> fields = new ArrayList<>();
        fields.addAll(remove);
        for (String field : fields) {
            switch (field) {
                case "Id":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.Id);
                    break;
                case "Name":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.name);
                    break;
                case "Description":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.description);
                    break;
                case "startDate":
                    uiAutoUitilities.get().clearStartDate();
                    break;
                case "endDate":
                    uiAutoUitilities.get().clearEndDate();
                    break;
                case "endReason":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.endReason);
                    break;
                case "stateId":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.stateId);
                    break;
                case "bodyDataSource":
                    uiAutoUitilities.get().clearWithActions(outboundCorrespondenceDefinitionPage.bodyDataSource);
                    break;
            }
        }
    }


    public void assertErrorMessages(String message) {
        switch (message) {
            case "Id":
                waitForVisibility(outboundCorrespondenceDefinitionPage.idFailMessage, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.idFailMessage.isDisplayed());
                break;
            case "Name":
                waitForVisibility(outboundCorrespondenceDefinitionPage.nameFailMessage, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.nameFailMessage.isDisplayed());
                break;
            case "Description":
                waitForVisibility(outboundCorrespondenceDefinitionPage.descriptionFailMessage, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.descriptionFailMessage.isDisplayed());
                break;
            case "startDate":
                waitForVisibility(outboundCorrespondenceDefinitionPage.startDateFailMessage, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.startDateFailMessage.isDisplayed());
                break;
            case "endDateCannotBePrior":
                waitForVisibility(outboundCorrespondenceDefinitionPage.endDateCannotBePrior, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.endDateCannotBePrior.isDisplayed());
                break;
            case "endReason":
                waitForVisibility(outboundCorrespondenceDefinitionPage.endReason, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.endReason.isDisplayed());
                break;
            case "endReasonRequired":
                waitForVisibility(outboundCorrespondenceDefinitionPage.endReasonRequired, 7);
                Assert.assertTrue(outboundCorrespondenceDefinitionPage.endReasonRequired.isDisplayed());
                break;
        }
    }

    public void assertFieldValue(String field, String value) {
        switch (field) {
            case "Id":
                Assert.assertEquals(value, outboundCorrespondenceDefinitionPage.Id.getAttribute("value"));
                break;
            case "Name":
                Assert.assertEquals(value, outboundCorrespondenceDefinitionPage.name.getAttribute("value"));
                break;
            case "Description":
                Assert.assertEquals(value, outboundCorrespondenceDefinitionPage.description.getAttribute("value"));
                break;
            case "startDate":
                Assert.assertEquals(value, outboundCorrespondenceDefinitionPage.startDate.getAttribute("value"));
                break;
            case "stateId":
                Assert.assertEquals(value, outboundCorrespondenceDefinitionPage.stateId.getAttribute("value"));
                break;
        }
    }

    public void assertCreatedBy() {
        waitForVisibility(outboundCorrespondenceDefinitionPage.creationByHeader, 7);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Assert.assertTrue(NumberUtils.isNumber(BrowserUtils.validNumberFilter(outboundCorrespondenceDefinitionPage.creationByUser.getText())));
        Assert.assertTrue(BrowserUtils.validNumberFilter(outboundCorrespondenceDefinitionPage.creationByUser.getText()).length() > 1);
    }

    public void assertDescriptionStringCount(int characters) {
        Assert.assertEquals(500, outboundCorrespondenceDefinitionPage.description.getAttribute("value").length());
    }

    public void assertDescriptionDoesNotEndWith(String s) {
        Assert.assertFalse(outboundCorrespondenceDefinitionPage.description.getAttribute("value").endsWith(s));
    }

    public void assertEndReasonDoesNotEndWith(String s) {
        Assert.assertFalse(outboundCorrespondenceDefinitionPage.description.getAttribute("value").endsWith(s));
    }

    public void assertStartDateEndDateErrorMessages(String startDateMessage, String endDateMessage) {
        Assert.assertEquals(startDateMessage, outboundCorrespondenceDefinitionPage.startDateMessage.getText());
        Assert.assertEquals(endDateMessage, outboundCorrespondenceDefinitionPage.endDateMessage.getText());
    }

    public void changeStartEndDate(String startDate, String endDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        switch (startDate) {
            case "Current_SysDate":
                changeValueOutboundCorrespondence("startDate", dtf.format(LocalDateTime.now()));
                break;
            case "Current_SysDate+1Month":
                changeValueOutboundCorrespondence("endDate", dtf.format(LocalDateTime.now().plusMonths(1)));
                break;
        }
        switch (endDate) {
            case "Current_SysDate-1Month":
                changeValueOutboundCorrespondence("endDate", dtf.format(LocalDateTime.now().minusMonths(1)));
                break;
            case "Current_SysDate+1Month":
                changeValueOutboundCorrespondence("endDate", dtf.format(LocalDateTime.now().plusMonths(1)));
                break;
            case "Current_SysDate+2Month":
                changeValueOutboundCorrespondence("endDate", dtf.format(LocalDateTime.now().plusMonths(2)));
                break;
        }
    }

    public void selectPreviouslyCreatedOutCorrDef() {
        String id = World.getWorld().outboundCorrespondenceDefinition.get().ID;
//        uiAutoUitilities.get()
//       .findInList(id);
    }

    /**
     * Creates Outbound Correspondence Definition through UI.
     * int definitions for how many definitions to create
     * Map key for which field to customize, value for value
     */
    public void createOutCorrDefUI(int definitions, Map<String, String> customFields) {
        for (int count = 0; count < definitions; count++) {
            createRandomOutboundCorrespondenceDef();
            for (String s : customFields.keySet()) {
                changeValueOutboundCorrespondence(s, customFields.get(s));
            }
            outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
//            waitForVisibility(outboundCorrespondenceDefinitionListPage.header,7);
            BrowserUtils.waitFor(3);
            fillRandomOutboundCorrespondenceDefUI();
            saveAndVerifyPopUp();
        }
    }

    /**
     * Creates Outbound Correspondence Definition through UI.
     * int definitions for how many definitions to create
     */
    public void createOutCorrDefUI(int definitions) {
        for (int count = 0; count < definitions; count++) {
            createRandomOutboundCorrespondenceDef();
            outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
//            waitForVisibility(outboundCorrespondenceDefinitionListPage.header,7);
            BrowserUtils.waitFor(3);
            fillRandomOutboundCorrespondenceDefUI();
            saveAndVerifyPopUp();
        }
    }

    public void assertStatus(String status) {
        List<WebElement> idColumn = outboundCorrespondenceDefinitionListPage.idColumn();
        List<WebElement> statusColumn = outboundCorrespondenceDefinitionListPage.statusColumn();
        for (int count = 0; count < idColumn.size(); count++) {
            if (idColumn.get(count).getText().equalsIgnoreCase(World.getWorld().outboundCorrespondenceDefinition.get().ID)) {
                Assert.assertEquals(statusColumn.get(count).getText().toUpperCase(), status.toUpperCase());
                break;
            }
        }
    }

    public void sortOutboundCorrespondenceList(String column) {
        switch (column.toLowerCase()) {
            case "id":
                outboundCorrespondenceDefinitionListPage.sortById.click();
                BrowserUtils.waitFor(3);
                outboundCorrespondenceDefinitionListPage.sortById.click();
                break;
            case "name":
                outboundCorrespondenceDefinitionListPage.sortByName.click();
                BrowserUtils.waitFor(3);
                outboundCorrespondenceDefinitionListPage.sortByName.click();
                break;
            case "status":
                outboundCorrespondenceDefinitionListPage.sortByStatus.click();
                BrowserUtils.waitFor(3);
                outboundCorrespondenceDefinitionListPage.sortByStatus.click();
                break;
        }
        BrowserUtils.waitFor(3);
    }

    public void assertSortedByColumn(String column) {
        List<WebElement> before;
        switch (column.toLowerCase()) {
            case "id":
                before = outboundCorrespondenceDefinitionListPage.idColumn();
                break;
            case "name":
                before = outboundCorrespondenceDefinitionListPage.nameColumn();
                break;
            case "status":
                before = outboundCorrespondenceDefinitionListPage.statusColumn();
                break;
            default:
                before = outboundCorrespondenceDefinitionListPage.idColumn();
        }
        List<WebElement> after = before;
        Collections.sort(after, uiAutoUitilities.get().WEBELEMENT_TEXT);
        for (int count = 0; count < after.size(); count++) {
            Assert.assertEquals(before.get(count).getText(), after.get(count).getText());
        }
    }

    public void assertEndReasonStringCount(int characters) {
        Assert.assertEquals(outboundCorrespondenceDefinitionPage.endReason.getAttribute("value").length(), 100);
    }

    public void clickFirstRecord() {
        outboundCorrespondenceDefinitionListPage.idColumn().get(0).click();
        waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
    }

    public void saveAndVerifyPopUp() {
        scrollToElement(outboundCorrespondenceDefinitionPage.saveButon);
        waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 7);
        waitFor(2);
        outboundCorrespondenceDefinitionPage.saveButon.click();
        BrowserUtils.waitFor(1);
//        outboundCorrespondenceDefinitionPage.savePopUp= Driver.getDriver().findElement(By.xpath("//*[contains(text(),'Correspondence Definition successfully updated')]"));
//        Assert.assertTrue("Correspondence Definition successfully updated".contains(outboundCorrespondenceDefinitionPage.savePopUp.getText()));
    }

    public void assertUpdatedByOn() {
        waitForVisibility(outboundCorrespondenceDefinitionPage.creationByHeader, 2);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.updatedByUser.getText().length() > 0);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.savePopUp.isDisplayed());
    }

    public void assertNameLength(int length) {
        int index = uiAutoUitilities.get().findInList(true, World.getWorld().outboundCorrespondenceDefinition.get().ID);
        Assert.assertEquals(outboundCorrespondenceDefinitionListPage.nameColumn().get(index).getText().length(), length);//Finds entry in name column and asserts equal to length variable
    }

    public void verifyCorrDefCreatedInResponse(Response response) {
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(World.getWorld().jsonPath.get().get("correspondence.correspondenceId").toString().isEmpty());
    }

    public void clickCorrespondenceDetailsBTN() {
        caseAndContactDetailsPage.clickCorrespondenceDetailsBTN();
    }

    public void verifyDetailsOfTheCorrespondence() {
        Assert.assertTrue(caseAndContactDetailsPage.verifyDetailsOfTheCorrespondence());
    }

    public void verifyCorrespondenceIDDisplayed() {
        Assert.assertTrue(caseAndContactDetailsPage.correspondenceID());
    }

    public void verifyCorrespondenceTypeDisplayed() {
        Assert.assertTrue(caseAndContactDetailsPage.correspondenceType());
    }

    public void populateAndVerifyInternalCaseID(String caseId) {
        Assert.assertEquals(caseId, caseAndContactDetailsPage.consumerInternalCaseId(caseId));
    }

    public void hoverElipsisRecipientColumn() {
        caseAndContactDetailsPage.hoverOCRecipientElipsis();
    }

    public void verifyOCRecipientToRecipientDetails() {
        Assert.assertTrue(caseAndContactDetailsPage.verifyOCRecipientToRecipientDetails());
    }

    public void clickOutboundCorresRecipientCarat() {
        caseAndContactDetailsPage.clickOutboundCorresRecipientCarat();
    }

    public void clickOutboundCorresChannelCarat() {
        caseAndContactDetailsPage.clickOutboundCorresChannelCarat();
    }

    public void verifyOCLanguageFirstIsTheSame() {
        Assert.assertTrue(caseAndContactDetailsPage.verifyOCLanguageFirstIsTheSame());
    }

    public void hoverElipsisChannelColumn() {
        caseAndContactDetailsPage.hoverOCChannelElipsis();
    }

    public void verifyElipsisChannelColumn(String channels) {
        Assert.assertTrue(caseAndContactDetailsPage.verifyElipsisChannelColumn(channels));
    }


    public void verifyOutboundCorresStatusIsDisplayed() {
        Assert.assertTrue(caseAndContactDetailsPage.detailsOfTheCorrespondenceStatusDisplayed());
    }

    public void verifyOutboundCorresDateFormat() {
        String date = caseAndContactDetailsPage.detailsOfTheCorrespondenceStatusDateTXT();
        Assert.assertTrue(date.matches("[0-1][0-9]/[0-3][0-9]/[0-9]{4}"));
    }

    public void verifyViewIconCorrespondenceIsDisplayed() {
        Assert.assertTrue(caseAndContactDetailsPage.viewIconCorrespondenceIsDisplayed());
    }

    public void verifyOutboundCorresSortOrder() throws ParseException {
        Assert.assertTrue(caseAndContactDetailsPage.VerifyDateSort());
    }

    public void verifyActiveContactPreferredLanguageIsDisplayed() {
        Assert.assertTrue(caseAndContactDetailsPage.activeContactPreferredLanguageIsDisplayed());
    }

    public void PopulatedWithRecordsRelatedToTheCaseID() {
        Assert.assertTrue(caseAndContactDetailsPage.CIDoutBound.isDisplayed());
        Assert.assertTrue(caseAndContactDetailsPage.OutboundCorresRecipient.isDisplayed());
    }


    public void verifyInboundCorrespondenceIDisCreated() {
        int actual = World.getWorld().jsonPath.get().get("correspondence.inboundCorrespondenceDefinitionId");
        Assert.assertEquals(actual, 38, "Expected maersk + Consumer id: 38, Actual Id:  " + actual);
    }

    public void verifyNotificationUpdateEvent(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("NOTIFICATION_UPDATE_EVENT", eventbyTraceId));
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        // String currentDate =  ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("YYYY-MM-dd");

        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "NOTIFICATION_UPDATE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusId\":9"));
        //Assert.assertTrue(eventbyTraceId.get("events["+ World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) +"].payload").toString().contains("\"statusMessage\":\"none/disabled\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusDatetime\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));

    }

    public void verifyNotificationUpdateEventPatch(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("NOTIFICATION_UPDATE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        // String currentDate =  ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("YYYY-MM-dd");

        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "NOTIFICATION_UPDATE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        //Assert.assertTrue(eventbyTraceId.get("events["+ World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) +"].payload").toString().contains("\"statusMessage\":\"none/disabled\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusDatetime\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service") + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"ecmsDocumentId\":" + String.valueOf(World.generalSave.get().get("ecmsDocumentId"))));
    }

    public void verifyNotificationUpdateEventViewedPatch(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("NOTIFICATION_UPDATE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        // String currentDate =  ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("YYYY-MM-dd");

        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "NOTIFICATION_UPDATE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        //Assert.assertTrue(eventbyTraceId.get("events["+ World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) +"].payload").toString().contains("\"statusMessage\":\"none/disabled\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusDatetime\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service") + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("NOTIFICATION_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"digitallyRead\":\"" + String.valueOf(World.generalSave.get().get("digitallyRead" + Hooks.nameAndTags.get()))));
    }

    public void verifyCorrespondenceUpdateEvent(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT", eventbyTraceId));
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        // String currentDate =  ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("YYYY-MM-dd");

        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusId\":19"));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusMessage\":\"Volume control\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"statusDatetime\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedBy\":\"" + API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().userId + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));

    }


    public void verifyEventSentDPBI(String event) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        //apiClassUtil.setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiMarsEvent").split("/app")[0]);
        apiClassUtil.setEndPoint("/app/crm/es/subscriber/records/12700/" + eventId.get());
        JsonPath response = apiClassUtil.getAPI().jsonPathEvaluator;
        Assert.assertEquals(apiClassUtil.statusCode, 200);
        Assert.assertEquals(response.getList("eventSubscriberRecords").size(), 1);
        Assert.assertEquals(response.get("eventSubscriberRecords[0].eventName"), event);
    }

    public void verifyEventSentDPBIPATCH() {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal();
        apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiMarsEvent").split("/app")[0]);
        apiClassUtil.setEndPoint("/app/crm/es/subscriber/records/12700/" + String.valueOf(World.generalSave.get().get("eventId")));
        JsonPath response = apiClassUtil.getAPI().jsonPathEvaluator;
        Assert.assertEquals(apiClassUtil.statusCode, 200);
        Assert.assertEquals(response.getList("eventSubscriberRecords").size(), 1);
        Assert.assertEquals(response.get("eventSubscriberRecords[0].eventName"), "NOTIFICATION_UPDATE_EVENT");
    }

    public void verifySavePopup() {
        waitFor(1);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.savePopUp.isDisplayed());
    }

    public void selectCRMProjectAfterAuthenticated() {
        waitForVisibility(loginPageCRM.projectId, 5);
        selectDropDown(loginPageCRM.projectId, projectName.get());
        waitFor(4);
        click(loginPageCRM.continueBtn);
        //added wait here to give sometime for UI to parse the response before making config api request.
        waitFor(8);
        waitForPageToLoad(10);
        click(loginPageCRM.acceptAndContinueBtn);
        //waitForVisibility(loginPage.permissionDropdown,10);
        waitForVisibility(loginPageCRM.userIcon, 10);
    }

    public void verifyLinksCreatedOBCR(JsonPath eventbyTraceId) {
        String actual = eventbyTraceId.prettify();
        Assert.assertTrue(actual.contains("\"internalRefType\\\":\\\"OUTBOUND_CORRESPONDENCE\\\",\\\"externalRefType\\\":\\\"CONTACT_RECORD\\\""));
        Assert.assertTrue(actual.contains("\"internalRefType\\\":\\\"CONTACT_RECORD\\\",\\\"externalRefType\\\":\\\"OUTBOUND_CORRESPONDENCE\\\""));

    }

    public void inboundcorrtypenonmandatory() {
        waitFor(2);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 6);
        new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.saveButon).perform();
        boolean status = quickIsDisplayed(outboundCorrespondenceDefinitionListPage.inboundcorrtypenonmandatory);
        Assert.assertTrue(!status);
    }

    public void verifyCorrespondenceUpdateEventAPI(JsonPath eventbyTraceId) {
        List<String> eventNames = eventbyTraceId.get("events.eventName");
        int obcUpdateEventFound = 0;
        boolean notificationUpdateEventFound = false;
        for (int i = 0; i < eventNames.size(); i++) {
            if (eventNames.get(i).equalsIgnoreCase("OUTBOUND_CORRESPONDENCE_UPDATE_EVENT")) {
                Assert.assertEquals(eventbyTraceId.get("events.status[" + i + "]").toString(), "SUCCESS");
                ++obcUpdateEventFound;
                eventId.set(eventbyTraceId.get("events.eventId[" + i + "]").toString());
            } else if (eventNames.get(i).equalsIgnoreCase("NOTIFICATION_UPDATE_EVENT")) {
                Assert.assertEquals(eventbyTraceId.get("events.status[" + i + "]").toString(), "SUCCESS");
                notificationUpdateEventFound = true;
            }
        }
        Assert.assertEquals(obcUpdateEventFound, 1, "0 or more than one OUTBOUND_CORRESPONDENCE_UPDATE_EVENT found");
        Assert.assertTrue(notificationUpdateEventFound, "NOTIFICATION_UPDATE_EVENT is not Created.");
    }

    public void validateEventNotCreated(JsonPath response) {
        Map m = response.getMap("eventsList");
        System.out.println(m);
        Assert.assertEquals(m.get("number"), 0);
        Assert.assertEquals(m.get("content.size"), null);
    }

    public void updateApprovalRequired(String isChecked) {
        String actualIsChecked = outboundCorrespondenceDefinitionPage.approvalRequired.getAttribute("value");
        if (isChecked.equalsIgnoreCase("true") && actualIsChecked.equalsIgnoreCase("false")) {
            outboundCorrespondenceDefinitionPage.approvalRequired.click();
        } else if (isChecked.equalsIgnoreCase("false") && actualIsChecked.equalsIgnoreCase("true")) {
            outboundCorrespondenceDefinitionPage.approvalRequired.click();
        }
        outboundCorrespondenceDefinitionPage.saveButon.click();
    }

    public void inboundcorrtypevalue() {
        waitFor(2);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        waitForClickablility(outboundCorrespondenceDefinitionPage.saveButon, 6);
        new Actions(Driver.getDriver()).click(outboundCorrespondenceDefinitionPage.saveButon).perform();
        boolean status = quickIsDisplayed(outboundCorrespondenceDefinitionListPage.inboundcorrtypenonmandatory);
        Assert.assertTrue(!status);
    }

    public void validateibfieldvaluesinOBDefinitions(List<String> dataTable) {
        waitFor(3);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionListPage.typedropdown.click();
        waitFor(3);
        List<WebElement> ibtypes = outboundCorrespondenceDefinitionListPage.typedropdownvalues;
        for (String expectedtype : dataTable) {
            boolean status = false;
            for (WebElement actualtype : ibtypes) {
                if (actualtype.getText().equalsIgnoreCase(expectedtype)) {
                    System.out.println(expectedtype + " : avaialble in type dropdown");
                    status = true;
                    break;
                }
            }
            Assert.assertTrue(status, expectedtype + "values invisible in Ib type dropdown in  Outbound Correspondence Definition page");

        }
    }

    public void validateAddTemplateValues(String actiontype, List<String> dataTable) {
        waitFor(2);
        jsClick(outboundCorrespondenceDefinitionListPage.templateButton);
        waitFor(2);
        List<WebElement> templatevalues = outboundCorrespondenceDefinitionListPage.addtemplatedownvalues;
        for (String expectedtype : dataTable) {
            boolean status = false;
            for (WebElement actualtype : templatevalues) {
                if (actualtype.getText().equalsIgnoreCase(expectedtype)) {
                    if (actiontype.equalsIgnoreCase("click"))
                        jsClick(actualtype);
                    status = true;
                    break;
                }
            }
            Assert.assertTrue(status, expectedtype + " :values invisible in add template type dropdown in  Outbound Correspondence Channel Definition page");

        }
    }

    public void iSelectFileForUpload(String filePath) throws Exception {
        String randomFileName = "1234";
        randomFileName += filePath;
        File copied;
        File sourceFile;
        String osName = System.getProperty("os.name");

        System.out.println("OS name: " + osName);
        if (osName.startsWith("Win")) {
            copied = new File("src/test/resources/tesData/api/dms/" + randomFileName);
            sourceFile = new File("src/test/resources/testData/api/dms/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " : Copied file exist in Local run");
            String choosePath = copied.getCanonicalPath();
            waitFor(1);
            outboundCorrespondenceDefinitionListPage.templateFileSelect.sendKeys(choosePath);

        } else {
            String jobName = System.getenv("JOB_BASE_NAME");
            System.out.println("JOB NAME: " + jobName);
            copied = new File("/home/jenkins/agent/workspace/QA/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName);
            sourceFile = new File("/home/jenkins/agent/workspace/QA/" + jobName + "/src/test/resources/testData/api/dms/" + filePath);
            FileUtils.copyFile(sourceFile, copied);
            System.out.println(copied.exists() + " Copied file exist in Jenkins run");
            RemoteWebElement uploadElement = (RemoteWebElement) Driver.getDriver().findElement(By.id("file-attach"));
            LocalFileDetector detector = new LocalFileDetector();
            File localFile = detector.getLocalFile("/home/jenkins/agent/workspace/QA/" + jobName + "/maersk-crm-qa-automation-framework/" + randomFileName);
            uploadElement.setFileDetector(detector);
            // enter the absolute file path into the file input field
            uploadElement.sendKeys(localFile.getAbsolutePath());

        }
        waitFor(5);
        copied.deleteOnExit();
        System.out.println(randomFileName);
    }

    public void validateTemplatesgridfieldvalues(Map<String, String> dataTable) {
        waitFor(3);
        for (String searchCriteria : dataTable.keySet()) {
            switch (searchCriteria) {
                case "Template File Name":
                    String expected_Template_file_name = dataTable.get("Template File Name");
                    String actual_Template_file_name = outboundCorrespondenceDefinitionListPage.templateFileName.getText();
                    Assert.assertEquals(actual_Template_file_name, expected_Template_file_name);
                    break;
                case "System":
                    String expected_System = dataTable.get("System");
                    String actual_System = outboundCorrespondenceDefinitionListPage.templateSystem.getText();
                    Assert.assertEquals(actual_System, expected_System);
                    break;
                case "External ID":
                    String expected_ExternalID = dataTable.get("External ID");
                    String actual_ExternalID = outboundCorrespondenceDefinitionListPage.templateExternalId.getText();
                    if (expected_ExternalID.equalsIgnoreCase("random"))
                        Assert.assertTrue(expected_ExternalID != null);
                    else
                        Assert.assertEquals(actual_ExternalID, expected_ExternalID);
                    break;
                case "Version":
                    String expected_Version = dataTable.get("Version");
                    String actual_Version = outboundCorrespondenceDefinitionListPage.templateVersion.getText();
                    Assert.assertEquals(actual_Version, expected_Version);
                    break;
                case "Language":
                    String expected_Language = dataTable.get("Language");
                    String actual_Language = outboundCorrespondenceDefinitionListPage.templateLanguage.getText();
                    Assert.assertEquals(actual_Language, expected_Language);
                    break;
                case "LanguageDropdown":
                    List<String> expected_Languagedropdown = World.generalList.get();
                    List<String> actual_Languagedropdown = new ArrayList<String>();
                    outboundCorrespondenceDefinitionListPage.templateLanguage.click();
                    waitFor(2);
                    List<WebElement> Languages = outboundCorrespondenceDefinitionListPage.typedropdownvalues;
                    for (WebElement lang : Languages) {
                        actual_Languagedropdown.add(lang.getText());
                    }
                    Collections.sort(expected_Languagedropdown);
                    Collections.sort(actual_Languagedropdown);
                    Assert.assertEquals(actual_Languagedropdown, expected_Languagedropdown);
                    break;
                case "Download":
                    String expected_downlaodstatus = dataTable.get("Download");
                    if (expected_downlaodstatus.equalsIgnoreCase("enabled"))
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.templatedownload.isEnabled());
                    else
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.templatedownload.isEnabled());
                    break;
                case "Delete":
                    String expected_deletestatus = dataTable.get("Delete");
                    if (expected_deletestatus.equalsIgnoreCase("enabled"))
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.templatedelete.isEnabled());
                    else
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.templatedelete.isEnabled());
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case - " + searchCriteria);
            }

        }

    }

    public void selectTemplatesgridfieldvalues(Map<String, String> dataTable) {

        OutboundCorrespondenceDefinitionChannelStepDefs outboundCorrespondenceDefinitionChannelStepDefs = new OutboundCorrespondenceDefinitionChannelStepDefs();
        for (String searchCriteria : dataTable.keySet()) {
            switch (searchCriteria) {
                case "Language":
                    outboundCorrespondenceDefinitionListPage.templateLanguage.click();
                    waitFor(2);
                    List<WebElement> Languages = outboundCorrespondenceDefinitionListPage.typedropdownvalues;
                    for (WebElement lang : Languages) {
                        if (lang.getText().equalsIgnoreCase(dataTable.get("Language"))) {
                            lang.click();
                            break;
                        }
                    }
                    break;
                case "StartDate":
                    outboundCorrespondenceDefinitionChannelStepDefs.changeDate("startDate", dataTable.get("StartDate"));
                    waitFor(1);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.templatestartDate, World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
                    break;
                case "EndDate":
                    outboundCorrespondenceDefinitionChannelStepDefs.changeDate("endDate", dataTable.get("EndDate"));
                    waitFor(1);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.templateendDate, World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case - " + searchCriteria);
            }
        }

        outboundCorrespondenceDefinitionChannelStepDefs.saveAndVerifyPopUp();
        waitFor(5);

    }

    public void validateTemplatesgridfieldvaluesForExternalTempalteId(String actiontype, Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            switch (searchCriteria) {
                case "System":
                    List<String> expected_Systemdropdown = Arrays.asList(dataTable.get("System").split(","));
                    List<String> actual_Systemdropdown = new ArrayList<String>();
                    outboundCorrespondenceDefinitionListPage.externalidtemplateSystem.click();
                    waitFor(2);
                    List<WebElement> Languages = outboundCorrespondenceDefinitionListPage.typedropdownvalues;
                    for (WebElement lang : Languages) {
                        actual_Systemdropdown.add(lang.getText());
                        if (actiontype.equalsIgnoreCase("select") && lang.getText().equalsIgnoreCase(expected_Systemdropdown.get(0)))
                            lang.click();
                    }
                    Collections.sort(expected_Systemdropdown);
                    Collections.sort(actual_Systemdropdown);
                    if (actiontype.equalsIgnoreCase("validate"))
                        Assert.assertEquals(actual_Systemdropdown, expected_Systemdropdown);
                    break;
                case "External ID":
                    String expected_ExternalID;
                    if (dataTable.get("External ID").equalsIgnoreCase("random"))
                        expected_ExternalID = "externalid_@#%$^#%^%".concat(ApiTestDataUtil.getApiTestDataUtilThreadLocal().getRandomString(30).randomString);
                    else
                        expected_ExternalID = dataTable.get("External ID");
                    outboundCorrespondenceDefinitionListPage.externalid.sendKeys(expected_ExternalID);
                    waitFor(2);
                    String actual_ExternalID = outboundCorrespondenceDefinitionListPage.externalid.getAttribute("value");
                    Assert.assertEquals(actual_ExternalID, expected_ExternalID);
                    break;
                case "Add":
                    if (actiontype.equalsIgnoreCase("select"))
                        outboundCorrespondenceDefinitionListPage.externalidadd.click();
                    else if (actiontype.equalsIgnoreCase("validate"))
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.externalidadd.isEnabled());
                    break;
                case "Cancel":
                    if (actiontype.equalsIgnoreCase("select"))
                        outboundCorrespondenceDefinitionListPage.externalidcancel.click();
                    else if (actiontype.equalsIgnoreCase("validate"))
                        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.externalidcancel.isEnabled());
                    break;
                default:
                    Assert.fail("Name of field from feature file does not match up to any case - " + searchCriteria);
            }

        }

    }

    public void validateRequiredfieldsForExternalTempalteId() {
        outboundCorrespondenceDefinitionListPage.externalidadd.click();
        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.externalidSystemReqmsg.isDisplayed());
        Assert.assertTrue(outboundCorrespondenceDefinitionListPage.externaltemplateidReqmsg.isDisplayed());
    }

    public void verfifyCorrespondenceUpdatedEvent(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        // String currentDate =  ApiTestDataUtil.getApiTestDataUtilThreadLocal().getCurrentDateAndTime("YYYY-MM-dd");

        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_UPDATE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"correspondenceRecipientId\":" + String.valueOf(World.generalSave.get().get("correspondenceRecipientId" + Hooks.nameAndTags.get()))));
    }

    private String getEventIdFromJsonResponse(String eventName, JsonPath eventbyTraceId) {
        List<Map<String, Object>> events = eventbyTraceId.getList("events");
        String eventId = "invalid";
        int index = 0;
        for (index = 0; index < events.size(); index++) {
            if (eventName.equalsIgnoreCase(String.valueOf(events.get(index).get("eventName")))) {
                eventId = String.valueOf(events.get(index).get("eventId"));
                System.out.println("event id - " + eventId + "found for \n Event Name - " + eventName);
                World.generalSave.get().put(eventName + "--INDEX--" + Hooks.nameAndTags.get(), index);
                return eventId;
            }
        }
        World.generalSave.get().put(eventName + "--INDEX--" + Hooks.nameAndTags.get(), index);
        return eventId;
    }

    public void verfifyCorrespondenceSaveEvent(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("CORRESPONDENCE_RECIPIENT_SAVE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "CORRESPONDENCE_RECIPIENT_SAVE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Create\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedBy\":\"" + "5555" + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"createdBy\":\"" + "4444" + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"updatedOn\":\"" + expectedDateAndHour.get() + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"correspondenceRecipientId\":" + String.valueOf(World.generalSave.get().get("NewlyCreatedCorrespondenceRecipientId" + Hooks.nameAndTags.get()))));
    }

    public void verfifyCorrespondenceSaveEventExternalIds(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("CORRESPONDENCE_RECIPIENT_SAVE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "CORRESPONDENCE_RECIPIENT_SAVE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Create\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalConsumerIdMedicaid\":\"" + String.valueOf(World.generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalCaseIdCHIP\":\"" + String.valueOf(World.generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalConsumerIdCHIP\":\""+ String.valueOf(World.generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CORRESPONDENCE_RECIPIENT_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalCaseIdMedicaid\":\"" + String.valueOf(World.generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get()))));
    }

    public void verfifyLetterDataExternalIds(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("LETTER_DATA_SAVE_EVENT", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "LETTER_DATA_SAVE_EVENT");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Create\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalConsumerIdMedicaid\\\":\\\"" + String.valueOf(World.generalSave.get().get("externalConsumerIdMedicaid" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalCaseIdCHIP\\\":\\\"" + String.valueOf(World.generalSave.get().get("externalCaseIdCHIP" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalConsumerIdCHIP\\\":\\\""+ String.valueOf(World.generalSave.get().get("externalConsumerIdCHIP" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("LETTER_DATA_SAVE_EVENT" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalCaseIdMedicaid\\\":\\\"" + String.valueOf(World.generalSave.get().get("externalCaseIdMedicaid" + Hooks.nameAndTags.get()))));
    }

    public void verfifyConsumerNotificationwCONSUMERIDReturnedEvent(JsonPath eventbyTraceId) {
        eventId.set( getEventIdFromJsonResponse("CONSUMER_NOTIFICATION_RETURNED", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "CONSUMER_NOTIFICATION_RETURNED");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"notificationId\":" + String.valueOf(World.generalSave.get().get("ReturnedNotificationNotificationId" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"consumerId\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerId" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"recipientId\":" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdRecipientRecord" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"channel\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdChannel" + Hooks.nameAndTags.get()))));
        //verify destination
        switch (String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdChannel" + Hooks.nameAndTags.get()))) {
            case "Mail":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"addressLine1\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdaddressLine1" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"addressLine2\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdaddressLine2" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"city\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdcity" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"state\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdState" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"zip\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdzip" + Hooks.nameAndTags.get()))));
                break;
            case "Email":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"emailAddress\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdemailAddress" + Hooks.nameAndTags.get()))));
                break;
            case "Text":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"textNumber\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdtextNumber" + Hooks.nameAndTags.get()))));
                break;
            case "Fax":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"faxNumber\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdfaxNumber" + Hooks.nameAndTags.get()))));
                break;
        }
    }

    public void verfifyAPPLICATION_CONSUMERNotificationReturnedEvent(JsonPath eventbyTraceId) {
        eventId.set(getEventIdFromJsonResponse("APPLICATION_CONSUMER_NOTIFICATION_RETURNED", eventbyTraceId));
        World.generalSave.get().put("eventId", eventId.get());
        Assert.assertNotNull(eventId.get());
        System.out.println("eventId is : " + eventId.get());
        String projectId = new APIAutoUtilities().getProjectId();
        String projectName = new APIAutoUtilities().getProjectName();
        Assert.assertEquals(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].eventName"), "APPLICATION_CONSUMER_NOTIFICATION_RETURNED");
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectId\":" + projectId + ""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"projectName\":\"" + projectName + "\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"action\":\"Update\""));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"notificationId\":" + String.valueOf(World.generalSave.get().get("ReturnedNotificationNotificationId" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"recipientId\":" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdRecipientRecord" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"externalRefId\":" + String.valueOf(World.generalSave.get().get("ReturnedNotificationExternalRefID" + Hooks.nameAndTags.get()))));
        Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("APPLICATION_CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"channel\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdChannel" + Hooks.nameAndTags.get()))));
        //verify destination
        switch (String.valueOf(World.generalSave.get().get("ReturnedNotificationAPPLICATION_CONSUMERChannel" + Hooks.nameAndTags.get()))) {
            case "Mail":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"addressLine1\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdaddressLine1" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"addressLine2\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdaddressLine2" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"city\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdcity" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"state\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdState" + Hooks.nameAndTags.get()))));
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"zip\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdzip" + Hooks.nameAndTags.get()))));
                break;
            case "Email":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"emailAddress\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdemailAddress" + Hooks.nameAndTags.get()))));
                break;
            case "Text":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"textNumber\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdtextNumber" + Hooks.nameAndTags.get()))));
                break;
            case "Fax":
                Assert.assertTrue(eventbyTraceId.get("events[" + World.generalSave.get().get("CONSUMER_NOTIFICATION_RETURNED" + "--INDEX--" + Hooks.nameAndTags.get()) + "].payload").toString().contains("\"faxNumber\":\"" + String.valueOf(World.generalSave.get().get("ReturnedNotificationConsumerIdfaxNumber" + Hooks.nameAndTags.get()))));
                break;
        }

    }
}



