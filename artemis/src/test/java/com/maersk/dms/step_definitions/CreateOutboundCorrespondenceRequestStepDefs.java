package com.maersk.dms.step_definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maersk.crm.utilities.*;
import com.maersk.dms.pages.CaseAndContactDetailsPage;
import com.maersk.dms.pages.CreateOutboundCorrespondencePage;
import com.maersk.dms.utilities.EventsUtilities;
import com.maersk.dms.utilities.UIAutoUitilities;
import io.cucumber.java.en.And;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static com.maersk.crm.utilities.World.*;
import static org.testng.Assert.assertTrue;

public class CreateOutboundCorrespondenceRequestStepDefs extends CRMUtilities implements ApiBase {

    CreateOutboundCorrespondencePage page = new CreateOutboundCorrespondencePage();

    private final ThreadLocal<String> Thirdpartycontact_FirstName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<String> Thirdpartycontact_LastName = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<List<String>> nonregarding_type = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal <UIAutoUitilities> uiAutoUitilities = ThreadLocal.withInitial(UIAutoUitilities::new);
    CaseAndContactDetailsPage caseAndContactDetailsPage = new CaseAndContactDetailsPage();
    final ThreadLocal<Random> random = ThreadLocal.withInitial(Random::new);
    private static final ThreadLocal<String> caseId = ThreadLocal.withInitial(String::new);
    private final ThreadLocal<JsonPath> recipientsJson = new ThreadLocal<>();


    public void clickonthirdpartycontact() {
        page.thirdpartycontact.click();
        waitFor(2);
        Assert.assertTrue(page.thirdpartycontact_firstname.isDisplayed());

    }

