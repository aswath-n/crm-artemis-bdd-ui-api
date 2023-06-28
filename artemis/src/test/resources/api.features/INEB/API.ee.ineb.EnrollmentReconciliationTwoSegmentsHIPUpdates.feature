Feature:IN-EB ENROLLMENT (A,C or C,C) Create or Update: Update for new HIP consumers

  @API-CP-33037 @API-CP-33037-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.1 CP-33037 No Accepted Enrollment in System, Create new enrollment segment for HIP Eligibility
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | no                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                        |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode3>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason2           |
      | planEndDateReason            | planEndDateReason2         |
      | rejectionReason              | rejectionReason2           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId    |
      | recordId            | 21                   |
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate   | highDate             |
      | txnStatus           | Accepted             |
      | programCode         | <programCode>        |
      | subProgramTypeCode  | <subProgramTypeCode> |
      | planCode            | <planCode3>          |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason2     |
      | planEndDateReason   | planEndDateReason2   |
      | rejectionReason     | rejectionReason2     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode2>                |
      | status               | <reconTxnStatus>           |
      | selectionReason      | selectionReason2           |
      | planEndDateReason    | planEndDateReason2         |
      | rejectionReason      | rejectionReason2           |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <reconBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | No Enrollment        |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | Nov1stCurrentYear           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | status        | SUCCESS                               |

    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus |
      | 33037-01 | highDate                 | Benefit Status Error | H           | HealthyIndianaPlan | HIP-Mandatory |[blank]| 455701400 | 455701400 | A                 | ACCEPTED       | Enrolled           |

  @API-CP-33037 @API-CP-33037-06 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.1 CP-33037 No Accepted Enrollment in System, Create new enrollment segment for HIP Eligibility- Disenrolled status -non high end date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | no                         |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                        |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode3>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason2           |
      | planEndDateReason            | planEndDateReason2         |
      | rejectionReason              | rejectionReason2           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId    |
      | recordId            | 21                   |
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate   | highDate             |
      | txnStatus           | Accepted             |
      | programCode         | <programCode>        |
      | subProgramTypeCode  | <subProgramTypeCode> |
      | planCode            | <planCode3>          |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason2     |
      | planEndDateReason   | planEndDateReason2   |
      | rejectionReason     | rejectionReason2     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode2>                |
      | status               | <reconTxnStatus>           |
      | selectionReason      | selectionReason2           |
      | planEndDateReason    | planEndDateReason2         |
      | rejectionReason      | rejectionReason2           |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <reconBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | No Enrollment        |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
      | [0].changeAllowedFrom    | null        |
      | [0].changeAllowedUntil   | null        |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | status        | SUCCESS                               |

    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus |
      | 33037-06 | lastDayOfThePresentYear  | Benefit Status Error | H           | HealthyIndianaPlan | HIP-Mandatory |[blank]| 455701400 | 455701400 | C                 | DISENROLLED    | Enrolled           |

  # this scenario of CP-33037 AC 2.0 is no longer valid and updated with CP-32272 AC 2.0
  @API-CP-33037 @API-CP-33037-02 @API-EE-IN-Removed @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.0 CP-33037 No Accepted Enrollment in System,DONOT Create new enrollment segment for HHW Eligibility
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | planCode                     | <planCode3>          |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason2     |
      | planEndDateReason            | planEndDateReason2   |
      | rejectionReason              | rejectionReason2     |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId    |
      | recordId            | 21                   |
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate   | highDate             |
      | txnStatus           | Accepted             |
      | programCode         | <programCode>        |
      | subProgramTypeCode  | <subProgramTypeCode> |
      | planCode            | <planCode3>          |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason2     |
      | planEndDateReason   | planEndDateReason2   |
      | rejectionReason     | rejectionReason2     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
      | benefitStatus       | Enrolled     |
      | eligibilityScenario | NO MATCH     |
      | enrollmentScenario  | null         |
      | timeframe           | Active       |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                 |
      | [0].consumerAction       | Enroll                   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 14DayFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                     |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId     |
      | consumerId    | <name>.consumerId                         |
      | status        | fail                                      |
      | errorStack    | No Enrollment Found with Multiple Records |
    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction |
      | 33037-02 | highDate                 | Eligible             | R           | HoosierHealthwise  | HHW-Mandatory |[blank]| 400752220 | 400752220 | A                 |

