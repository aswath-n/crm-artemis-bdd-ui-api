Feature: API: Case Controller

#  @case-consumer-api-CC @API-CRM-231-01 @API-CRM-482 @API-CRM-Regression @API-CC @Sujoy
  Scenario: Create Case and PI using API
    Given I initiated create case loader API
    When I will get the Authentication token for "" in "CRM"
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    Given I initiated get case member API for case ""
    Then I verify case consumer created


  @case-consumer-api-CC @API-CRM-SmokeTest @API-CC @Shruti @0325201901
  Scenario: Search Case records Health Record-- POST /app/crm/cases
    Given I will get the Authentication token for "" in "CRM"
    When I initiated Search Case records api
    When I provide case id as "" case status as "ACTIVE" and case type as "Member" for Search Case records
    When I can run search case records api
    Then I verify case records are retrieved
    When I provide case id as "{random}" case status as "" and case type as "" for Search Case records
    When I can run search case records api
    Then I verify case single case record is retrieved


 # Leave the node as blank required for search and others as NA
  @case-consumer-api-CC  @API-CRM-231 @API-CRM-482 @API-CC @API-CRM-Regression @Sujoy @0325201902
  Scenario Outline: Search Cases using API with combination of input parameters
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide case and consumer information to create a case
    And I initiated create case loader API
    And I can run create case loader API
    Given I initiated get consumers for case ""
    Then I verify case consumer created
    Given I initiated Search Case API
    When I can Search Case by "<Node1>" with value "<value1>", "<Node2>" with value "<value2>", "<Node3>" with value "<value3>", "<Node4>" with value "<value4>" and "<Node5>" with value "<value5>"
    And I run the case search API
    Then I verify Search Case by "<Node1>" with value "<value1>", "<Node2>" with value "<value2>", "<Node3>" with value "<value3>", "<Node4>" with value "<value4>" and "<Node5>" with value "<value5>"
    Examples:
      | Node1             | value1 | Node2            | value2 | Node3               | value3 | Node4       | value4 | Node5                    | value5 | success | projectName |
      | consumerFirstName |        | consumerLastName |        | consumerDateOfBirth | NA     | consumerSSN | NA     | consumerIdentificationNo | NA     | True    |             |
      | consumerFirstName | NA     | consumerLastName |        | consumerDateOfBirth |        | consumerSSN | NA     | consumerIdentificationNo | NA     | True    |             |
      | consumerFirstName | NA     | consumerLastName | NA     | consumerDateOfBirth | NA     | consumerSSN |        | consumerIdentificationNo | NA     | True    |             |
      | consumerFirstName |        | consumerLastName | NA     | consumerDateOfBirth |        | consumerSSN |        | consumerIdentificationNo | NA     | True    |             |
      | consumerFirstName |        | consumerLastName | NA     | consumerDateOfBirth |        | consumerSSN | NA     | consumerIdentificationNo | NA     | True    |             |

  @case-consumer-api-CC @API-CRM-778 @API-CRM-778-01 @API-CRM-Regression @Shruti @1101 @API-CC
  Scenario: Verify case member is created and all the details provided are captured
    When I will get the Authentication token for "" in "CRM"
    When I can provide case and consumer information to create a case
    And I initiated create case loader API
    And I can run create case loader API
    Given I initiated Create case member API
    When I can provide case member information
      | consumerRole | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
      | Member       | Consumer     | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           |        | spouse       |
    Given I initiated get case member API for case ""
    Then I verify the case member details using API
      | relationShip | consumerType | consumerRole |
      | spouse       | Consumer     | Member       |


  @case-consumer-api-CC  @API-CRM-394  @API-CRM-394-01 @API-CRM-Regression @API-CC @Shruti @1102
  Scenario: Verify that Active case members are displayed first followed by inactive
    When I will get the Authentication token for "" in "CRM"
    Given I initiated create case loader API
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    Given I initiated Create case member API
    When I can provide case member information
      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | effectiveEndDate | caseId | relationShip | consumerStatus |
      | Member       | {random}          | {random}         | 2022-08-03          | {random}    | Male               | today-10           | today+100        |        | Child        | Active         |
      | Member       | {random}          | {random}         | 2022-08-03          | {random}    | Male               | today-100          | today            |        | Spouse       | Active         |
      | Member       | {random}          | {random}         | 2022-08-03          | {random}    | Male               | today+50           | today+200        |        | Guardian     | Inactive       |
    Given I initiated get case member API for case ""
    Then I verify active case members fetched followed by inactive for consumerRole "Member" using API


