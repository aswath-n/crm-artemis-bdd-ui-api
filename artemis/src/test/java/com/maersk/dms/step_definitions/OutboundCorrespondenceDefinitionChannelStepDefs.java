package com.maersk.dms.step_definitions;

import com.github.javafaker.Faker;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionChannelPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionListPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionPage;
import com.maersk.dms.steps.OutboundCorrespondenceDefinitionChannelSteps;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.maersk.crm.utilities.BrowserUtils.*;

public class OutboundCorrespondenceDefinitionChannelStepDefs extends BrowserUtils{
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    private final APIAutoUtilities apiAutoUtilities =APIAutoUtilities.API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities();
    private final ThreadLocal<OutboundCorrespondenceDefinitionStepDefs> outboundCorrespondenceDefinitionStepDefs = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionStepDefs::new);
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private OutboundCorrespondenceDefinitionChannelPage outboundCorrespondenceDefinitionChannelPage = new OutboundCorrespondenceDefinitionChannelPage();
    //    private OutboundCorrespondenceDefinitionChannelSteps outboundCorrespondenceDefinitionChannelSteps = new OutboundCorrespondenceDefinitionChannelSteps();
    private final ThreadLocal<Integer> page = ThreadLocal.withInitial(()->1);
    private final ThreadLocal<String> oneChannelIdPrefix = ThreadLocal.withInitial(()->"001Chl");
    private final ThreadLocal<String> oneChannelId = ThreadLocal.withInitial(()->"001Ch10000");


    /**
     * Finds first Outbound Correspondence Definition with no channels
     * or creates one and selects it
     */
    public void selectDefLackingChannel() {
//        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionListPage.noRecordsMessage) && outboundCorrespondenceDefinitionListPage.idColumn().size() < 1) {
//            outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(1);
//        }
//        if (!findFirstInListNoChannel()) {
//            outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(1);
//            waitFor(2);
//        }
        outboundCorrespondenceDefinitionStepDefs.get().createOutCorrDefUI(1);
        waitFor(3);

    }

    public boolean findFirstInListNoChannel() {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        scrollUpRobot();
        UIAutoUitilities.page.set(1);
        do {
            for (int index = 0; index < list.size(); index++) {
                if (index < list.size() / 2) {
                    scrollUpRobot();//scroll up for top half of list
                } else {
                    scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);//scroll down for bottom half of list
                }
                waitForClickablility(list.get(index), 7);
                list.get(index).click();//select Correspondence Def
                waitForVisibility(outboundCorrespondenceDefinitionListPage.bottomHeader, 7);
                scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                if (checkIfNoChannels()) {//checks if has channels
                    found = true;
                    scrollToElement(outboundCorrespondenceDefinitionPage.Id);
                    OutboundCorrespondenceDefinitionChannelSteps.save.get().put("Id", outboundCorrespondenceDefinitionPage.Id.getAttribute("value"));
                    return true;
                } else {
                    navigateAwayDiscardChanges();
                    if (UIAutoUitilities.page.get() != 1) {
                        scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                        outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(UIAutoUitilities.page.get())).click();
                        waitForClickablility(outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(1)), 7);
                    }
                }
                waitFor(2);
                list = outboundCorrespondenceDefinitionListPage.idColumn();
            }//end of page list of correspondence definitions
            if (!uiAutoUitilities.get().goToNextPage()) {
                return false;
            }
            waitFor(2);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        } while (!found);
