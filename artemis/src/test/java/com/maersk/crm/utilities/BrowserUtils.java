package com.maersk.crm.utilities;

import com.github.javafaker.Faker;
import com.maersk.crm.beans.CreateAContactBean;
import com.maersk.crm.pages.LoginPage;
import com.maersk.crm.pages.tm.TMListOfProjectsPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
//import org.assertj.core.api.SoftAssertions;

public class BrowserUtils {
//    private WebDriver driver;
    protected static Map<String, String> tdm = new HashMap<String, String>();
    public static final ThreadLocal<String> trimURL = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> replaceTrimURL= ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<SoftAssertions> sa = ThreadLocal.withInitial(SoftAssertions::new);

//    public BrowserUtils() {
//
//        PageFactory.initElements(Driver.getDriver(), this);
//    }


    private LoginPage loginPage = new LoginPage();
    public TMListOfProjectsPage tmListOfProjectsPage = new TMListOfProjectsPage();
    TMProjectDetailsPage TMProjectDetailsPage = new TMProjectDetailsPage();
    //    Logger logger = Logger.getLogger(BrowserUtils.class);
    public static final String UINAMESURL = "https://uinames.com/api/?ext";

    public void logout() {
        Driver.getDriver().get("https://mars-crm-ui-qa.apps.non-prod.pcf-maersk.com");
    }

    public void scrollDown() {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("scrollTo(0, 500);");
    }


    public void scrollUp() {
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }

