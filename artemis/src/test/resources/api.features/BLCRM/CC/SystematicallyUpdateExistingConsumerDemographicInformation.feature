Feature: Systematically update existing consumer demographic information

  @API-CP-97 @API-CP-97-1 @umid @API-CC @API-CRM-Regression
  Scenario: Capture Exact Match Comparison and Update
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc01" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc01.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc02" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc01.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc01.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc01.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc01.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc01.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc01.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API for second run

  @API-CP-97 @API-CP-97-2 @umid @API-CC @API-CRM-Regression
  Scenario: Capture Update of the parameters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc3" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc3.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc03" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc3.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc3.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc3.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc3.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc3.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                     | Chief                                                                                         |
      | caseLoaderRequest[0].case.consumers[0].consumerMiddleName                                 | L                                                                                             |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                     | III                                                                                           |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                         | Neutral                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                      | UNKNOWN                                                                                          |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                           | UNKNOWN                                                                                          |
      | caseLoaderRequest[0].case.consumers[0].citizenship                                        | Citizen                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].usResidentStatusCode                               | Resident                                                                                      |
      | caseLoaderRequest[0].case.consumers[0].consumerDateOfBirth                                | 2016-06-01                                                                                    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeath                                        | 2019-02-01                                                                                    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedDate                            | 2019-03-01                                                                                    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedBy                              | Office of Vital statistics                                                                    |
      | caseLoaderRequest[0].case.consumers[0].aiNa                                               | true                                                                                          |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                       | Child                                                                                         |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cc3.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
    And I verify values updated

  @API-CP-97 @API-CP-97-3 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer Name Suffix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc4" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc4.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc04" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc4.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc4.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc4.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc4.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc4.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                     | <Suffix>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc4.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "suffix" is updated "<Suffix>"
    Examples:
      | Suffix |
      | II     |
      | III    |
      | IV     |
      | V      |
      | DDS    |
      | ESQ    |
      | JD     |
      | Jr     |
      | MD     |
      | PhD    |
      | RN     |
      | Sr     |

  @API-CP-97 @API-CP-97-4 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with null for Incoming Consumer Name Suffix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc5" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc5.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc05" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc5.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc5.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc5.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc5.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc5.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                     | <Suffix>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc5.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I will see consumer record successfully updated with null value for "Suffix" field
    Examples:
      | Suffix   |
      | null::   |
      | asdgdsad |
      |[blank]|

  @API-CP-97 @API-CP-97-5 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record prefix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc6" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc6.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc06" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc6.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc6.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc6.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc6.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc6.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                     | <prefix>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc6.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "prefix" is updated "<prefix>"
    Examples:
      | prefix |
      | Atty   |
      | Chief  |
      | Dean   |
      | Dr     |
      | Hon    |
      | Mr     |
      | Mrs    |
      | Ms     |
      | Miss   |
      | Prof   |
      | Rev    |

  @API-CP-97 @API-CP-97-6 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record prefix to null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc1" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | Dr              |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc1.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc2" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc1.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc1.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc1.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc1.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc1.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                     | <prefix>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc2.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I will see consumer record successfully updated with null value for "prefix" field
    Examples:
      | prefix   |
      | null::   |
      | asdgdsad |
      |[blank]|

  @API-CP-97 @API-CP-97-7 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer Gender Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc7" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | Dr              |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc7.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc07" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc7.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc7.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc7.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc7.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc7.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                         | <Gender>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc7.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "Gender" is updated "<Gender>"
    Examples:
      | Gender  |
      | Male    |
      | Female  |
      | Neutral |
      | UNKNOWN |
      | Other   |

  @API-CP-97 @API-CP-97-8 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Invalid Values for Incoming Consumer Gender Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc8" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | Dr              |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc8.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc08" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc8.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc8.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc8.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc8.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc8.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                         | <Gender>                                                                                      |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc8.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I will see consumer record successfully updated with null value for "Gender" field
    Examples:
      | Gender       |
      | Invalid      |
      | randomString |

  @API-CP-97 @API-CP-97-9 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record from Valid Values to NULL and empty String
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc9" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                     | Dr              |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                           | raceCode::      |
      | caseLoaderRequest[0].case.consumers[0].citizenship                                        | Citizen         |
      | caseLoaderRequest[0].case.consumers[0].usResidentStatusCode                               | Resident        |
      | caseLoaderRequest[0].case.consumers[0].consumerDateOfBirth                                | 2016-06-01      |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                       | Child           |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc9.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc09" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc9.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc9.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                     | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].consumerMiddleName                                 | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                     | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].aiNa                                               | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeath                                        | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedDate                            | <value>                                                                                       |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedBy                              | <value>                                                                                       |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc9.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I see list of parameters that remain as per initial save
    Examples:
      | value |
      | null  |
      |[blank]|

  @API-CP-97 @API-CP-97-10 @umid
  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer Ethnicity Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc10" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc010" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc10.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc10.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc10.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc10.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                      | <Ethnicity>                                                                                    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "Ethnicity" is updated "<Ethnicity>"
    Examples:
      | Ethnicity              |
      | Hispanic or Latino     |
      | Not Hispanic or Latino |
      | UNKNOWN                |

  @API-CP-97 @API-CP-97-11 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Invalid Values for Incoming Consumer Ethnicity Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc10" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc010" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc10.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc10.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc10.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc10.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                      | <Ethnicity>                                                                                    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc10.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I will see consumer record successfully updated with null value for "Ethnicity" field
    Examples:
      | Ethnicity      |
      | Invalid::      |
      | randomString:: |
      |[blank]|

  @API-CP-97 @API-CP-97-12 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer Race Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc11" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc11.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc011" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc11.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc11.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc11.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc11.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc11.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                           | <Race>                                                                                         |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc11.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "Race" is updated "<Race>"
    Examples:
      | Race                                      |
      | American Indian or Alaska Native          |
      | Asian                                     |
      | Black or African American                 |
      | Native Hawaiian or Other Pacific Islander |
      | Other Race                                |
      | White                                     |
      | More than one race                        |
      | UNKNOWN                                   |

  @API-CP-97 @API-CP-97-13 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Invalid Values for Incoming Consumer Race Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc12" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc12.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc012" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc12.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc12.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc12.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc11.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc11.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                           | <Race>                                                                                         |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc12.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I will see consumer record successfully updated with null value for "Race" field
    Examples:
      | Race           |
      | Invalid::      |
      | randomString:: |
      |[blank]|

