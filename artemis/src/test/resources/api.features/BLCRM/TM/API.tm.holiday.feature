Feature: API-Tenant Manager: Holiday APIs Validation

  @GA-API-CP-8364 @GA-API-CP-8364-1 @API-TM @tenant-manager @GA-API-TM-Regression @Vidya
  Scenario Outline: Verify Holiday GET API
    Given I initiated holyday GET API for "<projectId>" and "<year>"
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I run the holiday GET API
    Then I verify response has all the valid data
    Examples:
      | projectId | year|projectName|
      | GACRM     |2020 |[blank]|