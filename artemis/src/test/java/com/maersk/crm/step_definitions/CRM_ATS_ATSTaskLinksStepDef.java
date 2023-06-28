package com.maersk.crm.step_definitions;

import com.maersk.crm.api_step_definitions.APIATSSendEventAndCreateLinksController;
import com.maersk.crm.api_step_definitions.APIContactRecordController;
import com.maersk.dms.pages.ViewOutboundCorrespondenceDetailsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.google.gson.JsonObject;
import com.maersk.crm.api_step_definitions.APIATSApplicationController;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.*;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.maersk.crm.api_step_definitions.APIATSCorrespondenceController.createdOutboundCorrespondenceID;
import static org.testng.Assert.*;
import static com.maersk.crm.api_step_definitions.APIATSApplicationController.applicationIdAPI;
import static org.testng.Assert.*;

public class CRM_ATS_ATSTaskLinksStepDef extends CRMUtilities implements ApiBase {
    CRMCreateApplicationPage createApplication = new CRMCreateApplicationPage();
    APIATSSendEventAndCreateLinksController sendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    CRMTaskSearchPage taskSearchPage = new CRMTaskSearchPage();
    CRMContactRecordDashboardPage contactRecordDashboardPage = new CRMContactRecordDashboardPage();

    private static String firstIDonLinkTab = "";
    APIATSApplicationController apiatsApplicationController = new APIATSApplicationController();
    CRM_CreateApplicationStepDef createApplicationStepDef = new CRM_CreateApplicationStepDef();
    CRMApplicationTrackingPage trackingPage = new CRMApplicationTrackingPage();
    CRMCreateGeneralTaskPage taskPage = new CRMCreateGeneralTaskPage();
    CRMViewTaskPage viewTaskPage = new CRMViewTaskPage();
    CRMTaskManageMyTasksPage manageMyTasksPage = new CRMTaskManageMyTasksPage();
    CRM_MemberMatchingStepDef memberMatchingStepDef = new CRM_MemberMatchingStepDef();
    APIATSSendEventAndCreateLinksController apiatsSendEventAndCreateLinksController = new APIATSSendEventAndCreateLinksController();
    Map<String, String> appLinkIds = new HashMap<>();
    ViewOutboundCorrespondenceDetailsPage correspondenceDetailsPage = new ViewOutboundCorrespondenceDetailsPage();
    public static String caseIdUI = "";

    @When("I searched ats task using taskId from {string}")
    public void i_searched_ats_task_using_taskId_from(String type) {
        waitFor(1);
        waitForVisibility(taskSearchPage.taskId, 3);
        switch (type) {
            case "Inbound Correspondence":
                System.out.println("Task id from inb correspondence on Search: " + sendEventAndCreateLinksController.inbCreatedTaskId);
                taskSearchPage.taskId.sendKeys(sendEventAndCreateLinksController.inbCreatedTaskId.get());
                break;

            case "API":
                //        System.out.println("Task id from API Call on Search: "+apiatsApplicationController.apiCreatedTaskId);
                System.out.println("Task id from world class " + World.generalList.get().get(0));
                String taskIdInPut = World.generalList.get().get(0);
                taskSearchPage.taskId.sendKeys(taskIdInPut);
                break;
            case "UI - Research":
                System.out.println("Task if from UI Research: " + appLinkIds.get("Member Matching Research"));
                taskSearchPage.taskId.sendKeys(appLinkIds.get("Member Matching Research"));
                break;
        }
    }

    @Then("I verified that user is navigated to {string} page")
    public void i_verified_that_user_is_navigated_to_page(String pageType) {
        waitFor(2);
        System.out.println("Title here  " + Driver.getDriver().getCurrentUrl().toString());
        if (pageType.equals("Member Matching")) {
            assertTrue(Driver.getDriver().getCurrentUrl().toString().contains("memberMatching"));
        } else if (pageType.equals("View Task")) {
            assertTrue(Driver.getDriver().getCurrentUrl().toString().contains("view-task"));
            assertTrue(Driver.getDriver().getTitle().equals("View Task"));
        } else if (pageType.equals("Application")) {
            assertTrue(Driver.getDriver().getCurrentUrl().toString().contains("application"));
            createApplicationStepDef.i_verify_Application_Page_header_is_displayed("Medical Assistance");
        }
    }

