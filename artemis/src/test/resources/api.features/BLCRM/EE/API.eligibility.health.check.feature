@EE-HealthCheck
Feature: API: Eligibility Health Check Feature

    @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck
  Scenario Outline: Create New Eligibility record - POST /mars/eb/eligibilities
     Given I created a consumer for eligibility record
      When I will get the Authentication token for "<projectName>" in "CRM"
      When I initiated Eligibility Create API
    And I run the Eligibility Create API
    Then I will capture the facility placement data
      Examples:
        |projectName|
        ||

    @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck
    Scenario Outline: Update Exisiting Eligibility record -PUT   /mars/eb/eligibilities
    Given I initiated Eligibility API
      When I will get the Authentication token for "<projectName>" in "CRM"
    When I update hospice info
    And I run the Eligibility API
    Then I will verify the hospice placement data is updated
      Examples:
        |projectName|
        ||

   @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck @02032020
   Scenario Outline: Get All or Future or Active Eligibilities Records by Consumer Id
    Given I initiated get eligibility by consumer id "42083"
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run get eligibility api
    Then I verify eligibility records are displayed for the consumer "42083"
     Examples:
       |projectName|
       ||

   @API-Eligibility @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck @02032020
    Scenario Outline: Get All or Future or Active Eligibility segment Records by Consumer Id
      Given I initiated get eligibility segment by consumer id "42083"
     When I will get the Authentication token for "<projectName>" in "CRM"
      When I run get eligibility api
      Then I verify eligibility segment records are displayed for the consumer "42083"
     Examples:
       |projectName|
       ||




