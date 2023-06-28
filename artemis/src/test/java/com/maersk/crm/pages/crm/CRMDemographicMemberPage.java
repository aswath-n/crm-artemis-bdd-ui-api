package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMDemographicMemberPage {

    

    public CRMDemographicMemberPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "//*[text()='PRIMARY INDIVIDUALS']")
    public WebElement piHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='CONSUMER ID'])[1]")
    public WebElement consumerIdHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='FULL NAME'])[1]")
    public WebElement fullNameHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='DATE OF BIRTH'])[1]")
    public WebElement dobHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='AGE / GENDER'])[1]")
    public WebElement ageGenderHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='SSN'])[1]")
    public WebElement ssnHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='LANGUAGE'])[1]")
    public WebElement languageHeader;

    //Refactored Xpath --aswath 04/04/2019
    @FindBy(xpath = "(//th[text()='STATUS'])[1]")
    public WebElement statusHeader;

    @FindBy(xpath="(//*[contains(@class , 'MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 ')])[1]//tr[1]//td[3]")
    public WebElement firstPIConsumerID;

    @FindBy(xpath="(//*[contains(@class , 'MuiTable-root mdl-data-table mdl-js-data-table mx-table table mt-4 ')])[2]//tr[1]//td[3]")
    public WebElement firstCMConsumerID;

    //Changed to td[8] due new column -aswath 04/03/2019
    @FindBy(xpath="(//th[contains(text(),'CONSUMER ID')]/parent::tr/parent::thead/parent::table/tbody/tr[1]/td[8])[1]")
    public WebElement firstPIStatus;

    //@FindBy(xpath = "//*[contains(text(),'PRIMARY INDIVIDUALS')]//following-sibling::button")
    @FindBy(xpath = "//*[@id=\"members\"]/div[1]/button/span[1]/span")
    public WebElement addPIButton;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')])")
    public WebElement CaseMemberLabel;

    @FindBy(xpath = "//*[contains(text(),'CASE MEMBERS')]//following-sibling::button")
    public WebElement addCaseMemeberButton;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'CONSUMER ID')])")
    public WebElement consumerIDCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'FULL NAME')])")
    public WebElement fullNameCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'DATE OF BIRTH')])")
    public WebElement dateOfBirthCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'AGE')])")
    public WebElement  ageCoulmnDHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'SSN')])")
    public WebElement ssnCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'RELATIONSHIP')])")
    public WebElement relationshipCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'LANGUAGE')])")
    public WebElement languageCoulmnHeader;

    @FindBy(xpath = "(//*[contains(text(),'CASE MEMBERS')]//following-sibling::div//*[contains(text(),'STATUS')])")
    public WebElement statusCoulmnHeader;

    @FindBy(xpath = "(//*[(text()='CASE MEMBERS')]//following-sibling::div[contains(@class,'table')]//tr//td)")
    public WebElement caseMemberEachTableCell;

    @FindBy(xpath = "//*[(text()='CASE MEMBERS')]//following-sibling::div[contains(@class,'table')]//tbody/tr[1]")
    public List<WebElement> caseMemberEachRows;

    public By tableRows = By.xpath("//*[(text()='CASE MEMBERS')]//following-sibling::div[contains(@class,'table')]//tr");
    public By tablecells = By.xpath("//*[(text()='CASE MEMBERS')]//following-sibling::div[contains(@class,'table')]//tr//td");
    public By pages= By.xpath("//*[(text()='CASE MEMBERS')]//following-sibling::div[contains(@class,'table')]//ul[@class='pagination']//li//a");


    //p[contains(text(),'END DATE')]//following-sibling::h3
