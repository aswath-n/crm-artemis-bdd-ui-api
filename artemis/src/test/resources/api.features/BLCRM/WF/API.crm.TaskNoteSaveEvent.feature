Feature: Validation of task note save event

  @CP-18507 @CP-18507-01 @moldir @events-wf
  Scenario Outline: Add Notes Component in view task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "Work Queue" page
    And I expend first Task from the list by clicking in Task ID
    And I will provide information to task or SR note component
      | noteValue | String|
    And click on save button present in task or SR note component
    Then I verify Success message is displayed on note component
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName           | correlationId |
      |TASK_NOTE_SAVE_EVENT| taskId        |

  @CP-18507 @CP-18507-02 @moldir @events-wf
  Scenario Outline: Add Notes Component in edit task deatils page
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    When I navigate to "My Task" page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName           | correlationId |
      |TASK_NOTE_SAVE_EVENT| taskId        |

  @CP-18507 @CP-18507-03 @moldir @events-wf
  Scenario Outline: Add Notes Component in edit task details page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I have a request to create External task with the following values
      | taskTypeId | 12976 |
      | createdBy  | 8369  |
    Then  I send the request for the External task endpoint
    Then I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with srId
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName           | correlationId |
      |TASK_NOTE_SAVE_EVENT| taskId        |

  @CP-18507 @CP-18507-04 @moldir @events-wf
  Scenario Outline: Validate we can add not to closed General SR in edit page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    And I click on edit service request button
    And I will provide information to task or SR note component
      | noteValue | Line Break|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |taskId |taskType|srType                 |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy         |createdOn|eventName           | correlationId |contactReason|
      ||        |General Service Request|Closed ||        ||          ||              ||true         ||          ||        |Service AccountOne||TASK_NOTE_SAVE_EVENT| taskId        ||

  @CP-18507 @CP-18507-05 @moldir @events-wf
  Scenario Outline: Add Notes component in edit SR deatils page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "My Task" page
    And I click task id to get the results in descending order
    And I expend first Task from the list by clicking in Task ID
    And I will provide information to task or SR note component
      | noteValue | Test@123|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |eventName           | correlationId |
      |TASK_NOTE_SAVE_EVENT| taskId        |

  @CP-18507 @CP-18507-06 @moldir @events-wf
  Scenario Outline: Validate we can add not to closed Application SR
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And In search result click on task id to navigate to view page
    And I will provide information to task or SR note component
      | noteValue | String + Number|
    And click on save button present in task or SR note component
    #Then I verify Success message is displayed on note component
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    Then I verify TASK_NOTE_SAVE_EVENT for task or SR
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      |taskId |taskType|srType        |status |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy        |createdOn|eventName           | correlationId |contactReason|
      ||        |Application SR|Closed ||        ||          ||              ||true         ||          ||        |Service TesterTwo||TASK_NOTE_SAVE_EVENT| taskId        ||
