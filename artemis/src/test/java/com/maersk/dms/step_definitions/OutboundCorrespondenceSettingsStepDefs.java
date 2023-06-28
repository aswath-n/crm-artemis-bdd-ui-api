package com.maersk.dms.step_definitions;

import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.Driver;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionListPage;
import com.maersk.dms.pages.OutboundCorrespondenceDefinitionPage;
import com.maersk.dms.pages.OutboundCorrespondenceSettingsPage;
import com.maersk.dms.utilities.UIAutoUitilities;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;

public class OutboundCorrespondenceSettingsStepDefs extends BrowserUtils{
    private final ThreadLocal<UIAutoUitilities> uiAutoUitilities= ThreadLocal.withInitial(UIAutoUitilities::new);
    private OutboundCorrespondenceSettingsPage outboundCorrespondenceSettingsPage = new OutboundCorrespondenceSettingsPage();
    private OutboundCorrespondenceDefinitionPage outboundCorrespondenceDefinitionPage = new OutboundCorrespondenceDefinitionPage();
    private OutboundCorrespondenceDefinitionListPage outboundCorrespondenceDefinitionListPage = new OutboundCorrespondenceDefinitionListPage();

    public void createRandomValues() {
        World.createNewWorld();
        World.getWorld().outboundCorrespondenceSettings.get().createRandomValues();
    }

    public void changeValue(String field, String value) {
        switch (field) {
            case "defaultLanguage":
                World.getWorld().outboundCorrespondenceSettings.get().defaultLanguage = value;
                break;
            case "otherLanguages":
                World.getWorld().outboundCorrespondenceSettings.get().otherLanguages = value;
                break;
            case "vendorCompanyName":
                World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName = value;
                break;
            case "vendorContactName":
                World.getWorld().outboundCorrespondenceSettings.get().vendorContactName = value;
                break;
            case "vendorPhone":
                World.getWorld().outboundCorrespondenceSettings.get().vendorPhone = value;
                break;
            case "vendorEmail":
                World.getWorld().outboundCorrespondenceSettings.get().vendorEmail = value;
                break;
            case "outboundFileFormat":
                World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat = value;
                break;
            case "ftpHost":
                World.getWorld().outboundCorrespondenceSettings.get().ftpHost = value;
                break;
            case "ftpUser":
                World.getWorld().outboundCorrespondenceSettings.get().ftpUser = value;
                break;
            case "ftpPassword":
                World.getWorld().outboundCorrespondenceSettings.get().ftpPassword = value;
                break;
            case "ftpPort":
                World.getWorld().outboundCorrespondenceSettings.get().ftpPort = value;
                break;
            case "ftpFolder":
                World.getWorld().outboundCorrespondenceSettings.get().ftpFolder = value;
                break;
            case "inboundFilePathResponse":
                World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathResponse = value;
                break;
            case "inboundFilePathPDF":
                World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathPDF = value;
                break;
            case "smtpHost":
                World.getWorld().outboundCorrespondenceSettings.get().smtpHost = value;
                break;
            case "smtpUser":
                World.getWorld().outboundCorrespondenceSettings.get().smtpUser = value;
                break;
            case "smtpPassword":
                World.getWorld().outboundCorrespondenceSettings.get().smtpPassword = value;
                break;
            case "smtpPort":
                World.getWorld().outboundCorrespondenceSettings.get().smtpPort = value;
                break;
            case "smtpSenderEmail":
                World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail = value;
                break;
            case "smtpSenderEmailName":
                World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName = value;
                break;
            default:
                Assert.fail("Check Spelling of Cases: " + "Field = " + field + " | Value = " + value);
        }
    }

