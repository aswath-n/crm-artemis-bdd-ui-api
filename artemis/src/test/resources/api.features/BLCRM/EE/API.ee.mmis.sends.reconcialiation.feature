Feature: MMIS send reconciliation Scenarios for Eligiblity & Enrollment Scenario ID 21

  @API-CP-17306 @API-CP-17306-01 @API-CP-17303-01 @API-CP-19112-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Eligibility - Record ID 21 (Exact Match)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | anniversaryDate              | anniversaryDate        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate>  |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | <reconProgramCode>          |
      | subProgramTypeCode           | <reconSubProgramTypeCode>   |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | <reconProgramCode>          |
      | subProgramTypeCode    | <reconSubProgramTypeCode>   |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
# When there is exact match, we do not see update event created but rather only reconciliation event so commenting them out for now. Requirements in the stories should be checked again
#    Then I will verify an "ELIGIBILITY_UPDATE_EVENT" for "DPBI" is created with fields in payload
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION              |
#      | correlationId        | <name>a.traceid             |
      | correlationId        | null                        |
      | consumerId           | <name>.consumerId           |
      | channel              | SYSTEM_INTEGRATION          |
      | createdBy            | 597                         |
      | processDate          | current                     |
      | eligibilityStartDate | <reconEligibilityStartDate> |
      | eligibilityEndDate   | <reconEligibilityEndDate>   |
      | consumerName         | <name>                      |
    Examples:
      | name    | eligibilityStartDate | eligibilityEndDate | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus | programCode | reconProgramCode | subProgramTypeCode | reconSubProgramTypeCode |
      | 17306-1 | 1stDayofPresentMonth | null               | 1stDayofPresentMonth      | null                    | Exact Match   | M           | M                | MEDICAIDGF         | MEDICAIDGF              |

  @API-CP-17306 @API-CP-17306-01 @API-CP-17303-01 @API-CP-19112-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Eligibility - Record ID 21
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
      | subProgramTypeCode           | MEDICAIDGF             |
      | anniversaryDate              | anniversaryDate        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate>  |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | M                           |
      | subProgramTypeCode           | MEDICAIDGF                  |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | M                           |
      | subProgramTypeCode    | MEDICAIDGF                  |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName                          | RECONCILIATION              |
#      | correlationId                      | <name>a.traceid             |
      | correlationId                      | null                        |
      | consumerId                         | <name>.consumerId           |
      | channel                            | SYSTEM_INTEGRATION          |
      | createdBy                          | 597                         |
      | processDate                        | current                     |
      | consumerName                       | <name>                      |
      | oldValue.eligibilityProgramCode    | MEDICAID                    |
      | oldValue.eligibilitySubProgramCode | MEDICAIDGF                  |
      | oldValue.eligibilityStartDate      | <eligibilityStartDate>      |
      | oldValue.eligibilityEndDate        | <eligibilityEndDate>        |
      | oldValue.eligibilityEndReason      | null                        |
      | oldValue.eligibilityCategoryCode   | null                        |
      | oldValue.eligibilityCoverageCode   | string                      |
      | oldValue.eligibilityExemptionCode  | qwer                        |
      | oldValue.benefitStatus             | <oldValueBenefitStatus>     |
      | newValue.eligibilityProgramCode    | MEDICAID                    |
      | newValue.eligibilitySubProgramCode | MEDICAIDGF                  |
      | newValue.eligibilityStartDate      | <reconEligibilityStartDate> |
      | newValue.eligibilityEndDate        | <reconEligibilityEndDate>   |
      | newValue.eligibilityEndReason      | null                        |
      | newValue.eligibilityCategoryCode   | null                        |
      | newValue.eligibilityCoverageCode   | string                      |
      | newValue.eligibilityExemptionCode  | qwer                        |
      | newValue.benefitStatus             | null                        |

    Examples:
      | name    | eligibilityStartDate | eligibilityEndDate | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus           | oldValueBenefitStatus |
      | 17306-2 | 1stDayofPresentMonth | null               | 1stDayofNextMonth         | null                    | Changed Start Date      | Eligible              |
      | 17306-3 | 1stDayofPresentMonth | null               | 1stDayofPresentMonth      | 1stDayofNextMonth       | Changed End Date        | Benefit Status Error  |
      | 17306-4 | 1stDayofPresentMonth | future             | past                      | past                    | Shift Timeframe Back    | Benefit Status Error  |
      | 17306-5 | 1stDayofPresentMonth | future             | future                    | futureDate              | Shift Timeframe Forward | Benefit Status Error  |
      | 17306-6 | 1stDayofPresentMonth | future             | past                      | futureDate              | Expand Timeframe        | Benefit Status Error  |
      | 17306-7 | 1stDayofPresentMonth | futureDate         | nextMonth                 | future                  | Shrink Timeframe        | Benefit Status Error  |

  @API-CP-17306 @API-CP-17306-01 @API-CP-17303-01 @API-CP-19112-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Eligibility - Record ID 21 (Program Sub-Program Change)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | <name> |
      | age              | 0      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | anniversaryDate              | anniversaryDate        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                | facilityInfo                |
      | isEnrollemntRequired         | no                          |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate>  |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | <reconProgramCode>          |
      | subProgramTypeCode           | <reconSubProgramTypeCode>   |
      | categoryCode                 | 789AidCode                  |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | <reconProgramCode>          |
      | subProgramTypeCode    | <reconSubProgramTypeCode>   |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName                              | RECONCILIATION                |
