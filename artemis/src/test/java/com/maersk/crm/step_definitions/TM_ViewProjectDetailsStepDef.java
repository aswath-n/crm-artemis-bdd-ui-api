package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class TM_ViewProjectDetailsStepDef extends BrowserUtils {

    TMProjectDetailsPage projectDetails= new TMProjectDetailsPage();

    //Author: Vidya Date:1-22-2020
    @Then("I will verify {string},{string},{string},{string},{string},{string},{string},{string},{string},{string} fields values in project details page")
    public void verifyProjectDetailsAllFieldsValue(String prjName,String prjState,String prgName,String contractId,
                                                   String stateAgencyName,String startDate,String endDate,
                                                   String goLiveDate, String provStatus,String timeZone){
        waitFor(1);
        Assert.assertEquals(projectDetails.projectName.getAttribute("value"),prjName);
        Assert.assertEquals(projectDetails.getState.getAttribute("value"),prjState);
        Assert.assertEquals(projectDetails.programName.getAttribute("value"),prgName);
        Assert.assertEquals(projectDetails.contractId.getAttribute("value"),contractId);
        Assert.assertEquals(projectDetails.stateAgencyName.getAttribute("value"),stateAgencyName);
        Assert.assertEquals(projectDetails.getStartDate.getAttribute("value"),startDate);
        Assert.assertEquals(projectDetails.getEndDate.getAttribute("value"),endDate);
        Assert.assertEquals(projectDetails.getGoLiveDate.getAttribute("value"),goLiveDate);
        Assert.assertEquals(projectDetails.getProvStatus.getAttribute("value"),provStatus);
        Assert.assertEquals(projectDetails.getTimeZone.getAttribute("value"),timeZone);
    }

    //Author: Vidya Date:1-22-2020
    @Then("I will verify the Contact Details for {string},{string},{string},{string},{string},{string} in project details page")
    public void verifyContactDetaisInProjectDetailsPage(String Role,String fName, String mName, String lName,
                                                        String phone, String email){
        waitFor(1);
        String[] value={fName,mName,lName,phone,email};
        switch (Role){
            case "Account Manager":
                for(int i=0; i<projectDetails.actMngrDetails.size()-1;i++){
                    Assert.assertEquals(projectDetails.actMngrDetails.get(i).getText(),value[i]);
                }
                break;
            case "Contact1":
                for(int i=0; i<projectDetails.contact1Details.size()-1;i++){
                    Assert.assertEquals(projectDetails.contact1Details.get(i).getText(),value[i]);
                }
                break;
            case "Contact2":
                for(int i=0; i<projectDetails.contact2Details.size()-1;i++){
                    Assert.assertEquals(projectDetails.contact2Details.get(i).getText(),value[i]);
                }
                break;
            case "Account Approver":
                for(int i=0; i<projectDetails.actApvrDetails.size()-1;i++){
                    Assert.assertEquals(projectDetails.actApvrDetails.get(i).getText(),value[i]);
                }
                break;

        }
        /*String[] acntMngerDtl={"Adeline","","Pierre","6789926241","AdelineBPierre@maersk.com"};
        String[] acntApvrDtl={"Donna","","Herren","7032518502","DonnaLHerren@maersk.com"};
        String[] contact2Dtl={"Jeffrey","","Hines","4045758008","JefferyHines@maersk.com"};
        String[] contact1Dtl={"Octavius","","Robinson","4045758074","OctaviusRobinson@maersk.com"};

        //to validate account approver details
        for(int i=0; i<projectDetails.actApvrDetails.size()-1;i++){
            Assert.assertEquals(projectDetails.actApvrDetails.get(i).getText(),acntApvrDtl[i]);
        }

        //to validate account manager details
        for(int i=0; i<projectDetails.actMngrDetails.size()-1;i++){
            Assert.assertEquals(projectDetails.actMngrDetails.get(i).getText(),acntMngerDtl[i]);
        }

        //to validate contact one details
        for(int i=0; i<projectDetails.contact1Details.size()-1;i++){
            Assert.assertEquals(projectDetails.contact1Details.get(i).getText(),contact1Dtl[i]);
        }

        //to validate contact two details
        for(int i=0; i<projectDetails.contact2Details.size()-1;i++){
            Assert.assertEquals(projectDetails.contact2Details.get(i).getText(),contact2Dtl[i]);
        }*/
    }
}
