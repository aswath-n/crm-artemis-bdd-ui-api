Feature: Validation of Task search Page

  @CP-831 @CP-831-01 @CP-28286 @CP-28286-01 @CP-25316 @CP-25316-06 @CP-828 @CP-828-01 @vidya @crm-regression @ui-wf @ozgen @tasksearch
  Scenario Outline: Verify Save Task Search and Go To Page Sections are displayed when we search anything
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
 #   And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify save task search section is displayed
    And I click on continue button on warning message
    Then I verify Task Notes field is not displayed in Search Results page
    Then I verify Go to page section is displayed
    And I provide number "3" on the go to page section
    Then I verify that expected page number "3" results are displayed
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |       | General|      |         |          |        |       |          |      |              |          |true         |          |          |      |        |         | today   |             |

  @CP-831 @CP-831-02 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Search Name fields
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
 #   And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify search name accepts 50 alphanumeric spaces are allowed
    And Close the soft assertions
    Examples:
      |taskId|taskType|srType|status|statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      |      | General|      |      |          | 2      |       |          |      |              |          |true         |          |          |      |        |         | today   |             |

  @CP-831 @CP-831-03 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I enter value to search name field
    And I click on save button on task search page
    Then I verify success message and text are displayed task search page
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-831 @CP-831-04 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Verify Warning message is displayed
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
 #   And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I enter value to search name field
    And I click on cancel button on saved task search section
    Then I verify Warning message and text are displayed on task search page
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-831 @CP-831-05 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Verify functionality of Cancel button present on Warning message
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I enter value to search name field
    And I click on cancel button on saved task search section
    And I click on cancel button on warning message of task search page
    Then Verify will I remain on the same page,Task Search Name & Task Search Parameters Entered will not be cleared
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-831 @CP-831-06 @CP-828 @CP-828-03 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Verify functionality of Continue button present on Warning message
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I enter value to search name field
    And I click on cancel button on saved task search section
    And I click on continue button on warning message of task search page
    Then Verify will I remain on the same page,Task Search Name is cleared & Task Search Parameters Entered will not be cleared
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-831 @CP-831-07 @vidya@crm-regression @ui-wf @tasksearch
  Scenario Outline:Verify Save Task Search section is not displayed for any role other than Supervisor
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    Then I verify save task search section is not displayed
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-829 @CP-829-01 @CP-829-02 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
    When I navigate to "Saved Task Search" page
 #   When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    Then I verify Search Parameters will auto populated "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And I click on search button on task search page
    Then I verify save task search section is displayed
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         ||

  @CP-829 @CP-829-03 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters when clicked on cancel button
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
  #  When I navigate to "Saved Task Search" page
  #  When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    Then I verify Search Parameters will auto populated "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    When I update the Search parameter "<updateType>","<updateStatus>","<updatePriority>"
    And I click on search button on task search page
    And I click on cancel button on warning message
    Then I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    Then I verify Search Parameters will auto populated "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>"
    And Close the soft assertions
    Examples:
      |taskId |taskType    |srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|updateType  |updateStatus|updatePriority|contactReason|
      ||Calendar Day||         || 4      ||          ||              ||false        ||          ||        ||         |Inbound Task|Escalated   |1             ||

  @CP-829 @CP-829-04 @vidya @crm-regression @ui-wf @tasksearch
  Scenario Outline:Validate Save Search Parameters when saved it
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I create the save task search
  #  When I navigate to "Saved Task Search" page
  #  When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    When I update the Search parameter "<updateType>","<updateStatus>","<updatePriority>"
    Then I click on search button on task search page
    And I click on continue button on warning message
 #   And I create the save task search
  #  When I navigate to "Saved Task Search" page
  #  When I navigate to "Task Search" page
    And I click on cancel button on task search page
    And I will select save task search value from Search by Saved Task Search drop down
    Then I will verify newly created saved task search is updated with new search parameter "<taskId>","<taskType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<updateType>","<updateStatus>","<updatePriority>"
    And Close the soft assertions
    Examples:
      |taskId |taskType|srType|status   |statusDate|Priority|dueDate|searchCase|caseId|searchConsumer|consumerId|advanceSearch|consumerFN|consumerLN|source|assignee|createdBy|createdOn|updateType  |updateStatus|updatePriority|contactReason|
      || General||         || 4      ||          ||              ||false        ||          ||        ||         |Inbound Task|Complete    |1             ||

  @CP-28985 @CP-28985-01 @CP-30301 @CP-30301-01 @CP-30081 @CP-30081-02 @crm-regression @ui-wf @kamil @ruslan @tasksearch
  Scenario: Verification External case ID column header does not match data returned for task search
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "General" task page
    And I will provide following information before creating task
      | assignee | Service AccountOne |
      | taskInfo | random             |
    And I click on save button in create task page
 #   Then I verify Success message is displayed for task
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
  #  And I navigate to "Task/SR Management" and then to "Task/SR Search" in the left menu
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "staffAssignedTo"
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify Case Id "-- --" and Consumer ID are "-- --" in Task Search results
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And Close the soft assertions

