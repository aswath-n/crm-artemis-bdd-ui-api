 Feature: Task & Service Request Tab Page Part 2

  @CP-9522 @CP-9522-01 @crm-regression @ui-wf @Basha
  Scenario: Manual Case/Consumer Search - Case Summary - Task & SR Tab
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click case consumer search tab
    When I searched customer have First Name as "Mike" and Last Name as "Pond"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Case Id "takeFromUI" and Consumer name in Task List
    And Close the soft assertions

  @CP-9522 @CP-9522-02 @crm-regression @ui-wf @Basha
  Scenario: Manual Case/Consumer Search - Consumer Profile - Task & SR Tab
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click case consumer search tab
    When I searched customer have First Name as "LauraMEZjB" and Last Name as "CormieraTbVf"
    When I click on consumer id in search result
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Case Id "-- --" and Consumer name in Task List
    And Close the soft assertions

  @CP-9522 @CP-9522-03 @crm-regression @ui-wf @Basha
  Scenario: Manual Case/Consumer Search - Case Summary - Task & SR Tab From Initiate Contact
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Mike" and Last Name as "Pond"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Case Id "takeFromUI" and Consumer name in Task List
    And Close the soft assertions

  @CP-9522 @CP-9522-04 @CP-30301 @CP-30301-02 @CP-30081 @CP-30081-01 @crm-regression @ui-wf @Basha @ruslan
  Scenario: Manual Case/Consumer Search - Consumer Profile - Task & SR Tab From Initiate Contact
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Case Id "-- --" and Consumer name in Task List
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "null"
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify task search results are displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And Close the soft assertions

  @CP-10931 @CP-10931-01 @CP-31982 @CP-31982-07 @priyal @vidya @crm-regression @ui-wf
  Scenario: Validate Create Task UI in BLCRM for Incomplete Contact Record
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Adam" and Last Name as "Sandler"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Incomplete Contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo                 ||
      | assignee                 ||
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    Then I verify all fields values on Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    And I verify initiate button is displayed
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And I Navigate to Contact Record details page
    And I verify the link section has case consumer and task information
    And Wait for 5 seconds
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I navigated to the Edit History tab ON Edit Task
    And I navigated to the Task Details tab ON Edit Task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And Close the soft assertions

  @CP-13257 @CP-13257-01 @CP-23202 @CP-23202-02 @priyal @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ_SBE for Incomplete Contact Record
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Incomplete contact Record" task page
    Then I verify create task fields displayed on screen
    And I will provide following information before creating task
      | taskInfo                 |Line Break|
      | assignee                 ||
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    Then I verify all fields values on Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    And I verify initiate button is displayed
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And I Navigate to Contact Record details page
    And I verify the link section has case consumer and task information
    And I click on Active Contact widget
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | taskInfo            |maxlength|
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I get the position of newly created task
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in work queue when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify save task search section is displayed
    When I expand the first row in search result list
    Then I verify the task details are displayed in search result when expanded
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I verify the below details are displayed in task slider
    And I will update the following information in task slider
      | status                  |Complete|
      | disposition             |Contact Record Updated - Incomplete|
    And I click on save in Task Slider
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And Close the soft assertions

  @CP-11764 @CP-11764-03 @CP-1229 @CP-1229-02 @priyal @vidya @crm-regression @ui-wf
  Scenario: Verification of task slider is open inside task active contact page
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | taskInfo              | Line Break           |
      | assignee              | Service AccountEight |
      | name                  | Tagdemo              |
      | contactReason         | Information Request  |
      | preferredCallBackDate | today                |
      | preferredCallBackTime | 03:28 PM             |
      | preferredPhone        | 1234567890           |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I searched customer have First Name as "John" and Last Name as "Zhangg"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When If any In Progress task present then update that to Cancelled
    When I click case consumer search tab
    When I searched customer have First Name as "John" and Last Name as "Zhangg"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    Then Verify task slider is displayed
    And I verify Active contact screen displayed with initiate call
    And I verify internal "Case" id is search by default
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I verify autopopulated value on Contact details component
    And I verify contact type,contact channel, phone, contact Reason fields are editable fields on active contact ui
    And I link the contact to an existing Case
    When  I should see following dropdown options for "contact reason" field displayed
      | Complaint - Customer Service |
    And  I choose "Escalated" option for Contact Action field
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    Then I verify error message displayed and contact record is saved
    And I click on the priority in dashboard
    And I click on task id on the task slider
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status      | Complete               |
      | actionTaken | Did Not Reach Consumer |
      | disposition | User closed            |
    And I click on save in Task Slider
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    And Close the soft assertions

  @CP-10685 @CP-10685-01 @CP-15323 @CP-15323-02 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Review Complaint task with only mandatory data
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Review Complaint" task page
    Then I verify dynamic fields are dispayed
      |COMPLAINT ABOUT|
      |NAME           |
      |REASON         |
    And I will provide following information before creating task
      | taskInfo          |Test-CP-10685 $ allow Special Character &%|
      | assignee          ||
      | complaintAbout    |CAC (maersk)|
      | name              |Vidya Mithun|
      | reason            |Customer Service|
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    Then I verify all fields values on Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    And I verify initiate button is displayed
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And I Navigate to Contact Record details page
    And I verify the link section has case consumer and task information
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I get the position of newly created task
    Then I verify all columns values are displayed is valid
    When I expand the first row in task list
    Then I verify the task details are displayed in work queue when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And I verify task id and edit task button are displayed
    And I verify the link section has case consumer and contact id information
    And Close the soft assertions

  @CP-16149 @CP-16149-01 @CP-16063 @CP-16063-05 @vidya @crm-regression @ui-wf
  Scenario: Validate action taken field in create task and view task details page navigating from TSR tab
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Dashawn" and Last Name as "Konopelski"
    When I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Follow-up on Appeal" task page
    And I will provide following information before creating task
      | Status      | Complete                                                                      |
      | actionTaken | IDR Resolved Letter Generated,IDR Unresolved Letter Generated,No Action Taken |
      | disposition | IDR Unsuccessful                                                              |
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-15914 @CP-15914-03 @basha @nj-regression @ui-wf-nj
  Scenario: Verify error messages are displayed for action taken and disposition fields
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Review Appeals Form" task page
    And I will provide following information before creating task
      | Status                   | Complete  |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "DISPOSITION"
    And I verify "actionTaken" multi select drop down value
      |No Action Taken                  |
      |Outbound Call Successful         |
      |Outbound Call Unsuccessful       |
    Then I verify "disposition" single select drop down value
      |IDR Successful   |
      |IDR Unsuccessful |
      |Not a Valid Appeal|
    And I will provide following information before creating task
      | Status            | Complete  |
      | taskInfo          |Test CP-15914|
      | actionTaken       |Outbound Call Successful,Outbound Call Unsuccessful|
      | disposition       |IDR Unsuccessful|
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-16225 @CP-16225-01 @CP-23119 @CP-23119-01 @CP-22822 @CP-22822-02 @CP-28139 @vidya @ruslan @nj-regression @ui-wf-nj
  Scenario: Verification of GCNJ Resolve Appeal task type with Disposion value in create, edit, view task details page
    Given I logged into CRM with "Service Tester 4" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Sam" and Last Name as "Lee"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "GCNJ Resolve Appeal" task page
    And I will provide following information before creating task
      | Status           |Complete     |
    Then I verify "disposition" single select drop down value
      | IDR Successful   |
      | IDR Unsuccessful |
    And I will provide following information before creating task
      | Status           |Complete      |
      | disposition      |IDR Successful|
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And I validate consumer name "Sam Lee" on the search result
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-15913 @CP-15913-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Verification of Review OAL Appeal Decision task type with Disposion value in create, edit, view task details page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Review OAL Appeal Decision" task page
    And I will provide following information before creating task
      | Status           |Complete        |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify "actionTaken" multi select drop down value
      | Final Agency Decision Letter Generated |
      | No Action Taken                  |
    And I will provide following information before creating task
      | Status           |Complete        |
      | actionTaken      |Final Agency Decision Letter Generated,No Action Taken|
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And Close the soft assertions

  @CP-16149 @CP-16149-04 @vidya @crm-regression @ui-wf
  Scenario: Validate action taken field in view task details page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "FngEgFt" and Last Name as "LnIdxSq"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Follow-up on Appeal" task page
    And I will provide following information before creating task
      | status        |Escalated|
      | actionTaken   |IDR Resolved Letter Generated,IDR Unresolved Letter Generated,No Action Taken|
    Then I verify +# link is displayed on screen
    And I mouse over to +# to verify all selected values are displayed
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I get the position of newly created task
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-20720 @CP-20720-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CaseId filed is not present in task list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "MKYra" and Last Name as "Zxczxaa"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    When I expand the first row in task list
    Then I will check CaseId field is not present in TSR tab
    And Close the soft assertions

  @CP-19834 @CP-19834-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate Task info filed accept 1000 charactes
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer with default state for cover-va
    When I click on Create Consumer Button
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Escalation" task page
    And I will provide following information before creating task
      | taskInfo              | maxlength         |
      | status                | Complete          |
      | businessUnitAssigneeTo| EscalationBU      |
      | externalCaseId        | 345265478         |
#      | externalApplicationId | DFRTghy67         |
      | actionTakenSingle     | Escalated to DMAS |
      | contactRecordSingle   | Emergency         |
      | documentDueDate       | today             |
      | cpCRID                ||
      | cpSRID                ||
      | cpTaskID              ||
      | disposition           | Transferred       |
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And Close the soft assertions

  @CP-14664 @CP-14664-04 @CP-14664-02 @ui-wf-nj @nj-regression @ozgen
  Scenario: Display Get Insured Case and ConsumerId on TSR Tab and Task Slider
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I click on initiate contact record
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I navigate to "Returned Mail" task page
    And I will provide following information before creating task
      | taskInfo    | Task Info CP-14664-04|
    And I click on save button in create task page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    When I expand the first row in task list
    Then I verify the task details are displayed when expanded
    Then I click on initiate randomly
    Then Verify task slider is displayed
    And I verify the below details are displayed in task slider
    And I verify GetInsured case and consumer ids in "Task Slider"
    And I will update the following information in task slider
      | status       | Complete  |
      | disposition  | Resolved  |
    And I click on save in Task Slider
    And Close the soft assertions