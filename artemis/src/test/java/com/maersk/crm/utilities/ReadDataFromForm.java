package com.maersk.crm.utilities;

import com.maersk.crm.pages.crm.CRMConsumerSearchResultPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.World.getWorld;
import static org.testng.AssertJUnit.assertEquals;

public class ReadDataFromForm extends BrowserUtils {
    public void recordConsumerID(CRMContactRecordUIPage page) {
        getWorld().contactBean.get().setConsumerID(page.consumerID.getText());
    }

    public void selectPreviouslyCreatedConsumerFromConsumerSearch(CRMConsumerSearchResultPage page) {
//        page.getConsumberByFirstName(getWorld().contactBean.get()getFirstName()).click();
        staticWait(500);
        page.expandArrow.click();
        staticWait(500);
        page.consumerNameRadioButton.click();// todo remove this line after CRM-2314 bug is fixed
        waitFor(1);
        page.unableToAuthenticateCheckBox.click();
        staticWait(500);
//        new Actions(Driver.getDriver()).click(page.link).build().perform();
        BrowserUtils.scrollRight();
        staticWait(800);
        page.link.click();
    }

    public void selectPreviouslyContactRecord(CRMContactRecordUIPage page) {
        getWorld().contactRecordBean.get().setContactID(page.contactRecordFromContactDetails.getText().trim());
        staticWait(1000);
        page.getElementContainingTextOAllNodesInNodeSet(getWorld().contactRecordBean.get().getReasonsList().get(0).getContactReason()).click();
    }

    public void assertInboundContactRecordDetails(CRMContactRecordUIPage page){
        waitForVisibility(page.getElementContainingTextOAllNodesInNodeSet("CONTACT RECORD"),3);
        assertEquals(page.getContactRecordText("Contact Id").getText(),getWorld().contactRecordBean.get().getContactID());
        staticWait(500);
        assertEquals(filterTextFor(page.getContactRecordText("Full Name").getText()),getWorld().contactBean.get().getFirstName()+" "+getWorld().contactBean.get().getMiddleInitial()+" "+getWorld().contactBean.get().getLastName());
        page.getVisiblityOffButton("Social Security Number").click();
        staticWait(500);
        assertEquals(BrowserUtils.filterForValidSSN(page.getContactRecordText("Social Security Number").getText()),getWorld().contactBean.get().getSsnWithDashes());
        page.getVisiblityOffButton("Date Of Birth").click();
        staticWait(500);
        assertEquals(BrowserUtils.filterForValidDob(page.getContactRecordText("Date Of Birth").getText()),getWorld().contactBean.get().getDateOfBirthWithSlashes());
        staticWait(500);
        assertEquals(filterTextFor(page.getContactRecordText("Preferred Language").getText()), getWorld().contactBean.get().getPreferredLanguage());
        staticWait(500);
        assertEquals(filterTextFor(page.inOrOutBoundContactType.getText()), getWorld().contactRecordBean.get().getContactType());
    }

    public void assertOutboundContactRecordDetails(CRMContactRecordUIPage page){
        waitForVisibility(page.getElementContainingTextOAllNodesInNodeSet("CONTACT RECORD"),3);
        assertEquals(validNumberFilter(page.getContactRecordText("Contact Id").getText()),validNumberFilter(getWorld().contactRecordBean.get().getContactID()));
        staticWait(500);
        assertEquals(filterTextFor(page.getContactRecordText("Full Name").getText()),getWorld().contactBean.get().getFirstName()+" "+getWorld().contactBean.get().getMiddleInitial()+" "+getWorld().contactBean.get().getLastName());
        page.getVisiblityOffButton("Social Security Number").click();
        staticWait(500);
        assertEquals(BrowserUtils.filterForValidSSN(page.getContactRecordText("Social Security Number").getText()),getWorld().contactBean.get().getSsnWithDashes());
        page.getVisiblityOffButton("Date Of Birth").click();
        staticWait(500);
        assertEquals(BrowserUtils.filterForValidDob(page.getContactRecordText("Date Of Birth").getText()),getWorld().contactBean.get().getDateOfBirthWithSlashes());
        staticWait(500);
        assertEquals(filterTextFor(page.getContactRecordText("Preferred Language").getText()), getWorld().contactBean.get().getPreferredLanguage());
        staticWait(500);
        assertEquals(filterTextFor(page.inOrOutBoundContactType.getText()), getWorld().contactRecordBean.get().getContactType());
        staticWait(500);
        assertEquals(filterTextFor(page.campaignRefernce.getText()), getWorld().contactRecordBean.get().getContactCampaignType());

    }

    public void assertContactReasonsDetails(CRMContactRecordUIPage page){
        assertEquals(page.getElementContainingText(getWorld().contactRecordBean.get().getReasonsList().get(0).getContactReason()).getText().trim(), getWorld().contactRecordBean.get().getReasonsList().get(0).getContactReason());
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        page.getContactReasonExpandElement(1).click();
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        assertEquals(page.getContactReasonActionElementContainingText(1).getAttribute("innerHTML").trim(), getWorld().contactRecordBean.get().getReasonsList().get(0).getContactAction());
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        assertEquals(page.getElementContainingText(getWorld().contactRecordBean.get().getReasonsList().get(1).getContactReason()).getText().trim(), getWorld().contactRecordBean.get().getReasonsList().get(1).getContactReason());
        if (!((getWorld().contactRecordBean.get().getReasonsList().size()/3)>1)){
            return;
        }
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        page.getContactReasonExpandElement(2).click();
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        assertEquals(page.getContactReasonActionElementContainingText(2).getAttribute("innerHTML").trim(), getWorld().contactRecordBean.get().getReasonsList().get(1).getContactAction());
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        waitForVisibility(page.consumerID,3);
        assertEquals(page.getElementContainingText(getWorld().contactRecordBean.get().getReasonsList().get(2).getContactReason()).getText().trim(), getWorld().contactRecordBean.get().getReasonsList().get(2).getContactReason());
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        page.getContactReasonExpandElement(3).click();
        waitForClickablility(page.getContactReasonExpandElement(1),10);
        assertEquals(page.getContactReasonActionElementContainingText(3).getAttribute("innerHTML").trim(), getWorld().contactRecordBean.get().getReasonsList().get(2).getContactAction());
    }
}
