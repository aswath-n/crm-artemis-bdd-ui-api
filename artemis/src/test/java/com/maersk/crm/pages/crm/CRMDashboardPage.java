package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CRMDashboardPage {
    

    public CRMDashboardPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//ul[@class='mx-sidenavmenu-items']")
    public WebElement sideNavMenu;

    @FindBy(xpath = "//div[@data-route='/mars/crm/active-contact']/following-sibling::*[contains(text(),'Active Contact')]")
    public WebElement activeContactTab;

    @FindBy(xpath = "//li[2]")
    public WebElement case_ConsumerSearchIcon;

    @FindBy(xpath = "//span[text()='Case/Consumer Search']")
    public WebElement case_ConsumerSearchTab;

    @FindBy(xpath = "//span[text()='Case/Consumer Alert']")
    public WebElement case_ConsumerAlertTab;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/ul/li[3]")
    public WebElement contactRecordSearchTabIcon;

    @FindBy(xpath = "//*[text()='Contact Record Search']")
    public WebElement contactRecordSearchTab;

    @FindBy(xpath = "//span[text()='Task/SR Management']/..")
    public WebElement taskManagementTab;

    @FindBy(xpath = "//span[text()='Task/SR Management']/..//span[text()='keyboard_arrow_down']")
    public WebElement expandTaskManagementSideTab;

    @FindBy(xpath = "//span[text()='My Task / Work Queue']")
    public WebElement myTaskWorkQueueSideTab;

    @FindBy(xpath = "//div[contains(@class, 'mdl-layout__header')]/nav//div[@role='button']")
    public WebElement roleDropDown;

    @FindBy(xpath = "//li[@data-value='QA']")
    public WebElement roleQA;

    @FindBy(xpath = "//li[contains(@data-value, 'CSR')]")
    public WebElement roleCSR;

    @FindBy(xpath = "//div[@role='dialog' and contains(@style, 'block')]//button")
    public WebElement popUpCancelButton;

    @FindBy(xpath = "//div[@role='dialog' and contains(@style, 'block')]//a")
    public WebElement popUpContinueButton;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> listOfRoles;

    @FindBy(xpath = "//div[@role='dialog' and contains(@style, 'block')]//p")
    public WebElement popUpMessage;

    @FindBy(xpath = "//button[@id='menu-list-button']")
    public WebElement btnMenuList;

    @FindBy(xpath = "//li[@id='createTask']")
    public WebElement createTaskMenu;

    @FindBy(xpath = "//div[@id='sub-menu-list']//ul/li")
    public WebElement subMenuList;

    @FindBy(xpath = "//div[@role='document']//div//p")
    public WebElement warningPopUpMessage;

    @FindBy(xpath = "//p[contains(text(), 'ID')]")
    public WebElement loggedInUserId;

    @FindBy(xpath = "//div[@class=' mx-header__userinfo']//p")
    public WebElement loggedInUserName;

    public void clickOnRoleDropDown() {
        roleDropDown.click();
    }

    public void selectRole(String roleName) {
        Driver.getDriver().findElement(By.xpath("//li[contains(@data-value, '" + roleName + "')]")).click();
    }

    public void clickPopUpCancelButton() {
        popUpCancelButton.click();
    }

    public void clickPopUpContinueButton() {
        popUpContinueButton.click();
    }

    public ArrayList<String> getListOfActiveRoles() {
        ArrayList<String> roles = new ArrayList<String>();
        for (WebElement role : listOfRoles) {
            roles.add(role.getText());
        }
        return roles;
    }

    public String getSelectedRole() {
        return roleDropDown.getText();
    }

    public String getPopUpMessage() {
        return popUpMessage.getText();
    }

    @FindBy(xpath = "//span[text()='Task Management']/parent::a")
    public WebElement lnkTaskManagement;

    @FindBy(xpath = "//span[text()='Task Management']/button")
    public WebElement lnkTaskManagementExpand;

    @FindBy(xpath = "//span[text()='My Task / Work Queue']/parent::a")
    public WebElement lnkMyTaskWorkQueue;

    @FindBy(xpath = "//*[@name='createTask']")
    public WebElement createTask;

    @FindAll({
            @FindBy(xpath = "//li[@role='menuitem']")
    })
    public List<WebElement> taskTypes;

    @FindBy(xpath = "//*[@name = 'taskPriority']/..")
    public WebElement priorityDropDown;

    @FindBy(xpath = "//*[contains(text(), 'CREATE TASK')]")
    public WebElement createTaskSign;

    @FindBy(xpath = " //span[contains(text(),'WARNING MESSAGE')]/../../../../../../../following-sibling::div//span[text()='Cancel']")
    public WebElement warningPopupCancelBtn;

    @FindBy(xpath = "//*[text()='Continue']/parent::button")
    public WebElement warningPopupContinueBtn;

    @FindBy(xpath = "//span[text()='Task/SR Search']")
    public WebElement taskSearchSideTab;

    @FindBy(xpath = "//span[text()='Saved Task Search']")
    public WebElement savedTaskSearchSideTab;

    @FindBy(xpath = "//div[@class='mx-menu-icon-wrapper'][1]")
    public WebElement caseConsumerSideBarIcon;

    @FindBy(xpath = "(//div[@class='mx-menu-icon-wrapper'])[3]")
    public WebElement taskSidebarIcon;

    @FindBy(xpath = "(//*[@className = 'mdl-button mdl-js-button mdl-button--icon mx-btn float-right  ml-4 mt-2 mx-rotate']")
    public WebElement taskSearchDrop;

    @FindBy(xpath = "//*[text()='Task Search']")
    public WebElement taskSearchTab;

    @FindBy(xpath = "//span[contains(text(),'CREATE CORRESPONDENCE')]")
    public WebElement createCorrespondence;

    @FindBy(id = "CreateAlert")
    public WebElement createAlertTab;

    @FindBy(xpath = "//span[contains(@data-route, '/mars/crm/active-contact')]/..")
    public WebElement activeContactLink;

    @FindBy(xpath = "//span[contains(text(),'Correspondence')]")
    public WebElement correspondenceMainNavigationButton;

    @FindBy(xpath = "//span[contains(text(),'OUTBOUND SEARCH')]/..")
    public WebElement outboundCorrespondenceMainNavigationButton;

    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND CORRESPONDENCE SEARCH')]")
    public WebElement outboundCorrespondenceGlobalSearchHeader;

    @FindBy(xpath = "(//*[@class='nav-item'])[3]")
    public WebElement taskServiceRequestTab;

    @FindBy(xpath = "//span[text()='Correspondence']/..//span[text()='keyboard_arrow_down']")
    public WebElement expandCorrespondenceSideTab;

    @FindBy(xpath = "//div[@class='mx-menu-icon-wrapper']")
    public WebElement contactRecordSearchSidebarIcon;

    @FindBy(xpath = "//span[text()='Manage Queue Filter']")
    public WebElement manageQueueFilterTab;

    @FindBy(xpath = "//li[5]//a[1]//span[2]//button[1]")
    public WebElement expandCorres;

    @FindBy(xpath = "//li[@name='taskServiceRequest']/div/span")
    public WebElement createSRMenu;

    @FindBy(id = "agent-slider")
    public WebElement telephonyWidget;

    @FindBy(xpath = "//*[contains(text(),'INTERACTION MANAGEMENT')]")
    public WebElement expandCollapseCTI;

    @FindBy(xpath = "//span[contains(text(),'arrow_drop_down')]")
    public WebElement logOutDrop;

    @FindBy(xpath = "//body/div[@id='logout-menu']/div[3]/ul[1]")
    public WebElement logOutbutton;

    @FindAll({
            @FindBy(xpath = "//td[4]/button/span[1]/i")
    })
    public List<WebElement> downloadSucAlertBtn;

    @FindAll({
            @FindBy(xpath = "//td[5]/button/span/i")
    })
    public List<WebElement> downloadFailAlertBtn;

    @FindBy(xpath = "//div[@title='ConnectionPoint Logo']/../..//div[contains(@class,'datetime')]")
    public WebElement projectDateAtHeader;

    @FindBy(xpath = "//ul[@id='route.config4']//li")
    public List<WebElement> taskMangDDVlu;

    @FindBy(xpath = "//span[text()='Task/SR Management']")
    public WebElement taskManagementLeftMenu;

    @FindBy(xpath = "//span[text()='COMMUNITY OUTREACH MGMT']/..")
    public WebElement communityOutreachMGMT;

    @FindBy(xpath = "//span[text()='COMMUNITY OUTREACH MGMT']/..//span[text()='keyboard_arrow_down']")
    public WebElement expandcommunityOutreachMGMTSideTab;

    @FindBy(xpath="//button[contains(@class,'mx-rotate--up')]")
    public WebElement sideMenuCorrespondenceArrowUpButton;

    @FindBy(xpath = "//span[text()='assignment']")
    public WebElement taskMGMTab;
}