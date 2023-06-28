package com.maersk.dms.beans.outboundCorrespondence;

import com.github.javafaker.Faker;
import com.maersk.dms.pages.OutboundCorrespondenceSettingsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceSettings {
    public String defaultLanguage;
    public String otherLanguages;
    public String vendorEmail;
    public String outboundCorrespondence;
    public String outboundCorrespondenceTemplate;
    public String vendorCompanyName;
    public String vendorContactName;
    public String vendorPhone;
    public String outboundFileFormat;
    public String ftpHost;
    public String ftpUser;
    public String ftpPassword;
    public String ftpPort;
    public String ftpFolder;
    public String inboundFilePathResponse;
    public String inboundFilePathPDF;
    public String smtpHost;
    public String smtpUser;
    public String smtpPassword;
    public String smtpPort;
    public String smtpSenderEmail;
    public String smtpSenderEmailName;

//    private OutboundCorrespondenceSettingsPage outboundCorrespondenceSettingsPage = new OutboundCorrespondenceSettingsPage();

    public void createRandomValues() {
        Faker faker = new Faker();
//        this.defaultLanguage = String.valueOf(getDropDownValues(outboundCorrespondenceSettingsPage.defaultLanguage).get(faker.random().nextInt(getDropDownValues(outboundCorrespondenceSettingsPage.defaultLanguage).size())));
//        this.otherLanguages = String.valueOf(getDropDownValues(outboundCorrespondenceSettingsPage.otherLanguages).get(faker.random().nextInt(getDropDownValues(outboundCorrespondenceSettingsPage.otherLanguages).size())));
        this.defaultLanguage = "English (Large Print)";
        this.otherLanguages = "English";
        this.outboundFileFormat = "CSV";
        this.vendorCompanyName = RandomStringUtils.random(40, true, true);
        this.vendorContactName = RandomStringUtils.random(40, true, true);
        this.vendorPhone = RandomStringUtils.random(10, false, true);
        this.vendorEmail = RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(7, true, false) + ".com";
        this.outboundCorrespondence = RandomStringUtils.random(10, true, false);;
        this.outboundCorrespondenceTemplate = RandomStringUtils.random(10, true, false);;
        this.ftpHost = RandomStringUtils.random(249, true, true) + "!";
        this.ftpUser = RandomStringUtils.random(32, true, true);
        this.ftpPassword = RandomStringUtils.random(32, true, true);
        this.ftpFolder = RandomStringUtils.random(32, true, true);
        this.ftpPort = RandomStringUtils.random(4, false, true);
        this.inboundFilePathResponse = RandomStringUtils.random(249, true, true) + "!";
        this.inboundFilePathPDF = RandomStringUtils.random(249, true, true) + "!";
        this.smtpHost = RandomStringUtils.random(249, true, true) + "!";
        this.smtpUser = RandomStringUtils.random(32, true, true);
        this.smtpPassword = RandomStringUtils.random(32, true, true);
        this.smtpPort = RandomStringUtils.random(4, false, true);
        this.smtpSenderEmail = "muhammadyasin@maersk.com"; //static value bc of limited valid users for send grid service
        this.smtpSenderEmailName = RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(7, true, false) + ".com";
        returnValues();
    }

    public Map<String, String> returnValues() {
        Map<String, String> outBoundCorrespondenceDefinition = new HashMap<>();
        Field[] randomValues = getWorld().outboundCorrespondenceDefinition.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                outBoundCorrespondenceDefinition.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                outBoundCorrespondenceDefinition.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
        return outBoundCorrespondenceDefinition;
    }

    private List<WebElement> getDropDownValues(WebElement element) {
        return new Select(element).getOptions();
    }
}
