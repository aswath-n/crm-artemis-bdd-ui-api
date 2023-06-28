@API_BusinessDay
Feature: API-Tenant Manager: Business Day APIs Validation

  @API-CP-13824 @API-CP-13824-1 @API-TM @tenant-manager @API-TM-Regression @Vidya
  Scenario Outline: Verify Holiday GET API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated business day GET API for "<projectName>"
    When I run the business day GET API
    Then I verify response of business day get api has all the valid data
    Examples:
      |projectName|
      |[blank]|
      | NJ-SBE    |

  @API-CP-13824 @API-CP-13824-2 @API-TM @tenant-manager @API-TM-Regression @Vidya
  Scenario Outline: Verify Holiday GET API
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated business day put API
    When I Provide a body to create business day record
    And I run the business day Put API
    Then I verify response of business day put api
    And I provide a body to update the business day record
    And I run the business day Put API
    Then I verify response of business day put api
    Examples:
      |projectName|
      |[blank]|

  @API-CP-28436 @API-CP-28436-01 @API-TM @tenant-manager @API-TM-Regression @Shruti
  Scenario Outline: Verify error message is displayed if Business Day added with exisiting name
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated business day put API
    When I Provide a body to create business day record
    And I run the business day Put API
    Then I verify response of business day put api
    When I run the business day Put API
    And I verify the error message for duplicate name business day Put API
#    And I provide a body to update the business day record
#    And I run the business day Put API
#    Then I verify response of business day put api
    Examples:
      |projectName|
      |[blank]|




