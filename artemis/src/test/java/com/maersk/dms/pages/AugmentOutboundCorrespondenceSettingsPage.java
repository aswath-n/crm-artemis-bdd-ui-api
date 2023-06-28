package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
sean thorson
 */
public class AugmentOutboundCorrespondenceSettingsPage {
    

    public AugmentOutboundCorrespondenceSettingsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(xpath = "//span[contains(text(),'build')]/..")
    public WebElement leftDrawer;

    @FindBy(xpath = "//*[contains(text(),'Correspondence Outbound Configuration')]")
    public WebElement outBoundSettingsLink;

    @FindBy(xpath = "//a[contains(text(),'Correspondence Outbound Definitions')]")
    public WebElement outBounddefinationLink;


    @FindBy(xpath = "//*[contains(text(), 'Edit Settings')]")
    public WebElement editsettings;
    @FindBy(xpath = "//*[contains(text(), 'Save')]")
    public WebElement savesettings;
    @FindBy(xpath = "//input[contains(@id, 'sendCorrespondenceToAdultConsumersRegarding')]")
    public WebElement sendCorrespondenceToAdultConsumersRegarding;

    @FindBy(xpath = "//span[contains(@label, 'SEND CORRESPONDENCE TO ADULT CONSUMERS REGARDING')]")
    public WebElement verifysendCorrespondenceToAdultConsumersRegarding;

    @FindBy(xpath = "//p[contains(@class, 'MuiFormHelperText-root Mui-error Mui-required')] | //p[contains(@class, 'MuiFormHelperText-root Mui-error MuiFormHelperText-filled')]")
    public WebElement errormessage;

    @FindBy(xpath = "//*[contains(text(), 'Correspondence Configuration successfully updated.')]")
    public WebElement successmessage;


    @FindBy(xpath = "//span[contains(@label, 'DO NOT COMBINE RECIPIENTS AT SAME ADDRESS')]")
    public WebElement verifyDoNotCombineRecipientsAtSameAddress;

    @FindBy(xpath = "//input[contains(@id, 'donotCombineRecipientsAtSameAddress')]")
    public WebElement donotCombineRecipientsAtSameAddress;

    @FindBy(xpath = "//input[contains(@name, 'outboundFilenamePrefix')]")
    public WebElement outboundFilenamePrefix;

@FindBy(xpath = "//p[contains(text(), 'Outbound Filename Prefix')]/following-sibling::p")
public WebElement verifyoutboundFilenamePrefix;

    @FindBy(xpath = "//p[contains(text(), 'Outbound Correspondence')]/following-sibling::p")
    public WebElement verifydoctype;
    @FindBy(xpath = "//p[contains(text(), 'Outbound Correspondence Template')]/following-sibling::p")
    public WebElement verifytemplatetype;



    @FindBy(xpath = "//input[contains(@name, 'replyToEmailAddress')]")
    public WebElement replyToEmailAddress;


    @FindBy(xpath = "//p[contains(text(), 'Reply To Email Address')]/following-sibling::p")
    public WebElement verifyreplyToEmailAddress;

    @FindBy(xpath = "//input[contains(@name, 'phoneNumber')]")
    public WebElement phoneNumber;

    @FindBy(xpath = "//p[contains(text(), 'Phone Number')]/following-sibling::p")
    public WebElement verifyphoneno;


    @FindBy(xpath = "//input[contains(@name, 'faxNumber')]")
    public WebElement faxNumber;
    @FindBy(xpath = "//p[contains(text(), 'Fax Number')]/following-sibling::p")
    public WebElement verifyfaxno;
    @FindBy(xpath = "//input[contains(@name, 'emailAddress')]")
    public WebElement emailAddress;
    @FindBy(xpath = "(//p[contains(text(), 'Email Address')]/following-sibling::p)[3]")
    public WebElement verifyemailAddress;

    @FindBy(xpath = "//input[contains(@name, 'webAddress')]")
    public WebElement webAddress;
    @FindBy(xpath = "//p[contains(text(), 'Web Address')]/following-sibling::p")
    public WebElement verifywebaddress;
    @FindBy(xpath = "//input[contains(@name, 'mobileAppName')]")
    public WebElement mobileAppName;
    @FindBy(xpath = "//p[contains(text(), 'Mobile App Name')]/following-sibling::p")
    public WebElement verifyMobileappname;
    @FindBy(xpath = "//p[contains(text(), 'Vendor Outbound File Format')]/following-sibling::p")
    public WebElement verifyfileformat;


    @FindBy(xpath = "//input[contains(@name, 'fileFormat')]/..")
    public WebElement fileFormat;


    @FindBy(xpath = "(//input[contains(@name, 'outboundCorrespondence')])[1]")
    public WebElement ecmsoutboundCorrespondence;
    @FindBy(xpath = "//input[contains(@name, 'outboundCorrespondenceTemplate')]")
    public WebElement ecmsoutboundCorrespondenceTemplate;


    public WebElement getDropdownOption(String text) {
        return Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + text + "')]"));
    }

    //keerthi
    @FindBy(xpath = "//input[@name='ttyNumber']")
    public WebElement ttynumber;

    @FindBy(xpath = "//*[text()='tty Number format is invalid. Please enter it in format ##########']")
    public WebElement ttynumber_errormsg;

    @FindBy(xpath = "//h5[text()='OUTBOUND PRINT FILE FTP DETAILS']")
    public WebElement ftpheader;

    @FindBy(xpath = "//input[@name='user']")
    public WebElement smtpemailuser;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement smtppassworduser;

    @FindBy(xpath = "//h5[contains(text(),'RECIPIENT SETTINGS')]")
    public WebElement recipientsettings_label;

    @FindBy(xpath = "//*[@class='mx-header__datetime mx-rightborder']/p[1]")
    public WebElement topheader_date;

    @FindBy(xpath = "//*[@class='mx-header__datetime mx-rightborder']/p[2]")
    public WebElement topheader_time;

}
