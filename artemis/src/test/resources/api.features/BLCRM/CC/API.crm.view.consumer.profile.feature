Feature: API: View Consumer Profile Feature

  @API-CP-343  @API-CP-343-01 @asad @API-CRM-Regression
  Scenario Outline: Verify Consumer Profile Result
    Given I created consumer through API for viewing consumer profile
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated consumer profile search for viewing consumer profile
    When I searched for the created Consumer for viewing consumer profile
    Then I verify the consumer search result for viewing consumer profile
    Examples:
      |projectName|
      ||

  @API-CP-343  @API-CP-343-02 @asad @API-CRM-Regression
  Scenario Outline: Verify Single Consumer Profile
    Given I created consumer through API for viewing consumer profile
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated consumer profile search for viewing consumer profile
    When I searched for the created Consumer for viewing consumer profile
    Then I verify the consumer profile for viewing consumer profile
    Examples:
      |projectName|
      ||