package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMAddTeamPage extends TMUtilities {

    

    public TMAddTeamPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    //refatorBy: Vidya Date:28-02-2020
    @FindBy(xpath = "//h5[contains(text(),'ADD TEAM')]")
    public WebElement addTeamHeader;

    @FindBy(name = "teamName")
    public WebElement teamName;

    @FindBy(xpath = "//input[@id='businessUnitName']/..")
    public WebElement buDropDownBox;

    @FindBy(xpath = "//div[@id='menu-businessUnitName']//li")
    public List<WebElement> buDropDownBoxValue;

    @FindBy(xpath = "(//label[contains(text(),'Start Date')]/following-sibling::div/input)[1]")
    public WebElement teamStartDate;

    @FindBy(xpath = "(//label[contains(text(),'End Date')]/following-sibling::div/input)[1]")
    public WebElement teamEndDate;

    @FindBy(name = "teamDescription")
    public WebElement teamDescription;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement teamSaveButton;

    @FindBy(xpath = "//i[text()='clear']")
    public WebElement teamCancelButton;

    @FindBy(xpath = "//span[text()='keyboard_backspace']")
    public WebElement backArrow;

    @FindBy(xpath = "//input[@name='teamName']/../following-sibling::p")
    public WebElement teamNameFieldError;

    //refactorBy:vidya Date:02-28-20202
    @FindBy(xpath = "//input[@name='businessUnitName']/../following-sibling::p")
    public WebElement buFieldError;

    @FindBy(xpath = "//label[text()='Start Date']/following-sibling::p")
    public WebElement startDateFieldError;

    @FindBy(xpath = "//label[text()='End Date']/following-sibling::p")
    public WebElement endDateFieldError;

    @FindBy(xpath = "//span[contains(text(),'WARNING MESSAGE')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//p[contains(text(),'If you navigate away, your information will not be saved')]")
    public WebElement warningMessageTxt;

    @FindBy(xpath = "//span[text()='check']")
    public WebElement warningMsgContinueBtn;

    @FindBy(xpath = "//span[text()='clear']")
    public WebElement warningMsgCancelBtn;

    @FindBy(xpath = "//span[text()='SUCCESS MESSAGE']")
    public WebElement succesMessage;

    @FindBy(xpath = "//span[text()='Team Successfully Created']")
    public WebElement succesMessageTxt;

    //Modified By:-Vidya Date:-22/10/2019 Reason:- To write scripts for CP-3559
    @FindBy(xpath = "//button[@id='add-user-button']")
    public WebElement userAddBtn;

    @FindBy(xpath = "//h5[text()='USERS LIST']")
    public WebElement userListSection;

    @FindBy(xpath = "//h5[text()='NO RECORD AVAILABLE']")
    public WebElement noRecordAvailableTxt;

    @FindBy(xpath = "//*[text()='User Name']/..")
    public WebElement userNameField;

    //Refactored -Paramita 05/03/2020
    //@FindBy(xpath = "//p[text()='Email']")
    @FindBy(xpath = "//*[text()='Email']/..")
    public WebElement emailField;

    //Refactored -Paramita 05/03/2020
   // @FindBy(xpath = "//p[text()='maersk ID']")
    @FindBy(xpath = "//*[text()='maersk ID']/..")
    public WebElement maerskIdField;

    @FindBy(xpath = "//span[text()='Team Supervisor']/..//input/parent::span")
    public WebElement supervisorCheckBox;

    @FindBy(xpath = "//i[text()='close']")
    public WebElement userCloseIcon;

    @FindBy(xpath = "//i[text()='check']")
    public WebElement userSaveBtn;

    @FindBy(xpath = "//i[text()='clear']")
    public WebElement userCancelBtn;

    @FindBy(xpath = "//h5[text()='TEAM DETAILS']/../following-sibling::div//p")
    public List<WebElement> allFieldsheader;

    @FindBy(xpath = "//h5[text()='TEAM DETAILS']/../following-sibling::div//h6")
    public List<WebElement> allFieldsVlu;

    @FindBy(xpath = "//h5[text()='TEAM DETAILS']/following-sibling::h5")
    public WebElement teamStatus;

    @FindBy(xpath = "//button[@id='edit-team-button']")
    public WebElement editButton;

    // Added By Paramita

    @FindBy(xpath = "//h5[contains(text(),'TEAM DETAILS')]")
    public WebElement teamDetailsheader;

    @FindBy(xpath ="//ul[contains(@id, 'mui')]/li")
    public List<WebElement> userDropDownBoxValue;

    @FindBy(xpath ="//div[@class='css-11unzgr']/div")
    public List<WebElement> emailDropDownBoxValue;

    @FindBy(xpath ="//div[@class='css-11unzgr']/div")
    public List<WebElement> maxIDDropDownBoxValue;

    //@FindBy(xpath ="//*[@class='MuiTableRow-root']")
    @FindBy(xpath ="//div[contains(@class,'mx-teams-card')]")
    public List<WebElement> associatedTeamUserCount;

    @FindBy(xpath ="//div[@class='row']/div[1]")
    public List<WebElement> associatedTeamUser;


    @FindBy(xpath="//i[text()='edit']")
    public WebElement associatedTeamUserEdit;

    @FindBy(xpath="//i[text()='close']")
    public List<WebElement> teamuserdeleteicon;


    @FindBy(xpath = "//p[contains(text(),'@maersk.com')]")
    public WebElement teamUserEmail;

    @FindBy(xpath = "//*[text()='SUPERVISOR']")
    public WebElement supervisor;

    @FindBy(xpath = "//p[contains(text(),'MAX ID -')]")
    public WebElement teamMaxIDNO;


    @FindBy(xpath = "//p[contains(@class,'mx-user-title')]")
    public WebElement teamUserName;

    @FindBy(xpath = "//p[contains(@class,'mx-user-title')]")
    public List<WebElement> userName;

    @FindBy(xpath ="//p[contains(text(),'MAX ID')]/../../../preceding-sibling::div//div")
    public List<WebElement> teamUserSupervisorCardColor;

    @FindBy(xpath = "//div[contains(@class,'mx-user-card-body-left')]")
    public WebElement teamNonSupervisorCardColor;

    @FindBy(xpath ="//p[contains(text(),'BUSINESS UNIT NAME')]/..//h6")
    public WebElement BUValue;

    @FindBy(xpath="//i[text()='edit']")
    public List<WebElement> associatedUserEditIcon;

    @FindBy(xpath ="//h5[contains(text(),'USERS LIST')]/../..//div/img")
    public List<WebElement> associatedTeamImg;

    @FindBy(xpath ="//p[text()='TEAM NAME']/following-sibling::h6")
    public WebElement TeamNameValue;

    @FindBy(xpath="//h5[contains(text(),'USERS LIST')]/../../div[2]/div[@class='row']/div")
    public List<WebElement> listOfTeamUsers;

    //Umid Added elements

    @FindBy(xpath = "//td[text()='FUTURE']")
    public WebElement futureStatus;

    @FindBy(xpath = "//td[text()='FUTURE']/parent::tr/td[1]/a")
    public WebElement futureTeamName;

    @FindBy(xpath = "//td[text()='ACTIVE']")
    public WebElement activeStatus;

    @FindBy(xpath = "//td[text()='ACTIVE']/parent::tr/td[1]/a")
    public WebElement activeTeamName;

    public WebElement getRandomActiveTeamName(int index)
    {
        WebElement el = Driver.getDriver().findElement(By.xpath("(//td[text()='ACTIVE']/parent::tr)["+index+"]/td[1]/a"));
        return el;
    }
    public WebElement getRandomInactiveTeamName(int index)
    {
        WebElement el = Driver.getDriver().findElement(By.xpath("(//td[text()='INACTIVE']/parent::tr)["+index+"]/td[1]/a"));
        return el;
    }

    @FindBy(xpath = "//td[text()='INACTIVE']")
    public WebElement inactiveStatus;

    @FindBy(xpath = "//td[text()='INACTIVE']/parent::tr/td[1]/a")
    public WebElement inactiveTeamName;

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addBTN;


    public String getSupervisorName(){
        String supervisorName = "";
        for(WebElement user:listOfTeamUsers){
            user.findElement(By.xpath(".//i[text()='edit']")).click();
            if(user.findElement(By.xpath(".//input")).getAttribute("value").equalsIgnoreCase("true")){
                supervisorName = user.findElement(By.xpath(".//p[2]")).getText();
                user.findElement(By.xpath("//i[text()='check']/parent::button")).click();
                break;
            }
        }
        return supervisorName;
    }

    public void clickEditButtonForGivenUser(int indexOfUser){

        WebElement user = listOfTeamUsers.get(indexOfUser);
        user.findElement(By.xpath(".//i[text()='edit']")).click();
    }


}
