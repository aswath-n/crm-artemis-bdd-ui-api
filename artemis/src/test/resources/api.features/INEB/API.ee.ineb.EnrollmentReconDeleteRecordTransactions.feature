@Cp-32276
Feature:IN-EB: Assignment Update Decide Scenario - at least one "Delete" transaction and no "Add" or "Change" transactions

  @API-CP-32275 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Cp-32275-A.C.2.0-Inflight Enrollment- Donot update
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
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

    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 26                   |
      | [0].planCode           | <planCode2>          |
      | [0].startDate          | fdnxtmth::           |
      | [0].endDate            | highDate::           |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Statewide            |
      | [0].selectionReason    | 02                   |
      | [0].anniversaryDate    | anniversaryDate::    |
      | [0].channel            | SYSTEM_INTEGRATION   |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "<name>.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "<name>b"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | <name>.consumerId                |
      | [0].planId             | delete::                         |
      | [0].planCode           | <planCode1>                      |
      | [0].enrollmentId       | <name>b.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED              |
      | [0].txnStatus          | DISENROLL_SUBMITTED              |
      | [0].startDate          | 1stDayof2MonthsBefore::          |
      | [0].endDate            | <name>b.discontinuedEndDate      |
      | [0].enrollmentType     | delete::                         |
      | [0].subProgramTypeCode | <subProgramTypeCode>             |
      | [0].serviceRegionCode  | Statewide                        |
      | [1].planId             | delete::                         |
      | [1].planCode           | <planCode2>                      |
      | [1].consumerId         | <name>.consumerId                |
      | [1].enrollmentId       | <name>b.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                       |
      | [1].endDate            | highDate::                       |
      | [1].subProgramTypeCode | <subProgramTypeCode>             |
      | [1].serviceRegionCode  | Statewide                        |
    # plan transfer
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | eligibilityEndDate    | highDate             |
      | categoryCode          | 10                   |
      | programCode           | <programCode>        |
      | subProgramTypeCode    | <subProgramTypeCode> |
      | eligibilityStatusCode | M                    |
      | genericFieldText1     | null                 |
      | genericFieldText2     | 0                    |
      | createdOn             | current              |
      | createdBy             | 597                  |
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | <planCode1>           |
      | status               | DISENROLL_SUBMITTED   |
      | selectionReason      | selectionReason1      |
      | planEndDateReason    | null                  |
      | rejectionReason      | rejectionReason1      |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
      | anniversaryDate      | anniversaryDate    |
      | planCode             | <planCode2>        |
      | status               | SUBMITTED_TO_STATE |
      | selectionReason      | null               |
      | planEndDateReason    | null               |
      | rejectionReason      | null               |
#    And I initiated get benefit status by consumer id "<name>.consumerId"
#    And I run get enrollment api
#    Then I verify latest benefit status information with data
#      | programPopulation   | <population>           |
#      | population          | <population>           |
#      | benefitStatus       | <initialBenefitStatus> |
#      | eligibilityScenario | NO MATCH               |
#      | enrollmentScenario  | No Enrollment          |
#      | timeframe           | Active                 |
#    Then I Verify Consumer Actions as following data
#      | [0].action               | Unavailable              |
#      | [0].consumerAction       | Plan Change Pre-lockin   |
#      | [0].planSelectionAllowed | true                     |
#      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
#      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
#    Then I will verify business events are generated with data
#      | eventName     | ENROLLMENT_SAVE_EVENT                 |
##      | correlationId | <name>a.traceid       |
#      | correlationId | <name>a.enrollments.[0].correlationId |
#      | consumerId    | <name>.consumerId                     |
#      | consumerFound | true                                  |
#      | DPBI          |[blank]|

    # Now running our scenario with 2 D records
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
      | planCode                     | <planCode2>          |
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
      | enrollmentStartDate | 1stDayofNextMonth    |
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
      | genericFieldText2 | D         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | <planCode1>           |
      | status               | DISENROLL_SUBMITTED   |
      | selectionReason      | selectionReason1      |
      | planEndDateReason    | null                  |
      | rejectionReason      | rejectionReason1      |
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofNextMonth  |
      | medicalPlanEndDate   | highDate           |
      | enrollmentType       | MEDICAL            |
      | channel              | SYSTEM_INTEGRATION |
      | anniversaryDate      | anniversaryDate    |
      | planCode             | <planCode2>        |
      | status               | SUBMITTED_TO_STATE |
      | selectionReason      | null               |
      | planEndDateReason    | null               |
      | rejectionReason      | null               |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | Eligible             |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | In-flight Enrollment |
      | timeframe           | Active               |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required                 |
      | [0].consumerAction       | Enroll                   |
      | [0].planSelectionAllowed | null                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 14DayFromFirstDayOfMonth |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT                 |
