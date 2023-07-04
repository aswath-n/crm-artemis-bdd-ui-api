@CreateTask
Feature: Create Task Functionality Check

  @CP-22201 @CP-22201-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Tasks are removed from the Create Task option that are associated to a SR Workflow
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I minimize Genesys popup if populates
    Then I verify following "task" type are not present in 3 dot create task or sr link
      | Complaint Escalation |
      | Fair Hearing         |
      | Missing Information  |
      | Process Application  |
      | Review Appeal        |
      | Review Complaint     |
      | Transfer to LDSS     |
    And I verify following task or sr type are not present in task or sr type drop down on create task or sr page
      | Complaint Escalation |
      | Fair Hearing         |
      | Missing Information  |
      | Process Application  |
      | Review Appeal        |
      | Review Complaint     |
      | Transfer to LDSS     |
    And Close the soft assertions

    # AC 2.0 of CP-21391 is already automated as part CP-19349(Covered in many scripts)
  @CP-21391 @CP-21391-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify Assignee field is disable in create task page for SVC Tester 3 in coverVA project
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Authorized Representative Designation" task page
    Then I verify assignee field is disable in coverVa project
    And Close the soft assertions

  @CP-21391 @CP-21391-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify Assignee field is disable in edit task page for SVC Tester 3 in coverVA project
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    Then I verify assignee field is disable in coverVa project
    And Close the soft assertions

  @CP-22892 @CP-22892-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate Tasks are removed from the Create Task option that are associated to a SR Workflow
    Given I logged into CRM with "Service Account 8" and select a project "CoverVA"
    When I minimize Genesys popup if populates
    Then I verify following "task" type are not present in 3 dot create task or sr link
      | Final Application Review |
      | Process App V1           |
      | Link Correspondence      |
      | Missing Information      |
    And I verify following task or sr type are not present in task or sr type drop down on create task or sr page
      | Final Application Review |
      | Process App V1           |
      | Link Correspondence      |
      | Missing Information      |
    And Close the soft assertions

  @CP-22892 @CP-22892-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate SR are removed from the Create SR option that are associated to a SR Workflow
    Given I logged into CRM with "Service Account 8" and select a project "CoverVA"
    When I minimize Genesys popup if populates
    Then I verify following "SR" type are not present in 3 dot create task or sr link
      | App SR V1        |
      | App Renewal SR V1|
    And I verify following task or sr type are not present in task or sr type drop down on create task or sr page
      | App SR V1        |
      | App Renewal SR V1|
    And Close the soft assertions

  @CP-5190 @CP-5190-03 @CP-13829 @CP-13829-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Calculate Due Date for Calendar day and  Business Days
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "LDSS Communication Form" task page
    Then I verify due date field in create task page for 8 "Business Day"
    And I select "VaCMS System Error" option in task type drop down
    Then I verify due date field in create task page for 2 "Calendar Day"
    And Close the soft assertions

  @CP-22711 @CP-22711-01 @CP-22711-02 @CP-22711-03 @CP-22711-03.1 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Verify Manually Link a Correspondence to a Service Request/Task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Appeal              |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on id of "VACV Appeal" in Links section of "SR" page
    And I should see Create Links button appears in Links component
    Then I click on Create Link button and choose task or sr link
    And I verify user is navigate back to task search page
    And I populate required fields to do task search "207493","","","","","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify the link button is displayed
    Then I click cancel button Request and Task Search
    And I should see Create Links button appears in Links component
    And Close the soft assertions

  @CP-22711 @CP-22711-04 @CP-22711-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario: Verify Manually  SR/Task Link/Unlink to Correspondence
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType  | INBOUND CORRESPONDENCE   |
      | Channel      | Mail                     |
      | documentType | VACV Appeal              |
    When I send the request to create the Inbound Correspondence Save Event
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on id of "VACV Appeal" in Links section of "SR" page
    And I should see Create Links button appears in Links component
    Then I click on Create Link button and choose task or sr link
    And I verify user is navigate back to task search page
    And I will search with srId
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on id of "VACV Appeal" in Links section of "SR" page
    When Wait for 3 seconds
    Then Verify SR and Task linked to correspondence
    And I click the Unlink button on Correspondence
    And I verify Successfully Unlink on Correspondence
    And Close the soft assertions

  @CP-42780 @CP-42780-01 @CP-42774 @CP-42774-02 @CP-48567 @CP-48567-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Disabling Assignee Field for Appeals and PHC-Renewal business units in Create Task Page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Pre-Hearing Conference" task page
    And I click on save button in create task page
    And I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify "businessUnitAssignedTo" single select drop down value
      | Appeals      |
      | PHC Renewal  |
    And I will provide following information before creating task
      | assigneeBusinessUnit | Appeals |
    Then I verify assignee field is disable in coverVa project
    And I will provide following information before creating task
      | assigneeBusinessUnit | PHC Renewal |
    Then I verify assignee field is disable in coverVa project
    And I will provide following information before creating task
      | assigneeBusinessUnit  | Appeals                      |
      | externalApplicationId | random                       |
      | preHearingOutcome     | No Processing Delay          |
      | preHearingReason      | Coverage Type,Coverage Period|
      | caseStatus            | New Application              |
    And I click on save button on task edit page

  @CP-42780 @CP-42780-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Disabling Assignee Field for QA business units
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Outbound Call" task page
    And I will provide following information before creating task
      | assigneeBusinessUnit | QA |
    Then I verify assignee field is disable in coverVa project
    And I will provide following information before creating task
      | assigneeBusinessUnit | CPU PW            |
      | assignee             | Service Account 1 |
    And I will provide following information before creating task
      | assigneeBusinessUnit | QA |
    Then I verify assignee field is disable in coverVa project
    And Close the soft assertions

  @CP-42780 @CP-42780-03 @CP-42774 @CP-42774-03 @CP-48567 @CP-48567-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Disabling Assignee Field for Appeals and PHC-Renewal business units in Edit Task Page
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click taskId column in the task search result
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify "ASSIGNEE BUSINESS UNIT" field is required
    And I verify "businessUnitAssignedTo" single select drop down value
      | Appeals      |
      | PHC Renewal  |
    And I will update the following information in edit task page
      | assigneeBusinessUnit | Appeals |
    Then I verify assignee field is disable in coverVa project
    And I will update the following information in edit task page
      | assigneeBusinessUnit | PHC Renewal |
    Then I verify assignee field is disable in coverVa project
    And Close the soft assertions
    Examples:
      | taskId | taskType               | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Pre-Hearing Conference || Created ||          ||            ||                || true          ||            ||          || today     ||

  @CP-42779 @CP-42779-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario: Verification of mandatory fields on CVIU Communication Form create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU LDSS Communication Form" task page
    And I click on save button on task edit page
    And I verify task mandatory fields error message "VACMS CASE ID "
    And I verify task mandatory fields error message "MMIS MEMBER ID "
    And I verify task mandatory fields error message "FACILITY TYPE "
    And I verify task mandatory fields error message "FACILITY NAME "
    And I verify task mandatory fields error message "LOCALITY "
    And I will provide following information before creating task
      | status | Complete |
    Then I verify new completed date field is displayed with today's date
    And I will provide following information before creating task
      | completedDate | delete  |
    And I click on save button on task edit page
    And I verify task mandatory fields error message "COMPLETED DATE "
    And I will provide following information before creating task
      | completedDate | -3  |
    And I click on save button on task edit page
    And Close the soft assertions
    

