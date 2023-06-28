@AutoAssignmentSREvents
Feature: Validation of Auto Assignment SR Events

  @CP-34266 @CP-34266-02 @priyal @events-wf
  Scenario Outline: Validate in TASK_SAVE_EVENT for AA SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Auto Assignment SR" service request page
    And I will provide following information before creating task
      | priority   | 3|
    And I click on save button in create service request page
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify TASK_SAVE_EVENT for SR has all the proper data
    And I verify task details information in task save event for coverVA Task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | taskId | taskType | srType             | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn | eventName       | correlationId |contactReason|
      ||          | Auto Assignment SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     | TASK_SAVE_EVENT | Open          ||

  @CP-34268-02 @CP-34270 @CP-34270-02 @CP-34271 @CP-34271-02 @CP-34272 @CP-34272-02 @CP-34578 @CP-34578-02 @moldir @events-wf
  Scenario Outline: Validate in TASK_UPDATE_EVENT for AA SR
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Auto Assignment SR" service request page
    And I will provide following information before creating task
      | priority   | 3|
    And I click on save button in create service request page
    And Wait for 250 seconds
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page OR view SR page
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | <disposition>        |
    And I click on save button on task edit page
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_UPDATE_EVENT for TASK has all the proper data
    And I will check "TASK_UPDATE_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "TASK_UPDATE_EVENT" publish to DPBI
    Examples:
      | taskId | taskType | srType             | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy          | createdOn | correlationId | disposition        |contactReason|
      ||          | Auto Assignment SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     | Task_update   | Selection Made     ||
      ||          | Auto Assignment SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     | Task_update   | Selection Accepted ||
      ||          | Auto Assignment SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     | Task_update   | Loss of Eligibility||
      ||          | Auto Assignment SR | Open   || 3        ||            ||                || true          ||            ||          | Service AccountOne | today     | Task_update   | Selection Not Made ||
