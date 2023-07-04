Feature: Validate IVR transaction

  @CP-29803 @CP-29803-01 @Vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline:Verify record is stored in task, task details, task details history and external link table
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "Medicaid ID Card" for project ""
    And I will provide required information to create external task with "<taskInfo>" "<triggerDate>"
      ||
    When I initiated external create task api
    And I run the create external task API and check the status code is "200"
    And I initiated IVR get task by task id ""
    And I run IVR get task by task id API
    Then I verify response get from IVR get task api
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search with taskId
    And In search result click on task id to navigate to view page
    Examples:
      | projectName | taskInfo    | triggerDate |
      | CoverVA     | Validate IVR||