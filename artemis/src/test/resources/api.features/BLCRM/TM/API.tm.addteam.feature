Feature: API-Tenant Manager: Add Team & Team User

  @API-CP-739 @API-CP-739-1 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
  Scenario Outline: Create Team API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    Then I can get team API response status as "<status>" and error code as "<errorCode>"
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  | errorCode                    |projectName|
      |   8635    |{random} |{random} | success | currentDate |[blank]|     |ERROR_CODE_DATABASE_EXCEPTION |[blank]|
      |   8635    |{random} |{random} | success | +1          |[blank]|     |ERROR_CODE_DATABASE_EXCEPTION |[blank]|


    @API-CP-1684 @API-CP-1684-01 @API-TM @Shruti @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
    Scenario Outline: Add single user to a team
      When I will get the Authentication token for "<projectName>" in "Tenant Manager"
      Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
      When I initiated Create Team API
      And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
      And I run the create team API
      And I get team list for the "<projectId>"
      And I get user list for the "<projectId>"
      And I initiate create team user api
      And I provide team user details
      And I run create team user api
      Then I verify team user is saved
      Examples:
        | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |projectName|
        |   8635    |{random} |{random} | success | +1          |[blank]|     |[blank]|

  @API-CP-1684 @API-CP-1684-02 @API-TM @Shruti @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Add multiple team users
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    And I get team list for the "<projectId>"
    And I get user list for the "<projectId>"
    And I initiate create team user api
    And I provide multiple team user details
    And I run create team user api
    Then I verify team user is saved
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |projectName|
      |    8635   |{random} |{random} | success | +1          |[blank]|     |[blank]|


  @API-CP-1684 @API-CP-1684-03 @API-TM @Shruti @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Created On & Created By are captured when team user is saved
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    When I initiated Create Team API
    And I can provide team details with "<projectId>" "<teamName>" "<teamDesc>" "<startDate>" "<endDate>" "<bu>"
    And I run the create team API
    And I get team list for the "<projectId>"
    And I get user list for the "<projectId>"
    And I initiate create team user api
    And I provide team user details
    And I run create team user api
    And I verify team user is saved
    Then I verify created on and created by values in team user
    Examples:
      | projectId |teamName |teamDesc | status  | startDate   |endDate | bu  |projectName|
      |   8635    |{random} |{random} | success | +1          |[blank]|     |[blank]|


  @API-CP-2408 @API-CP-2408-01 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Verify Created On & Created By are captured when team user is saved
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    And I ensure Team Get API has at least one team record for "<projectId>"
    And I ensure Team Get API has at least one team user record for "<projectId>"
    When I initiate create team user api
    And I provide team user update details
    And I run create team user api
    And I verify team user is saved
    Then I verify updated on and updated by values in team user
    Examples:
      | projectId |projectName|
      |  8635     |  RegressionBaseline |