#@case-consumer-api-CC @API-CRM-779 @API-CRM-779-01 @API-CRM @API-CRM-Regression @API-CC @Shruti  Retired
#  Scenario: Verify the update of first name & last name of  case member
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I will get the Authentication token for "" in "CRM"
#    When I can provide case member information
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | today-9000          | {random}    | Male               | today-50           || Child        |
#    Given I initiated get case member API for case ""
#    When I Update case member details using API
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           || Child        |
#    And I initiated get case member API for case ""
#    Then I verify updates for case member details using API


#  @case-consumer-api-CC @API-CRM-779 @API-CRM-779-02 @API-CRM @API-CRM-Regression @API-CC @Shruti Retired
#  Scenario: Verify the update of first name & last name of  case member
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I will get the Authentication token for "" in "CRM"
#    When I can provide case member information
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | today-9000          | {random}    | Male               | today-50           || Child        |
#    Given I initiated get case member API for case ""
#    When I Update case member details using API
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           || Child        |
#    And I initiated get case member API for case ""
#    Then I verify updates for case member details using API


  #@case-consumer-api-CC @API-CRM-779 @API-CRM-779-02 @API-CRM @API-CRM-Regression @API-CC @Shruti  Muted due to duplicate
  #Scenario: Verify the update of first name & last name of  case member
   ## Given I created a case required for validations
   # Given I initiated Create case member API
   # When I will get the Authentication token for "" in "CRM"
    #When I can provide case member information
     # | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
      #| Member       | {random}          | {random}         | today-9000          | {random}    | Male               | today-50           || Child        |
    #Given I initiated get case member API for case ""
    #When I Update case member details using API
     # | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
      #| Member       | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           || Child        |
    #And I initiated get case member API for case ""
    #Then I verify updates for case member details using API


#  @case-consumer-api-CC @API-CRM-779 @API-CRM-779-06 @API-CRM-Regression @API-CC @Shruti    Retired
#  Scenario: Verify the update of gender & relationship of  case member
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I will get the Authentication token for "" in "CRM"
#    When I can provide case member information
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | today-3000          | {random}    | Male               | today-50           || Spouse       |
#    Given I initiated get case member API for case ""
#    When I Update case member details using API
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           || Child        |
#    And I initiated get case member API for case ""
#    Then I verify updates for case member details using API


#  @case-consumer-api-CC @API-CRM-779 @API-CRM-779-03 @API-CRM-Regression @API-CC @Shruti    Retired
#  Scenario: Verify the update of DOB & SSN of  case member
#    Given I created a case required for validations
#    Given I initiated Create case member API
#    When I will get the Authentication token for "" in "CRM"
#    When I can provide case member information
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | today-15000         | {random}    | Male               | today-50           || Guardian     |
#    Given I initiated get case member API for case ""
#    When I Update case member details using API
#      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip |
#      | Member       | {random}          | {random}         | 1991-02-02          | {random}    | Male               | today-50           || Child        |
#    And I initiated get case member API for case ""
#    Then I verify updates for case member details using API


  @case-consumer-api-CC @API-CRM-779 @API-CRM-779-04 @API-CRM @API-CRM-Regression @API-CC @Shruti
  Scenario: Verify the status is updated for case member via API
    Given I created a case required for validations
    Given I initiated Create case member API
    When I will get the Authentication token for "" in "CRM"
    When I can provide case member information
      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | effectiveEndDate | caseId | relationShip |
      | Member       | {random}          | {random}         | today-9000          | {random}    | Male               | today              | today+50         |        | Child        |
    Given I initiated get case member API for case ""
    When I Update case member details using API
      | consumerRole | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | effectiveEndDate | caseId | relationShip | consumerStatus |
      | Member       | {update}          | {update}         | 1991-02-02          | {random}    | Male               | today-100          | today-90         |        | Child        | Inactive       |
    And I initiated get case member API for case ""
    Then I verify status for case member details using API


