@ui-wf-nj-issue
Feature: Initiate Functionality oF My Task

  @CP-10684 @CP-10684-02 @ui-wf-nj @nj-regression @Basha
  Scenario Outline: Verify the field values on View Task details for the Review complaint
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "<Task Type>" task if not I will create task priority as "" taskStatus as "" assignee as "" task info as "" complaintAbout as "Other" name as "TestRC" reason as "Other" contactReason as "" prefCallBackDate as "" prefCallBackTime as "" prefPhone as "" extCaseID as "" appID as ""
    And I click task id to get the results in descending order
    And I will get the index of "<Task Type>" task and click on initiate button for that
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    And Close the soft assertions
    Examples:
      |Task Type       |
      |Review Complaint|

  @CP-10852 @CP-10852-03 @CP-10930 @CP-10930-01 @CP-10687 @CP-10687-06 @CP-34852 @CP-34852-07 @CP-34852-02 @ui-wf-nj @nj-regression @vidya
  Scenario: Validate Disposition field value when select from task slider for NJ-SBE project
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Inbound Application Data Entry" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "Inbound Application Data Entry" task and click on initiate button for that
    Then Verify task slider is displayed
    And Verify External Application Id accept only 30 character
    And I will update the following information in task slider
      | status            |Complete|
      | disposition       |Completed|
      | noteValue         | Test SliderNotes   |
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify view page has disposition value
    Then I verify saved task note from slider
    And I verify view page has external Application Id which we recently added
    And Close the soft assertions

#  @CP-10930 @CP-10930-02 @ui-wf-nj @nj-regression @vidya
  Scenario: Verify External Application ID us optional Field in task slider
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Inbound Application Data Entry" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "Inbound Application Data Entry" task and click on initiate button for that
    Then Verify task slider is displayed
    And I will clear the external application id if present in task slider
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify view page has external Application Id which we recently added
    And Close the soft assertions

