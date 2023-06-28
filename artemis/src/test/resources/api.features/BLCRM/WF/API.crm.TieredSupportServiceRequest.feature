Feature: Validation of Tiered Support Service Request Events

  @CP-47122 @CP-47122-02 @CP-47123 @CP-47123-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify TASK_SAVE_EVENT AND LINK_EVENT for Support Level N tasks
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | callerName                | Andrey Kolchugan |
      | preferredPhone            | 7892357810       |
      | memberName                | consumerName     |
      | originatingEscalationType | Level I          |
      | escalationReason          | Reason A         |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "Open"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I verify "LINK_EVENT" generated for "Support Level 1" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST,CASE TO SERVICE_REQUEST,SERVICE_REQUEST TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And Close the soft assertions

  @CP-47124 @CP-47124-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify TASK_SAVE_EVENT AND LINK_EVENT for Supervisor Escalation task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
#    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | callerName                | Alewa Popovich |
      | preferredPhone            | 7892357810     |
      | memberName                | consumerName   |
      | originatingEscalationType | Supervisor     |
      | escalationReason          | Reason A       |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I store task id on edit page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "Open"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I verify "LINK_EVENT" generated for "Supervisor Escalation" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST,CASE TO SERVICE_REQUEST,SERVICE_REQUEST TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And Close the soft assertions

  @CP-47965 @CP-47965-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify TASK_SAVE_EVENT AND LINK_EVENT for Escalation Unresolved Member Notification Task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
#    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | callerName                | James Bond   |
      | preferredPhone            | 7892357810   |
      | memberName                | consumerName |
      | originatingEscalationType | Level I      |
      | escalationReason          | Reason A     |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Support Level 1" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                   |
      | reasonForEdit | Corrected Data Entry       |
      | actionTaken   | Re-route Level 2           |
      | disposition   | Unresolved - Notify Member |
    And I click on save button on task edit page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "Open"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I verify "LINK_EVENT" generated for "Escalation Unresolved Member Notification" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST,CASE TO SERVICE_REQUEST,SERVICE_REQUEST TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And Close the soft assertions

  @CP-47125 @CP-47125-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify TASK_SAVE_EVENT AND LINK_EVENT for Escalation Resolved Member Notification Task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | callerName                | Alewa Popovich |
      | preferredPhone            | 7892357810     |
      | memberName                | consumerName   |
      | originatingEscalationType | Supervisor     |
      | escalationReason          | Reason B       |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                 |
      | reasonForEdit | Corrected Data Entry     |
      | actionTaken   | Unresolved Reason A      |
      | disposition   | Resolved - Notify Member |
    And I click on save button on task edit page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "Open"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I verify "LINK_EVENT" generated for "Escalation Resolved Member Notification" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST,CASE TO SERVICE_REQUEST,SERVICE_REQUEST TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And Close the soft assertions

  @CP-47678 @CP-47678-03 @crm-regression @ui-wf @ruslan
  Scenario: Verify TASK_SAVE_EVENT AND LINK_EVENT for Escalation Member Outreach Task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create CaseConsumer
    And I initiated Link API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
#    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Tiered Support SR" service request page
    And I will provide following information before creating task
      | callerName                | Alewa Popovich |
      | preferredPhone            | 7892357810     |
      | memberName                | consumerName   |
      | originatingEscalationType | Supervisor     |
      | escalationReason          | Reason A       |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Supervisor Escalation" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Member Outreach      |
      | disposition   | Outreach             |
    And I click on save button on task edit page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "Open"
    And I will run the Event GET API and get the payload
    Then I verify TASK_SAVE_EVENT for Task has all the proper data
    And I will check "TASK_SAVE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_SAVE_EVENT" publish to DPBI
    And I verify "LINK_EVENT" generated for "Escalation Member Outreach" task : "TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK,SERVICE_REQUEST TO CONSUMER,CONSUMER TO SERVICE_REQUEST,CASE TO SERVICE_REQUEST,SERVICE_REQUEST TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO CONSUMER,CONSUMER TO TASK,CASE TO TASK,TASK TO CASE,TASK TO SERVICE_REQUEST,SERVICE_REQUEST TO TASK"
    And I will check "LINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "LINK_EVENT" publish to DPBI
    And Close the soft assertions