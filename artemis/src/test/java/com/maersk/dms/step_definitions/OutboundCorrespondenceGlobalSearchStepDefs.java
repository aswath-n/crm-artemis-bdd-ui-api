package com.maersk.dms.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.OutboundCorrespondenceSearchPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.PdfUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.utilities.BrowserUtils.getBorderColorCode;
import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static com.maersk.crm.utilities.World.generalSave;
import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceGlobalSearchStepDefs extends CRMUtilities implements ApiBase {

    private OutboundCorrespondenceSearchPage searchPage = new OutboundCorrespondenceSearchPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private CRMContactRecordUIPage init = new CRMContactRecordUIPage();
    public Actions action = new Actions(Driver.getDriver());
    private APIClassUtil getCId = APIClassUtil.getApiClassUtilThreadLocal();
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    private final ThreadLocal<JsonObject> request = ThreadLocal.withInitial(JsonObject::new);
    final ThreadLocal<Random> random = ThreadLocal.withInitial(Random::new);
//    private final ThreadLocal<BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);


    public void fillInSearchCriteria(String searchCriteria, String searchValue) {
        action.moveToElement(init.initContact).build().perform();
        waitFor(2);
        switch (BrowserUtils.filterForLettersOnly(searchCriteria).toUpperCase()) {
            case "CORRESPONDENCEID":
                searchPage.correspondenceId.click();
                if (searchValue.equalsIgnoreCase("Invalid Document Id"))
                    searchPage.correspondenceId.sendKeys(RandomStringUtils.random(8, false, true));
                else if (searchValue.equalsIgnoreCase("previouslyCreated"))
                    searchPage.correspondenceId.sendKeys(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString());
                else if (searchValue.equalsIgnoreCase("randomCorrespondence"))
                    searchPage.correspondenceId.sendKeys(getWorld().outboundCorrespondenceBean.get().correspondenceId);
                else
                    searchPage.correspondenceId.sendKeys(searchValue);
                break;
            case "NOTIFICATIONID":
                searchPage.notificationId.click();
                if (searchValue.equalsIgnoreCase("Invalid Document Id"))
                    searchPage.notificationId.sendKeys(RandomStringUtils.random(8, false, true));
                else if (searchValue.equalsIgnoreCase("previouslyCreated")) {

                    Map<String, Object> notifications = (Map<String, Object>)API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString()).getList("recipients[0].notifications").get(0);
                    World.generalSave.get().put("nid", notifications.get("notificationId").toString());
                    searchPage.notificationId.sendKeys(notifications.get("notificationId").toString());
                }  else if (searchValue.equalsIgnoreCase("sendNowNID")) {
                    searchPage.notificationId.sendKeys(World.generalSave.get().get("sendNowNID").toString());
                }else
                    searchPage.notificationId.sendKeys(searchValue);
                break;

            case "CASEID":
                searchPage.caseId.click();
                if (searchValue.equalsIgnoreCase("Invalid Document Id"))
                    searchPage.caseId.sendKeys(RandomStringUtils.random(8, false, true));
                else if (searchValue.equalsIgnoreCase("previouslyCreated")) {
                    JsonPath resp = (JsonPath) World.generalSave.get().get("OutboundCorrespondenceResponse");
                    World.generalSave.get().put("caseid", resp.get("data.anchor.caseId").toString());
                    searchPage.caseId.sendKeys(resp.get("data.anchor.caseId").toString());
                } else if (searchValue.equalsIgnoreCase("previouslyCreatedCase")) {
                    Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                    String caseId = caseConsumerId.get("caseId");
                  searchPage.caseId.sendKeys(caseId);
            }
                    searchPage.caseId.sendKeys(searchValue);
                break;
            case "REGARDINGCONSUMERID":
                searchPage.regardingConsumerId.click();
                if (searchValue.equalsIgnoreCase("Invalid Document Id"))
                    searchPage.regardingConsumerId.sendKeys(RandomStringUtils.random(8, false, true));
                else if (searchValue.equalsIgnoreCase("previouslyCreated")) {
                    JsonPath resp = (JsonPath) World.generalSave.get().get("OutboundCorrespondenceResponse");
                    World.generalSave.get().put("regardingConsumerId", resp.get("data.anchor.regardingConsumerId[0]").toString());
                    searchPage.regardingConsumerId.sendKeys(resp.get("data.anchor.regardingConsumerId[0]").toString());
                }
                else if (searchValue.equalsIgnoreCase("random10created")) {
                    searchPage.regardingConsumerId.sendKeys(World.generalSave.get().get("regardingConsumerId").toString());
                }else
                    searchPage.regardingConsumerId.sendKeys(searchValue);
                break;
            case "REQUESTDATE":
                searchPage.requestDate.click();
                if (searchValue.equalsIgnoreCase("currentdate")) {
                    searchPage.requestDate.sendKeys(getCurrentDate());
                } else if (searchValue.equalsIgnoreCase("olddate")) {
                    searchPage.requestDate.sendKeys(getPriorDateFormatMMddyyyy(20));
                    searchPage.requestdatedefaultoperator.click();
                    searchPage.DropDownOptions.get(1).click();
                } else {
                    searchPage.requestDate.sendKeys(searchValue);
                }
                break;
            case "SENTDATE":
                searchPage.sentDate.click();
                if (searchValue.equalsIgnoreCase("currentdate")) {
                    searchPage.sentDate.sendKeys(getCurrentDate());
                } else
                    searchPage.sentDate.sendKeys(searchValue);
                break;
            case "CHANNEL":
                selectOptionFromMultiSelectDropDown(searchPage.channeldrpdown, searchValue);
                break;
            case "LANGUAGE":
                selectOptionFromMultiSelectDropDown(searchPage.languagedrpdown, searchValue);
                break;
            case "STATUS":
                selectOptionFromMultiSelectDropDown(searchPage.correspondenceStatusdrpdown, searchValue);
                break;
            case "NOTIFICATIONSTATUS":
                selectOptionFromMultiSelectDropDown(searchPage.notificationStatusDropdown, searchValue);
                break;
            case "CORRESPONDENCESTATUSREASON":
                selectOptionFromMultiSelectDropDown(searchPage.obglobalsearchcorrstatusreasondropdown, searchValue);
                break;
            case "NOTIFICATIONSTATUSREASON":
                selectOptionFromMultiSelectDropDown(searchPage.obglobalsearchnotificationstatusreasondropdown, searchValue);
                break;
            default:
                Assert.fail("Name of field from feature file does not match up to any case - " + searchValue);
        }

    }


    public int rowcount() {
        custompagination(20);
        int rowcount = 0;
        try {
            do {
                rowcount = rowcount + searchPage.rowcount.size();
                searchPage.arrow_forward.click();
                waitFor(8);
            } while (searchPage.arrow_forward.isDisplayed());
        } catch (Exception e) {
        }
        return rowcount;
    }

    public void custompagination(int number) {
        waitFor(2);
        selectOptionFromMultiSelectDropDown(searchPage.paginationdrpdown, "Show " + number);
        waitFor(5);
    }

    public void verifyColumnValuesFromResults(Map<String, String> dataTable) {
        String value = "";
        for (String searchCriteria : dataTable.keySet()) {
            switch (BrowserUtils.filterForLettersOnly(searchCriteria).toUpperCase()) {
                case "CORRESPONDENCEID":
                    for (WebElement element : searchPage.cidColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("previouslyCreated"))
                            value = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()).toString();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "NOTIFICATIONID":
                    for (WebElement element : searchPage.nidColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("previouslyCreated"))
                            value = World.generalSave.get().get("nid").toString();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;

                case "CASEID":
                    for (WebElement element : searchPage.caseIDColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("previouslyCreated"))
                            value = World.generalSave.get().get("caseid").toString();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "REGARDINGCONSUMERID":
                    for (WebElement element : searchPage.RegardingconsumerIDColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("previouslyCreated"))
                            value = World.generalSave.get().get("regardingConsumerId").toString();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "REQUESTDATE":
                    for (WebElement element : searchPage.requestedDateColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("currentdate"))
                            value = getCurrentDate();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "SENTDATE":
                    for (WebElement element : searchPage.statusdateColumn) {
                        if (dataTable.get(searchCriteria).equalsIgnoreCase("currentdate"))
                            value = getCurrentDate();
                        else
                            value = dataTable.get(searchCriteria);
                        Assert.assertEquals(element.getText(), value);
                        checkForDuplicateExists(element);
                    }
                    break;

                case "RECIPIENT":
                    for (WebElement element : searchPage.recipientColumn) {
                        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(element.getText());
                        Assert.assertEquals(response.get("inboundCorrespondence.fromEmailAddress").toString(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "LANGUAGE":
                    for (WebElement element : searchPage.languageColumn) {
                        Assert.assertEquals(element.getText(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;

                case "CORRESPONDENCETYPE":
                    boolean foundType = false;
                    String failType = "";
                    for (WebElement element : searchPage.typeColumn) {
                        for (String types : World.generalList.get()) {
                            if (types.equalsIgnoreCase(element.getText())) {
                                foundType = true;
                            } else {
                                failType = element.getText();
                            }
                        }
                        Assert.assertTrue(foundType, "Found this type instead | " + failType);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "CHANNEL":
                    boolean channelType = false;
                    String channelFailType = "";
                    for (WebElement element : searchPage.channelColumn) {
                        for (String types : World.generalList.get()) {
                            if (types.equalsIgnoreCase(element.getText())) {
                                channelType = true;
                            } else {
                                channelFailType = element.getText();
                            }
                        }
                        Assert.assertTrue(channelType, "Found this type instead | " + channelFailType);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "STATUS":
                    boolean foundStatus = false;
                    String failStatus = "";
                    for (WebElement element : searchPage.statusColumn) {
                        for (String types : World.generalList.get()) {
                            if (types.equalsIgnoreCase(element.getText())) {
                                foundStatus = true;
                            } else {
                                failStatus = element.getText();
                            }
                        }
                        Assert.assertTrue(foundStatus, "Found this status instead | " + failStatus);
                        checkForDuplicateExists(element);
                    }
                    break;

                default:
                    Assert.fail("Search Results from feature file does not match up to any case ");
            }
        }
    }

    public boolean isOnLastPage() {
        return !uiAutoUitilities.get().quickIsDisplayed(searchPage.arrow_forward);
    }

    public void goToNextPage() {
        if (!isOnLastPage()) {
            searchPage.arrow_forward.click();
            waitFor(3);
        }
    }

    public void checkForDuplicateExists(WebElement element) {
        Set<String> dupes = new HashSet<>();
        if (!dupes.add(element.getText())) {//checks if CID duplicates exist in CID column of search results
            Assert.fail("DUPLICATE element FOUND" + element.getText());
        }
    }


    public void verifyToolTipsSearchResults() {
        hover(searchPage.channelEllipsis);
        waitFor(1);
        Assert.assertTrue(searchPage.channelToolTip.isDisplayed());
        hover(searchPage.recipientEllipsis);
        waitFor(1);
        Assert.assertTrue(searchPage.channelToolTip.isDisplayed());
    }

    public void verifyOBNotificationViewedAndContainsValues(String type, List<String> dataTable) throws IOException {
        String NID = String.valueOf(World.generalSave.get().get("NOTIFICATIONID"));
        String rawPdfText = new PdfUtilities().getPdfTextByNotificationID(NID);
        String pdfText = rawPdfText.replaceAll(System.lineSeparator(), " ").replace("’","'");
        for (String value : dataTable) {
            if (type.contains("F")) {
                switch (type) {
                    case "JCFHCC":
                        switch (value){
                            case "title":
                                Assert.assertTrue(pdfText.contains("Hoosier Care Connect"), "Hoosier Care Connect text not found in PDF");
                                break;
                            case "correspondenceDate":
                                Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                                break;
                            case "reason":
                                Assert.assertTrue(StringUtils.containsIgnoreCase(pdfText, World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                                break;
                            default:
                                Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCFHIP":
                        switch (value){
                            case "title":
                                Assert.assertTrue(pdfText.contains("Healthy Indiana Plan"), "Healthy Indiana Plan text not found in PDF");
                                break;
                            case "correspondenceDate":
                                Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                                break;
                            case "reason":
                                Assert.assertTrue(StringUtils.containsIgnoreCase(pdfText, World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF " + pdfText);
                                break;
                            default:
                                Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCFHHW":
                        switch (value){
                            case "title":
                                Assert.assertTrue(pdfText.contains("Hoosier Healthwise"), "Hoosier Healthwise text not found in PDF");
                                break;
                            case "correspondenceDate":
                                Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                                break;
                            case "reason":
                                Assert.assertTrue(StringUtils.containsIgnoreCase(pdfText, World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                                break;
                            default:
                                Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                }
            } else if (type.contains("D")) {
                switch (type) {
                    case "JCDHCC":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Hoosier Care Connect"), "Hoosier Care Connect text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCDHIP":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Healthy Indiana Plan"), "Healthy Indiana Plan text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCDHHW":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Hoosier Healthwise"), "Hoosier Healthwise text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                }
            } else if (type.contains("A")) {
                switch (type) {
                    case "JCAHCC":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Hoosier Care Connect"),"Hoosier Care Connect text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCAHIP":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Healthy Indiana Plan"), "Healthy Indiana Plan text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                    case "JCAHHW":
                        if(value.equalsIgnoreCase("title")){
                            Assert.assertTrue(pdfText.contains("Hoosier Healthwise"), "Hoosier Healthwise text not found in PDF");
                        }else if(value.equalsIgnoreCase("correspondenceDate")){
                            Assert.assertTrue(pdfText.contains(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Correspondence date not found in PDF");
                        }else{
                            Assert.assertTrue(pdfText.contains(World.save.get().get(value)), World.save.get().get(value) + " Not found in PDF");
                        }
                        break;
                }
            }
        }
    }

    public void verifySearchCombinationForGivenStatusAndReason(String searchInput, String type, String status) {
        List<String> listOfStatuses = Arrays.asList("On Hold", "Returned", "Canceled", "Error");
        List<String> listOfProperOnHoldStatuses = Arrays.asList("State requested", "User requested", "Volume control", "Channel issues", "Destination issues", "Template issues", "Vendor issues");
        List<String> listOfProperCorReturnedStatuses = Arrays.asList("Undeliverable", "Refused", "Destination agent unresponsive", "Destination agent rejected message", "Destination invalid", "Change of Address", "Mailbox full");
        List<String> listOfProperNotReturnedStatuses = Arrays.asList("Undeliverable", "Refused", "Destination agent unresponsive", "Destination agent rejected message", "Destination invalid", "Change of Address", "Mailbox full");
        List<String> listOfProperCorCanceledStatuses = Arrays.asList("Requested in error", "Unresolvable error", "No Valid Destination", "Recipient No Longer Active", "Consumer Request", "No longer appropriate");
        List<String> listOfProperNotCanceledStatuses = Arrays.asList("No Valid Destination", "Recipient No Longer Active", "Consumer Request", "No longer appropriate", "Requested in error", "Superseded by Events");
        List<String> listOfProperErrorStatuses = Arrays.asList("Export Error", "Assembly Error", "Invalid Address");
        List<List<String>> listOfAllCorStatuses = Arrays.asList(listOfProperOnHoldStatuses, listOfProperCorReturnedStatuses, listOfProperCorCanceledStatuses, listOfProperErrorStatuses);
        List<List<String>> listOfAllNotStatuses = Arrays.asList(listOfProperOnHoldStatuses, listOfProperNotReturnedStatuses, listOfProperNotCanceledStatuses, listOfProperErrorStatuses);
        JsonArray tempStatusArray = null;
        JsonArray tempStatusReasonArray = null;
        int iteration = 0;
        if (searchInput.equals("invalid")) {
            if (type.equals("Correspondence")) {
                switch (status) {
                    case "On Hold":
                        JsonArray onHoldJsonArray = new JsonArray();
                        onHoldJsonArray.add(status);
                        for (List<String> listOfAllCorStatus : listOfAllCorStatuses) {
                            if (listOfAllCorStatus.equals(listOfProperOnHoldStatuses)) {
                                continue;
                            }
                            for (String allCorStatus : listOfAllCorStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allCorStatus);
                                request.get().add("correspondenceStatus", onHoldJsonArray);
                                request.get().add("correspondenceStatusReason", tempArray);
                                synchronized (response){
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));

                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence status " + status + " and Correspondence reason " + allCorStatus);
                            }
                        }
                        break;
                    case "Returned":
                        JsonArray returnedJsonArray = new JsonArray();
                        returnedJsonArray.add(status);
                        for (List<String> listOfAllCorStatus : listOfAllCorStatuses) {
                            if (listOfAllCorStatus.equals(listOfProperCorReturnedStatuses)) {
                                continue;
                            }
                            for (String allCorStatus : listOfAllCorStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allCorStatus);
                                request.get().add("correspondenceStatus", returnedJsonArray);
                                request.get().add("correspondenceStatusReason", tempArray);
                                synchronized (response){
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence status " + status + " and Correspondence reason " + allCorStatus);
                            }
                        }
                        break;
                    case "Canceled":
                        JsonArray canceledJsonArray = new JsonArray();
                        canceledJsonArray.add(status);
                        for (List<String> listOfAllCorStatus : listOfAllCorStatuses) {
                            if (listOfAllCorStatus.equals(listOfProperCorCanceledStatuses)) {
                                continue;
                            }
                            for (String allCorStatus : listOfAllCorStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allCorStatus);
                                request.get().add("correspondenceStatus", canceledJsonArray);
                                request.get().add("correspondenceStatusReason", tempArray);
                                synchronized (response){
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence status " + status + " and Correspondence reason " + allCorStatus);
                            }
                        }

                        break;
                    case "Error":
                        JsonArray errorJsonArray = new JsonArray();
                        errorJsonArray.add(status);
                        for (List<String> listOfAllCorStatus : listOfAllCorStatuses) {
                            if (listOfAllCorStatus.equals(listOfProperErrorStatuses)) {
                                continue;
                            }
                            for (String allCorStatus : listOfAllCorStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allCorStatus);
                                request.get().add("correspondenceStatus", errorJsonArray);
                                request.get().add("correspondenceStatusReason", tempArray);
                                synchronized (response){
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence status " + status + " and Correspondence reason " + allCorStatus);
                            }
                        }
                        break;
                    case "multiple":
                        do {
                            for (int i = 0; i < listOfStatuses.size(); i++) {
                                for (int j = 0; j < listOfStatuses.size(); j++) {
                                    tempStatusArray = new JsonArray();
                                    tempStatusReasonArray = new JsonArray();
                                    int randomListValueOne = random.get().nextInt(listOfAllCorStatuses.size());
                                    int randomListValueTwo = random.get().nextInt(listOfAllCorStatuses.size());
                                    if (i != j && i != randomListValueOne && i != randomListValueTwo && j != randomListValueTwo && j != randomListValueOne && randomListValueOne != randomListValueTwo) {

                                        String statusReasonOne = CRMUtilities.getRandomStringFromList(listOfAllCorStatuses.get(randomListValueOne));
                                        String statusReasonTwo = CRMUtilities.getRandomStringFromList(listOfAllCorStatuses.get(randomListValueTwo));

                                        tempStatusArray.add(listOfStatuses.get(i));
                                        tempStatusArray.add(listOfStatuses.get(j));
                                        tempStatusReasonArray.add(statusReasonOne);
                                        tempStatusReasonArray.add(statusReasonTwo);
                                        request.get().add("correspondenceStatus", tempStatusArray);
                                        request.get().add("correspondenceStatusReason", tempStatusReasonArray);
                                        synchronized (response){
                                            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                        }
                                        Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence statuses " + tempStatusArray + " and Correspondence reasons " + tempStatusReasonArray);
                                        iteration++;
                                    }
                                }
                            }
                        } while (iteration < listOfProperOnHoldStatuses.size());
                        break;
                }
            } else if (type.equals("Notification")) {
                switch (status) {
                    case "On Hold":
                        JsonArray onHoldJsonArray = new JsonArray();
                        onHoldJsonArray.add(status);
                        for (List<String> listOfAllNotStatus : listOfAllNotStatuses) {
                            if (listOfAllNotStatus.equals(listOfProperOnHoldStatuses)) {
                                continue;
                            }
                            for (String allNotStatus : listOfAllNotStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allNotStatus);
                                request.get().add("notificationStatus", onHoldJsonArray);
                                request.get().add("notificationStatusReason", tempArray);
                                synchronized (response){
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for notification status " + status + " and Notification reason " + allNotStatus);

                            }
                        }
                        break;
                    case "Returned":
                        JsonArray returnedJsonArray = new JsonArray();
                        returnedJsonArray.add(status);
                        for (List<String> listOfNotCorStatus : listOfAllNotStatuses) {
                            if (listOfNotCorStatus.equals(listOfProperNotReturnedStatuses)) {
                                continue;
                            }
                            for (String allNotStatus : listOfNotCorStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allNotStatus);
                                request.get().add("notificationStatus", returnedJsonArray);
                                request.get().add("notificationStatusReason", tempArray);
                                synchronized (response) {
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for notification status " + status + " and Notification reason " + allNotStatus);
                            }
                        }
                        break;
                    case "Canceled":
                        JsonArray canceledJsonArray = new JsonArray();
                        canceledJsonArray.add(status);
                        for (List<String> listOfAllNotStatus : listOfAllNotStatuses) {
                            if (listOfAllNotStatus.equals(listOfProperNotCanceledStatuses)) {
                                continue;
                            }
                            for (String allNotStatus : listOfAllNotStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allNotStatus);
                                request.get().add("notificationStatus", canceledJsonArray);
                                request.get().add("notificationStatusReason", tempArray);
                                synchronized (response) {
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for notification status " + status + " and Notification reason " + allNotStatus);
                            }
                        }

                        break;
                    case "Error":
                        JsonArray errorJsonArray = new JsonArray();
                        errorJsonArray.add(status);
                        for (List<String> listOfAllNotStatus : listOfAllNotStatuses) {
                            if (listOfAllNotStatus.equals(listOfProperErrorStatuses)) {
                                continue;
                            }
                            for (String allNotStatus : listOfAllNotStatus) {
                                JsonArray tempArray = new JsonArray();
                                tempArray.add(allNotStatus);
                                request.get().add("notificationStatus", errorJsonArray);
                                request.get().add("notificationStatusReason", tempArray);
                                synchronized (response) {
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for notification status " + status + " and Notification reason " + allNotStatus);
                            }
                        }
                        break;
                    case "multiple":
                        do {
                            for (int i = 0; i < listOfStatuses.size(); i++) {
                                for (int j = 0; j < listOfStatuses.size(); j++) {
                                    tempStatusArray = new JsonArray();
                                    tempStatusReasonArray = new JsonArray();
                                    int randomListValueOne = random.get().nextInt(listOfAllCorStatuses.size());
                                    int randomListValueTwo = random.get().nextInt(listOfAllCorStatuses.size());
                                    if (i != j && i != randomListValueOne && i != randomListValueTwo && j != randomListValueTwo && j != randomListValueOne && randomListValueOne != randomListValueTwo) {
                                        String statusReasonOne = CRMUtilities.getRandomStringFromList(listOfAllNotStatuses.get(randomListValueOne));
                                        String statusReasonTwo = CRMUtilities.getRandomStringFromList(listOfAllNotStatuses.get(randomListValueTwo));

                                        tempStatusArray.add(listOfStatuses.get(i));
                                        tempStatusArray.add(listOfStatuses.get(j));
                                        tempStatusReasonArray.add(statusReasonOne);
                                        tempStatusReasonArray.add(statusReasonTwo);
                                        request.get().add("notificationStatus", tempStatusArray);
                                        request.get().add("notificationStatusReason", tempStatusReasonArray);
                                        synchronized (response) {
                                            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                        }
                                        Assert.assertEquals(response.get().getInt("data.numberOfElements"), 0, "Expected number of elements 0, but found " + response.get().getString("data.numberOfElements") + " for notification statuses " + tempStatusArray + " and Notification reasons " + tempStatusReasonArray);
                                        iteration++;
                                    }
                                }
                            }
                        } while (iteration < listOfProperOnHoldStatuses.size());
                        break;
                }
            }
        }else if(searchInput.equals("valid")){
            if (type.equals("Correspondence")) {
                    for (int i = 0; i < listOfStatuses.size(); i++) {
                            tempStatusArray = new JsonArray();
                            tempStatusReasonArray = new JsonArray();
                            int randomListValueOne = random.get().nextInt(listOfAllCorStatuses.size());
                            int randomListValueTwo = random.get().nextInt(listOfAllCorStatuses.size());
                            if (randomListValueOne!=randomListValueTwo) {

                                String statusReasonOne = CRMUtilities.getRandomStringFromList(listOfAllCorStatuses.get(randomListValueOne));
                                String statusReasonTwo = CRMUtilities.getRandomStringFromList(listOfAllCorStatuses.get(randomListValueTwo));

                                tempStatusArray.add(listOfStatuses.get(randomListValueOne));
                                tempStatusArray.add(listOfStatuses.get(randomListValueTwo));
                                tempStatusReasonArray.add(statusReasonOne);
                                tempStatusReasonArray.add(statusReasonTwo);
                                request.get().add("correspondenceStatus", tempStatusArray);
                                request.get().add("correspondenceStatusReason", tempStatusReasonArray);
                                synchronized (response) {
                                    response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                                }
                                Assert.assertTrue(response.get().getInt("data.numberOfElements") > 0, "Expected number of elements greater than 0, but found " + response.get().getString("data.numberOfElements") + " for correspondence statuses " + tempStatusArray + " and Correspondence reasons " + tempStatusReasonArray);
                            }
                    }
            } else if (type.equals("Notification")) {
                for (int i = 0; i < listOfStatuses.size(); i++) {
                    for (int j = 0; j < listOfStatuses.size(); j++) {
                        tempStatusArray = new JsonArray();
                        tempStatusReasonArray = new JsonArray();
                        int randomListValueOne = random.get().nextInt(listOfAllCorStatuses.size());
                        int randomListValueTwo = random.get().nextInt(listOfAllCorStatuses.size());
                        if (i != j && randomListValueOne != randomListValueTwo) {
                            String statusReasonOne = CRMUtilities.getRandomStringFromList(listOfAllNotStatuses.get(randomListValueOne));
                            String statusReasonTwo = CRMUtilities.getRandomStringFromList(listOfAllNotStatuses.get(randomListValueTwo));

                            tempStatusArray.add(listOfStatuses.get(randomListValueOne));
                            tempStatusArray.add(listOfStatuses.get(randomListValueTwo));
                            tempStatusReasonArray.add(statusReasonOne);
                            tempStatusReasonArray.add(statusReasonTwo);
                            request.get().add("notificationStatus", tempStatusArray);
                            request.get().add("notificationStatusReason", tempStatusReasonArray);
                            synchronized (response) {
                                response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().outboundGlobalSearch(request.get()));
                            }
                            Assert.assertTrue(response.get().getInt("data.numberOfElements") > 0, "Expected number of elements greater than 0, but found " + response.get().getString("data.numberOfElements") + " for notification statuses " + tempStatusArray + " and Notification reasons " + tempStatusReasonArray);
                        }
                    }
                }
            }
        }
    }
}