#      | correlationId | <name>c.traceid         |
      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId    | <name>.consumerId                     |
      | status        | FAIL                                  |
      | errorStack    | In-flight Enrollment                  |
    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction |
      | 32275-01 | highDate                 | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 400752220 | 300119960 | 700410350 | D                 |

  @API-CP-32276 @API-CP-32276-01.1 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.1 CP-32276 Enrollment in System for the 'D' record combination-current- No match - Donot update
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
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
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | <reconEnrollmentEndDate> |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode2>              |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason2         |
      | planEndDateReason            | planEndDateReason2       |
      | rejectionReason              | rejectionReason2         |
      | fileSource                   | RECIPIENT                |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId         |
      | recordId            | 21                        |
      | enrollmentStartDate | 1stDayofPresentMonth      |
      | enrollmentEndDate   | 60DaysFromFirstDayOfMonth |
      | txnStatus           | Accepted                  |
      | programCode         | <programCode>             |
      | subProgramTypeCode  | <subProgramTypeCode>      |
      | planCode            | <planCode3>               |
      | planId              | 104                       |
      | serviceRegionCode   | Statewide                 |
      | anniversaryDate     | anniversaryDate           |
      | channel             | SYSTEM_INTEGRATION        |
      | selectionReason     | selectionReason2          |
      | planEndDateReason   | planEndDateReason2        |
      | rejectionReason     | rejectionReason2          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | D         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth       |
      | medicalPlanEndDate   | <initialEnrollmentEndDate> |
      | enrollmentType       | MEDICAL                    |
      | channel              | SYSTEM_INTEGRATION         |
      | anniversaryDate      | anniversaryDate            |
      | planCode             | <planCode1>                |
      | status               | <reconTxnStatus>           |
      | selectionReason      | selectionReason1           |
      | planEndDateReason    | planEndDateReason1         |
      | rejectionReason      | rejectionReason1           |
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
      | eventName  | ENROLLMENT_SAVE_EVENT        |
      | consumerId | <name>.consumerId            |
      | status     | FAIL                         |
      | errorStack | No Plan Match D Record Error |

    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus | reconEnrollmentEndDate | reconEnrollmentEndDate2 |
      | 32276-01 | highDate                 | Benefit Status Error | H           | HealthyIndianaPlan | HIP-Mandatory | 755726440 | 455701400 | 355787430 | D                 | ACCEPTED       | Enrolled           | future                 | lastDayofNextMonth      |


  @API-CP-32276 @API-CP-32280 @API-CP-32280-01 @API-CP-32276-01.2 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.1 CP-32276 Enrollment in System for the 'D' record combination-current- Plan match -Update & Recon Business Event
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
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
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | Nov1stCurrentYear           |
      | [0].changeAllowedUntil   | Dec15thCurrentYear          |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | enrollmentEndDate            | <reconEnrollmentEndDate> |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode2>              |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason2         |
      | planEndDateReason            | planEndDateReason2       |
      | rejectionReason              | rejectionReason2         |
      | fileSource                   | RECIPIENT                |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId         |
      | recordId            | 21                        |
      | enrollmentStartDate | 1stDayofNextMonth         |
      | enrollmentEndDate   | 60DaysFromFirstDayOfMonth |
      | txnStatus           | Accepted                  |
      | programCode         | <programCode>             |
      | subProgramTypeCode  | <subProgramTypeCode>      |
      | planCode            | <planCode1>               |
      | planId              | 104                       |
      | serviceRegionCode   | Statewide                 |
      | anniversaryDate     | anniversaryDate           |
      | channel             | SYSTEM_INTEGRATION        |
      | selectionReason     | selectionReason2          |
      | planEndDateReason   | Disenroll                 |
      | rejectionReason     | rejectionReason2          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | D         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | <planCode1>           |
      | status               | <reconTxnStatus>      |
      | selectionReason      | selectionReason1      |
      | planEndDateReason    | Disenroll             |
      | rejectionReason      | rejectionReason1      |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
