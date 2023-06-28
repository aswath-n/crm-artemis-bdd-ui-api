Feature: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario)

  @API-CP-30875 @API-CP-29755 @API-CP-29755-01 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 1.1 1.2
  1.1 Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (no Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-1.consumerId |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | programCode           | H                  |
      | subProgramTypeCode    | HealthyIndianaPlan |
      | genericFieldText1     | Eligible           |
      | fileSource            | HRECIP             |
    Then I send API call with name "29755-1a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29755-1.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | false    |
    Then I will verify business events are generated with data
      | eventName                                | RECONCILIATION                                          |
      | externalConsumerId                       | 29755-1.externalConsumerId                              |
      | correlationId                            | 29755-1a.eligibilities[0].coreEligibility.correlationId |
      | channel                                  | SYSTEM_INTEGRATION                                      |
      | createdBy                                | 597                                                     |
      | processDate                              | current                                                 |
      | externalCaseId                           | 29755-1.caseIdentificationNumber                        |
      | consumerId                               | 29755-1.consumerId                                      |
      | consumerName                             | 29755-1                                                 |
      | reconciliationAction                     | No Eligibility Segment                                  |
      # Business Event Optional Fields
      | eligibilityStartDate                     | null                                                    |
      | eligibilityEndDate                       | null                                                    |
      | eligibilityEndReason                     | null                                                    |
      | eligibilityCategoryCode                  | null                                                    |
      | eligibilityProgramCode                   | null                                                    |
      | eligibilitySubProgramCode                | null                                                    |
      | eligibilityCoverageCode                  | null                                                    |
      | eligibilityExemptionCode                 | null                                                    |
      | consumerPopulation                       | null                                                    |
      | benefitStatus                            | null                                                    |
      | additionalFileds.noEligSegment.hipStatus | Eligible                                                |
    # 1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "29755-1b" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29755-1.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | true     |
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                                          |
      | externalConsumerId                           | 29755-1.externalConsumerId                              |
      | correlationId                                | 29755-1b.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                      |
      | createdBy                                    | 597                                                     |
      | processDate                                  | current                                                 |
      | externalCaseId                               | 29755-1.caseIdentificationNumber                        |
      | consumerId                                   | 29755-1.consumerId                                      |
      | consumerName                                 | 29755-1                                                 |
      | reconciliationAction                         | Eligibility Created                                     |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                           | highDate                                                |
      | eligibilityEndReason                         | null                                                    |
      | eligibilityCategoryCode                      | 10                                                      |
      | eligibilityProgramCode                       | MEDICAID                                                |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                      |
      | eligibilityCoverageCode                      | cc01                                                    |
      | eligibilityExemptionCode                     | qwer                                                    |
      | consumerPopulation                           | HIP-Mandatory                                           |
      | benefitStatus                                | Benefit Status Error                                    |
      | additionalFileds.noMatch.HIP Status          | Eligible                                                |
      | additionalFileds.noMatch.Managed Care Status | M                                                       |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-02 @API-EE-IN @IN-EB-API-Regression @sobir @shruti
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 1.2
  1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | <fileSource>         |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "<name>.consumerId" with data
      | empty |[blank]|
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                                         |
      | externalConsumerId                           | <name>.externalConsumerId                              |
      | correlationId                                | <name>a.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                     |
      | createdBy                                    | 597                                                    |
      | processDate                                  | current                                                |
      | externalCaseId                               | <name>.caseIdentificationNumber                        |
      | consumerId                                   | <name>.consumerId                                      |
      | consumerName                                 | <name>                                                 |
      | reconciliationAction                         | Eligibility Created                                    |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth                                   |
      | eligibilityEndDate                           | highDate                                               |
      | eligibilityEndReason                         | null                                                   |
      | eligibilityCategoryCode                      | 10                                                     |
      | eligibilityProgramCode                       | MEDICAID                                               |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                     |
      | eligibilityCoverageCode                      | cc01                                                   |
      | eligibilityExemptionCode                     | qwer                                                   |
      | consumerPopulation                           | HIP-Mandatory                                          |
      | benefitStatus                                | Benefit Status Error                                   |
      | additionalFileds.noMatch.HIP Status          | null                                                   |
      | additionalFileds.noMatch.Managed Care Status | M                                                      |
    Examples: For both RECIPIENT and RECIPIENT_HRECIP files
      | name    | fileSource       | eligibilityEndDate |
      | 29755-2 | RECIPIENT        | highDate           |
      | 29755-3 | RECIPIENT_HRECIP | highDate           |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 1.3a
  1.3 Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-4.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Denied               |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 1                      |
      | segmentDetailValue2 | 6                      |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | OTHER                  |
    Then I send API call with name "29755-4a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29755-4.consumerId" with data
      | empty |[blank]|
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                                          |
      | externalConsumerId                           | 29755-4.externalConsumerId                              |
      | correlationId                                | 29755-4a.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                      |
      | createdBy                                    | 597                                                     |
      | processDate                                  | current                                                 |
      | externalCaseId                               | 29755-4.caseIdentificationNumber                        |
      | consumerId                                   | 29755-4.consumerId                                      |
      | consumerName                                 | 29755-4                                                 |
      | reconciliationAction                         | Eligibility Created                                     |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                           | highDate                                                |
      | eligibilityEndReason                         | null                                                    |
      | eligibilityCategoryCode                      | 10                                                      |
      | eligibilityProgramCode                       | MEDICAID                                                |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                      |
      | eligibilityCoverageCode                      | cc01                                                    |
      | eligibilityExemptionCode                     | qwer                                                    |
      | consumerPopulation                           | HIP-Mandatory                                           |
      | benefitStatus                                | Benefit Status Error                                    |
      | additionalFileds.noMatch.HIP Status          | Denied                                                  |
      | additionalFileds.noMatch.Managed Care Status | M                                                       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-4.consumerId |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | programCode           | H                  |
      | subProgramTypeCode    | HealthyIndianaPlan |
      | genericFieldText1     | Eligible           |
      | fileSource            | HRECIP             |
    Then I send API call with name "29755-4b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-4.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 455701400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    Then I verify hrecip eligibility information by consumerId "29755-4.consumerId" with data
      | empty |[blank]|
    And I initiated get benefit status by consumer id "29755-4.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Eligibility Updated","New Retroactive Enrollment"
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
    Then I will verify business events are generated with data
      | eventName                                               | RECONCILIATION                                          |
      | externalConsumerId                                      | 29755-4.externalConsumerId                              |
      | correlationId                                           | 29755-4b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                 | SYSTEM_INTEGRATION                                      |
      | createdBy                                               | 597                                                     |
      | processDate                                             | current                                                 |
      | externalCaseId                                          | 29755-4.caseIdentificationNumber                        |
      | consumerId                                              | 29755-4.consumerId                                      |
      | consumerName                                            | 29755-4                                                 |
      | reconciliationAction                                    | Eligibility Updated                                     |
      | consumerPopulation                                      | HIP-Mandatory                                           |
      # Business Event Optional Fields
      | oldValue                                                | null                                                    |
      | newValue.eligibilityStartDate                           | 1stDayofPresentMonth                                    |
      | newValue.eligibilityEndDate                             | highDate                                                |
      | newValue.eligibilityEndReason                           | null                                                    |
      | newValue.eligibilityCategoryCode                        | 10                                                      |
      | newValue.eligibilityProgramCode                         | MEDICAID                                                |
      | newValue.eligibilitySubProgramCode                      | HealthyIndianaPlan                                      |
      | newValue.eligibilityCoverageCode                        | cc01                                                    |
      | newValue.eligibilityExemptionCode                       | qwer                                                    |
      | newValue.benefitStatus                                  | Benefit Status Error                                    |
      | additionalFileds.eligibilityUpdated.HIP Status          | Eligible                                                |
      | additionalFileds.eligibilityUpdated.Managed Care Status | M                                                       |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 1.3b
  1.3 Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-5.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Denied               |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-5a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "29755-5.consumerId" with data
      | empty |[blank]|
    Then I will verify business events are generated with data
      | eventName                                    | RECONCILIATION                                          |
      | externalConsumerId                           | 29755-5.externalConsumerId                              |
      | correlationId                                | 29755-5a.eligibilities[0].coreEligibility.correlationId |
      | channel                                      | SYSTEM_INTEGRATION                                      |
      | createdBy                                    | 597                                                     |
      | processDate                                  | current                                                 |
      | externalCaseId                               | 29755-5.caseIdentificationNumber                        |
      | consumerId                                   | 29755-5.consumerId                                      |
      | consumerName                                 | 29755-5                                                 |
      | reconciliationAction                         | Eligibility Created                                     |
      # Business Event Optional Fields
      | eligibilityStartDate                         | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                           | highDate                                                |
      | eligibilityEndReason                         | null                                                    |
      | eligibilityCategoryCode                      | 10                                                      |
      | eligibilityProgramCode                       | MEDICAID                                                |
      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                      |
      | eligibilityCoverageCode                      | cc01                                                    |
      | eligibilityExemptionCode                     | qwer                                                    |
      | consumerPopulation                           | HIP-Mandatory                                           |
      | benefitStatus                                | Benefit Status Error                                    |
      | additionalFileds.noMatch.HIP Status          | Denied                                                  |
      | additionalFileds.noMatch.Managed Care Status | M                                                       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-5.consumerId |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | programCode           | H                  |
      | subProgramTypeCode    | HealthyIndianaPlan |
      | genericFieldText1     | Eligible           |
      | fileSource            | HRECIP             |
    Then I send API call with name "29755-5b" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-5.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentRecordId           | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | planCode                     | 455701400            |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | rejectionReason              | test1                |
    And I run create Eligibility and Enrollment API
    Then I verify hrecip eligibility information by consumerId "29755-5.consumerId" with data
      | empty |[blank]|
    And I initiated get benefit status by consumer id "29755-5.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Eligibility Updated","New Retroactive Enrollment"
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
    Then I will verify business events are generated with data
      | eventName                                                                                          | RECONCILIATION                                          |
      | externalConsumerId                                                                                 | 29755-5.externalConsumerId                              |
      | correlationId                                                                                      | 29755-5b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                                            | SYSTEM_INTEGRATION                                      |
      | createdBy                                                                                          | 597                                                     |
      | processDate                                                                                        | current                                                 |
      | externalCaseId                                                                                     | 29755-5.caseIdentificationNumber                        |
      | consumerId                                                                                         | 29755-5.consumerId                                      |
      | consumerName                                                                                       | 29755-5                                                 |
      | reconciliationAction                                                                               | Eligibility Updated                                     |
      | consumerPopulation                                                                                 | HIP-Mandatory                                           |
      # Business Event Optional Fields
      | oldValue                                                                                           | null                                                    |
      | newValue.eligibilityStartDate                                                                      | 1stDayofPresentMonth                                    |
      | newValue.eligibilityEndDate                                                                        | highDate                                                |
      | newValue.eligibilityEndReason                                                                      | null                                                    |
      | newValue.eligibilityCategoryCode                                                                   | 10                                                      |
      | newValue.eligibilityProgramCode                                                                    | MEDICAID                                                |
      | newValue.eligibilitySubProgramCode                                                                 | HealthyIndianaPlan                                      |
      | newValue.eligibilityCoverageCode                                                                   | cc01                                                    |
      | newValue.eligibilityExemptionCode                                                                  | qwer                                                    |
      | newValue.benefitStatus                                                                             | Benefit Status Error                                    |
      | newValue.additionalFileds.eligibilityUpdated.HIP Status                                            | Eligible                                                |
      | newValue.additionalFileds.eligibilityUpdated.Managed Care Status                                   | M                                                       |
      | newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                       |
      | newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                |
      | newValue.additionalFileds.eligibilityUpdated.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                    |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 1.4
  1.4 Inbound MRT Aid-Category Segment (ADDED) for Consumer WITHOUT Eligibility Segment (BASE)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-6.consumerId |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | genericFieldText1     | Eligible           |
      | fileSource            | RECIPIENT          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | MRT                    |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-6a" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-6.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-6.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | MRT                  |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName                                                            | RECONCILIATION                                          |
      | externalConsumerId                                                   | 29755-6.externalConsumerId                              |
      | correlationId                                                        | 29755-6a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                              | SYSTEM_INTEGRATION                                      |
      | createdBy                                                            | 597                                                     |
      | processDate                                                          | current                                                 |
      | externalCaseId                                                       | 29755-6.caseIdentificationNumber                        |
      | consumerId                                                           | 29755-6.consumerId                                      |
      | consumerName                                                         | 29755-6                                                 |
      | reconciliationAction                                                 | No Eligibility Segment                                  |
      | consumerPopulation                                                   | null                                                    |
      # Business Event Optional Fields
      | eligibilityStartDate                                                 | null                                                    |
      | eligibilityEndDate                                                   | null                                                    |
      | eligibilityEndReason                                                 | null                                                    |
      | eligibilityCategoryCode                                              | null                                                    |
      | eligibilityProgramCode                                               | null                                                    |
      | eligibilitySubProgramCode                                            | null                                                    |
      | eligibilityCoverageCode                                              | null                                                    |
      | eligibilityExemptionCode                                             | null                                                    |
      | benefitStatus                                                        | null                                                    |
      | additionalFileds.noEligSegment.Added Eligibility Category Code       | MRT                                                     |
      | additionalFileds.noEligSegment.Added Eligibility Category End Date   | highDate                                                |
      | additionalFileds.noEligSegment.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-6.consumerId |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | genericFieldText1     | Eligible           |
      | fileSource            | RECIPIENT          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth   |
      | endDate             | highDate            |
      | segmentDetailValue1 | MRT                 |
      | segmentDetailValue2 | null                |
      | segmentDetailValue3 | 1stDayofNextMonth:: |
      | segmentDetailValue4 | highDate::          |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY        |
    Then I send API call with name "29755-6b" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-6.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-6.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofNextMonth    |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | MRT                  |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | 597                  |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName                                                            | RECONCILIATION                                          |
      | externalConsumerId                                                   | 29755-6.externalConsumerId                              |
      | correlationId                                                        | 29755-6b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                              | SYSTEM_INTEGRATION                                      |
      | createdBy                                                            | 597                                                     |
      | processDate                                                          | current                                                 |
      | externalCaseId                                                       | 29755-6.caseIdentificationNumber                        |
      | consumerId                                                           | 29755-6.consumerId                                      |
      | consumerName                                                         | 29755-6                                                 |
      | reconciliationAction                                                 | No Eligibility Segment                                  |
      | consumerPopulation                                                   | null                                                    |
      # Business Event Optional Fields
      | eligibilityStartDate                                                 | null                                                    |
      | eligibilityEndDate                                                   | null                                                    |
      | eligibilityEndReason                                                 | null                                                    |
      | eligibilityCategoryCode                                              | null                                                    |
      | eligibilityProgramCode                                               | null                                                    |
      | eligibilitySubProgramCode                                            | null                                                    |
      | eligibilityCoverageCode                                              | null                                                    |
      | eligibilityExemptionCode                                             | null                                                    |
      | benefitStatus                                                        | null                                                    |
      | additionalFileds.noEligSegment.Added Eligibility Category Code       | MRT                                                     |
      | additionalFileds.noEligSegment.Added Eligibility Category End Date   | highDate                                                |
      | additionalFileds.noEligSegment.Added Eligibility Category Begin Date | 1stDayofNextMonth                                       |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-03 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 2.0 No Match + Exact Match
  2.0 Match First Inbound BASE Eligibility Segment to Consumer Eligibility in ConnectionPoint
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-7.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-7a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "29755-7.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-7.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-7.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                          |
      | externalConsumerId                                                             | 29755-7.externalConsumerId                              |
      | correlationId                                                                  | 29755-7a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                      |
      | createdBy                                                                      | 597                                                     |
      | processDate                                                                    | current                                                 |
      | externalCaseId                                                                 | 29755-7.caseIdentificationNumber                        |
      | consumerId                                                                     | 29755-7.consumerId                                      |
      | consumerName                                                                   | 29755-7                                                 |
      | reconciliationAction                                                           | Eligibility Created                                     |
      | consumerPopulation                                                             | HHW-Mandatory                                           |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                                |
      | eligibilitySubProgramCode                                                      | HoosierHealthwise                                       |
      | eligibilityStartDate                                                           | 1stDayofPresentMonth                                    |
      | eligibilityEndDate                                                             | highDate                                                |
      | eligibilityEndReason                                                           | null                                                    |
      | eligibilityCategoryCode                                                        | 10                                                      |
      | eligibilityCoverageCode                                                        | cc01                                                    |
      | eligibilityExemptionCode                                                       | qwer                                                    |
      | benefitStatus                                                                  | Eligible                                                |
      | additionalFileds.noMatch.HIP Status                                            | Eligible                                                |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                       |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                       |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-7.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | highDate           |
      | programCode                  | A                  |
      | subProgramTypeCode           | HoosierCareConnect |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | genericFieldText1            | Eligible           |
      | coverageCode                 | cc01               |
      | fileSource                   | RECIPIENT          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth   |
      | endDate             | highDate            |
      | segmentDetailValue1 | 5                   |
      | segmentDetailValue2 | null                |
      | segmentDetailValue3 | 1stDayofNextMonth:: |
      | segmentDetailValue4 | highDate::          |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY        |
    Then I send API call with name "29755-7b" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "29755-7.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Program Sub-Program Change With Date Change","null"
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-7.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-7.consumerId   |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofNextMonth    |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | 597                  |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName                                                                                         | RECONCILIATION                                          |
      | externalConsumerId                                                                                | 29755-7.externalConsumerId                              |
      | correlationId                                                                                     | 29755-7b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                                           | SYSTEM_INTEGRATION                                      |
      | createdBy                                                                                         | 597                                                     |
      | processDate                                                                                       | current                                                 |
      | externalCaseId                                                                                    | 29755-7.caseIdentificationNumber                        |
      | consumerId                                                                                        | 29755-7.consumerId                                      |
      | consumerName                                                                                      | 29755-7                                                 |
      | consumerPopulation                                                                                | HCC-Mandatory                                           |
      | reconciliationAction                                                                              | Program Change                                          |
      # Business Event Optional Fields
      | priorSegment.eligibilityProgramCode                                                               | MEDICAID                                                |
      | priorSegment.eligibilitySubProgramCode                                                            | HoosierHealthwise                                       |
      | priorSegment.eligibilityStartDate                                                                 | 1stDayofPresentMonth                                    |
      | priorSegment.eligibilityEndDate                                                                   | lastDayofPresentMonth                                   |
      | priorSegment.eligibilityEndReason                                                                 | null                                                    |
      | priorSegment.eligibilityCategoryCode                                                              | 10                                                      |
      | priorSegment.eligibilityCoverageCode                                                              | cc01                                                    |
      | priorSegment.eligibilityExemptionCode                                                             | qwer                                                    |
      | priorSegment.benefitStatus                                                                        | Eligible                                                |
      | priorSegment.additionalFileds.programChange.HIP Status                                            | Eligible                                                |
      | priorSegment.additionalFileds.programChange.Managed Care Status                                   | M                                                       |
      | priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                       |
      | priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                |
      | priorSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofNextMonth                                       |
      # * * * * * * * * * * * * * * *
      | newSegment.eligibilityProgramCode                                                                 | MEDICAID                                                |
      | newSegment.eligibilitySubProgramCode                                                              | HoosierCareConnect                                      |
      | newSegment.eligibilityStartDate                                                                   | 1stDayofNextMonth                                       |
      | newSegment.eligibilityEndDate                                                                     | highDate                                                |
      | newSegment.eligibilityEndReason                                                                   | null                                                    |
      | newSegment.eligibilityCategoryCode                                                                | 10                                                      |
      | newSegment.eligibilityCoverageCode                                                                | cc01                                                    |
      | newSegment.eligibilityExemptionCode                                                               | qwer                                                    |
      | newSegment.benefitStatus                                                                          | Eligible                                                |
      | newSegment.additionalFileds.programChange.HIP Status                                              | Eligible                                                |
      | newSegment.additionalFileds.programChange.Managed Care Status                                     | M                                                       |
      | newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Code         | 5                                                       |
      | newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category End Date     | highDate                                                |
      | newSegment.additionalFileds.programChange.ADDED_CATEGORY1.Added Eligibility Category Begin Date   | 1stDayofNextMonth                                       |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 2.0 (Error scenarios)
  2.0 Match First Inbound BASE Eligibility Segment to Consumer Eligibility in ConnectionPoint
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | <name>.consumerId    |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                         |
      | externalConsumerId                                                             | <name>.externalConsumerId                              |
      | correlationId                                                                  | <name>a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                      | 597                                                    |
      | processDate                                                                    | current                                                |
      | externalCaseId                                                                 | <name>.caseIdentificationNumber                        |
      | consumerId                                                                     | <name>.consumerId                                      |
      | consumerName                                                                   | <name>                                                 |
      | reconciliationAction                                                           | Eligibility Created                                    |
      | consumerPopulation                                                             | HHW-Mandatory                                          |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                               |
      | eligibilitySubProgramCode                                                      | HoosierHealthwise                                      |
      | eligibilityStartDate                                                           | 1stDayofPresentMonth                                   |
      | eligibilityEndDate                                                             | highDate                                               |
      | eligibilityEndReason                                                           | null                                                   |
      | eligibilityCategoryCode                                                        | 10                                                     |
      | eligibilityCoverageCode                                                        | cc01                                                   |
      | eligibilityExemptionCode                                                       | qwer                                                   |
      | benefitStatus                                                                  | Eligible                                               |
      | additionalFileds.noMatch.HIP Status                                            | Eligible                                               |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <startDate2>          |
      | eligibilityEndDate           | highDate              |
      | programCode                  | <programCode2>        |
      | subProgramTypeCode           | <subProgramTypeCode2> |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | <categoryCode2>       |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | HRECIP                |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <startDate2>          |
      | eligibilityEndDate           | highDate              |
      | programCode                  | <programCode2>        |
      | subProgramTypeCode           | <subProgramTypeCode2> |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | <categoryCode2>       |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "<name>.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | <name>.consumerId    |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                 |
      | correlationId | <name>b.eligibilities[0].coreEligibility.correlationId |
      | consumerId    | <name>.consumerId                                      |
      | status        | SUCCESS                                                |
    # delete as part of CP-41141
#      | errorStack    | <errorScenario>                                        |
    Examples: Different error scenarios
      | name     | errorScenario                                                | startDate2           | programCode2 | subProgramTypeCode2 | categoryCode2 |
      | 29755-8  | Program Sub-Program Changewith two segments                  | 1stDayofPresentMonth | A            | HoosierCareConnect  | 10            |
      | 29755-9  | Program Sub-Program Change With Date Changewith two segments | 1stDayofNextMonth    | A            | HoosierCareConnect  | 10            |
      | 29755-10 | Base Category Changewith two segments                        | 1stDayofPresentMonth | R            | HoosierHealthwise   | 7             |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 3.0 (same dates HIPstatus update)
  3.0 First Eligibility Reconciliation Data Check
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Denied               |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                         |
      | externalConsumerId                                                             | <name>.externalConsumerId                              |
      | correlationId                                                                  | <name>a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                      | 597                                                    |
      | processDate                                                                    | current                                                |
      | externalCaseId                                                                 | <name>.caseIdentificationNumber                        |
      | consumerId                                                                     | <name>.consumerId                                      |
      | consumerName                                                                   | <name>                                                 |
      | reconciliationAction                                                           | Eligibility Created                                    |
      | consumerPopulation                                                             | HIP-Mandatory                                          |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                               |
      | eligibilitySubProgramCode                                                      | HealthyIndianaPlan                                     |
      | eligibilityStartDate                                                           | 1stDayofPresentMonth                                   |
      | eligibilityEndDate                                                             | highDate                                               |
      | eligibilityEndReason                                                           | null                                                   |
      | eligibilityCategoryCode                                                        | 10                                                     |
      | eligibilityCoverageCode                                                        | cc01                                                   |
      | eligibilityExemptionCode                                                       | qwer                                                   |
      | benefitStatus                                                                  | Benefit Status Error                                   |
      | additionalFileds.noMatch.HIP Status                                            | Denied                                                 |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | <fileSource2>        |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 7                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                         | RECONCILIATION                                         |
      | externalConsumerId                                                                | <name>.externalConsumerId                              |
      | correlationId                                                                     | <name>b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                           | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                         | 597                                                    |
      | processDate                                                                       | current                                                |
      | externalCaseId                                                                    | <name>.caseIdentificationNumber                        |
      | consumerId                                                                        | <name>.consumerId                                      |
      | consumerName                                                                      | <name>                                                 |
      | reconciliationAction                                                              | Eligibility Updated                                    |
      | consumerPopulation                                                                | HIP-Mandatory                                          |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                            | MEDICAID                                               |
      | eligibilitySubProgramCode                                                         | HealthyIndianaPlan                                     |
      | eligibilityStartDate                                                              | 1stDayofPresentMonth                                   |
      | eligibilityEndDate                                                                | highDate                                               |
      | eligibilityEndReason                                                              | null                                                   |
      | eligibilityCategoryCode                                                           | 10                                                     |
      | eligibilityCoverageCode                                                           | cc01                                                   |
      | eligibilityExemptionCode                                                          | qwer                                                   |
      | benefitStatus                                                                     | Benefit Status Error                                   |
      | additionalFileds.exactMatch.HIP Status                                            | <HIPstatusRees>                                        |
      | additionalFileds.exactMatch.Managed Care Status                                   | M                                                      |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Code       | 7                                                      |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Examples: HIP status updated depending on fileSource
      | name     | fileSource2      | HIPstatusRees |
      | 29755-11 | HRECIP           | Eligible      |
      | 29755-12 | RECIPIENT_HRECIP | Eligible      |
      | 29755-13 | RECIPIENT        | Denied        |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 3.0 (changed start dates)
  3.0 First Eligibility Reconciliation Data Check
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Denied               |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                         |
      | externalConsumerId                                                             | <name>.externalConsumerId                              |
      | correlationId                                                                  | <name>a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                      | 597                                                    |
      | processDate                                                                    | current                                                |
      | externalCaseId                                                                 | <name>.caseIdentificationNumber                        |
      | consumerId                                                                     | <name>.consumerId                                      |
      | consumerName                                                                   | <name>                                                 |
      | reconciliationAction                                                           | Eligibility Created                                    |
      | consumerPopulation                                                             | HIP-Mandatory                                          |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                               |
      | eligibilitySubProgramCode                                                      | HealthyIndianaPlan                                     |
      | eligibilityStartDate                                                           | 1stDayofPresentMonth                                   |
      | eligibilityEndDate                                                             | highDate                                               |
      | eligibilityEndReason                                                           | null                                                   |
      | eligibilityCategoryCode                                                        | 10                                                     |
      | eligibilityCoverageCode                                                        | cc01                                                   |
      | eligibilityExemptionCode                                                       | qwer                                                   |
      | benefitStatus                                                                  | Benefit Status Error                                   |
      | additionalFileds.noMatch.HIP Status                                            | Denied                                                 |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth        |
      | eligibilityEndDate           | highDate                 |
      | programCode                  | H                        |
      | subProgramTypeCode           | HealthyIndianaPlan       |
      | eligibilityStatusCode        | <eligibilityStatusCode2> |
      | categoryCode                 | 10                       |
      | genericFieldText1            | Eligible                 |
      | coverageCode                 | cc01                     |
      | fileSource                   | <fileSource2>            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth      |
      | endDate             | highDate               |
      | segmentDetailValue1 | 7                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                                  | RECONCILIATION                                         |
      | externalConsumerId                                                                         | <name>.externalConsumerId                              |
      | correlationId                                                                              | <name>b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                                    | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                                  | 597                                                    |
      | processDate                                                                                | current                                                |
      | externalCaseId                                                                             | <name>.caseIdentificationNumber                        |
      | consumerId                                                                                 | <name>.consumerId                                      |
      | consumerName                                                                               | <name>                                                 |
      | reconciliationAction                                                                       | Eligibility Updated                                    |
      | consumerPopulation                                                                         | <consumerPopulationRes>                                |
      # Business Event Optional Fields
      | oldValue.eligibilityProgramCode                                                            | MEDICAID                                               |
      | oldValue.eligibilitySubProgramCode                                                         | HealthyIndianaPlan                                     |
      | oldValue.eligibilityStartDate                                                              | 1stDayofPresentMonth                                   |
      | oldValue.eligibilityEndDate                                                                | highDate                                               |
      | oldValue.eligibilityEndReason                                                              | null                                                   |
      | oldValue.eligibilityCategoryCode                                                           | 10                                                     |
      | oldValue.eligibilityCoverageCode                                                           | cc01                                                   |
      | oldValue.eligibilityExemptionCode                                                          | qwer                                                   |
      | oldValue.benefitStatus                                                                     | Benefit Status Error                                   |
      | oldValue.additionalFileds                                                                  | null                                                   |
      # * * * * * * * * * * * * * * * * * *
      | newValue.eligibilityProgramCode                                                            | MEDICAID                                               |
      | newValue.eligibilitySubProgramCode                                                         | HealthyIndianaPlan                                     |
      | newValue.eligibilityStartDate                                                              | 1stDayofNextMonth                                      |
      | newValue.eligibilityEndDate                                                                | highDate                                               |
      | newValue.eligibilityEndReason                                                              | null                                                   |
      | newValue.eligibilityCategoryCode                                                           | 10                                                     |
      | newValue.eligibilityCoverageCode                                                           | cc01                                                   |
      | newValue.eligibilityExemptionCode                                                          | qwer                                                   |
      | newValue.benefitStatus                                                                     | Benefit Status Error                                   |
      | newValue.additionalFileds                                                                  | null                                                   |
      # * * * * * * * * * * * * * * * * * *
      | newValue.additionalFileds.exactMatch.HIP Status                                            | <HIPstatusRees>                                        |
      | newValue.additionalFileds.exactMatch.Managed Care Status                                   | <eligibilityStatusCode2>                               |
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
      # * * * * * * * * * * * * * * * * * * *
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Code       | 7                                                      |
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category End Date   | highDate                                               |
      | newValue.additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Examples: HIP status updated depending on fileSource
      | name     | fileSource2      | HIPstatusRees | eligibilityStatusCode2 | consumerPopulationRes |
      | 29755-14 | HRECIP           | Eligible      | M                      | HIP-Mandatory         |
      | 29755-15 | RECIPIENT_HRECIP | Eligible      | V                      | HIP-Voluntary         |
      | 29755-16 | RECIPIENT        | Denied        | M                      | HIP-Mandatory         |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 4.0 5.0 (Merge Segments)
  4.0 Determine Update from Second (Future) Eligibility Segment
  5.0 Merge Two Inbound ADDED Eligibility segments with the same ADDED Aid-Category
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-17 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-17.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-17a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "29755-17.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                           |
      | externalConsumerId                                                             | 29755-17.externalConsumerId                              |
      | correlationId                                                                  | 29755-17a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                      | 597                                                      |
      | processDate                                                                    | current                                                  |
      | externalCaseId                                                                 | 29755-17.caseIdentificationNumber                        |
      | consumerId                                                                     | 29755-17.consumerId                                      |
      | consumerName                                                                   | 29755-17                                                 |
      | reconciliationAction                                                           | Eligibility Created                                      |
      | consumerPopulation                                                             | HCC-Mandatory                                            |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                                 |
      | eligibilitySubProgramCode                                                      | HoosierCareConnect                                       |
      | eligibilityStartDate                                                           | 1stDayofPresentMonth                                     |
      | eligibilityEndDate                                                             | highDate                                                 |
      | eligibilityEndReason                                                           | null                                                     |
      | eligibilityCategoryCode                                                        | 10                                                       |
      | eligibilityCoverageCode                                                        | cc01                                                     |
      | eligibilityExemptionCode                                                       | qwer                                                     |
      | benefitStatus                                                                  | Eligible                                                 |
      | additionalFileds.noMatch.HIP Status                                            | Eligible                                                 |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                        |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                        |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                     |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-17.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide one more eligibility details
      | consumerId                   | 29755-17.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | eligibilityRecordId          | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | programCode                  | A                   |
      | subProgramTypeCode           | HoosierCareConnect  |
      | eligibilityStatusCode        | M                   |
      | categoryCode                 | 10                  |
      | genericFieldText1            | Eligible            |
      | coverageCode                 | cc01                |
      | fileSource                   | RECIPIENT           |
    Then I send API call with name "29755-17b" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "29755-17.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "EXACT MATCH","null"
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-17.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-17.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |

#Scenario disabled due to chnages with CP-41141 and CP-41142
  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN-ignore @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 4.0 (Gap in Program)
  3.0 First Eligibility Reconciliation Data Check
  4.0 Determine Update from Second (Future) Eligibility Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-18 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-18.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | lastDayofPresentMonth   |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | lastDayofPresentMonth:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    Then I send API call with name "29755-18a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "29755-18.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                           |
      | externalConsumerId                                                             | 29755-18.externalConsumerId                              |
      | correlationId                                                                  | 29755-18a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                      | 597                                                      |
      | processDate                                                                    | current                                                  |
      | externalCaseId                                                                 | 29755-18.caseIdentificationNumber                        |
      | consumerId                                                                     | 29755-18.consumerId                                      |
      | consumerName                                                                   | 29755-18                                                 |
      | reconciliationAction                                                           | Eligibility Created                                      |
      | consumerPopulation                                                             | HCC-Mandatory                                            |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                                 |
      | eligibilitySubProgramCode                                                      | HoosierCareConnect                                       |
      | eligibilityStartDate                                                           | 1stDayof2MonthsBefore                                    |
      | eligibilityEndDate                                                             | lastDayofPresentMonth                                    |
      | eligibilityEndReason                                                           | null                                                     |
      | eligibilityCategoryCode                                                        | 10                                                       |
      | eligibilityCoverageCode                                                        | cc01                                                     |
      | eligibilityExemptionCode                                                       | qwer                                                     |
      #     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | benefitStatus                                                                  | Eligible                                                 |
      | additionalFileds.noMatch.HIP Status                                            | Eligible                                                 |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                        |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                        |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | lastDayofPresentMonth                                    |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayof2MonthsBefore                                    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-18.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | lastDayofPresentMonth |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide one more eligibility details
      | consumerId                   | 29755-18.consumerId    |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayOf2MonthsFromNow |
      | eligibilityEndDate           | highDate               |
      | programCode                  | A                      |
      | subProgramTypeCode           | HoosierCareConnect     |
      | eligibilityStatusCode        | M                      |
      | categoryCode                 | 10                     |
      | genericFieldText1            | Eligible               |
      | coverageCode                 | cc01                   |
      | fileSource                   | RECIPIENT              |
    Then I send API call with name "29755-18b" for create Eligibility and Enrollment
    When I initiated get eligibility by consumer id "29755-18.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in previously created Eligibility Record
      | eligibilityStartDate  | 1stDayof2MonthsBefore |
      | eligibilityEndDate    | lastDayofPresentMonth |
      | categoryCode          | 10                    |
      | programCode           | A                     |
      | subProgramTypeCode    | HoosierCareConnect    |
      | eligibilityStatusCode | M                     |
      | genericFieldText1     | Eligible              |
      | genericFieldText2     | 0                     |
      | createdOn             | current               |
      | createdBy             | 597                   |
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayOf2MonthsFromNow |
      | eligibilityEndDate    | highDate               |
      | categoryCode          | 10                     |
      | programCode           | A                      |
      | subProgramTypeCode    | HoosierCareConnect     |
      | eligibilityStatusCode | M                      |
      | genericFieldText1     | Eligible               |
      | genericFieldText2     | 0                      |
      | createdOn             | current                |
      | createdBy             | 597                    |
      | updatedOn             | current                |
      | updatedBy             | 597                    |
    And I initiated get benefit status by consumer id "29755-18.consumerId"
    And I run get enrollment api
    Then I verify benefit status information with data
      | programPopulation   | HCC-Mandatory                                                      |
      | population          | HCC-Mandatory                                                      |
      #     After CP-32018 implemented benefit status updated to Eligible instead of Benefit Status Error with eligibility non-high date
      | benefitStatus       | Eligible                                                           |
      | eligibilityScenario | First Eligibility: EXACT MATCH, Second Eligibility: Gap in Program |
      | timeframe           | Active                                                             |
    Then I verify latest benefit status information with data
      | programPopulation   | HCC-Mandatory                                                      |
      | population          | HCC-Mandatory                                                      |
      | benefitStatus       | Eligible                                                           |
      | eligibilityScenario | First Eligibility: EXACT MATCH, Second Eligibility: Gap in Program |
      | timeframe           | FUTURE                                                             |

#Scenario is invalid as per CP-41141,CP-41142
  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN-ignore @IN-EB-API-Regression @sobir
  Scenario Outline: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 4.0 (Error scenarios)
  4.0 Determine Update from Second (Future) Eligibility Segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Then I will verify business events are generated with data
      | eventName                                                                      | RECONCILIATION                                         |
      | externalConsumerId                                                             | <name>.externalConsumerId                              |
      | correlationId                                                                  | <name>a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                        | SYSTEM_INTEGRATION                                     |
      | createdBy                                                                      | 597                                                    |
      | processDate                                                                    | current                                                |
      | externalCaseId                                                                 | <name>.caseIdentificationNumber                        |
      | consumerId                                                                     | <name>.consumerId                                      |
      | consumerName                                                                   | <name>                                                 |
      | reconciliationAction                                                           | Eligibility Created                                    |
      | consumerPopulation                                                             | HCC-Mandatory                                          |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                         | MEDICAID                                               |
      | eligibilitySubProgramCode                                                      | HoosierCareConnect                                     |
      | eligibilityStartDate                                                           | 1stDayofNextMonth                                   |
      | eligibilityEndDate                                                             | highDate                                               |
      | eligibilityEndReason                                                           | null                                                   |
      | eligibilityCategoryCode                                                        | 10                                                     |
      | eligibilityCoverageCode                                                        | cc01                                                   |
      | eligibilityExemptionCode                                                       | qwer                                                   |
      | benefitStatus                                                                  | Eligible                                               |
      | additionalFileds.noMatch.HIP Status                                            | Eligible                                               |
      | additionalFileds.noMatch.Managed Care Status                                   | M                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                      |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                               |
      | additionalFileds.noMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide one more eligibility details
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth     |
      | eligibilityEndDate           | highDate              |
      | programCode                  | <programCode2>        |
      | subProgramTypeCode           | <subProgramTypeCode2> |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | <categoryCode2>       |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    Then I send API call with name "<name>b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                 |
      | correlationId | <name>.eligibilities[0].coreEligibility.correlationId |
      | consumerId    | <name>.consumerId                                      |
      | status        | FAIL                                                   |
      | errorStack    | <errorScenario>                                        |
    Examples: Different error scenarios
      | name     | errorScenario                                          | programCode2 | subProgramTypeCode2 | categoryCode2 |
      | 29755-19 | DIFFERENT PROGRAM WITH OVERLAPPING TIMEFRAME           | H            | HealthyIndianaPlan  | 10            |
      | 29755-20 | Different BASE Aid-Category with Overlapping Timeframe | A            | HoosierCareConnect  | 4             |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 5.0 (MERGE MRT)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-21 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-21.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth   |
      | endDate             | futureDate          |
      | segmentDetailValue1 | MRT                 |
      | segmentDetailValue2 | null                |
      | segmentDetailValue3 | 1stDayofNextMonth:: |
      | segmentDetailValue4 | highDate::          |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY        |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | MRT                    |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-21a" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                            | RECONCILIATION                                           |
      | externalConsumerId                                                   | 29755-21.externalConsumerId                              |
      | correlationId                                                        | 29755-21a.eligibilities[0].coreEligibility.correlationId |
      | channel                                                              | SYSTEM_INTEGRATION                                       |
      | createdBy                                                            | 597                                                      |
      | processDate                                                          | current                                                  |
      | externalCaseId                                                       | 29755-21.caseIdentificationNumber                        |
      | consumerId                                                           | 29755-21.consumerId                                      |
      | consumerName                                                         | 29755-21                                                 |
      | reconciliationAction                                                 | No Eligibility Segment                                   |
      | consumerPopulation                                                   | null                                                     |
      # Business Event Optional Fields
      | eligibilityStartDate                                                 | null                                                     |
      | eligibilityEndDate                                                   | null                                                     |
      | eligibilityEndReason                                                 | null                                                     |
      | eligibilityCategoryCode                                              | null                                                     |
      | eligibilityProgramCode                                               | null                                                     |
      | eligibilitySubProgramCode                                            | null                                                     |
      | eligibilityCoverageCode                                              | null                                                     |
      | eligibilityExemptionCode                                             | null                                                     |
      | benefitStatus                                                        | null                                                     |
      | additionalFileds.noEligSegment.Added Eligibility Category Code       | MRT                                                      |
      | additionalFileds.noEligSegment.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.noEligSegment.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                     |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-21.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-21.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | MRT                  |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofNextMonth    |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 5.0 (MERGE ADDED CATEGORY + core in CP)
  5.0 Merge Two Inbound ADDED Eligibility segments with the same ADDED Aid-Category
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-22 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-22.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 5                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-22a" for create Eligibility and Enrollment
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-22.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth   |
      | endDate             | highDate            |
      | segmentDetailValue1 | 7                   |
      | segmentDetailValue2 | null                |
      | segmentDetailValue3 | 1stDayofNextMonth:: |
      | segmentDetailValue4 | highDate::          |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY        |
    And User provide other eligibility segments details
      | startDate           | 1stDayofPresentMonth   |
      | endDate             | highDate               |
      | segmentDetailValue1 | 7                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofPresentMonth:: |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-22b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                         | RECONCILIATION                                           |
      | externalConsumerId                                                                | 29755-22.externalConsumerId                              |
      | correlationId                                                                     | 29755-22b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                           | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                         | 597                                                      |
      | processDate                                                                       | current                                                  |
      | externalCaseId                                                                    | 29755-22.caseIdentificationNumber                        |
      | consumerId                                                                        | 29755-22.consumerId                                      |
      | consumerName                                                                      | 29755-22                                                 |
      | reconciliationAction                                                              | Eligibility Updated                                      |
      | consumerPopulation                                                                | HCC-Mandatory                                            |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                            | MEDICAID                                                 |
      | eligibilitySubProgramCode                                                         | HoosierCareConnect                                       |
      | eligibilityStartDate                                                              | 1stDayofPresentMonth                                     |
      | eligibilityEndDate                                                                | highDate                                                 |
      | eligibilityEndReason                                                              | null                                                     |
      | eligibilityCategoryCode                                                           | 10                                                       |
      | eligibilityCoverageCode                                                           | cc01                                                     |
      | eligibilityExemptionCode                                                          | qwer                                                     |
      | benefitStatus                                                                     | Eligible                                                 |
      # * * * * * * *
      | additionalFileds.exactMatch.HIP Status                                            | Eligible                                                 |
      | additionalFileds.exactMatch.Managed Care Status                                   | M                                                        |
      # * * * * * * *
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                        |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                     |
      # * * * * * * *
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Code       | 7                                                        |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date | 1stDayofPresentMonth                                     |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-22.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console     |
      | [0].consumerId                | 29755-22.consumerId  |
      | [0].segmentTypeCode           | AID_CATEGORY         |
      | [0].startDate                 | 1stDayofPresentMonth |
      | [0].endDate                   | highDate             |
      | [0].segmentDetailValue1       | 5                    |
      | [0].segmentDetailValue2       | null                 |
      | [0].segmentDetailValue3       | 1stDayofPresentMonth |
      | [0].segmentDetailValue4       | highDate             |
      | [0].segmentDetailValue5       | null                 |
      | [0].segmentDetailValue6       | null                 |
      | [0].segmentDetailValue7       | null                 |
      | [0].segmentDetailValue8       | null                 |
      | [0].segmentDetailValue9       | null                 |
      | [0].segmentDetailValue10      | null                 |
      | [0].createdOn                 | current              |
      | [0].createdBy                 | null                 |
      | [0].updatedOn                 | current              |
      | [0].updatedBy                 | null                 |
      | [0].correlationId             | null                 |
      | [0].uiid                      | null                 |
      # * * * * * * *
      | [1].otherEligbilitySegmentsId | print in console     |
      | [1].consumerId                | 29755-22.consumerId  |
      | [1].segmentTypeCode           | AID_CATEGORY         |
      | [1].startDate                 | 1stDayofPresentMonth |
      | [1].endDate                   | highDate             |
      | [1].segmentDetailValue1       | 7                    |
      | [1].segmentDetailValue2       | null                 |
      | [1].segmentDetailValue3       | 1stDayofNextMonth    |
      | [1].segmentDetailValue4       | highDate             |
      | [1].segmentDetailValue5       | null                 |
      | [1].segmentDetailValue6       | null                 |
      | [1].segmentDetailValue7       | null                 |
      | [1].segmentDetailValue8       | null                 |
      | [1].segmentDetailValue9       | null                 |
      | [1].segmentDetailValue10      | null                 |
      | [1].createdOn                 | current              |
      | [1].createdBy                 | null                 |
      | [1].updatedOn                 | current              |
      | [1].updatedBy                 | null                 |
      | [1].correlationId             | null                 |
      | [1].uiid                      | null                 |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 5.0 (NON MERGEd ADDED CATEGORY + core in CP)
  5.0 Merge Two Inbound ADDED Eligibility segments with the same ADDED Aid-Category
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-23 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29755-23.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    Then I send API call with name "29755-23a" for create Eligibility and Enrollment
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-23.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | lastDayofPresentMonth   |
      | segmentDetailValue1 | 7                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | lastDayofPresentMonth:: |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayOf2MonthsFromNow |
      | endDate             | highDate               |
      | segmentDetailValue1 | 7                      |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | 1stDayofNextMonth::    |
      | segmentDetailValue4 | highDate::             |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29755-23b" for create Eligibility and Enrollment
    Then I will verify business events are generated with data
      | eventName                                                                         | RECONCILIATION                                           |
      | externalConsumerId                                                                | 29755-23.externalConsumerId                              |
      | correlationId                                                                     | 29755-23b.eligibilities[0].coreEligibility.correlationId |
      | channel                                                                           | SYSTEM_INTEGRATION                                       |
      | createdBy                                                                         | 597                                                      |
      | processDate                                                                       | current                                                  |
      | externalCaseId                                                                    | 29755-23.caseIdentificationNumber                        |
      | consumerId                                                                        | 29755-23.consumerId                                      |
      | consumerName                                                                      | 29755-23                                                 |
      | reconciliationAction                                                              | Eligibility Updated                                      |
      | consumerPopulation                                                                | HCC-Mandatory                                            |
      # Business Event Optional Fields
      | eligibilityProgramCode                                                            | MEDICAID                                                 |
      | eligibilitySubProgramCode                                                         | HoosierCareConnect                                       |
      | eligibilityStartDate                                                              | 1stDayof2MonthsBefore                                    |
      | eligibilityEndDate                                                                | highDate                                                 |
      | eligibilityEndReason                                                              | null                                                     |
      | eligibilityCategoryCode                                                           | 10                                                       |
      | eligibilityCoverageCode                                                           | cc01                                                     |
      | eligibilityExemptionCode                                                          | qwer                                                     |
      | benefitStatus                                                                     | Eligible                                                 |
      # * * * * * * *
      | additionalFileds.exactMatch.HIP Status                                            | Eligible                                                 |
      | additionalFileds.exactMatch.Managed Care Status                                   | M                                                        |
      # * * * * * * *
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Code       | 5                                                        |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.exactMatch.ADDED_CATEGORY1.Added Eligibility Category Begin Date | 1stDayof2MonthsBefore                                    |
      # * * * * * * *
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Code       | 7                                                        |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category End Date   | highDate                                                 |
      | additionalFileds.exactMatch.ADDED_CATEGORY2.Added Eligibility Category Begin Date | 1stDayOf2MonthsFromNow                                   |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-23.consumerId" with data
      | [0].otherEligbilitySegmentsId | print in console       |
      | [0].consumerId                | 29755-23.consumerId    |
      | [0].segmentTypeCode           | AID_CATEGORY           |
      | [0].startDate                 | 1stDayof2MonthsBefore  |
      | [0].endDate                   | highDate               |
      | [0].segmentDetailValue1       | 5                      |
      | [0].segmentDetailValue2       | null                   |
      | [0].segmentDetailValue3       | 1stDayof2MonthsBefore  |
      | [0].segmentDetailValue4       | highDate               |
      | [0].segmentDetailValue5       | null                   |
      | [0].segmentDetailValue6       | null                   |
      | [0].segmentDetailValue7       | null                   |
      | [0].segmentDetailValue8       | null                   |
      | [0].segmentDetailValue9       | null                   |
      | [0].segmentDetailValue10      | null                   |
      | [0].createdOn                 | current                |
      | [0].createdBy                 | null                   |
      | [0].updatedOn                 | current                |
      | [0].updatedBy                 | null                   |
      | [0].correlationId             | null                   |
      | [0].uiid                      | null                   |
      # * * * * * *  * *
      | [1].otherEligbilitySegmentsId | print in console       |
      | [1].consumerId                | 29755-23.consumerId    |
      | [1].segmentTypeCode           | AID_CATEGORY           |
      | [1].startDate                 | 1stDayOf2MonthsFromNow |
      | [1].endDate                   | highDate               |
      | [1].segmentDetailValue1       | 7                      |
      | [1].segmentDetailValue2       | null                   |
      | [1].segmentDetailValue3       | 1stDayofNextMonth      |
      | [1].segmentDetailValue4       | highDate               |
      | [1].segmentDetailValue5       | null                   |
      | [1].segmentDetailValue6       | null                   |
      | [1].segmentDetailValue7       | null                   |
      | [1].segmentDetailValue8       | null                   |
      | [1].segmentDetailValue9       | null                   |
      | [1].segmentDetailValue10      | null                   |
      | [1].createdOn                 | current                |
      | [1].createdBy                 | null                   |
      | [1].updatedOn                 | current                |
      | [1].updatedBy                 | null                   |
      | [1].correlationId             | null                   |
      | [1].uiid                      | null                   |


  @API-CP-30875 @API-CP-29755 @API-CP-29755-04 @API-EE-IN @IN-EB-API-Regression @sobir
  Scenario: IN-EB ELIGIBILITY BASE and ADDED Aid Category Codes MMIS Sends Reconciliation (Part 1 Decide Scenario) 5.0 (ERROR ADDED CATEGORY + NO core in CP)
  5.0 Merge Two Inbound ADDED Eligibility segments with the same ADDED Aid-Category
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29755-24 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | 29755-24.consumerId |
      | isEligibilityRequired | yes                 |
      | isEnrollemntRequired  | no                  |
      | createdBy             | 597                 |
      | recordId              | 21                  |
      | genericFieldText1     | Eligible            |
      | fileSource            | HRECIP              |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | lastDayofLastMonth      |
      | segmentDetailValue1 | 7                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | lastDayofLastMonth::    |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth   |
      | endDate             | highDate            |
      | segmentDetailValue1 | 7                   |
      | segmentDetailValue2 | null                |
      | segmentDetailValue3 | 1stDayofNextMonth:: |
      | segmentDetailValue4 | highDate::          |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY        |
    Then I send API call with name "29755-24a" for create Eligibility and Enrollment
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                   |
      | correlationId | 29755-24a.eligibilities[0].coreEligibility.correlationId |
      | consumerId    | 29755-24.consumerId                                      |
      | status        | FAIL                                                     |
      | errorStack    | No BASE eligibility segment for ADDED aid-category code  |
    Then I verfify other eiigbility segment type "" details for coma separated consumerIds "29755-24.consumerId" with data
      | empty |[blank]|
























