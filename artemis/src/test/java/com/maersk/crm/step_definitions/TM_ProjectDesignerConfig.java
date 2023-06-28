package com.maersk.crm.step_definitions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.maersk.crm.pages.tm.TMDesignerPage;
import com.maersk.crm.pages.tm.TMProjectDetailsPage;
import com.maersk.crm.utilities.BrowserUtils;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TM_ProjectDesignerConfig extends BrowserUtils {
    TMProjectDetailsPage tmProjectDetailsPage = new TMProjectDetailsPage();
    TMDesignerPage tmDesignerPage = new TMDesignerPage();

    public final ThreadLocal<String> pageKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> pageName = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> templateKey = ThreadLocal.withInitial(String::new);
    public final ThreadLocal<String> templateName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> fieldKey = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> fieldName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> gridKey = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> gridName = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> gridLabel = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> columnKey = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> columnLabel = ThreadLocal.withInitial(String::new);
    final ThreadLocal<String> colPosition = ThreadLocal.withInitial(String::new);
    final ThreadLocal<List<String>> properties = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> properties2 = ThreadLocal.withInitial(ArrayList::new);
    final ThreadLocal<List<String>> expectedFieldDataTypes = ThreadLocal.withInitial(() -> new ArrayList<>(Arrays.asList("label", "numeric", "character", "phone", "email", "ssn",
            "zipCode", "alphaNumeric", "alphaNumericSpecialCharacter", "freeCharacter", "alphaNumericNoSpace", "fullZipcode",
            "fullName", "cityZIP", "address"
    )));

    @Given("I navigate to project designer page")
    public void navigate_to_task_type() {
        Assert.assertTrue(tmProjectDetailsPage.designerConfig.isDisplayed(), "designer configuration icon is  not displayed");
        tmProjectDetailsPage.designerConfig.click();
        Assert.assertTrue(tmDesignerPage.pagesTitle.isDisplayed(), "Title of the page is  not displayed");
    }

    @When("I verify list of pages is displayed")
    public void verifyListOfPages() {
        Assert.assertTrue(tmDesignerPage.pageKeys.get(0).isDisplayed(), "Pages are not displayed");
    }

    @And("I verify list of templates is displayed")
    public void i_verify_list_of_templates_is_displayed() throws Throwable {
        Assert.assertTrue(tmDesignerPage.TemplateKeys.get(0).isDisplayed(), "No templates displayed");
        Assert.assertTrue(tmDesignerPage.templateKeyHeader.isDisplayed(), "Template Key header is not displayed");
        Assert.assertTrue(tmDesignerPage.templateNameHeader.isDisplayed(), "Template Name Header is not displayed");
        Assert.assertTrue(tmDesignerPage.templateTypeHeader.isDisplayed(), "Template type header is not displayed");
        Assert.assertTrue(tmDesignerPage.updatedOnHeader.isDisplayed(), "Template header Updated On is not displayed");
        Assert.assertTrue(tmDesignerPage.updatedByHeader.isDisplayed(), "Template header Updated By is not displayed");

    }

    @Then("I click on page {string} to create or select template in it")
    public void i_click_on_page_something_to_create_template_in_it(String tempPageKey) {
        tmDesignerPage.getPageKeyWithvalue(tempPageKey).click();
        pageKey.set(tempPageKey);
    }


    @When("I click on add template button of type {string} and enter details")
    public void i_click_on_add_template_button_of_type_string_and_enter_details(String type) {
        tmDesignerPage.addTemplateButton.click();
        Assert.assertTrue(tmDesignerPage.pageNameLabelwithValue(pageKey.get()).isDisplayed(), "PageNameis not displayed on Create template screen");
        templateName.set("Temp" + generateRandomNumberChars(3));
        tmDesignerPage.templateNameInputBox.sendKeys(templateName.get());
        templateKey.set("tk" + generateRandomNumberChars(5));
        tmDesignerPage.templateKeyInputBox.sendKeys(templateKey.get());
        selectDropDown(tmDesignerPage.templateTypeDropDown, type);
//        selectRandomDropDownOption(tmDesignerPage.templateTypeDropDown);
        tmDesignerPage.savetemplateBtn.click();
        waitFor(2);
        Assert.assertTrue(tmDesignerPage.templateSuccessMsg.isDisplayed());
        tmDesignerPage.okButton.click();
    }

    @Then("I click on newly created template to edit and save it")
    public void updateTemplate() {
        tmDesignerPage.getTemplateFromList(templateKey.get()).click();
        Assert.assertTrue(tmDesignerPage.pageNameLabelwithValue(pageKey.get()).isDisplayed(), "PageNameis not displayed on Create template screen");
        tmDesignerPage.templateEditButton.click();
        tmDesignerPage.templateNameInputBox.clear();
        templateName.set(generateRandomNumberChars(3) + "update");
        clearAndFillText(tmDesignerPage.templateNameInputBox, templateName.get());
        tmDesignerPage.savetemplateBtn.click();
        waitFor(2);
        Assert.assertTrue(tmDesignerPage.templateSuccessMsg.isDisplayed());
        tmDesignerPage.okButton.click();
    }

    @Then("I select newly created template")
    public void selectTemplate() {
        tmDesignerPage.getTemplateFromList(templateKey.get()).click();
        Assert.assertTrue(tmDesignerPage.pageNameLabelwithValue(pageKey.get()).isDisplayed(), "PageNameis not displayed on Create template screen");
    }

    @Then("I enter grid details to create new grid")
    public void i_enter_grid_details_to_create_new_grid() {
        Assert.assertTrue(tmDesignerPage.gridDetailsHeader.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridKeyLabel.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridNameLabel.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridLabelHeader.isDisplayed());
        Assert.assertTrue(tmDesignerPage.pageSizeHeader.isDisplayed());

        Assert.assertTrue(tmDesignerPage.gridKeyInputBox.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridNameInputBox.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridLabelInputBox.isDisplayed());
        Assert.assertTrue(tmDesignerPage.pageSizeInputBox.isDisplayed());
        Assert.assertTrue(tmDesignerPage.pageSizeDescMsg.isDisplayed());
        Assert.assertTrue(tmDesignerPage.filterInputBox.isDisplayed());
        gridKey.set("gridKey" + getRandomNumber(2));
        tmDesignerPage.gridKeyInputBox.sendKeys(gridKey.get());

        gridName.set("Name" + getRandomString(5));
        tmDesignerPage.gridNameInputBox.sendKeys(gridName.get());

        gridLabel.set("Label" + getRandomString(5));
        tmDesignerPage.gridLabelInputBox.sendKeys(gridLabel.get());
        tmDesignerPage.paginationYESRadioBtn.click();
        tmDesignerPage.pageSizeInputBox.sendKeys("5,10,15");
        tmDesignerPage.sortOrderASCRadioBtn.click();
        tmDesignerPage.filterInputBox.sendKeys(getRandomString(5));
    }

    @And("I click on save button to save grid details")
    public void i_click_on_save_button_to_save_grid_details() {
        tmDesignerPage.saveGridBtn.click();
    }

    @And("I verify new grid cannot be created in template")
    public void i_verify_new_grid_cannot_be_created_in_template() {
        Assert.assertTrue(tmDesignerPage.editGridBtn.isDisplayed());
    }

    @And("I verify Add Grid Column button is displayed")
    public void i_verify_add_grid_column_button_is_displayed() {
        Assert.assertTrue(tmDesignerPage.addColumnBtn.isDisplayed());
    }

    @And("I add columns with following data {string},{string},{string},{string},{string},{string}")
    public void i_I_add_columns_with_following_data(String colKey, String ColLabel, String position, String clickable, String seperator, String pCount) {
        tmDesignerPage.addColumnBtn.click();
        Assert.assertTrue(tmDesignerPage.gridColumnKeyLabel.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridColumnKeyInputBox.isDisplayed());
        if (colKey.equals("Random")) {
            columnKey .set( "key" + generateRandomNumberChars(3));
            clearAndFillText(tmDesignerPage.gridColumnKeyInputBox, columnKey.get());
        }
        Assert.assertTrue(tmDesignerPage.gridColumnLabelInputBox.isDisplayed());
        Assert.assertTrue(tmDesignerPage.gridColumnLabel.isDisplayed());
        if (ColLabel.equals("Random")) {
            columnLabel .set( "Label" + generateRandomNumberChars(5));
            clearAndFillText(tmDesignerPage.gridColumnLabelInputBox, columnLabel.get());
        }

        Assert.assertTrue(tmDesignerPage.positionInputBox.isDisplayed());
        colPosition .set( position);
        clearAndFillText(tmDesignerPage.positionInputBox, colPosition.get());
        if (clickable.equals("YES"))
            tmDesignerPage.clickableYESRadioBtn.click();
        else
            tmDesignerPage.clickableNORadioBtn.click();
        Assert.assertTrue(tmDesignerPage.seperatorLabel.isDisplayed());
        Assert.assertTrue(tmDesignerPage.seperatorInputBox.isDisplayed());
        clearAndFillText(tmDesignerPage.seperatorInputBox, seperator);

        Assert.assertTrue(tmDesignerPage.propertyInputBox.isDisplayed());
        String prop1 = generateRandomNumberChars(4);
        properties.get().add(prop1);
        clearAndFillText(tmDesignerPage.propertyInputBox, prop1);
        tmDesignerPage.addPropertyIcon.click();
        int count = Integer.parseInt(pCount);
        for (int i = 1; i <= count - 1; i++) {
            Assert.assertTrue(tmDesignerPage.propertyInputBox.isDisplayed());
            Assert.assertTrue(tmDesignerPage.propertyInputBoxWithIndex(i).isDisplayed());
            String prop2 = generateRandomNumberChars(4);
            properties.get().add(prop2);
            waitFor(1);
            clearAndFillText(tmDesignerPage.propertyInputBoxWithIndex(1), prop2);
            if (i == count - 1)
                break;
            tmDesignerPage.addPropertyIcon.click();
        }
        tmDesignerPage.saveGridColumn.click();
    }

    @And("I verify the column got saved and displayed")
    public void iVerifyTheColumnGotSavedAndDisplayed() {
        Assert.assertTrue(tmDesignerPage.getGridColumPanelLabel(columnLabel.get()).isDisplayed());
        Assert.assertTrue(tmDesignerPage.getGridColumPanelPosition(columnLabel.get(), colPosition.get()).isDisplayed());
    }

    @And("I verify the column details are displayed with properties")
    public void iverifyColumnDetailsAreDisplayed() {
        Assert.assertEquals(tmDesignerPage.gridColumnKeyInputBox.getAttribute("value"), columnKey.get());
        Assert.assertEquals(tmDesignerPage.gridColumnLabelInputBox.getAttribute("value"), columnLabel.get());
        Assert.assertEquals(tmDesignerPage.positionInputBox.getAttribute("value"), colPosition.get());
        Assert.assertTrue(tmDesignerPage.clickableYESRadioBtn.isSelected());
        Assert.assertTrue(tmDesignerPage.seperatorInputBox.isDisplayed());
        System.out.println("Size:" + tmDesignerPage.columnPropertiesList.size());
        for (int i = 1; i < tmDesignerPage.columnPropertiesList.size(); i++) {
            WebElement e = tmDesignerPage.columnPropertiesList.get(i);
            properties2.get().add(e.getAttribute("value"));
        }
        Assert.assertEquals(properties.get(), properties2.get(), "Column Properties are not correctly saved");
    }

    @And("I click on saved Grid Column")
    public void IClickOnSavedGridColumn() {
        tmDesignerPage.getGridColumPanelLabel(columnLabel.get()).click();
    }

    @And("I verify list of Grid Columns is displayed")
    public void i_verify_list_of_grid_columns_is_displayed() {
        Assert.assertTrue(tmDesignerPage.gridColumnListHeader.isDisplayed());
    }


    @And("I select template {string} to add fields in it")
    public void i_select_template_something_to_add_fields_in_it(String templateKey) {
        tmDesignerPage.templateLabelwithValue(templateKey).click();
    }


    @And("I click on button add field to create new field of type {string}")
    public void i_click_on_button_add_field_to_create_new_fields(String fieldType) {
        tmDesignerPage.addFieldButton.click();
        scrollUpUsingActions(2);
        selectDropDownWithText(tmDesignerPage.fieldTypeDropdown, fieldType);
        Assert.assertTrue(tmDesignerPage.fieldKeyInputBox.isDisplayed(), "Field Key Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldNameInputBox.isDisplayed(), "Field Name Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldOrderInputBox.isDisplayed(), "Field Order Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldDisplayLabelInputBox.isDisplayed(), "Field Display Label Box is not displayed");
        fieldKey.set("fldKey" + generateRandomNumberChars(3));
        fieldName.set("field" + generateRandomNumberChars(5));
        waitFor(2);
        scrollToElement(tmDesignerPage.fieldKeyInputBox);
        tmDesignerPage.fieldKeyInputBox.sendKeys(fieldKey.get());
        tmDesignerPage.fieldNameInputBox.sendKeys(fieldName.get());
        tmDesignerPage.fieldOrderInputBox.sendKeys(generateRandomNumberChars(1));
        tmDesignerPage.fieldDisplayLabelInputBox.sendKeys(generateRandomNumberChars(10));
        switch (fieldType) {
            case "Checkbox":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons not displayed");
                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.colorInputBox.isDisplayed(), "Color Input Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");
                tmDesignerPage.requiredRadioButtonYES.click();
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.colorInputBox.sendKeys("PRIMARY");
                tmDesignerPage.classNamePropsInputBox.sendKeys(generateRandomNumberChars(5));
                break;

            case "Radio Button":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons not displayed");
                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.colorInputBox.isDisplayed(), "Color Input Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");
                tmDesignerPage.requiredRadioButtonYES.click();
                Assert.assertTrue(tmDesignerPage.headerRow.isDisplayed());
                Assert.assertTrue((tmDesignerPage.rowRadioGroup.isDisplayed()));
                Assert.assertTrue(tmDesignerPage.defaultValueInputBox.isDisplayed());
                Assert.assertTrue((tmDesignerPage.optionsInputBox.isDisplayed()));
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.colorInputBox.sendKeys("PRIMARY");
                tmDesignerPage.defaultValueInputBox.sendKeys("random");
                tmDesignerPage.optionsInputBox.sendKeys("random");
                tmDesignerPage.classNamePropsInputBox.sendKeys(generateRandomNumberChars(5));
                break;

            case "Timepicker":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.labelAutoOk.isDisplayed(), "Label Auto Ok is not displayed");
                Assert.assertTrue(tmDesignerPage.autoOkRadioBtnGrp.isDisplayed(), "Auto Ok Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.labelClearable.isDisplayed(), "Label Clearable is not displayed");
                Assert.assertTrue(tmDesignerPage.clearableRadioBtnGrp.isDisplayed(), "Clearable Radio Buttons are not displayed");

                Assert.assertTrue(tmDesignerPage.minDateErrorMsgInputBox.isDisplayed(), "Minimum Date Error Msg Input Box not displated");
                Assert.assertTrue(tmDesignerPage.maxDateErrorMsgInputBox.isDisplayed(), "Maximum Date Error Msg Input Box not displated");
                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.invalidErrorMsgInputBox.isDisplayed(), "Invalid Error Msg Input box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");

                tmDesignerPage.requiredRadioButtonYES.click();
                tmDesignerPage.autoOkRadioButtonYES.click();
                tmDesignerPage.clearableRadioButtonYES.click();

                tmDesignerPage.minDateErrorMsgInputBox.sendKeys((generateRandomNumberChars(10)));
                tmDesignerPage.maxDateErrorMsgInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.invalidErrorMsgInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.classNamePropsInputBox.sendKeys("random");
                break;

            case "Textfield":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");

                Assert.assertTrue(tmDesignerPage.labelMinLength.isDisplayed(), "Label Min length is not displayed");
                Assert.assertTrue(tmDesignerPage.labelmaxLength.isDisplayed(), "Label Max length is not displayed");

                Assert.assertTrue(tmDesignerPage.minLengthErrorMsgInputBox.isDisplayed(), "Minimum Length Error Msg Input Box not displated");
                Assert.assertTrue(tmDesignerPage.maxLengthErrorMsgInputBox.isDisplayed(), "Maximum Length Error Msg Input Box not displated");

                Assert.assertTrue(tmDesignerPage.labelMultiline.isDisplayed(), "Label Multiline is not displayed");
                Assert.assertTrue(tmDesignerPage.multilineRadioBtnGrp.isDisplayed(), "Multiline Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.labelMaskValue.isDisplayed(), "Label Mask Value is not displayed");
                Assert.assertTrue(tmDesignerPage.maskValueRadioBtnGrp.isDisplayed(), "Mask Value Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.iconInputBox.isDisplayed(), "Icon Input Box is not displayed");

                tmDesignerPage.requiredRadioButtonYES.click();
                tmDesignerPage.fieldDataTypeDropdownBox.click();
                List<String> dataTypes = new ArrayList<>();
                for (WebElement e : tmDesignerPage.TextfieldDataTypes) {
                    waitFor(1);
                    dataTypes.add(e.getText());
                }
                tmDesignerPage.dataTypeValue.click();
                Assert.assertEquals(dataTypes, expectedFieldDataTypes.get());
                selectRandomDropDownOption(tmDesignerPage.fieldDataTypeDropdownBox);
                tmDesignerPage.minLengthInputBox.sendKeys((getRandomNumber(1)));
                tmDesignerPage.maxLengthInputBox.sendKeys(getRandomNumber(2));

                tmDesignerPage.minLengthErrorMsgInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.maxLengthErrorMsgInputBox.sendKeys(generateRandomNumberChars(10));

                tmDesignerPage.formatErrorMsgInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.classNamePropsInputBox.sendKeys("random");
                break;

            case "Dropdown":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.labelMultiselect.isDisplayed(), "Label Auto Ok is not displayed");
                Assert.assertTrue(tmDesignerPage.multiselectRadioBtnGrp.isDisplayed(), "Auto Ok Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.serviceDropdownBox.isDisplayed(), "Service Dropdown is not displayed");
                Assert.assertTrue(tmDesignerPage.tableDropdownBox.isDisplayed(), "Table dropdown is not displayed");

                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.iconInputBox.isDisplayed(), "Icon Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");

                tmDesignerPage.requiredRadioButtonYES.click();
                tmDesignerPage.multiselectRadioButtonYES.click();
                waitFor(2);
                scrollUpUsingActions(2);
                scrollToElement(tmDesignerPage.serviceDropdownBox);
                selectDropDown(tmDesignerPage.serviceDropdownBox, "mars-contact-record-blcrm");
                selectDropDown(tmDesignerPage.tableDropdownBox, "ENUM_DUMMY");
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.iconInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.classNamePropsInputBox.sendKeys("random");
                break;

            case "Autocomplete Dropdown":
                Assert.assertTrue(tmDesignerPage.labelRequired.isDisplayed(), "Label Required is not displayed");
                Assert.assertTrue(tmDesignerPage.requiredRadioBtnGrp.isDisplayed(), "Required Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.labelMultiselect.isDisplayed(), "Label Auto Ok is not displayed");
                Assert.assertTrue(tmDesignerPage.multiselectRadioBtnGrp.isDisplayed(), "Auto Ok Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.serviceDropdownBox.isDisplayed(), "Service Dropdown is not displayed");
                Assert.assertTrue(tmDesignerPage.tableDropdownBox.isDisplayed(), "Table dropdown is not displayed");
                Assert.assertTrue(tmDesignerPage.labelMultiselect.isDisplayed(), "Label Auto Ok is not displayed");
                Assert.assertTrue(tmDesignerPage.labelMisplayStyle.isDisplayed(), "Display Style Radio Buttons are not displayed");
                Assert.assertTrue(tmDesignerPage.serviceDropdownBox.isDisplayed(), "Service Dropdown is not displayed");
                Assert.assertTrue(tmDesignerPage.tableDropdownBox.isDisplayed(), "Table dropdown is not displayed");

                Assert.assertTrue(tmDesignerPage.errorMessageInputBox.isDisplayed(), "Error message Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.iconInputBox.isDisplayed(), "Icon Input Box is not displayed");
                Assert.assertTrue(tmDesignerPage.classNamePropsInputBox.isDisplayed(), "Class Name Props Input is not displayed");

                tmDesignerPage.requiredRadioButtonYES.click();
                tmDesignerPage.multiselectRadioButtonYES.click();
                tmDesignerPage.displayStyleRadioButtonTextField.click();
                waitFor(2);
                scrollUpUsingActions(2);
                scrollToElement(tmDesignerPage.serviceDropdownBox);
                selectDropDown(tmDesignerPage.serviceDropdownBox, "mars-contact-record-blcrm");
                selectDropDown(tmDesignerPage.tableDropdownBox, "ENUM_DUMMY");
                tmDesignerPage.errorMessageInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.iconInputBox.sendKeys(generateRandomNumberChars(10));
                tmDesignerPage.classNamePropsInputBox.sendKeys("random");
                break;

        }

        tmDesignerPage.saveFieldButton.click();
    }

    @And("I can verify common properties of the field {string} displayed")
    public void i_can_verify_common_properties_of_the_field_displayed(String fieldType) {
        tmDesignerPage.addFieldButton.click();
        scrollUpUsingActions(2);
        selectDropDown(tmDesignerPage.fieldTypeDropdown, fieldType);
        Assert.assertTrue(tmDesignerPage.fieldKeyInputBox.isDisplayed(), "Field Key Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldNameInputBox.isDisplayed(), "Field Name Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldOrderInputBox.isDisplayed(), "Field Order Input Box is not displayed");
        Assert.assertTrue(tmDesignerPage.fieldDisplayLabelInputBox.isDisplayed(), "Field Display Label Box is not displayed");
    }

    @And("I verify the new field {string} is successfully created and displayed in fields list")
    public void i_verify_the_new_field_something_is_successfully_created_and_displayed_in_fields_list(String fieldType) {
        Assert.assertTrue(tmDesignerPage.getFieldTicket(fieldKey.get()).isDisplayed(), "Field is not displayed in field list");
        Assert.assertTrue(tmDesignerPage.getFieldTicketType(fieldKey.get(), fieldType).isDisplayed(), "Field Type is not displayed in field list");
    }

    @And("I verify newly created template is displayed in template list")
    public void i_verify_newly_created_template_is_displayed_in_template_list() {
        Assert.assertTrue(tmDesignerPage.getTemplateFromList(templateKey.get()).isDisplayed());
    }

    @And("I verify newly updated template is displayed in template list")
    public void i_verify_newly_updated_templateis_displayed_in_template_list() {
        Assert.assertTrue(tmDesignerPage.getTemplateFromList(templateKey.get()).isDisplayed());
        Assert.assertEquals(templateName.get().toLowerCase(), tmDesignerPage.getTemplateNameFromList(templateKey.get()).getText().toLowerCase());
    }


    @When("I click on add page button to create new page")
    public void clickingAddButton() {
        tmDesignerPage.addPageButton.click();
        pageKey.set("pg" + generateRandomNumberChars(5));
        tmDesignerPage.pageKeyInput.sendKeys(pageKey.get());
        pageName.set("Page" + getRandomString(3));
        tmDesignerPage.pageNameInput.sendKeys(pageName.get());
        tmDesignerPage.savePageButton.click();
        System.out.println("PAGE KEY" + pageKey.get());
        Assert.assertTrue(tmDesignerPage.getPageKeyWithvalue(pageKey.get()).isDisplayed());
    }

    @And("I click on Configure Rules section")
    public void iClickOnConfigureRulesSection() {
        tmDesignerPage.configureRulesSection.click();
    }

}