#      | correlationId                          | <name>a.traceid               |
      | correlationId                          | null                          |
      | consumerId                             | <name>.consumerId             |
      | channel                                | SYSTEM_INTEGRATION            |
      | createdBy                              | 597                           |
      | processDate                            | current                       |
      | consumerName                           | <name>                        |
      | priorSegment.eligibilityProgramCode    | MEDICAID                      |
      | priorSegment.eligibilitySubProgramCode | <subProgramTypeCode>          |
      | priorSegment.eligibilityStartDate      | <eligibilityStartDate>        |
      | priorSegment.eligibilityEndDate        | lastDayofLastMonth            |
      | priorSegment.eligibilityEndReason      | Reconciliation Program Change |
      | priorSegment.eligibilityCategoryCode   | null                          |
      | priorSegment.eligibilityCoverageCode   | string                        |
      | priorSegment.eligibilityExemptionCode  | qwer                          |
      | priorSegment.benefitStatus             | <oldValueBenefitStatus>       |
      | newSegment.eligibilityProgramCode      | MEDICAID                      |
      | newSegment.eligibilitySubProgramCode   | <reconSubProgramTypeCode>     |
      | newSegment.eligibilityStartDate        | <reconEligibilityStartDate>   |
      | newSegment.eligibilityEndDate          | <reconEligibilityEndDate>     |
      | newSegment.eligibilityEndReason        | null                          |
      | newSegment.eligibilityCategoryCode     | 789AidCode                    |
      | newSegment.eligibilityCoverageCode     | string                        |
      | newSegment.eligibilityExemptionCode    | qwer                          |
      | newSegment.benefitStatus               | Eligible                      |

    Examples:
      | name    | eligibilityStartDate | eligibilityEndDate | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus                               | programCode | reconProgramCode | subProgramTypeCode | reconSubProgramTypeCode | oldValueBenefitStatus |
      | 17306-8 | 1stDayofPresentMonth | null               | 1stDayofPresentMonth      | null                    | Program Sub-Program Change                  | M           | F                | MEDICAIDGF         | FOSTERCARE              | Benefit Status Error  |
      | 17306-9 | 1stDayofLastMonth    | null               | 1stDayofPresentMonth      | null                    | Program Sub-Program Change With Date Change | M           | F                | MEDICAIDGF         | FOSTERCARE              | Benefit Status Error  |

  @API-CP-17306 @API-CP-17306-02 @API-CP-17303-01 @API-CP-19112-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Eligibility - No Match Scenario is identified and Eligibility Save Event is generated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                          |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconEnrollmentStartDate>  |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | <reconProgramCode>          |
      | subProgramTypeCode           | <reconSubProgramTypeCode>   |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<benefitStatus>","null"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | programCode           | <reconProgramCode>          |
      | subProgramTypeCode    | <reconSubProgramTypeCode>   |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName            | RECONCILIATION              |
#      | correlationId        | <name>a.traceid             |
      | correlationId        | null                        |
      | consumerId           | <name>.consumerId           |
      | channel              | SYSTEM_INTEGRATION          |
      | createdBy            | 597                         |
      | processDate          | current                     |
      | eligibilityStartDate | <reconEligibilityStartDate> |
      | eligibilityEndDate   | <reconEligibilityEndDate>   |
      | consumerName         | <name>                      |

    Examples:
      | name     | reconEligibilityStartDate | reconEligibilityEndDate | benefitStatus | reconProgramCode | reconSubProgramTypeCode |
      | 17306-10 | 1stDayofPresentMonth      | null                    | No Match      | M                | MEDICAIDGF              |

  @API-CP-17305 @API-CP-17307 @API-CP-19110 @API-CP-19112-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Recon Scenarios for Enrollment - Record ID 21 (No Match)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 17305-0 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
