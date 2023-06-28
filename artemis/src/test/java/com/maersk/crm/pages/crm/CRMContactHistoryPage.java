package com.maersk.crm.pages.crm;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRMContactHistoryPage extends BrowserUtils {

    


    public CRMContactHistoryPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][2]")
    public WebElement contactIdColumn;

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][3]")
    public WebElement dateColumn;

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][4]")
    public WebElement consumerNameColumn;

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][5]")
    public WebElement typeColumn;

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][6]")
    public WebElement reasonColumn;

    @FindBy(xpath = "//th[contains(@class ,'mdl-data-table__cell--non-numeric')][7]")
    public WebElement dispositionColumn;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'ml-1')]")
    })
    public List<WebElement> contactIDs;

    @FindAll({
            @FindBy(xpath = "//tbody/tr/td[3]")
    })
    public List<WebElement> dates;
    //refactorng 06/28/2019
    @FindAll({
            @FindBy(xpath = "//tbody/tr/td[4]")
    })
    public List<WebElement> consumers;
    //refactoring 12/03/18
    //refactorng 06/28/2019
    //refactoring by Vinuta 08/12/2020
    @FindAll({
            @FindBy(xpath = "//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mdl-color--grey-50 mb-0']/tbody/tr/td[5]")
    })
    public List<WebElement> types;
    //refactor 12/03/18
    //refactorng 06/28/2019
    @FindAll({
            @FindBy(xpath = "(//p[text()='Contact reason']/following-sibling::p)")
    })
    public List<WebElement> reasons;
    //refactoring 11/30/18
    //refactorng 06/28/2019
    @FindAll({
//            @FindBy(xpath = "//*[contains(@class ,'mx-table table mt-4 mb-0 mdl-color--grey-50')][1]//tr[*]//td[8]")
            @FindBy(xpath = "//tbody/tr/td[7]")
    })
    public List<WebElement> dispositions;

    @FindBy(xpath = "//*[@name='itemsPerPage']/..")
    public WebElement showPerPageDropdown;

    @FindBy(xpath = "//a[contains(text(),'»')]")
    public WebElement lastPaginationItem;

    @FindBy(xpath = "//p[text()='FIRST NAME']/following-sibling::h6")
    public WebElement lblThirdPartyFirstName;

    @FindBy(xpath = "//p[text()='LAST NAME']/following-sibling::h6")
    public WebElement lblThirdPartyLastName;

    @FindBy(xpath = "//p[text()='THIRD PARTY ORGANIZATION']/following-sibling::h6")
    public WebElement lblThirdPartyOrganizationName;

    @FindBy(xpath = "//p[text()='CONSUMER TYPE']/following-sibling::h6")
    public WebElement lblThirdPartyConsumerType;

    @FindBy(xpath = "//p[text()='PREFERRED LANGUAGE']/following-sibling::h6")
    public WebElement lblThirdPartyPreferredLanguage;

    @FindBy(xpath = "//h5[text()='CONTACT DETAILS']/../following-sibling::div[2]//p[text()='CHANNEL']/following-sibling::h6")
    public WebElement contactDetailsContactChannel;

    @FindBy(xpath = "//h5[text()='CONTACT DETAILS']/../following-sibling::div[2]//p[text()='PHONE']/following-sibling::h6")
    public WebElement contactDetailsPhoneNumber;

    @FindBy(xpath = "//h5[text()='CONTACT DETAILS']/../following-sibling::div[2]//p[text()='EMAIL']/following-sibling::h6")
    public WebElement contactDetailsEmail;

    @FindBy(xpath = "//p[text()='CONTACT ID']/../h6")
    public WebElement lblContactId;

    @FindBy(xpath = "//p[text()='USER NAME']/../h6")
    public WebElement lblUserName;

    @FindBy(xpath = "//p[text()='USER ID']/../h6")
    public WebElement lblUserId;

    @FindBy(xpath = "//p[text()='CONTACT START']/../h6")
    public WebElement lblContactStart;

    @FindBy(xpath = "//p[text()='CONTACT END']/../h6")
    public WebElement lblContactEnd;

    @FindBy(xpath = "//tbody/tr/td[1]")  ////h6[text()='CRM Case Id']/a
    public WebElement lnkCaseId;

    @FindBy(id = "mui-component-select-contactReasonEditType")
    public WebElement lstReasonForEdit;

    @FindBy(xpath = "//div[@id='menu-contactReasonEditType']//ul/li")
    public List<WebElement> reasonForEditDropDownValues;

    @FindBy(xpath = "//span[text()='EDIT CONTACT']/..")
    public WebElement btnEdit;

    @FindBy(xpath = "//*[contains(text(), 'Save')]/ancestor::button")
    public WebElement btnSave;

    @FindBy(id = "mui-component-select-contactReason")
    public WebElement lstEditContactReason;

    @FindBy(xpath = "(//span[text()='check'])[2]")
    public WebElement btnSaveEditedContactReason;

    @FindBy(xpath = "(//p[text()='Contact reason']/following-sibling::p)[2]")
    public WebElement lblEditedContactReason;

    @FindBy(xpath = "//p[text()='Contact action']/following-sibling::p/div")
    public List<WebElement> lblEditedContactActions;

    @FindBy(xpath = "//p[text()='Comments']/following-sibling::p[1]")
    public WebElement lblEditedContactReasonComments;

    //Changed the xpath due ico change
    @FindBy(xpath = "(//*[text()='chat'])[2]/ancestor::div[1]/following-sibling::div")
    public WebElement expandreasonsComments;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div[4]/div/div[1]/div[2]/span[1]")
    public WebElement expandPreviousReasonsComments;

    @FindBy(xpath = "//*[contains(@class, 'mx-accord-arrow mt-1')]")
    public WebElement expandSavedReasonsComments;

    @FindBy(id = "mui-component-select-contactAction")
    public WebElement expendcontactActionDropDown;

    @FindBy(id = "comments")
    public WebElement editReasonsComments;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/table/tbody/tr[1]/td[2]")
    public WebElement lblEditedOn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/table/tbody/tr[1]/td[3]")
    public WebElement lblEditedBy;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/table/tbody/tr[1]/td[4]")
    public WebElement lblReasonForEdit;


    @FindBy(xpath = "//p[contains(text(), 'Additional Comments')]")
    public WebElement savedAdditionalComments;

    @FindBy(xpath = "(//h6[contains(text(), 'ADDITIONAL COMMENTS')])[2]/../following-sibling::div")
    public WebElement expendAdditionalCommentsButton;

    @FindBy(xpath = "(//*[@name='comments'])[1]")
    public WebElement commentsTextBox;

    @FindBy(xpath = "(//*[@id=\"comments\"])[2]")
    public WebElement additionalCommentsTextBox;

    //@FindBy(xpath = "(//*[@class='material-icons'])[4]")
    @FindBy(xpath = "(//*[text()='ADDITIONAL COMMENTS']/ancestor::div[@tabindex=0]/following-sibling::div//button[2]/i)[2]")
    public WebElement saveAdditionalComments;

    @FindBy(xpath = "//p[text()='Comments']//following-sibling::p")
    public WebElement readTextAdditionalComments;

    @FindBy(xpath = "(//span[text()='check'])[3]")
    public WebElement saveAditionalCommentsButton;
    // (//h6[text()='ADDITIONAL COMMENTS']//ancestor::div[@tabindex='0']//following-sibling::div//button//i)[2]

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'mx-table table mdl-color--grey-50')][1]//tr[*]//td[*]/i")
    })
    public List<WebElement> inbountOutBoundIcons;

    @FindBy(id = "mui-component-select-contactRecordStatusType")
    public WebElement contactDispositionDropdown;

    @FindBy(xpath = "//p[text()='DISPOSITION']/..//h6")
    public WebElement readOnlyContactDisposition;  //contains(@id,"content-body")


    /**
     * @param iconName
     * @return boolean
     * @Author Muhabbat
     * this method checks if the contact Type & Chanel Icon is displayed on Contact History Page
     */
    public boolean contactTypeChanelIconIsPresent(String iconName) {
        WebElement el = Driver.getDriver().findElement(By.xpath("//i[contains(@class,'" + iconName + "')]"));
        return (el.isDisplayed());
    }

    /**
     * @param iconName
     * @return String
     * @Author Muhabbat
     * this method returns text which appears when hover over the Type & Chanel Icon on Contact History Page
     */
    public String getIconsHoverOverText(String iconName) {
        WebElement el = Driver.getDriver().findElement(By.xpath("//i[contains(@class,'" + iconName + "')]"));
        hover(el);
        return el.getAttribute("title");
    }


    @FindBy(xpath = "//*[contains (text(), 'CONTACT DETAILS')]")
    public WebElement contactDetailsTab;

    @FindBy(xpath = "//*[.='link_off']/../..")
    public WebElement unLink;

    @FindBy(xpath = "//*[contains(text(), 'CASE / CONSUMER SEARCH')]")
    public WebElement searchSectionHeader;

    @FindBy(xpath = "(//i[contains(text(),'search')])[2]/..")
    public WebElement searchButton;

    @FindBy(name = "consumerFirstName")
    public WebElement firstName;

    @FindBy(xpath = "//h6[text()=' SEARCH RESULT(S)']div[1]///ancestor::tr[1]/td[1]")
    public WebElement expandFistConsumer;
    //*[@id="root"]/div/main/div/div/div/div[2]/div[1]/div/div[5]/div/div[2]/div/table/tbody/tr[1]/td[1]/button


    @FindBy(xpath = "//h6[text()=' SEARCH RESULT(S)']/ancestor::div[1]//tr[1]/td[3]")
    public WebElement fistConsumerID;

    @FindBy(xpath = "//a[contains(@class,'mx-section-header float-left ml-2')]")
    public WebElement navigateBackToContactsList;

    /**
     * Author Muhabbat
     *
     * @param
     * @return this method displayed 20 items of saved contact records on Contact History Page
     */
    public void getTwentyItemsPerPage() {
        WebElement el = Driver.getDriver().findElement(By.xpath("//input[@name='itemsPerPage']/.."));
        selectDropDown(el, "Show 20");
    }

    @FindBy(xpath = "//*[contains(text(), 'CANCEL')]")
    public WebElement cancelEditButton;

    @FindBy(xpath = "(//span[contains(text(), 'check')])[4]")
    public WebElement warningMessageContinueButton;

    @FindBy(xpath = "//span[contains(text(), 'clear')]")
    public WebElement warningMessageCancelButton;

    @FindBy(xpath = "//p[text() = 'CONTACT ID']/../h6")
    public WebElement contactIdDetail;

    @FindBy(xpath = "(//h6[@class='m-0'])[2]")
    public WebElement userNameDetail;

    @FindBy(xpath = "//p[text() = 'USER ID']/../h6")
    public WebElement userIdDetail;

    @FindBy(xpath = "//p[text() = 'CONTACT START']/../h6")
    public WebElement contactStartDetail;

    @FindBy(xpath = "//p[text() = 'CONTACT END']/../h6")
    public WebElement contactEndDetail;

    @FindBy(xpath = "//p[text() = 'CONTACT TYPE']/../h6")
    public WebElement contactTypeDetail;

    @FindBy(xpath = "//p[text() = 'OUTCOME OF CONTACT']/../h6")
    public WebElement contactOutcomeDetail;

    @FindBy(xpath = "//p[text() = 'CHANNEL']/../h6")
    public WebElement contactChannelDetail;

    @FindBy(xpath = "//p[text() = 'PHONE']/../h6")
    public WebElement phoneNumberDetail;

    @FindBy(xpath = "//p[text() = 'DISPOSITION']/../h6")
    public WebElement contactDispositionDetail;

    /**
     * Author Muhabbat
     *
     * @param recordDetails
     * @return Hashmap
     * this method expends specific contact record and saves all Saved Contact record details for further comparision
     */
    public HashMap<String, String> getContactDetails(HashMap<String, String> recordDetails) {
        recordDetails.put("contactId", contactIdDetail.getText());
//        recordDetails.put("userNameDetail", userNameDetail.getText());
        recordDetails.put("userIdDetail", userIdDetail.getText());
        recordDetails.put("contactStartDetail", contactStartDetail.getText());
        recordDetails.put("contactEndDetail", contactEndDetail.getText());
        recordDetails.put("contactTypeDetail", contactTypeDetail.getText());
        recordDetails.put("contactOutcomeDetail", contactOutcomeDetail.getText());
        recordDetails.put("contactChannelDetail", contactChannelDetail.getText());
        recordDetails.put("phoneNumberDetail", phoneNumberDetail.getText());
        recordDetails.put("contactDispositionDetail", contactDispositionDetail.getText());
        return recordDetails;
    }

    /**
     * Author Muhabbat
     * Parameter: String Id
     * this method expends specific contact record with unique ID
     */
    public void expendLinkedContactRecord(String id) {
        for (WebElement contactID : contactIDs) {
//            System.out.println(contactID.getText());
//            System.out.println(id);
            if (contactID.getText().substring(0, 8).equalsIgnoreCase(id.substring(0, 8))) {
                contactID.click();
                break;
            }
        }
    }

    @FindBy(xpath = "//*[contains(text(), 'No Records Available')]")
    public WebElement noContactRecordMessage;

    @FindBy(xpath = "//*[contains(text(), 'Please link Case/Consumer record')]")
    public WebElement contactRecordHasToBeLinkedErrorMessage;

    @FindBy(id = "contactDetails")
    public WebElement contactEditDetailsTable;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/th[1]")
    public WebElement editedOnColumn;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/th[2]")
    public WebElement editedByColumn;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/th[3]")
    public WebElement reasonForEditColumn;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/td[1]")
    public WebElement editedOn;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/td[2]")
    public WebElement editedBy;

    @FindBy(xpath = "//*[@id='contactDetails']//tr[*]/td[3]")
    public WebElement reasonForEdit;

    @FindBy(xpath = "//*[@title='Manual']")
    public WebElement manualIcon;

    @FindAll({
            @FindBy(xpath = "(//th[contains(text(),'CONTACT ID')])[1]/parent::tr/parent::thead/parent::table/tbody/tr")
    })
    public List<WebElement> contactID;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div/div[1]/div/div/div[1]/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[1]/td[2]")
    public WebElement contactIcon;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div[1]/div[1]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr[2]/td[2]/a/i")
    public WebElement campManualIcon;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/div/div[1]/div[1]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr[2]/td[1]/i")
    public WebElement contactIconTitle;

    @FindBy(xpath = "//span[text()='EDIT HISTORY']")
    public WebElement editHistoryTab;

    @FindBy(xpath = "//span[text()='CONTACT DETAILS']")
    public WebElement contactDetailsTAB;

    @FindBy(xpath = "//span[contains(text(),'EDIT CONTACT')]")
    public List<WebElement> editButton;

    @FindBy(xpath = "//h5[text()='GENERAL - CONSUMER IN CONTACT']")
    public WebElement generalConsumerInContact;

    @FindBy(xpath = "//h5[text()='GENERAL - CONSUMER IN CONTACT']/../..//following-sibling::div//h5[text()='CONTACT DETAILS']")
    public WebElement contactDetailsHeader;

    @FindBy(xpath = "//h5[text()='GENERAL - CONSUMER IN CONTACT']/../..//following-sibling::div//h5[text()='WRAP-UP AND CLOSE']")
    public WebElement wrapUpAndCloseHeader;

    @FindBy(xpath = "//h5[text()='GENERAL - CONSUMER IN CONTACT']/../..//following-sibling::div//h5[text()='CONTACT EDIT REASONS']")
    public WebElement contactEditReasonHeader;

    @FindBy(xpath = "//h5[text()='GENERAL - CONSUMER IN CONTACT']/../..//following-sibling::div//h1[text()='LINKS']")
    public WebElement linkHeader;

    @FindBy(xpath = "//th[text()='ID']/ancestor::thead/following-sibling::tbody//td[3]")
    public WebElement linkSectionId;

    @FindBy(xpath = "//input[@id='contactReasonEditType']/..")
    public WebElement reasonForEditDropDown;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement saveBtn;


    /* Paramita */

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']")
    public WebElement thirdPartyLabelText;

    @FindBy(xpath = "//*[text()='THIRD PARTY - CONSUMER IN CONTACT']/../../..//following-sibling::div//h5[text()='LINKS']")
    public WebElement thirdPartylinkHeader;

    @FindBy(xpath = "//h5[text()='THIRD PARTY - CONSUMER CONTACTED ABOUT']/..//th[text()='CASE ID']")
    public WebElement thirdPartyCaseID;

    @FindBy(xpath = "//h5[text()='THIRD PARTY - CONSUMER CONTACTED ABOUT']/..//th[text()='CASE ID']/../../..//tbody//span")
    public List<WebElement> thirdPartyValue;

    @FindBy(xpath = "//h5[text()='THIRD PARTY - CONSUMER CONTACTED ABOUT']/..//th[text()='CONSUMER ID']")
    public WebElement thirdPartyConsumerCaseID;

    @FindBy(xpath = "//i[@title='Edit Contact']")
    public WebElement thirdPartyEditContact;

    @FindBy(xpath = "//h5[text()='THIRD PARTY - CONSUMER IN CONTACT']/../..//following-sibling::div//h5[text()='CONTACT DETAILS']")
    public WebElement thirdPartyContactDetails;

    @FindBy(xpath = "//h5[text()='THIRD PARTY - CONSUMER IN CONTACT']/../..//following-sibling::div//h5[text()='WRAP-UP AND CLOSE']")
    public WebElement thirdPartyWrapUpClose;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD INFORMATION']/../../../../following-sibling::div//descendant::h5[text()][not(parent::i/@class='material-icons')]")
    public List<WebElement> thirdPartySection;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD INFORMATION']/../../../../div//h5")
    public List<WebElement> hFiveThirdPartySection;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD INFORMATION']/../../../../div//h1")
    public List<WebElement> hOneThirdPartySection;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    public WebElement editSave;

    @FindBy(xpath = "//button[@role='tab']/..//descendant::span[text()]")
    public List<WebElement> tabMenuName;

    @FindBy(xpath = "//div[@class='col-12 text-right px-0 pt-3']/button[2]")
    public WebElement cancelCommentBtn;

    @FindBy(xpath = "//div[@class='col-12 text-right px-0']/button[2]")
    public WebElement cancelAdditionalCommentBtn;

    @FindBy(xpath = "//h5[text()='EDIT HISTORY']")
    public WebElement editHistoryHeader;

    @FindBy(xpath = "//h5[text()='CONTACT RECORD INFORMATION']")
    public WebElement contactRecordInfoHeader;

    @FindBy(xpath = "//tr/td[3]")
    public WebElement firstContactId;

    @FindBy(id = "sample5")
    public WebElement firstCommentBx;

    @FindBy(id = "sample6")
    public WebElement secondCommentBx;

    @FindBy(xpath = "//span[text()='Continue']")
    public WebElement continueBtnPop;

    @FindAll({
            @FindBy(xpath = "//*[contains(@class ,'MuiTable-root mdl-data-table mdl-js-data-table mx-table table mdl-color--grey-50 mb-0')][1]//tr[*]//td[6]")
    })
    public List<WebElement> reasonsList;


    @FindBy(xpath = "//p[contains(text(),'Materials Request')]")
    public WebElement resAfEdit;

    @FindBy(xpath = "//p[contains(text(),'Updated')]")
    public WebElement commentIr;

    @FindBy(xpath = "//p[contains(text(),'comments')]")
    public WebElement commentRef;

    @FindBy(xpath = "//tbody/tr/td[4]")
    public WebElement reasonForEd;

    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    public WebElement editedO;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    public WebElement editB;

    @FindBy(xpath = "//div[1]/div[1]/div[1]/textarea[1]")
    public WebElement additionalComments;

    @FindBy(xpath = "//div[@class='MuiButtonBase-root MuiIconButton-root MuiExpansionPanelSummary-expandIcon MuiIconButton-edgeEnd']")
    public WebElement additionalIcon;

    @FindBy(xpath = "//tr[1]//td[4]")
    public WebElement reasonForEditInHistory;

    @FindBy(xpath = "//tr[1]//td[2]")
    public WebElement editedOnEditHistory;

    @FindBy(xpath = "//tr[1]//td[3]")
    public WebElement editedByEditHistory;

    @FindBy(xpath = "//tr[1]//td[5]")
    public WebElement fieldLabelEditHistory;

    @FindBy(xpath = "//tr[1]//td[6]")
    public WebElement previousValueEditHistory;

    @FindBy(xpath = "//tr[1]//td[7]")
    public WebElement updatedValueEditHistory;

    @FindBy(xpath = "//tr//td[4]")
    public List<WebElement> listOfeditedOn;

    @FindBy(xpath = "//td[contains(text(),'Last Name')]")
    public WebElement fieldLabelLstName;

    @FindBy(xpath = "//td[contains(text(),'Preferred Language')]")
    public WebElement fieldLabelPrfLan;

    @FindBy(xpath = "//td[contains(text(),'Organization')]")
    public WebElement fieldLabelOrg;

    @FindBy(xpath = "//td[contains(text(),'Consumer Type')]")
    public WebElement fieldLabeConType;

    @FindBy(xpath = "//td[contains(text(),'First Name')]")
    public WebElement fieldLabeFrstName;

    @FindBy(xpath = "//tr//td[2]")
    public List<WebElement> listEditedOn;

    @FindBy(xpath = "//tr[3]//td[4]")
    public WebElement scndReasonForEdit;

    @FindBy(xpath = "//tr[5]//td[6]")
    public WebElement preFrstName;

    @FindBy(xpath = "//tr[5]/td[7]")
    public WebElement upFrstName;

    @FindBy(xpath = "//tr[1]/td[6]")
    public WebElement preConsumerType;

    @FindBy(xpath = "//tr[1]/td[7]")
    public WebElement upConsumerType;

    @FindBy(xpath = "//tr[3]/td[6]")
    public WebElement preLanguage;

    @FindBy(xpath = "//tr[3]/td[7]")
    public WebElement upLanguage;

    @FindBy(xpath = "//tr[7]//td[6]")
    public WebElement preLstName;

    @FindBy(xpath = "//tr[7]/td[7]")
    public WebElement upLstName;

    @FindBy(xpath = "//tr[9]//td[6]")
    public WebElement preOrganization;

    @FindBy(xpath = "//tr[9]/td[7]")
    public WebElement upOrganization;

    @FindBy(xpath = "//p[contains(text(), 'WRAP-UP TIME')]")
    public WebElement wrapUpTime;

    @FindBy(xpath = "//p[contains(text(), 'WRAP-UP TIME')]/parent::div/h6")
    public  WebElement WrapUpTime_Time;

    @FindBy(xpath = "//tbody[1]/tr/td[5]")
    public List<WebElement> fieldLabelList;

    @FindBy(xpath = "//tbody[1]/tr/td[6]")
    public List<WebElement> fieldPreValList;

    @FindBy(xpath = "//tbody[1]/tr/td[7]")
    public List<WebElement> fieldUpdValList;
    @FindBy(xpath = "//span[text()='DISPOSITION']/../../../../tbody/tr/td[7]")
    public List<WebElement> dispositionStatuses;

    @FindBy(xpath = "//td[contains(@class ,'MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric')][7]")
    public WebElement dispositionCol;

    @FindBy( xpath = "//*[contains(@class ,'MuiTable-root mdl-data-table mdl-js-data-table mx-table table mdl-color--grey-50 mb-0')][1]//tr[*]//td[3]")
    public List<WebElement>datesHistory;

    @FindBy( xpath = "//table[@class='MuiTable-root mdl-data-table mdl-js-data-table mx-table table mdl-color--grey-50 mb-0']/tbody/tr/td[4]")
    public List<WebElement>namesHistory;

    @FindBy( xpath = "//*[contains(@class ,'MuiTable-root mdl-data-table mdl-js-data-table mx-table table mdl-color--grey-50 mb-0')][1]//tr[*]//td[6]")
    public List<WebElement>reasonListHistory;

    @FindBy( xpath = "//*[contains(@class ,'ml-1')]")
    public List<WebElement>contactIdsHistory;

    @FindBy(xpath = "//tbody/tr/td[2]")
    public WebElement lnkConsumerId;

    @FindBy(xpath = "//button/span[contains(text(),'Search')]")
    public WebElement searchButtonAfterUnlinking;

    @FindBy(xpath = "(//button[@class ='mdl-button mdl-js-button mdl-button--icon mdl-button--colored mx-grid-accord-arrow'])[1]")
    public WebElement expandFirstConsumerAmongOthers;

    @FindBy(xpath = "//*[@id=\"40079\"]/span[1]/span[1]/input")
    public WebElement ethanConsumerOnCase;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[2]")
    public WebElement editedOnDate;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[2]")
    public WebElement editedByUser;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[4]")
    public WebElement frsReasonForEditColumn;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[5]")
    public WebElement frsFieldLabel;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[6]")
    public WebElement frsPreviousVal;

    @FindBy(xpath = "//table[1]/tbody[1]/tr[1]/td[7]")
    public WebElement frsUpdatedVal;

    @FindBy(xpath = "//p[contains(text(),'APPLICATION ID is Required')]")
    public WebElement applicationIdError;

    @FindBy(xpath = "//tbody/tr/td[4]")
    public List<WebElement> gethisReasonsList;

    @FindBy(xpath = "//td[contains(text(),'Correcting Call Summary')]")
    public WebElement callSummaryReasonEditHistory;

    @FindBy(xpath = "//p[contains(text(),'PHONE')]/parent::div/h6")
    public WebElement etlPhoneNumberFromFile;

    @FindBy(xpath = "//span[text() = 'Continue']/..")
    public WebElement continueButtonOnWarningPopUp;

    @FindBy(xpath = "//span[contains(text(),'keyboard_backspace')]")
    public WebElement backButton;

    @FindBy(xpath = "//h5[contains(text(),'CONTACT HISTORY')]/following::tbody/tr[1]/td")
    public List<WebElement> contactHistoryRowOne;

    @FindBy(xpath = "//h5[contains(text(),'CONTACT HISTORY')]/following::tbody/tr[2]/td")
    public List<WebElement> contactHistoryRowTwo;

    @FindBy(xpath = "//p[.='CHANNEL']/following-sibling::h6")
    public WebElement channelValue;

    @FindBy(xpath = "//p[.='PHONE']/following-sibling::h6")
    public WebElement phoneValue;

    @FindBy(xpath = "//p[.='EMAIL']/following-sibling::h6")
    public WebElement emailValue;

    @FindBy(xpath = "//p[.='PROGRAM']/following-sibling::h6")
    public WebElement programValues;

    @FindBy(xpath = "//p[.='TRANSLATION SERVICE']/following-sibling::h6")
    public WebElement translationServiceValues;

    @FindBy(xpath = "//p[.='EMAIL']/following-sibling::h6")
    public WebElement emailContactValues;

    @FindBy(xpath = "//tr/td[2]")
    public WebElement firstContactIdOnContactDetailsPage;
}


