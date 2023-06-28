package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMSRViewEditPage {
    

    public CRMSRViewEditPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[text()='EDIT SERVICE REQUEST']/parent::button")
    public WebElement btnEditSR;

    @FindBy(xpath = "//span[text()='Service Request Successfully Updated']")
    public WebElement updateSuccessMessageText;

    @FindBy(xpath = "//p[text()='VIEW SERVICE REQUEST']")
    public WebElement viewServiceRequest;

    @FindBy(xpath = "//h5[text()='SERVICE REQUEST DETAILS']")
    public WebElement serviceRequestDetails;

    @FindBy(xpath = "//p[text()='SR TYPE']/following-sibling::h6")
    public WebElement viewSrType;

    @FindBy(xpath = "//p[text()='SR INFORMATION']/following-sibling::h6")
    public WebElement viewSRInfo;

    @FindBy(xpath = "//p[text()='SR NOTES']/following-sibling::h6")
    public WebElement viewSRNote;

    @FindBy(xpath = "//p[text()='SR CATEGORY']/following-sibling::h6")
    public WebElement viewSRCategory;

    @FindBy(xpath = "//h5[text()='SR ID']")
    public WebElement srIdOnViewPage;

    @FindBy(xpath = "//span[text()='SR DETAILS']")
    public WebElement srDetailsTab;

}
