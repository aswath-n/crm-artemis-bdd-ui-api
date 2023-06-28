@lookheathcheck
Feature: API-CRM: lookup-rest-controller

  @auxiliaryService @lookup-api-AS @API-HealthCheck @API-CRM-SmokeTest @API-CRM-1289 @API-Lookup @Shruti @0212
  Scenario Outline: Verify Consumer Type Values
    Given I initiated Look up API with service "consumer" and table name "ENUM_CONSUMER_TYPE"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I run get look up api
    Then I verify values for "ENUM_CONSUMER_TYPE"
      |Agency   |
      |Consumer |
      |Media    |
      |Provider |
      |Anonymous|
    Examples:
      |projectName|
      ||


  @auxiliaryService @lookup-api-AS @API-HealthCheck  @API-CRM-SmokeTest @API-CRM-1289 @API-Lookup @Shruti @0212
  Scenario Outline: Verify Consumer Role Values
    Given I initiated Look up API with service "consumer" and table name "ENUM_CONSUMER_ROLE_TYPE"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I run get look up api
    Then I verify values for "ENUM_CONSUMER_ROLE_TYPE"
      |Primary Individual         |
      |Authorized Representative  |
      |Member                     |
    Examples:
      |projectName|
      ||