# this scenario of CP-33037 AC 2.0 is no longer valid and updated with CP-32272 AC 2.0
  @API-CP-33037 @API-CP-33037-02-dupl @API-EE-IN-Removed @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.0 CP-33037 No Accepted Enrollment in System,DONOT Create new enrollment segment for HCC Eligibility-dupl
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | enrollmentRecordId           | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityStatusCode        | M                    |
      | categoryCode                 | 10                   |
      | genericFieldText1            | null                 |
      | coverageCode                 | cc01                 |
      | planCode                     | <planCode3>          |
      | planId                       | 104                  |
      | serviceRegionCode            | Statewide            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
      | selectionReason              | selectionReason2     |
      | planEndDateReason            | planEndDateReason2   |
      | rejectionReason              | rejectionReason2     |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId    |
      | recordId            | 21                   |
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate   | highDate             |
      | txnStatus           | Accepted             |
      | programCode         | <programCode>        |
      | subProgramTypeCode  | <subProgramTypeCode> |
      | planCode            | <planCode3>          |
      | planId              | 104                  |
      | serviceRegionCode   | Statewide            |
      | anniversaryDate     | anniversaryDate      |
      | channel             | SYSTEM_INTEGRATION   |
      | selectionReason     | selectionReason2     |
      | planEndDateReason   | planEndDateReason2   |
      | rejectionReason     | rejectionReason2     |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population> |
      | population          | <population> |
      | benefitStatus       | Eligible     |
      | eligibilityScenario | NO MATCH     |
      | enrollmentScenario  | null         |
      | timeframe           | Active       |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                  |
      | [0].consumerAction       | Enroll                    |
      | [0].planSelectionAllowed | true                      |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth      |
      | [0].changeAllowedUntil   | 60DaysFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                     |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId     |
      | consumerId    | <name>.consumerId                         |
      | status        | fail                                      |
      | errorStack    | No Enrollment Found with Multiple Records |
    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction |
      | 33037-03 | highDate                 | Eligible             | A           | HoosierCareConnect | HCC-Mandatory |[blank]| 499254630 | 499254630 | A                 |

  @API-CP-33037  @API-CP-33037-04 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.1 CP-33037 No Accepted Enrollment in System, Create new enrollment segment for HIP Eligibility-different dates
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | enrollmentRecordId           | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | highDate               |
      | enrollmentStartDate          | 1stDayOf2MonthsFromNow |
      | enrollmentEndDate            | highDate               |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityStatusCode        | M                      |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | coverageCode                 | cc01                   |
      | planCode                     | <planCode3>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Statewide              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason2       |
      | planEndDateReason            | planEndDateReason2     |
      | rejectionReason              | rejectionReason2       |
      | fileSource                   | RECIPIENT              |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId     |
      | recordId            | 21                    |
      | enrollmentStartDate | 1stDayofPresentMonth  |
      | enrollmentEndDate   | lastDayofPresentMonth |
      | txnStatus           | Accepted              |
      | programCode         | <programCode>         |
      | subProgramTypeCode  | <subProgramTypeCode>  |
      | planCode            | <planCode3>           |
      | planId              | 104                   |
      | serviceRegionCode   | Statewide             |
      | anniversaryDate     | anniversaryDate       |
      | channel             | SYSTEM_INTEGRATION    |
      | selectionReason     | selectionReason2      |
      | planEndDateReason   | planEndDateReason2    |
      | rejectionReason     | rejectionReason2      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayOf2MonthsFromNow |
      | medicalPlanEndDate   | highDate               |
      | enrollmentType       | MEDICAL                |
      | channel              | SYSTEM_INTEGRATION     |
      | anniversaryDate      | anniversaryDate        |
      | planCode             | <planCode3>            |
      | status               | ACCEPTED               |
      | selectionReason      | selectionReason2       |
      | planEndDateReason    | planEndDateReason2     |
      | rejectionReason      | rejectionReason2       |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>  |
      | population          | <population>  |
      | benefitStatus       | Enrolled      |
      | eligibilityScenario | NO MATCH      |
      | enrollmentScenario  | No Enrollment |
      | timeframe           | Active        |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | Nov1stCurrentYear           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | status        |    SUCCESS                            |

    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction |
      | 33037-04 | highDate                 | Benefit Status Error | H           | HealthyIndianaPlan | HIP-Mandatory |[blank]| 455701400 | 455701400 | C                 |

