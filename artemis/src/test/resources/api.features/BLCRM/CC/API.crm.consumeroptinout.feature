Feature: API: Consumer Opt In/Out Feature

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @CP-268 @API-CP-268-04 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt In
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer Opt In
    And I run Create Consumer API for Consumer Opt In
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @CP-268 @API-CP-268-05 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Record Create Date and Created By fields
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt In
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer Opt In
    And I run Create Consumer API for Consumer Opt In
    Then I verify created on and created by values for "CONSUMER_SAVE_EVENT"
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|