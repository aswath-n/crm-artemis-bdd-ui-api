package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMCreateSRPage {

    

    public CRMCreateSRPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@id='srtype']/ancestor::div[1]")
    public WebElement lstSR;

    @FindBy(xpath = "//div[@id='menu-srtype']//ul/li")
    public List<WebElement> srListValues;

    @FindBy(xpath = "//textarea[@name='srInfo']")
    public WebElement txtSRInfo;

    @FindBy(xpath = "//input[@name='srPriority']/ancestor::div[1]")
    public WebElement lstSRPriority;

    @FindBy(xpath = "//p[text()='STATUS DATE']/following-sibling::p")
    public WebElement lblStatusDate;

    @FindBy(xpath = "//p[text()='DUE IN']/following-sibling::p")
    public WebElement lblDueIn;

    @FindBy(xpath = "//input[@name='srStatus']/ancestor::div[1]")
    public WebElement lstSRStatus;

    @FindBy(xpath = "//span[text()='Service Request Successfully Created']")
    public WebElement successMessageText;

    @FindBy(xpath = "//h5[text()='CREATE SERVICE REQUEST']//i[text()='description']")
    public WebElement csrHeader;

    @FindBy(xpath = "//p[text()='CREATE SERVICE REQUEST']")
    public WebElement csrPage;
}
