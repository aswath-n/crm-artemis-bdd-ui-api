@CreateTask
Feature: Create Task Functionality Check

  @CP-118  @CP-118-01 @CP-4407 @CP-4407-1 @CP-37688 @CP-37688-01 @crm-regression @ui-wf @vidya
  Scenario: Verification all fields and mandatory fields on create task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    Then I verify create task fields displayed
    Then I verify task type is selected as "General"
    And I will provide following information before creating task
      | taskType                 ||
      | priority                 ||
      | status                   ||
      | assignee                 ||
      | assigneeBusinessUnit     ||
      | taskInfo                 |ABC|
    And I click on save button on task edit page
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    Then I verify task mandatory fields error message "TASK TYPE"
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "STATUS"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify "taskPriority" single select drop down value
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
    Then I verify "taskStatus" single select drop down value
      | Complete |
      | Created |
      | Escalated |
    And I select "General" option in task type drop down
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And Get the task type information of "General" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions

  @CP-118  @CP-118-02 @crm-regression @ui-wf @vidya
  Scenario: Verification all fields and mandatory fields on create task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I select "Inbound Task" option in task type drop down
    Then I verify create task field values are changed
    And Close the soft assertions

    #Verify warning is Display when User attempts to return to Active Contact and has unsaved data entry
  @CP-689 @CP-689-02 @vidya @crm-regression @ui-wf
  Scenario: Verify warning is Display
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskType                 ||
      | priority                 ||
      | status                   ||
      | assignee                 ||
      | taskInfo                 |Task info|
    And I click on Active Contact widget
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Create Task |
    And I click on Active Contact widget
    And I click on continue button on warning message
    Then Verify should I return back to Active Contact screen
    And Close the soft assertions

  @CP-670 @CP-670-05 @paramita @regression @crm @crm-regression @ui-wf
  Scenario: Verify warning message is Display when User attempts to Create Task with unsaved data entry
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskType                 |General|
      | priority                 |3|
      | status                   ||
      | assignee                 |Service AccountOne|
      | taskInfo                 |Task info|
    When I click to navigate "Inbound Task" task page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |Create Task|
    When I click to navigate "Inbound Task" task page
    And I click on continue button on warning message
    Then I should return back to Create Task screen
    And Close the soft assertions

  @CP-4533 @CP-4533-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of all check box on the Create Plan Provider Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Plan/Provider" task page
    Then I verify all check box are able to select
    And Close the soft assertions

  @CP-4533 @CP-4533-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of all text box on the Create Plan Provider Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Plan/Provider" task page
    Then I verify all text box fields values
    Then I verify text box Date and Time field value and error message for following fields
      |Provider First Name   |
      |Provider Last Name    |
    And Close the soft assertions

  @CP-4533 @CP-4533-04 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create Plan Provider Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Plan/Provider" task page
    Then I verify Plan Id drop down value
      | 84 |
      | 85 |
      | 86 |
      | 87 |
    And I verify Plan Name drop down value
      | Amerigroup Community Care |
      | Caresource Georgia        |
      | Peach State               |
      | Wellcare                  |
    And I verify Plan change reason drop down value
      | Plan Change Reason 1 |
      | Plan Change Reason 2 |
      | Plan Change Reason 3 |
    And I verify Provider record issue drop down value
      | Provider Record Issue 1 |
      | Provider Record Issue 2 |
      | Provider Record Issue 3 |
      | Provider Record Issue 4 |
    And Close the soft assertions

  @CP-4533 @CP-4533-05 @vidya @crm-regression @ui-wf
  Scenario: Verification of all date fields on the Create Plan Provider Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Plan/Provider" task page
    Then I verify text box Date and Time field value and error message for following fields
      |Plan Effective Date|
      |Plan Start Date|
    And Close the soft assertions

  @CP-4535 @CP-4535-02 @CP-12321 @CP-12321-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of fields length for new old and provider address line 1 and 2 on the Create Address task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    Then I verify fields length for new old and provider address line 1 and 2
    And Close the soft assertions

  @CP-4535 @CP-4535-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create Address task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    Then I verify all Drop Down fields values on create Address task page
    And Close the soft assertions

  @CP-7885 @CP-7885-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create General/CRM/EB task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    And I select below list values in contact Reason multi select drop down
      |Information Request|
      |Update Information Request|
      |Materials Request         |
      |Missing Information Request|
      |Other                      |
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And Close the soft assertions

  @CP-4528 @CP-4528-02 @vidya @crm-regression @ui-wf
  Scenario: Verification of all text box on the Create General/CRM/EB Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    Then I verify text box Date and Time field value and error message for following fields
      |PREFERRED PHONE        |
      |Case Worker First Name |
      |Case Worker Last Name  |
    And Close the soft assertions

  @CP-4528 @CP-4528-03 @CP-15949 @CP-15949-03 @CP-13625 @CP-13625-01 @vidya @crm-regression @ui-wf
  Scenario: Verification of all drop down on the Create General/CRM/EB Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    Then I verify channel drop down value
      |Email        |
      |Fax          |
      |IVR          |
      |Mail         |
      |Mobile App   |
      |Phone        |
      |SMS Text     |
      |System Integration|
      |Web          |
      |Web Chat     |
    And I verify Contact Reason drop down value
      | Information Request        |
      | Update Information Request |
      | Materials Request          |
      | Missing Information Request|
      |  Other                     |
    And I verify Outreach Location drop down value
      | Location 1 |
      | Location 2 |
      | Location 3 |
    And I verify Program drop down value
      | CHIP |
      | Managed Care |
      | Medicaid |
      | Medicare |
      | SNIP  |
      | TANF  |
    And Close the soft assertions

  @CP-4528 @CP-4528-04 @CP-32925 @CP-32925-03 @vidya @crm-regression @ui-wf
  Scenario: Verification of all date and time fields on the Create General/CRM/EB Task Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General/CRM/EB" task page
    Then I verify text box Date and Time field value and error message for following fields
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
      |Appointment Date|
      |Appointment Time|
      |Date Of Birth|
    And Close the soft assertions

  @CP-11890 @CP-11890-01 @CP-11890-03 @CP-4535 @CP-4535-01 @crm-regression @ui-wf @Sean
  Scenario: Verify existing Address Fields display in their Field Group - create task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Address" task page
    Then I will see the following field labels in New Address section
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
    Then I will see the following field labels in Old Address section
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
    Then I will see the following field labels in Provider Address section
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
      | COUNTY         |
    And Close the soft assertions

  @CP-11890 @CP-11890-04 @crm-regression @ui-wf @ozgen
  Scenario Outline: Verify existing Address Fields display in their Field Group - view task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated get task types API for project "<projectId>"
    When I run get task type API
    And I get task type id for task type name "Address"
    And I initiated create task API
    And I create request body with below field params and assign it to created user "true"
      | Task_Field_Names | Task_Field_Values  |
      | TASK_INFO        | CP-11890 Testing   |
      | ASSIGNEE         | Service AccountOne |
      | NEW_ADDRESS      ||
      | OLD_ADDRESS      ||
      | PROVIDER_ADDRESS ||
    And I run create task API
    Then I verify task created successfully
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And Wait for 3 seconds
    Then I verify Address Field Groups are ordered in View task
      | NEW ADDRESS DETAILS      |
      | OLD ADDRESS DETAILS      |
      | PROVIDER ADDRESS DETAILS |
    Then I will see the following field labels in New Address section in view task
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
    Then I will see the following field labels in Old Address section in view task
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
    Then I will see the following field labels in Provider Address section in view task
      | ADDRESS LINE 1 |
      | ADDRESS LINE 2 |
      | CITY           |
      | STATE          |
      | ZIP CODE       |
      | COUNTY         |
    And Close the soft assertions
    Examples:
      | projectName | projectId |
      | BLCRM       ||

  @CP-11619 @CP-11619-01 @CP-18403 @CP-18403-02 @crm-regression @ui-wf @Basha
  Scenario: Verification of NJ task fields
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "fnpBBIU" and Last Name as "lnIOMKa"
    And I link the contact to an existing Case or Consumer Profile
    And I navigate to "NJ" task page
    And I verify Complaint About drop down value
      | CAC (maersk) |
      | Carrier       |
      #| Coverage      |
      #|Customer Service|
      | Exchange      |
      | FFM           |
      | Medicaid      |
      | Other         |
    Then I verify external application id accepts 30 alphanumeric spaces are allowed
    Then I verify external case id accepts 30 alphanumeric spaces are allowed
    Then I verify Name drop down fields value
      | fnpBBIU lnIOMKa |
    Then I verify user can enter a Name "Jason Roy" that does not exist in the consumers on the case or consumer profile
    And Close the soft assertions

  @CP-4534 @CP-4534-01 @crm-regression @ui-wf @kamil
  Scenario: Verify Display Selected Fields from Task Template
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Correspondence" task page
    And Verify Email error with wrong input
    And Verify FROM NAME field input
    And Verify field values on the Create Task screen
    Then Verify Invalid Address Reason dropdown
      | New Address, Moved - No Forwarding Address |
      | PO Box Closed                              |
      | Moved Out of Country                       |
      | Non-Deliverable                            |
      | Missing Suite or Apartment                 |
      | Poor Address Quality                       |
      | Move is Suggested                          |
      | Invalid City - State - ZIP Combo           |
    Then Verify Returned Mail Reason dropdown
      | Undeliverable                      |
      | Refused                            |
      | Mailbox Full                       |
      | Destination Agent Unresponsive     |
      | Destination Agent Rejected Message |
      | Destination Invalid                |
    And Close the soft assertions

#  @CP-4534 @CP-4534-2 @crm-regression @ui-wf @kamil
  Scenario: Verify Display Fields in Alphabetical Order and Outbound corres dropdown
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated get Correspondence List API
    And I run get Api request for Correspondence List
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Correspondence" task page
    Then Verify fields listed in alphabetical order
    Then Verify OUTBOUND CORRESPONDANCE TYPE dropdown values in Create Task screen
    And Close the soft assertions