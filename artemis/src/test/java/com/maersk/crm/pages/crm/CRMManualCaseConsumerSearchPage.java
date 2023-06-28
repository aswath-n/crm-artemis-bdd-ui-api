package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMManualCaseConsumerSearchPage {


    

    public CRMManualCaseConsumerSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[text()='ADVANCED SEARCH']/parent::button")
    public WebElement advancedSearchButton;

    @FindBy(id = "consumerFirstName")
    public WebElement firstName;

    @FindBy(id = "consumerMiddleName")
    public WebElement middleName;

    @FindBy(id = "consumerLastName")
    public WebElement lastName;

    @FindBy(id = "consumerSSN")
    public WebElement consumerSSN;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement DOB;

    @FindBy(id = "consumerEmail")
    public WebElement email;

    @FindBy(id = "consumerAddress1")
    public WebElement addressLine1;

    @FindBy(id = "consumerAddress2")
    public WebElement addressLine2;

    @FindBy(xpath = "//button[contains(@class, 'mx-btn-primary')]")
    public WebElement searchButton;

    @FindBy(xpath = "//button[contains(@class, 'mx-btn mx-btn-cancel')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//h6[contains(text(),'SEARCH RESULT')]/..//table/tbody/tr[not(contains(@class, 'collapse'))]")
    public List<WebElement> searchResultRows;


    @FindAll({
            @FindBy(xpath = "//*[contains(@class,'mdl-js-data-table mt-4 mx-table table mdl-color--grey-50 mb-0')]/tbody/tr/td[3]")
    })
    public List<WebElement> consumerIDs;


    //    @FindAll({
//            @FindBy(xpath = "//*[contains(@class,'mdl-js-data-table mt-4 mx-table table mdl-color--grey-50 mb-0')]/tbody/tr/td[2]")
//    })
    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[2]")
    public List<WebElement> caseIDs;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/tbody/tr/td[3]")
    public List<WebElement> dcCaseIDs;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[*]//table/tbody/tr/td[2]")
    public WebElement dcStateID;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[4]")
    public List<WebElement> dcConsumerName;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[8]//table/tbody/tr/td[9]")
    public List<WebElement> dcSearchResultPhones;

    @FindBy(xpath = "//*[contains(text(),'CRM CASE ID')]")
    public WebElement crmCaseIdField;

    @FindBy(xpath = "//*[contains(text(),'CRM CONSUMER ID')]")
    public WebElement crmConsumerIdField;

    @FindBy(xpath = "//span[contains(text(),'FIRST NAME')]")
    public WebElement firstNameField;

    @FindBy(xpath = "//span[contains(text(),'MI')]")
    public WebElement middleNameField;

    @FindBy(xpath = "//span[contains(text(),'LAST NAME')]")
    public WebElement lastNameField;

    @FindBy(xpath = "//span[contains(text(),'DATE OF BIRTH')]")
    public WebElement DOBField;

    @FindBy(xpath = "//span[contains(text(),'SSN')]")
    public WebElement SSNField;

    @FindBy(xpath = "(//*[contains(text(),'chevron_right')])[1]")
    public WebElement expandButton;

    @FindBy(xpath = "(//*[contains(text(),'HOME ADDRESS')])[1]")
    public WebElement homeAddressField;

    @FindBy(xpath = "((//td[contains(@class,'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-t')])[1]//p/following-sibling::h6)[1]")
    public WebElement homeAddressInformationFirstLine;

    @FindBy(xpath = "((//td[contains(@class,'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-t')])[1]//p/following-sibling::h6)[2]")
    public WebElement homeAddressInformationSecondLine;

    @FindBy(xpath = "(//*[contains(text(),'PRIMARY INDIVIDUAL')])[1]")
    public WebElement primaryIndividualField;

    @FindBy(xpath = "(//strong[text()='PRIMARY INDIVIDUAL']/parent::p/following-sibling::h6)[1]")
    public List<WebElement> primaryIndividualMembersTable;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBER')])[1]")
    public WebElement caseMembersField;

    @FindBy(xpath = "(//strong[text()='CASE MEMBER']/parent::p/following-sibling::h6)[1]")
    public List<WebElement> caseMembersTable;

    @FindBy(xpath = "(//*[contains(text(),'AUTHORIZED REPRESENTATIVE')])[1]")
    public WebElement authorizedRepresentativeField;

    @FindBy(xpath = "(//strong[text()='AUTHORIZED REPRESENTATIVE']/parent::p/following-sibling::h6)[1]")
    public List<WebElement> authorizedRepresentativetable;

    @FindBy(xpath = "//p[@class='mx-overflow-text mb-0']")
    public WebElement caseIdField;

    @FindBy(name = "caseId")
    public WebElement internalCaseId;

    @FindBy(name = "consumerId")
    public WebElement internalConsumerId;

    @FindBy(xpath = "(//*[@id='mui-component-select-resultsGrid'] | //*[@id='mui-component-select-resulstGrid'])[1] ")
    public WebElement itemsPerPageDropdown;

    @FindBy(xpath = "//*[contains(@class , 'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric  mx-link font-weight-bold')]/p")
    public List<WebElement> caseIdTable;

    @FindBy(xpath = "(//*[contains(@class ,'MuiTable')][1]//tr[*]//td[2])[1]")
    public WebElement CRMCaseIdTable;

    @FindBy(xpath = "//*[contains(@class ,'MuiTable')][1]//tr[*]//td[3]/p")
    public List<WebElement> consumerIdTable;

    @FindBy(xpath = "//*[contains(@class ,'MuiTable')][1]//tr[*]//td[3]/p")
    public List<WebElement> consumerIdTableWithoutCaseId;

    @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[4]")
    public List<WebElement> firstNameTable;

    @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[5]")
    public List<WebElement> middleNameTable;

    @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[6]")
    public List<WebElement> lastNameTable;

    @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[7]")
    public List<WebElement> dateOfBirthTable;

    @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[8]")
    public List<WebElement> SSNTable;

    @FindBy(xpath = "(//span[contains(text(),'visibility')])[2]")
    public WebElement unMaskDOB;

    @FindBy(xpath = "(//*[contains(text(),'visibility')])[4]")
    public WebElement unmaskSSN;

    @FindBy(xpath = "//*[contains(text(),'folder_shared')]")
    public WebElement caseIcon;

    @FindBy(xpath = "//*[contains(@class, 'mx-layout-top-drawer-fields float-left')]/*[last()]")
    public WebElement caseIdNextToCaseIcon;

    @FindBy(xpath = "//*[contains(@class, 'mx-layout-top-drawer-fields float-left')]/*[1]")
    public WebElement nameNextToCaseIcon;

    //    @FindAll({
//            @FindBy(xpath = "//*[contains(@class,'mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50')]/tbody/tr/td[4]")
//    })
    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[4]")
    public List<WebElement> consumerFNs;

    //    @FindAll({
//            @FindBy(xpath = "//*[contains(@class,'mdl-data-table mdl-js-data-table mx-table table mt-4 mb-0 mdl-color--grey-50')]/tbody/tr/td[6]")
//    })
    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[6]")
    public List<WebElement> consumerLNs;

    @FindBy(xpath = "//span[contains(text(),'ADVANCED SEARCH')]")
    public WebElement advanceSearch;

    @FindBy(xpath = "//span[@class='material-icons MuiIcon-root mr-2']")
    public WebElement advanceSearchManualSrhScreen;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[2]/p)[1]")
    public WebElement caseIdFirstRecordValue;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td/p)[2]")
    public WebElement consumerIdFirstRecordValue;

    @FindBy(xpath = "//*[contains(@id, 'caseType')]/..")
    public WebElement searchCaseOptions;

    @FindBy(xpath = "//*[contains(@id, 'consumerType')]/..")
    public WebElement searchConsumerOptions;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[4])[1]")
    public WebElement searchResultFirstNameFound;

    @FindBy(xpath = "//h5[@class='mx-section-header float-left']//following-sibling::h5")
    public WebElement noResultsFound;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[6])[1]")
    public WebElement searchResultLastNameFound;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[7])[1]")
    public WebElement searchResultDOBFound;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[7])")
    public WebElement searchResultDOBVisible;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td[8])[1]")
    public WebElement searchResultSSNFound;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mx-btn-border mdl-js-button mdl-button--primary mx-btn mx-btn-cancel']")
    public WebElement consumerCancelBtn;

    @FindBy(xpath = "//ul/li/a[contains(@class,'nav-link')]")
    public List<WebElement> caseProfileTabHeaders;

    @FindBy(id = "consumerCounty")
    public WebElement caseConsumerSearchCounty;

    @FindBy(id = "consumerCity")
    public WebElement caseConsumerSearchCity;

    @FindBy(xpath = "//*[@name='consumerState']//..")
    public WebElement caseConsumerState;

    @FindBy(id = "consumerPhoneNumber")
    public WebElement caseconsumerPhoneNumber;

    @FindBy(id = "zipCode")
    public WebElement caseConsumerSearchZipCode;

    @FindBy(id = "consumerId")
    public WebElement caseConsumerConsumerId;

    @FindBy(id = "mui-component-select-contactRecordType")
    public WebElement contactRecordType;

    @FindBy(xpath = "//div[@id='menu-consumerType']//ul/li")
    public List<WebElement> searchConsumerTypeValue;

    @FindBy(xpath = "//span[text()='Please enter the search parameters']")
    public WebElement searchErrorWithNoInputData;

    public WebElement minCharserrorMessage(String field) {
        return Driver.getDriver().findElement(By.xpath("//*[text()='" + field + " must be at least 2 characters']"));
    }

    @FindBy(xpath = "//*[text()='remove']")
    public List<WebElement> activeContactGadget;

    @FindBy(id = "mui-component-select-caseType")
    public WebElement contactRecordTypeDrp;

    public WebElement getConFirstSearchResult(String name) {
        return Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + name + "')]"));
    }

    @FindBy(xpath = "(//span[text()='search'])[2]")
    public WebElement manCaseConsumerSearchButton;

    @FindBy(xpath = "(//*[contains(text(),'SEARCH RESULT')]/ancestor::div[1]//table/tbody/tr/td/p)[1]")
    public WebElement caseIdFRValue;

    //pagination DD on CASE / CONSUMER SEARCH
    @FindBy(id = "mui-component-select-resulstGrid")
    public WebElement pageDD;

    @FindBy(xpath = "//*[contains(text(),'SEARCH RESULT')]/following-sibling::div[2]//tbody/tr/td[2]")
    public WebElement caseIdCreated;
}
