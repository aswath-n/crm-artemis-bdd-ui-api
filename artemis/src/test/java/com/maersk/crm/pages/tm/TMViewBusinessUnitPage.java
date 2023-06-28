package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMViewBusinessUnitPage extends TMUtilities {

    

    public TMViewBusinessUnitPage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    //refactorBy: Vidya  Date:03-02-2002
    //refactoredBy: Shruti Date:04-13-2020
    @FindBy(xpath = "//h5[contains(text(), '- BUSINESS UNIT DETAILS')]")
    public WebElement BUDetailsHdr;

    @FindBy(xpath = "//i[text()='edit']")
    public WebElement editButton;

    @FindBy(xpath = "//h5[text()='ACTIVE']")
    public WebElement activeStatus;

    @FindBy(xpath = "//h5[text()='FUTURE']")
    public WebElement futureStatus;

    @FindBy(xpath = "//h5[text()='INACTIVE']")
    public WebElement inactiveStatus;

    @FindBy(xpath = "//h5[text()='BUSINESS UNIT DETAILS']/../..//h6")
    public List<WebElement> allFieldsVlu;

    @FindBy(xpath = "//h5[text()='BUSINESS UNIT DETAILS']/../..//p")
    public List<WebElement> allFieldsheader;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATE TASK TYPE')]")
    public WebElement associateTaskTypeHdr;

    @FindBy(xpath = "//h5[contains(text(),'ASSOCIATE TASK TYPE')]/..//following-sibling::div//div")
    public WebElement associateTaskTypeVlu;

    @FindBy(xpath = "//h6[contains(text(),'PERMISSION GROUP')]/..//span//span")
    public List<WebElement> permissionVluOfTaskType;

    @FindBy(xpath = "//h6[contains(text(),'PERMISSION GROUP')]")
    public List<WebElement> permissionHeaderOfTaskType;


    //By Paramita
    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div")
    public List<WebElement> assocaitedTeamSize;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5")
    public List<WebElement> associatedTeamTitle;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p")
    public List<WebElement> associatedTeamStatus;

    @FindBy(xpath = "//p[text()='ACTIVE']")
    public WebElement activeTeamStatus;

    @FindBy(xpath = "//p[text()='FUTURE']")
    public WebElement futureTeamStatus;

    @FindBy(xpath = "//p[text()='INACTIVE']")
    public WebElement inactiveTeamStatus;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//div//div")
    public WebElement TeamStatuscolor;


    @FindBy(xpath = "//p[text()='START DATE ']")
    public WebElement statusStartDate;

    @FindBy(xpath = "//p[text()='START DATE ']/..//h6")
    public WebElement statusStartDateVal;

    @FindBy(xpath = "//p[text() ='END DATE']")
    public WebElement statusEndDate;

    @FindBy(xpath = "//p[text() ='END DATE']/..//h6")
    public WebElement statusEndDateVal;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p/../..//ul")
    public WebElement associatedTeamUserIcon;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p/../../..//div[2]")
    public List<WebElement> associatedTeamUserIconCount;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p/../..//ul/..//button[position()=1]")
    public List<WebElement> ExtraTeamUserIconCount;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p/../..//ul/..//button")
    public WebElement ExtraTeamUserIcon;

    @FindBy(xpath = "//h5[text()='ASSOCIATED TEAM(S)']/..//div//div//h5/..//p/../..//ul/..//button/..//div//div//ul")
    public List<WebElement> ExtraTeamUserName;











}
