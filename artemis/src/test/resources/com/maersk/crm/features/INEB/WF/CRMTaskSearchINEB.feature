Feature: Validation of Task search Page

  @CP-29290 @CP-29290-04 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verify CaseID and ConsumerID in task search result which we link with SR in IN-EB Tenant
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Customer Service Complaint" service request page
    And I will provide following information before creating task
      | memberName     | Vidya Mithun     |
      | preferredPhone | 1234567890       |
      | complaintAbout | maersk          |
      | complaintType  | Customer Service |
      | incidentDate   | today            |
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | searchCase     | Internal          |
      | searchConsumer | Internal          |
      | caseId         | getFromCreatePage |
      | consumerId     | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in ascending order in Task Search
    Then I verify following information in task search result
      | taskType   | Customer Service Complaint |
      | caseID     | getFromCreatePage          |
      | consumerID | getFromCreatePage          |
    And I click on cancel button on task search page
    And I will search with srId
    Then I verify following information in task search result
      | taskType   | Customer Service Complaint |
      | caseID     | getFromExnlID              |
      | consumerID | getFromExnlID              |
    And Close the soft assertions

  @CP-29078 @CP-29078-01 @crm-regression @ui-wf-ineb @vidya
  Scenario: Verify only task which are not assigned is returned in task search result
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned    |
      | taskTypes        | Outbound Call |
      | status           | Created       |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result not returned any SR and has task with assignee filed "-- --"
    And Close the soft assertions

  @CP-28985 @CP-28985-02 @crm-regression @ui-wf-ineb @kamil
  Scenario Outline: Verification External case ID or consumer ID linked should be displayed
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I search with  Search Case "<searchCase>" and Case ID "<caseId>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to "Rejected Selection" task page
    And I will provide following information before creating task
      | taskInfo    | random        |
      | status      | Complete      |
      | disposition | Contact State |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify following information in task search result
      | taskType | Rejected Selection |
      | caseID   | <caseId>           |
    And Close the soft assertions
    Examples:
      | taskId | taskType           | srType | status   | statusDate | Priority | dueDate | searchCase | caseId  | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      || Rejected Selection || Complete | today      ||         | Internal   | 10      ||            ||            ||        ||           ||               |
      || Rejected Selection || Complete | today      ||         | State      | 425wffs ||            ||            ||        ||           ||               |

  @CP-25076 @CP-25076-01 @crm-regression @ui-wf-ineb @ruslan
  Scenario: Validate State case id and Medicaid consumer id DD values
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    Then I verify "searchCase" single select drop down value in task search page
      | Internal |
      | State    |
    Then I verify "searchConsumer" single select drop down value in task search page
      | Internal     |
      | MEDICAID/RID |
    Then I verify task search field "SEARCH CASE" has default value "State"
    Then I verify task search field "SEARCH CONSUMER" has default value "MEDICAID/RID"
    And Close the soft assertions

  @CP-25076 @CP-25076-02 @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Add External Case ID + Consumer ID values in Task Search
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And I validate column "State CASE ID" is displayed on the task search result
    And I validate column "Medicaid CONSUMER ID" is displayed on the task search result
    Then I verify following information in task search result
      | caseID     | x50406317920 |
      | consumerID | x830643      |
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId       | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | expectedCaseId | expectedConsumerId | contactReason |
      ||          ||        ||          ||            | x50406317920 ||            ||            ||        ||           || x50406317920   | x830643            ||
      ||          ||        ||          ||            ||                | x830643    ||            ||        ||           || x50406317920   | x830643            ||

  @CP-28033 @CP-28033-01 @CP-28033-02 @CP-30299 @CP-30299-06 @crm-regression @ui-wf-ineb @kamil @ruslan
  Scenario:  Validate CSR Name field request object passes the user’s userId for this tenant
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Customer Service Complaint" service request page
    Then I verify CSR Name field is an autocomplete dropdown of all Active users
    And I will provide following information before creating task
      | memberName            | Kamil             |
      | preferredPhone        | 1234567890        |
      | complaintAbout        | maersk           |
      | complaintType         | Customer Service  |
      | incidentDate          | today             |
      | preferredCallBackTime | 04:30 PM          |
      | contactName           | Test              |
      | csrName               | Service TesterTwo |
    And I click on save button in create service request page
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I expend first SrId from the list by clicking in Task ID
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get task by task id "getSRIDFromUi"
    And I run get task by task id API
    And I verify request object passes csr name and userId for the SR
    Then I verify task table record for the task

  @CP-21344 @CP-21344-03 @priyal @crm-regression @ui-wf-ineb
  Scenario Outline:  verify Due in Days when SR in close status
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify due in days after close status for SR
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I verify due in days after close status for SR
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType                     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason |
      ||          | Customer Service Complaint | Closed ||          ||            ||                || true          ||            ||          || today     ||

  @CP-24051 @CP-24051-04 @vidya @crm-regression @ui-wf-ineb
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
      | Service Tester 2 | IN-EB       |

  @CP-24051 @CP-24051-02 @crm-regression @ui-wf-ineb @vidya
  Scenario Outline: Verify escalated check box unchecked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "unchecked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy         | createdOn | userName         | projectName | contactReason |
      ||          || Created || 3        ||            ||                || true          ||            || Service TesterTwo | Service TesterTwo | today     | Service Tester 2 | IN-EB       ||

  @CP-25850 @CP-25850-01 @CP-27055 @CP-27055-01 @crm-regression @ui-wf-ineb @ruslan @vidya
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
      | userName         | projectName | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | recordPerPage | numberOfPage | contactReason |
      | Service Tester 2 | IN-EB       ||          ||        ||          ||            ||                || true          ||            | User   ||           || show 5        | 100          ||

  @CP-23400 @CP-23400-04 @CP-31980 @CP-31980-04 @CP-31978 @CP-31978-01 @priyal @crm-regression @ui-wf-ineb @ruslan
  Scenario Outline: Verify re-factor Due Date for Task Search in UI request and verify Task Management DD on left side menu
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
    And I verify save task search section is displayed
    And I verify "blue" icon displays on "TASK"
    Then Validate due date column contains "<dueDate>" values "<sign>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | sign | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | userName         | projectName | contactReason |
      ||          || Created ||          | >    | today   ||        ||            | true          ||            ||          || today     | Service Tester 2 | IN-EB       ||

  @CP-25314 @CP-25314-01 @CP-31979 @CP-31979-03 @CP-31980 @CP-31980-08 @priyal @crm-regression @ui-wf-ineb @chandrakumar
  Scenario Outline: Validate Date of Contact field is displayed or not in advanced Task Search and verify Task search page ui
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | srTypes | Customer Service Complaint,Just Cause |
    And I click on search button on task search page
    And I verify "orange" icon displays on "SR"
    And I Verify "SR Type" multi select DD value in search page "Customer Service Complaint, Just Cause"
    And I click on cancel button on task search page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify dateOfContact field is removed in search page
    Then I verify dateOfContact column is removed in search result
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee          | createdBy | createdOn | expectedCaseId | expectedConsumerId | contactReason |
      ||          ||        ||          ||            ||                || true          ||            | User   | Service TesterTwo || today     ||                    ||