    @When("I verified that task type is {string}")
    public void i_verified_that_task_type_is(String taskType) {
        waitFor(1);
        System.out.println("Type is: " + taskSearchPage.taskTypes.get(0).getText().toString());
        assertTrue(taskSearchPage.taskTypes.get(0).getText().toString().equals(taskType), "Task type isnt verified");
    }

    @And("I verify that links section does not include {string} task")
    public void i_click_caseId_ATS_Links_section(String taskType) {
        waitFor(3);
        boolean hasTaskType = false;
        Driver.getDriver().findElements(By.xpath("//i[.='link']//following::table[1]/tbody[1]/tr/td[4]"));
        for (int i = 0; i < trackingPage.linkSize.size(); i++) {
            if (trackingPage.linkNames.get(i).getText().equalsIgnoreCase(taskType)) {
                hasTaskType = true;
            }
        }
        Assert.assertFalse(hasTaskType, taskType + " is present in the links section");
    }

    @Then("I verify refresh button is displayed and clicked on it")
    public void i_verify_refresh_button_is_displayed_and_clicked_on_it() {
        assertTrue(trackingPage.refreshButton.isDisplayed(), "Refresh button isnt displayed");
        jsClick(trackingPage.refreshButton);
        waitFor(4);
    }

    @Then("I verify application links component has all the business object linked : {string}")
    public void i_verify_application_links_component_has_all_the_business_object_linked(String expectedObjects, Map<String, String> taskTypeTable) {
        waitFor(2);
        scrollDownUsingActions(5);
        List<String> expectedLinksObjects = Arrays.stream(expectedObjects.split(",")).sorted().collect(Collectors.toList());
        List<String> actualLinkObjects = new ArrayList<>(Collections.emptyList());

        for (int i = 0; i < trackingPage.linkSize.size(); i++) {
            String linkType;
            linkType = trackingPage.linkNames.get(i).getText();
            actualLinkObjects.add(linkType);

            switch (linkType) {
                case "Task":
                    scrollDown();
                    waitFor(3);
                    assertTrue(trackingPage.taskIcon.isDisplayed(), "Task Icon isn't displayed on Links Component");
                    assertTrue(trackingPage.linkNames.get(i).getText().equals("Task"), "Task link name isnt verified on Links Component");
                    if (trackingPage.linkTypes.get(i).getText().equalsIgnoreCase("MEMBER MATCHING RESEARCH")) {
                        assertTrue(trackingPage.linkTypes.get(i).getText().equals(taskTypeTable.get("ResearchTask")), "Task type isnt verified on Links Component");
                        assertTrue(trackingPage.linkStatus.get(i).getText().equals(taskTypeTable.get("ResearchStatus")), "Status isnt verified");
                        assertTrue(trackingPage.linkIDs.get(i).getText().chars().allMatch(Character::isDigit), "Task id isn't verified on Links Component for UI.");
                        appLinkIds.put(trackingPage.linkTypes.get(i).getText(), trackingPage.linkIDs.get(i).getText());
                    } else {
                        if (taskTypeTable.get("Source").equalsIgnoreCase("API")) {
                            assertTrue(trackingPage.linkIDs.get(i).getText().equals(World.generalList.get().get(0)), "Task id isn't verified on Links Component for API. Expected: " + World.generalList.get().get(0) + " but found: " + trackingPage.linkIDs.get(i).getText());
                        } else if (taskTypeTable.get("Source").equalsIgnoreCase("UI")) {
                            assertTrue(trackingPage.linkIDs.get(i).getText().chars().allMatch(Character::isDigit), "Task id isn't verified on Links Component for API");
                        } else {
                            assertTrue(trackingPage.linkIDs.get(i).getText().equals(sendEventAndCreateLinksController.inbCreatedTaskId), "Task id isnt verified on Links Component for ECMS. Expected:  " + sendEventAndCreateLinksController.inbCreatedTaskId + " but found: " + trackingPage.linkIDs.get(i).getText());
                        }
                        assertTrue(trackingPage.linkTypes.get(i).getText().equals(taskTypeTable.get("Task").trim()), "Task type isnt verified on Links Component");
                        assertTrue(trackingPage.linkStatus.get(i).getText().equals(taskTypeTable.get("Status")), "Status isnt verified");
                    }
                    assertTrue(trackingPage.linkStatusDates.get(i).getText().equals(taskPage.projectDateAtHeader.getText()), "Status date isnt verified on Links Componenet");
                    System.out.println("Task verification is completed");
                    break;
                case "Inbound Correspondence":
                    assertTrue(trackingPage.correspondenceIcon.isDisplayed(), "Correspondence Icon isn't displayed on Links Component");
                    assertTrue(trackingPage.linkIDs.get(i).getText().equals(sendEventAndCreateLinksController.documentId), "Inbound document id isnt verified on Links Component");
                    assertTrue(trackingPage.linkNames.get(i).getText().equals("Inbound Correspondence"), "Inb Correspondence link name isnt verified on Links Component");
                    assertTrue(trackingPage.linkTypes.get(i).getText().equals("maersk Application"), "Inb correspondence type isnt verified on Links Component");
                    assertTrue(trackingPage.linkStatusDates.get(i).getText().equals(taskPage.projectDateAtHeader.getText()), "Status date isnt verified on Links Component");
                    assertTrue(trackingPage.linkStatus.get(i).getText().equals("RECEIVED"), "Status isnt verified");
                    System.out.println("Inbound correspondence verification is completed");
                    break;
                case "Outbound Correspondence":
                    assertTrue(trackingPage.correspondenceIcon.isDisplayed(), "Correspondence Icon isn't displayed on Links Component");
                    assertTrue(trackingPage.linkIDs.get(i).getText().equals(createdOutboundCorrespondenceID), "Outbound Correspondence  id isn't verified on Links Component");
                    assertTrue(trackingPage.linkNames.get(i).getText().equals("Outbound Correspondence"), "Outbound Correspondence link name isn't verified on Links Component");
                    assertTrue(trackingPage.linkTypes.get(i).getText().equals("AppId Application ID Required"), "Outbound correspondence type isn't verified on Links Component");
                    assertTrue(trackingPage.linkStatusDates.get(i).getText().equals(taskPage.projectDateAtHeader.getText()), "Status date isn't verified on Links Component");
                    assertTrue(trackingPage.linkStatus.get(i).getText().equals("Requested"), "Status isn't verified");
                    System.out.println("Outbound correspondence verification is completed");
                    break;
                case "Application":
                    assertTrue(trackingPage.linkIDs.get(i).getText().equals(memberMatchingStepDef.linkedAppID), "Application id isnt verified on Links Component");
                    assertTrue(trackingPage.linkNames.get(i).getText().equals("Application"), "Application link name isnt verified on Links Component");
                    assertTrue(trackingPage.linkTypes.get(i).getText().equals("Medical Assistance New"), "Application type isnt verified on Links Component");
                    assertTrue(trackingPage.linkStatusDates.get(i).getText().equals(taskPage.projectDateAtHeader.getText()), "Status date isnt verified on Links Component for Application");
                    assertTrue(trackingPage.linkStatus.get(i).getText().equalsIgnoreCase(memberMatchingStepDef.linkedAppStatus.get()), "Status isnt verified");
                    System.out.println("Application link verification is completed");
                    break;
                case "Case":
                    if (!taskTypeTable.get("CaseSource").equalsIgnoreCase("UI")) {
                        assertTrue(trackingPage.linkIDs.get(i).getText().equals(String.valueOf(APIContactRecordController.caseID.get())), "Case id isn't verified on Links .Expected:" + APIContactRecordController.caseID.get() + "but found : " + trackingPage.linkIDs.get(i).getText());
                    } else {
                        caseIdUI = trackingPage.linkIDs.get(i).getText();
                    }
                    assertTrue(trackingPage.linkNames.get(i).getText().equals("Case"), "Case  isn't verified on Links Component");
                    System.out.println("Case link verification is completed");
                    break;
                default:
                    fail("Incorrect switch argument " + linkType);
            }
        }
        assertEquals(actualLinkObjects.stream().sorted().collect(Collectors.toList()), expectedLinksObjects, "Link section contains incorrect objects");
        assertEquals(actualLinkObjects.size(), expectedLinksObjects.size(), "Link section contains incorrect objects");
    }

