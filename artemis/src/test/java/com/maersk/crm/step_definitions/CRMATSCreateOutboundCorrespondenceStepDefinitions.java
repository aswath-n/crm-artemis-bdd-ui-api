package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.CRMDashboardPage;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ResponseBody;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class CRMATSCreateOutboundCorrespondenceStepDefinitions extends CRMUtilities implements ApiBase {

    CRMDashboardPage dashBoard = new CRMDashboardPage();
    BrowserUtils browserUtils = new BrowserUtils();
    CreateOutboundCorrespondencePage createOutboundCorrespondencePage = new CreateOutboundCorrespondencePage();
    CRMUtilities crmUtilities = new CRMUtilities();
    APIATSApplicationController applicationController = new APIATSApplicationController();
    APIClassUtil api = APIClassUtil.getApiClassUtilThreadLocal();
    private ResponseBody response;
    private List<Map<String, Object>> applicationConsumers = new ArrayList<>();
    private List<Map<String, Object>> applicationConsumerAddress = new ArrayList<>();
    private List<Map<String, Object>> applicationConsumerEmail = new ArrayList<>();
    private List<Map<String, Object>> applicationConsumerFax = new ArrayList<>();
    private List<Map<String, Object>> applicationConsumerPhones = new ArrayList<>();
    private List<Map<String, Object>> recipientAddress = new ArrayList<>();
    private List<Map<String, Object>> recipientEmail = new ArrayList<>();
    private List<Map<String, Object>> recipientFax = new ArrayList<>();
    private Map<String, Object> PIAppConsumer = new HashMap<>();
    private Map<String, Object> AMAppConsumer = new HashMap<>();
    private Map<String, Object> ARAppConsumer = new HashMap<>();

    @And("I click create Correspondence on Hamburger button")
    public void iClickCreateCorrespondenceOnHamburgerButton() {
        waitForVisibility(dashBoard.btnMenuList, 20);
        browserUtils.jsClick(dashBoard.btnMenuList);
        dashBoard.createCorrespondence.click();
    }

    @When("I click on Alert on Hamburder button")
    public void i_click_on_Alert_on_Hamburder_button() {
        waitForVisibility(dashBoard.btnMenuList, 20);
        browserUtils.jsClick(dashBoard.btnMenuList);
        waitFor(2);
        dashBoard.createAlertTab.click();
    }

    @When("I choose {string} as a correspondence type")
    public void iChooseAsACorrespondenceType(String correspondenceType) {
        waitFor(4);
        browserUtils.selectDropDown(createOutboundCorrespondencePage.typeDropdown, correspondenceType);
    }

    @And("I verify the Application ID under Regarding Section")
    public void iVerifyTheApplicationIDUnderRegardingSection() {
        assertEquals(createOutboundCorrespondencePage.applicationIDValue.getText(), applicationIdAPI.get(), "Application ID is not correct under Regarding Section");
        BrowserUtils.waitFor(2);
    }

    @Then("I verify {string} Checkbox is selected and disabled under Send To Section")
    public void iVerifyCheckboxIsSelectedAndDisabledUnderSendToSection(String checkBox) {
        waitFor(3);
        switch (checkBox.toUpperCase()) {
            case "EMAIL":
                assertTrue(createOutboundCorrespondencePage.pIemailCheckBoxInputStatus.getAttribute("class").contains("Mui-checked"), checkBox + " is not selected under Send To Section");
                assertTrue(createOutboundCorrespondencePage.pIemailCheckBoxInputStatus.getAttribute("class").contains("Mui-disabled"), checkBox + " is not disabled under Send To Section");
                break;
            case "MAIL":
                assertTrue(createOutboundCorrespondencePage.pImailCheckBoxInputStatus.getAttribute("class").contains("Mui-checked"), checkBox + " is not selected under Send To Section");
                assertTrue(createOutboundCorrespondencePage.pImailCheckBoxInputStatus.getAttribute("class").contains("Mui-disabled"), checkBox + " is not disabled under Send To Section");
                break;
            case "FAX":
                assertTrue(createOutboundCorrespondencePage.pIfaxCheckBoxInputStatus.getAttribute("class").contains("Mui-checked"), checkBox + " is not selected under Send To Section");
                assertTrue(createOutboundCorrespondencePage.pIfaxCheckBoxInputStatus.getAttribute("class").contains("Mui-disabled"), checkBox + " is not disabled under Send To Section");
                break;
            default:
                fail("CheckBox type didn't match");
        }
    }

    @And("I verify following destinations listed for Primary Individual")
    public void iVerifyFollowingDestinationsListedForPrimaryIndividual(Map<String, String> expectedDestinations) {
        if (expectedDestinations.containsKey("Email")) {
            assertEquals(createOutboundCorrespondencePage.emailDestination.getText(), expectedDestinations.get("Email"), "Email Destination didn't match");
        }
        if (expectedDestinations.containsKey("Mail")) {
            assertEquals(createOutboundCorrespondencePage.mailDestination.getText(), expectedDestinations.get("Mail"), "Mail Destination didn't match");
        }
        if (expectedDestinations.containsKey("Fax")) {
            assertEquals(createOutboundCorrespondencePage.faxDestination.getText(), expectedDestinations.get("Fax"), "Fax Destination didn't match");
        }
    }

    @And("I click Save button for create correspondence page")
    public void iClickSaveButtonForCreateCorrespondencePage() {
        createOutboundCorrespondencePage.saveButton.click();
    }

    @Then("I verify {string} Checkbox is selected under Send To Section")
    public void iVerifyCheckboxIsSelectedUnderSendToSection(String checkBox) {
        waitForPageToLoad(3);
        switch (checkBox.toUpperCase()) {
            case "PRIMARY INDIVIDUAL":
                assertTrue(createOutboundCorrespondencePage.primaryIndividualCheckBox.isSelected(), checkBox + " is not selected under Send To Section");
                break;
            case "DEFAULT SEND TO":
                assertEquals(createOutboundCorrespondencePage.sendToCheckBox.getAttribute("value"), "true", checkBox + " is not selected under Send To Section");
                break;
            default:
                fail("CheckBox type didn't match");
        }
    }

    @Then("I verify {string} is appeared")
    public void i_verify_is_appeared(String fieldType) {
        switch (fieldType) {
            case "Application id field":
                assertTrue(createOutboundCorrespondencePage.applicationIDText.isDisplayed(), "Application id field isnt getting displayed");
                assertTrue(markedMandatory(createOutboundCorrespondencePage.applicationIDText), "Application id field isnt marked mandatory");
                break;
            case "Case id":
                assertTrue(createOutboundCorrespondencePage.caseID.isDisplayed(), "Case id field isnt displayed");
                assertTrue(markedMandatory(createOutboundCorrespondencePage.caseID), "CaseId field isnt marked mandatory");
                break;
            case "Validate button":
                waitForVisibility(createOutboundCorrespondencePage.validateButton, 5);
                assertTrue(createOutboundCorrespondencePage.validateButton.isDisplayed(), "Validate button isnt displayed");
                break;
            case "Application id Edit":
                assertTrue(createOutboundCorrespondencePage.appIDeditButton.isDisplayed());
                try {
                    Assert.assertFalse(createOutboundCorrespondencePage.validateButton.isDisplayed());
                } catch (Exception e) {
                    Assert.assertTrue(true);
                }
                break;
            case "Code field":
                assertTrue(createOutboundCorrespondencePage.sendto_firstname.isDisplayed(), "Code field isnt displayed");
                assertTrue(createOutboundCorrespondencePage.sendto_lastname.isDisplayed(), "Code field isnt displayed");
                try {
                    Assert.assertFalse(createOutboundCorrespondencePage.applicationIdField.isDisplayed());
                } catch (Exception e) {
                    Assert.assertTrue(true);
                }
                break;
        }
    }

    @And("I click on Validate button for application id check")
    public void i_click_on_Validate_button_for_application_id_check() {
        jsClick(createOutboundCorrespondencePage.validateButton);
    }

    @Then("I provide {string} for applicationId field")
    public void i_provide_for_applicationId_field(String applicationId) {
        switch (applicationId) {
            case "random":
                createOutboundCorrespondencePage.applicationIdField.sendKeys("6566544343243");
                break;
        }
    }

    @Then("I verify {string} message is getting displayed")
    public void i_verify_message_is_getting_displayed(String messageType) {
        BrowserUtils.waitFor(3);
        switch (messageType) {
            case "Application id required":
                assertTrue(createOutboundCorrespondencePage.appIdRequiredMessage.isDisplayed(), "Application id required message isnt getting displayed");
                break;
            case "Application id invalid":
                assertTrue(createOutboundCorrespondencePage.appIdInvalidMessage.isDisplayed(), "Application id invalid message isnt getting displayed");
                break;
            case "OB Success":
                assertTrue(createOutboundCorrespondencePage.successMessage.getText().equals("The Correspondence Record is saved successfully."), "Success Message isnt verified");
                break;
        }
    }

    @When("I provide recently created application id to applicationId field on outbound correspondence create page")
    public void i_provide_recently_created_application_id_to_applicationId_field_on_outbound_correspondence_create_page() {
        BrowserUtils.sendKeyToTextField(createOutboundCorrespondencePage.applicationIdField, applicationIdAPI.get());
    }

    @Then("I verify {string} recipients data with the following channel data")
    public void i_verify_recipients_data_with_the_following_channel_data(String ConsumerType, List<String> channelType) {
        waitFor(5);
        scrollDownUsingActions(5);
        response = applicationController.getMemberMatchingResponse().get(0);
        System.out.println("Response: " + response.jsonPath().prettyPrint());
        applicationConsumers = response.jsonPath().getList("data.applicationConsumers");

        for (int i = 0; i < applicationConsumers.size(); i++) {
            String consumerRole = applicationConsumers.get(i).get("consumerRoleType").toString();
            if (consumerRole.equalsIgnoreCase("Primary Individual")) {
                PIAppConsumer = applicationConsumers.get(i);
            } else if (consumerRole.equalsIgnoreCase("Authorized Rep")) {
                ARAppConsumer = applicationConsumers.get(i);
            } else if (consumerRole.equalsIgnoreCase("Application Member")) {
                AMAppConsumer = applicationConsumers.get(i);
            }
        }

        switch (ConsumerType) {
            case "Primary Individual":
                assertEquals((PIAppConsumer.get("consumerFirstName") + " " + PIAppConsumer.get("consumerLastName")), createOutboundCorrespondencePage.primaryIndividualName.getText(), "Mismatch in expected Primary Individual Full Name");
                assertEquals(PIAppConsumer.get("applicationConsumerId"), Integer.parseInt(createOutboundCorrespondencePage.primaryIndividualID.getText()), "Mismatch in expected Primary individual ID");
                createOutboundCorrespondencePage.primaryIndividualCheckBox.click();
                waitFor(3);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            applicationConsumerAddress = response.jsonPath().getList("data.applicationConsumers.applicationConsumerAddress");
                            String fullAddress = ((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressStreet1") + " " + ((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressCity") + " " + ((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressState") + " " + ((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressZip");
                            System.out.println("Full address for primary individual is: " + fullAddress);
                            assertEquals(("Application Primary Mailing - " + fullAddress), createOutboundCorrespondencePage.MailDestinationForPrimaryInd.getText(), "Mail destination isnt verified for primary individual");
                            break;
                        case "Email":
                            applicationConsumerEmail = response.jsonPath().getList("data.applicationConsumers.applicationConsumerEmail");
                            assertEquals(("Primary-" + ((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerEmail")).get(0)).get("emailAddress")), createOutboundCorrespondencePage.EmailDestinationForPrimaryInd.getText(), "Email destination isnt verified for primary individual");
                            break;
                        case "Fax":
                            applicationConsumerFax = response.jsonPath().getList("data.applicationConsumers.applicationConsumerPhone");
                            assertEquals((((HashMap) ((ArrayList) PIAppConsumer.get("applicationConsumerPhone")).get(0)).get("phoneNumber")), createOutboundCorrespondencePage.FaxDestinationForPrimaryInd.getText().replace("-", ""), "Fax destination isnt verified for primary individual");
                            break;
                    }
                }
                createOutboundCorrespondencePage.primaryIndividualCheckBox.click();
                break;


            case "Authorized Rep":
                assertEquals((ARAppConsumer.get("consumerFirstName") + " " + ARAppConsumer.get("consumerLastName")), createOutboundCorrespondencePage.authorizedRepName.getText(), "Mismatch in expected Authorized rep Full Name");
                assertEquals(ARAppConsumer.get("applicationConsumerId"), Integer.parseInt(createOutboundCorrespondencePage.authorizedRepID.getText()), "Mismatch in expected Authorized rep id");
                createOutboundCorrespondencePage.authorizedRepCheckBox.click();
                waitFor(3);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            applicationConsumerAddress = response.jsonPath().getList("data.applicationConsumers.applicationConsumerAddress");
                            String fullAddressAuthRep = ((HashMap) ((ArrayList) ARAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressStreet1") + " " + ((HashMap) ((ArrayList) ARAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressCity") + " " + ((HashMap) ((ArrayList) ARAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressState") + " " + ((HashMap) ((ArrayList) ARAppConsumer.get("applicationConsumerAddress")).get(0)).get("addressZip");
                            System.out.println("Full address for auth rep is: " + fullAddressAuthRep);
                            assertEquals(("Application Primary Mailing - " + fullAddressAuthRep), createOutboundCorrespondencePage.MailDestinationForAuthorizedRep.getText(), "Mail destination isnt verified for authorized rep");
                            break;
                        case "Email":
                            applicationConsumerEmail = response.jsonPath().getList("data.applicationConsumers.applicationConsumerEmail");
                            assertTrue(createOutboundCorrespondencePage.EmailDestinationForAuthorizedRep.getText().equals(""), "Email destination isnt verified for authorized rep");
                            break;
                        case "Fax":
                            applicationConsumerFax = response.jsonPath().getList("data.applicationConsumers.applicationConsumerPhone");
                            assertTrue(createOutboundCorrespondencePage.FaxDestinationForAuthorizedRep.getText().equals(""), "Fax destination isnt verified for authorized rep");
                            break;
                    }
                }
                createOutboundCorrespondencePage.authorizedRepCheckBox.click();
                break;

            case "Application Member":
                assertEquals((AMAppConsumer.get("consumerFirstName") + " " + AMAppConsumer.get("consumerLastName")), createOutboundCorrespondencePage.appMemberRepName.getText(), "Mismatch in expected Application Member Full Name");
                assertEquals(AMAppConsumer.get("applicationConsumerId"), Integer.parseInt(createOutboundCorrespondencePage.applicationMemberID.getText()), "Mismatch in expected Application Member ID");
                createOutboundCorrespondencePage.applicationMemberCheckBox.click();

                waitFor(3);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            assertTrue(createOutboundCorrespondencePage.MailDestinationForAppMember.getText().equals(""), "Mail destination isnt verified for application member");
                            break;
                        case "Email":
                            assertTrue(createOutboundCorrespondencePage.MailDestinationForAppMember.getText().equals(""), "Email destination isnt verified for application member");
                            break;
                        case "Fax":
                            assertTrue(createOutboundCorrespondencePage.FaxDestinationForAppMember.getText().equals(""), "Fax destination isnt verified for application member");
                            break;
                    }
                }
                createOutboundCorrespondencePage.applicationMemberCheckBox.click();
                break;
        }
    }

    @Then("I choose {string} recipient and other destinations")
    public void i_choose_recipient_and_other_destinations(String ConsumerType, List<String> channelType) {
        waitFor(3);

        switch (ConsumerType) {
            case "Primary Individual":
                createOutboundCorrespondencePage.primaryIndividualCheckBox.click();
                waitFor(1);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            createOutboundCorrespondencePage.MailCheckBoxForPrimaryInd.click();
                            break;
                        case "Email":
                            createOutboundCorrespondencePage.EmailCheckBoxForPrimaryInd.click();
                            break;
                        case "Fax":
                            createOutboundCorrespondencePage.FaxCheckBoxForPrimaryInd.click();
                            break;
                    }
                }
                break;

            case "Application Member":
                createOutboundCorrespondencePage.applicationMemberCheckBox.click();
                scrollToElement(createOutboundCorrespondencePage.FaxCheckBoxForAppMember);
                waitFor(1);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            createOutboundCorrespondencePage.MailCheckBoxForAppMember.click();
                            break;
                        case "Email":
                            createOutboundCorrespondencePage.EmailCheckBoxForAppMember.click();
                            break;
                        case "Fax":
                            createOutboundCorrespondencePage.FaxCheckBoxForAppMember.click();
                            break;
                    }
                }
                break;

            case "Authorized Rep":
                createOutboundCorrespondencePage.authorizedRepCheckBox.click();
                scrollToElement(createOutboundCorrespondencePage.FaxCheckBoxForAuthRep);
                waitFor(1);
                for (String each : channelType) {
                    switch (each) {
                        case "Mail":
                            createOutboundCorrespondencePage.MailCheckBoxForAuthRep.click();
                            break;
                        case "Email":
                            createOutboundCorrespondencePage.EmailCheckBoxForAuthRep.click();
                            break;
                        case "Fax":
                            createOutboundCorrespondencePage.FaxCheckBoxForAuthRep.click();
                            break;
                    }
                }
                break;
        }
    }


}