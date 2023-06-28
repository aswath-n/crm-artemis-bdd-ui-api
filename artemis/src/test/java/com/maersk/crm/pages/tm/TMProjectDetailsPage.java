package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class TMProjectDetailsPage {

    

    public TMProjectDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //refactorBy:vidya Date;03-02-2020
    @FindBy(xpath="//h5[contains(text(),'Add Project')]")
    public WebElement addProjectLogo;

    @FindBy(name = "projectName")
    public WebElement projectName;

 /* Refactored by -Paramita 12/10/2019 -Reason to automate code for Go-Live Date*/
    @FindBy(xpath="//input[@id='state']/..//div")
    public WebElement state;

    @FindBy(xpath = "//*[@id='menu-state']/div[2]/ul/li")
    public List<WebElement> stateList;

    public WebElement stateName(int i){
       return  Driver.getDriver().findElement(By.xpath("//*[@id='menu-state']/div[2]/ul/li["+i +"])"));
    }

    @FindBy(xpath = "//div[@id='menu-state']/div/ul/li[4]")
    public WebElement AR;

    @FindBy(name="programName")
    public WebElement programName;

    @FindBy(name="contractId")
    public WebElement contractId;

    @FindBy(name="stateAgencyName")
    public WebElement stateAgencyName;

    @FindBy(xpath="//*[text()='Contract Start Date']/following-sibling::div/input")
    public WebElement contractStartDate;

    @FindBy(xpath = "//*[text()='Contract End Date']/following-sibling::div/input")
    public WebElement contractEndDate;

    @FindBy(xpath ="//li[contains(text(),'Active')]")
    public WebElement activeStatus;

    /*@FindBy(xpath ="//*[@id='menu-provStatus']/div[2]/ul/li[2]")
    public WebElement activeStatus;*/

    @FindBy(xpath="//*[@id='menu-provStatus']/div[2]/ul/li[3]")
    public WebElement inactiveStatus;

//    @FindBy(xpath="//button[contains(@class, 'mx-btn-border mx-btn-primary')]/span[1]")
    @FindBy(xpath="//button[contains(span, ' Save')]")
    public WebElement saveButton;

    @FindBy(xpath="//*[@id=\"root\"]/div/main/div/div/div/div[2]/div/div[15]/div/div/table/tbody/tr[1]/td[1]/div/div/div/div")
    public WebElement role;

    @FindBy(xpath = "//*[@id='menu-contactRole']/div[2]/ul/li[2]")
    public WebElement accountApprover;

    @FindBy(xpath="//*[@id='menu-contactRole']/div[2]/ul/li[3]")
    public WebElement accountManager;

    @FindBy(xpath="//*[@id='menu-contactRole']/div[2]/ul/li[4]")
    public WebElement contact;

    @FindBy(name = "firstName")
    public WebElement firstName;

    @FindBy(name="middleName")
    public  WebElement middleName;

    @FindBy(name="lastName")
    public WebElement lastName;

    @FindBy(xpath="//*[@id='root']/div/main/div/div/div/div[2]/div/div[15]/div/div/table/tbody/tr[1]/td[5]/div/div/div")
    public WebElement phoneNumber;

    @FindBy(name="email")
    public WebElement email;

    @FindBy(xpath="//*[@id='root']/div/main/div/div/div/div[2]/div/div[15]/div/div/table/tbody/tr[1]/td[7]/button")
    public WebElement projectContactSave;

    //refactorBy: Vidya Date:28-02-2020
    @FindBy(xpath = "//*[@id='root']//h5")
    public WebElement viewProjectDashboard;

    @FindBy(name = "projectName")
    public WebElement editProjectName;

    @FindBy(xpath = "//input[@id='state']/../div")
    public WebElement editState;

    @FindBy(name = "programName")
    public WebElement editProgramName;

    @FindBy(name = "contractId")
    public WebElement editContractId;

    @FindBy(name = "stateAgencyName")
    public WebElement editStateAgencyName;

    @FindBy(xpath = "//*[contains(text(), 'Contract Start Date')]/..//input")
    public WebElement editStartDate;

    @FindBy(xpath = "//*[contains(text(), 'Contract End Date')]/..//input")
    public WebElement editEndDate;

    @FindBy(xpath = "//input[@name='provStatus']/..")
    public WebElement editProvStatus;

    public static String provStatusValueXpath = "//li[@class='jss95 jss542 jss545 jss549 jss550 jss540']";
    public String stateNameValueXpath = "//li[@class='jss95 jss560 jss563 jss567 jss568 jss558'])";

    //@FindBy(xpath = "//span[contains(text(), 'Save')]")
    //public WebElement saveButton;

    //refactorBy; Vidya Date:1-31-2020
    @FindBy(xpath = "//span[text()='keyboard_backspace']/../..")
    public WebElement backToProjectsButton;

    @FindAll(
            @FindBy(css = ".mx-table tbody tr")
    )
    public List<WebElement> projectContactRows;

    public final String contactRole = "td:nth-of-type(1)";
    public final String contactFirstName = "td:nth-of-type(2)";
    public final String contactMiddleName = "td:nth-of-type(3)";
    public final String contactLastName = "td:nth-of-type(4)";

    public final String contactPhoneNumber ="td:nth-of-type(5)"; //"formatted-ssn-input";
    public final String contactEmail = "td:nth-of-type(6)";
    public final String contactEditButton = "td:nth-of-type(7) button:nth-of-type(1)";
    public final String contactCancelButton = "td:nth-of-type(7) button:nth-of-type(2)";


    public void clickSaveButton() {
        BrowserUtils.waitFor(1);
        saveButton.click();
    }

    public void clickBackButton() {
        BrowserUtils.waitFor(1);
        backToProjectsButton.click();
    }

    public String getCurrentProjectName() {
        return editProjectName.getAttribute("value");
    }

    @FindBy (xpath = "//*[@name='contactRole']/..")
    public WebElement projectRoleDropdown;

    @FindBy (xpath = "//*[@name='firstName']")
    public WebElement createRoleFirstName;

    @FindBy (xpath = "//*[@name='lastName']")
    public WebElement createRoleLastName;

    @FindBy (xpath = "//*[@name='middleName']")
    public WebElement createRoleMiddleName;

    @FindBy (xpath = "//*[@name='fieldValue']")
    public WebElement createRolePhone;

    @FindBy (xpath = "//*[@name='email']")
    public WebElement createRoleEmail;

    @FindBy (xpath = "//*[text()= 'add']/../..")
    public WebElement createRoleSaveButton;

    @FindBy(xpath = "//tr[1]/td[6]")
    public WebElement emailIDoffirstRecord;

    @FindBy(xpath = "//input[@name='email']")
    public WebElement emailIDofSecondRecord;

    @FindBy(xpath = "(//i[text()='edit'])[2]")
    public WebElement secondEditButton;


    //ToDo change the xpath to dynamic
    //refactored 12/19 Vinuta
    @FindBy(xpath = "//*[text()=\"ERROR MESSAGE\"][@id='client-snackbar']")
    public WebElement errorSnackBarTitle;

    @FindBy(xpath="//span[contains(text(),'An existing Project record already has the same Project Name, State and Program Name. Please enter different values for at least one of these fields.')][@id='client-snackbar']")
    public WebElement duplicateProjError;

    @FindBy(xpath="//*[@id='root']/div/main/div/div/div/div[2]/div/div[9]/div/p")
    public WebElement contractErrorMessage;

    //By Vinuta//Refactored--Aswath 07/19/19
    @FindBy(xpath="//span[contains(text(),'group')]")
    //@FindBy(xpath = "(//a[contains(@href, '/tenant/projectDetails/')])[2]")
    public WebElement viewUserList;

    //Refactored--Aswath 07/19/19
    @FindBy(xpath="//span[contains(text(),'person')]")
   //@FindBy(xpath = "(//a[contains(@href, '/tenant/projectDetails/')])[3]")
    public WebElement viewRoleList;

    //Refactored--Aswath 07/19/19
    @FindBy(xpath = "//span[contains(text(),'build')]")
    // @FindBy(xpath = "(//a[contains(@href, '/tenant/projectDetails/')])[5]")
    public WebElement projConfig;

    @FindBy(xpath = "//span[contains(text(),'design_services')]")
    public WebElement designerConfig;

    @FindBy(xpath = "//a[text()='Task Template']")
    public WebElement taskTemplateNavigation;

    @FindBy(xpath = "//*[text()='PROJECT DETAILS']")
    public WebElement projectDetailsTitle;

    //refactorBy:Vidya 1-31-20202
    @FindBy(xpath="//*[text()='Contract end date cannot be equal or less than contract start date']")
    public WebElement contractDateErrorMessage;

    @FindBy(xpath = "//*[text()='Account Approver']")
    public WebElement acctApprAdded;

    @FindBy(xpath = "//*[text()='Account Manager']")
    public WebElement acctMgrAdded;

    @FindBy(xpath = "(//a[contains(@href, '/tenant/projectDetails/')])[5]")
    public WebElement holidaysNavigation;

    @FindBy(xpath = "//a[text()='Time Zone & Working Hours']")
    public WebElement timeZoneWorkingHoursNavigation;

    @FindBy(xpath = "//a[text()='Project ID']")
    public WebElement projectIdNavigation;

    @FindBy(xpath = "//a[text()='Task Type']")
    public WebElement taskTypeNavigation;

    @FindBy(xpath = "//a[text()='Upload Project']")
    public WebElement uploadProjectNavigation;

    @FindBy(xpath = "//div[@id='v-pills-tabContent']//h5")
    public WebElement lblSelectedConfigurationHeader;

    public boolean isApproverAdded(){
        try {
            if (acctApprAdded.isDisplayed() && acctMgrAdded.isDisplayed()) return true;
            else return false;
        }catch (Exception e){
            return false;
        }
    }
    @FindBy(xpath="//span[text()='add']/ancestor::button")
    public WebElement addRole;

    //Refactored--Aswath 07/19/19
    @FindBy(xpath = "//span[contains(text(),'verified_user')]")
    //@FindBy(xpath = "(//a[contains(@href, '/tenant/projectDetails/')])[4]")
    public WebElement viewPermissionList;

    @FindBy(xpath = "//span[text()='table_chart']")
    public WebElement businessUnitIcon;

    @FindBy(xpath = "//span[text()='title']")
    public WebElement teamIcon;

    @FindBy(xpath = "//*[@name='uploadType']/..")
    public WebElement uploadTypeOptions;

/* Author -Paramita */

    @FindBy(xpath="//*[@class='MuiTableRow-root']/tr")
    public List<WebElement> viewUserListsize;

    @FindBy(xpath="//tbody[@class='MuiTableBody-root']/tr/td[4]")
    public List<WebElement> viewUserListMaxID;

    @FindBy(xpath="//label[text()='Go-Live Date']/..//p")
    public WebElement goLiveDateErrorMessage;

    @FindBy(xpath="//label[text()='Contract Start Date']/..//p")
    public WebElement goLiveContractDateErrorMessage;

    @FindBy(xpath = "//div[@id='menu-state']/div/ul/li[4]")
    public WebElement stateoption;

    /* Author -Paramita */
    @FindBy(xpath = "//*[text()='Go-Live Date']/following-sibling::div/input")
    public WebElement goLiveDate;

    /* Refactored by Paramita  Date -10/12/2019 */
    @FindBy(xpath="//input[@id='provStatus']/..")
    public WebElement provisioningStatus;

    @FindBy(xpath="//*[text()='SUCCESS MESSAGE']")
    public WebElement  projSuccessStatus;

    @FindBy(xpath="//span[text()='check']")
    public WebElement  buttonSave;
    @FindBy(xpath = "//*[text()='build']")
    public WebElement projectConfigSideBar;

    //Below elements are added for validation of GA view project details page BY: Vidya Date:- 1-22-2020
    @FindBy(xpath = "//label[text()='Contract Start Date']/..//input")
    public WebElement getStartDate;

    @FindBy(xpath = "//label[text()='Contract End Date']/..//input")
    public WebElement getEndDate;

    @FindBy(xpath = "//label[text()='Go-Live Date']/..//input")
    public WebElement getGoLiveDate;

    @FindBy(id = "provStatus")
    public WebElement getProvStatus;

    @FindBy(id = "timeZone")
    public WebElement getTimeZone;

    @FindBy(id = "state")
    public WebElement getState;

    @FindBy(xpath="//td[text()='Account Manager']//following-sibling::td")
    public List<WebElement> actMngrDetails;

    @FindBy(xpath="//td[text()='Account Approver']//following-sibling::td")
    public List<WebElement> actApvrDetails;

    @FindBy(xpath="//td[text()='Octavius']/..//following-sibling::td")
    public List<WebElement> contact1Details;

    @FindBy(xpath="//td[text()='Jeffrey']/..//following-sibling::td")
    public List<WebElement> contact2Details;
	
    @FindBy(xpath="//ul[@role='listbox']//li")
    public List<WebElement>  timeZoneValue;

    @FindBy(xpath="//div[@id='mui-component-select-timeZone']")
	public WebElement timezonefield;

    @FindBy(xpath="//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMsg;

    @FindBy(xpath="//span[text()='Continue']")
    public WebElement warningMsgCntBtn;

    @FindBy(xpath="//span[text()='Cancel']")
    public WebElement warningMsgCancelBtn;

    //refactorBy:Vidya 1-31-2020
    @FindBy(xpath="//p[text()='The Time Zone is required and cannot be left blank.']")
    public WebElement timeZoneFieldLevelError;

    @FindBy(xpath = "//a[contains(text(), 'Lookup Configuration')]")
    public WebElement lookupconfiguration;

    @FindBy(xpath = " //input[contains(@name, 'selectedEnumTable')]/..")
    //@FindBy(xpath="//*[@id='mui-component-select-selectedEnumTable']")
    public WebElement selectedEnumTable;
    @FindBy(xpath = "//ul[contains(@role, 'listbox')]/li")
    public List<WebElement> selectenums;

    public WebElement getEnumTableWithValue(String val)
    {
        return Driver.getDriver().findElement(By.xpath("//ul[contains(@role, 'listbox')]/li[text()='"+val+"']"));
    }

    @FindBy(xpath = "//span[@class='MuiIconButton-label']")
    public WebElement calenderBtn;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarokbutton;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    public WebElement calendarclearbutton;

    @FindBy(xpath = " //input[contains(@name, 'selectedDatabase')]/..")
    public WebElement databaselist;

    @FindBy(xpath = "//span[contains(text(), 'search')]")
    public WebElement searchlookupbutton;
    @FindBy(xpath = "//button[contains(., 'ADD LOOKUP')]")
    public WebElement addlookupbutton;
    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root')]/descendant::span[contains(text(), 'add')]")
    public WebElement addlookuprecordbutton;
    @FindBy(xpath = "//span[contains(text(), 'clear')]")
    public WebElement clearbutton;
    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backArrow;
    @FindBy(xpath = "//*[contains(text(), 'Look Up Value Successfully Created')]")
    public WebElement successmesg;
    @FindBy(xpath = "//span[contains(text(), 'check')]")
    public WebElement okbutton;
    @FindBy(xpath = "//tr[contains(@class, 'MuiTableRow-root MuiTableRow-head')]/th")
    public List<WebElement> colvalues;
    @FindBy(xpath = "//div[contains(@class, 'mx-helpertext-overflow')]")
    public List<WebElement> errormesg;
    @FindBy(xpath = "//div[contains(@class, 'mx-helpertext-overflow')]")
    public WebElement valueValidation;
    @FindBy(xpath = "(//div[contains(@class, 'mx-helpertext-overflow')])[2]")
    public WebElement descriptionvalidation;
    @FindBy(xpath = "(//div[contains(@class, 'mx-helpertext-overflow')])[3]")
    public WebElement reportlabelvalidation;
    @FindBy(xpath = "(//div[contains(@class, 'mx-helpertext-overflow')])[4]")
    public WebElement orderbydefaultvalidation;
    @FindBy(xpath = "(//div[contains(@class, 'mx-helpertext-overflow')])[5]")
    public WebElement startdatevalidation;
    @FindBy(xpath = "//input[contains(@name, 'selectedValue')]")
    public WebElement value;
    @FindBy(xpath = "//input[contains(@name, 'selectedDescription')]")
    public WebElement description;
    @FindBy(xpath ="//input[contains(@name, 'selectedReportLabel')]")
    public WebElement reportlabel;

    @FindBy(xpath ="//input[contains(@name, 'selectedScope')]")
    public WebElement scope;

    @FindBy(xpath ="//input[contains(@name, 'selectedOrderByDefault')]")
    public WebElement orderByDefault;

    @FindBy(xpath = "//*[contains(text(), 'Look Up Value Successfully Created')]")
    public WebElement lookupSuccessMsg;

    public WebElement getSuccessMessage(String val)
    {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(), '"+val+"')]"));
    }

    public WebElement getEditBtnforLookUpRecord(String val)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//td[text()='"+val+"']/following-sibling::td//button"));
    }

    @FindBy(xpath ="//input[contains(@name, 'selectedOrderByDefault')]")
    public WebElement orderbydefault;

    @FindBy(xpath ="//input[contains(@id, 'selectedStartDate')]")
    public WebElement selectedStartDate;

    @FindBy(xpath ="//a[contains(text(), 'Holidays')]")
    public WebElement navigationlink;

    @FindBy(xpath =" //h5[contains(text(), 'HOLIDAY')]")
    public WebElement naviigatedpage;

    @FindBy(xpath ="//input[contains(@id, 'selectedEndDate')]")
    public WebElement selectedEndDate;

    @FindBy(xpath="//i[text()='edit']")
    public List<WebElement> editButton;

    @FindBy(xpath="//i[text()='check']")
    public WebElement checkButton;

    @FindBy(name="firstName")
    public List <WebElement> firstNameList;

    @FindBy(name="lastName")
    public List <WebElement> lastNameList;

    @FindBy(name="fieldValue")
    public List <WebElement> phoneNumList;

    @FindBy(xpath = "//a[text()='Business Days']")
    public WebElement businessDaysNavigation;

    @FindBy(xpath = "//*[contains(text(),' ADD CONTACT DETAILS')]")
    public WebElement addContactDetailsButton;

    @FindBy(xpath = "//*[contains(text(), 'NO RECORD AVAILABLE')]")
    public WebElement noProjectContacts;

    @FindBy(xpath = "//tr[@class='MuiTableRow-root']/td")
    public List<WebElement> projContactTableColValues;

    @FindBy(xpath = "//*[contains(text(),'Invalid Project name. Only A-Za-z0-9-_ are allowed | traceId:')]")
    public WebElement invalidProjectNameErrorMsg;

    @FindBy (xpath = "(//div[@role='button'])[1]")
    public WebElement selectDatabaseDropdown;

}
