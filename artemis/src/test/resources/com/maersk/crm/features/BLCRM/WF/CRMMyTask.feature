Feature: My Task View/Details/Navigation

  #refactorBy:Vidya Date:29-08-2020(combain many scripts as one end to end test script)
  @CRM-970 @CRM-970-01 @CRM-971 @CRM-971-01 @CRM-968 @CRM-968-01 @CP-5190 @CP-5190-04 @CP-4407 @CP-4407-3 @CRM-968 @CRM-968-03 @crm-regression @ui-wf @muhabbat @shruti
  Scenario: Verification all fields and mandatory fields on create task page
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | taskType                 |General|
      | priority                 |4|
      | status                   |Escalated|
      | assignee                 |Service AccountEight|
      | taskInfo                 |random|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    Then I verify task list has five records with pagination
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all field are displayed on My Task or Work Queue page
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I am Navigated to Task details page
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    Then I am able to navigate back to My Task or Work Queue page
    And Close the soft assertions

     #refactorBy:Vidya Date:24-02-2020 (Vidya need to do refactor this srcipt)
  @CRM-968 @CRM-968-05 @shruti @crm-regression @ui-wf
  Scenario Outline: Verify task are sorted ascending and desceding order
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I click on "<columnName>" column on My Tasks Page to set to descending
    Then I verify task records are displayed in descending order of their "<columnName>"
    And Close the soft assertions
    Examples:
      | columnName |
      | TaskId     |

#    new changes applied if you click on edit task and do not update and navigate away no warning message will be displayed.
#  @CP-689 @CP-689-03 @vidya @crm-regression @ui-wf
  Scenario: Verify warning is Display
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I click on Active Contact widget
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Task Details |
    And I click on Active Contact widget
    And I click on continue button on warning message
    Then Verify should I return back to Active Contact screen
    And Close the soft assertions

  @CP-448 @CP-448-01 @CP-5190 @CP-5190-02 @CP-5190 @CP-5190-05 @paramita @crm-regression @ui-wf
  Scenario: Validate past Due Task are in highlighted mode
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I navigate to "My Task" page
    Then I verify Task on the list are highlighted whose Due Date is past and not completed
    And I verify task is NOT past due and is NOT highlighted in red
    And Close the soft assertions

  @CP-330 @CP-330-01 @muhabbat @crm-regression @ui-wf
  Scenario: Verification Priority is displayed at FirstGlance
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    Then I verify Priority is displayed at First Glance
    And Close the soft assertions

  @CP-330 @CP-330-02 @muhabbat @crm-regression @ui-wf
  Scenario Outline: Verify task are sorted in ascending order
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I click on "<columnTitle>" column on My Tasks Page
    Then I verify task records are displayed in ascending order of their "<columnTitle>"
    And Close the soft assertions
    Examples:
      | columnTitle |
      | Priority    |

  @CP-330 @CP-330-03 @muhabbat @crm-regression @ui-wf
  Scenario Outline: Verify task are sorted in descending order
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I navigate to "My Task" page
    And I click on "<columnTitle>" column on My Tasks Page to set to descending
    Then I verify task records are displayed in descending order of their "<columnTitle>"
    And Close the soft assertions
    Examples:
      | columnTitle |
      | Priority    |

  @CP-11767 @CP-11767-05 @Basha @crm-regression @ui-wf
  Scenario: Verify task is assignee in My Tasks
    Given I logged into CRM with "Service Tester 5" and select a project "BLCRM"
    When I navigate to "My Task" page
    Then I verify assignee is the user who logged in "Service TesterFive"
    And Close the soft assertions