    @Then("I verify action taken value as {string} on View Task Page")
    public void i_verify_action_taken_value_as_on_View_Task_Page(String actionTaken) {
        waitFor(5);
        assertTrue(manageMyTasksPage.txtActionTaken.getText().equals(actionTaken), "Action taken value isnt captured");
    }

    @Then("I click id of {string} in ATS Links section")
    public void i_click_id_of_in_ATS_Links_section(String name, Map<String, String> typeTable) {
        waitFor(3);
        Driver.getDriver().findElements(By.xpath("//i[.='link']//following::table[1]/tbody[1]/tr/td[4]"));
        for (int i = 0; i < trackingPage.linkSize.size(); i++) {
            if (trackingPage.linkNames.get(i).getText().equalsIgnoreCase(name) && trackingPage.linkTypes.get(i).getText().equals(typeTable.get("Type"))) {
                System.out.println("Name: " + trackingPage.linkNames.get(i));
                System.out.println("Type from scenario: " + typeTable.get("Type"));
                jsClick(trackingPage.linkIDs.get(i));
            }
        }
        waitFor(2);
    }

    @And("I click on the previously created Case Id in ATS Links section")
    public void i_click_caseId_ATS_Links_section() {
        waitFor(3);
        Driver.getDriver().findElements(By.xpath("//i[.='link']//following::table[1]/tbody[1]/tr/td[4]"));
        for (int i = 0; i < trackingPage.linkSize.size(); i++) {
            if (trackingPage.linkNames.get(i).getText().equalsIgnoreCase("Case") && trackingPage.linkIDs.get(i).getText().equals(World.generalSave.get().get("caseConsumerCaseId").toString())) {
                System.out.println("Name: " + trackingPage.linkNames.get(i));
                System.out.println("Case Id from scenario: " + World.generalSave.get().get("caseConsumerCaseId").toString());
                jsClick(trackingPage.linkIDs.get(i));
            }
        }
        waitFor(2);
    }

