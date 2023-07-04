@CP-11701
Feature: API: Case Check Feature

  @asad @CP-11701-01 @API-CC @API-CC-NJ
  Scenario: Return Error when request is missing required fields for Consumer First Name
    Given I will get the Authentication token for "" in "CRM"
    When I initiate the case consumer API for project NJ
    When I run the case consumer API for project NJ with only "consumerFirstName"
    Then I verify the error code message from the response for only "consumerFirstName"

  @asad @CP-11701-02 @API-CC @API-CC-NJ
  Scenario: Return Error when request is missing required fields for Consumer Last Name
    Given I will get the Authentication token for "" in "CRM"
    When I initiate the case consumer API for project NJ
    When I run the case consumer API for project NJ with only "consumerLastName"
    Then I verify the error code message from the response for only "consumerLastName"

  @asad @CP-11701-03 @API-CC @API-CC-NJ
  Scenario: Return Error when request is missing required fields for Consumer Date of Birth
    Given I will get the Authentication token for "" in "CRM"
    When I initiate the case consumer API for project NJ
    When I run the case consumer API for project NJ with only "consumerDateOfBirth"
    Then I verify the error code message from the response for only "consumerDateOfBirth"

  @asad @CP-11701-04 @API-CC @API-CC-NJ
  Scenario: Return Error when request is missing required fields for Consumer First Name and Consumer Date of Birth
    Given I will get the Authentication token for "" in "CRM"
    When I initiate the case consumer API for project NJ
    When I run the case consumer API for project NJ with only "consumerFirstName-consumerDateOfBirth"
    Then I verify the error code message from the response for only "consumerFirstName-consumerDateOfBirth"

  @asad @CP-11701-05 @API-CC @API-CC-NJ
  Scenario: Return Error when request is missing required fields for Consumer Last Name and Consumer Date of Birth
    Given I will get the Authentication token for "" in "CRM"
    When I initiate the case consumer API for project NJ
    When I run the case consumer API for project NJ with only "consumerLastName-consumerDateOfBirth"
    Then I verify the error code message from the response for only "consumerLastName-consumerDateOfBirth"
