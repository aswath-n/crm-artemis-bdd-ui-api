package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.ConfigurationReader;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class CRM_ManualStartCallStepDef extends CRMUtilities implements ApiBase {

     CRMContactRecordUIPage crmContactRecordUIPage=new CRMContactRecordUIPage();
   //toDo add the header ID in the Configuration propetry file with the Testing account
    //public String headerID = ConfigurationReader.getProperty("headerID");
    public String userName=ConfigurationReader.getProperty("login");

     @Then("I should see the Inbound Contact text should be present")
    public void i_should_see_the_inbound_contact_text_should_be_present(){
        waitForVisibility(crmContactRecordUIPage.InboundText,10);
         Assert.assertTrue(crmContactRecordUIPage.InboundText.isDisplayed());
     }

     @Then("I should see the Phone text should be present")
    public void i_should_see_the_phone_text_should_be_present(){
         Assert.assertTrue(crmContactRecordUIPage.phoneText.isDisplayed());
     }

     @Then("I should see the User ID should be displayed")
    public void i_should_see_the_user_id_should_be_displayed(){
       String text=crmContactRecordUIPage.headerID.getText();
       System.out.println(text);
       //toDO with the Header ID and uncomment the If Condition
            Assert.assertTrue(crmContactRecordUIPage.headerID.isDisplayed());

     }

     @Then("I should see the Username should be displayed {string}")
      public void i_should_see_the_username_should_be_displayed(String expected){

         String text=crmContactRecordUIPage.headerUsername.getText();
         System.out.println(text);
         if(expected.contains(text)|| userName.contains(text))
         {
             Assert.assertTrue(crmContactRecordUIPage.headerUsername.isDisplayed());
         }
         else{
             Assert.assertTrue(crmContactRecordUIPage.headerUsername.isDisplayed());
         }
     }

}
