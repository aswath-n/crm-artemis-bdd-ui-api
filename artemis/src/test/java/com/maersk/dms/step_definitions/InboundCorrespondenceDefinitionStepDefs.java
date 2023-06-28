package com.maersk.dms.step_definitions;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.loggedInUserId;
import static com.maersk.crm.utilities.BrowserUtils.*;

public class InboundCorrespondenceDefinitionStepDefs extends BrowserUtils {

    private InboundCorrespondenceDefinitionListPage inboundCorrespondenceDefinitionListPage = new InboundCorrespondenceDefinitionListPage();
    private InboundCorrespondenceDefinitionPage inboundCorrespondenceDefinitionPage = new InboundCorrespondenceDefinitionPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private ThreadLocal<JsonObject> requestParams = ThreadLocal.withInitial(JsonObject::new);
    private final ThreadLocal<APIAutoUtilities> apiAutoUtilities = ThreadLocal.withInitial(APIAutoUtilities::new);
    private final ThreadLocal<List<Map<String, Object>>> inboundCorrespondenceDefinitionList = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<ZonedDateTime> nowUTC = ThreadLocal.withInitial(()->ZonedDateTime.now(ZoneOffset.UTC));
    private final ThreadLocal<LocalDateTime> localDateTime = ThreadLocal.withInitial(()->LocalDateTime.ofInstant(nowUTC.get().toInstant(), ZoneOffset.UTC));
    private final ThreadLocal<String> expectedDateAndHour = ThreadLocal.withInitial(() -> localDateTime.get().toString().substring(0, 13));

    public void fillRandomInboundCorrespondenceDefUI() {
        fillFieldsInboundCorrespondence();
    }

    public void fillRandomInboundCorrespondenceDefTaskRuleUI(Map<String, String> data) {

        for (String keyword : data.keySet()) {
            switch (keyword.toUpperCase()) {
                case "RANK":
                    String rank = data.get(keyword);
                    selectOptionFromMultiSelectDropDown(inboundCorrespondenceDefinitionPage.rank, rank);
                    break;
                case "TASK TYPE":
                    String tasktype = data.get(keyword);
                    selectFromMultiSelectDropDown(inboundCorrespondenceDefinitionPage.tasktype, tasktype);
                    break;
                case "CREATE ONLY ONE TASK PER SET":
                    String onlyonetaskperset = data.get(keyword);
                    String status = inboundCorrespondenceDefinitionPage.taskperset.getAttribute("value");
                    if (onlyonetaskperset.equalsIgnoreCase("true") && status.equalsIgnoreCase("false"))
                        inboundCorrespondenceDefinitionPage.taskperset.click();
                    if (onlyonetaskperset.equalsIgnoreCase("false") && status.equalsIgnoreCase("true"))
                        inboundCorrespondenceDefinitionPage.taskperset.click();
                    break;
                case "REQUIRED DATA ELEMENTS":
                    String[] reqdataelements = data.get(keyword).split(",");
                    for (String reqkey : reqdataelements) {
                        inboundCorrespondenceDefinitionPage.reqkeys.click();
                        BrowserUtils.waitFor(2);
                        inboundCorrespondenceDefinitionPage.selectreqkeysrecord(reqkey).click();
                        BrowserUtils.waitFor(2);
                    }
                    break;
                case "SUPPRESS IF SET HAS DOCUMENT OF THESE TYPES":
                    String[] documentset = data.get(keyword).split(",");
                    for (String documents : documentset) {
                        inboundCorrespondenceDefinitionPage.documentset.click();
                        BrowserUtils.waitFor(3);
                        inboundCorrespondenceDefinitionPage.selectdocumentset(documents).click();
                        BrowserUtils.waitFor(2);
                    }

                    break;
                default:
                    Assert.fail("Invalid field");
            }
        }

    }

