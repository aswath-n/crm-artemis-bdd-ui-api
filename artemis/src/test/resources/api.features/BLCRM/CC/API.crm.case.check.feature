Feature: API: Case Check Feature

#  @asad @API-CP-58-01 @case-consumer-api-CC  @API-CC @API-CRM-Regression
  Scenario: Unique Case Number Found
    When I will get the Authentication token for "" in "CRM"
    Given I run the Case Loader API for "existing case"
    When I initiated Case Check API for search case
    Then I will find the case for search case


#  @asad @API-CP-58-02 @case-consumer-api-CC @API-CC @API-CRM-Regression
  Scenario: No Unique Case Number Match Found
    When I will get the Authentication token for "" in "CRM"
    Given I run the Case Loader API for "no case"
    When I initiated Case Check API for search case
    Then I will not find the case for search case
