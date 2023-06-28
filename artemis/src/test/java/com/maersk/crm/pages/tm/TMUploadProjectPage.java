package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;


public class TMUploadProjectPage {

    

    public TMUploadProjectPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='UPLOAD PROJECT']")
    public WebElement UpladoPrjcthdr;

    @FindBy(xpath = "//i[text()='attach_file']")
    public WebElement attchFiletab;

    @FindBy(xpath = "//input[@id='file-attach']")
    public WebElement getAttachedFile;

    @FindBy(xpath = "(//span[contains(text(),' Upload')])[1]")
    public WebElement uploadBtn;

    @FindBy(xpath = "//span[contains(text(),'Upload failed. Duplicate fields found')]")
    public WebElement uploadErrorMsg1;

    @FindBy(xpath = "//span[contains(text(),'Upload failed. Duplicate grid found')]")
    public WebElement uploadErrorMsg2;

    @FindBy(xpath = "//span[contains(text(),'Upload failed. Duplicate columns found')]")
    public WebElement uploadErrorMsg3;

    @FindBy(xpath = "//span[contains(text(),'Upload Failed. Duplicate Page Key found')]")
    public WebElement uploadErrorMsg4;

    @FindBy(xpath = "//span[contains(text(),'Upload failed. Duplicate section key found')]")
    public WebElement uploadErrorMsg5;

    @FindBy(xpath = "//span[contains(text(),'Upload failed. Duplicate Labels found')]")
    public WebElement uploadErrorMsg6;


}