#  @API-CP-97 @API-CP-97-14 @umid @API-CC @API-CRM-Regression #failing not updating role
#  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer Role
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    Given I initiated Case Loader API request
#    And User send Api call with name "cc13" payload "apiCaseLoader" for CaseLoader
#      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
#      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
#      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
#      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
#      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
#      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
#    Then I initiate the Consumer search API
#    And I send API call for searching the Consumer With
#      | consumerLastName | cc13.caseLoaderRequest[0].case.consumers[0].consumerLastName |
#    And I map infos from Consumer Search API
#    Given I initiated Case Loader API request
#    And User send Api call with name "cc013" payload "apiCaseLoader" for CaseLoader
#      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc13.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
#      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc13.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
#      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc13.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
#      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc13.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
#      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc13.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
#      | caseLoaderRequest[0].case.consumers[0].consumerRole                                       | <Role>                                                                                         |
#    Then I initiate the Consumer search API
#    And I send API call for searching the Consumer With
#      | consumerLastName | cc13.caseLoaderRequest[0].case.consumers[0].consumerLastName |
#    And I verify "Role" is updated "<Role>"
#    Examples:
#      | Role                      |
#      | Primary Individual        |
#      | Member                    |
#      | Authorized Representative |

  @API-CP-97 @API-CP-97-15 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Invalid Values for Incoming Consumer Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc14" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc14.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc014" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc14.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc14.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc14.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc14.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc14.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerRole                                       | <Role>                                                                                         |
    Then Response will contain an error "Invalid consumer role found" and record will not be saved
    Examples:
      | Role           |
      | UNKNOWN        |
      | randomString:: |

  @API-CP-97 @API-CP-97-16 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Updating a Consumer record with Valid Values for Incoming Consumer relationShip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc15" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc15.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc015" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc15.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc15.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc15.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc15.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc15.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                       | <relationShip>                                                                                 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc15.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify "relationShip" is updated "<relationShip>"
    Examples:
      | relationShip |
      | Child        |
      | Spouse       |
      | Guardian     |
      | UNKNOWN      |

  @API-CP-97 @API-CP-97-17 @umid @API-CC @API-CRM-Regression #failing and updating instead of returning an error message
  Scenario: Updating a Consumer record with Invalid Values for Incoming Consumer relationShip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc16" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc16.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc016" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc16.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc16.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc16.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc16.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc16.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                       | lkgigkjhkjgf                                                                                   |
    Then Response will contain an error "Invalid consumer relationship found" and record will not be saved


  @API-CP-97 @API-CP-97-18 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Update Preferred Spoken/Written Language Field
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc17" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc17.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc018" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc17.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc17.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc17.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc17.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc17.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | <writtenLanguage>                                                                              |
      | caseLoaderRequest[0].case.consumers[0].spokenLanguage                                     | <spokenLanguage>                                                                               |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc17.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify languages updated "<writtenLanguage>" and "<spokenLanguage>"
    Examples:
      | writtenLanguage | spokenLanguage |
      | English         | English        |
      | Russian         | Russian        |
      | Vietnamese      | Vietnamese     |
      | Spanish         | Spanish        |
      | Other           | Other          |

  @API-CP-97 @API-CP-97-19 @umid @API-CC @API-CRM-Regression
  Scenario Outline: Update Preferred Spoken/Written Language Field to English when no valid parameter provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc18" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc18.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc018" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc18.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc18.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc18.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc18.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc18.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | <writtenLanguage>                                                                              |
      | caseLoaderRequest[0].case.consumers[0].spokenLanguage                                     | <spokenLanguage>                                                                               |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc18.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify languages updated "English" and "English"
    Examples:
      | writtenLanguage | spokenLanguage |
      | Unknown         | Unknown        |
      | randomString    | randomString   |

  @API-CP-97 @API-CP-97-20 @umid @API-CC @API-CRM-Regression
  Scenario: Record Update Date and Updated By fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc19" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc19.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I map infos from Consumer Search API
    Given I initiated Case Loader API request
    And User send Api call with name "cc019" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | cc19.caseLoaderRequest[0].case.consumers[0].consumerSSN                                        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | cc19.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | cc19.caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | cc19.caseLoaderRequest[0].case.consumers[0].consumerLastName                                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | cc19.caseLoaderRequest[0].case.caseIdentificationNumber                                        |
      | caseLoaderRequest[0].case.requestedBy                                                     | MK-Postman                                                                                    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc18.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    And I verify updated bys "MK-Postman" and "<updatedOn>"
