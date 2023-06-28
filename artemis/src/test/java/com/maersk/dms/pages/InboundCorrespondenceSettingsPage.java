package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InboundCorrespondenceSettingsPage {
    

    public InboundCorrespondenceSettingsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(),'Correspondence Inbound Configuration')]")
    public WebElement sideMenuInboundSettingsLink;

    @FindBy(xpath = "//*[contains(text(),'INBOUND CORRESPONDENCE CONFIGURATION')]")
    public WebElement inboundSettingsHeader;

}
