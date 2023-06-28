package com.maersk.dms.step_definitions;

import com.maersk.crm.pages.tm.TMAddNewUserPage;
import com.maersk.crm.pages.tm.TMAddTeamPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.pages.tm.TMProjectRolePage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.pages.TenantManagerOutboundCorrespondeceDefinitionDetailsPage;
import com.maersk.dms.utilities.UIAutoUitilities;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditTeamTeamDetailsStepDefs extends BrowserUtils{
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr= ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pubDPB = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    final ThreadLocal<UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    final ThreadLocal<ObtainCorrespondenceItemsFromECMSRequestStepDefs> oCIF = ThreadLocal.withInitial(ObtainCorrespondenceItemsFromECMSRequestStepDefs::new);
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> pDBPIE = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    TenantManagerOutboundCorrespondeceDefinitionDetailsPage tMOCD = new TenantManagerOutboundCorrespondeceDefinitionDetailsPage();
    TMAddTeamPage tmAddTeamPage = new TMAddTeamPage();
    TMProjectRolePage tmProjectRolePage = new TMProjectRolePage();
    TMAddNewUserPage tenantManageTeamSectionPage = new TMAddNewUserPage();
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();

    public Map<String,String> createTeamDetails(){
        Map<String,String> dets = new HashMap<>();
        dets.put("TeamName", RandomStringUtils.random(50, true, true));
        dets.put("Description", RandomStringUtils.random(50, true, true));
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar date = Calendar.getInstance();
        dets.put("startDate", dateformat.format(date.getTime()));
        date.add(Calendar.DAY_OF_MONTH, +2);
        dets.put("endDate", dateformat.format(date.getTime()));
        return dets;
    }

    public void navToTeamSection(){
        outCorr.get().clickIfElementIsDisplayed(tmProjectDetailsPage.teamIcon);
    }

    public void clickFutureTeam(){
        if (!outCorr.get().elementIsDisplayed(tmAddTeamPage.futureStatus)) {
            outCorr.get().clickIfElementIsDisplayed(tmAddTeamPage.addBTN);
            waitFor(2);
            outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamDetailsHeader);
            Map<String, String> details = createTeamDetails();
            fillTheFiled(tenantManageTeamSectionPage.teamName, details.get("TeamName"));
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.businessUnit);
            waitFor(4);
            outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(2));
            fillTheFiled(tMOCD.startEndDateFields.get(0), details.get("endDate"));
            fillTheFiled(tenantManageTeamSectionPage.teamDescription, details.get("Description"));
            outCorr.get().clickSaveButton();
            outCorr.get().verifySuccessMessageVisible();
            navToTeamSection();
            waitFor(4);
        }
        outCorr.get().clickIfElementIsDisplayed(tmAddTeamPage.futureTeamName);
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamDetailsHeader));
    }

    public void clickActiveTeam(){
        if(outCorr.get().elementIsDisplayed(tmAddTeamPage.activeStatus)) {
            int randomIndex= tenantManageTeamSectionPage.radomNumber(10);
            outCorr.get().clickIfElementIsDisplayed(tmAddTeamPage.getRandomActiveTeamName(randomIndex));
            Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamDetailsHeader));
        }
    }

    public void clickInactiveTeam(){
        if(outCorr.get().elementIsDisplayed(tmAddTeamPage.inactiveStatus)) {
            int randomIndex= tenantManageTeamSectionPage.radomNumber(10);
            outCorr.get().clickIfElementIsDisplayed(tmAddTeamPage.getRandomInactiveTeamName(randomIndex));
            Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamDetailsHeader));
        }
    }

    public void clickEditTeam(){
        outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamDetailsHeader);
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.editTeam);
    }

    public void fieldsEditable(String field, Map<String, String> details) {
        switch (field) {
            case "TeamName":
                fillTheFiled(tenantManageTeamSectionPage.teamName, details.get("TeamName"));
                break;
            case "BusinessUnit":
                outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.businessUnit);
                waitFor(4);
                outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(1));
                break;
            case "startDate":
                fillTheFiled(tMOCD.startEndDateFields.get(0), details.get("startDate"));
                break;
            case "endDate":
                if(tMOCD.startEndDateFields.size()==1){
                    fillTheFiled(tMOCD.startEndDateFields.get(0), details.get("endDate"));
                }else {
                    fillTheFiled(tMOCD.startEndDateFields.get(1), details.get("endDate"));
                }
                break;
            case "Description":
                fillTheFiled(tenantManageTeamSectionPage.teamDescription, details.get("Description"));
                break;
            default:
                Assert.fail("Check Spelling of Cases");
        }
    }

    public void clickOnSpanButton(String name){
        WebElement e = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + name + "')]"));
        outCorr.get().clickIfElementIsDisplayed(e);
    }

    public void verifyWarningMessageDisplayed(String message) {
        WebElement e = Driver.getDriver().findElement(By.xpath("//p[text()='" + message + "']"));
        waitFor(2);
        Assert.assertTrue(outCorr.get().elementIsDisplayed(e));
    }

    public void verifySuccessMessageDisplayed() {
        waitFor(2);
        Assert.assertTrue(tenantManageTeamSectionPage.teamUpdatedsuccessMsg.isDisplayed());
    }

    public void verifyOnTeamSection(){
        try {
            WebElement e = Driver.getDriver().findElement(By.xpath("//th[text()='STATUS']"));
            Assert.assertTrue(outCorr.get().elementIsDisplayed(e));
        }catch (Exception e){
            navToTeamSection();
            Assert.assertTrue(outCorr.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//th[text()='STATUS']"))));
        }
    }

    public void clickSaveAndVerifySuccessMessage(){
        outCorr.get().clickSaveButton();
        outCorr.get().verifySuccessMessageVisible();
        waitFor(3);
    }

    public void clickAddUserButton(){
        waitFor(3);
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamUserListHeader));
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.addUserListBTN);
    }

    public Integer countOfAddingUsers(){
        return tenantManageTeamSectionPage.countAddingUsers.size();
    }

    public Integer countOfListUsers(){
        return tenantManageTeamSectionPage.countListUsers.size();
    }

    public ArrayList<String> listUsersFirstNames(){
        ArrayList<String> names =new ArrayList<>();
        for(int i=0; i<=countOfListUsers()-1; i++){
            names.add(tenantManageTeamSectionPage.userListsNames.get(i).getText());
        }
        return names;
    }

    public void deleteListUser(){
        if(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.editBtns.get(1))){
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.editBtns.get(1));
        }else {
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.editBtns.get(0));
        }
        pubDPB.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.deleteBtn);
        waitFor(2);
    }

    public ArrayList<String> selectsUsersByUserName() {
        ArrayList<String> userLists = new ArrayList<>();
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamUserListHeader));
        for (int i = 0; i <= tenantManageTeamSectionPage.countAddingUsers.size() - 1; i++) {
//            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.countAddingUsers.get(0));
//            if (!outCorr.get().elementIsDisplayed(tmProjectRolePage.permissionOptions.get(i)))
//                outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.countAddingUsers.get(0));
//            outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(i));
//            waitFor(3);
            selectRandomDropDownOption(tenantManageTeamSectionPage.countAddingUsers.get(i));
        }
        for (int i = 0; i <= tenantManageTeamSectionPage.countAddingUsers.size() - 1; i++) {
            userLists.add(tenantManageTeamSectionPage.userListNames.get(i).getAttribute("value")
                    + "/" + tenantManageTeamSectionPage.userListEmails.get(i).getAttribute("value")
                    + "/" + tenantManageTeamSectionPage.userListMaxIDs.get(i).getAttribute("value"));
        }
        return userLists;
    }

    public void verifyUserListNamesOrder(){
        ArrayList<String> names = listUsersFirstNames();
        ArrayList <String> sorted = listUsersFirstNames();
        Collections.sort(sorted);
        for(int i =0 ; i<=names.size()-1; i++){
            Assert.assertEquals(names.get(i),sorted.get(i));
        }
    }

    public void verifyUserListExcludingSupervisorNamesOrder(){
        ArrayList<String> names = listUsersFirstNames();
        ArrayList <String> sorted = listUsersFirstNames();
        Collections.sort(sorted);
        for(int i =1 ; i<=names.size()-1; i++){
            Assert.assertEquals(names.get(i),sorted.get(i));
        }
    }

    public void selectSupervisor(){
        pubDPB.get().clickIfElementIsDisplayed(tmAddTeamPage.supervisorCheckBox);
    }

    public String addUserByEmail(String supervisor){
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamUserListHeader));
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.addUserListBTN);
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListEmail);
        if(!outCorr.get().elementIsDisplayed(tmProjectRolePage.permissionOptions.get(2))) outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListEmail);
        outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(2));
        String st = tenantManageTeamSectionPage.userListName.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListEmail.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListMaxID.getAttribute("value");
        if(supervisor.compareToIgnoreCase("true")==0){
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.teamSupervisor);
        }
        waitFor(2);
        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")));
        waitFor(5);
        return st;
    }

    public String addUserByUserName(String supervisor){
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamUserListHeader));
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.addUserListBTN);
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListName);
        if(!outCorr.get().elementIsDisplayed(tmProjectRolePage.permissionOptions.get(1))) outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListName);
        outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(1));
        String st = tenantManageTeamSectionPage.userListName.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListEmail.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListMaxID.getAttribute("value");
        if(supervisor.compareToIgnoreCase("true")==0){
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.teamSupervisor);
        }
        waitFor(2);
        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")));
        waitFor(5);
        return st;
    }

    public String addUserByMaxID(String supervisor){
        Assert.assertTrue(outCorr.get().elementIsDisplayed(tenantManageTeamSectionPage.teamUserListHeader));
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.addUserListBTN);
        outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListMaxID);
        if(!outCorr.get().elementIsDisplayed(tmProjectRolePage.permissionOptions.get(0))) outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.userListMaxID);
        outCorr.get().clickIfElementIsDisplayed(tmProjectRolePage.permissionOptions.get(0));
        String st = tenantManageTeamSectionPage.userListName.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListEmail.getAttribute("value") + "/" + tenantManageTeamSectionPage.userListMaxID.getAttribute("value");
        if(supervisor.compareToIgnoreCase("true")==0){
            outCorr.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.teamSupervisor);
        }
        waitFor(2);
        pubDPB.get().clickIfElementIsDisplayed(Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")));
        waitFor(5);
        return st;
    }

    public void editFirstUserList(){
        pubDPB.get().clickIfElementIsDisplayed(tenantManageTeamSectionPage.editBtns.get(0));
    }

    public boolean updateUserList(){
        boolean sup = false;
        if(pubDPB.get().elementIsDisplayed(tmAddTeamPage.noRecordAvailableTxt)){
            addUserByUserName("true");
            pubDPB.get().elementIsDisplayed(tenantManageTeamSectionPage.saveBtn);
        }
        editFirstUserList();
        if (tenantManageTeamSectionPage.supervisorEditCheckBox.getAttribute("value") != "true") sup = true;
        tenantManageTeamSectionPage.supervisorEditCheckBox.click();
        pubDPB.get().clickSaveButton();
        return sup;
    }

    public boolean verifyPContainsElement(String contains){
        return pubDPB.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + contains + "')]")));
    }

    public boolean verifyPElement(String text){
        return pubDPB.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + text + "']")));
    }

    public void verifySupervisorColor() {
        if (pubDPB.get().elementIsDisplayed(tenantManageTeamSectionPage.supervisorBC)) {
            String color = tenantManageTeamSectionPage.supervisorBC.getCssValue("background-color");
            String colorInHex = Color.fromString(color).asHex().toUpperCase();
            Assert.assertEquals("#E57373", colorInHex);
        }
    }

    public void verifyRegularUsersColor() {
        if (pubDPB.get().elementIsDisplayed(tenantManageTeamSectionPage.regularBC.get(0))) {
            for (int i = 0; i <= tenantManageTeamSectionPage.regularBC.size() - 1; i++) {
                String color = tenantManageTeamSectionPage.regularBC.get(i).getCssValue("background-color");
                String colorInHex = Color.fromString(color).asHex().toUpperCase();
                Assert.assertEquals("#768FDF", colorInHex);
            }
        }
    }

}
