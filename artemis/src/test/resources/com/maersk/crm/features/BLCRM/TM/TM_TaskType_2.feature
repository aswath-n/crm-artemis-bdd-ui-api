Feature: Tenant Manager - Task Type Functionality v2

  @CP-8954 @CP-8954-01 @CP-10950 @CP-10227 @paramita @tm-regression @ui-tm
  Scenario:Verify Associate Screen dropdown options in Create Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I click on Associate Screen dropdown field
    Then I see dropdown options sorted in alphabetical order

  @CP-8954 @CP-8954-02 @paramita @tm-regression @ui-tm
  Scenario:Verify Associate Screen dropdown options in Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "General" record
    And I click Edit task type button
    And I click on Associate Screen dropdown field
    Then I see dropdown options sorted in alphabetical order

  #failing due to defect : CP-10830
  @CP-8954 @CP-8954-03 @paramita @tm-regression @ui-tm
  Scenario:Verify  Associate Screen name option value is successfully saved while creating task type
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required task type information
    And I click on Add template button
    Then I see blank record get added in Template section
    When I select value "random" for template dropdown
    And I enter start date as "today" on associate template
    And I click on 'Add template' button once again
    And I click on Associate Screen dropdown field
    And I select dropdown value as 'Active Contact'
    And I provide the permission information for creating task
    When I click on save button on create task type screen
    Then I verify success message is displayed on task type screen
    And I verify newly created task type record

