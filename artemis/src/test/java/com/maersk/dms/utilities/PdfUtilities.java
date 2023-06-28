package com.maersk.dms.utilities;

import com.maersk.crm.utilities.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class PdfUtilities extends CRMUtilities implements ApiBase {


    private static WebDriver driver = Driver.getDriver();


    public static void verifyPdfContents(String expectedText) {
        String currentUrl = driver.getCurrentUrl();
        PDFTextStripper pdfTextStripper = null;
        PDDocument pdDocument = null;
        String parsedText = null;

        try {
            URL url = new URL((currentUrl));
            InputStream inputStream = url.openStream();
            BufferedInputStream file = new BufferedInputStream(inputStream);
            pdDocument = PDDocument.load(file);
            pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setStartPage(1);
            pdfTextStripper.setEndPage(5);
            parsedText = new PDFTextStripper().getText(pdDocument);
        } catch (MalformedURLException z) {
            z.printStackTrace();
            System.out.println("Error : URL could not be parsed");
        } catch (IOException x) {
            x.printStackTrace();
            System.out.println("Error : Unable to open PDF Parser");
            try {
                if (pdDocument != null) pdDocument.close();
            } catch (Exception w) {
                w.printStackTrace();
            }
        }
        if (parsedText != null) assertTrue(parsedText.contains(expectedText));
        //  assertEquals(parsedText.replaceAll("\\s+",""),expectedText);
    }

    public static List<File> getFolderContents(String fileLocation) {
        try {
//            BrowserUtils.classLoader(fileLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File folder = new File(fileLocation);
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return files;
    }

    public String getPdfTextByNotificationID(String NID) throws IOException {
        String URL = ConfigurationReader.getProperty("apiProxyBffCorrespondence") + "/ecms/document/contents/notification/" + NID + "?format=pdf";
        BrowserUtils.waitFor(5);
        if (EventsUtilities.getRawLogs(URL).size() > 0) {
            byte[] res = given().header("Authorization", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getTokenThreadLocal())
                    .header("Project-Name", API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().getApiProjectNameThreadLocal())
                    .contentType("application/pdf")
                    .get(ConfigurationReader.getProperty("apiBffCorrespondence") + "/ecms/document/contents/notification/" + NID + "?format=pdf").then()
                    .statusCode(200)
                    .extract()
                    .asByteArray();
            PDDocument document = PDDocument.load(new ByteArrayInputStream(res));
            return new PDFTextStripper().getText(document);
        } else {
            Assert.fail(URL);
        }
        return null;
    }


}