    @And("I verify The Link Header is displayed on Application Tracking Tab")
    public void iVerifyTheLinkHeaderIsDisplayedOnApplicationTrackingTab() {
        Assert.assertTrue(isElementDisplayed(trackingPage.linkHeader), "Link Header isn't displayed on Application Tracking Page");
    }

    @And("I verify the following fields for Links Tab")
    public void iVerifyTheFollowingFieldsForLinksTab(List<Map<String, String>> verifyValuesList) {
        for (int i = 0; i < verifyValuesList.size(); i++) {
            for (String verifyValues : verifyValuesList.get(i).keySet()) {
                switch (verifyValues.toUpperCase()) {
                    case "ICON":
                        if (verifyValuesList.get(i).get("ICON").equalsIgnoreCase("APPLICATION")) {
                            assertTrue(trackingPage.linkApplicationIconList.get(i).isDisplayed(), "Application Icon is not displayed on Link Panel");
                        }
                        break;
                    case "ID":
                        int applicationID = Integer.parseInt(applicationIdAPI.get());
                        int IDDifference = Integer.parseInt(verifyValuesList.get(i).get("ID"));
                        assertEquals(trackingPage.linkTableData.get((i * 7) + 1).getText(), String.valueOf(applicationID + IDDifference), "Application ID is mismatched on Link Panel");
                        break;
                    case "NAME":
                        assertEquals(trackingPage.linkTableData.get((i * 7) + 2).getText(), verifyValuesList.get(i).get("NAME"), "Name is mismatched on Link Panel");
                        break;
                    case "TYPE":
                        assertEquals(trackingPage.linkTableData.get((i * 7) + 3).getText(), verifyValuesList.get(i).get("TYPE"), "Type is mismatched on Link Panel");
                        break;
                    case "STATUS DATE":
                        if (verifyValuesList.get(i).get("STATUS DATE").equalsIgnoreCase("CURRENT DATE")) {
                            assertEquals(trackingPage.linkTableData.get((i * 7) + 4).getText(), getCurrentDate(), "Status Date is mismatched on Link Panel");
                        } else {
                            assertEquals(trackingPage.linkTableData.get((i * 7) + 4).getText(), verifyValuesList.get(i).get("STATUS DATE"), "Status Date is mismatched on Link Panel");
                        }
                        break;
                    case "STATUS":
                        assertEquals(trackingPage.linkTableData.get((i * 7) + 5).getText(), verifyValuesList.get(i).get("STATUS"), "Status is mismatched on Link Panel");
                        break;
                    default:
                        fail("Column Name mismatched");
                }
            }
            System.out.println(i + 1 + ". linked entity is verified");
        }
    }

