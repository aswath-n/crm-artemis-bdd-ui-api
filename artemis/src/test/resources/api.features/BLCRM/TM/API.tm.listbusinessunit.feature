@businessunitList
Feature: API-Tenant Manager: Business Unit List

  @API-CP-799 @API-CP-799-1 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
  Scenario Outline: Verify Business Unit Get API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    And I can get API response status as "<status>" and error code as "<errorCode>"
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I can verify each Business unit has all required information displayed
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate     |taskType|projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|        |[blank]|

  @API-CP-799 @API-CP-799-2 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Business Unit status in Get API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    And I can get API response status as "<status>" and error code as "<errorCode>"
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I can verify Business unit "<buStatus>" in get Api
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate     |buStatus  |taskType|projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|active    |[blank]|           |
      |[blank]|{random} |{random} | success | +1          |[blank]|future    |[blank]|           |
      |[blank]|{random} |{random} | success | -1          | -1         |inactive  |[blank]|           |
      |[blank]|{random} |{random} | success | -1          | +2         |active    |[blank]|           |

  @API-CP-799 @API-CP-799-3 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Business Unit Get API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    And I can get API response status as "<status>" and error code as "<errorCode>"
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I can verify all fields of business unit has proper value
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate     |taskType|projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|        |[blank]|

  @API-CP-799 @API-CP-799-4 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Business Unit records sorting order
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I check if it has 5 business unit records if not then I create 5 records
    And I initiated Business Unit By Project ID via API "<projectId>"
    Then I can verify Business Unit get API response will be "<status>"
    And I can verify in business unit GET API records are according to sorting order
    Examples:
      | projectId | status  |projectName|
      |[blank]| success |[blank]|