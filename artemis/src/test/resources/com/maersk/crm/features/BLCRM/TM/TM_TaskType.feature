Feature: Tenant Manager - Task Type Functionality - Version 1

  @CRM-1404 @CRM-1404-01 @Shruti @tm-regression @ui-tm
  Scenario: Verify create task type fields displayed
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify fields on the create task type screen
      | Task type    |
      | Priority     |
      | Due In Days  |
      | Description  |
      | Type of Days |
    And I verify task type field accepts max 50 characters
    And I verify type of days field selected by default value "Business Days"
    And I verify task type description field specifications
    And I verify task type due in days field specifications
    And I verify priority field has dropdown with values
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |



  #@CRM-1404 @CRM-1404-02 @Shruti @tm-regression @ui-tm
  #Merged in CRM-1404-01
  Scenario: Verify task type field specifications
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify task type field accepts max 50 characters

  #@CRM-1404 @CRM-1404-03 @Shruti @tm-regression @ui-tm
   #Merged in CRM-1404-01
  Scenario: Verify task type of days field default value
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify type of days field selected by default value "Business Days"

  @CRM-1404 @CRM-1404-04 @Shruti @tm-regression @ui-tm
  Scenario: Verify task type warning window displayed when click on back
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I click on back button on create task type
    Then I verify user is on create task type screen when click on cancel
    When I click on back button on create task type
    Then I verify user is redirected to task type configuration screen after clicking continue

  #@CRM-1404 @CRM-1404-05 @Shruti @tm-regression @ui-tm
   #Merged in CRM-1404-01
  Scenario: Verify task description field specifications
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify task type description field specifications

  #@CRM-1404 @CRM-1404-06 @Shruti @tm-regression @ui-tm
   #Merged in CRM-1404-01
  Scenario: Verify Due In Days field specifications
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify task type due in days field specifications


  #@CRM-1404 @CRM-1404-07 @Shruti @tm-regression @ui-tm
   #Merged in CRM-1404-01
  Scenario: Verify Priority Field specification
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify priority field has dropdown with values
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |

  @CRM-1404 @CRM-1404-08 @Shruti @tm-regression @ui-tm
  Scenario: Verify create task type mandatory fields error messages
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I click on save button on create task type screen
    Then I verify error is displayed for mandatory fields


  @CRM-1404 @CRM-1404-09 @Shruti @tm-regression @ui-tm @CRM-1409-03
  Scenario: Verify create task type fields displayed -enter group permission
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    Then I verify success message is displayed on task type screen
    And I verify newly created task type record

  @CRM-1404 @CRM-1404-10 @Shruti @tm-regression @ui-tm
  Scenario: task type -cancel & warning
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I click on cancel button on create tasktype screen
    Then I verify warning message is displayed on task type screen

  @CRM-1409 @CRM-1409-01 @CP-23091 @Shruti @tm-regression @ui-tm
  Scenario: task type -ASSOCIATE PERMISSION GROUP Fields are diaplayed
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify following fields are displayed
      | ASSOCIATE PERMISSION GROUP TO WORK             |
      | ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED |
      | ASSOCIATE PERMISSION GROUP TO CREATE           |
      | ASSOCIATE PERMISSION GROUP TO EDIT             |

  @CRM-1409 @CRM-1409-02 @CP-23091 @Shruti @tm-regression @ui-tm
  Scenario Outline: task type -ASSOCIATE PERMISSION GROUP Fields are displayed
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I enter "<Field>" as "<Value>" in task type screen
    And I enter "<Field>" as "<Value1>" in task type screen
    Then I verify the "<Field>" is multiselect
    Examples:
      | Field                                          | Value | Value1  |
      | ASSOCIATE PERMISSION GROUP TO WORK             | Csr   | Non Csr |
      | ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED | Csr   | Non Csr |
      | ASSOCIATE PERMISSION GROUP TO CREATE           | Csr   | Non Csr |
      | ASSOCIATE PERMISSION GROUP TO EDIT             | Csr   | Non Csr |


  @CRM-1409 @CRM-1409-04 @CP-23091 @Shruti @tm-regression @ui-tm
  Scenario Outline: task type -ASSOCIATE PERMISSION GROUP Auto complete
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify "<Field>" is auto complete when "<Value>" is entered
    Examples:
      | Field                                          | Value |
      | ASSOCIATE PERMISSION GROUP TO WORK             | Csr   |
      | ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED | Csr   |
      | ASSOCIATE PERMISSION GROUP TO CREATE           | Csr   |
      | ASSOCIATE PERMISSION GROUP TO EDIT             | Csr   |

 # @CRM-1410 @CRM-1410-01 @Shruti @tm-regression @ui-tm
  #Same as @CRM-1405-01
  Scenario: Verify Associate Screen is autocomplete & create a task type with Associate Screen
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record

  @CRM-1405 @CRM-1405-01 @aswath @tm-regression @ui-tm
  Scenario: Verify Newly created a task type with Associated Screen & verify task type section field values
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    Then I verify the task type detail section fields

  #@CRM-1405 @CRM-1405-02 @aswath @tm-regression @ui-tm
  #merged into CRM-1405-01
  Scenario: Verify task type section field values
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    Then I verify the task type detail section fields

  @CRM-1405 @CRM-1405-03 @aswath @tm-regression @ui-tm
  Scenario Outline: Verification of task type statuses are according order Active, Future, Inactive
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    And I create task type record with associate template start date "<startDate>" and end Date "<endDate>"
    And I verify newly created task type record
    Then Active task type are displayed on top followed by future and inactive
    Examples:
      | startDate | endDate |
      | current   | future  |

  @CRM-1405 @CRM-1405-04 @aswath @tm-regression @ui-tm
  Scenario Outline: Verification of task type statuses are accordingly to start and end date
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    And I create task type record with associate template start date "<startDate>" and end Date "<endDate>"
    And I verify newly created task type record
    Then verify status of Task type as "<status>"
    Examples:
      | startDate | endDate | status |
      | current   | future  | ACTIVE |
      | future    |[blank]| FUTURE |
      | current   |[blank]| ACTIVE |

  @CRM-1405 @CRM-1405-05 @aswath @tm-regression @ui-tm
  Scenario: Verification of task type status is INACTIVE
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record
    Then verify status of Task type as "INACTIVE"

  @CRM-1405 @CRM-1405-06 @aswath @tm-regression @ui-tm
  Scenario: Verification of Sort Order Associate Template
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    And I create task type record with multiple associate template multiple
    And I navigate to newly created task Type details page
    Then I verify the associate template sort order

  @CRM-1405 @CRM-1405-07 @aswath @tm-regression @ui-tm
  Scenario: Verification of only one Associate Screen can linked to task tpye
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I verify the number of associates screen linked

  @CRM-1405 @CRM-1405-08 @aswath @tm-regression @ui-tm
  Scenario: Verification of associate permission group to work
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I add multiple permissions to associated group to work
    Then I click on save button on create task type screen
    And I navigate to newly created task Type details page
    Then I verify multiple permissions to associated group to work


  @CRM-1405 @CRM-1405-09 @aswath @tm-regression @ui-tm
  Scenario: Verification of associate permission group to work - Escalated
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I add multiple permissions to associated group to work - Escalated
    Then I click on save button on create task type screen
    And I navigate to newly created task Type details page
    Then I verify multiple permissions to associated group to work - Escalated

  @CRM-1405 @CRM-1405-10 @CP-23091 @aswath @tm-regression @ui-tm
  Scenario: Verification of associate permission group to create/edit
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I add multiple permissions to associated group to create
    And I add multiple permissions to associated group to edit
    Then I click on save button on create task type screen
    And I navigate to newly created task Type details page
    Then I verify multiple permissions to associated group to create
    And I verify multiple permissions to associated group to edit



  #Refactored - Paramita 30th March 2020
  @CRM-1408 @CP-6214 @CRM-1408-01 @CP-6214-01 @vinuta @tm-regression @ui-tm
  Scenario: Verify Associate Template section is displayed with required fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I verify fields displayed under Associate Template

    #Refactored - CP-1075 Paramita - 30th March 2020
  @CRM-1408 @CP-1075 @CRM-1408-02 @CP-1075-02  @vinuta @tm-regression @ui-tm
  Scenario: Verify required fields in Associate Template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    And I select value "random" for template dropdown
    And I click on 'Add template' button once again
    Then I verify mandatory error is displayed for
      | Start Date |
    When I delete task template
    And I click on Add template button
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    Then I verify mandatory error is displayed for
      | Select Template |


  #failing CP-10706
  #Refactored  - Paramita 30th March 2020
  @CRM-1408 @CP-1075 @CRM-1408-03 @CP-1075-03 @vinuta @tm-regression @ui-tm
  Scenario: Validations for template start date & end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    And I enter the Start Date prior to the date of creation
    And I click on 'Add template' button once again
    And I see "The Start Date cannot be in the past" message under the "start" field on task type page
    When I enter the End Date "prior" to the Start Date on task type page
    And I click on 'Add template' button once again
    And I see "The End Date cannot be in the past" message under the "end" field on task type page



   #failing defect : CP-10700
  #Refactored - Paramita 30th March 2020
  @CRM-1408 @CP-1075 @CRM-1408-04 @CP-1075-04 @vinuta @tm-regression @ui-tm
  Scenario: Verify end date of 1st template is updated when 2nd template is added
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    And I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    And I select value "random" for template dropdown
    And I enter start date as "+2" on associate template
    And I click on 'Add template' button once again
    Then I verify end date is updated to "+1" on task template


  @CRM-1408 @CRM-1408-05 @vinuta
  Scenario: Verify template association can be cleared before saving
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    And I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on refresh template button
    Then I verify template association is cleared


    # Refactored - Paramita - 30th March 2020
  @CRM-1408 @CP-1075 @CRM-1408-06 @CP-1075-05  @vinuta @tm-regression @ui-tm
  Scenario: Verify end date of 1st template does not revert to null when 2nd template is deleted
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    And I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    And I select value "random" for template dropdown
    And I enter start date as "+2" on associate template
    And I click on 'Add template' button once again
    Then I verify end date is updated to "+1" on task template
    Then I delete template "two"
    And I verify end date is updated to "+1" on task template

    #Refactored - Paramita - CP-1075
  @CRM-1408 @CRM-1408-07 @CP-1075 @CP-1075-08 @vinuta @tm-regression @ui-tm
  Scenario: Verify task type is created by associating template with 'ADD Template' button
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    When I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    And I provide Associate Screen name
    And I provide the permission information for creating task
    When I click on save button on create task type screen
    Then I verify success message is displayed on task type screen
    And I verify newly created task type record


   #below scripts for CP-2002 and Author: Paramita
  @CP-2002 @CP-2002-01 @paramita @tm-regression @ui-tm
  Scenario: Verify read only fields displayed in View Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "General" record
    Then I should see all the fields in Ready only state in View Task Type screen
    And I do not see the Save or Cancel Button in View Task Type screen


  @CP-2002 @CP-2002-02 @paramita @tm-regression @ui-tm
  Scenario: Verify EDIT button displayed in View Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "General" record
    Then I verify EDIT button displayed in View Task Type screen


  @CP-2002 @CP-2002-03 @paramita @tm-regression @ui-tm
  Scenario:Verify green color code of the associated Task Template records based on current date in View Screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "General" record
    Then I verify color code of the associated Task Template based on "today" date

  #Refactored - Paramita 30th March 2020
  @CP-2002 @CP-2002-04  @paramita @tm-regression @ui-tm
  Scenario: :Verify orange color code of the associated Task Template records based on future date in View Screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    When I select value "random" for template dropdown
    And I enter start date as "+2" on associate template
    And I click on 'Add template' button once again
    And I provide Associate Screen name
    And I provide the permission information for creating task
    Then I click on save button on create task type screen
    When I select a newly created task type record
    Then I verify color code of the associated Task Template based on "future" date

