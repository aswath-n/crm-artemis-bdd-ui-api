Feature: API: Case Correspondence with Primary Individual on case

  @asad @API-CP-3636-01 @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Case Correspondence Provides Primary Individual on case
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Correspondence API for Primary Individual on case
    When I run Case Correspondence API for Primary Individual on case
    Then I will get Primary individual case and consumer level contact information
