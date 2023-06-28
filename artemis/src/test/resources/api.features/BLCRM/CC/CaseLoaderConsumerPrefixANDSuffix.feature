Feature: All Tenants | Revise Handling for Invalid/Null Value in Consumer Prefix/Suffix


  @API-CP-24551 @API-CP-24551-01 @API-CC @API-CRM-Regression @Beka
  Scenario: Leave Prefix or Suffix Blank When Invalid Value Is Passed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | notvalid        |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | notvalid        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate record saved with "Suffix" as null
    Then I validate record saved with "Prefix" as null

  @API-CP-24551 @API-CP-24551-02 @API-CC @API-CRM-Regression @Beka
  Scenario: Leave Prefix or Suffix Blank When Null Value Is Passed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | null::          |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | null::          |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate record saved with "Suffix" as null
    Then I validate record saved with "Prefix" as null
