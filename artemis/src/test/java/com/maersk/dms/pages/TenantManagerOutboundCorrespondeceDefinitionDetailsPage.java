package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class TenantManagerOutboundCorrespondeceDefinitionDetailsPage {
    

    public TenantManagerOutboundCorrespondeceDefinitionDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[text()='CORRESPONDENCE DEFINITIONS ']")
    public WebElement outboundCorrDefHeader;

    @FindBy(xpath = "//h5[text()=' OUTBOUND CORRESPONDENCE DEFINITION DETAILS ']")
    public WebElement outboundCorrDefDetHeader;

    @FindBy(xpath = "//h5[text()=' CORRESPONDENCE CHANNEL DETAILS ']")
    public WebElement corrChannelDetHeader;

    @FindBy(xpath = "//span[text()=' ADD CHANNEL']")
    public WebElement addChannelButton;

    @FindBy(id = "mui-component-select-selectChannel")
    public WebElement selectChannelField;

    @FindBy(xpath = "//input[@id='sendImmediately']/parent::span")
    public WebElement sendImmediatelyOpt;

    @FindBy(xpath = "//input[@id='mandatory']/parent::span")
    public WebElement mandatoryOpt;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    public List<WebElement> startEndDateFields;

    @FindBy(xpath = "//input[@name='cdfEndReason']")
    public WebElement endReason;

    @FindBy(name = "senderEmail")
    public WebElement senderEmail;

    @FindBy(xpath = "//span[text()='ID']")
    public WebElement corrDefID;

    @FindBy(xpath = "//td/a")
    public WebElement corrDefIDFirst;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/p[2]")
    public WebElement outboundCorrChannelType;

    @FindBy(xpath = "//p[text()='START DATE']/parent::div/p[2]")
    public WebElement outboundCorrStartDate;

    @FindBy(xpath = "//p[text()='END DATE']/parent::div/p[2]")
    public WebElement outboundCorrEndDate;

    @FindBy(xpath = "//input[@id='sendImmediately']")
    public WebElement outboundCorrSendImmediatelyOpt;

    @FindBy(xpath = "//input[@id='mandatory']")
    public WebElement outboundCorrMandatoryOpt;

    @FindBy(xpath = "//ul/li/a")
    public List<WebElement> corrDefPageNums;

    @FindBy(xpath = "//div[text()='show 10']")
    public WebElement defaultShow10;

    @FindBy(xpath = "//p[text()='END DATE']/parent::div/p[2]")
    public List<WebElement> channelsEndDates;

    @FindBy(xpath = "//h5[text()='No Records Available.']")
    public WebElement noRecordsChannel;

    @FindBy(xpath = "//input[@id='file-attach']")
    public WebElement inputAddTemplate;

    @FindBy(xpath = "//div[@id='mui-component-select-language']")
    public WebElement templateLanguage;


}
