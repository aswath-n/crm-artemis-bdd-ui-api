package com.maersk.dms.step_definitions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maersk.crm.step_definitions.Hooks;
import com.maersk.crm.utilities.*;

import com.google.gson.Gson;
import com.maersk.crm.utilities.Api_Storage;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.World;
import com.maersk.crm.utilities.etl_util.ETLBaseClass;
import com.maersk.crm.utilities.etl_util.ETL_Storage;
import com.maersk.crm.utilities.etl_util.S3_util;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.maersk.crm.utilities.World.generalSave;
import static org.apache.commons.io.FileUtils.copyFile;

public class CorrespondenceETLStepDefs extends CRMUtilities implements ETLBaseClass, ApiBase {
    private final ThreadLocal<ETL_Storage> etlStg = ThreadLocal.withInitial(ETL_Storage::getInstance);
    private final ThreadLocal<Api_Storage> stg = ThreadLocal.withInitial(Api_Storage::getInstance);
    public static ThreadLocal<String> fileName = ThreadLocal.withInitial(String::new);
    private static final ThreadLocal<List<String>> listOfUnprocessedNotification = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<List<String>> listOfExportedNotification = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<List<String>> listOfErrorNotification = ThreadLocal.withInitial(ArrayList::new);


    public void zipFile(File sourceFile) {
        try {
            fileName.set(RandomStringUtils.random(4, true, false) + ".zip");
            FileOutputStream fos = new FileOutputStream("src\\test\\resources\\testData\\api\\dms\\temp\\" + fileName.get());
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(sourceFile);
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void uploadFile(String fileType, String bucketName, String S3folder) {
        String env = "QE/";
        if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
            env = "QE-DEV/";
        }
        switch (fileType) {
            case "mailhouseRespObjectJob":
                break;
            case "mailhouseResponseJob":
                break;
        }
        s3.uploadObject(bucketName, env + S3folder + fileName.get(), "src\\test\\resources\\testData\\api\\dms\\temp\\" + fileName.get());
    }

    public File createFileWithNIDName(String nid) {
        File source = new File("src\\test\\resources\\testData\\api\\dms\\MultiPageDocumenMail.pdf");
        File destination = new File("src\\test\\resources\\testData\\api\\dms\\temp\\" + nid + ".pdf");
        try {
            copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("failed to create file");
        }
        return destination;
    }


    public File createJsonFileWithNIDName(String nid) {
        File source = new File("src\\test\\resources\\testData\\api\\dms\\temp\\responsefile.json");
        File destination = new File("src\\test\\resources\\testData\\api\\dms\\temp\\" + nid + ".json");
        try {
            copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    public void executeJob(String jobName, String project) {
        String env = "QE";
        if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
            env = "QE-DEV";
        }
        List<String> IDS = apiHelper.workSpaceCall(project, env);
        System.out.println("IDS.toString() = " + IDS.toString());
        String executionID = apiHelper.executablePostCall1(apiHelper.getExecutableCall(jobName, IDS.get(0), IDS.get(1)));
        System.out.println("executionID = " + executionID);
        String status = "";
        int count = 0;
        while (!status.equals("execution_successful")) {
            System.out.printf("\r" + jobName + " job on " + env + " env for " + project + " is still running . . ." + ++count + " minutes");
            status = apiHelper.executionAssertion(executionID);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            count++;
            if (count > 10) {
                org.testng.Assert.fail(jobName + " job on " + env + " env for " + project + " is completed successfully");
                break;
            }
        }
        System.out.println(jobName + " job on " + env + " env for " + project + " is completed successfully");
    }

    public void verifyNotification() {
        String cid = String.valueOf(World.generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
        JsonPath ob = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(cid);
        List<Map<String, Object>> notifications = ob.getList("recipients[0].notifications");
        for (Map<String, Object> notification : notifications) {
            if ("notificationId".equalsIgnoreCase(String.valueOf(notification.get("notificationId")))) {
                Assert.assertEquals(String.valueOf(notification.get("notificationId")), String.valueOf(World.generalSave.get().get("nid")));
                Assert.assertEquals(String.valueOf(notification.get("objectParentFileId")), fileName.get());
                Assert.assertNull(notification.get("objectReceivedOn"));// should be null as of CP-39472
            }
        }
    }

    public void deleteAllFilesByType(String extension) {
        File folder = new File("src\\test\\resources\\testData\\api\\dms\\temp");
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            if (f.getName().endsWith(extension)) {
                if (!f.delete()) {
                    System.out.println("failed to delete - " + f.getName() + " files");
                }
            }
        }
    }

    public void deleteAllFilesByType(String location, String extension) {
        File folder = new File(location);
        try {
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                if (f.getName().endsWith(extension)) {
                    if (!f.delete()) {
                        System.out.println("failed to delete - " + f.getName() + " files");
                    }
                }
            }
        } catch (NullPointerException nofolder) {
            System.out.println("no folder exists");
            return;
        }
    }

    public void deleteFolder(File folder) {
        File f = folder;
        if (!f.delete()) {
            System.out.println("failed to delete - " + f.getName() + " folder");
        }
    }

    public void downloadDIPFileS3(String bucket, String folder) {
        try {
            String env = "QE/";
            if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
                env = "QE-DEV/";
            }
            s3.downloadObjectFromS3(bucket, env + folder + fileName.get(), "src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\" + fileName.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unzipDIP(String folder) {
        try {
            unzip(Paths.get("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\" + fileName.get()), Paths.get("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped\\"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path zipTarget(ZipEntry zipEntry, Path targetDir) throws IOException {
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }

        return normalizePath;
    }

    private void unzip(Path source, Path target) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                boolean isDirectory = zipEntry.getName().endsWith(File.separator);
                Path newPath = zipTarget(zipEntry, target);
                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    public void cleanUpS3Download() {
        try {
            deleteAllFilesByType("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT", ".zip");
        } catch (NullPointerException noFolder) {
            System.out.println("no folder exists, no files deleted");
        }
    }

    public void cleanUpUnzipped() {
        try {
            deleteAllFilesByType("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped", ".pdf");
            deleteAllFilesByType("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped", ".dip");
        } catch (NullPointerException noFolder) {
            System.out.println("folder does not exit, no files deleted");
            return;
        }
    }

    public void verifyDIP() {
        File unzipped = new File("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped\\");
        String dipFileName = "";
        for (File listFile : unzipped.listFiles()) {
            if (listFile.getName().endsWith(".dip")) {
                dipFileName = listFile.getName();
            }
        }
        System.out.println("dip file name - " + dipFileName);
        String expected = BrowserUtils.validNumberFilter(LocalDateTime.now(ZoneOffset.UTC).toString().substring(0, 10)) + "-" + BrowserUtils.validNumberFilter(LocalDateTime.now(ZoneOffset.UTC).toString().substring(11, 13));
        Assert.assertTrue("dipfile name - " + dipFileName + "\n" + "expected - " + expected, dipFileName.startsWith(expected));
        verifyDipContents(dipFileName);
    }

    private void verifyDipContents(String dipFileName) {
        JsonPath settings = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        String obType = settings.getString("correspondenceSettingsResponse.outboundCorrespondenceEcmsDocumentType");
        String pdfName = String.valueOf(World.generalSave.get().get("nid"));
        String output = "";
        StringBuilder dip = new StringBuilder();
        try {
            File myObj = new File("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped\\" + dipFileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                dip.append(myReader.nextLine());
                System.out.println(dip.toString());
            }
            myReader.close();
            output = dip.toString();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        output = output.replace("\"", "");
        String expected = obType + "," + pdfName + ".pdf," + pdfName + ",16";
        Assert.assertEquals(output, expected);
    }

    public void getListOfUnprocessedNotifications(String channelType) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiECMSLetterData"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("/unprocessedNotifications?channel=" + channelType);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getAPI();
        listOfUnprocessedNotification.set( API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("data"));
        World.generalSave.get().put("unprocessedNotification", listOfUnprocessedNotification.get());
    }

    public void getLetterDataForEachUnprocessedNotifications() {
        Map<String, JsonPath> mapOfLetterData = new HashMap<>();
        for (String nid : listOfUnprocessedNotification.get()) {
            try {
                mapOfLetterData.put(nid, API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getLetterData(nid));
                listOfExportedNotification.get().add(nid);
            } catch (java.lang.AssertionError ex) {
                listOfErrorNotification.get().add(nid);
            }
        }
        World.generalSave.get().put("letterDataUnprocessedNotification", mapOfLetterData);
    }

    public String getOutboundFilenamePrefix() {
        JsonPath settings = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getObSettings(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getProjectId());
        return settings.getString("correspondenceSettingsResponse.vendorDetails[0].outboundCorrespondenceFileNamePrefix");
    }

    public void downloadPrintFile(String extension, String bucket, String folder) {
        try {
            String env = "QE/";
            if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
                env = "QE-DEV/";
            }
            fileName.set(getOutboundFilenamePrefix() + "_" + LocalDate.now().toString().replace("-", "") + "_" + extension + ".zip");
            System.out.println("PATH " + env + folder + fileName.get());
            s3.downloadObjectFromS3(bucket, env + folder + fileName.get(), "src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\" + fileName.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLatestFileNameExtension(String bucket, String folder) {
        String env = "QE/";
        if ("QA-DEV".equalsIgnoreCase(System.getProperty("env"))) {
            env = "QE-DEV/";
        }
        List<String> allFiles = s3.getAllFilesInFolder(bucket, env + folder);
        String currentDay = LocalDate.now().toString().replace("-", "");
        List<String> todayCreatedFiles = new ArrayList<>();
        String outboundCorrespondenceFileNamePrefix = getOutboundFilenamePrefix();
        for (String file : allFiles) {
            if (file.contains(currentDay)) {
                System.out.println("TODAY CREATED FILE " + file);
                todayCreatedFiles.add(file);
                Assert.assertTrue("File Name prefix verification failed for " + file, file.contains(outboundCorrespondenceFileNamePrefix) && file.contains(currentDay));
            }
        }
        String latestFile = todayCreatedFiles.get(todayCreatedFiles.size() - 1);
        return latestFile.substring(latestFile.indexOf(".zip") - 4, latestFile.indexOf(".zip"));
    }

    public void verifyMailingHouseFile() {
        try {
            unzip(Paths.get("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\" + fileName.get()), Paths.get("src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped\\"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, JsonPath> mapOfLetterData = (Map<String, JsonPath>) World.generalSave.get().get("letterDataUnprocessedNotification");
        String tempNotificationID = "";
        JsonElement actualElement;
        JsonElement expectedElement;
        String jsonFile = fileName.get().replace(".zip", ".json");
        String path = "src\\test\\resources\\testData\\api\\dms\\temp\\ETL_OUTPUT\\unzipped\\";
        JsonObject jsonResponse = S3_util.getJsonFromTempFolder(path, jsonFile);
        JsonParser jsonParser = new JsonParser();

        int notificationSize = jsonResponse.getAsJsonObject("MailingHouseFile").getAsJsonArray("Letter").size();

        Assert.assertEquals("Date verification failed", LocalDate.now().toString(), jsonResponse.getAsJsonObject("MailingHouseFile").getAsJsonObject().get("Date").getAsString());

        for (int i = 0; i < notificationSize; i++) {
            tempNotificationID = jsonResponse.getAsJsonObject("MailingHouseFile").getAsJsonArray("Letter").get(i).getAsJsonObject().getAsJsonObject("notification").get("notificationId").getAsString();

            actualElement = jsonParser.parse(jsonResponse.getAsJsonObject("MailingHouseFile").getAsJsonArray("Letter").get(i).toString());
            expectedElement = jsonParser.parse(mapOfLetterData.get(tempNotificationID).prettify());

            Assert.assertEquals(actualElement, expectedElement);
        }
    }

    public void verifyNotificationStatusUpdated(String status) {
        String name = "SvcLetterRequestJob";
        String tempCID;
        JsonPath tempJsonPath;
        switch (status) {
            case "Exported":
                for (String notification : listOfExportedNotification.get()) {
                    tempCID = getCIDByNID(notification);
                    tempJsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(tempCID);
                    Assert.assertEquals("Notification status verification failed, expected Exported, but found " + tempJsonPath.getString("currentStatus.status"),
                            "Exported",
                            tempJsonPath.getString("recipients[0].notifications[0].notificationStatus.status"));
                    Assert.assertEquals("Updated BY verification failed, expected " + getUserIDByName(name) + ", but found " + tempJsonPath.getString("recipients[0].notifications[0].updatedBy"),
                            getUserIDByName(name),
                            tempJsonPath.getString("recipients[0].notifications[0].updatedBy"));
                    Assert.assertEquals("Updated Date verification failed, expected " + String.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))).substring(0, 15) + ", but found " + tempJsonPath.getString("recipients[0].notifications[0].updatedDatetime").substring(0, 15),
                            String.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))).substring(0, 15),
                            tempJsonPath.getString("recipients[0].notifications[0].updatedDatetime").substring(0, 15));
                }
                break;
            case "Error":
                for (String notification : listOfErrorNotification.get()) {
                    tempCID = getCIDByNID(notification);
                    tempJsonPath = API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getCorrespondence(tempCID);
                    Assert.assertEquals("Notification status verification failed, expected Error, but found " + tempJsonPath.getString("recipients[0].notifications[0].notificationStatus.status"),
                            "Error",
                            tempJsonPath.getString("recipients[0].notifications[0].notificationStatus.status"));
                    Assert.assertEquals("Notification status verification failed, expected letterdata not available, but found " + tempJsonPath.getString("recipients[0].notifications[0].notificationStatus.statusReason"),
                            "letterdata not available",
                            tempJsonPath.getString("recipients[0].notifications[0].notificationStatus.statusReason"));
                    Assert.assertEquals("Updated BY verification failed, expected " + getUserIDByName(name) + ", but found " + tempJsonPath.getString("recipients[0].notifications[0].updatedBy"),
                            getUserIDByName(name),
                            tempJsonPath.getString("recipients[0].notifications[0].updatedBy"));
                    Assert.assertEquals("Updated Date verification failed, expected " + String.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))).substring(0, 15) + ", but found " + tempJsonPath.getString("recipients[0].notifications[0].updatedDatetime").substring(0, 15),
                            String.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))).substring(0, 15),
                            tempJsonPath.getString("recipients[0].notifications[0].updatedDatetime").substring(0, 15));
                }
                break;
        }
    }

    private String getCIDByNID(String notificationID) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences/ouboundcorrespondence?page=0&size=20");
        JsonObject request = new JsonObject();
        request.addProperty("notificationId", notificationID);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.content[0].id");
    }


    public void copynids(List<String> nids) {
        for (String nid : nids) {
            if (nid.contains("["))
                listOfUnprocessedNotification.get().add(nid.substring(1, nid.length() - 1));
            else
                listOfUnprocessedNotification.get().add(nid);
        }
        System.out.println("listOfUnprocessedNotification " + listOfUnprocessedNotification.get());

    }

    public void verifyRecordsProducedToDPBI(Map<String, String> dataTable) {
        JsonPath jsonPath;
        boolean isDPBIContainRecord = false;
        String tempCID = "";
        String tempNID = "";
        String tempConsumerID = "";
        for (String event : dataTable.keySet()) {
            switch (event) {
                case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                    jsonPath = getEventsByName(event);
                    for (String notification : listOfUnprocessedNotification.get()) {
                        tempNID = notification;
                        tempCID = getCIDByNID(tempNID);
                        int eventSize = jsonPath.getInt("eventsList.content.size");
                        for (int j = 0; j < eventSize; j++) {
                            if (jsonPath.getString("eventsList.content[" + j + "].payload").contains(tempCID)) {
                                isDPBIContainRecord = true;
                                break;
                            }
                        }
                    }
                    Assert.assertTrue(tempCID + " not found in " + event, isDPBIContainRecord);
                    break;
                case "NOTIFICATION_UPDATE_EVENT":
                    jsonPath = getEventsByName(event);
                    for (String notification : listOfUnprocessedNotification.get()) {
                        tempNID = notification;
                        int eventSize = jsonPath.getInt("eventsList.content.size");
                        for (int j = 0; j < eventSize; j++) {
                            if (jsonPath.getString("eventsList.content[" + j + "].payload").contains(tempNID)) {
                                isDPBIContainRecord = true;
                                break;
                            }
                        }
                    }
                    Assert.assertTrue(tempNID + " not found in " + event, isDPBIContainRecord);
                    break;
                case "CONSUMER_SUBSCRIPTION_UPDATE":
                    jsonPath = getEventsByName(event);

                    if ("DC-EB".equalsIgnoreCase(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectName())) {
                        tempConsumerID = API_THREAD_LOCAL_FACTORY.getCreateConsumerContactThreadLocal().getExternalConsumerId();
                    } else {
                        Map<String, String> caseConsumerId = (Map<String, String>) generalSave.get().get("caseConsumerId");
                        tempConsumerID = caseConsumerId.get("consumerId");
                    }
                    int eventSize = jsonPath.getInt("eventsList.content.size");
                    for (int j = 0; j < eventSize; j++) {
                        System.out.println(jsonPath.getString("eventsList.content[" + j + "].payload"));
                        Assert.assertEquals(jsonPath.getString("eventsList.content[" + j + "].module"), "OUTBOUND_CORRESPONDENCE");
                        Assert.assertEquals(jsonPath.getString("eventsList.content[" + j + "].status"), "SUCCESS");

                        if (dataTable.get(event).equalsIgnoreCase("with consumer")) {
                            if (jsonPath.getString("eventsList.content[" + j + "].payload").contains("{\"emailAddress\":\"workpcautomation@outlook.com\",\"message\":\"STOP\",\"externalRefType\":\"CONSUMER\",\"externalRefId\":\"" + tempConsumerID + "\",\"userId\":\"" + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service") + "\"}")) {
                                isDPBIContainRecord = true;
                                break;
                            }
                        } else if (dataTable.get(event).equalsIgnoreCase("without consumer")) {
                            if (jsonPath.getString("eventsList.content[" + j + "].payload").contains("{\"emailAddress\":\"workpcautomation@outlook.com\",\"message\":\"STOP\",\"userId\":\"" + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service") + "\"}")) {
                                isDPBIContainRecord = true;
                                break;
                            }
                        } else if (dataTable.get(event).contains("START") || dataTable.get(event).contains("STOP") )  {
                            if (jsonPath.getString("eventsList.content[" + j + "].payload").contains("{\"textNumber\":\""+dataTable.get(event).split(",")[1]+"\",\"message\":\""+dataTable.get(event).split(",")[0]+"\",\"userId\":\"" + API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getuseridbyfirstname("ECMS Service") + "\"}")) {
                                isDPBIContainRecord = true;
                                break;
                            }
                        }

                    }
                    Assert.assertTrue(event+" not found in " , isDPBIContainRecord);
                    break;
            }
        }

    }

    public void verifyEventsProducedToDPBI(Map<String, String> dataTable) {
        JsonPath jsonPath;
        String tempCID = "";
        String tempNID = "";
        for (String event : dataTable.keySet()) {
            boolean isDPBIContainRecord = false;
            switch (event) {
                case "CORRESPONDENCE_RECIPIENT_UPDATE_EVENT":
                case "CORRESPONDENCE_RECIPIENT_SAVE_EVENT":
                case "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT":
                    jsonPath = getEventsByName(event);
                    if (dataTable.get(event).equalsIgnoreCase("previouslyCreated"))
                        tempCID = String.valueOf(generalSave.get().get("OUTBOUNDCORRESPONDENCECID" + Hooks.nameAndTags.get()));
                    else
                        tempCID = dataTable.get(event);
                    int eventSize = jsonPath.getInt("eventsList.content.size");
                    for (int j = 0; j < eventSize; j++) {
                        if (jsonPath.getString("eventsList.content[" + j + "].payload").contains(tempCID)) {
                            System.out.println("payload : " + jsonPath.getString("eventsList.content[" + j + "].payload"));
                            isDPBIContainRecord = true;
                            break;
                        }
                    }
                    Assert.assertTrue(tempCID + " not found in " + event, isDPBIContainRecord);
                    break;
                case "NOTIFICATION_SAVE_EVENT":
                case "LETTER_DATA_SAVE_EVENT":
                case "NOTIFICATION_UPDATE_EVENT":
                    jsonPath = getEventsByName(event);

                    if (dataTable.get(event).equalsIgnoreCase("previouslyCreated"))
                        tempNID = listOfUnprocessedNotification.get().get(0);
                    else
                        tempNID = dataTable.get(event);
                    int eventSize2 = jsonPath.getInt("eventsList.content.size");
                    for (int j = 0; j < eventSize2; j++) {
                        if (jsonPath.getString("eventsList.content[" + j + "].payload").contains(tempNID)) {
                            System.out.println("payload : " + jsonPath.getString("eventsList.content[" + j + "].payload"));
                            isDPBIContainRecord = true;
                            break;
                        }
                    }
                    Assert.assertTrue(tempNID + " not found in " + event, isDPBIContainRecord);
                    break;
            }
        }
    }

    private JsonPath getEventsByName(String eventName) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiEventsURI"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("app/crm/events?size=100&page=0&sort=eventId,desc");
        JsonObject request = new JsonObject();
        request.addProperty("eventName", eventName);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator;
    }

    private String getUserIDByName(String name) {
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiTmUsers"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("users");
        JsonObject request = new JsonObject();
        request.addProperty("firstName", name);
        JsonObject user = new JsonObject();
        user.addProperty("projectId", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getProjectId());
        request.add("user", user);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("staffList.content[0].usersList[0].userId");
    }

    public void updateJsonFile(Map<String, String> dataTable) {

        // create a writer
        Writer writer = null;
        Map<String, Object> map = new HashMap<>();
        JsonObject jo = new JsonObject();
        jo.addProperty("vendorKey", "Vendor");
        jo.addProperty("originalAddressLine1", "12 main street");
        jo.addProperty("originalAddressLine2", "apt 2");
        jo.addProperty("originalAddressCity", "BURKE");
        jo.addProperty("originalAddressState", "VA");
        jo.addProperty("originalAddressZip", "22030");
        jo.addProperty("newAddressLine1", "15 main street");
        jo.addProperty("newAddressLine2", "apt 3");
        jo.addProperty("newAddressCity", "Fairfax");
        jo.addProperty("newAddressState", "VA");
        jo.addProperty("newAddressZip", "string");
        jo.addProperty("mailedAddressLine1", "string");
        jo.addProperty("mailedAddressLine2", "string");
        jo.addProperty("mailedAddressCity", "string");
        jo.addProperty("mailedAddressState", "string");
        jo.addProperty("mailedAddressZip", "string");
        jo.addProperty("mailedDate", "datestring");

        for (String data : dataTable.keySet()) {
            switch (data) {
                case "nid":
                    String nid;
                    if ("previouslyCreated".equalsIgnoreCase(dataTable.get("nid")))
                        nid = World.generalSave.get().get("nid").toString();
                    else
                        nid = dataTable.get("nid");
                    jo.addProperty("id", Integer.parseInt(nid));
                    break;
                case "pdfStatus":
                    jo.addProperty("pdfStatus", dataTable.get("pdfStatus"));
                    break;
                case "pdfReasonText":
                    jo.addProperty("pdfReasonText", dataTable.get("pdfReasonText"));
                    break;
                case "addressStatus":
                    jo.addProperty("addressStatus", dataTable.get("addressStatus"));
                    break;
                case "addressReasonText":
                    jo.addProperty("addressReasonText", dataTable.get("addressReasonText"));
                    break;
                default:
                    org.testng.Assert.fail("no matching cases");
            }
        }

        try {
            map.put("letter", new Object[]{jo});
            writer = new FileWriter("src\\test\\resources\\testData\\api\\dms\\temp\\responsefile.json");
            // convert map to JSON File
            new Gson().toJson(map, writer);
            // close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setfilename(String fname, String extension) {
        /*String filename;
        if ("previouslyCreated".equalsIgnoreCase(fname))
            filename= World.generalSave.get().get("nid").toString();
        else
            filename = fname;*/
        fileName.set(fname + "." + extension);
    }
}
