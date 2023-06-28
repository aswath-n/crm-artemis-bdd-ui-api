package com.maersk.dms.steps;


import com.maersk.crm.pages.crm.CRMCreateGeneralTaskPage;
import com.maersk.crm.pages.crm.CRMTaskManageMyTasksPage;
import com.maersk.crm.pages.crm.CRMWOrkQueuePage;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;
import com.maersk.crm.utilities.data_Module.crm_core_data_module.APIUtilitiesForUIScenarios;
import com.maersk.dms.pages.CorrespondenceDetailsPage;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.pages.OutBoundCorrespondenceDestiPage;

import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.dms.step_definitions.CreateOutboundCorrespondenceRequestStepDefs;
import com.maersk.dms.step_definitions.OutboundCorrespondenceStepDefs;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.testng.Assert;

import java.util.List;
import java.util.Map;


import static com.maersk.crm.utilities.World.generalSave;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateOutboundCorrespondenceRequestSteps extends CRMUtilities implements ApiBase {


    private final ThreadLocal<CreateOutboundCorrespondenceRequestStepDefs> stepdef = ThreadLocal.withInitial(CreateOutboundCorrespondenceRequestStepDefs::new);
    private final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    CorrespondenceDetailsPage correspondenceDetailsPage = new CorrespondenceDetailsPage();
    OutBoundCorrespondenceDestiPage correspondencePage = new OutBoundCorrespondenceDestiPage();
    CRMWOrkQueuePage workQueue = new CRMWOrkQueuePage();
    CRMTaskManageMyTasksPage myTask = new CRMTaskManageMyTasksPage();
    CreateOutboundCorrespondencePage createOutboundCorrespondencePage = new CreateOutboundCorrespondencePage();
    private final ThreadLocal<JsonPath> response = new ThreadLocal<>();
    CRMCreateGeneralTaskPage createGeneralTask = new CRMCreateGeneralTaskPage();


    // send to
    @And("I clicked on Third Party Contact")
    public void i_click_on_third_party_contact() {
        stepdef.get().clickonthirdpartycontact();
    }

    @And("I entered required data in Third party contact page")
    public void i_enter_third_party_contact_details(Map<String, String> dataTable) {
        stepdef.get().enterthirdpartycontactdetails(dataTable);
    }

    @And("I validate Send To section in correpondence request page")
    public void i_validate_SendTo_section(Map<String, String> dataTable) {
        stepdef.get().validateSendToSection(dataTable);
    }

    @And("I validate error messages for Send To section required fields")
    public void i_validate_SendTo_section_errrormessage() {
        stepdef.get().validate_SendTo_fields_errormessages();
    }

    @And("I validate the auto populate of recipient first and lastname")
    public void i_validate_autopolulate_recipient_firstname_lastname() {
        stepdef.get().validate_autopolulate_recipient_firstname_lastname();
    }

    @And("I validate the Regarding section is not displayed")
    public void i_validate_regarding_section() {
        stepdef.get().validate_regarding_section();
    }

    @And("I validate outbound definition type while creating from Third Party Contact")
    public void i_validate_type_section() {
        outCorr.get().clickMenuIconDropDown();
        outCorr.get().clickMenuIconCreateCorrOpt();
        outCorr.get().verifyOnCreateOutboundCorrespondenceSection();
        stepdef.get().validate_type_dropdown_values();
    }

    @And("I will capture all active correspondences for the project")
    public void storing_all_active_correspondances() {
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getallactivecorrespondances();
        stepdef.get().capture_non_regarding_type_values(response);

    }

    @And("I enter data in send To section")
    public void i_enter_data_in_sendto_section(Map<String, String> dataTable) {
        stepdef.get().enterdatainSendToSection(dataTable);
    }

    @Given("I have selected an Outbound Correspondence through the UI {string} as type")
    public void that_I_have_created_an_Outbound_Correspondence_through_the_UI(String obtype) {
        outCorr.get().clickMenuIconDropDown();
        outCorr.get().clickMenuIconCreateCorrOpt();
        outCorr.get().verifyOnCreateOutboundCorrespondenceSection();
        stepdef.get().selectOutboundCorrespondencetype(obtype);
    }

    @Given("{string} sections are hidden")
    public void sections_are_hidden(String section) {
        switch (section) {
            case "Regarding":
                stepdef.get().verifyRegardingIsNotDisplayed();
                break;
            case "Body Data":
                stepdef.get().verifyBodyDataIsNotDisplayed();
                break;
        }
    }

    @Given("I choose correspondence type which was created")
    public void i_choose_correspondence_type_which_was_created() {
        stepdef.get().enterOutboundCorrespondenceType(World.save.get().get("idAndNameOfOCD"));
    }

    @Then("Body Data section displays with the body data fields editable")
    public void body_Data_section_displays_with_the_body_data_fields_editable() {
        String expectedFieldName = World.save.get().get("dataElementName");
        waitFor(1);
        stepdef.get().VerifyBodyDataElementHeading();
        if(expectedFieldName==null)
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//label[contains(text(), 'FIRST NAME')]")).isEnabled());
        else
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//label[contains(text(), '" + expectedFieldName + "')]")).isEnabled());


    }

    @Then("{string} will display and be un-editable")
    public void will_display_and_be_un_editable(String key) {
        String expectedConsumerId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].consumers[0].consumerId") + "";
        String expectedCaseId = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].cases.caseId") + "";
        waitFor(1);
        switch (key) {
            case "Case ID":
                Assert.assertEquals(createOutboundCorrespondencePage.caseID.getText(), expectedCaseId);
                break;
            case "Consumer ID":
                Assert.assertEquals(createOutboundCorrespondencePage.consumerID.getText(), expectedConsumerId);
                break;
        }
    }

    @Then("Consumer ID's dropdown will include the active Consumers associated with the Case")
    public void dropdown_will_include_the_active_Consumers_associated_with_the_Case() {
        // to upper case both first and last name, in case name starting with lowercase
        String expectedFirstName = APIUtilitiesForUIScenarios.consumerFirstName.get().substring(0, 1).toUpperCase() + APIUtilitiesForUIScenarios.consumerFirstName.get().substring(1);
        String expectedLastName = APIUtilitiesForUIScenarios.consumerLastName.get().substring(0, 1).toUpperCase() + APIUtilitiesForUIScenarios.consumerLastName.get().substring(1);
        String expectedConsumerName = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.get("object.result[0].consumers[0].consumerId") + "-" + expectedFirstName + " " + expectedLastName;
        waitFor(1);
        createOutboundCorrespondencePage.dropdownArrowDown.click();
        System.out.println("Dropdown arrow clicked");
        waitFor(1);
        String actualFullName = createOutboundCorrespondencePage.dropdownListOfConsumers.get(0).getText();
        Assert.assertEquals(actualFullName, expectedConsumerName, "Dropdown values verification failed");
    }

    @Given("I scroll down and check the first recipient")
    public void i_scroll_down_and_check_the_first_recipient() {
        waitFor(3);
        new OutboundCorrespondenceStepDefs().selectRandomRecipientUI();
    }

    @Then("I should be navigated to View Outbound Correspondence details")
    public void i_should_be_navigated_to_View_Outbound_Correspondence_details(String expectedFullAddress) {
        waitForVisibility(correspondenceDetailsPage.correspondenceType, 40);
        System.out.println("page title : " + Driver.getDriver().getTitle());
        Assert.assertEquals(Driver.getDriver().getTitle(), "View Outbound Correspondence Details");
        waitFor(1);
        if (expectedFullAddress.equalsIgnoreCase("null")) {
            Assert.assertEquals(World.save.get().get("idAndNameOfOCD"), correspondenceDetailsPage.correspondenceType.getText(), "Correspondence type verification failed");
        } else {
            Assert.assertEquals(expectedFullAddress, correspondenceDetailsPage.correspondenceNotificationsDestination.getText(), "Destination verification failed");
            World.generalSave.get().put("NOTIFICATIONID", correspondenceDetailsPage.listOfNIDNotifications.get(0).getText());
        }
        World.generalSave.get().put("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get(), correspondenceDetailsPage.corIdValue.getText());
    }

    @And("I select mail opt in checkbox for the first recipient and click other")
    public void i_select_mail_opt_in_checkbox_for_the_first_recipient_and_click_other() {
        List<WebElement> lmail = correspondencePage.CheckMail;
        lmail.get(0).click();
        scrollDown();
        waitFor(3);
        List<WebElement> list = correspondencePage.OpenMailList;
        list.get(0).click();
        waitFor(3);
        correspondencePage.SelectOther.click();
        waitFor(2);
    }

    @Given("I select mail opt in checkbox for the first recipient and click existing address")
    public void i_select_mail_opt_in_checkbox_for_the_first_recipient_and_click_existing_address() {
        waitFor(2);
        List<WebElement> lmail = correspondencePage.CheckMail;
        lmail.get(0).click();
        scrollDown();
        waitFor(2);
        List<WebElement> list = correspondencePage.OpenMailList;
        list.get(0).click();
        waitFor(2);
        World.save.get().put("destinationMailAddress", createOutboundCorrespondencePage.mailDestinationValue.getAttribute("data-value"));
        System.out.println(World.save.get().get("destinationMailAddress"));
        createOutboundCorrespondencePage.mailDestinationValue.click();
    }

    @Then("I verify Outbound Correspondence Successfully saved")
    public void i_verify_Outbound_Correspondence_Successfully_saved() {
            waitFor(10);
            WebElement element = correspondencePage.SaveButton;
            jsClick(element);
            waitForVisibility(correspondencePage.SuccessMessage, 40);
            String message = correspondencePage.SuccessMessage.getText();
            System.out.println("success Message : " + message);
            Assert.assertEquals(message, "SUCCESS MESSAGE");
    }

    @And("I will search for the task with status {string}")
    public void i_will_search_for_the_task_with_status(String status) {
        int size = 1;
        boolean containStatusInProgress = true;
        if (workQueue.allRowTaskID.size() > 0 && workQueue.workQueueColumns.size() > 0) {
            if (isElementDisplayed(myTask.lnkArrowForward)) {
                click(myTask.lnkArrowForward);
                waitFor(1);
                size = Integer.parseInt(myTask.lnkPageNations.get(myTask.lnkPageNations.size() - 1).getText());
                click(myTask.lnkArrowBack);
                waitFor(1);
            }

            l1:
            for (int i = 1; i <= size; i++) {
                for (int j = 0; j < myTask.taskListRows.size(); j++) {
                    waitFor(1);
                    if (myTask.taskStatuses.get(j).getText().equals(status)) {
                        assertTrue(isElementDisplayed(myTask.taskInitiateButtons.get(j)), "Initiate button not displayed");
                        waitFor(1);
                        click(myTask.taskInitiateButtons.get(j));
                        waitFor(1);
                        containStatusInProgress = false;
                        break l1;
                    }
                }
                if (i != size) {
                    click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (i + 1) + "']")));
                    waitFor(1);
                }
            }
            if (containStatusInProgress) {
                for (int i = 0; i < size; i++) {
                    if (myTask.taskInitiateButtons.size() > 0) {
                        waitFor(2);
                        click(myTask.taskInitiateButtons.get(myTask.taskInitiateButtons.size() - 1));
                        waitFor(2);
                        break;
                    } else {
                        click(Driver.getDriver().findElement(By.xpath("//ul[@class='pagination']/li/a[text()='" + (size - 1 - i) + "']")));
                    }
                }
            }
        }
    }


    // body data elements

    @And("I validate Body Data Elements heading")
    public void i_validate_Body_Data_elements_heading() {
        stepdef.get().VerifyBodyDataElementHeading();
    }

    @Then("I validate Body Data elements labels")
    public void i_validate_Body_Data_elements_labels(List<String> bd) {
        stepdef.get().VerifyBodyDataElementLabels(bd);
    }

    @Then("I validate Body Data elements data types and maxlength")
    public void i_validate_Body_Data_elements_datatypes() {
        stepdef.get().VerifyBodyDataElementDatatypes();
    }

    @Then("I validate the error messages for the required body data elements")
    public void i_validate_Body_Data_elements_error_message(List<String> errormsgs) {
        stepdef.get().VerifyBodyDataElementerrormessage(errormsgs);
    }

    @Then("I Validate {string} Body data elements multiple occurences")
    public void i_validate_multiple_occurences_Body_Data_elements(String noofmultiinstances) {
        stepdef.get().VerifymultipleinstancesofBodyDataElement(Integer.parseInt(noofmultiinstances));
    }

    @Then("I Validate {string} Body data elements multiple deletions")
    public void i_validate_multiple_deletions_Body_Data_elements(String noofmultiinstances) {
        stepdef.get().VerifydeletionofmultipleinstancesofBodyDataElement(Integer.parseInt(noofmultiinstances));
    }


    @And("I enter data in body data elements")
    public void i_enter_data_in__Body_Data_elements_() {
        stepdef.get().enterBodyDataElementvalues();
    }

    @Then("I validate Cancel functionality")
    public void i_validate_Body_Data_elements_cancel_fuctionality() {
        stepdef.get().VerifyBodyDataElementcancelfunctionality();
    }

    @Then("I validate Save functionality")
    public void i_validate_Body_Data_elements_save_fuctionality() {
        stepdef.get().VerifyBodyDataElementSavefunctionality();
    }


    @Then("I Validate Regarding section is defaulted to the Consumer ID of that Consumer Profile and is not editable")
    public void i_validate_RegardingSection_Consumer_Profile() {
        String consumer_ID = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        stepdef.get().consumerprofile_RegardingSection(consumer_ID);
    }

    @Then("I validate Links section will display and auto-populate with a row showing a link to that Consumer")
    public void i_validate_LinkSection_Consumer_Profile() {
        String consumer_ID = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        stepdef.get().consumerprofile_LinkSection(consumer_ID);
    }

    @Then("I validate warning message for consumer link")
    public void i_validate_warningmessage_Consumer_Profile() {
        String consumer_ID = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        stepdef.get().consumerprofile_LinkSection_warningmessage(consumer_ID);
    }

    @Then("I validate navigation to Consumer Profile Demographic Info page")
    public void i_validate_navigation_Consumer_Profile_Demographicpage() {
        stepdef.get().navigation_ConsumerProfile_Demographicpage();
    }

    @Then("I Validate the only one potential Recipient in context of consumer")
    public void i_validate_only_recipient_consumer_profile() {
        String consumer_ID = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        String consumer_name = APIUtilitiesForUIScenarios.getInstance().getConsumerFirstName() + " " + APIUtilitiesForUIScenarios.getInstance().getConsumerLastName();
        stepdef.get().consumerprofile_only_recipient(consumer_ID, consumer_name);
    }

    @Then("I Validate default Channel and Destination")
    public void i_validate_channel_destination() {
        String consumer_mailing_address = "1 Street, 1 Street, Alpharetta, Iowa, 12345";
        stepdef.get().consumerprofile_channel_destination(consumer_mailing_address);
    }

    @Then("I Validate empty recipient value")
    public void i_validate_empty_recipient() {
        String consumer_ID = APIUtilitiesForUIScenarios.getInstance().getConsumerId();
        stepdef.get().empty_recipient(consumer_ID);
    }

    @Then("I fill mandatory fields and save correspondence")
    public void i_fill_mandatory_fields_and_save_correspondence() {
        createOutboundCorrespondencePage.sendToCheckBox.click();
        createOutboundCorrespondencePage.checkBoxEmail.click();
        waitFor(2);
        selectDropDown(createOutboundCorrespondencePage.emailDestination, "Other");
        createOutboundCorrespondencePage.saveButton.click();
        waitFor(15);
    }

    @And("I send the Outbound Correspondence Definition to the server with random valid data and required Keys")
    public void i_send_the_Outbound_Correspondence_Definition_to_the_server_with_random_valid_data_and_channels(List<String> dataTable) {
        Map<String, String> mapOfDefIdAndNames = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().createOCDefinitionWithChannels(dataTable);
        World.save.get().put("idAndNameOfOCD", mapOfDefIdAndNames.get("corName") + " - " + mapOfDefIdAndNames.get("mmsId"));
    }

    @Given("I add channels to created Outbound Correspondence Definition")
    public void i_add_channels_to_created_Outbound_Correspondence_Definition(List<String> listOfChannels) {
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().addChannelsToDefinition(listOfChannels);
    }

    @Given("I add channels to Outbound Correspondence Definition with mmsCode {string}")
    public void i_add_channels_to_created_Outbound_Correspondence_Definition(String corrId, List<String> listOfChannels) {
        API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().addChannelsToCorrDefinition(listOfChannels, corrId);
    }

    @Given("I verify label, format and behavior for")
    public void i_verify_label_format_and_behavior_for(List<String> dataTable) {
        for (String value : dataTable) {
            switch (value) {
                case "Type":
                    stepdef.get().verifyTypeFormatAndBehaviors();
                    break;
                case "Language":
                    stepdef.get().verifyLanguageFormatAndBehaviors();
                    break;
                case "Regarding":
                    stepdef.get().verifyRegardingLabel();
                    break;
                case "Case ID":
                    will_display_and_be_un_editable(value);
                    stepdef.get().verifyCaseIdFormatAndBehaviors();
                    break;
                case "Consumer ID":
                    stepdef.get().verifyConsumerIdFormatAndBehaviors();
                    break;
                case "Send to":
                    stepdef.get().verifySendToLabel();
                    break;
                case "Recipients":
                    stepdef.get().verifyRecipientsInfo();
                    break;
            }
        }
    }

    @Given("I select any value from Consumer\\(s) dropdown")
    public void i_select_any_value_from_Consumer_s_dropdown() {
        stepdef.get().selectAnyValueFromConsumerDropdown();
    }

    @Given("I click on the first case ID and consumer search result")
    public void i_click_on_the_first_case_ID_and_consumer_search_result() {
        BrowserUtils.waitFor(3);
        createOutboundCorrespondencePage.firstCaseIDFromSearch.click();
        BrowserUtils.waitFor(3);
    }

    @Then("I capture title of the page")
    public void i_capture_title_of_the_page() {
        World.save.get().put("previewsPageTitle", Driver.getDriver().getTitle());
    }

    @Then("I return to the page from where I navigated to create OC")
    public void i_return_to_the_page_from_where_I_navigated() {
        waitFor(3);
        if (World.save.get().get("previewsPageTitle").equalsIgnoreCase("Active General Contact")) {
            Assert.assertEquals(Driver.getDriver().getTitle(), "Active Linked General Contact");
        } else {
            Assert.assertEquals(Driver.getDriver().getTitle(), World.save.get().get("previewsPageTitle"));
        }
    }

    @Then("I expand first outbound correspondence")
    public void i_expand_first_outbound_correspondence() {
        stepdef.get().expandFirstOutCor();
    }

    @Then("I verify system captured Created On and Created By")
    public void i_verify_system_captured_Created_On_and_Created_By() {
        stepdef.get().verifyCreatedOnOutCor();
        stepdef.get().verifyCreatedByOutCor();
    }

    @And("I navigate away by clicking the back arrow on OC request")
    public void i_navigate_away_by_clicking_the_back_arrow_on_OC_request() {
        stepdef.get().clickBackArrow();
    }

    @Then("I verify navigation warning message displayed on OC request")
    public void i_verify_navigation_warning_message_displayed_on_OC_request() {
        stepdef.get().verifyWarningMessage();
    }

    @Then("I navigated back to OC request page")
    public void i_navigated_back_to_OC_request_page() {
        stepdef.get().verifyOCRequestTitle();
    }

    @And("I click Cancel button on OC request")
    public void i_click_Cancel_button_on_OC_request() {
        stepdef.get().clickCancelButton();
    }

    @And("I click on {string} button on warning message OC request")
    public void i_click_on_button_on_warning_message_OC_request(String value) {
        stepdef.get().clickOnWarningMessageButton(value);
    }

    @Given("I should see an indicator appear in the center of the screen")
    public void i_should_see_an_indicator_appear_in_the_center_of_the_screen() {
        waitForVisibility(createOutboundCorrespondencePage.loadingIndicator,5);
        Assert.assertTrue(stepdef.get().isLoadingCircleDisplayed(), "Loading indicator not appears verification failed");
    }

    @Given("I verify Outbound Correspondence Success Message")
    public void i_verify_Outbound_Correspondence_Success_Message() {
        waitForVisibility(correspondencePage.SuccessMessage, 40);
        String message = correspondencePage.SuccessMessage.getText();
        System.out.println("success Message : " + message);
        Assert.assertEquals(message, "SUCCESS MESSAGE");
    }

    @Then("I should not see an indicator anymore")
    public void i_should_not_see_an_indicator_anymore() {
        waitFor(2);
        Assert.assertFalse(stepdef.get().isLoadingCircleDisplayed(), "Loading indicator appears verification failed");
    }

    @Given("I should not see the active contact screen components")
    public void i_should_not_see_the_active_contact_screen_components() {
        Assert.assertFalse(stepdef.get().verifyActiveContactScreenDisplayed(), "The active contact screen displayed, verification failed");
    }

    @Given("I should not see the active task screen components")
    public void i_should_not_see_the_active_task_screen_components() {
        Assert.assertFalse(stepdef.get().verifyActiveTaskScreenDisplayed(), "The active task screen displayed, verification failed");
    }

    @Given("I have selected {string} as a type")
    public void i_have_selected_as_a_type(String type) {
        stepdef.get().selectOutboundCorrespondencetype(type);
        World.save.get().put("idAndNameOfOCD",type);
    }

    @Then("I verify {string} message in Outbound Correspondence details page")
    public void i_verify_Correspondence_assembled_message(String message) {
        waitForVisibility(correspondencePage.SuccessMessage, 30);
        String actualmessage = correspondencePage.SuccessMessage.getText();

        switch (message.toLowerCase()) {
            case "correspondence assembled successfully":
                Assert.assertEquals(actualmessage, "SUCCESS MESSAGE");
                Assert.assertTrue(correspondencePage.messagetext(message));
                break;
            case "error assembling correspondence":
                Assert.assertEquals(actualmessage, "ERROR MESSAGE");
                Assert.assertTrue(correspondencePage.messagetext(message));
                break;

        }
    }

    @Given("I have selected language for Outbound Correspondence type through the UI as {string}")
    public void that_I_have_selected_language_for_Outbound_Correspondence_through_the_UI(String language) {
        stepdef.get().selectOutboundCorrespondencelanguage(language);
    }

    @Given("I verify the following fields is displayed on create OB correspondence request page")
    public void i_verify_the_following_fields_is_displayed_on_create_OB_correspondence_request_page(Map<String, String> dataTable) {
        stepdef.get().verifyGivenValuesDisplayed(dataTable);
    }

    @Then("I validate request which was send from UI contains following values")
    public void i_validate_request_which_was_send_from_UI_contains_following_values(Map<String, String> dataTable) {
        stepdef.get().validateUIRequestContainsValues(dataTable);
    }

    @Given("I retrieve the Outbound Correspondence which was created")
    public void i_retrieve_the_Outbound_Correspondence_which_was_created() {
        synchronized (response){
            response.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCEID"))));
        }
    }

    @Given("I validate Outbound Correspondence Get request contains following values in anchor")
    public void i_validate_Outbound_Correspondence_Get_request_contains_following_values_in_anchor(Map<String, String> dataTable) {
        stepdef.get().verifyOutboundCorrespondenceResponseValues(dataTable, response.get());
    }

    @Given("I verify the Required fields values are displayed on create OB correspondence request page")
    public void i_verify_the_required_fields_is_displayed_on_create_OB_correspondence_request_page(Map<String, String> dataTable) {
        stepdef.get().verifyRequiredValuesDisplayed(dataTable);
    }

    @Then("I select random value from Action Taken drop down from task details")
    public void verifyCActionTakenDropDownValues() {
        waitForVisibility(createGeneralTask.actionTaken, 5);
        createGeneralTask.actionTaken.click();
        waitForVisibility(createGeneralTask.actionTakenDrDnValues.get(0), 5);
        createGeneralTask.actionTakenDrDnValues.get(0).click();
        createGeneralTask.actionTakenDrDnValues.get(0).sendKeys(Keys.ESCAPE);
        //new Actions(Driver.getDriver()).moveToElement(Driver.getDriver().findElement(By.xpath("//ul/li[contains(text(), '" + status + "')]"))).click().build().perform();
       // new Actions(Driver.getDriver()).moveToElement( createGeneralTask.actionTakenDrDnValues.get(0)).click().build().perform();
        waitFor(3);
    }


    @And("I select default send to checkbox")
    public void iSelectDefaultSendToCheckbox() {
        stepdef.get().selectDefaultSendToCheckbox();
    }

    @And("I verify usable channels are selected for default consumers")
    public void iVerifyUsableChannelsAreSelectedForDefaultConsumers() {
        stepdef.get().verifyUsableChannelsAreSelectedForDefaultConsumers();
    }

    @And("I validate Body Data Elements structure in OB correspondence details page")
    public void i_validate_Body_Data_elements_structure() {
        stepdef.get().VerifyBodyDataElementStructure();
    }
}
