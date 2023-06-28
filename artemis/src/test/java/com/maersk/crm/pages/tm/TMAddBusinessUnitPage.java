package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMAddBusinessUnitPage {
    

    public TMAddBusinessUnitPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(name = "businessUnitName")
    public WebElement businessUnitName;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement startDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement endDate;

    @FindBy(name = "businessUnitDescription")
    public WebElement businessUnitDescription;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement saveButton;

    @FindBy(xpath = "//i[text()='clear']")
    public WebElement cancelButton;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addButton;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]/../../following-sibling::div//p")
    public WebElement warningMessageTxt;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//span[text()='clear']")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//h5[text()='BUSINESS UNIT DETAILS']")
    public WebElement businessUnitLabl;

    @FindBy(xpath = "//span[text()='Business Unit Successfully Created']")
    public WebElement successMessageTxt;

    @FindBy(xpath = "//span[contains(text(),'SUCCESS MESSAGE')]")
    public WebElement successMessage;

    //refatorBy: Vidya Date:28-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]")
    public WebElement businessUnitListPage;

    //refatorBy: Vidya Date:28-02-2020
    @FindBy(xpath = "//h5[contains(text(),'TEAM')]")
    public WebElement teamListPage;

    @FindBy(xpath = "//label[text()='End Date']/following-sibling::p")
    public WebElement endDateError;

    @FindBy(xpath = "//label[text()='Start Date']/following-sibling::p")
    public WebElement startDateError;

    @FindBy(xpath = "//label[text()='Business Unit Name']/following-sibling::p")
    public WebElement businessUnitNameError;

    //Adding below elements for scripts Cp-3088 and CP-1909
    @FindBy(xpath = "//label[contains(text(),'Select Task Type')]/../div")
    public WebElement taskTypeDropDown;

    //Add for CP-1909 -Author - Paramita
    @FindBy(xpath = "//ul[@id='_Select Task Type(s)-popup']/li")
    public List<WebElement> taskTypeDropDownValue;

    //@FindBy(xpath = "//label[contains(text(),'Select Task Type')]/..//div//*[name()='svg'][@role]")
    @FindBy(xpath="//label[contains(text(),'Select Task Type')]/..//div[@role='button']")
    public List<WebElement> taskTypeDropDownValueDeleteIcon;

    @FindBy(xpath = "//label[contains(text(),'Select Task Type')]/..//div//*[name()='svg'][not(@role)]")
    public List<WebElement> taskTypeDeleteAllDropdownValue;

    @FindBy(id = "_Select Task Type(s)")
    public WebElement selectTaskType;

    @FindBy(xpath = "//div[1]/*[local-name()='svg'][1]")
    public WebElement frstTask;
}
