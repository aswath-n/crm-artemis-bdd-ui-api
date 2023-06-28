@API-TenantAddEditBUDetails
Feature: API-Tenant Manager: Add/Edit Business Unit Details

  @API-CP-653 @API-CP-653-1 @API-CP-1909 @API-CP-1909-1 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
  Scenario Outline: Create Business Unit API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate |taskType|projectName|
      |  8635         |{random} |{random} | success | currentDate |[blank]|        |[blank]|
      |  8635         |{random} |{random} | success | +1          |[blank]|        |[blank]|

  @API-CP-653 @API-CP-653-2 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression @API-TM-SmokeTest
  Scenario Outline: Validate Duplicate Business Unit is not created by API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API

    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    And I run the create business unit API
    Then I can get API response status as "<status1>" and error code as "<errorCode>"
    Examples:
      | projectId | BUName  | BUDesc  | status  | status1  | startDate   |errorCode|endDate|taskType|projectName|
      | 8635       |{random} |{random} | success | fail     | currentDate |Business Unit Name must be unique. Please enter a different Name|[blank]|   |[blank]|

  @API-CP-653 @API-CP-653-3 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Validate Duplicate Business Unit is created when dates are not overlapping
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    #Given I initiated Create Business Unit API
    When I can provide business unit details with "<projectId>" "<BUName1>" "<BUDesc>" "<startDate1>" "<endDate1>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    Examples:
      | projectId | BUName   | BUDesc   | status  | startDate   | endDate | BUName1  | startDate1 | endDate1 | taskType | projectName |
      | 8635      | {random} | {random} | success | currentDate | +1      | previous | +2         |[blank]|          |[blank]|

  @API-CP-1909 @API-CP-1909-2 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Create Business Unit API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Create Business Unit API

    When I can provide business unit details with "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate |taskType|projectName|
      |  8635         |{random} |{random} | success | currentDate |[blank]|{select}|[blank]|

  @API-CP-1683 @API-CP-1683-1 @API-TM @Vidya @tenantManagerAPI @tm-api-TM @API-TM-Regression
  Scenario Outline: Edit Business Unit API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I ensure Business Unit Get API has at least one business unit for "<projectId>"
    Given I initiated Create Business Unit API
    When I can provide business unit details with "<BUId>" "<projectId>" "<BUName>" "<BUDesc>" "<startDate>" "<endDate>" "<taskType>"
    And I run the create business unit API
    Then I can get API response status as "<status>" and error code as "<errorCode>"
    Examples:
      | projectId | BUName  | BUDesc  | status  | startDate   |endDate |taskType|BUId|projectName|
      |  8635         |{random} |{random} | success | currentDate |[blank]|{select}|[blank]|           |

