package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class TMProjectListPage extends TMUtilities {

    
    private BrowserUtils browserUtils = new BrowserUtils();
    private String projectName;

    public TMProjectListPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public final String searchDropDownList = "//*[@id='react-autowhatever-1']//li";

    @FindBy(xpath = "//label[starts-with(text(), 'State')]/..//input ")
    public WebElement searchByState;

    @FindBy(xpath = "//label[starts-with(text(), 'Project')]/..//input")
    public WebElement searchProject;

    @FindBy(xpath = "//label[starts-with(text(), 'Program')]/..//input ")
    public WebElement searchByProgram;

    @FindBy(xpath = "//label[starts-with(text(), 'Client/State Agency')]/..//input")
    public WebElement searchByClientStateAgency;

    //refactorBy:Vidya Date:28-02-2020
    @FindBy(xpath = "//span[contains(text(), 'search')]/../..")
    public WebElement search;

    @FindBy(xpath = "//span[contains(text(), ' Clear ')]")
    public WebElement clear;

    @FindAll({
            @FindBy(css = ".row.mx-0 > div")
    })
    public List<WebElement> projects;       //List<WebElement> projects  = Driver.getDriver().findElements(By.cssSelector(".row.mx-0 > div"));

    @FindBy(xpath = "//div[@class='col-3 mb-4']//h4")
    public List<WebElement> projectNamesList;

    //refactorBy:Vidya Date:02-03-2020
    @FindBy(xpath = "//*[@id='root']//h5")
    public WebElement projectListDashboard;

    public static String expendButtonClass = "button"; //"jss72";
    public static String projectNameCSS = "h4";
    public static String projectStateClass = "pr-2";
    public static String contractIdClass = "pl-2";
    public static String projectIdCSS = "h6";
    public static String provisioningStatusCSS = "h6 span";
    public static String programNameCSS = "div p:nth-of-type(2)";
    public static String clientNameCSS = "div p:nth-of-type(4)"; //todo check on xpath
    public static String startDateCSS = ".row .col-6:nth-of-type(1) p:nth-of-type(2)";
    public static String startDateFieldXpath = "(//div/p[@class='mdl-color-text--grey-500 mb-0'])[3]";
    public static String endDateCSS = ".row .col-6:nth-of-type(2) p:nth-of-type(2)";
    public static String endDateFieldXpath = "(//div/p[@class='mdl-color-text--grey-500 mb-0'])[4]";
    public static String projectStatusXpath = "//span[contains(@class, ' text-uppercase')]/strong";



    public static void expendProject(WebElement element) {
        waitFor ( 2 );
        element.findElement(By.cssSelector(expendButtonClass)).click();
    }


    /*this method reads and returns a list of project details of an existing N-amount of project */
    public List<String> projectInfo(int projNum) {
        List<String> projectInfoText = new ArrayList<>();

        WebElement currentProject = projects.get(projNum);
        browserUtils.hover(currentProject);
        projectInfoText.add(currentProject.findElement(By.cssSelector(projectNameCSS)).getText());
        projectInfoText.add(currentProject.findElement(By.className(projectStateClass)).getText());
        projectInfoText.add(currentProject.findElement(By.className(contractIdClass)).getText());
        String projectId = currentProject.findElement(By.cssSelector(projectIdCSS)).getText();
        //projectInfoText.add(projectId.substring(0, projectId.indexOf(" ")));
        projectInfoText.add(projectId.replaceAll("[^0-9?!\\.]",""));


        String proStatus = currentProject.findElement(By.cssSelector(provisioningStatusCSS)).getText().toLowerCase();
        proStatus = proStatus.substring(0, 1).toUpperCase() + proStatus.substring(1);
        projectInfoText.add(proStatus);

        projectInfoText.add(currentProject.findElement(By.cssSelector(programNameCSS)).getText());
        projectInfoText.add(currentProject.findElement(By.cssSelector(clientNameCSS)).getText());
        projectInfoText.add(currentProject.findElement(By.cssSelector(startDateCSS)).getText());
        projectInfoText.add(currentProject.findElement(By.cssSelector(endDateCSS)).getText());
        projectInfoText.add(currentProject.findElement(By.xpath(projectStatusXpath)).getText());
        return projectInfoText;
    }

    //By Vinuta
    public WebElement getProjectDetailsButton(String projName)
    {
        return Driver.getDriver().findElement(By.xpath("//h4[contains(text(), '" + projName + "')]/following-sibling::button"));
    }



}
