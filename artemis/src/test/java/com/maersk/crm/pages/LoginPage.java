package com.maersk.crm.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginPage {
    

    public LoginPage() {
        
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(name = "loginName")
    //@FindBy(name="username")
    public WebElement userName;

    @FindBy(name = "passwordValue")
    //@FindBy(name="password")
    public WebElement password;

    @FindBy(name = "username")
    public WebElement userNameCRM;

    @FindBy(name = "password")
    public WebElement passwordCRM;

    //    @FindBy(xpath="//span[contains(text(),'arrow_right_alt')]")
    @FindBy(xpath = "//input[@value='Sign in']")
    public WebElement submitButton;

    @FindBy(xpath = "//*[@class='jss62 jss63']")
    public WebElement invalidUserNameError;

    @FindBy(xpath = "//label[.='SELECT PROJECT']/following-sibling::div/div")
    public WebElement projectId;

    @FindBy(xpath = "//*[contains(text(), ' LOGIN ')]")
    public WebElement tmLoginButton;

    // @FindBy(xpath="//span[contains(text(),'CONTINUE')]/..")
    @FindBy(xpath = "//span[@class='MuiButton-label']")
    public WebElement continueBtn;

    @FindBy(xpath = "//*[@id='email']")
    public WebElement loginUserNameOrEmail;

    @FindBy(xpath = "(//*[@name='commit'])[2]")
    public WebElement nextBtn;

    @FindBy(xpath = "//*[@name='commit']")
    public WebElement signInBtn;

    @FindBy(xpath = "//*[contains(text(),'ACCEPT AND CONTINUE')]/.")
    public WebElement acceptAndContinueBtn;

    @FindBy(xpath = "//p[text()='No roles defined in the system. Please contact your system adminstrator']")
    public WebElement warningNoRolesDefined;

    @FindBy(xpath = "//span[text()='OK']")
    public WebElement wrngMsgOkBtn;

    @FindBy(xpath = "//div/img[@id='mx-logout']")
    public WebElement userIcon;

    @FindBy(xpath = "//*[@id=\"root\"]/div/header/div/nav/div[1]/div/div/div")
    public WebElement permissionDropdown;

    @FindBy(id = "mui-component-select-selectedRole")
    public WebElement userRoleDropDown;

    @FindBy(id = "mui-component-select-selectedProject")
    public WebElement selectProject;

}
