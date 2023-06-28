package com.maersk.crm.utilities;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.pages.crm.CRMCreateConsumerProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.maersk.crm.utilities.BrowserUtils.*;
import static com.maersk.crm.utilities.BrowserUtils.expandIfNecesary;
import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static com.maersk.crm.utilities.World.createNewWorld;
import static com.maersk.crm.utilities.World.getWorld;

public class FillOutForm extends BrowserUtils {



    public void createConsumerForm(CRMCreateConsumerProfilePage page){
        createNewWorld();
        Map<String,WebElement> locators = page.getLocatorsCreateConsumerForm();
        Map<String,String> consumerBeanData = getWorld().contactBean.get().getRandomConsumer();
        for(String name:locators.keySet()){
           if("state".equals(name)||"addressType".equals(name)||"gender".equals(name)||"county".equals(name)){
               selectDropDown(locators.get(name),consumerBeanData.get(name));
           }
           else{
               clearAndFillText(locators.get(name),consumerBeanData.get(name));
           }
           waitForClickablility(locators.get(name),10);System.out.println(consumerBeanData.get(name));
        }

    }
    /*
    Fills out contact details fields and contact dispositions from datatable
     */
    public void addContactReason(CRMContactRecordUIPage page, int amountReasonsToCreate, Map<String,String> dataTable){
        Map<String,WebElement> locators = page.getLocatorsReasons();
        for(int index=1;index<=amountReasonsToCreate;index++) {
//            staticWait(900);
            new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10)).until((ExpectedCondition<Boolean>) driver -> Driver.getDriver().findElement(By.xpath("//*[contains(text(), 'REASONS')]/../..")).getAttribute("aria-expanded").equalsIgnoreCase("false"));
//            expandIfNecesary(page.expendReasonButton,"aria-expanded","false");
            waitForClickablility(locators.get("contactReason"),10);
            selectDropDown(locators.get("contactReason"), dataTable.get("contactReason"+index));
            waitForClickablility(locators.get("contactAction"),10);
            selectDropDown(locators.get("contactAction"), dataTable.get("contactAction"+index));
            Driver.getDriver().findElement(By.xpath("//body")).click();
            staticWait(500);
            clearAndFillText(locators.get("reasonComments"), dataTable.get("reasonComments"+index));
            waitForClickablility(page.saveReasonButton,10);
            page.saveReasonButton.click();
        }
        getWorld().contactRecordBean.get().setContactReasonData(dataTable);
    }
    /*
   Fills out contact details fields and contact dispositions from random values
    */
    public void addContactReason(CRMContactRecordUIPage page, int amountReasonsToCreate){
        Map<String,WebElement> locators = page.getLocatorsReasons();
        Map<String,String> consumerBeanData = getWorld().contactRecordBean.get().getRandomContactReason();
        for(int index=1;index<=amountReasonsToCreate;index++) {
//            page.expendReasonButton.click();
            staticWait(500);
            for (String name : locators.keySet()) {
                if ("reasonComment".equals(name)) {
                    clearAndFillText(locators.get(name), consumerBeanData.get(name));
                }else if("contactAction".equals(name)){
                    selectDropDown(locators.get(name), consumerBeanData.get(name));
                    Driver.getDriver().findElement(By.xpath("//body")).click();
                    staticWait(500);
                }
                else {
                    selectDropDown(locators.get(name), consumerBeanData.get(name));
                }
            }
            page.saveReasonButton.click();
            staticWait(500);
        }
    }
    /*
        Fill out contact details from values coming from cucumber dataTable
     */
    public void contactDetailsInbound(CRMContactRecordUIPage page, Map<String,String> dataTable){
        Map<String,WebElement> locators = page.getLocatorsContactDetails();
//        new WebDriverWait(Driver.getDriver(),10).until(ExpectedConditions.invisibilityOf(locators.get("successSnackBar")));
        waitForClickablility(locators.get("contactType"),10);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        selectDropDown(locators.get("contactType"),dataTable.get("contactType"));
        getWorld().contactRecordBean.get().setContactType(dataTable.get("contactType"));
        waitForClickablility(locators.get("inboundCallQueue"),10);
        selectDropDown(locators.get("inboundCallQueue"),dataTable.get("inboundCallQueue"));
        waitForClickablility(locators.get("contactChannel"),10);
        selectDropDown(locators.get("contactChannel"),dataTable.get("contactChannel"));
        waitForClickablility(locators.get("contactDispositions"),10);
        selectDropDown(locators.get("contactDispositions"),dataTable.get("contactDispositions"));
        waitForClickablility(locators.get("selectProgram"),10);
        selectDropDown(locators.get("selectProgram"),dataTable.get("selectProgram"));
        Driver.getDriver().findElement(By.xpath("//body")).click();
        staticWait(700);
        clearAndFillText(locators.get("phoneNumber"),getWorld().contactBean.get().getPhoneNumber());
    }

    public void contactDetailsOutbound(CRMContactRecordUIPage page, Map<String,String> dataTable){
        Map<String,WebElement> locators = page.getLocatorsContactDetails();
        waitForClickablility(locators.get("contactType"),10);
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(2);
        selectDropDown(locators.get("contactType"),dataTable.get("contactType"));
        getWorld().contactRecordBean.get().setContactType(dataTable.get("contactType"));
        waitForClickablility(locators.get("contactCampaignType"),10);
        selectDropDown(locators.get("contactCampaignType"),dataTable.get("contactCampaignType"));
        getWorld().contactRecordBean.get().setContactCampaignType(dataTable.get("contactCampaignType"));
        waitForClickablility(locators.get("contactChannel"),10);
        selectDropDown(locators.get("contactChannel"),dataTable.get("contactChannel"));
        waitForClickablility(locators.get("contactDispositions"),10);
        selectDropDown(locators.get("contactDispositions"),dataTable.get("contactDispositions"));
        waitForClickablility(locators.get("selectProgram"),10);
        selectDropDown(locators.get("selectProgram"),dataTable.get("selectProgram"));
        Driver.getDriver().findElement(By.xpath("//body")).click();
        staticWait(700);
        clearAndFillText(locators.get("phoneNumber"),getWorld().contactBean.get().getPhoneNumber());
    }

    public List<WebElement> getElementsPresent(List<WebElement> list){
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        List<WebElement> validLocators = new ArrayList<>();
        for(WebElement element:list){
            try{
                if(element.isDisplayed()){
                    validLocators.add(element);
                }
            }
            catch (NoSuchElementException ns){
                continue;
            }
        }
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return validLocators;
    }

}