# this scenario of CP-33037 AC 2.1 is no longer valid and updated with CP-32272 AC 2.0
  @API-CP-33037  @API-CP-33037-05 @API-EE-IN-Removed @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 2.1 CP-33037 No Accepted Enrollment in System, Create new enrollment segment for HIP Eligibility-different plan codes & dates
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                         |
      | recordId                     | 21                         |
      | eligibilityRecordId          | 21                         |
      | enrollmentRecordId           | 21                         |
      | isEnrollmentProviderRequired | no                         |
      | eligibilityStartDate         | 1stDayofPresentMonth       |
      | eligibilityEndDate           | highDate                   |
      | enrollmentStartDate          | 1stDayofPresentMonth       |
      | enrollmentEndDate            | <initialEnrollmentEndDate> |
      | txnStatus                    | Accepted                   |
      | programCode                  | <programCode>              |
      | subProgramTypeCode           | <subProgramTypeCode>       |
      | eligibilityStatusCode        | M                          |
      | categoryCode                 | 10                         |
      | genericFieldText1            | null                       |
      | coverageCode                 | cc01                       |
      | planCode                     | <planCode1>                |
      | planId                       | 104                        |
      | serviceRegionCode            | Statewide                  |
      | anniversaryDate              | anniversaryDate            |
      | channel                      | SYSTEM_INTEGRATION         |
      | selectionReason              | selectionReason1           |
      | planEndDateReason            | planEndDateReason1         |
      | rejectionReason              | rejectionReason1           |
      | fileSource                   | RECIPIENT                  |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | A         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 21                     |
      | eligibilityRecordId          | 21                     |
      | enrollmentRecordId           | 21                     |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | highDate               |
      | enrollmentStartDate          | 1stDayOf2MonthsFromNow |
      | enrollmentEndDate            | highDate               |
      | txnStatus                    | Accepted               |
      | programCode                  | <programCode>          |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityStatusCode        | M                      |
      | categoryCode                 | 10                     |
      | genericFieldText1            | null                   |
      | coverageCode                 | cc01                   |
      | planCode                     | <planCode2>            |
      | planId                       | 104                    |
      | serviceRegionCode            | Statewide              |
      | anniversaryDate              | anniversaryDate        |
      | channel                      | SYSTEM_INTEGRATION     |
      | selectionReason              | selectionReason2       |
      | planEndDateReason            | planEndDateReason2     |
      | rejectionReason              | rejectionReason2       |
      | fileSource                   | RECIPIENT              |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId     |
      | recordId            | 21                    |
      | enrollmentStartDate | 1stDayofPresentMonth  |
      | enrollmentEndDate   | lastDayofPresentMonth |
      | txnStatus           | Accepted              |
      | programCode         | <programCode>         |
      | subProgramTypeCode  | <subProgramTypeCode>  |
      | planCode            | <planCode3>           |
      | planId              | 104                   |
      | serviceRegionCode   | Statewide             |
      | anniversaryDate     | anniversaryDate       |
      | channel             | SYSTEM_INTEGRATION    |
      | selectionReason     | selectionReason2      |
      | planEndDateReason   | planEndDateReason2    |
      | rejectionReason     | rejectionReason2      |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | C         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayOf2MonthsFromNow |
      | medicalPlanEndDate   | highDate             |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | anniversaryDate      | anniversaryDate      |
      | planCode             | <planCode2>          |
      | status               | ACCEPTED             |
      | selectionReason      | selectionReason2     |
      | planEndDateReason    | planEndDateReason2   |
      | rejectionReason      | rejectionReason2     |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>           |
      | population          | <population>           |
      | benefitStatus       | <initialBenefitStatus> |
      | eligibilityScenario | NO MATCH               |
      | enrollmentScenario  | null                   |
      | timeframe           | Active                 |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                     |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId     |
      | consumerId    | <name>.consumerId                         |
      | status        | fail                                      |
      | errorStack    | No Enrollment Found with Multiple Records |

    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction |
      | 33037-05 | highDate                 | Benefit Status Error | H           | HealthyIndianaPlan | HIP-Mandatory |[blank]| 455701400 | 355787430 | C                 |


