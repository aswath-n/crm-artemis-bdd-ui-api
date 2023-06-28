Feature: API-Tenant Manager: Task Template Controller

  @auxiliaryService @event-api-AS @API-HealthCheck @API-TM-SmokeTest @Sujoy @API-TaskTemplate-SmokeTest @failed0626 @tenantManagerAPI @task-template-api-TM
  Scenario Outline: Create Task Type API POST mars/tm/tasktype/save
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I get task template id from project ""
    And I get project permission API by Project ID "" page "0" size "10" and sort "permissionGroupName"
    And I initiated Create Task Type API
    When I can provide details with following information:
      |projectId      |[blank]|
      |priority       | 5                        |
      |createdBy      |Test Automation Smoke     |
      |dueInDays      |11                        |
      |workingDayFlag |true                      |
      |taskTypeName   |CreateTaskTypeSmoke 2     |
      |taskTypeDesc   |Create Task Type Desc 2   |
    And I can provide the following group permission types
      |ROUTE_NON_ESCALATED_TASK|
      |ROUTE_ESCALATED_TASK    |
    And I can run create task type API
    Then I verify task type id by searching based on projectId "" and taskTypeId ""
    Examples:
      |projectName|
      |[blank]|


  @auxiliaryService @search-services-AS @API-HealthCheck @API-TaskTemplate-SmokeTest @API-TM-SmokeTest  @Sujoy @tenantManagerAPI @task-template-api-TM @api-smoke-devops
  Scenario Outline: Search for Task Type by task name for project-/mars/tm/tasktype/bytaskname
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get task type by name
    When I provide project id as "" and task type name as "General" to get task type
    And I run get task type by name API
    Then I verify Task type records are retrieved
    Examples:
      |projectName|
      |[blank]|


  @API-HealthCheck @API-TaskManagement @API-TM-SmokeTest @Shruti @tenantManagerAPI @task-template-api-TM
  Scenario Outline: GET task types for project -/mars/tm/tasktype/projectId/44
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated get task types API for project "SelectFromConfigFile"
    When I run get task type API
    Then I verify Task type records are retrieved
    Examples:
      |projectName|
      |[blank]|