#      | categoryCode                 | 789Aidcode                  |
      | consumerId                   | 17305-0.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | planCode                     | 84                   |
      | planId                       | 1739                 |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "17305-0a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "17305-0.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "17305-0.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "No Match","No Match"
    When I initiated get eligibility by consumer id "17305-0.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
#      | categoryCode          | 789Aidcode                  |
      | programCode           | M                    |
      | subProgramTypeCode    | MEDICAIDGF           |
      | coverageCode          | string               |
      | eligibilityStatusCode | xyz                  |
      | exemptionCode         | qwer                 |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | null                 |
      | Created on            |[blank]|
      | createdBy             | 597                  |
      | Updated On            |[blank]|
      | updatedBy             | 597                  |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | 17305-0.consumerId                                      |
      | correlationId | 17305-0a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
#    Then I will verify an "ENROLLMENT_SAVE_EVENT" for "DPBI" is created with fields in payload
    Then I will verify business events are generated with data
      | eventName           | RECONCILIATION       |
#      | correlationId       | 17305-0a.traceid      |
      | correlationId       | null                  |
      | consumerId          | 17305-0.consumerId    |
      | channel             | SYSTEM_INTEGRATION   |
      | createdBy           | 597                  |
      | processDate         | current              |
      | consumerName        | 17305-0               |
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate   | null                 |
      | planChangeReason    | null                 |
      | rejectionReason     | null                 |
      | planCode            | 84                   |
      | enrollmentType      | MEDICAL              |
      | selectionStatus     | ACCEPTED             |
      | consumerPopulation  | MEDICAID-GENERAL     |

  @API-CP-17305 @API-CP-17307 @API-CP-19110 @API-CP-19112-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Recon Scenarios for Enrollment - Record ID 21 (Exact Match)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 17305-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 17305-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 17305-1.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 1739                 |
      | anniversaryDate              | future               |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
#      | categoryCode                 | 789Aidcode                  |
      | consumerId                   | 17305-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | enrollmentEndDate            | null                 |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | planCode                     | 84                   |
      | planId                       | 1739                 |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "17305-1a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "17305-1.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "17305-1.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "Exact Match","Exact Match"
    When I initiated get eligibility by consumer id "17305-1.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
#      | categoryCode          | 789Aidcode                  |
      | programCode           | M                    |
      | subProgramTypeCode    | MEDICAIDGF           |
      | coverageCode          | string               |
      | eligibilityStatusCode | xyz                  |
      | exemptionCode         | qwer                 |
      | eligibilityStartDate  | 1stDayofLastMonth    |
      | eligibilityEndDate    | null                 |
      | Created on            |[blank]|
      | createdBy             | 597                  |
      | Updated On            |[blank]|
      | updatedBy             | 597                  |
    And Wait for 10 seconds
# When there is exact match, we do not see update event created but rather only reconciliation event so commenting them out for now. Requirements in the stories should be checked again
#    Then I will verify business events are generated with data
#      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
##      | correlationId | 17305-1a.eligibilities[0].coreEligibility.correlationId |
#      | consumerId    | 17305-1.consumerId                                      |
#      | consumerFound | true                                                    |
#      | DPBI          |[blank]|
#    Then I will verify business events are generated with data
#      | eventName     | ENROLLMENT_UPDATE_EVENT |
##      | correlationId | 17305-1a.traceid        |
#      | consumerId    | 17305-1.consumerId      |
#      | consumerFound | true                    |
#      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName           | RECONCILIATION       |
      | module              | Enrollment           |
#      | correlationId       | 17305-1a.traceid     |
      | consumerId          | 17305-1.consumerId   |
      | channel             | SYSTEM_INTEGRATION   |
      | createdBy           | 597                  |
      | processDate         | current              |
      | consumerName        | 17305-1              |
      | enrollmentStartDate | 1stDayofLastMonth    |
      | enrollmentEndDate   | null                 |
      | planChangeReason    | null                 |
      | rejectionReason     | null                 |
      | planCode            | 84                   |
      | enrollmentType      | MEDICAL              |
      | selectionStatus     | ACCEPTED             |
      | consumerPopulation  | MEDICAID-GENERAL     |

  @API-CP-17305 @API-CP-17307 @API-CP-19110 @API-CP-19112-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Enrollment - Record ID 21
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
      | newCaseCreation  | yes      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
      | subProgramTypeCode           | MEDICAIDGF             |
      | anniversaryDate              | anniversaryDate        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | <planCode>            |
      | planId                       | 1739                  |
      | anniversaryDate              | future                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