    public void enterthirdpartycontactdetails(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "FirstName":
                    if ("Random".equalsIgnoreCase(dataTable.get("FirstName")))
                        Thirdpartycontact_FirstName.set( RandomStringUtils.random(11, true, false));
                    else
                        Thirdpartycontact_FirstName.set(dataTable.get("FirstName"));
                    page.thirdpartycontact_firstname.sendKeys(Thirdpartycontact_FirstName.get());
                    break;
                case "LastName":
                    if ("Random".equalsIgnoreCase(dataTable.get("LastName")))
                        Thirdpartycontact_LastName.set(RandomStringUtils.random(11, true, false));
                    else
                        Thirdpartycontact_LastName.set(dataTable.get("LastName"));
                    page.thirdpartycontact_lastname.sendKeys(Thirdpartycontact_LastName.get());
                    break;

                default:
                    throw new NotImplementedException("DataTable did not match an existing key, value must match keys in table | " + "dataTable value = " + data);
            }

        }
    }

    public void validateSendToSection(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "FirstName":
                    waitFor(1);
                    boolean fn_status = page.sendto_firstname.isDisplayed() && page.sendto_firstname.isEnabled();
                    Assert.assertEquals(true, fn_status);
                    break;
                case "LastName":
                    waitFor(1);
                    boolean ln_status = page.sendto_lastname.isDisplayed() && page.sendto_lastname.isEnabled();
                    Assert.assertEquals(true, ln_status);
                    break;
                case "Checkbox":
                    waitFor(1);
                    String expected_status = String.valueOf(page.sendto_checkmail.isSelected());
                    Assert.assertEquals(dataTable.get("Checkbox"), expected_status);
                    break;
                case "AddressLine1":
                    waitFor(1);
                    boolean ad1_status = page.sendto_addressline1.isDisplayed() && page.sendto_addressline1.isEnabled();
                    Assert.assertEquals(true, ad1_status);
                    break;

                case "AddressLine2":
                    waitFor(1);
                    boolean ad2_status = page.sendto_addressline2.isDisplayed() && page.sendto_addressline2.isEnabled();
                    Assert.assertEquals(true, ad2_status);
                    break;

                case "City":
                    waitFor(1);
                    boolean city_status = page.sendto_city_dropdown.isDisplayed() && page.sendto_city_dropdown.isEnabled();
                    Assert.assertEquals(true, city_status);
                    break;
                case "State":
                    waitFor(1);
                    boolean state_status = page.sendto_state_dropdown.isDisplayed() && page.sendto_state_dropdown.isEnabled();
                    Assert.assertEquals(true, state_status);
                    break;
                case "Zipcode":
                    waitFor(1);
                    boolean zipcode_status = page.sendto_zipcode_dropdown.isDisplayed() && page.sendto_zipcode_dropdown.isEnabled();
                    Assert.assertEquals(true, zipcode_status);
                    break;

                default:
                    throw new NotImplementedException("DataTable did not match an existing key, value must match keys in table | " + "dataTable value = " + data);
            }

        }
    }

    public void validate_SendTo_fields_errormessages() {
        waitFor(5);
        page.saveButton.click();
        waitFor(2);
        Assert.assertEquals("ADDRESS LINE 1 is required and cannot be left blank", page.addressline1_errormessage.getText());
        Assert.assertEquals("CITY is required and cannot be left blank", page.city_errormessage.getText());
        Assert.assertEquals("STATE is required and cannot be left blank", page.state_errormessage.getText());
        Assert.assertEquals("ZIP CODE is required and cannot be left blank", page.zipcode_errormessage.getText());

    }

    public void validate_autopolulate_recipient_firstname_lastname() {
        waitFor(5);

        String Expected_Thirdpartycontact_FirstName = page.sendto_firstname.getAttribute("value");
        String Expected_Thirdpartycontact_LastName = page.sendto_lastname.getAttribute("value");

        Assert.assertEquals(Expected_Thirdpartycontact_FirstName.toLowerCase(), Thirdpartycontact_FirstName.get().toLowerCase(), "First name unequal");
        Assert.assertEquals(Expected_Thirdpartycontact_LastName.toLowerCase(), Thirdpartycontact_LastName.get().toLowerCase(), "Last name unequal");
    }


    public void validate_regarding_section() {
        waitFor(3);
        try {
            page.regardingLabel.isDisplayed();
            Assert.assertTrue(false, "Regarding section displayed");
        } catch (Exception e) {
            Assert.assertTrue(true, "Regarding section not present");
        }
    }

    public void validate_type_dropdown_values() {
        page.type_textbox.click();
        waitFor(10);
        ArrayList<String> expected_nonregarding_type = new ArrayList<String>();

        List<WebElement> type_dropdownvalues = Driver.getDriver().findElements(By.xpath("//*[contains(@id, '_TYPE-option-')]"));
        for (int j = 0; j < type_dropdownvalues.size(); j++) {

            expected_nonregarding_type.add(type_dropdownvalues.get(j).getText());
        }
        Collections.sort(expected_nonregarding_type);
        Collections.sort(nonregarding_type.get());
        System.out.println(expected_nonregarding_type);
        System.out.println(nonregarding_type.get());
        boolean isEqual = expected_nonregarding_type.equals(nonregarding_type);
        Assert.assertTrue(isEqual, "type dropdown values are missing");


    }


    public void capture_non_regarding_type_values(JsonPath activecorrespondances) {
        String arraylength, usermayreq, correspondenceName, mmsid;
        //Retrieve size of array
        int size = activecorrespondances.getInt("listCorrespondence.size()");
        System.out.println("Array size is: " + size);

        for (int i = 0; i < size; i++) {
            arraylength = activecorrespondances.getString("listCorrespondence[" + i + "].correspondenceRequiredKeys.size()");
            usermayreq = activecorrespondances.getString("listCorrespondence[" + i + "].userMayRequest").toString();

            if (Integer.parseInt(arraylength) == 0 && usermayreq == "true") {
                correspondenceName = activecorrespondances.getString("listCorrespondence[" + i + "].correspondenceName");
                mmsid = activecorrespondances.getString("listCorrespondence[" + i + "].mmsId");
                nonregarding_type.get().add(correspondenceName + " - " + mmsid);
            }
        }

    }

    public void enterdatainSendToSection(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "AddressLine1":
                    waitFor(1);
                    String addressline1 = RandomStringUtils.random(9, false, true) + " " + RandomStringUtils.random(39, true, false);
                    page.sendto_addressline1.sendKeys(addressline1);
                    break;
                case "AddressLine2":
                    waitFor(1);
                    String addressline2 = RandomStringUtils.random(9, false, true) + " " + RandomStringUtils.random(39, true, false);
                    page.sendto_addressline2.sendKeys(addressline2);
                    break;
                case "City":
                    waitFor(1);
                    String City = RandomStringUtils.random(45, true, true) ;
                    page.sendto_city_dropdown.sendKeys(City);
                    break;
                case "State":
                    waitFor(1);
                    page.sendto_state_dropdown.click();
                    waitFor(1);
                    if ("Random".equalsIgnoreCase(dataTable.get("State")))
                        Driver.getDriver().findElement(By.xpath("//li[text()='Georgia']")).click();
                    else
                        Driver.getDriver().findElement(By.xpath("//li[text()='" + dataTable.get("State") + "']")).click();
                    break;
                case "Zipcode":
                    waitFor(1);
                    String zipcode = RandomStringUtils.random(5, false, true) ;
                    page.sendto_zipcode_dropdown.sendKeys(zipcode);
                    break;
                default:
                    throw new NotImplementedException("DataTable did not match an existing key, value must match keys in table | " + "dataTable value = " + data);
            }

        }
    }

    public void selectOutboundCorrespondencetype(String type) {
        waitFor(2);
        new Actions(Driver.getDriver()).click(page.type_textbox).sendKeys(type + Keys.ENTER).build().perform();
        //selectDropDownSlider(page.type_textbox,type);
        //Adding this to avoid stucking on choosing Correspondence Type step
        try {
            if(Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")).isDisplayed()){
                Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")).click();
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        waitFor(2);
    }

    public void enterOutboundCorrespondenceType(String type) {
        waitFor(3);
        new Actions(Driver.getDriver()).click(page.type_textbox).sendKeys(type + Keys.ENTER).build().perform();
        //Adding this to avoid stucking on choosing Correspondence Type step
        try {
            if(Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")).isDisplayed()){
                Driver.getDriver().findElement(By.xpath("//li[text()='" + type + "']")).click();
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void verifyRegardingIsNotDisplayed() {
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.regardingLabel), "Regarding is displayed, verification failed");
    }

    public void verifyBodyDataIsNotDisplayed() {
        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.bodydataelemntsheading), "Body Data is displayed, verification failed");
    }

    public void VerifyBodyDataElementHeading() {
        waitFor(2);
        String expectedheading = "BODY DATA ELEMENTS";
        String actualheading = page.bodydataelemntsheading.getText();
        Assert.assertEquals(actualheading, expectedheading, "Expected heading : " + expectedheading + " but found : " + actualheading);
    }

    public void VerifyBodyDataElementLabels(List<String> expectedlabelnames) {
        waitFor(5);
        String s = page.bodydatatable.getText();
        s = s.replace(" *", "");
        String[] labelnames = s.split("\n");
        List<String> actuallabelnames = Arrays.asList(labelnames);

        for (String label : expectedlabelnames) {
            Assert.assertTrue(actuallabelnames.contains(label), label + " - Label didn't find");

        }
    }

    public void VerifyBodyDataElementDatatypes() {

        waitFor(5);
        Assert.assertEquals("text", page.reqshorttextfield.getAttribute("type"));
        Assert.assertEquals("149", page.reqshorttextfield.getAttribute("maxlength"));

        Assert.assertEquals("number", page.reqnumfield.getAttribute("type"));

        Assert.assertEquals("text", page.reqdatefield.getAttribute("type"));

        Assert.assertEquals("checkbox", page.reqcbfield.getAttribute("type"));

        Assert.assertEquals("text", page.reqlongtextfield.getAttribute("type"));
        Assert.assertEquals("Infinity", page.reqlongtextfield.getAttribute("maxlength"));

    }

    public void VerifyBodyDataElementerrormessage(List<String> expectederrormessages) {

        page.saveButton.click();
        waitFor(1);
        for (String msg : expectederrormessages) {
            Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//p[contains(text(),'" + msg + "')]")).isDisplayed());

        }
    }


    public void VerifymultipleinstancesofBodyDataElement(int count) {
        List<WebElement> mi = Driver.getDriver().findElements(By.xpath("//button/i[@class='material-icons']"));

        for (int i = 1; i <= count * 2; i++) {
            Driver.getDriver().findElement(By.xpath("(//button/i[@class='material-icons'])[" + i + "]")).click();
            ++i;
            waitFor(1);
        }
        Assert.assertEquals(count, mi.size());
    }

    public void VerifydeletionofmultipleinstancesofBodyDataElement(int count) {
        List<WebElement> mi = Driver.getDriver().findElements(By.xpath("//button/i[@class='material-icons']"));
        for (int i = 1; i <= mi.size(); i++) {
            Driver.getDriver().findElement(By.xpath("(//button/i[@class='material-icons'])[" + i + "]")).click();
            waitFor(1);
            Driver.getDriver().findElement(By.xpath("(//button/i[@class='material-icons'])[" + (i + 1) + "]")).click();
            waitFor(1);
        }
        Assert.assertEquals(count, mi.size());
    }

    public void VerifyBodyDataElementcancelfunctionality() {

        page.CancelButton.click();
        waitFor(2);
        page.warningMessage_Continue.click();

    }

    public void enterBodyDataElementvalues() {

        for (int i = 1; i <= 10; i++) {
            Driver.getDriver().findElement(By.xpath("(//button/i[@class='material-icons'])[" + i + "]")).click();
            ++i;
            waitFor(1);
        }

        ArrayList<String> al = new ArrayList<>();
        al.add(RandomStringUtils.random(149, true, true));
        al.add("shorttext_1");
        al.add("-0.123456899");
        al.add("253698");
        al.add("08/26/2020");
        al.add("08/26/2025");
        al.add("true");
        al.add("true");
        al.add(RandomStringUtils.random(200, true, true));
        al.add("Longtext_1");
        al.add("ghgdahsd734638");

        for (int i = 1; i <= al.size(); i++) {

            if (i == 7 || i == 8)
                Driver.getDriver().findElement(By.xpath("//div[@class='row mb-4']/div[" + i + "]//input")).click();
            else
                Driver.getDriver().findElement(By.xpath("//div[@class='row mb-4']/div[" + i + "]//input")).sendKeys(al.get(i - 1));

        }

    }

    public void VerifyBodyDataElementSavefunctionality() {
        String actual_successmessage = "The Correspondence Record is saved successfully.";
        page.saveButton.click();
        waitForVisibility(page.successMessage, 15);
        String expected_successmessage = page.successMessage.getText();
        Assert.assertEquals(expected_successmessage, actual_successmessage);

    }


    public void consumerprofile_RegardingSection(String actual_consumer_ID) {
        waitFor(3);
        String expected_consumer_ID = page.consumer_reg.getText();
        Assert.assertEquals(expected_consumer_ID, actual_consumer_ID);

    }

    public void consumerprofile_LinkSection(String actual_consumer_ID) {
        waitFor(3);
        WebElement e = Driver.getDriver().findElement(By.xpath("//tbody//td[text()='" + actual_consumer_ID + "']"));
        Assert.assertTrue(e.isDisplayed());
    }

    public void consumerprofile_LinkSection_warningmessage(String actual_consumer_ID) {
        Driver.getDriver().findElement(By.xpath("//tbody//td[text()='" + actual_consumer_ID + "']")).click();
        waitFor(2);
        Assert.assertTrue(page.warning_msg.isDisplayed());

    }

    public void navigation_ConsumerProfile_Demographicpage() {
        page.continue_button.click();
        waitFor(2);
        Assert.assertTrue(page.demographicinfo_profiledetails.isDisplayed());
    }

    public void consumerprofile_only_recipient(String consumerID, String consumerName) {
        int actual_recipinet_list_count = page.recipient_list.size();
        Assert.assertEquals(actual_recipinet_list_count, 1);


        if (page.firstRecipient.isSelected()) {
            page.firstRecipient.click();
            String actual_recipient_checkbox_status = page.firstRecipient.getAttribute("value");
            Assert.assertEquals(actual_recipient_checkbox_status, "true");
        } else {
            Assert.assertTrue(false, "Recipient checkbox should be checked by default");
        }


        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + consumerID + "')]")).isDisplayed());
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + consumerName + "')]")).isDisplayed());

    }

    public void consumerprofile_channel_destination(String consumerAddress) {

        try {
            Assert.assertEquals(page.sendToLabel.isDisplayed(), false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }

        int actual_destination_list_count = page.destinationtype_list.size();
        Assert.assertEquals(actual_destination_list_count, 1);

        Assert.assertTrue(page.firstmailChannel.isDisplayed());


        if (page.firstChannel.isSelected()) {
            page.firstChannel.click();
            String actual_recipient_checkbox_status = page.firstChannel.getAttribute("value");
            Assert.assertEquals(actual_recipient_checkbox_status, "true");
        } else {
            Assert.assertTrue(false, "Mail checkbox should be checked by default");
        }

        String actual_mailingaddress = page.prepopulatemailingaddress.getText();
        Assert.assertEquals(actual_mailingaddress, "Consumer Mailing - " + consumerAddress);

        page.prepopulatemailingaddress.click();
        waitFor(2);
        int actual_maildestination_list_count = page.maildestinationvalue_list.size();
        Assert.assertEquals(actual_maildestination_list_count, 1);
    }

    public void empty_recipient(String consumerID) {
        String actual_header = page.notificationheader.getText();
        String expected_header = "NOTIFICATIONS";
        Assert.assertEquals(actual_header, expected_header);
        try {
            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//li[contains(text(),'" + consumerID + "')]")).isDisplayed(), false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    public void verifyTypeFormatAndBehaviors() {
        //verify type label
        String actualType = page.typeLabel.getText();
        Assert.assertTrue(actualType.startsWith("TYPE"), "Type label verification failed");
        //verify format
        page.typeDropdown.click();
        List<String> listOfTypes = getElementsText(page.typeDropDownValues);
        for (String type : listOfTypes) {
            Assert.assertTrue(type.contains(" - "), "Type format verification failed");
            String[] arr = type.split(" - ");
            String typeName = arr[0];
            Assert.assertTrue(uiAutoUitilities.get().isAlphanumericWithSpace(typeName));
            String typeID = arr[1];
            Assert.assertTrue(uiAutoUitilities.get().isAlphanumericWithSpace(typeID));
        }
        //verify autocomplete
        String type = listOfTypes.get(random.get().nextInt(listOfTypes.size()));
        String partOfStringForAutocomplete = type.substring(0, random.get().nextInt(type.length()) + 1).toLowerCase();
        page.typeDropdown.sendKeys(partOfStringForAutocomplete);
        List<String> listOfAutocompleteValues = getElementsText(page.typeDropDownValues);
        for (String value : listOfAutocompleteValues) {
            Assert.assertTrue(value.toLowerCase().startsWith(partOfStringForAutocomplete), "Autocomplete verification failed for type");
        }
        BrowserUtils.clearText(page.typeDropdown);
        page.saveButton.click();
        Assert.assertTrue(page.typeWarningMessage.isDisplayed(), "Type warning message verification failed");

        selectDropDown(page.typeDropdown, save.get().get("idAndNameOfOCD"));

    }

    public void verifyLanguageFormatAndBehaviors() {
        // verify label
        String actualLanguageLabel = page.languageLabel.getText();
        Assert.assertEquals(actualLanguageLabel, "LANGUAGE", "Language label verification failed");
        //verify default value
        waitFor(4);
        String actualLanguage = page.languageDropdown.getAttribute("value");
        Assert.assertEquals(actualLanguage, "English", "Language default value verification failed");
        //verify format
        page.languageDropdown.click();
        List<String> listOfLanguages = getElementsText(page.languageDropdownValues);
        for (String language : listOfLanguages) {
            Assert.assertTrue(uiAutoUitilities.get().isAlphanumericWithSpace(language));
            Assert.assertTrue(uiAutoUitilities.get().verifyTextLength(language, 30));
        }
        //verify autocomplete
        String language = listOfLanguages.get(random.get().nextInt(listOfLanguages.size()));
        String partOfStringForAutocomplete = language.substring(0, random.get().nextInt(language.length()) + 1);

        uiAutoUitilities.get().clearWithActions(page.languageDropdown);
        page.languageDropdown.sendKeys(partOfStringForAutocomplete);
        List<String> listOfAutocompleteValues = getElementsText(page.languageDropdownValues);
        for (String value : listOfAutocompleteValues) {
            Assert.assertTrue(value.contains(partOfStringForAutocomplete), "Autocomplete verification failed for language");
        }

        BrowserUtils.clearText(page.languageDropdown);
        page.languageDropdown.sendKeys("English" + Keys.ENTER);
    }

    public void verifyRegardingLabel() {
        Assert.assertTrue(page.regardingLabel.isDisplayed(), "Regarding label verification failed");
    }

    public void verifyCaseIdFormatAndBehaviors() {
        caseId.set(page.caseID.getText());
        Assert.assertTrue(BrowserUtils.hasOnlyDigits(page.caseID.getText()));
        Assert.assertTrue(UIAutoUitilities.verifyTextLength(caseId.get(), 10));
    }

    public void verifyConsumerIdFormatAndBehaviors() {
        waitForVisibility(page.dropdownArrowDown, 3);
        page.dropdownArrowDown.click();
        waitFor(1);
        List<String> actualListOfFullNames = getElementsText(page.dropdownListOfConsumers);
        page.dropdownArrowDown.click();
        synchronized (recipientsJson.get()){
            recipientsJson.set(API_THREAD_LOCAL_FACTORY.getAPIAutoUtilities().getRecipientsInfoByCaseNumber(caseId.get()));
        }
        List<String> expectedListOfFullNames = new ArrayList<>();
        int size = recipientsJson.get().getInt("consumers.size()");
        for (int i = 0; i < size; i++) {
            expectedListOfFullNames.add(recipientsJson.get().getString("consumers[" + i + "].consumerId") + "-" + recipientsJson.get().getString("consumers[" + i + "].firstName").substring(0, 1).toUpperCase() + recipientsJson.get().getString("consumers[" + i + "].firstName").substring(1) + " " + recipientsJson.get().getString("consumers[" + i + "].lastName").substring(0, 1).toUpperCase() + recipientsJson.get().getString("consumers[" + i + "].lastName").substring(1));
        }

        Assert.assertEquals(expectedListOfFullNames, actualListOfFullNames, "Consumer(s) dropdown verification failed");
        uiAutoUitilities.get().clickWithActions(page.saveButton);
        Assert.assertTrue(page.consumerIsRequiredWarningMessage.isDisplayed(), "Consumer is required warning message verification failed");
        //selecting random consumer from dropdown
        page.dropdownArrowDown.click();
        page.dropdownListOfConsumers.get(random.get().nextInt(page.dropdownListOfConsumers.size())).click();

    }

    public void verifySendToLabel() {
        Assert.assertTrue(page.sendtolabel.isDisplayed(), "Send To label verification failed");
    }

    public void verifyRecipientsInfo() {
        int recipientSize = recipientsJson.get().getInt("consumers.size()");
        waitFor(3);
        //verify list of checkbox
        for (int i = 0; i < recipientSize; i++) {

            Assert.assertTrue(page.listOfRecipientsCheckboxes.get(i).isDisplayed(), "Checkbox not displayed, verification failed");

            // verify list of consumer ID
            Assert.assertEquals(page.listOfRecipientsIDs.get(i).getText(), recipientsJson.get().getString("consumers[" + i + "].consumerId"), "Recipients ID verification failed");

            //verify list of names
            Assert.assertTrue(page.listOfRecipientsNames.get(i).getText().equalsIgnoreCase(recipientsJson.get().getString("consumers[" + i + "].firstName") + " " + recipientsJson.get().getString("consumers[" + i + "].lastName")), "Recipients full name verification failed");

            //verify list of roles
            Assert.assertEquals(page.listOfRecipientsRoles.get(i).getText(), recipientsJson.get().getString("consumers[" + i + "].consumerRole"), "Recipients role verification failed");
        }

        //verify warning message "At least one Recipient is required"

        uiAutoUitilities.get().clickWithActions(page.saveButton);
        waitFor(2);
        Assert.assertTrue(page.atLeastOneRecipientWarningMessage.isDisplayed(), "At least one Recipient is required warning message verification failed");


        //verify channels
        //verify when user checks a Recipient's checkbox, then a section expands to show channels in a grid
        waitForClickablility(page.listOfRecipientsCheckboxes.get(0), 3);
        uiAutoUitilities.get().clickWithActions(page.listOfRecipientsCheckboxes.get(0));


        //verify channel include : checkbox,
        int channelSize = page.listOfChannelsCheckBoxes.size();
        for (int i = 0; i < channelSize; i++) {
            Assert.assertTrue(page.listOfChannelsCheckBoxes.get(i).isDisplayed(), "Checkbox verification failed for channel " + page.listOfChannels.get(i));
            Assert.assertTrue(uiAutoUitilities.get().verifyTextLength(page.listOfChannels.get(i).getText(), 10));
            Assert.assertTrue(BrowserUtils.isAlphanumeric(page.listOfChannels.get(i).getText()), "Channel verification failed for channel " + page.listOfChannels.get(i));

        }

        //verify warning message - Error message when a Recipient is checked and no Channel is checked: "At least one Channel is required for each selected Consumer"
        waitFor(2);
        uiAutoUitilities.get().clickWithActions(page.saveButton);
        Assert.assertTrue(page.atLeastOneChannelWarningMessage.isDisplayed(), "At least one Channel is required for each selected consumer warning message verification failed");

        waitFor(2);
        page.listOfChannelsCheckBoxes.get(random.get().nextInt(page.listOfChannelsCheckBoxes.size())).click();
        uiAutoUitilities.get().clickWithActions(page.saveButton);
        //            Error message if the Channel is checked but the Destination is left blank: "Destination is required and cannot be left blank"
        Assert.assertTrue(page.destinationIsRequiredWarningMessage.isDisplayed(), "DESTINATION is required and cannot be left blank warning message verification failed");


        int indexOfMail = 0;
        int indexOfText = 0;
        int indexOfEmail = 0;
        int indexOfFax = 0;

        for (int i = 0; i < page.listOfChannels.size(); i++) {
            switch (page.listOfChannels.get(i).getText()) {
                case "MAIL":
                    indexOfMail = i;
                    break;
                case "TEXT":
                    indexOfText = i;
                    break;
                case "EMAIL":
                    indexOfEmail = i;
                    break;
                case "FAX":
                    indexOfFax = i;
                    break;
            }
        }
        waitFor(2);
        boolean usableFlagMail = recipientsJson.get().getBoolean("consumers[0].channels.mail.usability.usableFlag");
        List<String> expectedListOfDestinationForMail = new ArrayList<>();
        if (usableFlagMail) {
            uiAutoUitilities.get().clickWithActions(page.listOfChannelDestinations.get(indexOfMail));
            for (int i = 0; i < recipientsJson.get().getInt("consumers[0].channels.mail.addresses.size()"); i++) {
                boolean isAddressLine2 = false;
                try {
                    isAddressLine2 = !recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].addressLine2").isEmpty();
                } catch (Exception ex) {
                    System.out.println("Expected exception, doesn't have address line 2");
                }
                if (isAddressLine2) {
                    expectedListOfDestinationForMail.add("Case Mailing " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].addressLine1")
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].addressLine2")
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].city")
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].state").trim()
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].zip"));
                } else {
                    expectedListOfDestinationForMail.add("Case Mailing " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].addressLine1")
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].city")
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].state".trim())
                            + " " + recipientsJson.get().getString("consumers[0].channels.mail.addresses[" + i + "].zip"));
                }
            }
            expectedListOfDestinationForMail.add("Other");
            //                Mail  shows the active addresses linked to the Consumer, and if the Correspondence is at the Case level, the active addresses linked to the Case
            uiAutoUitilities.get().clickWithActions(page.listOfChannelDestinations.get(indexOfMail));
            List<String> actualListOfDestinationForMail = getElementsText(page.maildestinationvalue_list);
            Assert.assertEquals(actualListOfDestinationForMail, expectedListOfDestinationForMail, "Addresses verification failed for Mail destinations");
        }
        //escaping from drop down
        uiAutoUitilities.get().sendKeyWithActions(page.listOfChannelDestinations.get(indexOfMail), "" + Keys.ESCAPE);


        //                Email shows the active email addresses linked to the Consumer
        waitFor(2);
        boolean usableFlagEmail = recipientsJson.get().getBoolean("consumers[0].channels.email.usability.usableFlag");
        List<String> expectedListOfDestinationForEmail = new ArrayList<>();
        if (usableFlagEmail) {
            for (int i = 0; i < recipientsJson.get().getInt("consumers[0].channels.email.emailAddresses.size()"); i++) {
                boolean isPrimaryEmail = recipientsJson.get().getBoolean("consumers[0].channels.email.emailAddresses[" + i + "].attributes.primaryFlag");
                if (isPrimaryEmail) {
                    expectedListOfDestinationForEmail.add("Primary-" + recipientsJson.get().getString("consumers[0].channels.email.emailAddresses[" + i + "].emailAddress"));
                } else {
                    expectedListOfDestinationForEmail.add("Other-" + recipientsJson.get().getString("consumers[0].channels.email.emailAddresses[" + i + "].emailAddress"));
                }
            }
            expectedListOfDestinationForEmail.add("Other");
            waitFor(1);
            uiAutoUitilities.get().clickWithActions(page.listOfChannelDestinations.get(indexOfEmail));
            List<String> actualListOfDestinationForEmail = getElementsText(page.maildestinationvalue_list);
            Assert.assertEquals(actualListOfDestinationForEmail, expectedListOfDestinationForEmail, "Addresses verification failed for Email destinations");

            //escaping from drop down
            uiAutoUitilities.get().sendKeyWithActions(page.listOfChannelDestinations.get(indexOfEmail), "" + Keys.ESCAPE);
        }


