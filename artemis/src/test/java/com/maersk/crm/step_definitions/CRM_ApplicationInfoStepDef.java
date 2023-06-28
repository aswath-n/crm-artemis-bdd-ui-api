package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMApplicationInfoPage;
import com.maersk.crm.pages.crm.CRMDemographicContactInfoPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class CRM_ApplicationInfoStepDef extends CRMUtilities implements ApiBase {

    CRMApplicationInfoPage applicationInfoPage = new CRMApplicationInfoPage();
    CRMDemographicContactInfoPage demographicContactInfoPage = new CRMDemographicContactInfoPage();

    @Then("I see the Application Info Tab")
    public void i_see_the_application_info_tab() {
        waitForVisibility(demographicContactInfoPage.applicationInfoTab, 20);
        assertTrue(isElementDisplayed(demographicContactInfoPage.applicationInfoTabLocation), "applicationInfoTabLocation is incorrect");
    }

    @And("I click on the application info tab")
    public void i_click_application_info_tab(){
        waitForVisibility(demographicContactInfoPage.applicationInfoTabLink, 10);
        demographicContactInfoPage.applicationInfoTabLink.click();
        waitFor(2);
    }

    @And("I should not see application Info Tab")
    public void i_should_not_see_application_info_tab() {
        assertTrue(!isElementDisplayed(demographicContactInfoPage.applicationInfoTab), "applicationInfoTab is  displayed");
    }

    @And("I validate values of application Information for recent application")
    public void iValidateApplicationInformation(){
        Map<Integer, Map<String,Object>> map = (Map) World.generalSave.get().get("CaseApplicationMap");
        Integer appId = Integer.parseInt(applicationInfoPage.applicationIdColumnValues.get(0).getText());
        Map<String,Object> objMap = (Map)map.get(appId);
        System.out.println(objMap);
        Assert.assertEquals(objMap.get("applicationId").toString() ,applicationInfoPage.applicationIdColumnValues.get(0).getText() , "Application id does not match");
        Assert.assertEquals(objMap.get("applicationCycle").toString(),applicationInfoPage.appCycleColumnValues.get(0).getText() , "appCycleColumnValues does not match");
        Assert.assertEquals(objMap.get("channelId").toString() ,applicationInfoPage.channelColumnValues.get(0).getText() , "channelId does not match");
        Assert.assertEquals(objMap.get("applicationStatus").toString() ,applicationInfoPage.statusColumnValues.get(0).getText() , "applicationStatus does not match");
        if(objMap.get("consumerLastName")==null){
            Assert.assertEquals("-- --" ,applicationInfoPage.lastNameColumnValues.get(0).getText() , "consumerLastName does not match");
        }else {
            Assert.assertEquals(objMap.get("consumerLastName").toString(), applicationInfoPage.lastNameColumnValues.get(0).getText(), "consumerLastName does not match");
        }
        if(objMap.get("consumerFirstName")==null){
            Assert.assertEquals("-- --" ,applicationInfoPage.firstNameColumnValues.get(0).getText() , "consumerFirstName does not match");
        }else {
            Assert.assertEquals(objMap.get("consumerFirstName").toString(), applicationInfoPage.firstNameColumnValues.get(0).getText(), "consumerFirstName does not match");
        }
        if(objMap.get("programId")==null){
            Assert.assertEquals("-- --" ,applicationInfoPage.programColumnValues.get(0).getText() , "programId does not match");
        }else {
            Assert.assertEquals(objMap.get("programId").toString(), applicationInfoPage.programColumnValues.get(0).getText(), "programId does not match");
        }
        if(objMap.get("externalApplicationId")==null){
            Assert.assertEquals("-- --" ,applicationInfoPage.extAppIdColumnValues.get(0).getText() , "externalApplicationId does not match");
        }else {
            Assert.assertEquals(objMap.get("externalApplicationId").toString(), applicationInfoPage.extAppIdColumnValues.get(0).getText(), "externalApplicationId does not match");
        }
        if(objMap.get("consumerDateOfBirth")==null){
            Assert.assertEquals("-- --" ,applicationInfoPage.applicantDobColumnValues.get(0).getText() , "applicantDobColumnValues does not match");
        }else {
            DateTimeFormatter oldDtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter newDtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate consumerDateOfBirth = LocalDate.parse(objMap.get("consumerDateOfBirth").toString(), oldDtFormat);
            String consumerDt = consumerDateOfBirth.format(newDtFormat);
            Assert.assertTrue(consumerDt.equals(applicationInfoPage.applicantDobColumnValues.get(0).getText()),"applicantDobColumnValues does not match");
        }
    }

    @And ("I validate column order of Application Info Tab")
    public void iValidateColumnOrderOfApplicationInfoTab(List<List<String>> data){
        List<String> expectedColumns = data.get(0);
        List<WebElement> actualColumns = applicationInfoPage.headerValues;
        Assert.assertEquals(actualColumns.size()-1,expectedColumns.size());
        for (int i = 0; i < expectedColumns.size(); i++) {
            Assert.assertEquals(expectedColumns.get(i).toLowerCase().trim(), actualColumns.get(i+1).getText().toLowerCase().trim());
        }
    }

    @Then("I click on application Id {int}")
    public void iClickOnApplicationId(int rowNum){
        applicationInfoPage.applicationIdColumnValues.get(rowNum).click();
    }


}
