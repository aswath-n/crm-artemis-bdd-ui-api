Feature: Task & Service Request Tab Page

  @CP-142 @CP-142-02 @11150 @CP-11150-01 @CP-15323 @CP-15323-03 @vidya @vinuta @crm-regression @crm-smoke @ui-wf
  Scenario: View Task Information when task is expanded
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "vARYdcZ" and Last Name as "IFkSBbn"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo                 |Test-CP-142 $ allow Special Character &%|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    Then I verify all fields are displayed on Task Summary page
    Then I verify all fields values on Task & Service Request Tab
    When I expand the first row in task list
    Then I verify all headers are displayed in task service request tab
    Then I verify the task details are displayed when expanded
    And I verify initiate button is displayed
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    And I verify the link ids are displayed
    And I verify task id and edit task button are displayed
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And Close the soft assertions

  @CP-5775 @CP-5775-01 @CP-5775 @CP-5775-02 @crm-regression @ui-wf @Basha
  Scenario Outline: verify task is NOT highlighted in red for Cancelled and Complete status in Task List - Case Summary & Consumer Profile
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "AlexandroJaQkL" and Last Name as "LuettgenIeaNu"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify task is not highlighted in red for task status "<taskStatus>"
    And Close the soft assertions
    Examples:
      | taskStatus |
      | Complete   |
      | Cancelled  |

  @CP-1025 @CP-1025-01 @kamil @crm-regression @ui-wf
  Scenario: Verify task should always appear first on top of completed tasks in 'Task &Service' request tab
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "AlexJTSZN" and Last Name as "HaneBeoOC"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then Verify tasks should always appear first on top Completed tasks
    And Close the soft assertions

  @CP-1025 @CP-1025-02 @kamil @crm-regression @ui-wf
  Scenario:Verify Tasks are sorted by Due Date in ascending order, and then by Priority items when Task is not in a Completed or Cancelled status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "John" and Last Name as "Donald"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then Verify Tasks are sorted in ascending order order by Date when Task is not in a Completed or Cancelled status
    And Close the soft assertions

  @CP-1025 @CP-1025-03 @kamil @crm-regression @ui-wf
  Scenario: Verify Tasks are sorted in descending order by Completed or Cancelled Date
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Nancy" and Last Name as "FOREST"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then Verify Tasks are sorted in descending order by Date
    And Close the soft assertions

  @CP-10686 @CP-10686-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Inbound Application Data Entry
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Penelope" and Last Name as "Schwager"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Application Data Entry" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL APPLICATION ID|
      |EXTERNAL CASE ID       |
