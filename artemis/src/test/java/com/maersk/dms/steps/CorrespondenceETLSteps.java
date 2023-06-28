package com.maersk.dms.steps;

import com.google.gson.JsonNull;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.utilities.World;
import com.maersk.dms.step_definitions.CorrespondenceETLStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.maersk.crm.utilities.World.generalSave;
import static org.apache.commons.io.FileUtils.copyFile;

public class CorrespondenceETLSteps extends CRMUtilities implements ApiBase {
    private CorrespondenceETLStepDefs correspondenceETLStepDefs = new CorrespondenceETLStepDefs();

    @And("I have a pdf named after the {string} notification that is contained in a zip file")
    public void createPdfNidName(String nid) {
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
            JsonPath ob = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
            nid = ob.getString("recipients[0].notifications[0].notificationId");
            World.generalSave.get().put("nid", nid);
            World.generalSave.get().put("ob", ob);
        }
        File source = correspondenceETLStepDefs.createFileWithNIDName(nid);
        correspondenceETLStepDefs.zipFile(source);
    }

    @And("I upload the {string} file to aws bucket {string} folder {string}")
    public void uploadZipFile(String fileType, String bucketName, String S3folder) {
        correspondenceETLStepDefs.uploadFile(fileType, bucketName, S3folder);
    }

    @When("I run the {string} in project {string}")
    public void iRunTheInProject(String jobName, String project) {
        correspondenceETLStepDefs.executeJob(jobName, project);
    }

    @Then("I should see the notification has been updated properly as expected from running the {string}")
    public void iShouldSeeTheNotificationHasBeenUpdatedProperlyAsExpectedFromRunningThe(String job) {
        switch (job) {
            case "mailhouseRespObjectJob":
                correspondenceETLStepDefs.verifyNotification();
                break;
            default:
                Assert.fail("no matching cases");
        }
        cleanUp(job);
    }

    @Given("I have cleaned up the target folder from previous {string} runs")
    public void cleanUp(String job) {
        switch (job) {
            case "mailhouseRespObjectJob":
                correspondenceETLStepDefs.deleteAllFilesByType("pdf");
                correspondenceETLStepDefs.deleteAllFilesByType("zip");
                correspondenceETLStepDefs.cleanUpUnzipped();
                correspondenceETLStepDefs.cleanUpS3Download();
                break;
            case "mailhouseResponseJob":
                correspondenceETLStepDefs.deleteAllFilesByType("json");
                correspondenceETLStepDefs.deleteAllFilesByType("target\\ETL_OUTPUT","json");
                break;
            default:
                Assert.fail("no matching cases");
        }
    }

    @Then("I verify the dip file that was been created in the aws bucket {string} folder {string}")
    public void iVerifyTheDipFileThatWasBeenCreatedInTheAwsBucketFolder(String bucket, String folder) {
        correspondenceETLStepDefs.downloadDIPFileS3(bucket, folder);
        correspondenceETLStepDefs.unzipDIP("target\\ETL_OUTPUT");
        correspondenceETLStepDefs.verifyDIP();
        correspondenceETLStepDefs.deleteAllFilesByType("pdf");
        correspondenceETLStepDefs.deleteAllFilesByType("zip");
        correspondenceETLStepDefs.cleanUpUnzipped();
        correspondenceETLStepDefs.cleanUpS3Download();
    }

    @Given("I retrieve and store the Unprocessed Notifications for channel {string}")
    public void i_retrieve_and_store_the_Unprocessed_Notifications_for_channel(String channelType) {
        correspondenceETLStepDefs.getListOfUnprocessedNotifications(channelType);
    }


    @Given("I retrieve and store letter data for each Unprocessed Notifications")
    public void i_retrieve_and_store_letter_data_for_each_Unprocessed_Notifications() {
        correspondenceETLStepDefs.getLetterDataForEachUnprocessedNotifications();
    }

    @Then("I verify the zip file that was been created in the aws bucket {string} folder {string}")
    public void i_verify_the_zip_file_that_was_been_created_in_the_aws_bucket_folder(String bucket, String folder) {
        String extension = correspondenceETLStepDefs.getLatestFileNameExtension(bucket, folder);
        correspondenceETLStepDefs.downloadPrintFile(extension, bucket, folder);
        correspondenceETLStepDefs.verifyMailingHouseFile();
        correspondenceETLStepDefs.deleteAllFilesByType("json");
        correspondenceETLStepDefs.deleteAllFilesByType("zip");
        correspondenceETLStepDefs.cleanUpUnzipped();
        correspondenceETLStepDefs.cleanUpS3Download();
    }

    @Given("I verify the process updates the Status of each Notification successfully to {string}")
    public void i_verify_the_process_updates_the_Status_of_each_Notification_successfully_to(String status) {
        correspondenceETLStepDefs.verifyNotificationStatusUpdated(status);
    }

    @Given("I copied the following nids")
    public void i_copied_nids(Map<String, String> dataTable) {
        String notification = dataTable.get("notificationId");
        List<String> Notifications = new ArrayList<>();
        JsonPath response = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get())));
        if (notification.equalsIgnoreCase("previouslyCreated")) {
            String nid = response.getString("recipients[0].notifications.notificationId");
            Notifications.add(nid);
            correspondenceETLStepDefs.copynids(Notifications);
        } else if (notification.equalsIgnoreCase("mail") || notification.equalsIgnoreCase("email") || notification.equalsIgnoreCase("text")) {
            List<Object> channelList = response.getList("recipients[0].notifications.channelType");
            List<Object> notificationIdList = response.getList("recipients[0].notifications.notificationId");
            for (int i = 0; i < channelList.size(); i++) {
                if (channelList.get(i).toString().equalsIgnoreCase(notification)) {
                    notification = notificationIdList.get(i).toString();
                }
            }
            Notifications.add(notification);
            correspondenceETLStepDefs.copynids(Notifications);
        }
        System.out.println( "Notifications : "+Notifications);
    }

    @Given("I should see that following records are produced to DPBI")
    public void i_should_see_that_following_records_are_produced_to_DPBI(Map<String, String> dataTable) {
        correspondenceETLStepDefs.verifyRecordsProducedToDPBI(dataTable);
    }

    @Given("I should see that following events are produced to DPBI")
    public void i_should_see_that_following_events_are_produced_to_DPBI(Map<String, String> dataTable) {
        correspondenceETLStepDefs.verifyEventsProducedToDPBI(dataTable);
    }


    @And("I have a json file named after the notification and updated with following values")
    public void createJsonNidName(Map<String, String> dataTable) {
        String nid = "";
        if ("previouslyCreated".equalsIgnoreCase(dataTable.get("nid"))) {
            String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID"+ Hooks.nameAndTags.get()));
            JsonPath ob = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
            nid = ob.getString("recipients[0].notifications[0].notificationId");
            World.generalSave.get().put("nid", nid);
            World.generalSave.get().put("ob", ob);
        } else {
            nid = dataTable.get("nid");
            World.generalSave.get().put("nid", nid);
        }

        correspondenceETLStepDefs.updateJsonFile(dataTable);
        correspondenceETLStepDefs.createJsonFileWithNIDName(nid);
        correspondenceETLStepDefs.setfilename(nid, "json");
    }

    @Then("I verify the {string} file that was been created in the aws bucket {string} folder {string}")
    public void iVerifyTheFileThatWasBeenCreatedInTheAwsBucketFolder(String nid,String bucket, String folder) {
      boolean status= false;
        if ("previouslyCreated".equalsIgnoreCase(nid)) {
            nid = World.generalSave.get().get("nid").toString();
        }

        correspondenceETLStepDefs.downloadDIPFileS3(bucket, folder);
        File fi = new File("target\\ETL_OUTPUT");
        for (File f : Objects.requireNonNull(fi.listFiles())) {
            if(f.getName().equalsIgnoreCase(nid+".json")) {
                status=true;
                break;
            }
        }
        Assert.assertTrue(status);
    }

}
