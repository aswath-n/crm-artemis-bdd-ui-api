package com.maersk.dms.step_definitions;

import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.*;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.*;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ViewOutboundCorrespondenceStepDefs extends CRMUtilities implements ApiBase {
//    private static WebDriver driver = Driver.getDriver();
    static final ThreadLocal<String> currentHandle = ThreadLocal.withInitial(()->null);
    static ThreadLocal<Integer> anteNotes = ThreadLocal.withInitial(()->0);
    static ThreadLocal<Integer> postNotes = ThreadLocal.withInitial(()->0);
    static final ThreadLocal <String> postCancel = ThreadLocal.withInitial(String::new);
    static final ThreadLocal <String> anteCancel = ThreadLocal.withInitial(String::new);
    static final ThreadLocal <String> anteHandle = ThreadLocal.withInitial(String::new);
    static final ThreadLocal <String> postHandle = ThreadLocal.withInitial(String::new);
    static final ThreadLocal <String> anteUrl = ThreadLocal.withInitial(String::new);
    static final ThreadLocal <String> postUrl = ThreadLocal.withInitial(String::new);

    final ThreadLocal<ArrayList<String>> tabs = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<LinkedHashSet<String>> setOftabs = ThreadLocal.withInitial(LinkedHashSet::new);
    Actions actions;
    WebDriverWait wait;
    InboundCorrespondencePage inboundCorrespondencePage = new InboundCorrespondencePage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    public final static ThreadLocal<JsonPath> getCorrespondenceResponse = new ThreadLocal<>();
    private final ViewOutboundCorrespondenceDetailsPage viewOutboundCorrespondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    private final OutboundCorrespondenceSearchPage outboundCorrespondenceSearchPage = new OutboundCorrespondenceSearchPage();
    private final CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();

    CorrespondenceDetailsPage correspondenceDetailsPage = new CorrespondenceDetailsPage();

    private APIAutoUtilities apiAutoUtilities = new APIAutoUtilities();
    private static String apiDmsToken = ConfigurationReader.getProperty("apiDmsToken");
    private static String retrieveTemplateEndPoint = "/mars-ecms-bff/ecms/document/contents/notification/";

    public void multipleMail() {
        assertTrue(inboundCorrespondencePage.multipleOutboundsMail.isDisplayed());
        assertTrue(inboundCorrespondencePage.multipleOutboundsMail.getAttribute("innerHTML").contains("more_horiz"));
    }

    public void multipleEmail() {
        assertTrue(inboundCorrespondencePage.multipleOutboundsEmail.isDisplayed());
        assertTrue(inboundCorrespondencePage.multipleOutboundsEmail.getAttribute("innerHTML").contains("more_horiz"));
    }

    public void multipleFaxText() {
        assertTrue(inboundCorrespondencePage.multipleOutboundsFax.isDisplayed());
        assertTrue(inboundCorrespondencePage.multipleOutboundsFax.getAttribute("innerHTML").contains("more_horiz"));
    }

    public void outboundDetailNotifications() {
        assertTrue(inboundCorrespondencePage.outboundCorrespondenceDetailIcons.isDisplayed());
        assertTrue(inboundCorrespondencePage.outboundCorrespondenceDetailIcons.isEnabled());
    }


    public String matchEmail() {
        String expectedEmail = "MultiPageDocument--EMAILone2MultiPageDocument--EMAILtwo3MultiPageDocument--EMAILthree4MultiPageDocument--EMAILfour5";
        return expectedEmail;
    }

    public String matchMail() {
        String expectedMail = "MultiPageDocument--MAILone2MultiPageDocument--MAILtwo3MultiPageDocument--MAILthree4MultiPageDocument--MAILfour5";
        return expectedMail;
    }

    public String matchFax() {
        String expectedFax = "MultiPageDocument--FAXone2MultiPageDocument--FAXtwo3MultiPageDocument--FAXthree4MultiPageDocument--FAXfour5";
        return expectedFax;
    }

    public String matchText() {
        String expectedText = "MultiPageDocument--TEXTone2MultiPageDocument--TEXTtwo3MultiPageDocument--TEXTthree4MultiPageDocument--TEXTfour5";
        return expectedText;
    }


    public void scrollDownJS(String increment) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("scrollTo(0, " + increment + ");");
    }

    public void navigateToDemographicInfoScreen() {
        tabs.set( new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() >= 2) Driver.getDriver().switchTo().window(tabs.get().get(0));
        wait.until(ExpectedConditions.titleIs("Case View - Case & Contact Details"));
        inboundCorrespondencePage.demographicInfoTab.click();
    }

    public void navigateToFirstWindow() {
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() >= 2) Driver.getDriver().switchTo().window(tabs.get().get(0));
    }

    public void viewCorrespondence(String cid) {
        String mainSessionID = String.valueOf(Driver.getDriver().getWindowHandles());
        waitForPageToLoad(7);
        viewIconByCid(cid);
        setOftabs.set(new LinkedHashSet<>(Driver.getDriver().getWindowHandles()));
        for (String sessionID: setOftabs.get()) {
            if(!mainSessionID.equalsIgnoreCase(sessionID)){
             Driver.getDriver().switchTo().window(sessionID);
            }
        }
    }

    public void viewIconByCid(String cid) {
        String x = "//*[text()='" + cid + "']/../td[9]";
        hover(Driver.getDriver().findElement(By.xpath(x)));
        waitFor(3);
        Driver.getDriver().findElement(By.xpath(x)).click();
        BrowserUtils.waitFor(3);
    }

    public void viewCorrespondenceByNid(String nid) {
        String mainSessionID = String.valueOf(Driver.getDriver().getWindowHandles());
        waitForPageToLoad(7);
        viewIconByNid(nid);
        waitFor(4);
        setOftabs.set(new LinkedHashSet<>(Driver.getDriver().getWindowHandles()));
        for (String sessionID: setOftabs.get()) {
            if(!mainSessionID.equalsIgnoreCase(sessionID)){
                Driver.getDriver().switchTo().window(sessionID);
            }
        }
    }

    public void viewIconByNid(String nid) {
        String x = "(//*[text()='" + nid + "'])[1]/../../div[7]/p/button";
        Actions actions = new Actions(Driver.getDriver());
        WebElement pdfIcon = Driver.getDriver().findElement(By.xpath(x));
        actions.moveToElement(pdfIcon).perform();
        actions.doubleClick(pdfIcon).perform();
    }

    public void verifyDocumentWindowOpens(String anteHandle) {
        waitForPageToLoad(7);
        for (String handle : Driver.getDriver().getWindowHandles()) {
            if (!handle.equals(anteHandle)) Driver.getDriver().switchTo().window(handle);
        }
        wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
    }

    public void verifyDocumentWindowClosed() {
        waitForPageToLoad(7);
        Set<String> handles = Driver.getDriver().getWindowHandles();
        assertTrue(handles.size() < 2);
    }


    public void viewCorrespondenceDetailsByCid(String cid) {
        inboundCorrespondencePage.outboundCorrespondenceDetailsByCid(cid, Driver.getDriver());
    }

    public void moverToCorrespondenceHeader() {
        Actions actions = new Actions(Driver.getDriver());
        waitForVisibility(inboundCorrespondencePage.outboundCorrespondencesHeader, 7);
        actions.moveToElement(inboundCorrespondencePage.outboundCorrespondencesHeader).perform();
    }

    public void verifyCorrespondenceDetailsHeaderByCid(String cid) {
        waitFor(3);
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(inboundCorrespondencePage.correspondenceDetailsHeader).perform();
        assertEquals(inboundCorrespondencePage.correspondenceDetailsHeader.getAttribute("innerHTML"), cid);
    }

    public void verifyOutboundCorrespondenceDetailsSaveBtn() {
        waitForVisibility(inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn, 4);
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.isDisplayed());
    }

    public void verifyOutboundCorrespondenceDetailsCancelBtn() {
        waitForVisibility(inboundCorrespondencePage.correspondenceDetailsNotesCancelBtn, 4);
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNotesCancelBtn.isDisplayed());
    }

    public void addNewNote() {
        waitFor(8);
        scrollDownUsingActions(10);
        anteNotes.set(0);
        World.generalSave.get().put("note",getCurrentDateWithFormat());
        inboundCorrespondencePage.addNoteInput.sendKeys(String.valueOf(World.generalSave.get().get("note")));
        inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.click();
        assertTrue(inboundCorrespondencePage.correspondenceDetailsSaveNoteSuccessMsg.isDisplayed());
    }

    public void verifySaveSuccessMsg() {
        boolean isDisplayed=false;
        int max=5;
       while (max>0&&!isDisplayed){
           try {
               isDisplayed = inboundCorrespondencePage.correspondenceDetailsSaveNoteSuccessMsg.isDisplayed();
           } catch (Exception e) {
               isDisplayed = false;
           }finally {
               max--;
               System.out.println("max - "+max);
               waitFor(1);
           }
       }
        assertTrue(isDisplayed);
    }

    public void verifySavedNote() {
        waitFor(4);
        wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='pt-5 mx-comment-border']/p[@class='text-right']"), anteNotes.get()));
        } catch (TimeoutException a) {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='pt-5 mx-comment-border']/p[@class='text-right']"), anteNotes.get()));
        }
        postNotes.set( Driver.getDriver().findElements(By.xpath("//div[@class='pt-5 mx-comment-border']/p[@class='text-right']")).size());
        assertTrue(postNotes.get() > anteNotes.get());
    }

    public void verifyTextInputClear() {
        assertTrue(inboundCorrespondencePage.addNoteInput.getAttribute("value").isEmpty());
    }


    public void verifyLatestNoteText() {
        waitFor(15);
        String noteText = String.valueOf(World.generalSave.get().get("note"));
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+noteText+"')]")).isDisplayed());
    }

    public void verifyNotesFields() {
        waitFor(7);
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNoteUserNames.isDisplayed());
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNoteDateTimeStamps.isDisplayed());
        assertTrue(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'"+String.valueOf(World.generalSave.get().get("note"))+"')]")).isDisplayed());
    }

    public void verifyDescendingNotes() {
        waitFor(7);
        List<Integer> noteText = new ArrayList<>();
        List<WebElement> elements = Driver.getDriver().findElements(By.xpath("//div[@class='pt-5 mx-comment-border']/p[@class='ml-3 pl-4 pt-3']"));
        for (int i = 0; i < elements.size(); i++) {
            noteText.add(Integer.valueOf(elements.get(i).getText().trim()));
        }
        List<Integer> expected = new ArrayList<>(noteText);
        Collections.sort(expected);
        for (int i = 0; i < noteText.size(); i++) {
            Assert.assertEquals(noteText.get(i),expected.get(i));
        }
    }

    public void verifyCreatedOnOBNotes() {
        String timestamp = inboundCorrespondencePage.correspondenceDetailsLatestNoteAddedDate.getAttribute("innerText").trim();
        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String expected = dateFormat2.format(new Date());
        Assert.assertEquals(expected,timestamp);
    }


    public void verifyCreatedBy() {
        String created = inboundCorrespondencePage.correspondenceDetailsLatestNoteCreatedBy.getAttribute("innerText").trim();
        assertTrue(created.length() > 0);
    }

    public void populateNoteTextInput() {
        waitFor(4);
        scrollDownUsingActions(10);
        anteNotes.set(0);
        World.generalSave.get().put("note",getCurrentDateWithFormat());
        inboundCorrespondencePage.addNoteInput.sendKeys(String.valueOf(World.generalSave.get().get("note")));
    }

    public void verifyNavigationWarningMsg() {
        boolean isDisplayed=false;
        int max=8;
        while (max>0&&!isDisplayed){
            try {
                isDisplayed = inboundCorrespondencePage.correspondenceDetailsNavigationWarningMsg.isDisplayed();
            } catch (Exception e) {
                isDisplayed = false;
            }finally {
                max--;
                System.out.println("max - "+max);
                waitFor(1);
            }
        }
        assertTrue(isDisplayed);
//        wait.until(ExpectedConditions.visibilityOf(inboundCorrespondencePage.correspondenceDetailsNavigationWarningMsg));
//        assertTrue(inboundCorrespondencePage.correspondenceDetailsNavigationWarningMsg.isDisplayed());
    }

    public void attemptCancel() {
        inboundCorrespondencePage.correspondenceDetailsNotesCancelBtn.click();
    }

    public void verifyPageAfterCancel() {
        postCancel.set( Driver.getDriver().getTitle());
        assertTrue(postCancel.get().equalsIgnoreCase(anteCancel.get()));
    }

    public void verifyNewWindow() {
        waitForPageToLoad(7);
        tabs.set( new ArrayList<>(Driver.getDriver().getWindowHandles()));
        assertTrue(tabs.get().size() >= 2);
    }

    public boolean verifyNotificationViewed(String type) throws IOException {
        String api;
        Boolean bool = false;
        String mail = "";
        String email = "";
        String text = "";

        if (!"InboundDocument".equalsIgnoreCase(type)) {
            if(null==getCorrespondenceResponse.get()){
                synchronized (getCorrespondenceResponse){
                    getCorrespondenceResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()))));
                }
            }
            List<Map<String, String>> list = getCorrespondenceResponse.get().getList("recipients[0].notifications");
            for (Map<String, String> notifiations : list) {
                switch (notifiations.get("channelType").toUpperCase()) {
                    case "MAIL":
                        mail = notifiations.get("notificationId");
                        break;
                    case "EMAIL":
                        email = notifiations.get("notificationId");
                        break;
                    case "TEXT":
                        text = notifiations.get("notificationId");
                        break;
                }
            }
        }
        switch (type) {
            case "Mobile App":
                api = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/2799";
                if (EventsUtilities.getRawLogs(api).size() > 0) {
                    String accessToken = getAccessToken();
                    byte[] res = getCallPDFNID(accessToken, "2799");
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("This Is Mobile App Notification"));
                    Assert.assertTrue(pdfStripper.contains("Page 2 of Mobile App Notification "));
                    Assert.assertTrue(pdfStripper.contains("Page 3 of Mobile App Notification "));
                    bool = true;
                }
            case "Email":
                api = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/" + email;
                if (EventsUtilities.getRawLogs(api).size() > 0) {
                    String accessToken = getAccessToken();
                    byte[] res = getCallPDFNID(accessToken, email);
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("This Is Email Notification"));
                    Assert.assertTrue(pdfStripper.contains("Page 2 of Email Notification "));
                    Assert.assertTrue(pdfStripper.contains("Page 3 of Email Notification "));
                    bool = true;
                }
            case "Mail":
                api = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/" + mail;
                if (EventsUtilities.getRawLogs(api).size() > 0) {
                    String accessToken = getAccessToken();
                    byte[] res = getCallPDFNID(accessToken, mail);
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("This Is Mail Notification"));
                    Assert.assertTrue(pdfStripper.contains("Page 2 of Mail Notification "));
                    Assert.assertTrue(pdfStripper.contains("Page 3 of Mail Notification "));
                    bool = true;
                }
            case "Text":
                api = "https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/" + text;
                if (EventsUtilities.getRawLogs(api).size() > 0) {
                    String accessToken = getAccessToken();
                    byte[] res = getCallPDFNID(accessToken, text);
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("This Is Text Notification"));
                    Assert.assertTrue(pdfStripper.contains("Page 2 of Text Notification "));
                    Assert.assertTrue(pdfStripper.contains("Page 3 of Text Notification "));
                    bool = true;
                }
            case "InboundDocument":
                api = "https://max-nonprod-qa.apigee.pcf-maersk.com/mars-cp-webapp-apiproxy/mars-ecms-bff/ecms/document/contents/" + World.generalSave.get().get("InboundDocumentId") + "?format=pdf&type=Inbound%20Correspondence";
                if (EventsUtilities.getRawLogs(api).size() > 0) {
                    String accessToken = getAccessToken();
                    byte[] res = getCallPDFINBDOCUMENT(accessToken, World.generalSave.get().get("InboundDocumentId").toString());
                    PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
                    String pdfStripper = new PDFTextStripper().getText(document);
                    Assert.assertTrue(pdfStripper.contains("MultiPageDocument"));
                    Assert.assertTrue(pdfStripper.contains("MAIL two "));
                    Assert.assertTrue(pdfStripper.contains("MAIL three "));
                    Assert.assertTrue(pdfStripper.contains("MAIL four "));
                    bool = true;
                }
        }
        return bool;
    }

    private byte[] getCallPDFINBDOCUMENT(String accessToken, String id) {
        byte[] responseBody = given()
                .contentType("application/pdf")
                .auth().preemptive().oauth2(accessToken)
                .get("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/" + id + "?type=Inbound+Correspondence&format=pdf").then()
                .statusCode(200)
                .extract()
                .asByteArray();
        return responseBody;
    }

    public void zoomInOutOfDoc() throws AWTException {
        BrowserUtils.waitFor(10);
        tabs.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() >= 2) Driver.getDriver().switchTo().window(tabs.get().get(1));
        Robot robot = new Robot();
        try {
            for (int i = 0; i < 3; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_MINUS);
            }
            BrowserUtils.waitFor(5);
            for (int i = 0; i < 3; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_PLUS);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_PLUS);
            }
        } catch (IllegalArgumentException e) {
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void scrollDoc() throws AWTException {
        BrowserUtils.waitFor(10);
        tabs.set( new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() >= 2) Driver.getDriver().switchTo().window(tabs.get().get(1));
        Robot robot = new Robot();
        try {
            for (int i = 0; i < 7; i++) {
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            }
            BrowserUtils.waitFor(5);
            for (int i = 0; i < 7; i++) {
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
                robot.keyPress(KeyEvent.VK_PAGE_UP);
                robot.keyRelease(KeyEvent.VK_PAGE_UP);
            }
        } catch (IllegalArgumentException e) {
        }
    }

    public void viewHowManyPages() throws AWTException {
        BrowserUtils.waitFor(10);
        tabs.set( new ArrayList<>(Driver.getDriver().getWindowHandles()));
        if (tabs.get().size() > 2) Driver.getDriver().switchTo().window(tabs.get().get(1));
        Robot robot = new Robot();
        for (int i = 0; i < 5; i++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        }
    }

    public static String getAccessToken() throws IOException {
        String token = null;
        Response responseToken = given()
                .contentType("application/json")
                .contentType("application/x-www-form-urlencoded")
                .body("client_id=0GJtlsx4XTpNzAIRIFE82rwkVLqAqcS0" + "&client_secret=IlASqed8mbe7LT35")
                .post("https://api-qa-ext-non-prod.apigee.pcf-maersk.com" + "/mars-ecms-oauth/token" + "?grant_type=client_credentials");
        if (responseToken.getStatusCode() == 200) {
            token = responseToken.jsonPath().get("access_token");
        }
        return token;
    }

    public static byte[] getCallPDFNID(String accessToken, String NID) {
        byte[] responseBody = given()
                .contentType("application/pdf")
                .auth().preemptive().oauth2(accessToken)
                .get("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/" + NID).then()
                .statusCode(200)
                .extract()
                .asByteArray();
        return responseBody;
    }

    public static byte[] getCallPDFNID(String accessToken, String NID, String reqParam) {
        byte[] responseBody = given()
                .contentType("application/pdf")
                .auth().preemptive().oauth2(accessToken)
                .get("https://api-qa-ext-non-prod.apigee.pcf-maersk.com/mars-ecms-bff/ecms/document/contents/notification/" + NID + reqParam).then()
                .statusCode(200)
                .extract()
                .asByteArray();
        return responseBody;
    }

    public void verifyNoteButtons() {
        scrollToElement(inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn);
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNotesSaveBtn.isDisplayed());
        assertTrue(inboundCorrespondencePage.correspondenceDetailsNotesCancelBtn.isDisplayed());
    }

    public void viewCorrespondenceCID(String cid) {
        waitForPageToLoad(7);
        viewCid(cid);
    }

    public void viewCid(String cid) {
        String x = "(//*[text()='" + cid + "'])";
        hover(Driver.getDriver().findElement(By.xpath(x)));
        BrowserUtils.waitFor(3);
        Driver.getDriver().findElement(By.xpath(x)).click();
        BrowserUtils.waitFor(3);
    }

    public void viewVerifyOutboundStatus(String status) {
        waitFor(4);
        Assert.assertTrue(status.equalsIgnoreCase(inboundCorrespondencePage.viewVerifyOutboundStatus()));
    }

    public void viewVerifyOutboundStatusReason(String reason) {
        waitFor(4);
        Assert.assertTrue(reason.equalsIgnoreCase(inboundCorrespondencePage.viewVerifyOutboundStatusReason()));
    }

    public void verifyActionDropdownContainsValues(List<String> values, String status) {
        waitFor(3);
        List<String> actualDropdownValues = new ArrayList<>();
        for (String value : values) {
            for (int i = 0; i < correspondenceDetailsPage.listOfStatusNotifications.size(); i++) {
                if (correspondenceDetailsPage.listOfStatusNotifications.get(i).getText().equals(status)) {
                    correspondenceDetailsPage.listOfBurgerOptions.get(i).click();
                    actualDropdownValues = getElementsText(viewOutboundCorrespondenceDetailsPage.notiActionDropValues);
                    break;
                }
            }
            Assert.assertTrue(actualDropdownValues.contains(value));
        }
    }

    public void verifySystemUpdatesNotificationAndCascadeCorForGivenStatus(String status) {
        waitFor(3);
        Assert.assertEquals(correspondenceDetailsPage.correspondenceNotificationsStatus.getText(), status);
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(correspondenceDetailsPage.correspondenceNotificationsStatusReason));
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusDateNotifications.get(0).getText(), BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString()));
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText(), status);
        if (status.equalsIgnoreCase("sent")) {
            Assert.assertEquals(correspondenceDetailsPage.statusReasonValue.getText(), "");
        }
        Assert.assertTrue(correspondenceDetailsPage.statusDateValueTopPortion.getText().startsWith(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())));
        Assert.assertEquals(correspondenceDetailsPage.updatedByValue.getText(), correspondenceDetailsPage.navBarUsername.getText());
        Assert.assertTrue(correspondenceDetailsPage.updatedOnValue.getText().startsWith(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())));
    }

    public void updateAndStoreNotificationStatusDate() {
        uiAutoUitilities.get().clearWithActions(correspondenceDetailsPage.sentStatusDatePickerNotifications);
        String updatedSentDate = BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().minusDays(randomNumberBetweenTwoNumbers(1, 10)).toString());
        uiAutoUitilities.get().sendKeyWithActions(correspondenceDetailsPage.sentStatusDatePickerNotifications, updatedSentDate);
        World.save.get().put("updatedSentDate", updatedSentDate);
    }

    public void verifySystemKeepsStatusDate() {
        Assert.assertEquals(correspondenceDetailsPage.sentStatusDatePickerNotifications.getAttribute("value"), World.save.get().get("updatedSentDate"));
    }

    public void storeNotificationStatusAndData() {
        World.save.get().put("notificationStatusDate", correspondenceDetailsPage.listOfStatusDateNotifications.get(0).getText());
        World.save.get().put("notificationStatus", correspondenceDetailsPage.listOfStatusNotifications.get(0).getText());
    }

    public void verifyNotificationStatusAndDate() {
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusDateNotifications.get(0).getText(), World.save.get().get("notificationStatusDate"));
        Assert.assertEquals(correspondenceDetailsPage.listOfStatusNotifications.get(0).getText(), World.save.get().get("notificationStatus"));
    }

    public void verifyStatusDateDisplayed(String format) {
        waitFor(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Assert.assertEquals(caseAndContactDetailsPage.statusDateColumn.get(0).getText(), formatter.format(LocalDate.now()));
    }

    public void verifyHeaderOfViewImage(String headerText) {
        waitFor(4);
        String actualHeaderText = Driver.getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertTrue(actualHeaderText.contains(headerText),"Header of the image is not matched.Expected " + headerText + ".But found " + actualHeaderText);
    }

    public void verifyBodyOfHTMLViewImageContains(String text) {
        waitFor(2);
        WebElement bodyElement = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
        Assert.assertTrue(quickIsDisplayed(bodyElement), text + " is not found in body of the HTML image");
    }

    public void switchToOpenedWindow(String currentWindowHandle) {
        waitForPageToLoad(7);
        setOftabs.set(new LinkedHashSet<>(Driver.getDriver().getWindowHandles()));
        for (String sessionID:
                setOftabs.get()) {
            if(!currentWindowHandle.equalsIgnoreCase(sessionID)){
                Driver.getDriver().switchTo().window(sessionID);
            }
        }
    }

    public void verifyTwilioTemplate(String text) {
        String actualText = Driver.getDriver().findElement(By.xpath("//pre")).getText();

        if(text.equalsIgnoreCase("1600")){
            Assert.assertTrue(actualText.length()==1600,"Template Length is not 1600 character");
            Assert.assertTrue(actualText.substring(1599).equalsIgnoreCase("?"));
        }
        else {
            Assert.assertEquals(actualText, text, "Twilio Template Actual Text is mismatched");
        }
    }

    public void verifyErrorCodeOfRetrieveTemplateEndPoint(String templateType, int expectedStatusCode) {
        String notificationId = "";
        String api = api = apiDmsToken + retrieveTemplateEndPoint;
        Boolean bool = false;
        String mail = "";
        String email = "";
        String text = "";
        synchronized (getCorrespondenceResponse){
            getCorrespondenceResponse.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()))));
        }
        List<Map<String, String>> list = getCorrespondenceResponse.get().getList("recipients[0].notifications");
        for (Map<String, String> notifiations : list) {
            switch (notifiations.get("channelType").toUpperCase()) {
                case "MAIL":
                    mail = notifiations.get("notificationId");
                    break;
                case "EMAIL":
                    email = notifiations.get("notificationId");
                    break;
                case "TEXT":
                    text = notifiations.get("notificationId");
                    break;
            }
        }
            switch (templateType.toUpperCase()) {
                case "TEXT":
                    notificationId = text;
                    break;
                case "EMAIL":
                    notificationId = email;
                    break;
                case "MAIL":
                    notificationId = mail;
                    break;
        }
        int actualStatusCode =  apiAutoUtilities.getAPI(api, notificationId).statusCode();
        Assert.assertTrue(actualStatusCode == expectedStatusCode , "Expected statusCode mismatched.Expected " + expectedStatusCode + ", but found " + actualStatusCode );
    }
}