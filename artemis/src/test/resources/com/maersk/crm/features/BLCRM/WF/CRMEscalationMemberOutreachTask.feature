Feature: Validation of Escalation Member Outreach Task

  @CP-47678 @CP-47678-01 @CP-47124 @CP-47124-04 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify Supervisor Escalation task is created after Escalation Member Outreach task is completed
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
      | callerName                | Good Bunny                  |
      | preferredPhone            | 7892357810                  |
      | memberName                | consumerName                |
      | originatingEscalationType | <originatingEscalationType> |
      | escalationReason          | <escalationReason>          |
    And I click on save button in create service request page
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "<taskType>" in Links section of "SR" page
    And I store task id on edit page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Member Outreach      |
      | disposition   | Outreach             |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" contains : "<taskType>,Escalation Member Outreach" on "SR" link section
    And I click on id of "Escalation Member Outreach" in Links section of "SR" page
    Then I verify "Escalation Member Outreach" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Re-route Supervisor  |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" contains : "<taskType>,Escalation Member Outreach,Supervisor Escalation" on "SR" link section
    And Close the soft assertions
    Examples:
      | originatingEscalationType | taskType        | escalationReason | disposition                    |
      | Level I                   | Support Level 1 | Reason A         | Consumer Not Reached - Reroute |
      | Level II                  | Support Level 2 | Reason B         | Consumer Reached - Reroute     |
      | Level III                 | Support Level 3 | Reason C         | Consumer Reached - Reroute     |

  @CP-47678 @CP-47678-02 @CP-47123 @CP-47123-04 @crm-regression @ui-wf @ruslan
  Scenario: Verify Support Level task is created after Escalation Member Outreach task is completed
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
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" contains : "Supervisor Escalation,Escalation Member Outreach" on "SR" link section
    And I click on id of "Escalation Member Outreach" in Links section of "SR" page
    And I click on edit button on view task page
      | status        | Complete                       |
      | reasonForEdit | Corrected Data Entry           |
      | actionTaken   | Re-route Level 1               |
      | disposition   | Consumer Not Reached - Reroute |
    And I click on save button on task edit page
    Then I verify "Escalation Member Outreach" link section has all the business object linked : "Consumer,Case,Service Request"
    And I click on id of "Tiered Support SR" in Links section of "TASK" page
    Then I verify "Tiered Support SR" contains : "Supervisor Escalation,Escalation Member Outreach,Support Level 1" on "SR" link section
    And Close the soft assertions