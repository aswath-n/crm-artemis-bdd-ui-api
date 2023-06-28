@ProjectCreation
Feature: API-CRM Core:User Activity Controller


  @API-CP-25696 @API-CP-25696-1 @API-CRM @API-CORE @Araz @API-CRM-Regression
  Scenario Outline: Capture CP application refresh time using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiate userActivity end point for REST api call
    Then I construct userActivity request body with an actionId value as "<actionId>"
    Then I can verify the the user activity session using API
    Examples:
      | projectName | actionId |
      | BLCRM       | 5        |
      | IN-EB       | 5        |
      | NJ-SBE      | 5        |
      | CoverVA     | 5        |

  @API-CP-28424 @API-CP-28424-01 @API-CRM @API-CORE @Araz @API-CRM-Regression
  Scenario Outline: Capture CP application close time using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiate userActivity end point for REST api call
    Then I construct userActivity request body with an actionId value as "<actionId>"
    Then I can verify the the user activity session using API
    Examples:
      | projectName | actionId |
      | BLCRM       | 4        |
      | IN-EB       | 4        |
      | NJ-SBE      | 4        |
      | CoverVA     | 4        |
