Feature: Validation of Duplicate TASK or SR

  Background: Common steps for login functionality
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiate soft assertion

  @API-CP-38148 @API-CP-38148-01 @API-CP-38159 @API-CP-38159-01 @API-CP-38920 @API-CP-38920-01 @API-CRM-Regression @API-WF @ruslan
  Scenario Outline: Verify duplicate sr or task with linked case or consumer
    When I have a request to create External task with the following values
      | taskTypeId | <taskTypeID> |
      | createdBy  | 8369         |
      | <type>     | <id>         |
    Then I send the request for the External task endpoint
    When I have a request to verify duplicate task type with the following values
      | taskTypeId | <taskTypeID> |
      | <type>     | <id>         |
    Then I send the request for the duplicate task endpoint
    Then I verify "<restricted_or_allowed>" duplicate task response for "<linkType>"
    And Close the soft assertions
    Examples:
      | taskTypeID | type     | id     | linkType | restricted_or_allowed |
      | 12545      | CONSUMER | 164196 | CONSUMER | restricted            |
      | 13359      | CASE     | 92095  | CASE     | restricted            |
      | 15896      | CONSUMER | 164196 | CONSUMER | allowed               |
      | 15896      | CASE     | 92095  | CASE     | allowed               |

  @API-CP-38148 @API-CP-38148-02 @API-CP-38159 @API-CP-38159-02 @API-CP-38920 @API-CP-38920-02 @API-CRM-Regression @API-WF @ruslan
  Scenario Outline: Verify duplicate sr or task with linked case and consumer
    When I have a request to create External task with the following values
      | taskTypeId | <taskTypeID> |
      | createdBy  | 8369         |
      | CASE       | <case>       |
      | CONSUMER   | <consumer>   |
    Then I send the request for the External task endpoint
    When I have a request to verify duplicate task type with the following values
      | taskTypeId | <taskTypeID> |
      | CASE       | <case>       |
      | CONSUMER   | <consumer>   |
    Then I send the request for the duplicate task endpoint
    Then I verify "<restricted_or_allowed>" duplicate task response for "<linkType>"
    And Close the soft assertions
    Examples:
      | taskTypeID | case  | consumer | linkType          | restricted_or_allowed |
      | 12550      | 92095 | 164196   | CASE and CONSUMER | restricted            |
      | 15003      | 92095 | 164196   | CASE and CONSUMER | allowed               |

  @API-CP-38148 @API-CP-38148-03 @API-CP-38159 @API-CP-38159-03 @API-CP-38920 @API-CP-38920-03 @API-CRM-Regression @API-WF @ruslan
  Scenario Outline: Verify duplicate sr or task with linked case,consumer or application
    When I have a request to verify duplicate task type with the following values
      | taskTypeId | <taskTypeID> |
      | <type>     | <id>         |
    Then I send the request for the duplicate task endpoint
    Then I verify "<restricted_or_allowed>" duplicate task response for "<linkType>"
    And Close the soft assertions
    Examples:
      | taskTypeID | type        | id    | linkType    | restricted_or_allowed |
      | 12545      | CONSUMER    | 134633 | CONSUMER    | allowed               |
      | 16045      | CASE        | 16377  | CASE        | allowed               |
      | 15003      | APPLICATION | 26183  | APPLICATION | allowed               |
      | 15003      | APPLICATION | 64763  | APPLICATION | restricted            |

  @API-CP-38148 @API-CP-38148-04 @API-CP-38159 @API-CP-38159-04 @API-CP-38920 @API-CP-38920-04 @API-CRM-Regression @API-WF @ruslan
  Scenario Outline: Verify duplicate sr or task with linked case or consumer
    When I have a request to create External task with the following values
      | taskTypeId | <taskTypeID> |
      | createdBy  | 8369         |
      | <type>     | <id>         |
    Then I send the request for the External task endpoint
    Then I send the request for the External task endpoint
    Then I verify error message for "<type>" with "<restricted_or_allowed>" access
    And Close the soft assertions
    Examples:
      | taskTypeID | type        | id    | restricted_or_allowed |
      | 12545      | CONSUMER    | 164196 | restricted            |
      | 13359      | CASE        | 92095  | restricted            |
      | 15003      | APPLICATION | 64763  | restricted            |
      | 15003      | CONSUMER    | 8185   | allowed               |
      | 15003      | CASE        | 395    | allowed               |
      | 16045      | CASE        | 16377  | allowed               |

  @API-CP-38148 @API-CP-38148-05 @API-CP-38159 @API-CP-38159-05 @API-CP-38920 @API-CP-38920-05 @API-CRM-Regression @API-WF @ruslan
  Scenario Outline: Verify duplicate sr or task with linked case and consumer
    When I have a request to create External task with the following values
      | taskTypeId | <taskTypeID> |
      | createdBy  | 8369         |
      | CASE       | <case>       |
      | CONSUMER   | <consumer>   |
    Then I send the request for the External task endpoint
    Then I send the request for the External task endpoint
    Then I verify error message for "<type>" with "<restricted_or_allowed>" access
    And Close the soft assertions
    Examples:
      | taskTypeID | case  | consumer | type              | restricted_or_allowed |
      | 12550      | 92095 | 164196   | CASE and CONSUMER | restricted            |
      | 15003      | 92095 | 164196   | CASE and CONSUMER | allowed               |

  @API-CP-37761 @API-CP-37761-01 @priyal @crm-regression @ui-wf
  Scenario Outline: Verify create AA SR from API and Verify after Update the Due Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated active business units and teams api
    And User send Api call with payload "getBusinessUnitByTaskId" to get BU for "<taskTypeId>"
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "success"
    And I Get all the active business units from API response
    Then User send Api call with payload to get Task for given BU "<taskTypeId>"
    Examples:
      | taskTypeId| projectName| projectId|
      | 13359     | BLCRM      | 6203     |
      | 15415     | IN-EB      | 8861     |
      | 13520     | CoverVA    | 8166     |
      | 12840     | NJ-SBE     | 6210     |