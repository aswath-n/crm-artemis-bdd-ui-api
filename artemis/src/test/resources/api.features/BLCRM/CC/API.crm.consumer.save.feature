Feature: Systematically Create Consumer Record

  #  @API-CP-63 @API-CP-63-01 @API-CC @API-CRM-Regression @muhabbat functionality changed with CP-22349 implementation
  Scenario: Capture Required Information - FN missing
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | null::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerLastName | cc.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate record saved with "FirstName" as "NO_FIRST_NAME"


  #  @API-CP-63 @API-CP-63-02 @API-CC @API-CRM-Regression @muhabbat functionality changed with CP-22349 implementation
  Scenario: Capture Required Information - LN missing
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc1" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | null::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cc1.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
    Then I validate record saved with "LastName" as "NO_LAST_NAME"


  #  @API-CP-63 @API-CP-63-03 @API-CC @API-CRM-Regression @muhabbat functionality changed with CP-22349 implementation
  Scenario: Required Information No First Name nor Last Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc2" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | null::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | null::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    Then Caseloder Response will contain an error "Missing First and Last Name" and record will not be saved
    Then I initiate the Consumer search API
    And I send API call for quick searching the Consumer With
      | consumerIdentificationNo | cc2.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       |
      | consumerType             | cc2.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType |
    Then I validate Case Consumer record has not been saved


  @API-CP-63 @API-CP-63-04 @API-CC @API-CRM-Regression @muhabbat
  Scenario: Default Capturing Consumer Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc3" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP            |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerType                                             | null:           |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cc3.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cc3.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then Case Loader service will set the Consumer Type to Consumer

  @API-CP-63 @API-CP-63-04 @API-CC @API-CRM-Regression @muhabbat
  Scenario: Creating a Consumer record - Not Required fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc4" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
    # non mandatory fields for consumer to be created
      | caseLoaderRequest[0].case.consumers[0].consumerMiddleName                                       | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | null::    |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                               | null::    |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                            | null::    |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                                 | null::    |
      | caseLoaderRequest[0].case.consumers[0].usResidentStatusCode                                     | null::    |
      | caseLoaderRequest[0].case.consumers[0].citizenship                                              | null::    |
      | caseLoaderRequest[0].case.consumers[0].aiNa                                                     | null::    |
      | caseLoaderRequest[0].case.consumers[0].consumerDateOfBirth                                      | null::    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeath                                              | null::    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedBy                                    | null::    |
      | caseLoaderRequest[0].case.consumers[0].dateOfDeathNotifiedDate                                  | null::    |
      | caseLoaderRequest[0].case.consumers[0].pregnancyInd                                             | null::    |
      | caseLoaderRequest[0].case.consumers[0].pregnancyDueDate                                         | null::    |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                             | null::    |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                          | null::    |
      | caseLoaderRequest[0].case.consumers[0].spokenLanguage                                           | null::    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cc4.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cc4.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I validate Case Consumer record has been created "cc4"


  @API-CP-63 @API-CP-63-05 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Name Prefix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid  |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerPrefix                                           | <prefix>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<prefix>" value for "consumerPrefix" field for "<callName>"
    Examples:
      | prefix | callName |
      | Atty   | cc5      |
      | Chief  | cc6      |
      | Dean   | cc7      |
      | Dr     | cc8      |
      | Hon    | cc9      |
      | Mr     | cc10     |
      | Mrs    | cc11     |
      | Ms     | cc12     |
      | Miss   | cc13     |
      | Prof   | cc14     |
      | Rev    | cc15     |


  @API-CP-63 @API-CP-63-06 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Name Prefix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | <prefix>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with null value for "consumerPrefix" field for "<callName>"
    Examples:
      | prefix       | callName |
      | 123          | cc16     |
      | randomString | cc17     |
      |[blank]| cc18     |


  @API-CP-63 @API-CP-63-07 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Name Suffix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid  |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | <suffix>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<suffix>" value for "consumerSuffix" field for "<callName>"
    Examples:
      | suffix | callName |
      | II     | cc19     |
      | III    | cc20     |
      | IV     | cc21     |
      | V      | cc22     |
      | DDS    | cc23     |
      | ESQ    | cc24     |
      | JD     | cc25     |
      | Jr     | cc26     |
      | MD     | cc27     |
      | PhD    | cc28     |
      | RN     | cc29     |
      | Sr     | cc30     |

  @API-CP-63 @API-CP-63-08 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Name Suffix
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerSuffix                                           | <suffix>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with null value for "<consumerSuffix>" field for "<callName>"
    Examples:
      | suffix       | callName |
      | 123          | cc31     |
      | randomString | cc32     |
      |[blank]| cc33     |


  @API-CP-63 @API-CP-63-09 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Gender Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid  |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                               | <gender>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<gender>" value for "genderCode" field for "<callName>"
    Examples:
      | gender  | callName |
      | Male    | cc34     |
      | Female  | cc35     |
      | Neutral | cc36     |
      | UNKNOWN | cc37     |
      | Other   | cc38     |


  @API-CP-63 @API-CP-63-10 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Gender Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::8 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].genderCode                                               | <gender>  |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "UNKNOWN" value for "genderCode" field for "<callName>"
    Examples:
      | gender       | callName |
      | Invalid      | cc39     |
      | randomString | cc40     |

  @API-CP-63 @API-CP-63-11 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Ethnicity Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::      |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP        |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9   |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                           | <ethnicity> |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<ethnicity>" value for "ethnicityCode" field for "<callName>"
    Examples:
      | ethnicity              | callName |
      | Hispanic or Latino     | cc41     |
      | Not Hispanic or Latino | cc42     |
      | UNKNOWN                | cc43     |

  @API-CP-63 @API-CP-63-12 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Ethnicity Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::      |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP        |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9   |
      | caseLoaderRequest[0].case.consumers[0].ethnicityCode                                           | <ethnicity> |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "UNKNOWN" value for "ethnicityCode" field for "<callName>"
    Examples:
      | ethnicity    | callName |
      | Invalid      | cc44     |
      | randomString | cc45     |

  @API-CP-63 @API-CP-63-13 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Race Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid  |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                                 | <race>    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<race>" value for "raceCode" field for "<callName>"
    Examples:
      | race                                      | callName |
      | American Indian or Alaska Native          | cc46     |
      | Asian                                     | cc47     |
      | Black or African American                 | cc48     |
      | Native Hawaiian or Other Pacific Islander | cc49     |
      | Other Race                                | cc50     |
      | White                                     | cc51     |
      | More than one race                        | cc52     |
      | UNKNOWN                                   | cc53     |


  @API-CP-63 @API-CP-63-14 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Race Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].raceCode                                                 | <race>    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "UNKNOWN" value for "raceCode" field for "<callName>"
    Examples:
      | race         | callName |
      | Invalid      | cc54     |
      | randomString | cc55     |


  @API-CP-63 @API-CP-63-15 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerRole                                             | <role>    |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<role>" value for "consumerRole" field for "<callName>"
    Examples:
      | role                      | callName |
      | Primary Individual        | cc56     |
      | Member                    | cc57     |
      | Authorized Representative | cc58     |

  @API-CP-63 @API-CP-63-16 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].consumerRole                                             | <role>    |
    Then Caseloder Response will contain an error "Invalid consumer role found" and record will not be saved
    Then I initiate the Consumer search API
    And I send API call for quick searching the Consumer With
      | consumerIdentificationNo | <callName>.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       |
      | consumerType             | <callName>.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType |
    Then I validate Case Consumer record has not been saved
    Examples:
      | role         | callName |
      | Unknown      | cc59     |
      | randomString | cc60     |
      | null         | cc61     |
      | ""           | cc62     |

  @API-CP-63 @API-CP-63-17 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Valid Values for Incoming Consumer relationShip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::         |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::         |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP           |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9      |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                             | <relationship> |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<relationship>" value for "relationShip" field for "<callName>"
    Examples:
      | relationship | callName |
      | Child        | cc63     |
      | Spouse       | cc64     |
      | Guardian     | cc65     |

  @API-CP-63 @API-CP-63-18 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Creating a Consumer record with Invalid Values for Incoming Consumer relationShip
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::         |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::         |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP           |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9      |
      | caseLoaderRequest[0].case.consumers[0].relationShip                                             | <relationship> |
    Then Caseloder Response will contain an error "Invalid consumer relationship found" and record will not be saved
    Then I initiate the Consumer search API
    And I send API call for quick searching the Consumer With
      | consumerIdentificationNo | <callName>.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       |
      | consumerType             | <callName>.caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType |
    Then I validate Case Consumer record has not been saved
    Examples:
      | relationship | callName |