    public void validateInboundCorrespondenceDefTaskRuleDispalyedinUI(Map<String, String> data) {

        for (String keyword : data.keySet()) {
            switch (keyword.toUpperCase()) {
                case "RANK":
                    String rank = data.get(keyword);
                    Assert.assertEquals(inboundCorrespondenceDefinitionPage.rank.getText(), rank, "RANK is not matched/displayed");
                    break;
                case "TASK TYPE":
                    String tasktype = data.get(keyword);
                    Assert.assertEquals(inboundCorrespondenceDefinitionPage.tasktype.getText(), tasktype, "TASKTYPE is not matched/displayed");
                    break;
                case "CREATE ONLY ONE TASK PER SET":
                    String onlyonetaskperset = data.get(keyword);
                    String status = inboundCorrespondenceDefinitionPage.taskperset.getAttribute("value");
                    Assert.assertEquals(onlyonetaskperset, status, "CREATE ONLY ONE TASK PER SET is not matched/displayed");
                    break;
                case "REQUIRED DATA ELEMENTS":
                    String[] reqdataelements = data.get(keyword).split(",");
                    for (String reqkey : reqdataelements) {
                        Assert.assertTrue(inboundCorrespondenceDefinitionPage.taskrule_values_display(reqkey).isDisplayed(), reqkey + "is not matched/displayed");
                    }
                    break;
                case "SUPPRESS IF SET HAS DOCUMENT OF THESE TYPES":
                    String[] documentset = data.get(keyword).split(",");
                    for (String docset : documentset) {
                        Assert.assertTrue(inboundCorrespondenceDefinitionPage.taskrule_values_display(docset).isDisplayed(), docset + "is not matched/displayed");
                    }
                    break;
                default:
                    Assert.fail("Invalid field");
            }
        }

    }