//        waitFor(1);
//        list = outboundCorrespondenceDefinitionListPage.idColumn();
        return false;
    }

    public boolean checkIfNoChannels() {
        waitFor(2);
        return countChannels() == 0 && uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionPage.noChannels);
    }

    private int countChannels() {
        int size = 0;
        try {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
            size = outboundCorrespondenceDefinitionChannelPage.getChannels().size();
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        } catch (NoSuchElementException exception) {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            return 0;
        }
        return size;
    }

    public void createRandomChannelData() {
        World.createNewWorld();
        World.getWorld().outboundCorrespondenceDefinitionChannel.get().createRandomValuesChannel();
    }

    public void addChannelUI() {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        scrollUpRobot();
        waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        fillOutChannelUI();
        saveAndVerifyPopUp();
    }

    public void addChannelUI(boolean noSave) {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        scrollUpRobot();
        waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        fillOutChannelUI();
        if (noSave) {
            return;
        }
        saveAndVerifyPopUp();
    }

    public void addChannelUI(boolean noSave, String... skipFields) {
        outboundCorrespondenceDefinitionPage.addChannel.click();
        scrollUpRobot();
        waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        fillOutChannelUI(skipFields);
        if (noSave) {
            return;
        }
        saveAndVerifyPopUp();
    }

    public void addChannelUI(String... skipFields) {
        waitFor(2);
        outboundCorrespondenceDefinitionPage.addChannel.click();
        waitFor(2);
        scrollUpRobot();
        waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
        fillOutChannelUI(skipFields);
        saveAndVerifyPopUp();
    }

    public void fillOutChannelUI() {
        Map<String, String> values = new LinkedHashMap<>();
        waitFor(5);
        values.put("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
        values.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
        values.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        values.put("endReason", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endReason);
        values.put("senderEmailId", World.getWorld().outboundCorrespondenceDefinitionChannel.get().senderEmailId);
        values.put("sendImmediately", String.valueOf(Boolean.valueOf(World.getWorld().outboundCorrespondenceDefinitionChannel.get().sendImmediately)));
        values.put("mandatory", String.valueOf(World.getWorld().outboundCorrespondenceDefinitionChannel.get().mandatory));
        if (!values.get("channelType").equalsIgnoreCase("Mail")) {
            values.put("notificationPurposeDropdown", World.getWorld().outboundCorrespondenceDefinitionChannel.get().notificationPurpose);
        }
        for (Map.Entry<String, String> element : values.entrySet()) {
            switch (element.getKey()) {
                case "notificationPurposeDropdown":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.notificationPurposeDropdown, element.getValue());
                    waitFor(2);
                    break;
                case "channelType":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.channelType, element.getValue());
                    waitFor(2);
                    break;
                case "startDate":
                    waitFor(5);
                    outboundCorrespondenceDefinitionChannelPage.startDate.sendKeys(element.getValue());
                    break;
                case "endDate":
                    outboundCorrespondenceDefinitionChannelPage.endDate.sendKeys(element.getValue());
                    break;
                case "endReason":
                    outboundCorrespondenceDefinitionChannelPage.endReason.sendKeys(element.getValue());
                    break;
                case "senderEmailId":
                    if ("Email".equalsIgnoreCase(World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType)) {
                        outboundCorrespondenceDefinitionChannelPage.senderEmailId.sendKeys(element.getValue());
                        break;
                    }
                case "sendImmediately":
                    if (World.getWorld().outboundCorrespondenceDefinitionChannel.get().sendImmediately) {
                        outboundCorrespondenceDefinitionChannelPage.sendImmediately.click();
                    }
                    break;
                case "mandatory":
                    if (World.getWorld().outboundCorrespondenceDefinitionChannel.get().mandatory) {
                        outboundCorrespondenceDefinitionChannelPage.mandatory.click();
                    }
                    break;
                default:
            }
        }
    }

    public void fillOutChannelUI(String... skipField) {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("channelType", World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType);
        values.put("startDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate);
        values.put("endDate", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate);
        values.put("endReason", World.getWorld().outboundCorrespondenceDefinitionChannel.get().endReason);
        values.put("senderEmailId", World.getWorld().outboundCorrespondenceDefinitionChannel.get().senderEmailId);
        values.put("sendImmediately", String.valueOf(Boolean.valueOf(World.getWorld().outboundCorrespondenceDefinitionChannel.get().sendImmediately)));
        values.put("mandatory", String.valueOf(World.getWorld().outboundCorrespondenceDefinitionChannel.get().mandatory));
        if (!values.get("channelType").equalsIgnoreCase("Mail")) {
            values.put("notificationPurposeDropdown", World.getWorld().outboundCorrespondenceDefinitionChannel.get().notificationPurpose);
        }
        for (String skip : skipField) {
            values.remove(skip);
        }
        for (Map.Entry<String, String> element : values.entrySet()) {
            switch (element.getKey()) {
                case "notificationPurposeDropdown":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.notificationPurposeDropdown, element.getValue());
                    waitFor(2);
                    break;
                case "channelType":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.channelType, element.getValue());
                    waitFor(2);
                    break;
                case "startDate":
                    waitFor(2);
                    outboundCorrespondenceDefinitionChannelPage.startDate.sendKeys(element.getValue());
                    break;
                case "endDate":
                    waitFor(2);
                    outboundCorrespondenceDefinitionChannelPage.endDate.sendKeys(element.getValue());
                    break;
                case "endReason":
                    outboundCorrespondenceDefinitionChannelPage.endReason.sendKeys(element.getValue());
                    break;
                case "senderEmailId":
                    if ("Email".equalsIgnoreCase(World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType)) {
                        outboundCorrespondenceDefinitionChannelPage.senderEmailId.sendKeys(element.getValue());
                        break;
                    }
                    break;
                case "sendImmediately":
                    if (World.getWorld().outboundCorrespondenceDefinitionChannel.get().sendImmediately) {
                        outboundCorrespondenceDefinitionChannelPage.sendImmediately.click();
                    }
                    break;
                case "mandatory":
                    if (World.getWorld().outboundCorrespondenceDefinitionChannel.get().mandatory) {
                        outboundCorrespondenceDefinitionChannelPage.mandatory.click();
                    }
                    break;
                default:
            }
        }
    }

    public void fillOutChannelUI(Map<String, String> values) {
        for (Map.Entry<String, String> element : values.entrySet()) {
            switch (element.getKey()) {
                case "channelType":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.channelType, element.getValue());
                    waitFor(2);
                    break;
                case "notificationPurposeDropdown":
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceDefinitionChannelPage.notificationPurposeDropdown, element.getValue());
                    waitFor(2);
                    break;
                case "startDate":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.startDate, "");