@CP-8954 @CP-8954-04 @paramita @tm-regression @ui-tm
Scenario:Verify  Associate Screen name option value is successfully updated while updating task type
Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
And I navigate to Task Type Page
When I select a task type "General" record
And I click Edit task type button
And I scroll down to Associate Screen
And I click on Associate Screen dropdown field
And I select dropdown value as 'View Task Details'
And I click on save button on create task type screen
Then I verify update success message is displayed on View task type screen
And I scroll down to Associate Screen
Then I verify saved value for screen name 'View Task Details' in View screen

  @CP-8954 @CP-8954-05 @paramita @tm-regression @ui-tm
  Scenario:Verify read mode value for screen name in View task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "General" record
    And I scroll down to Associate Screen
    Then I verify readonly mode value for screen name in View Task type screen

  @CP-9631 @CP-9631-01 @Basha @tm-regression @ui-tm
  Scenario:Verification of SERVICE REQUEST indicator for Creating type and not saving changes
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I select the  Service Request checkbox
    Then I verify screen disables and clears any existing content from the PERMISSION GROUP TO WORK and the PERMISSION GROUP TO WORK - ESCALATED fields

  @CP-9631 @CP-9631-02 @Basha @tm-regression @ui-tm
  Scenario:Verification of SERVICE REQUEST indicator for editing type and not saving changes
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    And I click on Edit option
    And I select the Service Request checkbox on Edit Task Type page
    Then I verify screen disables and clears any existing content from the PERMISSION GROUP TO WORK and the PERMISSION GROUP TO WORK - ESCALATED fields

  @CP-9631 @CP-9631-03 @Basha @tm-regression @ui-tm
  Scenario:Verification of SERVICE REQUEST indicator for Creating type and saving changes
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I select the  Service Request checkbox
    Then I verify screen disables and clears any existing content from the PERMISSION GROUP TO WORK and the PERMISSION GROUP TO WORK - ESCALATED fields
    And I select a value for SR category field
    Then I click on save button on create task type screen
    And I verify newly created task type record

  @CP-9631 @CP-9631-04 @Basha @tm-regression @ui-tm
  Scenario:Verification of SERVICE REQUEST indicator for editing type and saving changes
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    Then I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    And I click on Edit option
    And I select the Service Request checkbox on Edit Task Type page
    Then I verify screen disables and clears any existing content from the PERMISSION GROUP TO WORK and the PERMISSION GROUP TO WORK - ESCALATED fields
    And I select a value for SR category field on Edit Task Type page
    Then I click on save button on create task type screen
    Then I should be able to save Task Type record successfully

  @CP-13475 @CP-13475-01 @vidya @tm-regression #@ui-tm-removed
  Scenario Outline:Verify task type permission drop down values in view page
    Given I logged into Tenant Manager and set the project context "project" value "SelectNJSBEConfig"
    And I navigate to Task Type Page
    When I select a task type "<taskType>" record
    Then I verify Permission Group to Work drop down values as expected
      | Back Office Csr     |
      | Csr                 |
      | Mailroom Specialist |
      | Mailroom Supervisor |
      | Manager             |
      | Research Unit Csr   |
      | Supervisor          |
      | Training Specialist |
    And I verify Permission Group to Work Escalate drop down values as expected
      | Manager             |
      | Supervisor          |
      | Research Unit Csr   |
      | Mailroom Supervisor |
    And I verify Permission Group to Create or Edit drop down values as expected
      | Mailroom Specialist |
      | Training Specialist |
      | Research Unit Csr   |
      | Mailroom Supervisor |
      | Csr                 |
      | Manager             |
      | Reporting Analyst   |
      | Supervisor          |
    Examples:
      | taskType                       |
      | Review Complaint               |
      | Outbound Call                  |
      | Process Inbound Document       |
      | Inbound Application Data Entry |
      | Returned Mail                  |
      | Verification Document Upload   |

  @CP-9076 @CP-9076-02 @basha @tm-regression @ui-tm
  Scenario:Validate the Inquiry/Complaint Task Type configuration
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Task Type Page
    When I select a task type "Inquiry/complaint" record
    Then I verify fields in task type screen
      | taskType               | Inquiry/complaint                                                                                                  |
      | serviceRequestCBStatus | false                                                                                                              |
      | priority               | 4                                                                                                                  |
      | dueInDays              | 2 Business Days                                                                                                    |
      | description            | Task to capture the Secure Message for all inquiries or complaints submitted by Consumers via the Secure WebPortal |
      | selectTemplate         | Inquiry/Complaint                                                                                                  |
      | associateScreenDD      | View Task Details                                                                                                  |
    Then I verify Permission Group to Work drop down values as expected
      | Csr |
    And I verify Permission Group to Work Escalate drop down values as expected
      | Csr |
    And I verify Permission Group to Create or Edit drop down values as expected
      | Csr |

  @CP-14371 @CP-14371-01 @vinuta @tm-regression @ui-tm
  Scenario:Verify Associate Screen name 'Member Matching'
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Type Page
    And I click on add new task type button
    And I provide the required task type information
    And I click on Associate Screen dropdown field
    And I select dropdown value as 'Member Matching'
    And I provide the permission information for creating task
    When I click on save button on create task type screen
    Then I verify success message is displayed on task type screen
    And I verify newly created task type record
    When I select a newly created task type record
    Then I verify saved value for screen name 'Member Matching' in View screen

  @CP-20847 @CP-20847-01 @priyal @tm-regression @ui-tm
  Scenario:Verify task type permission drop down values in view page for Enrollment Escalation Task
    Given I logged into Tenant Manager and set the project context "project" value "SelectNJSBEConfig"
    And I navigate to Task Type Page
    When I select a task type "Enrollment Escalation" record
    Then I verify fields in task type screen
      | taskType               | Enrollment Escalation                                                   |
      | serviceRequestCBStatus | false                                                                   |
      | priority               | 3                                                                       |
      | dueInDays              | 2 Calender Days                                                         |
      | description            | The purpose of this task is to handle escalations related to enrollment |
      | selectTemplate         | Enrollment Escalation                                                   |
      | associateScreenDD      | View Task Details                                                       |
    Then I verify Permission Group to Work drop down values as expected
      | Escalations Unit Csr |
      | Manager              |
      | Supervisor           |
    And I verify Permission Group to Work Escalate drop down values as expected
      | Escalations Unit Csr |
      | Manager              |
    And I verify Permission Group to Create or Edit drop down values as expected
      | Csr                  |
      | Escalations Unit Csr |
      | Manager              |
      | Supervisor           |

