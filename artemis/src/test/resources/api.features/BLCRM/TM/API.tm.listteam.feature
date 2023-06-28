Feature: API-Tenant Manager: Team List

  @API-CP-800 @API-CP-800-1 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
  Scenario Outline: Verify Team Get API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    Then I can get team API response status as "<status>" and error code as "<errorCode>"
    And I initiated Team By Project ID via API "<projectId>"
    Then I can verify Team get API response will be "<status>"
    And I can verify each Team has all required information displayed
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|     |[blank]|

  @API-CP-800 @API-CP-800-2 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Team status in Get API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    Then I can get team API response status as "<status>" and error code as "<errorCode>"
    And I initiated Team By Project ID via API "<projectId>"
    Then I can verify Team get API response will be "<status>"
    And I can verify each Team has all required information displayed
    And I can verify Team "<buStatus>" in get Api
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |buStatus  |projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|     |active    |[blank]|
      |[blank]|{random} |{random} | success | +1          |[blank]|     |future    |[blank]|
#      |[blank]|{random} |{random} | success | -1          | -1     |[blank]|inactive  |[blank]|
#      |[blank]|{random} |{random} | success | -1          | +2     |[blank]|active    |[blank]|
   #Team can not be created with past StartDate, functinality changed.

  @API-CP-800 @API-CP-800-3 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Team Get API return proper value
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    Then I can get team API response status as "<status>" and error code as "<errorCode>"
    And I initiated Team By Project ID via API "<projectId>"
    Then I can verify Team get API response will be "<status>"
    And I can verify all fields of team has proper value
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |projectName|
      |[blank]|{random} |{random} | success | currentDate |[blank]|     |[blank]|

  @API-CP-800 @API-CP-800-4 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Team records sorting order
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I check if it has 5 team records if not then I create 5 team records
    And I initiated Team By Project ID via API "<projectId>"
    Then I can verify Team get API response will be "<status>"
    And I can verify in team GET API teams are according to sorting order
    Examples:
      | projectId | status  |projectName|
      |[blank]| success |[blank]|