package com.maersk.dms.step_definitions;

import com.maersk.crm.pages.crm.CRMCaseContactDetailsPage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.pages.InboundCorrespondenceSearchPage;
import com.maersk.dms.pages.OutboundCorrespondenceSearchPage;
import com.maersk.dms.pages.ViewInboundCorrespondenceDetailsUIAPIPage;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maersk.crm.step_definitions.CRM_SearchApplicationStepDef.apiApplicationId;
import static com.maersk.crm.utilities.BrowserUtils.*;

public class InboundCorrespondenceSearchStepDef extends CRMUtilities implements ApiBase {
    private InboundCorrespondenceSearchPage inboundCorrespondenceSearchPage = new InboundCorrespondenceSearchPage();
    private ViewInboundCorrespondenceDetailsUIAPIPage viewInboundCorrespondenceDetailsUIAPIPage = new ViewInboundCorrespondenceDetailsUIAPIPage();
    final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final ThreadLocal<Actions> action = ThreadLocal.withInitial(() -> new Actions(Driver.getDriver()));
    private final ThreadLocal<String> token = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> actualCIdlist = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<List<String>> expectedCIdlist = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<String> firstclickedOnCID = ThreadLocal.withInitial(String::new);
    private CRMCaseContactDetailsPage crmCaseContactDetailsPage = new CRMCaseContactDetailsPage();
//    private final ThreadLocal <BrowserUtils> browserUtils = ThreadLocal.withInitial(BrowserUtils::new);

