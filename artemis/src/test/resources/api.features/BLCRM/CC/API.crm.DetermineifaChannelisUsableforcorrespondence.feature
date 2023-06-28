Feature: Determine if a Channel is Usable for correspondence: Determine priority of unusableReasons for CP-9941 and Cp-9942


  @API-CP-9941 @API-CP-9941-01 @API-CC @API-CRM-Regression @Beka
  Scenario: Determine Usable Channels: Text
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc100" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::          |

    And I send request for case correspondence API with just created ID
      | consumerLastName | cc100.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | Paperless Preference        |
      | emailUsableFlag     | true                        |
      | emailUnusableReason | null                        |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | true                        |
      | textUnusableReason  | null                        |


  @API-CP-9941 @API-CP-9941-02 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response with
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc101" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::          |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc101.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | true                        |
      | mailUnusableReason  | null                        |
      | emailUsableFlag     | true                        |
      | emailUnusableReason | null                        |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | true                        |
      | textUnusableReason  | null                        |


  @API-CP-9941 @API-CP-9941-03 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response with all optIn is false
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc102" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::          |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc102.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | Consumer opted out          |
      | emailUsableFlag     | false                       |
      | emailUnusableReason | Consumer opted out          |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | false                       |
      | textUnusableReason  | Consumer opted out          |

  @API-CP-9941 @API-CP-9941-04 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response with all contacts is inactive and OptIn is true
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc103" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.effectiveStartDate                  | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.effectiveEndDate                    | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.effectiveStartDate                  | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.effectiveEndDate                    | 2021-05-08T14:05:31.799Z |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc103.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | No active destination found |
      | emailUsableFlag     | false                       |
      | emailUnusableReason | No active destination found |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | false                       |
      | textUnusableReason  | No active destination found |

  @API-CP-9941 @API-CP-9941-05 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response with no contacts at all and OptIn is true
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc1040" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::          |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::          |
      | caseLoaderRequest[0].case.consumers[0].contacts.address                                   | null::          |
      | caseLoaderRequest[0].case.consumers[0].contacts.email                                     | null::          |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone                                     | null::          |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc1040.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | No active destination found |
      | emailUsableFlag     | false                       |
      | emailUnusableReason | No active destination found |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | false                       |
      | textUnusableReason  | No active destination found |

  @API-CP-9941 @API-CP-9941-06 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response with Fax contact is inactivate
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc104" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.effectiveStartDate                  | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.effectiveEndDate                    | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.effectiveStartDate                  | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.effectiveEndDate                    | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | Fax                      |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc104.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | No active destination found |
      | emailUsableFlag     | false                       |
      | emailUnusableReason | No active destination found |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | false                       |
      | textUnusableReason  | No active destination found |

  @API-CP-9942 @API-CP-9942-01 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response No active destination found when no active address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc105" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc105.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for
      | mailUsableFlag      | false                       |
      | mailUnusableReason  | No active destination found |
      | emailUsableFlag     | true                        |
      | emailUnusableReason | null                        |
      | faxUsableFlag       | false                       |
      | faxUnusableReason   | No active destination found |
      | textUsableFlag      | true                        |
      | textUnusableReason  | null                        |

  @API-CP-956 @API-CP-956-01 @API-CC @API-CRM-Regression @Beka
  Scenario: Standard Response Provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send a requast to caseCorespondens API with ConsumerID "46984"
    Then Verify response contains following info
      | consumerId               | 46984                       |
      | firstName                | AbbigaildlDzz               |
      | lastName                 | ChamplingwoGW               |
      | defaultFlag              | true                        |
      | preferredWrittenLanguage | ENGLISH                     |
      | mailUnusableReason       | No active destination found |
      | emailUsableFlag          | false                       |

  @API-CP-956 @API-CP-956-02 @API-CC @API-CRM-Regression @Beka
  Scenario: Alternative Responses - consumer not exist
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send a requast to caseCorespondens API with ConsumerID "989898"
    Then I get "404" response from server

  @API-CP-956 @API-CP-956-03 @API-CC @API-CRM-Regression @Beka
  Scenario: Alternative Responses - no Mailing Address for consumer at all
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And I send a requast to caseCorespondens API with ConsumerID "88719"
    Then Verify response contains following info when consumer not have mailing address
      | mailUnusableReason | No active destination found |
      | emailUsableFlag    | false                       |

  @API-CP-956 @API-CP-956-04 @API-CC @API-CRM-Regression @Beka
  Scenario: Alternative Responses - no Mailing Address for consumer profile one or more Inactivate
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And I send a requast to caseCorespondens API with ConsumerID "44840"
    Then Verify response contains following info when consumer not have mailing address
      | mailUnusableReason | No active destination found |
      | emailUsableFlag    | false                       |

  @API-CP-956 @API-CP-956-05 @API-CC @API-CRM-Regression @Beka
  Scenario: Alternative Responses -consumer profile only
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And I send a requast to caseCorespondens API with ConsumerID "44840"
    Then Verify response contains following info when consumer not have mailing address
      | mailUnusableReason | No active destination found |
      | emailUsableFlag    | false                       |
      | consumerRole       | null                        |

  @API-CP-14841 @API-CP-14841-01 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains proper writtenLanguage
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc106" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | SPANISH                  |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc106.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "SPANISH"

  @API-CP-14841 @API-CP-14841-02 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains English when writtenLanguage is empty string
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc107" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    |[blank]|
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc107.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "ENGLISH"

  @API-CP-14841 @API-CP-14841-03 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains English when writtenLanguage is NULL
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc108" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | null::                   |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc108.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "ENGLISH"

  @API-CP-14841 @API-CP-14841-04 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains proper writtenLanguage RUSSIAN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc109" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | RUSSIAN                  |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc109.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "RUSSIAN"

  @API-CP-14841 @API-CP-14841-05 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains proper writtenLanguage VIETNAMESE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc110" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | VIETNAMESE               |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc110.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "VIETNAMESE"

  @API-CP-14841 @API-CP-14841-06 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify response contains proper writtenLanguage OTHER
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc111" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::                    |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::                   |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7          |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | true::                   |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::                   |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveStartDate                | 2019-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.effectiveEndDate                  | 2021-05-08T14:05:31.799Z |
      | caseLoaderRequest[0].case.consumers[0].writtenLanguage                                    | OTHER                    |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc111.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains writtenLanguage as "OTHER"

  @API-CP-29153 @API-CP-29153-01 @API-CC @API-CRM-Regression @Beka
  Scenario: verify if Paperless - Include Active contacts
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc112" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::          |
      | caseLoaderRequest[0].case.consumers[0].correspondencePreference                           | Paperless       |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc112.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for if Consumer opted out
      | mailUnusableReason | Paperless Preference |
      | addresses          | not NULL             |

  @API-CP-29153 @API-CP-29153-02 @API-CC @API-CRM-Regression @Beka
  Scenario: verify if Consumer opted out - Include Active contacts
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cc113" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | randomstring::7 |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[0].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[1].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[2].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[3].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].consumerConsent[4].optIn                           | false::         |
      | caseLoaderRequest[0].case.consumers[0].communicationPreferences                           | null::          |
      | caseLoaderRequest[0].case.consumers[0].correspondencePreference                           | null::          |
    And I send request for case correspondence API with just created ID
      | consumerLastName | cc113.caseLoaderRequest[0].case.consumers[0].consumerLastName |
    Then I validate response channels object contains following data for if Consumer opted out
      | mailUnusableReason  | Consumer opted out |
      | addresses           | not NULL           |
      | emailUnusableReason | Consumer opted out |
      | emailAddresses      | not NULL           |
      | textUnusableReason  | Consumer opted out |
      | phoneNumbers        | not NULL           |
