package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMProjectRolePage {

    

    public TMProjectRolePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //Headers & Titles refactorBy:vidya date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'Add Role')]")
    public WebElement addRoleHeader;

    @FindBy(xpath = "//h5[contains(text(),'ROLE DETAILS')]")
    public WebElement roleDetailsPageTitle;

    //Role Details Locators
    @FindBy(name = "projectName")
    public WebElement projectName;

    @FindBy(name = "projectId")
    public WebElement projectId;

    @FindBy(name = "roleName")
    public WebElement roleName;

    @FindBy(name = "roleDesc")
    public WebElement roleDesc;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement roleStartDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement roleEndDate;

    //Permission Details Locators
    @FindBy(xpath = "//label[contains(text(),'Group permission name')]/following-sibling::div/input")
    public WebElement groupPermissionName;

    @FindBy(xpath = "//label[contains(text(),'Group Permission Description')]/following-sibling::div/input")
    public WebElement groupPermissionDescription;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[2]")
    public WebElement permissionStartDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[2]")
    public WebElement permissionEndDate;

    //Buttons
    @FindBy(xpath = "//span[contains(text(), 'Save')]/..")
    public WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(), 'Cancel')]/..")
    public WebElement cancelButton;

    //Error messages
    @FindBy(xpath = "//p[text()='The Role name is required and cannot be left blank.']")
    public WebElement roleNameBlankError;

    @FindBy(xpath = "//p[text()='The start date is required and cannot be left blank.']")
    public WebElement roleStartDateBlankError;

    //refactored 03/14 Vinuta - error message changed
    @FindBy(xpath = "//*[contains(text(), 'The end date cannot be in the past')]")
    public WebElement lessOrEqualEndDateError;

    //refactored 03/14 Vinuta - error message changed
    @FindBy(xpath = "//*[contains(text(), 'The start date cannot be in the past')]")
    public WebElement lessStartDateError;

    @FindBy(xpath = "(//*[contains(text(),'Invalid date format')])[1]")
    public WebElement invalidStartDateError;

    @FindBy(xpath = "(//*[contains(text(),'Invalid date format')])[2]")
    public WebElement invalidEndDateError;

    @FindBy(xpath = "(//*[contains(text(),'The date entered does not exist. Please enter a valid date.')])[1]")
    public WebElement nonExistingStartDate;

    @FindBy(xpath = "//p[text()='The date entered does not exist. Please enter a valid date.']")
    public WebElement nonExistingEndDate;

    //refactored 03/14 Vinuta - locator changed
    @FindBy(xpath = "//*[contains(text(),'Role with this name already exists for this project')]")
    public WebElement duplicateRoleError;

    //Pop-up messages

    @FindBy(xpath = "//*[text()='Project Role has been successfully created']")
    public WebElement roleCreatedPopUp;

    @FindBy(xpath = "//span[text()='Ok']/..")
    public WebElement okButton;

    @FindBy(xpath = "//*[contains(text(), 'All changes will be lost; Do you want to continue?')]")
    public WebElement cancelWarningPopUp;

    //refactored 12/19 Vinuta
    @FindBy(xpath = "//*[text()='Cancel']/parent::button")
    public WebElement warningPopUpNoButton;

    //refactored 01/04/2019 Vinuta
    @FindBy(xpath = "//*[text()='Continue']/parent::button")
    public WebElement warningPopUpYesButton;

    @FindAll({
            @FindBy(xpath = "//li[@role='option']")
    })
    public List<WebElement> permissionOptions;

    @FindBy(xpath = "//*[contains(text(), 'No such')]")
    public WebElement warningNoPermGroup;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successSnackbar;

    @FindBy(xpath = "//*[text()='Project Role has been successfully created']")
    public WebElement roleCreatedMessage;

    @FindBy(xpath = "//button//span[text()=' COPY PERMISSION']")
    public WebElement copyPermissionsButton;

    @FindBy(id = "mui-component-select-roleForCloning")
    public WebElement roleSelectforCloningDropdown;

    @FindBy(xpath = "(//span[contains(text(), ' Save')]/..)[2]")
    public WebElement permissionDetailsSaveButton;

    @FindBy(xpath = "//span[text()='Permissions cloned successfully']")
    public WebElement cloningSuccessMessage;

    @FindBy(xpath = "//tr//td//input")
    public List<WebElement> allRadioButtons;

    @FindBy(xpath = "//button//span[text()='NEW PERMISSION SETTING']")
    public WebElement newPermissionSettingPanel;
}