    public void verifyColumnValuesFromResults(Map<String, String> dataTable) {
        for (String searchCriteria : dataTable.keySet()) {
            switch (filterForLettersOnly(searchCriteria).toUpperCase()) {
                case "CID":
                    String cidResult = dataTable.get(searchCriteria);
                    if ("fromRequest".equalsIgnoreCase(cidResult)) {
                        cidResult = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
                    } else if ("InboundDocument".equalsIgnoreCase(cidResult)) {
                        cidResult = String.valueOf(World.generalSave.get().get("InboundDocumentIdDigital"));
                    }
                    for (WebElement element : inboundCorrespondenceSearchPage.cidColumn) {
                        Assert.assertEquals(element.getText(), cidResult);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "CASEID":
                    String caseId = dataTable.get(searchCriteria);
                    if ("previouslyCreated".equalsIgnoreCase(caseId)) {
                        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
                        caseId = caseConsumerId.get("caseId");
                    }
                    for (WebElement element : inboundCorrespondenceSearchPage.caseIDColumn) {
                        Assert.assertEquals(element.getText(),caseId);
                        checkForDuplicateExists(element);
                    }
                    break;
                case "CSETID":
                    for (WebElement element : inboundCorrespondenceSearchPage.setIdColumn) {
                        Assert.assertEquals(element.getText(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "ADDDEMOPHONENUMBER":
                    String ADDDEMOPHONENUMBER = dataTable.get(searchCriteria);
                    if ("previouslyCreated".equalsIgnoreCase(ADDDEMOPHONENUMBER)) {
                        ADDDEMOPHONENUMBER =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getIbDetailSpecificValue("fromPhoneNumber");
                    }
                    for (WebElement element : inboundCorrespondenceSearchPage.cidColumn) {
                        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(element.getText());
                        Assert.assertEquals(response.get("inboundCorrespondence.fromPhoneNumber").toString(), ADDDEMOPHONENUMBER,"phone number not matching");
                        checkForDuplicateExists(element);
                    }
                    break;
                case "FROMEMAIL":
                    for (WebElement element : inboundCorrespondenceSearchPage.cidColumn) {
                        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(element.getText());
                        Assert.assertEquals(response.get("inboundCorrespondence.fromEmailAddress").toString(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "FROMNAME":
                    for (WebElement element : inboundCorrespondenceSearchPage.fromNameColumn) {
                        Assert.assertEquals(element.getText(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "ORIGINALCID":
                    for (WebElement element : inboundCorrespondenceSearchPage.cidColumn) {
                        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(element.getText());
                        Assert.assertEquals(response.get("inboundCorrespondence.originItemId").toString(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "ORIGINALCSETID":
                    for (WebElement element : inboundCorrespondenceSearchPage.cidColumn) {
                        JsonPath response =API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().bffGetInboundCorrespondenceDetails(element.getText());
                        Assert.assertEquals(response.get("inboundCorrespondence.originSetId").toString(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "CONSUMERID":
                    for (WebElement element : inboundCorrespondenceSearchPage.consumerIDColumn) {
                        Assert.assertEquals(element.getText(), dataTable.get(searchCriteria));
                        checkForDuplicateExists(element);
                    }
                    break;
                case "CORRESPONDENCETYPE":
                    boolean foundType = false;
                    String failType = "";
                    for (WebElement element : inboundCorrespondenceSearchPage.typeColumn) {
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
                    for (WebElement element : inboundCorrespondenceSearchPage.channelColumn) {
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
                    for (WebElement element : inboundCorrespondenceSearchPage.statusColumn) {
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
                case "DATERECEIVED":
                    LocalDate datePriorNDays = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    String today = datePriorNDays.format(formatter);
                    for (WebElement element : inboundCorrespondenceSearchPage.dateReceivedColumn) {
                        Assert.assertEquals(element.getText(), today);
                        checkForDuplicateExists(element);
                    }
                    break;

                case "VIEWICON":
                    for (WebElement element : inboundCorrespondenceSearchPage.viewIcondColumn) {
                        Assert.assertTrue(element.isDisplayed());
                        checkForDuplicateExists(element);
                    }
                    break;
            }
        }
    }

    public boolean isOnLastPage() {
        return !uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.nextPageArrowKeyButton);
    }

    public void goToNextPage() {
        if (!isOnLastPage()) {
            inboundCorrespondenceSearchPage.nextPageArrowKeyButton.click();
            waitFor(3);
        }
    }

    public void checkForDuplicateExists(WebElement element) {
        Set<String> dupes = new HashSet<>();
        if (!dupes.add(element.getText())) {//checks if CID duplicates exist in CID column of search results
            Assert.fail("DUPLICATE element FOUND" + element.getText());
        }
    }

    public void verifyColumnNames(List<String> dataTable) {
        List<String> actualHeaders = new ArrayList<>();
        List<String> expectedHeaders = new ArrayList<>();
        expectedHeaders.addAll(dataTable);
        for (WebElement element : inboundCorrespondenceSearchPage.resultcolumnNames) {
            if (uiAutoUitilities.get().hasText(element.getText())) {
                actualHeaders.add(element.getText());
            }
        }
        Assert.assertEquals(actualHeaders.size(), expectedHeaders.size());
        Collections.sort(expectedHeaders, Collections.reverseOrder());
        Collections.sort(actualHeaders, Collections.reverseOrder());
        for (int i = 0; i < expectedHeaders.size(); i++) {
            Assert.assertEquals(actualHeaders.get(i), expectedHeaders.get(i));
        }
    }

    public void verifyLabels(List<String> dataTable) {
        List<String> actualLabels = new ArrayList<>();
        List<String> expectedLabels = new ArrayList<>();
        expectedLabels.addAll(dataTable);
        for (WebElement element : inboundCorrespondenceSearchPage.labelNames) {
            actualLabels.add(element.getText());
        }
        Assert.assertEquals(actualLabels.size(), expectedLabels.size());
        Collections.sort(expectedLabels);
        Collections.sort(actualLabels);
        for (int i = 0; i < expectedLabels.size(); i++) {
            Assert.assertEquals(actualLabels.get(i), expectedLabels.get(i));
        }
    }

    public void verifyChannelDropdown(List<String> dataTable) {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.addAll(dataTable);
        action.get().moveToElement(inboundCorrespondenceSearchPage.channelDropdown).click().build().perform();
        waitFor(2);
        List<WebElement> actualValues = inboundCorrespondenceSearchPage.channelDropdownValues;
        List<String> actual = new ArrayList<>();
        for (WebElement actualValue : actualValues) {
            if (uiAutoUitilities.get().hasText(actualValue.getText())) {
                actual.add(actualValue.getText());
            }
        }
        action.get().moveToElement(inboundCorrespondenceSearchPage.channelDropdown).click().build().perform();
        waitFor(1);
        Assert.assertEquals(actual.size(), expectedValues.size(), "values size mismatch");
        Collections.sort(expectedValues);
        Collections.sort(actual);
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(actual.get(i), expectedValues.get(i));
        }
    }

    public void verifyStatusDropdown(List<String> dataTable) {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.addAll(dataTable);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(2);
        action.get().moveToElement(inboundCorrespondenceSearchPage.statusDropdown).click().build().perform();
        waitFor(2);
        List<WebElement> actualValues = inboundCorrespondenceSearchPage.statusDropdownValues;
        if (actualValues.size() == 0) {
            inboundCorrespondenceSearchPage.statusDropdownValues = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'Complete')]/.."));
            actualValues = inboundCorrespondenceSearchPage.statusDropdownValues;
        }
        List<String> actual = new ArrayList<>();
        for (WebElement actualValue : actualValues) {
            if (filterTextFor(actualValue.getText()).length() > 0) {
                actual.add(actualValue.getText());
            }
        }
        action.get().moveToElement(inboundCorrespondenceSearchPage.statusDropdown).click().build().perform();
        waitFor(1);
        Assert.assertEquals(actual.size(), expectedValues.size(), "values size mismatch");
        Collections.sort(expectedValues);
        Collections.sort(actual);
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(actual.get(i), expectedValues.get(i));
        }
    }

    public void clickOnSearchButton() {
        inboundCorrespondenceSearchPage.searchButton.click();
    }

    public void verifyErrorMessage(String expectedMessage) {
        if (expectedMessage.contains("excess")) {
            waitForVisibility(inboundCorrespondenceSearchPage.excessMessageText, 5);
            String alertMessage = inboundCorrespondenceSearchPage.excessMessageText.getText();
            Assert.assertEquals(alertMessage.trim(), expectedMessage.trim());
        } else if (expectedMessage.contains("found")) {
            waitForVisibility(inboundCorrespondenceSearchPage.nothingFound, 5);
            String alertMessage = inboundCorrespondenceSearchPage.nothingFound.getText();
            Assert.assertEquals(alertMessage, expectedMessage);
        } else if (expectedMessage.contains("Invalid")) {
            waitForVisibility(inboundCorrespondenceSearchPage.noCIdDocumentErrorMsg, 5);
            String alertMessage = inboundCorrespondenceSearchPage.noCIdDocumentErrorMsg.getText();
            expectedMessage = expectedMessage + " " + String.valueOf(World.generalSave.get().get("InboundDocumentId"));
            expectedMessage = expectedMessage.replace("[", "").replace("]", "");
            Assert.assertTrue(alertMessage.contains(expectedMessage), "Alert msg: " + alertMessage + " Expected msg: " + expectedMessage);
        } else {
            waitFor(1);
            String alertMessage = inboundCorrespondenceSearchPage.errorMessageText.getText();
            Assert.assertEquals(alertMessage, expectedMessage);
        }
    }

    public void uploadRandomInboundDocument() {
        ObtainCorrespondenceItemsFromECMSRequestStepDefs obtainCorrespondenceItemsFromECMSRequestStepDefs = new ObtainCorrespondenceItemsFromECMSRequestStepDefs();
        try {
            token.set(obtainCorrespondenceItemsFromECMSRequestStepDefs.getAccessTokenBLCRMDMS());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = obtainCorrespondenceItemsFromECMSRequestStepDefs.inboundCorrespondenceInOnbase(token.get(), "", "");
        Assert.assertTrue(hasOnlyDigits(response), "response from inbound upload" + response);
    }

    public void fillInRandomValuesNoSearch(List<String> dataTable) {
        for (String criteria : dataTable) {
            switch (criteria.toUpperCase()) {
                case "CID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.correspondenceId.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "CASEID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.caseID.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "CONSUMERID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.consumerID.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "CORRESPONDENCESETID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.correspondenceSetID.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "ORIGINALCORRESPONDENCEID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.sourceCorrespondenceID.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "ORIGINALSETID":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.sourceCorrespondenceSetID.sendKeys(RandomStringUtils.random(5, false, true));
                    waitFor(1);
                    break;
                case "FROMPHONE":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.addDemoPhoneNumber.sendKeys(RandomStringUtils.random(10, false, true));
                    waitFor(1);
                    break;
                case "FROMEMAIL":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.fromEmailAddress.sendKeys(RandomStringUtils.random(5, true, false) + "@" + RandomStringUtils.random(4, true, false) + ".com");
                    waitFor(1);
                    break;
                case "FROMNAME":
                    waitFor(1);
                    inboundCorrespondenceSearchPage.fromName.sendKeys(RandomStringUtils.random(5, true, false));
                    waitFor(1);
                    break;
                case "TYPE":
                    waitFor(1);
                    selectRandomDropDownOption(inboundCorrespondenceSearchPage.typeDropDown);
                    waitFor(1);
                    Driver.getDriver().findElement(By.xpath("//body")).click();
                    waitFor(1);
                    break;
                case "STATUS":
                    waitFor(1);
                    selectRandomDropDownOption(inboundCorrespondenceSearchPage.statusDropDown);
                    waitFor(1);
                    Driver.getDriver().findElement(By.xpath("//body")).click();
                    waitFor(1);
                case "CHANNEL":
                    waitFor(1);
                    selectRandomDropDownOption(inboundCorrespondenceSearchPage.channelDropdown);
                    waitFor(1);
                    Driver.getDriver().findElement(By.xpath("//body")).click();
                    waitFor(1);
                    break;
                case "DATERECEIVED":
                    waitFor(1);
                    LocalDate datePriorNDays = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    String today = datePriorNDays.format(formatter).split("/")[1];
                    if (today.charAt(0) == '0') {
                        today = today.substring(1);
                    }
                    inboundCorrespondenceSearchPage.calendar.click();
                    waitFor(4);
                    action.get().moveToElement(Driver.getDriver().findElement(By.xpath("//button/span/p[.='" + today + "']"))).click().build().perform();
                    waitFor(1);
                    Driver.getDriver().findElement(By.xpath("//button/span[.='OK']")).click();
                    waitFor(1);
                    break;

            }
        }
    }

    public void clickOnCancelButton() {
        waitFor(4);
        scrollToElement(inboundCorrespondenceSearchPage.consumerID);
        waitFor(1);
        inboundCorrespondenceSearchPage.cancelButton.click();
    }

    public void clickOnCancelButtonForApplicationSearch() {
        waitFor(1);
        inboundCorrespondenceSearchPage.cancelButton.click();
    }

    public void verifyAllFieldsCleared() {
        Assert.assertTrue(inboundCorrespondenceSearchPage.consumerID.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.correspondenceSetID.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.typeDropDownValue.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.statusDropDownValue.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.dateReceived.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.channelDropdownValue.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.addDemoPhoneNumber.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.fromEmailAddress.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.fromName.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.sourceCorrespondenceID.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.sourceCorrespondenceSetID.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.caseID.getAttribute("value").length() < 1);
        Assert.assertTrue(inboundCorrespondenceSearchPage.consumerID.getAttribute("value").length() < 1);
    }

    public void verifyDateReceivedOperatorDropdown() {
        Assert.assertTrue(inboundCorrespondenceSearchPage.equalSymbols.isDisplayed(), "date received is not in default position");
    }

    public void verifyCIDhyperlink() {
        waitFor(2);
        for (WebElement cid : inboundCorrespondenceSearchPage.cidColumn) {
            Assert.assertTrue(cid.getAttribute("class").contains("mx-link"), "CID is not a Hyperlink : " + cid.getText());
        }
    }

    public void verifyClickedOnCIDPage() {

        String cIDvalueOnDetailsPage = viewInboundCorrespondenceDetailsUIAPIPage.cIDValueNumber.getText();
        Assert.assertEquals(firstclickedOnCID.get(), cIDvalueOnDetailsPage, "Clicked on CID: " + firstclickedOnCID + "CID on details: " + cIDvalueOnDetailsPage);
    }

    public void getCIDFirstValue() {
        firstclickedOnCID.set( inboundCorrespondenceSearchPage.cidList.get(0).getText());
    }

    public void clickONInboundDetailHeader() {
        waitFor(1);
        scrollToElement(viewInboundCorrespondenceDetailsUIAPIPage.backArrow);
        waitFor(1);
        viewInboundCorrespondenceDetailsUIAPIPage.backArrow.click();
        waitFor(1);
    }

    public void getExpectedCIDlist() {
        waitFor(2);
        for (WebElement cid : inboundCorrespondenceSearchPage.cidColumn) {
            expectedCIdlist.get().add(cid.getText());
        }
    }

    public void getListactualofCID() {
        waitFor(2);
        for (WebElement cid : inboundCorrespondenceSearchPage.cidColumn) {
            actualCIdlist.get().add(cid.getText());
        }
        Assert.assertTrue(actualCIdlist.get() != null, "Cid list is empty");
    }

    public void goBacktoFirstSearchPage() {
        boolean presenceOfFirstPageArrow = uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.backToFirstPageArrow);
        if (presenceOfFirstPageArrow) {
            scrollToElement(inboundCorrespondenceSearchPage.backToFirstPageArrow);
            waitFor(1);
            inboundCorrespondenceSearchPage.backToFirstPageArrow.click();
        } else {
            System.out.println("no back to first page button displayed");
        }
    }

    public void verifyActualExpectedCIDlist() {
        Assert.assertEquals(actualCIdlist.get(),  expectedCIdlist.get(), "Actual CID value list does not match Expected CID value List");
    }

    public void getCIDlastValue() {
        firstclickedOnCID.set(inboundCorrespondenceSearchPage.cidList.get(inboundCorrespondenceSearchPage.cidList.size() - 1).getText());
    }

    public void hoverPageButtonNextPage() {
        if (!isOnLastPage()) {
            action.get().moveToElement(Driver.getDriver().findElement(By.xpath("//span[.='arrow_forward']"))).click(Driver.getDriver().findElement(By.xpath("//span[.='arrow_forward']"))).build().perform();
            waitFor(3);
        }
    }

    public void checkCreatedCidNotInList(String cId) {
        boolean cidInList = false;
        cId = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        for (String eachCid : actualCIdlist.get()) {
            if (cId.equals(eachCid)) {
                cidInList = true;
            }
        }
        Assert.assertTrue(cidInList == false, "CId " + cId + " is in list ");
    }

    public void checkcIdnotInCaseContactDetailsInboList(String cId) {

        boolean cidInList = false;
        cId = String.valueOf(World.generalSave.get().get("InboundDocumentId"));
        for (String eachCid : actualCIdlist.get()) {
            if (cId.equals(eachCid)) {
                cidInList = true;
            }
        }
        Assert.assertTrue(cidInList == false, "CId " + cId + " is in list ");
    }

    public void getListOfCaseContactDetailsCIDs() {
        waitFor(2);
        for (WebElement cid : crmCaseContactDetailsPage.caseContactDeInboundcidColumn) {
            actualCIdlist.get().add(cid.getText());
        }
        Assert.assertTrue(actualCIdlist.get() != null, "Cid list in details tab is empty");
    }

    public void navToInbDetails(String inbDocument) {
        switch (inbDocument.toUpperCase()) {
            case "FIRST AVAILABLE":
                World.generalSave.get().put("InboundDocumentId", inboundCorrespondenceSearchPage.cidColumn.get(0).getText());
                inboundCorrespondenceSearchPage.cidColumn.get(0).click();
                waitFor(2);
                break;
            default:
                Assert.fail("inbDocument argument must match an existing case | " + inbDocument);
        }
    }

    public void clickViewInbDocument(String inbDocument) {
        switch (inbDocument.toUpperCase()) {
            case "FIRST AVAILABLE":
                hover(inboundCorrespondenceSearchPage.viewIcondColumn.get(0));
                waitFor(2);
                inboundCorrespondenceSearchPage.viewIcondColumn.get(0).click();
                waitFor(7);
                break;

            case "DOWNLOAD ICON":
                hover(inboundCorrespondenceSearchPage.fileCopy);
                waitFor(2);
                inboundCorrespondenceSearchPage.fileCopy.click();
                waitFor(7);
                break;
            default:
                Assert.fail("inbDocument argument must match an existing case | " + inbDocument);
        }
    }

    public void verifyTypeDropDown(Map<String, String> dataTable) {
        ArrayList<String> expected = new ArrayList();
        for (String exp : dataTable.keySet()) {
            expected.add(dataTable.get(exp.trim()));
        }
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(inboundCorrespondenceSearchPage.typeDropDown.getText().split(",")));
        actual.replaceAll(String::trim);
        Collections.sort(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    public void verifyTypeDropDown() {
        boolean found = false;
        inboundCorrespondenceSearchPage.typeDropDown.click();
        waitFor(2);
        List<String> actual = new ArrayList<>();
        inboundCorrespondenceSearchPage.typeDropDownOptions.stream().forEach((k) -> {
            actual.add(k.getText());
        });
        List<String> expected = new ArrayList<>();
        expected.addAll(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    public void verifyStatusDropDownSelected(Map<String, String> dataTable) {
        boolean found = false;
        for (String expected : dataTable.keySet()) {
            for (String selected : inboundCorrespondenceSearchPage.statusDropdown.getText().split(",")) {
                if (selected.trim().equalsIgnoreCase(dataTable.get(expected))) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
            found = false;
        }
    }

    public void verifyCoverVAHidingInbSearchOptions(List<String> elements) {
        waitForPageToLoad(15);
        waitFor(2);
        for (String element : elements) {
            switch (element.toUpperCase()) {
                case "CASE ID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.caseID), "Case Id is not hidden");
                    break;
                case "CONSUMER ID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.consumerID), "Consumer Id is not hidden");
                    break;
                case "CASE ID RESULTS COLUMN":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.caseIdResultsColumnLabel), "Case Id is not hidden");
                    break;
                case "CONSUMER ID RESULTS COLUMN":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceSearchPage.consumerIdResultsColumnLabel), "Consumer Id is not hidden");
                    break;
                case "SEARCH CASE DROPDOWN":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.casetypedropdown), "Case Tytpe dropdown is not hidden");
                    break;
                case "CRM CASE ID":
                    Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(viewInboundCorrespondenceDetailsUIAPIPage.caseidfield), "CRM Case Id is not hidden");
                    break;
                default:
                    Assert.fail("element not found in switch case");
            }
        }
    }

    public void verifyInbCaseConsumerSearchvalues() {
        waitFor(2);
        viewInboundCorrespondenceDetailsUIAPIPage.expandarrow.click();
        waitFor(2);
        //viewInboundCorrespondenceDetailsUIAPIPage.page.click();
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.linkCaseOnly.isEnabled(), "missing linkCaseOnly checkbox");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.consumermember.isEnabled(), "missing consumer member radiobutton");
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.linkrecord.isEnabled(), "missing link record button");
    }


    public void linkingCaseConsumertoibcorrespondence(String profiletype) {

        viewInboundCorrespondenceDetailsUIAPIPage.expandarrow.click();
        waitFor(2);

        if (profiletype.equalsIgnoreCase("case")) {
            viewInboundCorrespondenceDetailsUIAPIPage.linkCaseOnly.click();
            Assert.assertFalse(viewInboundCorrespondenceDetailsUIAPIPage.consumermember.isEnabled(), "Consumer member radiobutton is enabled");
            viewInboundCorrespondenceDetailsUIAPIPage.linkrecord.click();
        }
        if (profiletype.equalsIgnoreCase("case and consumer")) {
            viewInboundCorrespondenceDetailsUIAPIPage.consumermember.click();
            viewInboundCorrespondenceDetailsUIAPIPage.linkrecord.click();
        }
        if (profiletype.equalsIgnoreCase("consumer")) {
            viewInboundCorrespondenceDetailsUIAPIPage.linkrecord.click();
        }
    }


    public void viewingcaselinktoibcorrespondence() {
        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
        String caseid = caseConsumerId.get("caseId");
        String casename = "Case";
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(2);
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(5);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.caseid.getText(), caseid);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.casename.getText(), casename);
    }

    public void linkingApplicationtoibcorrespondence() {
        waitFor(1);
        viewInboundCorrespondenceDetailsUIAPIPage.linkApplicationButton.click();
    }

    public void viewingApplicationLinkedToInboundCorrespondence() {
        waitFor(1);
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(3);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.applicationID.getText(), apiApplicationId.get());
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.applicationName.getText(), "Application");
    }


    public void viewingcaseandconsumerlinktoibcorrespondence(String valuetype) {

        String caseid = "", consumerid = "", casename = "Case", consumername = "Consumer Profile";

        if (valuetype.equalsIgnoreCase("previouslycreated")) {
            Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");
            caseid = caseConsumerId.get("caseId");
            consumerid = caseConsumerId.get("consumerId");
        } else if (valuetype.equalsIgnoreCase("random")) {
            caseid = String.valueOf(World.generalSave.get().get("CASE"));
            consumerid = String.valueOf(World.generalSave.get().get("CONSUMER"));
        }

        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(2);
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(5);
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.caseid.getText().contains(caseid));
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.casename.getText(), casename);

        System.out.println(viewInboundCorrespondenceDetailsUIAPIPage.consumerid.getText() + " ---- " + consumerid);
        Assert.assertTrue(viewInboundCorrespondenceDetailsUIAPIPage.consumerid.getText().contains(consumerid));
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.consumername.getText(), consumername);
    }

    public void viewingconsumerlinktoibcorrespondence() {
        String consumerId = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        String consumername = "Consumer Profile";
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(2);
        viewInboundCorrespondenceDetailsUIAPIPage.refreshbutton.click();
        waitFor(5);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.casename.getText(), consumername);
        Assert.assertEquals(viewInboundCorrespondenceDetailsUIAPIPage.caseid.getText(), consumerId);
    }

    public void clickApiCreatedApplicationID() {
        WebElement applicationID = Driver.getDriver().findElement(By.xpath("//td[text()='" + apiApplicationId.get() + "']"));
        applicationID.click();
    }
}