#      | Unknown      | cc66     |
      | randomString | cc67     |


  @API-CP-63 @API-CP-63-19 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Capture Preferred Spoken/Written Language Field When Provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::8 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].spokenLanguage                                           | <spoken>  |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                          | <written> |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "<written>" "<spoken>" value for languages fields for "<callName>"
    Examples:
      | written    | spoken     | callName |
      | ENGLISH    | ENGLISH    | cc68     |
      | RUSSIAN    | RUSSIAN    | cc69     |
      | VIETNAMESE | VIETNAMESE | cc70     |
      | SPANISH    | SPANISH    | cc71     |


  @API-CP-63 @API-CP-63-20 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: : Default Preferred Spoken/Written Language Field to English When Not Provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::    |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | random::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | CHIP      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | random::9 |
      | caseLoaderRequest[0].case.consumers[0].spokenLanguage                                           | <spoken>  |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                          | <written> |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I will see consumer record successfully created with "ENGLISH" "ENGLISH" value for languages fields for "<callName>"
    Examples:
      | written      | spoken       | callName |
      | Unknown      | Unknown      | cc73     |
      | randomString | randomString | cc74     |
      | null         | null         | cc75     |
      | Japanese     | Farsi        | cc76     |
      | ""           | ""           | cc77     |


#  @CP-63-21
#  Scenario: Publish an event for DPBI to consume when systematically creating a Consumer record
#    Given  I systematically create a new consumer and pass filed
#    Then I will publish an event for DPBI to consume for reporting "CONSUMER_SAVE_EVENT"


#  @CP-63-22

#  Scenario: Record Create Date and Created By fields
#    Given  I systematically create a new consumer and pass filed
#    Then I will record the date the record was created
#    Then I will record the process which created the record

  @test
  Scenario:Step defs for utilities
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I send API CALL for Create CaseConsumer
