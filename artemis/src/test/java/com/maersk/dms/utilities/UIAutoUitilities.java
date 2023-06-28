package com.maersk.dms.utilities;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UIAutoUitilities extends BrowserUtils {
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();
    private InboundCorrespondenceDefinitionListPage inboundCorrespondenceDefinitionListPage = new InboundCorrespondenceDefinitionListPage();
    private CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private OutboundCorrespondenceDefinitionChannelPage outboundCorrespondenceDefinitionChannelPage = new OutboundCorrespondenceDefinitionChannelPage();
    public static final ThreadLocal<Integer> page = ThreadLocal.withInitial(()->1);
    private CaseAndContactDetailsPage dmsCaseContactDetPage = new CaseAndContactDetailsPage();
    Actions action = new Actions(Driver.getDriver());

    public static void gotoPage(List<WebElement> list) {
        if (list.size() < 2) return;
    }

    public static void gotoPage(List<WebElement> list, int pageNumber) {
        if (list.size() < 2) return;
    }

    public static Boolean hasText(String text) {
        if (text.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void selectFirstOutboundCorrespondence() {
        waitForVisibility(caseAndContactDetailsPage.correspondenceIDs.get(0), 7);
        hover(caseAndContactDetailsPage.correspondenceIDs.get(0));
        caseAndContactDetailsPage.correspondenceIDs.get(0).click();
    }

    public boolean goToNextPage() {
        scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
        if (quickIsDisplayed(outboundCorrespondenceDefinitionListPage.arrowForward)) {
            scrollToElement(outboundCorrespondenceDefinitionListPage.bottomHeader);
            page.set(page.get()+1);
            outboundCorrespondenceDefinitionListPage.pageNumber(String.valueOf(page.get())).click();
            return true;
        } else {
            return false;
        }
    }

    public void findInList(String id) {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        while (!found) {
            for (WebElement temp : outboundCorrespondenceDefinitionListPage.idColumn()) {
                if (temp.getText().trim().equalsIgnoreCase(id)) {
                    found = true;
                    page.set(1);
                    scrollToElement(temp);
                    temp.click();
                    waitFor(2);
//                    waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
                    return;
                }
            }
            goToNextPage();
            waitFor(1);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        }
    }

    public void findInList(String id, boolean noClick) {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        while (!found) {
            for (WebElement temp : outboundCorrespondenceDefinitionListPage.idColumn()) {
                if (temp.getText().trim().equalsIgnoreCase(id)) {
                    found = true;
                    page.set(1);
                    if (noClick) break;
                    temp.click();
                    waitForVisibility(outboundCorrespondenceDefinitionPage.outboundCorrespondenceHeader, 7);
                    break;
                }
            }
            if (!found) goToNextPage();
            waitFor(1);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        }
    }

    public int findInList(boolean returnIndex, String id) {
        boolean found = false;
        List<WebElement> list = outboundCorrespondenceDefinitionListPage.idColumn();
        while (!found) {
            for (int index = 0; index < list.size(); index++) {
                if (list.get(index).getText().trim().equalsIgnoreCase(id)) {
                    found = true;
                    page.set(1);
                    if (returnIndex) return index;
                }
            }
            if (!found) goToNextPage();
            waitFor(1);
            list = outboundCorrespondenceDefinitionListPage.idColumn();
        }
        return 0;
    }

    public int findInListIB(boolean returnIndex, String name) {
        boolean found = false;
        List<WebElement> list = inboundCorrespondenceDefinitionListPage.nameColumn();
        while (!found) {
            for (int index = 0; index < list.size(); index++) {
                if (list.get(index).getText().trim().equalsIgnoreCase(name)) {
                    found = true;
                    page.set(1);
                    if (returnIndex) return index;
                }
            }
            if (!isElementPresent(outboundCorrespondenceDefinitionListPage.arrowForward)) {
                Assert.fail("Definition Type not found");
            }
            if (!found) goToNextPage();
            waitFor(1);
            list = inboundCorrespondenceDefinitionListPage.nameColumn();
        }
        return 0;
    }

    /**
     * Sorting Comparator for sorting text from webelements in ascending order
     */
    public Comparator<WebElement> WEBELEMENT_TEXT = new Comparator<WebElement>() {
        @Override
        public int compare(WebElement o1, WebElement o2) {
            return o1.getText().compareTo(o2.getText());
        }
    };

    /**
     * Temporarily reduces implicit wait times for quick isDisplayed() on Webelements
     */
    public boolean quickIsDisplayed(WebElement element) {
        waitFor(3);
        boolean found = false;
        try {
            if (element.isDisplayed()) {
                found = true;
            }
            waitFor(10);
            return found;
        } catch (Exception exception) {
            waitFor(10);
            return false;
        }
    }

    public boolean quickIsDisplayed(String xpath) {
        waitFor(3);
        boolean found = false;
        WebElement element;
        try {
            element = Driver.getDriver().findElement(By.xpath(xpath));
            if (element.isDisplayed()) {
                found = true;
            }
            waitFor(10);
            return found;
        } catch (Exception exception) {
            waitFor(10);
            return false;
        }
    }

    public void clearWithActions(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.click(element)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.BACK_SPACE)
                .build()
                .perform();
    }

    public void clearFillWithActions(WebElement element, String text) {
        Actions actions = new Actions(Driver.getDriver());
        actions.click(element)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.BACK_SPACE)
                .sendKeys(text)
                .build()
                .perform();
    }

    public void clearStartDate() {
        waitFor(1);
        outboundCorrespondenceDefinitionPage.startDateCalendar.click();
        waitFor(1);
        outboundCorrespondenceDefinitionPage.startDateCalendarClear.click();
    }

    public void clearEndDate() {
        outboundCorrespondenceDefinitionPage.endDateCalendar.click();
        waitFor(1);
        outboundCorrespondenceDefinitionPage.endDateCalendarClear.click();
    }

    /**
     * Uses sendKeys, waits for sendKeys to finish max 6 sec, then verifies value in input
     */
    public void typeAndVerify(WebElement element, String text) {
        scrollToElement(element);
        BrowserUtils.staticWait(700);
        clearWithActions(element);
        element.sendKeys(text);
        int counter = 1;
        do {
            BrowserUtils.staticWait(500);
            counter++;
        } while (element.getAttribute("value").length() > text.length() || counter > 12);
        Assert.assertEquals(element.getAttribute("value"), text);
    }

    public void selectDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        if ("460 BLCRM0510".equalsIgnoreCase(selector)) {
            waitForVisibility(Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]")), 60);
        }
        staticWait(500);
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
        // hover(element);
        Assert.assertTrue(element.getText().contains(selector), "Selector " + selector + " - wasn't selected");
    }

    public void selectDropDown2(WebElement element, String selector) {
        BrowserUtils.staticWait(500);
        BrowserUtils.staticWait(500);
        element.sendKeys(selector + Keys.ENTER);
        BrowserUtils.staticWait(500);
        WebElement added = Driver.getDriver().findElement(By.xpath("//div[@role='button']/span[contains(text(),'" + selector + "')]"));
        Assert.assertTrue(quickIsDisplayed(element));
    }

    public void verifyErrorMessageDisplayed(String message) {
        Assert.assertTrue(quickIsDisplayed(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + message + "')]"))));
    }

    public void selectInboundCorrespondencetype(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        element.click();
        waitFor(1);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        waitFor(3);
        outboundCorrespondenceDefinitionPage.getDropdownOption(World.getWorld().outboundCorrespondenceDefinition.get().inboundCorrespondenceType).click();

    }

    public void chooseCalendarStartDate(WebElement calendarButton, WebElement calendarOk, String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        String[] currentDate = dtf.format(now).split("/");
        String[] fullDate = date.split("/");
        calendarButton.click();
        if (Integer.parseInt(fullDate[0]) - Integer.parseInt(currentDate[0]) > 0) {
            outboundCorrespondenceDefinitionChannelPage.nextCalendarMonth.click();
            waitFor(2);
        }
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiPickersSlideTransition-transitionContainer MuiPickersCalendar-transitionContainer')]/div/div/div/button/span/p[contains(text(),'" + fullDate[1] + "')]/../..")).click();
        waitFor(2);
        calendarOk.click();
        waitFor(3);
    }

    public void chooseCalendarEndDate(WebElement calendarButton, WebElement calendarOk, String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        String[] currentDate = dtf.format(now).split("/");
        String[] fullDate = date.split("/");
        calendarButton.click();
        if (Integer.parseInt(fullDate[0]) - Integer.parseInt(currentDate[0]) > 0) {
            outboundCorrespondenceDefinitionChannelPage.nextCalendarMonth.click();
            waitFor(2);
        }
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiPickersSlideTransition-transitionContainer MuiPickersCalendar-transitionContainer')]/div/div/div/button/span/p[contains(text(),'" + fullDate[1] + "')]/../..")).click();
        waitFor(2);
        calendarOk.click();
        waitFor(3);
    }


    public String getRandomString(int stringLength) {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(stringLength, useLetters, useNumbers);
    }

    public void clickJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        executor.executeScript("arguments[0].click();", element);
    }


    public List<Date> getDatesFromWebElements(List<WebElement> elements) {
        List<Date> dates = new ArrayList<>();
        for (WebElement element : elements) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                dates.add(new Date(String.valueOf(dateFormat.parse(element.getText()))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    public List<Date> getDatesFromWebElements(List<WebElement> elements, String dateFormatter) {
        List<Date> dates = new ArrayList<>();
        for (WebElement element : elements) {
            DateFormat dateFormat = new SimpleDateFormat(dateFormatter);
            try {
                dates.add(new Date(String.valueOf(dateFormat.parse(element.getText()))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    public boolean verifyDatesDescendingorder(List<Date> list) {
        boolean verify = true;
        List<Date> sorted = new ArrayList<>(list);
        Collections.sort(sorted, Collections.reverseOrder());
        for (int index = 0; index < list.size(); index++) {
            if (!list.get(index).toString().equalsIgnoreCase(sorted.get(index).toString())) {
                verify = false;
            }
        }
        return verify;
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * @param CID provide correspondence id
     *            goes to every page in outbound correspondence list
     *            starting from page 1 and stops at the page with the
     *            correspondence id that is provided, or stops on last page (max 100pages)
     */
    public void findInOutboundCorrespondenceListByCID(String CID) {
//        waitForVisibility(dmsCaseContactDetPage.getOutboundCorrPageNum("1"), 3);
//        scrollToElement(dmsCaseContactDetPage.getOutboundCorrPageNum("1"));
        int page = 1;
        boolean isCidDisplayed = false;
        boolean isLastPageDisplayed = false;
        boolean cidNotFound = false;
        int limit = 100;
        if ("previouslyCreated".equalsIgnoreCase(CID) || "fromRequest".equalsIgnoreCase(CID)) {
            CID = World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()).toString();
        }
        do {
            isLastPageDisplayed = quickIsDisplayed("//h5[contains(text(),'OUTBOUND')]/following::li/a/span[contains(text(),'last')]");
            isCidDisplayed = isCIDDisplayed(CID);
            if (isCidDisplayed) {
                waitFor(1);
                break;
            }
            if (!isLastPageDisplayed) {
                cidNotFound = true;
                break;
            }
            waitFor(1);
            dmsCaseContactDetPage.getOutboundCorrPageNum(String.valueOf(page)).click();
            waitFor(1);
            page++;
        } while (page < limit);
        if (cidNotFound) {
            Assert.fail("Correspondence Id - " + CID + " not found in list");
        }
    }

    public Boolean isCIDDisplayed(String CID) {
        waitFor(3);
        WebElement element;
        boolean found = false;
        try {
            element = Driver.getDriver().findElement(By.xpath("//h5[text()='OUTBOUND CORRESPONDENCES']/following::div[2]/div/div[2]/div/table/tbody/tr/td[text()='" + CID + "']"));
            if (element.isDisplayed()) {
                found = true;
            }
            waitFor(10);
            return found;
        } catch (Exception exception) {
            waitFor(10);
            return false;
        }
    }

    public String findCorrespondenceDefinitionWithChannel(String channel, Map<String, String> dataTable) {
        String idAndNameOfOCD = "";
        waitFor(3);
        int pages = getNumberOfPagesOnOCDefinition();
        outer:
        for (int i = 1; i <= pages; i++) {
            for (int j = 0; j < outboundCorrespondenceDefinitionListPage.idColumn().size(); j++) {
                if (i > 1) {
                    waitFor(1);
                    Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i) + "']")).click();
                    waitFor(1);
                }
                if (outboundCorrespondenceDefinitionListPage.statusColumn().get(j).getText().equalsIgnoreCase("ACTIVE")) {
                    outboundCorrespondenceDefinitionListPage.idColumn().get(j).click();
                    waitFor(2);
                    //to avoid NoSuchElementException exception
                    if (outboundCorrespondenceDefinitionPage.mail.size() > 0) {
                        if (isCorrespondenceContainsActiveChannel(channel) && !isUserMayRequestSelected()) {
                            idAndNameOfOCD = outboundCorrespondenceDefinitionPage.name.getAttribute("value") + " - " + outboundCorrespondenceDefinitionPage.Id.getAttribute("value");
                            outboundCorrespondenceDefinitionPage.mail.get(0).click();
                            waitFor(1);
                            if (quickIsDisplayed(outboundCorrespondenceDefinitionChannelPage.templateFileName)) {
                                System.out.println("Proper Channel found " + idAndNameOfOCD);
                                waitFor(1);
                                outboundCorrespondenceDefinitionPage.backButton.click();
                                if (!isSendImmediatelySelected()) {
                                    action.click(outboundCorrespondenceDefinitionChannelPage.sendImmediately).build().perform();
                                    System.out.println("Send Immediately selected");
                                }
                                if (!outboundCorrespondenceDefinitionChannelPage.endDate.getAttribute("value").isEmpty()) {
                                    System.out.println("Deleting end date");
                                    outboundCorrespondenceDefinitionChannelPage.endDate.click();
                                    waitFor(1);
                                    clearWithActions(outboundCorrespondenceDefinitionChannelPage.endDate);
                                    System.out.println("End Date deleted");
                                }
                                waitFor(1);
                                outboundCorrespondenceDefinitionChannelPage.saveButton.click();
                                waitFor(1);
                                clearRequiredKeysField();
                                clearDataElements();
                                fillOutCorrespondenceDefinitionDetails(dataTable);
                                outboundCorrespondenceDefinitionPage.saveButon.click();
                                waitFor(1);
                                outboundCorrespondenceDefinitionPage.backButton.click();
                                break outer;
                            } else {
                                waitFor(1);
                                outboundCorrespondenceDefinitionPage.backButton.click();
                                waitFor(1);
                                outboundCorrespondenceDefinitionPage.backButton.click();
                            }
                        } else {
                            waitFor(1);
                            outboundCorrespondenceDefinitionPage.backButton.click();
                        }
                    } else {
                        waitFor(1);
                        outboundCorrespondenceDefinitionPage.backButton.click();
                    }
                }

            }
            if (i != pages) {
                Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")).click();
                waitFor(1);
            }
        }

        return idAndNameOfOCD;
    }

    private int getNumberOfPagesOnOCDefinition() {
        int pageCount = 1;
        if (outboundCorrespondenceDefinitionListPage.arrowForward.isDisplayed()) {
            outboundCorrespondenceDefinitionListPage.arrowForward.click();
            pageCount = Integer.parseInt(outboundCorrespondenceDefinitionListPage.listOfPages.get(outboundCorrespondenceDefinitionListPage.listOfPages.size() - 1).getText());
            waitFor(1);
            outboundCorrespondenceDefinitionListPage.arrowBack.click();
            waitFor(1);
        }
        return pageCount;
    }

    private boolean isCorrespondenceContainsActiveChannel(String channelType) {
        return outboundCorrespondenceDefinitionPage.mail.get(0).getText().equalsIgnoreCase(channelType) && outboundCorrespondenceDefinitionPage.status.getText().equalsIgnoreCase("active");
    }

    private boolean isUserMayRequestSelected() {
        return outboundCorrespondenceDefinitionPage.userMayRequest.isSelected();
    }

    private boolean isSendImmediatelySelected() {
        return outboundCorrespondenceDefinitionChannelPage.sendImmediately.isSelected();
    }

    private void clearRequiredKeysField() {
        waitFor(1);
        outboundCorrespondenceDefinitionPage.deleteAllRequiredKeys.click();
    }

    private void clearDataElements() {
        for (WebElement deleteIcon : outboundCorrespondenceDefinitionPage.listOfDeleteIconBodyElements) {
            waitFor(1);
            deleteIcon.click();
        }
    }

    public void searchForOutboundDefinitionAndSetRequiredKeys(Map<String, String> dataTable) {
        waitFor(2);
        int pages = getNumberOfPagesOnOCDefinition();
        String definitionId = World.save.get().get("idAndNameOfOCD").split(" - ")[1];
        System.out.println("Looking for Definition Id " + definitionId);
        outer:
        for (int i = 1; i <= pages; i++) {
            for (int j = 0; j < outboundCorrespondenceDefinitionListPage.idColumn().size(); j++) {
                if (outboundCorrespondenceDefinitionListPage.idColumn().get(j).getText().equalsIgnoreCase(definitionId)) {
                    outboundCorrespondenceDefinitionListPage.idColumn().get(j).click();
                    break outer;
                }
            }
            if (i != pages) {
                Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")).click();
                waitFor(1);
            }
        }
        clearRequiredKeysField();
        clearDataElements();
        waitFor(2);
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "caseID":
                    if (dataTable.get("caseID").equalsIgnoreCase("true")) {
                        action.sendKeys(outboundCorrespondenceDefinitionPage.requiredKeys, "Case ID" + Keys.ENTER).build().perform();
                    }
                    break;
                case "consumerId":
                    if (dataTable.get("consumerId").equalsIgnoreCase("true")) {
                        action.sendKeys(outboundCorrespondenceDefinitionPage.requiredKeys, "Consumer ID" + Keys.ENTER).build().perform();
                    }
                    break;
                case "consumerIds":
                    if (dataTable.get("consumerIds").equalsIgnoreCase("true")) {
                        action.sendKeys(outboundCorrespondenceDefinitionPage.requiredKeys, "Consumer IDs" + Keys.ENTER).build().perform();
                    }
                    break;
                case "applicationId":
                    if (dataTable.get("applicationId").equalsIgnoreCase("true")) {
                        action.sendKeys(outboundCorrespondenceDefinitionPage.requiredKeys, "Application Id" + Keys.ENTER).build().perform();
                    }
                    break;
                case "firstName":
                    if (dataTable.get("firstName").equalsIgnoreCase("true")) {
                        action.click(outboundCorrespondenceDefinitionPage.addDataElement).build().perform();
                        String dataElementName = "First Name";
                        action.sendKeys(outboundCorrespondenceDefinitionPage.dataElementFieldName, dataElementName).build().perform();
                        World.save.get().put("dataElementName", dataElementName.toUpperCase());
                        action.sendKeys(outboundCorrespondenceDefinitionPage.dataElementFieldType, "Short Text" + Keys.ENTER).build().perform();
                        waitFor(1);
                        action.click(outboundCorrespondenceDefinitionPage.dataElementRequired).build().perform();
                    }
                    break;
            }
        }
        waitFor(1);
        action.click(outboundCorrespondenceDefinitionPage.saveButon).build().perform();
        waitFor(1);
    }

    public void fillOutCorrespondenceDefinitionDetails(Map<String, String> dataTable) {
        for (String keyword : dataTable.keySet()) {
            switch (keyword) {
                case "userMayRequest":
                    if (dataTable.get("userMayRequest").equalsIgnoreCase("true")) {
                        outboundCorrespondenceDefinitionPage.userMayRequest.click();
                    }
                    break;
                case "approvalRequired":
                    if (dataTable.get("approvalRequired").equalsIgnoreCase("true")) {
                        outboundCorrespondenceDefinitionPage.approvalRequired.click();
                    }
                    break;
                case "userMayEnter":
                    if (dataTable.get("userMayEnter").equalsIgnoreCase("true")) {
                        outboundCorrespondenceDefinitionPage.userMayEnter.click();
                    }
                    break;
            }
        }
    }

    public static boolean verifyWebElementTextLength(WebElement element, int expectedLength) {
        return element.getText().length() <= expectedLength;
    }

    public static boolean verifyTextLength(String element, int expectedLength) {
        return element.length() <= expectedLength;
    }

    //this method validate MM/DD/YYYY HH:MM XM date format
    public static boolean isMM_DD_YYYY_HHMM_XM_format(String date) {
        return date.matches("\\d{2}/\\d{2}/\\d{4}\\s+\\d{2}:\\d{2}\\s+(AM|PM)");
    }

    public static boolean isAlphanumericWithSpace(String text) {
        return text.matches("^[a-zA-Z0-9 ()]*$");
    }

    public void clickWithActions(WebElement element) {
        action.click(element).build().perform();
    }

    public void sendKeyWithActions(WebElement element, String key) {
        action.sendKeys(element, key).build().perform();
    }

    public static String ConverttoTimeZone(String timezone, Date date) {
        System.out.println("given date :  " + date);

        String timezonedate = "";
        switch (timezone) {
            case "GMT": {
                DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone gmtTime = TimeZone.getTimeZone("GMT");
                gmtFormat.setTimeZone(gmtTime);
                System.out.println("GMT Time: " + gmtFormat.format(date));
                timezonedate = gmtFormat.format(date);
                break;
            }
            case "CST": {
                DateFormat cstFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone cstTime = TimeZone.getTimeZone("GMT-6");
                cstFormat.setTimeZone(cstTime);
                System.out.println("CST Time: " + cstFormat.format(date));
                timezonedate = cstFormat.format(date);
                break;
            }
            case "CDT": {
                DateFormat cdtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone cdtTime = TimeZone.getTimeZone("GMT-5");
                cdtFormat.setTimeZone(cdtTime);
                System.out.println("CDT Time: " + cdtFormat.format(date));
                timezonedate = cdtFormat.format(date);
                break;
            }
            case "EST": {
                DateFormat estFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone estTime = TimeZone.getTimeZone("GMT-5");
                estFormat.setTimeZone(estTime);
                System.out.println("EST Time: " + estFormat.format(date));
                timezonedate = estFormat.format(date);
                break;
            }
            case "EDT": {
                DateFormat edtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone edtTime = TimeZone.getTimeZone("GMT-4");
                edtFormat.setTimeZone(edtTime);
                System.out.println("EDT Time: " + edtFormat.format(date));
                timezonedate = edtFormat.format(date);
                break;
            }
            case "PST": {
                DateFormat pstFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone pstTime = TimeZone.getTimeZone("GMT-8");
                pstFormat.setTimeZone(pstTime);
                System.out.println("PST Time: " + pstFormat.format(date));
                timezonedate = pstFormat.format(date);
                break;
            }
            case "PDT": {
                DateFormat pdtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                TimeZone pdtTime = TimeZone.getTimeZone("GMT-7");
                pdtFormat.setTimeZone(pdtTime);
                System.out.println("PDT Time: " + pdtFormat.format(date));
                timezonedate = pdtFormat.format(date);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid timezone :  " + timezone);
        }
        return timezonedate;
    }

    public static String UTCtoCustomTimeZone(String timezone, String utctime) {
        // utc time should be "2021-11-08T13:21:52Z" format
        //System.out.println("given date :  " + utctime);

        String timezonedate = "";
        switch (timezone) {
            case "EST":
            case "EDT": {
                timezonedate = Instant.parse(utctime)
                        .atZone(ZoneId.of("America/New_York"))
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
                break;
            }
            case "CST":
            case "CDT": {
                timezonedate = Instant.parse(utctime)
                        .atZone(ZoneId.of("America/Chicago"))
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
                break;
            }
            case "PST":
            case "PDT": {
                timezonedate = Instant.parse(utctime)
                        .atZone(ZoneId.of("America/Los_Angeles"))
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid timezone :  " + timezone);
        }
        return timezonedate;
    }


    public int currentday() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DATE);
        //Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        System.out.println("Current Date is: " + date);
        System.out.println("Current Day is:: " + day);
        System.out.println("Current Month is:: " + month);
        System.out.println("Current Year is: " + year);
        System.out.println("Current Day of Week is: " + dayOfWeek);
        System.out.println("Current Day of Month is: " + dayOfMonth);
        System.out.println("Current Day of Year is: " + dayOfYear);

        return day;
    }

    public void searchForOutboundDefinitionByID(String id) {
        waitFor(2);
        int pages = getNumberOfPagesOnOCDefinition();
        System.out.println("Looking for Definition Id " + id);
        outer:
        for (int i = 1; i <= pages; i++) {
            for (int j = 0; j < outboundCorrespondenceDefinitionListPage.idColumn().size(); j++) {
                if (outboundCorrespondenceDefinitionListPage.idColumn().get(j).getText().equalsIgnoreCase(id)) {
                    outboundCorrespondenceDefinitionListPage.idColumn().get(j).click();
                    break outer;
                }
            }
            if (i != pages) {
                Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")).click();
                waitFor(1);
            }
        }
        waitFor(2);
    }

    public void ItemsPerPage(int itemsperpage) {
        waitFor(2);
        outboundCorrespondenceDefinitionListPage.itemsperpage.click();
        waitFor(1);
        Driver.getDriver().findElement(By.xpath("//li[text()='Show " + itemsperpage + "']")).click();
        waitFor(5);
    }

    public static String getTextPhoneNumberReceiveSMS() {
//        Driver.getDriver().get("https://receive-smss.com/");
//        waitForPageToLoad(15);
//        BrowserUtils.waitFor(4);
//        WebElement phoneNumber = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'United States')]/preceding-sibling::*"));
        return "6465780322";
    }

    public void navToReceiveSmsTextPage() {
        Driver.getDriver().get("https://receive-smss.com/sms/1" + String.valueOf(World.generalSave.get().get("receiveSMS")));
        waitForPageToLoad(15);
        BrowserUtils.waitFor(4);
        scrollDown();
    }
}