##need to implement
##  @API-CRM-779 @API-CRM-779-05 @API-CRM @API-CRM-Regression @API-Case @Shruti
#  Scenario: Verify the update On field is populated when case member is updated
#    Given I created a case required for validations
#    Given I initiated Create case member API
  # When I will get the Authentication token for "<projectName>" in "CRM"
#    When I can provide case member information
#    |consumerRole | consumerFirstName  | consumerLastName |consumerDateOfBirth| consumerSSN |consumerGenderCode |effectiveStartDate |caseId | relationShip  |
#    |Member       |    {random}        | {random}         |today-15000        |  {random}   | Male              |today-50           || Guardian      |
#    Given I initiated get case member API for case ""
  # When I will get the Authentication token for "<projectName>" in "CRM"
#    When I Update case member details using API
#    |consumerRole | consumerFirstName  | consumerLastName |consumerDateOfBirth| consumerSSN |consumerGenderCode |effectiveStartDate |caseId |relationShip |
#    |Member       |    {random}        | {random}         |today-9000         |  {random}   | Male              |today-50           || Child       |
#    And I initiated get case member API for case ""
#    Then I verify updated on fields is populated for case member via API


  @case-consumer-api-CC @API-CRM-755 @API-CRM-755-01 @shruti @API-CC @API-CRM-Regression
  Scenario Outline: Verify Primary Individual status
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated create case loader API
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    Given I initiated Create case member API
    When I provide primary individual information "<consumerFirstName>" "<consumerLastName>" "<effectiveStartDate>" "<effectiveEndDate>" "<caseId>"
    And I initiated get "<consumerRole>" from API for "<caseId>"
    Then I can verify "<consumerRole>" is created on API response with "<status>"
    Examples:
      | consumerRole | consumerFirstName | consumerLastName | effectiveStartDate | effectiveEndDate | caseId | status   | projectName |
      | Member       | {random}          | {random}         | past               | past             |        | Inactive |             |
      | Member       | {random}          | {random}         | future             | future           |        | Inactive |             |
      | Member       | {random}          | {random}         | past               | future           |        | Active   |             |

  @case-consumer-api-CC @API-CRM-754-01 @API-CRM-754 @API-CRM-Regression @API-CC @shruti
  Scenario: Verify Primary Individual active records are fetched folowed by inactive
    Given I initiated create case loader API
    When I will get the Authentication token for "" in "CRM"
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    Given I initiated Create case member API
    When I provide primary individual information "{random}" "{random}" "past" "past" ""
    When I provide primary individual information "{random}" "{random}" "future" "future" ""
    When I provide primary individual information "{random}" "{random}" "past" "future" ""
    And I initiated get "Primary Individual" from API for ""
    Then I verify active case members fetched followed by inactive for consumerRole "Primary Individual" using API



   #need to leverage the exisiting createcasesonsumer json and pass the Primary Individual details
  @case-consumer-api-CC @API-CRM-756 @API-CRM-756-01 @API-CRM-Regression @API-CC @shruti @0327201901
  Scenario Outline: Verify the update of first name & last name of  PI
    Given I will get the Authentication token for "" in "CRM"
    When I initiated create case loader API
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    And I initiated get "<consumerRole>" from API for "<caseId>"
    And I Update primary Individual  using API
      | consumerFirstName | consumerLastName | effectiveStartDate | effectiveEndDate | caseId |
      | {update}          | {update}         | today-50           | today            |        |
    And I initiated get "<consumerRole>" from API for "<caseId>"
    Then I verify updates for primary Individual using API
    Examples:
      | consumerFirstName | consumerLastName | effectiveStartDate | effectiveEndDate | caseId |
      | {random}          | {random}         | today-50           | today            |        |

  @case-consumer-api-CC @API-CRM-27638 @API-CRM-27638-01 @API-CRM-Regression @API-CC @Beka
  Scenario: Pass multiple addresses, phones and emails in CaseLoader
    Given I initiated create case loader API
    When I will get the Authentication token for "" in "CRM"
    When I can provide case and consumer information to create a case
    And I can run create case loader API
    Given I initiated get case member API for case ""
    Then I verify case consumer created