#Feature: Tenant Manager - Edit Task Type

  @CP-1755 @CP-1755-01 @paramita @tm-regression @ui-tm
  Scenario: Verify read only fields displayed in Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select existing task type "Generaltest" record, if not will create new task type with required information
    And I click on Edit option
    Then I see the fields in Ready only state in Edit Task Type screen
    And I should see the Save or Cancel Button in Edit Task Type screen

  @CP-1755 @CP-1755-02 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify error validation message for required permission fields in Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    When I clear data for required Associate permission group "<Field>",then I see error error validation message
    Examples:
      | Field                                          |
      | ASSOCIATE PERMISSION GROUP TO WORK             |
      | ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED |

  @CP-1755 @CP-1755-03 @CP-23091 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify cancel button functionality appear on warning message on selecting Back arrow or Cancel option or navigate away in Edit task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    And I update task type permission "<Field>"
    And I click on available "<Button>" option
    And I click on cancel option on warning message
    Then I verify user is on edit task type screen and information should not save
    Examples:
      | Field                                | Button     |
      | ASSOCIATE PERMISSION GROUP TO CREATE | Cancel     |
      | ASSOCIATE PERMISSION GROUP TO EDIT   | Back Arrow |
      | ASSOCIATE PERMISSION GROUP TO CREATE | User icon  |

  @CP-1755 @CP-1755-04 @CP-23091 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify Continue button functionality appear on warning message on selecting Back arrow or Cancel option or navigate away in Edit task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    And I update task type permission "<Field>"
    And I click on available "<Button>" option
    And I click on Continue option in warning message
    Then I verify user is redirected to task type list screen
    Examples:
      | Field                                | Button     |
#      | ASSOCIATE PERMISSION GROUP TO EDIT   | Cancel     |
      | ASSOCIATE PERMISSION GROUP TO CREATE | Back Arrow |
      | ASSOCIATE PERMISSION GROUP TO EDIT   | User icon  |

  @CP-1755 @CP-1755-05 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify Save Permission & Success Message in Edit task type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    And I update permission "<Field>" with "<Value>" and click on Save
    Then I verify edit task is saved successfully and redirect to View Task screen
    Examples:
      | Field                                | Value   |
#      | ASSOCIATE PERMISSION GROUP TO CREATE | Non Csr |
      | ASSOCIATE PERMISSION GROUP TO EDIT   | Non Csr |

  @CP-1755 @CP-1755-06 @CP-23091 @paramita @tm-regression  #@ui-tm-invalid
  Scenario Outline: Verify optional permission field in Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    When I clear data for optional Associate permission group "<Field>"
    Then I should be able to save Task Type record successfully
    And I should see value get cleared for the optional permission field
    Examples:
      | Field                                |
      | ASSOCIATE PERMISSION GROUP TO CREATE |
      | ASSOCIATE PERMISSION GROUP TO EDIT   |

  @CP-1755 @CP-1755-07 @paramita @tm-regression @ui-tm
  Scenario Outline:Verify No warning message display on click available buttons without task updating task type field
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click on Edit option
    And I click on available "<Button>" option
    Then I verify user is redirected to task type list screen
    Examples:
      | Button     |
      | Cancel     |
      | Back Arrow |
      | User icon  |

  @CP-1755 @CP-1755-08 @paramita @tm-regression @ui-tm
  Scenario: Verify error validation message for required permission fields in Add/Create Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide all information for creating task without permission field
    And I click on Add Save button
    Then I verify warning message displayed for required permission field

  @CP-12207 @CP-12207-01 @CP-23091 @umid @tm-regression @ui-tm
  Scenario Outline: Save new Task Type w/Permissions
    Given I logged into Tenant Manager and set the project context "project" value "RegressionBaseline"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide all information for creating task without permission field
    And I enter "ASSOCIATE PERMISSION GROUP TO WORK" as "<value1>" in task type screen
    And I enter "ASSOCIATE PERMISSION GROUP TO WORK - ESCALATED" as "<value2>" in task type screen
    And I enter "ASSOCIATE PERMISSION GROUP TO CREATE" as "<value3>" in task type screen
    And I enter "ASSOCIATE PERMISSION GROUP TO EDIT" as "<value3>" in task type screen
    And I click on Add Save button
    And I navigate to the newly created task
    And I get rawLogs for "mars/tm/tasktype/save" "<value1>" "<value2>" "<value3>"
    Examples:
      | value1 | value2  | value3 |
      | Csr    | Non Csr | Csr    |

  @CP-12207 @CP-12207-02 @CP-23091 @umid @tm-regression @ui-tm
  Scenario: Update existing Task Type - add Permissions
    Given I logged into Tenant Manager and set the project context "project" value "RegressionBaseline"
    And I navigate to Task Type Page
    When I select a task type "General" record
    And I click on Edit option
    And I enter "ASSOCIATE PERMISSION GROUP TO CREATE" as "Non Csr" in task type screen
    And I enter "ASSOCIATE PERMISSION GROUP TO EDIT" as "Non Csr" in task type screen
    And I click on Add Save button
    And I get rawLogs for "mars/tm/tasktype/save" "" "" "Non Csr"