//                Text shows the active cell numbers linked to the Consumer
        waitFor(2);
        boolean usableFlagText = recipientsJson.get().getBoolean("consumers[0].channels.text.usability.usableFlag");
        List<String> expectedListOfDestinationForText = new ArrayList<>();
        if (usableFlagText) {
            for (int i = 0; i < recipientsJson.get().getInt("consumers[0].channels.text.phoneNumbers.size()"); i++) {
                expectedListOfDestinationForText.add(recipientsJson.get().getString("consumers[0].channels.text.phoneNumbers[" + i + "].phoneNumber").substring(0, 3) +
                        "-" + recipientsJson.get().getString("consumers[0].channels.text.phoneNumbers[" + i + "].phoneNumber").substring(3, 6) +
                        "-" + recipientsJson.get().getString("consumers[0].channels.text.phoneNumbers[" + i + "].phoneNumber").substring(6));
            }
            expectedListOfDestinationForText.add("Other");
            uiAutoUitilities.get().clickWithActions(page.listOfChannelDestinations.get(indexOfText));
            List<String> actualListOfDestinationForText = getElementsText(page.maildestinationvalue_list);
            Assert.assertEquals(actualListOfDestinationForText, expectedListOfDestinationForText, "Addresses verification failed for Text destinations");

            //escaping from drop down
            uiAutoUitilities.get().sendKeyWithActions(page.listOfChannelDestinations.get(indexOfText), "" + Keys.ESCAPE);
        }