#    And I will provide following information before creating task
 #     | taskInfo                 ||
 #     | assignee                 ||
 #     | externalApplicationId  ||
 #     | externalCaseId         ||
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

  @CP-10688 @CP-10688-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Process Inbound Document Entry task with only mandatory data
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Process Inbound Document" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
 #   And I will provide following information before creating task
 #     | taskInfo                 ||
 #     | assignee                 ||
 #     | externalCaseId         ||
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

  @CP-10823 @CP-10823-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Returned Mail task with only mandatory data
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Returned Mail" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
  #  And I will provide following information before creating task
  #    | taskInfo                 ||
  #    | assignee                 ||
  #    | externalCaseId         ||
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

  @CP-11413 @CP-11413-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Verification Document Upload task with only mandatory data
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Verification Document Upload" task page
    Then I verify dynamic fields are dispayed
      |EXTERNAL CASE ID       |
  #  And I will provide following information before creating task
  #    | taskInfo                 ||
  #    | assignee                 ||
  #    | externalCaseId         ||
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

  @CP-10691 @CP-10691-01 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Create Task UI in NJ-SBE for Outbound Call task with only mandatory data
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Outbound Call" task page
    Then I verify dynamic fields are dispayed
      |CONTACT REASON       |
      |NAME                 |
      |PREFERRED CALL BACK DATE|
      |PREFERRED CALL BACK TIME|
      |PREFERRED PHONE         |
    And I will provide following information before creating task
   #   | taskInfo                 ||
   #   | assignee                 ||
      | contactReason            |Announcements|
      | name                     |Vidya Mithun|
   #   | preferredCallBackDate    ||
   #   | preferredCallBackTime    ||
      | preferredPhone           |2345678901|
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

  @CP-10852 @CP-10852-01 @CP-13908 @CP-13908-02 @vidya @nj-regression @ui-wf-nj
  Scenario: Validate Disposition field value when select from create page for NJ-SBE project
    Given I logged into CRM with "Service Account 2" and select a project "NJ-SBE"
    And I click on initiate a contact button
    When I searched customer have First Name as "Kylie" and Last Name as "Myers"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I navigate to "Verification Document Upload" task page
    And I will provide following information before creating task
      | Status           |Complete        |
      | disposition      |Unable to Upload|
    And I click on save button in create task page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-10852 @CP-10852-02 @vidya @crm-regression @ui-wf
  Scenario: Validate Disposition field value when select from create page for BaseLine project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Nancy" and Last Name as "Barrow"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to "General Two" task page
    And I will provide following information before creating task
      | Status           |Complete        |
      | disposition      |Consumer not reached|
    And I click on save button in create task page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    Then I verify view page has disposition value
    And Close the soft assertions

  @CP-4345 @CP-4345-04 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is displayed in Task service request tab when user has permission to work and task is in create status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click case consumer search tab
    When I searched customer have First Name as "CathrynCVtAI" and Last Name as "StoltenberKIyjk"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Address" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Address" task with "Created" status does have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-05 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when user don’t have permission to work and task is in create status
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "AlexJTSZN" and Last Name as "HaneBeoOC"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Address" task with "Created" status does not have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-06 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is displayed in Task service request tab when user has permission to work and task is in Onhold status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "JanaeYIfHQ" and Last Name as "CrooksSFxhy"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Plan/Provider" task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I changed status on edit page to "OnHold"
    And I minimize the active call icon
    And I select the assignee as "Service AccountTwo" in my task edit page
    And I select value in "REASON FOR HOLD" drop down
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    Then I Verify "Plan/Provider" task with "OnHold" status does have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-07 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when user don’t have permission to work and task is in onHold status
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "AlexJTSZN" and Last Name as "HaneBeoOC"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Plan/Provider" task with "OnHold" status does not have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-08 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is displayed in Task service request tab when user has permission to escalate and task is in Escalated status
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Jane" and Last Name as "Jordan"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | status                   |Escalated|
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Escalated" status does have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-09 @CP-12576 @CP-12576-01 @crm-regression @ui-wf @vidya @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when user don’t have permission to escalate and task is in Escalated status
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Jane" and Last Name as "Jordan"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | status                   |Escalated|
      | taskInfo                 |Test CP 4345|
    And I click on save button in create task page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Escalated" status does not have Initiate button Task SR tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I changed status on edit page to "Escalated"
    And I minimize the active call icon
    And I select the assignee as "Service AccountTwo" in my task edit page
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Escalated" status does not have Initiate button Task SR tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I changed status on edit page to "Escalated"
    And I select the assignee as "Service AccountOne" in my task edit page
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Escalated" status does not have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-10 @CP-137 @CP-137-03 @CP-11152-01 @CP-5139 @CP-5139-04 @CP-145 @CP-145-02 @CP-11152-02 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is displayed in Task service request tab when status is open and current user is the assignee
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I click on initiate a contact button
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    When I searched customer have First Name as "Kelvin" and Last Name as "Lockman"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Inbound Task" task page
    And I will provide following information before creating task
      | taskInfo        | Test CP 4345        |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And I click on cancel button on task slider
    Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on continue on task details warning window
    Then I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    Then I verify the field values on View Task details
    And I click on save in Task Slider
    Then I Verify user is navigate back to Task and service Request Page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Task" task with "Open" status does have Initiate button Task SR tab
    And Close the soft assertions

  @CP-4345 @CP-4345-11 @crm-regression @ui-wf @Basha
  Scenario: verify initiate button is not displayed in Task service request tab when status is open and current user is not the assignee
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Kelvin" and Last Name as "Lockman"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I Verify "Inbound Task" task with "Open" status does not have Initiate button Task SR tab
    And Close the soft assertions

  @CP-11900 @CP-11900-03 @ui-wf-nj @nj-regression @kamil
  Scenario: Validate "Due In" calculation for Biz days to account for configured project Holidays/TSR
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I click to expand on primary individual
    And I click validate and link button
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Due In is calculated correct in Task Service Requests List
    And Close the soft assertions