//p[contains(text(),'END DATE')]
    @FindBy(xpath = "(//p[contains(text(),'END DATE')])")
    public WebElement inactiveEndDateTooltipText;

    @FindBy(xpath = "(//p[contains(text(),'END DATE')]//following-sibling::h6)")
    public WebElement inactiveEndDateTooltipVaule;

    @FindBy(xpath = "(//p[contains(text(),'START DATE')]//following-sibling::h6)")
    public WebElement inactiveStartDateTooltipVaule;

	  @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'CONSUMER ID')])[1]/parent::tr/parent::thead/parent::table/tbody")
    })   //deleted /tr at the end
    public List<WebElement> listOfPI;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'CONSUMER ID')])[1]/parent::tr/parent::thead/parent::table/tbody/tr/td[7]")
    })
    public List<WebElement> listOfPIStatuses;

    @FindAll({
            @FindBy(xpath = "(//div[@role='tooltip']/div/div/div)[1]/h6")
    })
    public List<WebElement> listOfPISStartDates;

    @FindBy(xpath = "(//th[text()='DATE OF BIRTH']/span[text()='visibility'])[2]")
    public WebElement caseMemberDobEyeIcon;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]//following-sibling::button")
    public WebElement addARButton;

    @FindBy(xpath = "//*[@id=\"members\"]/div[3]/div[2]/div/table/tbody/tr[1]/td[2]")
    public WebElement firstARconsumer;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mr-2'])[1]")
    public WebElement addPrimaryIndividualBtn;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mr-2'])[2]")
    public WebElement addCaseMemberBtn;

    @FindBy(xpath = "//*[contains(text(), 'PRIMARY INDIVIDUAL')]")
    public WebElement primaryIndividualPage;

    @FindBy(xpath = "//*[contains(text(), 'CASE MEMBER')]")
    public WebElement caseMemberPage;

    @FindBy(xpath = "//*[contains(text(), 'AUTHORIZED REPRESENTATIVE')]")
    public WebElement authorizedRepPage;

    @FindBy(name = "consumerFirstName")
    public WebElement firstName;

    @FindBy(name = "consumerLastName")
    public WebElement lastName;

    @FindBy(id="mui-component-select-genderCode")
    public WebElement gender;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd'])[1]")
    public WebElement dob;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement arDob;

    @FindBy(id="mui-component-select-genderCode")
    public WebElement arGender;

    @FindBy(id = "effectiveStartDate")
    public WebElement piStartDate;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd'])[2]")
    public WebElement startDate;

    @FindBy(id = "consumerSSN")
    public WebElement ssn;

    @FindBy(xpath = "(//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiInputBase-input MuiInput-input'])[4]")
    public WebElement relToPI;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement saveMemberBtn;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn mx-btn-border mx-btn-primary']")
    public WebElement arSaveMemberBtn;

    @FindBy(xpath = "//*[text()='Continue']/parent::button")
    public WebElement continueMemberBtn;

    @FindBy(xpath = "(//span[@class='material-icons MuiIcon-root mr-2'])[3]")
    public WebElement addAuthorizedRepBtn;

    @FindBy(xpath = "(//*[@id='mui-component-select-accessType']//.)")
    public WebElement arAccessType;

    @FindBy(xpath = "(//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd'])[1]")
    public WebElement arStartDate;

   // @FindBy(xpath = "//span[contains(text(), 'EDIT')]")
    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn mx-btn-border mx-btn-primary mdl-shadow--2dp ml-2 float-right']")
    public WebElement contactEdit;

    // @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mx-btn mx-btn-border mx-btn-primary']")
    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary mdl-shadow--2dp mr-3']")
    public WebElement contactEditSaveBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[1]")
    public WebElement arStartDateBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[3]")
    public WebElement arDobBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary'])[3]")
    public WebElement dateSelectBtn;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]")
    public WebElement authorizedRepresLabel;

    @FindBy(xpath = "//h5[contains(text(),'PRIMARY INDIVIDUALS')]")
    public WebElement primaryIndividualLabel;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[1]")
    public WebElement piCmDobBtn;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[2]")
    public WebElement piCmStartDateBtn;

    @FindBy(id= "consumerLastName")
    public WebElement consumerLastName;

    @FindBy(id="mui-component-select-preferredLanguage")
    public WebElement preferredLang;

    @FindBy(id="consumerSSN")
    public WebElement consumerSsn;

    @FindBy(xpath = "//*[contains(text(),'keyboard_backspace')]")
    public WebElement memberBackBtn;

    @FindBy(id="consumerFirstName")
    public WebElement consumerFrstName;

    @FindBy(id="consumerDateOfBirth")
    public WebElement consumerDoB;

    @FindBy(id="mui-component-select-correspondencePreference")
    public WebElement corresPref;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary mdl-shadow--2dp mr-3']")
    public WebElement saveAr;

    @FindBy(xpath = "//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd']")
    public WebElement startDateAr;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary float-left mb-4']")
    public WebElement continuePop;

    @FindBy(id="mui-component-select-writtenLanguage")
    public WebElement languagePre;

    @FindBy(id="mui-component-select-relationShip")
    public WebElement relationShp;

    @FindBy(id="mui-component-select-genderCode")
    public WebElement caseGender;

    @FindBy(id="effectiveStartDate")
    public WebElement effectiveStartDate;

    @FindBy(id = "mui-component-select-correspondencePreference")
    public WebElement correspondencePrefPI;

    @FindBy(xpath = "//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button']/.")
    public WebElement correspondencePrefDropDownVal;

    @FindBy(xpath = "//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root Mui-selected MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button Mui-selected']")
    public WebElement getCorrespondencePrefDropDownValSelected;

    @FindBy(xpath = "(//h5[@class='mx-section-header float-left mt-4'])[1]")
    public WebElement demographicMembersCheck;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiTab-root MuiTab-textColorPrimary Mui-selected MuiTab-fullWidth']")
    public WebElement demographicMembersPage;

    @FindBy(xpath = "(//button[@class='MuiButtonBase-root MuiIconButton-root'])[2]")
    public WebElement piStartDateBtn;

    @FindBy(id="effectiveEndDate")
    public WebElement effectiveEndDate;

    @FindBy(xpath = "//*[@id='mui-component-select-receiveCorrespondence']")
    public WebElement receiveCorrespondence;

    @FindBy(xpath = "//*[contains(text(), 'CONSUMER ROLE')]//..")
    public WebElement roleDropdown;

    @FindBy(xpath = "//*[@id='consumerRole']")
    public WebElement roleDropdownValue;

    @FindBy(xpath = "//*[contains(text(), 'RELATIONSHIP TO PI')]//..")
    public WebElement relationshipToPI;

    @FindBy(xpath = "//div[@class='col-12 px-5 mt-2']//p")
    public WebElement warningMessageCerentActiveAddress;

    @FindBy(id = "mui-component-select-relationShip")
    public WebElement relationshipToPIDropDown;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[2]")
    public WebElement authRepConsumerId;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[3]")
    public WebElement authRepName;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[4]")
    public WebElement authRepDOB;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[5]")
    public WebElement authRepAge;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[6]")
    public WebElement authRepSSNvalue;

    @FindBy(xpath = "//h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::tbody/tr[1]/td[7]")
    public WebElement AuthRepStatusValue;

    @FindBy(xpath = " //h5[contains(text(),'AUTHORIZED REPRESENTATIVE')]/following::h5[text()='No Records Available']")
    public WebElement AuthRepNoRecordAvailable;
}
