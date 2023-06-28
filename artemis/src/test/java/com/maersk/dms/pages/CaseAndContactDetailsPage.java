package com.maersk.dms.pages;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.maersk.crm.utilities.BrowserUtils.*;

public class CaseAndContactDetailsPage {

    
    BrowserUtils browserUtils = new BrowserUtils();

    public CaseAndContactDetailsPage() {
        
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//a[text()='Task & Service Request']")
    public WebElement taskAndServiceRequestLink;

    @FindBy(xpath = "//p[text()='CREATED ON']/parent::div/parent::div/parent::div/parent::div")
    public WebElement detailsOfTheCorrespondence;

    @FindBy(xpath = "//h5[text()='INBOUND CORRESPONDENCES']")
    public WebElement inboundCorresHeader;

    @FindBy(xpath = "//h5[text()='OUTBOUND CORRESPONDENCES']")
    public WebElement outboundCorresHeader;

    @FindBy(xpath = "//td/button/i[@name]")
    public WebElement correspondenceDetailsBTN;

    @FindBy(xpath = "//td/div/button/i[text()='file_copy']")
    public WebElement viewIconOC;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/parent::div/following::div/div[7]/p/button/i")
    public WebElement viewIconOCDetails;

    @FindBy(xpath = "//td/button/i[@name]")
    public List<WebElement> correspondenceDetailsBTNs;

    @FindBy(xpath = "//th[text()='CID']/parent::tr/parent::thead/following::tbody/tr/td[2]")
    public WebElement correspondenceID;

    @FindBy(xpath = "//th[text()='CID']/parent::tr/parent::thead/following::tbody/tr/td[2]")
    public List<WebElement> correspondenceIDs;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])[1]")
    public WebElement caseCorrespondenceLink;

    @FindBy(xpath = "//tr//td[contains(.,'Case')]/preceding-sibling::td[1]")
    public WebElement caseIdCorrespondenceLink;

    @FindBy(xpath = "(//td[@class='MuiTableCell-root MuiTableCell-body mdl-data-table__cell--non-numeric mx-link'])[3]")
    public WebElement consumerCorrespondenceLink;

    @FindBy(xpath = "//th[text()='CID']/parent::tr/parent::thead/following::tbody/tr/td[3]")
    ////p[text()='CHANNEL']/parent::div/parent::div/following::div/div/p
    public WebElement correspondenceType;

    @FindBy(xpath = "//p[text()='STATUS']/parent::div/parent::div/following-sibling::div/div[4]/p")
    public WebElement detailsOfTheCorrespondenceStatus;

    @FindBy(xpath = "//p[text()='STATUS DATE']/parent::div/parent::div/following::div/div[5]/p")
    public WebElement detailsOfTheCorrespondenceStatusDate;

    @FindBy(xpath = "//th[text()='RECIPIENT']/parent::tr/parent::thead/following::tbody/tr/td[5]/a/i")
    public WebElement outboundCorresRecipient;

    @FindBy(xpath = "//th[text()='RECIPIENT']/parent::tr/parent::thead/following::tbody/tr/td[5]/a/i/parent::a/parent::td/parent::tr/td")
    public WebElement outboundCorresRecipientCarat;

    @FindBy(xpath = "//th[text()='CHANNEL']/parent::tr/parent::thead/following::tbody/tr/td[5]/a/i/parent::a/parent::td/parent::tr/td")
    public WebElement outboundCorresChannelCarat;

    @FindBy(xpath = "//p[text()='RECIPIENT']/parent::div/div/following::p")
    public List<WebElement> outboundCorresRecipientElipsis;

    @FindBy(xpath = "//p[text()='RECIPIENT(S)']/parent::div/parent::div/following-sibling::div[2]/child::div[4]/p")
    public List<WebElement> outboundCorresDetailsRecipient;

    @FindBy(xpath = "//th[text()='LANGUAGE']/parent::tr/parent::thead/following::tbody/tr/td[4]")
    public WebElement outboundCorresLanguage;

    @FindBy(xpath = "//p[text()='LANGUAGE']/parent::div/parent::div/following::div/div[3]/p")
    public List<WebElement> outboundCorresDetailsLanguage;

    @FindBy(xpath = "//p[text()='RECIPIENT']/parent::div")
    public WebElement outboundCorresLanguageElipsis;

    @FindBy(xpath = "//th[text()='CHANNEL']/parent::tr/parent::thead/following::tbody/tr/td[6]/a/i")
    public WebElement outboundCorresChannel;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/div/following::p")
    public List<WebElement> outboundCorresChannelElipsis;

    @FindBy(xpath = "//p[text()='CHANNEL']/parent::div/parent::div/following-sibling::div/child::div[1]/p")
    public List<WebElement> outboundCorresDetailsChannel;

    @FindBy(xpath = "//input[@id='crmCaseID']")
    public WebElement consumerInternalCaseId;

    @FindBy(xpath = "//p[text()='CREATED ON']/parent::div/parent::div/following::div[2]/div[1]/p")
    public List<WebElement> outboundCorresDetailsDate;

    @FindBy(xpath = "//th[text()='CASE ID']/parent::tr/parent::thead/following::tbody/tr/td[7]")
    public WebElement activeContactPreferredLanguage;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[1]/div[3]/div/p)[2]")
    public WebElement outboundCorresCreatedByInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[1]/div[3]/div/p)[1]")
    public WebElement outboundCorresDetailsCreatedOnInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[1]/div[3]/div/p)[3]")
    public WebElement outboundCorresDetailStatusReasonInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[1]")
    public WebElement outboundCorresDetailsChannelDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[2]")
    public WebElement outboundCorresDetailsDestinationDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[3]")
    public WebElement outboundCorresDetailsLanguageDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[4]")
    public WebElement outboundCorresDetailsStatusDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[5]")
    public WebElement outboundCorresDetailsStatusDateDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[2]/div/p)[6]")
    public WebElement outboundCorresDetailsNIDDisplayed;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[1]")
    public WebElement outboundCorresDetailsChannelInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[2]")
    public WebElement outboundCorresDetailsDestinationInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[3]")
    public WebElement outboundCorresDetailsLanguageInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[4]")
    public WebElement outboundCorresDetailsStatusInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[5]")
    public WebElement outboundCorresDetailsStatusDateInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[6]")
    public WebElement outboundCorresDetailsNIDInfo;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div[2]/div[3]/div/p)[7]")
    public WebElement outboundCorresDetailsViewIcon;

    @FindBy(xpath = "(//div[@class='collapse show']/div/div/div/div[3])[1]/div[4]")
    public WebElement outboundCorresDetailsRecipientInfo;

    @FindBy(xpath = "//h5[text()='OUTBOUND CORRESPONDENCES']/following::div/ul/li[@class='active']/a")
    public WebElement activePageNum;

    @FindBy(xpath = "//td[text()='Mail']/parent::tr/td[2]")
    public List<WebElement> listMailCIDButtons;
    @FindBy(xpath = "//h5[contains(text(),'OUTBOUND')]/following::li/a/span[contains(text(),'last')]")
    public WebElement outboundCorrLastPageButton;

    @FindBy(xpath = "//div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[2]")
    public WebElement CIDoutBound;

    @FindBy(xpath = "//div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[5]")
    public WebElement OutboundCorresRecipient;

    @FindBy(xpath="//*[text()[contains(.,'OUTBOUND CORRESPONDENCES')]]/../following-sibling::div//table/tbody/tr/td[8]")
    public List<WebElement> statusDateColumn;

    public boolean verifyDetailsOfTheCorrespondence() {
        return verifyElementIsDisplayed(detailsOfTheCorrespondence);
    }

    public void navigateToTaskAndServiceRequestLink() {
        browserUtils.hover(taskAndServiceRequestLink);
        taskAndServiceRequestLink.click();
        BrowserUtils.waitFor(2);
    }

    public void clickCorrespondenceDetailsBTN() {
        browserUtils.hover(inboundCorresHeader);
        correspondenceDetailsBTN.click();
    }

    public boolean viewIconCorrespondenceIsDisplayed() {
       browserUtils.waitForVisibility(viewIconOCDetails, 5);
       browserUtils.waitForVisibility(viewIconOC, 5);
        if (viewIconOCDetails.isDisplayed() && viewIconOC.isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean activeContactPreferredLanguageIsDisplayed() {
       browserUtils.waitForVisibility(activeContactPreferredLanguage, 5);
        return verifyElementIsDisplayed(activeContactPreferredLanguage);
    }

    public boolean outboundCorrespondenceDisplayed() {
       browserUtils.waitForVisibility(outboundCorresChannel, 5);
        browserUtils.hover(inboundCorresHeader);
        return verifyElementIsDisplayed(outboundCorresHeader);
    }

    public boolean correspondenceID() {
        return verifyElementIsDisplayed(correspondenceID);
    }

    public boolean correspondenceType() {
        return verifyElementIsDisplayed(correspondenceType);
    }

    public boolean detailsOfTheCorrespondenceStatusDisplayed() {
        return verifyElementIsDisplayed(detailsOfTheCorrespondenceStatus);
    }

    public String detailsOfTheCorrespondenceStatusDateTXT() {
       browserUtils.waitForVisibility(detailsOfTheCorrespondenceStatusDate, 5);
        if (detailsOfTheCorrespondenceStatusDate.isDisplayed()) {
            return detailsOfTheCorrespondenceStatusDate.getText();
        } else return String.valueOf(false);
    }

    public void hoverOCRecipientElipsis() {
        browserUtils.hover(inboundCorresHeader);
        browserUtils.hover(outboundCorresRecipient);
    }

    public void clickOutboundCorresRecipientCarat() {
       browserUtils.waitForVisibility(outboundCorresRecipientCarat, 5);
        outboundCorresRecipientCarat.click();
    }

    public void clickOutboundCorresChannelCarat() {
       browserUtils.waitForVisibility(outboundCorresChannelCarat, 5);
        outboundCorresChannelCarat.click();
    }

    public boolean verifyOCRecipientToRecipientDetails() {
        List<String> oCR = listElementsToString(outboundCorresRecipientElipsis);
        List<String> oCDR = listElementsToString(outboundCorresDetailsRecipient);
        LinkedHashSet<String> oCDRHashSet = new LinkedHashSet<>(oCDR);
        ArrayList<String> oCDRSorted = new ArrayList<>(oCDRHashSet);
        boolean bool = false;
        for (int i = 0; i <= oCR.size() - 1; i++) {
            if (oCR.get(i).compareTo(oCDRSorted.get(i)) == 0) {
                bool = true;
            } else {
                bool = false;
            }
        }
        return bool;
    }

    public boolean verifyOCLanguageFirstIsTheSame() {
        browserUtils.hover(inboundCorresHeader);
        String pL = outboundCorresLangText();
        String dPL = outboundCorresDetailsLangText();
        if (pL.compareTo(dPL) == 0) {
            return true;
        } else return false;
    }

    public String outboundCorresLangText() {
       browserUtils.waitForVisibility(outboundCorresLanguage, 5);
        return elementToString(outboundCorresLanguage);
    }

    public String outboundCorresDetailsLangText() {
       browserUtils.waitForVisibility(outboundCorresDetailsLanguage.get(0), 5);
        return outboundCorresDetailsLanguage.get(0).getText();
    }

    public void hoverOCChannelElipsis() {
        browserUtils.hover(inboundCorresHeader);
        browserUtils.hover(outboundCorresChannel);
    }

    public boolean verifyElipsisChannelColumn(String channels) {
        List<String> oCC = listElementsToString(outboundCorresChannelElipsis);
        List<String> expected = Arrays.asList(channels.split(","));
//        List<String> oCDC = listElementsToString(outboundCorresDetailsChannel);
//        LinkedHashSet<String> oCDCHashSet = new LinkedHashSet<>(oCDC);
//        ArrayList<String> oCDCSorted = new ArrayList<>(oCDCHashSet);
//        boolean bool = false;

//        for (int i = 0; i <= oCC.size() - 1; i++) {
//            if (oCC.get(i).compareTo(oCDCSorted.get(i)) == 0) {
//                bool = true;
//            } else {
//                bool = false;
//            }
//        }

        return oCC.containsAll(expected);
    }

    public String consumerInternalCaseId(String caseId) {
       browserUtils.waitForVisibility(consumerInternalCaseId, 5);
        browserUtils.fillTheFiled(consumerInternalCaseId, caseId);
        return consumerInternalCaseId.getAttribute("value");
    }

    public boolean VerifyDateSort() throws ParseException {
        List<String> stringDates = outboundCorresSortDateString(outboundCorresDetailsDate);
        List<Date> actualDate = new ArrayList<>();
        System.out.println(stringDates);
        boolean bool = false;
        for (int i = 0; i <= stringDates.size() - 1; i++) {
            if (stringDates.get(i) != null && stringDates.get(i).compareTo("") != 0) {
                actualDate.add(new SimpleDateFormat("dd/MM/yyyy HH:mm a").parse(stringDates.get(i)));
            } else {
                continue;
            }
        }
        for (int i = 0; i <= stringDates.size() - 1; i++) {
            int y = 1;
            if (y == stringDates.size()) {
                break;
            }
            if (actualDate.get(i).before(actualDate.get(y))) {
                bool = true;
                y++;
            } else {
                break;
            }
        }
        return bool;
    }

    public List<String> outboundCorresSortDateString(List<WebElement> elements) {
        browserUtils.hover(inboundCorresHeader);
        List<String> string = new ArrayList<>();
        for (int i = 0; i <= correspondenceDetailsBTNs.size() - 1; i++) {
           browserUtils.waitForVisibility(correspondenceDetailsBTNs.get(i), 5);
            correspondenceDetailsBTNs.get(i).click();
           browserUtils.waitForVisibility(elements.get(i), 5);
            string.add(elements.get(i).getText());
        }
        return string;
    }

    public boolean verifyElementIsDisplayed(WebElement element) {
        try {
           browserUtils.waitForVisibility(element, 5);
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String elementToString(WebElement e) {
       browserUtils.waitForVisibility(e, 5);
        return e.getText();
    }

    public List<String> listElementsToString(List<WebElement> el) {
        List<String> listTXT = new ArrayList<>();
        for (WebElement e : el) {
            if (e.isDisplayed())
                listTXT.add(e.getText());
        }
        return listTXT;
    }

    public WebElement getOutboundCorrPageNum(String pageNum) {
        return Driver.getDriver().findElement(By.xpath("//*[text()[contains(.,'OUTBOUND CORRESPONDENCES')]]/following::ul[1]/li/a[contains(text(),'" + pageNum + "')]"));
    }

    public WebElement getOutboundCorrCID(String CID) {
        return Driver.getDriver().findElement(By.xpath("//h5[contains(text(),'OUTBOUND')]/following::td[contains(text(),'" + CID + "')]"));
    }

    public WebElement getOutboundCorrCaratByCid(String cid) {
        return Driver.getDriver().findElement(By.xpath("//h5[contains(text(),'OUTBOUND')]/following::i[@name='" + cid + "']/.."));
    }

    public String getCorrespondenceType(String corrId){
        String corrType = "";
        try {
            WebElement corrTypeEl = Driver.getDriver().findElement(By.xpath("//th[text()='CID']/parent::tr/parent::thead/following::tbody/tr[1]/td[2][contains(.,'" + corrId + "')]/following-sibling::td[1]"));
            if (corrTypeEl.isDisplayed()) {
                corrType = corrTypeEl.getText().trim();
            }
        }catch (Exception e){
            Assert.fail("failed at getCorrespondenceType" + e.getMessage());
        }
        return corrType;
    }
}