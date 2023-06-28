package com.maersk.dms.pages;

import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OutboundCorrespondenceSearchPage {
    

    public OutboundCorrespondenceSearchPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[contains(text(),'search')]")
    public WebElement outboundSearchButton;

    @FindBy(xpath = "//i[contains(text(),'chevron_right')]")
    public WebElement carrotIcon;

    @FindBy(xpath = "//p[contains(text(),'CREATED ON')]")
    public WebElement createdOnLabel;

    @FindBy(xpath = "//p[contains(text(),'CREATED BY')]")
    public WebElement createdByLabel;

    @FindBy(xpath = "//p[contains(text(),'STATUS REASON')]")
    public WebElement statusReasonLabel;

    @FindBy(xpath = "//p[contains(text(),'RECIPIENT(S)')]")
    public WebElement recipientLabel;

    @FindBy(xpath = "//*[@id=\"root\"]//div[1]/div[3]/div[1]/p")
    public WebElement createdonValue;

    @FindBy(xpath = "//*[@id=\"root\"]//div[1]/div[3]/div[2]/p")
    public WebElement createdByValue;

    @FindBy(xpath = "//*[@id=\"root\"]//div[1]/div[3]/div[3]/p")
    public WebElement statusReasonValue;

    @FindBy(xpath = "//*[@id=\"root\"]//div[1]/div[3]/div[4]/p")
    public WebElement recipientValue;

    @FindBy(xpath = "//p[contains(text(),'CHANNEL')]")
    public WebElement detailChannelLabel;

    @FindBy(xpath = "//p[contains(text(),'DESTINATION')]")
    public WebElement detailDestinationLabel;

    @FindBy(xpath = "//p[contains(text(),'LANGUAGE')]")
    public WebElement detailLanguageLabel;

    @FindBy(xpath = "//p[contains(text(),'STATUS')]")
    public WebElement detailStatusLabel;

    @FindBy(xpath = "//p[contains(text(),'STATUS DATE')]")
    public WebElement detailStatusDateLabel;

    @FindBy(xpath = "//p[contains(text(),'NID')]")
    public WebElement detailNIDLabel;

    @FindBy(xpath = "//p[contains(text(),'Mail')]")
    public WebElement detailMailChannel;

    @FindBy(xpath = "//p[contains(text(),'Email')]")
    public WebElement detailEmailChannel;

    @FindBy(xpath = "//p[contains(text(),'Text')]")
    public WebElement detailTextCHannel;

    @FindBy(xpath = "//p[contains(text(),'Fax')]")
    public WebElement detailFaxChannel;

    @FindBy(xpath = "//p[contains(text(),'12 address st,Canaan,NY,12029')]")
    public WebElement mailDestinationValue;

    @FindBy(xpath = "//p[contains(text(),'English')]")
    public WebElement mailLanguageValue;

    @FindBy(xpath = "(//p[.='Mail']/../../div)[4]/p")
    public WebElement detailMailStaValue;

    @FindBy(xpath = "(//p[.='Email']/../../div)[4]/p")
    public WebElement detailEmailStaValue;

    @FindBy(xpath = "(//p[.='Text']/../../div)[4]/p")
    public WebElement detailTextStaValue;

    @FindBy(xpath = "(//p[.='Fax']/../../div)[4]/p")
    public WebElement detailFaxStaValue;

    @FindBy(xpath = "//p[.='08/27/2020']")
    public List<WebElement> mailStatusDateValue;

    @FindBy(xpath = "//p[contains(text(),'1434')]")
    public WebElement mailNIDValue;

    @FindBy(xpath = "//p[contains(text(),'kljasdf@liausdf.com')]")
    public WebElement emailDestValue;

    @FindBy(xpath = "//p[contains(text(),'3232959484')]")
    public WebElement textPhoneValue;

    @FindBy(xpath = "//p[contains(text(),'5552959484')]")
    public WebElement faxPhoneValue;

    @FindBy(xpath = "//p[contains(text(),'Created')]")
    public WebElement emailStatusValue;

    @FindBy(xpath = "//p[contains(text(),'Sent')]")
    public WebElement textStatusValue;

    @FindBy(xpath = "//p[contains(text(),'Error')]")
    public WebElement faxStatusValue;

    @FindBy(xpath = "//p[contains(text(),'Mail')]/../following-sibling::div[6]//i[.='file_copy']")
    public WebElement mailHyperLink;

    @FindBy(id = "status tooltip")
    public WebElement errorTooltip;

    @FindBy(xpath = "(//div[@role='tooltip']/div/div/p)[1]")
    public WebElement hoverErrorMessage;

    @FindBy(xpath = "//p[.=\"08/27/2020 04:51 PM\"]")
    public WebElement createdOnTimeValue;

    @FindBy(xpath = "//p[.=\"2492\"]")
    public WebElement createdbyUserValue;

    @FindBy(xpath = "((//p[.='STATUS REASON']/../../../div)[3]/div)[3]")
    public WebElement detailStatusReasonValue;

    @FindBy(xpath = "//p[.=\"Arthur  Curry\"]")
    public WebElement detailRecipientValue;

    @FindBy(xpath = "//*[contains(text(),'keyboard_backspace')]")
    public WebElement navigateBackwards;

    @FindBy(xpath = "//*[contains(text(),'search')]/following::table/tbody/tr/td[2]")
    public WebElement firstCIdFromSearch;

    @FindBy(xpath = "//*[contains(text(),'search')]/following::table/tbody/tr/td[2]")
    public List<WebElement> cidColumnOutbSearch;

    @FindBy(xpath = "//h6[contains(text(),'CID')]/a")
    public WebElement cidOutbDetails;

    @FindBy(xpath = "(//*[@id='outbnd-recepnt'])[1]")
    public WebElement channelEllipsis;

    @FindBy(xpath = "(//*[@id='outbnd-recepnt'])[2]")
    public WebElement recipientEllipsis;

    @FindBy(xpath = "//*[contains(@class,'MuiTooltip-tooltip')]")
    public WebElement channelToolTip;

    /**
     * search criteria with ids
     */
    public WebElement correspondenceId;
    public WebElement caseId;
    public WebElement notificationId;
    public WebElement regardingConsumerId;
    public WebElement recipientConsumerId;
    public WebElement requestDate;
    public WebElement sentDate;
    //end of id section

    // global search part2 @keerthi
    @FindBy(xpath = "//label[@id='correspondenceId-label']")
    public WebElement correspondenceIdfieldlabel;

    @FindBy(xpath = "//label[@id='notificationId-label']")
    public WebElement notificationIdfieldlabel;

    @FindBy(xpath = "//label[@id='caseId-label']")
    public WebElement caseIdfieldlabel;

    @FindBy(xpath = "//label[@id='regardingConsumerId-label']")
    public WebElement regardingconsumerIdfieldlabel;

    @FindBy(xpath = "//label[@id='recipientConsumerId-label']")
    public WebElement recipientconsumerIdfieldlabel;

    @FindBy(xpath = "//label[@id='requestDate-label']")
    public WebElement requestdatefieldlabel;

    @FindBy(xpath = "//input[@id='requestDate']")
    public WebElement requestdate;

    @FindBy(xpath = "//label[@id='sentDate-label']")
    public WebElement sentdatefieldlabel;

    @FindBy(xpath = "//input[@id='sentDate']")
    public WebElement sentdate;

    @FindBy(xpath = "(//*[@id='mui-component-select-condtionType'])[1]")
    public WebElement requestdatedefaultoperator;

    @FindBy(xpath = "(//*[@id='mui-component-select-condtionType'])[2]")
    public WebElement sentdatedefaultoperator;

    @FindBy(xpath = "//span[contains(text(),'close')]")
    public WebElement cancel;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> DropDownOptions;

    @FindBy(xpath = "//div[@id='mui-component-select-channel']")
    public WebElement channeldrpdown;

    @FindBy(xpath = "//div[@id='mui-component-select-language']")
    public WebElement languagedrpdown;

    @FindBy(xpath = "//div[@id='mui-component-select-status']")
    public WebElement correspondenceStatusdrpdown;

    @FindBy(xpath = " //tbody/tr[@class='MuiTableRow-root']")
    public List<WebElement> rowcount;

    @FindBy(xpath = "//div[@id='mui-component-select-outboundDynamicGrid']")
    public WebElement paginationdrpdown;

    @FindBy(xpath = "//span[contains(text(),'arrow_forward')]")
    public WebElement arrow_forward;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[2]")
    public List<WebElement> cidColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[3]")
    public List<WebElement> typeColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[4]")
    public List<WebElement> requestedDateColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[5]")
    public List<WebElement> channelColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[6]")
    public List<WebElement> languageColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[7]")
    public List<WebElement> statusColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[8]")
    public List<WebElement> recipientColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[9]")
    public List<WebElement> caseIDColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/tbody/tr/td[10]")
    public List<WebElement> RegardingconsumerIDColumn;

    @FindBy(xpath = "//h5[contains(text(),'SEARCH RESULT')]/following::table/thead/tr/th")
    public List<WebElement> resultcolumnNames;

    @FindBy(xpath = "(//div[contains(@class,'row mx-comment-border py-2')]/div[6]/p)[1]")
    public List<WebElement> nidColumn;

    @FindBy(xpath = "(//div[contains(@class,'row mx-comment-border py-2')]/div[5]/p)[1]")
    public List<WebElement> statusdateColumn;

    @FindBy(xpath = "//label[@id='outboundDynamicGrid']/..//ul/li/a")
    public WebElement activePage;

    @FindBy(id = "mui-component-select-notificationStatus")
    public WebElement notificationStatusDropdown;

    @FindBy(xpath = "//i[@title='File Copy']")
    public WebElement viewIcon;

    @FindBy(id = "mui-component-select-statusReason")
    public WebElement obglobalsearchcorrstatusreasondropdown;

    @FindBy(id = "mui-component-select-notificationStatusReason")
    public WebElement obglobalsearchnotificationstatusreasondropdown;

    public WebElement getWebElementContaining(String text) {
        return Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
    }
}