#Feature: Tenant Manager -TaskType UI Updates to accomodate SR use

  @CP-4898 @CP-4898-01 @Shruti @tm-regression @ui-tm
  Scenario: Verify Service Request checkbox is displayed on Create task UI
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify that Service Request checkbox is displayed

  @CP-4898 @CP-4898-02 @Shruti @tm-regression @ui-tm
  Scenario: Verify Service Request checkbox is not mandatory
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating service request
    And I uncheck the  Service Request checkbox
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    Then I verify that Service Request checkbox is not mandatory

  @CP-4898 @CP-4898-03 @Shruti @tm-regression @ui-tm
  Scenario: verify SR category field is displayed on create task screen and is mandatory
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I click on add new task type button
    And I select the  Service Request checkbox
    Then I verify SR category field is displayed on create task screen
    When I click on save button on create task type screen
    Then I verify SR category field is mandatory

  @CP-4898 @CP-4898-04 @Shruti @tm-regression @ui-tm @CP-18386
  Scenario: verify SR category field is single select dropdown & dropdown values
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I click on add new task type button
    And I select the  Service Request checkbox
    Then I verify SR category field is single select dropdown
    And I verify SR category field has values
      | Appeal            |
      | Application       |
      | Complaint         |
      | Inquiry           |
      | Outreach          |
      | Research/Analysis |

  @CP-4898 @CP-4898-05 @Shruti @tm-regression @ui-tm
  Scenario: Verify Service Request is created succesfully
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I click on add new task type button
    And I select the  Service Request checkbox
    And I select a value for SR category field
    And I provide the required information for creating service request
    And I click on save button on create task type screen
    Then I verify success message is displayed on task type screen

  @CP-4898 @CP-4898-06 @Shruti @tm-regression @ui-tm
  Scenario: Verify view task type details -  Service reqest checkbox and SR category field value are saved
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I click on add new task type button
    And I provide the required information for creating service request
    And I click on save button on create task type screen
    And I click on the created SR
    Then I verify the SR checkbox and SR category values are saved

  @CP-4898 @CP-4898-07 @Shruti @tm-regression @ui-tm
  Scenario: view task type details -  Add icon
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I click on add new task type button
    And I provide the required information for creating service request
    And I click on save button on create task type screen
    Then I verify icon is displayed for Task and Service Request

  @CP-28997 @CP-28997-01 @Chandrakumar @tm-regression @ui-tm
  Scenario: Verify Systematically Close checkbox is displayed on create task screen and is mandatory
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I verify that Systematically Close checkbox is displayed
    And I select the Systematically Close checkbox in "Create" page
    And I uncheck the Systematically Close checkbox in "Create" page
    And I select the Systematically Close checkbox in "Create" page
    And I provide the required information for creating task
    And I select the  Service Request checkbox
    And I verify that systemtically Close checkbox is disabled and cleared out
    And I uncheck the  Service Request checkbox
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    Then I verify that Systematically Close checkbox is not mandatory

  @CP-28997 @CP-28997-02 @Chandrakumar @tm-regression @ui-tm
  Scenario: Verify Systematically Close checkbox is displayed on Edit Task Type screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task
    And I provide the permission information for creating task
    And I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to newly created task Type details page
    And I click on Edit option
    And I verify that Systematically Close checkbox is displayed
    And I select the Systematically Close checkbox in "Edit" page
    And I uncheck the Systematically Close checkbox in "Edit" page
    And I select the Systematically Close checkbox in "Edit" page
    And I select the Service Request checkbox on Edit Task Type page
    And I verify that systemtically Close checkbox is disabled and cleared out
    And I select a value for SR category field on Edit Task Type page
    And I click on save button on create task type screen
    Then I verify that Systematically Close checkbox is not mandatory on Edit Task Type page

  @CP-25173 @CP-33214 @CP-33214-01 @tm-regression @ui-tm @mital
  Scenario Outline: Update Permission group name when role name update happens
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    And I click on the configuration icon for the project
    And I navigate to Task Type Page
    When I click on add new task type button
    And I provide the required information for creating task with created role name
    Then I click on save button on create task type screen
    And I verify newly created task type record
    And I navigate to User role list screen
    When I selected newly created role
    Then I update role name with new name
    And I click on the configuration icon for the project
    And I navigate to Task Type Page
    When I select previously created Task Type
    Then I verify updated role name in Task Type Detail screen

    Examples:
      | roleName | roleDesc | startDate | endDate |
      | {random} | {random} | today     |[blank]|


  @CP-24418 @CP-24418-01 @tm-regression @ui-tm @mital
  Scenario: Add Link Requirements to Task Type Configuration - Create
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I click on add new task type button
    Then I verify fields on the create task type screen
      | Required Links Header |
      | CASE                  |
      | CONSUMER              |
      | CASE AND CONSUMER     |
      | CORRESPONDENCE        |

  @CP-24418 @CP-24418-02 @tm-regression @ui-tm @mital
  Scenario: Add Link Requirements to Task Type Configuration - View
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I select a task type "General" record
    And I verify fields on the task type details screen
      | CASE              |
      | CONSUMER          |
      | CASE AND CONSUMER |
      | CORRESPONDENCE    |

  @CP-24418 @CP-24418-03 @tm-regression @ui-tm @mital
  Scenario: Add Link Requirements to Task Type Configuration - edit
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click Edit task type button
    And I scroll down to Associate Screen
    When I select following required links radio buttons and checkbox
      | CASE RADIO BUTTON       |
      | CORRESPONDENCE CHECKBOX |
    And I click on save button on create task type screen
    Then I verify update success message is displayed
    And I click on the configuration icon for the project
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    Then I verify Required Links got saved as per selection
      | CASE RADIO BUTTON       |
      | CORRESPONDENCE CHECKBOX |

  @CP-24418 @CP-24418-04 @tm-regression @ui-tm @mital
  Scenario: Add Link Requirements to Task Type Configuration - edit
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    And I click Edit task type button
    And I scroll down to Associate Screen
    When I select following required links radio buttons and checkbox
      | CASE AND CONSUMER RADIO BUTTON |
      | CORRESPONDENCE CHECKBOX        |
    And I click on save button on create task type screen
    Then I verify update success message is displayed
    And I click on the configuration icon for the project
    And I navigate to Task Type Page
    When I select a task type "Generaltest" record
    Then I verify Required Links got saved as per selection
      | CASE AND CONSUMER RADIO BUTTON |
      | CORRESPONDENCE CHECKBOX        |
