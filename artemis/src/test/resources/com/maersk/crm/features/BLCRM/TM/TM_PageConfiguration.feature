@PageConfiguration
Feature: Tenant Manager - Page Configuration

  @CP-37231 @CP-36336 @CP-37231-01 @CP-36336-01 @CP-38912 @mital @tm-regression @ui-tm
  Scenario: View list of pages and create template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Grid" and enter details
    And I verify newly created template is displayed in template list

  @CP-37231 @CP-36336 @CP-37231-02 @CP-36336-02 @CP-38912 @mital @tm-regression @ui-tm
  Scenario: View list of pages and update template details
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Form" and enter details
    And I verify newly created template is displayed in template list
    And I click on newly created template to edit and save it
    And I verify newly updated template is displayed in template list

    @Cp-37432 @CP-37432-01 @CP-36337 @42059 @mital @tm-regression @ui-tm
    Scenario: Verify creation of Field Type - Checkbox
      Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
      And I navigate to project designer page
      When I verify list of pages is displayed
      Then I click on page "activeContact" to create/select template in it
      And I verify list of templates is displayed
      And I click on add template button of type "Form" and enter details
      And I verify newly created template is displayed in template list
      Then I select newly created template
      And I click on button add field to create new field of type "Checkbox"
      And I verify the new field "Checkbox" is successfully created and displayed in fields list

  @Cp-36339 @CP-36339-01 @CP-36339-01 @42059 @42059-04@mital @tm-regression @ui-tm
  Scenario Outline: Verify creation of Common Properties of an Field
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Form" and enter details
    And I verify newly created template is displayed in template list
    Then I select newly created template
    And I can verify common properties of the field "<fieldType>" displayed
    Examples:
      | fieldType             |
      | Radio Button          |
      | Autocomplete Dropdown |
      | Button                |
      | Datepicker            |
      | Dropdown              |
      | Textfield             |
      | Timepicker            |
      | Checkbox              |

  @CP-37431 @CP-37431-01 @CP-36339-01 @CP-36337 @42059 @42059-01 @mital @tm-regression @ui-tm
  Scenario: Verify creation of Field Type - Radio Button
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Form" and enter details
    And I verify newly created template is displayed in template list
    Then I select newly created template
    And I click on button add field to create new field of type "Radio Button"
    And I verify the new field "Radio Button" is successfully created and displayed in fields list

  @CP-37430 @CP-37430-01 @CP-36339-01 @CP-36337 @42059 @42059-02 @mital @tm-regression @ui-tm
  Scenario: Verify creation of Field Type - Timepicker
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Form" and enter details
    And I verify newly created template is displayed in template list
    Then I select newly created template
    And I click on button add field to create new field of type "Timepicker"
    And I verify the new field "Timepicker" is successfully created and displayed in fields list

  @CP-36331 @CP-36331-01 @CP-39480 @CP-36337 @42059 @42059-03 @mital @tm-regression @ui-tm
  Scenario: Verify creation of Field Type - Textfield
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Form" and enter details
    And I verify newly created template is displayed in template list
    Then I select newly created template
    And I click on button add field to create new field of type "Textfield"
    And I verify the new field "Textfield" is successfully created and displayed in fields list

  @CP-37226 @CP-37226-01 @CP-39482 @CP-37230 @CP-36337 @CP-36337-01 @priyal @mital @tm-regression @ui-tm
  Scenario Outline: Verify creation of Field Type - Dropdown and Field Type - Autocomplete Dropdown
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I select template "regressionTemplate" to add fields in it
    And I click on button add field to create new field of type "<fieldType>"
    And I verify the new field "<fieldType>" is successfully created and displayed in fields list
    Examples:
      |fieldType            |
      |Dropdown             |
      |Autocomplete Dropdown|

  @CP-36333 @CP-36333-01 @CP-39912 @mital @tm-regression @ui-tm
  Scenario: Verify associating Grid to a existing CP template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I click on add template button of type "Grid" and enter details
    And I verify newly created template is displayed in template list
    Then I select newly created template
    Then I enter grid details to create new grid
    And I click on save button to save grid details

  @CP-36333 @CP-36333-02 @CP-39912 @mital @tm-regression @ui-tm
  Scenario: Verify associating only one Grid to a existing CP template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I select template "rgressionGridTemplate" to add fields in it
    And I verify new grid cannot be created in template

  @CP-36333 @CP-36333-03 @CP-39912 @mital @tm-regression @ui-tm
  Scenario: Verify Grid column list to a existing CP template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I select template "rgressionGridTemplate" to add fields in it
    And I verify Add Grid Column button is displayed
    And I verify list of Grid Columns is displayed

  @CP-38223 @CP-38223-01 @CP-39917 @mital @tm-regression @ui-tm
  Scenario Outline: Verify Grid column list to a existing CP template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create/select template in it
    And I verify list of templates is displayed
    And I select template "rgressionGridTemplate" to add fields in it
    And I verify Add Grid Column button is displayed
    And I add columns with following data "<Column Key>","<Column Label>","<Position>","<Clickabel>","<Seperator>","<PropertyCount>"
    And I verify the column got saved and displayed
    When I click on saved Grid Column
    Then I verify the column details are displayed with properties
    Examples:
      | Column Key | Column Label | Position | Clickabel | Seperator | PropertyCount|
      | Random     | Random       | 5        | YES       | --        |      3        |