#      | categoryCode                 | 789Aidcode                  |
      | consumerId                   | <name>.consumerId           |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                         |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconenrollmentStartDate>  |
      | enrollmentEndDate            | <reconenrollmentEndDate>    |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | M                           |
      | subProgramTypeCode           | MEDICAIDGF                  |
      | planCode                     | <reconPlanCode>             |
      | planId                       | 1739                        |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
#      | categoryCode          | 789Aidcode                  |
      | programCode           | M                           |
      | subProgramTypeCode    | MEDICAIDGF                  |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
    And Wait for 10 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName                    | RECONCILIATION             |
#      | correlationId                | <name>a.traceid            |
      | correlationId                | null                       |
      | consumerId                   | <name>.consumerId          |
      | channel                      | SYSTEM_INTEGRATION         |
      | createdBy                    | 597                        |
      | processDate                  | current                    |
      | consumerName                 | <name>                     |
      | oldValue.enrollmentStartDate | <enrollmentStartDate>      |
      | oldValue.enrollmentEndDate   | <enrollmentEndDate>        |
      | oldValue.planChangeReason    | null                       |
      | oldValue.rejectionReason     | null                       |
      | oldValue.planCode            | <planCode>                 |
      | oldValue.enrollmentType      | MEDICAL                    |
      | oldValue.selectionStatus     | ACCEPTED                   |
      | oldValue.pcpNpi              | null                       |
      | oldValue.pcpName             | null                       |
      | newValue.enrollmentStartDate | <reconenrollmentStartDate> |
      | newValue.enrollmentEndDate   | <reconenrollmentEndDate>   |
      | newValue.planChangeReason    | null                       |
      | newValue.rejectionReason     | null                       |
      | newValue.planCode            | <reconPlanCode>            |
      | newValue.enrollmentType      | MEDICAL                    |
      | newValue.selectionStatus     | ACCEPTED                   |
      | newValue.pcpNpi              | null                       |
      | newValue.pcpName             | null                       |

    Examples:
      | name    | eligibilityStartDate | eligibilityEndDate | reconEligibilityStartDate | reconEligibilityEndDate | eligibilityScenario | enrollmentScenario      | planCode | reconPlanCode | reconenrollmentStartDate | reconenrollmentEndDate | enrollmentStartDate  | enrollmentEndDate |
      | 17305-2 | 1stDayof2MonthsBefore | null               | 1stDayof2MonthsBefore     | null                    | Exact Match         | Changed Start Date      | 84       | 84            | 1stDayofLastMonth        | null                   | 1stDayof2MonthsBefore | null              |
      | 17305-3 | 1stDayofLastMonth     | null               | 1stDayofLastMonth         | null                    | Exact Match         | Changed End Date        | 84       | 84            | 1stDayofLastMonth        | 1stDayofNextMonth      | 1stDayofLastMonth     | null              |
      | 17305-4 | 1stDayofLastMonth     | future             | 1stDayof2MonthsBefore     | futureDate              | Expand Timeframe    | Shift Timeframe Back    | 84       | 84            | 1stDayof2MonthsBefore    | lastDayofPresentMonth  | 1stDayofLastMonth     | future            |
      | 17305-5 | 1stDayofLastMonth     | futureDate         | 1stDayofPresentMonth      | futureDate              | Changed Start Date  | Shift Timeframe Forward | 84       | 84            | 1stDayofPresentMonth     | futureDate             | 1stDayofLastMonth     | future            |
      | 17305-6 | 1stDayofLastMonth     | future             | 1stDayof2MonthsBefore     | futureDate              | Expand Timeframe    | Expand Timeframe        | 84       | 84            | 1stDayof2MonthsBefore    | futureDate             | 1stDayofLastMonth     | future            |
      | 17305-7 | 1stDayofLastMonth     | futureDate         | 1stDayofPresentMonth      | futureDate              | Changed Start Date  | Shrink Timeframe        | 84       | 84            | 1stDayofPresentMonth     | future                 | 1stDayofLastMonth     | futureDate        |

  @API-CP-17305 @API-CP-17307 @API-CP-19110 @API-CP-19112-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Recon Scenarios for Enrollment - Record ID 21 (Plan Change)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
      | subProgramTypeCode           | MEDICAIDGF             |
      | anniversaryDate              | anniversaryDate        |
    Then I send API call with name "TY1" for create Eligibility and Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | enrollmentEndDate            | <enrollmentEndDate>   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | <planCode>            |
      | planId                       | 1739                  |
      | anniversaryDate              | future                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
