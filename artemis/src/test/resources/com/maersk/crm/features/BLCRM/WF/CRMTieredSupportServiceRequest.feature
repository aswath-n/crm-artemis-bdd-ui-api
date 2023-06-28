Feature: Tiered Support Service Request

  @CP-47122 @CP-47122-01 @CP-48366-01 @CP-47123 @CP-47123-02 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify Support Level N tasks is created after rerouted from Support Level Nx tasks
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
      | callerName                | Andrew Varid                |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify the "CURRENT ESCALATION TYPE" updated to "<originatingEscalationType>"
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task"
    And I click on id of "<SupportLevelNTask>" in Links section of "SR" page
    And I store task id on edit page
    Then I verify "<SupportLevelNTask>" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | <actionTaken>        |
      | disposition   | Re-route             |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify the "CURRENT ESCALATION TYPE" updated to "<actionTaken>"
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    Then I verify "Tiered Support SR" contains : "<SupportLevelNTask>,<reRoutedTask>" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | SupportLevelNTask | escalationReason | actionTaken      | reRoutedTask    |
      | Level I                   | Support Level 1   | Reason A         | Re-route Level 2 | Support Level 2 |
      | Level II                  | Support Level 2   | Reason B         | Re-route Level 3 | Support Level 3 |
      | Level III                 | Support Level 3   | Reason C         | Re-route Level 1 | Support Level 1 |

  @CP-47124 @CP-47124-01 @CP-47123 @CP-47123-01 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify Support Level N task is created after rerouted from Supervisor Escalation task
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
      | callerName                | Alewa Popovich              |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify the "CURRENT ESCALATION TYPE" updated to "<originatingEscalationType>"
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task"
    And I click on id of "Supervisor Escalation" in Links section of "SR" page
    And I store task id on edit page
    Then I verify "Supervisor Escalation" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | <actionTaken>        |
      | disposition   | Re-route             |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify the "CURRENT ESCALATION TYPE" updated to "<actionTaken>"
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    Then I verify "Tiered Support SR" contains : "Supervisor Escalation,<reRoutedTask>" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | escalationReason | actionTaken      | reRoutedTask    |
      | Supervisor                | Reason A         | Re-route Level 2 | Support Level 2 |
      | Supervisor                | Reason A         | Re-route Level 3 | Support Level 3 |
      | Supervisor                | Reason A         | Re-route Level 1 | Support Level 1 |

  @CP-47124 @CP-47124-02 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify Escalation Resolved Member Notification task is created after rerouted from Supervisor Escalation task
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
      | callerName                | Vasya Petrov                |
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
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Re-route Supervisor  |
      | disposition   | Re-route             |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task"
    And I click on id of "Supervisor Escalation" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                 |
      | reasonForEdit | Corrected Data Entry     |
      | actionTaken   | <actionTaken>            |
      | disposition   | Resolved - Notify Member |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" link section has all the business object linked : "Consumer,Case,Task,Task,Task"
    Then I verify "Tiered Support SR" contains : "<taskType>,Supervisor Escalation,Escalation Resolved Member Notification" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | taskType        | escalationReason | actionTaken         |
      | Level I                   | Support Level 1 | Reason A         | Resolved Reason A   |
      | Level II                  | Support Level 2 | Reason B         | Unresolved Reason B |
      | Level III                 | Support Level 3 | Reason C         | Re-route Level 1    |


  @CP-48366 @CP-48366-02 @crm-regression @ui-wf @kamil
  Scenario: Verify system will display the corresponding escalation reason linked
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
      | taskInfo                  | Test CP-47122 |
      | callerName                | maxlength     |
      | preferredPhone            | 7892357810    |
      | memberName                | consumerName  |
      | originatingEscalationType | Level II      |
      | escalationReason          | Reason B      |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | escalationReason | Reason 2             |
      | reasonForEdit    | Corrected Data Entry |
    And I click on save button on task edit page
    Then I verify "Tiered Support SR" contains : "Support Level 2" on "SR" link section
    And Close the soft assertions