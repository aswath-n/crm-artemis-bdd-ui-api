package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRM_CORE_CommunityEditPage {
    private WebDriver driver;

    public CRM_CORE_CommunityEditPage() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'edit')]/..")
    public WebElement editSessionButton;

    @FindBy(xpath = "//h5[contains(text(),'COMMUNITY OUTREACH SESSION DETAILS')]")
    public WebElement communityOutreachDeatilsPageHeader;

    @FindBy(xpath = "//h5[contains(text(),'SESSION ID: ')]")
    public WebElement sessionIdHeader;

    @FindBy(xpath = "//h5[.='SESSION DETAILS']")
    public WebElement sessionDetailsHeader;

    @FindBy(xpath = "//h5[.='POST SESSION FOLLOW UP']")
    public WebElement postSessionFollowUpHeader;

    @FindBy(xpath = "//h5[.='SESSION SCHEDULE']")
    public WebElement sessionScheduleHeader;

    @FindBy(xpath = "//h5[.='SITE INFORMATION']")
    public WebElement siteInformationHeader;

    @FindBy(xpath = "//h5[.='SITE CONTACT INFORMATION']")
    public WebElement siteContactInformationHeader;

    @FindBy(id = "mui-component-select-reasonForEdit")
    public WebElement reasonForEdit;

    @FindBy(id = "auto-Session Type")
    public WebElement sessionType;

    @FindBy(id = "auto-Channel")
    public WebElement channel;

    @FindBy(id = "mui-component-select-estimatedAttendees")
    public WebElement estimateAttendees;

    @FindBy(id = "auto-Session Region")
    public WebElement sessionRegion;

    @FindBy(xpath = "//label[@id='publicAllowedInd']/following-sibling::div//input[@name='publicAllowedInd' and @value='true']")
    public WebElement yesPublicAllowedButton;

    @FindBy(xpath = "//label[@id='publicAllowedInd']/following-sibling::div//input[@name='publicAllowedInd' and @value='false']")
    public WebElement noPublicAllowedButton;

    @FindBy(id = "auto-Session Status")
    public WebElement sessionStatus;

    @FindBy(id = "auto-Languages")
    public WebElement languages;

    @FindBy(id = "sessionNotes")
    public WebElement sessionNotes;

    @FindBy(id = "numOfAttendees")
    public WebElement numOfAttendees;

    @FindBy(id = "enrollmentUpdates")
    public WebElement enrollmentUpdates;

    @FindBy(id = "numOfSurveysCollected")
    public WebElement numOfSurveysCollected;

    @FindBy(id = "numOfCallVerified")
    public WebElement numOfCallVerified;

    @FindBy(xpath = "//label[contains(text(),'SESSION DATE')]/following-sibling::div/input")
    public WebElement sessionDate;

    @FindBy(xpath = "//label[contains(text(),'SESSION DATE')]/following-sibling::div/div/button")
    public WebElement sessionDatePicker;

    @FindBy(xpath = "//label[contains(text(),'SESSION START TIME')]/following-sibling::div/input")
    public WebElement sessionStartTime;

    @FindBy(xpath = "//label[contains(text(),'SESSION START TIME')]/following-sibling::div/div/button")
    public WebElement sessionStartTimePicker;

    @FindBy(xpath = "//label[contains(text(),'SESSION END TIME')]/following-sibling::div/input")
    public WebElement sessionEndTime;

    @FindBy(xpath = "//label[contains(text(),'SESSION END TIME')]/following-sibling::div/div/button")
    public WebElement sessionEndTimePicker;

    @FindBy(xpath = "//label[contains(text(),'TRAVEL TIME')]/following-sibling::div/input")
    public WebElement travelTime;

    @FindBy(xpath = "//label[contains(text(),'TRAVEL TIME')]/following-sibling::div/div/button")
    public WebElement travelTimePicker;

    @FindBy(xpath = "//label[contains(text(),'PREPARATION TIME')]/following-sibling::div/input")
    public WebElement preparationTime;

    @FindBy(xpath = "//label[contains(text(),'PREPARATION TIME')]/following-sibling::div/div/button")
    public WebElement preparationTimePicker;

    @FindBy(xpath = "//div[@class='MuiPickersBasePicker-container']")
    public WebElement dateTimePicker;

    @FindBy(id = "auto-Recurrence Frequency")
    public WebElement recurrenceFrequency;

    @FindBy(xpath = "//label[@id='clientReqistrationRequiredInd']/following-sibling::div//input[@name='clientReqistrationRequiredInd' and @value='false']")
    public WebElement noClientReqistrationRequiredInd;

    @FindBy(xpath = "//label[@id='clientReqistrationRequiredInd']/following-sibling::div//input[@name='clientReqistrationRequiredInd' and @value='true']")
    public WebElement yesClientReqistrationRequiredInd;

    @FindBy(id = "auto-Presenter")
    public WebElement presenter;

    @FindBy(id = "siteName")
    public WebElement siteName;

    @FindBy(id = "auto-Site Type")
    public WebElement siteType;

    @FindBy(id = "addressLine1")
    public WebElement siteAddressLine1;

    @FindBy(id = "addressLine2")
    public WebElement siteAddressLine2;

    @FindBy(xpath = "//input[@id='auto-City' and @name ='city']")
    public WebElement siteCity;

    @FindBy(xpath = "//input[@id='auto-State' and @name ='state']")
    public WebElement siteState;

    @FindBy(xpath = "//input[@id='auto-Zip' and @name ='zip']")
    public WebElement siteZip;

    @FindBy(id = "auto-County")
    public WebElement siteCounty;

    @FindBy(id = "accessibleInd")
    public WebElement accessibleIndCheckbox;

    @FindBy(xpath = "//span[.='ACCESSIBLE']")
    public WebElement accessibleCheckobxLabel;

    @FindBy(id = "notes")
    public WebElement siteNotes;

    @FindBy(id = "contactFirstName")
    public WebElement contactFirstName;

    @FindBy(id = "contactLastName")
    public WebElement contactLastName;

    @FindBy(id = "contactEmail")
    public WebElement contactEmail;

    @FindBy(id = "contactEmail-helper-text")
    public WebElement contactEmailIncorrectMsg;

    @FindBy(id = "contactAddressLine1")
    public WebElement contactAddressLine1;

    @FindBy(id = "contactAddressLine2")
    public WebElement contactAddressLine2;

    @FindBy(xpath = "//input[@id='auto-City' and @name ='contactCity']")
    public WebElement contactCity;

    @FindBy(xpath = "//input[@id='auto-State' and @name ='contactState']")
    public WebElement contactState;

    @FindBy(xpath = "//input[@id='auto-Zip' and @name ='zip']")
    public WebElement contactZip;

    @FindBy(id = "contactWorkPhone")
    public WebElement contactWorkPhone;

    @FindBy(id = "contactFaxPhone")
    public WebElement contactFaxPhone;

    @FindBy(id = "cellPhone")
    public WebElement cellPhone;

    @FindBy(xpath = "//button[.='CANCEL']")
    public WebElement cancelButton;

    @FindBy(xpath = "//button[.='done SAVE']")
    public WebElement saveButton;

    @FindBy(xpath = "//p[.='CREATED BY']")
    public WebElement createdByLabel;

    @FindBy(xpath = "//p[.='CREATED BY']/following-sibling::p")
    public WebElement createdByValue;

    @FindBy(xpath = "//p[.='CREATED DATE']")
    public WebElement createdDateLabel;

    @FindBy(xpath = "//p[.='CREATED DATE']/following-sibling::p")
    public WebElement createdDateValue;

    @FindBy(xpath = "//p[.='EDITED BY']")
    public WebElement editedByLabel;

    @FindBy(xpath = "//p[.='EDITED BY']/following-sibling::p")
    public WebElement editedByValue;

    @FindBy(xpath = "//p[.='EDITED DATE']")
    public WebElement editedDateLabel;

    @FindBy(xpath = "//p[.='EDITED DATE']/following-sibling::p")
    public WebElement editedDateValue;

    @FindBy(xpath = "//p[.='REASON FOR EDIT']")
    public WebElement reasonForEditLabel;

    @FindBy(xpath = "//p[.='REASON FOR EDIT']/following-sibling::p")
    public WebElement reasonForEditValue;

    @FindBy(xpath = "//h5[.='SESSION DETAILS']/../div/following-sibling::div//label")
    public List<WebElement> sessionDetailsLabelsList;

    @FindBy(xpath = "//h5[.='POST SESSION FOLLOW UP']/../div/following-sibling::div//label")
    public List<WebElement> postSessionsFollowUpLabelsList;

    @FindBy(xpath = "//h5[.='SESSION SCHEDULE']/../div/following-sibling::div//label")
    public List<WebElement> sessionScheduleLabelsList;

    @FindBy(xpath = "//h5[.='SITE INFORMATION']/../div/following-sibling::div//label")
    public List<WebElement> siteInformationLabelsList;

    @FindBy(xpath = "//h5[.='SITE CONTACT INFORMATION']/../div/following-sibling::div//label")
    public List<WebElement> siteContactInfoLabelsList;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root MuiButton-root MuiButton-text mdl-button--primary mx-btn mx-btn-border mx-btn-cancel float-left ml-2')]")
    public WebElement warningMessageCancel;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root MuiButton-root MuiButton-text mx-btn  mx-btn-border mx-btn-primary float-left mb-4')]")
    public WebElement warningMessageContinue;

    @FindBy(xpath = "//*[text()='keyboard_backspace']")
    public WebElement backspaceArrow;

    @FindBy(xpath = "//p[.='EDITED BY']/following-sibling::p")
    public WebElement detailsPageEditedByValue;

    @FindBy(xpath = "//p[.='EDITED DATE']/following-sibling::p")
    public WebElement detailsPageEditedDateValue;

    @FindBy(xpath = "//p[.='REASON FOR EDIT']/following-sibling::p")
    public WebElement detailsPageReasonForEditValue;

    @FindBy(xpath = "//p[contains(text(),'is required')]")
    public List<WebElement> requiredErrorList;

























}
