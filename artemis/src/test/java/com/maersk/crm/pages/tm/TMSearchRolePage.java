package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMSearchRolePage {
    

    public TMSearchRolePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'add')]")
    public WebElement addRoleButton;

    @FindBy(xpath="//label[text()='Role Name']/following-sibling::div/input")
    public WebElement roleName;

    @FindBy(xpath="//label[text()='Role Description']/following-sibling::div/input")
    public WebElement roleDescription;

    @FindBy(xpath="//input[@id='creationDateType']/../div")
    public WebElement creationDate;

    @FindBy(xpath="//input[@id='modifiedDateType']/../div")
    public WebElement modifiedDate;

    @FindBy(xpath="//input[@id='roleStatusType']/../div")
    public WebElement rolestatus;

    @FindBy(xpath="//a[@role='button']//i[text()='autorenew']")
    public WebElement refresh;

    @FindBy(xpath = "//button//span[text()=' Clear']")
    public WebElement clearButton;

    @FindBy(xpath="//span[contains(text(),'search')]")
    public WebElement search;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[1]")
    })
    public List<WebElement> roleNames;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[2]")
    })
    public List<WebElement> roleDescriptions;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[3]")
    })
    public List<WebElement> startDates;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[4]")
    })
    public List<WebElement> endDates;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[5]")
    })
    public List<WebElement> creationDates;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'table-responsive')]//tr[*]//td[6]")
    })
    public List<WebElement> lastModifiedDates;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'jss494 jss495')]//li[*]")
    })
    public List<WebElement> creationDropDownValues;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'jss512 jss513')]//li[*]")
    })
    public List<WebElement> modifiedDateDropDownValues;


    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'jss566 jss567')]//li[*]")
    })
    public List<WebElement>  rolestatusDropDownValues;

    @FindBy(xpath = "//*[text()='ROLE NAME']")
    public WebElement resultTableHeaderRoleName;

    @FindBy(xpath = "//*[text()='ROLE DESCRIPTION']")
    public WebElement resultTableHeaderRoleDesc;

    @FindBy(xpath = "//*[text()='START DATE']")
    public WebElement resultTableHeaderStartDate;

    @FindBy(xpath = "//*[text()='END DATE']")
    public WebElement resultTableHeaderEndDate;

    @FindBy(xpath = "//*[text()='CREATION DATE']")
    public WebElement resultTableHeaderCreationDate;

    @FindBy(xpath = "//*[text()='LAST MODIFIED DATE']")
    public WebElement resultTableHeaderModifyDate;

    @FindBy(xpath = "//h5[text()='There are no Project Roles matching your search criteria. Please review your search criteria and try again.']")
    public WebElement noRoleExistError;

    @FindBy(xpath = "//h5[text()='No Records Available']")
    public WebElement noRoleExist;

    @FindBy(xpath = "//tr/td[1]")
    public List<WebElement> roleNamesRolelist;

    @FindBy(xpath = "//table[1]/thead[1]/tr[2]/td[2]/span[1]")
    public WebElement viewPermissionRadio;

    @FindBy(xpath = "//table[1]/thead[1]/tr[2]/td[3]/span[1]")
    public WebElement editPermissonRadio;

    @FindBy(xpath = "(//thead//td[4]//span[1]//span[1]//div[1]//*[local-name()='svg'])[1]")
    public WebElement noPermissionRadio;

    @FindBy(xpath = "//table[1]/thead[1]/tr[2]/td[5]/label[1]/span[1]/span[1]/input[1]")
    public WebElement applyAllCheckBox;

    @FindBy(xpath = "//tr//td[1]//button[1]")
    public List<WebElement> listOfCarets;

    @FindBy(xpath = "//div[@id='mui-component-select-pages']")
    public WebElement filterByPagedrpdwn;

    @FindBy(xpath = "//tr//td[1]/span[1]")
    public List<WebElement> uploadedProNames;

    @FindBy(xpath = "//button[contains(text(),'Clear Filter')]")
    public WebElement clearFilterbutton;

    @FindBy(xpath = "//tr/td[5]/label[1]/span[1]/span[1]/input[1]")
    public List<WebElement> listApplyAllCheckBox;

    @FindBy(xpath = "//tr/td[6]/label[1]/span[1]/span[1]/input[1]")
    public List<WebElement> listApplyAllRolesCheckBox;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[1]/button[1]")
    public WebElement expandFistCaret;

    @FindBy(xpath = "//tr/td[2]/span[1]/span[1]/input[1]")
    public List<WebElement> listOfViewPermissions;

    @FindBy(xpath = "//*[normalize-space()='PERMISSION DETAILS']/ancestor::div[contains(@Class, 'tabs')]//span[contains(text(),'Save')]")
    public WebElement permissionSaveButton;

    @FindBy(xpath = "(//button//span[contains(text(),'Save')]//span)[2]")
    public WebElement permissionSaveBtnNewSetting;

    @FindBy(xpath = "//span[contains(text(),'Your request is being processed in the background.')]")
    public WebElement successMesAfterSave;

    @FindBy(xpath = "//h5[contains(text(),'ROLE DETAILS')]")
    public WebElement roleDetailsPage;

    @FindBy(xpath = "//tr/td[4]/span[1]/span[1]/input[1]")
    public List<WebElement> listOfNoPermission;

    @FindBy(xpath = "//tr/td[3]/span[1]/span[1]/input[1]")
    public List<WebElement> listOfEditPermission;

    @FindBy(xpath = "//tr//td[1]//span[1]")
    public WebElement firstResultName;

    @FindBy (xpath = "//a[@aria-controls='addMissingInfo']")
    public WebElement refreshBtn;

    @FindBy (xpath = "//div[@id='erroMessageInfo']")
    public WebElement UIConfigRefreshBar;

    @FindBy(xpath = " //span[text()='UI Config refresh is Successful']")
    public WebElement successMsgAfterConfigChanges;

    public WebElement roleNamewithIndex(String rolenamevalue)
    {
        return Driver.getDriver().findElement(By.xpath("//tr/td[text()='"+rolenamevalue+"']"));
    }

    public WebElement getpermissionforGivenPage(String page,String permission)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//td//span[text()='"+page+"']//parent::td//following-sibling::td//span//span//input[@value='"+permission+"']"));
    }

    @FindBy(xpath = "(//button//i[text()='chevron_right'])[1]")
    public WebElement caretSign;

    public WebElement getTemplateFomPage(String permissionType,String template, String Page)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//span[text()='"+Page+"']//parent::td//parent::tr//following-sibling::tr//td//span[text()='"+template+"']//parent::td//following-sibling::td//input[@value='"+permissionType+"']"));
    }
    public WebElement getCaretSignForGrid(String Grid)
    {
        return Driver.getDriver().findElement(By.xpath("//span[text()='"+Grid+"']//preceding-sibling::button"));
    }
    public WebElement getfieldFromTemplateFomPage(String permissionType,String field,String template, String Page)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//span[text()='"+Page+"']//parent::td//parent::tr//following-sibling::tr//td//span[text()='"+template+"']//parent::td//parent::tr//following-sibling::tr//span[text()='"+field+"']//parent::td//following-sibling::td//input[@value='"+permissionType+"']"));
    }
    public WebElement getgridFromTemplateFomPage(String permissionType,String grid,String template, String Page)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//span[text()='"+Page+"']//parent::td//parent::tr//following-sibling::tr//td//span[text()='"+template+"']//parent::td//parent::tr//following-sibling::tr//span[text()='"+grid+"']//parent::td//following-sibling::td//input[@value='"+permissionType+"']"));
    }
    public WebElement getgridColumnFromTemplateFomPage(String permissionType,String column,String grid,String template, String Page)
    {
        return Driver.getDriver().findElement(By.xpath("//tr//span[text()='"+Page+"']//parent::td//parent::tr//following-sibling::tr//td//span[text()='"+template+"']//parent::td//parent::tr//following-sibling::tr//span[text()='"+column+"']//parent::td//following-sibling::td//input[@value='"+permissionType+"']"));
    }
    public WebElement getCaretSignForTemplate(String template)
    {
        return Driver.getDriver().findElement(By.xpath("//span[text()='"+template+"']//preceding-sibling::button"));
    }

}