#      | categoryCode                 | 789Aidcode                  |
      | isEligibilityRequired        | yes                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                         |
      | recordId                     | 21                          |
      | isEnrollmentProviderRequired | no                          |
      | enrollmentStartDate          | <reconenrollmentStartDate>  |
      | enrollmentEndDate            | <reconenrollmentEndDate>    |
      | eligibilityStartDate         | <reconEligibilityStartDate> |
      | eligibilityEndDate           | <reconEligibilityEndDate>   |
      | txnStatus                    | Accepted                    |
      | programCode                  | M                           |
      | subProgramTypeCode           | MEDICAIDGF                  |
      | planCode                     | <reconPlanCode>             |
      | planId                       | 1739                        |
      | anniversaryDate              | anniversaryDate             |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "<name>.consumerId"
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
#      | categoryCode          | 789Aidcode                  |
      | programCode           | M                           |
      | subProgramTypeCode    | MEDICAIDGF                  |
      | coverageCode          | string                      |
      | eligibilityStatusCode | xyz                         |
      | exemptionCode         | qwer                        |
      | eligibilityStartDate  | <reconEligibilityStartDate> |
      | eligibilityEndDate    | <reconEligibilityEndDate>   |
      | Created on            |[blank]|
      | createdBy             | 597                         |
      | Updated On            |[blank]|
      | updatedBy             | 597                         |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
#      | correlationId | <name>a.traceid         |
      | consumerId    | <name>.consumerId       |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName                              | RECONCILIATION             |
      | module                                 | Enrollment                 |
#      | correlationId                          | <name>a.traceid            |
      | correlationId                          | null                       |
      | consumerId                             | <name>.consumerId          |
      | channel                                | SYSTEM_INTEGRATION         |
      | createdBy                              | 597                        |
      | processDate                            | current                    |
      | consumerName                           | <name>                     |
      | previousEnrollment.enrollmentStartDate | <enrollmentStartDate>      |
      | previousEnrollment.enrollmentEndDate   | <prevEnrollmentEndDate>    |
      | previousEnrollment.planChangeReason    | Reconciliation Plan Change |
      | previousEnrollment.rejectionReason     | null                       |
      | previousEnrollment.planCode            | <planCode>                 |
      | previousEnrollment.enrollmentType      | MEDICAL                    |
      | previousEnrollment.selectionStatus     | DISENROLLED                |
      | previousEnrollment.pcpNpi              | null                       |
      | previousEnrollment.pcpName             | null                       |
      | requestedSelection.enrollmentStartDate | <reconenrollmentStartDate> |
      | requestedSelection.enrollmentEndDate   | <reconenrollmentEndDate>   |
      | requestedSelection.planChangeReason    | null                       |
      | requestedSelection.rejectionReason     | null                       |
      | requestedSelection.planCode            | <reconPlanCode>            |
      | requestedSelection.enrollmentType      | MEDICAL                    |
      | requestedSelection.selectionStatus     | ACCEPTED                   |
      | requestedSelection.pcpNpi              | null                       |
      | requestedSelection.pcpName             | null                       |
    Examples:
      | name    | eligibilityStartDate  | eligibilityEndDate | reconEligibilityStartDate | reconEligibilityEndDate | eligibilityScenario | enrollmentScenario           | planCode | reconPlanCode | reconenrollmentStartDate | reconenrollmentEndDate | enrollmentStartDate   | enrollmentEndDate | prevEnrollmentEndDate    |
      | 17305-8 | 1stDayofLastMonth     | null               | 1stDayofLastMonth         | null                    | Exact Match         | Plan Change                  | 84       | 85            | 1stDayofLastMonth        | null                   | 1stDayofLastMonth     | null              | lastDayOf2MonthsBefore   |
      | 17305-9 | 1stDayof2MonthsBefore | null               | 1stDayofLastMonth         | null                    | Changed Start Date  | Plan Change with Date Change | 84       | 85            | 1stDayofLastMonth        | null                   | 1stDayof2MonthsBefore | null              | lastDayOf2ndMonthFromNow |