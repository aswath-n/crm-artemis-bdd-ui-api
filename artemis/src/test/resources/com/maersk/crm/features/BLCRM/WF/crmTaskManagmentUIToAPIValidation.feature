@ui-api-wf
Feature: Create/Edit task from UI and check the update in DB by using Get task api

  @CP-16063 @CP-16063-06 @CP-26388 @CP-26388-01 @CP-30299 @CP-30299-03 @vidya @kamil @ruslan @crm-regression @ui-wf
  Scenario Outline: Validate action taken field in create task and view task details page navigating from My task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeal Form" task page
    And I will provide following information before creating task
      | taskInfo      | Test CP 16149|
      | assignee      | Service AccountOne|
      | actionTaken   | Acknowledgement Letter Generated,IDR Resolved Letter Generated,Outbound Call Successful,Outbound Call Unsuccessful|
      | priority      | 5|
    And I click on save button in create task page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiated get task by "getFromUi" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Then I store task history response
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I store task details response
    Then I verify task table record for the task
    And I verify task details and task history details table records has selected action taken values
    And Close the soft assertions
    Examples:
      | taskId | taskType           | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy          | createdOn |contactReason|
      || Review Appeal Form || Created   || 5        ||            ||                || true          ||            || Service AccountOne| Service AccountOne | today     ||