    public void fillOutUIFields(String field, String value) {
        switch (field) {
            case "defaultLanguage":
                uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.defaultLanguage, value);
                break;
            case "otherLanguage":
                outboundCorrespondenceSettingsPage.otherLanguages.sendKeys(value + Keys.ENTER);
                break;
            case "otherLanguage2"://unique case only for 181-6
                hover(outboundCorrespondenceSettingsPage.otherLanguages);
                outboundCorrespondenceSettingsPage.otherLanguageDelete.click();
                waitFor(1);
                outboundCorrespondenceSettingsPage.otherLanguages.click();
                waitFor(1);
                outboundCorrespondenceSettingsPage.otherLanguages.sendKeys(value + Keys.RETURN);
                break;
            case "otherLanguageAuto":
                outboundCorrespondenceSettingsPage.otherLanguages.sendKeys(value);
                break;
            case "otherLanguageDropdown"://unique case only for 181-4
                outboundCorrespondenceSettingsPage.otherLanguages.click();
                waitFor(1);
               // outboundCorrespondenceSettingsPage.otherLanguages.sendKeys(value + Keys.RETURN);
                outboundCorrespondenceSettingsPage.otherLanguages.sendKeys(value);
                waitFor(1);
                break;
            case "vendorCompanyName":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorCompanyName, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorCompanyName, value);
                break;
            case "vendorContactName":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorContactName, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorContactName, value);
                break;
            case "vendorPhone":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorPhone, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorPhone, value);
                break;
            case "vendorEmail":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorEmail, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorEmail, value);
                break;
            case "outboundFileFormat":
                uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.outboundFileFormat, value);
                break;
            case "ftpHost":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpHost, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpHost, value);
                break;
            case "ftpUser":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpUser, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpUser, value);
                break;
            case "ftpPassword":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPassword, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPassword, value);
                break;
            case "ftpPort":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPort, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPort, value);
                break;
            case "ftpFolder":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpFolder, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpFolder, value);
                break;
            case "inboundFilePathResponse":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.inboundFilePathResponse, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.inboundFilePathResponse, value);
                break;
            case "inboundFilePathPDF":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.inboundFilePathPDF, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.inboundFilePathPDF, value);
                break;
            case "smtpHost":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpHost, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpHost, value);
                break;
            case "smtpUser":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpUser, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpUser, value);
                break;
            case "smtpPassword":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPassword, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPassword, value);
                break;
            case "smtpPort":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPort, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPort, value);
                break;
            case "smtpSenderEmail":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmail, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmail, value);
                break;
            case "smtpSenderEmailName":
                if (value.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmailName, "");
                    break;
                }
                uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmailName, value);
                break;
            default:
                Assert.fail("Check Spelling of Cases");

        }
    }

    public void fillOutUIFields() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("defaultLanguage", World.getWorld().outboundCorrespondenceSettings.get().defaultLanguage);
        values.put("otherLanguages", World.getWorld().outboundCorrespondenceSettings.get().otherLanguages);
        values.put("outboundCorrespondence", World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondence);
        values.put("outboundCorrespondenceTemplate", World.getWorld().outboundCorrespondenceSettings.get().outboundCorrespondenceTemplate);
        values.put("vendorCompanyName", World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName);
        values.put("vendorContactName", World.getWorld().outboundCorrespondenceSettings.get().vendorContactName);
        values.put("vendorPhone", World.getWorld().outboundCorrespondenceSettings.get().vendorPhone);
        values.put("vendorEmail", World.getWorld().outboundCorrespondenceSettings.get().vendorEmail);
        values.put("outboundFileFormat", World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat);
        values.put("ftpHost", World.getWorld().outboundCorrespondenceSettings.get().ftpHost);
        values.put("ftpUser", World.getWorld().outboundCorrespondenceSettings.get().ftpUser);
        values.put("ftpPassword", World.getWorld().outboundCorrespondenceSettings.get().ftpPassword);
        values.put("ftpPort", World.getWorld().outboundCorrespondenceSettings.get().ftpPort);
        values.put("ftpFolder", World.getWorld().outboundCorrespondenceSettings.get().ftpFolder);
        values.put("smtpHost", World.getWorld().outboundCorrespondenceSettings.get().smtpHost);
        values.put("smtpUser", World.getWorld().outboundCorrespondenceSettings.get().smtpUser);
        values.put("smtpPassword", World.getWorld().outboundCorrespondenceSettings.get().smtpPassword);
        values.put("smtpPort", World.getWorld().outboundCorrespondenceSettings.get().smtpPort);
        values.put("smtpSenderEmail", World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail);
        values.put("smtpSenderEmailName", World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName);
        for (Map.Entry<String, String> element : values.entrySet()) {
            switch (element.getKey()) {
                case "defaultLanguage":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        break;
                    }
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.defaultLanguage, element.getValue());
                    break;
                case "otherLanguages":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        break;
                    }
                    uiAutoUitilities.get().selectDropDown2(outboundCorrespondenceSettingsPage.otherLanguages, element.getValue());
                    break;
                case "outboundCorrespondence":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.outboundCorrespondence, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.outboundCorrespondence, element.getValue());
                    break;
                case "outboundCorrespondenceTemplate":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.outboundCorrespondenceTemplate, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.outboundCorrespondenceTemplate, element.getValue());
                    break;
                case "vendorCompanyName":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorCompanyName, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorCompanyName, element.getValue());
                    break;
                case "vendorContactName":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorContactName, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorContactName, element.getValue());
                    break;
                case "vendorPhone":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorPhone, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorPhone, element.getValue());
                    break;
                case "vendorEmail":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorEmail, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.vendorEmail, element.getValue());
                    break;
                case "outboundFileFormat":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        break;
                    }
                    uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.outboundFileFormat, element.getValue());
                    break;
                case "ftpHost":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpHost, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpHost, element.getValue());
                    break;
                case "ftpUser":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpUser, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpUser, element.getValue());
                    break;
                case "ftpPassword":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPassword, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPassword, element.getValue());
                    break;
                case "ftpPort":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPort, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpPort, element.getValue());
                    break;
                case "ftpFolder":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpFolder, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.ftpFolder, element.getValue());
                    break;
                case "smtpHost":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpHost, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpHost, element.getValue());
                    break;
                case "smtpUser":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpUser, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpUser, element.getValue());
                    break;
                case "smtpPassword":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPassword, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPassword, element.getValue());
                    break;
                case "smtpPort":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPort, "");
                        break;
                    }
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpPort, element.getValue());
                    break;
                case "smtpSenderEmail":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        break;
                    }
                    if (!outboundCorrespondenceSettingsPage.emailChannel.isSelected()) {
                        Assert.assertFalse(outboundCorrespondenceSettingsPage.smtpSenderEmail.isEnabled());
                        break;
                    }
                    save();
                    waitFor(2);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmail, element.getValue());
                    break;
                case "smtpSenderEmailName":
                    if (element.getValue().equalsIgnoreCase("null")) {
                        break;
                    }
                    if (!outboundCorrespondenceSettingsPage.emailChannel.isSelected()) {
                        Assert.assertFalse(outboundCorrespondenceSettingsPage.smtpSenderEmailName.isEnabled());
                        break;
                    }
                    save();
                    waitFor(2);
                    uiAutoUitilities.get().clearFillWithActions(outboundCorrespondenceSettingsPage.smtpSenderEmailName, element.getValue());
                    break;
                default:
                    Assert.fail("Check Spelling of Cases");
            }
        }
    }

    public void assertFailMessage(String message) {
        waitForVisibility(outboundCorrespondenceSettingsPage.getFailMessage(message), 7);
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getFailMessage(message).isDisplayed());
    }

    public void removeValueFromOtherLanguageBox(String value) {
        WebElement removeButton = Driver.getDriver().findElement(By.xpath("//*[text()='" + value + "']//parent::div/*[2]"));
        removeButton.click();
    }

    public void verifySaveSuccessfulPopup() {
        waitFor(1);
        waitForVisibility(outboundCorrespondenceSettingsPage.getFailMessage("Correspondence Configuration successfully updated."), 7);
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getFailMessage("Correspondence Configuration successfully updated.").isDisplayed());
    }

    public void save() {
        scrollToElement(outboundCorrespondenceSettingsPage.saveButton);
        outboundCorrespondenceSettingsPage.saveButton.click();
    }

    public void selectChannel(String channel) {
        switch (channel.toLowerCase()) {
            case "email":
                if (outboundCorrespondenceSettingsPage.emailChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.emailChannel.click();
                break;
            case "fax":
                if (outboundCorrespondenceSettingsPage.faxChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.faxChannel.click();
                break;
            case "text":
                if (outboundCorrespondenceSettingsPage.textChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.textChannel.click();
                break;
            case "mail":
                if (outboundCorrespondenceSettingsPage.mailChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.mailChannel.click();
                break;
            case "web portal":
                if (outboundCorrespondenceSettingsPage.webPortalChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.webPortalChannel.click();
                break;
            case "mobile app":
                if (outboundCorrespondenceSettingsPage.mobileAppChannel.isSelected()) {
                    break;
                }
                outboundCorrespondenceSettingsPage.mobileAppChannel.click();
                break;
            default:
                Assert.fail("Check Spelling of Cases: " + "Channel = " + channel);
        }
    }

    public void fillOutUIFieldsVerifyData(String field) {
        switch (field) {
            case "defaultLanguage":
                uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.defaultLanguage, World.getWorld().outboundCorrespondenceSettings.get().defaultLanguage);
                break;
            case "otherLanguages":
                uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.otherLanguages, World.getWorld().outboundCorrespondenceSettings.get().otherLanguages);
                break;
            case "vendorCompanyName":
                if (World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorCompanyName, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorCompanyName, World.getWorld().outboundCorrespondenceSettings.get().vendorCompanyName);
                break;
            case "vendorContactName":
                if (World.getWorld().outboundCorrespondenceSettings.get().vendorContactName.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorContactName, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorContactName, World.getWorld().outboundCorrespondenceSettings.get().vendorContactName);
                break;
            case "vendorPhone":
                if (World.getWorld().outboundCorrespondenceSettings.get().vendorPhone.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorPhone, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorPhone, World.getWorld().outboundCorrespondenceSettings.get().vendorPhone);
                break;
            case "vendorEmail":
                if (World.getWorld().outboundCorrespondenceSettings.get().vendorEmail.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorEmail, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.vendorEmail, World.getWorld().outboundCorrespondenceSettings.get().vendorEmail);
                break;
            case "outboundFileFormat":
                if (World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat.equalsIgnoreCase("null")) {
                    break;
                }
                uiAutoUitilities.get().selectDropDown(outboundCorrespondenceSettingsPage.outboundFileFormat, World.getWorld().outboundCorrespondenceSettings.get().outboundFileFormat);
                break;
            case "ftpHost":
                if (World.getWorld().outboundCorrespondenceSettings.get().ftpHost.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpHost, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpHost, World.getWorld().outboundCorrespondenceSettings.get().ftpHost);
                break;
            case "ftpUser":
                if (World.getWorld().outboundCorrespondenceSettings.get().ftpUser.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpUser, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpUser, World.getWorld().outboundCorrespondenceSettings.get().ftpUser);
                break;
            case "ftpPassword":
                if (World.getWorld().outboundCorrespondenceSettings.get().ftpPassword.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpPassword, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpPassword, World.getWorld().outboundCorrespondenceSettings.get().ftpPassword);
                break;
            case "ftpPort":
                if (World.getWorld().outboundCorrespondenceSettings.get().ftpPort.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpPort, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpPort, World.getWorld().outboundCorrespondenceSettings.get().ftpPort);
                break;
            case "ftpFolder":
                if (World.getWorld().outboundCorrespondenceSettings.get().ftpFolder.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpFolder, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.ftpFolder, World.getWorld().outboundCorrespondenceSettings.get().ftpFolder);
                break;
            case "inboundFilePathResponse":
                if (World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathResponse.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.inboundFilePathResponse, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.inboundFilePathResponse, World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathResponse);
                break;
            case "inboundFilePathPDF":
                if (World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathPDF.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.inboundFilePathPDF, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.inboundFilePathPDF, World.getWorld().outboundCorrespondenceSettings.get().inboundFilePathPDF);
                break;
            case "smtpHost":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpHost.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpHost, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpHost, World.getWorld().outboundCorrespondenceSettings.get().smtpHost);
                break;
            case "smtpUser":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpUser.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpUser, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpUser, World.getWorld().outboundCorrespondenceSettings.get().smtpUser);
                break;
            case "smtpPassword":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpPassword.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpPassword, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpPassword, World.getWorld().outboundCorrespondenceSettings.get().smtpPassword);
                break;
            case "smtpPort":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpPort.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpPort, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpPort, World.getWorld().outboundCorrespondenceSettings.get().smtpPort);
                break;
            case "smtpSenderEmail":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpSenderEmail, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpSenderEmail, World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmail);
                break;
            case "smtpSenderEmailName":
                if (World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName.equalsIgnoreCase("null")) {
                    uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpSenderEmailName, "");
                    break;
                }
                uiAutoUitilities.get().typeAndVerify(outboundCorrespondenceSettingsPage.smtpSenderEmailName, World.getWorld().outboundCorrespondenceSettings.get().smtpSenderEmailName);
                break;
            default:
                Assert.fail("Check Spelling of Cases - Field = " + field);
        }
    }

    public void navigateAwayDiscardChanges() {
        outboundCorrespondenceDefinitionPage.backButton.click();
        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceDefinitionListPage.header)) {
            return;
        }
        waitForVisibility(outboundCorrespondenceDefinitionListPage.discardPopUp, 7);
        outboundCorrespondenceDefinitionListPage.discardPopUp.click();
        waitForVisibility(outboundCorrespondenceDefinitionListPage.header, 7);
    }

    public void verifyFieldData(String field, String value) {
        switch (field) {
            case "defaultLanguage":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.defaultLanguage.getText().trim(), value);
                break;
            case "otherLanguages":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.otherLanguages.getAttribute("value"), value);
                break;
            case "otherLanguage":
                List<String> listOfOtherLanguages = getElementsText(outboundCorrespondenceSettingsPage.otherLanguagesSelectedValues);
                Assert.assertTrue(listOfOtherLanguages.contains(value));
                break;
            case "vendorCompanyName":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.vendorCompanyName.getAttribute("value"), value);
                break;
            case "vendorContactName":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.vendorContactName.getAttribute("value"), value);
                break;
            case "vendorPhone":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.vendorPhone.getAttribute("value"), value);
                break;
            case "vendorEmail":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.vendorEmail.getAttribute("value"), value);
                break;
            case "outboundFileFormat":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.outboundFileFormat.getAttribute("value"), value);
                break;
            case "ftpHost":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.ftpHost.getAttribute("value"), value);
                break;
            case "ftpUser":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.ftpUser.getAttribute("value"), value);
                break;
            case "ftpPassword":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.ftpPassword.getAttribute("value"), value);
                break;
            case "ftpPort":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.ftpPort.getAttribute("value"), value);
                break;
            case "ftpFolder":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.ftpFolder.getAttribute("value"), value);
                break;
            case "inboundFilePathResponse":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.inboundFilePathResponse.getAttribute("value"), value);
                break;
            case "inboundFilePathPDF":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.inboundFilePathPDF.getAttribute("value"), value);
                break;
            case "smtpHost":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpHost.getAttribute("value"), value);
                break;
            case "smtpUser":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpUser.getAttribute("value"), value);
                break;
            case "smtpPassword":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpPassword.getAttribute("value"), value);
                break;
            case "smtpPort":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpPort.getAttribute("value"), value);
                break;
            case "smtpSenderEmail":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpSenderEmail.getAttribute("value"), value);
                break;
            case "smtpSenderEmailName":
                Assert.assertEquals(outboundCorrespondenceSettingsPage.smtpSenderEmailName.getAttribute("value"), value);
                break;
            default:
                Assert.fail("Check Spelling of Cases - Field = " + field);
        }
    }

    public void verifyWebelementIsDisplayed(String webElement) {
        switch (webElement) {
            case "defaultLanguage":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.defaultLanguage.isDisplayed());
                break;
            case "otherLanguages":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.otherLanguages.isDisplayed());
                break;
            case "vendorCompanyName":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.vendorCompanyName.isDisplayed());
                break;
            case "vendorContactName":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.vendorContactName.isDisplayed());
                break;
            case "vendorPhone":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.vendorPhone.isDisplayed());
                break;
            case "vendorEmail":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.vendorEmail.isDisplayed());
                break;
            case "outboundFileFormat":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.outboundFileFormat.isDisplayed());
                break;
            case "ftpHost":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.ftpHost.isDisplayed());
                break;
            case "ftpUser":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.ftpUser.isDisplayed());
                break;
            case "ftpPassword":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.ftpPassword.isDisplayed());
                break;
            case "ftpPort":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.ftpPort.isDisplayed());
                break;
            case "ftpFolder":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.ftpFolder.isDisplayed());
                break;
            case "inboundFilePathResponse":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundFilePathResponse.isDisplayed());
                break;
            case "inboundFilePathPDF":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundFilePathPDF.isDisplayed());
                break;
            case "smtpHost":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpHost.isDisplayed());
                break;
            case "smtpUser":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpUser.isDisplayed());
                break;
            case "smtpPassword":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpPassword.isDisplayed());
                break;
            case "smtpPort":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpPort.isDisplayed());
                break;
            case "smtpSenderEmail":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpSenderEmail.isDisplayed());
                break;
            case "smtpSenderEmailName":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.smtpSenderEmailName.isDisplayed());
                break;
            case "autoComplete":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.presentationPopUp.isDisplayed());
                break;
            default:
                Assert.fail("Check Spelling of Cases - Field = " + webElement);
        }
    }

    public void ifEditButtonThenClick() {
        if (uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceSettingsPage.editSettingsButton)) {
            synchronized (World.configSettingsPresent){
                World.configSettingsPresent.set(true);
            }
            outboundCorrespondenceSettingsPage.editSettingsButton.click();
        }
        waitFor(3);
    }

    public void verifyNotInDropDown(String value) {
        outboundCorrespondenceSettingsPage.otherLanguagesInput.click();
        waitFor(1);
        if ("Spanish".equalsIgnoreCase(value)) {
            Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceSettingsPage.otherLanguageSpanish));
        } else if ("English".equalsIgnoreCase(value)) {
            Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(outboundCorrespondenceSettingsPage.otherLanguageEnglish));
        }
    }

    public void verifyChannels() {
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("WEB PORTAL").getText().equalsIgnoreCase("WEB PORTAL"));
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("EMAIL").getText().equalsIgnoreCase("EMAIL"));
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("FAX").getText().equalsIgnoreCase("FAX"));
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("MAIL").getText().equalsIgnoreCase("MAIL"));
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("MOBILE APP").getText().equalsIgnoreCase("MOBILE APP"));
        Assert.assertTrue(outboundCorrespondenceSettingsPage.getChannelLabel("TEXT").getText().equalsIgnoreCase("TEXT"));
    }

    public void verifyLanguages() {
        outboundCorrespondenceSettingsPage.defaultLanguage.click();
        waitFor(1);
        List<WebElement> actualLanguages = outboundCorrespondenceSettingsPage.getDefaultLanguages();
        List<String> expectedLanguages = new ArrayList<>();
        expectedLanguages.add("English");
        expectedLanguages.add("Spanish");
        expectedLanguages.add("Haitian Creole");
        expectedLanguages.add("English (Large Print)");
        expectedLanguages.add("Spanish (Large Print)");
        expectedLanguages.add("Alaskan Athabascan");
        expectedLanguages.add("Aleut");
        expectedLanguages.add("American Indian (Western Apache)");
        expectedLanguages.add("American Indian (Choctaw)");
        expectedLanguages.add("American Indian (Navajo)");
        expectedLanguages.add("American Indian (Pueblo - Tiwa)");
        expectedLanguages.add("American Indian (Ute)");
        expectedLanguages.add("Arabic");
        expectedLanguages.add("Armenian");
        expectedLanguages.add("Bangladeshi");
        expectedLanguages.add("Bengali");
        expectedLanguages.add("Bosnian");
        expectedLanguages.add("Cambodian (Khmer)");
        expectedLanguages.add("Carolinian");
        expectedLanguages.add("Chamorro");
        expectedLanguages.add("Chinese (Cantonese)");
        expectedLanguages.add("Chinese (Mandarin)");
        expectedLanguages.add("Farsi");
        expectedLanguages.add("Filipino");
        expectedLanguages.add("French");
        expectedLanguages.add("German");
        expectedLanguages.add("Greek");
        expectedLanguages.add("Gujarati");
        expectedLanguages.add("Hawaiian");
        expectedLanguages.add("Hindi");
        expectedLanguages.add("Hmong");
        expectedLanguages.add("Ilocano");
        expectedLanguages.add("Inupiat");
        expectedLanguages.add("Italian");
        expectedLanguages.add("Japanese");
        expectedLanguages.add("Korean");
        expectedLanguages.add("Laotian");
        expectedLanguages.add("Polish");
        expectedLanguages.add("Portuguese");
        expectedLanguages.add("Punjabi");
        expectedLanguages.add("Russian");
        expectedLanguages.add("Samoan");
        expectedLanguages.add("Somali");
        expectedLanguages.add("Tagalog");
        expectedLanguages.add("Urdu");
        expectedLanguages.add("Vietnamese");
        expectedLanguages.add("Yupik");
        for (String expectedLanguage : expectedLanguages) {
            boolean found = false;
            for (WebElement actualLanguage : actualLanguages) {
                if (expectedLanguage.equalsIgnoreCase(actualLanguage.getText())) {
                    Assert.assertEquals(expectedLanguage, actualLanguage.getText());
                    found = true;
                }
            }
            if (!found) Assert.fail(expectedLanguage + " not found");
        }
    }

    public void enableInboundChannel(String channel) {
        waitFor(5);
        switch (channel) {
            case "Mobile App":
                if (outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Web Chat":
                if (outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Web Portal":
                if (outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Mail":
                if (outboundCorrespondenceSettingsPage.inboundMailCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundMailCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Email":
                if (outboundCorrespondenceSettingsPage.inboundEmailCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundEmailCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Text":
                if (outboundCorrespondenceSettingsPage.inboundTextCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundTextCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Fax":
                if (outboundCorrespondenceSettingsPage.inboundFaxCheckbox.getAttribute("value").equalsIgnoreCase("false")) {
                    outboundCorrespondenceSettingsPage.inboundFaxCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
        }
    }

    public void disableInboundChannel(String channel) {
        waitFor(5);
        switch (channel) {
            case "Mobile App":
                if (outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Web Chat":
                if (outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Web Portal":
                if (outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Mail":
                if (outboundCorrespondenceSettingsPage.inboundMailCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundMailCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Email":
                if (outboundCorrespondenceSettingsPage.inboundEmailCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundEmailCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Text":
                if (outboundCorrespondenceSettingsPage.inboundTextCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundTextCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
            case "Fax":
                if (outboundCorrespondenceSettingsPage.inboundFaxCheckbox.getAttribute("value").equalsIgnoreCase("true")) {
                    outboundCorrespondenceSettingsPage.inboundFaxCheckbox.click();
                    waitFor(2);
                    break;
                } else {
                    waitFor(1);
                    break;
                }
        }
    }

    public void verifyEnableInboundChannel(String channel) {
        waitFor(5);
        switch (channel) {
            case "Mobile App":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Web Chat":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Web Portal":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Mail":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundMailCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Email":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundEmailCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Text":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundTextCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Fax":
                Assert.assertTrue(outboundCorrespondenceSettingsPage.inboundFaxCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
        }
    }

    public void verifyDisableInboundChannel(String channel) {
        switch (channel) {
            case "Mobile App":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundMobileAppCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Web Chat":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundWebChatCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Web Portal":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundWebPortalCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Mail":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundMailCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Email":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundEmailCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Text":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundTextCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
            case "Fax":
                Assert.assertFalse(outboundCorrespondenceSettingsPage.inboundFaxCheckbox.getAttribute("value").equalsIgnoreCase("true"));
                break;
        }
    }

    public void validateibfieldvaluesIBConfigurations(List<String> dataTable) {
        waitFor(3);
        outboundCorrespondenceDefinitionListPage.ibtypedropdown.click();
        waitFor(3);
        List<WebElement> ibtypes = outboundCorrespondenceDefinitionListPage.ibtypedropdownvalues;
        for (String expectedtype : dataTable) {
            boolean status = false;
            for (WebElement actualtype : ibtypes) {
                if (actualtype.getText().equalsIgnoreCase(expectedtype)) {
                    System.out.println(expectedtype + " : avaialble in type dropdown");
                    status = true;
                    break;
                }
            }
            Assert.assertTrue(status,expectedtype +"values invisible in Ib type dropdown in  Outbound Correspondence Definition page");

        }
    }
}
