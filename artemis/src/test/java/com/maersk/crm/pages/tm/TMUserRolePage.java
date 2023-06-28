package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class TMUserRolePage {

    

    public TMUserRolePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h5[contains(text(),'USER ROLES')]")
    public WebElement userRoleSection;

    @FindBy(xpath = "//input[@name='defaultRoleType']/..//div[@role='button']")
    public WebElement userRoleDropdown;

    public WebElement userRoleDropdownWithIndex(int index)
    {
        return Driver.getDriver().findElement(By.xpath("(//input[@name='defaultRoleType']/..//div[@role='button'])["+index+"]"));
    }

    @FindBy(xpath = "//input[@name='defaultRoleType']/..//div[@role='button']")
    public List<WebElement> defaultRoleDropdown;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> activeRoleNames;

    @FindBy(xpath = "//label[text()='Role Description']/..//input")
    public WebElement roleDescription;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[2]")
    public WebElement roleStartDate;

    @FindBy(xpath = "//i[text()='add']/..")
    public WebElement addRoleIcon;

   @FindBy(xpath = "(//span[text()='clear'])[1]")
    public List<WebElement> deleteRoleIcon;

   @FindBy(xpath = "//span[text()='delete']")
   public WebElement delRoleIcon;

    @FindBy(xpath = "//span[text()='delete']")
    public WebElement userDdeleteRoleIcon;

    @FindBy(xpath = "(//label[text()='End Date']/../div/input)[2]")
    public WebElement roleEndDate;

    @FindBy(xpath = "//*[contains(text(), 'The End Date must be greater than or equal to the Start Date')]")
    public WebElement priorRoleEndDateError;

    @FindBy(xpath = "(//span[text()='clear'])[1]")
    public WebElement userRoleCancelButton;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement userRoleBackArrow;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]/../../following-sibling::div//p")
    public WebElement warningMessageTxt;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//*[text()='Cancel']/parent::button")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//h5[contains(text()[1],'User List')]")
    public WebElement userListScreen;

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement userRoleAddButton;

    @FindBy(xpath = "//h5[contains(text()[1],'Add User')]")
    public WebElement addUserScreen;

    @FindBy(xpath = "//*[contains(text(),'The Default RoleType type is required and cannot be left blank')]")
    public WebElement selectRoleError;

    @FindBy(xpath = "//*[contains(text(),'The Start date is required and cannot be left blank')]")
    public WebElement startDateError;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[4]")
    public WebElement startDateCalendarButton;

    @FindBy(xpath = "//span[@class='MuiButton-label' and text()='OK']")
    public WebElement startDateSelectOkButton;

    public WebElement startDateCalendarButtonAtIndex(int index)
    {
        return Driver.getDriver().findElement(By.xpath("(//button[@class='MuiButtonBase-root MuiIconButton-root'])["+index+"]"));
    }

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[2]")
    })
    public List<WebElement> userName;

    @FindBy(xpath = "//div[@id='mui-component-select-defaultRoleType']")
    public List<WebElement> savedUserRoles;

    //@FindBy(xpath = "//label[text()='Default Role']/..//div/div[@role='button']")
    @FindBy(xpath = " //label[contains(text(),'Role')]/..//div/div[@role='button']")
    public List<WebElement> defaultRoleValue;

    @FindBy(xpath = "//*[text()='DEFAULT ROLES']/following::label[text()='Start Date']/..//input")
    public List<WebElement> roleStartDateValue;

    @FindBy(xpath = "//*[text()='DEFAULT ROLES']/following::label[text()='End Date']/..//input")
    public List<WebElement> roleEndDateValue;

    @FindBy(xpath = "(//span[@class='MuiButton-label'])[2]")
    public WebElement editRoleSavButton;

    @FindBy(xpath = "//span[contains(text(),'User successfully updated')]")
    public WebElement editSuccessMesg;

    @FindBy(xpath = "//h5[text()='ACCOUNT INACTIVATION']")
    public WebElement accountActivation;

    @FindBy(xpath = "//*[text()='User Updated Successfully']")
    public WebElement updateSuccessMesg;


    @FindBy(xpath = "//label[text()='Default Role']/..//div/div[@role='button']/..//*[name()='svg']")
    public WebElement defaultRolebutton;

    @FindBy(xpath = "//span[text()=' WARNING MESSAGE']/../../..//div[3]/p")
    public WebElement warningMesgText;

    @FindBy(xpath = "//span[text()='Yes']/..")
    public WebElement warningMesgYes;

    @FindBy(xpath = "//span[text()='No']/..")
    public WebElement warningMesgNo;

    @FindBy(xpath = "//*[text()='User Created Successfully']")
    public WebElement userCreatedMessage;

    @FindBy(xpath = "//*[@name='defaultRoleType']/..")
    public WebElement roleDropDown;

    @FindBy(xpath = "//ul[@class='pagination']/li/a")
    public List<WebElement> lnkPageCount;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[8]")
    })
    public List<WebElement> userStatus;

    @FindBy(xpath = "//span[contains(text(),'add')]/../..")
    public WebElement rolebutton;

    //Buttons
    @FindBy(xpath = "//span[text()=\" Save \"]/..")
    public WebElement RolesaveButton;

    @FindBy(xpath = "//span[@class='MuiButton-label']")
    public WebElement addRoleButton;

    @FindBy(xpath = "//input[@name='isDefaultRole']")
    public List<WebElement> defaultRoleToggle;




}