#  refactoring by priyal
  @CP-11619 @CP-11619-2 @Basha @crm-regression @ui-wf
  Scenario Outline: Create NJ Task by getting task types for project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "NJ" task page
    And I will provide following information before creating task
      | taskInfo          | Test 11248|
      | assignee          |Service AccountOne|
      | complaintAbout    |Exchange|
      | name              |Vidya Mithun|
      | externalApplicationId  | Test123|
      | externalCaseId         | caseId123|
      | reason             | Customer Service|
    And I click on save button in create task page
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    When I expend first Task from the list by clicking in Task ID
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiated get task by "getFromUi" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Then I store task history response
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I store task details response
    Then I verify task table record for the task
    And Close the soft assertions
    Examples:
      |projectName|projectId|Task Type|cmplAbt   |exAppId    |exCaseId    |name       |
      ||         |NJ       |Exchange  |ExtAPPID123|ExtCaseID123|Mark taylor|

  @CP-4533 @CP-4533-01 @CP-12427 @CP-12427-01 @CP-12768 @CP-12768-01 @CP-12769 @CP-12769-01 @priyal @vidya @crm-regression @ui-wf
  Scenario: Verify fields order in Plan Provider create task page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the field order of the "Plan/provider" template
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Plan/Provider" task page
    Then I verify fields order in PlanProvider create task page
    And I will provide following information before creating task
      | taskInfo                 |Test CP 12769|
      | assignee                 |Service AccountOne|
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I expend first Task from the list by clicking in Task ID
    Then I verify the below task details are displayed in my task
    Then I verify fields order in PlanProvider View task page
    And I click on edit button on view task page
    Then I verify fields order in PlanProvider create task page
    And I will update the following information in edit task page
      | status                  | Complete|
      | reasonForEdit           |Corrected Data Entry|
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I verify the updated information in view task details page
    Then I verify fields order in PlanProvider View task page
    And Close the soft assertions

  @CP-4528 @CP-4528-01 @CP-12427 @CP-12427-02 @vidya @crm-regression @ui-wf
  Scenario: Verify fields order in General CRM EB create task page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the field order of the "General/CRM/EB" template
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "General/CRM/EB" task page
    Then I verify fields order in General CRM EB create task page
    And Close the soft assertions

  @CP-11890 @CP-11890-02 @CP-12427 @CP-12427-03 @crm-regression @ui-wf @Sean
  Scenario: Verify Field Groups are ordered in Address create task page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the field order of the "Address" template
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "General" task page
    And I select "Address" option in task type drop down
    Then I verify Field Groups are ordered in Address create task page
    And Close the soft assertions

  @CP-4534 @CP-4534-1 @CP-12427 @CP-12427-04 @crm-regression @ui-wf @kamil
  Scenario: Verify fields order in Correspondence create task page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the field order of the "Correspondence" template
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "General" task page
    And I select "Correspondence" option in task type drop down
    Then I verify fields order in Correspondence create task page
    And Close the soft assertions

  @CP-12427 @CP-12427-05 @vidya @crm-regression @ui-wf
  Scenario: Verify fields order in All Fields create task page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the field order of the "All fields1" template
    When I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "All Fields" task page
    Then I verify fields order in All Fields create task page
    And Close the soft assertions

  @CP-4901 @CP-4901-01 @CP-3374 @CP-3374-01 @CP-20585 @CP-20585-01 @crm-regression @ui-wf @ui-sr @vidya
  Scenario: Verify only Active Task type in create task link and task type drop down and listed in alphabetical order
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    And I will get project time zone date
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all "Active" task type which has "Csr" permission to create and edit for project ""
    Then I verify only active task type is displayed in create task link
    And I verify only active task type is displayed in task type drop down in create task page
    And Close the soft assertions

  @CP-3374 @CP-3374-02 @crm-regression @ui-wf @ui-sr @vidya
  Scenario: Verify Future Task types are not present in create task link and task type drop down
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    And I will get project time zone date
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all "Future" task type which has "Csr" permission to create and edit for project ""
    Then I verify Future or InActive task types are not displayed in create task link
    And I verify Future or InActive task types are is displayed in task type drop down in create task page
    And Close the soft assertions

  @CP-3374 @CP-3374-03 @crm-regression @ui-wf @ui-sr @vidya
  Scenario: Verify Inactive Task types are not present in create task link and task type drop down
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I will get project time zone date
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get the all "Inactive" task type which has "Csr" permission to create and edit for project ""
    Then I verify Future or InActive task types are not displayed in create task link
    And I verify Future or InActive task types are is displayed in task type drop down in create task page
    And Close the soft assertions

  # refactor by priyal on 22-10-2021
  @CP-26388 @CP-26388-02 @crm-regression @ui-wf @kamil
  Scenario: Validate Invoke endpoint to retrieve all SR history records
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Service Request" service request page
    Then I verify task type is selected as "General Service Request"
    And I will provide following information before creating task
      | taskInfo | Test CP-26388 |
    And I click on save button in create service request page
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to view SR details page by clicking on sr id
    Then I initiated get task by "getFromUi" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external task
    Then I store task history response
    And I initiated get task by task id "getFromUi"
    And I run get task by task id API
    Then I store task details response
    And I verify SR details and SR history details table records has selected action taken values
    And Close the soft assertions

  @CP-20787 @CP-20787-01 @crm-regression @ui-cc @Beka
  Scenario: Add Consumer Button Displays on task page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Plan/Provider" task page
    When I click on Link Case or Consumer button under LINK section
    And I search consumer has first name as "Goodwill"
    Then I verify Add Consumer button
    And Close the soft assertions

  @CP-20787 @CP-20787-02 @crm-regression @ui-cc @Beka
  Scenario:  Select to Add New Consumer
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Plan/Provider" task page
    And I click on Link Case or Consumer button under LINK section
    And I search consumer has first name as "Goodwill"
    When I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And Close the soft assertions

  @CP-20787 @CP-20787-03 @crm-regression @ui-cc @Beka
  Scenario:  Verify new consumer is linked to the task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to "Plan/Provider" task page
    And I click on Link Case or Consumer button under LINK section
    And I search consumer has first name as "Goodwill"
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    And I click on Create Consumer Button
    Then I verify new consumer is linked to the task
    And Close the soft assertions

  @CP-30293 @CP-30293-01 @ui-wf-nj @nj-regression @priyal
  Scenario Outline:Verify only Active Task type in create task page and Navigation bar and verify edit button is displaying or not
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with specific role "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Supervisor"
    And I minimize Genesys popup if populates
    And I will get project time zone date
    And I will get the all "Active" task type which has "Mailroom Supervisor" permission to create and edit for project "NJ-SBE"
    Then I verify only active task type is displayed in create task link
    And I verify only active task type is displayed in task type drop down in create task page
    And I click on cancel button on create task type screen
    And I click on continue button on create task warning message
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","Incomplete contact Record","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify task id and edit task button are displayed
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","Subsidy Escalation","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify Edit Button is not displayed
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","Appeal","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I verify SR id and edit SR button are displayed
    Then I logOut while Working a Task
    Given I logged into CRM after logged out with "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Specialist"
    And I minimize Genesys popup if populates
    And I will get project time zone date
    And I will get the all "Active" task type which has "Mailroom Specialist" permission to create and edit for project "NJ-SBE"
    Then I verify only active task type is displayed in create task link
    And I verify only active task type is displayed in task type drop down in create task page
    And I click on cancel button on create task type screen
    And I click on continue button on create task warning message
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","Incomplete contact Record","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify Edit Button is not displayed
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","Review Appeals Form","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify task id and edit task button are displayed
    When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","Incomplete contact Record","Appeal","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I verify SR id and edit SR button are displayed
    And Close the soft assertions
    Examples:
      |taskId |taskType |srType|status|statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy |createdOn|contactReason|
      ||         ||      ||        ||          ||              ||             ||          ||        ||         ||

  @CP-30293 @CP-30293-02 @ui-wf-nj @nj-regression @priyal
  Scenario Outline: Verify initiate button is displaying if permission is Escalated
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    Given I logged into CRM with specific role "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Supervisor"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does have Initiate button Task SR tab
    When I navigate to "Work Queue" page
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does have Initiate button
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does have Initiate button Task Search Page
    Then I logOut while Working a Task
    Given I logged into CRM after logged out with "Service Tester 5" and select a project "NJ-SBE" and select a role "Mailroom Specialist"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "Bruce" and Last Name as "Banner"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does not have Initiate button Task SR tab
    When I navigate to "My Task" page
    Then I Verify "Inbound Application Data Entry" task with "Escalated" status does not have Initiate button
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify tasks are does not have initiate button since searched parameter does not have permission to work
    And Close the soft assertions
    Examples:
      |taskId |taskType                      |srType|status     |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy |createdOn|contactReason|
      ||Inbound Application Data Entry||Escalated  ||        ||          ||              ||             ||          ||        ||         ||