#  @CP-11767 @CP-11767-06 @Basha @crm-regression @ui-wf redundancy
#  Then I verify Complete or Cancelled task is NOT in My Tasks queue already validating it
  Scenario Outline: Verify task status cancelled complete in My Tasks
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "My Task" page
    Then I will verify "<taskStatus>" status task not present in Task list
    And Close the soft assertions
    Examples:
      | taskStatus |
      | Complete   |
      | Cancelled  |

  @CP-10686 @CP-10686-02 @CP-10687 @CP-10687-01 @CP-12963 @CP-12963-03 @CP-28816 @CP-28816-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create and Edit Task in NJ-SBE for Inbound Application Data Entry task with all data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Inbound Application Data Entry" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL APPLICATION ID|
      |EXTERNAL CASE ID       |
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10686$|
      | assignee                 |Service AccountTwo|
      | status                   |Escalated    |
      | externalApplicationId    |Test123|
      | externalCaseId           |caseId123|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does not have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 | 3            |
      | status                   | Escalated    |
      | reasonForEdit            |Corrected Data Entry|
      | assignee                 ||
      | taskInfo                 | Test CP-10687 Task Info$|
      | externalApplicationId    | UpdateTo4567|
      | externalCaseId           | UpdateCaseId1234|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 | 2           |
      | status                   |OnHold|
      | reasonForOnHold          |Missing Information|
      | assignee                 |Service AccountTwo|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-10688 @CP-10688-02 @CP-10689 @CP-10685-02 @CP-15323 @CP-15323-04 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create and Edit Task in NJ-SBE for Process Inbound Document Entry task with all data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Process Inbound Document" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10688|
      | assignee                 |Service AccountTwo|
      | externalCaseId           |CaseId123|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 |4            |
      | status                   |Escalated    |
      | assignee                 ||
      | taskInfo                 |TTest CP-10689 Task Info$|
      | externalCaseId           |UpdateCaseId1234|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-10823 @CP-10823-02 @CP-10822 @CP-10822-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create and Edit Task in NJ-SBE for Returned Mail task with all data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Returned Mail" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10823|
      | assignee                 |Service AccountTwo|
      | externalCaseId           |CaseId123|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 |4            |
      | status                   |Escalated    |
      | assignee                 ||
      | taskInfo                 |Test CP-10822 Task Info|
      | externalCaseId           |UpdateCaseId1234|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-11413 @CP-11413-02 @CP-11414 @CP-11414-01 @CP-31982 @CP-31982-10 @priyal @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create and Edit Task in NJ-SBE for Verification Document Upload task with all data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Verification Document Upload" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
    And I will provide following information before creating task
      | taskInfo                 | Test CP-11413|
      | assignee                 | Service AccountTwo|
      | externalCaseId           | CaseId123|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 | 4            |
      | status                   | Escalated    |
      | assignee                 ||
      | taskInfo                 | Test CP-11414 Task Info|
      | externalCaseId           | UpdateCaseId1234|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-10691 @CP-10691-02 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Outbound Call task with all data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    Then I verify dynamic fields are dispayed
      |CONTACT REASON       |
      |NAME                 |
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
      |PREFERRED PHONE         |
    And I will provide following information before creating task
      | taskInfo                 | Test CP-10691|
      | assignee                 | Service AccountTwo|
      | contactReason            |Announcements,Change of Address|
      |name                      |Vidya Mithun|
      | preferredCallBackDate    |today|
      | preferredCallBackTime    |03:28 PM|
      | preferredPhone           |1234567890|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions
    

  @CP-10931 @CP-10931-02 @CP-12963 @CP-12963-01 @CP-968 @CP-968-08 @CP-15323 @CP-15323-05 @vidya @crm-regression @ui-wf
  Scenario: Validate Create and Edit Task in BLCRM for Incomplete Contact Record task with all data
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Incomplete Contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10931$|
      | assignee                 |Service AccountEight|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    Then I Verify "Incomplete Contact Record" task with "Created" status does not have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I will verify task note is not displayed in edit page when task is in create status
    And I will update the following information in edit task page
      | priority                 |4            |
      | status                   |Escalated    |
      | assignee                 |Service AccountEight|
      | taskInfo                 |Test CP-10931 Task Info$|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    When If any In Progress task present then update that to Cancelled
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Then I am able to navigate back to My Task or Work Queue page
    And I click task id to get the results in descending order
    Then I Verify "Incomplete Contact Record" task with "Escalated" status does have Initiate button
    And I click task id to get the results in descending order
    And I click on initiate randomly
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    And I click task id to get the results in descending order
    Then I Verify "Incomplete Contact Record" task with "Open" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority                 |1|
      | status                   |OnHold|
      | reasonForOnHold          |Missing Information|
      | assignee                 |Service AccountEight|
      | taskInfo                 |random|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Then I am able to navigate back to My Task or Work Queue page
    And I click task id to get the results in descending order
    Then I Verify "Incomplete Contact Record" task with "OnHold" status does have Initiate button
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-10685 @CP-10685-06 @CP-37763 @CP-37763-04 @priyal @vidya @nj-regression @ui-wf-nj
  Scenario Outline: Validate Create Task UI in NJ-SBE for Review Complaint task with all data and Verify BU details
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiated Business Unit By Project ID via API "6210"
    Then I can verify Business Unit get API response will be "success"
    Then I get the business unit data from TM
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Complaint" task page
    Then I verify dynamic fields are dispayed
      |COMPLAINT ABOUT|
      |NAME           |
      |REASON         |
    And I will provide following information before creating task
      | taskInfo                 |Test CP-10685|
      | assignee                 |Service AccountTwo|
      | complaintAbout           |CAC (maersk)|
      | name                     |Vidya Mithun|
      | reason                   |Customer Service|
      | businessUnitAssigneeTo   |Back Office|
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | businessUnitAssigneeTo  ||
      | assignee                |Service AccountTwo|
    And I click on save button on task edit page
    And I verify the updated information in view task details page
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task table record for the task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And Close the soft assertions
    Examples:
      | taskId | taskType        | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn |contactReason|
      || Review Complaint|| Created||          ||            ||                || true          ||            ||          | Service AccountTwo | today     ||

  @CP-16149 @CP-16149-02 @CP-16063 @CP-16063-03 @CP-16148 @CP-16148-02 @vidya @crm-regression @ui-wf
  Scenario: Validate action taken field in create, view and Edit task details page navigating from My task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | taskInfo      | Test CP-16149|
      | assignee      | Service AccountOne|
      | actionTaken   |Acknowledgement Letter Generated,IDR Resolved Letter Generated,Outbound Call Successful,Outbound Call Unsuccessful|
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I will update the following information in edit task page
      | deselectActionTaken |Acknowledgement Letter Generated,IDR Resolved Letter Generated,Outbound Call Successful,Outbound Call Unsuccessful|
    And I will check action taken is not mandatory when task status is not complete
    And I changed status on edit page to "Complete"
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify Action Taken drop down value
      | Acknowledgement Letter Generated |
      | IDR Resolved Letter Generated    |
      | No Action Taken                  |
      | Outbound Call Successful         |
      |Outbound Call Unsuccessful        |
    And I verify "disposition" single select drop down value
      | Resolved   |
      | Unresolved |
    And I changed status on edit page to "Escalated"
    And I will check action taken is not mandatory when task status is not complete
    And I will update the following information in edit task page
      | status              |Cancelled|
      |reasonForCancel      |Created Incorrectly|
    And I will check action taken is not mandatory when task status is not complete
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-16225 @CP-16225-02 @CP-23119 @CP-23119-02 @CP-28139 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of GCNJ Resolve Appeal task type with Disposion value in create, edit, view task details page
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "GCNJ Resolve Appeal" task page
    And I will provide following information before creating task
      | taskInfo     | Test CP 16225|
      | assignee     | Service TesterFour|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "GCNJ Resolve Appeal" task with "Created" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         |Complete|
    And I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
    And I will update the following information in edit task page
      | disposition    |IDR Unsuccessful|
      | reasonForEdit  |Corrected Data Entry|
    And I click on save button on task edit page
    #Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

    #Its already Present in above script
  #@CP-16149 @CP-16149-02 @vidya @crm-regression @ui-wf
  Scenario: Validate action taken field in view task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | taskInfo                 | Test CP 16149|
      | assignee                 | Service AccountOne|
      | actionTaken   |Acknowledgement Letter Generated,IDR Resolved Letter Generated,Outbound Call Successful,Outbound Call Unsuccessful|
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And Close the soft assertions






