package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TMPermissionPage {

    

    public TMPermissionPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    //aswath

    @FindBy(xpath="//th[contains(text(),'GROUP PERMISSION NAME')]")
    public WebElement groupPermissionNameTabHdr;

    @FindBy(xpath="//th[contains(text(),'GROUP PERMISSION DESCRIPTION')]")
    public WebElement groupPermissionDescTabHdr;

    @FindBy(xpath="//th[contains(text(),'CREATION DATE')]")
    public WebElement creationDateTabHdr;

    @FindBy(xpath="//th[contains(text(),'LAST MODIFIED DATE')]")
    public WebElement lastModifiedDateTabHdr;

    @FindBy(xpath="//th[contains(text(),'ROLE NAMES')]")
    public WebElement rolesNameTabHdr;

    @FindBy(xpath="//div[contains(text(),'show 5')]")
    public WebElement showFiveDropdown;

    @FindBy(xpath="//h5[contains(text(),'No permissions found for the project')]")
    public WebElement noPermissionsFound;

    @FindBy(xpath="//*[@name='selectRole']//..")
    public WebElement selectRoleDropdown;

    @FindBy(xpath="//span[contains(text(),'Save')]")
    public WebElement saveOnPrmGrpDet;

    @FindBy(xpath="(//*[contains(text(),'chevron_right')])[1]")
    public WebElement expandFirst;

    @FindBy(xpath="(//*[contains(text(),'chevron_right')])[2]")
    public WebElement expandSecond;

    @FindBy(xpath="//span[contains(text(),'View')]")
    public WebElement viewRadiobtn;

    @FindBy(xpath="//span[contains(text(),'Apply For All')]")
    public WebElement applyAllCheckBox;

    @FindAll({
            @FindBy(xpath = "//th[contains(text(),'GROUP PERMISSION NAME')]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> grpPermissionTab;

    @FindBy(xpath="//span[contains(text(),'keyboard_backspace')]")
    public WebElement backArrow;

    @FindBy(xpath="//div[contains(@class,'float-right rt-page-dropdown')]")
    public WebElement paginationDrpdwn;


    @FindBy(xpath="//span[contains(text(),'arrow_forward')]")
    public WebElement nextPermissionPage;

    @FindBy(xpath = "//input[@value='noPermission']")
    //@FindBy(xpath = "//input[@value='noPermission']/ancestor::label/span")
    public WebElement btnNoPermissionRadio;

    @FindBy(xpath = "//input[@value='read']")
    //@FindBy(xpath = "//input[@value='read']/ancestor::label/span")
    public WebElement btnViewRadio;

    @FindBy(xpath = "//input[@value='write']")
   // @FindBy(xpath = "//input[@value='write']/ancestor::label/span")
    public WebElement btnEditRadio;

    @FindBy(xpath = "//h5[contains(text(), 'ACCESS')]/ancestor::div[@class='row']//button[1]")
    public WebElement btnAccessSave;

    @FindBy(xpath = "//span[text()='Yes']/parent::button")
    public WebElement btnYesConfirmationPopUp;

}
