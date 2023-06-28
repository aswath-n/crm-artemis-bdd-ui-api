package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TenantManagerDMSLoginPage {
    

    public TenantManagerDMSLoginPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[contains(text(), ' LOGIN ')]")
    public WebElement tmLoginButton;

    @FindBy(name="loginName")
    public WebElement userName;

    @FindBy(name="passwordValue")
    public WebElement password;

}
