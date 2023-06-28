Feature: Validation of Task search Page Second

  @CP-12179 @CP-12179-01 @crm-regression @ui-wf @ozgen @tasksearch
  Scenario Outline: Verification of Task Search using External Case IDs and Internal Case ID on BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    Then I search with  Search Case "<SearchCase>" and Case ID "<CaseId>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to "<TaskType>" task page
    And I will provide following information before creating task
      | taskInfo | Task search using external caseId field |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | searchCase | <SearchCase> |
      | caseId     | <CaseId>     |
    And I click on search button on task search page
  #  And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    Then I verify search result type is "<TaskType>" and CaseId is "<CaseId>"
    And I verify that displayed taskId matches with expected taskId
    And Close the soft assertions
    Examples:
      | SearchCase | CaseId       | TaskType       |
      | Medicaid   | 320633083447 | General        |
      | CHIP       | 11903025     | Correspondence |
      | Internal   | 124168       | Plan/Provider  |

  @CP-12179 @CP-12179-02 @crm-regression @ui-wf @ozgen @tasksearch
  Scenario Outline: Verification of Task Search using External Consumer IDs and Internal Consumer ID on BLCRM Project
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    And I search Consumer Type "<SearchConsumer>" and Consumer ID "<ConsumerId>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to "<TaskType>" task page
    And I will provide following information before creating task
      | taskInfo | Task search using external consumerId field |
    And I click on save button in create task page
#    Then I verify Success message is displayed for task
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | searchConsumer | <SearchConsumer> |
      | consumerId     | <ConsumerId>     |
    And I click on search button on task search page
    And I will search for newly created task on Task Search
 #   And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    Then I verify search result type is "<TaskType>" and ConsumerId is "<ConsumerId>"
    And I verify that displayed taskId matches with expected taskId
    And Close the soft assertions
    Examples:
      | SearchConsumer | ConsumerId   | TaskType       |
      | Medicaid       | 36184136test | General        |
      | CHIP           | 56140192     | Correspondence |
      | Internal       | 218248       | Plan/Provider  |

  @CP-24051 @CP-24051-01 @CP-31979 @CP-31979-01 @priyal @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline: Verify escalated check box checked in task search result and verify Task search page ui
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | taskTypes | Calendar Day,General Three             |
      | srTypes   | General Service Request,General Two SR |
    And I click on search button on task search page
    Then I click on continue button on warning message
    Then I Verify "Task Type" multi select DD value in search page "Calendar Day, General Three"
    And I Verify "SR Type" multi select DD value in search page "General Service Request, General Two SR"
    And I click on cancel button on task search page
    When I select escalate Flag checkbox
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "checked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy  | createdOn |contactReason|
      ||          ||         | today      ||         ||        ||            ||            ||        ||            ||             |

  @CP-24051 @CP-24051-02 @crm-regression @ui-wf @vidya @tasksearch
  Scenario Outline: Verify escalated check box unchecked in task search result
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has "escalated" checkbox "unchecked"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee           | createdBy          | createdOn | userName          | projectName |contactReason|
      ||          || Created || 3        ||            ||                || true          ||            || Service AccountOne | Service AccountOne || Service Account 1 | BLCRM       ||

  @CP-25850 @CP-25850-01 @CP-27055 @CP-27055-01 @crm-regression @ui-wf @ruslan @vidya @tasksearch
  Scenario Outline: verify warning message displayed for fetching 500 records
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
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
      | userName          | projectName | taskId | taskType | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | recordPerPage | numberOfPage |contactReason|
      | Service Account 1 | BLCRM       ||          ||        ||          ||            ||                || true          ||            | User   ||           || show 5        | 100          ||

  @CP-23400 @CP-23400-02 @CP-31980 @CP-31980-01 @CP-31987 @CP-31987-03 @priyal @crm-regression @crm-regression @ui-wf @ruslan @tasksearch
  Scenario Outline: Verify re-factor Due Date for Task Search in UI request and verify Task Management Dd in left side menu
    Given I logged into CRM with "<userName>" and select a project "<projectName>"
    And I minimize Genesys popup if populates
 #   Then I verify Task Management panel and it's DD value
 #     | MY TASK / WORK QUEUE |
 #     | TASK/SR SEARCH       |
 #     | MANAGE QUEUE FILTER  |
 #     | SAVED TASK SEARCH    |
 #   And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    When I select "<sign>" sign for due date in task search
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
 #   And I verify save task search section is displayed
    And I verify "blue" icon displays on "TASK"
    Then Validate due date column contains "<dueDate>" values "<sign>"
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType | status  | statusDate | Priority | sign | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | userName          | projectName |contactReason|
      ||          || Created ||          | =    | +3      ||        ||            ||            ||        ||           || Service Account 1 | BLCRM       ||


  @CP-28313 @CP-28313-01 @CP-28313-02 @crm-regression @ui-wf @kamil @tasksearch
  Scenario: Validate Task search functionality for consumers added to case
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo | 28313              |
      | assignee | Service AccountOne |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    Then I see radio button is associated with a Case or Consumer
    When I select Consumer radio button
    Then I see Link Record Consumer button get displayed
    When I click on Link Record Consumer button
    Then I verify task is linked with ConsumerID and CaseID
    And I click on save button in create task page
  #  Then I verify Success message is displayed for task
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    Then I click on edit button on view task page OR view SR page
    Then I verify task is linked with ConsumerID and CaseID
    Then I select "Unlink Case/Consumer" in the create link section
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "Mikel" and Last Name as "Stamm"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    And I select 'Link to Case Only' checkbox
    Then I see Link Record Case button get displayed
    When I click on Link Case button
    Then I verify task is linked with Case ID
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    And Verify only one CaseId is linked to task
    And Close the soft assertions

  @CP-939 @CP-939-01 @CP-31978 @CP-31978-03 @priyal @crm-regression @ui-wf @ruslan @tasksearch
  Scenario: Verify associated task to BU & Teams is fetched when I search by BU assignee & Teams assignee
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
 #   Then I verify Task Management panel and it's DD value
 #     | MY TASK / WORK QUEUE |
 #     | TASK/SR SEARCH       |
 #     | MANAGE QUEUE FILTER  |
 #     | SAVED TASK SEARCH    |
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    Then I verify search fields in Task SR Types on Search page
    And I click on cancel button on task search page
    And I perform a Task Search by following fields
      | advanceSearch        | true                           |
      | assigneeBusinessUnit | For Regression Do no update it |
    And I click on search button on task search page
  #  And I verify save task search section is displayed
    Then I verify "Fraud Form" task types displays on search result
    And I click on cancel button on task search page
    And I perform a Task Search by following fields
      | advanceSearch | true                                |
      | assigneeTeam  | For Regression Team Do no update it |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify "Fraud Form" task types displays on search result
    And Close the soft assertions

  @CP-828 @CP-828-04 @crm-regression @ui-wf @kamil @tasksearch
  Scenario: Verify I will NOT be able to perform a Task Search
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
  #  When I navigate to "Task Search" page
    And I click search button on task search page
    Then Verify I will NOT be able to perform a Task Search
    And Close the soft assertions

  @CP-35669 @CP-35669-01 @crm-regression @ui-wf @ozgen @tasksearch
  Scenario Outline:Highlight Selected Search Results Item When Navigating Back to Task Search Results
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I click on 3 task on search results
    And I  click on the BACK button
    And I verify that I am back to task search page and previously selected 3 row is highlighted
    And Close the soft assertions
    Examples:
      | taskId | taskType       | srType | status  | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn | contactReason|
      || General/CRM/EB || Created ||  5       ||            ||                ||               ||            ||          ||           ||