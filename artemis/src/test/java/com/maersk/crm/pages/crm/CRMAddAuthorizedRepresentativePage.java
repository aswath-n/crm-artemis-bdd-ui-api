package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRMAddAuthorizedRepresentativePage {

    

    public CRMAddAuthorizedRepresentativePage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//div[@id='members']/div[3]/button)")
    public WebElement arAddButton;

    @FindBy(xpath = "//*[@name='accessType']//..")
    public WebElement arAccessType;

    @FindBy(id = "effectiveStartDate")
    public WebElement arStartDate;

    @FindBy(id = "effectiveEndDate")
    public WebElement arEndDate;

    @FindBy(id = "consumerFirstName")
    public WebElement arFirstName;

    @FindBy(id = "consumerMiddleName")
    public WebElement arMiddleName;

    @FindBy(id = "consumerLastName")
    public WebElement arLastName;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement arDOB;

    @FindBy(id= "age")
    public WebElement arAge;

    @FindBy(xpath = "//*[@id='genderCode']/..")
    public WebElement arGender;

    @FindBy(id = "consumerSSN")
    public WebElement arSSN;

    @FindBy(xpath = "//*[@id='spokenLanguage']/..")
    public WebElement arSpokenLanguage;

    @FindBy(xpath = "//*[@id='writtenLanguage']/..")
    public WebElement arWrittenLanguage;

    @FindBy(xpath = "//ul/li[contains(text(), 'English')]/ancestor::ul/li[1]")
    public WebElement arPreferredLangEmpty;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement arSaveButton;

    @FindBy(xpath = "//span[contains(text(),'clear')]")
    public WebElement arCancelButton;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[1]")
    public WebElement arAccessTypeError;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[2]")
    public WebElement arStartDateError;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[3]")
    public WebElement arFirstNameError;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[4]")
    public WebElement arLastNameError;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[5]")
    public WebElement arDOBError;

    @FindBy(xpath = "(//p[@class='MuiFormHelperText-root Mui-error Mui-required'])[6]")
    public WebElement arPreferredLangError;

    @FindBy(xpath = "//p[contains(text(), 'If you')]")
    public WebElement arModalWarningMessage;

    @FindBy(xpath = "(//button[contains(@class,'mx-btn  mx-btn-border mx-btn-primary float-left mb-4')])")
    public WebElement arModalContButton;

    @FindBy(xpath = "(//button[contains(@class,'mdl-button--primary mx-btn mx-btn-border mx-btn-cancel float-left ml-2')])")
    public WebElement arModalCancelButton;

    @FindBy(xpath = "//p[@class='px-3']")
    public WebElement ARSaveWarningMessage;

    @FindBy(xpath = "(//span[contains(text(), 'check')])[4]/../..")
    public WebElement arContinueButton;

    @FindBy(xpath = "//span[contains(text(), 'SUCCESS MESSAGE')]/ancestor::div[1]/span")
    public WebElement arModalSuccessMessage;

    @FindBy(xpath = "(//h5[contains(text(), 'AUTHORIZED REPRESENTATIVE')]/following-sibling::div[2]/div//table/tbody/tr[1]/td[2])")
    public WebElement arView;

    @FindBy(xpath = "(//p[contains(text(), 'SOURCE')]/following-sibling::p[1])")
    public WebElement arSource;

    @FindBy(xpath = "(//p[contains(text(), 'CREATED BY')]/following-sibling::p[1])")
    public WebElement arCreatedBy;

    @FindBy(xpath = "(//p[contains(text(), 'CREATED ON')]/following-sibling::p[1])")
    public WebElement arCreatedOn;

    @FindBy(xpath = "//*[@id=\"ARStartDate\"]")
    public WebElement consumerARStartDate;

    @FindBy(xpath = "//*[@id=\"AREndDate\"]")
    public WebElement consumerAREndDate;

    @FindBy(xpath = "(//table//td[contains(@class,'-status')])[3]")
    public WebElement consumerARStatus;

    public By ARtablecells = By.xpath("//*[(text()='AUTHORIZED REPRESENTATIVE')]//following-sibling::div[contains(@class,'table')]//tr//td");
    public By ARtableRows = By.xpath("//*[(text()='AUTHORIZED REPRESENTATIVE')]//following-sibling::div[contains(@class,'table')]//tr");

    @FindBy(xpath = "//div[contains(@id,'correspondencePreference')]")
    public List<WebElement> correspondencePreferenceDropdown;

    @FindBy(id = "mui-component-select-correspondencePreference")
    public WebElement correspondencePreferenceDropdownOptions;

    @FindBy(xpath = "//li[contains(text(),'Paperless')]")
    public WebElement paperlessOption;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'CONSUMER ID')])[3]/parent::tr/parent::thead/parent::table/tbody")
    })
    public List<WebElement> listOfAR;

    @FindBy(xpath = "//label[contains(text(),'PREFERRED LANGUAGE')]")
    public WebElement preferredLangaugeLabel;

    @FindBy(id = "mui-component-select-receiveCorrespondence")
    public WebElement arReceiveCorrespondence;

    @FindBy(xpath = "//*[@name='spokenLanguage']//..")
    public WebElement arspokenLanguage;

    @FindBy(xpath = "//*[@name='writtenLanguage']//..")
    public WebElement arwrittenLanguage;

    @FindBy(xpath = "//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]//following-sibling::div//table/tbody/tr/td[2]")
    public WebElement selectAuthorizedRepresentativeRecord;


    public List<WebElement> getHiddenEyeElements() {
        return  Driver.getDriver().findElements(By.xpath("//button[contains(@class,'MuiIconButton-colorPrimary')]"));
    }

    public WebElement arCheckStatus(String status){
        return  Driver.getDriver().findElement(By.xpath("//*[contains(text(),'AUTHORIZED REPRESENTATIVE')]//following-sibling::div//table/tbody/tr/td[text()='"+status+"']"));
    }

    public WebElement getReuiredFieldValidation(String field){
        return Driver.getDriver().findElement   (By.xpath("//*[text()='"+field+" is required and cannot be left blank']"));
    }

    @FindBy(xpath = "//*[text()='Invalid date format']")
    public WebElement invalidDOB;

    @FindBy(xpath = "//*[text()='The Start Date is not in the correct format.']")
    public WebElement invalidStartDate;

    @FindBy(xpath = "//*[text()='SSN must be 9 characters']")
    public WebElement invalidSSN;

    @FindBy(xpath = "//span[contains(text(), 'keyboard_backspace')]")
    public WebElement arKeyboardBackSapce;

    @FindBy(xpath = "//span[text()='call']")
    public WebElement arCallButton;

    @FindBy(id="inactiveLabel")
    public  WebElement arInactive;

    @FindBy(xpath = "//*[contains(text(), 'Authorized Representative successfully updated')]")
    public WebElement arModalUpdateSuccessMessage;

    @FindBy(xpath = "//*[@aria-label='Toggle Visibility']")
    public WebElement unmaskDOB;

    @FindBy(xpath = "//*[@aria-label='Toggle SSN visibility']")
    public WebElement unmaskSSN;
}