#  @CP-14273 @CP-14273-01 @CP-10690 @CP-10690-02 @vidya @ui-wf-nj @nj-regression
  Scenario: Work Queue, verification of task slider is open inside Active Contact page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I will ensure assignee contains at least one "Outbound Call" task if not I will create task
    And I will get the index of "Outbound Call" task and click on initiate button for that
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    And I verify Task status drop down values in slider
      | In Progress |
      | Complete |
      | Escalated |
      | Cancel    |
    When I enter task notes "ABC" and select task status as "Complete"
    And I click on save in Task Slider
    And Verify the system displays an Error Message: "DISPOSITION is required and cannot be left blank"
    And I verify minimum lenght error message "TASK NOTES"
    And I verify dispostion field values in slider
      | Consumer not reached |
      | Consumer reached     |
      | Dialer Call Needed   |
      | Invalid Phone Number |
    When I enter text in task note field
    And I update the dispostion field in task slider as "Consumer reached"
    And I click on save in Task Slider
    Then I verify error message displayed and user remains on active contact screen and task slider closed
    When I navigate to "Task Search" page
    And I will search with taskId
    Then In result I verify task is saved with status "Complete"
    And Close the soft assertions

  @CP-14273 @CP-14273-03 @CP-10690 @CP-10690-03 @vidya @ui-wf-nj @nj-regression
  Scenario: My Task, verification of task slider is open inside Active Contact page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Outbound Call" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "Outbound Call" task and click on initiate button for that
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When  I should see following dropdown options for "contact reason" field displayed
      | Appeal |
    And  I choose "Follow Up" option for Contact Action field
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "GetCovered NJ" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    Then I verify error message displayed and contact record is saved
    And I click on save in Task Slider
    Then I verify navigate back to My Task or Work Queue page
    And Close the soft assertions

  @CP-13257 @CP-13257-05 @vidya @nj-regression @ui-wf-nj
  Scenario: My Task, verification of task slider is open inside task view details page
    Given I logged into CRM with "Service Account 7" and select a project "NJ-SBE"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I will ensure assignee contains at least one "Incomplete contact Record" task if not I will create task
    And I click task id to get the results in descending order
    And I will get the index of "Incomplete contact Record" task and click on initiate button for that
    Then Verify task slider is displayed
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    And Close the soft assertions

  @CP-10822 @CP-10822-04 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From Task search is navigate to View Inbound Correspondence Details UI for Returned Mail
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Returned Mail" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I verify Task status drop down values in slider
      | In Progress |
      | Complete |
      | Escalated |
      | Cancel |
    And I will update the following information in task slider
      | status            |Complete|
    And I verify dispostion field values in slider
      | Resolved |
      | Unresolved |
    And I update the dispostion field in task slider as "Unresolved"
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    Then I verify view page has disposition value
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     ||          |69805    |CP-10822|currentDate|

  @CP-11414 @CP-11414-04 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From Task Search is navigate to View Inbound Correspondence Details UI for Verification Document Upload
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Verification Document Upload" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I verify Task status drop down values in slider
      | In Progress |
      | Complete |
      | Escalated |
      | Cancel |
    And I will update the following information in task slider
      | status            |Complete|
    And I verify dispostion field values in slider
      | Unable to Upload |
      | Uploaded |
    And I update the dispostion field in task slider as "Uploaded"
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    Then I verify view page has disposition value
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     ||          |69806    |CP-11414|currentDate|

  @CP-10689 @CP-10689-04 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From Task search is navigate to View Inbound Correspondence Details UI for Process Inbound Document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Process Inbound Document" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I verify Task status drop down values in slider
      | In Progress |
      | Complete |
      | Escalated |
      | Cancel |
    And I will update the following information in task slider
      | status            |Complete|
    And I verify dispostion field values in slider
      | Resolved |
      | Unresolved |
    And I update the dispostion field in task slider as "Unresolved"
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And In search result click on task id to navigate to view page
    Then I verify view page has disposition value
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     ||          |69807    |CP-10689|currentDate|

  @CP-10687 @CP-10687-04 @nj-regression @ui-wf-nj @vidya
  Scenario Outline:Verify Initiate From Task Search is navigate to View Inbound Correspondence Details UI for Inbound Application Data Entry
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Inbound Application Data Entry" for project "NJ-SBE"
    And I will provide required information to create external task with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    And I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    And User is navigated to the View Correspondence Details UI for the linked Inbound Correspondence ID
    And I will update the following information in task slider
      | status            |Complete|
      | disposition       |Completed|
    And I click on save in Task Slider
    Then I verify user is navigate back to task search page
    And Close the soft assertions
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|triggerDate|
      |NJ-SBE     ||          |69804    |CP-10687|currentDate|

  @CP-13089 @CP-13089-1 @CP-13089-2 @CP-13089-3 @CP-30299 @CP-30299-04 @CP-30301 @CP-30301-07 @CP-30081 @CP-30081-06 @ui-wf-nj @nj-regression @elvin @ruslan
  Scenario: Validate Action Taken field in task slider for NJ-SBE project
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When I navigate to "Follow-Up on Appeal" task page
    And I will provide following information before creating task
      | assignee    | Service TesterFour |
      | actionTaken | No Action Taken    |
    And I click on save button in create task page
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "staffAssignedTo"
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify task search results are displayed
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And Verify task slider is displayed
    And I verify Action Taken value
    And I will update the action taken in task slider as ""
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Then I verify task mandatory fields error message "ACTION TAKEN"
    And I will update the following information in task slider
      | status          |Cancel|
    And I will check action taken is not mandatory when task status is not complete
    Then I verify "actionTaken" field does not contains value on "task slider" page
      | Acknowledgement Letter Generated |
      | No Action Taken                  |
      | Outbound Call Successful         |
      | Outbound Call Unsuccessful       |
    And I will update the following information in task slider
      | status          | Complete                         |
      | actionTaken     | Acknowledgement Letter Generated |
      | disposition     | IDR Unsuccessful                 |
    And I click on save in Task Slider
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I verify task details information for task fields for coverVA
    Then I verify task table record for the task
    And Close the soft assertions

  @CP-16059 @CP-16059-02 @CP-23202 @CP-23202-03 @crm-regression @ui-wf-nj @nj-regression @ruslan @priyal
  Scenario: Validate navigation Option to Task Slider to Allow a User to Navigate directly to View Task Details in NJ-SBE
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | taskInfo              | Line Break|
      | assignee              | Service AccountOne |
      | name                  | Ruslan             |
      | contactReason         | Announcements      |
      | preferredCallBackDate | today              |
      | preferredCallBackTime | 03:28 PM           |
      | preferredPhone        | 1234567890         |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    When I expand the first row in task list
    Then I verify the task details are displayed in my task when expanded
    When I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I click on the back arrow button
    Then I Verify user is navigate back to My task page
    And I navigate to newly created task by clicking on TaskID column header
    And I click on initiate randomly
    And I verify the below details are displayed in task slider
    And I click on task id on the task slider
    Then I verify should I navigated to view task page
    And I Verify Task slider is collasped
    Then I verify Active Contact Widget is visible on screen
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I will update the following information in task slider
      |status           |Cancel|
      |reasonForCancel  |Duplicate Task|
    And I click on save in Task Slider
    Then I verify error message displayed and user remains on active contact screen and task slider closed
    And Close the soft assertions

  @CP-25501 @CP-25501-02 @CP-28211 @CP-28211-01 @CP-30301 @CP-30301-12 @crm-regression @ui-wf-nj @nj-regression @ruslan @kamil
  Scenario: NJ-SBE : Validate user is not able to cancel task if task created systematically
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE Appeal Form"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE |
      | Channel      | Mail                   |
      | documentType | NJ SBE Appeal Form     |
      | ConsumerId   | 1                      |
      | CaseId       | 1                      |
    When I send the request to create the Inbound Correspondence Save Event
    When I navigate to "Task Search" page
    And I will search with srId
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I click on cancel button on task search page
    And I will search with srId
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click id of "Review Appeals Form" in Links section
    Then I verify "Review Appeals Form" task fields are displayed
    And I click on edit button on view task page
    Then I verify "task-status" field does not contains value on "edit" page
      | Cancelled |
    And I will update the following information in edit task page
      | assignee | Service AccountOne |
    And I click on save button on task edit page
    When I navigate to "Task Search" page
    And I will search with taskId
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    And Verify task slider is displayed
    Then I verify "taskStatus" field does not contains value on "task slider" page
      | Cancelled |
    And I will update the following information in task slider
      | status      | Complete        |
      | actionTaken | No Action Taken |
      | disposition | IDR Successful  |
    And I click on save in Task Slider
    Then I verify task save success message
    And Close the soft assertions

  @CP-30293 @CP-30293-03 @priyal @nj-regression @ui-wf-nj
  Scenario: Verification of Initiate button if permission group of work for NJ-SBE
    Given I logged into CRM with specific role "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Supervisor"
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I Verify "Incomplete contact Record" task with "Created" status does have Initiate button Task SR tab
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Incomplete Contact Record" task with "Created" status does have Initiate button
    Then I Verify "Incomplete Contact Record" task with "Open" status does have Initiate button
    Then I logOut while Working a Task
    Given I logged into CRM after logged out with "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Specialist"
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I Verify "Incomplete contact Record" task with "Created" status does not have Initiate button Task SR tab
    Then I Verify "Incomplete contact Record" task with "Open" status does have Initiate button Task SR tab
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    Then I Verify "Incomplete Contact Record" task with "Open" status does not have Initiate button
    Then I Verify "Incomplete Contact Record" task with "Created" status does not have Initiate button
    And Close the soft assertions