@ui-wf-nj-issue
Feature: Validation of Work Queue Page

  @CP-13133 @CP-13133-03 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify in task view page all dynamic fields are displayed if we not pass those in request body in NJ-SBE project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Review Complaint" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I Navigate to view task details page by using taskId which we created from api
    Then Verify all the dynamic fields of templates are displayed with null value
      |COMPLAINT ABOUT|
      |NAME        |
      |REASON         |
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     |68    |1446      |12357    |CP-13133|currentDate|

  @CP-13133 @CP-13133-04 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify in view task page extra field is not displayed even if we pass that in request body in NJ_SBE project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Review Complaint" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |External Case ID|
      |Test 1234       |
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I Navigate to view task details page by using taskId which we created from api
    Then Verify all the dynamic fields of templates are displayed with null value
      |COMPLAINT ABOUT|
      |NAME        |
      |REASON         |
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     |68    |1446      |12357    |CP-13133|currentDate|

  @CP-10684 @CP-10684-03 @ui-wf-nj @nj-regression @Basha
  Scenario: Verify initiate button for the Review complaint in Work Queue
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "Work Queue" page
    And I will ensure assignee contains at least one "Review Complaint" task if not I will create task priority as "" taskStatus as "" assignee as "" task info as "" complaintAbout as "Other" name as "TestRC" reason as "Other" contactReason as "" prefCallBackDate as "" prefCallBackTime as "" prefPhone as "" extCaseID as "" appID as ""
    And I click task id to get the results in descending order
    And I will get the index of "Review Complaint" task and click on initiate button for that
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-10684 @CP-10684-04 @ui-wf-nj @nj-regression @Basha
  Scenario: Verify that Task information field with 300 characers
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "Work Queue" page
    When I navigate to "Review Complaint" task page
    And I will provide following information before creating task
      | taskInfo          ||
      | assignee          ||
      | complaintAbout    |Other|
      | name              |Vidya Mithun|
      | reason            |Customer Service|
    And I click on save button in create task page
    And I click task id to get the results in descending order
    And I will get the index of "Review Complaint" task and click on initiate button for that
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And verify task note field length in task slider
    And I click on save in Task Slider
    When I click on My task tab
    And I click task id to get the results in descending order
    #And I verify Task Notes are saved for the task
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-10684 @CP-10684-05 @ui-wf-nj @nj-regression @Basha
  Scenario: Verify task slider status
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "Work Queue" page
    And I will ensure assignee contains at least one "Review Complaint" task if not I will create task priority as "" taskStatus as "" assignee as "" task info as "" complaintAbout as "Other" name as "TestRC" reason as "Other" contactReason as "" prefCallBackDate as "" prefCallBackTime as "" prefPhone as "" extCaseID as "" appID as ""
    And I click task id to get the results in descending order
    And I will get the index of "Review Complaint" task and click on initiate button for that
    Then Verify task slider is displayed
    Then I verify Task slider Status drop down values
      |In Progress|
      |Complete|
      |Escalated|
      |Cancel|
    And I will update the following information in task slider
      | status       | Complete |
    Then I verify Task slider Disposition drop down values
      |Referred|
      |Resolved|
      |Unresolved|
    And I will update the following information in task slider
      | disposition  | Resolved |
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-10684 @CP-10684-06 @ui-wf-nj @nj-regression @Basha
  Scenario: Verify Disposition field is required field in task slider
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "Work Queue" page
    And I will ensure assignee contains at least one "Review Complaint" task if not I will create task priority as "" taskStatus as "" assignee as "" task info as "" complaintAbout as "Other" name as "TestRC" reason as "Other" contactReason as "" prefCallBackDate as "" prefCallBackTime as "" prefPhone as "" extCaseID as "" appID as ""
    And I click task id to get the results in descending order
    And I will get the index of "Review Complaint" task and click on initiate button for that
    Then Verify task slider is displayed
    And I select value "Complete" from Task slider Status drop down
    Then I click save without selecting Disposition dd value to verify mandatory error message
    And I select value "Referred" from Task slider Disposition drop down
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-10852 @CP-10852-05 @ui-wf-nj @nj-regression @vidya
  Scenario: Validate Disposition field value when select from edit page for NJ-SBE project
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I make sure assignee contains at least one "Process Inbound Document" task if not I will create task
    And I click task id to get the results in descending order
    And I ensure My task page has at least one task with type "Process Inbound Document" and I navigate to view task
    Then verify disposition field is not displayed
    And I click on edit button on view task page
    And I minimize Genesys popup if populates
    And I changed status on edit page to "Complete"
    And I select value in "REASON FOR EDIT" drop down
    And I update the dispostion field in task slider as "Resolved"
    And I click on save button on task edit page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-10687 @CP-10687-05 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From work queue is navigate to View Inbound Correspondence Details UI for Inbound Application Data Entry
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And Get the task type information of "Inbound Application Data Entry" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    When If any In Progress task present then update that to Cancelled
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Task No Longer Required"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId   |taskInfo|triggerDate|
      |NJ-SBE     |615   |1000006776|takeFromAPI |CP-10687|currentDate|

  @CP-10687 @CP-10687-07 @CP-13257 @CP-13257-07 @CP-38837 @CP-38837-03 @nj-regression @ui-wf-nj @vidya @ruslan
  Scenario: Verification of Claim Next functionality for NJ-SBE project
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Work Queue" page
    When I click on "TaskId" column on My Tasks Page
    #Then I verify task records are displayed in descending order of their "TaskId"
    And I will check whether it contains at least one "Inbound Application Data Entry" task if not I will create task
    And I click on Claim Next button
    Then Verify task slider is displayed
    And I click on save button on task slider
    Then I verify user is redirected to work queue page
    And Close the soft assertions

  @CP-10822 @CP-10822-05 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From work queue is navigate to View Inbound Correspondence Details UI for Returned Mail
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And Get the task type information of "Returned Mail" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    When If any In Progress task present then update that to Cancelled
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And verify task note field length in task slider
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Task No Longer Required"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    #Then I verify view page has task note which we recently added
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId  |taskInfo|triggerDate|
      |NJ-SBE     | 620  |1000009986|takeFromAPI|CP-10687|currentDate|

  @CP-11414 @CP-11414-05 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From work queue is navigate to View Inbound Correspondence Details UI for Verification Document Upload
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And Get the task type information of "Verification Document Upload" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    When If any In Progress task present then update that to Cancelled
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And verify task note field length in task slider
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Task No Longer Required"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    #Then I verify view page has task note which we recently added
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId  |taskInfo|triggerDate|
      |NJ-SBE     | 1016 |1000010743|takeFromAPI|CP-11414|currentDate|

  @CP-10689 @CP-10689-05 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From work queue is navigate to View Inbound Correspondence Details UI for Process Inbound Document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And Get the task type information of "Process Inbound Document" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    When If any In Progress task present then update that to Cancelled
    And I Navigate to task which we created from api using task id and click on initiate button
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And verify task note field length in task slider
    And I update the task status in task slider as "Cancel"
    And I will select reason for cancel drop down value as "Task No Longer Required"
    And I click on save in Task Slider
    Then I verify user is redirected to work queue page
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    #Then I verify view page has task note which we recently added
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId  |taskInfo|triggerDate|
      |NJ-SBE     |738   |2150      |takeFromAPI|CP-10689|currentDate|

  @CP-13257 @CP-13257-06 @vidya @nj-regression @ui-wf-nj
  Scenario: Work queue, verification of task slider is open inside task view details page
    Given I logged into CRM with "Service Account 5" and select a project "NJ-SBE"
    When I navigate to "Incomplete contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo  | Test CP-13257|
      | assignee  ||
      | Status    |Escalated     |
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I will ensure there is no task in progress status
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-16225 @CP-16225-03 @CP-23119 @CP-23119-03 @CP-28139 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of GCNJ Resolve Appeal task type with Dispossion value in create, edit, view taask details page
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "GCNJ Resolve Appeal" task page
    And I will provide following information before creating task
      | taskInfo     | Test CP 16225|
      | status       |Escalated|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "GCNJ Resolve Appeal" task with "Escalated" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status         |Complete|
    And I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
    And I will update the following information in edit task page
      | disposition    |IDR Successful |
      | reasonForEdit  |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-15914 @CP-15914-02 @basha @nj-regression @ui-wf-nj
  Scenario: Update action taken field in Edit task page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | taskInfo          |Test CP-15914|
      | Status            |Escalated|
      | actionTaken       |-- --|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review Appeals Form" task with "Escalated" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            |OnHold|
      | reasonForOnHold   |Missing Information|
      | assignee          |Service AccountEight|
      | actionTaken       |Outbound Call Successful,Outbound Call Unsuccessful,No Action Taken|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    Then I verify tasks are have initiate button since searched parameter has permission to work
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-15913 @CP-15913-03 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of Review OAL Appeal Decision task type with Dispossion value in create, edit, view taask details page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Review OAL Appeal Decision" task page
    And I will provide following information before creating task
      | taskInfo     | Test CP 15913|
      | status       |Escalated|
      | actionTaken  |Final Agency Decision Letter Generated|
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Review OAL Appeal Decision" task with "Escalated" status does have Initiate button
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status              |Cancelled|
      |reasonForCancel      |Created Incorrectly|
      | deselectActionTaken |Final Agency Decision Letter Generated|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-14664 @CP-14664-2.1 @CP-14664-03 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId on Work Queue Page and Task Slider
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I click on initiate contact record
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | taskInfo    | Task Info CP-14664-2.1|
      | actionTaken | No Action Taken       |
    And I click on save button in create task page
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I verify all field are displayed on My Task or Work Queue page
    And I verify GetInsured case and consumer ids in "Work Queue"
    Then I click on initiate randomly
    Then Verify task slider is displayed
    And I verify the below details are displayed in task slider
    And I verify GetInsured case and consumer ids in "Task Slider"
    And I will update the following information in task slider
      | status       |Complete      |
      | disposition  |IDR Successful|
    And I click on save in Task Slider
    And Close the soft assertions