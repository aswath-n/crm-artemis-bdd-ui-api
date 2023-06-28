Feature: IN-EB ELIGIBILITY Reconciliation-Non High End Date-Loss of Eligibility-All populations

  @API-CP-24136 @API-CP-24136-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario: IN-EB ELIGIBILITY LOE -HIP- Non high end date (Part 1 Decide Scenario) 1.2
  1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide dummy enrollment and eligibility information to create enrollment
      | consumerId            | <name>.consumerId  |
      | isEligibilityRequired | yes                |
      | isEnrollemntRequired  | no                 |
      | createdBy             | 597                |
      | recordId              | 21                 |
      | programCode           | H                  |
      | subProgramTypeCode    | HealthyIndianaPlan |
      | genericFieldText1     | Eligible           |
      | fileSource            | HRECIP             |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "<name>.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | false    |
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
      | eligibilityEndDate           | future               |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "<name>.consumerId" with data
      | programCode           | H        |
      | hipStatus             | Eligible |
      | eligibilityStatusCode | null     |
      | createdOn             | current  |
      | updatedOn             | current  |
      | processInd            | true     |
    #Not implemented
#    Then I will verify business events are generated with data
#      | eventName                                    | RECONCILIATION                                         |
#      | externalConsumerId                           | <name>.externalConsumerId                              |
#      | correlationId                                | <name>a.eligibilities[0].coreEligibility.correlationId |
#      | channel                                      | SYSTEM_INTEGRATION                                     |
#      | createdBy                                    | 597                                                    |
#      | processDate                                  | current                                                |
#      | externalCaseId                               | <name>.caseIdentificationNumber                        |
#      | consumerId                                   | <name>.consumerId                                      |
#      | consumerName                                 | <name>                                                 |
#      | reconciliationAction                         | Eligibility Created                                    |
#      # Business Event Optional Fields
#      | eligibilityStartDate                         | 1stDayofPresentMonth                                   |
#      | eligibilityEndDate                           | highDate                                               |
#      | eligibilityEndReason                         | null                                                   |
#      | eligibilityCategoryCode                      | 10                                                     |
#      | eligibilityProgramCode                       | MEDICAID                                               |
#      | eligibilitySubProgramCode                    | HealthyIndianaPlan                                     |
#      | eligibilityCoverageCode                      | cc01                                                   |
#      | eligibilityExemptionCode                     | qwer                                                   |
#      | consumerPopulation                           | HIP-Mandatory                                          |
#      | benefitStatus                                | Benefit Status Error                                   |
#      | additionalFileds.noMatch.HIP Status          | null                                                   |
#      | additionalFileds.noMatch.Managed Care Status | M                                                      |


  @API-CP-24136 @API-CP-24136-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY LOE MMIS Sends Reconciliation (Part 1 Decide Scenario)1.2
  1.1 Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (no Eligibility in system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
     # 1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT_HRECIP     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    Then I verify hrecip eligibility information by consumerId "<name>.consumerId" with data
      | empty |[blank]|
    #not implemented
#    Then I will verify business events are generated with data
#      | eventName                                    | RECONCILIATION                                        |
#      | externalConsumerId                           | <name>.externalConsumerId                             |
#      | correlationId                                | <name>.eligibilities[0].coreEligibility.correlationId |
#      | channel                                      | SYSTEM_INTEGRATION                                    |
#      | createdBy                                    | 597                                                   |
#      | processDate                                  | current                                               |
#      | externalCaseId                               | <name>.caseIdentificationNumber                       |
#      | consumerId                                   | <name>.consumerId                                     |
#      | consumerName                                 | <name>                                                |
#      | reconciliationAction                         | Eligibility Created                                   |
#      # Business Event Optional Fields
#      | eligibilityStartDate                         | 1stDayofPresentMonth                                  |
#      | eligibilityEndDate                           | highDate                                              |
#      | eligibilityEndReason                         | null                                                  |
#      | eligibilityCategoryCode                      | 10                                                    |
#      | eligibilityProgramCode                       | MEDICAID                                              |
#      | eligibilitySubProgramCode                    | <subProgramTypeCode>                                  |
#      | eligibilityCoverageCode                      | cc01                                                  |
#      | eligibilityExemptionCode                     | qwer                                                  |
#      | consumerPopulation                           | HIP-Mandatory                                         |
#      | benefitStatus                                | Benefit Status Error                                  |
#      | additionalFileds.noMatch.HIP Status          | Eligible                                              |
#      | additionalFileds.noMatch.Managed Care Status | M                                                     |
    Examples:
      | name       | eligibilityEndDate | programCode | subProgramTypeCode | reconCategoryCode | reconEligibilityStatusCode |
      | 24135-01-1 | future             | H           | HealthyIndianaPlan | 10                | V                          |

  @API-CP-24136 @API-CP-24136-02-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY LOE MMIS Sends Reconciliation- No Match -Non high End Date -All programs
  Inbound Internal Scenario (21) for HIP Consumer WITHOUT Eligibility Segments (Match Eligibility in the system)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # 1.2 Inbound Internal Scenario (21) for HIP Consumer WITH Eligibility Segments (no Eligibility in system)
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | <genericFieldText1>  |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT-HRECIP     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "NO MATCH","null"
    Examples:
      | name       | eligibilityEndDate | programCode | subProgramTypeCode | genericFieldText1 |
      | 24136-02-2 | future             | R           | HoosierHealthwise  | null              |
      | 24136-02-3 | future             | A           | HoosierCareConnect | null              |
      | 24136-02-4 | future             | H           | HealthyIndianaPlan | Eligible          |

  @API-CP-24136 @API-CP-24136-02-02 @API-CP-24296 @API-CP-24296-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY LOE MMIS Sends Reconciliation-Exact Match & Changed End Date-Non High End Date- All programs
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | <genericFieldText1>  |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT-HRECIP     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth         |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | programCode                  | <programCode>                |
      | subProgramTypeCode           | <subProgramTypeCode>         |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | genericFieldText1            | <reconGenericFieldText1>     |
      | coverageCode                 | cc01                         |
      | fileSource                   | <fileSource>                 |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth         |
      | eligibilityEndDate    | <reconEligibilityEndDate>    |
      | categoryCode          | 10                           |
      | programCode           | <programCode>                |
      | subProgramTypeCode    | <subProgramTypeCode>         |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | genericFieldText1     | <expectedValue>              |
      | createdOn             | current                      |
      | createdBy             | 597                          |
    Examples:
      | name        | eligibilityEndDate | programCode | subProgramTypeCode | genericFieldText1 | reconGenericFieldText1 | scenario         | reconEligibilityEndDate | reconEligibilityStatusCode | population    | benefitStatus        | fileSource       | expectedValue | action      | consumerAction | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil        |
      | 24136-02-5  | future             | R           | HoosierHealthwise  | null              | null                   | EXACT MATCH      | future                  | M                          | HHW-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24136-02-6  | future             | A           | HoosierCareConnect | null              | null                   | EXACT MATCH      | future                  | M                          | HCC-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24136-02-7  | future             | H           | HealthyIndianaPlan | Eligible          | null                   | EXACT MATCH      | future                  | M                          | HIP-Mandatory | Benefit Status Error | RECIPIENT        | Eligible      | Unavailable | null           | false                | null                 | null                      |
      | 24136-02-8  | future             | R           | HoosierHealthwise  | null              | null                   | CHANGED END DATE | futureDate              | M                          | HHW-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24136-02-9  | future             | A           | HoosierCareConnect | null              | null                   | CHANGED END DATE | futureDate              | M                          | HCC-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24136-02-10 | future             | H           | HealthyIndianaPlan | Eligible          | Conditional            | CHANGED END DATE | futureDate              | M                          | HIP-Mandatory | benefit status error | RECIPIENT-HRECIP | Conditional   | Unavailable | null           | false                | null                 | null                      |
      | 24136-03-01 | future             | R           | HoosierHealthwise  | null              | null                   | EXACT MATCH      | future                  | V                          | HHW-Voluntary | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24136-03-02 | future             | A           | HoosierCareConnect | null              | null                   | EXACT MATCH      | future                  | X                          | HCC-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24136-03-03 | future             | H           | HealthyIndianaPlan | Eligible          | null                   | EXACT MATCH      | future                  | X                          | HIP-Mandatory | benefit status error | RECIPIENT        | Eligible      | Unavailable | null           | false                | null                 | null                      |
      | 24136-03-04 | future             | R           | HoosierHealthwise  | null              | null                   | CHANGED END DATE | futureDate              | V                          | HHW-Voluntary | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 14DayFromFirstDayOfMonth  |
      | 24136-03-05 | future             | A           | HoosierCareConnect | null              | null                   | CHANGED END DATE | futureDate              | X                          | HCC-Mandatory | Eligible             | RECIPIENT-HRECIP | null          | Required    | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24136-03-06 | future             | H           | HealthyIndianaPlan | null              | Eligible               | CHANGED END DATE | futureDate              | X                          | HIP-Mandatory | benefit status error | RECIPIENT-HRECIP | Eligible      | Unavailable | null           | false                | null                 | null                      |

  @API-CP-24136 @API-CP-24136-02-03 @API-CP-24296 @API-CP-24296-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY LOE MMIS Sends Reconciliation-Program Change-Non High End Date- All programs
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | <genericFieldText1>  |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT-HRECIP     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <reconEligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>         |
      | programCode                  | <reconProgramCode>           |
      | subProgramTypeCode           | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | genericFieldText1            | <reconGenericFieldText1>     |
      | coverageCode                 | cc01                         |
      | fileSource                   | <fileSource>                 |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <reconEligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>         |
      | categoryCode          | 10                           |
      | programCode           | <reconProgramCode>           |
      | subProgramTypeCode    | <reconSubProgramTypeCode>    |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | genericFieldText1     | <expectedvalue>              |
      | createdOn             | current                      |
      | createdBy             | 597                          |
    Examples:
      | name        | eligibilityEndDate | programCode | subProgramTypeCode | reconEligibilityStartDate | reconEligibilityStatusCode | eligibilityEndDate2 | reconProgramCode | reconSubProgramTypeCode | genericFieldText1 | reconGenericFieldText1 | fileSource       | population    | benefitStatus        | scenario                   | expectedvalue |
      | 24136-02-11 | future             | H           | HealthyIndianaPlan | 1stDayofPresentMonth      | V                          | 60DaysFromYesterday | R                | HoosierHealthwise       | ELigible          | null                   | RECIPIENT-HRECIP | HHW-Voluntary | ELigible             | Program Sub-Program Change | null          |
      | 24136-02-12 | future             | R           | HoosierHealthwise  | 1stDayofPresentMonth      | V                          | 60DaysFromYesterday | A                | HoosierCareConnect      | Eligible          | null                   | RECIPIENT-HRECIP | HCC-Voluntary | ELigible             | Program Sub-Program Change | null          |
      | 24136-02-13 | future             | A           | HoosierCareConnect | 1stDayofPresentMonth      | X                          | 60DaysFromYesterday | H                | HealthyIndianaPlan      | null              | Eligible               | RECIPIENT        | HIP-Mandatory | benefit status error | Program Sub-Program Change | null          |
      | 24136-02-24 | future             | A           | HoosierCareConnect | 1stDayofPresentMonth      | V                          | 60DaysFromYesterday | H                | HealthyIndianaPlan      | null              | Eligible               | RECIPIENT-HRECIP | HIP-Voluntary | benefit status error | Program Sub-Program Change | Eligible      |

  @API-CP-24136 @API-CP-24136-02-14 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY LOE MMIS Sends Reconciliation-Base Aid category Change-Non High End Date- All programs
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | <genericFieldText1>  |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT-HRECIP     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
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
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | <reconCategoryCode>  |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Base Category Change","null"
    Examples:
      | name        | eligibilityEndDate | programCode | subProgramTypeCode | reconCategoryCode | reconEligibilityStatusCode | eligibilityEndDate2 | reconProgramCode | reconSubProgramTypeCode |
      | 24136-02-14 | future             | H           | HealthyIndianaPlan | 100               | V                          | 60DaysFromYesterday | R                | HoosierHealthwise       |
      | 24136-02-15 | future             | R           | HoosierHealthwise  | 100               | V                          | 60DaysFromYesterday | A                | HoosierCareConnect      |
      | 24136-02-16 | future             | A           | HoosierCareConnect | 100               | V                          | 60DaysFromYesterday | H                | HealthyIndianaPlan      |

  @API-CP-24296 @API-CP-24296-02-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY RECON:Non high End Date LOE (Part 2 Create or Update) 2.0 10.0 (Non HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | eligibilityEndDate           | future               |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | Eligible             |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | A                       |
      | subProgramTypeCode           | HoosierCareConnect      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | genericFieldText1            | Eligible                |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>  |
      | eligibilityEndDate    | <eligibilityEndDate>    |
      | categoryCode          | 10                      |
      | programCode           | A                       |
      | subProgramTypeCode    | HoosierCareConnect      |
      | eligibilityStatusCode | <eligibilityStatusCode> |
      | genericFieldText1     | Eligible                |
      | genericFieldText2     | <chechCore>             |
      | createdOn             | current                 |
      | createdBy             | 597                     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Differens scenarios
      | name        | scenario         | eligibilityStartDate | eligibilityEndDate | eligibilityStatusCode | chechCore | population    | benefitStatus | action   | consumerAction | planSelectionAllowed | changeAllowedFrom    | changeAllowedUntil        |
      | 24296-02-01 | EXACT MATCH      | 1stDayofPresentMonth | future             | V                     | 0         | HCC-Voluntary | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |
      | 24296-02-02 | CHANGED END DATE | 1stDayofPresentMonth | futureDate         | M                     | 0         | HCC-Mandatory | Eligible      | Required | Enroll         | true                 | 1stDayofPresentMonth | 60DaysFromFirstDayOfMonth |

  @API-CP-24296 @API-CP-24296-02-02 @API-CP-33037 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: IN-EB ELIGIBILITY RECON:NOn high End Date LOE (Part 2 Create or Update) 2.0 10.0 (HIP)
  2.0 One Inbound BASE Segment: Update Matched Eligibility Segment
  10.0 Set the “Check CORE Indicator” when Conditions are Found
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
      | fileSource                   | HRECIP               |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId            |
      | isEligibilityRequired        | yes                          |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                           |
      | recordId                     | 21                           |
      | eligibilityRecordId          | 21                           |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate>       |
      | eligibilityEndDate           | <reconEligibilityEndDate>    |
      | programCode                  | H                            |
      | subProgramTypeCode           | HealthyIndianaPlan           |
      | eligibilityStatusCode        | <reconEligibilityStatusCode> |
      | categoryCode                 | 10                           |
      | genericFieldText1            | <reconHIPstatus>             |
      | coverageCode                 | cc01                         |
      | fileSource                   | <fileSource>                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | enrollmentRecordId           | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | <eligibilityEndDate>    |
      | txnStatus                    | Accepted                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 455701400               |
      | planId                       | 104                     |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | rejectionReason              | test1                   |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | <eligibilityStartDate>       |
      | eligibilityEndDate    | <reconEligibilityEndDate>    |
      | categoryCode          | 10                           |
      | programCode           | H                            |
      | subProgramTypeCode    | HealthyIndianaPlan           |
      | eligibilityStatusCode | <reconEligibilityStatusCode> |
      | genericFieldText1     | <reconHIPstatus>             |
      | genericFieldText2     | <chechCore>                  |
      | createdOn             | current                      |
      | createdBy             | 597                          |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>    |
      | population          | <population>    |
      | benefitStatus       | <benefitStatus> |
      | eligibilityScenario | <scenario>      |
      | timeframe           | Active          |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Examples: Different populations
      | name        | scenario    | eligibilityStartDate | eligibilityEndDate | eligibilityStatusCode | reconEligibilityStatusCode | HIPstatus | reconHIPstatus | fileSource       | chechCore | population    | benefitStatus | action      | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | reconEligibilityEndDate |
#      | 24296-02-03 | CHANGED END DATE | 1stDayofPresentMonth | futureDate         | M                     | V                          | Eligible  | Conditional    | HRECIP           | 0         | HIP-Voluntary | Benefit Status Error | Unavailable | null           | false                | null              | null               | future                  |
      | 24296-02-04 | EXACT MATCH | 1stDayofPresentMonth | futureDate         | M                     | X                          | Eligible  | Conditional    | RECIPIENT_HRECIP | 1         | HIP-Mandatory | Enrolled      | Unavailable | null           | false                | null              | null               | futureDate              |
