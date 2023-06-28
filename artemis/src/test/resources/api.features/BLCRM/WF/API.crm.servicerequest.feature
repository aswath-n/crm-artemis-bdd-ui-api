@apiSR
Feature: API: Verify Service Request

  @API-CP-16048 @API-CP-16048-01 @API-CP-17599 @API-CP-17599-01 @API-WF @API-CRM @vidya @task-manag-ms-WM @API-CRM-Regression
  Scenario Outline:Verify BPM process is not initiated when we create other then General service request and link to case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Two SR" for project ""
    And I will provide required information to create external sr with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |Disposition|
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external sr
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify Task table information in response of external sr
    And I verify Task Details and Task Details History table information in response of external sr
    And I verify External Link table information in response of external sr has "<caseId>" "<consumerId>" "<inboundId>" ""
    When I initiated bpm process get api by sr id for process name "general-servicerequest"
    And I run bpm process get api by sr id
    Then I will check bpm process is not started
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|
      ||8834  ||         ||

  @API-CP-16048 @API-CP-16048-02 @API-CP-14954 @API-CP-14954-02 @API-CP-17599 @API-CP-17599-02 @API-WF @API-CRM @vidya @task-manag-ms-WM @API-CRM-Regression
  Scenario Outline:Verify BPM process is initiated when we create General service request and link to case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Service Request" for project ""
    And I will provide required information to create external sr with "<caseId>" "<consumerId>" "<inboundId>" "<taskInfo>" "<triggerDate>"
      |Disposition|
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    Then I initiated get task by "" for task history
    And I run get task by task id API
    And I verify Task history table information in response of external sr
    Then I store task history response
    And I initiated get task by task id ""
    And I run get task by task id API
    Then I verify Task table information in response of external sr
    And I verify Task Details and Task Details History table information in response of external sr
    And I verify External Link table information in response of external sr has "<caseId>" "<consumerId>" "<inboundId>" "task"
    When I initiated bpm process get api by sr id for process name "general-servicerequest"
    And I run bpm process get api by sr id
    Then I will check response of bpm process get api has all the data with bpm instance id
    Examples:
      |projectName|caseId|consumerId|inboundId|taskInfo|
      ||8835  ||         ||