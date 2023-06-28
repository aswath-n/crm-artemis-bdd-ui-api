package com.maersk.crm.pages.tm;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class TMTaskFieldDetailsPage {

    

    public TMTaskFieldDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='TASK FIELD DETAILS ']")
    public WebElement TaskFieldDetailsLabel;

    @FindBy(xpath = "//input[@id='groupFieldCheckbox']")
    public WebElement CreateGrpFieldChkBox;

    @FindBy(xpath = "//input[@name='groupName']")
    public WebElement groupName;

    @FindBy(xpath = "//button[text()=' ADD FIELD']")
    public WebElement AddFieldButton;

    @FindBy(xpath = "//i[text()='delete']")
    public WebElement deleteBtn;

    @FindBy(xpath = "//div[@id='menu-fieldType']//ul/li")             //----->   //ul/li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button']
    public List<WebElement> FieldTypeList;

    @FindBy(xpath = "//input[@name='displayName']")
    public WebElement displayNameInputbx;

    @FindBy(xpath = "(//input[@name='displayName'])[2]")
    public WebElement displayNameInputbx2;

    @FindBy(xpath = "//div[@id='mui-component-select-fieldType']")
    public WebElement fieldTypeDrpDwn;

    @FindBy(xpath = "//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button']")
    public List<WebElement> fieldTypeDrpDwnList;

    @FindBy(xpath = "(//div[@id='mui-component-select-fieldType'])[2]")
    public WebElement fieldTypeDrpDwn2;

    @FindBy(xpath = "//input[@name='fieldKey']")
    public WebElement fieldKeyInputBx;

    @FindBy(xpath = "(//input[@name='fieldKey'])[2]")
    public WebElement fieldKeyInputBx2;

    @FindBy(xpath = "//span[text()=' Cancel']")
    public WebElement CancelBtn;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement ContinueBtn;

    @FindBy(xpath = "//span[@class='MuiButton-label']")
    public WebElement saveButton;

    public WebElement getFieldLocator( String fieldName){
        return Driver.getDriver().findElement(By.xpath("//span[text()='" + fieldName + "']"));
    }

    @FindBy(xpath = "//span[text()='fieldKey already in use, the duplicate field will not be saved']")
    public WebElement errormsgFieldKey;

    @FindBy(xpath = "//span[text()='Field display name already in use, the duplicate field will not be saved']")
    public WebElement errormsgFieldName;


}