#CP-32276 A.C 5.0
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <reconBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | Plan Match           |
      | timeframe           | Active               |
    #CP-32276 A.C 6.0
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
      | [0].changeAllowedFrom    | null        |
      | [0].changeAllowedUntil   | null        |
    Then I will verify business events are generated with data
      | eventName  | ENROLLMENT_SAVE_EVENT |
#      | correlationId | <name>c.traceid         |
#      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId | <name>.consumerId     |
      | status     | SUCCESS               |
    #CP-32280 A.C 9.0
    Then I will verify business events are generated with data
      | eventName                     | RECONCILIATION            |
#      | correlationId        | <name>a.traceid             |
      | consumerId                    | <name>.consumerId         |
      | channel                       | SYSTEM_INTEGRATION        |
      | createdBy                     | 597                       |
      | processDate                   | current                   |
      | reconciliationAction          | Enrollment Updated        |
      | oldValue.eligibilityStartDate | 1stDayofNextMonth         |
      | oldValue.eligibilityEndDate   | <reconEligibilityEndDate> |
      | oldValue.selectionStatus      | ACCEPTED                  |
      | newValue.selectionStatus      | DISENROLLED               |
      | consumerName                  | <name>                    |
      | newValue.eligibilityStartDate | 1stDayofNextMonth         |
      | newValue.eligibilityEndDate   | lastDayofPresentMonth     |


    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus | reconEnrollmentEndDate | reconEnrollmentEndDate2 |
      | 32276-03 | highDate                 | Enrolled             | H           | HealthyIndianaPlan | HIP-Mandatory | 755726440 | 455701400 | 355787430 | D                 | DISENROLLED    | Enrolled           | future                 | lastDayofNextMonth      |


  @API-CP-32276 @API-CP-32280 @API-CP-32280-02 @API-CP-32276-02.2 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.1 CP-32276 Enrollment in System for the 'D' record combination-current- Plan match-NON HIP -Update & Recon Business Event
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
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
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | enrollmentEndDate            | <reconEnrollmentEndDate> |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode2>              |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason2         |
      | planEndDateReason            | planEndDateReason2       |
      | rejectionReason              | rejectionReason2         |
      | fileSource                   | RECIPIENT                |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId         |
      | recordId            | 21                        |
      | enrollmentStartDate | 1stDayofNextMonth         |
      | enrollmentEndDate   | 60DaysFromFirstDayOfMonth |
      | txnStatus           | Accepted                  |
      | programCode         | <programCode>             |
      | subProgramTypeCode  | <subProgramTypeCode>      |
      | planCode            | <planCode1>               |
      | planId              | 104                       |
      | serviceRegionCode   | Statewide                 |
      | anniversaryDate     | anniversaryDate           |
      | channel             | SYSTEM_INTEGRATION        |
      | selectionReason     | selectionReason2          |
      | planEndDateReason   | Disenroll                 |
      | rejectionReason     | rejectionReason2          |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | D         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | 1stDayofPresentMonth  |
      | medicalPlanEndDate   | lastDayofPresentMonth |
      | enrollmentType       | MEDICAL               |
      | channel              | SYSTEM_INTEGRATION    |
      | anniversaryDate      | anniversaryDate       |
      | planCode             | <planCode1>           |
      | status               | <reconTxnStatus>      |
      | selectionReason      | selectionReason1      |
      | planEndDateReason    | Disenroll             |
      | rejectionReason      | rejectionReason1      |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