//                        uiAutoUitilities.get().clearStartDate();
                        break;
                    } else if (element.getValue().equalsIgnoreCase("currentDay")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.startDate, getCurrentDate());
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.startDate, "");
                    waitFor(1);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.startDate, element.getValue());
                    break;
                case "endDate":
                    if (element.getValue().equalsIgnoreCase("null")) {
//                        uiAutoUitilities.get().clearEndDate();
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.endDate, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.endDate, "");
                    waitFor(1);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.endDate, element.getValue());
                    break;
                case "endReason":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.endReason, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.endReason, element.getValue());
                    break;
                case "senderEmailId":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.senderEmailId, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceDefinitionChannelPage.senderEmailId, element.getValue());
                    break;
                case "sendImmediately":
                    if (element.getValue().equalsIgnoreCase("false") && outboundCorrespondenceDefinitionChannelPage.sendImmediately.isSelected()) {
                        outboundCorrespondenceDefinitionChannelPage.sendImmediately.click();
                    } else if (element.getValue().equalsIgnoreCase("true") && (!outboundCorrespondenceDefinitionChannelPage.sendImmediately.isSelected())) {
                        outboundCorrespondenceDefinitionChannelPage.sendImmediately.click();
                    }
                    break;
                case "mandatory":
                    if (element.getValue().equalsIgnoreCase("false") && outboundCorrespondenceDefinitionChannelPage.mandatory.isSelected()) {
                        outboundCorrespondenceDefinitionChannelPage.mandatory.click();
                    } else if (element.getValue().equalsIgnoreCase("true") && (!outboundCorrespondenceDefinitionChannelPage.mandatory.isSelected())) {
                        outboundCorrespondenceDefinitionChannelPage.mandatory.click();
                    }
                    break;
                default:
            }
        }
    }

    public void saveAndVerifyPopUp() {
        scrollToElement(outboundCorrespondenceDefinitionChannelPage.saveButton);
        waitFor(1);
        waitForClickablility(outboundCorrespondenceDefinitionChannelPage.saveButton, 7);
        outboundCorrespondenceDefinitionChannelPage.saveButton.click();
        waitFor(1);
        //pop up
    }

    public void verifySuccessfulChannelSaved() {
        Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.getChannels().size() > 0);
    }

    public void navigateAwayDiscardChanges() {
        outboundCorrespondenceDefinitionPage.backButton.click();
        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionListPage.header)) {
            return;
        }
        waitForVisibility(outboundCorrespondenceDefinitionListPage.discardPopUp, 7);
        outboundCorrespondenceDefinitionListPage.discardPopUp.click();
        waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }

    public void changeValueChannel(String field, String value) {
        switch (field) {
            case "channelType":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().channelType = value;
                break;
            case "startDate":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = value;
                break;
            case "endDate":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = value;
                break;
            case "endReason":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().endReason = value;
                break;
            case "senderEmailId":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().senderEmailId = value;
                break;
            case "sendImmediately":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().sendImmediately = Boolean.valueOf(value);
                break;
            case "mandatory":
                World.getWorld().outboundCorrespondenceDefinitionChannel.get().mandatory = Boolean.valueOf(value);
                break;
        }
    }

    public void changeDate(String field, String value) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Faker faker = new Faker();
        if ("startDate".equalsIgnoreCase(field)) {
            switch (value) {
                case "Current_SysDatePlusOneMonth":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().plusMonths(1));
                    break;
                case "Current_SysDateMinusOneMonth":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().minusMonths(1));
                    break;
                case "Current_SysDateMinusOneDay":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().minusDays(1));
                    break;
                case "Current_SysDatePlusOneWeek":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().plusWeeks(1));
                    break;
                case "Current_SysDatePlusOneDay":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().plusDays(1));
                    break;
                case "Current_SysDatePlusTwoDays":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now().plusDays(2));
                    break;
                case "Current_SysDate":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = dtf.format(LocalDateTime.now());
                    break;
                case "null":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().startDate = "null";
                    break;
            }
        } else {
            switch (value) {
                case "Current_SysDatePlusOneMonth":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now().plusMonths(1));
                    ;
                    break;
                case "Current_SysDateMinusOneMonth":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now().minusMonths(1));
                    break;
                case "Current_SysDatePlusOneWeek":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now().plusWeeks(1));
                    break;
                case "Current_SysDatePlusOneDay":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now().plusDays(1));
                    break;
                case "Current_SysDatePlusTwoDays":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now().plusDays(2));
                    break;
                case "Current_SysDate":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = dtf.format(LocalDateTime.now());
                    break;
                case "null":
                    World.getWorld().outboundCorrespondenceDefinitionChannel.get().endDate = "null";
                    break;
            }
        }
    }

    public void verifyFieldEnabled(String field) {
        switch (field) {
            case "channelType":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.channelType.isEnabled());
                break;
            case "startDate":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.startDate.isEnabled());
                break;
            case "endDate":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.endDate.isEnabled());
                break;
            case "endReason":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.endReason.isEnabled());
                break;
            case "senderEmailId":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.senderEmailId.isEnabled());
                break;
            case "sendImmediately":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.sendImmediately.isEnabled());
                break;
            case "mandatory":
                Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.mandatory.isEnabled());
                break;
        }
    }

    public void assertChannelFailMessage(String message) {
        waitForVisibility(outboundCorrespondenceDefinitionChannelPage.getFailMessage(message), 3);
        Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.getFailMessage(message).isDisplayed());
    }

    /**
     * select Outbound Correspondence Def
     * and verify only 1 channel and no end date
     */
    public void selectTDOneChannel() {
        if (!findOutboundCorrInList(oneChannelIdPrefix.get())) {
            createTestDataOutboundCorrOneChannel();
            createTestDataChannelNoEndDate();
            findOutboundCorrInList(oneChannelId.get());
        }
    }

    private boolean findOutboundCorrInList(String oneChannelId) {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        do {
            for (int index = 0; index < list.size(); index++) {
                if (list.get(index).getText().contains(oneChannelIdPrefix.get())) {
                    scrollToElement(list.get(index));
                    list.get(index).click();
                    found = true;
                    return true;
                }
                scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                list = outboundCorrespondenceDefinitionListPage.idColumn();
            }
            if (!uiAutoUitilities.get().goToNextPage()) {
                found = true;
            }
            waitFor(1);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        } while (!found);
        return false;
    }

    private void createTestDataChannelNoEndDate() {
        int checkChannels = checkTestDataChannelNoEndDate();
        if (checkChannels == 1) {
            return;
        } else if (checkChannels > 1) {

        }
        createRandomChannelData();
        fillOutChannelUI("endDate", "endReason");
    }

    private void createTestDataOutboundCorrOneChannel() {
        outboundCorrespondenceDefinitionStepDefs.get().createRandomOutboundCorrespondenceDef();
        outboundCorrespondenceDefinitionStepDefs.get().changeValueOutboundCorrespondence("Id", oneChannelIdPrefix.get() + RandomStringUtils.random(4, true, true));
        oneChannelId.set(World.getWorld().outboundCorrespondenceDefinition.get().ID);
        outboundCorrespondenceDefinitionListPage.addCorrespondenceDefinition.click();
        outboundCorrespondenceDefinitionStepDefs.get().fillFieldsOutboundCorrespondence();
        outboundCorrespondenceDefinitionStepDefs.get().saveAndVerifyPopUp();
        navigateAwayDiscardChanges();
    }

    private boolean checkTestDataOutboundCorrOneChannel() {
        return false;
    }

    public int checkTestDataChannelNoEndDate() {
        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionPage.noChannels)) {
            createRandomChannelData();
            outboundCorrespondenceDefinitionPage.addChannel.click();
            waitFor(1);
            scrollToElement(outboundCorrespondenceDefinitionChannelPage.channelType);
            fillOutChannelUI("endDate", "endReason");
            saveAndVerifyPopUp();
            waitFor(2);
        }
        return outboundCorrespondenceDefinitionChannelPage.getChannels().size();
    }

    public void verifySaveStamp() {
       scrollToElement(outboundCorrespondenceDefinitionPage.updatedByUser);
        Assert.assertTrue(outboundCorrespondenceDefinitionPage.updatedByUser.getText().contains("service"));
    }

    public void verifyChannelResponse(Response response) {
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(!response.body().jsonPath().get("channelDefinitionResponse.createdOn").toString().isEmpty());
        Assert.assertTrue(!response.body().jsonPath().get("channelDefinitionResponse.channelDefinitionId").toString().isEmpty());
    }

    public void moveFromAllFields(int fields) {
        List<WebElement> checkBoxes = outboundCorrespondenceDefinitionChannelPage.getAllFields();
        for (int count = 0; count < checkBoxes.size(); count++) {
            checkBoxes.get(count).click();
        }
        outboundCorrespondenceDefinitionChannelPage.chevronRight.click();
        waitFor(1);
    }

    public void selectWithChannelType(String channel) {
        boolean found = true;
        int page = 1;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        List<WebElement> channeltypes;
        do {
            for (int index = 0; index < list.size(); index++) {
                list.get(index).click();
                waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
                if (outboundCorrespondenceDefinitionPage.noChannels.isDisplayed() && outboundCorrespondenceDefinitionChannelPage.getChannels().size() == 0) {
                    navigateAwayDiscardChanges();
                } else if (hasChannelType(channel) == -1) {

                }
            }

        } while (found);

    }

    public int hasChannelType(String type) {
        List<WebElement> channeltypes = outboundCorrespondenceDefinitionChannelPage.getChannelType();
        for (int index = 0; index < channeltypes.size(); index++) {
            if (channeltypes.get(index).getText().equalsIgnoreCase(type)) {
                return index;
            }
        }
        return -1;
    }

    public boolean findCorrAtLeastOneChannel() {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        scrollUpRobot();
        UIAutoUitilities.page.set(1);
        do {
            for (int index = 0; index < list.size(); index++) {
                if (index < list.size() / 2) {
                    scrollUpRobot();//scroll up for top half of list
                } else {
                    scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);//scroll down for bottom half of list
                }
                waitForClickablility(list.get(index), 7);
                list.get(index).click();//select Correspondence Def
                waitForVisibility(outboundCorrespondenceDefinitionListPage.bottomHeader, 7);
                scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                if (!checkIfNoChannels()) {//checks if has channels
                    found = true;
                    outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
                    waitForVisibility(outboundCorrespondenceDefinitionChannelPage.channelHeader, 7);
                    return true;
                } else {
                    navigateAwayDiscardChanges();
                    if (UIAutoUitilities.page.get() != 1) {
                        scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                        outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(UIAutoUitilities.page.get())).click();
                        waitForClickablility(outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(1)), 7);
                    }
                }
                waitFor(2);
                list = outboundCorrespondenceDefinitionListPage.idColumn();
            }//end of page list of correspondence definitions
            if (!uiAutoUitilities.get().goToNextPage()) {
                return false;
            }
            waitFor(2);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        } while (!found);
