package com.maersk.dms.steps;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.dms.step_definitions.EditTeamTeamDetailsStepDefs;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class EditTeamTeamDetailsSteps extends BrowserUtils {
    final ThreadLocal<EditTeamTeamDetailsStepDefs> editTeamTeamDetailsStepDefs = ThreadLocal.withInitial(EditTeamTeamDetailsStepDefs::new);
    final ThreadLocal<PublishDPBIEventsOutboundCorrespondenceChangesStepDefs> outCorr = ThreadLocal.withInitial(PublishDPBIEventsOutboundCorrespondenceChangesStepDefs::new);
    final ThreadLocal<Map<String, String>> tmDetails = ThreadLocal.withInitial(() -> editTeamTeamDetailsStepDefs.get().createTeamDetails());
    final ThreadLocal<ArrayList<String>> fields = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<ArrayList<String>> userList = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<Boolean> superVisor = ThreadLocal.withInitial(() -> false);
    final ThreadLocal<Integer> countOfUsers = ThreadLocal.withInitial(() -> 0);
    final ThreadLocal<Integer> countOfAddingUsers = ThreadLocal.withInitial(() -> 0);

    @Given("I navigate to the TEAM Section of the content")
    public void i_navigate_to_the_TEAM_Section_of_the_content() {
        editTeamTeamDetailsStepDefs.get().navToTeamSection();
    }

    @When("I select the Edit button on the for a {string} Team")
    public void i_select_the_Edit_button_on_the_for_a_Team(String name) {
        switch (name) {
            case "FUTURE":
                editTeamTeamDetailsStepDefs.get().clickFutureTeam();
                break;
            case "ACTIVE":
                editTeamTeamDetailsStepDefs.get().clickActiveTeam();
                break;
            case "INACTIVE":
                editTeamTeamDetailsStepDefs.get().clickInactiveTeam();
                break;
            default:
                Assert.fail("Check Spelling of Cases");
        }
        editTeamTeamDetailsStepDefs.get().clickEditTeam();
    }


    @Then("the following fields become editable {string} {string} {string} {string}")
    public void the_following_fields_become_editable(String string, String string2, String string3, String string4) {
        fields.get().add(string);
        fields.get().add(string2);
        fields.get().add(string3);
        fields.get().add(string4);
        for (int i = 0; i <= fields.get().size() - 1; i++) {
            editTeamTeamDetailsStepDefs.get().fieldsEditable(fields.get().get(i), tmDetails.get());
        }
    }

    @Then("the following fields become editable {string} {string} {string}")
    public void the_following_fields_become_editable(String string, String string2, String string3) {
        fields.get().add(string);
        fields.get().add(string2);
        fields.get().add(string3);
        for (int i = 0; i <= fields.get().size() - 1; i++) {
            editTeamTeamDetailsStepDefs.get().fieldsEditable(fields.get().get(i), tmDetails.get());
        }
    }

    @Then("the following fields become editable {string} {string}")
    public void the_following_fields_become_editable(String string, String string2) {
        fields.get().add(string);
        fields.get().add(string2);
        for (int i = 0; i <= fields.get().size() - 1; i++) {
            editTeamTeamDetailsStepDefs.get().fieldsEditable(fields.get().get(i), tmDetails.get());
        }
    }

    @When("I click on {string} button")
    public void i_click_on_button(String button) {
        editTeamTeamDetailsStepDefs.get().clickOnSpanButton(button);
    }

    @Then("the system does not save, and displays {string}")
    public void the_system_does_not_save_and_displays(String error) {
        Assert.assertTrue(outCorr.get().elementIsDisplayed(Driver.getDriver().findElement(By.xpath("//p[text()='" + error + "']"))));
    }

    @Then("I will be navigated back to team units")
    public void i_will_be_navigated_back_to_team_units() {
        editTeamTeamDetailsStepDefs.get().verifyOnTeamSection();
    }

    @Then("the following fields become editable {string} {string} {string} {string} {string}")
    public void the_following_fields_become_editable(String string, String string2, String string3, String string4, String string5) {
        fields.get().add(string);
        fields.get().add(string2);
        fields.get().add(string3);
        fields.get().add(string4);
        fields.get().add(string5);
        for (int i = 0; i <= fields.get().size() - 1; i++) {
            editTeamTeamDetailsStepDefs.get().fieldsEditable(fields.get().get(i), tmDetails.get());
        }
    }

    @Then("I click on save button and it successfully saves")
    public void i_click_on_save_button_and_it_successfully_saves() {
        editTeamTeamDetailsStepDefs.get().verifySuccessMessageDisplayed();
    }

    @Then("Data that was given is saved")
    public void data_that_was_given_is_saved() {
        editTeamTeamDetailsStepDefs.get().navToTeamSection();
        BrowserUtils.waitFor(4);
        editTeamTeamDetailsStepDefs.get().navToTeamSection();
        BrowserUtils.waitFor(4);
        WebElement teamName = Driver.getDriver().findElement(By.xpath("//a[text()='" + tmDetails.get().get("TeamName") + "']"));
        hover(teamName);
        WebElement desc = Driver.getDriver().findElement(By.xpath("//td[text()='" + tmDetails.get().get("Description") + "']"));
        Assert.assertTrue(outCorr.get().elementIsDisplayed(teamName));
        Assert.assertTrue(outCorr.get().elementIsDisplayed(desc));
    }

    @Then("I search {string} in the Get Events endpoint for TM")
    public void i_search_in_the_Get_Events_endpoint_for_TM(String eventName) throws IOException {
        String accessToken = outCorr.get().getAccessToken();
        if (eventName.compareToIgnoreCase("TEAM_UPDATE_EVENT") == 0) {
            String searchQAEvent = outCorr.get().searchQAEvents(accessToken, eventName).replaceAll("\\\\", "");
            Assert.assertTrue(searchQAEvent.contains("\"action\":\"Update\""));
            Assert.assertTrue(searchQAEvent.contains("\"recordType\":\"Team\""));
            Assert.assertTrue(searchQAEvent.contains(tmDetails.get().get("TeamName")));
            Assert.assertTrue(searchQAEvent.contains(tmDetails.get().get("Description")));
        } else if (eventName.compareToIgnoreCase("TEAM_USER_SAVE_EVENT") == 0) {
            String searchQAEvent = outCorr.get().searchQAEvents(accessToken, eventName).replaceAll("\\\\", "");
            Assert.assertTrue(searchQAEvent.contains("\"eventName\":\"TEAM_USER_SAVE_EVENT\""));
            Assert.assertTrue(searchQAEvent.contains("\"module\":\"TENANT_MANAGER\""));
            Assert.assertTrue(searchQAEvent.contains("payload"));
            Assert.assertTrue(searchQAEvent.contains("createdBy"));
            Assert.assertTrue(searchQAEvent.contains("updatedOn"));
            Assert.assertTrue(searchQAEvent.contains("\"action\":\"Create\""));
            Assert.assertTrue(searchQAEvent.contains("\"recordType\":\"Team User\""));
            if (superVisor.get()) Assert.assertTrue(searchQAEvent.contains("\"supervisorFlag\":true"));
            else Assert.assertTrue(searchQAEvent.contains("\"supervisorFlag\":false"));
        } else if (eventName.compareToIgnoreCase("TEAM_USER_UPDATE_EVENT") == 0) {
            String searchQAEvent = outCorr.get().searchQAEvents(accessToken, eventName).replaceAll("\\\\", "");
            Assert.assertTrue(searchQAEvent.contains("\"eventName\":\"TEAM_USER_UPDATE_EVENT\""));
            Assert.assertTrue(searchQAEvent.contains("\"module\":\"TENANT_MANAGER\""));
            Assert.assertTrue(searchQAEvent.contains("payload"));
            Assert.assertTrue(searchQAEvent.contains("createdBy"));
            Assert.assertTrue(searchQAEvent.contains("updatedOn"));
            Assert.assertTrue(searchQAEvent.contains("updatedBy"));
            Assert.assertTrue(searchQAEvent.contains("\"action\":\"Update\""));
            Assert.assertTrue(searchQAEvent.contains("\"recordType\":\"Team User\""));
            if (superVisor.get()) Assert.assertTrue(searchQAEvent.contains("\"supervisorFlag\":true"));
            else Assert.assertTrue(searchQAEvent.contains("\"supervisorFlag\":false"));
        }

    }

    @Then("I will receive a Warning Message: {string}")
    public void i_will_receive_a_Warning_Message(String string) {
        editTeamTeamDetailsStepDefs.get().verifyWarningMessageDisplayed(string);
    }

    @When("I add a user in Team Details by User Name as Team Supervisor {string}")
    public void i_add_a_user_in_Team_Details_by_User_Name_as_Team_Supervisor(String supervisor) {
        if (supervisor.compareToIgnoreCase("true") == 0) superVisor.set(true);
        userList.get().add(editTeamTeamDetailsStepDefs.get().addUserByUserName(supervisor));
    }

    @When("I add a user in Team Details by Email as Team Supervisor {string}")
    public void i_add_a_user_in_Team_Details_by_Email_as_Team_Supervisor(String supervisor) {
        if (supervisor.compareToIgnoreCase("true") == 0) superVisor.set(true);
        userList.get().add(editTeamTeamDetailsStepDefs.get().addUserByEmail(supervisor));
    }

    @When("I add a user in Team Details by maersk ID as Team Supervisor {string}")
    public void i_add_a_user_in_Team_Details_by_maersk_ID_as_Team_Supervisor(String supervisor) {
        if (supervisor.compareToIgnoreCase("true") == 0) superVisor.set(true);
        userList.get().add(editTeamTeamDetailsStepDefs.get().addUserByMaxID(supervisor));
    }

    @When("I verify User List was added on UI")
    public void i_verify_User_List_was_added_on_UI() {
        BrowserUtils.waitFor(5);
        if (!userList.get().isEmpty()) {
            for (int i = 0; i <= userList.get().size() - 1; i++) {
                String[] st = userList.get().get(i).split("/");
                String[] fullName = st[0].split(" ");
                String firstName = fullName[0];
                String mID = st[2];
                String email = st[1];
                Assert.assertTrue(editTeamTeamDetailsStepDefs.get().verifyPContainsElement(firstName));
                Assert.assertTrue(editTeamTeamDetailsStepDefs.get().verifyPElement(email));
                Assert.assertTrue(editTeamTeamDetailsStepDefs.get().verifyPElement(mID));
            }
            editTeamTeamDetailsStepDefs.get().verifySupervisorColor();
        }
    }

    @When("I update User List")
    public void i_update_User_List() {
        superVisor.set(editTeamTeamDetailsStepDefs.get().updateUserList());
    }

    @When("I delete a Regular User")
    public void i_delete_a_Regular_User() {
        countOfUsers.set(editTeamTeamDetailsStepDefs.get().countOfListUsers());
        editTeamTeamDetailsStepDefs.get().deleteListUser();
    }

    @When("I verify user was deleted")
    public void i_verify_user_was_deleted() {
        Assert.assertTrue(editTeamTeamDetailsStepDefs.get().countOfListUsers().compareTo(countOfUsers.get() - 1) == 0);
    }

    @When("I add {int} user by clicking on the add user button")
    public void i_add_user_by_clicking_on_the_add_user_button(Integer int1) {
        for (int i = 1; i <= int1; i++) {
            editTeamTeamDetailsStepDefs.get().clickAddUserButton();
        }
        countOfAddingUsers.set(editTeamTeamDetailsStepDefs.get().countOfAddingUsers());
        Assert.assertTrue(int1.compareTo(countOfAddingUsers.get()) == 0);
    }

    @When("I add all the users to the same team amd make one of them as a Supervisor")
    public void i_add_all_the_users_to_the_same_team_amd_make_one_of_them_as_a_Supervisor() {
        userList.set(editTeamTeamDetailsStepDefs.get().selectsUsersByUserName());
        editTeamTeamDetailsStepDefs.get().selectSupervisor();
        Driver.getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();
    }

    @When("I verify Supervisor and Regular users colors")
    public void i_verify_Supervisor_and_Regular_users_colors() {
        editTeamTeamDetailsStepDefs.get().verifySupervisorColor();
        editTeamTeamDetailsStepDefs.get().verifyRegularUsersColor();

    }

    @When("I verify Regular user are listed in Alphabetical order")
    public void i_verify_Regular_user_are_listed_in_Alphabetical_order() {
        editTeamTeamDetailsStepDefs.get().verifyUserListNamesOrder();
    }

    @When("I verify users excluding Supervisor are listed in Alphabetical order")
    public void i_verify_user_excluding_Supervisor_are_listed_in_Alphabetical_order() {
        editTeamTeamDetailsStepDefs.get().verifyUserListExcludingSupervisorNamesOrder();
    }


}
