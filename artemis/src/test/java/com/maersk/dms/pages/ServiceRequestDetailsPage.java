package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ServiceRequestDetailsPage {

    public ServiceRequestDetailsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(name = "rid")
    public WebElement rid;

    @FindBy(name = "grievance")
    public WebElement grievance;

    @FindBy(name = "fromName")
    public WebElement contactName;

    @FindBy(name = "fieldValue")
    public WebElement contactPhone;

    @FindBy(name = "email")
    public WebElement contactEmail;

    @FindBy(id = "mui-component-select-programRequired")
    public WebElement programDropdown;

    @FindBy(xpath = "//input[@id='currentPlan']/..")
    public WebElement currentPlanDropdown;

    @FindBy(id = "mui-component-select-desiredPlan")
    public WebElement desiredPlanDropdown;

    @FindBy(id = "currentPlan")
    public WebElement currentPlanSelectedValue;

    @FindBy(id = "desiredPlan")
    public WebElement desiredPlanSelectedValue;

    @FindBy(id = "mui-component-select-reasonExplanationDropdown")
    public WebElement reasonDropdown;

    @FindBy(xpath = "//*[@id='menu-reasonExplanationDropdown']//div[3]/ul/li[contains(text(), ' ')]")
    public List<WebElement> listOfReasons;

    @FindBy(name = "explanation")
    public WebElement explanation;

    @FindBy(xpath = "//label[text()='DATE RECEIVED GRIEVANCE']/..//input")
    public WebElement dateReceivedGrievance;

    @FindBy(xpath = "//label[text()='DATE FOLLOW-UP EMAIL SENT']/..//input")
    public WebElement dateFollowUpEmailSent;

    @FindBy(xpath = "//label[text()='DATE HEALTH PLAN CONTACTED']/..//input")
    public WebElement dateHealthPlanContacted;

    @FindBy(xpath = "//label[text()='DATE STATE NOTIFIED']/..//input")
    public WebElement dateSateNotified;

    @FindBy(xpath = "//input[@id='decision']/..")
    public WebElement decision;

    @FindBy(xpath = "//input[@id='consumerSatisfied']/..")
    public WebElement consumerSatisfied;

    @FindBy(xpath = "//input[@id='finalDecision']/..")
    public WebElement finalDecision;

    @FindBy(xpath = "//label[text()='DATE DECISION LETTER SENT']/..//input")
    public WebElement dateDecisionLetterSent;

    @FindBy(id = "newPlanStartDate")
    public WebElement newPlanStartDate;

    @FindBy(id = "disposition")
    public WebElement disposition;

}
