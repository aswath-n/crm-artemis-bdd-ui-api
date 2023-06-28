package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMEditProfileDetailsOnProfileContactPage {
    
    public CRMEditProfileDetailsOnProfileContactPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy( id = "firstName")
    public WebElement profileDetailsFirstName;

    @FindBy( id = "lastName")
    public WebElement profileDetailsLastName;

    @FindBy( id = "SSN")
    public WebElement profileDetailsSSN;

    @FindBy(xpath="//span[text()=' EDIT']/../..")
    public WebElement profileContactEditButton;

}
