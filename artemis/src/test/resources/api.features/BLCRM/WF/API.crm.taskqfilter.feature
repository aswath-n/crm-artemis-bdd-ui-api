Feature: API: Task Q filter Feature

  @API-CP-659 @API-CP-659-01 @API-CP-665 @API-CP-665-01 @API-CRM @vidya @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline:Verify Task Queue Filter By Project Name and Queue Filter By Id get api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate task queue filter Get APi
    And I run the task queue filter Get APi
    Then I verify the response of task queue filter Get api has task queue table data
    And I verify the response has task queue filter task type table data
    And I verify the response has task queue filter scopes table data
    When I initiate view task queue filter Get APi
    And I run the task queue filter Get APi
    Then I verify the response of view task queue filter Get api has task queue table data
    And I verify the response has view task queue filter task type table data
    And I verify the response has view task queue filter scopes table data
    Examples:
      |projectName|
      ||
      |NJ-SBE     |

  @API-CP-655 @API-CP-655-01 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify active business unit ids of single task type id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    And I get business unit and team ids from business units and teams response api
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I Get all the active business units from API response
    And I initiated Business Unit api by passing business unit ID "singleVal"
    Then I verify active business unit ids are matches
    Examples:
      |projectName|projectId|status      |
      ||         |success     |

  @API-CP-655 @API-CP-655-02 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify active business unit ids of multiple task type ids
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General" for project ""
    Given I initiated active business units and teams api
    And I added task type id to the list
    And Get the task type information of "inquiry/complaint" for project ""
    Given I initiated active business units and teams api
    And I added task type id to the list
    When I provide taskTypeId for active business units and teams "multipleVal"
    And I can run active bu and teams api
    And I get business unit and team ids from business units and teams response api
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I Get all the active business units from API response
    And I initiated Business Unit api by passing business unit ID "multipleVal"
    Then I verify active business unit ids are matches
    Examples:
      |projectName|projectId|status      |
      ||         |success     |

  @API-CP-655 @API-CP-655-03 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify active team ids of single task type id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    And I get business unit and team ids from business units and teams response api
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I Get all the active business units from API response
    And I initiated Business Unit api by passing business unit ID "singleVal"
    And I initiated Team By Project ID via API "<projectId>"
    Then I can verify Team get API response will be "<status>"
    And I Get all the active team Ids from API response
    Then I verify active team ids are matches
    Examples:
      |projectName|projectId|status      |
      ||         |success     |

  @API-CP-655 @API-CP-655-04 @API-CRM @Basha @API-CRM-Regression @API-WF @task-manag-ms-WM
  Scenario Outline: Verify active users of perticular permission group id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated active users api
    When I provide permission group id "<permissionGroupId>" for active users
    And I can run active users api
    And I get active users from response api
    Then I verify active user values are matches
    Examples:
      |projectName|permissionGroupId|
      ||785              |

  @API-CP-28756 @API-CP-28756-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @ruslan
  Scenario Outline: Verify default due date does not contain time
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated search records API
    Then I validate defaultDueDate does not contain time
    Examples:
      | projectName |
      | BLCRM       |
      | NJ-SBE      |
      | CoverVA     |
      | IN-EB       |
