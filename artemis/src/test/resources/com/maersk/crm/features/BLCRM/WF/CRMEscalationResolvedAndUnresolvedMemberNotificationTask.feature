Feature: Verify Escalation Resolved/Unresolved Member Notification Task

  @CP-47125 @CP-47125-01 @CP-47965-02 @CP-47965 @crm-regression @ui-wf @ruslan
  Scenario Outline: Create Escalation Resolved Member Notification Task when Supervisor Escalation completed with disposition Resolved - Notify Member
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
      | callerName                | Alewa Popovich              |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Supervisor Escalation" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Unresolved Reason A  |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    Then I verify "Tiered Support SR" contains : "Supervisor Escalation,<taskType>" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | escalationReason | disposition                | taskType                                  |
      | Supervisor                | Reason C         | Resolved - Notify Member   | Escalation Resolved Member Notification   |
      | Supervisor                | Reason A         | Unresolved - Notify Member | Escalation Unresolved Member Notification |

  @CP-47125 @CP-47125-02 @crm-regression @ui-wf @ruslan
  Scenario Outline: Create Escalation Resolved Member Notification Task when ‘Support Level - N’ is completed with disposition Resolved - Notify Member
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
      | callerName                | Valeria Angelina            |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                 |
      | reasonForEdit | Corrected Data Entry     |
      | actionTaken   | <actionTaken>            |
      | disposition   | Resolved - Notify Member |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    Then I verify "Tiered Support SR" contains : "<taskType>,Escalation Resolved Member Notification" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | taskType        | escalationReason | actionTaken       |
      | Level I                   | Support Level 1 | Reason A         | Resolved Reason A |
      | Level II                  | Support Level 2 | Reason B         | Resolved Reason B |
      | Level III                 | Support Level 3 | Reason C         | Resolved Reason C |

  @CP-47965 @CP-47965-01 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify Support Level N tasks created and link to case and consumer
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
      | callerName                | Bob Alewkin                 |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "<taskType>" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                   |
      | reasonForEdit | Corrected Data Entry       |
      | actionTaken   | <actionTaken>              |
      | disposition   | Unresolved - Notify Member |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    Then I verify "Tiered Support SR" contains : "<taskType>,Escalation Unresolved Member Notification" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | taskType        | escalationReason | actionTaken      |
      | Level I                   | Support Level 1 | Reason A         | Re-route Level 2 |
      | Level II                  | Support Level 2 | Reason B         | Re-route Level 3 |
      | Level III                 | Support Level 3 | Reason C         | Re-route Level 1 |