//        waitFor(1);
//        list = outboundCorrespondenceDefinitionListPage.idColumn();
        return false;
    }

    public boolean findCorrAtLeastOneChannel(String type) {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        scrollUpRobot();
        UIAutoUitilities.page.set(1);
        do {
            for (int index = 0; index < list.size(); index++) {
                if (index < list.size() / 2) {
                    scrollUpRobot();//scroll up for top half of list
                } else {
                    scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);//scroll down for bottom half of list
                }
                waitForClickablility(list.get(index), 7);
                list.get(index).click();//select Correspondence Def
                waitForVisibility(outboundCorrespondenceDefinitionListPage.bottomHeader, 7);
                scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                if (hasChannelType(type) > 0) {//checks if has channels
                    found = true;
                    scrollToElement(outboundCorrespondenceDefinitionPage.Id);
                    OutboundCorrespondenceDefinitionChannelSteps.save.get().put("Id", outboundCorrespondenceDefinitionPage.Id.getAttribute("value"));
                    return true;
                } else {
                    navigateAwayDiscardChanges();
                    if (UIAutoUitilities.page.get() != 1) {
                        scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
                        outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(UIAutoUitilities.page.get())).click();
                        waitForClickablility(outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(1)), 7);
                    }
                }
                waitFor(2);
                list = outboundCorrespondenceDefinitionListPage.idColumn();
            }//end of page list of correspondence definitions
            if (!uiAutoUitilities.get().goToNextPage()) {
                return false;
            }
            waitFor(2);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        } while (!found);
