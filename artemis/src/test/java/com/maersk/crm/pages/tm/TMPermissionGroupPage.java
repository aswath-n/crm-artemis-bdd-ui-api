package com.maersk.crm.pages.tm;

import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.TMUtilities;
import org.apache.commons.net.util.SSLSocketUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.sound.midi.Soundbank;
import java.util.List;

public class TMPermissionGroupPage extends TMUtilities {

    

    public TMPermissionGroupPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //refactorBy:vidya Date:03-02-2020
    @FindBy(xpath = "//h5[contains(text(),'Permission Group')]")
    public WebElement permissionGroupHeader;

    @FindBy(xpath = "//span[contains(text(),' add')]")
    public WebElement addPermissionHeader;


    @FindBy(xpath = "//h5[contains(text(), 'PERMISSION GROUP DETAILS')]")
    public WebElement permissionGroupDetails;


    @FindBy(xpath = "//th[contains(text(),'GROUP PERMISSION NAME')]")
    public WebElement groupPermissionNameTabHdr;

    @FindBy(xpath = "//th[contains(text(),'GROUP PERMISSION DESCRIPTION')]")
    public WebElement groupPermissionDescTabHdr;

    @FindBy(xpath = "//th[contains(text(),'CREATION DATE')]")
    public WebElement creationDateTabHdr;

    @FindBy(xpath = "//th[contains(text(),'LAST MODIFIED DATE')]")
    public WebElement lastModifiedDateTabHdr;

    @FindBy(xpath = "//th[contains(text(),'ROLE NAMES')]")
    public WebElement rolesNameTabHdr;

    @FindBy(xpath = "(//th[contains(text(),'GROUP PERMISSION NAME')]/parent::tr/parent::thead/parent::table/tbody/tr[1]/td[1])[1]")
    public WebElement firstRecodInTab;

    @FindBy(xpath = "//div[contains(text(),'show 5')]")
    public WebElement showFiveDropdown;

    @FindBy(xpath = "//h5[contains(text(),'No Permissions Found')]")
    public WebElement noPermissionsFound;

    @FindBy(xpath = "//input[@name='permissionGroupName']")
    public WebElement inputPermissionGrpName;

    @FindBy(xpath = "//input[@name='permissionGroupDesc']")
    public WebElement inputPermissionGrpDesc;

    @FindBy(xpath = "//*[@name='selectRole']//..")
    public WebElement selectRoleDropdown;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement saveOnPrmGrpDet;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    public WebElement cancelOnPrmGrpDet;

    @FindBy(xpath = "//span[contains(text(),'Permission has been successfully created')]")
    public WebElement permissionSucessMsg;

    @FindBy(xpath = "(//*[contains(text(),'chevron_right')])[1]")
    public WebElement expandFirst;

    @FindBy(xpath = "(//*[contains(text(),'chevron_right')])[2]")
    public WebElement expandSecond;

    @FindBy(xpath = "(//tbody[1]/tr[1]/td[1])[3]")
    public WebElement selectFirstRcd;

    @FindBy(xpath = "//span[contains(text(),'View')]")
    public WebElement viewRadiobtn;

    @FindBy(xpath = "//span[contains(text(),'Edit')]")
    public WebElement editRadiobtn;

    @FindBy(xpath = "//span[contains(text(),'NoPermission')]")
    public WebElement noPermRadiobtn;

    @FindBy(xpath = "//span[contains(text(),'Apply For All')]")
    public WebElement applyAllCheckBox;

    @FindBy(xpath = "(//span[contains(text(),' Save')])[2]")
    public WebElement saveAccessPermission;

    @FindBy(xpath = "//p[contains(text(),'The Permission group name is required and cannot be left blank.')]")
    public WebElement blankPermissionNameError;

    @FindBy(xpath = "//p[contains(text(),'The Permission group description is required and cannot be left blank.')]")
    public WebElement blankDescriptionError;

    @FindBy(xpath = "//p[contains(text(),'The Select role is required and cannot be left blank.')]")
    public WebElement blankRoleError;

    @FindBy(xpath = "//p[contains(text(),'All changes will be lost; Do you want to continue?')]")
//    @FindBy(xpath = "//*[class='modal-content']")
    public WebElement cancelPermissionWarning;

    @FindBy(xpath = "//*[text()='Yes']/parent::button")
    public WebElement cancelPermissionWarningYesBtn;

    @FindBy(xpath = "//*[text()='No']/parent::button")
    public WebElement cancelPermissionWarningNoBtn;

    @FindBy(xpath = "//h5[contains(text(),' ACCESS PERMISSION ')]")
    public WebElement accessPermissionHeader;

