package com.maersk.dms.step_definitions;

import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionChannelPage;
import com.maersk.dms.pages.TenantManagerOutboundCorrespondeceDefinitionDetailsPage;
import com.maersk.dms.utilities.UIAutoUitilities;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChanelDefinitionWithinEffectiveDatesStepsDefs extends BrowserUtils {

    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubDPB = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    TenantManagerOutboundCorrespondeceDefinitionDetailsPage tMOCD = new TenantManagerOutboundCorrespondeceDefinitionDetailsPage();
    private OutboundCorrespondenceDefinitionChannelPage outboundCorrespondenceDefinitionChannelPage = new OutboundCorrespondenceDefinitionChannelPage();
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);

    public void updateCorrespondenceDefinitionEndDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +5);
        String newDate = dateFormat.format(calendar.getTime());
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tMOCD.outboundCorrDefDetHeader));

        for (int i = 0; i < tMOCD.channelsEndDates.size(); i++) {
            if(!tMOCD.channelsEndDates.get(i).getText().isEmpty()){
                tMOCD.channelsEndDates.get(i).click();
                uiAutoUitilities.get().clearWithActions(tMOCD.startEndDateFields.get(1));
                outboundCorrespondenceDefinitionChannelPage.saveButton.click();
                waitFor(2);
            }
        }

        fillTheFiled(tMOCD.startEndDateFields.get(1), newDate);
        if(tMOCD.endReason.getAttribute("value").isEmpty()) {
           fillTheFiled(tMOCD.endReason, RandomStringUtils.random(10, true, true));
        }
        pubDPB.get().clickSaveButton();
        pubDPB.get().verifyUpdateSuccessMessageVisible();
        waitFor(5);
    }

    public void verifyAnyChildChannelDefUpdated(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +5);
        String newDate = dateFormat.format(calendar.getTime());
        waitFor(5);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tMOCD.outboundCorrDefDetHeader));
        if(!pubDPB.get().elementIsDisplayed(tMOCD.noRecordsChannel)){
            List <String> endDates = getValuesListWebElement(tMOCD.channelsEndDates);
            if(!endDates.isEmpty()) {
                for (int i = 0; i <= endDates.size() - 1; i++) {
                    Assert.assertEquals(newDate, tMOCD.channelsEndDates.get(i).getText());
                }
            }
        }
    }

    public void enterStartDateBeforeParentStartDate(String parentDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(dateFormat.parse(parentDate));
        }catch(ParseException e){
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -2);
        String newDate = dateFormat.format(calendar.getTime());
        fillTheFiled(tMOCD.startEndDateFields.get(0),newDate);

    }

    public void enterEndDateBeforeParentStartDate(String parentDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(dateFormat.parse(parentDate));
        }catch(ParseException e){
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -2);
        String newDate = dateFormat.format(calendar.getTime());
        fillTheFiled(tMOCD.startEndDateFields.get(1),newDate);
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.corrChannelDetHeader);
    }

    public void enterEndDateAfterParentEndDate(String parentDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(dateFormat.parse(parentDate));
        }catch(ParseException e){
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, +2);
        String newDate = dateFormat.format(calendar.getTime());
        fillTheFiled(tMOCD.startEndDateFields.get(1),newDate);

    }

    public void verifyWarningMessage(String message){
        WebElement el = Driver.getDriver().findElement(By.xpath("//p[text()='" + message + "']"));
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(el));
    }

    public List<String> storeCorrDefStartEndDAtes() {
        List<String> startEndDaeValues = getValuesListWebElement(tMOCD.startEndDateFields);
        return startEndDaeValues;
    }

    public List<String> getValuesListWebElement(List<WebElement> el){
        ArrayList list = new ArrayList();
        for(WebElement element : el){
            if(pubDPB.get().elementIsDisplayed(element)){
                list.add(element.getAttribute("value"));
            }
        }
        return list;
    }


}