#CP-32276 A.C 5.0
#    Then I verify latest benefit status information with data
#      | programPopulation   | <population>         |
#      | population          | <population>         |
#      | benefitStatus       | <reconBenefitStatus> |
#      | eligibilityScenario | NO MATCH             |
#      | enrollmentScenario  | Plan Match           |
#      | timeframe           | Active               |
    #CP-32276 A.C 6.0
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | null                 |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth |
    Then I will verify business events are generated with data
      | eventName  | ENROLLMENT_SAVE_EVENT |
#      | correlationId | <name>c.traceid         |
#      | correlationId | <name>c.enrollments.[0].correlationId |
      | consumerId | <name>.consumerId     |
      | status     | SUCCESS         |
    #CP-32280 A.C 9.0
    Then I will verify business events are generated with data
      | eventName                     | RECONCILIATION            |
#      | correlationId        | <name>a.traceid             |
      | consumerId                    | <name>.consumerId         |
      | channel                       | SYSTEM_INTEGRATION        |
      | createdBy                     | 597                       |
      | processDate                   | current                   |
      | reconciliationAction          | Enrollment Updated        |
      | oldValue.eligibilityStartDate | 1stDayofNextMonth         |
      | oldValue.eligibilityEndDate   | <reconEligibilityEndDate> |
      | oldValue.selectionStatus      | ACCEPTED                  |
      | newValue.selectionStatus      | DISENROLLED               |
      | consumerName                  | <name>                    |
      | newValue.eligibilityStartDate | 1stDayofNextMonth         |
      | newValue.eligibilityEndDate   | lastDayofPresentMonth     |


    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus | reconEnrollmentEndDate |
      | 32276-05 | highDate                 | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 300119960 | 500307680 | 355787430 | D                 | DISENROLLED    | Eligible           | future                 |
      | 32276-06 | highDate                 | Enrolled             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 399243310 | 355787430 | D                 | DISENROLLED    | Eligible           | future                 |

  @API-CP-32276 @API-CP-32280 @API-CP-32280-03 @API-CP-32276-03.2 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: A.C 3.1 CP-32276 Enrollment in System for the 'D' record combination-current-Exact Match -NON HIP -Update & Recon Business Event
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    # Create pre-existing eligibility and enrollment
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId          |
      | isEligibilityRequired        | yes                        |
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
      | enrollmentScenario  | No Enrollment          |
      | timeframe           | Active                 |
    Then I Verify Consumer Actions as following data
      | [0].action               | Available                |
      | [0].consumerAction       | Plan Change Pre-lockin   |
      | [0].planSelectionAllowed | true                     |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth     |
      | [0].changeAllowedUntil   | 90DayFromFirstDayOfMonth |
    # Now running our scenario with A + C or C + C records
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId        |
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 21                       |
      | eligibilityRecordId          | 21                       |
      | enrollmentRecordId           | 21                       |
      | isEnrollmentProviderRequired | no                       |
      | eligibilityStartDate         | 1stDayofPresentMonth     |
      | eligibilityEndDate           | highDate                 |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | enrollmentEndDate            | <reconEnrollmentEndDate> |
      | txnStatus                    | Accepted                 |
      | programCode                  | <programCode>            |
      | subProgramTypeCode           | <subProgramTypeCode>     |
      | eligibilityStatusCode        | M                        |
      | categoryCode                 | 10                       |
      | genericFieldText1            | null                     |
      | coverageCode                 | cc01                     |
      | planCode                     | <planCode2>              |
      | planId                       | 104                      |
      | serviceRegionCode            | Statewide                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason2         |
      | planEndDateReason            | planEndDateReason2       |
      | rejectionReason              | rejectionReason2         |
      | fileSource                   | RECIPIENT                |
    And User provide other enrollment segments details
      | uiid              | uiid::              |
      | segmentTypeCode   | OTHER_TXN           |
      | genericFieldText1 | null                |
      | genericFieldText2 | <enrollReconAction> |
      | genericFieldText3 | MEDICAL             |
    And User provide one more enrollment details
      | consumerId          | <name>.consumerId          |
      | recordId            | 21                         |
      | enrollmentStartDate | 1stDayofPresentMonth       |
      | enrollmentEndDate   | <initialEnrollmentEndDate> |
      | txnStatus           | Accepted                   |
      | programCode         | <programCode>              |
      | subProgramTypeCode  | <subProgramTypeCode>       |
      | planCode            | <planCode1>                |
      | planId              | 104                        |
      | serviceRegionCode   | Statewide                  |
      | anniversaryDate     | anniversaryDate            |
      | channel             | SYSTEM_INTEGRATION         |
      | selectionReason     | selectionReason2           |
      | planEndDateReason   | Disenroll                  |
      | rejectionReason     | rejectionReason2           |
    And User provide other enrollment segments details
      | uiid              | uiid::    |
      | segmentTypeCode   | OTHER_TXN |
      | genericFieldText1 | null      |
      | genericFieldText2 | D         |
      | genericFieldText3 | MEDICAL   |
    Then I send API call with name "<name>c" for create Eligibility and Enrollment
      #CP-32276 A.C 5.0- Redetermine Benefit Status
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>         |
      | population          | <population>         |
      | benefitStatus       | <reconBenefitStatus> |
      | eligibilityScenario | NO MATCH             |
      | enrollmentScenario  | Plan Match           |
      | timeframe           | Active               |
    #CP-32276 A.C 6.0 Redetermine Consumer Actions
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | null                 |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth |
    Then I will verify business events are generated with data
      | eventName  | ENROLLMENT_SAVE_EVENT |
      | consumerId | <name>.consumerId     |
      | status     | SUCCESS            |
    #CP-32280 A.C 9.0
    Then I will verify business events are generated with data
      | eventName                     | RECONCILIATION            |
      | consumerId                    | <name>.consumerId         |
      | channel                       | SYSTEM_INTEGRATION        |
      | createdBy                     | 597                       |
      | processDate                   | current                   |
      | reconciliationAction          | Enrollment Updated        |
      | oldValue.eligibilityStartDate | 1stDayofNextMonth         |
      | oldValue.eligibilityEndDate   | <reconEligibilityEndDate> |
      | oldValue.selectionStatus      | ACCEPTED                  |
      | newValue.selectionStatus      | DISREGARDED               |
      | consumerName                  | <name>                    |
      | newValue.eligibilityStartDate | 1stDayofNextMonth         |
      | newValue.eligibilityEndDate   | lastDayofPresentMonth     |


    Examples: non HIP populations
      | name     | initialEnrollmentEndDate | initialBenefitStatus | programCode | subProgramTypeCode | population    | planCode1 | planCode2 | planCode3 | enrollReconAction | reconTxnStatus | reconBenefitStatus | reconEnrollmentEndDate |
      | 32276-07 | highDate                 | Enrolled             | R           | HoosierHealthwise  | HHW-Mandatory | 300119960 | 500307680 | 355787430 | D                 | DISENROLLED    | Eligible           | future                 |
      | 32276-08 | highDate                 | Enrolled             | A           | HoosierCareConnect | HCC-Mandatory | 499254630 | 399243310 | 355787430 | D                 | DISENROLLED    | Eligible           | future                 |
