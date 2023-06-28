package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMEditConsumerProfilePage {

    

    public CRMEditConsumerProfilePage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "consumerFirstName")
    public WebElement consUpdateFN;

    @FindBy(id = "consumerMiddleName")
    public WebElement consUpdateMN;

    @FindBy(id = "consumerLastName")
    public WebElement consUpdateLN;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement consUpdateDOB;

    @FindBy(xpath = "//*[@id='genderCode']/..")
    public WebElement consUpdateGender;

    @FindBy(id = "effectiveStartDate")
    public WebElement consUpdateStartDate;

    @FindBy(id = "effectiveEndDate")
    public WebElement consUpdateEndDate;

    @FindBy(xpath = "//*[@id='preferredLanguage']/..")
    public WebElement consUpdatePrefLang;

    @FindBy(id = "consumerSSN")
    public WebElement consUpdateSSN;

    @FindBy(xpath = "//*[contains(text(),'FIRST NAME is required and cannot be left blank')]")
    public WebElement consUpdateFNVal;

    @FindBy(xpath = "//*[contains(text(),'LAST NAME is required and cannot be left blank')]")
    public WebElement consUpdateLNVal;

    @FindBy(xpath = "//*[contains(text(),'DOB is required and cannot be left blank')]")
    public WebElement consUpdateDOBVal;

    @FindBy(xpath = "//*[contains(text(),'START DATE is required and cannot be left blank')]")
    public WebElement consUpdateStartVal;

    @FindBy(xpath = "//*[@id='mui-component-select-preferredLanguageCode']/../following-sibling::p")
    public WebElement consUpdatePrefVal;

    @FindBy(xpath="//*[contains(text(),'Invalid date format')]")
    public WebElement consUpdateDOBFormat;

    @FindBy(xpath = "//*[contains(text(),'Invalid date format')]")
    public WebElement consUpdateStartFormat;

    @FindBy(xpath = "//*[contains(text(),'Invalid date format')]")
    public WebElement consUpdateEndFormat;

    @FindBy(xpath = "//*[contains(text(),'SSN must be 9 characters')]")    //id = "SSN-helper-text"
    public WebElement consUpdateSSNFormat;

    @FindBy(xpath = "//div[@class='col-12 px-5']/button[1]")
    public WebElement continuePopUp;

    @FindBy(xpath = "//div[@class='col-12 px-5']/button[2]")
    public WebElement cancelPopUp;

    @FindBy(xpath = "//h5[@class='mx-section-header float-left']")
    public WebElement profileContactPage;

    @FindBy(xpath="(//*[contains(@class,'material-icons MuiIcon-root ml-')])[2]")
    public WebElement ssnUnMaskUpdatePage;

    @FindBy(xpath="(//div[contains(@class,'col-3')]//p[contains(text(),'CONSUMER NAME')]/following-sibling::h6)")
    public WebElement updatedFullName;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement saveButtonUpdateProfilePage;

    @FindBy(xpath = "(//span[contains(text(),'edit')])")
    public WebElement consumerEditButton;

    @FindBy(xpath = "(//span[contains(text(),'check')])")
    public WebElement saveConsumerUpdate;

    @FindBy(xpath="//*[@id='spokenLanguage']/..")
    public WebElement spokenLanguageDropdown;

    @FindBy(xpath="//*[@id='writtenLanguage']/..")
    public WebElement writtenLanguageDropdown;

    @FindBy(xpath = "(//span[contains(text(),'clear')])")
    public WebElement cancelConsumerUpdate;

    @FindBy(xpath = "//p[contains(text(),'Your updates will not be saved')]")
    public WebElement warningText;

    @FindBy(xpath = "//*[contains(text(),'INACTIVATE IMMEDIATELY')]")
    public WebElement consumerInactiveImmediateBtn;

    @FindBy(xpath = "//*[contains(@class,'mdl-data-table__cell--non-numeric mx-color-')]")
    public WebElement consumerStatus; // (//table[contains(@class,'mx-table-treeview table')])[3]

    @FindBy(xpath="//p[contains(text(),'CONSUMER NAME')]")
    public WebElement consumerNameHeader;

    @FindBy(xpath = "(//div[contains(@class,'col-3')]//p[contains(text(),'START DATE')]/following-sibling::h6)")
    public WebElement consumerStartDate;

    @FindBy(xpath = "(//div[contains(@class,'col-3')]//p[contains(text(),'END DATE')]/following-sibling::h6)")
    public WebElement consumerEndDate;

    @FindBy(xpath = "(//div[contains(@class,'col-3')]//p[contains(text(),'AGE')]/following-sibling::h6)")
    public WebElement consumerAge;

    @FindBy(xpath = "//h6[contains(@class, 'mt-0 float-right mx-color-text-primary')]/a")
    public WebElement consumerId;

    @FindBy(xpath = "//p[contains(text(),'GENDER')]/following-sibling::h6")
    public WebElement consumerGender;

    @FindBy(xpath = "//p[contains(text(),'DOB')]/following-sibling::h6")
    public WebElement consumerDOB;

    @FindBy(xpath = "//p[contains(text(),'SSN')]/following-sibling::h6")
    public WebElement consumerSSN;

    @FindBy(xpath = "//p[contains(text(),'SPOKEN LANGUAGE')]/following-sibling::h6")
    public WebElement consumerSpokenLang;

    @FindBy(xpath = "//p[contains(text(),'WRITTEN LANGUAGE')]/following-sibling::h6")
    public WebElement consumerWrittenLang;

    @FindBy(xpath = "//p[contains(text(),'CORRESPONDENCE PREFERENCE(S)')]/following-sibling::h6")
    public WebElement consumerCorrespondencePref;

    @FindBy(xpath = "(//tbody/tr/td[2])[1]")
    public WebElement convertedExternalConsumerId;

    @FindBy(xpath = "(//tbody/tr/td[1])[1]")
    public WebElement convertedCaseId;

    @FindBy(xpath = "//*[text()='COMMUNICATION OPT-IN INFORMATION']")
    public WebElement communicationOptInInfoTitle;

    @FindBy(xpath = "(//tbody/tr/td[2])")
    public WebElement caseIdOnCaseConsumerSearch;

    @FindBy(xpath = "(//tbody/tr/td[3])")
    public WebElement consumerIdOnCaseConsumerSearch;


}
