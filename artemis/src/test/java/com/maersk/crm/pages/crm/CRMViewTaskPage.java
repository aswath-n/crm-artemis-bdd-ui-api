package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CRMViewTaskPage {

    

    public CRMViewTaskPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[text()='TASK TYPE']/following-sibling::h6")
    public WebElement lblTaskType;

    @FindBy(xpath = "//*[text()='ASSIGNEE']/following-sibling::h6")
    public WebElement lblAssignee;

    @FindBy(xpath = "//*[text()='TASK NOTES']/following-sibling::h6")
    public WebElement lblTaskNotes;

    @FindBy(xpath = "//*[text()='TASK DETAIL']")
    public WebElement taskDetailHeader;

    @FindBy(xpath = "//h5[text()='VIEW INBOUND CORRESPONDENCE DETAILS']")
    public WebElement viewInboundCorrespondenceDetailHeader;

    @FindBy(xpath = "//p[contains(text(),'Service')]")
    public WebElement userAccountName;

    @FindBy(xpath = "//span[text()='edit']")
    public WebElement editTask;


   }
