@CaseRestController
Feature: API: Case Controller Health Check
    
  #commented by Aika
#  @auxiliaryService @search-services-AS @event-api-AS @caseConsumerAPI @case-consumer-api-CC @API-HealthCheck @API-CRM-SmokeTest @Sujoy @CaseRestController @0323-001
#  Scenario Outline: Search Case Consumer Health Check -- POST app/crm/case/consumers
#    Given I created a case required for validations
#    And I initiated Create case member API
#    When I can provide case member information
#      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip |
#      | Consumer     | {random}          | {random}         | today-15000         | {random}    | Male       | today-50           |[blank]| Spouse       |
#    And I initiated Search Case API
#    When I can Search Case by "<Node1>" with value "<value1>", "<Node2>" with value "<value2>", "<Node3>" with value "<value3>", "<Node4>" with value "<value4>" and "<Node5>" with value "<value5>"
#    And I run the case search API
#    Then I can verify on case search API response will be "<success>"
#    Examples:
#        | Node1           | value1  | Node2           | value2  | Node3              | value3       | Node4     | value4    | Node5                   | value5  |success|
#        |consumerFirstName|[blank]| consumerLastName|[blank]| consumerDateOfBirth|[blank]|consumerSSN |[blank]| consumerIdentificationNo|[blank]| True  |

  #commented by Aika
#  @auxiliaryService @search-services-AS @event-api-AS @caseConsumerAPI @case-consumer-api-CC @API-HealthCheck @API-CRM-SmokeTest @Shruti @CaseRestController @caseHead
#  Scenario: Create Case Health Check -- PUT app/crm/case
#    Given I created a case head
#    And I initiated create case API
#    When I can provide case information to create a case
#    And I can run create case API
#    Then I verify case id created
 #commented by Aika
#  @case-consumer-api-CC @API-HealthCheck @API-CRM-SmokeTest @Shruti @CaseRestController
#  Scenario: Create Case & Consumer Health Check -- PUT app/crm/caseConsumer
#    Given I created a case head
#    And I initiated create case consumer API
#    When I can provide case information to create a case
#    And I can run create case API
#    Then I verify case id created

#Failing due to bug CRM-2208
#  @API-HealthCheck @API-CRM-SmokeTest @Shruti @CaseRestController @0323-002
#  Scenario: Get the Case record based on case id -- GET /app/crm/case/{caseId}
#   Given I created a case required for validations
#    When I initiated get Case API by case id ""
#    And I can run Get case by case ID API
#    Then I verify case details are retrieved ""


  @case-consumer-api-CC @API-CRM-SmokeTest  @API-CC @Shruti
  Scenario Outline: Search Case records Health Record-- POST /app/crm/cases
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I provide case id as "" case status as "ACTIVE" and case type as "Member" for Search Case records
    And I initiated Search Case records api
    When I can run search case records api
    Then I verify case records are retrieved
    Examples:
      | projectName |
      |[blank]|

  #commented by Aika
#  @auxiliaryService @event-api-AS @caseConsumerAPI @case-consumer-api-CC @API-HealthCheck @API-CRM-SmokeTest @CaseRestController @Shruti
#  Scenario: Create Case Consumer Health Check -- PUT com/app/crm/casemember
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I can provide case member information
#    |consumerType | consumerFirstName  | consumerLastName |consumerDateOfBirth| consumerSSN |GenderCode |effectiveStartDate |caseId |relationShip|
#    |Member       |    {random}        | {random}         |today-15000        |  {random}   | Male              |today-50           |  12  |Spouse    |

    #commented by Aika
#  @auxiliaryService @event-api-AS @caseConsumerAPI @case-consumer-api-CC @API-HealthCheck  @API-CRM-SmokeTest @Shruti @1101 @CaseRestController
#  Scenario: List of Case Consumer by Case ID  Health Check -- GET com/app/crm/casemember/{caseId}
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I can provide case member information
#        |consumerType | consumerFirstName  | consumerLastName |consumerDateOfBirth| consumerSSN |consumerGenderCode |effectiveStartDate |caseId |relationShip|
#        |Consumer       |    {random}        | {random}         |today-9000         |  {random}   | Male              |today-50           |[blank]|Child       |
#    Given I initiated get case member API for case ""
#    Then I can verify case member is created


  @case-consumer-api-CC @API-CRM-SmokeTest @API-CC @Shruti
  Scenario Outline: Get Record based on correlationId and correlationType-- GET /app/crm/correlation/{correlationType}/{correlationId}
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a case required for validations
    And I can run create case loader API
    When I initiated search case by correlation id "<correlationType>" and "<correlationId>"
    And I can run Get case by case ID API
    Then I verify the case details in the response
    Examples:
      | correlationType | correlationId | projectName |
      | case            |[blank]|             |


  @API-CC @API-CRM-SmokeTest @Shruti  @0323-001
  Scenario Outline: Return the list of family member for a case -- GET /app/crm/consumers/{caseId}
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated get consumers for case "12334"
    Then I verify family member records are retrieved by case id
    Examples:
      | projectName |
      |[blank]|

   #commented by Aika
#   @caseConsumerAPI @case-consumer-api-CC @API-HealthCheck @API-CRM-SmokeTest @Shruti @CaseRestController @08262019-2
#   Scenario: Verify Case Save & Case member save events are generated for caseConsumer end point
#   Given I created a case head
#   And I initiated create case consumer API
#   When I can provide case and consumer information to create a case
#   And I can run create case API
#   And I verify case id created
#   Then I verify case event by searching events with correlation id


    #refactor according to new CaseLoader payload
    #ready scenario
  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Sujoy @0323-001
  Scenario Outline: Search Case Consumer Health Check -- POST app/crm/case/consumers
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer with below details:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |[blank]| Spouse       |
    And I initiated Search Case API for new CaseLoader
    Then I can Search Case by "consumerFirstName", "consumerLastName", "consumerSSN", "consumerIdentificationNo"
    And I run the case search API for new CaseLoader
    Then I can verify on case search API response will be "<success>" for new CaseLoader
    Examples:
      | success | projectName |
      | True    |[blank]|


  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Shruti
  Scenario Outline: Create Case Health Check -- PUT app/crm/case
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer
    Then I will create a new case for case loader case creation without consumers
    Then I verify case id created using new Case Loader
    Examples:
      | projectName |
      |[blank]|


  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Shruti
  Scenario Outline: Create Case Consumer Health Check -- PUT com/app/crm/casemember
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer with below details:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |[blank]| Spouse       |
    Then I will verify case consumer aka case member was successfully created from CaseLoader
    Examples:
      | projectName |
      |[blank]|


  @case-consumer-api-CC @API-CC @API-CRM-SmokeTest @Shruti @08262019-2 @CP-11786 @CP-11786-01 @events-cc
  Scenario Outline: Verify Case Save & Case member save events are generated for caseConsumer end point
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer
    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Then I will publish an "CASE_SAVE_EVENT" event for DPBI to consume for reporting
    Examples:
      | projectName |
      |[blank]|