#Feature: Tenant Manager -View Task Type

  @CP-1075 @CP-1075-01 @paramita @tm-regression @ui-tm
  Scenario:Verify 'Add Template' button display in Create Task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I see display of 'ADD TEMPLATE' button
    And I see no row get added under Associate Template section on click'ADD TEMPLATE' button

  @CP-1075 @CP-1075-06 @paramita  @tm-regression @ui-tm
  Scenario:Verify Green Task template color as Green when association template record is set as current date in Add Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    When I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    Then I verify color code of the associated Task Template based on "today" date

  @CP-1075 @CP-1075-07 @paramita @tm-regression @ui-tm
  Scenario:Verify Task template color as Orange when association template record is set as future date in Add Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    When I select value "random" for template dropdown
    And I enter start date as "+2" on associate template
    And I click on 'Add template' button once again
    Then I verify color code of the associated Task Template based on "future" date

  @CP-37879 @CP-37879-01 @mital  @tm-regression @ui-tm
  Scenario:Verify restrict duplicate task creation the task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I verify that restrict duplicate checkbox is displayed
    And I select the restrict duplicate checkbox in "Create" page
    And I uncheck the restrict duplicate checkbox in "Create" page
    And I select the restrict duplicate checkbox in "Create" page
    And I provide the required information for creating task
    And I verify that restrict duplicate checkbox is enabled
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    Then I verify that restrict duplicate checkbox is not mandatory

  @CP-37879 @CP-37879-02 @mital @tm-regression @ui-tm
  Scenario: Verify restrict duplicate on Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    And I click on Edit option
    And I verify that restrict duplicate checkbox is displayed
    And I select the restrict duplicate checkbox in "Edit" page
    And I uncheck the restrict duplicate checkbox in "Edit" page
    And I select the restrict duplicate checkbox in "Edit" page
    And I verify that restrict duplicate checkbox is enabled
    And I click on save button on create task type screen
    Then I verify that restrict duplicate checkbox is not mandatory on Edit Task Type page

  @CP-41912 @CP-41912-01 @CP-41912-02 @CP-41912-03 @mital @tm-regression @ui-tm
  Scenario: Verify an ability to add task type id while configuring a task type in TM
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    And I click on Edit option
    And I verify the Task Type Id is displayed