//                Fax shows the active fax numbers linked to the Consumer
        waitFor(2);
        boolean usableFlagFax = recipientsJson.get().getBoolean("consumers[0].channels.fax.usability.usableFlag");
        List<String> expectedListOfDestinationForFax = new ArrayList<>();
        if (usableFlagFax) {
            for (int i = 0; i < recipientsJson.get().getInt("consumers[0].channels.fax.size()"); i++) {
                expectedListOfDestinationForFax.add(recipientsJson.get().getString("consumers[0].channels.fax.phoneNumbers[" + i + "].phoneNumber").substring(0, 3) +
                        "-" + recipientsJson.get().getString("consumers[0].channels.fax.phoneNumbers[" + i + "].phoneNumber").substring(3, 6) +
                        "-" + recipientsJson.get().getString("consumers[0].channels.fax.phoneNumbers[" + i + "].phoneNumber").substring(6));
            }
            expectedListOfDestinationForFax.add("Other");
            uiAutoUitilities.get().clickWithActions(page.listOfChannelDestinations.get(indexOfFax));

            List<String> actualListOfDestinationForFax = getElementsText(page.maildestinationvalue_list);
            Assert.assertEquals(actualListOfDestinationForFax, expectedListOfDestinationForFax, "Addresses verification failed for Fax destinations");

            //escaping from drop down
            uiAutoUitilities.get().sendKeyWithActions(page.listOfChannelDestinations.get(indexOfFax), "" + Keys.ESCAPE);
        }
    }

    public void selectAnyValueFromConsumerDropdown() {
        waitForClickablility(page.consumerDropdown, 3);
        uiAutoUitilities.get().clickWithActions(page.consumerDropdown);
        uiAutoUitilities.get().clickWithActions(page.dropdownListOfConsumers.get(random.get().nextInt(page.dropdownListOfConsumers.size())));
    }

    public String getUserId() {
        return page.headerUserID.getText().split(" ")[1];
    }

    public void expandFirstOutCor() {
        waitForClickablility(caseAndContactDetailsPage.correspondenceDetailsBTNs.get(0), 3);
        caseAndContactDetailsPage.correspondenceDetailsBTNs.get(0).click();
    }

    public void verifyCreatedOnOutCor() {
        waitForVisibility(caseAndContactDetailsPage.outboundCorresDetailsCreatedOnInfo, 2);
        Assert.assertTrue(caseAndContactDetailsPage.outboundCorresDetailsCreatedOnInfo.getText().startsWith(BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().toString())), "Created on verification failed");
    }

    public void verifyCreatedByOutCor() {
        Assert.assertEquals(caseAndContactDetailsPage.outboundCorresCreatedByInfo.getText(), getUserId(), "Created by verification failed");
    }

    public void clickBackArrow() {
        page.backArrow.click();
    }

    public void verifyWarningMessage() {
        waitForVisibility(page.warning_msg, 8);
        String message = page.warning_msg.getText().trim();
        System.out.println("Message : " + message);
        Assert.assertEquals(message, "WARNING MESSAGE");
    }

    public void verifyOCRequestTitle() {
        String expectedTitle = "Create Outbound Correspondence Request";
        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle, "Title verification failed for OC request page");
    }

    public void clickCancelButton() {
        page.CancelButton.click();
    }

    public void clickOnWarningMessageButton(String value) {
        switch (value) {
            case "Cancel":
                page.warningMessageCancel.click();
                break;
            case "Continue":
                page.continue_button.click();
                break;
        }
    }

    public boolean isLoadingCircleDisplayed() {
        return uiAutoUitilities.get().quickIsDisplayed(page.loadingIndicator);
    }

    public boolean verifyActiveContactScreenDisplayed() {
        return uiAutoUitilities.get().quickIsDisplayed(page.activeContactScreen);
    }

    public boolean verifyActiveTaskScreenDisplayed() {
        return uiAutoUitilities.get().quickIsDisplayed(page.priorityDashboard);
    }

    public void selectOutboundCorrespondencelanguage(String lang) {
        new Actions(Driver.getDriver()).click(page.languageDropdown).sendKeys(lang + Keys.ENTER).build().perform();
        waitFor(1);
    }


    public void verifyGivenValuesDisplayed(Map<String, String> dataTable) {
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "caseID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.caseID.isDisplayed(), "Case ID not displayed, verification failed");
                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.caseID), "Case ID is displayed, verification failed");
                    }
                    break;
                case "consumerID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.consumerDropdown.isDisplayed(), "Consumer ID not displayed, verification failed");
                        selectAnyValueFromConsumerDropdown();
                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.consumerDropdown), "Consumer ID is displayed, verification failed");
                    }
                    break;
                case "consumerIDs":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.consumerDropdown.isDisplayed(), "Consumer ID not displayed, verification failed");
                        verifyOnlyOneConsumerCanBeSelected();
                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.consumerDropdown), "Consumer ID is displayed, verification failed");
                    }
                        break;
            }
        }
    }

    private void verifyOnlyOneConsumerCanBeSelected(){
        uiAutoUitilities.get().clickWithActions(page.consumerDropdown);
        if (page.dropdownListOfConsumers.size()>1){
            for (int i = 0; i < page.dropdownListOfConsumers.size(); i++) {
                waitFor(1);
                uiAutoUitilities.get().clickWithActions(page.dropdownListOfConsumers.get(random.get().nextInt(page.dropdownListOfConsumers.size())));
                Assert.assertEquals(page.listOfSelectedConsumers.size(), 1, "Verification failed, more than one consumer selected");
                waitFor(1);
                uiAutoUitilities.get().clickWithActions(page.consumerDropdownArrowDown);
            }
        }else{
            uiAutoUitilities.get().clickWithActions(page.dropdownListOfConsumers.get(random.get().nextInt(page.dropdownListOfConsumers.size())));
        }
    }

    public void validateUIRequestContainsValues(Map<String, String> dataTable) {
        BrowserUtils.waitFor(3);
        String URL = ConfigurationReader.getProperty("apiProxyDMSCorrespondence") + "/correspondences";
        List<String> events = EventsUtilities.getRawLogs(URL);
        String rawRequest = "";
        Map<String, String> caseConsumerIds = (Map<String, String>) generalSave.get().get("caseConsumerId");
        for (String event : events) {
            if (event.contains("\"method\":\"POST\"")) {
                rawRequest = event;
            }
        }
        int indexOfPostData = rawRequest.indexOf("{\\\"anchor\\\":{");
        int indexOfPostDataEntry = rawRequest.indexOf(",\"postDataEntries\"") - 1;
        String request = rawRequest.substring(indexOfPostData, indexOfPostDataEntry).replace("\\", "");
        String anchor = new JsonPath(request).getString("anchor");
        System.out.println("Printing anchor from UI request ");
        System.out.println(anchor);
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "caseID":
                    generalSave.get().put("caseId", caseConsumerIds.get("caseId"));
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(anchor.contains(caseConsumerIds.get("caseId")), "Case Id missed in anchor, verification failed");
                    } else {
                        Assert.assertFalse(anchor.contains(caseConsumerIds.get("caseId")), "Case Id is in anchor, verification failed");
                    }
                    break;
                case "consumerID":
                    generalSave.get().put("regardingConsumerId", caseConsumerIds.get("consumerId"));
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(anchor.contains(caseConsumerIds.get("consumerId")), "Consumer Id missed in anchor, verification failed");
                    } else {
                        Assert.assertFalse(anchor.contains(caseConsumerIds.get("consumerId")), "Consumer Id is in anchor, verification failed");
                    }
                    break;
                case "correspondenceType":
                    World.generalSave.get().put("OUTBOUNDCORRESPONDENCEID", getLastRequestedCorrespondenceIDByType(dataTable.get(data).split(" - ")[1]));
                    break;
            }
        }
    }

    public String getLastRequestedCorrespondenceIDByType(String type){
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setbaseUri(ConfigurationReader.getProperty("apiDMSCorrespondence"));
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().setEndPoint("correspondences/ouboundcorrespondence?page=0&size=2000");
        JsonObject request = new JsonObject();
        JsonArray searchArray = new JsonArray();
        searchArray.add(type);
        request.add("correspondenceType", searchArray);
        API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().PostAPIWithParameter(request);
        int lastRecord = API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getInt("data.numberOfElements")-1;
        return API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("data.content["+lastRecord+"].id");
    }

    public void verifyOutboundCorrespondenceResponseValues(Map<String, String> dataTable, JsonPath response) {
        String anchor = response.getString("anchor");
        System.out.println("Printing anchor response");
        System.out.println(anchor);
        for (String data : dataTable.keySet()) {
            switch (data) {
                case "caseID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(anchor.contains(String.valueOf(generalSave.get().get("caseId"))), "Case ID missed in response");
                    } else {
                        Assert.assertFalse(anchor.contains(String.valueOf(generalSave.get().get("caseId"))), "Response contains Case ID");
                    }
                    break;
                case "consumerID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(anchor.contains(String.valueOf(generalSave.get().get("regardingConsumerId"))), "Consumer ID missed in response");
                    } else {
                        Assert.assertFalse(anchor.contains(String.valueOf(generalSave.get().get("regardingConsumerId"))), "Response contains Consumer ID");
                    }
                    break;
            }
        }
    }

    public void verifyRequiredValuesDisplayed(Map<String, String> dataTable) {

        Map<String, String> caseConsumerId = (Map<String, String>) World.generalSave.get().get("caseConsumerId");

        for (String data : dataTable.keySet()) {
            switch (data) {
                case "caseID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.caseID.isDisplayed(), "Case ID not displayed, verification failed");
                        Assert.assertEquals(page.caseID.getText(), caseConsumerId.get("caseId"));

                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.caseID), "Case ID is displayed, verification failed");
                    }
                    break;
                case "consumerID":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.consumerDropdown.isDisplayed(), "Consumer ID not displayed, verification failed");
                        selectAnyValueFromConsumerDropdown();

                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.consumerDropdown), "Consumer ID is displayed, verification failed");
                    }
                    break;
                case "consumerIDs":
                    if (dataTable.get(data).equals("true")) {
                        Assert.assertTrue(page.consumerDropdown.isDisplayed(), "Consumer ID not displayed, verification failed");
                        verifyOnlyOneConsumerCanBeSelected();
                        for(WebElement e: page.listOfSelectedConsumers){
                            Assert.assertTrue(e.getText().contains(caseConsumerId.get("consumerId")));
                        }
                    } else {
                        Assert.assertFalse(uiAutoUitilities.get().quickIsDisplayed(page.consumerDropdown), "Consumer ID is displayed, verification failed");
                    }
                    break;
            }
        }
    }


    public void selectDefaultSendToCheckbox() {
        page.sendToCheckBox.click();
    }

    public void verifyUsableChannelsAreSelectedForDefaultConsumers() {
        for (int i = 0; i < generalList.get().size(); i++) {
            WebElement consumerCheckbox = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'"+generalList.get().get(i)+"')]"));
            WebElement mailCheckbox = page.findCheckboxElementWithConsumerIdChannelType(generalList.get().get(i),"Mail");
            WebElement emailCheckBox = page.findCheckboxElementWithConsumerIdChannelType(generalList.get().get(i),"Email");
            WebElement faxCheckBox = page.findCheckboxElementWithConsumerIdChannelType(generalList.get().get(i),"Fax");
            WebElement textCheckBox = page.findCheckboxElementWithConsumerIdChannelType(generalList.get().get(i),"Text");

            if(save.get().get("defaultFlag" + i).equalsIgnoreCase("true")){
                Assert.assertEquals(consumerCheckbox.getAttribute("value"),"true");

                Assert.assertEquals(mailCheckbox.getAttribute("value"),save.get().get("mailUsability"+i),"Mail Checkbox is mismatched");
                if(mailCheckbox.getAttribute("value").equalsIgnoreCase("true")){
                    verifyAbletoDeselectCheckboxes(mailCheckbox);
                }

                Assert.assertEquals(emailCheckBox.getAttribute("value"),save.get().get("emailUsability"+i),"Email Checkbox is mismatched");
                if(emailCheckBox.getAttribute("value").equalsIgnoreCase("true")){
                    verifyAbletoDeselectCheckboxes(emailCheckBox);
                }

                Assert.assertEquals(faxCheckBox.getAttribute("value"),save.get().get("faxUsability"+i),"Fax Checkbox is mismatched");
                if(mailCheckbox.getAttribute("value").equalsIgnoreCase("true")){
                    verifyAbletoDeselectCheckboxes(faxCheckBox);
                }

                Assert.assertEquals(textCheckBox.getAttribute("value"),save.get().get("textUsability"+i),"Text Checkbox is mismatched");
                if(mailCheckbox.getAttribute("value").equalsIgnoreCase("true")){
                    verifyAbletoDeselectCheckboxes(textCheckBox);
                }

                verifyAbletoDeselectCheckboxes(consumerCheckbox);
            }
            else{
                Assert.assertEquals(consumerCheckbox.getAttribute("value"),"false");
                Assert.assertEquals(mailCheckbox.getAttribute("value"), "false","Mail Checkbox is mismatched");
                Assert.assertEquals(emailCheckBox.getAttribute("value"),"false","Email Checkbox is mismatched");
                Assert.assertEquals(faxCheckBox.getAttribute("value"),"false","Fax Checkbox is mismatched");
                Assert.assertEquals(textCheckBox.getAttribute("value"),"false","Text Checkbox is mismatched");
            }
        }
    }

    public void verifyAbletoDeselectCheckboxes(WebElement checkBox) {
        checkBox.click();
        Assert.assertEquals(checkBox.getAttribute("value"),"false", "Not able to deselect" + checkBox);
    }

    public void VerifyBodyDataElementStructure() {
        waitFor(2);

        Assert.assertTrue(page.bodydataelementheading("BODY DATA ELEMENTS").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("EFFECTIVEDATE").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("2021-11-01").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("EXPLANATION").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("This is a paragraph blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah ").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("PREMIUM").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("AMOUNTDUE").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("20.56").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("PREMIUMOWED").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("PERSONS").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("FIRSTNAME1").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("JAMES").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("LASTNAME1").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("MADISON").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("FIRSTNAME2").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("RICKY").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("LASTNAME2").isDisplayed());
        Assert.assertTrue(page.bodydataelementheading("POINT").isDisplayed());

    }

    @And("I validate CaseCorrespondence API")
    public void iinitiatecasecorresopondence() {
        System.out.println("response :"+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().responseString);
        System.out.println("consumer list :"+ API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumers"));
        for (int i = 0; i < API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getList("consumers").size(); i++) {
            World.generalList.get().add(API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerId"));
            World.save.get().put("consumerFirstName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].firstName"));
            World.save.get().put("consumerLastName" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].lastName"));
            World.save.get().put("defaultFlag" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].defaultFlag"));
            World.save.get().put("consumerRole" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].consumerRole"));
            World.save.get().put("mailUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.mail.usability.usableFlag"));
            World.save.get().put("emailUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.email.usability.usableFlag"));
            World.save.get().put("faxUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.fax.usability.usableFlag"));
            World.save.get().put("textUsability" + i, API_THREAD_LOCAL_FACTORY.getAPIClassUtilThreadLocal().jsonPathEvaluator.getString("consumers[" + i + "].channels.text.usability.usableFlag"));
        }
    }
}