    public void scrollUpRobot() {

        try {
            Robot robot = new Robot();
            Driver.getDriver().findElement(By.xpath("//body")).click();
            for (int k = 0; k < 2; k++) {
                waitFor(1);
                robot.keyPress(KeyEvent.VK_PAGE_UP);
                robot.keyRelease(KeyEvent.VK_PAGE_UP);
                waitFor(1);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void scrollToTop() {
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("scroll(350, 0)");
    }


    //refactoring 10-19-18
    public void scrollToElement(WebElement element) {
        ThreadLocal<WebElement> webElementThreadLocal = ThreadLocal.withInitial(() -> element);
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("arguments[0].scrollIntoView()", webElementThreadLocal.get());
    }

    public void clearJavaScriptType(WebElement element, String text) {
        scrollToElement(element);
        waitForClickablility(element, 20);

        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("arguments[0].value = arguments[1]", element, text);
    }

    public List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<java.lang.String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }

        return elemTexts;
    }

    public WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        waitFor(2);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement fluentWait(final WebElement webElement, int timeinsec) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(timeinsec))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        return wait.until(driver1 -> webElement);
    }

    public void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) Driver.getDriver()).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    public List<WebElement> getDynamicWebElements(String givenXpath, String oldString, String newString) {
        return Driver.getDriver().findElements(By.xpath(givenXpath.replace(oldString, newString)));
    }

    public WebElement getDynamicWebElement(String givenXpath, String oldString, String newString) {
        return Driver.getDriver().findElement(By.xpath(givenXpath.replace(oldString, newString)));
    }

    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    public void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        System.out.println(element.getText());
        actions.moveToElement(element).perform();
    }

    public List<String> getElementsText(List<WebElement> list) {
        List<String> elemText = new ArrayList<>();
        for (WebElement el : list) {
            waitFor(1);
            if (!el.getText().isEmpty()) {
                elemText.add(el.getText());
            }

        }
        return elemText;
    }

    public void scrollUpUsingActions(int iterations) {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        Actions actions = new Actions(Driver.getDriver());
        for (int index = 0; index < iterations; index++) {
            actions.sendKeys(Keys.PAGE_UP).build().perform();
        }
    }

    public static void scrollRight() {
        try {
            Robot robot = new Robot();
            for (int k = 0; k < 2; k++) {
                waitFor(1);
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                waitFor(1);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void scrollDownUsingActions(int iterations) {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        Actions actions = new Actions(Driver.getDriver());
        for (int index = 0; index < iterations; index++) {
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        }
    }

    public static void expandIfNecesary(WebElement element, String attribute, String valueWhenHidden) {
        if (element.getAttribute(attribute).equals(valueWhenHidden)) {
            element.click();
        }
    }

    public static String filterForValidSSN(String ssn) {
        String result = "";
        Pattern pattern = Pattern.compile("[0-9][0-9,-]*-[0-9,-]*[0-9]");
        Matcher matcher = pattern.matcher(ssn);
        while (matcher.find()) {
            result += ssn.substring(matcher.start(), matcher.end());
        }
        return result;
    }

    public static String filterForValidDob(String dob) {
        String result = "";
        Pattern pattern = Pattern.compile("[0-9][0-9,/]*/[0-9,/]*[0-9]");
        Matcher matcher = pattern.matcher(dob);
        while (matcher.find()) {
            result += dob.substring(matcher.start(), matcher.end());
        }
        return result;
    }

    public static String filterTextFor(String validnumbersOrLettersOnly) {
        /*
        returned result includes an empty space in between each match, but trims the ends
         */
        String result = "";
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(validnumbersOrLettersOnly);
        while (matcher.find()) {
            result += validnumbersOrLettersOnly.substring(matcher.start(), matcher.end()) + " ";
        }
        return result.trim();
    }

    public static String filterForLettersOnly(String text) {
        String result = "";
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                result += String.valueOf(c);
            }
        }
        return result;
    }

    /*
     * This method is created by Shilpa
     * */
    public void highLightElement(WebElement element) {
        staticWait(100);
//        logger.info("Started Executing the Java Script ");
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');", element);
        try {
            Thread.sleep(1000);
//            logger.info("Executed and HighLighted");
        } catch (InterruptedException e) {
            System.out.print(e.getMessage());
        }

    }

    public void clearAndFillText(WebElement element, String text) {
        ThreadLocal<WebElement> webElementThreadLocal = ThreadLocal.withInitial(() -> element);
        ThreadLocal<String> textThreadlocal = ThreadLocal.withInitial(() -> text);
        try {
            waitFor(1);
            if (webElementThreadLocal.get().isDisplayed()) {
                try {
                    webElementThreadLocal.get().click();
                } catch (Exception e) {
                    JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
                    executor.executeScript("arguments[0].click();", webElementThreadLocal);
                }
                staticWait(300);
//        if (Driver.getDriver() instanceof FirefoxDriver||(System.getProperty("browser").equalsIgnoreCase("container-firefox"))) {
//            element.clear();
//            staticWait(300);
//            element.sendKeys(text);
//            return;
//        }
                webElementThreadLocal.get().clear();
                staticWait(300);
                webElementThreadLocal.get().sendKeys(Keys.chord(Keys.CONTROL, "a"), (textThreadlocal.get()));
            }
        } catch (Exception e) {
            System.out.println("Got into exception - " + e.getMessage());
        }
    }

    public void clearAndFillDate(WebElement element, String text) {
        try {
            if (element.isDisplayed()) {
                try {
                    element.click();
                } catch (Exception e) {
                    JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
                    executor.executeScript("arguments[0].click();", element);
                }
                staticWait(300);
                element.clear();
                staticWait(1000);
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"), (text));
                element.sendKeys(Keys.chord(Keys.TAB));
            }
        } catch (Exception e) {
            System.out.println("Got into exception - " + e.getMessage());
        }
    }

    /*
    this is duplicate of line 102 <public static void wait(){}>/
     */
    public static void staticWait(int timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String randomEmailId() {
        String n = UUID.randomUUID().toString().substring(30);
        String s = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()).toString().replaceAll(":", "").
                replaceAll("", "").replaceAll("-", "").replace(".", "")
                .substring(2);
        String email = "test" + n + s + "@gmail.com";
        return email;
    }

    public void login(String userName, String password) {
        clearAndFillText(loginPage.userName, userName);
        clearAndFillText(loginPage.password, password);
        loginPage.tmLoginButton.click();

    }

    /*  Added changes for IDP discovery authentication
     * */
    public void loginCRM(String userName, String password, String projectName) {
        System.out.println(projectName);
        System.out.println(userName);
        waitFor(2);
        try {
//            fillTheFiled(loginPage.userNameCRM, userName);
//            fillTheFiled(loginPage.passwordCRM, password);
//            loginPage.submitButton.click();
            clearAndFillText(loginPage.loginUserNameOrEmail, userName);
            waitFor(1);
            loginPage.nextBtn.click();
            clearAndFillText(loginPage.passwordCRM, password);
            waitFor(1);
            loginPage.signInBtn.click();
            waitForVisibility(loginPage.projectId, 5);
            selectDropDown(loginPage.projectId, projectName);
            waitFor(4);
            click(loginPage.continueBtn);
            //added wait here to give sometime for UI to parse the response before making config api request.
            waitFor(8);
            waitForPageToLoad(10);
            click(loginPage.acceptAndContinueBtn);
            //waitForVisibility(loginPage.permissionDropdown,10);
            waitForVisibility(loginPage.userIcon, 10);
            //Handling CTI pop-up here
            if (userName.equals("SVC_mars_tester_3")) {
                String mainWindow = Driver.getDriver().getWindowHandle();
                //Wait for one login page to open
                waitFor(8);
                Set<String> s1 = Driver.getDriver().getWindowHandles();
                System.out.println(s1.size());
                Iterator<String> i1 = s1.iterator();
                while (i1.hasNext()) {
                    String ChildWindow = i1.next();
                    if (!mainWindow.equalsIgnoreCase(ChildWindow)) {
                        Driver.getDriver().switchTo().window(ChildWindow);
                        //Write code here to login to telephonyWidget
                        Driver.getDriver().close();
                    }
                }
                Driver.getDriver().switchTo().window(mainWindow);
            }
            //waitForVisibility(loginPage.permissionDropdown, 5);
            waitForVisibility(loginPage.userIcon, 12);
        } catch (Exception e) {
//            waitFor(5);
            click(loginPage.wrngMsgOkBtn);
            selectDropDown(loginPage.projectId, projectName);
            click(loginPage.continueBtn);
            //added wait here to give sometime for UI to parse the response before making config api request.
            waitFor(3);
            click(loginPage.acceptAndContinueBtn);
        }
    }

    public static String getCurrentDateWithFormat() {
        Date currentDate = new Date();
        String DATE_FORMAT = "MMddyyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        return actualDate;

    }

    public String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String actualdate = dateFormat.format(cal);
        return actualdate;
    }

    //returns date time with yyyy-mm-ddThh:mm:00.000000+00:00 format
    public String getDateTimeWithTFormat() {
        return getCurrentDateInYearFormat() + "T" + getCurrentSystemDateTime().substring(11) + ":00.000000+00:00";
    }

    public String getCurrentDateInYearFormat() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String actualdate = simpleDateFormat.format(new Date());
        System.out.println(actualdate);
        return actualdate;
    }

    public static String getGreaterDateInYearFormat(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String actualDate = dateAfterNDays.format(formatter);
        return actualDate;
    }

    public static String getPriorDateInYearFormat(int nDays) {
        LocalDate datePriorNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String actualDate = datePriorNDays.format(formatter);
        return actualDate;
    }

    public static String getGreaterDate(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String actualDate = dateAfterNDays.format(formatter);
        return actualDate;
    }


    public static String getPriorDate(int nDays) {
        LocalDate datePriorNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String actualDate = datePriorNDays.format(formatter);
        return actualDate;
    }

    public static String getFutureDate(int nDays) {
        LocalDate dateFutureNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String actualDate = dateFutureNDays.format(formatter);
        return actualDate;
    }

    public String getCurrentDate() {
        Date currentDate = new Date();
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        return actualDate;

    }

    public static String getFutureDateInFormat(String nDays) {
        LocalDate dateFutureNDays = LocalDate.now().plusDays(Integer.parseInt(nDays.replace("+", "")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String actualDate = dateFutureNDays.format(formatter);
        return actualDate;
    }


    public String getFutureYearWithCurrentdate(int year) {
        String DATE_FORMAT = "MMddyyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return sdf.format(cal.getTime());

    }

    public static String getPriorDateyyyyMMdd(int nDays) {
        LocalDate datePriorNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String actualDate = datePriorNDays.format(formatter);
        return actualDate;
    }

    public static String getFutureDateyyyyMMdd(int nDays) {
        LocalDate dateFutureNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String actualDate = dateFutureNDays.format(formatter);
        return actualDate;
    }


    public void createAndSave(String projectName, String programName, String contractId, String clientName) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        TMProjectDetailsPage.state.click();
        hover(TMProjectDetailsPage.state);
        TMProjectDetailsPage.AR.click();
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);
        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        TMProjectDetailsPage.activeStatus.click();
        System.out.print("Clicked");
        //staticWait(5000);
        hover(TMProjectDetailsPage.saveButton);
        highLightElement(TMProjectDetailsPage.saveButton);
        TMProjectDetailsPage.saveButton.click();
        highLightElement(TMProjectDetailsPage.saveButton);
    }

    //refactorBy: Vidya 1-31-2020
    public void addContractDateAndSaveCurrentDate(String projectName, String programName, String contractId, String
            clientName, String startDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        TMProjectDetailsPage.state.click();
        hover(TMProjectDetailsPage.state);
        TMProjectDetailsPage.AR.click();
        waitFor(2);
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);
        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(TMProjectDetailsPage.contractEndDate, startDate);
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        TMProjectDetailsPage.activeStatus.click();
        waitFor(1);
        TMProjectDetailsPage.saveButton.click();
        /*//staticWait(5000);
        hover(TMProjectDetailsPage.saveButton);
        highLightElement(TMProjectDetailsPage.saveButton);
        TMProjectDetailsPage.saveButton.click();
        highLightElement(TMProjectDetailsPage.saveButton);*/
    }

    public void addContractDateAndSaveCurrentDateAndEndDate(String projectName, String programName, String
            contractId, String clientName, String startDate, String endDate) {
        clearAndFillText(TMProjectDetailsPage.projectName, projectName);
        TMProjectDetailsPage.state.click();
        hover(TMProjectDetailsPage.state);
        TMProjectDetailsPage.AR.click();
        clearAndFillText(TMProjectDetailsPage.programName, programName);
        clearAndFillText(TMProjectDetailsPage.contractId, contractId);
        clearAndFillText(TMProjectDetailsPage.stateAgencyName, clientName);
        clearAndFillText(TMProjectDetailsPage.contractStartDate, startDate);
        clearAndFillText(TMProjectDetailsPage.contractEndDate, endDate);
        TMProjectDetailsPage.provisioningStatus.click();
        hover(TMProjectDetailsPage.provisioningStatus);
        TMProjectDetailsPage.activeStatus.click();
        System.out.print("Clicked");
        //staticWait(5000);
        hover(TMProjectDetailsPage.saveButton);
        highLightElement(TMProjectDetailsPage.saveButton);
        TMProjectDetailsPage.saveButton.click();
        highLightElement(TMProjectDetailsPage.saveButton);
    }

    public void search(String projectName, String programName) {
        //clearAndFillText(tenantManagerListOfProjectsPage.state,state);
        clearAndFillText(tmListOfProjectsPage.project, projectName);
        clearAndFillText(tmListOfProjectsPage.programName, programName);
        staticWait(1000);
        hover(tmListOfProjectsPage.search);
        tmListOfProjectsPage.search.click();
        staticWait(100);

    }

    public void selectSearchResults() {
        highLightElement(tmListOfProjectsPage.elementSearchResults);
        //tenantManagerListOfProjectsPage.elementSearchResults.click();
        tmListOfProjectsPage.arrowClick.click();
    }

    public void getStateNames() {
        TMProjectDetailsPage.state.click();
        hover(TMProjectDetailsPage.state);
        List<WebElement> getStates = TMProjectDetailsPage.stateList;
        getStates.size();
        System.out.print("size is " + getStates.size());
        for (int i = 1; i < getStates.size(); i++) {
            System.out.println(getStates.get(i).getText());
        }
        TMProjectDetailsPage.AR.click();

    }

    public void createProjectContactAndSave(String firstName, String middleName, String lastName, String
            phoneNumber, String email) {
        highLightElement(TMProjectDetailsPage.role);
        hover(TMProjectDetailsPage.role);
        TMProjectDetailsPage.accountApprover.click();
        clearAndFillText(TMProjectDetailsPage.firstName, firstName);
        clearAndFillText(TMProjectDetailsPage.middleName, middleName);
        clearAndFillText(TMProjectDetailsPage.lastName, lastName);
        clearAndFillText(TMProjectDetailsPage.phoneNumber, phoneNumber);
        clearAndFillText(TMProjectDetailsPage.email, email);
        TMProjectDetailsPage.projectContactSave.click();

    }

    public static int radomNumber(int range) {
        Random ran = new Random();
        int ranNumb = ran.nextInt(range - 1) + 1;
        if (ranNumb == 0) {
            return ranNumb + 1;
        } else if (ranNumb == range) {
            return ranNumb - 1;
        } else {
            return ranNumb;
        }
    }

    /*
     * Author:Shilpa P
     * @return this method returns the random number from range 1-500
     *
     * */

    public int randomNumberGenartor() {
        Random rand = new Random();
        int randomValue = rand.nextInt(500);
        return randomValue;

    }


    /*this method asserts dropdown element being displayed and selected*/
    public void selectDropDown(WebElement element, String selector) {
        // for some reason Project Page is loading something, can't find
        // Solution. It waits about 1 sec and then starts selection process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hover(element);
        element.click();
        staticWait(900);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
        // browserUtils.hover(element);
        //Assert.assertEquals(element.getText(), selector, "Selector " + selector + " - wasn't selected");
        String selected = element.getText().isEmpty() ? element.getAttribute("value") : element.getText();
        System.out.println("selected = " + selected);
        Assert.assertTrue(selected.contains(selector), "Selector " + selector + " - wasn't selected");

    }

    public void selectDropDownWithText(WebElement element, String selector) {
        // for some reason Project Page is loading something, can't find
        // Solution. It waits about 1 sec and then starts selection process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hover(element);
        element.click();
        staticWait(900);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[text()='"+ selector + "']"));
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
        String selected = element.getText().isEmpty() ? element.getAttribute("value") : element.getText();
        System.out.println("selected = " + selected);
        Assert.assertTrue(selected.contains(selector), "Selector " + selector + " - wasn't selected");

    }

    /*this method selects dropdown element being provided*/
    public void selectDropDownNoCheck(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
    }

    /*This method selects value from Account Inactivation reason dropdown*/

    public void selectInactivateReason(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[@data-value='" + selector + "']"));
        scrollToElement(single);
        hover(single);
        single.click();
        hover(element);
        Assert.assertEquals(element.getText(), selector, "Selector " + selector + " - wasn't selected");
    }

    public static void sendKeyToTextField(WebElement element, String value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    /*this method returns present local time in 12 hours <hh:mm a> format. Exp: 03:58 pm*/
    public static String timeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime localTimeNow = LocalTime.now();
//        if (Driver.getDriver() instanceof ChromeOptions ) {
        localTimeNow = LocalTime.now().plusHours(5).plusMinutes(1);
//        }
        return dtf.format(localTimeNow);
    }

    /*this method returns a String of specific length*/
    public static String createTextString(int size) {
        StringBuilder text = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            text.append("abc!1234/'][- ");
            if (text.length() >= size) {
                break;
            }
        }
        return text.toString();
    }
    /*this method returns a string with all non Alphabetical symbols */

    public static String mostSymbolText() {
        StringBuilder text = new StringBuilder().append("3!@#$%H%^&*()_+-=e[{]}]\\|;:'").append("\"").append("r<>?,.+/mione");
        return text.toString();
    }

    /*this method asserts that there is no specific WebElement on the page */

    public boolean textIsNotPresent(String text) {
        boolean notPresent = false;
        try {
            Driver.getDriver().findElement(By.xpath("//*[contains(text(), '" + text + "')]"));
        } catch (NoSuchElementException e) {
            notPresent = true;
        }
        return notPresent;
    }

    public boolean textIsNotPresent2(String locator, int index) {
        boolean notPresent = false;
        try {
            Driver.getDriver().findElement(By.xpath("(//*[contains(text(), '" + locator + "')])[index]"));
        } catch (NoSuchElementException e) {
            notPresent = true;
        }
        return notPresent;
    }

    /*this method checks the text consists alphabetical symbols and space */
    public static boolean hasOnlyLettersSpaces(String text) {
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(text);
        boolean b = m.matches();
        return b;
    }

    public boolean stateDropdownHasAll(WebElement element) {
        boolean present = false;
        List<String> allStates = Arrays.asList("District of Columbia", "Arizona", "Alabama", "Alaska", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
                "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
                "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");
        List<String> states = new ArrayList<>();

        states.addAll(allStates);
        for (String state : states) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.click();
            // browserUtils.hover(element);
            waitFor(1);
            WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + state + "')]"));
            scrollToElement(single);
            present = (single.getText().equals(state));
            hover(single);
            single.click();
        }
        return present;
    }

    public static boolean hasOnlyDigits(String text) {
        text = text.replace("-", "");
        boolean isaDigit = true;

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (!Character.isDigit(character)) {
                isaDigit = false;
                break;
            }
        }
        return isaDigit;
    }

    public static boolean isAlphanumeric(String text) {
        boolean alphanumeric;
        if (text.matches("[A-Za-z0-9]+")) {

            return true;

        }
        return false;

    }

    public static Map getUINameForData() {

        Response response1 =
                given().accept(ContentType.JSON)
                        .and().params("amount", 1, "region", "United States")
                        .when().get(UINAMESURL);
        return response1.body().as(Map.class);
    }

    /**
     * this method gets and stores a new set of test data by api call to uiname.com
     */
    public static Map getNewTestData() {

        Response response2 =
                given().accept(ContentType.JSON)
                        .and().params("amount", 1, "region", "United States")
                        .when().get(UINAMESURL);
        JsonPath json = response2.jsonPath();
//       firstName = json.getString("name");
//       lastName = json.getString("surname");
//       ssn = json.getString("birthday.raw");
//       dateOfBirth =json.getString("birthday.mdy");
//       phoneNumber = json.getString("number");
//       gender = json.getString("gender");
//       email = json.getString("email");
//       zipCode = json.getString("number");
        assertEquals(response2.statusCode(), 200);
//       assertTrue(firstName!=null);
        return response2.body().as(Map.class);
    }

    public static Map getNewTestData2() {
        Map<String, Object> data = new HashMap<>();
        Faker faker = new Faker();
        Object name = faker.name().firstName();
        Object surname = faker.name().lastName();
        if (name.toString().length() > 10) {
            data.put("name", ((String) name).substring(0, 10) + RandomStringUtils.random(5, true, false));
        } else {
            data.put("name", name + RandomStringUtils.random(5, true, false));
        }
        if (surname.toString().length() > 10) {
            data.put("surname", ((String) surname).substring(0, 10) + RandomStringUtils.random(5, true, false));
        } else {
            data.put("surname", surname + RandomStringUtils.random(5, true, false));
        }
//        data.put("name",faker.name().firstName()+RandomStringUtils.random(5, true, false));
//        data.put("surname",faker.name().lastName()+RandomStringUtils.random(5, true, false));
        data.put("email", RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(3, true, true) + ".com");
        data.put("birthday", convertMilliSecondsToDateFormatMMddyyyy(LocalDateTime.now().minusYears(faker.random().nextInt(18, 65)).atZone(ZoneId.of("US/Eastern")).toInstant().toEpochMilli()));//String
        data.put("ssn", RandomStringUtils.random(9, false, true));
        data.put("phone", RandomStringUtils.random(10, false, true));
        data.put("zip", RandomStringUtils.random(5, false, true));
        data.put("externalId", RandomStringUtils.random(10, false, true));
        data.put("gender", System.currentTimeMillis() % 2 == 0 ? CreateAContactBean.Gender.MALE.getGender() : CreateAContactBean.Gender.FEMALE.getGender());
        return data;
    }

    public static String convertMilliSecondsToDateFormatMMddyyyy(long milliseconds) {
        DateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
        return simple.format(new Date(milliseconds));
    }


    /*
     * Author:Shruti
     * This method is used to verify that an element is not displayed
     * */
    public boolean IsElementDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    public static int randomNumberBetweenTwoNumbers(int low, int high) {
        Random ran = new Random();
        if (high == 1) {
            return high;
        }
        int ranNumb = ran.nextInt((high - low) + 1) + low;
        return ranNumb;
    }


    //@author Sean Thorson
    public static String generateRandomSpecialrChars2(int a) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz-";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }


    public static String generateRandomSpecialrChars(int a) {
        String candidateChars = "!@#$%^&*()/";
        int length = a;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }


    /*
     * Author:Shilpa P
     * This method is used to generate Random  Strings  with Number combination
     * @returns the String
     * */
    public static String generateRandomNumberChars() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int length = 17;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static String generateRandomNumberChars(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    //Author : Mital - 01/25/2023
    public static String generateRandomNumberOfLength(int length) {
        String candidateChars = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    /*
     * Author:Shilpa P
     * This method is used to generate Random  Strings  with Number combination
     * @returns the String
     * */
    public static String generateRandomCharacters() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = 17;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    //refactoring 10/23/18
    //12/14/2018 commented 697-705;- additional steps added to method, which were not required
    //This method is used for the specific function and can not be modified. Please make a copy of it and adjust that copy according to the fanctionality you are automating.
    public void singleSelectFromMultipleOptionDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
        hover(element);
        Assert.assertEquals(element.getText(), selector, "Selector " + selector + " - wasn't selected");
        waitFor(1);
        element.click();
        waitFor(1);
        single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        waitFor(1);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }

    /*
     * Author:Shruti
     * This method is used to compare hash maps
     * @returns the String
     * */
    public HashMap<String, String> getEmailIdsWithStatus() {
        List<WebElement> emailIDRows = Driver.getDriver().findElements(By.xpath("(//*[contains(@class , 'mdl-js-data-table mx-table table mt-4')])[3]//tr[*]//td[3]"));
        int numberofRowsInEmailIDSection = emailIDRows.size();
        HashMap<String, String> emailIDsWithStatus = new HashMap<String, String>();
        for (int i = 1; i <= numberofRowsInEmailIDSection; i++) {
            int counter = 1;
            emailIDsWithStatus.put(Driver.getDriver().findElement(By.xpath("(//*[contains(@class , 'mdl-js-data-table mx-table table mt-4')])[3]//tr[" + i + "]//td[3]")).getText(),
                    counter + Driver.getDriver().findElement(By.xpath("(//*[contains(@class , 'mdl-js-data-table mx-table table mt-4')])[3]//tr[" + i + "]//td[3]")).getText());
            i++;
            counter++;
        }
        return emailIDsWithStatus;
    }

    public boolean compareHashMaps(HashMap<String, String> mapA, HashMap<String, String> mapB) {
        try {
            for (String k : mapB.keySet()) {
                System.out.println("mapA" + mapA.get(k) + "and the value of the key is " + k);
                System.out.println("mapB" + mapB.get(k) + "and the value of the key is " + k);
                if (!mapA.get(k).equals(mapB.get(k))) {

                }
            }
            for (String y : mapA.keySet()) {
                if (!mapB.containsKey(y)) {
                    return false;
                }
            }
        } catch (NullPointerException np) {
            return false;
        }
        return true;

    }

    public String getEmailStatus(String emailID) {
        return Driver.getDriver().findElement(By.xpath("//td[.='" + emailID + "']/following-sibling::td[2]")).getText();
    }

    public void clickOnFirstEmaiId() {
        Driver.getDriver().findElement(By.xpath("(//th[contains(text(),'EMAIL ADDRESS')]/parent::tr/parent::thead/parent::table/tbody/tr)[2]/td[1]")).click();
    }

    public void updateEmailID(WebElement emailIDField) {
        clearAndFillText(emailIDField, randomEmailId());
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public void click(WebElement ele) {
        waitForVisibility(ele, 20);
        hover(ele);
        ele.click();
    }

    public HashMap<String, String> getHoverText() {
        HashMap<String, String> toolTipData = new HashMap<String, String>();
        for (int i = 1; i <= 2; i++) {
            toolTipData.put(getText(Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[" + i + "]/p"))).trim(),
                    getText(Driver.getDriver().findElement(By.xpath("(//div[@role='tooltip']/div/div/div)[" + i + "]/h6"))));
        }
        return toolTipData;
    }

    /*
     * Author:Shruti
     * Implementing treemap to verify the order of records
     * */
    public TreeMap<String, String> getRecordsOrder() {
        List<WebElement> emailIDRows = Driver.getDriver().findElements(By.xpath("//th[contains(text(),'EMAIL ADDRESS')]/ancestor::table/tbody"));
        int numberofRowsInEmailIDSection = emailIDRows.size();
        TreeMap<String, String> emailIDsWithStatus = new TreeMap<String, String>();
        for (int i = 1; i <= numberofRowsInEmailIDSection; i++) {
            emailIDsWithStatus.put(Driver.getDriver().findElement(By.xpath("//th[contains(text(),'EMAIL ADDRESS')]/ancestor::table/tbody[" + i + "]/tr[1]/td[2]")).getText(), Driver.getDriver().findElement(By.xpath("//th[contains(text(),'EMAIL ADDRESS')]/ancestor::table/tbody[\"+i+\"]/tr[1]/td[3]")).getText());
        }
        return emailIDsWithStatus;
    }
    /*
     * Author:Shruti
     * //This method can be extended for rest of the sections
     * */

    public int getPaginationSize(String section) {
        int size = 0;
        if (section.equals("email"))
            size = Driver.getDriver().findElements(By.xpath("(//ul[@class='pagination'])[3]/li/a")).size();
        return size;
    }

    /*
     * Author:Muhabat
     * this method is used for single select option from any dropdown
     * */
    public void singleSelectFromDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }

    public boolean textIsPresent(String text) {
        return Driver.getDriver().getPageSource().contains(text);

    }

    public static boolean ascendingOrderIDs(List<WebElement> IDs) {
        boolean is = false;
        int topId = 0;
        int nextBelowId = 0;
        for (int i = 0; i < IDs.size(); i++) {
            if (i == IDs.size() - 1) {
                continue;
            } else {
                topId = Integer.parseInt(validNumberFilter(IDs.get(i).getText()));
                nextBelowId = Integer.parseInt(validNumberFilter(IDs.get(i + 1).getText()));
                is = (topId < nextBelowId);
            }
            is = (topId <= nextBelowId);
        }
        return is;
    }

    public static String validNumberFilter(String text) {
        String result = "";
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                result += String.valueOf(c);
            }
        }
        return result;

    }

    public static boolean descendingOrderIDs(List<WebElement> IDs) {
        int topId = 0;
        int nextBelowId = 0;
        for (int i = 0; i <= IDs.size() - 1; i++) {
            topId = Integer.parseInt(validNumberFilter(IDs.get(i).getText()));
            if (i == IDs.size() - 1) {
                continue;
            } else {
                System.out.println("else");
                nextBelowId = Integer.parseInt(validNumberFilter(IDs.get(i + 1).getText()));
            }
        }
        return (topId > nextBelowId);
    }

    public boolean ascendingOrderDates(List<WebElement> dates) {
        boolean is = false;
        String topDate;
        String nextBelowDate;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(dates.size());
        for (int i = 0; i <= dates.size() - 1; i++) {

            topDate = dates.get(i).getText();
            if (i == dates.size() - 1) {
                continue;
            } else {
                nextBelowDate = dates.get(i + 1).getText();
                LocalDate date1 = LocalDate.parse(topDate, sdf);
                LocalDate date2 = LocalDate.parse(nextBelowDate, sdf);
                if (date1.isEqual(date2)) {
                    is = true;
                } else {
                    is = (date1.isAfter(date2));
                }
            }
        }
        return is;
    }

    public boolean descendingOrderDates(List<WebElement> dates) {
        boolean is = false;
        String topDate;
        String nextBelowDate;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i <= dates.size() - 1; i++) {

            topDate = dates.get(i).getText();
            if (i == dates.size() - 1) {
                continue;
            } else {
                nextBelowDate = dates.get(i + 1).getText();
                LocalDate date1 = LocalDate.parse(topDate, sdf);
                LocalDate date2 = LocalDate.parse(nextBelowDate, sdf);
                if (date1.isEqual(date2)) {
                    is = true;
                } else {
                    is = (date1.isBefore(date2));
                }
            }
        }
        return is;
    }

    public boolean ascendingOrderTexts(List<WebElement> texts) {
        boolean is = false;
        String topText;
        String nextBelowText;

        for (int i = 0; i <= texts.size() - 1; i++) {
            if (i + 1 == texts.size()) {
                is = true;
                continue;
            } else {
                topText = texts.get(i).getText();
                nextBelowText = texts.get(i + 1).getText();
                if (topText.equals("") || nextBelowText.equals("")) {
                    is = true;
                    continue;
                } else {
                    if (topText.charAt(0) == nextBelowText.charAt(0)) {
                        is = true;
                    } else {
                        is = (topText.charAt(0) < nextBelowText.charAt(0));
                    }
                }
            }
        }
        return is;
    }

    public boolean descendingOrderTexts(List<WebElement> texts) {
        boolean is = false;
        String topText;
        String nextBelowText;

        for (int i = 0; i <= texts.size() - 1; i++) {
            if (i + 1 == texts.size()) {
                is = true;
                continue;
            } else {
                topText = texts.get(i).getText();
                nextBelowText = texts.get(i + 1).getText();
                if (topText.equals("") || nextBelowText.equals("")) {
                    is = true;
                    continue;
                } else {
                    if (topText.charAt(0) == nextBelowText.charAt(0)) {
                        is = true;
                    } else {
                        is = (topText.charAt(0) > nextBelowText.charAt(0));
                    }
                }
            }
        }
        return is;
    }

    public String getRandomString(int stringLength) {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(stringLength, useLetters, useNumbers);
    }

    public synchronized static String getStaticRandomString(int stringLength) {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(stringLength, useLetters, useNumbers);
    }


    public void clearAndFillText2(WebElement element, String text) {
        element.click();
        element.clear();
        staticWait(500);
        element.sendKeys(text);
    }

    public static boolean isMMddYYYYformat(String text) {
        // System.out.println(text + " TEXT");
        if (text.matches("[01]\\d/[0-3]\\d/\\d{4}"))
            return true;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (Exception ex) {
            return false; //ParseException
        }
    }

    /*
     * Author:Muhabbat
     * this method returns age when input is a date in string format mm/dd/yyyy
     * */
    public int calculateAge(String dob) {
        DateTimeFormatter db = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(dob, db);
        LocalDate today = LocalDate.now();
        Period p = Period.between(localDate, today);
        return p.getYears();
    }

    /*
     * Author:Shilpa P
     * @return this method is used to return the first element from the list
     *
     * */

    public WebElement getFirstElement(List<WebElement> elements) {
        WebElement firstElement = null;
        for (WebElement element : elements) {
            firstElement = element;
            break;
        }
        return firstElement;
    }

    /*
     * Author:Shilpa P
     * @return the is method is used to return the Last  element from the list
     *
     *
     * */
    public WebElement getLastElement(List<WebElement> elements) {
        WebElement lastElement = null;
        int size = elements.size();
        lastElement = elements.get(size - 1);
        return lastElement;

    }


    public String searchAnyElementInList(List<WebElement> elements, String text) {
        int capturedText;
        List<String> elementTextValue;
        elementTextValue = getElementsText(elements);
        Collections.sort(elementTextValue);
        capturedText = Collections.binarySearch(elementTextValue, text);
        return elementTextValue.get(capturedText);

    }

    public String getRandomNumber(int numberLength) {
        boolean useLetters = false;
        boolean useNumbers = true;
        return String.valueOf(RandomStringUtils.random(numberLength, useLetters, useNumbers));
    }

    public synchronized static String getStaticRandomNumber(int numberLength) {
        boolean useLetters = false;
        boolean useNumbers = true;
        return String.valueOf(RandomStringUtils.random(numberLength, useLetters, useNumbers));
    }

    public Date convertStringToDate(String dateString) {
        Date date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = (Date) df.parse(dateString);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return date;
    }

    public boolean ascendingOrderDatesString(List<String> dates) {
        boolean is = false;
        String topDate;
        String nextBelowDate;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (int i = 0; i <= dates.size() - 1; i++) {

            topDate = dates.get(i);
            if (i == dates.size() - 1) {
                continue;
            } else {
                nextBelowDate = dates.get(i + 1);
                LocalDate date1 = LocalDate.parse(topDate, sdf);
                LocalDate date2 = LocalDate.parse(nextBelowDate, sdf);
                if (date1.isEqual(date2)) {
                    is = true;
                } else {
                    is = (date1.isBefore(date2));
                }
            }
        }

        return is;
    }

    public boolean descendingOrderDatesString(List<String> dates) {
        boolean is = false;
        String topDate;
        String nextBelowDate;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i <= dates.size() - 1; i++) {
            if (dates.get(i).isEmpty()) i++;
            topDate = dates.get(i);
            if (i == dates.size() - 1) {
                continue;
            } else {
                nextBelowDate = dates.get(i + 1);
                LocalDate date1 = LocalDate.parse(topDate, sdf);
                LocalDate date2 = LocalDate.parse(nextBelowDate, sdf);
                if (date1.isEqual(date2)) {
                    System.out.println(date1 + " And " + date2);
                    is = true;
                } else {
                    is = (date1.isAfter(date2));
                    System.out.println(date1 + " And " + date2);
                }
            }
        }
        return is;
    }

    public void selectOptionFromMultiSelectDropDown(WebElement element, String selector) {
        waitFor(1);
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[@data-value= '" + selector + "']"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }

    public void selectOptionFromMultiSelectDropDownNew(WebElement element, String selector) {
        waitFor(1);
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[text()= '" + selector + "']"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }
    public void selectFromMultiSelectDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
    }

    public void selectRandomOptionFromMultiSelectDropDown(WebElement element) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        int range = 0;
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        range = listElements.size();
        int random = randomNumberBetweenTwoNumbers(2, range);
        hover(element);
        WebElement single = (listElements.get(random - 1));
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);

    }

    public void selectRandomOptionFromMultiSelectDropDownWithoutEscape(WebElement element) {
        staticWait(1000);
        element.click();
        staticWait(500);
        int range = 0;
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        range = listElements.size();
        int random = randomNumberBetweenTwoNumbers(2, range);
        hover(element);
        WebElement single = (listElements.get(random - 1));
        hover(single);
        single.click();
    }

    /*
     * Author:Shruti
     * This method is used to verify that an element is not displayed
     * */
    public boolean isElementDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Author:Shruti
     * 02-04-2019
     * This method is used to click an elment in case that element is not visible
     * */
    public void jsClick(WebElement element) {
        try {
            waitFor(2);
            JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
            executor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println(" Came to exception on jsClick - " + e.getMessage());
            System.out.println(" Trying to click using webdriver ");
            element.click();
        }
    }

    public String getTextFromInputField(WebElement element) {
        return element.getAttribute("value");
    }

    public void clearAndFillTextWithActionClass(WebElement element, String text) {
        Actions a = new Actions(Driver.getDriver());
        a.moveToElement(element);
        a.click();
        a.sendKeys(element, text);
        a.build().perform();
    }

    public static String getCurrentSystemDateTime() {

        String DATE_FORMAT = "MM/dd/yyyy HH:mm";
        long timeNow = System.currentTimeMillis();
        Date currentDate = new Date(timeNow);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        return actualDate;

    }

    public static String getCurrentSystemDate() {

        String DATE_FORMAT = "yyyy-MM-dd";
        long dateNow = System.currentTimeMillis();
        Date currentDate = new Date(dateNow);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String actualDate = sdf.format(currentDate);
        return actualDate;

    }


    public static String getPriorDateAPIyyyyMMdd(int nDays) {
        LocalDate datePriorNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String actualDate = datePriorNDays.format(formatter);
        return actualDate;
    }


    public static String getFutureDateAPIyyyyMMdd(int nDays) {
        LocalDate dateFutureNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String actualDate = dateFutureNDays.format(formatter);
        return actualDate;
    }

    public static String getPriorYearAPIyyyyMMdd(int nYears, int nDays) {
        LocalDate datePriorNDays = LocalDate.now().minusYears(nYears).plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String actualDate = datePriorNDays.format(formatter);
        return actualDate;
    }

    /**
     * @Muhabbat this method returns system time of the execution environment
     */
    public static String systemTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime localTimeNow = LocalTime.now();
        return dtf.format(localTimeNow);
    }


    public boolean quickIsDisplayed(WebElement element) {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        try {
//            Driver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            if (element.isDisplayed()) return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }finally {
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
        return false;
    }

    /*this method asserts dropdown element being displayed and selected*/
    public void selectDropDownByIndex(WebElement element, List<WebElement> listElementsArg, int index) {
        // for some reason Project Page is loading something, can't find
        // Solution. It waits about 1 sec and then starts selection process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        int range = 0;
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        range = listElements.size();
        int random = randomNumberBetweenTwoNumbers(2, range);
        hover(element);
        WebElement single = null;
        single = listElements.get(index);
        scrollToElement(single);
        hover(single);
        single.click();
        waitFor(1);
    }


//to run below method
    /*public  static void main(String args) throws Exception {
        //String  urlPath="testData/tm/planAndRegionConfig/PC_Failure.xlsx";
        String urlPath="";
        new BrowserUtils ().classLoader (urlPath);
    }*/

    public static void classLoader(String urlPath) throws Exception {

        Class cls = Class.forName("com.maersk.crm.utilities.BrowserUtils");
        ClassLoader cLoader = cls.getClassLoader();
        System.out.println(cLoader.getClass());

        URL url = cLoader.getResource(urlPath);

        //Triming unsued characters
        System.out.println("--------------Fetching the absolute file path---------------------");
        String untrimURL = url.getFile();
        System.out.println("Without File :" + url.getFile());

        //Triming unsued characters
        System.out.println("-------------Triming the unused character-------------------");
        synchronized (trimURL){
            trimURL.set(untrimURL.substring(1));
        }
        System.out.println("Final URL path " + trimURL.get());
        String OSName = System.getProperty("os.name");
        synchronized (replaceTrimURL){
            if (OSName.contains("Windows")) {
                replaceTrimURL.set(trimURL.get().replace('/', '\\'));

            } else {
                replaceTrimURL.set(untrimURL);
            }
        }
        System.out.println("File Upload Path=" + replaceTrimURL.get());

    }

    //this method asserts dropdown element being displayed and selected/
    public void selectRandomDropDownOption(WebElement element) {
        // for some reason Project Page is loading something, can't find
        // Solution. It waits about 1 sec and then starts selection process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        int range = 1;
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        range = listElements.size();
        int random = randomNumberBetweenTwoNumbers(2, range);
        System.out.println("Printing Random :" + random);
        hover(element);
        WebElement single = (listElements.get(random - 1));
        hover(single);
        single.click();
        waitFor(1);
    }

    public void selectRandomDropDownOptionExcludingOne(WebElement element) {
        // for some reason Project Page is loading something, can't find
        // Solution. It waits about 1 sec and then starts selection process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        int range = 1;
        List<WebElement> listElements = Driver.getDriver().findElements(By.xpath("//li[@tabindex]"));
        range = listElements.size();
        int random = randomNumberBetweenTwoNumbers(2, range);
        if (random == 1) {
            random = 2;
        }
        System.out.println("Printing Random :" + random);
        hover(element);
        WebElement single = (listElements.get(random - 1));
        hover(single);
        single.click();
        waitFor(1);
    }

    /* Author -vidya 18/12/2019
        To get the color code of element */
    public static String getColorCode(WebElement element) {
        return Color.fromString(element.getCssValue("background-color")).asHex();
    }


    public String getInputYearWithCurrentdate(int year) {
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return sdf.format(cal.getTime());
    }

    public String getPastDayWithCurrentdate(int day) {
        String DATE_FORMAT = "MMddyyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        return sdf.format(cal.getTime());
    }


    //This meethod is used for the get date value with MM/dd/yyyy format
    public static String getGreaterDateFormatMMddyyyy(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String actualDate = dateAfterNDays.format(formatter);
        return actualDate;
    }


    /* Author -paramita 31/12/2019
        To get the color code of element based on css property */
    public static String getColorCode1(WebElement element) {
        return Color.fromString(element.getCssValue("border-left-color")).asHex();
    }


    /* Author -Aswath 01/01/2020
    this method dropdown element of auto complete single select  being displayed and selected*/
    public void autoFillSingleSelectDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//div[contains(text(), '" + selector + "')]/.."));


        System.out.println("Printing Single " + single);
        //Alternate step, once common methods are decided, need refactor accordingly
        //WebElement single = Driver.getDriver().findElement(By.xpath("//div[contains(text(), '" + selector + "')]"));
        //browserUtils.scrollToElement(single);
        hover(single);
        single.click();
    }


    public static boolean descendingOrderContactIDs(List<WebElement> IDs) {
        int topId = 0;
        int nextBelowId = 0;
        for (int i = 0; i <= IDs.size() - 1; i++) {
            topId = Integer.parseInt(IDs.get(i).getText());
            if (i == IDs.size() - 1) {
                continue;
            } else if (IDs.get(i + 1).getText().equals("--")) {
                break;
            } else {
                System.out.println("else");
                nextBelowId = Integer.parseInt(IDs.get(i + 1).getText());
                System.out.println("top id  " + topId);
                System.out.println("next below id  " + nextBelowId);
            }
            return (topId > nextBelowId);
        }
        return (topId > nextBelowId);
    }

    public static boolean ascendingOrderContactIDs(List<WebElement> IDs) {
        boolean is = false;
        int topId = 0;
        int nextBelowId = 0;
        for (int i = 0; i < IDs.size(); i++) {
            if (i == IDs.size() - 1) {
                continue;
            } else if (IDs.get(i + 1).getText().equals("--")) {
                break;
            } else {
                topId = Integer.parseInt(IDs.get(i).getText());
                nextBelowId = Integer.parseInt(IDs.get(i + 1).getText());
                is = (topId < nextBelowId);
            }
            is = (topId <= nextBelowId);
        }
        return is;
    }

    // Here is the code to check if array list is sorted in Ascending order or not
    public static boolean checkSorting(ArrayList<String> arraylist) {
        boolean isSorted = true;
        for (int i = 1; i < arraylist.size(); i++) {
            if (arraylist.get(i - 1).compareTo(arraylist.get(i)) > 0) {
                isSorted = false;
                break;
            }
        }
        return isSorted;
    }

    /**
     * Method accepting WebElement and Color with HEX color for verifying color
     *
     * @param element
     * @param HexColor
     */
    public static void verifyColorOfElement(WebElement element, String HexColor) {
        String color = Color.fromString(element.getCssValue("color")).asHex();
        Assert.assertEquals(color.toUpperCase(), HexColor, "Color not matched");
    }

    /* Author -Olga 08/21/2020
        To get the border color code of element */
    public static String getBorderColorCode(WebElement element) {
        return Color.fromString(element.getCssValue("border-color")).asHex();
    }

    /* Author -Olga 08/25/2020
          returns the new list without "-- --" elements in it  */
    public static List<List<String>> removeEmptyFields(List<List<String>> list) {
        List<List<String>> clean = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.get(i).size() - 1; j >= 0; j--) {
                if (list.get(i).get(j).equalsIgnoreCase("-- --")) {
                    list.get(i).remove(list.get(i).get(j));
                }
            }
        }
        return list;
    }

    /* Author -Olga 09/08/2020
           takes a String in format  MM/dd/yyyy and returns same date in future year as String  */
    public static String getNextYearDate(String date) {
        String monthDate = date.substring(0, 6);
        int year = Integer.parseInt(date.substring(6)) + 1;
        String next = monthDate + year;
        return next;
    }

    public boolean verifyDateFormat(WebElement ele) {
        boolean dateFormatCheck = false;
        String dateFieldFormatted = ele.getText();
        try {
            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(dateFieldFormatted.toString());
            if (date1 != null)
                dateFormatCheck = true;
        } catch (Exception e) {
            System.out.println("Date is not in the expected format");
        }

        return dateFormatCheck;
    }

    /**
     * Author - Olga 09/09/2020
     * Take 2 dates as a String in MM/dd/yyyy format and returns days difference between them
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long daysDifference(String date1, String date2) {
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        long daysBetween = 0;
        try {
            Date dateBefore = myFormat.parse(date1);
            Date dateAfter = myFormat.parse(date2);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            daysBetween = (difference / (1000 * 60 * 60 * 24));
            System.out.println("Number of Days between dates: " + daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysBetween + 1;
    }

    public static boolean isDateBetween(String mainDate, String date1, String date2) {
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateBefore;
            Date dateAfter;
            Date date = myFormat.parse(mainDate);
            if (date1.isEmpty()) {
                dateAfter = myFormat.parse(date2);
                return date.compareTo(dateAfter) <= 0;
            } else if (date2.isEmpty()) {
                dateBefore = myFormat.parse(date1);
                return dateBefore.compareTo(date) <= 0;
            } else {
                dateBefore = myFormat.parse(date1);
                dateAfter = myFormat.parse(date2);
                if (date.compareTo(dateBefore) >= 0 && date.compareTo(dateAfter) <= 0) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyUtcFormat(String date) {
        boolean utcFormat = false;
        try {
            Date date1 = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss").parse(date);
            if (date1 != null)
                utcFormat = true;
        } catch (Exception e) {
            System.out.println("Is not in the expected format");
        }

        return utcFormat;
    }

    public boolean isAttribtueAvailable(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String val = element.getAttribute(attribute);
            if (val != null) {
                result = true;
            }
        } catch (Exception e) {
        }

        return result;
    }

    public static void scrollUpRobotKey() {
        try {
            Robot robot = new Robot();
            for (int k = 0; k < 7; k++) {
                waitFor(1);
                robot.keyPress(KeyEvent.VK_PAGE_UP);
                robot.keyRelease(KeyEvent.VK_PAGE_UP);
                waitFor(1);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    //This method is used to get prior date value with MM/dd/yyyy format
    public static String getPriorDateFormatMMddyyyy(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String actualDate = dateAfterNDays.format(formatter);
        return actualDate;
    }

    //This method accepts yyyy-MM-dd in string and returns it in MM/dd/yyyy string format
    public static String convertyyyyMMddToMMddyyyy(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
        String convertedDate = "";
        try {
            convertedDate = format1.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    //This method is used to get prior date value with yyyy-MM-dd format
    public static String getPriorDateFormatYYYYMMdd(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().minusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateAfterNDays.format(formatter);
    }

    public static void clearText(WebElement element) {
        staticWait(300);
        element.clear();
        staticWait(1000);
    }

    public static void clearTextField(WebElement element) {
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        waitFor(1);
    }

    //This method is used to get future date value with yyyy-MM-dd format
    public static String getFutureDateFormatYYYYMMdd(int nDays) {
        LocalDate dateAfterNDays = LocalDate.now().plusDays(nDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateAfterNDays.format(formatter);
    }

    /* Author -Vidya 06/06/2021
    this method dropdown element of auto complete single select  being displayed and selected*/
    public void autoCompleteSingleSelectDropDown(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(500);
        hover(element);
        String selector1 = selector;
        if (selector.contains("Service")) {
            selector1 = selector.split(" ")[0];
        }
        sendKeyToTextField(element, selector1);

        WebElement single = Driver.getDriver().findElement(By.xpath("//div[@role='presentation']//ul//li[text()='" + selector + "']"));
        hover(single);
        single.click();
    }

    /* Author -Vidya 07/09/2021
    this method to get present or prior or Future date based on test data we pass*/
    public String getDate(String date) {
        String startDateValue = "";
        if (date.equals("today"))
            startDateValue = getCurrentDate();
        else if (date.contains("-"))
            startDateValue = getPriorDate(Integer.parseInt(date.replace("-", "")));
        else if (date.contains("+"))
            startDateValue = getFutureDate(Integer.parseInt(date.replace("+", "")));
        else
            startDateValue = date;
        return startDateValue;
    }

    /*Author - Vinuta 12-07-2021
    this method accepts a string in format Abcd Efg & returns camel cased abcdEfg pattern
     */
    public static String snakeToCamel(String str) {
        str = str.substring(0, 1).toLowerCase()
                + str.substring(1);

        while (str.contains(" ")) {
            str = str.replaceFirst(" [A-Z]", String.valueOf(Character.toUpperCase(str.charAt(str.indexOf(" ") + 1))));
            str = str.replaceAll("-", "");
        }// Return string

        return str;
    }

    //This method accepts mm/dd/yyyy in string and returns it in yyyy-mm-dd string format
    public static String convertMMddyyyyToyyyyMMdd(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String convertedDate = "";
        try {
            convertedDate = format1.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public void selectDropDownSlider(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        staticWait(900);
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[(text()=\"" + selector + "\")]"));
        scrollToElement(single);
        hover(single);
        single.click();
    }

    public List<String> convertToListStr(List<WebElement> options) {
        List<String> strOptions = new ArrayList<>();
        options.forEach(option -> {
            strOptions.add(option.getText().trim());
        });

        return strOptions;
    }

    /*Author-Vinuta
    Returns the key which has the given value in a Map
     */
    public String getKeyFromValue(Map hm, Object value) {
        String key;
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                key = o.toString();
                return key;
            }
        }
        return null;
    }

    /*Author-Vinuta
  Fluent Wait to wait until any snackbar disappears
   */
    public static void waitForElementToDisappear(final WebElement webElement, int timeinsec) {
        try {
            new FluentWait<WebDriver>(Driver.getDriver())
                    .withTimeout(Duration.ofSeconds(timeinsec))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class)
                    .until(new ExpectedCondition<Boolean>() {
                               public Boolean apply(WebDriver driver) {
                                   return (!webElement.isDisplayed());
                               }
                           }
                    );
        } catch (TimeoutException e) {
            System.out.println("Time Out On Element");
        }
    }

    public void loginCRMafterLoggOut(String userName, String password, String projectName) {
        System.out.println(projectName);
        System.out.println(userName);
        selectDropDown(loginPage.projectId, projectName);
        click(loginPage.continueBtn);
        //added wait here to give sometime for UI to parse the response before making config api request.
        waitFor(3);
        click(loginPage.acceptAndContinueBtn);
    }

    public boolean stateDropdownHasAllAbbr(WebElement element) {
        boolean present = false;
        List<String> allStates = Arrays.asList("AA Armed Forces Americas", "AE Armed Forces Europe", "DC District of Columbia", "AZ Arizona", "AL Alabama", "AP Armed Forces Pacific", "AK Alaska", "AR Arkansas", "AS American Samoa", "CA California",
                "CO Colorado", "CT Connecticut", "DC District of Columbia", "DE Delaware", "FL Florida", "FM Federated States of Micronesia", "GA Georgia", "GU Guam", "HI Hawaii", "ID Idaho", "IL Illinois", "IN Indiana", "IA Iowa",
                "KS Kansas", "KY Kentucky", "LA Louisiana", "MD Maryland", "MA Massachusetts", "ME Maine", "MH Marshall Islands", "MI Michigan", "MN Minnesota",
                "MO Missouri", "MP Northern Mariana Islands", "MS Mississippi", "MT Montana", "NE Nebraska", "NV Nevada", "NH New Hampshire", "NJ New Jersey", "NM New Mexico", "NY New York", "NC North Carolina",
                "ND North Dakota", "OH Ohio", "OK Oklahoma", "OR Oregon", "PA Pennsylvania", "PR Puerto Rico", "PW Palau", "RI Rhode Island", "SC South Carolina", "SD South Dakota",
                "TN Tennessee", "TX Texas", "UT Utah", "VT Vermont", "VA Virginia", "WA Washington", "WV West Virginia", "WI Wisconsin", "WY Wyoming");
        List<String> states = new ArrayList<>();
        System.out.println("number of values in state dropdown = " + states.size());
        states.addAll(allStates);
        for (String state : states) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.click();
            // browserUtils.hover(element);
            waitFor(1);
            WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + state + "')]"));
            scrollToElement(single);
            present = (single.getText().equals(state));
            hover(single);
            single.click();
        }
        return present;
    }

    public void selectMultiSelectDropDown(WebElement element, String selector) {
        waitFor(1);
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[text()='" + selector + "']"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }


    public void fillTheFiled(WebElement element, String text) {
        try {
            if (element.isDisplayed()) {
                try {
                    element.click();
                } catch (Exception e) {
                    JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
                    executor.executeScript("arguments[0].click();", element);
                }
                staticWait(300);
                element.clear();
                staticWait(1000);
                element.sendKeys(text);
            }
        } catch (Exception e) {
            System.out.println("Got into exception - " + e.getMessage());
        }
    }

    public void clearAndFillTextWithSendKeys(WebElement element, String text) {
        try {
            waitForVisibility(element, 5);
            if (element.isDisplayed()) {
                try {
                    element.click();
                } catch (Exception e) {
                    JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
                    executor.executeScript("arguments[0].click();", element);
                }
                staticWait(300);
                element.sendKeys(Keys.CONTROL + "a");
                element.sendKeys(Keys.DELETE);
                staticWait(300);
                element.sendKeys(text);
            }
        } catch (Exception e) {
            System.out.println("Got into exception - " + e.getMessage());
        }
    }

    public void loginCRMWithRoleSelction(String Role) {
        System.out.println(Role);
        waitFor(2);
        // select specific role for users
        selectDropDown(loginPage.userRoleDropDown, Role);
        waitFor(4);
        click(loginPage.continueBtn);
        waitForPageToLoad(10);
        click(loginPage.acceptAndContinueBtn);
        waitForVisibility(loginPage.userIcon, 10);
    }

    public void loginCRMafter(String userName, String password, String projectName) {
        System.out.println(projectName);
        System.out.println(userName);
        clearAndFillText(loginPage.loginUserNameOrEmail, userName);
        waitFor(1);
        loginPage.nextBtn.click();
        clearAndFillText(loginPage.passwordCRM, password);
        waitFor(1);
        loginPage.signInBtn.click();
        waitForVisibility(loginPage.projectId, 5);
        selectDropDown(loginPage.projectId, projectName);
        waitFor(3);
    }

    public void loginCRMafterLoggOutonCP(String userName, String password, String projectName) {
        System.out.println(projectName);
        System.out.println(userName);
        selectDropDown(loginPage.projectId, projectName);
        waitFor(3);
    }

    public void selectFromMultiSelectDropDownForWithEscapedKey(WebElement element, String selector) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
        hover(element);
        WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + selector + "')]"));
        scrollToElement(single);
        hover(single);
        single.click();
        single.sendKeys(Keys.ESCAPE);
    }

    public void scrollDownWithArrow(int count) {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        Actions actions = new Actions(Driver.getDriver());
        for (int i = 0; i < count; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).build().perform();
        }
    }

    public String getAttributesFromTheTag(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        Object elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",element);
        return elementAttributes.toString();
    }

    /**
     * <Description> scroll into view with true or false js argument</Description>
     *
     * @param element
     * @param ShouldLookDown pass true if the element you are looking is beneath, pass false if the element you are looking is above
     */
    public static void scrollToElement(WebElement element, boolean ShouldLookDown) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("arguments[0].scrollIntoView(" + ShouldLookDown + ")", element);
    }

    /**
     * <Description>returns a string from links section of ui from same row, based on another column's string</Description>
     * <Description>assumes there is only 1 instance of the matching expectedColumnValue</Description>
     * @param rowLocator
     * @param indexOfColumnToInspect
     * @param expectedColumnValue
     * @param desiredColumnIndex
     * @return
     */
    public String getUITableText(String rowLocator, int indexOfColumnToInspect, String expectedColumnValue, int desiredColumnIndex) {
        String result = "resultNotFound";
        List<WebElement> rows = Driver.getDriver().findElements(By.xpath(rowLocator));
        for (int index = 1; index <= rows.size(); index++) {
            if (expectedColumnValue.equalsIgnoreCase(Driver.getDriver().findElement(By.xpath("("+rowLocator+"/td["+indexOfColumnToInspect+"])["+index+"]")).getText().trim())){
                result = Driver.getDriver().findElement(By.xpath("("+rowLocator+"/td["+desiredColumnIndex+"])["+index+"]")).getText().trim();
            }
        }
        Assert.assertNotEquals("resultNotFound",result);
        return result;
    }




    public void loginCRM1() {

        System.out.println("TEst Prinint");
    }





}

