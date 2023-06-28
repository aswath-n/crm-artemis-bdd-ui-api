Feature: API: Consumer Opt Out Feature

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-4390 @API-CP-4390-02.1 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer for Consumer Opt in : PHONE
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "phone" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated for Consumer "phone" Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @API-CP-4390 @API-CP-4390-02.2 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer for Consumer Opt in : TEXT
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
   When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "text" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated for Consumer "text" Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @API-CP-4390 @API-CP-4390-02.3 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer for Consumer Opt in : EMAIL
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "email" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated for Consumer "email" Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @API-CP-4390 @API-CP-4390-02.4 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer for Consumer Opt in : FAX
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "fax" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated for Consumer "fax" Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @API-CP-4390 @API-CP-4390-02.5 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Publish an Event for DPBI to Consumer for Consumer Opt in : MAIL
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "mail" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I validate request is passed for a "CONSUMER_SAVE_EVENT" for "DPBI" to be generated for Consumer "mail" Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @API-CP-4390 @API-CP-4390-03 @API-CC @API-CRM-Regression @asad
  Scenario Outline: Record Create Date and Created By fields for Consumer Opt in
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API for Consumer Opt in
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>" for Consumer "" Opt in
    And I run Create Consumer API for Consumer Opt in
    Then I verify created on and created by values for "CONSUMER_SAVE_EVENT" for Consumer Opt in
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip |projectName|
      | {random}          | {random}         | {random}    | {random}   |[blank]|