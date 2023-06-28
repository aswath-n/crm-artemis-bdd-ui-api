@CP-testReason
Feature: Validation of Task search Page

  @CP-18447 @CP-18447-03 @CP-25316 @CP-25316-07 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: verify Task Search Criteria stays cleared after logging out/logging ack in
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify Task Notes field is not displayed in Search Results page
    And I click on cancel button on task search page
    And I logout from the app and login back
    And I minimize Genesys popup if populates
    And I navigate to "Task Search" page
    Then I verify task search parameter are cleared
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType         | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy         | createdOn |contactReason|
      | 8097   || Application SR | Open   | today      | 2        | +2      ||        | MMIS           | 1          | true          | Juila      | Lee        | User   | Service TesterTwo | Service TesterTwo | today     ||

  @CP-20585 @CP-20585-05 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: verify task type drop down values are listed in alphabetical order in task search page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    Then I verify task type drop down values are listed in alphabetical order
    And Close the soft assertions

  @CP-20586 @CP-20586-01 @CP-22015 @CP-22015-02 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Verify search case and case id field is not displayed in task search parameter and verify Reason For Cancel Field id displaying on edit page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    Then I verify search case and case id fields are not displayed in search parameter
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify "task-status" single select drop down value
      | Cancelled |
      | Complete  |
    And I will update the following information in edit task page
      | status | <statusVlu> |
    And I click on save button on task edit page
    Then I verify "<fieldName>" drop down is displayed as mandatory field
    And Close the soft assertions
    Examples:
      | taskId | taskType             | srType | status    | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | statusVlu | fieldName         |contactReason|
      || Attend Fair Hearing  || Complete  ||          ||            ||                ||               ||            ||          ||           | Cancelled | REASON FOR CANCEL ||
      || HPE Interim Decision || Cancelled ||          ||            ||                ||               ||            ||          ||           | Complete  | REASON FOR EDIT   ||

  @CP-21829 @CP-21829-01 @CP-21597 @CP-21597-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: verify My work space date in task search parameter as well as search result
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true       |
      | dateOfContact | 06/02/2021 |
    Then I verify dateOfContact is renamed as "MY WORK SPACE DATE"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I verify task search result has "MY WORKSPACE DATE" column
    Then I verify task search result has my work space date which we provide the search parameter
    And Close the soft assertions

  @CP-22730 @CP-22730-04 @CP-33898 @CP-33898-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate CaseId and Application ID are not displayed in task slider when we navigate to Edit Task Page from Task search
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I click on continue button on warning message
    And I select records count in pagination dropdown as "show 50" in "Task Search" page
    Then I verify view pagination "show 50" in "Task Search" page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete |
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value
      | Pending MI |
    And I will update the following information in edit task page
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value
      | Referred |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied/Closed       |
      | Reinstated and Approved |
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Approved             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And Close the soft assertions
    Examples:
      | taskId | taskType            | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Process Application || Created || 1        ||            ||                || false         ||            ||          ||           ||

  @CP-24051 @CP-24051-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Verify escalated check box checked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    When I select escalate Flag checkbox
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "checked"
    And Close the soft assertions
    Examples:
      | userName         | projectName |
      | Service Tester 2 | CoverVA     |

  @CP-24051 @CP-24051-02 @CP-31980 @CP-31980-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Verify escalated check box unchecked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
 #   And I verify save task search section is displayed
    Then Wait for 5 seconds
    And I verify "blue" icon displays on "TASK"
    Then I verify task search result has "escalated" checkbox "unchecked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy         | createdOn | userName         | projectName |contactReason|
      ||          || Created || 5        ||            ||                || true          ||            || Service TesterTwo | Service TesterTwo || Service Tester 2 | CoverVA     ||

  @CP-21019 @CP-21019-01 @CP-26134 @CP-26134-01 @CP-23339 @CP-23339-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: verify Application id in task search parameter as well as search result
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true      |
      | applicationId | TTT123410 |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I verify task search result has "APPLICATION ID" column
    Then I verify task search result has application id which we passed in search parameter
    Then I verify task search result has "expedited" checkbox "unchecked"
    And Close the soft assertions

  @CP-25850 @CP-25850-01 @CP-27055 @CP-27055-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @vidya
  Scenario Outline: verify warning message displayed for fetching 500 records
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click search button on task search page
    Then I validate the system displays an Warning Message: "Search results in excess. The first 500 are returned below. Enter additional search criteria to limit search results"
    Then I verify view pagination "<recordPerPage>" in "Task Search" page
    Then Verify "<numberOfPage>" pages listed in the task search result
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","Created","today","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click search button on task search page
    Then I validate the system does not displays an Warning Message: "Search results in excess. The first 500 are returned below. Enter additional search criteria to limit search results"
    And Close the soft assertions
    Examples:
      | userName         | projectName | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | recordPerPage | numberOfPage |contactReason|
      | Service Tester 2 | CoverVA     ||          ||        ||          ||            ||                || true          ||            | User   ||           || show 20       | 25           ||

  @CP-25850 @CP-25850-01-1 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: verify default number of pagination is show 20
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify view pagination "show 20" in "Task Search" page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          ||        ||          ||            ||                | 2          ||            ||        ||           ||             |

  @CP-25850 @CP-25850-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Verify return to the results page you were on after clicking on task id
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select records count in pagination dropdown as "show 5" in "Task Search" page
    When I navigate to "3" page in search result
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    Then I verify user is navigate back to task search page
    Then I verify user returned to the "3" in search page
    And I verify task ids list retains in search page
    Then I verify view pagination "show 5" in "Task Search" page
    And Close the soft assertions
    Examples:
      | taskId | taskType                                   | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || CPU Eligibility to DMAS Escalation         || Created || 2        ||            ||                ||               ||            ||          ||           ||

  @CP-25850 @CP-25850-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Verify return to the results page you were on after initiating work and cancel from task slider
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select records count in pagination dropdown as "show 10" in "Task Search" page
    When I navigate to "4" page in search result
    When If any In Progress task present then update that to Cancelled
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I click on cancel button on task slider
    Then I verify warning is displayed upon clicking Cancel button on Task slider
    And I click on continue on task details warning window
    Then I verify user returned to the "4" in search page
    Then I verify view pagination "show 10" in "Task Search" page
    And Close the soft assertions
    Examples:
      | taskId | taskType                           | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || CPU Eligibility to DMAS Escalation || Created || 2        ||            ||                ||               ||            ||          ||           ||

  @CP-25850 @CP-25850-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Verify return to the results page you were on after initiating work and save from task slider
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I select records count in pagination dropdown as "show 5" in "Task Search" page
    When I navigate to "2" page in search result
    And I click on initiate button in task search page
    Then Verify task slider is displayed
    And I click on save in Task Slider
    Then I verify task save success message
    Then I verify user returned to the "2" in search page
    Then I verify view pagination "show 5" in "Task Search" page
    And Close the soft assertions
    Examples:
      | taskId | taskType                           | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || CPU Eligibility to DMAS Escalation || Created || 2        ||            ||                ||               ||            ||          ||           ||

  @CP-23744 @CP-23744-01 @CP-23744-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario Outline: Verification of retaining search criteria and results
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    When If any In Progress task present then update that to Cancelled
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    Then I verify search parameters and search results are remain
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will click on back arrow on view task page
    Then I verify search parameters and search results are remain
    And In search result click on task id to navigate to view page
    Then I navigate to "Task Search" from task view page
    Then I verify search parameters and search results are remain
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I navigate to "Task Search" from task view page
    Then I verify search parameters and search results are remain
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status          | Cancel |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify search parameters and search results are remain
    Then I navigate to "My Task" from task view page
    When Wait for 3 seconds
    Then I navigate to "Task Search" from task view page
    Then I verify search parameters and search results are cleared out
    And Close the soft assertions
    Examples:
      | taskId | taskType               | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || DMAS to CPU Escalation || Created ||          ||            ||                ||               ||            ||          ||           ||

  @CP-28286 @CP-28286-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Verification of Specific Page of Task Search Results for Invalid Number in CoverVA Project
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I click on continue button on warning message
    Then I verify Go to page section is displayed
    And I provide number "9000" on the go to page section
    Then I verify that error message for invalid page number is getting displayed
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          || Created ||          ||            ||                ||               ||            ||          ||           ||

  @CP-28286 @CP-28286-04  @CP-31979 @CP-31979-04 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ozgen
  Scenario Outline: Verification of Specific Page of Task Search Results in CoverVA Project and verify Task search page ui
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | taskTypes | Appeal Remand,Appeals to DMAS Escalation                 |
      | srTypes   | App Renewal SR V1,Appeals SR,Application SR,Complaint SR |
    And I click on search button on task search page
    And I click on continue button on warning message
    Then I Verify "Task Type" multi select DD value in search page "Appeal Remand, Appeals to DMAS Escalation"
    And I Verify "SR Type" multi select DD value in search page "App Renewal SR V1, Appeals SR, Application SR, Complaint SR"
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click on continue button on warning message
    And I verify save task search section is displayed
    Then I verify Go to page section is displayed
    And I provide number "3" on the go to page section
    Then I verify that expected page number "3" results are displayed
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          || Created ||          ||            ||                ||               ||            ||          ||           ||

  @CP-23339 @CP-23339-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Verify Expedited check box checked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true      |
      | applicationId | T22239198 |
      | expedited     | true      |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "expedited" checkbox "checked"
    And Close the soft assertions
    Examples:
      | userName         | projectName |
      | Service Tester 2 | CoverVA     |

  @CP-29290 @CP-29290-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Verify CaseID and ConsumerID in task search result which we link with SR in CoverVA Tenant
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | applicationType       | MAGI Standard Application - CVIU |
      | externalApplicationId | random                           |
      | myWorkSpaceDate       | -2                               |
      | channel               | CommonHelp                       |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | consumerId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in ascending order in Task Search
    Then I verify following information in task search result
      | taskType     | Application SR    |
      | consumerIdVA | getFromCreatePage |
    And I click on cancel button on task search page
    And I will search with srId
    Then I verify following information in task search result
      | taskType     | Application SR    |
      | consumerIdVA | getFromCreatePage |
    And Close the soft assertions

  @CP-29078 @CP-29078-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify no record returned in task search result
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
      | status           | Open       |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify No Records Available message is displayed
    And Close the soft assertions

  @CP-22730 @CP-22730-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate CaseId and Application ID are not displayed in task slider when we initiate from Task search
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete |
      | actionTakenSingle | Sent NOA |
    And I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Denied        |
    And I will update the following information in edit task page
      | actionTakenSingle | Sent VCL |
    And I verify "disposition" single select drop down value
      | Pending MI |
    And I will update the following information in edit task page
      | actionTakenSingle | Transferred to LDSS |
    And I verify "disposition" single select drop down value
      | Referred |
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Process App V1 || Created || 3        ||            ||                || false         ||            ||          ||           ||

  @CP-23400 @CP-23400-03 @CP-31978 @CP-31978-04 @priyal @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario Outline: Verify re-factor Due Date for Task Search in UI request and verify Task Management DD in left side menu
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    Then I verify Task Management panel and it's DD value
      | MY TASK / WORK QUEUE |
      | TASK/SR SEARCH       |
      | MANAGE QUEUE FILTER  |
      | SAVED TASK SEARCH    |
    When I navigate to "Task Search" page
    When I select "<sign>" sign for due date in task search
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I click on continue button on warning message
    And I verify save task search section is displayed
    Then Validate due date column contains "<dueDate>" values "<sign>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | sign | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | userName         | projectName |contactReason|
      ||          || Open   ||          | >    | today   ||        ||            ||            ||        ||           || Service Tester 2 | CoverVA     ||

  @CP-24243 @CP-24243-01 @CP-31980 @CP-31980-07 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Systamatically created Verification Document for Application SR and link to business object consumer
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application Renewal"
    When I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I verify "orange" icon displays on "SR"
    And In search result click on task id to navigate to view page
    And I will click on back arrow on view task page
    Then I verify Link button is displayed in task search page
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType         | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | Application SR | Closed | 07/01/2022 ||         ||        ||            | false         ||            ||          ||           ||


  @CP-39860 @CP-39860-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario Outline: Verify search Contact Reason meet the specified criteria under the Search Results Page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And Verify Search results display Task with related Contact Reasons "<contactReason>"
    And Close the soft assertions
    Examples:
      | taskId | taskType                   | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason        |
      || Appeals to DMAS Escalation ||        ||          ||            ||                || true          ||            ||          ||           | Delayed Fair Hearing |

  @CP-39860 @CP-39860-01-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario Outline: Verify Search Results Display Page Column Names
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I click on continue button on warning message
    And I verify task search results are displayed
    And Verify Search Results Display Page Column Names
      | ID                   |
      | TYPE                 |
      | PRIORITY             |
      | STATUS               |
      | STATUS DATE          |
      | DUE DATE             |
      | MY WORKSPACE DATE    |
      | DUE IN               |
      | INTERNAL CONSUMER ID |
      | CONTACT REASON       |
      | APPLICATION ID       |
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Appeal Remand ||        ||          ||            ||                || true          ||            ||          ||           ||


  @CP-39860 @CP-39860-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil
  Scenario Outline: Verify Task/SR’s which do NOT have a Contact Reason field and/or field is null
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I click on continue button on warning message
    And I verify task search results are displayed
    And Verify Search results display Task with related Contact Reasons "-- --"
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Appeal Remand ||        ||          ||            ||                || true          ||            ||          ||           ||
