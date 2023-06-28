package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TMAddEditPermissionGroupPage {

    

    public TMAddEditPermissionGroupPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//div[@class='mx-layout-top-drawer']/div/h4[contains(text(),'Add User')]")
    public WebElement addUserHeader;
}

