package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class TMAddNewUserPage extends TMUtilities {

    

    public TMAddNewUserPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //By Vinuta refactorBy: Vidya 1-31-2020
    @FindBy(xpath = "//*[contains(text(),'Add User')]")
    public WebElement addUserHeader;

    @FindBy(xpath = "//h5[contains(text(),'USER DETAILS')]")
    public WebElement userDetailsPageTitle;

    @FindBy(name = "projectName")
    public WebElement projectName;

    @FindBy(name = "firstName")
    public WebElement addFirstName;

    @FindBy(name = "middleName")
    public WebElement addMiddleName;

    @FindBy(name = "lastName")
    public WebElement addLastName;

    @FindBy(name = "email")
    public WebElement addEmail;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement startDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement endDate;

    @FindBy(xpath="(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]/following-sibling::div/button")
    public WebElement endDateCalendarButton;

    @FindBy(xpath = "//span[text()='Clear']")
    public WebElement clearDate;

    @FindBy(xpath = "//*[@name='accountType']/..")
    public WebElement accountType;

    @FindBy(id = "mui-component-select-approver")
    public WebElement accountAuthType;

    @FindBy(xpath = "//*[@name='accountAuthType']/../div")
    public WebElement disabledAcctAuthType;

    @FindBy(xpath = "//div[@id='mui-component-select-applicationType']")
    public WebElement applicationType;

    @FindBy(xpath = "//label[contains(text(),'Authorization Date')]/following-sibling::div/input")
    public WebElement authDate;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary']")
    public WebElement saveButton;


    @FindBy(xpath = "//span[contains(text(),'Cancel')]/..")
    public WebElement cancelButton;

    @FindBy(id = "mui-component-select-overrideAuthReason")
    public WebElement overrideAuthReasonType;

    @FindBy(xpath = "//*[@name='isOverrideAuth']/..")
    public WebElement overrideAuthCheckbox;

    @FindBy(xpath = "//*[@name='isAccessToPHI']/..")
    public WebElement accessToPHICheckbox;

    @FindBy(id = "mui-component-select-phiReason")
    public WebElement phiReasonType;

    @FindBy(xpath = "//*[@name='isAccessToPII']/..")
    public WebElement accessToPIICheckbox;

    @FindBy(id = "mui-component-select-piiReason")
    public WebElement piiReasonType;

    @FindBy(xpath = "//input[@name='ismaerskEmp']")
    public WebElement maerskEmpCheckbox;

    @FindBy(name = "maerskId")
    public WebElement maerskEmpIdTextbox;

   /* @FindBy(xpath = "//*[text()='add_circle']")
    public WebElement addMaxId;*/

    @FindBy(xpath = "//span[text()='add_circle']")
    public WebElement addMaxId;

    @FindBy(xpath = "//*[text()='SUCCESS MESSAGE']")
    public WebElement successSnackbar;

    @FindBy(xpath = "//*[text()='User Created Successfully']")
    private WebElement userCreatedMessage;

    @FindBy(xpath = "//span[text()=\"Continue\"]/..")
    public WebElement continueBtnOnUserCreation;

    @FindBy(xpath = "//button/span[text()='OK']")
    public WebElement OkButton;

    @FindBy(id = "mui-component-select-inactivateReason")
    public WebElement accInactivationReason;

    @FindBy(id = "mui-component-select-reactivateReason")
    public WebElement accReactivationReason;

    @FindBy(xpath = "//span[contains(text(),'No employee record exists for a given MAX ID | traceId:')]")
    public WebElement noMaxIDFoundError;

    @FindBy(xpath = "//p[text()='Account Manager and Account Approvers must be identified']")
    private WebElement noApproversError;

    @FindBy(xpath = "//span[text()=\"Continue\"]/..")
    private WebElement continueBtnOnApproversError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[1]")
    public WebElement blankMaxIdError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[2]")
    public WebElement blankFirstNameError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[3]")
    public WebElement blankLastNameError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[4]")
    public WebElement blankEmailError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[4]")
    public WebElement blankStartDateError;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[3]")
    public WebElement blankEmailErrorNew;

    @FindBy(xpath = "(//*[contains(text(), 'is required and cannot be left blank.')])[6]")
    public WebElement blankAccountTypeError;

    @FindBy(xpath = "//ul/li[contains(text(), 'application')]/preceding-sibling::*[1]")
    public WebElement blankAccountTypeOption;

    @FindBy(xpath = "//*[contains(text(), 'The Start date cannot be in the past')]")
    public WebElement priorDateError;

    //refactoring 10-19-18
    @FindBy(xpath = "//*[contains(text(), 'The End Date must be greater than the Start Date')]")
    public WebElement lessOrEqualDateError;

    @FindBy(xpath = "//*[contains(text(), 'The end date cannot be in the past')]")
    public WebElement priorEndDateError;

    @FindBy(xpath = "(//*[contains(@class, 'mdl-textfield mdl-js-textfield mdl-textfield--floating-label text-left')])[2]/*[last()]")
    public WebElement endDateErrorMessage;

    @FindBy(xpath = "//*[contains(text(), 'add_circle')]")
    public WebElement addMaxIdButton;

    @FindBy(xpath = "//*[contains(text(),'INACTIVATE IMMEDIATELY')]/..//input[@type='checkbox']/..")
    public WebElement inactiveImmediatelyCheckbox;

    @FindBy(xpath = "//*[contains(text(), 'If you navigate away, your information will not be saved')]")
    public WebElement cancelWarningPopUp;

    @FindBy(xpath = "//span[text()='Cancel']")
    public WebElement warningPopUpCancelButton;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement warningPopUpContButton;

    @FindBy(xpath = "//*[contains(text(),'ERROR MESSAGE')]")
    public WebElement errorSnackbar;

    @FindBy(xpath = "//*[contains(text(),'User you are trying to create already is an active user in the system')]")
    public WebElement duplicateUserError;

    @FindBy(xpath = "//*[text()='The user account will be deactivated when you click the Save button']")
    public WebElement inactivateUserMessage;

    @FindBy(xpath = "//*[text()='The user has been inactivated successfully']")
    public WebElement inactivateSuccessMessage;

    @FindBy(xpath = "//*[text()='The user has been reactivated successfully']")
    public WebElement reactivateSuccessMessage;

    @FindBy(xpath = "//p[text()='End date must be empty to reactivate a user.']")
    public WebElement endDateReactivateError;

    @FindBy(xpath = "//*[@name='defaultRoleType']/..")
    public WebElement roleDropDown;

    @FindBy(xpath = "(//span[@class='MuiIconButton-label'])[9]")
    public WebElement rolesStartDateCalender;

    @FindBy(xpath = "(//span[@class='MuiButton-label'])[9]")
      public WebElement StartDateOkButton;

//    @FindBy(xpath = "//button[contains(text(),'ADD USER ROLES')]")
    @FindBy(xpath = "//span[@class='MuiButton-label']")
    public WebElement addRoleButton;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[2]")
    public WebElement roleStartDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[2]")
    public WebElement roleEndDate;

    @FindBy(xpath = "//span[text()=' ADD USER ROLES']/parent::button")
    public WebElement addRolePlusSign;

    @FindBy(xpath = "//input[@name='roleDescription']")
    public WebElement roleDisc;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMsg;

    @FindBy(xpath = "//span[text()='No']")
    public WebElement warningPopUpNoButton;

    @FindBy(xpath = "//span[text()='Yes']")
    public WebElement warningPopUpYesButton;

    @FindBy(xpath = "//span[@id='client-snackbar' and text() = 'Team Successfully Updated']")
    public WebElement teamUpdatedsuccessMsg;


    // moved these elements from addTeamPage


    @FindBy(xpath = "//h5[text()='ACTIVE']")
    public WebElement activeStatus;

    @FindBy(xpath = "//h5[text()='FUTURE']")
    public WebElement futureStatus;

    @FindBy(xpath = "//h5[text()='INACTIVE']")
    public WebElement inactiveStatus;

    //Umid added extra elements

    @FindBy(xpath = "//h5[text()='TEAM DETAILS']")
    public WebElement teamDetailsHeader;

    @FindBy(xpath = "//input[@name='teamName']")
    public WebElement teamName;

    @FindBy(xpath = "//div[@id='mui-component-select-businessUnitName']")
    public WebElement businessUnit;

    @FindBy(xpath = "//input[@name='teamDescription']")
    public WebElement teamDescription;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement cancelBTN;

    @FindBy(xpath = "//button[contains(text(),'EDIT TEAM')]")
    public WebElement editTeam;

    @FindBy(xpath = "//h5[text()='USERS LIST']")
    public WebElement teamUserListHeader;

    @FindBy(xpath = "//button[@id='add-user-button']")
    public WebElement addUserListBTN;

    @FindBy(xpath = "//label[text()='User Name']/parent::div/div/input")
    public WebElement userListName;

    @FindBy(xpath = "//label[text()='User Name']/parent::div/div/input")
    public List<WebElement> userListNames;

    @FindBy(xpath = "//label[text()='User Name']/parent::div/div/input")
    public List <WebElement> countAddingUsers;

    @FindBy(xpath = "//i[text()='edit']/parent::button/parent::div/p/parent::div/parent::div")
    public List <WebElement> countListUsers;

    @FindBy(xpath = "//i[text()='edit']/parent::button/parent::div/p[1]")
    public List <WebElement> userListsNames;

    @FindBy(xpath = "//span[text()='Team Supervisor']/parent::label/span/span")
    public WebElement teamSupervisor;

    @FindBy(xpath = "//label[text()='Email']/parent::div/div/input")
    public WebElement userListEmail;

    @FindBy(xpath = "//label[text()='Email']/parent::div/div/input")
    public List <WebElement> userListEmails;

    @FindBy(xpath = "//label[text()='maersk ID']/parent::div/div/input")
    public WebElement userListMaxID;

    @FindBy(xpath = "//label[text()='maersk ID']/parent::div/div/input")
    public List <WebElement> userListMaxIDs;

    @FindBy(xpath = "//span[text()='SUPERVISOR']/parent::div/parent::div/parent::div/parent::div/div/div")
    public WebElement supervisorBC;

    @FindBy(xpath = "//div[@class='mx-user-card-body-left m-0']")
    public List<WebElement> regularBC;

    @FindBy(xpath = "//span[text()='SUPERVISOR']/parent::label/span/span")
    public WebElement supervisorEditCheckBox;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveBtn;

    @FindBy(xpath = "//i[text()='edit']")
    public List<WebElement> editBtns;

    @FindBy(xpath = "//i[text()='delete']")
    public WebElement deleteBtn;

    @FindBy(xpath = "//div[4]/div[1]/div[1]/div[1]/h4[1]")
    public WebElement frstProName;

    @FindBy(xpath = "//div[4]/div[1]/div[1]/div[1]/button[1]")
    public WebElement firstProject;

    @FindBy(xpath = "//input[@name='isOverrideAuth']")
    public WebElement authCheckbox;

    @FindBy(id = "mui-component-select-overrideAuthReason")
    public WebElement authReasonType;

    @FindBy(xpath = "//input[@name='isAccessToPHI']")
    public WebElement isAccessToPHI;

    @FindBy(xpath = "//input[@name='isAccessToPII']")
    public WebElement isAccessToPII;

    @FindBy(id = "mui-component-select-PHIReasonType")
    public WebElement PHIReasonTypeOff;

    @FindBy(id = "mui-component-select-PIIReasonType")
    public WebElement PIIReasonTypeOff;

    @FindBy(id = "mui-component-select-defaultRoleType")
    public WebElement defaultRoleTypeOff;

    @FindBy(xpath = "//span[@title='Clear']")
    public WebElement closeButtonOnErrorAlert;


    public WebElement get_duplicateUserError(){return this.duplicateUserError;}

    public WebElement get_noApproversError() {
        return this.noApproversError;
    }

    public WebElement get_continueBtnOnApproversError() {
        return this.continueBtnOnApproversError;
    }

    public WebElement get_userCreatedMessage() {
        return this.userCreatedMessage;
    }

    public boolean isDisabledByDefault(String element) {
        if (Driver.getDriver().findElement(By.name(element)).getAttribute("value").equals("false")) {
            return true;
        }
        return false;
    }

    public String get_maerskEmp_Status() {
        return maerskEmpCheckbox.getAttribute("value");
    }

    public void set_maerskEmp_Status(String status) {
        String current_status = maerskEmpCheckbox.getAttribute("value");
        if (status.equalsIgnoreCase("True")) {
            if (current_status.equalsIgnoreCase("False"))
                jsClick(maerskEmpCheckbox);
        }
        if (status.equalsIgnoreCase("False")) {
            if (current_status.equalsIgnoreCase("True"))
                maerskEmpCheckbox.click();
        }
    }

    public void set_maerskEmp_Id(String maxID) {
        Assert.assertTrue(maerskEmpIdTextbox.isDisplayed(), "Max Id text box is not displayed");
        maerskEmpIdTextbox.clear();
        maerskEmpIdTextbox.sendKeys(maxID);
    }

    public void add_maxId() {

        Assert.assertTrue(addMaxId.isEnabled(), "Add Max ID button is not enabled");
        addMaxId.click();
    }

    public boolean get_maxIDError() {
        return noMaxIDFoundError.isDisplayed();
    }

    public WebElement get_addUserHeader() {
        return this.addUserHeader;
    }

    public WebElement get_userDetailsPageTitle() {
        return this.userDetailsPageTitle;
    }


    public boolean verifyReadOnlyElByValue(String value) {
        try {
            WebElement el = Driver.getDriver().findElement(By.cssSelector("[value='" + value + "']"));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyNotEditable(WebElement el, String text) {
        boolean b = false;
        waitFor(1);
        try {
            clearAndFillText(el, text);
        }
        catch(Exception e){}
        if (!el.getAttribute("value").equalsIgnoreCase(text)) {
            b = true;
        }
        return b;
    }

    /*
    This method verifies if element has readonly attribute
    param - Webelement
    return true if readonly attribute ispresent, else return false
    Author - Vinuta
    Date - Jan 10 2019
     */
    public boolean verifyReadOnly(WebElement el){
        if(el.getAttribute("readonly").equalsIgnoreCase("true")) return true;
        return false;
    }

}
