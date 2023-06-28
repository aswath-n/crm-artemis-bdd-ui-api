Feature: API: Consumer Controller

  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

#  #    # -- The following test case needs to redesign for Case creation without static case Id. Creation of Case and consumer currently not implemented in CRM.  --
  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Sujoy
  Scenario Outline: Create Consumer Health Check -- PUT com/app/crm/consumer
    Given I initiated Create Consumer API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>"
    And I can run create consumer API
    Then I can verify consumer consumerLastName with value "<consumerLastName>" on API response
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip | projectName |
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Sujoy
  Scenario Outline: Search Consumer Health Check -- POST app/crm/consumers
    Given I initiated Consumer Search API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can search consumer by "<Node>" with value "<value>"
    And I initiated Consumer Search API
    And I run the consumer search API
    Then I can verify consumer "<Node>" with value "<value>" on API response
    Examples:
      | Node             | value | projectName |
      | consumerFirstName | Emma  |[blank]|

  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Shruti
  Scenario Outline: Verify CONSUMER_SAVE_EVENT  are generated for consumer end point
    Given I initiated Create Consumer API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>"
    And I can run create consumer API
    Then I can verify consumer consumerLastName with value "<consumerLastName>" on API response
    And I get the consumerId from API response
    Then I verify consumer event by searching events with correlation id
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip | eventName           | projectName |
      | {random}          | {random}         | {random}    | {random}   | CONSUMER_SAVE_EVENT |[blank]|