    @FindBy(xpath = "(//table[contains(@class,'mdl-data-table mdl-js-data-table mx-table table')])[1]")
    public WebElement pageLevel;

    @FindBy(xpath = "(//table[contains(@class,'mdl-data-table mdl-js-data-table mx-table table')])[2]")
    public WebElement sectionLevel;

    @FindAll({
            @FindBy(xpath = "(//table[contains(@class,'mx-table-treeview table')])[3]")
    })
    public List<WebElement> fieldLevels;

    @FindBy(xpath = "//span[contains(text(),' Cancel ')]")
    public WebElement cancelAccessPermission;


    @FindBy(xpath = "//div/*[@role='radiogroup']//*[@id='1']")
    public WebElement viewRadioButtonFlag;

    @FindBy(xpath = "//div/*[@role='radiogroup']//*[@id='2']")
    public WebElement editRadioButtonFlag;

    @FindBy(xpath = "//div/*[@role='radiogroup']//*[@id='3']")
    public WebElement noPermissionRadioButtonFlag;

    @FindBy(xpath = "//div/*[@for='checkbox-2']//input")
    public WebElement applyCheckBoxFlag;

    @FindBy(xpath = "//*[contains(text(), 'chevron_right')]")
    public WebElement expandPageLevel;

    @FindBy(xpath = "(//*[contains(text(), 'chevron_right')])[3]")
    public WebElement expandSectionLevel;

    @FindBy(xpath = "//*[@name='itemsPerPage']/..")
    public WebElement itemsPerPageDropdown;

    @FindBy(xpath = "//span[text()='arrow_back']/parent::a")
    public WebElement lnkArrowBack;

    @FindBy(xpath = "//span[text()='arrow_forward']/parent::a")
    public WebElement lnkArrowForward;

    @FindBy(xpath = "//ul[@class='pagination']/li/a")
    public List<WebElement> lnkPageNations;

    @FindBy(xpath = "//tbody/tr")
    public List<WebElement> permissionGroupNameRows;

    /**
     * @Author: Muhabbat
     * this method selects a role on permission group page by index
     */
    public void selectRoleByIndex(int index) {
        selectRoleDropdown.click();
        WebElement el = Driver.getDriver().findElement(By.xpath("(//li[@tabindex])[" + ++index + "]"));
        el.click();
        waitFor(2);
    }

    @FindAll({
            @FindBy(xpath = "//tbody/tr[*]/td[1]")
    })
    public List<WebElement> permissionNames;

    @FindAll({
            @FindBy(xpath = "//tbody/tr[*]/td[2]")
    })
    public List<WebElement> permissionDescriptions;

    @FindAll({
            @FindBy(xpath = "//tbody/tr[*]/td[5]")
    })
    public List<WebElement> roleNames;

    /**
     * @Author: Muhabbat
     * this method verifies if created/edited permission group is displayed on List of Permissions Page
     */
    public boolean groupPermissionIsCreated(String expectedGroupName, String expectedGroupDescription, String expectedRole) {
        boolean created = false;
        try {
            for (int k = 0; k < permissionNames.size(); k++) {
                if (permissionNames.get(k).getText().equalsIgnoreCase(expectedGroupName)) {
                    created = (permissionDescriptions.get(k).getText().startsWith(expectedGroupDescription) |
                            roleNames.get(k).getText().equalsIgnoreCase(expectedRole));
                }
            }
        } catch (Exception e) {
            System.out.println("There is no permissions Created before. Table is not displayed");
        }
        return created;

    }

    /**
     * @Author: Muhabbat
     * this method expands previously saved permission group by Name, Group Description and Role
     */
    public void expendGroupPermissionByAllFields(String expectedGroupName, String expectedGroupDescription, String expectedRole) {
        selectDropDown(itemsPerPageDropdown, "show 20");

        for (int k = 0; k < permissionNames.size(); k++) {
            if (permissionNames.get(k).getText().equalsIgnoreCase(expectedGroupName) |
                    (permissionDescriptions.get(k).getText().equalsIgnoreCase(expectedGroupDescription)
                            | roleNames.get(k).getText().equalsIgnoreCase(expectedRole))) {
                permissionNames.get(k).click();
                waitFor(2);
            }
        }
    }

    /**
     * @Author: Muhabbat
     * this method expands a random permission group from list of previously saved ones
     */
    public void expendRandomGroupPermission() {
        selectDropDown(itemsPerPageDropdown, "show 20");
        int index = randomNumberBetweenTwoNumbers(0, permissionNames.size() - 1);
        permissionNames.get(index).click();
        waitFor(2);
    }

}