    @Then("I verify I navigated to {string} after clicking linked Entity")
    public void iVerifyINavigatedToAfterClickingLinkedEntity(String targetedPage) {
        switch (targetedPage.toUpperCase()) {
            case "TASK DETAILS":
                waitForVisibility(viewTaskPage.taskDetailHeader, 10);
                Assert.assertTrue(viewTaskPage.taskDetailHeader.isDisplayed(), "User didn't navigate to " + targetedPage);
                break;
            case "INBOUND CORRESPONDENCE DETAILS":
                waitForVisibility(viewTaskPage.viewInboundCorrespondenceDetailHeader, 10);
                Assert.assertTrue(viewTaskPage.viewInboundCorrespondenceDetailHeader.isDisplayed(), "User didn't navigate to " + targetedPage);
                break;
            case "OUTBOUND CORRESPONDENCE DETAILS":
                waitForVisibility(correspondenceDetailsPage.viewOutboundCorrespondenceDetailsHeader, 10);
                Assert.assertTrue(correspondenceDetailsPage.viewOutboundCorrespondenceDetailsHeader.isDisplayed(), "User didn't navigate to " + targetedPage);
                break;
            case "CASE/CONSUMER DETAILS":
                waitForVisibility(contactRecordDashboardPage.demographicInfoTab, 10);
                Assert.assertTrue(contactRecordDashboardPage.demographicInfoTab.isDisplayed(), "User didn't navigate to " + targetedPage);
                break;
            default:
                fail("Targeted Page didn't match");
        }
    }

    @And("I verify I see {int} linked entities on link panel")
    public void iVerifyISeeLinkedEntitiesOnLinkPanel(int linkedEntityCount) {
        Assert.assertTrue(trackingPage.linkIDs.size() == linkedEntityCount,
                "Linked Entity count is mismatched Expected Count : " + linkedEntityCount + "Actual : " + trackingPage.linkIDs.size());
    }

    @Then("I click the first ID on Links Panel on Application Tracking Tab")
    public void iClickTheFirstIDOnLinksPanelOnApplicationTrackingTab() {
        firstIDonLinkTab = trackingPage.linkTableData.get(1).getText();
        click(trackingPage.linkTableData.get(1));
    }

    @And("I verify I navigated to duplicated application page")
    public void iVerifyINavigatedToDuplicatedApplicationPage() {
        assertTrue(createApplication.addAuthorizedRepBtn.isDisplayed(), "User didn't navigate to Create Application Page");
        Assert.assertEquals(createApplication.applicationIdText.getText(), firstIDonLinkTab, "Application ID mismatched for Create Application Page");
    }

    @Then("I verify there is {string} linked entity displays on Link Panel")
    public void iVerifyThereIsLinkedEntityDisplaysOnLinkPanel(String linkedEntityCount) {
        waitFor(2);
        List<WebElement> linkedEntityList = Driver.getDriver().findElements(By.xpath("//i[@class='material-icons ml-4 align-middle mdl-color-text--grey-600']"));
        Assert.assertTrue(linkedEntityList.size() == Integer.parseInt(linkedEntityCount), "Linked Entity Count mismatched");
    }

