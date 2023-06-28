Feature: API: Case Loader with Eligibility and Enrollment information

  @asad @API-CP-3508-01 @API-EE @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Case Loader accepts Eligibility and Enrollment in request
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API for eligibility and enrollment
    When I run Case Loader API accepts eligibility and enrollment information in request
    Then I will get success response indicating input JSON was acceptable
