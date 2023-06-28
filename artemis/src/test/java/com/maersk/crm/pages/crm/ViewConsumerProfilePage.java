package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class ViewConsumerProfilePage {
    private BrowserUtils browserUtils = new BrowserUtils();


    public ViewConsumerProfilePage() {

        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//p[text()='ID']")
    public WebElement ccProfIdLabel;

    @FindBy(xpath = "//*[text()='CONSUMER NAME']")
    public WebElement cProfNameLabel;

    @FindBy(xpath = "//*[text()='DATE OF BIRTH']")
    public WebElement ccProfDobLabel;

    @FindBy(xpath = "//*[text()='SOCIAL SECURITY NUMBER']")
    public WebElement ccProfSsnLabel;

    @FindBy(xpath = "//*[text()='GENDER']")
    public WebElement ccProfGenderLabel;

    @FindBy(xpath = "//*[text()='AGE']")
    public WebElement ccProfAgeLabel;

    @FindBy(xpath = "//*[text()='DATE OF DEATH']")
    public WebElement ccProfDodLabel;

    @FindBy(xpath = "//*[text()='RACE']")
    public WebElement ccProfRaceCodeLabel;

    @FindBy(xpath = "//*[text()='PROFILE ID']")
    public WebElement ccProfileIdLabel;

    @FindBy(xpath = "//*[text()='LANGUAGE']")
    public WebElement ccProfileLangLabel;

    @FindBy(xpath = "//*[text()='PHONE']")
    public WebElement ccProfilePhoneLabel;

    @FindBy(xpath = "//*[text()='PHONE']/following-sibling::h6")
    public WebElement ccProfilePhoneNumber;

    @FindBy(xpath = "//*[text()='EMAIL']")
    public WebElement ccProfileEmailLabel;

    @FindBy(xpath = "//*[text()='ADDRESS']")
    public WebElement ccProfileAddressLabel;

    @FindBy(xpath = "//*[text()='STATE REPORTED DETAILS']")
    public WebElement stateReportedPanel;

    @FindBy(xpath = "//*[text()='MCO REPORTED DETAILS']")
    public WebElement mcoReportedPanel;

    @FindBy(xpath = "//*[text()='CONSUMER REPORTED DETAILS']")
    public WebElement consumerReportedDetailPanel;

    @FindBy(xpath = "//*[@title='Visiblity Icon']")
    public WebElement ccProfSnnUnmask;

    @FindBy(xpath = "//*[@title='Date Visibility Off/On']")
    public WebElement ccProfDobUnmask;

    @FindBy(xpath = "//*[text()='ORDER']")
    public WebElement orderLabel;

    @FindBy(xpath = "//*[text()='TYPE']")
    public WebElement typeLabel;

    @FindBy(xpath = "//*[text()='PHONE NUMBER']")
    public WebElement phoneNumberLabel;

    @FindBy(xpath = "//*[contains(text(), 'person')]//../following-sibling::*")
    public WebElement viewRecordConsumerNameHeader;

    public String getConsumerNameFromHeader(WebElement element) {
        String name = element.getText();
        return name;
    }

    public String getConsumerProfileFieldValue(String filedLabel) {
        WebElement fieldValue = Driver.getDriver().findElement(By.xpath("//*[contains(text(), '" + filedLabel + "')]//../following-sibling::*"));
        String value = fieldValue.getText();
        return value;
    }

    public boolean sourceHasAll(WebElement element) {
        boolean present = false;
        List<String> allSources = Arrays.asList("MMIS", "Newborn File", "Consumer Reported");
        List<String> sources = new ArrayList<>();

        sources.addAll(allSources);
        for (String source : sources) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.click();
            // browserUtils.hover(element);
            waitFor(1);
            WebElement single = Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + source + "')]"));
            browserUtils.scrollToElement(single);
            present = (single.getText().equals(source));
            browserUtils.hover(single);
            single.click();
        }
        return present;
    }

    @FindBy(xpath = "(//i[text()='edit'])[2]")
    public WebElement editAddressButton;

    @FindBy(xpath = "(//i[text()='add'])[2]")
    public WebElement addAddressButton;

    @FindBy(xpath = "(//i[text()='add'])[3]")
    public WebElement addPhoneButton;

    @FindBy(id = "mui-component-select-cellPhoneNumberOrder")
    public WebElement cellPhoneType;

    @FindBy(id = "mui-component-select-homePhoneNumberOrder")
    public WebElement homePhoneType;

    @FindBy(id = "mui-component-select-workPhoneNumberOrder")
    public WebElement workPhoneType;

    @FindBy(id = "cellPhoneNumber")
    public WebElement cellPhoneNumField;

    @FindBy(id = "homePhoneNumber")
    public WebElement homePhoneNumField;

    @FindBy(id = "workPhoneNumber")
    public WebElement workPhoneNumField;

    @FindBy(xpath = "//span[contains(text(),'check')]")
    public WebElement phoneSaveButton;

    @FindBy(id = "mAddressLIne1")
    public WebElement mailingAddressLine1;

    @FindBy(id = "mAddressLIne2")
    public WebElement mailingAddressLine2;

    @FindBy(id = "mAddressCity")
    public WebElement mailingCity;
    @FindBy(xpath = "//div/table/tbody/tr[1]/td[4]/span")
    public WebElement cellPhoneNumValue;

    @FindBy(id = "mui-component-select-mAddressState")
    public WebElement mailingStateDropdown;

    @FindBy(id = "mZipCode")
    public WebElement mailingZipCode;

    @FindBy(xpath = "//span[text()='MAILING ADDRESS']/following-sibling::span")
    public WebElement mailingFullAddressLineOnProfilePage;

    @FindBy(xpath = "//span[text()='PHYSICAL ADDRESS']/following-sibling::span")
    public WebElement physicalFullAddressLineOnProfilePage;

    @FindBy(id = "pAddressLIne1")
    public WebElement physicalAddressLine1;

    @FindBy(id = "pAddressLIne2")
    public WebElement physicalAddressLine2;

    @FindBy(id = "pAddressCity")
    public WebElement physicalCity;

    @FindBy(id = "mui-component-select-pAddressState")
    public WebElement physicalStateDropdown;

    @FindBy(id = "pZipCode")
    public WebElement physicalZipCode;

    @FindBy(xpath = "//*[text()='ADDRESS DETAILS']")
    public WebElement addressDetailsLableOnEditPage;

    @FindBy(xpath = "//button//i[text()='check']")
    public WebElement saveButtonOnAddressPage;

    @FindBy(xpath = "//button//*[text()='Cancel']")
    public WebElement cancelButtonOnAddressPage;

    @FindBy(xpath = "(//button//*[text()='close'])[1]")
    public WebElement deleteMailingAddressButtonOnAddressPage;

    @FindBy(xpath = "(//button//*[text()='close'])[2]")
    public WebElement deletePhysicalAddressButtonOnAddressPage;

    @FindBy(xpath = "//p[contains(text(), 'MAILING ADDRESS')]")
    public WebElement mailingAddressLabel;

    @FindBy(xpath = "//p[contains(text(), 'PHYSICAL ADDRESS')]")
    public WebElement physicalAddressLabel;

    @FindBy(xpath = "//*[contains(text(), 'Consumer successfully updated')]")
    public WebElement consumerSuccessfullyUpdatedMessage;

    @FindBy(xpath = "//div/table/tbody/tr[2]/td[4]/span")
    public WebElement homePhoneNumValue;

    @FindBy(xpath = "//div/table/tbody/tr[3]/td[4]/span")
    public WebElement workPhoneNumValue;

    @FindBy(xpath = "//div/table/tbody/tr/td[2]/p")
    public WebElement cellPhoneNumOrder;

    @FindBy(xpath = "//div/table/tbody/tr/td[2]/p")
    public WebElement homePhoneNumOrder;

    @FindBy(xpath = "//div/table/tbody/tr/td[2]/p")
    public WebElement workPhoneNumOrder;

    @FindBy(xpath = "//*[contains(text(), 'clear')]")
    public List<WebElement> clearButton;

    @FindBy(xpath = "//*[contains(text(), 'edit')]")
    public List<WebElement> editButton;

    @FindBy(xpath = "//*[text()='PHONE NUMBER DETAILS']")
    public WebElement phoneNumDetailPage;

    @FindBy(xpath = "//*[text()='CONSUMER-REPORTED COMMUNICATION']")
    public WebElement consumerRepComPage;

    @FindBy(xpath = "//*[text()='CASES']")
    public WebElement casesTitle;

    @FindBy(xpath = "//*[text()='CASE']")
    public WebElement caseHeader;

    @FindBy(xpath = "//*[text()='CASE ID']")
    public WebElement caseIDHeader;

    @FindBy(xpath = "//*[text()='MEDICAID']")
    public WebElement medicaidType;

    @FindBy(xpath = "//div/div/table/tbody/tr/td[3]/p")
    public WebElement caseIdValue;

    @FindBy(xpath = "//*[text()='CASE ADDRESS']")
    public WebElement caseAddressTxt;

    @FindBy(xpath = "//*[text()='CASE PHONE NUMBER']")
    public WebElement casePhoneNumTxt;

    @FindBy(xpath = "//*[text()='CASE EMAIL']")
    public WebElement caseEmailTxt;

    @FindBy(xpath = "//*[text()='CASE DETAILS']")
    public WebElement caseDetailsTab;

    @FindBy(xpath = "//*[text()='CONTACT DETAILS']")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//*[text()='Task & Service Request']")
    public WebElement taskANDserviceRequestTab;

    @FindBy(xpath = "//*[text()='Program & Benefit Info']")
    public WebElement programANDBenefitTab;

    @FindBy(xpath = "//*[text()='History']")
    public WebElement historyTab;

    @FindBy(xpath = "//*[text()='STATE ID']")
    public WebElement stateIdHeaderLabel;

    @FindBy(xpath = "//*[text()='FULL NAME']")
    public WebElement fullNameHeaderLabel;

    @FindBy(xpath = "//*[text()='DATE OF BIRTH']")
    public WebElement dateOfBirthHeaderLabel;

    @FindBy(xpath = "//*[text()='AGE / GENDER']")
    public WebElement ageGenderHeaderLabel;

    @FindBy(xpath = "//*[text()='AUTHORIZED REP']")
    public WebElement authorizedRepHeaderLabel;

    @FindBy(xpath = "//*[text()='CASE ADDRESS']//following-sibling::h6")
    public WebElement caseAddressValueOnCaseDetailsPage;

    @FindBy(xpath = "//*[text()='CASE PHONE NUMBER']//following-sibling::h6")
    public WebElement casePhoneValueOnCaseDetailsPage;

    @FindBy(xpath = "//*[text()='CASE EMAIL']//following-sibling::h6")
    public WebElement caseEmailValueOnCaseDetailsPage;

    @FindBy(id = "addressLine1")
    public WebElement mailAddressLine1;

    @FindBy(id = "addressLine2")
    public WebElement mailAddressLine2;

    @FindBy(id = "city")
    public WebElement mailCity;

    @FindBy(id = "mui-component-select-state")
    public WebElement mailStateDropdown;

    @FindBy(id = "zipCode")
    public WebElement mailZipCode;

    @FindBy(xpath = "//*[@id='mail']/..")
    public WebElement mailCheckbox;

    @FindBy(xpath = "//*[@id='email']/..")
    public WebElement emailCheckbox;

    @FindBy(xpath = "//*[@id='text']/..")
    public WebElement textCheckbox;

    @FindBy(xpath = "//*[@id='doNotCall']/..")
    public WebElement doNotCallCheckbox;

    @FindBy(xpath = "//div[1]/div[3]/div[3]/span")
    public WebElement mailingCheckBoxAddress;

    @FindBy(xpath = "//div[1]/div[5]/div[3]/span")
    public WebElement emilCheckBoxText;

    @FindBy(xpath = "//div[1]/div[6]/div[3]/span")
    public WebElement phoneNumCheckBoxText;

    @FindBy(xpath = "(//i[text()='edit'])[1]")
    public WebElement editLanguageButton;

    @FindBy(id = "mui-component-select-language")
    public WebElement languageDropDown;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> languageDropDownOptions;

    @FindBy(xpath = "(//i[text()='add'])[1]")
    public WebElement addLanguageButton;

    @FindBy(xpath = "(//i[text()='add'])[1]")
    public WebElement addCommunicationsButton;

    @FindBy(id = "emailText")
    public WebElement emailTextField;

    @FindBy(id = "phoneNumber")
    public WebElement phoneNumTextField;

    @FindBy(xpath = "//*[contains(text(), 'keyboard_backspace')]")
    public WebElement backArrow;

    @FindBy(xpath = "//*[text()='RELATIONSHIPS']")
    public WebElement RELATIONSHIPSTable;

    @FindBy(xpath = "//*[text()='RELATED TO']")
    public WebElement RELATEDtoColumn;

    @FindBy(xpath = "//*[text()='RELATIONSHIP']")
    public WebElement RELATIONSHIPcolumn;

    @FindBy(xpath = "(//p[text()='Child'])[1]")
    public WebElement childNameProfileOnRelationshipsPanel;

    @FindBy(xpath = "(//p[text()='Mother'])[1]")
    public WebElement motherNameProfileOnRelationshipsPanel;

    @FindBy(xpath = "(//*[text()=' Text Phone Number has been STOPPED via text message and cannot be used'])")
    public WebElement stoppedPhoneErrorMessage;

}
