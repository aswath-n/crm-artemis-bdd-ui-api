package com.maersk.dms.step_definitions;

import com.maersk.crm.pages.tm.TMProjectRolePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionPage;
import com.maersk.dms.pages.TenantManagerOutboundCorrespondeceDefinitionDetailsPage;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.pages.TenantManagerRoleDetailsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DPBIOutboundCorrespondenceConfigEventsStepDefs extends BrowserUtils{

    private final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubDPB = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    TenantManagerOutboundCorrespondeceDefinitionDetailsPage tMOCD = new TenantManagerOutboundCorrespondeceDefinitionDetailsPage();
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    TenantManagerRoleDetailsPage tenantManagerRoleDetailsPage = new TenantManagerRoleDetailsPage();

    public boolean outboundCorrDefDetHeaderDisplayed(){
        return pubDPB.get().elementIsDisplayed(tMOCD.outboundCorrDefDetHeader);
    }

    public boolean outboundCorrDefHeaderDisplayed(){
        return pubDPB.get().elementIsDisplayed(tMOCD.outboundCorrDefHeader);
    }

    public void clickAddChannelButton(){
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.addChannelButton);
    }

    public boolean corrChannelDetHeaderDisplayed(){
        return pubDPB.get().elementIsDisplayed(tMOCD.corrChannelDetHeader);
    }

    public void clickSelectChannelField(){
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.selectChannelField);
    }

    public void clickCorrDefID(){
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.corrDefID);
    }

    public void selectGivenChannelOpt(String type) {
        if (pubDPB.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")))) {
            Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")).click();
        }
    }

    public String outboundCorrChannelTypeTXT(){
        hover(tMOCD.outboundCorrChannelType);
        return pubDPB.get().getTextIfElementIsDisplayed(tMOCD.outboundCorrChannelType);
    }

    public void outboundCorrChannelTypeTXT(String type){
        WebElement e = Driver.getDriver().findElement(By.xpath("//p[text()='CHANNEL']/parent::div/p[text()='" + type + "']"));
        hover(e);
        e.click();
    }

    public String outboundCorrStartDate(){
        hover(tMOCD.outboundCorrStartDate);
        return pubDPB.get().getTextIfElementIsDisplayed(tMOCD.outboundCorrStartDate);
    }

    public String outboundCorrEndDate(){
        hover(tMOCD.outboundCorrEndDate);
        return pubDPB.get().getTextIfElementIsDisplayed(tMOCD.outboundCorrEndDate);
    }
    public String expectSendImmediatelyOpt(){
        return tMOCD.outboundCorrSendImmediatelyOpt.getAttribute("value");
    }

    public String expectMandatoryOpt(){
        return tMOCD.outboundCorrMandatoryOpt.getAttribute("value");
    }

    public void clickSendImmediatelyOpt(){
        if(Driver.getDriver().findElement(By.xpath("//input[@id='sendImmediately']")).getAttribute("value").compareToIgnoreCase("false")==0) {
            pubDPB.get().clickIfElementIsDisplayed(tMOCD.sendImmediatelyOpt);
        }
    }

    public void clickMandatoryOpt(){
        if(Driver.getDriver().findElement(By.xpath("//input[@id='mandatory']")).getAttribute("value").compareToIgnoreCase("false")==0) {
            pubDPB.get().clickIfElementIsDisplayed(tMOCD.mandatoryOpt);
        }
    }

    public void clickCorrDefIDFirst(){
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.corrDefIDFirst);
    }

    public ArrayList<String> enterStartEndDates() throws ParseException {
        ArrayList<String> startEndDates = new ArrayList<>();
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat payloadformat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar date = Calendar.getInstance();
        String startDate = dateformat.format(date.getTime());
        startEndDates.add(startDate);
        if(!tMOCD.startEndDateFields.get(0).getAttribute("value").isEmpty()){
            Actions actions = new Actions(Driver.getDriver());
            tMOCD.startEndDateFields.get(0).click();
            actions.keyDown(tMOCD.startEndDateFields.get(0), Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        }
        tMOCD.startEndDateFields.get(0).click();
        tMOCD.startEndDateFields.get(0).sendKeys(startDate);
        startEndDates.add(payloadformat.format(date.getTime()));
        date.add(Calendar.DAY_OF_MONTH, +1);
        String endDate = dateformat.format(date.getTime());
        startEndDates.add(endDate);
        tMOCD.startEndDateFields.get(1).click();
        tMOCD.startEndDateFields.get(1).sendKeys(endDate);
        date.add(Calendar.DAY_OF_MONTH, +1);
        startEndDates.add(payloadformat.format(date.getTime()));
        return startEndDates;
    }

    public ArrayList<String> enterStartEndDatesUpdate() throws ParseException {
        ArrayList<String> startEndDates = new ArrayList<>();
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat payloadformat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar date = Calendar.getInstance();
        String startDate = dateformat.format(date.getTime());
        startEndDates.add(startDate);
        if(!tMOCD.startEndDateFields.get(0).getAttribute("value").isEmpty()){
            Actions actions = new Actions(Driver.getDriver());
            actions.click(tMOCD.startEndDateFields.get(0));
            actions.keyDown(tMOCD.startEndDateFields.get(0), Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        }
        tMOCD.startEndDateFields.get(0).click();
        fillTheFiled(tMOCD.startEndDateFields.get(0),startDate);
        startEndDates.add(payloadformat.format(date.getTime()));
        date.add(Calendar.DAY_OF_MONTH, +1);
        String endDate = dateformat.format(date.getTime());
        startEndDates.add(endDate);
        tMOCD.startEndDateFields.get(1).click();
        fillTheFiled(tMOCD.startEndDateFields.get(1),endDate);
        startEndDates.add(payloadformat.format(date.getTime()));
        return startEndDates;
    }



    public String clickMessageSenderEmail(String message){
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.senderEmail);
        tMOCD.senderEmail.sendKeys(message);
        waitFor(2);
        return tMOCD.senderEmail.getAttribute("value");
    }

    public String clickMessageEndReason(String message){
        if(!tMOCD.endReason.getAttribute("value").isEmpty()){
            Actions actions = new Actions(Driver.getDriver());
            tMOCD.endReason.click();
            actions.keyDown(tMOCD.endReason, Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
        }
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.endReason);
        tMOCD.endReason.sendKeys(message);
        waitFor(2);
        return tMOCD.endReason.getAttribute("value");
    }

    public void clickSaveButtonChannelDetails(){
        pubDPB.get().clickSaveButton();
    }

    public void verifySuccessMessageChannelSave(){
        pubDPB.get().verifySuccessMessageVisible();
    }

    public void clickSaveButtonVerifySuccessMessage() {
        waitFor(2);
        clickSaveButtonChannelDetails();
        verifySuccessMessageChannelSave();
        waitFor(2);
    }

    public void clickOnGivenID(String id) {
        waitFor(2);
//        pubDPB.get().clickIfElementIsDisplayed(tMOCD.defaultShow10);
//        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//li[text()='show 20']")));
        for(int i=1; i<=tMOCD.corrDefPageNums.size(); i++){
            try{
                WebElement eID1 = Driver.getDriver().findElement(By.xpath("//td/a[text()='" + id + "']"));
                if(eID1.isDisplayed()){
                    pubDPB.get().clickIfElementIsDisplayed(eID1);
                    break;
                }
            }catch (NoSuchElementException ex){}
            try{
                hover(tMOCD.corrDefPageNums.get(i));
                pubDPB.get().clickIfElementIsDisplayed(tMOCD.corrDefPageNums.get(i));
                hover(tMOCD.outboundCorrDefHeader);
                WebElement eID = Driver.getDriver().findElement(By.xpath("//td/a[text()='" + id + "']"));
                if(eID.isDisplayed()){
                    pubDPB.get().clickIfElementIsDisplayed(eID);
                    break;
                }
            }catch (NoSuchElementException e){
                hover(tMOCD.corrDefPageNums.get(i));
                pubDPB.get().clickIfElementIsDisplayed(tMOCD.corrDefPageNums.get(i));
                continue;
            }
        }
        waitFor(2);
        Assert.assertTrue(outboundCorrDefDetHeaderDisplayed());
    }

    public ArrayList<String> addOutboundCorrChannel(String channelType, String sendImmediately, String mandatory, String endReason, String senderEmailId) throws ParseException {
        clickAddChannelButton();
        waitFor(2);
        Assert.assertTrue(corrChannelDetHeaderDisplayed());
        clickSelectChannelField();
        waitFor(2);
        selectGivenChannelOpt(channelType);
        boolean sendImmediatelyOpt = sendImmediately.equalsIgnoreCase("true");
        boolean MandatoryOpt = mandatory.equalsIgnoreCase("true");
        if(sendImmediatelyOpt) clickSendImmediatelyOpt();
        if(MandatoryOpt) clickMandatoryOpt();
        ArrayList<String> startEndDates = enterStartEndDates();
        clickMessageEndReason(endReason);
        waitFor(2);
        if(channelType.equalsIgnoreCase("email")) clickMessageSenderEmail(senderEmailId);
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//span[text()=' Save ']")).click();
        verifySuccessMessageChannelSave();
        waitFor(2);
        Assert.assertTrue(outboundCorrDefDetHeaderDisplayed());
        waitFor(2);
        Assert.assertTrue(channelType.equalsIgnoreCase(outboundCorrChannelTypeTXT()));
        Assert.assertTrue(startEndDates.get(0).equalsIgnoreCase(outboundCorrStartDate()));
        Assert.assertTrue(startEndDates.get(2).equalsIgnoreCase(outboundCorrEndDate()));
        if(sendImmediatelyOpt) Assert.assertTrue(sendImmediately.equalsIgnoreCase(expectSendImmediatelyOpt()));
        if(MandatoryOpt) Assert.assertTrue(mandatory.equalsIgnoreCase(expectMandatoryOpt()));
        return startEndDates;
    }

    public ArrayList<String> addTemplate() throws Exception {
        Assert.assertTrue(outboundCorrDefDetHeaderDisplayed());
        ArrayList returnedInfo = new ArrayList();
        returnedInfo.add(outboundCorrespondenceDefinitionPage.Id.getAttribute("value"));
        returnedInfo.add(pubDPB.get().getTextIfElementIsDisplayed(tMOCD.outboundCorrChannelType));
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.outboundCorrChannelType);
        Assert.assertTrue(corrChannelDetHeaderDisplayed());
        classLoader("testData/tm/planAndRegionConfig/TextDocument.txt");
        waitFor(5);
        if (Driver.getDriver() instanceof RemoteWebDriver && !(Driver.getDriver() instanceof ChromeDriver)) {
            ((RemoteWebDriver) Driver.getDriver()).setFileDetector(new LocalFileDetector());
        }
        tMOCD.inputAddTemplate.sendKeys(replaceTrimURL.get());
        pubDPB.get().clickIfElementIsDisplayed(tMOCD.templateLanguage);
        waitFor(2);
        pubDPB.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(0));
        waitFor(2);
        pubDPB.get().clickSaveButton();
        waitFor(5);
        List<String> st = EventsUtilities.getRawLogs("channelDefinitionId");
        for (int i = 0; i <= st.size()-1; i++) {
            String st1 = st.get(i);
            String channelDef []= st1.split(",");
            for(int y=0; y<=channelDef.length-1; y++) {
                if (channelDef[y].contains("channelDefinitionId")) {
                    String st2[] = channelDef[y].split(":");
                    if (st2[1].matches("[0-9]{2,}")) {
                        returnedInfo.add(st2[1]);
                    }
                }
            }
        }
        returnedInfo.add(pubDPB.get().getTextIfElementIsDisplayed(outboundCorrespondenceDefinitionPage.inboundCorrespondenceDropdown));
        System.out.println(returnedInfo);
        return returnedInfo;
    }

    public ArrayList<String> updateOutboundCorrChannel(String channelTypeEdit, String channelType, String sendImmediately, String mandatory, String endReason, String senderEmailId) throws ParseException {
        waitFor(2);
        Assert.assertTrue(outboundCorrDefDetHeaderDisplayed());
        outboundCorrChannelTypeTXT(channelTypeEdit);
        waitFor(2);
        Assert.assertTrue(corrChannelDetHeaderDisplayed());
        clickSelectChannelField();
        waitFor(2);
        selectGivenChannelOpt(channelType);
        boolean sendImmediatelyOpt = sendImmediately.equalsIgnoreCase("true");
        boolean MandatoryOpt = mandatory.equalsIgnoreCase("true");
        if(sendImmediatelyOpt) clickSendImmediatelyOpt();
        if(MandatoryOpt) clickMandatoryOpt();
        ArrayList<String> startEndDates = enterStartEndDatesUpdate();
        clickMessageEndReason(endReason);
        waitFor(2);
        if(channelType.equalsIgnoreCase("email")) clickMessageSenderEmail(senderEmailId);
        waitFor(2);
        Driver.getDriver().findElement(By.xpath("//span[text()=' Save ']")).click();
        verifySuccessMessageChannelSave();
        Assert.assertTrue(outboundCorrDefDetHeaderDisplayed());
        waitFor(2);
        Assert.assertTrue(channelType.equalsIgnoreCase(outboundCorrChannelTypeTXT()));
        Assert.assertTrue(startEndDates.get(0).equalsIgnoreCase(outboundCorrStartDate()));
        Assert.assertTrue(startEndDates.get(2).equalsIgnoreCase(outboundCorrEndDate()));
        if(sendImmediatelyOpt) Assert.assertTrue(sendImmediately.equalsIgnoreCase(expectSendImmediatelyOpt()));
        if(MandatoryOpt) Assert.assertTrue(mandatory.equalsIgnoreCase(expectMandatoryOpt()));
        return startEndDates;
    }

    private ArrayList<String> startEndDatesDefinitionFirstChannel() throws ParseException {
        ArrayList<String> startEndDates = new ArrayList<>();
        DateFormat payloadformat = new SimpleDateFormat("yyyy/dd/MM");
        startEndDates.add(payloadformat.format(new SimpleDateFormat("dd/MM/yyyy").parse(tMOCD.outboundCorrStartDate.getAttribute("value"))));
        if(!tMOCD.outboundCorrEndDate.getAttribute("value").isEmpty()){
            startEndDates.add(payloadformat.format(new SimpleDateFormat("dd/MM/yyyy").parse(tMOCD.outboundCorrEndDate.getAttribute("value"))));
        }
        return startEndDates;
    }

    public ArrayList<String> startEndDatesFormatForEventPayload(String startDate, String endDate) throws ParseException {
        ArrayList<String> startEndDates = new ArrayList<>();
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat payloadformat = new SimpleDateFormat("yyyy/MM/dd");
        for(int i=1; i<=2; i++) {
            if(i==1) {
                Date date = inputFormat.parse(startDate);
                startEndDates.add(payloadformat.format(date));
            }else {
                Date date = inputFormat.parse(endDate);
                startEndDates.add(payloadformat.format(date));
            }
        }
        return startEndDates;
    }

    public String getValueWebElement(WebElement el){
        try{
            return el.getAttribute("value");
        }catch(NoSuchElementException e ){
            return String.valueOf(false);
        }
    }

    public void fillRoleDesc(String roleDesc){
        if(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleDesc)){
            pubDPB.get().clickIfElementIsDisplayed(tenantManagerRoleDetailsPage.roleDesc);
            fillTheFiled(tenantManagerRoleDetailsPage.roleDesc, roleDesc);
        }
    }

    public void fillRoleName(String roleName){
        if(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleName)){
            pubDPB.get().clickIfElementIsDisplayed(tenantManagerRoleDetailsPage.roleName);
            fillTheFiled(tenantManagerRoleDetailsPage.roleName, roleName);
        }
    }

    public void clickAddRoleButton(){
        pubDPB.get().clickIfElementIsDisplayed(tenantManagerRoleDetailsPage.addRoleButton);
    }

    public Boolean checkRoleList(String st){
        return pubDPB.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//td[text()='" + st + "']")));
    }

    public void clickFirstRole(){
        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElements(By.xpath("//th[text() = 'ROLE NAME']/parent::tr/parent::thead/following::tbody/tr/td[1]")).get(0));
    }

    public ArrayList<String> addRole(String projectId, String projectName, String roleName, String roleDescription) throws ParseException {
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleDetailsHeader));
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectName),projectName);
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectId),projectId);
        waitFor(2);
        fillRoleName(roleName);
        fillRoleDesc(roleDescription);
        ArrayList<String> startEndDates = enterStartEndDates();
        clickSaveButtonChannelDetails();
        verifySuccessMessageChannelSave();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleListHeader));
        waitFor(2);
        return startEndDates;
    }

    public ArrayList<String> updateRole(String projectId, String projectName, String roleName, String roleDescription) throws ParseException {
        clickFirstRole();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleDetailsHeader));
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectName),projectName);
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectId),projectId);
        waitFor(2);
        fillRoleName(roleName);
        fillRoleDesc(roleDescription);
        ArrayList<String> startEndDates = enterStartEndDates();
        clickSaveButtonChannelDetails();
        verifySuccessMessageChannelSave();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleListHeader));
        waitFor(2);
        return startEndDates;
    }

    public ArrayList<String> updateRoleName(String projectId, String projectName, String roleName) throws ParseException {
        clickFirstRole();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleDetailsHeader));
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectName),projectName);
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectId),projectId);
        waitFor(2);
        fillRoleName(roleName);
        ArrayList<String> startEndDates = enterStartEndDates();
        clickSaveButtonChannelDetails();
        verifySuccessMessageChannelSave();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleListHeader));
        waitFor(2);
        return startEndDates;
    }

    public ArrayList<String> updateRoleDesc(String projectId, String projectName,String roleDesc) throws ParseException {
        clickFirstRole();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleDetailsHeader));
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectName),projectName);
        Assert.assertEquals(getValueWebElement(tenantManagerRoleDetailsPage.roleProjectId),projectId);
        waitFor(2);
        fillRoleDesc(roleDesc);
        ArrayList<String> startEndDates = enterStartEndDates();
        clickSaveButtonChannelDetails();
        verifySuccessMessageChannelSave();
        waitFor(2);
        Assert.assertTrue(pubDPB.get().elementIsDisplayed(tenantManagerRoleDetailsPage.roleListHeader));
        waitFor(2);
        return startEndDates;
    }


}
