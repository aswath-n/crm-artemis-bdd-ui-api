package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CRMAddCaseMemberPage {

    

    public CRMAddCaseMemberPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(xpath = "//a[text()='CASE MEMBERS']")
    public WebElement caseNavigationHeader;

    @FindBy(xpath = "//h5[text()='CASE MEMBERS']")
    public WebElement caseMembersHeader;

    @FindBy(xpath = "//*[contains(@name,'firstName')]")
    public WebElement firstNameInput;

    @FindBy(xpath = "//*[contains(@name,'middleName')]")
    public WebElement middleNameInput;

    @FindBy(id = "consumerLastName")
    public WebElement lastNameInput;

    @FindBy(xpath = "//*[@name='age']")
    public WebElement ageInput;

    @FindBy(xpath = "//*[@data-value='Male' and @role='option']")
    public WebElement genderMaleOption;

    @FindBy(xpath = "//*[@data-value='Female' and @role='option']")
    public WebElement genderFemaleOption;

    @FindBy(xpath = "//div[@id='mui-component-select-relationShip']")
    public WebElement relationshipInput;

    @FindBy(xpath = "//*[@data-value='Child' and @role='option']")
    public WebElement relationshipChildOption;

    @FindBy(xpath = "//*[@data-value='Guardian' and @role='option']")
    public WebElement relationshipGuardianOption;

    @FindBy(xpath = "//*[@data-value='Spouse' and @role='option']")
    public WebElement relationshipISpouseOption;

    @FindBy(xpath = "//*[@data-value='English' and @role='option']")
    public WebElement languageEnglishOption;

    @FindBy(xpath = "//*[@data-value='Spanish' and @role='option']")
    public WebElement langguageSpanishOption;

    @FindBy(id = "consumerDateOfBirth")
    public WebElement dobInput;

    @FindBy(id = "CMStartDate")
    public WebElement startDateInput;

    @FindBy(id = "CMEndName")
    public WebElement endDateInput;

    @FindBy(xpath = "//div[contains(@id, \"select-gender\")]")
    public WebElement genderInput;

    @FindBy(id = "consumerSSN")
    public WebElement ssnInput;

    @FindBy(xpath = "//div[contains(@id, \"select-preferredLanguage\")]")
    public WebElement preferredLanguageField;

    @FindBy(xpath = "//span[(text()='CANCEL')]")
    public WebElement CancelButton;

    @FindBy(xpath = "//span[(text()='SAVE')]")
    public WebElement SaveButton;

    @FindBy(xpath = "//span[contains(text(),'visibility_off')]")
    public WebElement DOBvisibilityonButton;

    @FindBy(xpath = "//span[text()='visibility']")
    public WebElement DOBvisibilityoffButton;

    // need to rework
    @FindBy(xpath = "//button[contains(@aria-label,'visibility')]//span")
    public WebElement SSNvisibilityonButton;

    @FindBy(xpath = "//button[contains(@aria-label,'visibility')]//span//following-sibling::span")
    public WebElement SSNvisibilityoffButton;

    @FindBy(xpath = "//p[contains(text(),'First Name')]")
    public WebElement firstNameValidation;

    @FindBy(xpath = "//p[contains(text(),'Last Name')]")
    public WebElement lastNameValidation;

    @FindBy(xpath = "//p[contains(text(),'DOB')]")
    public WebElement dobValidation;

    @FindBy(xpath = "//p[contains(text(),'gender')]")
    public WebElement genderValidation;

    @FindBy(xpath = "//p[contains(text(),'start date')]")
    public WebElement startDateValidation;

    @FindBy(xpath = "//p[contains(text(),'Relationship to PI')]")
    public WebElement relationshipToPIValidation;

    @FindBy(xpath = "//p[contains(text(),'SSN')]")
    public WebElement ssnalidation;

    @FindBy(xpath = "//span[contains(text(),'INACTIVATE IMMEDIATELY')]")
    public WebElement checkboxLabelInactive;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement checkboxInactive;

    @FindBy(xpath = "//*[contains(@name,'firstName')]/../following-sibling::p")
    public WebElement firstNameInputErrorMessage;

    @FindBy(xpath = "//*[contains(@name,'lastName')]/../following-sibling::p")
    public WebElement lastNameInputErrorMessage;

    @FindBy(xpath = "//*[contains(text(),'DOB is required and cannot be left blank')]")
    public WebElement dobInputErrorMessage;

    @FindBy(xpath = "//*[contains(text(),'START DATE is required and cannot be left blank')]")
    public WebElement startDateInputErrorMessage;

    @FindBy(xpath = "//*[contains(text(),'RELATIONSHIP TO PI is required and cannot be left bank')]")
    public WebElement relationshipInputErrorMessage;

    @FindBy(xpath = "//*[contains(text(),'GENDER is required and cannot be left blank')]")
    public WebElement genderInputErrorMessage;

    @FindBy(xpath = "//*[contains(text(),'SSN is required and cannot be left blank')]")
    public WebElement ssnInputErrorMessage;

    @FindBy(xpath = "(//*[contains(text(), 'ACTIVE')])[2]")
    public WebElement caseMembersStatus;
}