//        waitFor(1);
//        list = outboundCorrespondenceDefinitionListPage.idColumn();
        return false;
    }

    public boolean hasChannel() {
        try {
            waitFor(3);
            outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
            waitFor(10);
            return true;
        } catch (Exception exception) {
            waitFor(10);
            navigateAwayDiscardChanges();
            return false;
        }
    }

    public boolean hasChannel(boolean noclick) {
        try {
            waitFor(3);
            if (noclick) {
                return outboundCorrespondenceDefinitionChannelPage.getChannels().size() > 0;
            }
            outboundCorrespondenceDefinitionChannelPage.getChannels().get(0).click();
            waitFor(10);
            return true;
        } catch (Exception exception) {
            waitFor(10);
            navigateAwayDiscardChanges();
            return false;
        }
    }

    public boolean hasChannel(String type) {
        List<WebElement> list = new ArrayList<>();
        try {
            waitFor(3);
            list = outboundCorrespondenceDefinitionChannelPage.getChannelType();
            for (WebElement element : list) {
                if (element.getText().equalsIgnoreCase("Email")) {
                    element.click();
                    waitFor(1);
                    waitFor(10);
                    return true;
                }
            }
        } catch (Exception exception) {
            waitFor(10);
            navigateAwayDiscardChanges();
            if (UIAutoUitilities.page.get() != 1) {
                outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(UIAutoUitilities.page.get())).click();
            }
            return false;
        }
        navigateAwayDiscardChanges();
        return false;
    }

    public void verifyUpdated() {
        Assert.assertFalse(outboundCorrespondenceDefinitionChannelPage.updatedBy.getText().isEmpty());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Assert.assertTrue(outboundCorrespondenceDefinitionChannelPage.updatedOn.getText().equalsIgnoreCase(dtf.format(now)));
    }

    public void clickOnFirstChannel() {
        outboundCorrespondenceDefinitionChannelPage.listOfChannels.get(0).click();
    }

    public void verifyCreatedByAndCreatedOn() {
        String expected = outboundCorrespondenceDefinitionChannelPage.headerID.getText().replace("ID ", "");
        String actual = outboundCorrespondenceDefinitionChannelPage.createdByValue.getText();
        Assert.assertEquals(actual, expected, "Created by verification failed");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        Assert.assertEquals(outboundCorrespondenceDefinitionChannelPage.createdOnValue.getText(), dtf.format(now), "Created on verification failed");
    }

    public void verifyChannelPageUIValues(Map<String, String> channelData) {
        for (String channelPageValue : channelData.keySet()) {
            switch (channelPageValue) {
                case "notificationPurposeIsEnabled":
                    String classAttribValue = outboundCorrespondenceDefinitionChannelPage.getDropdownSelectedValue("notificationPurpose").getAttribute("class");
                    if (channelData.get(channelPageValue).equalsIgnoreCase("enabled")) {
                        Assert.assertFalse(classAttribValue.contains(channelData.get(channelPageValue)), "classAttribValue - " + classAttribValue);

                    } else {
                        Assert.assertTrue(classAttribValue.contains(channelData.get(channelPageValue)), "classAttribValue - " + classAttribValue);
                    }
                    break;
                case "includeOnWeb":
                    String includeOnWebAttribValue = outboundCorrespondenceDefinitionChannelPage.getCheckBoxSelectedValue("includeOnWeb").getAttribute("value");
                    Assert.assertTrue(includeOnWebAttribValue.contains(channelData.get(channelPageValue)), "includeOnWebAttribValue - " + includeOnWebAttribValue);
                    break;
                default:
                    Assert.assertEquals(outboundCorrespondenceDefinitionChannelPage.getDropdownSelectedValue(channelPageValue).getText(), channelData.get(channelPageValue));
            }
        }
    }
}