    public void fillFieldsInboundCorrespondence(String... removeFields) {
        staticWait(1000);
        List<String> fields = new ArrayList<>();
        fields.add("Name");
        fields.add("Description");
        fields.add("Barcode");

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
                case "Name":
                    fillTheFiled(inboundCorrespondenceDefinitionPage.name, World.getWorld().inboundCorrespondenceDefinition.get().name);
                    break;
                case "Description":
                    fillTheFiled(inboundCorrespondenceDefinitionPage.description, World.getWorld().inboundCorrespondenceDefinition.get().description);
                    break;
                case "Barcode":
                    fillTheFiled(inboundCorrespondenceDefinitionPage.barcode, World.getWorld().inboundCorrespondenceDefinition.get().barcode);
                    break;
            }

        }
    }

    public void skipFieldsInboundCorrespondence(String... removeFields) {
        if (removeFields.length > 0) {
            for (int index = 0; index < removeFields.length; index++) {
                switch (removeFields[index]) {
                    case "Barcode":
                        World.getWorld().inboundCorrespondenceDefinition.get().barcode = null;
                        break;
                    case "Name":
                        World.getWorld().inboundCorrespondenceDefinition.get().name = null;
                        break;
                    case "Description":
                        World.getWorld().inboundCorrespondenceDefinition.get().description = null;
                        break;
                }
            }
        }
    }

    /**
     * Fills out Inbound Correspondence Definition through UI, skips fields that are sent @Param
     */
    public void fillRandomInboundCorrespondenceDefUI(String... skipField) {
        createRandomInboundCorrespondenceDef();
        fillFieldsInboundCorrespondence(skipField);
        BrowserUtils.waitFor(2);
        waitForClickablility(inboundCorrespondenceDefinitionPage.saveButton, 6);
        new Actions(Driver.getDriver()).click(inboundCorrespondenceDefinitionPage.saveButton).perform();
    }

    /**
     * Creates Inbound Correspondence Definition through UI.
     * int definitions for how many definitions to create
     * Map key for which field to customize, value for value
     */
    public void createInbCorrDefUI(int definitions, Map<String, String> customFields) {
        for (int count = 0; count < definitions; count++) {
            createRandomInboundCorrespondenceDef();
            for (String s : customFields.keySet()) {
                changeValueInboundCorrespondence(s, customFields.get(s));
            }
            inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
            waitFor(3);
            fillRandomInboundCorrespondenceDefUI();
            saveDefinition();
            verifySuccessfullyCreatedPopUp();
        }
    }

    /**
     * Creates random values in Inbound Correspondence Definition in World Class
     */
    public void createRandomInboundCorrespondenceDef() {
        World.createNewWorld();
        World.getWorld().inboundCorrespondenceDefinition.get().createRandomValues();
    }

    /**
     * Creates Inbound Correspondence Definition through UI.
     * int definitions for how many definitions to create
     */
    public void createInbCorrDefUI(int definitions) {
        for (int count = 0; count < definitions; count++) {
            createRandomInboundCorrespondenceDef();
            inboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
            waitFor(3);
            fillRandomInboundCorrespondenceDefUI();
            saveDefinition();
            verifySuccessfullyCreatedPopUp();
        }
    }

    /**
     * Changes one field's value from random values created in Inbound Correspondence Definition instance in World Class
     */
    public void changeValueInboundCorrespondence(String field, String value) {
        switch (field) {
            case "Name":
                System.out.println(value + "/////");
                World.getWorld().inboundCorrespondenceDefinition.get().name = value;
                System.out.println(World.getWorld().inboundCorrespondenceDefinition.get().name + "////");
                break;
            case "Description":
                World.getWorld().inboundCorrespondenceDefinition.get().description = value;
                break;
            case "Barcode":
                World.getWorld().inboundCorrespondenceDefinition.get().barcode = value;
                break;
            case "Task Types":
                //Implemented
                break;
        }
    }

    public void clickFirstRecord() {
        inboundCorrespondenceDefinitionListPage.nameColumn().get(0).click();
        waitForVisibility(inboundCorrespondenceDefinitionPage.inboundCorrespondenceHeader, 7);
    }

    public void navigateBackToTheList() {
        inboundCorrespondenceDefinitionPage.backButton.click();
        if (uiAutoUitilities.get().quickIsDisplayed(inboundCorrespondenceDefinitionListPage.header)) {
            return;
        }
        waitForVisibility(inboundCorrespondenceDefinitionListPage.header, 2);
    }

    public void saveDefinition() {
        scrollToElement(inboundCorrespondenceDefinitionPage.saveButton);
        waitForClickablility(inboundCorrespondenceDefinitionPage.saveButton, 7);
        waitFor(2);
        inboundCorrespondenceDefinitionPage.saveButton.click();
        BrowserUtils.waitFor(1);
    }

    public void verifySuccessfullyCreatedPopUp() {
        inboundCorrespondenceDefinitionPage.savePopUp = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'Inbound Correspondence definition successfully created.')]"));
        Assert.assertTrue("Inbound Correspondence definition successfully created.".contains(inboundCorrespondenceDefinitionPage.savePopUp.getText()));
    }

    public void assertLength(int length, String columnLabel) {
        int index = uiAutoUitilities.get().findInListIB(true, World.getWorld().inboundCorrespondenceDefinition.get().name);
        switch (columnLabel.toUpperCase()) {
            case "NAME":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.nameColumn().get(index).getText().length(), length);
                break;
            case "BARCODE":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.barcodeColumn().get(index).getText().length(), length);
                break;
            case "TASK TYPES":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.taskTypes().get(index).getText().length(), length);
                break;
        }
    }

    public void storeInboundCorrespondenceDefinitionIntoTheList(String ID) {
        inboundCorrespondenceDefinitionList.set(apiAutoUtilities.get().getDefinitionListByProjectID(ID));
    }

    public void assertInboundCorrespondenceDefinitionByField(String field) {
        int index = -1;
        for (int i = inboundCorrespondenceDefinitionList.get().size() - 1; i >= 0; i--) {
            if (inboundCorrespondenceDefinitionList.get().get(i).get("name").toString().equalsIgnoreCase(World.getWorld().inboundCorrespondenceDefinition.get().name)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Assert.fail("Definition not found in the inbound correspondence definition list");
        }
        switch (field) {
            case "createdBy":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("createdBy").toString(), "2662", field + " did not match for the inbound correspondence definition");
                break;
            case "createdDatetime":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("createdDatetime").toString().substring(0, 13), expectedDateAndHour.get(), field + " did not match for the inbound correspondence definition");
                break;
            case "updatedBy":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("updatedBy").toString(), "2662", field + " did not match for the inbound correspondence definition");
                break;
            case "updatedDatetime":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("updatedDatetime").toString().substring(0, 13), expectedDateAndHour.get(), field + " did not match for the inbound correspondence definition");
                break;
            case "name":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("name").toString(), World.getWorld().inboundCorrespondenceDefinition.get().name, field + " did not match for the inbound correspondence definition");
                break;
            case "barcode":
                Assert.assertTrue(inboundCorrespondenceDefinitionList.get().get(index).get("barcode").toString().equalsIgnoreCase(World.getWorld().inboundCorrespondenceDefinition.get().barcode), field + " did not match for the inbound correspondence definition");
                break;
            case "description":
                Assert.assertEquals(inboundCorrespondenceDefinitionList.get().get(index).get("description").toString(), World.getWorld().inboundCorrespondenceDefinition.get().description, field + " did not match for the inbound correspondence definition");
                break;
            default:
                Assert.fail(field + "not found within the response body");
        }
    }

    public void assertInboundCorrespondenceDefinitionByField(String field, String expectedValue) {
        int index = uiAutoUitilities.get().findInListIB(true, World.getWorld().inboundCorrespondenceDefinition.get().name);
        switch (field.toUpperCase()) {
            case "NAME":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.nameColumn().get(index).getText(), expectedValue);
                break;
            case "BARCODE":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.barcodeColumn().get(index).getText(), expectedValue);
                break;
            case "TASK TYPES":
                Assert.assertEquals(inboundCorrespondenceDefinitionListPage.taskTypes().get(index).getText(), expectedValue);
                break;
        }
    }

    public void assertInboundCorrespondenceDefinitionByFieldRetrievedByPost(String field) { // Will be updated after Task Rule Story Auto Merged
        switch (field) {
            case "createdBy":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.createdBy").toString(), "144", field + " did not match for the inbound correspondence definition");
                break;
            case "createdDatetime":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.createdDatetime").toString().substring(0, 13), expectedDateAndHour.get(), field + " did not match for the inbound correspondence definition");
                break;
            case "updatedBy":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.updatedBy").toString(), "144", field + " did not match for the inbound correspondence definition");
                break;
            case "updatedDatetime":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.updatedDatetime").toString().substring(0, 13), expectedDateAndHour.get(), field + " did not match for the inbound correspondence definition");
                break;
            case "name":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.name").toString(), World.getWorld().inboundCorrespondenceDefinition.get().name, field + " did not match for the inbound correspondence definition");
                break;
            case "barcode":
                String actualBarcode = World.getWorld().inboundCorrespondenceDefinition.get().barcode;
                if (actualBarcode.length() == 3) {
                    actualBarcode = "0" + actualBarcode;
                } else if (actualBarcode.length() == 2) {
                    actualBarcode = "00" + actualBarcode;
                }
                String expectedBarcode = World.getWorld().jsonPath.get().get("inboundCorrespondence.barcode").toString();
                if (expectedBarcode.length() == 3) {
                    expectedBarcode = "0" + expectedBarcode;
                } else if (expectedBarcode.length() == 2) {
                    expectedBarcode = "00" + expectedBarcode;
                }
                Assert.assertEquals(actualBarcode, expectedBarcode, field + " did not match for the inbound correspondence definition");
                break;
            case "description":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.description").toString(), World.getWorld().inboundCorrespondenceDefinition.get().description, field + " did not match for the inbound correspondence definition");
                break;
            case "inboundCorrespondenceTaskRuleId":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].inboundCorrespondenceTaskRuleId").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("inboundCorrespondenceTaskRuleId").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "inboundCorrespondenceDefinitionId":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].inboundCorrespondenceDefinitionId").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("inboundCorrespondenceDefinitionId").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "rank":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].rank").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("rank").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "taskTypeId":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].taskTypeId").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("taskTypeId").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "requiredDataElements":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].requiredDataElements").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("requiredDataElements").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "createdByTaskRule":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].createdBy").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("createdBy").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "createdOnTaskRule":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].createdDatetime").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("createdDatetime").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "updatedByTaskRule":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].updatedBy").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("updatedBy").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "updatedOnTaskRule":
                Assert.assertEquals(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].updatedDatetime").toString(), World.getWorld().inboundCorrespondenceTaskRule.get().get("updatedDatetime").toString(), field + " did not match for the inbound correspondence definition");
                break;
            case "linkSameSetTaskFlag":
                Assert.assertTrue(World.getWorld().jsonPath.get().get("inboundCorrespondence.inboundCorrespondenceTaskRule[0].linkSameSetTaskFlag") == World.getWorld().inboundCorrespondenceTaskRule.get().get("linkSameSetTaskFlag"), field + " did not match for the inbound correspondence definition");
                break;
            default:
                Assert.fail(field + "not found within the response body");
        }
    }

    public void assertBarcode() {
        int index = uiAutoUitilities.get().findInListIB(true, World.getWorld().inboundCorrespondenceDefinition.get().name);
        StringBuilder expectedBarcode = new StringBuilder(World.getWorld().inboundCorrespondenceDefinition.get().barcode);
        for (int i = 0; i < (4 - World.getWorld().inboundCorrespondenceDefinition.get().barcode.length()); i++) {
            expectedBarcode.insert(0, "0");
        }
        Assert.assertEquals(String.valueOf(inboundCorrespondenceDefinitionListPage.barcodeColumn().get(index).getText()), String.valueOf(expectedBarcode));
    }

    public void assertDescriptionStringCount(int characters) {
        Assert.assertEquals(characters, inboundCorrespondenceDefinitionPage.description.getAttribute("value").length());
    }

    public void assertNameStringCount(int characters) {
        Assert.assertEquals(characters, inboundCorrespondenceDefinitionPage.name.getAttribute("value").length());
    }


    public void goToNextPage(int pagenumber) {
        inboundCorrespondenceDefinitionListPage.selectpage(pagenumber).click();
        waitFor(5);
    }


    public boolean viewIBCorrespondneceDef(String IBCorrdeftype) {
        BrowserUtils.waitFor(8);
        if (IBCorrdeftype.equalsIgnoreCase("previouslycreated"))
            IBCorrdeftype = World.getWorld().inboundCorrespondenceDefinition.get().name;

        boolean status = false;
        int rowsize = inboundCorrespondenceDefinitionListPage.nameColumn().size();

        if (rowsize != 0) {
            for (int i = 0; i < rowsize; i++) {
                if (IBCorrdeftype.equalsIgnoreCase("first")) {
                    inboundCorrespondenceDefinitionListPage.nameColumn().get(i).click();
                    status = true;
                    break;
                } else if (IBCorrdeftype.equals(inboundCorrespondenceDefinitionListPage.nameColumn().get(i).getText())) {
                    inboundCorrespondenceDefinitionListPage.nameColumn().get(i).click();
                    status = true;
                    break;
                }
            }
        }
        return status;

    }


    public void assertElementIsNotDisplayed(WebElement element) {
        Assert.assertTrue(IsElementDisplayed(element), "Element is present on Inbound Correspondence Definition Page");
    }
}
