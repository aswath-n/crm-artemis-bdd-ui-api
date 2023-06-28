package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CRM_CORE_CommunityOutreachPage {
    

    public CRM_CORE_CommunityOutreachPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "sessionNotes")
    public WebElement communityOutreachSessionNotes;

    @FindBy(id = "mui-component-select-estimatedAttendees")
    public WebElement estimatedAttendees;

    @FindBy(xpath = "//p[contains(text(),'If you continue, all the captured information will be lost')]")
    public WebElement warningMessage;

    @FindBy(xpath = "//span[@title ='Check mark']/parent::*")
    public WebElement warningMessageContinue;

    @FindBy(xpath = "//span[@title ='Clear']/parent::*")
    public WebElement warningMessageCancel;

    @FindBy(xpath = "//h5[contains(text(),'CREATE COMMUNITY OUTREACH SESSION')]")
    public WebElement createComunityOutreachSessionScreen;

    @FindBy(xpath = "//h5[contains(text(),'CONTACT RECORD')]")
    public WebElement contactRecordSearchScreen;

    @FindBy(id = "auto-Session Type")
    public WebElement sessionType;

    @FindBy(id = "auto-Received VIA")
    public WebElement receivedVia;

    @FindBy(id = "auto-Session Region")
    public WebElement sessionRegion;

    @FindBy(id = "publicAllowed")
    public WebElement publicAllowed;

    @FindBy(id = "auto-Session Status")
    public WebElement sessionStatus;

    @FindBy(id = "multilingualInd")
    public WebElement multilingual;

    @FindBy(id = "auto-Languages")
    public WebElement languages;

    @FindBy(id = "numberOfAttendees")
    public WebElement numberOfAttendees;

    @FindBy(id = "enrollmentUpdates")
    public WebElement enrollmentUpdates;

    @FindBy(id = "surveysCollected")
    public WebElement surveysCollected;

    @FindBy(id = "callCenterVerified")
    public WebElement callCenterVerified;

    @FindBy(xpath = "//input[@value='true']")
    public WebElement yesPublicAllowed;

    @FindBy(xpath = "//input[@value='false']")
    public WebElement noPublicAllowed;

    @FindBy(xpath = "//*[contains(text(),' NEXT')]")
    public WebElement nextButton;

    @FindBy(xpath = "//*[contains(text(),' SAVE')]")
    public WebElement saveButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/span")
    public WebElement sessionDetailsNavigation;

    @FindBy(xpath = "//p[contains(text(),'SESSION TYPE is required and cannot be left blank')]")
    public WebElement sessionTypeRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'SESSION STATUS is required and cannot be left blank')]")
    public WebElement sessionStatusRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'SESSION REGION is required and cannot be left blank.')]")
    public WebElement sessionRegionRequiredErrorMessage;

    @FindBy(xpath = "//span[contains(text(), '3')]")
    public WebElement siteInformationPageSteppingHeader;

    @FindBy(id = "siteName")
    public WebElement siteName;

    @FindBy(id = "auto-Site Type")
    public WebElement siteType;

    @FindBy(id = "addressLine1")
    public WebElement addressLine1;

    @FindBy(id = "addressLine2")
    public WebElement addressLine2;

    @FindBy(id = "city")
    public WebElement city;

    @FindBy(id = "state")
    public WebElement state;

    @FindBy(id = "zip")
    public WebElement zip;

    @FindBy(id = "county")
    public WebElement county;

    @FindBy(id = "accessibleInd")
    public WebElement accessibleCheckBox;

    @FindBy(id = "notes")
    public WebElement siteNotesInput;

    @FindBy(xpath = "//p[contains(text(),'SITE NAME is required and cannot be left blank')]")
    public WebElement siteNameRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'SITE TYPE is required and cannot be left blank')]")
    public WebElement siteTypeRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'ADDRESS LINE 1 is required and cannot be left blank')]")
    public WebElement addressLine1RequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'CITY is required and cannot be left blank')]")
    public WebElement cityRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'STATE is required and cannot be left blank')]")
    public WebElement stateRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'ZIP is required and cannot be left blank')]")
    public WebElement zipRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'COUNTY is required and cannot be left blank')]")
    public WebElement countyRequiredErrorMessage;

    @FindBy(xpath = "//i[contains(text(),'error_outline')]")
    public WebElement requiredInformationIcon;

    @FindBy(xpath = "//label[contains(text(),'SESSION DATE')]")
    public WebElement sessionDate;

    @FindBy(xpath = "//label[contains(text(),'SESSION START TIME')]")
    public WebElement sessionStartTime;

    @FindBy(xpath = "//label[contains(text(),'SESSION END TIME')]")
    public WebElement sessionEndTime;

    @FindBy(xpath = "//label[contains(text(),'TRAVEL TIME')]")
    public WebElement travelTime;

    @FindBy(xpath = "//label[contains(text(),'PREPARATION TIME')]")
    public WebElement preparationTime;

    @FindBy(id = "auto-Recurrence Frequency")
    public WebElement reoccurencyFrequency;

    @FindBy(xpath = "(//input[@name = 'clientReqistrationRequiredInd'])[1]")
    public WebElement clientRegistrationRequiredYes;

    @FindBy(xpath = "(//input[@name = 'clientReqistrationRequiredInd'])[2]")
    public WebElement clientRegistrationRequiredNo;

    @FindBy(id = "auto-Presenter")
    public WebElement presenter;

    @FindBy(xpath = "//span[contains(text(), '2')]")
    public WebElement sessionSchedulePageSteppingHeader;

    @FindBy(xpath = "//p[contains(text(),'SESSION DATE is required and cannot be left blank')]")
    public WebElement sessionDateRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'SESSION START TIME is required and cannot be left blank')]")
    public WebElement sessionStartTimeRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'SESSION END TIME is required and cannot be left blank')]")
    public WebElement sessionEndTimeRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'PRESENTER is required and cannot be left blank')]")
    public WebElement presenterRequiredErrorMessage;

    @FindBy(xpath = "//span[.='event_available']")
    public WebElement leftHandNavEvenMgmtCalendarIcon;

    @FindBy(xpath = "//span[.='COMMUNITY OUTREACH MGMT']")
    public WebElement leftHandNavCommOutreachMgmtButton;

    @FindBy(xpath = "//button[@data-menuname='communityOutreachActivity']/span[.='keyboard_arrow_down']")
    public WebElement leftHandNavCommArrowSelection;

    @FindBy(xpath = "//span[.='CREATE SESSION']")
    public WebElement leftHandCommunityCreateSession;

    @FindBy(xpath = "//span[text()='SEARCH SESSION']")
    public WebElement leftHandCommunitySearchSession;

    @FindBy(xpath = "//span[contains(text(), '4')]")
    public WebElement siteContactInfoPageSteppingHeader;

    @FindBy(id = "contactFirstName")
    public WebElement siteContactFirstName;

    @FindBy(id = "contactLastName")
    public WebElement siteContactLastName;

    @FindBy(id = "contactEmail")
    public WebElement siteContactEmail;

    @FindBy(id = "contactAddressLine1")
    public WebElement siteContactAddressLine1;

    @FindBy(id = "contactAddressLine2")
    public WebElement siteContactAddressLine2;

    @FindBy(id = "auto-City")
    public WebElement siteContactCity;

    @FindBy(id = "auto-State")
    public WebElement siteContactState;

    @FindBy(id = "auto-Zip")
    public WebElement siteContactZip;

    @FindBy(id = "contactWorkPhone")
    public WebElement siteContactWorkPhone;

    @FindBy(id = "contactFaxPhone")
    public WebElement siteContactFaxPhone;

    @FindBy(id = "cellPhone")
    public WebElement siteContactCellPhone;

    @FindBy(xpath = "//p[contains(text(),'FIRST NAME is required and cannot be left blank')]")
    public WebElement firstNameRequiredErrorMessage;

    @FindBy(xpath = "(//*[@class=\"MuiSvgIcon-root\"])[1]")
    public WebElement sessionDateCalendar;

    @FindBy(xpath = "//span[contains(text(),'OK')]")
    public WebElement calendarOKButton;

    @FindBy(xpath = "(//*[@class=\"MuiSvgIcon-root\"])[2]")
    public WebElement sessionStartTimeCalendar;

    @FindBy(xpath = "(//*[@class=\"MuiSvgIcon-root\"])[3]")
    public WebElement sessionEndTimeCalendar;

    @FindBy(xpath = "//*[contains(text(),'Success Saving Community  Session')]")
    public WebElement successSaveMessage;

    @FindBy(xpath = "//button[contains(text(),' SEARCH')]")
    public WebElement searchButton;

    @FindBy(xpath = "//button[contains(text(),' CANCEL')]")
    public WebElement cancelButton;

    @FindBy(id = "communityOutreachId")
    public WebElement sessionID;

    @FindBy(id = "auto-Presenter Name")
    public WebElement presenerName;

    @FindBy(id = "auto-Recurrence Frequency")
    public WebElement reoccurencyFrequencySearch;

    public WebElement firstFoundContactRecordFromSearch(int id) {
        return Driver.getDriver().findElement(By.xpath("//td[contains(text(),'" + id + "')]"));
    }

    @FindBy(xpath = "//p[contains(text(),'CREATED BY')]")
    public WebElement createdByLabel;

    @FindBy(xpath = "//p[contains(text(),'Service AccountOne')]")
    public WebElement createdByValue;

    @FindBy(xpath = "//p[contains(text(),'CREATED DATE')]")
    public WebElement createdDateLabel;

    @FindBy(xpath = "//p[contains(text(),'EDITED BY')]")
    public WebElement editedByLabel;

    @FindBy(xpath = "//p[contains(text(),'EDITED DATE')]")
    public WebElement editedDateLabel;

    @FindBy(xpath = "//p[contains(text(),'REASON FOR EDIT')]")
    public WebElement reasonForEditLabel;

    @FindBy(xpath = "//p[contains(text(),'Education/Enrollment Session')]")
    public WebElement sessionTypeValue;

    @FindBy(xpath = "//p[contains(text(),'Requested')]")
    public WebElement sessionStatusValue;

    @FindBy(xpath = "//p[contains(text(),'11/22/2022')]")
    public WebElement sessionDateValue;

    @FindBy(xpath = "//p[contains(text(),'11:33 AM')]")
    public WebElement sessionStartTimeValue;

    @FindBy(xpath = "//p[contains(text(),'02:33 AM')]")
    public WebElement sessionEndTimeValue;

    @FindBy(xpath = "//p[contains(text(),'John')]")
    public WebElement presenterValue;

    @FindBy(xpath = "//p[contains(text(),'hfhfhf')]")
    public WebElement siteNameValue;

    @FindBy(xpath = "//p[contains(text(),'5363 ghdfhfdh')]")
    public WebElement siteInfoAddressLineValue;

    @FindBy(xpath = "//p[contains(text(),'chicago')]")
    public WebElement siteInfoCityValue;

    @FindBy(xpath = "//p[contains(text(),'IL')]")
    public WebElement siteInfoStateValue;

    @FindBy(xpath = "//p[contains(text(),'34634-6477')]")
    public WebElement siteInfoZipValue;

    @FindBy(xpath = "//p[contains(text(),'Cook')]")
    public WebElement siteInfoCountyValue;

    @FindBy(xpath = "//p[contains(text(),'Aika')]")
    public WebElement siteContactInfoFirstNameValue;

    @FindBy(xpath = "//p[contains(text(),'Begi')]")
    public WebElement siteContactInfoLastNameValue;

    @FindBy(xpath = "//p[contains(text(),'aikabegi@gmail.com')]")
    public WebElement siteContactInfoEmailValue;

    @FindBy(xpath = "//p[contains(text(),'0284 sdfgsdgsd')]")
    public WebElement siteContactInfoAddressLine1Value;

    @FindBy(xpath = "//p[contains(text(),'3532 gfhdfhfd')]")
    public WebElement siteContactInfoAddressLine2Value;

    @FindBy(xpath = "//p[contains(text(),'3532 gfhdfhfd')]")
    public WebElement siteContactInfoWorkPhoneValue;

    @FindBy(xpath = "//p[contains(text(),'3532 gfhdfhfd')]")
    public WebElement siteContactInfoFaxPhoneValue;

    @FindBy(xpath = "//p[contains(text(),'3532 gfhdfhfd')]")
    public WebElement siteContactInfoCellPhoneValue;

    @FindBy(xpath = "//h5[contains(text(),'SESSION ID:')]")
    public WebElement sessionId;

    @FindBy(xpath = "//span[contains(text(),'EDIT SESSION')]")
    public WebElement editSessionButton;

    @FindBy(id = "contactAddressLine1")
    public WebElement contactAddressLine1;

    @FindBy(id = "auto-Channel")
    public WebElement channel;

    @FindBy(xpath = "//p[contains(text(),'CHANNEL is required and cannot be left blank.')]")
    public WebElement channelRequiredErrorMessage;

    @FindBy(xpath = "//p[contains(text(),'LANGUAGES is required and cannot be left blank.')]")
    public WebElement languagesRequiredErrorMessage;

    @FindBy(xpath = "//button[contains(text(), 'CALENDAR VIEW')]")
    public WebElement calendarViewButton;

    @FindBy(xpath = "//button[contains(text(), 'EXPORT')]")
    public WebElement exportButton;

    @FindBy(xpath = "//p[contains(text(),'Session details')]/..")
    public WebElement sessionDetailsNavigateTabButton;

    @FindBy(xpath = "//p[contains(text(),'Session schedule')]/..")
    public WebElement sessionScheduleNavigateTabButton;

    @FindBy(xpath = "//p[contains(text(),'Site Information')]/..")
    public WebElement siteInfoNavigateTabButton;

    @FindBy(xpath = "//p[contains(text(),'Site contact info')]/..")
    public WebElement siteContactInfoNavigateTabbutton;

    @FindBy(id = "auto-Ward")
    public WebElement wardDropdown;

    @FindBy(xpath = "//ul[@id='auto-Site Type-popup']/li")
    public List<WebElement> siteTypeValuesList;

    @FindBy(xpath = "//ul[@id='auto-Ward-popup']/li")
    public List<WebElement> wardValuesList;

    @FindBy(xpath = "//ul[@id='auto-Session Region-popup']/li")
    public List<WebElement> sessionRegionValuesList;

    @FindBy(xpath = "//ul[@id='auto-Channel-popup']/li")
    public List<WebElement> channelValueList;

    @FindBy(xpath = "//ul[@id='auto-Session Type-popup']/li")
    public List<WebElement> sessionTypeValuesList;

    @FindBy(xpath = "//ul[@id='auto-Languages-popup']/li")
    public List<WebElement> languagesValuesList;

    @FindBy(xpath = "//h5[contains(text(), 'SEARCH RESULT')]/../following-sibling::div//h2")
    public WebElement communityOutreachSearchDate;

    @FindBy(xpath = "//h5[contains(text(), 'SEARCH RESULT')]/../following-sibling::div/div[2]//tbody//td[2]")
    public WebElement communityOutreachSessionCalendarViewActualData;

    @FindBy(xpath = "//input[@id='auto-Languages']/../div/button[@title='Open']")
    public WebElement languagesOpenButton;
}