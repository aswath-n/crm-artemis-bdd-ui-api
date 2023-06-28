package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMEditBusinessUnitPage {
    

    public TMEditBusinessUnitPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[5]")
    public List<WebElement> statusClumVlu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[1]//a")
    public List<WebElement> businessUnitNameClumVlu;

    @FindBy(xpath="//p[text()='Business Unit Name']/..//h6")
    public WebElement BUName;

    @FindBy(xpath="//p[text()='Start Date']/..//h6")
    public WebElement BUStartDate;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]")
    public WebElement businessUnitListPage;

    @FindBy(xpath = "//i[text()='edit']")
    public WebElement businessUnitEditIcon;

    @FindBy(xpath = "//h5[text()='BUSINESS UNIT DETAILS']")
    public WebElement pageTitle;

    @FindBy(xpath = "//span[text()='Business Unit Successfully Updated']")
    public WebElement successMessageTxt;

    @FindBy(xpath = "//span[contains(text(),'SUCCESS MESSAGE')]")
    public WebElement successMessage;

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addButton;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[5]")
    public WebElement statusValu;

    //refactorBy:Vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'BUSINESS UNIT')]/../../following-sibling::main//tbody//td[1]//a")
    public WebElement businessUnitNameVlu;


    //@FindBy(xpath = "//div[@role='menuitem']")
    @FindBy(xpath = "//ul[@class='MuiAutocomplete-listbox']/li")
    public List<WebElement> taskTypeDropDownValue;
}