    @And("I click on the pagination dropdown on Link Panel and change pagination as {string}")
    public void iClickOnThePaginationDropdownOnLinkPanelAndChangePaginationAs(String pagination) {
        click(trackingPage.paginationButton);
        click(Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + pagination + "')]")));
    }

    @Then("I verify read only fields for {string} task")
    public void i_verify_read_only_fields_for_task(String taskType, Map<String, String> dataTable) {
        waitFor(5);
        assertTrue(manageMyTasksPage.channelValue.getText().equals(dataTable.get("Channel")), "Channel isnt verified on view task page");

        if (taskType.equals("Member Matching ECMS")) {
            assertTrue(manageMyTasksPage.inboundCorrespondenceTypeVlu.getText().equals(dataTable.get("documentType")), "Inbound correspondence type doesnt match on View Task Page");

        } else if (taskType.equals("Member Matching API")) {
            assertTrue(manageMyTasksPage.inboundCorrespondenceTypeVlu.getText().equals("-- --"), "Inbound correspondence type doesnt match on View Task Page for API created application");
        }
    }

    @And("I verify Reason and Notes associated to the Member Matching Research task will display in the Task Information Panel")
    public void verifyReasonAndNotesOnTaskSliderForMMResearchTask() {
        if (CRM_MemberMatchingStepDef.researchReasonNotes.get().isEmpty())
            assertEquals(manageMyTasksPage.taskSilderTaskInformation.getText(), CRM_MemberMatchingStepDef.researchReason.get());
        else
            assertEquals(manageMyTasksPage.taskSilderTaskInformation.getText(), CRM_MemberMatchingStepDef.researchReason.get() + "\n" + CRM_MemberMatchingStepDef.researchReasonNotes.get());
    }

    @Then("I verify Application Id field and value on Outbound Correspondence Page")
    public void I_verify_Application_Id_field_and_value_on_Outbound_Correspondence_Page() {
        assertTrue(correspondenceDetailsPage.applicationdIDfield.isDisplayed(), "Application id field isnt displayed");
        assertTrue(correspondenceDetailsPage.applicationIDvalue.getText().equals(apiatsApplicationController.applicationIdAPI.get()), "Application id didnt match");
    }

    @Then("I verify each consumer Ids and their role values on Outbound Correspondence Page")
    public void I_verify_each_consumer_Ids_and_their_role_values_on_Outbound_Correspondence_Page() {
        scrollDownUsingActions(1);
        scrollToElement(correspondenceDetailsPage.primaryIndConsumerId);
        waitForVisibility(correspondenceDetailsPage.primaryIndConsumerId, 3);
        assertTrue(correspondenceDetailsPage.primaryIndConsumerId.getText().equals(getKeyFromValue(sendEventAndCreateLinksController.createdConsumerDetails.get(), "Primary Individual")), "Primary individual id isnt verified");
        assertTrue(correspondenceDetailsPage.primaryIndConsumerName.getText().equals(apiatsApplicationController.piFirstName + " " + apiatsApplicationController.piLastName), "Primary individual name isnt verified");
        assertTrue(correspondenceDetailsPage.primaryIndConsumerRole.getText().equals("Primary Individual"), "Role isnt verified for primary individual");
        scrollDownUsingActions(1);
        scrollToElement(correspondenceDetailsPage.applicationMemberConsumerId);
        assertTrue(correspondenceDetailsPage.applicationMemberConsumerId.getText().equals(getKeyFromValue(sendEventAndCreateLinksController.createdConsumerDetails.get(), "Application Member")), "Application member id isnt verified");
        assertTrue(correspondenceDetailsPage.applicationMemberConsumerName.getText().equals(apiatsApplicationController.amFirstName + " " + apiatsApplicationController.amLastName), "Application member name isnt verified");
        assertTrue(correspondenceDetailsPage.applicationMemberConsumerRole.getText().equals("Application Member"), "Role isnt verified for application member");
        scrollDownUsingActions(2);
        scrollToElement(correspondenceDetailsPage.authorizedRepConsumerId);
        assertTrue(correspondenceDetailsPage.authorizedRepConsumerId.getText().equals(getKeyFromValue(sendEventAndCreateLinksController.createdConsumerDetails.get(), "Authorized Rep")), "Authorized rep id isnt verified");
        assertTrue(correspondenceDetailsPage.authorizedRepConsumerName.getText().equals(apiatsApplicationController.authRepFirstName + " " + apiatsApplicationController.authRepLastName), "Authorized representative name isnt verified");
        assertTrue(correspondenceDetailsPage.authorizedRepConsumerRole.getText().equals("Authorized Rep"), "Role isnt verified for authorized representative");

    }

    @And("I click icon of {string} in ATS link section")
    public void iClickIconOfInATSLinkSection(String iconType) {
        switch (iconType.toUpperCase()) {
            case "OUTBOUND CORRESPONDENCE":
                waitForClickablility(trackingPage.fileIcon, 2);
                click(trackingPage.fileIcon);
                break;
            default:
                fail(iconType + " didn't match with switch statement cases");
        }
        waitFor(2);
    }

    @And("I verify all linked entities are in sort order")
    public void iVerifyAllLinkedEntitiesAreInSortOrder() {
        ArrayList<Integer> linkIdList = new ArrayList<>();
        for (WebElement iDElement : trackingPage.linkIDs) {
            linkIdList.add(Integer.parseInt(iDElement.getText()));
        }
        ArrayList<Integer> expectedIdList = new ArrayList<>();

        expectedIdList.addAll(linkIdList);
        Collections.sort(linkIdList, Collections.reverseOrder());
        Assert.assertTrue(linkIdList.equals(expectedIdList), "Linked entities are not in sort order");
    }

    @And("I click initiate button on Links Panel")
    public void iClickInitiateButtonOnLinksPanel() {
        waitForClickablility(trackingPage.initiateButton, 2);
        trackingPage.initiateButton.click();
    }

    @And("I verify initiate button is not displayed on links component")
    public void iVerifyInitiateButtonIsNotDisplayedOnLinksComponent() {
        Assert.assertTrue(IsElementDisplayed(trackingPage.initiateButton), "Initiate button is displayed on Links Component");
    }
}